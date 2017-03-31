package dal.EmSheBao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import Conn.dbconn;
import Model.CoCompactModel;
import Model.EmSheBaoChangeModel;
import Model.EmShebaoBJModel;
import Model.EmShebaoChangeForeignerModel;
import Model.EmShebaoChangeMAModel;
import Model.EmShebaoSetupModel;
import Model.EmShebaoUpdateModel;
import Util.UserInfo;

public class Emsc_DeclareSelDal {
	private dbconn conn = new dbconn();

	// 获取社保独立开户的公司数据的数据列表
	public List<CoCompactModel> getSCompany() {
		List<CoCompactModel> list = new ArrayList<CoCompactModel>();
		CoCompactModel m = null;
		String sql = "SELECT distinct b.cid,coba_company,coba_shortSpell,coco_shebaoid,coba_shortname FROM CoCompact a INNER JOIN CoBase b ON a.cid=b.cid WHERE a.coco_shebao='独立开户' ORDER BY b.coba_shortspell";
		try {
			ResultSet rs = conn.GRS(sql);
			while (rs.next()) {
				m = new CoCompactModel();
				m.setCid(rs.getString("cid"));
				m.setCompany(rs.getString("coba_company"));
				m.setCoba_shortspell(rs.getString("coba_shortSpell"));
				m.setCoco_shebaoID(rs.getString("coco_shebaoid"));
				m.setCoba_shortname(rs.getString("coba_shortname"));
				list.add(m);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}

	// 获取补缴的所有所属月份
	public List<String> getBjOwnmonthList() {
		List<String> list = new ArrayList<String>();
		String sql = "select distinct Ownmonth from EmShebaoBJ order by Ownmonth desc";
		try {
			ResultSet rs = conn.GRS(sql);
			while (rs.next()) {
				list.add(rs.getString("Ownmonth"));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}

	// 获取社保变更的所有所属月份
	public List<String> getChangeOwnmonthList() {
		List<String> list = new ArrayList<String>();
		String sql = "select distinct Ownmonth from EmShebaoChange order by Ownmonth desc";
		try {
			ResultSet rs = conn.GRS(sql);
			while (rs.next()) {
				list.add(rs.getString("Ownmonth"));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}

	// 获取社保设置
	public EmShebaoSetupModel getSetup() {
		EmShebaoSetupModel m = new EmShebaoSetupModel();
		String sql = "select * from EmShebaoSetup";
		try {
			ResultSet rs = conn.GRS(sql);
			rs.next();
			m.setId(rs.getInt("id"));
			m.setLastday(rs.getInt("lastday"));
			m.setLastdayname(rs.getString("lastdayname"));
			m.setLastdaybj(rs.getInt("lastdaybj"));
			m.setLastdaynamebj(rs.getString("lastdaynamebj"));
			m.setOnair(rs.getInt("onair"));
			m.setOnairname(rs.getString("onairname"));
			m.setReason(rs.getString("reason"));
			m.setOnairbj(rs.getInt("onairbj"));
			m.setOnairnamebj(rs.getString("onairnamebj"));
			m.setReasonbj(rs.getString("reasonbj"));
			m.setCwday(rs.getInt("cwday"));
			m.setFallday(rs.getInt("fallday"));
			m.setAuditday(rs.getInt("auditday"));
			m.setAuditdayname(rs.getString("auditdayname"));
			m.setMalastday(rs.getInt("malastday"));
			m.setMalastdayname(rs.getString("malastdayname"));
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return m;
	}

	// 统计当月补缴数据
	public String[] getBjDataCount(int ownmonth) {
		String[] str = new String[5];
		String sql = "select bj=(select count(*) from EmShebaoBJ where ownmonth=?)+(select count(*) from EmShebaoBJJL where ownmonth=?),ifdeclare=(select count(*) from EmShebaoBJ where emsb_ifdeclare not in(0,4,5,7,8) and ownmonth=?)+(select count(*) from EmShebaoBJJL where esbj_ifdeclare not in(0,4,5,7,8) and ownmonth=?),single0=(select count(*) from EmShebaobj where emsb_single=0 and ownmonth=?)+(select count(*) from EmShebaoBJJL where esbj_single=0 and ownmonth=?),single2=(select count(*) from EmShebaobj where emsb_single=2 and ownmonth=?)+(select count(*) from EmShebaoBJJL where esbj_single=2 and ownmonth=?),single1=(select count(*) from EmShebaobj where emsb_single=1 and ownmonth=?)+(select count(*) from EmShebaoBJJL where esbj_single=1 and ownmonth=?)";
		PreparedStatement psmt = conn.getpre(sql);
		try {
			psmt.setInt(1, ownmonth);
			psmt.setInt(2, ownmonth);
			psmt.setInt(3, ownmonth);
			psmt.setInt(4, ownmonth);
			psmt.setInt(5, ownmonth);
			psmt.setInt(6, ownmonth);
			psmt.setInt(7, ownmonth);
			psmt.setInt(8, ownmonth);
			psmt.setInt(9, ownmonth);
			psmt.setInt(10, ownmonth);
			ResultSet rs = psmt.executeQuery();
			rs.next();
			str[0] = rs.getString("bj");
			str[1] = rs.getString("ifdeclare");
			str[2] = rs.getString("single0");
			str[3] = rs.getString("single2");
			str[4] = rs.getString("single1");
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return str;
	}

	// 根据搜索条件查询补缴数据
	public List<EmShebaoBJModel> getBjList(String where, int ownmonth) {
		List<EmShebaoBJModel> list = new ArrayList<EmShebaoBJModel>();
		EmShebaoBJModel m = null;
		StringBuilder sql = new StringBuilder();
		sql.append("select bj.*,co.coba_client,isnull(sbid.coco_shebaoID,0)coco_shebaoid,isnull(msg_a.msg_a,-1)msg_a,isnull(cf.cfCou,0)cfCou,isnull(ec.ecCou,0)ecCou");
		sql.append(" from (select ID,GID,CID,Ownmonth,emsb_name,emsb_company,emsb_idcard,emsb_single,emsb_FeeOwnmonth,emsb_Computerid,emsb_hj,emsb_m1,emsb_r1,emsb_m2,emsb_r2,emsb_m3,emsb_r3,"
				+ "emsb_Totalcp,emsb_Totalop,emsb_Ifdeclare,emsb_DeclareTime,emsb_DeclareName,emsb_addtime,emsb_addname,emsb_remark,emsb_flag,emsb_flagname,"
				+ "emsb_excelfile,emsb_printtime,emsb_dptime,emsb_Overdue,emsb_OverdueDate,emsb_startmonth,emsb_stopmonth,emsb_radix,emsb_tapr_id,emsb_reason,"
				+ "emsb_uploadfile,emsb_ConfirmTime,emsb_yltype=null,type='EmShebaoBJ' from EmShebaoBJ"
				+ "	union all select ID,GID,CID,Ownmonth,esbj_name,esbj_company,esbj_idcard,esbj_single,esbj_FeeOwnmonth,esbj_Computerid,esbj_hj,null,null,null,null,null,null,"
				+ "esbj_Totalcp,esbj_Totalop,esbj_Ifdeclare,esbj_DeclareTime,esbj_DeclareName,esbj_addtime,esbj_addname,esbj_remark,esbj_flag,esbj_flagname,esbj_excelfile,"
				+ "esbj_printtime,esbj_dptime,esbj_Overdue,esbj_OverdueDate,esbj_startmonth,esbj_stopmonth,esbj_radix,esbj_tapr_id,esbj_reason,esbj_uploadfile,esbj_ConfirmTime,"
				+ "esbj_yltype,type='EmShebaoBJJL' from EmShebaoBJJL)bj ");
		sql.append(" left join Cobase co on bj.cid=co.cid");
		sql.append(" left join (select c.gid,c.cid,d.coco_shebaoid from (select a.gid,a.cid,cf.coof_coco_id from CoGList a left join CoOfferList b on a.cgli_coli_id=b.coli_id left join CoOffer cf on b.coli_coof_id=cf.coof_id where b.coli_state=1 and b.coli_name='社会保险服务' group by a.gid,a.cid,cf.coof_coco_id)c inner join CoCompact d on c.coof_coco_id=d.coco_id where d.coco_shebaoid is not null)sbid on bj.GID=sbid.gid");
		sql.append(" left join (select smwr_tid,case when syme_log_id="
				+ UserInfo.getUserid()
				+ " then 2 when symr_log_id="
				+ UserInfo.getUserid()
				+ " then symr_readstate else 1 end msg_a from View_Message where EXISTS(select syme_id from (select smwr_tid,MAX(syme_id)syme_id from View_Message where syme_state=1 and  smwr_table='EmShebaoBJ' group by smwr_tid)msg where View_Message.syme_id=msg.syme_id))msg_a on bj.id=msg_a.smwr_tid and bj.type='EmShebaoBJ' ");
		sql.append(" left join (select gid,count(*) as cfCou from EmShebaoChangeForeigner where ownmonth="
				+ ownmonth
				+ " and (emsc_change='调入' or emsc_change='新增') and (emsc_ifdeclare=3 or emsc_ifdeclare=0 or emsc_ifdeclare=4) GROUP BY gid)cf on cf.GID=bj.GID");
		sql.append(" left join (select gid,count(*) as ecCou from EmShebaoChange where ownmonth="
				+ ownmonth
				+ " and (emsc_change='调入' or emsc_change='新增') and (emsc_ifdeclare=3 or emsc_ifdeclare=0 or emsc_ifdeclare=4) GROUP BY gid)ec on ec.gid = bj.GID");
		sql.append(" where (emsb_ifdeclare not in(4,2,7)) ");
		sql.append(where);
		sql.append(" order by bj.ownmonth,bj.gid,bj.emsb_startmonth desc");
		System.out.println(sql.toString());
		try {
			ResultSet rs = conn.GRS(sql.toString());
			while (rs.next()) {
				m = new EmShebaoBJModel();
				m.setId(rs.getInt("id"));
				m.setGid(rs.getInt("gid"));
				m.setCid(rs.getInt("cid"));
				m.setEmsb_company(rs.getString("emsb_company"));
				m.setEmsb_name(rs.getString("emsb_name"));
				m.setOwnmonth(rs.getInt("ownmonth"));
				m.setEmsb_computerid(rs.getString("emsb_computerid"));
				m.setEmsb_idcard(rs.getString("emsb_idcard"));
				m.setEmsb_startmonth(rs.getInt("emsb_startmonth"));
				m.setEmsb_radix(rs.getInt("emsb_radix"));
				m.setEmsb_addname(rs.getString("emsb_addname"));
				m.setEmsb_addtime(rs.getString("emsb_addtime"));
				m.setEmsb_ifdeclare(rs.getInt("emsb_ifdeclare"));
				m.setEmsb_printtime(rs.getString("emsb_printtime"));
				m.setEmsb_declaretime(rs.getString("emsb_declaretime"));
				m.setEmsb_declarename(rs.getString("emsb_declarename"));
				m.setEmsb_remark(rs.getString("emsb_remark"));
				m.setEmsb_dptime(rs.getDate("emsb_dptime"));
				m.setEmsb_flag(rs.getString("emsb_flag"));
				m.setEmsb_single(rs.getInt("emsb_single"));
				m.setEmsb_tapr_id(rs.getInt("emsb_tapr_id"));
				m.setCoco_shebaoid(rs.getString("coco_shebaoid"));
				m.setMsg_a(rs.getInt("msg_a"));
				m.setCfCou(rs.getInt("cfCou"));
				m.setEcCou(rs.getInt("ecCou"));
				m.setCoba_client(rs.getString("coba_client"));
				m.setEmsb_confirmtime(rs.getString("emsb_confirmtime"));
				m.setType(rs.getString("type"));
				m.setEmsb_yltype(rs.getString("emsb_yltype"));
				m.setEmsb_totalcp(rs.getBigDecimal("emsb_totalcp"));
				m.setEmsb_totalop(rs.getBigDecimal("emsb_totalop"));
				list.add(m);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}

	// 获取社保补缴备注信息
	public EmShebaoBJModel getBjFlag(int id) {
		EmShebaoBJModel m = new EmShebaoBJModel();
		String sql = "select id,emsb_flag,emsb_flagname from EmShebaoBJ where id=?";
		PreparedStatement psmt = conn.getpre(sql);
		try {
			psmt.setInt(1, id);
			ResultSet rs = psmt.executeQuery();
			rs.next();
			m.setId(rs.getInt("id"));
			m.setEmsb_flag(rs.getString("emsb_flag"));
			m.setEmsb_flagname(rs.getString("emsb_flagname"));
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return m;
	}

	// 根据ID获取社保补缴信息
	public EmShebaoBJModel getBjInfoById(int id) {
		EmShebaoBJModel m = new EmShebaoBJModel();
		StringBuilder sql = new StringBuilder();
		sql.append("select bj.*,co.coba_company,");
		sql.append("cfCou=isnull((select count(*) as cfCou from EmShebaoChangeForeigner cf where ownmonth=bj.ownmonth and (emsc_change='调入' or emsc_change='新增') and (emsc_ifdeclare=3 or emsc_ifdeclare=0 or emsc_ifdeclare=4) and cf.GID=bj.GID),0),");
		sql.append("ecCou=isnull((select count(*) as ecCou from EmShebaoChange ec where ownmonth=bj.ownmonth and (emsc_change='调入' or emsc_change='新增') and (emsc_ifdeclare=3 or emsc_ifdeclare=0 or emsc_ifdeclare=4) and ec.gid = bj.GID),0) ");
		sql.append("from EmShebaoBJ bj left join Cobase co on bj.cid=co.cid ");
		sql.append("where bj.ID=?");
		PreparedStatement psmt = conn.getpre(sql.toString());
		try {
			psmt.setInt(1, id);
			ResultSet rs = psmt.executeQuery();
			if (rs.next()) {
				m.setId(rs.getInt("id"));
				m.setGid(rs.getInt("gid"));
				m.setCid(rs.getInt("cid"));
				m.setEmsb_company(rs.getString("coba_company"));
				m.setEmsb_name(rs.getString("emsb_name"));
				m.setOwnmonth(rs.getInt("ownmonth"));
				m.setEmsb_computerid(rs.getString("emsb_computerid"));
				m.setEmsb_idcard(rs.getString("emsb_idcard"));
				m.setEmsb_startmonth(rs.getInt("emsb_startmonth"));
				m.setEmsb_radix(rs.getInt("emsb_radix"));
				m.setEmsb_addname(rs.getString("emsb_addname"));
				m.setEmsb_addtime(rs.getString("emsb_addtime"));
				m.setEmsb_ifdeclare(rs.getInt("emsb_ifdeclare"));
				m.setEmsb_printtime(rs.getString("emsb_printtime"));
				m.setEmsb_declaretime(rs.getString("emsb_declaretime"));
				m.setEmsb_declarename(rs.getString("emsb_declarename"));
				m.setEmsb_remark(rs.getString("emsb_remark"));
				m.setEmsb_dptime(rs.getDate("emsb_dptime"));
				m.setEmsb_flag(rs.getString("emsb_flag"));
				m.setEmsb_single(rs.getInt("emsb_single"));
				m.setEmsb_reason(rs.getString("emsb_reason"));
				m.setEmsb_tapr_id(rs.getInt("emsb_tapr_id"));
				m.setCfCou(rs.getInt("cfCou"));
				m.setEcCou(rs.getInt("ecCou"));
				m.setEmsb_uploadfile(rs.getString("emsb_uploadfile"));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return m;
	}

	// 根据ID获取社保补缴信息
	public List<EmShebaoBJModel> getBjAllInfoById(int id, String str) {
		List<EmShebaoBJModel> list = new ArrayList<EmShebaoBJModel>();
		EmShebaoBJModel m;
		StringBuilder sql = new StringBuilder();
		sql.append("select bj.*,co.coba_company,");
		sql.append("cfCou=isnull((select count(*) as cfCou from EmShebaoChangeForeigner cf where ownmonth=bj.ownmonth and (emsc_change='调入' or emsc_change='新增') and (emsc_ifdeclare=3 or emsc_ifdeclare=0 or emsc_ifdeclare=4) and cf.GID=bj.GID),0),");
		sql.append("ecCou=isnull((select count(*) as ecCou from EmShebaoChange ec where ownmonth=bj.ownmonth and (emsc_change='调入' or emsc_change='新增') and (emsc_ifdeclare=3 or emsc_ifdeclare=0 or emsc_ifdeclare=4) and ec.gid = bj.GID),0) ");
		sql.append("from (select * from emshebaobj where id=?)bj left join emshebaobj b on bj.ownmonth=b.ownmonth and bj.gid=b.gid left join Cobase co on bj.cid=co.cid "
				+ str);
		PreparedStatement psmt = conn.getpre(sql.toString());
		// System.out.print(sql);
		try {
			psmt.setInt(1, id);
			ResultSet rs = psmt.executeQuery();
			if (rs.next()) {
				m = new EmShebaoBJModel();
				m.setId(rs.getInt("id"));
				m.setGid(rs.getInt("gid"));
				m.setCid(rs.getInt("cid"));
				m.setEmsb_company(rs.getString("coba_company"));
				m.setEmsb_name(rs.getString("emsb_name"));
				m.setOwnmonth(rs.getInt("ownmonth"));
				m.setEmsb_computerid(rs.getString("emsb_computerid"));
				m.setEmsb_idcard(rs.getString("emsb_idcard"));
				m.setEmsb_startmonth(rs.getInt("emsb_startmonth"));
				m.setEmsb_radix(rs.getInt("emsb_radix"));
				m.setEmsb_addname(rs.getString("emsb_addname"));
				m.setEmsb_addtime(rs.getString("emsb_addtime"));
				m.setEmsb_ifdeclare(rs.getInt("emsb_ifdeclare"));
				m.setEmsb_printtime(rs.getString("emsb_printtime"));
				m.setEmsb_declaretime(rs.getString("emsb_declaretime"));
				m.setEmsb_declarename(rs.getString("emsb_declarename"));
				m.setEmsb_remark(rs.getString("emsb_remark"));
				m.setEmsb_dptime(rs.getDate("emsb_dptime"));
				m.setEmsb_flag(rs.getString("emsb_flag"));
				m.setEmsb_single(rs.getInt("emsb_single"));
				m.setEmsb_reason(rs.getString("emsb_reason"));
				m.setEmsb_tapr_id(rs.getInt("emsb_tapr_id"));
				m.setCfCou(rs.getInt("cfCou"));
				m.setEcCou(rs.getInt("ecCou"));
				m.setEmsb_uploadfile(rs.getString("emsb_uploadfile"));
				list.add(m);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return list;
	}

	// 根据ID获取社保补缴信息(医疗)
	public EmShebaoBJModel getBjJLInfoById(int id) {
		EmShebaoBJModel m = new EmShebaoBJModel();
		StringBuilder sql = new StringBuilder();
		sql.append("select bj.*,co.coba_company,");
		sql.append("cfCou=isnull((select count(*) as cfCou from EmShebaoChangeForeigner cf where ownmonth=bj.ownmonth and (emsc_change='调入' or emsc_change='新增') and (emsc_ifdeclare=3 or emsc_ifdeclare=0 or emsc_ifdeclare=4) and cf.GID=bj.GID),0),");
		sql.append("ecCou=isnull((select count(*) as ecCou from EmShebaoChange ec where ownmonth=bj.ownmonth and (emsc_change='调入' or emsc_change='新增') and (emsc_ifdeclare=3 or emsc_ifdeclare=0 or emsc_ifdeclare=4) and ec.gid = bj.GID),0) ");
		sql.append("from EmShebaoBJJL bj left join Cobase co on bj.cid=co.cid ");
		sql.append("where bj.ID=?");
		PreparedStatement psmt = conn.getpre(sql.toString());
		try {
			psmt.setInt(1, id);
			ResultSet rs = psmt.executeQuery();
			if (rs.next()) {
				m.setId(rs.getInt("id"));
				m.setGid(rs.getInt("gid"));
				m.setCid(rs.getInt("cid"));
				m.setEmsb_company(rs.getString("coba_company"));
				m.setEmsb_name(rs.getString("esbj_name"));
				m.setOwnmonth(rs.getInt("ownmonth"));
				m.setEmsb_computerid(rs.getString("esbj_computerid"));
				m.setEmsb_idcard(rs.getString("esbj_idcard"));
				m.setEmsb_startmonth(rs.getInt("esbj_startmonth"));
				m.setEmsb_radix(rs.getInt("esbj_radix"));
				m.setEmsb_addname(rs.getString("esbj_addname"));
				m.setEmsb_addtime(rs.getString("esbj_addtime"));
				m.setEmsb_ifdeclare(rs.getInt("esbj_ifdeclare"));
				m.setEmsb_printtime(rs.getString("esbj_printtime"));
				m.setEmsb_declaretime(rs.getString("esbj_declaretime"));
				m.setEmsb_declarename(rs.getString("esbj_declarename"));
				m.setEmsb_remark(rs.getString("esbj_remark"));
				m.setEmsb_dptime(rs.getDate("esbj_dptime"));
				m.setEmsb_flag(rs.getString("esbj_flag"));
				m.setEmsb_single(rs.getInt("esbj_single"));
				m.setEmsb_reason(rs.getString("esbj_reason"));
				m.setEmsb_tapr_id(rs.getInt("esbj_tapr_id"));
				m.setCfCou(rs.getInt("cfCou"));
				m.setEcCou(rs.getInt("ecCou"));
				m.setEmsb_uploadfile(rs.getString("esbj_uploadfile"));
				m.setEmsb_yltype(rs.getString("esbj_yltype"));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return m;
	}

	// 根据查询条件获取社保补缴信息(养老)
	public List<EmShebaoBJModel> getBjInfoByStr(String str) {
		List<EmShebaoBJModel> list = new ArrayList<EmShebaoBJModel>();
		EmShebaoBJModel m = new EmShebaoBJModel();
		String sql = "select bj.*,co.coba_company from EmShebaoBJ bj inner join CoBase as co on bj.cid=co.cid where 1=1"
				+ str + " order by bj.ownmonth desc";
		PreparedStatement psmt = conn.getpre(sql);
		try {
			ResultSet rs = psmt.executeQuery();
			while (rs.next()) {
				m = new EmShebaoBJModel();
				m.setId(rs.getInt("id"));
				m.setGid(rs.getInt("gid"));
				m.setCid(rs.getInt("cid"));
				m.setEmsb_company(rs.getString("coba_company"));
				m.setEmsb_name(rs.getString("emsb_name"));
				m.setOwnmonth(rs.getInt("ownmonth"));
				m.setEmsb_computerid(rs.getString("emsb_computerid"));
				m.setEmsb_idcard(rs.getString("emsb_idcard"));
				m.setEmsb_startmonth(rs.getInt("emsb_startmonth"));
				m.setEmsb_radix(rs.getInt("emsb_radix"));
				m.setEmsb_addname(rs.getString("emsb_addname"));
				m.setEmsb_addtime(rs.getString("emsb_addtime"));
				m.setEmsb_ifdeclare(rs.getInt("emsb_ifdeclare"));
				m.setEmsb_printtime(rs.getString("emsb_printtime"));
				m.setEmsb_declaretime(rs.getString("emsb_declaretime"));
				m.setEmsb_declarename(rs.getString("emsb_declarename"));
				m.setEmsb_remark(rs.getString("emsb_remark"));
				m.setEmsb_dptime(rs.getDate("emsb_dptime"));
				m.setEmsb_flag(rs.getString("emsb_flag"));
				m.setEmsb_single(rs.getInt("emsb_single"));
				m.setEmsb_reason(rs.getString("emsb_reason"));
				m.setEmsb_tapr_id(rs.getInt("emsb_tapr_id"));
				m.setEmsb_feeownmonth(rs.getInt("emsb_feeownmonth"));
				m.setEmsb_totalcp(rs.getBigDecimal("emsb_totalcp"));
				m.setEmsb_totalop(rs.getBigDecimal("emsb_totalop"));
				m.setEmsb_total(rs.getBigDecimal("emsb_totalcp").add(
						rs.getBigDecimal("emsb_totalop")));
				list.add(m);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return list;
	}

	// 根据查询条件获取社保补缴信息(医疗)
	public List<EmShebaoBJModel> getBjJLInfoByStr(String str) {
		List<EmShebaoBJModel> list = new ArrayList<EmShebaoBJModel>();
		EmShebaoBJModel m = new EmShebaoBJModel();
		String sql = "select bj.*,co.coba_company from EmShebaoBJJL bj inner join CoBase as co on bj.cid=co.cid where 1=1"
				+ str + " order by bj.ownmonth desc";
		PreparedStatement psmt = conn.getpre(sql);
		try {
			ResultSet rs = psmt.executeQuery();
			while (rs.next()) {
				m = new EmShebaoBJModel();
				m.setId(rs.getInt("id"));
				m.setGid(rs.getInt("gid"));
				m.setCid(rs.getInt("cid"));
				m.setEmsb_company(rs.getString("coba_company"));
				m.setEmsb_name(rs.getString("esbj_name"));
				m.setOwnmonth(rs.getInt("ownmonth"));
				m.setEmsb_computerid(rs.getString("esbj_computerid"));
				m.setEmsb_idcard(rs.getString("esbj_idcard"));
				m.setEmsb_startmonth(rs.getInt("esbj_startmonth"));
				m.setEmsb_radix(rs.getInt("esbj_radix"));
				m.setEmsb_addname(rs.getString("esbj_addname"));
				m.setEmsb_addtime(rs.getString("esbj_addtime"));
				m.setEmsb_ifdeclare(rs.getInt("esbj_ifdeclare"));
				m.setEmsb_printtime(rs.getString("esbj_printtime"));
				m.setEmsb_declaretime(rs.getString("esbj_declaretime"));
				m.setEmsb_declarename(rs.getString("esbj_declarename"));
				m.setEmsb_remark(rs.getString("esbj_remark"));
				m.setEmsb_dptime(rs.getDate("esbj_dptime"));
				m.setEmsb_flag(rs.getString("esbj_flag"));
				m.setEmsb_single(rs.getInt("esbj_single"));
				m.setEmsb_reason(rs.getString("esbj_reason"));
				m.setEmsb_tapr_id(rs.getInt("esbj_tapr_id"));
				m.setEmsb_feeownmonth(rs.getInt("esbj_feeownmonth"));
				m.setEmsb_totalcp(rs.getBigDecimal("esbj_totalcp"));
				m.setEmsb_totalop(rs.getBigDecimal("esbj_totalop"));
				m.setEmsb_total(rs.getBigDecimal("esbj_totalcp").add(
						rs.getBigDecimal("esbj_totalop")));
				list.add(m);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return list;
	}

	// 获取社保变更数据
	public List<EmSheBaoChangeModel> getEmscData(String where) {
		List<EmSheBaoChangeModel> list = new ArrayList<EmSheBaoChangeModel>();
		EmSheBaoChangeModel m = null;
		StringBuilder sql = new StringBuilder();
		sql.append("select ec.*,isnull(sbid.coco_shebaoID,0)coco_shebaoid,isnull(msg_a.msg_a,-1)msg_a");
		sql.append(" from Emshebaochange ec");
		sql.append(" left join (select c.gid,c.cid,d.coco_shebaoid from (select a.gid,a.cid,b.coli_coco_id from CoGList a INNER join CoOfferList b on a.cgli_coli_id=b.coli_id where b.coli_state=1 group by a.gid,a.cid,b.coli_coco_id)c inner join CoCompact d on c.coli_coco_id=d.coco_id where d.coco_shebaoid is not null)sbid on ec.GID=sbid.gid");
		sql.append(" left join (select smwr_tid,case when syme_log_id="
				+ UserInfo.getUserid()
				+ " then 2 when symr_log_id="
				+ UserInfo.getUserid()
				+ " then symr_readstate end msg_a from View_Message where EXISTS(select syme_id from (select smwr_tid,MAX(syme_id)syme_id from View_Message where syme_state=1 and (symr_log_id="
				+ UserInfo.getUserid()
				+ " or syme_log_id="
				+ UserInfo.getUserid()
				+ ") and  smwr_table='EmSheBaoChange' group by smwr_tid)msg where View_Message.syme_id=msg.syme_id))msg_a on ec.id=msg_a.smwr_tid");
		// sql.append(" left join (select smwr_tid,count(*)msg_w from View_SysMessage where smwr_class=2 and symr_readstate=0 and smwr_table='Emshebaochange' GROUP BY smwr_table,smwr_tid)msg_w on ec.id=msg_w.smwr_tid");
		// sql.append(" left join (select smwr_tid,count(*)msg_y from View_SysMessage where smwr_class=2 and symr_readstate=1 and smwr_table='Emshebaochange' GROUP BY smwr_table,smwr_tid)msg_y on ec.id=msg_y.smwr_tid");
		sql.append(" where 1=1 ");
		sql.append(where);
		try {
			ResultSet rs = conn.GRS(sql.toString());
			while (rs.next()) {
				m = new EmSheBaoChangeModel();
				m.setId(rs.getInt("id"));
				m.setCid(rs.getInt("cid"));
				m.setGid(rs.getInt("gid"));
				m.setOwnmonth(rs.getInt("ownmonth"));
				m.setEmsc_formula(rs.getString("emsc_formula"));
				m.setEmsc_company(rs.getString("emsc_company"));
				m.setEmsc_shortname(rs.getString("emsc_shortname"));
				m.setEmsc_name(rs.getString("emsc_name"));
				m.setEmsc_computerid(rs.getString("emsc_computerid"));
				m.setEmsc_idcard(rs.getString("emsc_idcard"));
				m.setEmsc_hj(rs.getString("emsc_hj"));
				m.setEmsc_radix(rs.getInt("emsc_radix"));
				m.setEmsc_folk(rs.getString("emsc_folk"));
				m.setEmsc_mobile(rs.getString("emsc_mobile"));
				m.setEmsc_worker(rs.getString("emsc_worker"));
				m.setEmsc_officialrank(rs.getString("emsc_officialrank"));
				m.setEmsc_hand(rs.getString("emsc_hand"));
				m.setEmsc_yl(rs.getString("emsc_yl"));
				m.setEmsc_gs(rs.getString("emsc_gs"));
				m.setEmsc_sye(rs.getString("emsc_sye"));
				m.setEmsc_syu(rs.getString("emsc_syu"));
				m.setEmsc_yltype(rs.getString("emsc_yltype"));
				m.setEmsc_house(rs.getString("emsc_house"));
				m.setEmsc_single(rs.getInt("emsc_single"));
				m.setEmsc_client(rs.getString("emsc_client"));
				m.setEmsc_remark(rs.getString("emsc_remark"));
				m.setEmsc_change(rs.getString("emsc_change"));
				m.setEmsc_content(rs.getString("emsc_content"));
				m.setEmsc_declaretime(rs.getString("emsc_declaretime"));
				m.setEmsc_declarename(rs.getString("emsc_declarename"));
				m.setEmsc_addtime(rs.getString("emsc_addtime"));
				m.setEmsc_addname(rs.getString("emsc_addname"));
				m.setEmsc_chargeman(rs.getString("emsc_chargeman"));
				m.setEmsc_excelfile(rs.getString("emsc_excelfile"));
				m.setEmsc_iffifteen(rs.getString("emsc_iffifteen"));
				m.setEmsc_ifdeclare(rs.getString("emsc_ifdeclare"));
				m.setEmsc_ifinure(rs.getString("emsc_ifinure"));
				m.setEmsc_ifmodify(rs.getString("emsc_ifmodify"));
				m.setEmsc_ifsame(rs.getString("emsc_ifsame"));
				m.setEmsc_ifmsg(rs.getString("emsc_ifmsg"));
				m.setEmsc_flag(rs.getString("emsc_flag"));
				m.setEmsc_flagname(rs.getString("emsc_flagname"));
				m.setEmsc_ifwrong(rs.getInt("emsc_ifwrong"));
				m.setEmsc_confirmtime(rs.getString("emsc_confirmtime"));
				m.setEmsc_tid(rs.getString("emsc_tid"));
				m.setEmsc_stopreason(rs.getString("emsc_stopreason"));
				m.setCoco_shebaoid(rs.getString("coco_shebaoid"));
				m.setMsg_a(rs.getString("msg_a"));
				// m.setMsg_w(rs.getString("msg_w"));
				// m.setMsg_y(rs.getString("msg_y"));
				m.setEmsc_tapr_id(rs.getInt("emsc_tapr_id"));
				list.add(m);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}

	// 根据ID获取社保变更信息
	public EmSheBaoChangeModel getEmSbChange(int id) {
		EmSheBaoChangeModel m = new EmSheBaoChangeModel();
		String sql = "select * from Emshebaochange where id=?";
		PreparedStatement psmt = conn.getpre(sql);
		try {
			psmt.setInt(1, id);
			ResultSet rs = psmt.executeQuery();
			if (rs.next()) {
				m.setId(rs.getInt("id"));
				m.setGid(rs.getInt("gid"));
				m.setCid(rs.getInt("cid"));
				m.setEmsc_company(rs.getString("emsc_company"));
				m.setOwnmonth(rs.getInt("ownmonth"));
				m.setEmsc_name(rs.getString("emsc_name"));
				m.setEmsc_computerid(rs.getString("emsc_computerid"));
				m.setEmsc_idcard(rs.getString("emsc_idcard"));
				m.setEmsc_hj(rs.getString("emsc_hj"));
				m.setEmsc_radix(rs.getInt("emsc_radix"));
				m.setEmsc_addname(rs.getString("emsc_addname"));
				m.setEmsc_addtime(rs.getString("emsc_addtime"));
				m.setEmsc_change(rs.getString("emsc_change"));
				m.setEmsc_content(rs.getString("emsc_content"));
				m.setEmsc_remark(rs.getString("emsc_remark"));
				m.setEmsc_tapr_id(rs.getInt("emsc_tapr_id"));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return m;
	}

	// 根据ID获取外籍人社保变更信息
	public EmShebaoChangeForeignerModel getForeignerChangeById(int id) {
		EmShebaoChangeForeignerModel m = new EmShebaoChangeForeignerModel();
		EmShebaoUpdateModel euModel = new EmShebaoUpdateModel();
		String sql = "select * from EmShebaoChangeForeigner ef inner join EmShebaoUpdate eu on ef.GID=eu.GID where ef.id=?";
		PreparedStatement psmt = conn.getpre(sql);
		try {
			psmt.setInt(1, id);
			ResultSet rs = psmt.executeQuery();
			rs.next();
			m.setId(rs.getInt("id"));
			m.setGid(rs.getInt("gid"));
			m.setCid(rs.getInt("cid"));
			m.setEmsc_company(rs.getString("emsc_company"));
			m.setEmsc_name(rs.getString("emsc_name"));
			m.setOwnmonth(rs.getInt("ownmonth"));
			m.setEmsc_computerid(rs.getString("emsc_computerid"));
			m.setEmsc_idcard(rs.getString("emsc_idcard"));
			m.setEmsc_addname(rs.getString("emsc_addname"));
			m.setEmsc_addtime(rs.getString("emsc_addtime"));
			m.setEmsc_ifdeclare(rs.getString("emsc_ifdeclare"));
			m.setEmsc_flag(rs.getString("emsc_flag"));
			m.setEmsc_radix(rs.getInt("emsc_radix"));
			m.setEmsc_hj(rs.getString("emsc_hj"));
			m.setEmsc_folk(rs.getString("emsc_folk"));
			m.setEmsc_mobile(rs.getString("emsc_mobile"));
			m.setEmsc_worker(rs.getString("emsc_worker"));
			m.setEmsc_officialrank(rs.getString("emsc_officialrank"));
			m.setEmsc_hand(rs.getString("emsc_hand"));
			m.setEmsc_yl(rs.getString("emsc_yl"));
			m.setEmsc_gs(rs.getString("emsc_gs"));
			m.setEmsc_sye(rs.getString("emsc_sye"));
			m.setEmsc_syu(rs.getString("emsc_syu"));
			m.setEmsc_yltype(rs.getString("emsc_yltype"));
			m.setEmsc_tapr_id(rs.getInt("emsc_tapr_id"));
			m.setEmsc_reason(rs.getString("emsc_reason"));
			euModel.setEsiu_ylcp(rs.getBigDecimal("esiu_ylcp"));
			euModel.setEsiu_jlcp(rs.getBigDecimal("esiu_jlcp"));
			euModel.setEsiu_syucp(rs.getBigDecimal("esiu_syucp"));
			euModel.setEsiu_syecp(rs.getBigDecimal("esiu_syecp"));
			euModel.setEsiu_gscp(rs.getBigDecimal("esiu_gscp"));
			euModel.setEsiu_ylop(rs.getBigDecimal("esiu_ylop"));
			euModel.setEsiu_jlop(rs.getBigDecimal("esiu_jlop"));
			euModel.setEsiu_syuop(rs.getBigDecimal("esiu_syuop"));
			euModel.setEsiu_syeop(rs.getBigDecimal("esiu_syeop"));
			euModel.setEsiu_gsop(rs.getBigDecimal("esiu_gsop"));
			euModel.setEsiu_totalcp(rs.getBigDecimal("esiu_totalcp"));
			euModel.setEsiu_totalop(rs.getBigDecimal("esiu_totalop"));
			m.setEmshebaoupdateModel(euModel);

		} catch (SQLException e) {
			e.printStackTrace();
		}
		return m;
	}

	// 根据ID获取社保生育津贴申请
	public EmShebaoChangeMAModel getMAChangeById(int id) {
		EmShebaoChangeMAModel m = new EmShebaoChangeMAModel();
		String sql = "select * from EmShebaoChangeMA where id=?";
		PreparedStatement psmt = conn.getpre(sql);
		try {
			psmt.setInt(1, id);
			ResultSet rs = psmt.executeQuery();
			rs.next();
			m.setId(rs.getInt("id"));
			m.setCid(rs.getInt("cid"));
			m.setGid(rs.getInt("gid"));
			m.setOwnmonth(rs.getInt("ownmonth"));
			m.setEscm_name(rs.getString("Escm_name"));
			m.setEscm_company(rs.getString("Escm_company"));
			m.setEscm_shortname(rs.getString("Escm_shortname"));
			m.setEscm_computerid(rs.getString("Escm_computerid"));
			m.setEscm_idcard(rs.getString("Escm_idcard"));
			m.setEscm_hj(rs.getString("Escm_hj"));
			m.setEscm_sex(rs.getString("Escm_sex"));
			m.setEscm_age(rs.getInt("Escm_age"));
			m.setEscm_single(rs.getInt("Escm_single"));
			m.setEscm_ifdeclare(rs.getInt("Escm_ifdeclare"));
			m.setEscm_declaretime(rs.getString("Escm_declaretime"));
			m.setEscm_declarename(rs.getString("Escm_declarename"));
			m.setEscm_addtime(rs.getString("Escm_addtime"));
			m.setEscm_addname(rs.getString("Escm_addname"));
			m.setEscm_easylabour(rs.getInt("Escm_easylabour"));
			m.setEscm_easylabourmb(rs.getInt("Escm_easylabourmb"));
			m.setEscm_dystocia(rs.getInt("Escm_dystocia"));
			m.setEscm_dystociatype(rs.getInt("Escm_dystociatype"));
			m.setEscm_dystociamb(rs.getInt("Escm_dystociamb"));
			m.setEscm_abortion_fm(rs.getInt("Escm_abortion_fm"));
			m.setEscm_abortion_nfm(rs.getInt("Escm_abortion_nfm"));
			m.setEscm_setiud(rs.getInt("Escm_setiud"));
			m.setEscm_getiud(rs.getInt("Escm_getiud"));
			m.setEscm_tuballigation(rs.getInt("Escm_tuballigation"));
			m.setEscm_tubalreversal(rs.getInt("Escm_tubalreversal"));
			m.setEscm_vasoligation(rs.getInt("Escm_vasoligation"));
			m.setEscm_vasostomy(rs.getInt("Escm_vasostomy"));
			m.setEscm_endoffp(rs.getString("Escm_endoffp"));
			m.setEscm_mobile(rs.getString("Escm_mobile"));
			m.setEscm_ifpay(rs.getInt("Escm_ifpay"));
			m.setEscm_bank(rs.getString("Escm_bank"));
			m.setEscm_bankacc(rs.getString("Escm_bankacc"));
			m.setEscm_ifagree(rs.getInt("Escm_ifagree"));
			m.setEscm_confirmtime(rs.getString("Escm_confirmtime"));
			m.setEscm_flag(rs.getString("escm_flag"));
			m.setEscm_remark(rs.getString("escm_remark"));
			m.setEscm_ifdata(rs.getInt("escm_ifdata"));
			m.setEscm_batchnum(rs.getString("escm_batchnum"));
			m.setEscm_af_filename(rs.getString("escm_af_filename"));
			m.setEscm_bf_filename(rs.getString("escm_bf_filename"));
			m.setEscm_fee(rs.getBigDecimal("escm_fee"));
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return m;
	}

	// 获取社保申报分配信息
	public List<String[]> getAssigenment() {
		String sql = "select d.dep_name,log_name,coba_shebaodeclare=(select top 1 coba_shebaodeclare from cobase where coba_client=l.log_name),count=(select count(cid) from cobase where coba_client=l.log_name) from login l left join department d on l.dep_id=d.dep_id where log_inure=1 and d.dep_name in ('客户服务部','全国项目部') order by d.dep_name,log_name";
		List<String[]> list = new ArrayList<String[]>();
		try {
			ResultSet rs = conn.GRS(sql);
			while (rs.next()) {
				list.add(new String[] { rs.getString("dep_name"),
						rs.getString("log_name"),
						rs.getString("coba_shebaodeclare"),
						rs.getString("count") });
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}

	// 获取雇员福利部用户
	public List<String> getGyflbUser() {
		String sql = "select log_name from login l left join department d on l.dep_id=d.dep_id where log_inure=1 and d.dep_name = '雇员福利部'";
		List<String> list = new ArrayList<String>();
		try {
			ResultSet rs = conn.GRS(sql);
			while (rs.next()) {
				list.add(rs.getString("log_name"));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}
}
