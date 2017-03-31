package dal.CoAgency;

import java.math.BigDecimal;
import java.sql.CallableStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import Conn.dbconn;
import Model.CoAgencyBaseModel;
import Model.CoAgencyBaseServiceModel;
import Model.CoAgencyLinkmanModel;

public class CoAgencyBaseOperateDal {
	private dbconn conn = new dbconn();

	// 委托机构基本信息新增
	public int AddBase(CoAgencyBaseModel m) {
		try {
			CallableStatement c = conn
					.getcall("CoAgencyBaseAdd_p_lwj(?,?,?,?,?,?,?,?,?,?,?,?,?,?)");
			c.setString(1, m.getCoab_name());
			c.setString(2, m.getCoab_namespell());
			c.setString(3, m.getCoab_province());
			c.setString(4, m.getCoab_city());
			c.setString(5, m.getCoab_setuptype());
			c.setString(6, m.getCoab_source());
			c.setString(7, m.getCoab_capital());
			c.setString(8, m.getCoab_regaddress());
			c.setString(9, m.getCoab_companymanager());
			c.setString(10, m.getCoab_business());
			c.setString(11, m.getCoab_remark());
			c.setString(12, m.getCoab_addname());
			c.setString(13, m.getCoab_shortname());
			c.registerOutParameter(14, java.sql.Types.INTEGER);
			c.execute();
			return c.getInt(14);
		} catch (SQLException e) {
			return 0;
		}
	}

	// 新增机构服务约定
	public void AddBaseService(CoAgencyBaseServiceModel m) {
		try {
			CallableStatement c = conn
					.getcall("CoAgencyBaseServiceAdd_p_lwj(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)");
			c.setInt(1, m.getCoas_coab_id());
			c.setInt(2, m.getCoas_type());
			c.setInt(3, m.getCoas_cabc_id());
			c.setString(4, m.getCoas_coaddress());
			c.setString(5, m.getCoas_zipcode());
			c.setString(6, m.getCoas_accountBank());
			c.setString(7, m.getCoas_accountName());
			c.setString(8, m.getCoas_accountNum());
			c.setString(9, m.getCoas_wtConfirReq());
			c.setString(10, m.getCoas_wtFeedbackReq());
			c.setInt(11, m.getCoas_billDay());
			c.setInt(12, m.getCoas_payday());
			c.setString(13, m.getCoas_paymon());
			c.setString(14, m.getCoas_payMethods());
			c.setString(15, m.getCoas_ifSendInvoice());
			c.setString(16, m.getCoas_invoiceAdd());
			c.setString(17, m.getCoas_invoiceLinkman());
			c.setString(18, m.getCoas_hz());
			c.setString(19, m.getCoas_remark());
			c.setString(20, m.getCoas_client());
			c.setString(21, m.getCoas_addname());
			
			c.setString(22, m.getCoas_chstate());
			c.setInt(23, m.getCoas_zjday());
			c.setInt(24, m.getCoas_zyday());
			c.setInt(25, m.getCoas_jyday());
			c.setString(26, m.getCoas_autstate());
			c.setString(27, m.getCoas_autmon());
			c.setString(28, m.getCoas_zyremark());
			c.setString(29, m.getCoas_jyremark());
			c.execute();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	// 机构联系人信息新增
	public int AddLinkman(int coab_id, CoAgencyLinkmanModel m, int type) {
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
			c.setInt(17, type);
			c.setInt(18, m.getCabc_id());
			c.registerOutParameter(19, java.sql.Types.INTEGER);
			c.execute();
			return c.getInt(19);
		} catch (SQLException e) {
			e.printStackTrace();
			return 0;
		}
	}

	// 分配城市与机构关系
	public int DisBase(int coab_id, String city, int cabc_ifdefault,
			BigDecimal cabc_fee, String addname) {
		try {
			CallableStatement c = conn
					.getcall("CoAgencyBaseDis_P_lwj(?,?,?,?,?,?)");
			c.setInt(1, coab_id);
			c.setString(2, city);
			c.setInt(3, cabc_ifdefault);
			c.setBigDecimal(4, cabc_fee);
			c.setString(5, addname);
			c.registerOutParameter(6, java.sql.Types.INTEGER);
			c.execute();
			return c.getInt(6);
		} catch (SQLException e) {
			e.printStackTrace();
			return 0;
		}
	}

	// 检查机构是否存在
	public boolean existBase(String name) throws Exception {
		ResultSet rs = null;
		StringBuilder sql = new StringBuilder();
		sql.append("SELECT count(coab_id) as count FROM CoAgencyBase where coab_state=1 and coab_name='");
		sql.append(name);
		sql.append("'");
		rs = conn.GRS(sql.toString());
		rs.next();
		if (rs.getInt("count") > 0) {
			return true;
		} else {
			return false;
		}

	}

	// 检查受托机构是否存在
	public boolean existBaseService(String name) throws Exception {
		ResultSet rs = null;
		StringBuilder sql = new StringBuilder();
		sql.append("select count(coas_id) as count from CoAgencyBaseService where coas_type=2 and coas_coab_id=(SELECT coab_id FROM CoAgencyBase where coab_state=1 and coab_name='");
		sql.append(name);
		sql.append("')");
		rs = conn.GRS(sql.toString());
		rs.next();
		if (rs.getInt("count") > 0) {
			return true;
		} else {
			return false;
		}

	}

	// 取消城市与机构的分配关系
	public int DelDisBasefromCity(int coab_id, String city, String addname) {
		try {
			CallableStatement c = conn
					.getcall("CoAgencyBaseDelDis_P_lwj(?,?,?,?)");
			c.setInt(1, coab_id);
			c.setString(2, city);
			c.setString(3, addname);
			c.registerOutParameter(4, java.sql.Types.INTEGER);
			c.execute();
			return c.getInt(4);
		} catch (SQLException e) {
			return 0;
		}
	}

	// 设置城市默认机构
	public int SetDefaultAgency(int cabc_id) {
		try {
			CallableStatement c = conn
					.getcall("CoAgencyBase_SetDefault_P_lwj(?,?)");
			c.setInt(1, cabc_id);
			c.registerOutParameter(2, java.sql.Types.INTEGER);
			c.execute();
			return c.getInt(2);
		} catch (SQLException e) {
			e.printStackTrace();
			return 0;
		}
	}

	// 修改机构基本信息
	public int UpBase(CoAgencyBaseModel m) {
		try {
			CallableStatement c = conn
					.getcall("CoAgencyBaseUp_p_lwj(?,?,?,?,?,?,?,?,?,?,?,?,?)");
			c.setInt(1, m.getCoab_id());
			c.setString(2, m.getCoab_province());
			c.setString(3, m.getCoab_city());
			c.setString(4, m.getCoab_setuptype());
			c.setString(5, m.getCoab_source());
			c.setString(6, m.getCoab_capital());
			c.setString(7, m.getCoab_regaddress());
			c.setString(8, m.getCoab_companymanager());
			c.setString(9, m.getCoab_business());
			c.setString(10, m.getCoab_remark());
			c.setString(11, m.getCoab_addname());
			c.setString(12, m.getCoab_shortname());
			c.registerOutParameter(13, java.sql.Types.INTEGER);
			c.execute();
			return c.getInt(13);
		} catch (SQLException e) {
			e.printStackTrace();
			return 0;
		}
	}

	// 修改机构服务约定
	public void UpBaseService(CoAgencyBaseServiceModel m) {
		try {
			CallableStatement c = conn
					.getcall("CoAgencyBaseServiceUp_p_lwj(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)");
			c.setInt(1, m.getCoas_id());
			c.setString(2, m.getCoas_accountBank());
			c.setString(3, m.getCoas_accountName());
			c.setString(4, m.getCoas_accountNum());
			c.setString(5, m.getCoas_wtConfirReq());
			c.setString(6, m.getCoas_wtFeedbackReq());
			c.setInt(7, m.getCoas_billDay());
			c.setInt(8, m.getCoas_payday());
			c.setString(9, m.getCoas_paymon());
			c.setString(10, m.getCoas_payMethods());
			c.setString(11, m.getCoas_ifSendInvoice());
			c.setString(12, m.getCoas_invoiceAdd());
			c.setString(13, m.getCoas_invoiceLinkman());
			c.setString(14, m.getCoas_hz());
			c.setString(15, m.getCoas_remark());
			c.setString(16, m.getCoas_client());
			c.setString(17, m.getCoas_addname());
			c.setString(18, m.getCoas_coaddress());
			c.setString(19, m.getCoas_zipcode());
			
			c.setString(20, m.getCoas_chstate());
			c.setInt(21, m.getCoas_zjday());
			c.setInt(22, m.getCoas_zyday());
			c.setInt(23, m.getCoas_jyday());
			c.setString(24, m.getCoas_autstate());
			c.setString(25, m.getCoas_autmon());
			c.setString(26, m.getCoas_zyremark());
			c.setString(27, m.getCoas_jyremark());
			c.execute();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

}