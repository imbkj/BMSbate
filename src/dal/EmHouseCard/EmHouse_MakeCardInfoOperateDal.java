package dal.EmHouseCard;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import Conn.dbconn;
import Model.EmHouseMakeCardInfoModel;
import Model.emhouseMakeCardBackInfoModel;
import Util.UserInfo;

public class EmHouse_MakeCardInfoOperateDal {
	//新增公积金领卡信息
	public Integer HouseMakecardinfoAdd(EmHouseMakeCardInfoModel m) {
		dbconn db = new dbconn();
		try {
			CallableStatement c = db
				.getcall("Make_GjjCard_cyj(?,?,?,?,?,?,?,?,?,?,?)");
			c.setInt(1, m.getGid());
			c.setString(2, m.getUsername());
			c.setString(3, m.getGjj_cno());
			c.setString(4, m.getGjj_no());
			c.setString(5, m.getGjj_insertblank());
			c.setString(6, m.getGjj_case());
			c.setString(7, m.getAddtime());
			c.setString(8, m.getAddtime());
			c.setString(9, m.getGjj_addname());
			c.setInt(10, m.getOwnmonth());
			c.registerOutParameter(11, java.sql.Types.INTEGER);
			c.execute();
			return c.getInt(11);
		} catch (SQLException e) {
			System.out.println("数据库操作错误："+e.getMessage());
			return 0;
		}
	}

	//更新领卡信息表任务单id
	public boolean updateTakeCardTaprid( int taprid,int id) {
		dbconn db = new dbconn();
		PreparedStatement psmt = null;
		int row = 0;
		String sql = "update EmHouseMakeCardInfo set tarpid=? where id=?";
		try {
				psmt = db.getpre(sql);
				psmt.setInt(1, taprid);
				psmt.setInt(2, id);
				row = psmt.executeUpdate();
			} catch (Exception e) {
				// TODO: handle exception
		}
		return row > 0 ? true : false;
	}
	
	//更新制卡信息表任务单id
	public int updateMakeCardInfo(int id,String str) {
		dbconn db = new dbconn();
		PreparedStatement psmt = null;
		int row = 0;
		String sql = "update EmHouseMakeCardInfo set modname='"+UserInfo.getUsername()+"'"+str+" where Id="+id;
		try {
			row=db.execQuery(sql);
		} catch (Exception e) {
			// TODO: handle exception
		}
		return row;
	}
	
	//退回公积金制卡信息
	public Integer backMakeCardInfo(emhouseMakeCardBackInfoModel m)
	{
		dbconn db = new dbconn();
		try {
			CallableStatement c = db
				.getcall("HuCard_BackMakeCard_cyj(?,?,?,?)");
			c.setInt(1, m.getBack_cardid());
			c.setString(2, UserInfo.getUsername());
			c.setString(3, m.getBack_case());
			c.registerOutParameter(4, java.sql.Types.INTEGER);
			c.execute();
			return c.getInt(4);
		} catch (SQLException e) {
			System.out.println("数据库操作错误："+e.getMessage());
			return 0;
		}
	}
}
