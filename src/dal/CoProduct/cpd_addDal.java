package dal.CoProduct;

import java.math.BigDecimal;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.ArrayList;
import java.util.List;

import org.zkoss.zul.ListModelList;

import Conn.dbconn;
import Model.CoAgencyBaseCityRelViewModel;
import Model.CoPAccountModel;
import Model.CoPFeeclassModel;
import Model.CoPclassModel;
import Model.CoProductModel;
import Model.CopCompactModel;
import Model.PubProCityModel;
import Model.PubStateModel;
import Util.UserInfo;

public class cpd_addDal {

	public List<String> getStandardList() throws SQLException {
		dbconn db = new dbconn();
		ResultSet rs = null;
		List<String> list = new ArrayList<String>();
		String sqlstr = "select cpst_id,cpst_name from CoPStandard";

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

	public List<String> getStandardList1(Integer copc_id) {
		dbconn db = new dbconn();
		ResultSet rs = null;
		List<String> list = new ArrayList<String>();
		String sqlstr = "select cpst_name from copclastarel a inner join copclass b on a.ccsr_copc_id=b.Copc_id"
				+ " inner join CoPStandard c on a.ccsr_cpst_id=c.cpst_id where copc_id="
				+ copc_id + " order by ccsr_cpst_id";

		try {
			rs = db.GRS(sqlstr);
			while (rs.next()) {
				list.add(rs.getString("cpst_name"));
			}
		} catch (Exception e) {
			System.out.print(e.toString());
		}
		return list;
	}

	public List<CoPclassModel> getclassList() throws SQLException {
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
	
	//获取产品默认价额及默认收费类型
	public List<CoProductModel> getfeeClass(Integer coprid,Integer coabId){
		List<CoProductModel> list = new ListModelList<>();
		dbconn db = new dbconn();
		String sql ="select cpfc_id,cpfc_unit+'/'+cpfc_chronon+'/'+cpfc_types as cpfc_name,cpfr_fee,cpfr_lock,cpfr_lock cpfr_lock1,cpfr_fee fee,cpfr_state ifU " +
				"from CoPFeeclass a " +
				"left join (" +
				"select * from CoPFeeRelation b " +
				"inner join CoProduct c on b.cpfr_copr_id=c.Copr_id " +
				"where cpfr_state=1 and copr_cabc_id=? and cpfr_copr_id=?) b on a.cpfc_id=b.cpfr_cpfc_id " +
				"order by a.cpfc_id";
		System.out.println(sql);
		System.out.println(coabId);
		System.out.println(coprid);
		
		try {
			list=db.find(sql, CoProductModel.class, null, coabId,coprid);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return list;
	}

	public List<CoPAccountModel> getaccountList() throws SQLException {
		dbconn db = new dbconn();
		ResultSet rs = null;
		List<CoPAccountModel> list = new ArrayList<CoPAccountModel>();
		String sqlstr = "select 0 as cpac_id,'' as cpac_name union "
				+ "select cpac_id,cpac_name from CoPAccount "
				+ "where cpac_name is not null and cpac_name <>''";

		try {
			rs = db.GRS(sqlstr);
			while (rs.next()) {
				CoPAccountModel model = new CoPAccountModel();
				model.setCpac_id(rs.getInt("cpac_id"));
				model.setCpac_name(rs.getString("cpac_name"));
				list.add(model);
			}
		} catch (Exception e) {
			System.out.print(e.toString());
		} finally {
			db.Close();
		}
		return list;
	}

	public List<PubProCityModel> getcityList() throws SQLException {
		dbconn db = new dbconn();
		ResultSet rs = null;
		List<PubProCityModel> list = new ArrayList<PubProCityModel>();
		String sqlstr = "select id,'('+spell+')'+name name,spell from PubProCity order by spell";

		try {
			rs = db.GRS(sqlstr);
			while (rs.next()) {
				PubProCityModel m = new PubProCityModel();
				m.setId(rs.getInt("id"));
				m.setName(rs.getString("name"));
				m.setSpell(rs.getString("spell"));
				list.add(m);
			}
		} catch (Exception e) {
			System.out.print(e.toString());
		} finally {
			db.Close();
		}
		return list;
	}

	public List<CoProductModel> geteclassList() throws SQLException {
		dbconn db = new dbconn();
		ResultSet rs = null;
		List<CoProductModel> list = new ArrayList<CoProductModel>();
		String sqlstr = "select cpfc_unit+'/'+cpfc_chronon+'/'+cpfc_types as cpfc_name,cpfc_id "
				+ "from CoPFeeclass";

		try {
			rs = db.GRS(sqlstr);
			while (rs.next()) {
				CoProductModel model = new CoProductModel();
				model.setCpfc_id(rs.getInt("cpfc_id"));
				model.setCpfc_name(rs.getString("cpfc_name"));
				model.setFee(BigDecimal.ZERO);
				list.add(model);
			}
		} catch (Exception e) {
			System.out.print(e.toString());
		} finally {
			db.Close();
		}
		return list;
	}
	
	
	public List<CoProductModel> geteclassListt1() throws SQLException {
		dbconn db = new dbconn();
		ResultSet rs = null;
		List<CoProductModel> list = new ArrayList<CoProductModel>();
		String sqlstr = "select  cpfc_unit+'/'+cpfc_chronon+'/'+cpfc_types as cpfc_name,cpfc_id "
				+ "from CoPFeeclass   ";

		try {
			rs = db.GRS(sqlstr);
			while (rs.next()) {
				CoProductModel model = new CoProductModel();
				model.setCpfc_id(rs.getInt("cpfc_id"));
				model.setCpfc_name(rs.getString("cpfc_name"));
				model.setFee(BigDecimal.ZERO);
				list.add(model);
			}
		} catch (Exception e) {
			System.out.print(e.toString());
		} finally {
			db.Close();
		}
		return list;
	}


	public List<CoPFeeclassModel> geteclassList1() throws SQLException {
		dbconn db = new dbconn();
		ResultSet rs = null;
		List<CoPFeeclassModel> list = new ArrayList<CoPFeeclassModel>();
		String sqlstr = "select cpfc_unit+'/'+cpfc_chronon+'/'+cpfc_types as cpfc_name,cpfc_id "
				+ "from CoPFeeclass";

		try {
			rs = db.GRS(sqlstr);
			while (rs.next()) {
				CoPFeeclassModel model = new CoPFeeclassModel();
				model.setCpfc_id(rs.getInt("cpfc_id"));
				model.setCpfc_name(rs.getString("cpfc_name"));
				list.add(model);
			}
		} catch (Exception e) {
			System.out.print(e.toString());
		} finally {
			db.Close();
		}
		return list;
	}

	public Integer cpdadd(CoProductModel cpmol) throws SQLException {
		dbconn db = new dbconn();
		Integer id = 0;
		try {

			id = Integer.parseInt(db.callWithReturn(
					"{?=call CoProductAdd_P_ply(?,?,?,?,?,?,?,?)}",
					Types.INTEGER, cpmol.getCopr_name(),
					cpmol.getCopr_content(), cpmol.getCopr_state(),
					cpmol.getCopr_copc_id(), cpmol.getCopr_addname(),
					cpmol.getCpst_name(), cpmol.getCopr_cabc_id(),
					cpmol.getCopr_type()).toString());

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			db.Close();
		}

		return id;
	}
	
	

	public int insertfee(CoProductModel cpmol) throws SQLException {
		int row = 0;
		PreparedStatement psmt = null;
		dbconn db = new dbconn();
		String sqlstr = "insert into copfeerelation(cpfr_cpfc_id,cpfr_fee,"
				+ "cpfr_copr_id,cpfr_addname,cpfr_addtime,cpfr_state,cpfr_lock) "
				+ "values(?,?,?,?,getdate(),1,?)";

		psmt = db.getpre(sqlstr);

		try {
			psmt.setInt(1, cpmol.getCpfc_id());
			psmt.setBigDecimal(2, cpmol.getFee());
			psmt.setInt(3, cpmol.getCopr_id());
			psmt.setString(4, cpmol.getCopr_addname());
			psmt.setInt(5, cpmol.getCpfr_lock());

			row = psmt.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			db.Close();
		}

		return row;
	}
//判断是否是存在此产品
	public boolean Exist(CoProductModel cop) throws SQLException {
		boolean isExist = false;
		ResultSet rs = null;
		int a = 0;
		dbconn db = new dbconn();
		String sqlstr = "select count(*) as copcount from CoProduct a,CoPclass b,CoPAccount c,"
				+ "CoAgencyBaseCityRel d"
				+ " where a.copr_copc_id=b.Copc_id and a.copr_cpac_id=c.cpac_id"
				+ " and a.copr_cabc_id=d.cabc_id and copr_name='"
				+ cop.getCopr_name()
				+ "' and copr_cabc_id="
				+ cop.getCopr_cabc_id();

		try {
			rs = db.GRS(sqlstr);

			while (rs.next()) {
				a = rs.getInt("copcount");
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (a != 0) {
				isExist = true;
			}
			db.Close();
		}
		return isExist;
	}

	//判断是否是存在此产品委托
		public boolean Existwt(CoProductModel cop) throws SQLException {
			boolean isExist = false;
			ResultSet rs = null;
			int a = 0;
			dbconn db = new dbconn();
			String sqlstr = "select count(*) as copcount from CoProduct a,CoPclass b,"
					+ "CoAgencyBaseCityRel d"
					+ " where a.copr_copc_id=b.Copc_id "
					+ " and a.copr_cabc_id=d.cabc_id and copr_name='"
					+ cop.getCopr_name()
					+ "' and copr_cabc_id="
					+ cop.getCopr_cabc_id();

			try {
				rs = db.GRS(sqlstr);

				while (rs.next()) {
					a = rs.getInt("copcount");
				}
			} catch (Exception e) {
				e.printStackTrace();
			} finally {
				if (a != 0) {
					isExist = true;
				}
				db.Close();
			}
			return isExist;
		}

	public int editfee(CoAgencyBaseCityRelViewModel m) throws SQLException
	{
		
		
	dbconn db = new dbconn();
	Integer id = 0;
	try {

		id = Integer.parseInt(db.callWithReturn(
				"{?=call CoAgencyfeeedit_p_zmj(?,?)}",
				Types.INTEGER,m.getCabc_id(),
				m.getCabc_fee()).toString());

	} catch (Exception e) {
		e.printStackTrace();
	} finally {
		db.Close();
	}
		
		return id;
		
	}
	
	
	public List<CoAgencyBaseCityRelViewModel> getCoagList() {
		List<CoAgencyBaseCityRelViewModel> list = new ArrayList<>();
		dbconn db = new dbconn();
		String sql = "select cabc_id,cabc_ppc_id,coab_name,cabc_coab_id"
				+ " from CoAgencyBaseCityRel_view where coab_state=1";

		try {
			list = db.find(sql, CoAgencyBaseCityRelViewModel.class,
					dbconn.parseSmap(CoAgencyBaseCityRelViewModel.class));
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}

	/**
	 * 任务单编号更新
	 * 
	 * @param ecoc_id
	 * @param tapr_id
	 * @return
	 * @throws Exception
	 */
	public boolean UpdateTaprid(Integer daid, Integer tapr_id) {
		dbconn db = new dbconn();
		Integer row = 0;
		String sql = "update CoProduct set copr_tapr_id=? where copr_id=?";

		try {
			PreparedStatement psmt = db.getpre(sql);

			psmt.setInt(1, tapr_id);
			psmt.setInt(2, daid);

			row = psmt.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return row == 1 ? true : false;
	}

	public CoProductModel getCoproductInfo(Integer copr_id) {
		List<CoProductModel> list = new ArrayList<>();
		dbconn db = new dbconn();
		String sql = "select Copr_id,copr_name,copr_content,copr_state,copr_stopname,copr_stoptime,"
				+ "copr_sort,copr_d1,copr_d2,copr_copc_id,copr_cpac_id,copr_addtime,copr_addname,"
				+ "copr_type,copr_cabc_id,copr_cmce_id,copr_emce_id,copr_table,copr_embe_id,copr_tapr_id,"
				+ "copr_laststate,copc_name,cpac_name,city,coab_name,"
				+ "stuff((select ','+cpst_name from CoPStandard e left join CoPStRelation f "
				+ "on e.cpst_id=f.cpsr_cpst_id where f.cpsr_copr_id=a.Copr_id order by cpst_id"
				+ " for xml path('')),1,1,'') cpst_name"
				+ " from CoProduct a inner join CoPclass b on a.copr_copc_id=b.Copc_id"
				+ " left join CoPAccount c on a.copr_cpac_id=c.cpac_id"
				+ " inner join CoAgencyBaseCityRel_view d on a.copr_cabc_id=d.cabc_id"
				+ " where copr_id=" + copr_id;

		try {
			list = db.find(sql, CoProductModel.class,
					dbconn.parseSmap(CoProductModel.class));
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list.get(0);
	}

	public Integer updatestate(CoProductModel m) throws SQLException {
		dbconn db = new dbconn();
		Integer id = 0;
		try {

			id = Integer.parseInt(db.callWithReturn(
					"{?=call CoProductUpdateState_P_ply(?,?,?,?)}",
					Types.INTEGER, m.getCopr_id(), m.getCopr_state(),
					m.getCopr_addname(), m.getCopr_remark()).toString());

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			db.Close();
		}

		return id;
	}

	public Integer CopComReladd(CopCompactModel m) {
		dbconn db = new dbconn();
		Integer id = 0;
		try {

			id = Integer.parseInt(db
					.callWithReturn("{?=call CopComRelAdd_P_ply(?,?,?,?,?,?)}",
							Types.INTEGER, m.getCpcr_id(), m.getCpcr_copr_id(),
							m.getCpcr_cpct_id(), m.getCpcr_state(),
							m.getCpcr_addname(), m.getCpcr_addtime())
					.toString());

		} catch (Exception e) {
			e.printStackTrace();
		}

		return id;
	}

	public Integer CopCompactAdd(CopCompactModel m) {
		dbconn db = new dbconn();
		Integer id = 0;
		try {

			id = Integer.parseInt(db
					.callWithReturn(
							"{?=call CopCompactAdd_P_ply(?,?,?,?,?,?)}",
							Types.INTEGER, m.getCpct_id(),
							m.getCpct_shortname(), m.getCpct_name(),
							m.getCpct_state(), m.getCpct_addname(),
							m.getCpct_addtime()).toString());

		} catch (Exception e) {
			e.printStackTrace();
		}

		return id;
	}

	public Integer CopCompactMod(CopCompactModel m) {
		dbconn db = new dbconn();
		Integer row = 0;
		try {

			row = Integer.parseInt(db
					.callWithReturn(
							"{?=call CopCompactMod_P_ply(?,?,?,?,?,?)}",
							Types.INTEGER, m.getCpct_id(),
							m.getCpct_shortname(), m.getCpct_name(),
							m.getCpct_state(), m.getCpct_addname(),
							m.getCpct_addtime()).toString());

		} catch (Exception e) {
			e.printStackTrace();
		}

		return row;
	}

	public Integer CoproductModAccount(Integer daid, Integer cpac_id) {
		dbconn db = new dbconn();
		Integer row = 0;
		String sql = "update CoProduct set copr_cpac_id=? where copr_id=?";

		try {
			PreparedStatement psmt = db.getpre(sql);

			psmt.setInt(1, cpac_id);
			psmt.setInt(2, daid);

			row = psmt.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return row;
	}

	public List<PubStateModel> getStateList(String str) {
		List<PubStateModel> list = new ArrayList<>();
		dbconn db = new dbconn();
		String sql = "select statename,operate from PubState where state=1"
				+ str + " order by orderid";

		try {
			list = db.find(sql, PubStateModel.class,
					dbconn.parseSmap(PubStateModel.class));
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}

	public Integer deleteCopComRelByCpctId(Integer cpct_id) {
		Integer row = 0;
		dbconn db = new dbconn();
		String sql = "delete from CopComRel where cpcr_cpct_id=?";

		try {
			PreparedStatement ppst = db.getpre(sql);

			ppst.setInt(1, cpct_id);

			row = ppst.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
		}

		return row;
	}
	
//	外地产品加入FS-4  1\2\3\5
	
//	AF   2
//	FS-2 3
//	FS 
//	FS-4 type:0 包含eFS ,非0的 就1
	 
	
	
//	1	AF	代表处员工派遣	1	吴礼清	2015-06-18 14:13:00	NULL
//	2	CS	财税服务合同	1	吴礼清	2015-06-18 14:13:00	NULL
//	3	DH	调户服务合同	1	吴礼清	2015-06-18 14:14:00	NULL
//	4	EFS	外籍员工人事代理/服务合同	1	吴礼清	2015-06-18 14:15:00	NULL
//	5	FS	中方员工人事代理/服务合同	1	吴礼清	2015-06-18 14:15:00	NULL
//	6	FS-2	企业员工派遣服务合同	1	吴礼清	2015-06-18 14:16:00	NULL
//	7	FS-4	实习生/退休人员派遣合同	1	吴礼清	2015-06-18 14:16:00	NULL
//	8	FW	法律服务合同	1	吴礼清	2015-06-18 14:16:00	NULL
//	9	SW	商务服务合同	1	吴礼清	2015-06-18 14:17:00	NULL
//	10	WB	外包服务合同	1	吴礼清	2015-06-18 14:18:00	NULL
//	11	WS	外事服务合同	1	吴礼清	2015-06-18 14:19:00	NULL
//	12	ZP	招聘服务合同	1	吴礼清	2015-06-18 14:20:00	NULL
//	所有外地产品直接分到 ：AF，FS，Fs-2，FS-4,
// 	非档案户口类的福利产品不分到：efs，其他都分 
	public Integer insertCopComRel(Integer copr_id,Integer type) {
		Integer row = 0;
		dbconn db = new dbconn();
		String sql ="";
		if (type==0)
		{
			sql= "insert into CopComRel(cpcr_copr_id,cpcr_cpct_id,cpcr_state,cpcr_addname,cpcr_addtime) " +
					"select " +copr_id+
					",cpct_id,1,'" + UserInfo.getUsername()+
					"',GETDATE() from CopCompact where cpct_id in (1,5,6,7,4) ";
		}
		else if (type==1)
		{
			sql= "insert into CopComRel(cpcr_copr_id,cpcr_cpct_id,cpcr_state,cpcr_addname,cpcr_addtime) " +
					"select " +copr_id+
					",cpct_id,1,'" + UserInfo.getUsername()+
					"',GETDATE() from CopCompact where cpct_id in (1,5,6,7)";
		}
		else  if (type==2)
		{
					sql= "insert into CopComRel(cpcr_copr_id,cpcr_cpct_id,cpcr_state,cpcr_addname,cpcr_addtime) " +
							"select " +copr_id+
							",cpct_id,1,'" + UserInfo.getUsername()+
							"',GETDATE() from CopCompact where cpct_id in (2)";
		}
		

		try {
			row = db.execQuery(sql);
		} catch (Exception e) {
			e.printStackTrace();
		}

		return row;
	}


	public Integer Coproduct_Embenefit(CoProductModel m) {
		dbconn db = new dbconn();
		Integer row = 0;
		try {

			row = Integer.parseInt(db.callWithReturn(
					"{?=call CoProduct_EmBenefit_P_ply(?,?)}", Types.INTEGER,
					m.getCopr_id(), m.getCopr_addname()).toString());

		} catch (Exception e) {
			e.printStackTrace();
		}

		return row;
	}
}
