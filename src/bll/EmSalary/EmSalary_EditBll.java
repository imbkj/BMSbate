package bll.EmSalary;

import java.sql.SQLException;

import impl.WorkflowCore.WfOperateImpl;
import service.WorkflowCore.WfBusinessService;
import service.WorkflowCore.WfOperateService;
import Conn.dbconn;
import Util.UserInfo;
import dal.EmSalary.EmSalary_EditDal;
import service.PubEmailService;
import impl.PubEamilImpl;
import Model.PubEmailModel;
import Util.UserInfo;

public class EmSalary_EditBll {
	private EmSalary_EditDal emsdal =new EmSalary_EditDal();
	
	/**
	 * @param args
	 */
//	public static void main(String[] args) {
//		// TODO Auto-generated method stub
//
//	}
//	
	
	public EmSalary_EditBll()
	{
		
	}
	
	//审核工资
	public int audit(int esda_id) throws SQLException {
		
						
			return emsdal.audit(esda_id);

	}
	
	//发放工资
	public int pay(int esda_id,String txt_date) throws SQLException {
		
	
					
			return emsdal.pay(esda_id,txt_date);

	}
	
	//重发工资
	public int repay(int esda_id) throws SQLException {
		
			return emsdal.repay(esda_id);

	}
	
	
	//发送电子工资单
	public int sedemail(int esda_id) throws SQLException {
		int row=0;
		
		String[] emailp;
		String emailcoent="";
		PubEmailService  pemail =new PubEamilImpl();
		emailp=emsdal.getemailinfo(esda_id);
		emailcoent=emsdal.getemailcontenct(Integer.parseInt(emailp[0]), esda_id, Integer.parseInt(emailp[7]),Integer.parseInt(emailp[6]));
		if (emailp[2]!=null)
		{
		for(int i=0;i<emailp[2].split(";").length;i++)
		{
			row=pemail.EmailAdd(Integer.parseInt(emailp[1]), 
					emailp[4], emailcoent, UserInfo.getUsername(),Integer.parseInt(emailp[6]),esda_id,emailp[2].split(";")[i].toString());
		}
		}
	
		
		return row;
	}
	
	
	//任务单前进
	public String[] passtonext(String id,int t_id,String username,int d_id) {
		Object[] obj = {id,"",username,d_id};
		System.out.println(t_id);
		int i=0;
		String msg[];
		WfBusinessService wfbs = new EmSalary_Editimpl();
		WfOperateService wfs = new WfOperateImpl(wfbs);
		 msg=wfs.PassToNext(obj, t_id, username, "", 0, "");
		 return  msg;
	}

}
