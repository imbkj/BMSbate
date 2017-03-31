package impl.WorkflowCore;

import java.util.List;
import java.util.Map;

import Util.Log4jInit;
import Util.MapStringChange;
import Util.PublishWindow;
import bll.Embase.EmBase_OnBoardBll;
import dal.Workflow.WfCoreFlowControlDal;
import impl.WorkflowCore.Core.WfTaskOperateImpl;
import impl.WorkflowCore.Core.WfFlowControlImpl;
import service.WorkflowCore.Core.WfTaskOperateService;
import service.WorkflowCore.WfBusinessService;
import service.WorkflowCore.WfOperateService;
import service.WorkflowCore.Core.WfFlowControlService;

public class WfOperateImpl implements WfOperateService {

	private WfBusinessService service;
	private WfFlowControlService wfcs = new WfFlowControlImpl();// 流程控制接口
	private WfTaskOperateService wfos = new WfTaskOperateImpl(); // 任务单操作接口

	// 构造函数(传入业务实现类)
	public WfOperateImpl(WfBusinessService impl) {
		service = impl;
	}

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
	 * @out: message[];
	 *       Str[0]:处理的状态,0失败1成功2出错;Str[1]提示信息；Str[2]返回第二部流程ID;Str[3]返回业务表主键ID
	 */
	public String[] AddTaskToNext(Object[] obj, String tacl_name,
			String tali_name, int wfbu_id, String username, String remark,
			int appointid, String appointcon) {
		return CoreAddTaskToNext(obj, tacl_name, tali_name, wfbu_id, username,
				remark, appointid, appointcon, null, true);

	}

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
			int appointid, String appointcon, Map<String, String> searchMap) {
		return CoreAddTaskToNext(obj, tacl_name, tali_name, wfbu_id, username,
				remark, appointid, appointcon,
				MapStringChange.MapToString(searchMap), true);
	}

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
	 * @out: message[];
	 *       Str[0]:处理的状态,0失败1成功2出错;Str[1]提示信息；Str[2]返回业务表主键ID;Str[3]返回流程ID;
	 */
	public String[] AddTask(Object[] obj, String tacl_name, String tali_name,
			int wfbu_id, String username, String remark) {
		return CoreAddTaskToNext(obj, tacl_name, tali_name, wfbu_id, username,
				remark, 0, "", null, false);
	}

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
			Map<String, String> searchMap) {
		return CoreAddTaskToNext(obj, tacl_name, tali_name, wfbu_id, username,
				remark, 0, "", MapStringChange.MapToString(searchMap), false);
	}

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
			String appointcon, int sappointid, String sappointcon) {
		return CoreAddSubTaskPToNext(obj, tali_name, tapr_id, 0, wfbu_id,
				username, remark, appointid, appointcon, sappointid,
				sappointcon, null, true);
	}

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
			Map<String, String> searchMap) {
		return CoreAddSubTaskPToNext(obj, tali_name, tapr_id, 0, wfbu_id,
				username, remark, appointid, appointcon, sappointid,
				sappointcon, MapStringChange.MapToString(searchMap), true);
	}

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
			String sappointcon) {
		return CoreAddSubTaskPToNext(obj, tali_name, tapr_id, pdataid, wfbu_id,
				username, remark, appointid, appointcon, sappointid,
				sappointcon, null, true);
	}

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
			String sappointcon, Map<String, String> searchMap) {
		return CoreAddSubTaskPToNext(obj, tali_name, tapr_id, pdataid, wfbu_id,
				username, remark, appointid, appointcon, sappointid,
				sappointcon, MapStringChange.MapToString(searchMap), true);
	}

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
	 * @out: message[]; Str[0]:处理的状态,0失败1成功2出错;Str[1]提示信息；Str[2]返回下一步流程ID
	 */
	public String[] PassToNext(Object[] obj, int tapr_id, String username,
			String remark, int appointid, String appointcon) {
		String[] messageBu = new String[5];
		try {
			// 检测操作权限
			if (CheckCompetence(tapr_id, username)) {
				if (CheckProcess(tapr_id)) {
					messageBu = BusinessOp(obj); // 业务处理
					if ("1".equals(messageBu[0])) {
						int dataId = Integer.parseInt(messageBu[2]); // 业务表ID
						String datatable = messageBu[3]; // 业务表名
						String opContent = messageBu[4]; // 操作内容
						String[] messageWf = CoreAddTaskLog(tapr_id, datatable,
								dataId, opContent, username, remark); // 记录操作内容
						messageWf = CorePassToNext(tapr_id, dataId, username,
								remark, appointid, appointcon); // 任务单转至下一步
						if ("1".equals(messageWf[0])) {
							int tapr_idNext = Integer.parseInt(messageWf[1]);
							UpdateTaprid(dataId, tapr_idNext, 0);// 更新业务表的流程ID
							messageBu[2] = String.valueOf(tapr_idNext);
							// 调用推送窗口
							publishWindow(wfcs.searchUser(tapr_idNext));
						} else if ("-1".equals(messageWf[0])) {
							// 业务完成，调用修改BMS业务中心状态的方法
							emOnBoardcompleteFlow(dataId, tapr_id, datatable,
									username);
							messageBu[0] = "1";
							messageBu[1] = "任务单处理完成";
						} else {
							if (!ErrOperate(dataId)) {
								addErrLog(0, tapr_id, dataId, "业务已处理，工作流引擎出错"
										+ messageWf[0] + ";" + messageWf[1],
										username);
							}
							return messageWf;
						}
					}
				} else {
					messageBu[0] = "0";
					messageBu[1] = "该步骤已被处理,无法进行操作 。。";
				}
			} else {
				messageBu[0] = "0";
				messageBu[1] = "您无操作权限，无法处理该业务。";
			}
		} catch (Exception e) {
			e.printStackTrace();
			messageBu[0] = "2";
			messageBu[1] = "ERR,任务流转出错。";
		}
		return messageBu;
	}

	/**
	 * @Methodname:跳过转下一步
	 * 
	 * @remark:通过转下一步,并记录操作内容
	 * 
	 * @procedure:检测操作权限->业务处理->任务单转至下一步->记录操作内容->更新业务表的流程ID
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
			String remark, int appointid, String appointcon) {
		String[] messageBu = new String[5];
		String[] messageWf = new String[5];
		try {
			// 检测操作权限
			if (CheckCompetence(tapr_id, username)) {
				messageBu = SkipBusinessOp(obj); // 业务处理
				if ("1".equals(messageBu[0])) {
					messageWf = CoreSkipToNext(tapr_id, username, remark,
							appointid, appointcon); // 任务单跳过操作转至下一步
					if ("1".equals(messageWf[0])) {
						CoreAddTaskLog(tapr_id, "", 0, "跳过", "系统", "跳过");

						int dataId = Integer.parseInt(messageBu[2]); // 业务表ID
						int tapr_idNext = Integer.parseInt(messageWf[1]);
						messageBu[2] = String.valueOf(tapr_idNext);
						UpdateTaprid(dataId, tapr_idNext, 0);// 更新业务表的流程ID
						// 调用推送窗口
						publishWindow(wfcs.searchUser(tapr_idNext));
					} else if ("-2".equals(messageWf[0])) {
						// 业务完成，调用修改BMS业务中心状态的方法
						try {
							emOnBoardcompleteFlow(
									Integer.parseInt(messageBu[2]), tapr_id,
									messageBu[3].toString(), username);
						} catch (Exception e) {
							e.printStackTrace();
						}
					} else {
						return messageWf;
					}
				}
			} else {
				messageBu[0] = "0";
				messageBu[1] = "您无操作权限，无法处理该业务。";
			}
		} catch (Exception e) {
			messageBu[0] = "2";
			messageBu[1] = "ERR,任务流转出错。";
		}
		return messageBu;
	}

	/**
	 * @Methodname:跳转至指定步骤
	 * 
	 * @remark:完成当前步骤,流程流转至指定步骤,并记录操作内容
	 * 
	 * @procedure:检测操作权限->业务处理->记录操作内容->任务单跳转至指定步骤->结束当前步骤->更新业务表的流程ID
	 * 
	 * @businessMethode:Operate，UpdateTaprid
	 * 
	 * @input: tapr_id：流程ID;tapr_dataid：业务表ID；tostep:指定跳转到的步骤；appointid
	 *         ：指定执行人(-2
	 *         自带SQL条件；-1指定某人（用户名）；默认为0不指定执行人；大于0为节点SQL的ID);appointcon:指定内容;
	 *         username:操作人;remark:备注信息；
	 * 
	 * @out: message[]; Str[0]:处理的状态,0失败1成功2出错;Str[1]提示信息；Str[2]tapr_id流程ID；
	 */
	public String[] SkipToN(Object[] obj, int tapr_id, int tostep,
			String username, String remark, int appointid, String appointcon) {
		String[] messageBu = new String[5];
		String[] messageWf = new String[5];
		try {
			// 检测操作权限
			if (CheckCompetence(tapr_id, username)) {
				if (CheckProcess(tapr_id)) {
					messageBu = BusinessOp(obj); // 业务处理
					if ("1".equals(messageBu[0])) {
						int dataId = Integer.parseInt(messageBu[2]); // 业务表ID
						String datatable = messageBu[3]; // 业务表名
						String opContent = messageBu[4]; // 操作内容
						messageWf = CoreAddTaskLog(tapr_id, datatable, dataId,
								opContent, username, remark); // 记录操作内容
						messageWf = CoreSkipToN(tapr_id, dataId, tostep,
								appointid, appointcon, username, remark);
						if ("1".equals(messageWf[0])) {
							int tapr_idNext = Integer.parseInt(messageWf[1]);
							messageBu[2] = String.valueOf(tapr_idNext);
							CorePassProcess(tapr_id, username, remark); // 结束当前流程
							UpdateTaprid(dataId, tapr_idNext, 0);// 更新业务表的流程ID
							// 调用推送窗口
							publishWindow(wfcs.searchUser(tapr_idNext));
						} else {
							return messageWf;
						}
					}
				} else {
					messageBu[0] = "0";
					messageBu[1] = "该步骤已被处理,无法进行操作 。。";
				}
			} else {
				messageBu[0] = "0";
				messageBu[1] = "您无操作权限，无法处理该业务。";
			}
		} catch (Exception e) {
			messageBu[0] = "2";
			messageBu[1] = "ERR,任务流转出错。";
		}
		return messageBu;
	}

	/**
	 * @Methodname:退回至上一步
	 * 
	 * @remark:
	 * 
	 * @procedure:检测操作权限->业务处理->任务单转至上一步->记录操作内容->更新业务表的流程ID
	 * 
	 * @businessMethode:ReturnOperate，UpdateTaprid
	 * 
	 * @input:obj:业务实现类参数 tapr_id：流程ID;username:操作人;remark: 操作人备注信息；
	 * 
	 * @out: message[]; Str[0]:处理的状态,0失败1成功2出错;Str[1]提示信息；Str[2]退回后的流程ID；
	 */
	public String[] ReturnToPrev(Object[] obj, int tapr_id, String username,
			String remark) {
		String[] messageBu = new String[5];
		String[] messageWf = new String[5];
		try {
			// 检测操作权限
			if (CheckCompetence(tapr_id, username)) {
				messageBu = ReturnBusinessOp(obj); // 退回业务处理
				if ("1".equals(messageBu[0])) {
					messageWf = CoreReturnToPrev(tapr_id, username); // 任务单退回操作转至上一步
					if ("1".equals(messageWf[0])) {
						int dataId = Integer.parseInt(messageBu[2]); // 业务表ID
						messageBu[2] = messageWf[1];
						String datatable = messageBu[3]; // 业务表名
						String opContent = messageBu[4]; // 操作内容
						CoreAddTaskLog(tapr_id, datatable, dataId, opContent,
								username, remark); // 记录操作内容

						int tapr_idLast = Integer.parseInt(messageWf[1]);
						UpdateTaprid(dataId, tapr_idLast, 0);// 更新业务表的流程ID
						// 调用推送窗口
						publishWindow(wfcs.searchUser(tapr_idLast));
					} else {
						return messageWf;
					}
				}
			} else {
				messageBu[0] = "0";
				messageBu[1] = "您无操作权限，无法处理该业务。";
			}
		} catch (Exception e) {
			messageBu[0] = "2";
			messageBu[1] = "ERR,任务流转出错。";
		}
		return messageBu;
	}

	/**
	 * @Methodname:退回至指定步骤
	 * 
	 * @remark:
	 * 
	 * @procedure:检测操作权限->业务处理->任务单退回至指定步骤->记录操作内容->更新业务表的流程ID
	 * 
	 * @businessMethode:ReturnOperate，UpdateTaprid
	 * 
	 * @input:obj:业务实现类参数 tapr_id：流程ID;tostep:指定步骤(既wfno_step)；username:操作人;
	 * 
	 * @out: message[]; Str[0]:处理的状态,0失败1成功2出错;Str[1]提示信息；Str[2]退回后的流程ID；
	 */
	public String[] ReturnToN(Object[] obj, int tapr_id, int tostep,
			String username) {
		String[] messageBu = new String[5];
		String[] messageWf = new String[5];
		try {
			// 检测操作权限
			if (CheckCompetence(tapr_id, username)) {
				messageBu = ReturnBusinessOp(obj); // 退回业务处理
				if ("1".equals(messageBu[0])) {
					messageWf = CoreReturnToN(tapr_id, tostep, username); // 任务单退回操作转至指定步骤
					if ("1".equals(messageWf[0])) {
						int dataId = Integer.parseInt(messageBu[2]); // 业务表ID
						messageBu[2] = messageWf[1];
						String datatable = messageBu[3]; // 业务表名
						String opContent = messageBu[4]; // 操作内容
						CoreAddTaskLog(tapr_id, datatable, dataId, opContent,
								username, "退回"); // 记录操作内容
						int tapr_idLast = Integer.parseInt(messageWf[1]);
						UpdateTaprid(dataId, tapr_idLast, 0);// 更新业务表的流程ID
						// 调用推送窗口
						publishWindow(wfcs.searchUser(tapr_idLast));
					} else {
						return messageWf;
					}
				}
			} else {
				messageBu[0] = "0";
				messageBu[1] = "您无操作权限，无法处理该业务。";
			}
		} catch (Exception e) {
			messageBu[0] = "2";
			messageBu[1] = "ERR,任务流转出错。";
		}
		return messageBu;
	}

	/**
	 * @Methodname:退回至指定步骤(增加remark字段)
	 * 
	 * @remark:
	 * 
	 * @procedure:检测操作权限->业务处理->任务单退回至指定步骤->记录操作内容->更新业务表的流程ID
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
			String username, String remark) {
		String[] messageBu = new String[5];
		String[] messageWf = new String[5];
		try {
			// 检测操作权限
			if (CheckCompetence(tapr_id, username)) {
				messageBu = ReturnBusinessOp(obj); // 退回业务处理
				if ("1".equals(messageBu[0])) {
					messageWf = CoreReturnToN(tapr_id, tostep, username); // 任务单退回操作转至指定步骤
					if ("1".equals(messageWf[0])) {
						int dataId = Integer.parseInt(messageBu[2]); // 业务表ID
						messageBu[2] = messageWf[1];
						String datatable = messageBu[3]; // 业务表名
						String opContent = messageBu[4]; // 操作内容
						CoreAddTaskLog(tapr_id, datatable, dataId, opContent,
								username, remark); // 记录操作内容
						int tapr_idLast = Integer.parseInt(messageWf[1]);
						UpdateTaprid(dataId, tapr_idLast, 0);// 更新业务表的流程ID
						// 调用推送窗口
						publishWindow(wfcs.searchUser(tapr_idLast));
					} else {
						return messageWf;
					}
				}
			} else {
				messageBu[0] = "0";
				messageBu[1] = "您无操作权限，无法处理该业务。";
			}
		} catch (Exception e) {
			messageBu[0] = "2";
			messageBu[1] = "ERR,任务流转出错。";
		}
		return messageBu;
	}

	/**
	 * @Methodname:撤回至指定步骤
	 * 
	 * @remark:1、撤回，仅有指定步骤的操作人有撤回权限，并且撤回后也仅有此人有继续操作的权限。 2、当前节点中“是否可回退”需选为是。
	 * 
	 * @procedure:检测操作权限->任务单退回至指定步骤->业务处理->记录操作内容->更新业务表的流程ID
	 * 
	 * @businessMethode:ReturnOperate，UpdateTaprid
	 * 
	 * @input:obj:业务实现类参数 tapr_id：流程ID;tostep:指定步骤(既wfno_step)；username:操作人;
	 * 
	 * @out: message[]; Str[0]:处理的状态,0失败1成功2出错;Str[1]提示信息；
	 */
	public String[] RevokeToN(Object[] obj, int tapr_id, int tostep,
			String username) {
		String[] messageBu = new String[5];
		String[] messageWf = new String[5];
		try {
			// 检测操作权限
			if (CheckRevokeCompetence(tapr_id, tostep, username)) {
				messageBu = ReturnBusinessOp(obj); // 退回业务处理
				if ("1".equals(messageBu[0])) {
					messageWf = CoreReturnToN(tapr_id, tostep, username); // 任务单退回操作转至指定步骤
					if ("1".equals(messageWf[0])) {
						int dataId = Integer.parseInt(messageBu[2]); // 业务表ID
						String datatable = messageBu[3]; // 业务表名
						String opContent = messageBu[4]; // 操作内容
						CoreAddTaskLog(tapr_id, datatable, dataId, opContent,
								username, "撤回"); // 记录操作内容
						int tapr_idLast = Integer.parseInt(messageWf[1]);
						UpdateTaprid(dataId, tapr_idLast, 0);// 更新业务表的流程ID
						// 调用推送窗口
						publishWindow(wfcs.searchUser(tapr_idLast));
					} else {
						return messageWf;
					}
				}
			} else {
				messageBu[0] = "0";
				messageBu[1] = "您无操作权限，无法处理该业务。";
			}
		} catch (Exception e) {
			messageBu[0] = "2";
			messageBu[1] = "ERR,任务流转出错。";
		}
		return messageBu;
	}

	/**
	 * @Methodname:撤回至指定步骤(增加remark字段)
	 * 
	 * @remark:1、撤回，仅有指定步骤的操作人有撤回权限，并且撤回后也仅有此人有继续操作的权限。 2、当前节点中“是否可回退”需选为是。
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
			String username, String remark) {
		String[] messageBu = new String[5];
		String[] messageWf = new String[5];
		try {
			// 检测操作权限
			if (CheckRevokeCompetence(tapr_id, tostep, username)) {
				messageBu = ReturnBusinessOp(obj); // 退回业务处理
				if ("1".equals(messageBu[0])) {
					messageWf = CoreReturnToN(tapr_id, tostep, username); // 任务单退回操作转至指定步骤
					if ("1".equals(messageWf[0])) {
						int dataId = Integer.parseInt(messageBu[2]); // 业务表ID
						String datatable = messageBu[3]; // 业务表名
						String opContent = messageBu[4]; // 操作内容
						CoreAddTaskLog(tapr_id, datatable, dataId, opContent,
								username, remark); // 记录操作内容
						int tapr_idLast = Integer.parseInt(messageWf[1]);
						UpdateTaprid(dataId, tapr_idLast, 0);// 更新业务表的流程ID
						// 调用推送窗口
						publishWindow(wfcs.searchUser(tapr_idLast));
					} else {
						return messageWf;
					}
				}
			} else {
				messageBu[0] = "0";
				messageBu[1] = "您无操作权限，无法处理该业务。";
			}
		} catch (Exception e) {
			messageBu[0] = "2";
			messageBu[1] = "ERR,任务流转出错。";
		}
		return messageBu;
	}

	/**
	 * @Methodname:终止任务单
	 * 
	 * @remark:
	 * 
	 * @procedure:检测操作权限->终止业务处理->任务单终止操作->记录操作内容
	 * 
	 * @businessMethode:StopOperate
	 * 
	 * @input:obj:业务实现类参数 tapr_id：流程ID;username:操作人;remark: 操作人备注信息；
	 * 
	 * @out: message[]; Str[0]:处理的状态,0失败1成功2出错;Str[1]提示信息；
	 */
	public String[] StopTask(Object[] obj, int tapr_id, String username,
			String remark) {
		String[] messageBu = new String[5];
		String[] messageWf = new String[5];
		try {
			// 检测操作权限
			if (CheckCompetence(tapr_id, username)) {
				messageBu = StopBusinessOp(obj);
				if ("1".equals(messageBu[0])) {
					messageWf = CoreStopTask(tapr_id, username, remark); // 任务单终止操作
					if ("1".equals(messageWf[0])) {
						int dataId = Integer.parseInt(messageBu[2]); // 业务表ID
						String datatable = messageBu[3]; // 业务表名
						String opContent = messageBu[4]; // 操作内容
						messageWf = CoreAddTaskLog(tapr_id, datatable, dataId,
								opContent, username, remark); // 记录操作内容
					} else {
						return messageWf;
					}
				} else {
					messageBu[0] = "2";
					messageBu[1] = "业务操作出错。";
				}
			} else {
				messageBu[0] = "0";
				messageBu[1] = "您无操作权限，无法处理该业务。";
			}
		} catch (Exception e) {
			e.printStackTrace();
			messageBu[0] = "2";
			messageBu[1] = "ERR,任务流转出错。";
		}
		return messageBu;
	}

	/**
	 * @Methodname:处理当前任务流程后完结任务单
	 * 
	 * @remark:
	 * 
	 * @procedure:检测操作权限->业务处理当前流程->任务单完结操作->记录操作内容
	 * 
	 * @businessMethode:Operate
	 * 
	 * @input:obj:业务实现类参数 tapr_id：流程ID;username:操作人;remark: 操作人备注信息；
	 * 
	 * @out: message[]; Str[0]:处理的状态,0失败1成功2出错;Str[1]提示信息；
	 */
	public String[] OverTask(Object[] obj, int tapr_id, String username,
			String remark) {
		String[] messageBu = new String[5];
		String[] messageWf = new String[5];
		try {
			// 检测操作权限
			if (CheckCompetence(tapr_id, username)) {
				messageBu = BusinessOp(obj);
				if ("1".equals(messageBu[0])) {
					messageWf = CoreOverTask(tapr_id, username, remark); // 任务单完结操作
					if ("1".equals(messageWf[0])) {

						int dataId = Integer.parseInt(messageBu[2]); // 业务表ID
						String datatable = messageBu[3]; // 业务表名
						String opContent = messageBu[4]; // 操作内容
						messageWf = CoreAddTaskLog(tapr_id, datatable, dataId,
								opContent, username, remark); // 记录操作内容
						// 业务完成，调用修改BMS业务中心状态的方法
						emOnBoardcompleteFlow(dataId, tapr_id, datatable,
								username);

					} else {
						return messageWf;
					}
				} else {
					messageBu[0] = "2";
					messageBu[1] = "业务操作出错。";
				}
			} else {
				messageBu[0] = "0";
				messageBu[1] = "您无操作权限，无法处理该业务。";
			}
		} catch (Exception e) {
			messageBu[0] = "2";
			messageBu[1] = "ERR,任务流转出错。";
		}
		return messageBu;
	}

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
	public boolean WaitTask(int tapr_id, String username, String remark) {
		boolean bool = false;
		try {
			bool = wfcs.upProcessState(tapr_id, 1, 6); // 任务流程挂起
			if (bool) {
				CoreAddTaskLog(tapr_id, "", 0, "", username, remark); // 记录操作内容
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return bool;
	}

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
	public boolean RestartTask(int tapr_id, String username) {
		boolean bool = false;
		try {
			bool = wfcs.upProcessState(tapr_id, 6, 1); // 任务流程重启
			if (bool) {
				CoreAddTaskLog(tapr_id, "", 0, "重新启用流程", username, ""); // 记录操作内容
				// 调用推送窗口
				publishWindow(wfcs.searchUser(tapr_id));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return bool;
	}

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
			String username, String remark) {
		String[] messageBu = new String[5];
		try {
			// 检测操作权限
			if (CheckCompetence(tapr_id, username)) {
				if (CheckProcess(tapr_id)) {
					messageBu = BusinessOp(obj); // 业务处理
					if ("1".equals(messageBu[0])) {
						int dataId = Integer.parseInt(messageBu[2]); // 业务表ID
						String datatable = messageBu[3]; // 业务表名
						String opContent = messageBu[4]; // 操作内容
						CoreAddTaskLog(tapr_id, datatable, dataId, opContent,
								username, remark); // 记录操作内容
					}
				} else {
					messageBu[0] = "0";
					messageBu[1] = "该步骤已被处理,无法进行操作 。。";
				}
			} else {
				messageBu[0] = "0";
				messageBu[1] = "您无操作权限，无法处理该业务。";
			}
		} catch (Exception e) {
			messageBu[0] = "2";
			messageBu[1] = "ERR,任务流转出错。";
		}
		return messageBu;
	}

	/**
	 * @Methodname:任务流程权限判断
	 * 
	 * @input: tapr_id：流程ID;username:操作人;
	 * 
	 * @out: true or false
	 */
	public boolean CheckCompetence(int tapr_id, String username) {
		if ("系统".equals(username)) {
			return true;
		} else {
			return wfcs.CheckCompetence(tapr_id, username);
		}
	}

	/**
	 * @Methodname:撤回至指定步骤的操作人权限判断
	 * 
	 * @input: tapr_id：当前流程ID,wfno_step:指定步骤;username:操作人;
	 * 
	 * @out: true or false
	 */
	public boolean CheckRevokeCompetence(int tapr_id, int wfno_step,
			String username) {
		if ("系统".equals(username)) {
			return true;
		} else {
			String stepAddName = wfcs.selRevokeOpName(tapr_id, wfno_step);
			if (username.equals(stepAddName) || "系统".equals(stepAddName))
				return true;
		}
		return false;
	}

	/**
	 * @Methodname:检查流程是否正在执行
	 * 
	 * @input: tapr_id：流程ID;
	 * 
	 * @out: true or false
	 */
	public boolean CheckProcess(int tapr_id) {
		return wfcs.CheckProcess(tapr_id);
	}

	// 业务方法操作
	private String[] BusinessOp(Object[] obj) {
		String[] message = new String[5];
		try {
			message = service.Operate(obj);
		} catch (Exception e) {
			e.printStackTrace();
			message[0] = "2";
			message[1] = "业务操作出错。";
		}
		return message;
	}

	// 业务方法跳过操作
	private String[] SkipBusinessOp(Object[] obj) {
		String[] message = new String[5];
		try {
			message = service.SkipOperate(obj);
		} catch (Exception e) {
			message[0] = "2";
			message[1] = "业务操作出错。";
		}
		return message;
	}

	// 业务方法退回操作
	private String[] ReturnBusinessOp(Object[] obj) {
		String[] message = new String[5];
		try {
			message = service.ReturnOperate(obj);
		} catch (Exception e) {
			message[0] = "2";
			message[1] = "业务操作出错。";
		}
		return message;
	}

	// 业务方法终止操作
	private String[] StopBusinessOp(Object[] obj) {
		String[] message = new String[5];
		try {
			message = service.StopOperate(obj);
		} catch (Exception e) {
			message[0] = "2";
			message[1] = "业务操作出错。";
		}
		return message;
	}

	// 更新业务数据表的流程ID
	private Boolean UpdateTaprid(int dataId, int tapr_id, int state) {
		try {
			if (service.UpdateTaprid(dataId, tapr_id, state)) {
				return true;
			}
		} catch (Exception e) {
			return false;
		}
		return false;
	}

	// 业务出错处理方法
	private Boolean ErrOperate(int dataId) {
		return service.ErrOperate(dataId);
	}

	// 新建任务单并转下一步
	private String[] CoreAddTaskToNext(Object[] obj, String tacl_name,
			String tali_name, int wfbu_id, String username, String remark,
			int appointid, String appointcon, String searchCon, boolean IfToNext) {
		String[] messageBu = new String[5];
		try {
			messageBu = BusinessOp(obj); // 业务处理
			if ("1".equals(messageBu[0])) {
				int dataId = Integer.parseInt(messageBu[2]); // 业务表ID
				String datatable = messageBu[3]; // 业务表名
				String opContent = messageBu[4]; // 操作内容
				String[] messageWf = CoreAddTask(tacl_name, tali_name, dataId,
						wfbu_id, username, remark, searchCon); // 新建任务单
				if ("1".equals(messageWf[0])) {
					int tapr_id = Integer.parseInt(messageWf[1]);
					UpdateTaprid(dataId, tapr_id, 0);// 更新业务表的流程ID
					messageWf = CoreAddTaskLog(tapr_id, datatable, dataId,
							opContent, username, remark); // 记录操作内容
					if (IfToNext) {
						messageWf = CorePassToNext(tapr_id, dataId, username,
								remark, appointid, appointcon); // 任务单转至下一步
						if ("1".equals(messageWf[0])) {
							int tapr_idNext = Integer.parseInt(messageWf[1]);
							UpdateTaprid(dataId, tapr_idNext, 0);// 更新业务表的流程ID
							messageBu[2] = String.valueOf(tapr_idNext);// 返回第二部流程ID
							messageBu[3] = String.valueOf(dataId); // 返回业务表主键ID
							// 调用推送窗口
							publishWindow(wfcs.searchUser(tapr_idNext));
						} else if ("-1".equals(messageWf[0])) {
							messageBu[0] = "1";
							messageBu[1] = "任务单处理完成";
						} else {
							return messageWf;
						}
					}
				} else {
					if (!ErrOperate(dataId)) {
						Log4jInit.toLog("业务已处理，工作流引擎出错。");
						addErrLog(0, 0, dataId, "业务已处理，工作流引擎出错。" + messageWf[0]
								+ ";" + messageWf[1], username);
					}
					return messageWf;
				}
			}
		} catch (Exception e) {
			System.out.println(e);
			e.printStackTrace();
			Log4jInit.toLog("err,新建任务单出错");
			messageBu[0] = "2";
			messageBu[1] = "err,新建任务单出错。";
		}
		return messageBu;

	}

	// 新增任务单
	private String[] CoreAddTask(String tacl_name, String tali_name,
			int tapr_dataid, int wfbu_id, String username, String remark,
			String searchCon) {
		String[] message = new String[5];
		int i;
		try {
			i = wfos.AddTask(tacl_name, tali_name, wfbu_id, tapr_dataid,
					username, remark, searchCon);
			if (i > 0) {
				message[0] = "1";
				message[1] = String.valueOf(i);
			} else if (i == 0) {
				message[0] = "2";
				message[1] = "新建任务单出错。";
			} else if (i == -1) {
				message[0] = "0";
				message[1] = "未找到相应的流程。";
			} else {
				message[0] = "2";
				message[1] = "未定义的错误。";
			}
		} catch (Exception e) {
			message[0] = "2";
			message[1] = "err,新建任务单出错。";
		}

		return message;
	}

	// 新建子任务单（业务处理）
	private String[] CoreAddSubTaskPToNext(Object[] obj, String tali_name,
			int tapr_id, int pdataid, int wfbu_id, String username,
			String remark, int appointid, String appointcon, int sappointid,
			String sappointcon, String searchCon, boolean IfPToNext) {
		String[] messageBu = new String[5];
		try {
			// 检测操作权限
			if (CheckCompetence(tapr_id, username)) {
				if (CheckProcess(tapr_id)) {
					messageBu = BusinessOp(obj); // 业务处理
					if ("1".equals(messageBu[0])) {
						int dataId = Integer.parseInt(messageBu[2]); // 业务表ID
						String datatable = messageBu[3]; // 业务表名
						String opContent = messageBu[4]; // 操作内容
						String[] messageWf = CoreAddTaskLog(tapr_id, datatable,
								dataId, opContent, username, remark); // 记录父任务单操作内容
						messageWf = CoreAddSubTask(tali_name, tapr_id, wfbu_id,
								dataId, appointid, appointcon, username,
								remark, searchCon); // 新建子任务单
						if ("1".equals(messageWf[0])) {
							int ctapr_id = Integer.parseInt(messageWf[1]);
							UpdateTaprid(dataId, ctapr_id, 1);// 更新子业务表的流程ID
							messageWf = CoreAddTaskLog(ctapr_id, datatable,
									dataId, opContent, username, remark); // 记录子任务单操作内容
							messageWf = CorePassToNext(ctapr_id, dataId,
									username, remark, sappointid, sappointcon); // 子任务单转至下一步
							if (IfPToNext) {
								messageWf = CorePassToNext(tapr_id, pdataid,
										username, remark, appointid, appointcon); // 父任务单转至下一步
							}
						} else {
							if (!ErrOperate(dataId)) {
								addErrLog(0, tapr_id, dataId, "子业务已处理，工作流引擎出错"
										+ messageWf[0] + ";" + messageWf[1],
										username);
							}
							return messageWf;
						}
					}
				} else {
					messageBu[0] = "0";
					messageBu[1] = "该步骤已被处理,无法进行操作 。。";
				}
			} else {
				messageBu[0] = "0";
				messageBu[1] = "您无操作权限，无法处理该业务。";
			}
		} catch (Exception e) {
			System.out.println(e);
			messageBu[0] = "2";
			messageBu[1] = "ERR,新建子任务单出错。";
		}
		return messageBu;
	}

	// 新增子任务单
	private String[] CoreAddSubTask(String tali_name, int tapr_id, int wfbu_id,
			int tapr_dataid, int nextappointid, String nextappointcon,
			String username, String remark, String searchCon) {
		String[] message = new String[5];
		int i;
		try {
			i = wfos.AddSubTask(tali_name, tapr_id, wfbu_id, tapr_dataid,
					nextappointid, nextappointcon, username, remark, searchCon);
			if (i > 0) {
				message[0] = "1";
				message[1] = String.valueOf(i);
			} else if (i == 0) {
				message[0] = "2";
				message[1] = "新建子任务单出错。";
			} else if (i == -1) {
				message[0] = "0";
				message[1] = "未找到相应的流程。";
			} else {
				message[0] = "2";
				message[1] = "未定义的错误。";
			}
		} catch (Exception e) {
			message[0] = "2";
			message[1] = "err,新建子任务单出错。";
		}

		return message;
	}

	// 记录操作日志
	private String[] CoreAddTaskLog(int tapr_id, String tapl_datatable,
			int tapl_datatableid, String tapl_content, String username,
			String remark) {
		String[] message = new String[5];
		try {
			int i = wfcs.AddTaskLog(tapr_id, tapl_datatable, tapl_datatableid,
					tapl_content, username, remark);
			if (i == 1) {
				message[0] = "1";
				message[1] = String.valueOf(i);
			} else if (i == 0) {
				message[0] = "2";
				message[1] = "记录操作内容出错。";
			} else if (i == -1) {
				message[0] = "0";
				message[1] = "该步骤已处理,无法继续操作 。";
			} else {
				message[0] = "2";
				message[1] = "未定义的错误。";
			}

		} catch (Exception e) {
			message[0] = "2";
			message[1] = "err,记录操作内容出错。";
		}
		return message;
	}

	// 通过转下一步
	private String[] CorePassToNext(int tapr_id, int tapr_dataid,
			String username, String remark, int appointid, String appointcon) {
		String[] message = new String[5];
		try {
			int i = wfcs.PassToNext(tapr_id, tapr_dataid, username, remark,
					appointid, appointcon);
			if (i > 0) {
				message[0] = "1";
				message[1] = String.valueOf(i);
			} else if (i == 0) {
				message[0] = "2";
				message[1] = "任务流转出错。";
			} else if (i == -1) {
				message[0] = "0";
				message[1] = "该步骤已被处理,无法进行操作 。";
			} else if (i == -2) {
				message[0] = "-1";
			} else {
				message[0] = "2";
				message[1] = "未定义的错误。";
			}
		} catch (Exception e) {
			message[0] = "2";
			message[1] = "err,任务流转出错。";
		}
		return message;
	}

	// 跳过转下一步
	private String[] CoreSkipToNext(int tapr_id, String username,
			String remark, int appointid, String appointcon) {
		String[] message = new String[5];
		try {
			int i = wfcs.SkipToNext(tapr_id, username, remark, appointid,
					appointcon);
			if (i > 0) {
				message[0] = "1";
				message[1] = String.valueOf(i);
			} else if (i == 0) {
				message[0] = "2";
				message[1] = "任务流转出错。";
			} else if (i == -1) {
				message[0] = "0";
				message[1] = "该步骤已被处理,无法进行操作 。";
			} else if (i == -2) {
				message[0] = "1";
			} else if (i == -3) {
				message[0] = "0";
				message[1] = "该步骤不允许跳过。";
			} else {
				message[0] = "0";
				message[1] = "未定义的错误。";
			}
		} catch (Exception e) {
			message[0] = "2";
			message[1] = "err,任务流转出错。";
		}
		return message;
	}

	// 退回上一步
	private String[] CoreReturnToPrev(int tapr_id, String username) {
		String[] message = new String[5];
		try {
			int i = wfcs.returnToPrev(tapr_id, username);
			if (i > 0) {
				message[0] = "1";
				message[1] = String.valueOf(i);
			} else if (i == 0) {
				message[0] = "2";
				message[1] = "任务流转出错。";
			} else if (i == -1) {
				message[0] = "0";
				message[1] = "该步骤已被处理,无法进行操作 。";
			} else if (i == -2) {
				message[0] = "0";
				message[1] = "该步骤不允许退回。";
			} else {
				message[0] = "0";
				message[1] = "未定义的错误。";
			}
		} catch (Exception e) {
			message[0] = "2";
			message[1] = "err,任务流转出错。";
		}
		return message;
	}

	// 退回至指定步骤
	private String[] CoreReturnToN(int tapr_id, int tostep, String username) {
		String[] message = new String[5];
		try {
			int i = wfcs.returnToN(tapr_id, tostep, username);
			if (i > 0) {
				message[0] = "1";
				message[1] = String.valueOf(i);
			} else if (i == 0) {
				message[0] = "2";
				message[1] = "任务流转出错。";
			} else if (i == -1) {
				message[0] = "0";
				message[1] = "该步骤已被处理,无法进行操作 。";
			} else if (i == -2) {
				message[0] = "0";
				message[1] = "该步骤不允许退回。";
			} else {
				message[0] = "0";
				message[1] = "未定义的错误。";
			}
		} catch (Exception e) {
			message[0] = "2";
			message[1] = "err,任务流转出错。";
		}
		return message;
	}

	// 终止任务单
	private String[] CoreStopTask(int tapr_id, String username, String remark) {
		String[] message = new String[5];
		try {
			int i = wfcs.StopTask(tapr_id, username, remark);
			if (i > 0) {
				message[0] = "1";
			} else if (i == 0) {
				message[0] = "2";
				message[1] = "任务终止出错。";
			} else if (i == -1) {
				message[0] = "0";
				message[1] = "该步骤已被处理,无法进行操作 。";
			} else if (i == -2) {
				message[0] = "0";
				message[1] = "该步骤不允许终止。";
			} else {
				message[0] = "0";
				message[1] = "未定义的错误。";
			}
		} catch (Exception e) {
			message[0] = "2";
			message[1] = "err,任务终止出错。";
		}
		return message;
	}

	// 记录错误日志
	private void addErrLog(int tali_id, int tapr_id, int dataid,
			String wfel_con, String username) {
		wfcs.addErrLog(tali_id, tapr_id, dataid, wfel_con, username);
	}

	// 通过当前流程
	private String[] CorePassProcess(int tapr_id, String username, String remark) {
		String[] message = new String[5];
		try {
			int i = wfcs.PassProcess(tapr_id, username, remark);
			if (i > 0) {
				message[0] = "1";
			} else if (i == 0) {
				message[0] = "2";
				message[1] = "当前任务办理出错。";
			} else if (i == -1) {
				message[0] = "0";
				message[1] = "该步骤已被处理,无法进行操作 。";
			} else {
				message[0] = "0";
				message[1] = "未定义的错误。";
			}
		} catch (Exception e) {
			message[0] = "2";
			message[1] = "err,当前任务办理出错。";
		}
		return message;
	}

	// 完结任务
	private String[] CoreOverTask(int tapr_id, String username, String remark) {
		String[] message = new String[5];
		try {
			int i = wfcs.OverTask(tapr_id, username, remark);
			if (i > 0) {
				message[0] = "1";
			} else if (i == 0) {
				message[0] = "2";
				message[1] = "完结任务出错。";
			} else if (i == -1) {
				message[0] = "0";
				message[1] = "该步骤已被处理,无法进行操作 。";
			} else {
				message[0] = "0";
				message[1] = "未定义的错误。";
			}
		} catch (Exception e) {
			message[0] = "2";
			message[1] = "err,完结任务办理出错。";
		}
		return message;
	}

	// 跳转至指定步骤
	private String[] CoreSkipToN(int tapr_id, int tapr_dataid, int tostep,
			int appointid, String appointcon, String username, String remark) {
		String[] message = new String[5];
		try {
			int i = wfcs.SkipToN(tapr_id, tapr_dataid, tostep, appointid,
					appointcon, username, remark);
			if (i > 0) {
				message[0] = "1";
				message[1] = String.valueOf(i);
			} else if (i == 0) {
				message[0] = "2";
				message[1] = "任务跳转出错。";
			} else if (i == -1) {
				message[0] = "0";
				message[1] = "该步骤已被处理,无法进行操作 。";
			} else if (i == -2) {
				message[0] = "0";
				message[1] = "当前任务为最后一步不可跳转。";
			} else {
				message[0] = "0";
				message[1] = "未定义的错误。";
			}
		} catch (Exception e) {
			message[0] = "2";
			message[1] = "err,任务跳转出错。";
		}
		return message;
	}

	@Override
	public String[] MoveToN(Object[] obj, int tapr_id, int tostep,
			String username, String remark) {
		// TODO Auto-generated method stub
		String[] message = new String[5];
		try {
			int i = wfcs.moveToN(tapr_id, tostep, username, remark);
			if (i > 0) {
				message[0] = "1";
				message[1] = String.valueOf(i);
			} else if (i == 0) {
				message[0] = "2";
				message[1] = "任务跳转出错。";
			} else if (i == -1) {
				message[0] = "0";
				message[1] = "该步骤已被处理,无法进行操作 。";
			} else if (i == -2) {
				message[0] = "0";
				message[1] = "当前任务为最后一步不可跳转。";
			} else {
				message[0] = "0";
				message[1] = "未定义的错误。";
			}
		} catch (Exception e) {
			message[0] = "2";
			message[1] = "err,任务跳转出错。";
		}

		return null;
	}

	/*
	 * @Methodname:撤销到上一步
	 * 
	 * @input: tapr_id：流程ID;username:操作人;remark:备注信息；
	 * 
	 * @out:大于0 ；小于等于0出错;
	 */
	public int revokeToPrev(int tapr_id, String username, String remark) {
		WfCoreFlowControlDal dal = new WfCoreFlowControlDal();
		int id;
		try {
			id = dal.revokeToPrev(tapr_id, username, remark);
		} catch (Exception e) {
			id = 0;
		}
		return id;
	}

	// 修改BMS业务中心的状态（用于完结当前步骤的所有方法）
	public void emOnBoardcompleteFlow(Integer dataid, Integer tapr_Id,
			String tbName, String addname) {
		try {
			EmBase_OnBoardBll obBll = new EmBase_OnBoardBll();
			obBll.completeFlow(dataid, tapr_Id, tbName, addname);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	// 推送提示窗口
	private void publishWindow(List<Integer> userid) {
		PublishWindow pw = new PublishWindow();
		for (Integer id : userid) {
			pw.publish("Uid" + id, "您有新的任务单等待处理。", null, null);
		}
	}

}
