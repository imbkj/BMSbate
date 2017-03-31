package Controller.CoFinanceManage.CoInvoice;

import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.zkoss.bind.annotation.BindingParam;
import org.zkoss.bind.annotation.Command;
import org.zkoss.bind.annotation.NotifyChange;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.event.EventListener;
import org.zkoss.zul.Combobox;
import org.zkoss.zul.Doublebox;
import org.zkoss.zul.Intbox;
import org.zkoss.zul.ListModelList;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Textbox;
import org.zkoss.zul.Window;
import org.zkoss.zul.Messagebox.ClickEvent;

import service.InvoiceService;

import bll.CoFinanceManage.CoInvoiceBll;
import bll.CoFinanceManage.CoInvoiceImpl;
import Model.CoBaseModel;
import Model.CoFinanceCollectModel;
import Model.CoInvoiceInfoModel;
import Model.CoInvoiceModel;
import Model.PubCodeConversionModel;
import Util.UserInfo;

public class InvoiceAddController {
	private List<CoFinanceCollectModel> list = new ListModelList<>();
	private List<CoInvoiceModel> ciList = new ListModelList<>();
	private List<CoInvoiceInfoModel> ciiList = new ListModelList<>();
	private List<PubCodeConversionModel> plist = new ListModelList<>();

	private CoFinanceCollectModel cfm = new CoFinanceCollectModel();
	private CoInvoiceModel cim = new CoInvoiceModel();
	private CoBaseModel cbm = new CoBaseModel();
	private CoInvoiceBll bll = new CoInvoiceBll();

	private Integer cid = Integer.parseInt(Executions.getCurrent().getArg()
			.get("cid").toString());
	private String company = Executions.getCurrent().getArg().get("company")
			.toString();

	private BigDecimal fee = new BigDecimal(0);
	private boolean modinfo = false;

	private SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
	private SimpleDateFormat sdf2 = new SimpleDateFormat("yyyyMM");

	public InvoiceAddController() {
		if (cid != null) {

			list = bll.searchList(cid);

			
			// fee = bll.getTotal(cid);
			initInfo();
			initFee();
		}
	}
	
	public void initInfo(){
		for (int i = 0; i < 6; i++) {
			CoInvoiceInfoModel cm = new CoInvoiceInfoModel();
			cm.setCoii_addname(UserInfo.getUsername());
			ciiList.add(cm);
		}
		plist = bll.getCodeList();

		CoInvoiceModel cm = new CoInvoiceModel();
		cm.setCid(cid);
		ciList = bll.getInvoiceList(cm);
		cim.setCoin_invoiceid(bll.createInvoiceId());
		plist.add(new PubCodeConversionModel());
		cbm = bll.getCobaseInfo(cid);
		cim.setRule(cbm.getCoba_invoicerule());
		cim.setIndustytype("服务业");
		cim.setCoin_title(cbm.getCoba_company());
		cim.setCoin_codeid("244031108451");
		cim.setCoin_iDate2(new Date());
		cim.setCoin_idate(sdf.format(new Date()));
		cim.setCoin_itype("发票四");
		cim.setCoin_iprint("未打印");
		cim.setCoin_addname(UserInfo.getUsername());
		cim.setCoin_total(new BigDecimal(0));
	}

	public void initFee() {

		boolean b = false;
		BigDecimal bd = new BigDecimal(0);
		BigDecimal bd2 = new BigDecimal(0);
		for (int i = 0; i < list.size(); i++) {
			if (list.get(i).isCheck()) {
				b = true;
				bd = bd.add(list.get(i).getCfco_TotalPaidIn());
				CoInvoiceModel cm = new CoInvoiceModel();
				cm.setCoin_cfco_id(list.get(i).getCfco_id());
				// 根据收款查询对应发票金额
				List<CoInvoiceModel> cimList = bll.getInvoiceList(cm);
				for (int j = 0; j < cimList.size(); j++) {
					if (cimList.get(j).getCoin_total() != null) {
						bd = bd.subtract(cimList.get(j).getCoin_total());
					}
				}

			}
		}

		if (b) {
			fee = bd;

		} else {
			// 获取所有发票合计金额
			for (int i = 0; i < ciList.size(); i++) {
				bd2 = bd2.add(ciList.get(i).getCoin_total());
			}
			fee = bll.getTotal(cid);
			fee = fee.subtract(bd2);
			fee = fee.compareTo(new BigDecimal(0)) > 0 ? fee
					: new BigDecimal(0);
		}

	}

	@Command
	@NotifyChange({ "cim", "fee","ciiList" })
	public void checkfee() {
		initFee();
		InvoiceService is = new CoInvoiceImpl();
		boolean b = false;
		for (CoFinanceCollectModel m : list) {
			if (m.isCheck() && m.getCfco_cfmb_number()!=null && !m.getCfco_cfmb_number().equals("0")) {
				b=true;
				break;
			}
		}
		
		if (b) {
			initInfo();
			ciiList = is.createInvoice2(list);
			
			int n = ciiList.size();
			for (int i = 0; i < 6-n; i++) {
				CoInvoiceInfoModel cm = new CoInvoiceInfoModel();
				cm.setCoii_addname(UserInfo.getUsername());
				ciiList.add(cm);
			}
			for (CoInvoiceInfoModel m : ciiList) {
				if (m.getCoii_fee()!=null) {
					cim.setCoin_total(cim.getCoin_total().add(m.getCoii_fee()));
				}
				
			}
		}else {
			ciiList=new ListModelList<>();
			for (int i = 0; i < 6; i++) {
				CoInvoiceInfoModel cm = new CoInvoiceInfoModel();
				cm.setCoii_addname(UserInfo.getUsername());
				ciiList.add(cm);
			}
			cim.setCoin_total(BigDecimal.ZERO);
		}
		
	}

	@Command
	public void create() {
		boolean b = false;
		for (int i = 0; i < list.size(); i++) {
			if (list.get(i).isCheck()) {
				b = true;
			}
		}
		if (b) {

		} else {

		}
	}

	@Command
	@NotifyChange("cim")
	public void modremark() {
		cim.setCoin_remark("发票代码:"
				+ (cim.getCoin_codeid() == null ? "" : cim.getCoin_codeid())
				+ " 发票编号:"
				+ (cim.getCoin_invoiceid() == null ? "" : cim
						.getCoin_invoiceid()));
	}

	@Command
	@NotifyChange({ "cim", "ciiList" })
	public void modItem(@BindingParam("a") CoInvoiceInfoModel cm,
			@BindingParam("b") Object obj) {
		if (obj.getClass().equals(Combobox.class)) {
			if (cm.getCoii_feetype() != null
					&& !cm.getCoii_feetype().equals("")) {

				cm.setCoii_owmonth(Integer.valueOf(sdf2.format(new Date())
						.toString()));
				cm.setCoii_content(cm.getCoii_feetype());
				cm.setCoii_fee(new BigDecimal(0));
			} else {
				cm.setCoii_owmonth(null);
				cm.setCoii_content(null);
				cm.setCoii_fee(null);
			}

		} else if (obj.getClass().equals(Intbox.class)) {

		} else if (obj.getClass().equals(Textbox.class)) {

		} else if (obj.getClass().equals(Doublebox.class)) {
			sumTotal();

		}
	}

	public void sumTotal() {
		cim.setCoin_total(new BigDecimal(0));
		for (int i = 0; i < ciiList.size(); i++) {
			if (ciiList.get(i).getCoii_feetype() != null
					&& !ciiList.get(i).getCoii_feetype().equals("")) {
				cim.setCoin_total(cim.getCoin_total().add(
						ciiList.get(i).getCoii_fee()));
			}
		}
	}

	public void reflash() {
		CoInvoiceModel cm = new CoInvoiceModel();
		cm.setCid(cid);
		ciList = bll.getInvoiceList(cm);
	
		list = bll.searchList(cid);
		initInfo();
		initFee();
	}

	@Command
	@NotifyChange({ "fee", "cim", "ciiList", "modinfo", "list" })
	public void readInvoice(@BindingParam("a") CoInvoiceModel ciModel) {
		cim = bll.getInvoice(ciModel.getCoin_id());
		ciiList = cim.getList();
		cim.setIndustytype("服务业");
		cim.setRule(cbm.getCoba_invoicerule());
		try {
			cim.setCoin_iDate2(sdf.parse(cim.getCoin_idate()));
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		Integer j = 6 - ciiList.size();
		for (int i = 0; i < j; i++) {
			CoInvoiceInfoModel cm = new CoInvoiceInfoModel();
			cm.setCoii_addname(UserInfo.getUsername());
			ciiList.add(cm);
		}

		for (int i = 0; i < list.size(); i++) {
			list.get(i).setCheck(false);
		}
		for (int i = 0; i < list.size(); i++) {
			for (int k = 0; k < cim.getList2().size(); k++) {
				if (list.get(i).getCfco_id() == cim.getList2().get(k)
						.getCire_cfco_id()) {
					list.get(i).setCheck(true);
				}
			}
		}
		initFee();
		modinfo = true;
	}

	@Command
	@NotifyChange({ "fee", "list", "ciList" })
	public void del(@BindingParam("a") final CoInvoiceModel ciModel) {
		Messagebox.show("确认提交数据?", "操作提示", new Messagebox.Button[] {
				Messagebox.Button.OK, Messagebox.Button.CANCEL },
				Messagebox.QUESTION,
				new EventListener<Messagebox.ClickEvent>() {
					@Override
					public void onEvent(ClickEvent event) throws Exception {
						// TODO Auto-generated method stub

						if (Messagebox.Button.OK.equals(event.getButton())) {
							Integer i = bll.del(ciModel.getCoin_id());
							if (i > 0) {
								Messagebox.show("操作成功!", "提示", Messagebox.OK,
										Messagebox.INFORMATION);
								reflash();
							} else {
								Messagebox.show("操作失败!", "提示", Messagebox.OK,
										Messagebox.ERROR);
								return;
							}
						}
					}
				});

	}

	@Command
	@NotifyChange({ "cim", "fee", "list", "ciList", "ciiList" })
	public void save() {
		boolean b = false;
		for (int i = 0; i < list.size(); i++) {
			if (list.get(i).isCheck()) {
				b = true;
			}
		}
		if (!b) {
			Messagebox.show("请输入选择收款!", "提示", Messagebox.OK, Messagebox.ERROR);
			return;
		}

		for (int i = 0; i < ciiList.size(); i++) {
			if (ciiList.get(i).getCoii_feetype() != null
					&& !ciiList.get(i).getCoii_feetype().equals("")) {
				if (ciiList.get(i).getCoii_owmonth() == null
						|| ciiList.get(i).getCoii_owmonth().equals("")) {
					Messagebox.show("请输入[" + ciiList.get(i).getCoii_feetype()
							+ "]所属月份", "提示", Messagebox.OK, Messagebox.ERROR);
					return;
				}

			}
		}
		sumTotal();
		if (fee.subtract(cim.getCoin_total()).compareTo(new BigDecimal(0)) < 0) {
			Messagebox.show("金额超出收款,请重新输入!", "提示", Messagebox.OK,
					Messagebox.ERROR);
			return;
		}

		Messagebox.show("确认提交数据?", "操作提示", new Messagebox.Button[] {
				Messagebox.Button.OK, Messagebox.Button.CANCEL },
				Messagebox.QUESTION,
				new EventListener<Messagebox.ClickEvent>() {
					@Override
					public void onEvent(ClickEvent event) throws Exception {
						// TODO Auto-generated method stub

						if (Messagebox.Button.OK.equals(event.getButton())) {
							Integer i = bll.add(cim, ciiList, list);

							if (i > 0) {
								bll.modCobase(cid, cim.getRule());
								Messagebox.show("提交成功", "提示", Messagebox.OK,
										Messagebox.INFORMATION);
								reflash();
							} else {
								Messagebox.show("提交失败,错误代码" + i, "提示",
										Messagebox.OK, Messagebox.ERROR);
							}
						}
					}
				});
	}

	@Command
	@NotifyChange({ "cim", "ciList", "ciiList" })
	public void modInvoice() {
		boolean b = false;
		for (int i = 0; i < list.size(); i++) {
			if (list.get(i).isCheck()) {
				b = true;
			}
		}
		if (!b) {
			Messagebox.show("请输入选择收款!", "提示", Messagebox.OK, Messagebox.ERROR);
			return;
		}

		for (int i = 0; i < ciiList.size(); i++) {
			if (ciiList.get(i).getCoii_feetype() != null
					&& !ciiList.get(i).getCoii_feetype().equals("")) {
				if (ciiList.get(i).getCoii_owmonth() == null
						|| ciiList.get(i).getCoii_owmonth().equals("")) {
					Messagebox.show("请输入[" + ciiList.get(i).getCoii_feetype()
							+ "]所属月份", "提示", Messagebox.OK, Messagebox.ERROR);
					return;
				}

			}
		}
		sumTotal();
		if (fee.subtract(cim.getCoin_total()).compareTo(new BigDecimal(0)) < 0) {
			Messagebox.show("金额超出收款,请重新输入!", "提示", Messagebox.OK,
					Messagebox.ERROR);
			return;
		}
		Messagebox.show("确认提交数据?", "操作提示", new Messagebox.Button[] {
				Messagebox.Button.OK, Messagebox.Button.CANCEL },
				Messagebox.QUESTION,
				new EventListener<Messagebox.ClickEvent>() {
					@Override
					public void onEvent(ClickEvent event) throws Exception {
						// TODO Auto-generated method stub

						if (Messagebox.Button.OK.equals(event.getButton())) {

							if (Messagebox.Button.OK.equals(event.getButton())) {
								Integer i = bll.mod(cim, ciiList, list);

								if (i > 0) {
									bll.modCobase(cid, cim.getRule());
									Messagebox.show("提交成功", "提示",
											Messagebox.OK,
											Messagebox.INFORMATION);
									reflash();
								} else {
									Messagebox.show("提交失败,错误代码" + i, "提示",
											Messagebox.OK, Messagebox.ERROR);
								}
							}
						}
					}
				});
	}

	@Command
	public void print(@BindingParam("a") CoInvoiceModel ciModel) {

		Map map = new HashMap();
		map.put("id", ciModel.getCoin_id());

		Window window = (Window) Executions.createComponents(
				"/CoFinanceManage/CoInvoice/Invoice.zul", null, map);
		window.doModal();
	}

	public List<CoFinanceCollectModel> getList() {
		return list;
	}

	public void setList(List<CoFinanceCollectModel> list) {
		this.list = list;
	}

	public CoFinanceCollectModel getCfm() {
		return cfm;
	}

	public void setCfm(CoFinanceCollectModel cfm) {
		this.cfm = cfm;
	}

	public List<PubCodeConversionModel> getPlist() {
		return plist;
	}

	public void setPlist(List<PubCodeConversionModel> plist) {
		this.plist = plist;
	}

	public CoInvoiceModel getCim() {
		return cim;
	}

	public void setCim(CoInvoiceModel cim) {
		this.cim = cim;
	}

	public int getCid() {
		return cid;
	}

	public void setCid(int cid) {
		this.cid = cid;
	}

	public String getCompany() {
		return company;
	}

	public void setCompany(String company) {
		this.company = company;
	}

	public BigDecimal getFee() {
		return fee;
	}

	public void setFee(BigDecimal fee) {
		this.fee = fee;
	}

	public List<CoInvoiceModel> getCiList() {
		return ciList;
	}

	public void setCiList(List<CoInvoiceModel> ciList) {
		this.ciList = ciList;
	}

	public List<CoInvoiceInfoModel> getCiiList() {
		return ciiList;
	}

	public void setCiiList(List<CoInvoiceInfoModel> ciiList) {
		this.ciiList = ciiList;
	}

	public boolean isModinfo() {
		return modinfo;
	}

	public void setModinfo(boolean modinfo) {
		this.modinfo = modinfo;
	}

}
