package dal.Archives;

import java.sql.SQLException;
import java.util.List;

import org.zkoss.zul.ListModelList;

import Conn.dbconn;
import Model.EmArchiveSetupModel;

public class EmArchiveSetupDal {
	public List<EmArchiveSetupModel> getSetUpList() {
		List<EmArchiveSetupModel> list = new ListModelList<EmArchiveSetupModel>();
		dbconn db = new dbconn();
		String sql = "select ease_id,ease_name,ease_rsfee,ease_hjfee,ease_rshjfee,ease_rshjloan,ease_latefee,ease_latedate,ease_payment "
				+ "from EmArchiveSetup where ease_state=1 order by ease_sort,ease_name";
		try {
			list = db.find(sql, EmArchiveSetupModel.class,
					dbconn.parseSmap(EmArchiveSetupModel.class));
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return list;
	}

	public List<EmArchiveSetupModel> getSetUpList(EmArchiveSetupModel em) {
		List<EmArchiveSetupModel> list = new ListModelList<EmArchiveSetupModel>();
		dbconn db = new dbconn();
		String sql = "select ease_id,ease_name,ease_rsfee,ease_hjfee,ease_rshjfee,"
				+ "ease_rshjloan,ease_latefee,ease_latedate,ease_payment,ease_limit "
				+ "from EmArchiveSetup" + " where 1=1 ";
		if (em.getEase_id() != null) {
			if (!em.getEase_id().equals("")) {
				sql = sql + " and ease_id= " + em.getEase_id();
			}
		}
		if (em.getEase_name() != null) {
			if (!em.getEase_name().equals("")) {
				sql = sql + " and ease_name= '" + em.getEase_name() + "'";
			}
		}

		if (em.getEase_state() != null) {
			if (!em.getEase_state().equals("")) {
				sql = sql + " and ease_state= " + em.getEase_state();
			}
		}

		sql = sql + " order by ease_sort,ease_name";
		try {
			list = db.find(sql, EmArchiveSetupModel.class,
					dbconn.parseSmap(EmArchiveSetupModel.class));
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return list;
	}
}
