/**
 * @Classname WfTaskOperateService
 * @ClassInfo 任务单操作接口
 * @author 李文洁
 * @Date 2013-10-16
 */

package service.Workflow;

public interface WfTaskOperateService {

	/*
	 * @Methodname:新建任务单
	 * @input:
	 * tacl_name：任务类别名称；tali_name：任务名称；wfbu_id：业务ID；username:操作人;remark:备注信息
	 * @out: -1：未找到相应的流程； 0:失败； 大于0的为任务流程编号（此值存入业务表）
	 */
	public int AddTask(String tacl_name, String tali_name, int wfbu_id,
			String username, String remark);
	
	/*
	 * @Methodname:新建子任务单
	 * @input:
	 * tali_name：任务名称；tapr_id：父任务当前任务流程ID；wfbu_id：业务ID；tapr_dataid：业务数据表ID；username:操作人;remark:备注信息
	 * @out: -1：未找到相应的流程；0:失败； 大于0的为任务流程编号（此值存入业务表）
	 */
	public int AddSubTask(String tali_name,int tapr_id, int wfbu_id,int tapr_dataid,
			String username, String remark);
}
