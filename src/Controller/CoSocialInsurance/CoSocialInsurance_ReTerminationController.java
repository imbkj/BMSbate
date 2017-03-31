package Controller.CoSocialInsurance;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.zkoss.bind.annotation.BindingParam;
import org.zkoss.bind.annotation.Command;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Window;

import Model.CoShebaoChangeModel;
import Model.CoShebaoModel;
import Util.UserInfo;
import bll.CoSocialInsurance.CoSocialInsurance_ListBll;
import bll.CoSocialInsurance.CoSocialInsurance_OperateBll;

public class CoSocialInsurance_ReTerminationController {
	private CoShebaoModel m = new CoShebaoModel();
	private CoShebaoChangeModel cm = new CoShebaoChangeModel();
	Integer daid = 0;
	private Date ownmonth = new Date();

	public CoSocialInsurance_ReTerminationController() {
		try {
			CoSocialInsurance_ListBll bll = new CoSocialInsurance_ListBll();

			daid = Integer.parseInt(Executions.getCurrent().getArg()
					.get("daid").toString());

			setCm(bll.getCoShebaoChangeInfo(daid));
			setM(bll.getCoShebaoInfo(cm.getCsbc_cosb_id()));

		} catch (Exception e) {
			e.printStackTrace();
			Messagebox
					.show("页面加载出错!", "ERROR", Messagebox.OK, Messagebox.ERROR);
		}
	}

	/**
	 * 提交
	 * 
	 * @param win
	 */
	@Command("submit")
	public void submit(@BindingParam("win") Window win) {
		try {
			fieldhandle();
		} catch (Exception e) {
			e.printStackTrace();
			Messagebox
					.show("数据处理出错!", "ERROR", Messagebox.OK, Messagebox.ERROR);
		}

		try {
			String[] str = new CoSocialInsurance_OperateBll().resubmit(cm);

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
			Messagebox.show("提交出错!", "ERROR", Messagebox.OK, Messagebox.ERROR);
		}
	}

	/**
	 * 字段处理
	 * 
	 */
	public void fieldhandle() {
		cm.setOwnmonth(Integer.parseInt(new SimpleDateFormat("yyyyMM")
				.format(ownmonth)));
		cm.setCsbc_addname(UserInfo.getUsername());
		cm.setCsbc_state(1);
	}

	public CoShebaoModel getM() {
		return m;
	}

	public void setM(CoShebaoModel m) {
		this.m = m;
	}

	public CoShebaoChangeModel getCm() {
		return cm;
	}

	public void setCm(CoShebaoChangeModel cm) {
		this.cm = cm;
	}

	public Date getOwnmonth() {
		return ownmonth;
	}

	public void setOwnmonth(Date ownmonth) {
		this.ownmonth = ownmonth;
	}
}
