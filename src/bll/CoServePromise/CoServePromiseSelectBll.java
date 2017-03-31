package bll.CoServePromise;

import java.util.List;

import dal.CoServePromise.CoServePromiseSelectDal;

import Model.CoBaseServePromiseModel;

public class CoServePromiseSelectBll {
	private CoServePromiseSelectDal dal = new CoServePromiseSelectDal();

	// 查询单位系统服务约定信息
	public List<CoBaseServePromiseModel> getPromiseList(String str) {
		return dal.getPromiseList(str);
	}

	// 查询是否已有材料
	public Integer ifHasDoc(Integer doin_id,Integer dsin_tid) {
		return dal.ifHasDoc(doin_id,dsin_tid);
	}
}
