package bll.CoProduct;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.zkoss.zul.ListModelList;

import dal.CoProduct.cpd_List1Dal;

import Model.CoAgencyBaseModel;
import Model.CoProductModel;
import Model.CopCompactModel;

public class cpd_ListBll {

	// 查询服务产品
	public List<CoProductModel> getcoproduct(CoProductModel cop,
			String timeformat, String timetype) throws SQLException {
		List<CoProductModel> list = new ListModelList<CoProductModel>();
		try {
			cpd_List1Dal dal = new cpd_List1Dal();
			String copr_name = cop.getCopr_name() == null ? "" : cop
					.getCopr_name();
			String cpac_name = cop.getCpac_name() == null ? "" : cop
					.getCpac_name();
			String copc_name = cop.getCopc_name() == null ? "" : cop
					.getCopc_name();
			String copr_addname = cop.getCopr_addname() == null ? "" : cop
					.getCopr_addname();
			String copr_type = cop.getCopr_type() == null ? "" : cop
					.getCopr_type();
			String coab_name = cop.getCoab_name() == null ? "" : cop
					.getCoab_name();
			String statename = cop.getStatename() == null ? "" : cop
					.getStatename();

			Integer id = cop.getId();
			String str = "";
			if (!copr_name.isEmpty()) {
				str += " and copr_name like '%" + copr_name + "%'";
			}
			if (!cpac_name.isEmpty()) {
				String[] str1 = cpac_name.split(",");
				str += " and cpac_name in(";
				for (int i = 0; i < str1.length; i++) {
					if (i == str1.length - 1) {
						str += "'" + str1[i] + "'";
					} else {
						str += "'" + str1[i] + "',";
					}
				}
				str += ")";
			}
			if (!copc_name.isEmpty()) {
				String[] str1 = copc_name.split(",");
				str += " and copc_name in(";
				for (int i = 0; i < str1.length; i++) {
					if (i == str1.length - 1) {
						str += "'" + str1[i] + "'";
					} else {
						str += "'" + str1[i] + "',";
					}
				}
				str += ")";
			}
			if (!statename.isEmpty()) {
				
				str += " and statename ='"+statename+"'";
				 
			}
			if (!copr_addname.isEmpty()) {

				str += " and copr_addname='" + copr_addname + "'";

			}

			if (!coab_name.isEmpty()) {

				str += " and coab_name='" + coab_name + "'";

			}

			if (id != null) {
				str += " and id=" + id;
			}
			if (!copr_type.isEmpty()) {
				str += " and copr_type='" + copr_type + "'";
			}
			list = dal.getcoproduct(str);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}

	public List<CoProductModel> getcoproduct(String str) throws SQLException {
		cpd_List1Dal dal = new cpd_List1Dal();
		return dal.getcoproduct(str);
	}

	// 获取添加人列表
	public List<String> getaddnameList() throws SQLException {
		List<String> list = new ListModelList<String>();
		cpd_List1Dal dal = new cpd_List1Dal();
		list = dal.getaddnameList();
		return list;
	}

	// 根据copr_id获取享受方式
	public List getStardBycoprid(int copr_id) throws SQLException {
		List list = new ListModelList();
		cpd_List1Dal dal = new cpd_List1Dal();
		list = dal.getStardBycoprid(copr_id);
		return list;
	}

	// 根据copr_id获取适用地
	public List getCityBycoprid(int copr_id) throws SQLException {
		List list = new ListModelList();
		cpd_List1Dal dal = new cpd_List1Dal();
		list = dal.getCityBycoprid(copr_id);
		return list;
	}

	// 根据copr_id和city获取收费单位和默认金额
	public List<CoProductModel> getEclassBycoprid(int copr_id, String city)
			throws SQLException {
		List<CoProductModel> list = new ArrayList<CoProductModel>();
		cpd_List1Dal dal = new cpd_List1Dal();
		list = dal.getEclassBycoprid(copr_id, city);
		return list;
	}

	// 根据copr_id获取收费单位和默认金额等信息列表
	public List<CoProductModel> getEclassBycoprid(int copr_id)
			throws SQLException {
		List<CoProductModel> list = new ArrayList<CoProductModel>();
		cpd_List1Dal dal = new cpd_List1Dal();
		list = dal.getEclassBycoprid(copr_id);
		return list;
	}

	// 根据copr_id,city,cpfc_id获取单条默认金额
	public List<CoProductModel> getSingleEclassBycoprid(int copr_id, int cpfc_id)
			throws SQLException {
		List<CoProductModel> list = new ArrayList<CoProductModel>();
		cpd_List1Dal dal = new cpd_List1Dal();
		list = dal.getSingleEclassBycoprid(copr_id, cpfc_id);
		return list;
	}

	// 删除服务产品
	public int delete(int copr_id) throws SQLException {
		int row = 0;
		cpd_List1Dal dal = new cpd_List1Dal();
		row = dal.delete(copr_id);
		return row;
	}
	
	// 删除服务产品
	public int redelete(int copr_id) throws SQLException {
		int row = 0;
		cpd_List1Dal dal = new cpd_List1Dal();
		row = dal.redelete(copr_id,copr_id);
		return row;
	}


	// 修改服务产品
	public CoProductModel cpdmod(CoProductModel cpmol) throws SQLException {
		CoProductModel cpmol1 = new CoProductModel();
		cpd_List1Dal dal = new cpd_List1Dal();
		cpmol1 = dal.cpdmod(cpmol);
		return cpmol1;
	}
	
	//修改合同和产品关联状态
	public Integer updateCopcompact(Integer id,Integer state){
		int i = 0;
		cpd_List1Dal dal = new cpd_List1Dal();
		i=dal.updateCopcompact(id, state);
		return i;
	}

	// 根据copr_id删除所有收费单位和默认金额
	public int delFeeRelation(int copr_id) throws SQLException {
		int row = 0;
		cpd_List1Dal dal = new cpd_List1Dal();
		row = dal.delFeeRelation(copr_id);
		return row;
	}

	public List<CoProductModel> getCopCityList() {
		cpd_List1Dal dal = new cpd_List1Dal();
		return dal.getCopCityList();
	}

	// 获取委托机构
	public List<CoAgencyBaseModel> getcopcoalist() {
		cpd_List1Dal dal = new cpd_List1Dal();
		return dal.getcopcoalist();
	}

	// 获取合同类型列表
	public List<CopCompactModel> getCopCompactList(String str) {
		cpd_List1Dal dal = new cpd_List1Dal();
		return dal.getCopCompactList(str);
	}

	// 获取合同类型与产品的关系列表
	public List<CopCompactModel> getCopComRelList(Integer cpct_id) {
		cpd_List1Dal dal = new cpd_List1Dal();
		return dal.getCopComRelList(cpct_id);
	}

	// 查询机构的服务产品
	public List<CoProductModel> getAgProduct(int cabc_id) {
		cpd_List1Dal dal = new cpd_List1Dal();
		return dal.getAgProduct(cabc_id);
	}
}
