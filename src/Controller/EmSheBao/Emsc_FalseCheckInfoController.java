package Controller.EmSheBao;

import org.zkoss.zk.ui.Executions;

import Model.EmShebaoErrModel;
import Model.EmShebaoUpdateModel;
import bll.EmSheBao.EmSheBao_DSelectBll;

public class Emsc_FalseCheckInfoController {
	private EmShebaoUpdateModel sbDataSZSI = new EmShebaoUpdateModel();// 台账后数据
	private EmShebaoUpdateModel sbDataBMS = new EmShebaoUpdateModel();// 系统数据
	private EmShebaoErrModel model = (EmShebaoErrModel) Executions.getCurrent()
			.getArg().get("model");

	private EmSheBao_DSelectBll dsbll = new EmSheBao_DSelectBll();

	public Emsc_FalseCheckInfoController() {
		sbDataSZSI = dsbll.getShebaoSZSIInfo(" AND computerid='"
				+ model.getEmse_computerid() + "' and ownmonth="
				+ String.valueOf(model.getOwnmonth()));
		sbDataBMS = dsbll.getShebaoUpdateInfo(" AND esiu_computerid='"
				+ model.getEmse_computerid() + "' and ownmonth="
				+ String.valueOf(model.getOwnmonth()) + " and esiu_ifstop=0");

		if (sbDataBMS.getEsiu_name()==null || "".equals(sbDataBMS.getEsiu_name()) || "NULL".equals(sbDataBMS.getEsiu_name())) {
			sbDataBMS = dsbll.getShebaoUpdateSimInfo(" AND esiu_computerid='"
					+ model.getEmse_computerid() + "' and ownmonth="
					+ String.valueOf(model.getOwnmonth())
					+ " and esiu_ifstop=0");
		}
	}

	public EmShebaoUpdateModel getSbDataSZSI() {
		return sbDataSZSI;
	}

	public void setSbDataSZSI(EmShebaoUpdateModel sbDataSZSI) {
		this.sbDataSZSI = sbDataSZSI;
	}

	public EmShebaoUpdateModel getSbDataBMS() {
		return sbDataBMS;
	}

	public void setSbDataBMS(EmShebaoUpdateModel sbDataBMS) {
		this.sbDataBMS = sbDataBMS;
	}

}
