package dal.CoCompact;

import java.sql.CallableStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import org.zkoss.zk.ui.Executions;

import Conn.dbconn;
import Model.CoCompactModel;
import Util.UserInfo;

public class CompactDetailDal {
	private static dbconn conn = new dbconn();

	// 获取所有公司合同数据集
	private ResultSet getCoCoBase() throws Exception {
		int coco_id = ((CoCompactModel) Executions.getCurrent().getArg()
				.get("cocoM")).getCoco_id();
		ResultSet rs = null;
		String sql = "select * from CoOffer a left join CoOfferList b on b.coli_coof_id=a.coof_id where a.coof_coco_id="
				+ coco_id;
		rs = conn.GRS(sql);
		return rs;
	}

	// 获取所有公司合同(CoCompact_CoLa_CoBase_V)
	public ArrayList<CoCompactModel> getCoCoBaseList() {
		ArrayList<CoCompactModel> CoCoBaseList = new ArrayList<CoCompactModel>();
		try {
			ResultSet rs = getCoCoBase();
			while (rs.next()) {
				CoCoBaseList.add(new CoCompactModel(rs.getInt("coof_id"), rs
						.getString("coli_id"), rs.getString("coof_id"), rs
						.getString("coli_pclass"), rs.getString("coli_city"),
						rs.getString("coli_name"), rs
								.getString("coli_standard"), rs
								.getString("coli_fee"), rs
								.getString("coli_fee"), null, null, null, null,
						null, 1, null, null, null, null, null, null, null,
						null, null, null, null, null, null, null));
			}
		} catch (Exception e) {
			System.out.println(e.toString());
		}
		return CoCoBaseList;
	}

	// 新增产品
	public String addco(String coli_id, String coli_fee, String coli_date) {
		try {
			CallableStatement c = conn.getcall("CoGlist_Add_P_zzq(?,?,?,?)");
			System.out.println("xxxxx");
			System.out.println(coli_id);
			System.out.println(coli_fee);
			System.out.println(coli_date);
			
			c.setString(1, coli_id);
			c.setString(2, coli_fee);
			c.setString(3, coli_date);
			c.registerOutParameter(4, java.sql.Types.INTEGER);
			c.execute();
			return c.getString(4);

		} catch (SQLException e) {
			return "1";
		}
	}

	// 变更收费金额
	public String changeco(String coli_id, String coli_fee, String coli_date) {
		try {
			CallableStatement c = conn.getcall("CoGlist_Change_P_zzq(?,?,?,?,?)");

			c.setString(1, coli_id);
			c.setString(2, coli_fee);
			c.setString(3, coli_date);
			c.setString(4, UserInfo.getUsername());
			c.registerOutParameter(5, java.sql.Types.INTEGER);
			c.execute();
			return c.getString(5);

		} catch (SQLException e) {
			return "1";
		}
	}

	// 变更收费金额(全国项目)
	public String changecoqg(String coli_id, String coli_fee, String coli_date) {
		try {
			CallableStatement c = conn
					.getcall("CoGlist_Change_qg_P_lsb(?,?,?,?,?)");

			c.setString(1, coli_id);
			c.setString(2, coli_fee);
			c.setString(3, coli_date);
			c.setString(4, UserInfo.getUsername());
			c.registerOutParameter(5, java.sql.Types.INTEGER);
			c.execute();
			return c.getString(5);

		} catch (SQLException e) {
			return "1";
		}
	}

	// 产品终止
	public String stopco(String coli_id, String coli_fee, String coli_date) {
		try {
			CallableStatement c = conn.getcall("CoGlist_Stop_P_zzq(?,?,?,?)");
			System.out.println(coli_id);
			System.out.println(coli_fee);
			System.out.println(coli_date);

			c.setString(1, coli_id);
			c.setString(2, coli_fee);
			c.setString(3, coli_date);
			c.registerOutParameter(4, java.sql.Types.INTEGER);
			c.execute();
			return c.getString(4);

		} catch (SQLException e) {
			return "1";
		}
	}
}
