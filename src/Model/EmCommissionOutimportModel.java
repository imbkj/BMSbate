package Model;

import java.math.BigDecimal;
import java.util.Date;

public class EmCommissionOutimportModel {
	// ecou_id
		private Integer ecou_id;
		// gid
		private Integer gid;
		// cid
		private Integer cid;
		// SocialInsurance表外键id
		private Integer ecou_soin_id;//当地标准代码
		private Integer ecou_ecos_id;//服务费代码
		// ecou_idcard
		private String ecou_idcard;//身份证
		//员工姓名
		private String ecou_name;
		// ecou_email
		private String ecou_email;//邮箱
		// ecou_mobile
		private String ecou_mobile;//手机号码
		//是否保管档案
		private  Integer ecou_filestate; 
		//合同起始日期
		private Date ecou_title_date;
		private String ecou_compact_f;//合同起始时间
		private String ecou_compact_l;//合同结束时间
		// ecou_com_phone 工作电话
		private String ecou_com_phone;
		// 社保基数
		private BigDecimal ecou_sb_base;
		// 公积金基数
		private BigDecimal ecou_house_base;
		//实际工资
		private BigDecimal ecou_salary;
		// 0.离职 1.在职
		private Integer ecou_state;
		// 客服
		private String ecou_client;
		// ecou_addname 添加人
		private String ecou_addname;
		// ecou_addtime 添加日期
		private Date ecou_addtime;
		// ecou_remark 备注
		private String ecou_remark;
		private String ecou_yldate ; //养老起始
		private String ecou_yliaodate ; //医疗起始
		private String ecou_dbdate ;//大病起始
		private String ecou_syudate ; //生育起始
		private String ecou_gsdate ; //工伤起始
		private String ecou_sydate ;//失业保险起始
		private String ecou_zfdate ; //住房起始
		private String ecou_zfcp ; //住房单位比例
		private String ecou_zfop  ;//住房个人比例
		private String  ecou_bczfdate;//补充公积金起始
		private String ecou_bczfcp;//补充住房单位比例
		private String ecou_bczfop ;//补充住房个人比例
		private String  ecou_cbjdate;//残保金起始时间
		private String ecou_fwfdate;//服务费起始时间
		private String ecou_filedate;//服务费起始时间
		private String ecou_importflie;//导入文件路径
		private Boolean checked;
		
		java.text.DecimalFormat df = new java.text.DecimalFormat("#.00");
		
		public String getEcou_name() {
			return ecou_name;
		}
		public void setEcou_name(String ecou_name) {
			this.ecou_name = ecou_name;
		}
		public Integer getEcou_filestate() {
			return ecou_filestate;
		}
		
		public Boolean getChecked() {
			return checked;
		}
		public void setChecked(Boolean checked) {
			this.checked = checked;
		}
		public void setEcou_filestate(Integer ecou_filestate) {
			this.ecou_filestate = ecou_filestate;
		}
		public Integer getEcou_id() {
			return ecou_id;
		}
		public void setEcou_id(Integer ecou_id) {
			this.ecou_id = ecou_id;
		}
		public Integer getGid() {
			return gid;
		}
		public void setGid(Integer gid) {
			this.gid = gid;
		}
		public Integer getCid() {
			return cid;
		}
		public void setCid(Integer cid) {
			this.cid = cid;
		}
		public Integer getEcou_soin_id() {
			return ecou_soin_id;
		}
		public void setEcou_soin_id(Integer ecou_soin_id) {
			this.ecou_soin_id = ecou_soin_id;
		}
		public Integer getEcou_ecos_id() {
			return ecou_ecos_id;
		}
		public void setEcou_ecos_id(Integer ecou_ecos_id) {
			this.ecou_ecos_id = ecou_ecos_id;
		}
		public String getEcou_idcard() {
			return ecou_idcard;
		}
		public void setEcou_idcard(String ecou_idcard) {
			this.ecou_idcard = ecou_idcard;
		}
		public String getEcou_email() {
			return ecou_email;
		}
		public void setEcou_email(String ecou_email) {
			this.ecou_email = ecou_email;
		}
		public String getEcou_mobile() {
			return ecou_mobile;
		}
		public void setEcou_mobile(String ecou_mobile) {
			this.ecou_mobile = ecou_mobile;
		}
		public Date getEcou_title_date() {
			return ecou_title_date;
		}
		public void setEcou_title_date(Date ecou_title_date) {
			this.ecou_title_date = ecou_title_date;
		}
		public String getEcou_com_phone() {
			return ecou_com_phone;
		}
		public void setEcou_com_phone(String ecou_com_phone) {
			this.ecou_com_phone = ecou_com_phone;
		}
		public BigDecimal getEcou_sb_base() {
			return ecou_sb_base;
		}
		public void setEcou_sb_base(BigDecimal ecou_sb_base) {
			this.ecou_sb_base = new BigDecimal(df.format(ecou_sb_base));
		}
		public BigDecimal getEcou_house_base() {
			return ecou_house_base;
		}
		public void setEcou_house_base(BigDecimal ecou_house_base) {
			this.ecou_house_base = new BigDecimal(df.format(ecou_house_base));
		}
		public BigDecimal getEcou_salary() {
			return ecou_salary;
		}
		public void setEcou_salary(BigDecimal ecou_salary) {
			this.ecou_salary = new BigDecimal(df.format(ecou_salary));
		}
		public Integer getEcou_state() {
			return ecou_state;
		}
		public void setEcou_state(Integer ecou_state) {
			this.ecou_state = ecou_state;
		}
		public String getEcou_client() {
			return ecou_client;
		}
		public void setEcou_client(String ecou_client) {
			this.ecou_client = ecou_client;
		}
		public String getEcou_addname() {
			return ecou_addname;
		}
		public void setEcou_addname(String ecou_addname) {
			this.ecou_addname = ecou_addname;
		}
		public Date getEcou_addtime() {
			return ecou_addtime;
		}
		public void setEcou_addtime(Date ecou_addtime) {
			this.ecou_addtime = ecou_addtime;
		}
		public String getEcou_remark() {
			return ecou_remark;
		}
		public void setEcou_remark(String ecou_remark) {
			this.ecou_remark = ecou_remark;
		}
		
		public String getEcou_yldate() {
			return ecou_yldate;
		}
		public void setEcou_yldate(String ecou_yldate) {
			this.ecou_yldate = ecou_yldate;
		}
		
		public String getEcou_zfcp() {
			return ecou_zfcp;
		}
		public void setEcou_zfcp(String ecou_zfcp) {
			this.ecou_zfcp = ecou_zfcp;
		}
		public String getEcou_zfop() {
			return ecou_zfop;
		}
		public void setEcou_zfop(String ecou_zfop) {
			this.ecou_zfop = ecou_zfop;
		}
		
		public String getEcou_bczfcp() {
			return ecou_bczfcp;
		}
		public void setEcou_bczfcp(String ecou_bczfcp) {
			this.ecou_bczfcp = ecou_bczfcp;
		}
		public String getEcou_bczfop() {
			return ecou_bczfop;
		}
		public void setEcou_bczfop(String ecou_bczfop) {
			this.ecou_bczfop = ecou_bczfop;
		}
		
		public String getEcou_importflie() {
			return ecou_importflie;
		}
		public void setEcou_importflie(String ecou_importflie) {
			this.ecou_importflie = ecou_importflie;
		}
		public String getEcou_compact_f() {
			return ecou_compact_f;
		}
		public void setEcou_compact_f(String ecou_compact_f) {
			this.ecou_compact_f = ecou_compact_f;
		}
		public String getEcou_compact_l() {
			return ecou_compact_l;
		}
		public void setEcou_compact_l(String ecou_compact_l) {
			this.ecou_compact_l = ecou_compact_l;
		}
		public String getEcou_yliaodate() {
			return ecou_yliaodate;
		}
		public void setEcou_yliaodate(String ecou_yliaodate) {
			this.ecou_yliaodate = ecou_yliaodate;
		}
		public String getEcou_dbdate() {
			return ecou_dbdate;
		}
		public void setEcou_dbdate(String ecou_dbdate) {
			this.ecou_dbdate = ecou_dbdate;
		}
		public String getEcou_syudate() {
			return ecou_syudate;
		}
		public void setEcou_syudate(String ecou_syudate) {
			this.ecou_syudate = ecou_syudate;
		}
		
		public String getEcou_filedate() {
			return ecou_filedate;
		}
		public void setEcou_filedate(String ecou_filedate) {
			this.ecou_filedate = ecou_filedate;
		}
		public String getEcou_gsdate() {
			return ecou_gsdate;
		}
		public void setEcou_gsdate(String ecou_gsdate) {
			this.ecou_gsdate = ecou_gsdate;
		}
		public String getEcou_sydate() {
			return ecou_sydate;
		}
		public void setEcou_sydate(String ecou_sydate) {
			this.ecou_sydate = ecou_sydate;
		}
		public String getEcou_zfdate() {
			return ecou_zfdate;
		}
		public void setEcou_zfdate(String ecou_zfdate) {
			this.ecou_zfdate = ecou_zfdate;
		}
		public String getEcou_bczfdate() {
			return ecou_bczfdate;
		}
		public void setEcou_bczfdate(String ecou_bczfdate) {
			this.ecou_bczfdate = ecou_bczfdate;
		}
		public String getEcou_cbjdate() {
			return ecou_cbjdate;
		}
		public void setEcou_cbjdate(String ecou_cbjdate) {
			this.ecou_cbjdate = ecou_cbjdate;
		}
		public String getEcou_fwfdate() {
			return ecou_fwfdate;
		}
		public void setEcou_fwfdate(String ecou_fwfdate) {
			this.ecou_fwfdate = ecou_fwfdate;
		}
		
		
		
}
