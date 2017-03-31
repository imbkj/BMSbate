package Controller.EmSalary;

import java.lang.reflect.Method;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.zkoss.bind.BindUtils;
import org.zkoss.bind.annotation.BindingParam;
import org.zkoss.bind.annotation.Command;
import org.zkoss.bind.annotation.ContextParam;
import org.zkoss.bind.annotation.ContextType;
import org.zkoss.bind.annotation.NotifyChange;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zul.Grid;
import org.zkoss.zul.Intbox;
import org.zkoss.zul.Label;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Row;
import org.zkoss.zul.Checkbox;
import org.zkoss.zul.Decimalbox;
import org.zkoss.zul.Textbox;
import org.zkoss.zul.Window;

import bll.EmSalary.EmSalary_SalaryOperateBll;
import bll.EmSalary.EmSalary_SalarySelBll;

import Model.EmSalaryBaseAddItemModel;
import Model.EmSalaryDataModel;
import Util.RegexUtil;
import Util.UserInfo;

public class EmSalaryBase_UpdateController {
	private final int cid = Integer.parseInt(Executions.getCurrent().getArg()
			.get("cid").toString());
	private final int ownmonth = Integer.parseInt(Executions.getCurrent()
			.getArg().get("ownmonth").toString());

	private List<EmSalaryDataModel> salaryList;
	private List<EmSalaryDataModel> salaryListSUM;// 合计
	private List<EmSalaryDataModel> salaryWinList;
	private List<EmSalaryDataModel> salaryWinListSUM;// 合计
	private List<EmSalaryBaseAddItemModel> itemList;
	private List<EmSalaryBaseAddItemModel> itemListSUM;// 合计
	private List<EmSalaryBaseAddItemModel> itemListSel;
	private List<EmSalaryBaseAddItemModel> itemListSelSUM;// 合计
	private EmSalary_SalarySelBll bll = new EmSalary_SalarySelBll();

	public EmSalaryBase_UpdateController() {
		salaryList = bll.getSalaryDataByCidMonth(cid, ownmonth, 3);
		itemList = bll.getItemInfoByCidMonthCanEdit(cid, ownmonth);
		itemListSel = bll.getItemInfoByCidMonthNoZero(cid, ownmonth);
		setEmSalaryDataModelOfItemList(salaryList, itemList);
		setEmSalaryDataModelOfItemListNoZero(salaryList, itemListSel);
		salaryWinList = salaryList;

		// 合计
		salaryListSUM = bll.getSalaryDataSUM(
				" and ed.esda_payment_state=3 AND ed.cid="
						+ String.valueOf(cid) + " AND ed.ownmonth="
						+ String.valueOf(ownmonth), null);
		itemListSUM = itemList;
		itemListSelSUM = itemListSel;
		setEmSalaryDataModelOfItemListSUM(salaryListSUM, itemListSUM);
		setEmSalaryDataModelOfItemListNoZeroSUM(salaryListSUM, itemListSelSUM);
		salaryWinListSUM = salaryListSUM;
	}

	// 修改工资
	@Command("UpSalary")
	public void UpSalary(@BindingParam("gdSalary") Grid gdSalary,
			@BindingParam("winEmSalaryUpdate") Window winEmSalaryUpdate) {
		try {
			List<EmSalaryDataModel> upSalList = getGridData(gdSalary);
			if (upSalList.size() > 0) {
				EmSalary_SalaryOperateBll bll = new EmSalary_SalaryOperateBll();
				String[] message = bll.UpSalary(upSalList);
				if (message[0].equals("1")) {
					// 弹出提示
					Messagebox.show(message[1], "操作提示", Messagebox.OK,
							Messagebox.NONE);
					refreshWin();
					BindUtils.postNotifyChange(null, null, this,
							"salaryWinList");
					// winEmSalaryUpdate.detach();

				} else {
					// 弹出提示
					Messagebox.show(message[1], "操作提示", Messagebox.OK,
							Messagebox.ERROR);
				}
			} else {
				Messagebox.show("未修改数据，无需提交。", "操作提示", Messagebox.OK,
						Messagebox.INFORMATION);
			}
		} catch (Exception e) {
			e.printStackTrace();
			Messagebox.show("修改工资出错。", "操作提示", Messagebox.OK, Messagebox.ERROR);
		}
	}

	// 删除工资
	@Command("DelSalary")
	public void DelSalary(@BindingParam("lbl") Label lbl) {
		EmSalaryDataModel m = (EmSalaryDataModel) ((Row) lbl.getParent()
				.getParent()).getValue();
		String name = m.getName();
		if (Messagebox.show("确认删除 ‘" + name + "’ 的这条工资数据吗？", "操作提示",
				Messagebox.YES | Messagebox.NO, Messagebox.QUESTION) == Messagebox.YES) {
			EmSalary_SalaryOperateBll opbll = new EmSalary_SalaryOperateBll();
			int i = opbll.DelSalary(m.getEsda_id());
			if (i == 1) {
				lbl.getParent().getParent().getParent()
						.removeChild(((Row) lbl.getParent().getParent()));
				salaryList.remove(m);
				removeSalaryWinList(m);
			}
		}
	}

	// 批量删除工资
	@Command("BatchDelSalary")
	public void BatchDelSalary(@BindingParam("gdSalary") Grid gdSalary) {
		try {
			List<Integer> list = getGdId(gdSalary);
			int delSum = list.size();
			if (Messagebox.show("确认批量删除勾选的这 " + delSum + "条工资数据吗？", "操作提示",
					Messagebox.YES | Messagebox.NO, Messagebox.QUESTION) == Messagebox.YES) {
				EmSalary_SalaryOperateBll opbll = new EmSalary_SalaryOperateBll();
				int scSum = 0;
				for (Integer id : list) {
					scSum += opbll.DelSalary(id);
				}
				if (scSum == delSum) {
					Messagebox.show("成功删除" + scSum + "条数据。", "操作提示",
							Messagebox.OK, Messagebox.INFORMATION);
					refreshWin();
					BindUtils.postNotifyChange(null, null, this,
							"salaryWinList");
				} else if (scSum == 0) {
					Messagebox.show("批量删除工资，失败。", "操作提示", Messagebox.OK,
							Messagebox.ERROR);
				} else {
					Messagebox.show("批量删除" + delSum + "条数据，其中"
							+ (delSum - scSum) + "条数据删除失败。", "操作提示",
							Messagebox.OK, Messagebox.INFORMATION);
					refreshWin();
					BindUtils.postNotifyChange(null, null, this,
							"salaryWinList");
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			Messagebox.show("批量删除工资，出错。", "操作提示", Messagebox.OK,
					Messagebox.ERROR);
		}
	}

	// 全选
	@Command("checkAll")
	public void checkAll(@BindingParam("gdSalary") Grid gdSalary,
			@BindingParam("cbAll") Checkbox cbAll) {
		List<Component> row = gdSalary.getRows().getChildren();
		boolean ifCheck = cbAll.isChecked();
		int page = gdSalary.getActivePage();
		int size = gdSalary.getPageSize();
		int min = page * size;
		int max = (page + 1) * size;
		try {
			for (int i = min; i < max; i++) {
				try {
					Checkbox ck = (Checkbox) row.get(i).getChildren().get(9)
							.getChildren().get(0);
					ck.setChecked(ifCheck);

				} catch (Exception e) {

				}
			}
		} catch (Exception e) {

		}
	}

	// 获取Grid的勾选的ID
	private List<Integer> getGdId(Grid gdSalary) {
		List<Integer> list = new ArrayList<Integer>();
		List<Component> rows = gdSalary.getRows().getChildren();
		Row row;
		for (Object obj : rows) {
			row = (Row) obj;
			try {
				// 判断该行是否勾选
				if (((Checkbox) row.getChildren().get(9).getChildren().get(0))
						.isChecked()) {
					list.add(((EmSalaryDataModel) row.getValue()).getEsda_id());
				}
			} catch (Exception e) {

			}
		}
		return list;
	}

	// 点击姓名查看工资的个人信息
	@Command("SelEmbase")
	public void SelEmbase(@BindingParam("lbl") Label lbl) {
		int esda_id = ((EmSalaryDataModel) ((Row) lbl.getParent()).getValue())
				.getEsda_id();
		int esda_payment_state = ((EmSalaryDataModel) ((Row) lbl.getParent())
				.getValue()).getEsda_payment_state();

		String url = "";
		if (esda_payment_state != 2) {
			url = "EmSalary_EmbaseUpdate.zul";
		} else {
			url = "EmSalary_Embase.zul";
		}

		Map<String, String> map = new HashMap<String, String>();
		map.put("esda_id", String.valueOf(esda_id));
		Window window = (Window) Executions.createComponents(url, null, map);
		window.doModal();
	}

	// 移除页面数据行
	@NotifyChange("salaryWinList")
	private void removeSalaryWinList(EmSalaryDataModel m) {
		salaryWinList.remove(m);
	}

	// 刷新页面数据
	@NotifyChange("salaryWinList")
	private void refreshWin() {
		salaryList = bll.getSalaryDataByCidMonth(cid, ownmonth, 3);
		setEmSalaryDataModelOfItemList(salaryList, itemList);
		salaryWinList = salaryList;
	}

	// 弹出导入变动数据页面
	@Command("ChangeSalary")
	public void ChangeSalary(@ContextParam(ContextType.VIEW) Component view) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("cid", cid);
		map.put("ownmonth", ownmonth);
		map.put("itemList", itemList);
		map.put("salaryList", salaryList);
		map.put("type", 1);
		Window window = (Window) Executions.createComponents(
				"EmSalary_UploadChange.zul", view, map);
		window.doModal();
	}

	// 弹出导入变动数据页面
	@Command("BatchAddSalary")
	public void BatchAddSalary(@ContextParam(ContextType.VIEW) Component view) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("cid", cid);
		map.put("ownmonth", ownmonth);
		map.put("itemList", itemList);
		Window window = (Window) Executions.createComponents(
				"EmSalary_UploadBatchAdd.zul", view, map);
		window.doModal();
	}

	// 子页面执行刷新salaryList
	@Command("setSalaryList")
	@NotifyChange("salaryWinList")
	public void setSalaryList() {
		salaryList = bll.getSalaryDataByCidMonth(cid, ownmonth, 3);
		setEmSalaryDataModelOfItemList(salaryList, itemList);
		salaryWinList = salaryList;
	}

	// 检索
	@Command("changeList")
	@NotifyChange("salaryWinList")
	public void changeList(@BindingParam("ibGid") Intbox ibGid,
			@BindingParam("txtName") Textbox txtName) {
		if (!"".equals(txtName.getValue()) || ibGid.getValue() != null) {
			if (ibGid.getValue() == null) {
				salaryWinList = getNewList(0, txtName.getValue());
			} else {
				salaryWinList = getNewList(ibGid.getValue(), txtName.getValue());
			}
		} else {
			salaryWinList = salaryList;
		}
	}

	// 打开待确认数据页面
	@Command("confirmSalary")
	public void confirmSalary(@BindingParam("winEmSalaryUpdate") Window winEmSalaryUpdate,
			@ContextParam(ContextType.VIEW) Component view)
			throws InterruptedException {
		// 判断用户是否为薪酬负责人
		// if (checkGzaddname(UserInfo.getUsername())) {
		Map<String, String> map = new HashMap<String, String>();
		map.put("cid", String.valueOf(cid));
		map.put("ownmonth", String.valueOf(ownmonth));
		Window window = (Window) Executions.createComponents(
				"EmSalary_ConfirmList.zul", view, map);
		window.doModal();
		winEmSalaryUpdate.detach();
		// }
	}

	// 检索数据
	private List<EmSalaryDataModel> getNewList(int gid, String name) {
		List<EmSalaryDataModel> list = new ArrayList<EmSalaryDataModel>();
		int i = 2;
		for (EmSalaryDataModel m : salaryList) {
			i = 2;
			if (gid == 0) {
				i = i - 1;
			} else if (gid == m.getGid()) {
				i = i - 1;
			}
			try {
				if (name == null || "".equals(name)) {
					i = i - 1;
				} else if (RegexUtil.isExists(name, m.getName())) {
					i = i - 1;
				}
			} catch (Exception e) {

			}
			if (i == 0) {
				list.add(m);
			}
		}
		return list;
	}

	// 获取Grid的修改数据
	private List<EmSalaryDataModel> getGridData(Grid gdSalary) {
		List<Component> rows = gdSalary.getRows().getChildren();
		List<EmSalaryDataModel> upSalList = new ArrayList<EmSalaryDataModel>();
		EmSalaryDataModel upSalModel = null;
		Row row;
		int cellCount = 0;
		for (Object obj : rows) {
			row = (Row) obj;
			try { // 判断该行是否有修改数据
				if (((Checkbox) row.getChildren().get(1).getChildren().get(0))
						.isChecked()) {
					upSalModel = (EmSalaryDataModel) row.getValue();
					cellCount = row.getChildren().size();
					// 遍历数据列
					for (int cel = 10; cel < cellCount; cel++) {
						// 反射数据到MODEL
						setField(upSalModel, itemList.get(cel - 10)
								.getCsii_col(), ((Decimalbox) row.getChildren()
								.get(cel)).getValue());
					}
					upSalList.add(upSalModel);
				}
			} catch (Exception e) {

			}
		}
		return upSalList;
	}

	// 初始化EmSalaryDataModel的itemList
	private void setEmSalaryDataModelOfItemList(List<EmSalaryDataModel> sdList,
			List<EmSalaryBaseAddItemModel> itList) {
		for (EmSalaryDataModel m : sdList) {
			try {
				m.setItemList(itList);
			} catch (Exception e) {

			}
		}
	}

	// 初始化EmSalaryDataModel的itemListSUM
	private void setEmSalaryDataModelOfItemListSUM(
			List<EmSalaryDataModel> sdList,
			List<EmSalaryBaseAddItemModel> itList) {
		for (EmSalaryDataModel m : sdList) {
			try {
				m.setItemListSUM(itList);
			} catch (Exception e) {

			}
		}
	}

	// 初始化非清零的itemList（用于展开查看）
	private void setEmSalaryDataModelOfItemListNoZero(
			List<EmSalaryDataModel> sdList,
			List<EmSalaryBaseAddItemModel> itList) {
		for (EmSalaryDataModel m : sdList) {
			try {
				m.setItemListSel(itList);
			} catch (Exception e) {

			}
		}
	}

	// 初始化非清零的itemList（用于展开查看）
	private void setEmSalaryDataModelOfItemListNoZeroSUM(
			List<EmSalaryDataModel> sdList,
			List<EmSalaryBaseAddItemModel> itList) {
		for (EmSalaryDataModel m : sdList) {
			try {
				m.setItemListSelSUM(itList);
			} catch (Exception e) {

			}
		}
	}

	// 调用Set方法
	private void setField(Object obj, String fieldname, Object value) {
		try {
			Method method = obj.getClass().getMethod(setMethod(fieldname),
					BigDecimal.class);
			method.invoke(obj, value);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	// 根据字段名获取SET方法名；
	private static String setMethod(String name) {
		return "set"
				+ name.replaceFirst(name.substring(0, 1), name.substring(0, 1)
						.toUpperCase());
	}

	public List<EmSalaryDataModel> getSalaryWinList() {
		return salaryWinList;
	}

	public List<EmSalaryBaseAddItemModel> getItemList() {
		return itemList;
	}

	public List<EmSalaryDataModel> getSalaryWinListSUM() {
		return salaryWinListSUM;
	}

	public List<EmSalaryBaseAddItemModel> getItemListSUM() {
		return itemListSUM;
	}
}
