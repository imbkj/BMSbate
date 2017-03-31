package Model;

public class PubZulModel {
	private int puzu_id;
	private String puzu_page;
	private String puzu_pspell;
	private String puzu_pclass;
	private String puzu_state;
	public int getPuzu_id() {
		return puzu_id;
	}
	public void setPuzu_id(int puzu_id) {
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
	public String getPuzu_state() {
		return puzu_state;
	}
	public void setPuzu_state(String puzu_state) {
		this.puzu_state = puzu_state;
	}
	public PubZulModel(int puzu_id, String puzu_page, String puzu_pspell,
			String puzu_pclass, String puzu_state) {
		super();
		this.puzu_id = puzu_id;
		this.puzu_page = puzu_page;
		this.puzu_pspell = puzu_pspell;
		this.puzu_pclass = puzu_pclass;
		this.puzu_state = puzu_state;
	}
}
