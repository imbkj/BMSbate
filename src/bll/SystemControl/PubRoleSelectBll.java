package bll.SystemControl;

import java.sql.SQLException;
import java.util.List;

import Model.RoleListModel;
import dal.SystemControl.UserListDal;

public class PubRoleSelectBll {
	//获取角色
			public List<RoleListModel> getrolelist() throws SQLException {
				UserListDal dal = new UserListDal();
				List<RoleListModel> list3 = dal.getrolelist();
				return list3;
			}
}
