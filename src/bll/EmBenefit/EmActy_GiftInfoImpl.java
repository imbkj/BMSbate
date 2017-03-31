package bll.EmBenefit;

import Model.EmActyGiftBackInfoModel;
import Model.EmActySuppilerGiftInfoModel;
import dal.EmBenefit.EmActy_GiftInfoOperateDal;
import service.WorkflowCore.WfBusinessService;

public class EmActy_GiftInfoImpl  implements WfBusinessService{
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
				int k=dal.EmActy_GiftAdd(model);
				if(k>0)
				{
					str[0]="1";
					str[1]="提交成功";
					str[2]=k+"";
					str[3]="EmActySuppilerGiftInfo";
					str[4]="福利申请";
				}
				else
				{
					str[0]="0";
					str[1]="提交失败";
				}
			}
			//更新
			else if(type=="2"||type.equals("2"))
			{
				EmActySuppilerGiftInfoModel model=(EmActySuppilerGiftInfoModel) obj[1];
				String sqlstr=(String) obj[2];
				boolean flag=dal.updateGiftInfo(sqlstr, model.getGift_id());
				if(flag)
				{

					str[0]="1";
					str[1]="提交成功";
					str[2]=model.getGift_id()+"";
					str[3]="EmActySuppilerGiftInfo";
					str[4]=model.getGift_remark();
				}
				else
				{
					str[0]="0";
					str[1]="提交失败";
				}
			}
			//重新提交
			if(type=="3"||type.equals("3"))
			{
				EmActySuppilerGiftInfoModel model=(EmActySuppilerGiftInfoModel) obj[1];
				int k=dal.EmActy_GiftInfoUpAgain(model);
				if(k>0)
				{
					str[0]="1";
					str[1]="提交成功";
					str[2]=model.getGift_id()+"";
					str[3]="EmActySuppilerGiftInfo";
					str[4]="福利重新申请";
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
		String[] str = new String[5];
		EmActyGiftBackInfoModel model= (EmActyGiftBackInfoModel) obj[1];
		int k=dal.EmActy_GiftBack(model);
		if(k>0)
		{
			str[0]="1";
			str[1]="退回成功";
			str[2]=model.getGtbk_giftid()+"";
			str[3]="EmActySuppilerGiftInfo";
			str[4]="退回福利申请";
		}
		else
		{
			str[0]="0";
			str[1]="提交失败";
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
		String[] str = new String[5];
		EmActyGiftBackInfoModel model= (EmActyGiftBackInfoModel) obj[1];
		int k=dal.EmActy_GiftBack(model);
		if(k>0)
		{
			str[0]="1";
			str[1]="退回成功";
			str[2]=model.getGtbk_giftid()+"";
			str[3]="EmActySuppilerGiftInfo";
			str[4]="退回福利申请";
		}
		else
		{
			str[0]="0";
			str[1]="提交失败";
		}
		return str;
	}

	@Override
	public Boolean UpdateTaprid(int dataId, int tapr_id, int state) {
		// TODO Auto-generated method stub
		return dal.updateGiftInfoTaprid(tapr_id, dataId);
	}

	@Override
	public Boolean ErrOperate(int dataId) {
		// TODO Auto-generated method stub
		return null;
	}

}
