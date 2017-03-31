package bll.CoAgencies;

import java.util.List;

import dal.CoAgencies.CoAg_CompactSelectDal;
import dal.CoAgency.WtAgency_DisCitySelDal;

import Model.CoAgencyBaseModel;
import Model.CoAgencyCompactModel;
import Model.PubProCityModel;

public class CoAg_CompactSelectBll {
	private CoAg_CompactSelectDal dal = new CoAg_CompactSelectDal();

	// 查询委托合同信息
	public List<CoAgencyCompactModel> getWtCompactList(String str) {
		return dal.getWtCompactList(str);
	}

	// 查询委托合同信息
	public List<CoAgencyCompactModel> getWtCompactList(int cabc_id) {
		return dal.getWtCompactList(cabc_id);
	}

	// 拼接查询语句
	public List<CoAgencyCompactModel> getList(CoAgencyCompactModel model,
			String sqls) {
		String sql = "";
		if (model.getCoct_coagname() != null
				&& !model.getCoct_coagname().equals("")) {
			sql = sql + " and coct_coagname like '%" + model.getCoct_coagname()
					+ "%'";
		}
		if (model.getCoct_compactid() != null
				&& !model.getCoct_compactid().equals("")) {
			sql = sql + " and coct_compactid like '%"
					+ model.getCoct_compactid() + "%'";
		}
		if (model.getCoct_addname() != null
				&& !model.getCoct_addname().equals("")) {
			sql = sql + " and coct_addname='" + model.getCoct_addname() + "'";
		}
		if (model.getStatename() != null && !model.getStatename().equals("")) {
			sql = sql + " and coct_state=" + getStateId(model.getStatename());
		}
		if (model.getCoct_type() != null && !model.getCoct_type().equals("")) {
			sql = sql + " and coct_type='" + model.getCoct_type() + "'";
		}
		if (model.getCity() != null && !model.getCity().equals("")) {
			sql = sql
					+ " and coct_id in(select ctcy_coct_id from CoAgencyBase a,CoAgencyBaseCityRel b "
					+ "left join CoAgencyCompactCityRel d on b.cabc_id=d.ctcy_cabc_id,PubProCity c "
					+ "where a.coab_id=b.cabc_coab_id and b.cabc_ppc_id=c.id and cabc_state=1 "
					+ "and name like '%" + model.getCity() + "%')";
		}
		return getWtCompactList(sql + sqls);
	}

	// 把中国文状态变为数字
	private int getStateId(String statename) {
		int stateid = -1;
		if (statename != null) {
			if (statename.equals("待制作")) {
				stateid = 0;
			} else if (statename.equals("待审核")) {
				stateid = 1;
			} else if (statename.equals("待签回")) {
				stateid = 2;
			} else if (statename.equals("待归档")) {
				stateid = 3;
			} else if (statename.equals("已归档")) {
				stateid = 4;
			} else if (statename.equals("已终止")) {
				stateid = 5;
			} else if (statename.equals("已解约")) {
				stateid = 6;
			}
		}
		return stateid;
	}

	// 查询添加人
	public List<String> getAddname() {
		return dal.getAddname();
	}

	// 查询机构
	public List<CoAgencyBaseModel> getCoAgencyBaseList(String str) {
		return dal.getCoAgencyBaseList(str);
	}

	// 根据服务城市获取委托机构
	public List<CoAgencyBaseModel> getCoAgBaseListByCity(String city) {
		WtAgency_DisCitySelDal dd = new WtAgency_DisCitySelDal();
		return dd.getCoAgBaseListByCity(city);
	}

	// 查询机构的城市
	public List<PubProCityModel> getCoPubProCityList(String sql) {
		return dal.getCoPubProCityList(sql);
	}

	// 查询机构的城市
	public List<PubProCityModel> getCoPubProCityListById(Integer coab_id) {
		return getCoPubProCityList(" and cabc_coab_id=" + coab_id);
	}

	// 根据合同表id查询城市
	public List<PubProCityModel> getCoPubProCityListByCaId(Integer cact_id) {
		String sql = "and ctcy_coct_id=" + cact_id;
		return getCoPubProCityList(sql);
	}

	// 根据合同id获取合同信息
	public CoAgencyCompactModel getCoAgencyCompactModel(Integer coct_id) {
		CoAgencyCompactModel model = new CoAgencyCompactModel();
		List<CoAgencyCompactModel> list = getWtCompactList(" and coct_id="
				+ coct_id);
		if (list.size() > 0) {
			model = list.get(0);
		}
		return model;
	}

	// 查询机构的城市
	public List<PubProCityModel> getCityList(String sqlstr) {
		return dal.getCityList(sqlstr);
	}

}
