package Controller.EmFinanceManage;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.xbill.DNS.Cache;
import org.zkoss.bind.annotation.BindingParam;
import org.zkoss.bind.annotation.Command;
import org.zkoss.bind.annotation.NotifyChange;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zul.Cell;
import org.zkoss.zul.Checkbox;
import org.zkoss.zul.Datebox;
import org.zkoss.zul.Grid;
import org.zkoss.zul.Label;
import org.zkoss.zul.ListModelList;
import org.zkoss.zul.Listitem;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Row;
import org.zkoss.zul.Window;

import sun.text.normalizer.UTF16;
import B.K;
import Model.CoBaseModel;
import Model.CoFinanceCollectImportModel;
import Model.CoFinanceCollectModel;
import Model.CoFinanceSortAccountssModel;
import Model.CoFinanceVATModel;
import Model.FinanceInvoiceModel;
import Model.SbGatherModel;
import Util.SingleBllFactory;
import Util.UserInfo;
import bll.CoBase.CoBase_SelectBll;
import bll.CoFinanceManage.Cfma_CollectImportBll;
import bll.CoFinanceManage.Cfma_SelBll;
import bll.CoLatencyClient.CoFinanceVatBll;
import bll.EmFinanceManage.Finance_InvoiceBll;
import bll.EmFinanceManage.GatherInfoBll;
import bll.EmFinanceManage.GatherInfoNewBll;

public class Finance_GatheringListssController {
	private DecimalFormat myformat = new DecimalFormat("0");
	private List<CoBaseModel> list = new ArrayList<CoBaseModel>();
	private GatherInfoBll bll = new GatherInfoBll();
	private GatherInfoNewBll bll1 = new GatherInfoNewBll();
	private CoFinanceVatBll vatBll = new CoFinanceVatBll();
	private Finance_InvoiceBll fbll = Finance_InvoiceBll.getInstance();
	private Date startDate;
	private Date endDate, ownmonth;
	private String cid = "", company = "", client = "";
	private BigDecimal total = BigDecimal.ZERO;
	private BigDecimal fee;
	private String colsql = "";
	private String inyongyou = "";
	private Date nowdate = new Date();
	private String remark = "";

	private String fpfrist = "";
	private String ufclass = "";
	private String cfss_type = "";
	private String cfss_fpstate;
	private Window win;

	public Finance_GatheringListssController() {
		startDate = new Date();
		endDate = new Date();
		if (startDate != null && endDate != null) {
			// CoFinanceCollect表记录每笔公司收款
			colsql = colsql
					+ " and convert(varchar(10),cfss_addtime,120) BETWEEN "
					+ " '" + dateToStr(startDate) + "' and '"
					+ dateToStr(endDate) + "'";
		}
		list = bll.setAmountToCollectss(colsql);
	}
	
	@Command
	public void winD(@BindingParam("a") Window w){
		win=w;
	}

	@Command
	public void checkCb(@BindingParam("cel") Cell cell,
			@BindingParam("m") CoBaseModel model) {
		Row row = (Row) cell.getParent();
		if (row.getChildren() != null && row.getChildren().size() > 0) {
			Cell rcell = (Cell) row.getChildren().get(0);
			if (rcell.getChildren() != null && rcell.getChildren().size() > 1) {
				Checkbox cb = (Checkbox) rcell.getChildren().get(1);
				if (cb.isChecked()) {
					model.setChecked(false);
					cb.setChecked(false);
				} else {
					model.setChecked(true);
					cb.setChecked(true);
				}
			}
		}
	}

	@Command
	@NotifyChange("total")
	public void selectLabel(@BindingParam("cel") Cell cell,
			@BindingParam("type") String type) {
		if (cell.getChildren() != null && cell.getChildren().size() > 0) {
			Label lab = (Label) cell.getChildren().get(0);
			if (lab.getStyle() != null
					&& (lab.getStyle().equals("color:red;") || lab.getStyle()
							.equals("color:#000099;"))) {// 消除
				String newfee = "0.00";
				if (lab.getValue() != null && !lab.getValue().equals("")) {
					newfee = lab.getValue();
					total = total.subtract(new BigDecimal(newfee));
				}

				lab.setStyle("color:#000000;");
				cell.setStyle("background-color:#f5fafe;");
				if (type != null && type.equals("total")) {
					lab.setStyle("color:#0000FF;");
				}
			} else {// 添加
				String newfee = "0.00";
				if (lab.getValue() != null && !lab.getValue().equals("")) {
					newfee = lab.getValue();
					total = total.add(new BigDecimal(newfee));
				}
				lab.setStyle("color:red;");
				cell.setStyle("background-color:#1C120E;");
				if (type != null && type.equals("total")) {
					lab.setStyle("color:#000099;");
					cell.setStyle("background-color:#CC3333;");
				}
			}
		}

	}

	@Command
	@NotifyChange("total")
	public void selectTotall(@BindingParam("cel") Cell cell) {
		if (cell.getChildren() != null && cell.getChildren().size() > 0) {
			Label lab = (Label) cell.getChildren().get(0);
			if (lab.getStyle() != null && lab.getStyle().equals("color:red;")) {// 消除
				String newfee = "0.00";
				if (lab.getValue() != null && !lab.getValue().equals("")) {
					newfee = lab.getValue();
					total = total.subtract(new BigDecimal(newfee));
				}
				lab.setStyle("color:#0000FF;");
				cell.setStyle("background-color:#f5fafe;");
			} else {// 添加
				String newfee = "0.00";
				if (lab.getValue() != null && !lab.getValue().equals("")) {
					newfee = lab.getValue();
					total = total.add(new BigDecimal(newfee));
				}
				lab.setStyle("color:#0000FF;");
				cell.setStyle("background-color:#CC3333;");
			}
		}

	}

	@Command
	public void addoffer(@BindingParam("gd") Grid gd) {
		List<CoBaseModel> checklist = new ArrayList<CoBaseModel>();
		int num = gd.getPageSize();
		if (list.size() < num) {
			num = list.size();
		}
		for (int i = 0; i < num; i++) {
			if (gd.getCell(i, 0) != null) {
				Cell cell = (Cell) gd.getCell(i, 0);
				if (cell.getChildren().size() > 1) {
					Checkbox ck = (Checkbox) cell.getChildren().get(1);
					if (ck.isChecked()) {
						CoBaseModel m = ck.getValue();
						checklist.add(m);
					}
				}
			}
		}
		if (checklist.size() > 0) {
			Map map = new HashMap<>();
			map.put("list", checklist);
			map.put("ownmonth", datesToStr(ownmonth));
			Window window = (Window) Executions.createComponents(
					"Finance_GatherInfoList.zul", null, map);
			window.doModal();
		} else {
			Messagebox.show("请选择数据", "提示", Messagebox.OK, Messagebox.ERROR);
		}
	}

	@Command
	public void addsk(@BindingParam("gd") Grid gd) {

		CoBase_SelectBll cobasebll = new CoBase_SelectBll();
		Cfma_SelBll cbll = new Cfma_SelBll();
		CoBaseModel cobasemodel = null;

		if ((cid.equals("") || cid.equals("0")) && company.equals("")) {

			Messagebox.show("请先输入公司名或公司编号。", "提示", Messagebox.OK,
					Messagebox.ERROR);
		} else {

			List<CoBaseModel> cobasemodellsit = new ArrayList<CoBaseModel>();
			if (!company.equals("")) {
				cobasemodellsit = cobasebll
						.getCobaseinfo(" and coba_servicestate=1 and a.coba_company like '%"
								+ company + "%'");
			}

			else {
				cobasemodellsit = cobasebll
						.getCobaseinfo(" and coba_servicestate=1 and a.cid="
								+ cid + "");
			}

			if (cobasemodellsit.size() == 0) {
				Messagebox.show("系统无法查询到改公司，请核查！", "操作提示", Messagebox.OK,
						Messagebox.ERROR);

				return;
			} else if (cobasemodellsit.size() > 1) {
				Messagebox.show("系统查询到2个以上记录，请核查！", "操作提示", Messagebox.OK,
						Messagebox.ERROR);
				return;
			} else {

				cobasemodel = cobasemodellsit.get(0);

				Map map = new HashMap<>();
				map.put("cid", cid);
				map.put("company", company);

				Window window = (Window) Executions.createComponents(
						"../CoFinanceManage/Cfma_CoGathering.zul", null, map);

				window.doModal();

			}
		}

	}

	// 弹出修改页面
	@Command
	@NotifyChange("list")
	public void edit(@BindingParam("model") CoBaseModel model) {
		Map map = new HashMap<>();
		map.put("cid", model.getCid());
		map.put("type", "1");
		Window window = (Window) Executions.createComponents(
				"Finance_GatherInfoEdit.zul", null, map);
		window.doModal();
		String owns = "";
		if (ownmonth != null) {
			owns = datesToStr(ownmonth);
			colsql = colsql + " and ownmonth=" + owns;
		}

		list = bll.setAmountToCollectss(colsql);
		total = BigDecimal.ZERO;
	}

	@Command
	@NotifyChange({ "list", "total" })
	public void search() {

		colsql = "";
		total = BigDecimal.ZERO;
		if (cid != null && !cid.equals("")) {
			colsql = colsql + " and a.cid=" + cid;
		}
		if (company != null && !company.equals("")) {
			colsql = colsql + " and (coba_company like '%" + company
					+ "%' or coba_shortname like '%" + company + "%')";
		}
		if (client != null && !client.equals("")) {
			colsql = colsql + " and coba_client like '%" + client + "%'";
		}

		if (startDate != null && endDate != null) {
			colsql = colsql
					+ " and convert(varchar(10),cfss_addtime,120) BETWEEN "
					+ " '" + dateToStr(startDate) + "' and '"
					+ dateToStr(endDate) + "'";
		}
		String owns = "";
		
		ownmonth=((Datebox) win.getFellow("ownmonth")).getValue();
		if (ownmonth != null) {
			owns = datesToStr(ownmonth);
			colsql = colsql + " and ownmonth=" + owns;
		}
		if (inyongyou != null && !inyongyou.equals("")) {
			if (inyongyou.equals("未录用友")) {
				colsql = colsql + " and cfss_yystate=0";
			} else {
				colsql = colsql + " and cfss_yystate=1";
			}
		}
		if (remark != null && !remark.equals("")) {

			colsql = colsql + " and cfss_remark like '%" + remark + "%'";
		}

		if (ufclass != null && !ufclass.equals("")) {
			if (ufclass.equals("FS")) {
				colsql = colsql + " and coba_ufclass ='224106'";
			} else if (ufclass.equals("AF")) {
				colsql = colsql + " and coba_ufclass ='224105'";
			}

		}

		if (fpfrist != null && !fpfrist.equals("")) {
			if (fpfrist.equals("是")) {
				colsql = colsql + " and cfss_fpfrist  =1";
			} else if (fpfrist.equals("否")) {
				colsql = colsql + " and cfss_fpfrist  =0";
			}
		}

		if (cfss_type != null && !cfss_type.equals("")) {
			colsql = colsql + " and cfss_type='" + cfss_type + "'";
		}
		if (cfss_fpstate != null && !cfss_fpstate.equals("")) {
			if (cfss_fpstate.equals("已开票")) {
				colsql = colsql + " and cfss_fpstate=1";
			} else if (cfss_fpstate.equals("未开票")) {
				colsql = colsql + " and cfss_fpstate=0";
			}
		}

		list = bll.setAmountToCollectss(colsql);
		if (fee != null) {
			List<CoBaseModel> nlist = new ArrayList<CoBaseModel>();
			for (CoBaseModel cm : list) {
				BigDecimal totalp = new BigDecimal(myformat.format(cm
						.getAmount().getCfmb_TotalPaidIn()));
				BigDecimal totafee = new BigDecimal(myformat.format(fee));
				if (totalp.compareTo(totafee) == 0) {
					nlist.add(cm);
				}
			}
			list = nlist;
		}
	}

	// 弹出修改页面
	@SuppressWarnings("unchecked")
	@Command
	@NotifyChange("list")
	public void editUfid(@BindingParam("model") CoBaseModel model) {
		Map map = new HashMap<>();
		map.put("cfss_cfso_id", model.getCfss_cfso_id());
		// map.put("type", "1");
		Window window = (Window) Executions.createComponents(
				"../CoFinanceManage/Cfma_CoGatheringmod.zul", null, map);
		window.doModal();
		list = bll.setAmountToCollectss(colsql);
		total = BigDecimal.ZERO;
	}

	@Command
	public void printGa() {
		List<CoBaseModel> clist = new ListModelList<>();
		for (CoBaseModel model : list) {
			if (model.getChecked() != null && model.getChecked()) {
				clist.add(model);

			}
		}
		if (clist.size() > 0) {
			Map<String, List<CoBaseModel>> map = new HashMap<String, List<CoBaseModel>>();
			map.put("cfcmList", clist);
			Window window = (Window) Executions.createComponents(
					"../CoFinanceManage/Cfma_invoiceAddDoc.zul", null, map);
			window.doModal();
		} else {
			Messagebox.show("请选择收款!", "操作提示", Messagebox.OK, Messagebox.ERROR);
		}
		total = BigDecimal.ZERO;

	}

	// 添加发票信息
	@Command
	// @NotifyChange({ "list", "total" })
	public void invoice(@BindingParam("a") CoBaseModel m) {
		Map map = new HashMap<>();
		map.put("model", m);
		Window window = (Window) Executions.createComponents(
				"Finance_Invoice.zul", null, map);
		window.doModal();
		// search();
	}

	// 批量打印发票
	@Command
	public void printInvoice() throws Exception {
		if (UserInfo.getUsername().equals("张笑笑")) {

			Integer k = 0;
			for (CoBaseModel m : list) {
				if (m.getChecked() != null && m.getChecked()) {
					if (m.getCfva_confirm() == null || !m.getCfva_confirm()) {
						Messagebox.show(m.getCoba_shortname() + ",发票信息未确认.",
								"操作提示", Messagebox.OK, Messagebox.ERROR);
						return;
					}
				}
			}
			for (CoBaseModel m : list) {
				if (m.getChecked() != null && m.getChecked()) {
					CoFinanceVATModel cfm = new CoFinanceVATModel();
					List<CoFinanceVATModel> ilist = vatBll.getCoFinanceVatDat(m
							.getCid());
					if (list.size() > 0) {
						cfm = ilist.get(0);
						// 写入发票信息
					} else {
						cfm.setCid(m.getCid());
						cfm.setCfva_company(m.getCoba_company());
						cfm.setCfva_only(true);
						cfm.setCfva_simple(true);
						vatBll.addCoFinanceVat(cfm);
					}
					FinanceInvoiceModel fim = new FinanceInvoiceModel();// 专票
					FinanceInvoiceModel fim2 = new FinanceInvoiceModel();// 普票
					fim.setFplx(1);
					fim.setGfmc(cfm.getCfva_title() == null ? "" : cfm
							.getCfva_title());
					fim.setGfsh(cfm.getCfva_number1() == null ? "" : cfm
							.getCfva_number1());
					fim.setGfdzdh((cfm.getCfva_reg_add() == null ? "" : cfm
							.getCfva_reg_add())
							+ " "
							+ (cfm.getCfva_tel() == null ? "" : cfm
									.getCfva_tel()));
					fim.setGfyhzh((cfm.getCfva_bank() == null ? "" : cfm
							.getCfva_bank())
							+ " "
							+ (cfm.getCfva_bank_acc() == null ? "" : cfm
									.getCfva_bank_acc()));

					fim2.setFplx(0);
					fim2.setGfmc(cfm.getCfva_title() == null ? "" : cfm
							.getCfva_title());
					fim2.setGfsh(cfm.getCfva_number1() == null ? "" : cfm
							.getCfva_number1());
					fim2.setGfdzdh((cfm.getCfva_reg_add() == null ? "" : cfm
							.getCfva_reg_add())
							+ " "
							+ (cfm.getCfva_tel() == null ? "" : cfm
									.getCfva_tel()));
					fim2.setGfyhzh((cfm.getCfva_bank() == null ? "" : cfm
							.getCfva_bank())
							+ " "
							+ (cfm.getCfva_bank_acc() == null ? "" : cfm
									.getCfva_bank_acc()));

					// 读取收款记录
					List<CoFinanceSortAccountssModel> list = fbll.getList(m
							.getCfss_cfso_id());
					if (list.size() > 0) {
						if (list.get(0).getCfss_type() != null) {

							String type = list.get(0).getCfss_type();
							fim = fbll.splitInvoice(list, type, fim);
							if (cfm.getCfva_only() != null
									&& cfm.getCfva_simple() != null
									&& cfm.getCfva_only().equals(false)
									&& cfm.getCfva_simple().equals(true)) {
								for (int i = 0; i < fim.getList().size(); i++) {
									fim2.getList().add(fim.getList().get(i));
									fim.getList().remove(i);
									i--;
								}
							} else {
								for (int i = 0; i < fim.getList().size(); i++) {
									if (fim.getList().get(i).getKind()
											.equals("普票")) {
										fim2.getList()
												.add(fim.getList().get(i));
										fim.getList().remove(i);
										i--;
									}
								}
							}

						}
					}

					if (fim.getList().size() > 0) {
						fim.setPrintState(true);
						Collections.sort(fim.getList());
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
						Collections.sort(fim2.getList());
						fim2.setPrintState(true);
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
					List<FinanceInvoiceModel> invoiceList = fbll
							.getInvoiceList(m.getCfss_cfso_id());
					if (fim.getList().size() > 0 && fim.isPrintState()) {
						fim.setDjh(m.getCfss_cfso_id() + "-"
								+ (invoiceList.size() + 1));
						i = fbll.add(fim);
					}
					if (fim2.getList().size() > 0 && fim2.isPrintState()) {
						fim2.setDjh(m.getCfss_cfso_id() + "-"
								+ (invoiceList.size() + (i > 0 ? 2 : 1)));
						j = fbll.add(fim2);
					}
					if (i.equals(0) && j.equals(0)) {
						k = 1;
					}
				}
			}
			if (k.equals(0)) {
				Messagebox.show("添加成功.", "操作提示", Messagebox.OK,
						Messagebox.ERROR);
			}
		} else {
			Messagebox.show("请通知IT开通打印权限.", "操作提示", Messagebox.OK,
					Messagebox.ERROR);
		}
	}

	@Command
	public void checkAll(@BindingParam("gd") Grid gd,
			@BindingParam("ck") Checkbox allCk) {
		int num = gd.getPageSize();
		if (list.size() < num) {
			num = list.size();
		}
		for (int i = 0; i < num; i++) {
			if (gd.getCell(i, 0) != null) {
				Cell cell = (Cell) gd.getCell(i, 0);
				if (cell.getChildren().size() > 1) {
					Checkbox ck = (Checkbox) cell.getChildren().get(1);
					CoBaseModel m = ck.getValue();
					m.setChecked(allCk.isChecked());
					ck.setChecked(allCk.isChecked());
				}
			}
		}
	}

	// 修改开票状态
	@Command("editFpstate")
	@NotifyChange({ "list", "total" })
	public void editFpstate(@BindingParam("m") CoBaseModel m) {

		if (UserInfo.getUsername().equals("张笑笑")) {

			Integer cfss_fpstate = 0;
			total = BigDecimal.ZERO;
			if (m.getCfss_fpstate().equals("未开票")) {
				cfss_fpstate = 1;
			} else {
				cfss_fpstate = 0;
			}
			int a = bll1.updateCfss_fpstate(m.getCfss_cfso_id(), cfss_fpstate);
			list = bll.setAmountToCollectss(colsql);
		} else {
			Messagebox.show("请通知IT开通修改权限.", "操作提示", Messagebox.OK,
					Messagebox.ERROR);
		}
		//
		// if(a>0){
		// if(Executions.getCurrent().getDesktop().getSession().getAttribute("cid1")!=null){
		// cid=Executions.getCurrent().getDesktop().getSession().getAttribute("cid1").toString();
		// }
		// if(Executions.getCurrent().getDesktop().getSession().getAttribute("company1")!=null){
		// company=Executions.getCurrent().getDesktop().getSession().getAttribute("company1").toString();
		// }
		// if(Executions.getCurrent().getDesktop().getSession().getAttribute("client1")!=null){
		// client=Executions.getCurrent().getDesktop().getSession().getAttribute("client1").toString();
		// }
		// if(Executions.getCurrent().getDesktop().getSession().getAttribute("startDate1")!=null){
		// startDate=(Date)
		// Executions.getCurrent().getDesktop().getSession().getAttribute("startDate1");
		// }
		// if(Executions.getCurrent().getDesktop().getSession().getAttribute("endDate1")!=null){
		// endDate=(Date)
		// Executions.getCurrent().getDesktop().getSession().getAttribute("endDate1");
		// }
		// if(Executions.getCurrent().getDesktop().getSession().getAttribute("ownmonth1")!=null){
		// ownmonth=(Date)
		// Executions.getCurrent().getDesktop().getSession().getAttribute("ownmonth1");
		// }
		// if(Executions.getCurrent().getDesktop().getSession().getAttribute("inyongyou1")!=null){
		// inyongyou=(String)
		// Executions.getCurrent().getDesktop().getSession().getAttribute("inyongyou1");
		// }
		// colsql = "";
		// total = BigDecimal.ZERO;
		// if (cid != null && !cid.equals("")) {
		// colsql = colsql + " and a.cid=" + cid;
		// }
		// if (company != null && !company.equals("")) {
		// colsql = colsql + " and coba_company like '%" + company + "%'";
		// }
		// if (client != null && !client.equals("")) {
		// colsql = colsql + " and coba_client like '%" + client + "%'";
		// }
		//
		// if (startDate != null && endDate != null) {
		// colsql=colsql+" and convert(varchar(10),cfss_addtime,120) BETWEEN " +
		// " '"+dateToStr(startDate)+"' and '"+dateToStr(endDate)+"'";
		// }
		// String owns = "";
		// if (ownmonth != null) {
		// owns = datesToStr(ownmonth);
		// colsql = colsql + " and ownmonth=" + owns;
		// }
		// if (inyongyou != null && !inyongyou.equals("")) {
		// if (inyongyou.equals("未录用友")) {
		// colsql=colsql+" and cfss_yystate=0";
		// }else
		// {
		// colsql=colsql+" and cfss_yystate=1";
		// }
		// }
		// if(remark!=null&&!remark.equals(""))
		// {
		//
		// }
		// list = bll1.setAmountToCollectssNew(colsql);
		// if (fee != null) {
		// List<CoBaseModel> nlist = new ArrayList<CoBaseModel>();
		// for (CoBaseModel cm : list) {
		// BigDecimal totalp = new BigDecimal(myformat.format(cm
		// .getAmount().getCfmb_TotalPaidIn()));
		// BigDecimal totafee = new BigDecimal(myformat.format(fee));
		// if (totalp.compareTo(totafee) == 0) {
		// nlist.add(cm);
		// }
		// }
		// list = nlist;
		// }
		//
		// }else{
		// Messagebox.show("未能修改开票状态！", "操作提示", Messagebox.OK,
		// Messagebox.ERROR);
		// return;
		// }
	}

	public List<CoBaseModel> getList() {
		return list;
	}

	public void setList(List<CoBaseModel> list) {
		this.list = list;
	}

	public GatherInfoBll getBll() {
		return bll;
	}

	public void setBll(GatherInfoBll bll) {
		this.bll = bll;
	}

	public Date getStartDate() {
		return startDate;
	}

	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}

	public Date getEndDate() {
		return endDate;
	}

	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}

	public String getCid() {
		return cid;
	}

	public void setCid(String cid) {
		this.cid = cid;
	}

	public String getCompany() {
		return company;
	}

	public void setCompany(String company) {
		this.company = company;
	}

	public String getClient() {
		return client;
	}

	public void setClient(String client) {
		this.client = client;
	}

	public Date getOwnmonth() {
		return ownmonth;
	}

	public void setOwnmonth(Date ownmonth) {
		this.ownmonth = ownmonth;
	}

	public BigDecimal getTotal() {
		return total;
	}

	public void setTotal(BigDecimal total) {
		this.total = total;
	}

	public BigDecimal getFee() {
		return fee;
	}

	public void setFee(BigDecimal fee) {
		this.fee = fee;
	}

	public String getInyongyou() {
		return inyongyou;
	}

	public void setInyongyou(String inyongyou) {
		this.inyongyou = inyongyou;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	private String dateToStr(Date date) {
		String datestr = "";
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		if (date != null) {
			try {
				datestr = format.format(date);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return datestr;
	}

	private String datesToStr(Date date) {
		String datestr = "";
		SimpleDateFormat format = new SimpleDateFormat("yyyyMM");
		if (date != null) {
			try {
				datestr = format.format(date);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return datestr;
	}

	public String getFpfrist() {
		return fpfrist;
	}

	public void setFpfrist(String fpfrist) {
		this.fpfrist = fpfrist;
	}

	public String getUfclass() {
		return ufclass;
	}

	public void setUfclass(String ufclass) {
		this.ufclass = ufclass;
	}

	public String getCfss_type() {
		return cfss_type;
	}

	public void setCfss_type(String cfss_type) {
		this.cfss_type = cfss_type;
	}

	public String getCfss_fpstate() {
		return cfss_fpstate;
	}

	public void setCfss_fpstate(String cfss_fpstate) {
		this.cfss_fpstate = cfss_fpstate;
	}

}
