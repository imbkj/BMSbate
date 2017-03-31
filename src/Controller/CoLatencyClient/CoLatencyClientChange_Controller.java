package Controller.CoLatencyClient;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.zkoss.bind.BindUtils;
import org.zkoss.bind.annotation.BindingParam;
import org.zkoss.bind.annotation.Command;
import org.zkoss.bind.annotation.NotifyChange;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.Path;
import org.zkoss.zul.Combobox;
import org.zkoss.zul.Grid;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Window;

import Model.CoAgencyLinkmanModel;
import Model.CoBaseModel;
import Model.CoCompactModel;
import Model.CoFinanceVATModel;
import Model.CoLatencyClientModel;
import Model.CoServiceRequestModel;
import Model.PubTradeModel;
import Model.SalaryPaperCoModel;
import Util.DateStringChange;
import Util.UserInfo;
import bll.CoBase.CoBase_SelectBll;
import bll.CoCompact.CoCompact_OperateBll;
import bll.CoLatencyClient.CoFinanceVatBll;
import bll.CoLatencyClient.CoLatencyClient_AddBll;
import bll.CoLatencyClient.CoServiceRequestOperateBll;
import bll.CoLatencyClient.CoServiceRequestSelectBll;
import bll.EmSalary.ItemFormula_OperateBll;
import bll.EmSalary.ItemFormula_SelectBll;
import bll.SalaryPaper.SalaryPaperBll;

public class CoLatencyClientChange_Controller {
	private CoLatencyClientModel frommodel = (CoLatencyClientModel) Executions
			.getCurrent().getArg().get("cola");
	private CoServiceRequestSelectBll sbll = new CoServiceRequestSelectBll();
	private List<String> loginlist = sbll.getLoginlist();
	private List<String> managerlist = sbll.getManagerlist();
	private List<String> loginlist2t = new ArrayList<String>();
	private List<String> loginlist3t = new ArrayList<String>();
	private List<String> datelistt = new ArrayList<String>();// 天的列表
	private CoBaseModel cmodel = new CoBaseModel();
	// suhongyuan
	private CoFinanceVATModel coFinanceVATModel = new CoFinanceVATModel();
	private CoFinanceVatBll vatBll = new CoFinanceVatBll();

	private CoLatencyClient_AddBll bll = new CoLatencyClient_AddBll();
	private List<PubTradeModel> tradelist = bll.getTradeIndo();
	private CoServiceRequestModel servicemodel = new CoServiceRequestModel();
	private CoBase_SelectBll cbll = new CoBase_SelectBll();
	private String sign = "";
	private Window win = (Window) Path.getComponent("/win");
	private CoServiceRequestSelectBll serbll = new CoServiceRequestSelectBll();
	private List<CoServiceRequestModel> list = serbll
			.getRequestInfoList(" and csqe_cola_id=" + frommodel.getCola_id());
	private List<CoAgencyLinkmanModel> linkmanList;
	private int linkmanNum;
	private boolean cs = false;
	private boolean only = false;
	private boolean simple = false;

	private SalaryPaperCoModel cosalarysetm = new SalaryPaperCoModel();

	public CoLatencyClientChange_Controller() {
		datelistt = datelistAdd();
		linkmanList = bll.getLinkmanForAg(frommodel.getCola_id());
		linkmanNum = linkmanList.size();
		if (frommodel.getCola_sign() != null
				&& !frommodel.getCola_sign().equals("")) {
			if (frommodel.getCola_sign().equals(1)) {
				sign = "是";
			} else {
				sign = "否";
			}
		}
		if (list.size() > 0) {
			servicemodel = list.get(0);
		}
		if (servicemodel.getCid() != null && !servicemodel.getCid().equals("")) {
			List<CoBaseModel> modellist = cbll.getCobaseinfo(" and a.cid="
					+ servicemodel.getCid());
			if (modellist.size() > 0) {
				cmodel = modellist.get(0);
			}
		}
		cs = bll.isHasCS(frommodel.getCola_id());

		coFinanceVATModel.setCfva_title(frommodel.getCola_company());
	}

	@Command
	@NotifyChange({ "only", "simple", "coFinanceVATModel" })
	public void modInvoice() {
		if (coFinanceVATModel.getCfva_taxpayers() != null) {
			if (coFinanceVATModel.getCfva_taxpayers().equals("否")) {
				only = true;
				simple = false;
				coFinanceVATModel.setCfva_only(false);
				coFinanceVATModel.setCfva_simple(true);
			} else if (coFinanceVATModel.getCfva_taxpayers().equals("是")) {
				only = false;
				simple = false;
				coFinanceVATModel.setCfva_only(true);
				coFinanceVATModel.setCfva_simple(true);
			} else {
				only = false;
				simple = false;
				coFinanceVATModel.setCfva_only(false);
				coFinanceVATModel.setCfva_simple(false);
			}
		}
	}

	// 转成功客户
	@Command
	public void changeclient(@BindingParam("win") Window win,
			@BindingParam("ifsign") Combobox ifsign) {
		Integer coconum = 0;
		String str = "";
		if (frommodel.getCola_state() == 0) {
			Integer cc = bll.ifExistCoCompact(frommodel.getCola_id());
			if (cc <= 0) {
				str = "该客户还没有合同信息，不能转公司";
			}
			if (str == "") {
				coconum = bll.getCoCompact(frommodel.getCola_id());
				if (coconum < 1) {
					str = "还有合同没有签回，不能转公司";
				}
			}
		}

		if (str == "") {
			str = isEmploy();
			if (str == "") {
				if (checkLinkman()) {
					cmodel.setCoba_addname(UserInfo.getUsername());// 添加人
					cmodel.setCoba_company(frommodel.getCola_company());
					if (sign != null) {
						if (sign.equals("是")) {
							cmodel.setCoba_sign(1);
						} else {
							cmodel.setCoba_sign(0);
						}
					}
					cmodel.setCoba_clientsource(frommodel
							.getCola_clientsource());
					cmodel.setCoba_clientsize(frommodel.getCola_clientsize());
					cmodel.setCoba_industytype(frommodel.getCola_trade());
					cmodel.setCoba_setuptype(frommodel.getCola_companytype());
					cmodel.setCoba_area(frommodel.getCola_clientarea());
					cmodel.setCoba_manager(cmodel.getCoba_manager());
					cmodel.setCoba_kind(frommodel.getCola_kind());
					cmodel.setCoba_ifhasbribery(frommodel
							.getCoba_ifhasbribery());

					CoLatencyClient_AddBll bll = new CoLatencyClient_AddBll();

					// 更改联系人信息
					try {
						for (CoAgencyLinkmanModel m : linkmanList) {
							bll.CoLatencyClientLinkmanAdd_P_lwj(m,
									frommodel.getCola_id());
						}
					} catch (Exception e) {
						e.printStackTrace();
					}

					String[] strs = bll.CoLatencyClientchange(cmodel,
							frommodel.getCola_id());
					// System.out.println("k="+k);
					if (strs[0] == "1") {
						List<CoLatencyClientModel> list = bll
								.getCoLatencyClientAllInfo(" and cola_id="
										+ frommodel.getCola_id());
						if (list.size() > 0) {
							if (list.get(0).getCid() != null) {
								CoCompact_OperateBll cocoBll = new CoCompact_OperateBll();
								List<CoCompactModel> clist = bll
										.getcocompactList(frommodel
												.getCola_id());
								for (int i = 0; i < clist.size(); i++) {
									CoCompactModel cm = new CoCompactModel();
									cm = clist.get(i);
									cocoBll.startMission(cm);
								}
							}
						}

						// 判断是否有代发工资、代报个税报价单项目，有就添加薪酬项目算法设置任务单
						ItemFormula_SelectBll ifBll = new ItemFormula_SelectBll();

						int chkGZ = ifBll.chkIfGZ(" AND b.coco_cola_id="
								+ frommodel.getCola_id());
						if (chkGZ != 0) {
							// 获取用户名
							String username = UserInfo.getUsername();

							// 获取当前所属月份
							// DateStringChange dsc = new DateStringChange();
							// MonthListUtil mlUtil = new MonthListUtil();
							Date nowDate = new Date(); // 获取当前时间
							int ownmonth = Integer.parseInt(DateStringChange
									.DatetoSting(nowDate, "yyyyMM"));

							// 获取cid
							int cid;
							// cid=bll.getCoLatencyClientAllInfo(" AND a.cola_id="+frommodel.getCola_id()).get(0).getCid();
							cid = Integer.parseInt(strs[3]);
							// 在CoSalaryItemIDInfo表新增一条数据
							ItemFormula_OperateBll ioBll = new ItemFormula_OperateBll();
							String[] msg = ioBll.clcAddSalaryItemId(cid,
									ownmonth, username);
							if (!msg[0].equals("1")) {
								Messagebox.show(msg[1], "操作提示", Messagebox.OK,
										Messagebox.ERROR);
							}
						}

						// 添加服务要求
						Integer kl = getServiceInfo();
						Integer kll = ifExistsSalary();
						Integer lk = kll + kl;

						if (lk > 0) {
							Integer cid;
							cid = Integer.parseInt(strs[3]);
							CoServiceRequestSelectBll cobll = new CoServiceRequestSelectBll();
							Integer cocoid = cobll.getCocoId(cid);
							servicemodel.setCid(cid);
							servicemodel.setCoco_id(cocoid);
							servicemodel
									.setCsqe_cola_id(frommodel.getCola_id());
							servicemodel.setCoba_shortname(cmodel
									.getCoba_shortname());

							CoServiceRequestOperateBll servicebll = new CoServiceRequestOperateBll();
							Integer ifexits = ifExistsSalary();
							// frommodel.getCola_state()表示不是第一次转成功客户
							// if(frommodel.getCoba_LTS()==1)
							// {

							// 插入工资单表
							cosalarysetm.setCid(cid);
							SalaryPaperBll spb = new SalaryPaperBll();
							spb.coinfoadd(cosalarysetm);

							servicemodel.setCsqe_request(kl);
							servicemodel.setCsqe_salary(kll);
							Integer csqeId = servicebll.CoServiceRequest_Add(
									servicemodel, ifexits);
						}
						// 营改增信息添加 suhongyaun
						coFinanceVATModel.setCid(Integer.parseInt(strs[3]));

						coFinanceVATModel.setCfva_reg_add(frommodel
								.getCola_address());

						coFinanceVATModel.setCfva_addname(UserInfo
								.getUsername());

						Integer vat = vatBll.addCoFinanceVat(coFinanceVATModel);

						Messagebox.show("转换成功", "提示", Messagebox.OK,
								Messagebox.INFORMATION);
						win.detach();
					} else {
						Messagebox.show("转换失败", "提示", Messagebox.OK,
								Messagebox.ERROR);
					}
				}
			} else {
				Messagebox.show(str, "提示", Messagebox.OK, Messagebox.ERROR);
			}
		} else {
			Messagebox.show(str, "提示", Messagebox.OK, Messagebox.ERROR);
		}
	}

	// 工资单显示判断
	@Command
	public void setvisble(@BindingParam("grid1") Grid gdzz,
			@BindingParam("grid2") Grid gdemail,
			@BindingParam("comb") Combobox cm) {
		if (cm.getValue().equals("E-mail工资单")) {
			gdzz.setVisible(false);
			gdemail.setVisible(true);
		}

		else if (cm.getValue().equals("密封工资单")) {
			gdzz.setVisible(true);
			gdemail.setVisible(false);
		} else {
			gdzz.setVisible(false);
			gdemail.setVisible(false);
		}

	}

	// 判断必填项是否为空
	private String isEmploy() {
		String str = "";
		if (frommodel.getCola_company() == null
				|| frommodel.getCola_company().equals("")) {
			str = "公司名称不能为空";
			return str;
		}
		if (cmodel.getCoba_shortname() == null
				|| cmodel.getCoba_shortname().equals("")) {
			str = "公司简称不能为空";
			return str;
		}
		if (frommodel.getCola_companytype() == null
				|| frommodel.getCola_companytype().equals("")) {
			str = "客户类型不能为空";
			return str;
		}
		if (frommodel.getCola_clientarea() == null
				|| frommodel.getCola_clientarea().equals("")) {
			str = "客户所在区域不能为空";
			return str;
		}
		if (frommodel.getCola_kind() == null
				|| frommodel.getCola_kind().equals("")) {
			str = "客户企业性质不能为空";
			return str;
		}
		if (cmodel.getCoba_manager() == null
				|| cmodel.getCoba_manager().equals("")) {
			str = "请选择部门经理";
			return str;
		}
		if (coFinanceVATModel.getCfva_title() == null
				|| coFinanceVATModel.getCfva_title().equals("")) {
			str = "发票抬头为空.";
		}
		if (cmodel.getCoba_address() == null
				|| cmodel.getCoba_address().equals("")) {
			str = "注册地址为空.";
		}

		if (coFinanceVATModel.getCfva_contact() == null
				|| coFinanceVATModel.getCfva_contact().equals("")) {
			str = "发票联系人为空.";
		}
		if (coFinanceVATModel.getCfva_contact_tel() == null
				|| coFinanceVATModel.getCfva_contact_tel().equals("")) {
			str = "发票联系人电话为空.";
		}
		if (coFinanceVATModel.getCfva_vat_add() == null
				|| coFinanceVATModel.getCfva_vat_add().equals("")) {
			str = "发票接收地址为空.";
		}
		if (coFinanceVATModel.getCfva_taxpayers() == null
				|| coFinanceVATModel.getCfva_taxpayers().equals("")) {
			str = "请选择纳税人类型";
		} else {
			if (coFinanceVATModel.getCfva_taxpayers().equals("是")) {
				if (coFinanceVATModel.getCfva_tel() == null
						|| coFinanceVATModel.getCfva_tel().equals("")) {
					str = "电话为空";
				}
				if (coFinanceVATModel.getCfva_number1() == null
						|| coFinanceVATModel.getCfva_number1().equals("")) {
					str = "纳税人识别号为空";
				}
				if (coFinanceVATModel.getCfva_bank_acc() == null
						|| coFinanceVATModel.getCfva_bank_acc().equals("")) {
					str = "银行账号为空";
				}
				if (coFinanceVATModel.getCfva_bank() == null
						|| coFinanceVATModel.getCfva_bank().equals("")) {
					str = "开户银行名称为空";
				}
			}

		}
		/************** 如果该客户有财税服务则下面内容必填 *******************/
		if (bll.isHasCS(frommodel.getCola_id())) {
			if (servicemodel.getEmfics_backdate() == null
					|| servicemodel.getEmfics_backdate().equals("")) {
				str = "工资个税付款日不能为空";
				return str;
			}
			if (servicemodel.getPaydate() == null
					|| servicemodel.getPaydate().equals("")) {
				str = "工资发放时间约定不能为空";
				return str;
			}
			if (servicemodel.getCsqe_salaryconfirmenddate() == null
					|| servicemodel.getCsqe_salaryconfirmenddate().equals("")) {
				str = "工资确认截止日不能为空";
				return str;
			}
			if (servicemodel.getCsqe_sbhousetype() == null
					|| servicemodel.getCsqe_sbhousetype().equals("")) {
				str = "社保公积金扣缴约定不能为空";
				return str;
			}
			if (servicemodel.getActype() == null
					|| servicemodel.getActype().equals("")) {
				str = "考勤计算约定不能为空";
				return str;
			}
			if (servicemodel.getTaxctype() == null
					|| servicemodel.getTaxctype().equals("")) {
				str = "个税计算约定不能为空";
				return str;
			}
			if (servicemodel.getTaxdetype() == null
					|| servicemodel.getTaxdetype().equals("")) {
				str = "个税申报约定不能为空";
				return str;
			}
			if (servicemodel.getTaxpay() == null
					|| servicemodel.getTaxpay().equals("")) {
				str = "个税转账约定不能为空";
				return str;
			}
			if (servicemodel.getTaxwt() == null
					|| servicemodel.getTaxwt().equals("")) {
				str = "委托外地申报约定不能为空";
				return str;
			}
			if (servicemodel.getTaxde_date() == null
					|| servicemodel.getTaxde_date().equals("")) {
				str = "个税申报时间不能为空";
				return str;
			}
			if (cosalarysetm.getCoss_payrollpapertype() == null
					|| cosalarysetm.getCoss_payrollpapertype().equals("")) {
				str = "工资单形式不能为空";
				return str;
			}
			if (bll.ifExistsgeshui(" and coco_cola_id="
					+ frommodel.getCola_id())) {
				if (servicemodel.getTaxdetype() == null
						|| servicemodel.getTaxdetype().equals("")) {
					str = "报价单有个税服务，个税申报约定不能为空";
					return str;
				}
			}

		}

		/*
		 * if (coFinanceVATModel.getCfva_taxpayers() == null ||
		 * coFinanceVATModel.getCfva_taxpayers().equals("") ||
		 * coFinanceVATModel.getCfva_taxpayers() == "否" ||
		 * coFinanceVATModel.getCfva_taxpayers().equals("否")) { str = ""; return
		 * str; } else { if (coFinanceVATModel.getCfva_taxpayers() == "是" ||
		 * coFinanceVATModel.getCfva_taxpayers().equals("是")) { if
		 * (coFinanceVATModel.getCfva_tel() == null ||
		 * coFinanceVATModel.getCfva_tel().equals("")) { str = "电话不能为空"; return
		 * str; } if (coFinanceVATModel.getCfva_number1() == null ||
		 * coFinanceVATModel.getCfva_number1().equals("")) { str = "纳税人识别号不能为空";
		 * return str; }
		 * 
		 * if (coFinanceVATModel.getCfva_bank_acc() == null ||
		 * coFinanceVATModel.getCfva_bank_acc().equals("")) { str = "银行账号不能为空";
		 * return str; } if (coFinanceVATModel.getCfva_bank() == null ||
		 * coFinanceVATModel.getCfva_bank().equals("")) { str =
		 * "开户银行名称(详细到支行)不能为空"; return str; } } }
		 */

		return str;
	}

	private List<String> datelistAdd() {
		List<String> li = new ArrayList<String>();
		for (int i = 1; i <= 31; i++) {
			li.add(i + "日");
		}
		return li;
	}

	// 获取页面服务要求信息数据
	private Integer getServiceInfo() {
		Integer k = 0;
		Combobox sbtype = (Combobox) win.getFellow("sbtype");
		Combobox emfi_backdate = (Combobox) win.getFellow("emfi_backdate");
		Combobox coba_emfi_senddate = (Combobox) win
				.getFellow("coba_emfi_senddate");
		Combobox cardpay = (Combobox) win.getFellow("cardpay");
		Combobox dtdservice = (Combobox) win.getFellow("dtdservice");
		Combobox wt = (Combobox) win.getFellow("wt");
		Combobox fservice = (Combobox) win.getFellow("fservice");
		Combobox houseover = (Combobox) win.getFellow("houseover");
		Combobox actype = (Combobox) win.getFellow("actype");
		Combobox report = (Combobox) win.getFellow("report");
		Combobox taxctype = (Combobox) win.getFellow("taxctype");
		Combobox taxdetype = (Combobox) win.getFellow("taxdetype");
		Combobox taxpay = (Combobox) win.getFellow("taxpay");
		Combobox taxwt = (Combobox) win.getFellow("taxwt");
		Combobox taxde_date = (Combobox) win.getFellow("taxde_date");
		Combobox gzpayroll_type = (Combobox) win.getFellow("gzpayroll_type");
		Combobox gzpayroll_b = (Combobox) win.getFellow("gzpayroll_b");
		Combobox paydate = (Combobox) win.getFellow("paydate");
		Combobox senddate = (Combobox) win.getFellow("senddate");
		// 社会保险缴纳情况 1：非深户一档医保；2：非深户二档医保；3：委托外地缴纳
		if (sbtype.getValue() != null && !sbtype.getValue().equals("")) {
			k = 1;
			servicemodel.setCsqe_sbtype(Integer.parseInt(sbtype
					.getSelectedItem().getValue().toString()));
		}
		// 各种证件的办理和费用收取 1：个人付；2：随付款；3：公司付
		if (cardpay.getValue() != null && !cardpay.getValue().equals("")) {
			k = 1;
			servicemodel.setCsqe_cardpay(Integer.parseInt(cardpay
					.getSelectedItem().getValue().toString()));
		}
		// 上门服务 1、有；0、无
		if (dtdservice.getValue() != null && !dtdservice.getValue().equals("")) {
			k = 1;
			servicemodel.setCsqe_dtdservice(Integer.parseInt(dtdservice
					.getSelectedItem().getValue().toString()));
		}
		// 委托外地
		if (wt.getValue() != null && !wt.getValue().equals("")) {
			k = 1;
			servicemodel.setCsqe_wt(Integer.parseInt(wt.getSelectedItem()
					.getValue().toString()));
		}
		// 外籍人服务 1、有，0、无
		if (fservice.getValue() != null && !fservice.getValue().equals("")) {
			k = 1;
			servicemodel.setCsqe_fservice(Integer.parseInt(fservice
					.getSelectedItem().getValue().toString()));
		}

		if (houseover.getValue() != null && !houseover.getValue().equals("")) {
			k = 1;
			servicemodel.setCsqe_houseover(Integer.parseInt(houseover
					.getSelectedItem().getValue().toString()));
		}
		if (actype.getValue() != null && !actype.getValue().equals("")) {
			k = 1;
			servicemodel.setCsqe_actype(Integer.parseInt(actype
					.getSelectedItem().getValue().toString()));
		}
		if (report.getValue() != null && !report.getValue().equals("")) {
			k = 1;
			servicemodel.setCsqe_report(Integer.parseInt(report
					.getSelectedItem().getValue().toString()));
		}
		if (taxctype.getValue() != null && !taxctype.getValue().equals("")) {
			k = 1;
			servicemodel.setCsqe_taxctype(Integer.parseInt(taxctype
					.getSelectedItem().getValue().toString()));
		}
		if (taxdetype.getValue() != null && !taxdetype.getValue().equals("")) {
			k = 1;
			servicemodel.setCsqe_taxdetype(Integer.parseInt(taxdetype
					.getSelectedItem().getValue().toString()));
		}
		if (taxpay.getValue() != null && !taxpay.getValue().equals("")) {
			k = 1;
			servicemodel.setCsqe_taxpay(Integer.parseInt(taxpay
					.getSelectedItem().getValue().toString()));
		}
		if (taxwt.getValue() != null && !taxwt.getValue().equals("")) {
			k = 1;
			servicemodel.setCsqe_taxwt(Integer.parseInt(taxwt.getSelectedItem()
					.getValue().toString()));
		}
		if (taxde_date.getValue() != null && !taxde_date.getValue().equals("")) {
			k = 1;
			servicemodel.setCsqe_taxde_date(Integer.parseInt(taxde_date
					.getSelectedItem().getValue().toString()));
		}
		if (gzpayroll_type.getValue() != null
				&& !gzpayroll_type.getValue().equals("")) {
			k = 1;
			servicemodel.setCsqe_gzpayroll_type(Integer.parseInt(gzpayroll_type
					.getSelectedItem().getValue().toString()));
		}
		if (gzpayroll_b.getValue() != null
				&& !gzpayroll_b.getValue().equals("")) {
			k = 1;
			servicemodel.setCsqe_gzpayroll_b(Integer.parseInt(gzpayroll_b
					.getSelectedItem().getValue().toString()));
		}
		if (paydate.getValue() != null && !paydate.getValue().equals("")) {
			k = 1;
			servicemodel.setCsqe_gz_paydate(getdate(paydate.getSelectedItem()
					.getValue().toString()));
		}
		if (senddate.getValue() != null && !senddate.getValue().equals("")) {
			servicemodel.setCsqe_isenddate(getdate(senddate.getValue()
					.toString()));
		}
		if (servicemodel.getEmfi_backdate() != null
				&& !servicemodel.getEmfi_backdate().equals("")) {
			k = 1;
			servicemodel.setCoba_emfi_backdate(getdate(servicemodel
					.getEmfi_backdate()));
		}
		if (servicemodel.getCoba_emfi_senddatestr() != null
				&& !servicemodel.getCoba_emfi_senddatestr().equals("")) {
			k = 1;
			servicemodel.setCoba_emfi_senddate(getdate(servicemodel
					.getCoba_emfi_senddatestr()));
		}
		if (servicemodel.getEmfics_backdate() != null
				&& !servicemodel.getEmfics_backdate().equals("")) {
			k = 1;
			servicemodel.setCoba_emfics_backdate(getdate(servicemodel
					.getEmfics_backdate()));
		}
		if (servicemodel.getPapergz_paydate() != null
				&& !servicemodel.getPapergz_paydate().equals("")) {
			k = 1;
			servicemodel.setCoba_papergz_paydate(getdate(servicemodel
					.getPapergz_paydate()));
		}
		return k;
	}

	// 是否有薪酬信息
	private Integer ifExistsSalary() {
		Integer k = 0;
		if (servicemodel.getCsqe_sbhousetype() != null
				&& !servicemodel.getCsqe_sbhousetype().equals("")) {
			k = 2;
		} else if (servicemodel.getCsqe_sbhouse_trans() != null
				&& !servicemodel.getCsqe_sbhouse_trans().equals("")) {
			k = 2;
		} else if (servicemodel.getCsqe_houseover() != null
				&& !servicemodel.getCsqe_houseover().equals("")) {
			k = 2;
		} else if (servicemodel.getCsqe_actype() != null
				&& !servicemodel.getCsqe_actype().equals("")) {
			k = 2;
		} else if (servicemodel.getCsqe_report() != null
				&& !servicemodel.getCsqe_report().equals("")) {
			k = 2;
		} else if (servicemodel.getCsqe_taxctype() != null
				&& !servicemodel.getCsqe_taxctype().equals("")) {
			k = 2;
		} else if (servicemodel.getCsqe_taxdetype() != null
				&& !servicemodel.getCsqe_taxdetype().equals("")) {
			k = 2;
		} else if (servicemodel.getCsqe_taxpay() != null
				&& !servicemodel.getCsqe_taxpay().equals("")) {
			k = 2;
		} else if (servicemodel.getCsqe_taxwt() != null
				&& !servicemodel.getCsqe_taxwt().equals("")) {
			k = 2;
		} else if (servicemodel.getCsqe_taxwt_place() != null
				&& !servicemodel.getCsqe_taxwt_place().equals("")) {
			k = 2;
		} else if (servicemodel.getCsqe_taxde_date() != null
				&& !servicemodel.getCsqe_taxde_date().equals("")) {
			k = 2;
		} else if (servicemodel.getCsqe_gzpayroll_type() != null
				&& !servicemodel.getCsqe_gzpayroll_type().equals("")) {
			k = 2;
		} else if (servicemodel.getCsqe_gzpayroll_type() != null
				&& !servicemodel.getCsqe_gzpayroll_type().equals("")) {
			k = 2;
		} else if (servicemodel.getCsqe_gzpayroll_people() != null
				&& !servicemodel.getCsqe_gzpayroll_people().equals("")) {
			k = 2;
		} else if (servicemodel.getCsqe_gzpayroll_b() != null
				&& !servicemodel.getCsqe_gzpayroll_b().equals("")) {
			k = 2;
		}

		return k;
	}

	// 截取数字
	public String getNumbers(String content) {
		Pattern pattern = Pattern.compile("\\d+");
		Matcher matcher = pattern.matcher(content);
		while (matcher.find()) {
			return matcher.group(0);
		}
		return "";
	}

	// 新增联系人
	@Command("addLinkman")
	public void addLinkman() {
		CoAgencyLinkmanModel m = new CoAgencyLinkmanModel();
		m.setCali_id(0);
		m.setLbType(1);
		m.setLbStyle("color:blue;");
		m.setLbValue("(新增)");
		linkmanList.add(m);
		linkmanNum = linkmanList.size();
		BindUtils.postNotifyChange(null, null, this, "linkmanList");
		BindUtils.postNotifyChange(null, null, this, "linkmanNum");
	}

	// 删除联系人
	@Command("delLinkman")
	public void delLinkman(@BindingParam("m") CoAgencyLinkmanModel m) {
		try {
			if (m.getLbType() < 0) {
				m.setLbType(0);
				m.setLbStyle("");
				m.setLbValue("");
			} else {
				m.setLbType(-1);
				m.setLbStyle("color:red;");
				m.setLbValue("(删除)");
			}
			BindUtils.postNotifyChange(null, null, m, "lbType");
			BindUtils.postNotifyChange(null, null, m, "lbStyle");
			BindUtils.postNotifyChange(null, null, m, "lbValue");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	// 检查联系人信息
	private boolean checkLinkman() {
		try {
			int size = 0;
			CoAgencyLinkmanModel m;
			if (linkmanList.size() == 0) {
				Messagebox.show("请添加至少一个联系人。", "提示", Messagebox.OK,
						Messagebox.INFORMATION);
				return false;
			} else {
				for (int i = 0; i < linkmanList.size(); i++) {
					m = linkmanList.get(i);
					if (m.getLbType() >= 0) {
						if (m.getCali_name() == null
								|| "".equals(m.getCali_name().trim())) {
							Messagebox
									.show("嘿！联系人" + (i + 1) + "的名称，您漏填了。",
											"提示", Messagebox.OK,
											Messagebox.INFORMATION);
							return false;
						} else if ((m.getCali_email() == null || "".equals(m
								.getCali_email().trim())
								&& (m.getCali_email1() == null || "".equals(m
										.getCali_email1().trim())))) {
							Messagebox
									.show("嘿！联系人" + (i + 1) + "的电子邮箱1，您漏填了。",
											"提示", Messagebox.OK,
											Messagebox.INFORMATION);
							return false;
						} else if ((m.getCali_mobile() == null || "".equals(m
								.getCali_mobile().trim()))
								&& (m.getCali_tel() == null || "".equals(m
										.getCali_tel().trim()))
								&& (m.getCali_tel1() == null || "".equals(m
										.getCali_tel1().trim()))) {
							Messagebox
									.show("嘿！联系人" + (i + 1) + "，给个联系电话吧。",
											"提示", Messagebox.OK,
											Messagebox.INFORMATION);
							return false;
						}
						size++;
					}
					if (size <= 0) {
						Messagebox.show("请添加至少一个联系人。", "提示", Messagebox.OK,
								Messagebox.INFORMATION);
						return false;
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return true;
	}

	private Integer getdate(String strdate) {
		Integer d = null;
		if (strdate != null && !strdate.equals("")) {
			d = Integer.parseInt(strdate.replace("日", "").trim());
		}
		return d;
	}

	public CoLatencyClientModel getFrommodel() {
		return frommodel;
	}

	public void setFrommodel(CoLatencyClientModel frommodel) {
		this.frommodel = frommodel;
	}

	public List<String> getLoginlist() {
		return loginlist;
	}

	public void setLoginlist(List<String> loginlist) {
		this.loginlist = loginlist;
	}

	public List<String> getLoginlist2t() {
		return loginlist2t;
	}

	public void setLoginlist2t(List<String> loginlist2t) {
		this.loginlist2t = loginlist2t;
	}

	public List<String> getLoginlist3t() {
		return loginlist3t;
	}

	public void set3t(List<String> loginlist3t) {
		this.loginlist3t = loginlist3t;
	}

	public List<String> getDatelistt() {
		return datelistt;
	}

	public void setDatelistt(List<String> datelistt) {
		this.datelistt = datelistt;
	}

	public CoBaseModel getCmodel() {
		return cmodel;
	}

	public void setCmodel(CoBaseModel cmodel) {
		this.cmodel = cmodel;
	}

	public List<PubTradeModel> getTradelist() {
		return tradelist;
	}

	public void setTradelist(List<PubTradeModel> tradelist) {
		this.tradelist = tradelist;
	}

	public String getSign() {
		return sign;
	}

	public void setSign(String sign) {
		this.sign = sign;
	}

	public CoServiceRequestModel getServicemodel() {
		return servicemodel;
	}

	public void setServicemodel(CoServiceRequestModel servicemodel) {
		this.servicemodel = servicemodel;
	}

	public List<String> getManagerlist() {
		return managerlist;
	}

	public void setManagerlist(List<String> managerlist) {
		this.managerlist = managerlist;
	}

	public List<CoAgencyLinkmanModel> getLinkmanList() {
		return linkmanList;
	}

	public int getLinkmanNum() {
		return linkmanNum;
	}

	public SalaryPaperCoModel getCosalarysetm() {
		return cosalarysetm;
	}

	public void setCosalarysetm(SalaryPaperCoModel cosalarysetm) {
		this.cosalarysetm = cosalarysetm;
	}

	public boolean isCs() {
		return cs;
	}

	public void setCs(boolean cs) {
		this.cs = cs;
	}

	public CoFinanceVATModel getCoFinanceVATModel() {
		return coFinanceVATModel;
	}

	public void setCoFinanceVATModel(CoFinanceVATModel coFinanceVATModel) {
		this.coFinanceVATModel = coFinanceVATModel;
	}

	public boolean isOnly() {
		return only;
	}

	public void setOnly(boolean only) {
		this.only = only;
	}

	public boolean isSimple() {
		return simple;
	}

	public void setSimple(boolean simple) {
		this.simple = simple;
	}

}
