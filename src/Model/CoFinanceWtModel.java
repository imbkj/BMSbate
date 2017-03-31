package Model;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class CoFinanceWtModel {
	private final DecimalFormat df = new DecimalFormat("#.00");
	private int cid;
	private int ownmonth;
	private String coba_name;
	private BigDecimal cofi_fee = BigDecimal.ZERO;
	private BigDecimal cofz_fee = BigDecimal.ZERO;
	private int emFiCount;
	private int emFzCount;
	private String company;
	private String coba_client;
	private List<EmFinanceWtModel> efWtList=new ArrayList<EmFinanceWtModel>();
	
	private String name;//委托城市
	private String coab_name;//委托机构
	private Integer cocoid;//合同id
	private Integer cabc_id;//委托机构与城市关联id
	public CoFinanceWtModel() {
		super();
	}

	public int getCid() {
		return cid;
	}

	public void setCid(int cid) {
		this.cid = cid;
	}

	public int getOwnmonth() {
		return ownmonth;
	}

	public void setOwnmonth(int ownmonth) {
		this.ownmonth = ownmonth;
	}

	public String getCoba_name() {
		return coba_name;
	}

	public void setCoba_name(String coba_name) {
		this.coba_name = coba_name;
	}

	public BigDecimal getCofi_fee() {
		return cofi_fee;
	}

	public void setCofi_fee(BigDecimal cofi_fee) {
		this.cofi_fee = new BigDecimal(df.format(cofi_fee));
	}

	public BigDecimal getCofz_fee() {
		return cofz_fee;
	}

	public void setCofz_fee(BigDecimal cofz_fee) {
		this.cofz_fee = new BigDecimal(df.format(cofz_fee));
	}

	public int getEmFiCount() {
		return emFiCount;
	}

	public void setEmFiCount(int emFiCount) {
		this.emFiCount = emFiCount;
	}

	public int getEmFzCount() {
		return emFzCount;
	}

	public void setEmFzCount(int emFzCount) {
		this.emFzCount = emFzCount;
	}

	public List<EmFinanceWtModel> getEfWtList() {
		return efWtList;
	}

	public void setEfWtList(List<EmFinanceWtModel> efWtList) {
		this.efWtList = efWtList;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getCoab_name() {
		return coab_name;
	}

	public void setCoab_name(String coab_name) {
		this.coab_name = coab_name;
	}

	public Integer getCocoid() {
		return cocoid;
	}

	public void setCocoid(Integer cocoid) {
		this.cocoid = cocoid;
	}

	public Integer getCabc_id() {
		return cabc_id;
	}

	public void setCabc_id(Integer cabc_id) {
		this.cabc_id = cabc_id;
	}

	public String getCompany() {
		return company;
	}

	public void setCompany(String company) {
		this.company = company;
	}

	public String getCoba_client() {
		return coba_client;
	}

	public void setCoba_client(String coba_client) {
		this.coba_client = coba_client;
	}
	
}
