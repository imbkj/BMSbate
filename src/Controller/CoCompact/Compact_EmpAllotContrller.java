package Controller.CoCompact;

import java.util.List;

import org.zkoss.bind.annotation.BindingParam;
import org.zkoss.bind.annotation.Command;
import org.zkoss.bind.annotation.NotifyChange;
import org.zkoss.zk.ui.event.EventListener;
import org.zkoss.zul.Cell;
import org.zkoss.zul.Checkbox;
import org.zkoss.zul.Combobox;
import org.zkoss.zul.Comboitem;
import org.zkoss.zul.Datebox;
import org.zkoss.zul.Grid;
import org.zkoss.zul.Image;
import org.zkoss.zul.ListModelList;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Textbox;
import org.zkoss.zul.Window;
import org.zkoss.zul.Messagebox.ClickEvent;

import bll.CoCompact.Compact_EmpAllotBll;

import Model.CoBaseModel;
import Model.CoCompactModel;
import Model.CoOfferListModel;
import Model.CoOfferModel;
import Model.EmbaseModel;
import Util.DateStringChange;

public class Compact_EmpAllotContrller {
	private List<CoBaseModel> cobaseList = new ListModelList<>();
	private List<CoBaseModel> clientList = new ListModelList<>();
	private List<EmbaseModel> embaseList = new ListModelList<>();
	private List<CoCompactModel> compactList = new ListModelList<>();
	private List<CoOfferModel> coofferList = new ListModelList<>();
	private List<CoOfferListModel> itemList = new ListModelList<>();

	private Compact_EmpAllotBll bll = new Compact_EmpAllotBll();

	private Window win;

	private Integer num = 0;

	public Compact_EmpAllotContrller() {
		clientList = bll.SearchClient(null, null, null, false);
		cobaseList = bll.SearchCobase(null, null, null, false);
	}

	@Command
	public void sWin(@BindingParam("a") Window w) {

		win = w;
	}

	@Command
	@NotifyChange({ "cobaseList", "compactList", "coofferList", "embaseList",
			"itemList" })
	public void searchCobase() {
		Combobox cbCompany = (Combobox) win.getFellow("company");
		Combobox cbClient = (Combobox) win.getFellow("client");
		Combobox cbCompact = (Combobox) win.getFellow("compact");
		Combobox cbCooffer = (Combobox) win.getFellow("cooffer");
		Integer cid = null;
		boolean fuzzy = false;
		if (cbCompany.getSelectedItem() != null) {
			cid = cbCompany.getSelectedItem().getValue();
		} else {
			if (!cbCompany.getValue().equals("")) {
				fuzzy = true;
			}
		}
		cobaseList = bll.SearchCobase(cid, cbCompany.getValue(),
				cbClient.getValue(), fuzzy);
		searchCompact();

		if (cbCompany.getValue().equals("")) {
			compactList = null;
			coofferList = null;
			embaseList = null;
			itemList = null;
			cbCompact.setValue("");
			cbCooffer.setValue("");
		} else {
			if (cbCompany.getSelectedItem() != null) {
				searchEmbase();
				if (compactList.size() > 0) {
					searchCooffer();
				}
				searchItemList();
			}
		}

	}

	@Command
	@NotifyChange("embaseList")
	public void searchEmbase() {

		Combobox cbCompany = (Combobox) win.getFellow("company");
		Textbox tbEmp = (Textbox) win.getFellow("emp");
		Integer cid = null;
		String name = tbEmp.getValue();
		if (cbCompany.getSelectedItem() != null) {
			cid = cbCompany.getSelectedItem().getValue();
			embaseList = bll.SearchEmbase(cid, name);
		} else {
			Comboitem ci = new Comboitem();

			if (cbCompany.getChildren().size() > 0) {
				ci = (Comboitem) cbCompany.getChildren().get(0);

				ci.getLabel().equals(cbCompany.getValue());
				if (ci.getLabel().equals(cbCompany.getValue())
						&& !cbCompany.getValue().equals("")) {
					cbCompany.setSelectedIndex(0);
					cid = cbCompany.getSelectedItem().getValue();
					embaseList = bll.SearchEmbase(cid, name);
				}
			}

		}
	}

	@Command
	@NotifyChange({ "coofferList" })
	public void searchCompact() {
		Combobox cbCompany = (Combobox) win.getFellow("company");
		Combobox cbCompact = (Combobox) win.getFellow("compact");
		Combobox cbCooffer = (Combobox) win.getFellow("cooffer");
		Integer cid = null;
		Integer id = null;
		if (cbCompany.getSelectedItem() != null) {
			cid = cbCompany.getSelectedItem().getValue();
		}
		if (cbCompact.getSelectedItem() != null) {
			id = cbCompact.getSelectedItem().getValue();
		}
		if (cid != null) {
			compactList = bll.SearchCompact(id, cid);
		}

		searchCooffer();
		if (coofferList != null && coofferList.size() > 0) {
			cbCooffer.setValue(coofferList.get(0).getCoof_name());
		} else {
			cbCooffer.setValue("");
		}
	}

	@Command
	@NotifyChange({ "coofferList", "itemList" })
	public void searchCooffer() {
		Combobox cbCompact = (Combobox) win.getFellow("compact");
		Combobox cbCooffer = (Combobox) win.getFellow("cooffer");
		cbCooffer.setValue("");
		if (cbCompact.getSelectedItem() != null) {
			coofferList = bll.SearchCooffer((Integer) cbCompact
					.getSelectedItem().getValue());
			searchItemList();
		}

	}

	@Command
	@NotifyChange("itemList")
	public void searchItemList() {
		Combobox cbCompany = (Combobox) win.getFellow("company");
		Combobox cbCompact = (Combobox) win.getFellow("compact");
		Combobox cbCooffer = (Combobox) win.getFellow("cooffer");
		Textbox tbItem = (Textbox) win.getFellow("item");
		Integer cid = null;
		Integer coId = null;
		Integer coofId = null;
		if (cbCompany.getSelectedItem() != null) {
			cid = cbCompany.getSelectedItem().getValue();
		} else {
			if (cbCompany.getChildren().size() > 0) {
				Comboitem ci = new Comboitem();
				ci = (Comboitem) cbCompany.getChildren().get(0);

				ci.getLabel().equals(cbCompany.getValue());
				if (ci.getLabel().equals(cbCompany.getValue())
						&& !cbCompany.getValue().equals("")) {
					cbCompany.setSelectedIndex(0);
					cid = cbCompany.getSelectedItem().getValue();
				}
			}
		}
		if (cid != null) {

			if (cbCompact.getSelectedItem() != null) {
				coId = cbCompact.getSelectedItem().getValue();
				if (cbCooffer.getSelectedItem() != null) {
					coofId = cbCooffer.getSelectedItem().getValue();
				}
			}
			itemList = bll.SearchItem(cid,coId, coofId, tbItem.getValue());
		}
	}

	@Command
	public void updateDate(@BindingParam("a") CoOfferListModel cm,
			@BindingParam("b") Datebox db, @BindingParam("c") Integer i) {
		if (db.getValue() != null) {
			if (i.equals(1)) {
				cm.setSt2(Integer.valueOf(DateStringChange.DatetoSting(
						db.getValue(), "yyyyMM")));

			} else {
				cm.setSt(Integer.valueOf(DateStringChange.DatetoSting(
						db.getValue(), "yyyyMM")));
			}
		}
	}

	@Command
	public void copyDate(@BindingParam("a") CoOfferListModel cm,
			@BindingParam("b") Image img, @BindingParam("c") Integer rowIndex,
			@BindingParam("d") Integer cellIndex) {
		Grid gd = (Grid) win.getFellow("gd");
		Datebox db;
		if (cellIndex.equals(5)) {
			db = (Datebox) gd.getCell(rowIndex, cellIndex).getChildren().get(0)
					.getChildren().get(0);
		} else {
			db = (Datebox) gd.getCell(rowIndex, cellIndex).getChildren().get(0)
					.getChildren().get(0);
		}
		if (db.getValue() != null) {

			for (int i = rowIndex; i < gd.getRows().getChildren().size(); i++) {
				Datebox db2 = (Datebox) gd.getCell(i, cellIndex).getChildren()
						.get(0).getChildren().get(0);
				db2.setValue(db.getValue());
				if (cellIndex.equals(5)) {
					itemList.get(i).setSt2(cm.getSt2());
				} else {
					itemList.get(i).setSt(cm.getSt());
				}

			}
		}
	}

	@Command
	public void checkAll() {
		Checkbox cb = (Checkbox) win.getFellow("cba");
		Grid gd = (Grid) win.getFellow("gd");
		Checkbox ck;
		for (int i = 0; i < gd.getRows().getChildren().size(); i++) {
			ck = (Checkbox) gd.getCell(i, 7).getChildren().get(0);
			ck.setChecked(cb.isChecked());
			itemList.get(i).setChecked(cb.isChecked());
		}
	}

	@Command
	@NotifyChange("num")
	public void checkAllEmp() {
		Checkbox cb = (Checkbox) win.getFellow("cka");
		Grid gd = (Grid) win.getFellow("gdEmp");
		Checkbox ck;
		for (int i = 0; i < gd.getRows().getChildren().size(); i++) {
			ck = (Checkbox) gd.getCell(i, 0).getChildren().get(0);
			ck.setChecked(cb.isChecked());
			embaseList.get(i).setChecked(cb.isChecked());
			num = cb.isChecked() ? gd.getRows().getChildren().size() : 0;
		}
	}

	@Command
	@NotifyChange("num")
	public void sum(@BindingParam("a") Checkbox ck) {
		if (ck.isChecked()) {
			num++;
		} else {
			num--;
		}
	}

	@Command
	@NotifyChange("embaseList")
	public void submit() {
		boolean b = false;
		for (int i = 0; i < itemList.size(); i++) {
			if (itemList.get(i).isChecked()) {
				if (itemList.get(i).getSt() == null
						|| itemList.get(i).getSt2() == null) {
					Messagebox
							.show("请选择[" + itemList.get(i).getColi_name()
									+ "]起始时间.", "操作提示", Messagebox.OK,
									Messagebox.ERROR);
					return;
				}
				b = true;
			}
		}

		if (!b) {
			Messagebox.show("请选择产品!", "操作提示", Messagebox.OK, Messagebox.ERROR);
			return;
		}
		if (num.equals(0)) {
			Messagebox.show("请选择员工!", "操作提示", Messagebox.OK, Messagebox.ERROR);
			return;
		}
		EventListener<ClickEvent> clickListener = new EventListener<Messagebox.ClickEvent>() {
			public void onEvent(ClickEvent event) throws Exception {
				if (Messagebox.Button.OK.equals(event.getButton())) {
					boolean b = bll.add(embaseList, itemList);
					if (b) {
						Combobox cbCompany = (Combobox) win
								.getFellow("company");
						embaseList = bll.SearchEmbase(
								Integer.valueOf(cbCompany.getSelectedItem()
										.getValue().toString()), null);
						Messagebox.show("提交成功!", "操作提示", Messagebox.OK,
								Messagebox.INFORMATION);
					} else {
						Messagebox.show("提交失败!", "操作提示", Messagebox.OK,
								Messagebox.ERROR);
					}
				}
			}
		};
		Messagebox.show("确认提交数据?", "操作提示", new Messagebox.Button[] {
				Messagebox.Button.OK, Messagebox.Button.CANCEL },
				Messagebox.QUESTION, clickListener);
	}

	public List<CoBaseModel> getCobaseList() {
		return cobaseList;
	}

	public void setCobaseList(List<CoBaseModel> cobaseList) {
		this.cobaseList = cobaseList;
	}

	public List<CoBaseModel> getClientList() {
		return clientList;
	}

	public void setClientList(List<CoBaseModel> clientList) {
		this.clientList = clientList;
	}

	public List<EmbaseModel> getEmbaseList() {
		return embaseList;
	}

	public void setEmbaseList(List<EmbaseModel> embaseList) {
		this.embaseList = embaseList;
	}

	public List<CoCompactModel> getCompactList() {
		return compactList;
	}

	public void setCompactList(List<CoCompactModel> compactList) {
		this.compactList = compactList;
	}

	public List<CoOfferModel> getCoofferList() {
		return coofferList;
	}

	public void setCoofferList(List<CoOfferModel> coofferList) {
		this.coofferList = coofferList;
	}

	public List<CoOfferListModel> getItemList() {
		return itemList;
	}

	public void setItemList(List<CoOfferListModel> itemList) {
		this.itemList = itemList;
	}

	public Integer getNum() {
		return num;
	}

	public void setNum(Integer num) {
		this.num = num;
	}

}
