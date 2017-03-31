package dal.CoLatencyClient;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import Conn.dbconn;
import Model.CalendarsModel;
import Model.CoAgencyLinkmanModel;
import Model.CoBaseModel;
import Model.CoCompactModel;
import Model.CoLaContactsModel;
import Model.CoLatencyClientModel;
import Model.EmActySuppilerGiftInfoModel;
import Model.LoginModel;
import Model.PubTradeModel;
import Util.UserInfo;

public class CoLatencyClientDal {
	private dbconn conn = new dbconn();

	// 根据公司名称查询是否已存在该公司
	public List<CoLatencyClientModel> getCoLatencyClientinfo(String name) {
		ResultSet rs = null;
		List<CoLatencyClientModel> list = new ArrayList<CoLatencyClientModel>();
		if (!list.isEmpty())
			list.clear();
		try {
			dbconn db = new dbconn();
			String sqlstr = "SELECT  * from CoLatencyClient where cola_state=1 and cola_company='"
					+ name + "' order by cola_id desc";
			rs = db.GRS(sqlstr);
			list = db.find(sqlstr, CoLatencyClientModel.class,
					dbconn.parseSmap(CoLatencyClientModel.class));
			// while (rs.next()) {
			// list.add(new CoLatencyClientModel(rs.getInt("cola_id"), rs
			// .getInt("cid"), rs.getString("cola_company"), rs
			// .getString("cola_spell"), rs
			// .getString("cola_companytype"), rs
			// .getString("cola_website"), rs.getInt("cola_sign"), rs
			// .getString("cola_address"), rs
			// .getString("cola_clientarea"), rs
			// .getString("cola_trade"), rs
			// .getString("cola_clientsize"), rs
			// .getString("cola_clientsource"), rs
			// .getString("cola_servicecontent"), rs
			// .getString("cola_remark"), rs
			// .getTimestamp("cola_addtime"), rs
			// .getString("cola_addname"), rs
			// .getInt("cola_successlevel"),
			// rs.getInt("cola_ownyear"), rs.getInt("coba_LTS"), rs
			// .getDate("cola_modifydate"), rs
			// .getString("cola_modifyname"), 0));
			// }
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return list;
	}

	// 查询潜在客户信息
	public List<CoLatencyClientModel> getCoLatencyClientAllInfo(String str) {
		ResultSet rs = null;
		List<CoLatencyClientModel> menuinfo = new ArrayList<CoLatencyClientModel>();
		if (!menuinfo.isEmpty())
			menuinfo.clear();
		try {
			dbconn db = new dbconn();
			String sqlstr = "SELECT  a.*,num,CASE cola_successlevel WHEN 1 THEN '较低' WHEN 2 THEN '较低' ";
			sqlstr = sqlstr
					+ "WHEN 3 THEN '一般' WHEN 4 THEN '较高' WHEN 5 THEN '较高' ELSE '-' END AS slevel,"
					+ "cola_realsize,isnull(colanum,0) colanum from CoLatencyClient a  left join "
					+ "(select count(*) as num,coca_colaid from CoAgencyLinkman a,colaColiLinkRel b  where a.cali_id=b.coca_caliid "
					+ "and cali_datatype=2 group by coca_colaid) b on a.cola_id=b.coca_colaid "
					+ " left join ( select COUNT(*) colanum,coco_cola_id from CoCompact group by "
					+ " coco_cola_id) c on a.cola_id=c.coco_cola_id "
					+ "where 1=1 " + str;
			sqlstr = sqlstr + " order by coba_LTS,cola_id desc";
			rs = db.GRS(sqlstr);
			while (rs.next()) {
				menuinfo.add(new CoLatencyClientModel(rs.getInt("cola_id"), rs
						.getInt("cid"), rs.getString("cola_company"), rs
						.getString("cola_spell"), rs
						.getString("cola_companytype"), rs
						.getString("cola_website"), rs.getInt("cola_sign"), rs
						.getString("cola_address"), rs
						.getString("cola_clientarea"), rs
						.getString("cola_trade"), rs
						.getString("cola_clientsize"), rs
						.getString("cola_clientsource"), rs
						.getString("cola_servicecontent"), rs
						.getString("cola_remark"), rs
						.getTimestamp("cola_addtime"), rs
						.getString("cola_addname"), rs
						.getInt("cola_successlevel"),
						rs.getInt("cola_ownyear"), rs.getInt("coba_LTS"), rs
								.getDate("cola_modifydate"), rs
								.getString("cola_modifyname"), 0, rs
								.getString("slevel"), rs
								.getString("cola_follower"), rs.getInt("num"),
						rs.getString("cola_realsize"), rs
								.getString("cola_kind"), rs.getInt("colanum")));
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return menuinfo;
	}

	// 添加一条潜在客户信息
	public int CoLatencyClient_Add_P_cyj(CoLatencyClientModel model) {
		try {
			Timestamp date = new Timestamp(System.currentTimeMillis());
			CallableStatement c = conn
					.getcall("CoLatencyClient_Add_P_cyj(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)");
			c.setString(1, model.getCola_company());
			c.setString(2, model.getCola_companytype());
			c.setString(3, model.getCola_website());
			c.setString(4, model.getCola_address());
			c.setInt(5, model.getCola_sign());
			c.setString(6, model.getCola_clientarea());
			c.setString(7, model.getCola_clientsize());
			c.setString(8, model.getCola_trade());
			c.setString(9, model.getCola_clientsource());
			c.setInt(10, model.getCola_successlevel());
			c.setInt(11, model.getCola_ownyear());
			c.setString(12, model.getCola_servicecontent());
			c.setString(13, model.getCola_remark());
			c.setTimestamp(14, date);
			c.setString(15, model.getCola_addname());
			c.setString(16, model.getCola_spell());
			c.setString(17, model.getCola_follower());
			c.setString(18, model.getCola_realsize());
			c.setString(19, model.getCola_kind());
			c.setString(20, model.getCola_servicessource());
			c.registerOutParameter(21, java.sql.Types.INTEGER);
			c.execute();
			return c.getInt(21);
		} catch (SQLException e) {
			return -1;
		}
	}

	// 修改一条潜在客户信息
	public int CoLatencyClient_update_P_cyj(CoLatencyClientModel model) {
		try {
			Timestamp date = new Timestamp(System.currentTimeMillis());
			CallableStatement c = conn
					.getcall("CoLatencyClient_update_P_cyj(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)");
			c.setInt(1, model.getCola_id());
			c.setString(2, model.getCola_company());
			c.setString(3, model.getCola_companytype());
			c.setString(4, model.getCola_website());
			c.setString(5, model.getCola_address());
			c.setInt(6, model.getCola_sign());
			c.setString(7, model.getCola_clientarea());
			c.setString(8, model.getCola_clientsize());
			c.setString(9, model.getCola_trade());
			c.setString(10, model.getCola_clientsource());
			c.setInt(11, model.getCola_successlevel());
			c.setInt(12, model.getCola_ownyear());
			c.setString(13, model.getCola_servicecontent());
			c.setString(14, model.getCola_remark());
			c.setTimestamp(15, date);
			c.setString(16, model.getCola_modifyname());
			c.setString(17, model.getCola_follower());
			c.setString(18, model.getCola_realsize());
			c.setString(19, model.getCola_kind());
			c.setString(20, model.getCola_servicessource());
			c.registerOutParameter(21, java.sql.Types.INTEGER);
			c.execute();
			return c.getInt(21);
		} catch (SQLException e) {
			return -1;
		}
	}

	// 添加一条潜在客户联系人信息
	public int CoLatencyClientLinkmanAdd_P_cyj(CoAgencyLinkmanModel model,
			int id) {
		try {
			Timestamp date = new Timestamp(System.currentTimeMillis());
			CallableStatement c = conn
					.getcall("CoLatencyClientLinkmanAdd_P_cyj(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)");
			c.setInt(1, id);
			c.setString(2, model.getCali_name());
			c.setString(3, model.getCali_job());
			c.setString(4, model.getCali_tel());
			c.setString(5, model.getCali_mobile());
			c.setString(6, model.getCali_fax());
			c.setString(7, model.getCali_email());
			c.setString(8, model.getCali_address());
			c.setString(9, model.getCali_remark());
			c.setString(10, model.getCali_addname());
			c.setTimestamp(11, date);
			c.setString(12, model.getCali_email1());
			c.setString(13, model.getCali_tel1());
			c.setString(14, model.getCali_duty());
			c.registerOutParameter(15, java.sql.Types.INTEGER);
			c.execute();
			return c.getInt(15);
		} catch (SQLException e) {
			return -1;
		}
	}

	// 更改潜在客户联系人信息（转成功客户）
	public int CoLatencyClientLinkmanAdd_P_lwj(CoAgencyLinkmanModel model,
			int id) {
		try {
			CallableStatement c = conn
					.getcall("CoLatencyClientLinkmanUp_P_lwj(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)");
			c.setInt(1, id);
			c.setString(2, model.getCali_name());
			c.setString(3, model.getCali_job());
			c.setString(4, model.getCali_tel());
			c.setString(5, model.getCali_mobile());
			c.setString(6, model.getCali_fax());
			c.setString(7, model.getCali_email());
			c.setString(8, model.getCali_address());
			c.setString(9, model.getCali_remark());
			c.setString(10, model.getCali_addname());
			c.setString(11, model.getCali_email1());
			c.setString(12, model.getCali_tel1());
			c.setString(13, model.getCali_duty());
			c.setInt(14, model.getCali_id());
			c.setInt(15, model.getLbType());
			c.registerOutParameter(16, java.sql.Types.INTEGER);
			c.execute();
			return c.getInt(16);
		} catch (SQLException e) {
			return -1;
		}
	}

	// 修改一条潜在客户联系人信息
	public int CoLatencyClientLinkmanupdate_P_cyj(CoAgencyLinkmanModel model) {
		try {
			Timestamp date = new Timestamp(System.currentTimeMillis());
			CallableStatement c = conn
					.getcall("CoLatencyClientLinkmanUpdate_P_cyj(?,?,?,?,?,?,?,?,?,?,?,?,?,?)");
			c.setInt(1, model.getCali_id());
			c.setString(2, model.getCali_name());
			c.setString(3, model.getCali_job());
			c.setString(4, model.getCali_tel());
			c.setString(5, model.getCali_tel1());
			c.setString(6, model.getCali_mobile());
			c.setString(7, model.getCali_fax());
			c.setString(8, model.getCali_email());
			c.setString(9, model.getCali_email1());
			c.setString(10, model.getCali_modname());
			c.setTimestamp(11, date);
			c.setString(12, model.getCali_remark());
			c.setString(13, model.getCali_duty());
			c.registerOutParameter(14, java.sql.Types.INTEGER);
			c.execute();
			return c.getInt(14);
		} catch (SQLException e) {
			return -1;
		}
	}

	// 根据机构ID查询联系人信息
	public List<CoAgencyLinkmanModel> getLinkmanForAg(int id) {
		ArrayList<CoAgencyLinkmanModel> coagLinkList = new ArrayList<CoAgencyLinkmanModel>();
		ResultSet rs = null;
		String sql = "select a.* from CoAgencyLinkman a,colaColiLinkRel b where a.cali_id=b.coca_caliid and cali_datatype=2 and coca_colaid="
				+ id;
		try {
			dbconn db = new dbconn();
			rs = db.GRS(sql);
			while (rs.next()) {
				coagLinkList.add(new CoAgencyLinkmanModel(rs.getInt("cali_id"),
						rs.getString("cali_linkman"),
						rs.getString("cali_name"), rs.getString("cali_ename"),
						rs.getString("cali_job"), rs.getString("cali_tel"), rs
								.getString("cali_mobile"), rs
								.getString("cali_fax"), rs
								.getString("cali_email"), rs
								.getString("cali_birth"), rs
								.getString("cali_hobby"), rs
								.getString("cali_address"), rs
								.getString("cali_personality"), rs
								.getString("cali_remark"), rs
								.getInt("cali_vip"), rs
								.getString("cali_addname"), rs
								.getString("cali_addtime"), rs
								.getString("cali_modname"), rs
								.getString("cali_modtime"), rs
								.getString("cali_delname"), rs
								.getString("cali_deltime"), rs
								.getString("cali_delReason"), rs
								.getString("cali_mobile1"), rs
								.getString("cali_mobile2"), rs
								.getString("cali_email1"), rs
								.getString("cali_email2"), rs
								.getString("cali_tel1"), rs
								.getString("cali_tel2"), rs
								.getString("cali_duty")));
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		return coagLinkList;
	}

	// 根据机构ID查询合同信息
	public List<CoCompactModel> getCompact(int id) {
		ArrayList<CoCompactModel> coagLinkList = new ArrayList<CoCompactModel>();
		ResultSet rs = null;
		String sql = "select * from CoCompact_CoLa_CoBase_V where coco_cola_id="
				+ id;
		try {
			dbconn db = new dbconn();
			rs = db.GRS(sql);
			while (rs.next()) {
				coagLinkList.add(new CoCompactModel(rs.getInt("coco_id"), rs
						.getString("cid"), rs.getString("coco_cola_id"), rs
						.getString("coco_compacttype"), rs
						.getString("coco_compactid"), rs
						.getString("coco_servicearea"), rs
						.getString("coco_signdate"), rs
						.getString("coco_stopdate"), rs
						.getString("coco_stopreason"), rs
						.getString("coco_stoptype"), rs
						.getString("coco_inuredate"), rs
						.getString("coco_indate"), rs.getString("coco_delay"),
						rs.getString("coco_remark"), rs.getInt("coco_state"),
						rs.getString("coco_addtime"), rs
								.getString("coco_addname"), rs
								.getString("company"), rs
								.getString("coba_shortname"), rs
								.getString("coco_returndate"), rs
								.getString("coco_signplace"), rs
								.getString("coco_money"), rs
								.getString("coco_invoice"), rs
								.getString("coco_filedate"), rs
								.getString("coco_fileid"), rs
								.getString("coco_printdate"), rs
								.getString("state"), rs
								.getString("coco_auditingdate"), rs
								.getString("coof_fee"), rs
								.getInt("coco_tapr_id"), rs
								.getString("coco_class"), rs
								.getString("coco_chs_copies"), rs
								.getString("coco_en_copies")));
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		return coagLinkList;
	}

	// 查询所属行业的信息
	public List<PubTradeModel> getTradeIndo() {
		ArrayList<PubTradeModel> tradeList = new ArrayList<PubTradeModel>();
		ResultSet rs = null;
		String sql = "select * from PubTrade";
		try {
			dbconn db = new dbconn();
			rs = db.GRS(sql);
			while (rs.next()) {
				tradeList.add(new PubTradeModel(rs.getInt("id"), rs
						.getString("name")));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return tradeList;
	}

	// 查询潜在客户报价人(添加人)
	public List<CoLatencyClientModel> getAddName() {
		List<CoLatencyClientModel> clientlist = new ArrayList<CoLatencyClientModel>();
		ResultSet rs = null;
		String sql = "SELECT  distinct(cola_addname) as colaaddname from CoLatencyClient";
		try {
			dbconn db = new dbconn();
			rs = db.GRS(sql);
			while (rs.next()) {
				CoLatencyClientModel model = new CoLatencyClientModel();
				model.setCola_addname(rs.getString("colaaddname"));
				clientlist.add(model);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return clientlist;
	}

	// 获取联系记录的信息
	public List<CoLaContactsModel> getCoLaContactsInfo(int id) {
		List<CoLaContactsModel> contractslist = new ArrayList<CoLaContactsModel>();
		ResultSet rs = null;
		String sql = "select * from CoLaContacts where clco_calo_id=" + id;
		try {
			dbconn db = new dbconn();
			rs = db.GRS(sql);
			while (rs.next()) {
				contractslist.add(new CoLaContactsModel(rs.getInt("clco_id"),
						rs.getString("clco_content"), rs
								.getTimestamp("clco_linktime"), rs
								.getTimestamp("clco_addtime"), rs
								.getString("clco_addname"), rs
								.getInt("clco_state"), rs
								.getInt("clco_calo_id")));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return contractslist;
	}

	// 添加联系记录的信息
	public int addCoLaContactsInfo(CoLaContactsModel model) {
		int k = 0;
		try {
			dbconn db = new dbconn();
			Timestamp date = new Timestamp(System.currentTimeMillis());
			SimpleDateFormat formatter = new SimpleDateFormat(
					"yyyy-MM-dd HH:mm:ss");
			String dateString = formatter.format(model.getClco_linktime());
			String sql = "insert into CoLaContacts(clco_calo_id,clco_content,clco_linktime,clco_addtime,clco_addname)";
			sql = sql + " values(" + model.getClco_calo_id() + ",'"
					+ model.getClco_content() + "','" + dateString;
			sql = sql + "','" + date + "','" + model.getClco_addname() + "')";
			k = db.execQuery(sql);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return k;
	}

	// 更新联系记录的信息
	public int UpdateCoLaContactsInfo(CoLaContactsModel model) {
		int k = 0;
		try {
			dbconn db = new dbconn();
			String sql = "update CoLaContacts set clco_content='"
					+ model.getClco_content() + "',clco_linktime='"
					+ model.getClco_linktime() + "'";
			sql = sql + " where clco_id=" + model.getClco_id();
			k = db.execQuery(sql);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return k;
	}

	// 删除联系记录的信息
	public int deleteCoLaContactsInfo(int id) {
		int k = 0;
		try {
			dbconn db = new dbconn();
			String sql = "delete CoLaContacts where clco_calo_id=" + id;
			k = db.execQuery(sql);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return k;
	}

	// 潜在客户转成功客户
	public int CoLatencyClientchange(CoBaseModel model, int cola_id) {
		try {
			CallableStatement c = conn
					.getcall("[CoCompany_Addnew_cyj](?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)");
			c.setInt(1, cola_id);
			c.setString(2, model.getCoba_company());
			c.setString(3, model.getCoba_shortname());
			c.setInt(4, model.getCoba_sign());
			c.setString(5, model.getCoba_address());// 注册地址
			c.setString(6, model.getCoba_englishname());
			c.setString(7, model.getCoba_clientsource());
			c.setString(8, model.getCoba_clientsize());
			c.setString(9, model.getCoba_industytype());// 所属行业
			c.setString(10, model.getCoba_setuptype());// 公司类型
			c.setString(11, model.getCoba_area());// 所在区域
			c.setString(12, model.getCoba_reg_fund());// 注册资金
			c.setString(13, model.getCoba_companymanager());// 法定代表
			c.setString(14, model.getCoba_manageraddress());
			c.setString(15, model.getCoba_postcode());
			c.setString(16, model.getCoba_invoiceaddress());
			c.setString(17, model.getCoba_compacttype());// 合同类型
			c.setString(18, model.getCoba_servicearea());// 服务区域
			c.setString(19, model.getCoba_client());
			c.setString(20, model.getCoba_manager());
			c.setString(21, model.getCoba_clientmanager());
			c.setString(22, model.getCoba_remark());
			c.setString(23, model.getCoba_addname());
			c.setString(24, model.getCoba_kind());
			c.setString(25, model.getCoba_ifhasbribery());
			c.registerOutParameter(26, java.sql.Types.INTEGER);
			c.execute();
			return c.getInt(26);
		} catch (SQLException e) {
			return -1;
		}
	}

	// 更新公司信息表任务单id
	public boolean updateCobaseTaprid(int taprid, int id) {
		dbconn db = new dbconn();
		PreparedStatement psmt = null;
		int row = 0;
		String sql = "update cobase set coba_tarpid=? where cid=?";
		try {
			psmt = db.getpre(sql);
			psmt.setInt(1, taprid);
			psmt.setInt(2, id);
			row = psmt.executeUpdate();
		} catch (Exception e) {
			// TODO: handle exception
		}
		return row > 0 ? true : false;
	}

	// 更新公司信息
	public boolean updateCobaseInfo(String sqls, Integer cid) {
		dbconn db = new dbconn();
		int row = 0;
		String sql = "update cobase set coba_modname='"
				+ UserInfo.getUsername() + "'" + sqls + " where cid=" + cid;
		try {
			row = db.execQuery(sql);
		} catch (Exception e) {
			// TODO: handle exception
		}
		return row > 0 ? true : false;
	}

	// 获取客服信息
	public List<String> getLoginInfo() {
		List<String> loginlist = new ArrayList<String>();
		ResultSet rs = null;
		String sql = "select * from View_loginrole where dep_id=(select dep_id from login where log_name='"
				+ UserInfo.getUsername() + "') order by log_name";
		try {
			dbconn db = new dbconn();
			rs = db.GRS(sql);
			loginlist.add("");
			while (rs.next()) {
				loginlist.add(rs.getString("log_name"));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return loginlist;
	}

	// 根据潜在客户Id查询是否有已签回的合同
	public Integer getCoCompact(Integer cola_id) {
		Integer k = 0;
		String sql = "select COUNT(*) num from CoCompact "
				+ "where coco_cola_id=" + cola_id + " and coco_state>3";
		dbconn db = new dbconn();
		try {
			ResultSet rs = db.GRS(sql);
			while (rs.next()) {
				k = rs.getInt("num");
			}
		} catch (Exception e) {

		}
		return k;
	}

	// 查询报价单是否有与个税相关的服务
	public boolean ifExistsgeshui(String str) {
		boolean flag = false;
		String sql = "select * from CoCompact a inner join CoOfferList b on a.coco_id=b.coli_coco_id" +
				" and coli_name like '%个税%' "+ str;
		try {
			dbconn db = new dbconn();
			ResultSet rs = db.GRS(sql);
			while (rs.next()) {
				flag = true;
			}
		} catch (Exception e) {

		}
		return flag;
	}

	// 根据潜在客户Id查询是否有有合同
	public Integer ifExistCoCompact(Integer cola_id) {
		Integer k = 0;
		String sql = "select COUNT(*) num from CoCompact where coco_cola_id="
				+ cola_id;
		dbconn db = new dbconn();
		try {
			ResultSet rs = db.GRS(sql);
			while (rs.next()) {
				k = rs.getInt("num");
			}
		} catch (Exception e) {

		}
		return k;
	}

	// 获取客服信息
	public List<String> getpidLoginlist() {
		List<String> loginlist = new ArrayList<String>();
		ResultSet rs = null;
		String sql = "select * from View_loginrole order by log_name";
		try {
			dbconn db = new dbconn();
			rs = db.GRS(sql);
			loginlist.add("");
			while (rs.next()) {
				loginlist.add(rs.getString("log_name"));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return loginlist;
	}

	// 获取潜在客户跟进人
	public List<String> getFollower() {
		List<String> list = new ArrayList<String>();
		list.add("");
		String sql = "select distinct(cola_follower) from CoLatencyClient "
				+ "where cola_follower is not null and cola_follower<>'' ";
		try {
			dbconn db = new dbconn();
			ResultSet rs = db.GRS(sql);
			while (rs.next()) {
				list.add(rs.getString("cola_follower"));
			}
		} catch (Exception e) {

		}
		return list;
	}

	// 根据输入的潜在客户的名称查询潜在客户表是否有该公司
	public List<CoLatencyClientModel> getCoLatencyClientinfoList(String name) {
		List<CoLatencyClientModel> list = new ArrayList<CoLatencyClientModel>();
		String sql = " select * from CoLatencyClient where cola_company like '%"
				+ name + "%'";
		try {
			dbconn db = new dbconn();
			ResultSet rs = db.GRS(sql);
			while (rs.next()) {
				CoLatencyClientModel model = new CoLatencyClientModel();
				model.setCola_company(rs.getString("cola_company"));
				model.setCompanytype("潜在客户");
				model.setCola_addname(rs.getString("cola_addname"));
				list.add(model);
			}
		} catch (Exception e) {

		}
		return list;
	}

	// 根据输入的潜在客户的名称查询服务中的客户客户表是否有该公司
	public List<CoLatencyClientModel> getCobasefoList(String name) {
		List<CoLatencyClientModel> list = new ArrayList<CoLatencyClientModel>();
		String sql = " select * from CoBase where (coba_company like '%" + name
				+ "%' or coba_shortname like '%" + name + "%')";
		try {
			dbconn db = new dbconn();
			ResultSet rs = db.GRS(sql);
			while (rs.next()) {
				CoLatencyClientModel model = new CoLatencyClientModel();
				model.setCola_company(rs.getString("coba_company"));
				if (rs.getInt("coba_servicestate") == 1) {
					model.setCompanytype("服务中");
				} else {
					model.setCompanytype("已解约");
				}
				model.setCola_addname(rs.getString("coba_client"));
				list.add(model);
			}
		} catch (Exception e) {

		}
		return list;
	}
	
	//查询客户是否有财税服务
	public boolean isHasCS(int cola_id)
	{
		String sql="select coof_cola_id,* from CoOffer a inner join CoOfferList b " +
				"on a.coof_id=b.coli_coof_id where coof_state=3 and coli_pclass ='财税服务'" +
				" and coof_cola_id="+cola_id;
		boolean flag=false;
		try {
			dbconn db = new dbconn();
			ResultSet rs = db.GRS(sql);
			while (rs.next()) {
				flag=true;
			}
		} catch (Exception e) {

		}
		return flag;
	}
}
