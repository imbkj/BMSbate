package bll.SocialInsurance;

import impl.WorkflowCore.WfOperateImpl;

import java.util.Date;
import java.util.List;

import service.WorkflowCore.WfBusinessService;
import service.WorkflowCore.WfOperateService;

import Model.SocialInsuranceAlgorithmViewModel;
import Model.SocialInsuranceChangeModel;
import Model.SocialInsuranceClassInfoViewModel;
import dal.SocialInsurance.AlgorithmOperateDal;

public class Algorithm_OperateBll {
	private AlgorithmOperateDal dal;

	public Algorithm_OperateBll() {
		dal = new AlgorithmOperateDal();
	}

	// 新增算法，传入参数，返回message数组(0失败1成功2出错)
	public String[] addAlgorithm(SocialInsuranceAlgorithmViewModel m,
			List<SocialInsuranceClassInfoViewModel> list) {
		String[] message = new String[3];
		try {
			int sial_id = dal.AddSiAlgorithm(m, 1);
			if (sial_id > 0) {
				AddSiAlgorithmInfo(list, sial_id);
				message[0] = "1";
				message[1] = "新增算法成功。";
				message[2] = String.valueOf(sial_id);
				
			} else {
				message[0] = "0";
				message[1] = "新增算法失败。";
			}
		} catch (Exception e) {
			message[0] = "2";
			message[1] = "新增算法出错。";
		}
		return message;
	}

	// 新增算法变更，启用任务单，返回message数组(0失败1成功2出错)
	public String[] addAlgorithmChangeToTask(
			SocialInsuranceAlgorithmViewModel m,
			List<SocialInsuranceClassInfoViewModel> list) {
		String[] message = new String[5];
		try {
			// 设置社保变更参数
			SocialInsuranceChangeModel cm = new SocialInsuranceChangeModel();
			cm.setSich_cabc_id(m.getSoin_cabc_id());
			cm.setSich_change_type(1);
			cm.setSich_title(m.getSoin_title());
			cm.setSich_addname(m.getSial_addname());
			Object[] obj = { "addAlgorithm", cm, m, list, "新增" };
			// 启用任务单
			WfBusinessService impl = new Algorithm_OperateImpl();
			WfOperateService wf = new WfOperateImpl(impl);
			message = wf.AddTaskToNext(obj, "社保字典库管理", "新增" + m.getCity() + "-"
					+ m.getCoab_name() + "的算法", 75, m.getSial_addname(), "", 0,
					"");
		} catch (Exception e) {
			message[0] = "2";
			message[1] = "新增算法出错。";
		}
		return message;
	}

	// 更新或调整算法，启用任务单，返回message数组(0失败1成功2出错)
	public String[] upAlgorithmChangeToTask(
			SocialInsuranceAlgorithmViewModel m,
			List<SocialInsuranceClassInfoViewModel> list) {
		String[] message = new String[5];
		try {
			// 设置社保变更参数
			SocialInsuranceChangeModel cm = new SocialInsuranceChangeModel();
			// 判断算法是调整还是更新
			String change = "";
			if (dal.existExecdate(m.getSoin_id(), m.getSial_execdate())) {
				// 更新
				cm.setSich_change_type(2);
				change = "更新";
			} else {
				// 调整
				cm.setSich_change_type(3);
				change = "调整";
			}
			cm.setSich_soin_id(m.getSoin_id());
			cm.setSich_sial_id(m.getSial_id());
			cm.setSich_cabc_id(m.getSoin_cabc_id());
			cm.setSich_title(m.getSoin_title());
			cm.setSich_addname(m.getSial_addname());
			Object[] obj = { "addAlgorithm", cm, m, list, change };
			// 启用任务单
			WfBusinessService impl = new Algorithm_OperateImpl();
			WfOperateService wf = new WfOperateImpl(impl);
			message = wf.AddTaskToNext(obj, "社保字典库管理", change + m.getCity()
					+ "-" + m.getCoab_name() + "的算法", 75, m.getSial_addname(),
					"", 0, "");
		} catch (Exception e) {
			message[0] = "2";
			message[1] = "更新算法出错。";
		}
		return message;
	}

	// 更新算法，传入参数，返回message数组(0失败1成功2出错)
	public String[] upAlgorithm(SocialInsuranceAlgorithmViewModel m,
			List<SocialInsuranceClassInfoViewModel> list) {
		String[] message = new String[2];
		try {
			if (dal.existExecdate(m.getSoin_id(), m.getSial_execdate())) {
				// 新增
				int sial_id = dal.AddSiAlgorithm(m, 2);
				if (sial_id > 0) {
					AddSiAlgorithmInfo(list, sial_id);
					message[0] = "1";
					message[1] = "更新算法成功。";
				} else {
					message[0] = "0";
					message[1] = "更新算法失败。";
				}
			} else {
				// 更新
				int result = dal.UpSiAlgorithm(m);
				if (result > 0) {
					UpSiAlgorithmInfo(list);
					message[0] = "1";
					message[1] = "更新算法成功。";
				} else {
					message[0] = "0";
					message[1] = "更新算法失败。";
				}
			}
		} catch (Exception e) {
			message[0] = "2";
			message[1] = "更新算法出错。";
		}
		return message;
	}

	// 停用社保标准，启用任务单，返回message数组(0失败1成功2出错)
	public String[] StopSiAlgorithmChangeToTask(
			SocialInsuranceAlgorithmViewModel m) {
		String[] message = new String[5];
		try {
			// 设置社保变更参数
			SocialInsuranceChangeModel cm = new SocialInsuranceChangeModel();
			cm.setSich_change_type(4);
			cm.setSich_title(m.getSoin_title());
			cm.setSich_cabc_id(m.getSoin_cabc_id());
			cm.setSich_sial_id(m.getSial_id());
			cm.setSich_Reason(m.getSoin_delreason());
			cm.setSich_addname(m.getSoin_delname());
			cm.setSich_soin_id(m.getSoin_id());
			cm.setSich_cabc_id(m.getSoin_cabc_id());
			Object[] obj = { "stopAlgorithm", cm };
			// 启用任务单
			WfBusinessService impl = new Algorithm_OperateImpl();
			WfOperateService wf = new WfOperateImpl(impl);
			message = wf.AddTaskToNext(obj, "社保字典库管理", "停用" + m.getCity() + "-"
					+ m.getCoab_name() + "的标准", 75, m.getSoin_delname(), "", 0,
					"");
		} catch (Exception e) {
			message[0] = "2";
			message[1] = "更新算法出错。";
		}
		return message;
	}

	// 禁止社保标准
	public int StopSiAlgorithm(int sial_id, String reason, String username) {
		return dal.StopSiAlgorithm(sial_id, reason, username);
	}

	// 新增社保字典详细项目
	private void AddSiAlgorithmInfo(
			List<SocialInsuranceClassInfoViewModel> list, int sial_id) {
		for (SocialInsuranceClassInfoViewModel m : list) {
			dal.AddSiAlgorithmInfo(m, sial_id);
		}
	}

	// 更新社保字典详细项目
	private void UpSiAlgorithmInfo(List<SocialInsuranceClassInfoViewModel> list) {
		for (SocialInsuranceClassInfoViewModel m : list) {
			dal.UpSiAlgorithmInfo(m);
		}
	}

	// 检测是否已有该算法
	public boolean existAlName(int cabc_id, String alName) {
		return dal.existAlName(cabc_id, alName);
	}

	// 检测变更信息中是否已有该算法未生效
	public boolean existChangeAlName(int cabc_id, String alName) {
		return dal.existChangeAlName(cabc_id, alName);
	}

	// 检测标准是否已有相同的执行时间（非当前算法）
	public boolean existExecdateExceptSial(int soin_id, Date sial_execdate,
			int sial_id) {
		return dal.existExecdateExceptSial(soin_id, sial_execdate, sial_id);
	}

	// 检测变更信息中是否已有该年月的算法调整或更新未生效数据
	public boolean existChangeExecdate(int soin_id, Date sial_execdate) {
		return dal.existChangeExecdate(soin_id, sial_execdate);
	}

	// 检测变更信息中是否已有该标准未生效的数据
	public boolean existChangeSoinId(int soin_id) {
		return dal.existChangeSoinId(soin_id);
	}
}
