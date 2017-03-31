package bll.EmBenefit;
import dal.EmBenefit.EmWelfareDal;
import Model.EmWelfareModel;
import service.WorkflowCore.WfBusinessService;

public class EmBenefit_bxImpl implements WfBusinessService {

	@Override
	public String[] Operate(Object[] obj) {
		// TODO Auto-generated method stub
		String[] message = new String[5];
		if (obj[0].equals("报销费用")) {
			EmWelfareModel em = new EmWelfareModel();
			em = (EmWelfareModel) obj[1];
			EmBenefit_comitEmListBll bll = new EmBenefit_comitEmListBll();
			Integer i = bll.bx(em);
			if (i > 0) {
				message[0] = "1";
				message[1] = "补缴确认数据";
				message[2] = em.getEmwf_id().toString();
				message[3] = "emhousebj";
				message[4] = "补缴确认数据";
			} else {
				message[0] = "2";
				message[1] = "补缴确认数据出错。";
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
		EmWelfareDal dal = new EmWelfareDal();
		Integer i = dal.updateTaprId(dataId, tapr_id);
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
