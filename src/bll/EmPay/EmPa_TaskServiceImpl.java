package bll.EmPay;

import java.util.List;

import service.WorkflowCore.WfBusinessService;
import Model.EmPayBackLogModel;
import Model.EmPayModel;
import Model.EmbaseModel;
import dal.EmPay.EmPa_OperateDal;

public class EmPa_TaskServiceImpl implements WfBusinessService {
	private EmPa_OperateDal dal = new EmPa_OperateDal();

	@SuppressWarnings("unchecked")
	@Override
	public String[] Operate(Object[] obj) {
		String[] str = new String[5];
		
		if (obj[0].equals("新增")) {
			EmPa_OperateBll bll = new EmPa_OperateBll();
			Integer i = bll.batchAdd((List<EmbaseModel>) obj[1],
					String.valueOf(obj[2]));
			Integer id = dal.AddTaskList(String.valueOf(obj[2]));
			;
			if (i > 0) {
				str[0] = "1";
				str[1] = "提交成功";
				str[2] = id.toString();
				str[3] = "EmPayTaskList";
				str[4] = "个人支付_新增";
			} else {
				str[0] = "-1";
				str[1] = "提交失败";
			}

		} else if (obj[0].equals("重新提交")) {
			List<EmPayModel> list = (List<EmPayModel>) obj[2];
			Integer i = 0;
			Integer j = 0;
			for (EmPayModel m : list) {
				j = dal.mod(m);
				if (j < 1) {
					i = -1;
				}
			}
			if (i.equals(0)) {
				i = 1;
			}
			if (i > 0) {
				str[0] = "1";
				str[1] = "提交成功";
				str[2] = obj[1].toString();
				str[3] = "EmPayTaskList";
				str[4] = "个人支付_重新提交";
			} else {
				str[0] = "-1";
				str[1] = "提交失败";
			}
		} else if (obj[0].equals("跳转部门经理")) {
			EmPa_OperateDal dal = new EmPa_OperateDal();
			EmPayModel m = new EmPayModel();
			m.setEmpa_number(obj[1].toString());
			Integer i =dal.ClientMApproval(m);
			if (i > 0) {
				str[0] = "1";
				str[1] = "提交成功";
				str[2] = obj[2].toString();
				str[3] = "EmPayTaskList";
				str[4] = "个人支付_跳过";
			} else {
				str[0] = "-1";
				str[1] = "提交失败";
			}
		}else if (obj[0].equals("审核")) {
			EmPa_OperateBll bll = new EmPa_OperateBll();
			EmPayModel m = (EmPayModel) obj[1];
			Integer i = bll.Approval(m);
			if (i > 0) {
				str[0] = "1";
				str[1] = "提交成功";
				str[2] = m.getPatkId().toString();
				str[3] = "EmPayTaskList";
				str[4] = "个人支付_审核";
			} else {
				str[0] = "-1";
				str[1] = "提交失败";
			}
		}

		return str;
	}

	@Override
	public String[] ReturnOperate(Object[] obj) {
		String[] str = new String[5];
		if (obj[0].equals("退回")) {
			EmPa_OperateBll bll = new EmPa_OperateBll();
			EmPayBackLogModel m = (EmPayBackLogModel) obj[1];
			Integer i = bll.back(m);
			if (i > 0) {
				str[0] = "1";
				str[1] = "提交成功";
				str[2] = obj[2].toString();
				str[3] = "EmPayTaskList";
				str[4] = "个人支付_退回";
			} else {
				str[0] = "-1";
				str[1] = "提交失败";
			}

		}

		return str;
	}

	@Override
	public String[] SkipOperate(Object[] obj) {
		String[] str = new String[5];
		
		return null;
	}

	@Override
	public String[] StopOperate(Object[] obj) {
		String[] str = new String[5];
		Integer i = 0;
		if (obj[0].equals("1")) {

			i = dal.stop(obj[2].toString());
			if (i > 0) {
				str[0] = "1";
				str[1] = "提交成功";
				str[2] = obj[1] + "";
				str[3] = "EmPayTaskList";
				str[4] = "个人支付_终止";
			} else {
				str[0] = "0";
				str[1] = "提交失败";
			}
		}
		return str;
	}

	@Override
	public Boolean UpdateTaprid(int dataId, int tapr_id, int state) {
		return dal.updateTaprid(tapr_id, dataId);
	}

	@Override
	public Boolean ErrOperate(int dataId) {

		return null;
	}

}
