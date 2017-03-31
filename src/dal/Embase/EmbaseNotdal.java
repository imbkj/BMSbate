package dal.Embase;

import Model.EmbaseModel;
import Model.EmbaseNotInModel;
import java.util.ArrayList;
import java.util.List;

import Conn.dbconn;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.zkoss.zul.ListModelList;

public class EmbaseNotdal {
	private static dbconn conn = new dbconn();

	// 根据查询语句获取员工列表数据集
	private ResultSet getembasenotin(String str) throws Exception {
		ResultSet rs = null;
		StringBuilder sql = new StringBuilder();
		sql.append("SELECT top 50* from  View_EmbaseNotin where 1=1 and emba_state>0 ");
 
		sql.append(str);
		sql.append(" order by emba_id desc,emba_state asc");
		// System.out.println(sql);
		rs = conn.GRS(sql.toString());
		return rs;
	}

	// 根据查询语句获取员工列表数据集
	public List<EmbaseNotInModel> getembanotinList(String str) {
		List<EmbaseNotInModel> embanotinList = new ArrayList<EmbaseNotInModel>();
		try {
			ResultSet rs = getembasenotin(str);
			while (rs.next()) {
				embanotinList.add(new EmbaseNotInModel(rs.getInt("emba_id"), rs
						.getInt("gid"), rs.getInt("cid"), rs
						.getString("emba_name"), rs
						.getString("emba_englishname"), rs
						.getString("emba_spell"), rs.getString("emba_sex"), rs
						.getString("emba_idcard"), rs
						.getString("emba_idcardclass"), rs
						.getString("emba_mobile"), rs.getInt("emba_state"), rs
						.getString("emba_remark"), rs.getString("emba_wt"), rs
						.getString("coba_name"), rs.getString("coba_client"),
						rs.getString("emba_form"),rs.getString("emba_state"),rs.getInt("emba_type"))
				
						);
			}

		} catch (Exception e) {
			System.out.println(e.toString());
		}
		return embanotinList;
	}
	
	// 根据ID获取员工信息
		public List<EmbaseNotInModel> getembanotinListall(Integer id)  {
			List<EmbaseNotInModel> list = new ListModelList<EmbaseNotInModel>();
			dbconn db = new dbconn();
			String sql = "select a.*,coba_company,coba_shortname,coba_client  " +
					"from embasenotin a inner join cobase b on a.cid=b.cid where emba_id=?";

			try {
				list = db.find(sql, EmbaseNotInModel.class,
						dbconn.parseSmap(EmbaseNotInModel.class), id);
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return list;
		}


	public String[] getembanotinList() {

		StringBuilder spstr = new StringBuilder();

		try {

			ResultSet rs = conn
					.GRS("SELECT  * from  View_EmbaseNotin where 1=1");
			while (rs.next()) {

				spstr.append(rs.getString("emba_spell") + "|"
						+ rs.getString("emba_name") + "|"
						+ rs.getString("coba_name"));
				spstr.append(",");
			}

		} catch (Exception e) {
			System.out.println(e.toString());
		}
		// System.out.println(spstr);
		// System.out.println(spstr.toString().split(",")[9].toString());
		return spstr.toString().split(",");

	}

	// 根据ID获取员工信息
	public List<EmbaseNotInModel> getEmBaseNotInById(Integer id)  {
		List<EmbaseNotInModel> list = new ListModelList<EmbaseNotInModel>();
		dbconn db = new dbconn();
		String sql = "select a.cid,a.gid,coba_company,coba_shortname,coba_client,emba_name,emba_idcard,emba_mobile,emba_wifename,emba_wifeidcard,emba_excompany,emba_excompanyid " +
				"from embasenotin a inner join cobase b on a.cid=b.cid where emba_id=?";

		try {
			list = db.find(sql, EmbaseNotInModel.class,
					dbconn.parseSmap(EmbaseNotInModel.class), id);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return list;
	}

}
