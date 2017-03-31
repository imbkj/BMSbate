package dal.ClientRelations.VisitInfo;

import java.sql.CallableStatement;
import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.zkoss.zul.ListModelList;

import Conn.dbconn;
import Model.CoProductModel;
import Model.VisitFollowModel;
import Model.VisitInfoModel;

public class vit_backDal {

	public static List<VisitFollowModel> getDept() throws SQLException {
		dbconn db = new dbconn();
		ResultSet rs = null;
		List<VisitFollowModel> list = new ArrayList<VisitFollowModel>();
		String sqlstr = "select 0 as dep_id,'' as dep_name union select dep_id,dep_name from department";

		try {
			rs = db.GRS(sqlstr);
			while (rs.next()) {
				VisitFollowModel vfm = new VisitFollowModel();
				vfm.setVifo_dept_id(rs.getInt("dep_id"));
				vfm.setDeptname(rs.getString("dep_name"));
				list.add(vfm);
			}
		} catch (Exception e) {
			System.out.print(e.toString());
		} finally {
			db.Close();
		}
		return list;
	}

	public static ListModelList<String> getLinkmanList(int viin_id)
			throws SQLException {
		dbconn db = new dbconn();
		ResultSet rs = null;
		ListModelList<String> list = new ListModelList<String>();
		String sqlstr = "select cali_name from VisitInfo a,CoviRelation b,CoLatencyClient c,"
				+ "colaColiLinkRel d,CoAgencyLinkman e where a.viin_id=b.covi_viin_id "
				+ "and b.covi_cola_id=c.cola_id and c.cola_id=d.coca_colaid and e.cali_id=d.coca_caliid "
				+ "and viin_id=" + viin_id;

		try {
			rs = db.GRS(sqlstr);
			while (rs.next()) {
				list.add(rs.getString("cali_name"));
			}
		} catch (Exception e) {
			System.out.print(e.toString());
		} finally {
			db.Close();
		}
		return list;
	}
	
	// 任务单编号更新
	public static int updatetaskid(int viin_id,int tapr_id) throws Exception {
		int row = 0;
		String sqlstr = "update VisitInfo  set viin_tapr_id="+tapr_id+" where viin_id="
				+viin_id+ "";
		dbconn update = new dbconn();
		try {
			
			row = update.execQuery(sqlstr);

		} catch (Exception e) {
			System.out.println(e.toString());
		} finally {
			update.Close();
		}
		return row;
	}


	public static int vitbackMod(VisitInfoModel vim) throws SQLException {
		CallableStatement csmt = null;
		dbconn db = new dbconn();
		int row = 0;

		// 创建存储过程的对象
		csmt = db.getcall("VisitBack_P_ply(?,?,?,?,?,?,?,?,?,?,?,?)");

		try {
			// 给存储过程的参数设置值
			Date truetime = vim.getViin_truetime() == null ? null : new Date(
					vim.getViin_truetime().getTime());
			csmt.setInt(1, vim.getViin_id());
			csmt.setString(2, vim.getViin_cost());
			csmt.setString(3, vim.getViin_costremark());
			csmt.setString(4, vim.getViin_personed());
			csmt.setString(5, vim.getViin_job());
			csmt.setString(6, vim.getViin_aim());
			csmt.setDate(7, truetime);
			csmt.setString(8, vim.getViin_summary());
			csmt.setString(9, vim.getViin_feedback());
			csmt.setBoolean(10, vim.isViin_iffolow());
			csmt.setInt(11, vim.getViin_state());

			// 注册存储过程的返回值
			csmt.registerOutParameter(12, java.sql.Types.INTEGER);

			// 执行存储过程
			csmt.execute();

			// 获取返回值
			row = csmt.getInt(12);

		} catch (Exception e) {
			System.out.print(e.toString());
		} finally {
			db.Close();
		}

		return row;
	}

	public static int vitfollowAdd(VisitFollowModel vfm) throws SQLException {
		CallableStatement csmt = null;
		dbconn db = new dbconn();
		int row = 0;

		// 创建存储过程的对象
		csmt = db.getcall("Visitfollow_Add_P_ply(?,?,?,?,?,?,?,?,?)");

		try {
			// 给存储过程的参数设置值
			Date disposetime = vfm.getVifo_disposetime() == null ? null
					: new Date(vfm.getVifo_disposetime().getTime());
			csmt.setString(1, vfm.getVifo_content());
			csmt.setString(2, vfm.getVifo_name());
			csmt.setInt(3, vfm.getVifo_dept_id());
			csmt.setDate(4, disposetime);
			csmt.setString(5, vfm.getVifo_disposecontent());
			csmt.setInt(6, vfm.getVire_viin_id());
			csmt.setInt(7, vfm.getVifo_order());
			csmt.setString(8, vfm.getVifo_addname());

			// 注册存储过程的返回值
			csmt.registerOutParameter(9, java.sql.Types.INTEGER);

			// 执行存储过程
			csmt.execute();

			// 获取返回值
			row = csmt.getInt(9);

		} catch (Exception e) {
			System.out.print(e.toString());
		} finally {
			db.Close();
		}
		return row;
	}
}
