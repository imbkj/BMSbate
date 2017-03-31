package Model;
import java.util.Date;

public class VisitInfoModel {
	
	private int viin_id;
	private int viin_month;
	private String viin_type;
	private String viin_person;
	private String viin_class;
	private String viin_aim;
	private String viin_cost;
	private String viin_costremark;
	private String viin_summary;
	private String viin_feedback;
	private boolean viin_iffolow;
	private int viin_state;
	private String viin_stateStr;
	private String viin_starttime;
	private String viin_endtime;
	private String viin_remark;
	
	
	private Date viin_truetime;
	private String viin_addname;
	private Date viin_addtime;
	private String viin_auditingname;
	private Date viin_auditingtime;
	public String getViin_remark() {
		return viin_remark;
	}
	public void setViin_remark(String viin_remark) {
		this.viin_remark = viin_remark;
	}


	private String  viin_subordinate;
	private String viin_personed;
	private String viin_job;	
	private String state;
	
	private int covi_id;
	private int  covi_viin_id; 
	private int covi_cola_id; 
	private int cid;
	
	
	private int cola_id;
	private String cola_company;
	private String cola_spell;
	private String cola_companytype;
	private String cola_address;
	private String cola_clientarea;
	private int cola_state;
	
	private int viin_tapr_id;
	
	
	public String getViin_starttime() {
		return viin_starttime;
	}
	public void setViin_starttime(String viin_starttime) {
		this.viin_starttime = viin_starttime;
	}
	public String getViin_endtime() {
		return viin_endtime;
	}
	public void setViin_endtime(String viin_endtime) {
		this.viin_endtime = viin_endtime;
	}
	public int getViin_tapr_id() {
		return viin_tapr_id;
	}
	public void setViin_tapr_id(int viin_tapr_id) {
		this.viin_tapr_id = viin_tapr_id;
	}
	public String getViin_stateStr() {
		return viin_stateStr;
	}
	public void setViin_stateStr(String viin_stateStr) {
		this.viin_stateStr = viin_stateStr;
	}
	public String getViin_subordinate() {
		return viin_subordinate;
	}
	public void setViin_subordinate(String viin_subordinate) {
		this.viin_subordinate = viin_subordinate;
	}
	public String getViin_personed() {
		return viin_personed;
	}
	public void setViin_personed(String viin_personed) {
		this.viin_personed = viin_personed;
	}
	public String getViin_job() {
		return viin_job;
	}
	public void setViin_job(String viin_job) {
		this.viin_job = viin_job;
	}
	public int getCola_id() {
		return cola_id;
	}
	public void setCola_id(int cola_id) {
		this.cola_id = cola_id;
	}
	public String getCola_company() {
		return cola_company;
	}
	public void setCola_company(String cola_company) {
		this.cola_company = cola_company;
	}
	public String getCola_spell() {
		return cola_spell;
	}
	public void setCola_spell(String cola_spell) {
		this.cola_spell = cola_spell;
	}
	public String getCola_companytype() {
		return cola_companytype;
	}
	public void setCola_companytype(String cola_companytype) {
		this.cola_companytype = cola_companytype;
	}
	public String getCola_address() {
		return cola_address;
	}
	public void setCola_address(String cola_address) {
		this.cola_address = cola_address;
	}
	public String getCola_clientarea() {
		return cola_clientarea;
	}
	public void setCola_clientarea(String cola_clientarea) {
		this.cola_clientarea = cola_clientarea;
	}
	public int getCola_state() {
		return cola_state;
	}
	public void setCola_state(int cola_state) {
		this.cola_state = cola_state;
	}
	public int getViin_id() {
		return viin_id;
	}
	public void setViin_id(int viin_id) {
		this.viin_id = viin_id;
	}
	public int getViin_month() {
		return viin_month;
	}
	public void setViin_month(int viin_month) {
		this.viin_month = viin_month;
	}
	public String getViin_type() {
		return viin_type;
	}
	public void setViin_type(String viin_type) {
		this.viin_type = viin_type;
	}
	public String getViin_person() {
		return viin_person;
	}
	public void setViin_person(String viin_person) {
		this.viin_person = viin_person;
	}
	public String getViin_class() {
		return viin_class;
	}
	public void setViin_class(String viin_class) {
		this.viin_class = viin_class;
	}
	public String getViin_aim() {
		return viin_aim;
	}
	public void setViin_aim(String viin_aim) {
		this.viin_aim = viin_aim;
	}
	public String getViin_cost() {
		return viin_cost;
	}
	public void setViin_cost(String viin_cost) {
		this.viin_cost = viin_cost;
	}
	public String getViin_costremark() {
		return viin_costremark;
	}
	public void setViin_costremark(String viin_costremark) {
		this.viin_costremark = viin_costremark;
	}
	public String getViin_summary() {
		return viin_summary;
	}
	public void setViin_summary(String viin_summary) {
		this.viin_summary = viin_summary;
	}
	public String getViin_feedback() {
		return viin_feedback;
	}
	public void setViin_feedback(String viin_feedback) {
		this.viin_feedback = viin_feedback;
	}
	public boolean isViin_iffolow() {
		return viin_iffolow;
	}
	public void setViin_iffolow(boolean viin_iffolow) {
		this.viin_iffolow = viin_iffolow;
	}
	public int getViin_state() {
		return viin_state;
	}
	public void setViin_state(int viin_state) {
		this.viin_state = viin_state;
	}
	public Date getViin_truetime() {
		return viin_truetime;
	}
	public void setViin_truetime(Date viin_truetime) {
		this.viin_truetime = viin_truetime;
	}
	public String getViin_addname() {
		return viin_addname;
	}
	public void setViin_addname(String viin_addname) {
		this.viin_addname = viin_addname;
	}
	public Date getViin_addtime() {
		return viin_addtime;
	}
	public void setViin_addtime(Date viin_addtime) {
		this.viin_addtime = viin_addtime;
	}
	public String getViin_auditingname() {
		return viin_auditingname;
	}
	public void setViin_auditingname(String viin_auditingname) {
		this.viin_auditingname = viin_auditingname;
	}
	public Date getViin_auditingtime() {
		return viin_auditingtime;
	}
	public void setViin_auditingtime(Date viin_auditingtime) {
		this.viin_auditingtime = viin_auditingtime;
	}
	public int getCovi_id() {
		return covi_id;
	}
	public void setCovi_id(int covi_id) {
		this.covi_id = covi_id;
	}
	public int getCovi_viin_id() {
		return covi_viin_id;
	}
	public void setCovi_viin_id(int covi_viin_id) {
		this.covi_viin_id = covi_viin_id;
	}
	public int getCovi_cola_id() {
		return covi_cola_id;
	}
	public void setCovi_cola_id(int covi_cola_id) {
		this.covi_cola_id = covi_cola_id;
	}
	public int getCid() {
		return cid;
	}
	public void setCid(int cid) {
		this.cid = cid;
	}
	public String getState() {
		return state;
	}
	public void setState(String state) {
		this.state = state;
	}
	
	public VisitInfoModel(int viin_id, int viin_month, String viin_type,
			String viin_person, String viin_class, String viin_aim,
			String viin_cost, String viin_costremark, String viin_summary,
			String viin_feedback, boolean viin_iffolow, int viin_state,
			Date viin_truetime, String viin_addname, Date viin_addtime,
			String viin_auditingname, Date viin_auditingtime,
			String viin_subordinate, String viin_personed, String viin_job,
			int covi_id, int covi_viin_id, int covi_cola_id, int cid,
			int cola_id, String cola_company, String cola_spell,
			String cola_companytype, String cola_address,
			String cola_clientarea, int cola_state) {
		super();
		this.viin_id = viin_id;
		this.viin_month = viin_month;
		this.viin_type = viin_type;
		this.viin_person = viin_person;
		this.viin_class = viin_class;
		this.viin_aim = viin_aim;
		this.viin_cost = viin_cost;
		this.viin_costremark = viin_costremark;
		this.viin_summary = viin_summary;
		this.viin_feedback = viin_feedback;
		this.viin_iffolow = viin_iffolow;
		this.viin_state = viin_state;
		this.viin_truetime = viin_truetime;
		this.viin_addname = viin_addname;
		this.viin_addtime = viin_addtime;
		this.viin_auditingname = viin_auditingname;
		this.viin_auditingtime = viin_auditingtime;
		this.viin_subordinate = viin_subordinate;
		this.viin_personed = viin_personed;
		this.viin_job = viin_job;
		this.covi_id = covi_id;
		this.covi_viin_id = covi_viin_id;
		this.covi_cola_id = covi_cola_id;
		this.cid = cid;
		this.cola_id = cola_id;
		this.cola_company = cola_company;
		this.cola_spell = cola_spell;
		this.cola_companytype = cola_companytype;
		this.cola_address = cola_address;
		this.cola_clientarea = cola_clientarea;
		this.cola_state = cola_state;
	}
	public VisitInfoModel() {
		super();
	}
	public VisitInfoModel(int viin_id, int viin_month, String viin_type,
			String viin_person, String viin_class, int viin_state,
			String viin_addname, Date viin_addtime, String viin_auditingname,
			Date viin_auditingtime, int covi_cola_id, int cid, int cola_id,
			String cola_company, String cola_spell, String cola_companytype,
			String cola_address, String cola_clientarea, int cola_state,String viin_stateStr,String viin_subordinate,int viin_tapr_id) {
		super();
		this.viin_id = viin_id;
		this.viin_month = viin_month;
		this.viin_type = viin_type;
		this.viin_person = viin_person;
		this.viin_class = viin_class;
		this.viin_state = viin_state;
		this.viin_addname = viin_addname;
		this.viin_addtime = viin_addtime;
		this.viin_auditingname = viin_auditingname;
		this.viin_auditingtime = viin_auditingtime;
		this.covi_cola_id = covi_cola_id;
		this.cid = cid;
		this.cola_id = cola_id;
		this.cola_company = cola_company;
		this.cola_spell = cola_spell;
		this.cola_companytype = cola_companytype;
		this.cola_address = cola_address;
		this.cola_clientarea = cola_clientarea;
		this.cola_state = cola_state;
		this.viin_stateStr = viin_stateStr;
		this.viin_subordinate = viin_subordinate;
		this.viin_tapr_id=viin_tapr_id;
	}
	
	
	public VisitInfoModel(int viin_id, int viin_month, String viin_type,
			String viin_person, String viin_class, int viin_state,
			String viin_addname, Date viin_addtime, String viin_auditingname,
			Date viin_auditingtime, int covi_cola_id, int cid, int cola_id,
			String cola_company, String cola_spell, String cola_companytype,
			String cola_address, String cola_clientarea, int cola_state,String viin_stateStr,String viin_subordinate,int viin_tapr_id
			, String viin_starttime,
			String viin_endtime, String viin_remark) {
		super();
		this.viin_id = viin_id;
		this.viin_month = viin_month;
		this.viin_type = viin_type;
		this.viin_person = viin_person;
		this.viin_class = viin_class;
		this.viin_state = viin_state;
		this.viin_addname = viin_addname;
		this.viin_addtime = viin_addtime;
		this.viin_auditingname = viin_auditingname;
		this.viin_auditingtime = viin_auditingtime;
		this.covi_cola_id = covi_cola_id;
		this.cid = cid;
		this.cola_id = cola_id;
		this.cola_company = cola_company;
		this.cola_spell = cola_spell;
		this.cola_companytype = cola_companytype;
		this.cola_address = cola_address;
		this.cola_clientarea = cola_clientarea;
		this.cola_state = cola_state;
		this.viin_stateStr = viin_stateStr;
		this.viin_subordinate = viin_subordinate;
		this.viin_tapr_id=viin_tapr_id;
		this.viin_starttime=viin_starttime;
		this.viin_endtime=viin_endtime;
		this.viin_remark=viin_remark;
	
		
	}
	
	
}
