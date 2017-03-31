/**
 * @Classname PubCityService
 * @ClassInfo 获取城市、省份数据接口（实现类impl.PubCityImpl）
 * @author 李文洁
 * @Date 2013-9-13
 */
package service;

import java.util.ArrayList;

import Model.PubProCityModel;
import Model.PubProvinceModel;
import Model.PubRegionModel;

public interface PubCityService {
	// 获取地区表所有数据
	ArrayList<PubRegionModel> getRegionAll() throws Exception;

	// 仅获取地区表名称
	ArrayList<String> getRegionName() throws Exception;

	// 获取省份表所有数据
	ArrayList<PubProvinceModel> getProvinceAll() throws Exception;

	// 仅获取省份名称
	ArrayList<String> getProvinceName() throws Exception;

	// 获取城市表所有数据
	ArrayList<PubProCityModel> getCityAll() throws Exception;

	// 仅获取城市名称
	ArrayList<String> getCityName() throws Exception;

	// 获取城市名称及城市名称的首字母(返回值0：带首字母的城市名称，如：s深圳;1:城市名称)
	ArrayList<String[]> getCityNameAddPy() throws Exception;

	// 根据地区名称查询省份名称，返回列表
	ArrayList<String> getProvinceNameByRegion(String str) throws Exception;

	// 根据城市查询省份名称,返回列表
	ArrayList<String> getProvinceName(String str) throws Exception;

	// 根据省份查询城市名称,返回列表
	ArrayList<String> getCityName(String str) throws Exception;
}
