package Model;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.NumberFormat;

public class SbGatherModel {
	private DecimalFormat myformat = new DecimalFormat("0.00");
	private Integer cid;
	private String coba_company;
	private BigDecimal sbktotal;
	private BigDecimal cfsa_total;
	private String coba_client;
	private String coba_ufid2;
	private BigDecimal sbbjtotal;
	private String coba_ufclass;
	private String coba_hsufclass;
	private String clientclass;
	private Integer ownmonth;
	private BigDecimal hstotal;
	private BigDecimal hsbjtotal;
	private String coab_name;
	private Integer finance_ownmonth;

	public Integer getCid() {
		return cid;
	}

	public void setCid(Integer cid) {
		this.cid = cid;
	}

	public String getCoba_company() {
		return coba_company;
	}

	public void setCoba_company(String coba_company) {
		this.coba_company = coba_company;
	}

	public BigDecimal getSbktotal() {
		return sbktotal;
	}

	public void setSbktotal(BigDecimal sbktotal) {
		try {
			if (sbktotal == null) {
				sbktotal = BigDecimal.ZERO;
			}
			sbktotal = new BigDecimal(myformat.format(sbktotal).toString());
		} catch (Exception e) {
			e.printStackTrace();
		}
		this.sbktotal = sbktotal;
	}

	public BigDecimal getCfsa_total() {
		return cfsa_total;
	}

	public void setCfsa_total(BigDecimal cfsa_total) {
		if (cfsa_total == null) {
			cfsa_total = BigDecimal.ZERO;
		}
		cfsa_total = new BigDecimal(myformat.format(cfsa_total).toString());
		this.cfsa_total = cfsa_total;
	}

	public String getCoba_client() {
		return coba_client;
	}

	public void setCoba_client(String coba_client) {
		this.coba_client = coba_client;
	}

	public String getCoba_ufid2() {
		return coba_ufid2;
	}

	public void setCoba_ufid2(String coba_ufid2) {
		this.coba_ufid2 = coba_ufid2;
	}

	public BigDecimal getSbbjtotal() {
		return sbbjtotal;
	}

	public void setSbbjtotal(BigDecimal sbbjtotal) {
		if (sbbjtotal == null) {
			sbbjtotal = BigDecimal.ZERO;
		}
		sbbjtotal = new BigDecimal(myformat.format(sbbjtotal).toString());
		this.sbbjtotal = sbbjtotal;
	}

	public String getCoba_ufclass() {
		return coba_ufclass;
	}

	public void setCoba_ufclass(String coba_ufclass) {
		if (coba_ufclass != null && coba_ufclass.equals("224105")) {
			setClientclass("AF");
			setCoba_hsufclass("224123");
		} else if (coba_ufclass != null && coba_ufclass.equals("224106")) {
			setClientclass("FS");
			setCoba_hsufclass("224124");
		} else {
			setClientclass("");
			setCoba_hsufclass("");
		}
		this.coba_ufclass = coba_ufclass;
	}

	public String getClientclass() {
		return clientclass;
	}

	public void setClientclass(String clientclass) {
		this.clientclass = clientclass;
	}

	public Integer getOwnmonth() {
		return ownmonth;
	}

	public void setOwnmonth(Integer ownmonth) {
		this.ownmonth = ownmonth;
	}

	public BigDecimal getHstotal() {
		return hstotal;
	}

	public void setHstotal(BigDecimal hstotal) {
		if (hstotal == null) {
			hstotal = BigDecimal.ZERO;
		}
		hstotal = new BigDecimal(myformat.format(hstotal).toString());
		this.hstotal = hstotal;
	}

	public BigDecimal getHsbjtotal() {
		return hsbjtotal;
	}

	public void setHsbjtotal(BigDecimal hsbjtotal) {
		if (hsbjtotal == null) {
			hsbjtotal = BigDecimal.ZERO;
		}
		hsbjtotal = new BigDecimal(myformat.format(hsbjtotal).toString());
		this.hsbjtotal = hsbjtotal;
	}

	public String getCoab_name() {
		return coab_name;
	}

	public void setCoab_name(String coab_name) {
		this.coab_name = coab_name;
	}

	public String getCoba_hsufclass() {
		return coba_hsufclass;
	}

	public void setCoba_hsufclass(String coba_hsufclass) {
		this.coba_hsufclass = coba_hsufclass;
	}

	public Integer getFinance_ownmonth() {
		return finance_ownmonth;
	}

	public void setFinance_ownmonth(Integer finance_ownmonth) {
		this.finance_ownmonth = finance_ownmonth;
	}
	
}
