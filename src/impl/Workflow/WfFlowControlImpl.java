/**
 * @Classname WfFlowControlImpl
 * @ClassInfo 流程控制实现类（继承WfFlowControlService接口）
 * @author 李文洁
 * @Date 2013-10-16
 */
package impl.Workflow;

import service.Workflow.WfFlowControlService;
import dal.Workflow.WfCoreFlowControlDal;

public class WfFlowControlImpl implements WfFlowControlService {
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
			return 0;
		}
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
			String remark, int appoint) {
		WfCoreFlowControlDal dal = new WfCoreFlowControlDal();
		int id;
		try {
			id = dal.PassToNext(tapr_id, tapr_dataid, username, remark, appoint);
		} catch (Exception e) {
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
			int appoint) {
		WfCoreFlowControlDal dal = new WfCoreFlowControlDal();
		int id;
		try {
			id = dal.SkipToNext(tapr_id, username, remark, appoint);
		} catch (Exception e) {
			id = 0;
		}
		return id;
	}

	/*
	 * @Methodname:撤销到上一步
	 * 
	 * @input:
	 * tapr_id：流程ID;username:操作人;remark:备注信息；
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

}
