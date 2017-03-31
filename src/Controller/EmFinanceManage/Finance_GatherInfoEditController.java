package Controller.EmFinanceManage;

import org.zkoss.bind.annotation.BindingParam;
import org.zkoss.bind.annotation.Command;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Window;

import bll.EmFinanceManage.GatherInfoBll;

import Model.CoBaseModel;

public class Finance_GatherInfoEditController {
	private CoBaseModel m = (CoBaseModel) Executions.getCurrent().getArg()
			.get("modelold");
	private String type =Executions.getCurrent().getArg()
			.get("type").toString();
	
	
	private GatherInfoBll bll = new GatherInfoBll();
	//private CoBaseModel m = bll.getClientClass(cid);
	private String clientclass="";
	private String fuid="";
	
	
	public Finance_GatherInfoEditController()
	{
		if(m.getCoba_ufclass()!=null)
		{
			if(m.getCoba_ufclass().equals("224105"))
			{
				clientclass="AF";
			}
			else
			{
				clientclass="FS";
			}
		}
		if(type.equals("1"))//修改coba_ufid
		{
			fuid=m.getCoba_ufid();
		}
		else
		{
			fuid=m.getCoba_ufid2();
		}
	}
	
	@Command
	public void submit(@BindingParam("win") Window win)
	{
		if(clientclass!=null)
		{
			if(clientclass.equals("AF"))
			{
				m.setCoba_ufclass("224105");
			}
			else
			{
				m.setCoba_ufclass("224106");
			}
		}
		Integer k=0;
		if(type.equals("1"))//修改coba_ufid
		{
			m.setCoba_ufid(fuid);
		}
		else//修改coba_ufid2
		{
			m.setCoba_ufid2(fuid);
		}
		k=bll.updateCoba(m);
		if(k>0)
		{
			Messagebox.show("更新成功", "提示", Messagebox.OK, Messagebox.INFORMATION);
			win.detach();
		}
		else
		{
			Messagebox.show("更新成功", "提示", Messagebox.OK, Messagebox.ERROR);
		}
	}
	
	public CoBaseModel getM() {
		return m;
	}

	public void setM(CoBaseModel m) {
		this.m = m;
	}

	public String getClientclass() {
		return clientclass;
	}

	public void setClientclass(String clientclass) {
		this.clientclass = clientclass;
	}
	
	public String getFuid() {
		return fuid;
	}

	public void setFuid(String fuid) {
		this.fuid = fuid;
	}
	
}
