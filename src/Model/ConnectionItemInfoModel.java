package Model;

public class ConnectionItemInfoModel {
	private int ciin_id;
	private String ciin_name;
	private String ciin_remark;
	
	
	
	public ConnectionItemInfoModel() {
		super();
	}

	
	
	public ConnectionItemInfoModel(int ciin_id, String ciin_name,
			String ciin_remark) {
		super();
		this.ciin_id = ciin_id;
		this.ciin_name = ciin_name;
		this.ciin_remark = ciin_remark;
	}



	public String getCiin_remark() {
		return ciin_remark;
	}


	public void setCiin_remark(String ciin_remark) {
		this.ciin_remark = ciin_remark;
	}


	public int getCiin_id() {
		return ciin_id;
	}
	public void setCiin_id(int ciin_id) {
		this.ciin_id = ciin_id;
	}
	public String getCiin_name() {
		return ciin_name;
	}
	public void setCiin_name(String ciin_name) {
		this.ciin_name = ciin_name;
	}
	
	
}
