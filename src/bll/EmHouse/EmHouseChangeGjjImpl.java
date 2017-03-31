package bll.EmHouse;

import Model.EmHouseChangeGJJModel;

import dal.EmHouse.EmHouseChangeGjjDal;
import service.WorkflowCore.WfBusinessService;

public class EmHouseChangeGjjImpl implements WfBusinessService {

	@Override
	public String[] Operate(Object[] obj) {
		// TODO Auto-generated method stub
		String[] message = new String[5];
		if (obj[0].equals("新增")) {
			Integer i = 0;
			/*
			 * EmHouseChangeGjjDal dal = new EmHouseChangeGjjDal();
			 * EmHouseChangeGJJModel em = (EmHouseChangeGJJModel) obj[1]; i =
			 * dal.add(em);
			 */
			if (i > 0) {
				message[0] = "1";
				message[1] = "提交成功!";
				message[2] = i.toString();
				message[3] = "EmHouseChangeGJJ";
				message[4] = "申请变更";
			} else {
				message[0] = "0";
				message[1] = "修改失败!";

			}
		} else if (obj[0].equals("再次提交")) {
			Integer i = 0;
			EmHouseChangeGjjDal dal = new EmHouseChangeGjjDal();
			EmHouseChangeGJJModel em = (EmHouseChangeGJJModel) obj[1];
			i = dal.Mod(em, em.getEhcg_id());
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
		}else if (obj[0].equals("重新发送")) {
			EmHouseChangeGJJModel em = new EmHouseChangeGJJModel();
			em = (EmHouseChangeGJJModel) obj[1];
			EmHouse_EditBll bll = new EmHouse_EditBll();
			Integer i =bll.ModGjj(em);
			if (i>0) {
				message[0] = "1";
				message[1] = "发送成功!";
				message[2] = em.getEhcg_id().toString();
				message[3] = "EmHouseChangeGJJ";
				message[4] = "重新发送";
			}else {
				message[0] = "2";
				message[1] = "重新发送出错。";
			}
		}

		return message;
	}

	@Override
	public String[] ReturnOperate(Object[] obj) {
		// TODO Auto-generated method stub

		String[] message = new String[5];
		if (obj[0].equals("交单退回")) {
			EmHouseChangeGJJModel em = new EmHouseChangeGJJModel();
			em = (EmHouseChangeGJJModel) obj[1];
			EmHouse_EditBll bll = new EmHouse_EditBll();
			Integer i = bll.ModGjj(em);

			if (i > 0) {
				message[0] = "1";
				message[1] = "提交成功!";
				message[2] = em.getEhcg_id().toString();
				message[3] = "EmHouseChangeGJJ";
				message[4] = "退回交单变更数据";
			} else {
				message[0] = "2";
				message[1] = "退回交单变更数据出错。";
			}
		}
		return message;
	}

	@Override
	public String[] SkipOperate(Object[] obj) {
		// TODO Auto-generated method stub
		String[] message = new String[5];
		if (obj[0].equals("重新发送")) {
			EmHouseChangeGJJModel em = new EmHouseChangeGJJModel();
			em = (EmHouseChangeGJJModel) obj[1];
			EmHouse_EditBll bll = new EmHouse_EditBll();
			Integer i =bll.ModGjj(em);
			if (i>0) {
				message[0] = "1";
				message[1] = "发送成功!";
				message[2] = em.getEhcg_id().toString();
				message[3] = "EmHouseChangeGJJ";
				message[4] = "重新发送";
			}else {
				message[0] = "2";
				message[1] = "重新发送出错。";
			}
		} else {
			message[0] = "1";
			message[1] = "提交成功!";
			message[2] = obj[0].toString();
			message[3] = "EmHouseChangeGJJ";
			message[4] = "跳过流程";
		}

		return message;
	}

	@Override
	public String[] StopOperate(Object[] obj) {
		// TODO Auto-generated method stub
		String[] message = new String[5];
		if (obj[0].equals("删除交单数据")) {
			EmHouseChangeGJJModel em = new EmHouseChangeGJJModel();
			em = (EmHouseChangeGJJModel) obj[1];
			EmHouse_EditBll bll = new EmHouse_EditBll();
			Integer i = bll.delGjj(em.getEhcg_id());
			if (i > 0) {
				message[0] = "1";
				message[1] = "删除数据";
				message[2] = em.getEhcg_id().toString();
				message[3] = "emhousechangegjj";
				message[4] = "终止流程";
			} else {
				message[0] = "2";
				message[1] = "删除数据出错。";
			}
		}
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
