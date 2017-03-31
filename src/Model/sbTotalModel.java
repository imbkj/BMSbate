package Model;

import java.math.BigDecimal;

public class sbTotalModel implements Comparable<sbTotalModel> {

	private Integer id;
	private Integer cid;
	private String uid;
	private String company;
	private BigDecimal fee;
	private String client;
	private String compacttype;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getCid() {
		return cid;
	}

	public void setCid(Integer cid) {
		this.cid = cid;
	}

	public String getUid() {
		return uid;
	}

	public void setUid(String uid) {
		this.uid = uid;
	}

	public String getCompany() {
		return company;
	}

	public void setCompany(String company) {
		this.company = company;
	}

	public BigDecimal getFee() {
		return fee;
	}

	public void setFee(BigDecimal fee) {
		this.fee = fee;
	}

	public String getClient() {
		return client;
	}

	public void setClient(String client) {
		this.client = client;
	}

	public String getCompacttype() {
		return compacttype;
	}

	public void setCompacttype(String compacttype) {
		this.compacttype = compacttype;
	}

	@Override
	public int compareTo(sbTotalModel obj) {
		// TODO Auto-generated method stub
		sbTotalModel m = obj;
		return this.cid - m.getCid();
	}

}
