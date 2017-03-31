package Controller.EmCommissionOut;

import impl.MessageImpl;

import java.math.BigDecimal;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.zkoss.bind.annotation.BindingParam;
import org.zkoss.bind.annotation.Command;
import org.zkoss.bind.annotation.NotifyChange;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zul.ListModelList;
import org.zkoss.zul.Messagebox;

import service.MessageService;
import dal.LoginDal;
import dal.EmCommissionOut.EmCommissionOut_OperateDal;
import bll.EmCommissionOut.EmCommissionOutListBll;
import bll.EmCommissionOut.EmCommissionyearchangetitleBll;
import Model.EmCommissionOutChangeModel;
import Model.EmCommissionOutFeeDetailChangeModel;
import Model.EmCommissionOutFeeDetailHistoryModel;
import Model.EmCommissionOutFeeDetailModel;
import Model.EmCommissionOutHistoryModel;
import Model.EmCommissionOutModel;
import Model.EmCommissionOutStandardModel;
import Model.EmCommissionYearChangemModel;
import Model.EmCommissionyearchangetitleModel;
import Model.SysMessageModel;
import Util.DateStringChange;
import Util.UserInfo;
import Util.SocialInsuranceEmCommissionOut;

public class EmCommissionOut_ntOperatesController {

	public String cbcity;
	public String wtjgname;
	public Integer id;
	private List<EmCommissionyearchangetitleModel> emcomm;
	EmCommissionyearchangetitleModel emcommodel = new EmCommissionyearchangetitleModel();
	EmCommissionyearchangetitleBll cbll = new EmCommissionyearchangetitleBll();
	private List<EmCommissionOutModel> emlist = new ListModelList<>();
	private List<EmCommissionYearChangemModel> emntyerlist = new ListModelList<>();
	private EmCommissionYearChangemModel emntmodel = new EmCommissionYearChangemModel();

	private List<EmCommissionOutFeeDetailChangeModel> cfeeList = new ListModelList<>();// 委托外地明细表
	private List<EmCommissionOutFeeDetailModel> feeList = new ListModelList<>();

	private EmCommissionOutModel m = new EmCommissionOutModel();
	private EmCommissionOutChangeModel cm = new EmCommissionOutChangeModel();
	EmCommissionOutListBll bll = new EmCommissionOutListBll();
	SocialInsuranceEmCommissionOut calUtil = new SocialInsuranceEmCommissionOut();
	private List<EmCommissionOutChangeModel> titleList;// 当地标准
	private EmCommissionOutChangeModel titleModel;// 获取标准详细信息
	private List<EmCommissionOutStandardModel> stardList;// 合同标准
	private EmCommissionOutStandardModel stardModel = new EmCommissionOutStandardModel();
	private EmCommissionOutChangeModel old_titleModel;
	private EmCommissionOutHistoryModel hemcommmodel = new EmCommissionOutHistoryModel();
	private List<EmCommissionOutFeeDetailHistoryModel> hemcommodeldt = new ListModelList<>();// 委托外地明细表

	public boolean ylvisble = false;
	public boolean yliaovisble = false;
	public boolean ecyt_gshang = false;
	public boolean ecyt_sye = false;
	public boolean ecyt_gjj = false;
	public boolean ecyt_bcgjj = false;
	public boolean ecyt_syu = false;
	public Date Startdate;
	public String nowdate = DateStringChange.Datestringnow("yyyy-MM");
	public List<String> betweenmonth;
	public int admin = 0;
	public StringBuilder wt_name=new StringBuilder();

	public EmCommissionOut_ntOperatesController() {
		try {
			cbcity = Executions.getCurrent().getArg().get("city").toString();

			wtjgname = Executions.getCurrent().getArg().get("jgname")
					.toString();
			id = Integer.parseInt(Executions.getCurrent().getArg().get("id")
					.toString());

			init();
		} catch (Exception e) {
			e.printStackTrace();
			Messagebox
					.show("页面加载出错!", "ERROR", Messagebox.OK, Messagebox.ERROR);
		}

		testAdmin();
	}

	// 测试权限
	private void testAdmin() {
		String username = UserInfo.getUsername();
		if ("赵敏捷".equals(username) || "张志强".equals(username)) {
			this.admin = 1;
		}
	}

	public void init() {

		setEmntyerlist(cbll.searchembaselisthd("4", id));
		setEmcomm(cbll.getemcommtlistforid(id));
		setEmcommodel(emcomm.get(0));
		if (emcommodel.getEcyt_ylao() == 1) {
			ylvisble = true;
		}
		if (emcommodel.getEcyt_yliao() == 1) {
			yliaovisble = true;
		}
		if (emcommodel.getEcyt_gshang() == 1) {
			ecyt_gshang = true;
		}
		if (emcommodel.getEcyt_sye() == 1) {
			ecyt_sye = true;
		}
		if (emcommodel.getEcyt_gjj() == 1) {
			ecyt_gjj = true;
		}
		if (emcommodel.getEcyt_bcgjj() == 1) {
			ecyt_bcgjj = true;
		}
		if (emcommodel.getEcyt_syu() == 1) {
			ecyt_syu = true;
		}
		Date now = new Date();
		emcommodel.setEcyt_modtime(now);
		emcommodel.setEcyt_modname(UserInfo.getUsername());
	}

	/**
	 * 时间批量修改
	 * 
	 * @param date
	 */
	@Command("dateAll")
	@NotifyChange({ "emcommodel" })
	public void dateAll(@BindingParam("date") Date date,
			@BindingParam("ind") int indexint) {

		if (indexint == 1) {

			if (emcommodel.getEcyt_sye() == 1) {
				emcommodel.setEcyt_syetime(date);
			}
			if (emcommodel.getEcyt_gshang() == 1) {
				emcommodel.setEcyt_gshangtime(date);
			}

			if (emcommodel.getEcyt_yliao() == 1) {
				emcommodel.setEcyt_yliaotime(date);
			}
			if (emcommodel.getEcyt_syu() == 1) {
				emcommodel.setEcyt_syutime(date);
			}

			if (emcommodel.getEcyt_gjj() == 1) {
				emcommodel.setEcyt_gjjtime(date);
			}
			if (emcommodel.getEcyt_bcgjj() == 1) {
				emcommodel.setEcyt_bcgjjtime(date);
			}

		} else if (indexint == 2) {
			if (emcommodel.getEcyt_gshang() == 1) {
				emcommodel.setEcyt_gshangtime(date);
			}

			if (emcommodel.getEcyt_yliao() == 1) {
				emcommodel.setEcyt_yliaotime(date);
			}
			if (emcommodel.getEcyt_syu() == 1) {
				emcommodel.setEcyt_syutime(date);
			}

			if (emcommodel.getEcyt_gjj() == 1) {
				emcommodel.setEcyt_gjjtime(date);
			}
			if (emcommodel.getEcyt_bcgjj() == 1) {
				emcommodel.setEcyt_bcgjjtime(date);
			}
		} else if (indexint == 3) {
			if (emcommodel.getEcyt_yliao() == 1) {
				emcommodel.setEcyt_yliaotime(date);
			}
			if (emcommodel.getEcyt_syu() == 1) {
				emcommodel.setEcyt_syutime(date);
			}

			if (emcommodel.getEcyt_gjj() == 1) {
				emcommodel.setEcyt_gjjtime(date);
			}
			if (emcommodel.getEcyt_bcgjj() == 1) {
				emcommodel.setEcyt_bcgjjtime(date);
			}
		} else if (indexint == 4) {
			if (emcommodel.getEcyt_syu() == 1) {
				emcommodel.setEcyt_syutime(date);
			}

			if (emcommodel.getEcyt_gjj() == 1) {
				emcommodel.setEcyt_gjjtime(date);
			}
			if (emcommodel.getEcyt_bcgjj() == 1) {
				emcommodel.setEcyt_bcgjjtime(date);
			}
		} else if (indexint == 5) {
			if (emcommodel.getEcyt_gjj() == 1) {
				emcommodel.setEcyt_gjjtime(date);
			}
			if (emcommodel.getEcyt_bcgjj() == 1) {
				emcommodel.setEcyt_bcgjjtime(date);
			}
		} else if (indexint == 6) {
			if (emcommodel.getEcyt_bcgjj() == 1) {
				emcommodel.setEcyt_bcgjjtime(date);
			}
		}

	}

	@Command("dateAll1")
	@NotifyChange({ "feeList" })
	public void dateAll1(@BindingParam("date") Date date,
			@BindingParam("index") Integer index,
			@BindingParam("class") String sicl_class) {

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
		if (titleModel.getEcoc_soin_id() != null) {
			// 设置计算信息
			if (calUtil.setCalculator(titleModel.getEcoc_soin_id(), title_date,
					stardModel.getEcos_shebao_feetype(),
					stardModel.getEcos_gjj_feetype())) {

				// 计算
				cfeeList = calUtil.getEmCommissionOutItemFee(cfeeList);

				// 总计
				calSum();

				setOld_titleModel(titleModel);
			} else {
				Messagebox.show(titleModel.getEcoc_soin_id() + "在"
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
	public void UpdateToChange(EmCommissionOutModel m,
			List<EmCommissionYearChangemModel> emntyerlist) {

		cm.setEmba_name(m.getEmba_name());
		cm.setGid(m.getGid());
		cm.setCid(m.getCid());
		cm.setEcoc_ecou_id(m.getEcou_id());
		cm.setEcoc_soin_id(m.getEcou_soin_id());
		cm.setEcoc_ecos_id(m.getEcou_ecos_id());
		cm.setEcoc_addtype("年调"); // 年调

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

			for (EmCommissionYearChangemModel eym : emntyerlist) {
				if (bll.getEmCommOutInfoByEcouid(m1.getEofd_ecou_id(), "")
						.getGid().equals(eym.getGid())) {
					// 将年调时间和基数对应赋给明FeeDetail明细中
					if (m1.getEofd_name().equals("养老保险")
							& eym.getEcyc_yl_date() != null) {
						m1.setEofd_em_base(eym.getEcyc_sb_base());
						m1.setEofd_co_base(eym.getEcyc_sb_base());
						m1.setEofd_start_date(DateStringChange.DatetoSting(
								eym.getEcyc_yl_date(), "yyyy-MM-01"));
						m1.setEofd_em_fstart_date(DateStringChange.DatetoSting(
								eym.getEcyc_yl_date(), "yyyy-MM-01"));
						m1.setEofd_co_fstart_date(DateStringChange.DatetoSting(
								eym.getEcyc_yl_date(), "yyyy-MM-01"));
						m1.setTempDate(eym.getEcyc_yl_date());

					}
					;
					if (m1.getEofd_name().equals("医疗保险")
							& eym.getEcyc_jl_date() != null) {
						m1.setEofd_em_base(eym.getEcyc_sb_base());
						m1.setEofd_co_base(eym.getEcyc_sb_base());
						m1.setEofd_start_date(DateStringChange.DatetoSting(
								eym.getEcyc_jl_date(), "yyyy-MM-01"));
						m1.setEofd_em_fstart_date(DateStringChange.DatetoSting(
								eym.getEcyc_jl_date(), "yyyy-MM-01"));
						m1.setEofd_co_fstart_date(DateStringChange.DatetoSting(
								eym.getEcyc_jl_date(), "yyyy-MM-01"));
						m1.setTempDate(eym.getEcyc_jl_date());
					}
					if (m1.getEofd_name().equals("生育保险")
							& eym.getEcyc_syu_date() != null) {
						m1.setEofd_em_base(eym.getEcyc_sb_base());
						m1.setEofd_co_base(eym.getEcyc_sb_base());
						m1.setEofd_start_date(DateStringChange.DatetoSting(
								eym.getEcyc_syu_date(), "yyyy-MM-01"));
						m1.setEofd_em_fstart_date(DateStringChange.DatetoSting(
								eym.getEcyc_syu_date(), "yyyy-MM-01"));
						m1.setEofd_co_fstart_date(DateStringChange.DatetoSting(
								eym.getEcyc_syu_date(), "yyyy-MM-01"));
						m1.setTempDate(eym.getEcyc_syu_date());
					}
					if (m1.getEofd_name().equals("工伤保险")
							& eym.getEcyc_gs_date() != null) {
						m1.setEofd_em_base(eym.getEcyc_sb_base());
						m1.setEofd_co_base(eym.getEcyc_sb_base());
						m1.setEofd_start_date(DateStringChange.DatetoSting(
								eym.getEcyc_gs_date(), "yyyy-MM-01"));
						m1.setEofd_em_fstart_date(DateStringChange.DatetoSting(
								eym.getEcyc_gs_date(), "yyyy-MM-01"));
						m1.setEofd_co_fstart_date(DateStringChange.DatetoSting(
								eym.getEcyc_gs_date(), "yyyy-MM-01"));
						m1.setTempDate(eym.getEcyc_gs_date());
					}
					if (m1.getEofd_name().equals("失业保险")
							& eym.getEcyc_sye_date() != null) {
						m1.setEofd_em_base(eym.getEcyc_sb_base());
						m1.setEofd_co_base(eym.getEcyc_sb_base());
						m1.setEofd_start_date(DateStringChange.DatetoSting(
								eym.getEcyc_sye_date(), "yyyy-MM-01"));
						m1.setEofd_em_fstart_date(DateStringChange.DatetoSting(
								eym.getEcyc_sye_date(), "yyyy-MM-01"));
						m1.setEofd_co_fstart_date(DateStringChange.DatetoSting(
								eym.getEcyc_sye_date(), "yyyy-MM-01"));
						m1.setTempDate(eym.getEcyc_sye_date());
					}
					if (m1.getEofd_name().equals("住房公积金")
							& eym.getEcyc_house_base().compareTo(
									BigDecimal.ZERO) > 0) {
						m1.setEofd_em_base(eym.getEcyc_house_base());
						m1.setEofd_co_base(eym.getEcyc_house_base());
						m1.setEofd_start_date(DateStringChange.DatetoSting(
								eym.getEcyc_house_date(), "yyyy-MM-01"));
						m1.setEofd_em_fstart_date(DateStringChange.DatetoSting(
								eym.getEcyc_house_date(), "yyyy-MM-01"));
						m1.setEofd_co_fstart_date(DateStringChange.DatetoSting(
								eym.getEcyc_house_date(), "yyyy-MM-01"));
						m1.setEofd_cp(eym.getEcyc_house_cp());
						m1.setEofd_op(eym.getEcyc_house_op());
						m1.setTempDate(eym.getEcyc_house_date());
					}
					if (m1.getEofd_name().equals("补充公积金")
							& eym.getEcyc_bc_base().compareTo(BigDecimal.ZERO) > 0) {
						m1.setEofd_em_base(eym.getEcyc_bc_base());
						m1.setEofd_co_base(eym.getEcyc_bc_base());
						m1.setEofd_start_date(DateStringChange.DatetoSting(
								eym.getEcyc_bc_date(), "yyyy-MM-01"));
						m1.setEofd_em_fstart_date(DateStringChange.DatetoSting(
								eym.getEcyc_bc_date(), "yyyy-MM-01"));
						m1.setEofd_co_fstart_date(DateStringChange.DatetoSting(
								eym.getEcyc_bc_date(), "yyyy-MM-01"));
						m1.setEofd_cp(eym.getEcyc_bc_cp());
						m1.setEofd_op(eym.getEcyc_bc_op());
						m1.setTempDate(eym.getEcyc_bc_date());
					}

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
					cm1.setTempDate(m1.getTempDate());
					cm1.setSicl_class(m1.getSicl_class());

					cfeeList.add(cm1);

				}
			}

		}
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

	public BigDecimal isnullforBigDecimal(BigDecimal x) {
		if (x == null) {
			x = BigDecimal.ZERO;
		}
		return x;
	}

	/**
	 * 计算总计
	 * 
	 */
	public void calSum() {
		// 重置总计
		// cm.setEcoc_sb_em_sum(new BigDecimal(0));
		// cm.setEcoc_sb_co_sum(new BigDecimal(0));
		// cm.setEcoc_sb_sum(new BigDecimal(0));
		// cm.setEcoc_gjj_em_sum(new BigDecimal(0));
		// cm.setEcoc_gjj_co_sum(new BigDecimal(0));
		// cm.setEcoc_gjj_sum(new BigDecimal(0));
		// cm.setEcoc_sum(new BigDecimal(0));
		// cm.setEcoc_welfare_sum(new BigDecimal(0));

		cm.setEcoc_sb_em_sum(BigDecimal.ZERO);
		cm.setEcoc_sb_co_sum(BigDecimal.ZERO);
		cm.setEcoc_sb_sum(BigDecimal.ZERO);
		cm.setEcoc_gjj_em_sum(BigDecimal.ZERO);
		cm.setEcoc_gjj_co_sum(BigDecimal.ZERO);
		cm.setEcoc_gjj_sum(BigDecimal.ZERO);
		cm.setEcoc_sum(BigDecimal.ZERO);
		cm.setEcoc_welfare_sum(BigDecimal.ZERO);

		try {
			// 社保、公积金、福利

			for (EmCommissionOutFeeDetailChangeModel mjs1 : cfeeList) {
				// 社保
				if (mjs1.getSicl_class().equals("社会保险")) {
					// 社保企业总计
					cm.setEcoc_sb_co_sum(cm.getEcoc_sb_co_sum().add(
							isnullforBigDecimal(mjs1.getEofc_co_sum())));
					// 社保个人总计
					cm.setEcoc_sb_em_sum(cm.getEcoc_sb_em_sum().add(
							isnullforBigDecimal(mjs1.getEofc_em_sum())));
				}
				// 公积金
				else if (mjs1.getSicl_class().equals("公积金")) {
					// 公积金企业总计
					cm.setEcoc_gjj_co_sum(cm.getEcoc_gjj_co_sum().add(
							isnullforBigDecimal(mjs1.getEofc_co_sum())));
					// 公积金个人总计
					cm.setEcoc_gjj_em_sum(cm.getEcoc_gjj_em_sum().add(
							isnullforBigDecimal(mjs1.getEofc_em_sum())));
				}
				// 福利
				else if (mjs1.getSicl_class().equals("福利项目")) {
					cm.setEcoc_welfare_sum(cm.getEcoc_welfare_sum().add(
							isnullforBigDecimal(mjs1.getEofc_month_sum())));
				}
				// 总计
				cm.setEcoc_sum(cm.getEcoc_sum().add(
						isnullforBigDecimal(mjs1.getEofc_month_sum())));
			}
			// 社保总计
			cm.setEcoc_sb_sum(cm.getEcoc_sb_em_sum().add(
					isnullforBigDecimal(cm.getEcoc_sb_co_sum())));
			// 公积金总计
			cm.setEcoc_gjj_sum(cm.getEcoc_gjj_em_sum().add(
					isnullforBigDecimal(cm.getEcoc_gjj_co_sum())));

		} catch (Exception e) {
			System.out.println("**********");
			System.out.println(cm.getGid());
			System.out.println("**********");
			e.printStackTrace();
			Messagebox
					.show("费用总计出错!", "ERROR", Messagebox.OK, Messagebox.ERROR);
		}
	}

	/**
	 * 提交
	 * 
	 * @param win
	 * @throws SQLException
	 * @throws ParseException
	 */
	@Command("submit")
	public void submit() throws SQLException, ParseException {
		// EmCommissionOut_OperateBll bll = new EmCommissionOut_OperateBll();

		if (Messagebox.show("点击确定后将更新系统在册数据和历史数据，造成不可逆修改。确定需要更新吗？",
				"INFORMATION", Messagebox.YES | Messagebox.NO,
				Messagebox.INFORMATION) == Messagebox.YES) {

			if (Messagebox.show("你真的确定需要更新吗？", "INFORMATION", Messagebox.YES
					| Messagebox.NO, Messagebox.INFORMATION) == Messagebox.YES) {

				System.out.println(DateStringChange
						.Datestringnow("yyyy-MM-dd HH:mm:ss"));

				Integer count = 0;
				emcommodel.setEcyt_modname(UserInfo.getUsername());
				// 更新年调时间
				count = cbll.edittitleadd(emcommodel);
				// 邮件通知注意有提前更新的情况
				if (DateStringChange.comparedate(emcommodel.getEcyt_ylaotime(),
						DateStringChange.Datenow()) == 2
						|| DateStringChange.comparedate(
								emcommodel.getEcyt_syetime(),
								DateStringChange.Datenow()) == 2
						|| DateStringChange.comparedate(
								emcommodel.getEcyt_gshangtime(),
								DateStringChange.Datenow()) == 2
						|| DateStringChange.comparedate(
								emcommodel.getEcyt_yliaotime(),
								DateStringChange.Datenow()) == 2
						|| DateStringChange.comparedate(
								emcommodel.getEcyt_syutime(),
								DateStringChange.Datenow()) == 2
						|| DateStringChange.comparedate(
								emcommodel.getEcyt_gjjtime(),
								DateStringChange.Datenow()) == 2
						|| DateStringChange.comparedate(
								emcommodel.getEcyt_bcgjjtime(),
								DateStringChange.Datenow()) == 2)

				{
					// 发邮件和系统短信
					MessageService msgservice = new MessageImpl("cobase",
							emcommodel.getEcyt_id());
					LoginDal d = new LoginDal();
					SysMessageModel sysm = new SysMessageModel();
					String msgstr = "年调" + emcommodel.getCity() + "编号:"
							+ emcommodel.getEcyt_id() + "提前操作，注意及时更新！";
					sysm.setSyme_title("年调提前操作");
					sysm.setSyme_content(msgstr);// 短信内容
					sysm.setSyme_log_id(d.getUserIDByname(UserInfo
							.getUsername()));// 发件人id
					sysm.setEmail(1);
					sysm.setEmailtitle(msgstr);
					sysm.setSymr_name("赵敏捷");// 收件人姓名
					sysm.setSymr_log_id(d.getUserIDByname("赵敏捷"));// ;
					msgservice.Add(sysm);
					sysm.setSymr_name("张志强");// 收件人姓名
					sysm.setSymr_log_id(d.getUserIDByname("张志强"));// ;
					msgservice.Add(sysm);
				}

				List<EmCommissionOutModel> ntlist = new ListModelList<>();
				// 获取年调影响的在册员工列表
				ntlist = bll.getEmCommOutListnt(" and b1.ecyt_id=" + id);
				// 获取年调影响的历史数据列表
				setEmntyerlist(cbll.searchembaselisthd("4", id));
				EmCommissionOut_OperateDal dal = new EmCommissionOut_OperateDal();
				Boolean nt = false;
				for (EmCommissionYearChangemModel lsm : emntyerlist) {

					nt = dal.Updateemcommissionyearchange(5, lsm.getEcyc_id());
				}

				// 遍历年调影响的在册员工列表
				// for (EmCommissionOutModel ml : ntlist) {
				// // 遍历调基列表将在册列表的基数更改为最新基数
				// for (EmCommissionYearChangemModel lsm : emntyerlist) {
				// if (lsm.getGid().equals(ml.getGid())) {
				// // 实际工资不用管
				// // if (lsm.getEcyc_yl_date()!=null) {
				// // m.setEcou_salary(lsm.getEcyc_sb_base());
				//
				// // }
				// if (lsm.getEcyc_sb_base()
				// .compareTo(BigDecimal.ZERO) == 1
				// && ((lsm.getEcyc_yl_date() != null && DateStringChange
				// .comparedate(lsm.getEcyc_yl_date(),
				// DateStringChange.Datenow()) != 2)
				// || (lsm.getEcyc_sye_date() != null && DateStringChange
				// .comparedate(
				// lsm.getEcyc_sye_date(),
				// DateStringChange
				// .Datenow()) != 2)
				// || (lsm.getEcyc_jl_date() != null && DateStringChange
				// .comparedate(lsm
				// .getEcyc_jl_date(),
				// DateStringChange
				// .Datenow()) != 2)
				// || (lsm.getEcyc_syu_date() != null && DateStringChange
				// .comparedate(
				// lsm.getEcyc_syu_date(),
				// DateStringChange
				// .Datenow()) != 2) || (lsm
				// .getEcyc_gs_date() != null && DateStringChange
				// .comparedate(lsm.getEcyc_gs_date(),
				// DateStringChange.Datenow()) != 2)
				//
				// )) {
				//
				// ml.setEcou_sb_base(lsm.getEcyc_sb_base());
				//
				// }
				// if (lsm.getEcyc_house_base().compareTo(
				// BigDecimal.ZERO) == 1
				// && (lsm.getEcyc_bc_base().compareTo(
				// BigDecimal.ZERO) == 1 || (lsm
				// .getEcyc_house_date() != null && DateStringChange
				// .comparedate(
				// lsm.getEcyc_house_date(),
				// DateStringChange.Datenow()) != 2))) {
				// ml.setEcou_house_base(lsm.getEcyc_house_base());
				// }
				// cm.setEmba_name(ml.getEmba_name());
				// cm.setGid(ml.getGid());
				// cm.setCid(ml.getCid());
				// cm.setEcoc_ecou_id(ml.getEcou_id());
				// cm.setEcoc_soin_id(ml.getEcou_soin_id());
				// cm.setEcoc_ecos_id(ml.getEcou_ecos_id());
				// cm.setEcoc_addtype("年调"); // 年调
				// cm.setEcoc_type("ecocchange");
				// cm.setEcoc_idcard(ml.getEcou_idcard());
				// cm.setEcoc_email(ml.getEcou_email());
				// cm.setEcoc_phone(ml.getEcou_phone());
				// cm.setEcoc_mobile(ml.getEcou_mobile());
				// cm.setEcoc_in_date(ml.getEcou_in_date());
				// cm.setEcoc_com_phone(ml.getEcou_com_phone());
				// cm.setEcoc_com_principal(ml.getEcou_com_principal());
				// cm.setEcoc_com_company(ml.getEcou_com_company());
				// cm.setEcoc_domicile(ml.getEcou_domicile());
				// cm.setEcoc_compact_jud(ml.getEcou_compact_jud());
				// cm.setEcoc_compact_f(ml.getEcou_compact_f());
				// cm.setEcoc_compact_l(ml.getEcou_compact_l());
				// cm.setEcoc_salary(ml.getEcou_salary());
				// cm.setEcoc_sb_base(ml.getEcou_sb_base());
				// cm.setEcoc_house_base(ml.getEcou_house_base());
				// cm.setEcoc_sb_em_sum(ml.getEcou_sb_em_sum());
				// cm.setEcoc_sb_co_sum(ml.getEcou_sb_co_sum());
				// cm.setEcoc_sb_sum(ml.getEcou_gjj_sum());
				// cm.setEcoc_gjj_em_sum(ml.getEcou_gjj_em_sum());
				// cm.setEcoc_gjj_co_sum(ml.getEcou_gjj_co_sum());
				// cm.setEcoc_gjj_sum(ml.getEcou_gjj_sum());
				// cm.setEcoc_welfare_sum(ml.getEcou_welfare_sum());
				// cm.setEcoc_service_fee(ml.getEcou_service_fee());
				// cm.setEcoc_file_fee(ml.getEcou_file_fee());
				// cm.setEcoc_sum(ml.getEcou_sum());
				// cm.setEcoc_stop_date(ml.getEcou_stop_date());
				// cm.setEcoc_stop_cause(ml.getEcou_stop_cause());
				// cm.setEcoc_cancel_cause(ml.getEcou_cancel_cause());
				// cm.setEcoc_laststate(0);
				// cm.setEcoc_state(1);
				// cm.setEcoc_client(ml.getEcou_client());
				// cm.setEcoc_addname(UserInfo.getUsername());
				// cm.setEcoc_remark("");
				// cm.setEcoc_title_date(ml.getEcou_title_date());
				//
				// // 获得在册列表明细（未更新基数）
				// setFeeList(bll
				// .getFeeDetailListanyway(" and eofd_ecou_id="
				// + ml.getEcou_id()));
				// cfeeList.clear();
				// for (EmCommissionOutFeeDetailModel m1 : feeList) {
				// // m1.setTempDate(null);
				// // 将年调时间和基数对应赋给明FeeDetail明细中 2个时间月份比较
				// // 0：相等，1：大于，2：小于
				// if (m1.getEofd_name().equals("养老保险")
				// & lsm.getEcyc_sb_base() != null
				// & lsm.getEcyc_sb_base().compareTo(
				// BigDecimal.ZERO) > 0
				// & DateStringChange.comparedate(
				// lsm.getEcyc_yl_date(),
				// DateStringChange.Datenow()) != 2) {
				// m1.setEofd_em_base(lsm.getEcyc_sb_base());
				// m1.setEofd_co_base(lsm.getEcyc_sb_base());
				// m1.setEofd_start_date(DateStringChange
				// .DatetoSting(lsm.getEcyc_yl_date(),
				// "yyyy-MM-01"));
				// m1.setEofd_em_fstart_date(DateStringChange
				// .DatetoSting(lsm.getEcyc_yl_date(),
				// "yyyy-MM-01"));
				// m1.setEofd_co_fstart_date(DateStringChange
				// .DatetoSting(lsm.getEcyc_yl_date(),
				// "yyyy-MM-01"));
				// m1.setTempDate(lsm.getEcyc_yl_date());
				// }
				// if (m1.getEofd_name().equals("医疗保险")
				// & lsm.getEcyc_jl_date() != null
				// & lsm.getEcyc_sb_base().compareTo(
				// BigDecimal.ZERO) > 0
				// & DateStringChange.comparedate(
				// lsm.getEcyc_jl_date(),
				// DateStringChange.Datenow()) != 2) {
				// m1.setEofd_em_base(lsm.getEcyc_sb_base());
				// m1.setEofd_co_base(lsm.getEcyc_sb_base());
				// m1.setEofd_start_date(DateStringChange
				// .DatetoSting(lsm.getEcyc_jl_date(),
				// "yyyy-MM-01"));
				// m1.setEofd_em_fstart_date(DateStringChange
				// .DatetoSting(lsm.getEcyc_jl_date(),
				// "yyyy-MM-01"));
				// m1.setEofd_co_fstart_date(DateStringChange
				// .DatetoSting(lsm.getEcyc_jl_date(),
				// "yyyy-MM-01"));
				// m1.setTempDate(lsm.getEcyc_jl_date());
				// }
				// if (m1.getEofd_name().equals("生育保险")
				// & lsm.getEcyc_syu_date() != null
				// & lsm.getEcyc_sb_base().compareTo(
				// BigDecimal.ZERO) > 0
				// & DateStringChange.comparedate(
				// lsm.getEcyc_syu_date(),
				// DateStringChange.Datenow()) != 2) {
				// m1.setEofd_em_base(lsm.getEcyc_sb_base());
				// m1.setEofd_co_base(lsm.getEcyc_sb_base());
				// m1.setEofd_start_date(DateStringChange
				// .DatetoSting(
				// lsm.getEcyc_syu_date(),
				// "yyyy-MM-01"));
				// m1.setEofd_em_fstart_date(DateStringChange
				// .DatetoSting(
				// lsm.getEcyc_syu_date(),
				// "yyyy-MM-01"));
				// m1.setEofd_co_fstart_date(DateStringChange
				// .DatetoSting(
				// lsm.getEcyc_syu_date(),
				// "yyyy-MM-01"));
				// m1.setTempDate(lsm.getEcyc_syu_date());
				// }
				// if (m1.getEofd_name().equals("工伤保险")
				// & lsm.getEcyc_gs_date() != null
				// & lsm.getEcyc_sb_base().compareTo(
				// BigDecimal.ZERO) > 0
				// & DateStringChange.comparedate(
				// lsm.getEcyc_gs_date(),
				// DateStringChange.Datenow()) != 2) {
				// m1.setEofd_em_base(lsm.getEcyc_sb_base());
				// m1.setEofd_co_base(lsm.getEcyc_sb_base());
				// m1.setEofd_start_date(DateStringChange
				// .DatetoSting(lsm.getEcyc_gs_date(),
				// "yyyy-MM-01"));
				// m1.setEofd_em_fstart_date(DateStringChange
				// .DatetoSting(lsm.getEcyc_gs_date(),
				// "yyyy-MM-01"));
				// m1.setEofd_co_fstart_date(DateStringChange
				// .DatetoSting(lsm.getEcyc_gs_date(),
				// "yyyy-MM-01"));
				// m1.setTempDate(lsm.getEcyc_gs_date());
				// }
				// if (m1.getEofd_name().equals("失业保险")
				// & lsm.getEcyc_sye_date() != null
				// & lsm.getEcyc_sb_base().compareTo(
				// BigDecimal.ZERO) > 0
				// & DateStringChange.comparedate(
				// lsm.getEcyc_sye_date(),
				// DateStringChange.Datenow()) != 2) {
				// m1.setEofd_em_base(lsm.getEcyc_sb_base());
				// m1.setEofd_co_base(lsm.getEcyc_sb_base());
				// m1.setEofd_start_date(DateStringChange
				// .DatetoSting(
				// lsm.getEcyc_sye_date(),
				// "yyyy-MM-01"));
				// m1.setEofd_em_fstart_date(DateStringChange
				// .DatetoSting(
				// lsm.getEcyc_sye_date(),
				// "yyyy-MM-01"));
				// m1.setEofd_co_fstart_date(DateStringChange
				// .DatetoSting(
				// lsm.getEcyc_sye_date(),
				// "yyyy-MM-01"));
				// m1.setTempDate(lsm.getEcyc_sye_date());
				// }
				// if (m1.getEofd_name().equals("住房公积金")
				// & lsm.getEcyc_house_base().compareTo(
				// BigDecimal.ZERO) > 0
				// & lsm.getEcyc_house_date() != null
				// & DateStringChange.comparedate(
				// lsm.getEcyc_house_date(),
				// DateStringChange.Datenow()) != 2) {
				// m1.setEofd_em_base(lsm.getEcyc_house_base());
				// m1.setEofd_co_base(lsm.getEcyc_house_base());
				// m1.setEofd_start_date(DateStringChange
				// .DatetoSting(
				// lsm.getEcyc_house_date(),
				// "yyyy-MM-01"));
				// m1.setEofd_em_fstart_date(DateStringChange
				// .DatetoSting(
				// lsm.getEcyc_house_date(),
				// "yyyy-MM-01"));
				// m1.setEofd_co_fstart_date(DateStringChange
				// .DatetoSting(
				// lsm.getEcyc_house_date(),
				// "yyyy-MM-01"));
				// m1.setEofd_cp(lsm.getEcyc_house_cp());
				// m1.setEofd_op(lsm.getEcyc_house_op());
				// m1.setTempDate(lsm.getEcyc_house_date());
				// }
				// if (m1.getEofd_name().equals("补充公积金")
				// & lsm.getEcyc_bc_base().compareTo(
				// BigDecimal.ZERO) > 0
				// & lsm.getEcyc_bc_date() != null
				// & DateStringChange.comparedate(
				// lsm.getEcyc_bc_date(),
				// DateStringChange.Datenow()) != 2) {
				// m1.setEofd_em_base(lsm.getEcyc_bc_base());
				// m1.setEofd_co_base(lsm.getEcyc_bc_base());
				// m1.setEofd_start_date(DateStringChange
				// .DatetoSting(lsm.getEcyc_bc_date(),
				// "yyyy-MM-01"));
				// m1.setEofd_em_fstart_date(DateStringChange
				// .DatetoSting(lsm.getEcyc_bc_date(),
				// "yyyy-MM-01"));
				// m1.setEofd_co_fstart_date(DateStringChange
				// .DatetoSting(lsm.getEcyc_bc_date(),
				// "yyyy-MM-01"));
				// m1.setEofd_cp(lsm.getEcyc_bc_cp());
				// m1.setEofd_op(lsm.getEcyc_bc_op());
				// m1.setTempDate(lsm.getEcyc_bc_date());
				// }
				// EmCommissionOutFeeDetailChangeModel cm1 = new
				// EmCommissionOutFeeDetailChangeModel();
				// cm1.setEofc_eofd_id(m1.getEofd_id());
				// cm1.setEofc_sicl_id(m1.getEofd_sicl_id());
				// cm1.setEofc_ecop_id(m1.getEofd_ecop_id());
				// cm1.setEofc_name(m1.getEofd_name());
				// cm1.setEofc_content(m1.getEofd_content());
				// cm1.setEofc_em_base(m1.getEofd_em_base());
				// cm1.setEofc_co_base(m1.getEofd_co_base());
				// cm1.setEofc_cp(m1.getEofd_cp());
				// cm1.setEofc_op(m1.getEofd_op());
				// cm1.setEofc_em_sum(m1.getEofd_em_sum());
				// cm1.setEofc_co_sum(m1.getEofd_co_sum());
				// cm1.setEofc_month_sum(m1.getEofd_month_sum());
				// cm1.setEofc_start_date(m1.getEofd_start_date());
				// cm1.setEofc_state(1);
				// cm1.setTempDate(m1.getTempDate());
				// cm1.setSicl_class(m1.getSicl_class());
				// cfeeList.add(cm1);
				//
				// }
				// Startdate = getdatest(lsm);
				// // 给调整主表和明细表赋值并赋予最新的年调基数,时间
				// // UpdateToChange(ml,emntyerlist);
				// // 获取计算需要的社保标准和委托出标准
				// setTitleModel(cm);
				// setStardModel(bll
				// .getStardInfo(cm.getEcoc_ecos_id()));
				// // 计算
				// // List<Date> dateList = OrderByStartDate();
				// Calc(Startdate);
				// calSum();
				//
				// // 处理数据
				// EmCommissionOut_OperateDal dal = new
				// EmCommissionOut_OperateDal();
				// List<EmCommissionOutFeeDetailChangeModel> list = cm
				// .getFeeList();
				// try {
				// id = dal.addEmcommissionOut(cm);
				// if (id > 0) {
				// // 项目费用详情新增
				// for (int i = 0; i < cfeeList.size(); i++) {
				// cfeeList.get(i).setEofc_ecoc_id(id);
				// //
				// list.get(i).setEofc_eofd_id(cfeeList.get(i).getEofc_eofd_id());
				// dal.addFeeDetail(cfeeList.get(i));
				// }
				// // 更新在册表
				// dal.CheckUpdateEmOut(id);
				//
				// }
				//
				// // 获取最早时间
				//
				// // Startdate =getdatest(lsm);
				// // 获取月份差
				// betweenmonth = DateStringChange
				// .getMonthBetween(DateStringChange
				// .DatetoSting(Startdate,
				// "yyyy-MM"), nowdate);
				// for (int i = 0; i <= betweenmonth.size() - 1; i++) {
				// // 计算、更新历史表;
				//
				// // 获取历史表的model和明细model
				// hemcommmodel = bll.getemouthistoryModel(
				// betweenmonth.get(i), cm.getGid(),
				// "");
				// hemcommodeldt = bll
				// .getEmOutEmFeeDetailHistoryModel(hemcommmodel
				// .getEcoh_id());
				//
				// if (lsm.getEcyc_yl_date() != null
				// || lsm.getEcyc_sye_date() != null
				// || lsm.getEcyc_jl_date() != null
				// || lsm.getEcyc_syu_date() != null
				// || lsm.getEcyc_gs_date() != null) {
				//
				// hemcommmodel.setEcoh_sb_base(lsm
				// .getEcyc_sb_base());
				//
				// }
				// if (lsm.getEcyc_house_base().compareTo(
				// BigDecimal.ZERO) == 1
				// || lsm.getEcyc_bc_base().compareTo(
				// BigDecimal.ZERO) == 1) {
				// hemcommmodel.setEcoh_house_base(lsm
				// .getEcyc_house_base());
				// }
				//
				// cm.setEmba_name(hemcommmodel.getEmba_name());
				// cm.setGid(hemcommmodel.getGid());
				// cm.setCid(hemcommmodel.getCid());
				// cm.setEcoc_ecou_id(hemcommmodel
				// .getEcoh_id());
				// cm.setEcoc_soin_id(hemcommmodel
				// .getEcoh_soin_id());
				// cm.setEcoc_ecos_id(hemcommmodel
				// .getEcoh_ecos_id());
				// cm.setEcoc_addtype("年调"); // 年调
				// cm.setEcoc_type("ecocchange");
				// cm.setEcoc_idcard(hemcommmodel
				// .getEcoh_idcard());
				// cm.setEcoc_email(hemcommmodel
				// .getEcoh_email());
				// cm.setEcoc_phone(hemcommmodel
				// .getEcoh_phone());
				// cm.setEcoc_mobile(hemcommmodel
				// .getEcoh_mobile());
				// cm.setEcoc_in_date(hemcommmodel
				// .getEcoh_in_date());
				// cm.setEcoc_com_phone(hemcommmodel
				// .getEcoh_com_phone());
				// cm.setEcoc_com_principal(hemcommmodel
				// .getEcoh_com_principal());
				// cm.setEcoc_com_company(hemcommmodel
				// .getEcoh_com_company());
				// cm.setEcoc_domicile(hemcommmodel
				// .getEcoh_domicile());
				// cm.setEcoc_compact_jud(hemcommmodel
				// .getEcoh_compact_jud());
				// cm.setEcoc_compact_f(hemcommmodel
				// .getEcoh_compact_f());
				// cm.setEcoc_compact_l(hemcommmodel
				// .getEcoh_compact_l());
				// cm.setEcoc_salary(hemcommmodel
				// .getEcoh_salary());
				// cm.setEcoc_sb_base(hemcommmodel
				// .getEcoh_sb_base());
				// cm.setEcoc_house_base(hemcommmodel
				// .getEcoh_house_base());
				// cm.setEcoc_sb_em_sum(hemcommmodel
				// .getEcoh_sb_em_sum());
				// cm.setEcoc_sb_co_sum(hemcommmodel
				// .getEcoh_sb_co_sum());
				// cm.setEcoc_sb_sum(hemcommmodel
				// .getEcoh_gjj_sum());
				// cm.setEcoc_gjj_em_sum(hemcommmodel
				// .getEcoh_gjj_em_sum());
				// cm.setEcoc_gjj_co_sum(hemcommmodel
				// .getEcoh_gjj_co_sum());
				// cm.setEcoc_gjj_sum(hemcommmodel
				// .getEcoh_gjj_sum());
				// cm.setEcoc_welfare_sum(hemcommmodel
				// .getEcoh_welfare_sum());
				// cm.setEcoc_service_fee(hemcommmodel
				// .getEcoh_service_fee());
				// cm.setEcoc_file_fee(hemcommmodel
				// .getEcoh_file_fee());
				// cm.setEcoc_sum(hemcommmodel.getEcoh_sum());
				// cm.setEcoc_stop_date(hemcommmodel
				// .getEcoh_stop_date());
				// cm.setEcoc_stop_cause(hemcommmodel
				// .getEcoh_stop_cause());
				// cm.setEcoc_cancel_cause(hemcommmodel
				// .getEcoh_cancel_cause());
				// cm.setEcoc_laststate(0);
				// cm.setEcoc_state(1);
				// cm.setEcoc_client(hemcommmodel
				// .getEcoh_client());
				// cm.setEcoc_addname(UserInfo.getUsername());
				// cm.setEcoc_remark("");
				// cm.setEcoc_title_date(hemcommmodel
				// .getEcoh_title_date());
				//
				// // 历史明细
				// cfeeList.clear();
				// for (EmCommissionOutFeeDetailHistoryModel hm1 :
				// hemcommodeldt) {
				// hm1.setTempDate(null);
				//
				// // 将年调时间和基数对应赋给明FeeDetail明细中
				// if (hm1.getEofh_name().equals("养老保险")
				// & lsm.getEcyc_yl_date() != null) {
				//
				// hm1.setEofh_em_base(lsm
				// .getEcyc_sb_base());
				// hm1.setEofh_co_base(lsm
				// .getEcyc_sb_base());
				// hm1.setEofh_start_date(DateStringChange
				// .DatetoSting(lsm
				// .getEcyc_yl_date(),
				// "yyyy-MM-01"));
				// hm1.setEofh_em_fstart_date(DateStringChange
				// .DatetoSting(lsm
				// .getEcyc_yl_date(),
				// "yyyy-MM-01"));
				// hm1.setEofh_co_fstart_date(DateStringChange
				// .DatetoSting(lsm
				// .getEcyc_yl_date(),
				// "yyyy-MM-01"));
				// hm1.setTempDate(lsm
				// .getEcyc_yl_date());
				//
				// }
				// if (hm1.getEofh_name().equals("医疗保险")
				// & lsm.getEcyc_jl_date() != null) {
				//
				// hm1.setEofh_em_base(lsm
				// .getEcyc_sb_base());
				// hm1.setEofh_co_base(lsm
				// .getEcyc_sb_base());
				// hm1.setEofh_start_date(DateStringChange
				// .DatetoSting(lsm
				// .getEcyc_jl_date(),
				// "yyyy-MM-01"));
				// hm1.setEofh_em_fstart_date(DateStringChange
				// .DatetoSting(lsm
				// .getEcyc_jl_date(),
				// "yyyy-MM-01"));
				// hm1.setEofh_co_fstart_date(DateStringChange
				// .DatetoSting(lsm
				// .getEcyc_jl_date(),
				// "yyyy-MM-01"));
				// hm1.setTempDate(lsm
				// .getEcyc_jl_date());
				//
				// }
				// if (hm1.getEofh_name().equals("生育保险")
				// & lsm.getEcyc_syu_date() != null) {
				//
				// hm1.setEofh_em_base(lsm
				// .getEcyc_sb_base());
				// hm1.setEofh_co_base(lsm
				// .getEcyc_sb_base());
				// hm1.setEofh_start_date(DateStringChange.DatetoSting(
				// lsm.getEcyc_syu_date(),
				// "yyyy-MM-01"));
				// hm1.setEofh_em_fstart_date(DateStringChange.DatetoSting(
				// lsm.getEcyc_syu_date(),
				// "yyyy-MM-01"));
				// hm1.setEofh_co_fstart_date(DateStringChange.DatetoSting(
				// lsm.getEcyc_syu_date(),
				// "yyyy-MM-01"));
				// hm1.setTempDate(lsm
				// .getEcyc_syu_date());
				//
				// }
				// if (hm1.getEofh_name().equals("工伤保险")
				// & lsm.getEcyc_gs_date() != null) {
				//
				// hm1.setEofh_em_base(lsm
				// .getEcyc_sb_base());
				// hm1.setEofh_co_base(lsm
				// .getEcyc_sb_base());
				// hm1.setEofh_start_date(DateStringChange
				// .DatetoSting(lsm
				// .getEcyc_gs_date(),
				// "yyyy-MM-01"));
				// hm1.setEofh_em_fstart_date(DateStringChange
				// .DatetoSting(lsm
				// .getEcyc_gs_date(),
				// "yyyy-MM-01"));
				// hm1.setEofh_co_fstart_date(DateStringChange
				// .DatetoSting(lsm
				// .getEcyc_gs_date(),
				// "yyyy-MM-01"));
				// hm1.setTempDate(lsm
				// .getEcyc_gs_date());
				//
				// }
				// if (hm1.getEofh_name().equals("失业保险")
				// & lsm.getEcyc_sye_date() != null) {
				//
				// hm1.setEofh_em_base(lsm
				// .getEcyc_sb_base());
				// hm1.setEofh_co_base(lsm
				// .getEcyc_sb_base());
				// hm1.setEofh_start_date(DateStringChange.DatetoSting(
				// lsm.getEcyc_sye_date(),
				// "yyyy-MM-01"));
				// hm1.setEofh_em_fstart_date(DateStringChange.DatetoSting(
				// lsm.getEcyc_sye_date(),
				// "yyyy-MM-01"));
				// hm1.setEofh_co_fstart_date(DateStringChange.DatetoSting(
				// lsm.getEcyc_sye_date(),
				// "yyyy-MM-01"));
				// hm1.setTempDate(lsm
				// .getEcyc_sye_date());
				//
				// }
				// if (hm1.getEofh_name().equals("住房公积金")
				// & lsm.getEcyc_house_base()
				// .compareTo(
				// BigDecimal.ZERO) > 0) {
				//
				// hm1.setEofh_em_base(lsm
				// .getEcyc_house_base());
				// hm1.setEofh_co_base(lsm
				// .getEcyc_house_base());
				// hm1.setEofh_start_date(DateStringChange.DatetoSting(
				// lsm.getEcyc_house_date(),
				// "yyyy-MM-01"));
				// hm1.setEofh_em_fstart_date(DateStringChange.DatetoSting(
				// lsm.getEcyc_house_date(),
				// "yyyy-MM-01"));
				// hm1.setEofh_co_fstart_date(DateStringChange.DatetoSting(
				// lsm.getEcyc_house_date(),
				// "yyyy-MM-01"));
				// hm1.setEofh_cp(lsm
				// .getEcyc_house_cp());
				// hm1.setEofh_op(lsm
				// .getEcyc_house_op());
				// hm1.setTempDate(lsm
				// .getEcyc_house_date());
				//
				// }
				// if (hm1.getEofh_name().equals("补充公积金")
				// & lsm.getEcyc_bc_base()
				// .compareTo(
				// BigDecimal.ZERO) > 0) {
				//
				// hm1.setEofh_em_base(lsm
				// .getEcyc_bc_base());
				// hm1.setEofh_co_base(lsm
				// .getEcyc_bc_base());
				// hm1.setEofh_start_date(DateStringChange
				// .DatetoSting(lsm
				// .getEcyc_bc_date(),
				// "yyyy-MM-01"));
				// hm1.setEofh_em_fstart_date(DateStringChange
				// .DatetoSting(lsm
				// .getEcyc_bc_date(),
				// "yyyy-MM-01"));
				// hm1.setEofh_co_fstart_date(DateStringChange
				// .DatetoSting(lsm
				// .getEcyc_bc_date(),
				// "yyyy-MM-01"));
				// hm1.setEofh_cp(lsm.getEcyc_bc_cp());
				// hm1.setEofh_op(lsm.getEcyc_bc_op());
				// hm1.setTempDate(lsm
				// .getEcyc_bc_date());
				//
				// }
				//
				// EmCommissionOutFeeDetailChangeModel cm1 = new
				// EmCommissionOutFeeDetailChangeModel();
				// cm1.setEofc_eofd_id(hm1.getEofh_id());
				// cm1.setEofc_sicl_id(hm1
				// .getEofh_sicl_id());
				// cm1.setEofc_ecop_id(hm1
				// .getEofh_ecop_id());
				// cm1.setEofc_name(hm1.getEofh_name());
				// cm1.setEofc_content(hm1
				// .getEofh_content());
				// cm1.setEofc_em_base(hm1
				// .getEofh_em_base());
				// cm1.setEofc_co_base(hm1
				// .getEofh_co_base());
				// cm1.setEofc_cp(hm1.getEofh_cp());
				// cm1.setEofc_op(hm1.getEofh_op());
				// cm1.setEofc_em_sum(hm1.getEofh_em_sum());
				// cm1.setEofc_co_sum(hm1.getEofh_co_sum());
				// cm1.setEofc_month_sum(hm1
				// .getEofh_month_sum());
				// cm1.setEofc_start_date(hm1
				// .getEofh_start_date());
				// cm1.setEofc_state(1);
				// cm1.setTempDate(hm1.getTempDate());
				// cm1.setSicl_class(hm1.getSicl_class());
				// cfeeList.add(cm1);
				// }
				//
				// // 更新model的title
				// setTitleModel(cm);
				// setStardModel(bll.getStardInfo(cm
				// .getEcoc_ecos_id()));
				// // 计算
				// List<Date> dateListh = OrderByStartDate();
				//
				// Calc(dateListh.get(0));
				// calSum();
				//
				// // 处理历史数据
				// // EmCommissionOut_OperateDal dalh = new
				// // EmCommissionOut_OperateDal();
				// List<EmCommissionOutFeeDetailChangeModel> listh = cm
				// .getFeeList();
				// try {
				// id = dal.updateEmcommissionOuthistroy(cm);
				// if (id > 0) {
				// // 项目费用详情新增
				// for (int i1 = 0; i1 < cfeeList
				// .size(); i1++) {
				// cfeeList.get(i1)
				// .setEofc_ecoc_id(id);
				// //
				// list.get(i).setEofc_eofd_id(cfeeList.get(i).getEofc_eofd_id());
				// dal.changeFeeDetailhistroy(cfeeList
				// .get(i1));
				// }
				// // 更新在册表
				// // dal.CheckUpdateEmOut(id);
				// }
				// }
				//
				// catch (Exception e) {
				// }
				//
				// }
				//
				// } catch (Exception e) {
				// }
				// }
				// }
				// }
				if (!nt) {
					Messagebox.show("提交失败!", "ERROR", Messagebox.OK,
							Messagebox.ERROR);
				} else {
					Messagebox.show("提交成功", "INFORMATION", Messagebox.OK,
							Messagebox.INFORMATION);
				}

			}

		}

		System.out.println(DateStringChange
				.Datestringnow("yyyy-MM-dd HH:mm:ss"));

	}

	/**
	 * 提交
	 * 
	 * @param win
	 * @throws SQLException
	 * @throws ParseException
	 */
	@Command("submitnew")
	public void submitnew() throws SQLException, ParseException {
		// EmCommissionOut_OperateBll bll = new EmCommissionOut_OperateBll();
		if (Messagebox.show("点击确定后将更新系统在册数据和历史数据，造成不可逆修改。确定需要更新吗？",
				"INFORMATION", Messagebox.YES | Messagebox.NO,
				Messagebox.INFORMATION) == Messagebox.YES) {
			if (Messagebox.show("你真的确定需要更新吗？", "INFORMATION", Messagebox.YES
					| Messagebox.NO, Messagebox.INFORMATION) == Messagebox.YES) {
				System.out.println(DateStringChange
						.Datestringnow("yyyy-MM-dd HH:mm:ss"));
				Integer count = 0;
				emcommodel.setEcyt_modname(UserInfo.getUsername());
				// 更新年调时间

				List<EmCommissionOutModel> ntlist = new ListModelList<>();

				// 获取年调影响的在册员工列表
				ntlist = bll.getEmCommOutListntzd(" and b1.ecyt_id=" + id);
				// 获取年调影响的历史数据列表
				setEmntyerlist(cbll.getembaListhdzd());
				// 遍历年调影响的在册员工列表
				for (EmCommissionOutModel ml : ntlist) {
					// 遍历调基列表将在册列表的基数更改为最新基数
					for (EmCommissionYearChangemModel lsm : emntyerlist) {
						if (lsm.getGid().equals(ml.getGid())
								&& ml.getEcos_cabc_id() == lsm.getCoab_id()) {
							// 实际工资不用管
							// if (lsm.getEcyc_yl_date()!=null) {
							// m.setEcou_salary(lsm.getEcyc_sb_base());

							// }
							if (lsm.getEcyc_sb_base()
									.compareTo(BigDecimal.ZERO) == 1
									&& ((lsm.getEcyc_yl_date() != null && DateStringChange
											.comparedate(lsm.getEcyc_yl_date(),
													DateStringChange.Datenow()) != 2)
											|| (lsm.getEcyc_sye_date() != null && DateStringChange
													.comparedate(
															lsm.getEcyc_sye_date(),
															DateStringChange
																	.Datenow()) != 2)
											|| (lsm.getEcyc_jl_date() != null && DateStringChange
													.comparedate(lsm
															.getEcyc_jl_date(),
															DateStringChange
																	.Datenow()) != 2)
											|| (lsm.getEcyc_syu_date() != null && DateStringChange
													.comparedate(
															lsm.getEcyc_syu_date(),
															DateStringChange
																	.Datenow()) != 2) || (lsm
											.getEcyc_gs_date() != null && DateStringChange
											.comparedate(lsm.getEcyc_gs_date(),
													DateStringChange.Datenow()) != 2)

									)) {

								ml.setEcou_sb_base(lsm.getEcyc_sb_base());

							}
							if (lsm.getEcyc_house_base().compareTo(
									BigDecimal.ZERO) == 1
									&& (lsm.getEcyc_bc_base().compareTo(
											BigDecimal.ZERO) == 1 || (lsm
											.getEcyc_house_date() != null && DateStringChange
											.comparedate(
													lsm.getEcyc_house_date(),
													DateStringChange.Datenow()) != 2))) {
								ml.setEcou_house_base(lsm.getEcyc_house_base());
							}
							cm.setEmba_name(ml.getEmba_name());
							cm.setGid(ml.getGid());
							cm.setCid(ml.getCid());
							cm.setEcoc_ecou_id(ml.getEcou_id());
							cm.setEcoc_soin_id(ml.getEcou_soin_id());
							cm.setEcoc_ecos_id(ml.getEcou_ecos_id());
							cm.setEcoc_addtype("年调"); // 年调
							cm.setEcoc_type("ecocchange");
							cm.setEcoc_idcard(ml.getEcou_idcard());
							cm.setEcoc_email(ml.getEcou_email());
							cm.setEcoc_phone(ml.getEcou_phone());
							cm.setEcoc_mobile(ml.getEcou_mobile());
							cm.setEcoc_in_date(ml.getEcou_in_date());
							cm.setEcoc_com_phone(ml.getEcou_com_phone());
							cm.setEcoc_com_principal(ml.getEcou_com_principal());
							cm.setEcoc_com_company(ml.getEcou_com_company());
							cm.setEcoc_domicile(ml.getEcou_domicile());
							cm.setEcoc_compact_jud(ml.getEcou_compact_jud());
							cm.setEcoc_compact_f(ml.getEcou_compact_f());
							cm.setEcoc_compact_l(ml.getEcou_compact_l());
							cm.setEcoc_salary(ml.getEcou_salary());
							cm.setEcoc_sb_base(ml.getEcou_sb_base());
							cm.setEcoc_house_base(ml.getEcou_house_base());
							cm.setEcoc_sb_em_sum(ml.getEcou_sb_em_sum());
							cm.setEcoc_sb_co_sum(ml.getEcou_sb_co_sum());
							cm.setEcoc_sb_sum(ml.getEcou_gjj_sum());
							cm.setEcoc_gjj_em_sum(ml.getEcou_gjj_em_sum());
							cm.setEcoc_gjj_co_sum(ml.getEcou_gjj_co_sum());
							cm.setEcoc_gjj_sum(ml.getEcou_gjj_sum());
							cm.setEcoc_welfare_sum(ml.getEcou_welfare_sum());
							cm.setEcoc_service_fee(ml.getEcou_service_fee());
							cm.setEcoc_file_fee(ml.getEcou_file_fee());
							cm.setEcoc_sum(ml.getEcou_sum());
							cm.setEcoc_stop_date(ml.getEcou_stop_date());
							cm.setEcoc_stop_cause(ml.getEcou_stop_cause());
							cm.setEcoc_cancel_cause(ml.getEcou_cancel_cause());
							cm.setEcoc_laststate(0);
							cm.setEcoc_state(1);
							cm.setEcoc_client(ml.getEcou_client());
							cm.setEcoc_addname(UserInfo.getUsername());
							cm.setEcoc_remark("");
							cm.setEcoc_title_date(ml.getEcou_title_date());

							// 获得在册列表明细（未更新基数）
							setFeeList(bll
									.getFeeDetailListanyway(" and eofd_ecou_id="
											+ ml.getEcou_id()));
							cfeeList.clear();
							for (EmCommissionOutFeeDetailModel m1 : feeList) {
								// m1.setTempDate(null);
								// 将年调时间和基数对应赋给明FeeDetail明细中 2个时间月份比较
								// 0：相等，1：大于，2：小于
								if (m1.getEofd_name().equals("养老保险")
										& lsm.getEcyc_sb_base() != null
										& lsm.getEcyc_sb_base().compareTo(
												BigDecimal.ZERO) > 0
										& DateStringChange.comparedate(
												lsm.getEcyc_yl_date(),
												DateStringChange.Datenow()) != 2) {
									m1.setEofd_em_base(lsm.getEcyc_sb_base());
									m1.setEofd_co_base(lsm.getEcyc_sb_base());
									m1.setEofd_start_date(DateStringChange
											.DatetoSting(lsm.getEcyc_yl_date(),
													"yyyy-MM-01"));
									m1.setEofd_em_fstart_date(DateStringChange
											.DatetoSting(lsm.getEcyc_yl_date(),
													"yyyy-MM-01"));
									m1.setEofd_co_fstart_date(DateStringChange
											.DatetoSting(lsm.getEcyc_yl_date(),
													"yyyy-MM-01"));
									m1.setTempDate(lsm.getEcyc_yl_date());
								}
								if (m1.getEofd_name().equals("医疗保险")
										& lsm.getEcyc_jl_date() != null
										& lsm.getEcyc_sb_base().compareTo(
												BigDecimal.ZERO) > 0
										& DateStringChange.comparedate(
												lsm.getEcyc_jl_date(),
												DateStringChange.Datenow()) != 2) {
									m1.setEofd_em_base(lsm.getEcyc_sb_base());
									m1.setEofd_co_base(lsm.getEcyc_sb_base());
									m1.setEofd_start_date(DateStringChange
											.DatetoSting(lsm.getEcyc_jl_date(),
													"yyyy-MM-01"));
									m1.setEofd_em_fstart_date(DateStringChange
											.DatetoSting(lsm.getEcyc_jl_date(),
													"yyyy-MM-01"));
									m1.setEofd_co_fstart_date(DateStringChange
											.DatetoSting(lsm.getEcyc_jl_date(),
													"yyyy-MM-01"));
									m1.setTempDate(lsm.getEcyc_jl_date());
								}
								if (m1.getEofd_name().equals("生育保险")
										& lsm.getEcyc_syu_date() != null
										& lsm.getEcyc_sb_base().compareTo(
												BigDecimal.ZERO) > 0
										& DateStringChange.comparedate(
												lsm.getEcyc_syu_date(),
												DateStringChange.Datenow()) != 2) {
									m1.setEofd_em_base(lsm.getEcyc_sb_base());
									m1.setEofd_co_base(lsm.getEcyc_sb_base());
									m1.setEofd_start_date(DateStringChange
											.DatetoSting(
													lsm.getEcyc_syu_date(),
													"yyyy-MM-01"));
									m1.setEofd_em_fstart_date(DateStringChange
											.DatetoSting(
													lsm.getEcyc_syu_date(),
													"yyyy-MM-01"));
									m1.setEofd_co_fstart_date(DateStringChange
											.DatetoSting(
													lsm.getEcyc_syu_date(),
													"yyyy-MM-01"));
									m1.setTempDate(lsm.getEcyc_syu_date());
								}
								if (m1.getEofd_name().equals("工伤保险")
										& lsm.getEcyc_gs_date() != null
										& lsm.getEcyc_sb_base().compareTo(
												BigDecimal.ZERO) > 0
										& DateStringChange.comparedate(
												lsm.getEcyc_gs_date(),
												DateStringChange.Datenow()) != 2) {
									m1.setEofd_em_base(lsm.getEcyc_sb_base());
									m1.setEofd_co_base(lsm.getEcyc_sb_base());
									m1.setEofd_start_date(DateStringChange
											.DatetoSting(lsm.getEcyc_gs_date(),
													"yyyy-MM-01"));
									m1.setEofd_em_fstart_date(DateStringChange
											.DatetoSting(lsm.getEcyc_gs_date(),
													"yyyy-MM-01"));
									m1.setEofd_co_fstart_date(DateStringChange
											.DatetoSting(lsm.getEcyc_gs_date(),
													"yyyy-MM-01"));
									m1.setTempDate(lsm.getEcyc_gs_date());
								}
								if (m1.getEofd_name().equals("失业保险")
										& lsm.getEcyc_sye_date() != null
										& lsm.getEcyc_sb_base().compareTo(
												BigDecimal.ZERO) > 0
										& DateStringChange.comparedate(
												lsm.getEcyc_sye_date(),
												DateStringChange.Datenow()) != 2) {
									m1.setEofd_em_base(lsm.getEcyc_sb_base());
									m1.setEofd_co_base(lsm.getEcyc_sb_base());
									m1.setEofd_start_date(DateStringChange
											.DatetoSting(
													lsm.getEcyc_sye_date(),
													"yyyy-MM-01"));
									m1.setEofd_em_fstart_date(DateStringChange
											.DatetoSting(
													lsm.getEcyc_sye_date(),
													"yyyy-MM-01"));
									m1.setEofd_co_fstart_date(DateStringChange
											.DatetoSting(
													lsm.getEcyc_sye_date(),
													"yyyy-MM-01"));
									m1.setTempDate(lsm.getEcyc_sye_date());
								}
								if (m1.getEofd_name().equals("住房公积金")
										& lsm.getEcyc_house_base().compareTo(
												BigDecimal.ZERO) > 0
										& lsm.getEcyc_house_date() != null
										& DateStringChange.comparedate(
												lsm.getEcyc_house_date(),
												DateStringChange.Datenow()) != 2) {
									m1.setEofd_em_base(lsm.getEcyc_house_base());
									m1.setEofd_co_base(lsm.getEcyc_house_base());
									m1.setEofd_start_date(DateStringChange
											.DatetoSting(
													lsm.getEcyc_house_date(),
													"yyyy-MM-01"));
									m1.setEofd_em_fstart_date(DateStringChange
											.DatetoSting(
													lsm.getEcyc_house_date(),
													"yyyy-MM-01"));
									m1.setEofd_co_fstart_date(DateStringChange
											.DatetoSting(
													lsm.getEcyc_house_date(),
													"yyyy-MM-01"));
									m1.setEofd_cp(lsm.getEcyc_house_cp());
									m1.setEofd_op(lsm.getEcyc_house_op());
									m1.setTempDate(lsm.getEcyc_house_date());
								}
								if (m1.getEofd_name().equals("补充公积金")
										& lsm.getEcyc_bc_base().compareTo(
												BigDecimal.ZERO) > 0
										& lsm.getEcyc_bc_date() != null
										& DateStringChange.comparedate(
												lsm.getEcyc_bc_date(),
												DateStringChange.Datenow()) != 2) {
									m1.setEofd_em_base(lsm.getEcyc_bc_base());
									m1.setEofd_co_base(lsm.getEcyc_bc_base());
									m1.setEofd_start_date(DateStringChange
											.DatetoSting(lsm.getEcyc_bc_date(),
													"yyyy-MM-01"));
									m1.setEofd_em_fstart_date(DateStringChange
											.DatetoSting(lsm.getEcyc_bc_date(),
													"yyyy-MM-01"));
									m1.setEofd_co_fstart_date(DateStringChange
											.DatetoSting(lsm.getEcyc_bc_date(),
													"yyyy-MM-01"));
									m1.setEofd_cp(lsm.getEcyc_bc_cp());
									m1.setEofd_op(lsm.getEcyc_bc_op());
									m1.setTempDate(lsm.getEcyc_bc_date());
								}
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
								cm1.setTempDate(m1.getTempDate());
								cm1.setSicl_class(m1.getSicl_class());
								cfeeList.add(cm1);

							}
							Startdate = getdatest(lsm);
							// 给调整主表和明细表赋值并赋予最新的年调基数,时间
							// UpdateToChange(ml,emntyerlist);
							// 获取计算需要的社保标准和委托出标准
							setTitleModel(cm);
							setStardModel(bll
									.getStardInfo(cm.getEcoc_ecos_id()));
							// 计算
							// List<Date> dateList = OrderByStartDate();
							Calc(Startdate);
							calSum();

							// 处理数据
							EmCommissionOut_OperateDal dal = new EmCommissionOut_OperateDal();
							// 更新年调数据状态
//							 Boolean nt = dal.Updateemcommissionyearchange(5,
//									lsm.getEcyc_id());

							
								wt_name.setLength(0);
 
								for (EmCommissionOutFeeDetailChangeModel m12 : cfeeList) {
									// new
									// EmCommissionOut_OperateDal().addBJFeeDetail(m1);

									wt_name.append(m12.getEofc_sicl_id()
											.toString() + ",");
									wt_name.append(m12.getEofc_name().toString()
											+ ",");
									wt_name.append(m12.getEofc_co_base()
											.toString() + ",");
									wt_name.append(m12.getEofc_em_base()
											.toString() + ",");
									wt_name.append(m12.getEofc_cp() + ",");
									wt_name.append(m12.getEofc_op() + ",");
									wt_name.append(m12.getEofc_co_sum() + ",");
									wt_name.append(m12.getEofc_em_sum() + ",");
									wt_name.append(m12.getEofc_month_sum()
											.toString() + ",");
									wt_name.append(m12.getEofc_start_date()
											+ "|");
								
								}
								
								
								try {
									id = dal.addEmcommissionOut(cm,wt_name.toString());

									if (id > 0) {
										
										
										dal.Updateemcommissionyearchangebc(5,lsm.getEcyc_id());
									  
									}
										

								} catch (Exception e) {
									
									Messagebox.show("提交失败,请联系IT部门!", "ERROR", Messagebox.OK,
											Messagebox.ERROR);
								}
							}
						}
					}
				}
				

			}

		Messagebox.show("提交成功", "INFORMATION", Messagebox.OK,
				Messagebox.INFORMATION);

		System.out.println(DateStringChange
				.Datestringnow("yyyy-MM-dd HH:mm:ss"));

	}

	private Date getdatest(EmCommissionYearChangemModel lsm) {
		Date sdate = new Date();
		sdate = DateStringChange.Datenow();
		// if (sdate != null) {
		// sdate = DateStringChange.comparedatereturndate(sdate,
		// lsm.getEcyc_yl_date());
		// if (sdate != null) {
		// sdate = DateStringChange.comparedatereturndate(sdate,
		// lsm.getEcyc_house_date());
		// if (sdate != null) {
		// sdate = DateStringChange.comparedatereturndate(sdate,
		// lsm.getEcyc_jl_date());
		// if (sdate != null) {
		// sdate = DateStringChange.comparedatereturndate(sdate,
		// lsm.getEcyc_syu_date());
		//
		// if (sdate != null) {
		// sdate = DateStringChange.comparedatereturndate(
		// sdate, lsm.getEcyc_gs_date());
		//
		// if (sdate != null) {
		// sdate = DateStringChange.comparedatereturndate(
		// sdate, lsm.getEcyc_sye_date());
		// if (sdate != null) {
		// sdate = DateStringChange
		// .comparedatereturndate(sdate,
		// lsm.getEcyc_bc_date());
		// }
		// }
		// }
		// }
		//
		// }
		// }
		// }

		return sdate;

	}

	public List<EmCommissionOutModel> getEmlist() {
		return emlist;
	}

	public void setEmlist(List<EmCommissionOutModel> emlist) {
		this.emlist = emlist;
	}

	public EmCommissionYearChangemModel getEmntmodel() {
		return emntmodel;
	}

	public void setEmntmodel(EmCommissionYearChangemModel emntmodel) {
		this.emntmodel = emntmodel;
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

	public List<EmCommissionOutStandardModel> getStardList() {
		return stardList;
	}

	public void setStardList(List<EmCommissionOutStandardModel> stardList) {
		this.stardList = stardList;
	}

	public EmCommissionOutStandardModel getStardModel() {
		return stardModel;
	}

	public void setStardModel(EmCommissionOutStandardModel stardModel) {
		this.stardModel = stardModel;
	}

	public EmCommissionOutChangeModel getOld_titleModel() {
		return old_titleModel;
	}

	public void setOld_titleModel(EmCommissionOutChangeModel old_titleModel) {
		this.old_titleModel = old_titleModel;
	}

	public List<EmCommissionYearChangemModel> getEmntyerlist() {
		return emntyerlist;
	}

	public void setEmntyerlist(List<EmCommissionYearChangemModel> emntyerlist) {
		this.emntyerlist = emntyerlist;
	}

	public List<EmCommissionOutFeeDetailChangeModel> getCfeeList() {
		return cfeeList;
	}

	public void setCfeeList(List<EmCommissionOutFeeDetailChangeModel> cfeeList) {
		this.cfeeList = cfeeList;
	}

	public List<EmCommissionOutFeeDetailModel> getFeeList() {
		return feeList;
	}

	public void setFeeList(List<EmCommissionOutFeeDetailModel> feeList) {
		this.feeList = feeList;
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

	public List<EmCommissionyearchangetitleModel> getEmcomm() {
		return emcomm;
	}

	public void setEmcomm(List<EmCommissionyearchangetitleModel> emcomm) {
		this.emcomm = emcomm;
	}

	public EmCommissionyearchangetitleModel getEmcommodel() {
		return emcommodel;
	}

	public void setEmcommodel(EmCommissionyearchangetitleModel emcommodel) {
		this.emcommodel = emcommodel;
	}

	public String getWtjgname() {
		return wtjgname;
	}

	public void setWtjgname(String wtjgname) {
		this.wtjgname = wtjgname;
	}

	public boolean isYlvisble() {
		return ylvisble;
	}

	public void setYlvisble(boolean ylvisble) {
		this.ylvisble = ylvisble;
	}

	public boolean isYliaovisble() {
		return yliaovisble;
	}

	public void setYliaovisble(boolean yliaovisble) {
		this.yliaovisble = yliaovisble;
	}

	public boolean isEcyt_gshang() {
		return ecyt_gshang;
	}

	public void setEcyt_gshang(boolean ecyt_gshang) {
		this.ecyt_gshang = ecyt_gshang;
	}

	public boolean isEcyt_sye() {
		return ecyt_sye;
	}

	public void setEcyt_sye(boolean ecyt_sye) {
		this.ecyt_sye = ecyt_sye;
	}

	public boolean isEcyt_gjj() {
		return ecyt_gjj;
	}

	public void setEcyt_gjj(boolean ecyt_gjj) {
		this.ecyt_gjj = ecyt_gjj;
	}

	public boolean isEcyt_bcgjj() {
		return ecyt_bcgjj;
	}

	public void setEcyt_bcgjj(boolean ecyt_bcgjj) {
		this.ecyt_bcgjj = ecyt_bcgjj;
	}

	public boolean isEcyt_syu() {
		return ecyt_syu;
	}

	public void setEcyt_syu(boolean ecyt_syu) {
		this.ecyt_syu = ecyt_syu;
	}

	public int getAdmin() {
		return admin;
	}

	public void setAdmin(int admin) {
		this.admin = admin;
	}

}
