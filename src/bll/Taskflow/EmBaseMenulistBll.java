package bll.Taskflow;

import impl.WorkflowCore.WfOperateImpl;
import service.WorkflowCore.WfBusinessService;
import service.WorkflowCore.WfOperateService;
import bll.EmHouseCard.EmHouse_MakeCardAddImpl;
import Model.EmHouseMakeCardInfoModel;
import Model.EmbaseModel;
import Util.UserInfo;
import dal.Taskflow.EmBaseMenulistDal;

public class EmBaseMenulistBll {
	private EmBaseMenulistDal dal=new EmBaseMenulistDal();
	
	//员工基本信息修改
	public Integer EmbaseUpdate(EmbaseModel em) {
		return dal.EmbaseUpdate(em);
	}
	
	//把选择的菜单Id添加到EmbaseMenuList表
	public Integer EmbaseMenuListAdd(Integer gid,Integer menuid)
	{
		return dal.EmbaseMenuListAdd(gid, menuid);
	}
	//根据gid删除信息
	public Integer EmbaseMenuListdelete(Integer gid)
	{
		return dal.EmbaseMenuListdelete(gid);
	}
	
	//下一步
	public String[] EmbaseMenuListUpdate(Integer tarpid,Integer id,Integer cid) {
		Object[] obj = {id};
		WfBusinessService wfbs = new EmbaseMenuListImpl();
		WfOperateService wfs = new WfOperateImpl(wfbs);
		String[] str = wfs.PassToNext(obj,tarpid, UserInfo.getUsername(), "", cid, "");
		return str;
	}
}
