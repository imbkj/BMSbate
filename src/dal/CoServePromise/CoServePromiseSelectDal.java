package dal.CoServePromise;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.zkoss.zul.ListModelList;

import Conn.dbconn;
import Model.CoBaseServePromiseModel;

public class CoServePromiseSelectDal {

	// 查询单位系统服务约定信息
	public List<CoBaseServePromiseModel> getPromiseList(String str) {
		List<CoBaseServePromiseModel> list = new ArrayList<CoBaseServePromiseModel>();
		dbconn db = new dbconn();
		String sql = "select convert(char(10),cosp_addtime,120) as cosp_addtime,cosp_enty_caliname,"
				+ " convert(char(10),cosp_acnt_sbukeyreachtime,120) cosp_acnt_sbukeyreachtime,"
				+ " convert(char(10),cosp_anct_houseukeyreachtime,120) cosp_anct_houseukeyreachtime,"
				+ " * from CoBaseServePromise " + "where 1=1 " + str;
		try {
			list = db.find(sql, CoBaseServePromiseModel.class,
					dbconn.parseSmap(CoBaseServePromiseModel.class));
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return list;
	}

	// 查询是否已有材料
	public Integer ifHasDoc(Integer doin_id, Integer dsin_tid) {
		String sql = " SELECT dire_puzu_id,dsin_addtime,dsin_addname,"
				+ "* FROM DocumentsSubmitInfo a inner join DipzRelation b "
				+ " on a.dsin_dire_id=b.dire_id and dire_puzu_id=" + doin_id
				+ " and dsin_tid=" + dsin_tid;
		System.out.println(sql);
		Integer k = 0;
		dbconn db = new dbconn();
		try {
			ResultSet rs = db.GRS(sql);
			while (rs.next()) {
				k = k + 1;
			}
		} catch (Exception e) {

		}
		return k;
	}

	// 查询体检联系人信息
	public List<CoBaseServePromiseModel> getlist(Integer cid) {
		List<CoBaseServePromiseModel> list = new ListModelList<>();
		dbconn db = new dbconn();
		String sql = "select * from CoBaseServePromise where cid=?";
		try {
			list = db.find(sql, CoBaseServePromiseModel.class, null, cid);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return list;

	}
}
