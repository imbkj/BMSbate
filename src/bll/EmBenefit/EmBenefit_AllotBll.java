package bll.EmBenefit;

import java.util.List;

import org.zkoss.zul.ListModelList;

import dal.CoProduct.cpd_List1Dal;
import dal.EmBenefit.EmBenefitDal;

import Model.CoProductModel;
import Model.EmBenefitModel;

public class EmBenefit_AllotBll {

	public String getName(Integer id) {
		List<EmBenefitModel> list = new ListModelList<>();
		EmBenefitDal dal = new EmBenefitDal();
		list = dal.getlistInfo(id);
		String name = list.get(0).getEmbf_name();
		return name;

	}

	public List<CoProductModel> getEbList(Integer id) {
		List<CoProductModel> list = new ListModelList<>();
		cpd_List1Dal dal = new cpd_List1Dal();
		list = dal.getListByEmbfId(id);
		return list;
	}

	public List<CoProductModel> getCpList(Integer id,String name) {
		List<CoProductModel> list = new ListModelList<>();
		cpd_List1Dal dal = new cpd_List1Dal();
		list = dal.getListExceptById(id, name);
		return list;
	}
	
	public Integer updateList(Integer beId,Integer coprId){
		Integer i=0;
		cpd_List1Dal dal = new cpd_List1Dal();
		i=dal.updateBFId(beId, coprId);
		return i;
	}
	
	public Integer clearList(Integer beId1,Integer beId2){
		Integer i=0;
		cpd_List1Dal dal = new cpd_List1Dal();
		i=dal.clearBFId(beId1, beId2);
		return i;
	}
	
}
