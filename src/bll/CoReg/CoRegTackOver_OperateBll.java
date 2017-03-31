package bll.CoReg;

import impl.WorkflowCore.WfOperateImpl;
import service.WorkflowCore.WfOperateService;
import Model.CoOnlineRegisterInfoModel;
import Util.UserInfo;

public class CoRegTackOver_OperateBll {
	// 公司就业立户接管
	public String[] CoRegTackOver(CoOnlineRegisterInfoModel m) {

		Object[] obj = { "1", m };
		Integer cid = 0;
		if (m.getCid() != null) {
			cid = m.getCid();
		}
		WfOperateService wfs = new WfOperateImpl(new CoReg_OperateBll());
		String[] str = wfs.AddTaskToNext(obj, "公司就业立户及签订计生责任书",
				m.getCoba_shortname() + "公司就业立户及签订计生责任书", 112,
				UserInfo.getUsername(), "", cid, "");
		return str;
	}
}
