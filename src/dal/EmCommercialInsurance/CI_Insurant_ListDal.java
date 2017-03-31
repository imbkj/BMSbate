package dal.EmCommercialInsurance;

import java.sql.CallableStatement;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import org.zkoss.zul.ListModelList;

import Conn.dbconn;
import Model.CI_Insurant_ListModel;
import Model.EmCommissionOutChangeModel;
import Util.DateStringChange;
import Util.UserInfo;

public class CI_Insurant_ListDal {
	private dbconn conn = new dbconn();
	int getonwmonth = Integer.parseInt(DateStringChange.getOwnmonth());

	String username = UserInfo.getUsername();

	// 判断数据合法性
	public String[] getallstate(String birthday, String idcard,
			String castsort, String buycount, String fact_date, String city,
			String work, String compact_qd) throws SQLException {
		String[] cimessage = new String[2];
		cimessage[0] = "";
		cimessage[1] = "";

		try {
			CallableStatement c = conn
					.getcall("EmComInsuranceMessage_P_zzq(?,?,?,?,?,?,?,?,?,?)");

			System.out.println(birthday);
			System.out.println(idcard);
			System.out.println(castsort);
			System.out.println(buycount);
			System.out.println(fact_date);
			System.out.println(city);
			System.out.println(work);

			c.setString(1, birthday);
			c.setString(2, idcard);
			c.setString(3, castsort);
			c.setString(4, buycount);
			c.setString(5, fact_date);
			c.setString(6, city);
			c.setString(7, work);
			c.setString(8, compact_qd);
			c.registerOutParameter(9, java.sql.Types.NVARCHAR);
			c.registerOutParameter(10, java.sql.Types.NVARCHAR);
			c.execute();
			cimessage[0] = c.getString(9);
			cimessage[1] = c.getString(10);

			System.out.println("-----");
			System.out.println(c.getString(9));

			System.out.println(c.getString(10));
			return cimessage;

		} catch (SQLException e) {
			return cimessage;
		}
	}

	// 获取该员工分配的商业类型
	public List<CI_Insurant_ListModel> getci_insurant_castsort(String gid)
			throws SQLException {
		dbconn db = new dbconn();
		ResultSet rs = null;

		List<CI_Insurant_ListModel> list = new ArrayList<CI_Insurant_ListModel>();
		String sqlstr = "select coli_name,coli_amount,substring(cast(cgli_startdate2 as varchar(6)),1,4)+'-'+substring(cast(cgli_startdate2 as varchar(6)),5,2)+'-01' ef_date,d.ecin_castsort,a.gid from coglist a left join coofferlist b on b.coli_id=a.cgli_coli_id left join EmComInsuranceCompact c on c.ecic_insurance_qtype=b.coli_name left join (select distinct gid,ecin_castsort,ecic_insurance_qtype from EmComInsurance a left join EmComInsuranceCompact b on b.ecic_insurance_type=a.ecin_castsort where ecin_state=1 and ecic_compact_type<>'1') d on d.gid=a.gid and d.ecic_insurance_qtype=b.coli_name where coli_pclass='商业保险' and a.gid="
				+ gid
				+ " and (cgli_stopdate is null or cgli_stopdate>="
				+ getonwmonth + ") and ecic_link=0 and d.ecin_castsort is null";
		try {
			rs = db.GRS(sqlstr);
			System.out.println(sqlstr);
			while (rs.next()) {
				CI_Insurant_ListModel model = new CI_Insurant_ListModel();
				model.setEcin_castsort(rs.getString("coli_name"));
				model.setEcin_castsort(rs.getString("coli_name"));
				model.setEcin_buy_count(rs.getString("coli_amount") + ".0");
				model.setEcin_ef_date1(rs.getDate("ef_date"));
				model.setGid(rs.getString("gid"));
				list.add(model);
			}
		} catch (Exception e) {
			System.out.print(e.toString());
		} finally {
			db.Close();
		}
		return list;
	}

	// 获取该员工分配的商业类型
	public List<CI_Insurant_ListModel> getci_insurant_applycastsort(String gid)
			throws SQLException {
		dbconn db = new dbconn();
		ResultSet rs = null;

		List<CI_Insurant_ListModel> list = new ArrayList<CI_Insurant_ListModel>();
		String sqlstr = "select gid,ecia_castsort,ecia_addtime,case when ecia_state=0 then '未处理' else '已处理' end ecia_state from EmComInsuranceApply where gid="
				+ gid;
		try {
			rs = db.GRS(sqlstr);
			System.out.println(sqlstr);
			while (rs.next()) {
				CI_Insurant_ListModel model = new CI_Insurant_ListModel();
				model.setEcin_castsort(rs.getString("ecia_castsort"));
				model.setEcin_addtime(rs.getString("ecia_addtime"));
				model.setEcin_state(rs.getString("ecia_state"));
				model.setGid(rs.getString("gid"));
				list.add(model);
			}
		} catch (Exception e) {
			System.out.print(e.toString());
		} finally {
			db.Close();
		}
		return list;
	}

	// 获取该员工邮箱信息
	public List<CI_Insurant_ListModel> getembase(String gid)
			throws SQLException {
		dbconn db = new dbconn();
		ResultSet rs = null;

		List<CI_Insurant_ListModel> list = new ArrayList<CI_Insurant_ListModel>();
		String sqlstr = "select emba_name,ecin_castsort,emba_sbemail,emba_email,a.gid from EmComInsurance a left join embase b on b.gid=a.gid where a.gid in (178648,178650,178884,181154,131908,183861,184030,121511,117231,108895,140354,42040,108070,160376,103248,81470,158685,74062,96951,126716,131993,45261,171501,100710,67499,96962,128509,121521,119449,104749,123764,134306,98162,101195,126715,77284,77285,96970,74049,101194,117266,49730,114165,83229,103250,136895,136900,121514,155509,43045,121525,99161,87330,106364,104743,121526,123790,136898,130296,141349,134969,98001,99214,159370,160116,151334,50876,178078,78196,27726,34318,60304,34162,100585,158384,39923,133274,104228,128226,128231,128227,79781,13269,18127,78051,10736,92198,160553,50189,38784,75036,75774,11830,11831,11832,11836,11837,11834,11840,11838,16545,11828,16064,11604,11607,11608,11609,13883,15066,15065,20140,20597,21459,22281,15152,156115,156128,160404,156129,138172,160403,160401,159007,87635,82464,114283,93131,140546,160195,127001,87630,52627,114274,101669,90613,97005,147501,65257,117145,153125,81247,132466,74789,114273,127002,101673,158645,158624,52360,84379,125198,84378,156834,140624,153124,82522,93127,55261,147500,120430,84381,156823,89429,147481,103167,105017,156810,149966,124167,117147,81243,153123,117143,77396,124166,79339,156811,125199,90989,103164,128878,87632,158653,114277,84390,78341,112890,101671,153122,89428,134585,156847,79689,149969,128229,160263,153489,66944,122453,91971,147502,95188,50975,85542,74716,61821,105009,74794,81245,155127,160379,157205,172414,160380,131192,172403,160381,53368,45443,148157,94028,95638,125841,12985,108546,70521,120260,120290,13449,131250,85757,168943,123113,119687,120688,43658,147885,152496,82628,35535,138123,140160,43657,35538,117295,83707,151370,135461,17191,83869,17183,67338,24641,88712,17194,141604,74735,64576,17188,26969,28227,17192,159260,141335,16475,52048,37963,107147,144884,30466,97503,23520,42640,126931,55520,128641,102853,32544,115568,145113,13065,98369,128634,136594,147108,60935,141591,11762,140025,36828,125390,50823,118354,15455,105584,33075,76668,12879,12003,178091,115572,151410,105585,70250,67567,120534,27709,47169,28323,102543,102540,102524,48104,123156,104061,55056,110630,105048,104090,125311,96533,129365,119448,140296,134755,123157,67633,119126,129868,122718,134754,158104,95457,158915,113719,96094,110633,151854,103072,140223,158914,96628,158460,94501,151856,92515,160111,101065,121927,23994,24027,114214,83585,82032,113318,67986,65008,67705,99097,97246,129882,102373,90455,90458,74212,74213,137171,99213) and ecin_state=1 and ecin_castsort='P7-意外B型' and emba_sbemail is not null and emba_sbemail<>''  and ecin_ownmonth is null";
		try {
			rs = db.GRS(sqlstr);
			while (rs.next()) {
				CI_Insurant_ListModel model = new CI_Insurant_ListModel();
				model.setEcin_insurant(rs.getString("emba_name"));
				model.setEcin_castsort(rs.getString("ecin_castsort"));
				model.setEcin_account(rs.getString("emba_sbemail"));
				list.add(model);
			}
		} catch (Exception e) {
			System.out.print(e.toString());
		} finally {
			db.Close();
		}
		return list;
	}

	// 获取该员工邮箱信息
	public List<CI_Insurant_ListModel> getemail_list(String tapr_id)
			throws SQLException {
		dbconn db = new dbconn();
		ResultSet rs = null;

		List<CI_Insurant_ListModel> list = new ArrayList<CI_Insurant_ListModel>();
		String sqlstr = "select coba_client,log_email,ecic_insurant,ecic_idcard,coba_company from EmComInsuranceChange a left join cobase b on b.cid=a.cid left join login c on c.log_name=b.coba_client where ecic_tapr_id="
				+ tapr_id;
		try {
			rs = db.GRS(sqlstr);
			System.out.println(sqlstr);
			while (rs.next()) {
				CI_Insurant_ListModel model = new CI_Insurant_ListModel();
				model.setEcin_insurant(rs.getString("coba_client"));
				model.setEcin_account(rs.getString("log_email"));
				model.setEcin_company(rs.getString("coba_company"));
				model.setEcin_idcard(rs.getString("ecic_idcard"));
				model.setEcin_insurer(rs.getString("ecic_insurant"));
				list.add(model);
			}
		} catch (Exception e) {
			System.out.print(e.toString());
		} finally {
			db.Close();
		}
		return list;
	}

	// 获取该员工公司编号
	public List<CI_Insurant_ListModel> getallcid(String gid)
			throws SQLException {
		dbconn db = new dbconn();
		ResultSet rs = null;
		List<CI_Insurant_ListModel> list = new ArrayList<CI_Insurant_ListModel>();
		String sqlstr = "select cid from embase where gid=" + gid;
		try {
			rs = db.GRS(sqlstr);
			while (rs.next()) {
				CI_Insurant_ListModel model = new CI_Insurant_ListModel();
				model.setCid(rs.getString("cid"));
				list.add(model);
			}
		} catch (Exception e) {
			System.out.print(e.toString());
		} finally {
			db.Close();
		}
		return list;
	}

	// 获取该员工公司编号
	public List<CI_Insurant_ListModel> getebcccid(String ebcc_id)
			throws SQLException {
		dbconn db = new dbconn();
		ResultSet rs = null;
		List<CI_Insurant_ListModel> list = new ArrayList<CI_Insurant_ListModel>();
		String sqlstr = "select cid from embasecompactchange where ebcc_id="
				+ ebcc_id;
		try {
			System.out.println(sqlstr);
			rs = db.GRS(sqlstr);
			while (rs.next()) {
				CI_Insurant_ListModel model = new CI_Insurant_ListModel();
				model.setCid(rs.getString("cid"));
				list.add(model);
			}
		} catch (Exception e) {
			System.out.print(e.toString());
		} finally {
			db.Close();
		}
		return list;
	}

	// 获取该员工公司编号
	public List<CI_Insurant_ListModel> getallcidname(int gid)
			throws SQLException {
		dbconn db = new dbconn();
		ResultSet rs = null;
		List<CI_Insurant_ListModel> list = new ArrayList<CI_Insurant_ListModel>();
		String sqlstr = "select a.cid,coba_shortname from embase a left join cobase b on b.cid=a.cid where gid="
				+ gid;
		try {
			rs = db.GRS(sqlstr);
			while (rs.next()) {
				CI_Insurant_ListModel model = new CI_Insurant_ListModel();
				model.setCid(rs.getString("cid"));
				model.setCoba_shortname(rs.getString("coba_shortname"));
				list.add(model);
			}
		} catch (Exception e) {
			System.out.print(e.toString());
		} finally {
			db.Close();
		}
		return list;
	}

	// 获取该员工公司编号
	public List<CI_Insurant_ListModel> getallclient(int gid)
			throws SQLException {
		dbconn db = new dbconn();
		ResultSet rs = null;
		List<CI_Insurant_ListModel> list = new ArrayList<CI_Insurant_ListModel>();
		String sqlstr = "select a.cid,coba_shortname,coba_client from embase a left join cobase b on b.cid=a.cid where gid="
				+ gid;
		try {
			rs = db.GRS(sqlstr);
			while (rs.next()) {
				CI_Insurant_ListModel model = new CI_Insurant_ListModel();
				model.setCid(rs.getString("cid"));
				model.setCoba_shortname(rs.getString("coba_shortname"));
				model.setCoba_client(rs.getString("coba_client"));
				list.add(model);
			}
		} catch (Exception e) {
			System.out.print(e.toString());
		} finally {
			db.Close();
		}
		return list;
	}

	// 获取该员工首次信息
	public List<CI_Insurant_ListModel> getfbase(String gid, String name)
			throws SQLException {
		dbconn db = new dbconn();
		ResultSet rs = null;
		List<CI_Insurant_ListModel> list = new ArrayList<CI_Insurant_ListModel>();
		String sqlstr = "select * from EmComInsurance where gid=" + gid
				+ " and ecin_insurant='" + name + "'";
		try {
			rs = db.GRS(sqlstr);
			while (rs.next()) {
				CI_Insurant_ListModel model = new CI_Insurant_ListModel();
				model.setEcin_insurant(rs.getString("ecin_insurant"));
				model.setEcin_idcard(rs.getString("ecin_idcard"));
				model.setEcin_sex(rs.getString("ecin_sex"));
				model.setEcin_birthday(rs.getString("ecin_birthday"));
				list.add(model);
			}
		} catch (Exception e) {
			System.out.print(e.toString());
		} finally {
			db.Close();
		}
		return list;
	}

	// 获取该员工公司编号
	public List<CI_Insurant_ListModel> getbcid(int ecin_id) throws SQLException {
		dbconn db = new dbconn();
		ResultSet rs = null;
		List<CI_Insurant_ListModel> list = new ArrayList<CI_Insurant_ListModel>();
		String sqlstr = "select b.cid from EmComInsuranceBChange a left join EmComInsurance b on b.ecin_id=a.ecib_in_id where ecib_id="
				+ ecin_id;
		try {
			rs = db.GRS(sqlstr);
			while (rs.next()) {
				CI_Insurant_ListModel model = new CI_Insurant_ListModel();
				model.setCid(rs.getString("cid"));
				list.add(model);
			}
		} catch (Exception e) {
			System.out.print(e.toString());
		} finally {
			db.Close();
		}
		return list;
	}

	// 获取该员工公司编号
	public List<CI_Insurant_ListModel> getcid(int ecin_id) throws SQLException {
		dbconn db = new dbconn();
		ResultSet rs = null;
		List<CI_Insurant_ListModel> list = new ArrayList<CI_Insurant_ListModel>();
		String sqlstr = "select cid from EmComInsuranceBChange a left join EmComInsurance b on b.ecin_id=a.ecib_in_id where ecib_id="
				+ ecin_id;

		try {
			rs = db.GRS(sqlstr);
			while (rs.next()) {
				CI_Insurant_ListModel model = new CI_Insurant_ListModel();
				model.setCid(rs.getString("cid"));
				list.add(model);
			}
		} catch (Exception e) {
			System.out.print(e.toString());
		} finally {
			db.Close();
		}
		return list;
	}

	// 获取该员工公司编号
	public List<CI_Insurant_ListModel> getecincid(int ecin_id)
			throws SQLException {
		dbconn db = new dbconn();
		ResultSet rs = null;
		List<CI_Insurant_ListModel> list = new ArrayList<CI_Insurant_ListModel>();
		String sqlstr = "select cid from EmComInsuranceChange where ecic_id="
				+ ecin_id;

		try {
			rs = db.GRS(sqlstr);
			while (rs.next()) {
				CI_Insurant_ListModel model = new CI_Insurant_ListModel();
				model.setCid(rs.getString("cid"));
				list.add(model);
			}
		} catch (Exception e) {
			System.out.print(e.toString());
		} finally {
			db.Close();
		}
		return list;
	}

	// 获取该员工姓名
	public List<CI_Insurant_ListModel> getname(int ecin_id) throws SQLException {
		dbconn db = new dbconn();
		ResultSet rs = null;
		List<CI_Insurant_ListModel> list = new ArrayList<CI_Insurant_ListModel>();
		String sqlstr = "select cid,ecin_insurant from EmComInsurance where ecin_id="
				+ ecin_id;
		try {
			rs = db.GRS(sqlstr);
			while (rs.next()) {
				CI_Insurant_ListModel model = new CI_Insurant_ListModel();
				model.setCid(rs.getString("cid"));
				model.setEcin_insurant(rs.getString("ecin_insurant"));
				list.add(model);
			}
		} catch (Exception e) {
			System.out.print(e.toString());
		} finally {
			db.Close();
		}
		return list;
	}

	// 获取该员工姓名
	public List<CI_Insurant_ListModel> gettaprid(int gid) throws SQLException {
		dbconn db = new dbconn();
		ResultSet rs = null;
		List<CI_Insurant_ListModel> list = new ArrayList<CI_Insurant_ListModel>();
		String sqlstr = "select a.cid,ecia_tapr_id,emba_name from EmComInsuranceApply a left join embase b on b.gid=a.gid where ecia_state=0 and a.gid="
				+ gid;
		try {
			System.out.println(sqlstr);
			rs = db.GRS(sqlstr);
			while (rs.next()) {
				CI_Insurant_ListModel model = new CI_Insurant_ListModel();
				model.setCid(rs.getString("cid"));
				model.setEcin_tapr_id(rs.getString("ecia_tapr_id"));
				model.setEcin_insurant(rs.getString("emba_name"));
				list.add(model);
			}
		} catch (Exception e) {
			System.out.print(e.toString());
		} finally {
			db.Close();
		}
		return list;
	}

	// 获取该员工报价单分配
	public List<CI_Insurant_ListModel> getcoli(int gid) throws SQLException {
		dbconn db = new dbconn();
		ResultSet rs = null;
		List<CI_Insurant_ListModel> list = new ArrayList<CI_Insurant_ListModel>();
		String sqlstr = "select coli_name,coli_amount,substring(cast(cgli_startdate as varchar(6)),1,4)+'-'+substring(cast(cgli_startdate as varchar(6)),5,2)+'-01' ef_date from coglist a left join coofferlist b on b.coli_id=a.cgli_coli_id left join EmComInsuranceCompact c on c.ecic_insurance_qtype=b.coli_name where coli_account='商保费' and (cgli_stopdate is null or cgli_stopdate>="
				+ getonwmonth + ") and gid=" + gid;
		String coli_st = "0";
		try {
			rs = db.GRS(sqlstr);
			rs.last();
			System.out.println(rs.getRow());
			coli_st = String.valueOf(rs.getRow());
			CI_Insurant_ListModel model = new CI_Insurant_ListModel();
			model.setCoba_client(coli_st);
			list.add(model);
			// while (rs.next()) {
			// CI_Insurant_ListModel model = new CI_Insurant_ListModel();
			// model.setCoba_client("1");
			// list.add(model);
			// }
		} catch (Exception e) {
			System.out.print(e.toString());
		} finally {
			db.Close();
		}
		return list;
	}

	// 获取该员工报价单分配
	public List<CI_Insurant_ListModel> getcoli_emailst(int gid)
			throws SQLException {
		dbconn db = new dbconn();
		ResultSet rs = null;
		List<CI_Insurant_ListModel> list = new ArrayList<CI_Insurant_ListModel>();
		String sqlstr = "select * from embase where emba_sbemail is not null and gid="
				+ gid;
		String coli_st = "0";
		try {
			rs = db.GRS(sqlstr);
			rs.last();
			coli_st = String.valueOf(rs.getRow());
			CI_Insurant_ListModel model = new CI_Insurant_ListModel();
			model.setCoba_client(coli_st);
			list.add(model);
			// while (rs.next()) {
			// CI_Insurant_ListModel model = new CI_Insurant_ListModel();
			// model.setCoba_client("1");
			// list.add(model);
			// }
		} catch (Exception e) {
			System.out.print(e.toString());
		} finally {
			db.Close();
		}
		return list;
	}

	// 获取该员工连带人报价单分配
	public List<CI_Insurant_ListModel> getcoli_l(int gid) throws SQLException {
		dbconn db = new dbconn();
		ResultSet rs = null;
		List<CI_Insurant_ListModel> list = new ArrayList<CI_Insurant_ListModel>();
		String sqlstr = "select coli_name,coli_amount,substring(cast(cgli_startdate as varchar(6)),1,4)+'-'+substring(cast(cgli_startdate as varchar(6)),5,2)+'-01' ef_date from coglist a left join coofferlist b on b.coli_id=a.cgli_coli_id left join EmComInsuranceCompact c on c.ecic_insurance_qtype=b.coli_name where coli_account='商保费' and ecic_link=1 and (cgli_stopdate is null or cgli_stopdate>="
				+ getonwmonth + ") and gid=" + gid;
		String coli_st = "0";
		try {
			rs = db.GRS(sqlstr);
			rs.last();
			System.out.println(rs.getRow());
			coli_st = String.valueOf(rs.getRow());
			CI_Insurant_ListModel model = new CI_Insurant_ListModel();
			model.setCoba_client(coli_st);
			list.add(model);
			// while (rs.next()) {
			// CI_Insurant_ListModel model = new CI_Insurant_ListModel();
			// model.setCoba_client("1");
			// list.add(model);
			// }
		} catch (Exception e) {
			System.out.print(e.toString());
		} finally {
			db.Close();
		}
		return list;
	}

	// 获取该员工连带人报价单分配
	public List<CI_Insurant_ListModel> gettb_gid(int tb_id) throws SQLException {
		dbconn db = new dbconn();
		ResultSet rs = null;
		List<CI_Insurant_ListModel> list = new ArrayList<CI_Insurant_ListModel>();
		String sqlstr = "select gid,ecia_tapr_id,coba_client from EmComInsuranceApply a left join cobase b on b.cid=a.cid  where ecia_id="
				+ tb_id;
		try {
			System.out.println(sqlstr);
			rs = db.GRS(sqlstr);
			while (rs.next()) {
				CI_Insurant_ListModel model = new CI_Insurant_ListModel();
				model.setGid(rs.getString("gid"));
				model.setEcin_tapr_id(rs.getString("ecia_tapr_id"));
				model.setEcin_client(rs.getString("coba_client"));
				list.add(model);
			}
		} catch (Exception e) {
			System.out.print(e.toString());
		} finally {
			db.Close();
		}
		return list;
	}

	// 获取该员工连带人报价单分配
	public List<CI_Insurant_ListModel> getbuycount_lst(int gid, String castsort)
			throws SQLException {
		dbconn db = new dbconn();
		ResultSet rs = null;
		List<CI_Insurant_ListModel> list = new ArrayList<CI_Insurant_ListModel>();
		String sqlstr = "select coli_amount from coglist a left join coofferlist b on b.coli_id=a.cgli_coli_id left join EmComInsuranceCompact c on c.ecic_insurance_qtype=b.coli_name where coli_account='商保费' and (cgli_stopdate is null or cgli_stopdate>="
				+ getonwmonth
				+ ") and gid="
				+ gid
				+ " and coli_name='"
				+ castsort + "'";
		System.out.println(sqlstr);
		float coli_st = 0;
		try {
			rs = db.GRS(sqlstr);
			rs.last();

			coli_st = rs.getFloat("coli_amount");

			for (float i = 1; i < coli_st + 1; i++) {
				// i=i+1;
				CI_Insurant_ListModel model = new CI_Insurant_ListModel();
				System.out.println(i);
				model.setCoba_client(String.valueOf(i));
				list.add(model);
			}

			// while (rs.next()) {
			// CI_Insurant_ListModel model = new CI_Insurant_ListModel();
			// model.setCoba_client("1");
			// list.add(model);
			// }
		} catch (Exception e) {
			System.out.print(e.toString());
		} finally {
			db.Close();
		}
		return list;
	}

	// 获取该员工分配的商业连带人类型
	public List<CI_Insurant_ListModel> getci_insurant_linkcastsort(String gid)
			throws SQLException {
		dbconn db = new dbconn();
		ResultSet rs = null;
		List<CI_Insurant_ListModel> list = new ArrayList<CI_Insurant_ListModel>();
		String sqlstr = "select coli_name,coli_amount,substring(cast(cgli_startdate as varchar(6)),1,4)+'-'+substring(cast(cgli_startdate as varchar(6)),5,2)+'-01' ef_date,ecic_compact_type,name,idcard,birthday from coglist a left join coofferlist b on b.coli_id=a.cgli_coli_id left join EmComInsuranceCompact c on c.ecic_insurance_qtype=b.coli_name left join embase d on d.gid=a.gid left join (select distinct gid,ecin_castsort,ecic_insurance_qtype from EmComInsurance a left join EmComInsuranceCompact b on b.ecic_insurance_type=a.ecin_castsort where ecin_state=1 and ecic_compact_type<>'子女') e on e.gid=a.gid and e.ecic_insurance_qtype=b.coli_name left join (select gid,emba_sbname1 name,emba_sbage1 age,emba_sbidcard1 idcard,emba_sbbirth1 birthday,emba_sbrelation1 relation from embase where emba_sbname1 is not null and emba_sbrelation1 is not null union all select gid,emba_sbname2,emba_sbage2,emba_sbidcard2,emba_sbbirth2,emba_sbrelation2 from embase where emba_sbname2 is not null and emba_sbrelation2 is not null union all select gid,emba_sbname3,emba_sbage3,emba_sbidcard3,emba_sbbirth3,emba_sbrelation3 from embase where emba_sbname3 is not null and emba_sbrelation3 is not null union all select gid,emba_sbname4,emba_sbage4,emba_sbidcard4,emba_sbbirth4,emba_sbrelation4 from embase where emba_sbname4 is not null and emba_sbrelation4 is not null) f on f.gid=a.gid and f.relation=ecic_compact_type  where coli_pclass='商业保险' and d.gid="
				+ gid
				+ " and (cgli_stopdate is null or cgli_stopdate>="
				+ getonwmonth + ") and ecic_link=1 and e.ecin_castsort is null";
		System.out.println(sqlstr);
		try {
			rs = db.GRS(sqlstr);
			while (rs.next()) {
				CI_Insurant_ListModel model = new CI_Insurant_ListModel();
				model.setEcin_castsort(rs.getString("coli_name"));
				model.setEcin_buy_count(rs.getString("coli_amount"));
				model.setEcin_ef_date1(null);
				model.setEcin_sconnection(rs.getString("ecic_compact_type"));
				model.setEcin_insurant(rs.getString("name"));
				model.setEcin_idcard(rs.getString("idcard"));
				model.setEcin_birthdays(rs.getDate("birthday"));
				list.add(model);
			}
		} catch (Exception e) {
			System.out.print(e.toString());
		} finally {
			db.Close();
		}
		return list;
	}

	// 获取该员工分配的商业连带人类型
	public List<CI_Insurant_ListModel> getci_insurant_linkcastsortadd(String gid)
			throws SQLException {
		dbconn db = new dbconn();
		ResultSet rs = null;
		ResultSet rs1 = null;
		List<CI_Insurant_ListModel> list = new ArrayList<CI_Insurant_ListModel>();
		String sqlstr = "select coli_name,coli_amount,substring(cast(cgli_startdate as varchar(6)),1,4)+'-'+substring(cast(cgli_startdate as varchar(6)),5,2)+'-01' ef_date,ecic_compact_type,name,idcard,birthday from coglist a left join coofferlist b on b.coli_id=a.cgli_coli_id left join EmComInsuranceCompact c on c.ecic_insurance_qtype=b.coli_name left join embase d on d.gid=a.gid left join (select distinct gid,ecin_castsort,ecic_insurance_qtype from EmComInsurance a left join EmComInsuranceCompact b on b.ecic_insurance_type=a.ecin_castsort where ecin_state=1 and ecic_compact_type<>'子女') e on e.gid=a.gid and e.ecic_insurance_qtype=b.coli_name left join (select gid,emba_sbname1 name,emba_sbage1 age,emba_sbidcard1 idcard,emba_sbbirth1 birthday,emba_sbrelation1 relation from embase where emba_sbname1 is not null and emba_sbrelation1 is not null union all select gid,emba_sbname2,emba_sbage2,emba_sbidcard2,emba_sbbirth2,emba_sbrelation2 from embase where emba_sbname2 is not null and emba_sbrelation2 is not null union all select gid,emba_sbname3,emba_sbage3,emba_sbidcard3,emba_sbbirth3,emba_sbrelation3 from embase where emba_sbname3 is not null and emba_sbrelation3 is not null union all select gid,emba_sbname4,emba_sbage4,emba_sbidcard4,emba_sbbirth4,emba_sbrelation4 from embase where emba_sbname4 is not null and emba_sbrelation4 is not null) f on f.gid=a.gid and f.relation=ecic_compact_type  where coli_pclass='商业保险' and d.gid="
				+ gid
				+ " and (cgli_stopdate is null or cgli_stopdate>="
				+ getonwmonth + ") and ecic_link=1 and e.ecin_castsort is null";
		System.out.println(sqlstr);
		try {
			rs = db.GRS(sqlstr);
			while (rs.next()) {
				CI_Insurant_ListModel model = new CI_Insurant_ListModel();
				model.setEcin_castsort(rs.getString("coli_name"));
				model.setEcin_buy_count(rs.getString("coli_amount"));
				model.setEcin_ef_date1(null);
				String sqlstr1 = "select a.cid,ecia_tapr_id,emba_name,ecia_date,ecia_castsort from EmComInsuranceApply a left join embase b on b.gid=a.gid where ecia_state=0 and a.gid="
						+ gid;
				try {
					rs1 = db.GRS(sqlstr1);
					while (rs1.next()) {
						model.setEcin_ef_date1(rs1.getDate("ecia_date"));
						model.setEcin_balance_name(rs1
								.getString("ecia_castsort"));
					}
				} catch (Exception e) {
					System.out.print(e.toString());
				} finally {
					db.Close();
				}

				model.setEcin_sconnection(rs.getString("ecic_compact_type"));
				model.setEcin_insurant(rs.getString("name"));
				model.setEcin_idcard(rs.getString("idcard"));
				model.setEcin_birthdays(rs.getDate("birthday"));
				list.add(model);
			}
		} catch (Exception e) {
			System.out.print(e.toString());
		} finally {
			db.Close();
		}
		return list;
	}

	// 获取该员工的基本信息
	public List<CI_Insurant_ListModel> getembaselist(String gid)
			throws SQLException {
		dbconn db = new dbconn();
		ResultSet rs = null;
		List<CI_Insurant_ListModel> list = new ArrayList<CI_Insurant_ListModel>();
		String sqlstr = "select emba_name,cast(year(emba_birth) as varchar(4))+'-'+cast(month(emba_birth) as varchar(4))+'-'+cast(day(emba_birth) as varchar(4)) emba_birth,emba_sex,emba_idcard,emba_mobile,coba_shortname from embase a left join cobase b on b.cid=a.cid where a.gid="
				+ gid + "";
		try {
			rs = db.GRS(sqlstr);
			while (rs.next()) {
				CI_Insurant_ListModel model = new CI_Insurant_ListModel();
				model.setEcin_insurant(rs.getString("emba_name"));
				model.setEcin_idcard(rs.getString("emba_idcard"));
				model.setEcin_sex(rs.getString("emba_sex"));
				model.setEcin_birthday(rs.getString("emba_birth"));
				model.setEcin_company(rs.getString("coba_shortname"));
				model.setEcin_work_st(rs.getString("emba_mobile"));
				list.add(model);
			}
		} catch (Exception e) {
			System.out.print(e.toString());
		} finally {
			db.Close();
		}
		return list;
	}

	// 获取未审核商保数据
	public List<CI_Insurant_ListModel> getci_insurant_list(String str1)
			throws SQLException {
		dbconn db = new dbconn();
		ResultSet rs = null;
		List<CI_Insurant_ListModel> list = new ArrayList<CI_Insurant_ListModel>();
		String sqlstr = "select ecic_tapr_id,a.ecic_id,coba_company,case when d.ecic_link=1 then emba_name else '' end emba_name,emba_idcard,ecic_castsort,ecic_buy_count,case when ecic_state=1 then '新增' else '停缴' end state,coba_client,CONVERT(varchar(10),ecic_in_date,120) ecic_in_date,CONVERT(varchar(10),ecic_st_date,120) ecic_st_date,ecic_buy_count,ecic_state2,coba_shortname,ecic_insurant,ecic_sign_state,case when substring(ecic_castsort,1,1)='雇' OR substring(ecic_castsort,1,1)='增' then 1 else case when ecic_in_date<=GETDATE() then 1 else 0 end+case when ecic_st_date<=GETDATE() then 1 else 0 end end ecic_sb_st,CONVERT(varchar(10),ecic_birthday,120) ecic_birthday,ecic_compact,ecic_work_city from EmComInsuranceChange a left join embase b on b.gid=a.gid left join cobase c on c.cid=a.cid left join EmComInsuranceCompact d on d.ecic_insurance_type=a.ecic_castsort where ecic_state2=0"
				+ str1 + " order by ecic_id";
		System.out.println(sqlstr);
		try {
			rs = db.GRS(sqlstr);
			while (rs.next()) {
				System.out.println(rs.getString("ecic_tapr_id"));
				CI_Insurant_ListModel model = new CI_Insurant_ListModel();
				model.setEcin_insurant(rs.getString("ecic_insurant"));
				model.setEcin_insurer(rs.getString("emba_name"));
				model.setEcin_idcard(rs.getString("emba_idcard"));
				model.setEcin_castsort(rs.getString("ecic_castsort"));
				model.setEcin_state(rs.getString("state"));
				model.setEcin_in_date(rs.getString("ecic_in_date"));
				model.setEcin_st_date(rs.getString("ecic_st_date"));
				model.setCoba_company(rs.getString("coba_shortname"));
				model.setCoba_client(rs.getString("coba_client"));
				model.setEcin_id(rs.getInt("ecic_id"));
				model.setEcin_tapr_id(rs.getString("ecic_tapr_id"));
				model.setEcin_buy_count(rs.getString("ecic_buy_count"));
				model.setEcin_state2(rs.getString("ecic_state2"));
				model.setEcin_cl_count(rs.getString("ecic_sign_state"));
				model.setEcin_account(rs.getString("ecic_sb_st"));
				model.setEcin_birthday(rs.getString("ecic_birthday"));
				model.setEcin_balance_name(rs.getString("ecic_compact"));
				model.setEcin_work_city(rs.getString("ecic_work_city"));
				list.add(model);
			}
		} catch (Exception e) {
			System.out.print(e.toString());
		} finally {
			db.Close();
		}
		return list;
	}

	// 获取未处理任务单商保数据
	public List<CI_Insurant_ListModel> getci_insurant_rlist(String str1)
			throws SQLException {
		dbconn db = new dbconn();
		ResultSet rs = null;
		List<CI_Insurant_ListModel> list = new ArrayList<CI_Insurant_ListModel>();
		String sqlstr = "select *,CONVERT(varchar(100), ecia_date, 23) ecdate  from EmComInsuranceApply a left join embase b on b.gid=a.gid left join cobase c on c.CID=a.cid where 1=1"
				+ str1 + " order by ecia_id";
		System.out.println(sqlstr);
		try {
			rs = db.GRS(sqlstr);
			while (rs.next()) {

				CI_Insurant_ListModel model = new CI_Insurant_ListModel();
				model.setEcin_insurant(rs.getString("emba_name"));
				model.setEcin_idcard(rs.getString("emba_idcard"));
				model.setEcin_castsort(rs.getString("ecia_castsort"));
				model.setEcin_in_date(rs.getString("ecdate"));
				model.setCoba_company(rs.getString("coba_shortname"));
				model.setCoba_client(rs.getString("coba_client"));
				model.setEcin_id(rs.getInt("ecia_id"));
				model.setEcin_tapr_id(rs.getString("ecia_tapr_id"));
				model.setEcin_balance_name(rs.getString("coba_assistant"));
				model.setCid(rs.getString("cid"));
				model.setEcin_addtime(rs.getString("ecia_addtime"));
				list.add(model);
			}
		} catch (Exception e) {
			System.out.print(e.toString());
		} finally {
			db.Close();
		}
		return list;
	}

	// 获取未审核商保数据
	public List<CI_Insurant_ListModel> getci_insurant_bchlist()
			throws SQLException {
		dbconn db = new dbconn();
		ResultSet rs = null;
		List<CI_Insurant_ListModel> list = new ArrayList<CI_Insurant_ListModel>();
		String sqlstr = "select *,CONVERT(varchar(100), ecin_birthday, 23) birthday from EmComInsuranceBChange a left join EmComInsurance b on b.ecin_id=a.ecib_in_id left join cobase c on c.cid=b.cid where ecib_state=0";
		System.out.println(sqlstr);
		try {
			rs = db.GRS(sqlstr);
			while (rs.next()) {

				CI_Insurant_ListModel model = new CI_Insurant_ListModel();
				model.setEcin_insurant(rs.getString("ecin_insurant"));
				model.setEcin_idcard(rs.getString("ecin_idcard"));
				model.setEcin_castsort(rs.getString("ecin_castsort"));
				// model.setEcin_state(rs.getString("state"));
				model.setEcin_sex(rs.getString("ecin_sex"));
				model.setEcin_birthday(rs.getString("birthday"));
				model.setCoba_company(rs.getString("coba_shortname"));
				model.setCoba_client(rs.getString("coba_client"));
				model.setEcin_id(rs.getInt("ecib_id"));
				model.setEcin_tapr_id(rs.getString("ecib_tapr_id"));
				model.setEcin_remark(rs.getString("ecib_class"));
				model.setEcin_state(rs.getString("ecib_change_f"));
				model.setEcin_state2(rs.getString("ecib_change_l"));
				list.add(model);
			}
		} catch (Exception e) {
			System.out.print(e.toString());
		} finally {
			db.Close();
		}
		return list;
	}

	// 获取商保支付数据
	public List<CI_Insurant_ListModel> getci_insurant_paylist(String str1)
			throws SQLException {
		dbconn db = new dbconn();
		ResultSet rs = null;
		List<CI_Insurant_ListModel> list = new ArrayList<CI_Insurant_ListModel>();
		String sqlstr = "select a.cid,a.gid,ecic_pfee,ecic_tapr_id,a.ecic_id,coba_company,emba_name,emba_idcard,ecic_castsort,ecic_buy_count,case when ecic_state=1 then '新增' else '停缴' end state,coba_client from EmComInsuranceChange a left join embase b on b.gid=a.gid left join cobase c on c.cid=a.cid left join EmComInsuranceCompact d on d.ecic_insurance_type=a.ecic_castsort where ecic_state2=2 and ecic_bstate=1"
				+ str1;
		try {
			rs = db.GRS(sqlstr);
			while (rs.next()) {

				CI_Insurant_ListModel model = new CI_Insurant_ListModel();
				model.setEcin_insurant(rs.getString("emba_name"));
				model.setEcin_idcard(rs.getString("emba_idcard"));
				model.setEcin_castsort(rs.getString("ecic_castsort"));
				model.setEcin_state(rs.getString("state"));
				model.setCoba_company(rs.getString("coba_company"));
				model.setCoba_client(rs.getString("coba_client"));
				model.setEcin_id(rs.getInt("ecic_id"));
				model.setEcin_tapr_id(rs.getString("ecic_tapr_id"));
				model.setEcin_add_money(rs.getString("ecic_pfee"));
				model.setGid(rs.getString("gid"));
				model.setCid(rs.getString("cid"));
				list.add(model);
			}
		} catch (Exception e) {
			System.out.print(e.toString());
		} finally {
			db.Close();
		}
		return list;
	}

	// 获取商保支付添加
	public int pay_ci(int ecic_id, String paynum, String ownmonth)
			throws SQLException {
		dbconn db = new dbconn();
		ResultSet rs = null;

		String sqlstr = "update EmComInsuranceChange set ecic_bstate=2 where ecic_id="
				+ ecic_id;
		try {
			System.out.println(sqlstr);
			rs = db.GRS(sqlstr);
		} catch (Exception e) {
			System.out.print(e.toString());
		} finally {
			db.Close();
		}
		return 1;
	}

	// 获取商保支付更新
	public int payaut_ci(String paynum, String ownmonth) throws SQLException {
		try {
			CallableStatement c = conn.getcall("ESPA_AutUpdate_P_zzq(?,?,?,?)");
			c.setString(1, "商保费");
			c.setString(2, ownmonth);
			c.setString(3, paynum);
			c.registerOutParameter(4, java.sql.Types.INTEGER);
			c.execute();
			return c.getInt(4);
		} catch (Exception e) {
			System.out.print(e.toString());
		} finally {
			conn.Close();
		}
		return 1;
	}

	// 获取已审核商保数据
	public List<CI_Insurant_ListModel> getci_insurant_autlist(String str1)
			throws SQLException {
		dbconn db = new dbconn();
		ResultSet rs = null;
		List<CI_Insurant_ListModel> list = new ArrayList<CI_Insurant_ListModel>();
		String sqlstr = "select ecic_bstate,ecic_tapr_id,a.ecic_id,coba_company,emba_name,emba_idcard,ecic_castsort,ecic_buy_count,case when ecic_state=1 then '新增' else '停缴' end state,coba_client,CONVERT(varchar(10),ecic_in_date,120) ecic_in_date,CONVERT(varchar(10),ecic_st_date,120) ecic_st_date from EmComInsuranceChange a left join embase b on b.gid=a.gid left join cobase c on c.cid=a.cid left join EmComInsuranceCompact d on d.ecic_insurance_type=a.ecic_castsort where ecic_state2=1"
				+ str1;
		System.out.println(sqlstr);
		try {
			rs = db.GRS(sqlstr);
			while (rs.next()) {

				CI_Insurant_ListModel model = new CI_Insurant_ListModel();
				model.setEcin_insurant(rs.getString("emba_name"));
				model.setEcin_idcard(rs.getString("emba_idcard"));
				model.setEcin_castsort(rs.getString("ecic_castsort"));
				model.setEcin_state(rs.getString("state"));
				model.setEcin_in_date(rs.getString("ecic_in_date"));
				model.setEcin_st_date(rs.getString("ecic_st_date"));
				model.setCoba_company(rs.getString("coba_company"));
				model.setCoba_client(rs.getString("coba_client"));
				model.setEcin_id(rs.getInt("ecic_id"));
				model.setEcin_tapr_id(rs.getString("ecic_tapr_id"));
				model.setEcin_state2(rs.getString("ecic_bstate"));
				list.add(model);
			}
		} catch (Exception e) {
			System.out.print(e.toString());
		} finally {
			db.Close();
		}
		return list;
	}

	// 获取已审核商保数据
	public List<CI_Insurant_ListModel> getcastsortlist() throws SQLException {
		dbconn db = new dbconn();
		ResultSet rs = null;
		List<CI_Insurant_ListModel> list = new ArrayList<CI_Insurant_ListModel>();
		String sqlstr = "select distinct ecic_title from EmComInsuranceCompact";
		try {
			rs = db.GRS(sqlstr);
			while (rs.next()) {

				CI_Insurant_ListModel model = new CI_Insurant_ListModel();
				model.setEcin_castsort(rs.getString("ecic_title"));
				list.add(model);
			}
		} catch (Exception e) {
			System.out.print(e.toString());
		} finally {
			db.Close();
		}
		return list;
	}

	// 获取客服
	public List<CI_Insurant_ListModel> clientlist() throws SQLException {
		dbconn db = new dbconn();
		ResultSet rs = null;
		List<CI_Insurant_ListModel> list = new ArrayList<CI_Insurant_ListModel>();
		String sqlstr = "select distinct coba_client from cobase where coba_servicestate=1 and coba_client is not null and coba_client<>'' order by coba_client";
		try {
			rs = db.GRS(sqlstr);
			while (rs.next()) {

				CI_Insurant_ListModel model = new CI_Insurant_ListModel();
				model.setEcin_castsort(rs.getString("coba_client"));
				list.add(model);
			}
		} catch (Exception e) {
			System.out.print(e.toString());
		} finally {
			db.Close();
		}
		return list;
	}

	// 获取中心客服
	public List<CI_Insurant_ListModel> rclientlist() throws SQLException {
		dbconn db = new dbconn();
		ResultSet rs = null;
		List<CI_Insurant_ListModel> list = new ArrayList<CI_Insurant_ListModel>();
		String sqlstr = "select distinct coba_assistant from cobase where coba_servicestate=1 and coba_assistant is not null and coba_assistant<>'' order by coba_assistant";
		try {
			rs = db.GRS(sqlstr);
			while (rs.next()) {

				CI_Insurant_ListModel model = new CI_Insurant_ListModel();
				model.setEcin_castsort(rs.getString("coba_assistant"));
				list.add(model);
			}
		} catch (Exception e) {
			System.out.print(e.toString());
		} finally {
			db.Close();
		}
		return list;
	}

	// 获取商保审核时间
	public List<CI_Insurant_ListModel> getcastsortdatelist()
			throws SQLException {
		dbconn db = new dbconn();
		ResultSet rs = null;
		List<CI_Insurant_ListModel> list = new ArrayList<CI_Insurant_ListModel>();
		String sqlstr = "select distinct cast(year(ecin_de_date) as varchar(4))+'-'+cast(month(ecin_de_date) as varchar(4))+'-'+cast(day(ecin_de_date) as varchar(4)) ecic_de_date from EmComInsurance order by ecic_de_date desc";
		try {
			rs = db.GRS(sqlstr);
			while (rs.next()) {

				CI_Insurant_ListModel model = new CI_Insurant_ListModel();
				model.setEcin_de_date(rs.getString("ecic_de_date"));
				list.add(model);
			}
		} catch (Exception e) {
			System.out.print(e.toString());
		} finally {
			db.Close();
		}
		return list;
	}

	// 获取商保在保类型
	public List<CI_Insurant_ListModel> getci_insurant_castsortout(String gid)
			throws SQLException {
		dbconn db = new dbconn();
		ResultSet rs = null;
		List<CI_Insurant_ListModel> list = new ArrayList<CI_Insurant_ListModel>();
		String sqlstr = "select ecin_id,coba_company,emba_name,emba_idcard,ecin_castsort,ecin_buy_count,case when ecin_state=1 then '新增' else '停缴' end state,coba_client,ecin_insurant from EmComInsurance a left join embase b on b.gid=a.gid left join cobase c on c.cid=a.cid left join EmComInsuranceCompact d on d.ecic_insurance_type=a.ecin_castsort where ecin_state=1 and a.gid="
				+ gid + "";
		try {
			rs = db.GRS(sqlstr);
			while (rs.next()) {
				CI_Insurant_ListModel model = new CI_Insurant_ListModel();
				model.setEcin_insurant(rs.getString("emba_name"));
				model.setEcin_idcard(rs.getString("emba_idcard"));
				model.setEcin_castsort(rs.getString("ecin_castsort"));
				model.setEcin_state(rs.getString("state"));
				model.setCoba_company(rs.getString("coba_company"));
				model.setCoba_client(rs.getString("coba_client"));
				model.setEcin_id(rs.getInt("ecin_id"));
				model.setEcin_insurant(rs.getString("ecin_insurant"));
				model.setEcin_buy_count(rs.getString("ecin_buy_count"));
				list.add(model);
			}
		} catch (Exception e) {
			System.out.print(e.toString());
		} finally {
			db.Close();
		}
		return list;
	}

	// 获取商保在保类型
	public List<CI_Insurant_ListModel> getci_link_list(String gid)
			throws SQLException {
		dbconn db = new dbconn();
		ResultSet rs = null;
		List<CI_Insurant_ListModel> list = new ArrayList<CI_Insurant_ListModel>();
		String sqlstr = "select * from EmComInsurance a left join EmComInsuranceCompact b on b.ecic_insurance_type=a.ecin_castsort where ecin_state=1 and gid="
				+ gid + "";
		try {
			rs = db.GRS(sqlstr);
			while (rs.next()) {
				CI_Insurant_ListModel model = new CI_Insurant_ListModel();
				model.setEcin_insurant(rs.getString("ecin_insurant"));
				model.setEcin_idcard(rs.getString("ecin_idcard"));
				model.setEcin_castsort(rs.getString("ecin_castsort"));
				model.setEcin_sex(rs.getString("ecin_sex"));
				model.setEcin_birthdays(rs.getDate("ecin_birthday"));
				model.setEcin_id(rs.getInt("ecin_id"));
				list.add(model);
			}
		} catch (Exception e) {
			System.out.print(e.toString());
		} finally {
			db.Close();
		}
		return list;
	}

	// 获取商保信息
	public List<CI_Insurant_ListModel> getci_insurant_blist(int gid)
			throws SQLException {
		dbconn db = new dbconn();
		ResultSet rs = null;
		List<CI_Insurant_ListModel> list = new ArrayList<CI_Insurant_ListModel>();
		String sqlstr = "select ecin_id,ecin_insurant,ecin_idcard,ecic_insurance_qtype ecin_castsort,ecin_sex,CONVERT(varchar(100), ecin_birthday, 23) ecin_birthday,CONVERT(varchar(100), ecin_ef_date, 23) ecin_ef_date,ecin_work_city,ecin_work_st,ecin_state,ecin_state2,ecin_buy_count,ecin_compact from EmComInsurance a left join EmComInsuranceCompact b on b.ecic_insurance_type=a.ecin_castsort where gid="
				+ gid + "";
		try {
			rs = db.GRS(sqlstr);
			String change;
			String state;
			System.out.println(sqlstr);
			while (rs.next()) {
				if (rs.getString("ecin_state").equals("1")) {
					change = "加保";
				} else {
					change = "停缴";
				}
				if (rs.getString("ecin_state2").equals("0")) {
					state = "未申报";
				} else {
					state = "已申报";
				}
				CI_Insurant_ListModel model = new CI_Insurant_ListModel();
				model.setEcin_insurant(rs.getString("ecin_insurant"));
				model.setEcin_idcard(rs.getString("ecin_idcard"));
				model.setEcin_castsort(rs.getString("ecin_castsort"));
				model.setEcin_sex(rs.getString("ecin_sex"));
				model.setEcin_birthdays1(rs.getString("ecin_birthday"));
				model.setEcin_ef_date(rs.getString("ecin_ef_date"));
				model.setEcin_work_city(rs.getString("ecin_work_city"));
				model.setEcin_work_st(rs.getString("ecin_work_st"));
				model.setEcin_state(change + "--" + state);
				model.setEcin_id(rs.getInt("ecin_id"));
				model.setEcin_buy_count(rs.getString("ecin_buy_count"));
				model.setEcin_compact_number(rs.getString("ecin_compact"));
				list.add(model);
			}
		} catch (Exception e) {
			System.out.print(e.toString());
		} finally {
			db.Close();
		}
		return list;
	}

	// 获取商保信息
	public List<CI_Insurant_ListModel> getci_insurant_lblist(int gid)
			throws SQLException {
		dbconn db = new dbconn();
		ResultSet rs = null;
		List<CI_Insurant_ListModel> list = new ArrayList<CI_Insurant_ListModel>();
		String sqlstr = "select ecin_id,ecin_insurant,ecin_idcard,ecic_insurance_qtype ecin_castsort,ecin_sex,CONVERT(varchar(100), ecin_birthday, 23) ecin_birthday,CONVERT(varchar(100), ecin_ef_date, 23) ecin_ef_date,ecin_work_city,ecin_work_st,ecin_state,ecin_state2,ecin_buy_count,ecin_compact from EmComInsurance a left join EmComInsuranceCompact b on b.ecic_insurance_type=a.ecin_castsort where ecic_link=1 and gid="
				+ gid + "";
		try {
			rs = db.GRS(sqlstr);
			String change;
			String state;
			System.out.println(sqlstr);
			while (rs.next()) {
				if (rs.getString("ecin_state").equals("1")) {
					change = "加保";
				} else {
					change = "停缴";
				}
				if (rs.getString("ecin_state2").equals("0")) {
					state = "未申报";
				} else {
					state = "已申报";
				}
				CI_Insurant_ListModel model = new CI_Insurant_ListModel();
				model.setEcin_insurant(rs.getString("ecin_insurant"));
				model.setEcin_idcard(rs.getString("ecin_idcard"));
				model.setEcin_castsort(rs.getString("ecin_castsort"));
				model.setEcin_sex(rs.getString("ecin_sex"));
				model.setEcin_birthdays1(rs.getString("ecin_birthday"));
				model.setEcin_ef_date(rs.getString("ecin_ef_date"));
				model.setEcin_work_city(rs.getString("ecin_work_city"));
				model.setEcin_work_st(rs.getString("ecin_work_st"));
				model.setEcin_state(change + "--" + state);
				model.setEcin_id(rs.getInt("ecin_id"));
				model.setEcin_buy_count(rs.getString("ecin_buy_count"));
				model.setEcin_compact_number(rs.getString("ecin_compact"));
				list.add(model);
			}
		} catch (Exception e) {
			System.out.print(e.toString());
		} finally {
			db.Close();
		}
		return list;
	}

	// 获取商保在保类型
	public List<CI_Insurant_ListModel> getci_excel(String id)
			throws SQLException {
		dbconn db = new dbconn();
		ResultSet rs = null;
		List<CI_Insurant_ListModel> list = new ArrayList<CI_Insurant_ListModel>();
		String sqlstr = "select a.ecic_id,a.cid cid,ecic_insurant 保险人,case when h.ecic_link=1 then emba_name else '' end 主险人,case when h.ecic_link=1 then emba_idcard end 主险人身份证,ecic_sconnection 与员工关系,coba_company 公司名称,a.ecic_idcard 身份证,ecic_sex 性别,CONVERT(varchar(100), ecic_birthday, 23) 出生日期,ecic_castsort 投保类型,ecic_buy_count 份数,CONVERT(varchar(100), ecic_ef_date, 23) 生效时间,CONVERT(varchar(100), ecic_in_date, 23) 投保时间,CONVERT(varchar(100), ecic_de_date, 23) 申报时间,CONVERT(varchar(100), ecic_st_date, 23) 停缴时间,CONVERT(varchar(100), ecic_st_dedate, 23) 停报时间,in_date 历史投保时间,case when ecic_state=1 then '加保' else '停缴' end 状态,coba_client 客服,ecic_work_city 社保缴交城市,a.gid gid,case when isnull(g.cl_count,0)>0 then '有理赔' end cl_count,ecic_work_st,ecic_compact cont from EmComInsuranceChange a left join cobase b on b.cid=a.cid left join login c on c.log_name=b.coba_client left join (select distinct gid,ecin_idcard from EmComInsurance where ecin_insurer is null) d on d.gid=a.gid left join (select gid,min(ecin_in_date) in_date from EmComInsurance group by gid) e on e.gid=a.gid left join embase f on f.gid=a.gid left join (select gid,COUNT(*) cl_count from EmCommercialClaim group by gid) g on g.gid=a.gid left join EmComInsuranceCompact h on h.ecic_insurance_type=a.ecic_castsort left join (select gid,COUNT(*) cont from EmBaseCompact group by gid) i on i.gid=a.gid where a.ecic_id="
				+ id + "";
		System.out.println("xxxxx");
		System.out.println(sqlstr);
		try {
			rs = db.GRS(sqlstr);
			System.out.println(sqlstr);
			while (rs.next()) {
				CI_Insurant_ListModel model = new CI_Insurant_ListModel();
				model.setGid(rs.getString("gid"));
				model.setCid(rs.getString("cid"));
				model.setEcin_insurant(rs.getString("保险人"));
				model.setEcin_insurer(rs.getString("主险人"));
				model.setEcin_zidcard(rs.getString("主险人身份证"));
				model.setEcin_sconnection(rs.getString("与员工关系"));
				model.setEcin_company(rs.getString("公司名称"));
				model.setEcin_idcard(rs.getString("身份证"));
				model.setEcin_sex(rs.getString("性别"));
				model.setEcin_birthday(rs.getString("出生日期"));
				model.setEcin_castsort(rs.getString("投保类型"));
				model.setEcin_buy_count(rs.getString("份数"));
				model.setEcin_ef_date(rs.getString("生效时间"));
				model.setEcin_in_date(rs.getString("投保时间"));
				model.setEcin_de_date(rs.getString("申报时间"));
				model.setEcin_st_date(rs.getString("停缴时间"));
				model.setEcin_st_dedate(rs.getString("停报时间"));
				model.setEcin_his_date(rs.getString("历史投保时间"));
				model.setEcin_state(rs.getString("状态"));
				model.setEcin_client(rs.getString("客服"));
				model.setEcin_work_city(rs.getString("社保缴交城市"));
				model.setEcin_id(rs.getInt("ecic_id"));
				model.setEcin_cl_count(rs.getString("cl_count"));
				model.setEcin_work_st(rs.getString("ecic_work_st"));
				model.setEcin_state2(rs.getString("cont"));
				list.add(model);
			}
		} catch (Exception e) {
			System.out.print(e.toString());
		} finally {
			db.Close();
		}
		return list;
	}

	// 获取商保在保删除
	public List<CI_Insurant_ListModel> ecin_del1(String id) throws SQLException {
		dbconn db = new dbconn();
		ResultSet rs = null;
		ResultSet rs1 = null;
		ResultSet rs2 = null;
		List<CI_Insurant_ListModel> list = new ArrayList<CI_Insurant_ListModel>();
		String sqlstr = "select ecic_inid from EmComInsuranceChange where ecic_id="
				+ id + "";
		try {
			rs = db.GRS(sqlstr);
			while (rs.next()) {
				String sqlstr1 = "delete EmComInsuranceChange where ecic_id="
						+ id + "";
				String sqlstr2 = "delete EmComInsurance where ecin_id="
						+ rs.getString("ecic_inid") + "";

				rs1 = db.GRS(sqlstr1);
				rs2 = db.GRS(sqlstr2);
			}
		} catch (Exception e) {
			System.out.print(e.toString());
		} finally {
			db.Close();
		}
		return list;
	}

	// 获取商保在保类型
	public List<CI_Insurant_ListModel> getci_zc_excel(String str1, String str2,
			String str3, String str4, String str5, String str6, String str7,
			String str8) throws SQLException {
		dbconn db = new dbconn();
		ResultSet rs = null;
		List<CI_Insurant_ListModel> list = new ArrayList<CI_Insurant_ListModel>();
		String sqlstr = "select a.ecin_id,a.cid cid,ecin_insurant 保险人,ecin_insurer 主险人,case when ecin_insurer is not null then d.ecin_idcard end 主险人身份证,ecin_sconnection 与员工关系,coba_company 公司名称,a.ecin_idcard 身份证,ecin_sex 性别,CONVERT(varchar(100), ecin_birthday, 23) 出生日期,ecin_castsort 投保类型,ecin_buy_count 份数,CONVERT(varchar(100), ecin_ef_date, 23) 生效时间,CONVERT(varchar(100), ecin_in_date, 23) 投保时间,CONVERT(varchar(100), ecin_de_date, 23) 申报时间,CONVERT(varchar(100), ecin_st_date, 23) 停缴时间,CONVERT(varchar(100), ecin_st_dedate, 23) 停报时间,in_date 历史投保时间,case when ecin_state=1 then '新增' else '停缴' end 状态,coba_client 客服,emba_wt 社保缴交城市,a.gid gid,case when isnull(g.cl_count,0)>0 then '有理赔' end cl_count from EmComInsurance a left join cobase b on b.cid=a.cid left join login c on c.log_name=b.coba_client left join (select top 1 gid,ecin_idcard from EmComInsurance where ecin_insurer is null) d on d.gid=a.gid left join (select gid,min(ecin_in_date) in_date from EmComInsurance group by gid) e on e.gid=a.gid left join embase f on f.gid=a.gid left join (select gid,COUNT(*) cl_count from EmCommercialClaim group by gid) g on g.gid=a.gid left join EmComInsuranceCompact h on h.ecic_insurance_type=a.ecin_castsort where 1=1"
				+ str1 + str2 + str3 + str4 + str5 + str6 + str7;
		System.out.println(sqlstr);
		try {
			rs = db.GRS(sqlstr);
			while (rs.next()) {
				CI_Insurant_ListModel model = new CI_Insurant_ListModel();
				model.setGid(rs.getString("gid"));
				model.setCid(rs.getString("cid"));
				model.setEcin_insurant(rs.getString("保险人"));
				model.setEcin_insurer(rs.getString("主险人"));
				model.setEcin_zidcard(rs.getString("主险人身份证"));
				model.setEcin_sconnection(rs.getString("与员工关系"));
				model.setEcin_company(rs.getString("公司名称"));
				model.setEcin_idcard(rs.getString("身份证"));
				model.setEcin_sex(rs.getString("性别"));
				model.setEcin_birthday(rs.getString("出生日期"));
				model.setEcin_castsort(rs.getString("投保类型"));
				model.setEcin_buy_count(rs.getString("份数"));
				model.setEcin_ef_date(rs.getString("生效时间"));
				model.setEcin_in_date(rs.getString("投保时间"));
				model.setEcin_de_date(rs.getString("申报时间"));
				model.setEcin_st_date(rs.getString("停缴时间"));
				model.setEcin_st_dedate(rs.getString("停报时间"));
				model.setEcin_his_date(rs.getString("历史投保时间"));
				model.setEcin_state(rs.getString("状态"));
				model.setEcin_client(rs.getString("客服"));
				model.setEcin_work_city(rs.getString("社保缴交城市"));
				model.setEcin_id(rs.getInt("ecin_id"));
				model.setEcin_cl_count(rs.getString("cl_count"));
				list.add(model);
			}
		} catch (Exception e) {
			System.out.print(e.toString());
		} finally {
			db.Close();
		}
		return list;
	}

	// 获取商保在保类型
	public List<CI_Insurant_ListModel> getbci_excel(String id)
			throws SQLException {
		dbconn db = new dbconn();
		ResultSet rs = null;
		List<CI_Insurant_ListModel> list = new ArrayList<CI_Insurant_ListModel>();
		String sqlstr = "select b.gid,b.cid,ecib_id,ecin_insurant 保险人,ecin_idcard 身份证,ecin_sex 性别,ecin_birthday 出生日期,ecin_sconnection 与员工关系,ecin_insurer 主险人,'' 主险人身份证,ecib_class 变更类型,ecib_change_f 变更前,ecib_change_l 变更后,' ' 备注,coba_company 公司名称,coba_client 客服,ecin_in_date 投保日期,ecin_castsort 投保类型 from EmComInsuranceBChange a left join EmComInsurance b on b.ecin_id=a.ecib_in_id left join CoBase c on c.CID=b.cid where ecib_id="
				+ id + "";
		try {
			System.out.println(sqlstr);
			rs = db.GRS(sqlstr);
			while (rs.next()) {
				CI_Insurant_ListModel model = new CI_Insurant_ListModel();
				model.setGid(rs.getString("gid"));
				model.setCid(rs.getString("cid"));
				model.setEcin_insurant(rs.getString("保险人"));
				model.setEcin_insurer(rs.getString("主险人"));
				model.setEcin_zidcard(rs.getString("主险人身份证"));
				model.setEcin_sconnection(rs.getString("与员工关系"));
				model.setEcin_company(rs.getString("公司名称"));
				model.setEcin_idcard(rs.getString("身份证"));
				model.setEcin_sex(rs.getString("性别"));
				model.setEcin_birthday(rs.getString("出生日期"));
				model.setEcin_castsort(rs.getString("投保类型"));
				model.setEcin_in_date(rs.getString("投保日期"));
				model.setEcin_remark(rs.getString("变更类型"));
				model.setEcin_state(rs.getString("变更前"));
				model.setEcin_state2(rs.getString("变更后"));
				model.setEcin_client(rs.getString("客服"));
				model.setEcin_id(rs.getInt("ecib_id"));
				list.add(model);
			}
		} catch (Exception e) {
			System.out.print(e.toString());
		} finally {
			db.Close();
		}
		return list;
	}

	// 获取未审核商保数据
	public List<CI_Insurant_ListModel> getci_insurant_chlist(String str1,
			String str2, String str3, String str4, String str5, String str6,
			String str7, String str8) throws SQLException {
		dbconn db = new dbconn();
		ResultSet rs = null;
		List<CI_Insurant_ListModel> list = new ArrayList<CI_Insurant_ListModel>();
		String sqlstr = "select a.gid,ecin_insurant,ecin_insurer,coba_shortname coba_company,ecin_sconnection,emba_idcard,ecin_castsort,ecin_buy_count,case when ecin_state=1 then '新增' else '停缴' end state,coba_client,CONVERT(varchar(100), ecin_in_date, 23) ecin_in_date,CONVERT(varchar(100), ecin_st_date, 23) ecin_st_date from EmComInsurance a left join embase b on b.gid=a.gid left join cobase c on c.cid=a.cid left join EmComInsuranceCompact d on d.ecic_insurance_type=a.ecin_castsort  left join login e on e.log_name=c.coba_client where 1=1"
				+ str1 + str2 + str3 + str4 + str5 + str6 + str7 + str8;
		try {
			System.out.println(sqlstr);
			rs = db.GRS(sqlstr);
			while (rs.next()) {

				CI_Insurant_ListModel model = new CI_Insurant_ListModel();
				model.setGid(rs.getString("gid"));
				model.setEcin_insurant(rs.getString("ecin_insurant"));
				model.setEcin_insurer(rs.getString("ecin_insurer"));
				model.setEcin_idcard(rs.getString("emba_idcard"));
				model.setEcin_sconnection(rs.getString("ecin_sconnection"));
				model.setEcin_castsort(rs.getString("ecin_castsort"));
				model.setEcin_state(rs.getString("state"));
				model.setEcin_in_date(rs.getString("ecin_in_date"));
				model.setEcin_st_date(rs.getString("ecin_st_date"));
				model.setCoba_company(rs.getString("coba_company"));
				model.setCoba_client(rs.getString("coba_client"));
				list.add(model);
			}
		} catch (Exception e) {
			System.out.print(e.toString());
		} finally {
			db.Close();
		}
		return list;
	}

	// 检查员工是否存在在册数据
	public boolean getupdateInfo(Integer gid, String name) {
		boolean b = false;
		List<CI_Insurant_ListModel> list = new ListModelList<>();
		dbconn db = new dbconn();
		String sql = "select convert(varchar(10),a.gid)gid "
				+ " from EmComInsurance a"
				+ " left join EmComInsuranceCompact d on d.ecic_insurance_type=a.ecin_castsort "
				+ " where gid=? and ecic_insurance_qtype=? and ecin_state=1";
		try {
			list = db.find(sql, CI_Insurant_ListModel.class, null, gid, name);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		if (list.size() > 0) {
			b = true;
		}
		return b;
	}

	// 查询该员工是否已有社保信息
	public boolean check_ciin(int gid) {
		String sql = "select coli_name,coli_amount,substring(cast(cgli_startdate as varchar(6)),1,4)+'-'+substring(cast(cgli_startdate as varchar(6)),5,2)+'-01' ef_date from coglist a left join coofferlist b on b.coli_id=a.cgli_coli_id left join EmComInsuranceCompact c on c.ecic_insurance_qtype=b.coli_name where coli_pclass='商业保险' and gid=? and (cgli_stopdate is null or cgli_stopdate>="
				+ getonwmonth + ")";
		PreparedStatement psmt = conn.getpre(sql);
		System.out.println(sql);
		boolean bool = false;
		try {
			psmt.setInt(1, gid);
			ResultSet rs = psmt.executeQuery();
			if (rs.next())
				if (rs.getRow() == 0)
					bool = true;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return bool;
	}

	public int sign_ok(int ecin_id) throws Exception {
		dbconn db = new dbconn();
		ResultSet rs = null;
		// List<CI_Insurant_ListModel> list = new
		// ArrayList<CI_Insurant_ListModel>();
		String sqlstr = "update EmComInsuranceChange set  ecic_sign_state=0,ecic_sign_addname='"
				+ username
				+ "',ecic_sign_addtime=getdate() where ecic_id="
				+ ecin_id;
		rs = db.GRS(sqlstr);
		db.Close();
		return 0;
	}

	// 获取该委托任务单信息
	public List<CI_Insurant_ListModel> gettali_list(int tapr_id)
			throws SQLException {
		dbconn db = new dbconn();
		ResultSet rs = null;

		List<CI_Insurant_ListModel> list = new ArrayList<CI_Insurant_ListModel>();
		String sqlstr = "select tali_id,tali_name from TaskProcess a left join TaskList b on b.tali_id=a.tapr_tali_id where tapr_id="
				+ tapr_id;
		System.out.println(sqlstr);
		try {
			rs = db.GRS(sqlstr);
			while (rs.next()) {
				CI_Insurant_ListModel model = new CI_Insurant_ListModel();
				model.setEcin_id(Integer.parseInt(rs.getString("tali_id")));
				model.setEcin_insurant(rs.getString("tali_name"));
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
