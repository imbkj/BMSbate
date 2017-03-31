package Model;

import java.math.BigDecimal;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class EmCensusModel {
	DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
	// / emhj_id
	private Integer emhj_id;
	// / 雇员编号
	private Integer gid;
	// / 公司编号
	private Integer cid;
	// / emhj_no
	private String emhj_no;
	// / 姓名
	private String emhj_name;
	// / 身份证
	private String emhj_idcard;
	// / 户口状态:0未确认1已确认2已借卡3已还卡4退回5办理成功6迁出7退回
	private String emhj_state;
	// / 户口类型
	private String emhj_type;
	// / 入户时间
	private String emhj_in_time;
	// / 入户方式
	private String emhj_in_class;
	// / 入户协议时间
	private Date emhj_agreement_time;
	// / 备注
	private String emhj_remark;
	// / 添加人
	private String emhj_addname;
	// / 添加时间
	private Date emhj_addtime;
	// / emhj_return_time
	private Date emhj_return_time;
	// / 借卡原因
	private String emhj_case;
	// / emhj_botime
	private Date emhj_botime;
	// / emhj_outime
	private String emhj_outime;
	// / emhj_handin_name
	private String emhj_handin_name;
	// / emhj_fee
	private BigDecimal emhj_fee;
	private BigDecimal emhj_fees;
	// / emhj_surezl_time
	private Date emhj_surezl_time;
	// / emhj_statename1
	private String emhj_statename1;
	// / emhj_statename2
	private String emhj_statename2;
	// / emhj_statename3
	private String emhj_statename3;
	// / emhj_statename4
	private String emhj_statename4;
	// / emhj_statename5
	private String emhj_statename5;
	// / emhj_statename6
	private String emhj_statename6;
	// / emhj_suretime
	private Date emhj_suretime;
	// / 0初始值1借出2还回
	private Integer emhj_grhk;
	// / 0初始值1借出2还回
	private Integer emhj_sy;
	// / 0初始值1借出2还回
	private Integer emhj_syfy;
	// / emhj_feetype
	private String emhj_feetype;
	// / emhj_backtime
	private Date emhj_backtime;
	// / emhj_backfee
	private BigDecimal emhj_backfee;
	// / emhj_confertime
	private Date emhj_confertime;
	// / emhj_outtime
	private Date emhj_outtime;
	// / emhj_outcase
	private String emhj_outcase;
	// / emhj_outplace
	private String emhj_outplace;
	// / emhj_outname
	private String emhj_outname;
	// / emhj_place
	private String emhj_place;
	// / emhj_ifrebackrc
	private Integer emhj_ifrebackrc;
	private Integer emhj_taprid;
	private String coba_shortname;
	private String coba_client;
	private String states;
	private Integer emhj_pid;
	private String emhj_dnaddress, emhj_address;
	private String operateinfo;
	private String emhj_intype;
	private String emhj_intypename;

	public Integer getEmhj_id() {
		return emhj_id;
	}

	public void setEmhj_id(Integer emhj_id) {
		this.emhj_id = emhj_id;
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

	public String getEmhj_no() {
		return emhj_no;
	}

	public void setEmhj_no(String emhj_no) {
		this.emhj_no = emhj_no;
	}

	public String getEmhj_name() {
		return emhj_name;
	}

	public void setEmhj_name(String emhj_name) {
		this.emhj_name = emhj_name;
	}

	public String getEmhj_idcard() {
		return emhj_idcard;
	}

	public void setEmhj_idcard(String emhj_idcard) {
		this.emhj_idcard = emhj_idcard;
	}

	public String getEmhj_state() {
		return emhj_state;
	}

	public void setEmhj_state(String emhj_state) {
		this.emhj_state = emhj_state;
	}

	public String getEmhj_type() {
		return emhj_type;
	}

	public void setEmhj_type(String emhj_type) {
		this.emhj_type = emhj_type;
	}

	public String getEmhj_in_time() {
		return emhj_in_time;
	}

	public void setEmhj_in_time(String emhj_in_time) {
		this.emhj_in_time = emhj_in_time;
	}

	public String getEmhj_in_class() {
		return emhj_in_class;
	}

	public void setEmhj_in_class(String emhj_in_class) {
		this.emhj_in_class = emhj_in_class;
	}

	public Date getEmhj_agreement_time() {
		return emhj_agreement_time;
	}

	public void setEmhj_agreement_time(Date emhj_agreement_time) {
		this.emhj_agreement_time = emhj_agreement_time;
	}

	public String getEmhj_remark() {
		return emhj_remark;
	}

	public void setEmhj_remark(String emhj_remark) {
		this.emhj_remark = emhj_remark;
	}

	public String getEmhj_addname() {
		return emhj_addname;
	}

	public void setEmhj_addname(String emhj_addname) {
		this.emhj_addname = emhj_addname;
	}

	public Date getEmhj_addtime() {
		return emhj_addtime;
	}

	public void setEmhj_addtime(Date emhj_addtime) {
		this.emhj_addtime = emhj_addtime;
	}

	public Date getEmhj_return_time() {
		return emhj_return_time;
	}

	public void setEmhj_return_time(Date emhj_return_time) {
		this.emhj_return_time = emhj_return_time;
	}

	public String getEmhj_case() {
		return emhj_case;
	}

	public void setEmhj_case(String emhj_case) {
		this.emhj_case = emhj_case;
	}

	public Date getEmhj_botime() {
		return emhj_botime;
	}

	public void setEmhj_botime(Date emhj_botime) {
		this.emhj_botime = emhj_botime;
	}

	public String getEmhj_outime() {
		return emhj_outime;
	}

	public void setEmhj_outime(String emhj_outime) {
		this.emhj_outime = emhj_outime;
	}

	public String getEmhj_handin_name() {
		return emhj_handin_name;
	}

	public void setEmhj_handin_name(String emhj_handin_name) {
		this.emhj_handin_name = emhj_handin_name;
	}

	public Date getEmhj_surezl_time() {
		return emhj_surezl_time;
	}

	public void setEmhj_surezl_time(Date emhj_surezl_time) {
		this.emhj_surezl_time = emhj_surezl_time;
	}

	public String getEmhj_statename1() {
		return emhj_statename1;
	}

	public void setEmhj_statename1(String emhj_statename1) {
		this.emhj_statename1 = emhj_statename1;
	}

	public String getEmhj_statename2() {
		return emhj_statename2;
	}

	public void setEmhj_statename2(String emhj_statename2) {
		this.emhj_statename2 = emhj_statename2;
	}

	public String getEmhj_statename3() {
		return emhj_statename3;
	}

	public void setEmhj_statename3(String emhj_statename3) {
		this.emhj_statename3 = emhj_statename3;
	}

	public String getEmhj_statename4() {
		return emhj_statename4;
	}

	public void setEmhj_statename4(String emhj_statename4) {
		this.emhj_statename4 = emhj_statename4;
	}

	public String getEmhj_statename5() {
		return emhj_statename5;
	}

	public void setEmhj_statename5(String emhj_statename5) {
		this.emhj_statename5 = emhj_statename5;
	}

	public String getEmhj_statename6() {
		return emhj_statename6;
	}

	public void setEmhj_statename6(String emhj_statename6) {
		this.emhj_statename6 = emhj_statename6;
	}

	public Date getEmhj_suretime() {
		return emhj_suretime;
	}

	public void setEmhj_suretime(Date emhj_suretime) {
		this.emhj_suretime = emhj_suretime;
	}

	public Integer getEmhj_grhk() {
		return emhj_grhk;
	}

	public void setEmhj_grhk(Integer emhj_grhk) {
		this.emhj_grhk = emhj_grhk;
	}

	public Integer getEmhj_sy() {
		return emhj_sy;
	}

	public void setEmhj_sy(Integer emhj_sy) {
		this.emhj_sy = emhj_sy;
	}

	public Integer getEmhj_syfy() {
		return emhj_syfy;
	}

	public void setEmhj_syfy(Integer emhj_syfy) {
		this.emhj_syfy = emhj_syfy;
	}

	public String getEmhj_feetype() {
		return emhj_feetype;
	}

	public void setEmhj_feetype(String emhj_feetype) {
		this.emhj_feetype = emhj_feetype;
	}

	public Date getEmhj_backtime() {
		return emhj_backtime;
	}

	public void setEmhj_backtime(Date emhj_backtime) {
		this.emhj_backtime = emhj_backtime;
	}

	public Date getEmhj_confertime() {
		return emhj_confertime;
	}

	public void setEmhj_confertime(Date emhj_confertime) {
		this.emhj_confertime = emhj_confertime;
	}

	public Date getEmhj_outtime() {
		return emhj_outtime;
	}

	public void setEmhj_outtime(Date emhj_outtime) {
		this.emhj_outtime = emhj_outtime;
	}

	public String getEmhj_outcase() {
		return emhj_outcase;
	}

	public void setEmhj_outcase(String emhj_outcase) {
		this.emhj_outcase = emhj_outcase;
	}

	public String getEmhj_outplace() {
		return emhj_outplace;
	}

	public void setEmhj_outplace(String emhj_outplace) {
		this.emhj_outplace = emhj_outplace;
	}

	public String getEmhj_outname() {
		return emhj_outname;
	}

	public void setEmhj_outname(String emhj_outname) {
		this.emhj_outname = emhj_outname;
	}

	public String getEmhj_place() {
		return emhj_place;
	}

	public void setEmhj_place(String emhj_place) {
		this.emhj_place = emhj_place;
	}

	public Integer getEmhj_ifrebackrc() {
		return emhj_ifrebackrc;
	}

	public void setEmhj_ifrebackrc(Integer emhj_ifrebackrc) {
		this.emhj_ifrebackrc = emhj_ifrebackrc;
	}

	public BigDecimal getEmhj_fee() {
		return emhj_fee;
	}

	public void setEmhj_fee(BigDecimal emhj_fee) {
		this.emhj_fee = emhj_fee;
	}

	public BigDecimal getEmhj_backfee() {
		return emhj_backfee;
	}

	public void setEmhj_backfee(BigDecimal emhj_backfee) {
		this.emhj_backfee = emhj_backfee;
	}

	public Integer getEmhj_taprid() {
		return emhj_taprid;
	}

	public void setEmhj_taprid(Integer emhj_taprid) {
		this.emhj_taprid = emhj_taprid;
	}

	public String getCoba_shortname() {
		return coba_shortname;
	}

	public void setCoba_shortname(String coba_shortname) {
		this.coba_shortname = coba_shortname;
	}

	public String getStates() {
		return states;
	}

	public void setStates(String states) {
		this.states = states;
	}

	public BigDecimal getEmhj_fees() {
		return emhj_fees;
	}

	public void setEmhj_fees(BigDecimal emhj_fees) {
		this.emhj_fees = emhj_fees;
	}

	public Integer getEmhj_pid() {
		return emhj_pid;
	}

	public void setEmhj_pid(Integer emhj_pid) {
		this.emhj_pid = emhj_pid;
	}

	public String getEmhj_dnaddress() {
		return emhj_dnaddress;
	}

	public void setEmhj_dnaddress(String emhj_dnaddress) {
		this.emhj_dnaddress = emhj_dnaddress;
	}

	public String getEmhj_address() {
		return emhj_address;
	}

	public void setEmhj_address(String emhj_address) {
		this.emhj_address = emhj_address;
	}

	public String getOperateinfo() {
		return operateinfo;
	}

	public void setOperateinfo(String operateinfo) {
		this.operateinfo = operateinfo;
	}

	public String getEmhj_intype() {
		return emhj_intype;
	}

	public void setEmhj_intype(String emhj_intype) {
		this.emhj_intype = emhj_intype;
	}

	public String getEmhj_intypename() {
		return emhj_intypename;
	}

	public void setEmhj_intypename(String emhj_intypename) {
		this.emhj_intypename = emhj_intypename;
	}

	public String getCoba_client() {
		return coba_client;
	}

	public void setCoba_client(String coba_client) {
		this.coba_client = coba_client;
	}

}
