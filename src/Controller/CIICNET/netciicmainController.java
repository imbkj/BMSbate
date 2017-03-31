package Controller.CIICNET;

 
import java.util.List;

import org.zkoss.jsp.zul.WindowTag;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.select.SelectorComposer;
import org.zkoss.zk.ui.select.annotation.Wire;

import bll.CIICNET.createuserbll;

import Model.EmbaseModel;



public class netciicmainController  extends SelectorComposer<Component> {
	
	//int  gid =Integer.parseInt(Executions.getCurrent().getArg().get("gid").toString());
	
	private int gid=0;
	private int emba_id=0;
	private createuserbll bll=new createuserbll();
	
	private EmbaseModel m =new EmbaseModel();
	
	@Wire
	private org.zkoss.zul.Window netmainwin;
	

	@Override
	public void doAfterCompose(Component comp) throws Exception {
		// TODO Auto-generated method stub
		super.doAfterCompose(comp);
		netmainwin.setTitle(m.getCoba_company()+"--"+m.getEmba_name());
		
	}

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * @param args
	 */
	 public netciicmainController()
	 {
		 
 		  gid = 155640;
 		  emba_id =187724;
//	
//			try
//			{
//			  gid =Integer.parseInt(Executions.getCurrent().getParameter("gid").toString());
//			  emba_id =Integer.parseInt(Executions.getCurrent().getParameter("emba_id").toString());
//			}
//			catch(Exception e)
//			{
			gid =Integer.parseInt(Executions.getCurrent().getArg().get("gid").toString());
			emba_id =Integer.parseInt(Executions.getCurrent().getArg().get("emba_id").toString());
//			}
//		
		m  =bll.getembasemodel(gid).get(0);
		//m.getEmba_id();
		 m.setEmba_id(emba_id);
		 m.setGid(gid);
		 
		 System.out.println(m.getCid());
		
	 }
	 
	 

	public EmbaseModel getM() {
		return m;
	}

	public void setM(EmbaseModel m) {
		this.m = m;
	}
	 
	 

}
