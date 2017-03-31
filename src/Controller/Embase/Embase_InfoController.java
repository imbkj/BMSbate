package Controller.Embase;

import org.zkoss.zk.ui.Executions;

import bll.Embase.EmbaseListBll;

import Model.EmbaseModel;

public class Embase_InfoController {
	private String gid =Executions.getCurrent().getArg().get("gid")+"";
	private String cid = Executions.getCurrent().getArg().get("cid")+"";
	private EmbaseListBll bll=new EmbaseListBll();
	private EmbaseModel model=bll.getEmbaseInfo(" and a.cid="+cid+" and gid="+gid);
	
	public EmbaseModel getModel() {
		return model;
	}
	public void setModel(EmbaseModel model) {
		this.model = model;
	}
	
	
	

}
