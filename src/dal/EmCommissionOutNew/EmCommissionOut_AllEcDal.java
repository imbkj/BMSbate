package dal.EmCommissionOutNew;

import java.sql.CallableStatement;
import java.sql.SQLException;

import Conn.dbconn;

public class EmCommissionOut_AllEcDal {
	private dbconn conn = new dbconn();

	// 委托单二次确认
	public int ecaut(String ecoc_id, String yl_cpdate, String yl_opdate,
			String sye_cpdate, String sye_opdate, String gs_cpdate,
			String gs_opdate, String jl_cpdate, String jl_opdate,
			String syu_cpdate, String syu_opdate, String house_cpdate,
			String house_opdate, String fw_date, String file_date,
			String other_date) {
		try {
			System.out.println("aaaa");
			System.out.println(ecoc_id);
			System.out.println( yl_cpdate);
			System.out.println(yl_opdate);
			System.out.println(sye_cpdate);
			System.out.println( sye_opdate);
			System.out.println( gs_cpdate);
			System.out.println( gs_opdate);
			System.out.println( jl_cpdate);
			System.out.println(jl_opdate);
			System.out.println( syu_cpdate);
			System.out.println( syu_opdate);
			System.out.println(house_cpdate);
			System.out.println(house_opdate);
			System.out.println( fw_date);
			System.out.println(file_date);
			System.out.println(other_date);

			CallableStatement c = conn
					.getcall("EmCommissionOut_NewAllEcAutUpdate_P_zzq(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)");

			c.setString(1, ecoc_id);
			c.setString(2, yl_cpdate+"-01");
			c.setString(3, yl_opdate+"-01");
			c.setString(4, sye_cpdate+"-01");
			c.setString(5, sye_opdate+"-01");
			c.setString(6, gs_cpdate+"-01");
			c.setString(7, gs_opdate+"-01");
			c.setString(8, jl_cpdate+"-01");
			c.setString(9, jl_opdate+"-01");
			c.setString(10, syu_cpdate+"-01");
			c.setString(11, syu_opdate+"-01");
			c.setString(12, house_cpdate+"-01");
			c.setString(13, house_opdate+"-01");
			c.setString(14, fw_date+"-01");
			c.setString(15, file_date+"-01");
			c.setString(16, other_date+"-01");
			c.registerOutParameter(17, java.sql.Types.INTEGER);
			c.execute();
			return c.getInt(17);

		} catch (SQLException e) {
			return 1;
		}
	}
	
	// 委托单二次确认
		public int ecdiaut(String ecoc_id, String yl_cpdate, String yl_opdate,
				String sye_cpdate, String sye_opdate, String gs_cpdate,
				String gs_opdate, String jl_cpdate, String jl_opdate,
				String syu_cpdate, String syu_opdate, String house_cpdate,
				String house_opdate, String fw_date, String file_date,
				String other_date) {
			try {
				System.out.println("aaaa");
				System.out.println(ecoc_id);
				System.out.println( yl_cpdate);
				System.out.println(yl_opdate);
				System.out.println(sye_cpdate);
				System.out.println( sye_opdate);
				System.out.println( gs_cpdate);
				System.out.println( gs_opdate);
				System.out.println( jl_cpdate);
				System.out.println(jl_opdate);
				System.out.println( syu_cpdate);
				System.out.println( syu_opdate);
				System.out.println(house_cpdate);
				System.out.println(house_opdate);
				System.out.println( fw_date);
				System.out.println(file_date);
				System.out.println(other_date);

				CallableStatement c = conn
						.getcall("EmCommissionOut_NewAllEcDiAutUpdate_P_zzq(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)");

				c.setString(1, ecoc_id);
				c.setString(2, yl_cpdate+"-01");
				c.setString(3, yl_opdate+"-01");
				c.setString(4, sye_cpdate+"-01");
				c.setString(5, sye_opdate+"-01");
				c.setString(6, gs_cpdate+"-01");
				c.setString(7, gs_opdate+"-01");
				c.setString(8, jl_cpdate+"-01");
				c.setString(9, jl_opdate+"-01");
				c.setString(10, syu_cpdate+"-01");
				c.setString(11, syu_opdate+"-01");
				c.setString(12, house_cpdate+"-01");
				c.setString(13, house_opdate+"-01");
				c.setString(14, fw_date+"-01");
				c.setString(15, file_date+"-01");
				c.setString(16, other_date+"-01");
				c.registerOutParameter(17, java.sql.Types.INTEGER);
				c.execute();
				return c.getInt(17);

			} catch (SQLException e) {
				return 1;
			}
		}
}
