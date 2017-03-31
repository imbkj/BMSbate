package dal.SysMessage;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import Conn.dbconn;
import Model.SysMessageModel;

public class SysMessage_NotReadInfoListDal {
	public List<SysMessageModel> getNotReadInfoList(int syme_log_id,
			int symr_log_id, String str) {
		ResultSet rs = null;
		dbconn db = new dbconn();
		List<SysMessageModel> list = new ArrayList<SysMessageModel>();
		String sql = "select syme_id,syme_addname,syme_title,syme_content,"
				+ "convert(nvarchar(16),syme_addtime,120) syme_addtime,symr_id,symr_readstate "
				+ "from SysMessageRecipient a inner join SysMessage b "
				+ "on a.symr_syme_id=b.syme_id where syme_state=1 "
				+ "and syme_log_id=" + syme_log_id + " and symr_log_id="
				+ symr_log_id + str + " order by syme_id desc,symr_readstate";

		try {
			rs = db.GRS(sql);
			while (rs.next()) {
				SysMessageModel model = new SysMessageModel();
				model.setSyme_id(rs.getInt("syme_id"));
				model.setSyme_addname(rs.getString("syme_addname"));
				model.setSyme_title(rs.getString("syme_title"));
				model.setSyme_content(rs.getString("syme_content"));
				model.setSyme_addtime(rs.getString("syme_addtime"));
				model.setSymr_id(rs.getInt("symr_id"));
				model.setSymr_readstate(rs.getInt("symr_readstate"));
				list.add(model);
			}
			db.Close();
		} catch (Exception e) {
			// TODO: handle exception
			System.out.println(e.toString());
		}

		return list;
	}

	public List<SysMessageModel> getNotReadRecipientlistList(int syme_id) {
		ResultSet rs = null;
		dbconn db = new dbconn();
		List<SysMessageModel> list = new ArrayList<SysMessageModel>();
		String sql = "select symr_name,symr_log_id,dep_name "
				+ "from SysMessageRecipient a inner join SysMessage b "
				+ "on a.symr_syme_id = b.syme_id left outer join Login c "
				+ "on a.symr_log_id=c.log_id left outer join department d "
				+ "on c.dep_id=d.dep_id where syme_id=" + syme_id;

		try {
			rs = db.GRS(sql);
			while (rs.next()) {
				SysMessageModel model = new SysMessageModel();
				model.setSymr_name(rs.getString("symr_name"));
				model.setSymr_log_id(rs.getInt("symr_log_id"));
				model.setDep_name(rs.getString("dep_name"));
				list.add(model);
			}
			db.Close();
		} catch (Exception e) {
			// TODO: handle exception
			System.out.println(e.toString());
		}

		return list;
	}

	public List<SysMessageModel> getReplyMessageList(int syme_id,
			int syme_log_id) {
		ResultSet rs = null;
		dbconn db = new dbconn();
		List<SysMessageModel> list = new ArrayList<SysMessageModel>();
		String sql = "select *,convert(nvarchar(19),syme_addtime,120) addtime "
				+ "from SysMessage where syme_reply_id=" + syme_id
				+ "and syme_state=1 and syme_log_id=" + syme_log_id
				+ " order by syme_id desc";

		try {
			rs = db.GRS(sql);
			while (rs.next()) {
				SysMessageModel model = new SysMessageModel();
				model.setSyme_id(rs.getInt("syme_id"));
				model.setSyme_content(rs.getString("syme_content"));
				model.setSyme_addname(rs.getString("syme_addname"));
				model.setSyme_addtime(rs.getString("addtime"));
				model.setSyme_log_id(rs.getInt("syme_log_id"));
				list.add(model);
			}
			db.Close();
		} catch (Exception e) {
			// TODO: handle exception
			System.out.println(e.toString());
		}

		return list;
	}

	public List<SysMessageModel> findAll(int syme_id, int syme_log_id) {
		ResultSet rs = null;
		dbconn db = new dbconn();
		List<SysMessageModel> list = new ArrayList<SysMessageModel>();
		String sql = "SELECT distinct a.syme_id,symr_id,syme_title,syme_content,syme_addname,symr_readstate,"
				+ "convert(nvarchar(16),syme_addtime,120)+'('+datename(weekday,syme_addtime)+')' "
				+ "syme_addtime,syme_log_id,syme_url,syme_para,smwr_table,smwr_tid,"
				+ "syme_state,syme_reply_id,STUFF((select '、'+symr_name from sysmessagerecipient "
				+ "where symr_syme_id=a.syme_id for xml path('')),1,1,'') symr_names "
				+ "FROM SysMessage a inner join SysMessageRecipient b on a.syme_id=b.symr_syme_id "
				+ " left join SysMessageWfRelation c on a.syme_id=c.syme_id " +
				" where syme_reply_id=(select syme_reply_id from SysMessage where syme_id="
				+ syme_id + ") and (symr_log_id=" + syme_log_id
				+ " or syme_log_id=" + syme_log_id + ") and syme_state=1 order by a.syme_id desc";
		try {
			rs = db.GRS(sql);
			while (rs.next()) {
				SysMessageModel model = new SysMessageModel();
				model.setSyme_id(rs.getInt("syme_id"));
				model.setSyme_title(rs.getString("syme_title"));
				model.setSyme_content(rs.getString("syme_content"));
				model.setSyme_addname(rs.getString("syme_addname"));
				model.setSyme_addtime(rs.getString("syme_addtime"));
				model.setSyme_log_id(rs.getInt("syme_log_id"));
				model.setSyme_state(rs.getInt("syme_state"));
				model.setSyme_reply_id(rs.getInt("syme_reply_id"));
				model.setSymr_name(rs.getString("symr_names"));
				model.setSyme_url(rs.getString("syme_url"));
				model.setSyme_para(rs.getString("syme_para"));
				model.setSmwr_table(rs.getString("smwr_table"));
				model.setSmwr_tid(rs.getInt("smwr_tid"));
				model.setSymr_readstate(rs.getInt("symr_readstate"));
				model.setSymr_id(rs.getInt("symr_id"));
				list.add(model);
			}
			db.Close();
		} catch (Exception e) {
			// TODO: handle exception
			System.out.println(e.toString());
		}

		return list;
	}

	public int updateReadState(int symr_id, int state) {
		int row = 0;
		PreparedStatement psmt = null;
		dbconn db = new dbconn();
		String sql = "update sysmessagerecipient set symr_readstate=" + state
				+ ",symr_readtime=getdate() " + "where symr_id=?";
		try {
			psmt = db.getpre(sql);
			psmt.setInt(1, symr_id);

			row = psmt.executeUpdate();
		} catch (Exception e) {
			// TODO: handle exception
		}
		return row;
	}
}
