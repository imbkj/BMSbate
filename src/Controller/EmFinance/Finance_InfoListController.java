package Controller.EmFinance;

import java.io.File;
import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.zkoss.bind.annotation.BindingParam;
import org.zkoss.bind.annotation.Command;
import org.zkoss.bind.annotation.NotifyChange;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.event.EventListener;
import org.zkoss.zul.Cell;
import org.zkoss.zul.Checkbox;
import org.zkoss.zul.Filedownload;
import org.zkoss.zul.Grid;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Window;
import org.zkoss.zul.Messagebox.ClickEvent;

import service.ExcelService;

import bll.CoFinanceManage.EmFinanceWt_SelBll;
import bll.CoFinanceManage.cfma_OperateBll;
import bll.CoLatencyClient.CoServiceRequestSelectBll;
import bll.EmFinance.EmFinance_SelectBll;

import Model.CoFinanceMonthlyBillModel;
import Model.CoFinanceWtModel;
import Model.EmFinanceZYTModel;
import Util.UserInfo;
import Util.plyUtil;

public class Finance_InfoListController {
	private String city = "", setup = "", client = "", cid = "", company = "",
			confirm = "", xiao = "";
	private Integer ownmonth = 0;
	private EmFinance_SelectBll bll = new EmFinance_SelectBll();
	private List<EmFinanceZYTModel> list = new ArrayList<EmFinanceZYTModel>();
	private List<String> wtarealist = new ArrayList<String>();// 委托地区
	private List<String> setuplist = new ArrayList<String>();// 委托机构
	private List<String> loginlist = new ArrayList<String>();// 客服
	private CoServiceRequestSelectBll cbll = new CoServiceRequestSelectBll();
	private Integer openflag = 1;
	private String sqlstr = "";
	private String chae = "";
	private int com_num = 0;
	private BigDecimal com_fee = BigDecimal.ZERO;
	private BigDecimal zyt_fee = BigDecimal.ZERO;
	private String difference = "";
	private String str = "";
	public Finance_InfoListController() {
		if (city != null && !city.equals("") && ownmonth != null
				&& ownmonth > 0) {
			list = bll.getEmFinanceZYTn(ownmonth, city, "", "");
			list = Difference();
		}
		//loginlist = bll.getLoginlist("");
	}

	// 计算差额
	private List<EmFinanceZYTModel> Difference() {
		List<EmFinanceZYTModel> nlist = new ArrayList<EmFinanceZYTModel>();
		com_fee = BigDecimal.ZERO;
		zyt_fee = BigDecimal.ZERO;
		for (EmFinanceZYTModel m : list) {
			boolean flag = false;
			com_fee = com_fee.add(m.getEmfi_total());
			zyt_fee = zyt_fee.add(m.getTotal());
			m.setFifztotals(m.getEmfi_total().subtract(m.getTotal()));
			if (m.getFifztotals().compareTo(BigDecimal.ZERO) < 0) {
				m.setFifztotals(BigDecimal.ZERO.subtract(m.getFifztotals()));
			}
			if (confirm != null && !confirm.equals("")) {
				if (confirm.equals("未确认")) {
					if (m.getStateid() != null && m.getStateid().equals(0)) {
						flag = true;
					}
				} else if (confirm.equals("已确认")) {
					if (m.getStateid() != null && m.getStateid().equals(1)) {
						flag = true;
					}
				} else {
					if (m.getStateid() != null && m.getStateid().equals(-1)) {
						flag = true;
					}
				}
			} else {
				flag = true;
			}
			if (flag) {
				if (difference != null && !difference.equals("")) {
					if (difference.equals("有差额")) {
						BigDecimal firsttotal = BigDecimal.ZERO;
						if (m.getFifztotals().compareTo(firsttotal) > 0) {
							flag = true;
						}
						else{
							flag = false;
						}
					} else {
						BigDecimal firsttotal = BigDecimal.ZERO;
						if (m.getFifztotals().compareTo(firsttotal) <= 0) {
							flag = true;
						}else{
							flag = false;
						}
					}
				}
			}

			if (flag) {
				nlist.add(m);
			}
		}
		
		com_num = nlist.size();
		return nlist;
	}

	// 弹出详细页面
	@Command
	public void detail(@BindingParam("win") Window win,
			@BindingParam("model") EmFinanceZYTModel model) {
		// CoFinanceWtModel m = bll.getModel(model.getName(),
		// model.getOwnmonth(),
		// model.getCid());
		// if (m.getEfWtList().size() > 0) {
		if (openflag == 1) {
			Map map = new HashMap<>();
			map.put("model", model);
			Window window = (Window) Executions.createComponents(
					"Finance_EmbaseList.zul", null, map);
			openflag = 2;
			window.doModal();
			openflag = 1;
		}
		/*
		 * } else { Messagebox.show("该公司没有员工台帐", "提示", Messagebox.OK,
		 * Messagebox.ERROR); }
		 */
	}

	// 查询
	@Command
	@NotifyChange({ "list", "com_num", "com_fee", "zyt_fee" })
	public void search(@BindingParam("ownmonth") Date ownmonth) {
		
		EmFinanceZYTModel model = new EmFinanceZYTModel();
		str = "";
		if (city != null && !city.equals("") && datetostr(ownmonth) != null
				&& !datetostr(ownmonth).equals("")) {
			model.setScity(city);
			
			if (client != null && !client.equals("")) {
				model.setCoba_client(client);
				str = str + " and coba_client='" + client + "'";
			}
			if (cid != null && !cid.equals("")) {
				str = str + " and coba.cid='" + cid + "'";
			}
			if (company != null && !company.equals("")) {
				str = str + " and emfz_company='" + company + "'";
			}

			if (xiao != null && !xiao.equals("")) {

			}

			this.ownmonth = Integer.parseInt(datetostr(ownmonth));
			model.setOwnmonth(this.ownmonth);
			
			list = bll.getEmFinanceZYTn(Integer.parseInt(datetostr(ownmonth)),
					city, setup, str);
			
			list = Difference();
			
		} else {
			Messagebox.show("必须选择所属月份和委托地区", "提示", Messagebox.OK,
					Messagebox.ERROR);
		}
	}

	// 根据填写的所属月份获取委托地区
	@Command
	@NotifyChange("wtarealist")
	public void changearea(@BindingParam("ownmonth") Date ownmonth) {
		if (datetostr(ownmonth) != null && !datetostr(ownmonth).equals("")) {
			wtarealist = bll.getWtAreaLists(Integer
					.parseInt(datetostr(ownmonth)));
		}
	}

	// 根据填写的所属月份获取委托地区
	@Command
	@NotifyChange({ "setuplist", "loginlist", "setup" })
	public void changesetup(@BindingParam("ownmonth") Date ownmonth) {
		if (ownmonth != null && !ownmonth.equals("")
				&& city != null && !city.equals("")) {
			setuplist = bll.getSetupLists(city);
			setup = "";
		}
		if (city != null && !city.equals("")) {
			loginlist = bll.getLoginlist(city);
		}
	}

	// 弹出导入台帐数据
	@Command
	public void Import() {
		Window window = (Window) Executions.createComponents("ImportFile.zul",
				null, null);
		window.doModal();
	}

	// 同步台帐
	@Command
	@NotifyChange({ "list", "com_num", "com_fee", "zyt_fee" })
	public void synFinance(@BindingParam("model") EmFinanceZYTModel model) {
		// 查询是否已生成账单，如果有账单而且账单未确认则先删除账单
		int[] ints = bll.getCocoID(" and coab_city='" + model.getName()
				+ "' and cid=" + model.getCid());
		Integer y = 0;
		EmFinanceWt_SelBll sbll = new EmFinanceWt_SelBll();
		cfma_OperateBll obll = new cfma_OperateBll();
		for (int i = 0; i < ints.length; i++) {
			String billnum = sbll.getBillNumberByCp(model.getOwnmonth(),
					ints[i]);
			if (!billnum.equals("0")) {
				String[] ss = obll.delBill(billnum);
				if (ss[0] == "1") {
					y = y + 1;
				}
			}
		}

		int k = 0;
		// obll.synchronousFinance(model.getCid(), model.getOwnmonth());
		// if (bll.ifHasBill(model.getCid(), ownmonth)) {
		// k = 1;
		// Messagebox.show("该公司已有账单，请先撤销账单", "提示", Messagebox.OK,
		// Messagebox.ERROR);
		// }

		for (int i = 0; i < ints.length; i++) {
			String[] str = obll.createBillByCp(model.getCid(),
					model.getOwnmonth(), ints[i], UserInfo.getUsername(), "",
					false);
			if (str[0] == "1") {
				y = 1;
			} else if (str[0] == "0") {
				k = k + 1;
			}
		}
		Messagebox.show("台帐已同步", "提示", Messagebox.OK, Messagebox.INFORMATION);
//		list = bll.getEmFinanceZYTn(ownmonth, city, setup, "");
//		list = Difference();
		search(strtodate(ownmonth.toString()));
	}

	// 生成账单
	@Command
	@NotifyChange({ "list", "com_num", "com_fee", "zyt_fee" })
	public void createbill(@BindingParam("model") EmFinanceZYTModel model) {
		cfma_OperateBll obll = new cfma_OperateBll();
		// 同步台账
		obll.synchronousFinance(model.getCid(), model.getOwnmonth());
		/*
		 * if (bll.ifHasBill(model.getCid(), ownmonth)) { k = 1;
		 * Messagebox.show("该公司已有账单，请先撤销账单", "提示", Messagebox.OK,
		 * Messagebox.ERROR); }
		 */
		// cfma_OperateBll obll = new cfma_OperateBll();
		int[] ints = bll.getCocoID(" and coab_city='" + model.getName()
				+ "' and cid=" + model.getCid());
		Integer y = 0, k = 0;
		long startTime = System.currentTimeMillis();
		for (int i = 0; i < ints.length; i++) {
			String[] str = obll.createBillByCp(model.getCid(),
					model.getOwnmonth(), ints[i], UserInfo.getUsername(), "",
					false);
			if (str[0] == "1") {
				y = 1;
			} else if (str[0] == "0") {
				k = k + 1;
			}
		}

		if (y > 0) {
			Messagebox.show("生成账单成功", "提示", Messagebox.OK,
					Messagebox.INFORMATION);
			list = bll.getEmFinanceZYTn(ownmonth, city, setup, str);
			list = Difference();
		} else if (k == ints.length) {
			Messagebox.show("未找到该合同的台账数据", "提示", Messagebox.OK,
					Messagebox.ERROR);
		} else {
			Messagebox.show("生成账单失败", "提示", Messagebox.OK, Messagebox.ERROR);
		}
	}

	// 确认台帐（确认账单）
	@Command
	@NotifyChange({ "list", "com_num", "com_fee", "zyt_fee" })
	public void confirmbill(@BindingParam("model") EmFinanceZYTModel model) {
		cfma_OperateBll obll = new cfma_OperateBll();
		int[] ints = bll.getCocoID(" and coab_city='" + model.getName()
				+ "' and cid=" + model.getCid());
		Integer y = 0;
		long startTime = System.currentTimeMillis();
		EmFinanceWt_SelBll sbll = new EmFinanceWt_SelBll();
		for (int i = 0; i < ints.length; i++) {
			String billnum = sbll.getBillNumberByCp(model.getOwnmonth(),
					ints[i]);
			if (!billnum.equals("0")) {
				y = obll.confirmBill(billnum, 1, UserInfo.getUsername());
			}
		}
		long endTime = System.currentTimeMillis(); // 获取结束时间
		long currenttime = endTime - startTime;
		if (currenttime <= 500 && currenttime >= 0) {
			try {
				Thread.sleep(500 - currenttime);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		if (y > 0) {
			Messagebox
					.show("确认成功", "提示", Messagebox.OK, Messagebox.INFORMATION);
			list = bll.getEmFinanceZYTn(ownmonth, city, setup, str);
			list = Difference();
		} else {
			Messagebox.show("确认失败", "提示", Messagebox.OK, Messagebox.ERROR);
		}
	}

	// 撤销账单
	@Command
	@NotifyChange({ "list", "com_num", "com_fee", "zyt_fee" })
	public void delbill(@BindingParam("model") final EmFinanceZYTModel model) {
		EventListener<ClickEvent> clickListener = new EventListener<Messagebox.ClickEvent>() {
			public void onEvent(ClickEvent event) throws Exception {
				if (Messagebox.Button.YES.equals(event.getButton())) {
					cfma_OperateBll obll = new cfma_OperateBll();
					int[] ints = bll.getCocoID(" and coab_city='"
							+ model.getName() + "' and cid=" + model.getCid());
					Integer y = 0;
					EmFinanceWt_SelBll sbll = new EmFinanceWt_SelBll();
					for (int i = 0; i < ints.length; i++) {
						String billnum = sbll.getBillNumberByCp(
								model.getOwnmonth(), ints[i]);
						if (!billnum.equals("0")) {
							String[] ss = obll.delBill(billnum);
							if (ss[0] == "1") {
								y = y + 1;
							}
						}
					}
					if (y > 0) {
						Messagebox.show("撤销成功", "提示", Messagebox.OK,
								Messagebox.INFORMATION);
						list = bll.getEmFinanceZYTn(ownmonth, city, setup, str);
						list = Difference();
					} else {
						Messagebox.show("撤销失败", "提示", Messagebox.OK,
								Messagebox.ERROR);
					}
				}
			}
		};
		Messagebox.show("是否确定撤销", "提示", new Messagebox.Button[] {
				Messagebox.Button.YES, Messagebox.Button.NO },
				Messagebox.QUESTION, clickListener);
	}

	@Command
	public void checkall(@BindingParam("ck") Checkbox ck,
			@BindingParam("gd") Grid gd) {
		for (int i = 0; i < gd.getPageSize(); i++) {
			if (gd.getCell(i, 12) != null) {
				Cell cell = (Cell) gd.getCell(i, 12);
				if (cell.getChildren() != null) {
					Checkbox cb = (Checkbox) cell.getChildren().get(0);
					cb.setChecked(ck.isChecked());
				}
			}
		}
	}

	private List<EmFinanceZYTModel> checklist(Grid gd) {
		List<EmFinanceZYTModel> selectlist = new ArrayList<EmFinanceZYTModel>();
		for (int i = 0; i < gd.getPageSize(); i++) {
			if (gd.getCell(i, 12) != null) {
				Cell cell = (Cell) gd.getCell(i, 12);
				if (cell.getChildren() != null) {
					Checkbox cb = (Checkbox) cell.getChildren().get(0);
					if (cb.isChecked()) {
						EmFinanceZYTModel model = cb.getValue();
						selectlist.add(model);
					}
				}
			}
		}
		return selectlist;
	}

	private void showMsg() {
		Messagebox.show("请选择数据", "提示", Messagebox.OK, Messagebox.ERROR);
	}

	// 批量同步台帐
	@Command
	@NotifyChange({ "list", "com_num", "com_fee", "zyt_fee" })
	public void batchsynFinance(@BindingParam("gd") Grid gd,
			@BindingParam("gdck") Checkbox gdck) {
		List<EmFinanceZYTModel> selectlist = checklist(gd);
		if (selectlist.size() > 0) {
			cfma_OperateBll obll = new cfma_OperateBll();
			for (EmFinanceZYTModel m : selectlist) {
				obll.synchronousFinance(m.getCid(), m.getOwnmonth());
			}
			Messagebox.show("台帐已同步", "提示", Messagebox.OK,
					Messagebox.INFORMATION);
			search(strtodate(ownmonth.toString()));
			//list = Difference();
			gdck.setChecked(false);
		} else {
			showMsg();
		}

	}

	// 批量生成台帐
	@Command
	@NotifyChange({ "list", "com_num", "com_fee", "zyt_fee" })
	public void batchcreatebill(@BindingParam("gd") Grid gd,
			@BindingParam("gdck") Checkbox gdck) {
		List<EmFinanceZYTModel> selectlist = checklist(gd);
		if (selectlist.size() > 0) {
			cfma_OperateBll obll = new cfma_OperateBll();
			EmFinanceWt_SelBll sbll = new EmFinanceWt_SelBll();
			int k = 0;
			for (EmFinanceZYTModel m : selectlist) {
				int[] ints = bll.getCocoID(" and coab_city='" + m.getName()
						+ "' and cid=" + m.getCid());
				for (int i = 0; i < ints.length; i++) {
					// 先删除账单
					CoFinanceMonthlyBillModel bm = sbll.getBillNumberByCpModel(
							m.getOwnmonth(), ints[i]);
					if (bm.getCfmb_number() != null
							&& !bm.getCfmb_number().equals("")
							&& bm.getCfmb_PersonnelConfirm() == 1) {
						continue;
					}
					if (bm.getCfmb_number() != null
							&& !bm.getCfmb_number().equals("")
							&& bm.getCfmb_PersonnelConfirm() == 0) {
						obll.delBill(bm.getCfmb_number());
					}

					// obll.synchronousFinance(0, m.getOwnmonth());
					// 生成账单
					String[] str = obll.createBillByCp(m.getCid(),
							m.getOwnmonth(), ints[i], UserInfo.getUsername(),
							"", false);
					if (str[0] == "1") {

					} else if (str[0] == "0") {
						k = k + 1;
					}
				}
			}
			Messagebox.show("生成账单成功", "提示", Messagebox.OK,
					Messagebox.INFORMATION);
//			list = bll.getEmFinanceZYTn(ownmonth, city, setup, "");
//			list = Difference();
			search(strtodate(ownmonth.toString()));
			gdck.setChecked(false);
		} else {
			showMsg();
		}

	}

	// 批量确认台帐
	@Command
	@NotifyChange({ "list", "com_num", "com_fee", "zyt_fee" })
	public void batchconfirmbill(@BindingParam("gd") Grid gd,
			@BindingParam("gdck") Checkbox gdck) {
		List<EmFinanceZYTModel> selectlist = checklist(gd);
		if (selectlist.size() > 0) {
			for (EmFinanceZYTModel m : selectlist) {
				cfma_OperateBll obll = new cfma_OperateBll();
				int[] ints = bll.getCocoID(" and coab_city='" + m.getName()
						+ "' and cid=" + m.getCid());
				Integer y = 0;
				EmFinanceWt_SelBll sbll = new EmFinanceWt_SelBll();
				for (int i = 0; i < ints.length; i++) {
					String billnum = sbll.getBillNumberByCp(m.getOwnmonth(),
							ints[i]);
					if (!billnum.equals("0")) {
						y = obll.confirmBill(billnum, 1, UserInfo.getUsername());
					}
				}
			}
			Messagebox
					.show("确认成功", "提示", Messagebox.OK, Messagebox.INFORMATION);
//			list = bll.getEmFinanceZYTn(ownmonth, city, setup, "");
//			list = Difference();
			search(strtodate(ownmonth.toString()));
			gdck.setChecked(false);
		} else {
			showMsg();
		}
	}

	// 批量撤销台帐
	@Command
	@NotifyChange({ "list", "com_num", "com_fee", "zyt_fee" })
	public void batchdelbill(@BindingParam("gd") final Grid gd,
			@BindingParam("gdck") final Checkbox gdck) {
		EventListener<ClickEvent> clickListener = new EventListener<Messagebox.ClickEvent>() {
			public void onEvent(ClickEvent event) throws Exception {
				List<EmFinanceZYTModel> selectlist = checklist(gd);
				if (selectlist.size() > 0) {
					for (EmFinanceZYTModel m : selectlist) {
						cfma_OperateBll obll = new cfma_OperateBll();
						int[] ints = bll.getCocoID(" and coab_city='"
								+ m.getName() + "' and cid=" + m.getCid());
						Integer y = 0;
						EmFinanceWt_SelBll sbll = new EmFinanceWt_SelBll();
						for (int i = 0; i < ints.length; i++) {
							String billnum = sbll.getBillNumberByCp(
									m.getOwnmonth(), ints[i]);
							if (!billnum.equals("0")) {
								String[] ss = obll.delBill(billnum);
								if (ss[0] == "1") {
									y = y + 1;
								}
							}
						}
					}
					Messagebox.show("撤销成功", "提示", Messagebox.OK,
							Messagebox.INFORMATION);
					list = bll.getEmFinanceZYTn(ownmonth, city, setup, str);
					list = Difference();
					gdck.setChecked(false);
				} else {
					showMsg();
				}
			}
		};
		Messagebox.show("是否确定取消", "提示", new Messagebox.Button[] {
				Messagebox.Button.YES, Messagebox.Button.NO },
				Messagebox.QUESTION, clickListener);
	}

	// 打开账单备注
	@Command
	public void addremark(@BindingParam("model") final EmFinanceZYTModel model) {
		cfma_OperateBll obll = new cfma_OperateBll();
		int[] ints = bll.getCocoID(" and coab_city='" + model.getName()
				+ "' and cid=" + model.getCid());
		Integer y = 0;
		String billno = "";
		EmFinanceWt_SelBll sbll = new EmFinanceWt_SelBll();
		for (int i = 0; i < ints.length; i++) {
			billno = sbll.getBillNumberByCp(model.getOwnmonth(), ints[i]);
			if (!billno.equals("0")) {
				break;
			}
		}
		if (billno == null || billno.equals("")) {
			Messagebox.show("还没有账单", "提示", Messagebox.OK, Messagebox.ERROR);
		} else {
			Map map = new HashMap<>();
			map.put("billno", billno);
			Window window = (Window) Executions.createComponents(
					"Finance_AddRemark.zul", null, map);
			window.doModal();
		}
	}

	// 打开台帐页面
	@Command
	public void lookbill(@BindingParam("model") final EmFinanceZYTModel model) {
		cfma_OperateBll obll = new cfma_OperateBll();
		int[] ints = bll.getCocoID(" and coab_city='" + model.getName()
				+ "' and cid=" + model.getCid());
		Integer y = 0;
		String billno = "";
		EmFinanceWt_SelBll sbll = new EmFinanceWt_SelBll();
		for (int i = 0; i < ints.length; i++) {
			billno = sbll.getBillNumberByCp(model.getOwnmonth(), ints[i]);
			if (!billno.equals("0")) {
				break;
			}
		}
		if (billno == null || billno.equals("")) {
			Messagebox.show("还没有账单", "提示", Messagebox.OK, Messagebox.ERROR);
		} else {
			Map map = new HashMap<>();
			map.put("billNo", billno);
			Window window = (Window) Executions.createComponents(
					"/CoFinanceManage/Cfma_MonthlyBillView.zul", null, map);
			window.doModal();
		}
	}

	// 手动到处数据：导出数据时必须传入所属月份和委托地区
	@Command
	public void outexcel() throws Exception {
		String wt_area = city;
		int wt_ownmonth = ownmonth;
		String wt_setup = "";
		EmFinance_SelectBll bll = new EmFinance_SelectBll();
		// 新根据委托地区查询委托机构
		List<String> setup_list = bll.getSetupLists(wt_area);
		// 循环根据委托机构查询出公司
		List<CoFinanceWtModel> cof_list = new ArrayList<CoFinanceWtModel>();
		for (String setup_name : setup_list) {
			if (setup_name != null && !setup_name.equals("")) {
				// 根据所属月份和委托地区查询出所有的公司
				List<EmFinanceZYTModel> com_list = bll.getEmFinanceZYTn(
						wt_ownmonth, wt_area, setup_name, "");
				com_list = DifferenceOut(com_list);
				// 根据所属月份和公司编号查询出每家公司的员工
				for (EmFinanceZYTModel zm : com_list) {
					CoFinanceWtModel model = bll.getModelOut(zm.getName(),
							zm.getOwnmonth(), zm.getCid());
					model.setCompany(zm.getEmfz_company());
					model.setCoba_client(zm.getCoba_client());
					model.setCid(zm.getCid());
					model.setCoab_name(setup_name);
					cof_list.add(model);
				}
			}
		}
		plyUtil ply = new plyUtil();
		String path = "/../../EmFinance/downfile/";// 导出保存路径
		// 创建当前日子
		Date date = new Date();
		// 格式化日期
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmssSSS");
		// 格式化日期(产生文件名)
		String filename = "智翼通差额" + sdf.format(date) + ".xls";// 定义导出文件名称
		// 获取绝对路径
		path = ply.getAbsolutePath(path, filename, this);
		// System.out.println(path);
		// 创建文件
		File file = new File(path);
		file.createNewFile();
		String sheetName = "智翼通差额";// Excel表格名
		// 定义表头
		String[] title = { "序号", "委托机构", "公司编号", "公司简称", "客服", "员工编号", "姓名",
				"身份证", "系统合计", "智翼通合计", "合计差额", "系统社保合计", "智翼通社保合计", "社保差额",
				"系统养老", "智翼通养老", "养老差额", "系统失业", "智翼通失业", "失业差额", "系统工伤",
				"智翼通工伤", "工伤差额", "系统生育", "智翼通生育", "生育差额", "系统医疗", "智翼通医疗",
				"医疗差额", "系统住房公积金", "智翼通住房公积金", "住房公积金差额", "智翼通其它费用", "智翼通服务费",
				"智翼通档案费", "系统非标准费用", "系统姓名", "系统身份证" };
		try {
			// 使用自己写的Excel导出实现类把数据写入Excel
			ExcelService exl = new outExcelImpl(file, sheetName, title,
					cof_list);
			exl.writeExcel();
		} catch (Exception e) {
			System.out.println(e.toString());
		}
		Filedownload.save(file, "xls");// 导出Excel
		// file.delete();
	}

	// 计算差额
	private List<EmFinanceZYTModel> DifferenceOut(
			List<EmFinanceZYTModel> intolist) {
		List<EmFinanceZYTModel> nlist = new ArrayList<EmFinanceZYTModel>();
		for (EmFinanceZYTModel m : intolist) {
			m.setFifztotals(m.getEmfi_total().subtract(m.getTotal()));
			if (m.getFifztotals().compareTo(BigDecimal.ZERO) < 0) {
				m.setFifztotals(BigDecimal.ZERO.subtract(m.getFifztotals()));
			}
			if (confirm != null && !confirm.equals("")) {
				if (confirm.equals("未确认")) {
					if (m.getStateid() != null && m.getStateid().equals(0)) {
						nlist.add(m);
					}
				} else if (confirm.equals("已确认")) {
					if (m.getStateid() != null && m.getStateid().equals(1)) {
						nlist.add(m);
					}
				} else {
					if (m.getStateid() != null && m.getStateid().equals(-1)) {
						nlist.add(m);
					}
				}
			} else {
				nlist.add(m);
			}
		}
		return nlist;
	}

	public List<EmFinanceZYTModel> getList() {
		return list;
	}

	public void setList(List<EmFinanceZYTModel> list) {
		this.list = list;
	}

	public List<String> getWtarealist() {
		return wtarealist;
	}

	public void setWtarealist(List<String> wtarealist) {
		this.wtarealist = wtarealist;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getSetup() {
		return setup;
	}

	public void setSetup(String setup) {
		this.setup = setup;
	}

	public List<String> getSetuplist() {
		return setuplist;
	}

	public void setSetuplist(List<String> setuplist) {
		this.setuplist = setuplist;
	}

	public List<String> getLoginlist() {
		return loginlist;
	}

	public void setLoginlist(List<String> loginlist) {
		this.loginlist = loginlist;
	}

	public String getClient() {
		return client;
	}

	public void setClient(String client) {
		this.client = client;
	}

	public CoServiceRequestSelectBll getCbll() {
		return cbll;
	}

	public void setCbll(CoServiceRequestSelectBll cbll) {
		this.cbll = cbll;
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

	public String getConfirm() {
		return confirm;
	}

	public void setConfirm(String confirm) {
		this.confirm = confirm;
	}

	public String getXiao() {
		return xiao;
	}

	public void setXiao(String xiao) {
		this.xiao = xiao;
	}

	public Integer getOwnmonth() {
		return ownmonth;
	}

	public void setOwnmonth(Integer ownmonth) {
		this.ownmonth = ownmonth;
	}

	public String getChae() {
		return chae;
	}

	public void setChae(String chae) {
		this.chae = chae;
	}

	public int getCom_num() {
		return com_num;
	}

	public void setCom_num(int com_num) {
		this.com_num = com_num;
	}

	public BigDecimal getCom_fee() {
		return com_fee;
	}

	public void setCom_fee(BigDecimal com_fee) {
		this.com_fee = com_fee;
	}

	public BigDecimal getZyt_fee() {
		return zyt_fee;
	}

	public void setZyt_fee(BigDecimal zyt_fee) {
		this.zyt_fee = zyt_fee;
	}

	public String getDifference() {
		return difference;
	}

	public void setDifference(String difference) {
		this.difference = difference;
	}

	// 日期转字符串
	private String datetostr(Date date) {
		String str = "";
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMM");
		if (date != null && !date.equals("")) {
			str = sdf.format(date);
		}
		return str;
	}
	
	// 字符串转为时间
	private Date strtodate(String date) {
		Date rdate=null;
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMM");
		if (date != null && !date.equals("")) {
			try {
				rdate = sdf.parse(date);
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return rdate;
	}

}
