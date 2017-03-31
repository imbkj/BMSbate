package Util;

import java.math.BigDecimal;
import java.util.Map;

import dal.EmSalary.EmSalaryFunctionsDal;

public class EmSalaryFunctions {
	// 计算个人所得税
	public double PTax(double TaxBase) {
		double tax = 0;
		if (TaxBase > 0) {
			if (TaxBase > 80000) {
				tax = TaxBase * 0.45 - 13505;
			} else if (TaxBase > 55000) {
				tax = TaxBase * 0.35 - 5505;
			} else if (TaxBase > 35000) {
				tax = TaxBase * 0.30 - 2755;
			} else if (TaxBase > 9000) {
				tax = TaxBase * 0.25 - 1005;
			} else if (TaxBase > 4500) {
				tax = TaxBase * 0.20 - 555;
			} else if (TaxBase > 1500) {
				tax = TaxBase * 0.10 - 105;
			} else if (TaxBase > 0) {
				tax = TaxBase * 0.03;
			}
		}
		tax = (new BigDecimal(tax)).setScale(2, BigDecimal.ROUND_HALF_UP)
				.doubleValue();
		return tax;
	}

	// 计算年终奖金税
	public double DBTax(double DBase) {
		double tax = 0;
		if (DBase > 0) {
			if (DBase / 12 > 80000) {
				tax = DBase * 0.45 - 13505;
			} else if (DBase / 12 > 55000) {
				tax = DBase * 0.35 - 5505;
			} else if (DBase / 12 > 35000) {
				tax = DBase * 0.30 - 2755;
			} else if (DBase / 12 > 9000) {
				tax = DBase * 0.25 - 1005;
			} else if (DBase / 12 > 4500) {
				tax = DBase * 0.20 - 555;
			} else if (DBase / 12 > 1500) {
				tax = DBase * 0.10 - 105;
			} else if (DBase / 12 > 0) {
				tax = DBase * 0.03;
			}
		}
		tax = (new BigDecimal(tax)).setScale(2, BigDecimal.ROUND_HALF_UP)
				.doubleValue();
		return tax;
	}

	// 劳务报酬个人所得税(包含免税部分计算)
	public double LWTax(double LWBase) {
		double tax = 0;
		double base = 0;

		// 计算应税所得
		if (LWBase > 4000) {
			base = LWBase * 0.8;
		} else {
			base = LWBase - 800;
		}

		if (base > 0) {
			if (base > 50000) {
				tax = base * 0.40 - 7000;
			} else if (base > 20000) {
				tax = base * 0.30 - 2000;
			} else {
				tax = base * 0.20;
			}
		}
		tax = (new BigDecimal(tax)).setScale(2, BigDecimal.ROUND_HALF_UP)
				.doubleValue();
		return tax;
	}

	// 劳务报酬个人所得税(未包含免税部分计算，适用于有其他调税部分的情况，如商业保险费)
	public double LWTax2(double LWBase) {
		double tax = 0;
		double base = 0;

		base = LWBase;

		if (base > 0) {
			if (base > 50000) {
				tax = base * 0.40 - 7000;
			} else if (base > 20000) {
				tax = base * 0.30 - 2000;
			} else {
				tax = base * 0.20;
			}
		}
		tax = (new BigDecimal(tax)).setScale(2, BigDecimal.ROUND_HALF_UP)
				.doubleValue();
		return tax;
	}

	/**
	 * Function: HouseOver Author: 李文洁 Date: 2014-6-17 16:40 Description:
	 * 计算住房公积金超额部分 Input: HouseR：住房公积金基数，HouseP：住房公积金缴交比率， city:城市
	 * Return:住房公积金超额部分 Other:
	 **/
	public double HouseOver(double HouseR, double HouseP, double HouseCR,
			double HouseCP, String city, Integer ownmonth) {
		double HouseOver = 0;
		try {
			EmSalaryFunctionsDal dal = new EmSalaryFunctionsDal();
			Map<String, BigDecimal> map;
			if ("".equals(city) || city == null) {
				map = dal.getSpec("深圳", ownmonth);
			} else {
				map = dal.getSpec(city, ownmonth);
			}
			BigDecimal RadixL = map.get("住房公积金免税基数上限") == null ? BigDecimal.ZERO
					: map.get("住房公积金免税基数上限");
			BigDecimal PercentL = map.get("住房公积金免税比例上限") == null ? BigDecimal.ZERO
					: map.get("住房公积金免税比例上限");
			BigDecimal HouseNotTaxL = map.get("住房公积金免税部分上限") == null ? BigDecimal.ZERO
					: map.get("住房公积金免税部分上限");
			BigDecimal HouseRbd = new BigDecimal(HouseR);
			BigDecimal HousePbd = new BigDecimal(HouseP);
			BigDecimal HouseCRd = new BigDecimal(HouseCR);
			BigDecimal HouseCPd = new BigDecimal(HouseCP);
			BigDecimal h_over = BigDecimal.ZERO;
			if (HouseRbd.compareTo(RadixL) == 1
					&& HousePbd.compareTo(PercentL) != 1) {
				h_over = (HouseRbd.subtract(RadixL)).multiply(HousePbd).add(
						(HouseCRd.subtract(RadixL)).multiply(HouseCPd));
			} else if (HouseRbd.compareTo(RadixL) != 1
					&& HousePbd.compareTo(PercentL) == 1) {
				h_over = HouseRbd.multiply(HousePbd.subtract(PercentL)).add(
						(HouseCPd.subtract(PercentL)).multiply(HouseCRd));
			} else if (HouseRbd.compareTo(RadixL) == 1
					&& HousePbd.compareTo(PercentL) == 1) {
				h_over = (HouseRbd.multiply(HousePbd).subtract(HouseNotTaxL
						.divide(new BigDecimal(2)))).add((HouseCRd
						.multiply(HouseCPd)).subtract(HouseNotTaxL
						.divide(new BigDecimal(2))));
			}
			if (h_over.compareTo(BigDecimal.ZERO) == 1) {
				HouseOver = h_over.setScale(2, BigDecimal.ROUND_HALF_UP)
						.doubleValue();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return HouseOver;
	}

	/**
	 * Function: HouseOver2 Author: 李文洁 Date: 2015-6-30 17:30 Input:
	 * HouseOP(个人缴存额), double HouseCP(公司缴存额)， city:城市 Return: Other:
	 **/
	public double HouseOver2(double HouseOP, double HouseCP, String city,
			Integer ownmonth) {
		double HouseOver = 0;
		try {
			EmSalaryFunctionsDal dal = new EmSalaryFunctionsDal();
			Map<String, BigDecimal> map;
			if ("".equals(city) || city == null) {
				map = dal.getSpec("深圳", ownmonth);
			} else {
				map = dal.getSpec(city, ownmonth);
			}
			BigDecimal RadixL = map.get("住房公积金免税基数上限") == null ? BigDecimal.ZERO
					: map.get("住房公积金免税基数上限");
			BigDecimal PercentL = map.get("住房公积金免税比例上限") == null ? BigDecimal.ZERO
					: map.get("住房公积金免税比例上限");
			BigDecimal HouseOPd = new BigDecimal(HouseOP);
			BigDecimal HouseCPd = new BigDecimal(HouseCP);
			BigDecimal h_over = BigDecimal.ZERO;
			h_over = (HouseOPd.add(HouseCPd)).subtract(RadixL
					.multiply(PercentL).multiply(new BigDecimal(2)));
			if (h_over.compareTo(BigDecimal.ZERO) == 1) {
				HouseOver = h_over.setScale(2, BigDecimal.ROUND_HALF_UP)
						.doubleValue();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return HouseOver;
	}

	// // 测试
	// public static void main(String arg[]) {
	// EmSalaryFunctions ef = new EmSalaryFunctions();
	// System.out.println(ef.HouseOver(14753, 0.11));
	// System.out.println(ef.HouseOver(1000, 0.15));
	// System.out.println(ef.HouseOver(14753, 0.2));
	// System.out.println(ef.HouseOver(1000, 0.1));
	// System.out.println(ef.HouseOver(18692.999999, 0.111111));
	// }
}
