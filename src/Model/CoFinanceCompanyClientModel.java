package Model;

import java.math.BigDecimal;
import java.text.DecimalFormat;

public class CoFinanceCompanyClientModel {
	private final DecimalFormat df = new DecimalFormat("#.00");
	private int cid;
	private String coba_company;
	private String coba_shortname;
	private String coba_client;
	private String log_tel;
	private String log_email;
	private String coba_clientmanager;
	private String lm_tel;
	private String lm_email;
	private String ownmonth;
	private BigDecimal receivable;

	public CoFinanceCompanyClientModel() {
		super();
	}

	public int getCid() {
		return cid;
	}

	public void setCid(int cid) {
		this.cid = cid;
	}

	public String getCoba_company() {
		return coba_company;
	}

	public void setCoba_company(String coba_company) {
		this.coba_company = coba_company;
	}

	public String getCoba_shortname() {
		return coba_shortname;
	}

	public void setCoba_shortname(String coba_shortname) {
		this.coba_shortname = coba_shortname;
	}

	public String getCoba_client() {
		return coba_client;
	}

	public void setCoba_client(String coba_client) {
		this.coba_client = coba_client;
	}

	public String getLog_tel() {
		return log_tel;
	}

	public void setLog_tel(String log_tel) {
		this.log_tel = log_tel;
	}

	public String getLog_email() {
		return log_email;
	}

	public void setLog_email(String log_email) {
		this.log_email = log_email;
	}

	public String getCoba_clientmanager() {
		return coba_clientmanager;
	}

	public void setCoba_clientmanager(String coba_clientmanager) {
		this.coba_clientmanager = coba_clientmanager;
	}

	public String getLm_tel() {
		return lm_tel;
	}

	public void setLm_tel(String lm_tel) {
		this.lm_tel = lm_tel;
	}

	public String getLm_email() {
		return lm_email;
	}

	public void setLm_email(String lm_email) {
		this.lm_email = lm_email;
	}

	public String getOwnmonth() {
		return ownmonth;
	}

	public void setOwnmonth(String ownmonth) {
		this.ownmonth = ownmonth;
	}

	public BigDecimal getReceivable() {
		return receivable;
	}

	public void setReceivable(BigDecimal receivable) {
		this.receivable = new BigDecimal(df.format(receivable));
	}

}
