package bll.EmReg;

import java.util.ArrayList;
import java.util.List;

import dal.EmReg.EmReg_ListDal;
import dal.Embase.Embasedal;

import Model.CoOnlineRegisterInfoModel;
import Model.EmRegContactModel;
import Model.EmRegistrationInfoModel;
import Model.EmbaseModel;
import Model.HandoverPeopleModel;
import Model.PubCodeConversionModel;
import Model.PubFolkModel;
import Model.ResponsibilityBookModel;
import Model.WorkClassInfoModel;

public class EmReg_ListBll {

	// 获取员工列表
	public List<CoOnlineRegisterInfoModel> getEmbaseList() {
		return new EmReg_ListDal().getEmbaseList();
	}

	// 获取员工基本信息
	public EmRegistrationInfoModel getEmbaseInfo(Integer gid) {
		return new EmReg_ListDal().getEmbaseInfo(gid);
	}

	// 获取PubCodeConversion表的数据
	public List<PubCodeConversionModel> getPubCodeList(String str) {
		return new EmReg_ListDal().getPubCodeList(str);
	}

	// 获取民族列表
	public List<PubFolkModel> getFolkList() {
		return new EmReg_ListDal().getFolkList();
	}

	// 获取工种列表
	public List<WorkClassInfoModel> getWcinList() {
		return new EmReg_ListDal().getWcinList();
	}

	// 获取交接人
	public List<HandoverPeopleModel> getHandoverList() {
		return new EmReg_ListDal().getHandoverList();
	}

	// 获取员工就业登记申报列表
	public List<EmRegistrationInfoModel> getEmRegList(Integer flag, String str) {
		return new EmReg_ListDal().getEmRegList(flag, str);
	}

	// 获取状态列表
	public List<EmRegistrationInfoModel> getStateList(String str) {
		return new EmReg_ListDal().getStateList(str);
	}

	// 根据id查询终止原因
	public String getStopreson(Integer erin_id) {
		return new EmReg_ListDal().getStopreson(erin_id);
	}

	// 获取终止状态列表
	public List<EmRegistrationInfoModel> getStopStateList() {
		List<EmRegistrationInfoModel> list = new ArrayList<>();
		EmRegistrationInfoModel md = new EmRegistrationInfoModel();
		list.add(md);
		EmRegistrationInfoModel m = new EmRegistrationInfoModel();
		m.setErin_stop_state(0);
		m.setStop_statename("未终止");
		list.add(m);
		m = new EmRegistrationInfoModel();
		m.setErin_stop_state(1);
		m.setStop_statename("待终止");
		list.add(m);
		m = new EmRegistrationInfoModel();
		m.setErin_stop_state(2);
		m.setStop_statename("已终止");
		list.add(m);
		m = new EmRegistrationInfoModel();
		m.setErin_stop_state(3);
		m.setStop_statename("已解约");
		list.add(m);
		m = new EmRegistrationInfoModel();
		m.setErin_stop_state(4);
		m.setStop_statename("自动终止");
		list.add(m);
		return list;
	}

	// 获取状态详细列表
	public List<EmRegistrationInfoModel> getStateInfoList(String str) {
		return new EmReg_ListDal().getStateInfoList(str);
	}

	// 获取员工就业登记详情
	public EmRegistrationInfoModel getEmRegInfo(Integer erin_id, String str) {
		return new EmReg_ListDal().getEmRegInfo(erin_id, str);
	}

	// 根据GID获取员工信息,档案业务
	public EmbaseModel getEmBaseByGid(Integer gid) {
		EmbaseModel model = new EmbaseModel();
		List<EmbaseModel> list = new Embasedal().getEmBaseByGid(gid);
		if (list.size() > 0) {
			model = list.get(0);
		}
		return model;
	}

	// 获取多个员工就业登记详情列表
	public List<EmRegistrationInfoModel> getEmRegInfoList(String daids,
			String str) {
		return new EmReg_ListDal().getEmRegInfoList(daids, str);
	}

	// 获取状态变更记录列表
	public List<EmRegistrationInfoModel> getStateRelList(String str,
			Integer erin_id) {
		return new EmReg_ListDal().getStateRelList(str, erin_id);
	}

	// 根据gid获取就业登记信息
	public EmRegistrationInfoModel getInfo(Integer gid) {
		EmReg_ListDal dal = new EmReg_ListDal();
		return dal.getInfo(gid);
	}

	// 查询就业登记终止数据
	public List<EmRegistrationInfoModel> getEmRegList(String str) {
		EmReg_ListDal dal = new EmReg_ListDal();
		return dal.getEmRegList(str);
	}

	public List<EmRegistrationInfoModel> getStateTimeInfoList(Integer erin_id) {
		EmReg_ListDal dal = new EmReg_ListDal();
		return dal.getStateTimeInfoList(erin_id);
	}

	// 根据gid获取员工信息
	public EmbaseModel getEmbaseModel(Integer gid) {
		EmReg_ListDal dal = new EmReg_ListDal();
		return dal.getEmbaseModel(gid);
	}

	// 根据业务id和材料id查询是否已有交接材料
	public boolean ifUpdata(Integer dire_puzu_id, Integer tid) {
		EmReg_ListDal dal = new EmReg_ListDal();
		return dal.ifUpdata(dire_puzu_id, tid);
	}

	// 根据gid查询所是否已有居住证信息
	public Integer getEmResidencePermit(Integer gid) {
		EmReg_ListDal dal = new EmReg_ListDal();
		return dal.getEmResidencePermit(gid);
	}

	// 获取客服
	public List<String> getLogin() {
		EmReg_ListDal dal = new EmReg_ListDal();
		return dal.getLogin();
	}

	// 获取联系记录
	public List<EmRegContactModel> getEmRegContactList(Integer id, String table) {
		EmReg_ListDal dal = new EmReg_ListDal();
		return dal.getEmRegContactList(id, table);
	}

	// 获取联系记录
	public List<EmRegContactModel> getEmRegContactListByGid(Integer gid) {
		EmReg_ListDal dal = new EmReg_ListDal();
		return dal.getEmRegContactListByGid(gid);
	}

	// 根据cid查询公司的就业登记责任书信息
	public ResponsibilityBookModel getResponsibilityBook(Integer cid) {
		EmReg_ListDal dal = new EmReg_ListDal();
		return dal.getResponsibilityBook(cid);
	}
}
