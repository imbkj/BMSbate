package dal.EmHouseCard;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import Conn.dbconn;
import Model.EmHouseCardFailInfoModel;
import Model.EmHouseTakeCardInfoModel;
import Util.UserInfo;

public class EmHouse_TakeCardInfoOperateDal {
	//新增公积金领卡信息
	public Integer HouseTakecardinfoAdd(EmHouseTakeCardInfoModel m) {
		dbconn db = new dbconn();
		try {
			CallableStatement c = db
				.getcall("HuCard_TakeCardAdd_cyj(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)");
			c.setInt(1, m.getGid());
			c.setInt(2, m.getCid());
			c.setString(3, m.getUsername());
			c.setString(4, m.getCname());
			c.setString(5, m.getCoba_client());
			c.setString(6, m.getRe_apptime());
			c.setString(7, m.getIdcard());
			c.setString(8, m.getRe_accounttype());
			c.setString(9, m.getRe_type());
			c.setString(10, m.getRe_gjjno());
			c.setString(11, m.getRe_cardid());
			c.setString(12, m.getRe_applycase());
			c.setString(13, m.getRe_cardstate());
			c.setString(14,m.getRe_band());
			c.setString(15,m.getRe_cgjjno());
			c.setString(16,m.getRe_failcase());
			c.setInt(17,m.getRe_state());
			c.setString(18,m.getCoba_shortname());
			c.setString(19,m.getAddtime());
			c.setString(20,m.getOwnmonth());
			c.setString(21,m.getAddname());
			c.registerOutParameter(22, java.sql.Types.INTEGER);
			c.execute();
			return c.getInt(22);
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
		String sql = "update EmHouseTakeCardInfo set Re_taprid=? where Re_Id=?";
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
	
	//更新领卡信息表任务单id
	public int updateTakeCardInfo(int id,String str) {
		dbconn db = new dbconn();
		int row = 0;
		String sql = "update EmHouseTakeCardInfo set modname='"+UserInfo.getUsername()+"'"+str+" where Re_Id="+id;
		try {
			row=db.execQuery(sql);
		} catch (Exception e) {
			// TODO: handle exception
		}
		return row;
	}
	
	
	//退回公积金领卡信息
	public Integer HouseTakecardinfoBack(EmHouseTakeCardInfoModel m) {
		dbconn db = new dbconn();
		try {
			CallableStatement c = db
				.getcall("HuCard_TakeCardBack_cyj(?,?,?,?)");
			c.setInt(1, m.getRe_id());
			c.setString(2, m.getRe_failcase());
			c.setString(3, m.getAddname());
			c.registerOutParameter(4, java.sql.Types.INTEGER);
			c.execute();
			return c.getInt(4);
		} catch (SQLException e) {
			System.out.println("数据库操作错误："+e.getMessage());
			return 0;
		}
	}
	
	//添加备注
	public Integer addremark(EmHouseCardFailInfoModel m)
	{
		Integer k=0;
		try {
			dbconn db = new dbconn();
			String sql="insert into EmHouseCardFailInfo(fail_reid,fail_content,fail_addname)" +
				" values("+m.getFail_reid()+",'"+m.getFail_content()+"','"+m.getFail_addname()+"')";
			k=db.execQuery(sql);		
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		return k;
	}
}
