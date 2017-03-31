package dal.CoProduct;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.zkoss.zul.ListModelList;

import Conn.dbconn;
import Model.CoAgencyBaseModel;
import Model.CoPclassModel;
import Model.CoProductModel;
import Model.CopCompactModel;
import Util.plyUtil;

public class cpd_List1Dal {

	public List<CoProductModel> getcoproduct(String str) throws SQLException {
		List<CoProductModel> list = new ListModelList<CoProductModel>();
		dbconn db = new dbconn();
		ResultSet rs = null;
		String sqlstr = "select distinct copr_id,copr_name,copr_content,copr_type,cabc_ppc_id,cabc_coab_id,"
				+ "statename,copc_name,cpac_name,copr_addname,copr_addtime,copr_state,name,coab_name,"
				+ "(select count(*) from CoPFeeRelation where cpfr_copr_id=a.Copr_id and cpfr_state=1) cpfrcount"
				+ " from View_CoProduct a where 1=1 "
				+ str
				+ " order by copr_id desc";

		try {
			rs = db.GRS(sqlstr);
			while (rs.next()) {
				CoProductModel cop = new CoProductModel();
				cop.setId(rs.getInt("cabc_ppc_id"));
				cop.setCoab_id(rs.getInt("cabc_coab_id"));
				cop.setCopr_id(rs.getInt("copr_id"));
				cop.setCopr_name(rs.getString("copr_name"));
				cop.setCopr_content(rs.getString("copr_content"));
				cop.setCopr_content1(plyUtil.substr(
						rs.getString("copr_content"), 10));
				cop.setCopc_name(rs.getString("copc_name"));
				cop.setCpac_name(rs.getString("cpac_name"));
				cop.setCopr_addname(rs.getString("copr_addname"));
				cop.setCopr_addtime(rs.getDate("copr_addtime"));
				cop.setCopr_state(rs.getInt("copr_state"));
				cop.setCpfrcount(rs.getInt("cpfrcount"));
				cop.setName(rs.getString("name"));
				cop.setCoab_name(rs.getString("coab_name"));
				cop.setStatename(rs.getString("statename"));
				cop.setCopr_type(rs.getString("copr_type"));
				list.add(cop);
			}

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			db.Close();
		}

		return list;
	}

	/**
	 * @Title: getcoproductList
	 * @Description: TODO(查询产品信息)
	 * @param typeId
	 *            产品类型ID
	 * @param name
	 *            产品名称
	 * @return
	 * @return List<CoProductModel> 返回类型
	 * @throws
	 */
	public List<CoProductModel> getcoproductList(String typeId, String name,
			String buId) {
		List<CoProductModel> list = new ListModelList<>();
		dbconn db = new dbconn();
		StringBuilder sql = new StringBuilder();
		sql.append("select copr_id,copr_copc_id,copc_name,copr_name,copr_emce_id,emce_menuname"
				+ " from CoProduct a "
				+ "inner join CoPclass b on a.copr_copc_id=b.Copc_id "
				+ "left join EmbaseBusinessCenter c on a.copr_emce_id=c.emce_id "
				+ "where copr_state=2 and Copc_id like ? and copr_name like ?");
		if (!buId.equals("")) {
			sql.append(" and copr_emce_id =" + buId);
		}

		System.out.println(sql);
		try {
			list = db.find(sql.toString(), CoProductModel.class, null, typeId,
					name);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return list;
	}


	
	public List<CoProductModel> getCoproductList(String name) {
		List<CoProductModel> list = new ListModelList<>();
		dbconn db = new dbconn();
		String sql = "select copr_id,copr_name,name,coab_name from View_CoProduct where copr_state=2 and copr_copc_id=6";
		if (name != null && !name.equals("")) {
			sql = sql + " and copr_name like '%" + name + "%'";
		}
		sql = sql + " order by name,coab_name,copr_name";
		try {
			list = db.find(sql, CoProductModel.class, null);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return list;
	}

	// 排除已关联产品列表
	public List<CoProductModel> getListExceptById(Integer id, String name) {
		List<CoProductModel> list = new ListModelList<>();
		dbconn db = new dbconn();
		String sql = "select copr_id,copr_name,name,coab_name "
				+ "from View_CoProduct where copr_state=2 and copr_copc_id=6 and copr_embe_id=?";
		if (name != null && !name.equals("")) {
			sql = sql + " and copr_name like '%" + name + "%'";
		}
		sql = sql + " order by name,coab_name,copr_name";

		try {
			System.out.println(sql);
			list = db.find(sql, CoProductModel.class, null, id);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return list;
	}

	public List<CoProductModel> getListByEmbfId(Integer id) {
		List<CoProductModel> list = new ListModelList<>();
		dbconn db = new dbconn();
		String sql = "select distinct copr_id,copr_name,name,coab_name "
				+ "from View_CoProduct where copr_state=2 and copr_copc_id=6 and copr_embe_id=?"
				+ " order by name,coab_name,copr_name";
		try {
			list = db.find(sql, CoProductModel.class, null, id);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return list;
	}

	public List<CoPclassModel> getCoproductTypeList() {
		List<CoPclassModel> list = new ListModelList<>();
		dbconn db = new dbconn();
		String sql = "select copc_id,copc_name from CoPclass";
		try {
			list = db.find(sql, CoPclassModel.class, null);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return list;
	}

	public List<String> getaddnameList() throws SQLException {
		ResultSet rs = null;
		dbconn db = new dbconn();
		List<String> list = new ArrayList<String>();
		String sqlstr = "select '' as copr_addname union select copr_addname from coproduct "
				+ "where copr_addname is not null and copr_addname<>'' and copr_state=2";

		try {
			rs = db.GRS(sqlstr);
			while (rs.next()) {
				list.add(rs.getString("copr_addname"));
			}
		} catch (Exception e) {
			System.out.print(e.toString());
		} finally {
			db.Close();
		}
		return list;
	}

	// 合计未关联福利项目的产品数
	public Integer getCoprTotalByEmbfId() {
		Integer i = 0;
		dbconn db = new dbconn();
		String sql = "select count(*)row from coproduct where copr_state=2 and copr_copc_id=6 and copr_embe_id=0";
		try {
			i = db.find(sql, CoProductModel.class, null).get(0).getRow();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return i;

	}

	public List getStardBycoprid(int copr_id) throws SQLException {
		ResultSet rs = null;
		dbconn db = new dbconn();
		List list = new ArrayList();
		String sqlstr = "select cpst_name from CoProduct a,CoPStRelation b,CoPStandard c "
				+ "where a.Copr_id=b.cpsr_copr_id and b.cpsr_cpst_id=c.cpst_id and cpsr_state=1 and Copr_id="
				+ copr_id;

		try {
			rs = db.GRS(sqlstr);
			while (rs.next()) {
				list.add(rs.getString("cpst_name"));
			}
		} catch (Exception e) {
			System.out.print(e.toString());
		} finally {
			db.Close();
		}
		return list;
	}

	public List getCityBycoprid(int copr_id) throws SQLException {
		ResultSet rs = null;
		dbconn db = new dbconn();
		List list = new ArrayList();
		String sqlstr = "select cpcr_city from CoProduct a,CoPCityRelation b "
				+ "where a.Copr_id=b.cpcr_copr_id and cpcr_state=1 and Copr_id="
				+ copr_id;

		try {
			rs = db.GRS(sqlstr);
			while (rs.next()) {
				list.add(rs.getString("cpcr_city"));
			}
		} catch (Exception e) {
			System.out.print(e.toString());
		} finally {
			db.Close();
		}
		return list;
	}

	public List<CoProductModel> getEclassBycoprid(int copr_id, String city)
			throws SQLException {
		ResultSet rs = null;
		dbconn db = new dbconn();
		List<CoProductModel> list = new ArrayList<CoProductModel>();
		String sqlstr = "select cpfr_fee,cpfr_cpfc_id,cpfr_addtime,cpfr_addname,"
				+ "cpfc_unit+'/'+cpfc_types+'/'+cpfc_chronon as cpfc_name,cpfr_lock"
				+ " from CoPFeeRelation a left outer join CoPFeeclass b"
				+ " on a.cpfr_cpfc_id=b.cpfc_id and cpfr_state=1 "
				+ " where cpfr_copr_id=" + copr_id;

		try {
			rs = db.GRS(sqlstr);
			while (rs.next()) {
				CoProductModel cpmol = new CoProductModel();
				cpmol.setCpfr_cpfc_id(rs.getInt("cpfr_cpfc_id"));
				cpmol.setFee(rs.getBigDecimal("cpfr_fee"));
				cpmol.setCopr_addtime(rs.getDate("cpfr_addtime"));
				cpmol.setCopr_addname(rs.getString("cpfr_addname"));
				cpmol.setCpfc_name(rs.getString("cpfc_name"));
				cpmol.setCpfr_lock1(rs.getInt("cpfr_lock") == 1 ? true : false);
				list.add(cpmol);
			}
		} catch (Exception e) {
			System.out.print(e.toString());
		} finally {
			db.Close();
		}
		return list;
	}

	public List<CoProductModel> getEclassBycoprid(int copr_id)
			throws SQLException {
		ResultSet rs = null;
		dbconn db = new dbconn();
		List<CoProductModel> list = new ArrayList<CoProductModel>();
		String sqlstr = "select cpfr_fee,cpfr_cpfc_id,cpfr_addtime,cpfr_addname,"
				+ "cpfc_unit+'/'+cpfc_types+'/'+cpfc_chronon as cpfc_name,"
				+ "case cpfr_lock when 1 then '锁定' else '不锁定' end as lock,cpfr_lock"
				+ " from CoPFeeRelation a inner join CoPFeeclass b on a.cpfr_cpfc_id=b.cpfc_id"
				+ " where cpfr_state=1 and cpfr_copr_id="
				+ copr_id
				+ " order by cpfr_cpfc_id";

		try {
			rs = db.GRS(sqlstr);
			while (rs.next()) {
				CoProductModel cpmol = new CoProductModel();
				cpmol.setCpfr_cpfc_id(rs.getInt("cpfr_cpfc_id"));
				cpmol.setFee(rs.getBigDecimal("cpfr_fee"));
				cpmol.setCopr_addtime(rs.getDate("cpfr_addtime"));
				cpmol.setCopr_addname(rs.getString("cpfr_addname"));
				cpmol.setCpfc_name(rs.getString("cpfc_name"));
				cpmol.setLock(rs.getString("lock"));
				cpmol.setCpfr_lock(rs.getInt("cpfr_lock"));
				list.add(cpmol);
			}
		} catch (Exception e) {
			System.out.print(e.toString());
		} finally {
			db.Close();
		}
		return list;
	}

	public List<CoProductModel> getSingleEclassBycoprid(int copr_id, int cpfc_id)
			throws SQLException {
		ResultSet rs = null;
		dbconn db = new dbconn();
		List<CoProductModel> list = new ArrayList<CoProductModel>();
		String sqlstr = "select cpfr_fee,cpfr_lock from CoProduct a,CoPFeeRelation b "
				+ "where a.Copr_id=b.cpfr_copr_id and cpfr_state=1 "
				+ "and Copr_id=" + copr_id + " and cpfr_cpfc_id=" + cpfc_id;

		try {
			rs = db.GRS(sqlstr);
			while (rs.next()) {
				CoProductModel cpmol = new CoProductModel();
				cpmol.setFee(rs.getBigDecimal("cpfr_fee"));
				cpmol.setCpfr_lock(rs.getInt("cpfr_lock"));
				list.add(cpmol);
			}
		} catch (Exception e) {
			System.out.print(e.toString());
		} finally {
			db.Close();
		}
		return list;
	}

	public int delete(int copr_id) throws SQLException {
		deletere(copr_id);
		int row = 0;
		dbconn db = new dbconn();
		String sqlstr = "update coproduct set copr_state=0 where copr_id=?";
		PreparedStatement psmt = db.getpre(sqlstr);

		try {
			psmt.setInt(1, copr_id);

			row = psmt.executeUpdate();
		} catch (Exception e) {
			System.out.print(e.toString());
		} finally {
			db.Close();
		}
		return row;
		
		
	}
	
	//启用产品
	public int redelete(int copr_id,int copr_id1) throws SQLException {
		int row = 0;
		dbconn db = new dbconn();
		String sqlstr = "update coproduct set copr_state=(select COUNT(copr_cpac_id)+1 from coproduct " +
				"where Copr_id=?) where copr_id=?  ";
		//String sqlstr1=" delete CopComRel  where cpcr_copr_id=? ";
		PreparedStatement psmt = db.getpre(sqlstr);

		try {
			psmt.setInt(1, copr_id);
			psmt.setInt(2, copr_id1);

			row = psmt.executeUpdate();
		} catch (Exception e) {
			System.out.print(e.toString());
		} finally {
			db.Close();
		}
		return row;
	}
	
	//删除产品与合同关系表数据
	public int deletere(int copr_id) throws SQLException {
		int row = 0;
		dbconn db = new dbconn();
		String sqlstr = "delete CopComRel  where cpcr_copr_id=?  ";
		
		PreparedStatement psmt = db.getpre(sqlstr);

		try {
			psmt.setInt(1, copr_id);

			row = psmt.executeUpdate();
		} catch (Exception e) {
			System.out.print(e.toString());
		} finally {
			db.Close();
		}
		return row;
	}


	public Integer updateCopcompact(Integer id, Integer state) {
		dbconn db = new dbconn();
		String sql = "update Copcompact set cpct_state=? where cpct_id=?";
		Integer i=0;
		System.out.println(sql);
		System.out.println(id);
		System.out.println(state);
		try {
			i = db.updatePrepareSQL(sql, state, id);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return i;
	}

	// 更新福利项目关联
	public Integer updateBFId(Integer beId, Integer coprId) {
		Integer i = 0;
		dbconn db = new dbconn();
		String sql = "update coproduct set copr_embe_id=? where copr_id=?";
		try {
			i = db.updatePrepareSQL(sql, beId, coprId);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return i;
	}

	// 清空福利项目关联
	public Integer clearBFId(Integer beId1, Integer beId2) {
		Integer i = 0;
		dbconn db = new dbconn();
		String sql = "update coproduct set copr_embe_id=? where copr_embe_id=?";
		try {
			i = db.updatePrepareSQL(sql, beId1, beId2);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return i;
	}

	public CoProductModel cpdmod(CoProductModel cpmol) throws SQLException {
		CallableStatement csmt = null;
		dbconn db = new dbconn();
		CoProductModel cpmol1 = new CoProductModel();

		// 创建存储过程的对象
		csmt = db.getcall("CoProductMod_P_ply(?,?,?,?,?,?)");

		try {
			// 给存储过程的参数设置值
			csmt.setInt(1, cpmol.getCopr_id());
			csmt.setString(2, cpmol.getCopr_content());
			csmt.setString(3, cpmol.getCpst_name());
			csmt.setInt(4, cpmol.getCopr_cabc_id());
			csmt.setString(5, cpmol.getCopr_addname());

			// 注册存储过程的返回值
			csmt.registerOutParameter(6, java.sql.Types.INTEGER);

			// 执行存储过程
			csmt.execute();

			// 获取返回值
			cpmol1.setRow(csmt.getInt(6));

		} catch (Exception e) {
			System.out.print(e.toString());
		} finally {
			db.Close();
		}

		return cpmol1;
	}

	public int delFeeRelation(int copr_id) throws SQLException {
		int row = 0;
		dbconn db = new dbconn();
		String sqlstr = "update CoPFeeRelation set cpfr_state=0 where cpfr_state=1 and cpfr_copr_id=?";
		PreparedStatement psmt = db.getpre(sqlstr);

		try {
			psmt.setInt(1, copr_id);

			row = psmt.executeUpdate();
		} catch (Exception e) {
			System.out.print(e.toString());
		} finally {
			db.Close();
		}
		return row;
	}

	/**
	 * @Title: updateCopr
	 * @Description: TODO(更新产品业务关联)
	 * @param coprId
	 *            产品ID
	 * @param emceId
	 *            业务ID
	 * @return
	 * @return Integer 返回类型
	 * @throws
	 */
	public Integer updateCopr(Integer coprId, Integer emceId) {
		Integer i = 0;
		dbconn db = new dbconn();
		String sql = "update CoProduct set copr_emce_id=? where copr_id=?";
		try {
			i = db.updatePrepareSQL(sql, emceId, coprId);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return i;
	}

	public List<CoProductModel> getCopCityList() {
		List<CoProductModel> list = new ArrayList<>();
		dbconn db = new dbconn();
		String sql = "select distinct id,name from View_CoProduct order by name";

		try {
			list = db.find(sql, CoProductModel.class,
					dbconn.parseSmap(CoProductModel.class));
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}

	public List<CoAgencyBaseModel> getcopcoalist() {
		List<CoAgencyBaseModel> list = new ArrayList<>();
		dbconn db = new dbconn();
		String sql = "select distinct id,coab_name from View_CoProduct order by coab_name";

		try {
			list = db.find(sql, CoAgencyBaseModel.class,
					dbconn.parseSmap(CoAgencyBaseModel.class));
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}

	public List<CopCompactModel> getCopCompactList(String str) {
		List<CopCompactModel> list = new ArrayList<>();
		dbconn db = new dbconn();
		String sql = "select cpct_id,cpct_shortname,cpct_name,cpct_state,cpct_addname,cpct_addtime,"
				+ "case when cpct_state=1 then '可用' when cpct_state=0 then '不可用' end as statename"
				+ " from copcompact where 1=1" + str + "order by cpct_id desc";

		try {
			list = db.find(sql, CopCompactModel.class,
					dbconn.parseSmap(CopCompactModel.class));
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}

	public List<CopCompactModel> getCopComRelList(Integer cpct_id) {
		List<CopCompactModel> list = new ArrayList<>();
		dbconn db = new dbconn();
		String sql = "select copr_id,copr_name,copr_content,copr_state,copr_stopname,copr_stoptime,"
				+ "copr_sort,copr_d1,copr_d2,copr_copc_id,copr_cpac_id,copr_addtime,copr_addname,"
				+ "copr_type,copr_cabc_id,copc_name,cpac_name,name city,coab_name"
				+ " from copcomrel a inner join View_CoProduct b"
				+ " on a.cpcr_copr_id=b.Copr_id inner join copcompact c on a.cpcr_cpct_id=c.cpct_id"
				+ " where cpcr_state=1 and cpcr_cpct_id=?";

		try {
			list = db.find(sql, CopCompactModel.class,
					dbconn.parseSmap(CopCompactModel.class), cpct_id);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}

	// 查询机构的服务产品
	public List<CoProductModel> getAgProduct(int cabc_id) {
		List<CoProductModel> list = new ArrayList<CoProductModel>();
		CoProductModel cop;
		String sql = "select distinct copr_id,copr_name,copr_content,copr_type,cabc_ppc_id,cabc_coab_id,statename,copc_name,cpac_name,copr_addname,copr_addtime,copr_state,name,coab_name,(select count(*) from CoPFeeRelation where cpfr_copr_id=a.Copr_id and cpfr_state=1) cpfrcount from View_CoProduct a where copr_cabc_id=? order by copr_id desc";
		try {
			dbconn conn = new dbconn();
			PreparedStatement psmt = conn.getpre(sql);
			psmt.setInt(1, cabc_id);
			ResultSet rs = psmt.executeQuery();
			while (rs.next()) {
				cop = new CoProductModel();
				cop.setId(rs.getInt("cabc_ppc_id"));
				cop.setCoab_id(rs.getInt("cabc_coab_id"));
				cop.setCopr_id(rs.getInt("copr_id"));
				cop.setCopr_name(rs.getString("copr_name"));
				cop.setCopr_content(rs.getString("copr_content"));
				cop.setCopr_content1(plyUtil.substr(
						rs.getString("copr_content"), 10));
				cop.setCopc_name(rs.getString("copc_name"));
				cop.setCpac_name(rs.getString("cpac_name"));
				cop.setCopr_addname(rs.getString("copr_addname"));
				cop.setCopr_addtime(rs.getDate("copr_addtime"));
				cop.setCopr_state(rs.getInt("copr_state"));
				cop.setCpfrcount(rs.getInt("cpfrcount"));
				cop.setName(rs.getString("name"));
				cop.setCoab_name(rs.getString("coab_name"));
				cop.setStatename(rs.getString("statename"));
				cop.setCopr_type(rs.getString("copr_type"));
				list.add(cop);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}
}
