/*
 * 创建人：林少斌、李文洁
 * 创建时间：2013-9-12
 * 用途：委托机构基本信息查询Bll
 */

package bll.CoAgency;

import java.util.List;

import dal.CoAgency.CoAgencyBaseDal;
import Model.CoAgencyBaseCityRelViewModel;
import Model.CoAgencyBaseModel;
import service.CheckStringService;
import impl.CheckStringImpl;

public class CoAgencyBaseListBll {
	private CoAgencyBaseDal cabDal = new CoAgencyBaseDal();
	private List coagBaseList;

	// 查询所有机构
	public List getCoAgencyBaseAll() {
		coagBaseList = cabDal.getCoAgBaseList();
		return coagBaseList;
	}

	// 按条件查询机构
	public List searchCoAgencyBase(String province, String city, String name) {
		String sql = checkCoagBaseSearchName(province, city, name);
		coagBaseList = cabDal.getCoAgBaseList(sql);
		return coagBaseList;
	}

	// 根据机构id查询,返回机构信息
	public CoAgencyBaseModel getCoAgencyBase(int id) {
		CoAgencyBaseModel model = new CoAgencyBaseModel();
		return model;
	}

	// 查询机构省份
	public List getAgProvince() {
		List province = cabDal.getCoAgProvinceList();
		return province;
	}

	// 按机构省份查询城市
	public List getAgCity(String pro) {
		List city = cabDal.searchCoAgCityByPro(pro);
		return city;
	}

	// 输入机构ID，返回联系人信息
	public List getAgLinkman(int coab_id) {
		//List linkman = cabDal.getLinkmanForAg(coab_id);
		//return linkman;
		return null;
	}

	// 根据服务城市获取委托机构
	public List<CoAgencyBaseModel> getCoAgBaseByCity(String city) {
		return cabDal.getCoAgBaseByCity(city);
	}

	// 委托机构查询拼接SQL
	private String checkCoagBaseSearchName(String province, String city,
			String name) {
		CheckStringService ch = new CheckStringImpl();
		StringBuilder sql = new StringBuilder();
		if (province != "" && province != null) {
			province = province.trim();
			sql.append(" and coab_province='");
			sql.append(province);
			sql.append("' ");
		}
		if (city != "" && city != null) {
			city = city.trim();
			sql.append(" and coab_city='");
			sql.append(city);
			sql.append("' ");
		}
		if (name != "" && name != null) {
			name = name.trim();
			if (ch.isChinese(name)) {
				sql.append(" and coab_name like '%");
				sql.append(name);
				sql.append("%' ");
			} else if (ch.isNum(name)) {
				sql.append(" and (coab_id='");
				sql.append(name);
				sql.append("' or coab_name like '%");
				sql.append(name);
				sql.append("%')");
			} else if (ch.isLetter(name)) {
				sql.append(" and (coab_namespell like '%");
				sql.append(name);
				sql.append("%' or coab_name like '%");
				sql.append(name);
				sql.append("%')");
			} else {
				sql.append(" and (coab_namespell like '%");
				sql.append(name);
				sql.append("%' or  coab_name like '%");
				sql.append(name);
				sql.append("%')");
			}
		}
		return sql.toString();
	}

}
