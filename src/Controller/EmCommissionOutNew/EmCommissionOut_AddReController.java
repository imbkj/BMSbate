package Controller.EmCommissionOutNew;

import java.math.BigDecimal;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import org.zkoss.bind.annotation.BindingParam;
import org.zkoss.bind.annotation.Command;
import org.zkoss.bind.annotation.Init;
import org.zkoss.bind.annotation.NotifyChange;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zul.Combobox;
import org.zkoss.zul.Datebox;
import org.zkoss.zul.Doublebox;
import org.zkoss.zul.Grid;
import org.zkoss.zul.ListModelList;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Textbox;
import org.zkoss.zul.Window;

import dal.EmCommissionOut.EmCommissionOut_ListDal;

import bll.EmCommissionOutNew.EmCommissionOut_OperateBll;
import bll.EmCommissionOutNew.EmCommissionOut_AutOperateBll;
import bll.EmCommissionOutNew.EmCommissionOut_AddReBll;

import Model.EmCommissionOutChangeModel;
import Model.EmCommissionOutFeeDetailChangeModel;
import Model.EmCommissionOutModel;
import Model.EmCommissionOutStandardModel;
import Model.SocialInsuranceClassInfoViewModel;
import Util.DateStringChange;
import Util.RedirectUtil;
import Util.SocialInsuranceEmCommissionOut;
import Util.SocialInsuranceCalculator;

public class EmCommissionOut_AddReController {
	private EmCommissionOutChangeModel wt_list;// 显示委托主表明细

	private ListModelList<EmCommissionOutStandardModel> wtout_stand;// 获取委托出服务标准

	private ListModelList<EmCommissionOutStandardModel> wtout_title;// 获取字典库标准

	private ListModelList<EmCommissionOutFeeDetailChangeModel> wtout_feedetail;// 显示委托社保费用明细

	private ListModelList<EmCommissionOutFeeDetailChangeModel> housecp_de;// 显示委托住房企业比例

	private ListModelList<EmCommissionOutFeeDetailChangeModel> bchousecp_de;// 显示委托补充住房企业比例

	private List<EmCommissionOutFeeDetailChangeModel> balist = new ListModelList<>();// 获取标准福利产品信息

	private List<EmCommissionOutStandardModel> filelist;// 档案费列表

	private List<EmCommissionOutChangeModel> eclist;// 获取任务单

	EmCommissionOut_AddReBll bll = new EmCommissionOut_AddReBll();
	private EmCommissionOut_OperateBll ccsaBll = new EmCommissionOut_OperateBll();

	Integer daid = 0;

	private boolean file_state = true;
	// 合同起始时间
	private Date compact_f;
	// 合同结束时间
	private Date compact_l;
	private String compact_l_str = "有固定期限";
	private boolean compact_l_vis;

	SocialInsuranceCalculator tesbll = new SocialInsuranceCalculator();
	private EmCommissionOutModel m = new EmCommissionOutModel();
	private EmCommissionOutChangeModel cm = new EmCommissionOutChangeModel();
	private EmCommissionOutChangeModel titleModel;// 获取标准详细信息
	private EmCommissionOutChangeModel old_titleModel;
	private List<EmCommissionOutFeeDetailChangeModel> cfeeList = new ListModelList<>();

	private List<SocialInsuranceClassInfoViewModel> testlist = new ListModelList<>();

	BigDecimal basetestlist = null;

	SocialInsuranceEmCommissionOut calUtil = new SocialInsuranceEmCommissionOut();

	@SuppressWarnings("deprecation")
	@Init
	public void init() throws SQLException {

		daid = Integer.parseInt(Executions.getCurrent().getArg().get("daid")
				.toString());

		wt_list = bll.getwt_list(daid.toString());// 显示委托主表明细

		wtout_stand = new ListModelList<EmCommissionOutStandardModel>(
				bll.getwtout_stand(daid.toString()));// 获取委托出服务标准

		wtout_title = new ListModelList<EmCommissionOutStandardModel>(
				bll.getwtout_title(wt_list.getEcoc_ecos_id().toString()));// 获取委托出服务标准

		wtout_feedetail = new ListModelList<EmCommissionOutFeeDetailChangeModel>(
				bll.getfeedetail(daid.toString(), 0, "", 0, "", "", "",
						wt_list.getEcoc_bjmonth(), wt_list.getEcoc_bjmonth(),
						wt_list.getEcoc_bjmonth(), "1",
						wt_list.getWtss_shebaopayty(),
						wt_list.getWtss_gjjpayty()));// 显示委托费用明细

		housecp_de = new ListModelList<EmCommissionOutFeeDetailChangeModel>(
				bll.gethousecp(wt_list.getSoin_id().toString()));// 显示委托住房企业比例

		bchousecp_de = new ListModelList<EmCommissionOutFeeDetailChangeModel>(
				bll.getbchousecp(wt_list.getSoin_id().toString()));// 显示委托住房企业比例
		System.out.println("ssdfdsf");
		System.out.println("+" + wt_list.getEcoc_compact_l().toString() + "+");
		if (!wt_list.getEcoc_compact_l().toString().equals("无固定期限")) {
			compact_l_str = "有固定期限";
			compact_l_vis = true;
			compact_l = DateStringChange.StringtoDate(
					wt_list.getEcoc_compact_l(), "yyyy-MM-dd");
		} else {
			compact_l_str = "无固定期限";
			compact_l = null;
			compact_l_vis = false;

		}

		compact_f = DateStringChange.StringtoDate(wt_list.getEcoc_compact_f(),
				"yyyy-MM-dd");

		if (wt_list.getWtss_archives().equals("不保管")
				|| wt_list.getWtss_archives().equals("")) {
			file_state = true;
		} else {
			file_state = false;
		}

	}

	// 社保基数变更
	@Command("sb_baseChange")
	@NotifyChange({ "wtout_feedetail", "wt_list" })
	public void sb_baseChange(@BindingParam("sb_base") Doublebox sb_base,
			@BindingParam("house_base") Doublebox house_base,
			@BindingParam("sb_title") Combobox sb_title)
			throws NumberFormatException, Exception {

		EmCommissionOut_AutOperateBll ccsaBll = new EmCommissionOut_AutOperateBll();
		int rsult = 1;
		BigDecimal sb_cp_sum = new BigDecimal(0);
		BigDecimal sb_op_sum = new BigDecimal(0);
		BigDecimal sb_sum = new BigDecimal(0);
		BigDecimal gjj_cp_sum = new BigDecimal(0);
		BigDecimal gjj_op_sum = new BigDecimal(0);
		BigDecimal gjj_sum = new BigDecimal(0);
		BigDecimal fw_fee = new BigDecimal(0);
		BigDecimal file_fee = new BigDecimal(0);
		BigDecimal fl_fee = new BigDecimal(0);
		int sb_ti = 0;
		try {
			sb_ti = Integer.parseInt(sb_title.getSelectedItem().getValue()
					.toString());
		} catch (Exception e) {
			sb_ti = wt_list.getSoin_id();
		}

		m.setEcou_soin_id(sb_ti);

		wtout_feedetail = new ListModelList<EmCommissionOutFeeDetailChangeModel>(
				bll.getfeedetail(daid.toString(), sb_ti, sb_base.getValue()
						.toString(), 1, house_base.getValue().toString(), "",
						"", wt_list.getEcoc_bjmonth(), wt_list
								.getEcoc_bjmonth(), wt_list.getEcoc_bjmonth(),
						"1", wt_list.getWtss_shebaopayty(), wt_list
								.getWtss_gjjpayty()));// 显示委托费用明细

		for (int i = 0; i < wtout_feedetail.size(); i++) {
			if (wtout_feedetail.get(i).getEofc_name().equals("养老保险")
					|| wtout_feedetail.get(i).getEofc_name().equals("医疗保险")
					|| wtout_feedetail.get(i).getEofc_name().equals("大病医疗")
					|| wtout_feedetail.get(i).getEofc_name().equals("生育保险")
					|| wtout_feedetail.get(i).getEofc_name().equals("工伤保险")
					|| wtout_feedetail.get(i).getEofc_name().equals("失业保险")) {
				sb_cp_sum = sb_cp_sum.add(wtout_feedetail.get(i)
						.getEofc_co_sum());
				sb_op_sum = sb_op_sum.add(wtout_feedetail.get(i)
						.getEofc_em_sum());
				sb_sum = sb_sum.add(sb_cp_sum.add(sb_op_sum));
			}

			if (wtout_feedetail.get(i).getEofc_name().equals("住房公积金")
					|| wtout_feedetail.get(i).getEofc_name().equals("补充公积金")) {
				gjj_cp_sum = gjj_cp_sum.add(wtout_feedetail.get(i)
						.getEofc_co_sum());
				gjj_op_sum = gjj_op_sum.add(wtout_feedetail.get(i)
						.getEofc_em_sum());
				gjj_sum = sb_sum.add(sb_cp_sum.add(sb_op_sum));
			}

			if (wtout_feedetail.get(i).getEofc_name().equals("服务费")) {
				fw_fee = wtout_feedetail.get(i).getEofc_month_sum();
			}

			if (wtout_feedetail.get(i).getEofc_name().equals("档案费")) {
				file_fee = wtout_feedetail.get(i).getEofc_month_sum();
			}

			if (!wtout_feedetail.get(i).getEofc_name().equals("养老保险")
					&& !wtout_feedetail.get(i).getEofc_name().equals("医疗保险")
					&& !wtout_feedetail.get(i).getEofc_name().equals("大病医疗")
					&& !wtout_feedetail.get(i).getEofc_name().equals("生育保险")
					&& !wtout_feedetail.get(i).getEofc_name().equals("工伤保险")
					&& !wtout_feedetail.get(i).getEofc_name().equals("失业保险")
					&& !wtout_feedetail.get(i).getEofc_name().equals("住房公积金")
					&& !wtout_feedetail.get(i).getEofc_name().equals("补充公积金")
					&& !wtout_feedetail.get(i).getEofc_name().equals("服务费")
					&& !wtout_feedetail.get(i).getEofc_name().equals("档案费")) {
				fl_fee = fl_fee.add(wtout_feedetail.get(i).getEofc_month_sum());
			}
		}
		wt_list.setEcoc_sb_co_sum(sb_cp_sum);
		wt_list.setEcoc_sb_em_sum(sb_op_sum);
		wt_list.setEcoc_sb_sum(sb_cp_sum.add(sb_op_sum));
		wt_list.setEcoc_gjj_co_sum(gjj_cp_sum);
		wt_list.setEcoc_gjj_em_sum(gjj_op_sum);
		wt_list.setEcoc_gjj_sum(gjj_cp_sum.add(gjj_op_sum));
		wt_list.setEcoc_sum(sb_cp_sum.add(sb_op_sum.add(gjj_cp_sum
				.add(gjj_op_sum.add(fw_fee.add(file_fee.add(fl_fee)))))));
		wt_list.setEcoc_welfare_sum(fl_fee);
	}

	// 社保基数变更
	@Command("base_per_change")
	@NotifyChange({ "wtout_feedetail", "wt_list" })
	public void base_per_change(@BindingParam("sb_base") Doublebox sb_base,
			@BindingParam("house_base") Doublebox house_base,
			@BindingParam("abase") EmCommissionOutFeeDetailChangeModel abase)
			throws NumberFormatException, Exception {
		BigDecimal cop_sum = new BigDecimal(0);

		balist = new ListModelList<EmCommissionOutFeeDetailChangeModel>(
				bll.getfeedetail(daid.toString(), wt_list.getSoin_id(), abase
						.getEofc_co_base().toString(), 1, abase
						.getEofc_co_base().toString(), "", "", abase
						.getEofc_cp(), abase.getEofc_op(), abase.getEofc_cp(),
						"2", wt_list.getWtss_shebaopayty(), wt_list
								.getWtss_gjjpayty()));// 显示委托费用明细

		for (int i = 0; i < balist.size(); i++) {
			if (balist.get(i).getEofc_name().equals(abase.getEofc_name())) {
				wtout_feedetail.get(i).setEofc_co_base(
						balist.get(i).getEofc_co_base());
				wtout_feedetail.get(i).setEofc_co_sum(
						balist.get(i).getEofc_co_sum());
				wtout_feedetail.get(i).setEofc_cp(balist.get(i).getEofc_cp());
				cop_sum = balist.get(i).getEofc_co_sum();
			}
		}

		balist = new ListModelList<EmCommissionOutFeeDetailChangeModel>(
				bll.getfeedetail(daid.toString(), wt_list.getSoin_id(), abase
						.getEofc_em_base().toString(), 1, abase
						.getEofc_em_base().toString(), "", "", abase
						.getEofc_cp(), abase.getEofc_op(), abase.getEofc_op(),
						"2", wt_list.getWtss_shebaopayty(), wt_list
								.getWtss_gjjpayty()));// 显示委托费用明细

		for (int i = 0; i < balist.size(); i++) {
			if (balist.get(i).getEofc_name().equals(abase.getEofc_name())) {
				wtout_feedetail.get(i).setEofc_em_base(
						balist.get(i).getEofc_em_base());
				wtout_feedetail.get(i).setEofc_em_sum(
						balist.get(i).getEofc_em_sum());
				wtout_feedetail.get(i).setEofc_op(balist.get(i).getEofc_op());
				cop_sum = cop_sum.add(balist.get(i).getEofc_em_sum());

				wtout_feedetail.get(i).setEofc_month_sum(cop_sum);

			}
		}

		BigDecimal sb_cp_sum = new BigDecimal(0);
		BigDecimal sb_op_sum = new BigDecimal(0);
		BigDecimal sb_sum = new BigDecimal(0);
		BigDecimal gjj_cp_sum = new BigDecimal(0);
		BigDecimal gjj_op_sum = new BigDecimal(0);
		BigDecimal gjj_sum = new BigDecimal(0);
		BigDecimal fw_fee = new BigDecimal(0);
		BigDecimal file_fee = new BigDecimal(0);
		BigDecimal fl_fee = new BigDecimal(0);

		for (int i = 0; i < wtout_feedetail.size(); i++) {
			if (wtout_feedetail.get(i).getEofc_name().equals("养老保险")
					|| wtout_feedetail.get(i).getEofc_name().equals("医疗保险")
					|| wtout_feedetail.get(i).getEofc_name().equals("大病医疗")
					|| wtout_feedetail.get(i).getEofc_name().equals("生育保险")
					|| wtout_feedetail.get(i).getEofc_name().equals("工伤保险")
					|| wtout_feedetail.get(i).getEofc_name().equals("失业保险")) {
				sb_cp_sum = sb_cp_sum.add(wtout_feedetail.get(i)
						.getEofc_co_sum());
				sb_op_sum = sb_op_sum.add(wtout_feedetail.get(i)
						.getEofc_em_sum());
				sb_sum = sb_sum.add(sb_cp_sum.add(sb_op_sum));
			}

			if (wtout_feedetail.get(i).getEofc_name().equals("住房公积金")
					|| wtout_feedetail.get(i).getEofc_name().equals("补充公积金")) {
				gjj_cp_sum = gjj_cp_sum.add(wtout_feedetail.get(i)
						.getEofc_co_sum());
				gjj_op_sum = gjj_op_sum.add(wtout_feedetail.get(i)
						.getEofc_em_sum());
				gjj_sum = sb_sum.add(sb_cp_sum.add(sb_op_sum));
			}

			if (wtout_feedetail.get(i).getEofc_name().equals("服务费")) {
				fw_fee = wtout_feedetail.get(i).getEofc_month_sum();
			}

			if (wtout_feedetail.get(i).getEofc_name().equals("档案费")) {
				file_fee = wtout_feedetail.get(i).getEofc_month_sum();
			}

			if (!wtout_feedetail.get(i).getEofc_name().equals("养老保险")
					&& !wtout_feedetail.get(i).getEofc_name().equals("医疗保险")
					&& !wtout_feedetail.get(i).getEofc_name().equals("大病医疗")
					&& !wtout_feedetail.get(i).getEofc_name().equals("生育保险")
					&& !wtout_feedetail.get(i).getEofc_name().equals("工伤保险")
					&& !wtout_feedetail.get(i).getEofc_name().equals("失业保险")
					&& !wtout_feedetail.get(i).getEofc_name().equals("住房公积金")
					&& !wtout_feedetail.get(i).getEofc_name().equals("补充公积金")
					&& !wtout_feedetail.get(i).getEofc_name().equals("服务费")
					&& !wtout_feedetail.get(i).getEofc_name().equals("档案费")) {
				fl_fee = fl_fee.add(wtout_feedetail.get(i).getEofc_month_sum());
			}
		}
		wt_list.setEcoc_sb_co_sum(sb_cp_sum);
		wt_list.setEcoc_sb_em_sum(sb_op_sum);
		wt_list.setEcoc_sb_sum(sb_cp_sum.add(sb_op_sum));
		wt_list.setEcoc_gjj_co_sum(gjj_cp_sum);
		wt_list.setEcoc_gjj_em_sum(gjj_op_sum);
		wt_list.setEcoc_gjj_sum(gjj_cp_sum.add(gjj_op_sum));
		wt_list.setEcoc_sum(sb_cp_sum.add(sb_op_sum.add(gjj_cp_sum
				.add(gjj_op_sum.add(fw_fee.add(file_fee.add(fl_fee)))))));
		wt_list.setEcoc_welfare_sum(fl_fee);
	}

	// 标准变更
	@Command("titleChange")
	@NotifyChange({ "wtout_feedetail", "wt_list" })
	public void titleChange(@BindingParam("sb_base") Doublebox sb_base,
			@BindingParam("house_base") Doublebox house_base,
			@BindingParam("abase") EmCommissionOutFeeDetailChangeModel abase,
			@BindingParam("sb_title") Combobox sb_title)
			throws NumberFormatException, Exception {
		sb_baseChange(sb_base, house_base, sb_title);

		BigDecimal sb_cp_sum = new BigDecimal(0);
		BigDecimal sb_op_sum = new BigDecimal(0);
		BigDecimal sb_sum = new BigDecimal(0);
		BigDecimal gjj_cp_sum = new BigDecimal(0);
		BigDecimal gjj_op_sum = new BigDecimal(0);
		BigDecimal gjj_sum = new BigDecimal(0);
		BigDecimal fw_fee = new BigDecimal(0);
		BigDecimal file_fee = new BigDecimal(0);
		BigDecimal fl_fee = new BigDecimal(0);

		for (int i = 0; i < wtout_feedetail.size(); i++) {
			if (wtout_feedetail.get(i).getEofc_name().equals("养老保险")
					|| wtout_feedetail.get(i).getEofc_name().equals("医疗保险")
					|| wtout_feedetail.get(i).getEofc_name().equals("大病医疗")
					|| wtout_feedetail.get(i).getEofc_name().equals("生育保险")
					|| wtout_feedetail.get(i).getEofc_name().equals("工伤保险")
					|| wtout_feedetail.get(i).getEofc_name().equals("失业保险")) {
				sb_cp_sum = sb_cp_sum.add(wtout_feedetail.get(i)
						.getEofc_co_sum());
				sb_op_sum = sb_op_sum.add(wtout_feedetail.get(i)
						.getEofc_em_sum());
				sb_sum = sb_sum.add(sb_cp_sum.add(sb_op_sum));
			}

			if (wtout_feedetail.get(i).getEofc_name().equals("住房公积金")
					|| wtout_feedetail.get(i).getEofc_name().equals("补充公积金")) {
				gjj_cp_sum = gjj_cp_sum.add(wtout_feedetail.get(i)
						.getEofc_co_sum());
				gjj_op_sum = gjj_op_sum.add(wtout_feedetail.get(i)
						.getEofc_em_sum());
				gjj_sum = sb_sum.add(sb_cp_sum.add(sb_op_sum));
			}

			if (wtout_feedetail.get(i).getEofc_name().equals("服务费")) {
				fw_fee = wtout_feedetail.get(i).getEofc_month_sum();
			}

			if (wtout_feedetail.get(i).getEofc_name().equals("档案费")) {
				file_fee = wtout_feedetail.get(i).getEofc_month_sum();
			}

			if (!wtout_feedetail.get(i).getEofc_name().equals("养老保险")
					&& !wtout_feedetail.get(i).getEofc_name().equals("医疗保险")
					&& !wtout_feedetail.get(i).getEofc_name().equals("大病医疗")
					&& !wtout_feedetail.get(i).getEofc_name().equals("生育保险")
					&& !wtout_feedetail.get(i).getEofc_name().equals("工伤保险")
					&& !wtout_feedetail.get(i).getEofc_name().equals("失业保险")
					&& !wtout_feedetail.get(i).getEofc_name().equals("住房公积金")
					&& !wtout_feedetail.get(i).getEofc_name().equals("补充公积金")
					&& !wtout_feedetail.get(i).getEofc_name().equals("服务费")
					&& !wtout_feedetail.get(i).getEofc_name().equals("档案费")) {
				fl_fee = fl_fee.add(wtout_feedetail.get(i).getEofc_month_sum());
			}
		}
		wt_list.setEcoc_sb_co_sum(sb_cp_sum);
		wt_list.setEcoc_sb_em_sum(sb_op_sum);
		wt_list.setEcoc_sb_sum(sb_cp_sum.add(sb_op_sum));
		wt_list.setEcoc_gjj_co_sum(gjj_cp_sum);
		wt_list.setEcoc_gjj_em_sum(gjj_op_sum);
		wt_list.setEcoc_gjj_sum(gjj_cp_sum.add(gjj_op_sum));
		wt_list.setEcoc_sum(sb_cp_sum.add(sb_op_sum.add(gjj_cp_sum
				.add(gjj_op_sum.add(fw_fee.add(file_fee.add(fl_fee)))))));
		wt_list.setEcoc_welfare_sum(fl_fee);
	}

	// 委托合同变更
	@Command("stardChange")
	@NotifyChange({ "wtout_title", "wt_list" })
	public void stardChange(@BindingParam("sb_base") Doublebox sb_base,
			@BindingParam("house_base") Doublebox house_base,
			@BindingParam("abase") EmCommissionOutFeeDetailChangeModel abase,
			@BindingParam("ecos_id") Combobox ecos_id,
			@BindingParam("sb_title") Combobox sb_title)
			throws NumberFormatException, Exception {

		wtout_title = new ListModelList<EmCommissionOutStandardModel>(
				bll.getwtout_title(ecos_id.getSelectedItem().getValue()
						.toString()));// 获取委托出服务标准

		sb_baseChange(sb_base, house_base, sb_title);

		BigDecimal sb_cp_sum = new BigDecimal(0);
		BigDecimal sb_op_sum = new BigDecimal(0);
		BigDecimal sb_sum = new BigDecimal(0);
		BigDecimal gjj_cp_sum = new BigDecimal(0);
		BigDecimal gjj_op_sum = new BigDecimal(0);
		BigDecimal gjj_sum = new BigDecimal(0);
		BigDecimal fw_fee = new BigDecimal(0);
		BigDecimal file_fee = new BigDecimal(0);
		BigDecimal fl_fee = new BigDecimal(0);

		for (int i = 0; i < wtout_feedetail.size(); i++) {
			if (wtout_feedetail.get(i).getEofc_name().equals("养老保险")
					|| wtout_feedetail.get(i).getEofc_name().equals("医疗保险")
					|| wtout_feedetail.get(i).getEofc_name().equals("大病医疗")
					|| wtout_feedetail.get(i).getEofc_name().equals("生育保险")
					|| wtout_feedetail.get(i).getEofc_name().equals("工伤保险")
					|| wtout_feedetail.get(i).getEofc_name().equals("失业保险")) {
				sb_cp_sum = sb_cp_sum.add(wtout_feedetail.get(i)
						.getEofc_co_sum());
				sb_op_sum = sb_op_sum.add(wtout_feedetail.get(i)
						.getEofc_em_sum());
				sb_sum = sb_sum.add(sb_cp_sum.add(sb_op_sum));
			}

			if (wtout_feedetail.get(i).getEofc_name().equals("住房公积金")
					|| wtout_feedetail.get(i).getEofc_name().equals("补充公积金")) {
				gjj_cp_sum = gjj_cp_sum.add(wtout_feedetail.get(i)
						.getEofc_co_sum());
				gjj_op_sum = gjj_op_sum.add(wtout_feedetail.get(i)
						.getEofc_em_sum());
				gjj_sum = sb_sum.add(sb_cp_sum.add(sb_op_sum));
			}

			if (wtout_feedetail.get(i).getEofc_name().equals("服务费")) {
				fw_fee = wtout_feedetail.get(i).getEofc_month_sum();
			}

			if (wtout_feedetail.get(i).getEofc_name().equals("档案费")) {
				file_fee = wtout_feedetail.get(i).getEofc_month_sum();
			}

			if (!wtout_feedetail.get(i).getEofc_name().equals("养老保险")
					&& !wtout_feedetail.get(i).getEofc_name().equals("医疗保险")
					&& !wtout_feedetail.get(i).getEofc_name().equals("大病医疗")
					&& !wtout_feedetail.get(i).getEofc_name().equals("生育保险")
					&& !wtout_feedetail.get(i).getEofc_name().equals("工伤保险")
					&& !wtout_feedetail.get(i).getEofc_name().equals("失业保险")
					&& !wtout_feedetail.get(i).getEofc_name().equals("住房公积金")
					&& !wtout_feedetail.get(i).getEofc_name().equals("补充公积金")
					&& !wtout_feedetail.get(i).getEofc_name().equals("服务费")
					&& !wtout_feedetail.get(i).getEofc_name().equals("档案费")) {
				fl_fee = fl_fee.add(wtout_feedetail.get(i).getEofc_month_sum());
			}
		}
		wt_list.setEcoc_sb_co_sum(sb_cp_sum);
		wt_list.setEcoc_sb_em_sum(sb_op_sum);
		wt_list.setEcoc_sb_sum(sb_cp_sum.add(sb_op_sum));
		wt_list.setEcoc_gjj_co_sum(gjj_cp_sum);
		wt_list.setEcoc_gjj_em_sum(gjj_op_sum);
		wt_list.setEcoc_gjj_sum(gjj_cp_sum.add(gjj_op_sum));
		wt_list.setEcoc_sum(sb_cp_sum.add(sb_op_sum.add(gjj_cp_sum
				.add(gjj_op_sum.add(fw_fee.add(file_fee.add(fl_fee)))))));
		wt_list.setEcoc_welfare_sum(fl_fee);
	}

	// 档案变更
	@Command("fileChange")
	@NotifyChange({ "wtout_feedetail", "wt_list" })
	public void fileChange(@BindingParam("ch1") Combobox ch1)
			throws NumberFormatException, Exception {
		for (int i = 0; i < wtout_feedetail.size(); i++) {
			if (ch1.getValue().equals("是")) {

				setFilelist(bll.getfilelist(wt_list.getGid(), wtout_stand
						.get(0).getEcos_cabc_id()));
				if (filelist.size() == 0) {
					Messagebox.show("请通知项目部添加外地档案产品！", "ERROR", Messagebox.OK,
							Messagebox.ERROR);
				} else

				{
					if (wtout_feedetail.get(i).getEofc_name().equals("档案费")) {
						wtout_feedetail.get(i).setEofc_month_sum(
								filelist.get(0).getEcos_archvie_fee());
					}
				}
			} else {
				if (wtout_feedetail.get(i).getEofc_name().equals("档案费")) {
					wtout_feedetail.get(i).setEofc_month_sum(new BigDecimal(0));
				}
			}
		}
	}

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

	// 提交
	@Command("submit")
	public void submit(@BindingParam("sb_base") Doublebox sb_base,
			@BindingParam("house_base") Doublebox house_base,
			@BindingParam("ecoc_salary") Doublebox ecoc_salary,
			@BindingParam("sb_title") Combobox sb_title,
			@BindingParam("remark") Textbox remark,
			@BindingParam("g2") Grid g2, @BindingParam("ch1") Combobox ch1,
			@BindingParam("wtss_la") Combobox wtss_la,
			@BindingParam("win") Window win) throws Exception {
		int rsult = 0;

		/*
		 * if (remark.getValue().equals("")) { Messagebox.show("请在备注中录入调整内容！",
		 * "ERROR", Messagebox.OK, Messagebox.ERROR); return; }
		 * 
		 * if (!wt_list.getWtss_archives().equals("不保管") &&
		 * ch1.getValue().equals("")) { Messagebox.show("请选择是否保管档案！", "ERROR",
		 * Messagebox.OK, Messagebox.ERROR); return; }
		 */
		int sb_ti = 0;
		try {
			sb_ti = Integer.parseInt(sb_title.getSelectedItem().getValue()
					.toString());
		} catch (Exception e) {
			sb_ti = wt_list.getSoin_id();
		}

		m.setEcou_soin_id(sb_ti);

		int ecou_count = 0;
		String wt_name = "";
		for (int i = 0; i < wtout_feedetail.size(); i++) {
			Datebox start_date = (Datebox) g2.getCell(i, 8).getChildren()
					.get(0);

			String sf_date = "";
			if (start_date.getValue() != null) {
				sf_date = ccsaBll.DatetoSting(start_date.getValue());
			}

			if (!sf_date.equals("")) {
				wt_name = wt_name
						+ wtout_feedetail.get(i).getEofc_sicl_id().toString()
						+ ","
						+ wtout_feedetail.get(i).getEofc_name().toString()
						+ ","
						+ wtout_feedetail.get(i).getEofc_co_base().toString()
						+ ","
						+ wtout_feedetail.get(i).getEofc_em_base().toString()
						+ "," + wtout_feedetail.get(i).getEofc_cp().toString()
						+ "," + wtout_feedetail.get(i).getEofc_op().toString()
						+ ","
						+ wtout_feedetail.get(i).getEofc_co_sum().toString()
						+ ","
						+ wtout_feedetail.get(i).getEofc_em_sum().toString()
						+ ","
						+ wtout_feedetail.get(i).getEofc_month_sum().toString()
						+ "," + sf_date + "|";
				ecou_count = ecou_count + 1;

				if (wtout_feedetail.get(i).getEofc_name().toString()
						.equals("住房公积金")
						&& !wtout_feedetail.get(i).getEofc_co_base().toString()
								.equals("0.00")) {
					if (wtout_feedetail.get(i).getEofc_cp().toString()
							.equals("0")
							|| wtout_feedetail.get(i).getEofc_op().toString()
									.equals("0")) {
						Messagebox.show("请选择公积金比例！", "ERROR", Messagebox.OK,
								Messagebox.ERROR);
						return;
					}
				}
			}
			// rsult = bll.change_feeadd(daid, wtout_feedetail.get(i),
			// sf_date);// 更新二次确认时间
		}

		String cf_date = "";
		if (compact_f.toString() != null) {
			cf_date = ccsaBll.DatetoSting(compact_f);
		}

		String cl_date = "";
		if ("有固定期限".equals(compact_l_str)) {
			if (compact_l.toString() != "" && compact_l != null) {
				cl_date = ccsaBll.DatetoSting(compact_l);
			}
		}

		if (ecou_count > 8) {
			String[] message = ccsaBll.addRe(wt_list, daid, new BigDecimal(
					sb_base.getValue()), new BigDecimal(house_base.getValue()),
					new BigDecimal(ecoc_salary.getValue()), remark.getValue(),
					m.getEcou_soin_id().toString(), wtss_la.getValue(),
					wt_name, cf_date, cl_date);

			if (!message[0].equals("0")) {
				Messagebox.show("操作成功！", "操作提示",
						new Messagebox.Button[] { Messagebox.Button.OK },
						Messagebox.INFORMATION, null);
				win.detach();
				RedirectUtil util = new RedirectUtil();
				util.refreshEmUrl("/EmCommissionOut/EmCommissionOut_EmSelectList.zul");// url为跳转页面连接
			} else {
				Messagebox.show("系统加载缓慢，请过5秒后提交！", "ERROR", Messagebox.OK,
						Messagebox.ERROR);
			}
		} else {
			Messagebox.show("系统加载缓慢，请过5秒后提交！", "ERROR", Messagebox.OK,
					Messagebox.ERROR);
		}

	}

	// 终止流程
	@Command("over")
	public void over(@BindingParam("win") Window win) throws SQLException {
		EmCommissionOut_ListDal dal = new EmCommissionOut_ListDal();

		eclist = new ListModelList<EmCommissionOutChangeModel>(
				dal.geteclist(wt_list.getEcoc_tapr_id()));

		Map<String, String> map = new HashMap<String, String>();
		map.put("tali_id", String.valueOf(eclist.get(0).getEcoc_ecos_id()));
		map.put("tali_name", String.valueOf(eclist.get(0).getEcoc_addtype()));
		Window window = (Window) Executions.createComponents(
				"Task_StopTask.zul", null, map);
		window.doModal();
		win.detach();
	}

	// 企业日期控制
	@Command("dateAll")
	public void dateAll1(@BindingParam("g2") Grid gridco) throws SQLException,
			ParseException {
		try {
			Datebox cp_date1 = (Datebox) gridco.getCell(0, 8).getChildren()
					.get(0);

			String dateStr = cp_date1.getValue().toString();
			SimpleDateFormat sdf = new SimpleDateFormat(
					"EEE MMM dd HH:mm:ss zzz yyyy", Locale.US);

			// java.util.Date对象
			Date date = (Date) sdf.parse(dateStr);

			String formatStr = new SimpleDateFormat("yyyy-MM-DD").format(date);

			System.out.println(formatStr);

			SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy-MM-DD");
			Date date1 = sdf1.parse(formatStr);

			for (int i = 1; i < gridco.getRows().getChildren().size(); i++) {
				Datebox dateot = (Datebox) gridco.getCell(i, 8).getChildren()
						.get(0);

				dateot.setValue(date1);
			}
		} catch (Exception e) {
			// TODO: handle exception
		}
	}

	public EmCommissionOutChangeModel getWt_list() {
		return wt_list;
	}

	public void setWt_list(EmCommissionOutChangeModel wt_list) {
		this.wt_list = wt_list;
	}

	public ListModelList<EmCommissionOutStandardModel> getWtout_stand() {
		return wtout_stand;
	}

	public void setWtout_stand(
			ListModelList<EmCommissionOutStandardModel> wtout_stand) {
		this.wtout_stand = wtout_stand;
	}

	public ListModelList<EmCommissionOutFeeDetailChangeModel> getWtout_feedetail() {
		return wtout_feedetail;
	}

	public void setWtout_feedetail(
			ListModelList<EmCommissionOutFeeDetailChangeModel> wtout_feedetail) {
		this.wtout_feedetail = wtout_feedetail;
	}

	public ListModelList<EmCommissionOutFeeDetailChangeModel> getHousecp_de() {
		return housecp_de;
	}

	public void setHousecp_de(
			ListModelList<EmCommissionOutFeeDetailChangeModel> housecp_de) {
		this.housecp_de = housecp_de;
	}

	public EmCommissionOutChangeModel getOld_titleModel() {
		return old_titleModel;
	}

	public void setOld_titleModel(EmCommissionOutChangeModel old_titleModel) {
		this.old_titleModel = old_titleModel;
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

	public List<EmCommissionOutFeeDetailChangeModel> getCfeeList() {
		return cfeeList;
	}

	public void setCfeeList(List<EmCommissionOutFeeDetailChangeModel> cfeeList) {
		this.cfeeList = cfeeList;
	}

	public ListModelList<EmCommissionOutFeeDetailChangeModel> getBchousecp_de() {
		return bchousecp_de;
	}

	public void setBchousecp_de(
			ListModelList<EmCommissionOutFeeDetailChangeModel> bchousecp_de) {
		this.bchousecp_de = bchousecp_de;
	}

	public ListModelList<EmCommissionOutStandardModel> getWtout_title() {
		return wtout_title;
	}

	public void setWtout_title(
			ListModelList<EmCommissionOutStandardModel> wtout_title) {
		this.wtout_title = wtout_title;
	}

	public boolean isFile_state() {
		return file_state;
	}

	public void setFile_state(boolean file_state) {
		this.file_state = file_state;
	}

	public List<EmCommissionOutStandardModel> getFilelist() {
		return filelist;
	}

	public void setFilelist(List<EmCommissionOutStandardModel> filelist) {
		this.filelist = filelist;
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

}
