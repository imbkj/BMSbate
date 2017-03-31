package Model;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.Date;

public class EmHJBorrowCardModel {
	private DecimalFormat myformat = new DecimalFormat("0.00");
	// / ehbc_id
	private Integer ehbc_id;
	// / ehbc_type
	private String ehbc_type;
	// / ehbc_outime
	private String ehbc_outime;
	// / ehbc_backtime
	private String ehbc_backtime;
	// / ehbc_handin_name
	private String ehbc_handin_name;
	// / ehbc_tableid
	private Integer ehbc_tableid;
	// / ehbc_class
	private Integer ehbc_class;
	// / ehbc_fee
	private BigDecimal ehbc_fee;
	// / ehbc_remark
	private String ehbc_remark;
	// / ehbc_state
	private Integer ehbc_state;
	// / ehbc_wtname
	private String ehbc_wtname;
	// / ehbc_wtidcard
	private String ehbc_wtidcard;
	// / ehbc_wtfeetype
	private String ehbc_wtfeetype;
	// / ehbc_tableChid
	private Integer ehbc_tablechid;
	// / ehbc_case
	private String ehbc_case;
	// / ehbc_backfee
	private BigDecimal ehbc_backfee;
	// / ehbc_backname
	private String ehbc_backname;
	// / ehbc_grhk
	private Integer ehbc_grhk;
	// / ehbc_sy
	private Integer ehbc_sy;
	// / ehbc_syfy
	private Integer ehbc_syfy;
	// / ehbc_addtime
	private Date ehbc_addtime;
	// / ehbc_addname
	private String ehbc_addname;
	// / ehbc_namejk
	private String ehbc_namejk;
	// / ehbc_namehk
	private String ehbc_namehk;
	// / ehbc_feeedittime
	private Date ehbc_feeedittime;
	// / ehbc_feeeditname
	private String ehbc_feeeditname;
	private Integer emhj_id;// 户籍表id
	private String emhj_no;// 户口编号
	private String emhj_name;// 户口员工
	private String coba_company;// 单位名称
	private String emhj_idcard;
	private String emhj_in_time;
	private String emhj_in_class;
	private String emhj_type;
	private String emhj_state;
	private String emba_state;
	private String coba_shortname;
	private String states;
	private Integer ehbc_tarpid;
	private BigDecimal ehbc_fees;

	private String ehbc_cashpledname;
	private String ehbc_cashpledtime;
	
	private String ehbc_backcase;
	
	private String emba_statename;
	private Integer gid,cid;
	private String ehbc_name;
	private String ehbc_idcard;
	private boolean iffamily;
	private boolean isFamily;
	private String emba_name;
	private String emba_idcard;

	public Integer getEhbc_id() {
		return ehbc_id;
	}

	public void setEhbc_id(Integer ehbc_id) {
		this.ehbc_id = ehbc_id;
	}

	public String getEhbc_type() {
		return ehbc_type;
	}

	public void setEhbc_type(String ehbc_type) {
		this.ehbc_type = ehbc_type;
	}

	public String getEhbc_outime() {
		return ehbc_outime;
	}

	public void setEhbc_outime(String ehbc_outime) {
		this.ehbc_outime = ehbc_outime;
	}

	public String getEhbc_backtime() {
		return ehbc_backtime;
	}

	public void setEhbc_backtime(String ehbc_backtime) {
		this.ehbc_backtime = ehbc_backtime;
	}

	public String getEhbc_handin_name() {
		return ehbc_handin_name;
	}

	public void setEhbc_handin_name(String ehbc_handin_name) {
		this.ehbc_handin_name = ehbc_handin_name;
	}

	public Integer getEhbc_tableid() {
		return ehbc_tableid;
	}

	public void setEhbc_tableid(Integer ehbc_tableid) {
		this.ehbc_tableid = ehbc_tableid;
	}

	public Integer getEhbc_class() {
		return ehbc_class;
	}

	public void setEhbc_class(Integer ehbc_class) {
		this.ehbc_class = ehbc_class;
	}

	public BigDecimal getEhbc_fee() {
		return ehbc_fee;
	}

	public void setEhbc_fee(BigDecimal ehbc_fee) {
		if (ehbc_fee == null) {
			ehbc_fee = BigDecimal.ZERO;
		}
		ehbc_fee = new BigDecimal(myformat.format(ehbc_fee).toString());
		this.ehbc_fee = ehbc_fee;
	}

	public String getEhbc_remark() {
		return ehbc_remark;
	}

	public void setEhbc_remark(String ehbc_remark) {
		this.ehbc_remark = ehbc_remark;
	}

	public Integer getEhbc_state() {
		return ehbc_state;
	}

	public void setEhbc_state(Integer ehbc_state) {
		this.ehbc_state = ehbc_state;
	}

	public String getEhbc_wtname() {
		return ehbc_wtname;
	}

	public void setEhbc_wtname(String ehbc_wtname) {
		if(ehbc_wtname!=null&&!ehbc_wtname.equals(""))
		{
			setFamily(true);
		}else
		{
			setFamily(false);
		}
		this.ehbc_wtname = ehbc_wtname;
	}

	public String getEhbc_wtidcard() {
		return ehbc_wtidcard;
	}

	public void setEhbc_wtidcard(String ehbc_wtidcard) {
		this.ehbc_wtidcard = ehbc_wtidcard;
	}

	public String getEhbc_wtfeetype() {
		return ehbc_wtfeetype;
	}

	public void setEhbc_wtfeetype(String ehbc_wtfeetype) {
		this.ehbc_wtfeetype = ehbc_wtfeetype;
	}

	public Integer getEhbc_tablechid() {
		return ehbc_tablechid;
	}

	public void setEhbc_tablechid(Integer ehbc_tablechid) {
		this.ehbc_tablechid = ehbc_tablechid;
	}

	public String getEhbc_case() {
		return ehbc_case;
	}

	public void setEhbc_case(String ehbc_case) {
		this.ehbc_case = ehbc_case;
	}

	public BigDecimal getEhbc_backfee() {
		return ehbc_backfee;
	}

	public void setEhbc_backfee(BigDecimal ehbc_backfee) {
		this.ehbc_backfee = ehbc_backfee;
	}

	public String getEhbc_backname() {
		return ehbc_backname;
	}

	public void setEhbc_backname(String ehbc_backname) {
		this.ehbc_backname = ehbc_backname;
	}

	public Integer getEhbc_grhk() {
		return ehbc_grhk;
	}

	public void setEhbc_grhk(Integer ehbc_grhk) {
		this.ehbc_grhk = ehbc_grhk;
	}

	public Integer getEhbc_sy() {
		return ehbc_sy;
	}

	public void setEhbc_sy(Integer ehbc_sy) {
		this.ehbc_sy = ehbc_sy;
	}

	public Integer getEhbc_syfy() {
		return ehbc_syfy;
	}

	public void setEhbc_syfy(Integer ehbc_syfy) {
		this.ehbc_syfy = ehbc_syfy;
	}

	public Date getEhbc_addtime() {
		return ehbc_addtime;
	}

	public void setEhbc_addtime(Date ehbc_addtime) {
		this.ehbc_addtime = ehbc_addtime;
	}

	public String getEhbc_addname() {
		return ehbc_addname;
	}

	public void setEhbc_addname(String ehbc_addname) {
		this.ehbc_addname = ehbc_addname;
	}

	public String getEhbc_namejk() {
		return ehbc_namejk;
	}

	public void setEhbc_namejk(String ehbc_namejk) {
		this.ehbc_namejk = ehbc_namejk;
	}

	public String getEhbc_namehk() {
		return ehbc_namehk;
	}

	public void setEhbc_namehk(String ehbc_namehk) {
		this.ehbc_namehk = ehbc_namehk;
	}

	public Date getEhbc_feeedittime() {
		return ehbc_feeedittime;
	}

	public void setEhbc_feeedittime(Date ehbc_feeedittime) {
		this.ehbc_feeedittime = ehbc_feeedittime;
	}

	public String getEhbc_feeeditname() {
		return ehbc_feeeditname;
	}

	public void setEhbc_feeeditname(String ehbc_feeeditname) {
		this.ehbc_feeeditname = ehbc_feeeditname;
	}

	public Integer getEmhj_id() {
		return emhj_id;
	}

	public void setEmhj_id(Integer emhj_id) {
		this.emhj_id = emhj_id;
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

	public String getCoba_company() {
		return coba_company;
	}

	public void setCoba_company(String coba_company) {
		this.coba_company = coba_company;
	}

	public String getEmhj_idcard() {
		return emhj_idcard;
	}

	public void setEmhj_idcard(String emhj_idcard) {
		this.emhj_idcard = emhj_idcard;
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

	public String getEmhj_type() {
		return emhj_type;
	}

	public void setEmhj_type(String emhj_type) {
		this.emhj_type = emhj_type;
	}

	public String getEmba_state() {
		return emba_state;
	}

	public void setEmba_state(String emba_state) {
		this.emba_state = emba_state;
	}

	public String getCoba_shortname() {
		return coba_shortname;
	}

	public void setCoba_shortname(String coba_shortname) {
		this.coba_shortname = coba_shortname;
	}

	public String getEmhj_state() {
		return emhj_state;
	}

	public void setEmhj_state(String emhj_state) {
		this.emhj_state = emhj_state;
	}

	public String getStates() {
		return states;
	}

	public void setStates(String states) {
		this.states = states;
	}

	public Integer getEhbc_tarpid() {
		return ehbc_tarpid;
	}

	public void setEhbc_tarpid(Integer ehbc_tarpid) {
		this.ehbc_tarpid = ehbc_tarpid;
	}

	public BigDecimal getEhbc_fees() {
		return ehbc_fees;
	}

	public void setEhbc_fees(BigDecimal ehbc_fees) {
		this.ehbc_fees = ehbc_fees;
	}

	public String getEhbc_cashpledname() {
		return ehbc_cashpledname;
	}

	public void setEhbc_cashpledname(String ehbc_cashpledname) {
		this.ehbc_cashpledname = ehbc_cashpledname;
	}

	public String getEhbc_cashpledtime() {
		return ehbc_cashpledtime;
	}

	public void setEhbc_cashpledtime(String ehbc_cashpledtime) {
		this.ehbc_cashpledtime = ehbc_cashpledtime;
	}

	public String getEmba_statename() {
		return emba_statename;
	}

	public void setEmba_statename(String emba_statename) {
		this.emba_statename = emba_statename;
	}

	public String getEhbc_backcase() {
		return ehbc_backcase;
	}

	public void setEhbc_backcase(String ehbc_backcase) {
		this.ehbc_backcase = ehbc_backcase;
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

	public String getEhbc_name() {
		return ehbc_name;
	}

	public void setEhbc_name(String ehbc_name) {
		this.ehbc_name = ehbc_name;
	}

	public String getEhbc_idcard() {
		return ehbc_idcard;
	}

	public void setEhbc_idcard(String ehbc_idcard) {
		this.ehbc_idcard = ehbc_idcard;
	}

	public boolean isIffamily() {
		return iffamily;
	}

	public void setIffamily(boolean iffamily) {
		this.iffamily = iffamily;
	}

	public boolean isFamily() {
		return isFamily;
	}

	public void setFamily(boolean isFamily) {
		this.isFamily = isFamily;
	}

	public String getEmba_name() {
		return emba_name;
	}

	public void setEmba_name(String emba_name) {
		this.emba_name = emba_name;
	}

	public String getEmba_idcard() {
		return emba_idcard;
	}

	public void setEmba_idcard(String emba_idcard) {
		this.emba_idcard = emba_idcard;
	}
	
}
