package bll.Back;

import Model.BackCauseInfoModel;
import dal.Back.BackAddDal;

public class BackAddBll {
	private BackAddDal dal=new BackAddDal();
	
	public Integer back(BackCauseInfoModel m)
	{
		return dal.back(m);
	}

}
