package Model;

public class EmBenefitRelationModel {
	private Integer ebre_id;
	private Integer ebre_embf_id;
	private Integer ebre_supp_id;
	private String ebre_addtime;
	private String ebre_addname;
	private Integer ebre_state;

	private String embf_name;
	private String supp_name;

	public Integer getEbre_id() {
		return ebre_id;
	}

	public void setEbre_id(Integer ebre_id) {
		this.ebre_id = ebre_id;
	}

	public Integer getEbre_embf_id() {
		return ebre_embf_id;
	}

	public void setEbre_embf_id(Integer ebre_embf_id) {
		this.ebre_embf_id = ebre_embf_id;
	}

	public Integer getEbre_supp_id() {
		return ebre_supp_id;
	}

	public void setEbre_supp_id(Integer ebre_supp_id) {
		this.ebre_supp_id = ebre_supp_id;
	}

	public String getEbre_addtime() {
		return ebre_addtime;
	}

	public void setEbre_addtime(String ebre_addtime) {
		this.ebre_addtime = ebre_addtime;
	}

	public String getEbre_addname() {
		return ebre_addname;
	}

	public void setEbre_addname(String ebre_addname) {
		this.ebre_addname = ebre_addname;
	}

	public Integer getEbre_state() {
		return ebre_state;
	}

	public void setEbre_state(Integer ebre_state) {
		this.ebre_state = ebre_state;
	}

	public String getEmbf_name() {
		return embf_name;
	}

	public void setEmbf_name(String embf_name) {
		this.embf_name = embf_name;
	}

	public String getSupp_name() {
		return supp_name;
	}

	public void setSupp_name(String supp_name) {
		this.supp_name = supp_name;
	}

}
