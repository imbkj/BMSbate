package Controller.EmHouseCard;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.zkoss.bind.BindUtils;
import org.zkoss.bind.annotation.BindingParam;
import org.zkoss.bind.annotation.Command;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zul.Grid;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Window;

import Controller.DocumentsInfo.DocumentsInfo_OperationController;
import Model.EmHouseMakeCardInfoModel;
import Model.EmHouseUpdateModel;
import Model.EmbaseModel;
import Util.RedirectUtil;
import Util.UserInfo;
import bll.EmHouseCard.EmHouse_MakeCardInfoOperateBll;
import bll.EmHouseCard.EmHouse_MakeCardInfoSelectBll;
import bll.EmHouseCard.EmHouse_TakeCardInfoSelectBll;

public class HuCard_MakeCardInfoAddController {
	private String gid =Executions.getCurrent().getArg().get("gid")+"";
	private EmHouse_TakeCardInfoSelectBll bll=new EmHouse_TakeCardInfoSelectBll();
	private List<String> ownmonthlist=bll.getOwnmonthInfo("distinct(ownmonth) as ownmonth"," order by ownmonth desc","ownmonth");
	private EmHouse_MakeCardInfoSelectBll mbll=new EmHouse_MakeCardInfoSelectBll();
	private List<String> banklist=mbll.getBankInfo();
	private EmHouseUpdateModel model=mbll.getEmhouseInfo(gid);
	private EmbaseModel cmodel=mbll.getEmBaseModel(" and gid="+gid);
	String ownmon="";
	public HuCard_MakeCardInfoAddController()
	{
		Calendar cal = Calendar.getInstance();
		int year = cal.get(Calendar.YEAR);
		int month = cal.get(Calendar.MONTH);
		if(month<9)
		{
			month=month+1;
			ownmon=year+"0"+month;
		}
		else
		{
			month=month+1;
			ownmon=year+""+month;
		}
	}
	
	//制卡信息新增提交
	@Command
	public void summit(@BindingParam("ownmonth") String ownmonth,@BindingParam("username") String username,
			@BindingParam("company") String company,@BindingParam("companyid") String companyid,
			@BindingParam("bank") String bank,@BindingParam("houseid") String houseid,
			@BindingParam("makecase") String makecase,@BindingParam("windeclare") Window windeclare,
			@BindingParam("gd") final Grid gd)
	{
		DocumentsInfo_OperationController docOC = new DocumentsInfo_OperationController();
		String[] message=new String[5];
		try {
			message = docOC.AddchkHaveTo(gd);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		if (message[0].equals("1")) {
			EmHouseMakeCardInfoModel m=new EmHouseMakeCardInfoModel();
			EmHouse_MakeCardInfoOperateBll obll=new EmHouse_MakeCardInfoOperateBll();
			m.setGid(Integer.parseInt(gid));
			m.setUsername(username);
			m.setGjj_cno(companyid);
			m.setGjj_no(houseid);
			m.setGjj_insertblank(bank);
			m.setGjj_case(makecase);
			m.setAddtime(datechange(new Date()));
			m.setGjj_addname(UserInfo.getUsername());
			m.setOwnmonth(Integer.parseInt(ownmonth));
			String[] str=obll.EmHuMakeCardAdd(m);
			if(str[0]=="1")
			{
				if(str[3]!=null&&!str[3].equals(""))
				{
					try {
						message = docOC.AddsubmitDoc(gd,str[3].toString());
					} catch (Exception e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
				Messagebox.show("提交成功", "提示", Messagebox.OK, Messagebox.INFORMATION);
				RedirectUtil util=new RedirectUtil();
				util.refreshEmUrl("/EmHouseCard/CardInfoList.zul");
			}
			else
			{
				Messagebox.show("提交失败", "提示", Messagebox.OK, Messagebox.ERROR);
			}	
		}
	}
	public List<String> getOwnmonthlist() {
		return ownmonthlist;
	}
	public void setOwnmonthlist(List<String> ownmonthlist) {
		this.ownmonthlist = ownmonthlist;
	}
	public List<String> getBanklist() {
		return banklist;
	}
	public void setBanklist(List<String> banklist) {
		this.banklist = banklist;
	}
	public EmHouseUpdateModel getModel() {
		return model;
	}
	public void setModel(EmHouseUpdateModel model) {
		this.model = model;
	}
	public String getOwnmon() {
		return ownmon;
	}
	public void setOwnmon(String ownmon) {
		this.ownmon = ownmon;
	}
	
	
	
	public EmbaseModel getCmodel() {
		return cmodel;
	}

	public void setCmodel(EmbaseModel cmodel) {
		this.cmodel = cmodel;
	}

	private String datechange(Date d)
	{
		String date="";
		SimpleDateFormat time=new SimpleDateFormat("yyyy-MM-dd hh:mm:ss"); 
		date=time.format(d);
		return date;
	}
}
