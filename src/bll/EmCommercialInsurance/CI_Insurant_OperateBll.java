package bll.EmCommercialInsurance;

import impl.WorkflowCore.WfOperateImpl;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import com.sun.mail.iap.Response;

import dal.EmCommercialInsurance.CI_Insurant_ListDal;

import service.WorkflowCore.WfOperateService;
import Controller.systemWindowController;
import Model.CI_Insurant_ListModel;
import Util.UserInfo;
import bll.EmCommercialInsurance.CI_Insurant_OperateImpl;

public class CI_Insurant_OperateBll {
	String username = UserInfo.getUsername();

	// 商业保险新增
	public String[] add_insurant(String gid, String cid, String ecin_castsort,
			String ecin_buy_count, String fact_date, String embase1,
			String embase2, String embase3, String embase4, String embase5,
			String embase6, String embase7, String getld, String compact_qd)
			throws Exception {
		String[] message = new String[2];
		try {
			Object[] obj = { "1", gid, cid, ecin_castsort, ecin_buy_count,
					fact_date, embase1, embase2, embase3, embase4, embase5,
					embase6, username, embase7, getld, compact_qd };
			// 执行工作流

			WfOperateService wf = new WfOperateImpl(
					new CI_Insurant_OperateImpl());
			message = wf.AddTaskToNext(obj, "商业保险", embase1 + "--"
					+ ecin_castsort + "商业保险新增", 26, username, "",
					Integer.parseInt(cid), "");

		} catch (Exception e) {
			message[0] = "2";
			message[1] = "商业保险新增，操作出错。";
		}

		try {
			// 判断是否有连带人申请任务单
			CI_Insurant_ListDal dal = new CI_Insurant_ListDal(); 
			List<CI_Insurant_ListModel> ld_list = dal.gettaprid(Integer
					.parseInt(gid));

			if (ld_list.size() > 0) {
				Object[] obj2 = { "1", gid, cid, getld, username };
				WfOperateService wf2 = new WfOperateImpl(
						new CI_Insurant_ApplyOperateImpl());
				message = wf2.PassToNext(obj2,
						Integer.parseInt(ld_list.get(0).getEcin_tapr_id()),
						username, "",
						Integer.parseInt(ld_list.get(0).getCid()), "");
			}
		} catch (Exception e) {
			message[0] = "2";
			message[1] = "商业保险新增，操作出错。";
		}

		return message;
	}

	// 商业保险连带人申请
	public String[] apply_insurant(String gid, String cid, int getld,
			String f_date,String castsort) throws Exception {
		String[] message = new String[2];
		try {
			Object[] obj = { "6", gid, cid, getld, username, f_date,castsort };
			// 执行工作流

			CI_Insurant_ListDal dal = new CI_Insurant_ListDal();
			List<CI_Insurant_ListModel> ld_list = dal.getembaselist(gid);

			WfOperateService wf = new WfOperateImpl(
					new CI_Insurant_ApplyOperateImpl());
			message = wf.AddTaskToNext(obj, "商业保险", ld_list.get(0)
					.getEcin_insurant() + "--" + "商业保险连带人新增", 120, username,
					"", Integer.parseInt(cid), "");
		} catch (Exception e) {
			message[0] = "2";
			message[1] = "商业保险新增，操作出错。";
		}

		return message;

	}

	// 商业保险停缴
	public String[] out_insurant(String gid, String cid, String ecin_castsort,
			String fact_date, String ecin_id) throws Exception {
		String[] message = new String[2];
		try {
			Object[] obj = { "4", ecin_id, fact_date, username };
			// 执行工作流

			CI_Insurant_ListDal dal = new CI_Insurant_ListDal();
			List<CI_Insurant_ListModel> list = dal.getname(Integer
					.parseInt(ecin_id));

			WfOperateService wf = new WfOperateImpl(
					new CI_Insurant_OperateImpl());
			message = wf.AddTaskToNext(obj, "商业保险", list.get(0)
					.getEcin_insurant() + "--" + ecin_castsort + "商业保险停缴", 26,
					username, "", Integer.parseInt(cid), "");

		} catch (Exception e) {
			message[0] = "2";
			message[1] = "商业保险停缴，操作出错。";
		}
		return message;
	}

	// 商业保险审核
	public String[] aut_insurant(int ecin_id, int ecin_tapr_id)
			throws Exception {
		String[] message = new String[2];

		try {
			Object[] obj = { "2", ecin_id };
			// 执行工作流
			CI_Insurant_ListDal dal = new CI_Insurant_ListDal();
			List<CI_Insurant_ListModel> list = dal.getecincid(ecin_id);

			WfOperateService wf = new WfOperateImpl(
					new CI_Insurant_OperateImpl());
			message = wf.PassToNext(obj, ecin_tapr_id, username, "",
					Integer.parseInt(list.get(0).getCid()), "");
		} catch (Exception e) {
			message[0] = "2";
			message[1] = "商业保险新增审核，操作出错。";
		}
		return message;
	}

	// 商业保险信息变更审核
	public String[] aut_bcinsurant(int ecin_id, int ecin_tapr_id)
			throws Exception {
		String[] message = new String[2];
		try {
			Object[] obj = { "6", ecin_id };
			// 执行工作流
			CI_Insurant_ListDal dal = new CI_Insurant_ListDal();
			List<CI_Insurant_ListModel> list = dal.getcid(ecin_id);
			//System.out.println(Integer.parseInt(list.get(0).getCid()));
			WfOperateService wf = new WfOperateImpl(
					new CI_InsurantChange_OperateImpl());
			message = wf.PassToNext(obj, ecin_tapr_id, username, "",
					Integer.parseInt(list.get(0).getCid()), "");

		} catch (Exception e) {
			message[0] = "2";
			message[1] = "商业保险信息变更审核，操作出错。";
		}
		return message;
	}

	// 商业保险申报
	public String[] autup_insurant(int ecin_id, int ecin_tapr_id)
			throws Exception {
		String[] message = new String[2];
		try {
			Object[] obj = { "3", ecin_id };
			// 执行工作流
			CI_Insurant_ListDal dal = new CI_Insurant_ListDal();
			List<CI_Insurant_ListModel> list = dal.getecincid(ecin_id);
			WfOperateService wf = new WfOperateImpl(
					new CI_Insurant_OperateImpl());
			message = wf.PassToNext(obj, ecin_tapr_id, username, "",
					Integer.parseInt(list.get(0).getCid()), "");
		} catch (Exception e) {
			message[0] = "2";
			message[1] = "商业保险新增申报，操作出错。";
		}
		return message;
	}

	// 商业保险信息变更
	public String[] change_insurant(String gid, String name, String idcard,
			String sex, String birthday, String cid, String lname)
			throws Exception {
		String[] message = new String[2];
		try {
			Object[] obj = { "5", gid, cid, name, idcard, sex, birthday, lname,
					username };
			// 执行工作流

			WfOperateService wf = new WfOperateImpl(
					new CI_InsurantChange_OperateImpl());
			message = wf.AddTaskToNext(obj, "商业保险", lname + "--商业保险信息变更", 46,
					username, "", Integer.parseInt(cid), "");

		} catch (Exception e) {
			message[0] = "2";
			message[1] = "商业保险信息变更，操作出错。";
		}
		return message;
	}

	// 商业保险变更删除
	public String[] del_insurant(String id, String ecin_tapr_id)
			throws Exception {
		String[] message = new String[2];
		try {
			Object[] obj = { "10", id, username };
			// 执行工作流
			WfOperateService wf = new WfOperateImpl(
					new CI_InsurantChange_OperateImpl());
			message = wf.StopTask(obj, Integer.parseInt(ecin_tapr_id),
					username, "商业保险删除");
			//System.out.println(message[1]);
		} catch (Exception e) {
			message[0] = "2";
			message[1] = "商业保险变更删除，操作出错。";
		}
		return message;
	}

	// 商业保险变更删除(入职)
	public String[] del_insurantByOnboard(String id, String ecin_tapr_id)
			throws Exception {
		String[] message = new String[2];
		try {
			Object[] obj = { "10", id, username };
			// 执行工作流
			WfOperateService wf = new WfOperateImpl(
					new CI_InsurantChange_OperateImpl());
			message = wf.StopTask(obj, Integer.parseInt(ecin_tapr_id), "系统",
					"商业保险删除");
			//System.out.println(message[1]);
		} catch (Exception e) {
			message[0] = "2";
			message[1] = "商业保险变更删除，操作出错。";
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
