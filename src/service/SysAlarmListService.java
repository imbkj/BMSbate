package service;

public interface SysAlarmListService {
	//通过预警项目ID,角色ID,用户ID更新预警计数值
	//3个参数不使用填0
	public abstract Integer UpdateList(Integer itemId,Integer rolId,Integer logId);
	
	public abstract Integer UpdateList(Integer itemId,Integer logId);
	//新增预警项目
	//参数:需分配预警项目用户ID,添加人
	public abstract Integer AddList(Integer logId,String userName);
	
	
	
}
