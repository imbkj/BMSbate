package Model;

public class CoSalaryItemAttributeModel {
	private int csia_id;
	private String csia_attribute;
	private String csia_ms;
	private String csia_remark;
	
	
	public CoSalaryItemAttributeModel() {
		super();
	}
	
	public CoSalaryItemAttributeModel(int csia_id, String csia_attribute,
			String csia_ms, String csia_remark) {
		super();
		this.csia_id = csia_id;
		this.csia_attribute = csia_attribute;
		this.csia_ms = csia_ms;
		this.csia_remark = csia_remark;
	}

	public int getCsia_id() {
		return csia_id;
	}
	public void setCsia_id(int csia_id) {
		this.csia_id = csia_id;
	}
	public String getCsia_attribute() {
		return csia_attribute;
	}
	public void setCsia_attribute(String csia_attribute) {
		this.csia_attribute = csia_attribute;
	}
	public String getCsia_ms() {
		return csia_ms;
	}
	public void setCsia_ms(String csia_ms) {
		this.csia_ms = csia_ms;
	}
	public String getCsia_remark() {
		return csia_remark;
	}
	public void setCsia_remark(String csia_remark) {
		this.csia_remark = csia_remark;
	}
	
	
}
