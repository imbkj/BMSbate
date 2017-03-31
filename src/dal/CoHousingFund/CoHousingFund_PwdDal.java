package dal.CoHousingFund;

import java.sql.CallableStatement;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import Conn.dbconn;
import Model.CoHousingFundModel;
import Model.CoHousingFundPwdChangeModel;
import Model.CoHousingFundPwdModel;
import Model.CoHousingFundZbModel;
import Util.UserInfo;

/**
 * 密钥专办员数据访问层
 * 
 * @author Administrator
 * 
 */
public class CoHousingFund_PwdDal extends dbconn {

	public int getcohfid(int cid) {
		int cohfid = 0;
		String sql = " SELECT cohf_id FROM CoHousingFund  WHERE cid = ? ";
		try {
			PreparedStatement pstmt = getpre(sql);
			pstmt.setInt(1, cid);
			ResultSet rs = pstmt.executeQuery();
			while (rs.next()) {
				cohfid = rs.getInt("cohf_id");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		return cohfid;
	}

	public int delPwdd(int cfpc_id) {
		int row = 0;
		String sql = " UPDATE CoHousingFundPwdChange SET cfpc_del = 1 WHERE cfpc_id = ? ";
		Object[] objs = { cfpc_id };
		try {
			row = updatePrepareSQL(sql, objs);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return row;
	}

	public int UpdateLimit1(CoHousingFundPwdChangeModel m) {
		int row = 0;
		String sql = " UPDATE CoHousingFundPwdChange SET cfpc_remark = ?,cfpc_yearlimit=? WHERE cfpc_id =? ";
		Object[] objs = { m.getCfpc_remark(), m.getCfpc_yearlimit(),
				m.getCfpc_id() };
		try {
			row = updatePrepareSQL(sql, objs);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return row;
	}

	public int UpdateLimit(CoHousingFundPwdChangeModel m) {
		int row = 0;
		String sql = " UPDATE CoHousingFundPwdChange SET cfpc_remark = ?,cfpc_yearlimit=? WHERE cfpc_id =? ";
		Object[] objs = { m.getCfpc_remark(), m.getCfpc_yearlimit(),
				m.getCfpc_id() };
		try {
			row = updatePrepareSQL(sql, objs);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return row;
	}

	public int UpdatePwdZb(CoHousingFundPwdChangeModel m) {
		int row = 0;
		String sql = " UPDATE CoHousingFundPwdChange SET cfpc_remark = ?,cfpc_chfp_id = ?,cfpc_zb_name=?,cfpc_zb_number=? WHERE cfpc_id = ? ";
		Object[] objs = { m.getCfpc_remark(), m.getCfpc_chfp_id(),
				m.getCfpc_zb_name(), m.getCfpc_zb_number(), m.getCfpc_id() };
		try {
			row = updatePrepareSQL(sql, objs);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return row;
	}

	public CoHousingFundPwdModel getPwdByid(int chfp_id) {
		CoHousingFundPwdModel pm = new CoHousingFundPwdModel();
		String sql = " SELECT chfp_zb_name,chfp_zb_number,chfp_startdate,chfp_enddate,chfp_yearlimit FROM CoHousingFundPwd WHERE chfp_id = ? ";
		PreparedStatement pstmt = getpre(sql);
		try {
			pstmt.setInt(1, chfp_id);
			ResultSet rs = pstmt.executeQuery();
			while (rs.next()) {
				pm.setChfp_zb_name(rs.getString("chfp_zb_name"));
				pm.setChfp_zb_number(rs.getString("chfp_zb_number"));
				pm.setChfp_startdate(rs.getDate("chfp_startdate"));
				pm.setChfp_enddate(rs.getDate("chfp_enddate"));
				pm.setChfp_yearlimit(rs.getInt("chfp_yearlimit"));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return pm;
	}

	public CoHousingFundPwdChangeModel getPwdChangeByid(int cfpc_id) {
		CoHousingFundPwdChangeModel c = new CoHousingFundPwdChangeModel();
		String sql = " SELECT * FROM CoHousingFundPwdChange WHERE cfpc_id = ? and cfpc_del = 0 ";
		PreparedStatement pstmt = getpre(sql);
		try {
			pstmt.setInt(1, cfpc_id);
			ResultSet rs = pstmt.executeQuery();
			while (rs.next()) {
				c.setCfpc_chfp_id(rs.getInt("cfpc_chfp_id"));
				c.setOwnmonth(rs.getInt("ownmonth"));
				c.setCfpc_addtype(rs.getString("cfpc_addtype"));
				c.setCfpc_zb_name(rs.getString("cfpc_zb_name"));
				c.setCfpc_zb_number(rs.getString("cfpc_zb_number"));
				c.setCfpc_yearlimit(rs.getInt("cfpc_yearlimit"));
				c.setStartdate((rs.getDate("cfpc_startdate")));
				c.setEnddate((rs.getDate("cfpc_enddate")));
				c.setCfpc_remark(rs.getString("cfpc_remark"));
				c.setFlag(rs.getInt("cfpc_flag"));
				c.setCfpc_chfz_id(rs.getInt("cfpc_chfz_id"));
				c.setCfpc_id(cfpc_id);

			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return c;
	}

	public List<CoHousingFundPwdChangeModel> queryPwd() {
		List<CoHousingFundPwdChangeModel> list = new ArrayList<CoHousingFundPwdChangeModel>();
		String sql = " SELECT cfpc_remark,a.ownmonth as own,cohf_id,cid,cohf_company,cohf_houseid,cfpc_zb_name,cfpc_zb_number,cfpc_addtype,CONVERT(varchar(100),cfpc_addtime,120) as addtime,CONVERT(varchar(100),cfpc_completetime,120) as ctime,cfpc_addname,cfpc_state,CONVERT(varchar(100),cfpc_startdate,120)as sdate,CONVERT(varchar(100),cfpc_enddate,120)as edate,cfpc_backreason,cfpc_yearlimit FROM CoHousingFundPwdChange a "
				+ " left join (SELECT * FROM CoHousingFund where cid > 0) b "
				+ " on b.cohf_id = a.cfpc_cohf_id "
				+ "left join (SELECT * FROM CoHousingFundPwd WHERE chfp_state=1)c "
				+ " on a.cfpc_chfp_id=c.chfp_id where cfpc_del = 0 order by cfpc_state asc,addtime desc ";
		try {
			ResultSet rs = GRS(sql);
			while (rs.next()) {
				CoHousingFundPwdChangeModel cohf = new CoHousingFundPwdChangeModel();
				cohf.setCfpc_remark(rs.getString("cfpc_remark"));
				cohf.setOwnmonth(rs.getInt("own"));
				cohf.setCid(String.valueOf(rs.getInt("cid")));
				cohf.setCohf_company(rs.getString("cohf_company"));
				cohf.setCohf_houseid(rs.getString("cohf_houseid"));
				cohf.setCfpc_zb_name(rs.getString("cfpc_zb_name"));
				cohf.setCfpc_zb_number(rs.getString("cfpc_zb_number"));
				cohf.setCfpc_addtype(rs.getString("cfpc_addtype"));
				cohf.setCfpc_addtimeString(rs.getString("addtime"));
				cohf.setCfpc_completetime(rs.getString("ctime"));
				cohf.setCfpc_addname(rs.getString("cfpc_addname"));
				cohf.setCfpc_state(rs.getInt("cfpc_state"));
				cohf.setStartDateString(rs.getString("sdate"));
				cohf.setEndDateString(rs.getString("edate"));
				cohf.setBackReason(rs.getString("cfpc_backreason"));
				cohf.setCfpc_yearlimit(rs.getInt("cfpc_yearlimit"));
				list.add(cohf);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}

	public List<CoHousingFundPwdChangeModel> queryPwdByCondition(
			String queryCondition) {
		List<CoHousingFundPwdChangeModel> list = new ArrayList<CoHousingFundPwdChangeModel>();
		String sql = " SELECT cfpc_remark,a.ownmonth as own,cohf_id,cid,cohf_company,cohf_houseid,cfpc_zb_name,cfpc_zb_number,cfpc_addtype,CONVERT(varchar(100),cfpc_addtime,120) as addtime,CONVERT(varchar(100),cfpc_completetime,120) as ctime,cfpc_addname,cfpc_state,CONVERT(varchar(100),cfpc_startdate,120)as sdate,CONVERT(varchar(100),cfpc_enddate,120)as edate,cfpc_backreason FROM CoHousingFundPwdChange a "
				+ " left join (SELECT * FROM CoHousingFund where cid > 0) b "
				+ " on b.cohf_id = a.cfpc_cohf_id  "
				+ "left join (SELECT * FROM CoHousingFundPwd WHERE chfp_state=1)c "
				+ " on a.cfpc_chfp_id=c.chfp_id WHERE "
				+ queryCondition
				+ "  order by cfpc_state asc,addtime desc ";
		try {
			ResultSet rs = GRS(sql);
			while (rs.next()) {
				CoHousingFundPwdChangeModel cohf = new CoHousingFundPwdChangeModel();
				cohf.setCfpc_remark(rs.getString("cfpc_remark"));
				cohf.setOwnmonth(rs.getInt("own"));
				cohf.setCid(String.valueOf(rs.getInt("cid")));
				cohf.setCohf_company(rs.getString("cohf_company"));
				cohf.setCohf_houseid(rs.getString("cohf_houseid"));
				cohf.setCfpc_zb_name(rs.getString("cfpc_zb_name"));
				cohf.setCfpc_zb_number(rs.getString("cfpc_zb_number"));
				cohf.setCfpc_addtype(rs.getString("cfpc_addtype"));
				cohf.setCfpc_addtimeString(rs.getString("addtime"));
				cohf.setCfpc_completetime(rs.getString("ctime"));
				cohf.setCfpc_addname(rs.getString("cfpc_addname"));
				cohf.setCfpc_state(rs.getInt("cfpc_state"));
				cohf.setStartDateString(rs.getString("sdate"));
				cohf.setEndDateString(rs.getString("edate"));
				cohf.setBackReason(rs.getString("cfpc_backreason"));
				list.add(cohf);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}

	public CoHousingFundPwdChangeModel getDate(int cfpc) {
		CoHousingFundPwdChangeModel cfpm = new CoHousingFundPwdChangeModel();
		String sql = " SELECT CONVERT(varchar(100),cfpc_startdate,120) as stime,CONVERT(varchar(100),cfpc_enddate,120) as etime FROM CoHousingFundPwdChange WHERE cfpc_id = ?";
		PreparedStatement pstmt = getpre(sql);
		try {
			pstmt.setInt(1, cfpc);
			ResultSet rs = pstmt.executeQuery();
			while (rs.next()) {
				cfpm.setStartDateString(rs.getString("stime"));
				cfpm.setEndDateString(rs.getString("etime"));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return cfpm;

	}

	public int resubmitPwdChange(CoHousingFundPwdChangeModel cfpm) {
		int row = 0;
		String sql = " UPDATE CoHousingFundPwdChange SET cfpc_state=1 WHERE cfpc_id=?";
		Object[] objs = { cfpm.getCfpc_id() };
		try {
			row = updatePrepareSQL(sql, objs);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return row;

	}

	public int changePwd(CoHousingFundPwdModel cfpm, int cfpc_id) {
		int row = 0;
		String sql = " UPDATE CoHousingFundPwd SET chfp_chfz_id = ?,chfp_zb_name=?,chfp_zb_number=?,chfp_completetime=getDate() WHERE chfp_id = ? and chfp_state = 1 UPDATE CoHousingFundPwdChange SET cfpc_completetime=getDate() WHERE cfpc_id= ?";
		Object[] objs = { cfpm.getChfp_chfz_id(), cfpm.getChfp_zb_name(),
				cfpm.getChfp_zb_number(), cfpm.getChfp_id(), cfpc_id };
		try {
			row = updatePrepareSQL(sql, objs);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return row;
	}

	public int changePwdc(CoHousingFundPwdModel cfpm, int cfpc_id, int sbstate) {
		int row = 0;
		String sql = " UPDATE CoHousingFundPwdChange SET cfpc_chfp_id=?,cfpc_zb_name=?,cfpc_zb_number=?,cfpc_chfz_id=?,cfpc_yearlimit=?,cfpc_startdate=?,cfpc_enddate=?,cfpc_state=? WHERE cfpc_id= ? ";
		Object[] objs = { cfpm.getChfp_id(), cfpm.getChfp_zb_name(),
				cfpm.getChfp_zb_number(), cfpm.getChfp_chfz_id(),
				cfpm.getChfp_yearlimit(), cfpm.getChfp_startdate(),
				cfpm.getChfp_enddate(), sbstate, cfpc_id };
		try {
			row = updatePrepareSQL(sql, objs);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return row;
	}

	public List<CoHousingFundZbModel> notPwdZb(int cohf_id) {
		System.out.println(cohf_id);
		List<CoHousingFundZbModel> list = new ArrayList<CoHousingFundZbModel>();
		String sql = " SELECT * FROM CoHousingFundZb where chfz_id not in (SELECT chfp_chfz_id FROM CoHousingFundPwd where chfp_state = 1 and chfp_cohf_id=? and chfp_chfz_id != 0) and chfz_cohf_id= ? and (chfz_state = 1  or chfz_state = 3) ";
		PreparedStatement pstmt = getpre(sql);
		try {
			pstmt.setInt(1, cohf_id);
			pstmt.setInt(2, cohf_id);
			ResultSet rs = pstmt.executeQuery();
			while (rs.next()) {
				CoHousingFundZbModel cfzm = new CoHousingFundZbModel();
				cfzm.setChfz_name(rs.getString("chfz_name"));
				cfzm.setChfz_number(rs.getString("chfz_number"));
				cfzm.setChfz_id(rs.getInt("chfz_id"));
				cfzm.setChfz_addname(rs.getString("chfz_addname"));
				cfzm.setChfz_mail(rs.getString("chfz_mail"));
				list.add(cfzm);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return list;
	}

	/**
	 * 修改状态
	 * 
	 * @param cfcm
	 * @return
	 */
	public int changeStatus(CoHousingFundPwdChangeModel cfcm) {
		int row = 0;
		String sql = " UPDATE CoHousingFundPwdChange SET cfpc_state = ?,cfpc_backreason=? WHERE cfpc_id = ?  ";
		Object[] objs = { cfcm.getCfpc_state(), cfcm.getBackReason(),
				cfcm.getCfpc_id() };
		try {
			row = updatePrepareSQL(sql, objs);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return row;
	}

	public int renewalPwd(CoHousingFundPwdChangeModel cfpc) {
		int row = 0;
		String sql = " UPDATE CoHousingFundPwd SET ownmonth=?,chfp_yearlimit=?,chfp_startdate=?,chfp_enddate=?,chfp_completetime=getDate(),chfp_addname=? WHERE chfp_id=?  UPDATE CoHousingFundPwdChange SET cfpc_completetime=getDate() WHERE cfpc_id=?";
		Object[] objs = { cfpc.getOwnmonth(), cfpc.getCfpc_yearlimit(),
				new java.sql.Date(cfpc.getStartdate().getTime()),
				new java.sql.Date(cfpc.getEnddate().getTime()),
				cfpc.getCfpc_addname(), cfpc.getCfpc_id(), cfpc.getCfpc_id() };
		try {
			row = updatePrepareSQL(sql, objs);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return row;
	}

	public int renewalPwdc(CoHousingFundPwdChangeModel cfpc) {
		int row = 0;
		String sql = " UPDATE CoHousingFundPwdChange SET cfpc_chfp_id=?,ownmonth=?,cfpc_zb_name=?,cfpc_zb_number=?,cfpc_startdate=?,cfpc_enddate=?,cfpc_state=?,cfpc_yearlimit=? WHERE cfpc_id=?";
		Object[] objs = { cfpc.getCfpc_chfp_id(), cfpc.getOwnmonth(),
				cfpc.getCfpc_zb_name(), cfpc.getCfpc_zb_number(),
				new java.sql.Date(cfpc.getStartdate().getTime()),
				new java.sql.Date(cfpc.getEnddate().getTime()),
				cfpc.getCfpc_state(), cfpc.getCfpc_yearlimit(),
				cfpc.getCfpc_id() };
		try {
			row = updatePrepareSQL(sql, objs);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return row;

	}

	public List<CoHousingFundPwdModel> allPwd(int cohf_id) {
		List<CoHousingFundPwdModel> list = new ArrayList<CoHousingFundPwdModel>();
		String sql = " SELECT * FROM CoHousingFundPwd WHERE chfp_cohf_id = ? and chfp_state = 1 ";
		PreparedStatement pstmt = getpre(sql);
		try {
			pstmt.setInt(1, cohf_id);
			ResultSet rs = pstmt.executeQuery();
			while (rs.next()) {
				CoHousingFundPwdModel cfpm = new CoHousingFundPwdModel();
				cfpm.setChfp_id(rs.getInt("chfp_id"));
				cfpm.setChfp_zb_name(rs.getString("chfp_zb_name"));
				cfpm.setChfp_zb_number(rs.getString("chfp_zb_number"));
				list.add(cfpm);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return list;
	}

	/**
	 * 前到添加密钥信息
	 * 
	 * @param cfpc
	 * @return
	 */
	public int addPwdChange(CoHousingFundPwdChangeModel cfpc, String sign) {
		CallableStatement csmt = getcall("CoHousingFund_AddPwdChange_gxj(?,?,?,?,?,?,?,?,?,?,?,?,?)");
		try {
			csmt.setInt(1, cfpc.getCfpc_chfp_id());
			csmt.setString(2, cfpc.getCfpc_addtype());
			csmt.setString(3, cfpc.getCfpc_addname());
			csmt.setInt(4, cfpc.getOwnmonth());
			csmt.setInt(5, cfpc.getCfpc_cohf_id());
			csmt.setInt(6, cfpc.getCfpc_state());
			csmt.setString(7, cfpc.getCfpc_remark());
			csmt.setString(8, cfpc.getCfpc_zb_name());
			csmt.setString(9, cfpc.getCfpc_zb_number());
			csmt.setInt(10, cfpc.getCfpc_yearlimit());
			csmt.setInt(11, cfpc.getCfpc_flag());
			csmt.setString(12, sign);
			csmt.registerOutParameter(13, java.sql.Types.INTEGER);
			csmt.execute();
			return csmt.getInt(13);
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return 0;
		// int row = 0;
		//
		// try {
		// StringBuffer sql = new StringBuffer(
		// " INSERT CoHousingFundPwdChange(cfpc_chfp_id,cfpc_addtype,cfpc_addname,ownmonth,cfpc_cohf_id,cfpc_state,cfpc_remark ");
		// if (sign != null && sign.equals("密钥变更")) {
		// sql.append(" ,cfpc_zb_name,cfpc_zb_number ) VALUES(?,?,?,?,?,?,?,?,?)");
		// Object[] objs = { cfpc.getCfpc_chfp_id(),
		// cfpc.getCfpc_addtype(), cfpc.getCfpc_addname(),
		// cfpc.getOwnmonth(), cfpc.getCfpc_cohf_id(),
		// cfpc.getCfpc_state(), cfpc.getCfpc_remark(),
		// cfpc.getCfpc_zb_name(), cfpc.getCfpc_zb_number() };
		// row = updatePrepareSQL(sql.toString(), objs);
		// } else {
		// sql.append(" ,cfpc_yearlimit) VALUES(?,?,?,?,?,?,?)");
		// Object[] objs = { cfpc.getCfpc_addtype(),
		// cfpc.getCfpc_addname(), cfpc.getOwnmonth(),
		// cfpc.getCfpc_cohf_id(), cfpc.getCfpc_state(),
		// cfpc.getCfpc_remark(), cfpc.getCfpc_yearlimit() };
		// row = updatePrepareSQL(sql.toString(), objs);
		// }
		//
		// } catch (SQLException e) {
		// e.printStackTrace();
		// }
		// return row;

	}

	/**
	 * 后到列表查询
	 * 
	 * @return
	 */
	public List<CoHousingFundPwdChangeModel> getPwdListChange() {
		List<CoHousingFundPwdChangeModel> list = new ArrayList<CoHousingFundPwdChangeModel>();
		String sql = "SELECT a.cfpc_flag,a.ownmonth,cfpc_chfp_id,cfpc_cohf_id,cfpc_id,cid,cohf_company,cohf_houseid,cfpc_addtype,"
				+ "cfpc_addtime addtime,cfpc_completetime ctime,cfpc_addname,cfpc_state,cfpc_remark,"
				+ "cfpc_yearlimit,cfpc_zb_name,cfpc_zb_number,cfpc_startdate,cfpc_enddate,"
				+ "isnull(msg_a.msg_a,-1)msg_a,cfpc_backreason,isnull(cfpc_zb_name,'')+isnull(cfpc_zb_number,'') numberAndName "
				+ " FROM CoHousingFundPwdChange a "
				+ " left join (SELECT * FROM  CoHousingFund where cid > 0 )b on a.cfpc_cohf_id=b.cohf_id "
				+ " left join (select smwr_tid,case when syme_log_id= "
				+ UserInfo.getUserid()
				+ " then 2 when symr_log_id=  "
				+ UserInfo.getUserid()
				+ " then symr_readstate end msg_a from View_Message where EXISTS(select syme_id from (select smwr_tid,MAX(syme_id)syme_id from View_Message where syme_state=1 and (symr_log_id="
				+ UserInfo.getUserid()
				+ " or syme_log_id="
				+ UserInfo.getUserid()
				+ " ) and  smwr_table='CoHousingFundPwdChange' group by smwr_tid)msg where View_Message.syme_id=msg.syme_id))msg_a on a.cfpc_id=msg_a.smwr_tid"
				+ " where cfpc_del = 0 ORDER BY cfpc_state ASC, addtime DESC";
		try {
			ResultSet rs = GRS(sql);
			while (rs.next()) {
				CoHousingFundPwdChangeModel cfpc = new CoHousingFundPwdChangeModel();
				cfpc.setOwnmonth(rs.getInt("ownmonth"));
				cfpc.setCfpc_flag(rs.getInt("cfpc_flag"));
				cfpc.setCfpc_chfp_id(rs.getInt("cfpc_chfp_id"));
				cfpc.setCfpc_cohf_id(rs.getInt("cfpc_cohf_id"));
				cfpc.setCfpc_id(rs.getInt("cfpc_id"));
				cfpc.setCid(rs.getString("cid"));
				cfpc.setCfpc_cohf_company(rs.getString("cohf_company"));
				cfpc.setCfpc_cohf_houseid(rs.getString("cohf_houseid"));
				cfpc.setCfpc_addtype(rs.getString("cfpc_addtype"));
				if (rs.getString("addtime") != null) {
					cfpc.setCfpc_addtimeString(rs.getString("addtime"));
				}
				if (rs.getString("ctime") != null) {
					cfpc.setCfpc_completetime(rs.getString("ctime"));
				}
				cfpc.setCfpc_addname(rs.getString("cfpc_addname"));
				cfpc.setCfpc_state(rs.getInt("cfpc_state"));
				cfpc.setCfpc_remark(rs.getString("cfpc_remark"));
				cfpc.setCfpc_yearlimit(rs.getInt("cfpc_yearlimit"));
				cfpc.setCfpc_zb_name(rs.getString("cfpc_zb_name"));
				cfpc.setCfpc_zb_number(rs.getString("cfpc_zb_number"));
				cfpc.setCfpc_startdate(rs.getString("cfpc_startdate"));
				cfpc.setCfpc_enddate(rs.getString("cfpc_enddate"));
				cfpc.setStartdate(rs.getDate("cfpc_startdate"));
				cfpc.setEnddate(rs.getDate("cfpc_enddate"));
				cfpc.setMsg_a(rs.getInt("msg_a"));
				cfpc.setBackReason(rs.getString("cfpc_backreason"));
				if (rs.getString("numberAndName") != null
						&& !rs.getString("numberAndName").equals("")) {
					cfpc.setNumberAndName(rs.getString("numberAndName"));
				}

				list.add(cfpc);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}

	public List<CoHousingFundPwdChangeModel> getPwdListChangeByCondition(
			String Condition) {
		List<CoHousingFundPwdChangeModel> list = new ArrayList<CoHousingFundPwdChangeModel>();
		String sql = "SELECT cfpc_chfp_id,cfpc_cohf_id,cfpc_id,cid,cohf_company,cohf_houseid,cfpc_addtype,CONVERT(varchar(100),cfpc_addtime,120) as addtime,CONVERT(varchar(100),cfpc_completetime,120) as ctime,cfpc_addname,cfpc_state,cfpc_remark,cfpc_yearlimit,cfpc_zb_name,cfpc_zb_number,cfpc_startdate,cfpc_enddate,isnull(msg_a.msg_a,-1)msg_a,cfpc_backreason "
				+ " FROM CoHousingFundPwdChange a "
				+ " left join (SELECT * FROM CoHousingFund WHERE cid > 0) b "
				+ " on a.cfpc_cohf_id=b.cohf_id "
				+ " left join (select smwr_tid,case when syme_log_id= "
				+ UserInfo.getUserid()
				+ " then 2 when symr_log_id=  "
				+ UserInfo.getUserid()
				+ " then symr_readstate end msg_a from View_Message where EXISTS(select syme_id from (select smwr_tid,MAX(syme_id)syme_id from View_Message where syme_state=1 and (symr_log_id="
				+ UserInfo.getUserid()
				+ " or syme_log_id="
				+ UserInfo.getUserid()
				+ " ) and  smwr_table='CoHousingFundPwdChange' group by smwr_tid)msg where View_Message.syme_id=msg.syme_id))msg_a on a.cfpc_id=msg_a.smwr_tid where  "
				+ Condition
				+ " and cfpc_del = 0 ORDER BY cfpc_state ASC , addtime DESC";
		try {
			ResultSet rs = GRS(sql);
			while (rs.next()) {
				CoHousingFundPwdChangeModel cfpc = new CoHousingFundPwdChangeModel();
				cfpc.setCfpc_chfp_id(rs.getInt("cfpc_chfp_id"));
				cfpc.setCfpc_cohf_id(rs.getInt("cfpc_cohf_id"));
				cfpc.setCfpc_id(rs.getInt("cfpc_id"));
				cfpc.setCid(rs.getString("cid"));
				cfpc.setCfpc_cohf_company(rs.getString("cohf_company"));
				cfpc.setCfpc_cohf_houseid(rs.getString("cohf_houseid"));
				cfpc.setCfpc_addtype(rs.getString("cfpc_addtype"));
				if (rs.getString("addtime") != null) {
					cfpc.setCfpc_addtimeString(rs.getString("addtime"));
				}
				if (rs.getString("ctime") != null) {
					cfpc.setCfpc_completetime(rs.getString("ctime"));
				}
				cfpc.setCfpc_addname(rs.getString("cfpc_addname"));
				cfpc.setCfpc_state(rs.getInt("cfpc_state"));
				cfpc.setCfpc_remark(rs.getString("cfpc_remark"));
				cfpc.setCfpc_yearlimit(rs.getInt("cfpc_yearlimit"));
				cfpc.setCfpc_zb_name(rs.getString("cfpc_zb_name"));
				cfpc.setCfpc_zb_number(rs.getString("cfpc_zb_number"));
				cfpc.setCfpc_startdate(rs.getString("cfpc_startdate"));
				cfpc.setCfpc_enddate(rs.getString("cfpc_enddate"));
				cfpc.setMsg_a(rs.getInt("msg_a"));
				cfpc.setBackReason(rs.getString("cfpc_backreason"));
				list.add(cfpc);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}

	/**
	 * 根据单位id查密钥
	 * 
	 * @param cohf_id
	 * @return
	 */
	public List<CoHousingFundPwdModel> getCoHousingFundPwdById(int cohf_id) {
		List<CoHousingFundPwdModel> list = new ArrayList<CoHousingFundPwdModel>();
		String sql = " SELECT * FROM CoHousingFundPwd WHERE chfp_cohf_id = ? and chfp_state = 1 ";
		PreparedStatement pstmt = getpre(sql);
		try {
			pstmt.setInt(1, cohf_id);
			ResultSet rs = pstmt.executeQuery();
			while (rs.next()) {
				CoHousingFundPwdModel cfpm = new CoHousingFundPwdModel();
				cfpm.setChfp_id(rs.getInt("chfp_id"));
				cfpm.setChfp_zb_name(rs.getString("chfp_zb_name"));
				cfpm.setChfp_zb_number(rs.getString("chfp_zb_number"));
				cfpm.setChfp_yearlimit(rs.getInt("chfp_yearlimit"));
				cfpm.setChfp_startdate(rs.getDate("chfp_startdate"));
				cfpm.setChfp_enddate(rs.getDate("chfp_enddate"));
				cfpm.setChfp_chfz_id(rs.getInt("chfp_chfz_id"));
				cfpm.setChfp_state(rs.getInt("chfp_state"));
				list.add(cfpm);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return list;
	}

	/**
	 * 该单位该专办员是否存在密钥
	 * 
	 * @param cohf_id
	 * @param addZbid
	 * @return
	 */
	public int havePwd(int cohf_id, int addZbid) {
		int count = 0;
		String sql = " SELECT COUNT(*) FROM CoHousingFundPwd WHERE chfp_cohf_id= ? and chfp_chfz_id = ? and chfp_state = 1 ";
		PreparedStatement pstmt = getpre(sql);
		try {
			pstmt.setInt(1, cohf_id);
			pstmt.setInt(2, addZbid);
			ResultSet rs = pstmt.executeQuery();
			while (rs.next()) {
				count = rs.getInt(1);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return count;
	}

	public int addPwd(CoHousingFundPwdChangeModel cfpc) {
		int row = 0;
		String sql = "declare @i int"
				+ " INSERT CoHousingFundPwd(ownmonth,chfp_cohf_id,chfp_zb_name,chfp_zb_number,chfp_yearlimit,chfp_startdate,chfp_enddate,chfp_addname,chfp_state,chfp_chfz_id,chfp_addtime,chfp_completetime) VALUES(?,?,?,?,?,?,?,?,1,?,getDate(),getDate())"
				+ " set @i=SCOPE_IDENTITY()"
				+ " UPDATE CoHousingFundPwdChange SET cfpc_chfp_id=@i,cfpc_completetime=getDate()"
				+ " WHERE cfpc_id=?  ";
		Object[] objs = { cfpc.getOwnmonth(), cfpc.getCfpc_cohf_id(),
				cfpc.getCfpc_zb_name(), cfpc.getCfpc_zb_number(),
				cfpc.getCfpc_yearlimit(),
				new java.sql.Date(cfpc.getStartdate().getTime()),
				new java.sql.Date(cfpc.getEnddate().getTime()),
				cfpc.getCfpc_addname(), cfpc.getCfpc_chfz_id(),
				// ---------我只是个分割线--------
				cfpc.getCfpc_id() };
		try {
			row = updatePrepareSQL(sql, objs);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return row;
	}

	public int addPwdc(CoHousingFundPwdChangeModel m) {
		int row = 0;
		dbconn db = new dbconn();
		String sql = "UPDATE CoHousingFundPwdChange "
				+ "SET cfpc_chfp_id=cfpc_chfp_id";

		if (m.getCfpc_chfz_id() != null) {
			sql += ",cfpc_chfz_id=" + m.getCfpc_chfz_id();
		}
		if (m.getCfpc_zb_name() != null) {
			sql += ",cfpc_zb_name=" + m.getCfpc_zb_name();
		}
		if (m.getCfpc_zb_number() != null) {
			sql += ",cfpc_zb_number=" + m.getCfpc_zb_number();
		}
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		if (m.getStartdate() != null) {
			sql += ",cfpc_startdate='" + sdf.format(m.getStartdate()) + "'";
		}
		if (m.getEnddate() != null) {
			sql += ",cfpc_enddate='" + sdf.format(m.getEnddate()) + "'";
		}
		if (m.getCfpc_state() != null) {
			sql += ",cfpc_state=" + m.getCfpc_state();
		}

		if (m.getCfpc_id() != null) {
			sql += " where cfpc_id=" + m.getCfpc_id();
			try {
				row = db.updatePrepareSQL(sql);
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

		return row;
	}

	// 插入密码
	public void addPassWord(CoHousingFundPwdModel p) {
		String sql = " INSERT INTO CoHousingFundPwd (chfp_cohf_id,chfp_pwd,chfp_addname,chfp_addtime,chfp_state) values(?,?,?,getDate(),1)";
		Object[] objs = { p.getChfp_cohf_id(), p.getChfp_pwd(),
				p.getChfp_addname() };
		try {
			updatePrepareSQL(sql, objs);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

}
