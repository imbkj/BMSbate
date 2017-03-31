package bll.EmSalary;

import java.text.SimpleDateFormat;
import java.util.Date;

import Model.CoSalaryItemFormulaModel;
import Model.CoSalaryItemIDInfoModel;
import Model.EmSalaryBaseAdd_viewModel;
import Model.EmSalaryBaseSel_viewModel;
import dal.EmSalary.EmSalary_EditDal;
import dal.EmSalary.ItemFormula_OperateDal;
import service.WorkflowCore.WfBusinessService;

public class EmSalary_DataOperateImpl implements WfBusinessService {
	private ItemFormula_OperateDal ifDal = new ItemFormula_OperateDal();

	@Override
	public String[] Operate(Object[] obj) {
		String[] message = new String[5];
		if (obj[0].toString() == "1") {
			message = repayReason((EmSalaryBaseSel_viewModel) obj[1],
					obj[2].toString());
		} else if (obj[0].toString() == "2") {
			message[0] = "1";
			message[1] = "通知客服修改员工银行信息操作成功!";
			message[2] = String.valueOf(obj[1]);
			message[3] = "EmSalaryData";
			message[4] = "员工工资重发";
		} else if (obj[0].toString() == "3") {
			message = upBankInfo((EmSalaryBaseSel_viewModel) obj[1],
					(EmSalaryBaseAdd_viewModel) obj[2], obj[3].toString());
		} else if (obj[0].toString() == "4") {
			message = repay((EmSalaryBaseSel_viewModel) obj[1]);
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
		return dal.upEmSalaryDataTaprId(tapr_id, dataId);
	}

	@Override
	public Boolean ErrOperate(int dataId) {
		// TODO Auto-generated method stub
		return null;
	}

	// 新增指定月份的默认项目
	public String[] repayReason(EmSalaryBaseSel_viewModel m, String username) {
		String[] message = new String[5];
		int result;
		result = 0;
		try {
			result = ifDal.repayReason(m, username);

			if (result == 0) {
				message[0] = "1";
				message[1] = "员工工资重发操作成功!";
			} else {
				message[0] = "0";
				message[1] = "员工工资重发操作失败!";
			}
		} catch (Exception e) {
			message[0] = "2";
			message[1] = "员工工资重发操作出错。";
		}
		message[2] = String.valueOf(m.getEsda_id());
		message[3] = "EmSalaryData";
		message[4] = "员工工资重发";
		return message;
	}

	// 修改银行信息
	public String[] upBankInfo(EmSalaryBaseSel_viewModel esdaM,
			EmSalaryBaseAdd_viewModel embaM, String username) {
		String[] message = new String[5];
		int result;
		result = 0;
		try {
			result = ifDal.upBankInfo(esdaM, embaM, username);

			if (result == 0) {
				message[0] = "1";
				message[1] = "修改员工银行信息操作成功!";
			} else {
				message[0] = "0";
				message[1] = "修改员工银行信息操作失败!";
			}
		} catch (Exception e) {
			message[0] = "2";
			message[1] = "修改员工银行信息操作出错。";
		}
		message[2] = String.valueOf(esdaM.getEsda_id());
		message[3] = "EmSalaryData";
		message[4] = "修改员工银行信息";
		return message;
	}

	// 工资重发
	public String[] repay(EmSalaryBaseSel_viewModel esdaM) {
		EmSalary_EditDal eDal=new EmSalary_EditDal();
		String[] message = new String[5];
		int result;
		result = 0;
		try {
			String nowtime = "";
			SimpleDateFormat sdf = new SimpleDateFormat(
						"yyyy-MM-dd HH:mm:00");
			Date date = new Date();
			nowtime = sdf.format(date);

			result = eDal.pay(esdaM.getEsda_id(),nowtime);

			if (result > 0) {
				message[0] = "1";
				message[1] = "工资重发操作成功!";
			} else {
				message[0] = "0";
				message[1] = "工资重发操作失败!";
			}
		} catch (Exception e) {
			message[0] = "2";
			message[1] = "工资重发操作出错。";
		}
		message[2] = String.valueOf(esdaM.getEsda_id());
		message[3] = "EmSalaryData";
		message[4] = "工资重发";
		return message;
	}
}
