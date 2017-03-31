package bll.SocialInsurance;

import java.util.List;

import Model.SocialInsuranceAlgorithmViewModel;
import Model.SocialInsuranceChangeModel;
import Model.SocialInsuranceClassInfoViewModel;
import service.WorkflowCore.WfBusinessService;
import dal.SocialInsurance.AlgorithmOperateDal;
import dal.SocialInsurance.Algorithm_ChangeOperateDal;

public class Algorithm_OperateImpl implements WfBusinessService {

	@Override
	public String[] Operate(Object[] obj) {
		String[] message = new String[5];
		String optype = obj[0].toString();
		switch (optype) {
		// 新增算法
		case "addAlgorithm":
			message = addSocialInsuranceChange(obj);
			break;
		// 禁用标准
		case "stopAlgorithm":
			message = stopAlgorithmChange(obj);
			break;
		// 重新提交
		case "resubmit":
			message = resubmit(obj);
			break;
		// 审核通过
		case "confirm":
			message = confirm(obj);
			break;
		}

		return message;
	}

	// 退回任务单
	@Override
	public String[] ReturnOperate(Object[] obj) {
		String[] message = new String[5];
		try {
			Algorithm_ChangeOperateDal opdal = new Algorithm_ChangeOperateDal();
			SocialInsuranceChangeModel cm = (SocialInsuranceChangeModel) obj[0];
			opdal.returnAlgorithmChange(cm);
			message[0] = "1";
			message[1] = "算法变更退回成功。";
			message[2] = String.valueOf(cm.getSich_id());
			message[3] = "SocialInsuranceChange";
			message[4] = "退回算法变更。";
		} catch (Exception e) {
			message[0] = "2";
			message[1] = "社保变更退回出错。";
		}
		return message;
	}

	@Override
	public String[] SkipOperate(Object[] obj) {
		// TODO Auto-generated method stub
		return null;
	}

	// 终止任务单
	@Override
	public String[] StopOperate(Object[] obj) {
		String[] message = new String[5];
		try {
			Algorithm_ChangeOperateDal opdal = new Algorithm_ChangeOperateDal();
			SocialInsuranceChangeModel cm = (SocialInsuranceChangeModel) obj[0];
			opdal.StopTaskAlgorithmChange(cm);
			message[0] = "1";
			message[1] = "终止算法变更成功。";
			message[2] = String.valueOf(cm.getSich_id());
			message[3] = "SocialInsuranceChange";
			message[4] = "终止算法变更。";
		} catch (Exception e) {
			message[0] = "2";
			message[1] = "终止算法变更出错。";
		}
		return message;
	}

	@Override
	public Boolean UpdateTaprid(int dataId, int tapr_id, int state) {
		AlgorithmOperateDal dal = new AlgorithmOperateDal();
		return dal.upSocialInsuranceChangeTaprId(tapr_id, dataId);
	}

	@Override
	public Boolean ErrOperate(int dataId) {
		// TODO Auto-generated method stub
		return null;
	}

	// 社保字典库新增算法至变更表
	private String[] addSocialInsuranceChange(Object[] obj) {
		String[] message = new String[5];
		try {
			AlgorithmOperateDal dal = new AlgorithmOperateDal();
			SocialInsuranceChangeModel cm = (SocialInsuranceChangeModel) obj[1];
			SocialInsuranceAlgorithmViewModel m = (SocialInsuranceAlgorithmViewModel) obj[2];
			List<SocialInsuranceClassInfoViewModel> list = (List<SocialInsuranceClassInfoViewModel>) obj[3];
			String change = obj[4].toString();
			// 插入社保字典库变更信息
			int sich_id = dal.AddSocialInsuranceChange(cm);
			if (sich_id > 0) {
				AddSiAlgorithmInfo(m, list, sich_id, dal);
				message[0] = "1";
				message[1] = change + "算法成功。";
				message[2] = String.valueOf(sich_id);
				message[3] = "SocialInsuranceChange";
				message[4] = change + "算法：" + cm.getSich_title();
			} else {
				message[0] = "0";
				message[1] = change + "算法失败。";
			}
		} catch (Exception e) {
			message[0] = "2";
			message[1] = "err,业务处理出错。";
		}
		return message;
	}

	// 社保字典库停用标准至变更表
	private String[] stopAlgorithmChange(Object[] obj) {
		String[] message = new String[5];
		try {
			AlgorithmOperateDal dal = new AlgorithmOperateDal();
			SocialInsuranceChangeModel cm = (SocialInsuranceChangeModel) obj[1];
			// 插入社保字典库变更信息
			int sich_id = dal.AddSocialInsuranceChange(cm);
			if (sich_id > 0) {
				message[0] = "1";
				message[1] = "停用标准成功。";
				message[2] = String.valueOf(sich_id);
				message[3] = "SocialInsuranceChange";
				message[4] = "停用标准：" + cm.getSich_title();
			} else {
				message[0] = "0";
				message[1] = "停用标准失败。";
			}
		} catch (Exception e) {
			message[0] = "2";
			message[1] = "err,业务处理出错。";
		}
		return message;
	}

	// 新增社保字典算法及算法项目变更
	private void AddSiAlgorithmInfo(SocialInsuranceAlgorithmViewModel m,
			List<SocialInsuranceClassInfoViewModel> list, int sich_id,
			AlgorithmOperateDal dal) {

		try {
			// 插入算法变更
			int siac_id = dal.AddSocialInsuranceAlgorithmChange(m, sich_id);
			if (siac_id > 0) {
				// 循环插入算法项目变更
				for (SocialInsuranceClassInfoViewModel lm : list) {
					dal.AddSocialInseranceAlgorithmInfoChange(lm, siac_id);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	// 重新提交
	private String[] resubmit(Object[] obj) {
		String[] message = new String[5];
		try {
			Algorithm_ChangeOperateDal opdal = new Algorithm_ChangeOperateDal();
			SocialInsuranceChangeModel cm = (SocialInsuranceChangeModel) obj[1];
			opdal.resubmitAlgorithmChange(cm);
			message[0] = "1";
			message[1] = "社保变更重新提交成功。";
			message[2] = String.valueOf(cm.getSich_id());
			message[3] = "SocialInsuranceChange";
			message[4] = "重新提交算法：" + cm.getSich_title();
		} catch (Exception e) {
			e.printStackTrace();
			message[0] = "2";
			message[1] = "重新提交算法变更出错。";
		}
		return message;
	}

	// 审核通过
	private String[] confirm(Object[] obj) {
		String[] message = new String[5];
		try {
			Algorithm_ChangeOperateDal opdal = new Algorithm_ChangeOperateDal();
			int sich_id = Integer.parseInt(obj[1].toString());
			int sich_change_type = Integer.parseInt(obj[2].toString());
			String username = obj[3].toString();
			int result = 0;
			// 根据变更类型选择执行的存储过程
			switch (sich_change_type) {
			case 1:
				result = opdal.AddSocialInsuranceChangeConfirm(sich_id,
						username);
				break;
			case 2:
				result = opdal.UpAddSocialInsuranceChangeConfirm(sich_id,
						username);
				break;
			case 3:
				result = opdal
						.UpSocialInsuranceChangeConfirm(sich_id, username);
				break;
			case 4:
				result = opdal.StopSocialInsuranceChangeConfirm(sich_id,
						username);
				break;
			default:
				break;
			}
			if (result == 1) {
				message[0] = "1";
				message[1] = "审核成功。";
				message[2] = String.valueOf(sich_id);
				message[3] = "SocialInsuranceChange";
				message[4] = "算法变更审核通过";
			} else {
				message[0] = "0";
				message[1] = "操作失败。";
				message[2] = String.valueOf(sich_id);
				message[3] = "SocialInsuranceChange";
				message[4] = "算法变更审核";
			}
		} catch (Exception e) {
			e.printStackTrace();
			message[0] = "2";
			message[1] = "重新提交算法变更出错。";
		}
		return message;
	}
}
