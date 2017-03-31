package Model;

import java.util.Date;

import Util.DateStringChange;

public class CoBaseLinkFamilyModel {
	private int cblf_id;
	private int cblf_cali_id;
	private String cblf_relationship;
	private String cblf_name;
	private String cblf_birth;
	private Date cblf_birthdate;
	private String cblf_occupation;
	private String cblf_company;
	private String cblf_hobby;
	private String cblf_remark;
	private String cblf_addname;
	private String cblf_addtime;
	private int cblf_state;
	private String cblf_sex;
	private String cblf_birthYearStr;
	private String cblf_birthMonthDayStr;
	private Date cblf_birthYear;
	private Date cblf_birthMonthDay;

	public CoBaseLinkFamilyModel() {
		super();
	}

	public CoBaseLinkFamilyModel(int cblf_id, int cblf_cali_id,
			String cblf_relationship, String cblf_name, String cblf_birth,
			String cblf_occupation, String cblf_company, String cblf_hobby,
			String cblf_remark, String cblf_addname, String cblf_addtime,
			int cblf_state) {
		super();
		this.cblf_id = cblf_id;
		this.cblf_cali_id = cblf_cali_id;
		this.cblf_relationship = cblf_relationship;
		this.cblf_name = cblf_name;
		this.cblf_birth = cblf_birth;
		this.cblf_occupation = cblf_occupation;
		this.cblf_company = cblf_company;
		this.cblf_hobby = cblf_hobby;
		this.cblf_remark = cblf_remark;
		this.cblf_addname = cblf_addname;
		this.cblf_addtime = cblf_addtime;
		this.cblf_state = cblf_state;
	}

	public CoBaseLinkFamilyModel(int cblf_id, int cblf_cali_id,
			String cblf_relationship, String cblf_name, String cblf_birth,
			Date cblf_birthdate, String cblf_occupation, String cblf_company,
			String cblf_hobby, String cblf_remark, String cblf_addname,
			String cblf_addtime, int cblf_state, String cblf_sex,
			String cblf_birthYearStr, String cblf_birthMonthDayStr) {
		super();
		this.cblf_id = cblf_id;
		this.cblf_cali_id = cblf_cali_id;
		this.cblf_relationship = cblf_relationship;
		this.cblf_name = cblf_name;
		this.cblf_birth = cblf_birth;
		this.cblf_birthdate = cblf_birthdate;
		this.cblf_occupation = cblf_occupation;
		this.cblf_company = cblf_company;
		this.cblf_hobby = cblf_hobby;
		this.cblf_remark = cblf_remark;
		this.cblf_addname = cblf_addname;
		this.cblf_addtime = cblf_addtime;
		this.cblf_state = cblf_state;
		this.cblf_sex = cblf_sex;
		this.cblf_birthYearStr = cblf_birthYearStr;
		this.cblf_birthMonthDayStr = cblf_birthMonthDayStr;
		if (cblf_birthYearStr != null && !"".equals(cblf_birthYearStr))
			this.cblf_birthYear = DateStringChange.StringtoDate(
					cblf_birthYearStr, "yyyy");
		if (cblf_birthMonthDayStr != null && !"".equals(cblf_birthMonthDayStr))
			this.cblf_birthMonthDay = DateStringChange.StringtoDate(
					cblf_birthMonthDayStr, "MM-dd");
	}

	public Date getCblf_birthdate() {
		return cblf_birthdate;
	}

	public void setCblf_birthdate(Date cblf_birthdate) {
		this.cblf_birthdate = cblf_birthdate;
	}

	public int getCblf_id() {
		return cblf_id;
	}

	public void setCblf_id(int cblf_id) {
		this.cblf_id = cblf_id;
	}

	public int getCblf_cali_id() {
		return cblf_cali_id;
	}

	public void setCblf_cali_id(int cblf_cali_id) {
		this.cblf_cali_id = cblf_cali_id;
	}

	public String getCblf_relationship() {
		return cblf_relationship;
	}

	public void setCblf_relationship(String cblf_relationship) {
		this.cblf_relationship = cblf_relationship;
	}

	public String getCblf_name() {
		return cblf_name;
	}

	public void setCblf_name(String cblf_name) {
		this.cblf_name = cblf_name;
	}

	public String getCblf_birth() {
		return cblf_birth;
	}

	public void setCblf_birth(String cblf_birth) {
		this.cblf_birth = cblf_birth;
	}

	public String getCblf_occupation() {
		return cblf_occupation;
	}

	public void setCblf_occupation(String cblf_occupation) {
		this.cblf_occupation = cblf_occupation;
	}

	public String getCblf_company() {
		return cblf_company;
	}

	public void setCblf_company(String cblf_company) {
		this.cblf_company = cblf_company;
	}

	public String getCblf_hobby() {
		return cblf_hobby;
	}

	public void setCblf_hobby(String cblf_hobby) {
		this.cblf_hobby = cblf_hobby;
	}

	public String getCblf_remark() {
		return cblf_remark;
	}

	public void setCblf_remark(String cblf_remark) {
		this.cblf_remark = cblf_remark;
	}

	public String getCblf_addname() {
		return cblf_addname;
	}

	public void setCblf_addname(String cblf_addname) {
		this.cblf_addname = cblf_addname;
	}

	public String getCblf_addtime() {
		return cblf_addtime;
	}

	public void setCblf_addtime(String cblf_addtime) {
		this.cblf_addtime = cblf_addtime;
	}

	public int getCblf_state() {
		return cblf_state;
	}

	public void setCblf_state(int cblf_state) {
		this.cblf_state = cblf_state;
	}

	public String getCblf_sex() {
		return cblf_sex;
	}

	public void setCblf_sex(String cblf_sex) {
		this.cblf_sex = cblf_sex;
	}

	public String getCblf_birthYearStr() {
		return cblf_birthYearStr;
	}

	public void setCblf_birthYearStr(String cblf_birthYearStr) {
		this.cblf_birthYearStr = cblf_birthYearStr;
	}

	public String getCblf_birthMonthDayStr() {
		return cblf_birthMonthDayStr;
	}

	public void setCblf_birthMonthDayStr(String cblf_birthMonthDayStr) {
		this.cblf_birthMonthDayStr = cblf_birthMonthDayStr;
	}

	public Date getCblf_birthYear() {
		return cblf_birthYear;
	}

	public void setCblf_birthYear(Date cblf_birthYear) {
		this.cblf_birthYear = cblf_birthYear;
		try {
			if (cblf_birthYear != null)
				this.cblf_birthYearStr = DateStringChange.DatetoSting(
						cblf_birthYear, "yyyy");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public Date getCblf_birthMonthDay() {
		return cblf_birthMonthDay;
	}

	public void setCblf_birthMonthDay(Date cblf_birthMonthDay) {
		this.cblf_birthMonthDay = cblf_birthMonthDay;
		try {
			if (cblf_birthMonthDay != null)
				this.cblf_birthMonthDayStr = DateStringChange.DatetoSting(
						cblf_birthMonthDay, "MM-dd");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
