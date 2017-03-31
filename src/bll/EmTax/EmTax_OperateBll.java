package bll.EmTax;

import Model.EmSalaryInfoModel;
import dal.EmTax.EmTax_OperateDal;

public class EmTax_OperateBll {
	private EmTax_OperateDal dal = new EmTax_OperateDal();

	// 分配个税申报地
	public String[] assginTaxPlace(EmSalaryInfoModel m) {
		String[] message = new String[2];
		int result = 0;
		try {
			result = dal.assginTaxPlace(m);

			if (result == 0) {
				message[0] = "1";
				message[1] = "操作成功!";
			} else {
				message[0] = "0";
				message[1] = "操作失败!";
			}
		} catch (Exception e) {
			message[0] = "2";
			message[1] = "操作出错。";
		}
		return message;
	}

}
