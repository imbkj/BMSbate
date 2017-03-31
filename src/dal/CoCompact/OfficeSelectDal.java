package dal.CoCompact;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

import Conn.dbconn;
import Model.DocumentsInfoPubPicModel;
import Model.OfficeModel;

public class OfficeSelectDal {
	private static dbconn conn = new dbconn();

	// 获取所有合同数据
	private ResultSet getofficeFile(int puof_tid, int sc,int puf_id) throws Exception {
		ResultSet rs = null;
		String sql;
		sql = "";
		if (sc == 1) {
			sql = "select * from puboffice a left join CoCompact b on b.coco_id=a.puof_tid where puof_pute_id=2 and puof_tid=" + puf_id;
		}
		if (sc == 2) {
			sql = "select * from puboffice a left join CoCompact b on b.coco_id=a.puof_tid where puof_pute_id=2 and puof_tid=" + puof_tid;
		}
		System.out.println(sql);
		rs = conn.GRS(sql);
		return rs;
	}
	
	// 根据coco_id获取公司退回信息
			private ResultSet getOutContRs(String coco_id) throws Exception {
				String sql = "select * from cocompact where coco_id=?";
				PreparedStatement psmt = conn.getpre(sql);
				psmt.setString(1, coco_id);
				ResultSet rs = psmt.executeQuery();
				return rs;
			}

	// 获取所有公司合同(CoCompact_CoLa_CoBase_V)
	public ArrayList<OfficeModel> getofficeList(int puof_tid, int sc,int puf_id) {
		ArrayList<OfficeModel> officeList = new ArrayList<OfficeModel>();
		try {
			ResultSet rs = getofficeFile(puof_tid, sc,puf_id);
			while (rs.next()) {
				officeList.add(new OfficeModel(rs.getInt("puof_pute_id"), rs
						.getInt("puof_type"), rs.getInt("puof_tid"), rs
						.getString("puof_url"), rs.getString("puof_addname"),
						rs.getString("puof_addtime"), rs
								.getString("coco_compacttype"), rs
								.getString("coco_compactid")));
			}
		} catch (Exception e) {
			System.out.println(e.toString());
		}
		return officeList;
	}
	
	// 根据coco_id获取退回信息
			public ArrayList<DocumentsInfoPubPicModel> getoutcont(String coco_id)
					throws Exception {
				ArrayList<DocumentsInfoPubPicModel> list = new ArrayList<DocumentsInfoPubPicModel>();
				ResultSet rs = null;

				rs=getOutContRs(coco_id);
				try {
					while (rs.next()) {
						DocumentsInfoPubPicModel m = new DocumentsInfoPubPicModel();
						m.setDoin_content(rs.getString("coco_outcontent"));
						m.setPupi_ip(rs.getString("coco_save"));
						list.add(m);
					}
				} catch (Exception e) {

				}
				return list;
			}
}
