package Util;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import Model.EmCommissionOutFeeDetailChangeModel;
import Model.SocialInsuranceClassInfoViewModel;

public class SocialInsuranceEmCommissionOut extends SocialInsuranceCalculator {
	private final BigDecimal zero = BigDecimal.ZERO;
	private Map<String, SocialInsuranceClassInfoViewModel> infoMap;
	private Map<Integer, String[]> classMap;
	private String SbFeeType;
	private String GjjFeeType;

	public SocialInsuranceEmCommissionOut() {
		classMap = getSocialInsuranceClass();
	}

	/**
	 * @Methodname:设定社保算法
	 */
	public boolean setCalculator(int soin_id, Date sial_execdate,
			String SbType, String GjjType) {
		boolean bool = false;
		try {
			infoMap = getSiAlInfo(soin_id, sial_execdate);
			SbFeeType = SbType;
			GjjFeeType = GjjType;
			if (infoMap.size() > 0) {
				bool = true;
			}
		} catch (Exception e) {

		}
		return bool;
	}

	/**
	 * @Methodname:委托出社保公积金各项目费用计算
	 * 
	 * @out: EmCommissionOutModel；
	 */
	public List<EmCommissionOutFeeDetailChangeModel> getEmCommissionOutItemFee(
			List<EmCommissionOutFeeDetailChangeModel> list) {
		List<EmCommissionOutFeeDetailChangeModel> newList = new ArrayList<EmCommissionOutFeeDetailChangeModel>();
		try {
			for (EmCommissionOutFeeDetailChangeModel m : list) {
				newList.add(sumItemFee(m));
			}
		} catch (Exception e) {
			newList = list;
		}
		return newList;
	}

	// 计算委托出项目缴交额
	private EmCommissionOutFeeDetailChangeModel sumItemFee(
			EmCommissionOutFeeDetailChangeModel m) {
		int Eofc_sicl_id = m.getEofc_sicl_id();
		SocialInsuranceClassInfoViewModel itemModel = null;

		try {
			// 获取项目类型及名称
			String[] item = classMap.get(Eofc_sicl_id);
			if (item != null) {
				if ("社会保险".equals(item[0])) {
					if (m.getEofc_co_base() != null) {
						if (m.getEofc_co_base().compareTo(zero) != -1) {
							itemModel = getCalculation(item[1], "企业",
									m.getEofc_co_base());
							// 赋社保企业比例
							m.setEofc_cp(itemModel.getSiai_proportion());
							// 如果基数等于零，不计算
							if (m.getEofc_co_base().compareTo(zero) == 0) {
								m.setEofc_em_base(zero);
								m.setEofc_em_sum(zero);
							} else {
								itemModel = sumSbItemFee(itemModel);
								m.setEofc_co_base(itemModel.getRadix());
								m.setEofc_co_sum(itemModel.getFee());
							}
						}
					}
					if (m.getEofc_em_base() != null) {
						if (m.getEofc_em_base().compareTo(zero) != -1) {
							itemModel = getCalculation(item[1], "个人",
									m.getEofc_em_base());
							// 赋社保个人比例
							m.setEofc_op(itemModel.getSiai_proportion());
							// 如果基数等于零，不计算
							if (m.getEofc_co_base().compareTo(zero) == 0) {
								m.setEofc_em_base(zero);
								m.setEofc_em_sum(zero);
							} else {
								itemModel = sumSbItemFee(itemModel);
								m.setEofc_em_base(itemModel.getRadix());
								m.setEofc_em_sum(itemModel.getFee());
							}
						}
					}
					if ("委托对账".equals(SbFeeType)) {
						sumFee(m);
					}
				} else if ("公积金".equals(item[0])) {
					if (m.getEofc_co_base() != null) {
						if (m.getEofc_co_base().compareTo(zero) != -1) {
							itemModel = getCalculation(item[1], "企业",
									m.getEofc_co_base());
							if (m.getEofc_cp() == null
									|| m.getEofc_cp().isEmpty()) {
								try {
									// 赋最低比例
									m.initcpList(itemModel.getSiai_proportion());
								} catch (Exception e) {

								}
							}
							// 如果基数等于零，不计算
							if (m.getEofc_co_base().compareTo(zero) == 0) {
								m.setEofc_co_base(zero);
								m.setEofc_co_sum(zero);
							} else {
								
								//如果,
								itemModel.setSiai_proportion(itemModel.getSiai_proportion().replace("-", ","));
								
								String[] po=itemModel.getSiai_proportion().split(",");
								
								if (po.length==1)
								
								{
									m.setEofc_cp(itemModel.getSiai_proportion());
									
								}	
							 
								
								itemModel = sumGjjItemFee(itemModel,
										m.getEofc_cp());
								m.setEofc_co_base(itemModel.getRadix());
								m.setEofc_co_sum(itemModel.getFee());
							}
						}
					}
					if (m.getEofc_em_base() != null) {
						if (m.getEofc_em_base().compareTo(zero) != -1) {
							itemModel = getCalculation(item[1], "个人",
									m.getEofc_em_base());
							if (m.getEofc_op() == null
									|| m.getEofc_op().isEmpty()) {
								try {
									// 赋最低比例
									m.initopList(itemModel.getSiai_proportion());
								} catch (Exception e) {

								}
							}
							// 如果基数等于零，不计算
							if (m.getEofc_em_base().compareTo(zero) == 0) {
								m.setEofc_em_base(zero);
								m.setEofc_em_sum(zero);
							} else {
								
								//itemModel.getSiai_proportion().replace("-", ",");
								
								itemModel.setSiai_proportion(itemModel.getSiai_proportion().replace("-", ","));
								
								
								String[] po=itemModel.getSiai_proportion().split(",");
								
								if (po.length==1)
								
								{
									m.setEofc_op(itemModel.getSiai_proportion());
									
								}	
								
								itemModel = sumGjjItemFee(itemModel,
										m.getEofc_op());
								m.setEofc_em_base(itemModel.getRadix());
								m.setEofc_em_sum(itemModel.getFee());
							}
						}
					}
					if ("委托对账".equals(GjjFeeType)) {
						sumFee(m);
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return m;
	}

	// 计算公司及个人缴交部分
	private void sumFee(EmCommissionOutFeeDetailChangeModel m) {
		BigDecimal emFee = zero;
		BigDecimal coFee = zero;
		if (m.getEofc_co_sum() != null) {
			coFee = m.getEofc_co_sum();
		}
		if (m.getEofc_em_sum() != null) {
			emFee = m.getEofc_em_sum();
		}
		m.setEofc_month_sum(coFee.add(emFee));
	}

	// 根据项目及缴交方选择算法,设置基数
	private SocialInsuranceClassInfoViewModel getCalculation(String name,
			String payunit, BigDecimal base) {
		SocialInsuranceClassInfoViewModel sicM = infoMap.get(name + payunit);
		sicM.setRadix(base);
		return sicM;
	}

	// 计算社保各项目缴交额
	private SocialInsuranceClassInfoViewModel sumSbItemFee(
			SocialInsuranceClassInfoViewModel m) {
		// 定义变量
		BigDecimal basic;
		BigDecimal fee;
		try {
			if (ifSum(m.getSicl_name())) {
				// 计算实际基数
				basic = checkBasic(m.getRadix(), m.getSiai_basic_ud(),
						m.getSiai_basic_dd());
				// 计算
				fee = calculate(basic, m.getSiai_proportion());
				// 校验金额及处理小数
				fee = dealDecimal(
						checkBasic(fee, m.getSiai_deposit_ud(),
								m.getSiai_deposit_dd()), m.getSide_decimal());
				m.setFee(fee);
				m.setRadix(basic);
			}
		} catch (Exception e) {
			m.setFee(zero);
			m.setRadix(zero);
		}
		return m;
	}

	// 计算公积金各项目缴交额
	private SocialInsuranceClassInfoViewModel sumGjjItemFee(
			SocialInsuranceClassInfoViewModel m, String gjjProportion) {
		// 定义变量
		BigDecimal basic;
		BigDecimal fee;
		try {
			if (ifSum(m.getSicl_name())) {
				// 计算实际基数
				basic = checkBasic(m.getRadix(), m.getSiai_basic_ud(),
						m.getSiai_basic_dd());
				// 计算
				fee = calculate(basic, gjjProportion);
				// 校验金额及处理小数
				fee = dealDecimal(
						checkBasic(fee, m.getSiai_deposit_ud(),
								m.getSiai_deposit_dd()), m.getSide_decimal());
				m.setFee(fee);
				m.setRadix(basic);
			}
		} catch (Exception e) {
			m.setFee(zero);
			m.setRadix(zero);
		}
		return m;
	}
}
