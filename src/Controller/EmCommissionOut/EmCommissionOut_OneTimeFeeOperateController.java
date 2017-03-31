package Controller.EmCommissionOut;

import java.math.BigDecimal;
import java.util.List;

import org.zkoss.bind.annotation.BindingParam;
import org.zkoss.bind.annotation.Command;
import org.zkoss.bind.annotation.NotifyChange;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zul.ListModelList;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Window;

import bll.EmCommissionOut.EmCommissionOutListBll;
import bll.EmCommissionOut.EmCommissionOut_OperateBll;

import Model.EmCommissionOutChangeModel;
import Model.EmCommissionOutFeeDetailChangeModel;
import Util.UserInfo;

public class EmCommissionOut_OneTimeFeeOperateController {

	private EmCommissionOutChangeModel m = new EmCommissionOutChangeModel();
	private List<EmCommissionOutFeeDetailChangeModel> feeList = new ListModelList<>();
	Integer daid = 0;
	private EmCommissionOutChangeModel mm = new EmCommissionOutChangeModel();
	public EmCommissionOut_OneTimeFeeOperateController() {
		try {
			daid = Integer.parseInt(Executions.getCurrent().getArg()
					.get("daid").toString());
			mm = (EmCommissionOutChangeModel) Executions.getCurrent().getArg()
					.get("cm");
			init();
		} catch (Exception e) {
			Messagebox
					.show("页面加载出错!", "ERROR", Messagebox.OK, Messagebox.ERROR);
			e.printStackTrace();
		}
	}

	/**
	 * 数据初始化
	 * 
	 */
	public void init() {
		EmCommissionOutListBll bll = new EmCommissionOutListBll();

		try {
			setM(bll.getEmCommOutChangeInfo(daid, ""));
			setFeeList(bll
					.getFeeDetailChangeList(" and eofc_month_sum>0 and eofc_ecoc_id="
							+ daid));

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 总计
	 * 
	 */
	@Command("calcsum")
	@NotifyChange({ "m" })
	public void calcsum() {
		m.setEcoc_sum(BigDecimal.ZERO);
		for (EmCommissionOutFeeDetailChangeModel m1 : feeList) {
			m.setEcoc_sum(m.getEcoc_sum().add(m1.getEofc_month_sum()));
		}
	}

	/**
	 * 提交
	 * 
	 * @param win
	 */
	@Command("submit")
	public void submit(@BindingParam("win") Window win) {
		EmCommissionOut_OperateBll bll = new EmCommissionOut_OperateBll();

		m.setEcoc_addname(UserInfo.getUsername());
		m.setEcoc_state(6);
		m.setType("ecocotf");
		m.setEcoc_remark(m.getEcoc_remark1());

		try {
			String[] str = bll.updatestate(m, feeList);

			if (str[0].equals("1")) {
				mm.setUpdateState(true);
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

	public EmCommissionOutChangeModel getM() {
		return m;
	}

	public void setM(EmCommissionOutChangeModel m) {
		this.m = m;
	}

	public List<EmCommissionOutFeeDetailChangeModel> getFeeList() {
		return feeList;
	}

	public void setFeeList(List<EmCommissionOutFeeDetailChangeModel> feeList) {
		this.feeList = feeList;
	}
}
