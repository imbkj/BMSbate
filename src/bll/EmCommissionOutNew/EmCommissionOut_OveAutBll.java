package bll.EmCommissionOutNew;

import java.sql.SQLException;
import java.util.List;

import Model.CoAgencyLinkmanModel;
import Model.EmCommissionOutChangeModel;
import Model.EmCommissionOutZYTModel;
import dal.EmCommissionOutNew.EmCommissionOut_AutDal;
import dal.EmCommissionOutNew.EmCommissionOut_OveAutDal;

public class EmCommissionOut_OveAutBll {
	// 获取委托城市
	public List<EmCommissionOutChangeModel> getcitylist() throws SQLException {
		EmCommissionOut_OveAutDal dal = new EmCommissionOut_OveAutDal();
		List<EmCommissionOutChangeModel> list = dal.getcitylist();
		return list;
	}

	// 获取委托类型
	public List<EmCommissionOutChangeModel> gettypelist(String city)
			throws SQLException {
		EmCommissionOut_OveAutDal dal = new EmCommissionOut_OveAutDal();
		String str = "";
		if (!city.equals("")) {
			str = " and wtss_city='" + city + "'";
		}
		List<EmCommissionOutChangeModel> list = dal.gettypelist(str);
		return list;
	}

	// 获取委托机构
	public List<EmCommissionOutChangeModel> getwtjglist(String city)
			throws SQLException {
		EmCommissionOut_OveAutDal dal = new EmCommissionOut_OveAutDal();
		String str = "";
		if (!city.equals("")) {
			str = " and wtss_city='" + city + "'";
		}
		List<EmCommissionOutChangeModel> list = dal.getwtjglist(str);
		return list;
	}

	// 获取客服名称
	public List<EmCommissionOutChangeModel> getclientlist(String city)
			throws SQLException {
		EmCommissionOut_OveAutDal dal = new EmCommissionOut_OveAutDal();
		String str = "";
		if (!city.equals("")) {
			str = " and wtss_city='" + city + "'";
		}
		List<EmCommissionOutChangeModel> list = dal.getclientlist(str);
		return list;
	}

	// 获取未确认数据
	public List<EmCommissionOutChangeModel> getwtout_aut_list(String cid,
			String gid, String name, String company, String type, String wtjg,
			String city, String client, String state) throws SQLException {
		EmCommissionOut_OveAutDal dal = new EmCommissionOut_OveAutDal();

		String str = "";
		if (!cid.equals("")) {
			str = " and a.cid=" + cid;
		}

		if (!gid.equals("")) {
			str = str + " and a.gid=" + gid;
		}

		if (!name.equals("")) {
			str = str + " and emba_name like '%" + name + "%'";
		}

		if (!company.equals("")) {
			str = str + " and coba_shortname like '%" + company + "%'";
		}

		if (!type.equals("")) {
			str = str + " and ecoc_addtype='" + type + "'";
		}

		if (!wtjg.equals("")) {
			str = str + " and coab_shortname='" + wtjg + "'";
		}

		if (!city.equals("")) {
			str = str + " and wtss_city='" + city + "'";
		}

		if (!client.equals("")) {
			str = str + " and coba_client='" + client + "'";
		}

		if (!state.equals("")) {
			str = str + " and ecoc_state='" + state + "'";
		} else {
			str = str + " and ecoc_state in (2,3)";
		}

		List<EmCommissionOutChangeModel> list = dal.getwtout_aut_list(str);
		return list;
	}

	// 获取新增excel数据
	public List<EmCommissionOutZYTModel> ci_excel(String id)
			throws SQLException {
		EmCommissionOut_OveAutDal dal = new EmCommissionOut_OveAutDal();
		List<EmCommissionOutZYTModel> list = dal.getci_excel(id);
		return list;
	}

	// 获取离职excel数据
	public List<EmCommissionOutZYTModel> ci_exceldim(String id)
			throws SQLException {
		EmCommissionOut_OveAutDal dal = new EmCommissionOut_OveAutDal();
		List<EmCommissionOutZYTModel> list = dal.getci_exceldim(id);
		return list;
	}

	// 获取联系人信息
	public List<CoAgencyLinkmanModel> getLinkManList(Integer cabc_id) {
		EmCommissionOut_OveAutDal dal = new EmCommissionOut_OveAutDal();
		return dal.getLinkManList(cabc_id);
	}

	// 获取委托数据统计
	public EmCommissionOutChangeModel getwt_state_list() throws SQLException {
		EmCommissionOut_OveAutDal dal = new EmCommissionOut_OveAutDal();
		return dal.getwt_state_list();
	}
}
