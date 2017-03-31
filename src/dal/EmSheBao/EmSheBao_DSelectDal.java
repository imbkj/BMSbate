/*
 * 创建人：林少斌
 * 创建时间：2013-12-26
 * 用途：社保变更数据查询Dal
 */
package dal.EmSheBao;

import java.math.BigDecimal;
import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.zkoss.zul.ListModelList;

import Conn.dbconn;
import Model.CoCompactModel;
import Model.EmSheBaoChangeModel;
import Model.EmSheBaoFinanceModel;
import Model.EmShebaoBJModel;
import Model.EmShebaoChangeForeignerModel;
import Model.EmShebaoChangeMAModel;
import Model.EmShebaoChangeSZSIModel;
import Model.EmShebaoDeclareOKModel;
import Model.EmShebaoErrModel;
import Model.EmShebaoSZSIFileModel;
import Model.EmShebaoSetupModel;
import Model.EmShebaoUpdateModel;
import Model.EmbaseModel;
import Util.UserInfo;

public class EmSheBao_DSelectDal {
	private static dbconn conn = new dbconn();

	/**
	 * @Title: shebaoOnboard
	 * @Description: TODO(判断员工入职是否完成,社保在册数据)
	 * @return
	 * @return boolean 返回类型
	 * @throws
	 */
	public boolean shebaoOnboard(Integer gid) {
		boolean b = false;
		List<EmbaseModel> list = new ListModelList<>();
		dbconn db = new dbconn();
		String sql = "select gid from EmBase a where gid=?"
				+ " and (exists (select 1 from emshebaochange where GID=a.gid)"
				+ " or exists (select 1 from EmShebaoChangeForeigner where GID=a.gid))";
		try {
			list = db.find(sql, EmbaseModel.class, null, gid);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		if (list.size() > 0) {
			b = true;
		}
		return b;
	}

	/**
	 * @Title: shebaobjOnboard
	 * @Description: TODO(判断员工入职是否完成,社保补缴数据)
	 * @param gid
	 * @return
	 * @return boolean 返回类型
	 * @throws
	 */
	public boolean shebaobjOnboard(Integer gid) {
		boolean b = false;
		List<EmbaseModel> list = new ListModelList<>();
		dbconn db = new dbconn();
		String sql = "select gid from EmBase a where gid=?"
				+ " and exists (select 1 from emshebaobj where GID=a.gid and (emsb_tapr_id is null or emsb_tapr_id>0))";
		try {
			list = db.find(sql, EmbaseModel.class, null, gid);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		if (list.size() > 0) {
			b = true;
		}
		return b;
	}

	// 查询是否存在社保在册数据
	public boolean getupdateInfo(Integer gid) {
		boolean b = false;
		List<EmShebaoUpdateModel> list = new ListModelList<>();
		dbconn db = new dbconn();
		String sql = "select gid from emshebaoupdate where gid=?";
		try {
			list = db.find(sql, EmShebaoUpdateModel.class, null, gid);
			if (list.size() > 0) {
				b = true;
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return b;
	}

	// 查询是否存在补缴数据
	public boolean getbjInfo(Integer gid) {
		boolean b = false;
		List<EmShebaoBJModel> list = new ListModelList<>();
		dbconn db = new dbconn();
		String sql = "select gid from emshebaobj where gid=? and emsb_ifdeclare !=3 and emsb_ifdeclare !=9";
		try {
			list = db.find(sql, EmShebaoBJModel.class, null, gid);
			if (list.size() > 0) {
				b = true;
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return b;
	}

	// 查询社保在册数据
	public List<EmShebaoUpdateModel> getupdateList(Integer gid) {
		List<EmShebaoUpdateModel> list = new ListModelList<>();
		dbconn db = new dbconn();
		String sql = "select emhu_id,cid,gid,emhu_single,emhu_ifstop from emshebaoupdate where gid=?";
		try {
			list = db.find(sql, EmShebaoUpdateModel.class, null, gid);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return list;
	}

	// 获取社保独立开户的公司数据的数据列表
	public List<CoCompactModel> getSCompany() {
		List<CoCompactModel> list = new ArrayList<CoCompactModel>();
		CoCompactModel m = null;
		String sql = "SELECT coba_shortname,coba_shortSpell,ISNULL(coco_shebaoid,0)coco_shebaoid FROM cocompact_single1_v a INNER JOIN CoBase b ON a.cid=b.cid WHERE a.coco_shebao='独立开户' ORDER BY b.coba_shortspell,b.coba_shortname";
		try {

			m = new CoCompactModel();
			m.setCompany("所有公司");
			m.setCoba_shortspell("");
			m.setCoco_shebaoID("0");
			list.add(m);

			ResultSet rs = conn.GRS(sql);
			while (rs.next()) {
				m = new CoCompactModel();
				m.setCompany(rs.getString("coba_shortname"));
				m.setCoba_shortspell(rs.getString("coba_shortSpell"));
				m.setCoco_shebaoID(rs.getString("coco_shebaoid"));
				list.add(m);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}

	// 获取未申报完成的独立开户公司
	public List<CoCompactModel> getNDSCompany(String ownmonth) {
		List<CoCompactModel> list = new ArrayList<CoCompactModel>();
		CoCompactModel m = null;
		String sql = "select a.cid,coba_company,coba_shortSpell,ISNULL(coco_shebaoid,0)coco_shebaoid from EmShebaoChange a inner join cobase b on a.cid=b.cid inner join cocompact_single1_v c on a.cid=c.cid where ownmonth="
				+ ownmonth
				+ " and emsc_single=1 and emsc_ifdeclare=0 and coco_shebaoid is not null and c.COCO_STATE>1 and c.coco_shebao='独立开户' group by a.cid,coba_company,coba_shortSpell,coco_shebaoid order by coba_shortSpell,coco_shebaoid";
		try {

			m = new CoCompactModel();
			m.setCompany("所有公司");
			m.setCoba_shortspell("");
			m.setCoco_shebaoID("");
			list.add(m);

			ResultSet rs = conn.GRS(sql);
			while (rs.next()) {
				m = new CoCompactModel();
				m.setCompany(rs.getString("coba_company"));
				m.setCoba_shortspell(rs.getString("coba_shortSpell"));
				m.setCoco_shebaoID(rs.getString("coco_shebaoid"));
				list.add(m);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}

	// 获取未申报完成的独立开户公司(外籍人新参保)
	public List<CoCompactModel> getForNDSCompany(String ownmonth) {
		List<CoCompactModel> list = new ArrayList<CoCompactModel>();
		CoCompactModel m = null;
		String sql = "select a.cid,coba_company,coba_shortSpell,coco_shebaoid from EmShebaoChangeForeigner a inner join cobase b on a.cid=b.cid inner join cocompact_single1_v c on a.cid=c.cid  where ownmonth="
				+ ownmonth
				+ "  and emsc_single=1 and emsc_ifdeclare=0 and coco_shebaoid is not null group by a.cid,coba_company,coba_shortSpell,coco_shebaoid order by coba_shortSpell";
		try {

			m = new CoCompactModel();
			m.setCompany("所有公司");
			m.setCoba_shortspell("");
			m.setCoco_shebaoID("");
			list.add(m);

			ResultSet rs = conn.GRS(sql);
			while (rs.next()) {
				m = new CoCompactModel();
				m.setCompany(rs.getString("coba_company"));
				m.setCoba_shortspell(rs.getString("coba_shortSpell"));
				m.setCoco_shebaoID(rs.getString("coco_shebaoid"));
				list.add(m);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}

	// 获取未申报完成的独立开户公司(特殊交单)
	public List<CoCompactModel> getSZSINDSCompany(String ownmonth) {
		List<CoCompactModel> list = new ArrayList<CoCompactModel>();
		CoCompactModel m = null;
		String sql = "select a.cid,coba_company,coba_shortSpell,coco_shebaoid from EmShebaoChangeSZSI a inner join cobase b on a.cid=b.cid inner join cocompact_single1_v c on a.cid=c.cid where ownmonth="
				+ ownmonth
				+ " and escs_single=1 and escs_ifdeclare not in(4,2,7)  and coco_shebaoid is not null group by a.cid,coba_company,coba_shortSpell,coco_shebaoid order by coba_shortSpell";
		try {
			m = new CoCompactModel();
			m.setCompany("所有公司");
			m.setCoba_shortspell("");
			m.setCoco_shebaoID("");
			list.add(m);

			ResultSet rs = conn.GRS(sql);
			while (rs.next()) {
				m = new CoCompactModel();
				m.setCompany(rs.getString("coba_company"));
				m.setCoba_shortspell(rs.getString("coba_shortSpell"));
				m.setCid(rs.getString("cid"));
				m.setCoco_shebaoID(rs.getString("coco_shebaoid"));
				list.add(m);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}

	// 获取社保变更数据
	public List<EmSheBaoChangeModel> getEmSCData(String top, String str,
			String order, String bjOwnmonth) {
		List<EmSheBaoChangeModel> list = new ArrayList<EmSheBaoChangeModel>();
		EmSheBaoChangeModel m = null;
		String sql = "SELECT "
				+ top
				+ " sb.*,isnull(sbid.coco_shebaoID,0)coco_shebaoid,isnull(msg_a.msg_a,-1)msg_a,isnull(bj.bj_cou,0)bj_cou ";
		sql = sql + " from emshebaochange sb ";
		sql = sql
				+ " left join (select c.gid,c.cid,d.coco_shebaoid from (select a.gid,a.cid,cf.coof_coco_id from CoGList a left join CoOfferList b on a.cgli_coli_id=b.coli_id left join CoOffer cf on b.coli_coof_id=cf.coof_id where b.coli_state=1 and b.coli_name='社会保险服务' group by a.gid,a.cid,cf.coof_coco_id)c inner join cocompact_single1_v d on c.coof_coco_id=d.coco_id where d.coco_shebaoid is not null group by c.gid,c.cid,d.coco_shebaoid)sbid on sb.GID=sbid.gid AND sb.cid=sbid.cid ";
		sql = sql
				+ " left join (select smwr_tid,case when syme_log_id="
				+ UserInfo.getUserid()
				+ " then 2 when symr_log_id="
				+ UserInfo.getUserid()
				+ " then symr_readstate else 1 end msg_a from View_Message where EXISTS(select syme_id from (select smwr_tid,MAX(syme_id)syme_id from View_Message where syme_state=1 and  smwr_table='EmSheBaoChange' group by smwr_tid)msg where View_Message.syme_id=msg.syme_id))msg_a on sb.id=msg_a.smwr_tid ";
		// sql = sql+
		// " left join (select smwr_tid,count(*)msg_w from View_SysMessage where smwr_class=2 and symr_readstate=0 and smwr_table='EmSheBaoChange' GROUP BY smwr_table,smwr_tid)msg_w on sb.id=msg_w.smwr_tid ";
		// sql = sql+
		// " left join (select smwr_tid,count(*)msg_y from View_SysMessage where smwr_class=2 and symr_readstate=1 and smwr_table='EmSheBaoChange' GROUP BY smwr_table,smwr_tid)msg_y on sb.id=msg_y.smwr_tid";
		sql = sql
				+ " left join (select gid,count(*)bj_cou from EmShebaoBJ WHERE Ownmonth>="
				+ bjOwnmonth + " GROUP BY gid)bj  on sb.gid=bj.gid ";
		sql = sql + " where sb.emsc_ifdeclare<4 " + str + " " + order;
		// System.out.println(sql);
		try {
			ResultSet rs = conn.GRS(sql);
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
				m.setBj_cou(rs.getString("bj_cou"));
				m.setEmsc_tapr_id(rs.getInt("emsc_tapr_id"));
				list.add(m);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}

	// 获取社保交单变更数据(交单数据)
	public List<EmShebaoChangeSZSIModel> getEmSCSZSIData(String top,
			String str, String order) {
		List<EmShebaoChangeSZSIModel> list = new ArrayList<EmShebaoChangeSZSIModel>();
		EmShebaoChangeSZSIModel m = null;
		String sql = "SELECT "
				+ top
				+ " sb.*,isnull(sbid.coco_shebaoID,0)coco_shebaoid,isnull(msg_a.msg_a,-1)msg_a ";
		sql = sql
				+ " from (select  a.*,a.ownmonth szsimonth,d.esiu_computerid,d.esiu_idcard,b.coba_client as escs_client from EmShebaoChangeSZSI as a inner join CoBase as b on a.cid=b.cid inner join embase as c on a.gid=c.gid left join emshebaoupdate d on a.gid=d.gid)sb ";
		sql = sql
				+ " left join (select c.gid,c.cid,d.coco_shebaoid from (select a.gid,a.cid,cf.coof_coco_id from CoGList a left join CoOfferList b on a.cgli_coli_id=b.coli_id left join CoOffer cf on b.coli_coof_id=cf.coof_id where b.coli_state=1 and b.coli_name='社会保险服务' group by a.gid,a.cid,cf.coof_coco_id)c inner join cocompact_single1_v d on c.coof_coco_id=d.coco_id where d.coco_shebaoid is not null group by c.gid,c.cid,d.coco_shebaoid)sbid on sb.GID=sbid.gid AND sb.cid=sbid.cid ";
		sql = sql
				+ " left join (select smwr_tid,case when syme_log_id="
				+ UserInfo.getUserid()
				+ " then 2 when symr_log_id="
				+ UserInfo.getUserid()
				+ " then symr_readstate  else 1 end msg_a from View_Message where EXISTS(select syme_id from (select smwr_tid,MAX(syme_id)syme_id from View_Message where syme_state=1 and  smwr_table='EmShebaoChangeSZSI' group by smwr_tid)msg where View_Message.syme_id=msg.syme_id))msg_a on sb.escs_id=msg_a.smwr_tid ";
		// sql = sql+
		// " left join (select smwr_tid,count(*)msg_w from View_SysMessage where smwr_class=2 and symr_readstate=0 and smwr_table='EmShebaoChangeSZSI' GROUP BY smwr_table,smwr_tid)msg_w on sb.escs_id=msg_w.smwr_tid ";
		// sql = sql+
		// " left join (select smwr_tid,count(*)msg_y from View_SysMessage where smwr_class=2 and symr_readstate=1 and smwr_table='EmShebaoChangeSZSI' GROUP BY smwr_table,smwr_tid)msg_y on sb.escs_id=msg_y.smwr_tid";
		sql = sql + " where 1=1" + str + " " + order;
		// System.out.println(sql);
		try {

			ResultSet rs = conn.GRS(sql);
			while (rs.next()) {
				m = new EmShebaoChangeSZSIModel();
				m.setEscs_id(rs.getInt("escs_id"));
				m.setCid(rs.getInt("cid"));
				m.setGid(rs.getInt("gid"));
				m.setOwnmonth(rs.getInt("szsimonth"));
				m.setEscs_company(rs.getString("escs_company"));
				m.setEscs_name(rs.getString("escs_name"));
				m.setComputerid(rs.getString("esiu_computerid"));
				m.setIdcard(rs.getString("esiu_idcard"));
				m.setEscs_change(rs.getString("escs_change"));
				m.setEscs_content(rs.getString("escs_content"));
				m.setEscs_changehj(rs.getString("escs_changehj"));
				m.setEscs_changename(rs.getString("escs_changename"));
				m.setEscs_changeidcard(rs.getString("escs_changeidcard"));
				m.setEscs_changeylid(rs.getString("escs_changeylid"));
				m.setEscs_changecid(rs.getString("escs_changecid"));
				m.setEscs_changeofficialrank(rs
						.getString("escs_changeofficialrank"));
				m.setEscs_addtime(rs.getDate("escs_addtime"));
				m.setEscs_addname(rs.getString("escs_addname"));
				m.setEscs_ifdeclare(rs.getInt("escs_ifdeclare"));
				m.setEscs_declaretime(rs.getDate("escs_declaretime"));
				m.setEscs_declarename(rs.getString("escs_declarename"));
				m.setEscs_single(rs.getInt("escs_single"));
				m.setEscs_shebaoid(rs.getString("escs_shebaoid"));
				m.setEscs_remark(rs.getString("escs_remark"));
				m.setEscs_ifbase(rs.getInt("escs_ifbase"));
				m.setEscs_ifsb(rs.getInt("escs_ifsb"));
				m.setEscs_rtime(rs.getString("escs_rtime"));
				m.setEscs_rname(rs.getString("escs_rname"));
				m.setEscs_qname(rs.getString("escs_qname"));
				m.setEscs_ttime(rs.getString("escs_ttime"));
				m.setEscs_tname(rs.getString("escs_tname"));
				m.setEscs_hname(rs.getString("escs_hname"));
				m.setEscs_flag(rs.getString("escs_flag"));
				m.setEscs_flagname(rs.getString("escs_flagname"));
				m.setEmsc_tapr_id(rs.getInt("escs_tapr_id"));
				m.setCoco_shebaoid(rs.getString("coco_shebaoid"));
				m.setMsg_a(rs.getString("msg_a"));
				// m.setMsg_w(rs.getString("msg_w"));
				// m.setMsg_y(rs.getString("msg_y"));
				m.setEscs_confirmtime(rs.getString("escs_confirmtime"));
				m.setEscs_ifnet(rs.getInt("escs_ifnet"));
				list.add(m);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}

	// 获取社保交单变更数据
	public List<EmShebaoChangeSZSIModel> getEmSCSZSIData(String str,
			String order) {
		List<EmShebaoChangeSZSIModel> list = new ArrayList<EmShebaoChangeSZSIModel>();
		EmShebaoChangeSZSIModel m = null;
		String sql = "select * from EmShebaoChangeSZSI  where 1=1 " + str
				+ order;
		try {

			ResultSet rs = conn.GRS(sql);
			while (rs.next()) {
				m = new EmShebaoChangeSZSIModel();
				m.setEscs_id(rs.getInt("escs_id"));
				m.setGid(rs.getInt("gid"));
				m.setCid(rs.getInt("cid"));
				m.setOwnmonth(rs.getInt("ownmonth"));
				m.setEscs_company(rs.getString("escs_company"));
				m.setEscs_name(rs.getString("escs_name"));
				m.setEscs_change(rs.getString("escs_change"));
				m.setEscs_content(rs.getString("escs_content"));
				m.setEscs_changehj(rs.getString("escs_changehj"));
				m.setEscs_changename(rs.getString("escs_changename"));
				m.setEscs_changeidcard(rs.getString("escs_changeidcard"));
				m.setEscs_changeylid(rs.getString("escs_changeylid"));
				m.setEscs_changecid(rs.getString("escs_changecid"));
				m.setEscs_changeofficialrank(rs
						.getString("escs_changeofficialrank"));
				m.setEscs_addtime(rs.getDate("escs_addtime"));
				m.setEscs_addname(rs.getString("escs_addname"));
				m.setEscs_ifdeclare(rs.getInt("escs_ifdeclare"));
				m.setEscs_declaretime(rs.getDate("escs_declaretime"));
				m.setEscs_declarename(rs.getString("escs_declarename"));
				m.setEscs_single(rs.getInt("escs_single"));
				m.setEscs_shebaoid(rs.getString("escs_shebaoid"));
				m.setEscs_remark(rs.getString("escs_remark"));
				m.setEscs_ifbase(rs.getInt("escs_ifbase"));
				m.setEscs_ifsb(rs.getInt("escs_ifsb"));
				m.setEscs_rtime(rs.getString("escs_rtime"));
				m.setEscs_rname(rs.getString("escs_rname"));
				m.setEscs_qname(rs.getString("escs_qname"));
				m.setEscs_ttime(rs.getString("escs_ttime"));
				m.setEscs_tname(rs.getString("escs_tname"));
				m.setEscs_hname(rs.getString("escs_hname"));
				m.setEscs_flag(rs.getString("escs_flag"));
				m.setEscs_flagname(rs.getString("escs_flagname"));
				m.setEmsc_tapr_id(rs.getInt("escs_tapr_id"));
				m.setEscs_uploadfile(rs.getString("escs_uploadfile"));
				m.setEscs_ifnet(rs.getInt("escs_ifnet"));
				list.add(m);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}

	// 获取社保变更数据
	public List<EmSheBaoChangeModel> getEmSCData(String str, String order) {
		List<EmSheBaoChangeModel> list = new ArrayList<EmSheBaoChangeModel>();
		EmSheBaoChangeModel m = null;
		String sql = "select * from EmShebaoChange a inner join CoBase as c on a.cid=c.cid inner join EmBase as d on a.gid=d.gid  where 1=1 "
				+ str + order;
		try {

			ResultSet rs = conn.GRS(sql);
			while (rs.next()) {
				m = new EmSheBaoChangeModel();
				m.setId(rs.getInt("id"));
				m.setCid(rs.getInt("cid"));
				m.setGid(rs.getInt("gid"));
				m.setOwnmonth(rs.getInt("ownmonth"));
				m.setEmsc_formula(rs.getString("emsc_formula"));
				m.setEmsc_company(rs.getString("coba_company"));
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
				m.setEmsc_tapr_id(rs.getInt("emsc_tapr_id"));
				m.setEmba_birth(rs.getString("emba_birth"));
				m.setEmba_sex(rs.getString("emba_sex"));
				list.add(m);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}

	// 根据GID获取员工历史社保表的信息
	public List<EmShebaoUpdateModel> getAllShebaoByGid(int gid) {
		List<EmShebaoUpdateModel> list = new ArrayList<EmShebaoUpdateModel>();
		EmShebaoUpdateModel m = null;
		String sql = "select es.*,co.coba_company from EmShebao es inner join CoBase co on es.CID=co.CID where GID=? order by Ownmonth desc";
		PreparedStatement psmt = conn.getpre(sql);
		try {
			psmt.setInt(1, gid);
			ResultSet rs = psmt.executeQuery();
			while (rs.next()) {
				m = new EmShebaoUpdateModel(rs.getInt("id"), rs.getInt("cid"),
						rs.getInt("gid"), rs.getInt("ownmonth"),
						rs.getString("coba_company"),
						rs.getString("esiu_name"),
						rs.getString("esiu_computerid"),
						rs.getString("esiu_idcard"), rs.getString("esiu_hj"),
						rs.getInt("esiu_radix"), rs.getString("esiu_yl"),
						rs.getString("esiu_gs"), rs.getString("esiu_sye"),
						rs.getString("esiu_syu"), rs.getString("esiu_yltype"),
						rs.getString("esiu_house"),
						rs.getBigDecimal("esiu_ylcp"),
						rs.getBigDecimal("esiu_ylop"),
						rs.getBigDecimal("esiu_jlcp"),
						rs.getBigDecimal("esiu_jlop"),
						rs.getBigDecimal("esiu_syucp"), BigDecimal.ZERO,
						rs.getBigDecimal("esiu_syecp"),
						rs.getBigDecimal("esiu_syeop"),
						rs.getBigDecimal("esiu_gscp"), BigDecimal.ZERO,
						rs.getBigDecimal("esiu_housecp"),
						rs.getBigDecimal("esiu_houseop"),
						rs.getBigDecimal("esiu_totalcp"),
						rs.getBigDecimal("esiu_totalop"),
						rs.getInt("esiu_ifinure"), rs.getString("esiu_remark"),
						rs.getString("esiu_addname"),
						rs.getString("esiu_addtime"), rs.getInt("esiu_single"),
						rs.getString("esiu_worker"),
						rs.getString("esiu_officialrank"), "", "", 0, 0, "",
						"", 0, "", "");
				list.add(m);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}

	// 根据GID获取员工社保所有变更信息
	public List<EmSheBaoChangeModel> getAllChangListByGid(int gid) {
		List<EmSheBaoChangeModel> list = new ArrayList<EmSheBaoChangeModel>();
		EmSheBaoChangeModel m = null;
		StringBuilder sql = new StringBuilder();
		sql.append("select a.*,b.msg_a from ");
		sql.append("(SELECT type='1',ID,emsc_Shortname,emsc_Name,emsc_Single,Ownmonth,emsc_Change,emsc_Content,emsc_AddTime,emsc_AddName,emsc_IfDeclare,case emsc_change when'停交' then emsc_stopreason+' '+isnull(emsc_Remark,'') else emsc_Remark end as emsc_Remark,emsc_radix,emsc_idcard,emsc_computerid,emsc_declarename,emsc_declaretime,emsc_hj,emsc_yl,emsc_gs,emsc_sye,emsc_syu,emsc_yltype FROM EmShebaochange where gid=? ");
		sql.append("union all SELECT type='2',escs_id,escs_Company,escs_Name,escs_Single,Ownmonth,escs_Change,escs_Content,escs_Addtime,escs_Addname,escs_IfDeclare,escs_Remark,null as radix,null as idcard,null as computerid,escs_declarename,escs_declaretime,null as hj,null as emsc_yl,null as emsc_gs,null as emsc_sye,null as emsc_syu,null as emsc_yltype FROM EmShebaoChangeSZSI where gid=? ");
		sql.append("union all SELECT type='3',ID,emsc_Shortname,emsc_Name,emsc_Single,Ownmonth,emsc_Change,emsc_Content,emsc_AddTime,emsc_AddName,emsc_IfDeclare,emsc_Remark,emsc_radix,emsc_idcard,emsc_computerid,emsc_declarename,emsc_declaretime,emsc_hj,emsc_yl,emsc_gs,emsc_sye,emsc_syu,emsc_yltype FROM EmShebaoChangeForeigner where gid=? ");
		sql.append("union all SELECT type='4',ID,emsb_company,emsb_name,emsb_single,Ownmonth,'补缴','补缴'+CONVERT(varchar(10),emsb_startmonth)+'社保',emsb_addtime,emsb_addname,emsb_Ifdeclare,emsb_remark,emsb_radix,emsb_idcard,emsb_computerid,emsb_declarename,emsb_declaretime,emsb_hj,null as emsc_yl,null as emsc_gs,null as emsc_sye,null as emsc_syu,null as emsc_yltype FROM EmShebaoBJ where (emsb_tapr_id<>0 or emsb_tapr_id is null) and gid=?)a ");
		sql.append(" left join ");
		sql.append("(select smwr_tid,case when syme_log_id=250 then 2 when symr_log_id=250 then symr_readstate else 1 end msg_a,case smwr_table when 'EmSheBaoChange' then 1 when 'EmShebaoChangeSZSI' then 2 when 'EmShebaoChangeForeigner' then 3 when 'EmShebaoBJ' then 4 end as type from View_Message where EXISTS(select syme_id from (select smwr_tid,MAX(syme_id)syme_id from View_Message where syme_state=1  and smwr_table in('EmSheBaoChange','EmShebaoChangeSZSI','EmShebaoChangeForeigner','EmShebaoBJ') group by smwr_tid)msg where View_Message.syme_id=msg.syme_id))b");
		sql.append(" on a.type=b.type and a.ID=b.smwr_tid ");
		sql.append("order by ownmonth desc,emsc_AddTime desc");

		PreparedStatement psmt = conn.getpre(sql.toString());
		try {
			psmt.setInt(1, gid);
			psmt.setInt(2, gid);
			psmt.setInt(3, gid);
			psmt.setInt(4, gid);
			ResultSet rs = psmt.executeQuery();
			while (rs.next()) {
				m = new EmSheBaoChangeModel();
				m.setChangetype(rs.getInt("type"));
				m.setId(rs.getInt("id"));
				m.setEmsc_shortname(rs.getString("emsc_Shortname"));
				m.setEmsc_name(rs.getString("emsc_name"));
				m.setEmsc_single(rs.getInt("emsc_Single"));
				m.setOwnmonth(rs.getInt("ownmonth"));
				m.setEmsc_change(rs.getString("emsc_change"));
				m.setEmsc_content(rs.getString("emsc_content"));
				m.setEmsc_addtime(rs.getString("emsc_addtime"));
				m.setEmsc_addname(rs.getString("emsc_addname"));
				m.setEmsc_ifdeclareInt(rs.getInt("emsc_ifdeclare"));
				m.setEmsc_remark(rs.getString("emsc_remark"));
				m.setMsg_a(rs.getString("msg_a"));
				m.setEmsc_radix(rs.getInt("emsc_radix"));
				m.setEmsc_idcard(rs.getString("emsc_idcard"));
				m.setEmsc_computerid(rs.getString("emsc_computerid"));
				m.setEmsc_declarename(rs.getString("emsc_declarename"));
				m.setEmsc_declaretime(rs.getString("emsc_declaretime"));
				m.setEmsc_hj(rs.getString("emsc_hj"));
				m.setEmsc_yl(rs.getString("emsc_yl"));
				m.setEmsc_yltype(rs.getString("emsc_yltype"));
				m.setEmsc_syu(rs.getString("emsc_syu"));
				m.setEmsc_gs(rs.getString("emsc_gs"));
				m.setEmsc_sye(rs.getString("emsc_sye"));
				list.add(m);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}

	// 获取社保补交数据(单表)
	public List<EmShebaoBJModel> getEmSBjData(String str, String order) {
		List<EmShebaoBJModel> list = new ArrayList<EmShebaoBJModel>();
		EmShebaoBJModel m = null;
		String sql = "select * from EmShebaoBJ as a inner join CoBase as c on a.cid=c.cid inner join EmBase as d on a.gid=d.gid  where 1=1 "
				+ str + order;
		try {

			ResultSet rs = conn.GRS(sql);
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
				m.setCoba_client(rs.getString("coba_client"));
				list.add(m);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}

	// 获取社保外籍人新参保数据
	public List<EmShebaoChangeForeignerModel> getEmSForCData(String str,
			String order) {
		List<EmShebaoChangeForeignerModel> list = new ArrayList<EmShebaoChangeForeignerModel>();
		EmShebaoChangeForeignerModel m = null;
		String sql = "select * from EmShebaoChangeForeigner as a inner join EmShebaoUpdate as b on a.gid=b.gid inner join CoBase as c on a.cid=c.cid inner join EmBase as d on a.gid=d.gid  where 1=1 "
				+ str + order;
		try {

			ResultSet rs = conn.GRS(sql);
			while (rs.next()) {
				m = new EmShebaoChangeForeignerModel();
				m.setId(rs.getInt("id"));
				m.setCid(rs.getInt("cid"));
				m.setGid(rs.getInt("gid"));
				m.setOwnmonth(rs.getInt("ownmonth"));
				m.setEmsc_formula(rs.getString("emsc_formula"));
				m.setEmsc_company(rs.getString("coba_company"));
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
				m.setEmsc_tapr_id(rs.getInt("emsc_tapr_id"));
				m.setEmba_birth(rs.getString("emba_birth"));
				m.setEmba_sex(rs.getString("emba_sex"));
				list.add(m);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}

	// 获取社保外籍人新参保数据
	public List<EmShebaoChangeForeignerModel> getEmSForCData(String top,
			String str, String order) {
		List<EmShebaoChangeForeignerModel> list = new ArrayList<EmShebaoChangeForeignerModel>();
		EmShebaoChangeForeignerModel m = null;
		String sql = "SELECT "
				+ top
				+ " sb.*,isnull(sbid.coco_shebaoID,0)coco_shebaoid,isnull(msg_a.msg_a,-1)msg_a ";
		sql = sql
				+ " from (select a.*,b.coba_client,b.coba_company,convert(varchar(10),c.emba_birth,120)as emba_birth,c.emba_sex from EmShebaoChangeForeigner as a inner join CoBase as b on a.cid=b.cid inner join embase as c on a.gid=c.gid)sb ";
		sql = sql
				+ " left join (select c.gid,c.cid,d.coco_shebaoid from (select a.gid,a.cid,cf.coof_coco_id from CoGList a left join CoOfferList b on a.cgli_coli_id=b.coli_id left join CoOffer cf on b.coli_coof_id=cf.coof_id where b.coli_state=1 and b.coli_name='社会保险服务' group by a.gid,a.cid,cf.coof_coco_id)c inner join cocompact_single1_v d on c.coof_coco_id=d.coco_id where d.coco_shebaoid is not null group by c.gid,c.cid,d.coco_shebaoid)sbid on sb.GID=sbid.gid AND sb.cid=sbid.cid ";
		sql = sql
				+ " left join (select smwr_tid,case when syme_log_id="
				+ UserInfo.getUserid()
				+ " then 2 when symr_log_id="
				+ UserInfo.getUserid()
				+ " then symr_readstate else 1 end msg_a from View_Message where EXISTS(select syme_id from (select smwr_tid,MAX(syme_id)syme_id from View_Message where syme_state=1 and  smwr_table='EmShebaoChangeForeigner' group by smwr_tid)msg where View_Message.syme_id=msg.syme_id))msg_a on sb.id=msg_a.smwr_tid ";
		sql = sql + " where sb.emsc_ifdeclare not in(4,7) " + str + " " + order;
		try {

			ResultSet rs = conn.GRS(sql);
			while (rs.next()) {
				m = new EmShebaoChangeForeignerModel();
				m.setId(rs.getInt("id"));
				m.setCid(rs.getInt("cid"));
				m.setGid(rs.getInt("gid"));
				m.setOwnmonth(rs.getInt("ownmonth"));
				m.setEmsc_formula(rs.getString("emsc_formula"));
				m.setEmsc_company(rs.getString("coba_company"));
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
				m.setEmsc_client(rs.getString("coba_client"));
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
				m.setEmsc_tapr_id(rs.getInt("emsc_tapr_id"));
				m.setEmba_birth(rs.getString("emba_birth"));
				m.setEmba_sex(rs.getString("emba_sex"));
				m.setCoco_shebaoid(rs.getString("coco_shebaoid"));
				m.setMsg_a(rs.getString("msg_a"));
				m.setEmsc_confirmtime(rs.getString("emsc_confirmtime"));
				list.add(m);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}

	// 获取社保生育津贴申请数据(多表)
	public List<EmShebaoChangeMAModel> getEscmMACData(String top, String str,
			String order) {
		List<EmShebaoChangeMAModel> list = new ArrayList<EmShebaoChangeMAModel>();
		EmShebaoChangeMAModel m = null;
		String sql = "SELECT "
				+ top
				+ " sb.*,isnull(sbid.coco_shebaoID,0)coco_shebaoid,isnull(msg_a.msg_a,-1)msg_a ";
		sql = sql
				+ " from (select a.*,convert(varchar(10),a.escm_declareTime,120) as declareTime,convert(varchar(10),a.escm_def_filetime,120) as def_filetime,convert(varchar(10),a.escm_fd_ctime,120) as fd_ctime,b.coba_client,b.coba_company,convert(varchar(10),c.emba_birth,120)as emba_birth from EmShebaoChangeMA as a inner join CoBase as b on a.cid=b.cid inner join embase as c on a.gid=c.gid)sb ";
		sql = sql
				+ " left join (select c.gid,c.cid,d.coco_shebaoid from (select a.gid,a.cid,cf.coof_coco_id from CoGList a left join CoOfferList b on a.cgli_coli_id=b.coli_id left join CoOffer cf on b.coli_coof_id=cf.coof_id where b.coli_state=1 and b.coli_name='社会保险服务' group by a.gid,a.cid,cf.coof_coco_id)c inner join cocompact_single1_v d on c.coof_coco_id=d.coco_id where d.coco_shebaoid is not null group by c.gid,c.cid,d.coco_shebaoid)sbid on sb.GID=sbid.gid AND sb.cid=sbid.cid ";
		sql = sql
				+ " left join (select smwr_tid,case when syme_log_id="
				+ UserInfo.getUserid()
				+ " then 2 when symr_log_id="
				+ UserInfo.getUserid()
				+ " then symr_readstate else 1 end msg_a from View_Message where EXISTS(select syme_id from (select smwr_tid,MAX(syme_id)syme_id from View_Message where syme_state=1 and  smwr_table='EmShebaoChangeMA' group by smwr_tid)msg where View_Message.syme_id=msg.syme_id))msg_a on sb.id=msg_a.smwr_tid ";
		sql = sql + " where sb.escm_ifdeclare!=4 " + str + " " + order;
		try {

			ResultSet rs = conn.GRS(sql);
			while (rs.next()) {
				m = new EmShebaoChangeMAModel();
				m.setId(rs.getInt("id"));
				m.setCid(rs.getInt("cid"));
				m.setGid(rs.getInt("gid"));
				m.setOwnmonth(rs.getInt("ownmonth"));
				m.setMsg_a(rs.getString("msg_a"));

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
				m.setEscm_declaretime(rs.getString("declaretime"));
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
				m.setCoco_shebaoid(rs.getString("Coco_shebaoid"));
				m.setEscm_client(rs.getString("coba_client"));
				m.setEscm_flag(rs.getString("escm_flag"));
				m.setEscm_remark(rs.getString("escm_remark"));
				m.setEscm_ifdata(rs.getInt("escm_ifdata"));
				m.setEscm_batchnum(rs.getString("escm_batchnum"));
				m.setEscm_af_filename(rs.getString("escm_af_filename"));
				m.setEscm_bf_filename(rs.getString("escm_bf_filename"));
				m.setEscm_def_filename(rs.getString("escm_def_filename"));
				m.setEscm_fee(rs.getBigDecimal("escm_fee"));
				m.setEscm_fd_ctime(rs.getString("fd_ctime"));
				m.setEscm_def_filetime(rs.getString("def_filetime"));
				list.add(m);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}

	public String[] getChangeDataCount(String ownmonth) {
		String[] count = new String[24];

		String sql = "select (select count(*) from EmShebaoChange where ownmonth="
				+ ownmonth
				+ ")c0,(select count(*) from EmShebaoChange where emsc_ifdeclare<>0 and emsc_ifdeclare<>4 and emsc_ifdeclare<>5 and ownmonth="
				+ ownmonth
				+ ")c1,(select count(*) from EmShebaoChange where emsc_single=0 and ownmonth="
				+ ownmonth
				+ ")c2,(select count(*) from EmShebaoChange where emsc_single=2 and ownmonth="
				+ ownmonth
				+ ")c3,(select count(*) from EmShebaoChange where emsc_single=1 and ownmonth="
				+ ownmonth
				+ ")c4,(select count(*) from EmShebaoChange where emsc_ifsame=1 and ownmonth="
				+ ownmonth
				+ ")c5,(select count(*) from EmShebaoChange where emsc_ifmodify=1 and ownmonth="
				+ ownmonth
				+ ")c6,(select count(*) from EmShebaoChange where emsc_ifwrong=1 and ownmonth="
				+ ownmonth
				+ ")c7,(select count(*) from EmShebaoChange where emsc_ifdeclare=1 and emsc_single=0 and ownmonth="
				+ ownmonth
				+ ")c8,(select count(*) from EmShebaoChange where emsc_ifdeclare=0 and emsc_single=0 and ownmonth="
				+ ownmonth
				+ ")c9,(select count(*) from EmShebaoChange where emsc_ifdeclare=2 and emsc_single=0 and ownmonth="
				+ ownmonth
				+ ")c10,(select count(*) from EmShebaoChange where emsc_ifdeclare=1 and emsc_single=2 and ownmonth="
				+ ownmonth
				+ ")c11,(select count(*) from EmShebaoChange where emsc_ifdeclare=0 and emsc_single=2 and ownmonth="
				+ ownmonth
				+ ")c12,(select count(*) from EmShebaoChange where emsc_ifdeclare=2 and emsc_single=2 and ownmonth="
				+ ownmonth
				+ ")c13,(select count(*) from EmShebaoChange where emsc_single=3 and ownmonth="
				+ ownmonth
				+ ")c14,(select count(*) from EmShebaoChange where emsc_single=4 and ownmonth="
				+ ownmonth
				+ ")c15,(select count(*) from EmShebaoChange where emsc_single=3 and ownmonth="
				+ ownmonth
				+ ")c16,(select count(*) from EmShebaoChange where emsc_ifdeclare=1 and emsc_single=3 and ownmonth="
				+ ownmonth
				+ ")c17,(select count(*) from EmShebaoChange where emsc_ifdeclare=0 and emsc_single=3 and ownmonth="
				+ ownmonth
				+ ")c18,(select count(*) from EmShebaoChange where emsc_ifdeclare=2 and emsc_single=3 and ownmonth="
				+ ownmonth
				+ ")c19,(select count(*) from EmShebaoChange where emsc_single=4 and ownmonth="
				+ ownmonth
				+ ")c20,(select count(*) from EmShebaoChange where emsc_ifdeclare=1 and emsc_single=4 and ownmonth="
				+ ownmonth
				+ ")c21,(select count(*) from EmShebaoChange where emsc_ifdeclare=0 and emsc_single=4 and ownmonth="
				+ ownmonth
				+ ")c22,(select count(*) from EmShebaoChange where emsc_ifdeclare=2 and emsc_single=4 and ownmonth="
				+ ownmonth + ")c23";
		try {

			ResultSet rs = conn.GRS(sql);
			while (rs.next()) {
				count[0] = rs.getString("c0");
				count[1] = rs.getString("c1");
				count[2] = rs.getString("c2");
				count[3] = rs.getString("c3");
				count[4] = rs.getString("c4");
				count[5] = rs.getString("c5");
				count[6] = rs.getString("c6");
				count[7] = rs.getString("c7");
				count[8] = rs.getString("c8");
				count[9] = rs.getString("c9");
				count[10] = rs.getString("c10");
				count[11] = rs.getString("c11");
				count[12] = rs.getString("c12");
				count[13] = rs.getString("c13");
				count[14] = rs.getString("c14");
				count[15] = rs.getString("c15");
				count[16] = rs.getString("c16");
				count[17] = rs.getString("c17");
				count[18] = rs.getString("c18");
				count[19] = rs.getString("c19");
				count[20] = rs.getString("c20");
				count[21] = rs.getString("c21");
				count[22] = rs.getString("c22");
				count[23] = rs.getString("c23");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return count;
	}

	public String[] getSZSIChangeDataCount(String ownmonth) {
		String[] count = new String[11];

		String sql = "select (select count(*) from EmShebaoChangeSZSI where ownmonth="
				+ ownmonth
				+ ")c0,(select count(*) from EmShebaoChangeSZSI where escs_ifdeclare not in(0,4,5,7,8) and ownmonth="
				+ ownmonth
				+ ")c1,(select count(*) from EmShebaoChangeSZSI where escs_single=0 and ownmonth="
				+ ownmonth
				+ ")c2,(select count(*) from EmShebaoChangeSZSI where escs_single=2 and ownmonth="
				+ ownmonth
				+ ")c3,(select count(*) from EmShebaoChangeSZSI where escs_single=1 and ownmonth="
				+ ownmonth
				+ ")c4,(select count(*) from EmShebaoChangeSZSI where escs_ifdeclare=1 and escs_single=0 and ownmonth="
				+ ownmonth
				+ ")c5,(select count(*) from EmShebaoChangeSZSI where escs_ifdeclare=0 and escs_single=0 and ownmonth="
				+ ownmonth
				+ ")c6,(select count(*) from EmShebaoChangeSZSI where escs_ifdeclare=2 and escs_single=0 and ownmonth="
				+ ownmonth
				+ ")c7,(select count(*) from EmShebaoChangeSZSI where escs_ifdeclare=1 and escs_single=2 and ownmonth="
				+ ownmonth
				+ ")c8,(select count(*) from EmShebaoChangeSZSI where escs_ifdeclare=0 and escs_single=2 and ownmonth="
				+ ownmonth
				+ ")c9,(select count(*) from EmShebaoChangeSZSI where escs_ifdeclare=2 and escs_single=2 and ownmonth="
				+ ownmonth + ")c10";
		try {

			ResultSet rs = conn.GRS(sql);
			while (rs.next()) {
				count[0] = rs.getString("c0");
				count[1] = rs.getString("c1");
				count[2] = rs.getString("c2");
				count[3] = rs.getString("c3");
				count[4] = rs.getString("c4");
				count[5] = rs.getString("c5");
				count[6] = rs.getString("c6");
				count[7] = rs.getString("c7");
				count[8] = rs.getString("c8");
				count[9] = rs.getString("c9");
				count[10] = rs.getString("c10");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return count;
	}

	public String[] getForeignerDataCount(String ownmonth) {
		String[] count = new String[2];

		String sql = "select (select count(*) from EmShebaoChangeForeigner where ownmonth="
				+ ownmonth
				+ ")c0,(select count(*) from EmShebaoChangeForeigner where emsc_ifdeclare<>0 and ownmonth="
				+ ownmonth + ")c1";
		try {

			ResultSet rs = conn.GRS(sql);
			while (rs.next()) {
				count[0] = rs.getString("c0");
				count[1] = rs.getString("c1");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return count;
	}

	public String[] getEscmMADataCount(String ownmonth) {
		String[] count = new String[2];

		String sql = "select (select count(*) from EmShebaoChangeMA where ownmonth="
				+ ownmonth
				+ ")c0,(select count(*) from EmShebaoChangeMA where escm_ifdeclare<>0 and ownmonth="
				+ ownmonth + ")c1";
		try {

			ResultSet rs = conn.GRS(sql);
			while (rs.next()) {
				count[0] = rs.getString("c0");
				count[1] = rs.getString("c1");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return count;
	}

	public String folk(String str) {
		String result = "";
		String sql = "SELECT * FROM PubFolk WHERE pufo_name='" + str + "'";
		try {

			ResultSet rs = conn.GRS(sql);
			while (rs.next()) {
				result = rs.getString("pufo_id");
			}
		} catch (Exception e) {
			e.printStackTrace();
			result = "err";
		}
		return result;
	}

	public List<EmSheBaoChangeModel> getEmSCExcelData(String filename) {
		List<EmSheBaoChangeModel> list = new ArrayList<EmSheBaoChangeModel>();
		EmSheBaoChangeModel m = null;
		String sql = "Select b.coba_shortname,c.emsc_name,sid,c.emsc_tapr_id from EmShebaoExcel as a inner join CoBase as b on a.cid=b.cid inner join EmShebaoChange as c on a.sid=c.id  where a.emex_groupname='"
				+ filename + "'";
		try {

			ResultSet rs = conn.GRS(sql);
			while (rs.next()) {
				m = new EmSheBaoChangeModel();
				m.setId(rs.getInt("sid"));
				m.setEmsc_shortname(rs.getString("coba_shortname"));
				m.setEmsc_name(rs.getString("emsc_name"));
				m.setEmsc_tapr_id(rs.getInt("emsc_tapr_id"));
				list.add(m);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}

	public List<EmShebaoChangeForeignerModel> getEmForSCExcelData(
			String filename) {
		List<EmShebaoChangeForeignerModel> list = new ArrayList<EmShebaoChangeForeignerModel>();
		EmShebaoChangeForeignerModel m = null;
		String sql = "select * from EmShebaoChangeForeigner where emsc_excelfile='"
				+ filename + "'";
		try {

			ResultSet rs = conn.GRS(sql);
			while (rs.next()) {
				m = new EmShebaoChangeForeignerModel();
				m.setId(rs.getInt("id"));
				m.setEmsc_shortname(rs.getString("emsc_company"));
				m.setEmsc_name(rs.getString("emsc_name"));
				m.setEmsc_tapr_id(rs.getInt("emsc_tapr_id"));
				list.add(m);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}

	public List<EmShebaoDeclareOKModel> getEsdoData(String filename) {
		List<EmShebaoDeclareOKModel> list = new ArrayList<EmShebaoDeclareOKModel>();
		EmShebaoDeclareOKModel m = null;

		String sql = "select a.*,b.CID,b.GID from EmShebaoDeclareOK a left join EmShebaoUpdate b on a.esdo_computerid=b.esiu_computerid where a.esdo_filename='"
				+ filename + "'";
		try {

			ResultSet rs = conn.GRS(sql);
			while (rs.next()) {
				m = new EmShebaoDeclareOKModel();
				m.setId(rs.getInt("id"));
				m.setOwnmonth(rs.getInt("ownmonth"));
				m.setEsdo_name(rs.getString("esdo_name"));
				m.setEsdo_computerid(rs.getString("esdo_computerid"));
				m.setEsdo_idcard(rs.getString("esdo_idcard"));
				m.setEsdo_hj(rs.getString("esdo_hj"));
				m.setEsdo_worker(rs.getString("esdo_worker"));
				m.setEsdo_radix(rs.getInt("esdo_radix"));
				m.setEsdo_yl(rs.getString("esdo_yl"));
				m.setEsdo_yltype(rs.getString("esdo_yltype"));
				m.setEsdo_gs(rs.getString("esdo_gs"));
				m.setEsdo_house(rs.getString("esdo_house"));
				m.setEsdo_sye(rs.getString("esdo_sye"));
				m.setEsdo_oktime(rs.getString("esdo_oktime"));
				m.setEsdo_class(rs.getString("esdo_class"));
				m.setEsdo_addtime(rs.getString("esdo_addtime"));
				m.setEsdo_addname(rs.getString("esdo_addname"));
				m.setEsdo_filename(rs.getString("esdo_filename"));
				m.setEsdo_ifupdate(rs.getInt("esdo_ifupdate"));
				m.setCid(rs.getInt("cid"));
				m.setGid(rs.getInt("gid"));
				list.add(m);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}

	public int getTapr_id(int esdo_id) {
		try {
			CallableStatement c = conn.getcall("EMSI_GetTaprID_P_lsb(?,?)");
			c.setInt(1, esdo_id);
			c.registerOutParameter(2, java.sql.Types.INTEGER);
			c.execute();
			return c.getInt(2);
		} catch (SQLException e) {
			e.printStackTrace();
			return 0;
		}
	}

	// 获取台账前数据
	public List<EmSheBaoFinanceModel> getSZSIFinanceMonth(String ownmonth) {
		List<EmSheBaoFinanceModel> list = new ArrayList<EmSheBaoFinanceModel>();
		EmSheBaoFinanceModel m = null;

		String sql = "select (select count(*) from EmShebaoSZSIMonth where single=1 and ownmonth="
				+ ownmonth
				+ ")szsi_1_cou,(select count(*) from EmShebaoSZSIMonth where single=0 and ownmonth="
				+ ownmonth
				+ ")szsi_0_cou,(select count(*) from EmShebaoSZSIMonth where single=2 and ownmonth="
				+ ownmonth
				+ ")szsi_2_cou,(select count(*) from EmShebaoSZSIMonth where ownmonth="
				+ ownmonth
				+ ")szsi_a_cou,(select count(*) from EmShebaoUpdate where esiu_single=0 and ownmonth="
				+ ownmonth
				+ " and esiu_ifstop=0)sb_0_cou,(select count(*) from EmShebaoUpdate where esiu_single=2 and ownmonth="
				+ ownmonth + " and esiu_ifstop=0)sb_2_cou";
		try {
			ResultSet rs = conn.GRS(sql);
			while (rs.next()) {
				m = new EmSheBaoFinanceModel();
				m.setSzsi_1_cou(rs.getInt("szsi_1_cou"));
				m.setSzsi_0_cou(rs.getInt("szsi_0_cou"));
				m.setSzsi_2_cou(rs.getInt("szsi_2_cou"));
				m.setShebao_count(rs.getInt("szsi_a_cou"));
				m.setSb_0_cou(rs.getInt("sb_0_cou"));
				m.setSb_2_cou(rs.getInt("sb_2_cou"));
				m.setCe_0_cou(rs.getInt("szsi_0_cou") - rs.getInt("sb_0_cou"));
				m.setCe_2_cou(rs.getInt("szsi_2_cou") - rs.getInt("sb_2_cou"));
				list.add(m);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}

	// 获取独立户台账前数据
	public List<EmSheBaoFinanceModel> getSZSIFinanceSingleMonth(String ownmonth) {
		List<EmSheBaoFinanceModel> list = new ArrayList<EmSheBaoFinanceModel>();
		EmSheBaoFinanceModel m = null;
		/*
		 * String sql =
		 * " select a.cid,a.ownmonth,a.coco_shebaoID as shebaoid,isnull(a.sb_cou,0)sb_cou,isnull(b.szsi_cou,0)szsi_cou,e.coba_company as company,e.coba_client,b.addname,b.addtime from (select c.CID,c.Ownmonth,d.coco_shebaoID,COUNT(*)sb_cou from EmShebaoUpdate c left join (select cid,coco_shebaoID from cocompact_single1_v where coco_shebao='独立开户' group by cid,coco_shebaoid)d on c.CID=d.cid where c.esiu_single=1 and c.esiu_ifstop=0 group by c.CID,c.Ownmonth,d.coco_shebaoID)a left join (select CID,shebaoid,Ownmonth,addname,addtime,count(*)szsi_cou from EmShebaoSZSIMonth where single=1 and ownmonth="
		 * + ownmonth +
		 * " group by cid,shebaoid,Ownmonth,addname,addtime)b on a.cid=b.cid and a.ownmonth=b.ownmonth and a.coco_shebaoID=b.shebaoid left join cobase e on a.cid=e.cid where a.Ownmonth="
		 * + ownmonth;
		 */
		String sql = "select sb.cid,sb.Ownmonth,sb.shebaoid,sb.sb_cou,szsi.szsi_cou,sb.company,sb.coba_client,sb.addname,sb.addtime from (select cid=Esiu_single,ownmonth,case Esiu_single when 0 then 391055 when 2 then 167120 end as shebaoid,Esiu_single,COUNT(*)sb_cou,case Esiu_single when 0 then '深圳中智经济技术合作有限公司' when 2 then '深圳中智经济技术合作有限公司(委托)' end AS company,coba_client=null,addname=null,addtime=null from EmShebaoUpdate where Esiu_single in(0,2) and esiu_ifstop=0 and Ownmonth="
				+ ownmonth
				+ " GROUP by Ownmonth,Esiu_single)sb left join (select single,COUNT(*)szsi_cou from EmShebaoSZSIMonth where single in(0,2) and Ownmonth="
				+ ownmonth
				+ " GROUP by Ownmonth,single)szsi on sb.Esiu_single=szsi.single "
				+ "union all "
				+ "select a.cid,a.ownmonth,a.coco_shebaoID as shebaoid,isnull(a.sb_cou,0)sb_cou,isnull(b.szsi_cou,0)szsi_cou,e.coba_company as company,e.coba_client,b.addname,b.addtime "
				+ "from ("
				+ "	select c.CID,c.Ownmonth,d.coco_shebaoID,COUNT(*)sb_cou "
				+ "	from EmShebaoUpdate c "
				+ "	left join ("
				+ "	select cid,coco_shebaoID "
				+ "	from cocompact_single1_v "
				+ "	where coco_shebao='独立开户' and coco_shebaoid is not null "
				+ "	group by cid,coco_shebaoid"
				+ ")d on c.CID=d.cid "
				+ "where c.esiu_single=1 and c.esiu_ifstop=0 "
				+ "group by c.CID,c.Ownmonth,d.coco_shebaoID"
				+ ")a left join ("
				+ "	select CID,shebaoid,Ownmonth,addname,addtime,count(*)szsi_cou from EmShebaoSZSIMonth where single=1 and ownmonth="
				+ ownmonth
				+ " group by cid,shebaoid,Ownmonth,addname,addtime"
				+ ")b on a.cid=b.cid and a.ownmonth=b.ownmonth and a.coco_shebaoID=b.shebaoid left join cobase e on a.cid=e.cid where a.Ownmonth="
				+ ownmonth;
		try {
			System.out.println(sql);
			ResultSet rs = conn.GRS(sql);
			while (rs.next()) {
				m = new EmSheBaoFinanceModel();
				m.setCid(rs.getInt("cid"));
				m.setOwnmonth(rs.getInt("ownmonth"));
				m.setCompany(rs.getString("company"));
				m.setShebaoid(rs.getString("shebaoid"));
				m.setClient(rs.getString("coba_client"));
				m.setAddname(rs.getString("addname"));
				m.setSzsi_1_cou(rs.getInt("szsi_cou"));
				m.setSb_1_cou(rs.getInt("sb_cou"));
				m.setCe_1_cou(rs.getInt("szsi_cou") - rs.getInt("sb_cou"));
				list.add(m);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}

	// 获取台账后数据
	public List<EmSheBaoFinanceModel> getSZSIFinance(String ownmonth) {
		List<EmSheBaoFinanceModel> list = new ArrayList<EmSheBaoFinanceModel>();
		EmSheBaoFinanceModel m = null;

		String sql = "select (select count(*) from EmShebaoSZSI where single=1 and ownmonth="
				+ ownmonth
				+ ")szsi_1_cou,(select count(*) from EmShebaoSZSI where single=0 and ownmonth="
				+ ownmonth
				+ ")szsi_0_cou,(select count(*) from EmShebaoSZSI where single=2 and ownmonth="
				+ ownmonth
				+ ")szsi_2_cou,(select count(*) from EmShebaoSZSI where ownmonth="
				+ ownmonth
				+ ")szsi_a_cou,(select count(*) from EmShebaoUpdate where esiu_single=0 and ownmonth="
				+ ownmonth
				+ " and esiu_ifstop=0)sb_0_cou,(select count(*) from EmShebaoUpdate where esiu_single=2 and ownmonth="
				+ ownmonth
				+ " and esiu_ifstop=0)sb_2_cou,(select isnull(sum(total),0) from EmShebaoSZSI where single=0 and ownmonth="
				+ ownmonth
				+ ")szsi_0_cost,(select isnull(sum(total),0) from EmShebaoSZSI where single=2 and ownmonth="
				+ ownmonth
				+ ")szsi_2_cost,(select isnull(sum(esiu_totalop+esiu_totalcp),0) from EmShebaoUpdate where esiu_single=0 and ownmonth="
				+ ownmonth
				+ " and esiu_ifstop=0)sb_0_cost,(select isnull(sum(esiu_totalop+esiu_totalcp),0) from EmShebaoUpdate where esiu_single=2 and ownmonth="
				+ ownmonth + " and esiu_ifstop=0)sb_2_cost";
		try {

			ResultSet rs = conn.GRS(sql);
			while (rs.next()) {
				m = new EmSheBaoFinanceModel();
				m.setSzsi_1_cou(rs.getInt("szsi_1_cou"));
				m.setSzsi_0_cou(rs.getInt("szsi_0_cou"));
				m.setSzsi_2_cou(rs.getInt("szsi_2_cou"));
				m.setShebao_count(rs.getInt("szsi_a_cou"));
				m.setSb_0_cou(rs.getInt("sb_0_cou"));
				m.setSb_2_cou(rs.getInt("sb_2_cou"));
				m.setCe_0_cou(rs.getInt("szsi_0_cou") - rs.getInt("sb_0_cou"));
				m.setCe_2_cou(rs.getInt("szsi_2_cou") - rs.getInt("sb_2_cou"));
				m.setSzsi_0_cost(rs.getBigDecimal("szsi_0_cost"));
				m.setSzsi_2_cost(rs.getBigDecimal("szsi_2_cost"));
				m.setSb_0_cost(rs.getBigDecimal("sb_0_cost"));
				m.setSb_2_cost(rs.getBigDecimal("sb_2_cost"));
				m.setCe_0_cost(rs.getBigDecimal("szsi_0_cost").subtract(
						rs.getBigDecimal("sb_0_cost")));
				m.setCe_2_cost(rs.getBigDecimal("szsi_2_cost").subtract(
						rs.getBigDecimal("sb_2_cost")));
				list.add(m);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}

	// 获取独立户台账后数据
	public List<EmSheBaoFinanceModel> getSZSIFinanceSingle(String ownmonth) {
		List<EmSheBaoFinanceModel> list = new ArrayList<EmSheBaoFinanceModel>();
		EmSheBaoFinanceModel m = null;
		/*
		 * String sql =
		 * "select a.cid,a.ownmonth,a.coco_shebaoID as shebaoid,isnull(a.sb_cou,0)sb_cou,isnull(a.sb_cost,0)sb_cost,isnull(b.szsi_cou,0)szsi_cou,isnull(b.szsi_cost,0)szsi_cost,e.coba_company as company,e.coba_client,b.addname,b.addtime from (select c.CID,c.Ownmonth,d.coco_shebaoID,COUNT(*)sb_cou,sum(esiu_totalcp+esiu_totalop)sb_cost from EmShebaoUpdate c left join (select cid,coco_shebaoID from cocompact_single1_v where coco_shebao='独立开户' group by cid,coco_shebaoid)d on c.CID=d.cid where c.esiu_single=1 and c.esiu_ifstop=0 group by c.CID,c.Ownmonth,d.coco_shebaoID)a left join (select CID,shebaoid,Ownmonth,addname,addtime,count(*)szsi_cou,sum(total)szsi_cost from EmShebaoSZSI where single=1 and ownmonth="
		 * + ownmonth +
		 * " group by cid,shebaoid,Ownmonth,addname,addtime)b on a.cid=b.cid and a.ownmonth=b.ownmonth and a.coco_shebaoID=b.shebaoid left join cobase e on a.cid=e.cid where a.Ownmonth="
		 * + ownmonth;
		 */
		String sql = "select sb.cid,sb.Ownmonth,sb.shebaoid,sb.sb_cou,sb.sb_cost,isnull(szsi.szsi_cou,0)szsi_cou,isnull(szsi.szsi_cost,0)szsi_cost,sb.company,sb.coba_client,sb.addname,sb.addtime from (select cid=Esiu_single,ownmonth,case Esiu_single when 0 then 391055 when 2 then 167120 end as shebaoid,Esiu_single,COUNT(*)sb_cou,sum(esiu_totalcp+esiu_totalop)sb_cost,case Esiu_single when 0 then '深圳中智经济技术合作有限公司' when 2 then '深圳中智经济技术合作有限公司(委托)' end AS company,coba_client=null,addname=null,addtime=null from EmShebaoUpdate where Esiu_single in(0,2) and esiu_ifstop=0 and Ownmonth="
				+ ownmonth
				+ " GROUP by Ownmonth,Esiu_single)sb left join (select single,COUNT(*)szsi_cou,sum(total)szsi_cost from EmShebaoszsi where single in(0,2) and Ownmonth="
				+ ownmonth
				+ " GROUP by Ownmonth,single)szsi on sb.Esiu_single=szsi.single union all select a.cid,a.ownmonth,a.coco_shebaoID as shebaoid,isnull(a.sb_cou,0)sb_cou,isnull(a.sb_cost,0)sb_cost,isnull(b.szsi_cou,0)szsi_cou,isnull(b.szsi_cost,0)szsi_cost,e.coba_company as company,e.coba_client,b.addname,b.addtime from (select c.CID,c.Ownmonth,d.coco_shebaoID,COUNT(*)sb_cou,sum(esiu_totalcp+esiu_totalop)sb_cost from EmShebaoUpdate c left join (select cid,coco_shebaoID from cocompact_single1_v where coco_shebao='独立开户' and coco_shebaoid is not null group by cid,coco_shebaoid)d on c.CID=d.cid where c.esiu_single=1 and c.esiu_ifstop=0 group by c.CID,c.Ownmonth,d.coco_shebaoID)a left join (select CID,shebaoid,Ownmonth,addname,addtime,count(*)szsi_cou,sum(total)szsi_cost from EmShebaoSZSI where single=1 and ownmonth="
				+ ownmonth
				+ " group by cid,shebaoid,Ownmonth,addname,addtime)b on a.cid=b.cid and a.ownmonth=b.ownmonth and a.coco_shebaoID=b.shebaoid left join cobase e on a.cid=e.cid where a.Ownmonth="
				+ ownmonth;

		try {

			ResultSet rs = conn.GRS(sql);
			while (rs.next()) {
				m = new EmSheBaoFinanceModel();
				m.setCid(rs.getInt("cid"));
				m.setOwnmonth(rs.getInt("ownmonth"));
				m.setCompany(rs.getString("company"));
				m.setShebaoid(rs.getString("shebaoid"));
				m.setClient(rs.getString("coba_client"));
				m.setAddname(rs.getString("addname"));
				m.setSzsi_1_cou(rs.getInt("szsi_cou"));
				m.setSb_1_cou(rs.getInt("sb_cou"));
				m.setCe_1_cou(rs.getInt("szsi_cou") - rs.getInt("sb_cou"));
				m.setSzsi_1_cost(rs.getBigDecimal("szsi_cost"));
				m.setSb_1_cost(rs.getBigDecimal("sb_cost"));
				m.setCe_1_cost(rs.getBigDecimal("szsi_cost").subtract(
						rs.getBigDecimal("sb_cost")));
				// m.setState(rs.getString("state"));
				list.add(m);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}

	// 台账前逻辑检查数据
	public List<EmShebaoErrModel> getShebaoErrMonth(String str) {
		List<EmShebaoErrModel> list = new ArrayList<EmShebaoErrModel>();
		EmShebaoErrModel m = null;

		String sql = "select *,isnull(a.emse_Solve,'') as solve from EmShebaoErrMonth as a left join CoBase as b on a.cid=b.cid where 1=1 "
				+ str + " order by emse_err";
		try {

			ResultSet rs = conn.GRS(sql);
			while (rs.next()) {
				m = new EmShebaoErrModel();
				m.setId(rs.getInt("id"));
				m.setCid(rs.getInt("cid"));
				m.setGid(rs.getInt("gid"));
				m.setOwnmonth(rs.getInt("ownmonth"));
				m.setCompany(rs.getString("coba_company"));
				m.setCoba_shortname(rs.getString("coba_shortname"));
				m.setCoba_client(rs.getString("coba_client"));
				m.setEmse_computerid(rs.getString("emse_computerid"));
				m.setEmse_idcard(rs.getString("emse_idcard"));
				m.setEmse_name(rs.getString("emse_name"));
				m.setEmse_err(rs.getString("emse_err"));
				m.setEmse_Remark(rs.getString("emse_Remark"));
				m.setEmse_Remind(rs.getInt("emse_Remind"));
				m.setEmse_addtime(rs.getString("emse_addtime"));
				m.setEmse_addname(rs.getString("emse_addname"));
				m.setEmse_Normal(rs.getInt("emse_Normal"));
				m.setEmse_Solve(rs.getString("solve"));
				m.setEmse_Note(rs.getString("emse_Note"));
				m.setEmse_SolveName(rs.getString("emse_SolveName"));
				m.setEmse_SolveTime(rs.getString("emse_SolveTime"));
				list.add(m);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}

	// 台账后逻辑检查数据
	public List<EmShebaoErrModel> getShebaoErr(String str) {
		List<EmShebaoErrModel> list = new ArrayList<EmShebaoErrModel>();
		EmShebaoErrModel m = null;

		String sql = "select *,isnull(a.emse_Solve,'') as solve from EmShebaoErr as a left join CoBase as b on a.cid=b.cid where 1=1 "
				+ str + " order by emse_err";
		try {
			ResultSet rs = conn.GRS(sql);
			while (rs.next()) {
				m = new EmShebaoErrModel();
				m.setId(rs.getInt("id"));
				m.setCid(rs.getInt("cid"));
				m.setGid(rs.getInt("gid"));
				m.setOwnmonth(rs.getInt("ownmonth"));
				m.setCompany(rs.getString("coba_company"));
				m.setCoba_shortname(rs.getString("coba_shortname"));
				m.setCoba_client(rs.getString("coba_client"));
				m.setEmse_computerid(rs.getString("emse_computerid"));
				m.setEmse_idcard(rs.getString("emse_idcard"));
				m.setEmse_name(rs.getString("emse_name"));
				m.setEmse_err(rs.getString("emse_err"));
				m.setEmse_Remark(rs.getString("emse_Remark"));
				m.setEmse_Remind(rs.getInt("emse_Remind"));
				m.setEmse_addtime(rs.getString("emse_addtime"));
				m.setEmse_addname(rs.getString("emse_addname"));
				m.setEmse_Normal(rs.getInt("emse_Normal"));
				m.setEmse_Solve(rs.getString("solve"));
				m.setEmse_Note(rs.getString("emse_Note"));
				m.setEmse_SolveName(rs.getString("emse_SolveName"));
				m.setEmse_SolveTime(rs.getString("emse_SolveTime"));
				list.add(m);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}

	// 台前数据统计
	public String[] getSZSIMonthCount(String ownmonth) {
		String[] count = new String[24];

		String sql = "select (select COUNT(*) from EmShebaoUpdate where Esiu_IfStop=0 and Ownmonth="
				+ ownmonth
				+ ")c0,(select COUNT(*) from EmShebaoSZSIMonth where ownmonth="
				+ ownmonth
				+ ")c1,(select COUNT(*) from EmShebaoUpdate where Esiu_IfStop=0 and Esiu_single=1 and Ownmonth="
				+ ownmonth
				+ ")c2,(select COUNT(*) from EmShebaoSZSIMonth where single=1 and ownmonth="
				+ ownmonth + ")c3";
		try {

			ResultSet rs = conn.GRS(sql);
			while (rs.next()) {
				count[0] = rs.getString("c0");
				count[1] = rs.getString("c1");
				count[2] = rs.getString("c2");
				count[3] = rs.getString("c3");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return count;
	}

	// 台后数据统计
	public String[] getSZSICount(String ownmonth) {
		String[] count = new String[4];

		String sql = "select (select COUNT(*) from EmShebaoUpdate where Esiu_IfStop=0 and Ownmonth="
				+ ownmonth
				+ ")c0,(select COUNT(*) from EmShebaoSZSI where ownmonth="
				+ ownmonth
				+ ")c1,(select COUNT(*) from EmShebaoUpdate where Esiu_IfStop=0 and Esiu_single=1 and Ownmonth="
				+ ownmonth
				+ ")c2,(select COUNT(*) from EmShebaoSZSI where single=1 and ownmonth="
				+ ownmonth + ")c3";
		try {

			ResultSet rs = conn.GRS(sql);
			while (rs.next()) {
				count[0] = rs.getString("c0");
				count[1] = rs.getString("c1");
				count[2] = rs.getString("c2");
				count[3] = rs.getString("c3");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return count;
	}

	public String getSheBaoIT() {
		String people = "";
		String sql = "select top 1 * from HandoverPeople where hape_channel='系统负责人' and hape_type='社保' and hape_state=1";
		try {
			ResultSet rs = conn.GRS(sql);
			while (rs.next()) {
				people = rs.getString("hape_username");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return people;
	}

	public String getSZSIFeeFileName(Integer shebaoid) {
		String filename = "";
		String sql = "select top 1 filename from EmShebaoSZSIfee where shebaoid is not null and shebaoid='"
				+ String.valueOf(shebaoid)
				+ "' and DATEDIFF(D,addtime,getdate())=0";
		try {
			ResultSet rs = conn.GRS(sql);
			while (rs.next()) {
				filename = rs.getString("filename");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return filename;
	}

	// 获取在册表数据
	public EmShebaoUpdateModel getShebaoUpdateInfo(String str) {
		EmShebaoUpdateModel m = new EmShebaoUpdateModel();
		String sql = "select * from EmShebaoUpdate where 1=1 " + str;
		try {// System.out.println(sql);
			ResultSet rs = conn.GRS(sql);
			if (rs.next()) {
				m.setEsiu_name(rs.getString("esiu_name"));
				m.setEsiu_lbid(rs.getString("esiu_lbid"));
				m.setEsiu_radix(rs.getInt("esiu_radix"));
				m.setEsiu_hj(rs.getString("esiu_hj"));
				m.setEsiu_yl(rs.getString("esiu_yl"));
				m.setEsiu_yltype(rs.getString("esiu_yltype"));
				m.setEsiu_gs(rs.getString("esiu_gs"));
				m.setEsiu_sye(rs.getString("esiu_sye"));
				m.setEsiu_syu(rs.getString("esiu_syu"));
				m.setEsiu_ylcp(rs.getBigDecimal("esiu_ylcp"));
				m.setEsiu_ylop(rs.getBigDecimal("esiu_ylop"));
				m.setEsiu_jlcp(rs.getBigDecimal("esiu_jlcp"));
				m.setEsiu_jlop(rs.getBigDecimal("esiu_jlop"));
				m.setEsiu_gscp(rs.getBigDecimal("esiu_gscp"));
				m.setEsiu_syecp(rs.getBigDecimal("esiu_syecp"));
				m.setEsiu_syucp(rs.getBigDecimal("esiu_syucp"));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return m;
	}

	// 获取在册备份表数据
	public EmShebaoUpdateModel getShebaoUpdateSimInfo(String str) {
		EmShebaoUpdateModel m = new EmShebaoUpdateModel();
		String sql = "select * from EmShebaoUpdateSim where 1=1 " + str;
		try {// System.out.println(sql);
			ResultSet rs = conn.GRS(sql);
			if (rs.next()) {
				m.setEsiu_name(rs.getString("esiu_name"));
				m.setEsiu_lbid(rs.getString("esiu_lbid"));
				m.setEsiu_radix(rs.getInt("esiu_radix"));
				m.setEsiu_hj(rs.getString("esiu_hj"));
				m.setEsiu_yl(rs.getString("esiu_yl"));
				m.setEsiu_yltype(rs.getString("esiu_yltype"));
				m.setEsiu_gs(rs.getString("esiu_gs"));
				m.setEsiu_sye(rs.getString("esiu_sye"));
				m.setEsiu_syu(rs.getString("esiu_syu"));
				m.setEsiu_ylcp(rs.getBigDecimal("esiu_ylcp"));
				m.setEsiu_ylop(rs.getBigDecimal("esiu_ylop"));
				m.setEsiu_jlcp(rs.getBigDecimal("esiu_jlcp"));
				m.setEsiu_jlop(rs.getBigDecimal("esiu_jlop"));
				m.setEsiu_gscp(rs.getBigDecimal("esiu_gscp"));
				m.setEsiu_syecp(rs.getBigDecimal("esiu_syecp"));
				m.setEsiu_syucp(rs.getBigDecimal("esiu_syucp"));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return m;
	}

	// 获取台账前数据
	public EmShebaoUpdateModel getShebaoSZSIMonthInfo(String str) {
		EmShebaoUpdateModel m = new EmShebaoUpdateModel();
		String sql = "select * from EmShebaoSZSImonth where 1=1 " + str;
		try {
			ResultSet rs = conn.GRS(sql);
			if (rs.next()) {
				m.setEsiu_name(rs.getString("name"));
				m.setEsiu_lbid(rs.getString("lbid"));
				m.setEsiu_radix(rs.getInt("radix"));
				m.setEsiu_hj(rs.getString("hj"));
				m.setEsiu_yl(rs.getString("yl"));
				m.setEsiu_yltype(rs.getString("yltype"));
				m.setEsiu_gs(rs.getString("gs"));
				m.setEsiu_sye(rs.getString("sye"));
				m.setEsiu_syu(rs.getString("syu"));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return m;
	}

	// 获取台账后数据
	public EmShebaoUpdateModel getShebaoSZSIInfo(String str) {
		EmShebaoUpdateModel m = new EmShebaoUpdateModel();
		String sql = "select * from EmShebaoSZSI where 1=1 " + str;
		try {
			ResultSet rs = conn.GRS(sql);
			if (rs.next()) {
				m.setEsiu_name(rs.getString("name"));
				m.setEsiu_lbid(rs.getString("lbid"));
				m.setEsiu_radix(rs.getInt("radix"));
				m.setEsiu_hj(rs.getString("hj"));
				m.setEsiu_yl(rs.getString("yl"));
				m.setEsiu_yltype(rs.getString("yltype"));
				m.setEsiu_gs(rs.getString("gs"));
				m.setEsiu_sye(rs.getString("sye"));
				m.setEsiu_syu(rs.getString("syu"));
				m.setEsiu_ylcp(rs.getBigDecimal("ylcp"));
				m.setEsiu_ylop(rs.getBigDecimal("ylop"));
				m.setEsiu_jlcp(rs.getBigDecimal("jlcp"));
				m.setEsiu_jlop(rs.getBigDecimal("jlop"));
				m.setEsiu_gscp(rs.getBigDecimal("gscp"));
				m.setEsiu_syecp(rs.getBigDecimal("syecp"));
				m.setEsiu_syucp(rs.getBigDecimal("syucp"));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return m;
	}

	// 获取当前社保月份
	public String getShebaoNowmonth() {
		String nowmonth = "";
		String sql = "select top 1 ownmonth from EmShebaoUpdate";
		try {

			ResultSet rs = conn.GRS(sql);
			while (rs.next()) {
				nowmonth = String.valueOf(rs.getInt("ownmonth"));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return nowmonth;
	}

	// 检查是否已导入自定单位编号的台账前数据
	public int getFinanceMonthCount(String shebaoid) {
		int count = 0;
		String sql = "select COUNT(*)cou from EmShebaoSZSIMonth where ownmonth=(select top 1 ownmonth from emshebaoupdate)  and shebaoid="
				+ shebaoid;

		try {

			ResultSet rs = conn.GRS(sql);
			while (rs.next()) {
				count = rs.getInt("cou");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return count;
	}

	// 网上申报变动情况
	public List<String> getSChange(String str) {
		List<String> change = new ArrayList<String>();
		String sql = "select distinct emsc_change from EmShebaoChange WHERE 1=1"
				+ str;
		try {

			ResultSet rs = conn.GRS(sql);
			change.add("全部");
			while (rs.next()) {
				change.add(rs.getString("emsc_change"));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return change;
	}

	public List<EmShebaoSetupModel> getList() {
		List<EmShebaoSetupModel> list = new ListModelList<>();
		dbconn db = new dbconn();
		String sql = "select convert(varchar(10),year(GETDATE()))+'-'+convert(varchar(10),month(GETDATE()))+'-'+convert(varchar(10),LastDay)lastdate,OnAir "
				+ "from EmShebaoSetup";
		try {
			list = db.find(sql, EmShebaoSetupModel.class, null);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return list;
	}

	// 台后数据统计
	public List<EmShebaoSZSIFileModel> downSZSI(String cid) {
		List<EmShebaoSZSIFileModel> result = new ArrayList<EmShebaoSZSIFileModel>();

		String sql = "select coco_shebao,case coco_shebao when '中智开户' then '391055' when '中智开户(委托)' then '167120' when '中智开户(派遣)' then '464781' when '中智开户(外包)' then '464780' else coco_shebaoID end as shebaoid,"
				+ "cid from CoCompact where cid="
				+ cid
				+ " and coco_compacttype!='cs' and coco_state>3 and coco_state!=9 group by coco_shebao,cid,coco_shebaoID";

		try {

			ResultSet rs = conn.GRS(sql);
			while (rs.next()) {

				if (!"".equals(rs.getString("shebaoid"))
						&& rs.getString("shebaoid") != null) {
					// 获取fileurl
					List<EmShebaoSZSIFileModel> fileUrl = new ArrayList<EmShebaoSZSIFileModel>();
					fileUrl = getSZSIFile(rs.getString("shebaoid"));

					if (fileUrl.size() > 0) {
						for (int i = 0; i < fileUrl.size(); i++) {
							result.add(fileUrl.get(i));
						}
					}
				}

			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}

	// 获取社保台账后文件路径
	public List<EmShebaoSZSIFileModel> getSZSIFile(String shebaoid) {
		List<EmShebaoSZSIFileModel> result = new ArrayList<EmShebaoSZSIFileModel>();
		String top = "2";
		if ("167120".equals(shebaoid)) {
			top = "4";// 委托账户有两个excel文件
		}
		String sql = "select top "
				+ top
				+ " max(essf_id)id,essf_type,essf_fileurl,essf_filename from EmShebaoSZSIFile where ownmonth=(select top 1 ownmonth from emshebaoszsi) "
				+ "and essf_shebaoid=" + shebaoid
				+ " group by essf_type,essf_fileurl,essf_filename";

		try {

			ResultSet rs = conn.GRS(sql);
			while (rs.next()) {
				EmShebaoSZSIFileModel m = new EmShebaoSZSIFileModel();
				m.setEssf_filename(rs.getString("essf_filename"));
				m.setEssf_type(rs.getInt("essf_type"));
				m.setEssf_fileurl(rs.getString("essf_fileurl"));
				result.add(m);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}
}
