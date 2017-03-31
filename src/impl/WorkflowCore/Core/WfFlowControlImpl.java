/**
 * @Classname WfFlowControlImpl
 * @ClassInfo 流程控制实现类（继承WfFlowControlService接口）
 * @author 李文洁
 * @Date 2013-10-16
 */
package impl.WorkflowCore.Core;

import java.util.List;

import service.WorkflowCore.Core.WfFlowControlService;
import Model.LoginModel;
import dal.WorkflowCore.WfCoreFlowControlDal;

public class WfFlowControlImpl implements WfFlowControlService {

	/*
	 * @Methodname:检测操作权限
	 * 
	 * @input: tapr_id：流程ID；username:操作人;
	 * 
	 * @out: true or flase
	 */
	public boolean CheckCompetence(int tapr_id, String username) {
		WfCoreFlowControlDal dal = new WfCoreFlowControlDal();
		try {
			return dal.CheckCompetence(tapr_id, username);
		} catch (Exception e) {
			addErrLog(0, tapr_id, 0, "权限检查出错", username);
			return false;
		}
	}

	/*
	 * @Methodname:检查流程是否正在执行
	 * 
	 * @input: tapr_id：流程ID
	 * 
	 * @out: true or flase
	 */
	public boolean CheckProcess(int tapr_id) {
		WfCoreFlowControlDal dal = new WfCoreFlowControlDal();
		try {
			return dal.CheckProcess(tapr_id);
		} catch (Exception e) {
			return false;
		}
	}

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
			String remark) {
		WfCoreFlowControlDal dal = new WfCoreFlowControlDal();
		try {
			return dal.AddTaskLog(tapr_id, tapl_datatable, tapl_datatableid,
					tapl_content, username, remark);
		} catch (Exception e) {
			addErrLog(0, tapr_id, 0, "记录操作出错", username);
			return 0;
		}
	}

	/*
	 * @Methodname:通过当前流程
	 * 
	 * @input: tapr_id：流程ID;username:操作人;remark:备注信息；
	 * 
	 * @out:-1该步骤已处理,无法继续操作；等于0出错;1成功;
	 */
	public int PassProcess(int tapr_id, String username, String remark) {
		WfCoreFlowControlDal dal = new WfCoreFlowControlDal();
		int id;
		try {
			id = dal.PassProcess(tapr_id, username, remark);
		} catch (Exception e) {
			id = 0;
			addErrLog(0, tapr_id, 0, "通过当前流程出错", username);
		}
		return id;
	}

	/*
	 * @Methodname:通过转下一步
	 * 
	 * @input:
	 * tapr_id：流程ID；tali_name：任务名称；tapr_dataid：业务表ID；username:操作人;remark:
	 * 备注信息；appoint：指定下一步操作人的log_id,不指定传0
	 * 
	 * @out: 返回流程编号(-2为最后一步已完成任务-1该步骤已处理,无法继续操作；0出错；大于0的为流程编号)
	 */
	public int PassToNext(int tapr_id, int tapr_dataid, String username,
			String remark, int appointid, String appointcon) {
		WfCoreFlowControlDal dal = new WfCoreFlowControlDal();
		int id;
		try {
			id = dal.PassToNext(tapr_id, tapr_dataid, username, remark,
					appointid, appointcon);
		} catch (Exception e) {
			addErrLog(0, tapr_id, 0, "通过转下一步出错", username);
			id = 0;
		}
		return id;
	}

	/*
	 * @Methodname:跳过转下一步
	 * 
	 * @input: tapr_id：流程ID;username:操作人;remark:
	 * 备注信息；appoint：指定下一步操作人的log_id,不指定传0
	 * 
	 * @out: -3该步骤不允许跳过-2成功跳过最后一步已完成任务-1该步骤已处理,无法继续操作；0出错；大于0的为流程编号
	 */
	public int SkipToNext(int tapr_id, String username, String remark,
			int appointid, String appointcon) {
		WfCoreFlowControlDal dal = new WfCoreFlowControlDal();
		int id;
		try {
			id = dal.SkipToNext(tapr_id, username, remark, appointid,
					appointcon);
		} catch (Exception e) {
			addErrLog(0, tapr_id, 0, "跳过转下一步出错", username);
			id = 0;
		}
		return id;
	}

	/*
	 * @Methodname:跳转至指定步骤
	 * 
	 * @input: tapr_id：流程ID;tapr_dataid：业务表ID；tostep:指定跳转到的步骤；appointid
	 * ：指定执行人(-2自带SQL条件；-1指定某人（用户名）；默认为0不指定执行人；大于0为节点SQL的ID);appointcon:指定内容;
	 * username:操作人;remark:备注信息；
	 * 
	 * @out: -2为当前任务为最后一步不可跳转-1该步骤已处理,无法继续操作；0出错；大于0的为流程编号
	 */
	public int SkipToN(int tapr_id, int tapr_dataid, int tostep, int appointid,
			String appointcon, String username, String remark) {
		WfCoreFlowControlDal dal = new WfCoreFlowControlDal();
		int id;
		try {
			id = dal.SkipToN(tapr_id, tapr_dataid, tostep, appointid,
					appointcon, username, remark);
		} catch (Exception e) {
			addErrLog(0, tapr_id, 0, "跳转至指定步骤出错", username);
			id = 0;
		}
		return id;
	}
	
	/*
	 * @Methodname:移动至指定步骤
	 * 
	 * @input: tapr_id：流程ID;tostep:指定跳转到的步骤;
	 * username:操作人;remark:备注信息；
	 * 
	 * @out: -2为当前任务为最后一步不可跳转-1该步骤已处理,无法继续操作；0出错；大于0的为流程编号
	 */
	@Override
	public int moveToN(int tapr_id, int tostep, String username, String remark) {
		// TODO Auto-generated method stub
		int id;
		WfCoreFlowControlDal dal = new WfCoreFlowControlDal();
		try {
			id = dal.moveToN(tapr_id, tostep, username, remark);
		} catch (Exception e) {
			addErrLog(0, tapr_id, 0, "跳转至指定步骤出错", username);
			id = 0;
		}
		return id;
	}

	/*
	 * @Methodname:撤销到上一步
	 * 
	 * @input: tapr_id：流程ID;username:操作人;remark:备注信息；
	 * 
	 * @out:大于0 ；小于等于0出错;
	 */
	public int revokeToPrev(int tapr_id, String username, String remark) {
		// WfCoreFlowControlDal dal = new WfCoreFlowControlDal();
		int id;
		try {
			id = 1;
		} catch (Exception e) {
			id = 0;
		}
		return id;
	}

	/*
	 * @Methodname:退回到上一步
	 * 
	 * @input: tapr_id：流程ID;username:操作人;
	 * 
	 * @out:-2该步骤不允许退回；-1该步骤已处理,无法继续操作；等于0出错;大于0 返回流程ID；
	 */
	public int returnToPrev(int tapr_id, String username) {
		WfCoreFlowControlDal dal = new WfCoreFlowControlDal();
		int id;
		try {
			id = dal.returnToPrev(tapr_id, username);
		} catch (Exception e) {
			id = 0;
			addErrLog(0, tapr_id, 0, "退回到上一步出错", username);
		}
		return id;
	}

	/*
	 * @Methodname:退回到指定步骤
	 * 
	 * @input: tapr_id：流程ID;tostep:指定步骤(既wfno_step)；username:操作人;
	 * 
	 * @out:-2该步骤不允许退回；-1该步骤已处理,无法继续操作；等于0出错;大于0 返回流程ID；
	 */
	public int returnToN(int tapr_id, int tostep, String username) {
		WfCoreFlowControlDal dal = new WfCoreFlowControlDal();
		int id;
		try {
			id = dal.returnToN(tapr_id, tostep, username);
		} catch (Exception e) {
			id = 0;
			addErrLog(0, tapr_id, 0, "退回至指定步骤出错", username);
		}
		return id;
	}

	/*
	 * @Methodname:终止任务
	 * 
	 * @input: tapr_id：流程ID;username:操作人;remark:备注信息；
	 * 
	 * @out:-2该步骤不允许终止；-1该步骤已处理,无法继续操作；等于0出错;1成功;
	 */
	public int StopTask(int tapr_id, String username, String remark) {
		WfCoreFlowControlDal dal = new WfCoreFlowControlDal();
		int i;
		try {
			i = dal.StopTaskToDb(tapr_id, username, remark);
		} catch (Exception e) {
			i = 0;
		}
		return i;
	}

	/*
	 * @Methodname:完结任务
	 * 
	 * @input: tapr_id：流程ID;username:操作人;remark:备注信息；
	 * 
	 * @out:-1该步骤已处理,无法继续操作；等于0出错;1成功;
	 */
	public int OverTask(int tapr_id, String username, String remark) {
		WfCoreFlowControlDal dal = new WfCoreFlowControlDal();
		int i;
		try {
			i = dal.OverTask(tapr_id, username, remark);
		} catch (Exception e) {
			i = 0;
		}
		return i;
	}

	/*
	 * @Methodname:记录出错日志
	 * 
	 * @input: 任务单ID，流程ID，错误内容，操作人
	 * 
	 * @out:
	 */
	public void addErrLog(int tali_id, int tapr_id, int dataid,
			String wfel_con, String username) {
		WfCoreFlowControlDal dal = new WfCoreFlowControlDal();
		dal.addErrLog(tali_id, tapr_id, dataid, wfel_con, username);
	}

	/*
	 * @Methodname:更改流程状态
	 * 
	 * @input: tapr_id：流程ID，openState：初始状态，endState：修改后状态
	 * 
	 * @out: true:成功; false :失败
	 */
	public boolean upProcessState(int tapr_id, int openState, int endState) {
		WfCoreFlowControlDal dal = new WfCoreFlowControlDal();
		return dal.upProcessState(tapr_id, openState, endState);
	}

	/*
	 * @Methodname:通过流程ID查询可操作人
	 * 
	 * @input: tapr_id：流程ID
	 * 
	 * @out: Userid
	 */
	public List<Integer> searchUser(int tapr_id) {
		WfCoreFlowControlDal dal = new WfCoreFlowControlDal();
		return dal.searchUser(tapr_id);
	}

	// 通过流程ID查询可操作人
	public List<LoginModel> searchUserName(int tapr_id) {
		WfCoreFlowControlDal dal = new WfCoreFlowControlDal();
		return dal.searchUserName(tapr_id);
	}

	// 查询撤回至指定步骤的操作人
	public String selRevokeOpName(int tapr_id, int wfno_step) {
		WfCoreFlowControlDal dal = new WfCoreFlowControlDal();
		return dal.selRevokeOpName(tapr_id, wfno_step);
	}

	
}
