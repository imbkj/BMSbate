package dal.EmHouse;

import java.sql.SQLException;
import java.util.List;

import org.zkoss.zul.ListModelList;

import Conn.dbconn;
import Model.EmHouseUploadErrModel;

public class EmHouseUploadErrDal {

	public List<EmHouseUploadErrModel> getlist(EmHouseUploadErrModel m) {
		List<EmHouseUploadErrModel> list = new ListModelList<>();
		dbconn db = new dbconn();
		String sql = "select ehle_id,ehle_houseid,ehle_idcard,ehle_errMessage,ehle_state,ehle_addtime addtime,ehle_addname,log_id logId,"
				+ "b.emhc_id,b.cid,b.gid,emhc_company company, emhc_name name,emhc_companyid companyid,emhc_radix radix, emhc_cpp cpp,emhc_change change,case when emhc_id is null then '未匹配有效数据' else case emhc_ifdeclare when 0 then '未申报' when 2 then '申报中' end end declareName,emhc_addname addname, emhc_addtime addtime2,"
				+ "b.emhc_ifprogress,b.emhc_tapr_id"
				+ " from EmHouseUploadErr a"
				+ " left join emhousechange b on (isnull(a.ehle_houseid,'')=b.emhc_houseid or isnull(a.ehle_idcard,'')=b.emhc_idcard) and a.ehle_change=b.emhc_change and b.emhc_tid=0 and emhc_ifdeclare in (0,2)";

		if (m.getOwnmonth() != null && !m.getOwnmonth().equals("")) {
			sql += " and b.ownmonth=" + m.getOwnmonth() + "";
		}
		sql += " left join login c on b.emhc_addname=c.log_name and c.log_inure=1"
				+ " where 1=1";

		if (m.getEhle_state() != null && !m.getEhle_state().equals("")) {
			sql += " and ehle_state=" + m.getEhle_state();
		}

		if (m.getEhle_addname() != null && !m.getEhle_addname().equals("")) {
			sql += " and ehle_addname='" + m.getEhle_addname() + "'";
		}

		if (m.getEhle_change() != null && !m.getEhle_change().equals("")) {
			sql += " and ehle_change='" + m.getEhle_change() + "'";
		}

		sql += " order by emhc_change desc,ehle_change,emhc_company,emhc_name,ehle_idcard,ehle_houseid";
		System.out.println(sql);
		try {
			list = db.find(sql, EmHouseUploadErrModel.class, null);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return list;
	}

	// 添加数据
	public Integer add(EmHouseUploadErrModel m) {
		Integer i = 0;
		dbconn db = new dbconn();
		String sql = "insert into EmHouseUploadErr(ehle_houseid,ehle_idcard,ehle_errMessage,ehle_change,ehle_addname)"
				+ "values(?,?,?,?,?)";
		try {
			i = db.insertAndReturnKey(sql, m.getEhle_houseid(),
					m.getEhle_idcard(), m.getEhle_errMessage(),
					m.getEhle_change(), m.getEhle_addname());
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return i;
	}

	// 删除未提交数据
	public Integer del(String name) {
		Integer i = 0;
		dbconn db = new dbconn();
		String sql = "delete from EmHouseUploadErr where ehle_state=1 and ehle_addname=?";
		try {
			i = db.updatePrepareSQL(sql, name);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return i;
	}

	// 删除数据
	public Integer delId(Integer id) {
		Integer i = 0;
		dbconn db = new dbconn();
		String sql = "delete from EmHouseUploadErr where ehle_id=?";
		try {
			i = db.updatePrepareSQL(sql, id);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return i;
	}
}
