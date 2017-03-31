package bll.EmCensus.EmDh;

import dal.EmCensus.EmDh.EmDh_OperateDal;
import impl.WorkflowCore.WfOperateImpl;
import service.WorkflowCore.WfBusinessService;
import service.WorkflowCore.WfOperateService;
import Model.EmDhModel;
import Util.DateUtil;
import Util.UserInfo;

public class EmDh_LxOpeateUpDataImpl implements EmDh_LxOpeate{

	@Override
	public String[] edit(EmDhModel m) {
		EmDh_OperateDal dal = new EmDh_OperateDal();
		String sql = ",emdh_time2='" + DateUtil.timechange(m.getEmdh_time2())+ "'";
		String[] str = new String[5];
		if (m.getEmdh_taprid() != null) {
			Object[] object = { "交接材料", m, sql };
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
