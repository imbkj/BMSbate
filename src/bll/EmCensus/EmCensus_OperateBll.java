package bll.EmCensus;

import java.util.HashMap;
import java.util.Map;

import impl.WorkflowCore.WfOperateImpl;
import service.WorkflowCore.WfBusinessService;
import service.WorkflowCore.WfOperateService;
import bll.Archives.Archive_FileProveImpl;
import bll.EmCensus.EmDh.EmDhIpml;
import Model.BackCauseInfoModel;
import Model.EmCensusModel;
import Model.EmDhModel;
import Model.EmHJBorrowCardModel;
import Util.UserInfo;
import dal.EmCensus.EmCensus_OperateDal;

public class EmCensus_OperateBll {
	EmCensus_OperateDal dal = new EmCensus_OperateDal();

	// 落户新增
	public String[] EmCensusAdd(EmCensusModel m) {
		Object[] obj = { "1", m };
		Integer cid = 0;
		if (m.getCid() != null) {
			cid = m.getCid();
		}
		String taskname=m.getCoba_shortname() + "(" + m.getCid() + ")——"
				+ m.getEmhj_name() + "(" + m.getGid() + ")户口新增";
		Map<String, String> map = new HashMap<String, String>();
		map.put("cid", cid + "");
		map.put("gid", m.getGid() + "");
		WfBusinessService wfbs = new EmCensus_addImpl();
		WfOperateService wfs = new WfOperateImpl(wfbs);
		String[] str = wfs.AddTaskToNext(
				obj,
				"落户申请",
				taskname, 40,
				UserInfo.getUsername(), "", cid, "", map);
		// 跳过待确定
		if (str[0].equals("1")) {
			String tapid = str[2], id = str[3];
			if (tapid != null && !tapid.equals("")) {
				m.setEmhj_taprid(Integer.parseInt(tapid));
				m.setEmhj_id(Integer.parseInt(id));
				m.setOperateinfo("系统跳过");
				Object[] objs = { "2", m, "" };
				WfBusinessService wfbsw = new EmCensus_addImpl();
				WfOperateService wfsw = new WfOperateImpl(wfbsw);
				//wfsw.PassToNext(objs, m.getEmhj_taprid(), UserInfo.getUsername(),"系统自动跳过", 0, "");
				wfsw.SkipToNext(objs, m.getEmhj_taprid(),  "系统", "系统自动跳过", 0, "");
			}
		}
		return str;
	}

	// 落户新增(调户完成后)
	public String[] EmCensusInfoAdd(EmCensusModel m) {
		Object[] obj = { "0", m };
		WfBusinessService wfbs = new EmCensus_addImpl();
		WfOperateService wfs = new WfOperateImpl(wfbs);
		Map<String, String> map = new HashMap<String, String>();
		Integer cid = 0;
		if (m.getCid() != null) {
			cid = m.getCid();
		}
		map.put("cid", cid + "");
		map.put("gid", m.getGid() + "");
		String[] str = wfs.AddTaskToNext(obj, "落户申请",
				m.getEmhj_name() + "落户申请", 40, UserInfo.getUsername(), "", cid,
				"", map);
		return str;
	}

	// 落户申请修改
	public String[] EmCensusUpdate(EmCensusModel m, String sql, int state) {
		String[] str = new String[5];
		Object[] obj = { "2", m, sql };
		// 等于0则转下一步
		if (state == 0&&m.getEmhj_taprid()!=null&&!m.getEmhj_taprid().equals("")) {
			WfBusinessService wfbs = new EmCensus_addImpl();
			WfOperateService wfs = new WfOperateImpl(wfbs);
			str = wfs.PassToNext(obj, m.getEmhj_taprid(),
					UserInfo.getUsername(), "", 0, "");
		}
		// 等于1则不改变任务单
		else if (state == 1) {
			EmCensus_OperateDal dal = new EmCensus_OperateDal();
			int k = dal.UpdateEmCensusInfo(m, sql);
			if (k > 0) {
				str[0] = "1";
				str[1] = "提交成功";
				str[2] = m.getEmhj_id() + "";
			}
		}
		return str;
	}

	// 借卡新增
	public String[] EmHJBorrowCardAdd(EmHJBorrowCardModel m) {
		Object[] obj = { "1", m };
		WfBusinessService wfbs = new EmCensus_BorrowCard_AddIpml();
		WfOperateService wfs = new WfOperateImpl(wfbs);
		Map<String, String> map = new HashMap<String, String>();
		String gidstr="";
		if(m.getGid()!=null)
		{
			gidstr="("+m.getGid()+")";
		}
		String taskname= m.getEmhj_name()+gidstr+"借卡申请";
		if(m.isIffamily())
		{
			taskname= m.getEhbc_wtname()+gidstr+"借卡申请";
		}
		String[] str = wfs.AddTaskToNext(obj, "户口卡借卡申请",taskname, 43, UserInfo.getUsername(), "", 0, "");
		return str;
	}

	// 借卡修改
	public String[] EmHJBorrowCardUpdate(EmHJBorrowCardModel m, String sql,
			int state) {
		String[] str = new String[5];
		Object[] obj = { "2", m, sql };
		if (state == 0) {
			WfBusinessService wfbs = new EmCensus_BorrowCard_AddIpml();
			WfOperateService wfs = new WfOperateImpl(wfbs);
			String user = UserInfo.getUsername();
			if (user == null || user.equals("")) {
				user = m.getEhbc_addname();
			}
			if (m.getEhbc_tarpid() != null) {
				str = wfs.PassToNext(obj, m.getEhbc_tarpid(), user, "", 0, "");
			} else {
				EmCensus_OperateDal dal = new EmCensus_OperateDal();
				int k = dal.UpdateEmHJBorrowCardInfo(m, sql);
				if (k > 0) {
					str[0] = "1";
					str[1] = "提交成功";
					str[2] = m.getEmhj_id() + "";
				}
			}
		} else if (state == 1) {
			EmCensus_OperateDal dal = new EmCensus_OperateDal();
			int k = dal.UpdateEmHJBorrowCardInfo(m, sql);
			if (k > 0) {
				str[0] = "1";
				str[1] = "提交成功";
				str[2] = m.getEmhj_id() + "";
			}
		} else if (state == 2) {
			if (m.getEhbc_tarpid() != null) {
				WfBusinessService wfbs = new EmCensus_BorrowCard_AddIpml();
				WfOperateService wfs = new WfOperateImpl(wfbs);
				str = wfs.SkipToN(obj, m.getEhbc_tarpid(), 5,
						UserInfo.getUsername(), "", 0, "");
			} else {
				EmCensus_OperateDal dal = new EmCensus_OperateDal();
				int k = dal.UpdateEmHJBorrowCardInfo(m, sql);
				if (k > 0) {
					str[0] = "1";
					str[1] = "提交成功";
					str[2] = m.getEmhj_id() + "";
				}
			}
		}
		// state==3表示 只借了首页复印件，审核后把流程完结
		else if (state == 3) {
			if (m.getEhbc_tarpid() != null) {
				WfBusinessService wfbs = new EmCensus_BorrowCard_AddIpml();
				WfOperateService wfs = new WfOperateImpl(wfbs);
				str = wfs.OverTask(obj, m.getEhbc_tarpid(),
						UserInfo.getUsername(), "");
			} else {
				EmCensus_OperateDal dal = new EmCensus_OperateDal();
				int k = dal.UpdateEmHJBorrowCardInfo(m, sql);
				if (k > 0) {
					str[0] = "1";
					str[1] = "提交成功";
					str[2] = m.getEmhj_id() + "";
				}
			}
		}
		return str;
	}

	// 结束借卡流程
	public String[] endjk(EmHJBorrowCardModel m, Integer tarpid) {
		String[] str = new String[5];
		Object[] obj = { m };
		WfBusinessService wfbs = new EmCensus_BorrowCard_AddIpml();
		WfOperateService wfs = new WfOperateImpl(wfbs);
		str = wfs.StopTask(obj, tarpid, "系统", UserInfo.getUsername()+"结束任务单流程");
		return str;
	}

	// 落户退回到上一步
	public String[] EmCensusback(EmCensusModel m, String sql) {
		String[] str = new String[5];
		Object[] obj = { "", m, sql };
		if(m.getEmhj_taprid()!=null&&!m.getEmhj_taprid().equals(""))
		{
			WfBusinessService wfbs = new EmCensus_addImpl();
			WfOperateService wfs = new WfOperateImpl(wfbs);
			str = wfs.ReturnToPrev(obj, m.getEmhj_taprid(), UserInfo.getUsername(),
				"");
		}
		else
		{
			EmCensus_OperateDal dal = new EmCensus_OperateDal();
			int k = dal.UpdateEmCensusInfo(m, sql);
			if (k > 0) {
				str[0] = "1";
				str[1] = "提交成功";
				str[2] = m.getEmhj_id() + "";
			}
		}
		return str;
	}

	// 落户退回并结束流程
	public String[] EmCensusClientback(EmCensusModel m, String sql) {
		String[] str = new String[5];
		Object[] obj = { "", m, sql };
		if(m.getEmhj_taprid()!=null&&!m.getEmhj_taprid().equals(""))
		{
			WfBusinessService wfbs = new EmCensus_addImpl();
			WfOperateService wfs = new WfOperateImpl(wfbs);
			str = wfs.StopTask(obj, m.getEmhj_taprid(), UserInfo.getUsername(), "");
		}else
		{
			EmCensus_OperateDal dal = new EmCensus_OperateDal();
			int k = dal.UpdateEmCensusInfo(m, sql);
			if (k > 0) {
				str[0] = "1";
				str[1] = "提交成功";
				str[2] = m.getEmhj_id() + "";
			}
		}
		return str;
	}

	// 更新落户申请信息
	public int UpdateEmCensusInfo(EmCensusModel model, String str) {
		return dal.UpdateEmCensusInfo(model, str);
	}

	public int updateBorrowCardInfo(EmHJBorrowCardModel model, String str) {
		EmCensus_OperateDal dal = new EmCensus_OperateDal();
		int k = dal.UpdateEmHJBorrowCardInfo(model, str);
		return k;
	}
}
