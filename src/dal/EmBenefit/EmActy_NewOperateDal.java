package dal.EmBenefit;

import java.sql.CallableStatement;
import java.sql.SQLException;

import Conn.dbconn;
import Model.EmActyProduceModel;
import Model.EmActyProducetypeModel;
import Model.EmActyWarehouseModel;
import Util.UserInfo;

public class EmActy_NewOperateDal {
	// 新增或者修改库存信息
	public Integer EmActyWarehouse(EmActyWarehouseModel model) {
		dbconn db = new dbconn();
		try {
			CallableStatement c = db
					.getcall("[EmActyWarehouse_New_Add_p_cyj](?,?,?,?,?,?,?)");
			c.setString(1, model.getWase_name());
			c.setInt(2, model.getWase_totalnum());
			c.setString(3, model.getWase_addname());
			c.setString(3, model.getWase_addname());
			c.setInt(
					4,
					model.getWase_prod_id() == null ? 0 : model
							.getWase_prod_id());
			c.setInt(
					5,
					model.getWase_prty_id() == null ? 0 : model
							.getWase_prty_id());
			c.setString(6, model.getWase_unit());
			c.registerOutParameter(7, java.sql.Types.INTEGER);
			c.execute();
			return c.getInt(7);
		} catch (SQLException e) {
			return 0;
		}
	}

	// 把福利信息更新为已采购
	public Integer buyEmWelfareInfo(String sortid) {
		dbconn db = new dbconn();
		int row = 0;
		String sql = "update EmWelfare set emwf_modname='"
				+ UserInfo.getUsername() + "',emwf_state=6"
				+ " where emwf_sortid='" + sortid
				+ "'and (emwf_state=5 or emwf_state=10)";
		try {
			row = db.execQuery(sql);
		} catch (Exception e) {
			// TODO: handle exception
		}
		return row;
	}

	// 添加产品信息
	public int AddProduce(EmActyProduceModel m) {
		dbconn db = new dbconn();
		try {
			CallableStatement c = db
					.getcall("EmActyProduce_Add_p_cyj(?,?,?,?,?,?,?)");
			c.setString(1, m.getProd_name());
			c.setString(2, m.getProd_unit());
			c.setBigDecimal(3, m.getProd_discount());
			c.setString(4, m.getProd_pricetype());
			c.setInt(5, m.getProd_supp_id());
			c.setString(6, m.getProd_addname());
			c.registerOutParameter(7, java.sql.Types.INTEGER);
			c.execute();
			return c.getInt(7);
		} catch (SQLException e) {
			return 0;
		}
	}

	// 添加产品分项信息
	public int AddProduceType(EmActyProducetypeModel m) {
		dbconn db = new dbconn();
		int k=0;
		String sql="insert into EmActyProduceType( prty_prod_id, prty_name, " +
				"prty_discount, prty_unit, prty_addname) " +
				"values("+m.getPrty_prod_id()+",'"+m.getPrty_name()+"','" +
				m.getPrty_discount()+"','"+m.getPrty_unit()+"','"+m.getPrty_addname()+"')";
		k=db.execQuery(sql);
		return k;
	}
	
	//删除产品分项
}
