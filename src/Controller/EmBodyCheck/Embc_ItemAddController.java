package Controller.EmBodyCheck;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.zkoss.bind.annotation.Command;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.Path;
import org.zkoss.zk.ui.event.EventListener;
import org.zkoss.zul.Combobox;
import org.zkoss.zul.ListModelList;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Window;
import org.zkoss.zul.Messagebox.ClickEvent;

import Model.EmBcSetupModel;
import Model.EmBodyCheckItemModel;
import bll.EmBodyCheck.EmbcItem_OperateBll;
import bll.EmBodyCheck.EmbcItem_SelectBll;
import bll.EmBodyCheck.Embc_SetupSellectBll;

public class Embc_ItemAddController {
	private List<EmBcSetupModel> setuplist = new ListModelList<>();
	Embc_SetupSellectBll bll = new Embc_SetupSellectBll();
	EmbcItem_OperateBll obll = new EmbcItem_OperateBll();
	EmbcItem_SelectBll sbll = new EmbcItem_SelectBll();
	EmBodyCheckItemModel eim = new EmBodyCheckItemModel();

	private Window win = (Window) Path.getComponent("/bcWinadd");

	public Embc_ItemAddController() {
		setSetuplist();

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
			if (sbll.haveItem(eim.getEbit_name(), null, eim.getEbit_hospital())) {
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
		if (eim.getEbit_charge().subtract(eim.getEbit_discount())
				.compareTo(BigDecimal.ZERO) < 0) {
			Messagebox.show("体检项目金额必须大于折扣金额", "提示", Messagebox.OK,
					Messagebox.ERROR);
			return;
		}

		if (cbitemstate.getSelectedItem() != null) {
			eim.setEbit_state(Integer.valueOf(cbitemstate.getSelectedItem()
					.getValue().toString()));
		}

		if (cbpackages.getSelectedItem() != null) {
			eim.setEbit_package(Integer.valueOf(cbpackages.getSelectedItem()
					.getValue().toString()));
		}
		if (cbblood.getSelectedItem() != null) {
			eim.setEbit_blood(Integer.valueOf(cbblood.getSelectedItem()
					.getValue().toString()));
		}
		if (cbbmain.getSelectedItem() != null) {
			eim.setEbit_bmain(Integer.valueOf(cbbmain.getSelectedItem()
					.getValue().toString()));
		}
		if (cbsex.getSelectedItem() != null) {
			eim.setEbit_sex(Integer.valueOf(cbsex.getSelectedItem().getValue()
					.toString()));
			if (cbsex.getValue().equals("已婚")) {
				eim.setEbit_marry(1);
			} else {
				eim.setEbit_marry(0);
			}
		}
		if (cbfrequency.getSelectedItem() != null) {
			eim.setEbit_frequency(Integer.valueOf(cbfrequency.getSelectedItem()
					.getValue().toString()));
		}

		Messagebox.show("确认提交数据?", "操作提示", new Messagebox.Button[] {
				Messagebox.Button.OK, Messagebox.Button.CANCEL },
				Messagebox.QUESTION,
				new EventListener<Messagebox.ClickEvent>() {
					@Override
					public void onEvent(ClickEvent event) throws Exception {
						// TODO Auto-generated method stub

						if (Messagebox.Button.OK.equals(event.getButton())) {
							Integer i = obll.EmBodyCheckItemAdd(eim);
							if (i > 0) {
								Messagebox.show("提交成功", "提示", Messagebox.OK,
										Messagebox.INFORMATION);
								win.detach();
								Map map = new HashMap<>();
								map.put("id", i);
								Window window = (Window) Executions
										.createComponents(
												"Embc_ItemManagerList.zul",
												null, map);
								window.doModal();
							} else {
								Messagebox.show("提交失败", "提示", Messagebox.OK,
										Messagebox.ERROR);
							}
						}
					}
				});
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

	public void setSetuplist() {
		EmBcSetupModel em = new EmBcSetupModel();
		// em.setEbcs_state(1);
		this.setuplist = bll.getEmBcSetupList(em);
	}
}
