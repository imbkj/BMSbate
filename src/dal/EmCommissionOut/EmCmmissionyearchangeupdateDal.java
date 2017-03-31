package dal.EmCommissionOut;

import java.sql.PreparedStatement;

import Conn.dbconn;

public class EmCmmissionyearchangeupdateDal {


	dbconn db = new dbconn();
	
	
	public int updateyeardata(String sqlstr)
	{
		
		Integer row = 0;
	//	System.out.println(sqlstr);
		try {
			row=db.execQuery(sqlstr);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return row;
	}
}
