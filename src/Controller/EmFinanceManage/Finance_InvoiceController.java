package Controller.EmFinanceManage;

import java.lang.reflect.InvocationTargetException;
import java.sql.SQLException;
import java.util.Collections;
import java.util.List;

import org.zkoss.bind.annotation.Command;
import org.zkoss.bind.annotation.NotifyChange;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.event.EventListener;
import org.zkoss.zul.ListModelList;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Messagebox.ClickEvent;

import Model.CoBaseModel;
import Model.CoFinanceSortAccountssModel;
import Model.CoFinanceVATModel;
import Model.FinanceInvoiceModel;
import Model.SystLogModel;
import Util.EntitiesComparedUtils;
import Util.UserInfo;
import bll.CoLatencyClient.CoFinanceVatBll;
import bll.EmFinanceManage.Finance_InvoiceBll;
import bll.SystemControl.SystLogInfoBll;

public class Finance_InvoiceController {

	private FinanceInvoiceModel fim = new FinanceInvoiceModel();// 专票
	private FinanceInvoiceModel fim2 = new FinanceInvoiceModel();// 普票
	private CoFinanceVATModel cfm = new CoFinanceVATModel();
	private CoFinanceVATModel cfm2 = new CoFinanceVATModel();// 对比用历史数据
	private CoFinanceVatBll vatBll = new CoFinanceVatBll();
	private Finance_InvoiceBll bll = Finance_InvoiceBll.getInstance();
	private SystLogInfoBll lbll = new SystLogInfoBll();
	private boolean simple = false;
	private boolean only = false;
	private List<FinanceInvoiceModel> invoiceList = new ListModelList<>();
	private List<SystLogModel> cfmLog = new ListModelList<>();

	private String cfso_id = "";

	public Finance_InvoiceController() {
		if (Executions.getCurrent().getArg().get("model") != null) {
			CoBaseModel m = (CoBaseModel) Executions.getCurrent().getArg()
					.get("model");
			// 获取收款记录
			cfso_id = m.getCfss_cfso_id();

			// 获取财务记录的发票信息
			try {
				List<CoFinanceVATModel> list = vatBll.getCoFinanceVatDat(m
						.getCid());
				if (list.size() > 0) {
					cfm = list.get(0);
					// 写入发票信息
				} else {
					cfm.setCid(m.getCid());
					cfm.setCfva_company(m.getCoba_company());
					cfm.setCfva_only(true);
					cfm.setCfva_simple(true);
					cfm.setCfva_sp(false);
					cfm.setCfva_confirm(false);
					vatBll.addCoFinanceVat(cfm);
				}
				cfmLog=vatBll.getModLog(cfm.getCid());
				updateInvoice();
				searchInvoice();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		cfm2 = (CoFinanceVATModel) cfm.clone();
	}

	public void searchInvoice() {
		invoiceList = bll.getInvoiceList(cfso_id);
	}

	public void updateInvoice() {
		if (cfm != null) {

			fim.setFplx(1);
			fim.setGfmc(cfm.getCfva_title() == null ? "" : cfm.getCfva_title());
			fim.setGfsh(cfm.getCfva_number1() == null ? "" : cfm
					.getCfva_number1());
			fim.setGfdzdh((cfm.getCfva_reg_add() == null ? "" : cfm
					.getCfva_reg_add())
					+ " "
					+ (cfm.getCfva_tel() == null ? "" : cfm.getCfva_tel()));
			fim.setGfyhzh((cfm.getCfva_bank() == null ? "" : cfm.getCfva_bank())
					+ " "
					+ (cfm.getCfva_bank_acc() == null ? "" : cfm
							.getCfva_bank_acc()));

			fim2.setFplx(0);
			fim2.setGfmc(cfm.getCfva_title() == null ? "" : cfm.getCfva_title());
			fim2.setGfsh(cfm.getCfva_number1() == null ? "" : cfm
					.getCfva_number1());
			fim2.setGfdzdh((cfm.getCfva_reg_add() == null ? "" : cfm
					.getCfva_reg_add())
					+ " "
					+ (cfm.getCfva_tel() == null ? "" : cfm.getCfva_tel()));
			fim2.setGfyhzh((cfm.getCfva_bank() == null ? "" : cfm
					.getCfva_bank())
					+ " "
					+ (cfm.getCfva_bank_acc() == null ? "" : cfm
							.getCfva_bank_acc()));
			sumTotal(cfso_id);
			if (fim.getList().size() > 0) {
				fim.setDisplay(true);
				fim.setPrintState(true);
				Collections.sort(fim.getList());
			}
			if (fim2.getList().size() > 0) {
				Collections.sort(fim2.getList());
				fim2.setDisplay(true);
				fim2.setPrintState(true);
			}

		}
	}

	@Command
	@NotifyChange("cfm")
	public void mod() {
		String str = checkinfo();
		if (!str.equals("")) {
			Messagebox.show(str, "操作提示", Messagebox.OK, Messagebox.ERROR);
			return;
		}
		try {
			List<SystLogModel> list = EntitiesComparedUtils.compareModel(cfm2,
					cfm, CoFinanceVATModel.class);
			for (SystLogModel m : list) {
				lbll.addLog(null, cfm.getCfva_id(), cfm.getCid(), 0, "发票信息",
						m.getContent(), UserInfo.getUsername());
			}
		} catch (InstantiationException | IllegalAccessException
				| NoSuchMethodException | SecurityException
				| IllegalArgumentException | InvocationTargetException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		Integer i = vatBll.updateCoFinanceVat(cfm);
		cfm2 = (CoFinanceVATModel) cfm.clone();
		if (i > 0) {
			Messagebox.show("修改成功", "操作提示", Messagebox.OK,
					Messagebox.INFORMATION);
		} else {
			Messagebox.show("修改失败", "操作提示", Messagebox.OK, Messagebox.ERROR);
		}
	}

	public void sumTotal(String cfso_id) {
		// 读取收款记录
		List<CoFinanceSortAccountssModel> list = bll.getList(cfso_id);
		if (list.size() > 0) {
			if (list.get(0).getCfss_type() != null) {

				String type = list.get(0).getCfss_type();
				fim = bll.splitInvoice(list, type, fim);
				if (cfm.getCfva_only() != null && cfm.getCfva_simple() != null
						&& cfm.getCfva_only().equals(false)
						&& cfm.getCfva_simple().equals(true)) {
					for (int i = 0; i < fim.getList().size(); i++) {
						fim2.getList().add(fim.getList().get(i));
						fim.getList().remove(i);
						i--;
					}
				} else {
					for (int i = 0; i < fim.getList().size(); i++) {
						if (fim.getList().get(i).getKind().equals("普票")) {
							fim2.getList().add(fim.getList().get(i));
							fim.getList().remove(i);
							i--;
						}
					}
				}

			}
		}

	}

	public String checkinfo() {
		String str = "";
		if (cfm.getCfva_title() == null || cfm.getCfva_title().equals("")) {
			str = "发票抬头为空.";
		}

		if (cfm.getCfva_taxpayers() == null
				|| cfm.getCfva_taxpayers().equals("")) {
			str = "请选择纳税人类型";
		} else {
			if (cfm.getCfva_taxpayers().equals("是") && cfm.getCfva_only()) {
				if (cfm.getCfva_tel() == null || cfm.getCfva_tel().equals("")) {
					str = "电话为空";
				}
				if (cfm.getCfva_number1() == null
						|| cfm.getCfva_number1().equals("")) {
					str = "纳税人识别号为空";
				}
				if (cfm.getCfva_bank_acc() == null
						|| cfm.getCfva_bank_acc().equals("")) {
					str = "银行账号为空";
				}
				if (cfm.getCfva_bank() == null || cfm.getCfva_bank().equals("")) {
					str = "开户银行名称为空";
				}
			}

		}
		return str;
	}

	@Command
	@NotifyChange("invoiceList")
	public void submit() {

		Messagebox.show("确认提交数据?", "操作提示", new Messagebox.Button[] {
				Messagebox.Button.OK, Messagebox.Button.CANCEL },
				Messagebox.QUESTION,
				new EventListener<Messagebox.ClickEvent>() {
					@Override
					public void onEvent(ClickEvent event) throws Exception {
						// TODO Auto-generated method stub

						if (Messagebox.Button.OK.equals(event.getButton())) {
							if (fim.getList().size() > 0) {
								if (fim.getGfmc() == null
										|| fim.getGfmc().equals("")) {
									Messagebox.show("发票抬头不能为空", "操作提示",
											Messagebox.OK, Messagebox.ERROR);
									return;
								}
								if (fim.getGfdzdh() != null) {
									if (gblen(fim.getGfdzdh()) > 100) {
										Messagebox.show("注册地址信息超出100个字符.",
												"操作提示", Messagebox.OK,
												Messagebox.ERROR);
										return;
									}
								} else {
									Messagebox.show("注册地址信息为空", "操作提示",
											Messagebox.OK, Messagebox.ERROR);
									return;
								}
								for (int i = 0, num = fim.getList().size(); i < num; i++) {
									if (fim.getList().get(i).getSpmc() == null
											|| fim.getList().get(i).getSpmc()
													.equals("")) {
										fim.getList().remove(i);
										i--;
									}
								}
							}
							if (fim2.getList().size() > 0) {
								if (fim2.getGfmc() == null
										|| fim2.getGfmc().equals("")) {
									Messagebox.show("发票抬头不能为空", "操作提示",
											Messagebox.OK, Messagebox.ERROR);
									return;
								}
								if (fim2.getGfdzdh() != null) {
									if (gblen(fim2.getGfdzdh()) > 100) {
										Messagebox.show("注册地址信息超出100个字符.",
												"操作提示", Messagebox.OK,
												Messagebox.ERROR);
										return;
									}
								} else {
									Messagebox.show("注册地址信息为空", "操作提示",
											Messagebox.OK, Messagebox.ERROR);
									return;
								}
								for (int i = 0, num = fim2.getList().size(); i < num; i++) {
									if (fim2.getList().get(i).getSpmc() == null
											|| fim2.getList().get(i).getSpmc()
													.equals("")) {
										fim2.getList().remove(i);
										i--;
									}
								}
							}

							Integer i = 0;
							Integer j = 0;
							if (fim.getList().size() > 0 && fim.isPrintState()) {
								fim.setDjh(cfso_id + "-"
										+ (invoiceList.size() + 1));
								i = bll.add(fim);
							}
							if (fim2.getList().size() > 0
									&& fim2.isPrintState()) {
								fim2.setDjh(cfso_id
										+ "-"
										+ (invoiceList.size() + (i > 0 ? 2 : 1)));
								j = bll.add(fim2);
							}

							if (i > 0 || j > 0) {
								searchInvoice();
								Messagebox.show("添加成功.", "操作提示", Messagebox.OK,
										Messagebox.INFORMATION);
							} else {
								Messagebox.show("添加失败.", "操作提示", Messagebox.OK,
										Messagebox.ERROR);
							}
						}
					}
				});
	}

	public Integer gblen(String letter) {
		Integer len = 0;
		String chinese = "[\u4e00-\u9fa5]";
		for (Integer i = 0; i < letter.length(); i++) {

			if (letter.substring(i, i + 1).matches(chinese)) {
				len += 2;
			} else {
				len++;
			}

		}
		return len;
	}

	public FinanceInvoiceModel getFim() {
		return fim;
	}

	public void setFim(FinanceInvoiceModel fim) {
		this.fim = fim;
	}

	public CoFinanceVATModel getCfm() {
		return cfm;
	}

	public void setCfm(CoFinanceVATModel cfm) {
		this.cfm = cfm;
	}

	public FinanceInvoiceModel getFim2() {
		return fim2;
	}

	public void setFim2(FinanceInvoiceModel fim2) {
		this.fim2 = fim2;
	}

	public boolean isSimple() {
		return simple;
	}

	public void setSimple(boolean simple) {
		this.simple = simple;
	}

	public boolean isOnly() {
		return only;
	}

	public void setOnly(boolean only) {
		this.only = only;
	}

	public List<FinanceInvoiceModel> getInvoiceList() {
		return invoiceList;
	}

	public void setInvoiceList(List<FinanceInvoiceModel> invoiceList) {
		this.invoiceList = invoiceList;
	}

	public List<SystLogModel> getCfmLog() {
		return cfmLog;
	}

	public void setCfmLog(List<SystLogModel> cfmLog) {
		this.cfmLog = cfmLog;
	}

}
