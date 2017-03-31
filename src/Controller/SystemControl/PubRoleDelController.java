package Controller.SystemControl;

import java.sql.SQLException;

import javax.management.relation.Role;

import org.zkoss.bind.annotation.BindingParam;
import org.zkoss.bind.annotation.Command;
import org.zkoss.bind.annotation.Init;
import org.zkoss.bind.annotation.NotifyChange;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.event.EventListener;
import org.zkoss.zk.ui.select.SelectorComposer;
import org.zkoss.zk.ui.select.annotation.Wire;
import org.zkoss.zul.Label;
import org.zkoss.zul.ListModelList;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Textbox;
import org.zkoss.zul.Window;
import org.zkoss.zul.Messagebox.ClickEvent;

import Model.RoleListModel;
import bll.SystemControl.PubRoleDelBll;
import bll.SystemControl.UserListBll;

public class PubRoleDelController extends SelectorComposer<Component> {
	private static final long serialVersionUID = 1L;
	@Wire
	private Label name;
	@Wire
	private Label index;

	UserListBll bll = new UserListBll();

	private ListModelList<RoleListModel> rolelist;// 获取角色

	@Init
	public void init() throws SQLException {
		rolelist = new ListModelList<RoleListModel>(bll.getrolelist());
	}

	// 查询角色
	@Command("search")
	@NotifyChange("rolelist")
	public void submit(@BindingParam("tb1") Textbox tb1) throws Exception {
		rolelist = new ListModelList<RoleListModel>();
		rolelist = new ListModelList<RoleListModel>(bll.getrolelistc(tb1
				.getValue()));
	}

	@Command
	public void openrole_edit(@BindingParam("id") int rol_id)
			throws SQLException {
		// int row = bll.delete(rol_id);
		PubRoleDelBll pb = new PubRoleDelBll(null, null, rol_id);
		// window.doModal();

		// 判断是否插入数据********************start**************************************************************
		if (pb.Dochek() > 0) {
			EventListener<ClickEvent> clickListener = new EventListener<Messagebox.ClickEvent>() {
				public void onEvent(ClickEvent event) throws Exception {
					if (Messagebox.Button.OK.equals(event.getButton())) {
						Executions.sendRedirect("PubRole_Del.zul");
					}
				}
			};
			Messagebox.show("删除成功!", "操作提示",
					new Messagebox.Button[] { Messagebox.Button.OK },
					Messagebox.INFORMATION, clickListener);
		} else {
			Messagebox.show("删除失败!", "操作提示", Messagebox.OK, Messagebox.ERROR);
		}
		// ******************************end*****************************************************************
	}

	public ListModelList<RoleListModel> getRolelist() {
		return rolelist;
	}

	public void setRolelist(ListModelList<RoleListModel> rolelist) {
		this.rolelist = rolelist;
	}

}
