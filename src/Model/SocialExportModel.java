package Model;

import java.util.Map;

public class SocialExportModel {
	private String city;
	private Map<Integer, SocialInsuranceAlgorithmViewModel> soinMap;

	public SocialExportModel() {
		super();
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public Map<Integer, SocialInsuranceAlgorithmViewModel> getSoinMap() {
		return soinMap;
	}

	public void setSoinMap(
			Map<Integer, SocialInsuranceAlgorithmViewModel> soinMap) {
		this.soinMap = soinMap;
	}

}
