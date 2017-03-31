package Controller.EmCommissionOut;

import java.util.List;

import org.zkoss.zk.ui.Executions;
import org.zkoss.zul.ListModelList;
import org.zkoss.zul.Messagebox;

import bll.EmCommissionOut.EmCommissionOutListBll;

import Model.EmCommissionOutPayUpdateCRTModel;
import Model.EmCommissionOutPayUpdateModel;

public class EmCommissionOutPayUpdate_ImportExcel_PreviewController {

	private List<EmCommissionOutPayUpdateModel> puList = new ListModelList<>();
	private List<EmCommissionOutPayUpdateCRTModel> fieldList = new ListModelList<>();
	private Integer ecut_id = 0;

	@SuppressWarnings("unchecked")
	public EmCommissionOutPayUpdate_ImportExcel_PreviewController() {
		try {
			puList = (List<EmCommissionOutPayUpdateModel>) Executions
					.getCurrent().getArg().get("puList");
			ecut_id = Integer.parseInt(Executions.getCurrent().getArg()
					.get("ecut_id").toString());

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
		EmCommissionOutListBll bll = new EmCommissionOutListBll();
		try {
			setFieldList(bll.getTableFieldList("EmCommissionOutPayUpdate",
					ecut_id));

			if (puList.size() > 0) {
				for (int i = 0; i < puList.size(); i++) {
					EmCommissionOutPayUpdateModel pum = puList.get(i);
					List<Object> objsList = pum.getObjsList();

					for (EmCommissionOutPayUpdateCRTModel crtm : fieldList) {
						if (crtm.getEcpr_ecpu_field() != null) {
							Object obj = pum
									.getField(crtm.getEcpr_ecpu_field());
							objsList.add(obj);
						}
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public List<EmCommissionOutPayUpdateModel> getPuList() {
		return puList;
	}

	public void setPuList(List<EmCommissionOutPayUpdateModel> puList) {
		this.puList = puList;
	}

	public List<EmCommissionOutPayUpdateCRTModel> getFieldList() {
		return fieldList;
	}

	public void setFieldList(List<EmCommissionOutPayUpdateCRTModel> fieldList) {
		this.fieldList = fieldList;
	}

	public final Integer getEcut_id() {
		return ecut_id;
	}

	public final void setEcut_id(Integer ecut_id) {
		this.ecut_id = ecut_id;
	}
}
