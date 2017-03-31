package bll.CoMenuList;

import java.util.List;

import Model.CobaseMenulistModel;
import dal.CoMenuList.Come_SelectDal;

public class Come_SelectBll {
	private Come_SelectDal dal=new Come_SelectDal();
	// 获取公司菜单名称
	public List<CobaseMenulistModel> getEmbaseMenuListInfo(String str){
		return dal.getEmbaseMenuListInfo(str);	
	}
	
	// 查询菜单与角色关系表
	public List<CobaseMenulistModel> getCobaseMenuListRel(String str) {
		return dal.getCobaseMenuListRel(str);
	}
	
	//根据id查询菜单
	public CobaseMenulistModel getMenuInfo(String id){
		List<CobaseMenulistModel> list=getEmbaseMenuListInfo("and come_id="+id);
		CobaseMenulistModel model=new CobaseMenulistModel();
		if(list.size()>0)
		{
			model=list.get(0);
		}
		return model;
	}
}
