package dal.CoPolicyNotice;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import Conn.dbconn;
import Model.CoPolicyNoticeFileModel;
import Model.CoPolicyNoticeModel;

public class PoNo_SelectDal {
	// 查询政策通知基本信息
	public List<CoPolicyNoticeModel> getCoPolicyNoticeList(String str) {
		List<CoPolicyNoticeModel> list = new ArrayList<CoPolicyNoticeModel>();
		dbconn db = new dbconn();
		String sql = "select convert(varchar(10),pono_uploadtime,120) as pono_uploadtime,"
				+ "convert(varchar(10),pono_addtime,120) as pono_addtime,"
				+ "convert(varchar(10),pono_modtime,120) as pono_modtime,"
				+ "* from CoPolicyNotice where pono_state=1 " + str+" order by pono_id desc";
		try {
			list = db.find(sql, CoPolicyNoticeModel.class,
					dbconn.parseSmap(CoPolicyNoticeModel.class));
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}

	// 查询政策通知文件信息
	public List<CoPolicyNoticeFileModel> getCoPolicyNoticeFileList(String str) {
		List<CoPolicyNoticeFileModel> list = new ArrayList<CoPolicyNoticeFileModel>();
		dbconn db = new dbconn();
		String sql = "select convert(varchar(16),file_addtime,120) as file_addtime,"
				+ "convert(varchar(16),file_modtime,120) as file_modtime,"
				+ "* from CoPolicyNoticeFile where file_state=1" + str;
		try {
			list = db.find(sql, CoPolicyNoticeFileModel.class,
					dbconn.parseSmap(CoPolicyNoticeFileModel.class));
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}

	// 根据文件名查询是否已有该文件
	public boolean isExistFile(String filename) {
		boolean flag = false;
		dbconn db = new dbconn();
		String sql = "select * from  CoServicePolicyFile where file_title='"
				+ filename + "'";
		try {
			ResultSet rs = db.GRS(sql);
			while (rs.next()) {
				flag = true;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return flag;
	}

	// 查询政策通知与业务关联信息
	public List<CoPolicyNoticeModel> getNoticeRelationList(String str) {
		List<CoPolicyNoticeModel> list = new ArrayList<CoPolicyNoticeModel>();
		dbconn db = new dbconn();
		String sql = "select convert(varchar(10),pono_uploadtime,120) as pono_uploadtime,"
				+ "convert(varchar(16),pono_addtime,120) as pono_addtime,"
				+ "convert(varchar(16),pono_modtime,120) as pono_modtime,"
				+ "convert(varchar(16),Cpnr_addtime,120) as Cpnr_addtime,"
				+ "* from CoPolicyNotice a,CoPolicynoticeRelation b where a.pono_id=b.Cpnr_pono_id "
				+ " and pono_state=1 " + str;
		try {
			list = db.find(sql, CoPolicyNoticeModel.class,
					dbconn.parseSmap(CoPolicyNoticeModel.class));
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}

	public String getEmail(Integer log_id, String log_name) {
		String sql = "select * from login where log_id=" + log_id
				+ " or log_name='" + log_name + "'";
		dbconn db = new dbconn();
		String email="";
		try {
			ResultSet rs=db.GRS(sql);
			while (rs.next()) {
				email=rs.getString("log_email");
			}
		} catch (Exception e) {
		}
		return email;
	}
}
