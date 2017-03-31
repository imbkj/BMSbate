package Controller.SystemControl;

import bll.Taskflow.Task_ListBll;

import Model.TaskListGroupModel;
import impl.UserInfoImpl;

import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.zkoss.bind.BindUtils;
import org.zkoss.bind.annotation.BindingParam;
import org.zkoss.bind.annotation.Command;
import org.zkoss.bind.annotation.Init;
import org.zkoss.bind.annotation.NotifyChange;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.Path;
import org.zkoss.zk.ui.Session;
import org.zkoss.zk.ui.UiException;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.EventListener;
import org.zkoss.zk.ui.event.EventQueue;
import org.zkoss.zk.ui.event.EventQueues;
import org.zkoss.zkex.zul.Fisheye;
import org.zkoss.zul.Div;
import org.zkoss.zul.Grid;
import org.zkoss.zul.Group;
import org.zkoss.zul.Hlayout;
import org.zkoss.zul.Iframe;
import org.zkoss.zul.Label;
import org.zkoss.zul.Listbox;
import org.zkoss.zul.Menuitem;
import org.zkoss.zul.Menupopup;
import org.zkoss.zul.Tab;
import org.zkoss.zul.Tabbox;
import org.zkoss.zul.Tabpanel;
import org.zkoss.zul.Tabs;
import org.zkoss.zul.Tree;
import org.zkoss.zul.Treecell;
import org.zkoss.zul.Treechildren;
import org.zkoss.zul.Treeitem;
import org.zkoss.zul.Treerow;
import org.zkoss.zul.Window;

import bll.SystemControl.MenuListBll;
import Model.FixedTabMenuListModel;
import Model.MenuListModel;
import Model.WfTaskListInfoModel;
import Util.UserInfo;
import service.UserInfoService;

public class Menu_TreeController {
	/*
	 * Author:陈耀家 Create date: 09/06/2013 Description:使用tree生成菜单
	 */
	private static final long serialVersionUID = 1L;

	private String nowtabid;
	private EventQueue<Event> que;
	private EventQueue<Event> que2;
	private String indexsrc = "main.zul";

	private Session ss = Executions.getCurrent().getDesktop().getSession();
	// String[] message = lgbll.checkLogin("彭耀", "123", ss);

	private MenuListBll bll = new MenuListBll();
	private TaskListGroupModel tlgroupmodel;
	private String nowtime;

	String username = UserInfo.getUsername();

	Session session = Executions.getCurrent().getDesktop().getSession();
	UserInfoService uservice = new UserInfoImpl(session);
	private Window win = (Window) Path.getComponent("/indexwindow");

	// private boolean ifdiv=false,ifhl=true;

	public Menu_TreeController() {
		// 查询用户角色首页
		String src = bll.getIndexSrc();
		if (src != null && !src.equals("")) {
			indexsrc = src;
		}
		que = EventQueues.lookup("task" + UserInfo.getUserid(),
				EventQueues.SESSION, true);
		que.subscribe(new EventListener<Event>() {
			public void onEvent(Event evt) {
				taskRefresh();
			}
		});

		que2 = EventQueues.lookup("tab" + UserInfo.getUserid(),
				EventQueues.SESSION, true);
		que2.subscribe(new EventListener<Event>() {
			public void onEvent(Event evt) {
				Map map = (Map) evt.getData();
				String url = (String) map.get("url");
				String tabtext = map.get("tabtext").toString();
				indexAddTab(url, tabtext);
			}
		});
	}

	private void taskRefresh() {
		try {
			inlist = tbll.getTaskBycoprid(UserInfo.getUsername());
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		list = tbll.getTaskList(inlist);
		BindUtils.postNotifyChange(null, null, this, "list");
	}

	Task_ListBll tbll = new Task_ListBll();
	// int copr_id = (Integer) Executions.getCurrent().getArg().get("copr_id");
	private boolean showGroup = false;
	private List<WfTaskListInfoModel> list = new ArrayList<WfTaskListInfoModel>();
	private List<WfTaskListInfoModel> inlist = new ArrayList<WfTaskListInfoModel>();
	private List<WfTaskListInfoModel> alllist = new ArrayList<WfTaskListInfoModel>();

	// 初始化
	//@Init
	public void init() throws SQLException {
		inlist = tbll.getTaskBycoprid(UserInfo.getUsername());
		list = tbll.getTaskList(inlist);
		alllist = list;
	}

	// 查询
	@Command
	@NotifyChange("list")
	public void search(@BindingParam("val") String val) {
		if (val != null && !val.equals("")) {
			list = tbll.getTaskClassList(alllist, val);
		} else {
			list = tbll.getTaskList(inlist);
		}
	}

	public List<WfTaskListInfoModel> getList() {
		return list;
	}

	public void setList(List<WfTaskListInfoModel> list) {
		this.list = list;
	}

	@Command
	public void opens(@BindingParam("group") Group groupid) {
		groupid.setOpen(false);
	}

	// 点击任务单生成页面
	@Command
	public void newtab(@BindingParam("tabs") Tabs tabs,
			@BindingParam("tree") Tree tree, @BindingParam("tig_1") Tab tig_1,
			@BindingParam("tig00") Tab tig00,
			@BindingParam("tbx1") Tabbox tbx1,
			@BindingParam("listb") Listbox listb) {
		if (list.size() > 0) {
			if (listb.getSelectedItem() != null) {
				WfTaskListInfoModel taskmodel = listb.getSelectedItem()
						.getValue();
				List list = tbx1.getTabs().getChildren();
				List listp = tbx1.getTabpanels().getChildren();
				int k = 0;
				for (int j = 0; j < list.size(); j++) {
					String ggh = ((Component) list.get(j)).getId();
					if (ggh != null && !ggh.equals("")) {
						String idd = ggh.trim().substring(3);
						if (idd.equals("152")) {
							((Tab) list.get(j)).close();
							k = 0;
						}
					}
				}
				if (k == 0) {
					List<MenuListModel> listk = bll.getMenuId(152);
					MenuListModel model = (MenuListModel) listk.get(0);
					Tab newTab = new Tab(model.getMeu_name());
					newTab.setSelected(true);
					newTab.addEventListener("onClose", new MyListener1(tig00));
					newTab.setClosable(true);
					newTab.setId("tig" + model.getMeu_id());
					newTab.setImage("images/vs_aspx.png");
					Tabpanel newTabpanel = new Tabpanel();
					newTabpanel.setId("tip" + model.getMeu_id());

					Iframe fm = new Iframe("/Taskflow/Task_ContentList.zul?id="
							+ taskmodel.getTacl_id());
					newTabpanel.appendChild(fm);
					tbx1.getTabs().insertBefore(newTab, tig_1);
					newTabpanel.setParent(tbx1.getTabpanels());
				}
			}
		}
	}

	// 标签的双击事件
	@Command
	public void doubleTab(@BindingParam("tbx1") Tabbox tbx1,
			@BindingParam("tig00") Tab tig00) {
		List list = tbx1.getTabs().getChildren();
		int num = list.size();
		int j = 0;
		for (int i = num - 1; i >= 0; i--) {
			String ggh = ((Tab) list.get(i)).getId();
			if (!ggh.equals("tig00") && !ggh.equals("tig_1")) {
				Tab t = (Tab) list.get(i);
				if (t.isSelected()) {
					t.close();
					j = i - 1;
				}
			}
		}
		if (j > 0) {
			Tab t2 = (Tab) list.get(j);
			t2.setSelected(true);
		} else {
			tig00.setSelected(true);
		}
	}

	// 标签的点击事件
	@Command
	public void onClickTab(@BindingParam("tbx1") Tabbox tbx1,
			@BindingParam("tree") Tree tree,
			@BindingParam("frstindex") Tabpanel frstindex,
			@BindingParam("fixedtab") Menuitem fixedtab) {
		List list = tbx1.getTabs().getChildren();
		int num = list.size();
		int j = 0;
		String tabid = "";
		for (int i = num - 1; i >= 0; i--) {
			Tab tabed = (Tab) list.get(i);
			if (tabed.isSelected()) {
				tabid = ((Tab) list.get(i)).getId();
				if (tabid != null && !tabid.equals("")) {
					String ids = tabid.trim().substring(3);
					// 判断是否是点击了首页标签，是的话就刷新首页
					if (tabid == "tig00" || tabid.equals("tig00")) {
						// 刷新首页
						frstindex.invalidate();
						// 设置固定标签的按钮为不可见
						fixedtab.setVisible(false);
					} else {
						// 根据菜单id查询点击时是否可刷新
						if (bll.ifreflesh(ids)) {
							// Tab tb=((Tab) list.get(i));
							List listp = tbx1.getTabpanels().getChildren();
							Tabpanel tp = (Tabpanel) listp.get(i);
							tp.invalidate();
						}
						// 设置固定标签的按钮为可见
						fixedtab.setVisible(true);
						// 获取被点击的标签id

						int mid = Integer.parseInt(ids);
						// 根据id和用户名查询该标签是否已经添加了固定标签
						FixedTabMenuListModel fmodel = bll
								.getFixedTabMenuListid(username, mid);
						if (fmodel.getId() == 0) {
							fixedtab.setLabel("固定标签");
						} else {
							fixedtab.setLabel("取消固定标签");
						}
					}
				}
			}
		}
		if (tabid != null && !tabid.equals("")) {
			Collection<Treeitem> items = tree.getItems();
			for (Treeitem itemed : items) {
				if (itemed.getId() != null && !itemed.getId().equals("")) {
					if (itemed.getId().substring(3) == tabid.substring(3)
							|| itemed.getId().substring(3)
									.equals(tabid.substring(3))) {
						itemed.setSelected(true);
					}
				}
			}
		}
	}

	// Treechildren的点击事件监控类
	class TreeMyListener implements org.zkoss.zk.ui.event.EventListener {
		Tree tree;
		Tabbox tbx1;
		Menuitem fixedtab;
		Tab tig_1;
		Tab tig00;

		public TreeMyListener(Tree tree, Tabbox tbx1, Menuitem fixedtab,
				Tab tig_1, Tab tig00) {
			this.tree = tree;
			this.tbx1 = tbx1;
			this.fixedtab = fixedtab;
			this.tig_1 = tig_1;
			this.tig00 = tig00;
		}

		public void onEvent(Event e) throws UiException {
			addTab(0, tree, tbx1, fixedtab, tig_1, tig00);
		}
	}

	// 折叠所有的树的节点
	@Command
	public void closetree(@BindingParam("tree") Tree tree) {
		Collection<Treeitem> items = tree.getItems();
		for (Treeitem itemed : items)
			itemed.setOpen(false);
	}

	// 折叠所有的树的节点
	@Command
	public void opentree(@BindingParam("tree") Tree tree) {
		Collection<Treeitem> items = tree.getItems();
		for (Treeitem itemed : items)
			itemed.setOpen(true);
	}

	// 关闭所有标签
	@Command
	public void closeAllTab(@BindingParam("tbx1") Tabbox tbx1,
			@BindingParam("tig00") Tab tig00) {
		List list = tbx1.getTabs().getChildren();
		int num = list.size();
		for (int i = num - 1; i >= 0; i--) {
			String ggh = ((Tab) list.get(i)).getId();
			if (!ggh.equals("tig00") && !ggh.equals("tig_1")) {
				((Tab) list.get(i)).close();
			}
		}
		tig00.setSelected(true);
	}

	// 固定/取消固定标签
	@Command
	public void fixedtab(@BindingParam("tbx1") Tabbox tbx1,
			@BindingParam("fixedtab") Menuitem fixedtab) {
		List list = tbx1.getTabs().getChildren();
		int num = list.size();
		for (int i = num - 1; i >= 0; i--) {
			String ggh = ((Tab) list.get(i)).getId();
			if (!ggh.equals("tig00") && !ggh.equals("tig_1")) {
				if (((Tab) list.get(i)).isSelected()) {
					if (fixedtab.getLabel() == "固定标签"
							|| fixedtab.getLabel().equals("固定标签")) {
						String idstr = ((Tab) list.get(i)).getId();
						if (idstr != null && !idstr.equals("")) {
							String ids = idstr.trim().substring(3);
							int mid = Integer.parseInt(ids);
							bll.addFixedTabMenuList(username, mid);
							fixedtab.setLabel("取消固定标签");
						}
					} else {
						String idstr = ((Tab) list.get(i)).getId();
						if (idstr != null && !idstr.equals("")) {
							String ids = idstr.trim().substring(3);
							int mid = Integer.parseInt(ids);
							bll.deleteFixedTabMenuList(username, mid);
							fixedtab.setLabel("固定标签");
						}
					}
				}
			}

		}
	}

	// 关闭其他标签
	@Command
	public void closeOtherTab(@BindingParam("tbx1") Tabbox tbx1) {
		List list = tbx1.getTabs().getChildren();
		int num = list.size();
		for (int i = num - 1; i >= 0; i--) {
			String ggh = ((Tab) list.get(i)).getId();
			if (!ggh.equals("tig00") && !ggh.equals("tig_1")) {
				if (!((Tab) list.get(i)).isSelected()) {
					((Tab) list.get(i)).close();
				}
			}
		}
	}

	// 定义生成树子节点的方法
	@Command
	public void addTree(@BindingParam("tree") Tree tree,
			@BindingParam("onclicktree") Menupopup onclicktree,
			@BindingParam("tbx1") Tabbox tbx1,
			@BindingParam("fixedtab") Menuitem fixedtab,
			@BindingParam("tig_1") Tab tig_1, @BindingParam("tig00") Tab tig00) {
		// 调用bll层中getMenuPInfo()方法查询树最顶层的菜单
		List<MenuListModel> list = null;
		list = bll.getMenuListInfo();
		// 生成一个树的子节点
		Treechildren trc = new Treechildren();
		// 把生成的树的子节点添加到最原始的树上
		trc.setParent(tree);
		trc.setContext(onclicktree);
		trc.addEventListener("onClick", new TreeMyListener(tree, tbx1,
				fixedtab, tig_1, tig00));
		// 先判断list是否为空
		if (list.size() > 0) {
			// 循环获取List中的值
			for (int i = 0; i < list.size(); i++) {
				if (list.get(i).getMeu_pid() == 0) {
					// 获取list中的一组数据
					MenuListModel model = (MenuListModel) list.get(i);
					// 生成一个tree子节点newitem
					Treeitem newitem = new Treeitem();
					// 给newitem设置id
					newitem.setId("tid" + model.getMeu_id());
					// 设置newitem的默认打开方式是false，也就是树默认是不展开的
					newitem.setOpen(false);
					newitem.setLabel(model.getMeu_name().trim());
					// 把newitem添加到节点trc中,作为trc的一个子节点
					newitem.setParent(trc);
					// 调用AddTreeItem方法
					AddTreeItem(newitem, model.getMeu_id(), tig00, list, tree,
							tbx1, fixedtab, tig_1);
				}
			}
		}
	}

	// 加载固定标签信息
	@Command
	public void addFixedTab(@BindingParam("tbx1") Tabbox tbx1,
			@BindingParam("tig_1") Tab tig_1,
			@BindingParam("fixedtab") Menuitem fixedtab,
			@BindingParam("tig00") Tab tig00, @BindingParam("tree") Tree tree) {
		List<FixedTabMenuListModel> fixedtabinfo = bll
				.getFixedTabMenuList(username);
		if (!fixedtabinfo.isEmpty()) {
			for (int i = 0; i < fixedtabinfo.size(); i++) {
				List<MenuListModel> listk = bll.getMenuId(fixedtabinfo.get(i)
						.getMenu_id());
				if (!listk.isEmpty()) {
					MenuListModel mmodel = listk.get(0);
					if (mmodel.getMeu_url() == null
							|| mmodel.getMeu_url().equals("")) {
					} else {
						Tab newTab = new Tab(mmodel.getMeu_name());
						newTab.addEventListener("onClose", new TabMyListener(
								tbx1, fixedtab, tig00, tree));
						newTab.setClosable(true);
						newTab.setId("tig" + mmodel.getMeu_id());
						newTab.setImage("images/vs_aspx.png");
						Tabpanel newTabpanel = new Tabpanel();
						newTabpanel.setId("tip" + mmodel.getMeu_id());
						Iframe fm = new Iframe(mmodel.getMeu_url());
						newTabpanel.appendChild(fm);
						tbx1.getTabs().insertBefore(newTab, tig_1);
						newTabpanel.setParent(tbx1.getTabpanels());
					}
				}
			}
		}
	}

	// 定义递归函数AddTreeItem()
	private void AddTreeItem(Treeitem tem, int parentID, Tab tig00,
			List<MenuListModel> list, Tree tree, Tabbox tbx1,
			Menuitem fixedtab, Tab tig_1) {
		// 生成一个子节点
		Treechildren newchild = new Treechildren();
		// 生成事件监控类，监控Treechildren的点击事件
		newchild.addEventListener("onClick", new TreeMyListener(tree, tbx1,
				fixedtab, tig_1, tig00));
		for (int j = 0; j < list.size(); j++) {
			// 重新获取palist
			MenuListModel pmodel = list.get(j);
			if (pmodel.getMeu_pid() == parentID) {
				Treeitem item = new Treeitem();
				item.setId("tid" + pmodel.getMeu_id());
				item.setOpen(false);
				item.setParent(newchild);
				newchild.setParent(tem);
				Treerow tw = new Treerow();
				Treecell tl = new Treecell();
				tl.setLabel(pmodel.getMeu_name());
				tl.setParent(tw);
				tw.setParent(item);
				// 使用递归方法循环生成树节点菜单
				this.AddTreeItem(item, pmodel.getMeu_id(), tig00, list, tree,
						tbx1, fixedtab, tig_1);
			}
		}
		// }
	}

	// 定义点击树时生成tab标签的方法
	public void addTab(int tabid, Tree tree, Tabbox tbx1, Menuitem fixedtab,
			Tab tig_1, Tab tig00) {
		String selected = "tip0000";
		try {
			// 判断被选中的树的节点是否展开，不是的话就展开，是的话就关闭
			if (tree.getSelectedItem().isOpen()) {
				tree.getSelectedItem().setOpen(false);
			} else {
				tree.getSelectedItem().setOpen(true);
			}
			// 获取被选中的树节点
			selected = tree.getSelectedItem().getId();
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
		// 获取id为tbx1的tabbox的Tabs中的所有tab
		List list = tbx1.getTabs().getChildren();
		// 获取id为tbx1的tabbox的Tabpanels中的所有tabpanel
		List listp = tbx1.getTabpanels().getChildren();
		int id = 0;
		if (tabid == 0) {
			if (selected != null && !selected.equals("")) {
				id = Integer.parseInt(selected.trim().substring(3));
			}
		} else {
			id = 152;
		}

		List<MenuListModel> listk = bll.getMenuId(id);
		int k = 0;
		for (int j = 0; j < list.size(); j++) {
			String ggh = ((Component) list.get(j)).getId();
			if (ggh != null && !ggh.equals("")) {
				String idd = ggh.trim().substring(3);
				String ids = selected.trim().substring(3);
				if (idd.equals(ids)) {
					((Tab) list.get(j)).setSelected(true);
					int tabiid = Integer.parseInt(idd);
					FixedTabMenuListModel fmodel = bll.getFixedTabMenuListid(
							username, tabiid);
					if (fmodel.getId() == 0) {
						fixedtab.setVisible(true);
						fixedtab.setLabel("固定标签");
					} else {
						fixedtab.setVisible(true);
						fixedtab.setLabel("取消固定标签");
					}
					((Component) listp.get(j)).invalidate();
					k = 1;
				}
			}
		}
		if (listk.size() > 0) {
			MenuListModel model = (MenuListModel) listk.get(0);
			if (k == 0) {
				if (model.getMeu_url() == null || model.getMeu_url().equals("")) {
				} else {
					Tab newTab = new Tab(model.getMeu_name());
					newTab.setSelected(true);
					newTab.addEventListener("onClose", new TabMyListener(tbx1,
							fixedtab, tig00, tree));
					newTab.setClosable(true);
					newTab.setId("tig" + model.getMeu_id());
					// 查询是否已经固定了标签
					FixedTabMenuListModel fmodel = bll.getFixedTabMenuListid(
							username, model.getMeu_id());
					if (fmodel.getId() == 0) {
						fixedtab.setVisible(true);
						fixedtab.setLabel("固定标签");
					} else {
						fixedtab.setVisible(true);
						fixedtab.setLabel("取消固定标签");
					}
					newTab.setImage("images/vs_aspx.png");
					Tabpanel newTabpanel = new Tabpanel();
					newTabpanel.setId("tip" + model.getMeu_id());
					Iframe fm = new Iframe(model.getMeu_url());
					newTabpanel.appendChild(fm);
					tbx1.getTabs().insertBefore(newTab, tig_1);
					newTabpanel.setParent(tbx1.getTabpanels());
				}
			}
		}
	}

	class MyListener1 implements org.zkoss.zk.ui.event.EventListener {
		Tab tag00;

		public void onEvent(Event e) throws UiException {
			tag00.setSelected(true);
		}

		public MyListener1(Tab tag00) {
			this.tag00 = tag00;
		}
	}

	// 定义一个实现接口EventListener的类来监听tab关闭事件
	class TabMyListener implements org.zkoss.zk.ui.event.EventListener {
		Tabbox tbx1;
		Menuitem fixedtab;
		Tab tig00;
		Tree tree;

		public TabMyListener(Tabbox tbx1, Menuitem fixedtab, Tab tig00,
				Tree tree) {
			this.tbx1 = tbx1;
			this.fixedtab = fixedtab;
			this.tig00 = tig00;
			this.tree = tree;
		}

		public void onEvent(Event e) throws UiException {
			Tab tab = (Tab) e.getTarget();
			List list = tbx1.getTabs().getChildren();
			int num = list.size();
			if (num == 3) {
				fixedtab.setVisible(false);
			}
			for (int i = num - 1; i >= 0; i--) {
				String ggh = ((Tab) list.get(i)).getId();
				if (!ggh.equals("tig00") && !ggh.equals("tig_1")) {
					if (((Tab) list.get(i)).getId() == tab.getId()
							|| ((Tab) list.get(i)).getId().equals(tab.getId())) {
						if (i > 0) {
							if (tab.isSelected()) {
								((Tab) list.get(i - 1)).setSelected(true);
								String tabid = ((Tab) list.get(i - 1)).getId();
								if (tabid != null && !tabid.equals("")) {
									Collection<Treeitem> items = tree
											.getItems();
									for (Treeitem itemed : items) {
										if (tabid != null && !tabid.equals("")) {
											if (itemed.getId().substring(3) == tabid
													.substring(3)
													|| itemed
															.getId()
															.substring(3)
															.equals(tabid
																	.substring(3))) {
												itemed.setSelected(true);
											}
										}
									}
								}

							}
						} else {
							tig00.setSelected(true);
						}
					}
				}
			}
		}
	}

	// 菜单与任务单中心转换
	@Command("change")
	public void change(@BindingParam("fisheye") Fisheye fisheye,
			@BindingParam("tree") Tree tree, @BindingParam("gd") Grid gd,
			@BindingParam("fisheye1") Fisheye fisheye1) {
		if (fisheye.getId().equals("menu")) {
			tree.setVisible(true);
			gd.setVisible(false);
			fisheye.setImage("/images/menu_2.jpg");
			fisheye1.setImage("/images/rw_1.jpg");
		} else {
			tree.setVisible(false);
			gd.setVisible(true);
			fisheye.setImage("/images/rw_2.jpg");
			fisheye1.setImage("/images/menu_1.jpg");
		}
	}

	// 菜单与任务单中心转换
	@Command("change1")
	@NotifyChange({ "tlgroupmodel", "list" })
	public void change1(@BindingParam("fisheye") Fisheye fisheye,
			@BindingParam("tbx1") Tabbox tbx1, @BindingParam("tree") Tree tree,
			@BindingParam("gd") Div gdv, @BindingParam("hl") Hlayout hl,
			@BindingParam("title") Label title,
			@BindingParam("tig_1") Tab tig_1, @BindingParam("tig00") Tab tig00,
			@BindingParam("fixedtab") Menuitem fixedtab) throws SQLException {

		// 获取id为tbx1的tabbox的Tabs中的所有tab
		List list = tbx1.getTabs().getChildren();
		// 获取id为tbx1的tabbox的Tabpanels中的所有tabpanel
		List listp = tbx1.getTabpanels().getChildren();
		Integer k = 0;

		if (fisheye.getId().equals("menu")) {
			/*
			for (int j = 0; j < list.size(); j++) {
				String ggh = ((Component) list.get(j)).getId();
				if (ggh != null && !ggh.equals("")) {
					String idd = ggh.trim().substring(3);
					if (!idd.equals("152") && idd.equals(nowtabid)) {
						((Tab) list.get(j)).setSelected(true);
					}
					if (((Tab) list.get(j)).isSelected() && !idd.equals("152")) {
						nowtabid = idd;
					}
				}
			}*/
			hl.setVisible(true);
			tree.setVisible(true);
			gdv.setVisible(false);

			title.setValue("菜单");
		} else {
			tree.setVisible(false);
			hl.setVisible(false);
			gdv.setVisible(true);
			title.setValue("任务单中心");

			for (int j = 0; j < list.size(); j++) {
				String ggh = ((Component) list.get(j)).getId();
				if (ggh != null && !ggh.equals("")) {
					String idd = ggh.trim().substring(3);
					if (((Tab) list.get(j)).isSelected() && !idd.equals("152")) {
						nowtabid = idd;
					}
					if (idd.equals("152")) {
						((Tab) list.get(j)).setSelected(true);
						((Component) listp.get(j)).invalidate();
						k = 1;
						// 设置固定标签的按钮为可见
						fixedtab.setVisible(true);
						// 根据id和用户名查询该标签是否已经添加了固定标签
						FixedTabMenuListModel fmodel = bll
								.getFixedTabMenuListid(username, 152);
						if (fmodel.getId() == 0) {
							fixedtab.setLabel("固定标签");
						} else {
							fixedtab.setLabel("取消固定标签");
						}
					}
				}
			}

			if (k == 0) {
				List<MenuListModel> listk = bll.getMenuId(152);
				MenuListModel model = (MenuListModel) listk.get(0);
				Tab newTab = new Tab(model.getMeu_name());
				newTab.setSelected(true);
				newTab.addEventListener("onClose", new MyListener1(tig00));
				newTab.setClosable(true);
				newTab.setId("tig" + model.getMeu_id());
				newTab.setImage("images/vs_aspx.png");
				Tabpanel newTabpanel = new Tabpanel();
				newTabpanel.setId("tip" + model.getMeu_id());
				Iframe fm = new Iframe("/Taskflow/Task_ContentList.zul?id=0");
				newTabpanel.appendChild(fm);
				tbx1.getTabs().insertBefore(newTab, tig_1);
				newTabpanel.setParent(tbx1.getTabpanels());

				// 设置固定标签的按钮为可见
				fixedtab.setVisible(true);
				// 根据id和用户名查询该标签是否已经添加了固定标签
				FixedTabMenuListModel fmodel = bll.getFixedTabMenuListid(
						username, 152);
				if (fmodel.getId() == 0) {
					fixedtab.setLabel("固定标签");
				} else {
					fixedtab.setLabel("取消固定标签");
				}
			}
			init();
		}
		
	}

	/**************************** 在页面打开标签 ****************************/
	private void indexAddTab(String newurl, String tabtext) {
		Tabbox tbx1 = (Tabbox) win.getFellow("tbx1");
		Tab tig_1 = (Tab) win.getFellow("tig_1");
		Menuitem fixedtab = (Menuitem) win.getFellow("fixedtab");
		Tab tig00 = (Tab) win.getFellow("tig00");
		Tree tree = (Tree) win.getFellow("tree");
		String selected = "tip0000";
		// 获取id为tbx1的tabbox的Tabs中的所有tab
		List list = tbx1.getTabs().getChildren();
		// 获取id为tbx1的tabbox的Tabpanels中的所有tabpanel
		List listp = tbx1.getTabpanels().getChildren();
		int k=0;
		for (int j = 0; j < list.size(); j++) {
			Tab tab = ((Tab) list.get(j));
			String ggh = tab.getLabel();
			if (ggh != null &&ggh.equals(tabtext)) {
				((Tab) list.get(j)).setSelected(true);
				k=1;
			}
		}
		
		if (k==0&&newurl != null && !newurl.equals("")) {
			Tab newTab = new Tab(tabtext);
			newTab.setSelected(true);
			newTab.addEventListener("onClose", new TabMyListener(tbx1,
					fixedtab, tig00, tree));
			newTab.setClosable(true);
			newTab.setImage("images/vs_aspx.png");
			Tabpanel newTabpanel = new Tabpanel();
			Iframe fm = new Iframe(newurl);
			newTabpanel.appendChild(fm);
			tbx1.getTabs().insertBefore(newTab, tig_1);
			newTabpanel.setParent(tbx1.getTabpanels());
		}
	}

	public TaskListGroupModel getTlgroupmodel() {
		return tlgroupmodel;
	}

	public void setTlgroupmodel(TaskListGroupModel tlgroupmodel) {
		this.tlgroupmodel = tlgroupmodel;
	}

	@Command("getnowtime")
	@NotifyChange("nowtime")
	public void getnowtime() {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
		nowtime = sdf.format(new Date());
	}

	public String getNowtime() {
		return nowtime;
	}

	public void setNowtime(String nowtime) {
		this.nowtime = nowtime;
	}

	public String getIndexsrc() {
		return indexsrc;
	}

	public void setIndexsrc(String indexsrc) {
		this.indexsrc = indexsrc;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}
}
