package Controller.EmReg;

import java.text.SimpleDateFormat;
import java.util.List;

import org.zkoss.bind.annotation.BindingParam;
import org.zkoss.bind.annotation.Command;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zul.ListModelList;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Window;

import dal.EmReg.EmReg_OperateDal;

import bll.EmReg.EmReg_ListBll;
import bll.EmReg.EmReg_OperateBll;
import Model.EmRegistrationInfoModel;
import Model.PubCodeConversionModel;

public class EmReg_hd_ModController {
	Integer daid = 0;
	private EmRegistrationInfoModel erm = new EmRegistrationInfoModel();
	private List<PubCodeConversionModel> oetypeList;// 就业类型
	private PubCodeConversionModel oetypeM = new PubCodeConversionModel();

	SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

	public EmReg_hd_ModController() {
		try {
			EmReg_ListBll bll = new EmReg_ListBll();

			daid = Integer.parseInt(Executions.getCurrent().getArg()
					.get("daid").toString());

			setErm(bll.getEmRegInfo(daid, ""));
			setOetypeList(new ListModelList<>(
					bll.getPubCodeList(" and pucl_id=16 and pcco_name='就业类型'")));
			if (erm.getErin_oe_type() != null && erm.getErin_oe_type() != 0) {
				for (PubCodeConversionModel m : oetypeList) {
					if (m.getPcco_code().equals(erm.getErin_oe_type() + "")) {
						setOetypeM(m);
						break;
					}
				}
			}
			oetypeList.add(0, new PubCodeConversionModel());

		} catch (Exception e) {
			e.printStackTrace();
			Messagebox
					.show("页面加载出错!", "ERROR", Messagebox.OK, Messagebox.ERROR);
		}
	}

	@Command("submit")
	public void submit(@BindingParam("win") Window win) {

		try {
			EmReg_OperateBll bll = new EmReg_OperateBll();

			erm.setErin_oe_type(oetypeM.getPcco_code() == null ? null : Integer
					.parseInt(oetypeM.getPcco_code()));

			Integer row = new EmReg_OperateDal().mod(erm);
			if (row > 0) {
				Messagebox.show("提交成功!", "INFORMATION", Messagebox.OK,
						Messagebox.INFORMATION);
				win.detach();
			} else {
				Messagebox.show("提交失败!", "ERROR", Messagebox.OK,
						Messagebox.ERROR);
			}
		} catch (Exception e) {
			e.printStackTrace();
			Messagebox.show("提交出错!", "ERROR", Messagebox.OK, Messagebox.ERROR);
		}
	}

	public EmRegistrationInfoModel getErm() {
		return erm;
	}

	public void setErm(EmRegistrationInfoModel erm) {
		this.erm = erm;
	}

	public List<PubCodeConversionModel> getOetypeList() {
		return oetypeList;
	}

	public void setOetypeList(List<PubCodeConversionModel> oetypeList) {
		this.oetypeList = oetypeList;
	}

	public PubCodeConversionModel getOetypeM() {
		return oetypeM;
	}

	public void setOetypeM(PubCodeConversionModel oetypeM) {
		this.oetypeM = oetypeM;
	}
}
