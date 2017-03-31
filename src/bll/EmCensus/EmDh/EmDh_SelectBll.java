package bll.EmCensus.EmDh;

import java.util.List;

import Model.EmArchiveRemarkModel;
import Model.EmDhFileModel;
import Model.EmDhModel;
import Model.EmbaseModel;
import dal.EmCensus.EmDh.EmDh_SelectDal;

public class EmDh_SelectBll {
	EmDh_SelectDal dal = new EmDh_SelectDal();

	// 根据条件查找员工信息
	public List<EmbaseModel> getEmbaseInfo(String str) {
		return dal.getEmbaseInfo(str);
	}

	// 查询报价单是否有档案托管或者户口挂靠
	public List<String> getCogListInfo(int gid, int ownmonth, String coli_name) {
		return dal.getCogListInfo(gid, ownmonth, coli_name);
	}

	// 根据条件查找调户信息
	public List<EmDhModel> getEmDhInfo(String str) {
		return dal.getEmDhInfo(str);
	}

	// 判断任务单流程的步骤
	public List<String> getStateId(String str) {
		return dal.getStateId(str);
	}

	// 获取调户备注
	public List<EmArchiveRemarkModel> getDhRemark(int emdh_id) {
		return dal.getDhRemark(emdh_id);
	}

	public String ishaveService(Integer gid, String coli_name) {
		return dal.ishaveService(gid, coli_name);
	}

	public List<String> getClient() {
		return dal.getClient();
	}

	// 查询员工是否已有调户信息
	public boolean ifExistDhInfo(Integer gid) {
		return dal.ifExistDhInfo(gid);
	}

	// 获取客服信息
	public List<String> getLoginInfo() {
		return dal.getLoginInfo();
	}

	// 查询材料归档材料
	public List<EmDhFileModel> getFileing() {
		return dal.getFileing();
	}

	// 根据id查询已选择的归档材料
	public List<EmDhFileModel> getDhFile(Integer emdh_id) {
		return dal.getDhFile(emdh_id);
	}

	// 根据cid查询社保所属账户
	public boolean getShebaoType(Integer cid) {
		return dal.getShebaoType(cid);
	}
}
