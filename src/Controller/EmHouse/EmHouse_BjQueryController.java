package Controller.EmHouse;

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
import org.zkoss.zul.Datebox;
import org.zkoss.zul.ListModelList;
import org.zkoss.zul.Textbox;
import org.zkoss.zul.Window;

import bll.EmHouse.EmHouse_QueryBll;

import Model.CoBaseModel;
import Model.CoHousingFundModel;
import Model.EmHouseBJModel;
import Model.EmHouseChangeModel;
import Model.EmbaseModel;
import Model.loginroleModel;
import Util.DateStringChange;

public class EmHouse_BjQueryController {
	private EmHouse_QueryBll bll = new EmHouse_QueryBll();
	private List<CoBaseModel> companyList = bll.getcompanylist("");
	private List<EmbaseModel> empList = bll.getembaseList("", "");
	private List<EmHouseChangeModel> ownmonthlist = bll.getownmontList();
	private List<loginroleModel> clientList = bll.getclientList("");
	private Window win = (Window) Path.getComponent("/bjwin");
	private List<CoHousingFundModel> jclist = new ListModelList<>();

	private Integer allNum = 0;
	private Integer declareNum = 0;

	String month = "";

	private boolean companyCheck = false;
	private boolean empCheck = false;

	public EmHouse_BjQueryController() {
		month = DateStringChange.Datestringnow("yyyyMM");
		allNum = bll.bjAllNum();
		declareNum = bll.bjDeclareNum();
		//jclist = bll.getjcList();
		CoHousingFundModel m = new CoHousingFundModel();
		m.setCohf_bankjc("中国银行");
		jclist.add(m);
		m = new CoHousingFundModel();
		m.setCohf_bankjc("中信银行");
		jclist.add(m);
	}

	@Command("updatecobase")
	@NotifyChange("companyList")
	public void updatecobase(@BindingParam("a") InputEvent event) {
		companyList = bll.getcompanylist(event.getValue());
		empList = bll.getembaseList(event.getValue(), "");
	}

	@Command("changeEmbase")
	@NotifyChange("empList")
	public void changeEmbase() {
		Combobox cbCobase = (Combobox) win.getFellow("company");
		empList = bll.getembaseList(cbCobase.getValue(), "");
	}

	@Command("updateEmbase")
	@NotifyChange("empList")
	public void updateEmbase(@BindingParam("a") InputEvent event) {
		Combobox cbCobase = (Combobox) win.getFellow("company");
		empList = bll.getembaseList(cbCobase.getValue(), event.getValue());

	}

	@Command
	public void submit() {
		String ownmonth = ((Combobox) win.getFellow("ownmonth")).getValue();
		String company = ((Combobox) win.getFellow("company")).getValue();
		String emp = ((Combobox) win.getFellow("emp")).getValue();
		String reason = ((Combobox) win.getFellow("reason")).getValue();
		String op = ((Combobox) win.getFellow("op")).getValue();
		String client = ((Combobox) win.getFellow("client")).getValue();
		String ifdeclare = ((Combobox) win.getFellow("ifdeclare")).getValue();
		String ifsingle = ((Combobox) win.getFellow("ifsingle")).getValue();
		Date declaretime = ((Datebox) win.getFellow("declaretime")).getValue();
		Date addtime = ((Datebox) win.getFellow("addtime")).getValue();
		String addname = ((Combobox) win.getFellow("addname")).getValue();
		String companyid = ((Textbox) win.getFellow("companyid")).getValue();
		String jc = ((Textbox) win.getFellow("jc")).getValue();
		EmHouseBJModel m = new EmHouseBJModel();
		String sql = "";
		String sql2 = "";

		if (companyCheck || empCheck) {
			if (companyCheck) {
				sql += " and (a.cid like '%" + company
						+ "%' or emhb_company like '%" + company
						+ "%' or coba_company like '%" + company
						+ "%' or coba_shortname like '%" + company + "%')";
			}
			if (empCheck) {
				sql += " and (a.gid like '%" + emp + "%' or emhb_name like '%"
						+ emp + "%')";
			}
			sql2 = sql;
		} else {

			if (company != null && !company.equals("") && company != "") {
				sql += " and (a.cid like '%" + company
						+ "%' or emhb_company like '%" + company
						+ "%' or coba_company like '%" + company
						+ "%' or coba_shortname like '%" + company + "%')";
			}

			if (emp != null && !emp.equals("") && emp != "") {
				sql += " and (a.gid like '%" + emp + "%' or emhb_name like '%"
						+ emp + "%')";
			}
			if (ownmonth != null && !ownmonth.equals("") && ownmonth != "") {
				sql = sql + " and a.ownmonth=" + ownmonth;
			}
			if (reason != null && !reason.equals("") && reason != "") {
				sql = sql + " and emhb_reason='" + reason + "'";
			}
			if (op != null && !op.equals("") && op != "") {
				sql = sql
						+ " and emhb_cpp="
						+ ((Combobox) win.getFellow("op")).getSelectedItem()
								.getValue();
			}
			if (client != null && !client.equals("") && client != "") {
				sql = sql
						+ " and a.cid in(select cid from CoBase where coba_client='"
						+ client + "')";
			}
			if (ifdeclare != null && !ifdeclare.equals("") && ifdeclare != "") {
				sql = sql
						+ " and emhb_ifdeclare="
						+ ((Combobox) win.getFellow("ifdeclare"))
								.getSelectedItem().getValue();
			}
			if (ifsingle != null && !ifsingle.equals("") && ifsingle != "") {
				if (!((Combobox) win.getFellow("ifsingle")).getSelectedItem()
						.getValue().equals("3")
						&& ((Combobox) win.getFellow("ifsingle"))
								.getSelectedItem().getValue() != "3") {
					sql = sql
							+ " and emhb_single="
							+ ((Combobox) win.getFellow("ifsingle"))
									.getSelectedItem().getValue();
				} else {
					sql = sql + " and emhb_single in(0,2)";
				}
			}
			if (declaretime != null && !declaretime.equals("")) {
				sql = sql + " and convert(varchar(10),emhb_declaretime,120)='"
						+ timechange(declaretime) + "'";
			}
			if (addtime != null && !addtime.equals("")) {
				sql = sql + " and convert(varchar(10),emhb_addtime,120)='"
						+ timechange(addtime) + "'";
			}
			if (addname != null && !addname.equals("") && addname != "") {
				sql = sql + " and emhb_addname='" + addname + "'";
			}
			if (companyid != null && !companyid.equals("") && companyid != "") {
				sql = sql + " and emhb_companyid='" + companyid + "'";
			}
			if (jc != null && !jc.equals("")) {
				sql = sql +" and e.cohf_bankjc='" + jc + "'";
			}
			sql2 = sql;
		}

		Map map = new HashMap();
		map.put("sql", sql2);

		Window window = (Window) Executions.createComponents(
				"Emhouse_BJDeclareList.zul", null, map);
		try {
			window.doModal();
		} catch (Exception e) {
		}
	}

	public List<CoBaseModel> getCompanyList() {
		return companyList;
	}

	public void setCompanyList(List<CoBaseModel> companyList) {
		this.companyList = companyList;
	}

	public List<EmbaseModel> getEmpList() {
		return empList;
	}

	public void setEmpList(List<EmbaseModel> empList) {
		this.empList = empList;
	}

	public List<EmHouseChangeModel> getOwnmonthlist() {
		return ownmonthlist;
	}

	public void setOwnmonthlist(List<EmHouseChangeModel> ownmonthlist) {
		this.ownmonthlist = ownmonthlist;
	}

	public List<loginroleModel> getClientList() {
		return clientList;
	}

	public void setClientList(List<loginroleModel> clientList) {
		this.clientList = clientList;
	}

	public String getMonth() {
		return month;
	}

	public void setMonth(String month) {
		this.month = month;
	}

	public boolean isCompanyCheck() {
		return companyCheck;
	}

	public void setCompanyCheck(boolean companyCheck) {
		this.companyCheck = companyCheck;
	}

	public boolean isEmpCheck() {
		return empCheck;
	}

	public void setEmpCheck(boolean empCheck) {
		this.empCheck = empCheck;
	}

	// 时间格式转换
	private java.sql.Date timechange(java.util.Date d) {
		java.sql.Date da = null;
		if (d != null && !d.equals("")) {
			java.sql.Date date = new java.sql.Date(d.getTime());
			da = date;
		}
		return da;
	}

	public Integer getAllNum() {
		return allNum;
	}

	public void setAllNum(Integer allNum) {
		this.allNum = allNum;
	}

	public Integer getDeclareNum() {
		return declareNum;
	}

	public void setDeclareNum(Integer declareNum) {
		this.declareNum = declareNum;
	}

	public List<CoHousingFundModel> getJclist() {
		return jclist;
	}

	public void setJclist(List<CoHousingFundModel> jclist) {
		this.jclist = jclist;
	}

}
