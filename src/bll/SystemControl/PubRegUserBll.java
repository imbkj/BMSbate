package bll.SystemControl;

import java.util.List;

import dal.SystemControl.PubRegUserDal;

import Model.LoginModel;

public class PubRegUserBll {
	private Integer y;
	private Integer y1;
	private PubRegUserDal data;
	private PubRegUserDal data2;
	private LoginModel reg;
	private LoginModel chk;

	public void PubRegUserBllAdd(String name, int teamleader, String phone,
			String sex, String psd, String email, String mobile, String intime,
			int dep_id, int role_id) {

		reg = new LoginModel(0, name, teamleader, phone, sex, psd, email,
				mobile, intime, dep_id, role_id);
	}

	public void UserCF(String name) {
		chk = new LoginModel(0, name);
	}

	public int Dochek() {

		data = new PubRegUserDal();

		int y = 0;
		try {
			y = data.AddReg(reg);
		} catch (Exception e) {

			e.printStackTrace();
		}
		return y;
	}

	public void setY(Integer y) {
		this.y = y;
	}

	public int DochekCF() {
		data2 = new PubRegUserDal();

		int y1 = 0;
		try {
			y1 = data2.UserCF(chk);
		} catch (Exception e) {

			e.printStackTrace();
		}
		return y1;
	}

	public void setY1(Integer y1) {
		this.y1 = y1;
	}

	// 查询系统用户信息
	public List<LoginModel> getLoginInfo(String str) {
		data = new PubRegUserDal();
		return data.getLoginInfo(str);
	}

	// 修改用户信息
	public Integer User_Edit(LoginModel m) {
		PubRegUserDal dal = new PubRegUserDal();
		return dal.User_Edit(m);
	}

	// 修改用户信息并分配角色和部门
	public Integer User_Portion(LoginModel m) {
		PubRegUserDal dal = new PubRegUserDal();
		return dal.User_Portion(m);
	}
}
