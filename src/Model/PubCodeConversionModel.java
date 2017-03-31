package Model;

public class PubCodeConversionModel {

	// pcco_id
	private int pcco_id;
	// pucl_id
	private int pucl_id;
	// pcco_name
	private String pcco_name;
	// pcco_cn
	private String pcco_cn;
	// pcco_cn
	private String pcco_cnname;
	// pcco_code
	private String pcco_code;
	// pcco_code+pcco_cn
	private String name;

	public int getPcco_id() {
		return pcco_id;
	}

	public void setPcco_id(int pcco_id) {
		this.pcco_id = pcco_id;
	}

	public int getPucl_id() {
		return pucl_id;
	}

	public void setPucl_id(int pucl_id) {
		this.pucl_id = pucl_id;
	}

	public String getPcco_name() {
		return pcco_name;
	}

	public void setPcco_name(String pcco_name) {
		this.pcco_name = pcco_name;
	}

	public String getPcco_cn() {
		return pcco_cn;
	}

	public void setPcco_cn(String pcco_cn) {
		this.pcco_cn = pcco_cn;
	}

	public String getPcco_code() {
		return pcco_code;
	}

	public void setPcco_code(String pcco_code) {
		this.pcco_code = pcco_code;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPcco_cnname() {
		return pcco_cnname;
	}

	public void setPcco_cnname(String pcco_cnname) {
		this.pcco_cnname = pcco_cnname;
	}
	
}
