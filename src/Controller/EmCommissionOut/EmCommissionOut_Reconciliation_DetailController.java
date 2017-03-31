package Controller.EmCommissionOut;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.zkoss.bind.annotation.BindingParam;
import org.zkoss.bind.annotation.Command;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zul.ListModelList;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Window;

import bll.EmCommissionOut.EmCommissionOutListBll;

import Model.EmCommissionOutHistoryModel;

public class EmCommissionOut_Reconciliation_DetailController {

	String ownmonth = "";
	Integer cabc_id;

	private List<EmCommissionOutHistoryModel> echList = new ListModelList<>();

	public EmCommissionOut_Reconciliation_DetailController() {
		try {
			ownmonth = Executions.getCurrent().getArg().get("ownmonth")
					.toString();
			cabc_id = Integer.parseInt(Executions.getCurrent().getArg()
					.get("cabc_id").toString());

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
			if (!ownmonth.isEmpty() && cabc_id != null) {
				echList = bll.getEmOutCompanyCompareList(ownmonth, cabc_id, "");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 弹出员工列表
	 * 
	 * @param cid
	 */
	@Command("openEmList")
	public void openEmList(@BindingParam("cid") Integer cid) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("ownmonth", ownmonth);
		map.put("cid", cid);

		Window window = (Window) Executions.createComponents(
				"/EmCommissionOut/EmCommissionOut_Reconciliation_EmList.zul",
				null, map);
		window.doModal();
	}

	public List<EmCommissionOutHistoryModel> getEchList() {
		return echList;
	}

	public void setEchList(List<EmCommissionOutHistoryModel> echList) {
		this.echList = echList;
	}
}
