package dal.EmExpress;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import Conn.dbconn;
import Model.EmExpressContactInfoModel;
import Model.EmExpressInfoModel;
import Util.UserInfo;

public class EmExpressInfoOperateDal {
	//新增个人快递信息
	public Integer EmExpressInfoAdd(EmExpressInfoModel m) {
		dbconn db = new dbconn();
		try {
			CallableStatement c = db
				.getcall("EmExpressInfo_Add_P_cyj(?,?,?,?,?,?)");
			c.setInt(1, m.getExpr_exct_id());
			c.setInt(2, m.getGid());
			c.setString(3, m.getExpr_content());
			c.setString(4, m.getExpr_rank());
			c.setString(5, UserInfo.getUsername());
			c.registerOutParameter(6, java.sql.Types.INTEGER);
			c.execute();
			return c.getInt(6);
		} catch (SQLException e) {
			System.out.println("数据库操作错误："+e.getMessage());
			return 0;
		}
	}
	
	//新增公司快递信息
	public Integer EmExpressInfoCobaseAdd(EmExpressInfoModel m) {
		dbconn db = new dbconn();
		try {
			CallableStatement c = db
				.getcall("EmExpressInfo_Cobase_Add_P_cyj(?,?,?,?,?,?)");
			c.setInt(1, m.getExpr_exct_id());
			c.setInt(2, m.getCid());
			c.setString(3, m.getExpr_content());
			c.setString(4, m.getExpr_rank());
			c.setString(5, UserInfo.getUsername());
			c.registerOutParameter(6, java.sql.Types.INTEGER);
			c.execute();
			return c.getInt(6);
		} catch (SQLException e) {
			System.out.println("数据库操作错误："+e.getMessage());
			return 0;
		}
	}
	
	//修改快递信息
	public Integer EmExpressInfoupdate(EmExpressInfoModel m) {
		dbconn db = new dbconn();
		try {
			CallableStatement c = db
				.getcall("EmExpressInfo_update_P_cyj(?,?,?,?,?,?,?)");
			c.setInt(1, m.getExpr_exct_id());
			c.setString(2, m.getExpr_content());
			c.setString(3, m.getExpr_rank());
			c.setString(4, m.getExpr_class());
			c.setString(5, UserInfo.getUsername());
			c.setInt(6, m.getExpr_id());
			c.registerOutParameter(7, java.sql.Types.INTEGER);
			c.execute();
			return c.getInt(7);
		} catch (SQLException e) {
			System.out.println("数据库操作错误："+e.getMessage());
			return 0;
		}
	}
	
	//新增快递收件地址信息
	public Integer EmExpressContactInfoAdd(EmExpressContactInfoModel m) {
		dbconn db = new dbconn();
		try {
			CallableStatement c = db
				.getcall("EmExpressContactInfo_Add_P_cyj(?,?,?,?,?,?,?,?,?,?)");
			c.setInt(1, m.getGid());
			c.setInt(2, m.getCid());
			c.setString(3, m.getExct_type());
			c.setString(4, m.getExct_receivename());
			c.setString(5, m.getExct_address());
			c.setString(6, m.getExct_code());
			c.setString(7, m.getExct_mobile());
			c.setString(8,UserInfo.getUsername());
			c.setString(9, m.getExct_phone());
			c.registerOutParameter(10, java.sql.Types.INTEGER);
			c.execute();
			return c.getInt(10);
		} catch (SQLException e) {
			System.out.println("数据库操作错误："+e.getMessage());
			return 0;
		}
	}
	
	//修改快递收件地址信息
	public Integer EmExpressContactInfoEdit(EmExpressContactInfoModel m) {
		dbconn db = new dbconn();
		try {
			CallableStatement c = db
			.getcall("EmExpressContactInfo_Edit_P_cyj(?,?,?,?,?,?,?,?)");
			c.setInt(1, m.getExct_id());
			c.setString(2, m.getExct_type());
			c.setString(3, m.getExct_receivename());
			c.setString(4, m.getExct_address());
			c.setString(5, m.getExct_code());
			c.setString(6, m.getExct_mobile());
			c.setString(7, m.getExct_phone());
			c.registerOutParameter(8, java.sql.Types.INTEGER);
			c.execute();
			return c.getInt(8);
		} catch (SQLException e) {
			System.out.println("数据库操作错误："+e.getMessage());
			return 0;
		}
	}
	
	//新增公司快递收件地址信息
	public Integer EmExpressContactInfoCobaseAdd(EmExpressContactInfoModel m) {
		dbconn db = new dbconn();
		try {
			CallableStatement c = db
				.getcall("EmExpressContactInfo_Cobase_Add_P_cyj(?,?,?,?,?,?,?,?,?)");
			c.setInt(1, m.getCid());
			c.setString(2, m.getExct_type());
			c.setString(3, m.getExct_receivename());
			c.setString(4, m.getExct_address());
			c.setString(5, m.getExct_code());
			c.setString(6, m.getExct_mobile());
			c.setString(7,UserInfo.getUsername());
			c.setString(8, m.getExct_phone());
			c.registerOutParameter(9, java.sql.Types.INTEGER);
			c.execute();
			return c.getInt(9);
		} catch (SQLException e) {
			System.out.println("数据库操作错误："+e.getMessage());
			return 0;
		}
	}
	
	///更新档案业务表的任务单id
	public boolean updateTaprid(int id, int taprid) {
		dbconn db = new dbconn();
		PreparedStatement psmt = null;
		int row = 0;
		String sql = "update EmExpressInfo set expr_tarpid=? where expr_id=?";
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
	
	//修改快递信息
	public Integer updateExpressInfo(String sql,Integer id)
	{
		Integer k=0;
		dbconn db=new dbconn();
		sql="update EmExpressInfo set expr_modname='"+UserInfo.getUsername()+"'"+sql+" where expr_id="+id;
		try {
			k=db.execQuery(sql);
		} catch (Exception e) {
			// TODO: handle exception
		}
		return k;
	}
}
