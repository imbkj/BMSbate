package dal.SocialInsurance;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

import Conn.dbconn;
import Model.SocialInsuranceAlgorithmViewModel;
import Model.SocialInsuranceChangeModel;
import Model.SocialInsuranceClassInfoViewModel;
import Util.DateStringChange;

public class AlgorithmChangeDal {
	private static dbconn conn = new dbconn();

	// 获取社保字典库变更信息数据(type:更变类型(1新增标准or更新算法or调整算法2停用标准or停用算法))
	public SocialInsuranceChangeModel getSocialInsuranceChange(int sich_id) {
		SocialInsuranceChangeModel m = new SocialInsuranceChangeModel();
		try {
			ResultSet rs = getSocialInsuranceChangeRs(sich_id);
			rs.next();
			m.setSich_id(rs.getInt("sich_id"));
			m.setSich_soin_id(rs.getInt("sich_soin_id"));
			m.setSich_sial_id(rs.getInt("sich_sial_id"));
			m.setSich_cabc_id(rs.getInt("sich_cabc_id"));
			m.setSich_change_type(rs.getInt("sich_change_type"));
			m.setSich_title(rs.getString("sich_title"));
			m.setSich_addname(rs.getString("sich_addname"));
			m.setSich_addtime(rs.getString("sich_addtime"));
			m.setSich_state(rs.getInt("sich_state"));
			m.setSich_tapr_id(rs.getInt("sich_tapr_id"));
			m.setCity(rs.getString("city"));
			m.setCoab_name(rs.getString("coab_name"));
			m.setExecdate(rs.getString("execdate"));
			m.setSich_ReturnReason(rs.getString("sich_ReturnReason"));
			m.setSich_Reason(rs.getString("sich_Reason"));
			m.setSial_execdate(rs.getDate("sial_execdate"));
		} catch (Exception e) {
			e.printStackTrace();
		}
		return m;
	}

	// 获取社保字典库变更信息数据集(type:更变类型(1新增标准or更新算法or调整算法2停用标准or停用算法))
	private ResultSet getSocialInsuranceChangeRs(int sich_id) {
		ResultSet rs = null;
		try {
			StringBuilder sql = new StringBuilder();
			sql.append("select sial_execdate,sich_id,sich_soin_id,sich_sial_id,sich_cabc_id,sich_change_type,sich_title,sich_addname,CONVERT(varchar(100), sich_addtime, 120) as sich_addtime,sich_state,sich_tapr_id,");
			sql.append("cabc.city,cabc.coab_name,sich_ReturnReason,sich_Reason,");
			sql.append(" execdate=(case when sich_change_type=4 or sich_change_type=5 then (convert(varchar(10),year(sial.sial_execdate))+'年'+convert(varchar(10),month(sial.sial_execdate))+'月') else (convert(varchar(10),year(siac.siac_execdate))+'年'+convert(varchar(10),month(siac.siac_execdate))+'月') end)");
			sql.append(" from SocialInsuranceChange sic");
			sql.append(" left join CoAgencyBaseCityRel_view cabc on sic.sich_cabc_id=cabc.cabc_id");
			sql.append(" left join SocialInsuranceAlgorithmChange siac on sic.sich_id=siac.siac_sich_id");
			sql.append(" left join SocialInsuranceAlgorithm sial on sial.sial_id=sic.sich_sial_id");
			sql.append(" where sich_id=?");

			PreparedStatement psmt = conn.getpre(sql.toString());
			psmt.setInt(1, sich_id);
			rs = psmt.executeQuery();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return rs;
	}

	// 根据sich_id获取算法变更基本信息
	public SocialInsuranceAlgorithmViewModel getSiAlChange(int sich_id) {
		SocialInsuranceAlgorithmViewModel m = new SocialInsuranceAlgorithmViewModel();
		try {
			ResultSet rs = conn.GRS("exec getSiAlChange_p_lwj " + sich_id);
			rs.next();
			m.setCity(rs.getString("city"));
			m.setCoab_name(rs.getString("coab_name"));
			m.setSoin_title(rs.getString("sich_title"));
			m.setSial_id(rs.getInt("siac_id"));
			m.setSial_execdate(DateStringChange.StringtoDate(
					rs.getString("execdate"), "yyyy-MM-dd"));
			m.setSial_standard(rs.getString("siac_standard"));
			m.setSial_sb_standard(rs.getInt("siac_sb_standard"));
			m.setSial_gjj_standard(rs.getInt("siac_gjj_standard"));
			m.setSial_avg_salary(rs.getBigDecimal("siac_avg_salary"));
			m.setSial_lowest_salary(rs.getBigDecimal("siac_lowest_salary"));
			m.setSial_city_remark(rs.getString("siac_city_remark"));
			m.setSial_sb_remark(rs.getString("siac_sb_remark"));
			m.setSial_gjj_remark(rs.getString("siac_gjj_remark"));
			m.setSial_cb_remark(rs.getString("siac_cb_remark"));
			m.setSial_gjj_standardstr(rs.getString("gjjstandard"));
			m.setSial_sb_standardstr(rs.getString("sbstandard"));
			m.setSich_change_type(rs.getInt("sich_change_type"));
			m.setSich_sial_id(rs.getInt("sich_sial_id"));
		} catch (Exception e) {
			System.out.println(e.toString());
		}
		return m;
	}

	// 根据siac_id获取算法项目变更详细信息
	public ArrayList<SocialInsuranceClassInfoViewModel> getSiAlInfoChange(
			int siac_id) {
		ArrayList<SocialInsuranceClassInfoViewModel> list = new ArrayList<SocialInsuranceClassInfoViewModel>();
		try {
			StringBuilder sql = new StringBuilder();
			sql.append("select sicl_id,sicl_class,sicl_name,(case sicl_payunit when 1 then '企业' when 2 then '个人' end) as sicl_payunit,sicl_ifclass,sicl_ifname,sicl_order,saic.*,sd.side_decimal");
			sql.append(" from SocialInsuranceClass sc left join SocialInseranceAlgorithmInfoChange saic on saic.saic_sicl_id=sc.sicl_id and saic_state=1 ");
			sql.append(" left join SocialInsuranceDecimal sd on saic.saic_side_id=sd.side_id");
			sql.append(" where sicl_state = 1 and saic_siac_id=? order by sicl_order");
			PreparedStatement psmt = conn.getpre(sql.toString());
			psmt.setInt(1, siac_id);
			ResultSet rs = psmt.executeQuery();
			while (rs.next()) {
				list.add(new SocialInsuranceClassInfoViewModel(rs
						.getInt("sicl_id"), rs.getString("sicl_class"), rs
						.getString("sicl_name"), rs.getString("sicl_payunit"),
						rs.getInt("sicl_ifclass"), rs.getInt("sicl_ifname"), rs
								.getInt("saic_id"), rs.getInt("saic_sicl_id"),
						rs.getInt("saic_side_id"), rs
								.getBigDecimal("saic_basic_u"), rs
								.getBigDecimal("saic_basic_d"), rs
								.getBigDecimal("saic_deposit_u"), rs
								.getBigDecimal("saic_deposit_d"), rs
								.getString("saic_proportion"), rs
								.getString("saic_algorithm"), rs
								.getString("saic_remark"), rs
								.getString("side_decimal")));
			}
		} catch (Exception e) {
			System.out.println(e.toString());
		}
		return list;
	}

	// 获取变更前的算法项目
	public SocialInsuranceClassInfoViewModel getOldSiAlInfoChange(
			int siai_sial_id, int siai_sicl_id) {
		SocialInsuranceClassInfoViewModel m = null;
		String sql = "select * from SocialInseranceAlgorithmInfo siai left join SocialInsuranceDecimal side on siai.siai_side_id=side.side_id where siai_sial_id=? and siai_sicl_id=?";
		try {
			PreparedStatement psmt = conn.getpre(sql.toString());
			psmt.setInt(1, siai_sial_id);
			psmt.setInt(2, siai_sicl_id);
			ResultSet rs = psmt.executeQuery();
			if (rs.next()) {
				m = new SocialInsuranceClassInfoViewModel();
				m.setSiai_algorithm(rs.getString("siai_algorithm"));
				m.setSiai_basic_dd(rs.getBigDecimal("siai_basic_d"));
				m.setSiai_basic_ud(rs.getBigDecimal("siai_basic_u"));
				m.setSiai_deposit_dd(rs.getBigDecimal("siai_deposit_d"));
				m.setSiai_deposit_ud(rs.getBigDecimal("siai_deposit_u"));
				m.setSiai_proportion(rs.getString("siai_proportion"));
				m.setSiai_remark(rs.getString("siai_remark"));
				m.setSide_decimal(rs.getString("side_decimal"));
			}
		} catch (Exception e) {
			System.out.println(e.toString());
		}
		return m;
	}
}
