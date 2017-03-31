package Controller.EmCommissionOut;

import java.math.BigDecimal;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.zkoss.bind.annotation.BindingParam;
import org.zkoss.bind.annotation.Command;
import org.zkoss.bind.annotation.NotifyChange;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zul.Combobox;
import org.zkoss.zul.ListModelList;
import org.zkoss.zul.Messagebox;

import bll.EmCommissionOut.EmCommissionOutListBll;
import bll.EmCommissionOut.EmCommissionOut_OperateBll;

import Controller.EmResidencePermit.newExcelImpl;
import Model.EmCommissionOutChangeModel;
import Model.EmCommissionOutFeeDetailChangeModel;
import Model.EmCommissionOutFeeDetailModel;
import Model.EmCommissionOutModel;
import Model.EmCommissionOutStandardModel;
import Util.DateStringChange;
import Util.SocialInsuranceEmCommissionOut;
import Util.UserInfo;

public class EmCommissionOut_ChangeController {

	private EmCommissionOutModel m = new EmCommissionOutModel();
	private EmCommissionOutChangeModel cm = new EmCommissionOutChangeModel();

	private List<EmCommissionOutFeeDetailModel> feeList = new ListModelList<>();
	private List<EmCommissionOutFeeDetailChangeModel> cfeeList = new ListModelList<>();
	private List<EmCommissionOutStandardModel> stardList;// 合同标准
	private EmCommissionOutStandardModel stardModel = new EmCommissionOutStandardModel();
	private List<EmCommissionOutChangeModel> titleList;// 当地标准
	private EmCommissionOutChangeModel titleModel;// 获取标准详细信息
	private EmCommissionOutChangeModel old_titleModel;
	private List<EmCommissionOutFeeDetailChangeModel> flList = new ListModelList<>();// 获取标准福利产品信息

	private List<EmCommissionOutStandardModel> filelist;// 档案费列表
	private List<EmCommissionOutStandardModel> fuwulist;// 服务费列表

	// 合同起始时间
	private Date compact_f;
	// 合同结束时间
	private Date compact_l;
	private String compact_l_str = "有固定期限";
	private boolean compact_l_vis;

	private boolean file_state = true;
	private String filestr = "";

	// 变更项列表
	private List<String> changeitemsList = new ArrayList<>();

	SocialInsuranceEmCommissionOut calUtil = new SocialInsuranceEmCommissionOut();

	public EmCommissionOut_ChangeController() {
		try {
			m = (EmCommissionOutModel) Executions.getCurrent()
					.getAttribute("m");

			init();
		} catch (Exception e) {
			Messagebox
					.show("页面加载出错!", "ERROR", Messagebox.OK, Messagebox.ERROR);
			e.printStackTrace();
		}
	}

	/**
	 * 初始化
	 * 
	 */
	public void init() {
		EmCommissionOutListBll bll = new EmCommissionOutListBll();

		try {
			setFeeList(bll.getFeeDetailList(" and eofd_ecou_id="
					+ m.getEcou_id()));

			UpdateToChange();

			compact_f = DateStringChange.StringtoDate(cm.getEcoc_compact_f(),
					"yyyy-MM-dd");
			if ("无固定期限".equals(cm.getEcoc_compact_l())) {
				compact_l_str = "无固定期限";
				compact_l = null;
			} else if ("有固定期限".equals(cm.getEcoc_compact_l())) {
				compact_l_str = "有固定期限";
				compact_l = DateStringChange.StringtoDate(
						cm.getEcoc_compact_l(), "yyyy-MM-dd");
			} else {
				compact_l_str = "有固定期限";
				compact_l = null;
			}
			
			setStardList(bll.getStardListed(cm.getCid(), m.getGid()));
			setStardModel(bll.getStardInfo(cm.getEcoc_ecos_id()));
			m.setEcou_compact_jud(stardModel.getWtss_laborcontract());
			cm.setEcoc_compact_jud(stardModel.getWtss_laborcontract());
			for (EmCommissionOutStandardModel m1 : stardList) {
				if (m1.getEcos_id().equals(cm.getEcoc_ecos_id())) {
					setStardModel(m1);
					break;
				}
			}
			setTitleList(bll.getTitleList(cm.getEcoc_ecos_id()));
			for (EmCommissionOutChangeModel m1 : titleList) {
				if (m1.getSoin_id().equals(cm.getEcoc_soin_id())) {
					setTitleModel(m1);
					setOld_titleModel(m1);
					break;
				}

				compact_l_change();
			}

			// 获取福利列表
			setFlList(bll.getflList(m.getGid(), stardModel.getCity(),
					stardModel.getCoab_name()));

			for (EmCommissionOutFeeDetailChangeModel m1 : flList) {
				if (m1.getEofc_start_date() == null) {
					m1.setTempDate(new Date());
				}
			}

			// 移除福利项目
			for (int i = 0; i < cfeeList.size(); i++) {
				EmCommissionOutFeeDetailChangeModel m1 = cfeeList.get(i);
				if (m1.getSicl_class().equals("福利项目")) {
					cfeeList.remove(m1);
					i--;
				}
			}

			// 添加新的福利项目
			cfeeList.addAll(flList);

			//titleChange();
		} catch (Exception e) {
			e.printStackTrace();
		}

		if (stardList.get(0).getWtss_archives().equals("不保管")
				|| stardList.get(0).getWtss_archives().equals("")) {
			file_state = true;
		} else {
			file_state = false;
		}
		
		System.out.println(m.getCid());
		
		if(!m.getCid().equals(2227)){
		sb_baseChange();
		house_baseChange();
		}

		if (m.getEcou_house_base().toString().equals("0.00")&&!m.getCid().equals(2227)) {
			titleChange();
			sb_baseChange();
			house_baseChange();
		}
	}

	/**
	 * 控制合同结束日期datebox控件是否显示
	 * 
	 */
	@Command("compact_l_change")
	@NotifyChange({ "compact_l_vis" })
	public void compact_l_change() {
		if (compact_l_str.equals("无固定期限")) {
			compact_l_vis = false;
			compact_l = null;
		} else if (compact_l_str.equals("有固定期限")) {
			compact_l_vis = true;
		}
	}

	/**
	 * 关联委托标准表标准名
	 * 
	 * @param stardModel
	 */
	@Command("stardChange")
	@NotifyChange({ "titleList", "titleModel", "cfeeList", "cm", "stardModel" })
	public void stardChange() {
		EmCommissionOutListBll bll = new EmCommissionOutListBll();

		try {
			setTitleList(bll.getTitleList(stardModel.getEcos_id()));
			setTitleModel(titleList.get(0));

			m.setEcou_service_fee(stardModel.getEcos_service_fee());
			// m.setEcou_file_fee(stardModel.getEcos_archvie_fee());

			// 获取福利列表
			setFlList(bll.getflList(m.getGid(), stardModel.getCity(),
					stardModel.getCoab_name()));

			for (EmCommissionOutFeeDetailChangeModel m1 : flList) {
				if (m1.getEofc_start_date() == null) {
					m1.setTempDate(new Date());
				}
			}

			// 移除福利项目
			for (int i = 0; i < cfeeList.size(); i++) {
				EmCommissionOutFeeDetailChangeModel m1 = cfeeList.get(i);
				if (m1.getSicl_class().equals("福利项目")) {
					cfeeList.remove(m1);
					i--;
				}
			}

			// 添加新的福利项目
			cfeeList.addAll(flList);

			titleChange();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 关联委托标准表详细信息
	 * 
	 */
	@Command("titleChange")
	@NotifyChange({ "cfeeList", "cm", "titleModel" })
	public void titleChange() {
		try {

			// 清空比例
			for (EmCommissionOutFeeDetailChangeModel m1 : cfeeList) {
				m1.setEofc_cp(null);
				m1.setEofc_op(null);
			}

			List<Date> dateList = OrderByStartDate();
			Calc(dateList.get(0));

			sb_baseChange();
			house_baseChange();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 社保基数变更
	 * 
	 */
	@Command("sb_baseChange")
	@NotifyChange({ "cfeeList", "cm" })
	public void sb_baseChange() {
		if (cm.getEcoc_sb_base() != null) {
			if (cm.getEcoc_sb_base().compareTo(new BigDecimal(0)) == 1) {
				for (EmCommissionOutFeeDetailChangeModel m1 : cfeeList) {
					if (m1.getSicl_class().equals("社会保险")) {
						m1.setEofc_em_base(cm.getEcoc_sb_base());
						m1.setEofc_co_base(cm.getEcoc_sb_base());
						if (m1.getTempDate() == null) {
							m1.setTempDate(new Date());
						}
					}
				}
			} else if (cm.getEcoc_sb_base().compareTo(new BigDecimal(0)) == 0) {
				for (EmCommissionOutFeeDetailChangeModel m1 : cfeeList) {
					if (m1.getSicl_class().equals("社会保险")) {
						m1.setEofc_em_base(null);
						m1.setEofc_co_base(null);
						m1.setTempDate(null);
					}
				}
			}
		}
		List<Date> dateList = OrderByStartDate();
		System.out.println("xx");
		System.out.println(dateList.get(0));
		Calc(dateList.get(0));
	}

	/**
	 * 公积金基数变更
	 * 
	 */
	@Command("house_baseChange")
	@NotifyChange({ "cfeeList", "cm" })
	public void house_baseChange() {
		if (cm.getEcoc_house_base() != null) {
			if (cm.getEcoc_house_base().compareTo(new BigDecimal(0)) == 1) {
				for (EmCommissionOutFeeDetailChangeModel m1 : cfeeList) {
					if (m1.getSicl_class().equals("公积金")) {
						m1.setEofc_em_base(cm.getEcoc_house_base());
						m1.setEofc_co_base(cm.getEcoc_house_base());
						if (m1.getTempDate() == null) {
							m1.setTempDate(new Date());
						}
					}
				}
			} else if (cm.getEcoc_house_base().compareTo(new BigDecimal(0)) == 0) {
				for (EmCommissionOutFeeDetailChangeModel m1 : cfeeList) {
					if (m1.getSicl_class().equals("公积金")) {
						m1.setEofc_em_base(null);
						m1.setEofc_co_base(null);
						m1.setTempDate(null);
					}
				}
			}
		}
		List<Date> dateList = OrderByStartDate();
		Calc(dateList.get(0));
	}

	/**
	 * 对起始日排序(从小到大)
	 * 
	 * @return List<Date>
	 */
	public List<Date> OrderByStartDate() {
		List<Date> list = new ArrayList<>();
		for (EmCommissionOutFeeDetailChangeModel m1 : cfeeList) {

			if (m1.getTempDate() != null) {
				list.add(new Date());
			}
		}

		if (list.size() > 0) {
			for (int i = 0; i < list.size() - 1; i++) {
				for (int j = 0; j < list.size() - 1 - i; j++) {
					if (list.get(j).after(list.get(j + 1))) {
						Date date = list.get(j);
						list.set(j, list.get(j + 1));
						list.set(j + 1, date);
					}
				}
			}
		}

		return list;
	}

	/**
	 * 基数、比例变更
	 * 
	 */
	@Command("base_per_change")
	@NotifyChange({ "cfeeList", "cm" })
	public void base_per_change() {
		try {
			List<Date> dateList = OrderByStartDate();
			Calc(dateList.get(0));
		} catch (Exception e) {
			// TODO: handle exception
		}

	}

	/**
	 * 计算
	 * 
	 * @param subM
	 * @param list
	 * @param title_date
	 * @return
	 */
	public void Calc(Date title_date) {
		if (titleModel.getSoin_id() != null) {
			// 设置计算信息
			if (calUtil.setCalculator(titleModel.getSoin_id(), title_date,
					stardModel.getEcos_shebao_feetype(),
					stardModel.getEcos_gjj_feetype())) {

				// 计算
				cfeeList = calUtil.getEmCommissionOutItemFee(cfeeList);

				// 总计
				calSum();

				setOld_titleModel(titleModel);
			} else {
				Messagebox.show(titleModel.getSoin_title() + "在"
						+ DateStringChange.DatetoSting(title_date, "yyyy-MM")
						+ "未生效!", "ERROR", Messagebox.OK, Messagebox.ERROR);

				setTitleModel(old_titleModel);
			}
		}
	}

	/**
	 * 在册数据复制到变更数据
	 * 
	 */
	public void UpdateToChange() {
		cm.setEmba_name(m.getEmba_name());
		cm.setGid(m.getGid());
		cm.setCid(m.getCid());
		cm.setEcoc_ecou_id(m.getEcou_id());
		cm.setEcoc_soin_id(m.getEcou_soin_id());
		cm.setEcoc_ecos_id(m.getEcou_ecos_id());
		cm.setEcoc_addtype("调整");
		cm.setEcoc_type("ecocchange");
		cm.setEcoc_idcard(m.getEcou_idcard());
		cm.setEcoc_email(m.getEcou_email());
		cm.setEcoc_phone(m.getEcou_phone());
		cm.setEcoc_mobile(m.getEcou_mobile());
		cm.setEcoc_in_date(m.getEcou_in_date());
		cm.setEcoc_com_phone(m.getEcou_com_phone());
		cm.setEcoc_com_principal(m.getEcou_com_principal());
		cm.setEcoc_com_company(m.getEcou_com_company());
		cm.setEcoc_domicile(m.getEcou_domicile());
		cm.setEcoc_compact_jud(m.getEcou_compact_jud());
		cm.setEcoc_compact_f(m.getEcou_compact_f());
		cm.setEcoc_compact_l(m.getEcou_compact_l());
		cm.setEcoc_salary(m.getEcou_salary());
		cm.setEcoc_sb_base(m.getEcou_sb_base());
		cm.setEcoc_house_base(m.getEcou_house_base());
		cm.setEcoc_sb_em_sum(m.getEcou_sb_em_sum());
		cm.setEcoc_sb_co_sum(m.getEcou_sb_co_sum());
		cm.setEcoc_sb_sum(m.getEcou_gjj_sum());
		cm.setEcoc_gjj_em_sum(m.getEcou_gjj_em_sum());
		cm.setEcoc_gjj_co_sum(m.getEcou_gjj_co_sum());
		cm.setEcoc_gjj_sum(m.getEcou_gjj_sum());
		cm.setEcoc_welfare_sum(m.getEcou_welfare_sum());
		cm.setEcoc_service_fee(m.getEcou_service_fee());
		cm.setEcoc_file_fee(m.getEcou_file_fee());
		cm.setEcoc_sum(m.getEcou_sum());
		cm.setEcoc_stop_date(m.getEcou_stop_date());
		cm.setEcoc_stop_cause(m.getEcou_stop_cause());
		cm.setEcoc_cancel_cause(m.getEcou_cancel_cause());
		cm.setEcoc_laststate(0);
		cm.setEcoc_state(1);
		cm.setEcoc_client(m.getEcou_client());
		cm.setEcoc_addname(UserInfo.getUsername());
		cm.setEcoc_remark("");

		for (EmCommissionOutFeeDetailModel m1 : feeList) {
			EmCommissionOutFeeDetailChangeModel cm1 = new EmCommissionOutFeeDetailChangeModel();

			cm1.setEofc_eofd_id(m1.getEofd_id());
			cm1.setEofc_sicl_id(m1.getEofd_sicl_id());
			cm1.setEofc_ecop_id(m1.getEofd_ecop_id());
			cm1.setEofc_name(m1.getEofd_name());
			cm1.setEofc_content(m1.getEofd_content());
			cm1.setEofc_em_base(m1.getEofd_em_base());
			cm1.setEofc_co_base(m1.getEofd_co_base());
			cm1.setEofc_cp(m1.getEofd_cp());
			cm1.setEofc_op(m1.getEofd_op());
			cm1.setEofc_em_sum(m1.getEofd_em_sum());
			cm1.setEofc_co_sum(m1.getEofd_co_sum());
			cm1.setEofc_month_sum(m1.getEofd_month_sum());
			cm1.setEofc_start_date(m1.getEofd_start_date());
			cm1.setEofc_state(1);
			cm1.setTempDate(new Date());
			cm1.setSicl_class(m1.getSicl_class());

			cfeeList.add(cm1);

		}
	}

	/**
	 * 时间批量修改
	 * 
	 * @param date
	 */
	@Command("dateAll")
	@NotifyChange({ "cfeeList" })
	public void dateAll(@BindingParam("date") Date date,
			@BindingParam("index") Integer index,
			@BindingParam("class") String sicl_class) {
		try {
			if (sicl_class.equals("all")) {
				date = cfeeList.get(0).getTempDate();
				index = 0;
			}
			for (int i = index + 1; i < cfeeList.size(); i++) {
				if (cfeeList.get(i).getSicl_class().equals(sicl_class)
						|| sicl_class.equals("all")) {
					cfeeList.get(i).setTempDate(date);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	// 控制档案费
	@Command("fileChange")
	@NotifyChange({ "cfeeList", "m" })
	public void fileChange(@BindingParam("ch1") Combobox ch1)
			throws SQLException {
		EmCommissionOutListBll bll = new EmCommissionOutListBll();
		filestr = "";
		List<EmCommissionOutFeeDetailChangeModel> lsfeeList = new ListModelList<>();

		if (ch1.getValue().equals("是")) {

			setFilelist(bll.getfilelist(m.getGid(),
					stardModel.getEcos_cabc_id()));

			if (filelist.size() == 0) {
				Messagebox.show("请通知项目部添加外地档案产品！", "ERROR", Messagebox.OK,
						Messagebox.ERROR);
			} else

			{
				cm.setEcoc_file_fee(filelist.get(0).getEcos_archvie_fee());
			}
			// cm.setEcoc_service_fee(fuwulist.get(0).getEcos_service_fee());

			for (EmCommissionOutFeeDetailChangeModel m1 : cfeeList) {
				EmCommissionOutFeeDetailChangeModel cm1 = new EmCommissionOutFeeDetailChangeModel();

				cm1.setEofc_eofd_id(m1.getEofc_id());
				cm1.setEofc_sicl_id(m1.getEofc_sicl_id());
				cm1.setEofc_ecop_id(m1.getEofc_ecop_id());
				cm1.setEofc_name(m1.getEofc_name());
				cm1.setEofc_content(m1.getEofc_content());
				cm1.setEofc_em_base(m1.getEofc_em_base());
				cm1.setEofc_co_base(m1.getEofc_co_base());
				cm1.setEofc_cp(m1.getEofc_cp());
				cm1.setEofc_op(m1.getEofc_op());
				cm1.setEofc_em_sum(m1.getEofc_em_sum());
				cm1.setEofc_co_sum(m1.getEofc_co_sum());
				if (m1.getEofc_name().equals("档案费")) {
					cm1.setEofc_month_sum(filelist.get(0).getEcos_archvie_fee());
				} else {
					cm1.setEofc_month_sum(m1.getEofc_month_sum());
				}
				cm1.setEofc_start_date(m1.getEofc_start_date());
				cm1.setEofc_state(1);
				cm1.setTempDate(m1.getTempDate());
				cm1.setSicl_class(m1.getSicl_class());

				lsfeeList.add(cm1);

				filestr = "新增档案;";
			}

		} else {
			for (EmCommissionOutFeeDetailChangeModel m1 : cfeeList) {
				EmCommissionOutFeeDetailChangeModel cm1 = new EmCommissionOutFeeDetailChangeModel();

				cm1.setEofc_eofd_id(m1.getEofc_id());
				cm1.setEofc_sicl_id(m1.getEofc_sicl_id());
				cm1.setEofc_ecop_id(m1.getEofc_ecop_id());
				cm1.setEofc_name(m1.getEofc_name());
				cm1.setEofc_content(m1.getEofc_content());
				cm1.setEofc_em_base(m1.getEofc_em_base());
				cm1.setEofc_co_base(m1.getEofc_co_base());
				cm1.setEofc_cp(m1.getEofc_cp());
				cm1.setEofc_op(m1.getEofc_op());
				cm1.setEofc_em_sum(m1.getEofc_em_sum());
				cm1.setEofc_co_sum(m1.getEofc_co_sum());
				if (m1.getEofc_name().equals("档案费")) {
					cm1.setEofc_month_sum((new BigDecimal(0)));
				} else {
					cm1.setEofc_month_sum(m1.getEofc_month_sum());
				}
				cm1.setEofc_start_date(m1.getEofc_start_date());
				cm1.setEofc_state(1);
				cm1.setTempDate(m1.getTempDate());
				cm1.setSicl_class(m1.getSicl_class());

				lsfeeList.add(cm1);

				filestr = "";

			}
		}

		cfeeList = lsfeeList;

		calSum();

	}

	/**
	 * 获取变更项
	 * 
	 */
	public void getchangeitem() {
		try {
			changeitemsList.clear();
			if (!filestr.equals("")) {
				changeitemsList.add(filestr);
			}
			Integer oldInteger = null;
			Integer newInteger = null;
			String oldString = null;
			String newString = null;
			BigDecimal oldBigDecimal = null;
			BigDecimal newBigDecimal = null;

			// 合同标准
			oldInteger = m.getEcou_ecos_id() == null ? 0 : m.getEcou_ecos_id();
			newInteger = cm.getEcoc_ecos_id() == null ? 0 : cm
					.getEcoc_ecos_id();
			if (!oldInteger.equals(newInteger)) {
				changeitemsList.add("合同标准变更；");
			}
			// 当地标准
			oldInteger = m.getEcou_soin_id() == null ? 0 : m.getEcou_soin_id();
			newInteger = cm.getEcoc_soin_id() == null ? 0 : cm
					.getEcoc_soin_id();
			if (!oldInteger.equals(newInteger)) {
				changeitemsList.add("当地标准变更；");
			}
			// 合同签定方
			oldString = m.getEcou_compact_jud() == null ? "" : m
					.getEcou_compact_jud();
			newString = cm.getEcoc_compact_jud() == null ? "" : cm
					.getEcoc_compact_jud();
			if (!oldString.equals(newString)) {
				changeitemsList.add("合同签定方变更；");
			}
			// 实际工资
			oldBigDecimal = m.getEcou_salary() == null ? BigDecimal.ZERO : m
					.getEcou_salary();
			newBigDecimal = cm.getEcoc_salary() == null ? BigDecimal.ZERO : cm
					.getEcoc_salary();
			if (!oldBigDecimal.equals(newBigDecimal)) {
				changeitemsList.add("实际工资变更；");
			}
			// 社保基数
			oldBigDecimal = m.getEcou_sb_base() == null ? BigDecimal.ZERO : m
					.getEcou_sb_base();
			newBigDecimal = cm.getEcoc_sb_base() == null ? BigDecimal.ZERO : cm
					.getEcoc_sb_base();
			if (!oldBigDecimal.equals(newBigDecimal)) {
				changeitemsList.add("社保基数变更；");
			}
			// 公积金基数
			oldBigDecimal = m.getEcou_house_base() == null ? BigDecimal.ZERO
					: m.getEcou_house_base();
			newBigDecimal = cm.getEcoc_house_base() == null ? BigDecimal.ZERO
					: cm.getEcoc_house_base();
			if (!oldBigDecimal.equals(newBigDecimal)) {
				changeitemsList.add("公积金基数变更；");
			}

			// String name="";

			if (cfeeList.size() > feeList.size()) {
				for (EmCommissionOutFeeDetailChangeModel lcm1 : cfeeList) {
					for (EmCommissionOutFeeDetailModel lm1 : feeList) {

						if (lcm1.getEofc_name().equals(lm1.getEofd_name())) {
							if (!lm1.getEofd_start_date().equals(
									lcm1.getEofc_start_date())) {
								changeitemsList.add("部分险种起始日期有变更");
								break;
							}
						}
					}
				}
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 提交
	 * 
	 */
	@Command("submit")
	@NotifyChange({ "cm", "cfeeList", "stardList", "titleList", "compact_f",
			"compact_l", "stardModel", "titleModel" })
	public void submit() {

		EmCommissionOut_OperateBll bll = new EmCommissionOut_OperateBll();
		try {
			for (EmCommissionOutFeeDetailChangeModel m1 : cfeeList) {

				m1.setEofc_start_date(m1.getTempDate() == null ? null
						: DateStringChange.DatetoSting(m1.getTempDate(),
								"yyyy-MM-01"));

				if (m1.getEofc_name().equals("服务费")) {
					if (m1.getEofc_start_date() == null) {
						Messagebox.show("请填写服务费起始时间！", "ERROR", Messagebox.OK,
								Messagebox.ERROR);
						return;
					}
				}

			}
			cm.setEcoc_title_date(DateStringChange.DatetoSting(
					OrderByStartDate().get(0), "yyyy-MM-01"));
			cm.setFeeList(cfeeList);
			cm.setEcoc_soin_id(titleModel.getSoin_id());
			cm.setEcoc_ecos_id(stardModel.getEcos_id());
			cm.setEcoc_compact_f(compact_f == null ? null : DateStringChange
					.DatetoSting(compact_f, "yyyy-MM-dd"));
			cm.setEcoc_compact_f(compact_f == null ? null : DateStringChange
					.DatetoSting(compact_f, "yyyy-MM-dd"));
			if (compact_l != null) {
				cm.setEcoc_compact_l(DateStringChange.DatetoSting(compact_l,
						"yyyy-MM-dd"));
			} else {
				cm.setEcoc_compact_l("无固定期限");
			}

			// 获取变更项
			getchangeitem();

			String changeStr = "";
			for (String str : changeitemsList) {
				changeStr += str;
			}

			if (changeStr == "" && "".equals(cm.getEcoc_remark().toString())) {
				Messagebox.show("请填写备注内容！", "ERROR", Messagebox.OK,
						Messagebox.ERROR);
				return;
			}
			cm.setEcoc_remark(cm.getEcoc_remark() + changeStr);
			cm.setRemark(cm.getEcoc_remark());

			if (Messagebox.show("变更内容为:\"" + cm.getEcoc_remark()
					+ "\"\n是否确认提交?", "CONFIRM", Messagebox.OK
					| Messagebox.CANCEL, Messagebox.QUESTION) == Messagebox.OK) {

				String[] str = bll.change(cm, cm.getEmba_name());

				if (str[0].equals("1")) {
					Messagebox.show(str[1], "INFORMATION", Messagebox.OK,
							Messagebox.INFORMATION);
					cfeeList.clear();
					init();
				} else if (str[0].equals("0")) {
					Messagebox.show(str[1], "ERROR", Messagebox.OK,
							Messagebox.ERROR);
				}
			}

		} catch (Exception e) {
			e.printStackTrace();
			Messagebox.show("提交出错!", "ERROR", Messagebox.OK, Messagebox.ERROR);
		}
	}

	/**
	 * 计算总计
	 * 
	 */
	public void calSum() {
		// 重置总计
		cm.setEcoc_sb_em_sum(new BigDecimal(0));
		cm.setEcoc_sb_co_sum(new BigDecimal(0));
		cm.setEcoc_sb_sum(new BigDecimal(0));
		cm.setEcoc_gjj_em_sum(new BigDecimal(0));
		cm.setEcoc_gjj_co_sum(new BigDecimal(0));
		cm.setEcoc_gjj_sum(new BigDecimal(0));
		cm.setEcoc_sum(new BigDecimal(0));
		cm.setEcoc_welfare_sum(new BigDecimal(0));

		try {
			// 社保、公积金、福利
			for (EmCommissionOutFeeDetailChangeModel m1 : cfeeList) {
				// 社保
				if (m1.getSicl_class().equals("社会保险")) {
					// 社保企业总计
					cm.setEcoc_sb_co_sum(cm.getEcoc_sb_co_sum().add(
							m1.getEofc_co_sum()));
					// 社保个人总计
					cm.setEcoc_sb_em_sum(cm.getEcoc_sb_em_sum().add(
							m1.getEofc_em_sum()));
				}
				// 公积金
				else if (m1.getSicl_class().equals("公积金")) {
					// 公积金企业总计
					cm.setEcoc_gjj_co_sum(cm.getEcoc_gjj_co_sum().add(
							m1.getEofc_co_sum()));
					// 公积金个人总计
					cm.setEcoc_gjj_em_sum(cm.getEcoc_gjj_em_sum().add(
							m1.getEofc_em_sum()));
				}
				// 福利
				else if (m1.getSicl_class().equals("福利项目")) {
					cm.setEcoc_welfare_sum(cm.getEcoc_welfare_sum().add(
							m1.getEofc_month_sum()));
				}
				// 总计
				cm.setEcoc_sum(cm.getEcoc_sum().add(m1.getEofc_month_sum()));
			}
			// 社保总计
			cm.setEcoc_sb_sum(cm.getEcoc_sb_em_sum()
					.add(cm.getEcoc_sb_co_sum()));
			// 公积金总计
			cm.setEcoc_gjj_sum(cm.getEcoc_gjj_em_sum().add(
					cm.getEcoc_gjj_co_sum()));

		} catch (Exception e) {
			e.printStackTrace();
			Messagebox
					.show("费用总计出错!", "ERROR", Messagebox.OK, Messagebox.ERROR);
		}
	}

	public List<EmCommissionOutStandardModel> getFilelist() {
		return filelist;
	}

	public void setFilelist(List<EmCommissionOutStandardModel> filelist) {
		this.filelist = filelist;
	}

	public boolean isFile_state() {
		return file_state;
	}

	public void setFile_state(boolean file_state) {
		this.file_state = file_state;
	}

	public final List<EmCommissionOutStandardModel> getStardList() {
		return stardList;
	}

	public final EmCommissionOutStandardModel getStardModel() {
		return stardModel;
	}

	public final List<EmCommissionOutChangeModel> getTitleList() {
		return titleList;
	}

	public final EmCommissionOutChangeModel getTitleModel() {
		return titleModel;
	}

	public final Date getCompact_f() {
		return compact_f;
	}

	public final Date getCompact_l() {
		return compact_l;
	}

	public final String getCompact_l_str() {
		return compact_l_str;
	}

	public final boolean isCompact_l_vis() {
		return compact_l_vis;
	}

	public final void setStardList(List<EmCommissionOutStandardModel> stardList) {
		this.stardList = stardList;
	}

	public final void setStardModel(EmCommissionOutStandardModel stardModel) {
		this.stardModel = stardModel;
	}

	public final void setTitleList(List<EmCommissionOutChangeModel> titleList) {
		this.titleList = titleList;
	}

	public final void setTitleModel(EmCommissionOutChangeModel titleModel) {
		this.titleModel = titleModel;
	}

	public final void setCompact_f(Date compact_f) {
		this.compact_f = compact_f;
	}

	public final void setCompact_l(Date compact_l) {
		this.compact_l = compact_l;
	}

	public final void setCompact_l_str(String compact_l_str) {
		this.compact_l_str = compact_l_str;
	}

	public final void setCompact_l_vis(boolean compact_l_vis) {
		this.compact_l_vis = compact_l_vis;
	}

	public EmCommissionOutModel getM() {
		return m;
	}

	public void setM(EmCommissionOutModel m) {
		this.m = m;
	}

	public EmCommissionOutChangeModel getCm() {
		return cm;
	}

	public void setCm(EmCommissionOutChangeModel cm) {
		this.cm = cm;
	}

	public List<EmCommissionOutFeeDetailModel> getFeeList() {
		return feeList;
	}

	public void setFeeList(List<EmCommissionOutFeeDetailModel> feeList) {
		this.feeList = feeList;
	}

	public List<EmCommissionOutFeeDetailChangeModel> getCfeeList() {
		return cfeeList;
	}

	public void setCfeeList(List<EmCommissionOutFeeDetailChangeModel> cfeeList) {
		this.cfeeList = cfeeList;
	}

	public final List<EmCommissionOutFeeDetailChangeModel> getFlList() {
		return flList;
	}

	public final void setFlList(List<EmCommissionOutFeeDetailChangeModel> flList) {
		this.flList = flList;
	}

	public EmCommissionOutChangeModel getOld_titleModel() {
		return old_titleModel;
	}

	public void setOld_titleModel(EmCommissionOutChangeModel old_titleModel) {
		this.old_titleModel = old_titleModel;
	}

	public List<String> getChangeitemsList() {
		return changeitemsList;
	}

	public void setChangeitemsList(List<String> changeitemsList) {
		this.changeitemsList = changeitemsList;
	}

	public List<EmCommissionOutStandardModel> getFuwulist() {
		return fuwulist;
	}

	public void setFuwulist(List<EmCommissionOutStandardModel> fuwulist) {
		this.fuwulist = fuwulist;
	}

}
