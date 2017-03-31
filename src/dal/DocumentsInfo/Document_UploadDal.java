package dal.DocumentsInfo;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import Model.DocumentsInfoModel;
import Model.DocumentsInfoPubPicModel;
import Conn.dbconn;

public class Document_UploadDal {
	private static dbconn conn = new dbconn();

	// 根据类型获取文件类型数据集
	private ResultSet getDocClassByTypeRs(int type) throws Exception {
		String sql = "select doin_id,doin_class,doin_content from DocumentsInfo where doin_type=? and doin_state=1";
		PreparedStatement psmt = conn.getpre(sql);
		psmt.setInt(1, type);
		ResultSet rs = psmt.executeQuery();
		return rs;
	}

	// 根据cid获取公司上传文件
	private ResultSet getPicByCidRs(int cid) throws Exception {
		String sql = "select pupi_id,pupi_url,pupi_addname,pupi_addtime,pupi_size,doin_class,doin_content from PubPic pp left join DocumentsInfo di on pp.pupi_doin_id=di.doin_id where pp.pupi_state=1 and cid=? and (gid=0 or gid is null) order by pupi_addtime desc";
		PreparedStatement psmt = conn.getpre(sql);
		psmt.setInt(1, cid);
		ResultSet rs = psmt.executeQuery();
		return rs;
	}

	// 根据gid获取公司上传文件
	private ResultSet getPicByGidRs(int gid) throws Exception {
		String sql = "select pupi_id,pupi_url,pupi_addname,pupi_addtime,pupi_size,doin_class,doin_content from PubPic pp left join DocumentsInfo di on pp.pupi_doin_id=di.doin_id where pp.pupi_state=1 and gid=? order by pupi_addtime desc";
		PreparedStatement psmt = conn.getpre(sql);
		psmt.setInt(1, gid);
		ResultSet rs = psmt.executeQuery();
		return rs;
	}

	// 根据ID获取PubPic表url信息
	private ResultSet getPicUrlRs(int id) throws Exception {
		String sql = "select pupi_url from PubPic where pupi_id=?";
		PreparedStatement psmt = conn.getpre(sql);
		psmt.setInt(1, id);
		ResultSet rs = psmt.executeQuery();
		return rs;
	}

	// 根据类型获取文件类型
	public ArrayList<DocumentsInfoModel> getDocClassByType(int type)
			throws Exception {
		ArrayList<DocumentsInfoModel> list = new ArrayList<DocumentsInfoModel>();
		ResultSet rs = getDocClassByTypeRs(type);
		try {
			while (rs.next()) {
				DocumentsInfoModel m = new DocumentsInfoModel();
				m.setDoin_id(rs.getInt("doin_id"));
				m.setDoin_class(rs.getString("doin_class"));
				m.setDoin_content(rs.getString("doin_content"));
				list.add(m);
			}
		} catch (Exception e) {

		}
		return list;
	}

	// 根据gid或cid获取文件信息
	public ArrayList<DocumentsInfoPubPicModel> getPicById(int id, int type)
			throws Exception {
		ArrayList<DocumentsInfoPubPicModel> list = new ArrayList<DocumentsInfoPubPicModel>();
		ResultSet rs = null;
		if (type == 1) {
			rs = getPicByGidRs(id);
		} else if (type == 2) {
			rs = getPicByCidRs(id);
		}
		try {
			while (rs.next()) {
				DocumentsInfoPubPicModel m = new DocumentsInfoPubPicModel();
				m.setPupi_id(rs.getInt("pupi_id"));
				m.setPupi_url(rs.getString("pupi_url"));
				m.setPupi_addname(rs.getString("pupi_addname"));
				m.setPupi_addtime(rs.getString("pupi_addtime"));
				m.setPupi_size(rs.getString("pupi_size"));
				m.setDoin_class(rs.getString("doin_class"));
				m.setDoin_content(rs.getString("doin_content"));
				list.add(m);
			}
		} catch (Exception e) {

		}
		return list;
	}

	// 根据ID获取PubPic表url信息
	public String getPicUrl(int id) throws Exception {
		ResultSet rs = getPicUrlRs(id);
		String url = "";
		try {
			while (rs.next()) {
				url = rs.getString("pupi_url");
			}
		} catch (Exception e) {

		}
		return url;
	}

	// 文件上传
	public boolean DocFileUpload(int cid, int gid, int doin_id, String url,
			String size, String addname) {
		try {
			CallableStatement c = conn
					.getcall("DocFileUpload_P_lwj(?,?,?,?,?,?,?)");
			c.setInt(1, cid);
			c.setInt(2, gid);
			c.setInt(3, doin_id);
			c.setString(4, url);
			c.setString(5, size);
			c.setString(6, addname);
			c.registerOutParameter(7, java.sql.Types.INTEGER);
			c.execute();
			if (c.getInt(7) == 1) {
				return true;
			}
		} catch (SQLException e) {

		}
		return false;
	}
}
