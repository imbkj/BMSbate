package bll.EmBenefit;

import java.util.List;

import org.zkoss.zul.ListModelList;

import dal.CoAgency.CoAgencyBaseDal;
import dal.EmBenefit.EmActy_SupplierOperateDal;
import dal.EmBenefit.EmActy_SupplierSelectDal;
import dal.EmBenefit.EmWelfareDal;

import Model.CoAgencyLinkmanModel;
import Model.EmActyProduceModel;
import Model.EmActyProducetypeModel;
import Model.EmActySupProductInfoModel;
import Model.EmActySupplierInfoModel;
import Model.EmWelfareModel;

public class EmBenefit_commitInfoBll {
	// 查询员工活动确认列表
	public List<EmWelfareModel> getList(EmWelfareModel ewfm) {
		List<EmWelfareModel> list = new ListModelList<>();
		EmWelfareDal dal = new EmWelfareDal();
		list = dal.getList(ewfm);

		return list;
	}

	// 查询供应商信息
	public List<EmActySupplierInfoModel> getSuppList(EmActySupplierInfoModel em) {
		List<EmActySupplierInfoModel> list = new ListModelList<>();
		EmActy_SupplierSelectDal dal = new EmActy_SupplierSelectDal();
		list = dal.getSIList(em);

		return list;

	}

	// 查询产品信息
	public List<EmActySupProductInfoModel> getSupProductList(
			EmActySupProductInfoModel em) {
		List<EmActySupProductInfoModel> list = new ListModelList<>();
		EmActy_SupplierSelectDal dal = new EmActy_SupplierSelectDal();
		list = dal.getSPList(em);
		return list;
	}

	// 根据供应商查询产品
	public List<EmActySupProductInfoModel> getProductList(Integer id) {
		List<EmActySupProductInfoModel> list = new ListModelList<>();
		EmActy_SupplierSelectDal dal = new EmActy_SupplierSelectDal();
		list = dal.getProductList(id);
		return list;
	}

	// 修改员工活动信息
	public Integer mod(EmWelfareModel ewfm) {
		Integer i = 0;
		EmWelfareDal dal = new EmWelfareDal();
		i = dal.mod(ewfm);

		return i;
	}

	// 读取公司(员工活动)联系人信息
	public List<CoAgencyLinkmanModel> getLinkInfo(Integer gid) {
		List<CoAgencyLinkmanModel> list = new ListModelList<>();
		CoAgencyBaseDal dal = new CoAgencyBaseDal();
		list = dal.getlinkInfo(gid);
		return list;
	}

	// 查询享受方式
	public List<String> getCopStandard() {
		EmWelfareDal dal = new EmWelfareDal();
		return dal.getCopStandard();
	}

	// 查询产品
	public List<EmActyProduceModel> getEmActyProduce() {
		EmWelfareDal dal = new EmWelfareDal();
		List<EmActyProduceModel> list = dal.getEmActyProduce();
		for (EmActyProduceModel m : list) {
			List<EmActyProducetypeModel> tlist = getEmActyProduceType(m
					.getProd_id());
			m.setPtypeList(tlist);
		}
		return list;
	}

	// 查询产品
	public List<EmActyProduceModel> getEmActyProduceList(int prod_supp_id) {
		EmWelfareDal dal = new EmWelfareDal();
		return dal.getEmActyProduceList(prod_supp_id);
	}

	// 查询产品
	public List<EmActyProduceModel> getEmActyProduceBySupId(int prod_supp_id) {
		EmWelfareDal dal = new EmWelfareDal();
		List<EmActyProduceModel> list = dal
				.getEmActyProduceBySupId(prod_supp_id);
		for (EmActyProduceModel m : list) {
			List<EmActyProducetypeModel> tlist = getEmActyProduceType(m
					.getProd_id());
			m.setPtypeList(tlist);
		}
		return list;
	}

	// 查询单个产品
	public EmActyProduceModel getEmActyProduceInfo(int prod_id) {
		EmWelfareDal dal = new EmWelfareDal();
		EmActyProduceModel m = dal.getEmActyProduceInfo(prod_id);
		List<EmActyProducetypeModel> tlist = getEmActyProduceType(m
				.getProd_id());
		m.setPtypeList(tlist);
		return m;
	}

	// 查询产品类型
	public List<EmActyProducetypeModel> getEmActyProduceType(int prod_id) {
		EmWelfareDal dal = new EmWelfareDal();
		return dal.getEmActyProduceType(prod_id);
	}

	// 查询单个产品类型
	public EmActyProducetypeModel getEmActyProduceTypeInfo(int prty_id) {
		EmWelfareDal dal = new EmWelfareDal();
		return dal.getEmActyProduceTypeInfo(prty_id);
	}

	// 更新福利信息
	public int editWm(int emwf_prod_id, int emwf_id) {
		EmWelfareDal dal = new EmWelfareDal();
		return dal.editWm(emwf_prod_id, emwf_id);
	}

}
