package Controller.EmCommissionOut.Template.Import;

import java.util.List;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zul.ListModelList;
import org.zkoss.zul.Messagebox;
import Model.EmCommissionOutPayUpdateCRTModel;
import Model.PubStateRelModel;
import bll.EmCommissionOut.EmCommissionOutListBll;

public class EmOut_Tem_Import_DetailController {
	private EmCommissionOutPayUpdateCRTModel m = new EmCommissionOutPayUpdateCRTModel();
	private List<EmCommissionOutPayUpdateCRTModel> fieldList;
	private List<PubStateRelModel> hosList = new ListModelList<>();

	Integer daid = 0;

	private Boolean ifsub = false;

	public EmOut_Tem_Import_DetailController() {
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
	 * 页面初始化
	 * 
	 */
	public void init() {
		try {
			EmCommissionOutListBll bll = new EmCommissionOutListBll();
			setFieldList(bll.getFieldList(""));
			setM(bll.getEmOutPayUpdateT(" and ecut_id=" + daid).get(0));
			m.setcList(bll.getEmOutPayUpdateC(" and ecuc_ecut_id=" + daid));
			setHosList(bll.getHosList(daid, " and pbsr_type='ecut'"));

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public final EmCommissionOutPayUpdateCRTModel getM() {
		return m;
	}

	public final List<EmCommissionOutPayUpdateCRTModel> getFieldList() {
		return fieldList;
	}

	public final void setM(EmCommissionOutPayUpdateCRTModel m) {
		this.m = m;
	}

	public final void setFieldList(
			List<EmCommissionOutPayUpdateCRTModel> fieldList) {
		this.fieldList = fieldList;
	}

	public Boolean getIfsub() {
		return ifsub;
	}

	public void setIfsub(Boolean ifsub) {
		this.ifsub = ifsub;
	}

	public List<PubStateRelModel> getHosList() {
		return hosList;
	}

	public void setHosList(List<PubStateRelModel> hosList) {
		this.hosList = hosList;
	}
}
