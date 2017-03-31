package bll.EmBenefit;

import impl.WorkflowCore.WfOperateImpl;
import service.WorkflowCore.WfBusinessService;
import service.WorkflowCore.WfOperateService;
import Model.EmActySuppilerGiftInfoModel;
import Util.UserInfo;

public class EmActy_NewOperateServicePassToNextImpl implements
		EmActy_NewOperateService {

	/**
	 * @author：陈耀家
	 * @Descript:通过下一步
	 */
	@Override
	public String[] edit(EmActySuppilerGiftInfoModel m, String sql) {
		String[] str = new String[5];
		Object[] obj = { "2", m, sql };
		WfBusinessService wfbs = new EmActy_GiftImpl();
		WfOperateService wfs = new WfOperateImpl(wfbs);
		str = wfs.PassToNext(obj, m.getGift_tarpid(), UserInfo.getUsername(),
				"", 0, "");
		return str;
	}

}
