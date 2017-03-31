package Controller.EmHouseCard;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.zkoss.bind.annotation.BindingParam;
import org.zkoss.bind.annotation.Command;
import org.zkoss.bind.annotation.NotifyChange;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zul.Checkbox;
import org.zkoss.zul.Combobox;
import org.zkoss.zul.Grid;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Window;

import Model.EmHouseTakeCardInfoModel;
import bll.CoLatencyClient.CoLatencyClient_AddBll;
import bll.EmHouseCard.EmHouse_TakeCardInfoSelectBll;

public class EmHouse_TakeEditController {
	private EmHouse_TakeCardInfoSelectBll bll=new EmHouse_TakeCardInfoSelectBll();
	private List<EmHouseTakeCardInfoModel> list=new ArrayList<EmHouseTakeCardInfoModel>();
	private List<String> ownmonthlist=bll.getOwnmonthInfo("distinct(ownmonth) as ownmonth"," order by ownmonth desc","ownmonth");
	private List<String> applist=bll.getOwnmonthInfo("distinct(convert(varchar(6),re_apptime,112)) as appmonth"," and re_apptime is not null order by appmonth desc","appmonth");
	//获取客服信息
	private CoLatencyClient_AddBll clientbll=new CoLatencyClient_AddBll();
	private List<String> clientlist=clientbll.getLoginInfo();
	private List<EmHouseTakeCardInfoModel> slist=bll.getStateInfo(" and state_type=2 ");
	private Integer zlnum,flnum,yhnum;//助理核收、福利核收、已交银行
	private String strsql="";
	
	public EmHouse_TakeEditController()
	{
		list=bll.getEmhouseTakeCardInfo(" and state_name in('助理核收','福利核收','已交银行')");
		Integer[] intinfo=bll.getCountInfo();
		zlnum=intinfo[0];
		flnum=intinfo[1];
		yhnum=intinfo[2];
	}
	//查询
	@Command
	@NotifyChange("list")
	public void search(@BindingParam("nametype") String nametype,@BindingParam("name") String name,
			@BindingParam("ownmonth") String ownmonth,@BindingParam("appmonth") String appmonth,
			@BindingParam("ifidcard") String ifidcard,@BindingParam("clienttype") String clienttype,
			@BindingParam("client") String client,@BindingParam("type") String type,
			@BindingParam("statename") Combobox statename,@BindingParam("timetype") String timetype,
			@BindingParam("timevalue") Date timevalue)
	{
		String str="";
		if(!name.equals("")&&name!=""&&!nametype.equals("")&&nametype!="")
		{
			if(nametype.equals("员工姓名")||nametype=="员工姓名")
			{
				str=str+" and username='"+name+"'";
			}
			else if(nametype.equals("身份证号")||nametype=="身份证号")
			{
				str=str+" and idcard like'%"+name+"%'";
			}
			else if(nametype.equals("单位简称")||nametype=="单位简称")
			{
				str=str+" and shortname like'%"+name+"%'";
			}
			else if(nametype.equals("个人公积金号")||nametype=="个人公积金号")
			{
				str=str+" and re_gjjno like'%"+name+"%'";
			}
			else if(nametype.equals("单位公积金号")||nametype=="单位公积金号")
			{
				str=str+" and re_cgjjno like'%"+name+"%'";
			}
		}
		if(!clienttype.equals("")&&clienttype!=""&&!client.equals("")&&client!="")
		{
			if(clienttype.equals("客服")||clienttype=="客服")
			{
				str=str+" and (re_client='"+client+"' or coba_client='"+client+"')";
			}
			else if(nametype.equals("客服助理")||nametype=="客服助理")
			{
				str=str+" and Pla_ClientAssistant='"+client+"'";
			}
			else if(nametype.equals("福利助理")||nametype=="福利助理")
			{
				str=str+" and Pla_WelfreAssistant='"+client+"'";
			}
		}
		
		if(!timetype.equals("")&&timetype!=""&&timevalue!=null&&!timevalue.equals(""))
		{ 	String ve=datechange(timevalue);
			if(timetype.equals("客服核收资料时间")||timetype=="客服核收资料时间")
			{
				str=str+" and convert(varchar(10),Pla_clientTime,120)='"+ve+"'";
			}
			else if(timetype.equals("助理核收资料时间")||timetype=="助理核收资料时间")
			{
				str=str+" and convert(varchar(10),Pla_ClientAssistantTime,120) ='"+ve+"'";
			}
			else if(timetype.equals("福利核收资料时间")||timetype=="福利核收资料时间")
			{
				str=str+" and convert(varchar(10),Gjj_WelfreAssistantTime,120) ='"+ve+"'";
			}
			else if(timetype.equals("递交银行时间")||timetype=="递交银行时间")
			{
				str=str+" and convert(varchar(10),Pla_ToBankTime,120) ='"+ve+"'";
			}
			else if(timetype.equals("福利领卡时间")||timetype=="福利领卡时间")
			{
				str=str+" and convert(varchar(10),Pla_flTime,120) ='"+ve+"'";
			}
			else if(timetype.equals("客服领卡时间")||timetype=="客服领卡时间")
			{
				str=str+" and convert(varchar(10),Pla_ReceiveTime,120) ='"+ve+"'";
			}
			else if(timetype.equals("员工领卡时间")||timetype=="员工领卡时间")
			{
				str=str+" and convert(varchar(10),Re_time,120) ='"+ve+"'";
			}
		}
		
		if(!ownmonth.equals("")&&ownmonth!="")
		{
			str=str+" and a.ownmonth="+ownmonth;
		}
		
		if(!appmonth.equals("")&&appmonth!="")
		{
			str=str+" and convert(varchar(6),re_apptime,112)='"+appmonth+"'";
		}
		
		if(!type.equals("")&&type!="")
		{
			str=str+" and Re_AccountType like '%"+type+"%'";
		}
		
		if(statename.getValue()!=null&&!statename.getValue().equals("")&&statename.getValue()!="")
		{
			str=str+" and re_state="+statename.getSelectedItem().getValue();
		}
		list=bll.getEmhouseTakeCardInfo(str+strsql);
	}
	
	//导入数据
	@Command
	public void insertdata()
	{
		/*String filepath = "D:/workspace/BMS/WebContent/EmHouseCard/s.xls"; 
		if(filepath.trim().toLowerCase().endsWith(".xls"))
		{
			loadXls(filepath);
			System.out.println("end=.xls");
		}
		else if(filepath.trim().toLowerCase().endsWith(".xlsx"))
		{
			loadXlsx(filepath);
			System.out.println("end=.xlsx");
		}*/
		Window window=(Window)Executions.createComponents("HuCard_TakeInfoAdd.zul", null, null);
		window.doModal();
	}
	
	//全选
	@Command
	public void checkall(@BindingParam("ck") Checkbox ck,@BindingParam("gd") Grid gd)
	{
		int n=500;
		if(list.size()<500)
		{
			n=list.size();
		}
		for (int i = 0; i < n; i++) {
			Checkbox cb = (Checkbox) gd.getCell(i,12).getChildren().get(0);
			cb.setChecked(ck.isChecked());
		}
	}
	
	//打开批量处理页面
	@Command
	public void openZUL(@BindingParam("gd") Grid gd,@BindingParam("url") String url)
	{
		List<EmHouseTakeCardInfoModel> maplist=new ArrayList<EmHouseTakeCardInfoModel>();
		int n=gd.getPageSize();
		if(list.size()<gd.getPageSize())
		{
			n=list.size();
		}
		for (int i = 0; i < n; i++) {
			Checkbox cb = (Checkbox) gd.getCell(i,12).getChildren().get(0);
			if(cb.isChecked()&&cb.isVisible())
			{
				EmHouseTakeCardInfoModel ml=cb.getValue();
				maplist.add(ml);
			}
		}
		String s=ifsame(maplist);
		if(s.equals("")||s=="")
		{
			Map map=new HashMap<>();
			map.put("list", maplist);
			Window window=(Window)Executions.createComponents(url, null, map);
			window.doModal();
		}
		else
		{
			Messagebox.show(s, "提示", Messagebox.OK, Messagebox.ERROR);
		}
	}
	
	//判断选择的数据是否都符合要求
	private String ifsame(List<EmHouseTakeCardInfoModel> maplist)
	{
		String str="";
		String bankname="";//缴存银行
		Integer stateid=0;//状态id
		String accounttype="";
		if(maplist.size()>0)
		{
		for(int i=0;i<maplist.size();i++)
		{
			if(i==0)
			{
				if(maplist.get(0)!=null)
				{
					bankname=maplist.get(0).getCohf_banklk()+"";
					stateid=maplist.get(0).getRe_state();
					accounttype=maplist.get(0).getRe_accounttype()+"";
				}
			}
			else
			{
				if(!stateid.equals(maplist.get(i).getRe_state())&&stateid!=maplist.get(i).getRe_state())
				{
					str="不同的领卡状态数据不能做批量处理";
					break;
				}
				else if(!ifaccountsame(accounttype,maplist.get(i).getRe_accounttype()))
				{
					str="独立户与中智户不能一起做批量处理";
					break;
				}
				else if(!bankname.equals(maplist.get(i).getCohf_banklk())&&bankname!=maplist.get(i).getCohf_banklk())
				{
					str="不同的缴存银行不能一起做批量处理";
					break;
				}
			}
		}
		}
		else
		{
			str="请选择数据";
		}
		return str;
	}
	
	//判断两个开户类型是否相同
	private boolean ifaccountsame(String firstaccount,String nextaccount)
	{
		boolean flag=true;
		if(firstaccount.equals("独立开户")||firstaccount=="独立开户")
		{
			if(!nextaccount.equals("独立开户")&&nextaccount!="独立开户")
			{
				flag=false;
			}
		}
		else
		{
			if(!nextaccount.equals("独立开户")&&nextaccount!="独立开户")
			{
				flag=false;
			}
		}
		return flag;
	}
	
	public List<EmHouseTakeCardInfoModel> getList() {
		return list;
	}

	public void setList(List<EmHouseTakeCardInfoModel> list) {
		this.list = list;
	}

	public List<String> getOwnmonthlist() {
		return ownmonthlist;
	}

	public void setOwnmonthlist(List<String> ownmonthlist) {
		this.ownmonthlist = ownmonthlist;
	}

	public List<String> getApplist() {
		return applist;
	}

	public void setApplist(List<String> applist) {
		this.applist = applist;
	}

	public List<String> getClientlist() {
		return clientlist;
	}

	public void setClientlist(List<String> clientlist) {
		this.clientlist = clientlist;
	}

	public List<EmHouseTakeCardInfoModel> getSlist() {
		return slist;
	}

	public void setSlist(List<EmHouseTakeCardInfoModel> slist) {
		this.slist = slist;
	}
	private String datechange(Date d)
	{
		String date="";
		SimpleDateFormat time=new SimpleDateFormat("yyyy-MM-dd"); 
		date=time.format(d);
		return date;
	}

	public Integer getZlnum() {
		return zlnum;
	}

	public void setZlnum(Integer zlnum) {
		this.zlnum = zlnum;
	}

	public Integer getFlnum() {
		return flnum;
	}

	public void setFlnum(Integer flnum) {
		this.flnum = flnum;
	}

	public Integer getYhnum() {
		return yhnum;
	}

	public void setYhnum(Integer yhnum) {
		this.yhnum = yhnum;
	}
}
