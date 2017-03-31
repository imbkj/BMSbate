package Controller.EmBenefit;

import java.util.List;

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

import bll.EmBenefit.EmBenefit_comitEmListBll;
import Model.CoBaseModel;
import Model.EmBenefitModel;
import Model.EmWelfareModel;
import Model.EmbaseModel;

public class EmBenefit_AddEMController {

	private EmWelfareModel ewm = (EmWelfareModel) Executions.getCurrent()
			.getArg().get("em");
	private Window win = (Window) Path.getComponent("/winEmp");
	private EmBenefit_comitEmListBll bll = new EmBenefit_comitEmListBll();

	private List<EmbaseModel> emList = new ListModelList<>();
	private List<CoBaseModel> coList = new ListModelList<>();
	private List<CoBaseModel> clientList = new ListModelList<>();
	private List<EmBenefitModel> itemList = new ListModelList<>();
	private List<EmbaseModel> nameList = new ListModelList<>();

	public EmBenefit_AddEMController() {
		coList = bll.companylist(null);
		clientList = bll.clientlist();
		itemList = bll.itemList();
	}

	@Command
	public void winD(@BindingParam("a") Window w) {
		win = w;
	}

	@Command
	@NotifyChange({ "coList", "nameList" })
	public void updateCompanyList() {
		Combobox cb = (Combobox) win.getFellow("client");
		Combobox cb2 = (Combobox) win.getFellow("company");
		if (cb.getSelectedItem() != null
				&& !cb.getSelectedItem().getLabel().equals("")) {
			cb2.setValue("");
			coList = bll.companylist(cb.getSelectedItem().getLabel());
		}
		updateEmbaseList();
	}

	@Command
	@NotifyChange({ "nameList" })
	public void updateEmbaseList() {
		Combobox cb = (Combobox) win.getFellow("client");
		Combobox cb2 = (Combobox) win.getFellow("company");
		Combobox cb3 = (Combobox) win.getFellow("emp");
		Integer cid = null;
		String client = "";
		String name = cb3.getValue();
		if (cb.getSelectedItem() != null
				&& !cb.getSelectedItem().getLabel().equals("")) {
			client = cb.getSelectedItem().getLabel();
		}
		if (cb2.getSelectedItem() != null
				&& !cb2.getSelectedItem().getValue().equals("")) {
			cid = cb2.getSelectedItem().getValue();
		}
		nameList = bll.nameList(cid, client, name);
	}

	@Command
	@NotifyChange("emList")
	public void search() {
		Combobox cb = (Combobox) win.getFellow("client");
		Combobox cb2 = (Combobox) win.getFellow("company");
		Combobox cb3 = (Combobox) win.getFellow("emp");
		Combobox cb4 = (Combobox) win.getFellow("fl");
		Integer cid = null;
		String client = "";
		String name = cb3.getValue();
		Integer item = null;
		if (cb4.getSelectedItem() != null) {
			item = cb4.getSelectedItem().getValue();
			if (cb.getSelectedItem() != null
					&& !cb.getSelectedItem().getLabel().equals("")) {
				client = cb.getSelectedItem().getLabel();
			}
			if (cb2.getSelectedItem() != null
					&& !cb2.getSelectedItem().getValue().equals("")) {
				cid = cb2.getSelectedItem().getValue();
			}
			emList = bll.empList(cid, client, name, item);
		} else {
			Messagebox.show("请选择福利项目!", "操作提示", Messagebox.OK,
					Messagebox.INFORMATION);
		}
	}

	@Command
	public void submit() {
		boolean b = false;
		for (EmbaseModel m : emList) {
			if (m.isChecked()) {
				b = true;
				if (m.getColi_flpaykind() == null
						|| m.getColi_flpaykind().equals("")) {
					Messagebox.show(
							m.getCoba_company() + "," + m.getEmba_name()
									+ ",报价单项目未录入付款性质!", "操作提示", Messagebox.OK,
							Messagebox.INFORMATION);
					return;
				}
			}
		}
		if (!b) {
			Messagebox.show("请选择需要添加福利的人员名单!", "操作提示", Messagebox.OK,
					Messagebox.INFORMATION);
			return;
		}
		Messagebox.show("确认提交数据?", "操作提示", new Messagebox.Button[] {
				Messagebox.Button.OK, Messagebox.Button.CANCEL },
				Messagebox.QUESTION,
				new EventListener<Messagebox.ClickEvent>() {
					@Override
					public void onEvent(ClickEvent event) throws Exception {
						// TODO Auto-generated method stub

						if (Messagebox.Button.OK.equals(event.getButton())) {
							Integer j = 0;
							ewm.setIdList("");
							for (EmbaseModel m : emList) {
								if (m.isChecked()) {
									if (m.getColi_flpaykind() != null
											&& !m.getColi_flpaykind()
													.equals("")) {

										Integer i = bll.add(m);
										if (i > 0) {
											ewm.setIdList(ewm.getIdList() + ","
													+ i);
										} else {
											Messagebox.show(m.getCoba_company()
													+ "," + m.getEmba_name()
													+ ",数据添加失败!", "操作提示",
													Messagebox.OK,
													Messagebox.INFORMATION);
											return;
										}
										j++;
									} else {
										Messagebox.show(m.getCoba_company()
												+ "," + m.getEmba_name()
												+ ",报价单项目未录入付款性质!", "操作提示",
												Messagebox.OK,
												Messagebox.INFORMATION);
										return;
									}
								}
							}
							Messagebox.show("成功添加" + j + "条数据.", "操作提示",
									Messagebox.OK, Messagebox.INFORMATION);
							ewm.setIdList(ewm.getIdList().substring(1));
							win.detach();
						}
					}
				});

	}

	@Command
	public void checkall(@BindingParam("a") Checkbox ck) {
		for (EmbaseModel m : emList) {
			m.setChecked(ck.isChecked());
			BindUtils.postNotifyChange(null, null, m, "checked");
		}
	}

	public List<EmbaseModel> getEmList() {
		return emList;
	}

	public void setEmList(List<EmbaseModel> emList) {
		this.emList = emList;
	}

	public List<CoBaseModel> getCoList() {
		return coList;
	}

	public void setCoList(List<CoBaseModel> coList) {
		this.coList = coList;
	}

	public List<CoBaseModel> getClientList() {
		return clientList;
	}

	public void setClientList(List<CoBaseModel> clientList) {
		this.clientList = clientList;
	}

	public List<EmBenefitModel> getItemList() {
		return itemList;
	}

	public void setItemList(List<EmBenefitModel> itemList) {
		this.itemList = itemList;
	}

	public List<EmbaseModel> getNameList() {
		return nameList;
	}

	public void setNameList(List<EmbaseModel> nameList) {
		this.nameList = nameList;
	}

}
