package Controller.EmBenefit;

import java.text.SimpleDateFormat;
import java.util.List;

import org.zkoss.bind.annotation.Command;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.Path;
import org.zkoss.zul.Combobox;
import org.zkoss.zul.Datebox;
import org.zkoss.zul.Intbox;
import org.zkoss.zul.ListModelList;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Window;

import bll.EmBenefit.EmBenefit_addBll;

import Model.EmBenefitModel;
import Util.UserInfo;

public class EmBenefit_addController {
	private List<EmBenefitModel> nameList = new ListModelList<>();
	private EmBenefit_addBll bll = new EmBenefit_addBll();
	private Window win = (Window) Path.getComponent("/winEW");
	private EmBenefitModel ebfm = new EmBenefitModel();

	private String username = UserInfo.getUsername();

	public EmBenefit_addController() {
		setNameList("");
	}

	@Command("submit")
	public void submit() {
		Combobox cbName = (Combobox) win.getFellow("name");
		Datebox dbstart = (Datebox) win.getFellow("start");
		Combobox cbType = (Combobox) win.getFellow("aroundType");
		Intbox ibCycle = (Intbox) win.getFellow("cycle");
		Combobox cbUnit = (Combobox) win.getFellow("unit");
		Combobox cbmold = (Combobox) win.getFellow("mold");

		if (cbName.getValue()!= null && !cbName.getValue().equals("")) {

			ebfm.setEmbf_name(cbName.getValue());

			if (dbstart.getValue() != null && !dbstart.getValue().equals("")) {
				SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
				ebfm.setEmbf_start(sdf.format(dbstart.getValue()));
			}
			ebfm.setEmbf_type(cbType.getValue().equals("是") ? 1 : 0);
			ebfm.setEmbf_cycle(ibCycle.getValue());
			ebfm.setEmbf_unit(cbUnit.getValue());
			ebfm.setEmbf_mold(cbmold.getValue());
			ebfm.setEmbf_addname(username);
			if (bll.add(ebfm) > 0) {
				Messagebox.show("添加成功", "操作提示", Messagebox.OK,
						Messagebox.INFORMATION);
				Executions.sendRedirect("/EmBenefit/EmBenefit_add.zul");
			} else {
				Messagebox
						.show("添加失败", "操作提示", Messagebox.OK, Messagebox.ERROR);
			}

		} else {
			Messagebox.show("请输入福利项目名称", "操作提示", Messagebox.OK,
					Messagebox.ERROR);
		}
	}

	public List<EmBenefitModel> getNameList() {
		return nameList;
	}

	public void setNameList(String name) {
		this.nameList = bll.getNameList(name);
	}

	public EmBenefitModel getEbfm() {
		return ebfm;
	}

	public void setEbfm(EmBenefitModel ebfm) {
		this.ebfm = ebfm;
	}

}
