package dal.EmBenefit;

import java.math.BigDecimal;
import java.sql.CallableStatement;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.ParseException;
import java.text.SimpleDateFormat;

import Conn.dbconn;
import Model.EmActyContactContentInfoModel;
import Model.EmActyGiftBackInfoModel;
import Model.EmActyGiftOutInfoModel;
import Model.EmActySuppilerGiftInfoModel;
import Model.EmActyWarehouseHistoryModel;
import Model.EmActyWarehouseModel;
import Model.EmWelfareModel;
import Model.EmactyUseHouseLogModel;
import Util.UserInfo;

public class EmActy_GiftInfoOperateDal {
	// 新增礼品信息
	public Integer EmActy_GiftAdd(EmActySuppilerGiftInfoModel m) {
		dbconn db = new dbconn();
		try {
			CallableStatement c = db
					.getcall("EmActySuppilerGiftInfo_Add_cyj(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)");
			c.setString(1, m.getGift_name());
			c.setString(2, m.getGift_brand());
			c.setString(3, m.getSupp_name());
			c.setString(4, m.getGift_color());
			c.setString(5, m.getGift_production());
			c.setString(6, m.getGift_class());
			c.setBigDecimal(7, m.getGift_totalprice());
			c.setInt(8, m.getGift_totalnum());
			c.setString(9, m.getGift_addname());
			c.setString(10, m.getGift_remark());
			c.setString(11, m.getGift_inaddress());
			c.setBigDecimal(12, m.getGift_price());
			c.setBigDecimal(13, m.getGift_nowprice());
			c.setInt(14, m.getOwnmonth());
			c.registerOutParameter(15, java.sql.Types.INTEGER);
			c.execute();
			return c.getInt(15);
		} catch (SQLException e) {
			System.out.println("错误：" + e.toString());
			return 0;
		}
	}

	// 新增礼品信息(新修改)
	public Integer EmActy_GiftInfoAdd(EmActySuppilerGiftInfoModel m) {
		dbconn db = new dbconn();
		try {
			CallableStatement c = db
					.getcall("EmActyGiftInfo_Add_cyj(?,?,?,?,?,?,?,?,?,?,?,?,?,?)");
			c.setString(1, m.getGift_name());
			c.setString(2, m.getSupp_name());
			c.setBigDecimal(3, m.getGift_totalprice());
			c.setInt(4, m.getGift_totalnum());
			c.setString(5, m.getGift_addname());
			c.setString(6, m.getGift_remark());
			c.setString(7, m.getGift_inaddress());
			c.setBigDecimal(8, m.getGift_price());
			c.setBigDecimal(9, m.getGift_nowprice());
			c.setInt(10, m.getOwnmonth());
			c.setString(11, m.getGift_sortid());
			c.setString(12, m.getGift_type());
			c.setString(13, m.getGift_projectname());
			c.registerOutParameter(14, java.sql.Types.INTEGER);
			c.execute();
			return c.getInt(14);
		} catch (SQLException e) {
			System.out.println("错误：" + e.toString());
			return 0;
		}
	}

	// 重新提交(新修改)
	public Integer EmActy_GiftInfoUpAgain(EmActySuppilerGiftInfoModel m) {
		dbconn db = new dbconn();
		try {
			CallableStatement c = db
					.getcall("EmActyGiftInfo_UpAgain_cyj(?,?,?,?,?,?,?,?,?,?,?,?,?)");
			c.setString(1, m.getGift_name());
			c.setString(2, m.getSupp_name());
			c.setBigDecimal(3, m.getGift_totalprice());
			c.setInt(4, m.getGift_totalnum());
			c.setString(5, m.getGift_addname());
			c.setString(6, m.getGift_remark());
			c.setString(7, m.getGift_inaddress());
			c.setBigDecimal(8, m.getGift_price());
			c.setBigDecimal(9, m.getGift_nowprice());
			c.setInt(10, m.getOwnmonth());
			c.setInt(11, m.getGift_id());
			c.setString(12, m.getGift_type());
			c.registerOutParameter(13, java.sql.Types.INTEGER);
			c.execute();
			return c.getInt(13);
		} catch (SQLException e) {
			System.out.println("错误：" + e.toString());
			return 0;
		}
	}

	// 重新提交
	public Integer EmActy_GiftUpAgain(EmActySuppilerGiftInfoModel m) {
		dbconn db = new dbconn();
		try {
			CallableStatement c = db
					.getcall("EmActySuppilerGiftInfo_UpAgain_cyj(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)");
			c.setString(1, m.getGift_name());
			c.setString(2, m.getGift_brand());
			c.setString(3, m.getSupp_name());
			c.setString(4, m.getGift_color());
			c.setString(5, m.getGift_production());
			c.setString(6, m.getGift_class());
			c.setInt(7, m.getGift_totalnum());
			c.setString(8, m.getGift_addname());
			c.setString(9, m.getGift_remark());
			c.setString(10, m.getGift_inaddress());
			c.setInt(11, m.getGift_id());
			c.setBigDecimal(12, m.getGift_price());
			c.setBigDecimal(13, m.getGift_nowprice());
			c.setBigDecimal(14, m.getGift_totalprice());
			c.registerOutParameter(15, java.sql.Types.INTEGER);
			c.execute();
			return c.getInt(15);
		} catch (SQLException e) {
			return 0;
		}
	}

	// 修改礼品信息
	public Integer EmActy_GiftEdit(EmActySuppilerGiftInfoModel m) {
		dbconn db = new dbconn();
		try {
			CallableStatement c = db
					.getcall("EmActySuppilerGiftInfo_Edit_cyj(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)");
			c.setString(1, m.getGift_name());
			c.setString(2, m.getGift_brand());
			c.setString(3, m.getSupp_name());
			c.setString(4, m.getGift_color());
			c.setString(5, m.getGift_production());
			c.setString(6, m.getGift_class());
			c.setString(7, m.getGift_intime());
			c.setInt(8, m.getGift_totalnum());
			c.setString(9, m.getGift_addname());
			c.setString(10, m.getGift_remark());
			c.setString(11, m.getGift_inaddress());
			c.setInt(12, m.getGift_nownum());
			c.setInt(13, m.getGift_id());
			c.setBigDecimal(14, m.getGift_price());
			c.setBigDecimal(15, m.getGift_nowprice());
			c.setBigDecimal(16, m.getGift_totalprice());
			c.registerOutParameter(17, java.sql.Types.INTEGER);
			c.execute();
			return c.getInt(17);
		} catch (SQLException e) {
			return 0;
		}
	}

	// 礼品出库并修改库存数量
	public Integer EmActy_Giftout(EmActyGiftOutInfoModel m) {
		dbconn db = new dbconn();
		try {
			CallableStatement c = db
					.getcall("EmActyGiftOutInfo_Add_cyj(?,?,?,?,?)");
			c.setInt(1, m.getGout_giftid());
			c.setInt(2, m.getGout_num());
			c.setString(3, m.getGout_name());
			c.setString(4, m.getGout_remark());
			c.registerOutParameter(5, java.sql.Types.INTEGER);
			c.execute();
			return c.getInt(5);
		} catch (SQLException e) {
			return 0;
		}
	}

	// 退回礼品采购申请
	public Integer EmActy_GiftBack(EmActyGiftBackInfoModel m) {
		dbconn db = new dbconn();
		try {
			CallableStatement c = db
					.getcall("EmActyGiftInfo_Back_cyj(?,?,?,?)");
			c.setInt(1, m.getGtbk_giftid());
			c.setString(2, m.getGibk_cause());
			c.setString(3, m.getGtbk_name());
			c.registerOutParameter(4, java.sql.Types.INTEGER);
			c.execute();
			return c.getInt(4);
		} catch (SQLException e) {
			return 0;
		}
	}

	// 更新领卡信息表任务单id
	public boolean updateGiftInfoTaprid(int taprid, int id) {
		dbconn db = new dbconn();
		PreparedStatement psmt = null;
		int row = 0;
		String sql = "update EmActySuppilerGiftInfo set gift_tarpid=? where gift_id=?";
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

	// 更新领卡信息表任务单id(支付费用)
	public boolean updateGiftInfopTaprid(int taprid, int id) {
		dbconn db = new dbconn();
		PreparedStatement psmt = null;
		int row = 0;
		String sql = "update EmActySuppilerGiftInfo set gift_ptarpid=? where gift_id=?";
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

	// 根据emwf_sortid 更新EmWelfare表的任务id
	public boolean updateEmWelfareTaprid(int taprid, String emwf_sortid) {
		dbconn db = new dbconn();
		PreparedStatement psmt = null;
		int row = 0;
		String sql = "update EmWelfare set emwf_tapr_id=? where emwf_sortid=?";
		try {
			psmt = db.getpre(sql);
			psmt.setInt(1, taprid);
			psmt.setString(2, emwf_sortid);
			row = psmt.executeUpdate();
		} catch (Exception e) {
			// TODO: handle exception
		}
		return row > 0 ? true : false;
	}

	// 根据emwf_sortid和emwf_tapr_id 更新EmWelfare表的任务emwf_tapr_id
	public boolean updateEmWelfareTaprids(int taprid, String emwf_sortid) {
		dbconn db = new dbconn();
		PreparedStatement psmt = null;
		int row = 0;
		String sql = "update EmWelfare set emwf_tapr_id=? where emwf_sortid=?";
		try {
			psmt = db.getpre(sql);
			psmt.setInt(1, taprid);
			psmt.setString(2, emwf_sortid);
			row = psmt.executeUpdate();
		} catch (Exception e) {
			// TODO: handle exception
		}
		return row > 0 ? true : false;
	}

	// 更新领卡信息表任务单id
	public boolean updateGiftInfo(String sqlstr, int id) {
		dbconn db = new dbconn();
		int row = 0;
		String sql = "update EmActySuppilerGiftInfo set gift_modname='"
				+ UserInfo.getUsername() + "'" + sqlstr + "  where gift_id="
				+ id;
		try {
			row = db.execQuery(sql);
		} catch (Exception e) {
			// TODO: handle exception
		}
		return row > 0 ? true : false;
	}

	// 更新员工福利名单信息
	public boolean updateEmWelfare(String sortid, String idstr, Integer stateid) {
		dbconn db = new dbconn();
		int row = 0;
		String sql = "update EmWelfare set emwf_sortid='" + sortid
				+ "',emwf_state=" + stateid + "  where emwf_id in (" + idstr
				+ ")";
		try {
			row = db.execQuery(sql);
		} catch (Exception e) {
			// TODO: handle exception
		}
		return row > 0 ? true : false;
	}

	// 更新员工福利名单信息
	public boolean updateEmWelfareBySortid(String sortid, Integer stateid) {
		dbconn db = new dbconn();
		int row = 0;
		String sql = "update EmWelfare set emwf_state=" + stateid + ""
				+ " where emwf_sortid='" + sortid + "'";
		try {
			row = db.execQuery(sql);
		} catch (Exception e) {
			// TODO: handle exception
		}
		return row > 0 ? true : false;
	}

	// 更新员工福利名单信息
	public int updateEmWelfareBySortid(String sortid, String sqls) {
		dbconn db = new dbconn();
		int row = 0;
		String sql = "update EmWelfare set emwf_modname='"
				+ UserInfo.getUsername() + "'" + sqls + ""
				+ " where emwf_sortid='" + sortid + "'";
		try {
			row = db.execQuery(sql);
		} catch (Exception e) {
			// TODO: handle exception
		}
		return row;
	}

	// 更新员工福利名单信息
	public boolean updateEmWelfare(String idstr, Integer ownmonth) {
		dbconn db = new dbconn();
		int row = 0;
		String sql = "update EmWelfare set ownmonth='" + ownmonth
				+ "' where emwf_id in (" + idstr + ")";
		try {
			row = db.execQuery(sql);
		} catch (Exception e) {
			// TODO: handle exception
		}
		return row > 0 ? true : false;
	}

	// 更新员工福利名单信息表的任务id
	public boolean updateEmWelfareTarpid(String idstr, Integer tarpid) {
		dbconn db = new dbconn();
		int row = 0;
		String sql = "update EmWelfare set emwf_tapr_id=" + tarpid
				+ " where emwf_id in (" + idstr + ")";
		try {
			row = db.execQuery(sql);
		} catch (Exception e) {
			// TODO: handle exception
		}
		return row > 0 ? true : false;
	}

	// 更新员工福利名单信息
	public Integer updateEmWelfare(Integer gift_id, Integer emwf_id,
			String client) {
		dbconn db = new dbconn();
		try {
			CallableStatement c = db
					.getcall("EmActyGiftInfo_update_cyj(?,?,?,?)");
			c.setInt(1, gift_id);
			c.setInt(2, emwf_id);
			c.setString(3, client);
			c.registerOutParameter(4, java.sql.Types.INTEGER);
			c.execute();
			return c.getInt(4);
		} catch (SQLException e) {
			System.out.println("错误：" + e.getMessage());
			return 0;
		}
	}

	// 更新员工福利名单信息(新修改)
	public Integer updateEmWelfare2(Integer gift_id, EmWelfareModel model) {
		dbconn db = new dbconn();
		try {
			CallableStatement c = db
					.getcall("GiftAndEmWelfare_update_cyj(?,?,?,?,?,?)");
			c.setInt(1, gift_id);
			c.setInt(2, model.getEmwf_id());
			c.setString(3, model.getEmwf_takeclient());
			c.setDate(4, StringtoDate(model.getEmwf_sendtime()));
			c.setString(5, UserInfo.getUsername());
			c.registerOutParameter(6, java.sql.Types.INTEGER);
			c.execute();
			return c.getInt(6);
		} catch (SQLException e) {
			System.out.println("错误：" + e.getMessage());
			return 0;
		}
	}

	// 更新员工福利名单信息
	public Integer updateEmWelfares(String emwf_linktime,
			String emwf_linkcontent, Integer id) {
		dbconn db = new dbconn();
		int row = 0;
		String sql = "update EmWelfare set emwf_linktime='" + emwf_linktime
				+ "',emwf_linkcontent='" + emwf_linkcontent + "'"
				+ " ,emwf_state=9 where emwf_id =" + id;
		try {
			row = db.execQuery(sql);
		} catch (Exception e) {
			// TODO: handle exception
		}
		return row;
	}

	// 更新员工福利名单信息
	public Integer updateEmWelfareInfo(String sqlstr, Integer id) {
		dbconn db = new dbconn();
		int row = 0;
		String sql = "update EmWelfare set emwf_modname='"
				+ UserInfo.getUsername() + "'" + sqlstr + " where emwf_id ="
				+ id;
		try {
			row = db.execQuery(sql);
		} catch (Exception e) {
			// TODO: handle exception
		}
		return row;
	}

	// 市场部退回客服确认的数据
	public Integer backEmWelfareInfo(String emwf_backcase, String strid) {
		dbconn db = new dbconn();
		int row = 0;
		String sql = "update EmWelfare set emwf_modname='"
				+ UserInfo.getUsername() + "',Emwf_state=16,emwf_backcase='"
				+ emwf_backcase + "'" + " where emwf_id in(" + strid + ")";
		try {
			row = db.execQuery(sql);
		} catch (Exception e) {
			// TODO: handle exception
		}
		return row;
	}

	// 根据付款单号更新福利信息状态
	public Integer updateEmWelfareInfos(String sqlstr, String paynum) {
		dbconn db = new dbconn();
		int row = 0;
		String sql = "update EmWelfare set emwf_modname='"
				+ UserInfo.getUsername() + "'" + sqlstr
				+ " where emwf_paynum ='" + paynum + "'";
		try {
			row = db.execQuery(sql);
		} catch (Exception e) {
			// TODO: handle exception
		}
		return row;
	}

	// 生成支付通知时更新员工福利名单信息
	public Integer updateEmWelfareInfo2(String sortid) {
		dbconn db = new dbconn();
		int row = 0;
		String sql = "update EmWelfare set emwf_modname='"
				+ UserInfo.getUsername() + "',emwf_state=7"
				+ " where emwf_sortid='" + sortid
				+ "' and (emwf_state=6 or emwf_linktime is not null)";
		try {
			row = db.execQuery(sql);
		} catch (Exception e) {
			// TODO: handle exception
		}
		return row;
	}

	// 供应商联系信息新增
	public Integer EmActyContactContentInfo(EmActyContactContentInfoModel m) {
		dbconn db = new dbconn();
		try {
			CallableStatement c = db
					.getcall("EmActyContactContentInfo_Add_cyj(?,?,?,?,?)");
			c.setInt(1, m.getCact_gift_id());
			c.setString(2, m.getCact_content());
			c.setString(3, m.getCact_addname());
			c.setString(4, m.getCact_remark());
			c.registerOutParameter(5, java.sql.Types.INTEGER);
			c.execute();
			return c.getInt(5);
		} catch (SQLException e) {
			return 0;
		}
	}

	// String类型转换Date
	public Date StringtoDate(String d) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		Date Date = null;
		try {
			if (d != null) {
				Date = timechange(sdf.parse(d));
			}
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return Date;
	}

	// 退回福利申请
	public Integer EmWelfare_back(String sortid, String backcase) {
		dbconn db = new dbconn();
		try {
			CallableStatement c = db.getcall("EmWelfare_back_cyj(?,?,?)");
			c.setString(1, sortid);
			c.setString(2, backcase);
			c.registerOutParameter(3, java.sql.Types.INTEGER);
			c.execute();
			return c.getInt(3);
		} catch (SQLException e) {
			return 0;
		}
	}

	// 取消申请
	public Integer EmWelfare_cancel(Integer emwf_id, String backcase) {
		dbconn db = new dbconn();
		try {
			CallableStatement c = db.getcall("EmWelfare_cancel_cyj(?,?,?)");
			c.setInt(1, emwf_id);
			c.setString(2, backcase);
			c.registerOutParameter(3, java.sql.Types.INTEGER);
			c.execute();
			return c.getInt(3);
		} catch (SQLException e) {
			return 0;
		}
	}

	// 新增或者修改库存信息
	public Integer EmActyWarehouse(EmActyWarehouseModel model) {
		if (model.getWase_prty_id() == null) {
			model.setWase_prty_id(0);
		}
		dbconn db = new dbconn();
		try {
			CallableStatement c = db
					.getcall("[EmActyWarehouse_Add_p_cyj](?,?,?,?,?,?,?)");
			c.setString(1, model.getWase_name());
			c.setInt(2, model.getWase_totalnum());
			c.setString(3, model.getWase_addname());
			c.setInt(4, model.getWase_prod_id());
			c.setInt(5, model.getWase_prty_id());
			c.setString(6, model.getWase_unit());
			c.registerOutParameter(7, java.sql.Types.INTEGER);
			c.execute();
			return c.getInt(7);
		} catch (SQLException e) {
			return 0;
		}
	}

	// 库存记录
	public Integer addEmActyWarehouseHistory(Integer emwf_id, Integer num,
			BigDecimal hsry_price, Integer hsry_wase_id) {
		dbconn db = new dbconn();
		int row = 0;
		String sql = "insert into EmActyWarehouseHistory(hsry_emwf_id, hsry_num,hsry_price,hsry_addname,hsry_wase_id) "
				+ " values ("
				+ emwf_id
				+ ","
				+ num
				+ ",'"
				+ hsry_price
				+ "','"
				+ UserInfo.getUsername() + "'," + hsry_wase_id + ")";
		try {
			row = db.execQuery(sql);
		} catch (Exception e) {
			// TODO: handle exception
		}
		return row;
	}

	// 库存记录
	public Integer addEmactyUseHouseLog(EmactyUseHouseLogModel m) {
		dbconn db = new dbconn();
		int row = 0;
		String sql = "insert into EmactyUseHouseLog(useh_sortid, useh_prod_id,useh_prty_id,"
				+ "useh_num,useh_addname,useh_price,useh_wase_id) "
				+ " values ('"
				+ m.getUseh_sortid()
				+ "',"
				+ m.getUseh_prod_id()
				+ ","
				+ m.getUseh_prty_id()
				+ ","
				+ m.getUseh_num()
				+ ",'"
				+ UserInfo.getUsername() + "','" + m.getUseh_price() + "',"+m.getUseh_wase_id()+")";
		try {
			row = db.execQuery(sql);
		} catch (Exception e) {
			// TODO: handle exception
		}
		return row;
	}

	// 减去使用的库存数量
	public Integer delEmactyUseHouse(int wase_id, int useh_num) {
		dbconn db = new dbconn();
		int row = 0;
		String sql = "update EmActyWarehouse set wase_nownum=" + useh_num
				+ " where wase_id=" + wase_id;
		row = db.execQuery(sql);
		try {
			row = db.execQuery(sql);
		} catch (Exception e) {
			// TODO: handle exception
		}
		return row;
	}

	// 修改库存
	public Integer WarehouseHistoryEdit(EmActyWarehouseHistoryModel m,
			Integer num) {
		dbconn db = new dbconn();
		try {
			CallableStatement c = db
					.getcall("WarehouseHistory_Edit_p_cyj(?,?,?,?,?)");
			c.setInt(1, m.getHsry_wase_id());
			c.setInt(2, m.getHsry_id());
			c.setInt(3, num);
			c.setString(4, UserInfo.getUsername());
			c.registerOutParameter(5, java.sql.Types.INTEGER);
			c.execute();
			return c.getInt(5);
		} catch (SQLException e) {
			return 0;
		}
	}

	// 修改库存
	public Integer WarehouseEdit(EmActyWarehouseModel m, Integer num,
			String editcontent) {
		dbconn db = new dbconn();
		try {
			CallableStatement c = db.getcall("Warehouse_Edit_p_cyj(?,?,?,?,?)");
			c.setInt(1, m.getWase_id());
			c.setInt(2, num);
			c.setString(3, UserInfo.getUsername());
			c.setString(4, editcontent);
			c.registerOutParameter(5, java.sql.Types.INTEGER);
			c.execute();
			return c.getInt(5);
		} catch (SQLException e) {
			return 0;
		}
	}

	public int insertHouse(int wase_id, int hsnt_num, int wase_tnum,
			int changenum, String editcontent, String hsnt_remark,
			String wase_name, String hsnt_tali_name) {
		dbconn db = new dbconn();
		try {
			CallableStatement c = db
					.getcall("EmActyWarehouse_Edit_p_cyj(?,?,?,?,?,?,?,?,?,?)");
			c.setInt(1, wase_id);
			c.setInt(2, hsnt_num);
			c.setInt(3, wase_tnum);
			c.setInt(4, changenum);
			c.setString(5, editcontent);
			c.setString(6, UserInfo.getUsername());
			c.setString(7, hsnt_remark);
			c.setString(8, wase_name);
			c.setString(9, hsnt_tali_name);
			c.registerOutParameter(10, java.sql.Types.INTEGER);
			c.execute();
			return c.getInt(10);
		} catch (SQLException e) {
			return 0;
		}
	}

	// 时间格式转换
	private Date timechange(java.util.Date d) {
		Date da = null;
		if (d != null && !d.equals("")) {
			java.sql.Date date = new java.sql.Date(d.getTime());
			da = date;
		}
		return da;
	}
}
