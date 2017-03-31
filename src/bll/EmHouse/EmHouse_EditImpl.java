package bll.EmHouse;

import Model.EmHouseChangeModel;
import Model.EmHouseUpdateModel;
import Util.UserInfo;
import dal.EmHouse.EmHouseChangeDal;
import dal.EmHouse.EmHouseUpdateDal;
import service.WorkflowCore.WfBusinessService;

public class EmHouse_EditImpl implements WfBusinessService {

	@Override
	public String[] Operate(Object[] obj) {
		// TODO Auto-generated method stub
		String[] message = new String[5];
		if (obj[0].equals("重新发送")) {

			EmHouseChangeModel em = new EmHouseChangeModel();
			em = (EmHouseChangeModel) obj[1];
			if (em.getEmhc_id() != null) {
				EmHouseChangeDal dal = new EmHouseChangeDal();
				Integer i = dal.mod(em, em.getEmhc_id(), null, null, null);
				if (i > 0) {
					message[0] = "1";
					message[1] = "确认数据";
					message[2] = em.getEmhc_id().toString();
					message[3] = "emhousechange";
					message[4] = "重新发送公积金数据。";
				} else {
					message[0] = "2";
					message[1] = "重新发送公积金数据出错。";
				}
			}
		} else if (obj[0].equals("调回")) {
			EmHouseChangeModel em = new EmHouseChangeModel();
			em = (EmHouseChangeModel) obj[1];
			EmHouse_EditBll bll = new EmHouse_EditBll();
			Integer i =bll.MoveBack(em);
			if (i>0) {
				message[0] = "1";
				message[1] = "审核数据";
				message[2] = i.toString();
				message[3] = "emhousechange";
				message[4] = "跳过";
			}else {
				message[0] = "2";
				message[1] = "调回公积金数据出错。";
			}
			
		} else if (obj[0].equals("跳过审核")) {
			EmHouseChangeModel em = new EmHouseChangeModel();
			em = (EmHouseChangeModel) obj[1];
			message[0] = "1";
			message[1] = "审核数据";
			message[2] = em.getEmhc_id().toString();
			message[3] = "emhousechange";
			message[4] = "跳过";
		} else if (obj[0].equals("审核")) {
			EmHouseChangeModel em = new EmHouseChangeModel();
			em = (EmHouseChangeModel) obj[1];
			EmHouse_EditBll bll = new EmHouse_EditBll();
			Integer i = bll.aduit(em);
			if (i > 0) {
				message[0] = "1";
				message[1] = "审核数据";
				message[2] = i.toString();
				message[3] = "emhousechange";
				message[4] = "审核数据";
			} else {
				message[0] = "2";
				message[1] = "审核数据出错。";
			}

		} else if (obj[0].equals("确认数据")) {
			EmHouseChangeModel em = new EmHouseChangeModel();
			em = (EmHouseChangeModel) obj[1];
			EmHouse_EditBll bll = new EmHouse_EditBll();
			Integer i = bll.modCommitState(em);
			if (i > 0) {
				message[0] = "1";
				message[1] = "确认数据";
				message[2] = i.toString();
				message[3] = "emhousechange";
				message[4] = "确认数据";
			} else {
				message[0] = "2";
				message[1] = "确认数据出错。";
			}

		} else if (obj[0].equals("新增转调入")) {
			EmHouseChangeModel em = new EmHouseChangeModel();
			em = (EmHouseChangeModel) obj[1];
			EmHouse_EditBll bll = new EmHouse_EditBll();
			Integer i = bll.changeMove(em);
			if (i > 0) {
				message[0] = "1";
				message[1] = "提交成功!";
				message[2] = em.getEmhc_id().toString();
				message[3] = "EmHouseChange";
				message[4] = "调入公积金";
			} else {
				message[0] = "2";
				message[1] = "确认数据出错。";
			}

		} else if (obj[0].equals("调入转新增")) {
			EmHouseChangeModel em = new EmHouseChangeModel();
			em = (EmHouseChangeModel) obj[1];
			EmHouse_EditBll bll = new EmHouse_EditBll();
			Integer i = bll.changeNew(em);
			if (i > 0) {
				message[0] = "1";
				message[1] = "提交成功!";
				message[2] = em.getEmhc_id().toString();
				message[3] = "EmHouseChange";
				message[4] = "新增公积金";
			} else {
				message[0] = "2";
				message[1] = "确认数据出错。";
			}

		} else if (obj[0].equals("比例基数调整")) {
			EmHouseChangeModel em = new EmHouseChangeModel();
			em = (EmHouseChangeModel) obj[1];
			EmHouse_EditBll bll = new EmHouse_EditBll();
			Integer i = bll.changeinfo(em);
			if (i > 0) {
				message[0] = "1";
				message[1] = "提交成功!";
				message[2] = i.toString();
				message[3] = "EmHouseChange";
				message[4] = "比例基数调整";
			} else {
				message[0] = "2";
				message[1] = "调整数据出错。";
			}
		} else if (obj[0].equals("调整账户")) {
			EmHouseChangeModel em = new EmHouseChangeModel();
			em = (EmHouseChangeModel) obj[1];
			EmHouse_EditBll bll = new EmHouse_EditBll();
			Integer i = bll.changeinfo(em);
			if (i > 0) {
				message[0] = "1";
				message[1] = "提交成功!";
				message[2] = i.toString();
				message[3] = "EmHouseChange";
				message[4] = "转账户";
			} else {
				message[0] = "2";
				message[1] = "转账户数据出错。";
			}
		} else if (obj[0].equals("补充转账户流程")) {
			EmHouseChangeModel em = new EmHouseChangeModel();
			em = (EmHouseChangeModel) obj[1];

			message[0] = "1";
			message[1] = "提交成功!";
			message[2] = em.getEmhc_id().toString();
			message[3] = "EmHouseChange";
			message[4] = "转账户";

		}else if (obj[0].equals("跳过确认")) {
			EmHouseChangeModel em = new EmHouseChangeModel();
			em = (EmHouseChangeModel) obj[1];
			message[0] = "1";
			message[1] = "确认数据";
			message[2] = em.getEmhc_id().toString();
			message[3] = "emhousechange";
			message[4] = "确认数据";
		}else if(obj[0].equals("年度调基")) {
			EmHouseUpdateModel em = new EmHouseUpdateModel();
			em = (EmHouseUpdateModel) obj[1];
			EmHouseUpdateDal dal = new EmHouseUpdateDal();
			Integer i = dal.salery(em);
			message[0] = "1";
			message[1] = "年度调基";
			message[2] = i.toString();
			message[3] = "emhousechange";
			message[4] = "新增数据";
			
		}else if (obj[0].equals("新增任务单")) {
			EmHouseChangeModel em = new EmHouseChangeModel();
			em = (EmHouseChangeModel) obj[1];
			EmHouse_EditBll bll = new EmHouse_EditBll();
			Integer i =bll.modInfo(em);
			if (i>0) {
				message[0] = "1";
				message[1] = "新增数据";
				message[2] = i.toString();
				message[3] = "emhousechange";
				message[4] = "新增数据";
			}else {
				message[0] = "2";
				message[1] = "新增公积金任务单出错。";
			}
			
		}else if (obj[0].equals("调入任务单")) {
			EmHouseChangeModel em = new EmHouseChangeModel();
			em = (EmHouseChangeModel) obj[1];
			EmHouse_EditBll bll = new EmHouse_EditBll();
			Integer i =bll.modInfo(em);
			if (i>0) {
				message[0] = "1";
				message[1] = "调入数据";
				message[2] = i.toString();
				message[3] = "emhousechange";
				message[4] = "调入数据";
			}else {
				message[0] = "2";
				message[1] = "调入公积金任务单出错。";
			}
		}

		return message;
	}

	@Override
	public String[] ReturnOperate(Object[] obj) {
		// TODO Auto-generated method stub
		String[] message = new String[5];
		if (obj[0].equals("退回数据")) {
			EmHouseChangeModel em = new EmHouseChangeModel();
			em = (EmHouseChangeModel) obj[1];
			EmHouseChangeDal dal = new EmHouseChangeDal();
			Integer i = dal.mod(em, em.getEmhc_id(), null, null, null);
			if (i > 0) {
				message[0] = "1";
				message[1] = "退回数据";
				message[2] = em.getEmhc_id().toString();
				message[3] = "emhousechange";
				message[4] = UserInfo.getUsername()+"退回公积金数据。";
			} else {
				message[0] = "2";
				message[1] = "公积金变更退回出错。";
			}
		} else if (obj[0].equals("返回待确认")) {
			EmHouseChangeModel em = new EmHouseChangeModel();
			em = (EmHouseChangeModel) obj[1];
			EmHouse_EditBll bll = new EmHouse_EditBll();
			Integer i = bll.modCommitState(em);
			if (i > 0) {
				message[0] = "1";
				message[1] = "撤回数据";
				message[2] = i.toString();
				message[3] = "emhousechange";
				message[4] = UserInfo.getUsername()+"返回确认数据";
			} else {
				message[0] = "2";
				message[1] = "返回确认数据出错。";
			}
		}
		return message;
	}

	@Override
	public String[] SkipOperate(Object[] obj) {
		// TODO Auto-generated method stub
		String[] message = new String[5];

		if (obj[0].equals("确认数据")) {
			EmHouseChangeModel em = new EmHouseChangeModel();
			em = (EmHouseChangeModel) obj[1];
			EmHouse_EditBll bll = new EmHouse_EditBll();
			Integer i = bll.modCommitState(em);
			if (i > 0) {
				message[0] = "1";
				message[1] = "确认数据";
				message[2] = i.toString();
				message[3] = "emhousechange";
				message[4] = "确认数据";
			} else {
				message[0] = "2";
				message[1] = "确认数据出错。";
			}

		}

		return message;
	}

	@Override
	public String[] StopOperate(Object[] obj) {
		// TODO Auto-generated method stub
		String[] message = new String[5];

		if (obj[0].equals("新增转调入")) {
			EmHouseChangeModel em = new EmHouseChangeModel();
			em = (EmHouseChangeModel) obj[1];
			EmHouseChangeDal dal = new EmHouseChangeDal();
			
			message[0] = "1";
			message[1] = "新增转调入流程";
			message[2] = em.getEmhc_id().toString();
			message[3] = "emhousechange";
			message[4] = "终止流程";
		} else if (obj[0].equals("调入转新增")) {
			EmHouseChangeModel em = new EmHouseChangeModel();
			em = (EmHouseChangeModel) obj[1];
			message[0] = "1";
			message[1] = "调入转新增流程";
			message[2] = em.getEmhc_id().toString();
			message[3] = "emhousechange";
			message[4] = "终止流程";
		}else if(obj[0].equals("删除数据")){
			EmHouseChangeModel em = new EmHouseChangeModel();
			em = (EmHouseChangeModel) obj[1];
			EmHouse_EditBll bll = new EmHouse_EditBll();
			
			Integer i = bll.del(em.getEmhc_id());
			
			EmHouseChangeDal dal = new EmHouseChangeDal();
			dal.updateRadixData(em.getEmhc_id(), em.getEmhc_tid());
			
			if (i>0) {
				message[0] = "1";
				message[1] = "删除数据";
				message[2] = em.getEmhc_id().toString();
				message[3] = "emhousechange";
				message[4] = "终止流程";
			}else {
				message[0] = "2";
				message[1] = "删除数据出错。";
			}
		}
		return message;
	}

	@Override
	public Boolean UpdateTaprid(int dataId, int tapr_id, int state) {
		// TODO Auto-generated method stub
		EmHouseChangeDal dal = new EmHouseChangeDal();
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
