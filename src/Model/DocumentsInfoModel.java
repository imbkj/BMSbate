package Model;

public class DocumentsInfoModel {
	private int doin_id;
	private String doin_type;
	private String doin_class;
	private String doin_content;
	private String doin_state;
	private String doin_remark;
	private String doin_addname;
	private String doin_addtime;
	private String dire_id;
	private String dire_doin_id;
	private String dire_puzu_id;
	private String dire_ifhaveto;
	private String dire_state;
	private String dire_addname;
	private String dire_addtime;
	private String puzu_id;
	private String puzu_page;
	private String puzu_pspell;
	private String puzu_pclass;
	private String dire_ifcheck;
	private String pupi_state; //判断是否已存在上传文件，数据库视图(DocumentsInfo_Dire_PubZul_V)中无该字段
	

	public DocumentsInfoModel() {
		super();
	}
	public DocumentsInfoModel(int doin_id, String doin_type, String doin_class,
			String doin_content, String doin_state, String doin_remark,
			String doin_addname, String doin_addtime, String dire_id,
			String dire_doin_id, String dire_puzu_id, String dire_ifhaveto,
			String dire_state, String dire_addname, String dire_addtime,
			String puzu_id, String puzu_page, String puzu_pspell,
			String puzu_pclass, String dire_ifcheck, String pupi_state) {
		super();
		this.doin_id = doin_id;
		this.doin_type = doin_type;
		this.doin_class = doin_class;
		this.doin_content = doin_content;
		this.doin_state = doin_state;
		this.doin_remark = doin_remark;
		this.doin_addname = doin_addname;
		this.doin_addtime = doin_addtime;
		this.dire_id = dire_id;
		this.dire_doin_id = dire_doin_id;
		this.dire_puzu_id = dire_puzu_id;
		this.dire_ifhaveto = dire_ifhaveto;
		this.dire_state = dire_state;
		this.dire_addname = dire_addname;
		this.dire_addtime = dire_addtime;
		this.puzu_id = puzu_id;
		this.puzu_page = puzu_page;
		this.puzu_pspell = puzu_pspell;
		this.puzu_pclass = puzu_pclass;
		this.dire_ifcheck = dire_ifcheck;
		this.pupi_state = pupi_state;
	}
	public String getPupi_state() {
		return pupi_state;
	}
	public void setPupi_state(String pupi_state) {
		this.pupi_state = pupi_state;
	}
	public int getDoin_id() {
		return doin_id;
	}
	public void setDoin_id(int doin_id) {
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
	public String getDire_state() {
		return dire_state;
	}
	public void setDire_state(String dire_state) {
		this.dire_state = dire_state;
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
	public String getPuzu_id() {
		return puzu_id;
	}
	public void setPuzu_id(String puzu_id) {
		this.puzu_id = puzu_id;
	}
	public String getPuzu_page() {
		return puzu_page;
	}
	public void setPuzu_page(String puzu_page) {
		this.puzu_page = puzu_page;
	}
	public String getPuzu_pspell() {
		return puzu_pspell;
	}
	public void setPuzu_pspell(String puzu_pspell) {
		this.puzu_pspell = puzu_pspell;
	}
	public String getPuzu_pclass() {
		return puzu_pclass;
	}
	public void setPuzu_pclass(String puzu_pclass) {
		this.puzu_pclass = puzu_pclass;
	}
	public String getDire_ifcheck() {
		return dire_ifcheck;
	}
	public void setDire_ifcheck(String dire_ifcheck) {
		this.dire_ifcheck = dire_ifcheck;
	}

	
}
