package Model;

import java.math.BigDecimal;
import java.util.Date;


public class wtoutFeeModel {
	java.text.DecimalFormat   df   =new   java.text.DecimalFormat("#.00");
	
	private int wtot_feechangeid;
	private int wtot_feeid;
	private int cid;
	// / wtot_feetitle
	private String wtot_feetitle;
	// / wtot_fee
	private BigDecimal wtot_fee;
	// / wtot_addname
	private String wtot_addname;
	// / wtot_addtime
	private String wtot_addtime;
	// / wtot_examinenamekf
	private String wtot_examinenamekf;
	// / wtot_examinetimefk
	private String wtot_examinetimefk;
	// / wtot_editname
	private String wtot_editname;
	// / wtot_edittime
	private String wtot_edittime;
	// / wtot_remark
	private String wtot_remark;
	// / wtot_examinenameqg
	private String wtot_examinenameqg;
	// / wtot_examinetimeqg
	private String wtot_examinetimeqg;
	// / wtot_state
	private int wtot_state;

	private int coba_id;

	private String wtss_city;
	
	private String wtot_statestr;
	
	private String coba_company;

	private String coba_shortname;
	
	private String city;
	private String province;
	private String coab_namespell;
	private Integer cabc_ppc_id;
	
	private Integer wtot_tapr_id;

	private Integer wtss_id;
	
	private String wtot_backremark;
	
	private String wtss_title;
	
	private Date wtot_comfdate;

	private String coab_name;
	
	private Integer sumnum;
	private String wtot_sbownmonth;
	private String wtot_gjjownmonth;
	private int wtot_ifview;
	
 

	public int getWtot_ifview() {
		return wtot_ifview;
	}


	public void setWtot_ifview(int wtot_ifview) {
		this.wtot_ifview = wtot_ifview;
	}


	public Integer getSumnum() {
		if (sumnum==null)
		{
			return 0;
		}
		else
		{
			return sumnum;
		}
		
	}

	
	public String getWtot_sbownmonth() {
		return wtot_sbownmonth;
	}


	public void setWtot_sbownmonth(String wtot_sbownmonth) {
		this.wtot_sbownmonth = wtot_sbownmonth;
	}


	public String getWtot_gjjownmonth() {
		return wtot_gjjownmonth;
	}


	public void setWtot_gjjownmonth(String wtot_gjjownmonth) {
		this.wtot_gjjownmonth = wtot_gjjownmonth;
	}


	public void setSumnum(Integer sumnum) {
		this.sumnum = sumnum;
	}

	public String getCoab_name() {
		return coab_name;
	}

	public void setCoab_name(String coab_name) {
		this.coab_name = coab_name;
	}

	public Date getWtot_comfdate() {
		return wtot_comfdate;
	}

	public void setWtot_comfdate(Date wtot_comfdate) {
		this.wtot_comfdate = wtot_comfdate;
	}

	public String getWtss_title() {
		return wtss_title;
	}

	public void setWtss_title(String wtss_title) {
		this.wtss_title = wtss_title;
	}

	public int getWtot_feechangeid() {
		return wtot_feechangeid;
	}

	public void setWtot_feechangeid(int wtot_feechangeid) {
		this.wtot_feechangeid = wtot_feechangeid;
	}

	public String getWtot_backremark() {
		return wtot_backremark;
	}

	public void setWtot_backremark(String wtot_backremark) {
		this.wtot_backremark = wtot_backremark;
	}

	public Integer getWtss_id() {
		return wtss_id;
	}

	public void setWtss_id(Integer wtss_id) {
		this.wtss_id = wtss_id;
	}

	public Integer getWtot_tapr_id() {
		return wtot_tapr_id;
	}

	public void setWtot_tapr_id(Integer wtot_tapr_id) {
		this.wtot_tapr_id = wtot_tapr_id;
	}

	public Integer getCabc_ppc_id() {
		return cabc_ppc_id;
	}

	public void setCabc_ppc_id(Integer cabc_ppc_id) {
		this.cabc_ppc_id = cabc_ppc_id;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getProvince() {
		return province;
	}

	public void setProvince(String province) {
		this.province = province;
	}

	public String getCoab_namespell() {
		return coab_namespell;
	}

	public void setCoab_namespell(String coab_namespell) {
		this.coab_namespell = coab_namespell;
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

	public int getCid() {
		return cid;
	}

	public void setCid(int cid) {
		this.cid = cid;
	}

	public String getWtot_statestr() {
		return wtot_statestr;
	}

	public void setWtot_statestr(String wtot_statestr) {
		this.wtot_statestr = wtot_statestr;
	}

	public int getWtot_feeid() {
		return wtot_feeid;
	}

	public void setWtot_feeid(int wtot_feeid) {
		this.wtot_feeid = wtot_feeid;
	}

	public String getWtot_feetitle() {
		return wtot_feetitle;
	}

	public void setWtot_feetitle(String wtot_feetitle) {
		this.wtot_feetitle = wtot_feetitle;
	}

	public BigDecimal getWtot_fee() {
		return wtot_fee;
	}

	public void setWtot_fee(BigDecimal wtot_fee) {
		this.wtot_fee = new BigDecimal(df.format(wtot_fee));
	}

	public String getWtot_addname() {
		return wtot_addname;
	}

	public void setWtot_addname(String wtot_addname) {
		this.wtot_addname = wtot_addname;
	}

	public String getWtot_addtime() {
		return wtot_addtime;
	}

	public void setWtot_addtime(String wtot_addtime) {
		this.wtot_addtime = wtot_addtime;
	}

	public String getWtot_examinenamekf() {
		return wtot_examinenamekf;
	}

	public void setWtot_examinenamekf(String wtot_examinenamekf) {
		this.wtot_examinenamekf = wtot_examinenamekf;
	}

	public String getWtot_examinetimefk() {
		return wtot_examinetimefk;
	}

	public void setWtot_examinetimefk(String wtot_examinetimefk) {
		this.wtot_examinetimefk = wtot_examinetimefk;
	}

	public String getWtot_editname() {
		return wtot_editname;
	}

	public void setWtot_editname(String wtot_editname) {
		this.wtot_editname = wtot_editname;
	}

	public String getWtot_edittime() {
		return wtot_edittime;
	}

	public void setWtot_edittime(String wtot_edittime) {
		this.wtot_edittime = wtot_edittime;
	}

	public String getWtot_remark() {
		return wtot_remark;
	}

	public void setWtot_remark(String wtot_remark) {
		this.wtot_remark = wtot_remark;
	}

	public String getWtot_examinenameqg() {
		return wtot_examinenameqg;
	}

	public void setWtot_examinenameqg(String wtot_examinenameqg) {
		this.wtot_examinenameqg = wtot_examinenameqg;
	}

	public String getWtot_examinetimeqg() {
		return wtot_examinetimeqg;
	}

	public void setWtot_examinetimeqg(String wtot_examinetimeqg) {
		this.wtot_examinetimeqg = wtot_examinetimeqg;
	}

	public int getWtot_state() {
		return wtot_state;
	}

	public void setWtot_state(int wtot_state) {
		this.wtot_state = wtot_state;
	}

	public int getCoba_id() {
		return coba_id;
	}

	public void setCoba_id(int coba_id) {
		this.coba_id = coba_id;
	}

	public String getWtss_city() {
		return wtss_city;
	}

	public void setWtss_city(String wtss_city) {
		this.wtss_city = wtss_city;
	}

	public wtoutFeeModel() {
		super();
	}

}
