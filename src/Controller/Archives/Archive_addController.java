package Controller.Archives;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.zkoss.bind.annotation.BindingParam;
import org.zkoss.bind.annotation.Command;
import org.zkoss.bind.annotation.NotifyChange;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.event.InputEvent;
import org.zkoss.zk.ui.select.SelectorComposer;
import org.zkoss.zul.Combobox;
import org.zkoss.zul.Grid;
import org.zkoss.zul.ListModelList;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Window;

import com.sun.java.swing.plaf.windows.resources.windows;
import com.sun.mail.dsn.message_deliverystatus;

import bll.Archives.Archive_addBll;
import Model.CoBaseModel;
import Model.EmbaseModel;

public class Archive_addController{
	private List<CoBaseModel> cobaselist = new ListModelList<CoBaseModel>();
	private List<CoBaseModel> clientlist = new ListModelList<CoBaseModel>();
	private List<EmbaseModel> namelist = new ListModelList<EmbaseModel>();
	private List<EmbaseModel> embaselist = new ListModelList<EmbaseModel>();

	private Archive_addBll bll = new Archive_addBll();

	public Archive_addController() {
		setClientlist();
		setCobaselist("", "");

	}

	// 选择客服人员更新公司、员工列表
	@Command("updateCompany")
	@NotifyChange({ "cobaselist", "namelist", "embaselist" })
	public void updateCompany(@BindingParam("a") CoBaseModel cm,
			@BindingParam("b") Combobox cbcompany,
			@BindingParam("c") Combobox cbemp) {
		cbcompany.setValue("");
		setCobaselist(cm.getCoba_client(), "");
		cbemp.setValue("");
		if (cobaselist.size() > 0) {
			setNamelist("", String.valueOf(cobaselist.get(0).getCid()), "");
			// setEmbaselist("", "", "");
		} else {
			setNamelist("", "", "");
		}

	}

	// 选择公司名称更新员工列表
	@Command("changeCompany")
	@NotifyChange({ "namelist", "embaselist" })
	public void changeCompany(@BindingParam("a") CoBaseModel cm,
			@BindingParam("b") Combobox cbEmp) {
		if (cm != null) {
			cbEmp.setValue("");
			setNamelist("", String.valueOf(cm.getCid()), "");
			setEmbaselist("", String.valueOf(cm.getCid()), "");
		}

	}

	// 选择员工更新员工列表
	@Command("changeEmp")
	@NotifyChange("embaselist")
	public void changeEmp(@BindingParam("a") EmbaseModel em) {
		if (em != null) {
			setEmbaselist("", String.valueOf(em.getCid()),
					String.valueOf(em.getGid()));
		}

	}

	// 输入公司名称、编号、简称
	@Command("editCompany")
	@NotifyChange("cobaselist")
	public void editCompany(@BindingParam("a") InputEvent e,
			@BindingParam("b") Combobox cbclient) {

		String client = "";
		if (cbclient.getSelectedIndex() > 0) {
			client = cbclient.getSelectedItem().getLabel();
			client = client == "全部" ? "" : client;
		}
		setCobaselist(client, e.getValue());

	}

	// 输入员工名称、编号、简称
	@Command("editEmp")
	@NotifyChange("namelist")
	public void editEmp(@BindingParam("a") InputEvent e,
			@BindingParam("b") CoBaseModel cmClient,
			@BindingParam("c") CoBaseModel cmCompany) {
		String client = "";
		String company = "";

		if (cmClient != null) {
			client = cmClient.getCoba_client() == "全部" ? "" : cmClient
					.getCoba_client();
		}
		if (cmCompany != null) {
			company = String.valueOf(cmCompany.getCid());
		}
		setNamelist(client, company, e.getValue());
		setEmbaselist(client, company, e.getValue());

	}

	@Command("filesplit")
	public void filesplit(@BindingParam("a") EmbaseModel ebm,
			@BindingParam("b") Integer t) {
		Integer num = 0;
		switch (t) {
		case 1://档案调入
			num = bll.checkDatum(ebm.getGid(), 0);
			if (num == 0) {
				Map<String, String> map = new HashMap<String, String>();
				map.put("gid", String.valueOf(ebm.getGid()));

				Window window = (Window) Executions.createComponents(
						"Archive_FileImport.zul", null, map);
				window.doModal();
			}else{
				Messagebox.show("已有调入业务,请勿重复提交!", "操作提示", Messagebox.OK,
						Messagebox.ERROR);
			}

			break;
		case 2://档案转出
			num = bll.checkDatum(ebm.getGid(), 5);
			if (num == 0) {
				Map<String, String> map = new HashMap<String, String>();
				map.put("gid", String.valueOf(ebm.getGid()));

				Window window = (Window) Executions.createComponents(
						"Archive_FileCheckOut.zul", null, map);
				window.doModal();
			}else{
				Messagebox.show("已有调入业务,请勿重复提交!", "操作提示", Messagebox.OK,
						Messagebox.ERROR);
			}
			break;
		case 3://查借材料
			num = bll.checkDatum(ebm.getGid(), 1);
			if (num == 0) {
				Map<String, String> map = new HashMap<String, String>();
				map.put("gid", String.valueOf(ebm.getGid()));

				Window window = (Window) Executions.createComponents(
						"Archive_FileConsult.zul", null, map);
				window.doModal();
			}else{
				Messagebox.show("已有调入业务,请勿重复提交!", "操作提示", Messagebox.OK,
						Messagebox.ERROR);
			}
			break;
		case 4://出具证明
			num = bll.checkDatum(ebm.getGid(), 2);
			if (num == 0) {
				Map<String, String> map = new HashMap<String, String>();
				map.put("gid", String.valueOf(ebm.getGid()));

				Window window = (Window) Executions.createComponents(
						"Archive_FileProve.zul", null, map);
				window.doModal();
			}else{
				Messagebox.show("已有调入业务,请勿重复提交!", "操作提示", Messagebox.OK,
						Messagebox.ERROR);
			}
			break;
		case 5://材料鉴别
			num = bll.checkDatum(ebm.getGid(), 3);
			if (num == 0) {
				Map<String, String> map = new HashMap<String, String>();
				map.put("gid", String.valueOf(ebm.getGid()));

				Window window = (Window) Executions.createComponents(
						"Archive_FileFiling.zul", null, map);
				window.doModal();
			}else{
				Messagebox.show("已有调入业务,请勿重复提交!", "操作提示", Messagebox.OK,
						Messagebox.ERROR);
			}
			break;
		case 6://转正定级
			num = bll.checkDatum(ebm.getGid(), 4);
			if (num == 0) {
				Map<String, String> map = new HashMap<String, String>();
				map.put("gid", String.valueOf(ebm.getGid()));

				Window window = (Window) Executions.createComponents(
						"Archive_FilePass.zul", null, map);
				window.doModal();
			}else{
				Messagebox.show("已有调入业务,请勿重复提交!", "操作提示", Messagebox.OK,
						Messagebox.ERROR);
			}
			break;
		case 7:

			break;
		default:
			break;
		}
	}

	public List<CoBaseModel> getClientlist() {
		return clientlist;
	}

	public void setClientlist() {
		this.clientlist = bll.getClientList();
	}

	public void setCobaselist(String client, String name) {
		this.cobaselist = bll.getCobaseList(client, name);
	}

	public List<CoBaseModel> getCobaselist() {
		return cobaselist;
	}

	public List<EmbaseModel> getEmbaselist() {
		return embaselist;
	}

	public void setEmbaselist(String client, String cid, String name) {
		this.embaselist = bll.getEmbaselist(client, cid, name);
	}

	public List<EmbaseModel> getNamelist() {
		return namelist;
	}

	public void setNamelist(String client, String cid, String name) {
		this.namelist = bll.getEmbaseName(client, cid, name);
	}

}
