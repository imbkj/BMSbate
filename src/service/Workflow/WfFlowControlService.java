/**
 * @Classname WfFlowControlService
 * @ClassInfo 流程控制接口
 * @author 李文洁
 * @Date 2013-10-16
 */

package service.Workflow;

public interface WfFlowControlService {


	/*
	 * @Methodname:记录操作内容
	 * 
	 * @input:
	 * tapr_id：流程ID；tapl_datatable：业务表名；tapl_datatableid：业务表ID；tapl_content：操作内容;
	 * username:操作人;remark: 备注信息；
	 * 
	 * @out: -1:该步骤已处理,无法继续操作 0:操作出错 1:成功；
	 */
	public int AddTaskLog(int tapr_id, String tapl_datatable, int tapl_datatableid,
			String tapl_content, String username, String remark);
	/*
	 * @Methodname:通过转下一步
	 * 
	 * @input:
	 * tapr_id：流程ID;tapr_dataid：业务表ID；username:操作人;remark:
	 * 备注信息；appoint：指定下一步操作人的log_id,不指定传0
	 * 
	 * @out: -2为最后一步已完成任务-1该步骤已处理,无法继续操作；0出错；大于0的为流程编号
	 */
	public int PassToNext(int tapr_id, int tapr_dataid, String username,
			String remark, int appoint);
	/*
	 * @Methodname:跳过转下一步
	 * 
	 * @input:
	 * tapr_id：流程ID;username:操作人;remark:
	 * 备注信息；appoint：指定下一步操作人的log_id,不指定传0
	 * 
	 * @out: -3该步骤不允许跳过-2成功跳过最后一步已完成任务-1该步骤已处理,无法继续操作；0出错；大于0的为流程编号
	 */
	public int SkipToNext(int tapr_id,String username,String remark,int appoint);
	
	/*
	 * @Methodname:撤销到上一步
	 * 
	 * @input:
	 * tapr_id：流程ID;username:操作人;remark:备注信息；
	 * 
	 * @out:大于0 ；小于等于0出错;
	 */
	public int revokeToPrev(int tapr_id,String username,String remark);
}
