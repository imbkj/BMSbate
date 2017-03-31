package dal.SysMessage;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import Conn.dbconn;
import Model.SysMessageModel;

public class SysMessage_ReciListDal {

	public List<SysMessageModel> getReciList(int log_id, String str) {
		ResultSet rs = null;
		dbconn db = new dbconn();
		List<SysMessageModel> list = new ArrayList<SysMessageModel>();
		String sql = "select *,CONVERT(nvarchar(16),syme_addtime,120) addtime,"
				+ "STUFF((select '、'+symr_name from sysmessagerecipient "
				+ "where symr_syme_id=a.syme_id for xml path('')),1,1,'') symr_names "
				+ "from view_sysmessage a where syme_state=1 "
				+ "and symr_log_id=" + log_id + str
				+ " order by syme_id desc";

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
				model.setSymr_log_id(log_id);
				model.setSymr_name(rs.getString("symr_names"));
				model.setSymr_readstate(rs.getInt("symr_readstate"));
				model.setSmwr_table(rs.getString("smwr_table"));
				model.setSmwr_tid(rs.getInt("smwr_tid"));
				list.add(model);
			}
			db.Close();
		} catch (Exception e) {
			// TODO: handle exception
		}
		return list;
	}

	public List<SysMessageModel> getReplyList(int syme_id, int log_id) {
		ResultSet rs = null;
		dbconn db = new dbconn();
		List<SysMessageModel> list = new ArrayList<SysMessageModel>();
		String sql = "select *,CONVERT(nvarchar(16),syme_addtime,120) addtime from sysmessage"
				+ " where syme_state=1 and syme_reply_id="
				+ syme_id
				+ " and syme_log_id=" + log_id;

		try {
			rs = db.GRS(sql);
			while (rs.next()) {
				SysMessageModel model = new SysMessageModel();
				model.setSyme_id(rs.getInt("syme_id"));
				model.setSyme_content(rs.getString("syme_content"));
				model.setSyme_addname(rs.getString("syme_addname"));
				model.setSyme_addtime(rs.getString("addtime"));
				model.setSyme_log_id(rs.getInt("syme_log_id"));
				model.setSyme_reply_id(rs.getInt("syme_reply_id"));
				model.setSyme_state(rs.getInt("syme_state"));
				/*model.setSymr_id(rs.getInt("symr_id"));
				model.setSymr_log_id(rs.getInt("symr_log_id"));
				model.setSymr_name(rs.getString("symr_name"));
				model.setSymr_readstate(rs.getInt("symr_readstate"));*/
				list.add(model);
			}
			db.Close();
		} catch (Exception e) {
			System.out.println(e.toString());
		}
		return list;
	}
}
