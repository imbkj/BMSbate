package Controller.Embase;

import impl.WorkflowCore.WfOperateImpl;

import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.zkoss.bind.BindUtils;
import org.zkoss.bind.annotation.BindingParam;
import org.zkoss.bind.annotation.Command;
import org.zkoss.bind.annotation.GlobalCommand;
import org.zkoss.bind.annotation.NotifyChange;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.event.EventListener;
import org.zkoss.zul.DefaultTreeModel;
import org.zkoss.zul.Include;
import org.zkoss.zul.ListModelList;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.TreeModel;
import org.zkoss.zul.TreeNode;
import org.zkoss.zul.Treeitem;
import org.zkoss.zul.Treerow;
import org.zkoss.zul.Window;
import org.zkoss.zul.Messagebox.ClickEvent;

import service.WorkflowCore.WfBusinessService;
import service.WorkflowCore.WfOperateService;

import bll.EmZYT.EmZYT_OperateBll;
import bll.Embase.EmBase_OnBoardBll;
import bll.Embase.EmbaseLogin_AddBll;
import bll.Embase.Embase_OnBoardImpl;

import Util.CategoryModel;
import Util.UserInfo;
import Model.EmZYTModel;
import Model.EmbaseBusinessCenterModel;
import Model.EmbaseModel;
import Util.CategoryTreeNode;

public class EmBase_EntryController {

	private Integer way;
	private EmbaseModel ebm;

	private Boolean ifembasenotin = false;
	private int emba_id;
	private int cid;
	private EmbaseModel ebmnotin;

	private boolean next;

	private List<EmbaseBusinessCenterModel> ecList = new ListModelList<>();
	private List<EmbaseBusinessCenterModel> ecList2 = new ListModelList<>();

	private TreeModel<TreeNode<CategoryModel>> tree;
	private String url = "../Embase/Embase_Update.zul";

	private EmBase_OnBoardBll bll = new EmBase_OnBoardBll();

	private Window winE;
	private String winId = "winAdd"
			+ java.util.concurrent.ThreadLocalRandom.current().nextLong(1000,
					1999);

	// 智翼通接口传入值
	private EmZYTModel emztM = new EmZYTModel();

	public EmBase_EntryController() throws SQLException {

		try {
			way = Integer.parseInt(Executions.getCurrent().getParameter("way")
					.toString());
		} catch (Exception e) {
			way = 0;
		}
		try {

			cid = Integer.parseInt(Executions.getCurrent().getArg().get("cid")
					.toString());
			emba_id = Integer.parseInt(Executions.getCurrent().getArg()
					.get("embanotin_id").toString());
			if (emba_id > 0) {

				ifembasenotin = true;
				EmbaseLogin_AddBll notinbll = new EmbaseLogin_AddBll();
				ebmnotin = notinbll.getEmbaseLoginInfo(emba_id).get(0);

			}
		} catch (Exception e) {
			ifembasenotin = false;
		}

		// 获取智翼通接口model数据
		if (Executions.getCurrent().getAttribute("emztM") != null) {
			emztM = (EmZYTModel) Executions.getCurrent().getAttribute("emztM");
		}

		if (way == 1) {// 接口跳转过来
			ebm = (EmbaseModel) Executions.getCurrent().getAttribute("ebm");
		} else {
			ebm = (EmbaseModel) Executions.getCurrent().getArg().get("ebm");
		}

		if (ebm.getGid() == null) {
			ebm = bll.getEmbaseInfo(ebm.getEmba_id()).get(0);
		}
		setEcList(ebm.getGid());
		setEcList2();

		// next = bll.getentry(ebm.getGid());

		CategoryModel c = bll.treeNode(ecList2, ecList, new CategoryModel(0,
				"员工信息", "", "../Embase/Embase_Update.zul"));

		CategoryTreeNode rootNode = constructCategoryTreeNode(c);
		tree = new DefaultTreeModel<CategoryModel>(rootNode);
		((DefaultTreeModel<CategoryModel>) tree).addOpenPath(new int[] { 0 });
	}

	@Command("wininfo")
	public void wininfo(@BindingParam("a") Window winD) {
		winE = winD;
	}

	private CategoryTreeNode constructCategoryTreeNode(CategoryModel cm) {
		CategoryTreeNode ct = new CategoryTreeNode(cm, 0);

		List<CategoryTreeNode> list = new LinkedList<CategoryTreeNode>();
		list.add(ct);
		CategoryTreeNode child;
		CategoryTreeNode node;
		while (!list.isEmpty()) {
			node = list.remove(0);
			for (CategoryModel childCM : node.getData().getChildren()) {
				child = new CategoryTreeNode(childCM, 0);
				node.add(child);
				list.add(child);
			}
		}
		CategoryTreeNode rootNode = new CategoryTreeNode(null, -1);
		rootNode.add(ct);
		return rootNode;
	}

	@Command
	@NotifyChange("url")
	public void link(@BindingParam("a") CategoryTreeNode cn,
			@BindingParam("b") Treerow tr) {
		Include ic = (Include) winE.getFellow("refleshurl");
		if (cn.getData() != null && cn.getData().getLink() != null
				&& !cn.getData().getLink().equals("")) {

			url = cn.getData().getLink().toString();
		}
		Treeitem ti = (Treeitem) tr.getParent();
		if (ti != null) {
			ti.setOpen(!ti.isOpen());
		}

	}

	@Command
	public void prev() {
		if (emztM != null && way == 1) {
			Map<String, Object> map = new HashMap<String, Object>();
			// map.put("daid", ebm.getGid());
			map.put("embaId", ebm.getEmba_id());
			map.put("emztM", emztM);
			BindUtils.postGlobalCommand(null, null, "embaPrev", map);
		} else {

			Map map = new HashMap<>();
			// map.put("daid", ebm.getGid());
			map.put("embaId", ebm.getEmba_id());
			Window window = (Window) Executions.createComponents(
					"../EmBase/EmBase_Add.zul", null, map);
			window.doModal();
			winE.detach();
		}
	}

	@Command
	public void submit() throws SQLException {
		next = bll.getentry(ebm.getGid());
		if (next) {
			// 更新预增的数据到基本信息

			Messagebox.show("确认变更数据?", "操作提示", new Messagebox.Button[] {
					Messagebox.Button.OK, Messagebox.Button.CANCEL },
					Messagebox.QUESTION,
					new EventListener<Messagebox.ClickEvent>() {
						@Override
						public void onEvent(ClickEvent event) throws Exception {
							// TODO Auto-generated method stub

							if (Messagebox.Button.OK.equals(event.getButton())) {
								List<EmbaseModel> embaseList = bll
										.embaseinfo(ebm.getEmba_id());
								if (embaseList.size() > 0) {
									if ((embaseList.get(0).getEmba_sb_radix() != null && embaseList
											.get(0).getEmba_sb_radix()
											.compareTo(BigDecimal.ZERO) > 0)
											|| (embaseList.get(0)
													.getEmba_house_radix() != null && embaseList
													.get(0)
													.getEmba_house_radix()
													.compareTo(BigDecimal.ZERO) > 0)) {

										WfBusinessService wfbs = new Embase_OnBoardImpl();
										WfOperateService wfs = new WfOperateImpl(
												wfbs);
										EmbaseModel em = new EmbaseModel();
										if (em.getEmba_state() != null
												&& !em.getEmba_state()
														.equals(1)) {
											em.setEmba_state(3);
										}
										em.setEmba_modname(UserInfo.getUsername());
										em.setEmba_id(ebm.getEmba_id());
										Object[] obj = { "员工入职", em };
										// 新增跳过
										String[] str = wfs.AddTaskToNext(obj,
												"员工入职(无预增)", ebm.getEmba_name()
														+ "(" + ebm.getGid()
														+ ")入职", 87,
												UserInfo.getUsername(), "",
												ebm.getCid(), "");
										em.setEmba_state(5);
										EmbaseModel m = new EmbaseModel();
										m.setEmba_id(em.getEmba_id());
										m.setEmba_state(5);
										m.setEmba_modname(UserInfo.getUsername());
										Object[] obj1 = { "员工入职", m };
										String[] str1 = wfs.SkipToNext(obj1,
												Integer.valueOf(str[2]),
												UserInfo.getUsername(), "",
												ebm.getCid(), "");

										if (str[0].equals("1")) {
											// 发现财税时更新任务单
											bll.updateMission(ebm.getEmba_id(),
													ebm.getGid(),
													Integer.valueOf(str[2]));

											// 智翼通接口数据处理
											if (emztM != null) {
												EmZYT_OperateBll obll = new EmZYT_OperateBll();
												emztM.setEmzt_state(8);
												obll.upEmZYTGid(emztM);

											}

											if (emztM != null && way == 1) {

												BindUtils.postGlobalCommand(
														null, null, "winClose",
														null);
											} else {
												winE.detach();
											}
											Messagebox.show("操作成功!", "操作提示",
													Messagebox.OK,
													Messagebox.INFORMATION);

										} else {
											Messagebox.show("操作失败!", "操作提示",
													Messagebox.OK,
													Messagebox.ERROR);
										}
									} else {
										// 智翼通接口数据处理
										if (emztM != null) {
											EmZYT_OperateBll obll = new EmZYT_OperateBll();
											emztM.setEmzt_state(8);
											obll.upEmZYTGid(emztM);

										}
										EmbaseModel em = new EmbaseModel();
										em.setEmba_state(1);// 财务状态
										em.setEmba_id(ebm.getEmba_id());
										em.setEmba_modname(UserInfo.getUsername());
										Integer i = bll.modInfo(em);

										if (emztM != null && way == 1) {
											BindUtils.postGlobalCommand(null,
													null, "winClose", null);
										} else {
											winE.detach();
										}
									}
								}
							}
						}
					});
		}

	}

	@Command
	public void next() throws SQLException {
		next = bll.getentry(ebm.getGid());
		if (next) {

			if (ebmnotin != null)// 更新为入职状态
			{
				EmbaseLogin_AddBll notinbll = new EmbaseLogin_AddBll();
				notinbll.EmbaseloginUpdatestate(1, ebmnotin.getEmba_id());

			}

			Messagebox.show("确认变更数据?", "操作提示", new Messagebox.Button[] {
					Messagebox.Button.OK, Messagebox.Button.CANCEL },
					Messagebox.QUESTION,
					new EventListener<Messagebox.ClickEvent>() {
						@Override
						public void onEvent(ClickEvent event) throws Exception {
							// TODO Auto-generated method stub

							if (Messagebox.Button.OK.equals(event.getButton())) {
								List<EmbaseModel> embaseList = bll
										.embaseinfo(ebm.getEmba_id());
								if (embaseList.size() > 0) {
									if ((embaseList.get(0).getEmba_sb_radix() != null && embaseList
											.get(0).getEmba_sb_radix()
											.compareTo(BigDecimal.ZERO) > 0)
											|| (embaseList.get(0)
													.getEmba_house_radix() != null && embaseList
													.get(0)
													.getEmba_house_radix()
													.compareTo(BigDecimal.ZERO) > 0)) {

										WfBusinessService wfbs = new Embase_OnBoardImpl();
										WfOperateService wfs = new WfOperateImpl(
												wfbs);
										EmbaseModel em = new EmbaseModel();
										em.setEmba_state(3);
										em.setEmba_id(ebm.getEmba_id());
										em.setEmba_modname(UserInfo.getUsername());
										Object[] obj = { "员工入职", em };

										String[] str;
										str = wfs.PassToNext(obj,
												ebm.getEmba_tapr_id(),
												UserInfo.getUsername(), "",
												ebm.getCid(), "");
	
										
										if (str[0].equals("1")) {

											// 智翼通接口数据处理
											if (emztM != null) {
												EmZYT_OperateBll obll = new EmZYT_OperateBll();
												emztM.setEmzt_state(8);
												obll.upEmZYTGid(emztM);
											}

											if (emztM != null && way == 1) {
												//em.setEmba_state(5);
												//Integer i = bll.modInfo(em);

												EmbaseModel m = new EmbaseModel();
												m.setEmba_id(em.getEmba_id());
												m.setEmba_state(5);
												m.setEmba_modname(UserInfo.getUsername());
												Object[] obj1 = { "员工入职", m };

												// 智翼通接口的数据跳过客服确认页的节点
												str = wfs.SkipToNext(obj1,
														Integer.parseInt(str[2]),
														UserInfo.getUsername(), "",
														ebm.getCid(), "");
												
												BindUtils.postGlobalCommand(
														null, null, "winClose",
														null);
											} else {
												em.setEmba_state(3);
												em.setEmba_modname(UserInfo.getUsername());
												Integer i = bll.modInfo(em);
												Map map = new HashMap();
												map.put("ebm", ebm);
												map.put("emztM", emztM);
												Window window = (Window) Executions
														.createComponents(
																"../Embase/Embase_confirm.zul",
																null, map);

												window.doModal();
												winE.detach();
											}
											Messagebox.show("操作成功!", "操作提示",
													Messagebox.OK,
													Messagebox.INFORMATION);

										} else {
											Messagebox.show("操作失败!", "操作提示",
													Messagebox.OK,
													Messagebox.ERROR);
										}
									} else {
										// 智翼通接口数据处理
										if (emztM != null) {
											EmZYT_OperateBll obll = new EmZYT_OperateBll();
											emztM.setEmzt_state(8);
											obll.upEmZYTGid(emztM);

										}
										EmbaseModel em = new EmbaseModel();
										em.setEmba_id(ebm.getEmba_id());

										if (emztM != null && way == 1) {

											em.setEmba_state(5);
											em.setEmba_modname(UserInfo.getUsername());
											Integer i = bll.modInfo(em);
											BindUtils.postGlobalCommand(null,
													null, "winClose", null);
										} else {
											em.setEmba_state(3);
											em.setEmba_modname(UserInfo.getUsername());
											Integer i = bll.modInfo(em);
											Map map = new HashMap();
											map.put("ebm", ebm);
											map.put("emztM", emztM);
											Window window = (Window) Executions
													.createComponents(
															"../Embase/Embase_confirm.zul",
															null, map);

											window.doModal();
											winE.detach();
										}
									}
								}
							}
						}
					});
		}
	}

	

	@GlobalCommand("refreshEntrySecondUrl")
	public void refreshEntrySecondUrl(@BindingParam("url") String newurl) {
		try {
			Include refleshurl = (Include) winE.getFellow("refleshurl");
			if (refleshurl != null) {
				refleshurl.setSrc("");
				refleshurl.setSrc(newurl);
			} else {
				winE.detach();
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public int getCid() {
		return cid;
	}

	public void setCid(int cid) {
		this.cid = cid;
	}

	public Boolean getIfembasenotin() {
		return ifembasenotin;
	}

	public void setIfembasenotin(Boolean ifembasenotin) {
		this.ifembasenotin = ifembasenotin;
	}

	public int getEmba_id() {
		return emba_id;
	}

	public void setEmba_id(int emba_id) {
		this.emba_id = emba_id;
	}

	public EmbaseModel getEbm() {
		return ebm;
	}

	public void setEbm(EmbaseModel ebm) {
		this.ebm = ebm;
	}

	public TreeModel<TreeNode<CategoryModel>> getTree() {
		return tree;
	}

	public List<EmbaseBusinessCenterModel> getEcList() {
		return ecList;
	}

	public void setEcList(Integer gid) {
		this.ecList = bll.tree(2, gid);
	}

	public List<EmbaseBusinessCenterModel> getEcList2() {
		return ecList2;
	}

	public void setEcList2() {
		this.ecList2 = bll.tree(1, null);
	}

	public Window getWinE() {
		return winE;
	}

	public void setWinE(Window winE) {
		this.winE = winE;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public boolean isNext() {
		return next;
	}

	public void setNext(boolean next) {
		this.next = next;
	}

	public EmZYTModel getEmztM() {
		return emztM;
	}

	public void setEmztM(EmZYTModel emztM) {
		this.emztM = emztM;
	}

	public String getWinId() {
		return winId;
	}

	public void setWinId(String winId) {
		this.winId = winId;
	}

}
