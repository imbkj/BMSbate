package bll.SystemControl;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.zkoss.zul.ListModelList;

import dal.SystemControl.PubRoleManangerDal;
import dal.SystemControl.UserListDal;
import Model.DocumentsListModel;
import Model.LoginUserModel;
import Model.DepartmentListModel;
import Model.RoleListModel;

public class UserListBll {
	// 获取员工列表
	public List<LoginUserModel> getuserlist() throws SQLException {
		UserListDal dal = new UserListDal();
		List<LoginUserModel> list = dal.getuserlist();
		return list;
	}
	
	// 获取员工列表依据部门
	public List<LoginUserModel> getuserlistdep(int dep_id) throws SQLException {
		UserListDal dal = new UserListDal();
		List<LoginUserModel> list = dal.getuserlistfordid(dep_id);
		return list;
	}
	// 获取员工列表依据部门
		public List<LoginUserModel> getuserlistdep(String dep_id) throws SQLException {
			UserListDal dal = new UserListDal();
			List<LoginUserModel> list = dal.getuserlistfordid(dep_id);
			return list;
		}



	// 获取部门列表
	public List<DepartmentListModel> getdeplist() throws SQLException {
		UserListDal dal = new UserListDal();
		List<DepartmentListModel> list2 = dal.getdeplist();
		return list2;
	}

	// 获取角色
	public List<RoleListModel> getrolelist() throws SQLException {
		UserListDal dal = new UserListDal();
		List<RoleListModel> list3 = dal.getrolelist();
		return list3;
	}

	// 查询角色
	public List<LoginUserModel> getroleclist(int role_id) throws SQLException {
		UserListDal dal = new UserListDal();
		List<LoginUserModel> list4 = dal.getroleclist(role_id);
		return list4;
	}

	// 查询角色
	public List<RoleListModel> getrolelistc(String role_name)
			throws SQLException {
		UserListDal dal = new UserListDal();
		List<RoleListModel> list5 = dal.getrolelistc(role_name);
		return list5;
	}
}
