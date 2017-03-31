package Controller.EmHouse;

import java.text.SimpleDateFormat;
import java.util.Date;
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
import bll.EmHouse.Emhouse_BjBll;
import Model.CoBaseModel;
import Model.EmHouseBJModel;
import Model.EmHouseCpp;
import Model.EmbaseModel;
import Model.PubCodeConversionModel;
import Model.loginroleModel;

public class EmHouse_BjSearchController {
	private List<EmHouseBJModel> ownmonthlist = new ListModelList<>();
	private List<PubCodeConversionModel> reasonList = new ListModelList<>();
	private List<EmHouseCpp> cppList = new ListModelList<>();
	private List<EmHouseBJModel> addList = new ListModelList<>();
	private List<loginroleModel> clientList = new ListModelList<>();
	private List<CoBaseModel> companyList = new ListModelList<>();
	private List<EmbaseModel> empList = new ListModelList<>();

	private Integer changeNum = 0;
	private Integer finishNum = 0;
	private Integer zzNum = 0;
	private Integer singleNum = 0;
	private Emhouse_BjBll bll = new Emhouse_BjBll();

	private Window win = (Window) Path.getComponent("/winbjSearchIndex");

	private EmHouseBJModel ebjm = new EmHouseBJModel();
	private SimpleDateFormat sdf = new SimpleDateFormat("yyyyMM");

	public EmHouse_BjSearchController() {
		Date d = new Date();
		ebjm.setOwnmonth(Integer.valueOf(sdf.format(d)));
		setOwnmonthlist();
		setAddList();
		setClientList("");
		setCompanyList("");
		setEmpList("", "");
		setChangeNum();
		setFinishNum();
		setZzNum();
		setSingleNum();
		setReasonList();
		setCppList();
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
		setEmpList(cbCobase.getValue(), "");

	}

	@Command("updateEmbase")
	@NotifyChange("empList")
	public void updateEmbase(@BindingParam("a") InputEvent event) {
		Combobox cbCobase = (Combobox) win.getFellow("company");
		setEmpList(cbCobase.getValue(), event.getValue());

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

	@Command("submit")
	public void submit() {
		String ownmonth = ((Combobox) win.getFellow("ownmonth")).getValue();
		String company = ((Combobox) win.getFellow("company")).getValue();
		String emp = ((Combobox) win.getFellow("emp")).getValue();
		String reason = ((Combobox) win.getFellow("reason")).getValue();
		String dept = ((Combobox) win.getFellow("dept")).getValue();
		String client = ((Combobox) win.getFellow("client")).getValue();

		String ifdeclare = null;
		Combobox cbdeclare = (Combobox) win.getFellow("ifdeclare");
		if (cbdeclare.getSelectedItem() != null) {
			ifdeclare = cbdeclare.getSelectedItem().getValue();
			ebjm.setEmhb_ifdeclare(Integer.valueOf(ifdeclare));
		}

		Combobox cbsingle = (Combobox) win.getFellow("ifsingle");
		String ifsingle = null;
		if (cbsingle.getSelectedItem() != null) {
			ifsingle = cbsingle.getSelectedItem().getValue();
			ebjm.setEmhb_single(Integer.valueOf(ifsingle));
		}

		String cpp = null;
		Combobox cbcpp = (Combobox) win.getFellow("cpp");
		if (cbcpp.getSelectedItem() != null) {
			cpp = cbcpp.getSelectedItem().getValue().toString();
			ebjm.setEmhb_cpp(Double.valueOf(cpp));
		}
		Map map = new HashMap();
		if (ownmonth != null && !ownmonth.equals("")) {
			ebjm.setOwnmonth(Integer.valueOf(ownmonth));
		}

		ebjm.setEmhb_company(company);
		ebjm.setEmhb_name(emp);
		ebjm.setEmhb_reason(reason);
		ebjm.setDept(dept);
		ebjm.setClient(client);
		map.put("ebjm", ebjm);

		Window window = (Window) Executions.createComponents(
				"EmHouse_BjSearchList.zul", null, map);
		window.doModal();
		// win.detach();
	}

	public List<EmHouseBJModel> getOwnmonthlist() {
		return ownmonthlist;
	}

	public void setOwnmonthlist() {
		this.ownmonthlist = bll.getownmontList();
	}

	public List<EmHouseBJModel> getAddList() {
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

	public Integer getSingleNum() {
		return singleNum;
	}

	public void setSingleNum() {
		this.singleNum = bll.getsingleNum();
	}

	public EmHouseBJModel getEbjm() {
		return ebjm;
	}

	public void setEbjm(EmHouseBJModel ebjm) {
		this.ebjm = ebjm;
	}

	public List<PubCodeConversionModel> getReasonList() {
		return reasonList;
	}

	public void setReasonList() {
		this.reasonList = bll.getreasonList();
	}

	public List<EmHouseCpp> getCppList() {
		return cppList;
	}

	public void setCppList() {
		for (double i = 5; i < 21; i++) {
			EmHouseCpp ec = new EmHouseCpp();
			ec.setCp(i / 100);
			ec.setOp(i / 100);
			ec.setCpName((int) i + "%");
			cppList.add(ec);
		}
		this.cppList = cppList;
	}

}
