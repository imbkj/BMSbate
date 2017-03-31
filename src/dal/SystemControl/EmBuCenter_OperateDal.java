package dal.SystemControl;

import impl.UserInfoImpl;

import java.sql.ResultSet;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.Session;

import service.UserInfoService;
import Conn.dbconn;

public class EmBuCenter_OperateDal {
	//更新业务菜单权限
	public int updateEmbuMenuPub(int rol_id,int meu_id)
	{
		int k=0;
		ResultSet rs = null;
		dbconn db = new dbconn();
		String sqlstr = "select * from EmbuGroupRel where rol_id="+rol_id+" and embu_id="+meu_id;
		try {
			rs = db.GRS(sqlstr);
			if(!rs.next())
			{
				Session session =  Executions.getCurrent().getDesktop().getSession();
				UserInfoService uservice=new UserInfoImpl(session);
				String username=uservice.getUsername();
				Date currentTime = new Date();
				SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
				String meustr="insert into EmbuGroupRel(rol_id,embu_id,addname,addtime) values("+rol_id+","+meu_id+",'"+username+"','"+formatter.format(currentTime)+"')";
				k=db.execQuery(meustr);
			}
			else
			{
				k=1;
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return k;
	}
	
	//删除菜单权限
	public int deleteEmbuMenuPub(String str,int rol_id)
	{
		int k=0;
		dbconn db = new dbconn();
		String sqlstr="";
		if(str=="all"||str.equals("all"))
		{
			sqlstr = "delete from EmbuGroupRel where rol_id="+rol_id;
		}
		else
		{
			sqlstr = "delete from EmbuGroupRel where rol_id="+rol_id+" and embu_id not in("+str+")";
		}
		k=db.execQuery(sqlstr);
		return k;
	}

}
