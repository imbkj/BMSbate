package bll.EmBenefit;

import java.util.List;

import Model.CoBaseModel;
import Model.SurveyContentModel;
import Model.SurveyInfoModel;
import dal.EmBenefit.EmActy_SurveyInfoSelectDal;

public class EmActy_SurveyInfoSelectBll {
	private EmActy_SurveyInfoSelectDal dal=new EmActy_SurveyInfoSelectDal();
	// 查询员工活动——满意度调查内容信息
	public List<SurveyInfoModel> getSurverTitleInfo(String str){
		return dal.getSurverTitleInfo(str);
	}
	
	// 查询员工活动——满意度调查内容信息
	public List<SurveyContentModel> getSurveyContentInfo(String str){
		return dal.getSurveyContentInfo(str);
	}
	
	//获取题目表中的最大排序号
	public Integer getMaxOrder()
	{
		return dal.getMaxOrder();
	}
	
	// 查询员工活动——满意度调查单位信息
	public List<CoBaseModel> getSurveyCobaseInfo(String str){
		return dal.getSurveyCobaseInfo(str);
	}

}
