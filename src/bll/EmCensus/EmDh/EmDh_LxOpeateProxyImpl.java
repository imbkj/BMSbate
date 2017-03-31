package bll.EmCensus.EmDh;

import impl.WorkflowCore.WfOperateImpl;

import org.zkoss.zul.Messagebox;

import dal.EmCensus.EmDh.EmDh_OperateDal;

import service.WorkflowCore.WfBusinessService;
import service.WorkflowCore.WfOperateService;

import Model.EmDhModel;
import Util.DateUtil;
import Util.UserInfo;

public class EmDh_LxOpeateProxyImpl implements EmDh_LxOpeate {

	@Override
	public String[] edit(EmDhModel m) {
		EmDh_OperateDal dal = new EmDh_OperateDal();
		String sql = ",emdh_proxytime='" + DateUtil.timechange(m.getProxytime())
				+ "',emdh_proxyname='" + UserInfo.getUsername() + "'";
		String[] str = new String[5];
		if (m.getEmdh_taprid() != null) {
			Object[] object = { "代理部受理", m, sql };
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
