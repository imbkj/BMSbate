package Controller.CoFinanceManage;

import java.math.BigDecimal;

import org.zkoss.bind.Binder;
import org.zkoss.bind.annotation.BindingParam;
import org.zkoss.bind.annotation.Command;
import org.zkoss.bind.annotation.ContextParam;
import org.zkoss.bind.annotation.ContextType;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Window;

import bll.CoFinanceManage.Cfma_AgencyOperateBll;

import Model.CoFinanceAgencyMonthlyBillModel;
import Util.UserInfo;

public class Cfma_AgencyBillChargeOffController {
	private final CoFinanceAgencyMonthlyBillModel billModel = (CoFinanceAgencyMonthlyBillModel) Executions
			.getCurrent().getArg().get("m");
	private String username;
	private BigDecimal imbalance;

	public Cfma_AgencyBillChargeOffController() {
		username = UserInfo.getUsername();
		imbalance = billModel.getImbalance().abs();
	}

	//账单核销
	@Command("ChargeOff")
	public void ChargeOff(@BindingParam("reason") String reason,
			@BindingParam("win") Window win,
			@ContextParam(ContextType.VIEW) Component view) {
		try {
			if (Messagebox.show("确认核销该账单吗？共核销金额：" + imbalance, "操作提示",
					Messagebox.YES | Messagebox.NO, Messagebox.QUESTION) == Messagebox.YES) {
				Cfma_AgencyOperateBll opBll = new Cfma_AgencyOperateBll();
				int i = opBll.agencyBillChargeOff(billModel.getCfab_number(),
						username, reason);
				if (i == 1) {
					Binder bind = (Binder) view.getParent().getAttribute(
							"binder");
					bind.postCommand("refreshAll", null);
					Messagebox.show("核销账单成功。", "操作提示", Messagebox.OK,
							Messagebox.NONE);
					win.detach();
				} else {
					Messagebox.show("核销账单失败。", "操作提示", Messagebox.OK,
							Messagebox.NONE);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			Messagebox.show("核销账单出错。", "操作提示", Messagebox.OK,
					Messagebox.NONE);
		}
	}

	public CoFinanceAgencyMonthlyBillModel getBillModel() {
		return billModel;
	}

	public String getUsername() {
		return username;
	}

	public BigDecimal getImbalance() {
		return imbalance;
	}

}
