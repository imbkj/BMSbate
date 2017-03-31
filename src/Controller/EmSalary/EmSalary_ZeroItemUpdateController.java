package Controller.EmSalary;

import java.util.ArrayList;

import java.util.List;
import java.util.Set;

import org.zkoss.bind.annotation.BindingParam;
import org.zkoss.bind.annotation.Command;
import org.zkoss.bind.annotation.NotifyChange;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zul.Combobox;
import org.zkoss.zul.Listbox;
import org.zkoss.zul.Listitem;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Window;

import Model.CoFormulaInfoModel;
import Model.CoSalaryItemFormulaModel;
import Model.EmSalaryBaseSel_viewModel;
import Util.UserInfo;
import bll.EmSalary.EmSalary_NonZeroOperateBll;
import bll.EmSalary.ItemFormula_OperateBll;
import bll.EmSalary.ItemFormula_SelectBll;

public class EmSalary_ZeroItemUpdateController {
	private List<CoSalaryItemFormulaModel> itemFormulaList;
	private ItemFormula_SelectBll ifSBll = new ItemFormula_SelectBll();
	private ItemFormula_OperateBll ifOBll = new ItemFormula_OperateBll();
	private EmSalary_NonZeroOperateBll nzOBll = new EmSalary_NonZeroOperateBll();

	private int cid = Integer.parseInt(Executions.getCurrent().getArg()
			.get("cid").toString());

	private String ownmonth;
	private String[] ownmonthList;
	private List<CoFormulaInfoModel> formulaList;
	private String cfin_id = "0";
	private CoFormulaInfoModel fModel;
	private String csii_itemid = "";

	private List<EmSalaryBaseSel_viewModel> emList;
	private List<EmSalaryBaseSel_viewModel> createList;
	private Window win;
	// 获取用户名
	String username = UserInfo.getUsername();

	public EmSalary_ZeroItemUpdateController() {
		// 获取该公司可操作非清零项目数据更新的月份
		ownmonthList = ifSBll.getZeroItemOwnmonth(cid);

		// 获取最新月份
		try {
			ownmonth = ownmonthList[0];
		} catch (Exception e) {
			ownmonth = "100000";
		}

		// 获取数据
		pageStart(cid, ownmonth);

	}

	@Command("createWin")
	public void createWin(@BindingParam("win") Window win1) {
		win = win1;
		itemFormulaList = ifSBll.getItemFormula(String.valueOf(cid), ownmonth,
				"", " AND csii_ifzero=2");
		// 检查是否可以操作当前页面
		if (itemFormulaList.size() > 0) {
			// 关闭窗口
			win.detach();
			Messagebox.show("存在未操作完成的“非清零工资项目数据更新”的任务单，请完成。", "操作提示",
					Messagebox.OK, Messagebox.ERROR);
		}
	}

	// 查询
	@Command("seach")
	@NotifyChange({ "emList", "createList", "itemFormulaList", "formulaList",
			"fModel" })
	public void seach(@BindingParam("ownmonthCb") Combobox ownmonthCb) {
		if (ownmonthCb.getSelectedItem() != null) {
			ownmonth = ownmonthCb.getValue();

			csii_itemid = ifSBll
					.getCoSalaryItemID(
							" AND cid=" + cid + " AND ownmonth=" + ownmonth)
					.get(0).getCsii_itemid();

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

			// 获取数据
			select(cid, ownmonth, cfin_id);
		}
	}

	// 查询
	@Command("seachFormula")
	@NotifyChange({ "emList", "createList", "itemFormulaList" })
	public void seachFormula(@BindingParam("ownmonthCb") Combobox ownmonthCb,
			@BindingParam("cfin_id") Combobox cfin_id) {
		if (cfin_id.getSelectedItem() != null
				&& ownmonthCb.getSelectedItem() != null) {
			ownmonth = ownmonthCb.getValue();

			// 获取数据
			select(cid, ownmonth,
					String.valueOf(cfin_id.getSelectedItem().getValue()));

		} else {
			// 弹出提示
			Messagebox
					.show("请选择月份和算法", "操作提示", Messagebox.OK, Messagebox.ERROR);
		}
	}

	// 页面初始化
	public void pageStart(Integer cid, String ownmonth) {
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

		// 获取数据
		select(cid, ownmonth, cfin_id);

	}

	// 获取数据
	public void select(int cid, String ownmonth, String cfin_id) {
		// 检查是否可以操作当前页面
		/*
		 * itemFormulaList = ifSBll.getItemFormula(String.valueOf(cid),
		 * ownmonth, "", " AND csii_ifzero=2");
		 * 
		 * if (itemFormulaList.size() > 0) {
		 * Messagebox.show("存在未操作完成的“非清零工资项目数据更新”的任务单，请完成。", "操作提示",
		 * Messagebox.OK, Messagebox.ERROR);
		 * 
		 * // 关闭窗口 win.detach();
		 * 
		 * } else {
		 */
		// 获取非清零字段列表
		itemFormulaList = ifSBll.getItemFormula(String.valueOf(cid), ownmonth,
				"", " AND csii_ifzero=0 AND csii_fd_state<>2");

		// 获取工资数据列表
		emList = ifSBll.getZeroItemEsdaDataList(" AND cid=" + cid
				+ " and ownmonth=" + ownmonth+" AND cfin_id="+cfin_id);
		
		createList = new ArrayList<EmSalaryBaseSel_viewModel>();
		// }
	}

	// 提交
	@Command("submit")
	public void submit(@BindingParam("win") Window win,
			@BindingParam("itemList") Listbox itemList) {
		String item_str = "0"; // 非清零字段id字符串
		Set<Listitem> check = itemList.getSelectedItems();
		if (!check.isEmpty()) {
			try {
				// 遍历勾选项
				for (Listitem c : check) {
					item_str = item_str
							+ ","
							+ String.valueOf(((CoSalaryItemFormulaModel) c
									.getValue()).getCsii_id());
				}

				// 获取需生成的员工数据
				if (createList.size() > 0) {
					// 生成数据
					String[] message = nzOBll.addEmSalaryDataTemp(createList,
							username, cid, Integer.parseInt(ownmonth));

					if (message[0].equals("1")) {
						// 修改工资项目清零状态
						String[] msgItem = ifOBll.upZeroItemState(item_str);

						if (!msgItem[0].equals("0")) {
							Messagebox.show("项目更新错误。", "操作提示", Messagebox.OK,
									Messagebox.ERROR);
						} else {
							// 弹出提示
							Messagebox.show(message[1], "操作提示", Messagebox.OK,
									Messagebox.NONE);
						}
					} else {
						// 弹出提示
						Messagebox.show(message[1], "操作提示", Messagebox.OK,
								Messagebox.ERROR);
					}
					win.detach();
				} else {
					Messagebox.show("请工资数据。", "操作提示", Messagebox.OK,
							Messagebox.ERROR);
				}

			} catch (Exception e) {
				Messagebox.show("项目选择错误。", "操作提示", Messagebox.OK,
						Messagebox.ERROR);
			}
		} else {
			Messagebox.show("请先选择工资项目。", "操作提示", Messagebox.OK,
					Messagebox.ERROR);
		}
	}

	// 全选
	@Command("chooseAll")
	@NotifyChange({ "emList", "createList" })
	public void chooseAll() {
		try {
			createList.addAll(emList);
			emList.clear();
		} catch (Exception e) {

		}
	}

	// 选择
	@Command("choose")
	@NotifyChange({ "emList", "createList" })
	public void choose(@BindingParam("select") Listitem select) {
		try {
			EmSalaryBaseSel_viewModel m = (EmSalaryBaseSel_viewModel) select
					.getValue();
			if (createList.add(m)) {
				emList.remove(m);
			}
		} catch (Exception e) {

		}
	}

	// 单个移除
	@Command("remove")
	@NotifyChange({ "emList", "createList" })
	public void remove(@BindingParam("select") Listitem select) {
		try {
			EmSalaryBaseSel_viewModel m = (EmSalaryBaseSel_viewModel) select
					.getValue();
			if (emList.add(m)) {
				createList.remove(m);
			}
		} catch (Exception e) {

		}
	}

	// 全移除
	@Command("removeAll")
	@NotifyChange({ "emList", "createList" })
	public void removeAll() {
		try {
			emList.addAll(createList);
			createList.clear();
		} catch (Exception e) {

		}
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

	public List<EmSalaryBaseSel_viewModel> getEmList() {
		return emList;
	}

	public void setEmList(List<EmSalaryBaseSel_viewModel> emList) {
		this.emList = emList;
	}

	public List<EmSalaryBaseSel_viewModel> getCreateList() {
		return createList;
	}

	public void setCreateList(List<EmSalaryBaseSel_viewModel> createList) {
		this.createList = createList;
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

}
