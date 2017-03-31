package bll.EmBodyCheck;

import service.WorkflowCore.WfBusinessService;
import Model.EmBodyCheckModel;
import Model.EmBodycheckCancelModel;
import dal.EmBodyCheck.EmBcInfo_OperatetDal;

public class EmBcInfoImpl implements WfBusinessService{
	EmBcInfo_OperatetDal dal=new EmBcInfo_OperatetDal();
	@Override
	public String[] Operate(Object[] obj) {
		// TODO Auto-generated method stub
		String[] str = new String[5];
		try{		
			String type=(String) obj[0];
			
			if(type=="1"||type.equals("1"))
			{
				EmBodyCheckModel model=(EmBodyCheckModel) obj[1];
				int k=dal.EmBodyCheckAdd(model);
				if(k>0)
				{
					str[0]="1";
					str[1]="提交成功";
					str[2]=k+"";
					str[3]="EmBodyCheck";
					str[4]="体检新增";
				}
				else
				{
					str[0]="0";
					str[1]="提交失败";
				}
			}
			else if(type=="2"||type.equals("2"))
			{
				EmBodyCheckModel model=(EmBodyCheckModel) obj[1];
				//更新业务信息
				String sql=(String) obj[3];
				int k=dal.updateEmbodyChecklist(model.getEbcl_id(), sql);
				if(k>0)
				{
					str[0]="1";
					str[1]="提交成功";
					str[2]=model.getEmbc_id()+"";
					str[3]="EmBodyCheck";
					str[4]=model.getOcon();
				}
				else
				{
					str[0]="0";
					str[1]="提交失败";
				}
			}
			else if(type=="3"||type.equals("3"))
			{
				EmBodyCheckModel model=(EmBodyCheckModel) obj[1];
				//更新业务信息
				String sql=(String) obj[3];
				int k=dal.updateEmbodyChecklist(model.getEbcl_id(), sql);
				if(k>0)
				{
					str[0]="1";
					str[1]="提交成功";
					str[2]=model.getEmbc_id()+"";
					str[3]="EmBodyCheck";
					str[4]=model.getOcon();
				}
				else
				{
					str[0]="0";
					str[1]="提交失败";
				}
			}
			//前道取消预约
			else if(type=="4"||type.equals("4"))
			{
				EmBodycheckCancelModel model=(EmBodycheckCancelModel) obj[1];
				int k=dal.EmBodyCheckCancel(model);
				if(k>0)
				{
					str[0]="1";
					str[1]="提交成功";
					str[2]=model.getEmca_embc_id()+"";
					str[3]="EmBodyCheck";
					str[4]="前道取消预约";
				}
				else
				{
					str[0]="0";
					str[1]="提交失败";
				}
			}else if (type.equals("5")) {
				EmBodyCheckModel model=(EmBodyCheckModel) obj[1];
				str[0]="1";
				str[1]="提交成功";
				str[2]=model.getEmbc_id().toString();
				str[3]="EmBodyCheck";
				str[4]="前道重新预约";
			}else if(type.equals("6")){
				EmBodyCheckModel model=(EmBodyCheckModel) obj[1];
				String sql=(String) obj[2];
				String sql2=(String) obj[3];
				int k=dal.updateEmbodyChecklist(model.getEbcl_id(), sql);
				dal.updateEmbodyCheckInfo(model.getEmbc_id(), sql2);
				if(k>0)
				{
					str[0]="1";
					str[1]="提交成功";
					str[2]=model.getEmbc_id()+"";
					str[3]="EmBodyCheck";
					str[4]="";
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
		EmBodyCheckModel model=(EmBodyCheckModel) obj[0];
		String sql=(String) obj[1];
		int k=dal.updateEmbodyChecklist(model.getEbcl_id(), sql);
		if(k>0)
		{
			str[0]="1";
			str[1]="提交成功";
			str[2]=model.getEmbc_id()+"";
			str[3]="EmBodyCheck";
			str[4]=model.getOcon();
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
		EmBodyCheckModel model=(EmBodyCheckModel) obj[0];
		String sql=(String) obj[1];
		int k=dal.updateEmbodyChecklist(model.getEbcl_id(), sql);
		if(k>0)
		{
			str[0]="1";
			str[1]="提交成功";
			str[2]=model.getEmbc_id()+"";
			str[3]="EmBodyCheck";
			str[4]="";
		}
		else
		{
			str[0]="0";
			str[1]="提交失败";
		}
		return str;
	}

	@Override
	public String[] StopOperate(Object[] obj) {
		// TODO Auto-generated method stub
		String[] str = new String[5];
		EmBodyCheckModel model=(EmBodyCheckModel) obj[0];
		String sql=(String) obj[1];
		int k=dal.updateEmbodyChecklist(model.getEbcl_id(), sql);
		if(k>0)
		{
			str[0]="1";
			str[1]="提交成功";
			str[2]=model.getEmbc_id()+"";
			str[3]="EmBodyCheck";
			str[4]="";
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
		return dal.updateEmbodyCheckTaprid(dataId, tapr_id);
	}

	@Override
	public Boolean ErrOperate(int dataId) {
		// TODO Auto-generated method stub
		return null;
	}

}
