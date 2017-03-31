package Controller.SystemControl;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.Set;

import javax.servlet.http.HttpServletResponse;

import impl.SystemControl.Menu_PubImpl;

import org.zkoss.bind.annotation.BindingParam;
import org.zkoss.bind.annotation.Command;
import org.zkoss.bind.annotation.NotifyChange;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.select.SelectorComposer;
import org.zkoss.zk.ui.select.annotation.Listen;
import org.zkoss.zk.ui.select.annotation.Wire;
import org.zkoss.zul.Checkbox;
import org.zkoss.zul.Combobox;
import org.zkoss.zul.Filedownload;
import org.zkoss.zul.Grid;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Tree;
import org.zkoss.zul.Treecell;
import org.zkoss.zul.Treechildren;
import org.zkoss.zul.Treeitem;
import org.zkoss.zul.Treerow;


import bll.SystemControl.EmBuCenter_OperateBll;
import bll.SystemControl.EmBuCenter_SelectBll;
import bll.SystemControl.MenuListBll;
import bll.SystemControl.Menu_RolePubBll;

import Model.EmbaseBusinessCenterModel;
import Model.MenuListModel;
import Model.RoleModel;
import Util.UserInfo;
import Util.plyUtil;
import service.ExcelService;
import service.PublicDataService;

public class MenuList_PubManangerController extends SelectorComposer<Component>{
	/*
	  Author:陈耀家
	  Create date: 09/13/2013
	  Description:菜单权限分配控制类
	*/
	private static final long serialVersionUID = 1L;
	private Menu_RolePubBll bll=new Menu_RolePubBll();
	private List<RoleModel> rolemodel=bll.getRoleData();
	private List<MenuListModel> menulist=new ArrayList<MenuListModel>();
	private List<EmbaseBusinessCenterModel> embulist=new ArrayList<EmbaseBusinessCenterModel>();
	private int rolId;
	private MenuListBll menubll=new MenuListBll();
	
	//选择操作角色列表事件
	@Command
	public void roleselect(@BindingParam("rolelist") Combobox rolelist,@BindingParam("tree") Tree tree,
			@BindingParam("tre") Tree tre,@BindingParam("menuall") Checkbox menuall,@BindingParam("busall") Checkbox busall){
		int selectid=rolelist.getSelectedItem().getValue();
		menuall.setChecked(false);
		busall.setChecked(false);
		//得到角色的id
		this.rolId=selectid;
		Menu_RolePubBll bll=new Menu_RolePubBll();
		EmBuCenter_SelectBll sbl=new EmBuCenter_SelectBll();
		//得到主菜单的树所有的节点
		Collection<Treeitem> items=tree.getItems();
		//得到业务菜单的树所有的节点
		Collection<Treeitem> itemtre=tre.getItems();
		List<MenuListModel> mlists=new ArrayList<MenuListModel>();
		List<EmbaseBusinessCenterModel> bulist=new ArrayList<EmbaseBusinessCenterModel>();
		//查询主菜单权限关系表是否有该角色的数据
		mlists=bll.existmenugroup(" and rol_id="+rolId);
		//查询业务中心菜单权限关系表是否有该角色的数据
		bulist=sbl.getbumenupub(" and rol_id="+rolId);
		//主菜单
		for(Treeitem itemed: items)
		{
			String idstr=itemed.getId();
			if(idstr!=""&&!idstr.equals(""))
			{
				int id = Integer.parseInt(idstr.substring(3));
				boolean flag=ifsame(mlists, id);
				if(flag)
				{
					itemed.setOpen(true);
					itemed.setSelected(true);
				}
				else
				{
					itemed.setOpen(false);
					itemed.setSelected(false);
				}
			}
		}
		
		//业务中心菜单
		for(Treeitem itemed: itemtre)
		{
			String idstr=itemed.getId();
			if(idstr!=""&&!idstr.equals(""))
			{
				int id = Integer.parseInt(idstr.substring(2));
				boolean flag=ifsametre(bulist, id);
				if(flag)
				{
					itemed.setOpen(true);
					itemed.setSelected(true);
				}
				else
				{
					itemed.setOpen(false);
					itemed.setSelected(false);
				}
			}
		}
		
	}
	
	
	//导出excel
		@Command("Export")
		public void Export(HttpServletResponse response,@BindingParam("gd") Grid gd) throws Exception {
			MenuListBll nl=new MenuListBll();
			List<MenuListModel> cobamenulist=nl.getMenuinfolist();
			plyUtil ply = new plyUtil();
			String path = "/../../SystemControl/file/";
			// 创建当前日子
			Date date = new Date();
			// 格式化日期
			SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmssSSS");
			// 格式化日期(产生文件名)
			String filename = "菜单.xls";
			// 获取绝对路径
			path=ply.getAbsolutePath(path, filename, this);
			// System.out.println(path);
			// 创建文件
			File file = new File(path);
			file.createNewFile();
			String sheetName = "公积金卡领卡信息";
			String[] title = {"一级菜单", "二级菜单", "三级菜单", "四级菜单","五级菜单","六级菜单","排序"};
			try {
				ExcelService exl = new bll.SystemControl.ExcelImpl(file, sheetName, title,cobamenulist);
				exl.writeExcel();
			} catch (Exception e) {
				System.out.println(e.toString());
			}
			Filedownload.save(file, "xls");
			//file.delete();
		}
	
	private boolean ifsame(List<MenuListModel> mlists,int id)
	{
		boolean flag=false;
		if(!mlists.isEmpty())
		{
			for(int i=0;i<mlists.size();i++)
			{
				if(id==mlists.get(i).getMeu_id())
				{
					flag=true;
				}
			}
		}
		return flag;
	}
	
	private boolean ifsametre(List<EmbaseBusinessCenterModel> mlists,int id)
	{
		boolean flag=false;
		if(!mlists.isEmpty())
		{
			for(int i=0;i<mlists.size();i++)
			{
				if(id==mlists.get(i).getEmce_id())
				{
					flag=true;
				}
			}
		}
		return flag;
	}
	
	//选择主菜单的checkbox事件
	@Command
	public void menucheck(@BindingParam("tree") Tree tree){
		if (!menulist.isEmpty())
			menulist.clear();
		if(tree.getSelectedItem()!=null)
		{
			Set<Treeitem> i=tree.getSelectedItems();
			for(Treeitem t : i){
				t.setOpen(true);
				MenuListModel menumodel=new MenuListModel();
				String idstr=t.getId();
				int id = Integer.parseInt(idstr.substring(3));
				menumodel.setMeu_id(id);
				this.menulist.add(menumodel);
			}
		}
	}
	//分配主菜单的提交事件
	@Command
	public void menusummit(){
		if(rolId==0)
		{
			Messagebox.show("请选择操作角色");
		}
		else
		{
			//当选择的菜单不为空的时候就执行下面的代码，menulist中存着选择了的菜单的model
			if(!menulist.isEmpty())
			{
				String sql="";
				//把选择了的菜单的model一个一个取出来
				for(int i=0;i<menulist.size();i++)
				{
					if(sql=="")
					{
						sql=""+menulist.get(i).getMeu_id();
					}
					else
					{
						sql=sql+","+menulist.get(i).getMeu_id();
					}
					//把菜单id添加到关系表
					PublicDataService pubservice=new Menu_PubImpl(rolId, menulist.get(i).getMeu_id());
					pubservice.update();
			    }
				PublicDataService delpubservice=new Menu_PubImpl(sql,rolId);
				//删除除了选择了的菜单外的所有菜单
				delpubservice.delete();
				Messagebox.show("权限分配成功");
			}
			else
			{
				//如果没有攒竹菜单则把关系表该组的数据全部删除
				PublicDataService delpubservice=new Menu_PubImpl("all",rolId);
				delpubservice.delete();
				Messagebox.show("权限分配成功");
			}
		}
	}
	
	
	//选择业务中心菜单的checkbox事件
	@Command
	public void embumenucheck(@BindingParam("tree") Tree tree){
		if (!embulist.isEmpty())
			embulist.clear();
		if(tree.getSelectedItem()!=null)
		{
			Set<Treeitem> i=tree.getSelectedItems();
			for(Treeitem t : i){
				t.setOpen(true);
				EmbaseBusinessCenterModel embumodel=new EmbaseBusinessCenterModel();
				String idstr=t.getId();
				int id = Integer.parseInt(idstr.substring(2));
				embumodel.setEmce_id(id);
				this.embulist.add(embumodel);
			}
		}
	}
	
	//分配业务中心菜单的提交事件
	@Command
	public void embusummit(){
		EmBuCenter_OperateBll bl=new EmBuCenter_OperateBll();
		if(rolId==0)
		{
			Messagebox.show("请选择操作角色");
		}
		else
		{
			//当选择的菜单不为空的时候就执行下面的代码，menulist中存着选择了的菜单的model
			if(!embulist.isEmpty())
			{
				Integer k=0;
				String sql="";
				//把选择了的菜单的model一个一个取出来
				for(int i=0;i<embulist.size();i++)
				{
					if(sql=="")
					{
						sql=""+embulist.get(i).getEmce_id();
					}
					else
					{
						sql=sql+","+embulist.get(i).getEmce_id();
					}
					//把菜单id添加到关系表
					k=k+bl.updateEmbuMenuPub(rolId, embulist.get(i).getEmce_id());
				}
				if(k>0)
				{
					bl.deleteEmbuMenuPub(sql, rolId);
					Messagebox.show("权限分配成功");
				}
				else
				{
					Messagebox.show("权限分配失败","提示",Messagebox.OK,Messagebox.ERROR);
				}
			}
			else
			{
				//如果没有选择菜单则把关系表该组的数据全部删除
				bl.deleteEmbuMenuPub("all", rolId);
				Messagebox.show("权限分配成功");
			}
		}
	}
	
	//定义生成树子节点的方法
	@Command
	public void addTree(@BindingParam("tree") Tree tree) {
		// 调用DAL层中treeDal类的getTreeData()方法查询树最顶层的菜单
		List<MenuListModel> list=null;
		MenuListBll bllsa = new MenuListBll();
		list = bllsa.getMenuListInfo();
		// 生成一个树的子节点
		Treechildren trc = new Treechildren();
		// 把生成的树的子节点添加到最原始的树上
		trc.setParent(tree);
		// 先判断list是否为空
		if (list.size() > 0) {
			// 循环获取List中的值
			for (int i = 0; i < list.size(); i++) {
				if(list.get(i).getMeu_pid()==0)
				{
					// 获取list中的一组数据
					MenuListModel model = (MenuListModel) list.get(i);
					// 生成一个tree子节点newitem
					Treeitem newitem = new Treeitem();
					// 给newitem设置id
					newitem.setId("tid" + model.getMeu_id());
					// 设置newitem的默认打开方式是false，也就是树默认是不展开的
					newitem.setOpen(false);
					newitem.setLabel(model.getMeu_name().trim());
					// 把newitem添加到节点trc中,作为trc的一个子节点
					newitem.setParent(trc);
					// 调用AddTreeItem方法
					AddTreeItem(newitem, model.getMeu_id(),list);
				}
			}
		}
	}

	// 定义递归函数AddTreeItem()
	private void AddTreeItem(Treeitem tem, int parentID,List<MenuListModel> list) {
		// 生成一个子节点
		Treechildren newchild = new Treechildren();
		for (int j = 0; j < list.size(); j++) {
			// 重新获取palist
			//palist= menubll.getMenuParentInfo(parentID);
			MenuListModel pmodel = (MenuListModel) list.get(j);
			if(pmodel.getMeu_pid()==parentID)
			{
				Treeitem item = new Treeitem();
				item.setId("tid" + pmodel.getMeu_id());
				item.setOpen(false);
				item.setParent(newchild);
				newchild.setParent(tem);
				Treerow tw = new Treerow();
				Treecell tl = new Treecell();
				tl.setLabel(pmodel.getMeu_name());
				tl.setParent(tw);
				tw.setParent(item);
				//使用递归方法循环生成树节点菜单
				this.AddTreeItem(item, pmodel.getMeu_id(),list);
			}
		}
	}
	
	//生成业务中心菜单的树
	@Command
	public void oncreatetree(@BindingParam("tree") Tree tree)
	{
		EmBuCenter_SelectBll bll=new EmBuCenter_SelectBll();
		String strs=" and emce_must<>0";
		
		List<EmbaseBusinessCenterModel> pubtreelist=bll.getEmbaseBusinessCenterInfo(strs);
		if(!pubtreelist.isEmpty())
		{
			Treechildren trc = new Treechildren();
			trc.setParent(tree);
			for(int y=0;y<pubtreelist.size();y++)
			{
				EmbaseBusinessCenterModel model=pubtreelist.get(y);
				if(model.getEmce_pid()==0)
				{
					// 生成一个树的子节点
					Treeitem newitem = new Treeitem();
					newitem.setParent(trc);
					newitem.setOpen(false);
					newitem.setLabel(model.getEmce_menuname());
					newitem.setId("id"+model.getEmce_id());
					newitem.setValue(model.getEmce_menuurl());
					//newitem.addEventListener("onClose", new MyListener());
					//调用递归函数
					embuAddTreeItem(newitem,model.getEmce_id(),pubtreelist);
				}
			}
		}
	}
	
	// 定义递归函数AddTreeItem()
	private void embuAddTreeItem(Treeitem ptem, int parentID,List<EmbaseBusinessCenterModel> list) {
		Treechildren newchild = new Treechildren();
		int h=0;
		for(int i=0;i<list.size();i++)
		{
			EmbaseBusinessCenterModel model=list.get(i);
			if(model.getEmce_pid()==parentID)
			{
				newchild.setParent(ptem);
				Treeitem item = new Treeitem();
				item.setOpen(false);
				item.setParent(newchild);
				item.setId("id"+model.getEmce_id());
				item.setValue(model.getEmce_menuurl());
				Treerow tw = new Treerow();
				Treecell tl = new Treecell();
				tl.setLabel(model.getEmce_menuname());
				tl.setParent(tw);
				tw.setParent(item);
				//函数递归
				embuAddTreeItem(item,model.getEmce_id(),list);
			}
		}
	}
	
	//业务中心菜单的选择事件
	@Command
	public void onselecttree(@BindingParam("item") Treeitem item)
	{
		if(item!=null)
		{
			if (item.isOpen()) {
				item.setOpen(false);
			} else {
				item.setOpen(true);
			}
		}
	}
	
	//主菜单全选
	@Command
	public void checkallmenu(@BindingParam("tree") Tree tree,@BindingParam("ck") Checkbox ck)
	{
		if (!menulist.isEmpty())
			menulist.clear();
		//得到主菜单的树所有的节点
		Collection<Treeitem> items=tree.getItems();
		for(Treeitem itemed: items)
		{
			itemed.setSelected(ck.isChecked());
			itemed.setOpen(true);
			if(itemed.isSelected())
			{
				MenuListModel menumodel=new MenuListModel();
				String idstr=itemed.getId();
				int id = Integer.parseInt(idstr.substring(3));
				menumodel.setMeu_id(id);
				this.menulist.add(menumodel);
			}
		}
	}
	
	//业务中心菜单全选
	@Command
	public void checkallmenubu(@BindingParam("tree") Tree tree,@BindingParam("ck") Checkbox ck)
	{
		if (!embulist.isEmpty())
			embulist.clear();
		//得到主菜单的树所有的节点
		Collection<Treeitem> items=tree.getItems();
		for(Treeitem itemed: items)
		{		
			itemed.setSelected(ck.isChecked());
			itemed.setOpen(true);
			if(itemed.isSelected())
			{
				EmbaseBusinessCenterModel embumodel=new EmbaseBusinessCenterModel();
				String idstr=itemed.getId();
				int id = Integer.parseInt(idstr.substring(2));
				embumodel.setEmce_id(id);
				this.embulist.add(embumodel);
			}
		}
	}
	
	public List<RoleModel> getRolemodel() {
		return rolemodel;
	}
}
