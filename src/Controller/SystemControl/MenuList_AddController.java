package Controller.SystemControl;

import impl.SystemControl.MenuListIpml;

import java.util.List;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.select.SelectorComposer;
import org.zkoss.zk.ui.select.annotation.Listen;
import org.zkoss.zk.ui.select.annotation.Wire;
import org.zkoss.zul.Combobox;
import org.zkoss.zul.Listbox;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Textbox;
import bll.SystemControl.MenuListBll;

import Model.MenuListModel;

import service.PublicDataService;

public class MenuList_AddController extends SelectorComposer<Component>{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@Wire
	private Textbox menuname; //一级菜单名称
	@Wire
	private Textbox menuorder; //一级菜单排序
	@Wire
	private Listbox menutarget; //一级菜单的打开方式
	@Wire
	private Textbox menuurl; //一级菜单的URL
	@Wire
	private Textbox menuremark; //一级菜单的备注
	@Wire
	private Textbox tmenuname; //二级菜单名称
	@Wire
	private Combobox firstmenu; //一级菜单的下拉列表
	@Wire
	private Textbox tmenuorder; //二级菜单排序
	@Wire
	private Listbox tmenutarget; //二级菜单的打开方式
	@Wire
	private Textbox tmenuurl; //二级菜单的URL
	@Wire
	private Textbox tmenuremark; //二级菜单的备注
	int selectid;
	
	MenuListBll bll=new MenuListBll();
	List<MenuListModel> menulist=bll.getMenuinfolist();
	
	public List<MenuListModel> getMenulist() {
		return menulist;
	}
	
	//定义添加一级菜单的方法
	@Listen("onClick = #addmenubtn")
	public void addMenuList(){
		int order=1;
		if(menuname.getValue()!=null&&!menuname.getValue().equals(""))
		{
			if(menuorder.getValue()!=""&&!menuorder.getValue().equals(""))
			{
				order=Integer.parseInt(menuorder.getValue());
			}
			MenuListModel model=new MenuListModel(0, menuname.getValue(), menuurl.getValue(),0,"",menuremark.getValue(),order,menutarget.getSelectedItem().getLabel().toString());
			PublicDataService service=new MenuListIpml(model,0);
			int k=service.add();
			if(k>0)
			{
				Messagebox.show("添加成功", "提示", Messagebox.OK, Messagebox.INFORMATION);
				Executions.sendRedirect("MenuList_Add.zul");
			}
			else
			{
				Messagebox.show("添加失败", "提示", Messagebox.OK, Messagebox.INFORMATION);
			}
		}
		else
		{
			Messagebox.show("菜单名称不能为空", "提示", Messagebox.OK, Messagebox.INFORMATION);
			menuname.focus();
		}
	}
	
	//定义添加二级菜单的方法
	@Listen("onClick = #taddmenubtn")
	public void addtMenuList(){
		int order=1;
		int pid=0;
		String num=firstmenu.getValue();
		if(num!=null&&!num.equals("")&&num!="")
		{
			if(tmenuname.getValue()!=null&&!tmenuname.getValue().equals(""))
			{
				pid=Integer.parseInt(firstmenu.getSelectedItem().getValue().toString());
				if(tmenuorder.getValue()!="")
				{
					order=Integer.parseInt(tmenuorder.getValue());
				}
				MenuListModel model=new MenuListModel(0, tmenuname.getValue(), tmenuurl.getValue(),pid,"",tmenuremark.getValue(),order,menutarget.getSelectedItem().getLabel().toString());
				PublicDataService service=new MenuListIpml(model,0);
				int k=service.add();
				if(k>0)
				{	
					Messagebox.show("添加成功", "提示", Messagebox.OK, Messagebox.INFORMATION);
					Executions.sendRedirect("MenuList_Add.zul");	//跳转页面
				}
				else
				{
					Messagebox.show("添加失败", "提示", Messagebox.OK, Messagebox.INFORMATION);
				}
			}
			else
			{
				Messagebox.show("子菜单名称不能为空", "提示", Messagebox.OK, Messagebox.INFORMATION);
				tmenuname.focus();
			}
		}
		else
		{
			Messagebox.show("请选择父菜单", "提示", Messagebox.OK, Messagebox.INFORMATION);
		}
	}
	
	//添加子菜单的时候选择的父级菜单的下拉列表的选择事件
	@Listen("onSelect = #firstmenu")
    public void selecttree(Event e){
		this.selectid=firstmenu.getSelectedItem().getValue();
		PublicDataService service=new MenuListIpml(null,selectid);
		int k=service.check()+1;
		tmenuorder.setValue(""+k);
	}
}
