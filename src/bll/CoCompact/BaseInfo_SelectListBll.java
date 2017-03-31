/*
 * 创建人：林少斌
 * 创建时间：2013-10-16
 * 用途：公司合同基本信息查询Bll
 */
package bll.CoCompact;

import impl.CheckStringImpl;

import java.util.List;

import org.zkoss.zul.ListModelList;

import Model.CoAgencyBaseModel;
import Model.CoCompactModel;

import service.CheckStringService;

import dal.CoCompact.CoCompactBaseDal;

public class BaseInfo_SelectListBll {
	private CoCompactBaseDal ccbDal = new CoCompactBaseDal();
	private List cocoBaseList;

	// 查询所有机构
	public List getCoCoBaseAll() {
		cocoBaseList = ccbDal.getCoCoBaseList();
		return cocoBaseList;
	}

	// 按条件查询公司的合同
	public List<CoCompactModel> searchCoCoBaseList(String company,
			String compactid, String state, String addname, String coco_client,
			String coco_type) {
		String sql = checkCoCoBaseSearchName(company, compactid, state,
				addname, coco_client, coco_type);
		// System.out.println(sql);
		cocoBaseList = ccbDal.getCoCoBaseList(sql);
		return cocoBaseList;
	}

	// 按查询语句搜索公司的合同
	public List<CoCompactModel> searchCoCoBaseList(String sql) {
		cocoBaseList = ccbDal.getCoCoBaseList(sql);
		return cocoBaseList;
	}

	// 按ID查询
	public List<CoCompactModel> searchCoCoBase(String coco_id) {
		String sql = "and coco_id=" + coco_id;
		cocoBaseList = ccbDal.getCoCoBaseList(sql);
		return cocoBaseList;
	}

	// 按ID查询合同信息
	public List<CoCompactModel> getcompact(Integer coco_id) {
		List<CoCompactModel> list = new ListModelList<>();
		list = ccbDal.getcocompactList(coco_id);
		return list;
	}

	// 查询添加人
	public List getCoCoAddname(String str) {
		List cocoAddname = ccbDal.getCoCoAddnameList(str);
		return cocoAddname;
	}

	// 委托机构查询拼接SQL
	public String checkCoCoBaseSearchName(String company, String compactid,
			String state, String addname, String coco_client, String coco_type) {
		CheckStringService ch = new CheckStringImpl();
		StringBuilder sql = new StringBuilder();

		// 公司名称
		if (company != "" && company != null && !company.equals("")) {
			company = company.trim();
			if (ch.isChinese(company)) {
				sql.append(" and company like '%");
				sql.append(company);
				sql.append("%' ");
			} else if (ch.isNum(company)) {
				sql.append(" and (a.cid='");
				sql.append(company);
				sql.append("' or company like '%");
				sql.append(company);
				sql.append("%')");
			}
		}

		// 合同编号
		if (compactid != "" && compactid != null && !compactid.equals("")) {
			compactid = compactid.trim();
			sql.append(" and coco_compactid LIKE'%");
			sql.append(compactid);
			sql.append("%' ");
		}

		// 客服
		if (coco_client != "" && coco_client != null && !coco_client.equals("")) {
			coco_client = coco_client.trim();
			sql.append(" and coba_client LIKE'%");
			sql.append(coco_client);
			sql.append("%' ");
		}

		// 合同标准
		if (coco_type != "" && coco_type != null && !coco_type.equals("")) {
			coco_type = coco_type.trim();
			if (coco_type.equals("标准版")) {
				coco_type = "0";
			}
			if (coco_type.equals("一般非标")) {
				coco_type = "1";
			}
			if (coco_type.equals("特殊非标")) {
				coco_type = "2";
			}
			if (coco_type.equals("客户提供")) {
				coco_type = "3";
			}
			sql.append(" and coco_class='");
			sql.append(coco_type);
			sql.append("' ");
		}

		// 合同状态
		if (state != "" && state != null && !state.equals("")) {
			state = state.trim();
			if (state.equals("inure")) {
				sql.append(" and coco_state >0");
				sql.append(" and coco_state >0");
			} else {
				sql.append(" and coco_state='");
				sql.append(state);
				sql.append("' ");
			}

		}

		// 添加人
		if (addname != "" && addname != null && !addname.equals("")) {
			addname = addname.trim();
			sql.append(" and coco_addname LIKE '%");
			sql.append(addname);
			sql.append("%' ");
		}
		if (sql == null) {
			return "";
		} else {
			return sql.toString();
		}

	}

	public CoCompactModel getCoCompactInfo(String coco_id) {
		return ccbDal.getCoCompactInfo(coco_id);
	}

	public CoAgencyBaseModel getAgencyInfo(Integer coco_id) {
		return ccbDal.getAgencyInfo(coco_id);
	}
}
