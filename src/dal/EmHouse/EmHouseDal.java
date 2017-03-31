package dal.EmHouse;

import java.sql.SQLException;
import java.util.List;

import org.zkoss.zul.ListModelList;

import Conn.dbconn;
import Model.EmHouseModel;

public class EmHouseDal {

	/**
	 * @Title: houselist
	 * @Description: TODO(查询员工历史参保记录)
	 * @param gid
	 * @param idcard
	 * @return
	 * @return List<EmHouseModel> 返回类型
	 * @throws
	 */
	public List<EmHouseModel> houselist(Integer gid, String idcard,
			String houseid) {
		List<EmHouseModel> list = new ListModelList<>();
		dbconn db = new dbconn();
		String sql = "select ownmonth,emhu_name,emhu_idcard,emhu_houseid,emhu_radix,emhu_hj,emhu_cpp,emhu_opp,emhu_cp,emhu_op,emhu_total "
				+ " from emhouse"
				+ " where gid=? or CASE LEN(Emhu_IDCARD) WHEN 15 THEN DBO.idcard2(Emhu_IDCARD) ELSE Emhu_IDCARD END=? or emhu_houseid=?"
				+ " order by ownmonth desc";
		try {
			list = db.find(sql, EmHouseModel.class, null, gid, idcard, houseid);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return list;
	}
}
