package Controller.EmBodyCheck;

import java.util.ArrayList;
import java.util.List;

import org.zkoss.bind.annotation.BindingParam;
import org.zkoss.bind.annotation.Command;
import org.zkoss.bind.annotation.NotifyChange;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.Path;
import org.zkoss.zul.Bandbox;
import org.zkoss.zul.Combobox;
import org.zkoss.zul.ListModelList;
import org.zkoss.zul.Listitem;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Window;

import Model.EmBcSetupAddressModel;
import Model.EmBcSetupModel;
import Model.EmBodyCheckItemModel;
import Model.LoginModel;
import bll.EmBodyCheck.EmBcInfo_SelectBll;
import bll.EmBodyCheck.EmbcItem_OperateBll;
import bll.EmBodyCheck.EmbcItem_SelectBll;
import bll.EmBodyCheck.Embc_SetupSellectBll;

public class Embc_ItemInfoUpdateController {
	private List<EmBcSetupModel> setuplist = new ListModelList<>();
	private Embc_SetupSellectBll bll = new Embc_SetupSellectBll();
	private EmbcItem_OperateBll obll = new EmbcItem_OperateBll();
	private EmbcItem_SelectBll sbll = new EmbcItem_SelectBll();
	private EmBodyCheckItemModel eim = (EmBodyCheckItemModel) Executions.getCurrent()
			.getArg().get("model");
	private String selectedsetup = "";
	private EmBcInfo_SelectBll selectbll = new EmBcInfo_SelectBll();
	// 机构地址
	private List<EmBcSetupAddressModel> addresslist = new ArrayList<EmBcSetupAddressModel>();
	// 限制机构地址
	private List<EmBcSetupAddressModel> seaddresslist = new ArrayList<EmBcSetupAddressModel>();

	private Window win = (Window) Path.getComponent("/winItemMod");

	public Embc_ItemInfoUpdateController() {
		setSetuplist();
		if (eim.getEbit_blood() != null) {
			eim.setBlood(eim.getEbit_blood().equals(1) ? "是" : "否");
		}
		if (eim.getEbit_bmain() != null) {
			eim.setBmain(eim.getEbit_bmain().equals(1) ? "是" : "否");
		}
		if (eim.getEbit_package() != null) {
			eim.setPackages(eim.getEbit_package().equals(1) ? "是" : "否");
		}
		if (eim.getEbit_frequency() != null) {
			eim.setFrequency(eim.getEbit_frequency().equals(1) ? "是" : "否");
		}
		if (eim.getEbit_state() != null) {
			eim.setState(eim.getEbit_state().equals(1) ? "生效" : "终止");
		}
		EmBcSetupAddressModel ebam = new EmBcSetupAddressModel();
		ebam.setEbsa_ebcs_id(eim.getEbcs_id());
		ebam.setEbsa_state(1);
		ebam.setEmbc_confirm(1);
		addresslist.addAll(selectbll.getSetUpAddress(ebam));
	}

	@Command
	public void submit() {
		Combobox cbSetup = (Combobox) win.getFellow("setup");
		Combobox cbitemstate = (Combobox) win.getFellow("itemstate");
		Combobox cbpackages = (Combobox) win.getFellow("packages");
		Combobox cbblood = (Combobox) win.getFellow("blood");
		Combobox cbbmain = (Combobox) win.getFellow("bmain");
		Combobox cbsex = (Combobox) win.getFellow("sex");
		Combobox cbfrequency = (Combobox) win.getFellow("frequency");

		if (cbSetup.getSelectedItem() == null) {
			Messagebox.show("请选择体检机构", "提示", Messagebox.OK, Messagebox.ERROR);
			return;
		} else {
			eim.setEbit_hospital((Integer) cbSetup.getSelectedItem().getValue());
		}

		if (eim.getEbit_name() == null || eim.getEbit_name().equals("")) {
			Messagebox.show("请输入体检项目名称", "提示", Messagebox.OK, Messagebox.ERROR);
			return;
		} else {
			if (sbll.haveItem(eim.getEbit_name(), eim.getEbit_id(),
					eim.getEbcs_id())) {
				Messagebox.show("体检项目已存在", "提示", Messagebox.OK,
						Messagebox.ERROR);
				return;
			}
		}

		if (eim.getEbit_charge() == null) {
			Messagebox.show("请输入体检项目金额", "提示", Messagebox.OK, Messagebox.ERROR);
			return;
		}

		if (eim.getEbit_discount() == null) {
			Messagebox.show("请输入体检项目折扣金额", "提示", Messagebox.OK,
					Messagebox.ERROR);
			return;
		}

		// 项目状态
		if (cbitemstate.getSelectedItem() != null) {
			eim.setEbit_state(Integer.valueOf(cbitemstate.getSelectedItem()
					.getValue().toString()));
		}

		// 是否套餐项目
		if (cbpackages.getSelectedItem() != null) {
			eim.setEbit_package(Integer.valueOf(cbpackages.getSelectedItem()
					.getValue().toString()));
		}
		// 抽血项目
		if (cbblood.getSelectedItem() != null) {
			eim.setEbit_blood(Integer.valueOf(cbblood.getSelectedItem()
					.getValue().toString()));
		}

		// 是否选了抽血项目就要自动选择
		if (cbbmain.getSelectedItem() != null) {
			eim.setEbit_bmain(Integer.valueOf(cbbmain.getSelectedItem()
					.getValue().toString()));
		}

		// 性别
		if (cbsex.getSelectedItem() != null) {
			eim.setEbit_sex(Integer.valueOf(cbsex.getSelectedItem().getValue()
					.toString()));
			if (cbsex.getValue().equals("已婚")) {
				eim.setEbit_marry(1);
			} else {
				eim.setEbit_marry(0);
			}
		}

		// 使用频率
		if (cbfrequency.getSelectedItem() != null) {
			eim.setEbit_frequency(Integer.valueOf(cbfrequency.getSelectedItem()
					.getValue().toString()));
		}

		Integer i = obll.EmBodyCheckItemUpdate(eim);
		if (i > 0) {
			Messagebox
					.show("提交成功", "提示", Messagebox.OK, Messagebox.INFORMATION);
			win.detach();
		} else {
			Messagebox.show("提交失败", "提示", Messagebox.OK, Messagebox.ERROR);
		}

	}

	// 限定医院地址
	@Command
	@NotifyChange("selectedsetup")
	public void checkname(@BindingParam("bd") Bandbox bx,
			@BindingParam("model") EmBcSetupAddressModel model,
			@BindingParam("lbitem") Listitem item) {
		if (item.isSelected()) {
			seaddresslist.add(model);
		} else {
			seaddresslist.remove(model);
		}
		getSelectname();
	}

	@Command
	@NotifyChange({"addresslist","selectedsetup"})
	public void selectstup(@BindingParam("setup") Combobox setup) {
		if (setup.getValue() != null) {
			Integer ebcs_id=setup.getSelectedItem().getValue();
			EmBcSetupAddressModel ebam =new EmBcSetupAddressModel();
			ebam.setEbsa_ebcs_id(ebcs_id);
			ebam.setEbsa_state(1);
			ebam.setEmbc_confirm(1);
			addresslist=selectbll.getSetUpAddress(ebam);
			if(seaddresslist.size()>0)
			{
				seaddresslist.clear();
			}
			selectedsetup = "";
		}
	}

	// 把已选择的医院地址串起来
	private void getSelectname() {
		selectedsetup = "";
		for (EmBcSetupAddressModel m : seaddresslist) {
			if (selectedsetup == "") {
				selectedsetup = m.getEbsa_address();
			} else {
				selectedsetup = selectedsetup + ";" + m.getEbsa_address();
			}
		}
	}

	public EmBodyCheckItemModel getEim() {
		return eim;
	}

	public void setEim(EmBodyCheckItemModel eim) {
		this.eim = eim;
	}

	public List<EmBcSetupModel> getSetuplist() {
		return setuplist;
	}

	public String getSelectedsetup() {
		return selectedsetup;
	}

	public void setSelectedsetup(String selectedsetup) {
		this.selectedsetup = selectedsetup;
	}

	public List<EmBcSetupAddressModel> getAddresslist() {
		return addresslist;
	}

	public void setAddresslist(List<EmBcSetupAddressModel> addresslist) {
		this.addresslist = addresslist;
	}

	public void setSetuplist() {
		EmBcSetupModel em = new EmBcSetupModel();
		// em.setEbcs_state(1);
		this.setuplist = bll.getEmBcSetupList(em);
	}

}
