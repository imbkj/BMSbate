package bll.EmSalary;

import dal.EmSalary.ItemFormula_OperateDal;
import Model.CoSalaryItemFormulaModel;
import Model.CoSalaryItemIDInfoModel;
import service.WorkflowCore.WfBusinessService;

public class ItemFormula_OperateImpl implements WfBusinessService {
	private ItemFormula_OperateDal ifDal = new ItemFormula_OperateDal();

	@Override
	public String[] Operate(Object[] obj) {
		String[] message = new String[5];
		if (obj[0].toString() == "1") {
			message = addSalaryItemId((CoSalaryItemFormulaModel) obj[1]);
		} else if (obj[0].toString() == "2") {
			message = finishSalaryItemId((CoSalaryItemIDInfoModel) obj[1]);
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
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Boolean UpdateTaprid(int dataId, int tapr_id, int state) {
		ItemFormula_OperateDal dal = new ItemFormula_OperateDal();
		return dal.upCoSalaryItemIDInfoTaprId(tapr_id, dataId);
	}

	@Override
	public Boolean ErrOperate(int dataId) {
		// TODO Auto-generated method stub
		return null;
	}

	// 新增指定月份的默认项目
	public String[] addSalaryItemId(CoSalaryItemFormulaModel m) {
		String[] message = new String[5];
		String[] result = new String[2];
		result[0] = "0";
		try {
			result = ifDal.addSalaryItemId(m);

			if (result[0].equals("1")) {
				message[0] = "0";
				message[1] = "新增工资项目算法设置任务单操作失败!";
			} else {
				message[0] = "1";
				message[1] = "新增工资项目算法设置任务单操作成功!";
			}
		} catch (Exception e) {
			message[0] = "2";
			message[1] = "新增工资项目算法设置任务单操作出错。";
		}
		message[2] = result[1];
		message[3] = "CoSalaryItemIDInfo";
		message[4] = "新增工资项目算法设置任务单。";
		return message;
	}

	// 完成指定月份的默认项目
	public String[] finishSalaryItemId(CoSalaryItemIDInfoModel m) {
		String[] message = new String[5];
		int result = 0;
		try {
			result=ifDal.finishSalaryItemId(m);
			
			if (result == 0) {
				message[0] = "1";
				message[1] = "完成工资项目算法设置成功!";
			} else {
				message[0] = "0";
				message[1] = "完成工资项目算法设置失败!";
			}
		} catch (Exception e) {
			message[0] = "2";
			message[1] = "完成工资项目算法设置操作出错。";
		}
		message[2] = String.valueOf(m.getCsii_id());
		message[3] = "CoSalaryItemIDInfo";
		message[4] = "完成工资项目算法设置。";
		return message;
	}

}
