package bll.EmBenefit;

import dal.EmBenefit.EmActy_GiftInfoOperateDal;
import service.WorkflowCore.WfBusinessService;

public class EmWelfareImpl implements WfBusinessService {

	@Override
	public String[] Operate(Object[] obj) {
		// TODO Auto-generated method stub
		String[] str=new String[5];
		String id=(String)obj[0];
		String sql=(String)obj[1];
		EmActy_GiftInfoOperateDal dal=new EmActy_GiftInfoOperateDal();
		boolean k=dal.updateGiftInfo(sql, Integer.parseInt(id));
		if(k)
		{
			str[0]="1";
			str[1]="提交成功";
			str[2]=id+"";
			str[3]="EmActySuppilerGiftInfo";
			str[4]="退回";
		}
		else
		{
			str[0]="0";
			str[1]="提交失败";
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
		String[] str=new String[5];
		String id=(String)obj[0];
		String sortid=(String)obj[1];
		String backcase=(String)obj[2];
		EmActy_GiftInfoOperateDal dal=new EmActy_GiftInfoOperateDal();
		Integer k=dal.EmWelfare_back(sortid, backcase);
		if(k>0)
		{
			str[0]="1";
			str[1]="提交成功";
			str[2]=id+"";
			str[3]="EmActySuppilerGiftInfo";
			str[4]="退回";
		}
		return str;
	}

	@Override
	public Boolean UpdateTaprid(int dataId, int tapr_id, int state) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Boolean ErrOperate(int dataId) {
		// TODO Auto-generated method stub
		return null;
	}

}
