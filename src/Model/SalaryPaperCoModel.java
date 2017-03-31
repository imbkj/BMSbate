package Model;

/**
 * 工资单公司实体
 * 
 * @author Administrator
 * 
 */
public class SalaryPaperCoModel {

	private String coba_company; // 公司名称
	private String coba_shortname; // 公司简称
	private String coss_StringSendstate;

	private String coss_StringSectetend;

	private int coss_carbonCopy; // 是否抄送
	private String coss_StringcarbonCopy;

	// / 工资单公司设置主键
	private int coss_id;
	// / cid
	private int cid;
	// / 是否自动发送工资单，1表示自动，0表示否
	private int coss_sendstate;
	// / 是否抄送，1表示是，0表示否
	private int coss_carboncopy;
	// / 抄送地址，可多个，每个地址之间用","隔开
	private String coss_ccaddress;
	// / 是否密送，1为是，0表示否
	private int coss_secretsend;
	// / 密送地址，可多个，每个地址之间用","隔开
	private String coss_secretsendaddress;
	// / 工资单形式
	private String coss_payrollpapertype;
	// / 密封工资单发送时间约定
	private int coss_mfdate;
	// / 密封工资单交接人
	private String coss_mfname;
	// / 密封工资单是否需要底单0不要1要
	private int coss_ifmfdd;
	// / Email工资单发送时间
	private int coss_emailsenddate;
	// / Email工资单格式
	private String coss_emailtype;
	// / Email工资单暗送人
	private String coss_emailas;
	// / Email工资单抄送人
	private String coss_emailcs;
	// / Email工资单是否自动发送0 NO 1 YES
	private int coss_emailouto;

	private String coss_emailsenddatestr;

	private String coss_mfdatestr;

	private String coss_emailoutostr;

	private String coss_ifmfddstr;

	private String coba_shortspell;

	public int getCoss_mfdate() {
		return coss_mfdate;
	}

	public int getCoss_ifmfdd() {
		return coss_ifmfdd;
	}

	public int getCoss_emailsenddate() {
		return coss_emailsenddate;
	}

	public int getCoss_emailouto() {
		return coss_emailouto;
	}

	public String getCoss_emailoutostr() {
		return coss_emailoutostr;
	}

	public void setCoss_emailoutostr(String coss_emailoutostr) {
		this.coss_emailoutostr = coss_emailoutostr;
		switch (coss_emailoutostr) {
		case "否":
			this.coss_emailouto = 0;
			break;
		case "是":
			this.coss_emailouto = 1;
			break;
		default:
			this.coss_emailouto = 0;
			break;
		}
	}

	public String getCoss_ifmfddstr() {
		return coss_ifmfddstr;
	}

	public void setCoss_ifmfddstr(String coss_ifmfddstr) {
		this.coss_ifmfddstr = coss_ifmfddstr;
	}

	public String getCoss_emailsenddatestr() {
		return coss_emailsenddatestr;
	}

	public void setCoss_emailsenddatestr(String coss_emailsenddatestr) {
		this.coss_emailsenddatestr = coss_emailsenddatestr;
	}

	public String getCoss_mfdatestr() {
		return coss_mfdatestr;
	}

	public void setCoss_mfdatestr(String coss_mfdatestr) {
		this.coss_mfdatestr = coss_mfdatestr;
	}

	public int getCoss_carboncopy() {
		return coss_carboncopy;
	}

	public void setCoss_carboncopy(int coss_carboncopy) {
		this.coss_carboncopy = coss_carboncopy;
	}

	public String getCoss_payrollpapertype() {
		return coss_payrollpapertype;
	}

	public void setCoss_payrollpapertype(String coss_payrollpapertype) {
		this.coss_payrollpapertype = coss_payrollpapertype;
	}

	public int getCoss_mfdate(String str) {

		if (str != null) {
			return Integer.parseInt(str.replace("日", ""));
		} else {
			return 0;
		}
	}

	public void setCoss_mfdate(int coss_mfdate) {
		this.coss_mfdate = coss_mfdate;
	}

	public String getCoss_mfname() {
		return coss_mfname;
	}

	public void setCoss_mfname(String coss_mfname) {
		this.coss_mfname = coss_mfname;
	}

	public int getCoss_ifmfdd(String str) {

		if (str != null && str.equals("是")) {
			return 1;
		} else {
			return 0;
		}
	}

	public void setCoss_ifmfdd(int coss_ifmfdd) {
		this.coss_ifmfdd = coss_ifmfdd;
	}

	public int getCoss_emailsenddate(String str) {
		if (str != null) {
			return Integer.parseInt(str.replace("日", ""));
		} else {
			return 0;
		}
	}

	public void setCoss_emailsenddate(int coss_emailsenddate) {
		this.coss_emailsenddate = coss_emailsenddate;
	}

	public String getCoss_emailtype() {
		return coss_emailtype;
	}

	public void setCoss_emailtype(String coss_emailtype) {
		this.coss_emailtype = coss_emailtype;
	}

	public String getCoss_emailas() {
		return coss_emailas;
	}

	public void setCoss_emailas(String coss_emailas) {
		this.coss_emailas = coss_emailas;
	}

	public String getCoss_emailcs() {
		return coss_emailcs;
	}

	public void setCoss_emailcs(String coss_emailcs) {
		this.coss_emailcs = coss_emailcs;
	}

	public int getCoss_emailouto(String str) {

		if (str != null && str.equals("是")) {
			return 1;
		} else {
			return 0;
		}
	}

	public void setCoss_emailouto(int coss_emailouto) {
		this.coss_emailouto = coss_emailouto;
		switch (coss_emailouto) {
		case 0:
			this.coss_emailoutostr = "否";
			break;
		case 1:
			this.coss_emailoutostr = "是";
			break;
		default:
			this.coss_emailoutostr = "否";
			break;
		}
	}

	public int getCoss_id() {
		return coss_id;
	}

	public void setCoss_id(int coss_id) {
		this.coss_id = coss_id;
	}

	public String getCoss_StringSendstate() {
		return coss_StringSendstate;
	}

	public void setCoss_StringSendstate(String coss_StringSendstate) {
		this.coss_StringSendstate = coss_StringSendstate;
	}

	public String getCoss_StringSectetend() {
		return coss_StringSectetend;
	}

	public void setCoss_StringSectetend(String coss_StringSectetend) {
		this.coss_StringSectetend = coss_StringSectetend;
	}

	public String getCoss_StringcarbonCopy() {
		return coss_StringcarbonCopy;
	}

	public void setCoss_StringcarbonCopy(String coss_StringcarbonCopy) {
		this.coss_StringcarbonCopy = coss_StringcarbonCopy;
	}

	public String getCoba_company() {
		return coba_company;
	}

	public void setCoba_company(String coba_company) {
		this.coba_company = coba_company;
	}

	public int getCid() {
		return cid;
	}

	public void setCid(int cid) {
		this.cid = cid;
	}

	public int getCoss_sendstate() {
		return coss_sendstate;
	}

	public void setCoss_sendstate(int coss_sendstate) {
		this.coss_sendstate = coss_sendstate;
	}

	public int getCoss_secretsend() {
		return coss_secretsend;
	}

	public void setCoss_secretsend(int coss_secretsend) {
		this.coss_secretsend = coss_secretsend;
	}

	public String getCoss_secretsendaddress() {
		return coss_secretsendaddress;
	}

	public void setCoss_secretsendaddress(String coss_secretsendaddress) {
		this.coss_secretsendaddress = coss_secretsendaddress;
	}

	public String getCoss_ccaddress() {
		return coss_ccaddress;
	}

	public void setCoss_ccaddress(String coss_ccaddress) {
		this.coss_ccaddress = coss_ccaddress;
	}

	public int getCoss_carbonCopy() {
		return coss_carbonCopy;
	}

	public void setCoss_carbonCopy(int coss_carbonCopy) {
		this.coss_carbonCopy = coss_carbonCopy;
	}

	public String getCoba_shortname() {
		return coba_shortname;
	}

	public void setCoba_shortname(String coba_shortname) {
		this.coba_shortname = coba_shortname;
	}

	public String getCoba_shortspell() {
		return coba_shortspell;
	}

	public void setCoba_shortspell(String coba_shortspell) {
		this.coba_shortspell = coba_shortspell;
	}

}
