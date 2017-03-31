package Controller.SalaryPaper;

import java.util.ArrayList;
import java.util.List;

import org.zkoss.bind.Binder;
import org.zkoss.bind.annotation.BindingParam;
import org.zkoss.bind.annotation.Command;
import org.zkoss.bind.annotation.ContextParam;
import org.zkoss.bind.annotation.ContextType;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zul.Checkbox;
import org.zkoss.zul.Grid;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Radio;
import org.zkoss.zul.Radiogroup;
import org.zkoss.zul.Window;

import Model.CoSalaryMbModel;
import Model.EmSalaryInfoModel;
import bll.SalaryPaper.SalaryPaperBll;

/**
 * 设置工资单模版
 * 
 * @author Administrator
 * 
 */
public class SalaryPaper_setModelController {

	private EmSalaryInfoModel m = (EmSalaryInfoModel) Executions.getCurrent()
			.getArg().get("m");
	private List<CoSalaryMbModel> csmList;
	private List<CoSalaryMbModel> csmList1 = new ArrayList<CoSalaryMbModel>();
	private List<CoSalaryMbModel> csmList2 = new ArrayList<CoSalaryMbModel>();
	private SalaryPaperBll spb = new SalaryPaperBll();

	public SalaryPaper_setModelController() {
		init();
	}

	// 初始化
	public void init() {
		csmList = spb.getCsmList();
		for (int i = 0; i < csmList.size(); i++) {
			if (csmList.get(i).getCosm_state() == 1) {
				csmList1.add(csmList.get(i));
			} else {
				csmList2.add(csmList.get(i));
			}
		}
	}

	@Command
	public void createInfo(@BindingParam("grid") Grid grid,
			@BindingParam("radio") Radiogroup radio) {
		String cm = m.getEsin_cosm_model(); // 获得发送方式
		String[] str = null;
		if (cm != null) {
			str = cm.split(","); // 拆分字符串 返回一个字符串数组
			for (int i = 0; i < csmList2.size(); i++) {
				Checkbox c = (Checkbox) grid.getCell(i, 0).getChildren().get(0); // 得到所有的checkbox对象
				for (int j = 0; j < str.length; j++) {
					if (csmList2.get(i).getCosm_id() == (Integer.valueOf(str[j]
							.toString()))) { // 判断发送方式是否相同
						c.setChecked(true); // 存在相同的发送方式则自动勾选
					}
				}
			}
			for (int i = 0; i < csmList1.size(); i++) {
				Radio r = (Radio) radio.getChildren().get(i);
				for (int j = 0; j < str.length; j++) {
					if (csmList1.get(i).getCosm_id() == (Integer.valueOf(str[j]
							.toString()))) {
						r.setChecked(true);
					}
				}
			}
		}
	}

	@Command
	public void commit(@BindingParam("grid") Grid grid,
			@BindingParam("win") Window win,
			@ContextParam(ContextType.VIEW) Component view,
			@BindingParam("radio") Radiogroup radio) {
		List<CoSalaryMbModel> l = new ArrayList<CoSalaryMbModel>();
		// 获取到勾选的发送方式
		for (int i = 0; i < csmList2.size(); i++) {
			Checkbox c = (Checkbox) grid.getCell(i, 0).getChildren().get(0); // 得到所有的checkbox对象
			if (c.isChecked() == true) {
				l.add((CoSalaryMbModel) c.getValue());
			}
		}
		// 获取选择的radio
		for (int i = 0; i < csmList1.size(); i++) {
			Radio r = (Radio) radio.getChildren().get(i);
			if (r.isChecked() == true) {
				l.add((CoSalaryMbModel) r.getValue());
			}
		}
		if (l.size() > 0) {
			boolean flag = spb.setModel(m, l);
			if (flag) {
				Binder bind = (Binder) view.getParent().getAttribute("binder");
				bind.postCommand("esinfoList", null);
				Messagebox.show("设置成功", "操作提示", Messagebox.OK, Messagebox.NONE);
				win.detach();
			}
		} else {
			Messagebox.show("请选择模版", "操作提示", Messagebox.OK, Messagebox.ERROR);
		}

	}

	public EmSalaryInfoModel getM() {
		return m;
	}

	public void setM(EmSalaryInfoModel m) {
		this.m = m;
	}

	public List<CoSalaryMbModel> getCsmList1() {
		return csmList1;
	}

	public void setCsmList1(List<CoSalaryMbModel> csmList1) {
		this.csmList1 = csmList1;
	}

	public List<CoSalaryMbModel> getCsmList2() {
		return csmList2;
	}

	public void setCsmList2(List<CoSalaryMbModel> csmList2) {
		this.csmList2 = csmList2;
	}

}
