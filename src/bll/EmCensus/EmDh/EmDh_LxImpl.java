package bll.EmCensus.EmDh;

import Model.EmDhModel;
import dal.EmCensus.EmDh.EmDh_OperateDal;
import service.WorkflowCore.WfBusinessService;

public class EmDh_LxImpl implements WfBusinessService {
	private EmDh_OperateDal dal = new EmDh_OperateDal();

	@Override
	public String[] Operate(Object[] obj) {
		String[] str = new String[5];
		String type = obj[0].toString();
		EmDhModel m = (EmDhModel) obj[1];
		String sql = obj[2].toString();
		int k = 0;
		if (type.equals("交接材料") || type.equals("条件审核") || type.equals("信息预审")
				|| type.equals("预审确认")|| type.equals("代理部受理")|| type.equals("上报材料")
				|| type.equals("介绍信下达")|| type.equals("领取介绍信")) {
			k = dal.UpdateEmdhInfo(m, sql);
		}

		if (k > 0) {
			str[0] = "1";
			str[1] = "提交成功";
			str[2] = m.getId() + "";
			str[3] = "EmDH";
			str[4] = type;
		} else {
			str[0] = "0";
			str[1] = "提交失败";
		}
		return str;
	}

	@Override
	public String[] ReturnOperate(Object[] obj) {
		String operCon = (String) obj[0];
		EmDhModel model = (EmDhModel) obj[1];
		String sql = (String) obj[2];
		int k = dal.UpdateEmdhInfo(model, sql);
		String[] str = new String[5];
		if (k > 0) {
			str[0] = "1";
			str[1] = "提交成功";
			str[2] = model.getId() + "";
			str[3] = "EmDH";
			str[4] = operCon;
		} else {
			str[0] = "0";
			str[1] = "提交失败";
		}
		return str;
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
		Boolean flag = dal.updateTaprid(dataId, tapr_id);
		return flag;
	}

	@Override
	public Boolean ErrOperate(int dataId) {
		// TODO Auto-generated method stub
		return null;
	}

}
