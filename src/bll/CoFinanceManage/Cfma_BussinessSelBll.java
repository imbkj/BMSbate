package bll.CoFinanceManage;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import dal.CoFinanceManage.Cfma_BussinessSelDal;

import Model.CoFinanceDisposableModel;
import Model.CoFinanceProductModel;
import Model.EmFinanceCommissionOutCityModel;
import Model.EmFinanceCommissionOutDetailItemModel;
import Model.EmFinanceCommissionOutModel;
import Model.EmFinanceDisposableModel;
import Model.EmFinanceHouseGjjModel;
import Model.EmFinanceProductItemListModel;
import Model.EmFinanceProductModel;
import Model.EmFinanceSalaryModel;
import Model.EmFinanceSheBaoModel;
import Model.EmFinanceTaxModel;
import Model.EmFinanceValueAddTaxModel;
import Model.SocialInsuranceClassInfoViewModel;

public class Cfma_BussinessSelBll {
	private Cfma_BussinessSelDal dal = new Cfma_BussinessSelDal();

	// 按账单号获取社保费用
	public List<EmFinanceSheBaoModel> getShebaoList(int cid, int ownmonth,
			String cfmb_number) {
		return dal.getShebaoList(cid, ownmonth, cfmb_number);
	}

	// 按账单号获取公积金费用
	public List<EmFinanceHouseGjjModel> getGjjList(int cid, int ownmonth,
			String cfmb_number) {
		return dal.getGjjList(cid, ownmonth, cfmb_number);
	}

	// 按账单号获取个税费用
	public List<EmFinanceTaxModel> getTaxList(int cid, int ownmonth,
			String cfmb_number) {
		return dal.getTaxList(cid, ownmonth, cfmb_number);
	}

	// 按账单号获取工资费用
	public List<EmFinanceSalaryModel> getSalaryList(int cid, int ownmonth,
			String cfmb_number) {
		return dal.getSalaryList(cid, ownmonth, cfmb_number);
	}

	// 获取公司福利费用
	public List<CoFinanceProductModel> getCoProductList(int cid, int ownmonth,
			String cfmb_number) {
		return dal.getCoProductList(cid, ownmonth, cfmb_number);
	}

	// 按账单号获取员工福利费用
	public List<EmFinanceProductModel> getEmProductList(int cid, int ownmonth,
			String cfmb_number) {
		return dal.getEmProductList(cid, ownmonth, cfmb_number);
	}

	// 获取员工福利项目
	public List<EmFinanceProductItemListModel> getEmProductItemList(int cid,
			int ownmonth, String cfmb_number) {
		return dal.getEmProductItemList(cid, ownmonth, cfmb_number);
	}

	// 获取委托出费用
	public List<EmFinanceCommissionOutCityModel> getEmFinanceCommissionOutList(
			int cid, int ownmonth, String cfmb_number) {
		List<EmFinanceCommissionOutCityModel> cityList = new ArrayList<EmFinanceCommissionOutCityModel>();
		List<EmFinanceCommissionOutModel> efcoList = null;
		EmFinanceCommissionOutCityModel cityModel = null;
		EmFinanceCommissionOutModel efcoModel = null;
		String city = "";
		int efco_id = 0;
		try {
			List<EmFinanceCommissionOutDetailItemModel> detailItemList = null;
			List<EmFinanceCommissionOutDetailItemModel> productItemList = null;
			List<SocialInsuranceClassInfoViewModel> soinInfoList = dal
					.getEmCommissionOutSocialInsurance(cfmb_number, cid,
							ownmonth);
			ResultSet rs = dal.getEmFinanceCommissionOutRs(cid, ownmonth,
					cfmb_number);
			while (rs.next()) {
				if (!city.equals(rs.getString("city"))) {
					city = rs.getString("city");
					efco_id = rs.getInt("efco_id");
					detailItemList = dal.getEmCommissionOutDetailItem(cid,
							ownmonth, cfmb_number, city);
					productItemList = dal.getEmCommissionOutProductItem(cid,
							ownmonth, cfmb_number, city);
					cityModel = new EmFinanceCommissionOutCityModel();
					cityModel.setSoinList(getSoinList(city, soinInfoList,
							detailItemList));
					cityModel.setCity(rs.getString("city"));
					cityModel.setDetailItemList(detailItemList);
					cityModel.setProductItemList(productItemList);
					efcoList = new ArrayList<EmFinanceCommissionOutModel>();
					efcoModel = new EmFinanceCommissionOutModel();
					efcoModel.setGid(rs.getInt("gid"));
					efcoModel.setEfco_ecou_addtype(rs.getString("efco_ecou_addtype"));
					efcoModel.setEmba_idcard(rs.getString("emba_idcard"));
					efcoModel.setEmba_name(rs.getString("efba_emba_name"));
					efcoModel.setSoin_title(rs.getString("soin_title"));
					efcoModel.setEfco_ecou_welfare_sum(rs.getBigDecimal("efco_ecou_welfare_sum"));
					efcoModel.setEfco_receivable(rs
							.getBigDecimal("efco_Receivable"));
					efcoModel.insertDetailItemList(detailItemList);
					efcoModel.insertProductItemList(productItemList);
					if (rs.getInt("type") == 1) {
						efcoModel.insertReceivableToDetailItemList(
								rs.getString("efcd_eofd_name"),
								rs.getBigDecimal("efcd_eofd_em_base"),
								rs.getBigDecimal("efcd_eofd_co_base"),
								rs.getBigDecimal("efcd_eofd_em_sum"),
								rs.getBigDecimal("efcd_eofd_co_sum"));
					} else {
						efcoModel.insertReceivableToProductItemList(
								rs.getString("efcd_eofd_name"),
								rs.getBigDecimal("efcd_eofd_em_sum"));
					}
					efcoList.add(efcoModel);
					cityModel.setEmFinanceCommissionOutList(efcoList);
					cityList.add(cityModel);

				} else {
					if (rs.getInt("efco_id") != efco_id) {
						efco_id = rs.getInt("efco_id");
						efcoModel = new EmFinanceCommissionOutModel();
						efcoModel.setGid(rs.getInt("gid"));
						efcoModel.setEfco_ecou_addtype(rs.getString("efco_ecou_addtype"));
						efcoModel.setEmba_idcard(rs.getString("emba_idcard"));
						efcoModel.setEmba_name(rs.getString("efba_emba_name"));
						efcoModel.setSoin_title(rs.getString("soin_title"));
						efcoModel.setEfco_ecou_welfare_sum(rs.getBigDecimal("efco_ecou_welfare_sum"));
						efcoModel.setEfco_receivable(rs
								.getBigDecimal("efco_Receivable"));
						efcoModel.insertDetailItemList(detailItemList);
						efcoModel.insertProductItemList(productItemList);
						if (rs.getInt("type") == 1) {
							efcoModel.insertReceivableToDetailItemList(
									rs.getString("efcd_eofd_name"),
									rs.getBigDecimal("efcd_eofd_em_base"),
									rs.getBigDecimal("efcd_eofd_co_base"),
									rs.getBigDecimal("efcd_eofd_em_sum"),
									rs.getBigDecimal("efcd_eofd_co_sum"));
						} else {
							
							efcoModel.insertReceivableToProductItemList(
									rs.getString("efcd_eofd_name"),
									rs.getBigDecimal("efcd_eofd_em_sum"));
									
						}
						efcoList.add(efcoModel);
					} else {
						if (rs.getInt("type") == 1) {
							efcoModel.insertReceivableToDetailItemList(
									rs.getString("efcd_eofd_name"),
									rs.getBigDecimal("efcd_eofd_em_base"),
									rs.getBigDecimal("efcd_eofd_co_base"),
									rs.getBigDecimal("efcd_eofd_em_sum"),
									rs.getBigDecimal("efcd_eofd_co_sum"));
						} else {
							
							efcoModel.insertReceivableToProductItemList(
									rs.getString("efcd_eofd_name"),
									rs.getBigDecimal("efcd_eofd_em_sum"));
									
						}
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return cityList;
	}

	// 将社保算法并转换为EmFinanceCommissionOutModel
	private List<EmFinanceCommissionOutModel> getSoinList(String city,
			List<SocialInsuranceClassInfoViewModel> soinInfoList,
			List<EmFinanceCommissionOutDetailItemModel> detailItemList) {
		List<EmFinanceCommissionOutModel> soinList = new ArrayList<EmFinanceCommissionOutModel>();
		EmFinanceCommissionOutModel efcoModel = null;
		int soin_id = 0;
		try {
			for (SocialInsuranceClassInfoViewModel m : soinInfoList) {
				if (city.equals(m.getCity())) {
					if (m.getSoin_id() != soin_id) {
						soin_id = m.getSoin_id();
						efcoModel = new EmFinanceCommissionOutModel();
						efcoModel.setSoin_title(m.getSoin_title());
						efcoModel.insertDetailItemList(detailItemList);
						efcoModel.insertProportionToDetailItemList(
								m.getSicl_name(), m.getSicl_payunit(),
								m.getSiai_proportion());
						soinList.add(efcoModel);
					} else {
						efcoModel.insertProportionToDetailItemList(
								m.getSicl_name(), m.getSicl_payunit(),
								m.getSiai_proportion());
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return soinList;
	}

	// 按账单号获取员工非标
	public List<EmFinanceDisposableModel> getEmDisposableList(int cid,
			int ownmonth, String cfmb_number) {
		return dal.getEmDisposableList(cid, ownmonth, cfmb_number);
	}

	// 获取公司非标
	public List<CoFinanceDisposableModel> getCoDisposableList(int cid,
			int ownmonth, String cfmb_number) {
		return dal.getCoDisposableList(cid, ownmonth, cfmb_number);
	}
	
	//获取增值税金
	public List<EmFinanceValueAddTaxModel> getvatList(int cid,
			int ownmonth, String cfmb_number){
		return dal.getvatList(cid, ownmonth, cfmb_number);
	}
}
