package bll.CoLatencyClient;

import java.util.List;

import Model.CoBaseModel;
import Model.CoLatencyClientModel;
import Model.CoServiceRequestModel;
import dal.CoLatencyClient.CoServiceRequestSelectDal;

public class CoServiceRequestSelectBll {
	private CoServiceRequestSelectDal dal = new CoServiceRequestSelectDal();

	// 根据cid查询公司的合同信息表id
	public Integer getCocoId(Integer cid) {
		return dal.getCocoId(cid);
	}

	// 获取公司信息
	public CoBaseModel getCobaseInfo(String str) {
		CoBaseModel model = new CoBaseModel();
		List<CoBaseModel> list = dal.getCobaseInfoList(str);
		if (list.size() > 0) {
			model = list.get(0);
		}
		return model;
	}

	public List<String> getLoginlist() {
		return dal.getLoginlist();
	}
	
	public List<String> getLoginlistByCid(Integer cid)
	{
		return dal.getLoginlistByCid(cid);
	}

	// 查询客服经理
	public List<String> getClientManagerlist(Integer cid) {
		return dal.getClientManagerlist(cid);
	}

	// 查询各部门经理
	public List<String> getManagerlist() {
		return dal.getManagerlist();
	}

	// 获取财务部人员
	public List<String> getSalarylist() {
		return dal.getSalarylist();
	}

	// 获取服务要求信息
	public CoServiceRequestModel getRequestInfo(String str) {
		CoServiceRequestModel model = new CoServiceRequestModel();
		List<CoServiceRequestModel> list = getRequestInfoList(str);
		if (list.size() > 0) {
			model = list.get(0);
		}
		return model;
	}

	// 获取服务要求信息
	public List<CoServiceRequestModel> getRequestInfoList(String str) {
		return dal.getRequestInfoList(str);
	}

	// 查询公司全称是否已存在
	public boolean ifExist(String company) {
		return dal.ifExist(company);
	}

	// 查询公司简称是否已存在
	public boolean ifExistShortName(String company) {
		return dal.ifExistShortName(company);
	}

	// 根据cid获取潜在客户信息
	public CoLatencyClientModel getCoLatencyClient(Integer cid) {
		return dal.getCoLatencyClient(cid);
	}
}
