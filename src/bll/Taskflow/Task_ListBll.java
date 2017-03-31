package bll.Taskflow;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import service.WorkflowCore.WfSearchTaskService;
import impl.WorkflowCore.WfSearchTaskImpl;

import Model.TaskListModel;
import dal.Taskflow.Task_ListDal;
import Model.WfTaskListInfoModel;

public class Task_ListBll {
	
	public List<WfTaskListInfoModel> getTaskBycoprid(String  username)
			throws SQLException {
		List<WfTaskListInfoModel> list = new ArrayList<WfTaskListInfoModel>();
		WfSearchTaskService tasklistws= new WfSearchTaskImpl();
		list= tasklistws.SearchCanOpTaskByUser(username);
		return list;
	}
	
	public List<WfTaskListInfoModel> getTaskBycoprid(String  username,Integer taclId)
			throws SQLException {
		List<WfTaskListInfoModel> list = new ArrayList<WfTaskListInfoModel>();
		WfSearchTaskService tasklistws= new WfSearchTaskImpl();
		list= tasklistws.SearchCanOpTaskByUser(username,taclId);
		return list;
	}
	
	
	
	//查询
	public List<WfTaskListInfoModel> getTaskInfoList(List<WfTaskListInfoModel> inlist,Integer tacl_id)
	{
		List<WfTaskListInfoModel> list = new ArrayList<WfTaskListInfoModel>();
		Map<Integer,WfTaskListInfoModel> map=new HashMap<Integer,WfTaskListInfoModel>();
		for(WfTaskListInfoModel m:inlist)
		{
			if(tacl_id.equals(m.getTacl_id()))
			{
				list.add(m);
			}
		}
		return list;
	}

	public List<WfTaskListInfoModel> getTaskList(List<WfTaskListInfoModel> inlist)
	{
		List<WfTaskListInfoModel> list = new ArrayList<WfTaskListInfoModel>();
		Map<Integer,WfTaskListInfoModel> map=new HashMap<Integer,WfTaskListInfoModel>();
		for(WfTaskListInfoModel m:inlist)
		{
			if(!map.containsKey(m.getTacl_id()))
			{
				map.put(m.getTacl_id(), m);
				list.add(m);
			}
		}
		return list;
	}
	
	//类型查询
	public List<WfTaskListInfoModel> getTaskClassList(List<WfTaskListInfoModel> inlist,String str)
	{
		List<WfTaskListInfoModel> list = new ArrayList<WfTaskListInfoModel>();
		Map<Integer,WfTaskListInfoModel> map=new HashMap<Integer,WfTaskListInfoModel>();
		for(WfTaskListInfoModel m:inlist)
		{
			if(m.getTacl_name().contains(str.trim()))
			{
				list.add(m);
			}
			else if(m.getTacl_name().indexOf(str.trim())!=-1)
			{
				list.add(m);
			}
		}
		return list;
	}
	
	//任务查询
	public List<WfTaskListInfoModel> getTaskContentList(List<WfTaskListInfoModel> inlist,String str)
	{
		List<WfTaskListInfoModel> list = new ArrayList<WfTaskListInfoModel>();
		Map<Integer,WfTaskListInfoModel> map=new HashMap<Integer,WfTaskListInfoModel>();
		for(WfTaskListInfoModel m:inlist)
		{
			if(m.getTali_name().contains(str))
			{
				list.add(m);
			}
		}
		return list;
	}
	
}
