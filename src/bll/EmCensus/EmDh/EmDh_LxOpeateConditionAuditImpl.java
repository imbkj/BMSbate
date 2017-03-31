package bll.EmCensus.EmDh;

import dal.EmCensus.EmDh.EmDh_OperateDal;
import impl.WorkflowCore.WfOperateImpl;
import service.WorkflowCore.WfBusinessService;
import service.WorkflowCore.WfOperateService;
import Model.EmDhModel;
import Util.DateUtil;
import Util.UserInfo;
/**
 * 
 * @author chenyaojia
 *@Descript:条件审核
 */
public class EmDh_LxOpeateConditionAuditImpl implements EmDh_LxOpeate{

	@Override
	public String[] edit(EmDhModel m) {
		EmDh_OperateDal dal = new EmDh_OperateDal();
		String sql = ",emdh_time3='" + DateUtil.timechange(m.getEmdh_time3())
				+ "',emdh_state=2,emdh_zhtype='" + m.getEmdh_zhtype() + "'";
		String[] str = new String[5];
		if (m.getEmdh_taprid() != null) {
			Object[] object = { "条件审核", m, sql };
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
