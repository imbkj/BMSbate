package Controller.EmBenefit;

import java.util.List;

import org.zkoss.zk.ui.Executions;

import Model.SurveyHistoryContentInfoModel;
import Model.SurveyHistoryTitleInfoModel;
import bll.EmBenefit.EmActy_SurveryHistorySelectBll;

public class EmActy_SurveryReltInfoController {
	private Integer tit_id = (Integer) Executions.getCurrent().getArg().get("tit_id");
	private EmActy_SurveryHistorySelectBll bll=new EmActy_SurveryHistorySelectBll();
	private List<SurveyHistoryContentInfoModel> clist=bll.getSurveyHistoryResultInfo(" and hicn_titleid="+tit_id);
	private SurveyHistoryTitleInfoModel model=bll.getHistoryTitleModelInfo(" and hitl_id="+tit_id);
	
	
	public EmActy_SurveryReltInfoController()
	{
		System.out.println("model="+model.getRelt_txtcon());
	}
	
	public List<SurveyHistoryContentInfoModel> getClist() {
		return clist;
	}
	public void setClist(List<SurveyHistoryContentInfoModel> clist) {
		this.clist = clist;
	}
	public SurveyHistoryTitleInfoModel getModel() {
		return model;
	}
	public void setModel(SurveyHistoryTitleInfoModel model) {
		this.model = model;
	}
	
}
