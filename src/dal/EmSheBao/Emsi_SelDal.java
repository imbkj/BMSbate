package dal.EmSheBao;

import java.math.BigDecimal;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import Conn.dbconn;
import Model.CoBaseModel;
import Model.EmSheBaoChangeHjModel;
import Model.EmSheBaoChangeModel;
import Model.EmShebaoBJModel;
import Model.EmShebaoChangeSZSIModel;
import Model.EmShebaoFormulaModel;
import Model.EmShebaoSetupModel;
import Model.EmShebaoUpdateModel;
import Model.EmbaseModel;

public class Emsi_SelDal {
	private dbconn conn = new dbconn();

	// 获取所有公司基本信息
	public List<CoBaseModel> getCoBase() {
		List<CoBaseModel> list = new ArrayList<CoBaseModel>();
		CoBaseModel m = null;
		String sql = "select CID,coba_company,coba_namespell,coba_client,coba_servicestate from CoBase order by coba_servicestate desc,CID";
		try {
			ResultSet rs = conn.GRS(sql);
			while (rs.next()) {
				m = new CoBaseModel();
				m.setCid(rs.getInt("CID"));
				m.setCoba_company(rs.getString("coba_company"));
				m.setCoba_namespell(rs.getString("coba_namespell"));
				m.setCoba_client(rs.getString("coba_client"));
				m.setCoba_servicestate(rs.getInt("coba_servicestate"));
				list.add(m);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}

	// 根据Cid获取员工信息
	public List<EmbaseModel> getEmbaseByCid(int cid) {
		List<EmbaseModel> list = new ArrayList<EmbaseModel>();
		EmbaseModel m = null;
		String sql = "select gid,emba_name,emba_spell,emba_idcard,(case emba_state when 1 then '在职' else '离职' end) as state  from EmBase where cid=? order by emba_state desc";
		PreparedStatement psmt = conn.getpre(sql);
		try {
			psmt.setInt(1, cid);
			ResultSet rs = psmt.executeQuery();
			while (rs.next()) {
				m = new EmbaseModel();
				m.setGid(rs.getInt("gid"));
				m.setEmba_name(rs.getString("emba_name"));
				m.setEmba_spell(rs.getString("emba_spell"));
				m.setEmba_idcard(rs.getString("emba_idcard"));
				m.setStatename(rs.getString("state"));
				list.add(m);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return list;
	}

	// 根据GID获取员工信息
	public EmbaseModel getEmBase(int gid) {
		EmbaseModel m = new EmbaseModel();
		String sql = "select emba_gjjuname,emba_gjjidcard,emba_sbuname,emba_sbidcard,emba_folk,gid,emba_name,emba_pinyin,emba_mobile,emba_computerid,emba_formula"
				+ ",Emba_emsb_ownmonth,emba_emsb_m1,emba_emsb_m2,emba_emsb_m3,emba_idcard,emba_Nationality from EmBase where gid=?";
		PreparedStatement psmt = conn.getpre(sql);
		try {
			psmt.setInt(1, gid);
			ResultSet rs = psmt.executeQuery();
			if (rs.next()) {
				m.setGid(rs.getInt("gid"));
				m.setEmba_gjjidcard(rs.getString("emba_gjjidcard"));
				m.setEmba_gjjuname(rs.getString("emba_gjjuname"));
				m.setEmba_sbuname(rs.getString("emba_sbuname"));
				m.setEmba_sbidcard(rs.getString("emba_sbidcard"));
				m.setEmba_name(rs.getString("emba_name"));
				m.setEmba_pinyin(rs.getString("emba_pinyin"));
				m.setEmba_mobile(rs.getString("emba_mobile"));
				m.setEmba_computerid(rs.getString("emba_computerid"));
				m.setEmba_idcard(rs.getString("emba_idcard"));
				m.setEmba_folk(rs.getString("emba_folk"));
				m.setEmba_nationality(rs.getString("emba_nationality"));
				m.setEmba_emsb_ownmonth(rs.getString("emba_emsb_ownmonth"));
				m.setEmba_emsb_m1(rs.getString("emba_emsb_m1"));
				m.setEmba_emsb_m2(rs.getString("emba_emsb_m2"));
				m.setEmba_emsb_m3(rs.getString("emba_emsb_m3"));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return m;
	}

	// 根据GID获取员工补缴信息
	public EmbaseModel getEmBaseBj(int gid) {
		EmbaseModel m = new EmbaseModel();
		String sql = "select gid,emba_name,emba_emsb_ownmonth,emba_sb_radix,emba_emsb_feeownmonth,emba_emsb_m1,emba_emsb_r1,emba_emsb_m2,emba_emsb_r2,emba_emsb_m3,emba_emsb_r3,emba_emsb_jlbj1,emba_emsb_jlbj2,emba_emsb_jlbj3 from EmBase where gid=?";
		PreparedStatement psmt = conn.getpre(sql);
		try {
			psmt.setInt(1, gid);
			ResultSet rs = psmt.executeQuery();
			if (rs.next()) {
				m.setGid(rs.getInt("gid"));
				m.setEmba_name(rs.getString("emba_name"));
				m.setEmba_emsb_ownmonth(rs.getString("emba_emsb_ownmonth"));
				m.setEmba_emsb_feeownmonth(rs
						.getString("emba_emsb_feeownmonth"));
				m.setEmba_emsb_m1(rs.getString("emba_emsb_m1"));
				m.setEmba_emsb_r1(rs.getInt("emba_emsb_r1"));
				m.setEmba_emsb_m2(rs.getString("emba_emsb_m2"));
				m.setEmba_emsb_r2(rs.getInt("emba_emsb_r2"));
				m.setEmba_emsb_m3(rs.getString("emba_emsb_m3"));
				m.setEmba_emsb_r3(rs.getInt("emba_emsb_r3"));
				m.setEmba_sb_radix(new BigDecimal(Integer.toString(rs
						.getInt("emba_sb_radix"))));
				m.setEmba_emsb_jlbj1(rs.getInt("emba_emsb_jlbj1"));
				m.setEmba_emsb_jlbj2(rs.getInt("emba_emsb_jlbj2"));
				m.setEmba_emsb_jlbj3(rs.getInt("emba_emsb_jlbj3"));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return m;
	}

	// 获取民族列表
	public List<String> getFolk() {
		List<String> list = new ArrayList<String>();
		String sql = "select pufo_name from PubFolk";
		try {
			ResultSet rs = conn.GRS(sql);
			while (rs.next()) {
				list.add(rs.getString("pufo_name"));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}

	// 获取模板列表(传入是否为外籍人模板)
	public List<EmShebaoFormulaModel> getFormula(int ifForeign) {
		List<EmShebaoFormulaModel> list = new ArrayList<EmShebaoFormulaModel>();
		String sql = "select * from EmShebaoFormula where emsf_ifForeign=?";
		PreparedStatement psmt = conn.getpre(sql);
		try {
			psmt.setInt(1, ifForeign);
			ResultSet rs = psmt.executeQuery();
			while (rs.next()) {
				list.add(new EmShebaoFormulaModel(rs.getInt("id"), rs
						.getString("emsf_title"), rs.getString("emsf_hj"), rs
						.getString("emsf_yl"), rs.getString("emsf_yltype"), rs
						.getString("emsf_gs"), rs.getString("emsf_sye"), rs
						.getString("emsf_syu"), rs.getString("emsf_house"), rs
						.getString("emsf_officialRank"), rs
						.getString("emsf_worker"),
						rs.getString("emsf_addtime"), rs
								.getString("emsf_addname"), rs
								.getInt("emsf_ifForeign"), rs
								.getString("emsf_soin_title")));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}

	// 获取模板列表(传入是否为外籍人模板)
	public List<EmShebaoFormulaModel> getFormula(String ifForeign) {
		List<EmShebaoFormulaModel> list = new ArrayList<EmShebaoFormulaModel>();
		String sql = "select * from EmShebaoFormula where emsf_title=?";
		PreparedStatement psmt = conn.getpre(sql);
		try {
			psmt.setString(1, ifForeign);
			ResultSet rs = psmt.executeQuery();
			while (rs.next()) {
				list.add(new EmShebaoFormulaModel(rs.getInt("id"), rs
						.getString("emsf_title"), rs.getString("emsf_hj"), rs
						.getString("emsf_yl"), rs.getString("emsf_yltype"), rs
						.getString("emsf_gs"), rs.getString("emsf_sye"), rs
						.getString("emsf_syu"), rs.getString("emsf_house"), rs
						.getString("emsf_officialRank"), rs
						.getString("emsf_worker"),
						rs.getString("emsf_addtime"), rs
								.getString("emsf_addname"), rs
								.getInt("emsf_ifForeign"), rs
								.getString("emsf_soin_title")));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}

	// 获取社保设置
	public EmShebaoSetupModel getSbSetup() {
		EmShebaoSetupModel m = null;
		String sql = "select * from EmShebaoSetup";
		try {
			ResultSet rs = conn.GRS(sql);
			rs.next();
			m = new EmShebaoSetupModel(rs.getInt("id"), rs.getInt("lastday"),
					rs.getString("lastdayname"), rs.getInt("lastdaybj"),
					rs.getString("lastdaynamebj"), rs.getInt("onair"),
					rs.getString("onairname"), rs.getString("reason"),
					rs.getInt("onairbj"), rs.getString("onairnamebj"),
					rs.getString("reasonbj"), rs.getInt("cwday"),
					rs.getInt("fallday"), rs.getInt("auditday"),
					rs.getString("auditdayname"),rs.getInt("malastday"),
					rs.getString("malastdayname"));

		} catch (Exception e) {
			e.printStackTrace();
		}
		return m;
	}

	// 获取社保在册数据的最后所属月份
	public int getSbUpdateOwnmonth() {
		int ownmonth = 0;
		String sql = "select top 1 ownmonth from EmShebaoupdate";
		try {
			ResultSet rs = conn.GRS(sql);
			rs.next();
			ownmonth = rs.getInt("ownmonth");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return ownmonth;
	}

	// 根据GID获取员工社保在册表信息
	public EmShebaoUpdateModel getShebaoUpdateByGid(int gid) {
		EmShebaoUpdateModel m = null;
		String sql = "SELECT eu.*,ef.emsf_soin_title,eb.emba_sex,DATEDIFF(YY,eb.emba_birth,GETDATE()) as emba_age,eb.emba_mobile FROM EmShebaoUpdate eu left join EmShebaoFormula ef on eu.Esiu_formulaid=ef.id left join embase eb on eu.gid=eb.gid where eu.gid=? and eu.esiu_ifstop=0";
		PreparedStatement psmt = conn.getpre(sql);
		try {
			psmt.setInt(1, gid);
			ResultSet rs = psmt.executeQuery();
			if (rs.next()) {
				m = new EmShebaoUpdateModel(rs.getInt("id"), rs.getInt("cid"),
						rs.getInt("gid"), rs.getInt("ownmonth"),
						rs.getString("esiu_company"),
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
						rs.getBigDecimal("esiu_syucp"),
						rs.getBigDecimal("esiu_syuop"),
						rs.getBigDecimal("esiu_syecp"),
						rs.getBigDecimal("esiu_syeop"),
						rs.getBigDecimal("esiu_gscp"),
						rs.getBigDecimal("esiu_gsop"),
						rs.getBigDecimal("esiu_housecp"),
						rs.getBigDecimal("esiu_houseop"),
						rs.getBigDecimal("esiu_totalcp"),
						rs.getBigDecimal("esiu_totalop"),
						rs.getInt("esiu_ifinure"), rs.getString("esiu_remark"),
						rs.getString("esiu_addname"),
						rs.getString("esiu_addtime"), rs.getInt("esiu_single"),
						rs.getString("esiu_worker"),
						rs.getString("esiu_officialrank"),
						rs.getString("esiu_hand"), rs.getString("esiu_folk"),
						rs.getInt("esiu_ifstop"), rs.getInt("esiu_ifsame"),
						rs.getString("esiu_client"), rs.getString("esiu_lbid"),
						rs.getInt("esiu_shebaoid"),
						rs.getString("esiu_stopreason"),
						rs.getString("emsf_soin_title"),
						rs.getInt("Esiu_formulaid"), rs.getString("emba_sex"),
						rs.getString("emba_age"), rs.getString("emba_mobile"));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return m;
	}

	// 根据cid,single获取社保账户银行信息
	public String[] getShebaoBank(Integer cid) {
		String[] bankInfo = new String[2];
		String sql = "select top 1 cosb_bankname,cosb_bankacctid from CoShebao where cid=? order by cosb_id desc";
		PreparedStatement psmt = conn.getpre(sql);
		try {
			psmt.setInt(1, cid);
			ResultSet rs = psmt.executeQuery();
			if (rs.next()) {
				bankInfo[0] = rs.getString("cosb_bankname");
				bankInfo[1] = rs.getString("cosb_bankacctid");
			} else {
				bankInfo[0] = "";
				bankInfo[1] = "";
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return bankInfo;
	}

	// 根据GID获取员工社保在册表信息(不判断ifstop状态)
	public EmShebaoUpdateModel getShebaoUpdateByGid2(int gid) {
		EmShebaoUpdateModel m = null;
		String sql = "SELECT eu.*,ef.emsf_soin_title FROM EmShebaoUpdate eu left join EmShebaoFormula ef on eu.Esiu_formulaid=ef.id where gid=?";
		PreparedStatement psmt = conn.getpre(sql);
		try {
			psmt.setInt(1, gid);
			ResultSet rs = psmt.executeQuery();
			if (rs.next()) {
				m = new EmShebaoUpdateModel(rs.getInt("id"), rs.getInt("cid"),
						rs.getInt("gid"), rs.getInt("ownmonth"),
						rs.getString("esiu_company"),
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
						rs.getBigDecimal("esiu_syucp"),
						rs.getBigDecimal("esiu_syuop"),
						rs.getBigDecimal("esiu_syecp"),
						rs.getBigDecimal("esiu_syeop"),
						rs.getBigDecimal("esiu_gscp"),
						rs.getBigDecimal("esiu_gsop"),
						rs.getBigDecimal("esiu_housecp"),
						rs.getBigDecimal("esiu_houseop"),
						rs.getBigDecimal("esiu_totalcp"),
						rs.getBigDecimal("esiu_totalop"),
						rs.getInt("esiu_ifinure"), rs.getString("esiu_remark"),
						rs.getString("esiu_addname"),
						rs.getString("esiu_addtime"), rs.getInt("esiu_single"),
						rs.getString("esiu_worker"),
						rs.getString("esiu_officialrank"),
						rs.getString("esiu_hand"), rs.getString("esiu_folk"),
						rs.getInt("esiu_ifstop"), rs.getInt("esiu_ifsame"),
						rs.getString("esiu_client"), rs.getString("esiu_lbid"),
						rs.getInt("esiu_shebaoid"),
						rs.getString("esiu_stopreason"),
						rs.getString("emsf_soin_title"));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return m;
	}

	// 根据GID获取员工最新的社保表信息
	public EmShebaoUpdateModel getShebaoByGid(int gid) {
		EmShebaoUpdateModel m = null;
		String sql = "select top 1 es.*,co.coba_company from EmShebao es inner join CoBase co on es.CID=co.CID where GID=? order by Ownmonth desc";
		PreparedStatement psmt = conn.getpre(sql);
		try {
			psmt.setInt(1, gid);
			ResultSet rs = psmt.executeQuery();
			if (rs.next()) {
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
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return m;
	}

	// 根据GID获取员工社保所有变更信息
	public List<EmSheBaoChangeModel> getAllChangListByGid(int gid,
			String computerid) {
		List<EmSheBaoChangeModel> list = new ArrayList<EmSheBaoChangeModel>();
		EmSheBaoChangeModel m = null;
		StringBuilder sql = new StringBuilder();
		sql.append("select a.*,b.msg_a from ");
		sql.append("(SELECT type='1',ID,emsc_Shortname,emsc_Name,emsc_Single,Ownmonth,emsc_Change,emsc_Content,emsc_AddTime,emsc_AddName,emsc_IfDeclare,case emsc_change when'停交' then emsc_stopreason+' '+isnull(emsc_Remark,'') else emsc_Remark end as emsc_Remark,emsc_radix FROM EmShebaochange where gid=? or emsc_Computerid=? ");
		sql.append("union all SELECT type='2',escs_id,escs_Company,escs_Name,escs_Single,Ownmonth,escs_Change,escs_Content,escs_Addtime,escs_Addname,escs_IfDeclare,escs_Remark,null as radix FROM EmShebaoChangeSZSI where gid=? ");
		sql.append("union all SELECT type='3',ID,emsc_Shortname,emsc_Name,emsc_Single,Ownmonth,emsc_Change,emsc_Content,emsc_AddTime,emsc_AddName,emsc_IfDeclare,emsc_Remark,emsc_radix FROM EmShebaoChangeForeigner where gid=? or emsc_Computerid=? ");
		sql.append("union all SELECT type='4',ID,emsb_company,emsb_name,emsb_single,Ownmonth,'补缴','补缴'+CONVERT(varchar(10),emsb_startmonth)+' 社保养老',emsb_addtime,emsb_addname,emsb_Ifdeclare,emsb_remark,emsb_radix FROM EmShebaoBJ where (emsb_tapr_id<>0 or emsb_tapr_id is null) and gid=? or emsb_Computerid=? ");
		sql.append("union all SELECT type='5',ID,esbj_company,esbj_name,esbj_single,Ownmonth,'补缴','补缴'+CONVERT(varchar(10),esbj_startmonth)+' 社保医疗',esbj_addtime,esbj_addname,esbj_Ifdeclare,esbj_remark,esbj_radix FROM EmShebaoBJJL where (esbj_tapr_id<>0 or esbj_tapr_id is null) and gid=? or esbj_Computerid=?  ");
		sql.append("union all SELECT type='6',ID,escm_Shortname,escm_name,escm_single,Ownmonth,'生育津贴','生育津贴申请',escm_addtime,escm_addname,escm_Ifdeclare,escm_remark,null FROM EmShebaoChangeMA where gid=? or escm_Computerid=?)a ");
		sql.append(" left join ");
		sql.append("(select smwr_tid,case when syme_log_id=250 then 2 when symr_log_id=250 then symr_readstate else 1 end msg_a,case smwr_table when 'EmSheBaoChange' then 1 when 'EmShebaoChangeSZSI' then 2 when 'EmShebaoChangeForeigner' then 3 when 'EmShebaoBJ' then 4 when 'EmShebaoBJJL' then 5 when 'EmShebaoChangeMA' then 6 end as type from View_Message where EXISTS(select syme_id from (select smwr_tid,MAX(syme_id)syme_id from View_Message where syme_state=1  and smwr_table in('EmSheBaoChange','EmShebaoChangeSZSI','EmShebaoChangeForeigner','EmShebaoBJ','EmSheBaoChangeMA') group by smwr_tid)msg where View_Message.syme_id=msg.syme_id))b");
		sql.append(" on a.type=b.type and a.ID=b.smwr_tid ");
		sql.append("order by ownmonth desc,emsc_AddTime desc");
		PreparedStatement psmt = conn.getpre(sql.toString());
		try {
			psmt.setInt(1, gid);
			psmt.setString(2, computerid);
			psmt.setInt(3, gid);
			psmt.setInt(4, gid);
			psmt.setString(5, computerid);
			psmt.setInt(6, gid);
			psmt.setString(7, computerid);
			psmt.setInt(8, gid);
			psmt.setString(9, computerid);
			psmt.setInt(10, gid);
			psmt.setString(11, computerid);
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
				list.add(m);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}

	// 根据主键ID获取社保变更表数据
	public EmSheBaoChangeModel getChangById(int id) {
		EmSheBaoChangeModel m = null;
		String sql = "select *,tapr_id=isnull(emsc_tapr_id,0) from EmShebaochange where ID=?";
		PreparedStatement psmt = conn.getpre(sql);
		try {
			psmt.setInt(1, id);
			ResultSet rs = psmt.executeQuery();
			if (rs.next()) {
				m = new EmSheBaoChangeModel();
				m.setId(rs.getInt("id"));
				m.setGid(rs.getInt("gid"));
				m.setOwnmonth(rs.getInt("ownmonth"));
				m.setEmsc_computerid(rs.getString("emsc_computerid"));
				m.setEmsc_radix(rs.getInt("emsc_radix"));
				m.setEmsc_hj(rs.getString("emsc_hj"));
				m.setEmsc_yl(rs.getString("emsc_yl"));
				m.setEmsc_yltype(rs.getString("emsc_yltype"));
				m.setEmsc_sye(rs.getString("emsc_sye"));
				m.setEmsc_syu(rs.getString("emsc_syu"));
				m.setEmsc_gs(rs.getString("emsc_gs"));
				m.setEmsc_ifdeclareInt(rs.getInt("emsc_ifdeclare"));
				m.setEmsc_remark(rs.getString("emsc_remark"));
				m.setEmsc_tapr_id(rs.getInt("tapr_id"));
				m.setEmsc_stopreason(rs.getString("emsc_stopreason"));
				m.setEmsc_formula(rs.getString("emsc_formula"));
				m.setEmsc_folk(rs.getString("emsc_folk"));
				m.setEmsc_hand(rs.getString("emsc_hand"));
				m.setEmsc_hj(rs.getString("emsc_hj"));
				m.setEmsc_worker(rs.getString("emsc_worker"));
				m.setEmsc_mobile(rs.getString("emsc_mobile"));
				m.setEmsc_addname(rs.getString("emsc_addname"));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return m;
	}

	// 根据GID获取未处理的员工补缴信息
	public List<EmShebaoBJModel> getBjListByGid(int gid) {
		List<EmShebaoBJModel> list = new ArrayList<EmShebaoBJModel>();
		EmShebaoBJModel m = null;
		String sql = "select Id,gid,emsb_name,Ownmonth,emsb_single,emsb_Ifdeclare,emsb_addname,emsb_addtime,emsb_startmonth,emsb_radix,emsb_tapr_id,'1' as type from EmShebaoBJ where emsb_ifdeclare<>1 and GID=? and (emsb_tapr_id!=0 or emsb_tapr_id is null) ";
		sql = sql + " union all ";
		sql = sql
				+ " select Id,gid,esbj_name,Ownmonth,esbj_single,esbj_Ifdeclare,esbj_addname,esbj_addtime,esbj_startmonth,esbj_radix,esbj_tapr_id,'2' as type from EmShebaoBJJL where esbj_ifdeclare<>1 and GID=? and (esbj_tapr_id!=0 or esbj_tapr_id is null) order by ownmonth,emsb_startmonth,type desc ";
		PreparedStatement psmt = conn.getpre(sql);
		try {
			psmt.setInt(1, gid);
			psmt.setInt(2, gid);
			ResultSet rs = psmt.executeQuery();
			while (rs.next()) {
				m = new EmShebaoBJModel();
				m.setId(rs.getInt("id"));
				m.setType(rs.getString("type"));
				m.setGid(rs.getInt("gid"));
				m.setEmsb_name(rs.getString("emsb_name"));
				m.setOwnmonth(rs.getInt("ownmonth"));
				m.setEmsb_startmonth(rs.getInt("emsb_startmonth"));
				m.setEmsb_radix(rs.getInt("emsb_radix"));
				m.setEmsb_addname(rs.getString("emsb_addname"));
				m.setEmsb_addtime(rs.getString("emsb_addtime"));
				m.setEmsb_ifdeclare(rs.getInt("emsb_ifdeclare"));
				m.setEmsb_tapr_id(rs.getInt("emsb_tapr_id"));
				m.setEmsb_single(rs.getInt("emsb_single"));
				list.add(m);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}

	// 根据养老补交ID获取待确认的员工医疗补缴信息
	public EmShebaoBJModel getBjJLListByBJid(int id) {
		EmShebaoBJModel m = null;
		String sql = "select top 1 emshebaobjjl.* from EmShebaoBJJL,EmShebaoBJ where EmShebaoBJJL.GID=EmShebaoBJ.GID and EmShebaoBJJL.Ownmonth=EmShebaoBJ.Ownmonth and EmShebaoBJJL.esbj_startmonth=EmShebaoBJ.emsb_startmonth and EmShebaoBJ.ID=? order by emshebaobjjl.id desc";
		PreparedStatement psmt = conn.getpre(sql);
		try {
			// System.out.println(sql);
			// System.out.println(id);
			psmt.setInt(1, id);
			ResultSet rs = psmt.executeQuery();
			while (rs.next()) {
				m = new EmShebaoBJModel();
				m.setId(rs.getInt("id"));
				m.setGid(rs.getInt("gid"));
				m.setEmsb_name(rs.getString("esbj_name"));
				m.setOwnmonth(rs.getInt("ownmonth"));
				m.setEmsb_startmonth(rs.getInt("esbj_startmonth"));
				m.setEmsb_radix(rs.getInt("esbj_radix"));
				m.setEmsb_addname(rs.getString("esbj_addname"));
				m.setEmsb_addtime(rs.getString("esbj_addtime"));
				m.setEmsb_ifdeclare(rs.getInt("esbj_ifdeclare"));
				m.setEmsb_tapr_id(rs.getInt("esbj_tapr_id"));
				m.setEmsb_single(rs.getInt("esbj_single"));
				m.setEmsb_yltype(rs.getString("esbj_yltype"));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return m;
	}

	// 根据GID获取未处理完的员工社保变更信息
	public List<EmSheBaoChangeModel> getChangListByGid(int gid) {
		List<EmSheBaoChangeModel> list = new ArrayList<EmSheBaoChangeModel>();
		EmSheBaoChangeModel m = null;
		StringBuilder sql = new StringBuilder();
		sql.append(" SELECT type='1',ID,Ownmonth,emsc_Change,emsc_Content,emsc_AddTime,emsc_AddName,emsc_IfDeclare,emsc_tapr_id FROM EmShebaochange where emsc_ifdeclare<>1 and gid=?");
		sql.append(" union all SELECT type='2',escs_id,Ownmonth,escs_Change,escs_Content,escs_Addtime,escs_Addname,escs_IfDeclare,escs_tapr_id FROM EmShebaoChangeSZSI where escs_ifdeclare<>1 and gid=? ");
		sql.append(" union all SELECT type='3',ID,Ownmonth,emsc_Change,emsc_Content,emsc_AddTime,emsc_AddName,emsc_IfDeclare,emsc_tapr_id FROM EmShebaoChangeForeigner where emsc_ifdeclare<>1 and gid=?");
		sql.append(" union all SELECT type='4',ID,Ownmonth,'生育津贴申请','',escm_AddTime,escm_AddName,escm_IfDeclare,0 FROM EmShebaoChangeMA where escm_ifdeclare in(0,3) and gid=?");
		sql.append(" order by ownmonth desc");
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
				m.setOwnmonth(rs.getInt("ownmonth"));
				m.setEmsc_change(rs.getString("emsc_change"));
				m.setEmsc_content(rs.getString("emsc_content"));
				m.setEmsc_addtime(rs.getString("emsc_addtime"));
				m.setEmsc_addname(rs.getString("emsc_addname"));
				m.setEmsc_ifdeclare(rs.getString("emsc_ifdeclare"));
				m.setEmsc_tapr_id(rs.getInt("emsc_tapr_id"));
				list.add(m);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}

	// 获取员工社保特殊变更信息
	public EmShebaoChangeSZSIModel getEmSCSZSIData(int escs_id) {
		EmShebaoChangeSZSIModel m = new EmShebaoChangeSZSIModel();
		String sql = "select * from EmShebaoChangeSZSI  where escs_id=?";
		PreparedStatement psmt = conn.getpre(sql);
		try {
			psmt.setInt(1, escs_id);
			ResultSet rs = psmt.executeQuery();
			if (rs.next()) {
				m.setEscs_id(rs.getInt("escs_id"));
				m.setGid(rs.getInt("gid"));
				m.setCid(rs.getInt("cid"));
				m.setOwnmonth(rs.getInt("ownmonth"));
				m.setEscs_company(rs.getString("escs_company"));
				m.setEscs_name(rs.getString("escs_name"));
				m.setEscs_change(rs.getString("escs_change"));
				m.setEscs_content(rs.getString("escs_content"));
				m.setEscs_remark(rs.getString("escs_remark"));
				m.setEmsc_tapr_id(rs.getInt("escs_tapr_id"));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return m;
	}

	// 根据Gid获取工伤系数及失业下浮
	public String[] getItemProportionByGid(int gid) {
		String[] str = null;
		String sql = "select isnull(coco_Injury,0) as coco_Injury,coco_sb_sye=(1-isnull(coco_sb_sye,0)) from (select a.gid,a.cid,coof.coof_coco_id from CoGList a INNER join CoOfferList b on a.cgli_coli_id=b.coli_id INNER join CoOffer coof on b.coli_coof_id=coof.coof_id where b.coli_state=1 and cgli_state=1 and b.coli_name='社会保险服务' group by a.gid,a.cid,coof_coco_id)c inner join CoCompact d on c.coof_coco_id=d.coco_id where gid=? order by d.coco_addtime desc";
		PreparedStatement psmt = conn.getpre(sql);
		try {
			psmt.setInt(1, gid);
			ResultSet rs = psmt.executeQuery();
			rs.next();
			str = new String[] { rs.getString("coco_Injury"),
					rs.getString("coco_sb_sye") };
		} catch (Exception e) {

		}
		return str;
	}

	// 查询该员工是否分配“社会保险服务”项目
	public boolean existsCoOfferList(int gid) {
		StringBuilder sql = new StringBuilder();
		sql.append("select count(*)count  from CoGList cogl ");
		sql.append("left join CoOfferList coli on cogl.cgli_coli_id=coli.coli_id ");
		sql.append("left join CoOffer coof on coli.coli_coof_id=coof.coof_id ");
		sql.append("left join CoCompact coco on coof.coof_coco_id=coco.coco_id ");
		sql.append("where coli.coli_name='社会保险服务' and cgli_stopdate is null and gid=?");
		PreparedStatement psmt = conn.getpre(sql.toString());
		boolean bool = false;
		try {
			psmt.setInt(1, gid);
			ResultSet rs = psmt.executeQuery();
			if (rs.next())
				if (rs.getInt("count") > 0)
					bool = true;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return bool;
	}

	// 查询该员工是否已有社保信息
	public boolean existsShebao(int gid) {
		String sql = "select count(*)count from EmShebaoUpdate a INNER JOIN (SELECT gid FROM EmShebaoChange where emsc_Change='新增' OR emsc_Change ='调入' OR emsc_Change ='调回' union all SELECT gid FROM EmShebaoChangeForeigner where emsc_Change='新增' OR emsc_Change ='调入' OR emsc_Change ='调回') b ON a.GID=b.gid where a.GID=? and esiu_ifstop=0";
		PreparedStatement psmt = conn.getpre(sql);
		boolean bool = false;
		try {
			psmt.setInt(1, gid);
			ResultSet rs = psmt.executeQuery();
			if (rs.next())
				if (rs.getInt("count") > 0)
					bool = true;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return bool;
	}

	// 检查该电脑号或身份证号下是否有在册社保数据
	public String[] chkIfShebao(int gid) {
		String sql = "select COUNT(*)cou,Esiu_company,CID,GID,Esiu_Name from EmShebaoUpdate where gid!=? and Esiu_IfStop=0 and (Esiu_idcard in(select emba_sbidcard from embase where gid=?) or Esiu_ComputerID in (select emba_computerid from embase where gid=?) or Esiu_idcard in(select dbo.IDCardChange(emba_sbidcard) from embase where gid=?)) group by Esiu_company,CID,GID,Esiu_Name";
		PreparedStatement psmt = conn.getpre(sql);
		String[] str = null;
		try {
			psmt.setInt(1, gid);
			psmt.setInt(2, gid);
			psmt.setInt(3, gid);
			psmt.setInt(4, gid);
			ResultSet rs = psmt.executeQuery();
			if (rs.next()) {
				str = new String[] { String.valueOf(rs.getInt("cou")),
						rs.getString("CID"), rs.getString("Esiu_company"),
						rs.getString("GID"), rs.getString("Esiu_Name") };
				// 判断如果新员工编号和旧员工编号对应的社保单位编号一样，则不限制(全国项目部(颜璐)提出取消这个条件)
				/*
				if (getSheBaoIdByGid(gid)
						.equals(getSheBaoIdByGid(Integer.parseInt(rs
								.getString("gid"))))) {
					str = null;
				}
				*/
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return str;
	}

	// 根据gid获取社保单位编号
	public String getSheBaoIdByGid(Integer gid) {
		String shebaoid = "";
		String sql = "select dbo.GetShebaoID('" + gid + "') as shebaoid";
		PreparedStatement psmt = conn.getpre(sql);
		try {
			ResultSet rs = psmt.executeQuery();
			if (rs.next()) {
				shebaoid = rs.getString("shebaoid");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return shebaoid;
	}

	// 查询该员工是否在职
	public boolean existsEmState(int gid) {
		String sql = "select count(*)count from EmBase where GID=? and emba_state=1";
		PreparedStatement psmt = conn.getpre(sql);
		boolean bool = false;
		try {
			psmt.setInt(1, gid);
			ResultSet rs = psmt.executeQuery();
			if (rs.next())
				if (rs.getInt("count") > 0)
					bool = true;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return bool;
	}

	// 查询该员工是否为外籍人
	public boolean existsForeigner(int gid) {
		String sql = "select emba_Nationality=isnull(emba_Nationality,0) from embase where gid=?";
		PreparedStatement psmt = conn.getpre(sql);
		boolean bool = false;
		try {
			psmt.setInt(1, gid);
			ResultSet rs = psmt.executeQuery();
			if (rs.next())
				if (!"中国".equals(rs.getString("emba_Nationality")))
					bool = true;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return bool;
	}

	// 查询该员工社保是否被调走
	public boolean existsStop(int gid) {
		String sql = "select count(*)count from EmShebaoUpdate where GID=? and esiu_ifstop<>0";
		PreparedStatement psmt = conn.getpre(sql);
		boolean bool = false;
		try {
			psmt.setInt(1, gid);
			ResultSet rs = psmt.executeQuery();
			if (rs.next())
				if (rs.getInt("count") > 0)
					bool = true;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return bool;
	}

	// 检查变更表状态
	public int checkChangeDeclare(int id, int type) {
		String sql = null;
		try {
			switch (type) {
			case 1:
				sql = "SELECT emsc_IfDeclare FROM EmShebaochange where id=?";
				break;
			case 2:
				sql = "SELECT emsc_IfDeclare=escs_IfDeclare FROM EmShebaoChangeSZSI where escs_id=?";
				break;
			case 3:
				sql = "SELECT emsc_IfDeclare FROM EmShebaoChangeForeigner where id=?";
				break;
			case 4:
				sql = "SELECT emsc_IfDeclare=case escm_ifdeclare when 0 then 4 else escm_ifdeclare end  FROM EmShebaoChangeMA where id=?";
				break;
			}
			PreparedStatement psmt = conn.getpre(sql);
			psmt.setInt(1, id);
			ResultSet rs = psmt.executeQuery();
			if (rs.next()) {
				return rs.getInt("emsc_IfDeclare");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return -1;
	}

	public List<EmSheBaoChangeHjModel> getEmSheBaoChangeHjList(Integer sbci_id) {
		List<EmSheBaoChangeHjModel> list = new ArrayList<EmSheBaoChangeHjModel>();
		dbconn db = new dbconn();
		String sql = "select coba_shortname,coba_company,emba_idcard,emba_name,a.* from EmSheBaoChangeHj a "
				+ " inner join cobase b on a.cid=b.cid "
				+ " inner join embase c on a.gid=c.gid where sbci_id="
				+ sbci_id;
		try {
			list = db.find(sql, EmSheBaoChangeHjModel.class,
					dbconn.parseSmap(EmSheBaoChangeHjModel.class));
		} catch (Exception e) {
			e.printStackTrace();
		}

		return list;
	}

	//根据身份证号和出院日期查询生育津贴数据
	public boolean chkEscmByEoD(String idcard,String endDate) {
		String sql = "select count(*)count from EmShebaoChangeMA where escm_ifdeclare<>3 and escm_idcard=? and datediff(d,escm_endoffp,?)=0";
		PreparedStatement psmt = conn.getpre(sql);
		boolean bool = true;
		try {
			psmt.setString(1, idcard);
			psmt.setString(2, endDate);
			ResultSet rs = psmt.executeQuery();
			if (rs.next())
				if (rs.getInt("count") > 0)
					bool = false;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return bool;
	}
}
