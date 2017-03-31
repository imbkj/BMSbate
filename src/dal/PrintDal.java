package dal;
import java.sql.ResultSet;

/**
 * @author 苏宏远
 * @create 2016-08-31
 * 打印查询数据链路层
 */
import Conn.dbconn;

public class PrintDal {
	private int a=0;
	public int ISExit(String sql){
		dbconn conn = new dbconn();
		try {
			 ResultSet rs=conn.GRS(sql);
			 if(rs.next()){
				a=1; 
			 }else{
				a=0;
			 }
		} catch (Exception e) {
			
			e.printStackTrace();
		}
		return a;
	}
}
