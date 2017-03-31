package impl.SystemControl;

import java.util.List;

import bll.SystemControl.MenuListBll;
import service.PublicDataService;

import Model.MenuListModel;


public class MenuListIpml implements PublicDataService{
	MenuListBll bll=new MenuListBll();
	private MenuListModel menumodel;
	private int id=0;
	public MenuListIpml(MenuListModel model,int id){
		this.menumodel=model;
		this.id=id;
	}
	public MenuListIpml(){
	}
	@Override
	public int update() {
		// TODO Auto-generated method stub
		return bll.updateMenuList(menumodel);
	}
	@Override
	public int delete() {
		// TODO Auto-generated method stub
		return bll.deleteMenuList(menumodel);
	}
	@Override
	public int add() {
		// TODO Auto-generated method stub
		return bll.addMenuList(menumodel);
	}
	@Override
	public int check() {
		// TODO Auto-generated method stub
		List<MenuListModel> listmodel= bll.getMenuParentInfo(id);
		int l=0;
		if(listmodel.size()>0)
		{
			l=listmodel.size();
		}
		return l;
	}

}
