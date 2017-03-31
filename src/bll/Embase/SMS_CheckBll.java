package bll.Embase;

import java.sql.SQLException;
import java.util.List;

import Model.SMSModel;
import dal.Embase.SMS_CheckDal;

public class SMS_CheckBll {
	// 获取短信信息
	public List<SMSModel> getsmslist(String gid) throws SQLException {
		SMS_CheckDal dal = new SMS_CheckDal();
		List<SMSModel> list = dal.getsmslist(gid);
		return list;
	}

	// 获取短信信息
	public List<SMSModel> getsmsclasslist(String type,String mobile) throws SQLException {
		SMS_CheckDal dal = new SMS_CheckDal();
		List<SMSModel> list = dal.getsmsclasslist(type,mobile);
		return list;
	}
	
	// 获取短信信息
		public List<SMSModel> getsmsclasslistcon(String type,String mobile) throws SQLException {
			SMS_CheckDal dal = new SMS_CheckDal();
			List<SMSModel> list = dal.getsmsclasslistcon(type,mobile);
			return list;
		}
	
	// 获取员工信息
	public List<SMSModel> getsmsbase(String username) throws SQLException {
		SMS_CheckDal dal = new SMS_CheckDal();
		List<SMSModel> list = dal.getsmsbase(username);
		return list;
	}

	// 图片上传
	public String sms_add(String mobile, String content, String username)
			throws Exception {
		int result = 0;
		SMS_CheckDal dal = new SMS_CheckDal();
		String message;
		result = dal.sms_add(mobile, content, username);
		if (result != 0) {
			message = "1";
		} else {
			message = "0";
		}
		return message;
	}
}
