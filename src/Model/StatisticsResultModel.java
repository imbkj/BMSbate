package Model;

public class StatisticsResultModel {
	private int stre_id;
	private int stre_log_id;
	private int stre_smonth;
	private int stre_emonth;
	private String stre_content;
	private String stre_sum;
	private int stre_state;

	private int stsq_id;
	private int stsq_puzu_id;
	private String stsq_content;
	private String stsq_sql;
	private int stsq_state;
	private String addname;
	private String addtime;
	private int stsq_type;
	private String s_stsq_type;
	private String stsq_uom;
	private int stsq_graph;
	private String s_stsq_graph;
	
	private String puzu_class;
	private String puzu_page;
	
	public StatisticsResultModel() {
		super();
	}

	public StatisticsResultModel(int stre_id, int stre_log_id, int stre_smonth,
			int stre_emonth, String stre_content, String stre_sum,
			int stre_state, int stsq_id, int stsq_puzu_id, String stsq_content,
			String stsq_sql, int stsq_state, String addname, String addtime,
			int stsq_type, String s_stsq_type, String stsq_uom, int stsq_graph,
			String s_stsq_graph, String puzu_class, String puzu_page) {
		super();
		this.stre_id = stre_id;
		this.stre_log_id = stre_log_id;
		this.stre_smonth = stre_smonth;
		this.stre_emonth = stre_emonth;
		this.stre_content = stre_content;
		this.stre_sum = stre_sum;
		this.stre_state = stre_state;
		this.stsq_id = stsq_id;
		this.stsq_puzu_id = stsq_puzu_id;
		this.stsq_content = stsq_content;
		this.stsq_sql = stsq_sql;
		this.stsq_state = stsq_state;
		this.addname = addname;
		this.addtime = addtime;
		this.stsq_type = stsq_type;
		this.s_stsq_type = s_stsq_type;
		this.stsq_uom = stsq_uom;
		this.stsq_graph = stsq_graph;
		this.s_stsq_graph = s_stsq_graph;
		this.puzu_class = puzu_class;
		this.puzu_page = puzu_page;
	}

	public String getPuzu_class() {
		return puzu_class;
	}

	public void setPuzu_class(String puzu_class) {
		this.puzu_class = puzu_class;
	}

	public String getPuzu_page() {
		return puzu_page;
	}

	public void setPuzu_page(String puzu_page) {
		this.puzu_page = puzu_page;
	}

	public String getStre_content() {
		return stre_content;
	}

	public void setStre_content(String stre_content) {
		this.stre_content = stre_content;
	}

	public int getStre_state() {
		return stre_state;
	}

	public void setStre_state(int stre_state) {
		this.stre_state = stre_state;
	}

	public int getStsq_state() {
		return stsq_state;
	}

	public void setStsq_state(int stsq_state) {
		this.stsq_state = stsq_state;
	}

	public int getStre_id() {
		return stre_id;
	}
	public void setStre_id(int stre_id) {
		this.stre_id = stre_id;
	}
	public int getStre_log_id() {
		return stre_log_id;
	}
	public void setStre_log_id(int stre_log_id) {
		this.stre_log_id = stre_log_id;
	}
	public int getStre_smonth() {
		return stre_smonth;
	}
	public void setStre_smonth(int stre_smonth) {
		this.stre_smonth = stre_smonth;
	}
	public int getStre_emonth() {
		return stre_emonth;
	}
	public void setStre_emonth(int stre_emonth) {
		this.stre_emonth = stre_emonth;
	}
	
	public String getStre_sum() {
		return stre_sum;
	}
	public void setStre_sum(String stre_sum) {
		this.stre_sum = stre_sum;
	}
	
	public int getStsq_type() {
		return stsq_type;
	}


	public void setStsq_type(int stsq_type) {
		this.stsq_type = stsq_type;
	}


	public String getS_stsq_type() {
		return s_stsq_type;
	}


	public void setS_stsq_type(String s_stsq_type) {
		this.s_stsq_type = s_stsq_type;
	}


	public String getStsq_uom() {
		return stsq_uom;
	}


	public void setStsq_uom(String stsq_uom) {
		this.stsq_uom = stsq_uom;
	}


	public int getStsq_graph() {
		return stsq_graph;
	}


	public void setStsq_graph(int stsq_graph) {
		this.stsq_graph = stsq_graph;
	}


	public String getS_stsq_graph() {
		return s_stsq_graph;
	}


	public void setS_stsq_graph(String s_stsq_graph) {
		this.s_stsq_graph = s_stsq_graph;
	}


	public int getStsq_id() {
		return stsq_id;
	}
	public void setStsq_id(int stsq_id) {
		this.stsq_id = stsq_id;
	}
	public int getStsq_puzu_id() {
		return stsq_puzu_id;
	}
	public void setStsq_puzu_id(int stsq_puzu_id) {
		this.stsq_puzu_id = stsq_puzu_id;
	}
	public String getStsq_content() {
		return stsq_content;
	}
	public void setStsq_content(String stsq_content) {
		this.stsq_content = stsq_content;
	}
	public String getStsq_sql() {
		return stsq_sql;
	}
	public void setStsq_sql(String stsq_sql) {
		this.stsq_sql = stsq_sql;
	}
	public String getAddname() {
		return addname;
	}
	public void setAddname(String addname) {
		this.addname = addname;
	}
	public String getAddtime() {
		return addtime;
	}
	public void setAddtime(String addtime) {
		this.addtime = addtime;
	}

	
}
