package Controller.EmBodyCheck;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
import org.zkoss.zul.Textbox;
import org.zkoss.zul.Window;
import org.zkoss.zul.Messagebox.ClickEvent;

import bll.EmBodyCheck.EmbcItem_OperateBll;
import bll.EmBodyCheck.EmbcItem_SelectBll;

import Model.CoBaseModel;
import Model.EmBcItemGroupModel;
import Model.EmBcSetupModel;
import Model.EmBodyCheckItemModel;
import Util.UserInfo;

public class EmBc_ItemGroupAddController {
	private List<EmBodyCheckItemModel> list = new ListModelList<>();
	private List<String> clientList = new ListModelList<>();
	private List<CoBaseModel> companyList = new ListModelList<>();
	private List<EmBcSetupModel> setupList = new ListModelList<>();
	private EmBcItemGroupModel ebgm = new EmBcItemGroupModel();
	private List<EmBodyCheckItemModel> itemList = new ListModelList<>();
	private String username = UserInfo.getUsername();
	private Window win = (Window) Path.getComponent("/winItemAdd");
	private EmbcItem_SelectBll bll = new EmbcItem_SelectBll();

	private EmBcItemGroupModel em = new EmBcItemGroupModel();

	public EmBc_ItemGroupAddController() {
		clientList = bll.getClientList();
		setSetupList(null);
	}

	// 选择客服时查询该客服的公司
	@Command
	@NotifyChange("companyList")
	public void changecobase(@BindingParam("client") String client) {
		if (client != null) {
			companyList = bll.getCobaseByClientList(client);
		}

	}

	// 选择体检机构时查询体检机构的项目
	@Command
	@NotifyChange({ "list", "itemList" })
	public void changeitem() {
		Combobox cbsu = (Combobox) win.getFellow("setup");
		Textbox tb = (Textbox) win.getFellow("searchName");
		if (cbsu.getSelectedItem() != null
				&& !cbsu.getSelectedItem().getValue().equals("")) {
			setList(Integer.valueOf(cbsu.getSelectedItem().getValue()
					.toString()), tb.getValue());

		}
	}

	// 查询项目
	@Command
	@NotifyChange("list")
	public void searchitem() {
		Combobox cbsu = (Combobox) win.getFellow("setup");
		Textbox tb = (Textbox) win.getFellow("searchName");
		if (cbsu.getSelectedItem() != null
				&& !cbsu.getSelectedItem().getValue().equals("")) {
			setList(Integer.valueOf(cbsu.getSelectedItem().getValue()
					.toString()), tb.getValue());

		}
	}

	@Command
	@NotifyChange("itemList")
	public void getSelectItem(@BindingParam("a") EmBodyCheckItemModel em,
			@BindingParam("b") Checkbox ck) {

		if (ck.isChecked()) {
			boolean b = false;
			for (int i = 0; i < itemList.size(); i++) {
				if (itemList.get(i).getEbit_id().equals(ck.getValue())) {
					b = true;
				}
			}
			if (!b) {
				if (bll.getblood(em, itemList)) {
					itemList.add(em);
				} else {
					ck.setChecked(false);
					Messagebox.show("请先选择抽血项目!", "操作提示", Messagebox.OK,
							Messagebox.ERROR);
				}

			} else {
				ck.setChecked(false);
				Messagebox.show("该项目已存在!", "操作提示", Messagebox.OK,
						Messagebox.ERROR);
			}
		} else {
			for (int i = 0; i < itemList.size(); i++) {
				if (itemList.get(i).getEbit_id().equals(ck.getValue())) {
					itemList.remove(i);
				}
			}
		}

		setItemList(itemList);

	}

	// 组合新增
	@Command
	public void addgroup() {
		Textbox tbName = (Textbox) win.getFellow("groupname");
		Combobox cbName = (Combobox) win.getFellow("cobase");
		Combobox cbHospital = (Combobox) win.getFellow("setup");
		Textbox tbRemark = (Textbox) win.getFellow("remark");

		if (tbName.getValue().equals("")) {
			Messagebox
					.show("请输入组合名称!", "操作提示", Messagebox.OK, Messagebox.ERROR);
			return;
		} else {
			em.setEbig_name(tbName.getValue());
		}

		if (cbName.getSelectedItem() != null
				&& cbName.getSelectedItem().getValue() != null
				&& cbName.getSelectedItem().getValue().equals("")) {
			Messagebox.show("请选择公司!", "操作提示", Messagebox.OK, Messagebox.ERROR);
			return;
		} else {
			em.setCid(Integer.valueOf(cbName.getSelectedItem().getValue()
					.toString()));
		}

		if (cbHospital.getSelectedItem() != null
				&& cbHospital.getSelectedItem().getValue().equals("")) {
			Messagebox
					.show("请选择体检机构!", "操作提示", Messagebox.OK, Messagebox.ERROR);
			return;
		} else {
			em.setEbig_hospital(Integer.valueOf(cbHospital.getSelectedItem()
					.getValue().toString()));
		}
		if (itemList.size() == 0) {
			Messagebox
					.show("请选择体检项目!", "操作提示", Messagebox.OK, Messagebox.ERROR);
			return;
		}
		em.setEbig_remark(tbRemark.getValue());
		em.setEbig_addname(username);

		Messagebox.show("确认提交数据?", "操作提示", new Messagebox.Button[] {
				Messagebox.Button.OK, Messagebox.Button.CANCEL },
				Messagebox.QUESTION,
				new EventListener<Messagebox.ClickEvent>() {
					@Override
					public void onEvent(ClickEvent event) throws Exception {
						// TODO Auto-generated method stub

						if (Messagebox.Button.OK.equals(event.getButton())) {

							BigDecimal charge = new BigDecimal(0);
							BigDecimal discount = new BigDecimal(0);
							for (int i = 0; i < itemList.size(); i++) {
								charge = charge.add(itemList.get(i)
										.getEbit_charge());
								discount = discount.add(itemList.get(i)
										.getEbit_discount());
							}
							em.setEbig_charge(charge);
							em.setEbig_discount(discount);

							EmbcItem_OperateBll obll = new EmbcItem_OperateBll();
							Integer id = obll.ItemGroupAdd(em);
							if (id > 0) {
								for (int i = 0; i < itemList.size(); i++) {
									obll.AddEmbcIGList(itemList.get(i)
											.getEbit_id(), id, username);
								}
								
								Messagebox.show("添加成功!", "操作提示", Messagebox.OK,
										Messagebox.INFORMATION);
								win.detach();
								Map map = new HashMap<>();
								map.put("id", id);
								Window window = (Window) Executions
										.createComponents(
												"EmBc_ItemGroupManagerList.zul",
												null, map);
								window.doModal();
								

							} else {
								Messagebox.show("添加失败!", "操作提示", Messagebox.OK,
										Messagebox.ERROR);
							}
						}
					}
				});
	}

	public List<EmBodyCheckItemModel> getList() {
		return list;
	}

	public void setList(Integer hospital, String name) {
		EmBodyCheckItemModel m = new EmBodyCheckItemModel();
		m.setEbit_state(1);
		m.setEbit_name(name);
		m.setEbit_hospital(hospital);
		this.list = bll.getEmbcItem(m);
	}

	public List<String> getClientList() {
		return clientList;
	}

	public void setClientList(List<String> clientList) {
		this.clientList = clientList;
	}

	public List<CoBaseModel> getCompanyList() {
		return companyList;
	}

	public void setCompanyList(String client, String columns) {
		CoBaseModel cm = new CoBaseModel();
		cm.setCoba_servicestate(1);
		cm.setCoba_client(client);
		this.companyList = bll.getCobaseInfo(cm, columns, true);
	}

	public List<EmBcSetupModel> getSetupList() {
		return setupList;
	}

	public void setSetupList(Integer id) {
		EmBcSetupModel em = new EmBcSetupModel();
		em.setEbcs_state(1);
		em.setEbcs_id(id);
		this.setupList = bll.getEmbcSetUp(em);
	}

	public EmBcItemGroupModel getEbgm() {
		return ebgm;
	}

	public void setEbgm(EmBcItemGroupModel ebgm) {
		this.ebgm = ebgm;
	}

	public List<EmBodyCheckItemModel> getItemList() {
		return itemList;
	}

	public void setItemList(List<EmBodyCheckItemModel> itemList) {
		this.itemList = itemList;
	}

}
