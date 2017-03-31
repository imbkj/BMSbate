package Controller.EmHouseCard;

import org.zkoss.zk.ui.Executions;

import Model.EmHouseTakeCardInfoModel;

public class HuCard_TakeCardDetailInfoController {

	private EmHouseTakeCardInfoModel mo = new EmHouseTakeCardInfoModel();

	public HuCard_TakeCardDetailInfoController() {
		System.out.println(Executions.getCurrent().getArg().get("model"));
		if (Executions.getCurrent().getArg().get("model") != null) {
			mo = (EmHouseTakeCardInfoModel) Executions.getCurrent().getArg()
					.get("model");
			System.out.println(mo.getList().size());
		}
	}

	public EmHouseTakeCardInfoModel getMo() {
		return mo;
	}

	public void setMo(EmHouseTakeCardInfoModel mo) {
		this.mo = mo;
	}

}
