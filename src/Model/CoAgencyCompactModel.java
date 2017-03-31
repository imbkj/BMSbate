package Model;

public class CoAgencyCompactModel {
    /// coct_id
    private Integer  coct_id;
             /// ('合同编号')
    private String  coct_compactid;
             /// ('合同类型——受托合同、委托合同')
    private String  coct_type;
             /// 签合同日期
    private String  coct_signdate;
             /// 合同生效日
    private String  coct_effectdate;
             /// 合同到期日
    private String  coct_expiredate;
             /// 是否自动延期
    private Integer  coct_autoextend;
             /// 合同履行城市
    private String  coct_executcity;
             /// coct_sendinvoice
    private Integer  coct_sendinvoice;
             /// coct_payday
    private Integer  coct_payday;
             /// coct_paymon
    private String  coct_paymon;
             /// coct_accountbank
    private String  coct_accountbank;
             /// coct_accountnum
    private String  coct_accountnum;
             /// 合同终止日
    private String  coct_stopdate;
             /// 合同终止原因
    private String  coct_stopreason;
             /// 合同终止人
    private String  coct_stopname;
             /// coct_remark
    private String  coct_remark;
             /// coct_addname
    private String  coct_addname;
             /// coct_addtime
    private String  coct_addtime;
             //////1、已制作；2、已审核；3、已签回；4、已归档；5、已终止；6、已解约；
    private Integer  coct_state;
             /// coct_file
    private Integer  coct_file;
             /// coct_shareaid
    private Integer  coct_shareaid;
    
    private String coct_coagname;//机构名称
    
    private Integer coct_coagid;//机构Id
    private String coct_category;//合同类别
    
    private String coct_filename;//合同文件名
    
    private Integer coct_tarpid;
    
    private String coct_modname;
    private String coct_modtime;
    private String statename;
    private Integer num=0;
    
    /// 合同制作人
    private String  coct_makename;
             /// coct_maketime
    private String  coct_maketime;
             /// 合同审核人
    private String  coct_auditname;
             /// 合同签回人
    private String  coct_signbackname;
             /// coct_signbacktime
    private String  coct_signbacktime;
             /// 合同归档人
    private String  coct_archivename;
             /// 合同归档时间
    private String  coct_archivetime;
             /// coct_audittime
    private String  coct_audittime;
    
    private String coct_archivenumber;
    private String coct_ch_amount;
    private String coct_en_amount;
    private String autoex;//是否自动延期中文
    
    private String city;
    
    private String coct_backcase;
    private String coct_backtime;
    private String coct_backname;
            
	public Integer getCoct_id() {
		return coct_id;
	}
	public void setCoct_id(Integer coct_id) {
		this.coct_id = coct_id;
	}
	public String getCoct_compactid() {
		return coct_compactid;
	}
	public void setCoct_compactid(String coct_compactid) {
		this.coct_compactid = coct_compactid;
	}
	public String getCoct_type() {
		return coct_type;
	}
	public void setCoct_type(String coct_type) {
		this.coct_type = coct_type;
	}
	public String getCoct_signdate() {
		return coct_signdate;
	}
	public void setCoct_signdate(String coct_signdate) {
		this.coct_signdate = coct_signdate;
	}
	public String getCoct_effectdate() {
		return coct_effectdate;
	}
	public void setCoct_effectdate(String coct_effectdate) {
		this.coct_effectdate = coct_effectdate;
	}
	public String getCoct_expiredate() {
		return coct_expiredate;
	}
	public void setCoct_expiredate(String coct_expiredate) {
		this.coct_expiredate = coct_expiredate;
	}
	public Integer getCoct_autoextend() {
		return coct_autoextend;
	}
	public void setCoct_autoextend(Integer coct_autoextend) {
		this.coct_autoextend = coct_autoextend;
	}
	public String getCoct_executcity() {
		return coct_executcity;
	}
	public void setCoct_executcity(String coct_executcity) {
		this.coct_executcity = coct_executcity;
	}
	public Integer getCoct_sendinvoice() {
		return coct_sendinvoice;
	}
	public void setCoct_sendinvoice(Integer coct_sendinvoice) {
		this.coct_sendinvoice = coct_sendinvoice;
	}
	public Integer getCoct_payday() {
		return coct_payday;
	}
	public void setCoct_payday(Integer coct_payday) {
		this.coct_payday = coct_payday;
	}
	public String getCoct_paymon() {
		return coct_paymon;
	}
	public void setCoct_paymon(String coct_paymon) {
		this.coct_paymon = coct_paymon;
	}
	public String getCoct_accountbank() {
		return coct_accountbank;
	}
	public void setCoct_accountbank(String coct_accountbank) {
		this.coct_accountbank = coct_accountbank;
	}
	public String getCoct_accountnum() {
		return coct_accountnum;
	}
	public void setCoct_accountnum(String coct_accountnum) {
		this.coct_accountnum = coct_accountnum;
	}
	public String getCoct_stopdate() {
		return coct_stopdate;
	}
	public void setCoct_stopdate(String coct_stopdate) {
		this.coct_stopdate = coct_stopdate;
	}
	public String getCoct_stopreason() {
		return coct_stopreason;
	}
	public void setCoct_stopreason(String coct_stopreason) {
		this.coct_stopreason = coct_stopreason;
	}
	public String getCoct_stopname() {
		return coct_stopname;
	}
	public void setCoct_stopname(String coct_stopname) {
		this.coct_stopname = coct_stopname;
	}
	public String getCoct_remark() {
		return coct_remark;
	}
	public void setCoct_remark(String coct_remark) {
		this.coct_remark = coct_remark;
	}
	public String getCoct_addname() {
		return coct_addname;
	}
	public void setCoct_addname(String coct_addname) {
		this.coct_addname = coct_addname;
	}
	public String getCoct_addtime() {
		return coct_addtime;
	}
	public void setCoct_addtime(String coct_addtime) {
		this.coct_addtime = coct_addtime;
	}
	public Integer getCoct_state() {
		return coct_state;
	}
	public void setCoct_state(Integer coct_state) {
		this.coct_state = coct_state;
	}
	public Integer getCoct_file() {
		return coct_file;
	}
	public void setCoct_file(Integer coct_file) {
		this.coct_file = coct_file;
	}
	public Integer getCoct_shareaid() {
		return coct_shareaid;
	}
	public void setCoct_shareaid(Integer coct_shareaid) {
		this.coct_shareaid = coct_shareaid;
	}
	public String getCoct_coagname() {
		return coct_coagname;
	}
	public void setCoct_coagname(String coct_coagname) {
		this.coct_coagname = coct_coagname;
	}
	public Integer getCoct_coagid() {
		return coct_coagid;
	}
	public void setCoct_coagid(Integer coct_coagid) {
		this.coct_coagid = coct_coagid;
	}
	public String getCoct_category() {
		return coct_category;
	}
	public void setCoct_category(String coct_category) {
		this.coct_category = coct_category;
	}
	public Integer getCoct_tarpid() {
		return coct_tarpid;
	}
	public void setCoct_tarpid(Integer coct_tarpid) {
		this.coct_tarpid = coct_tarpid;
	}
	public String getCoct_filename() {
		return coct_filename;
	}
	public void setCoct_filename(String coct_filename) {
		this.coct_filename = coct_filename;
	}
	public String getCoct_modname() {
		return coct_modname;
	}
	public void setCoct_modname(String coct_modname) {
		this.coct_modname = coct_modname;
	}
	public String getCoct_modtime() {
		return coct_modtime;
	}
	public void setCoct_modtime(String coct_modtime) {
		this.coct_modtime = coct_modtime;
	}
	public String getCoct_makename() {
		return coct_makename;
	}
	public void setCoct_makename(String coct_makename) {
		this.coct_makename = coct_makename;
	}
	public String getCoct_maketime() {
		return coct_maketime;
	}
	public void setCoct_maketime(String coct_maketime) {
		this.coct_maketime = coct_maketime;
	}
	public String getCoct_auditname() {
		return coct_auditname;
	}
	public void setCoct_auditname(String coct_auditname) {
		this.coct_auditname = coct_auditname;
	}
	public String getCoct_signbackname() {
		return coct_signbackname;
	}
	public void setCoct_signbackname(String coct_signbackname) {
		this.coct_signbackname = coct_signbackname;
	}
	public String getCoct_signbacktime() {
		return coct_signbacktime;
	}
	public void setCoct_signbacktime(String coct_signbacktime) {
		this.coct_signbacktime = coct_signbacktime;
	}
	public String getCoct_archivename() {
		return coct_archivename;
	}
	public void setCoct_archivename(String coct_archivename) {
		this.coct_archivename = coct_archivename;
	}
	public String getCoct_archivetime() {
		return coct_archivetime;
	}
	public void setCoct_archivetime(String coct_archivetime) {
		this.coct_archivetime = coct_archivetime;
	}
	public String getCoct_audittime() {
		return coct_audittime;
	}
	public void setCoct_audittime(String coct_audittime) {
		this.coct_audittime = coct_audittime;
	}
	public String getCoct_archivenumber() {
		return coct_archivenumber;
	}
	public void setCoct_archivenumber(String coct_archivenumber) {
		this.coct_archivenumber = coct_archivenumber;
	}
	public String getCoct_ch_amount() {
		return coct_ch_amount;
	}
	public void setCoct_ch_amount(String coct_ch_amount) {
		this.coct_ch_amount = coct_ch_amount;
	}
	public String getCoct_en_amount() {
		return coct_en_amount;
	}
	public void setCoct_en_amount(String coct_en_amount) {
		this.coct_en_amount = coct_en_amount;
	}
	public String getStatename() {
		return statename;
	}
	public void setStatename(String statename) {
		this.statename = statename;
	}
	public Integer getNum() {
		return num;
	}
	public void setNum(Integer num) {
		this.num = num;
	}
	public String getAutoex() {
		return autoex;
	}
	public void setAutoex(String autoex) {
		this.autoex = autoex;
	}
	public String getCity() {
		return city;
	}
	public void setCity(String city) {
		this.city = city;
	}
	public String getCoct_backcase() {
		return coct_backcase;
	}
	public void setCoct_backcase(String coct_backcase) {
		this.coct_backcase = coct_backcase;
	}
	public String getCoct_backtime() {
		return coct_backtime;
	}
	public void setCoct_backtime(String coct_backtime) {
		this.coct_backtime = coct_backtime;
	}
	public String getCoct_backname() {
		return coct_backname;
	}
	public void setCoct_backname(String coct_backname) {
		this.coct_backname = coct_backname;
	}
    
    
}
