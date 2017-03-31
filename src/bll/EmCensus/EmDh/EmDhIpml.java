package bll.EmCensus.EmDh;

import dal.EmCensus.EmDh.EmDh_OperateDal;
import Model.EmArchiveDatumModel;
import Model.EmCensusModel;
import Model.EmDhModel;
import service.WorkflowCore.WfBusinessService;

public class EmDhIpml implements WfBusinessService{
	EmDh_OperateDal dal=new EmDh_OperateDal();
	@Override
	public String[] Operate(Object[] obj) {
		String[] str = new String[5];
			try{		
				String type=(String) obj[0];
				EmDhModel model=(EmDhModel) obj[1];
				if(type=="1"||type.equals("1"))
				{
					//调户申请
					int k=dal.EmDhAdd(model);
					if(k>0)
					{
						str[0]="1";
						str[1]="提交成功";
						str[2]=k+"";
						str[3]="EmDH";
						str[4]="新增调户申请";
					}
					else
					{
						str[0]="0";
						str[1]="提交失败";
					}
				}
				//未入职员工调户新增
				else if(type=="0"||type.equals("0"))
				{
					//未入职员工调户新增
					int k=dal.EmDhInfoAdd(model);
					if(k>0)
					{
						str[0]="1";
						str[1]="提交成功";
						str[2]=k+"";
						str[3]="EmDH";
						str[4]="新增调户申请";
					}
					else
					{
						str[0]="0";
						str[1]="提交失败";
					}
				}
				else if(type=="2"||type.equals("2"))
				{
					//调户修改
					String sql=(String) obj[2];
					int k=dal.UpdateEmdhInfo(model,sql);
					if(k>0)
					{
						str[0]="1";
						str[1]="提交成功";
						str[2]=model.getId()+"";
						str[3]="EmDH";
						str[4]=model.getStates();
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
		EmDhModel model=(EmDhModel) obj[1];
		String sql=(String) obj[2];
		int k=dal.UpdateEmdhInfo(model, sql);
		String[] str = new String[5];
		if(k>0)
		{
			str[0]="1";
			str[1]="提交成功";
			str[2]=model.getId()+"";
			str[3]="EmDH";
			str[4]="退回流程";
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
		// TODO Auto-generated method stub
		String[] str = new String[5];
		EmDhModel model=(EmDhModel) obj[1];
		String sql=(String) obj[3];
		//int k=dal.EmArchiveUpdateData(model,"",sql);
		int k=0;
		if(k>0)
		{
			str[0]="1";
			str[1]="提交成功";
			str[2]=model.getId()+"";
			str[3]=model.getId()+"";
			str[4]="更改状态";
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
		String flag=(String) obj[0];
		String[] str = new String[5];
		if(flag=="1")
		{
			EmDhModel model=(EmDhModel) obj[1];
			String sql=(String) obj[2];
			int k=dal.UpdateEmdhInfo(model,sql);
			if(k>0)
			{
				str[0]="1";
				str[1]="提交成功";
				str[2]=model.getId()+"";
				str[3]="EmDH";
				str[4]=model.getStates();
			}
			else
			{
				str[0]="0";
				str[1]="提交失败";
			}
		}
		else
		{
			EmDhModel model=(EmDhModel) obj[1];
			str[0]="1";
			str[1]="提交成功";
			str[2]=model.getId()+"";
			str[3]="EmDH";
			str[4]=model.getStates();
		}
		return str;
	}

	@Override
	public Boolean UpdateTaprid(int dataId, int tapr_id, int state) {
		// TODO Auto-generated method stub
		return dal.updateTaprid(dataId, tapr_id);
	}

	@Override
	public Boolean ErrOperate(int dataId) {
		// TODO Auto-generated method stub
		return null;
	}

}
