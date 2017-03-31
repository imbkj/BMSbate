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
import org.zkoss.zul.Checkbox;
import org.zkoss.zul.Decimalbox;
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
import bll.EmSalary.EmSalary_NonZeroOperateBll;
import bll.EmSalary.EmSalary_SalarySelBll;

public class EmSalary_UpdateNonZeroDataController {
	private final int taba_id = Integer.parseInt(Executions.getCurrent()
			.getArg().get("daid").toString());
	private final int taba_tapr_id = Integer.parseInt(Executions.getCurrent()
			.getArg().get("id").toString());

	private List<EmSalaryDataModel> salaryList;
	private List<EmSalaryDataModel> salaryWinList;
	private List<EmSalaryBaseAddItemModel> itemList;
	private int cid;
	private int ownmonth;
	private EmSalary_SalarySelBll bll = new EmSalary_SalarySelBll();
	private EmSalary_NonZeroOperateBll opbll = new EmSalary_NonZeroOperateBll();

	public EmSalary_UpdateNonZeroDataController() {
		salaryList = bll.getSalaryDataByTabaIdToNonZero(taba_id);
		if (salaryList.size() > 0) {
			EmSalaryDataModel m = (EmSalaryDataModel) salaryList.get(0);
			cid = m.getCid();
			ownmonth = m.getOwnmonth();
			itemList = bll.getCSIIInfo(cid, ownmonth, 2);
			setEmSalaryDataModelOfItemList(salaryList, itemList);
			salaryWinList = salaryList;
		}
	}

	// 保存修改工资
	@Command("UpSalary")
	public void UpSalary(@BindingParam("gdSalary") Grid gdSalary) {
		try {
			List<EmSalaryDataModel> upSalList = getGridData(gdSalary);
			if (upSalList.size() > 0) {
				String[] message = insertDB(upSalList);
				if (message[0].equals("1")) {
					// 弹出提示
					Messagebox.show(message[1], "操作提示", Messagebox.OK,
							Messagebox.NONE);
					refreshWin();
					BindUtils.postNotifyChange(null, null, this,
							"salaryWinList");

				} else {
					// 弹出提示
					Messagebox.show(message[1], "操作提示", Messagebox.OK,
							Messagebox.ERROR);
				}
			} else {
				Messagebox.show("未找到修改数据，无需提交。", "操作提示", Messagebox.OK,
						Messagebox.INFORMATION);
			}
		} catch (Exception e) {
			e.printStackTrace();
			Messagebox.show("修改工资出错。", "操作提示", Messagebox.OK, Messagebox.ERROR);
		}
	}

	// 删除工资
	@Command("DelSalary")
	public void DelSalary(@BindingParam("lbl") Label lbl,
			@BindingParam("win") Window win) {
		EmSalaryDataModel m = (EmSalaryDataModel) ((Row) lbl.getParent())
				.getValue();
		String name = m.getName();
		if (Messagebox.show("确认删除 ‘" + name + "’ 的这条工资修改数据吗？", "操作提示",
				Messagebox.YES | Messagebox.NO, Messagebox.QUESTION) == Messagebox.YES) {
			int i = opbll.DelSalaryNonZero(m.getEsdt_id(), m.getTbrb_id());
			if (i == 1) {
				lbl.getParent().getParent()
						.removeChild(((Row) lbl.getParent()));
				salaryList.remove(m);
				removeSalaryWinList(m);
				// 判断该任务单是否已无有效数据,若无则自动终止任务单。
				if (opbll.existEmSalaryAudtingAll(taba_id)) {
					String[] message = opbll.StopTask(taba_id, taba_tapr_id,
							UserInfo.getUsername());
					if ("1".equals(message[0])) {
						Messagebox.show("该任务单已无数据，任务单已自动终止。", "操作提示",
								Messagebox.OK, Messagebox.NONE);
						win.detach();
					}
				}
			}
		}
	}

	// 转下一步
	@Command("PassToNext")
	public void PassToNext(@BindingParam("win") Window win,
			@BindingParam("gdSalary") Grid gdSalary) {
		if (Messagebox.show("确认转下一步操作吗？", "操作提示", Messagebox.YES
				| Messagebox.NO, Messagebox.QUESTION) == Messagebox.YES) {			
			List<EmSalaryDataModel> upSalList = getGridData(gdSalary);
			//if (upSalList.size() > 0) {
				String[] message = insertDB(upSalList);
				if ("1".equals(message[0])) {
					message = opbll.UpdatePassToNext(UserInfo.getUsername(),
							taba_id, taba_tapr_id);
					if ("1".equals(message[0].toString())) {
						Messagebox.show(message[1], "操作提示", Messagebox.OK,
								Messagebox.NONE);
						win.detach();
					} else {
						Messagebox.show(message[1], "操作提示", Messagebox.OK,
								Messagebox.ERROR);
					}
				} else {
					// 弹出提示
					Messagebox.show(message[1], "操作提示", Messagebox.OK,
							Messagebox.ERROR);
				}
			/*} else {
				Messagebox.show("未找到修改数据，无需提交。", "操作提示", Messagebox.OK,
						Messagebox.INFORMATION);
			}*/
			
		}
	}

	// 终止任务单
	@Command("StopTask")
	public void StopTask(@BindingParam("win") Window win) {
		if (Messagebox.show("确认终止该任务单吗？", "操作提示", Messagebox.YES
				| Messagebox.NO, Messagebox.QUESTION) == Messagebox.YES) {
			String[] message = opbll.StopTask(taba_id, taba_tapr_id,
					UserInfo.getUsername());
			if ("1".equals(message[0].toString())) {
				Messagebox.show(message[1], "操作提示", Messagebox.OK,
						Messagebox.NONE);
				win.detach();
			} else {
				Messagebox.show(message[1], "操作提示", Messagebox.OK,
						Messagebox.ERROR);
			}
		}
	}

	// 移除页面数据行
	@NotifyChange("salaryWinList")
	private void removeSalaryWinList(EmSalaryDataModel m) {
		salaryWinList.remove(m);
	}

	// 刷新页面数据
	@NotifyChange("salaryWinList")
	private void refreshWin() {
		salaryList = bll.getSalaryDataByTabaIdToNonZero(taba_id);
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
		map.put("type", 2);
		Window window = (Window) Executions.createComponents(
				"../EmSalary/EmSalary_UploadChange.zul", view, map);
		window.doModal();
	}

	// 点击姓名查看工资的个人信息
	@Command("SelEmbase")
	public void SelEmbase(@BindingParam("lbl") Label lbl) {
		int esda_id = ((EmSalaryDataModel) ((Row) lbl.getParent()).getValue())
				.getEsda_id();
		Map<String, String> map = new HashMap<String, String>();
		map.put("esda_id", String.valueOf(esda_id));
		Window window = (Window) Executions.createComponents(
				"../EmSalary/EmSalary_Embase.zul", null, map);
		window.doModal();
	}

	// 子页面执行刷新salaryList
	@Command("setSalaryList")
	@NotifyChange("salaryWinList")
	public void setSalaryList() {
		salaryList = bll.getSalaryDataByTabaIdToNonZero(taba_id);
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

	// 修改工资插入数据库
	private String[] insertDB(List<EmSalaryDataModel> upSalList) {
		String[] message = opbll.UpSalaryNonZero(upSalList);
		return message;
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
				if (((Checkbox) row.getChildren().get(0).getChildren().get(0))
						.isChecked()) {
					upSalModel = (EmSalaryDataModel) row.getValue();
					cellCount = row.getChildren().size();
					// 遍历数据列
					for (int cel = 7; cel < cellCount; cel++) {
						// 反射数据到MODEL
						setField(upSalModel, itemList.get(cel - 7)
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
}
