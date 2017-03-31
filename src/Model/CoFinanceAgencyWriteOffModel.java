package Model;

import java.math.BigDecimal;
import java.text.DecimalFormat;

public class CoFinanceAgencyWriteOffModel {
	private final DecimalFormat df = new DecimalFormat("#.00");
	private int cawo_id;
	private int cawo_coab_id;
	private BigDecimal cawo_writeOffEx;
	private String cawo_addname;
	private String cawo_addtime;
	private int cawo_state;

	public CoFinanceAgencyWriteOffModel() {
		super();
	}

	public int getCawo_id() {
		return cawo_id;
	}

	public void setCawo_id(int cawo_id) {
		this.cawo_id = cawo_id;
	}

	public int getCawo_coab_id() {
		return cawo_coab_id;
	}

	public void setCawo_coab_id(int cawo_coab_id) {
		this.cawo_coab_id = cawo_coab_id;
	}

	public BigDecimal getCawo_writeOffEx() {
		return cawo_writeOffEx;
	}

	public void setCawo_writeOffEx(BigDecimal cawo_writeOffEx) {
		this.cawo_writeOffEx = new BigDecimal(df.format(cawo_writeOffEx));
	}

	public String getCawo_addname() {
		return cawo_addname;
	}

	public void setCawo_addname(String cawo_addname) {
		this.cawo_addname = cawo_addname;
	}

	public String getCawo_addtime() {
		return cawo_addtime;
	}

	public void setCawo_addtime(String cawo_addtime) {
		this.cawo_addtime = cawo_addtime;
	}

	public int getCawo_state() {
		return cawo_state;
	}

	public void setCawo_state(int cawo_state) {
		this.cawo_state = cawo_state;
	}

}
