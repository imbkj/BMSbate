package Controller.CoHousingFund;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.zkoss.bind.annotation.BindingParam;
import org.zkoss.bind.annotation.Command;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zul.ListModelList;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Window;

import Model.CoHousingFundChangeModel;
import Model.CoHousingFundModel;
import Model.CoHousingFundZbChangeModel;
import Model.CoHousingFundZbModel;
import Util.UserInfo;
import bll.CoHousingFund.CoHousingFund_ListBll;
import bll.CoHousingFund.CoHousingFund_OperateBll;

public class CoHousingFund_SurrenderController {
	Integer daid = 0;
	private CoHousingFundModel cohfModel = new CoHousingFundModel();
	private CoHousingFundChangeModel chfcModel = new CoHousingFundChangeModel();
	private List<CoHousingFundZbModel> zbList = new ListModelList<>();
	List<CoHousingFundZbChangeModel> zbchangeList = new ArrayList<>();

	public CoHousingFund_SurrenderController() {
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
			setZbList(bll.getZbList(" and chfz_state=1 and chfz_cohf_id="
					+ daid));

			chfcModel.setOwnmonthDate(new Date());
			chfcModel.setChfc_cohf_id(cohfModel.getCohf_id());
			chfcModel.setCid(cohfModel.getCid());
			chfcModel.setCoba_shortname(cohfModel.getCoba_shortname());
			chfcModel.setChfc_company(cohfModel.getCohf_company());
			chfcModel.setChfc_addtype("合同终止");
			chfcModel.setChfc_stop_type("合同终止");
			chfcModel.setChfc_addname(UserInfo.getUsername());
			chfcModel.setChfc_laststate(0);
			chfcModel.setChfc_state(1);
			chfcModel.setChfc_tzlstate(0);
			chfcModel.setChfc_remark("");
			chfcModel.setChfc_puzu_id(87);

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

			for (int i = 0; i < zbList.size(); i++) {
				CoHousingFundZbChangeModel m = new CoHousingFundZbChangeModel();
				CoHousingFundZbModel m1 = zbList.get(i);

				m.setCfzc_cohf_id(daid);
				m.setCfzc_addtype("注销");
				m.setOwnmonth(chfcModel.getOwnmonth());
				m.setCfzc_addname(UserInfo.getUsername());
				m.setCfzc_state(0);
				m.setCfzc_laststate(0);
				m.setCfzc_tzlstate(1);
				m.setCfzc_remark("因单位公积金合同终止，注销该专办员");

				m.setCfzc_chfz_id(m1.getChfz_id());
				m.setCfzc_name(m1.getChfz_name());
				m.setCfzc_number(m1.getChfz_number());
				m.setCfzc_tel(m1.getChfz_tel());
				m.setCfzc_mobile(m1.getChfz_mobile());
				m.setCfzc_mail(m1.getChfz_mail());

				zbchangeList.add(m);
			}
			chfcModel.setZbList(zbchangeList);

			String[] str = new CoHousingFund_OperateBll().surrender(chfcModel);

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

	public List<CoHousingFundZbModel> getZbList() {
		return zbList;
	}

	public void setZbList(List<CoHousingFundZbModel> zbList) {
		this.zbList = zbList;
	}
}
