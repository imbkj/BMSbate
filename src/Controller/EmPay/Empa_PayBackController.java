package Controller.EmPay;

import impl.WorkflowCore.WfOperateImpl;

import java.util.List;

import org.zkoss.bind.annotation.BindingParam;
import org.zkoss.bind.annotation.Command;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Window;

import service.WorkflowCore.WfBusinessService;
import service.WorkflowCore.WfOperateService;

import Model.EmPayBackLogModel;
import Model.EmPayModel;
import Util.UserInfo;
import bll.EmPay.EmPa_OperateBll;
import bll.EmPay.EmPa_SelectBll;
import bll.EmPay.EmPa_TaskService;
import bll.EmPay.EmPa_TaskServiceImpl;

public class Empa_PayBackController {
	private EmPayModel pm = null;
	private EmPayBackLogModel m = new EmPayBackLogModel();
	private EmPa_SelectBll sbll = new EmPa_SelectBll();
	private List<EmPayBackLogModel> list = null;

	public Empa_PayBackController() {
		if (Executions.getCurrent().getArg().get("m") != null) {
			pm = (EmPayModel) Executions.getCurrent().getArg().get("m");
			list = sbll.getEmPayBackList(pm.getEmpa_number());
			
		}

	}

	@Command
	public void submit(@BindingParam("win") Window win) {
		if (m.getBack_case() == null || m.getBack_case().equals("")) {
			Messagebox.show("请填写退回原因", "提示", Messagebox.OK, Messagebox.ERROR);
			return;
		}
		/*
		EmPa_OperateBll bll = new EmPa_OperateBll();
		m.setBack_addname(UserInfo.getUsername());
		m.setEmpa_state(9);
		m.setBack_empa_number(pm.getEmpa_number());
		int k = bll.back(m);

		// 任务单处理
		EmPa_TaskService service = new EmPa_TaskService();
		
		service.taskReturnToN(pm.getPatkId(), pm.getTaprId(),1);
*/
		m.setBack_addname(UserInfo.getUsername());
		m.setEmpa_state(9);
		m.setBack_empa_number(pm.getEmpa_number());
		Object[] obj = { "退回", m,pm.getPatkId() };
		WfBusinessService wfbs = new EmPa_TaskServiceImpl();
		WfOperateService wfs = new WfOperateImpl(wfbs);
		String[] str = wfs.ReturnToN(obj, pm.getTaprId(), 1, "系统");
		if (str[0].equals("1")) {
			pm.setChange(true);
			Messagebox
					.show("提交成功", "提示", Messagebox.OK, Messagebox.INFORMATION);
			win.detach();
		} else {
			Messagebox.show("提交失败", "提示", Messagebox.OK, Messagebox.ERROR);
		}
	}

	public EmPayBackLogModel getM() {
		return m;
	}

	public void setM(EmPayBackLogModel m) {
		this.m = m;
	}

	public List<EmPayBackLogModel> getList() {
		return list;
	}

	public void setList(List<EmPayBackLogModel> list) {
		this.list = list;
	}

}
