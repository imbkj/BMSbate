package Model;

import java.util.Date;

/**
 * 公积金缴交到账情况实体
 * @author Administrator
 *
 */
public class CoHousingFundQueryBillsModel {

	private int cqbc_id;
	private int cqbc_cfpa_id;  //公积金缴交信息表id
	private String isAccount;  //到账情况
	private Date queryDate; //查询日期
	private String addname;
	private Date addtime;
	public int getCqbc_id() {
		return cqbc_id;
	}
	public void setCqbc_id(int cqbc_id) {
		this.cqbc_id = cqbc_id;
	}
	public int getCqbc_cfpa_id() {
		return cqbc_cfpa_id;
	}
	public void setCqbc_cfpa_id(int cqbc_cfpa_id) {
		this.cqbc_cfpa_id = cqbc_cfpa_id;
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
