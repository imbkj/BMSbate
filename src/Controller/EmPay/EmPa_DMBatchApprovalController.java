package Controller.EmPay;

import impl.WorkflowCore.WfOperateImpl;

import java.math.BigDecimal;
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

import bll.EmPay.EmPa_OperateBll;
import bll.EmPay.EmPa_SelectBll;
import bll.EmPay.EmPa_TaskService;
import bll.EmPay.EmPa_TaskServiceImpl;

import Model.EmPayModel;
import Model.EmPayTaskListModel;
import Model.TaskProcessViewModel;
import Util.UserInfo;

public class EmPa_DMBatchApprovalController {
	private List<EmPayModel> list = new ArrayList<EmPayModel>();
	private EmPayTaskListModel tm = null;
	private EmPayModel model;
	private BigDecimal total = new BigDecimal(0);
	private EmPa_SelectBll bll = new EmPa_SelectBll();
	private String nextrole;
	private Integer step = 0;

	public EmPa_DMBatchApprovalController() {

		if (Executions.getCurrent().getArg().get("model") != null) {
			model = (EmPayModel) Executions.getCurrent().getArg().get("model");
			list = bll.getEmPayList(" and empa_number='"
					+ model.getEmpa_number() + "'");
			tm = bll.getEmPayTaskListByNumber(model.getEmpa_number());
			model.setPatkId(tm.getPatk_id());
			model.setTaprId(tm.getPatk_tapr_id());
			for (EmPayModel m : list) {
				total = total.add(m.getEmpa_fee());
			}
		}

		if (UserInfo.getDepID().equals("9")) {
			nextrole = "财务";
		} else {
			nextrole = "总经理助理";
		}
	}

	@Command
	@NotifyChange("list")
	public void submit(@BindingParam("win") Window win) {

		// 任务单处理
		model.setApprovalType("部门经理");
		WfBusinessService wfbs = new EmPa_TaskServiceImpl();
		WfOperateService wfs = new WfOperateImpl(wfbs);
		
		if (nextrole.equals("财务")) {
			step = 5;
			model.setCheckState(41);
		} else {
			step = 4;
			model.setCheckState(3);
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

	// 部门经理退回
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

	@Command
	@NotifyChange("list")
	public void delete(@BindingParam("model") EmPayModel model) {
		list.remove(model);
	}

	public List<EmPayModel> getList() {
		return list;
	}

	public BigDecimal getTotal() {
		return total;
	}

	public void setTotal(BigDecimal total) {
		this.total = total;
	}

	public String getNextrole() {
		return nextrole;
	}

	public void setNextrole(String nextrole) {
		this.nextrole = nextrole;
	}

}
