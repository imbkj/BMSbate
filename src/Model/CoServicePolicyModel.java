package Model;

import java.util.List;

public class CoServicePolicyModel {
	   /// sypo_id
	private Integer  sypo_id;
	    /// ownmonth
	private Integer  ownmonth;
	    /// 政策通知标题
	private String  sypo_title;
	    /// 发文机构
	private String  sypo_agencies;
	    /// 城市
	private String  sypo_city;
	    /// 文件类型，如：残保金、机构要求、社保
	private String  sypo_type;
	    /// 政策通知性质：机构通知、政府通知
	private String  sypo_nature;
	    /// 上传人
	private String  sypo_uploadname;
	    /// 上传时间
	private String  sypo_uploadtime;
	    /// 反馈截止日
	private String  sypo_feedbackbydate;
	    /// 是否待跟进
	private String  sypo_iffollow;
	    /// 说明
	private String  sypo_content;
	    /// sypo_remark
	private String  sypo_remark;
	    /// sypo_state
	private Integer  sypo_state;
	    /// sypo_addname
	private String  sypo_addname;
	    /// sypo_addtime
	private String  sypo_addtime;
	    /// sypo_modname
	private String  sypo_modname;
	    /// sypo_modtime
	private String  sypo_modtime;
	private List<CoServicePolicyFileModel> filelist;
	private String sypo_sminwage;
	private String sypo_sminwagedate;
	private String sypo_minwage;
	private String sypo_minwagedate;
	private String sypo_minwagestandard;
	private String sypo_standarddate;
	private String sypo_hourwage;
	private String sypo_hourwagedate;
	private String sypo_class;
	private String name;
	private Integer sypo_cityid;
	private Integer sypo_cabc_id;
	private Integer sypo_item_id;
	public Integer getSypo_id() {
		return sypo_id;
	}
	public void setSypo_id(Integer sypo_id) {
		this.sypo_id = sypo_id;
	}
	public Integer getOwnmonth() {
		return ownmonth;
	}
	public void setOwnmonth(Integer ownmonth) {
		this.ownmonth = ownmonth;
	}
	public String getSypo_title() {
		return sypo_title;
	}
	public void setSypo_title(String sypo_title) {
		this.sypo_title = sypo_title;
	}
	public String getSypo_agencies() {
		return sypo_agencies;
	}
	public void setSypo_agencies(String sypo_agencies) {
		this.sypo_agencies = sypo_agencies;
	}
	public String getSypo_city() {
		return sypo_city;
	}
	public void setSypo_city(String sypo_city) {
		this.sypo_city = sypo_city;
	}
	public String getSypo_type() {
		return sypo_type;
	}
	public void setSypo_type(String sypo_type) {
		this.sypo_type = sypo_type;
	}
	public String getSypo_nature() {
		return sypo_nature;
	}
	public void setSypo_nature(String sypo_nature) {
		this.sypo_nature = sypo_nature;
	}
	public String getSypo_uploadname() {
		return sypo_uploadname;
	}
	public void setSypo_uploadname(String sypo_uploadname) {
		this.sypo_uploadname = sypo_uploadname;
	}
	public String getSypo_uploadtime() {
		return sypo_uploadtime;
	}
	public void setSypo_uploadtime(String sypo_uploadtime) {
		this.sypo_uploadtime = sypo_uploadtime;
	}
	public String getSypo_feedbackbydate() {
		return sypo_feedbackbydate;
	}
	public void setSypo_feedbackbydate(String sypo_feedbackbydate) {
		this.sypo_feedbackbydate = sypo_feedbackbydate;
	}
	public String getSypo_iffollow() {
		return sypo_iffollow;
	}
	public void setSypo_iffollow(String sypo_iffollow) {
		this.sypo_iffollow = sypo_iffollow;
	}
	public String getSypo_content() {
		return sypo_content;
	}
	public void setSypo_content(String sypo_content) {
		this.sypo_content = sypo_content;
	}
	public String getSypo_remark() {
		return sypo_remark;
	}
	public void setSypo_remark(String sypo_remark) {
		this.sypo_remark = sypo_remark;
	}
	public Integer getSypo_state() {
		return sypo_state;
	}
	public void setSypo_state(Integer sypo_state) {
		this.sypo_state = sypo_state;
	}
	public String getSypo_addname() {
		return sypo_addname;
	}
	public void setSypo_addname(String sypo_addname) {
		this.sypo_addname = sypo_addname;
	}
	public String getSypo_addtime() {
		return sypo_addtime;
	}
	public void setSypo_addtime(String sypo_addtime) {
		this.sypo_addtime = sypo_addtime;
	}
	public String getSypo_modname() {
		return sypo_modname;
	}
	public void setSypo_modname(String sypo_modname) {
		this.sypo_modname = sypo_modname;
	}
	public String getSypo_modtime() {
		return sypo_modtime;
	}
	public void setSypo_modtime(String sypo_modtime) {
		this.sypo_modtime = sypo_modtime;
	}
	public List<CoServicePolicyFileModel> getFilelist() {
		return filelist;
	}
	public void setFilelist(List<CoServicePolicyFileModel> filelist) {
		this.filelist = filelist;
	}
	public String getSypo_sminwage() {
		return sypo_sminwage;
	}
	public void setSypo_sminwage(String sypo_sminwage) {
		this.sypo_sminwage = sypo_sminwage;
	}
	public String getSypo_sminwagedate() {
		return sypo_sminwagedate;
	}
	public void setSypo_sminwagedate(String sypo_sminwagedate) {
		this.sypo_sminwagedate = sypo_sminwagedate;
	}
	public String getSypo_minwage() {
		return sypo_minwage;
	}
	public void setSypo_minwage(String sypo_minwage) {
		this.sypo_minwage = sypo_minwage;
	}
	public String getSypo_minwagedate() {
		return sypo_minwagedate;
	}
	public void setSypo_minwagedate(String sypo_minwagedate) {
		this.sypo_minwagedate = sypo_minwagedate;
	}
	public String getSypo_minwagestandard() {
		return sypo_minwagestandard;
	}
	public void setSypo_minwagestandard(String sypo_minwagestandard) {
		this.sypo_minwagestandard = sypo_minwagestandard;
	}
	public String getSypo_standarddate() {
		return sypo_standarddate;
	}
	public void setSypo_standarddate(String sypo_standarddate) {
		this.sypo_standarddate = sypo_standarddate;
	}
	public String getSypo_hourwage() {
		return sypo_hourwage;
	}
	public void setSypo_hourwage(String sypo_hourwage) {
		this.sypo_hourwage = sypo_hourwage;
	}
	public String getSypo_hourwagedate() {
		return sypo_hourwagedate;
	}
	public void setSypo_hourwagedate(String sypo_hourwagedate) {
		this.sypo_hourwagedate = sypo_hourwagedate;
	}
	public String getSypo_class() {
		return sypo_class;
	}
	public void setSypo_class(String sypo_class) {
		this.sypo_class = sypo_class;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public Integer getSypo_cityid() {
		return sypo_cityid;
	}
	public void setSypo_cityid(Integer sypo_cityid) {
		this.sypo_cityid = sypo_cityid;
	}
	public Integer getSypo_cabc_id() {
		return sypo_cabc_id;
	}
	public void setSypo_cabc_id(Integer sypo_cabc_id) {
		this.sypo_cabc_id = sypo_cabc_id;
	}
	public Integer getSypo_item_id() {
		return sypo_item_id;
	}
	public void setSypo_item_id(Integer sypo_item_id) {
		this.sypo_item_id = sypo_item_id;
	}
	
}
