package Controller.CoProduct;

import java.util.List;

import org.zkoss.zk.ui.Executions;
import org.zkoss.zul.Messagebox;

import bll.CoProduct.cpd_ListBll;
import Model.CopCompactModel;

public class CoProduct_CompactOperateList_DetailController {

	private List<CopCompactModel> cpcrList;
	Integer daid = 0;

	public CoProduct_CompactOperateList_DetailController() {
		try {
			daid = Integer.parseInt(Executions.getCurrent().getArg()
					.get("daid").toString());

			init();
		} catch (Exception e) {
			Messagebox
					.show("页面加载出错!", "ERROR", Messagebox.OK, Messagebox.ERROR);
			e.printStackTrace();
		}
	}

	/**
	 * 页面初始化
	 * 
	 */
	public void init() {
		cpd_ListBll bll = new cpd_ListBll();
		try {
			setCpcrList(bll.getCopComRelList(daid));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public List<CopCompactModel> getCpcrList() {
		return cpcrList;
	}

	public void setCpcrList(List<CopCompactModel> cpcrList) {
		this.cpcrList = cpcrList;
	}
}
