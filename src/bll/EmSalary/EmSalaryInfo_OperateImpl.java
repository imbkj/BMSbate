package bll.EmSalary;

import java.util.List;

import Model.CoSalaryItemIDInfoModel;
import Model.EmBaseESInCFInModel;
import Model.EmSalaryInfoModel;
import dal.EmSalary.EmSalary_SalarySelDal;
import dal.EmSalary.ItemFormula_OperateDal;
import service.WorkflowCore.WfBusinessService;

public class EmSalaryInfo_OperateImpl implements WfBusinessService {
	private ItemFormula_OperateDal ifDal = new ItemFormula_OperateDal();

	@Override
	public String[] Operate(Object[] obj) {
		String[] message = new String[5];
		if (obj[0].toString() == "1") {
			message = addEmSalaryInfo((EmSalaryInfoModel) obj[1]);
		} else if (obj[0].toString() == "2") {
			message = finishEmSalaryInfo((EmBaseESInCFInModel) obj[1]);
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
		return ifDal.upEmSalaryInfoTaprId(tapr_id, dataId);
	}

	@Override
	public Boolean ErrOperate(int dataId) {
		// TODO Auto-generated method stub
		return null;
	}

	// 完成指定月份的默认项目
	public String[] addEmSalaryInfo(EmSalaryInfoModel m) {
		String[] message = new String[5];
		List<EmSalaryInfoModel> list;
		EmSalary_SalarySelDal dal = new EmSalary_SalarySelDal();
		list = dal.getEmSalaryInfo(" AND a.cid=" + m.getCid() + " AND a.gid="
				+ m.getGid());

		message[0] = "1";
		message[1] = "新增员工工资算法分配任务单成功!";
		message[2] = String.valueOf(list.get(0).getEsin_id());
		message[3] = "EmSalaryInfo";
		message[4] = "新增员工工资算法分配任务单。";
		return message;
	}

	// 完成指定月份的默认项目
	public String[] finishEmSalaryInfo(EmBaseESInCFInModel m) {
		String[] message = new String[5];
		int result = 0;
		try {
			result = ifDal.assFormulaInfo(m);

			if (result == 0) {
				message[0] = "1";
				message[1] = "员工工资算法分配成功!";
			} else {
				message[0] = "0";
				message[1] = "员工工资算法分配失败!";
			}
		} catch (Exception e) {
			message[0] = "2";
			message[1] = "员工工资算法分配出错。";
		}
		message[2] = String.valueOf(m.getEsin_id());
		message[3] = "CoSalaryItemIDInfo";
		message[4] = "员工工资算法分配";
		return message;
	}
}
