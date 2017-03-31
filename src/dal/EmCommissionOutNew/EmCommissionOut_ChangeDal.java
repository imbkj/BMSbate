package dal.EmCommissionOutNew;

import java.math.BigDecimal;
import java.sql.CallableStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.zkoss.zul.ListModelList;

import Conn.dbconn;
import Controller.EmResidencePermit.newExcelImpl;
import Model.EmCommissionOutChangeModel;
import Model.EmCommissionOutFeeDetailChangeModel;
import Model.EmCommissionOutStandardModel;
import Model.SocialInsuranceClassInfoViewModel;
import R.RS;
import Util.SocialInsuranceCalculator;

public class EmCommissionOut_ChangeDal {
	private List<SocialInsuranceClassInfoViewModel> testlist = new ListModelList<>();
	private List<SocialInsuranceClassInfoViewModel> testgjjlist = new ListModelList<>();
	SocialInsuranceCalculator tesbll = new SocialInsuranceCalculator();

	// 获取委托主表明细
	public EmCommissionOutChangeModel getwt_list(String ecou_id)
			throws SQLException {
		dbconn db = new dbconn();
		ResultSet rs = null;
		EmCommissionOutChangeModel list = new EmCommissionOutChangeModel();
		String sqlstr = "select a.gid,a.cid,emba_name,coba_company,wtss_city,ecou_phone,ecou_mobile,ecou_sb_co_sum+ecou_gjj_co_sum co_to,ecou_sb_em_sum+ecou_gjj_em_sum em_to,ecou_gjj_sum,ecou_sb_sum,ecou_soin_id,wtss_title+'-'+wtot_feetitle wtss_title,soin_title,wtss_shebaoco,wtss_shebaopayty,wtss_gjjco,wtss_gjjpayty,wtss_laborcontract,ecou_compact_l,ecou_compact_f,ecou_sum,emba_sex,ecou_idcard,ecou_email,ecou_remark,ecou_sb_base,ecou_house_base,wtss_archives,ecou_domicile,ecou_salary,ecou_sb_co_sum,ecou_gjj_co_sum,ecou_sb_em_sum,ecou_gjj_em_sum,ecou_welfare_sum,ecou_soin_id,ecou_ecos_id,h.eofd_cp,coba_shortname,ecou_title_date,coab_id from EmCommissionOut a left join embase b on b.gid=a.gid left join cobase c on c.cid=a.cid left join wtoutFee d on d.Wtot_feeid=a.ecou_ecos_id left join WtServiceStandardrelation e on e.wtot_feeid=d.Wtot_feeid left join WtServiceStandard f on f.wtss_id=e.wtss_id left join SocialInsurance g on g.soin_id=a.ecou_soin_id left join (select eofd_ecou_id,eofd_cp from EmCommissionOutFeeDetail where eofd_name='住房公积金') h on h.eofd_ecou_id=a.ecou_id where ecou_id="
				+ ecou_id;
		try {
			System.out.println(sqlstr);
			rs = db.GRS(sqlstr);
			while (rs.next()) {
				EmCommissionOutChangeModel model = new EmCommissionOutChangeModel();
				list.setGid(rs.getInt("gid"));
				list.setCid(rs.getInt("cid"));
				list.setEmba_name(rs.getString("emba_name"));
				list.setCoba_company(rs.getString("coba_company"));
				list.setEcoc_phone(rs.getString("ecou_phone"));
				list.setEcoc_mobile(rs.getString("ecou_mobile"));
				list.setCity(rs.getString("wtss_city"));
				list.setEcoc_sb_co_sum(rs.getBigDecimal("co_to"));
				list.setEcoc_sb_em_sum(rs.getBigDecimal("em_to"));
				list.setEcoc_sb_sum(rs.getBigDecimal("ecou_sb_sum"));
				list.setEcoc_gjj_sum(rs.getBigDecimal("ecou_gjj_sum"));
				// wtss_title,soin_title,wtss_shebaoco,wtss_shebaopayty,wtss_gjjco,wtss_gjjpayty,wtss_laborcontract
				list.setType(rs.getString("wtss_title"));
				list.setSoin_title(rs.getString("soin_title"));
				list.setWtss_shebaoco(rs.getString("wtss_shebaoco"));
				list.setWtss_shebaopayty(rs.getString("wtss_shebaopayty"));
				list.setWtss_gjjco(rs.getString("wtss_gjjco"));
				list.setWtss_gjjpayty(rs.getString("wtss_gjjpayty"));
				list.setWtss_laborcontract(rs.getString("wtss_laborcontract"));
				list.setEcoc_compact_f(rs.getString("ecou_compact_f"));
				list.setEcoc_compact_l(rs.getString("ecou_compact_l"));
				list.setEcoc_sum(rs.getBigDecimal("ecou_sum"));
				list.setEcoc_idcard(rs.getString("ecou_idcard"));
				list.setEcoc_email(rs.getString("ecou_email"));
				list.setEcoc_statestr(rs.getString("emba_sex"));
				list.setEcoc_remark(rs.getString("ecou_remark"));
				list.setEcoc_sb_base(rs.getBigDecimal("ecou_sb_base"));
				list.setEcoc_house_base(rs.getBigDecimal("ecou_house_base"));
				list.setWtss_archives(rs.getString("wtss_archives"));
				list.setEcoc_domicile(rs.getString("ecou_domicile"));
				list.setEcoc_salary(rs.getBigDecimal("ecou_salary"));
				list.setEcoc_sb_co_sum(rs.getBigDecimal("ecou_sb_co_sum"));
				list.setEcoc_sb_em_sum(rs.getBigDecimal("ecou_sb_em_sum"));
				list.setEcoc_gjj_co_sum(rs.getBigDecimal("ecou_gjj_co_sum"));
				list.setEcoc_gjj_em_sum(rs.getBigDecimal("ecou_gjj_em_sum"));
				list.setEcoc_welfare_sum(rs.getBigDecimal("ecou_welfare_sum"));
				list.setSoin_id(rs.getInt("ecou_soin_id"));
				list.setEcoc_ecos_id(rs.getInt("ecou_ecos_id"));
				list.setEcoc_bjmonth(rs.getString("eofd_cp"));
				list.setCoba_shortname(rs.getString("coba_shortname"));
				list.setEcoc_title_date(rs.getString("ecou_title_date"));
				list.setCoab_id(rs.getInt("coab_id"));
			}
		} catch (Exception e) {
			System.out.print(e.toString());
		} finally {
			db.Close();
		}
		return list;
	}

	// 获取委托合同标准
	public List<EmCommissionOutStandardModel> getwtout_stand(String ecou_id)
			throws SQLException {
		dbconn db = new dbconn();
		ResultSet rs = null;
		List<EmCommissionOutStandardModel> list = new ArrayList<EmCommissionOutStandardModel>();
		String sqlstr = "select b.wtot_feeid,wtss_title+'-'+b.wtot_feetitle title,coab_id from EmCommissionOut a left join wtoutFee b on b.cid=a.cid left join WtServiceStandardrelation c on c.wtot_feeid=b.Wtot_feeid left join WtServiceStandard d on d.wtss_id=c.wtss_id where ecou_id="
				+ ecou_id + " and wtot_state=3";
		try {
			System.out.println(sqlstr);
			rs = db.GRS(sqlstr);
			while (rs.next()) {

				EmCommissionOutStandardModel model = new EmCommissionOutStandardModel();
				model.setWtot_feeid(rs.getInt("wtot_feeid"));
				model.setTypename(rs.getString("title"));
				model.setEcos_cabc_id(rs.getInt("coab_id"));
				list.add(model);
			}
		} catch (Exception e) {
			System.out.print(e.toString());
		} finally {
			db.Close();
		}
		return list;
	}

	// 获取委托合同标准
	public List<EmCommissionOutStandardModel> getwtout_title(String ecos_id)
			throws SQLException {
		dbconn db = new dbconn();
		ResultSet rs = null;
		List<EmCommissionOutStandardModel> list = new ArrayList<EmCommissionOutStandardModel>();
		String sqlstr = "select distinct soin_title,soin_id,Convert(varchar(10),sial_execdate,120) sial_execdate from View_wtservicestandard a left join SocialInsurance b on b.soin_cabc_id=a.cabc_id left join (select sial_soin_id,MAX(sial_execdate) sial_execdate from SocialInsuranceAlgorithm where sial_execdate<=GETDATE() group by sial_soin_id) c on c.sial_soin_id=b.soin_id where soin_state=1 and wtot_feeid="
				+ ecos_id + " order by soin_title";
		try {
			System.out.println(sqlstr);
			rs = db.GRS(sqlstr);
			while (rs.next()) {

				EmCommissionOutStandardModel model = new EmCommissionOutStandardModel();
				model.setWtot_feeid(rs.getInt("soin_id"));
				model.setTypename(rs.getString("soin_title"));
				list.add(model);
			}
		} catch (Exception e) {
			System.out.print(e.toString());
		} finally {
			db.Close();
		}
		return list;
	}

	// 获取委托费用明细
	public List<EmCommissionOutFeeDetailChangeModel> getfeedetail(
			String ecoc_id, int soin_id, String sb_base, int x_st,
			String house_base, String abase, String aname, String cp,
			String op, String cop, String cur,String sb_pay,String gjj_pay) throws SQLException {
		dbconn db = new dbconn();
		ResultSet rs = null;
		ResultSet rs1 = null;
		int j = 0;
		int i = 0;
		int h = 0;
		int jh = 0;
		int gjh = 0;

		List<EmCommissionOutFeeDetailChangeModel> list = new ArrayList<EmCommissionOutFeeDetailChangeModel>();

		String sqlstr = "select eofd_ecou_id,eofd_name,eofd_em_base,eofd_co_base,isnull(eofd_op,'') eofd_op,isnull(eofd_cp,'') eofd_cp,eofd_month_sum,eofd_start_date,eofd_sicl_id,isnull(eofd_co_sum,0) eofd_co_sum,isnull(eofd_em_sum,0) eofd_em_sum,cast(year(getdate()) as varchar(5))+'-'+cast(month(getdate()) as varchar(5))+'-1' td1 from EmCommissionOutFeeDetail where eofd_name in ('养老保险','医疗保险','大病医疗','生育保险','工伤保险','失业保险','住房公积金','补充公积金','服务费','档案费') and eofd_ecou_id="
				+ ecoc_id + " order by eofd_sicl_id";
		try {

			rs = db.GRS(sqlstr);
			while (rs.next()) {

				EmCommissionOutFeeDetailChangeModel model = new EmCommissionOutFeeDetailChangeModel();
				model.setEofc_name(rs.getString("eofd_name"));
				model.setEofc_em_base(rs.getBigDecimal("eofd_em_base"));
				model.setEofc_co_base(rs.getBigDecimal("eofd_co_base"));
				model.setEofc_op(rs.getString("eofd_op"));
				model.setEofc_cp(rs.getString("eofd_cp"));
				model.setEofc_month_sum(rs.getBigDecimal("eofd_month_sum"));
				model.setEofc_addtime(rs.getDate("td1"));
				model.setEofc_co_sum(rs.getBigDecimal("eofd_co_sum"));
				model.setEofc_em_sum(rs.getBigDecimal("eofd_em_sum"));
				model.setTempDate(rs.getDate("td1"));
				model.setTempDate1(rs.getDate("td1"));
				model.setSicl_class(rs.getString("eofd_name"));
				model.setEofc_sicl_id(rs.getInt("eofd_sicl_id"));

				try {
					testlist = new ListModelList<SocialInsuranceClassInfoViewModel>(
							tesbll.getSbItemFee(soin_id,
									new BigDecimal(sb_base), new Date()));
					if (x_st == 1
							&& testlist.get(jh).getSicl_name()
									.equals(rs.getString("eofd_name"))) {
						// for (int i = 0; i < testlist.size() - 1; i++) {
						model.setEofc_name(rs.getString("eofd_name"));
						model.setEofc_em_base(testlist.get(i + 1).getRadix());
						model.setEofc_co_base(testlist.get(i).getRadix());
						model.setEofc_op(testlist.get(i + 1)
								.getSiai_proportion());
						model.setEofc_cp(testlist.get(i).getSiai_proportion());
						model.setEofc_month_sum(testlist.get(i).getFee()
								.add(testlist.get(i + 1).getFee()));
						model.setEofc_addtime(rs.getDate("td1"));
						model.setEofc_co_sum(testlist.get(i).getFee());
						model.setEofc_em_sum(testlist.get(i + 1).getFee());
						model.setTempDate(rs.getDate("td1"));
						model.setTempDate1(rs.getDate("td1"));
						model.setSicl_class(rs.getString("eofd_name"));
						if (sb_base.equals("0.00")||sb_base.equals("0")||sb_base.equals("0.0")) {
							model.setEofc_em_base(new BigDecimal(0));
							model.setEofc_em_sum(new BigDecimal(0));
							model.setEofc_co_base(new BigDecimal(0));
							model.setEofc_co_sum(new BigDecimal(0));
							model.setEofc_month_sum(new BigDecimal(0));
						}
						if(!sb_pay.equals("委托对账")){
							model.setEofc_month_sum(new BigDecimal(0));
						}
						i = i + 2;
						// }
						System.out.println("xxx--xxx");
						System.out.println(sb_base);
						System.out.println(house_base);
					}
				} catch (Exception e) {
					// TODO: handle exception
				}

				try {
					testgjjlist = new ListModelList<SocialInsuranceClassInfoViewModel>(
							tesbll.getGjjItemFee(soin_id, new BigDecimal(
									house_base), new BigDecimal(cop),
									new Date()));

					if (cur.equals("1")) {
						if (x_st == 1
								&& testgjjlist.get(gjh).getSicl_name()
										.equals(rs.getString("eofd_name"))
								&& rs.getString("eofd_name").equals("住房公积金")) {
							model.setEofc_name(rs.getString("eofd_name"));
							model.setEofc_em_base(testgjjlist.get(h + 1)
									.getRadix());
							model.setEofc_co_base(testgjjlist.get(h).getRadix());
							model.setEofc_op(op);
							model.setEofc_cp(cp);
							model.setEofc_month_sum(testgjjlist.get(h).getFee()
									.add(testgjjlist.get(h + 1).getFee()));
							model.setEofc_addtime(rs.getDate("td1"));
							model.setEofc_co_sum(testgjjlist.get(h).getFee());
							model.setEofc_em_sum(testgjjlist.get(h + 1)
									.getFee());
							model.setTempDate(rs.getDate("td1"));
							model.setTempDate1(rs.getDate("td1"));
							model.setSicl_class(rs.getString("eofd_name"));

							if (house_base.equals("0.00")||house_base.equals("0")||house_base.equals("0.0")) {
								model.setEofc_em_base(new BigDecimal(0));
								model.setEofc_em_sum(new BigDecimal(0));
								model.setEofc_co_base(new BigDecimal(0));
								model.setEofc_co_sum(new BigDecimal(0));
								model.setEofc_month_sum(new BigDecimal(0));
							}
							
							if(!gjj_pay.equals("委托对账")){
								model.setEofc_month_sum(new BigDecimal(0));
							}

							h = h + 2;

							gjh = gjh + 2;
						}
					} else {
						if (x_st == 1
								&& testgjjlist.get(gjh).getSicl_name()
										.equals(rs.getString("eofd_name"))) {
							model.setEofc_name(rs.getString("eofd_name"));
							model.setEofc_em_base(testgjjlist.get(h + 1)
									.getRadix());
							model.setEofc_co_base(testgjjlist.get(h).getRadix());
							model.setEofc_op(op);
							model.setEofc_cp(cp);
							model.setEofc_month_sum(testgjjlist.get(h).getFee()
									.add(testgjjlist.get(h + 1).getFee()));
							model.setEofc_addtime(rs.getDate("td1"));
							model.setEofc_co_sum(testgjjlist.get(h).getFee());
							model.setEofc_em_sum(testgjjlist.get(h + 1)
									.getFee());
							model.setTempDate(rs.getDate("td1"));
							model.setTempDate1(rs.getDate("td1"));
							model.setSicl_class(rs.getString("eofd_name"));

							if (house_base.equals("0.00")||house_base.equals("0")||house_base.equals("0.0")) {
								model.setEofc_em_base(new BigDecimal(0));
								model.setEofc_em_sum(new BigDecimal(0));
								model.setEofc_co_base(new BigDecimal(0));
								model.setEofc_co_sum(new BigDecimal(0));
								model.setEofc_month_sum(new BigDecimal(0));
							}
							if(!gjj_pay.equals("委托对账")){
								model.setEofc_month_sum(new BigDecimal(0));
							}
							
							h = h + 2;

							gjh = gjh + 2;
						}
					}

				} catch (Exception e) {
					// TODO: handle exception
				}
				jh = jh + 2;
				list.add(model);
				j = j + 2;

			}

			String sqlstr1 = "select coli_name eofc_name,coli_content eofc_content,coli_fee,(select MAX(eofd_sicl_id) from EmCommissionOutFeeDetail where eofd_ecou_id=ecou_id)+1 eofc_sicl_id,'福利项目' sicl_class,0 eofc_ecop_id,cast(year(getdate()) as varchar(5))+'-'+cast(month(getdate()) as varchar(5))+'-1' td1 from EmCommissionOut a left join wtoutFee b on b.Wtot_feeid=a.ecou_ecos_id left join CoAgencyBaseCityRel c on c.cabc_id=b.coab_id left join CoProduct e on e.copr_cabc_id=c.cabc_id left join CoOfferList f on f.coli_copr_id=e.Copr_id left join coglist g on g.cgli_coli_id=f.coli_id and g.gid=a.gid where copr_type='福利产品' and coli_pclass<>'档案' and a.gid=g.gid and ecou_id="
					+ ecoc_id;
			try {
				System.out.println(sqlstr1);

				rs1 = db.GRS(sqlstr1);
				while (rs1.next()) {

					EmCommissionOutFeeDetailChangeModel model = new EmCommissionOutFeeDetailChangeModel();
					model.setEofc_name(rs1.getString("eofc_name"));
					model.setEofc_em_base(new BigDecimal(0));
					model.setEofc_co_base(new BigDecimal(0));
					model.setEofc_op("0");
					model.setEofc_cp("0");
					model.setEofc_month_sum(rs1.getBigDecimal("coli_fee"));
					model.setEofc_addtime(rs1.getDate("td1"));
					model.setEofc_co_sum(new BigDecimal(0));
					model.setEofc_em_sum(new BigDecimal(0));
					model.setTempDate(rs1.getDate("td1"));
					model.setTempDate1(rs1.getDate("td1"));
					model.setSicl_class(rs1.getString("eofc_name"));
					model.setEofc_sicl_id(rs1.getInt("eofc_sicl_id"));
					list.add(model);
				}
			} catch (Exception e) {
				System.out.print(e.toString());
			}

		} catch (Exception e) {
			System.out.print(e.toString());
		} finally {
			db.Close();
		}

		return list;
	}
	

	// 获取委托费用明细
	public List<EmCommissionOutFeeDetailChangeModel> getfeedetailedit(
			String ecoc_id, int soin_id, String sb_base, int x_st,
			String house_base, String abase, String aname, String cp,
			String op, String cop, String cur,String sb_pay,String gjj_pay) throws SQLException {
		dbconn db = new dbconn();
		ResultSet rs = null;
		ResultSet rs1 = null;
		int j = 0;
		int i = 0;
		int h = 0;
		int jh = 0;
		int gjh = 0;

		List<EmCommissionOutFeeDetailChangeModel> list = new ArrayList<EmCommissionOutFeeDetailChangeModel>();

		String sqlstr = "select eofd_ecou_id,eofd_name,eofd_em_base,eofd_co_base,eofd_op,eofd_cp,eofd_month_sum,eofd_start_date,eofd_sicl_id,eofd_co_sum,eofd_em_sum,cast(year(eofd_start_date) as varchar(5))+'-'+cast(month(eofd_start_date) as varchar(5))+'-1' td1 from EmCommissionOutFeeDetail where eofd_name in ('养老保险','医疗保险','大病医疗','生育保险','工伤保险','失业保险','住房公积金','补充公积金','服务费','档案费') and eofd_ecou_id="
				+ ecoc_id + " order by eofd_sicl_id";
		try {

			rs = db.GRS(sqlstr);
			while (rs.next()) {
				
				System.out.println("ccccc");
				System.out.println(sb_base);

				EmCommissionOutFeeDetailChangeModel model = new EmCommissionOutFeeDetailChangeModel();
				model.setEofc_name(rs.getString("eofd_name"));
				model.setEofc_em_base(rs.getBigDecimal("eofd_em_base"));
				model.setEofc_co_base(rs.getBigDecimal("eofd_co_base"));
				model.setEofc_op(rs.getString("eofd_op"));
				model.setEofc_cp(rs.getString("eofd_cp"));
				model.setEofc_month_sum(rs.getBigDecimal("eofd_month_sum"));
				model.setEofc_addtime(rs.getDate("td1"));
				model.setEofc_co_sum(rs.getBigDecimal("eofd_co_sum"));
				model.setEofc_em_sum(rs.getBigDecimal("eofd_em_sum"));
				model.setTempDate(rs.getDate("td1"));
				model.setTempDate1(rs.getDate("td1"));
				model.setSicl_class(rs.getString("eofd_name"));
				model.setEofc_sicl_id(rs.getInt("eofd_sicl_id"));

				try {
					testlist = new ListModelList<SocialInsuranceClassInfoViewModel>(
							tesbll.getSbItemFee(soin_id,
									new BigDecimal(sb_base), new Date()));
					if (x_st == 1
							&& testlist.get(jh).getSicl_name()
									.equals(rs.getString("eofd_name"))) {
						// for (int i = 0; i < testlist.size() - 1; i++) {
						model.setEofc_name(rs.getString("eofd_name"));
						model.setEofc_em_base(testlist.get(i + 1).getRadix());
						model.setEofc_co_base(testlist.get(i).getRadix());
						model.setEofc_op(testlist.get(i + 1)
								.getSiai_proportion());
						model.setEofc_cp(testlist.get(i).getSiai_proportion());
						model.setEofc_month_sum(testlist.get(i).getFee()
								.add(testlist.get(i + 1).getFee()));
						model.setEofc_addtime(rs.getDate("td1"));
						model.setEofc_co_sum(testlist.get(i).getFee());
						model.setEofc_em_sum(testlist.get(i + 1).getFee());
						model.setTempDate(rs.getDate("td1"));
						model.setTempDate1(rs.getDate("td1"));
						model.setSicl_class(rs.getString("eofd_name"));
						if (sb_base.equals("0.00")) {
							model.setEofc_em_base(new BigDecimal(0));
							model.setEofc_em_sum(new BigDecimal(0));
							model.setEofc_co_base(new BigDecimal(0));
							model.setEofc_co_sum(new BigDecimal(0));
							model.setEofc_month_sum(new BigDecimal(0));
						}
						if(!sb_pay.equals("委托对账")){
							model.setEofc_month_sum(new BigDecimal(0));
						}
						i = i + 2;
						// }
					}
				} catch (Exception e) {
					// TODO: handle exception
				}

				try {
					testgjjlist = new ListModelList<SocialInsuranceClassInfoViewModel>(
							tesbll.getGjjItemFee(soin_id, new BigDecimal(
									house_base), new BigDecimal(cop),
									new Date()));

					if (cur.equals("1")) {
						if (x_st == 1
								&& testgjjlist.get(gjh).getSicl_name()
										.equals(rs.getString("eofd_name"))
								&& rs.getString("eofd_name").equals("住房公积金")) {
							model.setEofc_name(rs.getString("eofd_name"));
							model.setEofc_em_base(testgjjlist.get(h + 1)
									.getRadix());
							model.setEofc_co_base(testgjjlist.get(h).getRadix());
							model.setEofc_op(op);
							model.setEofc_cp(cp);
							model.setEofc_month_sum(testgjjlist.get(h).getFee()
									.add(testgjjlist.get(h + 1).getFee()));
							model.setEofc_addtime(rs.getDate("td1"));
							model.setEofc_co_sum(testgjjlist.get(h).getFee());
							model.setEofc_em_sum(testgjjlist.get(h + 1)
									.getFee());
							model.setTempDate(rs.getDate("td1"));
							model.setTempDate1(rs.getDate("td1"));
							model.setSicl_class(rs.getString("eofd_name"));

							if (house_base.equals("0.00")) {
								model.setEofc_em_base(new BigDecimal(0));
								model.setEofc_em_sum(new BigDecimal(0));
								model.setEofc_co_base(new BigDecimal(0));
								model.setEofc_co_sum(new BigDecimal(0));
								model.setEofc_month_sum(new BigDecimal(0));
							}
							
							if(!gjj_pay.equals("委托对账")){
								model.setEofc_month_sum(new BigDecimal(0));
							}

							h = h + 2;

							gjh = gjh + 2;
						}
					} else {
						if (x_st == 1
								&& testgjjlist.get(gjh).getSicl_name()
										.equals(rs.getString("eofd_name"))) {
							model.setEofc_name(rs.getString("eofd_name"));
							model.setEofc_em_base(testgjjlist.get(h + 1)
									.getRadix());
							model.setEofc_co_base(testgjjlist.get(h).getRadix());
							model.setEofc_op(op);
							model.setEofc_cp(cp);
							model.setEofc_month_sum(testgjjlist.get(h).getFee()
									.add(testgjjlist.get(h + 1).getFee()));
							model.setEofc_addtime(rs.getDate("td1"));
							model.setEofc_co_sum(testgjjlist.get(h).getFee());
							model.setEofc_em_sum(testgjjlist.get(h + 1)
									.getFee());
							model.setTempDate(rs.getDate("td1"));
							model.setTempDate1(rs.getDate("td1"));
							model.setSicl_class(rs.getString("eofd_name"));

							if (house_base.equals("0.00")) {
								model.setEofc_em_base(new BigDecimal(0));
								model.setEofc_em_sum(new BigDecimal(0));
								model.setEofc_co_base(new BigDecimal(0));
								model.setEofc_co_sum(new BigDecimal(0));
								model.setEofc_month_sum(new BigDecimal(0));
							}
							if(!gjj_pay.equals("委托对账")){
								model.setEofc_month_sum(new BigDecimal(0));
							}
							
							h = h + 2;

							gjh = gjh + 2;
						}
					}

				} catch (Exception e) {
					// TODO: handle exception
				}
				jh = jh + 2;
				list.add(model);
				j = j + 2;

			}

			String sqlstr1 = "select coli_name eofc_name,coli_content eofc_content,coli_fee,(select MAX(eofd_sicl_id) from EmCommissionOutFeeDetail where eofd_ecou_id=ecou_id)+1 eofc_sicl_id,'福利项目' sicl_class,0 eofc_ecop_id,cast(year(getdate()) as varchar(5))+'-'+cast(month(getdate()) as varchar(5))+'-1' td1 from EmCommissionOut a left join wtoutFee b on b.Wtot_feeid=a.ecou_ecos_id left join CoAgencyBaseCityRel c on c.cabc_id=b.coab_id left join CoProduct e on e.copr_cabc_id=c.cabc_id left join CoOfferList f on f.coli_copr_id=e.Copr_id left join coglist g on g.cgli_coli_id=f.coli_id and g.gid=a.gid where copr_type='福利产品' and coli_pclass<>'档案' and a.gid=g.gid and ecou_id="
					+ ecoc_id;
			try {
				System.out.println(sqlstr1);

				rs1 = db.GRS(sqlstr1);
				while (rs1.next()) {

					EmCommissionOutFeeDetailChangeModel model = new EmCommissionOutFeeDetailChangeModel();
					model.setEofc_name(rs1.getString("eofc_name"));
					model.setEofc_em_base(new BigDecimal(0));
					model.setEofc_co_base(new BigDecimal(0));
					model.setEofc_op("0");
					model.setEofc_cp("0");
					model.setEofc_month_sum(rs1.getBigDecimal("coli_fee"));
					model.setEofc_addtime(rs1.getDate("td1"));
					model.setEofc_co_sum(new BigDecimal(0));
					model.setEofc_em_sum(new BigDecimal(0));
					model.setTempDate(rs1.getDate("td1"));
					model.setTempDate1(rs1.getDate("td1"));
					model.setSicl_class(rs1.getString("eofc_name"));
					model.setEofc_sicl_id(rs1.getInt("eofc_sicl_id"));
					list.add(model);
				}
			} catch (Exception e) {
				System.out.print(e.toString());
			}

		} catch (Exception e) {
			System.out.print(e.toString());
		} finally {
			db.Close();
		}

		return list;
	}

	// 获取委托费用明细
	public List<EmCommissionOutFeeDetailChangeModel> getfeedetailc(
			String ecoc_id, int soin_id, String sb_base, int x_st,
			String house_base, String abase, String aname) throws SQLException {
		dbconn db = new dbconn();
		ResultSet rs = null;
		int j = 0;
		// int i = 0;
		int h = 0;
		int jh = 0;
		int gjh = 0;

		List<EmCommissionOutFeeDetailChangeModel> list = new ArrayList<EmCommissionOutFeeDetailChangeModel>();
		EmCommissionOutFeeDetailChangeModel model = new EmCommissionOutFeeDetailChangeModel();

		testlist = new ListModelList<SocialInsuranceClassInfoViewModel>(
				tesbll.getSbItemFee(soin_id, new BigDecimal(abase), new Date()));

		for (int i = 0; i < list.size(); i++) {
			if (list.get(i).getEofc_name().equals(aname)) {
				model.setEofc_name(rs.getString("eofd_name"));
				model.setEofc_em_base(testgjjlist.get(i + 1).getRadix());
				model.setEofc_co_base(testgjjlist.get(i).getRadix());
				model.setEofc_op(testgjjlist.get(i + 1).getSiai_proportion());
				model.setEofc_cp(testgjjlist.get(i).getSiai_proportion());
				model.setEofc_month_sum(testgjjlist.get(i).getFee()
						.add(testgjjlist.get(i + 1).getFee()));
				model.setEofc_addtime(rs.getDate("eofd_start_date"));
				model.setEofc_co_sum(testgjjlist.get(i).getFee());
				model.setEofc_em_sum(testgjjlist.get(i + 1).getFee());
				model.setTempDate(rs.getDate("td1"));
				model.setTempDate1(rs.getDate("td1"));
				model.setSicl_class(rs.getString("eofd_name"));
				list.add(model);
			}
		}
		return list;
	}

	// 获取委托住房企业比例
	public List<EmCommissionOutFeeDetailChangeModel> gethousecp(String soin_id)
			throws SQLException {
		dbconn db = new dbconn();
		ResultSet rs = null;
		List<EmCommissionOutFeeDetailChangeModel> list = new ArrayList<EmCommissionOutFeeDetailChangeModel>();
		String sqlstr = "select top 1 siai_proportion from SocialInsurance a left join SocialInsuranceAlgorithm b on b.sial_soin_id=a.soin_id left join SocialInseranceAlgorithmInfo c on c.siai_sial_id=b.sial_id where siai_sicl_id=13 and sial_execdate<=GETDATE() and soin_id="
				+ soin_id + " order by sial_execdate desc";
		try {
			System.out.println(sqlstr);
			rs = db.GRS(sqlstr);
			while (rs.next()) {
				String[] house_a = rs.getString("siai_proportion").split(",");
				if (house_a.length > 1) {
					for (int i = 0; i < house_a.length; i++) {
						EmCommissionOutFeeDetailChangeModel model = new EmCommissionOutFeeDetailChangeModel();
						model.setEofc_content(house_a[i]);
						list.add(model);
					}
				}

				String[] house_b = rs.getString("siai_proportion").split("-");
				if (house_b.length > 1) {
					float c1 = Float.parseFloat(house_b[0]) * 100;
					float c2 = Float.parseFloat(house_b[1]) * 100;
					for (float ii = c1; ii < c2+1; ii++) {
						EmCommissionOutFeeDetailChangeModel model = new EmCommissionOutFeeDetailChangeModel();
						model.setEofc_content(String.valueOf(ii / 100));
						list.add(model);
					}
				}

				if (house_a.length == 1 && house_b.length == 1) {
						EmCommissionOutFeeDetailChangeModel model = new EmCommissionOutFeeDetailChangeModel();
						model.setEofc_content(rs.getString("siai_proportion"));
						list.add(model);
				}
			}
		} catch (Exception e) {
			System.out.print(e.toString());
		} finally {
			db.Close();
		}
		return list;
	}

	// 获取委托住房企业比例
	public List<EmCommissionOutFeeDetailChangeModel> getbchousecp(String soin_id)
			throws SQLException {
		dbconn db = new dbconn();
		ResultSet rs = null;
		List<EmCommissionOutFeeDetailChangeModel> list = new ArrayList<EmCommissionOutFeeDetailChangeModel>();
		String sqlstr = "select top 1 siai_proportion from SocialInsurance a left join SocialInsuranceAlgorithm b on b.sial_soin_id=a.soin_id left join SocialInseranceAlgorithmInfo c on c.siai_sial_id=b.sial_id where siai_sicl_id=15 and sial_execdate<=GETDATE() and soin_id="
				+ soin_id + " order by sial_execdate desc";
		try {
			System.out.println(sqlstr);
			rs = db.GRS(sqlstr);
			while (rs.next()) {
				String[] house_a = rs.getString("siai_proportion").split(",");
				if (house_a.length > 1) {
					for (int i = 0; i < house_a.length; i++) {
						EmCommissionOutFeeDetailChangeModel model = new EmCommissionOutFeeDetailChangeModel();
						model.setEofc_content(house_a[i]);
						list.add(model);
					}
				}

				String[] house_b = rs.getString("siai_proportion").split("-");
				if (house_b.length > 1) {
					float c1 = Float.parseFloat(house_b[0]) * 100;
					float c2 = Float.parseFloat(house_b[1]) * 100;
					for (float ii = c1; ii < c2+1; ii++) {
						EmCommissionOutFeeDetailChangeModel model = new EmCommissionOutFeeDetailChangeModel();
						model.setEofc_content(String.valueOf(ii / 100));
						list.add(model);
					}
				}

				if (house_a.length == 1 && house_b.length == 1) {
						EmCommissionOutFeeDetailChangeModel model = new EmCommissionOutFeeDetailChangeModel();
						model.setEofc_content(rs.getString("siai_proportion"));
						list.add(model);
				}
			}
		} catch (Exception e) {
			System.out.print(e.toString());
		} finally {
			db.Close();
		}
		return list;
	}

	// 获取该公司档案费
	public List<EmCommissionOutStandardModel> getfilelist(Integer gid,
			Integer cabc_id) throws SQLException {
		dbconn db = new dbconn();
		List<EmCommissionOutStandardModel> list = new ArrayList<>();
		String sql = "select cpfr_fee ecos_archvie_fee from CoProduct a left join CoPFeeRelation b on b.cpfr_copr_id=a.Copr_id where cpfr_cpfc_id=1 and copr_copc_id=2 and a.copr_name LIKE '%档案%'  and b.cpfr_state=1 and copr_cabc_id="
				+ cabc_id;
		System.out.println(sql);
		try {
			list = db.find(sql, EmCommissionOutStandardModel.class,
					dbconn.parseSmap(EmCommissionOutStandardModel.class));
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}

	private dbconn conn = new dbconn();
	// 委托外地调整
		public int change_feeadd(Integer ecou_id,
				EmCommissionOutFeeDetailChangeModel wtfeelist,String start_date) {
			
			try {
				CallableStatement c = conn
						.getcall("EmCommissionOut_NewChangeFeeAdd_P_zzq(?,?,?,?,?,?,?,?,?,?,?,?)");				
				c.setInt(1, ecou_id);
				c.setInt(2, wtfeelist.getEofc_sicl_id());
				c.setBigDecimal(3, wtfeelist.getEofc_em_base());
				c.setBigDecimal(4, wtfeelist.getEofc_co_base());
				c.setString(5, wtfeelist.getEofc_cp());
				c.setString(6, wtfeelist.getEofc_op());
				c.setBigDecimal(7, wtfeelist.getEofc_co_sum());
				c.setBigDecimal(8, wtfeelist.getEofc_em_sum());
				c.setBigDecimal(9, wtfeelist.getEofc_month_sum());
				c.setString(10,wtfeelist.getEofc_name());
				c.setString(11,start_date);
				c.registerOutParameter(12, java.sql.Types.INTEGER);
				c.execute();
				return c.getInt(12);

			} catch (SQLException e) {
				return 1;
			}
		}
}
