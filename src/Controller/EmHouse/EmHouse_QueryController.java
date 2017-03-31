package Controller.EmHouse;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.zkoss.bind.annotation.BindingParam;
import org.zkoss.bind.annotation.Command;
import org.zkoss.bind.annotation.NotifyChange;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.Path;
import org.zkoss.zk.ui.event.InputEvent;
import org.zkoss.zul.Combobox;
import org.zkoss.zul.ListModelList;
import org.zkoss.zul.Window;

import bll.EmHouse.EmHouse_QueryBll;

import Model.CoBaseModel;
import Model.EmHouseChangeModel;
import Model.EmbaseModel;
import Model.loginroleModel;
import Util.UserInfo;

public class EmHouse_QueryController {
	private List<EmHouseChangeModel> ownmonthlist = new ListModelList<>();
	private List<EmHouseChangeModel> addList = new ListModelList<>();
	private List<loginroleModel> clientList = new ListModelList<>();
	private List<CoBaseModel> companyList = new ListModelList<>();
	private List<EmbaseModel> empList = new ListModelList<>();
	private String userid = UserInfo.getUserid();
	private List<CoBaseModel> singleList = new ListModelList<>();
	private Integer changeNum = 0;
	private Integer finishNum = 0;
	private Integer zzNum = 0;
	// private Integer zzwtNum = 0;
	private Integer singleNum = 0;
	private EmHouseChangeModel ecm = new EmHouseChangeModel();

	private EmHouse_QueryBll bll = new EmHouse_QueryBll();

	private Window win = (Window) Path.getComponent("/winSearchIndex");

	public EmHouse_QueryController() {
		ecm.setPageSize(300);
		setOwnmonthlist();
		setAddList();
		setClientList("");
		setCompanyList("");
		setEmpList("", "");
		setChangeNum();
		setFinishNum();
		setZzNum();
		setSingleNum();
		setSingleList("");
		ecm.setOwnmonth(ownmonthlist.get(1).getOwnmonth());
		ecm.setEmhc_ifdeclare2("未申报");
		ecm.setEmhc_single2("中智开户");

	}

	@Command("updatecobase")
	@NotifyChange("companyList")
	public void updatecobase(@BindingParam("a") InputEvent event) {

		if (!event.getValue().equals("")) {
			setCompanyList(event.getValue());
			setEmpList(event.getValue(), "");
		}
	}

	@Command("changeEmbase")
	@NotifyChange("empList")
	public void changeEmbase() {
		Combobox cbCobase = (Combobox) win.getFellow("company");
		if (cbCobase.getSelectedItem() != null) {
			setEmpList(cbCobase.getSelectedItem().getValue().toString(), "");
		}
	}

	@Command("updateEmbase")
	@NotifyChange("empList")
	public void updateEmbase(@BindingParam("a") InputEvent event) {
		Combobox cbCobase = (Combobox) win.getFellow("company");
		String cid = "";

		if (cbCobase.getSelectedItem() != null) {
			cid = cbCobase.getSelectedItem().getValue().toString();
		} else {
			cid = cbCobase.getValue();
		}
		setEmpList(cid, event.getValue());

	}

	@Command("updateclient")
	@NotifyChange("clientList")
	public void updateclient() {
		Combobox deptCB = (Combobox) win.getFellow("dept");
		if (deptCB.getSelectedItem() != null) {
			setClientList(deptCB.getSelectedItem().getLabel());
		}
	}

	@Command("changeClient")
	@NotifyChange("clientList")
	public void changeClient(@BindingParam("a") InputEvent event) {
		setClientList(event.getValue());
	}

	@Command("updateSingle")
	@NotifyChange("singleList")
	public void updateSingle(@BindingParam("a") InputEvent ie) {
		setSingleList(ie.getValue());

	}

	@Command("submit")
	public void submit() {
		Combobox cbcompany = ((Combobox) win.getFellow("company"));
		Combobox cbsingleCompany = ((Combobox) win.getFellow("singleCompany"));
		Combobox cbname = (Combobox) win.getFellow("emp");
		
		if (cbsingleCompany.getSelectedItem()!=null) {
			ecm.setEmhc_company(cbsingleCompany.getSelectedItem().getValue().toString());
		}else if (cbsingleCompany.getValue()!=null && !cbsingleCompany.getValue().equals("")) {
			ecm.setEmhc_company(cbsingleCompany.getValue());
		}else if (cbcompany.getSelectedItem()!=null) {
			ecm.setEmhc_company(cbcompany.getSelectedItem().getValue().toString());
		}else if (cbcompany.getValue()!=null && !cbcompany.getValue().equals("")) {
			ecm.setEmhc_company(cbcompany.getValue());
		}
		
		if (cbname.getSelectedItem()!=null) {
			ecm.setEmhc_name(cbname.getSelectedItem().getValue().toString());
		}else if (cbname.getValue()!=null && !cbname.getValue().equals("")) {
			ecm.setEmhc_name(cbname.getValue());
		}
		
		
		if (ecm.getEmhc_single2() != null && !ecm.getEmhc_single2().equals("")) {
			ecm.setEmhc_single(ecm.getEmhc_single2().equals("独立开户") ? 1 : 0);
		}


		Map map = new HashMap();
		map.put("ecm", ecm);
		Window window = (Window) Executions.createComponents(
				"EmHouse_QueryList.zul", null, map);
		window.doModal();
		// win.detach();
	}

	public List<EmHouseChangeModel> getOwnmonthlist() {
		return ownmonthlist;
	}

	public void setOwnmonthlist() {
		this.ownmonthlist = bll.getownmontList();
	}

	public List<EmHouseChangeModel> getAddList() {
		return addList;
	}

	public void setAddList() {
		this.addList = bll.getaddnamelist();
	}

	public List<loginroleModel> getClientList() {
		return clientList;
	}

	public void setClientList(String dept) {
		this.clientList = bll.getclientList(dept);
	}

	public List<CoBaseModel> getCompanyList() {
		return companyList;
	}

	public void setCompanyList(String name) {
		this.companyList = bll.getcompanylist(name);
	}

	public List<EmbaseModel> getEmpList() {
		return empList;
	}

	public void setEmpList(String cid, String name) {
		this.empList = bll.getembaseList(cid, name);
	}

	public Integer getChangeNum() {
		return changeNum;
	}

	public void setChangeNum() {
		this.changeNum = bll.getchangeNum();
	}

	public Integer getFinishNum() {
		return finishNum;
	}

	public void setFinishNum() {
		this.finishNum = bll.getfinishNum();
	}

	public Integer getZzNum() {
		return zzNum;
	}

	public void setZzNum() {
		this.zzNum = bll.getzzNum();
	}

	/*
	 * public Integer getZzwtNum() { return zzwtNum; }
	 * 
	 * public void setZzwtNum() { this.zzwtNum = bll.getzzwtNum(); }
	 */
	public Integer getSingleNum() {
		return singleNum;
	}

	public void setSingleNum() {
		this.singleNum = bll.getsingleNum();
	}

	public List<CoBaseModel> getSingleList() {
		return singleList;
	}

	public void setSingleList(String name) {
		this.singleList = bll.getsingleList(name);
	}

	public EmHouseChangeModel getEcm() {
		return ecm;
	}

	public void setEcm(EmHouseChangeModel ecm) {
		this.ecm = ecm;
	}

}
