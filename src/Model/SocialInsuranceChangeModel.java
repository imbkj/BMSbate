package Model;

import java.util.Date;

public class SocialInsuranceChangeModel {
	private int sich_id;
	private int sich_cabc_id;
	private int sich_change_type;
	private int sich_soin_id;
	private int sich_sial_id;
	private String sich_title;
	private String sich_ReturnReason;
	private String sich_Reason;
	private String sich_addname;
	private String sich_addtime;
	private String sich_examinedname;
	private String sich_examinedtime;
	private int sich_tapr_id;
	private int sich_state;
	private String sich_statestr;
	private String sich_change_typestr;
	private String city;
	private String coab_name;
	private String execdate;
	private Date sial_execdate;

	public SocialInsuranceChangeModel() {
		super();
	}

	
	
	public Date getSial_execdate() {
		return sial_execdate;
	}

	public void setSial_execdate(Date sial_execdate) {
		this.sial_execdate = sial_execdate;
	}



	public int getSich_id() {
		return sich_id;
	}

	public void setSich_id(int sich_id) {
		this.sich_id = sich_id;
	}

	public int getSich_cabc_id() {
		return sich_cabc_id;
	}

	public void setSich_cabc_id(int sich_cabc_id) {
		this.sich_cabc_id = sich_cabc_id;
	}

	public int getSich_change_type() {
		return sich_change_type;
	}

	public void setSich_change_type(int sich_change_type) {
		this.sich_change_type = sich_change_type;
		switch (sich_change_type) {
		case 1:
			sich_change_typestr = "新增标准";
			break;
		case 2:
			sich_change_typestr = "更新算法";
			break;
		case 3:
			sich_change_typestr = "调整算法";
			break;
		case 4:
			sich_change_typestr = "停用标准";
			break;
		case 5:
			sich_change_typestr = "停用算法";
			break;
		}
	}

	public int getSich_soin_id() {
		return sich_soin_id;
	}

	public void setSich_soin_id(int sich_soin_id) {
		this.sich_soin_id = sich_soin_id;
	}

	public int getSich_sial_id() {
		return sich_sial_id;
	}

	public void setSich_sial_id(int sich_sial_id) {
		this.sich_sial_id = sich_sial_id;
	}

	public String getSich_title() {
		return sich_title;
	}

	public void setSich_title(String sich_title) {
		this.sich_title = sich_title;
	}

	public String getSich_ReturnReason() {
		return sich_ReturnReason;
	}

	public void setSich_ReturnReason(String sich_ReturnReason) {
		this.sich_ReturnReason = sich_ReturnReason;
	}

	public String getSich_Reason() {
		return sich_Reason;
	}

	public void setSich_Reason(String sich_Reason) {
		this.sich_Reason = sich_Reason;
	}

	public String getSich_addname() {
		return sich_addname;
	}

	public void setSich_addname(String sich_addname) {
		this.sich_addname = sich_addname;
	}

	public String getSich_addtime() {
		return sich_addtime;
	}

	public void setSich_addtime(String sich_addtime) {
		this.sich_addtime = sich_addtime;
	}

	public int getSich_tapr_id() {
		return sich_tapr_id;
	}

	public void setSich_tapr_id(int sich_tapr_id) {
		this.sich_tapr_id = sich_tapr_id;
	}

	public int getSich_state() {
		return sich_state;
	}

	public void setSich_state(int sich_state) {
		this.sich_state = sich_state;
		switch (sich_state) {
		case 0:
			sich_statestr = "未审核";
			break;
		case 1:
			sich_statestr = "已生效";
			break;
		case 2:
			sich_statestr = "退回";
			break;
		case 3:
			sich_statestr = "无效";
			break;
		}
	}

	public String getSich_examinedname() {
		return sich_examinedname;
	}

	public void setSich_examinedname(String sich_examinedname) {
		this.sich_examinedname = sich_examinedname;
	}

	public String getSich_examinedtime() {
		return sich_examinedtime;
	}

	public void setSich_examinedtime(String sich_examinedtime) {
		this.sich_examinedtime = sich_examinedtime;
	}

	public String getSich_statestr() {
		return sich_statestr;
	}

	public void setSich_statestr(String sich_statestr) {
		this.sich_statestr = sich_statestr;
	}

	public String getSich_change_typestr() {
		return sich_change_typestr;
	}

	public void setSich_change_typestr(String sich_change_typestr) {
		this.sich_change_typestr = sich_change_typestr;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getCoab_name() {
		return coab_name;
	}

	public void setCoab_name(String coab_name) {
		this.coab_name = coab_name;
	}

	public String getExecdate() {
		return execdate;
	}

	public void setExecdate(String execdate) {
		this.execdate = execdate;
	}

}
