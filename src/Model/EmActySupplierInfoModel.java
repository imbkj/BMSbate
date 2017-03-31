package Model;

import java.util.List;

import org.zkoss.zul.ListModelList;

public class EmActySupplierInfoModel {
	// / supp_Id
	private Integer supp_id;
	// / supp_Nmae
	private String supp_name;
	// / supp_Address
	private String supp_address;
	// / supp_Mobile
	private String supp_mobile;
	private String supp_addname;
	private String supp_addtime;
	private String supp_website;
	private Integer connum;
	private Integer pronum;
	private String supp_remark;
	private Integer supp_state;
	private String productMold;
	private String supp_type;
	private String types;
	private boolean checked = false;

	private List<EmActySupContactManInfoModel> manList;
	private List<EmActySupProductInfoModel> productList;
	private List<String> itemList;
	private ListModelList<String> itemList2;

	public Integer getSupp_id() {
		return supp_id;
	}

	public void setSupp_id(Integer supp_id) {
		this.supp_id = supp_id;
	}

	public String getSupp_name() {
		return supp_name;
	}

	public void setSupp_name(String supp_name) {
		this.supp_name = supp_name;
	}

	public String getSupp_address() {
		return supp_address;
	}

	public void setSupp_address(String supp_address) {
		this.supp_address = supp_address;
	}

	public String getSupp_mobile() {
		return supp_mobile;
	}

	public void setSupp_mobile(String supp_mobile) {
		this.supp_mobile = supp_mobile;
	}

	public String getSupp_addname() {
		return supp_addname;
	}

	public void setSupp_addname(String supp_addname) {
		this.supp_addname = supp_addname;
	}

	public String getSupp_addtime() {
		return supp_addtime;
	}

	public void setSupp_addtime(String supp_addtime) {
		this.supp_addtime = supp_addtime;
	}

	public String getSupp_website() {
		return supp_website;
	}

	public void setSupp_website(String supp_website) {
		this.supp_website = supp_website;
	}

	public Integer getConnum() {
		return connum;
	}

	public void setConnum(Integer connum) {
		this.connum = connum;
	}

	public Integer getPronum() {
		return pronum;
	}

	public void setPronum(Integer pronum) {
		this.pronum = pronum;
	}

	public String getSupp_remark() {
		return supp_remark;
	}

	public void setSupp_remark(String supp_remark) {
		this.supp_remark = supp_remark;
	}

	public Integer getSupp_state() {
		return supp_state;
	}

	public void setSupp_state(Integer supp_state) {
		this.supp_state = supp_state;
	}

	public String getProductMold() {
		return productMold;
	}

	public void setProductMold(String productMold) {
		this.productMold = productMold;
	}

	public List<EmActySupContactManInfoModel> getManList() {
		return manList;
	}

	public void setManList(List<EmActySupContactManInfoModel> manList) {
		this.manList = manList;
	}

	public List<EmActySupProductInfoModel> getProductList() {
		return productList;
	}

	public void setProductList(List<EmActySupProductInfoModel> productList) {
		this.productList = productList;
	}

	public String getSupp_type() {
		return supp_type;
	}

	public void setSupp_type(String supp_type) {
		this.supp_type = supp_type;
	}

	public List<String> getItemList() {
		return itemList;
	}

	public void setItemList(List<String> itemList) {
		this.itemList = itemList;
	}

	public ListModelList<String> getItemList2() {
		return itemList2;
	}

	public void setItemList2(ListModelList<String> itemList2) {
		this.itemList2 = itemList2;
	}

	public String getTypes() {
		return types;
	}

	public void setTypes(String types) {
		this.types = types;
	}

	public boolean isChecked() {
		return checked;
	}

	public void setChecked(boolean checked) {
		this.checked = checked;
	}

}
