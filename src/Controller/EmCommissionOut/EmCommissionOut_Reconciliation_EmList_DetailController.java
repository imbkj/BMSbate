package Controller.EmCommissionOut;

import java.util.List;

import org.zkoss.zk.ui.Executions;
import org.zkoss.zul.ListModelList;
import org.zkoss.zul.Messagebox;

import Model.EmCommissionOutFeeDetailHistoryModel;
import bll.EmCommissionOut.EmCommissionOutListBll;

public class EmCommissionOut_Reconciliation_EmList_DetailController {

	Integer ecoh_id;
	Integer ecpu_id;
	Integer efco_id;
	Integer efch_id;

	private List<EmCommissionOutFeeDetailHistoryModel> efhList = new ListModelList<>();

	public EmCommissionOut_Reconciliation_EmList_DetailController() {
		try {
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
			try {
				ecoh_id = Integer.parseInt(Executions.getCurrent().getArg()
						.get("ecoh_id").toString());
			} catch (Exception e) {
				ecoh_id = null;
			}
			try {
				ecpu_id = Integer.parseInt(Executions.getCurrent().getArg()
						.get("ecpu_id").toString());
			} catch (Exception e) {
				ecpu_id = null;
			}
			try {
				efco_id = Integer.parseInt(Executions.getCurrent().getArg()
						.get("efco_id").toString());
			} catch (Exception e) {
				efco_id = null;
			}
			try {
				efch_id = Integer.parseInt(Executions.getCurrent().getArg()
						.get("efch_id").toString());
			} catch (Exception e) {
				efch_id = null;
			}

			efhList = bll.getEmOutEmFeeDetailCompareList(ecoh_id, ecpu_id,
					efco_id, efch_id);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public List<EmCommissionOutFeeDetailHistoryModel> getEfhList() {
		return efhList;
	}

	public void setEfhList(List<EmCommissionOutFeeDetailHistoryModel> efhList) {
		this.efhList = efhList;
	}
}
