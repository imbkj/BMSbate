package dal.CoHousingFund;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import org.zkoss.zul.ListModelList;

import sun.security.jca.GetInstance;

import com.sun.org.apache.regexp.internal.recompile;

import Conn.dbconn;
import Controller.EmSheBaocard.newExcelImpl;
import Model.CoHousingFundModel;
import Model.CoHousingFundPwdModel;
import Model.CoHousingFundZbChangeModel;
import Model.CoHousingFundZbModel;
import Util.UserInfo;

/**
 * 专办员申办数据访问
 * 
 * @author Administrator
 * 
 */
public class CoHousingFundZbDal extends dbconn {

	public CoHousingFundZbModel getZb(int chfz_id) {
		CoHousingFundZbModel m = new CoHousingFundZbModel();
		String sql = " SELECT * FROM CoHousingFundZb WHERE chfz_id = ? ";
		PreparedStatement pstmt = getpre(sql);
		try {
			pstmt.setInt(1, chfz_id);
			ResultSet rs = pstmt.executeQuery();
			while (rs.next()) {
				m.setChfz_name(rs.getString("chfz_name"));
				m.setChfz_number(rs.getString("chfz_number"));
			}
		} catch (Exception e) {

		}
		return m;
	}

	public int delSb(int cfzc_id) {
		int row = 0;
		String sql = " UPDATE CoHousingFundZbChange SET cfzc_del = 1 WHERE cfzc_id = ? ";
		Object[] objs = { cfzc_id };
		try {
			row = updatePrepareSQL(sql, objs);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return row;
	}

	public int UpdateZb(int chfz_id, int cfzc_id) {
		int row = 0;
		String sql = " UPDATE CoHousingFundZbChange SET cfzc_chfz_id = ? WHERE cfzc_id = ?";
		Object[] objs = { chfz_id, cfzc_id };
		try {
			row = updatePrepareSQL(sql, objs);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return row;
	}

	public CoHousingFundZbModel getNewZbInfo(int chfz_id) {
		CoHousingFundZbModel c = new CoHousingFundZbModel();
		String sql = " SELECT * FROM CoHousingFundZb WHERE chfz_id = ? ";

		PreparedStatement pstmt = getpre(sql);
		try {
			pstmt.setInt(1, chfz_id);
			ResultSet rs = pstmt.executeQuery();
			while (rs.next()) {
				c.setChfz_name(rs.getString("chfz_name"));
				c.setChfz_number(rs.getString("chfz_number"));
				c.setChfz_tel(rs.getString("chfz_tel"));
				c.setChfz_mail(rs.getString("chfz_mail"));
				c.setChfz_mobile(rs.getString("chfz_mobile"));
				c.setRemark(rs.getString("chfz_stop_reason"));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return c;
	}

	public CoHousingFundZbChangeModel ZbChangeInfo(int cfzc_id) {
		CoHousingFundZbChangeModel c = new CoHousingFundZbChangeModel();
		String sql = " SELECT * FROM CoHousingFundZbChange WHERE cfzc_id = ? ";
		PreparedStatement pstmt = getpre(sql);
		try {
			pstmt.setInt(1, cfzc_id);
			ResultSet rs = pstmt.executeQuery();
			while (rs.next()) {
				c.setCfzc_name(rs.getString("cfzc_name"));
				c.setCfzc_number(rs.getString("cfzc_number"));
				c.setOwnmonth(rs.getInt("ownmonth"));
				c.setCfzc_tel(rs.getString("cfzc_tel"));
				c.setCfzc_mail(rs.getString("cfzc_mail"));
				c.setCfzc_mobile(rs.getString("cfzc_mobile"));
				c.setCfzc_remark(rs.getString("cfzc_remark"));
				c.setCfzc_state(rs.getInt("cfzc_state"));
				c.setCfzc_addname(rs.getString("cfzc_addname"));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return c;
	}

	public CoHousingFundModel getCohf(int cohf_id) {
		CoHousingFundModel cohf = null;
		String sql = " SELECT cid FROM CoHousingFund WHERE cohf_id = ? ";
		PreparedStatement pstmt = getpre(sql);
		try {
			pstmt.setInt(1, cohf_id);
			ResultSet rs = pstmt.executeQuery();
			while (rs.next()) {
				cohf = new CoHousingFundModel();
				cohf.setCid(rs.getInt("cid"));

			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return cohf;
	}

	public List<CoHousingFundModel> queryZbByCondition(String Condition) {
		List<CoHousingFundModel> list = new ArrayList<CoHousingFundModel>();
		String sql = " SELECT cfzc_id,chfz_id,cohf_id,cid,cohf_company,cohf_houseid,cfzc_addtype,cfzc_name,cfzc_number,cfzc_addname,cfzc_state,CONVERT(varchar(100),cfzc_addtime,120) as addtime,CONVERT(varchar(100),chfz_addtime,120) as ctime FROM CoHousingFundZbChange b "
				+ " left join  (SELECT * FROM CoHousingFund WHERE cid > 0)a"
				+ " on a.cohf_id = b.cfzc_cohf_id"
				+ " left join (SELECT chfz_id,chfz_addtime,chfz_cohf_id FROM CoHousingFundZb WHERE chfz_state=1)c "
				+ " on b.cfzc_chfz_id=c.chfz_id  where "
				+ Condition
				+ " order by cfzc_state asc, addtime desc";
		try {
			ResultSet rs = GRS(sql);
			while (rs.next()) {
				CoHousingFundModel chfm = new CoHousingFundModel();
				chfm.setCohf_id(rs.getInt("cohf_id"));
				chfm.setCid(rs.getInt("cid"));
				chfm.setCohf_company(rs.getString("cohf_company"));
				chfm.setCohf_houseid(rs.getString("cohf_houseid"));
				chfm.setCfzc_addtype(rs.getString("cfzc_addtype"));
				chfm.setCfzc_name(rs.getString("cfzc_name"));
				chfm.setCfzc_number(rs.getString("cfzc_number"));
				chfm.setCfzc_addtime(rs.getString("addtime"));
				chfm.setCfzc_addname(rs.getString("cfzc_addname"));
				chfm.setCfzc_state(rs.getInt("cfzc_state"));
				chfm.setChfz_addtime(rs.getString("ctime"));
				chfm.setChfz_id(rs.getInt("chfz_id"));
				chfm.setCfzc_id(rs.getInt("cfzc_id"));
				list.add(chfm);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;

	}

	/**
	 * 所有的数据
	 * 
	 * @param Condition
	 * @return
	 */
	public List<CoHousingFundModel> queryZbByC(String Condition) {
		List<CoHousingFundModel> list = new ArrayList<CoHousingFundModel>();
		String sql = " SELECT cfzc_id,chfz_id,cohf_id,cid,cohf_company,b.ownmonth as own,cfzc_remark,CONVERT(varchar(100),cfzc_completetime,120)as ctime,cfzc_tel,cfzc_mobile,cfzc_mail,cohf_houseid,cfzc_addtype,cfzc_name,cfzc_number,cfzc_addname,cfzc_state,CONVERT(varchar(100),cfzc_addtime,120) as addtime,cfzc_backreason FROM CoHousingFundZbChange b "
				+ " left join  (SELECT * FROM CoHousingFund WHERE cid > 0 )a"
				+ " on a.cohf_id = b.cfzc_cohf_id"
				+ " left join (SELECT chfz_id,chfz_addtime,chfz_cohf_id FROM CoHousingFundZb )c "
				+ " on b.cfzc_chfz_id=c.chfz_id  WHERE "
				+ Condition
				+ " order by cfzc_state asc, addtime desc";
		try {
			ResultSet rs = GRS(sql);
			while (rs.next()) {
				CoHousingFundModel chfm = new CoHousingFundModel();
				chfm.setOwnmonth(rs.getInt("own"));
				chfm.setCohf_completetimeString(rs.getString("ctime"));
				chfm.setTel(rs.getString("cfzc_tel"));
				chfm.setMobile(rs.getString("cfzc_mobile"));
				chfm.setEmail(rs.getString("cfzc_mail"));
				chfm.setRemark(rs.getString("cfzc_remark"));
				chfm.setCohf_id(rs.getInt("cohf_id"));
				chfm.setCid(rs.getInt("cid"));
				chfm.setCohf_company(rs.getString("cohf_company"));
				chfm.setCohf_houseid(rs.getString("cohf_houseid"));
				chfm.setCfzc_addtype(rs.getString("cfzc_addtype"));
				chfm.setCfzc_name(rs.getString("cfzc_name"));
				chfm.setCfzc_number(rs.getString("cfzc_number"));
				chfm.setCfzc_addtime(rs.getString("addtime"));
				chfm.setCfzc_addname(rs.getString("cfzc_addname"));
				chfm.setCfzc_state(rs.getInt("cfzc_state"));
				chfm.setChfz_id(rs.getInt("chfz_id"));
				chfm.setCfzc_id(rs.getInt("cfzc_id"));
				chfm.setBackReason(rs.getString("cfzc_backreason"));
				list.add(chfm);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}

	/**
	 * 查询专办员的所有数据
	 * 
	 * @return
	 */
	public List<CoHousingFundModel> queryZbC() {
		List<CoHousingFundModel> list = new ArrayList<CoHousingFundModel>();
		String sql = " SELECT b.cfzc_id,chfz_id,cfzc_remark,CONVERT(varchar(100),cfzc_completetime,120)as ctime,chfz_tel,chfz_mobile,chfz_mail,b.ownmonth as own,cfzc_id,chfz_id,cohf_id,cid,cohf_company,cohf_houseid,cfzc_addtype,cfzc_name,cfzc_number,cfzc_state,cfzc_addname,CONVERT(varchar(100),cfzc_addtime,120) as addtime,CONVERT(varchar(100),chfz_addtime,120) as ctime,cfzc_backreason FROM CoHousingFundZbChange b "
				+ " left join (SELECT * FROM CoHousingFund where cid > 0)a"
				+ " on a.cohf_id = b.cfzc_cohf_id"
				+ " left join (SELECT * FROM CoHousingFundZb )c "
				+ " on b.cfzc_chfz_id=c.chfz_id WHERE cfzc_del = 0 " 
				+ " and a.CID in ( select cid from DataPopedom where log_id="
				+ UserInfo.getUserid()
				+ " and dat_selected=1 )"
				+" order by cfzc_state asc, addtime desc";
		try {
			ResultSet rs = GRS(sql);
			while (rs.next()) {
				CoHousingFundModel chfm = new CoHousingFundModel();
				chfm.setOwnmonth(rs.getInt("own"));
				chfm.setCfzc_id(rs.getInt("cfzc_id"));
				chfm.setChfz_id(rs.getInt("chfz_id"));
				chfm.setCohf_completetimeString(rs.getString("ctime"));
				chfm.setTel(rs.getString("chfz_tel"));
				chfm.setMobile(rs.getString("chfz_mobile"));
				chfm.setEmail(rs.getString("chfz_mail"));
				chfm.setRemark(rs.getString("cfzc_remark"));
				chfm.setCohf_id(rs.getInt("cohf_id"));
				chfm.setCid(rs.getInt("cid"));
				chfm.setCohf_company(rs.getString("cohf_company"));
				chfm.setCohf_houseid(rs.getString("cohf_houseid"));
				chfm.setCfzc_addtype(rs.getString("cfzc_addtype"));
				chfm.setCfzc_name(rs.getString("cfzc_name"));
				chfm.setCfzc_number(rs.getString("cfzc_number"));
				chfm.setCfzc_addtime(rs.getString("addtime"));
				chfm.setCfzc_addname(rs.getString("cfzc_addname"));
				chfm.setCfzc_state(rs.getInt("cfzc_state"));
				chfm.setChfz_addtime(rs.getString("ctime"));
				chfm.setChfz_id(rs.getInt("chfz_id"));
				chfm.setCfzc_id(rs.getInt("cfzc_id"));
				chfm.setBackReason(rs.getString("cfzc_backreason"));
				list.add(chfm);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}

	/**
	 * 查询专办员
	 * 
	 * @return
	 */
	public List<CoHousingFundModel> queryZb() {
		List<CoHousingFundModel> list = new ArrayList<CoHousingFundModel>();
		String sql = " SELECT cfzc_remark,CONVERT(varchar(100),cfzc_completetime,120)as ctime,chfz_tel,chfz_mobile,chfz_mail,c.ownmonth as own,cfzc_id,chfz_id,cohf_id,cid,cohf_company,cohf_houseid,cfzc_addtype,chfz_name,chfz_number,cfzc_state,cfzc_addname,CONVERT(varchar(100),cfzc_addtime,120) as addtime,CONVERT(varchar(100),chfz_addtime,120) as ctime FROM CoHousingFundZbChange b "
				+ " left join  (SELECT * FROM CoHousingFund where cid > 0) a"
				+ " on a.cohf_id = b.cfzc_cohf_id"
				+ " left join (SELECT * FROM CoHousingFundZb WHERE chfz_state=1)c "
				+ " on b.cfzc_chfz_id=c.chfz_id   WHERE cid > 0 and cfzc_del = 0 "  
				+ " and a.CID in ( select cid from DataPopedom where log_id="
				+ UserInfo.getUserid()
				+ " and dat_selected=1 )"
				+" order by cfzc_state asc, addtime desc";
		try {
			ResultSet rs = GRS(sql);
			while (rs.next()) {
				CoHousingFundModel chfm = new CoHousingFundModel();
				chfm.setOwnmonth(rs.getInt("own"));
				chfm.setCohf_completetimeString(rs.getString("ctime"));
				chfm.setTel(rs.getString("chfz_tel"));
				chfm.setMobile(rs.getString("chfz_mobile"));
				chfm.setEmail(rs.getString("chfz_mail"));
				chfm.setRemark(rs.getString("cfzc_remark"));
				chfm.setCohf_id(rs.getInt("cohf_id"));
				chfm.setCid(rs.getInt("cid"));
				chfm.setCohf_company(rs.getString("cohf_company"));
				chfm.setCohf_houseid(rs.getString("cohf_houseid"));
				chfm.setCfzc_addtype(rs.getString("cfzc_addtype"));
				chfm.setCfzc_name(rs.getString("chfz_name"));
				chfm.setCfzc_number(rs.getString("chfz_number"));
				chfm.setCfzc_addtime(rs.getString("addtime"));
				chfm.setCfzc_addname(rs.getString("cfzc_addname"));
				chfm.setCfzc_state(rs.getInt("cfzc_state"));
				chfm.setChfz_addtime(rs.getString("ctime"));
				chfm.setChfz_id(rs.getInt("chfz_id"));
				chfm.setCfzc_id(rs.getInt("cfzc_id"));
				list.add(chfm);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}

	public int delZbc(CoHousingFundZbChangeModel cfzc) {
		int row = 0;
		String sql = "  UPDATE CoHousingFundZbChange SET cfzc_flag=?,cfzc_name=?,cfzc_number=?,cfzc_chfz_id=?,cfzc_tel=?,cfzc_mobile=?,cfzc_mail=?,cfzc_state=? WHERE cfzc_id= ?";
		Object[] objs = { cfzc.getFlag(), cfzc.getCfzc_name(),
				cfzc.getCfzc_number(), cfzc.getCfzc_chfz_id(),
				cfzc.getCfzc_tel(), cfzc.getCfzc_mobile(), cfzc.getCfzc_mail(),
				cfzc.getCfzc_state(), cfzc.getCfzc_id() };
		try {
			row = updatePrepareSQL(sql, objs);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return row;

	}

	/**
	 * 根据id删除专办员
	 * 
	 * @param chfz_id
	 * @return
	 */
	public int delZb(CoHousingFundZbChangeModel cfzc) {
		int row = 0;
		String sql = " UPDATE  CoHousingFundZb SET chfz_state = 0 WHERE chfz_id=? UPDATE CoHousingFundZbChange SET cfzc_completetime=getDate() WHERE cfzc_id= ?";
		Object[] objs = { cfzc.getCfzc_chfz_id(), cfzc.getCfzc_id() };
		try {
			row = updatePrepareSQL(sql, objs);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return row;

	}

	/**
	 * 根据id查专办员
	 * 
	 * @return
	 */
	public CoHousingFundZbModel getCoHousingFundZbById(int chfz_id) {
		CoHousingFundZbModel chfz = new CoHousingFundZbModel();
		String sql = " SELECT * FROM CoHousingFundZb WHERE chfz_id = ? "
				+ " and  CID in ( select cid from DataPopedom where log_id="
				+ UserInfo.getUserid()
				+ " and dat_selected=1 )";
		PreparedStatement pstmt = getpre(sql);
		try {
			pstmt.setInt(1, chfz_id);
			ResultSet rs = pstmt.executeQuery();
			while (rs.next()) {
				chfz.setChfz_name(rs.getString("chfz_name"));
				chfz.setChfz_number(rs.getString("chfz_number"));
				chfz.setChfz_cohf_id(rs.getInt("chfz_cohf_id"));
			}
			System.out.println(sql);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return chfz;

	}

	public int updateCusZbChange(CoHousingFundZbChangeModel c) {
		int row = 0;
		if (c.getCfzc_state() != 0 && c.getCfzc_state() == 3) {
			String sql = " UPDATE CoHousingFundZbChange SET cfzc_chfz_id=?,cfzc_state=?,cfzc_name = ? ,cfzc_tel = ? ,cfzc_mobile= ?,cfzc_mail = ?,cfzc_completetime=getDate() WHERE cfzc_id= ?";
			Object[] objs = { c.getCfzc_chfz_id(), c.getCfzc_state(),
					c.getCfzc_name(), c.getCfzc_tel(), c.getCfzc_mobile(),
					c.getCfzc_mail(), c.getCfzc_id() };
			try {
				row = updatePrepareSQL(sql, objs);
			} catch (SQLException e) {
				e.printStackTrace();
			}
		} else {
			String sql = " UPDATE CoHousingFundZbChange SET cfzc_flag=?,cfzc_chfz_id=?,cfzc_name = ? ,cfzc_tel = ? ,cfzc_mobile= ?,cfzc_mail = ?,cfzc_number=?,cfzc_state=2 WHERE cfzc_id= ?";
			Object[] objs = { c.getFlag(), c.getCfzc_chfz_id(),
					c.getCfzc_name(), c.getCfzc_tel(), c.getCfzc_mobile(),
					c.getCfzc_mail(), c.getCfzc_number(), c.getCfzc_id() };
			try {
				row = updatePrepareSQL(sql, objs);
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return row;

	}

	/**
	 * 后到修改专办员信息
	 * 
	 * @param cfzm
	 * @return
	 */
	public int updateZb(CoHousingFundZbChangeModel cfzm, int chfz_id) {
		int row = 0;
		String sql = " UPDATE CoHousingFundZb SET chfz_name = ? ,chfz_tel = ? ,chfz_mobile= ?,chfz_mail = ? WHERE chfz_id = ? UPDATE CoHousingFundZbChange SET cfzc_completetime = getDate() WHERE cfzc_id=?  ";
		Object[] objs = { cfzm.getCfzc_name(), cfzm.getCfzc_tel(),
				cfzm.getCfzc_mobile(), cfzm.getCfzc_mail(), chfz_id,
				cfzm.getCfzc_id() };
		String sql1 = " UPDATE CoHousingFundPwd SET chfp_zb_name = ? WHERE chfp_cohf_id = ? and chfp_chfz_id = ? ";
		Object[] objs1 = { cfzm.getCfzc_name(), cfzm.getCfzc_cohf_id(), chfz_id };
		try {

			row = updatePrepareSQL(sql, objs);
			updatePrepareSQL(sql1, objs1);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		System.out.println(row);
		return row;
	}

	// 修改专办员信息change表
	public int addZbc(CoHousingFundZbModel cfzm, int state) {
		int row = 0;
		try {
			String sql = "UPDATE CoHousingFundZbChange SET cfzc_flag=1,cfzc_name=?,cfzc_number=?,cfzc_tel=?,cfzc_mobile=?,cfzc_mail=?,cfzc_remark=?,cfzc_state=? WHERE cfzc_id=?";
			Object[] objs = { cfzm.getChfz_name(), cfzm.getChfz_number(),
					cfzm.getChfz_tel(), cfzm.getChfz_mobile(),
					cfzm.getChfz_mail(), cfzm.getRemark(), state,
					cfzm.getCfzc_id() };
			row = updatePrepareSQL(sql, objs);
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return row;
	}

	// 修改专办员信息change表
	public int addZbChange(CoHousingFundZbChangeModel cfzm, int state) {
		int row = 0;
		try {
			String sql = "UPDATE CoHousingFundZbChange SET cfzc_name=?,cfzc_number=?,cfzc_tel=?,cfzc_mobile=?,cfzc_mail=?,cfzc_remark=?,cfzc_state=? WHERE cfzc_id=?";
			Object[] objs = { cfzm.getCfzc_name(), cfzm.getCfzc_number(),
					cfzm.getCfzc_tel(), cfzm.getCfzc_mobile(),
					cfzm.getCfzc_mail(), cfzm.getCfzc_remark(), state,
					cfzm.getCfzc_id() };
			row = updatePrepareSQL(sql, objs);
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return row;
	}

	public int addZb(CoHousingFundZbChangeModel cfzm) {
		int row = 0;
		try {
			String sql = " declare @i int INSERT CoHousingFundZb(ownmonth,chfz_cohf_id,chfz_name,chfz_number,chfz_tel,chfz_mobile,chfz_mail,chfz_addname,chfz_state,chfz_completetime,chfz_addtime) VALUES(?,?,?,?,?,?,?,?,?,getDate(),getDate()) set @i=@@IDENTITY  UPDATE CoHousingFundZbChange SET cfzc_chfz_id=@i,cfzc_completetime=getDate() WHERE cfzc_id=?";
			Object[] objs = { cfzm.getOwnmonth(), cfzm.getCfzc_cohf_id(),
					cfzm.getCfzc_name(), cfzm.getCfzc_number(),
					cfzm.getCfzc_tel(), cfzm.getCfzc_mobile(),
					cfzm.getCfzc_mail(), cfzm.getCfzc_addname(),
					cfzm.getCfzc_state(), cfzm.getCfzc_id() };
			row = updatePrepareSQL(sql, objs);
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return row;
	}

	public List<CoHousingFundModel> getCoHousingFundListbyCondition(String str) {
		List<CoHousingFundModel> list = new ArrayList<CoHousingFundModel>();
		String sql = " SELECT cohf_id,cid,cohf_company,ownmonth,cohf_firmonth,cohf_tsday,cohf_houseid,CONVERT(varchar(100),cohf_addtime,120) as addtime,cohf_client,cohf_state,cohf_ispwd,CONVERT(varchar(100),cohf_completetime,120) as ctime "
				+ " FROM CoHousingFund  where cid > 0 and  " + str;
		try {
			ResultSet rs = GRS(sql);
			while (rs.next()) {
				CoHousingFundModel chfm = new CoHousingFundModel();
				chfm.setCohf_id(rs.getInt("cohf_id"));
				chfm.setCid(rs.getInt("cid"));
				chfm.setCoba_company(rs.getString("cohf_company"));
				chfm.setOwnmonth(rs.getInt("ownmonth"));
				chfm.setCohf_firmonth(rs.getInt("cohf_firmonth"));
				if (rs.getString("cohf_tsday")!=null) {
					chfm.setCohf_tsday(rs.getInt("cohf_tsday"));
				}
				chfm.setCohf_houseid(rs.getString("cohf_houseid"));
				if (rs.getString("addtime") != null) {
					chfm.setCohf_addtimeString(rs.getString("addtime"));
				}
				chfm.setCohf_client(rs.getString("cohf_client"));
				chfm.setCohf_state(rs.getInt("cohf_state"));
				chfm.setIspwd(rs.getString("cohf_ispwd"));
				if (rs.getString("ctime") != null) {
					chfm.setCohf_completetimeString(rs.getString("ctime"));
				}
				list.add(chfm);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}

	public List<CoHousingFundModel> getCoHousingFundList() {
		List<CoHousingFundModel> list = new ArrayList<CoHousingFundModel>();
		String sql = " SELECT cohf_id,cobase.cid,cohf_company,ownmonth,cohf_firmonth,cohf_tsday,cohf_houseid,CONVERT(varchar(100),cohf_addtime,120) as addtime," +
				"coba_client,cohf_state,CONVERT(varchar(100),cohf_completetime,120) as ctime " +
				" FROM CoHousingFund  " +
				" left join cobase on CoHousingFund.cid=cobase.cid" 
				+" WHERE CoHousingFund.cid > 0 "				
				+ " and  CoHousingFund.CID in ( select cid from DataPopedom where log_id="
				+ UserInfo.getUserid()
				+ " and dat_selected=1 )"
				+ "  order by case coba_client when '"
				+UserInfo.getUsername()
				+"' then 0 else  1 end,coba_client,cohf_company ";
		try {
			ResultSet rs = GRS(sql);
			while (rs.next()) {
				CoHousingFundModel chfm = new CoHousingFundModel();
				chfm.setCohf_id(rs.getInt("cohf_id"));
				chfm.setCid(rs.getInt("cid"));
				chfm.setCoba_company(rs.getString("cohf_company"));
				chfm.setOwnmonth(rs.getInt("ownmonth"));
				chfm.setCohf_firmonth(rs.getInt("cohf_firmonth"));
				if (rs.getString("cohf_tsday")!=null) {
					chfm.setCohf_tsday(rs.getInt("cohf_tsday"));
				}
				chfm.setCohf_houseid(rs.getString("cohf_houseid"));
				if (rs.getString("addtime") != null) {
					chfm.setCohf_addtimeString(rs.getString("addtime"));
				}
				chfm.setCohf_client(rs.getString("coba_client"));
				chfm.setCohf_state(rs.getInt("cohf_state"));
				if (rs.getString("ctime") != null) {
					chfm.setCohf_completetimeString(rs.getString("ctime"));
				}
				list.add(chfm);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		return list;
	}

	public List<CoHousingFundZbChangeModel> getChfzListChangeByCondition(
			String condition) {

		List<CoHousingFundZbChangeModel> list = new ArrayList<CoHousingFundZbChangeModel>();
		String sql = " SELECT cfzc_chfz_id,cfzc_cohf_id,cfzc_id,cid,cohf_company,cohf_houseid,cfzc_addtype,cfzc_remark,CONVERT(varchar(100),cfzc_addtime,120)as addtime,CONVERT(varchar(100),cfzc_completetime,120) as ctime,cfzc_addname,cfzc_state,isnull(msg_a.msg_a,-1) as msg_a,cfzc_flag,cfzc_backreason FROM CoHousingFundZbChange a"
				+ " left join (SELECT * FROM CoHousingFund where cid > 0) b "
				+ " on  a.cfzc_cohf_id=b.cohf_id "
				+ " left join (select smwr_tid,case when syme_log_id= "
				+ UserInfo.getUserid()
				+ " then 2 when symr_log_id=  "
				+ UserInfo.getUserid()
				+ " then symr_readstate end msg_a from View_Message where EXISTS(select syme_id from (select smwr_tid,MAX(syme_id)syme_id from View_Message where syme_state=1 and (symr_log_id="
				+ UserInfo.getUserid()
				+ " or syme_log_id="
				+ UserInfo.getUserid()
				+ " ) and  smwr_table='CoHousingFundZbChange' group by smwr_tid)msg where View_Message.syme_id=msg.syme_id))msg_a on a.cfzc_id=msg_a.smwr_tid where  "
				+ condition
				+ " and cfzc_del = 0 order by "  
				+ "  order by case cfzc_addname when '"
				+UserInfo.getUsername()
				+"' then 0 else  1 end ,cfzc_state asc ,addtime DESC";
		try {
			ResultSet rs = GRS(sql);
			while (rs.next()) {
				CoHousingFundZbChangeModel cfzc = new CoHousingFundZbChangeModel();
				cfzc.setCfzc_chfz_id(rs.getInt("cfzc_chfz_id"));
				cfzc.setCfzc_cohf_id(rs.getInt("cfzc_cohf_id"));
				cfzc.setCfzc_id(rs.getInt("cfzc_id"));
				cfzc.setCid(rs.getString("cid"));
				cfzc.setCfzc_cohf_company(rs.getString("cohf_company"));
				cfzc.setCfzc_cohf_houseid(rs.getString("cohf_houseid"));
				cfzc.setCfzc_addtype(rs.getString("cfzc_addtype"));
				if (rs.getString("addtime") != null) {
					cfzc.setCfzc_addtimeString(rs.getString("addtime"));
				}
				if (rs.getString("ctime") != null) {
					cfzc.setCfzc_completetime(rs.getString("ctime"));
				}
				cfzc.setCfzc_addname(rs.getString("cfzc_addname"));
				cfzc.setCfzc_remark(rs.getString("cfzc_remark"));
				cfzc.setCfzc_state(rs.getInt("cfzc_state"));
				cfzc.setMsg_a(rs.getInt("msg_a"));
				cfzc.setFlag(rs.getInt("cfzc_flag"));
				cfzc.setBackReason(rs.getString("cfzc_backreason"));
				list.add(cfzc);
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}

	public List<CoHousingFundZbChangeModel> getChfzListChange() {
		List<CoHousingFundZbChangeModel> list = new ArrayList<CoHousingFundZbChangeModel>();
		String sql = "SELECT a.cfzc_number,coba_client,b.cid,a.cfzc_backreason,a.cfzc_name,a.cfzc_number,a.cfzc_tel,a.cfzc_mail,a.cfzc_mobile,a.cfzc_remark,a.ownmonth,cfzc_chfz_id,cfzc_cohf_id,cfzc_id,b.cid,cohf_company,cohf_houseid,cfzc_addtype,CONVERT(varchar(100),cfzc_addtime,120) as addtime,CONVERT(varchar(100),cfzc_completetime,120) as ctime,cfzc_addname,cfzc_state,cfzc_remark,isnull(msg_a.msg_a,-1)msg_a,cfzc_flag,cfzc_backreason FROM CoHousingFundZbChange a "
				+ " left join (SELECT * FROM CoHousingFund where cid > 0) b"
				+ " on a.cfzc_cohf_id=b.cohf_id "
				+"left join cobase cb on b.cid=cb.cid "
				+ " left join (select smwr_tid,case when syme_log_id= "
				+ UserInfo.getUserid()
				+ " then 2 when symr_log_id=  "
				+ UserInfo.getUserid()
				+ " then symr_readstate end msg_a from View_Message where EXISTS(select syme_id from (select smwr_tid,MAX(syme_id)syme_id from View_Message where syme_state=1 and (symr_log_id="
				+ UserInfo.getUserid()
				+ " or syme_log_id="
				+ UserInfo.getUserid()
				+ " ) and  smwr_table='CoHousingFundZbChange' group by smwr_tid)msg where View_Message.syme_id=msg.syme_id))msg_a on a.cfzc_id=msg_a.smwr_tid "
				+ " and b.CID in ( select cid from DataPopedom where log_id="
				+ UserInfo.getUserid()
				+ " and dat_selected=1 )"
				+ " where cfzc_del = 0 "  
				+ "  order by case coba_client when '"
				+ UserInfo.getUsername()
				+"' then 0 else  1 end , cfzc_state asc ,addtime desc";
		try {
			ResultSet rs = GRS(sql);
			while (rs.next()) {
				CoHousingFundZbChangeModel cfzc = new CoHousingFundZbChangeModel();
				cfzc.setCfzc_chfz_id(rs.getInt("cfzc_chfz_id"));
				cfzc.setCfzc_cohf_id(rs.getInt("cfzc_cohf_id"));
				cfzc.setCfzc_id(rs.getInt("cfzc_id"));
				cfzc.setCid(rs.getString("cid"));
				cfzc.setCfzc_cohf_company(rs.getString("cohf_company"));
				cfzc.setCfzc_cohf_houseid(rs.getString("cohf_houseid"));
				cfzc.setCfzc_addtype(rs.getString("cfzc_addtype"));
				if (rs.getString("addtime") != null) {
					cfzc.setCfzc_addtimeString(rs.getString("addtime"));
				}
				if (rs.getString("ctime") != null) {
					cfzc.setCfzc_completetime(rs.getString("ctime"));
				}
				cfzc.setCfzc_addname(rs.getString("cfzc_addname"));
				cfzc.setCfzc_state(rs.getInt("cfzc_state"));
				cfzc.setCfzc_remark(rs.getString("cfzc_remark"));
				cfzc.setMsg_a(rs.getInt("msg_a"));
				cfzc.setFlag(rs.getInt("cfzc_flag"));
				cfzc.setBackReason(rs.getString("cfzc_backreason"));
				cfzc.setOwnmonth(rs.getInt("ownmonth"));
				cfzc.setCfzc_name(rs.getString("cfzc_name"));
				cfzc.setCfzc_tel(rs.getString("cfzc_tel"));
				cfzc.setCfzc_mail(rs.getString("cfzc_mail"));
				cfzc.setCfzc_mobile(rs.getString("cfzc_mobile"));
				cfzc.setCfzc_remark(rs.getString("cfzc_remark"));
				cfzc.setBackReason(rs.getString("cfzc_backreason"));
				cfzc.setCfzc_number(rs.getString("cfzc_number"));
				list.add(cfzc);
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}

	public List<CoHousingFundModel> getChfzListByCondition(String str) {
		List<CoHousingFundModel> list = new ArrayList<CoHousingFundModel>();
		ResultSet rs = null;

		String sql = " SELECT cohf_id,cid,cohf_company,a.ownmonth,cohf_firmonth,cohf_tsday,cohf_houseid,CONVERT(varchar(100),cohf_addtime,120) as addtime,cohf_client,cohf_state,cohf_ispwd,cfzc_state,CONVERT(varchar(100),cohf_completetime,120)as ctime  FROM CoHousingFund a "
				+ " left join (select * from CoHousingFundZbChange where cfzc_state=4 and cfzc_del = 0) b "
				+ " on a.cohf_id=b.cfzc_cohf_id  where cid > 0 and " + str;
		try {
			rs = GRS(sql);
			while (rs.next()) {
				CoHousingFundModel chfm = new CoHousingFundModel();
				chfm.setCohf_id(rs.getInt("cohf_id"));
				chfm.setCid(rs.getInt("cid"));
				chfm.setCoba_company(rs.getString("cohf_company"));
				chfm.setOwnmonth(rs.getInt("ownmonth"));
				chfm.setCohf_firmonth(rs.getInt("cohf_firmonth"));
				if (rs.getString("cohf_tsday")!=null) {
					chfm.setCohf_tsday(rs.getInt("cohf_tsday"));
				}
				chfm.setCohf_houseid(rs.getString("cohf_houseid"));
				if (rs.getString("addtime") != null) {
					chfm.setCohf_addtimeString(rs.getString("addtime"));
				}
				chfm.setCohf_client(rs.getString("cohf_client"));
				chfm.setCohf_state(rs.getInt("cohf_state"));
				chfm.setIspwd(rs.getString("cohf_ispwd"));
				if (rs.getString("ctime") != null) {
					chfm.setCohf_completetimeString(rs.getString("ctime"));
				}

				list.add(chfm);
			}

		} catch (SQLException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}

	public CoHousingFundModel getCoHousingFundListById(int cid) {
		CoHousingFundModel chfm = null;
		try {
			String sql = " SELECT * FROM CoHousingFund WHERE cid = ? ";
			dbconn db = new dbconn();
			List<CoHousingFundModel> list = new ListModelList<>();
			list= db.find(sql, CoHousingFundModel.class, null, cid);
			if (list.size()>0) {
				chfm=list.get(0);
				chfm.setCoba_company(chfm.getCohf_company());
			}
			/*
			PreparedStatement pstmt = getpre(sql);
			pstmt.setInt(1, cid);
			ResultSet rs = pstmt.executeQuery();
			while (rs.next()) {
				chfm = new CoHousingFundModel();
				chfm.setCohf_id(rs.getInt("cohf_id"));
				chfm.setCid(rs.getInt("cid"));
				chfm.setCoba_company(rs.getString("cohf_company"));
				chfm.setOwnmonth(rs.getInt("ownmonth"));
				chfm.setCohf_firmonth(rs.getInt("cohf_firmonth"));
				chfm.setCohf_tsday(rs.getInt("cohf_tsday"));
				chfm.setCohf_houseid(rs.getString("cohf_houseid"));
				chfm.setCohf_addtime(rs.getDate("cohf_addtime"));
				chfm.setCohf_client(rs.getString("cohf_client"));
				chfm.setCohf_state(rs.getInt("cohf_state"));
				chfm.setCohf_addtype(rs.getString("cohf_addtype"));
				chfm.setCohf_addname(rs.getString("cohf_addname"));
				chfm.setCohf_area(rs.getString("cohf_area"));
				chfm.setCohf_attached(rs.getString("cohf_attached"));
				chfm.setCohf_bankgj(rs.getString("cohf_bankgj"));
				chfm.setCohf_bankjc(rs.getString("cohf_bankjc"));
				chfm.setCohf_bankjcid(rs.getString("cohf_bankjcid"));
				chfm.setCohf_banklk(rs.getString("cohf_bankgj"));
				chfm.setCohf_bankts(rs.getString("cohf_banklk"));
				chfm.setCohf_banktsacc(rs.getString("cohf_banktsacc"));
				chfm.setCohf_banktsid(rs.getString("cohf_banktsid"));
				chfm.setCohf_lastday(rs.getInt("cohf_lastday"));
				chfm.setCohf_sorid(rs.getString("cohf_sorid"));
				chfm.setCohf_stop_type(rs.getString("cohf_stop_type"));
				chfm.setCohf_comid(rs.getString("cohf_comid"));
				chfm.setCohf_zgtype(rs.getString("cohf_zgtype"));
				chfm.setCohf_pastal(rs.getString("cohf_pastal"));
				chfm.setCohf_nature(rs.getString("cohf_nature"));
				chfm.setCohf_ecoclass(rs.getString("cohf_ecoclass"));
				chfm.setCohf_industry(rs.getString("cohf_industry"));
				chfm.setCohf_attached(rs.getString("cohf_attached"));
				chfm.setCohf_corname(rs.getString("cohf_corname"));
				chfm.setCohf_coridtype(rs.getString("cohf_coridtype"));
				chfm.setCohf_coridcard(rs.getString("cohf_coridcard"));
				chfm.setCohf_cortel(rs.getString("cohf_cortel"));
				chfm.setCohf_department(rs.getString("cohf_department"));
				chfm.setCohf_departmenttel(rs.getString("cohf_departmenttel"));
				chfm.setCohf_createtime(rs.getString("cohf_createtime"));
				chfm.setCohf_regid(rs.getString("cohf_regid"));
				chfm.setCohf_taxpayerid(rs.getString("cohf_taxpayerid"));
				chfm.setCohf_jbdepartment(rs.getString("cohf_jbdepartment"));
				chfm.setCohf_contactname(rs.getString("cohf_contactname"));
				chfm.setCohf_contacttel(rs.getString("cohf_contacttel"));
				chfm.setCohf_contactmail(rs.getString("cohf_contactmail"));
				chfm.setCohf_contactmobile(rs.getString("cohf_contactmobile"));
				chfm.setCohf_ispwd(rs.getInt("cohf_ispwd"));
				chfm.setCohf_single(rs.getInt("cohf_single"));
			}*/
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return chfm;
	}

	public List<CoHousingFundZbModel> getZbListById(int cohf_id) {
		List<CoHousingFundZbModel> list = new ArrayList<CoHousingFundZbModel>();
		String sql = " SELECT * FROM CoHousingFundZb WHERE chfz_cohf_id = ? and chfz_state = 1 ";
		try {
			PreparedStatement pstmt = getpre(sql);
			pstmt.setInt(1, cohf_id);
			ResultSet rs = pstmt.executeQuery();
			while (rs.next()) {
				CoHousingFundZbModel cfzm = new CoHousingFundZbModel();
				cfzm.setChfz_id(rs.getInt("chfz_id"));
				cfzm.setChfz_chfc_id(rs.getInt("chfz_cohf_id"));
				cfzm.setChfz_cohf_id(rs.getInt("chfz_chfc_id"));
				cfzm.setOwnmonth(rs.getInt("ownmonth"));
				cfzm.setChfz_name(rs.getString("chfz_name"));
				cfzm.setChfz_number(rs.getString("chfz_number"));
				cfzm.setChfz_tel(rs.getString("chfz_tel"));
				cfzm.setChfz_mobile(rs.getString("chfz_mobile"));
				cfzm.setChfz_mail(rs.getString("chfz_mail"));
				cfzm.setChfz_addname(rs.getString("chfz_addname"));
				cfzm.setChfz_addtime(rs.getDate("chfz_addtime"));
				cfzm.setChfz_state(rs.getInt("chfz_state"));
				cfzm.setChfz_completetime(rs.getDate("chfz_completetime"));
				cfzm.setChfz_stop_reason(rs.getString("chfz_stop_reason"));
				cfzm.setChfz_stop_time(rs.getDate("chfz_stop_time"));
				list.add(cfzm);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return list;
	}

	public List<CoHousingFundPwdModel> getPwdListById(int cohf_id) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
		List<CoHousingFundPwdModel> list = new ArrayList<CoHousingFundPwdModel>();
		String sql = " SELECT * FROM CoHousingFundPwd a,CoHousingFund b WHERE a.chfp_cohf_id = ? and a.chfp_cohf_id=b.cohf_id";

		try {
			PreparedStatement pstmt = getpre(sql);
			pstmt.setInt(1, cohf_id);
			ResultSet rs = pstmt.executeQuery();

			while (rs.next()) {
				CoHousingFundPwdModel cfpm = new CoHousingFundPwdModel();
				cfpm.setChfp_chfz_id(rs.getInt("chfp_chfz_id"));
				cfpm.setChfp_id(rs.getInt("chfp_id"));
				cfpm.setChfp_chfc_id(rs.getInt("chfp_chfc_id"));
				cfpm.setChfp_cohf_id(rs.getInt("chfp_cohf_id"));
				cfpm.setOwnmonth(rs.getString("ownmonth"));
				cfpm.setChfp_zb_name(rs.getString("chfp_zb_name"));
				cfpm.setChfp_zb_number(rs.getString("chfp_zb_number"));
				cfpm.setChfp_yearlimit(rs.getInt("chfp_yearlimit"));
				cfpm.setChfp_startdate(rs.getDate("chfp_startdate"));
				cfpm.setChfp_enddate(rs.getDate("chfp_enddate"));
				cfpm.setChfp_addname(rs.getString("chfp_addname"));
				cfpm.setChfp_addtime(rs.getDate("chfp_addtime"));
				cfpm.setChfp_state(rs.getInt("chfp_state"));
				if (rs.getDate("chfp_completetime") != null) {
					cfpm.setChfp_completetime(sdf.format(rs
							.getDate("chfp_completetime")));
				}
				list.add(cfpm);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return list;
	}

	public int isHaveZb(int cohf_id) {
		int row = 0;
		try {
			String sql = " SELECT COUNT(*) FROM CoHousingFund a,CoHousingFundZb b WHERE a.cohf_id = b.chfz_cohf_id and cohf_id = ? and chfz_state = 1";
			PreparedStatement pstmt = getpre(sql);
			pstmt.setInt(1, cohf_id);
			ResultSet rs = pstmt.executeQuery();
			while (rs.next()) {
				row = rs.getInt(1);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return row;
	}

	public int resubmitZb(CoHousingFundZbChangeModel cfzc) {
		int row = 0;
		String sql = " UPDATE CoHousingFundZbChange SET cfzc_state=?,cfzc_remark=? WHERE cfzc_id = ?";
		try {
			Object[] objs = { cfzc.getCfzc_state(), cfzc.getCfzc_remark(),
					cfzc.getCfzc_id() };
			row = updatePrepareSQL(sql, objs);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return row;
	}

	public int addZbChange(CoHousingFundZbChangeModel cfzc) {
		CallableStatement csmt = getcall("CoHousingFundZbAdd_P_gxj(?,?,?,?,?,?,?,?,?)");
		try {
			csmt.setInt(1, cfzc.getCfzc_cohf_id());
			csmt.setString(2, cfzc.getCfzc_addname());
			csmt.setString(3, cfzc.getCfzc_addtype());
			csmt.setInt(4, cfzc.getCfzc_state());
			csmt.setString(5, cfzc.getCfzc_remark());
			csmt.setInt(6, cfzc.getCfzc_chfz_id());
			csmt.setInt(7, cfzc.getOwnmonth());
			csmt.setInt(8, cfzc.getCfzc_cfpc_id());
			csmt.registerOutParameter(9, java.sql.Types.INTEGER);
			csmt.execute();
			return csmt.getInt(9);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return 0;

	}

	public List<CoHousingFundZbModel> getNotPwdZb(int cohf_id) {

		List<CoHousingFundZbModel> list = new ArrayList<CoHousingFundZbModel>();
		String sql = " SELECT * FROM CoHousingFundZb where chfz_id not in (SELECT chfp_chfz_id FROM CoHousingFundPwd where chfp_state = 1 and chfp_cohf_id=?) and chfz_cohf_id= ? and chfz_state = 1 ";
		PreparedStatement pstmt = getpre(sql);
		try {
			pstmt.setInt(1, cohf_id);
			ResultSet rs = pstmt.executeQuery();
			while (rs.next()) {
				CoHousingFundZbModel cfzm = new CoHousingFundZbModel();
				cfzm.setChfz_id(rs.getInt("chfz_id"));
				cfzm.setChfz_name(rs.getString("chfz_name"));
				cfzm.setChfz_number(rs.getString("chfz_number"));
				cfzm.setChfz_tel(rs.getString("chfz_tel"));
				cfzm.setChfz_mobile(rs.getString("chfz_mobile"));
				cfzm.setChfz_mail(rs.getString("chfz_mail"));
				cfzm.setChfz_addname(rs.getString("chfz_addname"));
				cfzm.setChfz_addtime(rs.getDate("chfz_addtime"));
				cfzm.setChfz_state(rs.getInt("chfz_state"));
				cfzm.setChfz_completetimeString(rs
						.getString("chfz_completetime"));
				cfzm.setChfz_stop_reason(rs.getString("chfz_stop_reason"));
				cfzm.setChfz_stop_time(rs.getDate("chfz_stop_time"));
				list.add(cfzm);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return list;
	}

	public List<CoHousingFundZbModel> getZbListBycohf_id(int cohf_id) {
		List<CoHousingFundZbModel> list = new ArrayList<CoHousingFundZbModel>();
		String sql = " SELECT chfz_id,chfz_name,chfz_number,chfz_tel,chfz_mobile,chfz_mail,chfz_addname,chfz_addtime,chfz_state,chfz_completetime,chfz_stop_reason,chfz_stop_time FROM CoHousingFundZb WHERE chfz_cohf_id=? and chfz_state = 1 ";
		PreparedStatement pstmt = getpre(sql);
		try {
			pstmt.setInt(1, cohf_id);
			ResultSet rs = pstmt.executeQuery();
			while (rs.next()) {
				CoHousingFundZbModel cfzm = new CoHousingFundZbModel();
				cfzm.setChfz_id(rs.getInt("chfz_id"));
				cfzm.setChfz_name(rs.getString("chfz_name"));
				cfzm.setChfz_number(rs.getString("chfz_number"));
				cfzm.setChfz_tel(rs.getString("chfz_tel"));
				cfzm.setChfz_mobile(rs.getString("chfz_mobile"));
				cfzm.setChfz_mail(rs.getString("chfz_mail"));
				cfzm.setChfz_addname(rs.getString("chfz_addname"));
				cfzm.setChfz_addtime(rs.getDate("chfz_addtime"));
				cfzm.setChfz_state(rs.getInt("chfz_state"));
				cfzm.setChfz_completetimeString(rs
						.getString("chfz_completetime"));
				cfzm.setChfz_stop_reason(rs.getString("chfz_stop_reason"));
				cfzm.setChfz_stop_time(rs.getDate("chfz_stop_time"));
				list.add(cfzm);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return list;
	}

	public int changeStatus(CoHousingFundZbChangeModel cfzc) {
		int row = 0;
		try {
			String sql = " UPDATE CoHousingFundZbChange SET cfzc_state = ?,cfzc_backreason=? WHERE cfzc_id = ?  ";
			Object[] objs = { cfzc.getCfzc_state(), cfzc.getBackReason(),
					cfzc.getCfzc_id() };
			row = updatePrepareSQL(sql, objs);

		} catch (SQLException e) {
			e.printStackTrace();
		}
		return row;
	}

	public CoHousingFundZbChangeModel addtype(int cfzc_id) {
		CoHousingFundZbChangeModel cfzc = new CoHousingFundZbChangeModel();
		String sql = " SELECT cfzc_state,cfzc_chfz_id,cfzc_name,cfzc_number,cfzc_addtype,cfzc_remark,cfzc_tel,cfzc_mobile,cfzc_mail,cfzc_addname FROM CoHousingFundZbChange WHERE cfzc_id = ? and cfzc_del = 0 ";
		PreparedStatement pstmt = getpre(sql);
		try {
			pstmt.setInt(1, cfzc_id);
			ResultSet rs = pstmt.executeQuery();
			while (rs.next()) {
				cfzc.setCfzc_state(rs.getInt("cfzc_state"));
				cfzc.setCfzc_chfz_id(rs.getInt("cfzc_chfz_id"));
				cfzc.setCfzc_name(rs.getString("cfzc_name"));
				cfzc.setCfzc_number(rs.getString("cfzc_number"));
				cfzc.setCfzc_addtype(rs.getString("cfzc_addtype"));
				cfzc.setCfzc_remark(rs.getString("cfzc_remark"));
				cfzc.setCfzc_tel(rs.getString("cfzc_tel"));
				cfzc.setCfzc_mobile(rs.getString("cfzc_mobile"));
				cfzc.setCfzc_mail(rs.getString("cfzc_mail"));
				cfzc.setCfzc_addname(rs.getString("cfzc_addname"));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return cfzc;
	}

	public CoHousingFundZbModel getZbChangeInfo(int chfz_id) {
		CoHousingFundZbModel cfzm = new CoHousingFundZbModel();
		ResultSet rs = null;

		String sql = " SELECT * FROM CoHousingFundZb WHERE chfz_id = ? ";
		PreparedStatement pstmt = getpre(sql);
		try {
			pstmt.setInt(1, chfz_id);
			rs = pstmt.executeQuery();
			while (rs.next()) {
				cfzm.setChfz_name(rs.getString("chfz_name"));
				cfzm.setChfz_number(rs.getString("chfz_number"));
				cfzm.setChfz_tel(rs.getString("chfz_tel"));
				cfzm.setChfz_mobile(rs.getString("chfz_mobile"));
				cfzm.setChfz_mail(rs.getString("chfz_mail"));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return cfzm;
	}

	/**
	 * 专办员业务完成
	 * 
	 * @param r
	 * @return
	 */
	public int ZbCompelete(String r, int chfz_id) {

		int row = 0;
		String sql = " UPDATE CoHousingFundZb SET chfz_completetime = ? WHERE  chfz_id = ?";
		Object[] objs = { new java.sql.Date(System.currentTimeMillis()),
				chfz_id };
		try {
			row = updatePrepareSQL(sql, objs);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return row;
	}

	/**
	 * 该单位的该专办员是否存在密钥
	 * 
	 * @param cohf_id
	 * @param chfz_id
	 * @return
	 */
	public int isHavePwd(int cohf_id, int chfz_id) {
		int count = 0;
		String sql = "  SELECT COUNT(*) FROM CoHousingFundPwd WHERE chfp_cohf_id=? and chfp_chfz_id= ? and chfp_state = 1 ";
		PreparedStatement pstmt = getpre(sql);
		try {
			pstmt.setInt(1, cohf_id);
			pstmt.setInt(2, chfz_id);
			ResultSet rs = pstmt.executeQuery();
			while (rs.next()) {
				count = rs.getInt(1);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return count;
	}

}
