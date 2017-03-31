package bll.EmSalary;

import java.util.List;

import Model.CoSalaryItemFormulaModel;

import dal.EmSalary.EmSalary_SalaryOperateDal;
import dal.EmSalary.ItemFormula_OperateDal;
import service.WorkflowCore.WfBusinessService;

public class EmSalary_SalaryOperateImpl implements WfBusinessService {
	private EmSalary_SalaryOperateDal dal = new EmSalary_SalaryOperateDal();

	@Override
	public String[] Operate(Object[] obj) {
		String[] message = new String[5];
		if ("1".equals(obj[0].toString())) {
			message = ConfirmSalary((List<Integer>) obj[1],
					String.valueOf(obj[2]));
		} else if ("2".equals(obj[0].toString())) {
			message[0] = "1";
			message[1] = "审核成功。";
			message[2] = String.valueOf(obj[1].toString());
			message[3] = "TaskBatch";
			message[4] = "审核工资完成。";
		}
		return message;
	}

	@Override
	public String[] ReturnOperate(Object[] obj) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String[] SkipOperate(Object[] obj) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String[] StopOperate(Object[] obj) {
		String[] message = new String[5];
		if ("2".equals(obj[0].toString())) {
			message[0] = "1";
			message[1] = "审核成功。";
			message[2] = String.valueOf(obj[1].toString());
			message[3] = "TaskBatch";
			message[4] = "审核工资完成。";
		}
		return message;
	}

	@Override
	public Boolean UpdateTaprid(int dataId, int tapr_id, int state) {
		return dal.upTaskBatchTaprId(tapr_id, dataId);
	}

	@Override
	public Boolean ErrOperate(int dataId) {
		// TODO Auto-generated method stub
		return null;
	}

	// 工资批量确认
	private String[] ConfirmSalary(List<Integer> emSalaryList, String username) {
		String[] message = new String[5];
		int sum = emSalaryList.size();
		int success = 0;
		try {
			int taba_id = dal.addTaskBatch(username, "确认工资信息");
			if (taba_id > 0) {
				for (int esda_id : emSalaryList) {
					try {
						success = success
								+ dal.addTaskBatchRelBusiness(taba_id, esda_id);
					} catch (Exception e) {

					}
				}
				if (success == sum) {
					message[1] = "共确认" + sum + "条数据，全部成功。";
				} else {
					message[1] = "共确认" + sum + "条数据，成功确认" + success + "条数据。";
				}
				message[0] = "1";
				message[2] = String.valueOf(taba_id);
				message[3] = "TaskBatch";
				message[4] = "确认" + sum + "条工资数据。";
			} else {
				message[0] = "0";
				message[1] = "确认工资失败";
			}
		} catch (Exception e) {
			message[0] = "2";
			message[1] = "确认工资出错";
		}
		return message;

	}

}
