package Model;

import java.math.BigDecimal;

public class SocialInsuranceClassInfoViewModel {
	private int sicl_id;
	private String sicl_class;
	private String sicl_name;
	private String sicl_payunit;
	private int sicl_ifclass;
	private int sicl_ifname;
	private int siai_id;
	private int siai_sial_id;
	private int siai_side_id;
	private String siai_side_idstr;
	private String siai_basic_u;
	private String siai_basic_d;
	private String siai_deposit_u;
	private String siai_deposit_d;
	private BigDecimal siai_basic_ud;
	private BigDecimal siai_basic_dd;
	private BigDecimal siai_deposit_ud;
	private BigDecimal siai_deposit_dd;
	private String siai_proportion;
	private String siai_algorithm;
	private String siai_remark;
	private String siai_state;
	private String side_decimal;
	private BigDecimal fee = new BigDecimal(0);
	private BigDecimal radix = new BigDecimal(0);
	// 用于台账页面显示
	private int soin_id;
	private String city;
	private String soin_title;

	private SocialInsuranceClassInfoViewModel om;

	public SocialInsuranceClassInfoViewModel() {
		super();
	}

	public SocialInsuranceClassInfoViewModel(int sicl_id, String sicl_class,
			String sicl_name, String sicl_payunit, int sicl_ifclass,
			int sicl_ifname, int siai_id, int siai_sial_id, int siai_side_id,
			String siai_basic_u, String siai_basic_d, String siai_deposit_u,
			String siai_deposit_d, String siai_proportion,
			String siai_algorithm, String siai_remark, String side_decimal) {
		super();
		this.sicl_id = sicl_id;
		this.sicl_class = sicl_class;
		this.sicl_name = sicl_name;
		this.sicl_payunit = sicl_payunit;
		this.sicl_ifclass = sicl_ifclass;
		this.sicl_ifname = sicl_ifname;
		this.siai_id = siai_id;
		this.siai_sial_id = siai_sial_id;
		this.siai_side_id = siai_side_id;
		this.siai_basic_u = siai_basic_u;
		this.siai_basic_d = siai_basic_d;
		this.siai_deposit_u = siai_deposit_u;
		this.siai_deposit_d = siai_deposit_d;
		this.siai_proportion = siai_proportion;
		this.siai_algorithm = siai_algorithm;
		this.siai_remark = siai_remark;
		this.side_decimal = side_decimal;
	}

	public SocialInsuranceClassInfoViewModel(int sicl_id, String sicl_class,
			String sicl_name, String sicl_payunit, int sicl_ifclass,
			int sicl_ifname, int siai_id, int siai_sial_id, int siai_side_id,
			BigDecimal siai_basic_ud, BigDecimal siai_basic_dd,
			BigDecimal siai_deposit_ud, BigDecimal siai_deposit_dd,
			String siai_proportion, String siai_algorithm, String siai_remark,
			String side_decimal) {
		super();
		this.sicl_id = sicl_id;
		this.sicl_class = sicl_class;
		this.sicl_name = sicl_name;
		this.sicl_payunit = sicl_payunit;
		this.sicl_ifclass = sicl_ifclass;
		this.sicl_ifname = sicl_ifname;
		this.siai_id = siai_id;
		this.siai_sial_id = siai_sial_id;
		this.siai_side_id = siai_side_id;
		this.siai_basic_ud = siai_basic_ud;
		this.siai_basic_dd = siai_basic_dd;
		this.siai_deposit_ud = siai_deposit_ud;
		this.siai_deposit_dd = siai_deposit_dd;
		this.siai_proportion = siai_proportion;
		this.siai_algorithm = siai_algorithm;
		this.siai_remark = siai_remark;
		this.side_decimal = side_decimal;
	}

	public SocialInsuranceClassInfoViewModel(Integer sicl_id,
			String sicl_class, String sicl_name, String sicl_payunit,
			BigDecimal siai_basic_ud, BigDecimal siai_basic_dd,
			BigDecimal siai_deposit_ud, BigDecimal siai_deposit_dd,
			String siai_proportion, String side_decimal) {
		super();
		this.sicl_id = sicl_id;
		this.sicl_class = sicl_class;
		this.sicl_name = sicl_name;
		this.sicl_payunit = sicl_payunit;
		this.siai_basic_ud = siai_basic_ud;
		this.siai_basic_dd = siai_basic_dd;
		this.siai_deposit_ud = siai_deposit_ud;
		this.siai_deposit_dd = siai_deposit_dd;
		this.siai_proportion = siai_proportion;
		this.side_decimal = side_decimal;
	}

	public SocialInsuranceClassInfoViewModel(int sicl_id, String sicl_class,
			String sicl_name, String sicl_payunit, int sicl_ifclass,
			int sicl_ifname) {
		super();
		this.sicl_id = sicl_id;
		this.sicl_class = sicl_class;
		this.sicl_name = sicl_name;
		this.sicl_payunit = sicl_payunit;
		this.sicl_ifclass = sicl_ifclass;
		this.sicl_ifname = sicl_ifname;
	}

	public SocialInsuranceClassInfoViewModel(int sicl_id, String sicl_class,
			String sicl_name, String sicl_payunit, int sicl_ifclass,
			int sicl_ifname, int siai_id, int siai_sial_id, int siai_side_id,
			String siai_side_idstr, String siai_basic_u, String siai_basic_d,
			String siai_deposit_u, String siai_deposit_d,
			String siai_proportion, String siai_algorithm, String siai_remark,
			String siai_state) {
		super();
		this.sicl_id = sicl_id;
		this.sicl_class = sicl_class;
		this.sicl_name = sicl_name;
		this.sicl_payunit = sicl_payunit;
		this.sicl_ifclass = sicl_ifclass;
		this.sicl_ifname = sicl_ifname;
		this.siai_id = siai_id;
		this.siai_sial_id = siai_sial_id;
		this.siai_side_id = siai_side_id;
		this.siai_side_idstr = siai_side_idstr;
		this.siai_basic_u = siai_basic_u;
		this.siai_basic_d = siai_basic_d;
		this.siai_deposit_u = siai_deposit_u;
		this.siai_deposit_d = siai_deposit_d;
		this.siai_proportion = siai_proportion;
		this.siai_algorithm = siai_algorithm;
		this.siai_remark = siai_remark;
		this.siai_state = siai_state;
	}

	public int getSicl_id() {
		return sicl_id;
	}

	public void setSicl_id(int sicl_id) {
		this.sicl_id = sicl_id;
	}

	public String getSicl_class() {
		return sicl_class;
	}

	public void setSicl_class(String sicl_class) {
		this.sicl_class = sicl_class;
	}

	public String getSicl_name() {
		return sicl_name;
	}

	public void setSicl_name(String sicl_name) {
		this.sicl_name = sicl_name;
	}

	public String getSicl_payunit() {
		return sicl_payunit;
	}

	public void setSicl_payunit(String sicl_payunit) {
		this.sicl_payunit = sicl_payunit;
	}

	public int getSicl_ifclass() {
		return sicl_ifclass;
	}

	public void setSicl_ifclass(int sicl_ifclass) {
		this.sicl_ifclass = sicl_ifclass;
	}

	public int getSicl_ifname() {
		return sicl_ifname;
	}

	public void setSicl_ifname(int sicl_ifname) {
		this.sicl_ifname = sicl_ifname;
	}

	public int getSiai_id() {
		return siai_id;
	}

	public void setSiai_id(int siai_id) {
		this.siai_id = siai_id;
	}

	public int getSiai_sial_id() {
		return siai_sial_id;
	}

	public void setSiai_sial_id(int siai_sial_id) {
		this.siai_sial_id = siai_sial_id;
	}

	public int getSiai_side_id() {
		return siai_side_id;
	}

	public void setSiai_side_id(int siai_side_id) {
		this.siai_side_id = siai_side_id;
	}

	public String getSiai_side_idstr() {
		return siai_side_idstr;
	}

	public void setSiai_side_idstr(String siai_side_idstr) {
		this.siai_side_idstr = siai_side_idstr;
	}

	public String getSiai_basic_u() {
		return siai_basic_u;
	}

	public void setSiai_basic_u(String siai_basic_u) {
		this.siai_basic_u = siai_basic_u;
	}

	public String getSiai_basic_d() {
		return siai_basic_d;
	}

	public void setSiai_basic_d(String siai_basic_d) {
		this.siai_basic_d = siai_basic_d;
	}

	public String getSiai_deposit_u() {
		return siai_deposit_u;
	}

	public void setSiai_deposit_u(String siai_deposit_u) {
		this.siai_deposit_u = siai_deposit_u;
	}

	public String getSiai_deposit_d() {
		return siai_deposit_d;
	}

	public void setSiai_deposit_d(String siai_deposit_d) {
		this.siai_deposit_d = siai_deposit_d;
	}

	public String getSiai_proportion() {
		return siai_proportion;
	}

	public void setSiai_proportion(String siai_proportion) {
		this.siai_proportion = siai_proportion;
	}

	public String getSiai_algorithm() {
		return siai_algorithm;
	}

	public void setSiai_algorithm(String siai_algorithm) {
		this.siai_algorithm = siai_algorithm;
	}

	public String getSiai_remark() {
		return siai_remark;
	}

	public void setSiai_remark(String siai_remark) {
		this.siai_remark = siai_remark;
	}

	public String getSiai_state() {
		return siai_state;
	}

	public void setSiai_state(String siai_state) {
		this.siai_state = siai_state;
	}

	public String getSide_decimal() {
		return side_decimal;
	}

	public void setSide_decimal(String side_decimal) {
		this.side_decimal = side_decimal;
	}

	public BigDecimal getSiai_basic_ud() {
		return siai_basic_ud;
	}

	public void setSiai_basic_ud(BigDecimal siai_basic_ud) {
		this.siai_basic_ud = siai_basic_ud;
	}

	public BigDecimal getSiai_basic_dd() {
		return siai_basic_dd;
	}

	public void setSiai_basic_dd(BigDecimal siai_basic_dd) {
		this.siai_basic_dd = siai_basic_dd;
	}

	public BigDecimal getSiai_deposit_ud() {
		return siai_deposit_ud;
	}

	public void setSiai_deposit_ud(BigDecimal siai_deposit_ud) {
		this.siai_deposit_ud = siai_deposit_ud;
	}

	public BigDecimal getSiai_deposit_dd() {
		return siai_deposit_dd;
	}

	public void setSiai_deposit_dd(BigDecimal siai_deposit_dd) {
		this.siai_deposit_dd = siai_deposit_dd;
	}

	public BigDecimal getFee() {
		return fee;
	}

	public void setFee(BigDecimal fee) {
		this.fee = fee;
	}

	public BigDecimal getRadix() {
		return radix;
	}

	public void setRadix(BigDecimal radix) {
		this.radix = radix;
	}

	public int getSoin_id() {
		return soin_id;
	}

	public void setSoin_id(int soin_id) {
		this.soin_id = soin_id;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getSoin_title() {
		return soin_title;
	}

	public void setSoin_title(String soin_title) {
		this.soin_title = soin_title;
	}

	public SocialInsuranceClassInfoViewModel getOm() {
		return om;
	}

	public void setOm(SocialInsuranceClassInfoViewModel om) {
		this.om = om;
	}

}
