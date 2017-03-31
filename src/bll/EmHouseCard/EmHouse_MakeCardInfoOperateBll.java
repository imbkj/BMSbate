package bll.EmHouseCard;

import java.util.HashMap;
import java.util.Map;

import impl.WorkflowCore.WfOperateImpl;
import service.WorkflowCore.WfBusinessService;
import service.WorkflowCore.WfOperateService;
import Model.EmHouseMakeCardInfoModel;
import Model.EmHouseTakeCardInfoModel;
import Model.emhouseMakeCardBackInfoModel;
import Util.UserInfo;

public class EmHouse_MakeCardInfoOperateBll {
	//公积金制卡信息新增
	public String[] EmHuMakeCardAdd(EmHouseMakeCardInfoModel m) {
		Object[] obj = {"1",m};
		Integer cid=0;
		if(m.getCid()!=null)
		{
			cid=m.getCid();
		}
		Map<String, String> map=new HashMap<String, String>();
		map.put("cid", cid+"");
		map.put("gid", m.getGid()+"");
		WfBusinessService wfbs = new EmHouse_MakeCardAddImpl();
		WfOperateService wfs = new WfOperateImpl(wfbs);
		String[] str = wfs.AddTaskToNext(obj, "公积金制卡新增",
			m.getUsername()+"公积金制卡新增",64, UserInfo.getUsername(), "", cid, "",map);
		return str;
	}
	
	//公积金制卡信息修改
	public String[] EmHuMakeCardUpdate(EmHouseMakeCardInfoModel m,String sql) {
		Object[] obj = {"2",m,sql};
		WfBusinessService wfbs = new EmHouse_MakeCardAddImpl();
		WfOperateService wfs = new WfOperateImpl(wfbs);
		String[] str = wfs.PassToNext(obj,m.getTarpid(), UserInfo.getUsername(), "", 0, "");
		return str;
	}
	
	//退回
	public String[] backMakeCardInfo(emhouseMakeCardBackInfoModel m,Integer tarpid) {
		Object[] obj = {m};
		WfBusinessService wfbs = new EmHouse_MakeCardAddImpl();
		WfOperateService wfs = new WfOperateImpl(wfbs);
		String[] str = wfs.ReturnToN(obj,tarpid,2, UserInfo.getUsername());
		return str;
	}

}
