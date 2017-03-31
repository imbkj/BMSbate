package Controller.CoBase;

import java.util.List;

import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.select.SelectorComposer;

import bll.CoBase.CoBaseLinkMan_SelectBll;

import Model.CoAgencyLinkmanModel;
import Model.CoBaseLinkFamilyModel;

public class CoBaseLinkMan_SelController extends SelectorComposer<Component> {
	private static final long serialVersionUID = 1L;
	private final int cali_id = Integer.parseInt(Executions.getCurrent()
			.getArg().get("cali_id").toString());
	private CoAgencyLinkmanModel m;
	private List<CoBaseLinkFamilyModel> familyList;

	public CoBaseLinkMan_SelController() {
		CoBaseLinkMan_SelectBll bll = new CoBaseLinkMan_SelectBll();
		m = bll.getLinkModel(cali_id);
		familyList = bll.getLinkFamilyModel(cali_id);
	}

	public CoAgencyLinkmanModel getM() {
		return m;
	}

	public List<CoBaseLinkFamilyModel> getFamilyList() {
		return familyList;
	}

}
