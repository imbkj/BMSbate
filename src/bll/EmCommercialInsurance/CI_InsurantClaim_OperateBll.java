package bll.EmCommercialInsurance;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import dal.EmCommercialInsurance.CI_InsurantClaim_ListDal;

import impl.WorkflowCore.WfOperateImpl;
import service.WorkflowCore.WfOperateService;
import Model.CI_Insurant_ListModel;
import Util.UserInfo;

public class CI_InsurantClaim_OperateBll {
	String username = UserInfo.getUsername();

	// 商业保险理赔新增
	public String[] add_claim(String gid, String cid, String em1, String em2,
			String em3, String em4, String em5, String em6, String em7,
			String em8, String em9, String em10, String em11, String em12,
			String remark, String ra1, String ra2, String ra3, String ecin_iname)
			throws Exception {
		String[] message = new String[2];
		try {

			Object[] obj = { "1", gid, cid, em1, em2, em3, em4, em5, em6, em7,
					em8, em9, em10, em11, em12, username, remark, ra1, ra2,
					ra3, ecin_iname };
			// 执行工作流

			WfOperateService wf = new WfOperateImpl(
					new CI_InsurantClaim_OperateImpl());
			message = wf.AddTaskToNext(obj, "商业保险",
					em2 + "--" + em1 + "商保理赔新增", 47, username, "",
					Integer.parseInt(cid), "");

		} catch (Exception e) {
			message[0] = "2";
			message[1] = "商保理赔新增，操作出错。";
		}
		return message;
	}

	// 商业保险理赔修改
	public String[] edit_claim(String gid, String cid, String em1, String em2,
			String em3, String em4, String em5, String em6, String em7,
			String em8, String em9, String em10, String em11, String em12,
			String remark, String ra1, String ra2, String ra3,
			String ecin_iname, String eccl_id) throws Exception {
		String[] message = new String[2];
		try {

			Object[] obj = { "5", gid, cid, em1, em2, em3, em4, em5, em6, em7,
					em8, em9, em10, em11, em12, username, remark, ra1, ra2,
					ra3, ecin_iname, eccl_id };
			// 执行工作流

			WfOperateService wf = new WfOperateImpl(
					new CI_InsurantClaim_OperateImpl());
			message = wf.AddTaskToNext(obj, "商业保险",
					em2 + "--" + em1 + "商保理赔新增", 47, username, "",
					Integer.parseInt(cid), "");
			message[0]="1";
		} catch (Exception e) {
			e.printStackTrace();
			message[0] = "2";
			message[1] = "商保理赔新增，操作出错。";
		}
		return message;
	}

	// 商业保险审核
	public String[] waitaut_claim(int eccl_id, int eccl_tapr_id,
			String bx_date, String bx_name) throws Exception {
		String[] message = new String[2];
		try {
			Object[] obj = { "2", eccl_id, bx_date, bx_name };
			// 执行工作流
			CI_InsurantClaim_ListDal dal = new CI_InsurantClaim_ListDal();
			List<CI_Insurant_ListModel> list = dal.getcid(eccl_id);

			WfOperateService wf = new WfOperateImpl(
					new CI_InsurantClaim_OperateImpl());
			message = wf.PassToNext(obj, eccl_tapr_id, username, "",
					Integer.parseInt(list.get(0).getCid()), "");
		} catch (Exception e) {
			message[0] = "2";
			message[1] = "商保理赔审核，操作出错。";
		}
		return message;
	}

	// 商业保险处理中审核
	public String[] autup_claim(int eccl_id, int eccl_tapr_id, String bx_date,
			String fl_date) throws Exception {
		String[] message = new String[2];
		try {
			Object[] obj = { "3", eccl_id, bx_date, fl_date };
			// 执行工作流
			CI_InsurantClaim_ListDal dal = new CI_InsurantClaim_ListDal();
			List<CI_Insurant_ListModel> list = dal.getcid(eccl_id);

			WfOperateService wf = new WfOperateImpl(
					new CI_InsurantClaim_OperateImpl());
			message = wf.PassToNext(obj, eccl_tapr_id, username, "",
					Integer.parseInt(list.get(0).getCid()), "");
		} catch (Exception e) {
			message[0] = "2";
			message[1] = "商保理赔处理中，操作出错。";
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
