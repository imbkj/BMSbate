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
import org.zkoss.zul.Checkbox;
import org.zkoss.zul.Combobox;
import org.zkoss.zul.Datebox;
import org.zkoss.zul.ListModelList;
import org.zkoss.zul.Window;

import bll.EmHouse.EmHouseSetBll;
import bll.EmHouse.EmHouse_SearchBll;

import Model.CoBaseModel;
import Model.CoHousingFundModel;
import Model.EmHouseChangeModel;
import Model.EmHouseCpp;
import Model.EmbaseModel;
import Model.loginroleModel;
import R.EL;

public class EmHouse_SearchController {
	private List<EmHouseChangeModel> ownmonthlist = new ListModelList<>();
	private List<EmHouseChangeModel> addList = new ListModelList<>();
	private List<loginroleModel> clientList = new ListModelList<>();
	private List<CoBaseModel> companyList = new ListModelList<>();
	private List<CoBaseModel> singleList = new ListModelList<>();
	private List<EmbaseModel> empList = new ListModelList<>();
	private List<EmHouseCpp> ecList = new ListModelList<>();
	private List<CoHousingFundModel> jclist = new ListModelList<>();
	// private String userid = UserInfo.getUserid();

	private Integer changeNum = 0;
	private Integer finishNum = 0;
	private Integer zzNum = 0;
	private Integer zzwtNum = 0;
	private Integer singleNum = 0;
	private Integer notpassNum = 0;
	private boolean singleCheck = false;
	private boolean companyCheck = false;
	private boolean empCheck = false;
	private List<String> dateList = new ListModelList<>();

	private EmHouseChangeModel ecm = new EmHouseChangeModel();

	private SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

	private EmHouse_SearchBll bll = new EmHouse_SearchBll();
	private EmHouseSetBll sbll = new EmHouseSetBll();

	private Window win = (Window) Path.getComponent("/winSearchIndex");

	public EmHouse_SearchController() {
		ecm.setPageSize(300);
		setOwnmonthlist();
		setAddList();
		setClientList("");
		setCompanyList("");
		setSingleList("");
		setEmpList("", "");
		setEcList();
		// jclist = bll.getjcList();
		CoHousingFundModel m = new CoHousingFundModel();
		m.setCohf_bankjc("中国银行");
		jclist.add(m);
		m = new CoHousingFundModel();
		m.setCohf_bankjc("中信银行");
		jclist.add(m);
		EmHouseCpp ec = new EmHouseCpp();
		ec.setCpName("");
		ecList.add(0, ec);
		setChangeNum();
		setFinishNum();
		setZzNum();
		setZzwtNum();
		setSingleNum();
		ecm.setOwnmonth(sbll.nowmonth());
		ecm.setEmhc_ifdeclare2("未申报");
		ecm.setEmhc_single2("中智开户");
		dateList.add("");
		for (Integer i = 1; i <= 31; i++) {
			dateList.add(i.toString());
		}
	}

	@Command("updatecobase")
	@NotifyChange("companyList")
	public void updatecobase(@BindingParam("a") InputEvent event) {

		// if (!event.getValue().equals("")) {
		setCompanyList(event.getValue());
		setEmpList(event.getValue(), "");
		// }
	}

	@Command("updateSingle")
	@NotifyChange("singleList")
	public void updateSingle(@BindingParam("a") InputEvent event) {

		// if (!event.getValue().equals("")) {
		setSingleList(event.getValue());

		// }
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
		Combobox clientCB = (Combobox) win.getFellow("client");
		if (deptCB.getSelectedItem() != null) {
			clientCB.setValue("");
			setClientList(deptCB.getSelectedItem().getLabel());
		}
	}

	@Command("changeClient")
	@NotifyChange("clientList")
	public void changeClient(@BindingParam("a") InputEvent event) {
		setClientList(event.getValue());
	}

	@Command
	@NotifyChange("notpassNum")
	public void aduitNotPass() {
		Window window = (Window) Executions.createComponents(
				"EmHouse_AduitNotPass.zul", null, null);
		window.doModal();
	}

	@Command("submit")
	public void submit() {

		String company = ((Combobox) win.getFellow("company")).getValue();
		Combobox cbCompany = (Combobox) win.getFellow("singleCompany");
		String singleCompany = ((Combobox) win.getFellow("singleCompany"))
				.getValue();
		Combobox cbName = (Combobox) win.getFellow("emp");
		String pageSize = ((Combobox) win.getFellow("pageSize")).getValue();
		Datebox dbAddtime = (Datebox) win.getFellow("addtime");
		Datebox dbDeclareTime = (Datebox) win.getFellow("declareTime");

		Checkbox cbifsame = (Checkbox) win.getFellow("ifsame");
		Checkbox cbifmodify = (Checkbox) win.getFellow("ifmodify");
		Checkbox cbhouseid = (Checkbox) win.getFellow("houseid");
		Checkbox cbtodayAdd = (Checkbox) win.getFellow("todayAdd");
		Checkbox cbflag = (Checkbox) win.getFellow("flag");
		Checkbox cbifmsg = (Checkbox) win.getFellow("ifmsg");
		Checkbox cbifwrong = (Checkbox) win.getFellow("ifwrong");
		Checkbox cbifchange = (Checkbox) win.getFellow("ifchange");
		EmHouseChangeModel m = new EmHouseChangeModel();

		if (cbCompany.getSelectedItem() != null
				&& cbCompany.getSelectedItem().getValue() != null) {
			singleCompany = cbCompany.getSelectedItem().getValue();
		}

		if (!company.equals("") && companyCheck) {
			m.setEmhc_company(company);
			m.setEmhc_single2(null);
		} else if (singleCheck && !singleCompany.equals("")) {
			m.setEmhc_company(singleCompany);
			m.setEmhc_single2("独立开户");
		} else {
			if (!company.equals("")) {
				ecm.setEmhc_company(company);
				m.setEmhc_single2(null);
			} else if (!singleCompany.equals("")) {
				ecm.setEmhc_company(singleCompany);
				ecm.setEmhc_single2("独立开户");
			} else {
				ecm.setEmhc_company(null);
				m.setEmhc_single2(null);
			}
		}
		if (cbName != null && cbName.getValue() != null
				&& !cbName.getValue().equals("")) {
			if (empCheck) {
				m.setEmhc_name(cbName.getValue());
			} else {
				ecm.setEmhc_name(cbName.getValue());
			}

		}

		if ((m.getEmhc_company() != null && !m.getEmhc_company().equals(""))
				|| (m.getEmhc_name() != null && !m.getEmhc_name().equals(""))) {
			m.setPageSize(Integer.valueOf(pageSize));
			Map map = new HashMap();
			map.put("ecm", m);

			Window window = (Window) Executions.createComponents(
					"EmHouse_SearchList.zul", null, map);
			window.doModal();
		} else {

			if (cbifsame != null) {
				if (cbifsame.isChecked()) {
					ecm.setEmhc_ifsame(1);
				}
			}
			if (cbifmodify != null) {
				if (cbifmodify.isChecked()) {
					ecm.setEmhc_ifmodify(1);
				}
			}
			if (cbhouseid != null) {
				if (cbhouseid.isChecked()) {

				}
			}
			if (cbtodayAdd != null) {
				if (cbtodayAdd.isChecked()) {
					ecm.setEmhc_addtime(sdf.format(new Date()));
				}
			}
			if (cbflag != null) {
				if (cbflag.isChecked()) {

				}
			}
			if (cbifsame != null) {
				if (cbifsame.isChecked()) {
					ecm.setEmhc_ifsame(1);
				}
			}
			if (cbifmsg != null) {
				if (cbifmsg.isChecked()) {
					ecm.setEmhc_ifmsg(1);
				}
			}

			if (cbifwrong != null) {
				if (cbifwrong.isChecked()) {
					ecm.setEmhc_ifwrong(1);
				}
			}

			if (cbifchange != null) {
				if (cbifchange.isChecked()) {
					ecm.setTidchecked(true);
				}
			}

			if (((Datebox) win.getFellow("addtime")).getValue() != null) {
				ecm.setEmhc_addtime(sdf.format(((Datebox) win
						.getFellow("addtime")).getValue()));
			} else {
				ecm.setEmhc_addtime(null);
			}
			if (((Datebox) win.getFellow("declareTime")).getValue() != null) {
				ecm.setEmhc_declaretime(sdf.format(((Datebox) win
						.getFellow("declareTime")).getValue()));
			} else {
				ecm.setEmhc_declaretime(null);
			}

			if (ecm.getEmhc_single2() != null
					&& !ecm.getEmhc_single2().equals("")) {
				ecm.setEmhc_single(ecm.getEmhc_single2().equals("独立开户") ? 1 : 0);
			} else {
				ecm.setEmhc_single(null);
			}

			if (dbAddtime.getValue() != null) {
				if (!dbAddtime.getValue().equals("")) {
					ecm.setEmhc_addtime(sdf.format(dbAddtime.getValue()));
				}
			}

			if (dbDeclareTime.getValue() != null) {
				if (!dbDeclareTime.getValue().equals("")) {
					ecm.setEmhc_declaretime(sdf.format(dbDeclareTime.getValue()));
				}
			}

			if (pageSize != null && !pageSize.equals("")) {
				ecm.setPageSize(Integer.valueOf(pageSize));
			}
			Map map = new HashMap();
			map.put("ecm", ecm);

			Window window = (Window) Executions.createComponents(
					"EmHouse_SearchList.zul", null, map);
			window.doModal();
		}

	}

	@Command
	public void qSearch(@BindingParam("a") String types,
			@BindingParam("b") Integer declare, @BindingParam("c") Combobox cb) {
		EmHouseChangeModel m = new EmHouseChangeModel();
		if (types != null && !types.equals("")) {
			if (types.equals("new")) {
				m.setEmhc_change("新增");
				m.setEmhc_ifdeclare(0);
			} else if (types.equals("move")) {
				m.setEmhc_change("调入");
				m.setEmhc_ifdeclare(0);
			} else if (types.equals("stop")) {
				m.setEmhc_change("停交");
				m.setEmhc_ifdeclare(0);
			} else if (types.equals("year")) {
				m.setEmhc_change("年度调基");
				m.setEmhc_ifdeclare(0);
			}

			if (declare != null) {
				if (!declare.equals("")) {
					m.setEmhc_single(declare);
				} else {
					m.setEmhc_single(null);
				}
			} else {
				m.setEmhc_single(null);
			}

			if (cb != null) {
				if (cb.getValue() != null) {
					if (!cb.getValue().equals("")) {
						m.setEmhc_cpp2(cb.getValue());
					}
				}
			}
		}

		m.setPageSize(ecm.getPageSize());

		Map map = new HashMap();
		map.put("ecm", m);

		Window window = (Window) Executions.createComponents(
				"EmHouse_SearchList.zul", null, map);
		window.doModal();
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

	public Integer getZzwtNum() {
		return zzwtNum;
	}

	public void setZzwtNum() {
		this.zzwtNum = bll.getzzwtNum();
	}

	public Integer getSingleNum() {
		return singleNum;
	}

	public void setSingleNum() {
		this.singleNum = bll.getsingleNum();
	}

	public List<EmHouseCpp> getEcList() {
		return ecList;
	}

	public void setEcList() {
		this.ecList = bll.getcppList();
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

	public Integer getNotpassNum() {
		return notpassNum;
	}

	public void setNotpassNum(Integer notpassNum) {
		this.notpassNum = notpassNum;
	}

	public boolean isSingleCheck() {
		return singleCheck;
	}

	public void setSingleCheck(boolean singleCheck) {
		this.singleCheck = singleCheck;
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

	public List<String> getDateList() {
		return dateList;
	}

	public void setDateList(List<String> dateList) {
		this.dateList = dateList;
	}

	public List<CoHousingFundModel> getJclist() {
		return jclist;
	}

	public void setJclist(List<CoHousingFundModel> jclist) {
		this.jclist = jclist;
	}

}
