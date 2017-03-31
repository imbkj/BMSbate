package Controller.EmBenefit;

import java.util.List;

import org.zkoss.zk.ui.Executions;

import Model.SurveyContentModel;
import bll.EmBenefit.EmActy_SurveyInfoSelectBll;

public class EmActy_SurveyContentInfoListController {
	private Integer sury_id = (Integer) Executions.getCurrent().getArg().get("sury_id");
	private EmActy_SurveyInfoSelectBll bll=new EmActy_SurveyInfoSelectBll();
	private List<SurveyContentModel> list=bll.getSurveyContentInfo(" and cont_suryid="+sury_id);
	public List<SurveyContentModel> getList() {
		return list;
	}
	public void setList(List<SurveyContentModel> list) {
		this.list = list;
	}
}
