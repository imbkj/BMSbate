package Controller.EmBenefit;

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

import Model.EmActySuppilerGiftInfoModel;
import Model.EmActySupplierInfoModel;
import bll.EmBenefit.EmActy_GiftInfoSelectBll;

public class EmActy_GiftInfoListController {
	private EmActy_GiftInfoSelectBll bll=new EmActy_GiftInfoSelectBll();
	private List<EmActySuppilerGiftInfoModel> list=bll.getEmActyGiftInfo(" and gift_type = '礼品' ");
	private List<EmActySupplierInfoModel> suplist=bll.getSupName();
	private List<String> typelist=bll.getGiftType();
	private boolean visable=true;
	
	
	//查询
	@Command
	@NotifyChange({"list","visable"})
	public void seach(@BindingParam("name") String name,@BindingParam("ownmonth") Date ownmonth,
			@BindingParam("supname") String supname,@BindingParam("statetxt") String statetxt,
			@BindingParam("gifttype") String gifttype)
	{
		if(gifttype.equals("礼品")||gifttype=="礼品")
		{
			visable=true;
		}
		else
		{
			visable=false;
		}
		String str="";
		if(name!=null&&!name.equals("")&&name!="")
		{
			str=str+" and gift_name like'%"+name+"%'";
		}
		if(ownmonth!=null&&!ownmonth.equals(""))
		{
			str=str+" and ownmonth="+changedate(ownmonth);
		}
		if(supname!=null&&!supname.equals("")&&supname!="")
		{
			str=str+" and supp_name like '%"+supname+"%'";
		}
		if(statetxt!=null&&!statetxt.equals("")&&statetxt!=""&&!statetxt.equals("-1")&&statetxt!="-1")
		{
			str=str+" and gift_state="+statetxt;
		}
		if(gifttype!=null&&!gifttype.equals("")&&gifttype!="")
		{
			str=str+" and gift_type = '"+gifttype+"'";
		}
		list=bll.getEmActyGiftInfo(str);
	}
	
	//打开编辑页面
	@Command
	@NotifyChange("list")
	public void edit(@BindingParam("model") EmActySuppilerGiftInfoModel model,@BindingParam("url") String url)
	{
		Map map=new HashMap<>();
		map.put("model", model);
		Window window=(Window)Executions.createComponents(url, null, map);
		window.doModal();
		list=bll.getEmActyGiftInfo("");
	}
	
	//打开详细页面
	@Command
	@NotifyChange("list")
	public void openzul(@BindingParam("model") EmActySuppilerGiftInfoModel model)
	{
		Map map=new HashMap<>();
		map.put("model", model);
		Window window=(Window)Executions.createComponents("EmActy_GiftInfo.zul", null, map);
		window.doModal();
	}
	
	//打开更新页面(与任务单一样)
	@Command
	@NotifyChange("list")
	public void update(@BindingParam("model") EmActySuppilerGiftInfoModel model,@BindingParam("url") String url)
	{
		Map map=new HashMap<>();
		map.put("id", model.getGift_tarpid());
		map.put("daid", model.getGift_id());
		Window window=(Window)Executions.createComponents(url, null, map);
		window.doModal();
		list=bll.getEmActyGiftInfo("");
	}
		
	public List<EmActySuppilerGiftInfoModel> getList() {
		return list;
	}
	public void setList(List<EmActySuppilerGiftInfoModel> list) {
		this.list = list;
	}
	public List<EmActySupplierInfoModel> getSuplist() {
		return suplist;
	}
	public void setSuplist(List<EmActySupplierInfoModel> suplist) {
		this.suplist = suplist;
	}
	public List<String> getTypelist() {
		return typelist;
	}
	public void setTypelist(List<String> typelist) {
		this.typelist = typelist;
	}
	

	public boolean isVisable() {
		return visable;
	}

	public void setVisable(boolean visable) {
		this.visable = visable;
	}

	private String changedate(Date d)
	{
		String dateString="";
		SimpleDateFormat formatter = new SimpleDateFormat("yyyyMM");
		if(d!=null&&!d.equals(""))
		{
			dateString = formatter.format(d);
		}
		return dateString;
	}
}
