package Controller.EmCommissionOut;

import java.math.BigDecimal;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.zkoss.bind.annotation.BindingParam;
import org.zkoss.bind.annotation.Command;
import org.zkoss.bind.annotation.NotifyChange;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zul.Combobox;
import org.zkoss.zul.Label;
import org.zkoss.zul.ListModelList;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Radiogroup;
import org.zkoss.zul.Window;

import dal.EmCommissionOut.EmCommissionOut_ListDal;

import bll.EmCommissionOut.EmCommissionOutListBll;
import bll.EmCommissionOut.EmCommissionOut_OperateBll;
import Controller.systemWindowController;
import Model.EmCommissionOutModel;
import Model.EmCommissionOutChangeModel;
import Model.EmCommissionOutFeeDetailChangeModel;
import Model.EmCommissionOutPayModel;
import Model.EmCommissionOutStandardModel;
import Util.DateStringChange;
import Util.RedirectUtil;
import Util.SocialInsuranceEmCommissionOut;
import Util.UserInfo;

public class EmCommissionOutController {

	private EmCommissionOutChangeModel m = new EmCommissionOutChangeModel();
	Integer gid = 0;
	private List<EmCommissionOutChangeModel> subList = new ListModelList<>();

	private List<EmCommissionOutStandardModel> stardList;// 合同标准
	private EmCommissionOutStandardModel stardModel = new EmCommissionOutStandardModel();
	private List<EmCommissionOutChangeModel> titleList;// 当地标准
	private List<EmCommissionOutStandardModel> fuwulist;// 服务费列表
	private List<EmCommissionOutStandardModel> filelist;// 档案费列表
	private EmCommissionOutChangeModel titleModel = new EmCommissionOutChangeModel();// 获取标准详细信息
	private List<EmCommissionOutFeeDetailChangeModel> feeList = new ListModelList<>();
	private List<EmCommissionOutFeeDetailChangeModel> flList = new ListModelList<>();// 获取标准福利产品信息
	private List<EmCommissionOutChangeModel> cityList;// 城市
	private List<EmCommissionOutStandardModel> fuwu_fee;// 服务费
	private ListModelList<EmCommissionOutChangeModel> state_aut;// 判断是否有重复数据

	private boolean file_state = true;

	// 合同起始时间
	private Date compact_f;
	// 合同结束时间
	private Date compact_l;
	private String compact_l_str = "有固定期限";
	private boolean compact_l_vis;

	private boolean step1;
	private boolean step2;
	private boolean iscal;
	private int step;

	SocialInsuranceEmCommissionOut calUtil = new SocialInsuranceEmCommissionOut();

	public EmCommissionOutController() {
		try {
			gid = Integer.parseInt(Executions.getCurrent().getArg().get("gid")
					.toString());

			// 1；为员工业务中心，2；入职第二页
			step = Integer.parseInt(Executions.getCurrent().getArg()
					.get("step").toString());

			init();

		} catch (Exception e) {
			e.printStackTrace();
			Messagebox
					.show("页面加载出错!", "ERROR", Messagebox.OK, Messagebox.ERROR);
		}
	}

	/**
	 * 页面数据初始化
	 * 
	 */
	public void init() {
		EmCommissionOutListBll bll = new EmCommissionOutListBll();
		try {
			setM(bll.getEmbase(gid));
			m.setEcoc_sb_em_sum(new BigDecimal(0));
			m.setEcoc_sb_co_sum(new BigDecimal(0));
			m.setEcoc_sb_sum(new BigDecimal(0));
			m.setEcoc_gjj_em_sum(new BigDecimal(0));
			m.setEcoc_gjj_co_sum(new BigDecimal(0));
			m.setEcoc_gjj_sum(new BigDecimal(0));
			m.setEcoc_sum(new BigDecimal(0));
			m.setEcoc_welfare_sum(new BigDecimal(0));
			m.setEcoc_sb_base(new BigDecimal(0));
			m.setEcoc_house_base(new BigDecimal(0));
			m.setEcoc_salary(new BigDecimal(0));
			m.setGid(gid);
			setStardList(bll.getStardList(m.getCid(), m.getGid()));

			setCityList(bll.getCityList(m.getCid()));

			System.out.println(stardList.get(0).getWtss_archives());
			if (stardList.get(0).getWtss_archives().equals("不保管")
					|| stardList.get(0).getWtss_archives().equals("")) {
				file_state = true;
			} else {
				file_state = false;
			}

			compact_l_change();
			stepControl();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private Window winC;

	@Command("winC")
	public void winC(@BindingParam("a") Window winD) {
		winC = winD;

		if (stardList.size() == 0) {
			Messagebox.show("该公司未添加委托外地服务约定、委托外地服务费标准，请添加!", "操作提示",
					Messagebox.OK, Messagebox.ERROR);
			winC.detach();
		}
	}

	/**
	 * 关联委托标准表标准名
	 * 
	 * @param stardModel
	 */
	@Command("stardChange")
	@NotifyChange({ "titleList", "titleModel", "feeList", "m", "flList",
			"fuwulist", "stardModel", "file_state" })
	public void stardChange() {
		EmCommissionOutListBll bll = new EmCommissionOutListBll();
		// stardModel
		try {
			setStardList(bll.getStardListbyfeeid(stardModel.getEcos_id()));

			if (stardList.get(0).getWtss_archives().equals("不保管")
					|| stardList.get(0).getWtss_archives().equals("")) {
				file_state = true;
			} else {
				file_state = false;
			}

			setTitleList(bll.getTitleList(stardModel.getEcos_id()));
			setTitleModel(titleList.get(0));
			
			EmCommissionOut_ListDal dal = new EmCommissionOut_ListDal();
			state_aut = new ListModelList<EmCommissionOutChangeModel>(
					dal.getstate_aut(m.getGid(),stardModel.getEcos_id()));// 显示应付委托城市
			
			if(!state_aut.get(0).getEcoc_state().equals(0)){
				Messagebox.show("该员工已有该机构委托单，请误重复添加！", "操作提示", Messagebox.OK,
						Messagebox.ERROR);
				return;
			}

			// setFuwulist(bll.getfuwulist(m.getCid(),
			// stardModel.getEcos_cabc_id()));

			setFuwulist(bll.getfuwulist(m.getEcoc_ecos_id()));

			setFilelist(bll.getfilelist(m.getGid(),
					stardModel.getEcos_cabc_id()));

			// System.out.println("档案xx");
			// System.out.println(getFilelist().get(0).getEcos_archvie_fee());

			m.setEcoc_service_fee(stardModel.getEcos_service_fee());
			// m.setEcoc_file_fee(filelist.get(0).getEcos_archvie_fee());

			// 获取福利列表
			setFlList(bll.getflList(gid, stardModel.getCity(),
					stardModel.getCoab_name()));

			for (EmCommissionOutFeeDetailChangeModel m1 : flList) {
				if (m1.getEofc_start_date() == null) {
					// m1.setTempDate(new Date());
				}
			}

			titleChange();

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
	 * 关联委托标准表详细信息
	 * 
	 */
	@Command("titleChange")
	@NotifyChange({ "feeList", "m" })
	public void titleChange() {
		EmCommissionOutListBll bll = new EmCommissionOutListBll();
		try {
			
			if (titleModel.getSoin_id() != null) {
				// 获取服务项目
				setFeeList(bll.getNullSoClassList(titleModel.getSoin_id()));

				// 服务费
				EmCommissionOutFeeDetailChangeModel m1 = new EmCommissionOutFeeDetailChangeModel();
				m1.setEofc_name("服务费");
				m1.setSicl_class("委托出标准费用");
				m1.setEofc_month_sum(m.getEcoc_service_fee());
				m1.setEofc_em_base(new BigDecimal(0));
				m1.setEofc_co_base(new BigDecimal(0));
				if (m1.getTempDate() == null) {
					// m1.setTempDate(new Date());
				}
				m1.setEofc_sicl_id(22);
				feeList.add(m1);

				// 档案费
				// EmCommissionOutFeeDetailChangeModel m2 = new
				// EmCommissionOutFeeDetailChangeModel();
				// m2.setEofc_name("档案费");
				// m2.setSicl_class("委托出标准费用");
				// m2.setEofc_month_sum(m.getEcoc_file_fee());
				// m2.setEofc_em_base(new BigDecimal(0));
				// m2.setEofc_co_base(new BigDecimal(0));
				// if (m2.getTempDate() == null&&m2.getEofc_month_sum()!=null) {
				// m2.setTempDate(new Date());
				// }
				// m2.setEofc_sicl_id(23);
				// feeList.add(m2);

				// 福利项目
				feeList.addAll(flList);

				sb_baseChange();
				house_baseChange();

			} else {
				feeList = new ListModelList<>();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Command("file_st")
	@NotifyChange({ "file_state" })
	public void file_st(@BindingParam("file1") Combobox file1) {
		if (file1.getValue().equals("不保管")) {
			file_state = true;
		} else {
			file_state = false;
		}
	}

	// 控制档案费
	@Command("fileChange")
	@NotifyChange({ "feeList", "m" })
	public void fileChange(@BindingParam("ch1") Combobox ch1) {
		EmCommissionOutListBll bll = new EmCommissionOutListBll();
		try {
			if (ch1.getValue().equals("是")) {
				if (titleModel.getSoin_id() != null) {
					// 获取服务项目
					setFeeList(bll.getNullSoClassList(titleModel.getSoin_id()));

					// 服务费
					EmCommissionOutFeeDetailChangeModel m1 = new EmCommissionOutFeeDetailChangeModel();
					m1.setEofc_name("服务费");
					m1.setSicl_class("委托出标准费用");
					m1.setEofc_month_sum(m.getEcoc_service_fee());
					m1.setEofc_em_base(new BigDecimal(0));
					m1.setEofc_co_base(new BigDecimal(0));
					if (m1.getTempDate() == null) {
						// m1.setTempDate(new Date());
					}
					m1.setEofc_sicl_id(22);
					feeList.add(m1);

					// 档案费
					EmCommissionOutFeeDetailChangeModel m2 = new EmCommissionOutFeeDetailChangeModel();
					m2.setEofc_name("档案费");
					m2.setSicl_class("委托出标准费用");
					m2.setEofc_month_sum(getFilelist().get(0)
							.getEcos_archvie_fee());
					m2.setEofc_em_base(new BigDecimal(0));
					m2.setEofc_co_base(new BigDecimal(0));
					if (m2.getTempDate() == null
							&& m2.getEofc_month_sum() != null) {
						// m2.setTempDate(new Date());
					}
					m2.setEofc_sicl_id(23);
					feeList.add(m2);

					// 福利项目
					feeList.addAll(flList);

					sb_baseChange();
					house_baseChange();

				} else {
					feeList = new ListModelList<>();
				}
			} else {
				if (titleModel.getSoin_id() != null) {
					// 获取服务项目
					setFeeList(bll.getNullSoClassList(titleModel.getSoin_id()));

					// 服务费
					EmCommissionOutFeeDetailChangeModel m1 = new EmCommissionOutFeeDetailChangeModel();
					m1.setEofc_name("服务费");
					m1.setSicl_class("委托出标准费用");
					m1.setEofc_month_sum(m.getEcoc_service_fee());
					m1.setEofc_em_base(new BigDecimal(0));
					m1.setEofc_co_base(new BigDecimal(0));
					if (m1.getTempDate() == null) {
						// m1.setTempDate(new Date());
					}
					m1.setEofc_sicl_id(22);
					feeList.add(m1);

					// 档案费
					// EmCommissionOutFeeDetailChangeModel m2 = new
					// EmCommissionOutFeeDetailChangeModel();
					// m2.setEofc_name("档案费");
					// m2.setSicl_class("委托出标准费用");
					// m2.setEofc_month_sum(m.getEcoc_file_fee());
					// m2.setEofc_em_base(new BigDecimal(0));
					// m2.setEofc_co_base(new BigDecimal(0));
					// if (m2.getTempDate() == null
					// && m2.getEofc_month_sum() != null) {
					// m2.setTempDate(new Date());
					// }
					// m2.setEofc_sicl_id(23);
					// feeList.add(m2);

					// 福利项目
					feeList.addAll(flList);

					sb_baseChange();
					house_baseChange();

				} else {
					feeList = new ListModelList<>();
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 社保基数变更
	 * 
	 * @throws SQLException
	 * 
	 */
	@Command("sb_baseChange")
	@NotifyChange({ "feeList", "m" })
	public void sb_baseChange() throws SQLException {
		if (m.getEcoc_sb_base() != null) {
			if (m.getEcoc_sb_base().compareTo(new BigDecimal(0)) == 1) {
				for (EmCommissionOutFeeDetailChangeModel m1 : feeList) {
					if (m1.getSicl_class().equals("社会保险")) {
						EmCommissionOut_ListDal dal = new EmCommissionOut_ListDal();
						List<EmCommissionOutModel> list = dal.getgdate(gid,
								"社会保险服务");

						m1.setEofc_em_base(m.getEcoc_sb_base());
						m1.setEofc_co_base(m.getEcoc_sb_base());
						if (m1.getTempDate() == null) {
							try {
								// m1.setTempDate(list.get(0).getTitle_date());
							} catch (Exception e) {
								// TODO: handle exception
							}

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
		dateboxHandle();
	}

	/**
	 * 公积金基数变更
	 * 
	 * @throws SQLException
	 * 
	 */
	@Command("house_baseChange")
	@NotifyChange({ "feeList", "m" })
	public void house_baseChange() throws SQLException {
		if (m.getEcoc_house_base() != null) {
			if (m.getEcoc_house_base().compareTo(new BigDecimal(0)) == 1) {
				for (EmCommissionOutFeeDetailChangeModel m1 : feeList) {
					if (m1.getSicl_class().equals("公积金")
							|| m1.getSicl_class().equals("补充公积金")) {
						EmCommissionOut_ListDal dal = new EmCommissionOut_ListDal();
						List<EmCommissionOutModel> list = dal.getgdate(gid,
								"住房公积金服务");

						m1.setEofc_em_base(m.getEcoc_house_base());
						m1.setEofc_co_base(m.getEcoc_house_base());
						if (m1.getTempDate() == null) {
							try {
								if (m1.getEofc_name().equals("住房公积金")) {
									// m1.setTempDate(list.get(0).getTitle_date());
								}
							} catch (Exception e) {
								// TODO: handle exception
							}

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
		dateboxHandle();
	}

	/**
	 * 社保基数变更
	 * 
	 * @param m
	 */
	public void sb_baseChange(EmCommissionOutChangeModel m) {
		if (m != null) {
			if (m.getEcoc_sb_base() != null) {
				for (EmCommissionOutFeeDetailChangeModel m1 : m.getFeeList()) {
					if (m1.getSicl_class().equals("社会保险")
							&& m1.getEofc_co_base() != null
							&& m1.getEofc_em_base() != null) {
						m1.setEofc_em_base(m.getEcoc_sb_base());
						m1.setEofc_co_base(m.getEcoc_sb_base());
					}
				}
			}
		}
	}

	/**
	 * 公积金基数变更
	 * 
	 * @param m
	 */
	public void house_baseChange(EmCommissionOutChangeModel m) {
		if (m != null) {
			if (m.getEcoc_house_base() != null) {
				for (EmCommissionOutFeeDetailChangeModel m1 : m.getFeeList()) {
					if (m1.getSicl_class().equals("公积金")
							&& m1.getEofc_co_base() != null
							&& m1.getEofc_em_base() != null) {
						m1.setEofc_em_base(m.getEcoc_house_base());
						m1.setEofc_co_base(m.getEcoc_house_base());
					}
				}
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
			try {
				date = feeList.get(0).getTempDate();
				index = 0;
			} catch (Exception e) {
				// TODO: handle exception
			}

		}
		try {
			for (int i = index + 1; i < feeList.size(); i++) {
				if (feeList.get(i).getSicl_class().equals(sicl_class)
						|| sicl_class.equals("all")) {
					feeList.get(i).setTempDate(date);
				}
			}
		} catch (Exception e) {
			// TODO: handle exception
		}
	}

	/**
	 * 计算总计
	 * 
	 */
	public EmCommissionOutChangeModel calSum(EmCommissionOutChangeModel m) {
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
			for (EmCommissionOutFeeDetailChangeModel m1 : m.getFeeList()) {
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
				m.setEcoc_sum(m.getEcoc_sb_em_sum().add(
						m.getEcoc_sb_co_sum().add(
								m.getEcoc_gjj_em_sum().add(
										m.getEcoc_gjj_co_sum().add(
												m.getEcoc_service_fee())))));
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
	 * 判断项目起始日是否都为空
	 * 
	 * @return
	 */
	public boolean isdateallnull() {
		boolean isDateAllNull = true;
		for (int i = 0; i < feeList.size(); i++) {
			if (feeList.get(i).getTempDate() != null) {
				isDateAllNull = false;
			}
		}
		return isDateAllNull;
	}

	/**
	 * 提交下一步
	 * 
	 */
	@Command("next")
	@NotifyChange({ "step1", "step2", "subList" })
	public void next(@BindingParam("win") Window win) {
		try {
			if (stardModel == null || titleModel.getSoin_id() == null) {
				Messagebox.show("请选择合同标准和当地标准!", "ERROR", Messagebox.OK,
						Messagebox.ERROR);
			} else if (isdateallnull()) {
				Messagebox.show("请输入至少一个起始日!", "ERROR", Messagebox.OK,
						Messagebox.ERROR);
			} else if (m.getEcoc_phone().equals("")
					&& m.getEcoc_mobile().equals("")) {
				Messagebox.show("工作电话，个人手机请至少填写一个!", "ERROR", Messagebox.OK,
						Messagebox.ERROR);
			} else if (compact_f == null) {
				Messagebox.show("合同开始时间不能为空", "ERROR", Messagebox.OK,
						Messagebox.ERROR);
			} else if (compact_l_str.equals("有固定期限") && compact_l == null) {
				Messagebox.show("合同结束时间不能为空", "ERROR", Messagebox.OK,
						Messagebox.ERROR);
			} else {

				try {

					// 调用分单方法
					// subList = getEmOutList();
					subList = getEmOutChangeList();

					// 合同日期
					m.setEcoc_compact_f(compact_f == null ? null
							: DateStringChange.DatetoSting(compact_f,
									"yyyy-MM-dd"));
					if (compact_l != null) {
						m.setEcoc_compact_l(DateStringChange.DatetoSting(
								compact_l, "yyyy-MM-dd"));
					} else {
						m.setEcoc_compact_l("无固定期限");
					}

					// 步骤控制
					stepControl();

				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
	}

	/**
	 * 委托外地按照起始日分单
	 * 
	 * @return
	 */
	Date newdate = null;

	public List<EmCommissionOutChangeModel> getEmOutChangeList() {
		EmCommissionOutListBll bll = new EmCommissionOutListBll();
		List<EmCommissionOutChangeModel> subList = new ListModelList<>();
		List<EmCommissionOutFeeDetailChangeModel> list = new ArrayList<>();
		Date date = null;

		Integer count = 0;

		try {
			// 新增委托单分单代码块
			add: {
				list = feelistCopy();
				// 去除起始日为空的项目
				for (int i = 0; i < list.size(); i++) {
					if (list.get(i).getTempDate() == null) {
						list.remove(i);
						i--;
					}
				}

				if (list.size() > 0) {
					// 冒泡排序，从小到大
					for (int i = 0; i < list.size() - 1; i++) {
						for (int j = 0; j < list.size() - 1 - i; j++) {
							if (list.get(j).getTempDate()
									.after(list.get(j + 1).getTempDate())) {
								EmCommissionOutFeeDetailChangeModel tempM = list
										.get(j + 1);
								list.set(j + 1, list.get(j));
								list.set(j, tempM);
							}
						}
					}
					// 获取最小起始日
					date = list.get(0).getTempDate();

					list = feelistCopy();
					EmCommissionOutChangeModel subM = new EmCommissionOutChangeModel();

					// 新增委托单字段数据处理
					subM.setTitle_date(date);
					subM.setEcoc_addtype("新增");
					subM.setEcoc_type("ecocadd");
					for (EmCommissionOutFeeDetailChangeModel m1 : list) {
						if (m1.getTempDate() != null) {

							if (DateStringChange.comparedate(m1.getTempDate(),
									DateStringChange.Datenow()) == 1) {

								/*
								 * m1.setEofc_start_date(DateStringChange.
								 * DatetoSting( DateStringChange.Datenow(),
								 * "yyyy-MM-01"));
								 */
								m1.setEofc_start_date(DateStringChange
										.DatetoSting(m1.getTempDate(),
												"yyyy-MM-01"));

								newdate = DateStringChange.StringtoDate(
										DateStringChange.DatetoSting(
												DateStringChange.Datenow(),
												"yyyy-MM-01"), "yyyy-MM-01");

							} else {
								m1.setEofc_start_date(DateStringChange
										.DatetoSting(m1.getTempDate(),
												"yyyy-MM-01"));
								newdate = date;
							}

							/*
							 * m1.setEofc_start_date(DateStringChange.DatetoSting
							 * ( m1.getTempDate(), "yyyy-MM-01"));
							 */
						}
					}
					subM = submHandle(subM, list, newdate);

					// 添加新增委托单
					subList.add(subM);
					count++;
					/*
					 * // 调整委托单代码块 change: { List<EmCommissionOutChangeModel>
					 * alList; try { // 判断最小起始日到当月，是否有变更算法 alList =
					 * bll.getSoInAl(titleModel.getSoin_id(),
					 * DateStringChange.DatetoSting(date, "yyyy-MM-01"),
					 * DateStringChange .DatetoSting(new Date(), "yyyy-MM-01"));
					 * } catch (Exception e) { break change; }
					 * 
					 * if (alList.size() > 1) { for (int i = 1; i <
					 * alList.size(); i++) { EmCommissionOutChangeModel subM1 =
					 * new EmCommissionOutChangeModel();
					 * 
					 * date = DateStringChange.StringtoDate(alList
					 * .get(i).getSial_execdate(), "yyyy-MM-01"); list =
					 * feelistCopy();
					 * 
					 * // 处理调整单字段数据 subM1.setEcoc_addtype("调整");
					 * subM1.setEcoc_type("ecocchange");
					 * subM1.setTitle_date(date); for
					 * (EmCommissionOutFeeDetailChangeModel m1 : list) { if
					 * (m1.getTempDate() != null) { if
					 * (m1.getTempDate().before(date) || m1.getTempDate()
					 * .equals(date)) { m1.setEofc_start_date(DateStringChange
					 * .DatetoSting(date, "yyyy-MM-01")); } else {
					 * m1.setEofc_start_date(DateStringChange .DatetoSting(
					 * m1.getTempDate(), "yyyy-MM-01")); } } }
					 * 
					 * subM1 = submHandle(subM1, list, date);
					 * 
					 * // 调整委托单 subList.add(subM1); count++; } } }
					 */
					// 调用补缴方法
					bj(list, subList);
				} else {
					break add;
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		return subList;
	}

	/**
	 * 判断是否需要补缴，并加入补缴数据
	 * 
	 * @param list
	 *            起始日列表，已去除空值、从小到大排序
	 */
	public List<EmCommissionOutChangeModel> bj(
			List<EmCommissionOutFeeDetailChangeModel> list,
			List<EmCommissionOutChangeModel> subList) {
		try {
			// 当前时间
			Calendar c = Calendar.getInstance();
			c.setTime(new Date());
			// 最早起始日
			Calendar c1 = Calendar.getInstance();
			c1.setTime(list.get(0).getTempDate());

			// 月份差值=(年份差)*12+(月份差)
			Integer forcount = ((c.get(Calendar.YEAR) - (c1.get(Calendar.YEAR)))
					* 12 + c.get(Calendar.MONTH) + 1)
					- (c1.get(Calendar.MONTH) + 1);

			// 差值大于0，则需要添加补缴数据
			if (forcount > 0) {
				Date date = list.get(0).getTempDate();
				for (int i = 0; i < forcount; i++) {
					EmCommissionOutChangeModel subM = new EmCommissionOutChangeModel();
					list.clear();
					list = feelistCopy();

					subM.setTitle_date(date);
					subM.setEcoc_addtype("补缴");
					subM.setEcoc_state(1);
					for (EmCommissionOutFeeDetailChangeModel m1 : list) {
						if (m1.getTempDate() != null) {
							if (m1.getTempDate()
									.before(DateStringChange.StringtoDate(
											DateStringChange
													.Datestringnow("yyyy-MM-01"),
											"yyyy-MM-01"))) {
								m1.setEofc_start_date(DateStringChange
										.DatetoSting(date, "yyyy-MM-01"));
							} else {
								m1.setEofc_co_base(null);
								m1.setEofc_em_base(null);
							}
						}
					}

					subM = submHandle(subM, list, date);

					subList.add(subM);

					// 月份加1
					Calendar datec = Calendar.getInstance();
					datec.setTime(date);
					datec.add(2, 1);
					date = datec.getTime();
				}

				// 加入补缴收费月份
				for (EmCommissionOutChangeModel ecm : subList) {
					if (ecm.getEcoc_addtype().equals("补缴")) {
						ecm.setEcoc_bjmonth(DateStringChange.DatetoSting(date,
								"yyyyMM"));
					}
				}
			}

		} catch (Exception e) {
			e.printStackTrace();
		}

		return subList;
	}

	/**
	 * 委托单model处理(计算、赋值...)
	 * 
	 * @param subM
	 * @param list
	 * @param title_date
	 * @return
	 */
	public EmCommissionOutChangeModel submHandle(
			EmCommissionOutChangeModel subM,
			List<EmCommissionOutFeeDetailChangeModel> list, Date title_date) {
		// 设置计算信息
		if (calUtil.setCalculator(titleModel.getSoin_id(), title_date,
				stardModel.getEcos_shebao_feetype(),
				stardModel.getEcos_gjj_feetype())) {

			// 计算费用
			list = calUtil.getEmCommissionOutItemFee(list);

			fieldhandle(subM);

			subM.setFeeList(list);
		// 总计
			subM = calSum(subM);
		}

		return subM;
	}

	/**
	 * 复制feeList
	 * 
	 * @param list
	 * @return
	 */
	public List<EmCommissionOutFeeDetailChangeModel> feelistCopy() {
		List<EmCommissionOutFeeDetailChangeModel> list = new ListModelList<>();
		for (int i = 0; i < feeList.size(); i++) {
			EmCommissionOutFeeDetailChangeModel m1 = new EmCommissionOutFeeDetailChangeModel();
			EmCommissionOutFeeDetailChangeModel m2 = new EmCommissionOutFeeDetailChangeModel();

			m1 = feeList.get(i);

			m2.setEofc_name(m1.getEofc_name());
			m2.setEofc_sicl_id(m1.getEofc_sicl_id());
			m2.setSicl_class(m1.getSicl_class());
			m2.setEofc_em_base(m1.getEofc_em_base());
			m2.setEofc_co_base(m1.getEofc_co_base());
			m2.setEofc_em_sum(m1.getEofc_em_sum());
			m2.setEofc_co_sum(m1.getEofc_co_sum());
			m2.setTempDate(m1.getTempDate());
			m2.setEofc_month_sum(m1.getEofc_month_sum());
			m2.setEofc_content(m1.getEofc_content());
			m2.setEofc_state(1);
			m2.setEofc_ecop_id(m1.getEofc_ecop_id());

			list.add(m2);
		}

		return list;
	}

	/**
	 * 单条项目费用明细复制
	 * 
	 * @param m1
	 * @param m2
	 * @return
	 */
	public EmCommissionOutFeeDetailChangeModel feedetailCopy(
			EmCommissionOutFeeDetailChangeModel m, String start_date) {

		EmCommissionOutFeeDetailChangeModel m1 = new EmCommissionOutFeeDetailChangeModel();

		m1.setEofc_name(m.getEofc_name());
		m1.setEofc_sicl_id(m.getEofc_sicl_id());
		m1.setSicl_class(m.getSicl_class());
		m1.setEofc_em_base(m.getEofc_em_base());
		m1.setEofc_co_base(m.getEofc_co_base());
		m1.setEofc_em_sum(m.getEofc_em_sum());
		m1.setEofc_co_sum(m.getEofc_co_sum());
		m1.setEofc_month_sum(m.getEofc_month_sum());
		m1.setEofc_content(m.getEofc_content());
		m1.setEofc_start_date(start_date);

		return m1;
	}

	/**
	 * 字段处理
	 * 
	 */
	public void fieldhandle(EmCommissionOutChangeModel subM) {
		try {
			subM.setGid(gid);
			subM.setCid(m.getCid());
			subM.setEcoc_idcard(m.getEcoc_idcard());
			subM.setEcoc_compact_jud(m.getEcoc_compact_jud());
			subM.setEcoc_mobile(m.getEcoc_mobile());
			subM.setEcoc_phone(m.getEcoc_phone());
			subM.setEcoc_domicile(m.getEcoc_domicile());
			subM.setEcoc_email(m.getEcoc_email());
			subM.setEcoc_salary(m.getEcoc_salary());
			subM.setEcoc_remark(m.getEcoc_remark());
			subM.setEcoc_sb_base(m.getEcoc_sb_base());
			subM.setEcoc_house_base(m.getEcoc_house_base());
			subM.setEcoc_soin_id(titleModel.getSoin_id());
			subM.setEcoc_ecos_id(stardModel.getEcos_id());
			subM.setEcoc_service_fee(m.getEcoc_service_fee());
			subM.setEcoc_file_fee(m.getEcoc_file_fee());
			subM.setEcoc_laststate(0);
			subM.setEcoc_state(1);
			subM.setEcoc_addname(UserInfo.getUsername());
			subM.setEcoc_compact_f(compact_f == null ? null : DateStringChange
					.DatetoSting(compact_f, "yyyy-MM-dd"));
			if (compact_l != null) {
				subM.setEcoc_compact_l(DateStringChange.DatetoSting(compact_l,
						"yyyy-MM-dd"));
			} else {
				subM.setEcoc_compact_l("无固定期限");
			}
			subM.setEcoc_title_date(DateStringChange.DatetoSting(
					subM.getTitle_date(), "yyyy-MM-01"));
			subM.setEcoc_name("委托外地" + subM.getEcoc_addtype() + "("
					+ subM.getEcoc_title_date() + ")");
			subM.setEcoc_client(m.getEcoc_client());
			subM.setRemark(m.getEcoc_remark());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 判断是否显示datebox
	 * 
	 */
	public void dateboxHandle() {
		for (EmCommissionOutFeeDetailChangeModel m1 : feeList) {
			if ((m.getEcoc_sb_base().compareTo(new BigDecimal(0)) == 0 && m1
					.getSicl_class().equals("社会保险"))
					|| (m.getEcoc_house_base().compareTo(new BigDecimal(0)) == 0 && m1
							.getSicl_class().equals("公积金"))) {
				m1.setIsdate(false);
			} else {
				m1.setIsdate(true);
			}
		}
	}

	/**
	 * 步骤控制
	 * 
	 */
	private void stepControl() {
		if (subList == null || subList.size() == 0) {
			step1 = true;
			step2 = false;
		} else {
			step2 = true;
			step1 = false;
		}
	}

	/**
	 * 记录是否改动基数或比例
	 * 
	 */
	@Command("fieldchange")
	@NotifyChange({ "iscal" })
	public void fieldchange(
			@BindingParam("each") EmCommissionOutChangeModel each) {
		try {
			// 社保、公积金基数变更
			sb_baseChange(each);
			house_baseChange(each);

			setIscal(true);
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}

	}

	/**
	 * 计算费用
	 * 
	 * @param m
	 */
	@Command("feeCalc")
	@NotifyChange({ "subList", "iscal" })
	public void feeCalc() {
		String eobj_cp = null;

		String eobj_op = null;

		SocialInsuranceEmCommissionOut calUtil = new SocialInsuranceEmCommissionOut();

		List<EmCommissionOutFeeDetailChangeModel> lsfeelist = new ListModelList<>();
		lsfeelist.clear();
		for (EmCommissionOutChangeModel m : subList) {

			// 补充补缴的公积金基数
			if (m.getEcoc_addtype().equals("新增")) {
				for (EmCommissionOutFeeDetailChangeModel m1 : m.getFeeList()) {
					if (m1.getEofc_name().equals("住房公积金")) {
						eobj_cp = m1.getEofc_cp();
						eobj_op = m1.getEofc_op();
					}

				}
			} else if (m.getEcoc_addtype().equals("补缴")) {
				lsfeelist.clear();
				for (EmCommissionOutFeeDetailChangeModel mbj1 : m.getFeeList()) {
					if (mbj1.getEofc_name().equals("住房公积金")) {
						if (mbj1.getEofc_cp() == null
								|| mbj1.getEofc_cp().endsWith("0")) {
							mbj1.setEofc_cp(eobj_cp);
						}

						if (mbj1.getEofc_op() == null
								|| mbj1.getEofc_op().endsWith("0")) {
							mbj1.setEofc_op(eobj_op);
						}
					}

					lsfeelist.add(mbj1);

				}
				m.setFeeList(lsfeelist);
			}

			// 设置计算信息
			/*
			 * calUtil.setCalculator(titleModel.getSoin_id(), DateStringChange
			 * .StringtoDate(m.getEcoc_title_date(), "yyyy-MM-01"),
			 * stardModel.getEcos_shebao_feetype(), stardModel
			 * .getEcos_gjj_feetype());
			 */

			newdate = DateStringChange.StringtoDate(DateStringChange
					.DatetoSting(DateStringChange.Datenow(), "yyyy-MM-01"),
					"yyyy-MM-01");

			calUtil.setCalculator(titleModel.getSoin_id(), DateStringChange
					.StringtoDate((DateStringChange.DatetoSting(newdate,
							"yyyy-MM-01")), "yyyy-MM-01"), stardModel
					.getEcos_shebao_feetype(), stardModel.getEcos_gjj_feetype());

			// 计算费用
			m.setFeeList(calUtil.getEmCommissionOutItemFee(m.getFeeList()));

			// 总计
			m = calSum(m);
		}
		setIscal(false);
	}

	/**
	 * 上一步
	 * 
	 * @param win
	 */
	@Command("back")
	@NotifyChange({ "step1", "step2", "subList" })
	public void back(@BindingParam("win") Window win) {
		try {
			subList.clear();
			stepControl();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 提交
	 * 
	 */
	@Command("submit")
	@NotifyChange({ "step1", "step2", "subList", "m", "feeList", "stardList",
			"titleList", "compact_f", "compact_l", "stardModel", "titleModel" })
	public void submit() {

		if (subList.get(0).getEcoc_gjj_sum().compareTo(BigDecimal.ZERO) == 0
				&& m.getEcoc_house_base().compareTo(BigDecimal.ZERO) != 0) {
			Messagebox.show("请选择公积金比例！", "INFORMATION", Messagebox.OK,
					Messagebox.ERROR);
			return;
		}

		if (Messagebox.show("是否确认提交?!", "CONFIRM", Messagebox.OK
				| Messagebox.CANCEL, Messagebox.QUESTION) == Messagebox.OK) {

			EmCommissionOut_OperateBll bll = new EmCommissionOut_OperateBll();
			try {
				Integer count = bll.add(subList, m.getEmba_name(),
						stardModel.getCity());

				if (count > 0) {
					Messagebox.show("成功提交了" + count + "条委托单!", "INFORMATION",
							Messagebox.OK, Messagebox.INFORMATION);
					allclear();
					RedirectUtil util = new RedirectUtil();
					if (step == 1) {
						// 员工业务中心跳转方法:
						util.refreshEmUrl("/EmCommissionOut/EmCommissionOut_EmSelectList.zul");// url为跳转页面连接
					} else if (step == 2) {

						// 入职第二页跳转方法:
						util.refreshEntrySecondUrl("/EmCommissionOut/EmCommissionOut_EmSelectList.zul");// url为跳转页面连接
					}

				} else {
					Messagebox.show("委托单新增失败，请勿关于此窗口，5秒后在重新提交!", "ERROR",
							Messagebox.OK, Messagebox.ERROR);
				}
			} catch (Exception e) {
				e.printStackTrace();
				Messagebox.show("提交出错!", "ERROR", Messagebox.OK,
						Messagebox.ERROR);
			}
		}
	}

	/**
	 * 全部清空
	 * 
	 */
	public void allclear() {
		m = null;
		feeList.clear();
		subList.clear();
		stardList.clear();
		stardModel = null;
		titleList.clear();
		titleModel = null;
		flList.clear();
		compact_f = null;
		compact_l = null;
		init();
	}

	public EmCommissionOutChangeModel getM() {
		return m;
	}

	public void setM(EmCommissionOutChangeModel m) {
		this.m = m;
	}

	public List<EmCommissionOutStandardModel> getStardList() {
		return stardList;
	}

	public void setStardList(List<EmCommissionOutStandardModel> stardList) {
		this.stardList = stardList;
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

	public Date getCompact_f() {
		return compact_f;
	}

	public void setCompact_f(Date compact_f) {
		this.compact_f = compact_f;
	}

	public Date getCompact_l() {
		return compact_l;
	}

	public void setCompact_l(Date compact_l) {
		this.compact_l = compact_l;
	}

	public String getCompact_l_str() {
		return compact_l_str;
	}

	public void setCompact_l_str(String compact_l_str) {
		this.compact_l_str = compact_l_str;
	}

	public boolean isCompact_l_vis() {
		return compact_l_vis;
	}

	public void setCompact_l_vis(boolean compact_l_vis) {
		this.compact_l_vis = compact_l_vis;
	}

	public List<EmCommissionOutFeeDetailChangeModel> getFeeList() {
		return feeList;
	}

	public void setFeeList(List<EmCommissionOutFeeDetailChangeModel> feeList) {
		this.feeList = feeList;
	}

	public List<EmCommissionOutFeeDetailChangeModel> getFlList() {
		return flList;
	}

	public void setFlList(List<EmCommissionOutFeeDetailChangeModel> flList) {
		this.flList = flList;
	}

	public EmCommissionOutStandardModel getStardModel() {
		return stardModel;
	}

	public void setStardModel(EmCommissionOutStandardModel stardModel) {
		this.stardModel = stardModel;
	}

	public boolean isStep1() {
		return step1;
	}

	public void setStep1(boolean step1) {
		this.step1 = step1;
	}

	public boolean isStep2() {
		return step2;
	}

	public void setStep2(boolean step2) {
		this.step2 = step2;
	}

	public List<EmCommissionOutChangeModel> getSubList() {
		return subList;
	}

	public void setSubList(List<EmCommissionOutChangeModel> subList) {
		this.subList = subList;
	}

	public boolean isIscal() {
		return iscal;
	}

	public void setIscal(boolean iscal) {
		this.iscal = iscal;
	}

	public List<EmCommissionOutChangeModel> getCityList() {
		return cityList;
	}

	public void setCityList(List<EmCommissionOutChangeModel> cityList) {
		this.cityList = cityList;
	}

	public List<EmCommissionOutStandardModel> getFuwulist() {
		return fuwulist;
	}

	public void setFuwulist(List<EmCommissionOutStandardModel> fuwulist) {
		this.fuwulist = fuwulist;
	}

	public List<EmCommissionOutStandardModel> getFuwu_fee() {
		return fuwu_fee;
	}

	public void setFuwu_fee(List<EmCommissionOutStandardModel> fuwu_fee) {
		this.fuwu_fee = fuwu_fee;
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

	// /**
	// * 委托外地按照起始日分单
	// *
	// * @return
	// */
	// public List<EmCommissionOutChangeModel> getEmOutList() {
	//
	// EmCommissionOutListBll bll = new EmCommissionOutListBll();
	// List<EmCommissionOutChangeModel> subList = new ListModelList<>();
	// List<EmCommissionOutFeeDetailChangeModel> list = new ArrayList<>();
	// Integer a = 0;
	// Integer flag = 0;
	//
	// try {
	// fora: {
	// try {
	// list = feelistCopy();
	//
	// // 去除没有起始日的项目
	// for (int i = 0; i < list.size(); i++) {
	// if (list.get(i).getTempDate() == null) {
	// list.remove(i);
	// i--;
	// }
	// }
	//
	// // 判断起始日是否都一样
	// for (int i = 0; i < list.size(); i++) {
	// for (int j = i + 1; j < list.size(); j++) {
	// if (!list.get(i).getTempDate()
	// .equals(list.get(j).getTempDate())) {
	// flag = 1;
	// break fora;
	// }
	// }
	// }
	// } catch (Exception e) {
	// e.printStackTrace();
	// }
	// }
	//
	// if (flag == 0) {
	// // 起始日都一样
	// List<EmCommissionOutChangeModel> alList = new ListModelList<>();
	//
	// // 根据起始日到现在月份的时间段，获取使用的算法列表
	// try {
	// alList = bll.getSoInAl(titleModel.getSoin_id(),
	// DateStringChange.DatetoSting(list.get(0)
	// .getTempDate(), "yyyy-MM-01"),
	// DateStringChange.DatetoSting(new Date(),
	// "yyyy-MM-01"));
	// } catch (Exception e) {
	// e.printStackTrace();
	// }
	//
	// // 循环创建委托单
	// for (int i = 0; i < alList.size(); i++) {
	// EmCommissionOutChangeModel subM = new EmCommissionOutChangeModel();
	//
	// try {
	// list = feelistCopy();
	//
	// // 处理起始日
	// subM.setTitle_date(DateStringChange.StringtoDate(alList
	// .get(i).getSial_execdate(), "yyyy-MM-01"));
	// for (EmCommissionOutFeeDetailChangeModel m1 : list) {
	// if (m1.getTempDate() != null) {
	// m1.setEofc_start_date(DateStringChange
	// .DatetoSting(subM.getTitle_date(),
	// "yyyy-MM-01"));
	// }
	// }
	//
	// // 服务费、档案费
	// subM.setEcoc_service_fee(m.getEcoc_service_fee());
	// subM.setEcoc_file_fee(m.getEcoc_file_fee());
	//
	// // 委托单类型
	// if (i == 0) {
	// // 委托外地新增
	// subM.setEcoc_addtype("新增");
	// } else {
	// // 委托外地调整
	// subM.setEcoc_addtype("调整");
	// }
	//
	// // 计算、处理subM
	// submHandle(subM, list, DateStringChange.StringtoDate(
	// alList.get(i).getSial_execdate(), "yyyy-MM-01"));
	//
	// // 添加委托单信息和项目详情
	// subList.add(subM);
	// } catch (Exception e) {
	// e.printStackTrace();
	// }
	// }
	// } else if (flag == 1) {
	//
	// try {
	//
	// // 起始日不一样
	// list = feelistCopy();
	//
	// // 去空值
	// for (int i = 0; i < list.size(); i++) {
	// if (list.get(i).getTempDate() == null) {
	// list.remove(i);
	// i--;
	// }
	// }
	//
	// // 冒泡排序，从小到大
	// for (int i = 0; i < list.size() - 1; i++) {
	// for (int j = 0; j < list.size() - 1 - i; j++) {
	// if (list.get(j).getTempDate()
	// .after(list.get(j + 1).getTempDate())) {
	// EmCommissionOutFeeDetailChangeModel tempM = list
	// .get(j + 1);
	// list.set(j + 1, list.get(j));
	// list.set(j, tempM);
	// }
	// }
	// }
	//
	// // 最晚起始日
	// Calendar c = Calendar.getInstance();
	// c.setTime(list.get(list.size() - 1).getTempDate());
	// // 最早起始日
	// Calendar c1 = Calendar.getInstance();
	// c1.setTime(list.get(0).getTempDate());
	//
	// // 月份差值=(年份差)*12+(月份差)+1
	// Integer forcount = ((c.get(Calendar.YEAR) - (c1
	// .get(Calendar.YEAR))) * 12 + c.get(Calendar.MONTH) + 1)
	// - (c1.get(Calendar.MONTH) + 1) + 1;
	//
	// // 跨越的算法
	// List<EmCommissionOutChangeModel> alList = new ListModelList<>();
	// if (c.before(new Date())) {
	// c.setTime(new Date());
	// }
	// alList = bll.getSoInAl(titleModel.getSoin_id(),
	// DateStringChange.DatetoSting(c1.getTime(),
	// "yyyy-MM-01"), DateStringChange
	// .DatetoSting(c.getTime(), "yyyy-MM-01"));
	//
	// // 起始日
	// Date title_date = list.get(0).getTempDate();
	//
	// // 循环创建委托单
	// for (int i = 0; i < forcount; i++) {
	//
	// EmCommissionOutChangeModel subM = new EmCommissionOutChangeModel();
	//
	// // 委托外地新增
	// if (i == 0) {
	// // 费用详情列表
	// list = feelistCopy();
	//
	// for (int j = 0; j < feeList.size(); j++) {
	// EmCommissionOutFeeDetailChangeModel m1 = list
	// .get(j);
	//
	// if (m1.getTempDate() == null
	// || DateStringChange.StringtoDate(
	// DateStringChange.DatetoSting(
	// m1.getTempDate(),
	// "yyyy-MM-01"),
	// "yyyy-MM-01").after(title_date)) {
	// m1.setEofc_em_base(null);
	// m1.setEofc_co_base(null);
	// m1.setEofc_cp(null);
	// m1.setEofc_op(null);
	// m1.setEofc_em_sum(new BigDecimal(0));
	// m1.setEofc_co_sum(new BigDecimal(0));
	// m1.setEofc_start_date(null);
	// m1.setEofc_month_sum(new BigDecimal(0));
	// if (m1.getSicl_class().equals("服务费")) {
	// subM.setEcoc_service_fee(new BigDecimal(
	// 0));
	// } else if (m1.getSicl_class().equals("档案费")) {
	// subM.setEcoc_file_fee(new BigDecimal(0));
	// }
	// } else {
	// m1.setEofc_start_date(DateStringChange
	// .DatetoSting(title_date,
	// "yyyy-MM-01"));
	// if (m1.getSicl_class().equals("服务费")) {
	// subM.setEcoc_service_fee(m
	// .getEcoc_service_fee());
	// } else if (m1.getSicl_class().equals("档案费")) {
	// subM.setEcoc_file_fee(m
	// .getEcoc_file_fee());
	// }
	// }
	// }
	//
	// subM.setTitle_date(title_date);
	//
	// subM.setEcoc_addtype("新增");
	//
	// // 计算、处理subM
	// submHandle(subM, list, title_date);
	//
	// // 添加委托单信息和项目详情
	// subList.add(subM);
	// a++;
	//
	// } else {
	// // 委托外地调整
	// alFlag: {
	// for (EmCommissionOutChangeModel alM : alList) {
	// // 判断是否跨算法
	// if (DateStringChange.DatetoSting(
	// title_date, "yyyy-MM-01").equals(
	// alM.getSial_execdate())) {
	//
	// // 费用详情列表
	// list = feelistCopy();
	//
	// for (int j = 0; j < feeList.size(); j++) {
	// EmCommissionOutFeeDetailChangeModel m1 = list
	// .get(j);
	//
	// if (m1.getTempDate() == null
	// || DateStringChange
	// .StringtoDate(
	// DateStringChange
	// .DatetoSting(
	// m1.getTempDate(),
	// "yyyy-MM-01"),
	// "yyyy-MM-01")
	// .after(title_date)) {
	// m1.setEofc_em_base(null);
	// m1.setEofc_co_base(null);
	// m1.setEofc_cp(null);
	// m1.setEofc_op(null);
	// m1.setEofc_em_sum(new BigDecimal(
	// 0));
	// m1.setEofc_co_sum(new BigDecimal(
	// 0));
	// m1.setEofc_start_date(null);
	// m1.setEofc_month_sum(new BigDecimal(
	// 0));
	// if (m1.getSicl_class().equals(
	// "服务费")) {
	// subM.setEcoc_service_fee(new BigDecimal(
	// 0));
	// } else if (m1.getSicl_class()
	// .equals("档案费")) {
	// subM.setEcoc_file_fee(new BigDecimal(
	// 0));
	// }
	// } else {
	// m1.setEofc_start_date(DateStringChange
	// .DatetoSting(
	// title_date,
	// "yyyy-MM-01"));
	// if (m1.getSicl_class().equals(
	// "服务费")) {
	// subM.setEcoc_service_fee(m
	// .getEcoc_service_fee());
	// } else if (m1.getSicl_class()
	// .equals("档案费")) {
	// subM.setEcoc_file_fee(m
	// .getEcoc_file_fee());
	// }
	// }
	// }
	//
	// subM.setTitle_date(title_date);
	//
	// subM.setEcoc_addtype("调整");
	//
	// // 计算、处理subM
	// submHandle(subM, list, title_date);
	//
	// // 添加委托单信息和项目详情
	// subList.add(subM);
	// a++;
	//
	// break alFlag;
	// }
	// }
	// // 没跨算法
	//
	// // 费用详情列表
	// list = feelistCopy();
	//
	// Integer flag1 = 0;
	// for (int j = 0; j < feeList.size(); j++) {
	// EmCommissionOutFeeDetailChangeModel m1 = list
	// .get(j);
	//
	// if (m1.getTempDate() == null
	// || !DateStringChange
	// .DatetoSting(
	// m1.getTempDate(),
	// "yyyy-MM-01")
	// .equals(DateStringChange
	// .DatetoSting(
	// title_date,
	// "yyyy-MM-01"))) {
	// m1.setEofc_em_base(null);
	// m1.setEofc_co_base(null);
	// m1.setEofc_cp(null);
	// m1.setEofc_op(null);
	// m1.setEofc_em_sum(new BigDecimal(0));
	// m1.setEofc_co_sum(new BigDecimal(0));
	// m1.setEofc_start_date(null);
	// m1.setEofc_month_sum(new BigDecimal(0));
	// if (m1.getSicl_class().equals("服务费")) {
	// subM.setEcoc_service_fee(new BigDecimal(
	// 0));
	// } else if (m1.getSicl_class().equals(
	// "档案费")) {
	// subM.setEcoc_file_fee(new BigDecimal(
	// 0));
	// }
	// } else {
	// m1.setEofc_start_date(DateStringChange
	// .DatetoSting(title_date,
	// "yyyy-MM-01"));
	// m1.setIschange(true);
	// if (m1.getSicl_class().equals("服务费")) {
	// subM.setEcoc_service_fee(m
	// .getEcoc_service_fee());
	// } else if (m1.getSicl_class().equals(
	// "档案费")) {
	// subM.setEcoc_file_fee(m
	// .getEcoc_file_fee());
	// }
	// flag1 = 1;
	// }
	// }
	//
	// if (flag1.equals(1)) {
	//
	// // 如果上一张委托单的同一条项目有费用明细，则复制到这张委托单中
	// for (int j = 0; j < list.size(); j++) {
	// EmCommissionOutFeeDetailChangeModel m1 = new
	// EmCommissionOutFeeDetailChangeModel();
	// if (!m1.isIschange()) {
	// // 上一张委托单的同一条项目的起始日
	// String last_start_date = subList
	// .get(a - 1).getFeeList()
	// .get(j)
	// .getEofc_start_date();
	//
	// if (last_start_date != null) {
	// m1 = feedetailCopy(
	// subList.get(a - 1)
	// .getFeeList()
	// .get(j),
	// DateStringChange
	// .DatetoSting(
	// title_date,
	// "yyyy-MM-01"));
	// if (m1.getSicl_class().equals(
	// "服务费")) {
	// subM.setEcoc_service_fee(m
	// .getEcoc_service_fee());
	// } else if (m1.getSicl_class()
	// .equals("档案费")) {
	// subM.setEcoc_file_fee(m
	// .getEcoc_file_fee());
	// }
	// list.set(j, m1);
	// }
	// }
	// }
	//
	// subM.setTitle_date(title_date);
	//
	// subM.setEcoc_addtype("调整");
	//
	// // 计算、处理subM
	// submHandle(subM, list, title_date);
	// // 添加委托单信息和项目详情
	// subList.add(subM);
	// a++;
	// }
	// }
	// }
	//
	// // 月份加1
	// Calendar title_datec = Calendar.getInstance();
	// title_datec.setTime(title_date);
	// title_datec.add(2, 1);
	// title_date = title_datec.getTime();
	// }
	// } catch (Exception e) {
	// e.printStackTrace();
	// }
	// }
	// } catch (Exception e) {
	// e.printStackTrace();
	// }
	// return subList;
	// }
}
