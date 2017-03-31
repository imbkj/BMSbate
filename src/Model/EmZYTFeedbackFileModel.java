package Model;

public class EmZYTFeedbackFileModel {
	private int ezff_id;
	private String ezff_filename;
	private String ezff_fileurl;
	private String ezff_addtime;
	private String ezff_addname;
		
	public EmZYTFeedbackFileModel() {
		super();
	}
	public EmZYTFeedbackFileModel(int ezff_id, String ezff_filename,
			String ezff_fileurl, String ezff_addtime, String ezff_addname) {
		super();
		this.ezff_id = ezff_id;
		this.ezff_filename = ezff_filename;
		this.ezff_fileurl = ezff_fileurl;
		this.ezff_addtime = ezff_addtime;
		this.ezff_addname = ezff_addname;
	}
	
	public int getEzff_id() {
		return ezff_id;
	}
	public void setEzff_id(int ezff_id) {
		this.ezff_id = ezff_id;
	}
	public String getEzff_filename() {
		return ezff_filename;
	}
	public void setEzff_filename(String ezff_filename) {
		this.ezff_filename = ezff_filename;
	}
	public String getEzff_fileurl() {
		return ezff_fileurl;
	}
	public void setEzff_fileurl(String ezff_fileurl) {
		this.ezff_fileurl = ezff_fileurl;
	}
	public String getEzff_addtime() {
		return ezff_addtime;
	}
	public void setEzff_addtime(String ezff_addtime) {
		this.ezff_addtime = ezff_addtime;
	}
	public String getEzff_addname() {
		return ezff_addname;
	}
	public void setEzff_addname(String ezff_addname) {
		this.ezff_addname = ezff_addname;
	}

	
}
