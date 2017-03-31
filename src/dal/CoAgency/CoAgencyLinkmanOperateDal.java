package dal.CoAgency;

import java.sql.CallableStatement;
import java.sql.SQLException;
import Conn.dbconn;
import Model.CoAgencyLinkmanModel;

public class CoAgencyLinkmanOperateDal {
	private dbconn conn = new dbconn();

	// 委托机构联系人信息新增
	public int AddLinkman(int coab_id, CoAgencyLinkmanModel m) {
		try {
			CallableStatement c = conn
					.getcall("CoAgencyLinkmanAdd_P_lwj(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)");
			c.setInt(1, coab_id);
			c.setString(2, m.getCali_linkman());
			c.setString(3, m.getCali_name());
			c.setString(4, m.getCali_ename());
			c.setString(5, m.getCali_job());
			c.setString(6, m.getCali_tel());
			c.setString(7, m.getCali_mobile());
			c.setString(8, m.getCali_fax());
			c.setString(9, m.getCali_email());
			c.setString(10, m.getCali_birth());
			c.setString(11, m.getCali_hobby());
			c.setString(12, m.getCali_address());
			c.setString(13, m.getCali_personality());
			c.setString(14, m.getCali_remark());
			c.setInt(15, m.getCali_vip());
			c.setString(16, m.getCali_addname());
			c.setInt(17, m.getType());
			c.setInt(18, m.getCabc_id());
			c.registerOutParameter(19, java.sql.Types.INTEGER);
			c.execute();
			return c.getInt(19);
		} catch (SQLException e) {
			return 0;
		}

	}

	// 委托机构联系人信息更新
	public int UpLinkman(CoAgencyLinkmanModel m) {
		try {
			CallableStatement c = conn
					.getcall("CoAgencyLinkmanUp_P_lwj(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)");
			c.setInt(1, m.getCali_id());
			c.setString(2, m.getCali_linkman());
			c.setString(3, m.getCali_name());
			c.setString(4, m.getCali_ename());
			c.setString(5, m.getCali_job());
			c.setString(6, m.getCali_tel());
			c.setString(7, m.getCali_mobile());
			c.setString(8, m.getCali_fax());
			c.setString(9, m.getCali_email());
			c.setString(10, m.getCali_birth());
			c.setString(11, m.getCali_hobby());
			c.setString(12, m.getCali_address());
			c.setString(13, m.getCali_personality());
			c.setString(14, m.getCali_remark());
			c.setInt(15, m.getCali_vip());
			c.setString(16, m.getCali_addname());
			c.registerOutParameter(17, java.sql.Types.INTEGER);
			c.execute();
			return c.getInt(17);
		} catch (SQLException e) {
			return 1;
		}

	}

	// 委托机构联系人信息删除
	public int DelLinkman(int coab_id, int cali_id, int cabc_id,
			String addname, String reason) {
		try {
			CallableStatement c = conn
					.getcall("CoAgencyLinkmanDel_P_lwj(?,?,?,?,?,?)");
			c.setInt(1, coab_id);
			c.setInt(2, cali_id);
			c.setInt(3, cabc_id);
			c.setString(4, addname);
			c.setString(5, reason);
			c.registerOutParameter(6, java.sql.Types.INTEGER);
			c.execute();
			return c.getInt(6);
		} catch (SQLException e) {
			return 1;
		}

	}
}
