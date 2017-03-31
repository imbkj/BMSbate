package Model;

import java.math.BigDecimal;
import java.text.DecimalFormat;

public class EmFinanceProductItemListModel {
	private final DecimalFormat df = new DecimalFormat("#.00");
	private String className;
	private String name;
	private BigDecimal receivable = new BigDecimal(df.format(BigDecimal.ZERO));

	public EmFinanceProductItemListModel() {
		super();
	}

	public String getClassName() {
		return className;
	}

	public void setClassName(String className) {
		this.className = className;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public BigDecimal getReceivable() {
		return receivable;
	}

	public void setReceivable(BigDecimal receivable) {
		this.receivable = new BigDecimal(df.format(receivable));
	}

}
