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

public class EmSalary_ForDataSListController {
	private List<CoSalaryItemFormulaModel> forDataList;
	private ItemFormula_SelectBll ifSBll = new ItemFormula_SelectBll();
	private ItemFormula_OperateBll ifOBll = new ItemFormula_OperateBll();

	// 获取用户名
	String username = UserInfo.getUsername();
	String userid = UserInfo.getUserid();

	private int seq = 0;
	private String[] chkForItem;

	private String cid = String.valueOf(((CoSalaryItemFormulaModel) Executions
			.getCurrent().getArg().get("csiiM")).getCid());

	private String i_cfin_id = ((CoSalaryItemFormulaModel) Executions
			.getCurrent().getArg().get("csiiM")).getCfin_id();
	private String csii_itemid = ((CoSalaryItemFormulaModel) Executions
			.getCurrent().getArg().get("csiiM")).getCsii_itemid();
	private Integer csii_id = ((CoSalaryItemFormulaModel) Executions
			.getCurrent().getArg().get("csiiM")).getCsii_id();

	private CoFormulaInfoModel fModel;
	private CoSalaryItemFormulaModel csiiModel = (CoSalaryItemFormulaModel) Executions
			.getCurrent().getArg().get("csiiM");

	public EmSalary_ForDataSListController() {
		this.Inti();
	}

	// 页面初始化
	public void Inti() {
		seq = -1;
		forDataList = ifSBll.getFormulaData(" AND cfin_id=" + i_cfin_id
				+ " AND csii_id=" + csii_id);

		// 判断是否可修改工资项目组合
		chkForItem = new String[2];
		chkForItem = ifOBll.chkFormula(Integer.parseInt(i_cfin_id));
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
								+ i_cfin_id + " AND csii_id=" + csii_id);

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
			forDataList = ifSBll.getFormulaData(" AND cfin_id=" + i_cfin_id
					+ " AND csii_id=" + csii_id);
			if (forDataList.size() <= 0) {
				seq = -1;
			}
		} else {
			// 弹出提示
			Messagebox.show(chkForItem[1], "操作提示", Messagebox.OK,
					Messagebox.ERROR);
		}
	}

	// 新增算法内容
	@Command("addForData")
	@NotifyChange("forDataList")
	public void addForData() {
		// 判断是否为固定项目，是则不能删除
		if (chkForItem[0].equals("0")) {

			CoFormulaInfoModel cfinM = (CoFormulaInfoModel) ifSBll
					.getFormulaInfo(" AND cfin_id=" + i_cfin_id).get(0);

			// 专递cfinM
			Map map = new HashMap();
			map.put("cfinM", cfinM);
			map.put("csiiM", csiiModel);
			Window window = (Window) Executions.createComponents(
					"../EmSalary/EmSalary_ForDataSAdd.zul", null, map);
			window.doModal();
			forDataList = ifSBll.getFormulaData(" AND cfin_id=" + i_cfin_id
					+ " AND csii_id=" + csii_id);
			if (forDataList.size() <= 0) {
				seq = -1;
			}
		} else {
			// 弹出提示
			Messagebox.show(chkForItem[1], "操作提示", Messagebox.OK,
					Messagebox.ERROR);
		}
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
}
