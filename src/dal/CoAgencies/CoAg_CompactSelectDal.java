package dal.CoAgencies;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import Conn.dbconn;
import Model.CoAgencyBaseModel;
import Model.CoAgencyCompactModel;
import Model.PubProCityModel;

public class CoAg_CompactSelectDal {
	// 查询委托合同信息
	public List<CoAgencyCompactModel> getWtCompactList(String str) {
		List<CoAgencyCompactModel> list = new ArrayList<CoAgencyCompactModel>();
		dbconn db = new dbconn();
		String sql = "select convert(varchar(10),coct_signdate,120) as coct_signdate,"
				+ "convert(varchar(10),coct_effectdate,120) as coct_effectdate,"
				+ "convert(varchar(10),coct_expiredate,120) as coct_expiredate,"
				+ "convert(varchar(10),coct_stopdate,120) as coct_stopdate,"
				+ "convert(varchar(16),coct_addtime,120) as coct_addtime,"
				+ "convert(varchar(10),coct_maketime,120) as coct_maketime,"
				+ "convert(varchar(10),coct_audittime,120) as coct_audittime,"
				+ "convert(varchar(10),coct_signbacktime,120) as coct_signbacktime,"
				+ "convert(varchar(10),coct_archivetime,120) as coct_archivetime,"
				+ "case coct_state when 0 then '待制作' when 1 then '待审核' when 2 then '待签回' "
				+ "when 3 then '待归档' when 4 then '已归档' when 5 then '已终止' when 6 then '已解约'"
				+ " when 7 then '退回' end statename,"
				+ "case coct_autoextend when 0 then '否' when 1 then '是' end autoex,"
				+ "case when num>0 then num else 0 end num,a.* from CoAgencyCompact a left join "
				+ "(select COUNT(*) num,ctcy_coct_id from CoAgencyCompactCityRel GROUP BY ctcy_coct_id) b "
				+ " on a.coct_id=b.ctcy_coct_id where 1=1"
				+ str
				+ " order by coct_state";
		try {
			list = db.find(sql, CoAgencyCompactModel.class,
					dbconn.parseSmap(CoAgencyCompactModel.class));
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}

	// 查询委托合同信息
	public List<CoAgencyCompactModel> getWtCompactList(int cabc_id) {
		List<CoAgencyCompactModel> list = new ArrayList<CoAgencyCompactModel>();
		dbconn db = new dbconn();
		String sql = "select convert(varchar(10),coct_signdate,120) as coct_signdate,"
				+ "convert(varchar(10),coct_effectdate,120) as coct_effectdate,"
				+ "convert(varchar(10),coct_expiredate,120) as coct_expiredate,"
				+ "convert(varchar(10),coct_stopdate,120) as coct_stopdate,"
				+ "convert(varchar(16),coct_addtime,120) as coct_addtime,"
				+ "convert(varchar(10),coct_maketime,120) as coct_maketime,"
				+ "convert(varchar(10),coct_audittime,120) as coct_audittime,"
				+ "convert(varchar(10),coct_signbacktime,120) as coct_signbacktime,"
				+ "convert(varchar(10),coct_archivetime,120) as coct_archivetime,"
				+ "case coct_state when 0 then '待制作' when 1 then '待审核' when 2 then '待签回' "
				+ "when 3 then '待归档' when 4 then '已归档' when 5 then '已终止' when 6 then '已解约'"
				+ " when 7 then '退回' end statename,"
				+ "case coct_autoextend when 0 then '否' when 1 then '是' end autoex,cc.* "
				+ "from CoAgencyCompactCityRel cr "
				+ "inner join CoAgencyCompact cc on cr.ctcy_coct_id=cc.coct_id "
				+ "where cc.coct_type='委托合同' and cr.ctcy_cabc_id=" + cabc_id;
		try {
			list = db.find(sql, CoAgencyCompactModel.class,
					dbconn.parseSmap(CoAgencyCompactModel.class));
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}

	// 查询添加人
	public List<String> getAddname() {
		List<String> list = new ArrayList<String>();
		dbconn db = new dbconn();
		String sql = "select distinct(coct_addname) coct_addname from CoAgencyCompact"
				+ " where coct_addname is not null";
		try {
			ResultSet rs = db.GRS(sql);
			list.add("");
			while (rs.next()) {
				list.add(rs.getString("coct_addname"));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}

	// 查询机构
	public List<CoAgencyBaseModel> getCoAgencyBaseList(String str) {
		List<CoAgencyBaseModel> list = new ArrayList<CoAgencyBaseModel>();
		dbconn db = new dbconn();
		String sql = "select convert(varchar(10),coab_addtime,120) as coab_addtime,"
				+ "* from CoAgencyBase where coab_state=1" + str;
		try {
			list = db.find(sql, CoAgencyBaseModel.class,
					dbconn.parseSmap(CoAgencyBaseModel.class));
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}

	// 查询机构的城市
	public List<PubProCityModel> getCoPubProCityList(String sqlstr) {
		List<PubProCityModel> list = new ArrayList<PubProCityModel>();
		dbconn db = new dbconn();
		String sql = "select distinct(name) name from CoAgencyCompactCityRel d "
				+ "left join CoAgencyBaseCityRel b on d.ctcy_cabc_id=b.cabc_id "
				+ "left join CoAgencyBase a on a.coab_id=b.cabc_coab_id "
				+ "left join PubProCity ppc on ppc.id=b.cabc_ppc_id "
				+ "where cabc_state=1 " + sqlstr;
		try {
			list = db.find(sql, PubProCityModel.class,
					dbconn.parseSmap(PubProCityModel.class));
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}

	// 查询机构的城市
	public List<PubProCityModel> getCityList(String sqlstr) {
		List<PubProCityModel> list = new ArrayList<PubProCityModel>();
		dbconn db = new dbconn();
		String sql = "select cabc_id,cabc_coab_id,cabc_ppc_id,c.* from CoAgencyBase a,"
				+ "CoAgencyBaseCityRel b ,PubProCity c "
				+ "where a.coab_id=b.cabc_coab_id and b.cabc_ppc_id=c.id and cabc_state=1"
				+ sqlstr;
		try {
			list = db.find(sql, PubProCityModel.class,
					dbconn.parseSmap(PubProCityModel.class));
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}
}
