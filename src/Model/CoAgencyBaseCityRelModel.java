package Model;

import java.math.BigDecimal;
import java.text.DecimalFormat;

public class CoAgencyBaseCityRelModel {
	private final DecimalFormat df = new DecimalFormat("#.00");
	private Integer cabc_id;
	private int coab_id;
	private String region;
	private String province;
	private String city;
	private int cabc_ifdefault;
	private int cabc_state;
	private String cabc_ifdefaultStr;
	private String cabc_stateStr;
	private int hzAgencyCount;
	private Integer id;
	private BigDecimal cabc_fee;
	private boolean ifdefault;
	private String coab_name;

	public CoAgencyBaseCityRelModel() {
		super();
	}

	public void setHzInfo() {
		// 是否为默认机构
		switch (cabc_ifdefault) {
		case 0:
			cabc_ifdefaultStr = "否";
			break;
		case 1:
			cabc_ifdefaultStr = "是";
			break;
		}
		// 合作情况
		switch (cabc_state) {
		case 0:
			cabc_stateStr = "取消合作";
			break;
		case 1:
			cabc_stateStr = "合作中";
			break;
		}
	}

	public Integer getCabc_id() {
		return cabc_id;
	}

	public void setCabc_id(Integer cabc_id) {
		this.cabc_id = cabc_id;
	}

	public int getCoab_id() {
		return coab_id;
	}

	public void setCoab_id(int coab_id) {
		this.coab_id = coab_id;
	}

	public String getRegion() {
		return region;
	}

	public void setRegion(String region) {
		this.region = region;
	}

	public String getProvince() {
		return province;
	}

	public void setProvince(String province) {
		this.province = province;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public int getCabc_ifdefault() {
		return cabc_ifdefault;
	}

	public void setCabc_ifdefault(int cabc_ifdefault) {
		this.cabc_ifdefault = cabc_ifdefault;
		// 是否为默认机构
		switch (cabc_ifdefault) {
		case 0:
			cabc_ifdefaultStr = "否";
			ifdefault = false;
			break;
		case 1:
			cabc_ifdefaultStr = "是";
			ifdefault = true;
			break;
		}
	}

	public int getCabc_state() {
		return cabc_state;
	}

	public void setCabc_state(int cabc_state) {
		this.cabc_state = cabc_state;
	}

	public String getCabc_ifdefaultStr() {
		return cabc_ifdefaultStr;
	}

	public void setCabc_ifdefaultStr(String cabc_ifdefaultStr) {
		this.cabc_ifdefaultStr = cabc_ifdefaultStr;
	}

	public String getCabc_stateStr() {
		return cabc_stateStr;
	}

	public void setCabc_stateStr(String cabc_stateStr) {
		this.cabc_stateStr = cabc_stateStr;
	}

	public int getHzAgencyCount() {
		return hzAgencyCount;
	}

	public void setHzAgencyCount(int hzAgencyCount) {
		this.hzAgencyCount = hzAgencyCount;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public BigDecimal getCabc_fee() {
		return cabc_fee;
	}

	public void setCabc_fee(BigDecimal cabc_fee) {
		this.cabc_fee = new BigDecimal(df.format(cabc_fee));
	}

	public boolean isIfdefault() {
		return ifdefault;
	}

	public void setIfdefault(boolean ifdefault) {
		this.ifdefault = ifdefault;
	}

	public String getCoab_name() {
		return coab_name;
	}

	public void setCoab_name(String coab_name) {
		this.coab_name = coab_name;
	}

}
