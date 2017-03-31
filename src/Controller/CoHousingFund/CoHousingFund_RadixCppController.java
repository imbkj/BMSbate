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

import bll.CoHousingFund.CoHousingFund_ListBll;
import bll.CoHousingFund.CoHousingFund_OperateBll;

import Model.CoHousingFundChangeModel;
import Model.CoHousingFundInforChangeModel;
import Model.CoHousingFundModel;
import Util.UserInfo;

public class CoHousingFund_RadixCppController {

	Integer daid = 0;
	private CoHousingFundModel cohfModel = new CoHousingFundModel();
	private CoHousingFundChangeModel chfcModel = new CoHousingFundChangeModel();
	private List<Integer> cppList = new ListModelList<>();
	List<CoHousingFundInforChangeModel> cficList = new ArrayList<>();

	public CoHousingFund_RadixCppController() {
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
			chfcModel.setChfc_addtype("比例调整");
			chfcModel.setChfc_addname(UserInfo.getUsername());
			chfcModel.setChfc_laststate(0);
			chfcModel.setChfc_state(1);
			chfcModel.setChfc_remark("");

			for (int i = 5; i <= 12; i++) {
				if (i != cohfModel.getCpp()) {
					cppList.add(i);
				}
			}
			chfcModel.setCpp(cppList.get(0));
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
			chfcModel.cpp_handle();
			if (cohfModel.getCohf_ispwd().equals(1)) {
				chfcModel.setChfc_remark("该公司有密钥信息，无需提交纸质材料；"
						+ chfcModel.getChfc_remark());
				chfcModel.setChfc_puzu_id(0);
				chfcModel.setChfc_tzlstate(1);
			} else {
				chfcModel.setChfc_puzu_id(82);
				chfcModel.setChfc_tzlstate(0);
			}

			CoHousingFundInforChangeModel m = new CoHousingFundInforChangeModel();
			m.setCfic_chfc_id(daid);
			m.setCfic_changestyle("比例调整");
			m.setCfic_changeold(cohfModel.getCpp() + "%");
			m.setCfic_changenew(chfcModel.getCpp() + "%");
			cficList.add(m);

			chfcModel.setCficList(cficList);

			String[] str = new CoHousingFund_OperateBll().radixcpp(chfcModel);

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

	public final List<Integer> getCppList() {
		return cppList;
	}

	public final void setCppList(List<Integer> cppList) {
		this.cppList = cppList;
	}
}
