package bll.EmCensus.EmDh;

import dal.EmCensus.EmDh.EmDh_OperateDal;
import impl.WorkflowCore.WfOperateImpl;
import service.WorkflowCore.WfBusinessService;
import service.WorkflowCore.WfOperateService;
import Model.EmDhModel;
import Util.DateUtil;
import Util.UserInfo;

public class EmDh_LxOpeateInfoAuditImpl implements EmDh_LxOpeate{
	/**
	 * 
	 * @author chenyaojia
	 *@Descript:信息预审
	 */
	@Override
	public String[] edit(EmDhModel m) {
		EmDh_OperateDal dal = new EmDh_OperateDal();
		String sql = ",emdh_time4='" + DateUtil.timechange(m.getEmdh_time4())
				+ "',emdh_state=3";
		String[] str = new String[5];
		if (m.getEmdh_taprid() != null) {
			Object[] object = { "信息预审", m, sql };
			WfBusinessService service = new EmDh_LxImpl();
			WfOperateService wfservice = new WfOperateImpl(service);
			str = wfservice.PassToNext(object, m.getEmdh_taprid(),
					UserInfo.getUsername(), "", 0, "");
		} else {
			int k = dal.UpdateEmdhInfo(m, sql);
			if (k > 0) {
				str[0] = "1";
			} else {
				str[0] = "0";
				str[1] = "提交失败";
			}
		}
		return str;
	}

}
