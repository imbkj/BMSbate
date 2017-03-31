package bll.EmCommercialInsurance;

import dal.EmCommercialInsurance.CI_InsurantUpload_Dal;

public class CI_InsurantUpload_Bll {

	CI_InsurantUpload_Dal dal = new CI_InsurantUpload_Dal();

	// 插入商保核对文件
	public String[] uploadCIOK(String sheetname, String filename,
			String username, String fileAllname) {
		String[] message = new String[3];
		int result = 0;
		try {

			result = dal.uploadCIOK(sheetname, filename, username, fileAllname);

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

	// 插入商保理赔明细文件
	public String[] uploadCIclaimOK(String sheetname, String filename,
			String username, String fileAllname,String email_state) {
		String[] message = new String[3];
		int result = 0;
		try {

			result = dal.uploadCIclaimOK(sheetname, filename, username, fileAllname,email_state);

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
}
