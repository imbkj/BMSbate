package bll.EmBenefit;

import java.util.List;

import org.zkoss.zul.ListModelList;

import dal.EmBenefit.EmWelfareDal;

import Model.EmWelfareModel;

public class EmWelf_DetailBll {

	public List<EmWelfareModel> getList(EmWelfareModel ewm) {
		List<EmWelfareModel> list = new ListModelList<>();
		EmWelfareDal dal = new EmWelfareDal();
		list = dal.getList(ewm);

		return list;

	}
}
