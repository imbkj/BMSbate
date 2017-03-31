package bll.EmSheBao;

import impl.WorkflowCore.WfOperateImpl;
import service.WorkflowCore.WfBusinessService;
import service.WorkflowCore.WfOperateService;

public class Emsi_CheckOperateBll {

	// 社保补缴服务中心核收通過
	public String[] bjCheckPass(int id, int tapr_id, String reason,
			String username) {
		String[] message = new String[5];
		try {
			WfBusinessService impl = new Emsi_BjOperateImpl();
			WfOperateService wf = new WfOperateImpl(impl);
			Object[] obj = { "0", id, reason, username };
			message = wf.PassToNext(obj, tapr_id, username, reason, 0, "");
		} catch (Exception e) {
			e.printStackTrace();
			message[0] = "2";
			message[1] = "社保养老补缴,处理出错！";
		}
		return message;
	}

	// 社保补缴服务中心核收通過(医疗)
	public String[] bjJLCheckPass(int id, int tapr_id, String reason,
			String username) {
		String[] message = new String[5];
		try {
			WfBusinessService impl = new Emsi_BjJLOperateImpl();
			WfOperateService wf = new WfOperateImpl(impl);
			Object[] obj = { "0", id, reason, username };
			message = wf.PassToNext(obj, tapr_id, username, reason, 0, "");
		} catch (Exception e) {
			e.printStackTrace();
			message[0] = "2";
			message[1] = "社保医疗补缴,处理出错！";
		}
		return message;
	}

	// 社保补缴服务中心核收退回
	public String[] bjCheckReturn(int id, int tapr_id, String reason,
			String username) {
		String[] message = new String[5];
		try {
			WfBusinessService impl = new Emsi_BjOperateImpl();
			WfOperateService wf = new WfOperateImpl(impl);
			Object[] obj = { "0", id, reason, username };
			message = wf.ReturnToN(obj, tapr_id, 1, username);
		} catch (Exception e) {
			e.printStackTrace();
			message[0] = "2";
			message[1] = "社保补缴,处理出错！";
		}
		return message;
	}

	// 社保补缴服务中心核收退回(医疗)
	public String[] bjJLCheckReturn(int id, int tapr_id, String reason,
			String username) {
		String[] message = new String[5];
		try {
			WfBusinessService impl = new Emsi_BjJLOperateImpl();
			WfOperateService wf = new WfOperateImpl(impl);
			Object[] obj = { "0", id, reason, username };
			message = wf.ReturnToN(obj, tapr_id, 1, username);
		} catch (Exception e) {
			e.printStackTrace();
			message[0] = "2";
			message[1] = "社保补缴,处理出错！";
		}
		return message;
	}

	// 社保外籍人服务中心核收通過
	public String[] foreignerCheckPass(int id, int tapr_id, String reason,
			String username) {
		String[] message = new String[5];
		try {
			WfBusinessService impl = new Emsc_ForeignerDeclareImpl();
			WfOperateService wf = new WfOperateImpl(impl);
			Object[] obj = { "1", id, reason, username };
			message = wf.PassToNext(obj, tapr_id, username, reason, 0, "");
		} catch (Exception e) {
			e.printStackTrace();
			message[0] = "2";
			message[1] = "社保外籍人参保,处理出错！";
		}
		return message;
	}

	// 社保外籍人服务中心核收退回
	public String[] foreignerCheckReturn(int id, int tapr_id, String reason,
			String username) {
		String[] message = new String[5];
		try {
			WfBusinessService impl = new Emsc_ForeignerDeclareImpl();
			WfOperateService wf = new WfOperateImpl(impl);
			Object[] obj = { "0", id, reason, username };
			message = wf.ReturnToN(obj, tapr_id, 1, username);
		} catch (Exception e) {
			e.printStackTrace();
			message[0] = "2";
			message[1] = "社保外籍人参保,处理出错！";
		}
		return message;
	}
}
