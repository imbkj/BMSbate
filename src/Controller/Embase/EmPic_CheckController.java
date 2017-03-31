package Controller.Embase;

import java.sql.SQLException;
import java.util.List;

import org.zkoss.bind.annotation.BindingParam;
import org.zkoss.bind.annotation.Command;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zul.ListModelList;

import Model.EmPicModel;
import bll.Embase.EmPic_CheckBll;

public class EmPic_CheckController {
	private List<EmPicModel> empiclist = new ListModelList<EmPicModel>();
	EmPic_CheckBll bll = new EmPic_CheckBll();
	String gid = "";

	public EmPic_CheckController() throws SQLException {
		gid = Executions.getCurrent().getArg().get("gid").toString();

		empiclist = new ListModelList<EmPicModel>(bll.getpiclist(gid));// 获取短信内容
	}
	 
	@Command("openurl")
	public void openurl(@BindingParam("a") EmPicModel em) {
		Executions.sendRedirect(em.getEmpic_url()); 
		
		System.out.println("http://192.168.1.166/"+em.getEmpic_url());
	}

	public List<EmPicModel> getEmpiclist() {
		return empiclist;
	}

	public void setEmpiclist(List<EmPicModel> empiclist) {
		this.empiclist = empiclist;
	}

}
