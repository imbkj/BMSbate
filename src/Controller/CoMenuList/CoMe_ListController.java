package Controller.CoMenuList;

import java.util.List;

import org.zkoss.bind.annotation.BindingParam;
import org.zkoss.bind.annotation.Command;
import org.zkoss.bind.annotation.GlobalCommand;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.Path;
import org.zkoss.zk.ui.UiException;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zul.Include;
import org.zkoss.zul.Tree;
import org.zkoss.zul.Treecell;
import org.zkoss.zul.Treechildren;
import org.zkoss.zul.Treeitem;
import org.zkoss.zul.Treerow;
import org.zkoss.zul.Window;

import Model.CoAgencyBaseModel;
import Model.CoBaseModel;
import Model.CoCompactModel;
import Model.CobaseMenulistModel;
import Util.UserInfo;
import bll.CoBase.CoBase_SelectBll;
import bll.CoCompact.BaseInfo_SelectListBll;
import bll.CoMenuList.Come_SelectBll;

public class CoMe_ListController {
	private Come_SelectBll bll = new Come_SelectBll();
	private String sql = " and come_id in(select reml_come_id from CobaseMenuListRel "
			+ " where reml_role_id in(select a.rol_id from role a inner join logingroup b on a.rol_id=b.rol_id "
			+ " inner join login c on b.log_id=c.log_id where log_name='"
			+ UserInfo.getUsername() + "'))";
	private List<CobaseMenulistModel> list = bll.getEmbaseMenuListInfo(sql);
	private Integer cid = (Integer) Executions.getCurrent().getArg().get("cid");
	private CoBaseModel model = (CoBaseModel) Executions.getCurrent().getArg()
			.get("model");
	private Window win = (Window) Path.getComponent("/cocenterwin");
	private CoCompactModel cocomodel = new CoCompactModel();
	private CoAgencyBaseModel coabM = new CoAgencyBaseModel();
	
	public CoMe_ListController()
	{
		CoBase_SelectBll cobill=new CoBase_SelectBll();
		List<CoCompactModel> colt=cobill.getcompactLists(model.getCid());
		if(colt.size()>0)
		{
			cocomodel=colt.get(0);
		}
		BaseInfo_SelectListBll cocoBll = new BaseInfo_SelectListBll();
		if (cocomodel.getCoco_id() != null) {
			coabM = cocoBll.getAgencyInfo(cocomodel.getCoco_id());
		}
	}

	// 树的onCreate事件，用于生成树的时候生成树节点
	@Command
	public void oncreatetree(@BindingParam("tree") Tree tree,
			@BindingParam("refleshurl") Include refleshurl) {
		// 如果list不等于空则开始生成树的子节点
		if (!list.isEmpty()) {
			Treechildren trc = new Treechildren();
			// 生成事件监控类，监控Treechildren的点击事件
			trc.addEventListener("onClick", new MyListener(trc, refleshurl,
					tree));
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
					AddTreeItem(newitem, model.getCome_id(), list, refleshurl,
							tree);
				}
			}
		}
	}

	// 定义递归函数AddTreeItem()
	private void AddTreeItem(Treeitem ptem, int parentID,
			List<CobaseMenulistModel> list, Include refleshurl, Tree tree) {
		Treechildren newchild = new Treechildren();
		// 生成事件监控类，监控Treechildren的点击事件
		newchild.addEventListener("onClick", new MyListener(newchild,
				refleshurl, tree));
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
				AddTreeItem(item, model.getCome_id(), list, refleshurl, tree);
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
					Executions.getCurrent().setAttribute("name",
							item.getLabel());
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

	@GlobalCommand("refreshCoUrl")
	public void refreshCoUrl(@BindingParam("url") String newurl) {

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

	public Integer getCid() {
		return cid;
	}

	public void setCid(Integer cid) {
		this.cid = cid;
	}

	public CoBaseModel getModel() {
		return model;
	}

	public void setModel(CoBaseModel model) {
		this.model = model;
	}

	public CoAgencyBaseModel getCoabM() {
		return coabM;
	}

	public void setCoabM(CoAgencyBaseModel coabM) {
		this.coabM = coabM;
	}
	
}
