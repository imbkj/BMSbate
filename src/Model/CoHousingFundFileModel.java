package Model;

public class CoHousingFundFileModel {
	private Integer chff_id;
	private Integer chff_pid;
	private String chff_name;
	private String chff_url;
	private Integer chff_sort;
	private String chff_content;
	private String chff_remark;
	private Integer chff_state;
	private String chff_addtime;
	private String chff_addname;
	private Integer chff_iffile;

	private Boolean uploadState;
	private String uploadTips;

	public Integer getChff_id() {
		return chff_id;
	}

	public void setChff_id(Integer chff_id) {
		this.chff_id = chff_id;
	}

	public Integer getChff_pid() {
		return chff_pid;
	}

	public void setChff_pid(Integer chff_pid) {
		this.chff_pid = chff_pid;
	}

	public String getChff_name() {
		return chff_name;
	}

	public void setChff_name(String chff_name) {
		this.chff_name = chff_name;
	}

	public String getChff_url() {
		return chff_url;
	}

	public void setChff_url(String chff_url) {
		this.chff_url = chff_url;
	}

	public Integer getChff_sort() {
		return chff_sort;
	}

	public void setChff_sort(Integer chff_sort) {
		this.chff_sort = chff_sort;
	}

	public Integer getChff_state() {
		return chff_state;
	}

	public void setChff_state(Integer chff_state) {
		this.chff_state = chff_state;
	}

	public String getChff_addtime() {
		return chff_addtime;
	}

	public void setChff_addtime(String chff_addtime) {
		this.chff_addtime = chff_addtime;
	}

	public String getChff_addname() {
		return chff_addname;
	}

	public void setChff_addname(String chff_addname) {
		this.chff_addname = chff_addname;
	}

	public String getChff_content() {
		return chff_content;
	}

	public void setChff_content(String chff_content) {
		this.chff_content = chff_content;
	}

	public String getChff_remark() {
		return chff_remark;
	}

	public void setChff_remark(String chff_remark) {
		this.chff_remark = chff_remark;
	}

	public void setUploadState(Boolean uploadState) {
		this.uploadState = uploadState;
	}

	public String getUploadTips() {
		return uploadTips;
	}

	public void setUploadTips(String uploadTips) {
		this.uploadTips = uploadTips;
	}

	public Integer getChff_iffile() {
		return chff_iffile;
	}

	public void setChff_iffile(Integer chff_iffile) {
		this.chff_iffile = chff_iffile;
	}

	public Boolean getUploadState() {
		return uploadState;
	}

}
