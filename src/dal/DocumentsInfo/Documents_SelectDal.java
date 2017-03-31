package dal.DocumentsInfo;

import java.sql.ResultSet;
import java.util.ArrayList;

import Conn.dbconn;
import Model.DocumentsHandoverLogModel;
import Model.DocumentsInfoModel;
import Model.DocumentsSubmitInfoModel;

public class Documents_SelectDal {
	private static dbconn conn = new dbconn();

	// 获取新增页面显示材料数据集
	private ResultSet getAddPageDoc(String str) throws Exception {
		ResultSet rs = null;
		StringBuilder sql = new StringBuilder();
		sql.append("SELECT * ");
		sql.append(" FROM DocumentsInfo_Dire_PubZul_V where dire_state=1 ");
		sql.append(str);
		sql.append(" order by doin_content");
		rs = conn.GRS(sql.toString());
		return rs;
	}

	// 检查是否已经存在上传文件
	private ResultSet getPubPicCheck(String dtype,String cgid, String doin_id)
			throws Exception {
		ResultSet rs = null;
		StringBuilder sql = new StringBuilder();
		sql.append("SELECT state=1 ");
		sql.append(" FROM PubPic where pupi_state=1");
		if(dtype=="g"){
			sql.append(" AND gid=" + cgid);
		}
		else if(dtype=="c"){
			sql.append(" AND (gid IS NULL OR gid='') AND cid=" + cgid);
		}
		sql.append(" AND pupi_doin_id=" + doin_id);
		sql.append(" GROUP BY pupi_doin_id");
		rs = conn.GRS(sql.toString());
		return rs;
	}

	// 获取修改页面显示材料数据集
	private ResultSet getUpdatePageDoc(String str) throws Exception {
		ResultSet rs = null;
		StringBuilder sql = new StringBuilder();
		sql.append("SELECT * ");
		sql.append(" FROM DocumentsSubmitInfo_Dire_DocInfo_V where dsin_state>0 ");
		sql.append(str);
		sql.append(" order by doin_content");
		rs = conn.GRS(sql.toString());
		return rs;
	}

	// 获取材料交接记录数据集
	private ResultSet getDocLog(String str) throws Exception {
		ResultSet rs = null;
		StringBuilder sql = new StringBuilder();
		sql.append("SELECT * ");
		sql.append(" FROM DocumentsHandoverLog where 1=1 ");
		sql.append(str);
		sql.append(" order by dhlo_addtime");
		rs = conn.GRS(sql.toString());
		return rs;
	}

	// 获取新增页面显示材料数据插入model
	public ArrayList<DocumentsInfoModel> getAddPageDocList(String dtype,String cgid,
			String str,String puzu_id) {
		ArrayList<DocumentsInfoModel> docList = new ArrayList<DocumentsInfoModel>();
		try {
			ResultSet rs = getAddPageDoc(str);
			ResultSet chkrs;
			String pupi_state;
			while (rs.next()) {

				// 调用getPubPicCheck()方法，检查是否已经存在上传文件
				chkrs = getPubPicCheck(dtype,cgid,
						String.valueOf(rs.getInt("doin_id")));
				if (chkrs.next()) {
					pupi_state = "1";
				} else {
					pupi_state = "0";
				}

				// 向Model插入数据
				docList.add(new DocumentsInfoModel(rs.getInt("doin_id"), rs
						.getString("doin_type"), rs.getString("doin_class"), rs
						.getString("doin_content"), rs.getString("doin_state"),
						rs.getString("doin_remark"), rs
								.getString("doin_addname"), rs
								.getString("doin_addtime"), rs
								.getString("dire_id"), rs
								.getString("dire_doin_id"), puzu_id, rs
								.getString("dire_ifhaveto"), rs
								.getString("dire_state"), rs
								.getString("dire_addname"), rs
								.getString("dire_addtime"), puzu_id, rs
								.getString("puzu_page"), rs
								.getString("puzu_pspell"), rs
								.getString("puzu_pclass"), rs
								.getString("dire_ifcheck"), pupi_state));
			}
		} catch (Exception e) {
			System.out.println(e.toString());
		}
		return docList;
	}

	// 获取修改页面显示材料数据插入model
	public ArrayList<DocumentsSubmitInfoModel> getUpdatePageDocList(String dtype,String cgid,
			String str) {
		ArrayList<DocumentsSubmitInfoModel> docList = new ArrayList<DocumentsSubmitInfoModel>();
		try {
			ResultSet rs = getUpdatePageDoc(str);
			ResultSet chkrs;
			String pupi_state;
			while (rs.next()) {

				// 调用getPubPicCheck()方法，检查是否已经存在上传文件
				chkrs = getPubPicCheck(dtype,cgid,
						String.valueOf(rs.getInt("dire_doin_id")));
				if (chkrs.next()) {
					pupi_state = "1";
				} else {
					pupi_state = "0";
				}

				// 向Model插入数据
				docList.add(new DocumentsSubmitInfoModel(rs.getInt("dsin_id"),
						rs.getString("dsin_dire_id"), rs.getString("dsin_tid"),
						rs.getString("dsin_ifsubmit"), rs
								.getString("dsin_count"), rs
								.getString("dsin_state"), rs
								.getString("dsin_addname"), rs
								.getString("dsin_addtime"), rs
								.getString("dire_id"), rs
								.getString("dire_doin_id"), rs
								.getString("dire_puzu_id"), rs
								.getString("dire_ifhaveto"), rs
								.getString("dire_ifcheck"), rs
								.getString("dire_state"), rs
								.getString("doin_id"), rs
								.getString("doin_type"), rs
								.getString("doin_class"), rs
								.getString("doin_content"), rs
								.getString("doin_state"), rs
								.getString("doin_remark"), rs
								.getString("dire_addname"), rs
								.getString("dire_addtime"), rs
								.getString("doin_addname"), rs
								.getString("doin_addtime"), pupi_state,rs.getString("dsin_out_count")));

			}
		} catch (Exception e) {
			System.out.println(e.toString());
		}
		return docList;
	}

	// 获取材料交接记录 插入model
	public ArrayList<DocumentsHandoverLogModel> getDocLogList(String str) {
		ArrayList<DocumentsHandoverLogModel> logList = new ArrayList<DocumentsHandoverLogModel>();

		try {

			ResultSet rs = getDocLog(str);
			while (rs.next()) {
				// 向Model插入数据
				logList.add(new DocumentsHandoverLogModel(rs.getInt("dhlo_id"),
						rs.getString("dhlo_dsin_id"), rs
								.getString("dhlo_content"), rs
								.getString("dhlo_addname"), rs
								.getString("dhlo_addtime")));
			}
		} catch (Exception e) {
			System.out.println(e.toString());
		}
		return logList;
	}
}
