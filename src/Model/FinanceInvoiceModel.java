package Model;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import org.zkoss.zul.ListModelList;

public class FinanceInvoiceModel {
	private String djh;// 单据号
	private Date rq;// 日期
	private Integer fplx;// 发票类型
	private String fplxName;
	private String gfmc;// 购方名称
	private String gfsh;// 购方税号
	private String gfyhzh;// 购方银行账号
	private String gfdzdh;// 购方地址电话
	private String xcyhzh;// 销方银行账号
	private String xcdzdh;// 销方地址电话
	private String bz;// 备注
	private String kpr;// 开票人
	private String fhr;// 复核人
	private String skr;// 收款人

	private boolean display=false;
	private boolean openState;
	private BigDecimal total;
	private boolean printState=false;

	private List<FinanceInvoiceDetailModel> list = new ListModelList<>();

	public String getDjh() {
		return djh;
	}

	public void setDjh(String djh) {
		this.djh = djh;
	}

	public Date getRq() {
		return rq;
	}

	public void setRq(Date rq) {
		this.rq = rq;
	}

	public Integer getFplx() {
		return fplx;
	}

	public void setFplx(Integer fplx) {
		this.fplx = fplx;
	}

	public String getGfmc() {
		return gfmc;
	}

	public void setGfmc(String gfmc) {
		this.gfmc = gfmc;
	}

	public String getGfsh() {
		return gfsh;
	}

	public void setGfsh(String gfsh) {
		this.gfsh = gfsh;
	}

	public String getGfyhzh() {
		return gfyhzh;
	}

	public void setGfyhzh(String gfyhzh) {
		this.gfyhzh = gfyhzh;
	}

	public String getGfdzdh() {
		return gfdzdh;
	}

	public void setGfdzdh(String gfdzdh) {
		this.gfdzdh = gfdzdh;
	}

	public String getXcyhzh() {
		return xcyhzh;
	}

	public void setXcyhzh(String xcyhzh) {
		this.xcyhzh = xcyhzh;
	}

	public String getXcdzdh() {
		return xcdzdh;
	}

	public void setXcdzdh(String xcdzdh) {
		this.xcdzdh = xcdzdh;
	}

	public String getBz() {
		return bz;
	}

	public void setBz(String bz) {
		this.bz = bz;
	}

	public String getKpr() {
		return kpr;
	}

	public void setKpr(String kpr) {
		this.kpr = kpr;
	}

	public String getFhr() {
		return fhr;
	}

	public void setFhr(String fhr) {
		this.fhr = fhr;
	}

	public String getSkr() {
		return skr;
	}

	public void setSkr(String skr) {
		this.skr = skr;
	}

	public String getFplxName() {
		return fplxName;
	}

	public void setFplxName(String fplxName) {
		this.fplxName = fplxName;
	}

	public List<FinanceInvoiceDetailModel> getList() {
		return list;
	}

	public void setList(List<FinanceInvoiceDetailModel> list) {
		this.list = list;
	}

	public boolean isDisplay() {
		return display;
	}

	public void setDisplay(boolean display) {
		this.display = display;
	}

	public boolean isOpenState() {
		return openState;
	}

	public void setOpenState(boolean openState) {
		this.openState = openState;
	}

	public BigDecimal getTotal() {
		if (total != null) {
			total = total.setScale(2, BigDecimal.ROUND_HALF_UP);
		}
		return total;
	}

	public void setTotal(BigDecimal total) {
		this.total = total;
	}

	public boolean isPrintState() {
		return printState;
	}

	public void setPrintState(boolean printState) {
		this.printState = printState;
	}

}
