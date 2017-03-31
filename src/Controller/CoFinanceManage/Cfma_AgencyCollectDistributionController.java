package Controller.CoFinanceManage;

import java.math.BigDecimal;
import java.text.DecimalFormat;

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

import bll.CoFinanceManage.Cfma_AgencyOperateBll;
import bll.CoFinanceManage.Cfma_AgencySelBll;

import Model.CoFinanceAgencyMonthlyBillModel;
import Util.UserInfo;

public class Cfma_AgencyCollectDistributionController {
	private final String cfab_number = Executions.getCurrent().getArg()
			.get("cfab_number").toString();
	private CoFinanceAgencyMonthlyBillModel cfabModel;
	private String username;
	private BigDecimal amount;

	public Cfma_AgencyCollectDistributionController() {
		Cfma_AgencySelBll selBll = new Cfma_AgencySelBll();
		cfabModel = selBll.getAgencyMonthlyBillModel(cfab_number);
		username = UserInfo.getUsername();
		amount = BigDecimal.ZERO;
	}

	// 提交领款
	@Command("SubmitCollectDis")
	public void SubmitCollectDis(@BindingParam("win") Window win,
			@BindingParam("remark") String remark,
			@ContextParam(ContextType.VIEW) Component view) {
		DecimalFormat df = new DecimalFormat("#.00");
		amount = new BigDecimal(df.format(amount));
		if (amount.compareTo(BigDecimal.ZERO) != 1) {
			Messagebox.show("您领取的金额有误，请确认领取金额后再提交。", "操作提示", Messagebox.OK,
					Messagebox.INFORMATION);
		} else if (amount.compareTo(cfabModel.getCfat_Balance()) == 1) {
			Messagebox.show("您领取的金额过大，已超出可领金额。", "操作提示", Messagebox.OK,
					Messagebox.INFORMATION);
		} else if (amount.compareTo(cfabModel.getImbalance().abs()) == 1) {
			Messagebox.show("您领取的金额过大，已超出账单差额。", "操作提示", Messagebox.OK,
					Messagebox.INFORMATION);
		} else {
			// 提交领款
			Cfma_AgencyOperateBll opBll = new Cfma_AgencyOperateBll();
			int i = opBll.addAgencyDrawMoney(cfab_number,
					cfabModel.getCfab_coab_id(), amount, username, remark);
			if (i == 1) {
				Binder bind = (Binder) view.getParent().getAttribute("binder");
				bind.postCommand("refreshAll", null);
				Messagebox
						.show("领款成功。", "操作提示", Messagebox.OK, Messagebox.NONE);
				win.detach();
			} else {
				Messagebox.show("领款出错。", "操作提示", Messagebox.OK,
						Messagebox.ERROR);
			}
		}
	}

	// 自动填写领款总额(填写账单余额)
	@Command("autoImbalance")
	@NotifyChange("amount")
	public void autoImbalance() {
		amount = cfabModel.getImbalance().abs();
	}

	// 自动填写领款总额(填写可领金额)
	@Command("autoBalance")
	@NotifyChange("amount")
	public void autoBalance() {
		amount = cfabModel.getCfat_Balance();
	}

	public BigDecimal getAmount() {
		return amount;
	}

	public void setAmount(BigDecimal amount) {
		this.amount = amount;
	}

	public CoFinanceAgencyMonthlyBillModel getCfabModel() {
		return cfabModel;
	}

	public String getUsername() {
		return username;
	}

}
