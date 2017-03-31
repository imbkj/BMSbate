package Model;

public class DocumentsSubmitInfoModel {
	private int dsin_id;
	private String dsin_dire_id;
	private String dsin_tid;
	private String dsin_ifsubmit;
	private String dsin_count;
	private String dsin_state;
	private String dsin_addname;
	private String dsin_addtime;
	private String dire_id;
	private String dire_doin_id;
	private String dire_puzu_id;
	private String dire_ifhaveto;
	private String dire_ifcheck;
	private String dire_state;
	private String doin_id;
	private String doin_type;
	private String doin_class;
	private String doin_content;
	private String doin_state;
	private String doin_remark;
	private String dire_addname;
	private String dire_addtime;
	private String doin_addname;
	private String doin_addtime;
	private String pupi_state; //判断是否已存在上传文件，数据库视图(DocumentsSubmitInfo_Dire_DocInfo_V)中无该字段
	private String dsin_out_count;
	
	
	
	
	public DocumentsSubmitInfoModel(int dsin_id, String dsin_dire_id,
			String dsin_tid, String dsin_ifsubmit, String dsin_count,
			String dsin_state, String dsin_addname, String dsin_addtime,
			String dire_id, String dire_doin_id, String dire_puzu_id,
			String dire_ifhaveto, String dire_ifcheck, String dire_state,
			String doin_id, String doin_type, String doin_class,
			String doin_content, String doin_state, String doin_remark,
			String dire_addname, String dire_addtime, String doin_addname,
			String doin_addtime, String pupi_state, String dsin_out_count) {
		super();
		this.dsin_id = dsin_id;
		this.dsin_dire_id = dsin_dire_id;
		this.dsin_tid = dsin_tid;
		this.dsin_ifsubmit = dsin_ifsubmit;
		this.dsin_count = dsin_count;
		this.dsin_state = dsin_state;
		this.dsin_addname = dsin_addname;
		this.dsin_addtime = dsin_addtime;
		this.dire_id = dire_id;
		this.dire_doin_id = dire_doin_id;
		this.dire_puzu_id = dire_puzu_id;
		this.dire_ifhaveto = dire_ifhaveto;
		this.dire_ifcheck = dire_ifcheck;
		this.dire_state = dire_state;
		this.doin_id = doin_id;
		this.doin_type = doin_type;
		this.doin_class = doin_class;
		this.doin_content = doin_content;
		this.doin_state = doin_state;
		this.doin_remark = doin_remark;
		this.dire_addname = dire_addname;
		this.dire_addtime = dire_addtime;
		this.doin_addname = doin_addname;
		this.doin_addtime = doin_addtime;
		this.pupi_state = pupi_state;
		this.dsin_out_count = dsin_out_count;
	}
	public String getDsin_out_count() {
		return dsin_out_count;
	}
	public void setDsin_out_count(String dsin_out_count) {
		this.dsin_out_count = dsin_out_count;
	}
	public DocumentsSubmitInfoModel() {
		// TODO Auto-generated constructor stub
	}
	public String getDsin_count() {
		return dsin_count;
	}
	public void setDsin_count(String dsin_count) {
		this.dsin_count = dsin_count;
	}
	public String getPupi_state() {
		return pupi_state;
	}
	public void setPupi_state(String pupi_state) {
		this.pupi_state = pupi_state;
	}
	public int getDsin_id() {
		return dsin_id;
	}
	public void setDsin_id(int dsin_id) {
		this.dsin_id = dsin_id;
	}
	public String getDsin_dire_id() {
		return dsin_dire_id;
	}
	public void setDsin_dire_id(String dsin_dire_id) {
		this.dsin_dire_id = dsin_dire_id;
	}
	public String getDsin_tid() {
		return dsin_tid;
	}
	public void setDsin_tid(String dsin_tid) {
		this.dsin_tid = dsin_tid;
	}
	public String getDsin_ifsubmit() {
		return dsin_ifsubmit;
	}
	public void setDsin_ifsubmit(String dsin_ifsubmit) {
		this.dsin_ifsubmit = dsin_ifsubmit;
	}
	public String getDsin_state() {
		return dsin_state;
	}
	public void setDsin_state(String dsin_state) {
		this.dsin_state = dsin_state;
	}
	public String getDsin_addname() {
		return dsin_addname;
	}
	public void setDsin_addname(String dsin_addname) {
		this.dsin_addname = dsin_addname;
	}
	public String getDsin_addtime() {
		return dsin_addtime;
	}
	public void setDsin_addtime(String dsin_addtime) {
		this.dsin_addtime = dsin_addtime;
	}
	public String getDire_id() {
		return dire_id;
	}
	public void setDire_id(String dire_id) {
		this.dire_id = dire_id;
	}
	public String getDire_doin_id() {
		return dire_doin_id;
	}
	public void setDire_doin_id(String dire_doin_id) {
		this.dire_doin_id = dire_doin_id;
	}
	public String getDire_puzu_id() {
		return dire_puzu_id;
	}
	public void setDire_puzu_id(String dire_puzu_id) {
		this.dire_puzu_id = dire_puzu_id;
	}
	public String getDire_ifhaveto() {
		return dire_ifhaveto;
	}
	public void setDire_ifhaveto(String dire_ifhaveto) {
		this.dire_ifhaveto = dire_ifhaveto;
	}
	public String getDire_ifcheck() {
		return dire_ifcheck;
	}
	public void setDire_ifcheck(String dire_ifcheck) {
		this.dire_ifcheck = dire_ifcheck;
	}
	public String getDire_state() {
		return dire_state;
	}
	public void setDire_state(String dire_state) {
		this.dire_state = dire_state;
	}
	public String getDoin_id() {
		return doin_id;
	}
	public void setDoin_id(String doin_id) {
		this.doin_id = doin_id;
	}
	public String getDoin_type() {
		return doin_type;
	}
	public void setDoin_type(String doin_type) {
		this.doin_type = doin_type;
	}
	public String getDoin_class() {
		return doin_class;
	}
	public void setDoin_class(String doin_class) {
		this.doin_class = doin_class;
	}
	public String getDoin_content() {
		return doin_content;
	}
	public void setDoin_content(String doin_content) {
		this.doin_content = doin_content;
	}
	public String getDoin_state() {
		return doin_state;
	}
	public void setDoin_state(String doin_state) {
		this.doin_state = doin_state;
	}
	public String getDoin_remark() {
		return doin_remark;
	}
	public void setDoin_remark(String doin_remark) {
		this.doin_remark = doin_remark;
	}
	public String getDire_addname() {
		return dire_addname;
	}
	public void setDire_addname(String dire_addname) {
		this.dire_addname = dire_addname;
	}
	public String getDire_addtime() {
		return dire_addtime;
	}
	public void setDire_addtime(String dire_addtime) {
		this.dire_addtime = dire_addtime;
	}
	public String getDoin_addname() {
		return doin_addname;
	}
	public void setDoin_addname(String doin_addname) {
		this.doin_addname = doin_addname;
	}
	public String getDoin_addtime() {
		return doin_addtime;
	}
	public void setDoin_addtime(String doin_addtime) {
		this.doin_addtime = doin_addtime;
	}

	
}
