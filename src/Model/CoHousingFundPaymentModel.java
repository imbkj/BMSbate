package Model;

import java.util.Date;

/**
 * 公积金补缴到账情况实体
 * 
 * @author Administrator
 * 
 */
public class CoHousingFundPaymentModel {

	private int cofp_id;
	private int cofp_cfpa_id; // 公积金缴交信息表id
	private String isAccount; // 到账情况
	private Date queryDate;
	private String addname;
	private Date addtime;
	private String queryDateString;
	private String addtimeString;
	
	public String getQueryDateString() {
		return queryDateString;
	}
	public void setQueryDateString(String queryDateString) {
		this.queryDateString = queryDateString;
	}
	public String getAddtimeString() {
		return addtimeString;
	}
	public void setAddtimeString(String addtimeString) {
		this.addtimeString = addtimeString;
	}
	public int getCofp_id() {
		return cofp_id;
	}
	public void setCofp_id(int cofp_id) {
		this.cofp_id = cofp_id;
	}
	public int getCofp_cfpa_id() {
		return cofp_cfpa_id;
	}
	public void setCofp_cfpa_id(int cofp_cfpa_id) {
		this.cofp_cfpa_id = cofp_cfpa_id;
	}
	public String getIsAccount() {
		return isAccount;
	}
	public void setIsAccount(String isAccount) {
		this.isAccount = isAccount;
	}
	public Date getQueryDate() {
		return queryDate;
	}
	public void setQueryDate(Date queryDate) {
		this.queryDate = queryDate;
	}
	public String getAddname() {
		return addname;
	}
	public void setAddname(String addname) {
		this.addname = addname;
	}
	public Date getAddtime() {
		return addtime;
	}
	public void setAddtime(Date addtime) {
		this.addtime = addtime;
	}

}
