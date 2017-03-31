package Controller.EmCAFFeeInfo;

import impl.CheckStringImpl;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.zkoss.bind.annotation.Command;
import org.zkoss.bind.annotation.NotifyChange;

import service.CheckStringService;

import Model.EmCAFFeeInfoCompanyModel;
import bll.EmCAFFeeInfo.EmCAFFeeInfo_SelectBll;

public class EmCAFFeeInfo_CompanyListController {
	private EmCAFFeeInfo_SelectBll ecfiSBll = new EmCAFFeeInfo_SelectBll();

	private List<EmCAFFeeInfoCompanyModel> ecfiList = new ArrayList<EmCAFFeeInfoCompanyModel>();
	String str = "";

	// 查询条件
	private String company;

	public EmCAFFeeInfo_CompanyListController() throws SQLException {
		str = " AND a.ecfi_cl_number IS NOT NULL AND a.ecfi_cl_number<>'' AND a.ecfi_if_return=0";
		ecfiList = ecfiSBll.getECFICompanyList(str);
	}

	// 查询
	@Command("search")
	@NotifyChange({ "ecfiList" })
	public void search() throws SQLException {
		CheckStringService ch = new CheckStringImpl();
		str = " AND a.ecfi_cl_number IS NOT NULL AND a.ecfi_cl_number<>'' AND a.ecfi_if_return=0";
		// 公司名称
		if (company != "" && company != null && !company.equals("")) {
			company = company.trim();
			if (ch.isChinese(company)) {
				str = str + " and a.shortname like '%" + company + "%' ";
			} else if (ch.isNum(company)) {
				str = str + " and (a.cid='" + company + "' or a.shortname like '%"
						+ company + "%')";
			}
		}

		ecfiList = ecfiSBll.getECFICompanyList(str);
	}

	public List<EmCAFFeeInfoCompanyModel> getEcfiList() {
		return ecfiList;
	}

	public void setEcfiList(List<EmCAFFeeInfoCompanyModel> ecfiList) {
		this.ecfiList = ecfiList;
	}

	public String getCompany() {
		return company;
	}

	public void setCompany(String company) {
		this.company = company;
	}

}
