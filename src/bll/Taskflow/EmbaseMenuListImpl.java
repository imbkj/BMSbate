package bll.Taskflow;


import Model.EmbaseModel;
import Util.UserInfo;

import dal.Embase.Embasedal;
import service.WorkflowCore.WfBusinessService;

public class EmbaseMenuListImpl implements WfBusinessService {

	@Override
	public String[] Operate(Object[] obj) {
		// TODO Auto-generated method stub
		Embasedal dal = new Embasedal();
		EmbaseModel em = new EmbaseModel();
		em.setEmba_modname(UserInfo.getUsername());
		em.setEmba_state(1);
		em.setEmba_id(Integer.valueOf(obj[0].toString()));
		Integer i=dal.modInfo(em);
		String[] str = new String[5];
		if (i>0) {
			str[0] = "1";
			str[1] = "分配成功!";
			str[2] = obj[0].toString() + "";
			str[3] = "Embase";
			str[4] = "员工任务分配";
		}else {
			str[0] = "0";
			str[1] = "操作失败!";
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
		// EmBaseLogin_AddDal dal = new EmBaseLogin_AddDal();
		// dal.UpdateTaprid(dataId, tapr_id);
		Embasedal dal = new Embasedal();

		dal.UpdateTaprid(dataId, tapr_id);

		return true;
	}

	@Override
	public Boolean ErrOperate(int dataId) {
		// TODO Auto-generated method stub
		return null;
	}

}
