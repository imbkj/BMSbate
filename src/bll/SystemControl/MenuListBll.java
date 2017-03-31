package bll.SystemControl;

import java.util.List;

import dal.SystemControl.MenuListDal;

import Model.FixedTabMenuListModel;
import Model.MenuListModel;

public class MenuListBll {
	/*
	 * Author:陈耀家 
	 * Create date: 09/06/2013 
	 * Description:使用tree生成菜单
	 */

	// 实例化一个treeDal对象
	public MenuListDal dal = new MenuListDal();

	// 获取菜单表MenuList的数据
	public List<MenuListModel> getMenuListInfo() {
		return dal.getMenuData();
	}
	//查询菜单表menulist并返回List<TreeModel>类型的变量
	public List<MenuListModel> getMenuinfolist() {
		return dal.getMenuinfolist();
	}
	
	// 获取菜单表MenuList的数据和菜单的父菜单名称
	public List<MenuListModel> getMenuInfo(String str) {
		return dal.getMenuInfo(str);
	}
	
	// 根据登录用户获取菜单表MenuList的某菜单的子菜单的数据
	public List<MenuListModel> getMenuPInfo(int id) {
		return dal.getMenuParentInfo(id);
	}
	
	// 根据父id获取菜单表MenuList的某菜单的子菜单的数据
	public List<MenuListModel> getMenuParentInfo(int id) {
		return dal.getMenuPInfo(id);
	}
	
	// 根据id获取菜单表MenuList的数据
	public List<MenuListModel> getMenuId(int id) {
		return dal.getMenuId(id);
	}
	
	//添加菜单方法
	public int addMenuList(MenuListModel model){
		return dal.addMenuList(model);
	}
	
	//更新菜单方法
	public int updateMenuList(MenuListModel model)
	{	if(model.getMeu_name()!=model.getMeu_parentname()&&!model.getMeu_name().equals(model.getMeu_parentname()))
		{
			if(existMenuName(model)==1)
			{
				if(!dal.existMenuName(model).isEmpty())
				{
					int pid=dal.existMenuName(model).get(0).getMeu_id();
					return dal.updateMenuList(model,pid);
				}
				else
				{
					return dal.updateMenuList(model,0);
				}
			}
			else
			{
				return -1;
			}
		}
		else
		{
			return -2;
		}
	}
	
	//根据父菜单名称判断更改的父菜单是否是该菜单的子菜单
	public int existMenuName(MenuListModel model)
	{
		int k=1;
		System.out.println("次数"+k);
		if(model.getMeu_id()!=0)
		{
			List<MenuListModel> menumodel=dal.existMenuName(model);
			if(!menumodel.isEmpty())
			{
				if(model.getMeu_id()!=menumodel.get(0).getMeu_pid())
				{
					existMenuName(menumodel.get(0));
				}
				else
				{
					k=0;
				}
			}
			
		}
		return k;
	}
	
	//删除菜单方法
	public int deleteMenuList(MenuListModel model)
	{
		int k=0;
		//先判断是否有子菜单
		List<MenuListModel> menupainfo=dal.getMenuPInfo(model.getMeu_id());
		if(!menupainfo.isEmpty())
		{
			k=-1;
		}
		else
		{
			k=dal.deleteMenut(model);
		}
		return k;
	}
	
	//获取固定标签的信息
	public List<FixedTabMenuListModel> getFixedTabMenuList(String name) {
		return dal.getFixedTabMenuList(name);
	}
	//查询某用户是否已存在固定标签
	public boolean ifExistFixedTabMenu(String name,Integer id) {
		return dal.ifExistFixedTabMenu(name, id);
	}
	//添加固定标签的信息
	public int addFixedTabMenuList(String name,int id) {
		if(ifExistFixedTabMenu(name, id))
		{
			return 1;
		}
		else
		{
			return dal.addFixedTabMenuList(name, id);
		}
	}
	//取消固定标签的信息
	public int deleteFixedTabMenuList(String name,int id) {
		return dal.deleteFixedTabMenuList(name, id);
	}
	//根据username和menu_id获取固定标签的信息
	public FixedTabMenuListModel getFixedTabMenuListid(String name,int id) {
		return dal.getFixedTabMenuListid(name, id);
	}
	
	//根据菜单id 查询菜单的
	public MenuListModel getMenuFlag(String id) {
		return dal.getMenuFlag(id);
	}
	//判断点击标签时菜单是否刷新
	public boolean ifreflesh(String id)
	{
		boolean flag=false;
		MenuListModel model=getMenuFlag(id);
		if(model.getMenu_flag()!=null&&model.getMenu_flag()==1)
		{
			flag=true;
		}
		return flag;
	}
	
	//根据用户名查询用户的首页
	public String getIndexSrc()
	{
		return dal.getIndexSrc();
	}
}
