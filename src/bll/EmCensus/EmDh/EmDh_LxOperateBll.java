package bll.EmCensus.EmDh;

import java.util.Date;

import impl.WorkflowCore.WfOperateImpl;
import service.WorkflowCore.WfBusinessService;
import service.WorkflowCore.WfOperateService;
import Model.EmDhModel;
import Util.UserInfo;
import dal.EmCensus.EmDh.EmDh_OperateDal;

public class EmDh_LxOperateBll {
	private EmDh_OperateDal dal = new EmDh_OperateDal();

	public int UpdateEmdhInfo(EmDhModel m, String sql) {
		int k = dal.UpdateEmdhInfo(m, sql);
		return k;
	}

	// 重置流程，流程退回到第三步，交接材料
	public String[] ReturnStep(EmDhModel m, String sql) {
		String[] str = new String[5];
		if (m.getEmdh_taprid() != null) {
			Object[] obj = { "重置流程", m, sql };
			WfBusinessService service = new EmDh_LxImpl();
			WfOperateService wfservice = new WfOperateImpl(service);
			str = wfservice.ReturnToN(obj, m.getEmdh_taprid(), 3,
					UserInfo.getUsername());
		} else {
			int k = UpdateEmdhInfo(m, sql);
			if (k > 0) {
				str[0] = "1";
			} else {
				str[0] = "0";
				str[1] = "提交失败";
			}
		}
		return str;
	}
	// 时间格式转换
	private java.sql.Date timechange(Date d) {
		java.sql.Date da = null;
		if (d != null && !d.equals("")) {
			java.sql.Date date = new java.sql.Date(d.getTime());
			da = date;
		}
		return da;
	}
}
