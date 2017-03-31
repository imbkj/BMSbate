package dal.SocialInsurance;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import Conn.dbconn;
import Model.PubProCityModel;
import Model.SocialExportModel;
import Model.SocialInsuranceAlgorithmViewModel;
import Model.SocialInsuranceClassInfoViewModel;

public class SocialExportDal {
	private dbconn conn = new dbconn();

	// 获取有算法的城市
	public List<PubProCityModel> getInsuranceCityList() {
		List<PubProCityModel> list = new ArrayList<PubProCityModel>();
		PubProCityModel m;
		StringBuilder sql = new StringBuilder();
		sql.append("select ppc.id,('('+ppc.spell+') '+ppc.name)name,count(soin.soin_id)cou from SocialInsurance soin ");
		sql.append("inner join CoAgencyBaseCityRel cr on soin.soin_cabc_id=cr.cabc_id ");
		sql.append("inner join PubProCity ppc on cr.cabc_ppc_id=ppc.id ");
		sql.append("where soin.soin_state=1 ");
		
		//sql.append(" and  ppc.name IN ('南京','广州','上海','合肥','济南','长沙','郑州','北京','西安','武汉','成都','杭州','南宁','沈阳','石家庄','潮州','赣州','沧州','汕头','哈尔滨','太原','邢台','张家港','重庆','东莞','南昌','昆明','凯里','抚顺','天津','湛江','长春','大连','鞍山','衡阳','怀化','肥东','新乡','吉林','呼和浩特','荆州','宜春','湘潭','银川','鄂州','阜阳','兰州','玉林','丹阳','包头','洛阳','中山','萍乡','唐山','上饶','岳阳','贵阳','马鞍山','铜陵','宁德','连云港','绍兴','梅州','运城','柳州','桂林','滁州','蚌埠','襄阳','钦州','浏阳','葫芦岛','盘锦','泉州') ");
		
		
		
		
		sql.append("group by ppc.id,ppc.name,ppc.spell ");
		sql.append("order by ppc.spell ");
		try {
			ResultSet rs = conn.GRS(sql.toString());
			while (rs.next()) {
				m = new PubProCityModel();
				m.setId(rs.getInt("id"));
				m.setName(rs.getString("name"));
				m.setCou(rs.getInt("cou"));
				list.add(m);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}
	


	// 按城市获取算法明细数据
	public Map<Integer, SocialExportModel> getSocialInsuranceByCity(
			String cityIdList) {
		Map<Integer, SocialExportModel> cityMap = new HashMap<Integer, SocialExportModel>();
		SocialExportModel seM;
		SocialInsuranceAlgorithmViewModel sialM;
		List<SocialInsuranceClassInfoViewModel> ciList;
		SocialInsuranceClassInfoViewModel siM;
		int cityId;
		int sial_id;
		StringBuilder sql = new StringBuilder();
		sql.append("select *,(convert(varchar(10),year(sial_execdate))+'年'+convert(varchar(10),month(sial_execdate))+'月')execdate from SocialInsurance soin ");
		sql.append("inner join CoAgencyBaseCityRel cr on soin.soin_cabc_id=cr.cabc_id ");
		sql.append("inner join ");
		sql.append("(select si.soin_id,sial_id,sial_execdate,city,coab_name,sial_avg_salary,sial_lowest_salary,sial_sb_remark,sial_gjj_remark,sial_cb_remark,sial_addname,ROW_NUMBER() over(partition by si.soin_title,si.soin_cabc_id,sa.city order by sial_execdate desc) id ");
		sql.append("from SocialInsuranceAlgorithm_view sa left join SocialInsurance si on sa.sial_soin_id=si.soin_id ");
		sql.append("where si.soin_state=1 and sa.sial_state=1 )n on n.soin_id=soin.soin_id and id=1 ");
		sql.append("inner join SocialInsuranceClassInfo_view siai on siai.siai_sial_id=n.sial_id ");
		sql.append("where cr.cabc_ppc_id in(");
		sql.append(cityIdList);
		sql.append(") ");
		sql.append("order by cabc_ppc_id,soin.soin_id");
		try {
			System.out.println(sql);
			ResultSet rs = conn.GRS(sql.toString());
			while (rs.next()) {
				cityId = rs.getInt("cabc_ppc_id");
				if (!cityMap.containsKey(cityId)) {
					seM = new SocialExportModel();
					seM.setCity(rs.getString("city"));
					seM.setSoinMap(new HashMap<Integer, SocialInsuranceAlgorithmViewModel>());
					cityMap.put(cityId, seM);
				} else {
					seM = cityMap.get(cityId);
				}
				sial_id = rs.getInt("sial_id");
				if (!seM.getSoinMap().containsKey(sial_id)) {
					sialM = new SocialInsuranceAlgorithmViewModel();
					sialM.setCoab_name(rs.getString("coab_name"));
					sialM.setSoin_title(rs.getString("soin_title"));
					sialM.setSial_execdatestr(rs.getString("execdate"));
					if (!"0.00".equals(rs.getString("sial_avg_salary"))
							&& !"0".equals(rs.getString("sial_avg_salary"))) {
						sialM.setSial_avg_salarystr(rs
								.getString("sial_avg_salary"));
					}
					if (!"0.00".equals(rs.getString("sial_lowest_salary"))
							&& !"0".equals(rs.getString("sial_lowest_salary"))) {
						sialM.setSial_lowest_salarystr(rs
								.getString("sial_lowest_salary"));
					}
					sialM.setSial_sb_remark(rs.getString("sial_sb_remark"));
					sialM.setSial_gjj_remark(rs.getString("sial_gjj_remark"));
					sialM.setSial_cb_remark(rs.getString("sial_cb_remark"));
					sialM.setSial_addname(rs.getString("sial_addname"));
					ciList = new ArrayList<SocialInsuranceClassInfoViewModel>();
					sialM.setClassInfoList(ciList);
					seM.getSoinMap().put(sial_id, sialM);
				} else {
					ciList = seM.getSoinMap().get(sial_id).getClassInfoList();
				}
				siM = new SocialInsuranceClassInfoViewModel();
				siM.setSicl_name(rs.getString("sicl_name"));
				siM.setSicl_payunit(rs.getString("sicl_payunit"));
				if (!"0.00".equals(rs.getString("siai_basic_u"))
						&& !"0".equals(rs.getString("siai_basic_u"))) {
					siM.setSiai_basic_u(rs.getString("siai_basic_u"));
				}
				if (!"0.00".equals(rs.getString("siai_basic_d"))
						&& !"0".equals(rs.getString("siai_basic_d"))) {
					siM.setSiai_basic_d(rs.getString("siai_basic_d"));
				}
				if (!"0.00".equals(rs.getString("siai_deposit_u"))
						&& !"0".equals(rs.getString("siai_deposit_u"))) {
					siM.setSiai_deposit_u(rs.getString("siai_deposit_u"));
				}
				if (!"0.00".equals(rs.getString("siai_deposit_d"))
						&& !"0".equals(rs.getString("siai_deposit_d"))) {
					siM.setSiai_deposit_d(rs.getString("siai_deposit_d"));
				}
				if (!"0.00".equals(rs.getString("siai_proportion"))
						&& !"0".equals(rs.getString("siai_proportion"))) {
					siM.setSiai_proportion(rs.getString("siai_proportion"));
				}

				siM.setSiai_algorithm(rs.getString("siai_algorithm"));
				siM.setSiai_remark(rs.getString("siai_remark"));
				ciList.add(siM);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return cityMap;
	}
}
