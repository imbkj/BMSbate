package Controller.EmBenefit;

import impl.WorkflowCore.WfOperateImpl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.zkoss.bind.annotation.BindingParam;
import org.zkoss.bind.annotation.Command;
import org.zkoss.bind.annotation.NotifyChange;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.Path;
import org.zkoss.zk.ui.event.EventListener;
import org.zkoss.zul.Combobox;
import org.zkoss.zul.ListModelList;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Textbox;
import org.zkoss.zul.Window;
import org.zkoss.zul.Messagebox.ClickEvent;

import service.WorkflowCore.WfOperateService;

import bll.EmBenefit.EmActy_compactAddImpl;
import bll.EmBenefit.EmActy_compactBll;
import Model.EmActyCompactModel;
import Model.EmActySupProductInfoModel;
import Util.UserInfo;

public class EmActy_compactListController {
	private List<EmActyCompactModel> compactList = new ListModelList<>();
	private List<EmActyCompactModel> addnameList = new ListModelList<>();
	private EmActy_compactBll bll = new EmActy_compactBll();
	private Window win = (Window) Path.getComponent("/wincompact");

	public EmActy_compactListController() {
		setCompactList(null, true);
		setAddnameList("");
	}

	@Command("Search")
	@NotifyChange("compactList")
	public void Search() {
		Textbox tbName = (Textbox) win.getFellow("name");
		Textbox tbCompactId = (Textbox) win.getFellow("compactid");
		Combobox cbName = (Combobox) win.getFellow("addname");
		Combobox cbState = (Combobox) win.getFellow("compactState");
		EmActyCompactModel eacm = new EmActyCompactModel();
		if (tbName.getValue() != null && !tbName.getValue().equals("")) {
			eacm.setEaco_name(tbName.getValue());
		} else {
			eacm.setEaco_name(null);
		}
		if (tbCompactId.getValue() != null
				&& !tbCompactId.getValue().equals("")) {
			eacm.setEaco_compactid(tbCompactId.getValue());
		} else {
			eacm.setEaco_compactid(null);
		}
		if (cbName.getSelectedItem() != null
				&& !cbName.getSelectedItem().getLabel().equals("")) {
			eacm.setEaco_addname(cbName.getSelectedItem().getLabel());
		} else {
			eacm.setEaco_addname(null);
		}
		if (cbState.getSelectedItem() != null
				&& !cbState.getSelectedItem().getLabel().equals("")) {
			if (!cbState.getSelectedItem().getLabel().equals("全部")) {
				Integer state = null;
				switch (cbState.getSelectedItem().getLabel()) {
				case "生效":
					state = 1;
					break;
				case "合同制作":
					state = 2;
					break;
				case "待审核":
					state = 3;
					break;
				case "已审核":
					state = 4;
					break;
				case "退回":
					state = 5;
					break;
				case "已签回":
					state = 6;
					break;
				case "已归档":
					state = 7;
					break;
				case "取消":
					state = 0;
					break;
				default:
					break;
				}
				eacm.setEaco_state(state);
			} else {
				eacm.setEaco_state(null);
			}

		} else {
			eacm.setEaco_state(null);
		}
		setCompactList(eacm, true);

	}

	@Command("mod")
	@NotifyChange("compactList")
	public void mod(@BindingParam("a") EmActyCompactModel eacm) {
		Map map = new HashMap();

		map.put("id", eacm.getEaco_tapr_id());
		map.put("daid", eacm.getEaco_id());
		Window window = (Window) Executions.createComponents(
				"EmActy_compactMod.zul", null, map);
		window.doModal();
		setCompactList(null, true);
	}

	@Command
	@NotifyChange("compactList")
	public void del(@BindingParam("a") final EmActyCompactModel eacm) {
		Messagebox.show("确认删除合同?", "操作提示", new Messagebox.Button[] {
				Messagebox.Button.OK, Messagebox.Button.CANCEL },
				Messagebox.QUESTION,
				new EventListener<Messagebox.ClickEvent>() {
					@Override
					public void onEvent(ClickEvent event) throws Exception {
						// TODO Auto-generated method stub

						if (Messagebox.Button.OK.equals(event.getButton())) {
							if (eacm.getEaco_tapr_id() != null
									&& !eacm.getEaco_tapr_id().equals("")) {

								WfOperateService wf = new WfOperateImpl(
										new EmActy_compactAddImpl());
								Object[] obj = { 1, eacm };
								String[] str = wf.StopTask(obj,
										eacm.getEaco_tapr_id(),
										UserInfo.getUsername(), "");
								if (str[0].equals("1")) {
									Messagebox.show("操作成功!", "操作提示",
											Messagebox.OK,
											Messagebox.INFORMATION);
									setCompactList(null, true);
								} else {
									Messagebox.show("操作失败!", "操作提示",
											Messagebox.OK, Messagebox.ERROR);
								}
							} else {
								Integer i = bll.delCompact(eacm.getEaco_id());
								if (i > 0) {
									Messagebox.show("操作成功!", "操作提示",
											Messagebox.OK,
											Messagebox.INFORMATION);
									setCompactList(null, true);
								} else {
									Messagebox.show("操作失败!", "操作提示",
											Messagebox.OK, Messagebox.ERROR);
								}
							}
						}

					}
				});
	}

	@Command("addproduct")
	public void addproduct(@BindingParam("a") EmActyCompactModel eacm) {
		Map map = new HashMap();
		map.put("id", eacm.getEaco_tapr_id());
		map.put("daid", eacm.getEaco_id());
		Window window = (Window) Executions.createComponents(
				"EmActy_addProduct.zul", null, map);
		window.doModal();

	}

	@Command
	@NotifyChange("compactList")
	public void upload(@BindingParam("a") EmActyCompactModel eacm) {

		List<EmActySupProductInfoModel> list = bll.getProductInfo(eacm
				.getEaco_id());
		if (list.size() == 0) {
			Messagebox.show("请先添加合同所属产品.", "操作提示", Messagebox.OK,
					Messagebox.ERROR);
			return;
		}

		Map map = new HashMap();
		map.put("id", eacm.getEaco_id());
		Window window = (Window) Executions.createComponents(
				"EmActy_UpLoad.zul", null, map);
		window.doModal();
		setCompactList(null, true);
	}

	@Command("checkInfo")
	@NotifyChange("compactList")
	public void checkInfo(@BindingParam("a") EmActyCompactModel eacm,
			@BindingParam("b") Integer look) {
		if (look == null) {
			List<EmActySupProductInfoModel> list = bll.getProductInfo(eacm
					.getEaco_id());
			if (list.size() == 0) {
				Messagebox.show("请先添加合同所属产品.", "操作提示", Messagebox.OK,
						Messagebox.ERROR);
				return;
			}
		}
		Map map = new HashMap();
		map.put("id", eacm.getEaco_tapr_id());
		map.put("daid", eacm.getEaco_id());
		if (look == null) {
			look = eacm.getEaco_state();
		}
		map.put("look", look);
		Window window = (Window) Executions.createComponents(
				"EmActy_compactPrint.zul", null, map);
		window.doModal();
		setCompactList(null, true);
	}

	@Command
	@NotifyChange("compactList")
	public void back(@BindingParam("a") final EmActyCompactModel em) {
		Messagebox.show("确认撤回合同?", "操作提示", new Messagebox.Button[] {
				Messagebox.Button.OK, Messagebox.Button.CANCEL },
				Messagebox.QUESTION,
				new EventListener<Messagebox.ClickEvent>() {
					@Override
					public void onEvent(ClickEvent event) throws Exception {
						// TODO Auto-generated method stub

						if (Messagebox.Button.OK.equals(event.getButton())) {

							WfOperateService wf = new WfOperateImpl(
									new EmActy_compactAddImpl());
							em.setEaco_state(2);
							Object[] obj = { 5, em };
							String[] str = wf.RevokeToN(obj,
									em.getEaco_tapr_id(), 2,
									UserInfo.getUsername());
							/*
							 * String[] str = wf.ReturnToPrev(obj,
							 * em.getEaco_tapr_id(), UserInfo.getUsername(),
							 * "");
							 */
							if (str[0].equals("1")) {
								Messagebox.show("操作成功!", "操作提示", Messagebox.OK,
										Messagebox.INFORMATION);
								setCompactList(null, true);
							} else {
								Messagebox.show("操作失败!", "操作提示", Messagebox.OK,
										Messagebox.ERROR);
							}
						}
					}
				});
	}

	@Command
	@NotifyChange("compactList")
	public void stopcompact(@BindingParam("a") final EmActyCompactModel em) {
		Messagebox.show("确认终止合同?", "操作提示", new Messagebox.Button[] {
				Messagebox.Button.OK, Messagebox.Button.CANCEL },
				Messagebox.QUESTION,
				new EventListener<Messagebox.ClickEvent>() {
					@Override
					public void onEvent(ClickEvent event) throws Exception {
						// TODO Auto-generated method stub

						if (Messagebox.Button.OK.equals(event.getButton())) {
							Integer i = bll.stopCompact(em.getEaco_id());
							if (i > 0) {
								Messagebox.show("操作成功!", "操作提示", Messagebox.OK,
										Messagebox.INFORMATION);
								setCompactList(null, true);
							} else {
								Messagebox.show("操作失败!", "操作提示", Messagebox.OK,
										Messagebox.ERROR);
							}
						}
					}
				});
	}

	public List<EmActyCompactModel> getCompactList() {
		return compactList;
	}

	public void setCompactList(EmActyCompactModel eacm, Boolean desc) {
		this.compactList = bll.getList(eacm, desc);
	}

	public List<EmActyCompactModel> getAddnameList() {
		return addnameList;
	}

	public void setAddnameList(String name) {
		this.addnameList = bll.getaddNameList(name);
	}

}
