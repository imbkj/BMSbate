package bll.EmBenefit;

import Model.SurveyContentModel;
import Model.SurveyInfoModel;
import dal.EmBenefit.EmActy_SurveyInfoOperateDal;

public class EmActy_SurveyInfoOperateBll {
	private EmActy_SurveyInfoOperateDal dal=new EmActy_SurveyInfoOperateDal();
	//调查题目新增
	public Integer EmActy_SurveyTitleAdd(SurveyInfoModel m) {
		return dal.EmActy_SurveyTitleAdd(m);
	}
	
	//调查内容新增
	public Integer EmActy_SurveyContentAdd(SurveyContentModel m) {
		return dal.EmActy_SurveyContentAdd(m);
	}
	
	//修改调查题目信息
	public int updateSurveyTitleInfo(SurveyInfoModel model){
		return dal.updateSurveyTitleInfo(model);
	}
	//修改调查内容信息
	public int updateSurveyContentInfo(SurveyContentModel model){
		return dal.updateSurveyContentInfo(model);
	}
	
	//每年的调查信息新增
	public Integer EmActy_SurveyHistoryInfoAdd(Integer suryid,Integer ownyear,String hitl_type) {
		return dal.EmActy_SurveyHistoryInfoAdd(suryid, ownyear,hitl_type);
	}

}
