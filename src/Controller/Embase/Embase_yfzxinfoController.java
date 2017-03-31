package Controller.Embase;

import java.util.List;

import org.zkoss.jsp.zul.WindowTag;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.select.SelectorComposer;
import org.zkoss.zk.ui.select.annotation.Wire;

import bll.Embase.EmbaseListBll;

import Model.EmbaseModel;



public class Embase_yfzxinfoController  extends SelectorComposer<Component> {
	
	//int  gid =Integer.parseInt(Executions.getCurrent().getArg().get("gid").toString());
	
	private int gid;
	private EmbaseListBll bll=new EmbaseListBll();
	
	private EmbaseModel m =new EmbaseModel();
	
	@Wire
	private org.zkoss.zul.Window yfwin;
	

	@Override
	public void doAfterCompose(Component comp) throws Exception {
		// TODO Auto-generated method stub
		super.doAfterCompose(comp);
		yfwin.setTitle(m.getCoba_company()+"--"+m.getEmba_name());
		
	}

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * @param args
	 */
	 public Embase_yfzxinfoController()
	 {
		 
			try
			{
			  gid =Integer.parseInt(Executions.getCurrent().getParameter("gid").toString());
			}
			catch(Exception e)
			{
			gid =Integer.parseInt(Executions.getCurrent().getArg().get("gid").toString());
			}
		
		m  =bll.getEmbaseInfo("  and gid="+gid);
		 System.out.println(m.getCid());
		
	 }
	 
	 

	public EmbaseModel getM() {
		return m;
	}

	public void setM(EmbaseModel m) {
		this.m = m;
	}
	 
	 

}
