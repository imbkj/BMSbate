package bll.Archives;

import Model.EmArchiveDatumModel;
import dal.Archives.EmArchiveDatum_OperateDal;
import service.WorkflowCore.WfBusinessService;

public class Archive_FileCheckOutImpl implements WfBusinessService  {
	EmArchiveDatum_OperateDal dal=new EmArchiveDatum_OperateDal();
	
	@Override
	public String[] Operate(Object[] obj) {
		String[] str = new String[5];
		try{		
			String type=(String) obj[0];
			EmArchiveDatumModel model=(EmArchiveDatumModel) obj[1];
			
			if(type=="1"||type.equals("1"))
			{
				String remark=(String) obj[2];
				//新增档案调出
				int k=dal.addCheckOut(model,remark);
				if(k>0)
				{
					str[0]="1";
					str[1]="提交成功";
					str[2]=k+"";
					str[3]="EmArchiveDatum";
					str[4]=model.getEada_type();
				}
				else
				{
					str[0]="0";
					str[1]="提交失败";
				}
			}
			else if(type=="2"||type.equals("2"))
			{
				String remark=(String) obj[2];
				//档案调出修改
				String sql=(String) obj[3];
				int k=dal.EmArchiveUpdateData(model,remark,sql);
				if(k>0)
				{
					str[0]="1";
					str[1]="提交成功";
					str[2]=model.getEada_id()+"";
					str[3]="EmArchiveDatum";
					str[4]=model.getEada_type();
				}
				else
				{
					str[0]="0";
					str[1]="提交失败";
				}
			}
			//后道最后调出档案
			else if(type=="3"||type.equals("3"))
			{
				//档案调出修改
				int k=dal.EmArchiveOut(model);
				if(k>0)
				{
					str[0]="1";
					str[1]="提交成功";
					str[2]=model.getEada_id()+"";
					str[3]="EmArchiveDatum";
					str[4]="档案调出";
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
		String[] str = new String[5];
		EmArchiveDatumModel model=(EmArchiveDatumModel) obj[1];
		String sql=(String) obj[3];
		int k=dal.EmArchiveUpdateData(model,"",sql);
		if(k>0)
		{
			str[0]="1";
			str[1]="提交成功";
			str[2]=model.getEada_id()+"";
			str[3]=model.getEada_id()+"";
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
	public String[] SkipOperate(Object[] obj) {
		// TODO Auto-generated method stub
		String[] str = new String[5];
		EmArchiveDatumModel model=(EmArchiveDatumModel) obj[1];
		String sql=(String) obj[3];
		int k=dal.EmArchiveUpdateData(model,"",sql);
		if(k>0)
		{
			str[0]="1";
			str[1]="提交成功";
			str[2]=model.getEada_id()+"";
			str[3]=model.getEada_id()+"";
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
		EmArchiveDatumModel model=(EmArchiveDatumModel) obj[1];
		boolean flag=dal.delEmArchiveDatum(model.getEada_id());
		String[] str = new String[5];
		if(flag)
		{
			str[0]="1";
			str[1]="提交成功";
			str[2]=model.getEada_id()+"";
			str[3]=model.getEada_id()+"";
			str[4]="删除数据";
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
		return dal.updateTaprid(dataId, tapr_id);
	}

	@Override
	public Boolean ErrOperate(int dataId) {
		// TODO Auto-generated method stub
		return null;
	}

}
