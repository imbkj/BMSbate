package dal.CoReg;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;


import Conn.dbconn;
import Model.ResponsibilityBookModel;

public class CoReg_ResponseBookDal extends dbconn{

	// 查询就业登记列表
	public List<ResponsibilityBookModel> getRlist() {
		List<ResponsibilityBookModel> list = new ArrayList<ResponsibilityBookModel>();
		String sql = "select rebk_id,rebk_state,a.cid,coba_shortname,CONVERT(varchar(50),rebk_signdate,120)signtime,rebk_limit,rebk_addname,CONVERT(varchar(50),rebk_addtime,120)addtime,rebk_cori_id,rebk_step_state from ResponsibilityBook a "
				+ " left join CoBase b on a.CID = b.cid order by rebk_addtime desc";
		try {
		ResultSet rs = GRS(sql);
		while(rs.next()){
			ResponsibilityBookModel m = new ResponsibilityBookModel();
			m.setRebk_id(rs.getInt("rebk_id"));
			m.setRebk_state(rs.getInt("rebk_state"));
			m.setCid(rs.getInt("cid"));
			m.setCoba_shortname(rs.getString("coba_shortname"));
			m.setRebk_signdate(rs.getString("signtime"));
			m.setRebk_limit(rs.getString("rebk_limit"));
			m.setRebk_addname(rs.getString("rebk_addname"));
			m.setAddtime(rs.getString("addtime"));
			m.setRebk_cori_id(rs.getInt("rebk_cori_id"));
			m.setRebk_step_state(rs.getString("rebk_step_state"));
			list.add(m);
		}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}
}
