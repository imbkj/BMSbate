package dal.Batchprocessing;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.zkoss.zul.ListModelList;

import Conn.dbconn;
import Model.CoPolicyNoticeModel;
import Model.EmHouseChangeModel;
import Model.EmHouseUpdateModel;
import Model.EmHouseUploadModel;
import Util.UserInfo;

public class EmHouse_BathSelectDal {

	// 根据gid获取员工的基本信息
	public EmHouseChangeModel getEmbaInfo(EmHouseChangeModel m, Integer type) {
		dbconn db = new dbconn();
		Integer k = 0;
		try {
			ResultSet rs = db.GRS("exec [EmBase_getcompanyid_p_cyj]"
					+ m.getEmhc_cpp() + "," + m.getGid() + ",'"
					+ m.getEmhc_idcard() + "','" + m.getEmhc_name() + "',"
					+ type + "," + m.getOwnmonth());
			while (rs.next()) {
				m.setCid(rs.getInt("cid"));
				m.setCoba_company(rs.getString("coba_company"));
				m.setEmhc_companyid(rs.getString("cohf_houseid"));
				m.setEmhc_single(rs.getInt("single"));
				m.setEmhu_id(rs.getInt("emhu_id"));
				
				//m.setCohf_cpp(rs.getDouble("cohf_cpp"));
				//m.setEmhc_hradix(rs.getDouble("radix"));
				
				String errorMsg = rs.getString("errormsg");
				if (errorMsg != null && !errorMsg.equals("")) {
					m.setErrorMsg(rs.getString("errormsg"));
				}
				k = 1;
			}
		} catch (Exception e) {

		} finally {
			if (k <= 0) {
				m.setErrorMsg("没有找到该员工信息");
			}
		}
		return m;
	}

	public List<EmHouseUpdateModel> getupdateList(Integer gid, Double cpp) {
		List<EmHouseUpdateModel> list = new ListModelList<>();
		dbconn db = new dbconn();
		String sql = "select emhu_id,a.ownmonth,a.gid,a.cid,emhu_companyid,emhu_company,coba_shortname,emhu_name,emhu_idcard,"
				+ "emhu_idcard2,emhu_hj,emhu_computerid,emhu_houseid,emhu_mobile,"
				+ "emhu_title,emhu_wifename,emhu_wifeidcard,emhu_degree,"
				+ "emhu_radix,emhu_cpp,emhu_single,coba_client,coco_cpp,cohf_houseid"
				+ " from EmHouseUpdate a"
				+ " inner join cobase b on a.cid=b.cid"
				+ " inner join coglist c on a.gid=c.gid and (cgli_stopdate is null or (cgli_startdate<=a.ownmonth and cgli_stopdate>=a.ownmonth))"
				+ " inner join CoOfferList d on c.cgli_coli_id=d.coli_id and coli_name='住房公积金服务'"
				+ " inner join CoOffer e on d.coli_coof_id=e.coof_id"
				+ " inner join CoCompact f on e.coof_coco_id=f.coco_id"
				+ " inner join login h on b.coba_Client=h.log_name and log_inure=1"
				+ " inner join CoHousingFund g on a.emhu_companyid=g.cohf_houseid and ((cohf_single=1 and a.cid=g.cid) or cohf_single=0)"
				+ " where a.emhu_ifstop=0 and a.gid=?";

		try {
			list = db.find(sql, EmHouseUpdateModel.class, null, gid);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return list;
	}

	public List<EmHouseUploadModel> getEmHouseUploadList(String str) {
		List<EmHouseUploadModel> list = new ArrayList<EmHouseUploadModel>();
		dbconn db = new dbconn();
		String sql = "select coba_client,coba_company,a.* from EmHouseUpload a "
				+ " left join cobase b on a.cid=b.cid where hsup_addname='"
				+ UserInfo.getUsername() + "' " + str;
		System.out.print(sql);
		try {
			list = db.find(sql, EmHouseUploadModel.class,
					dbconn.parseSmap(EmHouseUploadModel.class));
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}

	// 获取最后一个批量号
	public String getLastBatchNumber(String type) {
		String batchNumber = "";
		dbconn db = new dbconn();
		String sql = "select top 1 * from EmHouseUpload  where hsup_addname='"
				+ UserInfo.getUsername() + "' and hsup_change='" + type
				+ "' order by hsup_id desc";
		try {
			ResultSet rs = db.GRS(sql);
			while (rs.next()) {
				batchNumber = rs.getString("hsup_batchnumber");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return batchNumber;
	}
}
