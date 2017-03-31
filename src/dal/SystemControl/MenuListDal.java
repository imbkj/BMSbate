package dal.SystemControl;

import impl.UserInfoImpl;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.Session;

import service.UserInfoService;

import Conn.dbconn;
import Model.FixedTabMenuListModel;
import Model.MenuListModel;
import Util.UserInfo;

public class MenuListDal {
	/*
	  Author:陈耀家
	  Create date: 09/06/2013
	  Description:只要对menulist进行操作
	*/
	//查询菜单表menulist并返回List<TreeModel>类型的变量
	public List<MenuListModel> getMenuData() {
		ResultSet rs = null;
		List<MenuListModel> menuinfo = new ArrayList<MenuListModel>();
		if (!menuinfo.isEmpty())
			menuinfo.clear();
		try {
			boolean flag=isManager();
			String str="";
			if(flag==false)
			{
				str=" and meu_id in(select meu_id from menugroup where rol_id in(select rol_id " +
						"from logingroup a,Login b where a.log_id=b.log_id" +
				   		" and log_name='"+UserInfo.getUsername()+"')) ";
			}
			dbconn db = new dbconn();
			//String sqlstr = "select * from menulist where menu_state=1 order by meu_orderid";
			String sqlstr="select * from menuList where menu_state=1 "+str+" order by meu_orderid";
			/*String sqlstr="select * from menuList where meu_id in(select meu_id from menugroup ";
			   sqlstr=sqlstr+"where rol_id="+UserInfo.getUserid()+")";*/
			rs = db.GRS(sqlstr);
			while (rs.next()) {
				MenuListModel model=new MenuListModel();
				model.setMeu_id(rs.getInt("meu_id"));
				model.setMeu_name(rs.getString("meu_name"));
				model.setMeu_url(rs.getString("meu_url"));
				model.setMeu_pid(rs.getInt("meu_pid"));
				model.setMeu_imgurl(rs.getString("meu_imgurl"));
				model.setMeu_remark(rs.getString("meu_Remark"));
				model.setMeu_orderid(rs.getInt("meu_orderid"));
				model.setMeu_target(rs.getString("menu_target"));
				model.setMenu_flag(rs.getInt("menu_flag"));
				menuinfo.add(model);
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return menuinfo;
	}
	
	//查询菜单表menulist并返回List<TreeModel>类型的变量
	public List<MenuListModel> getMenuinfolist() {
		ResultSet rs = null;
		List<MenuListModel> menuinfo = new ArrayList<MenuListModel>();
		if (!menuinfo.isEmpty())
			menuinfo.clear();
		try {
			dbconn db = new dbconn();
			
			String sqlstr = "select * from menulist where menu_state=1  order by meu_orderid";
			//String sqlstr="select * from menuList where meu_id in(select meu_id from menugroup ";
			//	   sqlstr=sqlstr+" where rol_id=(select a.rol_id from logingroup a,Login b where a.log_id=b.log_id and log_name='陈耀家'))";
			rs = db.GRS(sqlstr);
			while (rs.next()) {
				menuinfo.add(new MenuListModel(rs.getInt("meu_id"), rs
					.getString("meu_name"),
					rs.getString("meu_url"), rs.getInt("meu_pid"),rs.getString("meu_imgurl"),rs.getString("meu_Remark"),
					rs.getInt("meu_orderid"),rs.getString("menu_target")));
				}
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return menuinfo;
		}
	
	//查询菜单表menulist同时查询每个菜单的父菜单名称并返回List<TreeModel>类型的变量
	public List<MenuListModel> getMenuInfo(String str) {
		ResultSet rs = null;
		List<MenuListModel> menuinfo = new ArrayList<MenuListModel>();
		if (!menuinfo.isEmpty())
			menuinfo.clear();
			try {
				boolean flag=isManager();
				if(flag==false)
				{
					str=str+" and a.meu_id in(select meu_id from menugroup where rol_id in(select rol_id " +
							"from logingroup a,Login b where a.log_id=b.log_id" +
					   		" and log_name='"+UserInfo.getUsername()+"')) ";
				}
				dbconn db = new dbconn();
//				String sql="select a.meu_id,a.meu_name,a.meu_imgurl,a.meu_url,a.meu_pid,a.meu_remark,a.meu_orderid,b.meu_name as bmena ";
//					sql=sql+"from menulist a,(select meu_id, meu_name from menulist) b where 1=1 "+str+" and a.meu_pid=b.meu_id order ";
//					sql=sql+"by meu_orderid";
				String sql="select a.meu_id,a.meu_name,a.meu_imgurl,a.meu_url,a.meu_pid," +
						" a.meu_remark,a.meu_orderid,b.meu_name as bmena"+
						" from menulist a left join (select meu_id, meu_name from menulist) b " +
						" on a.meu_pid=b.meu_id where 1=1 and menu_state=1 "+str+" order by meu_orderid";
				rs = db.GRS(sql);
				while (rs.next()) {
					MenuListModel model=new MenuListModel();
					model.setMeu_id(rs.getInt("meu_id"));
					model.setMeu_name(rs.getString("meu_name"));
					model.setMeu_url(rs.getString("meu_url"));
					model.setMeu_pid(rs.getInt("meu_pid"));
					model.setMeu_remark(rs.getString("meu_remark"));
					model.setMeu_orderid(rs.getInt("meu_orderid"));
					model.setMeu_parentname(rs.getString("bmena"));
					menuinfo.add(model);
				}
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return menuinfo;
		}

	//查询菜单表menulist的meu_pid=id的行并返回List<TreeModel>类型的变量(用于首页登录)
	public List<MenuListModel> getMenuParentInfo(int id) {
		ResultSet rs = null;
		List<MenuListModel> menupainfo = new ArrayList<MenuListModel>();
		if (!menupainfo.isEmpty())
			menupainfo.clear();
		try {
			Session session =  Executions.getCurrent().getDesktop().getSession();
			UserInfoService uservice=new UserInfoImpl(session);
			String username=uservice.getUsername();
			dbconn db = new dbconn();
			String sqlstr = "select * from menulist where meu_pid="+ id+" and menu_state=1  order by meu_orderid";
			//根据登录用户的菜单权限显示菜单
			/*String sqlstr = "select * from menulist where meu_id in(select meu_id from menugroup ";
				   sqlstr=sqlstr+"where rol_id=(select a.rol_id from logingroup a,Login b where a.log_id=b.log_id ";
				   sqlstr=sqlstr+"and log_name='"+username+"')) and meu_pid="+ id;*/
			rs = db.GRS(sqlstr);
			while (rs.next()) {
				menupainfo.add(new MenuListModel(rs.getInt("meu_id"), rs
						.getString("meu_name"),
						rs.getString("meu_url"), rs.getInt("meu_pid"),rs.getString("meu_imgurl"),rs.getString("meu_Remark"),
						rs.getInt("meu_orderid"),rs.getString("menu_target")));
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return menupainfo;
	}
	
	
	//查询菜单表menulist的meu_pid=id的行并返回List<TreeModel>类型的变量(用于权限分配)
		public List<MenuListModel> getMenuPInfo(int id) {
			ResultSet rs = null;
			List<MenuListModel> menupainfo = new ArrayList<MenuListModel>();
			if (!menupainfo.isEmpty())
				menupainfo.clear();
			try {
				dbconn db = new dbconn();
				//String sqlstr = "select * from menulist where meu_pid="+ id;
				String sqlstr = "select * from menulist where meu_pid="+ id +" and menu_state=1  order by meu_orderid";
				rs = db.GRS(sqlstr);
				while (rs.next()) {
					menupainfo.add(new MenuListModel(rs.getInt("meu_id"), rs
							.getString("meu_name"),
							rs.getString("meu_url"), rs.getInt("meu_pid"),rs.getString("meu_imgurl"),rs.getString("meu_Remark"),
							rs.getInt("meu_orderid"),rs.getString("menu_target")));
				}
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return menupainfo;
		}
	
	//查询菜单表menulist的meu_id=id的行并返回List<TreeModel>类型的变量
	public List<MenuListModel> getMenuId(int id) {
		ResultSet rs = null;
		List<MenuListModel> menuid = new ArrayList<MenuListModel>();
		if (!menuid.isEmpty())
			menuid.clear();
		try {
			dbconn db = new dbconn();
			String sqlstr = "select* from menulist where meu_id="+ id+" and menu_state=1 order by meu_orderid";
			rs = db.GRS(sqlstr);
			while (rs.next()) {
				menuid.add(new MenuListModel(rs.getInt("meu_id"), rs
						.getString("meu_name"),
						rs.getString("meu_url"), rs.getInt("meu_pid"),rs.getString("meu_imgurl"),rs.getString("meu_Remark"),
						rs.getInt("meu_orderid"),rs.getString("menu_target")));
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return menuid;
	}
	
	//对菜单表menulist插入一条数据，并返回一个Int类型的数
	public int addMenuList(MenuListModel model){
		int k=0;
		try {
			dbconn db = new dbconn();
			String sql = "insert into menulist(meu_name,meu_url,meu_pid,meu_orderid,meu_imgurl,meu_remark,menu_target) ";
				   sql=sql+" values('"+model.getMeu_name()+"','"+model.getMeu_url()+"',"+model.getMeu_pid()+","+model.getMeu_orderid();
				   sql=sql+",'"+model.getMeu_imgurl()+"','"+model.getMeu_remark()+"','"+model.getMeu_target()+"')";
				   k=db.execQuery(sql);
//			Integer menu_id=getMenuId();
//			String sql = "insert into menulist(meu_name,meu_url,meu_pid,meu_orderid,meu_imgurl,meu_remark,menu_target) ";
//			   sql=sql+" values('"+model.getMeu_name()+"','"+model.getMeu_url()+"',"+model.getMeu_pid()+","+model.getMeu_orderid();
//			   sql=sql+",'"+model.getMeu_imgurl()+"','"+model.getMeu_remark()+"','"+model.getMeu_target()+"')";
//			   k=db.execQuery(sql);
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return k;
	}
	
	//修改菜单表menulist的一条数据，并返回一个Int类型的数
	public int updateMenuList(MenuListModel model,int pid){
		int k=0;
		try {
			String sql="";
			dbconn db = new dbconn();
				sql="update menulist set meu_name='"+model.getMeu_name()+"',meu_url='"+model.getMeu_url()+"',";
				sql=sql+"meu_remark='"+model.getMeu_remark()+"',meu_pid="+pid+",meu_orderid="+model.getMeu_orderid()+" where meu_id="+model.getMeu_id();
				k=db.execQuery(sql);		
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return k;
		}
		
		//查询菜单表menulist的meu_id=id的行并返回List<TreeModel>类型的变量
		public List<MenuListModel> existMenuName(MenuListModel model) {
			ResultSet rs = null;
			List<MenuListModel> menuid = new ArrayList<MenuListModel>();
			if (!menuid.isEmpty())
				menuid.clear();
			try {
				dbconn db = new dbconn();
				String sqlstr = "select * from menulist where meu_name='"+model.getMeu_parentname()+"' and menu_state=1 ";
				rs = db.GRS(sqlstr);
				while (rs.next()) {
					menuid.add(new MenuListModel(rs.getInt("meu_id"), rs
							.getString("meu_name"),
							rs.getString("meu_url"), rs.getInt("meu_pid"),rs.getString("meu_imgurl"),rs.getString("meu_Remark"),
							rs.getInt("meu_orderid"),rs.getString("menu_target")));
				}
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return menuid;
		}
		
		//删除菜单表menulist的一条数据，并返回一个Int类型的数
		public int deleteMenuList(MenuListModel model){
			int k=0;
			try {
				dbconn db = new dbconn();
				String sql="delete from  menulist where meu_id="+model.getMeu_id();
					k=db.execQuery(sql);	
					} catch (Exception e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
			return k;
		}
		
		//删除菜单表menulist的一条数据，并返回一个Int类型的数
				public int deleteMenut(MenuListModel model){
					int k=0;
					try {
						dbconn db = new dbconn();
						String sql="update menulist set menu_state=0 where meu_id="+model.getMeu_id();
							k=db.execQuery(sql);	
							} catch (Exception e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
					return k;
				}
		
		//获取固定标签的信息
		public List<FixedTabMenuListModel> getFixedTabMenuList(String name) {
			ResultSet rs = null;
			List<FixedTabMenuListModel> tabmenuList = new ArrayList<FixedTabMenuListModel>();
			if (!tabmenuList.isEmpty())
				tabmenuList.clear();
			try {
				dbconn db = new dbconn();
				String sqlstr = "select * from FixedTabMenuList where username='"+name+"'";
				rs = db.GRS(sqlstr);
				while (rs.next()) {
					tabmenuList.add(new FixedTabMenuListModel(rs.getInt("id"), rs.getInt("menu_id"),
							rs.getString("username")));
				}
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return tabmenuList;
		}
		
		//添加固定标签的信息
		public int addFixedTabMenuList(String name,int id) {
			int k=0;
			try {
				dbconn db = new dbconn();
				String sql="insert into FixedTabMenuList(menu_id,username) values("+id+",'"+name+"')";
					k=db.execQuery(sql);	
					} catch (Exception e) {
						// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return k;
		}
		
		//查询某用户是否已存在固定标签
		public boolean ifExistFixedTabMenu(String name,Integer id) {
			boolean flag=false;
			ResultSet rs = null;
			try {
				dbconn db = new dbconn();
				String sqlstr = "select * from FixedTabMenuList where username='"+name+"' and menu_id="+id;
				rs = db.GRS(sqlstr);
				while (rs.next()) {
					flag=true;
					}
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				return flag;
			}
		
		//取消固定标签的信息
		public int deleteFixedTabMenuList(String name,int id) {
			int k=0;
			try {
				dbconn db = new dbconn();
				String sql="delete from FixedTabMenuList where menu_id="+id+" and username='"+name+"'";
					k=db.execQuery(sql);	
					} catch (Exception e) {
						// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return k;
		}
		
		//根据username和menu_id获取固定标签的信息
		public FixedTabMenuListModel getFixedTabMenuListid(String name,int id) {
			ResultSet rs = null;
			FixedTabMenuListModel tabmenuList = new FixedTabMenuListModel();
			try {
				dbconn db = new dbconn();
				String sqlstr = "select * from FixedTabMenuList where username='"+name+"' and menu_id="+id;
				rs = db.GRS(sqlstr);
				while (rs.next()) {
					tabmenuList.setId(rs.getInt("id"));
					tabmenuList.setMenu_id(rs.getInt("menu_id"));
					tabmenuList.setUsername(rs.getString("username"));
				}
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return tabmenuList;
		}
		
		//根据菜单id 查询菜单的
		public MenuListModel getMenuFlag(String id) {
			ResultSet rs = null;
			MenuListModel model = new MenuListModel();
			try {
				dbconn db = new dbconn();
				String sql="select * from menuList where meu_id="+id;
				rs = db.GRS(sql);
				while (rs.next()) {
					model.setMeu_id(rs.getInt("meu_id"));
					model.setMeu_name(rs.getString("meu_name"));
					model.setMeu_url(rs.getString("meu_url"));
					model.setMeu_pid(rs.getInt("meu_pid"));
					model.setMeu_imgurl(rs.getString("meu_imgurl"));
					model.setMeu_remark(rs.getString("meu_Remark"));
					model.setMeu_orderid(rs.getInt("meu_orderid"));
					model.setMeu_target(rs.getString("menu_target"));
					model.setMenu_flag(rs.getInt("menu_flag"));
				}
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return model;
		}
		
		// 查询是否是系统管理员
		private boolean isManager() {
			String sql = "select * from role a inner join logingroup b on a.rol_id=b.rol_id "
					+ "inner join Login c on b.log_id=c.log_id "
					+ "where log_name='"
					+ UserInfo.getUsername()
					+ "' and rol_name='系统管理员'";
			boolean flag = false;
			dbconn db = new dbconn();
			try {
				ResultSet rs = db.GRS(sql);
				while (rs.next()) {
					flag = true;
				}
			} catch (Exception e) {

			}
			return flag;
		}
		
		//查询最新的菜单id
		private Integer getMenuId()
		{
			Integer menu_id=0;
			String sql="select top 1 * from menuList where menu_state=1 order by meu_id  desc";
			dbconn db=new dbconn();
			try{
				ResultSet rs=db.GRS(sql);
				while(rs.next())
				{
					menu_id=rs.getInt("meu_id");
				}
			}catch(Exception e)
			{
				
			}
			return menu_id;
		}
		
		//根据用户名查询用户的首页
		public String getIndexSrc()
		{
			String serurl="main.zul";
			String sql="select top 1 * from role a inner join " +
					"logingroup b on a.rol_id=b.rol_id inner join Login c " +
					"on b.log_id=c.log_id where log_name='"+UserInfo.getUsername()+"'";
			dbconn db=new dbconn();
			try{
				ResultSet rs=db.GRS(sql);
				while(rs.next())
				{
					if(rs.getString("rol_index")!=null&&!rs.getString("rol_index").equals(""))
					{
						serurl=rs.getString("rol_index");
					}
				}
			}catch(Exception e)
			{
				
			}
			return serurl;
		}
}
