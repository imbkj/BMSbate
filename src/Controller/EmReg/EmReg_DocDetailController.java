package Controller.EmReg;

import org.zkoss.zk.ui.Executions;
import org.zkoss.zul.Messagebox;

import bll.EmReg.EmReg_ListBll;
import Model.EmRegistrationInfoModel;

public class EmReg_DocDetailController {
	private EmRegistrationInfoModel erm = new EmRegistrationInfoModel();

	Integer daid;
	private Integer puzu_id;

	public EmReg_DocDetailController() {
		try {
			EmReg_ListBll bll = new EmReg_ListBll();

			daid = Integer.parseInt(Executions.getCurrent().getArg()
					.get("daid").toString());

			setErm(bll.getEmRegInfo(daid, ""));

			if (erm.getErin_hjtype() != null) {
				if (erm.getErin_hjtype().equals("本市城镇")) {
					setPuzu_id(erm.getCori_sz_puzu_id() == null ? 12 : erm
							.getCori_sz_puzu_id());
				} else {
					setPuzu_id(erm.getCori_wd_puzu_id() == null ? 22 : erm
							.getCori_wd_puzu_id());
				}
			}

		} catch (Exception e) {
			e.printStackTrace();
			Messagebox
					.show("页面加载出错!", "ERROR", Messagebox.OK, Messagebox.ERROR);
		}
	}

	public EmRegistrationInfoModel getErm() {
		return erm;
	}

	public void setErm(EmRegistrationInfoModel erm) {
		this.erm = erm;
	}

	public Integer getPuzu_id() {
		return puzu_id;
	}

	public void setPuzu_id(Integer puzu_id) {
		this.puzu_id = puzu_id;
	}
}
