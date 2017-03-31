package bll.Archives;

import dal.Archives.EmArchiveDatum_OperateDal;
import Model.EmArchiveDatumModel;
import service.WorkflowCore.WfBusinessService;

public class EmArchiveFilePassImpl implements WfBusinessService{
	EmArchiveDatum_OperateDal dal=new EmArchiveDatum_OperateDal();
	
	@Override
	public String[] Operate(Object[] obj) {
		String[] str = new String[5];
		try{		
			String type=(String) obj[0];
			EmArchiveDatumModel model=(EmArchiveDatumModel) obj[1];
			String remark=(String) obj[2];
			if(type=="1"||type.equals("1"))
			{
				//转正定级新增
				int k=dal.EmArchiveFilePassAdd(model,remark);
				if(k>0)
				{
					str[0]="1";
					str[1]="提交成功";
					str[2]=k+"";
					str[3]="EmArchiveDatum";
					str[4]="受理申请";
				}
				else
				{
					str[0]="0";
					str[1]="提交失败";
				}
			}
			else if(type=="2"||type.equals("2"))
			{
				//转正定级修改
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
		// TODO Auto-generated method stub
		return null;
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
