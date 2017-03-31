package Controller.SalaryPaper;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.zkoss.bind.Binder;
import org.zkoss.bind.annotation.BindingParam;
import org.zkoss.bind.annotation.Command;
import org.zkoss.bind.annotation.ContextParam;
import org.zkoss.bind.annotation.ContextType;
import org.zkoss.bind.annotation.NotifyChange;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Window;

import Model.CoSalaryItemInfoModel;
import Model.SalaryPaperCoModel;
import bll.SalaryPaper.SalaryPaperBll;

/**
 * 工资单公司设置
 * 
 * @author Administrator
 * 
 */
public class SalaryPaper_CoSetInfoController {

	private SalaryPaperCoModel m = (SalaryPaperCoModel) Executions.getCurrent()
			.getArg().get("m");
	private SalaryPaperBll spb = new SalaryPaperBll();

	public SalaryPaper_CoSetInfoController() {

	}

	// 保存公司设置
	@Command
	public void btnset(@BindingParam("win") Window win,
			@ContextParam(ContextType.VIEW) Component view) {
		boolean flag1 = true;
		boolean flag2 = true;
		if (m.getCoss_StringSectetend().equals("是")
				&& (m.getCoss_secretsendaddress() == null)) {
			flag1 = false;
			Messagebox.show("请设置暗送地址", "操作提示", Messagebox.OK, Messagebox.ERROR);
		}
		if (m.getCoss_StringcarbonCopy().equals("是")
				&& (m.getCoss_ccaddress().equals("") || m.getCoss_ccaddress() == null)) {
			flag2 = false;
			Messagebox.show("请设置抄送地址", "操作提示", Messagebox.OK, Messagebox.ERROR);
		}
		Binder bind = (Binder) view.getParent().getAttribute("binder");
		bind.postCommand("sCoSetList", null);
		if (flag1 && flag2) {
			boolean flag = spb.coInfoSet(m);
			if (flag) {
				win.detach();
				Messagebox.show("设置成功", "操作提示", Messagebox.OK, Messagebox.NONE);
			} else {
				Messagebox
						.show("设置失败", "操作提示", Messagebox.OK, Messagebox.ERROR);
			}
		}

	}

	public SalaryPaperCoModel getM() {
		return m;
	}

	public void setM(SalaryPaperCoModel m) {
		this.m = m;
	}

}
