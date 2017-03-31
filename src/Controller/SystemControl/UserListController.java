package Controller.SystemControl;

import java.sql.SQLException;

import org.zkoss.bind.annotation.BindingParam;
import org.zkoss.bind.annotation.Command;
import org.zkoss.bind.annotation.Init;
import org.zkoss.bind.annotation.NotifyChange;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.select.SelectorComposer;
import org.zkoss.zul.Combobox;
import org.zkoss.zul.ListModelList;

import Model.LoginUserModel;
import Model.DepartmentListModel;
import bll.SystemControl.UserListBll;
import Model.RoleListModel;

public class UserListController extends SelectorComposer<Component> {

	UserListBll bll = new UserListBll();
	DepartmentListModel depm =new DepartmentListModel();
	private ListModelList<LoginUserModel> userlist;// 获取员工列表

	private ListModelList<DepartmentListModel> deplist;// 获取部门列表
	
	private ListModelList<RoleListModel> rolelist;// 获取角色
	
	private ListModelList<LoginUserModel> roleclist;// 获取已选角色
	
	public  int dep_id;

	@Init
	public void init() throws SQLException {
		deplist = new ListModelList<DepartmentListModel>(bll.getdeplist());
		userlist = new ListModelList<LoginUserModel>(bll.getuserlist());
		
		rolelist = new ListModelList<RoleListModel>(bll.getrolelist());
	}
	
	//查询已分配角色
			@Command("rolesubmit")
			@NotifyChange("roleclist")
			public void submit(@BindingParam("tb1") Combobox tb1) throws Exception {
				roleclist = new ListModelList<LoginUserModel>();
				roleclist=new ListModelList <LoginUserModel>(bll.getroleclist(Integer.parseInt(tb1.getSelectedItem().getValue().toString())));
			}
			
			
			//城市和默认机构
			@Command("pidOnChange")
			@NotifyChange({ "M", "userlist" })
			public void cityOnChange() throws SQLException {
				
				userlist = new ListModelList<LoginUserModel>(bll.getuserlistdep(depm.getDep_id()));
				
			}
			@Command("depsubmit")	
			@NotifyChange("userlist")
			public void getuserlist() throws SQLException
			{
				userlist = new ListModelList<LoginUserModel>(bll.getuserlistdep(depm.getDep_name()));
			}
			
		
			
			

	public int getDep_id() {
				return dep_id;
			}

			public void setDep_id(int dep_id) {
				this.dep_id = dep_id;
			}


	public DepartmentListModel getDepm() {
				return depm;
			}

			public void setDepm(DepartmentListModel depm) {
				this.depm = depm;
			}

	public ListModelList<LoginUserModel> getUserlist() {
		return userlist;
	}
	

	public void setUserlist(ListModelList<LoginUserModel> userlist) {
		this.userlist = userlist;
	}

	public ListModelList<DepartmentListModel> getDeplist() {
		return deplist;
	}

	public void setDeplist(ListModelList<DepartmentListModel> deplist) {
		this.deplist = deplist;
	}

	public ListModelList<RoleListModel> getRolelist() {
		return rolelist;
	}

	public void setRolelist(ListModelList<RoleListModel> rolelist) {
		this.rolelist = rolelist;
	}
	
	public ListModelList<LoginUserModel> getRoleclist() {
		return roleclist;
	}

	public void setRoleclist(ListModelList<LoginUserModel> roleclist) {
		this.roleclist = roleclist;
	}
}
