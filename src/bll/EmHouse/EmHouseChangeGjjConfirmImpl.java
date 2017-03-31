package bll.EmHouse;

import Model.EmHouseChangeGJJModel;

import dal.EmHouse.EmHouseChangeGjjDal;
import service.WorkflowCore.WfBusinessService;

public class EmHouseChangeGjjConfirmImpl implements WfBusinessService {

	@Override
	public String[] Operate(Object[] obj) {
		// TODO Auto-generated method stub
		String[] message = new String[5];
		Integer i = 0;

		if (obj[0].equals("确认数据")) {
			EmHouseChangeGJJModel em = (EmHouseChangeGJJModel) obj[1];

			EmHouseChangeGjjDal dal = new EmHouseChangeGjjDal();

			i = dal.Mod(em, em.getEhcg_id());

			//dal.ModRe(em, em.getEhcg_id());

			if (i > 0) {
				message[0] = "1";
				message[1] = "提交成功!";
				message[2] = em.getEhcg_id().toString();
				message[3] = "EmHouseChangeGJJ";
				message[4] = "确认变更";
			} else {
				message[0] = "0";
				message[1] = "修改失败!";

			}
		}else if (obj[0].equals("申报数据")) {
			EmHouseChangeGJJModel em = (EmHouseChangeGJJModel) obj[1];
			EmHouseChangeGjjDal dal = new EmHouseChangeGjjDal();

			i = dal.Mod(em, em.getEhcg_id());

			//dal.ModRe(em, em.getEhcg_id());
			if (i > 0) {
				message[0] = "1";
				message[1] = "提交成功!";
				message[2] = em.getEhcg_id().toString();
				message[3] = "EmHouseChangeGJJ";
				message[4] = "申请变更";
			} else {
				message[0] = "0";
				message[1] = "修改失败!";

			}
		} 

		return message;
	}

	@Override
	public String[] ReturnOperate(Object[] obj) {
		// TODO Auto-generated method stub
		String[] message = new String[5];
		Integer i = 0;
		
		EmHouseChangeGJJModel em = (EmHouseChangeGJJModel) obj[1];
		EmHouseChangeGjjDal dal = new EmHouseChangeGjjDal();

		i = dal.Mod(em, em.getEhcg_id());

		//dal.ModRe(em, em.getEhcg_id());
		if (i > 0) {
			message[0] = "1";
			message[1] = "提交成功!";
			message[2] = em.getEhcg_id().toString();
			message[3] = "EmHouseChangeGJJ";
			message[4] = "退回变更";
		} else {
			message[0] = "0";
			message[1] = "修改失败!";

		}
		return message;
	}

	@Override
	public String[] SkipOperate(Object[] obj) {
		// TODO Auto-generated method stub
		String[] message = new String[5];
		Integer i = 0;
		
		EmHouseChangeGJJModel em = (EmHouseChangeGJJModel) obj[1];
		EmHouseChangeGjjDal dal = new EmHouseChangeGjjDal();

		i = dal.Mod(em, em.getEhcg_id());

		//dal.ModRe(em, em.getEhcg_id());
		if (i > 0) {
			message[0] = "1";
			message[1] = "提交成功!";
			message[2] = em.getEhcg_id().toString();
			message[3] = "EmHouseChangeGJJ";
			message[4] = "申请变更";
		} else {
			message[0] = "0";
			message[1] = "修改失败!";

		}
		return message;
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
		EmHouseChangeGjjDal dal = new EmHouseChangeGjjDal();
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
