package dal.SysMessage;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import Conn.dbconn;
import Model.SysMessageModel;

public class SysMessage_SendListDal {

	public List<SysMessageModel> getSendList(int log_id, String str) {
		ResultSet rs = null;
		dbconn db = new dbconn();
		List<SysMessageModel> list = new ArrayList<SysMessageModel>();
		String sql = "select syme_id,syme_title,syme_content,syme_addname,"
				+ "CONVERT(nvarchar(16),syme_addtime,120) addtime,"
				+ "syme_log_id,syme_reply_id,STUFF((select '、'+symr_name "
				+ "from SysMessageRecipient where symr_syme_id=a.syme_id "
				+ "for xml path('')),1,1,'') symr_names from SysMessage a "
				+ "where syme_state=1 and syme_log_id=" + log_id + str
				+ " order by syme_addtime desc,syme_id desc";

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
				model.setSymr_name(rs.getString("symr_names"));
				list.add(model);
			}
			db.Close();
		} catch (Exception e) {
			// TODO: handle exception
			System.out.println(e.toString());
		}

		return list;
	}

	public List<SysMessageModel> getReplyList(int symr_log_id, int syme_id) {
		ResultSet rs = null;
		dbconn db = new dbconn();
		List<SysMessageModel> list = new ArrayList<SysMessageModel>();
		String sql = "select syme_id,syme_content,syme_addname,"
				+ "CONVERT(nvarchar(16),syme_addtime,120) addtime,syme_log_id,symr_id "
				+ "from SysMessage a inner join SysMessageRecipient b "
				+ "on a.syme_id=b.symr_syme_id where syme_state=1 and syme_reply_id="
				+ syme_id + " and symr_log_id=" + symr_log_id;

		try {
			rs = db.GRS(sql);
			while (rs.next()) {
				SysMessageModel model = new SysMessageModel();
				model.setSyme_id(rs.getInt("syme_id"));
				model.setSyme_content(rs.getString("syme_content"));
				model.setSyme_addname(rs.getString("syme_addname"));
				model.setSyme_addtime(rs.getString("addtime"));
				model.setSyme_log_id(rs.getInt("syme_log_id"));
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
}
