package bll.EmCensus.EmDh;

import java.util.HashMap;
import java.util.Map;

import bll.Archives.Archive_FileImportImpl;
import bll.EmSheBao.Emsi_ChangeSZSIImpl;
import dal.EmCensus.EmDh.EmDh_OperateDal;
import impl.WorkflowCore.WfOperateImpl;
import service.WorkflowCore.WfBusinessService;
import service.WorkflowCore.WfOperateService;
import Model.EmDhModel;
import Model.EmSheBaoChangeHjModel;
import Model.EmShebaoChangeSZSIModel;
import Util.UserInfo;

public class EmDh_OperateBll {
	private EmDh_OperateDal dal = new EmDh_OperateDal();

	// 调户申请新增
	public String[] EmDhAdd(EmDhModel m) { // 先判断是毕业生接收还是调干或者招调工，招调工和调干的流程id
											// 是28,毕业生接收的流程id是31,123是留学生
		int wfid = 28;
		String dhtype="人才";
		if (m.getEmdh_dhtype().equals("毕业生接收") || m.getEmdh_dhtype() == "毕业生接收") {
			wfid = 31;
			dhtype="毕业生";
		}else if (m.getEmdh_dhtype().equals("留学生接收") || m.getEmdh_dhtype() == "留学生接收") {
			wfid = 123;
			dhtype="留学生";
		}
		Object[] obj = { "1", m };
		Integer cid = 0;
		if (m.getCid() != null) {
			cid = m.getCid();
		}
		Map<String, String> map = new HashMap<String, String>();
		map.put("cid", cid + "");
		map.put("gid", m.getGid() + "");
		WfBusinessService wfbs = new EmDhIpml();
		WfOperateService wfs = new WfOperateImpl(wfbs);
		String taskname=m.getCoba_shortname() + "(" + m.getCid() + ")——"+m.getEmdh_name() + "("
				+ m.getGid() + ")("+dhtype+")调户申请";
		String[] str = wfs.AddTaskToNext(obj, "调户申请", taskname, wfid, UserInfo.getUsername(), "", cid,
				"", map);
		// 如果是中智户则直接跳到信息预审步骤（第五步）
		if (str[0] == "1") {
			if (m.getEmdh_zhtype() != null && m.getEmdh_zhtype().contains("中智")) {
				if (str[2] != null && !str[2].equals("")) {
					m.setId(Integer.parseInt(str[3]));
					String sql = ",emdh_state=2";
					Object[] object = { "2", m, sql };
					WfBusinessService service = new EmDhIpml();
					WfOperateService wfservice = new WfOperateImpl(service);
					wfservice.SkipToN(object, Integer.parseInt(str[2]), 5,
							"系统", "", cid, "");
				}
			}
		}
		return str;
	}

	// 完结调户
	public String[] OverTask(EmDhModel m, int tarp_id, String sql) {
		String[] str = new String[5];
		Object[] object = { "2", m, sql };
		WfBusinessService service = new EmDhIpml();
		WfOperateService wfservice = new WfOperateImpl(service);
		str = wfservice.OverTask(object, tarp_id, UserInfo.getUsername(), "");
		return str;
	}

	// 跳到制定步骤
	public String[] skipTask(EmDhModel m, Integer tarp_id, String sql, int step) {
		String[] str = new String[5];
		Object[] object = { "2", m, sql };
		WfBusinessService service = new EmDhIpml();
		WfOperateService wfservice = new WfOperateImpl(service);
		wfservice.SkipToN(object, tarp_id, step, "系统", "", m.getCid(), "");
		return str;
	}

	// 调户申请新增(未入职员工：密钥cid和gid)
	public String[] EmDhInfoAdd(EmDhModel m) { // 先判断是毕业生接收还是调干或者招调工，招调工和调干的流程id
												// 是28,毕业生接收的流程id是31
		int wfid = 28;
		if (m.getEmdh_dhtype().equals("毕业生接收") || m.getEmdh_dhtype() == "毕业生接收") {
			wfid = 31;
		}
		Integer cid = 0;
		if (m.getCid() != null) {
			cid = m.getCid();
		}
		Map<String, String> map = new HashMap<String, String>();
		map.put("cid", cid + "");
		map.put("gid", m.getGid() + "");
		Object[] obj = { "0", m };
		WfBusinessService wfbs = new EmDhIpml();
		WfOperateService wfs = new WfOperateImpl(wfbs);
		String[] str = wfs.AddTaskToNext(obj, "调户申请", m.getEmdh_name() + "("
				+ m.getGid() + ")调户申请", wfid, UserInfo.getUsername(), "", cid,
				"", map);
		return str;
	}

	// 调户申请状态修改
	public String[] EmDhupdate(EmDhModel m, String sql, int k) {
		String[] str = new String[5];
		Object[] obj = { "2", m, sql };
		WfBusinessService wfbs = new EmDhIpml();
		WfOperateService wfs = new WfOperateImpl(wfbs);
		if (k == 0) {
			if (m.getEmdh_taprid() != null && !m.getEmdh_taprid().equals("")
					&& m.getEmdh_taprid() > 0) {
				str = wfs.PassToNext(obj, m.getEmdh_taprid(),
						UserInfo.getUsername(), "", 0, "");
			} else {
				int n = dal.UpdateEmdhInfo(m, sql);
				if (n > 0) {
					str[0] = "1";
				}
			}
		}
		// 如果k=1则终止任务并新建任务单
		else if (k == 1) {
			if (m.getEmdh_taprid() != null && !m.getEmdh_taprid().equals("")
					&& m.getEmdh_taprid() > 0) {
				str = wfs.StopTask(obj, m.getEmdh_taprid(),
						UserInfo.getUsername(), "由于调干方式有变所以终止流程");
			} else {
				int n = dal.UpdateEmdhInfo(m, sql);
				if (n > 0) {
					str[0] = "1";
				}
			}
			if (str[0] == "1") {
				// 新建任务单但不做业务处理
				int wfid = 28;
				if (m.getEmdh_dhtype() != null) {
					if (m.getEmdh_dhtype().equals("毕业生接收")
							|| m.getEmdh_dhtype() == "毕业生接收") {
						wfid = 31;
					}
				}
				m.setStates("新增调户申请");
				Integer cid = 0;
				if (m.getCid() != null) {
					cid = m.getCid();
				}
				Map<String, String> map = new HashMap<String, String>();
				map.put("cid", cid + "");
				map.put("gid", m.getGid() + "");
				str = wfs.AddTaskToNext(obj, "调户申请", m.getEmdh_name() + "调户申请("
						+ m.getEmdh_dhtype() + ")", wfid,
						UserInfo.getUsername(), "", cid, "", map);
			}
		}
		// k=2则表示取消流程和业务
		else if (k == 2) {
			obj[0] = "1";
			if (m.getEmdh_taprid() != null && !m.getEmdh_taprid().equals("")
					&& m.getEmdh_taprid() > 0) {
				str = wfs.StopTask(obj, m.getEmdh_taprid(),
						UserInfo.getUsername(), m.getEmdh_endreason());
			} else {
				int n = dal.UpdateEmdhInfo(m, sql);
				if (n > 0) {
					str[0] = "1";
				}
			}
		}
		// 如果等于3表示是最后一步，最后一步完了后就会查询是否增加户籍变更业务
		else if (k == 3) {
			if (m.getEmdh_taprid() != null && !m.getEmdh_taprid().equals("")
					&& m.getEmdh_taprid() > 0) {
				str = wfs.PassToNext(obj, m.getEmdh_taprid(),
						UserInfo.getUsername(), "", 0, "");
				if (str[0] == "1") {

				}
			} else {
				int n = dal.UpdateEmdhInfo(m, sql);
				if (n > 0) {
					str[0] = "1";
				}
			}
		}

		// k=4表示把流程退回到第三步
		else if (k == 4) {
			if (m.getEmdh_taprid() != null && !m.getEmdh_taprid().equals("")
					&& m.getEmdh_taprid() > 0) {
				str = wfs.ReturnToN(obj, m.getEmdh_taprid(), 3,
						UserInfo.getUsername());
			} else {
				int n = dal.UpdateEmdhInfo(m, sql);
				if (n > 0) {
					str[0] = "1";
				}
			}
		}
		return str;
	}

	// 更新调户申请信息
	public int UpdateEmdhInfo(EmDhModel model, String str) {
		EmDh_OperateDal dal = new EmDh_OperateDal();
		return dal.UpdateEmdhInfo(model, str);
	}

	// 添加备注
	public int addramark(String id, String content) {
		EmDh_OperateDal dal = new EmDh_OperateDal();
		return dal.addramark(id, content);
	}

	// 调户后启动档案保管
	public Integer addFileImport(String name, Integer gid, String idcard,
			String place, String wtmode, String hj, String filedate,
			String rspaykind, String rsinvoice, String hjpaykind,
			String hjinvoice, String charge, String remark, String addname) {
		Integer i = 0;

		WfBusinessService wfbs = new Archive_FileImportImpl();
		WfOperateService wfs = new WfOperateImpl(wfbs);
		Object[] obj = { gid, idcard, place, wtmode, hj, filedate, rspaykind,
				rsinvoice, hjpaykind, hjinvoice, charge, null, remark, addname };
		Map<String, String> map = new HashMap<String, String>();
		map.put("gid", gid + "");
		String[] str = wfs.AddTaskToNext(obj, "档案调入",
				name + "(" + gid.toString() + ")申请调入档案", 13, addname, "", 0,
				"", map);
		if (str[0] == "1") {
			EmDh_OperateDal dal = new EmDh_OperateDal();
			i = Integer.parseInt(str[3]);
			dal.DocInfoAdd_(i, 5, addname);
		}
		return i;
	}

	// 调户完成触发社保户籍变更
	public String[] changeSZSIAdd(EmSheBaoChangeHjModel m, String emba_name) {
		String[] message = new String[5];
		try {
			// 启用任务单
			WfBusinessService impl = new EmHjChangeIpml();
			WfOperateService wf = new WfOperateImpl(impl);
			Object[] obj = { "1", m };
			message = wf.AddTaskToNext(obj, m.getOwnmonth() + "社保户籍变更",
					emba_name + "社保户籍变更", 113, m.getSbci_addname(), emba_name
							+ "社保户籍变更", m.getCid(), "");
		} catch (Exception e) {
			message[0] = "2";
			message[1] = "操作出错！";
		}
		return message;
	}

	// 完成户籍变更
	public String[] changeSZSIUpdate(EmSheBaoChangeHjModel m, Integer tarpid) {
		String[] message = new String[5];
		try {
			// 启用任务单
			WfBusinessService impl = new EmHjChangeIpml();
			WfOperateService wf = new WfOperateImpl(impl);
			Object[] obj = { "2", m };
			message = wf.PassToNext(obj, tarpid, UserInfo.getUsername(), "",
					m.getCid(), "");
		} catch (Exception e) {
			message[0] = "2";
			message[1] = "操作出错！";
		}
		return message;
	}

	// 客服确认社保户籍变更后触发社保户籍变更任务单
	public String[] changeSZSI(EmShebaoChangeSZSIModel m, String emba_name) {
		String[] message = new String[5];
		try {
			// 启用任务单
			WfBusinessService impl = new Emsi_ChangeSZSIImpl();
			WfOperateService wf = new WfOperateImpl(impl);
			Object[] obj = { 0, m };
			message = wf.AddTaskToNext(obj, m.getOwnmonth() + "社保户籍变更",
					emba_name + "社保户籍变更", 113, m.getEscs_addname(),
					m.getEscs_name() + "社保户籍变更", m.getCid(), "");
		} catch (Exception e) {
			message[0] = "2";
			message[1] = "操作出错！";
		}
		return message;
	}

}
