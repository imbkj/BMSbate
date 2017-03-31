package Controller.EmSalary;

import java.util.List;

import org.zkoss.bind.annotation.BindingParam;
import org.zkoss.bind.annotation.Command;
import org.zkoss.bind.annotation.NotifyChange;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.event.EventListener;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Textbox;
import org.zkoss.zul.Window;
import org.zkoss.zul.Messagebox.ClickEvent;

import Model.CoSalaryItemFormulaModel;
import Util.CheckString;
import Util.UserInfo;
import bll.EmSalary.ItemFormula_OperateBll;
import bll.EmSalary.ItemFormula_SelectBll;

public class EmSalary_ForDataUpdateController {
	private List<CoSalaryItemFormulaModel> itemList;
	private ItemFormula_SelectBll ifSBll = new ItemFormula_SelectBll();
	private ItemFormula_OperateBll ifOBll = new ItemFormula_OperateBll();
	private CheckString cs = new CheckString();
	// 获取用户名
	String username = UserInfo.getUsername();
	String userid = UserInfo.getUserid();

	private String cfin_id = ((CoSalaryItemFormulaModel) Executions
			.getCurrent().getArg().get("cfdaM")).getCfin_id();
	private String cfda_id = ((CoSalaryItemFormulaModel) Executions
			.getCurrent().getArg().get("cfdaM")).getCfda_id();

	private String oFocus = "f"; // 判断是选择算法内容还是使用范围 f：算法内容；r：使用范围

	private String formula = ((CoSalaryItemFormulaModel) Executions
			.getCurrent().getArg().get("cfdaM")).getCfda_formula(); // 算法内容

	private String range = ((CoSalaryItemFormulaModel) Executions.getCurrent()
			.getArg().get("cfdaM")).getCfda_range(); // 使用范围

	private String t_formula = ((CoSalaryItemFormulaModel) Executions
			.getCurrent().getArg().get("cfdaM")).getCfda_t_formula(); // 页面显示的算法内容

	private String t_range = ((CoSalaryItemFormulaModel) Executions
			.getCurrent().getArg().get("cfdaM")).getCfda_t_range(); // 页面显示的使用范围

	// 用于判断字符不等于的情况
	private String b_content; // 点击("等于" 或" 不等于")前内容
	private String b_item; // 点击("等于" 或" 不等于")前项目
	private int ifequals; // 点击("等于" 或" 不等于")前项目

	public EmSalary_ForDataUpdateController() {
		// 获取csii_itemid
		String csii_itemid = ifSBll.getFormulaInfo(" AND cfin_id=" + cfin_id)
				.get(0).getCsii_itemid();
		// 获取cid
		String cid = String.valueOf(ifSBll
				.getFormulaInfo(" AND cfin_id=" + cfin_id).get(0).getCid());
		// 获取最新所属月份
		String ownmonth = String.valueOf(ifSBll
				.getCoSalaryItemID(" AND csii_itemid='" + csii_itemid + "'")
				.get(0).getOwnmonth());

		// 获取所有工资项目列表
		itemList = ifSBll.getItemFormula(cid, ownmonth, "",
				" AND csii_itemid='" + csii_itemid + "'");
	}

	// 判断选中的textbox
	@Command("changeFocus")
	public void changeFocus(@BindingParam("of") String of,
			@BindingParam("tbox") Textbox tbox,
			@BindingParam("obox") Textbox obox) {
		oFocus = of;
		tbox.setStyle("background-color:#CAE4FF;");
		obox.setStyle("background-color:#FFFFFF;");
	}

	// 提交事件
	@Command("submit")
	public void submit(
			@BindingParam("winForDataUpdate") final Window winForDataUpdate) {
		// 调用方法
		String[] message = ifOBll.updateFormulaData(cfda_id, cfin_id, formula,
				t_formula, range, t_range, username);

		// 弹出提示并跳转页面
		if (message[0].equals("0")) {
			EventListener<ClickEvent> clickListener = new EventListener<Messagebox.ClickEvent>() {
				public void onEvent(ClickEvent event) throws Exception {
					if (Messagebox.Button.OK.equals(event.getButton())) {
						// //跳转页面
						winForDataUpdate.detach();
					}
				}
			};
			// 弹出提示
			Messagebox.show(message[1], "操作提示",
					new Messagebox.Button[] { Messagebox.Button.OK },
					Messagebox.INFORMATION, clickListener);

		}
	}

	// 录入
	@Command("input")
	@NotifyChange({ "t_formula", "t_range" })
	public void input(@BindingParam("v") String v) {
		if (oFocus.equals("f")) {
			// 只能插入数字
			if (cs.isNum(v)) {
				formula = formula + v;
				t_formula = t_formula + v;
			} else {
				// 弹出提示
				Messagebox.show("只能录入纯数字！", "操作提示", Messagebox.OK,
						Messagebox.ERROR);
			}
		} else if (oFocus.equals("r")) {
			// 判断是否为纯数字
			if (cs.isNum(v)) {
				range = range + v;
				t_range = t_range + v;
			} else {
				
				// 判断是否为等于或不等于关系
				if (ifequals == 1) {// 等于关系
					range = b_content + "\""  + v + "\".equals("+ b_item+")";
				} else {// 不等于关系
					range = b_content + "!\""  + v + "\".equals("+ b_item+")";
				}
				t_range = t_range + "\"" + v + "\"";
			}
		}
	}

	// 编辑算法内容和使用范围
	@Command("calculator")
	@NotifyChange({ "t_formula", "t_range" })
	public void calculator(@BindingParam("f") String f,
			@BindingParam("t") String t, @BindingParam("type") int type) {
		if (!f.equals("清空")) {
			
			if (type == 1) {
				b_item = f; // 获取项目
				b_content = range; // 获取内容
			}

			
			if (oFocus.equals("f")) {
				formula = formula + changeFH(f, "f");
				t_formula = t_formula + changeFH(t, "t");
			} else if (oFocus.equals("r")) {
				range = range + changeFH(f, "f");
				t_range = t_range + changeFH(t, "t");
			}
		} else {
			if (oFocus.equals("f")) {
				formula = "";
				t_formula = "";
			} else if (oFocus.equals("r")) {
				range = "";
				t_range = "";
				b_item = "";
				b_content = "";
			}
		}
	}

	// 特殊符号转换
	public String changeFH(String t, String type) {
		String result = "";
		String f_result = "";
		String t_result = "";
		if (t.equals("大于")) {
			f_result = " > ";
			t_result = " > ";
		} else if (t.equals("小于")) {
			f_result = " < ";
			t_result = " > ";
		} else if (t.equals("大于等于")) {
			f_result = " >= ";
			t_result = " >= ";
		} else if (t.equals("小于等于")) {
			f_result = " <= ";
			t_result = " <= ";
		} else if (t.equals("等于")) {
			f_result = " == ";
			t_result = " = ";
		} else if (t.equals("不等于")) {
			f_result = " != ";
			t_result = " <> ";
		} else if (t.equals("and")) {
			f_result = " && ";
			t_result = " and ";
		} else if (t.equals("or")) {
			f_result = " || ";
			t_result = " or ";
		} else {
			f_result = t;
			t_result = t;
		}

		if (type.equals("f")) {
			result = f_result;
		} else if (type.equals("t")) {
			result = t_result;
		}
		return result;
	}

	public List<CoSalaryItemFormulaModel> getItemList() {
		return itemList;
	}

	public void setItemList(List<CoSalaryItemFormulaModel> itemList) {
		this.itemList = itemList;
	}

	public String getT_formula() {
		return t_formula;
	}

	public void setT_formula(String t_formula) {
		this.t_formula = t_formula;
	}

	public String getT_range() {
		return t_range;
	}

	public void setT_range(String t_range) {
		this.t_range = t_range;
	}
}
