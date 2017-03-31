package Model;

public class DocumentsHandoverLogModel {
	private int dhlo_id;
	private String dhlo_dsin_id;
	private String dhlo_content;
	private String dhlo_addname;
	private String dhlo_addtime;
	
	
	
	public DocumentsHandoverLogModel(int dhlo_id, String dhlo_dsin_id,
			String dhlo_content, String dhlo_addname, String dhlo_addtime) {
		super();
		this.dhlo_id = dhlo_id;
		this.dhlo_dsin_id = dhlo_dsin_id;
		this.dhlo_content = dhlo_content;
		this.dhlo_addname = dhlo_addname;
		this.dhlo_addtime = dhlo_addtime;
	}
	public int getDhlo_id() {
		return dhlo_id;
	}
	public void setDhlo_id(int dhlo_id) {
		this.dhlo_id = dhlo_id;
	}
	public String getDhlo_dsin_id() {
		return dhlo_dsin_id;
	}
	public void setDhlo_dsin_id(String dhlo_dsin_id) {
		this.dhlo_dsin_id = dhlo_dsin_id;
	}
	public String getDhlo_content() {
		return dhlo_content;
	}
	public void setDhlo_content(String dhlo_content) {
		this.dhlo_content = dhlo_content;
	}
	public String getDhlo_addname() {
		return dhlo_addname;
	}
	public void setDhlo_addname(String dhlo_addname) {
		this.dhlo_addname = dhlo_addname;
	}
	public String getDhlo_addtime() {
		return dhlo_addtime;
	}
	public void setDhlo_addtime(String dhlo_addtime) {
		this.dhlo_addtime = dhlo_addtime;
	}
	
	
}
