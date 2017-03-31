package Controller.EmCAFFeeInfo;

import impl.CheckStringImpl;

import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.zkoss.bind.annotation.BindingParam;
import org.zkoss.bind.annotation.Command;
import org.zkoss.bind.annotation.NotifyChange;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zul.Checkbox;
import org.zkoss.zul.Grid;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Row;
import org.zkoss.zul.Window;

import service.CheckStringService;

import Model.EmCAFFeeInfoModel;
import bll.EmCAFFeeInfo.EmCAFFeeInfo_SelectBll;

public class EmCAFFeeInfo_ManageController {
	private EmCAFFeeInfo_SelectBll ecfiSBll = new EmCAFFeeInfo_SelectBll();
	private SimpleDateFormat sdf = new SimpleDateFormat("yyyyMM");

	private List<EmCAFFeeInfoModel> ecfiList = new ArrayList<EmCAFFeeInfoModel>();
	String str = "";
	String orderBy = "";
	// 查询条件
	private String company;
	private String emp;
	private String type;
	private String state;
	private String pnum;

	public EmCAFFeeInfo_ManageController() throws SQLException {
		str = " AND ecfi_if_return=0 AND ownmonth=" + sdf.format(new Date());
		orderBy = " ORDER BY CHARINDEX(ecfi_state+',','待清账,已借款,未借款,已清账,'),shortname,name";
		ecfiList = ecfiSBll.getECFIInfoList(str, orderBy);
	}

	// 查询
	@Command("search")
	@NotifyChange({ "ecfiList" })
	public void search() throws SQLException {
		CheckStringService ch = new CheckStringImpl();
		str = " AND ecfi_if_return=0";
		// 公司名称
		if (company != "" && company != null && !company.equals("")) {
			company = company.trim();
			if (ch.isChinese(company)) {
				str = str + " and a.shortname like '%" + company + "%' ";
			} else if (ch.isNum(company)) {
				str = str + " and (a.cid='" + company
						+ "' or a.shortname like '%" + company + "%')";
			}
		}
		if (emp != null && !emp.equals("")) {
			if (ch.isNum(emp)) {
				str += " and a.gid like " + emp + "%";
			} else {
				str += " and a.name like '%" + emp + "%'";
			}
		}

		if (type != null && !type.equals("")) {
			str += " and ecfi_class='" + type + "'";

		}

		if (state != null && !state.equals("")) {
			str += " and ecfi_state='" + state + "'";

		}

		if (pnum != null && !pnum.equals("")) {
			str += " and ecfi_cl_number='" + pnum + "'";

		}

		ecfiList = ecfiSBll.getECFIInfoList(str, orderBy);
	}

	// 打开处理页面
	@Command("openUpdate")
	@NotifyChange({ "ecfiList" })
	public void openUpdate(@BindingParam("ecfi_id") Integer ecfi_id)
			throws SQLException {
		String url = "EmCAFFeeInfo_UpdateState.zul";
		Map map = new HashMap();
		map.put("ecfi_id", ecfi_id);
		Window window;
		window = (Window) Executions.createComponents(url, null, map);
		window.doModal();
		ecfiList = ecfiSBll.getECFIInfoList(str, orderBy);

	}

	// 打开处理页面
	@Command("openUpdateAll")
	@NotifyChange({ "ecfiList" })
	public void openUpdateAll(@BindingParam("gridwin") Grid gridwin)
			throws SQLException {
		// 检查所选数据是否为同一单号下的数据
		if (chkGdId(gridwin)) {
			String url = "EmCAFFeeInfo_UpdateAllState.zul";
			List<Integer> list = getGdId(gridwin);
			Map map = new HashMap();
			map.put("list_ecfi_id", list);
			Window window;
			window = (Window) Executions.createComponents(url, null, map);
			window.doModal();
			ecfiList = ecfiSBll.getECFIInfoList(str, orderBy);
		} else {
			Messagebox.show("请按单号逐批处理 !", "操作提示", Messagebox.OK,
					Messagebox.ERROR);
		}
	}

	// 获取Grid的勾选的ID
	private boolean chkGdId(Grid gridwin) {
		boolean b = true;
		String number = "";// 当前单号
		String l_number = "";// 上一条单号
		List<Component> rows = gridwin.getRows().getChildren();
		Row row;
		for (Object obj : rows) {
			row = (Row) obj;
			try {
				// 判断该行是否勾选
				if (((Checkbox) row.getChildren().get(17).getChildren().get(0))
						.isChecked()) {
					number = ((EmCAFFeeInfoModel) row.getValue())
							.getEcfi_cl_number();
					if (!"".equals(l_number)) {// 判断是否为第一条
						if (!number.equals(l_number)) {// 判断“当前单号”是否与“上一条单号”相同
							b = false;
							break;
						}
					}

					l_number = number;// 将“当前单号”赋值到“上一条单号”变量中

				}
			} catch (Exception e) {
				e.printStackTrace();
				b = false;
				break;
			}
		}
		return b;
	}

	// 全选
	@Command("checkAll")
	public void checkAll(@BindingParam("gridwin") Grid gridwin,
			@BindingParam("cbAll") Checkbox cbAll) {
		List<Component> row = gridwin.getRows().getChildren();
		boolean ifCheck = cbAll.isChecked();
		int page = gridwin.getActivePage();
		int size = gridwin.getPageSize();
		int min = page * size;
		int max = (page + 1) * size;
		try {
			for (int i = min; i < max; i++) {
				try {
					Checkbox ck = (Checkbox) row.get(i).getChildren().get(17)
							.getChildren().get(0);
					if (!ck.isDisabled()) {
						ck.setChecked(ifCheck);
					}
				} catch (Exception e) {

				}
			}
		} catch (Exception e) {

		}
	}

	// 获取Grid的勾选的ID
	private List<Integer> getGdId(Grid gridwin) {
		List<Integer> list = new ArrayList<Integer>();
		List<Component> rows = gridwin.getRows().getChildren();
		Row row;
		for (Object obj : rows) {
			row = (Row) obj;
			try {
				// 判断该行是否勾选
				if (((Checkbox) row.getChildren().get(17).getChildren().get(0))
						.isChecked()) {
					list.add(((EmCAFFeeInfoModel) row.getValue()).getEcfi_id());
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return list;
	}

	public List<EmCAFFeeInfoModel> getEcfiList() {
		return ecfiList;
	}

	public void setEcfiList(List<EmCAFFeeInfoModel> ecfiList) {
		this.ecfiList = ecfiList;
	}

	public String getCompany() {
		return company;
	}

	public void setCompany(String company) {
		this.company = company;
	}

	public String getEmp() {
		return emp;
	}

	public void setEmp(String emp) {
		this.emp = emp;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public String getPnum() {
		return pnum;
	}

	public void setPnum(String pnum) {
		this.pnum = pnum;
	}

}
