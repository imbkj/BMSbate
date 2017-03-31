package bll.ClientRelations.VisitInfo;

import dal.ClientRelations.VisitInfo.VisitInfoDal;
import service.WorkflowCore.WfBusinessService;

public class visitinfoimpl implements WfBusinessService {

	@Override
	public String[] Operate(Object[] obj) {
		// TODO Auto-generated method stub
		String[] message = new String[5];
		
		String mss1="";
		String mss4="";
		String errmss="";
	
		int i=0;
		if (obj[0].toString()=="1")
		{
			i=taskadd(obj);
			
		
			mss1="新增拜访计划成功!";
			mss4="新增拜访计划";
			errmss="新增拜访计划失败!";
			
		}
		
		else if (obj[0].toString()=="2")
		{
			i=taskaudit(obj);
			
			mss1="审核拜访计划成功!";
			mss4="审核拜访计划";
			errmss="审核拜访计划失败!";
		}
		
		if(i != 0){
			message[0] = "1";
			message[1] =mss1;
			message[2]=String.valueOf(i);
			message[3]="VisitInfo";
			message[4]=mss4;
		}
		else{
			message[0] = "0";
			message[1] = errmss;

		}
		return message;
	}
	
	private  int taskadd(Object[] obj)
	{
		int result = 0;
		VisitInfoDal viinDal = new VisitInfoDal();
		System.out.println("Start");
		result=viinDal.addVisitInfo(obj[1].toString(),obj[2].toString(),obj[3].toString(),obj[4].toString(),obj[5].toString(),obj[6].toString(),obj[7].toString(),obj[8].toString(),
				(java.util.Date)obj[9],(java.util.Date)obj[10],obj[11].toString());
		
		return result;
	}
	
	private  int taskaudit(Object[] obj)
	{
		int result = 0;
		//VisitInfoDal viinDal = new VisitInfoDal();
		VisitInfoDal VisitInfoD =new VisitInfoDal();
		
		//i= VisitInfoD.auditing(Integer.parseInt(viin_id),viin_auditname);
		try {
			result=VisitInfoD.auditing(Integer.parseInt(obj[1].toString()),obj[2].toString());
		} catch ( Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return result;
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
		VisitInfoDal viinDal = new VisitInfoDal();
		
		try {
			viinDal.updatetaskid(dataId,tapr_id);
			return true;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		}
		
	}

	@Override
	public Boolean ErrOperate(int dataId) {
		// TODO Auto-generated method stub
		return null;
	}

}
