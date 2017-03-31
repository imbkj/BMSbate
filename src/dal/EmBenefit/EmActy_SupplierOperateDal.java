package dal.EmBenefit;

import java.sql.CallableStatement;
import java.sql.SQLException;

import Conn.dbconn;
import Model.EmActyProduceModel;
import Model.EmActySupContactManInfoModel;
import Model.EmActySupProductInfoModel;
import Model.EmActySupplierInfoModel;
import Util.UserInfo;

public class EmActy_SupplierOperateDal {
	// 供应商信息新增
	public Integer EmActy_SupplierAdd(EmActySupplierInfoModel m) {
		dbconn db = new dbconn();
		try {
			CallableStatement c = db
					.getcall("EmActySupplierInfo_Add_cyj(?,?,?,?,?,?,?)");
			c.setString(1, m.getSupp_name());
			c.setString(2, m.getSupp_type());
			c.setString(3, m.getSupp_website());
			c.setString(4, m.getSupp_address());
			c.setString(5, m.getSupp_addname());
			c.setString(6, m.getSupp_remark());
			c.registerOutParameter(7, java.sql.Types.INTEGER);
			c.execute();
			return c.getInt(7);
		} catch (SQLException e) {
			return 0;
		}
	}

	// 供应商产品信息新增
	public Integer EmActySupProductInfoAdd(EmActySupProductInfoModel m) {
		dbconn db = new dbconn();
		try {
			CallableStatement c = db
					.getcall("EmActySupProductInfo_Add_cyj(?,?,?,?,?,?,?,?,?)");
			c.setInt(1, m.getProd_supid());
			c.setString(2, m.getProd_name());
			c.setBigDecimal(3, m.getProd_price());
			c.setBigDecimal(4, m.getProd_discountprice());
			c.setString(5, m.getProd_discount());
			c.setInt(6, m.getProd_totalnum());
			c.setString(7, m.getProd_addname());
			c.setString(8, m.getProd_remark());
			c.registerOutParameter(9, java.sql.Types.INTEGER);
			c.execute();
			return c.getInt(9);
		} catch (SQLException e) {
			System.out.println("错误：" + e.toString());
			return 0;
		}
	}

	// 供应商联系人信息新增
	public Integer EmActySupContactManInfoAdd(EmActySupContactManInfoModel m) {
		dbconn db = new dbconn();
		try {
			CallableStatement c = db
					.getcall("EmActySupContactManInfo_Add_cyj(?,?,?,?,?,?,?,?,?,?)");
			c.setInt(1, m.getCoct_supid());
			c.setString(2, m.getCoct_name());
			c.setString(3, m.getCoct_sex());
			c.setString(4, m.getCoct_mobile());
			c.setString(5, m.getCoct_phone());
			c.setString(6, m.getCoct_address());
			c.setString(7, m.getCoct_addname());
			c.setString(8, m.getCoct_Email());
			c.setString(9, m.getCoct_remark());
			c.registerOutParameter(10, java.sql.Types.INTEGER);
			c.execute();
			return c.getInt(10);
		} catch (SQLException e) {
			return 0;
		}
	}

	// 修改供应商基本信息
	public int updateEmActySupplierInfo(EmActySupplierInfoModel model) {
		int i = 0;

		if (model.getSupp_id() != null && !model.getSupp_id().equals("")) {
			dbconn db = new dbconn();
			String sql = "update EmActySupplierInfo set supp_modname='"
					+ UserInfo.getUsername() + "',supp_modtime=getdate()";

			if (model.getSupp_name() != null
					&& !model.getSupp_name().equals("")) {
				sql += ",supp_name='" + model.getSupp_name().replace("'", "''") + "'";
			}
			if (model.getSupp_address() != null
					&& !model.getSupp_address().equals("")) {
				sql += ",supp_address='" + model.getSupp_address().replace("'", "''") + "'";
			}
			if (model.getSupp_website() != null
					&& !model.getSupp_website().equals("")) {
				sql += ",supp_website='" + model.getSupp_website().replace("'", "''") + "'";
			}
			if (model.getSupp_remark() != null
					&& !model.getSupp_remark().equals("")) {
				sql += ",supp_remark='" + model.getSupp_remark().replace("'", "''") + "'";
			}
			if (model.getSupp_type()!=null
				&& !model.getSupp_type().equals("")) {
					sql += ",supp_type='" + model.getSupp_type().replace("'", "''") + "'";
			}

			sql += " where supp_Id=?";
			try {
				i = db.updatePrepareSQL(sql, model.getSupp_id());
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return i;
	}

	// 修改供应商联系人信息
	public int updateEmActySupProductInfo(EmActySupProductInfoModel model) {
		int i = 0;
		if (model.getProd_id() != null && !model.getProd_id().equals("")) {
			
			dbconn db = new dbconn();
			String sql = "update EmActySupProductInfo set prod_modname='"
					+ UserInfo.getUsername() + "',prod_modtime=getdate()";
			
			if (model.getProd_name() != null
					&& !model.getProd_name().equals("")) {
				sql += ",prod_name='" + model.getProd_name().replace("'", "''") + "'";
			}
			if (model.getProd_price() != null
					&& !model.getProd_price().equals("")) {
				sql += ",prod_Price='" + model.getProd_price() + "'";
			}
			if (model.getProd_discountprice() != null
					&& !model.getProd_discountprice().equals("")) {
				sql += ",prod_Discountprice='" + model.getProd_discountprice()
						+ "'";
			}
			if (model.getProd_discount() != null
					&& !model.getProd_discount().equals("")) {
				sql += ",prod_discount='" + model.getProd_discount() + "'";
			}
			if (model.getProd_totalnum() != null
					&& !model.getProd_totalnum().equals("")) {
				sql += ",prod_totalnum='" + model.getProd_totalnum() + "'";
			}
			if (model.getProd_state() != null
					&& !model.getProd_state().equals("")) {
				sql += ",prod_state='" + model.getProd_state() + "'";
			}
			if (model.getProd_remark() != null
					&& !model.getProd_remark().equals("")) {
				sql += ",prod_remark='" + model.getProd_remark() + "'";
			}

			sql += " where prod_id=?";

			try {
				i = db.updatePrepareSQL(sql, model.getProd_id());
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return i;
	}

	// 修改供应商产品信息
	public int updateEmActySupContactManInfo(EmActySupContactManInfoModel model) {
		int k = 0;
		try {
			String sql = "";
			dbconn db = new dbconn();
			sql = "update EmActySupContactManInfo set Coct_Name='"
					+ model.getCoct_name() + "',Coct_Phone='"
					+ model.getCoct_phone() + "',";
			sql = sql + "Coct_Mobile='" + model.getCoct_mobile()
					+ "',Coct_Address='" + model.getCoct_address() + "',"
					+ "Coct_Email='" + model.getCoct_Email() + "',Coct_state="
					+ model.getCoct_state() + "," + "Coct_remark='"
					+ model.getCoct_remark() + "' " + " where Coct_id="
					+ model.getCoct_id();
			k = db.execQuery(sql);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return k;
	}

	// 删除联系人信息
	public Integer deletecoct(Integer id) {
		int k = 0;
		try {
			String sql = "";
			dbconn db = new dbconn();
			sql = "update EmActySupContactManInfo set Coct_state=0"
					+ " where Coct_id=" + id;
			k = db.execQuery(sql);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return k;
	}

	// 删除报价信息
	public Integer deleteprod(Integer id) {
		int k = 0;
		try {
			String sql = "";
			dbconn db = new dbconn();
			sql = "update EmActySupProductInfo set prod_state=0"
					+ " where prod_id=" + id;
			k = db.execQuery(sql);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return k;
	}

	public Integer modProd(EmActySupProductInfoModel easm) {
		Integer i = 0;
		dbconn db = new dbconn();
		String sql = "update EmActySupProductInfo set prod_modtime=getdate(),prod_modname=?";
		if (easm.getProd_eaco_id() != null
				&& !easm.getProd_eaco_id().equals("")) {
			if (easm.getProd_eaco_id().equals(0)) {
				sql = sql + " ,prod_eaco_id =null";
			} else {
				sql = sql + " ,prod_eaco_id=" + easm.getProd_eaco_id();
			}

		}
		sql = sql + " where prod_id=?";
		try {
			i = db.updatePrepareSQL(sql, easm.getProd_modname(),
					easm.getProd_id());

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return i;
	}
	
	//修改产品信息
	public Integer EmActyProduceEdit(EmActyProduceModel m) {
		dbconn db = new dbconn();
		try {
				CallableStatement c = db
						.getcall("EmActyProduce_edit_P_cyj(?,?,?,?,?)");
				c.setInt(1, m.getProd_id());
				c.setInt(2, m.getPm().getPrty_id());
				c.setBigDecimal(3, m.getProd_discount());
				c.setString(4, m.getProd_pricetype());
				c.registerOutParameter(5, java.sql.Types.INTEGER);
				c.execute();
				return c.getInt(5);
			} catch (SQLException e) {
				return 0;
			}
		}

}
