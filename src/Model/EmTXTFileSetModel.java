package Model;

import java.math.BigDecimal;
import java.text.DecimalFormat;

public class EmTXTFileSetModel {
	private final DecimalFormat df = new DecimalFormat("#.00");
	private BigDecimal zero = BigDecimal.ZERO;
	private int etfs_id;
	private BigDecimal etfs_balance = zero;
	private BigDecimal etfs_remaining = zero;
	private String etfs_addtime;
	private String etfs_addname;

	public EmTXTFileSetModel() {
		super();
	}

	public int getEtfs_id() {
		return etfs_id;
	}

	public void setEtfs_id(int etfs_id) {
		this.etfs_id = etfs_id;
	}

	public BigDecimal getEtfs_balance() {
		return etfs_balance;
	}

	public void setEtfs_balance(BigDecimal etfs_balance) {
		this.etfs_balance = new BigDecimal(df.format(etfs_balance));
	}

	public BigDecimal getEtfs_remaining() {
		return etfs_remaining;
	}

	public void setEtfs_remaining(BigDecimal etfs_remaining) {
		this.etfs_remaining = new BigDecimal(df.format(etfs_remaining));
	}

	public String getEtfs_addtime() {
		return etfs_addtime;
	}

	public void setEtfs_addtime(String etfs_addtime) {
		this.etfs_addtime = etfs_addtime;
	}

	public String getEtfs_addname() {
		return etfs_addname;
	}

	public void setEtfs_addname(String etfs_addname) {
		this.etfs_addname = etfs_addname;
	}

}
