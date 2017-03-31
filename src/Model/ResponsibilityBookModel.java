package Model;

public class ResponsibilityBookModel {
    /// rebk_id
    private Integer  rebk_id;
             /// cid
    private Integer  cid;
             /// 责任书签订时间
    private String  rebk_signdate;
             /// 责任书签订人
    private String  rebk_signname;
             /// 责任书到期时间
    private String  rebk_enddate;
             /// 责任书年限
    private String  rebk_limit;
             /// 人事领取材料和表格时间
    private String  rebk_rs_taketime;
             /// 人事领取表格人
    private String  rsbk_rs_takename;
             /// 客服领取表格时间
    private String  rsbk_client_taketime;
             /// 领取表格的客服
    private String  rsbk_client_takename;
             /// 人事确认材料时间
    private String  rsbk_rs_signtime;
             /// 人事确认材料人
    private String  rsbk_rs_signname;
             /// 认识办理完成日期
    private String  rsbk_rs_oktime;
             /// rebk_state
    private Integer  rebk_state;
             /// 添加时间
    private String  rsbk_addtime;
             /// 添加人
    private String  rsbk_addname;
             /// 最后修改时间
    private String  rsbk_modtime;
             /// 最后修改人
    private String  rsbk_modname;
    private String rsbk_booktype;
    private Integer rsbk_cori_id;
    private String coba_shortname;
    private String rebk_addname;
    private String addtime;
    private int rebk_cori_id;
    private String rebk_step_state;
    
    
	public int getRebk_cori_id() {
		return rebk_cori_id;
	}
	public void setRebk_cori_id(int rebk_cori_id) {
		this.rebk_cori_id = rebk_cori_id;
	}
	public String getRebk_step_state() {
		return rebk_step_state;
	}
	public void setRebk_step_state(String rebk_step_state) {
		this.rebk_step_state = rebk_step_state;
	}
	public String getRebk_addname() {
		return rebk_addname;
	}
	public void setRebk_addname(String rebk_addname) {
		this.rebk_addname = rebk_addname;
	}
	public String getAddtime() {
		return addtime;
	}
	public void setAddtime(String addtime) {
		this.addtime = addtime;
	}

	public String getCoba_shortname() {
		return coba_shortname;
	}
	public void setCoba_shortname(String coba_shortname) {
		this.coba_shortname = coba_shortname;
	}
	public Integer getRebk_id() {
		return rebk_id;
	}
	public void setRebk_id(Integer rebk_id) {
		this.rebk_id = rebk_id;
	}
	public Integer getCid() {
		return cid;
	}
	public void setCid(Integer cid) {
		this.cid = cid;
	}
	public String getRebk_signdate() {
		return rebk_signdate;
	}
	public void setRebk_signdate(String rebk_signdate) {
		this.rebk_signdate = rebk_signdate;
	}
	public String getRebk_signname() {
		return rebk_signname;
	}
	public void setRebk_signname(String rebk_signname) {
		this.rebk_signname = rebk_signname;
	}
	public String getRebk_enddate() {
		return rebk_enddate;
	}
	public void setRebk_enddate(String rebk_enddate) {
		this.rebk_enddate = rebk_enddate;
	}
	public String getRebk_limit() {
		return rebk_limit;
	}
	public void setRebk_limit(String rebk_limit) {
		this.rebk_limit = rebk_limit;
	}
	public String getRebk_rs_taketime() {
		return rebk_rs_taketime;
	}
	public void setRebk_rs_taketime(String rebk_rs_taketime) {
		this.rebk_rs_taketime = rebk_rs_taketime;
	}
	public String getRsbk_rs_takename() {
		return rsbk_rs_takename;
	}
	public void setRsbk_rs_takename(String rsbk_rs_takename) {
		this.rsbk_rs_takename = rsbk_rs_takename;
	}
	public String getRsbk_client_taketime() {
		return rsbk_client_taketime;
	}
	public void setRsbk_client_taketime(String rsbk_client_taketime) {
		this.rsbk_client_taketime = rsbk_client_taketime;
	}
	public String getRsbk_client_takename() {
		return rsbk_client_takename;
	}
	public void setRsbk_client_takename(String rsbk_client_takename) {
		this.rsbk_client_takename = rsbk_client_takename;
	}
	public String getRsbk_rs_signtime() {
		return rsbk_rs_signtime;
	}
	public void setRsbk_rs_signtime(String rsbk_rs_signtime) {
		this.rsbk_rs_signtime = rsbk_rs_signtime;
	}
	public String getRsbk_rs_signname() {
		return rsbk_rs_signname;
	}
	public void setRsbk_rs_signname(String rsbk_rs_signname) {
		this.rsbk_rs_signname = rsbk_rs_signname;
	}
	public String getRsbk_rs_oktime() {
		return rsbk_rs_oktime;
	}
	public void setRsbk_rs_oktime(String rsbk_rs_oktime) {
		this.rsbk_rs_oktime = rsbk_rs_oktime;
	}
	public Integer getRebk_state() {
		return rebk_state;
	}
	public void setRebk_state(Integer rebk_state) {
		this.rebk_state = rebk_state;
	}
	public String getRsbk_addtime() {
		return rsbk_addtime;
	}
	public void setRsbk_addtime(String rsbk_addtime) {
		this.rsbk_addtime = rsbk_addtime;
	}
	public String getRsbk_addname() {
		return rsbk_addname;
	}
	public void setRsbk_addname(String rsbk_addname) {
		this.rsbk_addname = rsbk_addname;
	}
	public String getRsbk_modtime() {
		return rsbk_modtime;
	}
	public void setRsbk_modtime(String rsbk_modtime) {
		this.rsbk_modtime = rsbk_modtime;
	}
	public String getRsbk_modname() {
		return rsbk_modname;
	}
	public void setRsbk_modname(String rsbk_modname) {
		this.rsbk_modname = rsbk_modname;
	}
	public String getRsbk_booktype() {
		return rsbk_booktype;
	}
	public void setRsbk_booktype(String rsbk_booktype) {
		this.rsbk_booktype = rsbk_booktype;
	}
	public Integer getRsbk_cori_id() {
		return rsbk_cori_id;
	}
	public void setRsbk_cori_id(Integer rsbk_cori_id) {
		this.rsbk_cori_id = rsbk_cori_id;
	}
    
    
}
