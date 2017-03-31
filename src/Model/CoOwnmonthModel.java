package Model;

public class CoOwnmonthModel {
	private int cid;
	private String company;
	private String shortname;
	private int ownmonth;
	private String long_company;
	
	public CoOwnmonthModel() {
		super();
	}


	
	public CoOwnmonthModel(int cid, String company, String shortname,
			int ownmonth, String long_company) {
		super();
		this.cid = cid;
		this.company = company;
		this.shortname = shortname;
		this.ownmonth = ownmonth;
		this.long_company = long_company;
	}



	public String getLong_company() {
		return long_company;
	}



	public void setLong_company(String long_company) {
		this.long_company = long_company;
	}



	public int getCid() {
		return cid;
	}
	public void setCid(int cid) {
		this.cid = cid;
	}
	public String getCompany() {
		return company;
	}
	public void setCompany(String company) {
		this.company = company;
	}
	public String getShortname() {
		return shortname;
	}
	public void setShortname(String shortname) {
		this.shortname = shortname;
	}
	public int getOwnmonth() {
		return ownmonth;
	}
	public void setOwnmonth(int ownmonth) {
		this.ownmonth = ownmonth;
	}
	
	
}
