package bll.CoQuotation;

import java.util.HashMap;
import java.util.Map;

import impl.WorkflowCore.WfOperateImpl;
import service.WorkflowCore.WfBusinessService;
import service.WorkflowCore.WfOperateService;
import Model.CoLatencyClientModel;
import Model.CoOfferModel;
import Util.UserInfo;

public class CoofferOperateBll {
	//报价单审核流程新增
	public String[] CoofferAuditAdd(CoOfferModel m) {
		//根据潜在客户ID查询公司简称
		CoQuotation_List1Bll bll=new CoQuotation_List1Bll();
		CoLatencyClientModel comodel=bll.getModel(m.getCoof_cola_id());
		Object[] obj = {"1",m,"报价单审核新增"};
		Integer cid=0;
		if(m.getCid()!=null)
		{
			cid=m.getCid();
		}
		else
		{
			cid=m.getCoof_cola_id();
		}
		WfBusinessService wfbs = new CoofferImpl();
		WfOperateService wfs = new WfOperateImpl(wfbs);
		String[] str = wfs.AddTaskToNext(obj, "报价单审核",
		comodel.getCola_company()+"——"+m.getCoof_name()+"报价单审核",109, UserInfo.getUsername(), "",cid, "");
		return str;
	}
	
	//报价单审核
	public String[] CoofferAuditUpdate(CoOfferModel m,Integer tarpid) {
		Integer cid=0;
		if(m.getCid()!=null)
		{
			cid=m.getCid();
		}
		else
		{
			cid=m.getCoof_cola_id();
		}
		Object[] obj = {"2",m};
		WfBusinessService wfbs = new CoofferImpl();
		WfOperateService wfs = new WfOperateImpl(wfbs);
		String[] str = wfs.PassToNext(obj,tarpid, UserInfo.getUsername(), "",cid, "");
		return str;
	}
	
	//报价单审核退回
	public String[] CoofferAuditBack(CoOfferModel m,Integer tarpid) {
		Object[] obj = {"1",m};
		WfBusinessService wfbs = new CoofferImpl();
		WfOperateService wfs = new WfOperateImpl(wfbs);
		//退回时终止报价单审核流程
		String[] str = wfs.StopTask(obj,tarpid, UserInfo.getUsername(), "");
		return str;
	}
	
	//重新提交
	public String[] coofferReSummit(CoOfferModel m,Integer tarpid) {
		Object[] obj = {"1",m,"报价单审核重新提交"};
		Integer cid=0;
		if(m.getCid()!=null)
		{
			cid=m.getCid();
		}
		else
		{
			cid=m.getCoof_cola_id();
		}
		WfBusinessService wfbs = new CoofferImpl();
		WfOperateService wfs = new WfOperateImpl(wfbs);
		String[] str = wfs.PassToNext(obj,tarpid, UserInfo.getUsername(), "", cid, "");
		return str;
	}
}
