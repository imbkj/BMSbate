package Model;

import java.math.BigDecimal;

public class FinanceZYTModel {

	private Integer id;
	private String payid;
	private String city;
	private String institution;
	private String ownmonth;
	private String company;
	private String shortname;
	private String cid;
	private String gid;
	private String idcard;
	private String zytId;
	private String zytCid;
	private String payOwnmonth;
	private String financeOwnmonth;
	private String payType;
	private String name;
	private BigDecimal ylcpRadix;
	private BigDecimal ylopRadix;
	private BigDecimal jlcpRadix;
	private BigDecimal jlopRadix;
	private BigDecimal gscpRadix;
	private BigDecimal gsopRadix;
	private BigDecimal sycpRadix;
	private BigDecimal syopRadix;
	private BigDecimal syucpRadix;
	private BigDecimal syuopRadix;
	private BigDecimal gjjcpRadix;
	private BigDecimal gjjopRadix;
	private BigDecimal gbccpRadix;
	private BigDecimal gbcopRadix;

	private BigDecimal ylcp;
	private BigDecimal ylop;
	private BigDecimal yltotal;
	private BigDecimal jlcp;
	private BigDecimal jlop;
	private BigDecimal jltotal;
	private BigDecimal gscp;
	private BigDecimal gsop;
	private BigDecimal gstotal;
	private BigDecimal sycp;
	private BigDecimal syop;
	private BigDecimal sytotal;
	private BigDecimal syucp;
	private BigDecimal syuop;
	private BigDecimal syutotal;
	private BigDecimal gjjcp;
	private BigDecimal gjjop;
	private BigDecimal gjjtotal;
	private BigDecimal gbccp;
	private BigDecimal gbcop;
	private BigDecimal gbctotal;

	private BigDecimal flTotal;
	private BigDecimal otherTotal;
	private BigDecimal fileTotal;
	private BigDecimal feeTotal;
	private BigDecimal sbTotal;
	private BigDecimal gjjTotal;
	private BigDecimal total;
	private String bz;
	private String productName;
	private String productFee;
	private String uid;
	private String compactType;

	private String state;
	private String addname;
	private String addtime;

	private String content;
	private String fee;
	private String kmId;

	public FinanceZYTModel() {
		ylcpRadix = BigDecimal.ZERO;
		ylopRadix = BigDecimal.ZERO;
		jlcpRadix = BigDecimal.ZERO;
		jlopRadix = BigDecimal.ZERO;
		gscpRadix = BigDecimal.ZERO;
		gsopRadix = BigDecimal.ZERO;
		sycpRadix = BigDecimal.ZERO;
		syopRadix = BigDecimal.ZERO;
		syucpRadix = BigDecimal.ZERO;
		syuopRadix = BigDecimal.ZERO;
		gjjcpRadix = BigDecimal.ZERO;
		gjjopRadix = BigDecimal.ZERO;
		gbccpRadix = BigDecimal.ZERO;
		gbcopRadix = BigDecimal.ZERO;
		flTotal = BigDecimal.ZERO;
		otherTotal = BigDecimal.ZERO;
		fileTotal = BigDecimal.ZERO;
		feeTotal = BigDecimal.ZERO;
		sbTotal = BigDecimal.ZERO;
		gjjTotal = BigDecimal.ZERO;
		total = BigDecimal.ZERO;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getPayid() {
		return payid;
	}

	public void setPayid(String payid) {
		this.payid = payid;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getInstitution() {
		return institution;
	}

	public void setInstitution(String institution) {
		this.institution = institution;
	}

	public String getCompany() {
		return company;
	}

	public void setCompany(String company) {
		this.company = company;
	}

	public String getCid() {
		return cid;
	}

	public void setCid(String cid) {
		this.cid = cid;
	}

	public String getGid() {
		return gid;
	}

	public void setGid(String gid) {
		this.gid = gid;
	}

	public String getIdcard() {
		return idcard;
	}

	public void setIdcard(String idcard) {
		this.idcard = idcard;
	}

	public String getZytId() {
		return zytId;
	}

	public void setZytId(String zytId) {
		this.zytId = zytId;
	}

	public String getZytCid() {
		return zytCid;
	}

	public void setZytCid(String zytCid) {
		this.zytCid = zytCid;
	}

	public String getPayOwnmonth() {
		return payOwnmonth;
	}

	public void setPayOwnmonth(String payOwnmonth) {
		this.payOwnmonth = payOwnmonth;
	}

	public String getFinanceOwnmonth() {
		return financeOwnmonth;
	}

	public void setFinanceOwnmonth(String financeOwnmonth) {
		this.financeOwnmonth = financeOwnmonth;
	}

	public String getPayType() {
		return payType;
	}

	public void setPayType(String payType) {
		this.payType = payType;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public BigDecimal getYlcpRadix() {
		return ylcpRadix.setScale(2, BigDecimal.ROUND_HALF_UP);
	}

	public void setYlcpRadix(BigDecimal ylcpRadix) {
		this.ylcpRadix = ylcpRadix;
	}

	public BigDecimal getYlopRadix() {
		return ylopRadix.setScale(2, BigDecimal.ROUND_HALF_UP);
	}

	public void setYlopRadix(BigDecimal ylopRadix) {
		this.ylopRadix = ylopRadix;
	}

	public BigDecimal getJlcpRadix() {
		return jlcpRadix.setScale(2, BigDecimal.ROUND_HALF_UP);
	}

	public void setJlcpRadix(BigDecimal jlcpRadix) {
		this.jlcpRadix = jlcpRadix;
	}

	public BigDecimal getJlopRadix() {
		return jlopRadix.setScale(2, BigDecimal.ROUND_HALF_UP);
	}

	public void setJlopRadix(BigDecimal jlopRadix) {
		this.jlopRadix = jlopRadix;
	}

	public BigDecimal getGscpRadix() {
		return gscpRadix.setScale(2, BigDecimal.ROUND_HALF_UP);
	}

	public void setGscpRadix(BigDecimal gscpRadix) {
		this.gscpRadix = gscpRadix;
	}

	public BigDecimal getGsopRadix() {
		return gsopRadix.setScale(2, BigDecimal.ROUND_HALF_UP);
	}

	public void setGsopRadix(BigDecimal gsopRadix) {
		this.gsopRadix = gsopRadix;
	}

	public BigDecimal getSycpRadix() {
		return sycpRadix.setScale(2, BigDecimal.ROUND_HALF_UP);
	}

	public void setSycpRadix(BigDecimal sycpRadix) {
		this.sycpRadix = sycpRadix;
	}

	public BigDecimal getSyopRadix() {
		return syopRadix.setScale(2, BigDecimal.ROUND_HALF_UP);
	}

	public void setSyopRadix(BigDecimal syopRadix) {
		this.syopRadix = syopRadix;
	}

	public BigDecimal getSyucpRadix() {
		return syucpRadix.setScale(2, BigDecimal.ROUND_HALF_UP);
	}

	public void setSyucpRadix(BigDecimal syucpRadix) {
		this.syucpRadix = syucpRadix;
	}

	public BigDecimal getSyuopRadix() {
		return syuopRadix.setScale(2, BigDecimal.ROUND_HALF_UP);
	}

	public void setSyuopRadix(BigDecimal syuopRadix) {
		this.syuopRadix = syuopRadix;
	}

	public BigDecimal getGjjcpRadix() {
		return gjjcpRadix.setScale(2, BigDecimal.ROUND_HALF_UP);
	}

	public void setGjjcpRadix(BigDecimal gjjcpRadix) {
		this.gjjcpRadix = gjjcpRadix;
	}

	public BigDecimal getGjjopRadix() {
		return gjjopRadix.setScale(2, BigDecimal.ROUND_HALF_UP);
	}

	public void setGjjopRadix(BigDecimal gjjopRadix) {
		this.gjjopRadix = gjjopRadix;
	}

	public BigDecimal getGbccpRadix() {
		return gbccpRadix.setScale(2, BigDecimal.ROUND_HALF_UP);
	}

	public void setGbccpRadix(BigDecimal gbccpRadix) {
		this.gbccpRadix = gbccpRadix;
	}

	public BigDecimal getGbcopRadix() {
		return gbcopRadix.setScale(2, BigDecimal.ROUND_HALF_UP);
	}

	public void setGbcopRadix(BigDecimal gbcopRadix) {
		this.gbcopRadix = gbcopRadix;
	}

	public BigDecimal getYlcp() {
		return ylcp.setScale(2, BigDecimal.ROUND_HALF_UP);
	}

	public void setYlcp(BigDecimal ylcp) {
		this.ylcp = ylcp;
	}

	public BigDecimal getYlop() {
		return ylop.setScale(2, BigDecimal.ROUND_HALF_UP);
	}

	public void setYlop(BigDecimal ylop) {
		this.ylop = ylop;
	}

	public BigDecimal getYltotal() {
		return yltotal.setScale(2, BigDecimal.ROUND_HALF_UP);
	}

	public void setYltotal(BigDecimal yltotal) {
		this.yltotal = yltotal;
	}

	public BigDecimal getJlcp() {
		return jlcp.setScale(2, BigDecimal.ROUND_HALF_UP);
	}

	public void setJlcp(BigDecimal jlcp) {
		this.jlcp = jlcp;
	}

	public BigDecimal getJlop() {
		return jlop.setScale(2, BigDecimal.ROUND_HALF_UP);
	}

	public void setJlop(BigDecimal jlop) {
		this.jlop = jlop;
	}

	public BigDecimal getJltotal() {
		return jltotal.setScale(2, BigDecimal.ROUND_HALF_UP);
	}

	public void setJltotal(BigDecimal jltotal) {
		this.jltotal = jltotal;
	}

	public BigDecimal getGscp() {
		return gscp.setScale(2, BigDecimal.ROUND_HALF_UP);
	}

	public void setGscp(BigDecimal gscp) {
		this.gscp = gscp;
	}

	public BigDecimal getGsop() {
		return gsop.setScale(2, BigDecimal.ROUND_HALF_UP);
	}

	public void setGsop(BigDecimal gsop) {
		this.gsop = gsop;
	}

	public BigDecimal getGstotal() {
		return gstotal.setScale(2, BigDecimal.ROUND_HALF_UP);
	}

	public void setGstotal(BigDecimal gstotal) {
		this.gstotal = gstotal;
	}

	public BigDecimal getSycp() {
		return sycp.setScale(2, BigDecimal.ROUND_HALF_UP);
	}

	public void setSycp(BigDecimal sycp) {
		this.sycp = sycp;
	}

	public BigDecimal getSyop() {
		return syop.setScale(2, BigDecimal.ROUND_HALF_UP);
	}

	public void setSyop(BigDecimal syop) {
		this.syop = syop;
	}

	public BigDecimal getSytotal() {
		return sytotal.setScale(2, BigDecimal.ROUND_HALF_UP);
	}

	public void setSytotal(BigDecimal sytotal) {
		this.sytotal = sytotal;
	}

	public BigDecimal getSyucp() {
		return syucp.setScale(2, BigDecimal.ROUND_HALF_UP);
	}

	public void setSyucp(BigDecimal syucp) {
		this.syucp = syucp;
	}

	public BigDecimal getSyuop() {
		return syuop.setScale(2, BigDecimal.ROUND_HALF_UP);
	}

	public void setSyuop(BigDecimal syuop) {
		this.syuop = syuop;
	}

	public BigDecimal getSyutotal() {
		return syutotal.setScale(2, BigDecimal.ROUND_HALF_UP);
	}

	public void setSyutotal(BigDecimal syutotal) {
		this.syutotal = syutotal;
	}

	public BigDecimal getGjjcp() {
		return gjjcp.setScale(2, BigDecimal.ROUND_HALF_UP);
	}

	public void setGjjcp(BigDecimal gjjcp) {
		this.gjjcp = gjjcp;
	}

	public BigDecimal getGjjop() {
		return gjjop.setScale(2, BigDecimal.ROUND_HALF_UP);
	}

	public void setGjjop(BigDecimal gjjop) {
		this.gjjop = gjjop;
	}

	public BigDecimal getGjjtotal() {
		return gjjtotal.setScale(2, BigDecimal.ROUND_HALF_UP);
	}

	public void setGjjtotal(BigDecimal gjjtotal) {
		this.gjjtotal = gjjtotal;
	}

	public BigDecimal getGbccp() {
		return gbccp.setScale(2, BigDecimal.ROUND_HALF_UP);
	}

	public void setGbccp(BigDecimal gbccp) {
		this.gbccp = gbccp;
	}

	public BigDecimal getGbcop() {
		return gbcop.setScale(2, BigDecimal.ROUND_HALF_UP);
	}

	public void setGbcop(BigDecimal gbcop) {
		this.gbcop = gbcop;
	}

	public BigDecimal getGbctotal() {
		return gbctotal.setScale(2, BigDecimal.ROUND_HALF_UP);
	}

	public void setGbctotal(BigDecimal gbctotal) {
		this.gbctotal = gbctotal;
	}

	public BigDecimal getFlTotal() {
		return flTotal.setScale(2, BigDecimal.ROUND_HALF_UP);
	}

	public void setFlTotal(BigDecimal flTotal) {
		this.flTotal = flTotal;
	}

	public BigDecimal getOtherTotal() {
		return otherTotal.setScale(2, BigDecimal.ROUND_HALF_UP);
	}

	public void setOtherTotal(BigDecimal otherTotal) {
		this.otherTotal = otherTotal;
	}

	public BigDecimal getFileTotal() {
		return fileTotal.setScale(2, BigDecimal.ROUND_HALF_UP);
	}

	public void setFileTotal(BigDecimal fileTotal) {
		this.fileTotal = fileTotal;
	}

	public BigDecimal getFeeTotal() {
		return feeTotal.setScale(2, BigDecimal.ROUND_HALF_UP);
	}

	public void setFeeTotal(BigDecimal feeTotal) {
		this.feeTotal = feeTotal;
	}

	public BigDecimal getSbTotal() {
		return sbTotal.setScale(2, BigDecimal.ROUND_HALF_UP);
	}

	public void setSbTotal(BigDecimal sbTotal) {
		this.sbTotal = sbTotal;
	}

	public BigDecimal getGjjTotal() {
		return gjjTotal.setScale(2, BigDecimal.ROUND_HALF_UP);
	}

	public void setGjjTotal(BigDecimal gjjTotal) {
		this.gjjTotal = gjjTotal;
	}

	public BigDecimal getTotal() {
		return total.setScale(2, BigDecimal.ROUND_HALF_UP);
	}

	public void setTotal(BigDecimal total) {
		this.total = total;
	}

	public String getBz() {
		return bz;
	}

	public void setBz(String bz) {
		this.bz = bz;
	}

	public String getProductName() {
		return productName;
	}

	public void setProductName(String productName) {
		this.productName = productName;
	}

	public String getProductFee() {
		return productFee;
	}

	public void setProductFee(String productFee) {
		this.productFee = productFee;
	}

	public String getUid() {
		return uid;
	}

	public void setUid(String uid) {
		this.uid = uid;
	}

	public String getCompactType() {
		return compactType;
	}

	public void setCompactType(String compactType) {
		this.compactType = compactType;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public String getAddname() {
		return addname;
	}

	public void setAddname(String addname) {
		this.addname = addname;
	}

	public String getAddtime() {
		return addtime;
	}

	public void setAddtime(String addtime) {
		this.addtime = addtime;
	}

	public String getOwnmonth() {
		return ownmonth;
	}

	public void setOwnmonth(String ownmonth) {
		this.ownmonth = ownmonth;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getFee() {
		return fee;
	}

	public void setFee(String fee) {
		this.fee = fee;
	}

	public String getKmId() {
		return kmId;
	}

	public void setKmId(String kmId) {
		this.kmId = kmId;
	}

	public String getShortname() {
		return shortname;
	}

	public void setShortname(String shortname) {
		this.shortname = shortname;
	}

}
