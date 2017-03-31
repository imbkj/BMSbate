package bll.EmExpress;

import java.util.List;

import dal.EmExpress.EmExpressInfoSelectDal;

import Model.CoBaseModel;
import Model.EmExpressContactInfoModel;
import Model.EmExpressInfoModel;
import Model.EmbaseModel;

public class EmExpressInfoSelectBll {
	private EmExpressInfoSelectDal dal=new EmExpressInfoSelectDal();
	
	//查询邮件信息
	public List<EmExpressInfoModel> getEmExpressInfoList(String str,String strorder){
		return dal.getEmExpressInfoList(str,strorder);
	}
	
	//查询邮件地址信息
	public List<EmExpressContactInfoModel> getEmExpressContactInfoList(String str){
		return dal.getEmExpressContactInfoList(str);
	}
	
	//员工信息查询
	public List<EmbaseModel> getEmBaseInfoList(String str){
		return dal.getEmBaseInfoList(str);
	}
	
	//单位信息查询
	public List<CoBaseModel> getCoBaseInfoList(String str){
		return dal.getCoBaseInfoList(str);
	}
	//判断快递状态是否相同
	public Integer ifstatesame(String str)
	{
		return dal.ifstatesame(str);
	}
	
	//查询客服
	public List<String> getClient()
	{
		return dal.getClient();
	}
}
