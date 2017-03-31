package dal.EmHouse;

import java.sql.SQLException;
import java.util.List;

import org.zkoss.zul.ListModelList;

import Conn.dbconn;
import Model.CoHousingFundModel;

public class EmHouseCompanyIdDal {

	// 按公司编号查询独立户单位公积金在册列表
	public List<CoHousingFundModel> getlistByCid(Integer cid) {
		List<CoHousingFundModel> list = new ListModelList<>();
		dbconn db = new dbconn();
		String sql = "select  cohf_id, cid, cohf_company, ownmonth, cohf_houseid, cohf_addtype, cohf_cpp, cohf_client,"
				+ " cohf_bankts, cohf_banktsid, cohf_banktsacc, cohf_bankjc,cohf_bankjcid, cohf_bankgj, cohf_banklk, "
				+ "cohf_tsday, cohf_lastday, cohf_addtime, cohf_addname, cohf_state, cohf_sorid, cohf_stop_type, "
				+ "cohf_stoptime,cohf_stop_reason, cohf_comid, cohf_zgtype, cohf_address, cohf_area, cohf_pastal, "
				+ "cohf_nature, cohf_ecoclass, cohf_industry, cohf_attached, cohf_corname,cohf_coridtype, "
				+ "cohf_coridcard, cohf_cortel, cohf_department, cohf_departmenttel, cohf_createtime, cohf_regid, "
				+ "cohf_taxpayerid, cohf_jbdepartment, cohf_contactname,cohf_contacttel, cohf_contactmail, "
				+ "cohf_contactmobile, cohf_firmonth, cohf_ispwd, cohf_single, cohf_completetime, cohf_manstate,"
				+ " cohf_if_edit ,cohf_if_bj ,cohf_start_month ,cohf_end_month, cohf_hjstart_month, cohf_hjend_month,"
				+ " cohf_if_low, cohf_if_hj ,"
				+ "y+'-'+m+'-'+convert(varchar(10),case when cohf_lastday>day(dbo.endofmonth(y+'-'+m+'-1',0)) then day(dbo.endofmonth(y+'-'+m+'-1',0)) else cohf_lastday end)lastdate "
				+ " from CoHousingFund a "
				+ "inner join (select top 1 substring(convert(varchar(10),ownmonth),1,4)y,substring(convert(varchar(10),ownmonth),5,2)m from EmHouseUpdate)b on 1=1"
				+ " where 1=1";
		if (cid != null) {
			if (!cid.equals("")) {
				sql = sql + " and cid=" + cid;
			}
		}

		try {
			list = db.find(sql, CoHousingFundModel.class, null);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return list;
	}

	// 按编号查询独立户单位公积金在册列表
	public List<CoHousingFundModel> getlistById(Integer id) {
		List<CoHousingFundModel> list = new ListModelList<>();
		dbconn db = new dbconn();
		String sql = "select  cohf_id, cid, cohf_company, ownmonth, cohf_houseid, cohf_addtype, cohf_cpp, cohf_client, cohf_bankts, cohf_banktsid, cohf_banktsacc, cohf_bankjc, cohf_bankjcid,"
				+ "cohf_bankgj, cohf_banklk, cohf_tsday, cohf_lastday, cohf_addtime, cohf_addname, cohf_state, cohf_sorid, cohf_stop_type, cohf_stop_reason, cohf_comid, "
				+ "cohf_zgtype, cohf_address, cohf_area, cohf_pastal, cohf_nature, cohf_ecoclass, cohf_industry, cohf_attached, cohf_corname, cohf_coridtype, cohf_coridcard, "
				+ "cohf_cortel, cohf_department, cohf_departmenttel, cohf_createtime, cohf_regid, cohf_taxpayerid, cohf_jbdepartment, cohf_contactname, cohf_contacttel, "
				+ "cohf_contactmail, cohf_contactmobile, cohf_firmonth, cohf_ispwd, cohf_single, cohf_completetime, cohf_manstate, cohf_if_edit,chfz_id "
				+ "from CoHousingFund a inner join CoHousingFundZb b on a.cohf_id=b.chfz_cohf_id "
				+ "where cohf_state=1 and cohf_id=?";
		try {
			list = db.find(sql, CoHousingFundModel.class,
					dbconn.parseSmap(CoHousingFundModel.class), id);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return list;
	}
}
