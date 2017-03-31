package bll.Embase;

import java.util.List;

import org.zkoss.zul.ListModelList;

import dal.LoginDal;
import dal.EmBodyCheck.EmBcSetup_SelectDal;
import dal.EmHouse.EmHouseChangeDal;
import dal.EmHouse.EmHouseChangeGjjDal;
import dal.EmHouse.EmHouseUpdateDal;
import dal.EmHouse.Emhouse_BjDal;
import dal.Embase.EmbaseGdDal;

import Model.EmBcSetupAddressModel;
import Model.EmBcSetupModel;
import Model.EmHouseBJModel;
import Model.EmbaseGDModel;
import Model.loginroleModel;
import Util.UserInfo;

public class EmBase_gdBll {

	// 获取在职员工任务单列表
	public List<EmbaseGDModel> getinfoList(EmbaseGDModel em) {
		List<EmbaseGDModel> list = new ListModelList<>();
		EmbaseGdDal dal = new EmbaseGdDal();
		list = dal.getList(em, null, "");
		return list;
	}

	// 获取在职员工所属月份列表
	public List<EmbaseGDModel> getownmonthList() {
		List<EmbaseGDModel> list = new ListModelList<>();
		EmbaseGdDal dal = new EmbaseGdDal();
		list = dal.getList(null, "ownmonth", "desc");
		return list;
	}

	// 获取在职员工客服列表
	public List<EmbaseGDModel> getclientList() {
		List<EmbaseGDModel> list = new ListModelList<>();
		EmbaseGdDal dal = new EmbaseGdDal();
		list = dal.getList(null, "client", "");
		return list;
	}
	
	public List<loginroleModel> getclientList2() {
		List<loginroleModel> list = new ListModelList<>();
		LoginDal dal = new LoginDal();
		loginroleModel lm = new loginroleModel();
		lm.setLog_inure(1);
		list = dal.userInfo(lm,"log_name",true,"log_name");
		return list;
	}

	// 获取在职员工任务类型列表
	public List<EmbaseGDModel> gettypeList() {
		List<EmbaseGDModel> list = new ListModelList<>();
		EmbaseGdDal dal = new EmbaseGdDal();
		list = dal.getList(null, "type", "");
		return list;
	}

	public List<EmbaseGDModel> getListonjd(EmbaseGDModel m) {
		List<EmbaseGDModel> list = new ListModelList<>();
		EmbaseGdDal dal = new EmbaseGdDal();
		list = dal.getListonjd(m, null, "");
		return list;
	}

	public List<EmbaseGDModel> getListsbgjjkfback(EmbaseGDModel m) {
		List<EmbaseGDModel> list = new ListModelList<>();
		EmbaseGdDal dal = new EmbaseGdDal();
		list = dal.getListsbgjjkfback(m, null, "");
		return list;
	}

	// 获取在职员工任务类型列表
	public List<EmbaseGDModel> gettypeListsbgjjkfback() {
		List<EmbaseGDModel> list = new ListModelList<>();
		EmbaseGdDal dal = new EmbaseGdDal();
		list = dal.getListsbgjjkfback(null, "type", "");
		return list;
	}

	// 获取在职员工客服列表
	public List<EmbaseGDModel> getclientListsbgjjkfback() {
		List<EmbaseGDModel> list = new ListModelList<>();
		EmbaseGdDal dal = new EmbaseGdDal();
		list = dal.getListsbgjjkfback(null, "client", "");
		return list;
	}

	public List<EmbaseGDModel> getListldht(EmbaseGDModel m) {
		List<EmbaseGDModel> list = new ListModelList<>();
		EmbaseGdDal dal = new EmbaseGdDal();
		list = dal.getListldht(m, null, "");
		return list;
	}

	// 获取在职员工任务类型列表
	public List<EmbaseGDModel> gettypeListldht() {
		List<EmbaseGDModel> list = new ListModelList<>();
		EmbaseGdDal dal = new EmbaseGdDal();
		list = dal.getListldht(null, "type", "");
		return list;
	}

	// 获取添加人列表列表
	public List<EmbaseGDModel> getaddnameListldht() {
		List<EmbaseGDModel> list = new ListModelList<>();
		EmbaseGdDal dal = new EmbaseGdDal();
		list = dal.getListldht(null, "addname", "");
		return list;
	}

	// 获取在职员工任务类型列表
	public List<EmbaseGDModel> gettypeListnojd() {
		List<EmbaseGDModel> list = new ListModelList<>();
		EmbaseGdDal dal = new EmbaseGdDal();
		list = dal.getListonjd(null, "type", "");
		return list;
	}

	// 获取添加人列表列表
	public List<EmbaseGDModel> getaddnameListnojd() {
		List<EmbaseGDModel> list = new ListModelList<>();
		EmbaseGdDal dal = new EmbaseGdDal();
		list = dal.getListonjd(null, "addname", "");
		return list;
	}

	// 体检确认列表
	public List<EmbaseGDModel> getbclist(EmbaseGDModel m) {
		List<EmbaseGDModel> list = new ListModelList<>();
		EmbaseGdDal dal = new EmbaseGdDal();
		list = dal.getbcList(m, null, true);
		return list;

	}

	// 体检确认所屬月份列表
	public List<EmbaseGDModel> getbcOwnmonthlist() {
		List<EmbaseGDModel> list = new ListModelList<>();
		EmbaseGdDal dal = new EmbaseGdDal();
		list = dal.getbcList(null, "ownmonth", false);
		return list;

	}

	// 体检确认客服列表
	public List<EmbaseGDModel> getbcClientlist() {
		List<EmbaseGDModel> list = new ListModelList<>();
		EmbaseGdDal dal = new EmbaseGdDal();
		list = dal.getbcList(null, "client", true);
		return list;

	}

	// 体检确认医院列表
	public List<EmBcSetupModel> getbcHospitalList() {
		List<EmBcSetupModel> list = new ListModelList<>();
		EmBcSetup_SelectDal dal = new EmBcSetup_SelectDal();
		EmBcSetupModel m = new EmBcSetupModel();
		m.setEbcs_state(1);
		list = dal.getSetUpList(m);
		return list;

	}

	// 体检确认体检地址列表
	public List<EmBcSetupAddressModel> getbcAddressList(Integer hospital) {
		List<EmBcSetupAddressModel> list = new ListModelList<>();
		EmBcSetup_SelectDal dal = new EmBcSetup_SelectDal();
		EmBcSetupAddressModel m = new EmBcSetupAddressModel();
		m.setEbsa_ebcs_id(hospital);
		m.setEbsa_state(1);
		list = dal.getAddressList(m);
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

	// 更新公积金变更数据状态
	public Integer modHchange(Integer id, Integer gid) {
		EmHouseChangeDal dal = new EmHouseChangeDal();
		EmHouseUpdateDal dal2 = new EmHouseUpdateDal();
		Integer i = dal.updateState(id, 3, UserInfo.getUsername());
		dal2.updateData(gid);
		return i;
	}

	// 更新公积金补缴数据状态
	public Integer modHbj(Integer id) {
		Emhouse_BjDal dal = new Emhouse_BjDal();
		Integer i = dal.updateState(id, 3, UserInfo.getUsername());

		return i;
	}

	// 更新公积金变更数据状态
	public Integer modHgjj(Integer id) {
		EmHouseChangeGjjDal dal = new EmHouseChangeGjjDal();
		Integer i = dal.updateState(id, 3, UserInfo.getUsername());

		return i;
	}

	// 更新状态
	public boolean modinfo(EmbaseGDModel m) {
		Integer i = 0;
		EmbaseGdDal dal = new EmbaseGdDal();

		if (m.getEmgd_id() != null) {
			m.setEmgd_modname(UserInfo.getUsername());
			i = dal.mod(m, m.getEmgd_id());
		} else {
			m.setAddname(UserInfo.getUsername());
			i = dal.add(m);
			m.setEmgd_id(i);
		}
		if (i > 0) {
			return true;
		} else {
			return false;
		}
	}

	// 二次退回更新gd表状态
	public boolean modinfo(int typeid, int dataid, String typestring) {
		Integer i = 0;
		EmbaseGdDal dal = new EmbaseGdDal();

		i = dal.mod(typeid, dataid, typestring);

		if (i > 0) {
			return true;
		} else {
			return false;
		}
	}
}
