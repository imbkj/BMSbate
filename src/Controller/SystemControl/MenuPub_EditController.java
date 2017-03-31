package Controller.SystemControl;

import impl.SystemControl.MenuListIpml;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections.map.HashedMap;
import org.zkoss.bind.annotation.BindingParam;
import org.zkoss.bind.annotation.Command;
import org.zkoss.bind.annotation.NotifyChange;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.event.EventListener;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Window;
import org.zkoss.zul.Messagebox.ClickEvent;

import bll.SystemControl.MenuListBll;

import service.PublicDataService;

import Model.MenuListModel;

public class MenuPub_EditController{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String str="";
	MenuListBll bll=new MenuListBll();
	List<MenuListModel> menumodel=bll.getMenuInfo(" and menu_state=1");
	private String menuname="";
	private String pmenuname="";
	
	public List<MenuListModel> getMenumodel() {
		return menumodel;
	}
	
	//弹出更新菜单页面
	@Command
	@NotifyChange("menumodel")
	public void updatemenu(@BindingParam("menuupdate") final MenuListModel model)
	{
		Map map = new HashMap();
		map.put("model", model);
		Window window = (Window) Executions.createComponents(
				"MenuList_Update.zul", null, map);
		window.doModal();
		menumodel=bll.getMenuInfo(str);
	}
	
	//删除菜单事件
	@Command
	@NotifyChange("menumodel")
	public void deletemenu(@BindingParam("menudelete") final MenuListModel model)
	{
		 EventListener<ClickEvent> clickListener = new EventListener<Messagebox.ClickEvent>() {
	           public void onEvent(ClickEvent event) throws Exception {
	                if(Messagebox.Button.YES.equals(event.getButton())) {
	                	int k=0;
	                	PublicDataService menuservice=new MenuListIpml(model,0);
	                	k=menuservice.delete();
	                	if(k>0)
	                	{
	                		Messagebox.show("删除成功");
	                		menumodel=bll.getMenuInfo(" and menu_state=1");
	                	}
	                	else if(k==-1)
	                	{
	                		Messagebox.show("删除失败,请先删除该菜单的子菜单");
	                	}
	                	else{
	                		Messagebox.show("删除失败");
	                	}
	                }
	            }
	        };
		Messagebox.show("确定要删除该菜单吗?", "确认提示", new Messagebox.Button[]{
                Messagebox.Button.YES, Messagebox.Button.NO }, Messagebox.QUESTION, clickListener);
    
	}
	
	//查询菜单事件
	@Command
	@NotifyChange("menumodel")
	public void searchmenu()
	{
		str="";
		if(menuname!=""&&!menuname.equals(""))
		{
			str=str+" and a.meu_name like '%"+menuname+"%'";
		}
		if(pmenuname!=""&&!pmenuname.equals(""))
		{
			str=str+" and b.meu_name like '%"+pmenuname+"%'";
		}
		menumodel=bll.getMenuInfo(str);
	}

	public String getMenuname() {
		return menuname;
	}

	public void setMenuname(String menuname) {
		this.menuname = menuname;
	}

	public String getPmenuname() {
		return pmenuname;
	}

	public void setPmenuname(String pmenuname) {
		this.pmenuname = pmenuname;
	}
}
