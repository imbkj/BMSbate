package Model;

public class PubHealthModel {

	private Integer id;
	private String name;
	private Integer ph_state;
	private String spell;

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

	public Integer getPh_state() {
		return ph_state;
	}

	public void setPh_state(Integer ph_state) {
		this.ph_state = ph_state;
	}

	public String getSpell() {
		return spell;
	}

	public void setSpell(String spell) {
		this.spell = spell;
	}
}
