package Controller.CoLatencyClient;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.zkoss.bind.annotation.BindingParam;
import org.zkoss.bind.annotation.Command;
import org.zkoss.bind.annotation.NotifyChange;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.Path;
import org.zkoss.zul.Combobox;
import org.zkoss.zul.Grid;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Window;

import Model.CoBaseModel;
import Model.CoLatencyClientModel;
import Model.CoServiceRequestModel;
import Model.EmSalaryInfoModel;
import Model.SalaryPaperCoModel;
import Model.SalarySendTypeModel;
import Util.DateStringChange;
import Util.MonthListUtil;
import Util.RedirectUtil;
import Util.UserInfo;
import bll.CoLatencyClient.CoServiceRequestOperateBll;
import bll.CoLatencyClient.CoServiceRequestSelectBll;
import bll.EmSalary.ItemFormula_OperateBll;
import bll.EmSalary.ItemFormula_SelectBll;
import bll.SalaryPaper.SalaryPaperBll;

public class ColaSalaryInfoAddController {
	private String cid = Executions.getCurrent().getArg().get("cid").toString();
	private List<String> datelist = new ArrayList<String>();// 天的列表
	private CoServiceRequestModel servicemodel = new CoServiceRequestModel();
	private CoServiceRequestSelectBll bll = new CoServiceRequestSelectBll();
	private List<CoServiceRequestModel> list = bll
			.getRequestInfoList(" and a.cid=" + cid);
	private Window win = (Window) Path.getComponent("/win");
	private CoBaseModel model = bll.getCobaseInfo("and cid=" + cid);
	private CoLatencyClientModel m = bll.getCoLatencyClient(Integer
			.parseInt(cid));
	private SalaryPaperCoModel cosalarysetm = new SalaryPaperCoModel();

	public ColaSalaryInfoAddController() {
		datelist = datelistAdd();
		if (list.size() > 0) {
			servicemodel = list.get(0);
		}
		servicemodel.setCid(Integer.parseInt(cid));
	}

	// 创建窗口时检测该公司是否已有服务要求信息
	@Command
	public void createwin(@BindingParam("win") Window win) {
		if (servicemodel.getCsqe_salary() == 1) {
			Messagebox.show("该公司已有薪酬服务要求信息", "提示", Messagebox.OK,
					Messagebox.ERROR);
			reUrl();
		}
	}

	private void reUrl() {
		RedirectUtil util = new RedirectUtil();
		util.refreshEmUrl("../CoLatencyClient/ColaQuestInfo.zul");
	}

	// 人事服务要求新增
	@Command
	public void addrequest(@BindingParam("win") Window win) {
		CoServiceRequestOperateBll obll = new CoServiceRequestOperateBll();
		Integer k = ifEmploy(win);
		Integer row = 0;
		if (k > 0) {
			servicemodel.setCsqe_cola_id(m.getCola_id());
			// list.size()>0表示改潜在客户已经存在服务要求信息
			if (list.size() > 0) {
				servicemodel.setCsqe_salary(1);
				servicemodel.setCsqe_id(list.get(0).getCsqe_id());
				row = obll.CoServiceRequest_update(servicemodel);
			} else {
				row = obll.CoServiceRequests_Add(servicemodel);
			}
			if (row > 0) {
				Messagebox.show("提交成功", "提示", Messagebox.OK,
						Messagebox.INFORMATION);
				win.detach();
			} else {
				Messagebox.show("提交失败", "提示", Messagebox.OK, Messagebox.ERROR);
			}
		} else {
			Messagebox.show("请录入服务要求信息", "提示", Messagebox.OK, Messagebox.ERROR);
		}
	}

	// 服务要求修改
	@Command
	public void updaterequest(@BindingParam("win") Window win) {
		CoServiceRequestOperateBll obll = new CoServiceRequestOperateBll();
		Integer k = ifEmploy(win);
		Integer row = 0;
		if (k > 0) {
			servicemodel.setCsqe_cola_id(m.getCola_id());
			// list.size()>0表示改潜在客户已经存在服务要求信息
			if (list.size() > 0) {
				row = obll.CoServiceRequest_update(servicemodel);
			} else {
				row = obll.CoServiceRequests_Add(servicemodel);
			}
			if (row > 0) {
				Messagebox.show("提交成功", "提示", Messagebox.OK,
						Messagebox.INFORMATION);
				win.detach();
			} else {
				Messagebox.show("提交失败", "提示", Messagebox.OK, Messagebox.ERROR);
			}
		} else {
			Messagebox.show("请录入服务要求信息", "提示", Messagebox.OK, Messagebox.ERROR);
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

	// 修改email工资单类型
	@Command
	@NotifyChange("cosalarysetm")
	public void changeGZEmailtype(@BindingParam("emailtype") String emailtype) {
		if (emailtype.equals("附件pdf") || emailtype.equals("附件excel")) {
			cosalarysetm.setCoss_emailouto(0);
			Messagebox.show(
					"附件pdf 和 附件excel 类型的工资单暂时不能自动发送，已将“Email工资单是否自动发送”改成“否”！",
					"操作提示", Messagebox.OK, Messagebox.INFORMATION);
		}

	}

	// 薪酬服务信息新增
	@Command
	public void salaryadd(@BindingParam("win") Window win) {
		CoServiceRequestOperateBll obll = new CoServiceRequestOperateBll();
		Integer k = ifSalaryOk(win);
		Integer row = 0;
		int i = 0;
		if (k > 0) {
			servicemodel.setCsqe_cola_id(m.getCola_id());
			if (list.size() > 0) {
				servicemodel.setCsqe_salary(1);
				servicemodel.setCsqe_id(list.get(0).getCsqe_id());
				row = obll.CoServiceRequest_update(servicemodel);
			} else {

				row = obll.CoServiceRequest_Add(servicemodel, 1);
				// 插入工资单表
				cosalarysetm.setCid(Integer.parseInt(cid));
				SalaryPaperBll spb = new SalaryPaperBll();
				i = spb.coinfoadd(cosalarysetm);

				// 查询是否有个税
				ItemFormula_SelectBll itembll = new ItemFormula_SelectBll();
				int chkGZ = itembll.chkIfGZ(" and b.cid=" + cid);
				if (chkGZ != 0) {
					// 获取用户名
					String username = UserInfo.getUsername();

					// 获取当前所属月份
					DateStringChange dsc = new DateStringChange();
					MonthListUtil mlUtil = new MonthListUtil();
					Date nowDate = new Date(); // 获取当前时间
					int ownmonth = Integer.parseInt(dsc.DatetoSting(nowDate,
							"yyyyMM"));
					// 在CoSalaryItemIDInfo表新增一条数据
					ItemFormula_OperateBll ioBll = new ItemFormula_OperateBll();
					String[] msg = ioBll.clcAddSalaryItemId(
							Integer.parseInt(cid), ownmonth, username);
					if (!msg[0].equals("1")) {
						Messagebox.show(msg[1], "操作提示", Messagebox.OK,
								Messagebox.ERROR);
					}
				}
			}

			if (row + i > 0) {
				Messagebox.show("提交成功", "提示", Messagebox.OK,
						Messagebox.INFORMATION);
				win.detach();
			} else {
				Messagebox.show("提交失败", "提示", Messagebox.OK, Messagebox.ERROR);
			}
		}
	}

	// 检查输入的人事服务要求是否为空
	private Integer ifEmploy(Window win) {
		Integer k = 0;
		if (servicemodel.getIsenddate() != null
				&& !servicemodel.getIsenddate().equals("")) {
			servicemodel
					.setCsqe_isenddate(getdate(servicemodel.getIsenddate()));
			k = 1;
		}
		if (servicemodel.getSbtype() != null
				&& !servicemodel.getSbtype().equals("")) {
			Combobox sbtype = (Combobox) win.getFellow("sbtype");
			servicemodel.setCsqe_sbtype(Integer.parseInt(sbtype
					.getSelectedItem().getValue().toString()));
			k = 1;
		}
		if (servicemodel.getCsqe_house() != null
				&& !servicemodel.getCsqe_house().equals("")) {
			k = 1;
		}
		if (servicemodel.getCsqe_regist() != null
				&& !servicemodel.getCsqe_regist().equals("")) {
			k = 1;
		}
		if (servicemodel.getCardpay() != null
				&& !servicemodel.getCardpay().equals("")) {
			Combobox cardpay = (Combobox) win.getFellow("cardpay");
			servicemodel.setCsqe_cardpay(Integer.parseInt(cardpay
					.getSelectedItem().getValue().toString()));
			k = 1;
		}
		if (servicemodel.getDtdservice() != null
				&& !servicemodel.getDtdservice().equals("")) {
			Combobox dtdservice = (Combobox) win.getFellow("dtdservice");
			servicemodel.setCsqe_dtdservice(Integer.parseInt(dtdservice
					.getSelectedItem().getValue().toString()));
			k = 1;
		}
		if (servicemodel.getWt() != null && !servicemodel.getWt().equals("")) {
			Combobox wt = (Combobox) win.getFellow("wt");
			servicemodel.setCsqe_wt(Integer.parseInt(wt.getSelectedItem()
					.getValue().toString()));
			k = 1;
		}
		if (servicemodel.getFservice() != null
				&& !servicemodel.getFservice().equals("")) {
			Combobox fservice = (Combobox) win.getFellow("fservice");
			servicemodel.setCsqe_fservice(Integer.parseInt(fservice
					.getSelectedItem().getValue().toString()));
			k = 1;
		}
		if (servicemodel.getEmfi_backdate() != null
				&& !servicemodel.getEmfi_backdate().equals("")) {
			Combobox emfi_backdate = (Combobox) win.getFellow("emfi_backdate");
			servicemodel.setCoba_emfi_backdate(getdate(emfi_backdate
					.getSelectedItem().getValue().toString()));
			k = 1;
		}
		if (servicemodel.getCsqe_other() != null
				&& !servicemodel.getCsqe_other().equals("")) {
			k = 1;
		}
		if (servicemodel.getCsqe_ispa() != null
				&& !servicemodel.getCsqe_ispa().equals("")) {
			k = 1;
		}
		if (servicemodel.getCsqe_ws() != null
				&& !servicemodel.getCsqe_ws().equals("")) {
			k = 1;
		}
		if (servicemodel.getCsqe_todo() != null
				&& !servicemodel.getCsqe_todo().equals("")) {
			k = 1;
		}
		return k;
	}

	// 检查输入的薪酬服务信息
	private Integer ifSalaryOk(Window win) {
		Integer k = 0;
		if (servicemodel.getEmfics_backdate() != null
				&& !servicemodel.getEmfics_backdate().equals("")) {
			servicemodel.setCoba_emfics_backdate(getdate(servicemodel
					.getEmfics_backdate()));
			k = 1;
		}
		if (servicemodel.getPaydate() != null
				&& !servicemodel.getPaydate().equals("")) {
			servicemodel.setCsqe_gz_paydate(getdate(servicemodel.getPaydate()));
			k = 1;
		}
		if (servicemodel.getPapergz_paydate() != null
				&& !servicemodel.getPapergz_paydate().equals("")) {
			servicemodel.setCoba_papergz_paydate(getdate(servicemodel
					.getPapergz_paydate()));
			k = 1;
		}
		if (servicemodel.getCsqe_sbhousetype() != null
				&& !servicemodel.getCsqe_sbhousetype().equals("")) {
			k = 1;
		}
		if (servicemodel.getCsqe_sbhouse_trans() != null
				&& !servicemodel.getCsqe_sbhouse_trans().equals("")) {
			k = 1;
		}
		if (servicemodel.getHouseover() != null
				&& !servicemodel.getHouseover().equals("")) {
			Combobox houseover = (Combobox) win.getFellow("houseover");
			servicemodel.setCsqe_houseover(Integer.parseInt(houseover
					.getSelectedItem().getValue().toString()));
			k = 1;
		}
		if (servicemodel.getActype() != null
				&& !servicemodel.getActype().equals("")) {
			Combobox actype = (Combobox) win.getFellow("actype");
			servicemodel.setCsqe_actype(Integer.parseInt(actype
					.getSelectedItem().getValue().toString()));
			k = 1;
		}
		if (servicemodel.getReport() != null
				&& !servicemodel.getReport().equals("")) {
			Combobox report = (Combobox) win.getFellow("report");
			servicemodel.setCsqe_report(Integer.parseInt(report
					.getSelectedItem().getValue().toString()));
			k = 1;
		}
		if (servicemodel.getTaxctype() != null
				&& !servicemodel.getTaxctype().equals("")) {
			Combobox taxctype = (Combobox) win.getFellow("taxctype");
			servicemodel.setCsqe_taxctype(Integer.parseInt(taxctype
					.getSelectedItem().getValue().toString()));
			k = 1;
		}
		if (servicemodel.getTaxdetype() != null
				&& !servicemodel.getTaxdetype().equals("")) {
			Combobox taxdetype = (Combobox) win.getFellow("taxdetype");
			servicemodel.setCsqe_taxdetype(Integer.parseInt(taxdetype
					.getSelectedItem().getValue().toString()));
			k = 1;
		}
		if (servicemodel.getTaxpay() != null
				&& !servicemodel.getTaxpay().equals("")) {
			Combobox taxpay = (Combobox) win.getFellow("taxpay");
			servicemodel.setCsqe_taxpay(Integer.parseInt(taxpay
					.getSelectedItem().getValue().toString()));
			k = 1;
		}
		if (servicemodel.getTaxwt() != null
				&& !servicemodel.getTaxwt().equals("")) {
			Combobox taxwt = (Combobox) win.getFellow("taxwt");
			servicemodel.setCsqe_taxwt(Integer.parseInt(taxwt.getSelectedItem()
					.getValue().toString()));
			System.out.println("taxwt=" + taxwt.getSelectedItem().getValue());
			k = 1;
		}
		if (servicemodel.getTaxde_date() != null
				&& !servicemodel.getTaxde_date().equals("")) {
			Combobox taxde_date = (Combobox) win.getFellow("taxde_date");
			servicemodel.setCsqe_taxde_date(Integer.parseInt(taxde_date
					.getSelectedItem().getValue().toString()));
			k = 1;
		}
		if (servicemodel.getGzpayroll_type() != null
				&& !servicemodel.getGzpayroll_type().equals("")) {
			Combobox gzpayroll_type = (Combobox) win
					.getFellow("gzpayroll_type");
			servicemodel.setCsqe_gzpayroll_type(Integer.parseInt(gzpayroll_type
					.getSelectedItem().getValue().toString()));
			k = 1;
		}
		if (servicemodel.getGzpayroll_b() != null
				&& !servicemodel.getGzpayroll_b().equals("")) {
			Combobox gzpayroll_b = (Combobox) win.getFellow("gzpayroll_b");
			servicemodel.setCsqe_gzpayroll_b(Integer.parseInt(gzpayroll_b
					.getSelectedItem().getValue().toString()));
			k = 1;
		}
		return k;
	}

	private List<String> datelistAdd() {
		List<String> li = new ArrayList<String>();
		for (int i = 1; i <= 31; i++) {
			li.add(i + "日");
		}
		return li;
	}

	// 去掉日
	private Integer getdate(String strdate) {
		Integer d = null;
		if (strdate != null && !strdate.equals("")) {
			d = Integer.parseInt(strdate.replace("日", "").trim());
		}
		return d;
	}

	public SalaryPaperCoModel getCosalarysetm() {
		return cosalarysetm;
	}

	public void setCosalarysetm(SalaryPaperCoModel cosalarysetm) {
		this.cosalarysetm = cosalarysetm;
	}

	public List<String> getDatelist() {
		return datelist;
	}

	public void setDatelist(List<String> datelist) {
		this.datelist = datelist;
	}

	public CoServiceRequestModel getServicemodel() {
		return servicemodel;
	}

	public void setServicemodel(CoServiceRequestModel servicemodel) {
		this.servicemodel = servicemodel;
	}

	public CoBaseModel getModel() {
		return model;
	}

	public void setModel(CoBaseModel model) {
		this.model = model;
	}

}
