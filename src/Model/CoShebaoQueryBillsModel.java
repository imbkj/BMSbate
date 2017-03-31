package Model;

import java.util.Date;

/**
 * 社保缴交到账情况实体
 * @author Administrator
 *
 */
public class CoShebaoQueryBillsModel {

	private int csqb_id;  
	private int csqb_cspa_id; //社保缴交信息表id
	private String isAccount; //到账情况
	private String queryDate;   //查询日期
	private String csqb_addname;
	private String csqb_addtime;

	
	public int getCsqb_id() {
		return csqb_id;
	}
	public void setCsqb_id(int csqb_id) {
		this.csqb_id = csqb_id;
	}
	public int getCsqb_cspa_id() {
		return csqb_cspa_id;
	}
	public void setCsqb_cspa_id(int csqb_cspa_id) {
		this.csqb_cspa_id = csqb_cspa_id;
	}
	public String getIsAccount() {
		return isAccount;
	}
	public void setIsAccount(String isAccount) {
		this.isAccount = isAccount;
	}

	public String getCsqb_addname() {
		return csqb_addname;
	}
	public void setCsqb_addname(String csqb_addname) {
		this.csqb_addname = csqb_addname;
	}
	public String getQueryDate() {
		return queryDate;
	}
	public void setQueryDate(String queryDate) {
		this.queryDate = queryDate;
	}
	public String getCsqb_addtime() {
		return csqb_addtime;
	}
	public void setCsqb_addtime(String csqb_addtime) {
		this.csqb_addtime = csqb_addtime;
	}

	
	
	
}
