package bll.Embase;

import java.sql.SQLException;

import dal.Taskflow.TaskBatchDal;
import service.WorkflowCore.WfBusinessService;

public class EmBase_DistributeImpl implements WfBusinessService {

	@Override
	public String[] Operate(Object[] obj) {
		// TODO Auto-generated method stub
		Integer i = 0;
		String[] str = new String[5];
		TaskBatchDal dal = new TaskBatchDal();
		try {
			if (obj[0].equals(1)) {

				i = dal.add(obj[1].toString(), obj[2].toString(),
						obj[3].toString(), Integer.valueOf(obj[4].toString()));
				if (i > 0) {
					str[0] = "1";
					str[1] = "分配成功!";
					str[2] = i.toString() + "";
					str[3] = "TaskBatch";
					str[4] = "员工业务分配";
				} else {
					str[0] = "0";
					str[1] = "分配失败,请联系IT部门!";
				}
			} else if (obj[0].equals(2)) {
				str[0] = "1";
				str[1] = "任务完成!";
				str[2] = obj[1].toString();
				str[3] = "TaskBatch";
				str[4] = "员工业务分配";
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return str;
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
		// TODO Auto-generated method stub
		TaskBatchDal dal = new TaskBatchDal();
		try {
			dal.UpdateTaprid(dataId, tapr_id);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return true;
	}

	@Override
	public Boolean ErrOperate(int dataId) {
		// TODO Auto-generated method stub
		return null;
	}

}
