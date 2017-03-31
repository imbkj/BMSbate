package Controller.EmBenefit;

import java.util.List;

import org.zkoss.zk.ui.Executions;

import Model.SurveyHistoryContentInfoModel;
import bll.EmBenefit.EmActy_SurveryHistorySelectBll;

public class EmActy_SurHisContentListController {
	private Integer tit_id = (Integer) Executions.getCurrent().getArg().get("tit_id");
	private EmActy_SurveryHistorySelectBll bll=new EmActy_SurveryHistorySelectBll();
	private List<SurveyHistoryContentInfoModel> clist=bll.getSurveyHistoryContentInfo(" and hicn_titleid="+tit_id);
	public List<SurveyHistoryContentInfoModel> getClist() {
		return clist;
	}
	public void setClist(List<SurveyHistoryContentInfoModel> clist) {
		this.clist = clist;
	}
}
