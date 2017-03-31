package Controller.EmCommissionOut;

import java.util.List;

import org.zkoss.zk.ui.Executions;
import org.zkoss.zul.ListModelList;
import org.zkoss.zul.Messagebox;

import Model.EmCommissionOutChangeModel;
import Model.EmCommissionOutFeeDetailChangeModel;
import Model.EmCommissionOutStandardModel;
import Model.PubStateRelModel;
import bll.EmCommissionOut.EmCommissionOutListBll;

public class EmCommissionOut_ChangeDetailController {
	private EmCommissionOutChangeModel m = new EmCommissionOutChangeModel();
	Integer daid = 0;

	private EmCommissionOutStandardModel stardModel = new EmCommissionOutStandardModel();
	private List<EmCommissionOutFeeDetailChangeModel> feeList = new ListModelList<>();
	private List<PubStateRelModel> hosList = new ListModelList<>();

	public EmCommissionOut_ChangeDetailController() {
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
	 * 数据初始化
	 * 
	 */
	public void init() {
		EmCommissionOutListBll bll = new EmCommissionOutListBll();

		try {
			setM(bll.getEmCommOutChangeInfo(daid, " and type='ecocchange'"));
			setStardModel(bll.getStardInfo(m.getEcoc_ecos_id()));
			setFeeList(bll.getFeeDetailChangeList(" and eofc_ecoc_id=" + daid));
			setHosList(bll.getHosList(daid, " and pbsr_type like '%ecoc%'"));

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public final EmCommissionOutChangeModel getM() {
		return m;
	}

	public final EmCommissionOutStandardModel getStardModel() {
		return stardModel;
	}

	public final List<EmCommissionOutFeeDetailChangeModel> getFeeList() {
		return feeList;
	}

	public final List<PubStateRelModel> getHosList() {
		return hosList;
	}

	public final void setM(EmCommissionOutChangeModel m) {
		this.m = m;
	}

	public final void setStardModel(EmCommissionOutStandardModel stardModel) {
		this.stardModel = stardModel;
	}

	public final void setFeeList(
			List<EmCommissionOutFeeDetailChangeModel> feeList) {
		this.feeList = feeList;
	}

	public final void setHosList(List<PubStateRelModel> hosList) {
		this.hosList = hosList;
	}
}
