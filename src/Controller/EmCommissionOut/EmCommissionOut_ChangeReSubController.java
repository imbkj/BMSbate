package Controller.EmCommissionOut;

import java.math.BigDecimal;
import java.sql.SQLException;
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
import org.zkoss.zul.ListModelList;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Window;

import dal.EmCommissionOut.EmCommissionOut_ListDal;

import Model.EmCommissionOutChangeModel;
import Model.EmCommissionOutFeeDetailChangeModel;
import Model.EmCommissionOutStandardModel;
import Util.DateStringChange;
import Util.SocialInsuranceEmCommissionOut;
import Util.UserInfo;
import bll.EmCommissionOut.EmCommissionOutListBll;
import bll.EmCommissionOut.EmCommissionOut_OperateBll;

public class EmCommissionOut_ChangeReSubController {
	private EmCommissionOutChangeModel m = new EmCommissionOutChangeModel();
	Integer daid = 0;

	private List<EmCommissionOutStandardModel> stardList;// 合同标准
	private EmCommissionOutStandardModel stardModel = new EmCommissionOutStandardModel();
	private List<EmCommissionOutChangeModel> titleList;// 当地标准
	private EmCommissionOutChangeModel titleModel;// 获取标准详细信息
	private EmCommissionOutChangeModel old_titleModel;
	private List<EmCommissionOutFeeDetailChangeModel> feeList = new ListModelList<>();
	private List<EmCommissionOutFeeDetailChangeModel> flList = new ListModelList<>();// 获取标准福利产品信息
	private List<EmCommissionOutChangeModel> eclist;// 获取任务单

	// 合同起始时间
	private Date compact_f;
	// 合同结束时间
	private Date compact_l;
	private String compact_l_str = "有固定期限";
	private boolean compact_l_vis;

	SocialInsuranceEmCommissionOut calUtil = new SocialInsuranceEmCommissionOut();

	public EmCommissionOut_ChangeReSubController() {
		try {
			daid = Integer.parseInt(Executions.getCurrent().getArg()
					.get("daid").toString());

			init();

		} catch (Exception e) {
			e.printStackTrace();
			Messagebox
					.show("页面加载出错!", "ERROR", Messagebox.OK, Messagebox.ERROR);
		}
	}

	/**
	 * 数据初始化
	 * 
	 */
	public void init() {
		EmCommissionOutListBll bll = new EmCommissionOutListBll();

		try {
			setM(bll.getEmCommOutChangeInfo(daid, ""));
			m.setType(m.getEcoc_type());
			m.setEcoc_remark1("");

			compact_f = m.getEcoc_compact_f() == null ? null
					: new SimpleDateFormat("yyyy-MM-dd").parse(m
							.getEcoc_compact_f());
			if (m.getEcoc_compact_l().equals("无固定期限")) {
				compact_l_str = "无固定期限";
				compact_l = null;
			} else if (m.getEcoc_compact_l().equals("有固定期限")) {
				compact_l_str = "有固定期限";
				compact_l = new SimpleDateFormat("yyyy-MM-dd").parse(m
						.getEcoc_compact_l());
			} else {
				compact_l_str = "有固定期限";
				compact_l = null;
			}

			setStardList(bll.getStardList(m.getCid(), 0));
			setStardModel(bll.getStardInfo(m.getEcoc_ecos_id()));
			for (EmCommissionOutStandardModel m1 : stardList) {
				if (m1.getEcos_id().equals(m.getEcoc_ecos_id())) {
					setStardModel(m1);
					break;
				}
			}
			setTitleList(bll.getTitleList(m.getEcoc_ecos_id()));
			for (EmCommissionOutChangeModel m1 : titleList) {
				if (m1.getSoin_id().equals(m.getEcoc_soin_id())) {
					setTitleModel(m1);
					setOld_titleModel(m1);
					break;
				}
				compact_l_change();
			}

			setFeeList(bll.getFeeDetailChangeList(" and eofc_ecoc_id=" + daid));
			for (EmCommissionOutFeeDetailChangeModel m : feeList) {
				if (m.getEofc_start_date() != null) {
					m.setTempDate(new Date());
				}
			}

		} catch (Exception e) {
			e.printStackTrace();
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
	@NotifyChange({ "titleList", "titleModel", "feeList", "m", "stardModel" })
	public void stardChange() {
		EmCommissionOutListBll bll = new EmCommissionOutListBll();

		try {
			setTitleList(bll.getTitleList(stardModel.getEcos_id()));
			setTitleModel(titleList.get(0));

			m.setEcoc_service_fee(stardModel.getEcos_service_fee());
			m.setEcoc_file_fee(stardModel.getEcos_archvie_fee());

			// 获取福利列表
			setFlList(bll.getflList(m.getGid(),stardModel.getCity(),stardModel.getCoab_name()));

			for (EmCommissionOutFeeDetailChangeModel m1 : flList) {
				if (m1.getEofc_start_date() == null) {
					m1.setTempDate(new Date());
				}
			}

			// 移除福利项目
			for (int i = 0; i < feeList.size(); i++) {
				EmCommissionOutFeeDetailChangeModel m1 = feeList.get(i);
				if (m1.getSicl_class().equals("福利项目")) {
					feeList.remove(m1);
					i--;
				}
			}

			// 添加新的福利项目
			feeList.addAll(flList);

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
	@NotifyChange({ "feeList", "m", "titleModel" })
	public void titleChange() {
		try {

			// 清空比例
			for (EmCommissionOutFeeDetailChangeModel m1 : feeList) {
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
	@NotifyChange({ "feeList", "m" })
	public void sb_baseChange() {
		if (m.getEcoc_sb_base() != null) {
			if (m.getEcoc_sb_base().compareTo(new BigDecimal(0)) == 1) {
				for (EmCommissionOutFeeDetailChangeModel m1 : feeList) {
					if (m1.getSicl_class().equals("社会保险")) {
						m1.setEofc_em_base(m.getEcoc_sb_base());
						m1.setEofc_co_base(m.getEcoc_sb_base());
						if (m1.getTempDate() == null) {
							m1.setTempDate(new Date());
						}
					}
				}
			} else if (m.getEcoc_sb_base().compareTo(new BigDecimal(0)) == 0) {
				for (EmCommissionOutFeeDetailChangeModel m1 : feeList) {
					if (m1.getSicl_class().equals("社会保险")) {
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
	 * 公积金基数变更
	 * 
	 */
	@Command("house_baseChange")
	@NotifyChange({ "feeList", "m" })
	public void house_baseChange() {
		if (m.getEcoc_house_base() != null) {
			if (m.getEcoc_house_base().compareTo(new BigDecimal(0)) == 1) {
				for (EmCommissionOutFeeDetailChangeModel m1 : feeList) {
					if (m1.getSicl_class().equals("公积金")) {
						m1.setEofc_em_base(m.getEcoc_house_base());
						m1.setEofc_co_base(m.getEcoc_house_base());
						if (m1.getTempDate() == null) {
							m1.setTempDate(new Date());
						}
					}
				}
			} else if (m.getEcoc_house_base().compareTo(new BigDecimal(0)) == 0) {
				for (EmCommissionOutFeeDetailChangeModel m1 : feeList) {
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
		for (EmCommissionOutFeeDetailChangeModel m1 : feeList) {
			if (m1.getTempDate() != null) {
				list.add(m1.getTempDate());
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
	@NotifyChange({ "feeList", "m" })
	public void base_per_change() {
		List<Date> dateList = OrderByStartDate();
		Calc(dateList.get(0));
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
				feeList = calUtil.getEmCommissionOutItemFee(feeList);

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
	 * 时间批量修改
	 * 
	 * @param date
	 */
	@Command("dateAll")
	@NotifyChange({ "feeList" })
	public void dateAll(@BindingParam("date") Date date,
			@BindingParam("index") Integer index,
			@BindingParam("class") String sicl_class) {
		if (sicl_class.equals("all")) {
			date = feeList.get(0).getTempDate();
			index = 0;
		}
		for (int i = index + 1; i < feeList.size(); i++) {
			if (feeList.get(i).getSicl_class().equals(sicl_class)
					|| sicl_class.equals("all")) {
				feeList.get(i).setTempDate(date);
			}
		}
	}

	/**
	 * 计算总计
	 * 
	 */
	public EmCommissionOutChangeModel calSum() {
		// 重置总计
		m.setEcoc_sb_em_sum(new BigDecimal(0));
		m.setEcoc_sb_co_sum(new BigDecimal(0));
		m.setEcoc_sb_sum(new BigDecimal(0));
		m.setEcoc_gjj_em_sum(new BigDecimal(0));
		m.setEcoc_gjj_co_sum(new BigDecimal(0));
		m.setEcoc_gjj_sum(new BigDecimal(0));
		m.setEcoc_sum(new BigDecimal(0));
		m.setEcoc_welfare_sum(new BigDecimal(0));

		try {
			// 社保、公积金、福利
			for (EmCommissionOutFeeDetailChangeModel m1 : feeList) {
				// 社保
				if (m1.getSicl_class().equals("社会保险")) {
					// 社保企业总计
					m.setEcoc_sb_co_sum(m.getEcoc_sb_co_sum().add(
							m1.getEofc_co_sum()));
					// 社保个人总计
					m.setEcoc_sb_em_sum(m.getEcoc_sb_em_sum().add(
							m1.getEofc_em_sum()));
				}
				// 公积金
				else if (m1.getSicl_class().equals("公积金")) {
					// 公积金企业总计
					m.setEcoc_gjj_co_sum(m.getEcoc_gjj_co_sum().add(
							m1.getEofc_co_sum()));
					// 公积金个人总计
					m.setEcoc_gjj_em_sum(m.getEcoc_gjj_em_sum().add(
							m1.getEofc_em_sum()));
				}
				// 福利
				else if (m1.getSicl_class().equals("福利项目")) {
					m.setEcoc_welfare_sum(m.getEcoc_welfare_sum().add(
							m1.getEofc_month_sum()));
				}
				// 总计
				m.setEcoc_sum(m.getEcoc_sum().add(m1.getEofc_month_sum()));
			}
			// 社保总计
			m.setEcoc_sb_sum(m.getEcoc_sb_em_sum().add(m.getEcoc_sb_co_sum()));
			// 公积金总计
			m.setEcoc_gjj_sum(m.getEcoc_gjj_em_sum()
					.add(m.getEcoc_gjj_co_sum()));

		} catch (Exception e) {
			e.printStackTrace();
			Messagebox
					.show("费用总计出错!", "ERROR", Messagebox.OK, Messagebox.ERROR);
		}
		return m;
	}

	/**
	 * 提交
	 * 
	 * @param win
	 */
	@Command("submit")
	public void submit(@BindingParam("win") Window win) {
		EmCommissionOut_OperateBll bll = new EmCommissionOut_OperateBll();

		m.setEcoc_addname(UserInfo.getUsername());
		m.setEcoc_state(1);
		m.setEcoc_remark(m.getEcoc_remark1());
		m.setEcoc_title_date(DateStringChange.DatetoSting(OrderByStartDate()
				.get(0), "yyyy-MM-01"));
		m.setEcoc_soin_id(titleModel.getSoin_id());
		m.setEcoc_ecos_id(stardModel.getEcos_id());
		m.setEcoc_compact_f(compact_f == null ? null : DateStringChange
				.DatetoSting(compact_f, "yyyy-MM-dd"));
		m.setEcoc_compact_f(compact_f == null ? null : DateStringChange
				.DatetoSting(compact_f, "yyyy-MM-dd"));
		if (compact_l != null) {
			m.setEcoc_compact_l(DateStringChange.DatetoSting(compact_l,
					"yyyy-MM-dd"));
		} else {
			m.setEcoc_compact_l("无固定期限");
		}
		m.setRemark("重新提交;" + m.getEcoc_remark1());

		try {
			for (EmCommissionOutFeeDetailChangeModel m : feeList) {
				if (m.getTempDate() != null) {
					m.setEofc_start_date(DateStringChange.DatetoSting(
							m.getTempDate(), "yyyy-MM-01"));
				}
			}
			String[] str = bll.resub(m, feeList);

			if (str[0].equals("1")) {
				Messagebox.show(str[1], "INFORMATION", Messagebox.OK,
						Messagebox.INFORMATION);
				win.detach();
			} else {
				Messagebox.show(str[1], "ERROR", Messagebox.OK,
						Messagebox.ERROR);
			}
		} catch (Exception e) {
			e.printStackTrace();
			Messagebox.show("提交出错!", "ERROR", Messagebox.OK, Messagebox.ERROR);
		}
	}
	
	// 终止流程
		@Command("over")
		public void over(@BindingParam("win") Window win) throws SQLException {
			EmCommissionOut_ListDal dal = new EmCommissionOut_ListDal();
			eclist = new ListModelList<EmCommissionOutChangeModel>(
					dal.geteclist(m.getEcoc_tapr_id()));
			
			Map<String, String> map = new HashMap<String, String>();
			map.put("tali_id", String.valueOf(eclist.get(0).getEcoc_ecos_id()));
			map.put("tali_name", String.valueOf(eclist.get(0).getEcoc_addtype()));
			Window window = (Window) Executions.createComponents(
					"Task_StopTask.zul", null, map);
			window.doModal();
			win.detach();
		}

	public EmCommissionOutChangeModel getM() {
		return m;
	}

	public void setM(EmCommissionOutChangeModel m) {
		this.m = m;
	}

	public Integer getDaid() {
		return daid;
	}

	public void setDaid(Integer daid) {
		this.daid = daid;
	}

	public EmCommissionOutStandardModel getStardModel() {
		return stardModel;
	}

	public void setStardModel(EmCommissionOutStandardModel stardModel) {
		this.stardModel = stardModel;
	}

	public List<EmCommissionOutChangeModel> getTitleList() {
		return titleList;
	}

	public void setTitleList(List<EmCommissionOutChangeModel> titleList) {
		this.titleList = titleList;
	}

	public EmCommissionOutChangeModel getTitleModel() {
		return titleModel;
	}

	public void setTitleModel(EmCommissionOutChangeModel titleModel) {
		this.titleModel = titleModel;
	}

	public List<EmCommissionOutFeeDetailChangeModel> getFeeList() {
		return feeList;
	}

	public void setFeeList(List<EmCommissionOutFeeDetailChangeModel> feeList) {
		this.feeList = feeList;
	}

	public final EmCommissionOutChangeModel getOld_titleModel() {
		return old_titleModel;
	}

	public final void setOld_titleModel(
			EmCommissionOutChangeModel old_titleModel) {
		this.old_titleModel = old_titleModel;
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

	public final List<EmCommissionOutStandardModel> getStardList() {
		return stardList;
	}

	public final void setStardList(List<EmCommissionOutStandardModel> stardList) {
		this.stardList = stardList;
	}

	public final List<EmCommissionOutFeeDetailChangeModel> getFlList() {
		return flList;
	}

	public final void setFlList(List<EmCommissionOutFeeDetailChangeModel> flList) {
		this.flList = flList;
	}
}
