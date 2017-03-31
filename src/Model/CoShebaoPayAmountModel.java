package Model;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.Date;

/**
 * 社保缴交信息实体
 * 
 * @author Administrator
 * 
 */
public class CoShebaoPayAmountModel {
	private final DecimalFormat df = new DecimalFormat("#.00");
	private int cspa_id;
	private int cspa_cosb_id; // 社保基本信息表id
	private int bodycount; // 人数
	private BigDecimal acount; // 金额
	private String ownmonth; // 所属月份
	private String addname;
	private String addtime;
	private int cid;
	private String isaccount; // 到账情况
	private String coba_client;
	private String companyname;
	private String coirdcard;
	private Date queryDate;
	private int count;



	
	
	public int getCount() {
		return count;
	}

	public void setCount(int count) {
		this.count = count;
	}

	public Date getQueryDate() {
		return queryDate;
	}

	public void setQueryDate(Date queryDate) {
		this.queryDate = queryDate;
	}

	public int getCid() {
		return cid;
	}

	public void setCid(int cid) {
		this.cid = cid;
	}

	public String getIsaccount() {
		return isaccount;
	}

	public void setIsaccount(String isaccount) {
		this.isaccount = isaccount;
	}

	public String getCoba_client() {
		return coba_client;
	}

	public void setCoba_client(String coba_client) {
		this.coba_client = coba_client;
	}

	public String getCompanyname() {
		return companyname;
	}

	public void setCompanyname(String companyname) {
		this.companyname = companyname;
	}

	public String getCoirdcard() {
		return coirdcard;
	}

	public void setCoirdcard(String coirdcard) {
		this.coirdcard = coirdcard;
	}

	public int getCspa_id() {
		return cspa_id;
	}

	public void setCspa_id(int cspa_id) {
		this.cspa_id = cspa_id;
	}

	public int getCspa_cosb_id() {
		return cspa_cosb_id;
	}

	public void setCspa_cosb_id(int cspa_cosb_id) {
		this.cspa_cosb_id = cspa_cosb_id;
	}

	public int getBodycount() {
		return bodycount;
	}

	public void setBodycount(int bodycount) {
		this.bodycount = bodycount;
	}

	public BigDecimal getAcount() {
		return acount;
	}

	public void setAcount(BigDecimal acount) {
		if (acount != null) {
			this.acount = new BigDecimal(df.format(acount));
		}
	}

	public String getOwnmonth() {
		return ownmonth;
	}

	public void setOwnmonth(String ownmonth) {
		this.ownmonth = ownmonth;
	}

	public String getAddname() {
		return addname;
	}

	public void setAddname(String addname) {
		this.addname = addname;
	}

	public String getAddtime() {
		return addtime;
	}

	public void setAddtime(String addtime) {
		this.addtime = addtime;
	}

}
