package dal.CoCompact;

import java.sql.SQLException;
import java.util.List;

import org.zkoss.zul.ListModelList;

import Conn.dbconn;
import Model.CoCompactCppAduitModel;

public class CoCompactCppAduitDal {

	/**
	 * @Title: getlist
	 * @Description: TODO(查询合同公积金比例变更数据)
	 * @param cm
	 * @return
	 * @return List<CoCompactCppAduitModel> 返回类型
	 * @throws
	 */
	public List<CoCompactCppAduitModel> getlist(CoCompactCppAduitModel cm) {
		dbconn db = new dbconn();
		List<CoCompactCppAduitModel> list = new ListModelList<>();
		String sql = "select  coca_id, a.cid,coca_coco_id,coca_cpp,coca_houseid, coca_state, coca_aduit, "
				+ "coca_declaretime,coca_declarename, coca_addtime, coca_addname, coca_remark,coca_tapr_id,"
				+ " coco_compactid,coba_company"
				+ " from CoCompactCppAduit a"
				+ " inner join cocompact b on a.coca_coco_id=b.coco_id"
				+ " inner join cobase c on a.cid=c.cid" + " where 1=1";
		if (cm.getCid() != null && !cm.getCid().equals("")) {
			sql += " and a.cid=" + cm.getCid();
		}
		if (cm.getCoca_coco_id() != null && !cm.getCoca_coco_id().equals("")) {
			sql += " and coca_coco_id=" + cm.getCoca_coco_id();
		}
		if (cm.getCoca_id()!=null && !cm.getCoca_id().equals("")) {
			sql += " and coca_id=" + cm.getCoca_id();
		}
		if (cm.getCoca_state()!=null && !cm.getCoca_state().equals("")) {
			sql += " and coca_state=" + cm.getCoca_state();
		}
		if (cm.getCoca_aduit()!=null && !cm.getCoca_aduit().equals("")) {
			sql += " and coca_aduit=" + cm.getCoca_aduit();
		}
		System.out.println(sql);
		try {
			list = db.find(sql, CoCompactCppAduitModel.class, null);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return list;
	}

	/**
	 * @Title: add
	 * @Description: TODO(新增)
	 * @param cm
	 * @return
	 * @return Integer 返回类型
	 * @throws
	 */
	public Integer add(CoCompactCppAduitModel cm) {
		dbconn db = new dbconn();
		Integer i = 0;
		String sql = "insert into CoCompactCppAduit(cid,coca_coco_id,coca_cpp,coca_houseid,coca_state,coca_aduit,"
				+ "coca_addtime,coca_addname,coca_remark)"
				+ "values(?,?,?,?,1,0,getdate(),?,?)";
		try {

			i = db.insertAndReturnKey(sql, cm.getCid(), cm.getCoca_coco_id(),
					cm.getCoca_cpp(), cm.getCoca_houseid(),
					cm.getCoca_addname(), cm.getCoca_remark());
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return i;
	}

	public Integer mod(CoCompactCppAduitModel cm, Integer id) {
		dbconn db = new dbconn();
		Integer i = 0;
		if (id != null && id > 0) {

			String sql = "update CoCompactCppAduit set coca_modtime=getdate(),coca_modname=?";
			if (cm.getCoca_remark() != null && !cm.getCoca_remark().equals("")) {
				sql += ",coca_remark='" + cm.getCoca_remark() + "'";
			}
			if (cm.getCoca_aduit() != null && !cm.getCoca_aduit().equals("")) {
				sql += ",coca_aduit=" + cm.getCoca_aduit();
			}
			if (cm.getCoca_declarename() != null
					&& !cm.getCoca_declarename().equals("")) {
				sql += ",coca_declarename='" + cm.getCoca_declarename() + "'";
			}
			if (cm.getCoca_declaretime() != null
					&& !cm.getCoca_declaretime().equals("")) {
				sql += ",coca_declaretime='" + cm.getCoca_declaretime() + "'";
			}
			if (cm.getCoca_backreason()!=null && !cm.getCoca_backreason().equals("")) {
				sql += ",coca_backreasion='" + cm.getCoca_backreason() + "'";
			}
			
			if (cm.getCoca_state() != null
					&& !cm.getCoca_state().equals("")) {
				sql += ",coca_state=" + cm.getCoca_state();
			}
			

			sql += " where coca_id=?";
			try {
				i = db.updatePrepareSQL(sql, cm.getCoca_modname(), id);
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return i;
	}

	// 更新任务流程ID
	public Integer updateTaprId(Integer dataId, Integer taprId)
			throws SQLException {
		Integer i = 0;
		dbconn db = new dbconn();
		String sql = "update CoCompactCppAduit set coca_tapr_id=? where coca_id=?";
		i = db.updatePrepareSQL(sql, taprId, dataId);
		return i;
	}
}
