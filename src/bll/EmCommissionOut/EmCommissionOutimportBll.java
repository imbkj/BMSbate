package bll.EmCommissionOut;

import java.sql.SQLException;
import java.util.List;

import dal.EmCommissionOut.EmCommissionOutimportDal;
import Model.EmCommissionOutimportModel;
import Model.EmbaseModel;
import Model.SocialInsuranceAlgorithmViewModel;
import Model.wtoutFeeModel;

public class EmCommissionOutimportBll {
	
	private EmCommissionOutimportDal dal= new EmCommissionOutimportDal();
	//查询服务费名称列数据
	public List<String> getfeetitlelist() throws SQLException{
		return dal.getfeetitlelist();
	}
	//查询标准名列数据
	public List<String> gettitlelist() throws SQLException{
		return dal.gettitlelist();
	}
	//查询城市列数据
	public List<String> getcitylist() throws SQLException{
		return dal.getcitylist();	
	}
	//查询机构名列列数据
	public List<String> getnamelist() throws SQLException{
		return dal.getnamelist();
	}
	//查询wtoutFee服务列数据
	public List<wtoutFeeModel> getWtoutFeeList(String strsql) throws SQLException{
		return dal.getWtoutFeeList(strsql);
	}
	
	//查询当地标准名列数据
	public List<String> getsoinlist() throws SQLException{
		return dal.getsoinlist();
	}
	//查询当地城市列数据
	public List<String> getsoincitylist() throws SQLException{
		return dal.getsoincitylist();
	}
	//查询当地机构名列列数据
	public List<String> getsoinnamelist() throws SQLException{
		return dal.getsoinnamelist();
	}
	//查询当地标地服务SocialInsurance
	public List<SocialInsuranceAlgorithmViewModel> getSocialInsurance(String strsql) throws SQLException{
		return dal.getSocialInsurance(strsql);
	}
	//根据导入的execl 员工编号查询是否存在该员工信息
	public EmbaseModel findEmbaseInfo(String gid){
		return dal.findEmbaseInfo(gid);
	}
	//批量导入委托外地批量新增表 excel 文件内容
	public int importEntrustInfo(EmCommissionOutimportModel m) {
		return dal.importEntrustInfo(m);
	}
	//查询excel导入的委托外地信息
	public List<EmCommissionOutimportModel> getEntrustInfo(String strsql) throws SQLException{
		return dal.getEntrustInfo(strsql);
	}
	
	//获取导入表model
	public EmCommissionOutimportModel getEntrustInfomodel(int ecou_id) throws SQLException{
		return dal.getEntrustInfomodel(ecou_id);
	}
	
	
	//删除委托外地记录
	public int deleteEntrustInfo(Integer ecou_id){
		return dal.deleteEntrustInfo(ecou_id);
	}
	//更新委托外地记录
	public int updateEntrustInfo(EmCommissionOutimportModel mr){
		return dal.updateEntrustInfo(mr);
	}
	//查询员工姓名列数据
	public List<String> getecounamelist() throws SQLException{
		return dal.getecounamelist();
	}
	//查询添加人列数据
	public List<String> getaddnamelist() throws SQLException{
		return dal.getaddnamelist();
	}
	
	//身份核查
	public String[]  checkem(int ecou_id)
	{
		
		return dal.checkem(ecou_id);
		 
		
	}
}
