package dal.Archives;

import java.sql.CallableStatement;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;
import org.zkoss.zul.ListModelList;
import Conn.dbconn;
import Model.EmArchiveDatumModel;
import Model.EmDhFileModel;
import Util.UserInfo;

public class EmArchiveDatum_OperateDal {
	// 查询档案业务数据
	public List<EmArchiveDatumModel> getlist(Integer gid, Integer ownmonth,
			Integer state) {
		List<EmArchiveDatumModel> list = new ListModelList<>();
		dbconn db = new dbconn();
		String sql = "select eada_id,gid,eada_final,eada_tapr_id"
				+ " from emarchivedatum"
				+ " where gid=? and ownmonth>=? and eada_state=?";
		try {
			list = db.find(sql, EmArchiveDatumModel.class, null, gid, ownmonth,
					state);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return list;
	}

	// 更新档案业务数据状态
	public Integer updateInfo(EmArchiveDatumModel em, Integer id) {
		Integer i = 0;
		if (id != null && id > 0) {

			dbconn db = new dbconn();
			String sql = "update emarchivedatum set eada_modtime=getdate(),eada_modname='"
					+ em.getEada_modname() + "'";
			if (em.getEada_state() != null && !em.getEada_state().equals("")) {
				sql += ",eada_state=" + em.getEada_state();
			}

			sql += " where eada_id=?";
			try {
				i = db.updatePrepareSQL(sql, id);
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return i;
	}

	// 更新档案业务数据状态
	public Integer updateInfoEnd(Integer id) {
		Integer i = 0;
		if (id != null && id > 0) {

			dbconn db = new dbconn();
			String sql = "update emarchivedatum set eada_modtime=getdate(),eada_modname='"
					+UserInfo.getUsername() + "',eada_final=3 ";
			sql += " where eada_id=?";
			try {
				i = db.updatePrepareSQL(sql, id);
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return i;
	}

	// 受理EmArchive_Return_cyj
	public int Accepted(EmArchiveDatumModel m) {
		dbconn conn = new dbconn();
		try {
			CallableStatement c = conn.getcall("EmArchive_Auditing_cyj(?,?,?)");
			c.setInt(1, m.getEada_id());
			c.setString(2, m.getEada_addname());
			c.registerOutParameter(3, java.sql.Types.INTEGER);
			c.execute();
			return c.getInt(3);
		} catch (SQLException e) {
			return -2;
		}
	}

	// 退回
	public int EmArchiveReturn(EmArchiveDatumModel m) {
		dbconn conn = new dbconn();
		try {
			CallableStatement c = conn
					.getcall("EmArchive_Return_cyj(?,?,?,?,?,?)");
			c.setInt(1, m.getEada_id());
			c.setString(2, m.getEada_backcase());
			c.setString(3, m.getEada_addname());
			c.setInt(4, m.getGid());
			c.setInt(5, m.getCid());
			c.registerOutParameter(6, java.sql.Types.INTEGER);
			c.execute();
			int k = c.getInt(6);
			return k;
		} catch (SQLException e) {
			return -2;
		}
	}

	// 材料收回
	public int EmArchiveDataRetake(EmArchiveDatumModel m) {
		dbconn conn = new dbconn();
		try {
			CallableStatement c = conn
					.getcall("EmArchive_DataRetake_cyj(?,?,?,?)");
			c.setInt(1, m.getEada_id());
			c.setDate(2, timechange(m.getEada_returnarchivedate()));
			c.setString(3, m.getEada_addname());
			c.registerOutParameter(4, java.sql.Types.INTEGER);
			c.execute();
			int k = c.getInt(4);
			return k;
		} catch (SQLException e) {
			return -2;
		}
	}

	// 开具证明——更新开具证明的内容
	public int EmArchiveOpenProve(EmArchiveDatumModel m) {
		dbconn conn = new dbconn();
		try {
			CallableStatement c = conn
					.getcall("EmArchive_OpenProve_cyj(?,?,?,?,?,?)");
			c.setInt(1, m.getEada_id());
			c.setString(2, m.getEada_prove());
			c.setDate(3, timechange(m.getEada_drawprovedate()));
			c.setString(4, m.getEada_drawprovepeople());
			c.setString(5, m.getEada_addname());
			c.registerOutParameter(6, java.sql.Types.INTEGER);
			c.execute();
			int k = c.getInt(6);
			return k;
		} catch (SQLException e) {
			return -2;
		}
	}

	// 材料归档
	public int EmArchiveDataFile(EmArchiveDatumModel m) {
		dbconn conn = new dbconn();
		try {
			CallableStatement c = conn.getcall("EmArchive_DataFile(?,?,?,?)");
			c.setInt(1, m.getEada_id());
			c.setDate(2, timechange(m.getEada_filedate()));
			c.setString(3, m.getEada_addname());
			c.registerOutParameter(4, java.sql.Types.INTEGER);
			c.execute();
			int k = c.getInt(4);
			return k;
		} catch (SQLException e) {
			return -2;
		}
	}

	// 转正定级
	public int EmArchivePositive(EmArchiveDatumModel m) {
		dbconn conn = new dbconn();
		try {
			CallableStatement c = conn
					.getcall("EmArchive_Positive_cyj(?,?,?,?,?,?,?,?,?)");
			c.setInt(1, m.getEada_id());
			c.setDate(2, timechange(m.getEada_drawformdate()));
			c.setDate(3, timechange(m.getEada_returnformdate()));
			c.setDate(4, timechange(m.getEada_transactdate()));
			c.setString(5, m.getEada_rsetup());
			c.setDate(6, timechange(m.getEada_rdate()));
			c.setString(7, m.getEada_drawformpeople());
			c.setString(8, m.getEada_addname());
			c.registerOutParameter(9, java.sql.Types.INTEGER);
			c.execute();
			int k = c.getInt(9);
			return k;
		} catch (SQLException e) {
			return -2;
		}
	}

	// 档案调出
	public int EmArchiveCheckOut(EmArchiveDatumModel m, int emar_id) {
		dbconn conn = new dbconn();
		try {
			CallableStatement c = conn
					.getcall("EmArchive_CheckOut_cyj(?,?,?,?,?,?,?,?,?,?)");
			c.setInt(1, m.getEada_id());
			c.setInt(2, emar_id);
			c.setString(3, m.getEada_wtmode());
			c.setString(4, m.getEada_checkoutmode());
			c.setString(5, m.getEada_checkoutreason());
			c.setDate(6, timechange(m.getEada_checkoutdate()));
			c.setString(7, m.getEada_checkoutsetup());
			c.setString(8, m.getEada_final());
			c.setString(9, m.getEada_addname());
			c.registerOutParameter(10, java.sql.Types.INTEGER);
			c.execute();
			int k = c.getInt(10);
			return k;
		} catch (SQLException e) {
			return -2;
		}
	}

	// 欠费情况
	public int EmArchiveFee(EmArchiveDatumModel m) {
		dbconn conn = new dbconn();
		try {
			CallableStatement c = conn.getcall("EmArchive_Fee_cyj(?,?,?,?)");
			c.setInt(1, m.getEada_id());
			c.setString(2, m.getEada_arrearageinfo());
			c.setString(3, m.getEada_addname());
			c.registerOutParameter(4, java.sql.Types.INTEGER);
			c.execute();
			int k = c.getInt(4);
			return k;
		} catch (SQLException e) {
			return -2;
		}
	}

	// 新增材料借阅数据
	public int EmArchiveAddData(EmArchiveDatumModel m, String remark) {
		dbconn conn = new dbconn();
		try {
			CallableStatement c = conn
					.getcall("EmArchiveDatum_DataAdd_cyj(?,?,?,?,?,?)");
			c.setInt(1, m.getGid());
			c.setInt(2, m.getCid());
			c.setString(3, m.getEada_lendcause());
			c.setString(4, remark);
			c.setString(5, m.getEada_addname());
			c.registerOutParameter(6, java.sql.Types.INTEGER);
			c.execute();
			int k = c.getInt(6);
			return k;
		} catch (Exception e) {
			System.out.println("错误：" + e.getMessage());
			return -2;
		}
	}

	// 时间格式转换
	private Date timechange(java.util.Date d) {
		Date da = null;
		if (d != null && !d.equals("")) {
			java.sql.Date date = new java.sql.Date(d.getTime());
			da = date;
		}
		return da;
	}

	// 更新档案业务表的任务单id
	public boolean updateTaprid(int eadaid, int taprid) {
		dbconn db = new dbconn();
		PreparedStatement psmt = null;
		int row = 0;
		String sql = "update EmArchiveDatum set eada_tapr_id=? where eada_id=?";
		try {
			psmt = db.getpre(sql);
			psmt.setInt(1, taprid);
			psmt.setInt(2, eadaid);
			row = psmt.executeUpdate();
		} catch (Exception e) {
			// TODO: handle exception
		}
		return row > 0 ? true : false;
	}

	// 删除档案业务的数据
	public boolean delEmArchiveDatum(int eadaid) {
		dbconn db = new dbconn();
		PreparedStatement psmt = null;
		int row = 0;
		String sql = "delete from EmArchiveDatum where eada_id=?";
		try {
			psmt = db.getpre(sql);
			psmt.setInt(1, eadaid);
			row = psmt.executeUpdate();
		} catch (Exception e) {
			// TODO: handle exception
		}
		return row > 0 ? true : false;
	}

	// 更新档案业务表信息
	public int EmArchiveUpdateData(EmArchiveDatumModel m, String remark,
			String sql) {
		// sql语句最前面一定是逗号","，sql的格式如：sql=",eada_modname=addname"
		dbconn conn = new dbconn();
		try {
			CallableStatement c = conn
					.getcall("EmArchiveDatum_DatauPDATE_cyj(?,?,?,?,?,?)");
			c.setInt(1, m.getEada_id());
			c.setString(2, sql.toString());
			c.setString(3, remark);
			c.setString(4, m.getEada_addname());
			c.setString(5, m.getEada_remark());
			c.registerOutParameter(6, java.sql.Types.INTEGER);
			c.execute();
			int k = c.getInt(6);
			return k;
		} catch (Exception e) {
			System.out.println("错误：" + e.getMessage());
			return -2;
		}
	}

	// 后道调出档案
	public Integer EmArchiveOut(EmArchiveDatumModel m) {
		dbconn conn = new dbconn();
		try {
			CallableStatement c = conn
					.getcall("EmArchive_CheckOut_cyj(?,?,?,?,?,?,?,?,?,?)");
			c.setInt(1, m.getEada_id());
			c.setString(2, m.getEada_wtmode());
			c.setString(3, m.getEada_checkoutmode());
			c.setString(4, m.getEada_checkoutreason());
			c.setDate(5, datechange(m.getEada_checkoutdate()));
			c.setString(6, m.getEada_checkoutsetup());
			c.setString(7, m.getEada_final());
			c.setString(8, UserInfo.getUsername());
			c.setInt(9, m.getGid());
			c.registerOutParameter(10, java.sql.Types.INTEGER);
			c.execute();
			int k = c.getInt(10);
			return k;
		} catch (Exception e) {
			System.out.println("错误：" + e.getMessage());
			return -2;
		}
	}

	// 开具证明信息新增
	public int EmArchiveFileProveAdd(EmArchiveDatumModel m, String remark) {
		dbconn conn = new dbconn();
		try {
			CallableStatement c = conn
					.getcall("EmArchiveDatua_FileProve_cyj(?,?,?,?,?,?,?,?)");
			c.setInt(1, m.getGid());
			c.setInt(2, m.getCid());
			c.setString(3, m.getEada_orderdata());
			c.setString(4, m.getEada_cause());
			c.setString(5, remark);
			c.setString(6, m.getEada_addname());
			c.setString(7, m.getEada_prove());
			c.registerOutParameter(8, java.sql.Types.INTEGER);
			c.execute();
			int k = c.getInt(8);
			return k;
		} catch (Exception e) {
			System.out.println("错误：" + e.getMessage());
			return -2;
		}
	}

	// 材料鉴别归档新增
	public int EmArchiveFilingAdd(EmArchiveDatumModel m, String remark) {
		dbconn conn = new dbconn();
		try {
			CallableStatement c = conn
					.getcall("EmArchive_ServerFiling_cyj(?,?,?,?,?,?)");
			c.setInt(1, m.getGid());
			c.setInt(2, m.getCid());
			c.setString(3, m.getEada_file());
			c.setString(4, remark);
			c.setString(5, m.getEada_addname());
			c.registerOutParameter(6, java.sql.Types.INTEGER);
			c.execute();
			int k = c.getInt(6);
			return k;
		} catch (Exception e) {
			System.out.println("错误：" + e.getMessage());
			return -2;
		}
	}

	// 新增档案材料归档的材料信息
	public int FilingAdd(EmDhFileModel m) {
		Integer k = 0;
		dbconn conn = new dbconn();
		try {
			CallableStatement c = conn
					.getcall("EmDhFile_Add_p_cyj(?,?,?,?,?,?)");
			c.setInt(1, m.getDhfl_dh_id());
			c.setInt(2, m.getDhfl_file_id());
			c.setString(3, m.getDhfl_file_content());
			c.setString(4, m.getDhfl_addname());
			c.setInt(5, m.getDhfl_checked());
			c.registerOutParameter(6, java.sql.Types.INTEGER);
			c.execute();
			k = c.getInt(6);
			return k;
		} catch (Exception e) {
			System.out.println("错误：" + e.getMessage());
			return -2;
		}
	}

	// 转正定级新增
	public int EmArchiveFilePassAdd(EmArchiveDatumModel m, String remark) {
		dbconn conn = new dbconn();
		try {
			CallableStatement c = conn
					.getcall("EmArchive_ServerFilePass_cyj(?,?,?,?,?,?,?,?)");
			c.setInt(1, m.getGid());
			c.setInt(2, m.getCid());
			c.setInt(3, m.getEada_zg());
			c.setInt(4, m.getEada_bf());
			c.setInt(5, m.getEada_dms());
			c.setString(6, remark);
			c.setString(7, m.getEada_addname());
			c.registerOutParameter(8, java.sql.Types.INTEGER);
			c.execute();
			int k = c.getInt(8);
			return k;
		} catch (Exception e) {
			System.out.println("错误：" + e.getMessage());
			return -2;
		}
	}

	// 新增或修改业务材料提交情况表(DocumentsSubmitInfo)
	public int addDocSubmitInfo(String dire_id, String tid, String ifsubmit,
			String count, String handover_people, String addname, int state) {
		dbconn conn = new dbconn();
		try {
			CallableStatement c = conn
					.getcall("DocSubmitInfoAdd_p_CYJ(?,?,?,?,?,?,?,?)");
			c.setString(1, dire_id);
			c.setString(2, tid);
			c.setString(3, ifsubmit);
			c.setString(4, count);
			c.setString(5, handover_people);
			c.setString(6, addname);
			c.setInt(7, state);
			c.registerOutParameter(8, java.sql.Types.INTEGER);
			c.execute();
			return c.getInt(8);
		} catch (SQLException e) {
			return 1;
		}
	}

	// 删除档案业务新增数据
	public int deleteInfo(int eada_id, int tapr_id) {
		dbconn conn = new dbconn();
		try {
			CallableStatement c = conn.getcall("EmArchive_delete_cyj(?,?,?)");
			c.setInt(1, eada_id);
			c.setInt(2, tapr_id);
			c.registerOutParameter(3, java.sql.Types.INTEGER);
			c.execute();
			return c.getInt(3);
		} catch (SQLException e) {
			return 1;
		}
	}

	// 新增档案调出
	public int addCheckOut(EmArchiveDatumModel m, String remark) {
		dbconn conn = new dbconn();
		try {
			CallableStatement c = conn
					.getcall("EmArchive_ServerFileCheckOut_cyj(?,?,?,?,?,?,?,?,?,?,?,?)");
			c.setInt(1, m.getGid());
			c.setInt(2, m.getCid());
			c.setString(3, m.getEada_charge());
			c.setDate(4, timechange(m.getEada_chargedate()));
			c.setString(5, m.getEada_checkoutmode());
			c.setString(6, m.getEada_checkoutreason());
			c.setDate(7, timechange(m.getEada_checkoutdate()));
			c.setString(8, m.getEada_checkoutsetup());
			c.setString(9, remark);
			c.setString(10, m.getEada_addname());
			c.setString(11, m.getEada_fid());
			c.registerOutParameter(12, java.sql.Types.INTEGER);
			c.execute();
			return c.getInt(12);
		} catch (SQLException e) {
			return 1;
		}
	}

	// 时间格式转换
	private java.sql.Date datechange(java.util.Date d) {
		Date da = null;
		if (d != null && !d.equals("")) {
			java.sql.Date date = new java.sql.Date(d.getTime());
			da = date;
		}
		return da;
	}
}
