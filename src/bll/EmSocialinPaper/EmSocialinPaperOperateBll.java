package bll.EmSocialinPaper;

import org.zkoss.zul.Grid;

import impl.WorkflowCore.WfOperateImpl;
import service.WorkflowCore.WfBusinessService;
import service.WorkflowCore.WfOperateService;
import Controller.DocumentsInfo.DocumentsInfo_OperationController;
import Model.EmSocialinPaperModel;
import Util.UserInfo;

public class EmSocialinPaperOperateBll {

	// 新增
	public String[] add(Object... objs) {
		Object[] obj = { "1", (EmSocialinPaperModel) objs[0], (Grid) objs[1] };
		WfBusinessService wfbs = new EmSocialinPaperImpl();
		WfOperateService wfs = new WfOperateImpl(wfbs);
		String[] str = wfs.AddTaskToNext(obj, "社保卡办卡",
				((EmSocialinPaperModel) objs[0]).getName()
						+ ((EmSocialinPaperModel) objs[0]).getEspa_type()
						+ "社保卡", 6, UserInfo.getUsername(), "", 0, "");
		return str;
	}

	// 转下一步
	public String[] next(Object... objs) {
		Object[] obj = { "2", (EmSocialinPaperModel) objs[0] };
		WfBusinessService wfbs = new EmSocialinPaperImpl();
		WfOperateService wfs = new WfOperateImpl(wfbs);
		return wfs.PassToNext(obj,
				((EmSocialinPaperModel) objs[0]).getEspa_tapr_id(),
				UserInfo.getUsername(), "", 0, "");

	}

	// 退回
	public String[] back(Object... objs) {
		WfBusinessService wfbs = new EmSocialinPaperImpl();
		WfOperateService wfs = new WfOperateImpl(wfbs);
		return wfs.ReturnToPrev(objs,
				((EmSocialinPaperModel) objs[0]).getEspa_tapr_id(),
				UserInfo.getUsername(), "");
	}

	// 修改
	public String[] mod(EmSocialinPaperModel m, Grid gd) {
		try {
			/*
			 * EmSocialinPaperOperateDal dal = new EmSocialinPaperOperateDal();
			 * int row = 0; row = dal.Mod(m); if (row == 1) {
			 */
			DocumentsInfo_OperationController docOC = new DocumentsInfo_OperationController();
			return docOC.UpsubmitDoc(gd, m.getEspa_id() + "");
			/*
			 * } else { return new String[] { "0", "提交失败!" }; }
			 */
		} catch (Exception e) {
			return new String[] { "0", "提交出错!" };
		}
	}
}
