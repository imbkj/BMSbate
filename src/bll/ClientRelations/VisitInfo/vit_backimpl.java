package bll.ClientRelations.VisitInfo;
import java.sql.SQLException;

import Model.VisitInfoModel;
import impl.Workflow.WfFlowControlImpl;
import dal.ClientRelations.VisitInfo.VisitInfoDal;
import dal.ClientRelations.VisitInfo.vit_backDal;
import service.Workflow.WfFlowControlService;
import service.WorkflowCore.WfBusinessService;

public class vit_backimpl implements WfBusinessService {

	@Override
	public String[] Operate(Object[] obj) {
		// TODO Auto-generated method stub
		
		String[] message = new String[5];
		
		VisitInfoModel vim = (VisitInfoModel)obj[1];
		
		int i=0 ;
		try {
			i= vit_backDal.vitbackMod(vim);
			
			System.out.println(i);
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		if(i != 0){
			message[0] = "1";
			message[1] ="";
			message[2]=String.valueOf(i);
			message[3]="VisitInfo";
			message[4]=obj[2].toString();
		}
		else{
			message[0] = "0";
			message[1] = "";

		}
	
		System.out.println(message[0]);
		return message;
		
		
	}

	@Override
	public String[] ReturnOperate(Object[] obj) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String[] SkipOperate(Object[] obj) {
		// TODO Auto-generated method stub
		String[] str = {"1","",obj[0].toString()};
		return str;
	}

	@Override
	public String[] StopOperate(Object[] obj) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Boolean UpdateTaprid(int dataId, int tapr_id, int state) {
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
