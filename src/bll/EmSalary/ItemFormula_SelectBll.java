package bll.EmSalary;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import Model.CoFormulaInfoModel;
import Model.CoOwnmonthModel;
import Model.CoSalaryItemAttributeModel;
import Model.CoSalaryItemFormulaModel;
import Model.CoSalaryItemIDInfoModel;
import Model.ConnectionItemInfoModel;
import Model.EmBaseESInCFInModel;
import Model.EmSalaryBaseSel_viewModel;
import Model.EmTXTFileInfoModel;
import Model.EmTXTFileSetModel;

import dal.EmSalary.ItemFormula_SelectDal;

public class ItemFormula_SelectBll {
	private ItemFormula_SelectDal ifDal = new ItemFormula_SelectDal();
	private List ItemFormulaList;

	// 查询工资项目信息
	public List<CoSalaryItemFormulaModel> getItemFormula(String cid,
			String ownmonth, String itemid, String str) {

		String sql = "";
		if (!"".equals(cid)) {
			sql = sql + " AND cid=" + cid;
		}
		if (!"".equals(ownmonth)) {
			sql = sql + " AND ownmonth=" + ownmonth;
		}
		if (!"".equals(itemid)) {
			sql = sql + " AND csii_itemid='" + itemid + "'";
		}
		if (!"".equals(str)) {
			sql = sql + str;
		}
		ItemFormulaList = ifDal.getItemFormula(sql);

		return ItemFormulaList;

	}

	// 查询工资项目信息
	public List<CoSalaryItemFormulaModel> getItemFormulaCSIF(String cid,
			String ownmonth, String itemid, String str) {

		String sql = "";
		if (!"".equals(cid)) {
			sql = sql + " AND cid=" + cid;
		}
		if (!"".equals(ownmonth)) {
			sql = sql + " AND ownmonth=" + ownmonth;
		}
		if (!"".equals(itemid)) {
			sql = sql + " AND csii_itemid='" + itemid + "'";
		}
		if (!"".equals(str)) {
			sql = sql + str;
		}
		ItemFormulaList = ifDal.getItemFormulaCSIF(sql);

		return ItemFormulaList;

	}

	// 特殊关联项列表
	public List<ConnectionItemInfoModel> getConnectionItem(String str) {
		List<ConnectionItemInfoModel> conItemList;
		conItemList = ifDal.getConnectionItem(str);
		return conItemList;
	}

	// 项目属性列表
	public List<CoSalaryItemAttributeModel> getItemAttribute(String str) {
		List<CoSalaryItemAttributeModel> list;
		list = ifDal.getItemAttribute(str);
		return list;
	}

	// 获取有工资项目的公司
	public List<CoOwnmonthModel> getCobase(String str) {
		List<CoOwnmonthModel> cobaseList;
		cobaseList = ifDal.getCobase(str);
		return cobaseList;
	}

	// 获取有工资项目的月份
	public List<CoOwnmonthModel> getOwnmonth(String str) {
		List<CoOwnmonthModel> ownmonthList;
		ownmonthList = ifDal.getOwnmonth(str);
		return ownmonthList;
	}

	// 获取工资算法信息
	public List<CoFormulaInfoModel> getFormulaInfo(String str) {
		List<CoFormulaInfoModel> list;
		list = ifDal.getFormulaInfo(str);
		return list;
	}

	// 获取工资算法内容
	public List<CoSalaryItemFormulaModel> getFormulaData(String str) {
		List<CoSalaryItemFormulaModel> list;
		list = ifDal.getFormulaData(str);
		return list;
	}

	// 获取CoSalaryItemIDInfo数据
	public List<CoSalaryItemIDInfoModel> getCoSalaryItemID(String str) {
		List<CoSalaryItemIDInfoModel> list;
		list = ifDal.getCoSalaryItemID(str);
		return list;
	}

	// 获取算法分配的员工列表
	public List<EmBaseESInCFInModel> getEmBaESInList(String str) {
		List<EmBaseESInCFInModel> list;
		list = ifDal.getEmBaESInList(str);
		return list;
	}

	// 获取报盘数据txtdate下拉框
	public List<EmTXTFileInfoModel> getTXTDateList() {
		List<EmTXTFileInfoModel> list;
		list = ifDal.getTXTDateList();
		return list;
	}

	// 获取报盘数据txtBatch下拉框
	public List<EmTXTFileInfoModel> getTXTBatchList(String txtdate) {
		List<EmTXTFileInfoModel> list;
		list = ifDal.getTXTBatchList(txtdate);
		return list;
	}

	// 获取报盘数据
	public List<EmTXTFileInfoModel> getTXTDataList(String str) {
		List<EmTXTFileInfoModel> list;
		list = ifDal.getTXTDataList(str);
		return list;
	}

	// 获取报盘数据和支付模块数据
	public List<EmTXTFileInfoModel> getTXTEmPayDataList(String txt_date) {
		List<EmTXTFileInfoModel> list;
		list = ifDal.getTXTEmPayDataList(txt_date);
		return list;
	}

	// 获取报盘数据合计
	public String[] getTXTCountPay(String txt_date) {
		String[] count_pay = ifDal.getTXTCountPay(txt_date);
		return count_pay;
	}

	// 查询是否有代发工资、代报个税报价单项目
	public int chkIfGZ(String str) {
		return ifDal.chkIfGZ(str);
	}

	// 获取未参加工资算法的工资项目
	public List<CoSalaryItemFormulaModel> getNotJoinFormula(String csii_itemid) {
		List<CoSalaryItemFormulaModel> list = new ArrayList<CoSalaryItemFormulaModel>();
		List<CoSalaryItemFormulaModel> njfList;
		String formulaString;

		// 获取存在工资算法的工资项目
		njfList = ifDal.getNotJoinFormula(csii_itemid);

		// 获取算法内容字符串
		formulaString = ifDal.getFormulaString(csii_itemid);

		if ("".equals(formulaString)) {
			list = njfList;
		} else {
			// 找出没有参与工资算法的工资项目
			for (int i = 0; i < njfList.size(); i++) {
				if (formulaString.indexOf(njfList.get(i).getCsii_col()) <= 0) {
					CoSalaryItemFormulaModel model = new CoSalaryItemFormulaModel();
					model.setCsii_id(njfList.get(i).getCsii_id());
					model.setCsii_col(njfList.get(i).getCsii_col());
					model.setCsii_item_name(njfList.get(i).getCsii_item_name());
					model.setItem_name(njfList.get(i).getItem_name());
					list.add(model);
				}
			}
		}

		return list;
	}

	// 获取重发原因
	public List<String[]> getRepayReason() {
		ArrayList<String[]> dataList = new ArrayList<String[]>();
		dataList.add(new String[] { "1", "户名与账号不符" });
		dataList.add(new String[] { "2", "银行名表达不清" });
		dataList.add(new String[] { "3", "卡或帐号已销户" });
		dataList.add(new String[] { "4", "其它原因" });
		dataList.add(new String[] { "5", "卡或帐号所属地有误" });
		dataList.add(new String[] { "6", "卡或帐号不存在" });
		/* dataList.add(new String[] { "7", "名称无效或名称遗漏" }); */
		dataList.add(new String[] { "8", "卡或帐号已挂失" });
		dataList.add(new String[] { "9", "银行原因，请重新发送即可" });
		dataList.add(new String[] { "10", "卡或帐号未激活" });
		return dataList;
	}

	// 翻译重发原因
	public String tRepayReason(int d) {
		String result = "";

		switch (d) {
		case 1:
			result = "户名与账号不符";
			break;
		case 2:
			result = "银行名表达不清";
			break;
		case 3:
			result = "卡或帐号已销户";
			break;
		case 4:
			result = "其它原因";
			break;
		case 5:
			result = "卡或帐号所属地有误";
			break;
		case 6:
			result = "卡或帐号不存在";
			break;
		/*
		 * case 7: result = "名称无效或名称遗漏"; break;
		 */
		case 8:
			result = "卡或帐号已挂失";
			break;
		case 9:
			result = "银行原因，请重新发送即可";
			break;
		case 10:
			result = "卡或帐号未激活";
			break;
		}

		return result;
	}

	// 获取公司可操作非清零项目数据更新的月份
	public String[] getZeroItemOwnmonth(int cid) {
		String[] monthList;
		monthList = ifDal.getZeroItemOwnmonth(cid);
		return monthList;
	}

	// 获取公司可操作非清零项目数据更新的工资数据
	public List<EmSalaryBaseSel_viewModel> getZeroItemEsdaDataList(int cid,
			int ownmonth) {
		List<EmSalaryBaseSel_viewModel> list = new ArrayList<EmSalaryBaseSel_viewModel>();
		list = ifDal.getZeroItemEsdaDataList(cid, ownmonth);
		return list;
	}

	// 获取公司可操作非清零项目数据更新的工资数据
	public List<EmSalaryBaseSel_viewModel> getZeroItemEsdaDataList(String str) {
		List<EmSalaryBaseSel_viewModel> list = new ArrayList<EmSalaryBaseSel_viewModel>();
		list = ifDal.getZeroItemEsdaDataList(str);
		return list;
	}

	// 按cid获取公司算法
	public List<CoFormulaInfoModel> getFormulaInfoByCid(Integer cid) {
		List<CoFormulaInfoModel> list;
		String str = " and csii_itemid in (select csii_itemid from (select cid,MAX(ownmonth)ownmonth from CoSalaryItemIDInfo where cid="
				+ cid
				+ " group by cid)a left join CoSalaryItemIDInfo b on a.cid=b.cid and a.ownmonth=b.ownmonth)";
		list = ifDal.getFormulaInfo(str);
		return list;
	}

	// 获取当天最新的一条银行余额数据
	public EmTXTFileSetModel getTXTBalance() {
		return ifDal.getTXTBalance();
	}

}
