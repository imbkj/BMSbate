package bll.Archives;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import impl.WorkflowCore.WfOperateImpl;

import org.zkoss.zul.Grid;
import org.zkoss.zul.ListModelList;

import service.WorkflowCore.WfBusinessService;
import service.WorkflowCore.WfOperateService;
import dal.Archives.EmArchiveDatum_OperateDal;
import dal.Embase.Embasedal;
import Model.EmArchiveDatumModel;
import Model.EmArchiveModel;
import Model.EmDhFileModel;
import Model.EmSocialinPaperModel;
import Model.EmbaseModel;
import Util.UserInfo;

public class EmArchiveDatum_OperateBll {
	EmArchiveDatum_OperateDal dal = new EmArchiveDatum_OperateDal();

	// 查询是否有档案业务数据
	public List<EmArchiveDatumModel> searchList(Integer gid, Integer nowmonth) {
		List<EmArchiveDatumModel> list = new ListModelList<>();
		list = dal.getlist(gid, nowmonth, 1);

		return list;
	}

	// 终止流程
	public boolean stopFlow(Integer id, Integer taprId) {
		boolean b = false;
		WfBusinessService wfbs = new EmArchiveDatumDataImpl();
		WfOperateService wfs = new WfOperateImpl(wfbs);
		String[] str = new String[5];
		EmArchiveDatumModel em = new EmArchiveDatumModel();
		em.setEada_id(id);
		em.setEada_state(0);
		em.setEada_modname(UserInfo.getUsername());
		Object[] obj = { "终止流程", em };
		str = wfs.StopTask(obj, taprId, UserInfo.getUsername(), "");
		if (str[0].equals("1")) {
			b = true;
		}
		return b;
	}

	// 转下一步
	public String[] Accepted(EmArchiveDatumModel m, String sql) {
		Integer cid = 0;
		if (m.getCid() != null) {
			cid = m.getCid();
		}
		String[] str = new String[5];
		Object[] obj = { "2", m, "", sql };
		WfBusinessService wfbs = new EmArchiveDatumDataImpl();
		WfOperateService wfs = new WfOperateImpl(wfbs);
		str = wfs.PassToNext(obj, m.getEada_tapr_id(), UserInfo.getUsername(),
				"", cid, "");
		return str;
	}

	// 接触材料并判断是否需要归还材料，不需要则结束流程
	public String[] sendoutdata(EmArchiveDatumModel m, String sql, String ifend) {
		Integer cid = 0;
		if (m.getCid() != null) {
			cid = m.getCid();
		}
		String[] str = new String[5];
		Object[] obj = { "2", m, "", sql };
		WfBusinessService wfbs = new EmArchiveDatumDataImpl();
		WfOperateService wfs = new WfOperateImpl(wfbs);
		str = wfs.PassToNext(obj, m.getEada_tapr_id(), UserInfo.getUsername(),
				"", cid, "");
		if (ifend.equals("否")) {
			// 结束流程
			int tapr_id = Integer.parseInt(str[2]);
			Object[] objs = { "3", m, "", sql };
			wfs.StopTask(objs, tapr_id, "系统", UserInfo.getUsername()+"终止流程");
		}
		return str;
	}

	// 跳过
	public String[] skiptonext(EmArchiveDatumModel m, String sql, Integer tarpid) {
		String[] str = new String[5];
		Object[] obj = { "2", m, "", sql };
		WfBusinessService wfbs = new EmArchiveDatumDataImpl();
		WfOperateService wfs = new WfOperateImpl(wfbs);
		str = wfs.SkipToNext(obj, tarpid, "系统", "", 0, "");
		return str;
	}

	// 退回到第2步
	public String[] EmArchiveReturn(EmArchiveDatumModel m, String remark,
			String sql) {
		// int k=dal.EmArchiveReturn(m);
		String[] str = new String[5];
		Object[] obj = { "3", m, "", sql };
		WfBusinessService wfbs = new EmArchiveDatumDataImpl();
		WfOperateService wfs = new WfOperateImpl(wfbs);
		str = wfs
				.ReturnToN(obj, m.getEada_tapr_id(), 2, UserInfo.getUsername());
		return str;
	}

	// 退回到上一步
	public String[] EmArchiveback(EmArchiveDatumModel m, String remark,
			String sql) {
		String[] str = new String[5];
		Object[] obj = { "3", m, "", sql };
		WfBusinessService wfbs = new EmArchiveDatumDataImpl();
		WfOperateService wfs = new WfOperateImpl(wfbs);
		str = wfs.ReturnToPrev(obj, m.getEada_tapr_id(),
				UserInfo.getUsername(), remark);
		return str;
	}

	// 材料收回
	public String EmArchiveDataRetake(EmArchiveDatumModel m) {
		String str = "";
		int k = dal.EmArchiveDataRetake(m);
		if (k > 0) {
			str = "提交成功";
		} else if (str == "-2") {
			str = "系统出错了，请联系开发人员";
		} else {
			str = "提交失败";
		}
		return str;
	}

	// 开具证明新增
	public String[] EmArchiveFileProveAdd(EmArchiveDatumModel m, String remark,
			String state) {
		Integer cid = 0;
		if (m.getCid() != null) {
			cid = m.getCid();
		}
		Map<String, String> map = new HashMap<String, String>();
		map.put("cid", cid + "");
		map.put("gid", m.getGid() + "");
		Object[] obj = { "1", m, remark, state };
		WfBusinessService wfbs = new Archive_FileProveImpl();
		WfOperateService wfs = new WfOperateImpl(wfbs);
		String[] str = wfs.AddTaskToNext(obj, "出具证明", m.getEada_type(), 17,
				UserInfo.getUsername(), "", cid, "", map);
		if (str[0].equals("1")) {
			if (state == "0" || state.equals("0")) {
				String sql = ",eada_final=1";
				int tapr_id = Integer.parseInt(str[2]);
				int id = Integer.parseInt(str[3]);
				Object[] objs = { "2", m, "", sql };
				m.setEada_id(id);
				str = wfs.SkipToNext(objs, tapr_id, UserInfo.getUsername(), "",
						0, "");
				if (str[0] == "0") {
					dal.deleteInfo(id, tapr_id);
				}
			} else {
				m.setEada_id(Integer.parseInt(str[3]));
				String sql = ",eada_final=0";
				int k = dal.EmArchiveUpdateData(m, "", sql);
				if (k > 0) {
					str[0] = "1";
				}
			}
		}
		return str;
	}

	// 开具证明修改
	public String[] EmArchiveOpenProve(EmArchiveDatumModel m) {
		Integer cid = 0;
		if (m.getCid() != null) {
			cid = m.getCid();
		}
		Object[] obj = { "2", m, "", 0 };
		WfBusinessService wfbs = new Archive_FileProveImpl();
		WfOperateService wfs = new WfOperateImpl(wfbs);
		String[] str = wfs.PassToNext(obj, m.getEada_tapr_id(),
				UserInfo.getUsername(), "", cid, "");
		return str;
	}

	// 材料归档修改
	public String[] EmArchiveDataFile(EmArchiveDatumModel m, String remark,
			String state, String sql) {
		Integer cid = 0;
		if (m.getCid() != null) {
			cid = m.getCid();
		}
		m.setEada_final(state);
		String[] str = new String[5];
		Object[] obj = { "2", m, remark, sql };
		if (state == "1" || state.equals("1")) {
			WfBusinessService wfbs = new Archive_FileFilingImpl();
			WfOperateService wfs = new WfOperateImpl(wfbs);
			str = wfs.PassToNext(obj, m.getEada_tapr_id(),
					UserInfo.getUsername(), "", cid, "");
		} else {
			int k = dal.EmArchiveUpdateData(m, remark, sql);
			if (k > 0) {
				str[0] = "1";
			}
		}
		return str;
	}

	// 转正定级修改
	public String[] EmArchivePositive(EmArchiveDatumModel m, String remark,
			String state, String sql) {
		Integer cid = 0;
		if (m.getCid() != null) {
			cid = m.getCid();
		}
		m.setEada_final(state);
		String[] str = new String[5];
		Object[] obj = { "2", m, remark, sql };
		if (state == "1" || state.equals("1")) {
			WfBusinessService wfbs = new EmArchiveFilePassImpl();
			WfOperateService wfs = new WfOperateImpl(wfbs);
			str = wfs.PassToNext(obj, m.getEada_tapr_id(),
					UserInfo.getUsername(), "", cid, "");
		} else {
			int k = dal.EmArchiveUpdateData(m, remark, sql);
			if (k > 0) {
				str[0] = "1";
			}
		}
		return str;
	}

	// 档案调出修改
	public String[] EmArchiveCheckOut(EmArchiveDatumModel m, String remark,
			String state, String sql, int emar_id) {

		// int k=dal.EmArchiveCheckOut(m, emar_id);
		Integer cid = 0;
		if (m.getCid() != null) {
			cid = m.getCid();
		}
		m.setEada_final(state);
		String[] str = new String[5];
		Object[] obj = { "2", m, remark, sql, emar_id };
		if (state == "1" || state.equals("1")) {
			WfBusinessService wfbs = new Archive_FileCheckOutImpl();
			WfOperateService wfs = new WfOperateImpl(wfbs);
			str = wfs.PassToNext(obj, m.getEada_tapr_id(),
					UserInfo.getUsername(), "", cid, "");
		} else {
			int k = dal.EmArchiveUpdateData(m, remark, sql);
			if (k > 0) {
				str[0] = "1";
			}
		}
		return str;
	}

	// 档案调出完成并把档案表的状态该为0
	public String[] EmArchiveCheckOutEnd(EmArchiveDatumModel m) {
		Integer cid = 0;
		if (m.getCid() != null) {
			cid = m.getCid();
		}
		String[] str = new String[5];
		Object[] obj = { "3", m };
		WfBusinessService wfbs = new Archive_FileCheckOutImpl();
		WfOperateService wfs = new WfOperateImpl(wfbs);
		str = wfs.PassToNext(obj, m.getEada_tapr_id(), UserInfo.getUsername(),
				"", cid, "");
		return str;
	}

	// 档案调出修改(市内人才委托时的是否欠费使用)
	public String[] EmArchiveCheckOutifArrears(EmArchiveDatumModel m,
			String remark, String state, String sql, int emar_id) {
		Integer cid = 0;
		if (m.getCid() != null) {
			cid = m.getCid();
		}
		m.setEada_final(state);
		String[] str = new String[5];
		Object[] obj = { "2", m, remark, sql, emar_id };
		if (state == "1" || state.equals("1")) {
			WfBusinessService wfbs = new Archive_FileCheckOutImpl();
			WfOperateService wfs = new WfOperateImpl(wfbs);
			str = wfs.PassToNext(obj, m.getEada_tapr_id(),
					UserInfo.getUsername(), "", cid, "");

			if (str[0].equals("1")) {
				// 判断是否欠费,如果不欠费就跳到最后一步,1表示没有欠费
				if (m.getEada_ifArrears() == 1
						|| m.getEada_ifArrears().equals("1")) {
					sql = "";
					int tapr_id = Integer.parseInt(str[2]);
					int id = m.getEada_id();
					Object[] objs = { "2", m, "", sql };
					wfs.SkipToN(objs, tapr_id, 8, "系统", "", 0, "");
				}
			}
		} else {
			int k = dal.EmArchiveUpdateData(m, remark, sql);
			if (k > 0) {
				str[0] = "1";
			}
		}
		return str;
	}

	// 欠费情况
	public String EmArchiveFee(EmArchiveDatumModel m) {
		String str = "";
		int k = dal.EmArchiveFee(m);
		if (k > 0) {
			str = "提交成功";
		} else if (str == "-2") {
			str = "系统出错了，请联系开发人员";
		} else {
			str = "提交失败";
		}
		return str;
	}

	// 新增材料借阅数据
	public String[] EmArchiveAddData(EmArchiveDatumModel m, String remark,
			String state) {
		Integer cid = 0;
		if (m.getCid() != null) {
			cid = m.getCid();
		}
		Map<String, String> map = new HashMap<String, String>();
		map.put("cid", cid + "");
		map.put("gid", m.getGid() + "");
		Object[] obj = { "1", m, remark, state };
		WfBusinessService wfbs = new EmArchiveDatumDataImpl();
		WfOperateService wfs = new WfOperateImpl(wfbs);
		String[] str = wfs.AddTaskToNext(obj, "查借材料", m.getEada_type(), 7,
				UserInfo.getUsername(), "", cid, "", map);
		if (str[0].equals("1")) {
			if (state == "0" || state.equals("0")) {
				String sql = ",eada_final=1";
				int tapr_id = Integer.parseInt(str[2]);
				int id = Integer.parseInt(str[3]);
				Object[] objs = { "2", m, "", sql };
				m.setEada_id(id);
				str = wfs.SkipToNext(objs, tapr_id, UserInfo.getUsername(), "",
						0, "");
				if (str[0] == "0") {
					dal.deleteInfo(id, tapr_id);
				}
			} else {
				m.setEada_id(Integer.parseInt(str[3]));
				String sql = ",eada_final=0";
				int k = dal.EmArchiveUpdateData(m, "", sql);
				if (k > 0) {
					str[0] = "1";
				}
			}
		}
		return str;
	}

	// 修改档案业务信息
	public String[] EmArchiveAddUpdate(EmArchiveDatumModel m, String remark,
			String state, String sql) {
		Integer cid = 0;
		if (m.getCid() != null) {
			cid = m.getCid();
		}
		m.setEada_final(state);
		String[] str = new String[5];
		Object[] obj = { "2", m, remark, sql };
		if (state == "0" || state.equals("0")) {
			WfBusinessService wfbs = new EmArchiveDatumDataImpl();
			WfOperateService wfs = new WfOperateImpl(wfbs);
			str = wfs.PassToNext(obj, m.getEada_tapr_id(),
					UserInfo.getUsername(), "", cid, "");
		} else {
			int k = dal.EmArchiveUpdateData(m, remark, sql);
			if (k > 0) {
				str[0] = "1";
			}
		}
		return str;
	}

	// 材料鉴别归档新增
	public String[] EmArchiveFilingAdd(EmArchiveDatumModel m, String remark,
			String state) {
		Integer cid = 0;
		if (m.getCid() != null) {
			cid = m.getCid();
		}
		Map<String, String> map = new HashMap<String, String>();
		map.put("cid", cid + "");
		map.put("gid", m.getGid() + "");
		Object[] obj = { "1", m, remark, state };
		WfBusinessService wfbs = new Archive_FileFilingImpl();
		WfOperateService wfs = new WfOperateImpl(wfbs);
		String[] str = wfs.AddTaskToNext(obj, "材料鉴别", m.getEada_type(), 20,
				UserInfo.getUsername(), "", cid, "", map);
		if (str[0].equals("1")) {
			if (state == "0" || state.equals("0")) {// 跳过待确定
				String sql = ",eada_final=1";
				int tapr_id = Integer.parseInt(str[2]);
				int id = Integer.parseInt(str[3]);
				Object[] objs = { "2", m, "", sql };
				m.setEada_id(id);
				str = wfs.SkipToNext(objs, tapr_id, UserInfo.getUsername(), "",
						0, "");
				if (str[0] == "0") {
					dal.deleteInfo(id, tapr_id);
				}
			} else {
				m.setEada_id(Integer.parseInt(str[3]));
				String sql = ",eada_final=0";
				int k = dal.EmArchiveUpdateData(m, "", sql);
				if (k > 0) {
					str[0] = "1";
				}
			}
		}
		return str;
	}

	// 转正定级新增
	public String[] EmArchiveFilePassAdd(EmArchiveDatumModel m, String remark,
			String state) {
		Integer cid = 0;
		if (m.getCid() != null) {
			cid = m.getCid();
		}
		Map<String, String> map = new HashMap<String, String>();
		map.put("cid", cid + "");
		map.put("gid", m.getGid() + "");
		Object[] obj = { "1", m, remark, state };
		WfBusinessService wfbs = new EmArchiveFilePassImpl();
		WfOperateService wfs = new WfOperateImpl(wfbs);
		String[] str = wfs.AddTaskToNext(obj, "转正定级", m.getEada_type(), 21,
				UserInfo.getUsername(), "", cid, "", map);
		if (str[0].equals("1")) {
			if (state == "0" || state.equals("0")) {// 跳过待确定
				String sql = ",eada_final=1";
				int tapr_id = Integer.parseInt(str[2]);
				int id = Integer.parseInt(str[3]);
				Object[] objs = { "2", m, "", sql };
				m.setEada_id(id);
				str = wfs.SkipToNext(objs, tapr_id, UserInfo.getUsername(), "",
						0, "");
				if (str[0] == "0") {
					dal.deleteInfo(id, tapr_id);
				}
			} else {
				m.setEada_id(Integer.parseInt(str[3]));
				String sql = ",eada_final=0,eada_zg=" + m.getEada_zg()
						+ ",eada_bf=" + m.getEada_bf() + ",eada_dms="
						+ m.getEada_dms();
				int k = dal.EmArchiveUpdateData(m, "", sql);
				if (k > 0) {
					str[0] = "1";
				}
			}
		}
		return str;
	}

	// 调出新增
	public String[] EmArchiveCheckOut(EmArchiveDatumModel m, String remark,
			String state, EmArchiveModel model) {
		Integer cid = 0;
		if (m.getCid() != null) {
			cid = m.getCid();
		}
		Map<String, String> map = new HashMap<String, String>();
		map.put("cid", cid + "");
		map.put("gid", m.getGid() + "");
		int wfbu_id = 22;// 22是中智保管调出的任务单号
		if (model.getEmar_archivetype() == "委托人才"
				|| model.getEmar_archivetype().equals("委托人才")) {
			if (model.getEmar_archivearea() == "市外"
					|| model.getEmar_archivearea().equals("市外")) {
				wfbu_id = 24;// 24表示市外人才委托调出
				m.setEada_type(model.getEmar_name() + "(" + model.getGid()
						+ ")(市外人才)档案调出");
			} else {
				wfbu_id = 25;// 24表示市内人才委托调出
				m.setEada_type(model.getEmar_name() + "(" + model.getGid()
						+ ")(市内人才)档案调出");
			}
		} else {
			m.setEada_type(model.getEmar_name() + "(" + model.getGid() + ")"
					+ "(中智)档案调出");
		}
		Object[] obj = { "1", m, remark, state };
		WfBusinessService wfbs = new Archive_FileCheckOutImpl();
		WfOperateService wfs = new WfOperateImpl(wfbs);
		String[] str = wfs.AddTaskToNext(obj, "档案调出", m.getEada_type(),
				wfbu_id, UserInfo.getUsername(), "", cid, "", map);
		if (str[0].equals("1")) {
			state = "0";
			if (state == "0" || state.equals("0")) {
				String sql = ",eada_final=1";
				int tapr_id = Integer.parseInt(str[2]);
				int id = Integer.parseInt(str[3]);
				Object[] objs = { "2", m, "", sql };
				m.setEada_id(id);
				str = wfs.SkipToNext(objs, tapr_id, "系统", "", 0, "");
				if (str[0] == "0") {
					dal.deleteInfo(id, tapr_id);
				}
			} else {
				m.setEada_id(Integer.parseInt(str[3]));
				String sql = ",eada_final=0,eada_zg=" + m.getEada_zg()
						+ ",eada_bf=" + m.getEada_bf() + ",eada_dms="
						+ m.getEada_dms();
				int k = dal.EmArchiveUpdateData(m, "", sql);
				if (k > 0) {
					str[0] = "1";
				}
			}
		}
		return str;
	}

	// 删除调出数据
	public String[] delCheckOut(EmArchiveDatumModel m) {
		Object[] obj = { "1", m };
		WfBusinessService wfbs = new Archive_FileCheckOutImpl();
		WfOperateService wfs = new WfOperateImpl(wfbs);
		String[] str = new String[5];
		if (m.getEada_tapr_id() != null) {
			str = wfs.StopTask(obj, m.getEada_tapr_id(), "系统",
					UserInfo.getUsername() + "删除档案调出");
		}
		else
		{
			boolean flag=dal.delEmArchiveDatum(m.getEada_id());
			if(flag)
			{
				str[0]="1";
			}
		}
		return str;
	}

	// 获取员工姓名
	public EmbaseModel getEmbaseinfo(String str) {
		Embasedal emdal = new Embasedal();
		List<EmbaseModel> emlist = new ArrayList<EmbaseModel>();
		EmbaseModel model = new EmbaseModel();
		emlist = emdal.getembaList(str);
		if (!emlist.isEmpty()) {
			model = emlist.get(0);
		}
		return model;
	}

	// 新增档案材料归档的材料信息
	public int FilingAdd(EmDhFileModel m) {
		return dal.FilingAdd(m);
	}
}
