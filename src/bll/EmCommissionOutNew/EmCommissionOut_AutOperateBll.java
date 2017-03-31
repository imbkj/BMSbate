package bll.EmCommissionOutNew;

import impl.WorkflowCore.WfOperateImpl;

import java.util.List;

import org.zkoss.zul.ListModelList;

import service.WorkflowCore.WfOperateService;
import Controller.systemSetController;
import Model.EmCommissionOutChangeModel;
import Util.UserInfo;
import bll.EmCommissionOutNew.EmCommissionOut_AutOperateImpl;
import dal.EmCommissionOutNew.EmCommissionOut_AddAutDal;

public class EmCommissionOut_AutOperateBll {
	EmCommissionOut_AddAutBll bll = new EmCommissionOut_AddAutBll();
	private List<EmCommissionOutChangeModel> gettaprid = new ListModelList<>();// 获取cid,taprid

	// 委托外地一次确认
	public String[] yc_aut(String ecoc_id, int ecoc_tapr_id) throws Exception {
		String[] message = new String[2];

		try {

			Object[] obj = { "1", ecoc_id };
			// 执行工作流

			gettaprid = bll.gettaprid(String.valueOf(ecoc_id));// 社保费用明细

			WfOperateService wf = new WfOperateImpl(
					new EmCommissionOut_AutOperateImpl());
			message = wf.PassToNext(obj, ecoc_tapr_id, UserInfo.getUsername(),
					"", gettaprid.get(0).getCid(), "");

		} catch (Exception e) {
			message[0] = "2";
			message[1] = "委托单一次确认，操作出错。";
		}
		return message;
	}

	// 委托外地二次确认
	public String[] ec_aut(String ecoc_id, int ecoc_tapr_id) throws Exception {
		String[] message = new String[2];

		try {

			Object[] obj = { "2", ecoc_id };
			// 执行工作流

			gettaprid = bll.gettaprid(String.valueOf(ecoc_id));// 社保费用明细

			WfOperateService wf = new WfOperateImpl(
					new EmCommissionOut_AutOperateImpl());
			message = wf.PassToNext(obj, ecoc_tapr_id, UserInfo.getUsername(),
					"", gettaprid.get(0).getCid(), "");

		} catch (Exception e) {
			message[0] = "2";
			message[1] = "委托单二次确认，操作出错。";
		}
		return message;
	}

	// 委托外地批量二次确认
	public String[] allec_aut(String ecoc_id) throws Exception {
		String[] message = new String[2];

		try {

			Object[] obj = { "2", ecoc_id };
			// 执行工作流

			gettaprid = bll.gettaprid(String.valueOf(ecoc_id));// 社保费用明细
			System.out.println("ec");
			System.out.println(gettaprid.get(0).getEcoc_tapr_id());
			System.out.println(gettaprid.get(0).getCid());
			WfOperateService wf = new WfOperateImpl(
					new EmCommissionOut_AutOperateImpl());
			message = wf.PassToNext(obj, gettaprid.get(0).getEcoc_tapr_id(),
					UserInfo.getUsername(), "", gettaprid.get(0).getCid(), "");

		} catch (Exception e) {
			message[0] = "2";
			message[1] = "委托单二次确认，操作出错。";
		}
		return message;
	}

	// 退回
	public String[] back(String ecoc_id, int ecoc_tapr_id, String remark)
			throws Exception {
		String[] message = new String[2];

		try {
			Object[] obj = { "3", ecoc_id, remark };
			// 执行工作流
			WfOperateService wf = new WfOperateImpl(
					new EmCommissionOut_AutOperateImpl());
			message = wf.ReturnToN(obj, ecoc_tapr_id,1,
					UserInfo.getUsername(), remark);

		} catch (Exception e) {
			message[0] = "2";
			message[1] = "委托单退回，操作出错。";
		}
		return message;
	}
}
