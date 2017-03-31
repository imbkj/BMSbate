package Model;

import java.math.BigDecimal;

public class EmBodyCheckItemModel {
	// / ebit_id
	private Integer ebit_id;

	private String idList;

	// / 项目所属科目(暂无)
	private String ebit_type;

	// / ebit_name
	private String ebit_name;
	private String ebit_name2;
	// / ebit_charge
	private BigDecimal ebit_charge;
	// / ebit_info
	private String ebit_info;
	// / ebit_remark
	private String ebit_remark;
	// / ebit_hospital
	private Integer ebit_hospital;
	// / ebit_bmain
	private Integer ebit_bmain;
	// / ebit_blood
	private Integer ebit_blood;
	//
	private Integer ebit_sex;
	// / 0:常规;1:已婚项目
	private Integer ebit_marry;
	// / ebit_state
	private Integer ebit_state;
	// / ebit_package
	private Integer ebit_package;
	// / ebit_frequency
	private Integer ebit_frequency;
	// / ebit_discount
	private BigDecimal ebit_discount;
	// / ebit_addname
	private String ebit_addname;
	// / ebit_addtime
	private String ebit_addtime;
	// / ebit_modname
	private String ebit_modname;
	// / ebit_modtime
	private String ebit_modtime;
	private String ebcs_hospital;// 机构名称
	private Integer ebcs_state;// 机构状态
	private Integer ebcs_id;// 机构ID
	private String sex;// 中文性别
	private String blood;// 验血项目
	private String bmain;// 抽血项目
	private String marry;// 已婚项目
	private String packages;// 套餐项目
	private String frequency;// 常用项目
	private String state;// 项目状态
	private String idstr;// id拼接字符串
	private String xzcontent;// 限制内容

	private boolean flag = false;
	private boolean checked;
	private boolean top;
	private Integer topNum;
	private Boolean checkItem;

	private Integer eigl_ebig_id;
	private String ebig_name;

	public Integer getEbit_id() {
		return ebit_id;
	}

	public void setEbit_id(Integer ebit_id) {
		this.ebit_id = ebit_id;
	}

	public String getEbit_type() {
		return ebit_type;
	}

	public void setEbit_type(String ebit_type) {
		this.ebit_type = ebit_type;
	}

	public String getEbit_name() {
		return ebit_name;
	}

	public void setEbit_name(String ebit_name) {
		this.ebit_name = ebit_name;
	}

	public BigDecimal getEbit_charge() {
		return ebit_charge;
	}

	public void setEbit_charge(BigDecimal ebit_charge) {
		this.ebit_charge = ebit_charge;
	}

	public String getEbit_info() {
		return ebit_info;
	}

	public void setEbit_info(String ebit_info) {
		this.ebit_info = ebit_info;
	}

	public String getEbit_remark() {
		return ebit_remark;
	}

	public void setEbit_remark(String ebit_remark) {
		this.ebit_remark = ebit_remark;
	}

	public Integer getEbit_hospital() {
		return ebit_hospital;
	}

	public void setEbit_hospital(Integer ebit_hospital) {
		this.ebit_hospital = ebit_hospital;
	}

	public Integer getEbit_bmain() {
		return ebit_bmain;
	}

	public void setEbit_bmain(Integer ebit_bmain) {
		this.ebit_bmain = ebit_bmain;
	}

	public Integer getEbit_blood() {
		return ebit_blood;
	}

	public void setEbit_blood(Integer ebit_blood) {
		this.ebit_blood = ebit_blood;
	}

	public Integer getEbit_sex() {
		return ebit_sex;
	}

	public void setEbit_sex(Integer ebit_sex) {
		this.ebit_sex = ebit_sex;
	}

	public Integer getEbit_marry() {
		return ebit_marry;
	}

	public void setEbit_marry(Integer ebit_marry) {
		this.ebit_marry = ebit_marry;
	}

	public Integer getEbit_state() {
		return ebit_state;
	}

	public void setEbit_state(Integer ebit_state) {
		this.ebit_state = ebit_state;
	}

	public Integer getEbit_package() {
		return ebit_package;
	}

	public void setEbit_package(Integer ebit_package) {
		this.ebit_package = ebit_package;
	}

	public Integer getEbit_frequency() {
		return ebit_frequency;
	}

	public void setEbit_frequency(Integer ebit_frequency) {
		this.ebit_frequency = ebit_frequency;
	}

	public BigDecimal getEbit_discount() {
		return ebit_discount;
	}

	public void setEbit_discount(BigDecimal ebit_discount) {
		this.ebit_discount = ebit_discount;
	}

	public String getEbit_addname() {
		return ebit_addname;
	}

	public void setEbit_addname(String ebit_addname) {
		this.ebit_addname = ebit_addname;
	}

	public String getEbit_addtime() {
		return ebit_addtime;
	}

	public void setEbit_addtime(String ebit_addtime) {
		this.ebit_addtime = ebit_addtime;
	}

	public String getEbit_modname() {
		return ebit_modname;
	}

	public void setEbit_modname(String ebit_modname) {
		this.ebit_modname = ebit_modname;
	}

	public String getEbit_modtime() {
		return ebit_modtime;
	}

	public void setEbit_modtime(String ebit_modtime) {
		this.ebit_modtime = ebit_modtime;
	}

	public String getEbcs_hospital() {
		return ebcs_hospital;
	}

	public void setEbcs_hospital(String ebcs_hospital) {
		this.ebcs_hospital = ebcs_hospital;
	}

	public String getSex() {
		return sex;
	}

	public void setSex(String sex) {
		this.sex = sex;
	}

	public boolean isFlag() {
		return flag;
	}

	public void setFlag(boolean flag) {
		this.flag = flag;
	}

	public String getIdList() {
		return idList;
	}

	public void setIdList(String idList) {
		this.idList = idList;
	}

	public boolean isChecked() {
		return checked;
	}

	public void setChecked(boolean checked) {
		this.checked = checked;
	}

	public Integer getEigl_ebig_id() {
		return eigl_ebig_id;
	}

	public void setEigl_ebig_id(Integer eigl_ebig_id) {
		this.eigl_ebig_id = eigl_ebig_id;
	}

	public boolean isTop() {
		return top;
	}

	public void setTop(boolean top) {
		this.top = top;
	}

	public Integer getTopNum() {
		return topNum;
	}

	public void setTopNum(Integer topNum) {
		this.topNum = topNum;
	}

	public String getBlood() {
		return blood;
	}

	public void setBlood(String blood) {
		this.blood = blood;
	}

	public String getMarry() {
		return marry;
	}

	public void setMarry(String marry) {
		this.marry = marry;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public String getBmain() {
		return bmain;
	}

	public void setBmain(String bmain) {
		this.bmain = bmain;
	}

	public String getPackages() {
		return packages;
	}

	public void setPackages(String packages) {
		this.packages = packages;
	}

	public String getFrequency() {
		return frequency;
	}

	public void setFrequency(String frequency) {
		this.frequency = frequency;
	}

	public Integer getEbcs_state() {
		return ebcs_state;
	}

	public void setEbcs_state(Integer ebcs_state) {
		this.ebcs_state = ebcs_state;
	}

	public Boolean getCheckItem() {
		return checkItem;
	}

	public void setCheckItem(Boolean checkItem) {
		this.checkItem = checkItem;
	}

	public Integer getEbcs_id() {
		return ebcs_id;
	}

	public void setEbcs_id(Integer ebcs_id) {
		this.ebcs_id = ebcs_id;
	}

	public String getIdstr() {
		return idstr;
	}

	public void setIdstr(String idstr) {
		this.idstr = idstr;
	}

	public String getXzcontent() {
		return xzcontent;
	}

	public void setXzcontent(String xzcontent) {
		this.xzcontent = xzcontent;
	}

	public String getEbit_name2() {
		return ebit_name2;
	}

	public void setEbit_name2(String ebit_name2) {
		this.ebit_name2 = ebit_name2;
	}

	public String getEbig_name() {
		return ebig_name;
	}

	public void setEbig_name(String ebig_name) {
		this.ebig_name = ebig_name;
	}

}
