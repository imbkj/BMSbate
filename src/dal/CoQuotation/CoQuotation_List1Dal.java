package dal.CoQuotation;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import Conn.dbconn;
import Model.CoLatencyClientModel;
import Model.CoOfferModel;
import Util.UserInfo;

public class CoQuotation_List1Dal {

	public List<CoOfferModel> getCoOfferList(int cola_id) throws SQLException {
		dbconn db = new dbconn();
		ResultSet rs = null;
		List<CoOfferModel> list = new ArrayList<CoOfferModel>();
		String sqlstr = "select *,case when coco_class=0 then '标准版' else '' end+case when coco_class=1 then '一般非标' else '' end+case when coco_class=2 then '特殊非标' else '' end+case when coco_class=3 then '客户提供' else '' end co_type from CoOffer a left join CopCompact b on a.coof_cpct_id=b.cpct_id left join CoCompact c on c.coco_id=a.coof_coco_id where coof_state>0 and coof_cola_id="
				+ cola_id;
		try {

			rs = db.GRS(sqlstr);
			while (rs.next()) {
				CoOfferModel model = new CoOfferModel();
				model.setCoof_cola_id(rs.getInt("coof_cola_id"));
				model.setCoof_id(rs.getInt("coof_id"));
				model.setCoof_name(rs.getString("coof_name"));
				model.setCoof_quotemode(rs.getString("coof_quotemode"));
				model.setCoof_cpct_id(rs.getInt("coof_cpct_id"));
				model.setCpct_shortname(rs.getString("cpct_shortname"));
				model.setCpct_name(rs.getString("cpct_name"));
				model.setCpct_name1(model.getCpct_shortname()
						+ (model.getCpct_name() == null ? "" : ("("
								+ model.getCpct_name() + ")")));
				model.setCoof_addname(rs.getString("coof_addname"));
				model.setCoof_addtime(rs.getDate("coof_addtime"));
				model.setCoof_state(rs.getInt("coof_state"));
				model.setCoof_remark(rs.getString("coof_remark"));
				model.setCoof_gm(rs.getString("coof_gm"));
				model.setCoof_sum(rs.getBigDecimal("coof_sum"));
				model.setCoof_tarp_id(rs.getInt("coof_tarp_id"));
				model.setCoof_auditing_name(rs.getString("coof_auditing_name"));
				model.setCoof_auditing_time(rs.getString("coof_auditing_time"));
				model.setCoof_backcase(rs.getString("coof_backcase"));
				model.setCoof_backname(rs.getString("coof_backname"));
				model.setCoof_auditaddname(rs.getString("coof_auditaddname"));
				model.setCoof_backtime(rs.getString("coof_backtime"));
				model.setCoof_auditaddtime(rs.getString("coof_auditaddtime"));
				model.setCoco_compactid(rs.getString("coco_compactid"));
				model.setCoco_house(rs.getString("coco_id"));
				model.setCoco_shebao(rs.getString("co_type"));
				list.add(model);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			db.Close();
		}
		return list;
	}

	public List<CoLatencyClientModel> getCoLatencyClientAllInfo(String str,String top) {
		ResultSet rs = null;
		List<CoLatencyClientModel> menuinfo = new ArrayList<CoLatencyClientModel>();
		if (!menuinfo.isEmpty())
			menuinfo.clear();
		try {// 潜在客户管理
			dbconn db = new dbconn();
			String sqlstr = "SELECT "+top+" *,CASE cola_successlevel WHEN 1 THEN '较低' WHEN 2 THEN '较低' "
					+ "WHEN 3 THEN '一般' WHEN 4 THEN '较高' WHEN 5 THEN '较高' ELSE '-' END AS slevel, "
					+ "quonum,case when log_inure=0 or log_inure is null then '离职人员' else cola_follower end addname "
					+ " from CoLatencyClient a left outer join "
					+ "(select coof_cola_id,count(*) quonum from cooffer where coof_state<>0 "
					+ "group by coof_cola_id) b "
					+ "on a.cola_id=b.coof_cola_id "
					+ " left join ( select COUNT(*) colanum,coco_cola_id from CoCompact where coco_state<=3"
					+ "  group by  coco_cola_id) c on a.cola_id=c.coco_cola_id"
					+ " left join login d on a.cola_follower=d.log_name"
					+ " where 1=1 and a.cola_id in ( select cola_id from DataPopedom where log_id="
					+ UserInfo.getUserid()
					+ " and  Dat_edited=1) "
					+ str
					+ " order by coba_LTS, cola_id desc";

			rs = db.GRS(sqlstr);
			System.out.println(sqlstr);
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
						.getString("cola_remark"), rs.getDate("cola_addtime"),
						rs.getString("addname"),
						rs.getInt("cola_successlevel"), rs
								.getInt("cola_ownyear"), rs.getInt("coba_LTS"),
						rs.getDate("cola_modifydate"), rs
								.getString("cola_modifyname"), 0, rs
								.getString("slevel"), rs
								.getString("cola_follower"), rs
								.getInt("quonum"),
						rs.getInt("quonum") > 0 ? true : false, rs
								.getString("cola_kind"), rs
								.getString("cola_realsize"),rs.getString("cola_servicessource")));
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return menuinfo;
	}
	
	public List<CoLatencyClientModel> getCoLatencyClientAllInfo(String str) {
		ResultSet rs = null;
		List<CoLatencyClientModel> menuinfo = new ArrayList<CoLatencyClientModel>();
		if (!menuinfo.isEmpty())
			menuinfo.clear();
		try {// 潜在客户管理
			dbconn db = new dbconn();
			String sqlstr = "SELECT  *,CASE cola_successlevel WHEN 1 THEN '较低' WHEN 2 THEN '较低' "
					+ "WHEN 3 THEN '一般' WHEN 4 THEN '较高' WHEN 5 THEN '较高' ELSE '-' END AS slevel, "
					+ "quonum,case when log_inure=0 or log_inure is null then '离职人员' else cola_follower end addname "
					+ " from CoLatencyClient a left outer join "
					+ "(select coof_cola_id,count(*) quonum from cooffer where coof_state<>0 "
					+ "group by coof_cola_id) b "
					+ "on a.cola_id=b.coof_cola_id "
					+ " left join ( select COUNT(*) colanum,coco_cola_id from CoCompact where coco_state<=3"
					+ "  group by  coco_cola_id) c on a.cola_id=c.coco_cola_id"
					+ " left join login d on a.cola_follower=d.log_name"
					+ " where 1=1 and a.cola_id in ( select cola_id from DataPopedom where log_id="
					+ UserInfo.getUserid()
					+ " and  Dat_edited=1) "
					+ str
					+ " order by coba_LTS, cola_id desc";

			rs = db.GRS(sqlstr);
			System.out.println(sqlstr);
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
						.getString("cola_remark"), rs.getDate("cola_addtime"),
						rs.getString("addname"),
						rs.getInt("cola_successlevel"), rs
								.getInt("cola_ownyear"), rs.getInt("coba_LTS"),
						rs.getDate("cola_modifydate"), rs
								.getString("cola_modifyname"), 0, rs
								.getString("slevel"), rs
								.getString("cola_follower"), rs
								.getInt("quonum"),
						rs.getInt("quonum") > 0 ? true : false, rs
								.getString("cola_kind"), rs
								.getString("cola_realsize"),rs.getString("cola_servicessource")));
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return menuinfo;
	}

	public List<String> getAddnameList() throws SQLException {
		dbconn db = new dbconn();
		ResultSet rs = null;
		List<String> list = new ArrayList<String>();
		String sqlstr = "select '' cola_addname union "
				+ "select distinct case log_inure when 0 then '离职人员' else cola_addname end"
				+ " from CoLatencyClient a"
				+ " left join login b on a.cola_addname=b.log_name"
				+ " where cola_state=1 "
				+ "and cola_addname is not null and cola_addname<>''";
		try {
			rs = db.GRS(sqlstr);
			while (rs.next()) {
				list.add(rs.getString("cola_addname"));
			}
		} catch (Exception e) {
			System.out.print(e.toString());
		} finally {
			db.Close();
		}
		return list;
	}
	
	public List<String> getFollowerList() throws SQLException {
		dbconn db = new dbconn();
		ResultSet rs = null;
		List<String> list = new ArrayList<String>();
		String sqlstr = "select '' cola_follower union "
				+ "select distinct case when log_inure =0 or log_inure is null then '离职人员'  else cola_follower end"
				+ " from CoLatencyClient a"
				+ " left join login b on a.cola_follower=b.log_name"
				+ " where cola_state=1 "
				+ "and cola_follower is not null and cola_follower<>''";
		try {
			rs = db.GRS(sqlstr);
			while (rs.next()) {
				list.add(rs.getString("cola_follower"));
			}
		} catch (Exception e) {
			System.out.print(e.toString());
		} finally {
			db.Close();
		}
		return list;
	}

	// 根据报价单id获取报价单信息
	public List<CoOfferModel> getCoOfferInfoList(int coof_id)
			throws SQLException {
		dbconn db = new dbconn();
		ResultSet rs = null;
		List<CoOfferModel> list = new ArrayList<CoOfferModel>();
		String sqlstr = "select * from CoOffer a left join CopCompact b on a.coof_cpct_id=b.cpct_id where coof_id="
				+ coof_id;
		try {

			rs = db.GRS(sqlstr);
			while (rs.next()) {
				CoOfferModel model = new CoOfferModel();
				model.setCoof_cola_id(rs.getInt("coof_cola_id"));
				model.setCoof_id(rs.getInt("coof_id"));
				model.setCoof_name(rs.getString("coof_name"));
				model.setCoof_quotemode(rs.getString("coof_quotemode"));
				model.setCoof_cpct_id(rs.getInt("coof_cpct_id"));
				model.setCpct_shortname(rs.getString("cpct_shortname"));
				model.setCpct_name(rs.getString("cpct_name"));
				model.setCpct_name1(model.getCpct_shortname() + "("
						+ model.getCpct_name() + ")");
				model.setCoof_addname(rs.getString("coof_addname"));
				model.setCoof_addtime(rs.getDate("coof_addtime"));
				model.setCoof_state(rs.getInt("coof_state"));
				model.setCoof_remark(rs.getString("coof_remark"));
				model.setCoof_gm(rs.getString("coof_gm"));
				model.setCoof_sum(rs.getBigDecimal("coof_sum"));
				model.setCoof_tarp_id(rs.getInt("coof_tarp_id"));
				model.setCoof_auditing_name(rs.getString("coof_auditing_name"));
				model.setCoof_auditing_time(rs.getString("coof_auditing_time"));
				model.setCoof_backcase(rs.getString("coof_backcase"));
				model.setCoof_backname(rs.getString("coof_backname"));
				model.setCoof_auditaddname(rs.getString("coof_auditaddname"));
				model.setCoof_backtime(rs.getString("coof_backtime"));
				model.setCoof_auditaddtime(rs.getString("coof_auditaddtime"));
				list.add(model);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			db.Close();
		}
		return list;
	}
}
