package bll.EmHouseCard;

import dal.EmHouseCard.EmHouse_MakeCardInfoOperateDal;
import Model.EmHouseMakeCardInfoModel;
import Model.EmHouseTakeCardInfoModel;
import Model.emhouseMakeCardBackInfoModel;
import service.WorkflowCore.WfBusinessService;

public class EmHouse_MakeCardAddImpl implements WfBusinessService{
	private EmHouse_MakeCardInfoOperateDal dal=new EmHouse_MakeCardInfoOperateDal();
	@Override
	public String[] Operate(Object[] obj) {
		// TODO Auto-generated method stub
		String[] str = new String[5];
		try{		
			String type=(String) obj[0];
			EmHouseMakeCardInfoModel model=(EmHouseMakeCardInfoModel) obj[1];
			if(type=="1"||type.equals("1"))
			{
				//材料鉴别归档新增
				int k=dal.HouseMakecardinfoAdd(model);
				if(k>0)
				{
					str[0]="1";
					str[1]="提交成功";
					str[2]=k+"";
					str[3]="EmHouseMakeCardInfo";
					str[4]="公积金制卡新增";
				}
				else
				{
					str[0]="0";
					str[1]="提交失败";
				}
			}
			else if(type=="2"||type.equals("2"))
			{
				String sql=(String) obj[2];
				int k=dal.updateMakeCardInfo(model.getId(),sql);
				if(k>0)
				{
					str[0]="1";
					str[1]="提交成功";
					str[2]=model.getId()+"";
					str[3]="EmHouseMakeCardInfo";
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
		emhouseMakeCardBackInfoModel model=(emhouseMakeCardBackInfoModel) obj[0];
		int k=dal.backMakeCardInfo(model);
		if(k>0)
		{
			str[0]="1";
			str[1]="提交成功";
			str[2]=model.getBack_cardid()+"";
			str[3]="EmHouseMakeCardInfo";
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
		return dal.updateTakeCardTaprid(tapr_id, dataId);
	}

	@Override
	public Boolean ErrOperate(int dataId) {
		// TODO Auto-generated method stub
		return null;
	}

}
