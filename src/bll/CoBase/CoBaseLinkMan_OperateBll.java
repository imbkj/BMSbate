package bll.CoBase;

import java.util.List;

import Model.CoAgencyLinkmanModel;
import Model.CoBaseLinkFamilyModel;
import dal.CoBase.CoBaseLinkman_OperateDal;

public class CoBaseLinkMan_OperateBll {
	private CoBaseLinkman_OperateDal dal = new CoBaseLinkman_OperateDal();

	// 新增公司联系人信息,传入参数，返回message数组(0失败1成功2出错)
	public String[] AddCoBaseLinkman(CoAgencyLinkmanModel m,
			List<CoBaseLinkFamilyModel> list, int cid) {
		String[] message = new String[2];
		try {
			int cali_id = dal.AddCoBaseLinkman(m, cid);
			if (cali_id > 0) {
				AddCoBaseLinkmanFamily(list, cali_id);
				message[0] = "1";
				message[1] = "新增联系人成功。";
			} else {
				message[0] = "0";
				message[1] = "新增联系人失败。";
			}
		} catch (Exception e) {
			message[0] = "2";
			message[1] = "新增联系人出错。";
		}
		return message;
	}

	// 新增公司联系人家属信息
	private void AddCoBaseLinkmanFamily(List<CoBaseLinkFamilyModel> list,
			int cali_id) {
		for (CoBaseLinkFamilyModel m : list) {
			if (m.getCblf_name() != null && !"".equals(m.getCblf_name()))
				dal.AddCoBaseLinkmanFamily(m, cali_id);
		}
	}

	// 更新公司联系人信息,传入参数，返回message数组(0失败1成功2出错)
	public String[] UpCoBaseLinkman(CoAgencyLinkmanModel m,
			List<CoBaseLinkFamilyModel> list, int cali_id) {
		String[] message = new String[2];
		try {
			int result = dal.UpCoBaseLinkman(m);
			if (result == 0) {
				dal.DelCoBaseLinkmanFamily(cali_id);
				AddCoBaseLinkmanFamily(list, cali_id);
				message[0] = "1";
				message[1] = "更新联系人成功。";
			} else {
				message[0] = "0";
				message[1] = "更新联系人失败。";
			}
		} catch (Exception e) {
			message[0] = "2";
			message[1] = "更新联系人出错。";
		}
		return message;
	}

	// 删除公司联系人信息
	public int DelCobaseLinkman(int cid, int cali_id, String reason,
			String uesrname) {
		return dal.DelCobaseLinkman(cid, cali_id, reason, uesrname);
	}

	// 检测是否已有该联系人
	public boolean existLinkman(int cid, String cali_name) {
		return dal.existLinkman(cid, cali_name);
	}
}
