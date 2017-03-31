package bll.EmCensus;

import Model.EmHJBorrowCardModel;
import dal.EmCensus.EmCensus_OperateDal;
import service.WorkflowCore.WfBusinessService;

public class EmCensus_BorrowCard_AddIpml implements WfBusinessService{
	EmCensus_OperateDal dal=new EmCensus_OperateDal();
	@Override
	public String[] Operate(Object[] obj) {
		// TODO Auto-generated method stub
		String[] str = new String[5];
		try{		
			String type=(String) obj[0];
			EmHJBorrowCardModel model=(EmHJBorrowCardModel) obj[1];
			if(type=="1"||type.equals("1"))
			{
				//借卡申请新增
				int k=dal.EmHJBorrowCardAdd(model);
				if(k>0)
				{
					str[0]="1";
					str[1]="提交成功";
					str[2]=k+"";
					str[3]="EmHJBorrowCard";
					str[4]="借卡申请";
				}
				else
				{
					str[0]="0";
					str[1]="提交失败";
				}
			}
			else if(type=="2"||type.equals("2"))
			{
				//借卡修改
				String sql=(String) obj[2];
				int k=dal.UpdateEmHJBorrowCardInfo(model,sql);
				if(k>0)
				{
					str[0]="1";
					str[1]="提交成功";
					str[2]=model.getEhbc_id()+"";
					str[3]="EmHJBorrowCard";
					//str[4]=model.getEada_type();
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
				System.out.println("错误："+e.getMessage());
				str[0]="2";
				str[1]="业务操作出错";
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
		EmHJBorrowCardModel model=(EmHJBorrowCardModel) obj[0];
		String[] str=new String[5];
		str[0]="1";
		str[1]="提交成功";
		str[2]=model.getEhbc_id()+"";
		str[3]="EmHJBorrowCard";
		str[4]="结束流程";
		return str;
	}

	@Override
	public Boolean UpdateTaprid(int dataId, int tapr_id, int state) {
		// TODO Auto-generated method stub
		return dal.updateborrowTaprid(dataId, tapr_id);
	}

	@Override
	public Boolean ErrOperate(int dataId) {
		// TODO Auto-generated method stub
		return null;
	}
	
}
