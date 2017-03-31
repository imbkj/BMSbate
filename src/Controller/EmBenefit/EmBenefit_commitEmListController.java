package Controller.EmBenefit;

import impl.WorkflowCore.WfOperateImpl;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.zkoss.bind.BindUtils;
import org.zkoss.bind.annotation.BindingParam;
import org.zkoss.bind.annotation.Command;
import org.zkoss.bind.annotation.NotifyChange;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.Path;
import org.zkoss.zk.ui.event.EventListener;
import org.zkoss.zul.Checkbox;
import org.zkoss.zul.Combobox;
import org.zkoss.zul.ListModelList;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Window;
import org.zkoss.zul.Messagebox.ClickEvent;

import service.WorkflowCore.WfBusinessService;
import service.WorkflowCore.WfOperateService;

import bll.EmBenefit.EmBenefit_bxImpl;
import bll.EmBenefit.EmBenefit_comitEmListBll;
import Model.EmBenefitModel;
import Model.EmWelfareModel;
import Util.UserInfo;

public class EmBenefit_commitEmListController {
	private List<EmWelfareModel> cobaseList = new ListModelList<>();
	private List<EmWelfareModel> clientList = new ListModelList<>();
	private List<EmWelfareModel> nameList = new ListModelList<>();
	private List<EmBenefitModel> itemList = new ListModelList<>();
	private List<EmWelfareModel> standardList = new ListModelList<>();
	private List<EmWelfareModel> list = new ListModelList<>();

	private EmWelfareModel ewfm = new EmWelfareModel();
	private EmBenefit_comitEmListBll bll = new EmBenefit_comitEmListBll();
	private Window win = (Window) Path.getComponent("/winEmpList");

	public EmBenefit_commitEmListController() {
		setCobaseList("");
		setClientList("");
		// setNameList("");
		setItemList("");
		setStandardList();
		ewfm.setEmwf_state(2);
		setList(ewfm);

	}

	@Command
	@NotifyChange("cobaseList")
	public void updateCompany(@BindingParam("a") Combobox cb) {

		setCobaseList(cb.getValue());

	}

	@Command
	@NotifyChange("nameList")
	public void updateNameList(@BindingParam("a") Combobox cb) {
		setNameList(cb.getValue());
	}

	@Command("Search")
	@NotifyChange("list")
	public void Search() {
		Combobox cbcompany = (Combobox) win.getFellow("company");
		Combobox cbclient = (Combobox) win.getFellow("client");
		Combobox cbname = (Combobox) win.getFellow("name");
		Combobox cbitem = (Combobox) win.getFellow("item");
		Combobox cbstandard = (Combobox) win.getFellow("standard");
		if (cbitem.getSelectedItem() != null) {
			ewfm.setEmwf_embf_id(Integer.valueOf(cbitem.getSelectedItem()
					.getValue().toString()));
		} else {
			ewfm.setEmwf_embf_id(null);
		}

		ewfm.setEmwf_company(cbcompany.getValue());

		ewfm.setEmwf_client(cbclient.getValue());

		ewfm.setEmwf_name(cbname.getValue());
		ewfm.setEmwf_standard(cbstandard.getValue());
		setList(ewfm);

	}

	@Command
	@NotifyChange("list")
	public void add() {
		Map map = new HashMap();
		EmWelfareModel em = new EmWelfareModel();
		map.put("em", em);
		Window window = (Window) Executions.createComponents(
				"../EmBenefit/EmBenefit_AddEM.zul", null, map);
		window.doModal();
		if (em.getIdList() != null) {
			setList(em);
		}

	}

	@Command("mod")
	@NotifyChange("list")
	public void mod(@BindingParam("a") EmWelfareModel em) {

		Map map = new HashMap();
		map.put("daid", em.getEmwf_id());
		map.put("id", em.getEmwf_tapr_id());
		Window window = (Window) Executions.createComponents(
				"../EmBenefit/EmBenefit_comitInfo.zul", null, map);
		window.doModal();
		setList(em);

	}

	@Command
	@NotifyChange("list")
	public void bx(@BindingParam("a") final EmWelfareModel em) {
		Messagebox.show("确认提交数据?", "操作提示", new Messagebox.Button[] {
				Messagebox.Button.OK, Messagebox.Button.CANCEL },
				Messagebox.QUESTION,
				new EventListener<Messagebox.ClickEvent>() {
					@Override
					public void onEvent(ClickEvent event) throws Exception {
						// TODO Auto-generated method stub

						if (Messagebox.Button.OK.equals(event.getButton())) {
							WfBusinessService wfbs = new EmBenefit_bxImpl();
							WfOperateService wfs = new WfOperateImpl(wfbs);
							em.setEmwf_state(17);
							Object[] obj = { "报销费用", em };
							String[] str = wfs.AddTaskToNext(
									obj,
									"员工福利费用报销",
									em.getEmwf_company() + ","
											+ em.getEmwf_name() + ","
											+ em.getEmbf_name() + "费用报销", 121,
									UserInfo.getUsername(), "", em.getCid(), "");
							if (str[0].equals("1")) {
								Messagebox.show("提交成功!", "操作提示", Messagebox.OK,
										Messagebox.INFORMATION);
								EmWelfareModel m = new EmWelfareModel();
								m.setEmwf_id(em.getEmwf_id());
								setList(m);
							} else {
								Messagebox.show("操作失败!", "操作提示", Messagebox.OK,
										Messagebox.ERROR);
							}
						}
					}
				});

	}

	@Command("modAll")
	@NotifyChange("list")
	public void modAll() {
		boolean b = false;
		int embf_id = 0;
		EmWelfareModel em = new EmWelfareModel();
		em.setIdList("");
		for (int i = 0; i < list.size(); i++) {
			if (list.get(i).isChecked()) {
				b = true;
				embf_id = list.get(i).getEmwf_embf_id();
				em.setIdList(em.getIdList() + "," + list.get(i).getEmwf_id());
			}
		}
		if (!em.getIdList().equals("")) {
			em.setIdList(em.getIdList().substring(1));
		}
		// 检查批量修改的数据用途是否一样
		int num = bll.isSameEmbenefit(em.getIdList());
		if (num > 1) {
			Messagebox.show("不同的福利项目不可以做批量修改", "操作提示", Messagebox.OK,
					Messagebox.INFORMATION);
			return;
		}

		if (b) {
			Map map = new HashMap();
			map.put("list", list);
			map.put("embf_id", embf_id);
			Window window = (Window) Executions.createComponents(
					"EmBenefit_CommitBatch.zul", null, map);
			window.doModal();
			setList(em);
		} else {
			Messagebox.show("请选择需要修改的名单!", "操作提示", Messagebox.OK,
					Messagebox.INFORMATION);
		}

	}

	@Command("checkall")
	public void checkall(@BindingParam("a") Checkbox cka) {
		for (EmWelfareModel m : list) {
			m.setChecked(cka.isChecked());
			BindUtils.postNotifyChange(null, null, m, "checked");
		}

	}

	@Command("commit")
	@NotifyChange("list")
	public void commit() {
		boolean flag = true;
		String idStr = "";
		for (EmWelfareModel m : list) {
			if (m.isChecked()) {
				idStr = idStr + m.getEmwf_id() + ",";
				if (m.getEmwf_prod_id() == null) {
					flag = false;
					break;
				}
				if (m.getProd_name() == null
						|| (!m.getProd_name().equals("面包卷") && m
								.getEmwf_prty_id() == null)) {
					flag = false;
					break;
				}
				if (m.getEmwf_producenum() == null
						|| m.getEmwf_producenum() <= 0) {
					flag = false;
					break;
				}
			}
		}
		if (!flag) {
			if (idStr.length() > 0) {
				idStr = idStr.substring(0, idStr.length() - 1);
			}
			boolean isGift = bll.isGift(idStr);
			if (!isGift) {
				Messagebox.show("礼品内容不完整，不能提交", "操作提示", Messagebox.OK,
						Messagebox.ERROR);
				return;
			}
		}
		Messagebox.show("确认提交数据?", "操作提示", new Messagebox.Button[] {
				Messagebox.Button.OK, Messagebox.Button.CANCEL },
				Messagebox.QUESTION,
				new EventListener<Messagebox.ClickEvent>() {
					@Override
					public void onEvent(ClickEvent event) throws Exception {
						if (Messagebox.Button.OK.equals(event.getButton())) {

							for (EmWelfareModel m : list) {
								if (m.isChecked()) {
									m.setEmwf_state(3);
									bll.commit(m);
								}
							}

							Messagebox.show("操作成功", "操作提示", Messagebox.OK,
									Messagebox.INFORMATION);
							setList(ewfm);
						}
					}
				});
	}

	// 打开退回信息页面
	@Command
	public void backinfo(@BindingParam("model") EmWelfareModel ml) {
		Map map = new HashMap();
		map.put("id", ml.getEmwf_id());
		Window window = (Window) Executions.createComponents(
				"EmActy_BackInfos.zul", null, map);
		window.doModal();
	}

	@Command
	@NotifyChange("list")
	public void delete() {
		Messagebox.show("确认删除已选数据?", "操作提示", new Messagebox.Button[] {
				Messagebox.Button.OK, Messagebox.Button.CANCEL },
				Messagebox.QUESTION,
				new EventListener<Messagebox.ClickEvent>() {
					@Override
					public void onEvent(ClickEvent event) throws Exception {
						if (Messagebox.Button.OK.equals(event.getButton())) {

							for (EmWelfareModel m : list) {
								if (m.isChecked()) {
									bll.deleteEmbenefit(m.getEmwf_id());
								}
							}

							Messagebox.show("操作成功", "操作提示", Messagebox.OK,
									Messagebox.INFORMATION);
							setList(ewfm);
						}
					}
				});
	}

	@Command
	public void edit() {

	}

	@Command
	@NotifyChange("list")
	public void del(@BindingParam("a") final EmWelfareModel em) {
		Messagebox.show("确认删除数据?", "操作提示", new Messagebox.Button[] {
				Messagebox.Button.OK, Messagebox.Button.CANCEL },
				Messagebox.QUESTION,
				new EventListener<Messagebox.ClickEvent>() {
					@Override
					public void onEvent(ClickEvent event) throws Exception {
						if (Messagebox.Button.OK.equals(event.getButton())) {
							bll.deleteEmbenefit(em.getEmwf_id());
							Messagebox.show("操作成功", "操作提示", Messagebox.OK,
									Messagebox.INFORMATION);
							setList(ewfm);
						}
					}
				});
	}

	public List<EmWelfareModel> getCobaseList() {
		return cobaseList;
	}

	public void setCobaseList(String name) {
		this.cobaseList = bll.getCompanyList(name);
	}

	public List<EmWelfareModel> getClientList() {
		return clientList;
	}

	public void setClientList(String name) {
		this.clientList = bll.getClientList(name);
	}

	public List<EmWelfareModel> getNameList() {
		return nameList;
	}

	public void setNameList(String name) {
		this.nameList = bll.getNameList(name);
	}

	public List<EmBenefitModel> getItemList() {
		return itemList;
	}

	public void setItemList(String name) {
		this.itemList = bll.getItemList(name);
	}

	public List<EmWelfareModel> getList() {
		return list;
	}

	public void setList(EmWelfareModel ewfm) {
		this.list = bll.getList(ewfm);
	}

	public List<EmWelfareModel> getStandardList() {
		return standardList;
	}

	public void setStandardList() {
		this.standardList = bll.getStandardList();
	}

}
