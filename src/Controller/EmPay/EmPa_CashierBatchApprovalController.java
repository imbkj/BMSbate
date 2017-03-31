package Controller.EmPay;

import impl.WorkflowCore.WfOperateImpl;

import java.util.ArrayList;
import java.util.List;

import org.zkoss.bind.annotation.BindingParam;
import org.zkoss.bind.annotation.Command;
import org.zkoss.bind.annotation.NotifyChange;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Window;

import service.WorkflowCore.WfBusinessService;
import service.WorkflowCore.WfOperateService;

import Model.EmPayModel;
import Model.EmPayTaskListModel;
import Util.UserInfo;
import bll.EmPay.EmPa_OperateBll;
import bll.EmPay.EmPa_SelectBll;
import bll.EmPay.EmPa_TaskService;
import bll.EmPay.EmPa_TaskServiceImpl;

public class EmPa_CashierBatchApprovalController {

	private List<EmPayModel> list = new ArrayList<EmPayModel>();
	private EmPayModel model;
	private EmPayTaskListModel tm = null;
	private EmPa_SelectBll bll = new EmPa_SelectBll();
	private EmPa_OperateBll obll = new EmPa_OperateBll();

	public EmPa_CashierBatchApprovalController() {
		if (Executions.getCurrent().getArg().get("model") != null) {
			model = (EmPayModel) Executions.getCurrent().getArg().get("model");
			list = bll.getEmPayList(" and empa_number='"
					+ model.getEmpa_number() + "'");
			tm = bll.getEmPayTaskListByNumber(model.getEmpa_number());
			model.setPatkId(tm.getPatk_id());
			model.setTaprId(tm.getPatk_tapr_id());

		}
	}

	@Command
	@NotifyChange("list")
	public void submit(@BindingParam("win") Window win) {

		// 任务单处理
		model.setApprovalType("出纳");
		WfBusinessService wfbs = new EmPa_TaskServiceImpl();
		WfOperateService wfs = new WfOperateImpl(wfbs);

		Object[] obj = { "审核", model };
		String[] str = wfs.PassToNext(obj, model.getTaprId(),
				UserInfo.getUsername(), "", 0, "");
		if (str[0].equals("1")) {
			Messagebox
					.show("提交成功", "提示", Messagebox.OK, Messagebox.INFORMATION);
			win.detach();
		} else {
			Messagebox.show("提交失败", "提示", Messagebox.OK, Messagebox.ERROR);
		}
	}

	@Command
	@NotifyChange("list")
	public void delete(@BindingParam("model") EmPayModel model) {
		list.remove(model);
	}

	public List<EmPayModel> getList() {
		return list;
	}

}
