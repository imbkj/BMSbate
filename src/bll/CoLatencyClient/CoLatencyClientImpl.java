package bll.CoLatencyClient;

import Model.CoBaseModel;
import dal.CoLatencyClient.CoLatencyClientDal;
import service.WorkflowCore.WfBusinessService;

public class CoLatencyClientImpl implements WfBusinessService{
	private CoLatencyClientDal dal=new CoLatencyClientDal();
	@Override
	public String[] Operate(Object[] obj) {
		// TODO Auto-generated method stub
		String str[] = new String[5];
		Integer i = Integer.valueOf(obj[0].toString());
		
		//i=1表示新增
		if (i==1) {
			CoBaseModel model=(CoBaseModel)obj[1];
			Integer colaid=Integer.valueOf(obj[2].toString());
			Integer k=dal.CoLatencyClientchange(model,colaid);
			if(k>0)
			{
				str[0]="1";
				str[1]="提交成功";
				str[2]=k+"";
				str[3]="CoBase";
				str[4]="转公司";
			}
			else
			{
				str[0]="0";
				str[1]="提交失败";
			}
		}
		else if(i==2)
		{
			String sql=(String)obj[1];
			Integer cid=Integer.parseInt(obj[2].toString());
			boolean flag=dal.updateCobaseInfo(sql, cid);
			if(flag)
			{
				str[0]="1";
				str[1]="提交成功";
				str[2]=cid+"";
				str[3]="CoBase";
				str[4]="转公司";
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
		return dal.updateCobaseTaprid(tapr_id, dataId);
	}

	@Override
	public Boolean ErrOperate(int dataId) {
		// TODO Auto-generated method stub
		return null;
	}

}
