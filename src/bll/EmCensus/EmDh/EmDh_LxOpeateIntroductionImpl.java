package bll.EmCensus.EmDh;

import java.math.BigDecimal;
import java.text.DecimalFormat;

import impl.WorkflowCore.WfOperateImpl;

import org.zkoss.zul.Messagebox;

import dal.EmCensus.EmDh.EmDh_OperateDal;

import service.WorkflowCore.WfBusinessService;
import service.WorkflowCore.WfOperateService;

import Model.EmDhModel;
import Util.DateUtil;
import Util.UserInfo;

public class EmDh_LxOpeateIntroductionImpl implements EmDh_LxOpeate {

	@Override
	public String[] edit(EmDhModel m) {
		EmDh_OperateDal dal = new EmDh_OperateDal();
		String sql = ",emdh_time8='" + DateUtil.timechange(m.getEmdh_time8()) + "'";
		if (m.getNowfee() != null) {
			DecimalFormat df = new DecimalFormat("#.00");
			BigDecimal bd =m.getNowfee();
			sql = sql + ",emdh_fee='" + df.format(bd)
					+ "',emdh_secondfeetype='" + m.getNowfeetype() + "'";
			bd = bd.add(m.getEmdh_totalfee());
			sql = sql + ",emdh_totalfee='" + df.format(bd) + "'";
		}
		String[] str = new String[5];
		if (m.getEmdh_taprid() != null) {
			Object[] object = { "介绍信下达", m, sql };
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
