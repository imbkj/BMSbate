package Controller.EmSheBao;

import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.select.SelectorComposer;

import bll.EmSheBao.Emsi_SelBll;

import Model.EmbaseModel;

public class Emsi_EmBaseController extends SelectorComposer<Component> {

	private static final long serialVersionUID = 1L;
	private final int gid = Integer.parseInt(Executions.getCurrent().getArg()
			.get("gid").toString());
	private EmbaseModel emModel;

	public Emsi_EmBaseController() {
		Emsi_SelBll selbll = new Emsi_SelBll();
		emModel = selbll.getEmBase(gid);
	}

	public EmbaseModel getEmModel() {
		return emModel;
	}
}
