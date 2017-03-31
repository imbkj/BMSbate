package bll.CoCompact.CoCompactSA;

import impl.CheckStringImpl;

import java.util.List;

import service.CheckStringService;
import dal.CoCompact.CoCompactSA.Compact_BcPrintSelectDal;

public class Compact_BcPrintSelectBll {
	private Compact_BcPrintSelectDal ccbDal = new Compact_BcPrintSelectDal();
	private List cocoBaseList;

	// 查询所有机构
	public List getCoCoBaseAll() {
		cocoBaseList=ccbDal.getCoCoBaseList();
		return cocoBaseList;
	}
	
	// 按条件查询机构
	public List searchCoCoBase(String company,String compactid,String state,String addname) {
		String sql=checkCoCoBaseSearchName(company,compactid,state,addname);
		cocoBaseList=ccbDal.getCoCoBaseList(sql);
		return cocoBaseList;
	}
	
	//查询添加人
	public List getCoCoAddname(String str){
		List cocoAddname=ccbDal.getCoCoAddnameList(str);
		return cocoAddname;
	}
	
	// 委托机构查询拼接SQL
	public String checkCoCoBaseSearchName(String company,String compactid,String state,String addname) {
		CheckStringService ch = new CheckStringImpl();
		StringBuilder sql = new StringBuilder();
		
		//公司名称
		if(company != "" && company != null){
			company = company.trim();
			if(ch.isChinese(company))
			{
				sql.append(" and company like '%");
				sql.append(company);
				sql.append("%' ");
			}
			else if(ch.isNum(company))
			{
				sql.append(" and (cid='");
				sql.append(company);
				sql.append("' or company like '%");
				sql.append(company);
				sql.append("%')");
			}
		}
		
		//合同编号
		if(compactid != "" && compactid != null){
			compactid = compactid.trim();
            sql.append(" and coco_compactid LIKE'%");
            sql.append(compactid);
            sql.append("%' ");
		}
		
		//合同状态
		if(state != "" && state != null){
			state = state.trim();
            sql.append(" and coco_state='");
            sql.append(state);
            sql.append("' ");
		}
		
		//添加人
		if(addname != "" && addname != null){
			addname = addname.trim();
            sql.append(" and coco_addname LIKE '%");
            sql.append(addname);
            sql.append("%' ");
		}
		
		 return sql.toString();
	}
}
