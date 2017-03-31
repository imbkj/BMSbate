package bll.CoReg;

import java.util.List;

import dal.CoReg.CoReg_ResponseBookDal;

import Model.ResponsibilityBookModel;

public class CoReg_ResponseBookBll {
	
	private CoReg_ResponseBookDal dal = new CoReg_ResponseBookDal();

	//查询就业登记列表
	public List<ResponsibilityBookModel> getRlist(){
		
		return dal.getRlist();
	}
}
