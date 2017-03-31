package Model;

import java.util.Date;

public class CoHousingFundInforChangeModel {
	// cfic_id
	private Integer cfic_id;
	// cfic_chfc_id
	private Integer cfic_chfc_id;
	// cfic_changestyle
	private String cfic_changestyle;
	// 变更前
	private String cfic_changeold;
	// 变更后
	private String cfic_changenew;
	// cfic_state
	private Integer cfic_state;
	// cfic_addtime
	private Date cfic_addtime;
	// 核准类信息、备案类信息
	private String cfic_changetype;

	public final Integer getCfic_id() {
		return cfic_id;
	}

	public final Integer getCfic_chfc_id() {
		return cfic_chfc_id;
	}

	public final String getCfic_changestyle() {
		return cfic_changestyle;
	}

	public final String getCfic_changeold() {
		return cfic_changeold;
	}

	public final String getCfic_changenew() {
		return cfic_changenew;
	}

	public final Integer getCfic_state() {
		return cfic_state;
	}

	public final Date getCfic_addtime() {
		return cfic_addtime;
	}

	public final void setCfic_id(Integer cfic_id) {
		this.cfic_id = cfic_id;
	}

	public final void setCfic_chfc_id(Integer cfic_chfc_id) {
		this.cfic_chfc_id = cfic_chfc_id;
	}

	public final void setCfic_changestyle(String cfic_changestyle) {
		this.cfic_changestyle = cfic_changestyle;
	}

	public final void setCfic_changeold(String cfic_changeold) {
		this.cfic_changeold = cfic_changeold;
	}

	public final void setCfic_changenew(String cfic_changenew) {
		this.cfic_changenew = cfic_changenew;
	}

	public final void setCfic_state(Integer cfic_state) {
		this.cfic_state = cfic_state;
	}

	public final void setCfic_addtime(Date cfic_addtime) {
		this.cfic_addtime = cfic_addtime;
	}

	public String getCfic_changetype() {
		return cfic_changetype;
	}

	public void setCfic_changetype(String cfic_changetype) {
		this.cfic_changetype = cfic_changetype;
	}

}
