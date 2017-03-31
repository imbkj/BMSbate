package bll.EmBodyCheck;

import dal.EmBodyCheck.EmBcInfo_OperatetDal;
import Model.EmBodyCheckCommitModel;
import service.WorkflowCore.WfBusinessService;

public class EmBcInfoCommitImpl implements WfBusinessService {

	@Override
	public String[] Operate(Object[] obj) {
		String[] str = new String[5];
		try {
			EmBodyCheckCommitModel em = (EmBodyCheckCommitModel) obj[1];
			if (obj[0].equals("年度体检新增")) {
				// 年度体检新增
				EmBcInfo_OperatetDal dal = new EmBcInfo_OperatetDal();
				Integer k = dal.embodycheckAddYear(em);
				if (k > 0) {
					str[0] = "1";
					str[1] = "提交成功";
					str[2] = k.toString();
					str[3] = "EmBodyCheck";
					str[4] = "体检新增";
				} else {
					str[0] = "0";
					str[1] = "提交失败";
				}
			}
		} catch (Exception e) {
			System.out.print("错误：" + e.getMessage());
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
		EmBcInfo_OperatetDal dal = new EmBcInfo_OperatetDal();
		return dal.updateEmbodyCheckTaprid(dataId, tapr_id);
	}

	@Override
	public Boolean ErrOperate(int dataId) {
		// TODO Auto-generated method stub
		return null;
	}

}
