package Controller.EmSalary;

import java.util.List;

import org.zkoss.bind.annotation.BindingParam;
import org.zkoss.bind.annotation.Command;
import org.zkoss.bind.annotation.NotifyChange;

import Model.CoSalaryItemFormulaModel;
import Model.EmSalaryDataModel;
import bll.EmSalary.EmSalary_SalarySelBll;

public class EmSalary_ListController {

	private List<EmSalaryDataModel> salaryList;
	private List<CoSalaryItemFormulaModel> itemList;
	private List<String> ownmonthList;
	private List<String[]> companyList;
	private EmSalary_SalarySelBll bll = new EmSalary_SalarySelBll();

	public EmSalary_ListController() {
		ownmonthList = bll.getOwnmonth();
	}

	@Command("ownmonthChange")
	@NotifyChange("companyList")
	public void ownmonthChange(@BindingParam("con") String value) {
		if (!"".equals(value) && value != null) {
			companyList = bll.getCompany(Integer.valueOf(value));
		}
	}

	@Command("companyChange")
	@NotifyChange({ "salaryList", "itemList" })
	public void companyChange(@BindingParam("com") String company,
			@BindingParam("ownmonth") String ownmonth) {
		System.out.println(company);
		System.out.println(ownmonth);
		if (!"".equals(company) && company != null) {
			salaryList = bll.getSalaryDataByCidMonth(Integer.valueOf(company),
					Integer.valueOf(ownmonth));
			itemList = bll.getItemInfoByCidMonth(Integer.valueOf(company),
					Integer.valueOf(ownmonth));
		}
	}

	public List<String> getOwnmonthList() {
		return ownmonthList;
	}

	public void setOwnmonthList(List<String> ownmonthList) {
		this.ownmonthList = ownmonthList;
	}

	public List<EmSalaryDataModel> getSalaryList() {
		return salaryList;
	}

	public void setSalaryList(List<EmSalaryDataModel> salaryList) {
		this.salaryList = salaryList;
	}

	public List<CoSalaryItemFormulaModel> getItemList() {
		return itemList;
	}

	public void setItemList(List<CoSalaryItemFormulaModel> itemList) {
		this.itemList = itemList;
	}

	public List<String[]> getCompanyList() {
		return companyList;
	}

	public void setCompanyList(List<String[]> companyList) {
		this.companyList = companyList;
	}

}
