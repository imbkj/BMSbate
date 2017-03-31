package bll.Archives;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.zkoss.zul.ListModelList;

import bll.Taskflow.Task_controlBll;

import Model.EmArchiveDatumModel;
import Model.EmArchiveLinkModel;
import Util.UserInfo;

import dal.Archives.EmArchiveDatumDal;
import dal.Archives.EmArchiveLinkModelDal;
import impl.WorkflowCore.WfOperateImpl;
import service.WorkflowCore.WfBusinessService;
import service.WorkflowCore.WfOperateService;

public class Archive_classifyBll {

	public List<EmArchiveDatumModel> getEmArchiveDatumInfo(Integer id) {
		List<EmArchiveDatumModel> list = new ListModelList<EmArchiveDatumModel>();
		EmArchiveDatumDal dal = new EmArchiveDatumDal();
		try {
			list = dal.getInfoById(id);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;

	}

	public Integer setFileType(Integer id, Integer tapr_id, String filetype,
			String filearea, String username, String eada_issdh) {
		Integer i = 0;
		List<EmArchiveDatumModel> list = new ListModelList<EmArchiveDatumModel>();
		EmArchiveDatumDal dal = new EmArchiveDatumDal();
		Integer cid = 0;
		Map<String, String> map = new HashMap<String, String>();
		try {
			list = dal.getInfoById(id);
			if (list.size() > 0) {
				cid = list.get(0).getCid();
				map.put("cid", cid + "");
				map.put("gid", list.get(0).getGid() + "");
			}
		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		WfBusinessService wfbs = new Archive_classifyImpl();
		WfOperateService wfs = new WfOperateImpl(wfbs);
		Object[] obj = { 1, filetype, filearea, id };
		// 选择档案类型
		String[] str = wfs.PassToNext(obj, tapr_id, username, "", cid, "");

		if (str[0].equals("1")) {
			i = Integer.valueOf(str[2]);

			try {

				// 转入中智流程
				if (filetype.equals("中智保管")) {
					Object[] o1 = { 3, id };
					String[] strs = wfs.AddTaskToNext(o1, "档案调入", list.get(0)
							.getEada_name()
							+ "("
							+ list.get(0).getGid().toString() + ")调入中智档案", 14,
							username, "", cid, "", map);
					if (eada_issdh.equals("否"))// 跳过开商调函步骤
					{
						if (strs[2] != null)// 如果任务单号不等于空
						{
							int new_tapr_id = Integer.parseInt(strs[2]);
							wfs.SkipToNext(obj, new_tapr_id, username,
									"跳过开商调函", 0, "");
						}
					}
					// 转入委托市内流程
				} else if (filetype.equals("委托人才") && filearea.equals("市内")) {
					Object[] o1 = { 4, id };
					wfs.AddTaskToNext(o1, "档案调入", list.get(0).getEada_name()
							+ "(" + list.get(0).getGid().toString()
							+ ")调入委托人才(市内)", 15, username, "", cid, "", map);
					// 转入委托市外流程
				} else if (filetype.equals("委托人才") && filearea.equals("市外")) {
					Object[] o1 = { 5, id };
					wfs.AddTaskToNext(o1, "档案调入", list.get(0).getEada_name()
							+ "(" + list.get(0).getGid().toString()
							+ ")调入委托人才(市外)", 16, username, "", cid, "", map);
				}

			} catch (Exception e) {
				e.printStackTrace();
			}
		}

		return i;
	}

	// 查询联系信息
	public List<EmArchiveLinkModel> getLinkInfo(Integer id) {
		List<EmArchiveLinkModel> list = new ListModelList<>();
		EmArchiveLinkModelDal dal = new EmArchiveLinkModelDal();
		try {
			list = dal.getInfoById(id);
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}

		return list;
	}

	// 查询联系信息
	public List<EmArchiveLinkModel> getInfoByGid(Integer gid) {
		EmArchiveLinkModelDal dal = new EmArchiveLinkModelDal();
		return dal.getInfoByGid(gid);
	}

	public Integer modData(String type, String area, String dataFinal,
			Integer id) {
		Integer i = 0;
		EmArchiveDatumModel m = new EmArchiveDatumModel();
		m.setEada_filetype(type);
		m.setEada_filearea(area);
		m.setEada_modname(UserInfo.getUsername());
		m.setEada_final("2");

		EmArchiveDatumDal dal = new EmArchiveDatumDal();
		i = dal.updateData(m, id);
		return i;
	}

	// 退回数据
	public Integer returnData(EmArchiveDatumModel em) {
		Integer i = 0;
		EmArchiveDatumModel m = new EmArchiveDatumModel();
		m.setEada_id(em.getEada_id());
		m.setEada_modname(UserInfo.getUsername());
		m.setEada_final("4");
		m.setEada_backcase(em.getEada_backcase());
		if (em.getEada_tapr_id() != null && em.getEada_tapr_id() > 0) {
			WfBusinessService wfbs = new Archive_classifyImpl();
			WfOperateService wfs = new WfOperateImpl(wfbs);

			Object[] o = { "人事退回数据", m };
			String s[] = wfs.ReturnToN(o, em.getEada_tapr_id(), 1, "系统");
			if (s[0].equals("1")) {
				i = 1;
				Task_controlBll tbll = new Task_controlBll();
				tbll.setOpName(Integer.valueOf(s[2]), em.getCoba_client());
			}
		} else {
			EmArchiveDatumDal dal = new EmArchiveDatumDal();
			i = dal.updateData(m, m.getEada_id());
		}

		return i;
	}
}
