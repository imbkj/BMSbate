package Model;

public class OfficeModel {
	private int puof_pute_id; // 所属类型id
	private int puof_type; // 是否上传
	private int puof_tid; // 所属表id
	private String puof_url; // 文件路径
	private String puof_addname; // 添加人
	private String puof_addtime; // 添加时间
	private String coco_compacttype;//合同类型
	private String coco_compactid;//合同id
	public int getPuof_pute_id() {
		return puof_pute_id;
	}
	public void setPuof_pute_id(int puof_pute_id) {
		this.puof_pute_id = puof_pute_id;
	}
	public int getPuof_type() {
		return puof_type;
	}
	public void setPuof_type(int puof_type) {
		this.puof_type = puof_type;
	}
	public int getPuof_tid() {
		return puof_tid;
	}
	public void setPuof_tid(int puof_tid) {
		this.puof_tid = puof_tid;
	}
	public String getPuof_url() {
		return puof_url;
	}
	public void setPuof_url(String puof_url) {
		this.puof_url = puof_url;
	}
	public String getPuof_addname() {
		return puof_addname;
	}
	public void setPuof_addname(String puof_addname) {
		this.puof_addname = puof_addname;
	}
	public String getPuof_addtime() {
		return puof_addtime;
	}
	public void setPuof_addtime(String puof_addtime) {
		this.puof_addtime = puof_addtime;
	}
	public String getCoco_compacttype() {
		return coco_compacttype;
	}
	public void setCoco_compacttype(String coco_compacttype) {
		this.coco_compacttype = coco_compacttype;
	}
	public String getCoco_compactid() {
		return coco_compactid;
	}
	public void setCoco_compactid(String coco_compactid) {
		this.coco_compactid = coco_compactid;
	}
	public OfficeModel(int puof_pute_id, int puof_type, int puof_tid,
			String puof_url, String puof_addname, String puof_addtime,
			String coco_compacttype, String coco_compactid) {
		super();
		this.puof_pute_id = puof_pute_id;
		this.puof_type = puof_type;
		this.puof_tid = puof_tid;
		this.puof_url = puof_url;
		this.puof_addname = puof_addname;
		this.puof_addtime = puof_addtime;
		this.coco_compacttype = coco_compacttype;
		this.coco_compactid = coco_compactid;
	}
}
