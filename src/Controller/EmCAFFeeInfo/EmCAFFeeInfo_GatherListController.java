package Controller.EmCAFFeeInfo;

import impl.CheckStringImpl;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.zkoss.bind.annotation.Command;
import org.zkoss.bind.annotation.NotifyChange;

import service.CheckStringService;

import Model.EmCAFFeeInfoCompanyModel;
import Util.DateStringChange;
import bll.EmCAFFeeInfo.EmCAFFeeInfo_SelectBll;

public class EmCAFFeeInfo_GatherListController {
	private EmCAFFeeInfo_SelectBll ecfiSBll = new EmCAFFeeInfo_SelectBll();
	private List<EmCAFFeeInfoCompanyModel> ecfiList = new ArrayList<EmCAFFeeInfoCompanyModel>();
	private DateStringChange dsc = new DateStringChange();
	String str = "";

	// 查询条件
	private Date rec_date;

	public EmCAFFeeInfo_GatherListController() throws SQLException {
		ecfiList = ecfiSBll.getECFIInfoGAllFee(str);
	}

	// 查询
	@Command("search")
	@NotifyChange({ "ecfiList" })
	public void search() throws SQLException {

		// 收款日期
		if (rec_date != null) {
			str = " AND ecfi_rec_date='"
					+ dsc.DatetoSting(rec_date, "yyyy-MM-dd") + "'";
		}
		ecfiList = ecfiSBll.getECFIInfoGAllFee(str);
	}

	public List<EmCAFFeeInfoCompanyModel> getEcfiList() {
		return ecfiList;
	}

	public void setEcfiList(List<EmCAFFeeInfoCompanyModel> ecfiList) {
		this.ecfiList = ecfiList;
	}

	public Date getRec_date() {
		return rec_date;
	}

	public void setRec_date(Date rec_date) {
		this.rec_date = rec_date;
	}

}
