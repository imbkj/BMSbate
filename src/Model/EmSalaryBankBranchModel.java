package Model;

public class EmSalaryBankBranchModel {
	private Integer esbb_id;
	private Integer esba_id;
	private String esbb_name;
	private String esbb_code;
	private Integer esbb_state;
	
	private String esba_name;//所属银行
	
	
	public EmSalaryBankBranchModel() {
		super();
	}
	public Integer getEsbb_id() {
		return esbb_id;
	}
	public void setEsbb_id(Integer esbb_id) {
		this.esbb_id = esbb_id;
	}
	public Integer getEsba_id() {
		return esba_id;
	}
	public void setEsba_id(Integer esba_id) {
		this.esba_id = esba_id;
	}
	public String getEsbb_name() {
		return esbb_name;
	}
	public void setEsbb_name(String esbb_name) {
		this.esbb_name = esbb_name;
	}
	public String getEsbb_code() {
		return esbb_code;
	}
	public void setEsbb_code(String esbb_code) {
		this.esbb_code = esbb_code;
	}
	public Integer getEsbb_state() {
		return esbb_state;
	}
	public void setEsbb_state(Integer esbb_state) {
		this.esbb_state = esbb_state;
	}
	public String getEsba_name() {
		return esba_name;
	}
	public void setEsba_name(String esba_name) {
		this.esba_name = esba_name;
	}
	
	
}
