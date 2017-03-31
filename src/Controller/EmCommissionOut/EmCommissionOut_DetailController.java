package Controller.EmCommissionOut;

import java.util.List;

import org.zkoss.zk.ui.Executions;
import org.zkoss.zul.ListModelList;
import org.zkoss.zul.Messagebox;

import Model.EmCommissionOutFeeDetailModel;
import Model.EmCommissionOutModel;
import Model.EmCommissionOutStandardModel;
import Util.SocialInsuranceEmCommissionOut;
import bll.EmCommissionOut.EmCommissionOutListBll;

public class EmCommissionOut_DetailController {
	private EmCommissionOutModel m = new EmCommissionOutModel();
	Integer daid = 0;

	private EmCommissionOutStandardModel stardModel = new EmCommissionOutStandardModel();
	private List<EmCommissionOutFeeDetailModel> feeList = new ListModelList<>();

	SocialInsuranceEmCommissionOut calUtil = new SocialInsuranceEmCommissionOut();

	public EmCommissionOut_DetailController() {
		try {
			try {
				daid = Integer.parseInt(Executions.getCurrent().getArg()
						.get("daid").toString());
			} catch (Exception e) {
				daid = Integer.parseInt(Executions.getCurrent()
						.getAttribute("daid").toString());
			}

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
			setM(bll.getEmCommOutInfo(daid));
			setStardModel(bll.getStardInfo(m.getEcou_ecos_id()));
			setFeeList(bll.getFeeDetailList(" and eofd_ecou_id=" + daid));

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public EmCommissionOutModel getM() {
		return m;
	}

	public void setM(EmCommissionOutModel m) {
		this.m = m;
	}

	public Integer getDaid() {
		return daid;
	}

	public void setDaid(Integer daid) {
		this.daid = daid;
	}

	public EmCommissionOutStandardModel getStardModel() {
		return stardModel;
	}

	public void setStardModel(EmCommissionOutStandardModel stardModel) {
		this.stardModel = stardModel;
	}

	public List<EmCommissionOutFeeDetailModel> getFeeList() {
		return feeList;
	}

	public void setFeeList(List<EmCommissionOutFeeDetailModel> feeList) {
		this.feeList = feeList;
	}
}
