package Model;

import java.util.Date;

public class SocialInsuranceModel {
	private Integer soin_id;
	private Integer sial_id;
	private Date sial_execdate;
	private String execdate;
	private String name;

	public Integer getSoin_id() {
		return soin_id;
	}

	public void setSoin_id(Integer soin_id) {
		this.soin_id = soin_id;
	}

	public Integer getSial_id() {
		return sial_id;
	}

	public void setSial_id(Integer sial_id) {
		this.sial_id = sial_id;
	}

	public Date getSial_execdate() {
		return sial_execdate;
	}

	public void setSial_execdate(Date sial_execdate) {
		this.sial_execdate = sial_execdate;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getExecdate() {
		return execdate;
	}

	public void setExecdate(String execdate) {
		this.execdate = execdate;
	}

}
