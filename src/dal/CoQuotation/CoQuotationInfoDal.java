package dal.CoQuotation;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import org.zkoss.zul.ListModelList;
import Conn.dbconn;
import Model.CI_Insurant_ListModel;
import Model.CoOLModeModel;
import Model.CoOfferListModel;

public class CoQuotationInfoDal {
	// 报价单项目详细信息
	public List<CoOfferListModel> getCoofferList(Integer coofId) {
		dbconn db = new dbconn();
		List<CoOfferListModel> list = new ListModelList<>();
		String sql = "select coli_id,coli_pclass,coli_name,coli_standard,coli_fee,coli_cpfc_name,"
				+ "coli_amount,coli_remark,coli_content,coli_coco_id,coli_group_id,coli_group_count,"
				+ "coli_coof_id,name city,coab_name"
				+ " from CoOfferList a"
				+ " left join CoProduct b on a.coli_copr_id=b.Copr_id"
				+ " left join CoAgencyBaseCityRel c on b.copr_cabc_id=c.cabc_id"
				+ " left join PubProCity d on cabc_ppc_id=d.id"
				+ " left join CoAgencyBase e on c.cabc_coab_id=e.coab_id"
				+ " left join CoPclass f on a.coli_pclass=f.Copc_name"
				+ " where coli_coof_id=? and coli_state=1 and (coli_copr_id>0 or coli_fee>0)"
				+ " order by copc_id,coli_group_id,coli_group_count desc";
		try {
			list = db.find(sql, CoOfferListModel.class, null, coofId);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return list;
	}

	// 报价单项目享受条件
	public List<CoOLModeModel> getcoolModelInfo(Integer coliId) {
		dbconn db = new dbconn();
		List<CoOLModeModel> list = new ListModelList<>();
		String sql = "select colm_name,colm_startmonth,colm_stopmonth,colm_fee,colm_type"
				+ " from CoOLMode"
				+ " where colm_state=1 and colm_coli_id=?"
				+ " order by colm_startmonth";
		try {
			list = db.find(sql, CoOLModeModel.class, null, coliId);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return list;
	}

	// 根据单个报价单
	public List<CoOfferListModel> getCoOfferlistList(int coli_coof_id)
			throws SQLException {
		dbconn db = new dbconn();
		dbconn db2 = new dbconn();
		ResultSet rs = null;
		ResultSet rs_st = null;
		ResultSet rs_cou = null;
		String coli_1 = "";
		int coli_2 = 0;
		int coli_3 = 0;
		int i = 0;
		String sql_st = "";
		String sql_ym = "";
		List<CoOfferListModel> list = new ArrayList<CoOfferListModel>();
		String sqlstr = "select * from CoOfferList a left outer join CoProduct b"
				+ " on a.coli_copr_id=b.Copr_id left outer join CoPclass c"
				+ " on b.copr_copc_id=c.Copc_id left outer join CoAgencyBaseCityRel d"
				+ " on b.copr_cabc_id=d.cabc_id left outer join PubProCity e"
				+ " on d.cabc_ppc_id=e.id left outer join CoAgencyBase f"
				+ " on d.cabc_coab_id=f.coab_id"
				+ " where coli_state=1  and ((coli_isfwf<>0 and coli_fee>0) or coli_copr_id<>0)"
				+ " and coli_coof_id="
				+ coli_coof_id
				+ " and coli_parid not in(select coli_id from CoOfferList"
				+ " where coli_coof_id="
				+ coli_coof_id
				+ " and coli_name='人事基础服务费') order by coli_group_id,coli_group_count desc";
		System.out.println(sqlstr);
		try {
			rs = db.GRS(sqlstr);
			while (rs.next()) {
				CoOfferListModel model = new CoOfferListModel();

				i = i + 1;
				sql_st = "select distinct emce_menuname emce_yw from CoOfferList a left join CoProduct b on b.Copr_id=a.coli_copr_id left join EmbaseBusinessCenter c on c.emce_id=b.copr_emce_id where coli_id="
						+ rs.getInt("coli_id");
				rs_st = db2.GRS(sql_st);

				System.out.println(sql_st);

				try {
					while (rs_st.next()) {

						if (rs_st.getString("emce_yw").equals("公积金信息")) {
							sql_ym = "select top 1 Emhu_IfStop em_cou from coglist a left join EmHouseUpdate b on b.gid=a.gid where cgli_coli_id="
									+ rs.getInt("coli_id")
									+ " order by Emhu_id desc";
							rs_cou = db2.GRS(sql_ym);
						}
						if (rs_st.getString("emce_yw").equals("社保信息")) {
							sql_ym = "select top 1 Esiu_IfStop em_cou from coglist a left join EmShebaoUpdate b on b.gid=a.gid where cgli_coli_id="
									+ rs.getInt("coli_id")
									+ " order by ID desc";
							rs_cou = db2.GRS(sql_ym);
						}

						try {
							while (rs_cou.next()) {
								coli_3 = rs_cou.getInt("em_cou");
							}
						} catch (Exception e) {
							coli_3 = 0;
						}
					}
				} catch (Exception e) {
					// System.out.print(e.toString());
					coli_3 = 0;
				}

				model.setCopr_type(coli_1);
				model.setCopr_emce_id(coli_2);
				model.setColi_parid(coli_3);
				model.setColi_id(rs.getInt("coli_id"));
				model.setColi_pclass(rs.getString("coli_pclass"));
				model.setColi_name(rs.getString("coli_name"));
				model.setColi_standard(rs.getString("coli_standard"));
				model.setColi_fee(rs.getBigDecimal("coli_fee"));
				model.setColi_cpfc_name(rs.getString("coli_cpfc_name"));
				model.setColi_amount(rs.getInt("coli_amount"));
				model.setColi_remark(rs.getString("coli_remark"));
				model.setColi_content(rs.getString("coli_content"));
				model.setColi_coco_id(rs.getInt("coli_coco_id"));
				model.setCity(rs.getString("name"));
				model.setCoab_name(rs.getString("coab_name"));
				model.setColi_group_id(rs.getInt("coli_group_id"));
				model.setColi_group_count(rs.getInt("coli_group_count"));
				model.setCoof_id(coli_coof_id);
				list.add(model);
			}
		} catch (Exception e) {
			// System.out.print(e.toString());
		} finally {
			db.Close();
		}
		return list;
	}

	// 根据多个报价单
	public List<CoOfferListModel> getCoOfferlistList(String coli_coof_ids)
			throws SQLException {
		dbconn db = new dbconn();
		dbconn db2 = new dbconn();
		ResultSet rs = null;
		ResultSet rs_st = null;
		ResultSet rs_cou = null;
		String coli_1 = "";
		int coli_2 = 0;
		int coli_3 = 0;
		int i = 0;
		String sql_st = "";
		String sql_ym = "";
		List<CoOfferListModel> list = new ArrayList<CoOfferListModel>();
		String sqlstr = "select * from CoOfferList a left outer join CoProduct b"
				+ " on a.coli_copr_id=b.Copr_id left outer join CoPclass c"
				+ " on b.copr_copc_id=c.Copc_id left outer join CoAgencyBaseCityRel d"
				+ " on b.copr_cabc_id=d.cabc_id left outer join PubProCity e"
				+ " on d.cabc_ppc_id=e.id left outer join CoAgencyBase f"
				+ " on d.cabc_coab_id=f.coab_id"
				+ " where coli_state=1  and (coli_isfwf<>0 or coli_copr_id<>0)"
				+ " and coli_coof_id in("
				+ coli_coof_ids
				+ ") and coli_parid not in(select coli_id from CoOfferList"
				+ " where coli_coof_id in("
				+ coli_coof_ids
				+ ") and coli_name='人事基础服务费') order by coli_group_id,coli_group_count desc";
		System.out.println(sqlstr);
		try {
			rs = db.GRS(sqlstr);
			while (rs.next()) {
				CoOfferListModel model = new CoOfferListModel();

				i = i + 1;
				sql_st = "select distinct emce_menuname emce_yw from CoOfferList a left join CoProduct b on b.Copr_id=a.coli_copr_id left join EmbaseBusinessCenter c on c.emce_id=b.copr_emce_id where coli_id="
						+ rs.getInt("coli_id");
				rs_st = db2.GRS(sql_st);

				System.out.println(sql_st);

				try {
					while (rs_st.next()) {

						if (rs_st.getString("emce_yw").equals("公积金信息")) {
							sql_ym = "select top 1 Emhu_IfStop em_cou from coglist a left join EmHouseUpdate b on b.gid=a.gid where cgli_coli_id="
									+ rs.getInt("coli_id")
									+ " order by Emhu_id desc";
							rs_cou = db2.GRS(sql_ym);
						}
						if (rs_st.getString("emce_yw").equals("社保信息")) {
							sql_ym = "select top 1 Esiu_IfStop em_cou from coglist a left join EmShebaoUpdate b on b.gid=a.gid where cgli_coli_id="
									+ rs.getInt("coli_id")
									+ " order by ID desc";
							rs_cou = db2.GRS(sql_ym);
						}

						try {
							while (rs_cou.next()) {
								coli_3 = rs_cou.getInt("em_cou");
							}
						} catch (Exception e) {
							coli_3 = 0;
						}
					}
				} catch (Exception e) {
					// System.out.print(e.toString());
					coli_3 = 0;
				}

				model.setCopr_type(coli_1);
				model.setCopr_emce_id(coli_2);
				model.setColi_parid(coli_3);
				model.setColi_id(rs.getInt("coli_id"));
				model.setColi_pclass(rs.getString("coli_pclass"));
				model.setColi_name(rs.getString("coli_name"));
				model.setColi_standard(rs.getString("coli_standard"));
				model.setColi_fee(rs.getBigDecimal("coli_fee"));
				model.setColi_cpfc_name(rs.getString("coli_cpfc_name"));
				model.setColi_amount(rs.getInt("coli_amount"));
				model.setColi_remark(rs.getString("coli_remark"));
				model.setColi_content(rs.getString("coli_content"));
				model.setColi_coco_id(rs.getInt("coli_coco_id"));
				model.setCity(rs.getString("name"));
				model.setCoab_name(rs.getString("coab_name"));
				model.setColi_group_id(rs.getInt("coli_group_id"));
				model.setColi_group_count(rs.getInt("coli_group_count"));
				model.setCoof_id(rs.getInt("coli_coof_id"));
				list.add(model);
			}
		} catch (Exception e) {
			// System.out.print(e.toString());
		} finally {
			db.Close();
		}
		return list;
	}

	public List<CoOfferListModel> getCoOfferlist(String coli_coco_id)
			throws SQLException {
		dbconn db = new dbconn();
		ResultSet rs = null;
		ResultSet rs_st = null;
		ResultSet rs_cou = null;
		String coli_1 = "";
		int coli_2 = 0;
		int coli_3 = 0;
		int i = 0;
		String sql_st = "";
		String sql_ym = "";
		List<CoOfferListModel> list = new ArrayList<CoOfferListModel>();
		String sqlstr = "select * from CoOfferList a left outer join CoProduct b"
				+ " on a.coli_copr_id=b.Copr_id left outer join CoPclass c"
				+ " on b.copr_copc_id=c.Copc_id left outer join CoAgencyBaseCityRel d"
				+ " on b.copr_cabc_id=d.cabc_id left outer join PubProCity e"
				+ " on d.cabc_ppc_id=e.id left outer join CoAgencyBase f"
				+ " on d.cabc_coab_id=f.coab_id"
				+ " where "
				+ " coli_coco_id="
				+ coli_coco_id + " and coli_state=1 order by coli_group_id";

		try {
			rs = db.GRS(sqlstr);
			while (rs.next()) {
				CoOfferListModel model = new CoOfferListModel();
				i = i + 1;
				sql_st = "select distinct emce_menuname emce_yw from CoOfferList a left join CoProduct b on b.Copr_id=a.coli_copr_id left join EmbaseBusinessCenter c on c.emce_id=b.copr_emce_id where coli_id="
						+ rs.getInt("coli_id");
				rs_st = db.GRS(sql_st);

				coli_1 = "";
				coli_2 = 0;

				while (rs_st.next()) {
					coli_3 = 0;
					sql_ym = "select 0 em_cou";
					rs_cou = db.GRS(sql_ym);

					System.out.println("xxxxxxxxxxxx");
					System.out.println(rs_st.getString("emce_yw"));

					if (rs_st.getString("emce_yw").equals("公积金信息")) {
						sql_ym = "select COUNT(*) em_cou from coglist a left join EmHouseUpdate b on b.gid=a.gid where emhu_ifstop=0 and ownmonth=201409 and cgli_coli_id="
								+ rs.getInt("coli_id");
						rs_cou = db.GRS(sql_ym);
					}
					if (rs_st.getString("emce_yw").equals("社保信息")) {
						sql_ym = "select COUNT(*) em_cou from coglist a left join EmShebaoUpdate b on b.gid=a.gid where esiu_ifstop=0 and ownmonth=201409 and cgli_coli_id="
								+ rs.getInt("coli_id");
						rs_cou = db.GRS(sql_ym);
					}

					System.out.println(sql_ym);

					while (rs_cou.next()) {
						coli_3 = rs_cou.getInt("em_cou");
					}
				}

				model.setCopr_type(coli_1);
				model.setCopr_emce_id(coli_2);
				model.setColi_parid(coli_3);
				model.setColi_id(rs.getInt("coli_id"));
				model.setColi_pclass(rs.getString("coli_pclass"));
				model.setColi_name(rs.getString("coli_name"));
				model.setColi_standard(rs.getString("coli_standard"));
				model.setColi_fee(rs.getBigDecimal("coli_fee"));
				model.setColi_cpfc_name(rs.getString("coli_cpfc_name"));
				model.setColi_amount(rs.getInt("coli_amount"));
				model.setColi_remark(rs.getString("coli_remark"));
				model.setColi_content(rs.getString("coli_content"));
				model.setCity(rs.getString("name"));
				model.setCoab_name(rs.getString("coab_name"));
				model.setColi_group_id(rs.getInt("coli_group_id"));
				model.setColi_group_count(rs.getInt("coli_group_count"));
				model.setColi_coco_id(rs.getInt("coli_coco_id"));
				list.add(model);
			}
		} catch (Exception e) {
			System.out.print(e.toString());
		} finally {
			db.Close();
		}
		return list;
	}

	public List<CoOfferListModel> getCoOfferlistInfo(String coli_coco_id) {
		dbconn db = new dbconn();

		List<CoOfferListModel> list = new ListModelList<>();
		String sql = "select coli_id,coli_pclass,coli_name,coli_standard,coli_fee,coli_cpfc_name,"
				+ "coli_amount,coli_remark,coli_content,name,coab_name,coli_group_id,coli_group_count,coli_parid"
				+ " from CoOfferList a left outer join CoProduct b"
				+ " on a.coli_copr_id=b.Copr_id left outer join CoPclass c"
				+ " on b.copr_copc_id=c.Copc_id left outer join CoAgencyBaseCityRel d"
				+ " on b.copr_cabc_id=d.cabc_id left outer join PubProCity e"
				+ " on d.cabc_ppc_id=e.id left outer join CoAgencyBase f"
				+ " on d.cabc_coab_id=f.coab_id"
				+ " where coli_coco_id="
				+ coli_coco_id + " and (coli_copr_id>0 or coli_isfwf>0)"
				// + " and coli_parid not in(select coli_id from CoOfferList"
				// + " where coli_coco_id="
				// + coli_coco_id
				// + " and coli_name='人事基础服务费')
				+ " order by coli_group_id";
		System.out.println(sql);
		try {
			list = db.find(sql, CoOfferListModel.class, null);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return list;
	}

	// 单个报价单
	public List<CoOfferListModel> getCompactClass(int coof_id)
			throws SQLException {
		dbconn db = new dbconn();
		ResultSet rs = null;
		List<CoOfferListModel> list = new ListModelList<>();
		String sql = "select distinct cpct_shortname,ebct_type from cooffer a left join CopCompact b on b.cpct_id=a.coof_cpct_id left join (select ebct_class,ebct_type from compactver where ecid=2 and ebct_state=1 and ebct_class is not null) c on c.ebct_class=b.cpct_shortname where coof_id="
				+ coof_id;

		try {
			System.out.println(sql);
			rs = db.GRS(sql);
			while (rs.next()) {
				CoOfferListModel model = new CoOfferListModel();
				model.setCoco_compacttype(rs.getString("ebct_type"));
				list.add(model);
			}
		} catch (Exception e) {
			System.out.print(e.toString());
		} finally {
			db.Close();
		}
		return list;
	}

	// 多个报价单
	public List<CoOfferListModel> getCompactClass(String coof_ids)
			throws SQLException {
		dbconn db = new dbconn();
		ResultSet rs = null;
		List<CoOfferListModel> list = new ListModelList<>();
		String sql = "select distinct cpct_shortname,ebct_type from cooffer a left join CopCompact b on b.cpct_id=a.coof_cpct_id left join (select ebct_class,ebct_type from compactver where ecid=2 and ebct_state=1 and ebct_class is not null) c on c.ebct_class=b.cpct_shortname where coof_id in("
				+ coof_ids + ")";

		try {
			System.out.println(sql);
			rs = db.GRS(sql);
			while (rs.next()) {
				CoOfferListModel model = new CoOfferListModel();
				model.setCoco_compacttype(rs.getString("ebct_type"));
				list.add(model);
			}
		} catch (Exception e) {
			System.out.print(e.toString());
		} finally {
			db.Close();
		}
		return list;
	}
}
