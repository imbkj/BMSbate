package Controller.SystemControl;

import java.util.ArrayList;
import java.util.List;

import org.zkoss.bind.annotation.BindingParam;
import org.zkoss.bind.annotation.Command;
import org.zkoss.bind.annotation.GlobalCommand;
import org.zkoss.bind.annotation.NotifyChange;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.Path;
import org.zkoss.zk.ui.UiException;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zul.Include;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Tree;
import org.zkoss.zul.Treecell;
import org.zkoss.zul.Treechildren;
import org.zkoss.zul.Treeitem;
import org.zkoss.zul.Treerow;
import org.zkoss.zul.Window;

import bll.CoCompact.BaseInfo_SelectListBll;
import bll.EmBodyCheck.EmBcInfo_SelectBll;
import bll.SystemControl.EmBuCenter_SelectBll;

import Model.CoAgencyBaseModel;
import Model.CoCompactModel;
import Model.EmbaseBusinessCenterModel;
import Model.EmbaseModel;
import Util.UserInfo;

public class EmBuCenterInfoListController {
	private Integer gid = 0;
	private Integer cid = 0;
	private Integer embaId = 0;
	// 当taprid不为空的时候就表示是在任务单中心打开业务中心，daid表示TaskBatch表的id,如果taprid为空则表示是在员工列表中打开业务中心，此时daid是从
	// 员工列表中传过来的gid
	private Integer daid = 0;
	// 从任务单中心传过来的任务单id
	private Integer taprid = 0;

	// 业务中心菜单查询的bll
	EmBuCenter_SelectBll bll = new EmBuCenter_SelectBll();
	// lists用于递归函数的时候存放数据（用于暂时存放）
	private List<EmbaseBusinessCenterModel> lists = new ArrayList<EmbaseBusinessCenterModel>();
	// 生成树的时候使用的菜单的list
	private List<EmbaseBusinessCenterModel> pubtreelist = new ArrayList<EmbaseBusinessCenterModel>();
	private String s = "";
	private Object did = Executions.getCurrent().getArg().get("daid");
	private Object tid = Executions.getCurrent().getArg().get("id");
	private EmBcInfo_SelectBll selectbll = new EmBcInfo_SelectBll();
	private EmbaseModel emmodel = new EmbaseModel();
	private Window win = (Window) Path.getComponent("/centerwin");
	private CoCompactModel cocomodel = new CoCompactModel();
	private CoAgencyBaseModel coabM = new CoAgencyBaseModel();

	// 构造函数
	public EmBuCenterInfoListController() {
		if (did != null) {
			daid = Integer.valueOf(did.toString());
		}

		if (tid != null) {
			taprid = Integer.valueOf(tid.toString());
		}

		// 当taprid不为空的时候就表示是从任务单中心打开页面
		if (taprid != null && taprid > 0) {
			// 根据穿过来的TaskBatch表的id查询员工的gid
			if (daid != null) {
				gid = bll.getgid(daid);
				embaId = bll.getEmbaseList(gid).get(0).getEmba_id();
			} else {
				s = "系统出错，请联系开发人员";
			}
		} else {
			// taprid不为空的时候表示是从员工列表传过来的gid
			gid = daid;
			embaId = (Integer) Executions.getCurrent().getArg().get("embaId");
		}

		List<EmbaseModel> embaselist = selectbll.getEmBaseInfo(" and gid="
				+ gid);
		if (!embaselist.isEmpty()) {
			emmodel = embaselist.get(0);
			cid = emmodel.getCid();
		}
		cocomodel = bll.getEmBaseCompact(gid);

		BaseInfo_SelectListBll cocoBll = new BaseInfo_SelectListBll();
		if (cocomodel.getCoco_id() != null) {
			coabM = cocoBll.getAgencyInfo(cocomodel.getCoco_id());
		}
	}

	// 树的onCreate事件，用于生成树的时候生成树节点
	@Command
	public void oncreatetree(@BindingParam("tree") Tree tree,
			@BindingParam("refleshurl") Include refleshurl) {
		// 菜单的查询条件
		String strs = "";
		strs = strs + " and emce_id in(select embu_id from EmbuGroupRel ";
		strs = strs
				+ " where rol_id in(select a.rol_id from logingroup a,Login b "
				+ "where a.log_id=b.log_id and log_name='"
				+ UserInfo.getUsername() + "'))";
		// 把操作者的菜单权限的所有菜单查出来
		pubtreelist = null;
		// List<EmbaseBusinessCenterModel> treelists=null;
		// pubtreelist = bll.getEmbaseBusinessCenterInfo(strs);
		// treelists=pubtreelist;
		// taprid为不空就表示是在任务单打开业务中心
		String strid = "";
		if (taprid != null && taprid > 0) {
			if (gid != null && gid > 0) {
				// 查询EmOnBoardList表获取索要打开的菜单id
				List<EmbaseBusinessCenterModel> olist = bll
						.getEmOnBoardList(gid);
				if (olist.size() > 0) {
					for (int j = 0; j < olist.size(); j++) {
						if (j == 0) {
							strid = olist.get(j).getEmce_id() + "";
						} else {
							strid = strid + "," + olist.get(j).getEmce_id()
									+ "";
						}
					}
				}
			} else {
				s = "系统出错，请联系开发人员";
			}
		}
		if (s == "") {
			if (strid.equals("") || strid == "") {
				pubtreelist = bll.getEmbaseBusinessCenterInfo(strs
						+ " and emce_must<>0");
			} else {
				// 根据EmOnBoardList的菜单id查询
				pubtreelist = bll.getEmbaseBusinessCenterInfo(strs
						+ " and emce_must<>0  and (emce_id in(" + strid
						+ ") or emce_id in(select emce_pid from "
						+ "EmbaseBusinessCenter where emce_id in(" + strid
						+ ")))");
			}
			// 如果pubtreelist不等于空则开始生成树的子节点
			if (!pubtreelist.isEmpty()) {
				Treechildren trc = new Treechildren();
				// 生成事件监控类，监控Treechildren的点击事件
				trc.addEventListener("onClick", new MyListener(trc, refleshurl,
						tree));
				trc.setParent(tree);
				for (int y = 0; y < pubtreelist.size(); y++) {
					EmbaseBusinessCenterModel model = pubtreelist.get(y);
					if (model.getEmce_pid() == 0) {
						// 生成一个树的子节点
						Treeitem newitem = new Treeitem();
						newitem.setParent(trc);
						newitem.setOpen(false);
						newitem.setLabel(model.getEmce_menuname());
						newitem.setId("id" + model.getEmce_id());
						newitem.setValue(model.getEmce_menuurl());
						// newitem.addEventListener("onClose", new
						// MyListener());
						// 调用递归函数
						AddTreeItem(newitem, model.getEmce_id(), pubtreelist,
								refleshurl, tree);
					}
				}
			}
		} else {
			Messagebox.show(s, "提示", Messagebox.OK, Messagebox.ERROR);
		}
	}

	// 定义递归函数AddTreeItem()
	private void AddTreeItem(Treeitem ptem, int parentID,
			List<EmbaseBusinessCenterModel> list, Include refleshurl, Tree tree) {
		Treechildren newchild = new Treechildren();
		// 生成事件监控类，监控Treechildren的点击事件
		newchild.addEventListener("onClick", new MyListener(newchild,
				refleshurl, tree));
		int h = 0;
		for (int i = 0; i < list.size(); i++) {
			EmbaseBusinessCenterModel model = list.get(i);
			if (model.getEmce_pid() == parentID) {
				newchild.setParent(ptem);
				Treeitem item = new Treeitem();
				item.setOpen(false);
				item.setParent(newchild);
				item.setId("id" + model.getEmce_id());
				item.setValue(model.getEmce_menuurl());
				Treerow tw = new Treerow();
				Treecell tl = new Treecell();
				tl.setLabel(model.getEmce_menuname());
				tl.setParent(tw);
				tw.setParent(item);
				// 函数递归
				AddTreeItem(item, model.getEmce_id(), list, refleshurl, tree);
			}
		}
	}

	// Treechildren的点击事件监控类
	class MyListener implements org.zkoss.zk.ui.event.EventListener {
		Treechildren trc = null;
		Tree tree = null;
		Include refleshurl = null;

		public void onEvent(Event e) throws UiException {
			Treeitem item = tree.getSelectedItem();
			if (item != null) {
				if (item.isOpen()) {
					item.setOpen(false);
				} else {
					item.setOpen(true);
				}
			}
			if (item.getValue() != null && !item.getValue().equals("")
					&& item.getValue() != "") {
				refleshurl.setSrc("");
				if (item.getValue() != null) {
					refleshurl.setSrc(item.getValue() + "");
				}
			}
		}

		// 类MyListener的构造函数
		public MyListener(Treechildren trc, Include refleshurl, Tree tree) {
			this.trc = trc;
			this.refleshurl = refleshurl;
			this.tree = tree;
		}
	}

	@GlobalCommand("refreshEmUrl")
	public void refreshEmUrl(@BindingParam("url") String newurl) {
		try {
			Include refleshurl = (Include) win.getFellow("refleshurl");
			if (refleshurl != null) {
				refleshurl.setSrc("");
				refleshurl.setSrc(newurl);
			} else {
				win.detach();
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public Integer getGid() {
		return gid;
	}

	public void setGid(Integer gid) {
		this.gid = gid;
	}

	public Integer getCid() {
		return cid;
	}

	public void setCid(Integer cid) {
		this.cid = cid;
	}

	public Integer getEmbaId() {
		return embaId;
	}

	public void setEmbaId(Integer embaId) {
		this.embaId = embaId;
	}

	public EmbaseModel getEmmodel() {
		return emmodel;
	}

	public void setEmmodel(EmbaseModel emmodel) {
		this.emmodel = emmodel;
	}

	public Window getWin() {
		return win;
	}

	public void setWin(Window win) {
		this.win = win;
	}

	// list1 是所有的菜單，list2是任務單傳過來的菜單
	private void getlists(List<EmbaseBusinessCenterModel> list1,
			List<EmbaseBusinessCenterModel> list2) {
		if (!lists.isEmpty())
			lists.clear();
		for (int i = 0; i < list2.size(); i++) {
			getlists2(list2.get(i).getEmce_id(), list1);
		}
	}

	// 递归函数，用于把传进来的菜单id的父菜单全部找出来
	private void getlists2(int id, List<EmbaseBusinessCenterModel> list) {
		for (int j = 0; j < list.size(); j++) {
			if (id == list.get(j).getEmce_id()) {
				lists.add(list.get(j));
				if (list.get(j).getEmce_pid() != 0) {
					getlists2(list.get(j).getEmce_pid(), list);
				}
			}
		}
	}

	public CoCompactModel getCocomodel() {
		return cocomodel;
	}

	public void setCocomodel(CoCompactModel cocomodel) {
		this.cocomodel = cocomodel;
	}

	public CoAgencyBaseModel getCoabM() {
		return coabM;
	}

	public void setCoabM(CoAgencyBaseModel coabM) {
		this.coabM = coabM;
	}
	
}
