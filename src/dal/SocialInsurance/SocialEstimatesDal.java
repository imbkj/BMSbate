package dal.SocialInsurance;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import Conn.dbconn;
import Model.SocialInsuranceAlgorithmViewModel;

public class SocialEstimatesDal {
	private dbconn conn = new dbconn();

	// 获取有算法的城市
	public List<String> getInsuranceCityList() {
		List<String> list = new ArrayList<String>();
		StringBuilder sql = new StringBuilder();
		sql.append("select ppc.name from SocialInsurance si ");
		sql.append("inner join CoAgencyBaseCityRel cr on si.soin_cabc_id=cr.cabc_id ");
		sql.append("inner join PubProCity ppc on ppc.id=cr.cabc_ppc_id ");
		sql.append("where si.soin_state=1 ");
		sql.append("group by ppc.name ");
		sql.append("order by ppc.name ");
		try {
			ResultSet rs = conn.GRS(sql.toString());
			while (rs.next()) {
				list.add(rs.getString("name"));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}

	// 根据城市获取算法
	public List<SocialInsuranceAlgorithmViewModel> getInsuranceList(String city) {
		List<SocialInsuranceAlgorithmViewModel> list = new ArrayList<SocialInsuranceAlgorithmViewModel>();
		SocialInsuranceAlgorithmViewModel m;
		StringBuilder sql = new StringBuilder();
		sql.append("select si.soin_id,si.soin_title,ca.coab_name,siai.siai_proportion from SocialInsurance si ");
		sql.append("inner join (select sial_soin_id,sial_id,ROW_NUMBER() over(partition by sial_soin_id order by sial_execdate desc) id from SocialInsuranceAlgorithm where sial_execdate<=getdate() and sial_state=1) sa on si.soin_id=sa.sial_soin_id and sa.id=1 ");
		sql.append("inner join (select siai.siai_sial_id,siai.siai_proportion from SocialInseranceAlgorithmInfo siai inner join SocialInsuranceClass sc on siai.siai_sicl_id=sc.sicl_id and sc.sicl_name='住房公积金' and sc.sicl_payunit=1) siai on siai.siai_sial_id=sa.sial_id ");
		sql.append("inner join CoAgencyBaseCityRel cr on si.soin_cabc_id=cr.cabc_id ");
		sql.append("inner join PubProCity ppc on ppc.id=cr.cabc_ppc_id ");
		sql.append("inner join CoAgencyBase ca on cr.cabc_coab_id=ca.coab_id ");
		sql.append("where ppc.name=? and si.soin_state=1");
		try {
			PreparedStatement psmt = conn.getpre(sql.toString());
			psmt.setString(1, city);
			ResultSet rs = psmt.executeQuery();
			while (rs.next()) {
				m = new SocialInsuranceAlgorithmViewModel();
				m.setSoin_id(rs.getInt("soin_id"));
				m.setSoin_title(rs.getString("soin_title"));
				m.setCoab_name(rs.getString("coab_name"));
				m.setGjj_proportion(rs.getString("siai_proportion"));
				list.add(m);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}
}
