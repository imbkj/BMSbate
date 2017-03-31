package Controller.Archives;

import org.zkoss.zk.ui.Executions;

public class Archive_ErrorInfoController {
	private String errorinfo=Executions.getCurrent().getParameter("errorinfo").toString();

	public String getErrorinfo() {
		return errorinfo;
	}

	public void setErrorinfo(String errorinfo) {
		this.errorinfo = errorinfo;
	}
	
}
