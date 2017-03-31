package dal.Embase;

import java.sql.CallableStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import sun.security.util.Length;

import Conn.dbconn;
import Model.EmPicModel;

public class EmPic_CheckDal {
	private static dbconn conn = new dbconn();

	// 显示图片信息
	public List<EmPicModel> getpiclist(String gid) throws SQLException {
		dbconn db = new dbconn();
		ResultSet rs = null;
		String ur_con = ""; 
		List<EmPicModel> list = new ArrayList<EmPicModel>();
		String sqlstr = "select empi_filename,empi_addtime,empi_class,len(isnull(empi_idcard,'')) empi_fidcard,empi_idcard from empic where gid="
				+ gid;
		try {
			rs = db.GRS(sqlstr);
			while (rs.next()) {
				EmPicModel model = new EmPicModel();
				model.setEmpic_name(rs.getString("empi_filename"));
				model.setEmpic_time(rs.getString("empi_addtime"));
				model.setEmpic_type(rs.getString("empi_class"));
				if (rs.getString("empi_fidcard").equals("0")) {
					ur_con = "empic/";
				} else {
					ur_con = "netpic/Photos/"
							+ rs.getString("empi_idcard") + "/";
				}

				model.setEmpic_url(ur_con + rs.getString("empi_filename"));
				list.add(model);
			}
		} catch (Exception e) {
			System.out.print(e.toString());
		} finally {
			db.Close();
		}
		return list;
	}

	// 显示公司图片信息
	public List<EmPicModel> getcopiclist(String cid) throws SQLException {
		dbconn db = new dbconn();
		ResultSet rs = null;
		String ur_con = "";
		List<EmPicModel> list = new ArrayList<EmPicModel>();
		String sqlstr = "select copi_filename,copi_addtime,copi_class from copic where cid="
				+ cid;
		try {
			rs = db.GRS(sqlstr);
			while (rs.next()) {
				EmPicModel model = new EmPicModel();
				model.setEmpic_name(rs.getString("copi_filename"));
				model.setEmpic_time(rs.getString("copi_addtime"));
				model.setEmpic_type(rs.getString("copi_class"));
				ur_con = "copic/";
				model.setEmpic_url(ur_con + rs.getString("copi_filename"));
				list.add(model);
			}
		} catch (Exception e) {
			System.out.print(e.toString());
		} finally {
			db.Close();
		}
		return list;
	}

	// 显示图片类型
	public List<EmPicModel> getempicclasslist(String cl) throws SQLException {
		dbconn db = new dbconn();
		ResultSet rs = null;
		List<EmPicModel> list = new ArrayList<EmPicModel>();
		String sqlstr = "select * from PubPicClass where ppcl_type='" + cl
				+ "'";
		try {
			rs = db.GRS(sqlstr);
			while (rs.next()) {
				EmPicModel model = new EmPicModel();
				model.setEmpic_name(rs.getString("ppcl_name"));
				list.add(model);
			}
		} catch (Exception e) {
			System.out.print(e.toString());
		} finally {
			db.Close();
		}
		return list;
	}

	// 显示图片类型
	public List<EmPicModel> getcopicclasslist(String cl) throws SQLException {
		dbconn db = new dbconn();
		ResultSet rs = null;
		List<EmPicModel> list = new ArrayList<EmPicModel>();
		String sqlstr = "select * from PubPicClass where ppcl_type='" + cl
				+ "'";
		try {
			rs = db.GRS(sqlstr);
			while (rs.next()) {
				EmPicModel model = new EmPicModel();
				model.setEmpic_name(rs.getString("ppcl_name"));
				list.add(model);
			}
		} catch (Exception e) {
			System.out.print(e.toString());
		} finally {
			db.Close();
		}
		return list;
	}

	// 图片上传
	public int empic_add(String gid, String pic_class, String url,
			String addname) {
		try {
			CallableStatement c = conn.getcall("EmPicUp_P_zzq(?,?,?,?,?)");
			c.setString(1, gid);
			c.setString(2, pic_class);
			c.setString(3, url);
			c.setString(4, addname);
			c.registerOutParameter(5, java.sql.Types.INTEGER);
			c.execute();
			return c.getInt(5);
		} catch (SQLException e) {
			return 1;
		}
	}

	// 图片上传
	public int copic_add(String cid, String pic_class, String url,
			String addname) {
		try {
			CallableStatement c = conn.getcall("CoPicUp_P_zzq(?,?,?,?,?)");
			c.setString(1, cid);
			c.setString(2, pic_class);
			c.setString(3, url);
			c.setString(4, addname);
			c.registerOutParameter(5, java.sql.Types.INTEGER);
			c.execute();
			return c.getInt(5);
		} catch (SQLException e) {
			return 1;
		}
	}
}
