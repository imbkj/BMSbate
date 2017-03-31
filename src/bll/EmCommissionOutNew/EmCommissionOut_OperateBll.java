package bll.EmCommissionOutNew;

import impl.WorkflowCore.WfOperateImpl;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Date;

import service.WorkflowCore.WfOperateService;
import Model.EmCommissionOutChangeModel;
import Util.UserInfo;
import bll.EmCommissionOutNew.EmCommissionOutNewImpl;
import bll.EmCommissionOutNew.EmCommissionOut_OperateBll;

public class EmCommissionOut_OperateBll {
	// 委托外地调整
	public String[] change(EmCommissionOutChangeModel wt_list, int ecou_id,
			BigDecimal sb_base, BigDecimal house_base, BigDecimal ecoc_salary,
			String remark, String soin_id, String wtss_lab,String wt_fee_content) throws Exception {
		String[] message = new String[2];

		try {

			Object[] obj = { "2", ecou_id, sb_base, house_base, ecoc_salary,
					remark, UserInfo.getUsername(), soin_id, wtss_lab,wt_fee_content};
			// 执行工作流
			WfOperateService wfs = new WfOperateImpl(
					new EmCommissionOutNewImpl());
			message = wfs.AddTaskToNext(obj, "委托外地调整",
					wt_list.getEmba_name() + "-" + wt_list.getCoba_shortname()
							+ "-委托外地" + "调整" + "("
							+ wt_list.getEcoc_title_date().substring(0, 7)
							+ ")", 66, UserInfo.getUsername(), "",
					wt_list.getCid(), "");
		} catch (Exception e) {
			message[0] = "0";
			message[1] = "委托外地调整，操作出错。";
		}
		return message;
	}
	
	// 委托外地修改
		public String[] edit(EmCommissionOutChangeModel wt_list, int ecou_id,
				BigDecimal sb_base, BigDecimal house_base, BigDecimal ecoc_salary,
				String remark, String soin_id, String wtss_lab) throws Exception {
			String[] message = new String[2];

			try {
				Object[] obj = { "2", ecou_id, sb_base, house_base, ecoc_salary,
						remark, UserInfo.getUsername(), soin_id, wtss_lab };
				// 执行工作流
				WfOperateService wfs = new WfOperateImpl(
						new EmCommissionOutNewImpl());
				message = wfs.AddTaskToNext(obj, "委托外地调整",
						wt_list.getEmba_name() + "-" + wt_list.getCoba_shortname()
								+ "-委托外地" + "调整" + "("
								+ wt_list.getEcoc_title_date().substring(0, 7)
								+ ")", 66, UserInfo.getUsername(), "",
						wt_list.getCid(), "");
			} catch (Exception e) {
				message[0] = "0";
				message[1] = "委托外地调整，操作出错。";
			}
			return message;
		}

	// 委托外地调整修改
	public String[] changeRe(EmCommissionOutChangeModel wt_list, int ecou_id,
			BigDecimal sb_base, BigDecimal house_base, BigDecimal ecoc_salary,
			String remark, String soin_id, String wtss_la,String compact_f,String compact_l,String wt_name) throws Exception {
		String[] message = new String[2];

		try {
			Object[] obj = { "3", ecou_id, sb_base, house_base, ecoc_salary,
					remark, UserInfo.getUsername(), soin_id, wtss_la,compact_f,compact_l,wt_name };
			// 执行工作流
			WfOperateService wfs = new WfOperateImpl(
					new EmCommissionOutNewImpl());

			message = wfs.PassToNext(obj, wt_list.getEcoc_tapr_id(),
					UserInfo.getUsername(), "", wt_list.getCid(), "");
		} catch (Exception e) {
			message[0] = "0";
			message[1] = "委托外地调整，操作出错。";
		}
		return message;
	}

	// 委托外地新增修改
	public String[] addRe(EmCommissionOutChangeModel wt_list, int ecou_id,
			BigDecimal sb_base, BigDecimal house_base, BigDecimal ecoc_salary,
			String remark, String soin_id, String wtss_la,String wt_name,String compact_f,String compact_l) throws Exception {
		String[] message = new String[2];

		try {
			Object[] obj = { "4", ecou_id, sb_base, house_base, ecoc_salary,
					remark, UserInfo.getUsername(), soin_id, wtss_la,wt_name,compact_f,compact_l };
			// 执行工作流
			WfOperateService wfs = new WfOperateImpl(
					new EmCommissionOutNewImpl());
			
			message = wfs.PassToNext(obj, wt_list.getEcoc_tapr_id(),
					UserInfo.getUsername(), "", wt_list.getCid(), "");
			
		} catch (Exception e) {
			message[0] = "0";
			message[1] = "委托外地新增，操作出错。";
		}
		return message;
	}

	// Date类型转换String
	public String DatetoSting(Date d) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		String Date = sdf.format(d);
		return Date;
	}
}
