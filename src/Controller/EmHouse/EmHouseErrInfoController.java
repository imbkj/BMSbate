package Controller.EmHouse;

import java.util.List;

import org.zkoss.zk.ui.Executions;
import org.zkoss.zul.ListModelList;

import Model.EmHouseErrModel;
import Model.EmHouseErrMonthModel;
import Model.EmHouseGJJModel;
import Model.EmHouseGJJMonthModel;
import Model.EmHouseUpdateModel;

public class EmHouseErrInfoController {
	private List<EmHouseUpdateModel> bmsList = new ListModelList<>();
	private List<EmHouseGJJModel> gjjList = new ListModelList<>();
	private List<EmHouseGJJMonthModel> gjjmonthList = new ListModelList<>();

	// 获取台帐(更新前/更新后)参数
	private Integer m = Integer.valueOf(Executions.getCurrent().getArg()
			.get("m").toString());
	private EmHouseErrModel egm = new EmHouseErrModel();
	private EmHouseErrMonthModel egmm = new EmHouseErrMonthModel();
	private EmHouseUpdateModel eum = new EmHouseUpdateModel();

	public EmHouseErrInfoController() {
		if (m.equals(1)) {
			egmm = (EmHouseErrMonthModel) Executions.getCurrent().getArg()
					.get("model");
		} else {
			egm = (EmHouseErrModel) Executions.getCurrent().getArg()
					.get("model");
			
		}

	}

	public Integer getM() {
		return m;
	}

	public void setM(Integer m) {
		this.m = m;
	}

	public EmHouseErrModel getEgm() {
		return egm;
	}

	public void setEgm(EmHouseErrModel egm) {
		this.egm = egm;
	}

	public EmHouseErrMonthModel getEgmm() {
		return egmm;
	}

	public void setEgmm(EmHouseErrMonthModel egmm) {
		this.egmm = egmm;
	}

	public EmHouseUpdateModel getEum() {
		return eum;
	}

	public void setEum(EmHouseUpdateModel eum) {
		this.eum = eum;
	}

}
