package bll.EmBenefit;

import impl.WorkflowCore.WfOperateImpl;
import service.WorkflowCore.WfBusinessService;
import service.WorkflowCore.WfOperateService;
import Model.EmActySuppilerGiftInfoModel;
import Util.UserInfo;

public class EmActy_NewOperateServiceSkipToNextImpl implements EmActy_NewOperateService{
	
	/**
	 * @author：陈耀家
	 * @Descript:流程跳过下一步
	 */
	@Override
	public String[] edit(EmActySuppilerGiftInfoModel m,String sql) {
		Object[] obj = { "2", m, sql };
		WfBusinessService wfbs = new EmActy_GiftImpl();
		WfOperateService wfs = new WfOperateImpl(wfbs);
		String[] str = wfs.SkipToNext(obj, m.getGift_tarpid(),
				UserInfo.getUsername(), "", 0, "");
		return str;
	}

}
