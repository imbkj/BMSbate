package Controller.EmSalary;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.select.SelectorComposer;
import org.zkoss.zk.ui.select.annotation.Listen;
import org.zkoss.zk.ui.select.annotation.Wire;
import org.zkoss.zul.Combobox;
import org.zkoss.zul.Decimalbox;
import org.zkoss.zul.Grid;
import org.zkoss.zul.Label;
import org.zkoss.zul.ListModelList;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Radiogroup;
import org.zkoss.zul.Row;
import org.zkoss.zul.Textbox;
import org.zkoss.zul.Window;

import bll.EmSalary.EmSalary_SalaryOperateBll;
import bll.EmSalary.EmSalary_SalarySelBll;

import Model.EmSalaryBaseAddItemModel;
import Model.EmSalaryBaseAdd_viewModel;
import Model.EmSalaryDataModel;
import Model.PubCodeConversionModel;
import Util.DateStringChange;
import Util.UserInfo;

public class EmSalary_Add_BaseController extends SelectorComposer<Component> {
	private static final long serialVersionUID = 1L;
	private final int gid = Integer.parseInt(Executions.getCurrent().getArg()
			.get("gid").toString());
	private final int cid = Integer.parseInt(Executions.getCurrent().getArg()
			.get("cid").toString());
	private EmSalary_SalarySelBll bll = new EmSalary_SalarySelBll();
	private ListModelList<PubCodeConversionModel> usageList;
	private EmSalaryBaseAdd_viewModel emdataList;
	private ListModelList<String> ownmonthList;
	private List<EmSalaryBaseAddItemModel> csIIInfoList;
	private String account;
	private String bank;
	private String nowMonth = DateStringChange
			.DatetoSting(new Date(), "yyyyMM");
	@Wire
	private Window winEmSalaryAdd;
	@Wire
	private Combobox ownmonth;
	@Wire
	private Combobox esda_usage_type;
	@Wire
	private Textbox esda_ba_name;
	@Wire
	private Radiogroup esda_confirm_state;
	@Wire
	private Textbox esda_remark;
	@Wire
	private Textbox esda_fd_remark;
	@Wire
	private Grid gdItem;
	@Wire
	private Label lblAccount;
	@Wire
	private Label lblBank;

	private boolean existsCoOffer = true;
	private String existsMessage;

	public EmSalary_Add_BaseController() {
		if (!bll.checkCoOfferList(gid)) {
			existsCoOffer = false;
			existsMessage = "该员工未分配工资或个税相应的报价单，无法添加工资信息。";
		}

		usageList = new ListModelList<PubCodeConversionModel>(
				bll.getCodeConversion());
		emdataList = bll.getEmSalaryDateAdd(gid,cid);
		ownmonthList = new ListModelList<String>(bll.getOwnmonth(
				emdataList.getCid(), gid, emdataList.getEsin_stopmonth()));
		account = emdataList.getEmba_gz_account();
		bank = emdataList.getEmba_gz_bank();
		ownmonthList.addToSelection(nowMonth);
		// 判断所属公司是否存在上月当月下月工资项目组合,没有则新增
		bll.existItem(bll.getCidByGid(gid), Integer.parseInt(nowMonth),
				UserInfo.getUsername());
	}

	public void doAfterCompose(Component comp) throws Exception {
		super.doAfterCompose(comp);
	}

	@Listen("onChange = #esda_usage_type")
	public void usageOnChange() {
		String code = esda_usage_type.getSelectedItem().getValue();
		bank = "";
		account = "";
		// 根据用途获取银行信息
		if ("1".equals(code)) {
			bank = emdataList.getEmba_writeoff_bank();
			account = emdataList.getEmba_writeoff_account();
		} else if ("2".equals(code)) {
			bank = emdataList.getEmba_house_bank();
			account = emdataList.getEmba_house_account();
		}
		if ("".equals(bank) || "".equals(account) || bank == null
				|| account == null) {
			account = emdataList.getEmba_gz_account();
			bank = emdataList.getEmba_gz_bank();
		}
		lblAccount.setValue(account);
		lblBank.setValue(bank);
		// 设置页面的项目
		setPageItem();
	}

	@Listen("onChange = #ownmonth")
	public void ownmonthOnChange() {
		// 设置页面的项目
		setPageItem();
	}

	@Listen("onClick = #btSubmit")
	public void addSalary() {
		try {
			if (checkPage()) {
				if (esda_usage_type.getSelectedItem() != null
						&& !"".equals(esda_usage_type.getSelectedItem())) {

					EmSalary_SalaryOperateBll bll = new EmSalary_SalaryOperateBll();

					EmSalaryDataModel m = getPageBase();
					List<EmSalaryBaseAddItemModel> list = getPageItem();
					String[] message = bll.AddSalary(m, list);
					if (message[0].equals("1")) {
						// 弹出提示
						Messagebox.show(message[1], "操作提示", Messagebox.OK,
								Messagebox.NONE);
						winEmSalaryAdd.detach();

					} else {
						// 弹出提示
						Messagebox.show(message[1], "操作提示", Messagebox.OK,
								Messagebox.ERROR);
					}
				} else {
					// 弹出提示
					esda_usage_type.focus();
					Messagebox.show("请选择用途。", "操作提示", Messagebox.OK,
							Messagebox.INFORMATION);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			Messagebox.show("新增工资出错。", "操作提示", Messagebox.OK, Messagebox.ERROR);
		}
	}

	// 获取页面个人信息
	private EmSalaryDataModel getPageBase() {
		EmSalaryDataModel m = new EmSalaryDataModel();
		m.setGid(gid);
		m.setCid(emdataList.getCid());
		m.setEsda_if_bms(1);
		m.setEsda_oof_state(0);
		m.setEsda_data_type(0);// test
		m.setEsda_addname(UserInfo.getUsername());
		m.setCfin_id(emdataList.getCfin_id());
		m.setCsii_itemid(emdataList.getCsii_itemid());
		m.setEsda_nationality(emdataList.getEmba_Nationality());
		m.setEsda_bank(bank);
		m.setEsda_bank_account(account);
		m.setEsda_ba_name(esda_ba_name.getValue());
		m.setOwnmonth(Integer.parseInt(ownmonth.getSelectedItem().getValue()
				.toString()));
		m.setEsda_usage_type(Integer.parseInt(esda_usage_type.getSelectedItem()
				.getValue().toString()));
		// m.setEsda_confirm_state(Integer.parseInt(esda_confirm_state
		// .getSelectedItem().getValue().toString()));
		m.setEsda_payment_state(3);
		m.setEsda_remark(esda_remark.getValue());
		m.setEsda_fd_remark(esda_fd_remark.getValue());
		return m;

	}

	// 获取页面项目信息
	private List<EmSalaryBaseAddItemModel> getPageItem() {
		List<EmSalaryBaseAddItemModel> list = new ArrayList<EmSalaryBaseAddItemModel>();
		List<Component> rows = gdItem.getRows().getChildren();
		EmSalaryBaseAddItemModel m = null;
		Row comp = null;
		for (Object obj : rows) {
			comp = (Row) obj;
			m = comp.getValue();
			try {
				m.setAmount(((Decimalbox) comp.getChildren().get(1)
						.getChildren().get(0)).getValue());
			} catch (Exception e) {
				m.setAmount(new BigDecimal(0));
			}
			if (m.getAmount() != null) {
				list.add(m);
			}
		}
		return list;
	}

	// 设置页面的项目
	private void setPageItem() {
		try {
			int salaryOwnmonth = Integer.parseInt(ownmonth.getSelectedItem()
					.getLabel());
			if (esda_usage_type.getSelectedItem().getValue() != null) {
				String code = esda_usage_type.getSelectedItem().getValue();
				int csii_ifzero = 1;
				if ("0".equals(code)) {
					if (emdataList.getEsda_cou() == 0) {
						csii_ifzero = -1;
					}
				}
				csIIInfoList = bll.getCSIIInfo(emdataList.getCid(),
						salaryOwnmonth, csii_ifzero);
				if (csIIInfoList.size() == 0) {
					gdItem.setEmptyMessage("新增工资仅可增加非清零的项目，该员工无非清零的字段。");
				} else {
					gdItem.setModel(new ListModelList<EmSalaryBaseAddItemModel>(
							csIIInfoList));
				}
			}
		} catch (Exception e) {

		}
	}

	// 检查页面数据
	private boolean checkPage() {
		boolean bool = false;
		try {
			if (emdataList.getEmba_Nationality() == null
					|| "".equals(emdataList.getEmba_Nationality())) {
				Messagebox.show("该员工无“国籍”信息，无法新增工资，请在员工信息中补全。", "操作提示",
						Messagebox.OK, Messagebox.NONE);
			} else if (emdataList.getEsin_hpro() == null
					|| "".equals(emdataList.getEsin_hpro())) {
				Messagebox.show("该员工无“雇佣性质”信息，无法新增工资，请在员工信息中补全。", "操作提示",
						Messagebox.OK, Messagebox.NONE);
			} else if (emdataList.getEsin_taxplace() == null
					|| "".equals(emdataList.getEsin_taxplace())) {
				Messagebox.show("该员工无“个税申报地”信息，无法新增工资，请在员工信息中补全。", "操作提示",
						Messagebox.OK, Messagebox.NONE);
			} else {
				bool = true;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return bool;

	}

	public EmSalaryBaseAdd_viewModel getEmdataList() {
		return emdataList;
	}

	public void setEmdataList(EmSalaryBaseAdd_viewModel emdataList) {
		this.emdataList = emdataList;
	}

	public ListModelList<PubCodeConversionModel> getUsageList() {
		return usageList;
	}

	public ListModelList<String> getOwnmonthList() {
		return ownmonthList;
	}

	public List<EmSalaryBaseAddItemModel> getCsIIInfoList() {
		return csIIInfoList;
	}

	public void setCsIIInfoList(List<EmSalaryBaseAddItemModel> csIIInfoList) {
		this.csIIInfoList = csIIInfoList;
	}

	public String getAccount() {
		return account;
	}

	public void setAccount(String account) {
		this.account = account;
	}

	public String getBank() {
		return bank;
	}

	public void setBank(String bank) {
		this.bank = bank;
	}

	public String getNowMonth() {
		return nowMonth;
	}

	public boolean isExistsCoOffer() {
		return existsCoOffer;
	}

	public String getExistsMessage() {
		return existsMessage;
	}

}
