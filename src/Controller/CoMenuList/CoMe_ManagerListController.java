package Controller.CoMenuList;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Set;

import org.zkoss.bind.annotation.BindingParam;
import org.zkoss.bind.annotation.Command;
import org.zkoss.zul.Checkbox;
import org.zkoss.zul.Combobox;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Tree;
import org.zkoss.zul.Treecell;
import org.zkoss.zul.Treechildren;
import org.zkoss.zul.Treeitem;
import org.zkoss.zul.Treerow;

import Conn.dbconn;
import Model.CobaseMenulistModel;
import Model.RoleModel;
import Util.UserInfo;
import bll.CoMenuList.Come_OperateBll;
import bll.CoMenuList.Come_SelectBll;
import bll.SystemControl.Menu_RolePubBll;

public class CoMe_ManagerListController {
	private Menu_RolePubBll roLebll = new Menu_RolePubBll();
	private List<RoleModel> rolemolist = roLebll.getRoleData();
	private Come_SelectBll bll = new Come_SelectBll();
	private List<CobaseMenulistModel> list = new ArrayList<CobaseMenulistModel>();

	private List<CobaseMenulistModel> menulist = new ArrayList<CobaseMenulistModel>();
	private String sql = " and come_id in(select reml_come_id from CobaseMenuListRel "
			+ " where reml_role_id in(select a.rol_id from role a inner join logingroup b on a.rol_id=b.rol_id "
			+ " inner join login c on b.log_id=c.log_id where log_name='"
			+ UserInfo.getUsername() + "'))";
	private int rolId;

	public CoMe_ManagerListController() {
		if (isManager()) {
			list = bll.getEmbaseMenuListInfo("");
		} else {
			list = bll.getEmbaseMenuListInfo(sql);
		}
	}

	// 树的onCreate事件，用于生成树的时候生成树节点
	@Command
	public void oncreatetree(@BindingParam("tree") Tree tree) {
		// 如果list不等于空则开始生成树的子节点
		if (!list.isEmpty()) {
			Treechildren trc = new Treechildren();
			trc.setParent(tree);
			for (int y = 0; y < list.size(); y++) {
				CobaseMenulistModel model = list.get(y);
				if (model.getCome_pid() == 0) {
					// 生成一个树的子节点
					Treeitem newitem = new Treeitem();
					newitem.setParent(trc);
					newitem.setOpen(false);
					newitem.setLabel(model.getCome_menuname());
					newitem.setId("id" + model.getCome_id());
					newitem.setValue(model.getCome_menuurl());
					// newitem.addEventListener("onClose", new
					// MyListener());
					// 调用递归函数
					AddTreeItem(newitem, model.getCome_id(), list, tree);
				}
			}
		}
	}

	// 定义递归函数AddTreeItem()
	private void AddTreeItem(Treeitem ptem, int parentID,
			List<CobaseMenulistModel> list, Tree tree) {
		Treechildren newchild = new Treechildren();
		int h = 0;
		for (int i = 0; i < list.size(); i++) {
			CobaseMenulistModel model = list.get(i);
			if (model.getCome_pid() == parentID) {
				newchild.setParent(ptem);
				Treeitem item = new Treeitem();
				item.setOpen(false);
				item.setParent(newchild);
				item.setId("id" + model.getCome_id());
				item.setValue(model.getCome_menuurl());
				Treerow tw = new Treerow();
				Treecell tl = new Treecell();
				tl.setLabel(model.getCome_menuname());
				tl.setParent(tw);
				tw.setParent(item);
				// 函数递归
				AddTreeItem(item, model.getCome_id(), list, tree);
			}
		}
	}

	// 选择主菜单的checkbox事件
	@Command
	public void menucheck(@BindingParam("tree") Tree tree) {
		if (!menulist.isEmpty())
			menulist.clear();
		if (tree.getSelectedItem() != null) {
			Set<Treeitem> i = tree.getSelectedItems();
			for (Treeitem t : i) {
				t.setOpen(true);
				CobaseMenulistModel menumodel = new CobaseMenulistModel();
				String idstr = t.getId();
				int id = Integer.parseInt(idstr.substring(2));
				menumodel.setCome_id(id);
				this.menulist.add(menumodel);
			}
		}
	}

	// 提交
	@Command
	public void menusummit() {
		if (rolId == 0) {
			Messagebox.show("请选择操作角色");
		} else {
			Come_OperateBll bl = new Come_OperateBll();
			if (!menulist.isEmpty()) {
				String sql = "";
				// 把选择了的菜单的model一个一个取出来
				for (CobaseMenulistModel m : menulist) {
					if (sql == "") {
						sql = "" + m.getCome_id();
					} else {
						sql = sql + "," + m.getCome_id();
					}
					// 把菜单id添加到关系表
					bl.updateCobaseMenuListRel(rolId, m.getCome_id());
				}
				bl.deleteCobaseMenuListRel(sql, rolId);
				Messagebox.show("权限分配成功");
			} else {
				// 如果没有攒竹菜单则把关系表该组的数据全部删除
				bl.deleteCobaseMenuListRel("all", rolId);
				Messagebox.show("权限分配成功");
			}

		}
	}

	// 全选
	@Command
	public void checkallmenu(@BindingParam("tree") Tree tree,
			@BindingParam("ck") Checkbox ck) {
		if (!menulist.isEmpty())
			menulist.clear();
		// 得到主菜单的树所有的节点
		Collection<Treeitem> items = tree.getItems();
		for (Treeitem itemed : items) {
			itemed.setSelected(ck.isChecked());
			itemed.setOpen(true);
			if (itemed.isSelected()) {
				CobaseMenulistModel menumodel = new CobaseMenulistModel();
				String idstr = itemed.getId();
				int id = Integer.parseInt(idstr.substring(2));
				menumodel.setCome_id(id);
				this.menulist.add(menumodel);
			}
		}
	}

	// 选择操作角色列表事件
	@Command
	public void roleselect(@BindingParam("rolelist") Combobox rolelist,
			@BindingParam("tree") Tree tree,
			@BindingParam("menuall") Checkbox menuall) {
		int selectid = rolelist.getSelectedItem().getValue();
		menuall.setChecked(false);
		// 得到角色的id
		this.rolId = selectid;
		// 得到树所有的节点
		Collection<Treeitem> items = tree.getItems();
		List<CobaseMenulistModel> mlists = new ArrayList<CobaseMenulistModel>();
		// 查询主菜单权限关系表是否有该角色的数据
		mlists = bll.getCobaseMenuListRel(" and reml_role_id=" + rolId);
		// 查询业务中心菜单权限关系表是否有该角色的数据
		for (Treeitem itemed : items) {
			String idstr = itemed.getId();
			if (idstr != "" && !idstr.equals("")) {
				int id = Integer.parseInt(idstr.substring(2));
				boolean flag = ifsame(mlists, id);
				if (flag) {
					itemed.setOpen(true);
					itemed.setSelected(true);
				} else {
					itemed.setOpen(false);
					itemed.setSelected(false);
				}
			}
		}
	}

	private boolean ifsame(List<CobaseMenulistModel> mlists, int id) {
		boolean flag = false;
		if (!mlists.isEmpty()) {
			for (int i = 0; i < mlists.size(); i++) {
				if (id == mlists.get(i).getCome_id()) {
					flag = true;
				}
			}
		}
		return flag;
	}

	public List<RoleModel> getRolemolist() {
		return rolemolist;
	}

	public void setRolemolist(List<RoleModel> rolemolist) {
		this.rolemolist = rolemolist;
	}

	// 查询是否是系统管理员
	private boolean isManager() {
		String sql = "select * from role a inner join logingroup b on a.rol_id=b.rol_id "
				+ "inner join Login c on b.log_id=c.log_id "
				+ "where log_name='"
				+ UserInfo.getUsername()
				+ "' and rol_name='系统管理员'";
		boolean flag = false;
		dbconn db = new dbconn();
		try {
			ResultSet rs = db.GRS(sql);
			while (rs.next()) {
				flag = true;
			}
		} catch (Exception e) {

		}
		return flag;
	}

}
