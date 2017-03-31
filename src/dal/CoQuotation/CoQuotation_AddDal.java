package dal.CoQuotation;

import java.sql.CallableStatement;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.ArrayList;
import java.util.List;

import org.zkoss.zul.ListModelList;

import Conn.dbconn;
import Model.CoAgencyBaseModel;
import Model.CoOLModeModel;
import Model.CoOfferListModel;
import Model.CoOfferModel;
import Model.CoPFeeclassModel;
import Model.CoPclassModel;
import Model.CoProductModel;
import Model.CopCompactModel;
import Model.PubProCityModel;

public class CoQuotation_AddDal {
	public List<String> getClassList() throws SQLException {
		dbconn db = new dbconn();
		ResultSet rs = null;
		List<String> list = new ArrayList<String>();
		String sqlstr = "select '' copc_name,0 copc_id union "
				+ "select distinct copc_name,copc_id from View_CoProduct order by copc_id";

		try {
			rs = db.GRS(sqlstr);
			while (rs.next()) {
				list.add(rs.getString("copc_name"));
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			db.Close();
		}
		return list;
	}

	public List<CoPFeeclassModel> getFeeClassList(int copr_id)
			throws SQLException {
		dbconn db = new dbconn();
		ResultSet rs = null;
		List<CoPFeeclassModel> list = new ArrayList<CoPFeeclassModel>();
		String sqlstr = "select cpfc_id,cpfc_unit+'/'+cpfc_chronon+'/'+cpfc_types as cpfc_name "
				+ " from CoPFeeRelation b inner join CoPFeeclass c"
				+ " on b.cpfr_cpfc_id=c.cpfc_id"
				+ " where cpfr_state=1 and cpfr_copr_id="
				+ copr_id
				+ " order by cpfc_id";

		try {
			rs = db.GRS(sqlstr);
			while (rs.next()) {
				CoPFeeclassModel cpModel = new CoPFeeclassModel();
				cpModel.setCpfc_id(rs.getInt("cpfc_id"));
				cpModel.setCpfc_name(rs.getString("cpfc_name"));
				list.add(cpModel);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			db.Close();
		}
		return list;
	}

	public List<CoProductModel> getCoproductFee(Integer id) {
		List<CoProductModel> list = new ListModelList<>();
		dbconn db = new dbconn();
		String sql = "select cpfc_id,cpfc_unit+'/'+cpfc_chronon+'/'+cpfc_types as cpfc_name ,cpfr_fee fee,cpfr_lock "
				+ "from CoPFeeRelation a "
				+ "inner join CoPFeeclass b on a.cpfr_cpfc_id=b.cpfc_id "
				+ "where cpfr_state=1 and cpfr_copr_id=?";
		try {
			list = db.find(sql, CoProductModel.class, null, id);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return list;
	}

	public List<CoProductModel> getCoproductList(String str)
			throws SQLException {
		dbconn db = new dbconn();
		ResultSet rs = null;
		List<CoProductModel> list = new ArrayList<CoProductModel>();
		String sqlstr = "select * from View_CoProduct a inner join copcomrel b on a.copr_id=b.cpcr_copr_id"
				+ " where copr_state=2" + str + " order by Copc_id";

		try {
			rs = db.GRS(sqlstr);
			while (rs.next()) {
				CoProductModel cpModel = new CoProductModel();
				cpModel.setCopr_id(rs.getInt("copr_id"));
				cpModel.setCopr_name(rs.getString("copr_name"));
				cpModel.setCity(rs.getString("name"));
				cpModel.setCpcr_cpct_id(rs.getInt("cpcr_cpct_id"));
				cpModel.setCopc_name(rs.getString("copc_name"));
				cpModel.setCopr_type(rs.getString("copr_type"));

				list.add(cpModel);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			db.Close();
		}
		return list;
	}

	// 修改显示外地产品价格
	public List<CoProductModel> getCoproductListzmj(String str)
			throws SQLException {
		dbconn db = new dbconn();
		ResultSet rs = null;
		List<CoProductModel> list = new ArrayList<CoProductModel>();
		String sqlstr = "select  a.copr_id ,copr_name,name,cpcr_cpct_id,copc_name,copr_type,cpfr_fee"
				+ "  from View_CoProduct a inner join copcomrel b on a.copr_id=b.cpcr_copr_id"
				+ " left join     (select Copr_id,cpfr_fee from  CoProduct aa left join CoPFeeRelation bb on aa.Copr_id=bb.cpfr_copr_id left join CoPFeeclass cc  ON bb.cpfr_cpfc_id =cc.cpfc_id  "
				+ "where aa.copr_cabc_id<>1 and cpfr_fee>0  and cpfr_state=1) ss"
				+ "  on a.Copr_id=ss.Copr_id "
				+ " where a.copr_state=2"
				+ str
				+ " order by a.Copc_id,copr_sort desc,copr_name";

		try {
			rs = db.GRS(sqlstr);
			while (rs.next()) {
				CoProductModel cpModel = new CoProductModel();
				cpModel.setCopr_id(rs.getInt("copr_id"));
				cpModel.setCopr_name(rs.getString("copr_name"));
				cpModel.setCity(rs.getString("name"));
				cpModel.setCpcr_cpct_id(rs.getInt("cpcr_cpct_id"));
				cpModel.setCopc_name(rs.getString("copc_name"));
				cpModel.setCopr_type(rs.getString("copr_type"));
				cpModel.setFee(rs.getBigDecimal("cpfr_fee"));
				list.add(cpModel);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			db.Close();
		}
		return list;
	}

	public ListModelList<PubProCityModel> getCityList() throws SQLException {
		dbconn db = new dbconn();
		ResultSet rs = null;
		ListModelList<PubProCityModel> list = new ListModelList<PubProCityModel>();
		String sqlstr = "select spell+name name,id from pubprocity "
				+ "order by spell";

		try {
			rs = db.GRS(sqlstr);
			while (rs.next()) {
				PubProCityModel ppcModel = new PubProCityModel();
				ppcModel.setId(rs.getInt("id"));
				ppcModel.setName(rs.getString("name"));
				list.add(ppcModel);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			db.Close();
		}
		return list;
	}

	public List<CoOfferListModel> getCoProductList(String pclass, String city)
			throws SQLException {
		dbconn db = new dbconn();
		ResultSet rs = null;
		List<CoOfferListModel> list = new ArrayList<CoOfferListModel>();
		String sqlstr = "select * from View_CoProduct " + "where Copc_name='"
				+ pclass + "' and cpcr_city='" + city + "' order by copr_name";
		try {
			rs = db.GRS(sqlstr);
			while (rs.next()) {
				CoOfferListModel model = new CoOfferListModel();
				model.setColi_copr_id(rs.getInt("copr_id"));
				model.setColi_name(rs.getString("copr_name"));
				model.setColi_content(rs.getString("copr_content"));
				list.add(model);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			db.Close();
		}
		return list;
	}

	public CoProductModel getFee(int copr_id, int cpfc_id) throws SQLException {
		dbconn db = new dbconn();
		ResultSet rs = null;
		CoProductModel model = new CoProductModel();
		String sqlstr = "select * from CoPFeeRelation where cpfr_copr_id="
				+ copr_id + " and " + "cpfr_cpfc_id=" + cpfc_id
				+ " and cpfr_state=1";

		try {
			rs = db.GRS(sqlstr);
			while (rs.next()) {
				model.setCpfc_id(rs.getInt("cpfr_cpfc_id"));
				model.setFee(rs.getBigDecimal("cpfr_fee"));
				model.setCpfr_lock(rs.getInt("cpfr_lock"));
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			db.Close();
		}
		return model;
	}

	public List<CoOfferListModel> getStandardList(int copr_id)
			throws SQLException {
		dbconn db = new dbconn();
		ResultSet rs = null;
		List<CoOfferListModel> list = new ArrayList<CoOfferListModel>();
		String sqlstr = "select * from CoPStRelation a,CoPStandard b "
				+ "where a.cpsr_cpst_id=b.cpst_id and cpsr_state=1 and cpsr_copr_id="
				+ copr_id;

		try {
			rs = db.GRS(sqlstr);
			while (rs.next()) {
				CoOfferListModel model = new CoOfferListModel();
				model.setColi_standard(rs.getString("cpst_name"));
				list.add(model);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			db.Close();
		}
		return list;
	}

	public CoOfferModel CoOfferAdd(CoOfferModel model) throws SQLException {
		CallableStatement csmt = null;
		dbconn db = new dbconn();
		CoOfferModel model1 = new CoOfferModel();

		// 创建存储过程的对象
		csmt = db
				.getcall("CoOfferAdd_P_ply(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)");

		try {
			// 给存储过程的参数设置值
			Date quotetime = model.getCoof_quotetime() == null ? null
					: new Date(model.getCoof_quotetime().getTime());
			csmt.setString(1, model.getCoof_name());
			csmt.setObject(2, model.getCoof_cpct_id());
			csmt.setString(3, model.getCoof_quotemode());
			csmt.setDate(4, quotetime);
			csmt.setString(5, model.getCoof_addname());
			csmt.setInt(6, model.getCoof_register());
			csmt.setInt(7, model.getCoof_cola_id());
			csmt.setInt(8, model.getCoof_min());
			csmt.setInt(9, model.getCoof_max());
			csmt.setString(10, model.getCoof_gm());
			csmt.setInt(11, model.getCoof_state());
			csmt.setInt(12, model.getCoof_id());
			csmt.setInt(13, model.getCid());
			csmt.setInt(14, model.getCoof_coco_id());
			csmt.setBigDecimal(15, model.getCoof_sum());
			// 注册存储过程的返回值
			csmt.registerOutParameter(16, java.sql.Types.INTEGER);
			csmt.registerOutParameter(17, java.sql.Types.INTEGER);

			// 执行存储过程
			csmt.execute();

			// 获取返回值
			model1.setRow(csmt.getInt(16));
			model1.setCoof_id(csmt.getInt(17));

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			db.Close();
		}

		return model1;
	}

	public void CoOfferListAdd(CoOfferListModel m) {
		dbconn db = new dbconn();

		try {
			db.callWithReturn(
					"{?=call CoOfferListAddTem_P_ply(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)}",
					Types.INTEGER, m.getColi_id(), m.getColi_coof_id(),
					m.getColi_copr_id(), m.getColi_name(), m.getColi_content(),
					m.getColi_pclass(), m.getColi_account(),
					m.getColi_standard(), m.getColi_gather(),
					m.getColi_remark(), m.getColi_addname(),
					m.getColi_addtime(), m.getColi_starttime(),
					m.getColi_stoptime(), m.getColi_modname(),
					m.getColi_modtime(), m.getColi_feetype(),
					m.getColi_amount(), m.getColi_state(),
					m.getColi_discount(), m.getColi_fee(), m.getColi_city(),
					m.getColi_cpfc_name(), m.getColi_coco_id(),
					m.getColi_parid(), m.getColi_isfwf(), m.getColi_group_id(),
					m.getColi_group_count(), m.getCoof_id());

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public List<CoAgencyBaseModel> getCoAgencyList(int id) throws SQLException {
		dbconn db = new dbconn();
		ResultSet rs = null;
		List<CoAgencyBaseModel> list = new ArrayList<CoAgencyBaseModel>();
		String sqlstr = "select * from CoAgencyBase a inner join "
				+ "CoAgencyBaseCityRel b on a.coab_id=b.cabc_coab_id "
				+ "where cabc_state=1 and coab_state=1 and b.cabc_ppc_id=" + id;

		try {
			rs = db.GRS(sqlstr);
			while (rs.next()) {
				CoAgencyBaseModel model = new CoAgencyBaseModel();
				model.setCoab_id(rs.getInt("cabc_id"));
				model.setCoab_name(rs.getString("coab_name"));
				list.add(model);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			db.Close();
		}
		return list;
	}

	public List<CoOfferListModel> getCoOfferListTemList(int coli_coof_id)
			throws Exception {
		dbconn db = new dbconn();
		ResultSet rs = null;
		List<CoOfferListModel> list = new ArrayList<CoOfferListModel>();
		String sqlstr = "select * from CoOfferList a left outer join "
				+ "CoPclass b on a.coli_name=b.Copc_name "
				+ "where coli_copr_id=0 and coli_isfwf=0 and coli_coof_id="
				+ coli_coof_id;

		try {
			rs = db.GRS(sqlstr);
			while (rs.next()) {
				CoOfferListModel model = new CoOfferListModel();
				model.setColi_coof_id(coli_coof_id);
				model.setColi_copr_id(rs.getInt("coli_copr_id"));
				model.setColi_id(rs.getInt("coli_id"));
				model.setColi_name(rs.getString("coli_name"));
				model.setColi_cpfc_name(rs.getString("coli_cpfc_name"));
				model.setColi_fee(rs.getBigDecimal("coli_fee"));
				model.setColi_isfwf(rs.getInt("coli_isfwf"));
				model.setColi_group_id(rs.getInt("coli_group_id"));
				model.setColi_group_count(rs.getInt("coli_group_count"));
				System.out.println("-===-");
				System.out.println(rs.getInt("coli_amount"));
				model.setColi_amount(rs.getInt("coli_amount") > 0 ? rs
						.getInt("coli_amount") : 1);
				System.out.println(model.getColi_amount());
				if (rs.getInt("coli_copr_id") == 0) {
					model.setInfoList(new ListModelList<CoOfferListModel>(
							getCoOfferListTemInfoList(rs.getInt("coli_id"),
									coli_coof_id)));
				}
				list.add(model);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			db.Close();
		}
		return list;
	}

	public List<CoOfferListModel> getCoOfferListTemInfoList(int coli_id,
			int coli_coof_id) throws Exception {
		dbconn db = new dbconn();
		ResultSet rs = null;
		List<CoOfferListModel> list = new ArrayList<CoOfferListModel>();
		String sqlstr = "select * from CoOfferList a left join CoProduct b"
				+ " on a.coli_copr_id=b.Copr_id left join CoAgencyBaseCityRel c"
				+ " on b.copr_cabc_id=c.cabc_id left join PubProCity d"
				+ " on c.cabc_ppc_id=d.id left join CoAgencyBase e"
				+ " on c.cabc_coab_id=e.coab_id where coli_parid="
				+ coli_id
				+ " order by case when coli_copr_id=0 then 1 else 0 end,coli_group_id";

		try {
			rs = db.GRS(sqlstr);
			while (rs.next()) {
				CoOfferListModel model = new CoOfferListModel();
				model.setColi_coof_id(rs.getInt("coli_coof_id"));
				model.setColi_id(rs.getInt("coli_id"));
				model.setColi_copr_id(rs.getInt("coli_copr_id"));
				model.setColi_name(rs.getString("coli_name"));
				model.setColi_content(rs.getString("coli_content"));
				model.setColi_fee(rs.getBigDecimal("coli_fee"));
				model.setColi_cpfc_name(rs.getString("coli_cpfc_name"));
				model.setCity(rs.getString("name"));
				model.setCopr_type(rs.getString("copr_type"));
				model.setColi_isfwf(rs.getInt("coli_isfwf"));
				model.setColi_pclass(rs.getString("coli_pclass"));
				model.setColi_amount(rs.getInt("coli_amount"));
				model.setCoab_name(rs.getString("coab_name"));
				model.setColi_group_id(rs.getInt("coli_group_id"));
				model.setColi_group_count(rs.getInt("coli_group_count"));
				model.setCoollist(getcoolmodeinfo(" and colm_coli_id="
						+ rs.getInt("coli_id")));
				list.add(model);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			db.Close();
		}
		return list;
	}

	public List<CoOfferListModel> getCoOfferListList(Integer coof_id) {
		dbconn db = new dbconn();
		List<CoOfferListModel> list = new ArrayList<>();
		String sql = "select coli_id,coli_coof_id,coli_copr_id,coli_name,coli_content,"
				+ "coli_pclass,coli_account,coli_standard,coli_gather,coli_remark,coli_addname,"
				+ "coli_addtime,coli_starttime,coli_stoptime,coli_modname,coli_modtime,coli_feetype,"
				+ "coli_amount,coli_state,coli_discount,coli_fee,coli_city,coli_cpfc_name,coli_coco_id,"
				+ "coli_parid,coli_isfwf,coli_group_id,coli_group_count,name city,coab_name"
				+ " from CoOfferList a left join View_CoProduct b on a.coli_copr_id=b.copr_id"
				+ " where coli_copr_id<>0 and coli_coof_id=?";

		try {
			list = db.find(sql, CoOfferListModel.class,
					dbconn.parseSmap(CoOfferListModel.class), coof_id);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}

	// 获取享受条件信息
	public List<CoOLModeModel> getcoolmodeinfo(String str) {
		List<CoOLModeModel> list = new ArrayList<CoOLModeModel>();
		dbconn db = new dbconn();
		ResultSet rs = null;
		String sql = "select * from CoOLMode where 1=1 " + str;
		try {
			Integer jk = 0;
			rs = db.GRS(sql);
			while (rs.next()) {
				CoOLModeModel m = new CoOLModeModel();
				m.setColm_id(rs.getInt("colm_id"));
				m.setColm_coli_id(rs.getInt("colm_coli_id"));
				m.setColm_name(rs.getString("colm_name"));
				m.setColm_startmonth(rs.getInt("colm_startmonth"));
				m.setColm_stopmonth(rs.getInt("colm_stopmonth"));
				m.setColm_fee(rs.getBigDecimal("colm_fee"));
				m.setColm_type(rs.getString("colm_type"));
				m.setColm_addname(rs.getString("colm_addname"));
				m.setColm_stargivemonth(rs.getInt("colm_stargivemonth"));
				m.setColm_endgivemonth(rs.getInt("colm_endgivemonth"));
				m.setColm_giveonemonth(rs.getInt("colm_giveonemonth"));
				m.setColm_enjoytype(rs.getString("colm_enjoytype"));
				if (rs.getInt("colm_selectid") == 1) {
					m.setInt11(rs.getInt("colm_stargivemonth"));
					m.setInt12(rs.getInt("colm_giveonemonth"));
				}
				if (rs.getInt("colm_selectid") == 2) {
					m.setInt21(rs.getInt("colm_fee"));
					m.setInt22(rs.getInt("colm_stargivemonth"));
					m.setInt23(rs.getInt("colm_giveonemonth"));
				}
				if (rs.getInt("colm_selectid") == 3) {
					m.setInt31(rs.getInt("colm_startmonth"));
					m.setInt32(rs.getInt("colm_stargivemonth"));
					m.setInt33(rs.getInt("colm_giveonemonth"));
				}
				if (rs.getInt("colm_selectid") == 4) {
					m.setInt41(rs.getInt("colm_startmonth"));
					m.setInt42(rs.getInt("colm_fee"));
					m.setInt43(rs.getInt("colm_stargivemonth"));
					m.setInt44(rs.getInt("colm_giveonemonth"));
				}
				if (rs.getInt("colm_selectid") == 5) {
					if (jk == 0) {
						m.setInt51(rs.getInt("colm_stopmonth"));
						m.setInt52(rs.getInt("colm_fee"));
						m.setInt53(rs.getInt("colm_stargivemonth"));
						m.setInt54(rs.getInt("colm_giveonemonth"));
					} else {
						m.setInt55(rs.getInt("colm_startmonth"));
						m.setInt56(rs.getInt("colm_fee"));
						m.setInt57(rs.getInt("colm_stargivemonth"));
						m.setInt58(rs.getInt("colm_giveonemonth"));
					}
				}
				m.setColm_selectid(rs.getInt("colm_selectid"));
				list.add(m);
				jk = jk + 1;
			}
		} catch (Exception e) {
			System.out.print("错误：" + e.toString());
		}
		return list;
	}

	public int CoOfferListUpdate(CoOfferListModel model) throws SQLException {
		dbconn db = new dbconn();
		int row = 0;

		try {

			row = Integer
					.valueOf(db
							.callWithReturn(
									"{?=call CoOfferListUpdate_P_ply(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)}",
									Types.INTEGER, model.getColi_id(),
									model.getColi_standard(),
									model.getColi_remark(),
									model.getColi_amount(),
									model.getColi_fee(),
									model.getColi_cpfc_name(),
									model.getColi_group_id(),
									model.getColi_group_count(),
									model.getColi_feetype(),
									model.getColi_flpaykind(),
									model.getColi_rspaykind(),
									model.getColi_rsinvoice(),
									model.getColi_hjpaykind(),
									model.getColi_hjinvoice(),
									model.getColi_sendmonth(),
									model.getColi_around(),
									model.getColi_feeDiscount()).toString());

		} catch (Exception e) {
			e.printStackTrace();
		}
		return row;
	}

	public int getTemNum(int coof_id) throws SQLException {
		ResultSet rs = null;
		dbconn db = new dbconn();
		int row = 0;
		String sql = "select count(*) num from CoOfferListTem where coli_coof_id="
				+ coof_id;

		try {

			rs = db.GRS(sql);
			while (rs.next()) {
				row = rs.getInt("num");
			}

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			db.Close();
		}
		return row;
	}

	public int CoOfferSubmit(CoOfferModel m) throws SQLException {
		CallableStatement csmt = null;
		dbconn db = new dbconn();
		int row = 0;

		// 创建存储过程的对象
		csmt = db.getcall("CoOfferSubmit_P_ply(?,?,?)");

		try {
			// 给存储过程的参数设置值
			csmt.setInt(1, m.getCoof_id());
			csmt.setBigDecimal(2, m.getCoof_sum());

			csmt.registerOutParameter(3, java.sql.Types.INTEGER);

			// 执行存储过程
			csmt.execute();

			row = csmt.getInt(3);

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			db.Close();
		}
		return row;
	}

	public int CoOfferListTemModState(int coof_id, int coli_state)
			throws Exception {
		PreparedStatement psmt = null;
		dbconn db = new dbconn();
		int row = 0;
		String sql = "update coofferlist set coli_state=? "
				+ "where coli_coof_id=? and coli_copr_id<>0";

		try {
			psmt = db.getpre(sql);
			psmt.setInt(1, coli_state);
			psmt.setInt(2, coof_id);

			row = psmt.executeUpdate();
		} catch (Exception e) {
			// TODO: handle exception
		} finally {
			db.Close();
		}

		return row;
	}

	public int CoOfferListTemDelete(int coof_id) throws Exception {
		PreparedStatement psmt = null;
		dbconn db = new dbconn();
		int row = 0;
		String sql = "delete from coofferlist where coli_coof_id=? and coli_state=0";

		try {
			psmt = db.getpre(sql);
			psmt.setInt(1, coof_id);

			row = psmt.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			db.Close();
		}

		return row;
	}

	public Integer CoOfferListNoPclassDelete(Integer coli_coof_id) {
		PreparedStatement psmt = null;
		dbconn db = new dbconn();
		int row = 0;
		String sql = "delete from CoOfferList where coli_id in(select a.coli_id from CoOfferList a,"
				+ " CoOfferList b where a.coli_id=b.coli_parid and a.coli_coof_id=?"
				+ " group by a.coli_id,a.coli_name having COUNT(*)<=1) or coli_parid in("
				+ " select a.coli_id from CoOfferList a,CoOfferList b where a.coli_id=b.coli_parid"
				+ " and a.coli_coof_id=? group by a.coli_id,a.coli_name having COUNT(*)<=1)";

		try {
			psmt = db.getpre(sql);
			psmt.setInt(1, coli_coof_id);
			psmt.setInt(2, coli_coof_id);

			row = psmt.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
		}

		return row;
	}

	public int CoOfferListTemfwfDelete(int coof_id) throws Exception {
		PreparedStatement psmt = null;
		dbconn db = new dbconn();
		int row = 0;
		String sql = "delete from coofferlist where coli_coof_id=? "
				+ "and coli_isfwf=1 and coli_fee=0";

		try {
			psmt = db.getpre(sql);
			psmt.setInt(1, coof_id);

			row = psmt.executeUpdate();
		} catch (Exception e) {
			// TODO: handle exception
		} finally {
			db.Close();
		}

		return row;
	}

	public void ClearCoOffer() throws SQLException {
		CallableStatement csmt = null;
		dbconn db = new dbconn();

		// 创建存储过程的对象
		csmt = db.getcall("ClearCoOffer");

		try {
			// 执行存储过程
			csmt.execute();

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			db.Close();
		}
	}

	public List<CoPclassModel> getclassList(String str) throws SQLException {
		dbconn db = new dbconn();
		ResultSet rs = null;
		List<CoPclassModel> list = new ArrayList<CoPclassModel>();
		String sqlstr = "select copc_id,copc_name,copc_type1 from CoPclass "
				+ "where copc_name is not null and copc_name<>''";

		try {
			rs = db.GRS(sqlstr);
			while (rs.next()) {
				CoPclassModel model = new CoPclassModel();
				model.setCopc_id(rs.getInt("copc_id"));
				model.setCopc_name(rs.getString("copc_name"));
				model.setCopc_type1(rs.getString("copc_type1"));
				list.add(model);
			}
		} catch (Exception e) {
			System.out.print(e.toString());
		} finally {
			db.Close();
		}
		return list;
	}

	public List<CoOfferListModel> getSpanList(Integer coli_id) {
		List<CoOfferListModel> list = new ArrayList<>();
		dbconn db = new dbconn();
		String sql = "select coli_id,coli_coof_id,coli_copr_id,coli_name,coli_content,coli_pclass,"
				+ "coli_account,coli_standard,coli_gather,coli_remark,coli_addname,coli_addtime,"
				+ "coli_starttime,coli_stoptime,coli_modname,coli_modtime,coli_feetype,coli_amount,"
				+ "coli_state,coli_city,coli_cpfc_name,coli_coco_id,coli_parid,"
				+ "coli_isfwf,coli_group_id,coli_group_count,"
				+ "cast(coli_fee as float) coli_fee "
				+ "from CoOfferList where coli_pclass=(select coli_name from CoOfferList where coli_id="
				+ coli_id
				+ ") and coli_coof_id=(select coli_coof_id from CoOfferList where coli_id="
				+ coli_id + ") and coli_group_count=1";

		try {
			list = db.find(sql, CoOfferListModel.class,
					dbconn.parseSmap(CoOfferListModel.class));
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}

	// 添加享受条件
	public Integer CoOLModeAdd(CoOLModeModel m, Integer ty) {
		dbconn db = new dbconn();
		try {
			CallableStatement c = db
					.getcall("CoOLMode_Add_cyj(?,?,?,?,?,?,?,?,?,?,?,?,?)");
			c.setInt(1, m.getColm_coli_id());
			c.setString(2, m.getColm_name());
			if (m.getColm_startmonth() == null
					|| m.getColm_startmonth().equals("")) {
				c.setInt(3, 0);
			} else {
				c.setInt(3, m.getColm_startmonth());
			}
			if (m.getColm_stopmonth() == null
					|| m.getColm_stopmonth().equals("")) {
				c.setInt(4, 0);
			} else {
				c.setInt(4, m.getColm_stopmonth());
			}
			c.setBigDecimal(5, m.getColm_fee());
			c.setString(6, m.getColm_type());
			c.setString(7, m.getColm_addname());
			if (m.getColm_stargivemonth() == null
					|| m.getColm_stargivemonth().equals("")) {
				c.setInt(8, 0);
			} else {
				c.setInt(8, m.getColm_stargivemonth());
			}
			if (m.getColm_giveonemonth() == null
					|| m.getColm_giveonemonth().equals("")) {
				c.setInt(9, 0);
			} else {
				c.setInt(9, m.getColm_giveonemonth());
			}
			// c.setInt(10, m.getColm_endgivemonth());
			c.setString(10, m.getColm_enjoytype());
			if (m.getColm_selectid() == null || m.getColm_selectid().equals("")) {
				c.setInt(11, 0);
			} else {
				c.setInt(11, m.getColm_selectid());
			}
			c.setInt(12, ty);
			c.registerOutParameter(13, java.sql.Types.INTEGER);
			c.execute();
			return c.getInt(13);
		} catch (SQLException e) {
			System.out.println("数据库操作错误：" + e.getMessage());
			return 0;
		}
	}

	// 添加享受条件
	public Integer CoOLModeupdate(CoOLModeModel m, Integer f) {
		dbconn db = new dbconn();
		/*
		 * try {
		 * 
		 * CallableStatement c = db
		 * .getcall("CoOLMode_update_cyj(?,?,?,?,?,?,?,?,?,?,?,?,?,?)");
		 * c.setInt(1, m.getColm_coli_id()); c.setString(2, m.getColm_name());
		 * c.setInt(3, m.getColm_startmonth()); c.setInt(4,
		 * m.getColm_stopmonth()); c.setBigDecimal(5, m.getColm_fee());
		 * c.setString(6, m.getColm_type()); c.setString(7,
		 * m.getColm_addname()); c.setInt(8, m.getColm_stargivemonth());
		 * c.setInt(9, m.getColm_giveonemonth()); // c.setInt(10,
		 * m.getColm_endgivemonth()); c.setString(10, m.getColm_enjoytype());
		 * c.setInt(11, m.getColm_selectid()); c.setInt(12, f); c.setInt(13,
		 * m.getColm_id()); c.registerOutParameter(14, java.sql.Types.INTEGER);
		 * c.execute(); return c.getInt(14);
		 * 
		 * } catch (SQLException e) { System.out.println("数据库操作错误：" +
		 * e.getMessage()); return 0; }
		 */
		Integer i = 0;
		try {
			i = (Integer) db.callWithReturn(
					"{?=call CoOLMode_update_cyj(?,?,?,?,?,?,?,?,?,?,?,?,?)}",
					Types.INTEGER, m.getColm_coli_id(), m.getColm_name(),
					m.getColm_startmonth(), m.getColm_stopmonth(),
					m.getColm_fee(), m.getColm_type(), m.getColm_addname(),
					m.getColm_stargivemonth(), m.getColm_giveonemonth(),
					m.getColm_enjoytype(), m.getColm_selectid(), f,
					m.getColm_id());
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return i;
	}

	// 读取历史报价单
	public List<CoOfferModel> getcoofList(Integer cola_id, Integer cid) {
		List<CoOfferModel> list = new ArrayList<>();
		dbconn db = new dbconn();
		String sql = "";
		if (cid == null) {
			sql = "select coof_id,coof_name,coof_cpct_id,coof_quotemode,coof_quotetime,coof_auditing_name,coof_auditing_time,coof_state,coof_filename,coof_addname,coof_addtime,coof_modname,coof_modtime,coof_remark,coof_tali_id,coof_register,coof_cola_id,cid,coof_coco_id,coof_min,coof_max,coof_gm,coof_sum from CoOffer"
					+ " where coof_cola_id="
					+ cola_id
					+ " and coof_state>0  order by coof_id desc";

		} else {
			if (cid > 0) {
				sql = "select coof_id,coof_name,coof_cpct_id,coof_quotemode,coof_quotetime,coof_auditing_name,coof_auditing_time,coof_state,coof_filename,coof_addname,coof_addtime,coof_modname,coof_modtime,coof_remark,coof_tali_id,coof_register,coof_cola_id,cid,coof_coco_id,coof_min,coof_max,coof_gm,coof_sum from CoOffer"
						+ " where cid="
						+ cid
						+ "  and coof_state>0   order by coof_id desc";

			}
		}

		try {
			list = db.find(sql, CoOfferModel.class,
					dbconn.parseSmap(CoOfferModel.class));
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}

	public List<CoOfferModel> getcoofListcoofid(Integer coof_id, Integer cid) {
		List<CoOfferModel> list = new ArrayList<>();
		dbconn db = new dbconn();
		String sql = "";
		if (cid == null) {
			sql = "select coof_id,coof_name,coof_cpct_id,coof_quotemode,coof_quotetime,coof_auditing_name,coof_auditing_time,coof_state,coof_filename,coof_addname,coof_addtime,coof_modname,coof_modtime,coof_remark,coof_tali_id,coof_register,coof_cola_id,cid,coof_coco_id,coof_min,coof_max,coof_gm,coof_sum from CoOffer"
					+ " where coof_id=" + coof_id
					// + " and coof_state in(1,3,4,5) order by coof_id desc";
					+ "   order by coof_id desc";
		} else {
			if (cid > 0) {
				sql = "select coof_id,coof_name,coof_cpct_id,coof_quotemode,coof_quotetime,coof_auditing_name,coof_auditing_time,coof_state,coof_filename,coof_addname,coof_addtime,coof_modname,coof_modtime,coof_remark,coof_tali_id,coof_register,coof_cola_id,cid,coof_coco_id,coof_min,coof_max,coof_gm,coof_sum from CoOffer"
						+ " where cid=" + cid
						// +
						// " and coof_state in(1,3,4,5) order by coof_id desc";
						+ " and coof_state <>0   order by coof_id desc";
			}
		}

		try {
			list = db.find(sql, CoOfferModel.class,
					dbconn.parseSmap(CoOfferModel.class));
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}

	// 读取退回
	public List<CoOfferModel> getcoofListcoofidth(Integer coof_id, Integer cid) {
		List<CoOfferModel> list = new ArrayList<>();
		dbconn db = new dbconn();
		String sql = "";
		if (cid == null) {
			sql = "select coof_id,coof_name,coof_cpct_id,coof_quotemode,coof_quotetime,coof_auditing_name,coof_auditing_time,coof_state,coof_filename,coof_addname,coof_addtime,coof_modname,coof_modtime,coof_remark,coof_tali_id,coof_register,coof_cola_id,cid,coof_coco_id,coof_min,coof_max,coof_gm,coof_sum from CoOffer"
					+ " where coof_id=" + coof_id
					// + " and coof_state in(1,3,4,5) order by coof_id desc";
					+ "   order by coof_id desc";
		} else {
			if (cid > 0) {
				sql = "select coof_id,coof_name,coof_cpct_id,coof_quotemode,coof_quotetime,coof_auditing_name,coof_auditing_time,coof_state,coof_filename,coof_addname,coof_addtime,coof_modname,coof_modtime,coof_remark,coof_tali_id,coof_register,coof_cola_id,cid,coof_coco_id,coof_min,coof_max,coof_gm,coof_sum from CoOffer"
						+ " where cid=" + cid
						// +
						// " and coof_state in(1,3,4,5) order by coof_id desc";
						+ "    order by coof_id desc";
			}
		}

		try {
			list = db.find(sql, CoOfferModel.class,
					dbconn.parseSmap(CoOfferModel.class));
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}

	public Integer NextDataHandle(Integer new_coof_id, Integer old_coof_id) {
		dbconn db = new dbconn();
		Integer row = 0;

		try {
			row = Integer.parseInt(db.callWithReturn(
					"{?=call CoOfferNextDataHandle_P_ply(?,?)}", Types.INTEGER,
					new_coof_id, old_coof_id).toString());
		} catch (Exception e) {
			e.printStackTrace();
		}

		return row;
	}

	// 单独添加项目
	public Integer coofferlistAdd(CoOfferListModel cm) {
		Integer i = 0;
		dbconn db = new dbconn();

		try {
			i = (Integer) db
					.callWithReturn(
							"{?=call CoOfferListAdd_P_py(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)}",
							Types.INTEGER, cm.getCoof_id(), cm
									.getColi_copr_id(), cm.getColi_name(), cm
									.getColi_content(), cm.getColi_standard(),
							cm.getColi_remark(), cm.getColi_addname(), cm
									.getColi_amount(), cm.getColi_discount(),
							cm.getColi_fee(), cm.getColi_cpfc_name(), cm
									.getColi_coco_id(), cm.getColi_isfwf(), cm
									.getCoof_id(), cm.getCoolmodel()
									.getColm_name(), cm.getCoolmodel()
									.getInt51(), cm.getCoolmodel().getInt55(),
							cm.getCoolmodel().getInt52(), cm.getCoolmodel()
									.getInt56(), cm.getCoolmodel()
									.getColm_type(), cm.getCoolmodel()
									.getColm_selectid(), cm.getCoolmodel()
									.getInt53(), cm.getCoolmodel().getInt57(),
							cm.getCoolmodel().getInt54(), cm.getCoolmodel()
									.getInt58(), cm.getColi_state(), cm
									.getColi_flpaykind(), cm
									.getColi_rspaykind(), cm
									.getColi_rsinvoice(), cm
									.getColi_hjpaykind(), cm
									.getColi_hjinvoice(),cm.getColi_sendmonth(),
									cm.getColi_around(),
									cm.getColi_feeDiscount());
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return i;
	}

	public CopCompactModel getCompactByCocoid(Integer coco_id) {
		dbconn db = new dbconn();
		List<CopCompactModel> list = new ArrayList<>();
		String sql = "select cpct_id,cpct_shortname,cpct_name from CopCompact"
				+ " where cpct_shortname=(select top 1 coco_compacttype from CoCompact where coco_id=?)"
				+ " and cpct_state=1  order by cpct_id ";

		try {
			list = db.find(sql, CopCompactModel.class, null, coco_id);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list.size() > 0 ? list.get(0) : null;
	}
}
