package Controller.EmPay;

import impl.WorkflowCore.WfOperateImpl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
import bll.EmPay.EmPa_SelectBll;
import bll.EmPay.EmPa_TaskServiceImpl;

public class EmPa_assApprovalController {
	private List<EmPayModel> list = new ArrayList<EmPayModel>();
	private EmPayModel model = new EmPayModel();
	private EmPayTaskListModel tm = null;
	private Integer step = 0;

	private EmPa_SelectBll bll = new EmPa_SelectBll();

	public EmPa_assApprovalController() {
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
	public void submit(@BindingParam("win") Window win) {

		// 任务单处理
		model.setApprovalType("总经理助理");
		WfBusinessService wfbs = new EmPa_TaskServiceImpl();
		WfOperateService wfs = new WfOperateImpl(wfbs);

		if (model.getEmpa_paytype().equals("报销")) {
			step = 5;
			model.setCheckState(41);
		} else {
			step = 5;
			model.setCheckState(42);
		}
		Object[] obj = { "审核", model };
		String[] str = wfs.SkipToN(obj, model.getTaprId(), step,
				UserInfo.getUsername(), "", 0, "");
		if (str[0].equals("1")) {
			Messagebox
					.show("提交成功", "提示", Messagebox.OK, Messagebox.INFORMATION);
			win.detach();
		} else {
			Messagebox.show("提交失败", "提示", Messagebox.OK, Messagebox.ERROR);
		}
	}

	// 总经理助理退回
	@Command
	@NotifyChange("list")
	public void singleBack(@BindingParam("win") Window win) {
		Map map = new HashMap<>();
		map.put("m", model);
		Window window = (Window) Executions.createComponents(
				"../EmPay/Empa_PayBack.zul", null, map);
		window.doModal();
		if (model.isChange()) {
			win.detach();
		}
	}

	public EmPayModel getModel() {
		return model;
	}

	public void setModel(EmPayModel model) {
		this.model = model;
	}

	public List<EmPayModel> getList() {
		return list;
	}

	public void setList(List<EmPayModel> list) {
		this.list = list;
	}
	
	
}
