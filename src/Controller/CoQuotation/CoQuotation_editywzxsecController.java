package Controller.CoQuotation;

import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.zkoss.bind.annotation.BindingParam;
import org.zkoss.bind.annotation.Command;
import org.zkoss.bind.annotation.NotifyChange;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.event.EventListener;
import org.zkoss.zul.Checkbox;
import org.zkoss.zul.Combobox;
import org.zkoss.zul.Grid;
import org.zkoss.zul.ListModelList;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Radiogroup;
import org.zkoss.zul.Window;
import org.zkoss.zul.Messagebox.ClickEvent;

import bll.CoCompact.CoCompact_OperateBll;
import bll.CoProduct.cpd_addBll;
import bll.CoQuotation.CoQuotation_AddBll;

import Model.CoCompactModel;
import Model.CoOfferListModel;
import Model.CoOfferModel;
import Model.CoPFeeclassModel;
import Model.CoProductModel;
import Util.ObjectAttributeUtil;

public class CoQuotation_editywzxsecController {

	private List<CoOfferListModel> coltList = new ListModelList<CoOfferListModel>();
	private List<CoPFeeclassModel> cpfList = new ListModelList<CoPFeeclassModel>();
	private CoOfferListModel clModel = new CoOfferListModel();
	private CoCompactModel coModel = new CoCompactModel();

	/* 收费单位 */
	private CoPFeeclassModel cpfcModel = new CoPFeeclassModel();

	/* 享受方式 */
	private List<CoOfferListModel> standardList = new ListModelList<CoOfferListModel>();
	private boolean standardDisable = true;

	/* 收费方式 */
	private List<String> feetypeList = new ListModelList<String>();
	private boolean feetypeDisable = true;
	private String feetype = "";

	/* 商保份数 */
	private List<Integer> amountList = new ListModelList<Integer>();
	private boolean amountDisable = true;

	/* 控制详情显示 */
	private boolean fwvis = false;// 服务费详情
	private boolean flvis = false;// 福利产品详情
	private boolean fwinvis = false;// 人事基础服务费小项详情
	private boolean xstjvis = false;// 享受条件
	private boolean xstjvis1 = false;
	private boolean xstjvis2 = false;
	private boolean feevis = true;// 收费单位及收费金额
	private boolean rspkvis = false;// 档案付款方式及开票方式
	private boolean hjpkvis = false;// 户口付款方式及开票方式
	private boolean flpkvis = false;// 福利付款方式及开票方式

	// 享受条件可见
	private Boolean divvi1 = false, divvi2 = false, divvi3 = false,
			divvi4 = false, divvi5 = false, divvi6 = false;

	/* 收费金额 */
	private boolean feeReadonly = false;
	private String feestyle = "";

	// 接收页面传值
	int coli_coof_id;
	Integer cola_id;
	Integer cid;
	Integer coco_id;
	String cola_company;
	// 获取报价单Model
	private CoOfferModel cfModel = (CoOfferModel) Executions.getCurrent()
			.getArg().get("cfModel");

	// 初始化
	public CoQuotation_editywzxsecController() throws Exception {

		// 报价单ID
		coli_coof_id = Integer.parseInt(Executions.getCurrent().getArg()
				.get("coofid").toString());
		try {
			// 潜在客户
			cola_id = Integer.parseInt(Executions.getCurrent().getArg()
					.get("colaid").toString());

			cola_company = Executions.getCurrent().getArg().get("colacompany")
					.toString();
		} catch (Exception e) {
			// TODO: handle exception

		}

		try {
			// 公司报价
			cid = Integer.parseInt(Executions.getCurrent().getArg().get("cid")
					.toString());
			coco_id = Integer.parseInt(Executions.getCurrent().getArg()
					.get("coco_id").toString());
			CoQuotation_AddBll bll = new CoQuotation_AddBll();
			List<CoCompactModel> list = bll.getcompact(coco_id);
			if (list.size() > 0) {
				coModel = list.get(0);
			}

		} catch (Exception e) {
			// TODO: handle exception
		}

		try {
			CoQuotation_AddBll bll = new CoQuotation_AddBll();
			// 报价单产品明细
			setColtList(bll.getCoOfferListTemList(coli_coof_id));
			CalcSum();
		} catch (Exception e) {
			Messagebox.show("页面加载失败,请联系IT部门!", "ERROR", Messagebox.OK,
					Messagebox.ERROR);
			e.printStackTrace();
		}
	}

	@Command("gridSelect")
	@NotifyChange({ "clModel", "fwvis", "flvis", "fwinvis", "standardList",
			"standardDisable", "feetypeList", "feetypeDisable", "amountList",
			"amountDisable", "cpfList", "feevis", "divvi1", "divvi2", "divvi3",
			"divvi4", "divvi5", "divvi6", "int11", "int12", "int21", "int22",
			"int23", "int31", "int32", "int33", "int41", "int42", "int43",
			"int44", "int51", "int52", "int53", "int54", "int55", "int56",
			"int57", "int58", "xstjvis","xstjvis1","xstjvis2", "flpkvis", "rspkvis", "hjpkvis",
			"feeReadonly", "feetype" })
	public void gridSelect(@BindingParam("info") CoOfferListModel info,
			@BindingParam("ifselect") Radiogroup ifselect,
			@BindingParam("emjoy") Combobox emjoy) {
		clModel = info;
		feestyle = "";
		selectchange();
		radioisselect(ifselect, emjoy);
		try {
			cpfcchange();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Command("openwin")
	@NotifyChange({ "fwvis", "flvis", "fwinvis", "standardList",
			"standardDisable", "feetypeList", "feetypeDisable", "amountList",
			"amountDisable", "cpfList", "coltList", "clModel" })
	public void openwin(@BindingParam("url") String url,
			@BindingParam("each") CoOfferListModel m) {
		Map<String, Object> map = new HashMap<>();
		map.put("coli_id", m.getColi_id());

		Window win = (Window) Executions.createComponents(url, null, map);
		win.doModal();

		try {
			CoQuotation_AddBll bll = new CoQuotation_AddBll();
			setColtList(bll.getCoOfferListTemList(coli_coof_id));
			clModel = coltList.get(0);
			selectchange();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	// 合并/拆分
	@SuppressWarnings("unchecked")
	@Command("span")
	@NotifyChange({ "coltList", "fwinvis" })
	public void span(@BindingParam("gd") Grid gd) {
		Integer group_count = 0;
		Integer group_id = 0;
		String cpfc_name = "";
		BigDecimal fee = new BigDecimal(0).setScale(2);
		Integer old_group_id = 0;
		Integer ckbcount = 0;

		for (int i = 0; i < gd.getRows().getChildren().size(); i++) {
			try {
				// 判断是否勾选
				if (((Checkbox) gd.getCell(i, 0).getChildren().get(0))
						.isChecked()) {
					CoOfferListModel m = (CoOfferListModel) gd.getModel()
							.getElementAt(i);
					// 判断是否为单独的项目
					if (m.getColi_group_count() >= 1) {
						// 获取收费单位
						if (cpfc_name.isEmpty()) {
							cpfc_name = m.getColi_cpfc_name();
						}
						// 获取分组id
						if (group_id == 0) {
							group_id = m.getColi_id();
						}
						// 获取合并总数
						if (m.getColi_cpfc_name().equals(cpfc_name)) {
							group_count += m.getColi_group_count();
							fee = fee.add(m.getColi_fee());
						}
					}
					if (m.getColi_group_count() > 1) {
						old_group_id = m.getColi_group_id();
					}
					ckbcount++;
				}
			} catch (Exception e) {
				continue;
			}
		}

		// 合并
		if (ckbcount > 1) {
			for (int i = 0; i < gd.getRows().getChildren().size(); i++) {
				try {
					CoOfferListModel m = (CoOfferListModel) gd.getModel()
							.getElementAt(i);

					if (m.getColi_group_id() == old_group_id
							&& m.getColi_group_count() == 0) {
						m.setColi_group_id(group_id);
					}
					// 判断是否勾选
					if (((Checkbox) gd.getCell(i, 0).getChildren().get(0))
							.isChecked()) {
						if (m.getColi_cpfc_name().equals(cpfc_name)) {
							m.setColi_group_id(group_id);
							// 判断是否为合并的第一项
							if (m.getColi_id() == group_id) {
								m.setColi_group_count(group_count);
								m.setColi_fee(fee);
							} else {
								m.setColi_group_count(0);
								m.setColi_fee(new BigDecimal(0));
							}
						}
					}
				} catch (Exception e) {
					continue;
				}
			}
			// 排序
			List<CoOfferListModel> infoList = (List<CoOfferListModel>) gd
					.getModel();
			for (int i = 0; i < infoList.size() - 1; i++) {
				for (int j = 0; j < infoList.size() - 1 - i; j++) {
					if (infoList.get(j).getColi_copr_id() != 0
							&& infoList.get(j + 1).getColi_copr_id() != 0) {
						if (infoList.get(j + 1).getColi_group_id() < infoList
								.get(j).getColi_group_id()) {
							CoOfferListModel m = infoList.get(j);
							infoList.set(j, infoList.get(j + 1));
							infoList.set(j + 1, m);
						}
					}
				}
			}
			fwinvis = false;

		}
		// 拆分
		else if (ckbcount == 1) {
			for (int i = 0; i < gd.getRows().getChildren().size(); i++) {
				try {
					CoOfferListModel m = (CoOfferListModel) gd.getModel()
							.getElementAt(i);
					// 判断是否为第一项
					if (m.getColi_id() == group_id) {
						// 循环遍历合并的所有项，并拆分
						for (int j = i; j < i + group_count; j++) {
							m = (CoOfferListModel) gd.getModel()
									.getElementAt(j);
							m.setColi_group_id(m.getColi_id());
							m.setColi_group_count(1);
						}
					}
				} catch (Exception e) {
					continue;
				}
			}// 排序
			List<CoOfferListModel> infoList = (List<CoOfferListModel>) gd
					.getModel();
			for (int i = 0; i < infoList.size() - 1; i++) {
				for (int j = 0; j < infoList.size() - 1 - i; j++) {
					if (infoList.get(j).getColi_copr_id() != 0
							&& infoList.get(j + 1).getColi_copr_id() != 0) {
						if (infoList.get(j + 1).getColi_group_id() < infoList
								.get(j).getColi_group_id()) {
							CoOfferListModel m = infoList.get(j);
							infoList.set(j, infoList.get(j + 1));
							infoList.set(j + 1, m);
						}
					}
				}
			}

		}
	}

	// 全选
	@Command("allcheck")
	public void allcheck(@BindingParam("grid") Grid gd,
			@BindingParam("allcheck") boolean allcheck) {
		for (int i = 0; i < gd.getRows().getChildren().size(); i++) {
			try {
				Checkbox ckb = (Checkbox) gd.getCell(i, 0).getChildren().get(0);
				ckb.setChecked(allcheck);
			} catch (Exception e) {
				continue;
			}
		}
	}

	@Command
	@NotifyChange("clModel")
	public void loaddata(@BindingParam("a") Combobox cb) {
		if (cb.getValue() != null) {
			clModel.setColi_feetype(cb.getValue());

		}

	}

	// 享受条件的可见
	@Command
	@NotifyChange({ "clModel", "divvi1", "divvi2", "divvi3", "divvi4",
			"divvi5", "divvi6", "int11", "int12", "int21", "int22", "int23",
			"int31", "int32", "int33", "int41", "int42", "int43", "int44",
			"int51", "int52", "int53", "int54", "int55", "int56", "int57",
			"int58" })
	public void divchange(@BindingParam("ifselect") Radiogroup ifselect,
			@BindingParam("emjoy") Combobox emjoy) {
		if (emjoy.getValue() != null && !emjoy.getValue().equals("")
				|| emjoy.getValue() != "") {

			if (emjoy.getSelectedItem().getValue().equals("2")
					|| emjoy.getSelectedItem().getValue() == "2") {
				if (ifselect.getSelectedItem() != null) {
					if (ifselect.getSelectedItem().getValue().equals("1")
							|| ifselect.getSelectedItem().getValue() == "1") {
						clModel.getCoolmodel().setColm_selectid(1);
						divvi1 = true;
						divvi2 = false;
						divvi3 = false;
						divvi4 = false;
						divvi5 = false;
						divvi6 = false;
					} else if (ifselect.getSelectedItem().getValue()
							.equals("2")
							|| ifselect.getSelectedItem().getValue() == "2") {
						divvi1 = false;
						divvi2 = true;
						divvi3 = false;
						divvi4 = false;
						divvi5 = false;
						divvi6 = false;
						clModel.getCoolmodel().setColm_selectid(2);
					} else if (ifselect.getSelectedItem().getValue()
							.equals("3")
							|| ifselect.getSelectedItem().getValue() == "3") {
						divvi1 = false;
						divvi2 = false;
						divvi3 = true;
						divvi4 = false;
						divvi5 = false;
						divvi6 = false;
						clModel.getCoolmodel().setColm_selectid(3);
					} else if (ifselect.getSelectedItem().getValue()
							.equals("4")
							|| ifselect.getSelectedItem().getValue() == "4") {
						divvi1 = false;
						divvi2 = false;
						divvi3 = false;
						divvi4 = true;
						divvi5 = false;
						divvi6 = false;
						clModel.getCoolmodel().setColm_selectid(4);
					} else if (ifselect.getSelectedItem().getValue()
							.equals("5")
							|| ifselect.getSelectedItem().getValue() == "5") {
						divvi1 = false;
						divvi2 = false;
						divvi3 = false;
						divvi4 = false;
						divvi5 = true;
						divvi6 = true;
						clModel.getCoolmodel().setColm_selectid(5);
					}
				}

			} else {
				divvi1 = false;
				divvi2 = false;
				divvi3 = false;
				divvi4 = false;
				divvi5 = false;
				divvi6 = false;
				clModel.getCoolmodel().setColm_selectid(null);
			}
		}
		if (ifselect.getSelectedItem() != null) {
			clModel.getCoolmodel()
					.setColm_selectid(
							Integer.parseInt(ifselect.getSelectedItem()
									.getValue() + ""));
		}
	}

	// 通过各种条件判断应该显示哪个详情grid
	@Command("selectchange")
	@NotifyChange({ "fwvis", "flvis", "fwinvis", "standardList",
			"standardDisable", "feetypeList", "feetypeDisable", "amountList",
			"amountDisable", "cpfList", "feevis", "xstjvis", "xstjvis1",
			"xstjvis2", "flpkvis", "rspkvis", "hjpkvis", "feetype", "clModel",
			"cpfcModel" })
	public void selectchange() {
		if (clModel.getColi_isfwf() != null && clModel.getColi_isfwf() == 1) {
			try {
				setCpfList(new cpd_addBll().geteclass1());
				if (cpfList.size() > 0) {
					cpfcModel = cpfList.get(0);
				}

			} catch (Exception e) {
				Messagebox.show("收费单位获取失败,请联系IT部门!", "ERROR", Messagebox.OK,
						Messagebox.ERROR);
				e.printStackTrace();
			}
			setFwvis(true);
			setFlvis(false);
			setFwinvis(false);
			setFlpkvis(false);
			setRspkvis(false);
			setHjpkvis(false);
		} else {
			try {
				setCpfList(new CoQuotation_AddBll().getFeeClassList(clModel
						.getColi_copr_id()));
				if (cpfList.size() > 0) {
					cpfcModel = cpfList.get(0);
				}
			} catch (Exception e) {
				Messagebox.show("收费单位获取失败,请联系IT部门!", "ERROR", Messagebox.OK,
						Messagebox.ERROR);
				e.printStackTrace();
			}
			if (clModel.getCopr_type().equals("服务产品")
					&& (clModel.getColi_name() != null
							&& !clModel.getColi_name().equals("服档费") && !clModel
							.getColi_name().equals("综合管理费") && !clModel
							.getColi_name().equals("保管服务费"))) {
				setFwinvis(true);
				setFwvis(false);
				setFlvis(false);
				setFlpkvis(false);
				setRspkvis(false);
				setHjpkvis(false);

				if (clModel.getColi_group_count() == 0) {
					feevis = false;
				} else {
					feevis = true;
				}
			} else {
				initfl();
				setFlvis(true);
				setFwvis(false);
				setFwinvis(false);

				// 享受条件是否显示
				if (clModel.getColi_pclass().equals("员工福利")) {
					setXstjvis(true);
					setFlpkvis(false);
					setRspkvis(false);
					setHjpkvis(false);
					if (!(clModel.getColi_flpaykind() != null && clModel
							.getColi_flpaykind().equals(""))) {
						clModel.setColi_flpaykind("公司按月支付");
					}
					try {
						setCpfList(new CoQuotation_AddBll().getFeeClassList(clModel
								.getColi_copr_id()));
					} catch (SQLException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					if (cpfList.size() > 0) {
						cpfcModel = cpfList.get(0);
					}
					if (clModel.getColi_standard() != null) {
						if (clModel.getColi_name().equals("春节礼品")
								|| clModel.getColi_name().equals("文化生活礼品（电影票）")
								|| clModel.getColi_name().equals("生日礼品")) {
							xstjvis1 = true;
							xstjvis2 = true;
						} else if (clModel.getColi_standard().equals("常规享受")) {
							xstjvis1 = true;
							xstjvis2 = false;
						} else {
							xstjvis1 = true;
							xstjvis2 = true;
						}
					} else {
						switch (clModel.getColi_name()) {
						case "三八节礼品":
						case "端午节礼品":
						case "六一节礼品":
						case "中秋礼品":
						case "夏日饮料":
						case "春游活动":
						case "秋游活动":
							xstjvis1 = true;
							xstjvis2 = false;
							break;
						default:
							xstjvis1 = true;
							xstjvis2 = true;
							break;
						}
					}
					

				} else if (clModel.getColi_pclass().equals("档案")
						|| (clModel.getColi_name() != null && (clModel
								.getColi_name().equals("服档费") || clModel
								.getColi_name().equals("综合管理费") || clModel
								.getColi_name().equals("保管服务费")))) {
					setXstjvis(false);
					setFlpkvis(false);
					setRspkvis(true);
					setHjpkvis(true);

				} else {
					setXstjvis(false);
					setFlpkvis(false);
					setRspkvis(false);
					setHjpkvis(false);
				}

				if (clModel.getColi_group_count() == 0) {
					feevis = false;
				} else {
					feevis = true;
				}
			}
		}
	}

	// 初始化福利产品详情
	public void initfl() {
		try {
			CoQuotation_AddBll bll = new CoQuotation_AddBll();

			/* 享受方式 */
			standardList = bll.getStandardList(clModel.getColi_copr_id());
			standardDisable = standardList.size() > 0 ? false : true;
			standardList.add(0, new CoOfferListModel());
			boolean b = false;
			if (standardList.size() > 0) {
				for (CoOfferListModel m : standardList) {
					if (m.getColi_standard() != null
							&& m.getColi_standard().equals("常规享受")) {
						clModel.setColi_standard("常规享受");
						b = true;
						break;
					}
				}
			}
			if (!b) {
				clModel.setColi_standard("");
			}
			/* 收费方式 */
			if (clModel.getColi_pclass().equals("档案")
					|| (clModel.getColi_name() != null && (clModel
							.getColi_name().equals("服档费") || clModel
							.getColi_name().equals("综合管理费") || clModel
							.getColi_name().equals("保管服务费")))) {
				feetypeDisable = false;
				feetypeList.clear();
				if (clModel.getColi_feetype() != null
						&& !clModel.getColi_feetype().equals("")) {
					feetype = clModel.getColi_feetype();
				} else {
					feetype = "";
				}

				feetypeList.add("按实际收取");
				feetypeList.add("全部收取");

			} else if (clModel.getColi_pclass().equals("商业保险")) {
				feetypeDisable = false;
				feetypeList.clear();
				if (clModel.getColi_feetype() != null
						&& !clModel.getColi_feetype().equals("")) {
					feetype = clModel.getColi_feetype();
				} else {
					feetype = "";
				}
				feetypeList.add("按年收费，员工离职需核算是否退费");
				feetypeList.add("按月收费，员工离职需核算是否补费");
				feetypeList.add("按月收费，员工离职无需核算是否补费");
				clModel.setColi_standard(standardList.get(1).getColi_standard());
			} else {
				feetypeList.clear();
				feetype = "";
				feetypeDisable = true;
			}

			/* 商保份数 */
			if (clModel.getColi_pclass().equals("商业保险")) {
				amountDisable = false;
				amountList.clear();
				for (int i = 1; i <= 5; i++) {
					amountList.add(i);
				}
			} else {
				amountDisable = true;
				amountList.clear();
			}
		} catch (Exception e) {
			Messagebox.show("加载" + clModel.getColi_name() + "的详细信息出错,请联系IT部门!",
					"ERROR", Messagebox.OK, Messagebox.ERROR);
			e.printStackTrace();
		}

	}

	// 改变收费方式
	@Command("feetypechange")
	@NotifyChange("clModel")
	public void feetypechange() {
		clModel.setColi_remark(feetype + ".");
		clModel.setColi_feetype(feetype);
	}

	// 改变收费单位
	@Command("cpfcchange")
	@NotifyChange({ "clModel", "feeReadonly" })
	public void cpfcchange() throws Exception {
		CoProductModel cpModel = new CoProductModel();
		CoQuotation_AddBll bll = new CoQuotation_AddBll();
		cpModel = bll.getFee(clModel.getColi_copr_id(), cpfcModel.getCpfc_id());

		try {
			if (cpModel != null && cpModel.getCpfc_id() != null
					&& !cpModel.getCpfc_id().equals(cpfcModel.getCpfc_id())) {
				clModel.setColi_fee(cpModel.getFee());
			} else {
				if (feestyle.equals("")) {
					feestyle = cpfcModel.getCpfc_name();
				} else {
					if (!feestyle.equals(cpfcModel.getCpfc_name())) {
						clModel.setColi_fee(new BigDecimal(0));
						feestyle = cpfcModel.getCpfc_name();
					}
				}
			}
			if (cpModel.getCpfr_lock() != null
					&& cpModel.getCpfr_lock().equals(1)) {
				feeReadonly = true;
			} else {
				feeReadonly = false;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 计算合计
	 * 
	 */
	@Command("CalcSum")
	@NotifyChange({ "coltList", "cfModel" })
	public void CalcSum() {
		try {
			cfModel.setCoof_sum(BigDecimal.ZERO);
			cfModel.setCoof_sumInfo(null);
			Map<String, BigDecimal> mapTotal = new HashMap<>();
			for (CoOfferListModel m : coltList) {
				m.setSum(BigDecimal.ZERO);
				m.setSumInfo(null);
				Map<String, BigDecimal> map = new HashMap<>();
				map.put(m.getColi_cpfc_name(), m.getColi_fee());

				for (CoOfferListModel m1 : m.getInfoList()) {
					m.setSum(m.getSum().add(m1.getColi_fee()));
					if (map.containsKey(m1.getColi_cpfc_name())) {
						BigDecimal fee = new BigDecimal(0);
						if (m1.getColi_amount() != null
								&& m1.getColi_amount() > 0) {
							fee = m1.getColi_fee().multiply(
									new BigDecimal(m1.getColi_amount()));
						} else {
							fee = m1.getColi_fee();
						}
						map.put(m1.getColi_cpfc_name(),
								map.get(m1.getColi_cpfc_name()).add(fee));
					} else {
						map.put(m1.getColi_cpfc_name(), m1.getColi_fee());
					}

				}

				for (Iterator<Entry<String, BigDecimal>> it = map.entrySet()
						.iterator(); it.hasNext();) {
					Entry<String, BigDecimal> en = it.next();

					m.setSumInfo(m.getSumInfo() == null ? en.getValue()
							+ en.getKey() : m.getSumInfo() + ";"
							+ en.getValue() + en.getKey());
					if (mapTotal.containsKey(en.getKey())) {
						mapTotal.put(en.getKey(), mapTotal.get(en.getKey())
								.add(en.getValue()));
					} else {
						mapTotal.put(en.getKey(), en.getValue());
					}

				}

			}

			for (Iterator<Entry<String, BigDecimal>> it = mapTotal.entrySet()
					.iterator(); it.hasNext();) {
				Entry<String, BigDecimal> en = it.next();

				cfModel.setCoof_sumInfo(cfModel.getCoof_sumInfo() == null ? (en
						.getValue() + en.getKey()) : (cfModel.getCoof_sumInfo()
						+ ";" + en.getValue() + en.getKey()));
				if (en.getKey().equals("元/月/人")) {
					cfModel.setCoof_sum(cfModel.getCoof_sum()
							.add(en.getValue()));
				}
			}

			AllDetailOpen(false);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 全部展开、全部折叠
	 * 
	 */
	@Command
	@NotifyChange({ "coltList" })
	public void AllDetailOpen(@BindingParam("isopen") Boolean isopen) {
		try {
			for (CoOfferListModel m : coltList) {
				m.setDetailOpen(isopen);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 点击detail展开时，将其他的detail折叠
	 * 
	 * @param m
	 */
	@Command
	@NotifyChange({ "coltList" })
	public void DetailOpenOne(@BindingParam("each") CoOfferListModel m) {
		try {
			if (m.getDetailOpen()) {
				for (CoOfferListModel m1 : coltList) {
					if (!m1.getColi_id().equals(m.getColi_id())) {
						m1.setDetailOpen(false);
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	// 上一步
	@Command("back")
	public void back(@BindingParam("win") Window win) {
		CoQuotation_AddBll bll = new CoQuotation_AddBll();
		try {
			int su = bll.CoOfferListUpdate(coltList, 0);
			if (su == 1) {
				List<CoProductModel> selectList = new ListModelList<CoProductModel>();
				for (int i = 0; i < coltList.size(); i++) {
					if (coltList.get(i).getInfoList().size() > 0) {
						List<CoOfferListModel> list = coltList.get(i)
								.getInfoList();
						for (int j = 0; j < list.size(); j++) {
							if (!list.get(j).getColi_copr_id().equals(0)) {
								CoProductModel cpModel = new CoProductModel();
								cpModel.setCopr_id(list.get(j)
										.getColi_copr_id());
								cpModel.setCopr_name(list.get(j).getColi_name());
								cpModel.setCity(list.get(j).getCity());
								selectList.add(cpModel);
							}
						}
					}
				}
				win.detach();

				Map<String, Object> map = new HashMap<String, Object>();
				map.put("coofid", coli_coof_id);
				map.put("colaid", cola_id);
				map.put("cid", cid);
				map.put("coco_id", coco_id);
				map.put("colacompany", cola_company);
				map.put("selectList", selectList);
				map.put("cfModel", cfModel);
				Window window = (Window) Executions.createComponents(
						"/CoQuotation/CoQuotation_editywzx.zul", null, map);
				window.doModal();
			}
		} catch (Exception e) {
			Messagebox.show("回到上一步出错,请联系IT部门!", "ERROR", Messagebox.OK,
					Messagebox.ERROR);
			e.printStackTrace();
		}

	}

	// 检查项目信息是否录入完整
	public boolean judgeItem() {
		boolean b = true;

		ObjectAttributeUtil oa = new ObjectAttributeUtil();
		for (CoOfferListModel cm : coltList) {
			for (CoOfferListModel m : cm.getInfoList()) {
				if (m.getColi_copr_id() != null && m.getColi_copr_id() > 0  && !m.getCopr_type().equals("服务产品")) {
					// 判断产品类型,享受方式
					switch (m.getColi_pclass()) {
					case "档案":
					case "户口":
					case "商业保险":
					case "体检":
					case "员工福利":
						if (m.getColi_standard() == null
								|| m.getColi_standard().equals("")) {
							Messagebox.show(
									m.getColi_pclass() + "-" + m.getColi_name()
											+ ",享受方式为空.", "ERROR",
									Messagebox.OK, Messagebox.ERROR);
							return false;
						}
						break;

					default:
						break;
					}
					// 判断产品类型,收费方式
					switch (m.getColi_pclass()) {

					case "商业保险":
						if (m.getColi_feetype() == null
								|| m.getColi_feetype().equals("")) {
							Messagebox.show(
									m.getColi_pclass() + "-" + m.getColi_name()
											+ ",收费方式为空.", "ERROR",
									Messagebox.OK, Messagebox.ERROR);
							return false;
						}
						if (m.getColi_amount() == null
								|| m.getColi_amount().equals("")) {
							Messagebox.show(
									m.getColi_pclass() + "-" + m.getColi_name()
											+ ",份数为空.", "ERROR", Messagebox.OK,
									Messagebox.ERROR);
							return false;
						}
						break;

					default:
						break;
					}

					// 判断产品名称,享受条件
					switch (m.getColi_name()) {
					case "综合管理费":
					case "保管服务费":
					case "服档费":
						if (m.getColi_feetype() == null
								|| m.getColi_feetype().equals("")) {
							Messagebox.show(
									m.getColi_pclass() + "-" + m.getColi_name()
											+ ",收费方式为空.", "ERROR",
									Messagebox.OK, Messagebox.ERROR);
							return false;
						}
						if (m.getColi_rspaykind() == null
								|| m.getColi_rspaykind().equals("")) {
							Messagebox.show(
									m.getColi_pclass() + "-" + m.getColi_name()
											+ ",档案费用付款方式为空.", "ERROR",
									Messagebox.OK, Messagebox.ERROR);
							return false;
						}
						if (m.getColi_standard() == null
								|| m.getColi_standard().equals("")) {
							Messagebox.show(
									m.getColi_pclass() + "-" + m.getColi_name()
											+ ",享受方式为空.", "ERROR",
									Messagebox.OK, Messagebox.ERROR);
							return false;
						}
						break;
					default:
						break;

					}

				}

			}

		}
		return b;
	}
	
	// 更新福利项目的享受条件时,重置相关字段
		@Command
		@NotifyChange({ "xstjvis1", "xstjvis2", "clModel" })
		public void updateStand() {
			if (clModel.getColi_pclass().equals("员工福利")) {
				clModel.getCoolmodel().setInt51(null);
				clModel.getCoolmodel().setInt52(null);
				clModel.getCoolmodel().setInt53(null);
				clModel.getCoolmodel().setInt54(null);
				clModel.getCoolmodel().setInt55(null);
				clModel.getCoolmodel().setInt56(null);
				clModel.getCoolmodel().setInt57(null);
				clModel.getCoolmodel().setInt58(null);
				if (clModel.getColi_standard() != null) {
					if (clModel.getColi_name().equals("春节礼品")
							|| clModel.getColi_name().equals("文化生活礼品（电影票）")
							|| clModel.getColi_name().equals("生日礼品")) {
						xstjvis1 = true;
						xstjvis2 = true;
					} else if (clModel.getColi_standard().equals("常规享受")) {
						xstjvis1 = true;
						xstjvis2 = false;
					} else {
						xstjvis1 = true;
						xstjvis2 = true;
					}
				} else {
					switch (clModel.getColi_name()) {
					case "三八节礼品":
					case "端午节礼品":
					case "六一节礼品":
					case "中秋礼品":
					case "夏日饮料":
					case "春游活动":
					case "秋游活动":
						xstjvis1 = true;
						xstjvis2 = false;
						break;
					default:
						xstjvis1 = true;
						xstjvis2 = true;
						break;
					}
				}
			}
		}

	// 提交
	@Command("submit")
	public void submit(@BindingParam("win") final Window win) {

		CalcSum();
		if (!judgeItem()) {
			return;
		}
		System.out.println("***");
		Messagebox.show("确认提交数据?", "操作提示", new Messagebox.Button[] {
				Messagebox.Button.OK, Messagebox.Button.CANCEL },
				Messagebox.QUESTION,
				new EventListener<Messagebox.ClickEvent>() {
					@Override
					public void onEvent(ClickEvent event) throws Exception {
						// TODO Auto-generated method stub

						if (Messagebox.Button.OK.equals(event.getButton())) {

							CoQuotation_AddBll bll = new CoQuotation_AddBll();
							CoCompact_OperateBll obll = new CoCompact_OperateBll();
							int su = bll.coOfferSubmit(coltList, cfModel);

							if (su == 1) {
								// bll.ClearCoOffer();
								if (coModel.getCid2() != null
										&& coModel.getCid2() > 0) {
									obll.startMission(coModel);
								}

								if (Messagebox.show("提交成功!", "INFORMATION",
										Messagebox.OK, Messagebox.INFORMATION) == Messagebox.OK) {
									win.detach();

								}
							} else {
								Messagebox.show("提交失败,请联系IT部门! 错误代码:" + su,
										"ERROR", Messagebox.OK,
										Messagebox.ERROR);
							}
						}
					}
				});

	}

	// 判断享受条件的单选
	private void radioisselect(Radiogroup ifselect, Combobox emjoy) {
		boolean iss = false;
		if (clModel.getCoolmodel() != null) {
			if (clModel.getCoolmodel().getColm_enjoytype() != null) {
				if (clModel.getCoolmodel().getColm_enjoytype().equals("电影券")
						|| clModel.getCoolmodel().getColm_enjoytype() == "电影券") {
					iss = true;
				}
			}
			if (clModel.getCoolmodel().getColm_selectid() != null) {
				if (clModel.getCoolmodel().getColm_selectid() == 1) {
					ifselect.getItems().get(0).setSelected(true);
					if (iss) {
						divvi1 = true;
						divvi2 = false;
						divvi3 = false;
						divvi4 = false;
						divvi5 = false;
						divvi6 = false;
					}
				} else if (clModel.getCoolmodel().getColm_selectid() == 2) {
					ifselect.getItems().get(1).setSelected(true);
					if (iss) {
						divvi1 = false;
						divvi2 = true;
						divvi3 = false;
						divvi4 = false;
						divvi5 = false;
						divvi6 = false;
					}
				} else if (clModel.getCoolmodel().getColm_selectid() == 3) {
					ifselect.getItems().get(2).setSelected(true);
					if (iss) {
						divvi1 = false;
						divvi2 = false;
						divvi3 = true;
						divvi4 = false;
						divvi5 = false;
						divvi6 = false;
					}
				} else if (clModel.getCoolmodel().getColm_selectid() == 4) {
					ifselect.getItems().get(3).setSelected(true);
					if (iss) {
						divvi1 = false;
						divvi2 = false;
						divvi3 = false;
						divvi4 = true;
						divvi5 = false;
						divvi6 = false;
					}
				} else if (clModel.getCoolmodel().getColm_selectid() == 5) {
					ifselect.getItems().get(4).setSelected(true);
					if (iss) {
						divvi1 = false;
						divvi2 = false;
						divvi3 = false;
						divvi4 = false;
						divvi5 = true;
						divvi6 = true;
					}
				}
			} else {
				ifselect.getItems().get(0).setSelected(false);
				ifselect.getItems().get(1).setSelected(false);
				ifselect.getItems().get(2).setSelected(false);
				ifselect.getItems().get(3).setSelected(false);
				ifselect.getItems().get(4).setSelected(false);
				divvi1 = false;
				divvi2 = false;
				divvi3 = false;
				divvi4 = false;
				divvi5 = false;
				divvi6 = false;
			}
		}
		if (iss == false) {
			divvi1 = false;
			divvi2 = false;
			divvi3 = false;
			divvi4 = false;
			divvi5 = false;
			divvi6 = false;
		}

	}

	@Command("close")
	public void close() throws Exception {
		CoQuotation_AddBll bll = new CoQuotation_AddBll();
		// bll.ClearCoOffer();
	}

	public List<CoOfferListModel> getColtList() {
		return coltList;
	}

	public void setColtList(List<CoOfferListModel> coltList) {
		this.coltList = coltList;
	}

	public CoOfferListModel getClModel() {
		return clModel;
	}

	public void setClModel(CoOfferListModel clModel) {
		this.clModel = clModel;
	}

	public List<CoPFeeclassModel> getCpfList() {
		return cpfList;
	}

	public void setCpfList(List<CoPFeeclassModel> cpfList) {
		this.cpfList = cpfList;
	}

	public boolean isFwvis() {
		return fwvis;
	}

	public void setFwvis(boolean fwvis) {
		this.fwvis = fwvis;
	}

	public boolean isFlvis() {
		return flvis;
	}

	public void setFlvis(boolean flvis) {
		this.flvis = flvis;
	}

	public boolean isFwinvis() {
		return fwinvis;
	}

	public void setFwinvis(boolean fwinvis) {
		this.fwinvis = fwinvis;
	}

	public List<CoOfferListModel> getStandardList() {
		return standardList;
	}

	public void setStandardList(List<CoOfferListModel> standardList) {
		this.standardList = standardList;
	}

	public List<String> getFeetypeList() {
		return feetypeList;
	}

	public void setFeetypeList(List<String> feetypeList) {
		this.feetypeList = feetypeList;
	}

	public List<Integer> getAmountList() {
		return amountList;
	}

	public void setAmountList(List<Integer> amountList) {
		this.amountList = amountList;
	}

	public boolean isStandardDisable() {
		return standardDisable;
	}

	public void setStandardDisable(boolean standardDisable) {
		this.standardDisable = standardDisable;
	}

	public boolean isFeetypeDisable() {
		return feetypeDisable;
	}

	public void setFeetypeDisable(boolean feetypeDisable) {
		this.feetypeDisable = feetypeDisable;
	}

	public boolean isAmountDisable() {
		return amountDisable;
	}

	public void setAmountDisable(boolean amountDisable) {
		this.amountDisable = amountDisable;
	}

	public String getFeetype() {
		return feetype;
	}

	public void setFeetype(String feetype) {
		this.feetype = feetype;
	}

	public CoPFeeclassModel getCpfcModel() {
		return cpfcModel;
	}

	public void setCpfcModel(CoPFeeclassModel cpfcModel) {
		this.cpfcModel = cpfcModel;
	}

	public boolean isFeeReadonly() {
		return feeReadonly;
	}

	public void setFeeReadonly(boolean feeReadonly) {
		this.feeReadonly = feeReadonly;
	}

	public boolean isFeevis() {
		return feevis;
	}

	public void setFeevis(boolean feevis) {
		this.feevis = feevis;
	}

	public Boolean getDivvi1() {
		return divvi1;
	}

	public void setDivvi1(Boolean divvi1) {
		this.divvi1 = divvi1;
	}

	public Boolean getDivvi2() {
		return divvi2;
	}

	public void setDivvi2(Boolean divvi2) {
		this.divvi2 = divvi2;
	}

	public Boolean getDivvi3() {
		return divvi3;
	}

	public void setDivvi3(Boolean divvi3) {
		this.divvi3 = divvi3;
	}

	public Boolean getDivvi4() {
		return divvi4;
	}

	public void setDivvi4(Boolean divvi4) {
		this.divvi4 = divvi4;
	}

	public Boolean getDivvi5() {
		return divvi5;
	}

	public void setDivvi5(Boolean divvi5) {
		this.divvi5 = divvi5;
	}

	public Boolean getDivvi6() {
		return divvi6;
	}

	public void setDivvi6(Boolean divvi6) {
		this.divvi6 = divvi6;
	}

	public final CoOfferModel getCfModel() {
		return cfModel;
	}

	public final void setCfModel(CoOfferModel cfModel) {
		this.cfModel = cfModel;
	}

	public boolean isXstjvis() {
		return xstjvis;
	}

	public void setXstjvis(boolean xstjvis) {
		this.xstjvis = xstjvis;
	}

	public boolean isRspkvis() {
		return rspkvis;
	}

	public void setRspkvis(boolean rspkvis) {
		this.rspkvis = rspkvis;
	}

	public boolean isHjpkvis() {
		return hjpkvis;
	}

	public void setHjpkvis(boolean hjpkvis) {
		this.hjpkvis = hjpkvis;
	}

	public boolean isFlpkvis() {
		return flpkvis;
	}

	public void setFlpkvis(boolean flpkvis) {
		this.flpkvis = flpkvis;
	}

	public boolean isXstjvis1() {
		return xstjvis1;
	}

	public void setXstjvis1(boolean xstjvis1) {
		this.xstjvis1 = xstjvis1;
	}

	public boolean isXstjvis2() {
		return xstjvis2;
	}

	public void setXstjvis2(boolean xstjvis2) {
		this.xstjvis2 = xstjvis2;
	}

}
