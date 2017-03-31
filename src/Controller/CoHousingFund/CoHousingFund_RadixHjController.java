package Controller.CoHousingFund;

import java.util.Calendar;
import java.util.Date;

import org.zkoss.bind.annotation.BindingParam;
import org.zkoss.bind.annotation.Command;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Window;

import Model.CoHousingFundChangeModel;
import Model.CoHousingFundModel;
import Util.UserInfo;
import bll.CoHousingFund.CoHousingFund_ListBll;
import bll.CoHousingFund.CoHousingFund_OperateBll;

public class CoHousingFund_RadixHjController {
	Integer daid = 0;
	private CoHousingFundModel cohfModel = new CoHousingFundModel();
	private CoHousingFundChangeModel chfcModel = new CoHousingFundChangeModel();

	public CoHousingFund_RadixHjController() {
		try {
			daid = Integer.valueOf(Executions.getCurrent().getArg().get("daid")
					.toString());

			init();
		} catch (Exception e) {
			e.printStackTrace();
			Messagebox
					.show("页面加载出错!", "ERROR", Messagebox.OK, Messagebox.ERROR);
		}
	}

	public void init() {
		CoHousingFund_ListBll bll = new CoHousingFund_ListBll();
		try {
			setCohfModel(bll.getCoHoList(" and cohf_id=" + daid,false).get(0));

			chfcModel.setOwnmonthDate(new Date());
			chfcModel.setChfc_start_monthDate(new Date());
			Calendar calendar = Calendar.getInstance();
			calendar.setTime(new Date());
			calendar.add(Calendar.MONTH, 1);
			chfcModel.setChfc_end_monthDate(calendar.getTime());
			chfcModel.setChfc_cohf_id(cohfModel.getCohf_id());
			chfcModel.setCid(cohfModel.getCid());
			chfcModel.setCoba_shortname(cohfModel.getCoba_shortname());
			chfcModel.setChfc_company(cohfModel.getCohf_company());
			chfcModel.setChfc_addtype("缓缴");
			chfcModel.setChfc_addname(UserInfo.getUsername());
			chfcModel.setChfc_laststate(0);
			chfcModel.setChfc_state(1);
			chfcModel.setChfc_tzlstate(0);
			chfcModel.setChfc_puzu_id(81);
			chfcModel.last_emcount_sum();
			chfcModel.setChfc_last_month(cohfModel.getLast_month());

		} catch (Exception e) {
			e.printStackTrace();
			Messagebox.show("页面初始化出错!", "ERROR", Messagebox.OK,
					Messagebox.ERROR);
		}
	}

	/**
	 * 提交
	 * 
	 */
	@Command
	public void submit(@BindingParam("win") Window win) {
		try {
			chfcModel.month_handle();

			String[] str = new CoHousingFund_OperateBll().radixhj(chfcModel);

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

	public final CoHousingFundModel getCohfModel() {
		return cohfModel;
	}

	public final CoHousingFundChangeModel getChfcModel() {
		return chfcModel;
	}

	public final void setCohfModel(CoHousingFundModel cohfModel) {
		this.cohfModel = cohfModel;
	}

	public final void setChfcModel(CoHousingFundChangeModel chfcModel) {
		this.chfcModel = chfcModel;
	}
}
