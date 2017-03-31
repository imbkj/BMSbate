package bll.Archives;

import Model.CoglistModel;
import Model.EmArchiveDatumModel;
import Model.EmArchiveSetupModel;
import Model.EmbaseModel;
import Util.UserInfo;

import impl.WorkflowCore.WfOperateImpl;

import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.zkoss.zul.ListModelList;

import service.WorkflowCore.WfBusinessService;
import service.WorkflowCore.WfOperateService;
import bll.Embase.EmBase_NotinAddImpl;

import dal.Archives.EmArchiveDatumDal;
import dal.Archives.EmArchiveSetupDal;
import dal.Embase.CoglistDal;
import dal.Embase.Embasedal;

public class Archive_FileImportBll {
	/**
	 * @Title: getEmbaseInfo
	 * @Description: TODO(查询员工信息)
	 * @param gid
	 * @return
	 * @return List<EmbaseModel> 返回类型
	 * @throws
	 */
	public List<EmbaseModel> getEmbaseInfo(Integer gid) {
		List<EmbaseModel> list = new ListModelList<EmbaseModel>();
		Embasedal dal = new Embasedal();
		try {
			list = dal.getEmBaseByGid(gid);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}

	public List<EmArchiveDatumModel> getealist(Integer id) {
		List<EmArchiveDatumModel> list = new ListModelList<>();
		EmArchiveDatumDal dal = new EmArchiveDatumDal();
		try {
			list = dal.getInfoById(id);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return list;
	}

	// 查询员工报价单是否选择的档案项目
	public List<CoglistModel> getItem(Integer gid) {

		Date d = new Date();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMM");
		Integer ownmonth = Integer.valueOf(sdf.format(d));
		CoglistDal dal = new CoglistDal();
		List<CoglistModel> list = dal.getitemlist(gid, ownmonth, "档案");

		return list;
	}

	/**
	 * @Title: getSetUp
	 * @Description: TODO(查询人才机构)
	 * @return
	 * @return List<EmArchiveSetupModel> 返回类型
	 * @throws
	 */
	public List<EmArchiveSetupModel> getSetUp() {
		List<EmArchiveSetupModel> list = new ListModelList<EmArchiveSetupModel>();
		EmArchiveSetupDal dal = new EmArchiveSetupDal();
		try {
			list = dal.getSetUpList();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}

	// 查询是否有调入未处理数据
	public boolean importData(Integer gid) {
		boolean b = false;
		EmArchiveDatumDal dal = new EmArchiveDatumDal();
		try {
			Integer i = dal.checkData(gid, 0);
			if (i > 0) {
				b = true;
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return b;
	}

	public Integer addFileImport(String name, Integer gid, String idcard,
			String place, String wtmode, String hj, String filedate,
			String rspaykind, String rsinvoice, String hjpaykind,
			String hjinvoice, String charge, String sf, String remark,
			String addname) {
		Integer i = 0;

		WfBusinessService wfbs = new Archive_FileImportImpl();
		WfOperateService wfs = new WfOperateImpl(wfbs);
		Object[] obj = { "新增调入", gid, idcard, place, wtmode, hj, filedate,
				rspaykind, rsinvoice, hjpaykind, hjinvoice, charge, sf, remark,
				addname };
		EmArchiveDatumDal dl = new EmArchiveDatumDal();
		Integer cid = 0;
		Integer c = dl.getCid(gid);
		Map<String, String> map = new HashMap<String, String>();

		if (c != null) {
			cid = c;
			map.put("cid", cid + "");
		}
		map.put("gid", gid + "");
		String[] str = wfs.AddTaskToNext(obj, "档案调入",
				name + "(" + gid.toString() + ")申请调入档案", 13, addname, "", cid,
				"", map);
		if (str[0].equals("1")) {
			i = Integer.valueOf(str[3]);

			Object[] obj2 = { "跳过",str[3] };
			wfs.SkipToNext(obj2, Integer.valueOf(str[2]),
					UserInfo.getUsername(), "", 0, "");

		}

		return i;
	}

	public Integer passData(EmArchiveDatumModel em) {
		Integer i = 0;
		WfBusinessService wfbs = new Archive_FileImportImpl();
		WfOperateService wfs = new WfOperateImpl(wfbs);
		Object[] obj = { "重新发送", em };
		String[] str = wfs.SkipToN(obj, em.getEada_tapr_id(), 3,
				UserInfo.getUsername(), "", em.getCid(), "");

		if (str[0].equals("1")) {
			i = 1;
		}
		return i;
	}
}
