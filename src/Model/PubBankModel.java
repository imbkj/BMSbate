package Model;

public class PubBankModel {

	private Integer id;
	private String name;
	private Integer pb_state;
	private Integer bank_doc_id;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Integer getPb_state() {
		return pb_state;
	}

	public void setPb_state(Integer pb_state) {
		this.pb_state = pb_state;
	}

	public Integer getBank_doc_id() {
		return bank_doc_id;
	}

	public void setBank_doc_id(Integer bank_doc_id) {
		this.bank_doc_id = bank_doc_id;
	}
	
}
