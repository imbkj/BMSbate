package bll.EmSheBaocard;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import dal.CoSocialInsurance.CoSocialInsurance_ListDal;
import dal.EmSheBaocard.EmShebaoCardInfoSelectDal;

import Model.CoShebaoModel;
import Model.CoshebaobankModel;
import Model.EmSheBaoChangeModel;
import Model.EmShebaoCardInfoModel;
import Model.EmShebaoUpdateModel;
import Model.EmbaseModel;
import Model.EmshebaoCardBankDataInfoModel;
import Model.PubBankModel;
import Model.PubCodeConversionModel;

public class EmShebaoCardInfoSelectBll {
	private EmShebaoCardInfoSelectDal dal = new EmShebaoCardInfoSelectDal();

	// 查询员工信息
	public List<EmbaseModel> getEmbaseInfoById(String gid) {
		List<EmbaseModel> list = new ArrayList<EmbaseModel>();
		try {
			list = dal.getEmbaseInfoByGid(gid);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return list;
	}

	// 查询社保卡信息
	public List<EmShebaoCardInfoModel> getEmShebaoCardInfoList(String str) {
		return dal.getEmShebaoCardInfoList(str);
	}
	
	// 查询社保卡信息
	public EmShebaoCardInfoModel getEmShebaoCardInfo(String str) {
		List<EmShebaoCardInfoModel> list=getEmShebaoCardInfoList(str);
		EmShebaoCardInfoModel m=new EmShebaoCardInfoModel();
		if(list.size()>0)
		{
			m=list.get(0);
		}
		return m;
	}

	// 从PubCodeConversion表查询信息
	public List<PubCodeConversionModel> getPubCodeConversionList(
			Integer pucl_id, String pcco_name) {
		return dal.getPubCodeConversionList(pucl_id, pcco_name);
	}

	// 查询民族列表
	public List<PubCodeConversionModel> getFolkList(String str) {
		return dal.getFolkList(str);
	}

	// 查询省列表
	public List<PubCodeConversionModel> getPubProvinceList(String str) {
		return dal.getPubProvinceList(str);
	}

	// 查询城市列表
	public List<PubCodeConversionModel> getCityList(String str) {
		return dal.getCityList(str);
	}

	// 根据状态名称获取状态id
	public Integer getState(String cdst_statename) {
		Integer k = 0;
		List<EmShebaoCardInfoModel> list = getStateList(" and cdst_statename='"
				+ cdst_statename + "'");
		if (list.size() > 1) {
			k = list.get(1).getCdst_id();
		}
		return k;
	}

	// 根据状态名称获取状态id
	public List<EmShebaoCardInfoModel> getStateList(String str) {
		return dal.getStateList(str);
	}

	// 查询员工信息列表
	public List<EmbaseModel> getEmbaseList(String str) {
		List<EmbaseModel> list = new ArrayList<EmbaseModel>();
		list = dal.getEmbaseList(str);
		return list;
	}

	// 查询
	public List<EmbaseModel> getEmbaseList(EmbaseModel model) {
		String sql = "";
		if (model.getGid() != null && !model.getGid().equals("")) {
			sql = sql + " and a.gid=" + model.getGid();
		}

		if (model.getCid() != null && !model.getCid().equals("")) {
			sql = sql + " and a.cid=" + model.getCid();
		}
		if (model.getEmba_name() != null && !model.getEmba_name().equals("")) {
			sql = sql + " and emba_name='" + model.getEmba_name() + "'";
		}
		if (model.getEmba_idcard() != null
				&& !model.getEmba_idcard().equals("")) {
			sql = sql + " and emba_idcard='" + model.getEmba_idcard() + "'";
		}
		if (model.getEmba_computerid() != null
				&& !model.getEmba_computerid().equals("")) {
			sql = sql + " and esiu_computerid='" + model.getEmba_computerid()
					+ "'";
		}
		if (model.getCoba_shortname() != null
				&& !model.getCoba_shortname().equals("")) {
			sql = sql + " and coba_shortname='" + model.getCoba_shortname()
					+ "'";
		}
		if (model.getCoba_client() != null
				&& !model.getCoba_client().equals("")) {
			sql = sql + " and coba_client='" + model.getCoba_client() + "'";
		}
		//sql = sql + " and esiu_computerid is not null";
		return getEmbaseList(sql);
	}

	// 获取客服
	public List<String> getClientList() {
		return dal.getClientList();
	}

	// 根据cid查询银行
	public CoShebaoModel getCoshebaoInfo(Integer cid) {
		return dal.getCoshebaoInfo(cid);
	}

	// 根据开户代码查询银行
	public CoShebaoModel getCoshebaoBankInfo(String cosb_sorid) {
		return dal.getCoshebaoBankInfo(cosb_sorid);
	}

	// 获取制卡银行信息
	public List<PubBankModel> getBankList(String str) {
		CoSocialInsurance_ListDal dal = new CoSocialInsurance_ListDal();
		return dal.getBankList(str);
	}

	// 根据银行名称获取银行材料id
	public PubBankModel getBankModel(String bankname) {
		PubBankModel model = new PubBankModel();
		if (bankname != null) {
			List<PubBankModel> list = getBankList(" and bank_name like '%"
					+ bankname + "%'");
			if (list.size() > 0) {
				model = list.get(0);
			}
		}
		return model;
	}

	// 查询银行
	public List<CoshebaobankModel> getBankInfoList(String str) {
		return dal.getBankInfoList(str);
	}

	// 查询材料
	public List<EmshebaoCardBankDataInfoModel> getDataList(String str) {
		return dal.getDataList(str);
	}

	// 根据银行名称获取银行材料id
	public CoshebaobankModel getBankInfoModel(String bankname) {
		CoshebaobankModel model = new CoshebaobankModel();
		if (bankname != null) {
			List<CoshebaobankModel> list = getBankInfoList(" and bank_name like '%"
					+ bankname + "%'");
			if (list.size() > 0) {
				model = list.get(0);
			}
		}
		return model;
	}

	// 根据银行id把材料查询出来
	public List<CoshebaobankModel> getBankDataInfoList(String str) {
		List<CoshebaobankModel> list = getBankInfoList(str);
		for (CoshebaobankModel m : list) {
			List<EmshebaoCardBankDataInfoModel> datalist = getDataList(" and data_bank_id="
					+ m.getBank_id());
			m.setList(datalist);
		}
		return list;
	}

	// 根据gid查询员工是否有社保信息
	public EmSheBaoChangeModel getshebaoindo(Integer gid) {
		return dal.getshebaoindo(gid);
	}

	public boolean ifExistShebaoInfo(Integer gid) {
		return dal.ifExistShebaoInfo(gid);
	}

	public EmShebaoUpdateModel ShebaoInfo(Integer gid) {
		return dal.ShebaoInfo(gid);
	}

	// 查询银行支行
	public List<String> getBankBranchInfoList() {
		return dal.getBankBranchInfoList();
	}

	public boolean ifSameClass(String idstr) {
		return dal.ifSameClass(idstr);
	}

	// 获取社保卡所有添加人
	public List<String> getAddList() {
		return dal.getAddList();
	}

	// 根据身份证查询员工信息
	public EmbaseModel getEmbaseInfoList(EmbaseModel m) {
		return dal.getEmbaseInfoList(m);
	}

	// 获取客服
	public List<String> getClientsList() {
		return dal.getClientsList();
	}
}
