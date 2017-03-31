package Controller.EmBenefit;

import dal.EmBenefit.EmActy_GiftInfoOperateDal;
import Model.EmActySuppilerGiftInfoModel;
import service.WorkflowCore.WfBusinessService;

public class EmActy_PayInfoImpl implements WfBusinessService{
	private EmActy_GiftInfoOperateDal dal=new EmActy_GiftInfoOperateDal();
	
	@Override
	public String[] Operate(Object[] obj) {
		// TODO Auto-generated method stub
		String[] str = new String[5];
		try{		
			String type=(String) obj[0];
			//新增
			if(type=="1"||type.equals("1"))
			{
				EmActySuppilerGiftInfoModel model=(EmActySuppilerGiftInfoModel) obj[1];
				int k=model.getGift_id();
				if(k>0)
				{
					str[0]="1";
					str[1]="提交成功";
					str[2]=k+"";
					str[3]="EmActySuppilerGiftInfo";
					str[4]="生成支付通知";
				}
				else
				{
					str[0]="0";
					str[1]="提交失败";
				}
			}
		}
		catch(Exception e)
		{
			System.out.print("错误："+e.getMessage());
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
		return dal.updateGiftInfopTaprid(tapr_id, dataId);
	}

	@Override
	public Boolean ErrOperate(int dataId) {
		// TODO Auto-generated method stub
		return null;
	}

	

}
