package bll.CoCompact;

import impl.CheckStringImpl;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import service.CheckStringService;
import dal.CoCompact.CompactDetailDal;

public class Compact_DetailBll {
	private CompactDetailDal ccbDal = new CompactDetailDal();
	private List cocoBaseList;
	String message;

	// 新增产品
	public String addco(String coli_id, String coli_fee, String coli_date) {

		message = ccbDal.addco(coli_id, coli_fee, coli_date);
		return message;
	}

	// 变更费用
	public String changeco(String coli_id, String coli_fee, String coli_date) {

		message = ccbDal.changeco(coli_id, coli_fee, coli_date);
		return message;
	}

	// 变更费用(全国项目)
	public String changecoqg(String coli_id, String coli_fee, String coli_date) {

		message = ccbDal.changecoqg(coli_id, coli_fee, coli_date);
		return message;
	}

	// 终止
	public String stopco(String coli_id, String coli_fee, String coli_date) {

		message = ccbDal.stopco(coli_id, coli_fee, coli_date);
		return message;
	}

	// 变更收费金额
	public List getCoCoBaseAll() {
		cocoBaseList = ccbDal.getCoCoBaseList();
		return cocoBaseList;
	}

	// Date类型转换String
	public String DatetoSting(Date d) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMM");
		String Date = sdf.format(d);
		return Date;
	}

	// 委托机构查询拼接SQL
	public String checkCoCoBaseSearchName(String company, String compactid,
			String state, String addname) {
		CheckStringService ch = new CheckStringImpl();
		StringBuilder sql = new StringBuilder();

		// 公司名称
		if (company != "" && company != null) {
			company = company.trim();
			if (ch.isChinese(company)) {
				sql.append(" and company like '%");
				sql.append(company);
				sql.append("%' ");
			} else if (ch.isNum(company)) {
				sql.append(" and (cid='");
				sql.append(company);
				sql.append("' or company like '%");
				sql.append(company);
				sql.append("%')");
			}
		}

		// 合同编号
		if (compactid != "" && compactid != null) {
			compactid = compactid.trim();
			sql.append(" and coco_compactid LIKE'%");
			sql.append(compactid);
			sql.append("%' ");
		}

		// 合同状态
		if (state != "" && state != null) {
			state = state.trim();
			sql.append(" and coco_state='");
			sql.append(state);
			sql.append("' ");
		}

		// 添加人
		if (addname != "" && addname != null) {
			addname = addname.trim();
			sql.append(" and coco_addname LIKE '%");
			sql.append(addname);
			sql.append("%' ");
		}

		return sql.toString();
	}
}
