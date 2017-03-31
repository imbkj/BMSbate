/**
 * @Classname CoBase_OperateDal
 * @ClassInfo 公司基本信息数据库修改处理类
 * @author 林少斌、陈耀家
 * @Date 2013-11-27
 */
package dal.CoBase;

import java.sql.CallableStatement;
import java.sql.SQLException;

import Conn.dbconn;
import Controller.systemWindowController;
import Model.CoBaseModel;
import Util.UserInfo;

public class CoBase_OperateDal {
	private dbconn conn = new dbconn();

	// 修改公司注册信息
	public int updateCoBaseReg(String username, CoBaseModel m) {
		try {
			CallableStatement c = conn
					.getcall("CoBaseRegUpdate_P_lsb(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)");
			c.setInt(1, m.getCid());
			c.setString(2, m.getCoba_regagent());
			c.setString(3, m.getCoba_companymanager());
			c.setString(4, m.getCoba_address());
			c.setString(5, m.getCoba_certificate());
			c.setString(6, m.getCoba_bregid());
			c.setString(7, m.getCoba_regexpires());
			c.setString(8, m.getCoba_regtime());
			c.setString(9, m.getCoba_reglimit());
			c.setString(10, m.getCoba_organcode());
			c.setString(11, m.getCoba_orregtime());
			c.setString(12, m.getCoba_organdeadline());
			c.setString(13, m.getCoba_ntid());
			c.setString(14, m.getCoba_ntregtime());
			c.setString(15, m.getCoba_ntlimit());
			c.setString(16, m.getCoba_ntbank());
			c.setString(17, m.getCoba_ntaccount());
			c.setString(18, m.getCoba_ntdeadline());
			c.setString(19, m.getCoba_ltregid());
			c.setString(20, m.getCoba_ltregtime());
			c.setString(21, m.getCoba_ltlimit());
			c.setString(22, m.getCoba_ltstate());
			c.setString(23, m.getCoba_ltid());
			c.setString(24, m.getCoba_ltdeadline());
			c.setString(25, m.getCoba_ltbank());
			c.setString(26, m.getCoba_ltaccount());
			c.setString(27, m.getCoba_gtstate());
			c.setString(28, m.getCoba_gtbank());
			c.setString(29, m.getCoba_gtacc());
			c.setString(30, m.getCoba_gtto());
			c.setString(31, m.getCoba_gtdeadline());
			c.setInt(32, m.getCoba_gzautoemail());
			c.setInt(33, m.getCoba_gzautoemaildays());
			c.setInt(34, m.getCoba_gzemailtype());
			c.setString(35, m.getCoba_ufclass());
			c.setString(36, m.getCoba_ufid());
			c.setString(37, m.getCoba_ufid2());
			c.setString(38, m.getCoba_sihospital());
			c.setString(39, m.getCoba_sihosphone());
			c.setString(40, m.getCoba_sihosadd());
			c.setString(41, m.getCoba_regremark());
			c.registerOutParameter(42, java.sql.Types.INTEGER);
			c.execute();

			return c.getInt(42);
		} catch (SQLException e) {
			return 1;
		}
	}

	// 修改公司财务信息
	public int updateCoBaseCS(String username, CoBaseModel m) {
		try {
			CallableStatement c = conn
					.getcall("CoBaseCSUpdate_P_lsb(?,?,?,?,?,?,?,?,?,?,?)");
			c.setInt(1, m.getCid());
			c.setString(2, m.getCoba_emfi_paydate());
			c.setString(3, m.getCoba_emfi_backdate());
			c.setString(4, m.getCoba_gz_paydate());
			c.setString(5, m.getCoba_emailgz_paydate());
			c.setString(6, m.getCoba_papergz_paydate());
			c.setString(7, m.getCoba_emfics_deldate());
			c.setString(8, m.getCoba_emfics_paydate());
			c.setString(9, m.getCoba_emfics_backdate());
			c.setString(10, m.getCoba_invoicerule());
			c.registerOutParameter(11, java.sql.Types.INTEGER);
			c.execute();

			return c.getInt(11);

		} catch (SQLException e) {
			return 1;
		}
	}

	// 新增公司信息
	public int addCoBaseInfo(CoBaseModel m) {
		try {
			CallableStatement c = conn
					.getcall("CoBase_Add_cyj(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)");
			c.setString(1, m.getCoba_company());
			c.setString(2, m.getCoba_shortname());
			c.setString(3, m.getCoba_address());
			c.setString(4, m.getCoba_englishname());
			c.setString(5, m.getCoba_clientsource());
			c.setString(6, m.getCoba_wtco());
			c.setString(7, m.getCoba_clientsize());
			c.setString(8, m.getCoba_industytype());
			c.setString(9, m.getCoba_setuptype());
			c.setString(10, m.getCoba_area());
			c.setString(11, m.getCoba_reg_fund());
			c.setString(12, m.getCoba_manageraddress());
			c.setString(13, m.getCoba_invoiceaddress());
			c.setString(14, m.getCoba_compacttype());
			c.setString(15, m.getCoba_servicearea());
			c.setString(16, m.getCoba_client());
			c.setString(17, m.getCoba_manager());
			c.setString(18, m.getCoba_clientmanager());
			c.setString(19, m.getCoba_hzqsr());
			c.setString(20, m.getCoba_remark());
			c.setString(21, m.getCoba_addname());
			c.setString(22, m.getCoba_website());
			c.setString(23, m.getCoba_kind());
			c.registerOutParameter(24, java.sql.Types.INTEGER);
			c.registerOutParameter(25, java.sql.Types.INTEGER);
			c.execute();
			return c.getInt(25);
		} catch (SQLException e) {
			return -1;
		}
	}

	// 修改公司信息
	public int updateCoBaseInfo(CoBaseModel m) {
		try {
			CallableStatement c = conn
					.getcall("CoBase_Update_cyj(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)");
			c.setString(1, m.getCoba_company());
			c.setString(2, m.getCoba_shortname());
			c.setString(3, m.getCoba_address());
			c.setString(4, m.getCoba_englishname());
			c.setString(5, m.getCoba_clientsource());
			c.setInt(6, m.getCoba_sign());
			c.setString(7, m.getCoba_clientsize());
			c.setString(8, m.getCoba_industytype());
			c.setString(9, m.getCoba_setuptype());// 公司类型
			c.setString(10, m.getCoba_area());
			c.setString(11, m.getCoba_reg_fund());
			c.setString(12, m.getCoba_invoiceaddress());
			c.setString(13, m.getCoba_servicearea());
			c.setString(14, m.getCoba_client());
			c.setString(15, m.getCoba_manager());
			c.setString(16, m.getCoba_clientmanager());
			c.setString(17, m.getCoba_developer());
			c.setString(18, m.getCoba_hzqsr());
			c.setString(19, m.getCoba_remark());
			c.setString(20, m.getCoba_addname());
			c.setInt(21, m.getCid());
			c.setString(22, m.getCoba_website());
			c.setString(23, m.getCoba_spell());
			c.setString(24, m.getCoba_shortspell());
			c.setString(25, m.getCoba_namespell());
			c.setString(26, m.getCoba_kind());
			c.setString(27, m.getCoba_ifhasbribery());
			c.setString(28, m.getCoba_fpremark());

			c.registerOutParameter(29, java.sql.Types.INTEGER);
			c.execute();
			return c.getInt(29);
		} catch (SQLException e) {
			return -1;
		}
	}

	public Integer modCobase(Integer id, Integer cid, CoBaseModel cm) {
		dbconn db = new dbconn();
		Integer i = 0;
		String sql = "update cobase set coba_modtime=getdate(),coba_modname='"
				+ UserInfo.getUsername() + "'";

		if (cm.getCoba_invoicerule() != null) {
			sql = sql + ",coba_invoicerule='" + cm.getCoba_invoicerule() + "'";
		}

		sql = sql + " where 1=1";
		if ((id != null && id > 0) || (cid != null && cid > 0)) {
			if (id != null && id > 0) {
				sql = sql + " and coba_id=" + id;
			}
			if (cid != null && cid > 0) {
				sql = sql + " and cid=" + cid;
			}

			System.out.println(sql);
			try {
				i = db.updatePrepareSQL(sql);
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

		return i;
	}

	public Integer stopCobase(Integer cid, String stopdate, String stopreason,
			String stopRemark, String modname) {
		Integer i = 0;
		dbconn db = new dbconn();
		String sql = "update cobase set coba_servicestate=0,coba_stoptime=?,coba_stopreason=?,coba_stopremark=?,coba_modname=?,coba_modtime=getdate()"
				+ " where cid=?";
		try {
			i=db.updatePrepareSQL(sql, stopdate,stopreason,stopRemark,modname,cid);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return i;
	}
}
