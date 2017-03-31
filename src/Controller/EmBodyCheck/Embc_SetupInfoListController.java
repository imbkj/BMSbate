package Controller.EmBodyCheck;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.zkoss.bind.annotation.BindingParam;
import org.zkoss.bind.annotation.Command;
import org.zkoss.bind.annotation.NotifyChange;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Window;

import Model.EmBcSetupModel;
import Util.UserInfo;
import bll.EmBodyCheck.Embc_SetupSellectBll;

public class Embc_SetupInfoListController {
	Embc_SetupSellectBll bll=new Embc_SetupSellectBll();
	private List<EmBcSetupModel> setuplist=bll.getEmBcSetupInfo("");
	
	//体检机构新增
	@Command
	@NotifyChange("setuplist")
	public void embcsetupadd(@BindingParam("setup") String setup,@BindingParam("linkname") String linkname,
			@BindingParam("phone") String phone,@BindingParam("begindate") Date begindate,
			@BindingParam("enddate") Date enddate,@BindingParam("states") String states){

		String sql="";
		if(begindate!=null&&!begindate.equals(""))
		{
			sql=sql+" and ebcs_inuredate='"+timechange(begindate)+"'";
		}
		if(enddate!=null&&!enddate.equals(""))
		{
			sql=sql+" and ebcs_indate='"+timechange(enddate)+"'";
		}
		if(setup!=null&&!setup.equals("")&&setup!="")
		{
			sql=sql+" and ebcs_hospital like'%"+setup+"%'";
		}
		if(linkname!=null&&!linkname.equals("")&&linkname!="")
		{
			sql=sql+" and ebcs_name='"+linkname+"'";
		}
		if(phone!=null&&!phone.equals("")&&phone!="")
		{
			sql=sql+" and ebcs_phone='"+phone+"'";
		}
		if(states!=null&&!states.equals("")&&states!=""&&!states.equals("-1")&&states!="-1")
		{
			sql=sql+" and ebcs_state="+states;
		}
		setuplist=bll.getEmBcSetupInfo(sql);
	}
	
	//机构详细信息
	@Command
	@NotifyChange("setuplist")
	public void openZUL(@BindingParam("model") EmBcSetupModel model,@BindingParam("url") String url)
	{
		Map map=new HashMap<>();
		map.put("model",model);
		Window window=(Window)Executions.createComponents(url, null, map);
		window.doModal();
		setuplist=bll.getEmBcSetupInfo(" and ebcs_id="+model.getEbcs_id());
	}
	
	//时间格式转换
	private java.sql.Date timechange(java.util.Date d)
	{
		java.sql.Date da=null;
		if(d!=null&&!d.equals(""))
		{
			java.sql.Date date=new java.sql.Date(d.getTime());
			da=date;
		}
		return da;
	}
	public List<EmBcSetupModel> getSetuplist() {
		return setuplist;
	}
	public void setSetuplist(List<EmBcSetupModel> setuplist) {
		this.setuplist = setuplist;
	}	
}
