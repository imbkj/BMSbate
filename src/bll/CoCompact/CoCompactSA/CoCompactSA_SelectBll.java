package bll.CoCompact.CoCompactSA;

import impl.CheckStringImpl;

import java.util.ArrayList;
import java.util.List;

import service.CheckStringService;
import Model.CoCompactSAModel;
import dal.CoCompact.CoCompactSA.CoCompactSA_SelectDal;

public class CoCompactSA_SelectBll {
	private CoCompactSA_SelectDal ccsaSDal = new CoCompactSA_SelectDal();
	private List ccsaBaseList;

	// 查询所有机构
	public List getCCSABaseAll() {
		ccsaBaseList=ccsaSDal.getCCSABaseList();
		return ccsaBaseList;
	}
	
	// 按条件查询
	public List searchCCSABase(String company,String compactid,String state,String addname) {
		String sql=checkCCSABaseSearchName(company,compactid,state,addname);
		ccsaBaseList=ccsaSDal.getCCSABaseList(sql);
		return ccsaBaseList;
	}
	
	// 按ID查询
	public List<CoCompactSAModel> searchCCSABase(int ccsa_id) {
		String sql="and ccsa_id="+ccsa_id;
		ccsaBaseList=ccsaSDal.getCCSABaseList(sql);
		return ccsaBaseList;
	}
	
	
	//查询添加人
	public List getCCSAAddname(String str){
		List cocoAddname=ccsaSDal.getCoCoAddnameList(str);
		return cocoAddname;
	}
	
	// 委托机构查询拼接SQL
	public String checkCCSABaseSearchName(String company,String compactid,String state,String addname) {
		CheckStringService ch = new CheckStringImpl();
		StringBuilder sql = new StringBuilder();
		
		//公司名称
		if(company != "" && company != null && !company.equals("")){
			company = company.trim();
			if(ch.isChinese(company))
			{
				sql.append(" and coba_company like '%");
				sql.append(company);
				sql.append("%' ");
			}
			else if(ch.isNum(company))
			{
				sql.append(" and (cid='");
				sql.append(company);
				sql.append("' or coba_company like '%");
				sql.append(company);
				sql.append("%')");
			}
		}
		
		//合同编号
		if(compactid != "" && compactid != null && !compactid.equals("")){
			compactid = compactid.trim();
            sql.append(" and coco_compactid LIKE'%");
            sql.append(compactid);
            sql.append("%' ");
		}
				
		//添加人
		if(addname != "" && addname != null && !addname.equals("")){
			addname = addname.trim();
            sql.append(" and ccsa_addname LIKE '%");
            sql.append(addname);
            sql.append("%' ");
		}
		
		 return sql.toString();
	}
	
	//获取补充协议的模板
	public ArrayList<String> getCoCompactSAPubOffice(int id)
	{
		CoCompactSA_SelectDal dal=new CoCompactSA_SelectDal();
		return dal.getCoCompactSAPubOffice(id);
	}
}
