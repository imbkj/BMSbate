package Model;

import java.math.BigDecimal;

public class EmHouseDictModel {
	// / ID
	private Integer id;
	// / ownmonth
	private Integer ownmonth;
	// / SP
	private BigDecimal sp;
	// / SpUp
	private Integer spup;
	// / SpDown
	private Integer spdown;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getOwnmonth() {
		return ownmonth;
	}

	public void setOwnmonth(Integer ownmonth) {
		this.ownmonth = ownmonth;
	}

	public BigDecimal getSp() {
		return sp;
	}

	public void setSp(BigDecimal sp) {
		this.sp = sp;
	}

	public Integer getSpup() {
		return spup;
	}

	public void setSpup(Integer spup) {
		this.spup = spup;
	}

	public Integer getSpdown() {
		return spdown;
	}

	public void setSpdown(Integer spdown) {
		this.spdown = spdown;
	}

}
