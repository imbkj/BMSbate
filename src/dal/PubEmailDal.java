package dal;

import java.sql.CallableStatement;
import java.sql.Types;
import java.util.Date;

import Conn.dbconn;
import Model.PubEmailModel;

public class PubEmailDal {
	
	/** 
	* @Title: EmailAdd 
	* @Description: TODO(发送邮件) 
	* @param md
	* @return
	* @return Integer    返回类型 
	* @throws 
	*/
	public Integer EmailAdd(PubEmailModel md){
		Integer i = 0;
		dbconn db = new dbconn();
		
		try {
			
			CallableStatement c=db.getcall(" PubEmailAdd_P_ply(?,?,?,?,?,?,?,?)");
			
			c.setString(1, md.getPuem_title());
			c.setString(2, md.getPuem_content());
			c.setString(3, md.getPuem_email());
			c.setString(4, md.getPuem_replyto());
			c.setString(5, md.getPuem_emailcc());
			c.setString(6, md.getPuem_sendtime());
			c.setString(7, md.getPuem_addname());
			c.setInt(8, md.getPuem_ifHTML());
			
			c.execute();
			return 1;
	
		} catch (Exception e) {
			e.printStackTrace();
			return 0;
		}
	
	}
	
	public Integer EmailAdd(int gid,String titel, String content,String username, int ifhtml,int esda_id,String email){
		Integer i = 0;
		dbconn db = new dbconn();
		
		try {
		
			CallableStatement c=db.getcall(" PubsendEail_P_zmj(?,?,?,?,?,?,?,?)");
			c.setInt(1, gid);
			c.setString(2, titel);
			c.setString(3, content);
			c.setString(4, username);
			c.setInt(5, ifhtml);
			c.setInt(6, esda_id);
			c.setString(7, email);
			
			c.registerOutParameter(8, java.sql.Types.INTEGER);
			c.execute();
			return c.getInt(8);
	
		} catch (Exception e) {
			e.printStackTrace();
			return 0;
		}

	}
		
}
