package Controller.EmSalary;

import java.util.List;

import org.zkoss.bind.annotation.BindingParam;
import org.zkoss.bind.annotation.Command;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.event.EventListener;
import org.zkoss.zul.Combobox;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Messagebox.ClickEvent;
import org.zkoss.zul.Window;

import bll.EmSalary.EmSalary_SalarySelBll;
import bll.EmSalary.ItemFormula_OperateBll;
import bll.EmSalary.ItemFormula_SelectBll;

import Model.CoFormulaInfoModel;
import Model.CoSalaryItemIDInfoModel;
import Model.EmBaseESInCFInModel;
import Model.EmSalaryInfoModel;
import Util.UserInfo;

public class EmSalary_ForEmAssignController {
	private EmSalary_SalarySelBll esBll = new EmSalary_SalarySelBll();
	private ItemFormula_SelectBll ifSBll = new ItemFormula_SelectBll();
	private ItemFormula_OperateBll ifOBll = new ItemFormula_OperateBll();

	private int tapr_id = Integer.parseInt(Executions.getCurrent().getArg()
			.get("id").toString());
	private int esin_id = Integer.parseInt(Executions.getCurrent().getArg()
			.get("daid").toString());

	// 获取用户名
	String username = UserInfo.getUsername();
	private List<CoFormulaInfoModel> formulaList;// 算法列表
	private List<EmSalaryInfoModel> esinList;// 薪酬信息列表
	private int gid;
	private int cid;
	private String company;
	private String name;

	public EmSalary_ForEmAssignController() {
		// 获取员工薪酬信息
		esinList = esBll.getEmSalaryInfo(esin_id);
		gid = esinList.get(0).getGid();
		company = esinList.get(0).getCompany();
		name = esinList.get(0).getName();
		cid = esinList.get(0).getCid();

		// 获取项目组合id
		String csii_itemid = "";
		List<CoSalaryItemIDInfoModel> csiiList;
		csiiList = ifSBll.getCoSalaryItemID(" AND cid=" + cid);
		if (csiiList.size() > 0) {
			csii_itemid = csiiList.get(0).getCsii_itemid();
		} else {
			// 弹出提示
			Messagebox.show("请先设置好公司算法！", "操作提示", Messagebox.OK,
					Messagebox.ERROR);
		}

		// 获取算法列表
		formulaList = ifSBll.getFormulaInfo(" AND csii_itemid='" + csii_itemid
				+ "'");
	}

	@Command("submit")
	public void submit(@BindingParam("w1") final Window w1,
			@BindingParam("formula") Combobox formula) {
		if (formula.getSelectedItem() != null) {

			EmBaseESInCFInModel m = new EmBaseESInCFInModel();
			m.setEsin_id(String.valueOf(esin_id));
			m.setCid(cid);
			m.setGid(gid);
			m.setCfin_id(formula.getSelectedItem().getValue().toString());
			m.setEsin_addname(username);

			// 调用方法
			String[] message = ifOBll.finishSalaryInfoWF(m, tapr_id);

			// 弹出提示并跳转页面
			if (message[0].equals("1")) {
				EventListener<ClickEvent> clickListener = new EventListener<Messagebox.ClickEvent>() {
					public void onEvent(ClickEvent event) throws Exception {
						if (Messagebox.Button.OK.equals(event.getButton())) {
							w1.detach();
						}
					}
				};
				// 弹出提示
				Messagebox.show(message[1], "操作提示",
						new Messagebox.Button[] { Messagebox.Button.OK },
						Messagebox.INFORMATION, clickListener);
			} else {
				// 弹出提示
				Messagebox.show(message[1], "操作提示", Messagebox.OK,
						Messagebox.ERROR);
			}

		} else {
			// 弹出提示
			Messagebox.show("请选择算法！", "操作提示", Messagebox.OK, Messagebox.ERROR);
		}
	}

	public int getGid() {
		return gid;
	}

	public void setGid(int gid) {
		this.gid = gid;
	}

	public String getCompany() {
		return company;
	}

	public void setCompany(String company) {
		this.company = company;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public List<CoFormulaInfoModel> getFormulaList() {
		return formulaList;
	}

	public void setFormulaList(List<CoFormulaInfoModel> formulaList) {
		this.formulaList = formulaList;
	}

}
