package bll.CoAgency;

import java.text.SimpleDateFormat;
import java.util.Date;

import Model.CoAgencyLinkmanModel;
import dal.CoAgency.CoAgencyLinkmanOperateDal;

public class Linkman_UpBll {
	// 更新委托机构联系人信息，传入参数，返回message数组(0失败1成功2出错)
	public String[] UpdateLinkmanBase(int cali_id, String cali_linkman,
			String cali_name, String cali_ename, String cali_job,
			String cali_tel, String cali_mobile, String cali_fax,
			String cali_email, String cali_birth, String cali_hobby,
			String cali_address, String cali_personality, String cali_remark,
			String cali_vip, String cali_addname) {
		String[] message = new String[2];
		try {
			CoAgencyLinkmanOperateDal opDal = new CoAgencyLinkmanOperateDal();
			message = existAddLinkman(cali_name, cali_tel, cali_mobile);
			if (message[0] == null) {
				CoAgencyLinkmanModel m = new CoAgencyLinkmanModel();
				int result = 0;
				int vip = Integer.parseInt(cali_vip);
				m.setCali_id(cali_id);
				m.setCali_linkman(cali_linkman);
				m.setCali_name(cali_name);
				m.setCali_ename(cali_ename);
				m.setCali_job(cali_job);
				m.setCali_tel(cali_tel);
				m.setCali_mobile(cali_mobile);
				m.setCali_fax(cali_fax);
				m.setCali_email(cali_email);
				m.setCali_birth(cali_birth);
				m.setCali_hobby(cali_hobby);
				m.setCali_address(cali_address);
				m.setCali_personality(cali_personality);
				m.setCali_remark(cali_remark);
				m.setCali_vip(vip);
				m.setCali_addname(cali_addname);
				result = opDal.UpLinkman(m);
				if (result == 0) {
					message[0] = "1";
					message[1] = "更新机构联系人成功!";
				} else if (result == -110) {
					message[0] = "0";
					message[1] = "数据并未改动，无需提交！";
				} else {
					message[0] = "0";
					message[1] = "更新机构联系人失败!";
				}
			}
		} catch (Exception e) {
			message[0] = "2";
			message[1] = "机构联系人，更新出错。";

		}
		return message;
	}

	// Date类型转换String
	public String DatetoSting(Date d) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		String Date = sdf.format(d);
		return Date;
	}

	// 检测新增委托机构联系人信息，录入数据的正确性
	private String[] existAddLinkman(String cali_name, String cali_tel,
			String cali_mobile) {
		String[] message = new String[2];
		try {
			if (cali_name == "" || cali_name == null) {
				message[0] = "0";
				message[1] = "请输入联系人名称。";
			} else if ((cali_tel == "" || cali_name == null)
					&& (cali_mobile == "" || cali_mobile == null)) {
				message[0] = "0";
				message[1] = "请输入手机或者座机其中任意一个联系方式!";
			}
		} catch (Exception e) {
			message[0] = "0";
			message[1] = "机构联系人，更新出错。";
		}
		return message;
	}
}
