package bll.EmBenefit;

import impl.WorkflowCore.WfOperateImpl;
import service.WorkflowCore.WfBusinessService;
import service.WorkflowCore.WfOperateService;
import Model.EmActySuppilerGiftInfoModel;
import Util.UserInfo;

public class EmActy_NewOperateServiceReturnToNImpl implements
		EmActy_NewOperateService {

	/**
	 * @author：陈耀家
	 * @Descript:流程退回到指定步骤
	 */
	@Override
	public String[] edit(EmActySuppilerGiftInfoModel m, String sql) {
		String[] str = new String[5];
		Object[] obj = { "2", m, sql };
		int tostep =m.getTostep();
		WfBusinessService wfbs = new EmActy_GiftImpl();
		WfOperateService wfs = new WfOperateImpl(wfbs);
		str = wfs.ReturnToN(obj, m.getGift_tarpid(), tostep,
				UserInfo.getUsername());
		return str;
	}

}
