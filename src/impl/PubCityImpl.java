/**
 * @Classname PubCityImpl
 * @ClassInfo 获取城市、省份数据实现类（继承接口service.PubCityService）
 * @author 李文洁
 * @Date 2013-9-13
 */
package impl;

import java.util.ArrayList;

import Model.PubProCityModel;
import Model.PubProvinceModel;
import Model.PubRegionModel;
import service.PubCityService;
import dal.PubCityDal;

public class PubCityImpl implements PubCityService {
	PubCityDal dal = new PubCityDal();

	// 获取地区表所有数据
	public ArrayList<PubRegionModel> getRegionAll() throws Exception {
		ArrayList<PubRegionModel> provinceAll = dal.getRegionAll();
		return provinceAll;
	}

	// 仅获取地区表名称
	public ArrayList<String> getRegionName() throws Exception {
		ArrayList<String> region = dal.getRegionName();
		return region;
	}

	// 获取省份表所有数据
	@Override
	public ArrayList<PubProvinceModel> getProvinceAll() throws Exception {
		ArrayList<PubProvinceModel> provinceAll = dal.getProvinceAll();
		return provinceAll;
	}

	// 仅获取省份名称
	@Override
	public ArrayList<String> getProvinceName() throws Exception {
		ArrayList<String> provinceName = dal.getProvinceName();
		return provinceName;
	}

	// 获取城市表所有数据
	@Override
	public ArrayList<PubProCityModel> getCityAll() throws Exception {
		ArrayList<PubProCityModel> cityAll = dal.getCityAll();
		return cityAll;
	}

	// 仅获取城市名称
	@Override
	public ArrayList<String> getCityName() throws Exception {
		ArrayList<String> cityName = dal.getCityName();
		return cityName;
	}

	// 获取城市名称及城市名称的首字母(返回值0：带首字母的城市名称，如：s深圳;1:城市名称)
	public ArrayList<String[]> getCityNameAddPy() throws Exception {
		ArrayList<String[]> cityName = dal.getCityNameAddPy();
		return cityName;
	}

	// 根据地区名称查询省份名称，返回列表
	@Override
	public ArrayList<String> getProvinceNameByRegion(String str)
			throws Exception {
		ArrayList<String> provinceName = dal.getProvinceNameByRegion(str);
		return provinceName;
	}

	// 根据城市查询省份名称,返回列表
	@Override
	public ArrayList<String> getProvinceName(String str) throws Exception {
		ArrayList<String> provinceName = dal.getProvinceName(str);
		return provinceName;
	}

	// 根据省份查询城市名称,返回列表
	@Override
	public ArrayList<String> getCityName(String str) throws Exception {
		ArrayList<String> cityName = dal.getCityName(str);
		return cityName;
	}
	
	//查询城市id
	public Integer getCityId(String name)
	{
		return dal.getCityId(name);
	}

}
