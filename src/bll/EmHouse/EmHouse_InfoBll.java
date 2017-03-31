package bll.EmHouse;

import java.sql.SQLException;
import java.util.List;

import org.zkoss.zul.ListModelList;

import dal.LoginDal;
import dal.EmHouse.EmHouseChangeDal;
import dal.EmHouse.EmHouseChangeGjjDal;
import dal.EmHouse.EmHouseDal;
import dal.EmHouse.EmHouseUpdateDal;
import dal.EmHouse.Emhouse_BjDal;
import dal.Embase.Embasedal;
import dal.SysMessage.SysMessage_EditDal;

import Model.EmHouseBJModel;
import Model.EmHouseChangeGJJModel;
import Model.EmHouseChangeModel;
import Model.EmHouseModel;
import Model.EmHouseUpdateModel;
import Model.EmbaseModel;
import Model.SysMessageModel;
import Model.loginroleModel;
import Util.IdcardUtil;
import Util.StringFormat;

public class EmHouse_InfoBll {

	public List<EmbaseModel> baseInfo(Integer gid) {
		List<EmbaseModel> list = new ListModelList<>();
		Embasedal dal = new Embasedal();
		list = dal.getEmBaseByGid(gid);
		return list;
	}

	/**
	 * @Title: getInfoById
	 * @Description: TODO(这里用一句话描述这个方法的作用)
	 * @param id
	 * @return
	 * @return List<EmHouseChangeModel> 返回类型
	 * @throws
	 */
	public List<EmHouseChangeModel> getInfoById(Integer id) {
		List<EmHouseChangeModel> list = new ListModelList<>();
		EmHouseChangeDal dal = new EmHouseChangeDal();

		try {
			list = dal.getInfoById(id);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return list;
	}

	public List<EmHouseChangeModel> getInfoByEmpInfo(EmHouseChangeModel em) {
		List<EmHouseChangeModel> list = new ListModelList<>();
		EmHouseChangeDal dal = new EmHouseChangeDal();

		list = dal.getChangeList(em, false, null, false, null,
				" ownmonth desc,emhc_addtime desc");
		return list;
	}

	// 公积金个人首页信息
	public List<EmHouseChangeModel> gethouseIndex(Integer gid, String houseid,
			String idcard) {
		List<EmHouseChangeModel> list = new ListModelList<>();
		if (houseid != null) {
			houseid = StringFormat.replaceSpace(houseid);
		}
		if (idcard != null) {
			idcard = StringFormat.replaceSpace(idcard);
			if (idcard.length() == 15) {
				idcard = IdcardUtil.conver15CardTo18(idcard);
			}
		}

		EmHouseUpdateDal dal = new EmHouseUpdateDal();
		list = dal.houseIndex(gid, houseid, idcard);
		return list;
	}

	// 在册信息
	public List<EmHouseUpdateModel> houseupdateInfo(Integer gid, Integer stop) {
		List<EmHouseUpdateModel> list = new ListModelList<>();
		EmHouseUpdateDal dal = new EmHouseUpdateDal();
		list = dal.houseupdateInfoByGid(gid, stop);
		return list;
	}

	// 交单变更信息
	public List<EmHouseChangeGJJModel> getChangeGjjInfo(Integer gid) {
		List<EmHouseChangeGJJModel> list = new ListModelList<>();
		EmHouseChangeGjjDal dal = new EmHouseChangeGjjDal();
		list = dal.getInfoByGid(gid);
		return list;
	}

	// 补缴信息
	public List<EmHouseBJModel> getbjList(Integer gid) {
		List<EmHouseBJModel> list = new ListModelList<>();
		Emhouse_BjDal dal = new Emhouse_BjDal();
		EmHouseBJModel em = new EmHouseBJModel();
		em.setGid(gid);
		list = dal.housebjList(em, false, null, false, null, "ownmonth desc",
				false);
		return list;
	}

	// 查询相同人员的补缴信息
	public List<EmHouseBJModel> getSamebjList(Integer gid) {
		List<EmHouseBJModel> list = new ListModelList<>();
		Emhouse_BjDal dal = new Emhouse_BjDal();
		EmHouseBJModel em = new EmHouseBJModel();
		em.setGid(gid);
		em.setSameInfo(true);
		list = dal.housebjList(em, false, null, false, null,
				"ownmonth desc,emhb_addtime desc", false);
		return list;
	}

	// 获取短信list表
	public List<SysMessageModel> getMsgList(String tbName) {
		List<SysMessageModel> list = new ListModelList<>();
		SysMessage_EditDal dal = new SysMessage_EditDal();
		// list=dal.getlist(tbName, username, userid);
		list = dal.getbacklist(tbName);
		return list;
	}

	// 刷新在册数据
	public void updateInfo(Integer gid) {
		EmHouseUpdateDal dal = new EmHouseUpdateDal();
		dal.updateData(gid);
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

	// 查询员工历史参保记录
	public List<EmHouseModel> historyList(Integer gid, String idcard,
			String houseid) {
		List<EmHouseModel> list = new ListModelList<>();
		EmHouseDal dal = new EmHouseDal();
		list = dal.houselist(gid, idcard, houseid);
		return list;

	}
}
