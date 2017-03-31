package bll.EmBenefit;

import impl.WorkflowCore.WfOperateImpl;
import service.WorkflowCore.WfBusinessService;
import service.WorkflowCore.WfOperateService;
import Model.EmActySuppilerGiftInfoModel;
import Util.UserInfo;

public class EmActy_NewOperateServiceSkipToNImpl implements EmActy_NewOperateService{
	
	/**
	 * @author：陈耀家
	 * @Descript:流程跳到指定步骤
	 */
	@Override
	public String[] edit(EmActySuppilerGiftInfoModel m,String sql) {
		Object[] obj = { "2", m, sql };
		int tostep=m.getTostep();
		WfBusinessService wfbs = new EmActy_GiftImpl();
		WfOperateService wfs = new WfOperateImpl(wfbs);
		String[] str = wfs.SkipToN(obj, m.getGift_tarpid(),tostep,
				UserInfo.getUsername(), "", 0, "");
		return str;
	}

}
