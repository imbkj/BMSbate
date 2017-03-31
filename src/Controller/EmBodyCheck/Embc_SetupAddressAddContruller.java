package Controller.EmBodyCheck;

import org.zkoss.bind.annotation.BindingParam;
import org.zkoss.bind.annotation.Command;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.Path;
import org.zkoss.zul.Grid;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Textbox;
import org.zkoss.zul.Window;

import bll.EmBodyCheck.EmBcSetup_OperateBll;

import Model.EmBcSetupAddressModel;
import Model.EmBcSetupModel;
import Util.UserInfo;

public class Embc_SetupAddressAddContruller {
	EmBcSetupModel frommodel = (EmBcSetupModel) Executions.getCurrent()
			.getArg().get("model");

	private Window win = (Window) Path.getComponent("/addresswin");

	@Command
	public void addressAdd(@BindingParam("addresswin") Window addresswin,
			@BindingParam("gd") Grid gd, @BindingParam("num") Integer num) {
		EmBcSetup_OperateBll bll = new EmBcSetup_OperateBll();
		int k = 0;
		for (int i = 1; i <= num; i++) {

			Textbox tx = (Textbox) gd.getCell(i, 1).getChildren().get(0);
			if (tx.getValue() != null && !tx.getValue().equals("")
					&& tx.getValue() != "") {
				EmBcSetupAddressModel model = new EmBcSetupAddressModel();
				model.setEbsa_ebcs_id(frommodel.getEbcs_id());
				model.setEbsa_addname(UserInfo.getUsername());
				model.setEbsa_address(tx.getValue());
				int n = bll.AddEmBcSetupAddress(model);
				if (n > 0) {
					k++;
				}
			}
		}
		if (k > 0) {
			Messagebox.show("成功新增" + k + "条机构地址信息", "提示", Messagebox.OK,
					Messagebox.INFORMATION);
			win.detach();
		} else {
			Messagebox.show("提交失败", "提示", Messagebox.OK, Messagebox.ERROR);
		}
	}

}
