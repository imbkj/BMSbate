package Model;

import java.util.Date;

import Util.DateStringChange;

/*
 * 创建人：林少斌、李文洁
 * 创建时间：2013-9-12
 * 用途：委托机构联系人信息表
 */

public class CoAgencyLinkmanModel {

	private Integer cali_id; // 主键ID
	private String cali_linkman; // 联系人组名
	private String cali_name; // 联系人姓名
	private String cali_ename; // 英文名
	private String cali_job; // 职位
	private String cali_tel; // 联系电话
	private String cali_mobile; // 手机
	private String cali_fax; // 传真
	private String cali_email; // 邮箱
	private String cali_birth; // 生日
	private String cali_hobby; // 爱好
	private String cali_address; // 联系地址
	private String cali_personality; // 个性描述
	private String cali_remark; // 备注
	private Integer cali_vip = 0; // 联系人重要性（0不重要,1重要）
	private String cali_addname; // 添加人
	private String cali_addtime; // 添加时间
	private String cali_modname; // 最后修改人
	private String cali_modtime; // 修改时间
	private String cali_delname; // 删除人
	private String cali_deltime; // 删除时间
	private String cali_delReason;
	private Integer type;
	/* 添加——陈耀家 */
	private String cali_mobile1;
	private String cali_mobile2;
	private String cali_email1;
	private String cali_email2;
	private String cali_tel1;
	private String cali_tel2;

	/* 添加——李文洁 */
	private String cali_nickname;
	private String cali_duty;
	private String cali_sex;
	private String cali_folk;
	private String cali_origo;
	private String cali_hjaddress;
	private String cali_marriage;
	private String cali_height;
	private String cali_figure;
	private String cali_character;
	private String cali_weibo;
	private String cali_weixin;
	private String cali_qq;
	private String cali_degree;
	private String cali_school;
	private String cali_schoolcity;
	private String cali_specialty;
	private String cali_lastindustry;
	private String cali_lastworktime;
	private String cali_lastjob;
	private String cali_lastcompany;
	private String cali_lastcompanyAddress;
	private String cali_developmentPlan;
	private String cali_hobbyActivities;
	private String cali_hobbyClub;
	private String cali_communityActivities;
	private String cali_religiousBelief;
	private String cali_conversationTopics;
	private String cali_hobbyFood;
	private String cali_diet;
	private String cali_ifOpInvitationMeals;
	private String cali_ifOpSengGift;
	private String cali_notTalkAbout;
	private String cali_attentionProblem;
	private Date cali_birthdate;

	// 关系表
	private Integer cabl_id;
	private Integer cabl_state;
	private Integer cabc_id;

	// 潜在客户联系人状态（转成功页面用）
	private Integer lbType = 0;
	private String lbStyle = "";
	private String lbValue = "";
	private Integer cblr_state;
	private boolean cali_vipBool;

	// 生日
	private String cali_birthYearStr;
	private String cali_birthMonthDayStr;
	private Date cali_birthYear;
	private Date cali_birthMonthDay;

	public CoAgencyLinkmanModel() {
		super();
	}

	public CoAgencyLinkmanModel(Integer cali_id, String cali_linkman,
			String cali_name, String cali_ename, String cali_job,
			String cali_tel, String cali_mobile, String cali_fax,
			String cali_email, String cali_birth, String cali_hobby,
			String cali_address, String cali_personality, String cali_remark,
			Integer cali_vip, String cali_addname, String cali_addtime,
			String cali_modname, String cali_modtime, String cali_delname,
			String cali_deltime, String cali_delReason) {
		super();
		this.cali_id = cali_id;
		this.cali_linkman = cali_linkman;
		this.cali_name = cali_name;
		this.cali_ename = cali_ename;
		this.cali_job = cali_job;
		this.cali_tel = cali_tel;
		this.cali_mobile = cali_mobile;
		this.cali_fax = cali_fax;
		this.cali_email = cali_email;
		this.cali_birth = cali_birth;
		this.cali_hobby = cali_hobby;
		this.cali_address = cali_address;
		this.cali_personality = cali_personality;
		this.cali_remark = cali_remark;
		this.cali_vip = cali_vip;
		this.cali_addname = cali_addname;
		this.cali_addtime = cali_addtime;
		this.cali_modname = cali_modname;
		this.cali_modtime = cali_modtime;
		this.cali_delname = cali_delname;
		this.cali_deltime = cali_deltime;
		this.cali_delReason = cali_delReason;
	}

	public CoAgencyLinkmanModel(Integer cali_id, String cali_linkman,
			String cali_name, String cali_ename, String cali_job,
			String cali_tel, String cali_mobile, String cali_fax,
			String cali_email, String cali_birth, String cali_hobby,
			String cali_address, String cali_personality, String cali_remark,
			Integer cali_vip, String cali_addname, String cali_addtime,
			String cali_modname, String cali_modtime, String cali_delname,
			String cali_deltime, String cali_delReason, String cali_mobile1,
			String cali_mobile2, String cali_email1, String cali_email2,
			String cali_tel1, String cali_tel2, String cali_duty) {
		super();
		this.cali_id = cali_id;
		this.cali_linkman = cali_linkman;
		this.cali_name = cali_name;
		this.cali_ename = cali_ename;
		this.cali_job = cali_job;
		this.cali_tel = cali_tel;
		this.cali_mobile = cali_mobile;
		this.cali_fax = cali_fax;
		this.cali_email = cali_email;
		this.cali_birth = cali_birth;
		this.cali_hobby = cali_hobby;
		this.cali_address = cali_address;
		this.cali_personality = cali_personality;
		this.cali_remark = cali_remark;
		this.cali_vip = cali_vip;
		this.cali_addname = cali_addname;
		this.cali_addtime = cali_addtime;
		this.cali_modname = cali_modname;
		this.cali_modtime = cali_modtime;
		this.cali_delname = cali_delname;
		this.cali_deltime = cali_deltime;
		this.cali_delReason = cali_delReason;
		this.cali_mobile1 = cali_mobile1;
		this.cali_mobile2 = cali_mobile2;
		this.cali_email1 = cali_email1;
		this.cali_email2 = cali_email2;
		this.cali_tel1 = cali_tel1;
		this.cali_tel2 = cali_tel2;
		this.cali_duty = cali_duty;
	}

	public CoAgencyLinkmanModel(Integer cali_id, String cali_linkman,
			String cali_name, String cali_ename, String cali_job,
			String cali_tel, String cali_mobile, String cali_fax,
			String cali_email, String cali_birth, String cali_hobby,
			String cali_address, String cali_personality, String cali_remark,
			Integer cali_vip, String cali_addname, String cali_addtime,
			String cali_modname, String cali_modtime, String cali_delname,
			String cali_deltime, String cali_delReason, String cali_mobile1,
			String cali_mobile2, String cali_email1, String cali_email2,
			String cali_tel1, String cali_tel2, String cali_nickname,
			String cali_duty, String cali_sex, String cali_folk,
			String cali_origo, String cali_hjaddress, String cali_marriage,
			String cali_height, String cali_figure, String cali_character,
			String cali_weibo, String cali_weixin, String cali_qq,
			String cali_degree, String cali_school, String cali_schoolcity,
			String cali_specialty, String cali_lastindustry,
			String cali_lastworktime, String cali_lastjob,
			String cali_lastcompany, String cali_lastcompanyAddress,
			String cali_developmentPlan, String cali_hobbyActivities,
			String cali_hobbyClub, String cali_communityActivities,
			String cali_religiousBelief, String cali_conversationTopics,
			String cali_hobbyFood, String cali_diet,
			String cali_ifOpInvitationMeals, String cali_ifOpSengGift,
			String cali_notTalkAbout, String cali_attentionProblem) {
		super();
		this.cali_id = cali_id;
		this.cali_linkman = cali_linkman;
		this.cali_name = cali_name;
		this.cali_ename = cali_ename;
		this.cali_job = cali_job;
		this.cali_tel = cali_tel;
		this.cali_mobile = cali_mobile;
		this.cali_fax = cali_fax;
		this.cali_email = cali_email;
		this.cali_birth = cali_birth;
		this.cali_hobby = cali_hobby;
		this.cali_address = cali_address;
		this.cali_personality = cali_personality;
		this.cali_remark = cali_remark;
		this.cali_vip = cali_vip;
		this.cali_addname = cali_addname;
		this.cali_addtime = cali_addtime;
		this.cali_modname = cali_modname;
		this.cali_modtime = cali_modtime;
		this.cali_delname = cali_delname;
		this.cali_deltime = cali_deltime;
		this.cali_delReason = cali_delReason;
		this.cali_mobile1 = cali_mobile1;
		this.cali_mobile2 = cali_mobile2;
		this.cali_email1 = cali_email1;
		this.cali_email2 = cali_email2;
		this.cali_tel1 = cali_tel1;
		this.cali_tel2 = cali_tel2;
		this.cali_nickname = cali_nickname;
		this.cali_duty = cali_duty;
		this.cali_sex = cali_sex;
		this.cali_folk = cali_folk;
		this.cali_origo = cali_origo;
		this.cali_hjaddress = cali_hjaddress;
		this.cali_marriage = cali_marriage;
		this.cali_height = cali_height;
		this.cali_figure = cali_figure;
		this.cali_character = cali_character;
		this.cali_weibo = cali_weibo;
		this.cali_weixin = cali_weixin;
		this.cali_qq = cali_qq;
		this.cali_degree = cali_degree;
		this.cali_school = cali_school;
		this.cali_schoolcity = cali_schoolcity;
		this.cali_specialty = cali_specialty;
		this.cali_lastindustry = cali_lastindustry;
		this.cali_lastworktime = cali_lastworktime;
		this.cali_lastjob = cali_lastjob;
		this.cali_lastcompany = cali_lastcompany;
		this.cali_lastcompanyAddress = cali_lastcompanyAddress;
		this.cali_developmentPlan = cali_developmentPlan;
		this.cali_hobbyActivities = cali_hobbyActivities;
		this.cali_hobbyClub = cali_hobbyClub;
		this.cali_communityActivities = cali_communityActivities;
		this.cali_religiousBelief = cali_religiousBelief;
		this.cali_conversationTopics = cali_conversationTopics;
		this.cali_hobbyFood = cali_hobbyFood;
		this.cali_diet = cali_diet;
		this.cali_ifOpInvitationMeals = cali_ifOpInvitationMeals;
		this.cali_ifOpSengGift = cali_ifOpSengGift;
		this.cali_notTalkAbout = cali_notTalkAbout;
		this.cali_attentionProblem = cali_attentionProblem;
	}

	public CoAgencyLinkmanModel(Integer cali_id, String cali_linkman,
			String cali_name, String cali_ename, String cali_job,
			String cali_tel, String cali_mobile, String cali_fax,
			String cali_email, String cali_birth, String cali_hobby,
			String cali_address, String cali_personality, String cali_remark,
			Integer cali_vip, String cali_addname, String cali_addtime,
			String cali_modname, String cali_modtime, String cali_delname,
			String cali_deltime, String cali_delReason, String cali_mobile1,
			String cali_mobile2, String cali_email1, String cali_email2,
			String cali_tel1, String cali_tel2, String cali_nickname,
			String cali_duty, String cali_sex, String cali_folk,
			String cali_origo, String cali_hjaddress, String cali_marriage,
			String cali_height, String cali_figure, String cali_character,
			String cali_weibo, String cali_weixin, String cali_qq,
			String cali_degree, String cali_school, String cali_schoolcity,
			String cali_specialty, String cali_lastindustry,
			String cali_lastworktime, String cali_lastjob,
			String cali_lastcompany, String cali_lastcompanyAddress,
			String cali_developmentPlan, String cali_hobbyActivities,
			String cali_hobbyClub, String cali_communityActivities,
			String cali_religiousBelief, String cali_conversationTopics,
			String cali_hobbyFood, String cali_diet,
			String cali_ifOpInvitationMeals, String cali_ifOpSengGift,
			String cali_notTalkAbout, String cali_attentionProblem,
			Integer cblr_state) {
		super();
		this.cali_id = cali_id;
		this.cali_linkman = cali_linkman;
		this.cali_name = cali_name;
		this.cali_ename = cali_ename;
		this.cali_job = cali_job;
		this.cali_tel = cali_tel;
		this.cali_mobile = cali_mobile;
		this.cali_fax = cali_fax;
		this.cali_email = cali_email;
		this.cali_birth = cali_birth;
		this.cali_hobby = cali_hobby;
		this.cali_address = cali_address;
		this.cali_personality = cali_personality;
		this.cali_remark = cali_remark;
		this.cali_vip = cali_vip;
		this.cali_addname = cali_addname;
		this.cali_addtime = cali_addtime;
		this.cali_modname = cali_modname;
		this.cali_modtime = cali_modtime;
		this.cali_delname = cali_delname;
		this.cali_deltime = cali_deltime;
		this.cali_delReason = cali_delReason;
		this.cali_mobile1 = cali_mobile1;
		this.cali_mobile2 = cali_mobile2;
		this.cali_email1 = cali_email1;
		this.cali_email2 = cali_email2;
		this.cali_tel1 = cali_tel1;
		this.cali_tel2 = cali_tel2;
		this.cali_nickname = cali_nickname;
		this.cali_duty = cali_duty;
		this.cali_sex = cali_sex;
		this.cali_folk = cali_folk;
		this.cali_origo = cali_origo;
		this.cali_hjaddress = cali_hjaddress;
		this.cali_marriage = cali_marriage;
		this.cali_height = cali_height;
		this.cali_figure = cali_figure;
		this.cali_character = cali_character;
		this.cali_weibo = cali_weibo;
		this.cali_weixin = cali_weixin;
		this.cali_qq = cali_qq;
		this.cali_degree = cali_degree;
		this.cali_school = cali_school;
		this.cali_schoolcity = cali_schoolcity;
		this.cali_specialty = cali_specialty;
		this.cali_lastindustry = cali_lastindustry;
		this.cali_lastworktime = cali_lastworktime;
		this.cali_lastjob = cali_lastjob;
		this.cali_lastcompany = cali_lastcompany;
		this.cali_lastcompanyAddress = cali_lastcompanyAddress;
		this.cali_developmentPlan = cali_developmentPlan;
		this.cali_hobbyActivities = cali_hobbyActivities;
		this.cali_hobbyClub = cali_hobbyClub;
		this.cali_communityActivities = cali_communityActivities;
		this.cali_religiousBelief = cali_religiousBelief;
		this.cali_conversationTopics = cali_conversationTopics;
		this.cali_hobbyFood = cali_hobbyFood;
		this.cali_diet = cali_diet;
		this.cali_ifOpInvitationMeals = cali_ifOpInvitationMeals;
		this.cali_ifOpSengGift = cali_ifOpSengGift;
		this.cali_notTalkAbout = cali_notTalkAbout;
		this.cali_attentionProblem = cali_attentionProblem;
		this.cblr_state = cblr_state;
	}

	public CoAgencyLinkmanModel(Integer cali_id, String cali_linkman,
			String cali_name, String cali_ename, String cali_job,
			String cali_tel, String cali_mobile, String cali_fax,
			String cali_email, String cali_birth, String cali_hobby,
			String cali_address, String cali_personality, String cali_remark,
			Integer cali_vip, String cali_addname, String cali_addtime,
			String cali_modname, String cali_modtime, String cali_delname,
			String cali_deltime, String cali_delReason, String cali_mobile1,
			String cali_mobile2, String cali_email1, String cali_email2,
			String cali_tel1, String cali_tel2, String cali_nickname,
			String cali_duty, String cali_sex, String cali_folk,
			String cali_origo, String cali_hjaddress, String cali_marriage,
			String cali_height, String cali_figure, String cali_character,
			String cali_weibo, String cali_weixin, String cali_qq,
			String cali_degree, String cali_school, String cali_schoolcity,
			String cali_specialty, String cali_lastindustry,
			String cali_lastworktime, String cali_lastjob,
			String cali_lastcompany, String cali_lastcompanyAddress,
			String cali_developmentPlan, String cali_hobbyActivities,
			String cali_hobbyClub, String cali_communityActivities,
			String cali_religiousBelief, String cali_conversationTopics,
			String cali_hobbyFood, String cali_diet,
			String cali_ifOpInvitationMeals, String cali_ifOpSengGift,
			String cali_notTalkAbout, String cali_attentionProblem,
			Date cali_birthdate, String cali_birthYearStr,
			String cali_birthMonthDayStr) {
		super();
		this.cali_id = cali_id;
		this.cali_linkman = cali_linkman;
		this.cali_name = cali_name;
		this.cali_ename = cali_ename;
		this.cali_job = cali_job;
		this.cali_tel = cali_tel;
		this.cali_mobile = cali_mobile;
		this.cali_fax = cali_fax;
		this.cali_email = cali_email;
		this.cali_birth = cali_birth;
		this.cali_hobby = cali_hobby;
		this.cali_address = cali_address;
		this.cali_personality = cali_personality;
		this.cali_remark = cali_remark;
		this.cali_vip = cali_vip;
		this.cali_addname = cali_addname;
		this.cali_addtime = cali_addtime;
		this.cali_modname = cali_modname;
		this.cali_modtime = cali_modtime;
		this.cali_delname = cali_delname;
		this.cali_deltime = cali_deltime;
		this.cali_delReason = cali_delReason;
		this.cali_mobile1 = cali_mobile1;
		this.cali_mobile2 = cali_mobile2;
		this.cali_email1 = cali_email1;
		this.cali_email2 = cali_email2;
		this.cali_tel1 = cali_tel1;
		this.cali_tel2 = cali_tel2;
		this.cali_nickname = cali_nickname;
		this.cali_duty = cali_duty;
		this.cali_sex = cali_sex;
		this.cali_folk = cali_folk;
		this.cali_origo = cali_origo;
		this.cali_hjaddress = cali_hjaddress;
		this.cali_marriage = cali_marriage;
		this.cali_height = cali_height;
		this.cali_figure = cali_figure;
		this.cali_character = cali_character;
		this.cali_weibo = cali_weibo;
		this.cali_weixin = cali_weixin;
		this.cali_qq = cali_qq;
		this.cali_degree = cali_degree;
		this.cali_school = cali_school;
		this.cali_schoolcity = cali_schoolcity;
		this.cali_specialty = cali_specialty;
		this.cali_lastindustry = cali_lastindustry;
		this.cali_lastworktime = cali_lastworktime;
		this.cali_lastjob = cali_lastjob;
		this.cali_lastcompany = cali_lastcompany;
		this.cali_lastcompanyAddress = cali_lastcompanyAddress;
		this.cali_developmentPlan = cali_developmentPlan;
		this.cali_hobbyActivities = cali_hobbyActivities;
		this.cali_hobbyClub = cali_hobbyClub;
		this.cali_communityActivities = cali_communityActivities;
		this.cali_religiousBelief = cali_religiousBelief;
		this.cali_conversationTopics = cali_conversationTopics;
		this.cali_hobbyFood = cali_hobbyFood;
		this.cali_diet = cali_diet;
		this.cali_ifOpInvitationMeals = cali_ifOpInvitationMeals;
		this.cali_ifOpSengGift = cali_ifOpSengGift;
		this.cali_notTalkAbout = cali_notTalkAbout;
		this.cali_attentionProblem = cali_attentionProblem;
		this.cali_birthdate = cali_birthdate;
		this.cali_birthYearStr = cali_birthYearStr;
		this.cali_birthMonthDayStr = cali_birthMonthDayStr;
		this.cali_vipBool = cali_vip == 1 ? true : false;
		if (cali_birthYearStr != null && !"".equals(cali_birthYearStr))
			this.cali_birthYear = DateStringChange.StringtoDate(
					cali_birthYearStr, "yyyy");
		if (cali_birthMonthDayStr != null && !"".equals(cali_birthMonthDayStr))
			this.cali_birthMonthDay = DateStringChange.StringtoDate(
					cali_birthMonthDayStr, "MM-dd");

	}

	public Date getCali_birthdate() {
		return cali_birthdate;
	}

	public void setCali_birthdate(Date cali_birthdate) {
		this.cali_birthdate = cali_birthdate;
	}

	public Integer getCali_id() {
		return cali_id;
	}

	public void setCali_id(Integer cali_id) {
		this.cali_id = cali_id;
	}

	public String getCali_linkman() {
		return cali_linkman;
	}

	public void setCali_linkman(String cali_linkman) {
		this.cali_linkman = cali_linkman;
	}

	public String getCali_name() {
		return cali_name;
	}

	public void setCali_name(String cali_name) {
		this.cali_name = cali_name;
	}

	public String getCali_ename() {
		return cali_ename;
	}

	public void setCali_ename(String cali_ename) {
		this.cali_ename = cali_ename;
	}

	public String getCali_job() {
		return cali_job;
	}

	public void setCali_job(String cali_job) {
		this.cali_job = cali_job;
	}

	public String getCali_tel() {
		return cali_tel;
	}

	public void setCali_tel(String cali_tel) {
		this.cali_tel = cali_tel;
	}

	public String getCali_mobile() {
		return cali_mobile;
	}

	public void setCali_mobile(String cali_mobile) {
		this.cali_mobile = cali_mobile;
	}

	public String getCali_fax() {
		return cali_fax;
	}

	public void setCali_fax(String cali_fax) {
		this.cali_fax = cali_fax;
	}

	public String getCali_email() {
		return cali_email;
	}

	public void setCali_email(String cali_email) {
		this.cali_email = cali_email;
	}

	public String getCali_birth() {
		return cali_birth;
	}

	public void setCali_birth(String cali_birth) {
		this.cali_birth = cali_birth;
	}

	public String getCali_hobby() {
		return cali_hobby;
	}

	public void setCali_hobby(String cali_hobby) {
		this.cali_hobby = cali_hobby;
	}

	public String getCali_address() {
		return cali_address;
	}

	public void setCali_address(String cali_address) {
		this.cali_address = cali_address;
	}

	public String getCali_personality() {
		return cali_personality;
	}

	public void setCali_personality(String cali_personality) {
		this.cali_personality = cali_personality;
	}

	public String getCali_remark() {
		return cali_remark;
	}

	public void setCali_remark(String cali_remark) {
		this.cali_remark = cali_remark;
	}

	public Integer getCali_vip() {
		return cali_vip;
	}

	public void setCali_vip(Integer cali_vip) {
		this.cali_vip = cali_vip;
		if (cali_vip == 1)
			this.cali_vipBool = true;
		else
			this.cali_vipBool = false;
	}

	public String getCali_addname() {
		return cali_addname;
	}

	public void setCali_addname(String cali_addname) {
		this.cali_addname = cali_addname;
	}

	public String getCali_addtime() {
		return cali_addtime;
	}

	public void setCali_addtime(String cali_addtime) {
		this.cali_addtime = cali_addtime;
	}

	public String getCali_modname() {
		return cali_modname;
	}

	public void setCali_modname(String cali_modname) {
		this.cali_modname = cali_modname;
	}

	public String getCali_modtime() {
		return cali_modtime;
	}

	public void setCali_modtime(String cali_modtime) {
		this.cali_modtime = cali_modtime;
	}

	public String getCali_delname() {
		return cali_delname;
	}

	public void setCali_delname(String cali_delname) {
		this.cali_delname = cali_delname;
	}

	public String getCali_deltime() {
		return cali_deltime;
	}

	public void setCali_deltime(String cali_deltime) {
		this.cali_deltime = cali_deltime;
	}

	public String getCali_delReason() {
		return cali_delReason;
	}

	public void setCali_delReason(String cali_delReason) {
		this.cali_delReason = cali_delReason;
	}

	public String getCali_mobile1() {
		return cali_mobile1;
	}

	public void setCali_mobile1(String cali_mobile1) {
		this.cali_mobile1 = cali_mobile1;
	}

	public String getCali_mobile2() {
		return cali_mobile2;
	}

	public void setCali_mobile2(String cali_mobile2) {
		this.cali_mobile2 = cali_mobile2;
	}

	public String getCali_email1() {
		return cali_email1;
	}

	public void setCali_email1(String cali_email1) {
		this.cali_email1 = cali_email1;
	}

	public String getCali_email2() {
		return cali_email2;
	}

	public void setCali_email2(String cali_email2) {
		this.cali_email2 = cali_email2;
	}

	public String getCali_tel1() {
		return cali_tel1;
	}

	public void setCali_tel1(String cali_tel1) {
		this.cali_tel1 = cali_tel1;
	}

	public String getCali_tel2() {
		return cali_tel2;
	}

	public void setCali_tel2(String cali_tel2) {
		this.cali_tel2 = cali_tel2;
	}

	public String getCali_nickname() {
		return cali_nickname;
	}

	public void setCali_nickname(String cali_nickname) {
		this.cali_nickname = cali_nickname;
	}

	public String getCali_duty() {
		return cali_duty;
	}

	public void setCali_duty(String cali_duty) {
		this.cali_duty = cali_duty;
	}

	public String getCali_sex() {
		return cali_sex;
	}

	public void setCali_sex(String cali_sex) {
		this.cali_sex = cali_sex;
	}

	public String getCali_folk() {
		return cali_folk;
	}

	public void setCali_folk(String cali_folk) {
		this.cali_folk = cali_folk;
	}

	public String getCali_origo() {
		return cali_origo;
	}

	public void setCali_origo(String cali_origo) {
		this.cali_origo = cali_origo;
	}

	public String getCali_hjaddress() {
		return cali_hjaddress;
	}

	public void setCali_hjaddress(String cali_hjaddress) {
		this.cali_hjaddress = cali_hjaddress;
	}

	public String getCali_marriage() {
		return cali_marriage;
	}

	public void setCali_marriage(String cali_marriage) {
		this.cali_marriage = cali_marriage;
	}

	public String getCali_height() {
		return cali_height;
	}

	public void setCali_height(String cali_height) {
		this.cali_height = cali_height;
	}

	public String getCali_figure() {
		return cali_figure;
	}

	public void setCali_figure(String cali_figure) {
		this.cali_figure = cali_figure;
	}

	public String getCali_character() {
		return cali_character;
	}

	public void setCali_character(String cali_character) {
		this.cali_character = cali_character;
	}

	public String getCali_weibo() {
		return cali_weibo;
	}

	public void setCali_weibo(String cali_weibo) {
		this.cali_weibo = cali_weibo;
	}

	public String getCali_weixin() {
		return cali_weixin;
	}

	public void setCali_weixin(String cali_weixin) {
		this.cali_weixin = cali_weixin;
	}

	public String getCali_qq() {
		return cali_qq;
	}

	public void setCali_qq(String cali_qq) {
		this.cali_qq = cali_qq;
	}

	public String getCali_degree() {
		return cali_degree;
	}

	public void setCali_degree(String cali_degree) {
		this.cali_degree = cali_degree;
	}

	public String getCali_school() {
		return cali_school;
	}

	public void setCali_school(String cali_school) {
		this.cali_school = cali_school;
	}

	public String getCali_schoolcity() {
		return cali_schoolcity;
	}

	public void setCali_schoolcity(String cali_schoolcity) {
		this.cali_schoolcity = cali_schoolcity;
	}

	public String getCali_specialty() {
		return cali_specialty;
	}

	public void setCali_specialty(String cali_specialty) {
		this.cali_specialty = cali_specialty;
	}

	public String getCali_lastindustry() {
		return cali_lastindustry;
	}

	public void setCali_lastindustry(String cali_lastindustry) {
		this.cali_lastindustry = cali_lastindustry;
	}

	public String getCali_lastworktime() {
		return cali_lastworktime;
	}

	public void setCali_lastworktime(String cali_lastworktime) {
		this.cali_lastworktime = cali_lastworktime;
	}

	public String getCali_lastjob() {
		return cali_lastjob;
	}

	public void setCali_lastjob(String cali_lastjob) {
		this.cali_lastjob = cali_lastjob;
	}

	public String getCali_lastcompany() {
		return cali_lastcompany;
	}

	public void setCali_lastcompany(String cali_lastcompany) {
		this.cali_lastcompany = cali_lastcompany;
	}

	public String getCali_lastcompanyAddress() {
		return cali_lastcompanyAddress;
	}

	public void setCali_lastcompanyAddress(String cali_lastcompanyAddress) {
		this.cali_lastcompanyAddress = cali_lastcompanyAddress;
	}

	public String getCali_developmentPlan() {
		return cali_developmentPlan;
	}

	public void setCali_developmentPlan(String cali_developmentPlan) {
		this.cali_developmentPlan = cali_developmentPlan;
	}

	public String getCali_hobbyActivities() {
		return cali_hobbyActivities;
	}

	public void setCali_hobbyActivities(String cali_hobbyActivities) {
		this.cali_hobbyActivities = cali_hobbyActivities;
	}

	public String getCali_hobbyClub() {
		return cali_hobbyClub;
	}

	public void setCali_hobbyClub(String cali_hobbyClub) {
		this.cali_hobbyClub = cali_hobbyClub;
	}

	public String getCali_communityActivities() {
		return cali_communityActivities;
	}

	public void setCali_communityActivities(String cali_communityActivities) {
		this.cali_communityActivities = cali_communityActivities;
	}

	public String getCali_religiousBelief() {
		return cali_religiousBelief;
	}

	public void setCali_religiousBelief(String cali_religiousBelief) {
		this.cali_religiousBelief = cali_religiousBelief;
	}

	public String getCali_conversationTopics() {
		return cali_conversationTopics;
	}

	public void setCali_conversationTopics(String cali_conversationTopics) {
		this.cali_conversationTopics = cali_conversationTopics;
	}

	public String getCali_hobbyFood() {
		return cali_hobbyFood;
	}

	public void setCali_hobbyFood(String cali_hobbyFood) {
		this.cali_hobbyFood = cali_hobbyFood;
	}

	public String getCali_diet() {
		return cali_diet;
	}

	public void setCali_diet(String cali_diet) {
		this.cali_diet = cali_diet;
	}

	public String getCali_ifOpInvitationMeals() {
		return cali_ifOpInvitationMeals;
	}

	public void setCali_ifOpInvitationMeals(String cali_ifOpInvitationMeals) {
		this.cali_ifOpInvitationMeals = cali_ifOpInvitationMeals;
	}

	public String getCali_ifOpSengGift() {
		return cali_ifOpSengGift;
	}

	public void setCali_ifOpSengGift(String cali_ifOpSengGift) {
		this.cali_ifOpSengGift = cali_ifOpSengGift;
	}

	public String getCali_notTalkAbout() {
		return cali_notTalkAbout;
	}

	public void setCali_notTalkAbout(String cali_notTalkAbout) {
		this.cali_notTalkAbout = cali_notTalkAbout;
	}

	public String getCali_attentionProblem() {
		return cali_attentionProblem;
	}

	public void setCali_attentionProblem(String cali_attentionProblem) {
		this.cali_attentionProblem = cali_attentionProblem;
	}

	public Integer getType() {
		return type;
	}

	public void setType(Integer type) {
		this.type = type;
	}

	public Integer getCabl_id() {
		return cabl_id;
	}

	public void setCabl_id(Integer cabl_id) {
		this.cabl_id = cabl_id;
	}

	public Integer getCabl_state() {
		return cabl_state;
	}

	public void setCabl_state(Integer cabl_state) {
		this.cabl_state = cabl_state;
	}

	public Integer getCabc_id() {
		return cabc_id;
	}

	public void setCabc_id(Integer cabc_id) {
		this.cabc_id = cabc_id;
	}

	public Integer getLbType() {
		return lbType;
	}

	public void setLbType(Integer lbType) {
		this.lbType = lbType;
	}

	public String getLbStyle() {
		return lbStyle;
	}

	public void setLbStyle(String lbStyle) {
		this.lbStyle = lbStyle;
	}

	public String getLbValue() {
		return lbValue;
	}

	public void setLbValue(String lbValue) {
		this.lbValue = lbValue;
	}

	public Integer getCblr_state() {
		return cblr_state;
	}

	public void setCblr_state(Integer cblr_state) {
		this.cblr_state = cblr_state;
	}

	public boolean isCali_vipBool() {
		return cali_vipBool;
	}

	public void setCali_vipBool(boolean cali_vipBool) {
		this.cali_vipBool = cali_vipBool;
		cali_vip = cali_vipBool ? 1 : 0;
	}

	public String getCali_birthYearStr() {
		return cali_birthYearStr;
	}

	public void setCali_birthYearStr(String cali_birthYearStr) {
		this.cali_birthYearStr = cali_birthYearStr;
	}

	public String getCali_birthMonthDayStr() {
		return cali_birthMonthDayStr;
	}

	public void setCali_birthMonthDayStr(String cali_birthMonthDayStr) {
		this.cali_birthMonthDayStr = cali_birthMonthDayStr;
	}

	public Date getCali_birthYear() {
		return cali_birthYear;
	}

	public void setCali_birthYear(Date cali_birthYear) {
		this.cali_birthYear = cali_birthYear;
		try {
			if (cali_birthYear != null)
				this.cali_birthYearStr = DateStringChange.DatetoSting(
						cali_birthYear, "yyyy");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public Date getCali_birthMonthDay() {
		return cali_birthMonthDay;
	}

	public void setCali_birthMonthDay(Date cali_birthMonthDay) {
		this.cali_birthMonthDay = cali_birthMonthDay;
		try {
			if (cali_birthMonthDay != null)
				this.cali_birthMonthDayStr = DateStringChange.DatetoSting(
						cali_birthMonthDay, "MM-dd");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
