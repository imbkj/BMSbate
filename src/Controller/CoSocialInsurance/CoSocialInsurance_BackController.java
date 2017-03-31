package Controller.CoSocialInsurance;

import org.zkoss.bind.annotation.BindingParam;
import org.zkoss.bind.annotation.Command;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Window;

import bll.CoSocialInsurance.CoSocialInsurance_ListBll;
import bll.CoSocialInsurance.CoSocialInsurance_OperateBll;
import Model.CoShebaoChangeModel;
import Util.UserInfo;

public class CoSocialInsurance_BackController {

	private CoShebaoChangeModel m = new CoShebaoChangeModel();
	Integer daid = 0;

	public CoSocialInsurance_BackController() {
		try {
			CoSocialInsurance_ListBll bll = new CoSocialInsurance_ListBll();

			daid = Integer.parseInt(Executions.getCurrent().getArg()
					.get("daid").toString());

			setM(bll.getCoShebaoChangeInfo(daid));
			m.setPbsr_addname(UserInfo.getUsername());
		} catch (Exception e) {
			e.printStackTrace();
			Messagebox
					.show("页面加载出错!", "ERROR", Messagebox.OK, Messagebox.ERROR);
		}
	}

	@Command("submit")
	public void submit(@BindingParam("win") Window win) {
		if (m.getPbsr_remark() == null || m.getPbsr_remark().isEmpty()) {
			Messagebox.show("请输入原因!", "ERROR", Messagebox.OK, Messagebox.ERROR);
		} else {
			try {
				String type = "";
				switch (m.getCsbc_addtype()) {
				case "缴存登记":
					type = "csoiadd";
					break;
				case "账户接管":
					type = "csoiadd1";
					break;
				case "信息变更":
					type = "csoichange";
					break;
				case "账户注销":
					type = "csoican";
					break;
				case "管理终止":
					type = "csoiter";
					break;
				default:
					break;
				}
				m.setType(type);

				String[] str = new CoSocialInsurance_OperateBll().back(m);
				if (str[0].equals("1")) {
					Messagebox.show(str[1], "INFORMATION", Messagebox.OK,
							Messagebox.INFORMATION);
					win.detach();
				} else {
					Messagebox.show(str[1], "ERROR", Messagebox.OK,
							Messagebox.ERROR);
				}
			} catch (Exception e) {
				e.printStackTrace();
				Messagebox.show("提交出错!", "ERROR", Messagebox.OK,
						Messagebox.ERROR);
			}
		}
	}

	public CoShebaoChangeModel getM() {
		return m;
	}

	public void setM(CoShebaoChangeModel m) {
		this.m = m;
	}
}
