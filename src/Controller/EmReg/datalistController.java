package Controller.EmReg;

import org.zkoss.bind.annotation.BindingParam;
import org.zkoss.bind.annotation.GlobalCommand;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.Path;
import org.zkoss.zul.Grid;
import org.zkoss.zul.Window;

import Controller.DocumentsInfo.DocumentsInfo_OperationController;

public class datalistController {
	private Window win = (Window) Path.getComponent("/datawin");
	private String typeid = Executions.getCurrent().getParameter("typeid").toString();
	@GlobalCommand("adddata")
	public void adddata(@BindingParam("id") Integer id)
	{
		if(id!=null)
		{
			DocumentsInfo_OperationController docOC = new DocumentsInfo_OperationController();
			Grid gd=(Grid) win.getFellow("docGrid");
			try {
				if(typeid!=null&&typeid.equals("1"))
				{
					docOC.AddsubmitDoc(gd,id.toString());
				}
				else if(typeid!=null&&typeid.equals("2"))
				{
					docOC.UpsubmitDoc(gd, id.toString());
				}
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
	}
}
