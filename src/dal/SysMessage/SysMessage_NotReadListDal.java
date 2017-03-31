package dal.SysMessage;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import Conn.dbconn;
import Model.SysMessageModel;

public class SysMessage_NotReadListDal {

	public List<SysMessageModel> getNotReadList(int log_id, String str) {
		ResultSet rs = null;
		dbconn db = new dbconn();
		List<SysMessageModel> list = new ArrayList<SysMessageModel>();
		// String sql =
		// "select COUNT(*) count,syme_addname,syme_log_id,dep_name from SysMessage a "
		// + "inner join SysMessageRecipient b on a.syme_id=b.symr_syme_id "
		// + "left outer join Login c on a.syme_log_id=c.log_id "
		// + "left outer join department d on c.dep_id=d.dep_id "
		// + "where symr_log_id="
		// + log_id
		// + " and syme_state=1 and symr_readstate=0"
		// + str
		// + " group by syme_addname,syme_log_id,dep_name";

		String sql = "select distinct notreadcount,recicount,syme_addname,a.syme_log_id,dep_name "
				+ "from SysMessage a inner join SysMessageRecipient b on a.syme_id=b.symr_syme_id "
				+ "left outer join Login c on a.syme_log_id=c.log_id left outer join department d "
				+ "on c.dep_id=d.dep_id left outer join (select COUNT(*) notreadcount,syme_log_id "
				+ "from SysMessage a inner join SysMessageRecipient b on a.syme_id=b.symr_syme_id "
				+ "where symr_readstate=0 and syme_state=1 and symr_log_id="
				+ log_id
				+ " group by syme_log_id) e "
				+ "on a.syme_log_id=e.syme_log_id left outer join "
				+ "(select COUNT(*) recicount,syme_log_id from SysMessage a "
				+ "inner join SysMessageRecipient b on a.syme_id=b.symr_syme_id "
				+ "where syme_state=1 and symr_log_id="
				+ log_id
				+ " group by syme_log_id) f on a.syme_log_id=f.syme_log_id "
				+ "where syme_state=1 and b.symr_log_id=" + log_id + str
				+ "  order by notreadcount desc,dep_name";

		try {
			rs = db.GRS(sql);
			while (rs.next()) {
				SysMessageModel model = new SysMessageModel();
				model.setRow(rs.getInt("notreadcount"));
				model.setSyme_addname(rs.getString("syme_addname"));
				model.setSyme_log_id(rs.getInt("syme_log_id"));
				model.setDep_name(rs.getString("dep_name"));
				model.setRecicount(rs.getInt("recicount"));
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
