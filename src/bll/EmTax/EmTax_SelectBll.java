package bll.EmTax;

import java.math.BigDecimal;
import java.math.MathContext;
import java.math.RoundingMode;
import java.util.List;

import dal.EmTax.EmTax_SelectDal;

import Model.CoBaseModel;
import Model.EmTaxInfoModel;

public class EmTax_SelectBll {
	EmTax_SelectDal dal = new EmTax_SelectDal();

	// 获取所有公司基本信息
	public List<CoBaseModel> getCoBase() {
		return dal.getCoBase();
	}

	// 员工明细时个税数据
	public List<EmTaxInfoModel> getEmTaxList(String str,String ownmonth) {
		return dal.getEmTaxList(str,ownmonth);
	}

	// 公司汇总时个税数据
	public List<EmTaxInfoModel> getCoTaxList(String str, int ifLast,String ownmonth) {
		return dal.getCoTaxList(str, ifLast,ownmonth);
	}

	// 公司个税申报地分配情况
	public List<EmTaxInfoModel> getCoTaxPlaceList(String str) {
		return dal.getCoTaxPlaceList(str);
	}

	// 所有个税申报城市
	public List<String> getCity() {
		return dal.getCity();
	}

	// 获取需分配个税申报地的员工列表
	public List<EmTaxInfoModel> getTPEmList(String str) {
		return dal.getTPEmList(str);
	}

	// 导出员工明细时个税数据更新
	public String[] upEmTax(String str,String ownmonth,String filename) {
		String[] message = new String[3];
		int result = 0;
		try {

			result = dal.upEmTax(str,ownmonth,filename);

			if (result == 0) {
				message[0] = "1";
				message[1] = "操作成功!";
				message[2] = String.valueOf(result);
			} else {
				message[0] = "0";
				message[1] = "操作失败!";
				message[2] = "0";
			}
		} catch (Exception e) {
			message[0] = "2";
			message[1] = "操作出错。";
			message[2] = "0";
		}
		return message;
	}

	// 导出公司汇总时个税数据更新
	public String[] upCoTax(String str) {
		String[] message = new String[3];
		int result = 0;
		try {

			result = dal.upCoTax(str);

			if (result == 0) {
				message[0] = "1";
				message[1] = "操作成功!";
				message[2] = String.valueOf(result);
			} else {
				message[0] = "0";
				message[1] = "操作失败!";
				message[2] = "0";
			}
		} catch (Exception e) {
			message[0] = "2";
			message[1] = "操作出错。";
			message[2] = "0";
		}
		return message;
	}

	// 获取个税税率
	public String GetTaxS(BigDecimal taxBase, int taxClass) {
		String taxS = "0";

		if (taxClass == 1) {
			if (taxBase.compareTo(BigDecimal.valueOf(0)) == 1) {
				if (taxBase.compareTo(BigDecimal.valueOf(80000)) == 1) {
					taxS = "0.45";
				} else if (taxBase.compareTo(BigDecimal.valueOf(55000)) == 1) {
					taxS = "0.35";
				} else if (taxBase.compareTo(BigDecimal.valueOf(35000)) == 1) {
					taxS = "0.30";
				} else if (taxBase.compareTo(BigDecimal.valueOf(9000)) == 1) {
					taxS = "0.25";
				} else if (taxBase.compareTo(BigDecimal.valueOf(4500)) == 1) {
					taxS = "0.20";
				} else if (taxBase.compareTo(BigDecimal.valueOf(1500)) == 1) {
					taxS = "0.10";
				} else if (taxBase.compareTo(BigDecimal.valueOf(0)) == 1) {
					taxS = "0.03";
				} else {
					taxS = "0";
				}
			}
		} else if (taxClass == 2) {
			if (taxBase.compareTo(BigDecimal.valueOf(0)) == 1) {

				MathContext mc = new MathContext(2, RoundingMode.HALF_DOWN); // 保留两位小数
				BigDecimal taxBaseD12 = taxBase.divide(BigDecimal.valueOf(12),
						mc);// 应税基数除12

				if (taxBaseD12.compareTo(BigDecimal.valueOf(80000)) == 1) {
					taxS = "0.45";
				} else if (taxBaseD12.compareTo(BigDecimal.valueOf(55000)) == 1) {
					taxS = "0.35";
				} else if (taxBaseD12.compareTo(BigDecimal.valueOf(35000)) == 1) {
					taxS = "0.30";
				} else if (taxBaseD12.compareTo(BigDecimal.valueOf(9000)) == 1) {
					taxS = "0.25";
				} else if (taxBaseD12.compareTo(BigDecimal.valueOf(4500)) == 1) {
					taxS = "0.20";
				} else if (taxBaseD12.compareTo(BigDecimal.valueOf(1500)) == 1) {
					taxS = "0.10";
				} else if (taxBaseD12.compareTo(BigDecimal.valueOf(0)) == 1) {
					taxS = "0.03";
				} else {
					taxS = "0";
				}
			}
		}

		else if (taxClass == 5) {

			BigDecimal base;

			if (taxBase.compareTo(BigDecimal.valueOf(4000)) == 1) {
				base = taxBase.multiply(BigDecimal.valueOf(0.08));
			} else {
				base = taxBase.subtract(BigDecimal.valueOf(800));
			}

			if (base.compareTo(BigDecimal.valueOf(0)) == 1) {
				if (base.compareTo(BigDecimal.valueOf(50000)) == 1) {
					taxS = "0.40";
				} else if (base.compareTo(BigDecimal.valueOf(20000)) == 1) {
					taxS = "0.30";
				} else if (base.compareTo(BigDecimal.valueOf(0)) == 1) {
					taxS = "0.20";
				} else {
					taxS = "0";
				}
			} else {
				taxS = "0";
			}
		}
		return taxS;
	}

	// 获取个税速算扣除数
	public String GetTaxM(BigDecimal taxBase, int taxClass) {

		String taxM = "0";

		if (taxClass == 1) {
			if (taxBase.compareTo(BigDecimal.valueOf(0)) == 1) {
				if (taxBase.compareTo(BigDecimal.valueOf(80000)) == 1) {
					taxM = "13505";
				} else if (taxBase.compareTo(BigDecimal.valueOf(55000)) == 1) {
					taxM = "5505";
				} else if (taxBase.compareTo(BigDecimal.valueOf(35000)) == 1) {
					taxM = "2755";
				} else if (taxBase.compareTo(BigDecimal.valueOf(9000)) == 1) {
					taxM = "1005";
				} else if (taxBase.compareTo(BigDecimal.valueOf(4500)) == 1) {
					taxM = "555";
				} else if (taxBase.compareTo(BigDecimal.valueOf(1500)) == 1) {
					taxM = "105";
				} else if (taxBase.compareTo(BigDecimal.valueOf(0)) == 1) {
					taxM = "0";
				} else {
					taxM = "0";
				}
			}
		}

		else if (taxClass == 2) {
			if (taxBase.compareTo(BigDecimal.valueOf(0)) == 1) {

				MathContext mc = new MathContext(2, RoundingMode.HALF_DOWN); // 保留两位小数
				BigDecimal taxBaseD12 = taxBase.divide(BigDecimal.valueOf(12),
						mc);// 应税基数除12

				if (taxBaseD12.compareTo(BigDecimal.valueOf(80000)) == 1) {
					taxM = "13505";
				} else if (taxBaseD12.compareTo(BigDecimal.valueOf(55000)) == 1) {
					taxM = "5505";
				} else if (taxBaseD12.compareTo(BigDecimal.valueOf(35000)) == 1) {
					taxM = "2755";
				} else if (taxBaseD12.compareTo(BigDecimal.valueOf(9000)) == 1) {
					taxM = "1005";
				} else if (taxBaseD12.compareTo(BigDecimal.valueOf(4500)) == 1) {
					taxM = "555";
				} else if (taxBaseD12.compareTo(BigDecimal.valueOf(1500)) == 1) {
					taxM = "105";
				} else if (taxBaseD12.compareTo(BigDecimal.valueOf(0)) == 1) {
					taxM = "0";
				} else {
					taxM = "0";
				}
			}
		}

		else if (taxClass == 5) {

			BigDecimal base;

			if (taxBase.compareTo(BigDecimal.valueOf(4000)) == 1) {
				base = taxBase.multiply(BigDecimal.valueOf(0.08));
			} else {
				base = taxBase.subtract(BigDecimal.valueOf(800));
			}

			if (base.compareTo(BigDecimal.valueOf(0)) == 1) {
				if (base.compareTo(BigDecimal.valueOf(50000)) == 1) {
					taxM = "7000";
				} else if (base.compareTo(BigDecimal.valueOf(20000)) == 1) {
					taxM = "2000";
				} else if (base.compareTo(BigDecimal.valueOf(0)) == 1) {
					taxM = "0";
				} else {
					taxM = "0";
				}
			} else {
				taxM = "0";
			}

		}
		return taxM;
	}
}
