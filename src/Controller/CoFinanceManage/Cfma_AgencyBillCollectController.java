package Controller.CoFinanceManage;

import java.math.BigDecimal;
import java.text.DecimalFormat;

import org.zkoss.bind.Binder;
import org.zkoss.bind.annotation.BindingParam;
import org.zkoss.bind.annotation.Command;
import org.zkoss.bind.annotation.ContextParam;
import org.zkoss.bind.annotation.ContextType;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Window;

import Model.CoFinanceAgencyMonthlyBillModel;
import Util.UserInfo;
import bll.CoFinanceManage.Cfma_AgencyOperateBll;
import bll.CoFinanceManage.Cfma_AgencySelBll;

public class Cfma_AgencyBillCollectController {
	private final String cfab_number = Executions.getCurrent().getArg()
			.get("cfab_number").toString();
	private CoFinanceAgencyMonthlyBillModel mbModel;
	private String username;

	public Cfma_AgencyBillCollectController() {
		Cfma_AgencySelBll bll = new Cfma_AgencySelBll();
		// 根据cid查账单号
		
		 mbModel = bll.getAgencyMonthlyBillModel(cfab_number);
		username = UserInfo.getUsername();
	}

	// 添加账单收款
	@Command("addCollect")
	public void addCollect(@BindingParam("paidin") BigDecimal paidin,
			@BindingParam("remark") String remark,
			@BindingParam("win") Window win,
			@ContextParam(ContextType.VIEW) Component view) {
		try {
			if (paidin.compareTo(BigDecimal.ZERO) == 1) {
				DecimalFormat df = new DecimalFormat("#.00");
				paidin = new BigDecimal(df.format(paidin));
				if (Messagebox.show("请核对收款金额：" + paidin, "操作提示", Messagebox.YES
						| Messagebox.NO, Messagebox.QUESTION) == Messagebox.YES) {
					Cfma_AgencyOperateBll opbll = new Cfma_AgencyOperateBll();
					String[] message = opbll.addAgencyBillCollect(
							mbModel.getCfab_coab_id(),
							mbModel.getCfab_number(), paidin, username, remark);
					if ("1".equals(message[0])) {
						Binder bind = (Binder) view.getParent().getAttribute(
								"binder");
						bind.postCommand("refreshAll", null);
						Messagebox.show(message[1], "操作提示", Messagebox.OK,
								Messagebox.NONE);
						win.detach();
					} else {
						Messagebox.show(message[1], "操作提示", Messagebox.OK,
								Messagebox.ERROR);
					}
				}
			} else {
				Messagebox.show("您领取的金额有误，请确认领取金额后再提交。", "操作提示", Messagebox.OK,
						Messagebox.INFORMATION);
			}

		} catch (Exception e) {
			Messagebox.show("添加收款出错。", "操作提示", Messagebox.OK, Messagebox.ERROR);
		}

	}

	public String getUsername() {
		return username;
	}

	public CoFinanceAgencyMonthlyBillModel getMbModel() {
		return mbModel;
	}

}
