package bll.EmBenefit;

import Model.EmActyCompactModel;
import Model.EmActySupProductInfoModel;
import Util.UserInfo;

import java.util.List;

import org.zkoss.zul.ListModelList;

import dal.PubOfficeDal;
import dal.EmBenefit.EmActyCompactDal;
import dal.EmBenefit.EmActy_SupplierSelectDal;

public class EmActy_compactBll {
	public List<EmActyCompactModel> getList(EmActyCompactModel eacm,
			Boolean desc) {
		List<EmActyCompactModel> list = new ListModelList<>();
		EmActyCompactDal dal = new EmActyCompactDal();
		list = dal.getList(eacm, desc);

		return list;
	}

	public List<EmActyCompactModel> getaddNameList(String name) {
		List<EmActyCompactModel> list = new ListModelList<>();

		EmActyCompactDal dal = new EmActyCompactDal();
		list = dal.getAddNameList(name);

		return list;

	}
	
	public Integer delCompact(Integer eacoId){
		Integer i=0;
		EmActyCompactDal dal = new EmActyCompactDal();
		i=dal.del(eacoId);
		
		return i;
	}
	
	//终止合同
	public Integer stopCompact(Integer eacoId){
		Integer i=0;
		EmActyCompactDal dal = new EmActyCompactDal();
		i=dal.stopcompact(eacoId);
		
		return i;
	}
	
	//获取供应商产品
	public List<EmActySupProductInfoModel> getProductInfo(Integer eacoId){
		List<EmActySupProductInfoModel> list = new ListModelList<>();
		EmActy_SupplierSelectDal dal = new EmActy_SupplierSelectDal();
		EmActySupProductInfoModel em = new EmActySupProductInfoModel();
		em.setProd_eaco_id(eacoId);
		em.setProd_state(1);
		list=dal.getEmActySupProductInfo(em);
		return list;
	}

	// 写入合同文件记录
	public Integer writeDoc(Integer id, String url) {
		Integer i = 0;
		PubOfficeDal dal = new PubOfficeDal();
		i = dal.add(9, id, url, 1, UserInfo.getUsername());
		return i;
	}

}
