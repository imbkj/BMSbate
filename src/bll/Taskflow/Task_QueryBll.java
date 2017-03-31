package bll.Taskflow;

import java.util.List;

import org.zkoss.zul.ListModelList;

import dal.SystemControl.UserListDal;
import dal.Taskflow.TaskClassDal;
import dal.Taskflow.TaskListDal;
import dal.Taskflow.TaskProcessListViewDal;

import Model.TaskClassModel;
import Model.TaskListModel;
import Model.TaskProcessListModel;
import Model.loginroleModel;

public class Task_QueryBll {
	/**
	 * @Title: getTaskProcessList
	 * @Description: TODO(获取任务单视图列表)
	 * @param logid
	 * @param depid
	 * @param rolid
	 * @param taclName
	 * @param taliName
	 * @return
	 * @return List<TaskProcessListModel> 返回类型
	 * @throws
	 */
	public List<TaskProcessListModel> getTaskProcessList(Integer logid,
			Integer depid, Integer rolid,Integer userid, Integer taclName, String taliName) {
		List<TaskProcessListModel> list = new ListModelList<TaskProcessListModel>();
		TaskProcessListViewDal dal = new TaskProcessListViewDal();
		String s_logid = logid.toString().equals("") ? "%" : logid.toString();
		String s_depid = depid.toString().equals("0") ? "%" : depid.toString();
		String s_rolid = rolid.toString().equals("0") ? "%" : rolid.toString();
		String s_userid = userid.toString().equals("0") ? "%" : userid.toString();
		String s_taclName = taclName.toString().equals("0") ? "%" : taclName.toString();
		taliName = taliName.equals("") ? "%" : taliName+"%";

		try {
			list = dal.getList(s_logid, s_depid, s_rolid,s_userid, s_taclName, taliName);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}

	/**
	 * @Title: getUserList
	 * @Description: TODO(获取用户列表)
	 * @param userid
	 * @param depid
	 * @param rolid
	 * @param username
	 * @return
	 * @return List<loginroleModel> 返回类型
	 * @throws
	 */
	public List<loginroleModel> getUserList(Integer userid, Integer depid,
			Integer rolid, String username) {
		List<loginroleModel> list = new ListModelList<loginroleModel>();
		UserListDal dal = new UserListDal();
		username = username.equals("") ? "%" : username + '%';
		String s_dep = depid.toString().equals("0") ? "%"
				: depid.toString() + '%';
		String s_rol = rolid.toString().equals("0") ? "%"
				: rolid.toString() + '%';

		try {
			list = dal.getUserList(userid, s_dep, s_rol, username);
		} catch (Exception e) {
			e.printStackTrace();
		}

		return list;
	}

	/**
	 * @Title: getDepList
	 * @Description: TODO(获取部门列表)
	 * @param userid
	 * @param depname
	 * @return
	 * @return List<loginroleModel> 返回类型
	 * @throws
	 */
	public List<loginroleModel> getDepList(Integer userid, String depname) {
		List<loginroleModel> list = new ListModelList<loginroleModel>();
		UserListDal dal = new UserListDal();
		depname = depname.equals("") ? "%" : depname + '%';
		try {
			list = dal.getDepList(userid, depname);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}

	/**
	 * @Title: getRolList
	 * @Description: TODO(获取角色列表)
	 * @param userid
	 * @param depid
	 * @param rolname
	 * @return
	 * @return List<loginroleModel> 返回类型
	 * @throws
	 */
	public List<loginroleModel> getRolList(Integer userid, Integer depid,
			String rolname) {
		List<loginroleModel> list = new ListModelList<loginroleModel>();
		UserListDal dal = new UserListDal();
		String s_dep = depid.toString().equals("0") ? "%"
				: depid.toString() + '%';
		rolname = rolname.equals("") ? "%" : rolname + '%';
		try {
			list = dal.getRolList(userid, s_dep, rolname);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}
	
	/** 
	* @Title: getTaskClasslist 
	* @Description: TODO(获取任务类型) 
	* @return
	* @return List<TaskClassModel>    返回类型 
	* @throws 
	*/
	public List<TaskClassModel> getTaskClasslist(){
		List<TaskClassModel> list = new ListModelList<TaskClassModel>();
		TaskClassDal dal = new TaskClassDal();
		try {
			list = dal.getDisList();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}
	
	/** 
	* @Title: getTasklist 
	* @Description: TODO(获取任务名称) 
	* @return
	* @return List<TaskListModel>    返回类型 
	* @throws 
	*/
	public List<TaskListModel> getTasklist(){
		List<TaskListModel> list = new ListModelList<TaskListModel>();
		TaskListDal dal = new TaskListDal();
		try {
			list = dal.getDisList();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}
	
	
}
