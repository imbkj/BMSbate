package Controller.EmCommissionOut;

import java.util.List;

import org.zkoss.zk.ui.Executions;
import org.zkoss.zul.ListModelList;
import org.zkoss.zul.Messagebox;

import Model.EmCommissionOutPayUpdateFeeDetailModel;

public class EmCommissionOutPayUpdate_ImportExcel_Preview_DetailController {

	private List<EmCommissionOutPayUpdateFeeDetailModel> feeList = new ListModelList<>();

	@SuppressWarnings("unchecked")
	public EmCommissionOutPayUpdate_ImportExcel_Preview_DetailController() {
		try {
			setFeeList((List<EmCommissionOutPayUpdateFeeDetailModel>) Executions
					.getCurrent().getArg().get("feeList"));

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
		try {

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
