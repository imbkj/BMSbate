package Controller.CoBase;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.zkoss.bind.annotation.BindingParam;
import org.zkoss.bind.annotation.Command;
import org.zkoss.bind.annotation.ContextParam;
import org.zkoss.bind.annotation.ContextType;
import org.zkoss.bind.annotation.NotifyChange;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.select.SelectorComposer;
import org.zkoss.zk.ui.select.annotation.Listen;
import org.zkoss.zk.ui.select.annotation.Wire;
import org.zkoss.zul.Combobox;
import org.zkoss.zul.Grid;
import org.zkoss.zul.ListModel;
import org.zkoss.zul.ListModelList;
import org.zkoss.zul.SimpleListModel;
import org.zkoss.zul.Textbox;
import org.zkoss.zul.Tree;
import org.zkoss.zul.Treecell;
import org.zkoss.zul.Treechildren;
import org.zkoss.zul.Treeitem;
import org.zkoss.zul.Treerow;
import org.zkoss.zul.Window;

import bll.CoBase.CoBase_SelectBll;
import bll.CoBase.PubTree_OperateBll;
import bll.CoLatencyClient.CoLatencyClient_AddBll;

import Model.CoBaseModel;
import Model.pubTreeModel;
import Util.UserInfo;

public class CoBase_InfoListforallController extends SelectorComposer<Component> {
	@Wire
	private Tree tree;
	@Wire
	private Combobox client;
	@Wire
	private Combobox combo;
	@Wire
	private Textbox companyname;
	@Wire
	private Textbox cid;
	@Wire
	private Grid cobaseinfo;
	private String str = "";
	private String strs = "";
	private String strwhere = "";
	ListModel<String> loginlist;
	PubTree_OperateBll bll = new PubTree_OperateBll();
	CoBase_SelectBll sebll = new CoBase_SelectBll();
	ListModel<CoBaseModel> cobaselist;

	public CoBase_InfoListforallController() {
	}

	// 重写组件初始化方法
	public void doAfterCompose(Component comp) throws Exception {
		// TODO Auto-generated method stub
		super.doAfterCompose(comp);
		addTree();
		str = " and  1=1";
		// str="and a.cid=1184";
		cobaselist = new ListModelList<CoBaseModel>(sebll.getCobaseinfoforall(str));
		cobaseinfo.setModel(cobaselist);
		CoLatencyClient_AddBll bll2 = new CoLatencyClient_AddBll();
		loginlist = new ListModelList<String>(bll2.getLoginInfo());
		// client.setModel(loginlist);

	}

	// 生成树
	private void addTree() {

		List<pubTreeModel> pubtreelist = bll.getPubTreeAllInfo();
		if (!pubtreelist.isEmpty()) {
			Treechildren trc = new Treechildren();
			trc.setParent(tree);
			for (int y = 0; y < pubtreelist.size(); y++) {
				pubTreeModel model = pubtreelist.get(y);
				if (model.getPid() == 0) {
					// 生成一个树的子节点
					Treeitem newitem = new Treeitem();
					newitem.setParent(trc);
					newitem.setOpen(false);
					newitem.setLabel(model.getName());
					newitem.setId("id" + model.getId());
					// 调用递归函数
					AddTreeItem(newitem, model.getId(), pubtreelist);
				}
			}
		}
	}

	// 定义递归函数AddTreeItem()
	private void AddTreeItem(Treeitem ptem, int parentID,
			List<pubTreeModel> list) {
		Treechildren newchild = new Treechildren();
		int h = 0;
		for (int i = 0; i < list.size(); i++) {
			pubTreeModel model = list.get(i);
			if (model.getPid() == parentID) {
				newchild.setParent(ptem);
				Treeitem item = new Treeitem();
				item.setOpen(false);
				item.setParent(newchild);
				item.setId("id" + model.getId());
				Treerow tw = new Treerow();
				Treecell tl = new Treecell();
				tl.setLabel(model.getName());
				tl.setParent(tw);
				tw.setParent(item);
				// 函数递归
				AddTreeItem(item, model.getId(), list);
			}
		}
	}

	// 弹出公司详细信息页面
	public void cobasedetial(String str) {
		if (str != "" && !str.equals("") && str != null) {
			Map cidmap = new HashMap();
			cidmap.put("cid", str);
			Window window = (Window) Executions.createComponents(
					"CoBase_DetailInfoshow.zul", null, cidmap);
			window.doModal();
		}
	}

	public void opcooffer(String cid) {
		if (cid != null && !cid.isEmpty()) {
			Map map = new HashMap();
			map.put("cid", cid);
			Window window = (Window) Executions.createComponents(
					"/CoBase/Cobase_allinoneshow.zul", null, map);
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
		map.put("cobaM", cobaM);
		map.put("type", type);
		Window window = (Window) Executions.createComponents(url, null, map);
		window.doModal();
		System.out.println("strs=" + strs);
		if (!strs.equals("") && strs != "") {
			cobaselist = new ListModelList<CoBaseModel>(
					sebll.getCobaseinfoforall(strs));
		}
		{
			cobaselist = new ListModelList<CoBaseModel>(
					sebll.getCobaseinfoforall(" and a.cid=" + cid));
		}
		cobaseinfo.setModel(cobaselist);
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
				"../CoCompact/Compact_InfoAdd.zul", null, map);
		window.doModal();
	}

	// 新增合同信息(全国项目部)
	public void AddCompanyForQG(String cid) {
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

	// 点击树事件
	@Listen("onClick =#tree > Treechildren")
	public void addCoLatencyClient() {
		if (tree.getSelectedItem().isOpen()) {
			tree.getSelectedItem().setOpen(false);
		} else {
			tree.getSelectedItem().setOpen(true);
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
		cobaselist = new ListModelList<CoBaseModel>(mo);
		cobaseinfo.setModel(cobaselist);
	}

	@Listen("onOk =#combo")
	public void s() {
		search();
	}

	// 公司信息查询页面
	@Listen("onChange =#combo")
	public void search() {
		String stre = combo.getValue();
		// System.out.println(combo.getValue());
		String strwhere = "";
		try {
			if (stre.indexOf("|") != -1) {
				strwhere = stre.split("\\|")[1];
				List<CoBaseModel> mo = sebll.getshCobaseinfo(strwhere);
				cobaselist = new ListModelList<CoBaseModel>(mo);
				cobaseinfo.setModel(cobaselist);
			} else {

				List<CoBaseModel> mo = sebll.getshCobaseinfo(stre);
				cobaselist = new ListModelList<CoBaseModel>(mo);
				cobaseinfo.setModel(cobaselist);
			}
		} catch (Exception e) {
			System.out.println(e.toString());
		}
	}

	// 新增员工
	public void addem(int a, String cid) {
		Map<String, Object> map = new HashMap<String, Object>();
		String url = "";
		CoBaseModel cobaM = new CoBaseModel();
		List<CoBaseModel> ListcoM = sebll.getshCobaseinfo(cid);
		cobaM = ListcoM.get(0);
		System.out.println(cobaM.getCoba_company());
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

	public String getStrwhere() {
		return strwhere;
	}

	public void setStrwhere(String strwhere) {
		this.strwhere = strwhere;
	}

	public ListModel<CoBaseModel> getCobaselist() {
		return cobaselist;
	}

	public void setCobaselist(ListModel<CoBaseModel> cobaselist) {
		this.cobaselist = cobaselist;
	}

}
