package bll.EmBodyCheck;

import java.util.List;

import org.zkoss.zul.ListModelList;

import Model.CoBaseModel;
import Model.CoBaseServePromiseModel;
import Model.EmBcItemGroupModel;
import Model.EmBcSetupAddressModel;
import Model.EmBcSetupModel;
import Model.EmBodyCheckCommitModel;
import Model.EmBodyCheckItemModel;
import Model.loginroleModel;
import dal.LoginDal;
import dal.CoServePromise.CoServePromiseSelectDal;
import dal.EmBodyCheck.EmBcInfo_OperatetDal;
import dal.EmBodyCheck.EmBcSetup_SelectDal;
import dal.EmBodyCheck.EmBc_CommitSelectDal;
import dal.EmBodyCheck.EmbcItem_SelectDal;

public class EmBc_CommitSelectBll {

	// 年度体检信息
	public List<EmBodyCheckCommitModel> getEmBodyCheckCommitInfo(
			EmBodyCheckCommitModel em) {
		EmBc_CommitSelectDal dal = new EmBc_CommitSelectDal();
		return dal.getEmBodyCheckCommitInfo(em, true);
	}

	// 查询机构名称+地址
	public List<EmBcSetupModel> getEmBcSetupname(String str) {
		EmBc_CommitSelectDal dal = new EmBc_CommitSelectDal();
		return dal.getEmBcSetupname(str);
	}

	// 查询体检组合
	public List<EmBcItemGroupModel> getEmBcGroupInfo(String str) {
		EmBc_CommitSelectDal dal = new EmBc_CommitSelectDal();
		return dal.getEmBcGroupInfo(str, false);
	}

	// 查询体检机构
	public List<EmBcSetupModel> getSUList(EmBcSetupModel ebsm) {

		EmBcSetup_SelectDal dal = new EmBcSetup_SelectDal();
		return dal.getSetUpList(ebsm);
	}

	// 查询体检机构地址
	public List<EmBcSetupAddressModel> getSAList(EmBcSetupAddressModel ebam) {
		EmBcSetup_SelectDal dal = new EmBcSetup_SelectDal();
		return dal.getAddressList(ebam);
	}

	// 查询公司列表
	public List<CoBaseModel> companylist(String company, String emp,
			String client, Integer hospital) {
		List<CoBaseModel> list = new ListModelList<>();
		EmbcItem_SelectDal dal = new EmbcItem_SelectDal();
		list = dal.getcompanylist(company, emp, client, hospital);
		return list;
	}

	// 查询所属月份列表
	public List<EmBodyCheckCommitModel> ownmonthlist() {
		List<EmBodyCheckCommitModel> list = new ListModelList<>();
		EmBc_CommitSelectDal dal = new EmBc_CommitSelectDal();
		list = dal.ownmonthlist();
		return list;
	}

	// 查询员工对应体检项目
	public List<EmBodyCheckItemModel> getbcItem(Integer gid, String sex,
			String marry, Integer hospital) {
		List<EmBodyCheckItemModel> list = new ListModelList<>();
		EmbcItem_SelectDal dal = new EmbcItem_SelectDal();
		list = dal.bcItem(gid, sex, marry, hospital);
		return list;
	}

	// 查询组合列表
	public List<EmBcItemGroupModel> cubeList(String company, Integer cid,
			String emp, String client, Integer hospital) {
		List<EmBcItemGroupModel> list = new ListModelList<>();
		List<EmBodyCheckItemModel> itemlist = new ListModelList<>();
		EmBcItemGroupModel eigm = new EmBcItemGroupModel();
		eigm.setCid(cid);
		eigm.setEbig_hospital(hospital);
		eigm.setCoba_shortname(company);
		eigm.setCoba_client(client);
		eigm.setEmba_name(emp);
		eigm.setEbig_state(1);
		EmBodyCheckItemModel em = new EmBodyCheckItemModel();
		em.setEbit_hospital(hospital);
		em.setEbit_package(1);
		em.setEbit_state(1);
		EmbcItem_SelectDal dal = new EmbcItem_SelectDal();
		if (cid != null) {
			list.addAll(dal.getGroupList(eigm));
		}

		itemlist = dal.getItemList(em);
		if (itemlist.size() > 0) {
			for (int i = 0; i < itemlist.size(); i++) {
				EmBcItemGroupModel egm = new EmBcItemGroupModel();
				egm.setEigl_ebit_id(itemlist.get(i).getEbit_id());
				egm.setEbig_name(itemlist.get(i).getEbit_name());
				list.add(list.size(), egm);
			}
		}
		return list;
	}

	// 查询公司体检组合及所属医院体检套餐
	public List<EmBcItemGroupModel> getGroupList(Integer hospital, Integer cid) {
		EmbcItem_SelectDal dal = new EmbcItem_SelectDal();
		List<EmBcItemGroupModel> list = new ListModelList<>();
		List<EmBodyCheckItemModel> itemlist = new ListModelList<>();
		EmBcItemGroupModel eigm = new EmBcItemGroupModel();
		eigm.setCid(cid);
		eigm.setEbig_hospital(hospital);
		EmBodyCheckItemModel em = new EmBodyCheckItemModel();
		em.setEbit_hospital(hospital);
		em.setEbit_package(1);
		list.addAll(dal.getGroupList(eigm));
		itemlist = dal.getItemList(em);
		if (itemlist.size() > 0) {
			for (int i = 0; i < itemlist.size(); i++) {
				EmBcItemGroupModel egm = new EmBcItemGroupModel();
				egm.setEigl_ebit_id(itemlist.get(i).getEbit_id());
				egm.setEbig_name(itemlist.get(i).getEbit_name());
				list.add(list.size(), egm);
			}
		}
		return list;
	}

	// 修改年度体检信息
	public Integer modCommitInfo(EmBodyCheckCommitModel em) {
		EmBcInfo_OperatetDal dal = new EmBcInfo_OperatetDal();
		Integer i = dal.embodycheckModYear(em);
		return i;
	}

	// 查询客服名单
	public List<loginroleModel> clientList(Integer userid) {
		List<loginroleModel> list = new ListModelList<>();
		LoginDal dal = new LoginDal();
		list = dal.getbcCLientList(userid);
		return list;
	}

	// 删除名单
	public Integer del(Integer id) {
		Integer i = 0;
		EmBcInfo_OperatetDal dal = new EmBcInfo_OperatetDal();
		EmBodyCheckCommitModel em = new EmBodyCheckCommitModel();
		em.setEbcc_id(id);
		em.setEbcc_deleteState(1);
		i = dal.embodycheckModYear(em);
		return i;
	}

	// 查询公司操作约定中是否已录入联系人信息
	public boolean linkInfo(Integer cid) {
		boolean b = false;
		CoBaseServePromiseModel m = new CoBaseServePromiseModel();
		CoServePromiseSelectDal dal = new CoServePromiseSelectDal();
		List<CoBaseServePromiseModel> list = dal.getlist(cid);
		if (list.size() > 0) {
			m = list.get(0);
			if (m.getCosp_bcrp_bclinkname() != null
					&& !m.getCosp_bcrp_bclinkname().equals("")
					&& m.getCosp_bcrp_caliname() != null
					&& !m.getCosp_bcrp_caliname().equals("")) {
				b = true;
			}
		}
		return b;
	}
}
