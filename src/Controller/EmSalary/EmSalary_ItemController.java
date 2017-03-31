package Controller.EmSalary;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import java.util.List;

import org.zkoss.bind.annotation.BindingParam;
import org.zkoss.bind.annotation.Command;
import org.zkoss.bind.annotation.NotifyChange;

import org.zkoss.zk.ui.Executions;

import org.zkoss.zul.Combobox;
import org.zkoss.zul.Listbox;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Window;

import Model.CoFormulaInfoModel;
import Model.CoSalaryItemFormulaModel;
import Util.DateStringChange;
import Util.MonthListUtil;
import Util.UserInfo;
import bll.EmSalary.ItemFormula_OperateBll;
import bll.EmSalary.ItemFormula_SelectBll;

public class EmSalary_ItemController {
	private List<CoSalaryItemFormulaModel> itemFormulaList;
	private List<CoFormulaInfoModel> formulaList;
	private ItemFormula_SelectBll ifSBll = new ItemFormula_SelectBll();
	private ItemFormula_OperateBll ifOBll = new ItemFormula_OperateBll();
	private DateStringChange dsc = new DateStringChange();
	private MonthListUtil mlUtil = new MonthListUtil();
	// 获取用户名
	String username = UserInfo.getUsername();
	String userid = UserInfo.getUserid();

	private int seq = 0;
	private String[] chkForItem;

	// 获取当前所属月份
	Date nowDate = new Date(); // 获取当前时间
	private String ownmonth = dsc.DatetoSting(nowDate, "yyyyMM");

	// 获取当前所属月份未来第二个月
	private String[] f_ownmonth = mlUtil.getMonthList(true, ownmonth, "f", 2);

	private String cid = String.valueOf(Executions.getCurrent().getArg()
			.get("cid").toString());
	private String[] ownmonthList;
	private String csii_itemid = "";
	private String cfin_id = "0";

	private CoFormulaInfoModel fModel;

	public EmSalary_ItemController() {
		// 检查是否为全新公司，如果是，插入一套默认项目
		ifOBll.addSalaryItemId(Integer.parseInt(cid),
				Integer.parseInt(ownmonth), username);

		/*
		 * // 获取项目算法id csii_itemid = ifSBll .getCoSalaryItemID( " AND cid=" +
		 * cid + " AND ownmonth=" + ownmonth).get(0) .getCsii_itemid();
		 * 
		 * // 获取算法下拉框 formulaList = ifSBll.getFormulaInfo(" AND csii_itemid='" +
		 * csii_itemid + "'"); if (formulaList.size() > 0) { cfin_id =
		 * String.valueOf(formulaList.get(0).getCfin_id());
		 * 
		 * // 初始化算法名称下拉框 for (CoFormulaInfoModel fm : formulaList) { if
		 * (fm.getCfin_id() == Integer.parseInt(cfin_id)) { fModel = fm; } } }
		 * 
		 * // 获取工资项目列表 itemFormulaList = ifSBll.getItemFormulaCSIF(cid,
		 * ownmonth, "", " AND csii_fd_state<>2 AND cfin_id=" + cfin_id);
		 * ownmonthList = mlUtil.getMonthList(true, f_ownmonth[1], "h", 5);
		 * 
		 * // 判断是否可修改工资项目组合 chkForItem = new String[2]; chkForItem =
		 * ifOBll.chkSalaryFormulaItem(Integer.parseInt(cid),
		 * Integer.parseInt(ownmonth));
		 * 
		 * if (itemFormulaList.size() <= 0) { seq = -1; }
		 */
		pageStart(cid, ownmonth);
	}

	// 页面初始化
	public void pageStart(String cid, String ownmonth) {
		// 获取项目算法id
		csii_itemid = ifSBll
				.getCoSalaryItemID(
						" AND cid=" + cid + " AND ownmonth=" + ownmonth).get(0)
				.getCsii_itemid();

		// 获取算法下拉框
		formulaList = ifSBll.getFormulaInfo(" AND csii_itemid='" + csii_itemid
				+ "'");
		if (formulaList.size() > 0) {
			cfin_id = String.valueOf(formulaList.get(0).getCfin_id());

			// 初始化算法名称下拉框
			for (CoFormulaInfoModel fm : formulaList) {
				if (fm.getCfin_id() == Integer.parseInt(cfin_id)) {
					fModel = fm;
				}
			}
		}

		// 获取工资项目列表
		itemFormulaList = ifSBll.getItemFormulaCSIF(cid, ownmonth, "",
				" AND csii_fd_state<>2 AND cfin_id=" + cfin_id);
		ownmonthList = mlUtil.getMonthList(true, f_ownmonth[1], "h", 5);

		// 判断是否可修改工资项目组合
		chkForItem = new String[2];
		chkForItem = ifOBll.chkSalaryFormulaItem(Integer.parseInt(cid),
				Integer.parseInt(ownmonth));

		if (itemFormulaList.size() <= 0) {
			seq = -1;
		}
	}

	// 查询
	@Command("seachFormula")
	@NotifyChange({ "itemFormulaList", "chkForItem" })
	public void seachFormula(@BindingParam("ownmonthCb") Combobox ownmonthCb,
			@BindingParam("cfin_id") Combobox cfin_id) {
		if (cfin_id.getSelectedItem() != null
				&& ownmonthCb.getSelectedItem() != null) {
			ownmonth = ownmonthCb.getValue();

			// 获取项目算法id
			csii_itemid = ifSBll
					.getCoSalaryItemID(
							" AND cid=" + cid + " AND ownmonth=" + ownmonth)
					.get(0).getCsii_itemid();
			// System.out.println(csii_itemid);
			// 获取算法下拉框
			String s_cfin_id = "0";
			formulaList = ifSBll.getFormulaInfo(" AND csii_itemid='"
					+ csii_itemid + "'");
			if (formulaList.size() > 0) {
				s_cfin_id = String.valueOf(formulaList.get(0).getCfin_id());
			}

			itemFormulaList = ifSBll.getItemFormulaCSIF(cid, ownmonth, "",
					" AND csii_fd_state<>2 AND cfin_id=" + s_cfin_id);

			// 判断是否可修改工资项目组合
			chkForItem = new String[2];
			chkForItem = ifOBll.chkSalaryFormulaItem(Integer.parseInt(cid),
					Integer.parseInt(ownmonth));

			if (itemFormulaList.size() <= 0) {
				seq = -1;
			}

		} else {
			// 弹出提示
			Messagebox
					.show("请选择月份和算法", "操作提示", Messagebox.OK, Messagebox.ERROR);
		}
	}

	// 查询月份获取算法下拉框
	@Command("seach")
	@NotifyChange({ "formulaList", "fModel", "itemFormulaList", "chkForItem" })
	public void seach(@BindingParam("ownmonthCb") Combobox ownmonthCb) {
		if (ownmonthCb.getSelectedItem() != null) {
			ownmonth = ownmonthCb.getValue();

			// 检查是否为全新公司，如果是，插入一套默认项目
			ifOBll.addSalaryItemId(Integer.parseInt(cid),
					Integer.parseInt(ownmonth), username);

			csii_itemid = ifSBll
					.getCoSalaryItemID(
							" AND cid=" + cid + " AND ownmonth=" + ownmonth)
					.get(0).getCsii_itemid();
			// System.out.println(csii_itemid);
			// 获取算法下拉框
			formulaList = ifSBll.getFormulaInfo(" AND csii_itemid='"
					+ csii_itemid + "'");

			if (formulaList.size() > 0) {
				cfin_id = String.valueOf(formulaList.get(0).getCfin_id());

				// 初始化算法名称下拉框
				for (CoFormulaInfoModel fm : formulaList) {
					if (fm.getCfin_id() == Integer.parseInt(cfin_id)) {
						fModel = fm;
					}
				}
			}
			// 获取工资项目列表
			itemFormulaList = ifSBll.getItemFormulaCSIF(cid, ownmonth, "",
					" AND csii_fd_state<>2 AND cfin_id=" + cfin_id);

			// 判断是否可修改工资项目组合
			chkForItem = new String[2];
			chkForItem = ifOBll.chkSalaryFormulaItem(Integer.parseInt(cid),
					Integer.parseInt(ownmonth));

			if (itemFormulaList.size() <= 0) {
				seq = -1;
			}
		} else {
			// 弹出提示
			Messagebox.show("请选择月份", "操作提示", Messagebox.OK, Messagebox.ERROR);
		}
	}

	// 修改项目顺序
	@Command("move")
	@NotifyChange("itemFormulaList")
	public void move(@BindingParam("type") String type,
			@BindingParam("itemList") Listbox itemList) {

		if (itemList.getSelectedItem() != null) {

			int chk = 1;
			if (itemList.getSelectedIndex() == 0 && type.equals("top")) {// 第一列点击top无用
				chk = 0;
			}
			if (itemList.getSelectedIndex() == itemFormulaList.size() - 1
					&& type.equals("bottom")) {// 最后一列点击bottom无用
				chk = 0;
			}

			if (chk != 0) {
				int csii_id;
				csii_id = ((CoSalaryItemFormulaModel) itemList
						.getSelectedItem().getValue()).getCsii_id();
				String[] message = ifOBll.changeItemsSequence(type, csii_id);
				// 操作出错提示
				if (!message[0].equals("1")) {
					// 弹出提示
					Messagebox.show(message[1], "操作提示", Messagebox.OK,
							Messagebox.ERROR);
				} else {
					// 重新加载数据
					itemFormulaList = ifSBll.getItemFormulaCSIF(cid, ownmonth,
							"", " AND csii_fd_state<>2 AND cfin_id=" + cfin_id);

					// 保持选择项选中
					if (type.equals("top")) {
						seq = 0;
					} else if (type.equals("up")) {
						seq = itemList.getSelectedIndex() - 1;
					} else if (type.equals("down")) {
						seq = itemList.getSelectedIndex() + 1;
					} else if (type.equals("bottom")) {
						seq = itemFormulaList.size() - 1;
					}
				}
			}

		}
	}

	// 弹出项目改变项目显示隐藏状态页面
	@Command("openChangeDis")
	@NotifyChange("itemFormulaList")
	public void openChangeDis() {
		Map map = new HashMap();
		map.put("cid", cid);
		map.put("ownmonth", ownmonth);
		map.put("cfin_id", cfin_id);
		Window window = (Window) Executions.createComponents(
				"../EmSalary/EmSalary_ItemChangeDis.zul", null, map);
		window.doModal();
		itemFormulaList = ifSBll.getItemFormulaCSIF(cid, ownmonth, "",
				" AND csii_fd_state<>2 AND cfin_id=" + cfin_id);
	}

	// 项目改变项目显示隐藏状态
	@Command("changeDis")
	@NotifyChange("itemFormulaList")
	public void changeDis(@BindingParam("fd_state") Integer fd_state,
			@BindingParam("csii_id") Integer csii_id) {
		ifOBll.changeSalaryItemsDis(csii_id, fd_state);
		itemFormulaList = ifSBll.getItemFormulaCSIF(cid, ownmonth, "",
				" AND csii_fd_state<>2 AND cfin_id=" + cfin_id);
	}

	// 新增项目
	@Command("addItem")
	@NotifyChange("itemFormulaList")
	public void addItem(@BindingParam("ownmonthCb") Combobox ownmonthCb) {
		ifOBll.createFormula(Integer.parseInt(cid), Integer.parseInt(ownmonth),
				Integer.parseInt(cfin_id));
		// if (chkForItem[0].equals("0")) {
		String item_id = "";
		item_id = itemFormulaList.get(0).getCsii_itemid();

		Map map = new HashMap();
		map.put("cid", cid);
		map.put("ownmonth", ownmonthCb.getValue());
		map.put("item_id", item_id);
		map.put("cfin_id", cfin_id);
		Window window = (Window) Executions.createComponents(
				"../EmSalary/EmSalary_ItemAdd.zul", null, map);
		window.doModal();
		itemFormulaList = ifSBll.getItemFormulaCSIF(cid, ownmonth, "",
				" AND csii_fd_state<>2 AND cfin_id=" + cfin_id);
		/*
		 * } else { // 弹出提示 Messagebox.show(chkForItem[1], "操作提示",
		 * Messagebox.OK, Messagebox.ERROR); }
		 */
	}

	// 修改项目 或 删除项目
	@Command("editItem")
	@NotifyChange("itemFormulaList")
	public void editItem(@BindingParam("url") String url,
			@BindingParam("csiiM") CoSalaryItemFormulaModel csiiM) {
		// 判断是否为固定项目，是则不能删除
		if (chkForItem[0].equals("0")) {
			// 专递csiiM
			Map map = new HashMap();
			map.put("csiiM", csiiM);
			Window window = (Window) Executions
					.createComponents(url, null, map);
			window.doModal();
			itemFormulaList = ifSBll.getItemFormulaCSIF(
					String.valueOf(csiiM.getCid()),
					String.valueOf(csiiM.getOwnmonth()), "",
					" AND csii_fd_state<>2 AND cfin_id=" + cfin_id);

		} else {
			// 弹出提示
			Messagebox.show(chkForItem[1], "操作提示", Messagebox.OK,
					Messagebox.ERROR);
		}
	}

	// 修改项目名称
	@Command("upItemName")
	@NotifyChange("itemFormulaList")
	public void upItemName(@BindingParam("csiiM") CoSalaryItemFormulaModel csiiM) {
		// 专递csiiM
		Map map = new HashMap();
		map.put("csiiM", csiiM);
		Window window = (Window) Executions.createComponents(
				"EmSalary_ItemNameUpdate.zul", null, map);
		window.doModal();
		itemFormulaList = ifSBll.getItemFormulaCSIF(
				String.valueOf(csiiM.getCid()),
				String.valueOf(csiiM.getOwnmonth()), "",
				" AND csii_fd_state<>2 AND cfin_id=" + cfin_id);

	}

	// 设置算法
	@Command("editFormula")
	@NotifyChange("itemFormulaList")
	public void editFormula(
			@BindingParam("csiiM") CoSalaryItemFormulaModel csiiM) {
		// 专递csiiM
		Map map = new HashMap();
		map.put("csiiM", csiiM);
		Window window = (Window) Executions.createComponents(
				"../EmSalary/EmSalary_ForDataSList.zul", null, map);
		window.doModal();
		itemFormulaList = ifSBll.getItemFormulaCSIF(
				String.valueOf(csiiM.getCid()),
				String.valueOf(csiiM.getOwnmonth()), "",
				" AND csii_fd_state<>2 AND cfin_id=" + cfin_id);
	}

	// 修改清零状态
	@Command("editIfZero")
	@NotifyChange("itemFormulaList")
	public void editIfZero(@BindingParam("csiiM") CoSalaryItemFormulaModel csiiM) {
		// 专递csiiM
		Map map = new HashMap();
		map.put("csiiM", csiiM);
		Window window = (Window) Executions.createComponents(
				"../EmSalary/EmSalary_ItemIfZero.zul", null, map);
		window.doModal();
		itemFormulaList = ifSBll.getItemFormulaCSIF(
				String.valueOf(csiiM.getCid()),
				String.valueOf(csiiM.getOwnmonth()), "",
				" AND csii_fd_state<>2 AND cfin_id=" + cfin_id);
	}

	// 复制项目算法组合
	@Command("copyItem")
	@NotifyChange("itemFormulaList")
	public void copyItem(@BindingParam("ownmonthCb") Combobox ownmonthCb) {
		// if (chkForItem[0].equals("0") || chkForItem[0].equals("2")) {//
		// 只要当月没有工资数据都可以重新设置项目算法
		if (chkForItem[0].equals("0")) {
			// 专递cobaM
			Map map = new HashMap();
			map.put("cid", cid);
			map.put("ownmonth", ownmonthCb.getValue());
			Window window = (Window) Executions.createComponents(
					"../EmSalary/EmSalary_ItemCopy.zul", null, map);
			window.doModal();
			// itemFormulaList = ifSBll.getItemFormula(cid, ownmonth,
			// ""," AND csii_fd_state<>2");
			pageStart(cid, ownmonth);
		} else {
			// 弹出提示
			Messagebox.show(chkForItem[1], "操作提示", Messagebox.OK,
					Messagebox.ERROR);
		}
	}

	// 验证算法
	@Command("chkFor")
	public void chkFor(@BindingParam("cfin_id") Combobox cfin_id) {
		if (cfin_id.getSelectedItem() != null) {
			// 专递参数
			Map map = new HashMap();
			map.put("cfin_id", cfin_id.getSelectedItem().getValue());
			map.put("cid", cid);
			Window window = (Window) Executions.createComponents(
					"../EmSalary/EmSalary_ChkFormula.zul", null, map);
			window.doModal();
		}
	}

	public int getSeq() {
		return seq;
	}

	public void setSeq(int seq) {
		this.seq = seq;
	}

	public List<CoSalaryItemFormulaModel> getItemFormulaList() {
		return itemFormulaList;
	}

	public void setItemFormulaList(
			List<CoSalaryItemFormulaModel> itemFormulaList) {
		this.itemFormulaList = itemFormulaList;
	}

	public String[] getOwnmonthList() {
		return ownmonthList;
	}

	public void setOwnmonthList(String[] ownmonthList) {
		this.ownmonthList = ownmonthList;
	}

	public String getOwnmonth() {
		return ownmonth;
	}

	public void setOwnmonth(String ownmonth) {
		this.ownmonth = ownmonth;
	}

	public List<CoFormulaInfoModel> getFormulaList() {
		return formulaList;
	}

	public void setFormulaList(List<CoFormulaInfoModel> formulaList) {
		this.formulaList = formulaList;
	}

	public CoFormulaInfoModel getfModel() {
		return fModel;
	}

	public void setfModel(CoFormulaInfoModel fModel) {
		this.fModel = fModel;
	}

	public String[] getChkForItem() {
		return chkForItem;
	}

	public void setChkForItem(String[] chkForItem) {
		this.chkForItem = chkForItem;
	}

}
