package bll.CoServePromise;

import Model.CoBaseServePromiseModel;
import dal.CoServePromise.CoServePromiseOperateDal;

public class CoServePromiseOperateBll {
	private CoServePromiseOperateDal dal=new CoServePromiseOperateDal();
	//单位系统服务约定信息新增
	public Integer CoBaseServePromise_Add(CoBaseServePromiseModel m) {
		return dal.CoBaseServePromise_Add(m);
	}
	
	//单位系统服务约定信息修改
	public Integer CoBaseServePromise_Update(CoBaseServePromiseModel m) {
		return dal.CoBaseServePromise_Update(m);
	}

}
