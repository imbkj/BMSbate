/**
 * @Classname WfCoreSearchTaskService
 * @ClassInfo 查询任务单接口
 * @author 李文洁
 * @Date 2013-10-24
 */
package service.WorkflowCore;

import java.util.List;

import Model.WfTaskListInfoModel;

public interface WfSearchTaskService {
	/**
	 * @Methodname:根据用户名查找可操作的任务单
	 * 
	 * @input: username:用户名;
	 * 
	 * @output: List<WfTaskListInfoModel>
	 **/
	public List<WfTaskListInfoModel> SearchCanOpTaskByUser(String username);
	
	/** 
	* @Title: SearchCanOpTaskByUser 
	* @Description: TODO(根据用户名和任务类型编号查找可操作的任务单) 
	* @param username
	* @param taclId
	* @return
	* @return List<WfTaskListInfoModel>    返回类型 
	* @throws 
	*/
	public List<WfTaskListInfoModel> SearchCanOpTaskByUser(String username,Integer taclId);

	/**
	 * @Methodname:检索任务单
	 * 
	 * @input: list:任务单列表;key:检索字段;value:检索内容；
	 * 
	 * @output: List<WfTaskListInfoModel>
	 **/
	public List<WfTaskListInfoModel> SearchOpTaskByCon(
			List<WfTaskListInfoModel> list, String key, String value);
}
