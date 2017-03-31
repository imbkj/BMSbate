package Model;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

public class CoOfferListModel implements Cloneable {

	private Integer coli_id;
	private Integer coli_coof_id;
	private String coli_city;
	private String cityspy;
	private String citypy;
	private Integer coli_copr_id;
	private String coli_name;
	private String coli_content;
	private String coli_pclass;
	private String coli_account;
	private Integer coli_gather;
	private String coli_remark;
	private String coli_addname;
	private Date coli_addtime;
	private Date coli_starttime;
	private Date coli_stoptime;
	private String coli_modname;
	private Date coli_modtime;
	private Integer coli_amount;
	private Integer coli_state;
	private Double coli_discount;
	private BigDecimal coli_fee;
	private BigDecimal coli_fee2;
	private String coli_cpfc_name;
	private String coli_standard;
	private String cpfc_name;
	private Integer coof_id;
	private String coof_name;
	private String city;
	private String copr_type;
	private Integer coli_isfwf;
	private List<CoOfferListModel> infoList;
	private String coab_name;
	private Integer coli_group_id;
	private Integer coli_group_count;
	private Integer copr_emce_id;
	private BigDecimal sum;
	private String sumInfo;
	private String coli_feetype;
	private Integer coli_coco_id;
	private Integer coli_parid;
	private boolean checked;
	private boolean addchecked;
	private Integer stopdate;
	private Integer copc_id;

	private Integer coli_sendmonth;
	private Integer coli_around;
	private String coli_feeDiscount;

	private Integer coof_state;

	private Date cgli_startdate;
	private Date cgli_startdate2;
	private Date cgli_stopdate;
	private Integer st;
	private Integer st2;
	private Integer sd;

	private String coco_compacttype;
	private String coco_shebao;
	private String coco_house;
	private String coco_compactid;
	private Integer coco_state;
	private String coco_cpp;
	private String coco_gs;

	private Integer a;
	private Integer row;
	private Integer cid;
	private Integer coId;
	private Boolean product;
	private boolean cococheck;
	private boolean coofcheck;

	private String coli_rspaykind;
	private String coli_hjpaykind;
	private String coli_flpaykind;
	private String coli_rsinvoice;
	private String coli_hjinvoice;

	private Boolean detailOpen = false;
	private Boolean allot = false;

	private List<CoOfferModel> comList;
	private List<CoCompactModel> ccmList;

	private List<CoOLModeModel> coollist;// 享受条件的model
	private CoOLModeModel coolmodel = new CoOLModeModel();
	private CoOLModeModel coolmodel1 = new CoOLModeModel();

	private String cpac_name;

	public Object clone() {
		try {
			return super.clone();
		} catch (CloneNotSupportedException e) {
			// TODO Auto-generated catch block
			return null;
		}

	}

	public String getCpac_name() {
		return cpac_name;
	}

	public void setCpac_name(String cpac_name) {
		this.cpac_name = cpac_name;
	}

	public Integer getCoId() {
		return coId;
	}

	public void setCoId(Integer coId) {
		this.coId = coId;
	}

	public Integer getCid() {
		return cid;
	}

	public void setCid(Integer cid) {
		this.cid = cid;
	}

	public Integer getCopr_emce_id() {
		return copr_emce_id;
	}

	public void setCopr_emce_id(Integer copr_emce_id) {
		this.copr_emce_id = copr_emce_id;
	}

	public String getColi_city() {
		return coli_city;
	}

	public void setColi_city(String coli_city) {
		this.coli_city = coli_city;
	}

	public String getColi_name() {
		return coli_name;
	}

	public void setColi_name(String coli_name) {
		this.coli_name = coli_name;
	}

	public String getColi_content() {
		return coli_content;
	}

	public void setColi_content(String coli_content) {
		this.coli_content = coli_content;
	}

	public String getCityspy() {
		return cityspy;
	}

	public void setCityspy(String cityspy) {
		this.cityspy = cityspy;
	}

	public String getCitypy() {
		return citypy;
	}

	public void setCitypy(String citypy) {
		this.citypy = citypy;
	}

	public String getColi_standard() {
		return coli_standard;
	}

	public void setColi_standard(String coli_standard) {
		this.coli_standard = coli_standard;
	}

	public Integer getColi_id() {
		return coli_id;
	}

	public void setColi_id(Integer coli_id) {
		this.coli_id = coli_id;
	}

	public Integer getColi_coof_id() {
		return coli_coof_id;
	}

	public void setColi_coof_id(Integer coli_coof_id) {
		this.coli_coof_id = coli_coof_id;
	}

	public String getColi_pclass() {
		return coli_pclass;
	}

	public void setColi_pclass(String coli_pclass) {
		this.coli_pclass = coli_pclass;
	}

	public String getColi_account() {
		return coli_account;
	}

	public void setColi_account(String coli_account) {
		this.coli_account = coli_account;
	}

	public String getColi_remark() {
		return coli_remark;
	}

	public void setColi_remark(String coli_remark) {
		this.coli_remark = coli_remark;
	}

	public String getColi_addname() {
		return coli_addname;
	}

	public void setColi_addname(String coli_addname) {
		this.coli_addname = coli_addname;
	}

	public Date getColi_addtime() {
		return coli_addtime;
	}

	public void setColi_addtime(Date coli_addtime) {
		this.coli_addtime = coli_addtime;
	}

	public Date getColi_starttime() {
		return coli_starttime;
	}

	public void setColi_starttime(Date coli_starttime) {
		this.coli_starttime = coli_starttime;
	}

	public Date getColi_stoptime() {
		return coli_stoptime;
	}

	public void setColi_stoptime(Date coli_stoptime) {
		this.coli_stoptime = coli_stoptime;
	}

	public String getColi_modname() {
		return coli_modname;
	}

	public void setColi_modname(String coli_modname) {
		this.coli_modname = coli_modname;
	}

	public Date getColi_modtime() {
		return coli_modtime;
	}

	public void setColi_modtime(Date coli_modtime) {
		this.coli_modtime = coli_modtime;
	}

	public Integer getColi_amount() {
		return coli_amount;
	}

	public void setColi_amount(Integer coli_amount) {
		this.coli_amount = coli_amount;
	}

	public Integer getColi_state() {
		return coli_state;
	}

	public void setColi_state(Integer coli_state) {
		this.coli_state = coli_state;
	}

	public Double getColi_discount() {
		return coli_discount;
	}

	public void setColi_discount(Double coli_discount) {
		this.coli_discount = coli_discount;
	}

	public BigDecimal getColi_fee() {
		return coli_fee;
	}

	public void setColi_fee(BigDecimal coli_fee) {
		this.coli_fee = coli_fee == null ? BigDecimal.ZERO : coli_fee.setScale(
				2, BigDecimal.ROUND_HALF_UP);
	}

	public BigDecimal getColi_fee2() {
		return coli_fee2;
	}

	public void setColi_fee2(BigDecimal coli_fee2) {
		this.coli_fee2 = coli_fee2 == null ? BigDecimal.ZERO : coli_fee2
				.setScale(2, BigDecimal.ROUND_HALF_UP);
	}

	public Integer getA() {
		return a;
	}

	public void setA(Integer a) {
		this.a = a;
	}

	public Integer getColi_gather() {
		return coli_gather;
	}

	public void setColi_gather(Integer coli_gather) {
		this.coli_gather = coli_gather;
	}

	public String getCpfc_name() {
		return cpfc_name;
	}

	public void setCpfc_name(String cpfc_name) {
		this.cpfc_name = cpfc_name;
	}

	public Integer getRow() {
		return row;
	}

	public void setRow(Integer row) {
		this.row = row;
	}

	public Integer getCoof_id() {
		return coof_id;
	}

	public void setCoof_id(Integer coof_id) {
		this.coof_id = coof_id;
	}

	public String getCoof_name() {
		return coof_name;
	}

	public void setCoof_name(String coof_name) {
		this.coof_name = coof_name;
	}

	public String getColi_cpfc_name() {
		return coli_cpfc_name;
	}

	public void setColi_cpfc_name(String coli_cpfc_name) {
		this.coli_cpfc_name = coli_cpfc_name;
	}

	public Integer getColi_copr_id() {
		return coli_copr_id;
	}

	public void setColi_copr_id(Integer coli_copr_id) {
		this.coli_copr_id = coli_copr_id;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getCopr_type() {
		return copr_type;
	}

	public void setCopr_type(String copr_type) {
		this.copr_type = copr_type;
	}

	public List<CoOfferListModel> getInfoList() {
		return infoList;
	}

	public void setInfoList(List<CoOfferListModel> infoList) {
		this.infoList = infoList;
	}

	public Integer getColi_isfwf() {
		return coli_isfwf;
	}

	public void setColi_isfwf(Integer coli_isfwf) {
		this.coli_isfwf = coli_isfwf;
	}

	public String getCoab_name() {
		return coab_name;
	}

	public void setCoab_name(String coab_name) {
		this.coab_name = coab_name;
	}

	public Integer getColi_group_id() {
		return coli_group_id;
	}

	public void setColi_group_id(Integer coli_group_id) {
		this.coli_group_id = coli_group_id;
	}

	public Integer getColi_group_count() {
		return coli_group_count;
	}

	public void setColi_group_count(Integer coli_group_count) {
		this.coli_group_count = coli_group_count;
	}

	public List<CoOLModeModel> getCoollist() {
		return coollist;
	}

	public void setCoollist(List<CoOLModeModel> coollist) {
		this.coollist = coollist;
		if (this.coollist.size() == 1) {
			if (getCoolmodel().getColm_selectid() == null) {
				setCoolmodel(this.coollist.get(0));
			}
		} else if (this.coollist.size() == 2) {
			if (getCoolmodel().getColm_selectid() == null) {
				setCoolmodel(this.coollist.get(0));
				setCoolmodel1(this.coollist.get(1));
			}
		}
	}

	public CoOLModeModel getCoolmodel() {
		return coolmodel;
	}

	public void setCoolmodel(CoOLModeModel coolmodel) {
		this.coolmodel = coolmodel;
	}

	public CoOLModeModel getCoolmodel1() {
		return coolmodel1;
	}

	public void setCoolmodel1(CoOLModeModel coolmodel1) {
		this.coolmodel1 = coolmodel1;
	}

	public BigDecimal getSum() {
		return sum;
	}

	public void setSum(BigDecimal sum) {
		this.sum = sum == null ? BigDecimal.ZERO : sum.setScale(2,
				BigDecimal.ROUND_HALF_UP);
	}

	public Boolean getDetailOpen() {
		return detailOpen;
	}

	public void setDetailOpen(Boolean detailOpen) {
		this.detailOpen = detailOpen;
	}

	public String getColi_feetype() {
		return coli_feetype;
	}

	public void setColi_feetype(String coli_feetype) {
		this.coli_feetype = coli_feetype;
	}

	public Integer getColi_coco_id() {
		return coli_coco_id;
	}

	public void setColi_coco_id(Integer coli_coco_id) {
		this.coli_coco_id = coli_coco_id;
	}

	public Integer getColi_parid() {
		return coli_parid;
	}

	public void setColi_parid(Integer coli_parid) {
		this.coli_parid = coli_parid;
	}

	public boolean isChecked() {
		return checked;
	}

	public void setChecked(boolean checked) {
		this.checked = checked;
	}

	public Date getCgli_startdate() {
		return cgli_startdate;
	}

	public void setCgli_startdate(Date cgli_startdate) {
		this.cgli_startdate = cgli_startdate;
	}

	public Date getCgli_startdate2() {
		return cgli_startdate2;
	}

	public void setCgli_startdate2(Date cgli_startdate2) {
		this.cgli_startdate2 = cgli_startdate2;
	}

	public Date getCgli_stopdate() {
		return cgli_stopdate;
	}

	public void setCgli_stopdate(Date cgli_stopdate) {
		this.cgli_stopdate = cgli_stopdate;
	}

	public String getCoco_shebao() {
		return coco_shebao;
	}

	public void setCoco_shebao(String coco_shebao) {
		this.coco_shebao = coco_shebao;
	}

	public String getCoco_house() {
		return coco_house;
	}

	public void setCoco_house(String coco_house) {
		this.coco_house = coco_house;
	}

	public String getCoco_compactid() {
		return coco_compactid;
	}

	public void setCoco_compactid(String coco_compactid) {
		this.coco_compactid = coco_compactid;
	}

	public Integer getSt() {
		return st;
	}

	public void setSt(Integer st) {
		this.st = st;
	}

	public Integer getSt2() {
		return st2;
	}

	public void setSt2(Integer st2) {
		this.st2 = st2;
	}

	public Integer getSd() {
		return sd;
	}

	public void setSd(Integer sd) {
		this.sd = sd;
	}

	public String getSumInfo() {
		return sumInfo;
	}

	public void setSumInfo(String sumInfo) {
		this.sumInfo = sumInfo;
	}

	public Integer getStopdate() {
		return stopdate;
	}

	public void setStopdate(Integer stopdate) {
		this.stopdate = stopdate;
	}

	public List<CoOfferModel> getComList() {
		return comList;
	}

	public void setComList(List<CoOfferModel> comList) {
		this.comList = comList;
	}

	public List<CoCompactModel> getCcmList() {
		return ccmList;
	}

	public void setCcmList(List<CoCompactModel> ccmList) {
		this.ccmList = ccmList;
	}

	public Boolean getProduct() {
		return product;
	}

	public void setProduct(Boolean product) {
		this.product = product;
	}

	public boolean isCococheck() {
		return cococheck;
	}

	public void setCococheck(boolean cococheck) {
		this.cococheck = cococheck;
	}

	public boolean isCoofcheck() {
		return coofcheck;
	}

	public void setCoofcheck(boolean coofcheck) {
		this.coofcheck = coofcheck;
	}

	public boolean isAddchecked() {
		return addchecked;
	}

	public void setAddchecked(boolean addchecked) {
		this.addchecked = addchecked;
	}

	public String getColi_rspaykind() {
		return coli_rspaykind;
	}

	public void setColi_rspaykind(String coli_rspaykind) {
		this.coli_rspaykind = coli_rspaykind;
	}

	public String getColi_hjpaykind() {
		return coli_hjpaykind;
	}

	public void setColi_hjpaykind(String coli_hjpaykind) {
		this.coli_hjpaykind = coli_hjpaykind;
	}

	public String getColi_flpaykind() {
		return coli_flpaykind;
	}

	public void setColi_flpaykind(String coli_flpaykind) {
		this.coli_flpaykind = coli_flpaykind;
	}

	public String getColi_rsinvoice() {
		return coli_rsinvoice;
	}

	public void setColi_rsinvoice(String coli_rsinvoice) {
		this.coli_rsinvoice = coli_rsinvoice;
	}

	public String getColi_hjinvoice() {
		return coli_hjinvoice;
	}

	public void setColi_hjinvoice(String coli_hjinvoice) {
		this.coli_hjinvoice = coli_hjinvoice;
	}

	public Boolean getAllot() {
		return allot;
	}

	public void setAllot(Boolean allot) {
		this.allot = allot;
	}

	public String getCoco_compacttype() {
		return coco_compacttype;
	}

	public void setCoco_compacttype(String coco_compacttype) {
		this.coco_compacttype = coco_compacttype;
	}

	public Integer getCopc_id() {
		return copc_id;
	}

	public void setCopc_id(Integer copc_id) {
		this.copc_id = copc_id;
	}

	public Integer getCoof_state() {
		return coof_state;
	}

	public void setCoof_state(Integer coof_state) {
		this.coof_state = coof_state;
	}

	public Integer getCoco_state() {
		return coco_state;
	}

	public void setCoco_state(Integer coco_state) {
		this.coco_state = coco_state;
	}

	public String getCoco_cpp() {
		return coco_cpp;
	}

	public void setCoco_cpp(String coco_cpp) {
		this.coco_cpp = coco_cpp;
	}

	public Integer getColi_sendmonth() {
		return coli_sendmonth;
	}

	public void setColi_sendmonth(Integer coli_sendmonth) {
		this.coli_sendmonth = coli_sendmonth;
	}

	public Integer getColi_around() {
		return coli_around;
	}

	public void setColi_around(Integer coli_around) {
		this.coli_around = coli_around;
	}

	public String getColi_feeDiscount() {
		return coli_feeDiscount;
	}

	public void setColi_feeDiscount(String coli_feeDiscount) {
		this.coli_feeDiscount = coli_feeDiscount;
	}


	public String getCoco_gs() {
		return coco_gs;
	}

	public void setCoco_gs(String coco_gs) {
		this.coco_gs = coco_gs;
	}


}
