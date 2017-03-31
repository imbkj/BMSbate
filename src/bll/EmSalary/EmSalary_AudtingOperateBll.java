package bll.EmSalary;

import impl.WorkflowCore.WfOperateImpl;

import java.util.List;

import Model.EmSalaryDataModel;

import service.WorkflowCore.WfOperateService;

import dal.EmSalary.EmSalary_AudtingDal;

public class EmSalary_AudtingOperateBll {
	private EmSalary_AudtingDal dal = new EmSalary_AudtingDal();

	// 工资审核(type: 1审核通过 2退回)
	public String[] audtingOperate(List<Integer> emSalaryList, String username,
			int taba_id, int tapr_id, int type) {
		String[] message = new String[3];
		if (type == 1) {
			message = audtingEmSalary(emSalaryList, username, taba_id, tapr_id);
		} else if (type == 2) {
			message = returnEmSalary(emSalaryList, username, taba_id, tapr_id);
		}
		return message;
	}

	// 工资待发放确认(type: 1确认通过 2退回)
	public String[] payAudtingOperate(List<Integer[]> emSalaryList,
			String username, int taba_id, int tapr_id, int type) {
		String[] message = new String[3];
		if (type == 1) {
			message = payAudtingEmSalary(emSalaryList, username, taba_id,
					tapr_id);
		} else if (type == 2) {
			message = payReturnEmSalary(emSalaryList, username, taba_id,
					tapr_id);
		}
		return message;
	}

	// 工资待发放确认
	private String[] payAudtingEmSalary(List<Integer[]> emSalaryList,
			String username, int taba_id, int tapr_id) {
		String[] message = new String[3];
		int sum = emSalaryList.size();
		int success = 0;
		try {
			for (Integer[] tbrb_id : emSalaryList) {
				try {
					success = success + dal.payAudtingEmSalary(tbrb_id[0]);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
			// 判断审核成功记录
			if (success == sum) {
				message[0] = "1";
				message[1] = "共审核" + sum + "条数据，全部成功。";
			} else {
				message[0] = "1";
				message[1] = "共审核" + sum + "条数据，成功确认" + success + "条数据。";
			}
			// 检测是否已审核完所有数据（返回true将转至下一步）
			if (dal.existEmSalaryPayAudtingAll(taba_id)) {
				WfOperateService wf = new WfOperateImpl(
						new EmSalary_SalaryOperateImpl());
				Object[] obj = { "2", taba_id };
				wf.PassToNext(obj, tapr_id, username, "", taba_id, "");
				message[2] = "1";
			}
		} catch (Exception e) {
			e.printStackTrace();
			message[0] = "2";
			message[1] = "审核工资出错";
		}
		return message;

	}

	// 工资待发放暂停发放
	public String[] payHoldEmSalary(List<EmSalaryDataModel> emSalaryList) {
		String[] message = new String[3];
		int sum = emSalaryList.size();
		int success = 0;
		try {
			for (int i = 0; i < emSalaryList.size(); i++) {
				try {
					EmSalaryDataModel esM = new EmSalaryDataModel();
					esM = emSalaryList.get(i);
					esM.setEsda_ifhold(1);
					success = success + dal.holdEmSalary(esM);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
			// 判断审核成功记录
			if (success == sum) {
				message[0] = "1";
				message[1] = "共暂停发放" + sum + "条数据，全部成功。";
			} else {
				message[0] = "1";
				message[1] = "共暂停发放" + sum + "条数据，成功确认" + success + "条数据。";
			}

		} catch (Exception e) {
			e.printStackTrace();
			message[0] = "2";
			message[1] = "工资暂停发放出错";
		}
		return message;

	}

	// 暂停发放工资转已审核
	public String[] holdAudting(EmSalaryDataModel m) {
		String[] message = new String[3];
		try {
			m.setEsda_ifhold(0);
			int success = dal.holdEmSalary(m);

			// 判断审核成功记录
			if (success == 1) {
				message[0] = "1";
				message[1] = "转已审核成功。";
			} else {
				message[0] = "0";
				message[1] = "转已审核失败。";
			}

		} catch (Exception e) {
			e.printStackTrace();
			message[0] = "2";
			message[1] = "转已审核出错";
		}
		return message;

	}

	// 工资待发放退回
	private String[] payReturnEmSalary(List<Integer[]> emSalaryList,
			String username, int taba_id, int tapr_id) {
		String[] message = new String[3];
		int sum = emSalaryList.size();
		int success = 0;
		try {
			for (Integer[] tbrb_id : emSalaryList) {
				try {
					success = success + dal.returnEmSalary(tbrb_id[0]);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
			// 判断审核成功记录
			if (success == sum) {
				message[0] = "1";
				message[1] = "共退回" + sum + "条数据，全部成功。";
			} else {
				message[0] = "1";
				message[1] = "共退回" + sum + "条数据，成功确认" + success + "条数据。";
			}

			if (dal.existEmSalaryEffAll(taba_id)) {
				// 检测工资批量审核单是否已无有效数据（返回true将终止任务单）
				WfOperateService wf = new WfOperateImpl(
						new EmSalary_SalaryOperateImpl());
				Object[] obj = { "2", taba_id };
				wf.StopTask(obj, tapr_id, username, "工资数据已全部退回，终止任务。");
				message[2] = "1";
			} else if (dal.existEmSalaryPayAudtingAll(taba_id)) {
				// 检测是否已审核完所有数据（返回true将转至下一步）
				WfOperateService wf = new WfOperateImpl(
						new EmSalary_SalaryOperateImpl());
				Object[] obj = { "2", taba_id };
				wf.PassToNext(obj, tapr_id, username, "", 0, "");
				message[2] = "1";
			}
		} catch (Exception e) {
			e.printStackTrace();
			message[0] = "2";
			message[1] = "退回工资出错";
		}
		return message;

	}

	// 工资发放退回
	public String[] payReturnSalary(List<Integer> emSalaryList,
			String username, int taba_id, int tapr_id) {
		String[] message = new String[3];
		int sum = emSalaryList.size();
		int success = 0;
		try {
			for (Integer tbrb_id : emSalaryList) {
				try {
					success = success + dal.returnEmSalary(tbrb_id);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
			// 判断审核成功记录
			if (success == sum) {
				message[0] = "1";
				message[1] = "共退回" + sum + "条数据，全部成功。";
			} else {
				message[0] = "1";
				message[1] = "共退回" + sum + "条数据，成功确认" + success + "条数据。";
			}

			/*if (dal.existEmSalaryEffAll(taba_id)) {
				// 检测工资批量审核单是否已无有效数据（返回true将终止任务单）
				WfOperateService wf = new WfOperateImpl(
						new EmSalary_SalaryOperateImpl());
				Object[] obj = { "2", taba_id };
				wf.StopTask(obj, tapr_id, username, "工资数据已全部退回，终止任务。");
				message[2] = "1";
			} else if (dal.existEmSalaryPayAudtingAll(taba_id)) {
				// 检测是否已审核完所有数据（返回true将转至下一步）
				WfOperateService wf = new WfOperateImpl(
						new EmSalary_SalaryOperateImpl());
				Object[] obj = { "2", taba_id };
				wf.PassToNext(obj, tapr_id, username, "", 0, "");
				message[2] = "1";
			}*/
		} catch (Exception e) {
			e.printStackTrace();
			message[0] = "2";
			message[1] = "退回工资出错";
		}
		return message;

	}

	// 工资审核通过
	private String[] audtingEmSalary(List<Integer> emSalaryList,
			String username, int taba_id, int tapr_id) {
		String[] message = new String[3];
		int sum = emSalaryList.size();
		int success = 0;
		try {
			for (int tbrb_id : emSalaryList) {
				try {
					success = success + dal.audtingEmSalary(tbrb_id);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
			// 判断审核成功记录
			if (success == sum) {
				message[0] = "1";
				message[1] = "共审核" + sum + "条数据，全部成功。";
			} else {
				message[0] = "1";
				message[1] = "共审核" + sum + "条数据，成功确认" + success + "条数据。";
			}
			// 检测是否已审核完所有数据（返回true将转至下一步）
			if (dal.existEmSalaryAudtingAll(taba_id)) {
				WfOperateService wf = new WfOperateImpl(
						new EmSalary_SalaryOperateImpl());
				Object[] obj = { "2", taba_id };
				wf.PassToNext(obj, tapr_id, username, "", taba_id, "");
				message[2] = "1";
			}
		} catch (Exception e) {
			e.printStackTrace();
			message[0] = "2";
			message[1] = "审核工资出错";
		}
		return message;

	}

	// 工资审核退回
	private String[] returnEmSalary(List<Integer> emSalaryList,
			String username, int taba_id, int tapr_id) {
		String[] message = new String[3];
		int sum = emSalaryList.size();
		int success = 0;
		try {
			for (int tbrb_id : emSalaryList) {
				try {
					success = success + dal.returnEmSalary(tbrb_id);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
			// 判断审核成功记录
			if (success == sum) {
				message[0] = "1";
				message[1] = "共退回" + sum + "条数据，全部成功。";
			} else {
				message[0] = "1";
				message[1] = "共退回" + sum + "条数据，成功确认" + success + "条数据。";
			}

			if (dal.existEmSalaryEffAll(taba_id)) {
				// 检测工资批量审核单是否已无有效数据（返回true将终止任务单）
				WfOperateService wf = new WfOperateImpl(
						new EmSalary_SalaryOperateImpl());
				Object[] obj = { "2", taba_id };
				wf.StopTask(obj, tapr_id, username, "工资数据已全部退回，终止任务。");
				message[2] = "1";
			} else if (dal.existEmSalaryAudtingAll(taba_id)) {
				// 检测是否已审核完所有数据（返回true将转至下一步）
				WfOperateService wf = new WfOperateImpl(
						new EmSalary_SalaryOperateImpl());
				Object[] obj = { "2", taba_id };
				wf.PassToNext(obj, tapr_id, username, "", 0, "");
				message[2] = "1";
			}
		} catch (Exception e) {
			e.printStackTrace();
			message[0] = "2";
			message[1] = "退回工资出错";
		}
		return message;

	}

}
