package bll.EmFinanceManage;

import java.math.BigDecimal;
import java.util.Collections;
import java.util.List;

import org.zkoss.zhtml.Big;
import org.zkoss.zul.ListModelList;

import Model.AccountToInvoiceModel;
import Model.CoFinanceSortAccountssModel;
import Model.FinanceInvoiceDetailModel;
import Model.FinanceInvoiceModel;
import Util.UserInfo;
import dal.CoFinanceManage.AccountToInvoiceDal;
import dal.EmFinanceManage.CoFinanceSortAccountssNewDal;

public class Finance_InvoiceBll {
	private Finance_InvoiceBll() {
	}

	private static final Finance_InvoiceBll single = new Finance_InvoiceBll();

	// 静态工厂方法
	public static Finance_InvoiceBll getInstance() {
		return single;
	}

	// 查询收款
	public List<CoFinanceSortAccountssModel> getList(String cfso_id) {
		List<CoFinanceSortAccountssModel> list = new ListModelList<>();
		CoFinanceSortAccountssNewDal dal = CoFinanceSortAccountssNewDal
				.getInstance();
		list = dal.getList(cfso_id);
		return list;
	}

	// 查询发票信息
	public List<FinanceInvoiceModel> getInvoiceList(String cfso_id) {
		List<FinanceInvoiceModel> list = new ListModelList<>();
		CoFinanceSortAccountssNewDal dal = CoFinanceSortAccountssNewDal
				.getInstance();
		list = dal.getInvoiceList(cfso_id);
		return list;
	}

	// 计算发票金额
	public FinanceInvoiceModel splitInvoice(
			List<CoFinanceSortAccountssModel> l, String type,
			FinanceInvoiceModel fm) {

		List<FinanceInvoiceDetailModel> dmList = initDetail(type);
		boolean allin = false;
		boolean sb = false;
		boolean gz = false;
		BigDecimal total = BigDecimal.ZERO;
		BigDecimal cb = BigDecimal.ZERO;
		BigDecimal tax = BigDecimal.ZERO;
		// 收款明细
		for (CoFinanceSortAccountssModel m : l) {
			if (!allin) {
				if (m.getCfss_allin()) {
					allin = true;
				}
			}
			// AccountToInvoice
			for (FinanceInvoiceDetailModel m2 : dmList) {

				if (m2.getType().equals(m.getCfss_type())
						&& m2.getKm().equals(m.getCfss_cpac_name())) {
					boolean b = false;
					total = total.add(m.getCfss_Receivable());
					for (FinanceInvoiceDetailModel m3 : fm.getList()) {
						if (m3.getSpmc().equals(m2.getSpmc())) {
							b = true;

							if (m2.getSingle().equals(21)) {

								if (!m2.getJe().equals("0.00")) {
									sb = true;
								}
							}
							if (m2.getSingle().equals(31)
									&& !m2.getJe().equals("0.00")) {
								gz = true;
							}
							BigDecimal fee = new BigDecimal(m3.getJe());
							fee = fee.add(m.getCfss_Receivable());
							m3.setJe(fee.toString());
							if (m3.getKm2() == null) {
								m3.setKm2(m2.getKm2());
							}
						}
					}
					if (!b) {
						if (m2.getSingle().equals(21)) {

							if (!m2.getJe().equals("0.00")) {
								sb = true;
							}
						}
						if (m2.getSingle().equals(31)
								&& !m2.getJe().equals("0.00")) {
							gz = true;
						}
						FinanceInvoiceDetailModel dm = new FinanceInvoiceDetailModel();
						dm.setType(m2.getType());
						dm.setSpmc(m2.getSpmc());
						dm.setKm(m2.getKm());
						dm.setKm2(m2.getKm2());
						dm.setKind(m2.getKind());
						dm.setJe(m.getCfss_Receivable().toString());
						dm.setSlv(m2.getSlv());
						dm.setSsbm(m2.getSsbm());
						dm.setSort(m2.getSort());
						dm.setSingle(m2.getSingle());
						fm.getList().add(dm);
					}
				}
			}
			if (allin) {
				boolean b2 = false;
				for (FinanceInvoiceDetailModel d : fm.getList()) {
					if (d.getSingle().equals(1)) {
						b2 = true;
					}
				}
				if (!b2) {
					for (FinanceInvoiceDetailModel d : dmList) {
						if (d.getSingle().equals(1)) {
							FinanceInvoiceDetailModel dm = new FinanceInvoiceDetailModel();
							dm.setType(d.getType());
							dm.setSpmc(d.getSpmc());
							dm.setKm(d.getKm());
							dm.setKm2(d.getKm2());
							dm.setKind(d.getKind());
							dm.setJe("0");
							dm.setSlv(d.getSlv());
							dm.setSsbm(d.getSsbm());
							dm.setSort(d.getSort());
							dm.setSingle(d.getSingle());
							fm.getList().add(dm);
						}
					}
				}
			}
		}

		if (!sb) {
			for (FinanceInvoiceDetailModel m : fm.getList()) {
				if (m.getSingle().equals(22)) {
					m.setSpmc(m.getKm2());
				}
			}
		}
		if (!gz) {
			for (FinanceInvoiceDetailModel m : fm.getList()) {
				if (m.getSingle().equals(32)) {
					m.setSpmc(m.getKm2());
				}
			}
		}

		if (allin) {
			for (int i = 0; i < fm.getList().size(); i++) {
				if (fm.getList().get(i).getSingle().equals(1)) {
					fm.getList().get(i).setJe(total.toString());
					fm.getList().get(i).setSpmc(fm.getList().get(i).getKm2());
				} else {
					fm.getList().remove(i);
					i--;
				}
			}
		}

		for (int i = 0; i < fm.getList().size(); i++) {
			if (fm.getList().get(i).getJe().equals("0.00")) {
				fm.getList().remove(i);
				i--;
			}
		}

		return fm;
	}

	public List<FinanceInvoiceDetailModel> initDetail(String type) {
		List<FinanceInvoiceDetailModel> list = new ListModelList<>();
		List<AccountToInvoiceModel> list2 = new ListModelList<>();

		AccountToInvoiceDal dal = AccountToInvoiceDal.getInstance();
		list2 = dal.getlist(type);
		for (AccountToInvoiceModel m : list2) {
			FinanceInvoiceDetailModel dm = new FinanceInvoiceDetailModel();
			dm.setType(m.getAtin_type());
			dm.setSpmc(m.getAtin_km());
			dm.setKm(m.getAtin_account());
			dm.setKm2(m.getAtin_km2());
			dm.setKind(m.getAtin_kind());
			dm.setJe("0");
			dm.setSlv(m.getAtin_slv().toString());
			dm.setSsbm(m.getAtin_spbm());
			dm.setSingle(m.getAtin_single());
			dm.setSort(m.getAtin_sort());
			list.add(dm);
		}

		return list;
	}

	public Integer add(FinanceInvoiceModel fm) {
		CoFinanceSortAccountssNewDal dal = CoFinanceSortAccountssNewDal
				.getInstance();
		Integer i = 0;
		i = dal.addinfo(fm.getDjh(), fm.getFplx().toString(), fm.getGfmc(),
				fm.getGfsh(), fm.getGfyhzh(), fm.getGfdzdh(), fm.getBz(),
				UserInfo.getUsername());

		if (i > 0) {
			Integer k = 1;
			for (FinanceInvoiceDetailModel m : fm.getList()) {

				i = dal.addDetail(fm.getDjh().toString(), k, m.getSpmc(),
						m.getGgxh(), m.getJldw(), m.getJe(), m.getSlv(),
						m.getSpsm(), m.getSsbm());
				k++;
			}
		}
		return i;
	}
}
