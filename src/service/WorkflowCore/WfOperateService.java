/**
 * @Classname WfOperateService
 * @ClassInfo 任务单操作接口
 * @author 李文洁
 * @Date 2013-10-24
 */
package service.WorkflowCore;

import java.util.Map;

public interface WfOperateService {
	/**
	 * @Methodname:新建任务单并转下一步
	 * 
	 * @remark:新建任务单常用方法；调用的业务方法：
	 * 
	 * @procedure:业务处理->新建任务单->更新业务表的流程ID->记录操作内容->任务单转至下一步->更新业务表的流程ID
	 * 
	 * @businessMethode:Operate，UpdateTaprid，ErrOperate
	 * 
	 * @input: obj:业务实现类参数；
	 *         tacl_name：任务类别名称；tali_name：任务名称；wfbu_id：业务ID；username
	 *         :操作人;remark:备注信息；appointid
	 *         ：指定下一步执行人(-2自带SQL条件；-1指定某人（用户名）；默认为0不指定执行人
	 *         ；大于0为节点SQL的ID);appointcon:指定内容;
	 * 
	 * @out: message[]; Str[0]:处理的状态,0失败1成功2出错;Str[1]提示信息；
	 */
	public String[] AddTaskToNext(Object[] obj, String tacl_name,
			String tali_name, int wfbu_id, String username, String remark,
			int appointid, String appointcon);

	/**
	 * @Methodname:新建任务单并转下一步
	 * 
	 * @remark:新建任务单常用方法；调用的业务方法：
	 * 
	 * @procedure:业务处理->新建任务单->更新业务表的流程ID->记录操作内容->任务单转至下一步->更新业务表的流程ID
	 * 
	 * @businessMethode:Operate，UpdateTaprid，ErrOperate
	 * 
	 * @input: obj:业务实现类参数；
	 *         tacl_name：任务类别名称；tali_name：任务名称；wfbu_id：业务ID；username
	 *         :操作人;remark:备注信息；appointid
	 *         ：指定下一步执行人(-2自带SQL条件；-1指定某人（用户名）；默认为0不指定执行人
	 *         ；大于0为节点SQL的ID);appointcon
	 *         :指定内容;searchMap:任务单搜索条件（Key:搜索条件;value:搜索内容）
	 * 
	 * @out: message[];
	 *       Str[0]:处理的状态,0失败1成功2出错;Str[1]提示信息；Str[2]返回第二部流程ID;Str[3]返回业务表主键ID
	 */
	public String[] AddTaskToNext(Object[] obj, String tacl_name,
			String tali_name, int wfbu_id, String username, String remark,
			int appointid, String appointcon, Map<String, String> searchMap);

	/**
	 * @Methodname:新建任务单
	 * 
	 * @remark:仅新建任务单，并保持在第一步（特殊用法）
	 * 
	 * @procedure:业务处理->新建任务单->更新业务表的流程ID->记录操作内容
	 * 
	 * @businessMethode:Operate，UpdateTaprid，ErrOperate
	 * 
	 * @input:obj:业务实现类参数； 
	 *                     tacl_name：任务类别名称；tali_name：任务名称；wfbu_id：业务ID；username:
	 *                     操作人;remark:备注信息
	 * 
	 * @out: message[]; Str[0]:处理的状态,0失败1成功2出错;Str[1]提示信息；
	 */
	public String[] AddTask(Object[] obj, String tacl_name, String tali_name,
			int wfbu_id, String username, String remark);

	/**
	 * @Methodname:新建任务单
	 * 
	 * @remark:仅新建任务单，并保持在第一步（特殊用法）
	 * 
	 * @procedure:业务处理->新建任务单->更新业务表的流程ID->记录操作内容
	 * 
	 * @businessMethode:Operate，UpdateTaprid，ErrOperate
	 * 
	 * @input:obj:业务实现类参数； 
	 *                     tacl_name：任务类别名称；tali_name：任务名称；wfbu_id：业务ID；username:
	 *                     操作人
	 *                     ;remark:备注信息；searchMap:任务单搜索条件（Key:搜索条件;value:搜索内容）
	 * 
	 * @out: message[];
	 *       Str[0]:处理的状态,0失败1成功2出错;Str[1]提示信息；Str[2]返回业务表主键ID;Str[3]返回流程ID;
	 */
	public String[] AddTask(Object[] obj, String tacl_name, String tali_name,
			int wfbu_id, String username, String remark,
			Map<String, String> searchMap);

	/**
	 * @Methodname:新建子任务单（父子任务串行）
	 * 
	 * @remark:新建子任务单,父任务单节点待子任务单完成后才跳转至下一步
	 * 
	 * @procedure:业务处理->记录父任务单操作内容->新建子任务单->更新子业务表的流程ID->记录子任务单操作内容->子任务跳至下一步
	 * 
	 * @businessMethode:Operate，UpdateTaprid，ErrOperate
	 * 
	 * @input:obj:业务实现类参数； tali_name：任务名称；tapr_id：流程ID;wfbu_id：子业务ID；username :
	 *                     操作人;remark:备注信息;sappointid:子任务第二步指定人ID;sappointcon:
	 *                     子任务第二步指定内容
	 * 
	 * @out: message[]; Str[0]:处理的状态,0失败1成功2出错;Str[1]提示信息；
	 */
	public String[] AddSubTask(Object[] obj, String tali_name, int tapr_id,
			int wfbu_id, String username, String remark, int appointid,
			String appointcon, int sappointid, String sappointcon);

	/**
	 * @Methodname:新建子任务单（父子任务串行）
	 * 
	 * @remark:新建子任务单,父任务单节点待子任务单完成后才跳转至下一步
	 * 
	 * @procedure:业务处理->记录父任务单操作内容->新建子任务单->更新子业务表的流程ID->记录子任务单操作内容->子任务跳至下一步
	 * 
	 * @businessMethode:Operate，UpdateTaprid，ErrOperate
	 * 
	 * @input:obj:业务实现类参数； tali_name：任务名称；tapr_id：流程ID;wfbu_id：子业务ID；username :
	 *                     操作人;remark:备注信息;sappointid:子任务第二步指定人ID;sappointcon:
	 *                     子任务第二步指定内容；searchMap:任务单搜索条件（Key:搜索条件;value:搜索内容）
	 * 
	 * @out: message[]; Str[0]:处理的状态,0失败1成功2出错;Str[1]提示信息；
	 */
	public String[] AddSubTask(Object[] obj, String tali_name, int tapr_id,
			int wfbu_id, String username, String remark, int appointid,
			String appointcon, int sappointid, String sappointcon,
			Map<String, String> searchMap);

	/**
	 * @Methodname:新建子任务单（父子任务并行）
	 * 
	 * @remark:新建子任务单,父任务单节点跳转至下一步
	 * 
	 * @procedure:业务处理->记录父任务单操作内容->新建子任务单->更新子业务表的流程ID->记录子任务单操作内容->子任务跳至下一步->父任务跳至下一步
	 * 
	 * @businessMethode:Operate，UpdateTaprid，ErrOperate
	 * 
	 * @input:obj:业务实现类参数； tali_name：任务名称；tapr_id：流程ID;wfbu_id：子业务ID；username :
	 *                     操作人;remark:备注信息;appointid:父任务下一步指定人ID;appointcon:
	 *                     父任务下一步指定内容
	 *                     ;sappointid:子任务第二步指定人ID;sappointcon:子任务第二步指定内容
	 * 
	 * @out: message[]; Str[0]:处理的状态,0失败1成功2出错;Str[1]提示信息；
	 */
	public String[] AddSubTaskPToNext(Object[] obj, String tali_name,
			int tapr_id, int pdataid, int wfbu_id, String username,
			String remark, int appointid, String appointcon, int sappointid,
			String sappointcon);

	/**
	 * @Methodname:新建子任务单（父子任务并行）
	 * 
	 * @remark:新建子任务单,父任务单节点跳转至下一步
	 * 
	 * @procedure:业务处理->记录父任务单操作内容->新建子任务单->更新子业务表的流程ID->记录子任务单操作内容->子任务跳至下一步->父任务跳至下一步
	 * 
	 * @businessMethode:Operate，UpdateTaprid，ErrOperate
	 * 
	 * @input:obj:业务实现类参数； tali_name：任务名称；tapr_id：流程ID;wfbu_id：子业务ID；username :
	 *                     操作人;remark:备注信息;appointid:父任务下一步指定人ID;appointcon:
	 *                     父任务下一步指定内容
	 *                     ;sappointid:子任务第二步指定人ID;sappointcon:子任务第二步指定内容
	 *                     ；searchMap:任务单搜索条件（Key:搜索条件;value:搜索内容）
	 * 
	 * @out: message[]; Str[0]:处理的状态,0失败1成功2出错;Str[1]提示信息；
	 */
	public String[] AddSubTaskPToNext(Object[] obj, String tali_name,
			int tapr_id, int pdataid, int wfbu_id, String username,
			String remark, int appointid, String appointcon, int sappointid,
			String sappointcon, Map<String, String> searchMap);

	/**
	 * @Methodname:通过转下一步
	 * 
	 * @remark:通过转下一步,并记录操作内容
	 * 
	 * @procedure:检测操作权限->业务处理->记录操作内容->任务单转至下一步->更新业务表的流程ID
	 * 
	 * @businessMethode:Operate，UpdateTaprid，ErrOperate
	 * 
	 * @input:obj:业务实现类参数； tapr_id：流程ID;username:操作人;remark: 备注信息；appointid
	 *                     ：指定下一步执行人
	 *                     (-2自带SQL条件；-1指定某人（用户名）；默认为0不指定执行人；大于0为节点SQL的ID
	 *                     );appointcon:指定内容;
	 * 
	 * @out: message[]; Str[0]:处理的状态,0失败1成功2出错;Str[1]提示信息；
	 */
	public String[] PassToNext(Object[] obj, int tapr_id, String username,
			String remark, int appointid, String appointcon);

	/**
	 * @Methodname:跳过转下一步
	 * 
	 * @remark:通过转下一步,并记录操作内容
	 * 
	 * @procedure:检测操作权限->任务单转至下一步->业务处理->更新业务表的流程ID
	 * 
	 * @businessMethode:SkipOperate，UpdateTaprid
	 * 
	 * @input:obj:业务实现类参数 tapr_id：流程ID;username:操作人;remark: 备注信息；appointid
	 *                    ：指定下一步执行人
	 *                    (-2自带SQL条件；-1指定某人（用户名）；默认为0不指定执行人；大于0为节点SQL的ID
	 *                    );appointcon:指定内容;
	 * 
	 * @out: message[]; Str[0]:处理的状态,0失败1成功2出错;Str[1]提示信息；Str[2]tapr_id流程ID；
	 */
	public String[] SkipToNext(Object[] obj, int tapr_id, String username,
			String remark, int appointid, String appointcon);

	/**
	 * @Methodname:跳转至指定步骤
	 * 
	 * @remark:完成当前步骤,流程流转至指定步骤,并记录操作内容
	 * 
	 * @procedure:检测操作权限->业务处理->记录操作内容->任务单跳转至指定步骤->结束当前步骤->更新业务表的流程ID
	 * 
	 * @businessMethode:SkipOperate，UpdateTaprid
	 * 
	 * @input: tapr_id：流程ID;tapr_dataid：业务表ID；tostep:指定跳转到的步骤；appointid
	 *         ：指定执行人(-2
	 *         自带SQL条件；-1指定某人（用户名）；默认为0不指定执行人；大于0为节点SQL的ID);appointcon:指定内容;
	 *         username:操作人;remark:备注信息；
	 * 
	 * @out: message[]; Str[0]:处理的状态,0失败1成功2出错;Str[1]提示信息；Str[2]tapr_id流程ID；
	 */
	public String[] SkipToN(Object[] obj, int tapr_id, int tostep,
			String username, String remark, int appointid, String appointcon);

	/**
	 * @Methodname:退回至上一步
	 * 
	 * @remark:
	 * 
	 * @procedure:检测操作权限->任务单转至上一步->业务处理->记录操作内容->更新业务表的流程ID
	 * 
	 * @businessMethode:SkipOperate，UpdateTaprid
	 * 
	 * @input:obj:业务实现类参数 tapr_id：流程ID;username:操作人;remark: 操作人备注信息；
	 * 
	 * @out: message[]; Str[0]:处理的状态,0失败1成功2出错;Str[1]提示信息；Str[2]退回后的流程ID；
	 */
	public String[] ReturnToPrev(Object[] obj, int tapr_id, String username,
			String remark);

	/**
	 * @Methodname:退回至指定步骤
	 * 
	 * @remark:
	 * 
	 * @procedure:检测操作权限->任务单退回至指定步骤->业务处理->记录操作内容->更新业务表的流程ID
	 * 
	 * @businessMethode:ReturnOperate，UpdateTaprid
	 * 
	 * @input:obj:业务实现类参数 
	 *                    tapr_id：流程ID;tostep:指定步骤(既wfno_step)；username:操作人;remark
	 *                    : 操作人备注信息；
	 * 
	 * @out: message[]; Str[0]:处理的状态,0失败1成功2出错;Str[1]提示信息；Str[2]退回后的流程ID；
	 */
	public String[] ReturnToN(Object[] obj, int tapr_id, int tostep,
			String username);

	/**
	 * @Methodname:退回至指定步骤
	 * 
	 * @remark:
	 * 
	 * @procedure:检测操作权限->任务单退回至指定步骤->业务处理->记录操作内容->更新业务表的流程ID
	 * 
	 * @businessMethode:ReturnOperate，UpdateTaprid
	 * 
	 * @input:obj:业务实现类参数 
	 *                    tapr_id：流程ID;tostep:指定步骤(既wfno_step)；username:操作人;remark
	 *                    : 操作人备注信息；
	 * 
	 * @out: message[]; Str[0]:处理的状态,0失败1成功2出错;Str[1]提示信息；Str[2]退回后的流程ID；
	 */
	public String[] ReturnToN(Object[] obj, int tapr_id, int tostep,
			String username, String remark);

	/**
	 * @Methodname:撤回至指定步骤
	 * 
	 * @remark:撤回，仅有指定步骤的操作人有撤回权限，并且撤回后也仅有此人有继续操作的权限。
	 * 
	 * @procedure:检测操作权限->任务单退回至指定步骤->业务处理->记录操作内容->更新业务表的流程ID
	 * 
	 * @businessMethode:ReturnOperate，UpdateTaprid
	 * 
	 * @input:obj:业务实现类参数 
	 *                    tapr_id：流程ID;tostep:指定步骤(既wfno_step)；username:操作人;remark
	 *                    : 操作人备注信息；
	 * 
	 * @out: message[]; Str[0]:处理的状态,0失败1成功2出错;Str[1]提示信息；
	 */
	public String[] RevokeToN(Object[] obj, int tapr_id, int tostep,
			String username);

	/**
	 * @Methodname:撤回至指定步骤
	 * 
	 * @remark:撤回，仅有指定步骤的操作人有撤回权限，并且撤回后也仅有此人有继续操作的权限。
	 * 
	 * @procedure:检测操作权限->任务单退回至指定步骤->业务处理->记录操作内容->更新业务表的流程ID
	 * 
	 * @businessMethode:ReturnOperate，UpdateTaprid
	 * 
	 * @input:obj:业务实现类参数 
	 *                    tapr_id：流程ID;tostep:指定步骤(既wfno_step)；username:操作人;remark
	 *                    : 操作人备注信息；
	 * 
	 * @out: message[]; Str[0]:处理的状态,0失败1成功2出错;Str[1]提示信息；
	 */
	public String[] RevokeToN(Object[] obj, int tapr_id, int tostep,
			String username, String remark);
	
	/** 
	* @Title: MoveToN 
	* @Description: TODO(调整任务单步骤,该方法不检查权限,可前后移动任务单) 
	* @param obj 传入实现类参数
	* @param tapr_id 流程ID
	* @param tostep 步骤ID
	* @param username 操作人
	* @param remark 备注
	* @return 执行结果
	* @return String[]    返回类型 
	* @throws 
	*/
	public String[] MoveToN(Object[] obj, int tapr_id, int tostep,
			String username, String remark);

	/**
	 * @Methodname:终止任务单
	 * 
	 * @remark:
	 * 
	 * @procedure:检测操作权限->任务单终止操作->记录操作内容->终止业务处理
	 * 
	 * @businessMethode:StopOperate
	 * 
	 * @input:obj:业务实现类参数 tapr_id：流程ID;username:操作人;remark: 操作人备注信息；
	 * 
	 * @out: message[]; Str[0]:处理的状态,0失败1成功2出错;Str[1]提示信息；
	 */
	public String[] StopTask(Object[] obj, int tapr_id, String username,
			String remark);

	/**
	 * @Methodname:处理当前任务流程后完结任务单
	 * 
	 * @remark:
	 * 
	 * @procedure:检测操作权限->任务单完结操作->业务处理当前流程->记录操作内容
	 * 
	 * @businessMethode:BusinessOp
	 * 
	 * @input:obj:业务实现类参数 tapr_id：流程ID;username:操作人;remark: 操作人备注信息；
	 * 
	 * @out: message[]; Str[0]:处理的状态,0失败1成功2出错;Str[1]提示信息；
	 */
	public String[] OverTask(Object[] obj, int tapr_id, String username,
			String remark);

	/**
	 * @Methodname:任务流程挂起
	 * 
	 * @remark:
	 * 
	 * @procedure:任务流程挂起->记录操作内容
	 * 
	 * @businessMethode:
	 * 
	 * @input: tapr_id：流程ID;username:操作人;remark: 备注信息；
	 * 
	 * @out: true:成功; false :失败
	 */
	public boolean WaitTask(int tapr_id, String username, String remark);

	/**
	 * @Methodname:任务流程重启
	 * 
	 * @remark:
	 * 
	 * @procedure: 任务流程重启->记录操作内容
	 * 
	 * @businessMethode:
	 * 
	 * @input: tapr_id：流程ID；username :用户名
	 * 
	 * @out: true:成功; false :失败；
	 */
	public boolean RestartTask(int tapr_id, String username);

	/**
	 * @Methodname:业务处理并记录操作内容
	 * 
	 * @procedure:检测权限->业务处理->记录操作内容
	 * 
	 * @input:obj:业务实现类参数； tapr_id：流程ID;username:操作人;remark: 备注信息；
	 * 
	 * @out: message[]; Str[0]:处理的状态,0失败1成功2出错;Str[1]提示信息；
	 */
	public String[] BusinessOpAddLog(Object[] obj, int tapr_id,
			String username, String remark);

	/**
	 * @Methodname:任务流程权限判断
	 * 
	 * @input: tapr_id：流程ID;username:操作人;
	 * 
	 * @out: true or false
	 */
	public boolean CheckCompetence(int tapr_id, String username);

	/*
	 * @Methodname:撤销到上一步
	 * 
	 * @input: tapr_id：流程ID;username:操作人;remark:备注信息；
	 * 
	 * @out:大于0 ；小于等于0出错;
	 */
	public int revokeToPrev(int tapr_id, String username, String remark);

}
