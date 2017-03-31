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
import Model.TaskProcessViewModel;
import Util.UserInfo;
import bll.EmPay.EmPa_OperateBll;
import bll.EmPay.EmPa_SelectBll;
import bll.EmPay.EmPa_TaskService;
import bll.EmPay.EmPa_TaskServiceImpl;

public class EmPa_FinanceBatchApprovalController {
	private List<EmPayModel> list = new ArrayList<EmPayModel>();;
	private EmPayModel model = null;
	private EmPayTaskListModel tm = null;
	private EmPa_SelectBll bll = new EmPa_SelectBll();
	private String nextrole;
	private Integer step = 0;

	public EmPa_FinanceBatchApprovalController() {

		if (Executions.getCurrent().getArg().get("model") != null) {
			model = (EmPayModel) Executions.getCurrent().getArg().get("model");
			list = bll.getEmPayList(" and empa_number='"
					+ model.getEmpa_number() + "'");
			tm = bll.getEmPayTaskListByNumber(model.getEmpa_number());
			model.setPatkId(tm.getPatk_id());
			model.setTaprId(tm.getPatk_tapr_id());
		}
		nextrole="总经理";
	}

	@Command
	@NotifyChange("list")
	public void submit(@BindingParam("win") Window win) {
		if (nextrole == null || nextrole.equals("")) {
			Messagebox
					.show("请选择下一步操作角色", "提示", Messagebox.OK, Messagebox.ERROR);
			return;
		}
		// 任务单处理
		model.setApprovalType("财务");
		WfBusinessService wfbs = new EmPa_TaskServiceImpl();
		WfOperateService wfs = new WfOperateImpl(wfbs);
		
		if (nextrole.equals("总经理")) {
			if (model.getEmpa_number().contains("报销")) {
				step = 7;
				model.setCheckState(7);
			}else {
				step=6;
				model.setCheckState(7);
			}
			
		} else {
			if (model.getEmpa_number().contains("报销")) {
				step = 9;
				model.setCheckState(8);
			}else {
				step = 7;
				model.setCheckState(8);
			}
			
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

	// 财务退回
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

	public List<EmPayModel> getList() {
		return list;
	}

	public String getNextrole() {
		return nextrole;
	}

	public void setNextrole(String nextrole) {
		this.nextrole = nextrole;
	}

}
