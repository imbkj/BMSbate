package Model;

import java.util.List;

import org.zkoss.util.media.Media;

public class CoPolicyNoticeModel {
    /// pono_id
private Integer  pono_id;
    /// ownmonth
private Integer  ownmonth;
    /// 政策通知标题
private String  pono_title;
    /// 发文机构
private String  pono_agencies;
    /// 城市
private String  pono_city;
    /// 文件类型，如：残保金、机构要求、社保
private String  pono_type;
    /// 政策通知性质：机构通知、政府通知
private String  pono_nature;
    /// 上传人
private String  pono_uploadname;
    /// 上传时间
private String  pono_uploadtime;
    /// 反馈截止日
private String  pono_feedbackbydate;
    /// 是否待跟进
private String  pono_iffollow;
    /// 说明
private String  pono_content;
    /// pono_remark
private String  pono_remark;
    /// pono_state
private Integer  pono_state;
    /// pono_addname
private String  pono_addname;
    /// pono_addtime
private String  pono_addtime;
    /// pono_modname
private String  pono_modname;
    /// pono_modtime
private String  pono_modtime;
private List<CoPolicyNoticeFileModel> filelist;
private Integer pono_cityid;
private Integer pono_cabc_id;

private String Cpnr_type;
private Integer Cpnr_pono_id;
private Integer Cpnr_data_id;
private String Cpnr_addtime;
private String Cpnr_addname;


public Integer getPono_id() {
	return pono_id;
}
public void setPono_id(Integer pono_id) {
	this.pono_id = pono_id;
}
public Integer getOwnmonth() {
	return ownmonth;
}
public void setOwnmonth(Integer ownmonth) {
	this.ownmonth = ownmonth;
}
public String getPono_title() {
	return pono_title;
}
public void setPono_title(String pono_title) {
	this.pono_title = pono_title;
}
public String getPono_agencies() {
	return pono_agencies;
}
public void setPono_agencies(String pono_agencies) {
	this.pono_agencies = pono_agencies;
}
public String getPono_city() {
	return pono_city;
}
public void setPono_city(String pono_city) {
	this.pono_city = pono_city;
}
public String getPono_type() {
	return pono_type;
}
public void setPono_type(String pono_type) {
	this.pono_type = pono_type;
}
public String getPono_nature() {
	return pono_nature;
}
public void setPono_nature(String pono_nature) {
	this.pono_nature = pono_nature;
}
public String getPono_uploadname() {
	return pono_uploadname;
}
public void setPono_uploadname(String pono_uploadname) {
	this.pono_uploadname = pono_uploadname;
}
public String getPono_uploadtime() {
	return pono_uploadtime;
}
public void setPono_uploadtime(String pono_uploadtime) {
	this.pono_uploadtime = pono_uploadtime;
}
public String getPono_feedbackbydate() {
	return pono_feedbackbydate;
}
public void setPono_feedbackbydate(String pono_feedbackbydate) {
	this.pono_feedbackbydate = pono_feedbackbydate;
}
public String getPono_iffollow() {
	return pono_iffollow;
}
public void setPono_iffollow(String pono_iffollow) {
	this.pono_iffollow = pono_iffollow;
}
public String getPono_content() {
	return pono_content;
}
public void setPono_content(String pono_content) {
	this.pono_content = pono_content;
}
public String getPono_remark() {
	return pono_remark;
}
public void setPono_remark(String pono_remark) {
	this.pono_remark = pono_remark;
}
public Integer getPono_state() {
	return pono_state;
}
public void setPono_state(Integer pono_state) {
	this.pono_state = pono_state;
}
public String getPono_addname() {
	return pono_addname;
}
public void setPono_addname(String pono_addname) {
	this.pono_addname = pono_addname;
}
public String getPono_addtime() {
	return pono_addtime;
}
public void setPono_addtime(String pono_addtime) {
	this.pono_addtime = pono_addtime;
}
public String getPono_modname() {
	return pono_modname;
}
public void setPono_modname(String pono_modname) {
	this.pono_modname = pono_modname;
}
public String getPono_modtime() {
	return pono_modtime;
}
public void setPono_modtime(String pono_modtime) {
	this.pono_modtime = pono_modtime;
}
public List<CoPolicyNoticeFileModel> getFilelist() {
	return filelist;
}
public void setFilelist(List<CoPolicyNoticeFileModel> filelist) {
	this.filelist = filelist;
}
public Integer getPono_cityid() {
	return pono_cityid;
}
public void setPono_cityid(Integer pono_cityid) {
	this.pono_cityid = pono_cityid;
}
public Integer getPono_cabc_id() {
	return pono_cabc_id;
}
public void setPono_cabc_id(Integer pono_cabc_id) {
	this.pono_cabc_id = pono_cabc_id;
}
public String getCpnr_type() {
	return Cpnr_type;
}
public void setCpnr_type(String cpnr_type) {
	Cpnr_type = cpnr_type;
}
public Integer getCpnr_pono_id() {
	return Cpnr_pono_id;
}
public void setCpnr_pono_id(Integer cpnr_pono_id) {
	Cpnr_pono_id = cpnr_pono_id;
}
public Integer getCpnr_data_id() {
	return Cpnr_data_id;
}
public void setCpnr_data_id(Integer cpnr_data_id) {
	Cpnr_data_id = cpnr_data_id;
}
public String getCpnr_addtime() {
	return Cpnr_addtime;
}
public void setCpnr_addtime(String cpnr_addtime) {
	Cpnr_addtime = cpnr_addtime;
}
public String getCpnr_addname() {
	return Cpnr_addname;
}
public void setCpnr_addname(String cpnr_addname) {
	Cpnr_addname = cpnr_addname;
}

}
