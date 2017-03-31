package bll.CoAgencies;

import dal.CoAgencies.CoAg_CompactOperateDal;
import Model.CoAgencyCompactModel;
import service.WorkflowCore.WfBusinessService;

public class CoAgencyCompactImpl implements WfBusinessService{
	private CoAg_CompactOperateDal dal=new CoAg_CompactOperateDal();
	@Override
	public String[] Operate(Object[] obj) {
		String[] str = new String[5];
		try{		
			String type=(String) obj[0];
			CoAgencyCompactModel model=(CoAgencyCompactModel) obj[1];
			if(type=="1"||type.equals("1"))
			{
				int k=dal.CoAgencyCompactAdd(model);
				if(k>0)
				{
					str[0]="1";
					str[1]="提交成功";
					str[2]=k+"";
					str[3]="CoAgencyCompact";
					str[4]="委托机构合同新增";
				}
				else
				{
					str[0]="0";
					str[1]="提交失败";
				}
			}
			else if(type=="2"||type.equals("2"))
			{
				String operateinfo=(String) obj[2];
				int k=1;
				if(k>0)
				{
					str[0]="1";
					str[1]="提交成功";
					str[2]=model.getCoct_id()+"";
					str[3]="CoAgencyCompact";
					str[4]=operateinfo;
				}
				else
				{
					str[0]="0";
					str[1]="提交失败";
				}
			}
			//重新提交
			else if(type=="3"||type.equals("3"))
			{
				
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
		CoAgencyCompactModel model=(CoAgencyCompactModel) obj[1];
		Integer k=dal.BackComapct(model);
		if(k>0)
		{
			str[0]="1";
			str[1]="提交成功";
			str[2]=model.getCoct_id()+"";
			str[3]="CoAgencyCompact";
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
		return dal.updateCoAgencyCompactTaprid(tapr_id, dataId);
	}

	@Override
	public Boolean ErrOperate(int dataId) {
		// TODO Auto-generated method stub
		return null;
	}

}
