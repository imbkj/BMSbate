package bll.EmCensus.EmDh;

import Model.EmDhModel;
import dal.EmCensus.EmDh.EmDh_OperateDal;
import service.WorkflowCore.WfBusinessService;

public class EmDh_hjUpdateImpl implements WfBusinessService{
	EmDh_OperateDal dal=new EmDh_OperateDal();
	@Override
	public String[] Operate(Object[] obj) {
		// TODO Auto-generated method stub
		String type=(String) obj[0];
		EmDhModel model=(EmDhModel) obj[1];
		String[] str = new String[5];
		str[0]="1";
		str[1]="提交成功";
		str[2]=model.getId()+"";
		str[3]="EmDH";
		str[4]="户籍变更申请";
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
		return dal.updateemdh_hjtaprid(dataId, tapr_id);
	}

	@Override
	public Boolean ErrOperate(int dataId) {
		// TODO Auto-generated method stub
		return null;
	}

}
