package bll.Workflow;

import java.util.List;

import Model.LoginModel;
import dal.Workflow.WfNodeDisUserDal;

public class WfNode_DisUserBll {
	private WfNodeDisUserDal dal = new WfNodeDisUserDal();

	// 获取部门数据
	public List<String> getDepartment() {
		List<String> depList = dal.getDepartment();
		return depList;
	}

	// 根据节点ID获取已选部门数据
	public List<String> getDepartment(int wfno_id) {
		List<String> depList = dal.getDepartment(wfno_id);
		return depList;
	}

	// 获取角色数据
	public List<String> getRole() {
		List<String> roleList = dal.getRole();
		return roleList;
	}

	// 根据节点ID获取角色数据
	public List<String> getRole(int wfno_id) {
		List<String> roleList = dal.getRole(wfno_id);
		return roleList;
	}

	// 获取用户数据
	public List<String> getUser() {
		List<String> userList = dal.getUser();
		return userList;
	}

	// 根据节点ID获取用户数据
	public List<String> getUser(int wfno_id) {
		List<String> userList = dal.getUser(wfno_id);
		return userList;
	}

	// 初始获取选择用户的列表信息
	public List<LoginModel> getUserList(int wfno_id) {
		List<LoginModel> userList = dal.getUserList(wfno_id);
		return userList;
	}

	// 根据所选内容获取用户的列表信息
	public List<LoginModel> getUserList(Object depStr, Object roleStr,
			Object userStr) {
		String dep = checkStr(depStr);
		String role = checkStr(roleStr);
		String user = checkStr(userStr);
		List<LoginModel> userList = dal.getUserList(dep, role, user);
		return userList;
	}

	// 编辑获取选项（修改object内容为带''的String）
	private String checkStr(Object obj) {
		String str = obj.toString();
		str = str.replace("[", "'");
		str = str.replace("]", "'");
		str = str.replace(",", "','");
		str = str.replace(" ", "");
		return str;
	}

	// 编辑获取选项（修改object内容为String）
	private String DisStr(Object obj) {
		String str = obj.toString();
		str = str.replace("[", "");
		str = str.replace("]", "");
		str = str.replace(" ", "");
		return str;
	}
	// 提交分配结果
	public String[] UpdateNodeUser(int wfno_id, Object depStr, Object roleStr,
			Object userStr, String addname) {
		String[] message = new String[2];
		try {
			String dep = DisStr(depStr);
			String role = DisStr(roleStr);
			String user = DisStr(userStr);
			int Del = dal.DelNodeUser(wfno_id);
			int upcount = 0;
			if (Del == 1) {
				if (!"".equals(dep)) {
					String[] depCon = dep.split(",");
					for (String selDep : depCon) {
						upcount = upcount
								+ dal.UpdateNodeUser(wfno_id, 1, selDep,
										addname);
					}

				}
				if (!"".equals(role)) {
					String[] roleCon = role.split(",");
					for (String selRole : roleCon) {
						upcount = upcount
								+ dal.UpdateNodeUser(wfno_id, 2, selRole,
										addname);
					}
				}
				if (!"".equals(user)) {
					String[] userCon = user.split(",");
					for (String selUser : userCon) {
						upcount = upcount
								+ dal.UpdateNodeUser(wfno_id, 3, selUser,
										addname);
					}
				}
				message[0] = "1";
				message[1] = "操作成功，共操作数据;"+upcount+"条。";
				return message;
			}
			message[0] = "0";
			message[1] = "操作失败。";
		} catch (Exception e) {
			message[0] = "2";
			message[1] = "提交信息出错。";
		}
		return message;
	}
}
