package dal.SystemControl;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import Conn.dbconn;
import Model.MenuListModel;

public class MenuList_UpdateDal {
	//修改菜单表menulist的一条数据，并返回一个Int类型的数
	public int updateMenuList(MenuListModel model){
		int k=0;
		try {
			String sql="";
			dbconn db = new dbconn();
			sql="update menulist set meu_name='"+model.getMeu_name()+"',meu_url='"+model.getMeu_url()+"',";
			sql=sql+"meu_remark='"+model.getMeu_remark()+"',meu_pid="+model.getMeu_pid()+",meu_orderid="+model.getMeu_orderid()+" where meu_id="+model.getMeu_id();
			k=db.execQuery(sql);		
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return k;
	}
	
	//修改菜单表menulist的一条数据，并返回一个Int类型的数
	public MenuListModel selectMenuList(MenuListModel model){
		MenuListModel mmodel=new MenuListModel();
		
		ResultSet rs = null;
		try {
			dbconn db = new dbconn();
			String sqlstr = "select * from menulist where meu_id="+model.getMeu_pid();
			rs = db.GRS(sqlstr);
			while (rs.next()) {
				mmodel.setMeu_pid(rs.getInt("meu_pid"));
				mmodel.setMeu_id(rs.getInt("meu_id"));
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return mmodel;
	}
}
