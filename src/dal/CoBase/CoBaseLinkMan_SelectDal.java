/**
 * @Classname CoBaseLinkMan_SelectDal
 * @ClassInfo 公司联系人数据库显示类
 * @author 林少斌
 * @Date 2013-11-29
 *  */
package dal.CoBase;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import Conn.dbconn;
import Model.CoAgencyLinkmanModel;
import Model.CoBaseLinkFamilyModel;
import Util.DateStringChange;

public class CoBaseLinkMan_SelectDal {
	private dbconn conn = new dbconn();

	// 根据cali_id获取联系人基本信息数据集
	private ResultSet getLinkModelRs(int cali_id) throws SQLException {
		ResultSet rs = null;
		String sql = "SELECT *,birth=CONVERT(varchar(100), cali_birth, 20) FROM CoAgencyLinkman where cali_id=?";
		PreparedStatement psmt = conn.getpre(sql);
		psmt.setInt(1, cali_id);
		rs = psmt.executeQuery();
		return rs;
	}

	// 根据cali_id获取联系人基本信息
	public CoAgencyLinkmanModel getLinkModel(int cali_id) {
		CoAgencyLinkmanModel m = null;
		try {
			ResultSet rs = getLinkModelRs(cali_id);
			rs.next();
			m = new CoAgencyLinkmanModel(rs.getInt("cali_id"),
					rs.getString("cali_linkman"), rs.getString("cali_name"),
					rs.getString("cali_ename"), rs.getString("cali_job"),
					rs.getString("cali_tel"), rs.getString("cali_mobile"),
					rs.getString("cali_fax"), rs.getString("cali_email"),
					rs.getString("birth"), rs.getString("cali_hobby"),
					rs.getString("cali_address"),
					rs.getString("cali_personality"),
					rs.getString("cali_remark"), rs.getInt("cali_vip"),
					rs.getString("cali_addname"), rs.getString("cali_addtime"),
					rs.getString("cali_modname"), rs.getString("cali_modtime"),
					rs.getString("cali_delname"), rs.getString("cali_deltime"),
					rs.getString("cali_delReason"),
					rs.getString("cali_mobile1"), rs.getString("cali_mobile2"),
					rs.getString("cali_email1"), rs.getString("cali_email2"),
					rs.getString("cali_tel1"), rs.getString("cali_tel2"),
					rs.getString("cali_nickname"), rs.getString("cali_duty"),
					rs.getString("cali_sex"), rs.getString("cali_folk"),
					rs.getString("cali_origo"), rs.getString("cali_hjaddress"),
					rs.getString("cali_marriage"), rs.getString("cali_height"),
					rs.getString("cali_figure"),
					rs.getString("cali_character"), rs.getString("cali_weibo"),
					rs.getString("cali_weixin"), rs.getString("cali_qq"),
					rs.getString("cali_degree"), rs.getString("cali_school"),
					rs.getString("cali_schoolcity"),
					rs.getString("cali_specialty"),
					rs.getString("cali_lastindustry"),
					rs.getString("cali_lastworktime"),
					rs.getString("cali_lastjob"),
					rs.getString("cali_lastcompany"),
					rs.getString("cali_lastcompanyAddress"),
					rs.getString("cali_developmentPlan"),
					rs.getString("cali_hobbyActivities"),
					rs.getString("cali_hobbyClub"),
					rs.getString("cali_communityActivities"),
					rs.getString("cali_religiousBelief"),
					rs.getString("cali_conversationTopics"),
					rs.getString("cali_hobbyFood"), rs.getString("cali_diet"),
					rs.getString("cali_ifOpInvitationMeals"),
					rs.getString("cali_ifOpSengGift"),
					rs.getString("cali_notTalkAbout"),
					rs.getString("cali_attentionProblem"),
					rs.getString("birth") != null ? DateStringChange
							.StringtoDate(rs.getString("birth"), "yyyy-MM-dd")
							: null, rs.getString("cali_birthYear"),
					rs.getString("cali_birthMonthDay"));
		} catch (Exception e) {
			System.out.println(e.toString());
		}
		return m;
	}

	// 根据cali_id获取联系人家属信息数据集
	private ResultSet getLinkFamilyModelRs(int cali_id) throws SQLException {
		ResultSet rs = null;
		String sql = "SELECT *,birth=CONVERT(varchar(100), cblf_birth, 20) FROM CoBaseLinkFamily where cblf_cali_id=? and cblf_state=1";
		PreparedStatement psmt = conn.getpre(sql);
		psmt.setInt(1, cali_id);
		rs = psmt.executeQuery();
		return rs;
	}

	// 根据cali_id获取联系人家属信息
	public ArrayList<CoBaseLinkFamilyModel> getLinkFamilyModel(int cali_id) {
		ArrayList<CoBaseLinkFamilyModel> list = new ArrayList<CoBaseLinkFamilyModel>();
		try {
			ResultSet rs = getLinkFamilyModelRs(cali_id);
			while (rs.next()) {
				list.add(new CoBaseLinkFamilyModel(rs.getInt("cblf_id"), rs
						.getInt("cblf_cali_id"), rs
						.getString("cblf_relationship"), rs
						.getString("cblf_name"), rs.getString("birth"), rs
						.getString("birth") != null ? DateStringChange
						.StringtoDate(rs.getString("birth"), "yyyy-MM-dd")
						: null, rs.getString("cblf_occupation"), rs
						.getString("cblf_company"), rs.getString("cblf_hobby"),
						rs.getString("cblf_remark"), rs
								.getString("cblf_addname"), rs
								.getString("cblf_addtime"), rs
								.getInt("cblf_state"),
						rs.getString("cblf_sex"), rs
								.getString("cblf_birthYear"), rs
								.getString("cblf_birthMonthDay")));
			}
		} catch (Exception e) {
			System.out.println(e.toString());
		}
		return list;
	}

	// 根据cid获取联系人基本信息数据集
	private ResultSet getLinkModelByCidRs(int cid) throws SQLException {
		ResultSet rs = null;
		String sql = "select * from CoBaseLinkRel cr right join CoAgencyLinkman cl on cr.cblr_cali_id=cl.cali_id where cid=? order by cr.cblr_state desc,cl.cali_vip desc";
		PreparedStatement psmt = conn.getpre(sql);
		psmt.setInt(1, cid);
		rs = psmt.executeQuery();
		return rs;
	}

	// 根据cid获取联系人基本信息数据集
	private ResultSet getLinkModelByCidRs(int cid, int state)
			throws SQLException {
		ResultSet rs = null;
		String sql = "select * from CoBaseLinkRel cr right join CoAgencyLinkman cl on cr.cblr_cali_id=cl.cali_id where cid=? and cblr_state=? order by cr.cblr_state desc,cl.cali_vip desc";
		PreparedStatement psmt = conn.getpre(sql);
		psmt.setInt(1, cid);
		psmt.setInt(2, state);
		rs = psmt.executeQuery();
		return rs;
	}

	// 根据cid获取联系人基本信息
	public ArrayList<CoAgencyLinkmanModel> getLinkmanByCid(int cid) {
		ArrayList<CoAgencyLinkmanModel> list = new ArrayList<CoAgencyLinkmanModel>();
		try {
			ResultSet rs = getLinkModelByCidRs(cid);
			while (rs.next()) {
				list.add(new CoAgencyLinkmanModel(rs.getInt("cali_id"), rs
						.getString("cali_linkman"), rs.getString("cali_name"),
						rs.getString("cali_ename"), rs.getString("cali_job"),
						rs.getString("cali_tel"), rs.getString("cali_mobile"),
						rs.getString("cali_fax"), rs.getString("cali_email"),
						rs.getString("cali_birth"), rs.getString("cali_hobby"),
						rs.getString("cali_address"), rs
								.getString("cali_personality"), rs
								.getString("cali_remark"), rs
								.getInt("cali_vip"), rs
								.getString("cali_addname"), rs
								.getString("cali_addtime"), rs
								.getString("cali_modname"), rs
								.getString("cali_modtime"), rs
								.getString("cali_delname"), rs
								.getString("cali_deltime"), rs
								.getString("cali_delReason"), rs
								.getString("cali_mobile1"), rs
								.getString("cali_mobile2"), rs
								.getString("cali_email1"), rs
								.getString("cali_email2"), rs
								.getString("cali_tel1"), rs
								.getString("cali_tel2"), rs
								.getString("cali_nickname"), rs
								.getString("cali_duty"), rs
								.getString("cali_sex"), rs
								.getString("cali_folk"), rs
								.getString("cali_origo"), rs
								.getString("cali_hjaddress"), rs
								.getString("cali_marriage"), rs
								.getString("cali_height"), rs
								.getString("cali_figure"), rs
								.getString("cali_character"), rs
								.getString("cali_weibo"), rs
								.getString("cali_weixin"), rs
								.getString("cali_qq"), rs
								.getString("cali_degree"), rs
								.getString("cali_school"), rs
								.getString("cali_schoolcity"), rs
								.getString("cali_specialty"), rs
								.getString("cali_lastindustry"), rs
								.getString("cali_lastworktime"), rs
								.getString("cali_lastjob"), rs
								.getString("cali_lastcompany"), rs
								.getString("cali_lastcompanyAddress"), rs
								.getString("cali_developmentPlan"), rs
								.getString("cali_hobbyActivities"), rs
								.getString("cali_hobbyClub"), rs
								.getString("cali_communityActivities"), rs
								.getString("cali_religiousBelief"), rs
								.getString("cali_conversationTopics"), rs
								.getString("cali_hobbyFood"), rs
								.getString("cali_diet"), rs
								.getString("cali_ifOpInvitationMeals"), rs
								.getString("cali_ifOpSengGift"), rs
								.getString("cali_notTalkAbout"), rs
								.getString("cali_attentionProblem"), rs
								.getInt("cblr_state")));
			}

		} catch (Exception e) {
			System.out.println(e.toString());
		}
		return list;
	}

	// 根据cid获取联系人基本信息
	public ArrayList<CoAgencyLinkmanModel> getLinkmanByCid(int cid, int state) {
		ArrayList<CoAgencyLinkmanModel> list = new ArrayList<CoAgencyLinkmanModel>();
		try {
			ResultSet rs = getLinkModelByCidRs(cid, state);
			while (rs.next()) {
				list.add(new CoAgencyLinkmanModel(rs.getInt("cali_id"), rs
						.getString("cali_linkman"), rs.getString("cali_name"),
						rs.getString("cali_ename"), rs.getString("cali_job"),
						rs.getString("cali_tel"), rs.getString("cali_mobile"),
						rs.getString("cali_fax"), rs.getString("cali_email"),
						rs.getString("cali_birth"), rs.getString("cali_hobby"),
						rs.getString("cali_address"), rs
								.getString("cali_personality"), rs
								.getString("cali_remark"), rs
								.getInt("cali_vip"), rs
								.getString("cali_addname"), rs
								.getString("cali_addtime"), rs
								.getString("cali_modname"), rs
								.getString("cali_modtime"), rs
								.getString("cali_delname"), rs
								.getString("cali_deltime"), rs
								.getString("cali_delReason"), rs
								.getString("cali_mobile1"), rs
								.getString("cali_mobile2"), rs
								.getString("cali_email1"), rs
								.getString("cali_email2"), rs
								.getString("cali_tel1"), rs
								.getString("cali_tel2"), rs
								.getString("cali_nickname"), rs
								.getString("cali_duty"), rs
								.getString("cali_sex"), rs
								.getString("cali_folk"), rs
								.getString("cali_origo"), rs
								.getString("cali_hjaddress"), rs
								.getString("cali_marriage"), rs
								.getString("cali_height"), rs
								.getString("cali_figure"), rs
								.getString("cali_character"), rs
								.getString("cali_weibo"), rs
								.getString("cali_weixin"), rs
								.getString("cali_qq"), rs
								.getString("cali_degree"), rs
								.getString("cali_school"), rs
								.getString("cali_schoolcity"), rs
								.getString("cali_specialty"), rs
								.getString("cali_lastindustry"), rs
								.getString("cali_lastworktime"), rs
								.getString("cali_lastjob"), rs
								.getString("cali_lastcompany"), rs
								.getString("cali_lastcompanyAddress"), rs
								.getString("cali_developmentPlan"), rs
								.getString("cali_hobbyActivities"), rs
								.getString("cali_hobbyClub"), rs
								.getString("cali_communityActivities"), rs
								.getString("cali_religiousBelief"), rs
								.getString("cali_conversationTopics"), rs
								.getString("cali_hobbyFood"), rs
								.getString("cali_diet"), rs
								.getString("cali_ifOpInvitationMeals"), rs
								.getString("cali_ifOpSengGift"), rs
								.getString("cali_notTalkAbout"), rs
								.getString("cali_attentionProblem"), rs
								.getInt("cblr_state")));
			}

		} catch (Exception e) {
			System.out.println(e.toString());
		}
		return list;
	}
}
