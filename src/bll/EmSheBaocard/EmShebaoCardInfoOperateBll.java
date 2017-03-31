package bll.EmSheBaocard;

import java.util.HashMap;
import java.util.Map;

import org.omg.PortableInterceptor.INACTIVE;

import impl.WorkflowCore.WfOperateImpl;
import service.WorkflowCore.WfBusinessService;
import service.WorkflowCore.WfOperateService;
import bll.EmHouseCard.EmHouse_MakeCardAddImpl;
import dal.EmSheBaocard.EmShebaoCardInfoOperateDal;
import Model.EmHouseMakeCardInfoModel;
import Model.EmShebaoCardInfoModel;
import Util.UserInfo;

public class EmShebaoCardInfoOperateBll {
	private EmShebaoCardInfoOperateDal dal = new EmShebaoCardInfoOperateDal();

	// 中心社保卡信息新增
	public String[] EmShebaoCardAdd(EmShebaoCardInfoModel m, Integer task_id) {
		Map map = new HashMap<>();
		map.put("cid", m.getCid());
		map.put("gid", m.getGid());
		map.put("ownmonth", m.getOwnmonth());
		Object[] obj = { "1", m };
		Integer cid = 0;
		if (m.getCid() != null) {
			cid = m.getCid();
		}
		WfBusinessService wfbs = new EmShebaoCardAddImpl();
		WfOperateService wfs = new WfOperateImpl(wfbs);
		String[] str = wfs.AddTaskToNext(obj, "社保卡新增", m.getSbcd_name() + "("
				+ m.getGid() + ")" + "社保卡新增", task_id, UserInfo.getUsername(),
				"", cid, "", map);
		return str;
	}

	// 客服社保卡信息新增
	public String[] EmShebaoCardClientAdd(EmShebaoCardInfoModel m,
			Integer task_id) {
		Map map = new HashMap<>();
		map.put("cid", m.getCid());
		map.put("gid", m.getGid());
		map.put("ownmonth", m.getOwnmonth());
		Object[] obj = { "1", m };
		Integer cid = 0;
		if (m.getCid() != null) {
			cid = m.getCid();
		}
		WfBusinessService wfbs = new EmShebaoCardAddImpl();
		WfOperateService wfs = new WfOperateImpl(wfbs);
		String[] str = wfs.AddTaskToNext(obj, "社保卡新增", m.getSbcd_name() + "("
				+ m.getGid() + ")" + "社保卡新增", task_id, UserInfo.getUsername(),
				"", cid, "", map);
		return str;
	}

	// 社保卡暂存
	public Integer sbcdAdd(EmShebaoCardInfoModel m) {
		return dal.EmShebaoCardInfoAdd(m);
	}

	// 社保卡信息更新
	public String[] EmShebaoCardUpdate(EmShebaoCardInfoModel m, String sql) {
		Object[] obj = { "2", m, sql };
		Integer cid = 0;
		if (m.getCid() != null) {
			cid = m.getCid();
		}
		WfBusinessService wfbs = new EmShebaoCardAddImpl();
		WfOperateService wfs = new WfOperateImpl(wfbs);
		String[] str = new String[5];
		if (m.getSbcd_tarpid() != null) {
			str = wfs.PassToNext(obj, m.getSbcd_tarpid(),
					UserInfo.getUsername(), "", cid, "");
		} else {
			int k = dal.updateCardInfo(sql, m.getSbcd_id());
			if (k > 0) {
				str[0] = "1";
			}
		}
		return str;
	}

	// 社保卡信息更新
	public String[] EmShebaoCardAddAgain(EmShebaoCardInfoModel m) {
		Object[] obj = { "3", m };
		WfBusinessService wfbs = new EmShebaoCardAddImpl();
		WfOperateService wfs = new WfOperateImpl(wfbs);
		Integer cid = 0;
		if (m.getCid() != null) {
			cid = m.getCid();
		}
		String[] str = new String[5];
		if (m.getSbcd_tarpid() != null) {
			str = wfs.PassToNext(obj, m.getSbcd_tarpid(),
					UserInfo.getUsername(), "", cid, "");
		} else {
			int k = dal.EmShebaoCardInfoUpdate(m);
			if (k > 0) {
				str[0] = "1";
			}
		}
		return str;
	}

	// 退回社保卡到第二步
	public String[] EmShebaoCardback(EmShebaoCardInfoModel m, String sql) {
		Object[] obj = { m, sql };
		WfBusinessService wfbs = new EmShebaoCardAddImpl();
		WfOperateService wfs = new WfOperateImpl(wfbs);
		String[] str = new String[5];
		if (m.getSbcd_tarpid() != null) {
			str = wfs.ReturnToN(obj, m.getSbcd_tarpid(), 2,
					UserInfo.getUsername());
		} else {
			int k = dal.updateCardInfo(sql, m.getSbcd_id());
			if (k > 0) {
				str[0] = "1";
			}
		}
		return str;
	}

	// 社保卡为中心签收改为员工已领时处理完后直接完结流程
	public String[] EmShebaoCardUpdateandEnd(EmShebaoCardInfoModel m, String sql) {
		Object[] obj = { "2", m, sql };
		WfBusinessService wfbs = new EmShebaoCardAddImpl();
		WfOperateService wfs = new WfOperateImpl(wfbs);
		String[] str = new String[5];
		if (m.getSbcd_tarpid() != null) {
			str = wfs.OverTask(obj, m.getSbcd_tarpid(), UserInfo.getUsername(),
					"");
		} else {
			int k = dal.updateCardInfo(sql, m.getSbcd_id());
			if (k > 0) {
				str[0] = "1";
			}
		}
		return str;
	}

	// 社保卡取消办理
	public String[] EmShebaoCardCancelAndEnd(EmShebaoCardInfoModel m,
			String cancelcase) {
		Object[] obj = { "cancel", m, cancelcase };
		WfBusinessService wfbs = new EmShebaoCardAddImpl();
		WfOperateService wfs = new WfOperateImpl(wfbs);

		String[] str = new String[5];
		if (m.getSbcd_tarpid() != null) {
			str = wfs.OverTask(obj, m.getSbcd_tarpid(), "系统",
					UserInfo.getUsername() + "操作：取消办理");
		} else {
			boolean flag = dal.CancelShebaoCardInfo(cancelcase, m.getSbcd_id());
			if (flag) {
				str[0] = "1";
				str[1] = "提交成功";
			} else {
				str[0] = "0";
				str[1] = "提交成功";
			}
		}
		return str;
	}

	// 社保卡修改状态并取消任务单
	public String[] EmShebaoCardCancelAndEnd(EmShebaoCardInfoModel m,
			String cancelcase,int stateid,String statename) {
		Object[] obj = { "edit", m, cancelcase,stateid};
		WfBusinessService wfbs = new EmShebaoCardAddImpl();
		WfOperateService wfs = new WfOperateImpl(wfbs);

		String[] str = new String[5];
		if (m.getSbcd_tarpid() != null) {
			str = wfs.OverTask(obj, m.getSbcd_tarpid(), "系统",
					UserInfo.getUsername() +"操作："+statename);
		} else {
			boolean flag = dal.CancelShebaoCardInfo(cancelcase, m.getSbcd_id(),stateid);
			if (flag) {
				str[0] = "1";
				str[1] = "提交成功";
			} else {
				str[0] = "0";
				str[1] = "失败";
			}
		}
		return str;
	}

	// 社保卡新增
	public Integer EmbodyCheckFileAdd(EmShebaoCardInfoModel m) {
		return dal.EmShebaoCardInfoAdd(m);
	}

}
