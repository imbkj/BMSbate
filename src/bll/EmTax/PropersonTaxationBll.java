package bll.EmTax;

import java.sql.SQLException;
import java.util.List;

import Model.PropersonTaxationModel;
import dal.EmTax.PropersonTaxationDal;

public class PropersonTaxationBll {
	private PropersonTaxationDal dal =new PropersonTaxationDal();
	//获取客服列表
	public List<String> getClientList(){
		return dal.getClientList();
	}
	public List<PropersonTaxationModel> findBigPropersonTaxationList(Integer clientName) throws SQLException{
		return dal.findBigPropersonTaxationList(clientName);
	}
	//获取大类
	public List<PropersonTaxationModel> findPropersonTaxationList(Integer clientName) throws SQLException{
		return dal.findPropersonTaxationList(clientName);
	}
	//获取财务类数据
	public List<PropersonTaxationModel> findPropersonTaxationFinancialList(Integer clientName) throws SQLException{
		return dal.findPropersonTaxationFinancialList(clientName);
	}
	//派遣明细
	public List<PropersonTaxationModel> findPropersonTaxationSendList(String clientName) throws SQLException{
		return dal.findPropersonTaxationSendList(clientName);
	}
	//代理明细
	public List<PropersonTaxationModel> findPropersonTaxationAgentList(String clientName) throws SQLException{
		return dal.findPropersonTaxationAgentList(clientName);
	}
	//财税明细
	public List<PropersonTaxationModel> findPropersonTaxationTaxList(String clientName) throws SQLException{
		return dal.findPropersonTaxationTaxList(clientName);
	}
	//委托外地总人数 
	public List<PropersonTaxationModel> getBigEntrustList(Integer clientName) throws SQLException{
		return dal.getBigEntrustList(clientName);
	}
	
	//委托外地分人数 
	public List<PropersonTaxationModel> getEntrustList(Integer clientName) throws SQLException{
		return dal.getEntrustList(clientName);
	}
	 //委托外地明细
	public List<PropersonTaxationModel> getEntrustDetailList(String clientName,String cpct_type) throws SQLException{
		return dal.getEntrustDetailList(clientName, cpct_type);
	}
	//外籍人汇总
	public List<PropersonTaxationModel> getForexList(Integer clientName) throws SQLException{
		return dal.getForexList(clientName);
	}
	//外籍人详情
	public List<PropersonTaxationModel> getForexDetailList(String clientName) throws SQLException{
		return dal.getForexDetailList(clientName);
	}
}
	
