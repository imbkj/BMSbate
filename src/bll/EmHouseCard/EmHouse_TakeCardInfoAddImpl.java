package bll.EmHouseCard;

import Model.EmHouseTakeCardInfoModel;
import dal.EmHouseCard.EmHouse_TakeCardInfoOperateDal;
import service.WorkflowCore.WfBusinessService;

public class EmHouse_TakeCardInfoAddImpl implements WfBusinessService{
	private EmHouse_TakeCardInfoOperateDal dal=new EmHouse_TakeCardInfoOperateDal();
	@Override
	public String[] Operate(Object[] obj) {
		String[] str = new String[5];
		try{		
			String type=(String) obj[0];
			EmHouseTakeCardInfoModel model=(EmHouseTakeCardInfoModel) obj[1];
			if(type=="1"||type.equals("1"))
			{
				//新增
				int k=dal.HouseTakecardinfoAdd(model);
				if(k>0)
				{
					str[0]="1";
					str[1]="提交成功";
					str[2]=k+"";
					str[3]="EmHouseTakeCardInfo";
					str[4]="公积金领卡新增";
				}
				else
				{
					str[0]="0";
					str[1]="提交失败";
				}
			}
			
			if(type=="2"||type.equals("2"))
			{
				
				String sql=(String) obj[2];
				int k=dal.updateTakeCardInfo(model.getRe_id(),sql);
				if(k>0)
				{
					str[0]="1";
					str[1]="提交成功";
					str[2]=model.getRe_id()+"";
					str[3]="EmHouseTakeCardInfo";
					str[4]=model.getState_Name();
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
		EmHouseTakeCardInfoModel model=(EmHouseTakeCardInfoModel) obj[0];
		int k=dal.HouseTakecardinfoBack(model);
		if(k>0)
		{
			str[0]="1";
			str[1]="提交成功";
			str[2]=model.getRe_id()+"";
			str[3]="EmHouseTakeCardInfo";
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
	public String[] SkipOperate(Object[] obj) {
		// TODO Auto-generated method stub
		String[] str = new String[5];
		EmHouseTakeCardInfoModel model=(EmHouseTakeCardInfoModel) obj[1];
		str[0]="1";
		str[1]="提交成功";
		str[2]=model.getRe_id()+"";
		str[3]="EmHouseTakeCardInfo";
		str[4]="跳过";
		
		return str;
	}

	@Override
	public String[] StopOperate(Object[] obj) {
		String[] str = new String[5];
		EmHouseTakeCardInfoModel model=(EmHouseTakeCardInfoModel) obj[1];
		str[0]="1";
		str[1]="提交成功";
		str[2]=model.getRe_id()+"";
		str[3]="EmHouseTakeCardInfo";
		str[4]="结束任务单";
		return str;
	}

	@Override
	public Boolean UpdateTaprid(int dataId, int tapr_id, int state) {
		// TODO Auto-generated method stub
		return dal.updateTakeCardTaprid(tapr_id, dataId);
	}

	@Override
	public Boolean ErrOperate(int dataId) {
		// TODO Auto-generated method stub
		return null;
	}

}
