package bll.CoLatencyClient;

import java.util.HashMap;
import java.util.Map;

import impl.WorkflowCore.WfOperateImpl;
import service.WorkflowCore.WfBusinessService;
import service.WorkflowCore.WfOperateService;
import dal.CoLatencyClient.CoServiceRequestOperateDal;
import Model.CoServiceRequestModel;
import Util.UserInfo;

public class CoServiceRequestOperateBll {
	private CoServiceRequestOperateDal dal=new CoServiceRequestOperateDal();
	// 添加服务要求信息
	public Integer CoServiceRequests_Add(CoServiceRequestModel model) {
		model.setCsqe_request(1);
		return dal.CoServiceRequest_Add(model);
	}
	
	// 服务要求信息修改
	public Integer CoServiceRequest_update(CoServiceRequestModel model) {
		return dal.CoServiceRequest_update(model);
	}
	
	// 添加服务要求信息
	public Integer CoServiceRequest_Add(CoServiceRequestModel model,Integer ifexits) {
		//model.setCsqe_salary(1);
		Integer k=0;
		if(ifexits>0)
		{
			Object[] obj = {"1",model};
			Integer cid=0;
			if(model.getCid()!=null)
			{
				cid=model.getCid();
			}
			model.setCsqe_salary(1);
			Map<String, String> map=new HashMap<String, String>();
			map.put("cid", cid+"");
			WfBusinessService wfbs = new CoServiceRequestIpml();
			WfOperateService wfs = new WfOperateImpl(wfbs);
			String[] str = wfs.AddTaskToNext(obj, "薪酬服务交接",
					model.getCoba_shortname()+"("+model.getCid()+")薪酬服务交接",92, UserInfo.getUsername(), "", cid, "",map);
			if(str[0]=="1")
			{
				k=1;
			}
		}
		else
		{
			k=dal.CoServiceRequest_Add(model);
		}
		return k;
	}
	
	// 添加服务要求信息
	public Integer CoServiceRequest_updates(CoServiceRequestModel model,Integer ifexits) {
		model.setCsqe_salary(1);
			Integer k=0;
			if(ifexits>0)
			{
				Integer cid=0;
				if(model.getCid()!=null)
				{
					cid=model.getCid();
				}
				//表示已存在服务要求，只需更新
				Object[] obj = {"0",model};
				WfBusinessService wfbs = new CoServiceRequestIpml();
				WfOperateService wfs = new WfOperateImpl(wfbs);
				String[] str = wfs.AddTaskToNext(obj, "薪酬服务交接",
						model.getCoba_shortname()+"("+model.getCid()+")薪酬服务交接",92, UserInfo.getUsername(), "",cid, "");
				if(str[0]=="1")
				{
					k=1;
				}
			}
			else
			{
				k=dal.CoServiceRequest_Add(model);
			}
			return k;
		}
	
	//修改薪酬服务要求
	public String[] CoServiceRequest_update(CoServiceRequestModel model,String sql,Integer tarid) {
		Object[] obj = {"2",model,sql};
		String[] str=new String[5];
		Integer cid=0;
		if(model.getCid()!=null)
		{
			cid=model.getCid();
		}
		WfBusinessService wfbs = new CoServiceRequestIpml();
		WfOperateService wfs = new WfOperateImpl(wfbs);
		str = wfs.PassToNext(obj,tarid, UserInfo.getUsername(), "",cid, "");
		return str;
	}
	
	//更新公司薪酬负责人和薪酬审核人
	public boolean updateCobase(String coba_gzaddname,String coba_gzaudname,int cid) {
		return dal.updateCobase(coba_gzaddname, coba_gzaudname, cid);
	}
}
