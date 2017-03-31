package Controller.EmPay;

import impl.WorkflowCore.WfOperateImpl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.zkoss.bind.annotation.BindingParam;
import org.zkoss.bind.annotation.Command;
import org.zkoss.bind.annotation.NotifyChange;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zul.ListModelList;
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

public class Empa_ClientManagerApprovalController {
	private List<EmPayModel> list = new ListModelList<EmPayModel>();
	private EmPayTaskListModel tm = null;
	private EmPayModel model = null;
	private EmPa_SelectBll bll = new EmPa_SelectBll();
	private EmPa_OperateBll obll = new EmPa_OperateBll();
	

	public Empa_ClientManagerApprovalController() {

		if (Executions.getCurrent().getArg().get("model") != null) {
			model = (EmPayModel) Executions.getCurrent().getArg().get("model");
			list = bll.getEmPayList(" and empa_number='" + model.getEmpa_number()
					+ "'");
			tm = bll.getEmPayTaskListByNumber(model.getEmpa_number());
			model.setPatkId(tm.getPatk_id());
			model.setTaprId(tm.getPatk_tapr_id());
		}
	}

	@Command
	@NotifyChange("list")
	public void submit(@BindingParam("win") Window win) {
		model.setApprovalType("客户经理");
		WfBusinessService wfbs = new EmPa_TaskServiceImpl();
		WfOperateService wfs = new WfOperateImpl(wfbs);
		Object[] obj={"审核",model};
		String[] str=wfs.PassToNext(obj, model.getTaprId(), UserInfo.getUsername(), "", 0, "");
		
		if (str[0].equals("1")) {
			Messagebox
					.show("提交成功", "提示", Messagebox.OK, Messagebox.INFORMATION);
			win.detach();
		} else {
			Messagebox.show("提交失败", "提示", Messagebox.OK, Messagebox.ERROR);
		}
	}


	//客服经理退回
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

	public void setList(List<EmPayModel> list) {
		this.list = list;
	}
	
	
}
