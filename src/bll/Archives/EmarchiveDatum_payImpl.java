package bll.Archives;


import Model.EmGatheringModel;
import bll.EmFinanceManage.emgt_OperateBll;
import dal.Archives.EmArchiveDatumDal;
import service.WorkflowCore.WfBusinessService;

public class EmarchiveDatum_payImpl implements WfBusinessService {

	@Override
	public String[] Operate(Object[] obj) {
		// TODO Auto-generated method stub

		String[] message = new String[5];
		if (obj[0].equals("财务录入费用")) {

			EmGatheringModel em = new EmGatheringModel();
			em = (EmGatheringModel) obj[1];
			emgt_OperateBll bll = new emgt_OperateBll();
			Integer i = bll.EmGatheringAdd(em);

			if (i > 0) {
				message[0] = "1";
				message[1] = "提交成功!";
				message[2] = obj[2].toString();
				message[3] = "emarchivedatum";
				message[4] = "财务结算费用";
			} else {
				message[0] = "2";
				message[1] = "提交出错。";
			}
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
		// TODO Auto-generated method stub
		Integer i = 0;
		EmArchiveDatumDal dal = new EmArchiveDatumDal();
		try {
			i = dal.updateTaprId(dataId, tapr_id);
		} catch (Exception e) {
			e.printStackTrace();
		}
		if (i > 0) {
			return true;
		} else {
			return false;
		}
	}

	@Override
	public Boolean ErrOperate(int dataId) {
		// TODO Auto-generated method stub
		return null;
	}

}
