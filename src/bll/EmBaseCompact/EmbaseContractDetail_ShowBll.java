package bll.EmBaseCompact;

import java.sql.SQLException;
import java.util.List;

import Model.EmbaseContractDetailModel;
import dal.EmBaseCompact.EmbaseContractDetail_ShowDal;

public class EmbaseContractDetail_ShowBll {
	EmbaseContractDetail_ShowDal dal = new EmbaseContractDetail_ShowDal();
	//获取公司列表
	public List<String> getCompanyList(){
			return dal.getCompanyList();
	}
	//获取员工列表
	public List<String> getNameList(){
		return dal.getNameList();
	}
	//获取身份证列表
	public List<String> getIdcardList(){
		return dal.getIdcardList();
	}
	//获取客服列表
	public List<String> getClientList(){
		return dal.getClientList();
	}
	//获取添加人列表
	public List<String> getAddnameList(){
		return dal.getAddnameList();
	}
	//获取员工合同详情
	public List<EmbaseContractDetailModel> getEmbaseContractDetail(String strsql) throws SQLException{
		return dal.getEmbaseContractDetail(strsql);
	}
	//审核
	public int appCommont(int puof_id){
		return dal.appCommont(puof_id);
	}
}
