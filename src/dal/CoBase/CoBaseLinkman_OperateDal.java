package dal.CoBase;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import Conn.dbconn;
import Model.CoAgencyLinkmanModel;
import Model.CoBaseLinkFamilyModel;

public class CoBaseLinkman_OperateDal {
	private dbconn conn = new dbconn();

	// 新增公司联系人信息
	public int AddCoBaseLinkman(CoAgencyLinkmanModel m, int cid) {
		try {
			CallableStatement c = conn
					.getcall("CoBaseLinkman_Add_p_lwj(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)");
			c.setInt(1, cid);
			c.setString(2, m.getCali_name());
			c.setString(3, m.getCali_ename());
			c.setString(4, m.getCali_sex());
			c.setString(5, m.getCali_job());
			c.setString(6, m.getCali_duty());
			c.setString(7, m.getCali_tel());
			c.setString(8, m.getCali_tel1());
			c.setString(9, m.getCali_mobile());

			c.setString(10, m.getCali_email());
			c.setString(11, m.getCali_email1());
			c.setString(12, m.getCali_fax());
			c.setString(13, m.getCali_weibo());
			c.setString(14, m.getCali_weixin());
			c.setString(15, m.getCali_qq());
			c.setString(16, m.getCali_address());
			c.setString(17, m.getCali_remark());
			c.setString(18, m.getCali_nickname());
			c.setString(19, m.getCali_birth());

			c.setString(20, m.getCali_folk());
			c.setString(21, m.getCali_height());
			c.setString(22, m.getCali_figure());
			c.setString(23, m.getCali_character());
			c.setString(24, m.getCali_origo());
			c.setString(25, m.getCali_hjaddress());
			c.setString(26, m.getCali_degree());
			c.setString(27, m.getCali_schoolcity());
			c.setString(28, m.getCali_specialty());
			c.setString(29, m.getCali_school());

			c.setString(30, m.getCali_marriage());
			c.setString(31, m.getCali_lastindustry());
			c.setString(32, m.getCali_lastworktime());
			c.setString(33, m.getCali_lastcompany());
			c.setString(34, m.getCali_lastjob());
			c.setString(35, m.getCali_lastcompanyAddress());
			c.setString(36, m.getCali_developmentPlan());
			c.setString(37, m.getCali_hobby());
			c.setString(38, m.getCali_hobbyActivities());
			c.setString(39, m.getCali_religiousBelief());

			c.setString(40, m.getCali_hobbyClub());
			c.setString(41, m.getCali_communityActivities());
			c.setString(42, m.getCali_conversationTopics());
			c.setString(43, m.getCali_hobbyFood());
			c.setString(44, m.getCali_diet());
			c.setString(45, m.getCali_ifOpInvitationMeals());
			c.setString(46, m.getCali_ifOpSengGift());
			c.setString(47, m.getCali_notTalkAbout());
			c.setString(48, m.getCali_attentionProblem());
			c.setString(49, m.getCali_addname());
			c.setInt(50, m.getCali_vip());
			c.setString(51, m.getCali_birthYearStr());
			c.setString(52, m.getCali_birthMonthDayStr());
			c.registerOutParameter(53, java.sql.Types.INTEGER);
			c.execute();
			return c.getInt(53);
		} catch (SQLException e) {
			return 0;
		}
	}

	// 新增公司联系人家属信息
	public void AddCoBaseLinkmanFamily(CoBaseLinkFamilyModel m, int cali_id) {
		try {
			CallableStatement c = conn
					.getcall("CoBaseLinkmanFamily_Add_p_lwj(?,?,?,?,?,?,?,?,?,?,?,?)");
			c.setInt(1, cali_id);
			c.setString(2, m.getCblf_relationship());
			c.setString(3, m.getCblf_name());
			c.setString(4, m.getCblf_birth());
			c.setString(5, m.getCblf_occupation());
			c.setString(6, m.getCblf_company());
			c.setString(7, m.getCblf_hobby());
			c.setString(8, m.getCblf_remark());
			c.setString(9, m.getCblf_addname());
			c.setString(10, m.getCblf_sex());
			c.setString(11, m.getCblf_birthYearStr());
			c.setString(12, m.getCblf_birthMonthDayStr());
			c.execute();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	// 更新公司联系人基本信息
	public int UpCoBaseLinkman(CoAgencyLinkmanModel m) {
		try {
			CallableStatement c = conn
					.getcall("CoBaseLinkman_Up_p_lwj(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)");
			c.setInt(1, m.getCali_id());
			c.setString(2, m.getCali_name());
			c.setString(3, m.getCali_ename());
			c.setString(4, m.getCali_sex());
			c.setString(5, m.getCali_job());
			c.setString(6, m.getCali_duty());
			c.setString(7, m.getCali_tel());
			c.setString(8, m.getCali_tel1());
			c.setString(9, m.getCali_mobile());

			c.setString(10, m.getCali_email());
			c.setString(11, m.getCali_email1());
			c.setString(12, m.getCali_fax());
			c.setString(13, m.getCali_weibo());
			c.setString(14, m.getCali_weixin());
			c.setString(15, m.getCali_qq());
			c.setString(16, m.getCali_address());
			c.setString(17, m.getCali_remark());
			c.setString(18, m.getCali_nickname());
			c.setString(19, m.getCali_birth());

			c.setString(20, m.getCali_folk());
			c.setString(21, m.getCali_height());
			c.setString(22, m.getCali_figure());
			c.setString(23, m.getCali_character());
			c.setString(24, m.getCali_origo());
			c.setString(25, m.getCali_hjaddress());
			c.setString(26, m.getCali_degree());
			c.setString(27, m.getCali_schoolcity());
			c.setString(28, m.getCali_specialty());
			c.setString(29, m.getCali_school());

			c.setString(30, m.getCali_marriage());
			c.setString(31, m.getCali_lastindustry());
			c.setString(32, m.getCali_lastworktime());
			c.setString(33, m.getCali_lastcompany());
			c.setString(34, m.getCali_lastjob());
			c.setString(35, m.getCali_lastcompanyAddress());
			c.setString(36, m.getCali_developmentPlan());
			c.setString(37, m.getCali_hobby());
			c.setString(38, m.getCali_hobbyActivities());
			c.setString(39, m.getCali_religiousBelief());

			c.setString(40, m.getCali_hobbyClub());
			c.setString(41, m.getCali_communityActivities());
			c.setString(42, m.getCali_conversationTopics());
			c.setString(43, m.getCali_hobbyFood());
			c.setString(44, m.getCali_diet());
			c.setString(45, m.getCali_ifOpInvitationMeals());
			c.setString(46, m.getCali_ifOpSengGift());
			c.setString(47, m.getCali_notTalkAbout());
			c.setString(48, m.getCali_attentionProblem());
			c.setString(49, m.getCali_addname());
			c.setInt(50, m.getCali_vip());
			c.setString(51, m.getCali_birthYearStr());
			c.setString(52, m.getCali_birthMonthDayStr());
			c.registerOutParameter(53, java.sql.Types.INTEGER);
			c.execute();
			return c.getInt(53);
		} catch (SQLException e) {
			return 1;
		}
	}

	// 删除公司联系人信息
	public int DelCobaseLinkman(int cid, int cali_id, String reason,
			String uesrname) {
		try {
			CallableStatement c = conn
					.getcall("CoBaseLinkmanDel_p_lwj(?,?,?,?,?)");
			c.setInt(1, cid);
			c.setInt(2, cali_id);
			c.setString(3, reason);
			c.setString(4, uesrname);
			c.registerOutParameter(5, java.sql.Types.INTEGER);
			c.execute();
			return c.getInt(5);
		} catch (SQLException e) {
			return 1;
		}
	}

	// 删除联系人家属信息
	public void DelCoBaseLinkmanFamily(int cali_id) {
		try {
			CallableStatement c = conn
					.getcall("CoBaseLinkmanFamily_Del_p_lwj(?)");
			c.setInt(1, cali_id);
			c.execute();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	// 检测是否已有该联系人
	public boolean existLinkman(int cid, String cali_name) {
		ResultSet rs = null;
		String sql = "select COUNT(cblr_id) as count from CoBaseLinkRel cr left join CoAgencyLinkman cl on cr.cblr_cali_id=cl.cali_id  where cr.cid=? and cl.cali_name=? and cr.cblr_state=1 and cl.cali_state=1";
		PreparedStatement psmt = conn.getpre(sql);
		try {
			psmt.setInt(1, cid);
			psmt.setString(2, cali_name);
			rs = psmt.executeQuery();
			rs.next();
			if (rs.getInt("count") == 0) {
				return true;
			} else {
				return false;
			}
		} catch (SQLException e) {
			return false;
		}
	}

}
