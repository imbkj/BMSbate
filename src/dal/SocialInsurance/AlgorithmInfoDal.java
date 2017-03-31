package dal.SocialInsurance;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import Model.CoAgencyBaseCityRelViewModel;
import Model.EmCommissionOutFeeDetailChangeModel;
import Model.EmCommissionOutModel;
import Model.EmShebaoUpdateModel;
import Model.SocialInsuranceAlgorithmViewModel;
import Model.SocialInsuranceClassInfoViewModel;
import Model.SocialInsuranceDecimalModel;
import Model.SocialInsuranceStandardModel;
import Util.DateStringChange;
import Conn.dbconn;

public class AlgorithmInfoDal {
	private static dbconn conn = new dbconn();

	// 根据ID获取机构名称及服务城市数据集
	private ResultSet getBaseCityByIdRs(int id) throws SQLException {
		ResultSet rs = null;
		String sql = "SELECT * FROM CoAgencyBaseCityRel_view where cabc_id=?";
		PreparedStatement psmt = conn.getpre(sql);
		psmt.setInt(1, id);
		rs = psmt.executeQuery();
		return rs;
	}

	// 获取社保或公积金标准数据集(type:1社保2公积金)
	private ResultSet getStandardRs(int type) throws SQLException {
		ResultSet rs = null;
		String sql = "select * from SocialInsuranceStandard where (sist_type=? or sist_type=0) and sist_state=1";
		PreparedStatement psmt = conn.getpre(sql);
		psmt.setInt(1, type);
		rs = psmt.executeQuery();
		return rs;
	}

	// 获取项目小数处理列表数据集
	private ResultSet getDecimalRs() throws Exception {
		ResultSet rs = null;
		String sql = "select * from SocialInsuranceDecimal where side_state=1";
		rs = conn.GRS(sql);
		return rs;
	}

	// 获取项目及类型列表数据集
	private ResultSet getSiClassRs(int cabc_id) throws Exception {
		ResultSet rs = null;
		String sql = "select sicl_id,sicl_class,sicl_name,(case sicl_payunit when 1 then '企业' when 2 then '个人' end) as sicl_payunit,sicl_ifclass,sicl_ifname from SocialInsuranceClass sicl left join CoAgencyBaseCityRel cr on cr.cabc_id=? and sicl.sicl_ppc_id=cr.cabc_ppc_id where sicl_state = 1 or cr.cabc_id is not null order by sicl_order";
		PreparedStatement psmt = conn.getpre(sql);
		psmt.setInt(1, cabc_id);
		rs = psmt.executeQuery();
		return rs;
	}

	// 获取项目及类型列表
	public ArrayList<SocialInsuranceClassInfoViewModel> getSiClass(int cabc_id) {
		ArrayList<SocialInsuranceClassInfoViewModel> list = new ArrayList<SocialInsuranceClassInfoViewModel>();
		try {
			ResultSet rs = getSiClassRs(cabc_id);
			while (rs.next()) {
				list.add(new SocialInsuranceClassInfoViewModel(rs
						.getInt("sicl_id"), rs.getString("sicl_class"), rs
						.getString("sicl_name"), rs.getString("sicl_payunit"),
						rs.getInt("sicl_ifclass"), rs.getInt("sicl_ifname")));
			}
		} catch (Exception e) {
			System.out.println(e.toString());
		}
		return list;
	}

	// 获取项目小数处理列表
	public ArrayList<SocialInsuranceDecimalModel> getDecimal() {
		ArrayList<SocialInsuranceDecimalModel> list = new ArrayList<SocialInsuranceDecimalModel>();
		try {
			ResultSet rs = getDecimalRs();
			while (rs.next()) {
				list.add(new SocialInsuranceDecimalModel(rs.getInt("side_id"),
						rs.getString("side_decimal")));
			}
		} catch (Exception e) {
			System.out.println(e.toString());
		}
		return list;
	}

	// 获取社保或公积金标准(type:1社保2公积金)
	public ArrayList<SocialInsuranceStandardModel> getStandard(int type) {
		ArrayList<SocialInsuranceStandardModel> list = new ArrayList<SocialInsuranceStandardModel>();
		try {
			ResultSet rs = getStandardRs(type);
			while (rs.next()) {
				list.add(new SocialInsuranceStandardModel(rs.getInt("sist_id"),
						rs.getInt("sist_type"), rs.getString("sist_standard")));
			}
		} catch (Exception e) {
			System.out.println(e.toString());
		}
		return list;
	}

	// 根据ID获取机构名称及服务城市
	public CoAgencyBaseCityRelViewModel getBaseCityById(int id) {
		CoAgencyBaseCityRelViewModel m = null;
		try {
			ResultSet rs = getBaseCityByIdRs(id);
			rs.next();
			m = new CoAgencyBaseCityRelViewModel(rs.getInt("cabc_id"),
					rs.getInt("cabc_coab_id"), rs.getString("coab_name"),
					rs.getString("coab_namespell"),
					rs.getString("coab_setuptype"), rs.getString("province"),
					rs.getString("city"));
		} catch (Exception e) {
			System.out.println(e.toString());
		}
		return m;
	}

	// 根据sial_id获取算法基本信息数据集
	private ResultSet getSiAlBaseRs(int sial_id) throws Exception {
		ResultSet rs = null;
		String sql = "select * from SocialInsuranceAlgorithm_view where sial_id=?";
		PreparedStatement psmt = conn.getpre(sql);
		psmt.setInt(1, sial_id);
		rs = psmt.executeQuery();
		return rs;
	}

	// 根据sial_id获取算法基本信息
	public SocialInsuranceAlgorithmViewModel getSiAlBase(int sial_id) {
		SocialInsuranceAlgorithmViewModel m = new SocialInsuranceAlgorithmViewModel();
		try {
			ResultSet rs = getSiAlBaseRs(sial_id);
			rs.next();
			m.setCity(rs.getString("city"));
			m.setCoab_name(rs.getString("coab_name"));
			m.setSoin_id(rs.getInt("soin_id"));
			m.setSoin_title(rs.getString("soin_title"));
			m.setSoin_cabc_id(rs.getInt("soin_cabc_id"));
			m.setSial_id(rs.getInt("sial_id"));
			m.setSial_execdate(DateStringChange.StringtoDate(
					rs.getString("execdate"), "yyyy-MM-dd"));
			m.setSial_standard(rs.getString("sial_standard"));
			m.setSial_sb_standard(rs.getInt("sial_sb_standard"));
			m.setSial_gjj_standard(rs.getInt("sial_gjj_standard"));
			m.setSial_avg_salary(rs.getBigDecimal("sial_avg_salary"));
			m.setSial_lowest_salary(rs.getBigDecimal("sial_lowest_salary"));
			m.setSial_city_remark(rs.getString("sial_city_remark"));
			m.setSial_sb_remark(rs.getString("sial_sb_remark"));
			m.setSial_gjj_remark(rs.getString("sial_gjj_remark"));
			m.setSial_cb_remark(rs.getString("sial_cb_remark"));
			m.setSial_gjj_standardstr(rs.getString("gjjstandard"));
			m.setSial_sb_standardstr(rs.getString("sbstandard"));
		} catch (Exception e) {
			System.out.println(e.toString());
		}
		return m;
	}

	// 根据sial_id获取算法项目列表数据集
	private ResultSet getSiAlInfoRs(int sial_id) throws Exception {
		ResultSet rs = null;
		String sql = "select * from SocialInsuranceClassInfo_view where siai_sial_id=? order by sicl_order";
		PreparedStatement psmt = conn.getpre(sql);
		psmt.setInt(1, sial_id);
		rs = psmt.executeQuery();
		return rs;
	}

	// 根据sial_id获取算法项目详细信息
	public ArrayList<SocialInsuranceClassInfoViewModel> getSiAlInfo(int sial_id) {
		ArrayList<SocialInsuranceClassInfoViewModel> list = new ArrayList<SocialInsuranceClassInfoViewModel>();
		try {
			ResultSet rs = getSiAlInfoRs(sial_id);
			while (rs.next()) {
				list.add(new SocialInsuranceClassInfoViewModel(rs
						.getInt("sicl_id"), rs.getString("sicl_class"), rs
						.getString("sicl_name"), rs.getString("sicl_payunit"),
						rs.getInt("sicl_ifclass"), rs.getInt("sicl_ifname"), rs
								.getInt("siai_id"), rs.getInt("siai_sial_id"),
						rs.getInt("siai_side_id"), rs
								.getBigDecimal("siai_basic_u"), rs
								.getBigDecimal("siai_basic_d"), rs
								.getBigDecimal("siai_deposit_u"), rs
								.getBigDecimal("siai_deposit_d"), rs
								.getString("siai_proportion"), rs
								.getString("siai_algorithm"), rs
								.getString("siai_remark"), rs
								.getString("side_decimal")));
			}
		} catch (Exception e) {
			System.out.println(e.toString());
		}
		return list;
	}

	// 获取算法的执行年月列表数据集
	private ResultSet getExecdateListRs(int sial_id) throws Exception {
		ResultSet rs = null;
		String sql = "select si.sial_id,sial_execdate=(convert(varchar(10),year(sial_execdate))+'年'+convert(varchar(10),month(sial_execdate))+'月') from SocialInsuranceAlgorithm si where sial_soin_id=(select sial_soin_id from SocialInsuranceAlgorithm where sial_id=?) and sial_state=1 order by sial_execdate desc";
		PreparedStatement psmt = conn.getpre(sql);
		psmt.setInt(1, sial_id);
		rs = psmt.executeQuery();
		return rs;
	}

	// 获取算法的执行年月列表数据
	public ArrayList<String[]> getExecdateList(int sial_id) {
		ArrayList<String[]> list = new ArrayList<String[]>();
		try {
			ResultSet rs = getExecdateListRs(sial_id);
			while (rs.next()) {
				list.add(new String[] { rs.getString("sial_id"),
						rs.getString("sial_execdate") });
			}
		} catch (Exception e) {
			System.out.println(e.toString());
		}
		return list;
	}

	// 查找在册数据
	public List<EmCommissionOutModel> getRegDataBySoinId(int soin_id) {
		List<EmCommissionOutModel> list = new ArrayList<EmCommissionOutModel>();
		StringBuilder sql = new StringBuilder();
		sql.append("select eo.cid,co.coba_shortname,ecou_id,co.coba_client,eo.gid,em.emba_name,eo.ecou_idcard from EmCommissionOut eo ");
		sql.append("inner join CoBase co on eo.cid=co.CID ");
		sql.append("inner join EmBase em on eo.gid=em.gid ");
		sql.append("where  ecou_state=1 and ecou_soin_id=? ");
		sql.append("order by ecou_addtype desc");
		PreparedStatement psmt = conn.getpre(sql.toString());
		try {
			psmt.setInt(1, soin_id);
			ResultSet rs = psmt.executeQuery();
			EmCommissionOutModel m;
			while (rs.next()) {
				m = new EmCommissionOutModel();
				m.setCid(rs.getInt("cid"));
				m.setEcou_id(rs.getInt("ecou_id"));
				m.setCoba_shortname(rs.getString("coba_shortname"));
				m.setEcou_client(rs.getString("coba_client"));
				m.setGid(rs.getInt("gid"));
				m.setEmba_name(rs.getString("emba_name"));
				m.setEcou_idcard(rs.getString("ecou_idcard"));
				list.add(m);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}

	// 根据sial_id查找在册表的明细数据
	public List<EmCommissionOutFeeDetailChangeModel> getRegDataFeeDetailBySoinId(
			int sial_id) {
		List<EmCommissionOutFeeDetailChangeModel> list = new ArrayList<EmCommissionOutFeeDetailChangeModel>();
		EmCommissionOutFeeDetailChangeModel m = null;
		try {
			ResultSet rs = getDetail(sial_id, 1);
			while (rs.next()) {
				m = new EmCommissionOutFeeDetailChangeModel();
				m.setEofc_id(rs.getInt("eofd_id"));
				m.setEofc_co_base(rs.getBigDecimal("eofd_co_base"));
				m.setEofc_em_base(rs.getBigDecimal("eofd_em_base"));
				m.setEofc_cp(rs.getString("eofd_cp"));
				m.setEofc_op(rs.getString("eofd_op"));
				m.setEofc_sicl_id(rs.getInt("eofd_sicl_id"));
				list.add(m);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}

	// 根据sial_id查找变更表的明细数据
	public List<EmCommissionOutFeeDetailChangeModel> getRegDataFeeDetailChangeBySoinId(
			int sial_id) {
		List<EmCommissionOutFeeDetailChangeModel> list = new ArrayList<EmCommissionOutFeeDetailChangeModel>();
		EmCommissionOutFeeDetailChangeModel m = null;
		try {
			ResultSet rs = getDetail(sial_id, 3);
			while (rs.next()) {
				m = new EmCommissionOutFeeDetailChangeModel();
				m.setEofc_id(rs.getInt("eofc_id"));
				m.setEofc_co_base(rs.getBigDecimal("eofc_co_base"));
				m.setEofc_em_base(rs.getBigDecimal("eofc_em_base"));
				m.setEofc_cp(rs.getString("eofc_cp"));
				m.setEofc_op(rs.getString("eofc_op"));
				m.setEofc_sicl_id(rs.getInt("eofc_sicl_id"));
				list.add(m);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}

	// 根据算法标准查询委托出明细数据(dataType:1、在册表数据2、历史表数据3、变更表数据)
	public ResultSet getDetail(int sial_id, int dataType) {
		ResultSet rs = null;
		try {
			rs = conn.GRS("exec SiAlgorithmSelRegData_p_zmj " + sial_id + ","
					+ dataType);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return rs;
	}

	// 根据算法名称查找本地社保在册数据
	public List<EmShebaoUpdateModel> getLocalShebaoUpdate(String soin_title,
			int ownmonth) {
		List<EmShebaoUpdateModel> list = new ArrayList<EmShebaoUpdateModel>();
		String sql = "select eu.*,ef.emsf_soin_title,co.coba_client from EmShebaoUpdate eu inner join EmShebaoFormula ef on eu.Esiu_formulaid=ef.id inner join cobase co on co.cid=eu.cid where ef.emsf_soin_title=? and eu.ownmonth>=?";
		PreparedStatement psmt = conn.getpre(sql);
		try {
			psmt.setString(1, soin_title);
			psmt.setInt(2, ownmonth);
			ResultSet rs = psmt.executeQuery();
			EmShebaoUpdateModel m;
			while (rs.next()) {
				m = new EmShebaoUpdateModel();
				m.setId(rs.getInt("id"));
				m.setOwnmonth(rs.getInt("ownmonth"));
				m.setCid(rs.getInt("cid"));
				m.setEsiu_company(rs.getString("esiu_company"));
				m.setGid(rs.getInt("gid"));
				m.setEsiu_name(rs.getString("esiu_name"));
				m.setEmsf_soin_title(rs.getString("emsf_soin_title"));
				m.setEsiu_idcard(rs.getString("esiu_idcard"));
				m.setEsiu_yl(rs.getString("esiu_yl"));
				m.setEsiu_gs(rs.getString("esiu_gs"));
				m.setEsiu_sye(rs.getString("esiu_sye"));
				m.setEsiu_syu(rs.getString("esiu_syu"));
				m.setEsiu_yltype(rs.getString("esiu_yltype"));
				m.setEsiu_radix(rs.getInt("esiu_radix"));
				m.setEsiu_client(rs.getString("coba_client"));
				list.add(m);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}
	
	// 根据算法名称 公司编号和员工编号 查找本地社保在册数据
		public List<EmShebaoUpdateModel> getLocalShebaoUpdateByCidGid(String soin_title,
				int ownmonth,String str) {
			List<EmShebaoUpdateModel> list = new ArrayList<EmShebaoUpdateModel>();
			String sql = "select eu.*,ef.emsf_soin_title,co.coba_client from (select * from EmShebaoUpdate where 1=1 "+str+")eu inner join EmShebaoFormula ef on eu.Esiu_formulaid=ef.id inner join cobase co on co.cid=eu.cid where ef.emsf_soin_title=? and eu.ownmonth>=?";
			PreparedStatement psmt = conn.getpre(sql);
			try {
				//System.out.println(sql);
				//System.out.println(soin_title);
				//System.out.println(ownmonth);
				psmt.setString(1, soin_title);
				psmt.setInt(2, ownmonth);
				ResultSet rs = psmt.executeQuery();
				EmShebaoUpdateModel m;
				while (rs.next()) {
					m = new EmShebaoUpdateModel();
					m.setId(rs.getInt("id"));
					m.setOwnmonth(rs.getInt("ownmonth"));
					m.setCid(rs.getInt("cid"));
					m.setEsiu_company(rs.getString("esiu_company"));
					m.setGid(rs.getInt("gid"));
					m.setEsiu_name(rs.getString("esiu_name"));
					m.setEmsf_soin_title(rs.getString("emsf_soin_title"));
					m.setEsiu_idcard(rs.getString("esiu_idcard"));
					m.setEsiu_yl(rs.getString("esiu_yl"));
					m.setEsiu_gs(rs.getString("esiu_gs"));
					m.setEsiu_sye(rs.getString("esiu_sye"));
					m.setEsiu_syu(rs.getString("esiu_syu"));
					m.setEsiu_yltype(rs.getString("esiu_yltype"));
					m.setEsiu_radix(rs.getInt("esiu_radix"));
					m.setEsiu_client(rs.getString("coba_client"));
					list.add(m);
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
			return list;
		}
	
}
