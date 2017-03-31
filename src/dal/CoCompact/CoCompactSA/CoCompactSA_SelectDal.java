package dal.CoCompact.CoCompactSA;

import java.sql.ResultSet;
import java.util.ArrayList;

import Conn.dbconn;
import Model.CoCompactSAModel;

public class CoCompactSA_SelectDal {
	private static dbconn conn = new dbconn();

	// 获取所有公司合同协议数据集
	private ResultSet getCCSABase() throws Exception {
		ResultSet rs = null;
		String sql = "SELECT * FROM CoCompactSA_CoCo_CoBase_V order by ccsa_id desc";
		rs = conn.GRS(sql);
		return rs;
	}

	// 根据查询语句获取公司协议合同数据集
	private ResultSet getCCSABase(String str) throws Exception {
		ResultSet rs = null;
		StringBuilder sql = new StringBuilder();
		sql.append("SELECT * ");
		sql.append(" FROM CoCompactSA_CoCo_CoBase_V where 1=1 ");
		sql.append(str);
		sql.append(" order by ccsa_id desc");
		System.out.println("SELECT *  FROM CoCompactSA_CoCo_CoBase_V where 1=1 "+str+" order by ccsa_id desc");
		rs = conn.GRS(sql.toString());
		return rs;
	}

	// 获取添加人数据集
	private ResultSet getCCSAAddname(String str) throws Exception {
		ResultSet rs = null;
		String sql = "SELECT DISTINCT ccsa_addname FROM CoCompactSA_CoCo_CoBase_V WHERE ccsa_addname IS NOT NULL "
				+ str + " ORDER BY ccsa_addname";
		rs = conn.GRS(sql);
		return rs;
	}

	// 获取所有公司合同协议(CoCompactSA_CoCo_CoBase_V)
	public ArrayList<CoCompactSAModel> getCCSABaseList() {
		ArrayList<CoCompactSAModel> CCSABaseList = new ArrayList<CoCompactSAModel>();
		try {
			ResultSet rs = getCCSABase();
			while (rs.next()) {
				CCSABaseList.add(new CoCompactSAModel(rs.getInt("ccsa_id"), rs
						.getInt("ccsa_coco_id"), rs.getInt("cid"), rs
						.getString("ccsa_type"), rs.getString("ccsa_said"), rs
						.getString("ccsa_signdate"), rs
						.getString("ccsa_stopdate"), rs
						.getString("ccsa_stopreason"), rs
						.getString("ccsa_stoptype"), rs
						.getString("ccsa_inuredate"), rs
						.getString("ccsa_indate"), rs.getString("ccsa_delay"),
						rs.getString("ccsa_auditingdate"), rs
								.getString("ccsa_printdate"), rs
								.getString("ccsa_returndate"), rs
								.getString("ccsa_signplace"), rs
								.getString("ccsa_filedate"), rs
								.getString("ccsa_fileid"), rs
								.getString("ccsa_chs_copies"), rs
								.getString("ccsa_en_copies"), rs
								.getInt("ccsa_state"), rs.getString("state"),
						rs.getString("ccsa_remark"), rs
								.getString("ccsa_addname"), rs
								.getString("ccsa_addtime"), rs
								.getString("coba_company"), rs
								.getString("coba_shortname"), rs
								.getString("coco_compactid"), rs
								.getInt("ccsa_tapr_id")));
			}
		} catch (Exception e) {
			System.out.println(e.toString());
		}
		return CCSABaseList;
	}

	// 根据查询语句获取所有公司合同协议(CoCompactSA_CoCo_CoBase_V)
	public ArrayList<CoCompactSAModel> getCCSABaseList(String str) {
		ArrayList<CoCompactSAModel> CCSABaseList = new ArrayList<CoCompactSAModel>();
		try {
			ResultSet rs = getCCSABase(str);
			while (rs.next()) {
				CCSABaseList.add(new CoCompactSAModel(rs.getInt("ccsa_id"), rs
						.getInt("ccsa_coco_id"), rs.getInt("cid"), rs
						.getString("ccsa_type"), rs.getString("ccsa_said"), rs
						.getString("ccsa_signdate"), rs
						.getString("ccsa_stopdate"), rs
						.getString("ccsa_stopreason"), rs
						.getString("ccsa_stoptype"), rs
						.getString("ccsa_inuredate"), rs
						.getString("ccsa_indate"), rs.getString("ccsa_delay"),
						rs.getString("ccsa_auditingdate"), rs
								.getString("ccsa_printdate"), rs
								.getString("ccsa_returndate"), rs
								.getString("ccsa_signplace"), rs
								.getString("ccsa_filedate"), rs
								.getString("ccsa_fileid"), rs
								.getString("ccsa_chs_copies"), rs
								.getString("ccsa_en_copies"), rs
								.getInt("ccsa_state"), rs.getString("state"),
						rs.getString("ccsa_remark"), rs
								.getString("ccsa_addname"), rs
								.getString("ccsa_addtime"), rs
								.getString("coba_company"), rs
								.getString("coba_shortname"), rs
								.getString("coco_compactid"), rs
								.getInt("ccsa_tapr_id")));
			}
		} catch (Exception e) {
			System.out.println(e.toString());
		}
		return CCSABaseList;
	}

	// 获取添加人
	public ArrayList getCoCoAddnameList(String str) {
		ArrayList CCSAAddnameList = new ArrayList();
		try {
			ResultSet rs = getCCSAAddname(str);
			while (rs.next()) {
				CCSAAddnameList.add(new CoCompactSAModel(rs
						.getString("ccsa_addname")));
			}
		} catch (Exception e) {
			System.out.println(e.toString());
		}
		return CCSAAddnameList;
	}
	
	//获取补充协议的模板
	public ArrayList<String> getCoCompactSAPubOffice(int id)
	{
		ArrayList<String> list=new ArrayList<String>();
		try {
			ResultSet rs = null;
			dbconn db = new dbconn();
			String sqlstr = "select puof_url from CoCompactSA a,PubOffice b where a.ccsa_id=b.puof_tid and puof_pute_id="+id;
			rs = db.GRS(sqlstr);
			while (rs.next()) {
				list.add(rs.getString("puof_url"));
			}
		} catch (Exception e) {
			System.out.println(e.toString());
		}
		return list;
	}
}
