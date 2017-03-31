package bll.Embase;

import Model.EmbaseModel;
import dal.Embase.CoglistDal;
import dal.Embase.EmBaseLogin_AddDal;
import dal.Embase.EmOnBoardListDal;
import dal.Embase.Embasedal;
import service.WorkflowCore.WfBusinessService;

public class Embase_OnBoardImpl implements WfBusinessService {

	@Override
	public String[] Operate(Object[] obj) {
		// TODO Auto-generated method stub
		Integer i = 0;
		String[] str = new String[5];

		if (obj[0].equals("员工入职")) {
			EmbaseModel em = (EmbaseModel) obj[1];
			EmBase_OnBoardBll bll = new EmBase_OnBoardBll();
			i = bll.modInfo(em);
			if (i > 0) {
				str[0] = "1";
				str[1] = "分配成功!";
				str[2] = em.getEmba_id().toString();
				str[3] = "EmBase";
				str[4] = "员工项目分配";
			} else {
				str[0] = "0";
				str[1] = "分配失败,请联系IT部门!";
			}
		} else if (obj[0].equals("确认信息")) {
			EmbaseModel em = (EmbaseModel) obj[1];
			EmBase_OnBoardBll bll = new EmBase_OnBoardBll();
			i = bll.modInfo(em);
			if (i > 0) {
				str[0] = "1";
				str[1] = "确认成功";
				str[2] = em.getEmba_id().toString();
				str[3] = "EmBase";
				str[4] = "确认信息";
			} else {
				str[0] = "0";
				str[1] = "确认信息失败,请联系IT部门!";
			}
		}else if (obj[0].equals("薪酬")) {
			Integer id = Integer.valueOf(obj[1].toString());
			Integer gid = Integer.valueOf(obj[2].toString());
			Integer ownmonth = Integer.valueOf(obj[3].toString());
			EmBase_OnBoardBll bll = new EmBase_OnBoardBll();
			if (bll.updateInfo(id,gid, ownmonth)) {
				str[0] = "1";
				str[1] = "操作成功";
				str[2] = id.toString();
				str[3] = "EmBase";
				str[4] = "薪酬信息跳过";	
			}else {
				str[0] = "0";
				str[1] = "薪酬信息跳过失败,请联系IT部门!";
			}
			
		}

		return str;
	}

	@Override
	public String[] ReturnOperate(Object[] obj) {
		Integer i = 0;
		String[] str = new String[5];
		if (obj[0].equals("退回上一步")) {
			EmbaseModel em = (EmbaseModel) obj[1];
			EmBase_OnBoardBll bll = new EmBase_OnBoardBll();
			i = bll.modInfo(em);
			if (i > 0) {
				str[0] = "1";
				str[1] = "退回成功";
				str[2] = em.getEmba_id().toString();
				str[3] = "EmBase";
				str[4] = "退回";
			} else {
				str[0] = "0";
				str[1] = "退回失败,请联系IT部门!";
			}
		}else if (obj[0].equals("返回报价单")) {
			EmbaseModel em = (EmbaseModel) obj[1];
			EmBase_OnBoardBll bll = new EmBase_OnBoardBll();
			i = bll.modInfo(em);
			if (i > 0) {
				str[0] = "1";
				str[1] = "返回上一步";
				str[2] = em.getEmba_id().toString();
				str[3] = "EmBase";
				str[4] = "返回";
			} else {
				str[0] = "0";
				str[1] = "返回失败,请联系IT部门!";
			}
		}
		return str;
	}

	@Override
	public String[] SkipOperate(Object[] obj) {
		// TODO Auto-generated method stub
		String[] str = new String[5];
		Integer i = 0;
		if (obj[0].equals("员工入职")) {
			EmbaseModel em = (EmbaseModel) obj[1];
			EmBase_OnBoardBll bll = new EmBase_OnBoardBll();
			i = bll.modInfo(em);
			str[0] = "1";
			str[1] = "跳过";
			str[2] = em.getEmba_id().toString();
			str[3] = "EmBase";
			str[4] = "跳过";
		}
		return str;
	}

	@Override
	public String[] StopOperate(Object[] obj) {
		// TODO Auto-generated method stub
		String[] str = new String[5];
		if (obj[0].equals("取消入职")) {
			EmbaseModel em = (EmbaseModel) obj[1];
			Embasedal dal = new Embasedal();
			Integer i= dal.modInfo(em);
			if (i > 0) {
				str[0] = "1";
				str[1] = "取消入职";
				str[2] = em.getEmba_id().toString();
				str[3] = "EmBase";
				str[4] = "终止";
			} else {
				str[0] = "0";
				str[1] = "操作失败,请联系IT部门!";
			}
		}
		return str;
	}

	@Override
	public Boolean UpdateTaprid(int dataId, int tapr_id, int state) {
		// TODO Auto-generated method stub

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
