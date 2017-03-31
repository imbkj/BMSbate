package bll.EmSheBao;

import dal.EmSheBao.Emsi_OperateDal;
import service.WorkflowCore.WfBusinessService;

public class Emsi_AduitPassImpl implements WfBusinessService {
	private Emsi_OperateDal dal;

	public Emsi_AduitPassImpl() {
		dal = new Emsi_OperateDal();
	}

	@Override
	public String[] Operate(Object[] obj) {
		String[] message = new String[5];
		try {
			boolean bool = dal.upEmShebaoChangeIfdeclare(Integer.valueOf(obj[0]
					.toString()));
			if(bool){
				message[0] = "1";
				message[1] = "社保审核成功！";
				message[2] = obj[0].toString();
				message[3] = "EmShebaoChange";
				message[4] = "审核通过";
			}else{
				message[0] = "0";
				message[1] = "社保审核失败！";
			}
		} catch (Exception e) {
			message[0] = "2";
			message[1] = "社保审核出错！";
			e.printStackTrace();
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
		return dal.upEmShebaoChangeTaprId(tapr_id, dataId);
	}

	@Override
	public Boolean ErrOperate(int dataId) {
		// TODO Auto-generated method stub
		return null;
	}

}
