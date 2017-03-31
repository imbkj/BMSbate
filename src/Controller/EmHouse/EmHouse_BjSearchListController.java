package Controller.EmHouse;

import java.util.List;

import org.zkoss.bind.annotation.BindingParam;
import org.zkoss.bind.annotation.Command;
import org.zkoss.bind.annotation.NotifyChange;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.event.InputEvent;
import org.zkoss.zul.ListModelList;

import bll.EmHouse.EmHouse_BjSearchBll;

import Model.EmHouseBJModel;
import Util.FileOperate;

public class EmHouse_BjSearchListController {
	private EmHouseBJModel ebm = (EmHouseBJModel) Executions.getCurrent()
			.getArg().get("ebjm");
	private EmHouseBJModel ejm = new EmHouseBJModel();
	private List<EmHouseBJModel> list = new ListModelList<>();
	private List<EmHouseBJModel> ownmonthList = new ListModelList<>();
	private List<EmHouseBJModel> feemonthList = new ListModelList<>();
	private List<EmHouseBJModel> companyList = new ListModelList<>();
	private List<EmHouseBJModel> empList = new ListModelList<>();

	private EmHouse_BjSearchBll bll = new EmHouse_BjSearchBll();
	private boolean searchType=false;

	public EmHouse_BjSearchListController() {
		setList(ebm);
		setCompanyList(ejm);
		setEmpList(ejm);
		setOwnmonthList(ejm);
		setFeemonthList(ejm);
	}

	@Command
	@NotifyChange("list")
	public void search() {
		setList(ejm);
		searchType=true;
	}

	@Command
	public void export() {
		String str = bll.export(searchType?ejm:ebm);
		FileOperate.download("OfficeFile/DownLoad/EmHouse/" + str
				+ ".xls");
	}
	
	@Command
	@NotifyChange("companyList")
	public void updateCompany(@BindingParam("a") InputEvent event){
		if (!event.getValue().equals("")) {
			EmHouseBJModel em = new EmHouseBJModel();
			em.setEmhb_company(event.getValue());
			setCompanyList(em);
			
		}
	}
	
	@Command
	@NotifyChange("empList")
	public void updateEMP(@BindingParam("a") InputEvent event){
		if (!event.getValue().equals("")) {
			EmHouseBJModel em = new EmHouseBJModel();
			em.setEmhb_name(event.getValue());
			setEmpList(em);
			
		}
	}

	public EmHouseBJModel getEbm() {
		return ebm;
	}

	public void setEbm(EmHouseBJModel ebm) {
		this.ebm = ebm;
	}

	public List<EmHouseBJModel> getList() {
		return list;
	}

	public void setList(EmHouseBJModel em) {
		this.list = bll.houselist(em);
	}

	public List<EmHouseBJModel> getOwnmonthList() {
		return ownmonthList;
	}

	public void setOwnmonthList(EmHouseBJModel em) {
		this.ownmonthList = bll.combolist(em, "ownmonth");
	}

	public List<EmHouseBJModel> getFeemonthList() {
		return feemonthList;
	}

	public void setFeemonthList(EmHouseBJModel em) {
		this.feemonthList = bll.combolist(em, "feemonth");
	}

	public List<EmHouseBJModel> getCompanyList() {
		return companyList;
	}

	public void setCompanyList(EmHouseBJModel em) {
		this.companyList = bll.combolist(em, "company");
	}

	public List<EmHouseBJModel> getEmpList() {
		return empList;
	}

	public void setEmpList(EmHouseBJModel em) {
		this.empList = bll.combolist(em, "emp");
	}

	public EmHouseBJModel getEjm() {
		return ejm;
	}

	public void setEjm(EmHouseBJModel ejm) {
		this.ejm = ejm;
	}

}
