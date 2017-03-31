package Model;

public class CoFinanceMonthlyBillTempletModel {
	private int cfmt_id;
	private String cfmt_name;
	private String cfmt_prefix;
	private int cfmt_order;
	private int cfmt_state;

	public CoFinanceMonthlyBillTempletModel() {
		super();
	}

	public int getCfmt_id() {
		return cfmt_id;
	}

	public void setCfmt_id(int cfmt_id) {
		this.cfmt_id = cfmt_id;
	}

	public String getCfmt_name() {
		return cfmt_name;
	}

	public void setCfmt_name(String cfmt_name) {
		this.cfmt_name = cfmt_name;
	}

	public String getCfmt_prefix() {
		return cfmt_prefix;
	}

	public void setCfmt_prefix(String cfmt_prefix) {
		this.cfmt_prefix = cfmt_prefix;
	}

	public int getCfmt_order() {
		return cfmt_order;
	}

	public void setCfmt_order(int cfmt_order) {
		this.cfmt_order = cfmt_order;
	}

	public int getCfmt_state() {
		return cfmt_state;
	}

	public void setCfmt_state(int cfmt_state) {
		this.cfmt_state = cfmt_state;
	}

}
