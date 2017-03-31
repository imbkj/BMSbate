package dal.SystemControl;

import java.sql.CallableStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.zkoss.zul.ListModelList;

import Conn.dbconn;
import Model.SystLogModel;
import Util.UserInfo;

public class SystLogDal {
	private dbconn conn = new dbconn();

	public int addSystLogInfo(SystLogModel m) throws SQLException {
		CallableStatement c = conn
				.getcall("SystLog_Add_shy(?,?,?,?,?,?,?,?,?,?,?)");
		c.setString(1, m.getTid());
		c.setString(2, m.getCid());
		c.setString(3, m.getGID());
		c.setString(4, m.getOwnmonth());
		c.setString(5, m.getOpsql());
		c.setString(6, m.getClass1());
		c.setString(7, m.getAddname());
		c.setString(8, m.getContent());
		c.setString(9, m.getIP());
		c.setString(10, m.getAddtime());
		c.registerOutParameter(11, java.sql.Types.INTEGER);
		c.execute();
		return c.getInt(11);
	}

	public int addLog(String IP, Integer cid, Integer gid, String type,
			String content, String addname) {
		dbconn db = new dbconn();
		String sql = "insert into systLog(cid,gid,class,addname,content,IP)values(?,?,?,?,?,?)";
		Integer i = 0;
		try {
			i = db.insertAndReturnKey(sql, cid, gid, type, addname, content, IP);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return i;
	}

	public int addLog(String IP, Integer tid, Integer cid, Integer gid,
			String type, String content, String addname) {
		dbconn db = new dbconn();
		String sql = "insert into systLog(tid,cid,gid,class,addname,content,IP)values(?,?,?,?,?,?,?)";
		Integer i = 0;
		try {
			i = db.insertAndReturnKey(sql, tid, cid, gid, type, addname,
					content, IP);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return i;
	}

	public List<SystLogModel> getSystLogInfo(String str) {
		List<SystLogModel> list = new ArrayList<SystLogModel>();
		String sql = "SELECT ID,tid,cid,GID,ownmonth,opsql,Class,addname,content,IP,addtime FROM systLog where 1=1";
		if (str != null && !str.equals(" ")) {
			sql = sql + str;
		}
		System.out.println(sql);
		ResultSet rs;
		try {
			rs = conn.GRS(sql);
			while (rs.next()) {
				SystLogModel sl = new SystLogModel();
				sl.setId(rs.getString("ID"));
				sl.setTid(rs.getString("tid"));
				sl.setCid(rs.getString("cid"));
				sl.setGID(rs.getString("GID"));
				sl.setOwnmonth(rs.getString("ownmonth"));
				sl.setOpsql(rs.getString("opsql"));
				sl.setClass1(rs.getString("Class"));
				sl.setAddname(rs.getString("addname"));
				sl.setContent(rs.getString("content"));
				sl.setIP(rs.getString("IP"));
				sl.setAddtime(rs.getString("addtime"));
				list.add(sl);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				conn.Close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return list;
	}

	// 查询发票修改列表
	public List<SystLogModel> getLogList(String company, String client,
			String developer, String date1, String date2) {
		List<SystLogModel> list = new ListModelList<>();
		dbconn db = new dbconn();
		String sql = "select convert(varchar(10),a.cid)cid,coba_company,coba_client,coba_developer,coba_addtime,addtime"
				+ " from (select cid,Class,convert(varchar(10),addtime,120)addtime,addname"
				+ " from systLog where Class='发票信息'"
				+ " group by cid,Class,convert(varchar(10),addtime,120),addname"
				+ ")a inner join cobase b on a.cid=b.CID" + " where 1=1";

		if (company != null && !company.equals("")) {
			sql += " and (coba_company like '%" + company + "%' or a.cid ='"
					+ company + "' or coba_shortname like '%" + company + "%')";
		}
		if (client != null && !client.equals("")) {
			sql += " and coba_client = '" + client + "'";
		}
		if (developer != null && !developer.equals("")) {
			sql += " and coba_developer = '" + developer + "'";
		}
		if (date1 != null && !date1.equals("")) {
			sql += " and DATEDIFF(D,addtime,'" + date1 + "')<=0";
		}
		if (date2 != null && !date2.equals("")) {
			sql += " and DATEDIFF(D,addtime,'" + date1 + "')>=0";
		}
		sql += " order by addtime desc,coba_client,coba_company";
		System.out.println(sql);
		try {
			list = db.find(sql, SystLogModel.class, null);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return list;
	}

	// 读取发票修改日志
	public List<SystLogModel> getlog(Integer cid, String addtime) {
		dbconn db = new dbconn();
		String sql = "select convert(varchar(10),cid)cid,content,addname,addtime from systLog"
				+ " where Class='发票信息' and cid=?"
				+ " and datediff(D,addtime,?)=0 order by addtime desc";
		List<SystLogModel> list = new ListModelList<>();
		try {
			list = db.find(sql, SystLogModel.class, null, cid, addtime);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return list;
	}

	// 读取发票修改日志
	public List<SystLogModel> getlogById(Integer id) {
		dbconn db = new dbconn();
		String sql = "select cid,content,addname,addtime from systLog"
				+ " where Class='发票信息' and tid=?";
		List<SystLogModel> list = new ListModelList<>();
		try {
			list = db.find(sql, SystLogModel.class, null, id);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return list;
	}
}
