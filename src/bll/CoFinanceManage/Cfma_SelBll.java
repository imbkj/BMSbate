package bll.CoFinanceManage;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import Model.CoBaseModel;
import Model.CoFinanceCollectModel;
import Model.CoFinanceMonthlyAccountModel;
import Model.CoFinanceMonthlyBillModel;
import Model.CoFinanceMonthlyBillSortAccountModel;
import Model.CoFinanceMonthlyBillTempletConModel;
import Model.CoFinanceMonthlyBillTempletModel;
import Model.CoFinanceTotalAccountModel;
import Model.CoInvoiceModel;
import Model.CollectAmountModel;
import Model.CoFinanceSortAccountssModel;

import dal.CoFinanceManage.Cfma_SelDal;

public class Cfma_SelBll {
	private Cfma_SelDal dal;

	public Cfma_SelBll() {
		dal = new Cfma_SelDal();
	}

	public List<String> getClientnameList() {
		return dal.getClientnameList();
	}
	public List<String> getClientnameListkf() {
		return dal.getClientnameListkf();
	}
	
	public List<String> getClientnameListxc() {
		return dal.getClientnameListxc();
	}
	
	
	
	

	public List<CoFinanceMonthlyBillModel> getBillList(int cid) {
		return dal.getBillList(cid);
	}

	public List<CoFinanceCollectModel> getCollectListPrint(List<String> l) {
		return dal.getCollectListPrint(l);
	}

	/**
	 * 获取今天的所有收款
	 * 
	 * @param addtime
	 * @return
	 */
	public List<CoFinanceCollectModel> getTodayCollect(String addtime) {

		return dal.getTodayCollect(addtime);
	}

	public List<String> getCoPAccountList() {
		return dal.getCoPAccountList();
	}
	
 

	 
	/**
	 * 获取 收款总额
	 * 
	 * @param addtime
	 * @return
	 */
	public List<CoFinanceSortAccountssModel> CoFinanceSortAccountssModellist(String strwhere) {
		
		return dal.getCoFinanceSortAccountssModellist(strwhere);
		
	}
	
	
	/**
	 * 获取收款list
	 * 
	 * @param addtime
	 * @return
	 */
	public List<CoFinanceSortAccountssModel> CoFinanceSortAccountssModellist1(String strwhere) {
		
		return dal.getCoFinanceSortAccountssModellist1(strwhere);
		
	}
	
	
	public List<CoFinanceCollectModel> getCfcListByCid(
			List<CoFinanceCollectModel> l) {
		List<CoFinanceCollectModel> li = new ArrayList<CoFinanceCollectModel>();
		List<CoFinanceCollectModel> list = dal.getCfcListByCid(l);
		for (int j = 0; j < list.size(); j++) {
			for (int i = 0; i < l.size(); i++) {
				if (list.get(j).getCid() == l.get(i).getCid()) {
					list.get(j).setCoba_company(l.get(i).getCoba_company());
					list.get(j).setOwnmonth(l.get(i).getOwnmonth());
					list.get(j).setCfco_remark(l.get(i).getCfco_remark());
					list.get(j).setCoba_client(l.get(i).getCoba_client());
					list.get(j).setCfco_addtime(l.get(i).getCfco_addtime());
				}
			}
			if (list.get(j).getCfmb_PersonnelReceivable() != null
					&& list.get(j).getCfmb_FinanceReceivable() != null) {
				list.get(j).setTotalReceivable(
						list.get(j).getCfmb_PersonnelReceivable()
								.add(list.get(j).getCfmb_FinanceReceivable()));
			}
			if (list.get(j).getCfmb_TotalPaidIn() != null
					&& list.get(j).getCfmb_LoanBalance() != null) {
				list.get(j).setImbalance(
						list.get(j).getCfmb_TotalPaidIn()
								.add(list.get(j).getCfmb_LoanBalance())
								.add(list.get(j).getCfmb_CarryForwardEx())
								.subtract(list.get(j).getTotalReceivable()));
				if (list.get(j).getImbalance().compareTo(BigDecimal.ZERO) == 0) {
					list.get(j).setCfmb_WriteOffs("已销帐");
					li.add(list.get(j));
				}
			}
		}

		return li;
	}

	/**
	 * 导出excel
	 * 
	 * @param ownmonth
	 * @param startDate
	 * @param endDate
	 */
	public List<CoInvoiceModel> searchCoInvoice(Date startDate, Date endDate) {
		// 获取当前系统的时间
		String date = new SimpleDateFormat("yyyyMMddHHmmss").format(System
				.currentTimeMillis());
		List<CoInvoiceModel> list = dal.searchCoInvoice(startDate, endDate);

		return list;
	}

	public List<CoFinanceMonthlyBillSortAccountModel> getCfmbList(
			String cfmb_number) {
		CoFinanceMonthlyBillSortAccountModel m = new CoFinanceMonthlyBillSortAccountModel();
		String total = "总计";
		BigDecimal Receivable = new BigDecimal(0);
		BigDecimal PaidIn = new BigDecimal(0);
		List<CoFinanceMonthlyBillSortAccountModel> l = dal
				.getCfmbList(cfmb_number);
		for (int i = 0; i < l.size(); i++) {
			Receivable = Receivable.add(l.get(i).getCfsa_Receivable());
			PaidIn = PaidIn.add(l.get(i).getCfsa_PaidIn());
		}
		m.setCfsa_cpac_name(total);
		m.setCfsa_Receivable(Receivable);
		m.setCfsa_PaidIn(PaidIn);
		m.sumImbalance();
		l.add(m);
		return l;
	}

	/**
	 * 根据cid查询收款
	 * 
	 * @return
	 */
	public List<CoFinanceMonthlyBillModel> getCfcList(int cid, String ownmonth,
			Date sd, Date ed, String remark, String count) {
		Cfma_BillSelBll bll = new Cfma_BillSelBll();
		List<CoFinanceMonthlyBillModel> l = dal.getCfcList(cid, ownmonth, sd,
				ed, remark, count);
		List<CoFinanceMonthlyBillModel> list = new ArrayList<CoFinanceMonthlyBillModel>();
		for (int i = 0; i < l.size(); i++) {
			if (l.get(i).getCfmb_number() == null) {
				l.get(i).setCfmb_PersonnelConfirm(-1);
				l.get(i).setCfmb_FinanceConfirm(-1);
				l.get(i).setCfmb_WriteOffsStr(null);
				l.get(i).setCfmb_name("公司收款");
			}
			if (l.get(i).getCfmb_number() != null) {
				CoFinanceMonthlyBillModel billModel = bll.getBillModel(l.get(i)
						.getCfmb_number());
				l.get(i).setCfmb_WriteOffsStr(billModel.getCfmb_WriteOffsStr());
				l.get(i).setCfmb_WriteOffs(billModel.getCfmb_WriteOffs());
			}
			if (l.get(i).getCfmb_TotalPaidIn() != null) {
				list.add(l.get(i));
			}

		}
		return list;
	}

	// 获取台账应收月份
	public List<String> getCoFinanceOwnmonth() {
		return dal.getCoFinanceOwnmonth();
	}

	// 获取财务部用户
	public List<String> getCwLoginname() {
		return dal.getCwLoginname();
	}

	// 获取公司每月应收统计表数据
	public List<CoFinanceMonthlyAccountModel> getMonthlyAccount(String where) {
		return dal.getMonthlyAccount(where);
	}

	// 获取公司每月应收统计表数据
	/*
	 * public List<CoFinanceMonthlyAccountModel> getMonthlyAccount(int cfta_id)
	 * { return dal.getMonthlyAccount(cfta_id); }
	 */

	// 获取公司每月应收统计表数据
	public CoFinanceMonthlyAccountModel getMonthlyAccountByCfmaId(int cfma_id) {
		return dal.getMonthlyAccountByCfmaId(cfma_id);
	}

	// 获取公司总账表数据
	public List<CoFinanceTotalAccountModel> getTotalAccount(String where) {
		return dal.getTotalAccount(where);
	}

	// 公司总账合计
	public CoFinanceTotalAccountModel getCompanyTotalAccount(int cid) {
		return dal.getCompanyTotalAccount(cid);
	}

	// 获取账单数据
	public List<CoFinanceMonthlyBillModel> getMonthlyBill(int cfta_id,
			int ownmonth) {
		return dal.getMonthlyBill(cfta_id, ownmonth);
	}

	// 根据搜索条件获取账单数据
	public List<CoFinanceMonthlyBillModel> getAllBill(String where) {
		return dal.getAllBill(where);
	}

	// 获取该公司所有账单数据
	public List<CoFinanceMonthlyBillModel> getCompanyAllBill(int cfta_id) {
		return dal.getCompanyAllBill(cfta_id);
	}

	// 公司台账按月查询详细
	public CoFinanceMonthlyAccountModel getMonthlyCompanyView(int cid,
			int ownmonth) {
		return dal.getMonthlyCompanyView(cid, ownmonth);
	}

	// 查询公司台账详细
	public List<CoFinanceMonthlyAccountModel> getMonthlyCompanyFinance(int cid) {
		return dal.getMonthlyCompanyFinance(cid);
	}

	// 获取公司台账财务科目
	public List<CoFinanceMonthlyBillSortAccountModel> getMonthlySortAccount(
			int cid, int ownmonth) {
		return dal.getMonthlySortAccount(cid, ownmonth);
	}

	// 统计当月销账数据
	public int[] getOwnmonthCount(int ownmonth) {
		return dal.getOwnmonthCount(ownmonth);
	}

	// 获取账单生成模板
	public List<CoFinanceMonthlyBillTempletModel> getBillTemplet() {
		return dal.getBillTemplet();
	}
	// 获取账单生成模板
	public List<CoFinanceMonthlyBillTempletModel> getBillTemplet(int depid) {
		return dal.getBillTemplet(depid);
	}

	// 获取当月未分配账单业务的合同信息
	public List<CoFinanceMonthlyBillTempletConModel> getBillTempletCompact(
			int cid, int ownmonth) {
		return dal.getBillTempletCompact(cid, ownmonth);
	}

	// 获取当月未分配账单业务的雇员信息
	public List<CoFinanceMonthlyBillTempletConModel> getBillTempletEmbase(
			int cid, int ownmonth) {
		return dal.getBillTempletEmbase(cid, ownmonth);
	}

	// 获取当月公司业务委托地区信息
	public List<CoFinanceMonthlyBillTempletConModel> getBillTempletCommissionOutCity(
			int cid, int ownmonth) {
		return dal.getBillTempletCommissionOutCity(cid, ownmonth);
	}

	// 获取当月公司的服务项目信息(未加账单)
	public List<CoFinanceMonthlyBillTempletConModel> getBillTempletItem(
			int cid, int ownmonth) {
		return dal.getBillTempletItem(cid, ownmonth);
	}

	// 收款纪录
	public List<CoFinanceCollectModel> getCollectLog(int cid) {
		return dal.getCollectLog(cid);
	}
	
	
	//获取领款列表
	public List<CoFinanceCollectModel> getCollectlk(String wheresql) throws Exception {
		return dal.getCollectlk(wheresql);
	}
	

	
	public List<CollectAmountModel> getCoinfoListall(){
		return dal.getCoinfoListall();
	}
	
	
	// 将amount的纵向数据加到账单列表中
	public List<CoBaseModel> setAmountToCollect(String ownmonth, Date sdate,
			Date edate, String remark) {
		List<CoBaseModel> coList = dal.getCoinfoList();
		List<CoFinanceCollectModel> collects = dal.getCollectList(ownmonth,
				sdate, edate, remark);
	
		for (CoBaseModel m : coList) {
			List<CoFinanceCollectModel> l = new ArrayList<CoFinanceCollectModel>();
			// 循环的查询账单号
			for (CoFinanceCollectModel m1 : collects) {
				if (m.getCid() == m1.getCid()) {
					l.add(m1);
				}
			}
			m.setCfcms(l);
			
		}
	
		return coList;
	}

	// 添加发票领取人
	public int addInvoiceReceive(CoInvoiceModel m) {
		return dal.addInvoiceReceive(m);
	}
}
