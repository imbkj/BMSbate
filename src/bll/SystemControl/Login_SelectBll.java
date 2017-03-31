package bll.SystemControl;

import java.util.List;

import Model.DepartmentModel;
import Model.LoginModel;
import Model.RoleModel;
import dal.SystemControl.Login_SelectDal;

public class Login_SelectBll {
	private Login_SelectDal dal = new Login_SelectDal();

	// 通过log_id查询用户信息
	public LoginModel getLoginInfoByUserId(String userId) {
		return dal.getLoginInfoByUserId(userId);
	}

	// 查询用户信息
	public List<LoginModel> getLoginList(String str) {
		return dal.getLoginList(str);
	}

	// 根据部门ID查询用户信息
	public List<LoginModel> getLoginListByDepId(Integer dep_id) {
		String str = " and dep_id=" + dep_id;
		return getLoginList(str);
	}

	// 查询部门信息
	public List<DepartmentModel> getDepartmentList() {
		return dal.getDepartmentList();
	}

	// 查询角色信息
	public List<RoleModel> getRoleList() {
		return dal.getRoleList();
	}
}
