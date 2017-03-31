package Model;

public class EmbodyCheckFileModel {
    /// file_id
    private Integer  file_id;
             /// 体检医院id
    private Integer  file_ebcs_id;
             /// 体检医院地址id
    private Integer  file_ebsa_id;
             /// 文件路径
    private String  file_url;
             /// file_remark
    private String  file_remark;
             /// 状态：1、有效；0、无效；
    private Integer  file_state;
    
    private String file_addname;
    
    private String file_filename;
    private String ebcs_hospital;
    private String ebsa_address;
	public Integer getFile_id() {
		return file_id;
	}
	public void setFile_id(Integer file_id) {
		this.file_id = file_id;
	}
	public Integer getFile_ebcs_id() {
		return file_ebcs_id;
	}
	public void setFile_ebcs_id(Integer file_ebcs_id) {
		this.file_ebcs_id = file_ebcs_id;
	}
	public Integer getFile_ebsa_id() {
		return file_ebsa_id;
	}
	public void setFile_ebsa_id(Integer file_ebsa_id) {
		this.file_ebsa_id = file_ebsa_id;
	}
	public String getFile_url() {
		return file_url;
	}
	public void setFile_url(String file_url) {
		this.file_url = file_url;
	}
	public String getFile_remark() {
		return file_remark;
	}
	public void setFile_remark(String file_remark) {
		this.file_remark = file_remark;
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
	public String getFile_filename() {
		return file_filename;
	}
	public void setFile_filename(String file_filename) {
		this.file_filename = file_filename;
	}
	public String getEbcs_hospital() {
		return ebcs_hospital;
	}
	public void setEbcs_hospital(String ebcs_hospital) {
		this.ebcs_hospital = ebcs_hospital;
	}
	public String getEbsa_address() {
		return ebsa_address;
	}
	public void setEbsa_address(String ebsa_address) {
		this.ebsa_address = ebsa_address;
	}
	
}
