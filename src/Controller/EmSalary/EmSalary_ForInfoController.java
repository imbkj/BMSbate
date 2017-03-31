package Controller.EmSalary;


import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
import Util.UserInfo;
import bll.EmSalary.ItemFormula_OperateBll;
import bll.EmSalary.ItemFormula_SelectBll;

public class EmSalary_ForInfoController {
	private List<CoFormulaInfoModel> formulaList;
	private List<CoSalaryItemFormulaModel> forDataList;
	private ItemFormula_SelectBll ifSBll = new ItemFormula_SelectBll();
	private ItemFormula_OperateBll ifOBll = new ItemFormula_OperateBll();

	// 获取用户名
	String username = UserInfo.getUsername();
	String userid = UserInfo.getUserid();

	private int seq = 0;
	private String[] chkForItem;

	private String cid = String.valueOf(Executions.getCurrent().getArg()
			.get("cid").toString());

	private int i_cfin_id;
	private String csii_itemid;
	private String for_remark = "";

	private CoFormulaInfoModel fModel;

	public EmSalary_ForInfoController() {
		this.Inti();

	}

	// 页面初始化
	public void Inti() {
		i_cfin_id = 0;
		try {
			csii_itemid = ifSBll.getCoSalaryItemID(" AND cid=" + cid).get(0)
					.getCsii_itemid();
		} catch (Exception e) {
			// 弹出提示
			Messagebox.show("请先设置工资项目！", "操作提示", Messagebox.OK,
					Messagebox.ERROR);
		}

		// 获取算法下拉框
		formulaList = ifSBll.getFormulaInfo(" AND csii_itemid='" + csii_itemid
				+ "'");

		// 获取算法内容
		if (formulaList.size() > 0) {
			if (formulaList.get(0).getCfin_id() != 0) {
				i_cfin_id = formulaList.get(0).getCfin_id();
				for_remark = formulaList.get(0).getCfin_remark();

				// 初始化算法名称下拉框
				for (CoFormulaInfoModel fm : formulaList) {
					if (fm.getCfin_id() == i_cfin_id) {
						fModel = fm;
					}
				}
			}
		}

		seq = -1;

		forDataList = ifSBll.getFormulaData(" AND cfin_id=" + i_cfin_id);

		// 判断是否可修改工资项目组合
		chkForItem = new String[2];
		chkForItem = ifOBll.chkFormula(i_cfin_id);
	}

	// 查询
	@Command("seach")
	@NotifyChange({ "forDataList", "for_remark" })
	public void seach(@BindingParam("cfin_id") Combobox cfin_id) {
		i_cfin_id = 0;
		if (cfin_id.getSelectedItem() != null) {
			i_cfin_id = Integer.parseInt(cfin_id.getSelectedItem().getValue()
					.toString());

			// 获取备注
			for (CoFormulaInfoModel fm : formulaList) {
				if (fm.getCfin_id() == i_cfin_id) {
					for_remark = fm.getCfin_remark();
				}
			}
		}

		forDataList = ifSBll.getFormulaData(" AND cfin_id=" + i_cfin_id);
		if (forDataList.size() <= 0) {
			seq = -1;
		}

		// 判断是否可修改工资项目组合
		chkForItem = new String[2];
		chkForItem = ifOBll.chkFormula(i_cfin_id);
	}

	// 修改项目顺序
	@Command("move")
	@NotifyChange("forDataList")
	public void move(@BindingParam("type") String type,
			@BindingParam("itemList") Listbox itemList) {

		if (chkForItem[0].equals("0")) {
			if (itemList.getSelectedItem() != null) {

				int chk = 1;
				if (itemList.getSelectedIndex() == 0 && type.equals("top")) {// 第一列点击top无用
					chk = 0;
				}
				if (itemList.getSelectedIndex() == forDataList.size() - 1
						&& type.equals("bottom")) {// 最后一列点击bottom无用
					chk = 0;
				}

				if (chk != 0) {
					int cfda_id;
					cfda_id = Integer
							.parseInt(((CoSalaryItemFormulaModel) itemList
									.getSelectedItem().getValue()).getCfda_id()
									.toString());
					String[] message = ifOBll.changeForSequence(type, cfda_id);

					// 操作出错提示
					if (!message[0].equals("1")) {
						// 弹出提示
						Messagebox.show(message[1], "操作提示", Messagebox.OK,
								Messagebox.ERROR);
					} else {
						// 重新加载数据
						forDataList = ifSBll.getFormulaData(" AND cfin_id="
								+ i_cfin_id);

						// 保持选择项选中
						if (type.equals("top")) {
							seq = 0;
						} else if (type.equals("up")) {
							seq = itemList.getSelectedIndex() - 1;
						} else if (type.equals("down")) {
							seq = itemList.getSelectedIndex() + 1;
						} else if (type.equals("bottom")) {
							seq = forDataList.size() - 1;
						}
					}
				}

			}
		} else {
			// 弹出提示
			Messagebox.show(chkForItem[1], "操作提示", Messagebox.OK,
					Messagebox.ERROR);
		}
	}

	// 修改项目 或 删除项目
	@Command("editItem")
	@NotifyChange("forDataList")
	public void editItem(@BindingParam("url") String url,
			@BindingParam("cfdaM") CoSalaryItemFormulaModel cfdaM) {
		// 判断是否为固定项目，是则不能删除
		if (chkForItem[0].equals("0")) {
			// 专递cfdaM
			Map map = new HashMap();
			map.put("cfdaM", cfdaM);
			Window window = (Window) Executions
					.createComponents(url, null, map);
			window.doModal();
			forDataList = ifSBll.getFormulaData(" AND cfin_id=" + i_cfin_id);
			if (forDataList.size() <= 0) {
				seq = -1;
			}
		} else {
			// 弹出提示
			Messagebox.show(chkForItem[1], "操作提示", Messagebox.OK,
					Messagebox.ERROR);
		}
	}

	// 删除算法组合
	@Command("delFor")
	@NotifyChange({ "formulaList", "forDataList" })
	public void delFor(@BindingParam("cfin_id") Combobox cfin_id) {
		// 判断是否为固定项目，是则不能删除
		if (chkForItem[0].equals("0")) {

			CoFormulaInfoModel cfinM = (CoFormulaInfoModel) ifSBll
					.getFormulaInfo(
							" AND cfin_id="
									+ String.valueOf(cfin_id.getSelectedItem()
											.getValue())).get(0);

			// 专递cfinM
			Map map = new HashMap();
			map.put("cfinM", cfinM);
			Window window = (Window) Executions.createComponents(
					"../EmSalary/EmSalary_ForDel.zul", null, map);
			window.doModal();

			this.Inti();

			try {
				cfin_id.setValue("");
				cfin_id.setSelectedIndex(0);
			} catch (Exception e) {
				cfin_id.setSelectedIndex(-1);
			}

		} else {
			// 弹出提示
			Messagebox.show(chkForItem[1], "操作提示", Messagebox.OK,
					Messagebox.ERROR);
		}
	}

	// 新增算法组合
	@Command("addFor")
	@NotifyChange({ "formulaList", "forDataList" })
	public void addFor(@BindingParam("cfin_id") Combobox cfin_id) {
		// 专递cid
		Map map = new HashMap();
		map.put("cid", cid);
		Window window = (Window) Executions.createComponents(
				"../EmSalary/EmSalary_ForAdd.zul", null, map);
		window.doModal();

		this.Inti();

		try {
			cfin_id.setValue("");
			cfin_id.setSelectedIndex(0);
		} catch (Exception e) {
			cfin_id.setSelectedIndex(-1);
		}
	}

	// 新增算法内容
	@Command("addForData")
	@NotifyChange("forDataList")
	public void addForData(@BindingParam("cfin_id") Combobox cfin_id) {
		// 判断是否为固定项目，是则不能删除
		if (chkForItem[0].equals("0")) {

			CoFormulaInfoModel cfinM = (CoFormulaInfoModel) ifSBll
					.getFormulaInfo(
							" AND cfin_id="
									+ String.valueOf(cfin_id.getSelectedItem()
											.getValue())).get(0);

			// 专递cfinM
			Map map = new HashMap();
			map.put("cfinM", cfinM);
			Window window = (Window) Executions.createComponents(
					"../EmSalary/EmSalary_ForDataAdd.zul", null, map);
			window.doModal();
			forDataList = ifSBll.getFormulaData(" AND cfin_id=" + i_cfin_id);
			if (forDataList.size() <= 0) {
				seq = -1;
			}
		} else {
			// 弹出提示
			Messagebox.show(chkForItem[1], "操作提示", Messagebox.OK,
					Messagebox.ERROR);
		}
	}

	// 验证算法
	@Command("chkFor")
	public void chkFor(@BindingParam("cfin_id") Combobox cfin_id) {
		i_cfin_id=0;
		if (cfin_id.getSelectedItem() != null) {
			i_cfin_id = Integer.parseInt(cfin_id.getSelectedItem().getValue()
					.toString());
		
		// 专递参数
		Map map = new HashMap();
		map.put("cfin_id", i_cfin_id);
		map.put("cid", cid);
		Window window = (Window) Executions.createComponents(
				"../EmSalary/EmSalary_ChkFormula.zul", null, map);
		window.doModal();
		}
	}

	public List<CoFormulaInfoModel> getFormulaList() {
		return formulaList;
	}

	public void setFormulaList(List<CoFormulaInfoModel> formulaList) {
		this.formulaList = formulaList;
	}

	public List<CoSalaryItemFormulaModel> getForDataList() {
		return forDataList;
	}

	public void setForDataList(List<CoSalaryItemFormulaModel> forDataList) {
		this.forDataList = forDataList;
	}

	public int getSeq() {
		return seq;
	}

	public void setSeq(int seq) {
		this.seq = seq;
	}

	public CoFormulaInfoModel getfModel() {
		return fModel;
	}

	public void setfModel(CoFormulaInfoModel fModel) {
		this.fModel = fModel;
	}

	public String getFor_remark() {
		return for_remark;
	}

	public void setFor_remark(String for_remark) {
		this.for_remark = for_remark;
	}

}
