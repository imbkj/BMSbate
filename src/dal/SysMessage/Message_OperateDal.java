package dal.SysMessage;

import java.sql.CallableStatement;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Date;

import Conn.dbconn;
import Model.SysEmailFileModel;
import Model.SysEmailModel;
import Model.SysMessageModel;
import Util.UserInfo;

public class Message_OperateDal {
	
	public Integer SysMessageAdd(SysMessageModel model)
			throws SQLException {
		CallableStatement csmt = null;
		dbconn db = new dbconn();
		// 创建存储过程的对象
		csmt = db.getcall("MessageAdd_P_cyj(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)");
		Integer k=0;
		try {
			// 给存储过程的参数设置值
			csmt.setString(1, model.getSyme_content());
			csmt.setString(2, model.getSyme_addname());
			csmt.setInt(3, 1);
			csmt.setInt(4, model.getSyme_log_id());//发件人
			csmt.setInt(5, model.getSyme_reply_id());
			csmt.setString(6,model.getSyme_title());
			csmt.setString(7, model.getSyme_url());
			csmt.setString(8, model.getSyme_para());
			csmt.setString(9, model.getSymr_name());
			csmt.setString(10, model.getSmwr_type());
			csmt.setInt(11,0);
			csmt.setInt(12,0);
			csmt.setString(13, model.getSmwr_table());
			csmt.setInt(14, model.getSmwr_tid());
			csmt.setInt(15, model.getSymr_log_id());//收件人
			csmt.setInt(16, model.getEmail());
			csmt.setString(17, model.getEmailtitle());
			// 注册存储过程的返回值
			csmt.registerOutParameter(18, java.sql.Types.INTEGER);

			// 执行存储过程
			csmt.execute();

			// 获取返回值
			k=csmt.getInt(18);

		} catch (Exception e) {
			System.out.print(e.toString());
		} finally {
			db.Close();
		}
		return k;
	}
	
	//回复短信
	public Integer SysMessageReply(SysMessageModel model)
			throws SQLException {
		CallableStatement csmt = null;
		dbconn db = new dbconn();
		// 创建存储过程的对象
		csmt = db.getcall("Messagereply_P_cyj(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)");
		Integer k=0;
		try {
			// 给存储过程的参数设置值
			csmt.setString(1, model.getSyme_content());
			csmt.setString(2, model.getSyme_addname());
			csmt.setInt(3, 1);
			csmt.setInt(4, model.getSyme_log_id());
			csmt.setInt(5, model.getSyme_reply_id());
			csmt.setString(6,model.getSyme_title());
			csmt.setString(7, model.getSyme_url());
			csmt.setString(8, model.getSyme_para());
			csmt.setString(9, model.getSymr_name());
			csmt.setString(10, model.getSmwr_type());
			csmt.setInt(11,0);
			csmt.setInt(12,0);
			csmt.setString(13, model.getSmwr_table());
			csmt.setInt(14, model.getSmwr_tid());
			csmt.setInt(15, model.getSymr_log_id());
			csmt.setInt(16, model.getEmail());
			csmt.setString(17, model.getEmailtitle());
			csmt.setInt(18, model.getSyme_id());
			// 注册存储过程的返回值
			csmt.registerOutParameter(19, java.sql.Types.INTEGER);

			// 执行存储过程
			csmt.execute();

			// 获取返回值
			k=csmt.getInt(19);

		} catch (Exception e) {
			System.out.print(e.toString());
		} finally {
			db.Close();
		}
		return k;
	}
	
	//把短信阅读状态改为已读
	public Integer updateMessage(String smwr_table,Integer smwr_tid)
	{
		Integer k=0;
		String sql=" update SysMessageRecipient set symr_readstate=1," +
				"symr_readtime='"+DateToStr()+"' where symr_id in( select symr_id " +
				"from View_Message where smwr_table='"+smwr_table+"' and symr_readstate=0 " +
				" and smwr_tid="+smwr_tid+" and symr_log_id="+UserInfo.getUserid()+")";
		dbconn db=new dbconn();
		try{
			k=db.execQuery(sql);
		}catch(Exception e)
		{
			
		}
		return k;
	}
	
	//把短信阅读状态改为已读
	public Integer updateMessageRead(Integer symr_tid)
	{
		Integer k=0;
		String sql=" update SysMessageRecipient set symr_readstate=1," +
			" symr_readtime='"+DateToStr()+"' where symr_id =" +symr_tid +
			" and symr_log_id="+UserInfo.getUserid()+" and symr_readstate=0";
		dbconn db=new dbconn();
		try{
			k=db.execQuery(sql);
		}catch(Exception e)
		{
				
		}
		return k;
	}
	
	//添加系统发送邮件的记录
	public int addSysEmail(SysEmailModel m)
	{
		int k=0;
		CallableStatement csmt = null;
		dbconn db = new dbconn();
		// 创建存储过程的对象
		csmt = db.getcall("sysEmail_add_p_cyj(?,?,?,?,?,?)");
		try {
			// 给存储过程的参数设置值
			csmt.setString(1, m.getPfil_title());
			csmt.setString(2,m.getPfil_title());
			csmt.setString(3, m.getPfil_addname());
			csmt.setString(4, m.getPfil_email());
			csmt.setString(5, m.getPfil_remark());
			// 注册存储过程的返回值
			csmt.registerOutParameter(6, java.sql.Types.INTEGER);

			// 执行存储过程
			csmt.execute();

			// 获取返回值
			k=csmt.getInt(6);

		} catch (Exception e) {
			System.out.print(e.toString());
		} finally {
			
		}
		return k;
	}
	
	public int addSysEmailFile(SysEmailFileModel m)
	{
		int k=0;
		StringBuffer sql=new StringBuffer();
		sql.append("insert into SysEmailFile(file_pfil_id, file_title, file_url, file_addname)");
		sql.append(" values(");
		sql.append(m.getFile_pfil_id());
		sql.append(",");
		sql.append("'");
		sql.append(m.getFile_title());
		sql.append("'");
		sql.append(",'");
		sql.append(m.getFile_url());
		sql.append("'");
		sql.append(",'");
		sql.append(m.getFile_addname());
		sql.append("')");
		dbconn db=new dbconn();
		k=db.execQuery(sql.toString());
		return k;
	}
	
	/**
	* 日期转换成字符串
	* @param date 
	* @return str
	*/
	public static String DateToStr() {
		Date date=new Date();
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String str = format.format(date);
		return str;
	} 
}
