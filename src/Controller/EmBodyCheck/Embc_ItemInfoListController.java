package Controller.EmBodyCheck;

import java.io.File;
import java.io.FileNotFoundException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.zkoss.bind.annotation.BindingParam;
import org.zkoss.bind.annotation.Command;
import org.zkoss.bind.annotation.NotifyChange;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.Path;
import org.zkoss.zul.Cell;
import org.zkoss.zul.Checkbox;
import org.zkoss.zul.Combobox;
import org.zkoss.zul.Filedownload;
import org.zkoss.zul.Grid;
import org.zkoss.zul.ListModelList;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Window;

import Model.EmBcSetupModel;
import Model.EmBodyCheckItemModel;
import Model.loginroleModel;
import Util.FileOperate;
import Util.UserInfo;
import Util.pinyin4jUtil;
import bll.EmBodyCheck.EmbcItem_SelectBll;

public class Embc_ItemInfoListController {
	EmbcItem_SelectBll bll = new EmbcItem_SelectBll();
	private List<EmBodyCheckItemModel> itemlist = new ListModelList<>();
	private List<EmBcSetupModel> setupList = new ListModelList<>();
	private List<EmBodyCheckItemModel> eiList = new ListModelList<>();
	String oldsql = "";
	private boolean fee = false;

	private Window win = (Window) Path.getComponent("/bcWin");

	public Embc_ItemInfoListController() {
		Integer id = 0;
		oldsql = " and ebcs_state=1 and a.ebit_state=1";
		if (Executions.getCurrent().getArg().get("id") != null) {
			id = Integer.valueOf(Executions.getCurrent().getArg().get("id")
					.toString());
			oldsql = "and a.ebit_id=" + id;
		}
		setItemlist(oldsql);
		EmBcSetupModel ebsm = new EmBcSetupModel();

		setSetupList(ebsm);
		EmBodyCheckItemModel ebim = new EmBodyCheckItemModel();
		ebim.setEbit_state(1);
		setEiList(ebim);
		fee = bll.feePermission(Integer.valueOf(UserInfo.getUserid()));
	}

	@Command
	@NotifyChange("eiList")
	public void updateSelect() {
		Combobox cbSetup = (Combobox) win.getFellow("setup");
		Combobox cbSetupState = (Combobox) win.getFellow("setupState");
		Combobox cbItemname = (Combobox) win.getFellow("itemname");
		Combobox cbitemState = (Combobox) win.getFellow("itemState");
		EmBodyCheckItemModel ebim = new EmBodyCheckItemModel();

		if (cbSetup.getSelectedItem() != null) {
			ebim.setEbit_hospital(Integer.valueOf(cbSetup.getSelectedItem()
					.getValue().toString()));
		}
		if (cbItemname.getValue() != null) {
			ebim.setEbit_name(cbItemname.getValue());
		}
		if (cbSetupState.getSelectedItem() != null) {
			ebim.setEbcs_state(Integer.valueOf(cbSetupState.getSelectedItem()
					.getValue().toString()));
		}
		if (cbitemState.getSelectedItem() != null) {
			ebim.setEbit_state(Integer.valueOf(cbitemState.getSelectedItem()
					.getValue().toString()));
		}

		setEiList(ebim);
	}

	// 查询
	@Command
	@NotifyChange("itemlist")
	public void search() {
		Combobox cbSetup = (Combobox) win.getFellow("setup");
		Combobox cbItemname = (Combobox) win.getFellow("itemname");
		Combobox cbsetupState = (Combobox) win.getFellow("setupState");
		Combobox cbitemState = (Combobox) win.getFellow("itemState");

		String sql = "";
		if (cbSetup != null && !cbSetup.getValue().equals("")) {
			sql = sql + " and b.ebcs_hospital like '" + cbSetup.getValue()
					+ "'";
		}
		if (cbItemname != null && !cbItemname.getValue().equals("")) {
			sql = sql + " and ebit_name like '%" + cbItemname.getValue() + "%'";
		}
		if (cbsetupState.getSelectedItem() != null) {
			sql = sql + " and ebcs_state="
					+ cbsetupState.getSelectedItem().getValue();
		}

		if (cbitemState.getSelectedItem() != null) {
			sql = sql + " and ebit_state="
					+ cbitemState.getSelectedItem().getValue();
		}
		oldsql = sql;
		setItemlist(oldsql);
	}

	// 体检项目更新
	@Command
	@NotifyChange("itemlist")
	public void itemupdate(@BindingParam("model") EmBodyCheckItemModel model) {
		Map map = new HashMap<>();
		map.put("model", model);
		Window window = (Window) Executions.createComponents(
				"Embc_ItemInfoUpdate.zul", null, map);
		window.doModal();
		setItemlist(oldsql);
	}

	// 全选
	@Command
	public void checkall(@BindingParam("gd") Grid gd,
			@BindingParam("ck") Checkbox ck) {
		for (int i = 0; i < gd.getPageSize(); i++) {
			if (gd.getCell(i, 7) != null) {
				Cell cell = (Cell) gd.getCell(i, 7);
				if (cell.getChildren().size() > 0
						&& cell.getChildren().get(0) != null) {
					Checkbox cb = (Checkbox) cell.getChildren().get(0);
					cb.setChecked(ck.isChecked());
				}
			}
		}
	}

	// 全选（管理页面）
	@Command
	public void checkallpix(@BindingParam("gd") Grid gd,
			@BindingParam("ck") Checkbox ck) {
		for (int i = 0; i < gd.getPageSize(); i++) {
			if (gd.getCell(i, 9) != null) {
				Cell cell = (Cell) gd.getCell(i, 9);
				if (cell.getChildren().size() > 0
						&& cell.getChildren().get(0) != null) {
					Checkbox cb = (Checkbox) cell.getChildren().get(0);
					cb.setChecked(ck.isChecked());
				}
			}
		}
	}

	@Command
	// (前道)
	public void export(@BindingParam("gd") Grid gd) {
		List<EmBodyCheckItemModel> ilist = new ListModelList<>();
		for (int i = 0; i < gd.getPageSize(); i++) {
			if (gd.getCell(i, 7) != null) {
				Cell cell = (Cell) gd.getCell(i, 7);
				if (cell.getChildren().size() > 0
						&& cell.getChildren().get(0) != null) {
					Checkbox cb = (Checkbox) cell.getChildren().get(0);
					if (cb.isChecked()) {
						EmBodyCheckItemModel m = cb.getValue();
						ilist.add(m);
					}
				}
			}
		}
		Combobox cbSetup = (Combobox) win.getFellow("setup");
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
		Date trialTime = new Date();
		String exfileName = sdf.format(trialTime) + "_Item_"
				+ pinyin4jUtil.getPinYinHeadChar(UserInfo.getUsername())
				+ ".xls";

		if (cbSetup.getSelectedItem() != null) {

			if (bll.precreatefile(
					ilist,
					Integer.valueOf(cbSetup.getSelectedItem().getValue()
							.toString()), exfileName, fee)) {

				String absolutePath = FileOperate.getAbsolutePath();
				try {
					Filedownload.save(new File(absolutePath
							+ "OfficeFile/DownLoad/EmBodyCheck/Item/"
							+ exfileName), null);
				} catch (FileNotFoundException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			} else {
				Messagebox.show("文件生成失败!", "操作提示", Messagebox.OK,
						Messagebox.ERROR);
			}
		} else {
			Messagebox
					.show("请选择体检机构!", "操作提示", Messagebox.OK, Messagebox.ERROR);
		}
	}

	@Command
	// (后道)
	public void exportpix(@BindingParam("gd") Grid gd) {
		List<EmBodyCheckItemModel> ilist = new ListModelList<>();
		boolean b = false;
		for (int i = 0; i < gd.getPageSize(); i++) {
			if (gd.getCell(i, 9) != null) {
				Cell cell = (Cell) gd.getCell(i, 9);
				if (cell.getChildren().size() > 0
						&& cell.getChildren().get(0) != null) {
					Checkbox cb = (Checkbox) cell.getChildren().get(0);
					if (cb.isChecked()) {
						b = true;
						EmBodyCheckItemModel m = cb.getValue();
						ilist.add(m);
					}
				}
			}
		}
		Combobox cbSetup = (Combobox) win.getFellow("setup");
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
		Date trialTime = new Date();
		String exfileName = sdf.format(trialTime) + "_Item_"
				+ pinyin4jUtil.getPinYinHeadChar(UserInfo.getUsername())
				+ ".xls";

		if (cbSetup.getSelectedItem() != null) {
			if (!b) {
				for (EmBodyCheckItemModel m : itemlist) {
					ilist.add(m);
				}
			}
			if (bll.pixcreatefile(
					ilist,
					Integer.valueOf(cbSetup.getSelectedItem().getValue()
							.toString()), exfileName, fee)) {

				String absolutePath = FileOperate.getAbsolutePath();
				try {
					Filedownload.save(new File(absolutePath
							+ "OfficeFile/DownLoad/EmBodyCheck/Item/"
							+ exfileName), null);
				} catch (FileNotFoundException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			} else {
				Messagebox.show("文件生成失败!", "操作提示", Messagebox.OK,
						Messagebox.ERROR);
			}
		} else {
			Messagebox
					.show("请选择体检机构!", "操作提示", Messagebox.OK, Messagebox.ERROR);
		}
	}

	public List<EmBodyCheckItemModel> getItemlist() {
		return itemlist;
	}

	public void setItemlist(String sql) {
		this.itemlist = bll.getEmBcItemInfo(sql);
	}

	public boolean isFee() {
		return fee;
	}

	public void setFee(boolean fee) {
		this.fee = fee;
	}

	public List<EmBcSetupModel> getSetupList() {
		return setupList;
	}

	public void setSetupList(EmBcSetupModel em) {
		this.setupList = bll.getEmbcSetUp(em);
	}

	public List<EmBodyCheckItemModel> getEiList() {
		return eiList;
	}

	public void setEiList(EmBodyCheckItemModel em) {
		em.setTop(true);
		em.setTopNum(10);
		this.eiList = bll.getEmbcItem(em);
	}

}
