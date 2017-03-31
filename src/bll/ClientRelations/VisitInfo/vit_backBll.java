package bll.ClientRelations.VisitInfo;

import impl.Workflow.WfFlowControlImpl;
import impl.WorkflowCore.WfOperateImpl;

import java.sql.CallableStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import service.Workflow.WfFlowControlService;
import service.Workflow.WfTaskOperateService;
import service.WorkflowCore.WfBusinessService;
import service.WorkflowCore.WfOperateService;

import org.zkoss.zul.ListModelList;

import dal.ClientRelations.VisitInfo.VisitInfoDal;
import dal.ClientRelations.VisitInfo.vit_backDal;

import Conn.dbconn;
import Model.VisitFollowModel;
import Model.VisitInfoModel;

import bll.ClientRelations.VisitInfo.vit_backimpl;

public class vit_backBll {
	
	
	//获取部门
	public static List<VisitFollowModel> getDept() throws SQLException{
		List<VisitFollowModel> list = new ArrayList<VisitFollowModel>();
		list=vit_backDal.getDept();
		return list;
	}
	
	//获取联系人名称
	public static ListModelList<String> getLinkmanList(int viin_id) throws SQLException {
		ListModelList<String> list = new ListModelList<String>();
		list=vit_backDal.getLinkmanList(viin_id);
		return list;
	}
	
	//state 2 type 1 hold 0  录入拜访计划
	//state 3 type 1 hold 0   录入拜访就完结
	//state 3 type 0 hold 1  修改拜访 
	//state 5 type 1 hold 0  完结 
	
	//拜访/回访结果情况录入  hold 0:执行任务单到下一步  type：在完结步骤 type=0 执行任务单跳转3次（无跟进的） 不等于0 执行2次
	public static int vitbackMod(VisitInfoModel vim,int hold,int type,String username) throws Exception {
		String str="";
		int i ;
		//i= vit_backDal.vitbackMod(vim);
		String[] message = new String[5];
		
		WfBusinessService wfbs =new vit_backimpl();
		WfOperateService wfs= new WfOperateImpl(wfbs);
		
		
		Object[] obj=new Object[3];
		obj[0]="0";
		obj[1]=vim;
		
			if (hold==0)
			{
				if (vim.getViin_state()==2)
					
				{
					
					str="录入拜访信息";
//					System.out.println(vim.getViin_id());
//					System.out.println(str);
					obj[2]=str;
					message=wfs.PassToNext(obj, vim.getViin_tapr_id(),username, str, 0, "");
				
					 
				}
				
				if (vim.getViin_state()==3)
				{
					if (type==0)
					{
//						System.out.println(vim.getViin_id());
//						System.out.println(str);
					obj[2]=str;
					message=wfs.PassToNext(obj, vim.getViin_tapr_id(),username, str, 0, "");
					}
					else
					{
						str="跟进拜访计划";
//						System.out.println(vim.getViin_id());
//						System.out.println(str);
						obj[2]=str;
						message=wfs.PassToNext(obj, vim.getViin_tapr_id(),username, str, 0, "");
					}
				}
				
				if (vim.getViin_state()==5)
				{

					str="拜访计划完结 ";
					if (type==1)
					{
//					//任务单跳转至下一步
//					int x=wfcs.PassToNext(vim.getViin_tapr_id(),vim.getViin_id(),vim.getViin_addname(), "", 0);
//					//更新业务表任务单id
//					
//					vit_backDal.updatetaskid(vim.getViin_id(), x);
//					
//					int y=wfcs.PassToNext(x,vim.getViin_id(),vim.getViin_addname(), "", 0);
//					//更新业务表任务单id
//					
//					vit_backDal.updatetaskid(vim.getViin_id(), y);
//					
//					int z=wfcs.PassToNext(y,vim.getViin_id(),vim.getViin_addname(), "", 0);
//					//更新业务表任务单id
//					
//					vit_backDal.updatetaskid(vim.getViin_id(), z);
//					

					//System.out.println(message[1]);
					
					
//						int x=wfcs.PassToNext(vim.getViin_tapr_id(),vim.getViin_id(),vim.getViin_addname(), "", 0);
//						//更新业务表任务单id
//						
//						vit_backDal.updatetaskid(vim.getViin_id(), x);
//						
//						int y=wfcs.PassToNext(x,vim.getViin_id(),vim.getViin_addname(), "", 0);
//						//更新业务表任务单id
//						
//						vit_backDal.updatetaskid(vim.getViin_id(), y);
						
						obj[2]=str;
						//message=wfs.SkipToN(obj, vim.getViin_tapr_id(), 5, username, str, 0, "");
//						System.out.println(vim.getViin_id());
//						System.out.println(str);
						message=wfs.OverTask(obj, vim.getViin_tapr_id(),username, str);
						//message=wfs.SkipToNext(obj, vim.getViin_tapr_id(),username, str, 0, "");
						
					}
					
					else
					{
						obj[2]=str;
						//message=wfs.SkipToN(obj, vim.getViin_tapr_id(), 5, username, str, 0, "");
//						System.out.println(vim.getViin_id());
//						System.out.println(str);
						message=wfs.OverTask(obj, vim.getViin_tapr_id(),username, str);
					}
				}
			
			}
			else
			{
//				if (vim.getViin_state()==3)
//				{
//					str="拜访计划完结 ";
//					obj[2]=str;
//					message=wfs.OverTask(obj, vim.getViin_tapr_id(),username, str);
//				}
				
				str="保存成功";
//				System.out.println(vim.getViin_id());
//				System.out.println(str);
				obj[2]=str;
				message=wfs.BusinessOpAddLog(obj, vim.getViin_tapr_id(), username, "保存");
			
			}
		
//		System.out.println(message[1]);
		return Integer.parseInt(message[0]);
		
	}
	
	//跟进事项录入
	public static int vitfollowAdd(VisitFollowModel vfm) throws SQLException {
		return vit_backDal.vitfollowAdd(vfm);
		
		
	}
}
