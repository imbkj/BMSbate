package Controller.CoHousingFund;

import org.zkoss.bind.annotation.BindingParam;
import org.zkoss.bind.annotation.Command;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zul.Grid;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Window;

import Model.CoHousingFundChangeModel;
import Model.CoHousingFundModel;
import Util.UserInfo;
import bll.CoHousingFund.CoHousingFund_ListBll;
import bll.CoHousingFund.CoHousingFund_OperateBll;

public class CoHousingFund_CancelOperateController {
	Integer daid = 0;
	private Integer state = 0;
	private CoHousingFundChangeModel chfcModel = new CoHousingFundChangeModel();
	private CoHousingFundModel cohfModel = new CoHousingFundModel();

	public CoHousingFund_CancelOperateController() {
		try {
			daid = Integer.parseInt(Executions.getCurrent().getArg()
					.get("daid").toString());

			init();
		} catch (Exception e) {
			e.printStackTrace();
			Messagebox
					.show("页面加载出错!", "ERROR", Messagebox.OK, Messagebox.ERROR);
		}
	}

	/**
	 * 初始化
	 * 
	 */
	public void init() {
		CoHousingFund_ListBll bll = new CoHousingFund_ListBll();
		try {
			chfcModel = bll.getCoHoChangeList(" and chfc_id=" + daid).get(0);
			cohfModel = bll.getCoHoList(
					" and cohf_id=" + chfcModel.getChfc_cohf_id(),false).get(0);

			if (chfcModel.getChfc_state().equals(4)
					|| chfcModel.getChfc_state().equals(5)) {
				state = chfcModel.getChfc_laststate();
			} else {
				state = chfcModel.getChfc_state();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 提交
	 * 
	 * @param win
	 */
	@Command
	public void submit(@BindingParam("win") Window win,
			@BindingParam("gd") Grid gd) {
		CoHousingFund_OperateBll bll = new CoHousingFund_OperateBll();

		try {
			chfcModel.setChfc_state(state + 1);
			chfcModel.setChfc_addname(UserInfo.getUsername());

			String[] str = bll.updatestate(chfcModel, gd);
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
			Messagebox.show("提交出错：" + e.toString(), "ERROR", Messagebox.OK,
					Messagebox.ERROR);
		}
	}

	public final Integer getState() {
		return state;
	}

	public final CoHousingFundChangeModel getChfcModel() {
		return chfcModel;
	}

	public final void setState(Integer state) {
		this.state = state;
	}

	public final void setChfcModel(CoHousingFundChangeModel chfcModel) {
		this.chfcModel = chfcModel;
	}

	public CoHousingFundModel getCohfModel() {
		return cohfModel;
	}

	public void setCohfModel(CoHousingFundModel cohfModel) {
		this.cohfModel = cohfModel;
	}
}
