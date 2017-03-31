package bll.CoFinanceManage;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.zkoss.zul.ListModelList;

import service.InvoiceService;

import dal.PubCodeConversionDal;
import dal.CoBase.CoBase_OperateDal;
import dal.CoBase.CoBase_SelectDal;
import dal.CoFinanceManage.Cfma_BillSelDal;
import dal.CoFinanceManage.Cfma_SelDal;
import Model.CoBaseModel;
import Model.CoFinanceCollectModel;
import Model.CoFinanceMonthlyBillSortAccountModel;
import Model.CoFinanceTotalAccountModel;
import Model.CoInvoiceInfoModel;
import Model.CoInvoiceModel;
import Model.CoInvoiceRelationModel;
import Model.PubCodeConversionModel;
import Util.DateStringChange;

public class CoInvoiceBll {

	/**
	 * @Title: searchList
	 * @Description: TODO(查询账单费用列表)
	 * @param cid
	 * @param l
	 * @return
	 * @return List<CoFinanceMonthlyBillSortAccountModel> 返回类型
	 * @throws
	 */
	public List<CoFinanceCollectModel> searchList(Integer cid) {
		List<CoFinanceCollectModel> list = new ListModelList<>();
		Cfma_BillSelDal dal = new Cfma_BillSelDal();
		list = dal.getInvoiceList(cid);
		return list;
	}

	// 获取新发票号
	public String createInvoiceId() {

		InvoiceService is = new CoInvoiceImpl();
		String str = is.createInvoiceId();
		return str;
	}

	// 查询发票列表
	public List<CoInvoiceModel> getInvoiceList(CoInvoiceModel cm) {
		List<CoInvoiceModel> list = new ListModelList<>();
		InvoiceService is = new CoInvoiceImpl();
		list = is.search(cm);
		return list;
	}

	// 获取单个发票完整信息
	public CoInvoiceModel getInvoice(Integer id) {
		CoInvoiceModel cm = new CoInvoiceModel();
		List<CoInvoiceModel> list = new ListModelList<>();
		List<CoInvoiceInfoModel> list2 = new ListModelList<>();
		List<CoInvoiceRelationModel> list3 = new ListModelList<>();
		InvoiceService is = new CoInvoiceImpl();
		list = is.search(id);
		if (list.size() > 0) {
			cm = list.get(0);
			list2 = is.searchDetail(id);
			list3 = is.searchRelation(id);
			if (list2.size() > 0) {
				cm.setList(list2);
			}
			if (list3.size() > 0) {
				cm.setList2(list3);
			}
		}
		return cm;
	}

	// 查询发票科目
	public List<PubCodeConversionModel> getCodeList() {
		List<PubCodeConversionModel> list = new ListModelList<>();
		PubCodeConversionDal dal = new PubCodeConversionDal();
		list = dal.getListInfo(37, "发票科目");
		return list;
	}

	// 查询公司信息
	public CoBaseModel getCobaseInfo(Integer cid) {
		List<CoBaseModel> list = new ListModelList<>();
		CoBase_SelectDal dal = new CoBase_SelectDal();
		CoBaseModel cm = new CoBaseModel();
		cm.setCid(cid);
		list = dal.getCoBaseInfo(cm, null, null, null, null);
		if (list.size() > 0) {
			cm = list.get(0);
		}
		return cm;
	}

	// 获取公司总收款
	public BigDecimal getTotal(Integer cid) {
		BigDecimal bd = new BigDecimal(0);
		Cfma_SelBll bll = new Cfma_SelBll();
		CoFinanceTotalAccountModel cm = bll.getCompanyTotalAccount(cid);
		if (cm != null) {
			bd = cm.getTotalPaidIn();
		}
		return bd;
	}

	// 新增发票信息
	public Integer add(CoInvoiceModel cm, List<CoInvoiceInfoModel> list,
			List<CoFinanceCollectModel> list2) {
		InvoiceService is = new CoInvoiceImpl();
		Integer i = is.add(cm, list, list2);
		return i;
	}

	// 修改发票信息
	public Integer mod(CoInvoiceModel cm, List<CoInvoiceInfoModel> list,
			List<CoFinanceCollectModel> list2) {
		InvoiceService is = new CoInvoiceImpl();
		Integer i = is.mod(cm.getCoin_id(), cm, list, list2);
		return i;
	}

	// 删除发票信息
	public Integer del(Integer id) {
		InvoiceService is = new CoInvoiceImpl();
		Integer i = is.delInvoice(id);
		return i;
	}

	// 更新开票规则
	public Integer modCobase(Integer cid, String rule) {
		CoBase_OperateDal dal = new CoBase_OperateDal();
		CoBaseModel cm = new CoBaseModel();
		cm.setCoba_invoicerule(rule);
		Integer i = dal.modCobase(null, cid, cm);
		return i;
	}

	// 查询收款明细
	public List<CoFinanceMonthlyBillSortAccountModel> getbillInfo(String num) {
		List<CoFinanceMonthlyBillSortAccountModel> list = new ListModelList<>();
		Cfma_BillSelDal dal = new Cfma_BillSelDal();
		list = dal.getlist(num);
		return list;
	}

	
}
