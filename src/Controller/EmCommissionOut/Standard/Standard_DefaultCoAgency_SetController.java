package Controller.EmCommissionOut.Standard;

import java.util.Date;
import java.util.List;

import org.zkoss.bind.annotation.BindingParam;
import org.zkoss.bind.annotation.Command;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zul.ListModelList;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Window;

import Model.CoAgencyBaseCityRelViewModel;
import Model.EmCommissionOutStandardModel;
import Util.UserInfo;
import bll.EmCommissionOut.Standard.Standard_ListBll;
import bll.EmCommissionOut.Standard.Standard_OperateBll;

public class Standard_DefaultCoAgency_SetController {

	Integer daid = 0;
	private EmCommissionOutStandardModel m = new EmCommissionOutStandardModel();

	// 委托机构
	private List<CoAgencyBaseCityRelViewModel> caList = new ListModelList<>();
	private CoAgencyBaseCityRelViewModel caM = new CoAgencyBaseCityRelViewModel();

	public Standard_DefaultCoAgency_SetController() {
		try {
			Standard_ListBll bll = new Standard_ListBll();

			daid = Integer.parseInt(Executions.getCurrent().getArg()
					.get("daid").toString());

			setM(bll.getStandardInfo(daid));
			setCaList(bll.getCoAgencyList(m.getEcos_cabc_id()));
			setCaM(caList.size() > 0 ? caList.get(0) : caM);
		} catch (Exception e) {
			e.printStackTrace();
			Messagebox
					.show("页面加载出错!", "ERROR", Messagebox.OK, Messagebox.ERROR);
		}
	}

	@Command("submit")
	public void submit(@BindingParam("win") Window win) {
		try {
			if (caM.getCabc_id() == 0) {
				Messagebox.show("请选择机构!", "ERROR", Messagebox.OK,
						Messagebox.ERROR);
			} else {
				m.setEcos_state(2);
				m.setCabc_id(caM.getCabc_id());
				m.setEosr_addname(UserInfo.getUsername());
				m.setEosr_statetime(new Date());

				String[] str = new Standard_OperateBll().UpdateState(m);

				if (str[0].equals("1")) {
					Messagebox.show(str[1], "INFORMATION", Messagebox.OK,
							Messagebox.INFORMATION);
					win.detach();
				} else {
					Messagebox.show(str[1], "ERROR", Messagebox.OK,
							Messagebox.ERROR);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			Messagebox.show("提交出错!", "ERROR", Messagebox.OK, Messagebox.ERROR);
		}
	}

	public EmCommissionOutStandardModel getM() {
		return m;
	}

	public void setM(EmCommissionOutStandardModel m) {
		this.m = m;
	}

	public List<CoAgencyBaseCityRelViewModel> getCaList() {
		return caList;
	}

	public void setCaList(List<CoAgencyBaseCityRelViewModel> caList) {
		this.caList = caList;
	}

	public CoAgencyBaseCityRelViewModel getCaM() {
		return caM;
	}

	public void setCaM(CoAgencyBaseCityRelViewModel caM) {
		this.caM = caM;
	}
}
