package Controller.EmSalary;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.zkoss.bind.BindUtils;
import org.zkoss.bind.annotation.BindingParam;
import org.zkoss.bind.annotation.Command;
import org.zkoss.bind.annotation.NotifyChange;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zul.Checkbox;
import org.zkoss.zul.Grid;
import org.zkoss.zul.Intbox;
import org.zkoss.zul.Label;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Row;
import org.zkoss.zul.Textbox;
import org.zkoss.zul.Window;

import Model.EmSalaryBaseAddItemModel;
import Model.EmSalaryDataModel;
import Util.RegexUtil;
import Util.UserInfo;
import bll.EmSalary.EmSalary_AudtingOperateBll;
import bll.EmSalary.EmSalary_SalarySelBll;

public class EmSalary_PayAudtingListController {
	private final int taba_id = Integer.parseInt(Executions.getCurrent()
			.getArg().get("daid").toString());
	private final int taba_tapr_id = Integer.parseInt(Executions.getCurrent()
			.getArg().get("id").toString());

	private List<EmSalaryDataModel> salaryList;
	private List<EmSalaryDataModel> salaryWinList;
	private List<EmSalaryBaseAddItemModel> itemList;
	private List<EmSalaryDataModel> salaryListSUM;// 合计
	private List<EmSalaryDataModel> salaryWinListSUM;// 合计
	private List<EmSalaryBaseAddItemModel> itemListSUM;// 合计
	private EmSalary_SalarySelBll bll;

	public EmSalary_PayAudtingListController() {
		bll = new EmSalary_SalarySelBll();
		salaryList = bll.getSalaryDataByTabaIdToPay(taba_id);
		if (salaryList.size() > 0) {
			EmSalaryDataModel m = (EmSalaryDataModel) salaryList.get(0);
			itemList = bll.getItemInfoByCidMonthNoPay(m.getCid(),
					m.getOwnmonth());
			setEmSalaryDataModelOfItemList(salaryList, itemList);
			salaryWinList = salaryList;

			// 合计
			salaryListSUM = bll.getSalaryDataSUM("", taba_id);
			itemListSUM = itemList;
			setEmSalaryDataModelOfItemListSUM(salaryListSUM, itemListSUM);
			salaryWinListSUM = salaryListSUM;
		}

	}

	// 查看台账账单管理页面
	@Command("billManage")
	public void billManage() {
		try {
			int cfma_id = getCfma_id();
			if (cfma_id != 0) {
				Map<String, Integer> map = new HashMap<String, Integer>();
				map.put("cfma_id", cfma_id);
				Window window = (Window) Executions.createComponents(
						"../CoFinanceManage/Cfma_MonthlyBillManage.zul", null,
						map);
				window.doModal();
				refreshSalaryWinList();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	// 获取台账的cfma_id
	private int getCfma_id() {
		if (salaryList.size() > 0) {
			try {
				EmSalaryDataModel m = salaryList.get(0);
				int cfma_id = bll.getCfmaId(m.getCid(), m.getGid(),
						m.getOwnmonth());
				return cfma_id;

			} catch (Exception e) {
				e.printStackTrace();
			}
		} else {
			Messagebox.show("未找到工资有效数据，无法关联台账页面。", "操作提示", Messagebox.OK,
					Messagebox.INFORMATION);
		}
		return 0;
	}

	// 待发放确认
	@Command("paySalary")
	public void paySalary(@BindingParam("gdSalary") Grid gdSalary,
			@BindingParam("win") Window win) {
		if (Messagebox.show("确认待发放选中的工资数据吗？", "操作提示", Messagebox.YES
				| Messagebox.NO, Messagebox.QUESTION) == Messagebox.YES) {
			try {
				List<Integer[]> list = getGdId(gdSalary);
				if (checkReceivableConfirm(list)) {
					operateSalary(list, win, 1);
				} else {
					// 弹出提示
					Messagebox.show("勾选项中有台账应收未确认的数据，请确认台账后再操作。", "操作提示",
							Messagebox.OK, Messagebox.INFORMATION);
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	// 暂停发放
	@Command("holdSalary")
	public void holdSalary(@BindingParam("gdSalary") Grid gdSalary,
			@BindingParam("win") Window win) {
		try {
			if (Messagebox.show("确认暂停发放选中的工资数据吗？", "操作提示", Messagebox.YES
					| Messagebox.NO, Messagebox.QUESTION) == Messagebox.YES) {

				List<EmSalaryDataModel> list = getGdModel(gdSalary);
				if (list.size() != 0) {
					EmSalary_AudtingOperateBll opBll = new EmSalary_AudtingOperateBll();
					String[] message = opBll.payHoldEmSalary(list);

					if ("1".equals(message[0])) {
						// 弹出提示
						Messagebox.show(message[1], "操作提示", Messagebox.OK,
								Messagebox.NONE);
						if ("1".equals(message[2])) {
							win.detach();
						} else {
							refreshSalaryWinList();
						}
					} else {
						// 弹出提示
						Messagebox.show(message[1], "操作提示", Messagebox.OK,
								Messagebox.ERROR);
					}
				}

			} else {
				Messagebox.show("请勾选您需要确认的数据。", "操作提示", Messagebox.OK,
						Messagebox.ERROR);
			}
		} catch (Exception e) {
			e.printStackTrace();
			Messagebox.show("工资暂停发放出错。", "操作提示", Messagebox.OK,
					Messagebox.ERROR);
		}
	}

	// 退回工资数据
	@Command("returnSalary")
	public void returnSalary(@BindingParam("gdSalary") Grid gdSalary,
			@BindingParam("win") Window win) {
		if (Messagebox.show("确认退回选中的工资数据吗？", "操作提示", Messagebox.YES
				| Messagebox.NO, Messagebox.QUESTION) == Messagebox.YES) {
			List<Integer[]> list = getGdId(gdSalary);
			operateSalary(list, win, 2);
		}
	}

	// 工资数据审核、退回操作(type: 1发放工资 2退回)
	private void operateSalary(List<Integer[]> list, Window win, int type) {
		EmSalary_AudtingOperateBll opBll = new EmSalary_AudtingOperateBll();
		try {

			if (list.size() != 0) {
				String[] message = opBll.payAudtingOperate(list,
						UserInfo.getUsername(), taba_id, taba_tapr_id, type);
				if ("1".equals(message[0])) {
					// 弹出提示
					Messagebox.show(message[1], "操作提示", Messagebox.OK,
							Messagebox.NONE);
					if ("1".equals(message[2])) {
						win.detach();
					} else {
						refreshSalaryWinList();
					}
				} else {
					// 弹出提示
					Messagebox.show(message[1], "操作提示", Messagebox.OK,
							Messagebox.ERROR);
				}
			} else {
				Messagebox.show("请勾选您需要确认的数据。", "操作提示", Messagebox.OK,
						Messagebox.INFORMATION);
			}
		} catch (Exception e) {
			e.printStackTrace();
			Messagebox.show("工资审核出错。", "操作提示", Messagebox.OK, Messagebox.ERROR);
		}
	}

	// 刷新页面
	public void refreshSalaryWinList() {
		salaryList = bll.getSalaryDataByTabaIdToPay(taba_id);
		setEmSalaryDataModelOfItemList(salaryList, itemList);
		salaryWinList = salaryList;
		BindUtils.postNotifyChange(null, null, this, "salaryWinList");
	}

	// 获取Grid的勾选的ID
	private List<Integer[]> getGdId(Grid gdSalary) {
		List<Integer[]> list = new ArrayList<Integer[]>();
		List<Component> rows = gdSalary.getRows().getChildren();
		Row row;
		for (Object obj : rows) {
			row = (Row) obj;
			try {
				// 判断该行是否勾选
				if (((Checkbox) row.getChildren().get(1)).isChecked()) {
					list.add(new Integer[] {
							((EmSalaryDataModel) row.getValue()).getTbrb_id(),
							((EmSalaryDataModel) row.getValue())
									.getTbrb_customInt() });
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return list;
	}

	// 获取Grid的勾选的model
	private List<EmSalaryDataModel> getGdModel(Grid gdSalary) {
		List<EmSalaryDataModel> list = new ArrayList<EmSalaryDataModel>();
		List<Component> rows = gdSalary.getRows().getChildren();
		Row row;
		for (Object obj : rows) {
			row = (Row) obj;
			try {
				// 判断该行是否勾选
				if (((Checkbox) row.getChildren().get(1)).isChecked()) {
					EmSalaryDataModel m = new EmSalaryDataModel();
					m = (EmSalaryDataModel) row.getValue();
					list.add(m);
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return list;
	}

	// 检测勾选的数据台账应收是否已全部确认
	private boolean checkReceivableConfirm(List<Integer[]> list) {
		for (Integer[] i : list) {
			if (i[1] == 0)
				return false;
		}
		return true;
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

	// 点击员工编号查看工资的个人信息
	@Command("SelEmbase")
	public void SelEmbase(@BindingParam("lbl") Label lbl) {
		int esda_id = ((EmSalaryDataModel) ((Row) lbl.getParent()).getValue())
				.getEsda_id();
		int esda_payment_state = ((EmSalaryDataModel) ((Row) lbl.getParent())
				.getValue()).getEsda_payment_state();

		String url = "";
		if (esda_payment_state != 2) {
			url = "../EmSalary/EmSalary_EmbaseUpdate.zul";
		} else {
			url = "../EmSalary/EmSalary_Embase.zul";
		}

		Map<String, String> map = new HashMap<String, String>();
		map.put("esda_id", String.valueOf(esda_id));
		Window window = (Window) Executions.createComponents(url, null, map);
		window.doModal();
	}

	// 点击姓名修改报价单信息
	@Command("upCoGlist")
	public void upCoGlist(@BindingParam("lbl") Label lbl) {
		int gid = ((EmSalaryDataModel) ((Row) lbl.getParent()).getValue())
				.getGid();
		Map<String, String> map = new HashMap<String, String>();
		map.put("gid", String.valueOf(gid));
		Window window = (Window) Executions.createComponents(
				"../Embase/EmBase_CoGlistUp.zul", null, map);
		window.doModal();
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
					Checkbox ck = (Checkbox) row.get(i).getChildren().get(1);
					if (!ck.isDisabled()) {
						ck.setChecked(ifCheck);
					}
				} catch (Exception e) {

				}
			}
		} catch (Exception e) {

		}
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
