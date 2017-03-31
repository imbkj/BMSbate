package Model;

import java.math.BigDecimal;

public class EmSheBaoFinanceModel {
	private BigDecimal zero = new BigDecimal(0);
	private int cid;
	private int ownmonth;
	private String company; // 公司名称
	private String shebaoid; // 单位编号
	private int shebao_count; // 全部参保人数
	private int bms_count; // 全部系统人数
	private int p_balance; // 全部差额
	private int szsi_0_cou;// 中智大户参保人数
	private int sb_0_cou;// 中智大户系统人数
	private int ce_0_cou;// 中智大户差额
	private int szsi_1_cou = 0;// 独立户参保人数
	private int sb_1_cou = 0;// 独立户系统人数
	private int ce_1_cou = 0;// 独立户差额
	private int szsi_2_cou = 0;// 中智大户(委托)参保人数
	private int sb_2_cou = 0;// 中智大户(委托)系统人数
	private int ce_2_cou = 0;// 中智大户(委托)差额
	private BigDecimal shebao_cost = zero; // 社保局扣款金额
	private BigDecimal bms_cost = zero; // 系统扣款金额
	private BigDecimal c_balance = zero; // 差额
	private BigDecimal szsi_0_cost = zero; // 中智大户社保局扣款金额
	private BigDecimal sb_0_cost = zero; // 中智大户系统扣款金额
	private BigDecimal ce_0_cost = zero; // 中智大户差额
	private BigDecimal szsi_1_cost = zero; // 独立户社保局扣款金额
	private BigDecimal sb_1_cost = zero; // 独立户系统扣款金额
	private BigDecimal ce_1_cost = zero; // 独立户差额
	private BigDecimal szsi_2_cost = zero; // 中智大户(委托)社保局扣款金额
	private BigDecimal sb_2_cost = zero; // 中智大户(委托)系统扣款金额
	private BigDecimal ce_2_cost = zero; // 中智大户(委托)差额
	private String client; // 客服代表
	private String addname; // 更新人
	private String state;// 到账情况

	public EmSheBaoFinanceModel() {
		super();
	}

	
	public EmSheBaoFinanceModel(BigDecimal zero, int cid, int ownmonth,
			String company, String shebaoid, int shebao_count, int bms_count,
			int p_balance, int szsi_0_cou, int sb_0_cou, int ce_0_cou,
			int szsi_1_cou, int sb_1_cou, int ce_1_cou, int szsi_2_cou,
			int sb_2_cou, int ce_2_cou, BigDecimal shebao_cost,
			BigDecimal bms_cost, BigDecimal c_balance, BigDecimal szsi_0_cost,
			BigDecimal sb_0_cost, BigDecimal ce_0_cost, BigDecimal szsi_1_cost,
			BigDecimal sb_1_cost, BigDecimal ce_1_cost, BigDecimal szsi_2_cost,
			BigDecimal sb_2_cost, BigDecimal ce_2_cost, String client,
			String addname, String state) {
		super();
		this.zero = zero;
		this.cid = cid;
		this.ownmonth = ownmonth;
		this.company = company;
		this.shebaoid = shebaoid;
		this.shebao_count = shebao_count;
		this.bms_count = bms_count;
		this.p_balance = p_balance;
		this.szsi_0_cou = szsi_0_cou;
		this.sb_0_cou = sb_0_cou;
		this.ce_0_cou = ce_0_cou;
		this.szsi_1_cou = szsi_1_cou;
		this.sb_1_cou = sb_1_cou;
		this.ce_1_cou = ce_1_cou;
		this.szsi_2_cou = szsi_2_cou;
		this.sb_2_cou = sb_2_cou;
		this.ce_2_cou = ce_2_cou;
		this.shebao_cost = shebao_cost;
		this.bms_cost = bms_cost;
		this.c_balance = c_balance;
		this.szsi_0_cost = szsi_0_cost;
		this.sb_0_cost = sb_0_cost;
		this.ce_0_cost = ce_0_cost;
		this.szsi_1_cost = szsi_1_cost;
		this.sb_1_cost = sb_1_cost;
		this.ce_1_cost = ce_1_cost;
		this.szsi_2_cost = szsi_2_cost;
		this.sb_2_cost = sb_2_cost;
		this.ce_2_cost = ce_2_cost;
		this.client = client;
		this.addname = addname;
		this.state = state;
	}


	public int getCid() {
		return cid;
	}


	public void setCid(int cid) {
		this.cid = cid;
	}


	public int getOwnmonth() {
		return ownmonth;
	}


	public void setOwnmonth(int ownmonth) {
		this.ownmonth = ownmonth;
	}


	public BigDecimal getSzsi_0_cost() {
		return szsi_0_cost;
	}

	public void setSzsi_0_cost(BigDecimal szsi_0_cost) {
		this.szsi_0_cost = szsi_0_cost;
	}



	public BigDecimal getSb_0_cost() {
		return sb_0_cost;
	}

	public void setSb_0_cost(BigDecimal sb_0_cost) {
		this.sb_0_cost = sb_0_cost;
	}



	public BigDecimal getCe_0_cost() {
		return ce_0_cost;
	}



	public void setCe_0_cost(BigDecimal ce_0_cost) {
		this.ce_0_cost = ce_0_cost;
	}



	public BigDecimal getSzsi_1_cost() {
		return szsi_1_cost;
	}



	public void setSzsi_1_cost(BigDecimal szsi_1_cost) {
		this.szsi_1_cost = szsi_1_cost;
	}



	public BigDecimal getSb_1_cost() {
		return sb_1_cost;
	}



	public void setSb_1_cost(BigDecimal sb_1_cost) {
		this.sb_1_cost = sb_1_cost;
	}



	public BigDecimal getCe_1_cost() {
		return ce_1_cost;
	}



	public void setCe_1_cost(BigDecimal ce_1_cost) {
		this.ce_1_cost = ce_1_cost;
	}



	public BigDecimal getSzsi_2_cost() {
		return szsi_2_cost;
	}



	public void setSzsi_2_cost(BigDecimal szsi_2_cost) {
		this.szsi_2_cost = szsi_2_cost;
	}



	public BigDecimal getSb_2_cost() {
		return sb_2_cost;
	}



	public void setSb_2_cost(BigDecimal sb_2_cost) {
		this.sb_2_cost = sb_2_cost;
	}



	public BigDecimal getCe_2_cost() {
		return ce_2_cost;
	}



	public void setCe_2_cost(BigDecimal ce_2_cost) {
		this.ce_2_cost = ce_2_cost;
	}



	public int getSzsi_1_cou() {
		return szsi_1_cou;
	}

	public void setSzsi_1_cou(int szsi_1_cou) {
		this.szsi_1_cou = szsi_1_cou;
	}

	public int getSb_1_cou() {
		return sb_1_cou;
	}

	public void setSb_1_cou(int sb_1_cou) {
		this.sb_1_cou = sb_1_cou;
	}

	public int getCe_1_cou() {
		return ce_1_cou;
	}

	public void setCe_1_cou(int ce_1_cou) {
		this.ce_1_cou = ce_1_cou;
	}

	public int getSzsi_0_cou() {
		return szsi_0_cou;
	}

	public void setSzsi_0_cou(int szsi_0_cou) {
		this.szsi_0_cou = szsi_0_cou;
	}

	public int getSb_0_cou() {
		return sb_0_cou;
	}

	public void setSb_0_cou(int sb_0_cou) {
		this.sb_0_cou = sb_0_cou;
	}

	public int getCe_0_cou() {
		return ce_0_cou;
	}

	public void setCe_0_cou(int ce_0_cou) {
		this.ce_0_cou = ce_0_cou;
	}

	public int getSzsi_2_cou() {
		return szsi_2_cou;
	}

	public void setSzsi_2_cou(int szsi_2_cou) {
		this.szsi_2_cou = szsi_2_cou;
	}

	public int getSb_2_cou() {
		return sb_2_cou;
	}

	public void setSb_2_cou(int sb_2_cou) {
		this.sb_2_cou = sb_2_cou;
	}

	public int getCe_2_cou() {
		return ce_2_cou;
	}

	public void setCe_2_cou(int ce_2_cou) {
		this.ce_2_cou = ce_2_cou;
	}

	public BigDecimal getZero() {
		return zero;
	}

	public void setZero(BigDecimal zero) {
		this.zero = zero;
	}

	public String getCompany() {
		return company;
	}

	public void setCompany(String company) {
		this.company = company;
	}

	public String getShebaoid() {
		return shebaoid;
	}

	public void setShebaoid(String shebaoid) {
		this.shebaoid = shebaoid;
	}

	public int getShebao_count() {
		return shebao_count;
	}

	public void setShebao_count(int shebao_count) {
		this.shebao_count = shebao_count;
	}

	public int getBms_count() {
		return bms_count;
	}

	public void setBms_count(int bms_count) {
		this.bms_count = bms_count;
	}

	public int getP_balance() {
		return p_balance;
	}

	public void setP_balance(int p_balance) {
		this.p_balance = p_balance;
	}

	public BigDecimal getShebao_cost() {
		return shebao_cost;
	}

	public void setShebao_cost(BigDecimal shebao_cost) {
		this.shebao_cost = shebao_cost;
	}

	public BigDecimal getBms_cost() {
		return bms_cost;
	}

	public void setBms_cost(BigDecimal bms_cost) {
		this.bms_cost = bms_cost;
	}

	public BigDecimal getC_balance() {
		return c_balance;
	}

	public void setC_balance(BigDecimal c_balance) {
		this.c_balance = c_balance;
	}

	public String getClient() {
		return client;
	}

	public void setClient(String client) {
		this.client = client;
	}

	public String getAddname() {
		return addname;
	}

	public void setAddname(String addname) {
		this.addname = addname;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

}
