package Controller.EmHouse;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.zkoss.bind.annotation.BindingParam;
import org.zkoss.bind.annotation.Command;
import org.zkoss.bind.annotation.NotifyChange;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zul.ListModelList;
import org.zkoss.zul.Window;

import Model.CoHousingFundModel;
import Model.EmHouseChangeModel;
import Model.EmHouseErrModel;
import Model.EmbaseModel;
import Model.LoginModel;
import bll.EmHouse.EmHouseSetBll;
import bll.EmHouse.EmHouse_EditBll;
import bll.EmHouse.EmHouse_FinanceBll;

public class EmHouseErrController {

	private List<EmHouseErrModel> list;
	private List<EmHouseErrModel> ownmonthList;
	private List<EmHouseErrModel> errList;
	private List<EmHouseErrModel> clientList;
	private List<EmHouseErrModel> companyList;
	private List<EmHouseErrModel> tsdateList;

	private EmHouse_FinanceBll bll = new EmHouse_FinanceBll();
	private EmHouseSetBll sbll = new EmHouseSetBll();

	private EmHouseErrModel eem = new EmHouseErrModel();

	public EmHouseErrController() {
		eem.setOwnmonth(sbll.nowmonth());
		list = bll.getEmhouseErrList(eem.getOwnmonth(), null, null, null, null,
				null, null, null, null, false, null);
		ownmonthList = bll.getEmhouseErrList(null, null, null, null, null,
				null, null, null, null, true, "a.ownmonth");
		errList = bll.getEmhouseErrList(eem.getOwnmonth(), null, null, null,
				null, null, null, null, null, true, "emhe_err");
		clientList = bll.getEmhouseErrList(eem.getOwnmonth(), null, null, null,
				null, null, null, null, null, true, "coba_client");
		companyList = bll.getEmhouseErrList(eem.getOwnmonth(), null, null,
				null, null, null, null, null, null, true, "coba_shortname");
		tsdateList = bll.getEmhouseErrList(eem.getOwnmonth(), null, null, null,
				null, null, null, null, null, true, "cohf_tsday");
	}

	@Command
	public void info(@BindingParam("a") EmHouseErrModel em) {
		Map map = new HashMap();
		map.put("m", 0);
		map.put("model", em);
		Window window = (Window) Executions.createComponents(
				"EmHouseErrInfo.zul", null, map);
		window.doModal();
	}

	@Command
	public void export() {
		bll.exportErrExcel(list);
	}

	@Command
	@NotifyChange({ "list", "clientList", "companyList", "errList",
			"tsdateList" })
	public void searchList() {
		if (eem.getSingle() != null && !eem.getSingle().equals("")) {
			eem.setEmhe_single(eem.getSingle().equals("中智大户") ? 0 : 1);
		} else {
			eem.setEmhe_single(null);
		}

		list = bll.getEmhouseErrList(eem.getOwnmonth(), eem.getEmhe_single(),
				eem.getEmhe_err(), eem.getCoba_client(),
				eem.getCoba_shortname(), eem.getEmhe_name(),
				eem.getEmhe_idcard(), eem.getEmhe_houseid(),
				eem.getCohf_tsday(), false, null);
		errList = bll.getEmhouseErrList(eem.getOwnmonth(),
				eem.getEmhe_single(), null, eem.getCoba_client(),
				eem.getCoba_shortname(), eem.getEmhe_name(),
				eem.getEmhe_idcard(), eem.getEmhe_houseid(),
				eem.getCohf_tsday(), true, "emhe_err");
		clientList = bll.getEmhouseErrList(eem.getOwnmonth(),
				eem.getEmhe_single(), eem.getEmhe_err(), null,
				eem.getCoba_shortname(), eem.getEmhe_name(),
				eem.getEmhe_idcard(), eem.getEmhe_houseid(),
				eem.getCohf_tsday(), true, "coba_client");
		companyList = bll.getEmhouseErrList(eem.getOwnmonth(),
				eem.getEmhe_single(), eem.getEmhe_err(), eem.getCoba_client(),
				null, eem.getEmhe_name(), eem.getEmhe_idcard(),
				eem.getEmhe_houseid(), eem.getCohf_tsday(), true,
				"coba_shortname");
		tsdateList = bll.getEmhouseErrList(eem.getOwnmonth(),
				eem.getEmhe_single(), eem.getEmhe_err(), eem.getCoba_client(),
				null, eem.getEmhe_name(), eem.getEmhe_idcard(),
				eem.getEmhe_houseid(), eem.getCohf_tsday(), true, "cohf_tsday");
	}

	@Command
	public void checkerr(@BindingParam("a") EmHouseErrModel em,
			@BindingParam("b") Integer type) {
		Map map = new HashMap<>();
		map.put("type", "住房公积金核对");// 业务类型:来自WfClass的wfcl_name
		map.put("id", em.getId());// 业务表id
		map.put("tablename", "EmHouseErr");// 业务表名
		map.put("title", em.getCoba_company() + "," + em.getEmhe_name());

		map.put("clazz", new EmHouse_EditBll());
		map.put("method", "updateErrData");
		map.put("pclass", EmHouseErrModel.class);
		em.setType(type);
		map.put("parameter", em);
		map.put("checkName", type.equals(1)?"核查失败":"汇缴失败");

		EmbaseModel emp = new EmbaseModel();
		emp.setCoba_company(em.getCoba_company());
		emp.setCid(em.getCid());
		emp.setGid(em.getGid());
		emp.setEmba_name(em.getEmhe_name());
		map.put("embase", emp);

		List<LoginModel> mlist = new ArrayList<LoginModel>();
		LoginModel m = new LoginModel();
		m.setLog_name(em.getCoba_client());

		// 收件人姓名和收件人id至少要填一个
		mlist.add(m);
		map.put("list", mlist);// 默认收件人list
		Window window = (Window) Executions.createComponents(
				"../SysMessage/Message_Add.zul", null, map);
		window.doModal();
	}

	public List<EmHouseErrModel> getList() {
		return list;
	}

	public void setList(List<EmHouseErrModel> list) {
		this.list = list;
	}

	public List<EmHouseErrModel> getOwnmonthList() {
		return ownmonthList;
	}

	public void setOwnmonthList(List<EmHouseErrModel> ownmonthList) {
		this.ownmonthList = ownmonthList;
	}

	public List<EmHouseErrModel> getErrList() {
		return errList;
	}

	public void setErrList(List<EmHouseErrModel> errList) {
		this.errList = errList;
	}

	public List<EmHouseErrModel> getClientList() {
		return clientList;
	}

	public void setClientList(List<EmHouseErrModel> clientList) {
		this.clientList = clientList;
	}

	public List<EmHouseErrModel> getCompanyList() {
		return companyList;
	}

	public void setCompanyList(List<EmHouseErrModel> companyList) {
		this.companyList = companyList;
	}

	public EmHouseErrModel getEem() {
		return eem;
	}

	public void setEem(EmHouseErrModel eem) {
		this.eem = eem;
	}

	public List<EmHouseErrModel> getTsdateList() {
		return tsdateList;
	}

	public void setTsdateList(List<EmHouseErrModel> tsdateList) {
		this.tsdateList = tsdateList;
	}

}
