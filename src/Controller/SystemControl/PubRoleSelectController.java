package Controller.SystemControl;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

import org.zkoss.bind.annotation.BindingParam;
import org.zkoss.bind.annotation.Command;
import org.zkoss.bind.annotation.Init;
import org.zkoss.bind.annotation.NotifyChange;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.select.SelectorComposer;
import org.zkoss.zk.ui.select.annotation.Listen;
import org.zkoss.zk.ui.util.GenericForwardComposer;
import org.zkoss.zul.ListModelList;
import org.zkoss.zul.Textbox;
import org.zkoss.zul.Window;

import bll.SystemControl.UserListBll;
import Model.LoginUserModel;
import Model.RoleListModel;
import Model.RoleModel;

public class PubRoleSelectController extends SelectorComposer<Component> {
	UserListBll bll = new UserListBll();

	private ListModelList<RoleListModel> rolelist;// 获取角色

	private ListModelList<RoleListModel> getrolelistc;// 获取已选角色

	@Init
	public void init() throws SQLException {
		rolelist = new ListModelList<RoleListModel>(bll.getrolelist());
	}

	private Map map = new HashMap();

	@Command
	public void openrole_edit(@BindingParam("rol") final RoleListModel myDao) {
		// 专递参数rol_id
		map.put("myDao", myDao);
		Window window = (Window) Executions.createComponents(
				"PubRole_Edit.zul", null, map);
		window.doModal();
	}

	// 查询角色
	@Command("search")
	@NotifyChange("rolelist")
	public void submit(@BindingParam("tb1") Textbox tb1) throws Exception {
		rolelist = new ListModelList<RoleListModel>();
		rolelist = new ListModelList<RoleListModel>(bll.getrolelistc(tb1
				.getValue()));
	}

	public ListModelList<RoleListModel> getRolelist() {
		return rolelist;
	}

	public void setRolelist(ListModelList<RoleListModel> rolelist) {
		this.rolelist = rolelist;
	}
}
