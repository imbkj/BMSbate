package Controller.EmCommissionOut;

import Model.EmCommissionOutZYTModel;
import Model.CoAgencyBaseCityRelViewModel;
import Model.CoAgencyLinkmanModel;
import Model.EmCommissionOutChangeModel;
import Model.EmCommissionOutFeeDetailChangeModel;
import Model.PubStateModel;
import Util.FileOperate;
import Util.RegexUtil;
import Util.UserInfo;
import Util.plyUtil;

import java.io.File;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import jxl.Workbook;
import jxl.format.Alignment;
import jxl.format.Border;
import jxl.format.BorderLineStyle;
import jxl.format.CellFormat;
import jxl.format.VerticalAlignment;
import jxl.write.Label;
import jxl.write.WritableCellFormat;
import jxl.write.WritableFont;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;
import jxl.write.WriteException;

import org.zkoss.bind.BindUtils;
import org.zkoss.bind.annotation.BindingParam;
import org.zkoss.bind.annotation.Command;
import org.zkoss.bind.annotation.NotifyChange;
import org.zkoss.bind.impl.BinderImpl;
import org.zkoss.bind.impl.BinderUtil;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zul.ListModelList;
import org.zkoss.zul.Listitem;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Window;

import bll.EmCommissionOut.EmCommissionOutListBll;
import bll.EmCommissionOut.EmCommissionOut_OperateBll;

public class EmCommissionOut_hdOperateListController {

	private List<EmCommissionOutChangeModel> ecocList;
	private List<EmCommissionOutChangeModel> secocList = new ListModelList<>();
	private List<CoAgencyBaseCityRelViewModel> cityList = new ListModelList<>();
	private List<CoAgencyBaseCityRelViewModel> coabList = new ListModelList<>();
	private List<EmCommissionOutChangeModel> clientList = new ListModelList<>();
	private List<PubStateModel> stateList = new ListModelList<>();
	private List<EmCommissionOutChangeModel> countList = new ListModelList<>();
	private List<EmCommissionOutFeeDetailChangeModel> feeList = new ListModelList<>();
	private List<String> stardList = new ListModelList<>();
	private ListModelList<EmCommissionOutZYTModel> ci_excel;// 导出excel商保数据

	// 检索条件
	private String cid = "";
	private String shortname = "";
	private String gid = "";
	private String name = "";
	private String sointitle = "";
	private String city = "";
	private String coabname = "";
	private String client = "";
	private String statename = "";
	private String addtype = "";
	private Date addtime = null;

	private boolean mu = true;
	private boolean ifupdates = true;
	private boolean ifexcel = true;

	public EmCommissionOut_hdOperateListController() {
		try {
			bind();
			search("1");
		} catch (Exception e) {
			e.printStackTrace();
			Messagebox
					.show("页面加载出错!", "ERROR", Messagebox.OK, Messagebox.ERROR);
		}
	}

	/**
	 * 初始化绑定
	 * 
	 */
	public void bind() {
		EmCommissionOutListBll bll = new EmCommissionOutListBll();

		try {
			setEcocList(bll
					.getEmCommOutChangeList(" and typename='后道状态' and ecoc_state=1"));
			setCityList(bll.getCityList("", ""));
			cityList.add(0, null);
			setCoabList(bll.getCoagencyList());
			coabList.add(0, null);
			setClientList(bll.getClientList());
			clientList.add(0, null);
			setStardList(bll.getStardList());
			stardList.add(0, null);
			statelistbind();

			setCountList(bll.getCountList());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 状态列表绑定
	 * 
	 */
	@Command("statelistbind")
	@NotifyChange({ "stateList", "statename", "secocList" })
	public void statelistbind() {
		try {
			EmCommissionOutListBll bll = new EmCommissionOutListBll();
			String type = statehandle();

			if (!type.isEmpty()) {
				setStateList(bll.getStateList(" and typename='后道状态' and type='"
						+ type + "'"));
			} else {
				setStateList(bll
						.getStateList(" and typename='后道状态' and type='ecocall'"));
			}
			stateList.add(0, null);
			// statename = "";

			search("1");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 列表检索
	 * 
	 */
	@Command("search")
	@NotifyChange({ "secocList" })
	public void search(String st) {
		secocList.clear();
		if (st == "") {
			EmCommissionOutListBll bll = new EmCommissionOutListBll();
			setEcocList(bll.getEmCommOutChangeList(" and typename='后道状态'"));
		}
		for (EmCommissionOutChangeModel m : ecocList) {
			if (!cid.isEmpty()) {
				if (!RegexUtil.isExists(cid, m.getCid() + "")) {
					continue;
				}
			}
			if (!shortname.isEmpty()) {
				if (!RegexUtil.isExists(shortname, m.getCoba_shortname())) {
					continue;
				}
			}
			if (!gid.isEmpty()) {
				if (!RegexUtil.isExists(gid, m.getGid() + "")) {
					continue;
				}
			}
			if (!name.isEmpty()) {
				if (!RegexUtil.isExists(name, m.getEmba_name())) {
					continue;
				}
			}
			if (!sointitle.isEmpty()) {
				if (!sointitle.equals(m.getSoin_title())) {
					continue;
				}
			}
			if (!city.isEmpty()) {
				if (!city.equals(m.getCity())) {
					continue;
				}
			}
			if (!coabname.isEmpty()) {
				if (!coabname.equals(m.getCoab_name())) {
					continue;
				}
			}
			if (!client.isEmpty()) {
				if (!client.equals(m.getEcoc_client())) {
					continue;
				}
			}
			if (!statename.isEmpty()) {
				if (!statename.equals(m.getStatename())) {
					continue;
				}
			}
			if (!addtype.isEmpty()) {
				if (!addtype.equals(m.getEcoc_addtype())) {
					continue;
				}
			}

			String ef_date = "";
			if (addtime != null) {
				SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
				String Date = sdf.format(addtime);
				ef_date = Date;
			}
			if (!ef_date.isEmpty()) {
				// if (!addtime.equals(m.getEcoc_addtime())) {
				// continue;
				// }
				if (!RegexUtil
						.isExists(ef_date, m.getEcoc_addtime().toString())) {
					continue;
				}
			}
			secocList.add(m);
		}
	}

	@Command("tsearch")
	@NotifyChange({ "secocList" })
	public void tsearch() {
		EmCommissionOutListBll bll = new EmCommissionOutListBll();

		// secocList.clear();
		/*
		 * setEcocList(bll.getEmCommOutChangeList2("")); for
		 * (EmCommissionOutChangeModel m : ecocList) { secocList.add(m); }
		 */
		// setEcocList(bll.getEmCommOutChangeList(" and typename='后道状态'"));

		search("");
	}

	/**
	 * 综合检索
	 * 
	 */
	@Command("multsearch")
	@NotifyChange({ "secocList", "statename", "addtype", "stateList",
			"ifupdates", "ifexcel", "cityList" })
	public void multsearch(@BindingParam("ecoc_addtype") String ecoc_addtype,
			@BindingParam("ecoc_state") String ecoc_state,
			@BindingParam("url_state") org.zkoss.zul.Label url_state) {
		EmCommissionOutListBll bll = new EmCommissionOutListBll();
		System.out.println(new Date());
		setEcocList(bll.getEmCommOutChangeList(" and typename='后道状态'"));
		System.out.println(new Date());
		// search("");
		addtype = ecoc_addtype;
		statename = ecoc_state;
		url_state.setValue("0");
		setCityList(bll.getCityList(addtype, statename));
		statelistbind();
		System.out.println(addtype);
		System.out.println(statename);
		System.out.println(url_state.getValue());
		ifupdates = true;
		ifexcel = true;
		
	}

	@Command("ts_search")
	@NotifyChange({ "secocList", "statename", "addtype", "stateList",
			"ifupdates", "ifexcel" })
	public void ts_search(@BindingParam("ecoc_addtype") String ecoc_addtype,
			@BindingParam("ecoc_state") String ecoc_state,
			@BindingParam("url_state") org.zkoss.zul.Label url_state) {
		addtype = ecoc_addtype;
		statelistbind();
		statename = ecoc_state;
		tsearch();
		url_state.setValue("1");
		ifupdates = true;
		ifexcel = true;
	}

	/**
	 * 弹出状态变更窗口
	 * 
	 * @param m
	 * @param url
	 */
	@Command("updatestate")
	@NotifyChange({ "secocList", "countList" })
	public void updatestate(@BindingParam("each") EmCommissionOutChangeModel m,
			@BindingParam("url") String url,
			@BindingParam("url_state") org.zkoss.zul.Label url_state) {
		System.out.println(statename);
		m.setUpdateState(false);
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("daid", m.getEcoc_id());
		map.put("url_state", url_state.getValue());
		map.put("cm", m);
		Window window = (Window) Executions.createComponents(url, null, map);
		window.doModal();
		System.out.println(m.isUpdateState());

		if (m.isUpdateState()) {
			EmCommissionOutListBll bll = new EmCommissionOutListBll();
			setEcocList(bll.getEmCommOutChangeList(" and typename='后道状态'"));
			search("1");
		}

		// gid=m.getGid().toString();
		/*
		 * if (url_state.getValue().equals("0")) { search(""); } else {
		 * tsearch(); }
		 */
	}

	/**
	 * 弹出详情窗口
	 * 
	 * @param m
	 * @param url
	 */
	@Command("detail")
	public void detail(@BindingParam("each") EmCommissionOutChangeModel m,
			@BindingParam("url") String url) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("daid", m.getEcoc_id());

		Window window = (Window) Executions.createComponents(url, null, map);
		window.doModal();

		// bind();
		// search("");
	}

	public String statehandle() {
		String type = "";
		if (addtype.equals("新增")) {
			type = "ecocadd";
		} else if (addtype.equals("调整")) {
			type = "ecocchange";
		} else if (addtype.equals("离职")) {
			type = "ecocter";
		} else if (addtype.equals("一次性费用")) {
			type = "ecocotf";
		} else if (addtype.equals("取消")) {
			type = "ecoccancel";
		}
		return type;
	}

	/**
	 * 判断是否可以批量操作(导出excel、状态变更)
	 * 
	 * @param set
	 */
	@Command("ListOnClick")
	@NotifyChange({ "ifupdates", "ifexcel" })
	public void ListOnClick(@BindingParam("set") Set<Listitem> set) {
		try {
			if (set.size() > 0) {
				EmCommissionOutChangeModel m = new EmCommissionOutChangeModel();
				set.iterator().next().getValue();
				for (Listitem list : set) {
					if (list.getValue() != null) {
						m = list.getValue();
						break;
					}
				}

				String addtype1 = m.getEcoc_addtype();
				String statename1 = m.getStatename();

				if (statename1.equals("未确认") || statename1.equals("一次确认")) {
					ifupdates = false;
					for (Listitem lt : set) {
						m = lt.getValue();
						if (m != null) {

							if (!addtype1.equals(m.getEcoc_addtype())
									|| !statename1.equals(m.getStatename())) {
								ifupdates = true;
								break;
							}
						}
					}
				} else {
					ifupdates = true;
				}

				ifexcel = false;
				for (Listitem lt : set) {
					m = lt.getValue();
					if (m != null) {
						if (!addtype1.equals(m.getEcoc_addtype())
								|| !statename1.equals(m.getStatename())) {
							ifexcel = true;
							break;
						}
					}
				}

			} else {
				ifupdates = true;
				ifexcel = true;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 批量状态变更
	 * 
	 */
	@Command("updates")
	@NotifyChange({ "secocList", "ifupdates", "ifexcel", "countList" })
	public void updates(@BindingParam("set") Set<Listitem> set) {
		EmCommissionOut_OperateBll bll = new EmCommissionOut_OperateBll();
		Integer count = 0;
		String[] str = new String[5];

		try {
			EmCommissionOutChangeModel m = new EmCommissionOutChangeModel();
			set.iterator().next().getValue();
			for (Listitem list : set) {
				if (list.getValue() != null) {
					m = list.getValue();
					break;
				}
			}
			if (m.getEcoc_state() == 1) {
				if (Messagebox.show("是否确定提交勾选的" + set.size() + "条委托单?",
						"CONFIRM", Messagebox.OK | Messagebox.CANCEL,
						Messagebox.QUESTION) == Messagebox.OK) {
					for (Listitem lt : set) {
						m = lt.getValue();
						if (m != null) {
							m.setEcoc_state(m.getEcoc_state() + 1);
							m.setType(m.getEcoc_type());
							m.setRemark("手动批量操作状态变更");

							str = bll.updatestate(m, null);

							if (str[0].equals("1")) {
								count++;
							}
						}
					}

					if (set.size() == count) {
						Messagebox.show("全部提交成功\n共更新:" + count + "条委托单!",
								"INFORMATION", Messagebox.OK,
								Messagebox.INFORMATION);
					} else if (set.size() - count > 0) {
						Messagebox.show("部分提交成功\n勾选了:" + set.size()
								+ "条委托单,更新成功:" + count + "条委托单", "ERROR",
								Messagebox.OK, Messagebox.ERROR);
					} else {
						Messagebox.show("全部提交失败!", "ERROR", Messagebox.OK,
								Messagebox.ERROR);
					}
				}
			} else if (m.getEcoc_state() == 2) {
				String daids = "";
				for (Listitem lt : set) {
					m = lt.getValue();

					daids += "," + m.getEcoc_id();
				}
				daids = daids.substring(1);

				Map<String, Object> map = new HashMap<>();
				map.put("daids", daids);

				Window window = (Window) Executions.createComponents(
						"/EmCommissionOut/EmCommissionOut_Operates.zul", null,
						map);
				window.doModal();
			}

			ifupdates = true;
			ifexcel = true;
			bind();
			search("1");
		} catch (Exception e) {
			e.printStackTrace();
			Messagebox.show("提交出错!", "ERROR", Messagebox.OK, Messagebox.ERROR);
		}
	}

	/**
	 * 导出Excel(单条、批量)
	 * 
	 * @param set
	 */
	@Command("ExportExcel")
	public void ExportExcel(@BindingParam("set") Set<Listitem> set) {
		if (set.size() <= 0) {
			Messagebox.show("请至少选择一个员工!", "ERROR", Messagebox.OK,
					Messagebox.ERROR);
		} else {

			// 获取set中第一项委托单类型
			String addtypeString = ((EmCommissionOutChangeModel) set.iterator()
					.next().getValue()).getEcoc_addtype();

			// 按照委托单类型筛选数据，并装入excelList中
			List<EmCommissionOutChangeModel> excelList = new ListModelList<>();

			for (Listitem lt : set) {
				EmCommissionOutChangeModel m1 = lt.getValue();
				if (m1.getEcoc_addtype().equals(addtypeString)) {
					excelList.add(m1);
				}
			}

			// 按照set中第一个类型导出
			if (addtypeString.equals("新增") || addtypeString.equals("调整")) {
				Excel_Add(excelList);
			}

			if (addtypeString.equals("离职")) {
				Excel_End(excelList);
			}
		}
	}

	// 导出批量上传数据
	@Command("ZYTExcel")
	public void ZYTExcel(@BindingParam("set") Set<Listitem> set)
			throws Exception {
		EmCommissionOutListBll bll = new EmCommissionOutListBll();
		if (set.size() <= 0) {
			Messagebox.show("请至少选择一个员工!", "ERROR", Messagebox.OK,
					Messagebox.ERROR);
		} else {
			// 获取set中第一项委托单类型
			String addtypeString = ((EmCommissionOutChangeModel) set.iterator()
					.next().getValue()).getEcoc_addtype();

			// 按照委托单类型筛选数据，并装入excelList中
			List<EmCommissionOutChangeModel> excelList = new ListModelList<>();

			for (Listitem lt : set) {
				EmCommissionOutChangeModel m1 = lt.getValue();
				if (m1.getEcoc_addtype().equals(addtypeString)) {
					excelList.add(m1);
				}
			}

			String filename = ""; // 文件名
			String absolutePath; // 服务器地址
			String filetype = "wt_out_excel.xls"; // 文件类型
			SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
			Date date = new Date();
			String nowtime = sdf.format(date);

			filename = nowtime + filetype;
			absolutePath = FileOperate.getAbsolutePath();

			String excel_tp = "";

			if (addtypeString.equals("新增") || addtypeString.equals("调整")) {
				excel_tp = "/OfficeFile/Templet/EmCommissionOut/WT_ZYT_UP.xls";
			} else {
				excel_tp = "/OfficeFile/Templet/EmCommissionOut/WT_ZYT_DIM.xls";
			}

			// 创建exce
			// 读取Excel模板
			Workbook wb = Workbook
					.getWorkbook(new File(absolutePath + excel_tp));
			// 使用模板创建
			WritableWorkbook wwb = Workbook.createWorkbook(new File(
					absolutePath + "/OfficeFile/DownLoad/EmCommissionOut/"
							+ filename), wb);
			// 生成工作表
			WritableSheet sheet = wwb.getSheet(0);
			// Label label1 = null;
			int rowdata = 0;
			String pid = "";
			for (int i = 0; i < excelList.size(); i++) {

				if (addtypeString.equals("新增") || addtypeString.equals("调整")) {
					ci_excel = new ListModelList<EmCommissionOutZYTModel>(
							bll.ci_excel(excelList.get(i).getEcoc_id()
									.toString()));// 显示保险在保类型
				} else {
					ci_excel = new ListModelList<EmCommissionOutZYTModel>(
							bll.ci_exceldim(excelList.get(i).getEcoc_id()
									.toString()));// 显示保险在保类型
				}

				rowdata = rowdata + 1;

				pid = String.valueOf(rowdata - 3);

				jxl.write.Label label1 = new jxl.write.Label(0, rowdata,
						ci_excel.get(0).getT1());
				jxl.write.Label label2 = new jxl.write.Label(1, rowdata,
						ci_excel.get(0).getT2());
				jxl.write.Label label3 = new jxl.write.Label(2, rowdata,
						ci_excel.get(0).getT3());
				jxl.write.Label label4 = new jxl.write.Label(3, rowdata,
						ci_excel.get(0).getT4());
				jxl.write.Label label5 = new jxl.write.Label(4, rowdata,
						ci_excel.get(0).getT5());
				jxl.write.Label label6 = new jxl.write.Label(5, rowdata,
						ci_excel.get(0).getT6());
				jxl.write.Label label7 = new jxl.write.Label(6, rowdata,
						ci_excel.get(0).getT7());
				jxl.write.Label label8 = new jxl.write.Label(7, rowdata,
						ci_excel.get(0).getT8());
				jxl.write.Label label9 = new jxl.write.Label(8, rowdata,
						ci_excel.get(0).getT9());
				jxl.write.Label label10 = new jxl.write.Label(9, rowdata,
						ci_excel.get(0).getT10());
				jxl.write.Label label11 = new jxl.write.Label(10, rowdata,
						ci_excel.get(0).getT11());
				jxl.write.Label label12 = new jxl.write.Label(11, rowdata,
						ci_excel.get(0).getT12());
				jxl.write.Label label13 = new jxl.write.Label(12, rowdata,
						ci_excel.get(0).getT13());
				jxl.write.Label label14 = new jxl.write.Label(13, rowdata,
						ci_excel.get(0).getT14());
				jxl.write.Label label15 = new jxl.write.Label(14, rowdata,
						ci_excel.get(0).getT15());
				jxl.write.Label label16 = new jxl.write.Label(15, rowdata,
						ci_excel.get(0).getT16());
				jxl.write.Label label17 = new jxl.write.Label(16, rowdata,
						ci_excel.get(0).getT17());
				jxl.write.Label label18 = new jxl.write.Label(17, rowdata,
						ci_excel.get(0).getT18());
				jxl.write.Label label19 = new jxl.write.Label(18, rowdata,
						ci_excel.get(0).getT19());
				jxl.write.Label label20 = new jxl.write.Label(19, rowdata,
						ci_excel.get(0).getT20());
				jxl.write.Label label21 = new jxl.write.Label(20, rowdata,
						ci_excel.get(0).getT21());
				jxl.write.Label label22 = new jxl.write.Label(21, rowdata,
						ci_excel.get(0).getT22());
				jxl.write.Label label23 = new jxl.write.Label(22, rowdata,
						ci_excel.get(0).getT23());
				jxl.write.Label label24 = new jxl.write.Label(23, rowdata,
						ci_excel.get(0).getT24());
				jxl.write.Label label25 = new jxl.write.Label(24, rowdata,
						ci_excel.get(0).getT25());
				jxl.write.Label label26 = new jxl.write.Label(25, rowdata,
						ci_excel.get(0).getT26());
				jxl.write.Label label27 = new jxl.write.Label(26, rowdata,
						ci_excel.get(0).getT27());
				jxl.write.Label label28 = new jxl.write.Label(27, rowdata,
						ci_excel.get(0).getT28());
				jxl.write.Label label29 = new jxl.write.Label(28, rowdata,
						ci_excel.get(0).getT29());
				jxl.write.Label label30 = new jxl.write.Label(29, rowdata,
						ci_excel.get(0).getT30());
				jxl.write.Label label31 = new jxl.write.Label(30, rowdata,
						ci_excel.get(0).getT31());
				jxl.write.Label label32 = new jxl.write.Label(31, rowdata,
						ci_excel.get(0).getT32());
				jxl.write.Label label33 = new jxl.write.Label(32, rowdata,
						ci_excel.get(0).getT33());
				jxl.write.Label label34 = new jxl.write.Label(33, rowdata,
						ci_excel.get(0).getT34());
				jxl.write.Label label35 = new jxl.write.Label(34, rowdata,
						ci_excel.get(0).getT35());
				jxl.write.Label label36 = new jxl.write.Label(35, rowdata,
						ci_excel.get(0).getT36());
				jxl.write.Label label37 = new jxl.write.Label(36, rowdata,
						ci_excel.get(0).getT37());
				jxl.write.Label label38 = new jxl.write.Label(37, rowdata,
						ci_excel.get(0).getT38());
				jxl.write.Label label39 = new jxl.write.Label(38, rowdata,
						ci_excel.get(0).getT39());
				jxl.write.Label label40 = new jxl.write.Label(39, rowdata,
						ci_excel.get(0).getT40());
				jxl.write.Label label41 = new jxl.write.Label(40, rowdata,
						ci_excel.get(0).getT41());
				jxl.write.Label label42 = new jxl.write.Label(41, rowdata,
						ci_excel.get(0).getT42());
				jxl.write.Label label43 = new jxl.write.Label(42, rowdata,
						ci_excel.get(0).getT43());
				jxl.write.Label label44 = new jxl.write.Label(43, rowdata,
						ci_excel.get(0).getT44());
				jxl.write.Label label45 = new jxl.write.Label(44, rowdata,
						ci_excel.get(0).getT45());
				jxl.write.Label label46 = new jxl.write.Label(45, rowdata,
						ci_excel.get(0).getT46());
				jxl.write.Label label47 = new jxl.write.Label(46, rowdata,
						ci_excel.get(0).getT47());
				jxl.write.Label label48 = new jxl.write.Label(47, rowdata,
						ci_excel.get(0).getT48());
				jxl.write.Label label49 = new jxl.write.Label(48, rowdata,
						ci_excel.get(0).getT49());
				jxl.write.Label label50 = new jxl.write.Label(49, rowdata,
						ci_excel.get(0).getT50());
				jxl.write.Label label51 = new jxl.write.Label(50, rowdata,
						ci_excel.get(0).getT51());
				jxl.write.Label label52 = new jxl.write.Label(51, rowdata,
						ci_excel.get(0).getT52());
				jxl.write.Label label53 = new jxl.write.Label(52, rowdata,
						ci_excel.get(0).getT53());
				jxl.write.Label label54 = new jxl.write.Label(53, rowdata,
						ci_excel.get(0).getT54());
				jxl.write.Label label55 = new jxl.write.Label(54, rowdata,
						ci_excel.get(0).getT55());
				jxl.write.Label label56 = new jxl.write.Label(55, rowdata,
						ci_excel.get(0).getT56());
				jxl.write.Label label57 = new jxl.write.Label(56, rowdata,
						ci_excel.get(0).getT57());
				jxl.write.Label label58 = new jxl.write.Label(57, rowdata,
						ci_excel.get(0).getT58());
				jxl.write.Label label59 = new jxl.write.Label(58, rowdata,
						ci_excel.get(0).getT59());
				jxl.write.Label label60 = new jxl.write.Label(59, rowdata,
						ci_excel.get(0).getT60());
				jxl.write.Label label61 = new jxl.write.Label(60, rowdata,
						ci_excel.get(0).getT61());
				jxl.write.Label label62 = new jxl.write.Label(61, rowdata,
						ci_excel.get(0).getT62());
				jxl.write.Label label63 = new jxl.write.Label(62, rowdata,
						ci_excel.get(0).getT63());
				jxl.write.Label label64 = new jxl.write.Label(63, rowdata,
						ci_excel.get(0).getT64());
				jxl.write.Label label65 = new jxl.write.Label(64, rowdata,
						ci_excel.get(0).getT65());
				jxl.write.Label label66 = new jxl.write.Label(65, rowdata,
						ci_excel.get(0).getT66());
				jxl.write.Label label67 = new jxl.write.Label(66, rowdata,
						ci_excel.get(0).getT67());
				jxl.write.Label label68 = new jxl.write.Label(67, rowdata,
						ci_excel.get(0).getT68());
				jxl.write.Label label69 = new jxl.write.Label(68, rowdata,
						ci_excel.get(0).getT69());
				jxl.write.Label label70 = new jxl.write.Label(69, rowdata,
						ci_excel.get(0).getT70());
				jxl.write.Label label71 = new jxl.write.Label(70, rowdata,
						ci_excel.get(0).getT71());
				jxl.write.Label label72 = new jxl.write.Label(71, rowdata,
						ci_excel.get(0).getT72());
				jxl.write.Label label73 = new jxl.write.Label(72, rowdata,
						ci_excel.get(0).getT73());
				jxl.write.Label label74 = new jxl.write.Label(73, rowdata,
						ci_excel.get(0).getT74());
				jxl.write.Label label75 = new jxl.write.Label(74, rowdata,
						ci_excel.get(0).getT75());
				jxl.write.Label label76 = new jxl.write.Label(75, rowdata,
						ci_excel.get(0).getT76());
				jxl.write.Label label77 = new jxl.write.Label(76, rowdata,
						ci_excel.get(0).getT77());
				jxl.write.Label label78 = new jxl.write.Label(77, rowdata,
						ci_excel.get(0).getT78());
				jxl.write.Label label79 = new jxl.write.Label(78, rowdata,
						ci_excel.get(0).getT79());
				jxl.write.Label label80 = new jxl.write.Label(79, rowdata,
						ci_excel.get(0).getT80());
				jxl.write.Label label81 = new jxl.write.Label(80, rowdata,
						ci_excel.get(0).getT81());
				jxl.write.Label label82 = new jxl.write.Label(81, rowdata,
						ci_excel.get(0).getT82());
				jxl.write.Label label83 = new jxl.write.Label(82, rowdata,
						ci_excel.get(0).getT83());
				jxl.write.Label label84 = new jxl.write.Label(83, rowdata,
						ci_excel.get(0).getT84());
				jxl.write.Label label85 = new jxl.write.Label(84, rowdata,
						ci_excel.get(0).getT85());
				jxl.write.Label label86 = new jxl.write.Label(85, rowdata,
						ci_excel.get(0).getT86());
				jxl.write.Label label87 = new jxl.write.Label(86, rowdata,
						ci_excel.get(0).getT87());
				jxl.write.Label label88 = new jxl.write.Label(87, rowdata,
						ci_excel.get(0).getT88());
				jxl.write.Label label89 = new jxl.write.Label(88, rowdata,
						ci_excel.get(0).getT89());
				jxl.write.Label label90 = new jxl.write.Label(89, rowdata,
						ci_excel.get(0).getT90());
				jxl.write.Label label91 = new jxl.write.Label(90, rowdata,
						ci_excel.get(0).getT91());
				jxl.write.Label label92 = new jxl.write.Label(91, rowdata,
						ci_excel.get(0).getT92());
				jxl.write.Label label93 = new jxl.write.Label(92, rowdata,
						ci_excel.get(0).getT93());
				jxl.write.Label label94 = new jxl.write.Label(93, rowdata,
						ci_excel.get(0).getT94());
				jxl.write.Label label95 = new jxl.write.Label(94, rowdata,
						ci_excel.get(0).getT95());
				jxl.write.Label label96 = new jxl.write.Label(95, rowdata,
						ci_excel.get(0).getT96());
				jxl.write.Label label97 = new jxl.write.Label(96, rowdata,
						ci_excel.get(0).getT97());
				jxl.write.Label label98 = new jxl.write.Label(97, rowdata,
						ci_excel.get(0).getT98());
				jxl.write.Label label99 = new jxl.write.Label(98, rowdata,
						ci_excel.get(0).getT99());
				jxl.write.Label label100 = new jxl.write.Label(99, rowdata,
						ci_excel.get(0).getT100());
				jxl.write.Label label101 = new jxl.write.Label(100, rowdata,
						ci_excel.get(0).getT101());
				jxl.write.Label label102 = new jxl.write.Label(101, rowdata,
						ci_excel.get(0).getT102());
				jxl.write.Label label103 = new jxl.write.Label(102, rowdata,
						ci_excel.get(0).getT103());
				jxl.write.Label label104 = new jxl.write.Label(103, rowdata,
						ci_excel.get(0).getT104());
				jxl.write.Label label105 = new jxl.write.Label(104, rowdata,
						ci_excel.get(0).getT105());
				jxl.write.Label label106 = new jxl.write.Label(105, rowdata,
						ci_excel.get(0).getT106());
				jxl.write.Label label107 = new jxl.write.Label(106, rowdata,
						ci_excel.get(0).getT107());
				jxl.write.Label label108 = new jxl.write.Label(107, rowdata,
						ci_excel.get(0).getT108());
				jxl.write.Label label109 = new jxl.write.Label(108, rowdata,
						ci_excel.get(0).getT109());
				jxl.write.Label label110 = new jxl.write.Label(109, rowdata,
						ci_excel.get(0).getT110());
				jxl.write.Label label111 = new jxl.write.Label(110, rowdata,
						ci_excel.get(0).getT111());
				jxl.write.Label label112 = new jxl.write.Label(111, rowdata,
						ci_excel.get(0).getT112());
				jxl.write.Label label113 = new jxl.write.Label(112, rowdata,
						ci_excel.get(0).getT113());
				jxl.write.Label label114 = new jxl.write.Label(113, rowdata,
						ci_excel.get(0).getT114());
				jxl.write.Label label115 = new jxl.write.Label(114, rowdata,
						ci_excel.get(0).getT115());
				jxl.write.Label label116 = new jxl.write.Label(115, rowdata,
						ci_excel.get(0).getT116());
				jxl.write.Label label117 = new jxl.write.Label(116, rowdata,
						ci_excel.get(0).getT117());
				jxl.write.Label label118 = new jxl.write.Label(117, rowdata,
						ci_excel.get(0).getT118());
				jxl.write.Label label119 = new jxl.write.Label(118, rowdata,
						ci_excel.get(0).getT119());
				jxl.write.Label label120 = new jxl.write.Label(119, rowdata,
						ci_excel.get(0).getT120());
				jxl.write.Label label121 = new jxl.write.Label(120, rowdata,
						ci_excel.get(0).getT121());
				jxl.write.Label label122 = new jxl.write.Label(121, rowdata,
						ci_excel.get(0).getT122());
				jxl.write.Label label123 = new jxl.write.Label(122, rowdata,
						ci_excel.get(0).getT123());
				jxl.write.Label label124 = new jxl.write.Label(123, rowdata,
						ci_excel.get(0).getT124());
				jxl.write.Label label125 = new jxl.write.Label(124, rowdata,
						ci_excel.get(0).getT125());
				jxl.write.Label label126 = new jxl.write.Label(125, rowdata,
						ci_excel.get(0).getT126());
				jxl.write.Label label127 = new jxl.write.Label(126, rowdata,
						ci_excel.get(0).getT127());
				jxl.write.Label label128 = new jxl.write.Label(127, rowdata,
						ci_excel.get(0).getT128());
				jxl.write.Label label129 = new jxl.write.Label(128, rowdata,
						ci_excel.get(0).getT129());
				jxl.write.Label label130 = new jxl.write.Label(129, rowdata,
						ci_excel.get(0).getT130());
				jxl.write.Label label131 = new jxl.write.Label(130, rowdata,
						ci_excel.get(0).getT131());
				jxl.write.Label label132 = new jxl.write.Label(131, rowdata,
						ci_excel.get(0).getT132());
				jxl.write.Label label133 = new jxl.write.Label(132, rowdata,
						ci_excel.get(0).getT133());
				jxl.write.Label label134 = new jxl.write.Label(133, rowdata,
						ci_excel.get(0).getT134());
				jxl.write.Label label135 = new jxl.write.Label(134, rowdata,
						ci_excel.get(0).getT135());
				jxl.write.Label label136 = new jxl.write.Label(135, rowdata,
						ci_excel.get(0).getT136());
				jxl.write.Label label137 = new jxl.write.Label(136, rowdata,
						ci_excel.get(0).getT137());
				jxl.write.Label label138 = new jxl.write.Label(137, rowdata,
						ci_excel.get(0).getT138());
				jxl.write.Label label139 = new jxl.write.Label(138, rowdata,
						ci_excel.get(0).getT139());
				jxl.write.Label label140 = new jxl.write.Label(139, rowdata,
						ci_excel.get(0).getT140());
				jxl.write.Label label141 = new jxl.write.Label(140, rowdata,
						ci_excel.get(0).getT141());
				jxl.write.Label label142 = new jxl.write.Label(141, rowdata,
						ci_excel.get(0).getT142());
				jxl.write.Label label143 = new jxl.write.Label(142, rowdata,
						ci_excel.get(0).getT143());
				jxl.write.Label label144 = new jxl.write.Label(143, rowdata,
						ci_excel.get(0).getT144());
				jxl.write.Label label145 = new jxl.write.Label(144, rowdata,
						ci_excel.get(0).getT145());
				jxl.write.Label label146 = new jxl.write.Label(145, rowdata,
						ci_excel.get(0).getT146());
				jxl.write.Label label147 = new jxl.write.Label(146, rowdata,
						ci_excel.get(0).getT147());
				jxl.write.Label label148 = new jxl.write.Label(147, rowdata,
						ci_excel.get(0).getT148());
				jxl.write.Label label149 = new jxl.write.Label(148, rowdata,
						ci_excel.get(0).getT149());
				jxl.write.Label label150 = new jxl.write.Label(149, rowdata,
						ci_excel.get(0).getT150());
				jxl.write.Label label151 = new jxl.write.Label(150, rowdata,
						ci_excel.get(0).getT151());
				jxl.write.Label label152 = new jxl.write.Label(151, rowdata,
						ci_excel.get(0).getT152());
				jxl.write.Label label153 = new jxl.write.Label(152, rowdata,
						ci_excel.get(0).getT153());
				jxl.write.Label label154 = new jxl.write.Label(153, rowdata,
						ci_excel.get(0).getT154());
				jxl.write.Label label155 = new jxl.write.Label(154, rowdata,
						ci_excel.get(0).getT155());
				jxl.write.Label label156 = new jxl.write.Label(155, rowdata,
						ci_excel.get(0).getT156());
				jxl.write.Label label157 = new jxl.write.Label(156, rowdata,
						ci_excel.get(0).getT157());
				jxl.write.Label label158 = new jxl.write.Label(157, rowdata,
						ci_excel.get(0).getT158());
				jxl.write.Label label159 = new jxl.write.Label(158, rowdata,
						ci_excel.get(0).getT159());
				jxl.write.Label label160 = new jxl.write.Label(159, rowdata,
						ci_excel.get(0).getT160());
				jxl.write.Label label161 = new jxl.write.Label(160, rowdata,
						ci_excel.get(0).getT161());
				jxl.write.Label label162 = new jxl.write.Label(161, rowdata,
						ci_excel.get(0).getT162());
				jxl.write.Label label163 = new jxl.write.Label(162, rowdata,
						ci_excel.get(0).getT163());
				jxl.write.Label label164 = new jxl.write.Label(163, rowdata,
						ci_excel.get(0).getT164());
				jxl.write.Label label165 = new jxl.write.Label(164, rowdata,
						ci_excel.get(0).getT165());
				jxl.write.Label label166 = new jxl.write.Label(165, rowdata,
						ci_excel.get(0).getT166());
				jxl.write.Label label167 = new jxl.write.Label(166, rowdata,
						ci_excel.get(0).getT167());
				jxl.write.Label label168 = new jxl.write.Label(167, rowdata,
						ci_excel.get(0).getT168());
				jxl.write.Label label169 = new jxl.write.Label(168, rowdata,
						ci_excel.get(0).getT169());
				jxl.write.Label label170 = new jxl.write.Label(169, rowdata,
						ci_excel.get(0).getT170());
				jxl.write.Label label171 = new jxl.write.Label(170, rowdata,
						ci_excel.get(0).getT171());
				jxl.write.Label label172 = new jxl.write.Label(171, rowdata,
						ci_excel.get(0).getT172());
				jxl.write.Label label173 = new jxl.write.Label(172, rowdata,
						ci_excel.get(0).getT173());
				jxl.write.Label label174 = new jxl.write.Label(173, rowdata,
						ci_excel.get(0).getT174());
				jxl.write.Label label175 = new jxl.write.Label(174, rowdata,
						ci_excel.get(0).getT175());
				jxl.write.Label label176 = new jxl.write.Label(175, rowdata,
						ci_excel.get(0).getT176());
				jxl.write.Label label177 = new jxl.write.Label(176, rowdata,
						ci_excel.get(0).getT177());
				jxl.write.Label label178 = new jxl.write.Label(177, rowdata,
						ci_excel.get(0).getT178());
				jxl.write.Label label179 = new jxl.write.Label(178, rowdata,
						ci_excel.get(0).getT179());
				jxl.write.Label label180 = new jxl.write.Label(179, rowdata,
						ci_excel.get(0).getT180());
				jxl.write.Label label181 = new jxl.write.Label(180, rowdata,
						ci_excel.get(0).getT181());
				jxl.write.Label label182 = new jxl.write.Label(181, rowdata,
						ci_excel.get(0).getT182());
				jxl.write.Label label183 = new jxl.write.Label(182, rowdata,
						ci_excel.get(0).getT183());
				jxl.write.Label label184 = new jxl.write.Label(183, rowdata,
						ci_excel.get(0).getT184());
				jxl.write.Label label185 = new jxl.write.Label(184, rowdata,
						ci_excel.get(0).getT185());
				jxl.write.Label label186 = new jxl.write.Label(185, rowdata,
						ci_excel.get(0).getT186());
				jxl.write.Label label187 = new jxl.write.Label(186, rowdata,
						ci_excel.get(0).getT187());
				jxl.write.Label label188 = new jxl.write.Label(187, rowdata,
						ci_excel.get(0).getT188());
				jxl.write.Label label189 = new jxl.write.Label(188, rowdata,
						ci_excel.get(0).getT189());
				jxl.write.Label label190 = new jxl.write.Label(189, rowdata,
						ci_excel.get(0).getT190());
				jxl.write.Label label191 = new jxl.write.Label(190, rowdata,
						ci_excel.get(0).getT191());
				jxl.write.Label label192 = new jxl.write.Label(191, rowdata,
						ci_excel.get(0).getT192());
				jxl.write.Label label193 = new jxl.write.Label(192, rowdata,
						ci_excel.get(0).getT193());
				jxl.write.Label label194 = new jxl.write.Label(193, rowdata,
						ci_excel.get(0).getT194());
				jxl.write.Label label195 = new jxl.write.Label(194, rowdata,
						ci_excel.get(0).getT195());
				jxl.write.Label label196 = new jxl.write.Label(195, rowdata,
						ci_excel.get(0).getT196());
				jxl.write.Label label197 = new jxl.write.Label(196, rowdata,
						ci_excel.get(0).getT197());
				jxl.write.Label label198 = new jxl.write.Label(197, rowdata,
						ci_excel.get(0).getT198());
				jxl.write.Label label199 = new jxl.write.Label(198, rowdata,
						ci_excel.get(0).getT199());
				jxl.write.Label label200 = new jxl.write.Label(199, rowdata,
						ci_excel.get(0).getT200());

				sheet.addCell(label1);
				sheet.addCell(label2);
				sheet.addCell(label3);
				sheet.addCell(label4);
				sheet.addCell(label5);
				sheet.addCell(label6);
				sheet.addCell(label7);
				sheet.addCell(label8);
				sheet.addCell(label9);
				sheet.addCell(label10);
				sheet.addCell(label11);
				sheet.addCell(label12);
				sheet.addCell(label13);
				sheet.addCell(label14);
				sheet.addCell(label15);
				sheet.addCell(label16);
				sheet.addCell(label17);
				sheet.addCell(label18);
				sheet.addCell(label19);
				sheet.addCell(label20);
				sheet.addCell(label21);
				sheet.addCell(label22);
				sheet.addCell(label23);
				sheet.addCell(label24);
				sheet.addCell(label25);
				sheet.addCell(label26);
				sheet.addCell(label27);
				sheet.addCell(label28);
				sheet.addCell(label29);
				sheet.addCell(label30);
				sheet.addCell(label31);
				sheet.addCell(label32);
				sheet.addCell(label33);
				sheet.addCell(label34);
				sheet.addCell(label35);
				sheet.addCell(label36);
				sheet.addCell(label37);
				sheet.addCell(label38);
				sheet.addCell(label39);
				sheet.addCell(label40);
				sheet.addCell(label41);
				sheet.addCell(label42);
				sheet.addCell(label43);
				sheet.addCell(label44);
				sheet.addCell(label45);
				sheet.addCell(label46);
				sheet.addCell(label47);
				sheet.addCell(label48);
				sheet.addCell(label49);
				sheet.addCell(label50);
				sheet.addCell(label51);
				sheet.addCell(label52);
				sheet.addCell(label53);
				sheet.addCell(label54);
				sheet.addCell(label55);
				sheet.addCell(label56);
				sheet.addCell(label57);
				sheet.addCell(label58);
				sheet.addCell(label59);
				sheet.addCell(label60);
				sheet.addCell(label61);
				sheet.addCell(label62);
				sheet.addCell(label63);
				sheet.addCell(label64);
				sheet.addCell(label65);
				sheet.addCell(label66);
				sheet.addCell(label67);
				sheet.addCell(label68);
				sheet.addCell(label69);
				sheet.addCell(label70);
				sheet.addCell(label71);
				sheet.addCell(label72);
				sheet.addCell(label73);
				sheet.addCell(label74);
				sheet.addCell(label75);
				sheet.addCell(label76);
				sheet.addCell(label77);
				sheet.addCell(label78);
				sheet.addCell(label79);
				sheet.addCell(label80);
				sheet.addCell(label81);
				sheet.addCell(label82);
				sheet.addCell(label83);
				sheet.addCell(label84);
				sheet.addCell(label85);
				sheet.addCell(label86);
				sheet.addCell(label87);
				sheet.addCell(label88);
				sheet.addCell(label89);
				sheet.addCell(label90);
				sheet.addCell(label91);
				sheet.addCell(label92);
				sheet.addCell(label93);
				sheet.addCell(label94);
				sheet.addCell(label95);
				sheet.addCell(label96);
				sheet.addCell(label97);
				sheet.addCell(label98);
				sheet.addCell(label99);
				sheet.addCell(label100);
				sheet.addCell(label101);
				sheet.addCell(label102);
				sheet.addCell(label103);
				sheet.addCell(label104);
				sheet.addCell(label105);
				sheet.addCell(label106);
				sheet.addCell(label107);
				sheet.addCell(label108);
				sheet.addCell(label109);
				sheet.addCell(label110);
				sheet.addCell(label111);
				sheet.addCell(label112);
				sheet.addCell(label113);
				sheet.addCell(label114);
				sheet.addCell(label115);
				sheet.addCell(label116);
				sheet.addCell(label117);
				sheet.addCell(label118);
				sheet.addCell(label119);
				sheet.addCell(label120);
				sheet.addCell(label121);
				sheet.addCell(label122);
				sheet.addCell(label123);
				sheet.addCell(label124);
				sheet.addCell(label125);
				sheet.addCell(label126);
				sheet.addCell(label127);
				sheet.addCell(label128);
				sheet.addCell(label129);
				sheet.addCell(label130);
				sheet.addCell(label131);
				sheet.addCell(label132);
				sheet.addCell(label133);
				sheet.addCell(label134);
				sheet.addCell(label135);
				sheet.addCell(label136);
				sheet.addCell(label137);
				sheet.addCell(label138);
				sheet.addCell(label139);
				sheet.addCell(label140);
				sheet.addCell(label141);
				sheet.addCell(label142);
				sheet.addCell(label143);
				sheet.addCell(label144);
				sheet.addCell(label145);
				sheet.addCell(label146);
				sheet.addCell(label147);
				sheet.addCell(label148);
				sheet.addCell(label149);
				sheet.addCell(label150);
				sheet.addCell(label151);
				sheet.addCell(label152);
				sheet.addCell(label153);
				sheet.addCell(label154);
				sheet.addCell(label155);
				sheet.addCell(label156);
				sheet.addCell(label157);
				sheet.addCell(label158);
				sheet.addCell(label159);
				sheet.addCell(label160);
				sheet.addCell(label161);
				sheet.addCell(label162);
				sheet.addCell(label163);
				sheet.addCell(label164);
				sheet.addCell(label165);
				sheet.addCell(label166);
				sheet.addCell(label167);
				sheet.addCell(label168);
				sheet.addCell(label169);
				sheet.addCell(label170);
				sheet.addCell(label171);
				sheet.addCell(label172);
				sheet.addCell(label173);
				sheet.addCell(label174);
				sheet.addCell(label175);
				sheet.addCell(label176);
				sheet.addCell(label177);
				sheet.addCell(label178);
				sheet.addCell(label179);
				sheet.addCell(label180);
				sheet.addCell(label181);
				sheet.addCell(label182);
				sheet.addCell(label183);
				sheet.addCell(label184);
				sheet.addCell(label185);
				sheet.addCell(label186);
				sheet.addCell(label187);
				sheet.addCell(label188);
				sheet.addCell(label189);
				sheet.addCell(label190);
				sheet.addCell(label191);
				sheet.addCell(label192);
				sheet.addCell(label193);
				sheet.addCell(label194);
				sheet.addCell(label195);
				sheet.addCell(label196);
				sheet.addCell(label197);
				sheet.addCell(label198);
				sheet.addCell(label199);
				sheet.addCell(label200);

			}

			// 写入数据
			wwb.write();
			// 关闭文件
			wwb.close();

			FileOperate.download("/OfficeFile/DownLoad/EmCommissionOut/"
					+ filename);
			// 弹出提示
			Messagebox.show("操作成功！", "操作提示",
					new Messagebox.Button[] { Messagebox.Button.OK },
					Messagebox.INFORMATION, null);
			// Executions.sendRedirect("CI_Insurant_Aut.zul");
		}
	}

	/**
	 * 新增导出Excel
	 * 
	 */
	public void Excel_Add(List<EmCommissionOutChangeModel> excelList) {
		EmCommissionOutListBll bll = new EmCommissionOutListBll();
		excel: {
			if (excelList.size() == 0) {

				EmCommissionOutChangeModel m = excelList.get(0);
				try {
					String filename = new SimpleDateFormat("yyyyMMddHHmmssSSS")
							.format(new Date())
							+ UserInfo.getUserid()
							+ "wt_out_excel.xls";
					File file = new File(new plyUtil().getAbsolutePath(
							"/../../OfficeFile/DownLoad/EmCommissionOut",
							filename, this));
					try {
						file.createNewFile();
					} catch (Exception e) {
						Messagebox.show("生成文件出错,请再次点击生成!", "ERROR",
								Messagebox.OK, Messagebox.ERROR);
						break excel;
					}

					// 读取Excel模板
					Workbook wb = Workbook
							.getWorkbook(new File(
									new plyUtil()
											.getAbsolutePath(
													"/../../OfficeFile/Templet/EmCommissionOut",
													"wt_add.xls", this)));

					// 使用模板创建
					WritableWorkbook wwb = Workbook.createWorkbook(file, wb);

					// 生成工作表,(name:First Sheet,参数0表示这是第一页)
					WritableSheet sheet = wwb.getSheet(0);

					WritableCellFormat wfCo = getFormatBorder();

					// 开始写入内容
					try {
						Label label = null;
						CellFormat cf = null;
						WritableCellFormat wcf = null;

						// 获取字体样式
						cf = sheet.getCell(0, 4).getCellFormat();
						wcf = new WritableCellFormat(cf);
						// 委托单类型
						label = new Label(0, 4, "委托合同(" + m.getEcoc_addtype()
								+ ")", wcf);
						sheet.addCell(label);

						// 获取字体样式
						cf = sheet.getCell(0, 10).getCellFormat();
						wcf = new WritableCellFormat(cf);
						// 受托单位名称..
						label = new Label(1, 5, m.getCoab_company(), wcf);
						sheet.addCell(label);

						// 委托单位项目负责人..
						label = new Label(5, 6, UserInfo.getUsername(), wcf);
						sheet.addCell(label);

						List<CoAgencyLinkmanModel> calList = new ListModelList<>();
						calList = bll.getLinkManList(m.getEcos_cabc_id());
						// 受托单位联系人..
						label = new Label(1, 6, calList.get(0).getCali_name(),
								wcf);
						sheet.addCell(label);

						// 受托单位联系人电话..
						label = new Label(1, 7, calList.get(0).getCali_tel(),
								wcf);
						sheet.addCell(label);
						// 受托单位传真号码..
						label = new Label(1, 8, calList.get(0).getCali_fax(),
								wcf);
						sheet.addCell(label);

						// 公司编号..
						label = new Label(4, 10, m.getCid().toString(), wcf);
						sheet.addCell(label);
						// 员工编号..
						label = new Label(4, 11, m.getGid().toString(), wcf);
						sheet.addCell(label);
						// 雇员姓名..
						label = new Label(1, 11, m.getEmba_name(), wcf);
						sheet.addCell(label);
						// 户籍
						// label = new Label(3, 8, m.getEcoc_domicile(), wcf);
						// sheet.addCell(label);
						// 身份证号..
						label = new Label(6, 11, m.getEcoc_idcard(), wcf);
						sheet.addCell(label);
						// 工作单位..
						label = new Label(1, 10, m.getCoba_company(), wcf);
						sheet.addCell(label);
						// 社保缴纳地
						label = new Label(6, 10, m.getCity(), wcf);
						sheet.addCell(label);
						// 工作电话..
						label = new Label(1, 12, m.getEcoc_phone(), wcf);
						sheet.addCell(label);
						// 个人手机..
						label = new Label(6, 12, m.getEcoc_mobile(), wcf);
						sheet.addCell(label);
						// Email..
						label = new Label(1, 13, m.getEcoc_email(), wcf);
						sheet.addCell(label);
						// 社保基数..
						label = new Label(1, 14, m.getEcoc_sb_base() + "", wcf);
						sheet.addCell(label);
						// 公积金基数
						label = new Label(4, 14, m.getEcoc_house_base() + "",
								wcf);
						sheet.addCell(label);

						// 档案情况..
						label = new Label(6, 15, m.getWtss_archives(), wcf);
						sheet.addCell(label);
						// 社保情况..
						label = new Label(4, 13, m.getWtss_shebaoco(), wcf);
						sheet.addCell(label);
						// 住房情况..
						label = new Label(6, 13, m.getWtss_gjjco(), wcf);
						sheet.addCell(label);
						// 用工情况..
						label = new Label(6, 14, m.getWtss_employment(), wcf);
						sheet.addCell(label);
						// 劳动合同版本..
						label = new Label(1, 16, m.getWtss_laborcontractbb(),
								wcf);
						sheet.addCell(label);
						// 劳动合同签订方..
						label = new Label(4, 16, m.getWtss_laborcontract(), wcf);
						sheet.addCell(label);

						// 获取字体样式
						cf = sheet.getCell(2, 17).getCellFormat();
						wcf = new WritableCellFormat(cf);
						// 服务项目小计
						BigDecimal fwsum = new BigDecimal(0);
						int row = 0;
						int row1 = 28;
						String fuwu_fee = "";
						BigDecimal jl_sum = new BigDecimal(0);
						String jl_cp = "";
						String jl_op = "";
						for (int i = 0; i < feeList.size(); i++) {
							EmCommissionOutFeeDetailChangeModel m1 = feeList
									.get(i);

							if (m1.getSicl_class().equals("社会保险")
									|| m1.getSicl_class().equals("公积金")) {
								switch (m1.getEofc_name()) {
								case "养老保险":
									row = 18;
									break;
								case "失业保险":
									row = 19;
									break;
								case "工伤保险":
									row = 20;
									break;
								case "医疗保险":
									row = 21;
									break;
								case "生育保险":
									row = 22;
									break;
								case "住房公积金":
									row = 23;
									break;
								case "补充公积金":
									row = 24;
									break;

								default:
									break;
								}
								if (m1.getEofc_name().equals("补充公积金")) {
									// 企业缴费基数
									label = new Label(2, row,
											m1.getEofc_co_base() + "", wfCo);
									sheet.addCell(label);
									// 个人缴费基数
									label = new Label(3, row,
											m1.getEofc_em_base() + "", wfCo);
									sheet.addCell(label);
									if (m1.getEofc_name().equals("补充公积金")) {

										// 企业比例、个人比例
										label = new Label(4, row,
												m1.getEofc_op(), wfCo);
										sheet.addCell(label);

									} else {
										// 企业比例
										label = new Label(4, row,
												m1.getEofc_op(), wfCo);
										sheet.addCell(label);
										// 个人比例
										label = new Label(5, row,
												m1.getEofc_cp(), wfCo);
										sheet.addCell(label);
									}
									// 月缴费
									label = new Label(6, row,
											m1.getEofc_month_sum() + "", wfCo);
									sheet.addCell(label);
									// 委托起始日
									label = new Label(7, row,
											m1.getEofc_start_date(), wfCo);
									sheet.addCell(label);

									if (m1.getEofc_name().equals("医疗保险")) {
										jl_sum = m1.getEofc_month_sum();
										jl_cp = m1.getEofc_cp();
										jl_op = m1.getEofc_op();
									}

									if (m1.getEofc_name().equals("大病医疗")) {
										jl_sum = jl_sum.add(m1
												.getEofc_month_sum());
										// 月缴费
										label = new Label(6, row,
												jl_sum.toString(), wfCo);
										sheet.addCell(label);
										label = new Label(4, row, jl_op, wfCo);
										sheet.addCell(label);
										label = new Label(5, row, jl_cp, wfCo);
										sheet.addCell(label);
									}

									fwsum = fwsum.add(m1.getEofc_month_sum());
								} else if (m1.getSicl_class().equals("服务费")) {
									// 服务费
									// label = new Label(5, 30,
									// m1.getEofc_month_sum()
									// + "", wfCo);
									// sheet.addCell(label);
									fuwu_fee = m1.getEofc_month_sum()
											.toString();
								} else if (m1.getSicl_class().equals("档案费")) {
								} else {
									if (m1.getEofc_start_date() != null) {
										sheet.insertRow(row1);
										sheet.mergeCells(1, row1, 5, row1);

										if (m1.getEofc_name().equals("服务费")) {
											fuwu_fee = m1.getEofc_month_sum()
													.toString();
										}

										// 福利项目
										label = new Label(0, row1,
												m1.getEofc_name(), sheet
														.getCell(0, 22)
														.getCellFormat());
										sheet.addCell(label);
										// 福利内容
										label = new Label(1, row1,
												m1.getEofc_content(),
												new WritableCellFormat(sheet
														.getCell(1, 22)
														.getCellFormat()));
										sheet.addCell(label);
										// 福利费用
										label = new Label(6, row1,
												m1.getEofc_month_sum() + "",
												wfCo);
										sheet.addCell(label);
										// 福利时间
										label = new Label(7, row1,
												m1.getEofc_start_date() + "",
												wfCo);
										sheet.addCell(label);

										row1++;
									}
								}
							}

							row1 += 2;

							// 小计
							label = new Label(5, 24, fwsum + "", wcf);
							sheet.addCell(label);

							// 社保合计
							label = new Label(0, row1, m.getEcoc_sb_sum() + "",
									wcf);
							sheet.addCell(label);
							// 公积金合计
							label = new Label(1, row1,
									m.getEcoc_gjj_sum() + "", wcf);
							sheet.addCell(label);
							// 档案费
							label = new Label(2, row1, m.getEcoc_file_fee()
									+ "", wcf);
							sheet.addCell(label);
							// 福利合计
							label = new Label(3, row1, m.getEcoc_welfare_sum()
									+ "", wcf);
							sheet.addCell(label);
							// 服务费
							label = new Label(5, row1, fuwu_fee, wcf);
							sheet.addCell(label);
							// 合计
							label = new Label(6, row1, m.getEcoc_sum() + "",
									wcf);
							sheet.addCell(label);
						}
					} catch (Exception e) {
						e.printStackTrace();
						Messagebox.show("写入内容失败!", "ERROR", Messagebox.OK,
								Messagebox.ERROR);
						break excel;
					}

					// 写入数据
					wwb.write();
					// 关闭文件
					wwb.close();

					try {
						FileOperate
								.download("OfficeFile/DownLoad/EmCommissionOut/"
										+ filename);
					} catch (Exception e) {
						e.printStackTrace();
						Messagebox.show("获取下载文件失败!", "ERROR", Messagebox.OK,
								Messagebox.ERROR);
						break excel;
					}

				} catch (Exception e) {
					e.printStackTrace();
					Messagebox.show("导出出错!", "ERROR", Messagebox.OK,
							Messagebox.ERROR);
				}
			} else if (excelList.size() > 0) {
				try {
					String filename = new SimpleDateFormat("yyyyMMddHHmmssSSS")
							.format(new Date())
							+ UserInfo.getUserid()
							+ "wt_out_all_excel.xls";
					File file = new File(new plyUtil().getAbsolutePath(
							"/../../OfficeFile/DownLoad/EmCommissionOut",
							filename, this));
					try {
						file.createNewFile();
					} catch (Exception e) {
						Messagebox.show("生成文件出错,请再次点击生成!", "ERROR",
								Messagebox.OK, Messagebox.ERROR);
						break excel;
					}

					// 读取Excel模板
					Workbook wb = Workbook
							.getWorkbook(new File(
									new plyUtil()
											.getAbsolutePath(
													"/../../OfficeFile/Templet/EmCommissionOut",
													"wt_out_all.xls", this)));

					// 使用模板创建
					WritableWorkbook wwb = Workbook.createWorkbook(file, wb);

					// 生成工作表,(name:First Sheet,参数0表示这是第一页)
					WritableSheet sheet = wwb.getSheet(0);

					WritableCellFormat wcf = null;
					Label label = null;
					int row = 12;

					for (int i = 0; i < excelList.size(); i++) {
						EmCommissionOutChangeModel m = excelList.get(i);

						if (m.getEcoc_addtype().equals("新增")
								|| m.getEcoc_addtype().equals("调整")) {

							// 插入一行
							sheet.insertRow(row);

							// 序号
							wcf = new WritableCellFormat(sheet.getCell(0,
									row + 1).getCellFormat());
							label = new Label(0, row, i + 1 + "", wcf);
							sheet.addCell(label);

							// 公司名称
							sheet.mergeCells(1, row, 2, row);
							wcf = new WritableCellFormat(sheet.getCell(1,
									row + 1).getCellFormat());
							label = new Label(1, row, m.getCoba_shortname(),
									wcf);
							sheet.addCell(label);

							// 员工姓名
							wcf = new WritableCellFormat(sheet.getCell(3,
									row + 1).getCellFormat());
							label = new Label(3, row, m.getEmba_name(), wcf);
							sheet.addCell(label);

							// 身份证号码
							// sheet.mergeCells(4, row, 4, row);
							wcf = new WritableCellFormat(sheet.getCell(2,
									row + 1).getCellFormat());
							label = new Label(4, row, m.getEcoc_idcard(), wcf);
							sheet.addCell(label);

							// 联系电话
							// sheet.mergeCells(4, row, 5, row);
							wcf = new WritableCellFormat(sheet.getCell(5,
									row + 1).getCellFormat());
							label = new Label(5, row, m.getEcoc_phone() + "/"
									+ m.getEcoc_mobile(), wcf);
							sheet.addCell(label);

							// 户籍
							// sheet.mergeCells(6, row, 7, row);
							// wcf = new WritableCellFormat(sheet.getCell(6,
							// row + 1).getCellFormat());
							// label = new Label(6, row, m.getEcoc_domicile(),
							// wcf);
							// sheet.addCell(label);

							// 受托地
							wcf = new WritableCellFormat(sheet.getCell(6,
									row + 1).getCellFormat());
							label = new Label(6, row, m.getCity(), wcf);
							sheet.addCell(label);

							String sb_incept_date = "";
							String house_incept_date = "";
							setFeeList(bll
									.getFeeDetailChangeList(" and eofc_ecoc_id="
											+ m.getEcoc_id()));

							for (EmCommissionOutFeeDetailChangeModel m1 : feeList) {
								if (m1.getEofc_start_date() != null) {
									if (m1.getSicl_class().equals("社会保险")
											&& sb_incept_date.isEmpty()) {
										sb_incept_date = m1
												.getEofc_start_date();
									} else if (m1.getSicl_class().equals("公积金")
											&& house_incept_date.isEmpty()) {
										house_incept_date = m1
												.getEofc_start_date();
									}
								}
							}

							// 社保委托起始日
							wcf = new WritableCellFormat(sheet.getCell(7,
									row + 1).getCellFormat());
							label = new Label(7, row, sb_incept_date, wcf);
							sheet.addCell(label);

							// 住房委托起始日
							wcf = new WritableCellFormat(sheet.getCell(12,
									row + 1).getCellFormat());
							label = new Label(12, row, house_incept_date, wcf);
							sheet.addCell(label);

							// 社保基数
							wcf = new WritableCellFormat(sheet.getCell(8,
									row + 1).getCellFormat());
							label = new Label(8, row, m.getEcoc_sb_base() + "",
									wcf);
							sheet.addCell(label);

							// 公司社保费
							wcf = new WritableCellFormat(sheet.getCell(9,
									row + 1).getCellFormat());
							label = new Label(9, row, m.getEcoc_sb_co_sum()
									+ "", wcf);
							sheet.addCell(label);

							// 个人社保费
							wcf = new WritableCellFormat(sheet.getCell(10,
									row + 1).getCellFormat());
							label = new Label(10, row, m.getEcoc_sb_em_sum()
									+ "", wcf);
							sheet.addCell(label);

							// 合计社保费
							wcf = new WritableCellFormat(sheet.getCell(11,
									row + 1).getCellFormat());
							label = new Label(11, row, m.getEcoc_sb_sum() + "",
									wcf);
							sheet.addCell(label);

							// 住房基数
							wcf = new WritableCellFormat(sheet.getCell(13,
									row + 1).getCellFormat());
							label = new Label(13, row, m.getEcoc_house_base()
									+ "", wcf);
							sheet.addCell(label);

							// 商保
							wcf = new WritableCellFormat(sheet.getCell(18,
									row + 1).getCellFormat());
							label = new Label(18, row, "0.00", wcf);
							sheet.addCell(label);

							// 体检
							wcf = new WritableCellFormat(sheet.getCell(17,
									row + 1).getCellFormat());
							label = new Label(17, row, "0.00", wcf);
							sheet.addCell(label);

							// 其它时间
							wcf = new WritableCellFormat(sheet.getCell(19,
									row + 1).getCellFormat());
							label = new Label(19, row, "", wcf);
							sheet.addCell(label);

							// 总计
							wcf = new WritableCellFormat(sheet.getCell(22,
									row + 1).getCellFormat());
							label = new Label(22, row, m.getEcoc_sum() + "",
									wcf);
							sheet.addCell(label);

							// 备注
							wcf = new WritableCellFormat(sheet.getCell(23,
									row + 1).getCellFormat());
							label = new Label(23, row, m.getEcoc_remark() + "",
									wcf);
							sheet.addCell(label);

							for (EmCommissionOutFeeDetailChangeModel m1 : feeList) {
								switch (m1.getEofc_name()) {
								/*
								 * case "养老保险": label = new Label(2, row,
								 * m1.getEofc_em_base() + "", wcf);
								 * sheet.addCell(label); break; case "失业保险":
								 * label = new Label(3, row,
								 * m1.getEofc_em_base() + "", wcf);
								 * sheet.addCell(label); break; case "工伤保险":
								 * label = new Label(4, row,
								 * m1.getEofc_em_base() + "", wcf);
								 * sheet.addCell(label); break; case "医疗保险":
								 * label = new Label(5, row,
								 * m1.getEofc_em_base() + "", wcf);
								 * sheet.addCell(label); break; case "生育保险":
								 * label = new Label(6, row,
								 * m1.getEofc_em_base() + "", wcf);
								 * sheet.addCell(label); break;
								 */
								case "住房公积金":

									label = new Label(15, row, m1.getEofc_op(),
											wcf);
									sheet.addCell(label);
									label = new Label(14, row, m1.getEofc_cp(),
											wcf);
									sheet.addCell(label);
									label = new Label(16, row,
											m.getEcoc_gjj_sum() + "", wcf);
									sheet.addCell(label);
									break;
								default:
									break;
								}
							}

							// 补充福利
							// label = new Label(11, row,
							// m.getEcoc_welfare_sum()
							// + "", wcf);
							// sheet.addCell(label);
							// 服务费
							label = new Label(21, row, m.getEcoc_service_fee()
									+ "", wcf);
							sheet.addCell(label);
							// 档案费
							label = new Label(20, row, m.getEcoc_file_fee()
									+ "", wcf);
							sheet.addCell(label);
							// 其他
							// label = new Label(14, row, "", wcf);
							// sheet.addCell(label);
							// 备注
							label = new Label(22, row, m.getEcoc_remark(), wcf);
							sheet.addCell(label);

							row++;
						}
					}
					sheet.removeRow(row);

					EmCommissionOutChangeModel m = excelList.get(0);

					// 获取委托机构联系人信息
					List<CoAgencyLinkmanModel> calList = new ListModelList<>();
					calList = bll.getLinkManList(m.getEcos_cabc_id());
					System.out.println("xxxx");
					System.out.println(m.getEcos_cabc_id());
					System.out.println(calList.get(0).getCali_name());
					System.out.println(m.getCoab_name());
					// 委托机构
					wcf = new WritableCellFormat(sheet.getCell(2, 3)
							.getCellFormat());

					label = new Label(1, 3, m.getCoab_company(), wcf);
					sheet.addCell(label);

					// 联系人姓名
					label = new Label(1, 4, calList.get(0).getCali_name(), wcf);
					sheet.addCell(label);

					// 传真
					// wcf = new WritableCellFormat(sheet.getCell(9, 5)
					// .getCellFormat());
					label = new Label(1, 6, calList.get(0).getCali_fax(), wcf);
					sheet.addCell(label);

					// 电话
					// wcf = new WritableCellFormat(sheet.getCell(9, row - 1)
					// .getCellFormat());
					label = new Label(1, 5, calList.get(0).getCali_tel(), wcf);
					sheet.addCell(label);

					// 写入数据
					wwb.write();
					// 关闭文件
					wwb.close();

					try {
						FileOperate
								.download("OfficeFile/DownLoad/EmCommissionOut/"
										+ filename);
					} catch (Exception e) {
						e.printStackTrace();
						Messagebox.show("获取下载文件失败!", "ERROR", Messagebox.OK,
								Messagebox.ERROR);
						break excel;
					}
				} catch (Exception e) {
					e.printStackTrace();
					Messagebox.show("导出出错!", "ERROR", Messagebox.OK,
							Messagebox.ERROR);
				}
			}
		}
	}

	/**
	 * 新增导出Excel
	 * 
	 */
	public void Excel_End(List<EmCommissionOutChangeModel> excelList) {
		EmCommissionOutListBll bll = new EmCommissionOutListBll();
		excel: {
			if (excelList.size() == 0) {

				try {
					EmCommissionOutChangeModel m = excelList.get(0);
					System.out.println("ss");
					System.out.println(m.getCoab_name());
					String filename = new SimpleDateFormat("yyyyMMddHHmmssSSS")
							.format(new Date())
							+ UserInfo.getUserid()
							+ "wt_out_excelter.xls";
					File file = new File(new plyUtil().getAbsolutePath(
							"/../../OfficeFile/DownLoad/EmCommissionOut",
							filename, this));
					try {
						file.createNewFile();
					} catch (Exception e) {
						Messagebox.show("生成文件出错,请再次点击生成!", "ERROR",
								Messagebox.OK, Messagebox.ERROR);
						break excel;
					}

					// 读取Excel模板
					Workbook wb = Workbook
							.getWorkbook(new File(
									new plyUtil()
											.getAbsolutePath(
													"/../../OfficeFile/Templet/EmCommissionOut",
													"wt_dis.xls", this)));

					// 使用模板创建
					WritableWorkbook wwb = Workbook.createWorkbook(file, wb);

					// 生成工作表,(name:First Sheet,参数0表示这是第一页)
					WritableSheet sheet = wwb.getSheet(0);

					WritableCellFormat wfCo = getFormatBorder();

					// 开始写入内容
					try {
						Label label = null;
						CellFormat cf = null;
						WritableCellFormat wcf = null;

						// 获取字体样式
						cf = sheet.getCell(0, 5).getCellFormat();

						List<CoAgencyLinkmanModel> calList = new ListModelList<>();
						calList = bll.getLinkManList(m.getEcos_cabc_id());

						// 获取字体样式
						wfCo = new WritableCellFormat(cf);
						// 受托单位名称..
						label = new Label(1, 5, m.getCoab_company(), wfCo);
						sheet.addCell(label);
						// 受托单位传真号码..
						label = new Label(1, 8, calList.get(0).getCali_fax(),
								wfCo);
						sheet.addCell(label);
						// 受托单位经办人..
						label = new Label(1, 6, calList.get(0).getCali_name(),
								wfCo);
						sheet.addCell(label);

						// 离职时间
						label = new Label(2, 14, m.getEcoc_stop_date()
								.toString(), wfCo);
						sheet.addCell(label);

						// 离职原因
						label = new Label(5, 14, m.getEcoc_stop_cause(), wfCo);
						sheet.addCell(label);

						// 委托单位项目负责人..
						label = new Label(4, 6, UserInfo.getUsername(), wfCo);
						sheet.addCell(label);

						// 委托日期..
						// label = new Label(3, 5, new
						// SimpleDateFormat("yyyy-MM-dd").format(new Date()),
						// wcf);
						// sheet.addCell(label);
						// 工作单位..

						// 获取字体样式
						cf = sheet.getCell(0, 12).getCellFormat();
						wfCo = new WritableCellFormat(cf);

						// 受托城市..
						label = new Label(6, 12, m.getCity(), wfCo);
						sheet.addCell(label);

						label = new Label(2, 12, m.getCoba_company(), wfCo);
						sheet.addCell(label);
						// 雇员姓名..
						label = new Label(2, 13, m.getEmba_name(), wfCo);
						sheet.addCell(label);
						// 身份证号..
						label = new Label(5, 13, m.getEcoc_idcard(), wfCo);
						sheet.addCell(label);

						for (int i = 0; i < feeList.size(); i++) {
							if (feeList.get(i).getEofc_name().equals("养老保险")) {
								// 养老终止时间..
								label = new Label(2, 16, feeList.get(i)
										.getEofc_stop_date(), wfCo);
								sheet.addCell(label);
							}

							if (feeList.get(i).getEofc_name().equals("医疗保险")) {
								// 养老终止时间..
								label = new Label(2, 17, feeList.get(i)
										.getEofc_stop_date(), wfCo);
								sheet.addCell(label);
							}

							if (feeList.get(i).getEofc_name().equals("生育保险")) {
								// 养老终止时间..
								label = new Label(2, 18, feeList.get(i)
										.getEofc_stop_date(), wfCo);
								sheet.addCell(label);
							}

							if (feeList.get(i).getEofc_name().equals("工伤保险")) {
								// 养老终止时间..
								label = new Label(5, 17, feeList.get(i)
										.getEofc_stop_date(), wfCo);
								sheet.addCell(label);
							}

							if (feeList.get(i).getEofc_name().equals("失业保险")) {
								// 养老终止时间..
								label = new Label(5, 16, feeList.get(i)
										.getEofc_stop_date(), wfCo);
								sheet.addCell(label);
							}

							if (feeList.get(i).getEofc_name().equals("住房公积金")) {
								// 养老终止时间..
								label = new Label(2, 19, feeList.get(i)
										.getEofc_stop_date(), wfCo);
								sheet.addCell(label);
							}

							if (feeList.get(i).getEofc_name().equals("补充公积金")) {
								// 养老终止时间..
								label = new Label(5, 19, feeList.get(i)
										.getEofc_stop_date(), wfCo);
								sheet.addCell(label);
							}

							if (feeList.get(i).getEofc_name().equals("服务费")) {
								// 养老终止时间..
								label = new Label(2, 20, feeList.get(i)
										.getEofc_stop_date(), wfCo);
								sheet.addCell(label);
							}

						}

					} catch (Exception e) {
						e.printStackTrace();
						Messagebox.show("写入内容失败!", "ERROR", Messagebox.OK,
								Messagebox.ERROR);
						break excel;
					}

					// 写入数据
					wwb.write();
					// 关闭文件
					wwb.close();

					try {
						FileOperate
								.download("OfficeFile/DownLoad/EmCommissionOut/"
										+ filename);
					} catch (Exception e) {
						e.printStackTrace();
						Messagebox.show("获取下载文件失败!", "ERROR", Messagebox.OK,
								Messagebox.ERROR);
						break excel;
					}

				} catch (Exception e) {
					e.printStackTrace();
					Messagebox.show("导出出错!", "ERROR", Messagebox.OK,
							Messagebox.ERROR);
				}

			} else if (excelList.size() > 0) {
				try {
					String filename = new SimpleDateFormat("yyyyMMddHHmmssSSS")
							.format(new Date())
							+ UserInfo.getUserid()
							+ "wt_out_all_excel.xls";
					File file = new File(new plyUtil().getAbsolutePath(
							"/../../OfficeFile/DownLoad/EmCommissionOut",
							filename, this));
					try {
						file.createNewFile();
					} catch (Exception e) {
						Messagebox.show("生成文件出错,请再次点击生成!", "ERROR",
								Messagebox.OK, Messagebox.ERROR);
						break excel;
					}

					// 读取Excel模板
					Workbook wb = Workbook
							.getWorkbook(new File(
									new plyUtil()
											.getAbsolutePath(
													"/../../OfficeFile/Templet/EmCommissionOut",
													"wt_dis_all.xls", this)));

					// 使用模板创建
					WritableWorkbook wwb = Workbook.createWorkbook(file, wb);

					// 生成工作表,(name:First Sheet,参数0表示这是第一页)
					WritableSheet sheet = wwb.getSheet(0);

					WritableCellFormat wfCo = getFormatBorder();

					WritableCellFormat wcf = null;
					WritableCellFormat wcfin = null;
					Label label = null;
					int row = 12;

					CellFormat cf = null;

					cf = sheet.getCell(0, 12).getCellFormat();
					wfCo = new WritableCellFormat(cf);

					EmCommissionOutChangeModel mj = excelList.get(0);

					List<CoAgencyLinkmanModel> calList = new ListModelList<>();
					calList = bll.getLinkManList(mj.getEcos_cabc_id());

					// 机构
					wcfin = new WritableCellFormat(sheet.getCell(2, 3)
							.getCellFormat());
					label = new Label(2, 3, mj.getCoba_company() + "", wcfin);
					sheet.addCell(label);

					// 受托单位传真号码..
					label = new Label(2, 6, calList.get(0).getCali_fax(), wcfin);
					sheet.addCell(label);
					// 受托单位经办人..
					label = new Label(2, 4, calList.get(0).getCali_name(),
							wcfin);
					sheet.addCell(label);

					for (int i = 0; i < excelList.size(); i++) {
						EmCommissionOutChangeModel m = excelList.get(i);

						if (m.getEcoc_addtype().equals("离职")) {

							// 插入一行
							sheet.insertRow(row);

							// 序号
							wcf = new WritableCellFormat(sheet.getCell(0,
									row + 1).getCellFormat());
							label = new Label(0, row, i + 1 + "", wcf);
							sheet.addCell(label);

							// 公司名称
							sheet.mergeCells(1, row, 2, row);
							wcf = new WritableCellFormat(sheet.getCell(1,
									row + 1).getCellFormat());
							label = new Label(1, row, m.getCoba_company() + "",
									wcf);
							sheet.addCell(label);

							// 员工姓名
							wcf = new WritableCellFormat(sheet.getCell(3,
									row + 1).getCellFormat());
							label = new Label(3, row, m.getEmba_name() + "",
									wcf);
							sheet.addCell(label);

							// 身份证号码
							wcf = new WritableCellFormat(sheet.getCell(4,
									row + 1).getCellFormat());
							label = new Label(4, row, m.getEcoc_idcard() + "",
									wcf);
							sheet.addCell(label);

							// 离职时间
							wcf = new WritableCellFormat(sheet.getCell(6,
									row + 1).getCellFormat());
							label = new Label(6, row, m.getEcoc_stop_date()
									+ "", wcf);
							sheet.addCell(label);

							// 离职原因
							wcf = new WritableCellFormat(sheet.getCell(7,
									row + 1).getCellFormat());
							label = new Label(7, row, m.getEcoc_stop_cause()
									+ "", wcf);
							sheet.addCell(label);

							// 离职原因
							wcf = new WritableCellFormat(sheet.getCell(7,
									row + 1).getCellFormat());
							label = new Label(11, row, "", wcf);
							sheet.addCell(label);

							// 联系电话
							// sheet.mergeCells(4, row, 5, row);
							// wcf = new WritableCellFormat(sheet.getCell(4,
							// row + 1).getCellFormat());
							// label = new Label(4, row, m.getEcoc_phone(),
							// wcf);
							// sheet.addCell(label);

							// 户籍
							// sheet.mergeCells(6, row, 7, row);
							// wcf = new WritableCellFormat(sheet.getCell(6,
							// row + 1).getCellFormat());
							// label = new Label(6, row, m.getEcoc_domicile(),
							// wcf);
							// sheet.addCell(label);

							// 受托地
							wcf = new WritableCellFormat(sheet.getCell(5,
									row + 1).getCellFormat());
							label = new Label(5, row, m.getCity(), wcf);
							sheet.addCell(label);

							// 档案状态
							// wcf = new WritableCellFormat(sheet.getCell(9,
							// row + 1).getCellFormat());
							// label = new Label(9, row, m.getEcoc_file_fee()
							// .compareTo(BigDecimal.ZERO) == 0 ? "否"
							// : "是", wcf);
							// sheet.addCell(label);

							String sb_incept_date = "";
							String house_incept_date = "";
							setFeeList(bll
									.getFeeDetailChangeList(" and eofc_ecoc_id="
											+ m.getEcoc_id()));

							for (EmCommissionOutFeeDetailChangeModel m1 : feeList) {
								if (m1.getEofc_stop_date() != null) {
									if (m1.getSicl_class().equals("社会保险")
											&& sb_incept_date.isEmpty()) {
										sb_incept_date = m1.getEofc_stop_date();
									} else if (m1.getSicl_class().equals("公积金")
											&& house_incept_date.isEmpty()) {
										house_incept_date = m1
												.getEofc_stop_date();
									}
								}
							}

							// 社保终止时间
							// wcf = new WritableCellFormat(sheet.getCell(6,
							// row + 1).getCellFormat());
							label = new Label(8, row, sb_incept_date, wcf);
							sheet.addCell(label);

							// 住房委托起始日
							// wcf = new WritableCellFormat(sheet.getCell(8,
							// row + 1).getCellFormat());
							label = new Label(9, row, house_incept_date, wcf);
							sheet.addCell(label);

							// 其它费用
							label = new Label(10, row, m.getEcoc_welfare_sum()
									+ "", wcf);
							sheet.addCell(label);

							/*
							 * for (EmCommissionOutFeeDetailChangeModel m1 :
							 * feeList) { switch (m1.getEofc_name()) { case
							 * "养老保险": label = new Label(10, row,
							 * m1.getEofc_stop_date() + "", wfCo);
							 * sheet.addCell(label); break; case "失业保险": label =
							 * new Label(11, row, m1.getEofc_stop_date() + "",
							 * wfCo); sheet.addCell(label); break; case "工伤保险":
							 * label = new Label(12, row, m1.getEofc_stop_date()
							 * + "", wfCo); sheet.addCell(label); break; case
							 * "医疗保险": label = new Label(13, row,
							 * m1.getEofc_stop_date() + "", wfCo);
							 * sheet.addCell(label); break; case "生育保险": label =
							 * new Label(14, row, m1.getEofc_stop_date() + "",
							 * wfCo); sheet.addCell(label); break; case "住房公积金":
							 * label = new Label(15, row, m1.getEofc_stop_date()
							 * + "", wfCo); sheet.addCell(label); break;
							 * default: break; } }
							 */
							// 补充福利
							// label = new Label(19, row,
							// m.getEcoc_welfare_sum()
							// + "", wcf);
							// sheet.addCell(label);
							// 服务费
							// label = new Label(20, row,
							// m.getEcoc_service_fee()
							// + "", wcf);
							// sheet.addCell(label);
							// 档案费
							// label = new Label(21, row, m.getEcoc_file_fee()
							// + "", wcf);
							// sheet.addCell(label);
							// 其他
							// label = new Label(22, row, "", wcf);
							// sheet.addCell(label);
							// 备注
							// label = new Label(23, row, m.getEcoc_remark(),
							// wcf);
							// sheet.addCell(label);

							row++;
							// row += 2;
						}
					}
					// sheet.removeRow(row);
					// sheet.removeRow(row - 2);

					// EmCommissionOutChangeModel m = excelList.get(0);

					// 获取委托机构联系人信息
					// List<CoAgencyLinkmanModel> calList = new
					// ListModelList<>();
					// calList = bll.getLinkManList(m.getEcos_cabc_id());

					// 委托机构
					// wcf = new WritableCellFormat(sheet.getCell(1, 5)
					// .getCellFormat());
					// label = new Label(1, 6, m.getCoab_name(), wcf);
					// sheet.addCell(label);

					// 联系人姓名
					// label = new Label(6, 6, calList.get(0).getCali_name(),
					// wcf);
					// sheet.addCell(label);

					// 传真
					// wcf = new WritableCellFormat(sheet.getCell(9, 5)
					// .getCellFormat());
					// label = new Label(9, 6, calList.get(0).getCali_fax(),
					// wcf);
					// sheet.addCell(label);

					// 公司名称
					// wcf = new WritableCellFormat(sheet.getCell(0, 10)
					// .getCellFormat());
					// label = new Label(1, 10, m.getCoba_company(), wcf);
					// sheet.addCell(label);

					// 委托出日期
					// wcf = new WritableCellFormat(sheet.getCell(7, 10)
					// .getCellFormat());
					// label = new Label(8, 10, m.getEcoc_title_date(), wcf);
					// sheet.addCell(label);

					// row = row + 14;

					// 委托机构
					// wcf = new WritableCellFormat(sheet.getCell(1, row - 1)
					// .getCellFormat());
					// label = new Label(1, row, m.getCoab_name(), wcf);
					// sheet.addCell(label);

					// 联系人姓名
					// label = new Label(6, row, calList.get(0).getCali_name(),
					// wcf);
					// sheet.addCell(label);

					// 传真
					// wcf = new WritableCellFormat(sheet.getCell(9, row - 1)
					// .getCellFormat());
					// label = new Label(9, row, calList.get(0).getCali_fax(),
					// wcf);
					// sheet.addCell(label);

					// 公司名称
					// wcf = new WritableCellFormat(sheet.getCell(0, row + 4)
					// .getCellFormat());
					// label = new Label(1, row + 4, m.getCoba_company(), wcf);
					// sheet.addCell(label);

					row = row - 1;

					// 委托单位联系人
					// wcf = new WritableCellFormat(sheet.getCell(0, row)
					// .getCellFormat());
					// label = new Label(2, row, calList.get(0).getCali_name(),
					// wcf);
					// sheet.addCell(label);

					// 电话
					// wcf = new WritableCellFormat(sheet.getCell(9, row - 1)
					// .getCellFormat());
					// label = new Label(9, row, calList.get(0).getCali_tel(),
					// wcf);
					// sheet.addCell(label);

					// 传真
					// label = new Label(9, row + 1,
					// calList.get(0).getCali_fax(), wcf);
					// sheet.addCell(label);

					// 写入数据
					wwb.write();
					// 关闭文件
					wwb.close();

					try {
						FileOperate
								.download("OfficeFile/DownLoad/EmCommissionOut/"
										+ filename);
					} catch (Exception e) {
						e.printStackTrace();
						Messagebox.show("获取下载文件失败!", "ERROR", Messagebox.OK,
								Messagebox.ERROR);
						break excel;
					}
				} catch (Exception e) {
					e.printStackTrace();
					Messagebox.show("导出出错!", "ERROR", Messagebox.OK,
							Messagebox.ERROR);
				}
			}
		}
	}

	/**
	 * 批量退回
	 * 
	 * @param set
	 */
	@Command("allback")
	public void allback(@BindingParam("set") Set<Listitem> set) {
		if (set.size() <= 0) {
			Messagebox.show("请至少选择一个员工!", "ERROR", Messagebox.OK,
					Messagebox.ERROR);
		} else {

			// 获取set中第一项委托单类型
			String addtypeString = ((EmCommissionOutChangeModel) set.iterator()
					.next().getValue()).getEcoc_addtype();

			// 按照委托单类型筛选数据，并装入excelList中
			List<EmCommissionOutChangeModel> excelList = new ListModelList<>();
			String allecoc_id = "";
			String tapr_id = "";
			String allecou_id = "";
			int i = 0;

			Map arg = new HashMap();
			// arg.put("daid", emco.getEbco_id());
			arg.put("set1", set);
			Window wnd = (Window) Executions.createComponents(
					"EmCommissionOut_hdOperateListBack.zul", null, arg);
			wnd.doModal();
		}
	}

	// 居中带边框
	private WritableCellFormat getFormatBorder() {
		WritableFont wf = new WritableFont(WritableFont.createFont("宋体"), 10);
		WritableCellFormat wc = new WritableCellFormat(wf);
		try {
			wc.setAlignment(Alignment.CENTRE);
			wc.setVerticalAlignment(VerticalAlignment.CENTRE);
			wc.setBorder(Border.ALL, BorderLineStyle.THIN);
		} catch (WriteException e) {
			e.printStackTrace();
		}
		return wc;
	}

	public List<EmCommissionOutChangeModel> getEcocList() {
		return ecocList;
	}

	public void setEcocList(List<EmCommissionOutChangeModel> ecocList) {
		this.ecocList = ecocList;
	}

	public List<EmCommissionOutChangeModel> getSecocList() {
		return secocList;
	}

	public void setSecocList(List<EmCommissionOutChangeModel> secocList) {
		this.secocList = secocList;
	}

	public List<CoAgencyBaseCityRelViewModel> getCityList() {
		return cityList;
	}

	public void setCityList(List<CoAgencyBaseCityRelViewModel> cityList) {
		this.cityList = cityList;
	}

	public List<CoAgencyBaseCityRelViewModel> getCoabList() {
		return coabList;
	}

	public void setCoabList(List<CoAgencyBaseCityRelViewModel> coabList) {
		this.coabList = coabList;
	}

	public List<EmCommissionOutChangeModel> getClientList() {
		return clientList;
	}

	public void setClientList(List<EmCommissionOutChangeModel> clientList) {
		this.clientList = clientList;
	}

	public List<PubStateModel> getStateList() {
		return stateList;
	}

	public void setStateList(List<PubStateModel> stateList) {
		this.stateList = stateList;
	}

	public String getCid() {
		return cid;
	}

	public void setCid(String cid) {
		this.cid = cid;
	}

	public String getShortname() {
		return shortname;
	}

	public void setShortname(String shortname) {
		this.shortname = shortname;
	}

	public String getGid() {
		return gid;
	}

	public void setGid(String gid) {
		this.gid = gid;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getCoabname() {
		return coabname;
	}

	public void setCoabname(String coabname) {
		this.coabname = coabname;
	}

	public String getClient() {
		return client;
	}

	public void setClient(String client) {
		this.client = client;
	}

	public String getStatename() {
		return statename;
	}

	public void setStatename(String statename) {
		this.statename = statename;
	}

	public boolean isMu() {
		return mu;
	}

	public void setMu(boolean mu) {
		this.mu = mu;
	}

	public String getAddtype() {
		return addtype;
	}

	public void setAddtype(String addtype) {
		this.addtype = addtype;
	}

	public List<EmCommissionOutChangeModel> getCountList() {
		return countList;
	}

	public void setCountList(List<EmCommissionOutChangeModel> countList) {
		this.countList = countList;
	}

	public boolean isIfupdates() {
		return ifupdates;
	}

	public void setIfupdates(boolean ifupdates) {
		this.ifupdates = ifupdates;
	}

	public boolean isIfexcel() {
		return ifexcel;
	}

	public void setIfexcel(boolean ifexcel) {
		this.ifexcel = ifexcel;
	}

	public String getSointitle() {
		return sointitle;
	}

	public void setSointitle(String sointitle) {
		this.sointitle = sointitle;
	}

	public List<String> getStardList() {
		return stardList;
	}

	public void setStardList(List<String> stardList) {
		this.stardList = stardList;
	}

	public List<EmCommissionOutFeeDetailChangeModel> getFeeList() {
		return feeList;
	}

	public void setFeeList(List<EmCommissionOutFeeDetailChangeModel> feeList) {
		this.feeList = feeList;
	}

	public Date getAddtime() {
		return addtime;
	}

	public void setAddtime(Date addtime) {
		this.addtime = addtime;
	}

}
