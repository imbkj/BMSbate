package bll.ClientRelations.CreditRating;

import Model.CreditContentInfoModel;
import Model.CreditCriterionModel;
import dal.ClientRelations.CreditRating.CreditContent_OpraDal;

public class CreditContentInfo_AddBll {
	CreditContent_OpraDal dal=new CreditContent_OpraDal();
	
	//对信誉评定内容表CreditContentInfo插入一条数据，并返回一个Int类型的数
	public int addCreditContentInfo(CreditContentInfoModel model,int id){
		return dal.addCreditContentInfo(model,id);
	}
	
	//对信誉评定标准信息表CreditCriterion插入一条数据，并返回一个Int类型的数
	public int addCreditCriterion(CreditCriterionModel model){
		return dal.addCreditCriterion(model);
	}

}
