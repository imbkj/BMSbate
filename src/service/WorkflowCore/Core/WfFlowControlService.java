/**
 * @Classname WfFlowControlService
 * @ClassInfo 流程控制接口
 * @author 李文洁
 * @Date 2013-10-16
 */

package service.WorkflowCore.Core;

import java.util.List;

public interface WfFlowControlService {

	/*
	 * @Methodname:检测操作权限
	 * 
	 * @input: tapr_id：流程ID；username:操作人;
	 * 
	 * @out: true or flase
	 */
	public boolean CheckCompetence(int tapr_id, String username);

	/*
	 * @Methodname:检查流程是否正在执行
	 * 
	 * @input: tapr_id：流程ID
	 * 
	 * @out: true or flase
	 */
	public boolean CheckProcess(int tapr_id);

	/*
	 * @Methodname:记录操作内容
	 * 
	 * @input:
	 * tapr_id：流程ID；tapl_datatable：业务表名；tapl_datatableid：业务表ID；tapl_content
	 * ：操作内容; username:操作人;remark: 备注信息；
	 * 
	 * @out: -1:该步骤已处理,无法继续操作 0:操作出错 1:成功；
	 */
	public int AddTaskLog(int tapr_id, String tapl_datatable,
			int tapl_datatableid, String tapl_content, String username,
			String remark);

	/*
	 * @Methodname:通过当前流程
	 * 
	 * @input: tapr_id：流程ID;username:操作人;remark:备注信息；
	 * 
	 * @out:-1该步骤已处理,无法继续操作；等于0出错;1成功;
	 */
	public int PassProcess(int tapr_id, String username, String remark);

	/*
	 * @Methodname:通过转下一步
	 * 
	 * @input: tapr_id：流程ID;tapr_dataid：业务表ID；username:操作人;remark:
	 * 备注信息；appointid
	 * ：指定执行人(-2自带SQL条件；-1指定某人（用户名）；默认为0不指定执行人；大于0为节点SQL的ID);appointcon:指定内容;
	 * 
	 * @out: -2为最后一步已完成任务-1该步骤已处理,无法继续操作；0出错；大于0的为流程编号
	 */
	public int PassToNext(int tapr_id, int tapr_dataid, String username,
			String remark, int appointid, String appointcon);

	/*
	 * @Methodname:跳过转下一步
	 * 
	 * @input: tapr_id：流程ID;username:操作人;remark:
	 * 备注信息；appoint：指定下一步操作人的log_id,不指定传0
	 * 
	 * @out: -3该步骤不允许跳过-2成功跳过最后一步已完成任务-1该步骤已处理,无法继续操作；0出错；大于0的为流程编号
	 */
	public int SkipToNext(int tapr_id, String username, String remark,
			int appointid, String appointcon);

	/*
	 * @Methodname:跳转至指定步骤
	 * 
	 * @input: tapr_id：流程ID;tapr_dataid：业务表ID；tostep:指定跳转到的步骤；appointid
	 * ：指定执行人(-2自带SQL条件；-1指定某人（用户名）；默认为0不指定执行人；大于0为节点SQL的ID);appointcon:指定内容;
	 * username:操作人;remark:备注信息；
	 * 
	 * @out: -2为最后一步已完成任务-1该步骤已处理,无法继续操作；0出错；大于0的为流程编号
	 */
	public int SkipToN(int tapr_id, int tapr_dataid, int tostep, int appointid,
			String appointcon, String username, String remark);

	/*
	 * @Methodname:撤销到上一步
	 * 
	 * @input: tapr_id：流程ID;username:操作人;remark:备注信息；
	 * 
	 * @out:大于0 ；小于等于0出错;
	 */
	public int revokeToPrev(int tapr_id, String username, String remark);

	/*
	 * @Methodname:退回到上一步
	 * 
	 * @input: tapr_id：流程ID;username:操作人;
	 * 
	 * @out:-2该步骤不允许退回；-1该步骤已处理,无法继续操作；等于0出错;大于0 返回流程ID；
	 */
	public int returnToPrev(int tapr_id, String username);

	/*
	 * @Methodname:退回到指定步骤
	 * 
	 * @input: tapr_id：流程ID;tostep:指定步骤(既wfno_step)；username:操作人;
	 * 
	 * @out:-2该步骤不允许退回；-1该步骤已处理,无法继续操作；等于0出错;大于0 返回流程ID；
	 */
	public int returnToN(int tapr_id, int tostep, String username);

	/*
	 * @Methodname:移动到指定步骤
	 * 
	 * @input: tapr_id：流程ID;tostep:指定步骤(既wfno_step)；username:操作人;
	 * 
	 * @out:-2为当前任务为最后一步不可跳转,-1该步骤已处理,无法继续操作；等于0出错;大于0 返回流程ID；
	 */
	public int moveToN(int tapr_id, int tostep, String username,String remark);
	/*
	 * @Methodname:终止任务
	 * 
	 * @input: tapr_id：流程ID;username:操作人;remark:备注信息；
	 * 
	 * @out:-2该步骤不允许终止；-1该步骤已处理,无法继续操作；等于0出错;1成功;
	 */
	public int StopTask(int tapr_id, String username, String remark);

	/*
	 * @Methodname:完结任务
	 * 
	 * @input: tapr_id：流程ID;username:操作人;remark:备注信息；
	 * 
	 * @out:-1该步骤已处理,无法继续操作；等于0出错;1成功;
	 */
	public int OverTask(int tapr_id, String username, String remark);

	/*
	 * @Methodname:记录出错日志
	 * 
	 * @input: 任务单ID，流程ID，错误内容，操作人
	 * 
	 * @out:
	 */
	public void addErrLog(int tali_id, int tapr_id, int dataid,
			String wfel_con, String username);

	/*
	 * @Methodname:更改流程状态
	 * 
	 * @input: tapr_id：流程ID，openState：初始状态，endState：修改后状态
	 * 
	 * @out: true:成功; false :失败
	 */
	public boolean upProcessState(int tapr_id, int openState, int endState);

	/*
	 * @Methodname:通过流程ID查询可操作人
	 * 
	 * @input: tapr_id：流程ID
	 * 
	 * @out: Userid
	 */
	public List<Integer> searchUser(int tapr_id);

	/*
	 * @Methodname:查询撤回至指定步骤的操作人
	 * 
	 * @input: tapr_id：当前流程ID,wfno_step:指定步骤
	 * 
	 * @out: 指定步骤的操作人
	 */
	public String selRevokeOpName(int tapr_id, int wfno_step);

}
