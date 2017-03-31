package bll.EmZYT;

import Model.EmZYTModel;
import dal.EmZYT.EmZYT_OperateDal;
import service.WorkflowCore.WfBusinessService;

public class EmZYT_OperateImpl implements WfBusinessService {

	@Override
	public String[] Operate(Object[] obj) {
		String[] message = new String[5];
		String i = obj[0].toString();

		if ("0".equals(i)) {// 任务单启动
			message[0] = "1";
			message[1] = obj[2] + "成功";
			message[2] = obj[1].toString();
			message[3] = "EmZYT";
			message[4] = obj[2].toString();
		} else if ("1".equals(i)) {// 已处理
			message = upEmZYTState(obj);
		} else if ("4".equals(i)) {// 确认调整单
			message = upAdjust(obj);
		} else if ("5".equals(i)) {// 确认非调整单，非新增
			message = upEmZYTState(obj);
		}

		return message;
	}

	@Override
	public String[] ReturnOperate(Object[] obj) {
		String[] message = new String[5];
		String i = obj[0].toString();
		if ("2".equals(i)) {// 退单(待跟踪)
			message = upEmZYTState(obj);
		}
		return message;
	}

	@Override
	public String[] SkipOperate(Object[] obj) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String[] StopOperate(Object[] obj) {
		String[] message = new String[5];
		String i = obj[0].toString();
		if ("3".equals(i)) {// 退单
			message = upEmZYTState(obj);
		}
		return message;
	}

	@Override
	public Boolean UpdateTaprid(int dataId, int tapr_id, int state) {
		EmZYT_OperateDal dal = new EmZYT_OperateDal();
		return dal.upEmZYTTaprId(tapr_id, dataId);
	}

	@Override
	public Boolean ErrOperate(int dataId) {
		// TODO Auto-generated method stub
		return null;
	}

	// 修改数据状态
	private String[] upEmZYTState(Object[] obj) {
		EmZYT_OperateDal dal = new EmZYT_OperateDal();
		int result = 0;
		String[] message = new String[5];
		int id = Integer.parseInt(obj[1].toString());
		result = dal.upEmZYTState((EmZYTModel) obj[2]);

		if (result == 0) {
			message[0] = "1";
			message[1] = "智翼通接口操作成功!";
			message[2] = String.valueOf(id);
			message[3] = "EmZYT";
			message[4] = "智翼通接口操作成功";
		} else {
			message[0] = "0";
			message[1] = "智翼通接口操作失败!";
		}
		return message;
	}

	// 确认调整单
	private String[] upAdjust(Object[] obj) {
		EmZYT_OperateDal dal = new EmZYT_OperateDal();
		int result = 0;
		String[] message = new String[5];
		int id = Integer.parseInt(obj[1].toString());
		result = dal.upAdjust((EmZYTModel) obj[2]);

		if (result == 0) {
			message[0] = "1";
			message[1] = "智翼通接口操作成功!";
			message[2] = String.valueOf(id);
			message[3] = "EmZYT";
			message[4] = "智翼通接口操作成功";
		} else {
			message[0] = "0";
			message[1] = "智翼通接口操作失败!";
		}
		return message;
	}
}
