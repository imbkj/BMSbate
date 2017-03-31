package bll.EmBodyCheck;

import java.math.BigDecimal;
import java.util.List;

import org.zkoss.zul.ListModelList;

import Model.CoBaseModel;
import Model.CoBaseServePromiseModel;
import Model.EmBcItemGroupModel;
import Model.EmBcSetupAddressModel;
import Model.EmBcSetupModel;
import Model.EmBodyCheckItemModel;
import Model.EmBodyCheckModel;
import Model.EmBodycheckCancelModel;
import Model.EmbaseModel;
import Model.embodycheckoperlogModel;
import Model.loginroleModel;
import dal.CoBase.CoBase_SelectDal;
import dal.CoServePromise.CoServePromiseSelectDal;
import dal.EmBodyCheck.EmBcInfo_SelectDal;
import dal.EmBodyCheck.EmBcSetup_SelectDal;
import dal.EmBodyCheck.EmbcItem_SelectDal;
import dal.SystemControl.UserListDal;

public class EmBcInfo_SelectBll {

	// 查询员工体检信息
	public List<EmBodyCheckModel> getEmBodyCheckInfo(String str) {
		EmBcInfo_SelectDal dal = new EmBcInfo_SelectDal();
		return dal.getEmBodyCheckInfo(str);
	}

	// 查询员工是否已婚
	public boolean getmarry(Integer gid) {
		boolean b = false;
		EmBcInfo_SelectDal dal = new EmBcInfo_SelectDal();
		List<EmBodyCheckModel> list = dal.getmarry(gid);
		if (list.size() > 0) {
			b = true;
		}
		return b;
	}

	// 查询员工体检列表
	public List<EmBodyCheckModel> getembodycheckList(EmBodyCheckModel em) {
		List<EmBodyCheckModel> list = new ListModelList<>();
		EmBcInfo_SelectDal dal = new EmBcInfo_SelectDal();
		list = dal.getEmBodyCheckList(em);
		return list;
	}

	// 获取单位员工信息
	public List<EmbaseModel> getEmBaseInfo(String str) {
		EmBcInfo_SelectDal dal = new EmBcInfo_SelectDal();
		return dal.getEmBaseInfo(str);
	}

	public List<loginroleModel> getClientList(loginroleModel lm) {
		List<loginroleModel> list = new ListModelList<>();
		UserListDal dal = new UserListDal();
		list = dal.getClientList(lm);
		return list;
	}

	// 获取体检机构信息
	public List<EmBcSetupModel> getSetupList(EmBcSetupModel ebsm) {
		List<EmBcSetupModel> list = new ListModelList<>();
		EmBcSetup_SelectDal dal = new EmBcSetup_SelectDal();
		list = dal.getSetUpList(ebsm);
		return list;
	}

	// 获取公司列表
	public List<CoBaseModel> getCobaseList(CoBaseModel cbm) {
		List<CoBaseModel> list = new ListModelList<>();
		CoBase_SelectDal dal = new CoBase_SelectDal();
		list = dal.getcompanyList(cbm.getCoba_client());

		return list;
	}

	// 查询公司列表(体检)
	public List<CoBaseModel> getcompanyEmbodyList(CoBaseModel cbm) {
		List<CoBaseModel> list = new ListModelList<>();
		CoBase_SelectDal dal = new CoBase_SelectDal();
		list = dal.getcompanyEmbodyList(cbm.getCoba_client());

		return list;
	}

	// 获取体检地址
	public List<EmBcSetupAddressModel> getSetUpAddress(
			EmBcSetupAddressModel ebam) {
		List<EmBcSetupAddressModel> list = new ListModelList<>();
		EmBcSetup_SelectDal dal = new EmBcSetup_SelectDal();
		list = dal.getAddressList(ebam);
		return list;
	}

	// 获取体检组合
	public List<EmBcItemGroupModel> getItemGroup(EmBcItemGroupModel eigm) {
		List<EmBcItemGroupModel> list = new ListModelList<>();
		EmbcItem_SelectDal dal = new EmbcItem_SelectDal();
		list = dal.getGroupList(eigm);
		return list;
	}

	// 获取体检项目
	public List<EmBodyCheckItemModel> getItemList(EmBodyCheckItemModel ebcm) {
		List<EmBodyCheckItemModel> list = new ListModelList<>();
		EmbcItem_SelectDal dal = new EmbcItem_SelectDal();

		list = dal.getItemList(ebcm);
		return list;
	}

	// 导出数据时替换体检项目名称
	public String getItemList(String itemNums) {
		String name = "";
		List<EmBodyCheckItemModel> list = new ListModelList<>();
		EmbcItem_SelectDal dal = new EmbcItem_SelectDal();
		list = dal.getItemList(itemNums);
		if (list.size() > 0) {
			for (EmBodyCheckItemModel m : list) {
				name = "," + m.getEbit_name();
			}
		}
		if (name.length() > 0) {
			name = name.substring(1);
		}

		return name;
	}

	// 获取体检项目金额合计
	public BigDecimal[] getItemTotal(String itemId) {
		BigDecimal[] bd = new BigDecimal[2];
		EmbcItem_SelectDal dal = new EmbcItem_SelectDal();
		bd = dal.getfee(itemId);
		return bd;
	}

	// 获取取消信息
	public List<EmBodycheckCancelModel> getEmBodycheckCancelList(String str) {
		EmBcInfo_SelectDal dal = new EmBcInfo_SelectDal();
		return dal.getEmBodycheckCancelList(str);
	}

	// 获取体检组合中的体检项目
	public List<EmBodyCheckItemModel> getGroupItem(Integer id) {
		EmbcItem_SelectDal dal = new EmbcItem_SelectDal();
		EmBodyCheckItemModel em = new EmBodyCheckItemModel();
		em.setEigl_ebig_id(id);
		return dal.getEbItemList(em);
	}

	// 获取体检组合中的体检项目
	public List<EmBodyCheckItemModel> getGroupItemList(EmBodyCheckItemModel em) {
		EmbcItem_SelectDal dal = new EmbcItem_SelectDal();
		return dal.getEbItemList(em);
	}

	// 根据embodycheck表id获取取消信息
	public EmBodycheckCancelModel getEmBodycheckCancelModel(Integer ebcl_id) {
		EmBodycheckCancelModel model = new EmBodycheckCancelModel();
		List<EmBodycheckCancelModel> list = getEmBodycheckCancelList(" and emca_ebcl_id="
				+ ebcl_id);
		if (list.size() > 0) {
			model = list.get(0);
		}
		return model;
	}

	// 导出联系医院名单时查询
	public List<EmBodyCheckModel> getEmBodyCheck(String str) {
		EmBcInfo_SelectDal dal = new EmBcInfo_SelectDal();
		return dal.getEmBodyCheck(str);
	}

	// 根据身份证号查询员工是否已有保健号
	public EmBodyCheckModel isHasEmbcId(String str) {
		EmBcInfo_SelectDal dal = new EmBcInfo_SelectDal();
		return dal.isHasEmbcId(str);
	}

	// 检测状态是否一样
	public boolean ifStateSame(String idstr) {
		EmBcInfo_SelectDal dal = new EmBcInfo_SelectDal();
		return dal.ifStateSame(idstr);
	}

	// 查询状态id
	public Integer getStateId(String idstr) {
		EmBcInfo_SelectDal dal = new EmBcInfo_SelectDal();
		return dal.getStateId(idstr);
	}
	
	//根据身份证查询体检中数据
	public List<EmBodyCheckModel> getBCList(String idcard,String bcid){
		EmBcInfo_SelectDal dal = new EmBcInfo_SelectDal();
		return dal.getBcList(idcard,bcid);
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

	// 员工员工是否有完成体检信息
	public String[] inureBc(String idcard) {
		String[] s = new String[2];
		EmBcInfo_SelectDal dal = new EmBcInfo_SelectDal();
		List<EmBodyCheckModel> list = dal.getInureBC(idcard);
		if (list.size() > 0) {
			s[0] = "1";
			s[1] = "已有体检信息:" + list.get(0).getEmbc_shortname() + ","
					+ list.get(0).getEmbc_name() + "(" + list.get(0).getCid()
					+ "," + list.get(0).getGid() + ","
					+ list.get(0).getEmbc_idcard() + ")"
					+ list.get(0).getEbcs_hospital() + "("
					+ list.get(0).getEbsa_address() + ")["
					+ list.get(0).getBookdate() + "]"
					+ list.get(0).getEmbc_statebname();
		} else {
			s[0] = "0";
		}
		return s;
	}

	public EmBcItemGroupModel getEmBcItemGroup(String group_id) {
		EmBcInfo_SelectDal dal = new EmBcInfo_SelectDal();
		return dal.getEmBcItemGroup(group_id);
	}
	
	public EmBcItemGroupModel getEmBcItemGroupList(String group_id) {
		EmBcInfo_SelectDal dal = new EmBcInfo_SelectDal();
		return dal.getgrouplist(group_id);
	}
	

	public boolean getEmBcCancel(Integer ebcl_id) {
		EmBcInfo_SelectDal dal = new EmBcInfo_SelectDal();
		return dal.getEmBcCancel(ebcl_id);
	}

	// 查询是否是已体检状态
	public Integer isSameChecked(String strid) {
		EmBcInfo_SelectDal dal = new EmBcInfo_SelectDal();
		return dal.isSameChecked(strid);
	}

	// 查询操作记录
	public List<embodycheckoperlogModel> getEmBodyCheckLog(int ebcl_id) {
		EmBcInfo_SelectDal dal = new EmBcInfo_SelectDal();
		return dal.getEmBodyCheckLog(ebcl_id);
	}

}
