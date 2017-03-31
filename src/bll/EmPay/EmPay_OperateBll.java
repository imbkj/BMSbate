package bll.EmPay;

import java.text.SimpleDateFormat;
import java.util.Date;

import dal.EmCommercialInsurance.CI_Insurant_ListDal;
import dal.EmPay.EmPay_OperateDal;

import impl.WorkflowCore.WfOperateImpl;
import service.WorkflowCore.WfOperateService;
import Util.UserInfo;
import bll.EmPay.EmPay_OperateImpl;

public class EmPay_OperateBll {
	String username = UserInfo.getUsername();

	// 支付新增
	public int add_pay(String gid, String cid, String paynum, String ownmonth,
			String type, String fee, String tid) throws Exception {
		int message = 0;
		try {
			EmPay_OperateDal dal = new EmPay_OperateDal();
			message = dal.add_pay(gid, cid, paynum, ownmonth, type, fee, tid);
			message = 1;

		} catch (Exception e) {
			message = 0;
		}
		return message;
	}

	// 支付财务审核
	public String[] aut_pay(int espa_id, int espa_tapr_id) throws Exception {
		String[] message = new String[2];

		try {
			Object[] obj = { "2", espa_id, username };
			WfOperateService wf = new WfOperateImpl(new EmPay_OperateImpl());
			message = wf.PassToNext(obj, espa_tapr_id, username, "", 0, "");
		} catch (Exception e) {
			message[0] = "2";
			message[1] = "支付通知财务审核，操作出错。";
		}
		return message;
	}

	// 支付财务审核
	public String[] mgaut_pay(int espa_id, int espa_tapr_id) throws Exception {
		String[] message = new String[2];

		try {
			Object[] obj = { "3", espa_id, username };
			WfOperateService wf = new WfOperateImpl(new EmPay_OperateImpl());
			message = wf.PassToNext(obj, espa_tapr_id, username, "", 0, "");
		} catch (Exception e) {
			message[0] = "2";
			message[1] = "支付通知总经理审核，操作出错。";
		}
		return message;
	}

	// 财务支付
	public String[] aut_payok(String em1, String em2,
			String em3, String em4, String em5, String em6, String em7,
			String ownmonth, String paynum,int espa_id, int espa_tapr_id) throws Exception {
		String[] message = new String[2];

		try {
			Object[] obj = { "4", espa_id, em1, em2, em3, em4, em5, em6, em7,ownmonth,paynum,username };
			WfOperateService wf = new WfOperateImpl(new EmPay_OperateImpl());
			message = wf.PassToNext(obj, espa_tapr_id, username, "", 0, "");
		} catch (Exception e) {
			message[0] = "2";
			message[1] = "支付通知支付，操作出错。";
		}
		return message;
	}

	// 支付总额更新
	public String[] up_pay(String paynum, String ownmonth, String type)
			throws Exception {
		String[] message = new String[2];
		try {
			Object[] obj = { "1", paynum, ownmonth, username, type };
			// 执行工作流
			WfOperateService wf = new WfOperateImpl(new EmPay_OperateImpl());
			message = wf.AddTaskToNext(obj, type + "支付", ownmonth + "--"
					+ paynum + type + "支付", 86, username, "", 0, "");
			message[0] = "1";
			message[1] = type + "支付，操作成功。";
		} catch (Exception e) {
			message[0] = "2";
			message[1] = type + "支付，操作出错。";
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
