package Controller.EmTax;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.zkoss.bind.annotation.BindingParam;
import org.zkoss.bind.annotation.Command;
import org.zkoss.bind.annotation.NotifyChange;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zul.Window;

import bll.EmTax.EmTax_SelectBll;

import Model.EmTaxInfoModel;
import Util.CheckString;

public class EmTax_TaxPlaceListController {
	private EmTax_SelectBll tBll = new EmTax_SelectBll();
	private List<EmTaxInfoModel> dataList;
	private String company = "";
	private String state = "";

	public EmTax_TaxPlaceListController() {
		search();
	}

	@Command("search")
	@NotifyChange({ "dataList" })
	public void search() {
		String sql = "";
		if (!"".equals(company)) {

			if (CheckString.isNum(company)) {
				sql = sql + "  AND cid=" + company;
			} else if (CheckString.isChinese(company)) {
				sql = sql + " AND coba_shortname LIKE '%" + company + "%'";
			} else if (CheckString.isLetter(company)) {
				sql = sql + " AND coba_shortspell LIKE '%" + company + "%'";
			}

		}
		if ("未完成".equals(state)) {
			sql = sql + " AND y<>s";
		} else if ("已完成".equals(state)) {
			sql = sql + " AND y=s";
		}

		dataList = tBll.getCoTaxPlaceList(sql);
	}

	@Command("assign")
	@NotifyChange({ "dataList" })
	public void assign(@BindingParam("m") EmTaxInfoModel m) {
		Map map = new HashMap();
		map.put("daid", m.getCid());
		Window window = (Window) Executions.createComponents("EmTax_TaxPlaceAssign.zul", null, map);
		window.doModal();

		// 刷新页面
		search();
	}

	public List<EmTaxInfoModel> getDataList() {
		return dataList;
	}

	public void setDataList(List<EmTaxInfoModel> dataList) {
		this.dataList = dataList;
	}

	public String getCompany() {
		return company;
	}

	public void setCompany(String company) {
		this.company = company;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

}
