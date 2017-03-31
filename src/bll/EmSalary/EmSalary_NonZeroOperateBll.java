package bll.EmSalary;

import impl.WorkflowCore.WfOperateImpl;

import java.util.List;

import service.WorkflowCore.WfOperateService;

import dal.LoginDal;
import dal.EmSalary.EmSalary_AudtingDal;
import dal.EmSalary.EmSalary_NonZeroOperateDal;
import dal.EmSalary.EmSalary_SalarySelDal;
import Model.EmSalaryBaseSel_viewModel;
import Model.EmSalaryDataModel;

public class EmSalary_NonZeroOperateBll {
	private EmSalary_NonZeroOperateDal dal;

	public EmSalary_NonZeroOperateBll() {
		dal = new EmSalary_NonZeroOperateDal();
	}

	// 修改工资非清零字段
	public String[] UpSalaryNonZero(List<EmSalaryDataModel> list) {
		int sum = list.size();
		int success = 0;
		int up = 1;
		String[] message = new String[3];
		try {
			for (EmSalaryDataModel m : list) {
				try {
					up = dal.UpSalaryNonZero(m);
					if (up == 0) {
						success = success + 1;
					}
				} catch (Exception e) {
					e.printStackTrace();
				}
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		if (success == sum) {
			message[0] = "1";
			message[1] = "共修改工资数据" + sum + "条，全部成功。";
		} else {
			message[0] = "0";
			message[1] = "共修改工资数据" + sum + "条，有" + (sum - success) + "条数据修改失败。";
			message[2] = String.valueOf(success);
		}
		return message;
	}

	// 刪除工资
	public int DelSalaryNonZero(int esdt_id, int tbrb_id) {
		return dal.DelSalaryNonZero(esdt_id, tbrb_id);
	}

	// 非清零字段修改审核操作(type:1确认2退回3终止)
	public String[] audtingOperate(int taba_id, int tapr_id, String username,
			int type, List<EmSalaryDataModel> salaryList) {
		String[] message = new String[5];
		try {
			switch (type) {
			case 1:
				message = confirmOperate(taba_id, tapr_id, username, salaryList);
				break;
			case 2:
				message = returnTask(taba_id, tapr_id, username);
				break;
			case 3:
				message = StopTask(taba_id, tapr_id, username);
				break;
			default:
				message[0] = "2";
				message[1] = "操作出错。";
				break;
			}
		} catch (Exception e) {

		}
		return message;
	}

	// 非清零字段数据确认
	public String[] confirmOperate(int taba_id, int tapr_id, String username,
			List<EmSalaryDataModel> salaryList) {
		String[] message = null;
		try {
			Object[] obj = { "3", taba_id, salaryList };
			WfOperateService wf = new WfOperateImpl(
					new EmSalary_NonZeroOperateImpl());
			message = wf.PassToNext(obj, tapr_id, username, "", 0, "");
		} catch (Exception e) {
			message[0] = "2";
			message[1] = "操作出错。";
		}
		return message;
	}

	// 修改非清零字段转下一步
	public String[] UpdatePassToNext(String username, int taba_id,
			int taba_tapr_id) {
		String[] message = new String[5];
		try {
			Object[] obj = { "1", taba_id };
			WfOperateService wf = new WfOperateImpl(
					new EmSalary_NonZeroOperateImpl());
			message = wf.PassToNext(obj, taba_tapr_id, username, "", 0, "");
		} catch (Exception e) {
			message[0] = "2";
			message[1] = "操作出错。";
		}
		return message;
	}

	// 退回工资非清零字段修改任务单
	public String[] returnTask(int taba_id, int tapr_id, String username) {
		String[] message = new String[3];
		try {
			WfOperateService wf = new WfOperateImpl(
					new EmSalary_NonZeroOperateImpl());
			Object[] obj = { "4", taba_id };
			message = wf.ReturnToPrev(obj, tapr_id, username, "退回工资非清零字段修改任务单");
		} catch (Exception e) {
			e.printStackTrace();
			message[0] = "2";
			message[1] = "任务单退回出错。";
		}
		return message;
	}

	// 终止工资非清零字段修改任务单
	public String[] StopTask(int taba_id, int tapr_id, String username) {
		String[] message = new String[3];
		try {
			WfOperateService wf = new WfOperateImpl(
					new EmSalary_NonZeroOperateImpl());
			Object[] obj = { "2", taba_id };
			message = wf.StopTask(obj, tapr_id, username, "工资数据已全部退回，终止任务。");
		} catch (Exception e) {
			e.printStackTrace();
			message[0] = "2";
			message[1] = "任务单终止出错。";
		}
		return message;
	}

	// 检测工资批量审核单是否已无有效数据（返回true将终止任务单）
	public boolean existEmSalaryAudtingAll(int taba_id) {
		EmSalary_AudtingDal audDal = new EmSalary_AudtingDal();
		return audDal.existEmSalaryEffAll(taba_id);
	}

	// 将工资表数据复制到Temp表，并将任务单插入TaskBatchRelBusiness表信息
	public String[] addEmSalaryDataTemp(
			List<EmSalaryBaseSel_viewModel> emSalaryList, String username,
			int cid, int ownmonth) {
		String[] message = new String[2];
		EmSalary_NonZeroOperateImpl impl = new EmSalary_NonZeroOperateImpl();
		WfOperateService wf = new WfOperateImpl(impl);
		EmSalary_SalarySelDal selBll = new EmSalary_SalarySelDal();
		try {
			String CoShortName = selBll.getCoShortName(cid);
			Object[] obj = { "2", emSalaryList, username };
			message = wf.AddTaskToNext(obj, ownmonth + "非清零工资项目数据变更",
					CoShortName + "非清零工资项目数据变更", 84, username, ownmonth
							+ CoShortName + "非清零工资项目数据变更", cid, "");
		} catch (Exception e) {
			message[0] = "2";
			message[1] = "非清零工资项目数据变更出错";
		}
		return message;
	}

	// 获取loginID
	public Integer getUserIDByname(String username) {
		Integer userID = 0;
		LoginDal loginDal = new LoginDal();
		userID = loginDal.getUserIDByname(username);
		return userID;
	}
}
