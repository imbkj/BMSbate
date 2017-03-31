package bll.CoAgency;

import impl.PubCityImpl;

import java.util.List;

import Model.CoAgencyBaseCityRelModel;
import Model.CoAgencyBaseModel;
import Model.CoAgencyBaseServiceModel;
import Model.CoAgencyLinkmanModel;

import dal.CoAgency.BaseInfo_SelDal;

import service.PubCityService;

public class BaseInfo_SelBll {
	private BaseInfo_SelDal dal = new BaseInfo_SelDal();

	// 获取省份名称，返回列表
	public List<String> getProvinceName() throws Exception {
		PubCityService pcs = new PubCityImpl();
		List<String> province = pcs.getProvinceName();
		return province;
	}

	// 根据省份，查询城市名称，返回列表
	public List<String> getCityName(String province) throws Exception {
		PubCityService pcs = new PubCityImpl();
		List<String> city = pcs.getCityName(province);
		return city;
	}

	// 获取所有城市列表
	public List<String> getCityName() {
		List<String> city = null;
		try {
			PubCityService pcs = new PubCityImpl();
			city = pcs.getCityName();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return city;
	}

	// 获取全国项目部客服姓名，返回列表
	public List<String> getClient() {
		List<String> client = dal.getClient();
		return client;
	}

	// 获取所有机构
	public List<CoAgencyBaseModel> getAgency(int coas_type) {
		return dal.getAgency(coas_type);
	}

	// 获取委托机构列表(委托机构管理界面)
	public List<CoAgencyBaseModel> getWtAgencyList() {
		return dal.getWtAgencyList();
	}

	// 获取受托机构列表(受托机构管理界面)
	public List<CoAgencyBaseModel> getStAgencyList() {
		return dal.getStAgencyList();
	}

	// 根据coab_id获取服务城市列表
	public List<CoAgencyBaseCityRelModel> getServiceCityList(int coab_id) {
		return dal.getServiceCityList(coab_id);
	}

	// 根据coab_id获取机构基本信息
	public CoAgencyBaseModel getCoAgencyBaseModel(int coab_id) {
		return dal.getCoAgencyBaseModel(coab_id);
	}

	// 根据cabc_id获取机构基本信息
	public CoAgencyBaseModel getCoAgencyBaseModelByCabcId(int cabc_id) {
		return dal.getCoAgencyBaseModelByCabcId(cabc_id);
	}

	// 根据coab_name获取机构基本信息
	public CoAgencyBaseModel getCoAgencyBaseModel(String coab_name) {
		return dal.getCoAgencyBaseModel(coab_name);
	}

	// 根据coab_id获取机构服务约定信息
	public CoAgencyBaseServiceModel getCoAgencyBaseServiceModel(int coab_id,
			int coas_type) {
		return dal.getCoAgencyBaseServiceModel(coab_id, coas_type);
	}

	// 根据cabc_id获取机构服务约定信息
	public CoAgencyBaseServiceModel getCoAgencyBaseServiceModel(int cabc_id) {
		return dal.getCoAgencyBaseServiceModel(cabc_id);
	}

	// 根据coab_id获取机构联系人列表
	public List<CoAgencyLinkmanModel> getCoAgencyLinkmanList(int coab_id,
			int cabl_type) {
		return dal.getCoAgencyLinkmanList(coab_id, cabl_type);
	}

	// 根据cabc_id获取委托机构联系人列表
	public List<CoAgencyLinkmanModel> getWtCoAgencyLinkmanList(int cabc_id) {
		return dal.getWtCoAgencyLinkmanList(cabc_id);
	}

	// 根据coab_id获取委托机构可操作的城市名称列表
	public List<String> getCityDisList(int coab_id) {
		return dal.getCityDisList(coab_id);
	}

	// 根据cabc_id获取机构信息
	public CoAgencyBaseCityRelModel getCoabModel(int cabc_id) {
		return dal.getCoabModel(cabc_id);
	}

}
