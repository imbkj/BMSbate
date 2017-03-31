package dal.EmBodyCheck;

import java.sql.CallableStatement;
import java.sql.SQLException;
import java.sql.Types;
import java.util.List;

import Conn.dbconn;
import Model.EmBcItemGroupModel;
import Model.EmBodyCheckItemModel;
import Util.UserInfo;

public class EmbcItem_OperateDal {
	// 新增体检项目
	public Integer EmBodyCheckItemAdd(EmBodyCheckItemModel em) {
		dbconn db = new dbconn();
		Integer i = 0;
		try {
			i = (Integer) db.callWithReturn(
					"{?=call EmBcItem_Add_cyj(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)}",
					Types.INTEGER, em.getEbit_hospital(), em.getEbit_name(),em.getEbit_name2(),
					em.getEbit_charge(), em.getEbit_discount(),
					em.getEbit_package(), em.getEbit_state(),
					em.getEbit_blood(), em.getEbit_bmain(), em.getEbit_sex(),
					em.getEbit_marry(), em.getEbit_frequency(),
					em.getEbit_info(), em.getEbit_remark(),
					UserInfo.getUsername());
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return i;
		/*
		 * try { CallableStatement c = db
		 * .getcall("EmBcItem_Add_cyj(?,?,?,?,?,?,?,?,?,?)"); c.setString(1,
		 * m.getEbcs_hospital()); c.setString(2, m.getEbit_name());
		 * c.setBigDecimal(3, m.getEbit_charge()); c.setBigDecimal(4,
		 * m.getEbit_discount()); c.setInt(5, m.getEbit_blood()); c.setInt(6,
		 * m.getEbit_state()); c.setString(7, m.getEbit_info()); c.setString(8,
		 * m.getEbit_remark()); c.setString(9, m.getEbit_addname());
		 * c.registerOutParameter(10, java.sql.Types.INTEGER); c.execute();
		 * return c.getInt(10); } catch (SQLException e) { return 0; }
		 */
	}

	// 修改体检项目
	public Integer EmBodyCheckItemUpdate(EmBodyCheckItemModel em) {
		dbconn db = new dbconn();
		Integer i = 0;

		try {
			i = (Integer) db
					.callWithReturn(
							"{?=call EmBcItem_Update_cyj(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)}",
							Types.INTEGER, em.getEbit_id(),
							em.getEbit_hospital(), em.getEbit_name(),
							em.getEbit_name2(), em.getEbit_charge(),
							em.getEbit_discount(), em.getEbit_package(),
							em.getEbit_state(), em.getEbit_blood(),
							em.getEbit_bmain(), em.getEbit_sex(),
							em.getEbit_marry(), em.getEbit_frequency(),
							em.getEbit_info(), em.getEbit_remark(),
							UserInfo.getUsername());
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return i;
		/*
		 * try {
		 * 
		 * CallableStatement c = db
		 * .getcall("EmBcItem_Update_cyj(?,?,?,?,?,?,?,?,?,?,?)");
		 * c.setString(1, m.getEbcs_hospital()); c.setString(2,
		 * m.getEbit_name()); c.setBigDecimal(3, m.getEbit_charge());
		 * c.setBigDecimal(4, m.getEbit_discount()); c.setInt(5,
		 * m.getEbit_blood()); c.setInt(6, m.getEbit_state()); c.setString(7,
		 * m.getEbit_info()); c.setString(8, m.getEbit_remark()); c.setString(9,
		 * m.getEbit_addname()); c.setInt(10, m.getEbit_id());
		 * c.registerOutParameter(11, java.sql.Types.INTEGER); c.execute();
		 * return c.getInt(11); } catch (SQLException e) { return 0; }
		 */
	}

	// 体检项目组合新增
	public Integer ItemGroupAdd(EmBcItemGroupModel m) {
		dbconn db = new dbconn();
		try {
			CallableStatement c = db
					.getcall("EmBcItemGroup_Add_cyj(?,?,?,?,?,?,?,?)");
			c.setInt(1, m.getCid());
			c.setString(2, m.getEbig_name());
			c.setInt(3, m.getEbig_hospital());
			c.setString(4, m.getEbig_remark());
			c.setString(5, m.getEbig_addname());
			c.setBigDecimal(6, m.getEbig_charge());
			c.setBigDecimal(7, m.getEbig_discount());
			c.registerOutParameter(8, java.sql.Types.INTEGER);
			c.execute();
			return c.getInt(8);
		} catch (SQLException e) {
			return 0;
		}
	}

	public Integer ItemGroupInfoAdd(Integer ebit_id, Integer id, String addname) {
		Integer i = 0;
		dbconn db = new dbconn();
		String sql = "insert into EmbcIGList(eigl_ebig_id,eigl_ebit_id,eigl_addtime,eigl_addname)"
				+ "values (?,?,getdate(),?)";
		try {
			i = db.insertAndReturnKey(sql, id, ebit_id, addname);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return i;

	}

	// 体检项目组合修改
	public Integer ItemGroupEdit(EmBcItemGroupModel m,
			List<EmBodyCheckItemModel> list) {
		Integer i = 0;
		dbconn db = new dbconn();
		try {
			i = Integer.valueOf(db.callWithReturn(
					"{?=call EmBcItemGroup_Edit_cyj(?,?,?,?,?,?,?,?)}",
					Types.INTEGER, m.getEbig_id(), m.getCid(),
					m.getEbig_name(), m.getEbig_hospital(), m.getEbig_remark(),
					m.getEbig_charge(), m.getEbig_discount(),
					m.getEbig_modname()).toString());
			for (int j = 0; j < list.size(); j++) {
				AddEmbcIGList(m.getEbig_id(), list.get(j).getEbit_id(),
						m.getEbig_modname());
			}
		} catch (NumberFormatException | SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return i;
	}

	// 添加组合关系表
	public int AddEmbcIGList(int eigl_ebig_id, int eigl_ebit_id, String username) {
		int k = 0;
		try {
			dbconn db = new dbconn();
			String sql = "INSERT INTO EmbcIGList(eigl_ebig_id,eigl_ebit_id,eigl_addname)"
					+ " VALUES("
					+ eigl_ebig_id
					+ ",'"
					+ eigl_ebit_id
					+ "','"
					+ username + "')";
			k = db.execQuery(sql);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return k;
	}

	// 删除某个组合的关系信息
	public int delEmbcIGList(int eigl_ebig_id) {
		int k = 0;
		try {
			dbconn db = new dbconn();
			String sql = "delete from EmbcIGList where eigl_ebig_id="
					+ eigl_ebig_id;
			k = db.execQuery(sql);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return k;
	}
}
