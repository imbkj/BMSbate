package bll.EmHouseCard;

import java.util.HashMap;
import java.util.Map;

import dal.EmHouseCard.EmHouse_TakeCardInfoOperateDal;
import impl.WorkflowCore.WfOperateImpl;
import service.WorkflowCore.WfBusinessService;
import service.WorkflowCore.WfOperateService;
import Conn.dbconn;
import Model.EmHouseCardFailInfoModel;
import Model.EmHouseTakeCardInfoModel;
import Util.UserInfo;

public class EmHouse_TakeCardInfoOperateBll {

	// 公积金领卡信息新增
	/* 修改--取消客服核收步骤，所有的数据直接到服务中心 */
	public String[] EmHuTakeCardAdd(EmHouseTakeCardInfoModel m) {
		Object[] obj = { "1", m };
		WfBusinessService wfbs = new EmHouse_TakeCardInfoAddImpl();
		WfOperateService wfs = new WfOperateImpl(wfbs);
		Map<String, String> map = new HashMap<String, String>();
		Integer cid = 0;
		if (m.getCid() != null) {
			cid = m.getCid();
		}
		map.put("cid", cid + "");
		map.put("gid", m.getGid() + "");
		map.put("ownmonth", m.getOwnmonth());
		String[] str = wfs.AddTaskToNext(obj, "公积金领卡新增", m.getUsername() + "("
				+ m.getGid() + ")" + "公积金领卡新增", 63, UserInfo.getUsername(), "",
				cid, "", map);

		return str;
	}

	// 跳过下一步
	public String[] EmHuTakeCardSkip(EmHouseTakeCardInfoModel m, Integer tapr_id) {
		Object[] obj = { "1", m };
		WfBusinessService wfbs = new EmHouse_TakeCardInfoAddImpl();
		WfOperateService wfs = new WfOperateImpl(wfbs);
		String[] str = wfs.SkipToNext(obj, tapr_id, "系统", "", m.getCid(), "");
		return str;
	}

	// 公积金领卡信息更新(t==0就是客服或者客服助理核收)
	public String[] updateTakeCardInfo(EmHouseTakeCardInfoModel m, String sql,
			int t) {
		Object[] obj = { "2", m, sql };
		WfBusinessService wfbs = new EmHouse_TakeCardInfoAddImpl();
		WfOperateService wfs = new WfOperateImpl(wfbs);
		String[] str = new String[6];
		if (m.getRe_taprid() != null) {
			str = wfs.PassToNext(obj, m.getRe_taprid(), UserInfo.getUsername(),
					"", m.getCid(), "");
			if (t == 0) {
				if (str[0] == "1" && m.getRe_docId() != null) {
					EmHouse_TakeCardInfoOperateDal dal = new EmHouse_TakeCardInfoOperateDal();
					String strs = ",re_docId=" + m.getRe_docId();
					dal.updateTakeCardInfo(m.getRe_id(), strs);
				}
			}
		} else {
			Integer k = updateTakeCardInfo(m.getRe_id(), sql);
			if (k > 0) {
				str[0] = "1";
			}
		}
		return str;
	}

	// 福利退回
	public String[] backTakeCardInfo(EmHouseTakeCardInfoModel m) {
		Object[] obj = { m };
		WfBusinessService wfbs = new EmHouse_TakeCardInfoAddImpl();
		WfOperateService wfs = new WfOperateImpl(wfbs);
		String[] str = new String[6];
		if (m.getRe_taprid() != null) {
			str = wfs.ReturnToN(obj, m.getRe_taprid(), 3,
					UserInfo.getUsername());
		} else {
			EmHouse_TakeCardInfoOperateDal dal = new EmHouse_TakeCardInfoOperateDal();
			int k = dal.HouseTakecardinfoBack(m);
			if (k > 0) {
				str[0] = "1";
			}
		}
		return str;
	}

	// 公积金领卡信息更新(t==1表示已交客服，则不跳转直接到下一步,t=1表示员工已领，跳转到最后节点流程完成)
	public String[] TakeCardInfoEdit(EmHouseTakeCardInfoModel m, String t,
			String sql) {
		Object[] obj = { "2", m, sql };
		String[] str = new String[5];
		WfBusinessService wfbs = new EmHouse_TakeCardInfoAddImpl();
		WfOperateService wfs = new WfOperateImpl(wfbs);
		Integer cid = 0;
		if (m.getCid() != null) {
			cid = m.getCid();
		}
		if (m.getRe_taprid() != null) {
			// 下一步
			if (t.equals("1") || t == "1") {
				str = wfs.PassToNext(obj, m.getRe_taprid(),
						UserInfo.getUsername(), "", cid, "");
			}
			// 跳到员工已领并结束流程
			else if (t.equals("2") || t == "2") {
				str = wfs.OverTask(obj, m.getRe_taprid(),
						UserInfo.getUsername(), "");
			}
		} else {
			Integer k = updateTakeCardInfo(m.getRe_id(), sql);
			if (k > 0) {
				str[0] = "1";
			}
		}
		return str;
	}

	// 添加备注
	public Integer addremark(EmHouseCardFailInfoModel m) {
		EmHouse_TakeCardInfoOperateDal dal = new EmHouse_TakeCardInfoOperateDal();
		return dal.addremark(m);
	}

	// 更新领卡信息表任务单id
	public int updateTakeCardInfo(int id, String str) {
		EmHouse_TakeCardInfoOperateDal dal = new EmHouse_TakeCardInfoOperateDal();
		return dal.updateTakeCardInfo(id, str);
	}

	// 修改状态
	public int UpdateTakeCardInfoState(EmHouseTakeCardInfoModel m,
			String newstateid) {
		EmHouse_TakeCardInfoOperateDal dal = new EmHouse_TakeCardInfoOperateDal();
		String sql = ",re_state=" + newstateid;
		int k = dal.updateTakeCardInfo(m.getRe_id(), sql);
		if (k > 0) {
			if (m.getRe_taprid() != null) {
				WfBusinessService wfbs = new EmHouse_TakeCardInfoAddImpl();
				WfOperateService wfs = new WfOperateImpl(wfbs);
				Object[] obj = { "2", m, sql };
				if (newstateid.equals(19)) {
					sql=",re_taprid = null";
					 dal.updateTakeCardInfo(m.getRe_id(), sql);
				} else {
					wfs.StopTask(obj, m.getRe_taprid(), "系统", UserInfo.getUsername()+"修改状态，结束任务单");
				}
			}
		}
		return k;
	}
	
}
