package bll.EmSheBaocard;

import dal.EmSheBaocard.EmShebaoCardInfoOperateDal;
import Model.EmShebaoCardInfoModel;
import Util.UserInfo;
import service.WorkflowCore.WfBusinessService;

public class EmShebaoCardAddImpl implements WfBusinessService {
	private EmShebaoCardInfoOperateDal dal=new EmShebaoCardInfoOperateDal();
	@Override
	public String[] Operate(Object[] obj) {
		String[] str = new String[5];
		try{		
			String type=(String) obj[0];
			EmShebaoCardInfoModel model=(EmShebaoCardInfoModel) obj[1];
			if(type=="1"||type.equals("1"))
			{
				int k=dal.EmShebaoCardInfoAdd(model);
				if(k>0)
				{
					str[0]="1";
					str[1]="提交成功";
					str[2]=k+"";
					str[3]="EmShebaoCardInfo";
					str[4]="社保卡新增";
				}
				else
				{
					str[0]="0";
					str[1]="提交失败";
				}
			}
			else if(type=="2"||type.equals("2"))
			{
				String sqlstr=(String) obj[2];
				int k=dal.updateCardInfo(sqlstr, model.getSbcd_id());
				if(k>0)
				{
					str[0]="1";
					str[1]="提交成功";
					str[2]=model.getSbcd_id()+"";
					str[3]="EmShebaoCardInfo";
					str[4]=model.getCdst_statename();
				}
				else
				{
					str[0]="0";
					str[1]="提交失败";
				}
			}
			//服务中心核收资料
			else if(type=="3"||type.equals("3"))
			{
				int k=dal.EmShebaoCardInfoUpdate(model);
				if(k>0)
				{
					str[0]="1";
					str[1]="提交成功";
					str[2]=model.getSbcd_id()+"";
					str[3]="EmShebaoCardInfo";
					str[4]="服务中心核收资料";
				}
				else
				{
					str[0]="0";
					str[1]="提交失败";
				}
			}//服务中心核收资料
			else if(type=="cancel"||type.equals("cancel"))
			{
				String cancelcase=(String) obj[2];
				boolean flag=dal.CancelShebaoCardInfo(cancelcase,model.getSbcd_id());
				if(flag)
				{
					str[0]="1";
					str[1]="提交成功";
					str[2]=model.getSbcd_id()+"";
					str[3]="EmShebaoCardInfo";
					str[4]=UserInfo.getUsername()+"操作取消办理";
				}
				else
				{
					str[0]="0";
					str[1]="提交失败";
				}
			}
			else if(type=="edit"||type.equals("cancel"))
			{
				String cancelcase=(String) obj[2];
				Integer stateid=(Integer) obj[3];
				boolean flag=dal.CancelShebaoCardInfo(cancelcase,model.getSbcd_id(),stateid);
				if(flag)
				{
					str[0]="1";
					str[1]="提交成功";
					str[2]=model.getSbcd_id()+"";
					str[3]="EmShebaoCardInfo";
					str[4]=UserInfo.getUsername()+"修改领卡状态，取消办理";
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
		EmShebaoCardInfoModel model=(EmShebaoCardInfoModel) obj[0];
		String sqlstr=(String) obj[1];
		int k=dal.updateCardInfo(sqlstr, model.getSbcd_id());
		if(k>0)
		{
			str[0]="1";
			str[1]="提交成功";
			str[2]=model.getSbcd_id()+"";
			str[3]="EmShebaoCardInfo";
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
		return dal.updateShebaoCardTaprid(tapr_id, dataId);
	}

	@Override
	public Boolean ErrOperate(int dataId) {
		// TODO Auto-generated method stub
		return null;
	}

}
