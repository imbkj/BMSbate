package Controller.EmSalary;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.zkoss.bind.annotation.BindingParam;
import org.zkoss.bind.annotation.Command;
import org.zkoss.bind.annotation.NotifyChange;

import org.zkoss.zk.ui.Executions;
import org.zkoss.zul.Grid;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Window;

import Model.EmSalaryDataModel;
import Util.CheckString;
import Util.DateStringChange;
import bll.EmSalary.EmSalaryInfoListBll;

public class EmSalaryList_RepayController {

	private List<EmSalaryDataModel> emsdata = new ArrayList<EmSalaryDataModel>();
	private EmSalaryInfoListBll emsbll = new EmSalaryInfoListBll();
	private DateStringChange dsc = new DateStringChange();

	// 获取当前所属月份
	Date nowDate = new Date(); // 获取当前时间
	private String ownmonth = dsc.DatetoSting(nowDate, "yyyyMM");
	private String company = "";
	private String ename = "";
	private String str = "";
	private List ownmonthlist;

	public EmSalaryList_RepayController() throws SQLException {
		ownmonthlist = emsbll.getownmonth("");
	}

	@Command("search")
	@NotifyChange({ "emsdata" })
	public void search(@BindingParam("gd") Grid gd) throws Exception {
		str = "";
		if (ownmonth == "" && company == "" && ename == "") {
			Messagebox
					.show("请输入查询条件！", "操作提示", Messagebox.OK, Messagebox.ERROR);
		} else {

			if (ownmonth != null && ownmonth != "" && !"".equals(ownmonth)) {
				str = str + " and a.ownmonth=" + ownmonth;
			}

			if (company != null && company != "" && !"".equals(company)) {
				if (CheckString.isNum(company)) {
					str = str + " And a.cid=" + company;
				} else if (CheckString.isChinese(company)) {
					str = str + " And b.coba_shortname like'%" + company + "%'";
				}
			}

			if (ename != null && ename != "" && !"".equals(ename)) {
				if (CheckString.isNum(ename)) {
					str = str + " And a.gid=" + ename;
				} else if (CheckString.isChinese(ename)) {
					str = str + " And a.esda_ba_name like'%" + ename + "%'";
				}
			}

		}

		emsdata = emsbll.getRepayData(str);
	}

	@Command("openUrl")
	@NotifyChange({ "emsdata" })
	public void openUrl(@BindingParam("d_type") String d_type,
			@BindingParam("tid") Integer tid) {

		if ("工资".equals(d_type)) {// 工资数据
			Map<String, String> map = new HashMap<String, String>();
			map.put("esda_id", String.valueOf(tid));
			Window window = (Window) Executions.createComponents(
					"EmSalary_RepayReason.zul", null, map);
			window.doModal();
		} else if ("支付".equals(d_type)) {
			// 支付模块数据
			Map<String, String> map = new HashMap<String, String>();
			map.put("empa_id", String.valueOf(tid));
			Window window = (Window) Executions.createComponents(
					"EmSalary_RepayEmPay.zul", null, map);
			window.doModal();
		}

		// 刷新
		emsdata = emsbll.getRepayData(str);
	}

	public List<EmSalaryDataModel> getEmsdata() {
		return emsdata;
	}

	public void setEmsdata(List<EmSalaryDataModel> emsdata) {
		this.emsdata = emsdata;
	}

	public String getOwnmonth() {
		return ownmonth;
	}

	public void setOwnmonth(String ownmonth) {
		this.ownmonth = ownmonth;
	}

	public List getOwnmonthlist() {
		return ownmonthlist;
	}

	public void setOwnmonthlist(List ownmonthlist) {
		this.ownmonthlist = ownmonthlist;
	}

	public String getCompany() {
		return company;
	}

	public void setCompany(String company) {
		this.company = company;
	}

	public String getEname() {
		return ename;
	}

	public void setEname(String ename) {
		this.ename = ename;
	}

}
