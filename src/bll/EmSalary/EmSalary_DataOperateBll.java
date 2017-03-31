package bll.EmSalary;

import dal.EmSalary.EmSalary_SalaryOperateDal;
import impl.WorkflowCore.WfOperateImpl;
import service.WorkflowCore.WfOperateService;
import Model.EmSalaryBaseAdd_viewModel;
import Model.EmSalaryBaseSel_viewModel;

public class EmSalary_DataOperateBll {

	// 工资重发原因
	public String[] repayReason(EmSalaryBaseSel_viewModel m, String username) {
		String[] message = new String[5];
		EmSalary_DataOperateImpl impl = new EmSalary_DataOperateImpl();
		WfOperateService wf = new WfOperateImpl(impl);
		try {

			Object[] obj = { "1", m, username };
			message = wf.AddTaskToNext(obj, "员工工资重发", m.getCoba_shortname()
					+ "公司的" + "(" + m.getGid() + ")" + m.getEmba_name() + "员工"
					+ m.getOwnmonth() + "月份工资重发", 83, username, "", m.getCid(),
					"");

		} catch (Exception e) {
			message[0] = "2";
			message[1] = "员工工资重发出错";
		}
		return message;
	}

	// 工资重发通知客服修改
	public String[] repaySend(EmSalaryBaseSel_viewModel m, String username) {
		String[] message = new String[5];
		try {
			Object[] obj = { "2", m.getEsda_id() };
			// 执行工作流
			WfOperateService wf = new WfOperateImpl(
					new EmSalary_DataOperateImpl());
			message = wf.PassToNext(obj, m.getEsda_tapr_id(), username, "",
					m.getCid(), "");

		} catch (Exception e) {
			message[0] = "2";
			message[1] = "通知客服修改员工银行信息出错";
		}
		return message;
	}

	// 修改银行信息
	public String[] upBankInfo(EmSalaryBaseSel_viewModel esdaM,
			EmSalaryBaseAdd_viewModel embaM, String username) {
		String[] message = new String[5];
		try {

			Object[] obj = { "3", esdaM, embaM, username };
			// 执行工作流
			WfOperateService wf = new WfOperateImpl(
					new EmSalary_DataOperateImpl());
			message = wf.PassToNext(obj, esdaM.getEsda_tapr_id(), username, "",
					0, "");

		} catch (Exception e) {
			message[0] = "2";
			message[1] = "修改员工银行信息出错";
		}
		return message;
	}

	// 工资重发
	public String[] repay(EmSalaryBaseSel_viewModel esdaM, String username) {
		String[] message = new String[5];
		try {

			Object[] obj = { "4", esdaM, username };
			// 执行工作流
			WfOperateService wf = new WfOperateImpl(
					new EmSalary_DataOperateImpl());
			message = wf.PassToNext(obj, esdaM.getEsda_tapr_id(), username, "",
					0, "");

		} catch (Exception e) {
			message[0] = "2";
			message[1] = "工资重发出错";
		}
		return message;
	}

	// 修改电汇审核状态byCID
	public String[] upTTVState(Integer cid, String payDate) {
		EmSalary_SalaryOperateDal dal = new EmSalary_SalaryOperateDal();
		String[] message = new String[2];
		try {
			int i = dal.upTTVState(cid, payDate);
			if (i == 0) {
				message[0] = "1";
				message[1] = "电汇审核成功。";
			} else {
				message[0] = "0";
				message[1] = "电汇审核失败。";
			}
		} catch (Exception e) {
			message[0] = "2";
			message[1] = "电汇审核出错。";
		}

		return message;
	}

	// 修改电汇审核状态by发放日期
	public String[] upTTVStateAll(String payDate) {
		EmSalary_SalaryOperateDal dal = new EmSalary_SalaryOperateDal();
		String[] message = new String[2];
		try {
			int i = dal.upTTVStateAll(payDate);
			if (i == 0) {
				message[0] = "1";
				message[1] = "电汇审核成功。";
			} else {
				message[0] = "0";
				message[1] = "电汇审核失败。";
			}
		} catch (Exception e) {
			message[0] = "2";
			message[1] = "电汇审核出错。";
		}

		return message;
	}

	// 支付模块数据重发
	public String[] repayEmPay(Integer empa_id,Integer reason) {
		EmSalary_SalaryOperateDal dal = new EmSalary_SalaryOperateDal();
		String[] message = new String[2];
		try {
			int i = dal.repayEmPay(empa_id,reason);
			if (i == 0) {
				message[0] = "1";
				message[1] = "支付数据重发成功。";
			} else {
				message[0] = "0";
				message[1] = "支付数据重发失败。";
			}
		} catch (Exception e) {
			message[0] = "2";
			message[1] = "支付数据重发出错。";
		}

		return message;
	}
}
