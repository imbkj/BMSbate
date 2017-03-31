package Controller.CoQuotation;

import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.zkoss.bind.BindContext;
import org.zkoss.bind.annotation.BindingParam;
import org.zkoss.bind.annotation.Command;
import org.zkoss.bind.annotation.ContextParam;
import org.zkoss.bind.annotation.ContextType;
import org.zkoss.bind.annotation.NotifyChange;
import org.zkoss.util.media.Media;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.event.EventListener;
import org.zkoss.zk.ui.event.UploadEvent;
import org.zkoss.zul.Combobox;
import org.zkoss.zul.ListModel;
import org.zkoss.zul.ListModelList;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Radiogroup;
import org.zkoss.zul.SimpleListModel;
import org.zkoss.zul.Textbox;
import org.zkoss.zul.Window;
import org.zkoss.zul.Messagebox.ClickEvent;

import bll.CoCompact.Compact_DetailBll;
import bll.CoCompact.CoCompactSA.CoCompactSA_OperateBll;
import bll.CoProduct.cpd_ListBll;
import bll.CoProduct.cpd_addBll;
import bll.CoQuotation.CoQuotation_AddBll;

import Controller.EmSheBaocard.newExcelImpl;
import Model.CoAgencyBaseModel;
import Model.CoOLModeModel;
import Model.CoOfferListModel;
import Model.CoOfferModel;
import Model.CoPFeeclassModel;
import Model.CoPclassModel;
import Model.CoProductModel;
import Model.CopCompactModel;
import Model.PubProCityModel;
import Util.FileOperate;
import Util.ObjectAttributeUtil;
import Util.UserInfo;

public class CoQuotation_ItemAddController {
	private List<CoProductModel> notselectList = new ListModelList<>();
	private CoOfferListModel clModel = new CoOfferListModel();
	private CopCompactModel cpctModel = new CopCompactModel();
	private ListModelList<PubProCityModel> cityList = new ListModelList<>();
	private ListModel<PubProCityModel> cityListModel;
	private List<CoAgencyBaseModel> agencyList = new ListModelList<CoAgencyBaseModel>();
	private List<String> coprList = new ListModelList<>();
	private ListModelList<CoPclassModel> sclasslist = new ListModelList<>();
	private List<CoPFeeclassModel> cpfList = new ListModelList<CoPFeeclassModel>();
	private List<CopCompactModel> cpctList;
	private List<CoPclassModel> classList;
	private List<CoOfferModel> coofList = new ListModelList<>();
	private Compact_DetailBll cocoBll = new Compact_DetailBll();
	private Media media;
	private String filename;
	private CoCompactSA_OperateBll ccsaBll = new CoCompactSA_OperateBll();

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

	// 检索条件
	private String copr_type = "";// 产品类别
	private CoPclassModel cpcModel;// 产品类型
	private PubProCityModel ppcModel = new PubProCityModel();// 城市
	private CoAgencyBaseModel abModel = new CoAgencyBaseModel();// 委托机构

	private String str = "";
	/* 获取页面传值 */
	private String company = Executions.getCurrent().getArg().get("company")
			.toString();
	private Integer cocoId = Integer.valueOf(Executions.getCurrent().getArg()
			.get("coco_id").toString());
	private Window win;

	// 初始化数据
	public CoQuotation_ItemAddController() {
		clModel.setColi_coco_id(cocoId);
		CoQuotation_AddBll bll = new CoQuotation_AddBll();
		coofList = bll.coofferList(cocoId);
		setCpctModel(bll.getCompactByCocoid(cocoId));
		try {
			setCityList(bll.getCityList());
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		setCityListModel(new SimpleListModel<PubProCityModel>(getCityList()));
		setCpctList(new cpd_ListBll().getCopCompactList(""));
		try {
			setClassList(bll.getclass(""));
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		coprList.add("");
		coprList.add("服务产品");
		coprList.add("福利产品");
		for (CopCompactModel m : cpctList) {
			if (m.getCpct_name() != null && !m.getCpct_name().isEmpty()) {
				m.setCpct_name(m.getCpct_shortname() + "(" + m.getCpct_name()
						+ ")");
			} else {
				m.setCpct_name(m.getCpct_shortname());
			}
		}

		for (PubProCityModel m : cityList) {
			if (m.getName().equals("s深圳")) {
				setPpcModel(m);
				break;
			}
		}

		try {
			search();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Command
	public void winName(@BindingParam("a") Window w) {
		win = w;
	}

	// 检索产品
	@Command("search")
	@NotifyChange({ "notselectList", "agencyList" })
	public void search() throws Exception {
		try {
			str = "";
			CoQuotation_AddBll bll = new CoQuotation_AddBll();
			if (!copr_type.isEmpty()) {
				str += " and copr_type='" + copr_type + "'";
			}
			if (cpcModel != null) {
				if (cpcModel.getCopc_id() != null) {
					str += " and copr_copc_id=" + cpcModel.getCopc_id();
				}

			}
			if (cpctModel != null) {
				if (cpctModel.getCpct_id() != null) {
					str += " and cpcr_cpct_id=" + cpctModel.getCpct_id();
				}

			}
			if (ppcModel != null) {
				if (ppcModel.getId() != 0) {
					setAgencyList(new CoQuotation_AddBll()
							.getCoAgencyList(ppcModel.getId()));
					if (agencyList.size() > 0) {
						abModel = agencyList.get(0);
					} else {
						str += " and 1=0";
						agencyList.add(0, new CoAgencyBaseModel());
						abModel = agencyList.get(0);
					}
				}
			} else {
				str += " and 1=0";
				agencyList.clear();
				agencyList.add(0, new CoAgencyBaseModel());
				abModel = agencyList.get(0);
			}
			if (abModel.getCoab_id() != 0) {
				str += " and copr_cabc_id=" + abModel.getCoab_id();
			}

			setNotselectList(new ListModelList<CoProductModel>(
					bll.getCoproductList(str)));
		} catch (Exception e) {
			Messagebox.show("检索失败,请联系IT部门!", "ERROR", Messagebox.OK,
					Messagebox.ERROR);
			e.printStackTrace();
		}
	}

	@Command
	@NotifyChange("notselectList")
	public void search1() {
		str = "";
		CoQuotation_AddBll bll = new CoQuotation_AddBll();
		if (!copr_type.isEmpty()) {
			str += " and copr_type='" + copr_type + "'";
		}
		if (cpcModel != null) {
			if (cpcModel.getCopc_id() != null) {
				str += " and copr_copc_id=" + cpcModel.getCopc_id();
			}

		}
		if (cpctModel != null) {
			if (cpctModel.getCpct_id() != null) {
				str += " and cpcr_cpct_id=" + cpctModel.getCpct_id();
			}

		}
		if (abModel.getCoab_id() != 0) {
			str += " and copr_cabc_id=" + abModel.getCoab_id();
		}
		try {
			setNotselectList(new ListModelList<CoProductModel>(
					bll.getCoproductList(str)));
		} catch (SQLException e) {
			Messagebox.show("检索失败,请联系IT部门!", "ERROR", Messagebox.OK,
					Messagebox.ERROR);
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/**
	 * 筛选产品类型列表
	 * 
	 */
	@Command("class_change")
	@NotifyChange({ "sclasslist" })
	public void class_change() {
		sclasslist.clear();
		if (!copr_type.isEmpty()) {
			for (CoPclassModel m : classList) {
				if (!copr_type.equals(m.getCopc_type1())) {
					continue;
				}

				sclasslist.add(m);
			}
			if (sclasslist.size() > 0) {
				sclasslist.add(0, null);
			}
		}
	}

	@Command("productSelect")
	@NotifyChange({ "clModel", "fwvis", "flvis", "fwinvis", "standardList",
			"standardDisable", "feetypeList", "feetypeDisable", "amountList",
			"amountDisable", "cpfList", "feevis", "divvi1", "divvi2", "divvi3",
			"divvi4", "divvi5", "divvi6", "int11", "int12", "int21", "int22",
			"int23", "int31", "int32", "int33", "int41", "int42", "int43",
			"int44", "int51", "int52", "int53", "int54", "int55", "int56",
			"int57", "int58", "xstjvis","xstjvis1","xstjvis2", "flpkvis", "rspkvis", "hjpkvis" })
	public void productSelect(@BindingParam("a") Combobox cb,
			@BindingParam("ifselect") Radiogroup ifselect,
			@BindingParam("emjoy") Combobox emjoy) {
		// clModel = info;
		CoQuotation_AddBll bll = new CoQuotation_AddBll();
		if (cb.getSelectedItem() != null) {

			CoProductModel cm = cb.getSelectedItem().getValue();
			List<CoProductModel> list = bll.getCoproductFee(cm.getCopr_id());
			clModel.setColi_name(cm.getCopr_name());
			clModel.setColi_copr_id(cm.getCopr_id());
			clModel.setCopr_type(cm.getCopr_type());
			clModel.setColi_pclass(cm.getCopc_name());
			clModel.setColi_name(cm.getCopr_name());
			clModel.setColi_group_count(1);
			clModel.setColi_isfwf(0);
			if (list.size() > 0) {
				clModel.setColi_cpfc_name(list.get(0).getCpfc_name());
				clModel.setColi_fee(list.get(0).getFee());
			}

			selectchange();
			radioisselect(ifselect, emjoy);
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
			clModel.setColi_fee(cpModel.getFee());
			feeReadonly = cpModel.getCpfr_lock() == 1 ? true : false;
		} catch (Exception e) {
			e.printStackTrace();
		}
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

	// 通过各种条件判断应该显示哪个详情grid
	@Command("selectchange")
	@NotifyChange({ "fwvis", "flvis", "fwinvis", "standardList",
			"standardDisable", "feetypeList", "feetypeDisable", "amountList",
			"amountDisable", "cpfList", "feevis", "xstjvis","xstjvis1","xstjvis2", "flpkvis",
			"rspkvis", "hjpkvis" , "clModel",
			"cpfcModel"})
	public void selectchange() {
		if (clModel.getColi_isfwf() != null && clModel.getColi_isfwf() == 1) {
			try {
				setCpfList(new cpd_addBll().geteclass1());
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
	
	//更新福利项目的享受条件时,重置相关字段
	@Command
	@NotifyChange({"xstjvis1","xstjvis2","clModel"})
	public void updateStand(){
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
				feetypeList.add("按实际收取");
				feetypeList.add("全部收取");
			} else if (clModel.getColi_pclass().equals("商业保险")) {
				feetypeDisable = false;
				feetypeList.clear();
				feetypeList.add("按年收费，员工离职需核算是否退费");
				feetypeList.add("按月收费，员工离职需核算是否补费");
				feetypeList.add("按月收费，员工离职无需核算是否补费");
				clModel.setColi_standard(standardList.get(1).getColi_standard());
			} else {
				feetypeList.clear();
				feetypeDisable = true;
			}

			/* 商保份数 */
			if (clModel.getColi_pclass().equals("商业保险")) {
				amountDisable = false;
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

	// 检查项目信息是否录入完整
	public boolean judgeItem() {
		boolean b = true;

		if (clModel.getColi_copr_id() != null && clModel.getColi_copr_id() > 0) {
			// 判断产品类型,享受方式
			switch (clModel.getColi_pclass()) {
			case "档案":
			case "户口":
			case "商业保险":
			case "体检":
			case "员工福利":
				if (clModel.getColi_standard() == null
						|| clModel.getColi_standard().equals("")) {
					Messagebox.show(
							clModel.getColi_pclass() + "-"
									+ clModel.getColi_name() + ",享受方式为空.",
							"ERROR", Messagebox.OK, Messagebox.ERROR);
					return false;
				}
				break;

			default:
				break;
			}
			// 判断产品类型,收费方式
			switch (clModel.getColi_pclass()) {

			case "商业保险":
				if (clModel.getColi_feetype() == null
						|| clModel.getColi_feetype().equals("")) {
					Messagebox.show(
							clModel.getColi_pclass() + "-"
									+ clModel.getColi_name() + ",收费方式为空.",
							"ERROR", Messagebox.OK, Messagebox.ERROR);
					return false;
				}
				if (clModel.getColi_amount() == null
						|| clModel.getColi_amount().equals("")) {
					Messagebox.show(
							clModel.getColi_pclass() + "-"
									+ clModel.getColi_name() + ",份数为空.",
							"ERROR", Messagebox.OK, Messagebox.ERROR);
					return false;
				}
				break;

			default:
				break;
			}

			// 判断产品名称,享受条件
			switch (clModel.getColi_name()) {
			case "综合管理费":
			case "保管服务费":
			case "服档费":
				if (clModel.getColi_feetype() == null
						|| clModel.getColi_feetype().equals("")) {
					Messagebox.show(
							clModel.getColi_pclass() + "-"
									+ clModel.getColi_name() + ",收费方式为空.",
							"ERROR", Messagebox.OK, Messagebox.ERROR);
					return false;
				}
				if (clModel.getColi_rspaykind() == null
						|| clModel.getColi_rspaykind().equals("")) {
					Messagebox.show(
							clModel.getColi_pclass() + "-"
									+ clModel.getColi_name() + ",档案费用付款方式为空.",
							"ERROR", Messagebox.OK, Messagebox.ERROR);
					return false;
				}
				if (clModel.getColi_standard() == null
						|| clModel.getColi_standard().equals("")) {
					Messagebox.show(
							clModel.getColi_pclass() + "-"
									+ clModel.getColi_name() + ",享受方式为空.",
							"ERROR", Messagebox.OK, Messagebox.ERROR);
					return false;
				}
				break;
			default:
				break;

			}

		}

		return b;
	}

	@Command
	public void submit(@BindingParam("bc_compact") Textbox bc_compact)
			throws Exception {
		final Combobox cb = (Combobox) win.getFellow("coproduct");
		Combobox cb2 = (Combobox) win.getFellow("coofer");

		if (cb2.getSelectedItem() != null) {
			if (cb2.getSelectedItem().getValue().equals("")) {
				Messagebox.show("请选择报价单!", "ERROR", Messagebox.OK,
						Messagebox.ERROR);
				return;
			} else {
				clModel.setCoof_id((Integer) cb2.getSelectedItem().getValue());
			}
		} else {
			Messagebox
					.show("请选择报价单!", "ERROR", Messagebox.OK, Messagebox.ERROR);
			return;
		}

		if (cb.getSelectedItem() != null) {
			if (cb.getSelectedItem().getValue().equals("")) {
				Messagebox.show("请选择产品!", "ERROR", Messagebox.OK,
						Messagebox.ERROR);
				return;
			} else {
				clModel.setColi_copr_id(((CoProductModel) cb.getSelectedItem()
						.getValue()).getCopr_id());
				clModel.setColi_name(((CoProductModel) cb.getSelectedItem()
						.getValue()).getCopr_name());
			}
		} else {
			Messagebox.show("请选择产品!", "ERROR", Messagebox.OK, Messagebox.ERROR);
			return;
		}

		if (!judgeItem()) {
			return;
		}

		clModel.setColi_addname(UserInfo.getUsername());
		clModel.setColi_isfwf(0);
		clModel.setColi_state(2);

		if (bc_compact.getValue().equals("")) {
			Messagebox.show("请选择补充协议!", "ERROR", Messagebox.OK,
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
							// 获取用户名
							String username = UserInfo.getUsername();
							// 编辑文件名
							String path = "OfficeFile/DownLoad/CoCompact/";

							SimpleDateFormat sdf = new SimpleDateFormat(
									"yyyyMMddHHmmss");
							Date date = new Date();
							String nowtime = sdf.format(date);
							String name = path
									+ nowtime
									+ ((CoProductModel) cb.getSelectedItem()
											.getValue()).getCopr_id() + "."
									+ media.getFormat();
							if (FileOperate.upload(media, name)) {
								// 调用合同签回方法
								String[] message = ccsaBll.DocFileUpload(
										cocoId, 0, "", name,
										FileOperate.getFileSize(media),
										username, 0, "产品新增");

							}
							CoQuotation_AddBll bll = new CoQuotation_AddBll();
							Integer i = bll.addCoofferlist(clModel);

							String ch_message = cocoBll.addco("0", clModel
									.getColi_fee().toString(), null);
							if (i > 0) {
								Messagebox.show("添加成功!", "ERROR",
										Messagebox.OK, Messagebox.INFORMATION);
								win.detach();
							} else {
								Messagebox.show("添加失败!", "ERROR",
										Messagebox.OK, Messagebox.ERROR);
							}
						}
					}
				});
	}

	@Command("browse")
	@NotifyChange("filename")
	public void browse(@ContextParam(ContextType.BIND_CONTEXT) BindContext ul) {
		UploadEvent upEvent = (UploadEvent) ul.getTriggerEvent();
		this.media = upEvent.getMedia();
		if (media.getFormat().equals("txt") || media.getFormat().equals("xls")
				|| media.getFormat().equals("xlsx")
				|| media.getFormat().equals("jpg")
				|| media.getFormat().equals("gid")
				|| media.getFormat().equals("pdf")) {
			this.media = null;
			Messagebox.show("上传文件出错，只能上传doc后缀的WORD文档。", "操作提示", Messagebox.OK,
					Messagebox.NONE);

		} else {
			setFilename(media.getName());
		}
	}

	public String getCompany() {
		return company;
	}

	public void setCompany(String company) {
		this.company = company;
	}

	public CoOfferListModel getClModel() {
		return clModel;
	}

	public void setClModel(CoOfferListModel clModel) {
		this.clModel = clModel;
	}

	public List<CoAgencyBaseModel> getAgencyList() {
		return agencyList;
	}

	public void setAgencyList(List<CoAgencyBaseModel> agencyList) {
		this.agencyList = agencyList;
	}

	public List<String> getCoprList() {
		return coprList;
	}

	public void setCoprList(List<String> coprList) {
		this.coprList = coprList;
	}

	public List<CoProductModel> getNotselectList() {
		return notselectList;
	}

	public void setNotselectList(List<CoProductModel> notselectList) {
		this.notselectList = notselectList;
	}

	public CoPFeeclassModel getCpfcModel() {
		return cpfcModel;
	}

	public void setCpfcModel(CoPFeeclassModel cpfcModel) {
		this.cpfcModel = cpfcModel;
	}

	public List<CoOfferListModel> getStandardList() {
		return standardList;
	}

	public void setStandardList(List<CoOfferListModel> standardList) {
		this.standardList = standardList;
	}

	public boolean isStandardDisable() {
		return standardDisable;
	}

	public void setStandardDisable(boolean standardDisable) {
		this.standardDisable = standardDisable;
	}

	public List<String> getFeetypeList() {
		return feetypeList;
	}

	public void setFeetypeList(List<String> feetypeList) {
		this.feetypeList = feetypeList;
	}

	public boolean isFeetypeDisable() {
		return feetypeDisable;
	}

	public void setFeetypeDisable(boolean feetypeDisable) {
		this.feetypeDisable = feetypeDisable;
	}

	public String getFeetype() {
		return feetype;
	}

	public void setFeetype(String feetype) {
		this.feetype = feetype;
	}

	public List<Integer> getAmountList() {
		return amountList;
	}

	public void setAmountList(List<Integer> amountList) {
		this.amountList = amountList;
	}

	public boolean isAmountDisable() {
		return amountDisable;
	}

	public void setAmountDisable(boolean amountDisable) {
		this.amountDisable = amountDisable;
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

	public boolean isXstjvis() {
		return xstjvis;
	}

	public void setXstjvis(boolean xstjvis) {
		this.xstjvis = xstjvis;
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

	public boolean isFeeReadonly() {
		return feeReadonly;
	}

	public void setFeeReadonly(boolean feeReadonly) {
		this.feeReadonly = feeReadonly;
	}

	public String getCopr_type() {
		return copr_type;
	}

	public void setCopr_type(String copr_type) {
		this.copr_type = copr_type;
	}

	public ListModelList<CoPclassModel> getSclasslist() {
		return sclasslist;
	}

	public void setSclasslist(ListModelList<CoPclassModel> sclasslist) {
		this.sclasslist = sclasslist;
	}

	public CoPclassModel getCpcModel() {
		return cpcModel;
	}

	public void setCpcModel(CoPclassModel cpcModel) {
		this.cpcModel = cpcModel;
	}

	public ListModelList<PubProCityModel> getCityList() {
		return cityList;
	}

	public void setCityList(ListModelList<PubProCityModel> cityList) {
		this.cityList = cityList;
	}

	public PubProCityModel getPpcModel() {
		return ppcModel;
	}

	public void setPpcModel(PubProCityModel ppcModel) {
		this.ppcModel = ppcModel;
	}

	public CoAgencyBaseModel getAbModel() {
		return abModel;
	}

	public void setAbModel(CoAgencyBaseModel abModel) {
		this.abModel = abModel;
	}

	public List<CoPFeeclassModel> getCpfList() {
		return cpfList;
	}

	public void setCpfList(List<CoPFeeclassModel> cpfList) {
		this.cpfList = cpfList;
	}

	public ListModel<PubProCityModel> getCityListModel() {
		return cityListModel;
	}

	public void setCityListModel(ListModel<PubProCityModel> cityListModel) {
		this.cityListModel = cityListModel;
	}

	public List<CopCompactModel> getCpctList() {
		return cpctList;
	}

	public void setCpctList(List<CopCompactModel> cpctList) {
		this.cpctList = cpctList;
	}

	public List<CoPclassModel> getClassList() {
		return classList;
	}

	public void setClassList(List<CoPclassModel> classList) {
		this.classList = classList;
	}

	public List<CoOfferModel> getCoofList() {
		return coofList;
	}

	public void setCoofList(List<CoOfferModel> coofList) {
		this.coofList = coofList;
	}

	public CopCompactModel getCpctModel() {
		return cpctModel;
	}

	public void setCpctModel(CopCompactModel cpctModel) {
		this.cpctModel = cpctModel;
	}

	public String getFilename() {
		return filename;
	}

	public void setFilename(String filename) {
		this.filename = filename;
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