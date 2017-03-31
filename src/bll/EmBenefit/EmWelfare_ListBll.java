package bll.EmBenefit;

import java.util.List;

import org.zkoss.zul.ListModelList;

import dal.EmBenefit.EmBenefitDal;
import dal.EmBenefit.EmWelfareDal;

import Model.EmBenefitModel;
import Model.EmWelfareModel;

public class EmWelfare_ListBll {
	public List<EmWelfareModel> getList(EmWelfareModel ewm) {
		List<EmWelfareModel> list = new ListModelList<>();
		EmWelfareDal dal = new EmWelfareDal();
		list = dal.getEmBfWfList(ewm);
		return list;
	}
	
	public List<EmBenefitModel> getitemList(){
		List<EmBenefitModel> list = new ListModelList<>();
		EmBenefitDal dal = new EmBenefitDal();
		list = dal.getDisNamelist();
		return list;
	}
}
