package Model;

import java.sql.Timestamp;

public class CoShebaoInforChangeModel {
	// csic_id
	private Integer csic_id;
	// 变更表id
	private Integer csic_csbc_id;
	// 变更项目
	private String csic_changestyle;
	// 变更前数据
	private String csic_changef;
	// 变更后数据
	private String csic_changel;
	// csic_state
	private Integer csic_state;
	// csic_addtime
	private Timestamp csic_addtime;

	public Integer getCsic_id() {
		return csic_id;
	}

	public void setCsic_id(Integer csic_id) {
		this.csic_id = csic_id;
	}

	public String getCsic_changestyle() {
		return csic_changestyle;
	}

	public void setCsic_changestyle(String csic_changestyle) {
		this.csic_changestyle = csic_changestyle;
	}

	public String getCsic_changef() {
		return csic_changef;
	}

	public void setCsic_changef(String csic_changef) {
		this.csic_changef = csic_changef;
	}

	public String getCsic_changel() {
		return csic_changel;
	}

	public void setCsic_changel(String csic_changel) {
		this.csic_changel = csic_changel;
	}

	public Integer getCsic_state() {
		return csic_state;
	}

	public void setCsic_state(Integer csic_state) {
		this.csic_state = csic_state;
	}

	public Timestamp getCsic_addtime() {
		return csic_addtime;
	}

	public void setCsic_addtime(Timestamp csic_addtime) {
		this.csic_addtime = csic_addtime;
	}

	public Integer getCsic_csbc_id() {
		return csic_csbc_id;
	}

	public void setCsic_csbc_id(Integer csic_csbc_id) {
		this.csic_csbc_id = csic_csbc_id;
	}
}
