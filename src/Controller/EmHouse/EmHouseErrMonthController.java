package Controller.EmHouse;

import java.util.List;

import org.zkoss.bind.annotation.Command;
import org.zkoss.bind.annotation.NotifyChange;
import org.zkoss.zul.ListModelList;

import Model.CoHousingFundModel;
import Model.EmHouseErrMonthModel;
import bll.EmHouse.EmHouseSetBll;
import bll.EmHouse.EmHouse_FinanceBll;

public class EmHouseErrMonthController {

	private List<EmHouseErrMonthModel> list;
	private List<EmHouseErrMonthModel> ownmonthList;
	private List<EmHouseErrMonthModel> errList;
	private List<EmHouseErrMonthModel> clientList;
	private List<EmHouseErrMonthModel> companyList;
	private List<EmHouseErrMonthModel> tsdateList;

	private EmHouse_FinanceBll bll = new EmHouse_FinanceBll();
	private EmHouseSetBll sbll = new EmHouseSetBll();

	private EmHouseErrMonthModel eem = new EmHouseErrMonthModel();

	public EmHouseErrMonthController() {
		eem.setOwnmonth(sbll.nowmonth());
		list = bll.getEmhouseErrMonthList(eem.getOwnmonth(), null, null, null,
				null, null, null, null, null, false, null);
		ownmonthList = bll.getEmhouseErrMonthList(null, null, null, null, null,
				null, null, null, null, true, "a.ownmonth");
		errList = bll.getEmhouseErrMonthList(eem.getOwnmonth(), null, null,
				null, null, null, null, null, null, true, "emhe_err");
		clientList = bll.getEmhouseErrMonthList(eem.getOwnmonth(), null, null,
				null, null, null, null, null, null, true, "coba_client");
		companyList = bll.getEmhouseErrMonthList(eem.getOwnmonth(), null, null,
				null, null, null, null, null, null, true, "coba_shortname");
		tsdateList = bll.getEmhouseErrMonthList(eem.getOwnmonth(), null, null, null,
				null, null, null, null, null, true, "cohf_tsday");
	}
	
	@Command
	public void export(){
		bll.exportMonthErrExcel(list);
	}

	@Command
	@NotifyChange({ "list", "clientList", "companyList", "errList" })
	public void searchList() {
		if (eem.getSingle() != null && !eem.getSingle().equals("")) {
			eem.setEmhe_single(eem.getSingle().equals("中智大户") ? 0 : 1);
		} else {
			eem.setEmhe_single(null);
		}

		list = bll.getEmhouseErrMonthList(eem.getOwnmonth(),
				eem.getEmhe_single(), eem.getEmhe_err(), eem.getCoba_client(),
				eem.getCoba_shortname(), eem.getEmhe_name(),
				eem.getEmhe_idcard(), eem.getEmhe_houseid(), eem.getCohf_tsday(),
				false, null);
		errList = bll.getEmhouseErrMonthList(eem.getOwnmonth(),
				eem.getEmhe_single(), null, eem.getCoba_client(),
				eem.getCoba_shortname(), eem.getEmhe_name(),
				eem.getEmhe_idcard(), eem.getEmhe_houseid(), eem.getCohf_tsday(),
				true, "emhe_err");
		clientList = bll.getEmhouseErrMonthList(eem.getOwnmonth(),
				eem.getEmhe_single(), eem.getEmhe_err(), null,
				eem.getCoba_shortname(), eem.getEmhe_name(),
				eem.getEmhe_idcard(), eem.getEmhe_houseid(), eem.getCohf_tsday(),
				true, "coba_client");
		companyList = bll.getEmhouseErrMonthList(eem.getOwnmonth(),
				eem.getEmhe_single(), eem.getEmhe_err(), eem.getCoba_client(),
				null, eem.getEmhe_name(), eem.getEmhe_idcard(),
				eem.getEmhe_houseid(), eem.getCohf_tsday(), true, "coba_shortname");
	}

	public List<EmHouseErrMonthModel> getList() {
		return list;
	}

	public void setList(List<EmHouseErrMonthModel> list) {
		this.list = list;
	}

	public List<EmHouseErrMonthModel> getOwnmonthList() {
		return ownmonthList;
	}

	public void setOwnmonthList(List<EmHouseErrMonthModel> ownmonthList) {
		this.ownmonthList = ownmonthList;
	}

	public List<EmHouseErrMonthModel> getErrList() {
		return errList;
	}

	public void setErrList(List<EmHouseErrMonthModel> errList) {
		this.errList = errList;
	}

	public List<EmHouseErrMonthModel> getClientList() {
		return clientList;
	}

	public void setClientList(List<EmHouseErrMonthModel> clientList) {
		this.clientList = clientList;
	}

	public List<EmHouseErrMonthModel> getCompanyList() {
		return companyList;
	}

	public void setCompanyList(List<EmHouseErrMonthModel> companyList) {
		this.companyList = companyList;
	}

	public EmHouseErrMonthModel getEem() {
		return eem;
	}

	public void setEem(EmHouseErrMonthModel eem) {
		this.eem = eem;
	}

	public List<EmHouseErrMonthModel> getTsdateList() {
		return tsdateList;
	}

	public void setTsdateList(List<EmHouseErrMonthModel> tsdateList) {
		this.tsdateList = tsdateList;
	}

}
