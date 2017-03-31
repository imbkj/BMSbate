/**
 * @Classname CoCompactBaseDal
 * @ClassInfo 公司合同基本信息数据库访问类
 * @author 林少斌
 * @Date 2013-10-16
 */
package dal.CoCompact;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.zkoss.zul.ListModelList;

import com.sun.org.apache.bcel.internal.generic.RETURN;

import Conn.dbconn;
import Model.CoAgencyBaseModel;
import Model.CoCompactModel;
import Util.UserInfo;

public class CoCompactBaseDal {
	private static dbconn conn = new dbconn();

	// 获取所有公司合同数据集
	private ResultSet getCoCoBase() throws Exception {
		ResultSet rs = null;
		String sql = "SELECT *,case when coco_class=0 then '标准版' else '' end+case when coco_class=1 then '一般非标' else '' end+case when coco_class=2 then '特殊非标' else '' end+case when coco_class=3 then '客户提供' else '' end co_type  FROM CoCompact_CoLa_CoBase_V  a left join CoBase b on b.CID=a.cid  where  a.CID in ( select cid from DataPopedom where log_id="
				+ UserInfo.getUserid()
				+ "  and  Dat_edited=1 )  order by coco_id desc";
		rs = conn.GRS(sql);
		return rs;
	}

	// 根据查询语句获取公司合同数据集
	private ResultSet getCoCoBase(String str) throws Exception {
		ResultSet rs = null;
		StringBuilder sql = new StringBuilder();
		sql.append("SELECT *,case when coco_class=0 then '标准版' else '' end+case when coco_class=1 then '一般非标' else '' end+case when coco_class=2 then '特殊非标' else '' end+case when coco_class=3 then '客户提供' else '' end co_type ");
		sql.append(" FROM CoCompact_CoLa_CoBase_V  a left join CoBase b on b.CID=a.cid where 1=1 and (exists(select cid from DataPopedom where log_id=25  and  Dat_edited=1 and a.CID=DataPopedom.cid) "
				+ " or exists(select cola_id from DataPopedom where log_id=25  and  Dat_edited=1 and a.coco_cola_id=DataPopedom.cola_id)) "
				+ str);
		sql.append(" order by coco_id desc");
		// System.out.println(sql.toString());
		rs = conn.GRS(sql.toString());
		return rs;
	}

	// 根据查询语句获取公司合同补充协议数据集
	private ResultSet getCoCoBaseBC(String str) throws Exception {
		ResultSet rs1 = null;
		StringBuilder sql1 = new StringBuilder();
		sql1.append("SELECT * ");
		sql1.append(" from CoCompactSA_CoLa_CoBase_V a where 1=1 "
				+ " and (exists(select cid from DataPopedom where log_id=25  and  Dat_edited=1 and a.CID=DataPopedom.cid) "
				+ " or exists(select cola_id from DataPopedom where log_id=25  and  Dat_edited=1 and a.coco_cola_id=DataPopedom.cola_id)) "
				+ str);
		sql1.append(" order by coco_id desc");
		// System.out.println(sql1.toString());
		rs1 = conn.GRS(sql1.toString());
		return rs1;
	}

	// 根据查询语句获取公司合同数据集
	private ResultSet getCoCoBaseBlist(String str) throws Exception {
		ResultSet rs = null;
		StringBuilder sql = new StringBuilder();
		sql.append("SELECT *,case when coco_class=0 then '标准版' else '' end+case when coco_class=1 then '一般非标' else '' end+case when coco_class=2 then '特殊非标' else '' end+case when coco_class=3 then '客户提供' else '' end co_type ");
		sql.append(" FROM CoCompact_CoLa_CoBase_V  a left join CoBase b on b.CID=a.cid where 1=1 and  a.CID in ( select cid from DataPopedom where log_id="
				+ UserInfo.getUserid() + "  and  Dat_edited=1 ) ");
		sql.append(" and a.cid=" + str);
		sql.append(" order by coco_id desc");
		System.out.println(sql.toString());
		rs = conn.GRS(sql.toString());
		return rs;
	}

	// 根据查询语句获取公司合同补充协议数据集
	private ResultSet getCoCoBaseBCBlist(String str) throws Exception {
		ResultSet rs1 = null;
		StringBuilder sql1 = new StringBuilder();
		sql1.append("SELECT *,'补充协议' co_type,coba_company company,b.cid sacid,CONVERT(varchar(100), coco_inuredate, 23) inurdate,CONVERT(varchar(100), coco_indate, 23) indate ");
		sql1.append(" from CoCompactSA a left join CoCompact b on a.ccsa_coco_id=b.coco_id left join CoBase c on c.CID=b.cid where 1=1 and b.cid in ( select cid from DataPopedom where log_id="
				+ UserInfo.getUserid() + ") ");
		sql1.append(" and b.cid=" + str);
		sql1.append(" order by coco_id desc");
		System.out.println(sql1.toString());
		rs1 = conn.GRS(sql1.toString());
		return rs1;
	}

	// 获取添加人数据集
	private ResultSet getCoCoAddname(String str) throws Exception {
		ResultSet rs = null;
		String sql = "SELECT DISTINCT coco_addname FROM CoCompact_CoLa_CoBase_V WHERE coco_addname IS NOT NULL "
				+ str + " ORDER BY coco_addname";
		rs = conn.GRS(sql);
		return rs;
	}

	// 获取所有公司合同(CoCompact_CoLa_CoBase_V)
	public ArrayList<CoCompactModel> getCoCoBaseList() {
		ArrayList<CoCompactModel> CoCoBaseList = new ArrayList<CoCompactModel>();
		try {
			ResultSet rs = getCoCoBase();
			while (rs.next()) {
				CoCoBaseList.add(new CoCompactModel(rs.getInt("coco_id"), rs
						.getString("cid"), rs.getString("coco_cola_id"), rs
						.getString("coco_compacttype"), rs
						.getString("coco_compactid"), rs
						.getString("coco_servicearea"), rs
						.getString("coco_signdate"), rs
						.getString("coco_stopdate"), rs
						.getString("coco_stopreason"), rs
						.getString("coco_stoptype"), rs
						.getString("coco_inuredate"), rs
						.getString("coco_indate"), rs.getString("coco_delay"),
						rs.getString("coco_remark"), rs.getInt("coco_state"),
						rs.getString("coco_addtime"), rs
								.getString("coco_addname"), rs
								.getString("company"), rs
								.getString("coba_client"), rs
								.getString("coco_returndate"), rs
								.getString("coco_signplace"), rs
								.getString("coco_money"), rs
								.getString("coco_invoice"), rs
								.getString("coco_filedate"), rs
								.getString("coco_fileid"), rs
								.getString("coco_printdate"), rs
								.getString("state"), rs
								.getString("coco_auditingdate"), rs
								.getString("coof_fee"), rs
								.getInt("coco_tapr_id"), rs
								.getString("co_type"), rs
								.getString("coco_chs_copies"), rs
								.getString("coco_en_copies"), rs
								.getString("coco_shebao"), rs
								.getString("coco_house"), rs
								.getString("coco_opp")

				));
			}
		} catch (Exception e) {
			System.out.println(e.toString());
		}
		return CoCoBaseList;
	}

	// 根据ID查询合同信息
	public List<CoCompactModel> getcocompactList(Integer id) {
		List<CoCompactModel> list = new ListModelList<>();
		dbconn db = new dbconn();
		String sql = "SELECT coco_id,cid cid2,convert(varchar(10),cid)cid,convert(varchar(10),coco_cola_id)coco_cola_id,coco_compacttype,coco_compactid,coco_servicearea,"
				+ "convert(varchar(10),coco_signdate,120)coco_signdate,convert(varchar(10),coco_stopdate,120)coco_stopdate,"
				+ "coco_stopreason,coco_stoptype,convert(varchar(10),coco_inuredate,120)coco_inuredate,"
				+ "convert(varchar(10),coco_indate,120)coco_indate,coco_delay,coco_remark,coco_state,"
				+ "convert(varchar(10),coco_addtime,120)coco_addtime,coco_addname,company,coba_shortname,"
				+ "convert(varchar(10),coco_returndate,120)coco_returndate,coco_signplace,coco_money,coco_invoice,"
				+ "convert(varchar(10),coco_filedate,120)coco_filedate,coco_fileid,convert(varchar(10),coco_printdate,120)coco_printdate,"
				+ "state,convert(varchar(10),coco_auditingdate,120)coco_auditingdate,convert(varchar(255),coof_fee)coof_fee,coco_tapr_id,convert(varchar(50),coco_class)coco_class,convert(varchar(50),coco_chs_copies)coco_chs_copies,convert(varchar(50),coco_en_copies)coco_en_copies,"
				+ "coco_shebao,coco_house,coco_gzperfee,coco_paydate,isnull(coco_fw_p,0)coco_fw_p,isnull(coco_fl_p,0)coco_fl_p,isnull(coco_dk_p,0)coco_dk_p "
				+ "from CoCompact_CoLa_CoBase_V "
				+ "where coco_id=? order by coco_id desc";
		try {
			list = db.find(sql, CoCompactModel.class, null, id);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return list;
	}

	// 根据合同id获取受托机构信息
	public CoAgencyBaseModel getAgencyInfo(Integer id) {
		CoAgencyBaseModel m = null;
		try {
			String sql = "select coab_city,coab_name from StAgencyBase_view where coab_id in(select top 1 cabc_id from CoCompact where cid in(select cid from CoCompact where coco_id="
					+ id + ") and cabc_id is not null)";
			ResultSet rs = conn.GRS(sql.toString());
			while (rs.next()) {
				m = new CoAgencyBaseModel();
				m.setCoab_city(rs.getString("coab_city"));
				m.setCoab_name(rs.getString("coab_name"));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return m;
	}

	// 根据ID查询合同信息
	public List<CoCompactModel> getcocompactListBycolaId(Integer colaid) {
		List<CoCompactModel> list = new ListModelList<>();
		dbconn db = new dbconn();
		String sql = "SELECT coco_id,cid cid2,convert(varchar(10),cid)cid,convert(varchar(10),coco_cola_id)coco_cola_id,coco_compacttype,coco_compactid,coco_servicearea,"
				+ "convert(varchar(10),coco_signdate,120)coco_signdate,convert(varchar(10),coco_stopdate,120)coco_stopdate,"
				+ "coco_stopreason,coco_stoptype,convert(varchar(10),coco_inuredate,120)coco_inuredate,"
				+ "convert(varchar(10),coco_indate,120)coco_indate,coco_delay,coco_remark,coco_state,"
				+ "convert(varchar(10),coco_addtime,120)coco_addtime,coco_addname,company,coba_shortname,"
				+ "convert(varchar(10),coco_returndate,120)coco_returndate,coco_signplace,coco_money,coco_invoice,"
				+ "convert(varchar(10),coco_filedate,120)coco_filedate,coco_fileid,convert(varchar(10),coco_printdate,120)coco_printdate,"
				+ "state,convert(varchar(10),coco_auditingdate,120)coco_auditingdate,convert(varchar(255),coof_fee)coof_fee,coco_tapr_id,convert(varchar(50),coco_class)coco_class,convert(varchar(50),coco_chs_copies)coco_chs_copies,convert(varchar(50),coco_en_copies)coco_en_copies,"
				+ "coco_shebao,coco_house "
				+ "from CoCompact_CoLa_CoBase_V "
				+ "where coco_cola_id=? and coco_state>3 order by coco_id desc";
		try {
			list = db.find(sql, CoCompactModel.class, null, colaid);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return list;
	}

	// 根据查询语句获取所有公司合同(CoCompact_CoLa_CoBase_V)
	public List<CoCompactModel> getCoCoBaseList(String str) {
		List<CoCompactModel> CoCoBaseList = new ArrayList<>();
		CoCompactModel m = null;
		try {
			ResultSet rs = getCoCoBase(str);
			while (rs.next()) {
				m = new CoCompactModel();
				m.setCoco_id(rs.getInt("coco_id"));
				m.setCid(rs.getString("cid"));
				m.setCoco_cola_id(rs.getString("coco_cola_id"));
				m.setCoco_compacttype(rs.getString("coco_compacttype"));
				m.setCoco_compactid(rs.getString("coco_compactid"));
				m.setCoco_servicearea(rs.getString("coco_servicearea"));
				m.setCoco_signdate(rs.getString("coco_signdate"));
				m.setCoco_stopdate(rs.getString("coco_stopdate"));
				m.setCoco_stopreason(rs.getString("coco_stopreason"));
				m.setCoco_stoptype(rs.getString("coco_stoptype"));
				m.setCoco_inuredate(rs.getString("coco_inuredate"));
				m.setCoco_indate(rs.getString("coco_indate"));
				m.setCoco_delay(rs.getString("coco_delay"));
				m.setCoco_remark(rs.getString("coco_remark"));
				m.setCoco_state(rs.getInt("coco_state"));
				m.setCoco_addtime(rs.getString("coco_addtime"));
				m.setCoco_addname(rs.getString("coco_addname"));
				m.setCompany(rs.getString("company"));
				m.setCoba_shortname(rs.getString("coba_client"));
				m.setCoco_returndate(rs.getString("coco_returndate"));
				m.setCoco_signplace(rs.getString("coco_signplace"));
				m.setCoco_money(rs.getString("coco_money"));
				m.setCoco_invoice(rs.getString("coco_invoice"));
				m.setCoco_filedate(rs.getString("coco_filedate"));
				m.setCoco_fileid(rs.getString("coco_fileid"));
				m.setCoco_printdate(rs.getString("coco_printdate"));
				m.setState(rs.getString("state"));
				m.setCoco_auditingdate(rs.getString("coco_auditingdate"));
				if (rs.getString("coof_fee") != null) {
					m.setCoof_fee(rs.getString("coof_fee").substring(0,
							rs.getString("coof_fee").length() - 2));
				}
				m.setCoco_tapr_id(rs.getInt("coco_tapr_id"));
				m.setCoco_class(rs.getString("co_type"));
				m.setCoco_chs_copies(rs.getString("coco_chs_copies"));
				m.setCoco_en_copies(rs.getString("coco_en_copies"));
				m.setCoco_shebao(rs.getString("coco_shebao"));
				m.setCoco_house(rs.getString("coco_house"));
				m.setCoco_opp(rs.getString("coco_opp"));
				m.setCoco_houseperfee(rs.getInt("coco_houseperfee"));
				m.setCoco_gzperfee(rs.getInt("coco_gzperfee"));
				m.setCoco_gs(rs.getString("coco_gs"));
				m.setCoco_ifgzpay(rs.getInt("coco_ifgzpay"));
				m.setCoco_paydate(rs.getInt("coco_paydate"));
				m.setCoco_fl_p(rs.getDouble("coco_fl_p"));
				m.setCoco_fw_p(rs.getDouble("coco_fw_p"));
				m.setCoco_dk_p(rs.getDouble("coco_dk_p"));
				m.setCoco_autst(rs.getInt("coco_autst"));

				CoCoBaseList.add(m);
			}

			ResultSet rs1 = getCoCoBaseBC(str);
			while (rs1.next()) {
				CoCoBaseList.add(new CoCompactModel(rs1.getInt("ccsa_id"), rs1
						.getString("sacid"), rs1.getString("coco_cola_id"), rs1
						.getString("coco_compacttype"), rs1
						.getString("coco_compactid"), rs1
						.getString("coco_servicearea"), rs1
						.getString("coco_signdate"), rs1
						.getString("coco_stopdate"), rs1
						.getString("coco_stopreason"), rs1
						.getString("coco_stoptype"), rs1.getString("inurdate"),
						rs1.getString("indate"), rs1.getString("coco_delay"),
						rs1.getString("coco_remark"), rs1.getInt("coco_state"),
						rs1.getString("coco_addtime"), rs1
								.getString("coco_addname"), "", rs1
								.getString("coba_client"), rs1
								.getString("coco_returndate"), rs1
								.getString("coco_signplace"), rs1
								.getString("coco_money"), rs1
								.getString("coco_invoice"), rs1
								.getString("coco_filedate"), rs1
								.getString("coco_fileid"), rs1
								.getString("coco_printdate"), "", rs1
								.getString("coco_auditingdate"), "0.0000", rs1
								.getInt("coco_tapr_id"), rs1
								.getString("co_type"), rs1
								.getString("coco_chs_copies"), rs1
								.getString("coco_en_copies"), rs1
								.getString("coco_shebao"), rs1
								.getString("coco_house"), rs1
								.getString("coco_opp")));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return CoCoBaseList;
	}

	// 根据查询语句获取所有公司合同(CoCompact_CoLa_CoBase_V)
	public List<CoCompactModel> getCoCoBaseBList(String str) {
		List<CoCompactModel> CoCoBaseList = new ArrayList<>();
		CoCompactModel m = null;
		try {
			ResultSet rs = getCoCoBaseBlist(str);
			while (rs.next()) {
				m = new CoCompactModel();
				m.setCoco_id(rs.getInt("coco_id"));
				m.setCid(rs.getString("cid"));
				m.setCoco_cola_id(rs.getString("coco_cola_id"));
				m.setCoco_compacttype(rs.getString("coco_compacttype"));
				m.setCoco_compactid(rs.getString("coco_compactid"));
				m.setCoco_servicearea(rs.getString("coco_servicearea"));
				m.setCoco_signdate(rs.getString("coco_signdate"));
				m.setCoco_stopdate(rs.getString("coco_stopdate"));
				m.setCoco_stopreason(rs.getString("coco_stopreason"));
				m.setCoco_stoptype(rs.getString("coco_stoptype"));
				m.setCoco_inuredate(rs.getString("coco_inuredate"));
				m.setCoco_indate(rs.getString("coco_indate"));
				m.setCoco_delay(rs.getString("coco_delay"));
				m.setCoco_remark(rs.getString("coco_remark"));
				m.setCoco_state(rs.getInt("coco_state"));
				m.setCoco_addtime(rs.getString("coco_addtime"));
				m.setCoco_addname(rs.getString("coco_addname"));
				m.setCompany(rs.getString("company"));
				m.setCoba_shortname(rs.getString("coba_client"));
				m.setCoco_returndate(rs.getString("coco_returndate"));
				m.setCoco_signplace(rs.getString("coco_signplace"));
				m.setCoco_money(rs.getString("coco_money"));
				m.setCoco_invoice(rs.getString("coco_invoice"));
				m.setCoco_filedate(rs.getString("coco_filedate"));
				m.setCoco_fileid(rs.getString("coco_fileid"));
				m.setCoco_printdate(rs.getString("coco_printdate"));
				m.setState(rs.getString("state"));
				m.setCoco_auditingdate(rs.getString("coco_auditingdate"));
				if (rs.getString("coof_fee") != null) {
					m.setCoof_fee(rs.getString("coof_fee").substring(0,
							rs.getString("coof_fee").length() - 2));
				}
				m.setCoco_tapr_id(rs.getInt("coco_tapr_id"));
				m.setCoco_class(rs.getString("co_type"));
				m.setCoco_chs_copies(rs.getString("coco_chs_copies"));
				m.setCoco_en_copies(rs.getString("coco_en_copies"));
				m.setCoco_shebao(rs.getString("coco_shebao"));
				m.setCoco_house(rs.getString("coco_house"));
				m.setCoco_opp(rs.getString("coco_opp"));
				m.setCoco_houseperfee(rs.getInt("coco_houseperfee"));
				m.setCoco_gzperfee(rs.getInt("coco_gzperfee"));
				m.setCoco_gs(rs.getString("coco_gs"));
				m.setCoco_ifgzpay(rs.getInt("coco_ifgzpay"));

				CoCoBaseList.add(m);
			}

			ResultSet rs1 = getCoCoBaseBCBlist(str);
			while (rs1.next()) {
				CoCoBaseList.add(new CoCompactModel(rs1.getInt("ccsa_id"), rs1
						.getString("sacid"), rs1.getString("coco_cola_id"), rs1
						.getString("coco_compacttype"), rs1
						.getString("coco_compactid"), rs1
						.getString("coco_servicearea"), rs1
						.getString("coco_signdate"), rs1
						.getString("coco_stopdate"), rs1
						.getString("coco_stopreason"), rs1
						.getString("coco_stoptype"), rs1.getString("inurdate"),
						rs1.getString("indate"), rs1.getString("coco_delay"),
						rs1.getString("coco_remark"), rs1.getInt("coco_state"),
						rs1.getString("coco_addtime"), rs1
								.getString("coco_addname"), "", rs1
								.getString("coba_client"), rs1
								.getString("coco_returndate"), rs1
								.getString("coco_signplace"), rs1
								.getString("coco_money"), rs1
								.getString("coco_invoice"), rs1
								.getString("coco_filedate"), rs1
								.getString("coco_fileid"), rs1
								.getString("coco_printdate"), "", rs1
								.getString("coco_auditingdate"), "0.0000", rs1
								.getInt("coco_tapr_id"), rs1
								.getString("co_type"), rs1
								.getString("coco_chs_copies"), rs1
								.getString("coco_en_copies"), rs1
								.getString("coco_shebao"), rs1
								.getString("coco_house"), rs1
								.getString("coco_opp")));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return CoCoBaseList;
	}

	// 获取添加人
	public ArrayList getCoCoAddnameList(String str) {
		ArrayList CoCoAddnameList = new ArrayList();
		try {
			ResultSet rs = getCoCoAddname(str);
			while (rs.next()) {
				CoCoAddnameList.add(new CoCompactModel(rs
						.getString("coco_addname")));
			}
		} catch (Exception e) {
			System.out.println(e.toString());
		}
		return CoCoAddnameList;
	}

	public CoCompactModel getCoCompactInfo(String coco_id) {
		List<CoCompactModel> list = new ArrayList<>();
		String sql = "select coco_id,cast(cid as nvarchar(5)) cid,coco_compacttype,coco_compactid,coco_servicearea,coco_signdate,coco_stopdate,coco_stopreason,coco_stoptype,coco_inuredate,coco_indate,coco_delay,coco_remark,coco_state,coco_addname,coco_returndate,coco_signplace,coco_money,coco_invoice,coco_filedate,coco_fileid,coco_printdate,coco_auditingdate,coco_tapr_id,coco_shebao,convert(varchar(50),coco_shebaoID) as coco_shebaoID,coco_shebaoAcc,coco_shebaobank,cast(coco_Injury as nvarchar(6)) coco_Injury,coco_house,coco_houseid,coco_cpp,coco_opp,coco_houseacc,coco_housebank,coco_gzmonth,coco_gsmonth,coco_sbfee,coco_housefee,coco_sbperfee,coco_gsfee,coco_gzpay,coco_wttype,cast(coco_sb_sye as nvarchar(6)) coco_sb_sye,coco_houseperfee,coco_gzperfee,coco_ifgzpay,coco_gs"
				+ " from CoCompact_CoLa_CoBase_V where coco_id=" + coco_id;
		try {
			list = new dbconn().find(sql, CoCompactModel.class,
					dbconn.parseSmap(CoCompactModel.class));
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list.size() > 0 ? list.get(0) : null;
	}
}
