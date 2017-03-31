package bll.CoFinanceManage;

import java.math.BigDecimal;

import dal.CoFinanceManage.Cfma_AgencyOperateDal;

public class Cfma_AgencyOperateBll {
	private Cfma_AgencyOperateDal dal = new Cfma_AgencyOperateDal();

	// 添加机构收款
	public int addAgencyCollect(int coab_id, String cfab_number,
			BigDecimal paidin, String username, String remark) {
		return dal.addAgencyCollect(coab_id, cfab_number, paidin, username,
				remark);
	}

	// 机构领款
	public int addAgencyDrawMoney(String cfab_number, int coab_id,
			BigDecimal drawEx, String username, String remark) {
		return dal.addAgencyDrawMoney(cfab_number, coab_id, drawEx, username,
				remark);
	}

	// 账单收款
	public String[] addAgencyBillCollect(int coab_id, String cfab_number,
			BigDecimal paidin, String username, String remark) {
		String[] message = new String[2];
		try {
			int i = addAgencyCollect(coab_id, cfab_number, paidin, username,
					remark);
			if (i == 1) {
				i = addAgencyDrawMoney(cfab_number, coab_id, paidin, username,
						remark);
				if (i == 1) {
					message[0] = "1";
					message[1] = "添加账单收款成功。";
				} else {
					message[0] = "1";
					message[1] = "账单收款已录入，分配金额时出错，请复制该信息并与计算机部门联系。";
				}
			} else {
				message[0] = "1";
				message[1] = "账单收款已录入，分配金额时出错，请复制该信息并与计算机部门联系。";
			}
		} catch (Exception e) {
			e.printStackTrace();
			message[0] = "2";
			message[1] = "账单收款录入出错，请复制该信息并与计算机部门联系。";
		}
		return message;
	}

	// 账单核销
	public int agencyBillChargeOff(String cfab_number, String username,
			String reason) {
		return dal.agencyBillChargeOff(cfab_number, username, reason);
	}
}
