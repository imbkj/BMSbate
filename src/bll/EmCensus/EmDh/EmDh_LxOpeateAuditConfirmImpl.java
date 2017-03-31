package bll.EmCensus.EmDh;

import impl.WorkflowCore.WfOperateImpl;

import java.math.BigDecimal;
import java.text.DecimalFormat;

import service.WorkflowCore.WfBusinessService;
import service.WorkflowCore.WfOperateService;

import dal.EmCensus.EmDh.EmDh_OperateDal;

import Model.EmDhModel;
import Util.DateUtil;
import Util.UserInfo;

public class EmDh_LxOpeateAuditConfirmImpl implements EmDh_LxOpeate{

	@Override
	public String[] edit(EmDhModel m) {
		EmDh_OperateDal dal = new EmDh_OperateDal();
		String sql = ",emdh_time5='" + DateUtil.timechange(m.getEmdh_time5()) + "',emdh_state=4";
		if (m.getEmdh_fee() != null) {
			DecimalFormat df = new DecimalFormat("#.00");
			BigDecimal bdfee = m.getEmdh_fee();
			sql = sql + ",emdh_fee='" + df.format(bdfee) + "'";
			bdfee = bdfee.add(m.getEmdh_totalfee());
			sql = sql + ",emdh_totalfee='" + df.format(bdfee) + "'";
			sql = sql + ",emdh_fistfeetype='" + m.getEmdh_fistfeetype() + "'";
		}
		String[] str = new String[5];
		if (m.getEmdh_taprid() != null) {
			Object[] object = { "预审确认", m, sql };
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
