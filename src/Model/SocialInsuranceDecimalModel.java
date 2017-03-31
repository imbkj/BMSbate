package Model;
public class SocialInsuranceDecimalModel {
	private int side_id;
	private String side_decimal;

	public SocialInsuranceDecimalModel() {
		super();
	}

	public SocialInsuranceDecimalModel(int side_id, String side_decimal) {
		super();
		this.side_id = side_id;
		this.side_decimal = side_decimal;
	}

	public int getSide_id() {
		return side_id;
	}

	public void setSide_id(int side_id) {
		this.side_id = side_id;
	}

	public String getSide_decimal() {
		return side_decimal;
	}

	public void setSide_decimal(String side_decimal) {
		this.side_decimal = side_decimal;
	}

}
