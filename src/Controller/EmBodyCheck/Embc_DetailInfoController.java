package Controller.EmBodyCheck;

import java.util.ArrayList;
import java.util.List;

import org.zkoss.zk.ui.Executions;

import Model.EmBcSetupModel;
import Model.EmBodyCheckItemModel;
import Model.EmBodyCheckModel;
import Model.EmbaseModel;
import bll.EmBodyCheck.EmBcInfo_SelectBll;
import bll.EmBodyCheck.EmbcItem_SelectBll;

public class Embc_DetailInfoController {
	private EmBodyCheckModel ecm = (EmBodyCheckModel) Executions.getCurrent()
			.getArg().get("model");
	private EmBcInfo_SelectBll selectbll = new EmBcInfo_SelectBll();
	private EmbcItem_SelectBll itembll = new EmbcItem_SelectBll();
	// 员工信息
	private List<EmbaseModel> embaselist = new ArrayList<EmbaseModel>();
	// 体检项目信息
	private List<EmBodyCheckItemModel> itemlist = new ArrayList<EmBodyCheckItemModel>();
	private List<EmBcSetupModel> setuplist = selectbll
			.getSetupList(new EmBcSetupModel());

	public Embc_DetailInfoController() {
		EmBodyCheckItemModel ebcm = new EmBodyCheckItemModel();
		ebcm.setEbit_state(1);
		ebcm.setEbit_hospital(getSetupId(ecm.getEbcs_hospital()));

		ebcm.setIdList(ecm.getEbcl_itemnums());
		itemlist = selectbll.getItemList(ebcm);
	}

	// 根据体检机构名称获取机构Id
	private Integer getSetupId(String name) {
		Integer id = null;
		for (EmBcSetupModel m : setuplist) {
			if (m.getEbcs_hospital() != null
					&& m.getEbcs_hospital().equals(name)) {
				id = m.getEbcs_id();
			}
		}
		return id;
	}

	public EmBodyCheckModel getEcm() {
		return ecm;
	}

	public void setEcm(EmBodyCheckModel ecm) {
		this.ecm = ecm;
	}

	public List<EmBodyCheckItemModel> getItemlist() {
		return itemlist;
	}

	public void setItemlist(List<EmBodyCheckItemModel> itemlist) {
		this.itemlist = itemlist;
	}
	
	
}
