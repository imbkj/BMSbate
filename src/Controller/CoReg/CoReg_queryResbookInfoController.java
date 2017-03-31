package Controller.CoReg;

import java.util.List;

import org.zkoss.bind.annotation.BindingParam;
import org.zkoss.bind.annotation.Command;
import org.zkoss.poi.util.Beta;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zul.ListModelList;
import org.zkoss.zul.Window;

import bll.CoReg.CoReg_ListBll;

import Model.CoOnlineRegisterInfoModel;
import Model.ResponsibilityBookModel;

public class CoReg_queryResbookInfoController {

	private CoOnlineRegisterInfoModel com = new CoOnlineRegisterInfoModel();
	private ResponsibilityBookModel m = (ResponsibilityBookModel) Executions
			.getCurrent().getArg().get("m");
	private CoReg_ListBll bll = new CoReg_ListBll();
	private List<CoOnlineRegisterInfoModel> hosList = new ListModelList<>();

	public CoReg_queryResbookInfoController() {
		com = bll.getCoregInfo(m.getRebk_cori_id());
		hosList = bll.getStateRelList(" and typename='后道状态'", m.getRebk_cori_id());
	}
	
	@Command
	public void createwin(@BindingParam("win") Window win)
	{
		if(com!=null&&com.getCoba_company()!=null&&!com.getCoba_company().equals(""))
		{
			win.setTitle(com.getCoba_company());
		}
		else
		{
			win.setTitle("计划责任书详细信息");
		}
	}

	
	public List<CoOnlineRegisterInfoModel> getHosList() {
		return hosList;
	}


	public void setHosList(List<CoOnlineRegisterInfoModel> hosList) {
		this.hosList = hosList;
	}


	public ResponsibilityBookModel getM() {
		return m;
	}


	public void setM(ResponsibilityBookModel m) {
		this.m = m;
	}


	public CoOnlineRegisterInfoModel getCom() {
		return com;
	}

	public void setCom(CoOnlineRegisterInfoModel com) {
		this.com = com;
	}

}
