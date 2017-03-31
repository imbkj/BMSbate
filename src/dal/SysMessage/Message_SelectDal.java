package dal.SysMessage;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.zkoss.zul.ListModelList;

import Conn.dbconn;
import Model.CoBaseModel;
import Model.EmbaseModel;
import Model.LoginModel;
import Model.SysMessageModel;
import Util.UserInfo;

public class Message_SelectDal {
	// 短信查询——只有发件人和收件人可以看到
	public List<SysMessageModel> getReciList(String smwr_table, Integer swmr_id) {
		String log_id = UserInfo.getUserid();
		ResultSet rs = null;
		dbconn db = new dbconn();
		List<SysMessageModel> list = new ArrayList<SysMessageModel>();
		String sql = "select *,CONVERT(nvarchar(19),syme_addtime,120) addtime, "
				+ "STUFF((select '、'+symr_name from sysmessagerecipient "
				+ "where symr_syme_id=a.syme_id for xml path('')),1,1,'') symr_names,"
				+ "case symr_readstate when 0 then '未读' when 1 then '已读' when 2 then '已回复' end statename "
				+ "from View_Message a where syme_state=1 "
				/***************** 注释部分为查询只有发件人和收件人看到的代码 ************************************/
				+ " and (syme_addname='"
				+ UserInfo.getUsername()
				+ "'"
				+ " or symr_log_id=" + log_id + ")";
		/*****************************************************/
		sql = sql + " and smwr_table='" + smwr_table + "' and smwr_tid="
				+ swmr_id;
		try {
			rs = db.GRS(sql);
			while (rs.next()) {
				SysMessageModel model = new SysMessageModel();
				model.setSyme_id(rs.getInt("syme_id"));
				model.setSyme_title(rs.getString("syme_title"));
				model.setSyme_content(rs.getString("syme_content"));
				model.setSyme_addname(rs.getString("syme_addname"));
				model.setSyme_addtime(rs.getString("addtime"));
				model.setSyme_log_id(rs.getInt("syme_log_id"));
				model.setSyme_reply_id(rs.getInt("syme_reply_id"));
				model.setSyme_state(rs.getInt("syme_state"));
				model.setSymr_id(rs.getInt("symr_id"));
				model.setSymr_log_id(rs.getInt("symr_log_id"));
				model.setSymr_name(rs.getString("symr_names"));
				model.setSymr_readstate(rs.getInt("symr_readstate"));
				model.setSmwr_type(rs.getString("smwr_type"));
				model.setSymr_readstate(rs.getInt("symr_readstate"));
				model.setStatename(rs.getString("statename"));
				list.add(model);
			}
			db.Close();
		} catch (Exception e) {
			// TODO: handle exception
		}
		return list;
	}

	// 短信查询——所有人可以看到
	public List<SysMessageModel> getSysList(String smwr_table, Integer swmr_id) {
		String log_id = UserInfo.getUserid();
		ResultSet rs = null;
		dbconn db = new dbconn();
		List<SysMessageModel> list = new ArrayList<SysMessageModel>();
		String sql = "select *,CONVERT(nvarchar(19),syme_addtime,120) addtime, "
				+ "STUFF((select '、'+symr_name from sysmessagerecipient "
				+ "where symr_syme_id=a.syme_id for xml path('')),1,1,'') symr_names,"
				+ "case symr_readstate when 0 then '未读' when 1 then '已读' when 2 then '已回复' end statename "
				+ "from View_Message a where syme_state=1 ";
		/***************** 注释部分为查询只有发件人和收件人看到的代码 ************************************/
		/*
		 * + " and (syme_addname='" + UserInfo.getUsername() + "'" +
		 * " or symr_log_id=" + log_id + ")";
		 */
		/*****************************************************/
		sql = sql + " and smwr_table='" + smwr_table + "' and smwr_tid="
				+ swmr_id;
		try {
			rs = db.GRS(sql);
			while (rs.next()) {
				SysMessageModel model = new SysMessageModel();
				model.setSyme_id(rs.getInt("syme_id"));
				model.setSyme_title(rs.getString("syme_title"));
				model.setSyme_content(rs.getString("syme_content"));
				model.setSyme_addname(rs.getString("syme_addname"));
				model.setSyme_addtime(rs.getString("addtime"));
				model.setSyme_log_id(rs.getInt("syme_log_id"));
				model.setSyme_reply_id(rs.getInt("syme_reply_id"));
				model.setSyme_state(rs.getInt("syme_state"));
				model.setSymr_id(rs.getInt("symr_id"));
				model.setSymr_log_id(rs.getInt("symr_log_id"));
				model.setSymr_name(rs.getString("symr_names"));
				model.setSymr_readstate(rs.getInt("symr_readstate"));
				model.setSmwr_type(rs.getString("smwr_type"));
				model.setSymr_readstate(rs.getInt("symr_readstate"));
				model.setStatename(rs.getString("statename"));
				list.add(model);
			}
			db.Close();
		} catch (Exception e) {
			// TODO: handle exception
		}
		return list;
	}

	// 查询部门
	public List<LoginModel> getDepartmentList() {
		List<LoginModel> list = new ArrayList<LoginModel>();
		String sql = "select * from department";
		try {
			dbconn db = new dbconn();
			ResultSet rs = db.GRS(sql);
			while (rs.next()) {
				LoginModel m = new LoginModel();
				m.setDep_id(rs.getInt("dep_id"));
				m.setDep_name(rs.getString("dep_name"));
				list.add(m);
			}
		} catch (Exception e) {
			// TODO: handle exception
		}
		return list;
	}

	// 根据部门编号查询员工
	public List<LoginModel> getLoginList(Integer dep_id, String name) {
		List<LoginModel> list = new ArrayList<LoginModel>();
		String nn = "";
//		if (name != null && !name.equals("")) {
//			nn = " and log_name not in(" + name + ")";
//		}
		String sql = "select * from login where log_inure=1 and dep_id="
				+ dep_id + nn;
		try {
			dbconn db = new dbconn();
			ResultSet rs = db.GRS(sql);
			while (rs.next()) {
				LoginModel m = new LoginModel();
				m.setLog_id(rs.getInt("log_id"));
				m.setLog_name(rs.getString("log_name"));
				m.setLog_email(rs.getString("log_email"));
				list.add(m);
			}
		} catch (Exception e) {
			// TODO: handle exception
		}
		return list;
	}

	// 获取模板
	public List<SysMessageModel> gettemList(String str) {
		ResultSet rs = null;
		dbconn db = new dbconn();
		List<SysMessageModel> list = new ArrayList<SysMessageModel>();
		String sql = "select *,CONVERT(nvarchar(16),pmte_addtime,120) addtime "
				+ "from PubMessageTemplet where pmte_state=1" + str;

		try {
			rs = db.GRS(sql);
			while (rs.next()) {
				SysMessageModel model = new SysMessageModel();
				model.setPmte_id(rs.getInt("pmte_id"));
				model.setPmte_model(rs.getString("pmte_model"));
				model.setPmte_class(rs.getString("pmte_class"));
				model.setPmte_content(rs.getString("pmte_content"));
				model.setPmte_addname(rs.getString("pmte_addname"));
				model.setPmte_addtime(rs.getString("addtime"));
				model.setPmte_state(rs.getInt("pmte_state"));
				model.setPmte_type(rs.getString("pmte_type"));
				list.add(model);
			}
			db.Close();
		} catch (Exception e) {
			// TODO: handle exception
			System.out.println(e.toString());
		}
		return list;
	}

	// 短信查询
	public Integer isRead(String smwr_table, Integer swmr_id) {
		Integer num = 0;
		String sql = "select COUNT(*) num from View_Message a where syme_state=1 and symr_readstate=0"
				+ " and symr_log_id="
				+ UserInfo.getUserid()
				+ " and smwr_table='"
				+ smwr_table
				+ "' and smwr_tid="
				+ swmr_id;
		ResultSet rs = null;
		dbconn db = new dbconn();
		try {
			rs = db.GRS(sql);
			while (rs.next()) {
				num = num + rs.getInt("num");
			}
			db.Close();
		} catch (Exception e) {
			// TODO: handle exception
			System.out.println(e.toString());
		}
		return num;
	}

	// 查询客服
	public LoginModel getClient(String str) {
		LoginModel model = new LoginModel();
		String sql = "select * from login  where log_inure=1" + str;
		dbconn db = new dbconn();
		try {
			ResultSet rs = db.GRS(sql);
			while (rs.next()) {
				model.setLog_id(rs.getInt("log_id"));
				model.setLog_name(rs.getString("log_name"));
				model.setDep_id(rs.getInt("dep_id"));
			}
		} catch (Exception e) {

		}
		return model;
	}

	// 获取员工名称列表
	public List<EmbaseModel> getEmbaseList(String str) {
		List<EmbaseModel> list = new ListModelList<EmbaseModel>();
		dbconn db = new dbconn();
		String sql = "select top 500 a.cid,coba_company,gid,emba_name,emba_idcard,emba_mobile,emba_email,"
				+ "coba_client,emba_state from EmBase a inner join CoBase b on a.cid=b.cid "
				+ "where 1=1 " + str + " order by emba_state desc";
		try {
			list = db.find(sql.toString(), EmbaseModel.class,
					dbconn.parseSmap(EmbaseModel.class));
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return list;
	}

	public List<String> getLoginList() {
		List<String> list = new ArrayList<String>();
		String sql = "select * from login  where log_inure=1 order by log_spell";
		dbconn db = new dbconn();
		try {
			ResultSet rs = db.GRS(sql);
			list.add("");
			while (rs.next()) {
				list.add(rs.getString("log_name"));
			}

		} catch (Exception e) {

		}
		return list;
	}

	// 根据gid 获取公司简称和客服
	public CoBaseModel getCoBaseModel(Integer gid) {
		CoBaseModel m = new CoBaseModel();
		String sql = "select * from cobase a inner join embase b on a.cid=b.cid where gid="
				+ gid;
		dbconn db = new dbconn();
		try {
			ResultSet rs = db.GRS(sql);
			while (rs.next()) {
				m.setCoba_shortname(rs.getString("coba_shortname"));
				m.setCoba_client(rs.getString("coba_client"));
			}
		} catch (Exception e) {
		}
		return m;
	}

	public boolean ifExistMessage() {
		boolean flag = false;
		String sql = "select * from SysMessageRecipient where symr_readstate=0 and symr_log_id="
				+ UserInfo.getUserid();
		try{
			dbconn db=new dbconn();
			ResultSet rs=db.GRS(sql);
			while(rs.next())
			{
				flag = true;
			}
		}catch(Exception e)
		{
			
		}
		return flag;
	}
}
