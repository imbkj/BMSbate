package Model;

import java.util.List;

public class EmFinanceCommissionOutCityModel {
	private String city;
	private List<EmFinanceCommissionOutModel> soinList;
	private List<EmFinanceCommissionOutModel> emFinanceCommissionOutList;
	private List<EmFinanceCommissionOutDetailItemModel> detailItemList;
	private List<EmFinanceCommissionOutDetailItemModel> productItemList;

	public EmFinanceCommissionOutCityModel() {
		super();
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public List<EmFinanceCommissionOutModel> getSoinList() {
		return soinList;
	}

	public void setSoinList(List<EmFinanceCommissionOutModel> soinList) {
		this.soinList = soinList;
	}

	public List<EmFinanceCommissionOutModel> getEmFinanceCommissionOutList() {
		return emFinanceCommissionOutList;
	}

	public void setEmFinanceCommissionOutList(
			List<EmFinanceCommissionOutModel> emFinanceCommissionOutList) {
		this.emFinanceCommissionOutList = emFinanceCommissionOutList;
	}

	public List<EmFinanceCommissionOutDetailItemModel> getDetailItemList() {
		return detailItemList;
	}

	public void setDetailItemList(
			List<EmFinanceCommissionOutDetailItemModel> detailItemList) {
		this.detailItemList = detailItemList;
	}

	public List<EmFinanceCommissionOutDetailItemModel> getProductItemList() {
		return productItemList;
	}

	public void setProductItemList(
			List<EmFinanceCommissionOutDetailItemModel> productItemList) {
		this.productItemList = productItemList;
	}

}
