package bll.CoAgency;

import dal.CoAgency.CoAgencyLinkmanOperateDal;

public class Linkman_DelBll {
	// 删除委托机构联系人信息，传入参数，返回message数组(0失败1成功2出错)
	public String[] DelLinkmanBase(int coab_id, int cali_id, int cabc_id,
			String addname, String reason) {
		String[] message = new String[2];
		try {
			CoAgencyLinkmanOperateDal opDal = new CoAgencyLinkmanOperateDal();
			int result = 0;
			result = opDal.DelLinkman(coab_id, cali_id, cabc_id, addname,
					reason);
			if (result == 0) {
				message[0] = "1";
				message[1] = "删除机构联系人成功!";
			} else {
				message[0] = "0";
				message[1] = "删除机构联系人失败!";
			}

		} catch (Exception e) {
			message[0] = "2";
			message[1] = "机构联系人，删除出错。";

		}
		return message;
	}
}
