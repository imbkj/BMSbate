package bll.CoAgency;

import java.util.List;
import impl.PubCityImpl;
import dal.CoAgency.CoAgencyBaseDal;

public class BaseInfo_CityDisBll {
	private PubCityImpl impl = new PubCityImpl();


	// 列表地区省份城市及机构数量
	public List<String[]> getCityBaseCount() throws Exception {
		CoAgencyBaseDal dal = new CoAgencyBaseDal();
		List<String[]> Base = dal.getCityBaseCount();
		return Base;
	}

	// 按地区省份城市查询城市省份地区及机构数量
	public List<String[]> getCityBaseCount(String region, String province,
			String city) throws Exception {
		CoAgencyBaseDal dal = new CoAgencyBaseDal();
		List<String[]> Base = null;
		if (!"".equals(city)) {
			Base = dal.getCityBaseCount("", "", city);
		} else if (!"".equals(province)) {
			Base = dal.getCityBaseCount("", province, city);
		} else if (!"".equals(region)) {
			Base = dal.getCityBaseCount(region, province, city);
		} else {
			Base = dal.getCityBaseCount();
		}
		return Base;
	}
}
