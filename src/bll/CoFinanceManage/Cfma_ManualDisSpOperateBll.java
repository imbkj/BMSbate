package bll.CoFinanceManage;

import dal.CoFinanceManage.Cfma_ManualDisposableDal;
import impl.WorkflowCore.WfOperateImpl;
import Model.CoBaseModel;
import Model.CoFinanceManualDisposableModel;
import Model.EmbaseModel;
import Util.UserInfo;
import service.WorkflowCore.WfBusinessService;
import service.WorkflowCore.WfOperateService;

/**
 * 手动添加非标
 * 
 * @author Administrator
 * 
 */
public class Cfma_ManualDisSpOperateBll implements WfBusinessService {

	/**
	 * 退回任务单
	 * 
	 * @return
	 */
	public String[] back(CoFinanceManualDisposableModel m, String sign) {
		Object[] obj = { sign, m };
		WfOperateService wfs = new WfOperateImpl(
				new Cfma_ManualDisSpOperateBll());
		String[] str = wfs.ReturnToPrev(obj, m.getCfmd_tapr_id(),
				UserInfo.getUsername(), m.getCfmd_backreason());
		return str;
	}

	/**
	 * 手动添加公司非标
	 * 
	 * @return
	 */
	public String[] addCoDispo(CoFinanceManualDisposableModel m, CoBaseModel com) {
		Object[] obj = { "1", m };
		WfOperateService wfs = new WfOperateImpl(
				new Cfma_ManualDisSpOperateBll());
		String[] str = wfs.AddTaskToNext(obj, "手动添加公司非标", com.getCoba_company()
				+ "手动添加公司非标", 114, UserInfo.getUsername(), m.getCfmd_Reason(),
				com.getCid(), null);
		return str;
	}

	/**
	 * 手动添加员工非标
	 * 
	 * @return
	 */
	public String[] addEmDispo(CoFinanceManualDisposableModel m, EmbaseModel em) {
		Object[] obj = { "添加员工非标", m };
		WfOperateService wfs = new WfOperateImpl(
				new Cfma_ManualDisSpOperateBll());
		String[] str = wfs.AddTaskToNext(obj, "手动添加员工非标", em.getEmba_name()
				+ "手动添加员工非标", 114, UserInfo.getUsername(), m.getCfmd_Reason(),
				m.getCid(), null);
		return str;
	}

	/**
	 * 把退回的任务单重新提交
	 * 
	 * @return
	 */
	public String[] reSubmit(CoFinanceManualDisposableModel m, CoBaseModel com) {
		Object[] obj = { "重新提交任务单", m };
		WfOperateService wfs = new WfOperateImpl(
				new Cfma_ManualDisSpOperateBll());
		String[] str = wfs.PassToNext(obj, m.getCfmd_tapr_id(),
				UserInfo.getUsername(), m.getCfmd_Reason(), com.getCid(), null);
		return str;
	}

	/**
	 * 审核非标
	 * 
	 * @param m
	 * @param com
	 * @return
	 */
	public String[] cheackCoDispo(CoFinanceManualDisposableModel m,
			CoBaseModel com) {
		Object[] obj = { "2", m };
		WfOperateService wfs = new WfOperateImpl(
				new Cfma_ManualDisSpOperateBll());
		String[] str = wfs.PassToNext(obj, m.getCfmd_tapr_id(),
				UserInfo.getUsername(), m.getCfmd_Reason(), com.getCid(), null);
		return str;
	}

	/**
	 * 批量审核非标
	 * 
	 * @param m
	 * @param com
	 * @return
	 */
	public String[] plCheackCoDispo(CoFinanceManualDisposableModel m) {
		Object[] obj = { "2", m };
		WfOperateService wfs = new WfOperateImpl(
				new Cfma_ManualDisSpOperateBll());
		String[] str = wfs.PassToNext(obj, m.getCfmd_tapr_id(),
				UserInfo.getUsername(), m.getCfmd_Reason(), m.getCid(), null);
		return str;
	}

	/**
	 * 重新提交时修改了金额为正数，则终止任务单
	 * 
	 * @param m
	 * @return
	 */
	public String[] stopRwd(CoFinanceManualDisposableModel m) {
		Object[] obj = { "手动非标应收金额大于0", m };
		WfOperateService wfs = new WfOperateImpl(
				new Cfma_ManualDisSpOperateBll());
		String[] str = wfs.StopTask(obj, m.getCfmd_tapr_id(),
				UserInfo.getUsername(), m.getCfmd_Reason());
		return str;
	}

	/**
	 * 删除数据并且终止任务单
	 * 
	 * @param m
	 * @return
	 */
	public String[] deleteData(CoFinanceManualDisposableModel m) {
		Object[] obj = { "删除", m };
		WfOperateService wfs = new WfOperateImpl(
				new Cfma_ManualDisSpOperateBll());
		String[] str = wfs.StopTask(obj, m.getCfmd_tapr_id(),
				UserInfo.getUsername(), m.getCfmd_Reason());
		return str;
	}

	@Override
	public String[] Operate(Object[] obj) {
		String[] str = new String[5];
		int id = 0; // 完成业务操作后返回的id
		if (obj[0].equals("1")) { // 数组第一个元素为1表示为手动添加公司非标
			Cfma_ManualDisposableDal dal = new Cfma_ManualDisposableDal();
			CoFinanceManualDisposableModel m = (CoFinanceManualDisposableModel) obj[1];
			m.setGid(0);
			id = dal.addCoDispo(m);
			if (id > 0) {
				str[0] = "1";
				str[1] = "提交成功!";
				str[2] = id + "";
				str[3] = "CoFinanceManualDisposable";
				str[4] = "手动添加公司非标";
			} else {
				str[0] = "0";
				str[1] = "提交失败!";
			}
		} else if (obj[0].equals("2")) {// 数组第一个元素为2表示为手动添加公司非标审核
			Cfma_ManualDisposableDal dal = new Cfma_ManualDisposableDal();
			CoFinanceManualDisposableModel m = (CoFinanceManualDisposableModel) obj[1];
			m.setCfmd_state(2);
			int row = dal.checkCoDispo(m);
			if (row > 0) {
				str[0] = "1";
				str[1] = "审核成功!";
				str[2] = m.getCfmd_id() + "";
				str[3] = "CoFinanceManualDisposable";
				str[4] = "审核手动添加非标";
			} else {
				str[0] = "0";
				str[1] = "审核失败!";
			}
		} else if (obj[0].equals("重新提交任务单")) {
			Cfma_ManualDisposableDal dal = new Cfma_ManualDisposableDal();
			CoFinanceManualDisposableModel m = (CoFinanceManualDisposableModel) obj[1];
			m.setCfmd_state(4);
			int row = dal.reSubCoDispo(m);
			if (row > 0) {
				str[0] = "1";
				str[1] = "提交成功!";
				str[2] = m.getCfmd_id() + "";
				str[3] = "CoFinanceManualDisposable";
				str[4] = "重新提交手动添加公司非标";
			} else {
				str[0] = "0";
				str[1] = "提交失败!";
			}
		} else if (obj[0].equals("添加员工非标")) {
			Cfma_ManualDisposableDal dal = new Cfma_ManualDisposableDal();
			CoFinanceManualDisposableModel m = (CoFinanceManualDisposableModel) obj[1];
			m.setCfmd_addname(UserInfo.getUsername());
			id = dal.addCoDispo(m);
			if (id > 0) {
				str[0] = "1";
				str[1] = "提交成功!";
				str[2] = id + "";
				str[3] = "CoFinanceManualDisposable";
				str[4] = "手动添加员工非标";
			} else {
				str[0] = "0";
				str[1] = "提交失败!";
			}
		}
		return str;
	}

	@Override
	public String[] ReturnOperate(Object[] obj) {
		int row = 0;
		String[] str = new String[5];
		if (obj[0].equals("公司退回")) {
			Cfma_ManualDisposableDal dal = new Cfma_ManualDisposableDal();
			CoFinanceManualDisposableModel m = (CoFinanceManualDisposableModel) obj[1];
			m.setCfmd_state(3);
			row = dal.moCoDispo(m);
			if (row > 0) {
				str[0] = "1";
				str[1] = "退回成功!";
				str[2] = m.getCfmd_id() + "";
				str[3] = "CoFinanceManualDisposable";
				str[4] = "退回手动添加公司非标";
			} else {
				str[0] = "0";
				str[1] = "退回失败!";
			}
		}
		return str;
	}

	@Override
	public String[] SkipOperate(Object[] obj) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String[] StopOperate(Object[] obj) {
		String[] str = new String[5];
		if (obj[0].equals("手动非标应收金额大于0")) {
			Cfma_ManualDisposableDal dal = new Cfma_ManualDisposableDal();
			CoFinanceManualDisposableModel m = (CoFinanceManualDisposableModel) obj[1];
			m.setCfmd_state(2);
			int row = dal.reSubCoDispo(m);
			if (row > 0) {
				str[0] = "1";
				str[1] = "提交成功!";
				str[2] = m.getCfmd_id() + "";
				str[3] = "CoFinanceManualDisposable";
				str[4] = "重新提交手动添加公司非标";
			} else {
				str[0] = "0";
				str[1] = "提交失败!";
			}
		} else if (obj[0].equals("删除")) {
			Cfma_ManualDisposableDal dal = new Cfma_ManualDisposableDal();
			CoFinanceManualDisposableModel m = (CoFinanceManualDisposableModel) obj[1];
			int row = dal.delete(m.getCfmd_id());
			if (row > 0) {
				str[0] = "1";
				str[1] = "删除成功!";
				str[2] = m.getCfmd_id() + "";
				str[3] = "CoFinanceManualDisposable";
				str[4] = "删除手动添加公司非标";
			} else {
				str[0] = "0";
				str[1] = "提交失败!";
			}

		}
		return str;
	}

	// 更新任务单id
	@Override
	public Boolean UpdateTaprid(int dataId, int tapr_id, int state) {
		boolean flag = false;
		Cfma_ManualDisposableDal dal = new Cfma_ManualDisposableDal();
		if (dal.updateTaprid(dataId, tapr_id) > 0) {
			flag = true;
		}
		return flag;
	}

	@Override
	public Boolean ErrOperate(int dataId) {
		// TODO Auto-generated method stub
		return null;
	}

}
