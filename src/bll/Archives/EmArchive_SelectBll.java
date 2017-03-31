package bll.Archives;

import java.util.List;

import org.zkoss.zul.ListModelList;

import dal.LoginDal;
import dal.Archives.EmArchiveLinkModelDal;
import dal.Archives.EmArchiveSetupDal;
import dal.Archives.EmArchive_SelectDal;
import dal.Embase.CoglistDal;
import dal.SysMessage.SysMessage_EditDal;

import Model.CoglistModel;
import Model.EmArchiveDatumModel;
import Model.EmArchiveLinkModel;
import Model.EmArchiveModel;
import Model.EmArchiveRemarkModel;
import Model.EmArchiveSetupModel;
import Model.SysMessageModel;
import Model.TaskProcessViewModel;
import Model.loginroleModel;

public class EmArchive_SelectBll {
	EmArchive_SelectDal dal = new EmArchive_SelectDal();

	// 根据传入的查询条件获取档案信息
	public List<EmArchiveModel> getEmArchiveInfo(String str) {
		return dal.getEmArchiveInfo(str);
	}

	// 获取短信list表
	public List<SysMessageModel> getMsgList(String tbName, String username,
			Integer userid) {
		List<SysMessageModel> list = new ListModelList<>();
		SysMessage_EditDal dal = new SysMessage_EditDal();
		list = dal.getlist(tbName, username, userid);
		return list;
	}

	public List<CoglistModel> coglistInfo(Integer gid) {
		List<CoglistModel> list = new ListModelList<>();
		CoglistDal dal = new CoglistDal();
		list = dal.coglistInfo(gid);
		return list;
	}

	public List<loginroleModel> getuserlist(String rolId) {
		List<loginroleModel> list = new ListModelList<>();
		LoginDal dal = new LoginDal();
		loginroleModel lm = new loginroleModel();
		lm.setLog_inure(1);
		lm.setRolId(rolId);
		list = dal.userInfo(lm, "log_id,log_name", true, "log_name");
		return list;
	}

	// 根据传入的查询条件获取档案备注信息
	public List<EmArchiveRemarkModel> getEmArchiveRemarkInfo(String str) {
		return dal.getEmArchiveRemarkInfo(str);
	}

	public List<EmArchiveDatumModel> getEmArchiveDatumInfo(String str) {
		return dal.getEmArchiveDatumInfo(str);
	}

	public EmArchiveDatumModel getEmArchiveDatumModel(String str) {
		EmArchiveDatumModel model = new EmArchiveDatumModel();
		List<EmArchiveDatumModel> list = dal.getEmArchiveDatumInfo(str);
		if (list.size() > 0) {
			model = list.get(0);
		}
		return model;
	}

	public List<TaskProcessViewModel> getLastId(String id) {
		return dal.getLastId(id);
	}

	public List<EmArchiveModel> getFidInfo(int gid) {
		return dal.getFidInfo(gid);
	}

	// 获取档案信息
	public EmArchiveModel getEmArchiveInfo(Integer gid) {
		return dal.getEmArchiveInfo(gid);
	}

	// 获取档案信息
	public Integer getIfEmArchiveInfo(Integer gid) {
		return dal.getIfEmArchiveInfo(gid);
	}

	// 查询员工的收费结束时间
	public String getstopdate(Integer eada_id) {
		return dal.getstopdate(eada_id);
	}

	// 获取客服信息
	public List<String> getLoginInfo() {
		return dal.getLoginInfo();
	}

	// 根据档案号查询该档案是否已有档案调出信息
	public Integer ifExistCheckOutInfo(String fid,Integer gid) {
		return dal.ifExistCheckOutInfo(fid,gid);
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
}
