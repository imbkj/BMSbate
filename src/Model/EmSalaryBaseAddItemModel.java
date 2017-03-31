package Model;

import java.math.BigDecimal;
import java.text.DecimalFormat;

public class EmSalaryBaseAddItemModel {
	private int csii_id;
	private String csii_item_name;
	private String csii_item_anothername;
	private String csii_item_englishname;
	private int ciin_id;
	private String csii_col;
	private String cfda;
	private int csii_fd_state;
	private int csii_csd_state;
	private String csgi_content;
	private int if_gd;
	private int cfda_id;
	private BigDecimal amount;
	private final DecimalFormat df = new DecimalFormat("#.00");

	public EmSalaryBaseAddItemModel() {
		super();
	}

	public EmSalaryBaseAddItemModel(int csii_id, String csii_item_name,
			int ciin_id, String csii_col, String cfda, String csgi_content,
			int if_gd) {
		super();
		this.csii_id = csii_id;
		this.csii_item_name = csii_item_name;
		this.ciin_id = ciin_id;
		this.csii_col = csii_col;
		this.cfda = cfda;
		this.csgi_content = csgi_content;
		this.if_gd = if_gd;
	}

	
	public String getCsii_item_anothername() {
		return csii_item_anothername;
	}

	public void setCsii_item_anothername(String csii_item_anothername) {
		this.csii_item_anothername = csii_item_anothername;
	}

	public String getCsii_item_englishname() {
		return csii_item_englishname;
	}

	public void setCsii_item_englishname(String csii_item_englishname) {
		this.csii_item_englishname = csii_item_englishname;
	}

	public int getCsii_id() {
		return csii_id;
	}

	public void setCsii_id(int csii_id) {
		this.csii_id = csii_id;
	}

	public String getCsii_item_name() {
		return csii_item_name;
	}

	
	public int getCsii_csd_state() {
		return csii_csd_state;
	}

	public void setCsii_csd_state(int csii_csd_state) {
		this.csii_csd_state = csii_csd_state;
	}

	public void setCsii_item_name(String csii_item_name) {
		this.csii_item_name = csii_item_name;
	}

	public int getCiin_id() {
		return ciin_id;
	}

	public void setCiin_id(int ciin_id) {
		this.ciin_id = ciin_id;
	}

	public String getCsii_col() {
		return csii_col;
	}

	public void setCsii_col(String csii_col) {
		this.csii_col = csii_col;
	}

	public String getCfda() {
		return cfda;
	}

	public void setCfda(String cfda) {
		this.cfda = cfda;
	}

	public String getCsgi_content() {
		return csgi_content;
	}

	public void setCsgi_content(String csgi_content) {
		this.csgi_content = csgi_content;
	}

	public int getIf_gd() {
		return if_gd;
	}

	public void setIf_gd(int if_gd) {
		this.if_gd = if_gd;
	}

	public BigDecimal getAmount() {
		return amount;
	}

	public void setAmount(BigDecimal amount) {
		this.amount = new BigDecimal(df.format(amount));
	}

	public int getCsii_fd_state() {
		return csii_fd_state;
	}

	public void setCsii_fd_state(int csii_fd_state) {
		this.csii_fd_state = csii_fd_state;
	}

	public int getCfda_id() {
		return cfda_id;
	}

	public void setCfda_id(int cfda_id) {
		this.cfda_id = cfda_id;
	}

}
