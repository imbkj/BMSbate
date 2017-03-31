package bll.EmBodyCheck;

import impl.WorkflowCore.WfOperateImpl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import service.WorkflowCore.WfBusinessService;
import service.WorkflowCore.WfOperateService;
import Model.EmBodyCheckCommitModel;
import Model.EmBodyCheckItemModel;
import Model.EmBodyCheckListModel;
import Model.EmBodyCheckModel;
import Model.EmBodycheckCancelModel;
import Model.embodycheckoperlogModel;
import Util.UserInfo;
import dal.EmBodyCheck.EmBcInfo_OperatetDal;
import dal.EmBodyCheck.EmBcInfo_SelectDal;

public class EmBcInfo_OperateBll {
	EmBcInfo_OperatetDal dal = new EmBcInfo_OperatetDal();

	// 体检信息新增
	public String[] EmBodyCheckAdd(EmBodyCheckModel m,
			EmBodyCheckListModel mlist) {
		m.setCid(m.getCid() == null ? 0 : m.getCid());
		m.setGid(m.getGid() == null ? 0 : m.getGid());
		Object[] obj = { "1", m };
		Map<String, String> map = new HashMap<String, String>();
		map.put("cid", m.getCid() + "");
		map.put("gid", m.getGid() + "");
		WfBusinessService wfbs = new EmBcInfoImpl();
		WfOperateService wfs = new WfOperateImpl(wfbs);

		String[] str = wfs.AddTaskToNext(obj, "员工体检新增", m.getEmbc_name()
				+ "体检新增", 54, UserInfo.getUsername(), "", m.getCid(), "", map);
		if (str[0] == "1") {
			mlist.setEbcl_embc_id(Integer.parseInt(str[3]));
			// System.out.println("embc_id="+Integer.parseInt(str[3]));
			int ebcl_id = EmBodyCheckListAdd(mlist);
			// System.out.println("ebcl_id="+ebcl_id);
			// 跳过待确定
			// m.setEmbc_tapr_id(Integer.parseInt(str[2]));
			// m.setEmbc_id(Integer.parseInt(str[3]));
			// m.setEbcl_id(ebcl_id);
			// String skipsql=",ebcl_state=2";//把状态改为待申报
			// EmBodyCheckSkipToNext(m,skipsql);
		}
		return str;
	}

	// 年度体检信息新增
	public String[] EmBodyCheckAdd2(EmBodyCheckCommitModel m) {
		Object[] obj = { "年度体检新增", m };
		Map<String, String> map = new HashMap<String, String>();
		map.put("cid", m.getCid() + "");
		map.put("gid", m.getGid() + "");
		WfBusinessService wfbs = new EmBcInfoCommitImpl();
		WfOperateService wfs = new WfOperateImpl(wfbs);
		String[] str = wfs.AddTaskToNext(obj, "员工体检新增", m.getEmba_name()
				+ "体检新增", 54, UserInfo.getUsername(), "", m.getCid(), "", map);
		return str;
	}

	// 体检信息修改
	public String[] EmBodyCheckEdit(EmBodyCheckModel m, String sql, String ty) {
		String[] str = new String[5];
		Object[] obj = { ty, m, "", sql };
		WfBusinessService wfbs = new EmBcInfoImpl();
		WfOperateService wfs = new WfOperateImpl(wfbs);
		if (m.getEmbc_tapr_id() != null) {
			str = wfs.PassToNext(obj, m.getEmbc_tapr_id(),
					UserInfo.getUsername(), "", m.getCid(), "");
			if (str[0].equals("1")) {
				if (m.getEbcl_state() != null && m.getEbcl_state().equals(3)) {
					if (str[2] != null && !str[2].equals("")) {
						Object[] objs = { m, "" };
						str = wfs.SkipToNext(objs, Integer.parseInt(str[2]),
								"系统", "", m.getCid(), "");
					}
				}
			}
		} else {
			Integer ok = updateEmbodyChecklist(m.getEbcl_id(), sql);
			if (ok > 0) {
				str[0] = "1";
			}
		}
		return str;
	}

	// 导入保健号
	public String[] importBcId(EmBodyCheckModel m) {
		String[] str = new String[5];
		WfBusinessService wfbs = new EmBcInfoImpl();
		WfOperateService wfs = new WfOperateImpl(wfbs);
		String sql = ",ebcl_bcid='" + m.getEmbc_bcid() + "',ebcl_state=4";
		String sql2 = ",embc_bcid='" + m.getEmbc_bcid() + "'";
		Object[] obj = { "6",m, sql,sql2 };
		str = wfs.SkipToN(obj, m.getEmbc_tapr_id(), 8, "系统",
				UserInfo.getUsername()+"导入保健号", 0, "");
		return str;

	}

	// 不取消体检直接重新预约
	public String[] EmBodyCheckReschedule(EmBodyCheckModel m, Integer tapr_id,
			String sql) {
		String[] str = new String[5];
		Object[] obj = { m, sql };
		WfBusinessService wfbs = new EmBcInfoImpl();
		WfOperateService wfs = new WfOperateImpl(wfbs);
		if (tapr_id != null && !tapr_id.equals(0)) {
			str = wfs.ReturnToN(obj, tapr_id, 2, "系统");
			Object[] obj2 = { "5", m };

			if (m.getEbcl_type().equals(2)) {
				wfs.SkipToN(obj2, Integer.valueOf(str[2]), 4, "系统",
						UserInfo.getUsername() + "重新预约体检", 0, "");
			}

		} else {
			Integer ok = updateEmbodyChecklist(m.getEbcl_id(), sql);
			if (ok > 0) {
				str[0] = "1";
			}
		}
		return str;
	}

	// 体检信息退回到第二步
	public String[] EmBodyCheckBack(EmBodyCheckModel m, String sql) {
		String[] str = new String[5];
		if (m.getOcon() == null || m.getOcon().equals("")) {
			m.setOcon("退回");
		}
		Object[] obj = { m, sql };
		WfBusinessService wfbs = new EmBcInfoImpl();
		WfOperateService wfs = new WfOperateImpl(wfbs);
		if (m.getEmbc_tapr_id() != null) {
			str = wfs.ReturnToN(obj, m.getEmbc_tapr_id(), 2,
					UserInfo.getUsername());
		} else {
			int k = dal.updateEmbodyChecklist(m.getEbcl_id(), sql);
			if (k > 0) {
				str[0] = "1";
			}
		}
		return str;
	}

	// 体检信息退回到第一步
	public String[] EmBodyCheckClientBack(EmBodyCheckModel m, String sql) {
		String[] str = new String[5];
		if (m.getOcon() == null || m.getOcon().equals("")) {
			m.setOcon("退回");
		}
		Object[] obj = { m, sql };
		WfBusinessService wfbs = new EmBcInfoImpl();
		WfOperateService wfs = new WfOperateImpl(wfbs);
		if (m.getEmbc_tapr_id() != null) {
			str = wfs.ReturnToN(obj, m.getEmbc_tapr_id(), 1,
					UserInfo.getUsername());
		} else {
			int k = dal.updateEmbodyChecklist(m.getEbcl_id(), sql);
			if (k > 0) {
				str[0] = "1";
			}
		}
		return str;
	}

	// 跳过
	public String[] EmBodyCheckSkipToNext(EmBodyCheckModel m, String sql) {
		String[] str = new String[5];
		Object[] obj = { m, sql };
		WfBusinessService wfbs = new EmBcInfoImpl();
		WfOperateService wfs = new WfOperateImpl(wfbs);
		String username = UserInfo.getUsername();
		if (m.getOcon().equals("入职体检，跳过预约中")) {
			username = "系统";
		}
		if (m.getEmbc_tapr_id() != null) {
			/*
			 * str = wfs.SkipToNext(obj, m.getEmbc_tapr_id(), username, "",
			 * m.getCid(), "");
			 */
			str = wfs.SkipToN(obj, m.getEmbc_tapr_id(), 5, username,
					UserInfo.getUsername() + "确认体检信息", 0, "");
		} else {
			int k = dal.updateEmbodyChecklist(m.getEbcl_id(), sql);
			if (k > 0) {
				str[0] = "1";
			}
		}
		return str;
	}

	// 暂存
	public Integer scratch(Integer id, String sql) {
		return dal.updateEmbodyChecklist(id, sql);
	}

	// 体检信息新增
	public Integer EmBodyCheckAdds(EmBodyCheckModel m) {
		return dal.EmBodyCheckAdd(m);
	}

	// 体检预约信息新增
	public Integer EmBodyCheckListAdd(EmBodyCheckListModel m) {
		return dal.EmBodyCheckListAdd(m);
	}

	// 更新EmbodyChecklist表信息
	public int updateEmbodyChecklist(int id, String str) {
		return dal.updateEmbodyChecklist(id, str);
	}

	// 根据婚姻状况调整体检项目
	public Integer modItem(String item, Integer id) {
		Integer i = 0;
		String itemId = "";
		String itemName = "";
		EmBcInfo_SelectDal dal = new EmBcInfo_SelectDal();
		List<EmBodyCheckItemModel> list = dal.getItemID(item);
		for (EmBodyCheckItemModel m : list) {
			itemId = itemId + "," + m.getEbit_id();
			itemName = itemName + "," + m.getEbit_name();
		}
		itemId = itemId.substring(1);
		itemName = itemName.substring(1);
		EmBcInfo_OperatetDal dal2 = new EmBcInfo_OperatetDal();
		dal2.updateEmbodyChecklist(id, ",ebcl_items='" + itemName
				+ "',ebcl_itemnums='" + itemId + "'");
		return i;
	}

	// 取消预约——跳到第六步（后道确认取消预约）
	public String[] EmBodyCheckcancel(EmBodycheckCancelModel m, Integer tarpid) {
		String[] str = new String[5];
		WfBusinessService wfbs = new EmBcInfoImpl();
		WfOperateService wfs = new WfOperateImpl(wfbs);
		m.setOperatecontent("前道取消预约");
		Object[] obj = { "4", m };
		if (tarpid != null) {
			str = wfs.SkipToN(obj, tarpid, 6, "系统", "", 0, "");
		} else {
			int k = dal.EmBodyCheckCancel(m);
			if (k > 0) {
				str[0] = "1";
			}
		}
		return str;
	}

	// 取消体检信息
	public Integer EmBodyCheckCancel(EmBodycheckCancelModel m) {
		return dal.EmBodyCheckCancel(m);
	}

	// 后道确认取消预约——跳转到第二步
	public String[] EmBodyCheckCFCancel(EmBodyCheckModel m, String sql) {
		String[] str = new String[5];
		m.setOcon("后道确认取消预约");
		Object[] obj = { m, sql };
		WfBusinessService wfbs = new EmBcInfoImpl();
		WfOperateService wfs = new WfOperateImpl(wfbs);
		if (m.getEmbc_tapr_id() != null) {
			str = wfs.ReturnToN(obj, m.getEmbc_tapr_id(), 2,
					UserInfo.getUsername());
		} else {
			int k = dal.updateEmbodyChecklist(m.getEbcl_id(), sql);
			if (k > 0) {
				str[0] = "1";
			}
		}
		return str;
	}

	// 跳到指定步骤
	public String[] EmBodyCheckSkip(EmBodyCheckModel m, String sql,
			Integer step, Integer nowstep) {
		String[] str = new String[5];
		Object[] obj = { "2", m, "", sql };
		WfBusinessService wfbs = new EmBcInfoImpl();
		WfOperateService wfs = new WfOperateImpl(wfbs);
		if (m.getEmbc_tapr_id() != null) {
			if (step.equals(nowstep)) {
				str = wfs.PassToNext(obj, m.getEmbc_tapr_id(),
						UserInfo.getUsername(), "", m.getCid(), "");
				if (step == 5 || step.equals(5)) {
					Integer nextSkipTapr_id = Integer.parseInt(str[2]);
					m.setEmbc_tapr_id(nextSkipTapr_id);
					str = EmBodyCheckSkipToNext(m, "");
				}
			} else {
				str = wfs.SkipToN(obj, m.getEmbc_tapr_id(), step,
						UserInfo.getUsername(), "", 0, "");

				if (str[0] == "1") {
					Integer nextTapr_id = Integer.parseInt(str[2]);
					str = wfs.PassToNext(obj, nextTapr_id,
							UserInfo.getUsername(), "", m.getCid(), "");
					if (step == 5 || step.equals(5)) {
						Integer nextSkipTapr_id = Integer.parseInt(str[2]);
						m.setEmbc_tapr_id(nextSkipTapr_id);
						str = EmBodyCheckSkipToNext(m, "");
					}
				}
			}
		} else {
			int k = dal.updateEmbodyChecklist(m.getEbcl_id(), sql);
			if (k > 0) {
				str[0] = "1";
			}
		}
		return str;
	}

	// 前道删除
	public String[] delBodyCheck(EmBodyCheckModel m, String sql) {
		String[] str = new String[5];
		m.setOcon("前道删除体检信息");
		Object[] obj = { m, sql };
		WfBusinessService wfbs = new EmBcInfoImpl();
		WfOperateService wfs = new WfOperateImpl(wfbs);
		if (m.getEmbc_tapr_id() != null) {
			str = wfs.StopTask(obj, m.getEmbc_tapr_id(), "系统",
					UserInfo.getUsername() + "删除该员工的体检数据");
		} else {
			int k = dal.updateEmbodyChecklist(m.getEbcl_id(), sql);
			if (k > 0) {
				str[0] = "1";
			}
		}
		return str;
	}

	// 更新体检信息
	public Integer updateEmbodyCheckInfo(int id, String sqlstr) {
		return dal.updateEmbodyCheckInfo(id, sqlstr);
	}

	// 更新EmbodyChecklist表信息
	public int EditEmbodyChecklist(String idstr, String str) {
		return dal.EditEmbodyChecklist(idstr, str);
	}

	// 更新体检信息任务单id
	public Integer updateEmbodyCheckInfo(String idstr, String sqlstr) {
		return dal.updateEmbodyCheckInfo(idstr, sqlstr);
	}

	// 体检信息修改
	public String[] EmBodyCheckEditAll(EmBodyCheckModel m, String sql, String ty) {
		String[] str = new String[5];
		sql = sql + ",ebcl_modtime=getdate(),ebcl_modname='"
				+ UserInfo.getUsername() + "'";
		Object[] obj = { ty, m, "", sql };
		WfBusinessService wfbs = new EmBcInfoImpl();
		WfOperateService wfs = new WfOperateImpl(wfbs);
		if (m.getEmbc_tapr_id() != null) {
			str = wfs.PassToNext(obj, m.getEmbc_tapr_id(),
					UserInfo.getUsername(), "", m.getCid(), "");
		} else {
			int k = dal.updateEmbodyChecklist(m.getEbcl_id(), sql);
			if (k > 0) {
				str[0] = "1";
			}
		}
		return str;
	}

	// 体检信息修改
	public String[] EmBodyCheckEditUpAgain(EmBodyCheckModel m, String sql,
			String ty) {
		String[] str = new String[5];
		Object[] obj = { ty, m, "", sql };
		WfBusinessService wfbs = new EmBcInfoImpl();
		WfOperateService wfs = new WfOperateImpl(wfbs);
		if (m.getEmbc_tapr_id() != null) {
			str = wfs.PassToNext(obj, m.getEmbc_tapr_id(),
					UserInfo.getUsername(), "", m.getCid(), "");
		} else {
			int k = dal.updateEmbodyChecklist(m.getEbcl_id(), sql);
			if (k > 0) {
				str[0] = "1";
			}
		}
		return str;
	}

	// 体检信息修改
	public String[] EmBodyCheckSkip(EmBodyCheckModel m, Integer tarpid) {
		WfBusinessService wfbs = new EmBcInfoImpl();
		WfOperateService wfs = new WfOperateImpl(wfbs);
		Object[] objs = { m, "" };
		String str[] = new String[5];
		if (tarpid != null) {
			str = wfs.SkipToNext(objs, tarpid, UserInfo.getUsername(), "",
					m.getCid(), "");
		} else {
			int k = dal.updateEmbodyChecklist(m.getEbcl_id(), "");
			if (k > 0) {
				str[0] = "1";
			}
		}
		return str;
	}

	// 入职体检跳过
	public String[] EmBodyCheckSkip(EmBodyCheckModel m, String sql) {
		String[] str = new String[5];
		Object[] obj = { m, sql };
		WfBusinessService wfbs = new EmBcInfoImpl();
		WfOperateService wfs = new WfOperateImpl(wfbs);
		if (m.getEmbc_tapr_id() != null) {
			str = wfs.SkipToNext(obj, m.getEmbc_tapr_id(), "系统",
					UserInfo.getUsername() + "：入职体检申报跳过预约中", m.getCid(), "");
		} else {
			int k = dal.updateEmbodyChecklist(m.getEbcl_id(), sql);
			if (k > 0) {
				str[0] = "1";
			}
		}
		return str;
	}

	// 更新embase表的emba_email
	public int updateEmabseEmail(String emba_email, int gid) {
		return dal.updateEmabseEmail(emba_email, gid);
	}

	// 更新embase表的emba_email
	public int updateEmabseMarital(String emba_marital, int gid) {
		return dal.updateEmabseMarital(emba_marital, gid);
	}

	public int insertLog(embodycheckoperlogModel m) {
		return dal.insertLog(m);
	}
}
