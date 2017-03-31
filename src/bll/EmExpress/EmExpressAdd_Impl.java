package bll.EmExpress;

import dal.EmExpress.EmExpressInfoOperateDal;
import Model.EmExpressInfoModel;
import service.WorkflowCore.WfBusinessService;

public class EmExpressAdd_Impl implements WfBusinessService{
	private EmExpressInfoOperateDal dal=new EmExpressInfoOperateDal();
	@Override
	public String[] Operate(Object[] obj) {
		String[] str = new String[5];
		try{		
			String type=(String) obj[0];
			EmExpressInfoModel model=(EmExpressInfoModel) obj[1];
			if(type=="1"||type.equals("1"))
			{
				//快递信息新增
				String classtype=(String) obj[2];
				int k=0;
				//个人快递
				if(classtype!=null&&classtype.equals("emba"))
				{
					k=dal.EmExpressInfoAdd(model);
				}
				//公司快递
				else
				{
					k=dal.EmExpressInfoCobaseAdd(model);
				}
				if(k>0)
				{
					str[0]="1";
					str[1]="提交成功";
					str[2]=k+"";
					str[3]="EmExpressInfo";
					str[4]="发送快递信息新增";
				}
				else
				{
					str[0]="0";
					str[1]="提交失败";
				}
			}
			else if(type=="2"||type.equals("2"))
			{
				//落入申请修改
				String sql=(String) obj[2];
				int k=0;
				k=dal.updateExpressInfo(sql, model.getExpr_id());
				if(k>0)
				{
					str[0]="1";
					str[1]="提交成功";
					str[2]=model.getExpr_id()+"";
					str[3]="EmExpressInfo";
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
