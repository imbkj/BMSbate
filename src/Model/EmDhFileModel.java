package Model;

public class EmDhFileModel {

	// / dhfl_id
	private Integer dhfl_id;
	// / dhfl_dh_id
	private Integer dhfl_dh_id;
	// / dhfl_file_id
	private Integer dhfl_file_id;
	// / dhfl_state
	private Integer dhfl_state;
	// / file_id
	private Integer file_id;
	// / file_finame
	private String file_finame;
	// / file_state
	private Integer file_state;
	
	private String dhfl_file_content;
	private String dhfl_addname;
	private String dhfl_addtime;
	private Integer dhfl_checked;
	private boolean ischecked=false;
	private String filename;
	public Integer getDhfl_id() {
		return dhfl_id;
	}

	public void setDhfl_id(Integer dhfl_id) {
		this.dhfl_id = dhfl_id;
	}

	public Integer getDhfl_dh_id() {
		return dhfl_dh_id;
	}

	public void setDhfl_dh_id(Integer dhfl_dh_id) {
		this.dhfl_dh_id = dhfl_dh_id;
	}

	public Integer getDhfl_file_id() {
		return dhfl_file_id;
	}

	public void setDhfl_file_id(Integer dhfl_file_id) {
		this.dhfl_file_id = dhfl_file_id;
	}

	public Integer getDhfl_state() {
		return dhfl_state;
	}

	public void setDhfl_state(Integer dhfl_state) {
		this.dhfl_state = dhfl_state;
	}

	public Integer getFile_id() {
		return file_id;
	}

	public void setFile_id(Integer file_id) {
		this.file_id = file_id;
	}

	public String getFile_finame() {
		return file_finame;
	}

	public void setFile_finame(String file_finame) {
		this.file_finame = file_finame;
	}

	public Integer getFile_state() {
		return file_state;
	}

	public void setFile_state(Integer file_state) {
		this.file_state = file_state;
	}

	public String getDhfl_file_content() {
		return dhfl_file_content;
	}

	public void setDhfl_file_content(String dhfl_file_content) {
		this.dhfl_file_content = dhfl_file_content;
	}

	public String getDhfl_addname() {
		return dhfl_addname;
	}

	public void setDhfl_addname(String dhfl_addname) {
		this.dhfl_addname = dhfl_addname;
	}

	public String getDhfl_addtime() {
		return dhfl_addtime;
	}

	public void setDhfl_addtime(String dhfl_addtime) {
		this.dhfl_addtime = dhfl_addtime;
	}

	public Integer getDhfl_checked() {
		return dhfl_checked;
	}

	public void setDhfl_checked(Integer dhfl_checked) {
		if(dhfl_checked!=null&&dhfl_checked==1)
		{
			setIschecked(true);
		}
		this.dhfl_checked = dhfl_checked;
	}

	public boolean isIschecked() {
		return ischecked;
	}

	public void setIschecked(boolean ischecked) {
		this.ischecked = ischecked;
	}

	public String getFilename() {
		return filename;
	}

	public void setFilename(String filename) {
		this.filename = filename;
	}
	
}
