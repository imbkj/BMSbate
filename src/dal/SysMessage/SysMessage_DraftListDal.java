package dal.SysMessage;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import Conn.dbconn;
import Model.SysMessageModel;

public class SysMessage_DraftListDal {

	public List<SysMessageModel> getDraftList(int syme_log_id, String str) {
		ResultSet rs = null;
		dbconn db = new dbconn();
		List<SysMessageModel> list = new ArrayList<SysMessageModel>();
		String sql = "select *,CONVERT(nvarchar(19),syme_addtime,120) addtime,"
				+ "STUFF(" + "(select '、'+symr_name from sysmessagerecipient "
				+ "where symr_syme_id=a.syme_id " + "for xml path(''))"
				+ ",1,1,'') symr_names " + "from SysMessage a "
				+ "where syme_state=0 and syme_log_id=" + syme_log_id + str
				+ " order by syme_id desc";

		try {
			rs = db.GRS(sql);
			while (rs.next()) {
				SysMessageModel model = new SysMessageModel();
				model.setSyme_id(rs.getInt("syme_id"));
				model.setSyme_content(rs.getString("syme_content"));
				model.setSyme_addname(rs.getString("syme_addname"));
				model.setSyme_addtime(rs.getString("addtime"));
				model.setSyme_log_id(syme_log_id);
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
	
	public int delete(int syme_id){
		int row = 0;
		PreparedStatement psmt = null;
		dbconn db = new dbconn();
		String sql = "delete from sysmessage where syme_id=?";
		try {
			psmt = db.getpre(sql);
			psmt.setInt(1, syme_id);

			row = psmt.executeUpdate();
			db.Close();
		} catch (Exception e) {
			// TODO: handle exception
		}
		
		return row;
	}
	
	public List<SysMessageModel> findAll(int syme_id, int syme_log_id) {
		ResultSet rs = null;
		dbconn db = new dbconn();
		List<SysMessageModel> list = new ArrayList<SysMessageModel>();
		String sql = "SELECT distinct syme_id,syme_title,syme_content,syme_addname,"
				+ "convert(nvarchar(16),syme_addtime,120)+'('+datename(weekday,syme_addtime)+')' "
				+ "syme_addtime,syme_log_id,syme_url,syme_para,"
				+ "syme_state,syme_reply_id,STUFF((select '、'+symr_name from sysmessagerecipient "
				+ "where symr_syme_id=a.syme_id for xml path('')),1,1,'') symr_names "
				+ "FROM SysMessage a left outer join SysMessageRecipient b on a.syme_id=b.symr_syme_id "
				+ "where syme_reply_id=(select syme_reply_id from SysMessage where syme_id="
				+ syme_id + ") and (symr_log_id=" + syme_log_id
				+ " or syme_log_id=" + syme_log_id + ") and syme_id<="
				+ syme_id + " order by syme_id desc";

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
