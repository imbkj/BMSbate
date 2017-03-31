package bll.SystemControl;

import java.util.List;

import Model.MenuListModel;
import Model.RoleModel;
import dal.SystemControl.Menu_RolePubDal;

public class Menu_RolePubBll {
	/*
	  Author:陈耀家
	  Create date: 09/13/2013
	  Description:把菜单分配给角色
	*/
	private Menu_RolePubDal dal=new Menu_RolePubDal();
	
	//获取操作角色数据
	public List<RoleModel> getRoleData(){
		return dal.getRoleData();
	}
	
	//更新角色对菜单的权限
	public int updateMenuPub(int rol_id,int meu_id)
	{
		int k=0;
		k=dal.updateMenuPub(rol_id, meu_id);
		return k;	
	}
	
	//删除角色对菜单的权限
	public int deleteMenuPub(String str,int rol_id)
	{
		int k=0;
		k=dal.deleteMenuPub(str, rol_id);
		return k;
	}
	
	//判断某个角色是否有menu_id的菜单权限
	public List<MenuListModel> existmenugroup(String str){
		List<MenuListModel> model=dal.getmenupub(str);
		
		return model;
	}
}
