package Controller.CoFinanceManage;

import org.zkoss.bind.Binder;
import org.zkoss.bind.annotation.BindingParam;
import org.zkoss.bind.annotation.Command;
import org.zkoss.bind.annotation.ContextParam;
import org.zkoss.bind.annotation.ContextType;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Window;

import bll.CoFinanceManage.Cfma_BillSelBll;
import bll.CoFinanceManage.Cfma_CollectOperateBll;

import Model.CoFinanceMonthlyBillModel;
import Util.UserInfo;

public class Cfma_BillCarryForwardController {
	private final String billNo = Executions.getCurrent().getArg()
			.get("billNo").toString();

	private CoFinanceMonthlyBillModel billModel;
	private String username;

	public Cfma_BillCarryForwardController() {
		Cfma_BillSelBll bll = new Cfma_BillSelBll();
		billModel = bll.getBillModel(billNo);
		username = UserInfo.getUsername();
		bll = null;
	}

	// 账单结转
	@Command("CarryForward")
	public void CarryForward(@BindingParam("win") Window win,
			@BindingParam("remark") String remark,
			@ContextParam(ContextType.VIEW) Component view) {
		try {
			Cfma_CollectOperateBll opbll = new Cfma_CollectOperateBll();
			int i = opbll.BillCarryForward(billModel.getCfmb_number(),
					billModel.getCid(), billModel.getOwnmonth(), remark,
					username);
			if (i == 1) {
				Binder bind = (Binder) view.getParent().getAttribute("binder");
				bind.postCommand("refreshWin", null);
				Messagebox.show("账单结转成功。", "操作提示", Messagebox.OK,
						Messagebox.NONE);
				win.detach();
			} else {
				Messagebox.show("账单结转失败。", "操作提示", Messagebox.OK,
						Messagebox.INFORMATION);
			}
		} catch (Exception e) {
			Messagebox.show("账单结转出错。", "操作提示", Messagebox.OK, Messagebox.ERROR);
		}
	}

	public CoFinanceMonthlyBillModel getBillModel() {
		return billModel;
	}

	public String getUsername() {
		return username;
	}

}
