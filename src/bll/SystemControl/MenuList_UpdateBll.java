package bll.SystemControl;

import dal.SystemControl.MenuList_UpdateDal;
import Model.MenuListModel;

public class MenuList_UpdateBll {
	private String str="";
	MenuList_UpdateDal dal=new MenuList_UpdateDal();
	//修改菜单表menulist的一条数据，并返回一个Int类型的数
	public int updateMenuList(MenuListModel model){
		return dal.updateMenuList(model);
	}
	
	//判断修改的父菜单是否是该菜单的子菜单
	public String ifsecond(MenuListModel model,int menuid)
	{
		MenuListModel mmodel=dal.selectMenuList(model);
		if(mmodel.getMeu_pid()!=menuid)
		{
			
		}
		else
		{
			str="选择的父菜单不能使该菜单的子菜单";
		}
		
		if(mmodel.getMeu_pid()!=menuid)
		{
			if(mmodel.getMeu_pid()!=0&&str=="")
			{
				str=ifsecond(mmodel,menuid);
			}
		}
		return str;
	}

}
