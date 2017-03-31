package bll.EmBenefit;

import java.util.List;

import Model.SurveyHistoryContentInfoModel;
import Model.SurveyHistoryTitleInfoModel;
import dal.EmBenefit.EmActy_SurveryHistorySelectDal;

public class EmActy_SurveryHistorySelectBll {
	private EmActy_SurveryHistorySelectDal dal=new EmActy_SurveryHistorySelectDal();
	
	// 查询员工活动——满意度调查历史题目信息
	public List<SurveyHistoryTitleInfoModel> getSurveyHistoryTitleInfo(String str){
		return dal.getSurveyHistoryTitleInfo(str);
	}
	
	// 查询员工活动——满意度调查历史内容信息
	public List<SurveyHistoryContentInfoModel> getSurveyHistoryContentInfo(String str){
		return dal.getSurveyHistoryContentInfo(str);
	}
	
	// 查询员工活动——满意度调查结果信息
	public List<SurveyHistoryContentInfoModel> getSurveyHistoryResultInfo(String str){
		return dal.getSurveyHistoryResultInfo(str);
	}
	
	// 查询员工活动——满意度调查单条历史题目信息
	public SurveyHistoryTitleInfoModel getHistoryTitleModelInfo(String str){
		return dal.getHistoryTitleModelInfo(str);
	}
	
	// 查询员工活动——满意度调查结果关系信息表数据
	public List<SurveyHistoryContentInfoModel> getSurveyResultInfo(String str){
		return dal.getSurveyResultInfo(str);
	}

}
