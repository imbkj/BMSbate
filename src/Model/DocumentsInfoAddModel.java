package Model;

public class DocumentsInfoAddModel {
	private int doin_id;
	private int doin_type;
	private String doin_class;
	private String doin_content;
	private int doin_state;
	private String doin_remark;
	private String doin_addname;
	private String doin_addtime;
	public int getDoin_id() {
		return doin_id;
	}
	public void setDoin_id(int doin_id) {
		this.doin_id = doin_id;
	}
	public int getDoin_type() {
		return doin_type;
	}
	public void setDoin_type(int doin_type) {
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
	public int getDoin_state() {
		return doin_state;
	}
	public void setDoin_state(int doin_state) {
		this.doin_state = doin_state;
	}
	public String getDoin_remark() {
		return doin_remark;
	}
	public void setDoin_remark(String doin_remark) {
		this.doin_remark = doin_remark;
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
	
	public DocumentsInfoAddModel(int doin_id,int doin_type,
			String doin_content, String doin_remark,int doin_state) {
		super();
		this.doin_id = doin_id;
		this.doin_type = doin_type;
		this.doin_content = doin_content;
		this.doin_remark = doin_remark;
		this.doin_state = doin_state;
	}
}
