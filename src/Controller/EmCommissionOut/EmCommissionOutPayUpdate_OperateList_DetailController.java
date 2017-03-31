package Controller.EmCommissionOut;

import java.util.List;

import org.zkoss.zk.ui.Executions;
import org.zkoss.zul.ListModelList;
import org.zkoss.zul.Messagebox;

import bll.EmCommissionOut.EmCommissionOutListBll;

import Model.EmCommissionOutPayUpdateFeeDetailModel;

public class EmCommissionOutPayUpdate_OperateList_DetailController {

	private List<EmCommissionOutPayUpdateFeeDetailModel> feeList = new ListModelList<>();

	private Integer daid = 0;

	public EmCommissionOutPayUpdate_OperateList_DetailController() {
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
	 * 页面初始化
	 * 
	 */
	public void init() {
		EmCommissionOutListBll bll = new EmCommissionOutListBll();
		try {
			setFeeList(bll.getEmOutPayUpdateFeeDetailList(" and epfd_ecpu_id="
					+ daid));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public List<EmCommissionOutPayUpdateFeeDetailModel> getFeeList() {
		return feeList;
	}

	public void setFeeList(List<EmCommissionOutPayUpdateFeeDetailModel> feeList) {
		this.feeList = feeList;
	}
}
