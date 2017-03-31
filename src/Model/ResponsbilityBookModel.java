package Model;

/**
 * 计生责任书
 * 
 * @author Administrator
 * 
 */
public class ResponsbilityBookModel {

	private int rebk_id;
	private int cid;
	private String rebk_signdateString; // 责任书签订时间
	private String rebk_signdate; // 责任书签订时间
	private String rebk_signname; // 责任书签订人
	private String rebk_enddate; // 责任书到期时间
	private String rebk_enddateString;
	private String rebk_limit; // 期限
	private String rebk_rs_taketime; // 人事领取材料和表格时间
	private String rebk_rs_taketimeString;
	private String rebk_rs_takename; // 人事领取表格人
	private String rebk_client_taketime; // 客服领取材料时间
	private String rebk_client_taketimeString;
	private String rebk_client_takename; // 客服材料领取人
	private String rebk_rs_signtime; // 人事确认时间
	private String rebk_rs_signtimeString;
	private String rebk_rs_signname; // 人事确认人
	private String rebk_rs_oktime; // 人事完成日期
	private String rebk_rs_oktimeString;
	private int rebk_state; // 状态
	private String rebk_addtime;
	private String rebk_addtimeString;
	private String rebk_addname;
	private String rebk_modtime; // 最终修改时间
	private String rebk_modtimeString;
	private String rebk_modname; // 修改人
	private String rebk_booktype; // 办理类型
	private int rebk_cori_id; // 就业表id
	private int rebk_step_state; // 流程步骤控制
	private String rebk_need_doc;   //反馈材料
	private String rebk_rs_doctime;  //反馈材料时间
	
	
	

	public String getRebk_rs_doctime() {
		return rebk_rs_doctime;
	}

	public void setRebk_rs_doctime(String rebk_rs_doctime) {
		this.rebk_rs_doctime = rebk_rs_doctime;
	}

	public String getRebk_need_doc() {
		return rebk_need_doc;
	}

	public void setRebk_need_doc(String rebk_need_doc) {
		this.rebk_need_doc = rebk_need_doc;
	}

	public int getRebk_id() {
		return rebk_id;
	}

	public void setRebk_id(int rebk_id) {
		this.rebk_id = rebk_id;
	}

	public int getCid() {
		return cid;
	}

	public void setCid(int cid) {
		this.cid = cid;
	}

	public String getRebk_signdateString() {
		return rebk_signdateString;
	}

	public void setRebk_signdateString(String rebk_signdateString) {
		this.rebk_signdateString = rebk_signdateString;
	}

	public String getRebk_signdate() {
		return rebk_signdate;
	}

	public void setRebk_signdate(String rebk_signdate) {
		this.rebk_signdate = rebk_signdate;
	}

	public String getRebk_signname() {
		return rebk_signname;
	}

	public void setRebk_signname(String rebk_signname) {
		this.rebk_signname = rebk_signname;
	}

	public String getRebk_enddate() {
		return rebk_enddate;
	}

	public void setRebk_enddate(String rebk_enddate) {
		this.rebk_enddate = rebk_enddate;
	}

	public String getRebk_enddateString() {
		return rebk_enddateString;
	}

	public void setRebk_enddateString(String rebk_enddateString) {
		this.rebk_enddateString = rebk_enddateString;
	}

	public String getRebk_limit() {
		return rebk_limit;
	}

	public void setRebk_limit(String rebk_limit) {
		this.rebk_limit = rebk_limit;
	}

	public String getRebk_rs_taketime() {
		return rebk_rs_taketime;
	}

	public void setRebk_rs_taketime(String rebk_rs_taketime) {
		this.rebk_rs_taketime = rebk_rs_taketime;
	}

	public String getRebk_rs_taketimeString() {
		return rebk_rs_taketimeString;
	}

	public void setRebk_rs_taketimeString(String rebk_rs_taketimeString) {
		this.rebk_rs_taketimeString = rebk_rs_taketimeString;
	}

	public String getRebk_rs_takename() {
		return rebk_rs_takename;
	}

	public void setRebk_rs_takename(String rebk_rs_takename) {
		this.rebk_rs_takename = rebk_rs_takename;
	}

	public String getRebk_client_taketime() {
		return rebk_client_taketime;
	}

	public void setRebk_client_taketime(String rebk_client_taketime) {
		this.rebk_client_taketime = rebk_client_taketime;
	}

	public String getRebk_client_taketimeString() {
		return rebk_client_taketimeString;
	}

	public void setRebk_client_taketimeString(String rebk_client_taketimeString) {
		this.rebk_client_taketimeString = rebk_client_taketimeString;
	}

	public String getRebk_client_takename() {
		return rebk_client_takename;
	}

	public void setRebk_client_takename(String rebk_client_takename) {
		this.rebk_client_takename = rebk_client_takename;
	}

	public String getRebk_rs_signtime() {
		return rebk_rs_signtime;
	}

	public void setRebk_rs_signtime(String rebk_rs_signtime) {
		this.rebk_rs_signtime = rebk_rs_signtime;
	}

	public String getRebk_rs_signtimeString() {
		return rebk_rs_signtimeString;
	}

	public void setRebk_rs_signtimeString(String rebk_rs_signtimeString) {
		this.rebk_rs_signtimeString = rebk_rs_signtimeString;
	}

	public String getRebk_rs_signname() {
		return rebk_rs_signname;
	}

	public void setRebk_rs_signname(String rebk_rs_signname) {
		this.rebk_rs_signname = rebk_rs_signname;
	}

	public String getRebk_rs_oktime() {
		return rebk_rs_oktime;
	}

	public void setRebk_rs_oktime(String rebk_rs_oktime) {
		this.rebk_rs_oktime = rebk_rs_oktime;
	}

	public String getRebk_rs_oktimeString() {
		return rebk_rs_oktimeString;
	}

	public void setRebk_rs_oktimeString(String rebk_rs_oktimeString) {
		this.rebk_rs_oktimeString = rebk_rs_oktimeString;
	}

	public int getRebk_state() {
		return rebk_state;
	}

	public void setRebk_state(int rebk_state) {
		this.rebk_state = rebk_state;
	}

	public String getRebk_addtime() {
		return rebk_addtime;
	}

	public void setRebk_addtime(String rebk_addtime) {
		this.rebk_addtime = rebk_addtime;
	}

	public String getRebk_addtimeString() {
		return rebk_addtimeString;
	}

	public void setRebk_addtimeString(String rebk_addtimeString) {
		this.rebk_addtimeString = rebk_addtimeString;
	}

	public String getRebk_addname() {
		return rebk_addname;
	}

	public void setRebk_addname(String rebk_addname) {
		this.rebk_addname = rebk_addname;
	}

	public String getRebk_modtime() {
		return rebk_modtime;
	}

	public void setRebk_modtime(String rebk_modtime) {
		this.rebk_modtime = rebk_modtime;
	}

	public String getRebk_modtimeString() {
		return rebk_modtimeString;
	}

	public void setRebk_modtimeString(String rebk_modtimeString) {
		this.rebk_modtimeString = rebk_modtimeString;
	}

	public String getRebk_modname() {
		return rebk_modname;
	}

	public void setRebk_modname(String rebk_modname) {
		this.rebk_modname = rebk_modname;
	}

	public String getRebk_booktype() {
		return rebk_booktype;
	}

	public void setRebk_booktype(String rebk_booktype) {
		this.rebk_booktype = rebk_booktype;
	}

	public int getRebk_cori_id() {
		return rebk_cori_id;
	}

	public void setRebk_cori_id(int rebk_cori_id) {
		this.rebk_cori_id = rebk_cori_id;
	}

	public int getRebk_step_state() {
		return rebk_step_state;
	}

	public void setRebk_step_state(int rebk_step_state) {
		this.rebk_step_state = rebk_step_state;
	}

}
