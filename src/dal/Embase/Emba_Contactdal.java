package dal.Embase;

import Model.EmbaseModel;
import Model.Emcontactinfo;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.ArrayList;
import java.util.List;

import Conn.dbconn;

public class Emba_Contactdal {

	/**
	 * 修改基本信息表
	 * 
	 * @param em
	 * @return 受影响行数
	 */
	public int updateEmbaInfo(Emcontactinfo em) {
		dbconn db = new dbconn();
		int row = 0;
		String sql = " UPDATE embase SET emba_degree = ?,emba_folk=?,emba_email=?,emba_computerid=?,emba_hand=?,emba_houseid=? WHERE gid = ? ";
		Object[] objs = { em.getEmzt_education(), em.getEmzt_folk(),
				em.getEmzt_email(), em.getEmzt_computerid(), em.getEmzt_hand(),
				em.getEmzt_houseid(), em.getGid() };
		try {
			row = db.updatePrepareSQL(sql, objs);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return row;
	}
	
	/**
	 * 修改基本信息表
	 * 
	 * @param em
	 * @return 受影响行数
	 */
	public int updateEmcontent(Emcontactinfo em) {
		dbconn db = new dbconn();
		int row = 0;
		String sql = " UPDATE Emcontactinfo SET  emzt_contactstateweb = ?  WHERE gid = ? ";
		Object[] objs = { em.getEmzt_contactstateweb(),
				 em.getGid() };
		try {
			row = db.updatePrepareSQL(sql, objs);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return row;
	}


	// 获取listModel
	@SuppressWarnings("null")
	public Emcontactinfo getemcontactmodel(Integer gid) {

		Emcontactinfo m = new Emcontactinfo();
		dbconn db = new dbconn();
		String sql = "select * from Emcontactinfo where gid=" + gid + "";
		m.setGid(gid);

		try {
			ResultSet rs = db.GRS(sql);
			while (rs.next()) {
				m.setId(rs.getInt("id"));
				m.setEmzt_email(rs.getString("emzt_email"));
				m.setEmzt_t_name(rs.getString("emzt_t_name"));
				m.setEmzt_t_idcard(rs.getString("emzt_t_idcard"));
				m.setEmzt_hjadd(rs.getString("emzt_hjadd"));
				m.setEmzt_education(rs.getString("emzt_education"));
				m.setEmzt_folk(rs.getString("emzt_folk"));
				m.setEmzt_hand(rs.getString("emzt_hand"));
				m.setEmzt_ifshebao(rs.getString("emzt_ifshebao"));
				m.setEmzt_computerid(rs.getString("emzt_computerid"));
				m.setEmzt_ifsbcard(rs.getString("emzt_ifsbcard"));
				m.setEmzt_ifhouse(rs.getString("emzt_ifhouse"));
				m.setEmzt_houseid(rs.getString("emzt_houseid"));
				m.setEmzt_marital(rs.getString("emzt_marital"));
				m.setEmzt_m_name(rs.getString("emzt_m_name"));
				m.setEmzt_m_idcard(rs.getString("emzt_m_idcard"));
				m.setEmzt_fileplace(rs.getString("emzt_fileplace"));
				m.setEmzt_ofileplace(rs.getString("emzt_ofileplace"));
				m.setEmzt_ifda(rs.getString("emzt_ifda"));
				m.setEmzt_ifowed(rs.getString("emzt_ifowed"));
				m.setEmzt_fileendmonth(rs.getInt("emzt_fileendmonth"));
				m.setEmzt_ifrc(rs.getString("emzt_ifrc"));
				m.setEmzt_iffileservice(rs.getString("emzt_iffileservice"));
				m.setEmzt_iffilechange(rs.getString("emzt_iffilechange"));
				m.setEmzt_nifc_reason(rs.getString("emzt_nifc_reason"));
				m.setEmzt_ifhouseseal(rs.getString("emzt_ifhouseseal"));
				m.setEmzt_contactstate(rs.getInt("emzt_contactstate"));
				m.setEmzt_datastate(rs.getInt("emzt_datastate"));
				m.setEmzt_contacttype(rs.getString("emzt_contacttype"));
				m.setEmzt_sbc_notice(rs.getString("emzt_sbc_notice"));
				m.setEmzt_data_notice(rs.getString("emzt_data_notice"));
				// m.setEmzt_wtgid(rs.getString("emzt_wtgid"));
				m.setEmzt_title(rs.getString("emzt_title"));
				// m.setEmzt_adtype(rs.getString("emzt_adtype"));
				// m.setEmzt_tel(rs.getString("emzt_tel"));
				// m.setEmzt_wtcid(rs.getString("emzt_wtcid"));
				// m.setEmzt_outreason(rs.getString("emzt_outreason"));
				m.setEmzt_sex(rs.getString("emzt_sex"));
				// m.setEmzt_sb_state(rs.getString("emzt_sb_state"));
				// m.setEmzt_house_state(rs.getString("emzt_house_state"));
				// m.setEmzt_iffeefile(rs.getString("emzt_iffeefile"));
				m.setEmzt_r_record(rs.getString("emzt_r_record"));
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		return m;

	}

	// 插入或修改联系记录
	public Integer addembacontactinfo(Emcontactinfo em) {
		Integer i = 0;
		dbconn db = new dbconn();

		System.out.println(em.getGid());
		System.out.println(em.getEmzt_t_name());
		System.out.println(em.getEmzt_t_idcard());
		System.out.println(em.getEmzt_hjadd());
		System.out.println(em.getEmzt_education());
		System.out.println(em.getEmzt_folk());
		System.out.println(em.getEmzt_hand());
		System.out.println(em.getEmzt_email());
		System.out.println(em.getEmzt_ifshebao());
		System.out.println(em.getEmzt_computerid());
		System.out.println(em.getEmzt_ifsbcard());
		System.out.println(em.getEmzt_ifhouse());
		System.out.println(em.getEmzt_houseid());
		System.out.println(em.getEmzt_marital());
		System.out.println(em.getEmzt_m_name());
		System.out.println(em.getEmzt_m_idcard());
		System.out.println(em.getEmzt_fileplace());
		System.out.println(em.getEmzt_ofileplace());
		System.out.println(em.getEmzt_ifda());
		System.out.println(em.getEmzt_ifowed());
		System.out.println(em.getEmzt_fileendmonth());
		System.out.println(em.getEmzt_ifrc());
		System.out.println(em.getEmzt_iffileservice());
		System.out.println(em.getEmzt_iffilechange());
		System.out.println(em.getEmzt_nifc_reason());
		System.out.println(em.getEmzt_ifhouseseal());
		System.out.println(em.getEmzt_contactstate());
		System.out.println(em.getEmzt_datastate());
		System.out.println(em.getEmzt_contacttype());
		System.out.println(em.getEmzt_sbc_notice());
		System.out.println(em.getEmzt_data_notice());
		System.out.println(em.getEmzt_title());
		System.out.println(em.getEmzt_sex());
		System.out.println(em.getEmzt_r_record());

		try {
			i = Integer.valueOf(db.callWithReturn(

					"{?=call Emcontactinfo_ADD_p_zmj(?,?,?,?,?,?,?,?,?,"
							+ "?,?,?,?,?,?,?,?,?,?,?,?,?"
							+ ",?,?,?,?,?,?,?,?,?,?,?,?,?)}", Types.INTEGER,
					em.getGid(), em.getEmzt_t_name(), em.getEmzt_t_idcard(),
					em.getEmzt_hjadd(), em.getEmzt_education(),
					em.getEmzt_folk(), em.getEmzt_hand(), em.getEmzt_email(),
					em.getEmzt_ifshebao(), em.getEmzt_computerid(),
					em.getEmzt_ifsbcard(), em.getEmzt_ifhouse(),
					em.getEmzt_houseid(), em.getEmzt_marital(),
					em.getEmzt_m_name(), em.getEmzt_m_idcard(),
					em.getEmzt_fileplace(), em.getEmzt_ofileplace(),
					em.getEmzt_ifda(), em.getEmzt_ifowed(),
					em.getEmzt_fileendmonth(), em.getEmzt_ifrc(),
					em.getEmzt_iffileservice(), em.getEmzt_iffilechange(),
					em.getEmzt_nifc_reason(), em.getEmzt_ifhouseseal(),
					em.getEmzt_contactstate(), em.getEmzt_datastate(),
					em.getEmzt_contacttype(), em.getEmzt_sbc_notice(),
					em.getEmzt_data_notice(), em.getEmzt_title(),
					em.getEmzt_sex(), em.getEmzt_r_record(), 0).toString());

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return i;
	}

}
