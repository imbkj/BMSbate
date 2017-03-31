package Controller.CoFinanceManage;

import java.util.List;

import bll.CoFinanceManage.Cfma_CollectImportBll;

import Model.CoFinanceCollectImportErrModel;

public class Cfma_CollectImportErrListController {
	private List<CoFinanceCollectImportErrModel> errList;
	private Cfma_CollectImportBll bll;

	public Cfma_CollectImportErrListController() {
		bll = new Cfma_CollectImportBll();
		errList = bll.getErrList();
	}

	public List<CoFinanceCollectImportErrModel> getErrList() {
		return errList;
	}

}
