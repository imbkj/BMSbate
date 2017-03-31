package bll.CoFinanceManage;

import java.math.BigDecimal;
import java.util.List;

import Model.CoFinanceAgencyMonthlyBillModel;

import dal.CoFinanceManage.Cfma_AgencyWriteOffOperateDal;

public class Cfma_AgencyWriteOffOperateBll {
	private Cfma_AgencyWriteOffOperateDal dal = new Cfma_AgencyWriteOffOperateDal();

	// 机构冲销
	public int CoFinanceAgencyWriteOff(int coab_id,
			List<CoFinanceAgencyMonthlyBillModel> wtList,
			List<CoFinanceAgencyMonthlyBillModel> stList,
			BigDecimal writeOffEx, String username) {
		int i = 0;
		try {
			String wtNumber = getNumberList(wtList);
			String stNumber = getNumberList(stList);
			i = dal.CoFinanceAgencyWriteOff(coab_id, wtNumber, stNumber,
					writeOffEx, username);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return i;
	}

	// 拼接月份字符数组
	private String getNumberList(List<CoFinanceAgencyMonthlyBillModel> list) {
		String number = "";
		try {
			for (CoFinanceAgencyMonthlyBillModel m : list) {
				number = number + m.getCfab_number() + ",";
			}
			number = number.substring(0, number.length() - 1);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return number;
	}
}
