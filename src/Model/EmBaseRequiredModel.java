package Model;

public class EmBaseRequiredModel {

	// embr_id
	private int embr_id;
	// embr_embl_id
	private int embr_emba_id;
	// embase表中必填字段
	private String embr_column;
	// embr_state
	private int embr_state;
	// embr_addtime
	private String embr_addtime;

	public int getEmbr_id() {
		return embr_id;
	}

	public void setEmbr_id(int embr_id) {
		this.embr_id = embr_id;
	}

	public int getEmbr_emba_id() {
		return embr_emba_id;
	}

	public void setEmbr_emba_id(int embr_emba_id) {
		this.embr_emba_id = embr_emba_id;
	}

	public String getEmbr_column() {
		return embr_column;
	}

	public void setEmbr_column(String embr_column) {
		this.embr_column = embr_column;
	}

	public int getEmbr_state() {
		return embr_state;
	}

	public void setEmbr_state(int embr_state) {
		this.embr_state = embr_state;
	}

	public String getEmbr_addtime() {
		return embr_addtime;
	}

	public void setEmbr_addtime(String embr_addtime) {
		this.embr_addtime = embr_addtime;
	}
}
