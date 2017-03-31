package Controller.EmCommissionOut;

import org.zkoss.zk.ui.Executions;
import org.zkoss.zul.Messagebox;

import Model.EmCommissionOutChangeModel;
import bll.EmCommissionOut.EmCommissionOutListBll;

public class EmCommissionOut_CancelDetailController {
	private EmCommissionOutChangeModel m = new EmCommissionOutChangeModel();
	Integer daid = 0;

	public EmCommissionOut_CancelDetailController() {
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
	 * 数据初始化
	 * 
	 */
	public void init() {
		EmCommissionOutListBll bll = new EmCommissionOutListBll();

		try {
			setM(bll.getEmCommOutChangeInfo(daid, ""));

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public EmCommissionOutChangeModel getM() {
		return m;
	}

	public void setM(EmCommissionOutChangeModel m) {
		this.m = m;
	}
}
