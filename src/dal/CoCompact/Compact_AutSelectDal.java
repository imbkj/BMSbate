/*
 * 创建人：张志强
 * 创建时间：2013-10-17
 * 用途：合同审核列表页面Dal
 */
package dal.CoCompact;

import java.sql.ResultSet;
import java.util.ArrayList;

import Conn.dbconn;
import Model.CoCompactModel;

public class Compact_AutSelectDal {
	private static dbconn conn = new dbconn();

	// 获取所有公司合同数据集
	private ResultSet getCoCoBase() throws Exception {
		ResultSet rs = null;
		String sql = "SELECT * FROM CoCompact_CoLa_CoBase_V where coco_state=1 order by coco_id desc";
		rs = conn.GRS(sql);
		return rs;
	}

	// 根据查询语句获取公司合同数据集
	private ResultSet getCoCoBase(String str) throws Exception {
		ResultSet rs = null;
		StringBuilder sql = new StringBuilder();
		sql.append("SELECT * ");
		sql.append(" FROM CoCompact_CoLa_CoBase_V where coco_state=1 ");
		sql.append(str);
		sql.append(" order by coco_id desc");
		rs = conn.GRS(sql.toString());
		return rs;
	}

	// 获取添加人数据集
	private ResultSet getCoCoAddname(String str) throws Exception {
		ResultSet rs = null;
		String sql = "SELECT DISTINCT coco_addname FROM CoCompact_CoLa_CoBase_V WHERE coco_addname IS NOT NULL "+str+" ORDER BY coco_addname";
		rs = conn.GRS(sql);
		return rs;
	}

	// 获取所有公司合同(CoCompact_CoLa_CoBase_V)
	public ArrayList<CoCompactModel> getCoCoBaseList() {
		ArrayList<CoCompactModel> CoCoBaseList = new ArrayList<CoCompactModel>();
		try {
			ResultSet rs = getCoCoBase();
			while (rs.next()) {
				CoCoBaseList.add(new CoCompactModel(rs.getInt("coco_id"), rs
						.getString("cid"), rs.getString("coco_cola_id"), rs
						.getString("coco_compacttype"), rs
						.getString("coco_compactid"), rs
						.getString("coco_servicearea"), rs
						.getString("coco_signdate"), rs
						.getString("coco_stopdate"), rs
						.getString("coco_stopreason"), rs
						.getString("coco_stoptype"), rs
						.getString("coco_inuredate"), rs
						.getString("coco_indate"), rs.getString("coco_delay"),
						rs.getString("coco_remark"), rs.getInt("coco_state"),
						rs.getString("coco_addtime"), rs
								.getString("coco_addname"), rs
								.getString("company"), rs
								.getString("coba_shortname"), rs
								.getString("coco_returndate"), rs
								.getString("coco_signplace"), rs
								.getString("coco_money"), rs
								.getString("coco_invoice"), rs
								.getString("coco_filedate"), rs
								.getString("coco_fileid"), rs
								.getString("coco_printdate"),
								rs.getString("state"),rs.getString("coco_auditingdate"),rs.getString("coof_fee"),rs.getInt("coco_tapr_id"),
								rs.getString("coco_class"),rs.getString("coco_chs_copies"),rs.getString("coco_en_copies")));
			}
		} catch (Exception e) {
			System.out.println(e.toString());
		}
		return CoCoBaseList;
	}

	// 根据查询语句获取所有公司合同(CoCompact_CoLa_CoBase_V)
	public ArrayList<CoCompactModel> getCoCoBaseList(String str) {
		ArrayList<CoCompactModel> CoCoBaseList = new ArrayList<CoCompactModel>();
		try {
			ResultSet rs = getCoCoBase(str);
			while (rs.next()) {
				CoCoBaseList.add(new CoCompactModel(rs.getInt("coco_id"), rs
						.getString("cid"), rs.getString("coco_cola_id"), rs
						.getString("coco_compacttype"), rs
						.getString("coco_compactid"), rs
						.getString("coco_servicearea"), rs
						.getString("coco_signdate"), rs
						.getString("coco_stopdate"), rs
						.getString("coco_stopreason"), rs
						.getString("coco_stoptype"), rs
						.getString("coco_inuredate"), rs
						.getString("coco_indate"), rs.getString("coco_delay"),
						rs.getString("coco_remark"), rs.getInt("coco_state"),
						rs.getString("coco_addtime"), rs
								.getString("coco_addname"), rs
								.getString("company"), rs
								.getString("coba_shortname"), rs
								.getString("coco_returndate"), rs
								.getString("coco_signplace"), rs
								.getString("coco_money"), rs
								.getString("coco_invoice"), rs
								.getString("coco_filedate"), rs
								.getString("coco_fileid"), rs
								.getString("coco_printdate"),
								rs.getString("state"),rs.getString("coco_auditingdate"),rs.getString("coof_fee"),rs.getInt("coco_tepr_id"),
								rs.getString("coco_class"),rs.getString("coco_chs_copies"),rs.getString("coco_en_copies")));
			}
		} catch (Exception e) {
			System.out.println(e.toString());
		}
		return CoCoBaseList;
	}

	// 获取添加人
	public ArrayList getCoCoAddnameList(String str) {
		ArrayList CoCoAddnameList = new ArrayList();
		try {
			ResultSet rs = getCoCoAddname(str);
			while (rs.next()) {
				CoCoAddnameList.add(new CoCompactModel(rs
						.getString("coco_addname")));
			}
		} catch (Exception e) {
			System.out.println(e.toString());
		}
		return CoCoAddnameList;
	}
}
