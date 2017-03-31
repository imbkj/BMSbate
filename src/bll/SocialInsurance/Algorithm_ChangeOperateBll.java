package bll.SocialInsurance;

import java.util.List;

import dal.SocialInsurance.Algorithm_ChangeOperateDal;

import impl.WorkflowCore.WfOperateImpl;
import service.WorkflowCore.WfBusinessService;
import service.WorkflowCore.WfOperateService;
import Model.SocialInsuranceAlgorithmViewModel;
import Model.SocialInsuranceChangeModel;
import Model.SocialInsuranceClassInfoViewModel;

public class Algorithm_ChangeOperateBll {

	// 算法变更生效
	public String[] ConfirmSiChange(SocialInsuranceChangeModel m,String username){
		String[] message = new String[5];
		try{
			Object[] obj = { "confirm", m.getSich_id(),m.getSich_change_type(),username };
			// 启用任务单
			WfBusinessService impl = new Algorithm_OperateImpl();
			WfOperateService wf = new WfOperateImpl(impl);
			message = wf.PassToNext(obj, m.getSich_tapr_id(), username, "该算法已生效", 0,
					"");
		}catch(Exception e){
			message[0] = "2";
			message[1] = "操作出错。";
		}
		return message;
	}

	// 终止社保算法变更任务流程
	public String[] StopTask(SocialInsuranceChangeModel m, String username) {
		String[] message = new String[5];
		try {
			Object[] obj = { m };
			// 启用任务单
			WfBusinessService impl = new Algorithm_OperateImpl();
			WfOperateService wf = new WfOperateImpl(impl);
			message = wf.StopTask(obj, m.getSich_tapr_id(), username, "");
		} catch (Exception e) {
			message[0] = "2";
			message[1] = "操作出错。";
		}
		return message;
	}

	// 重新提交社保变更
	public String[] resubmit(SocialInsuranceChangeModel m, String username) {
		String[] message = new String[5];
		try {
			Object[] obj = { "resubmit", m };
			// 启用任务单
			WfBusinessService impl = new Algorithm_OperateImpl();
			WfOperateService wf = new WfOperateImpl(impl);
			message = wf.PassToNext(obj, m.getSich_tapr_id(), username, "", 0,
					"");
		} catch (Exception e) {
			message[0] = "2";
			message[1] = "操作出错。";
		}
		return message;
	}

	// 退回社保变更
	public String[] returnPre(SocialInsuranceChangeModel m, String username) {
		String[] message = new String[5];
		try {
			Object[] obj = { m };
			// 启用任务单
			WfBusinessService impl = new Algorithm_OperateImpl();
			WfOperateService wf = new WfOperateImpl(impl);
			message = wf.ReturnToPrev(obj, m.getSich_tapr_id(), username,
					m.getSich_ReturnReason());
		} catch (Exception e) {
			message[0] = "2";
			message[1] = "退回算法变更出错。";
		}
		return message;
	}

	// 更新算法变更信息，传入参数，返回message数组(0失败1成功2出错)
	public String[] upAlgorithmChange(SocialInsuranceAlgorithmViewModel m,
			List<SocialInsuranceClassInfoViewModel> list) {
		String[] message = new String[2];
		Algorithm_ChangeOperateDal dal = new Algorithm_ChangeOperateDal();
		try {
			int result = dal.UpSiAlgorithmChange(m);
			if (result > 0) {
				for (SocialInsuranceClassInfoViewModel lm : list) {
					dal.UpSiAlgorithmInfoChange(lm);
				}
				message[0] = "1";
				message[1] = "更新算法变更成功。";
			} else {
				message[0] = "0";
				message[1] = "更新算法变更失败。";
			}
		} catch (Exception e) {
			message[0] = "2";
			message[1] = "更新算法变更出错。";
		}
		return message;
	}

}
