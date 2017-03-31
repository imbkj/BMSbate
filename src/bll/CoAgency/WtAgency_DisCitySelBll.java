package bll.CoAgency;

import impl.PubCityImpl;

import java.util.List;

import dal.CoAgency.WtAgency_DisCitySelDal;

import Model.CoAgencyBaseCityRelModel;
import Model.CoAgencyBaseModel;

public class WtAgency_DisCitySelBll {
	private WtAgency_DisCitySelDal dal = new WtAgency_DisCitySelDal();
	private PubCityImpl impl = new PubCityImpl();

	// 获取地区名称
	public List<String> getRegionName() throws Exception {
		List<String> region = impl.getRegionName();
		return region;
	}

	// 获取省份名称
	public List<String> getProvinceName() throws Exception {
		List<String> province = impl.getProvinceName();
		return province;
	}

	// 获取城市名称
	public List<String> getCityName() throws Exception {
		List<String> city = impl.getCityName();
		return city;
	}

	// 获取城市名称(带拼音首字母:s深圳)
	public List<String[]> getCityNamePY() throws Exception {
		List<String[]> city = impl.getCityNameAddPy();
		return city;
	}

	// 根据地区查找省份
	public List<String> searchProvinceName(String str) throws Exception {
		List<String> province = null;
		if (!"".equals(str)) {
			province = impl.getProvinceNameByRegion(str);
		} else {
			province = impl.getProvinceName();
		}
		return province;
	}

	// 根据省份查找城市
	public List<String> searchCityName(String str) throws Exception {
		List<String> city = null;
		if (!"".equals(str)) {
			city = impl.getCityName(str);
		} else {
			city = impl.getCityName();
		}
		return city;
	}

	// 获取所有城市及合作机构总数
	public List<CoAgencyBaseCityRelModel> getCoAgencyBaseCityRelList() {
		return dal.getCoAgencyBaseCityRelList();
	}

	// 根据服务城市获取委托机构
	public List<CoAgencyBaseModel> getCoAgBaseListByCity(String city) {
		return dal.getCoAgBaseListByCity(city);
	}

	// 根据服务城市获取未服务该城市的委托机构（用于分配服务城市界面）
	public List<CoAgencyBaseModel> getCoAgBaseListNotCity(String city) {
		return dal.getCoAgBaseListNotCity(city);
	}
}
