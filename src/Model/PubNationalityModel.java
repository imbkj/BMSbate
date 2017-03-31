package Model;

public class PubNationalityModel {
	private int id;
	private String puna_name;
	private int hid;

	public PubNationalityModel() {
		super();
	}

	public PubNationalityModel(int id, String puna_name, int hid) {
		super();
		this.id = id;
		this.puna_name = puna_name;
		this.hid = hid;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getPuna_name() {
		return puna_name;
	}

	public void setPuna_name(String puna_name) {
		this.puna_name = puna_name;
	}

	public int getHid() {
		return hid;
	}

	public void setHid(int hid) {
		this.hid = hid;
	}

}
