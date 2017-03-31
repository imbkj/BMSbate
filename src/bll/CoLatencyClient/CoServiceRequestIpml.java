package bll.CoLatencyClient;

import dal.CoLatencyClient.CoServiceRequestOperateDal;
import Model.CoServiceRequestModel;
import service.WorkflowCore.WfBusinessService;

public class CoServiceRequestIpml  implements WfBusinessService{
	private CoServiceRequestOperateDal dal=new CoServiceRequestOperateDal();
	@Override
	public String[] Operate(Object[] obj) {
		// TODO Auto-generated method stub
		String str[] = new String[5];
		Integer i = Integer.valueOf(obj[0].toString());
		CoServiceRequestModel model=(CoServiceRequestModel)obj[1];
		//i=1表示新增
		if (i==1) {
			Integer k=dal.CoServiceRequest_Add(model);
			if(k>0)
			{
				str[0]="1";
				str[1]="提交成功";
				str[2]=k+"";
				str[3]="CoServiceRequest";
				str[4]="服务要求信息新增";
			}
			else
			{
				str[0]="0";
				str[1]="提交失败";
			}
		}
		//i=0表示通过存储过程更新服务要求
		if (i==0) {
			Integer k=dal.CoServiceRequest_update(model);
			if(k>0)
			{
				str[0]="1";
				str[1]="提交成功";
				str[2]=model.getCsqe_id()+"";
				str[3]="CoServiceRequest";
				str[4]="服务要求信息新增";
			}
			else
			{
				str[0]="0";
				str[1]="提交失败";
			}
		}
		//i==2表示使用sql修改服务要求信息
		else if(i==2)
		{
			String sql=(String)obj[2];
			Integer k=dal.updateCoServiceRequestInfo(sql,model.getCsqe_id());
			if(k>0)
			{
				str[0]="1";
				str[1]="提交成功";
				str[2]=model.getCsqe_id()+"";
				str[3]="CoServiceRequest";
				str[4]="更新服务要求信息";
			}
			else
			{
				str[0]="0";
				str[1]="提交失败";
			}
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
		return dal.updateCoServiceRequestTaprid(tapr_id, dataId);
	}

	@Override
	public Boolean ErrOperate(int dataId) {
		// TODO Auto-generated method stub
		return null;
	}

}
