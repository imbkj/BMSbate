package Model;

import java.util.List;

import org.zkoss.util.media.Media;

public class CoPolicyNoticeFileModel {
	// / file_id
	private Integer file_id;
	private String file_name;
	// / file_pono_id
	private Integer file_pono_id;
	// / file_title
	private String file_title;
	// / file_url
	private String file_url;
	// / file_state
	private Integer file_state;
	// / file_addname
	private String file_addname;
	// / file_addtime
	private String file_addtime;
	// / file_modname
	private String file_modname;
	// / file_modtime
	private String file_modtime;
	private String file_type;
	private String file_remark;
	private Media media;
	private String errorMsg;
	private List<EmHouseTakeCardInfoModel> tlist;

	public Integer getFile_id() {
		return file_id;
	}

	public void setFile_id(Integer file_id) {
		this.file_id = file_id;
	}

	public Integer getFile_pono_id() {
		return file_pono_id;
	}

	public void setFile_pono_id(Integer file_pono_id) {
		this.file_pono_id = file_pono_id;
	}

	public String getFile_title() {
		return file_title;
	}

	public void setFile_title(String file_title) {
		this.file_title = file_title;
	}

	public String getFile_url() {
		return file_url;
	}

	public void setFile_url(String file_url) {
		this.file_url = file_url;
	}

	public Integer getFile_state() {
		return file_state;
	}

	public void setFile_state(Integer file_state) {
		this.file_state = file_state;
	}

	public String getFile_addname() {
		return file_addname;
	}

	public void setFile_addname(String file_addname) {
		this.file_addname = file_addname;
	}

	public String getFile_addtime() {
		return file_addtime;
	}

	public void setFile_addtime(String file_addtime) {
		this.file_addtime = file_addtime;
	}

	public String getFile_modname() {
		return file_modname;
	}

	public void setFile_modname(String file_modname) {
		this.file_modname = file_modname;
	}

	public String getFile_modtime() {
		return file_modtime;
	}

	public void setFile_modtime(String file_modtime) {
		this.file_modtime = file_modtime;
	}

	public String getFile_type() {
		return file_type;
	}

	public void setFile_type(String file_type) {
		this.file_type = file_type;
	}

	public String getFile_remark() {
		return file_remark;
	}

	public void setFile_remark(String file_remark) {
		this.file_remark = file_remark;
	}

	public Media getMedia() {
		return media;
	}

	public void setMedia(Media media) {
		this.media = media;
	}

	public String getErrorMsg() {
		return errorMsg;
	}

	public void setErrorMsg(String errorMsg) {
		this.errorMsg = errorMsg;
	}

	public List<EmHouseTakeCardInfoModel> getTlist() {
		return tlist;
	}

	public void setTlist(List<EmHouseTakeCardInfoModel> tlist) {
		this.tlist = tlist;
	}

	public String getFile_name() {
		return file_name;
	}

	public void setFile_name(String file_name) {
		this.file_name = file_name;
	}

}
