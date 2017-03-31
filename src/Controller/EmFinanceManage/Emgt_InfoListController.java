package Controller.EmFinanceManage;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.zkoss.bind.annotation.BindingParam;
import org.zkoss.bind.annotation.Command;
import org.zkoss.bind.annotation.NotifyChange;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zul.Window;

import bll.EmFinanceManage.emgt_selectBll;

import Model.EmGatheringModel;

public class Emgt_InfoListController {
	private emgt_selectBll bll = new emgt_selectBll();
	private List<EmGatheringModel> list = bll.getEmGatheringList("");
	private String company,gid,name,paytype,type;
	//查询
	@Command
	@NotifyChange("list")
	public void search(@BindingParam("ownmonth") Date ownmonth) {
		String sql="";
		if(ownmonth!=null)
		{
			sql=sql+" and ownmonth="+DateToStr(ownmonth);
		}
		if(company!=null&&!company.equals(""))
		{
			sql=sql+" and coba_shortname like '%"+company+"%'";
		}
		if(gid!=null&&!gid.equals(""))
		{
			sql=sql+" and a.gid="+gid;
		}
		if(name!=null&&!name.equals(""))
		{
			sql=sql+" and emba_name like '%"+name+"%'";
		}
		
		if(paytype!=null&&!paytype.equals(""))
		{
			sql=sql+" and emgt_paytype like '%"+paytype+"%'";
		}
		if(type!=null&&!type.equals(""))
		{
			sql=sql+" and emgt_type like '%"+type+"%'";
		}
		list = bll.getEmGatheringList(sql);
	}

	// 打开更新
	@Command
	@NotifyChange("list")
	public void openupdate(@BindingParam("model") EmGatheringModel model) {
		Map map = new HashMap<>();
		map.put("model", model);
		Window window;
		window = (Window) Executions.createComponents("Emgt_Update.zul", null,
				map);
		window.doModal();
		list = bll.getEmGatheringList("");
	}

	public List<EmGatheringModel> getList() {
		return list;
	}

	public void setList(List<EmGatheringModel> list) {
		this.list = list;
	}

	public String getCompany() {
		return company;
	}

	public void setCompany(String company) {
		this.company = company;
	}

	public String getGid() {
		return gid;
	}

	public void setGid(String gid) {
		this.gid = gid;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPaytype() {
		return paytype;
	}

	public void setPaytype(String paytype) {
		this.paytype = paytype;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}
	/**
	* 日期转换成字符串
	* @param date 
	* @return str
	*/
	public static String DateToStr(Date date) {
	   SimpleDateFormat format = new SimpleDateFormat("yyyyMM");
	   String str ="";
	   if(date!=null)
	   {
		   str = format.format(date);
	   }
	   return str;
	}
}
