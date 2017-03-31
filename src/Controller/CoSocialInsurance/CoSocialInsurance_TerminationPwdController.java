package Controller.CoSocialInsurance;

import org.zkoss.bind.annotation.BindingParam;
import org.zkoss.bind.annotation.Command;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Window;

import dal.CoSocialInsurance.CoSocialInsurance_OperateDal;

import bll.CoSocialInsurance.CoSocialInsurance_ListBll;
import Model.CoShebaoChangeModel;
import Model.CoShebaoModel;

public class CoSocialInsurance_TerminationPwdController {

	private CoShebaoModel m = new CoShebaoModel();
	private CoShebaoChangeModel cm = new CoShebaoChangeModel();
	Integer daid = 0;

	public CoSocialInsurance_TerminationPwdController() {
		CoSocialInsurance_ListBll bll = new CoSocialInsurance_ListBll();

		daid = Integer.parseInt(Executions.getCurrent().getArg().get("daid")
				.toString());

		setCm(bll.getCoShebaoChangeInfo(daid));
		setM(bll.getCoShebaoInfo(cm.getCsbc_cosb_id()));
	}

	/**
	 * 提交
	 * 
	 * @param win
	 * @param gd
	 */
	@Command("submit")
	public void submit(@BindingParam("win") Window win) {
		try {
			cm.setCsbc_pwd(m.getCosb_pwd());
			Integer row = new CoSocialInsurance_OperateDal().TerminationPwd(cm);
			if (row > 0) {
				Messagebox.show("提交成功!", "INFORMATION", Messagebox.OK,
						Messagebox.INFORMATION);
				win.detach();
			} else {
				Messagebox.show("提交失败!", "ERROR", Messagebox.OK,
						Messagebox.ERROR);
			}
		} catch (Exception e) {
			Messagebox.show("提交出错!", "ERROR", Messagebox.OK, Messagebox.ERROR);
			e.printStackTrace();
		}
	}

	public CoShebaoChangeModel getCm() {
		return cm;
	}

	public void setCm(CoShebaoChangeModel cm) {
		this.cm = cm;
	}

	public CoShebaoModel getM() {
		return m;
	}

	public void setM(CoShebaoModel m) {
		this.m = m;
	}
}
