package Controller.EmBodyCheck;

import java.util.List;

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

import Model.CoBaseModel;
import Model.EmBcItemGroupModel;
import Model.EmBcSetupModel;
import Model.EmBodyCheckItemModel;
import Model.loginroleModel;
import Util.UserInfo;
import bll.EmBodyCheck.EmbcItem_OperateBll;
import bll.EmBodyCheck.EmbcItem_SelectBll;

public class EmBc_ItemGroupEditController {
	private List<EmBodyCheckItemModel> list = new ListModelList<>();
	private List<EmBodyCheckItemModel> gList = new ListModelList<>();
	private List<loginroleModel> clientList = new ListModelList<>();
	private List<CoBaseModel> cobaseList = new ListModelList<>();
	private List<EmBcSetupModel> esList = new ListModelList<>();

	private EmbcItem_SelectBll bll = new EmbcItem_SelectBll();
	private EmbcItem_OperateBll obll = new EmbcItem_OperateBll();
	private EmBcItemGroupModel ebgm = (EmBcItemGroupModel) Executions
			.getCurrent().getArg().get("model");
	private Window win = (Window) Path.getComponent("/winedit");
	private String username = UserInfo.getUsername();
	private String idstr = "";
	private String searchContent = "";

	public EmBc_ItemGroupEditController() {
		setClientList();
		setCobaseList(ebgm.getCoba_client());
		setEsList();
		setgList(ebgm.getEbig_id(), null);
		idstr = idstrs();
		setList(ebgm.getEbig_hospital());
		ebgm.setEbig_modname(username);
	}

	@Command
	@NotifyChange("cobaseList")
	public void getCompanyList() {
		Combobox cb = (Combobox) win.getFellow("client");
		if (cb.getSelectedItem() != null) {
			setCobaseList(cb.getSelectedItem().getLabel());
		}
	}

	@Command
	@NotifyChange({ "list", "gList" })
	public void updateList(@BindingParam("a") Combobox cb) {
		if (cb.getSelectedItem() != null) {
			setList(Integer
					.parseInt(cb.getSelectedItem().getValue().toString()));
			if (Integer.parseInt(cb.getSelectedItem().getValue().toString()) == ebgm
					.getEbig_hospital()) {
				setgList(ebgm.getEbig_id(), null);
			} else {
				setgList(null, "0");
			}
		}
	}

	@Command
	@NotifyChange("gList")
	public void updateGlist(@BindingParam("a") EmBodyCheckItemModel em,
			@BindingParam("b") Checkbox cbk) {
		String idLIst = "";
		if (gList.size() > 0) {
			for (int i = 0; i < gList.size(); i++) {
				idLIst = idLIst + "," + gList.get(i).getEbit_id();
			}
			idLIst = idLIst.substring(1);
		}
		
		//System.out.println(idLIst);
		if (cbk.isChecked()) {
			System.out.println(111);
			if (bll.getblood(em, gList)) {
				
				idLIst = idLIst + "," + cbk.getValue();
				//System.out.println(idLIst);
				//idLIst = idLIst.substring(1);
				//System.out.println(idLIst);
			} else {
				cbk.setChecked(false);
				Messagebox.show("请先选择抽血项目", "操作提示", Messagebox.OK,
						Messagebox.ERROR);
				return;
			}
		} else {
			//System.out.println(222);
			for (int i = 0; i < gList.size(); i++) {
				if (gList.get(i).getEbit_id().equals(em.getEbit_id())) {
					gList.remove(i);
				}
			}
			if (gList.size() > 0) {
				idLIst = "";
				for (int i = 0; i < gList.size(); i++) {
					idLIst = idLIst + "," + gList.get(i).getEbit_id();
				}
				idLIst = idLIst.substring(1);
			}
		}
		//System.out.print(idLIst);
		setgList(null, idLIst);
	}
	
	@Command
	@NotifyChange("list")
	public void search(){
		EmBodyCheckItemModel em = new EmBodyCheckItemModel();
		em.setEbit_hospital(ebgm.getEbig_hospital());
		em.setEbit_state(1);
		em.setEbit_name(searchContent);
		list = bll.getEmbcItem(em);
	}

	@Command
	public void submit() {
		if (!bll.ifUseGroup(ebgm.getEbig_id())) {
			Textbox tb = (Textbox) win.getFellow("groupname");
			Combobox cbCompany = (Combobox) win.getFellow("cobase");
			Combobox cbSetup = (Combobox) win.getFellow("setup");
			if (tb.getValue() == null || tb.getValue().equals("")) {
				Messagebox.show("请输入组合名称", "操作提示", Messagebox.OK,
						Messagebox.ERROR);
				return;
			} else {
				ebgm.setEbig_name(tb.getValue());
			}

			if (cbCompany.getValue() == null || cbCompany.getValue().equals("")) {
				Messagebox.show("请选择公司", "操作提示", Messagebox.OK,
						Messagebox.ERROR);
				return;
			} else {
				if (cbCompany.getSelectedItem() != null) {
					ebgm.setCid(Integer.valueOf(cbCompany.getSelectedItem()
							.getValue().toString()));
				}
			}

			if (cbSetup.getSelectedItem() == null) {
				Messagebox.show("请选择体检机构", "操作提示", Messagebox.OK,
						Messagebox.ERROR);
				return;
			} else {
				ebgm.setEbig_hospital(Integer.valueOf(cbSetup.getSelectedItem()
						.getValue().toString()));
			}

			if (gList.size() == 0) {
				Messagebox.show("请选择体检项目", "操作提示", Messagebox.OK,
						Messagebox.ERROR);
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
								Integer i = obll.modGroup(ebgm, gList);
								if (i > 0) {
									Messagebox.show("操作成功!", "操作提示",
											Messagebox.OK,
											Messagebox.INFORMATION);
									win.detach();
								} else {
									Messagebox.show("操作失败!", "操作提示",
											Messagebox.OK, Messagebox.ERROR);
								}
							}

						}
					});
		} else {
			Messagebox.show("该组合正在使用，不能修改", "操作提示", Messagebox.OK,
					Messagebox.ERROR);
		}
	}

	// 取消
	@Command
	@NotifyChange("gList")
	public void cancel(@BindingParam("model") EmBodyCheckItemModel mol) {
		gList.remove(mol);
	}

	public List<EmBodyCheckItemModel> getList() {
		return list;
	}

	public void setList(Integer setupID) {
		EmBodyCheckItemModel em = new EmBodyCheckItemModel();
		em.setEbit_hospital(setupID);
		em.setEbit_state(1);
		em.setIdstr(idstr);
		this.list = bll.getEmbcItem(em);
	}

	public List<EmBodyCheckItemModel> getgList() {
		return gList;
	}

	public void setgList(Integer groupId, String idList) {
		EmBodyCheckItemModel em = new EmBodyCheckItemModel();
		em.setIdList(idList);
		em.setEigl_ebig_id(groupId);
		this.gList = bll.getEmbcItem(em);
	}

	public List<loginroleModel> getClientList() {
		return clientList;
	}

	public void setClientList() {
		loginroleModel lm = new loginroleModel();
		lm.setLog_inure(1);
		lm.setType(1);
		this.clientList = bll.getClientList(lm);
	}

	private String idstrs() {
		String ids = "";
		for (EmBodyCheckItemModel m : gList) {
			if (ids == "") {
				ids = m.getEbit_id() + "";
			} else {
				ids = ids + "," + m.getEbit_id();
			}
		}
		return ids;
	}

	public List<CoBaseModel> getCobaseList() {
		return cobaseList;
	}

	public void setCobaseList(String name) {
		this.cobaseList = bll.getCobaseList(name);
	}

	public List<EmBcSetupModel> getEsList() {
		return esList;
	}

	public void setEsList() {
		EmBcSetupModel em = new EmBcSetupModel();
		em.setEbcs_state(1);
		this.esList = bll.getEmbcSetUp(em);
	}

	public EmBcItemGroupModel getEbgm() {
		return ebgm;
	}

	public void setEbgm(EmBcItemGroupModel ebgm) {
		this.ebgm = ebgm;
	}

	public String getSearchContent() {
		return searchContent;
	}

	public void setSearchContent(String searchContent) {
		this.searchContent = searchContent;
	}

}
