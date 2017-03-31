package Controller.CoBase;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.zkoss.bind.BindUtils;
import org.zkoss.bind.annotation.BindingParam;
import org.zkoss.bind.annotation.Command;
import org.zkoss.bind.annotation.NotifyChange;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.UiException;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zul.Combobox;
import org.zkoss.zul.Grid;
import org.zkoss.zul.ListModel;
import org.zkoss.zul.ListModelList;
import org.zkoss.zul.Menupopup;
import org.zkoss.zul.Tree;
import org.zkoss.zul.Treecell;
import org.zkoss.zul.Treechildren;
import org.zkoss.zul.Treeitem;
import org.zkoss.zul.Treerow;
import org.zkoss.zul.Window;

import Controller.CoBase.CoBase_infoeditListControllers.MyListener;
import Model.CoBaseModel;
import Model.DepartmentModel;
import Model.LoginModel;
import Util.UserInfo;
import bll.CoBase.CoBase_SelectBll;
import bll.CoBase.PubTree_OperateBll;
import bll.CoLatencyClient.CoLatencyClient_AddBll;

public class CoBase_InfoListforallnewController {
	private String str = "";
	private String strs = "";
	private String strwhere = "";
	ListModel<String> loginlist;
	PubTree_OperateBll bll = new PubTree_OperateBll();
	CoBase_SelectBll sebll = new CoBase_SelectBll();
	private List<CoBaseModel> cobaselist = new ArrayList<CoBaseModel>();

	public CoBase_InfoListforallnewController() {
		str = " and  1=1";
		cobaselist = new ListModelList<CoBaseModel>(sebll.getCobaseinfoforall(str));
		CoLatencyClient_AddBll bll2 = new CoLatencyClient_AddBll();
		loginlist = new ListModelList<String>(bll2.getLoginInfo());
		
		
	}

	// 生成树
	@Command("oncreatetree")
	public void addTree(@BindingParam("tree") Tree tree,
			@BindingParam("onclicktree") Menupopup onclicktree) {
		List<DepartmentModel> pubtreelist = bll.getDepartmentInfo();
		List<LoginModel> lolist = bll.getLoginInfo();
		if (!pubtreelist.isEmpty()) {
			Treechildren trc = new Treechildren();
			// 生成事件监控类，监控Treechildren的点击事件
			trc.addEventListener("onClick", new MyListener(trc, tree));
			trc.setParent(tree);
			//trc.setContext(onclicktree);
			for (int y = 0; y < pubtreelist.size(); y++) {
				DepartmentModel model = pubtreelist.get(y);
				// 生成一个树的子节点
				Treeitem newitem = new Treeitem();
				newitem.setParent(trc);
				newitem.setOpen(false);
				newitem.setLabel(model.getDep_name());
				Treechildren newchild = new Treechildren();
				for (LoginModel lom :lolist) {
					if (lom.getLog_pid() ==5000
							&& lom.getDep_id() == model.getDep_id()) {
						
						newchild.setParent(newitem);
						Treeitem maitem = new Treeitem();
						maitem.setParent(newchild);
						maitem.setOpen(false);
						maitem.setLabel(lom.getLog_name());
						AddTreeItem(maitem, lom.getLog_id(), lolist, tree);
					}
				}
			}
		}
	}

	// 定义递归函数AddTreeItem()
	private void AddTreeItem(Treeitem ptem, int parentID,
			List<LoginModel> list, Tree tree) {
		Treechildren newchild = new Treechildren();
		// 生成事件监控类，监控Treechildren的点击事件
		newchild.addEventListener("onClick", new MyListener(newchild, tree));
		int h = 0;
		for (int i = 0; i < list.size(); i++) {
			LoginModel model = list.get(i);
			if (model.getLog_pid() == parentID) {
				newchild.setParent(ptem);
				Treeitem item = new Treeitem();
				item.setOpen(false);
				item.setParent(newchild);
				Treerow tw = new Treerow();
				Treecell tl = new Treecell();
				tl.setLabel(model.getLog_name());
				tl.setParent(tw);
				tw.setParent(item);
				// 函数递归
				AddTreeItem(item, model.getLog_id(), list, tree);
			}
		}
	}
	
	//弹出分配页面
	@Command
	public void allocation()
	{
		
	}

	// 弹出公司详细信息页面
	public void cobasedetial(String str) {
		if (str != "" && !str.equals("") && str != null) {
			Map cidmap = new HashMap();
			cidmap.put("cid", str);
			Window window = (Window) Executions.createComponents(
					"CoBase_DetailInfo.zul", null, cidmap);
			window.doModal();
		}
	}

	public void opcooffer(String cid) {
		if (cid != null && !cid.isEmpty()) {
			Map map = new HashMap();
			map.put("cid", cid);
			Window window = (Window) Executions.createComponents(
					"/CoBase/Cobase_allinone.zul", null, map);
			window.doModal();
		}
	}

	// 弹出公司各个操作页面
	public void openZUL(int id, String cid, Grid cobaseinfo) {
		// 传递cobaM
		String url = "";
		String type = "";// 社保公积金转移业务
		if (id == 1) {
			url = "CoBaseInfo_Update.zul";
		} else if (id == 2) {
			url = "CoBaseCS_Update.zul";
		} else if (id == 3) {
			url = "CoBaseReg_Update.zul";
		} else if (id == 4) {
			url = "/EmCommissionOut/Standard/Standard_Add.zul";
		} else if (id == 5) {
			url = "/Embase/EmQuotationMod.zul";
		} else if (id == 6) {
			type = "社会保险服务";
			url = "EmBase_MoveAccount.zul";
		} else if (id == 7) {
			type = "住房公积金服务";
			url = "EmBase_MoveAccount.zul";
		} else if (id == 8) {
			url = "/CoCompact/EmBase_CompactAllot.zul";
		}

		CoBaseModel cobaM = new CoBaseModel();
		List<CoBaseModel> mo = sebll.getCobaseinfoforall(" and a.cid=" + cid);
		if (!mo.isEmpty()) {
			cobaM = mo.get(0);
		}
		Map map = new HashMap();
		map.put("model", cobaM);
		map.put("type", type);
		Window window = (Window) Executions.createComponents(url, null, map);
		window.doModal();
		if (!strs.equals("") && strs != "") {
			cobaselist = new ListModelList<CoBaseModel>(
					sebll.getCobaseinfoforall(strs));
		}
		{
			cobaselist = new ListModelList<CoBaseModel>(
					sebll.getCobaseinfoforall(" and a.cid=" + cid));
		}
	}

	// 新增委托出标准
	public void EmCommissionOut(String cid) {
		String url = "";
		url = "/EmCommissionOut/Standard/Wtstandard_add.zul";

		Map<String, Object> map = new HashMap<>();
		map.put("cid", cid);
		Window window = (Window) Executions.createComponents(url, null, map);
		window.doModal();
	}

	// 新增委托出服务费
	public void EmCommissionOutfee(String cid) {
		String url = "";
		url = "/EmCommissionOut/Standard/Wtfee_add.zul";

		Map<String, Object> map = new HashMap<>();
		map.put("cid", cid);
		Window window = (Window) Executions.createComponents(url, null, map);
		window.doModal();
	}

	// 联系人管理
	public void openLinkMan_CtrlList(String cid) {
		Map<String, String> map = new HashMap<String, String>();
		map.put("cid", cid);
		Window window = (Window) Executions.createComponents(
				"CoBaseLinkMan_List.zul", null, map);
		window.doModal();
	}

	// 员工列表
	public void openEmbaseList(String cid) {
		Map<String, String> map = new HashMap<String, String>();
		map.put("cid", cid);
		Window window = (Window) Executions.createComponents("EmBaseList.zul",
				null, map);
		window.doModal();
	}

	// 新增合同信息
	public void AddCompany(String cid) {
		// 专递cobaM
		Map map = new HashMap();
		map.put("cid", cid);
		Window window = (Window) Executions.createComponents(
				"CoCompact_CompanyAdd.zul", null, map);
		window.doModal();
	}

	// 新增合同信息模板
	public void AddCompact(String cid) {
		// 专递cobaM
		Map map = new HashMap();
		map.put("cid", cid);
		Window window = (Window) Executions.createComponents(
				"../EmBaseCompact/EmBaseCompact_Upload.zul", null, map);
		window.doModal();
	}

	@Command
	public void openmemulist(@BindingParam("model") CoBaseModel cobaM) {
		Map map = new HashMap();
		map.put("cid", cobaM.getCid());
		map.put("model", cobaM);
		Window window = (Window) Executions.createComponents(
				"../CoMenuList/CoMe_List.zul", null, map);
		window.doModal();
	}

	private void recobase(List<CoBaseModel> mo) {
		cobaselist = new ListModelList<CoBaseModel>(mo);
		BindUtils.postNotifyChange(null, null, this, "cobaselist");
	}

	// Treechildren的点击事件监控类
	class MyListener implements org.zkoss.zk.ui.event.EventListener {
		Treechildren trc = null;
		Tree tree = null;

		// 类MyListener的构造函数
		public MyListener(Treechildren trc, Tree tree) {
			this.trc = trc;
			this.tree = tree;
		}

		public void onEvent(Event e) throws UiException {
			Treeitem item = tree.getSelectedItem();
			if (item != null) {
				if (item.isOpen()) {
					item.setOpen(false);
				} else {
					item.setOpen(true);
				}
				String clientname = tree.getSelectedItem().getLabel();
				str = " and coba_client='" + clientname + "'";
				strs = "";
				strs = str;
				List<CoBaseModel> mo = sebll.getCobaseinfoforall(str);
				if (mo.size() <= 0) {
					str = " and coba_client in(select log_name from Login a,department b "
							+ "where a.dep_id=b.dep_id and b.dep_name='"
							+ tree.getSelectedItem().getLabel() + "')";
					mo = sebll.getCobaseinfoforall(str);
				}
				recobase(mo);
			}
		}
	}

	// 公司信息查询页面
	@Command
	@NotifyChange({ "cobaseinfo", "cobaselist" })
	public void search(@BindingParam("combo") Combobox combo) {
		String stre = combo.getValue();
		// System.out.println(combo.getValue());
		String strwhere = "";
		try {
			if (stre.indexOf("|") != -1) {
				strwhere = stre.split("\\|")[1];
				List<CoBaseModel> mo = sebll.getCobaseinfoforall(strwhere);
				cobaselist = new ListModelList<CoBaseModel>(mo);
			} else {

				List<CoBaseModel> mo = sebll.getCobaseinfoforall(stre);
				cobaselist = new ListModelList<CoBaseModel>(mo);
			}
		} catch (Exception e) {
			System.out.println(e.toString());
		}
	}

	// 新增员工
	@Command("embasein")
	public void embasein(int a, CoBaseModel cobaM) {
		Map<String, Object> map = new HashMap<String, Object>();
		String url = "";

		// 预增
		if (a == 1) {
			url = "Embase_Addfst.zul";
			map.put("cid", cobaM.getCid());
			map.put("company", cobaM.getCoba_company());
			Window window = new Window();
			window.detach();
			window = (Window) Executions.createComponents(url, null, map);
			window.doModal();
		}
		// 新增
		else if (a == 2) {
			url = "Embase_Addsec.zul";
			map.put("cid", cobaM.getCid());
			Window window = (Window) Executions
					.createComponents(url, null, map);
			window.doModal();
		} else if (a == 3) {
			url = "Embase_Add.zul";
			map.put("cobaM", cobaM);
			// Window window = (Window) Executions
			// .createComponents(url, null, map);
			Window window = (Window) Executions.createComponents(
					"Embase_Add.zul", null, map);

			window.doModal();
		}
	}

	// 弹出服务约束
	public void addpromise(String url, String cid) {
		Map map = new HashMap<>();
		map.put("cid", cid);
		Window window = (Window) Executions.createComponents(url, null, map);
		window.doModal();
	}

	// 接收选择联系人的页面返回的联系人信息
	public void getLinkinfo() {

	}

	// 弹出公司详细信息页面
	@Command
	public void cobasedetials(@BindingParam("model") CoBaseModel cml) {
		Map cidmap = new HashMap();
		cidmap.put("cid", cml.getCid() + "");
		Window window = (Window) Executions.createComponents(
				"CoBase_DetailInfoshow.zul", null, cidmap);
		window.doModal();
	}

	public String getStrwhere() {
		return strwhere;
	}

	public void setStrwhere(String strwhere) {
		this.strwhere = strwhere;
	}

	public List<CoBaseModel> getCobaselist() {
		return cobaselist;
	}

	public void setCobaselist(List<CoBaseModel> cobaselist) {
		this.cobaselist = cobaselist;
	}
}
