package Model;

import java.math.BigDecimal;
import java.text.DecimalFormat;

public class CoFinanceAgencyWriteOffInfoModel {
	private final DecimalFormat df = new DecimalFormat("#.00");
	private int cawi_id;
	private int cawi_cawo_id;
	private int cawi_type;
	private int ownmonth;
	private BigDecimal cawi_writeOffEx;

	public CoFinanceAgencyWriteOffInfoModel() {
		super();
	}

	public int getCawi_id() {
		return cawi_id;
	}

	public void setCawi_id(int cawi_id) {
		this.cawi_id = cawi_id;
	}

	public int getCawi_cawo_id() {
		return cawi_cawo_id;
	}

	public void setCawi_cawo_id(int cawi_cawo_id) {
		this.cawi_cawo_id = cawi_cawo_id;
	}

	public int getCawi_type() {
		return cawi_type;
	}

	public void setCawi_type(int cawi_type) {
		this.cawi_type = cawi_type;
	}

	public int getOwnmonth() {
		return ownmonth;
	}

	public void setOwnmonth(int ownmonth) {
		this.ownmonth = ownmonth;
	}

	public BigDecimal getCawi_writeOffEx() {
		return cawi_writeOffEx;
	}

	public void setCawi_writeOffEx(BigDecimal cawi_writeOffEx) {
		this.cawi_writeOffEx = new BigDecimal(df.format(cawi_writeOffEx));
	}

}
