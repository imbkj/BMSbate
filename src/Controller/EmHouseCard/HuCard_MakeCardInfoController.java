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
import org.zkoss.zul.Window;

import bll.CoLatencyClient.CoLatencyClient_AddBll;
import bll.EmHouseCard.EmHouse_MakeCardInfoSelectBll;
import bll.EmHouseCard.EmHouse_TakeCardInfoSelectBll;

import Model.EmHouseMakeCardInfoModel;
import Model.EmHouseTakeCardInfoModel;

public class HuCard_MakeCardInfoController {
	private EmHouse_MakeCardInfoSelectBll bll=new EmHouse_MakeCardInfoSelectBll();
	private EmHouse_TakeCardInfoSelectBll sbll=new EmHouse_TakeCardInfoSelectBll();
	private List<EmHouseMakeCardInfoModel> list=new ArrayList<EmHouseMakeCardInfoModel>();
	private List<String> ownmonthlist=sbll.getOwnmonthInfo("distinct(ownmonth) as ownmonth"," order by ownmonth desc","ownmonth");
	//获取客服信息
	private List<String> clientlist=bll.getLoginInfo();
	private List<EmHouseTakeCardInfoModel> slist=sbll.getStateInfo(" and state_type=1 ");
	private String sid=Executions.getCurrent().getParameter("f").toString();
	private String strsql="";
	public HuCard_MakeCardInfoController()
	{
		//1表示客服页面
		if(sid.equals("1")||sid=="1")
		{
			strsql=" and state_name in('等待提交')";
		}
		//2表示福利页面
		else if(sid.equals("2")||sid=="2")
		{
			strsql=" and state_name in('助理核收','福利核收','正在制卡')";
		}
		list=bll.getEmhouseMakeCardInfo(strsql);
	}
	//查询
	@Command
	@NotifyChange("list")
	public void search(@BindingParam("nametype") String nametype,@BindingParam("name") String name,
			@BindingParam("ownmonth") Date ownmonth,@BindingParam("client") String client,
			@BindingParam("statename") String statename)
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
				str=str+" and coba_shortname like'%"+name+"%'";
			}
			else if(nametype.equals("个人公积金号")||nametype=="个人公积金号")
			{
				str=str+" and Gjj_No like'%"+name+"%'";
			}
			else if(nametype.equals("单位公积金号")||nametype=="单位公积金号")
			{
				str=str+" and Gjj_CNo like'%"+name+"%'";
			}
		}
		if(ownmonth!=null&&!ownmonth.equals(""))
		{
			str=str+" and ownmonth="+datetostr(ownmonth);
		}
		if(client!=null&&!client.equals("")&&client!="")
		{
			str=str+" and coba_client='"+client+"'";
		}
		if(statename!=null&&!statename.equals("")&&statename!="")
		{
			str=str+" and State_Name='"+statename+"' and State_type=1";
		}
		list=bll.getEmhouseMakeCardInfo(str);
	}
		
	//打开编辑页面
	@Command
	@NotifyChange("list")
	public void openzul(@BindingParam("model") EmHouseMakeCardInfoModel model,@BindingParam("url") String url)
	{
		Map map=new HashMap<>();
		map.put("daid", model.getId()+"");
		map.put("id", model.getTarpid()+"");
		Window window=(Window) Executions.createComponents(url, null, map);
		window.doModal();
		list=bll.getEmhouseMakeCardInfo(strsql);
	}
	
	//打开详细页面
	@Command
	public void opendetail(@BindingParam("model") EmHouseMakeCardInfoModel model)
	{
		Map map=new HashMap<>();
		map.put("model", model);
		Window window=(Window) Executions.createComponents("HuCard_MakeCardInfo.zul", null, map);
		window.doModal();
	}
	public List<EmHouseMakeCardInfoModel> getList() {
		return list;
	}
	public void setList(List<EmHouseMakeCardInfoModel> list) {
		this.list = list;
	}
	public List<String> getOwnmonthlist() {
		return ownmonthlist;
	}
	public void setOwnmonthlist(List<String> ownmonthlist) {
		this.ownmonthlist = ownmonthlist;
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
	
	private String datetostr(Date date)
	{
		String str="";
		SimpleDateFormat ft=new SimpleDateFormat("yyyyMM");
		if(date!=null)
		{
			str=ft.format(date);
		}
		return str;
	}
	
}
