package bll.ClientRelations.VisitInfo;

import java.util.Date;
import java.util.List;
import service.WorkflowCore.WfOperateService;
import impl.WorkflowCore.WfOperateImpl;
import Model.VisitInfoModel;
import dal.ClientRelations.VisitInfo.*;
import service.WorkflowCore.WfBusinessService;
import bll.ClientRelations.VisitInfo.visitinfoimpl;

public class VisitInfoBll {
	//session 获取

	public VisitInfoBll(){
		
	}
	
	//按条件获取审核数据列表
	public List<VisitInfoModel> getlist(String viin_person,String viin_month,String cola_company,String viin_type,String viin_addname,int type)
	{
		String strwhere = "";
			
		if(!viin_person.isEmpty())
		{
			strwhere= strwhere + " and viin_person like '%"+viin_person+"%'";
		}
		if(!viin_month.isEmpty())
		{
			strwhere=strwhere + " and viin_month ='" +Integer.parseInt(viin_month)+"'";
		}
		
		if(!cola_company.isEmpty())
		{
			strwhere=strwhere +  "  and cola_company like '%"+cola_company+"%'";
		}
		if(!viin_type.isEmpty())
		{
			strwhere=strwhere +  "  and viin_type like '%"+viin_type+"%'";
		}
		if(!viin_addname.isEmpty())
		{
			strwhere=strwhere +  "  and viin_addname like '%"+viin_addname+"%'";
		}
		
		if (type==0)
		{
			strwhere=strwhere +  " and viin_state=0";
		}
		else
		{
			strwhere=strwhere +  " and viin_state<>9";
		}
		
	
		
		
		VisitInfoDal VisitInfoD =new VisitInfoDal();
		return VisitInfoD.getlist(strwhere);
		
	}
	
	//任务单表单接口 只接收ID
	
	public List<VisitInfoModel> getlist(String viin_id)
	{
		String strwhere = "";
			
		if(!viin_id.isEmpty())
		{
			strwhere= strwhere + " and viin_id ="+Integer.parseInt(viin_id)+"";
		}
		
		VisitInfoDal VisitInfoD =new VisitInfoDal();
		return VisitInfoD.getlist(strwhere);
		
	}
	
	//提交审核
	public int auditing(String viin_id,String viin_auditname,int viin_tapr_id) 
	{
		String[] message = new String[5];
	
		int i=0;
		WfBusinessService wfbs =new visitinfoimpl();
		WfOperateService wfs= new WfOperateImpl(wfbs);
	
		try{
			
			Object[] obj ={"2",viin_id,viin_auditname};
			message=wfs.PassToNext(obj, viin_tapr_id, viin_auditname, "", 0, "");
			System.out.println(message[1]);
			if (message[0]=="1")
			{
				i=1;
			}
		}
		catch(Exception e)
		{
			i=0;
		}
		return i;
		
	}
	
	//退回
	public int untread(String viin_id) 
	{
		
		int i=0;
		try{
		if(!viin_id.isEmpty())
		{
			VisitInfoDal VisitInfoD =new VisitInfoDal();
			i= VisitInfoD.untread(Integer.parseInt(viin_id));
		}
		}
		catch(Exception e)
		{
			i=0;
		}
		return i;
		
	}
	
	//删除选择
	public int deleteing(String viin_id) 
	{
	
		
		int i=0;
		try{
		if(!viin_id.isEmpty())
		{
			VisitInfoDal VisitInfoD =new VisitInfoDal();
			i= VisitInfoD.deleteing(Integer.parseInt(viin_id));
		}
		}
		catch(Exception e)
		{
			System.out.println(e.toString());
			i=0;
		}
		return i;
		
	}
	
	//新增拜访计划，传入参数，返回message数组(0失败1成功2出错)
	public String[] addVisitInfo(String viin_person,String viin_subordinate,String viin_type,String viin_class,String viin_month, String cola_id,String viin_addname,String viin_state
			,Date viin_starttime,Date viin_endtime,String viin_remark){
		String[] message = null;
		
		WfBusinessService wfbs =new visitinfoimpl();
		WfOperateService wfs= new WfOperateImpl(wfbs);
		
		try {
			//任务单生成
			
			Object[] obj ={"1",viin_person,viin_subordinate,viin_type,viin_class,"201312",cola_id,viin_addname,viin_state,viin_starttime,viin_endtime,viin_remark};

			if (viin_state=="1")
			{
				message=wfs.AddTaskToNext(obj, "拜访计划", "XX公司拜访计划", 1, viin_addname, "", 0, "");
				
				wfs.SkipToNext(obj, Integer.parseInt(message[2]), viin_addname, "经理级添加无需审核",0, "");
			}
			else
			{
				message=wfs.AddTaskToNext(obj, "拜访计划", "XX公司拜访计划", 1, viin_addname, "", 0, "");
			}
			
		}
		catch (Exception e) {
		message[0] = "2";
		message[1] = "新增拜访计划出错。";
	}
	System.out.print(message[1]);
	return message;
	}
	

	
	
	//修改拜访计划，传入参数，返回message数组(0失败1成功2出错)
	public String[] updateVisitInfo(int viin_id,String viin_person,String viin_subordinate,String viin_type,String viin_class,String viin_month){
		String[] message = new String[2];
		try {
			int result = 0;
			VisitInfoDal viinDal = new VisitInfoDal();
			
			result=viinDal.updateVisitInfo(viin_id,viin_person,viin_subordinate,viin_type,viin_class,viin_month);
			if(result == 0){
				message[0] = "1";
				message[1] ="修改拜访计划成功!";
			}
			else{
				message[0] = "0";
				message[1] = "修改拜访计划失败!";
			}
		}
		catch (Exception e) {
		message[0] = "2";
		message[1] = "修改拜访计划出错。";
	}
	return message;
	}



}
	
