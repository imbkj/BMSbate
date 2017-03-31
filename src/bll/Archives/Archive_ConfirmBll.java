package bll.Archives;

import impl.WorkflowCore.WfOperateImpl;

import java.util.List;

import org.zkoss.zul.ListModelList;

import service.WorkflowCore.WfBusinessService;
import service.WorkflowCore.WfOperateService;

import Model.CoglistModel;
import Model.EmArchiveDatumModel;
import Model.EmArchiveLinkModel;
import Model.EmArchiveSetupModel;
import Util.UserInfo;
import dal.Archives.EmArchiveDatumDal;
import dal.Archives.EmArchiveLinkModelDal;
import dal.Archives.EmArchiveSetupDal;
import dal.Embase.CoglistDal;

public class Archive_ConfirmBll {
	public List<EmArchiveDatumModel> getDatafileInfo(Integer id){
		List<EmArchiveDatumModel> list=  new ListModelList<>();
		EmArchiveDatumDal dal = new EmArchiveDatumDal();
		list=dal.getInfo(id);
		return list;
	}
	
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

	public List<CoglistModel> coglistInfo(Integer gid) {
		List<CoglistModel> list = new ListModelList<>();
		CoglistDal dal = new CoglistDal();
		list = dal.coglistInfo(gid);
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

	public Integer setConfirm(EmArchiveDatumModel em) {
		Integer i = 0;

		WfBusinessService wfbs = new Archive_ConfirmImpl();
		WfOperateService wfs = new WfOperateImpl(wfbs);
		Object[] obj = { em };

		String[] str = wfs.PassToNext(obj, em.getEada_tapr_id(),
				UserInfo.getUsername(), "", em.getCid(), "");

		if (str[0].equals("1"))
			i = Integer.valueOf(str[2]);
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
}
