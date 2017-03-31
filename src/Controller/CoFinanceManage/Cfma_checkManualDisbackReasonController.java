package Controller.CoFinanceManage;

import impl.WorkflowCore.WfOperateImpl;

import org.zkoss.bind.annotation.BindingParam;
import org.zkoss.bind.annotation.Command;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Window;

import Model.CoFinanceManualDisposableModel;
import bll.CoFinanceManage.Cfma_ManualDisSpOperateBll;

/**
 * 深刻手动添加非标退回
 * 
 * @author Administrator
 * 
 */
public class Cfma_checkManualDisbackReasonController {

	private String backReason;

	private int taprid = (Integer) Executions.getCurrent().getArg()
			.get("taprid");
	private int daid = (Integer) Executions.getCurrent().getArg().get("daid");

	public Cfma_checkManualDisbackReasonController() {

	}

	// 提交退回
	@Command
	public void submit(@BindingParam("wins") Window win) {
		if (backReason != null) {
			CoFinanceManualDisposableModel m = new CoFinanceManualDisposableModel();

			m.setCfmd_state(3);
			m.setCfmd_backreason(backReason);
			m.setCfmd_id(daid);
			m.setCfmd_tapr_id(taprid);
			Cfma_ManualDisSpOperateBll operabll = new Cfma_ManualDisSpOperateBll();
			String[] str = operabll.back(m,"公司退回");
			if (str[0].equals("1")) {
				Messagebox.show(str[1], "操作提示", Messagebox.OK,
						Messagebox.INFORMATION);
				win.detach();
			} else {
				Messagebox
						.show(str[1], "操作提示", Messagebox.OK, Messagebox.ERROR);
			}
		} else {
			Messagebox
					.show("请填写退回原因!", "操作提示", Messagebox.OK, Messagebox.ERROR);
		}
	}

	public String getBackReason() {
		return backReason;
	}

	public void setBackReason(String backReason) {
		this.backReason = backReason;
	}

}
