package Model;

public class PubProCityModel {
	private int id; // 城市表id
	private String name; // 城市名称
	private int provinceid; // 省份表id
	private String spell; // 城市首字母
	private int cabc_coab_id;// 机构与城市关系表的机构Id
	private int cabc_id;// 机构与城市表Id
	private int cabc_ppc_id;// 机构与城市关系表的城市Id
	private Integer ctcy_cabc_id;
	private int cou;
	private boolean check = false;

	public PubProCityModel(int id, String name, int provinceid) {
		super();
		this.id = id;
		this.name = name;
		this.provinceid = provinceid;
	}

	public PubProCityModel() {
		super();
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getProvinceid() {
		return provinceid;
	}

	public void setProvinceid(int provinceid) {
		this.provinceid = provinceid;
	}

	public String getSpell() {
		return spell;
	}

	public void setSpell(String spell) {
		this.spell = spell;
	}

	public int getCabc_coab_id() {
		return cabc_coab_id;
	}

	public void setCabc_coab_id(int cabc_coab_id) {
		this.cabc_coab_id = cabc_coab_id;
	}

	public int getCabc_id() {
		return cabc_id;
	}

	public void setCabc_id(int cabc_id) {
		this.cabc_id = cabc_id;
	}

	public int getCabc_ppc_id() {
		return cabc_ppc_id;
	}

	public void setCabc_ppc_id(int cabc_ppc_id) {
		this.cabc_ppc_id = cabc_ppc_id;
	}

	public Integer getCtcy_cabc_id() {
		return ctcy_cabc_id;
	}

	public void setCtcy_cabc_id(Integer ctcy_cabc_id) {
		this.ctcy_cabc_id = ctcy_cabc_id;
	}

	public int getCou() {
		return cou;
	}

	public void setCou(int cou) {
		this.cou = cou;
	}

	public boolean isCheck() {
		return check;
	}

	public void setCheck(boolean check) {
		this.check = check;
	}

}
