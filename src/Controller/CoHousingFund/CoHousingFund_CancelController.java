package Controller.CoHousingFund;

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

public class CoHousingFund_CancelController {
	Integer daid = 0;
	private CoHousingFundModel cohfModel = new CoHousingFundModel();
	private CoHousingFundChangeModel chfcModel = new CoHousingFundChangeModel();

	public CoHousingFund_CancelController() {
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
			chfcModel.setChfc_cohf_id(cohfModel.getCohf_id());
			chfcModel.setCid(cohfModel.getCid());
			chfcModel.setCoba_shortname(cohfModel.getCoba_shortname());
			chfcModel.setChfc_company(cohfModel.getCohf_company());
			chfcModel.setChfc_addtype("登记注销");
			chfcModel.setChfc_stop_reason("分立被合并");
			chfcModel.setChfc_stop_type("登记注销");
			chfcModel.setChfc_addname(UserInfo.getUsername());
			chfcModel.setChfc_laststate(0);
			chfcModel.setChfc_state(1);
			chfcModel.setChfc_tzlstate(0);
			chfcModel.setChfc_remark("");

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
			switch (chfcModel.getChfc_stop_reason()) {
			case "分立被合并":
				chfcModel.setChfc_puzu_id(84);
				break;
			case "破产、解散、撤销":
				chfcModel.setChfc_puzu_id(85);
				break;
			case "被吊销营业执照":
				chfcModel.setChfc_puzu_id(86);
				break;
			default:
				break;
			}

			String[] str = new CoHousingFund_OperateBll().cancel(chfcModel);

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

	public CoHousingFundModel getCohfModel() {
		return cohfModel;
	}

	public void setCohfModel(CoHousingFundModel cohfModel) {
		this.cohfModel = cohfModel;
	}

	public CoHousingFundChangeModel getChfcModel() {
		return chfcModel;
	}

	public void setChfcModel(CoHousingFundChangeModel chfcModel) {
		this.chfcModel = chfcModel;
	}
}
