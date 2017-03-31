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

import Model.EmSalaryInfoModel;
import Model.SalarySendTypeModel;
import bll.SalaryPaper.SalaryPaperBll;

/**
 * 批量设置发送方式
 * 
 * @author Administrator
 * 
 */
public class SalaryPaper_PlSetSpController {
	private List<EmSalaryInfoModel> allList = (List<EmSalaryInfoModel>) Executions
			.getCurrent().getArg().get("l");
	private SalaryPaperBll spb = new SalaryPaperBll();
	private List<SalarySendTypeModel> stmList;
	private List<SalarySendTypeModel> stmList1 = new ArrayList<SalarySendTypeModel>();
	private List<SalarySendTypeModel> stmList2 = new ArrayList<SalarySendTypeModel>();

	public SalaryPaper_PlSetSpController() {
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

	// 提交设置
	@Command
	public void commit(@BindingParam("grid") Grid grid,
			@BindingParam("win") Window win,
			@ContextParam(ContextType.VIEW) Component view,
			@BindingParam("radio") Radiogroup radio) {
		List<String> l = new ArrayList<String>();
		// 获取到勾选的发送方式
		for (int i = 0; i < stmList2.size(); i++) {
			Checkbox c = (Checkbox) grid.getCell(i, 0).getChildren().get(0); // 得到所有的checkbox对象
			if (c.isChecked() == true) {
				l.add(c.getLabel().toString());
			}
		}
		// 获取选择的radio
		for (int i = 0; i < stmList1.size(); i++) {
			Radio r = (Radio) radio.getChildren().get(i);
			if (r.isChecked() == true) {
				l.add(r.getLabel().toString());
			}
		}
		if (l.size() > 0) {
			boolean flag = spb.setPlSendType(l, allList);
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

}
