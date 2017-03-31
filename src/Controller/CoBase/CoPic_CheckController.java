package Controller.CoBase;

import java.sql.SQLException;
import java.util.List;

import org.zkoss.bind.annotation.BindingParam;
import org.zkoss.bind.annotation.Command;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zul.ListModelList;

import Model.EmPicModel;
import bll.Embase.EmPic_CheckBll;

public class CoPic_CheckController {
	private List<EmPicModel> copiclist = new ListModelList<EmPicModel>();
	EmPic_CheckBll bll = new EmPic_CheckBll();
	String cid = "";

	public CoPic_CheckController() throws SQLException {
		cid = Executions.getCurrent().getArg().get("cid").toString();

		copiclist = new ListModelList<EmPicModel>(bll.getpiclist(cid));// 获取短信内容
	}
	
	@Command("openurl")
	public void openurl(@BindingParam("a") EmPicModel em) {
		Executions.sendRedirect("http://192.168.1.8/"+em.getEmpic_url()); 
		
		System.out.println("http://192.168.1.8/"+em.getEmpic_url());
	}

	public List<EmPicModel> getCopiclist() {
		return copiclist;
	}

	public void setCopiclist(List<EmPicModel> copiclist) {
		this.copiclist = copiclist;
	}

}
