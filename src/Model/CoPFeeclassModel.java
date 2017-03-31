package Model;

public class CoPFeeclassModel {

	private int cpfc_id;
	private String cpfc_name;

	public int getCpfc_id() {
		return cpfc_id;
	}

	public CoPFeeclassModel() {
		super();
	}

	public CoPFeeclassModel(int cpfc_id, String cpfc_name) {
		super();
		this.cpfc_id = cpfc_id;
		this.cpfc_name = cpfc_name;
	}

	public void setCpfc_id(int cpfc_id) {
		this.cpfc_id = cpfc_id;
	}

	public String getCpfc_name() {
		return cpfc_name;
	}

	public void setCpfc_name(String cpfc_name) {
		this.cpfc_name = cpfc_name;
	}
}
