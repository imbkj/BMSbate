package dal.EmBodyCheck;

import java.util.ArrayList;
import java.util.List;

import Conn.dbconn;
import Model.EmBodyCheckDateListModel;
import Util.UserInfo;

public class EmBc_DateListSelectDal {
	// 体检时间信息
	public List<EmBodyCheckDateListModel> getEmBodyCheckDateListInfo(
			String str, boolean mod) {
		List<EmBodyCheckDateListModel> list = new ArrayList<EmBodyCheckDateListModel>();
		dbconn db = new dbconn();
		String sql = "select a.cid,a.gid,coba_shortname,emba_name, emba_sex,emba_idcard,"
				+ "emba_indate,coba_client,ebdl_bctime bctime "
				+ " from embodycheckdatelist a  left join cobase b on a.cid=b.cid inner join embase c "
				+ " on a.gid=c.gid and a.cid=c.cid where c.emba_state =1";
		if (mod) {
			sql = sql
					+ " and a.CID in ( select cid from DataPopedom where log_id="
					+ UserInfo.getUserid() + " and  Dat_edited=1 )";
		} else {
			sql = sql
					+ " and a.CID in ( select cid from DataPopedom where log_id="
					+ UserInfo.getUserid() + " and dat_selected=1 )";
		}
		sql = sql + str;
		sql+=" order by month(ebdl_bctime),coba_shortname,emba_name";
		try {
			list = db.find(sql, EmBodyCheckDateListModel.class,
					dbconn.parseSmap(EmBodyCheckDateListModel.class));
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}
}
