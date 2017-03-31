package Controller.SystemControl;

import java.awt.Label;
import java.util.Calendar;
import java.util.List;

import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.select.SelectorComposer;
import org.zkoss.zk.ui.select.annotation.Listen;
import org.zkoss.zk.ui.select.annotation.Wire;
import org.zkoss.zul.Combobox;
import org.zkoss.zul.Comboitem;
import org.zkoss.zul.Intbox;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Textbox;
import org.zkoss.zul.Window;

import bll.SystemControl.MenuListBll;
import bll.SystemControl.MenuList_UpdateBll;

import Model.MenuListModel;

public class MenuList_UpdateController extends SelectorComposer<Component>{
	@Wire
	private Textbox menuname;
	@Wire
	private Textbox menuurl;
	@Wire
	private Combobox menupname;
	@Wire
	private Intbox maneorder;
	@Wire
	private Textbox menuremark;
	@Wire
	private Window win;
	MenuListModel frommodel = (MenuListModel)Executions.getCurrent().getArg().get("model");
	
	// 重写组件初始化方法
	public void doAfterCompose(Component comp) throws Exception {
		// TODO Auto-generated method stub
		super.doAfterCompose(comp);
		MenuListBll mbll=new MenuListBll();
		List<MenuListModel> mlist=mbll.getMenuListInfo();
		if(!mlist.isEmpty())
		{
			Comboitem itemempty=new Comboitem();
			itemempty.setParent(menupname);
			itemempty.setValue("0");
			itemempty.setLabel("");
			menupname.setSelectedItem(itemempty);
			for(int i=0;i<mlist.size();i++)
			{
				if(mlist.get(i).getMeu_id()!=frommodel.getMeu_id())
				{
					Comboitem item=new Comboitem();
					item.setParent(menupname);
					item.setValue(""+mlist.get(i).getMeu_id());
					item.setLabel(mlist.get(i).getMeu_name());
					if(mlist.get(i).getMeu_name()==frommodel.getMeu_parentname()||mlist.get(i).getMeu_name().equals(frommodel.getMeu_parentname()))
					{
						menupname.setSelectedItem(item);
					}
				}
			}
		}
	}
	
	@Listen("onClick=#summit")
	public void updateCompactTemp() throws Exception {
		String str="";
		if(menuname.getValue()==""||menuname.getValue().equals(""))
		{
			str="输入的菜单名称不能为空";
		}
		else
		{
			MenuList_UpdateBll bll=new MenuList_UpdateBll();
			MenuListModel newmodel=new MenuListModel();
			newmodel.setMeu_name(menuname.getValue().toString());
			newmodel.setMeu_id(frommodel.getMeu_id());
			newmodel.setMeu_orderid(maneorder.getValue());
			newmodel.setMeu_remark(menuremark.getValue().toString());
			newmodel.setMeu_url(menuurl.getValue());
			if(menupname.getValue()!=null)
			{
				if(menupname.getValue()!=""&&!menupname.getValue().equals(""))
				{
					
					newmodel.setMeu_pid(Integer.parseInt(menupname.getSelectedItem().getValue().toString()));
				}
			}
			String strs=bll.ifsecond(newmodel,frommodel.getMeu_id());
			if(strs=="")
			{
				int k=bll.updateMenuList(newmodel);
				if(k>0)
				{
					Messagebox.show("修改成功","提示",Messagebox.OK, Messagebox.INFORMATION);
					win.detach();
				}
				else
				{
					Messagebox.show("修改失败","提示",Messagebox.OK, Messagebox.INFORMATION);
				}
			}
			else
			{
				Messagebox.show(strs,"提示",Messagebox.OK, Messagebox.INFORMATION);
			}
		}
	}
}
