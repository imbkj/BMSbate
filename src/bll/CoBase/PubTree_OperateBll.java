package bll.CoBase;

import java.util.List;

import dal.CoBase.PubTree_OperateDal;

import Model.DepartmentModel;
import Model.LoginModel;
import Model.pubTreeModel;

public class PubTree_OperateBll {
	PubTree_OperateDal dal=new PubTree_OperateDal();
	
	//获取pubTree表的第一级数据
	public List<pubTreeModel> getPubTreeParentInfo()
	{
		return dal.getPubTreeParentInfo();
	}
	
	//根据pid获取pubTree表每一级数据
	public List<pubTreeModel> getPubTreeChildrenInfo(int pid)
	{
		return dal.getPubTreeChildrenInfo(pid);
	}
	
	//获取pubTree表所有数据
	public List<pubTreeModel> getPubTreeAllInfo()
	{
		return dal.getPubTreeAllInfo();
	}
	
	public List<DepartmentModel> getDepartmentInfo() {
		return dal.getDepartmentInfo();
	}
	
	public List<LoginModel> getLoginInfo() {
		return dal.getLoginInfo();
	}

}
