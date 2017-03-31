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
import org.zkoss.zul.Textbox;
import org.zkoss.zul.Window;

import Model.EmSalaryInfoModel;
import Model.SalarySendTypeModel;
import bll.SalaryPaper.SalaryPaperBll;

/**
 * 设置发送方式
 * 
 * @author Administrator
 * 
 */
public class SalaryPaper_setSpController {

	private EmSalaryInfoModel m = (EmSalaryInfoModel) Executions.getCurrent()
			.getArg().get("m");
	private SalaryPaperBll spb = new SalaryPaperBll();
	private List<SalarySendTypeModel> stmList;
	private List<SalarySendTypeModel> stmList1 = new ArrayList<SalarySendTypeModel>();
	private List<SalarySendTypeModel> stmList2 = new ArrayList<SalarySendTypeModel>();

	public SalaryPaper_setSpController() {

		init();
	}

	// 初始化
	public void init() {
		stmList = spb.getStmList();
		for (int i = 0; i < stmList.size(); i++) {
			if (stmList.get(i).getCost_state() == 1) {        
				stmList1.add(stmList.get(i));
			} else {
				stmList2.add(stmList.get(i));
			}
		}

	}

	@Command
	public void createInfo(@BindingParam("grid") Grid grid,
			@BindingParam("radio") Radiogroup radio) {
		String sendType = m.getEsin_cost_Type(); // 获得发送方式
		// String url = m.getEsin_cosm_typeurl() + " "; // 消除越界异常
		// String[] urlstr = null;
		// if (url != null) {
		// urlstr = url.split(",");
		// }

		String[] str = null;
		if (sendType != null) {
			str = sendType.split(","); // 拆分字符串 返回一个字符串数组
			for (int i = 0; i < stmList2.size(); i++) {
				// Textbox t = (Textbox) grid.getCell(i,
				// 1).getChildren().get(0);
				Checkbox c = (Checkbox) grid.getCell(i, 0).getChildren().get(0); // 得到所有的checkbox对象
				for (int j = 0; j < str.length; j++) {
					if (stmList2.get(i).getCost_name()
							.equals(str[j].toString())) { // 判断发送方式是否相同
						c.setChecked(true); // 存在相同的发送方式则自动勾选
						// t.setReadonly(false);
						// if (urlstr.length > 0) {
						// stmList.get(i).setCost_url(urlstr[j]);
						// }
					}
				}
			}
			for (int i = 0; i < stmList1.size(); i++) {
				Radio r = (Radio) radio.getChildren().get(i);
				for (int j = 0; j < str.length; j++) {
					if (stmList1.get(i).getCost_name()
							.equals(str[j].toString())) {
						r.setChecked(true);
					}
				}
			}
		}
	}

	// // 获取发送地址
	// @Command
	// public void inputUrl(@BindingParam("grid") Grid grid) {
	// for (int i = 0; i < stmList.size(); i++) {
	// Textbox t = (Textbox) grid.getCell(i, 1).getChildren().get(0);
	// Checkbox c = (Checkbox) grid.getCell(i, 0).getChildren().get(0); //
	// 得到所有的checkbox对象
	// if (c.isChecked() == true) {
	// t.setReadonly(false);
	// } else {
	// t.setReadonly(true);
	// }
	// }
	// }

	// 提交设置
	@Command
	public void commit(@BindingParam("grid") Grid grid,
			@BindingParam("win") Window win,
			@ContextParam(ContextType.VIEW) Component view,
			@BindingParam("radio") Radiogroup radio) {
		List<String> l = new ArrayList<String>();
		// List<String> urlList = new ArrayList<String>();
		// 获取到勾选的发送方式
		for (int i = 0; i < stmList2.size(); i++) {
			Checkbox c = (Checkbox) grid.getCell(i, 0).getChildren().get(0); // 得到所有的checkbox对象
			// Textbox t = (Textbox) grid.getCell(i, 1).getChildren().get(0);
			if (c.isChecked() == true) {
				l.add(c.getLabel().toString());
			}
			// if (t.isReadonly() == false) {
			// urlList.add(t.getValue().toString());
			// }
		}
		// 获取选择的radio
		for (int i = 0; i < stmList1.size(); i++) {
			Radio r = (Radio) radio.getChildren().get(i);
			if (r.isChecked() == true) {
				l.add(r.getLabel().toString());
			}
		}
		if (l.size() > 0) {
			boolean flag = spb.setSendType(l, m);
			if (flag) {
				Binder bind = (Binder) view.getParent().getAttribute("binder");
				bind.postCommand("esinfoList", null);
				Messagebox.show("设置成功", "操作提示", Messagebox.OK, Messagebox.NONE);
				win.detach();
			}
		} else {
			Messagebox.show("请选择发送方式", "操作提示", Messagebox.OK, Messagebox.ERROR);
		}
	}

	public List<SalarySendTypeModel> getStmList1() {
		return stmList1;
	}

	public void setStmList1(List<SalarySendTypeModel> stmList1) {
		this.stmList1 = stmList1;
	}

	public List<SalarySendTypeModel> getStmList2() {
		return stmList2;
	}

	public void setStmList2(List<SalarySendTypeModel> stmList2) {
		this.stmList2 = stmList2;
	}

	public EmSalaryInfoModel getM() {
		return m;
	}

	public void setM(EmSalaryInfoModel m) {
		this.m = m;
	}

	public List<SalarySendTypeModel> getStmList() {
		return stmList;
	}

	public void setStmList(List<SalarySendTypeModel> stmList) {
		this.stmList = stmList;
	}
}
