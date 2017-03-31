package dal.Embase;

import java.sql.CallableStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import Conn.dbconn;
import Model.SMSModel;
import Util.UserInfo;

public class SMS_CheckDal {
	private static dbconn conn = new dbconn();

	// 获取用户名
	String username = UserInfo.getUsername();

	// 显示短信信息
	public List<SMSModel> getsmslist(String gid) throws SQLException {
		dbconn db = new dbconn();
		ResultSet rs = null;
		List<SMSModel> list = new ArrayList<SMSModel>();
		String sqlstr = "select * from Sms_send a left join (select distinct gid,sms_se_id from Sms_rel) b on b.sms_se_id=a.id where gid='"
				+ gid + "'";
		try {
			if (gid != null) {

				rs = db.GRS(sqlstr);
				while (rs.next()) {
					SMSModel model = new SMSModel();
					model.setMobile(rs.getString("mobile"));
					model.setContent(rs.getString("content"));
					model.setSendname(rs.getString("username"));
					model.setSendtime(rs.getString("submit_time"));
					model.setSms_state(rs.getInt("result"));

					list.add(model);
				}
			}
		} catch (Exception e) {
			System.out.print(e.toString());
		} finally {
			db.Close();
		}
		return list;
	}

	// 显示短信信息
	public List<SMSModel> getsmsclasslist(String type, String mobile)
			throws SQLException {
		dbconn db = new dbconn();
		ResultSet rs = null;
		ResultSet rs1 = null;
		List<SMSModel> list = new ArrayList<SMSModel>();
		String str = "";
		if (!type.equals("")) {
			str = " and sms_type='" + type + "'";
		}
		String sqlstr = "select * from Sms_class where 1=1" + str;
		try {
			System.out.println(sqlstr);
			rs = db.GRS(sqlstr);
			while (rs.next()) {
				SMSModel model = new SMSModel();
				model.setSms_class(rs.getString("sms_type"));
				model.setSms_id(rs.getString("sms_id"));
				list.add(model);
			}
		} catch (Exception e) {
			System.out.print(e.toString());
		} finally {
			db.Close();
		}
		return list;
	}

	// 显示短信模板
	public List<SMSModel> getsmsclasslistcon(String type, String mobile)
			throws SQLException {
		dbconn db = new dbconn();
		ResultSet rs = null;
		ResultSet rs1 = null;
		String sm_contet = "";
		List<SMSModel> list = new ArrayList<SMSModel>();
		String str = "";
		if (!type.equals("")) {
			str = " and sms_type='" + type + "'";
		}
		String sqlstr = "select * from Sms_class where 1=1" + str;
		try {
			System.out.println(sqlstr);
			rs = db.GRS(sqlstr);
			while (rs.next()) {
				try {
					sm_contet = rs.getString("sms_content");// 短信内容
					String sqlstr1 = "select emba_name,coba_shortname,a.log_tel,substring(a.log_name,1,1) log_name,case when a.log_sex='M' then '先生' else '小姐' end sex,a.log_email,substring(c.log_name,1,1) coba_name,c.log_tel coba_tel,case when c.log_sex='M' then '先生' else '小姐' end coba_sex,'' coba_email from login a left join (select emba_name,coba_shortname,'"
							+ username
							+ "' username,emba_mobile,coba_client from EmBase a left join cobase b on b.CID=a.cid) b on b.username=a.log_name left join (select log_name,log_email,log_tel,log_sex from login) c on c.log_name=b.coba_client where emba_mobile='"
							+ mobile + "'";
					rs1 = db.GRS(sqlstr1);
					while (rs1.next()) {
						sm_contet = sm_contet.replace("st_name",
								rs1.getString("emba_name"));// 员工姓名
						sm_contet = sm_contet.replace("st_company",
								rs1.getString("coba_shortname"));// 公司名称
						sm_contet = sm_contet.replace("st_tel",
								rs1.getString("log_tel"));// 发信人联系电话
						sm_contet = sm_contet.replace("st_sname",
								rs1.getString("log_name"));// 发信人姓名
						sm_contet = sm_contet.replace("st_sex",
								rs1.getString("sex"));// 发信人性别
						sm_contet = sm_contet.replace("st_email",
								rs1.getString("log_email"));// 发信人邮箱

						sm_contet = sm_contet.replace("st_coba_name",
								rs1.getString("coba_name"));// 客服姓名
						sm_contet = sm_contet.replace("st_coba_sex",
								rs1.getString("coba_sex"));// 客服性别
						sm_contet = sm_contet.replace("st_coba_tel",
								rs1.getString("coba_tel"));// 客服电话
						sm_contet = sm_contet.replace("st_coba_email",
								rs1.getString("coba_email"));// 客服邮箱
					}

				} catch (Exception e) {
					System.out.print(e.toString());
				} finally {
					db.Close();
				}

				SMSModel model = new SMSModel();
				model.setSms_class(rs.getString("sms_type"));
				model.setSms_content(sm_contet);
				model.setSms_id(rs.getString("sms_id"));
				list.add(model);
			}
		} catch (Exception e) {
			System.out.print(e.toString());
		} finally {
			db.Close();
		}
		return list;
	}

	// 显示员工信息
	public List<SMSModel> getsmsbase(String username) throws SQLException {
		dbconn db = new dbconn();
		ResultSet rs = null;
		List<SMSModel> list = new ArrayList<SMSModel>();
		String str = "";

		String sqlstr = "select log_tel,substring(log_name,1,1),case when log_sex='M' then '先生' else '女士' end from login where log_name='"
				+ username + "'";
		try {
			rs = db.GRS(sqlstr);
			while (rs.next()) {
				SMSModel model = new SMSModel();
				model.setSms_class(rs.getString("sms_type"));
				model.setSms_content(rs.getString("sms_content"));
				model.setSms_id(rs.getString("sms_id"));
				list.add(model);
			}
		} catch (Exception e) {
			System.out.print(e.toString());
		} finally {
			db.Close();
		}
		return list;
	}

	// 图片上传
	public int sms_add(String mobile, String content, String addname) {
		System.out.println(addname);
		System.out.println(mobile);
		System.out.println(content);
		System.out.println(2);
		try {
			CallableStatement c = conn.getcall("P_EMSMS_ADD(?,?,?,?)");
			c.setString(1, addname);
			c.setString(2, mobile);
			c.setString(3, content);
			c.setInt(4, 2);
			c.execute();
			return c.getInt(4);
		} catch (SQLException e) {
			return 1;
		}
	}
}
