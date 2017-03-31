package Model;

public class SocialInsuranceStandardModel {
	private int sist_id;
	private int sist_type;
	private String sist_standard;

	public SocialInsuranceStandardModel() {
		super();
	}

	public SocialInsuranceStandardModel(int sist_id, int sist_type,
			String sist_standard) {
		super();
		this.sist_id = sist_id;
		this.sist_type = sist_type;
		this.sist_standard = sist_standard;
	}

	public int getSist_id() {
		return sist_id;
	}

	public void setSist_id(int sist_id) {
		this.sist_id = sist_id;
	}

	public int getSist_type() {
		return sist_type;
	}

	public void setSist_type(int sist_type) {
		this.sist_type = sist_type;
	}

	public String getSist_standard() {
		return sist_standard;
	}

	public void setSist_standard(String sist_standard) {
		this.sist_standard = sist_standard;
	}

}
