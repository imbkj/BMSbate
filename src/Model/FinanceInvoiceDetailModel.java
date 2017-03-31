package Model;

public class FinanceInvoiceDetailModel implements
		Comparable<FinanceInvoiceDetailModel> {
	private String id;
	private String djh;
	private Integer xh;
	private String spmc;
	private String ggxh;
	private String jldw;
	private String je;
	private String slv;
	private String spsm;
	private String ssbm;

	private String km;// 科目
	private String km2;// 科目
	private String type;// 类型
	private String kind;
	private Integer single;
	private Integer sort;
	private boolean allin;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getDjh() {
		return djh;
	}

	public void setDjh(String djh) {
		this.djh = djh;
	}

	public Integer getXh() {
		return xh;
	}

	public void setXh(Integer xh) {
		this.xh = xh;
	}

	public String getSpmc() {
		return spmc;
	}

	public void setSpmc(String spmc) {
		this.spmc = spmc;
	}

	public String getGgxh() {
		return ggxh;
	}

	public void setGgxh(String ggxh) {
		this.ggxh = ggxh;
	}

	public String getJldw() {
		return jldw;
	}

	public void setJldw(String jldw) {
		this.jldw = jldw;
	}

	public String getJe() {
		return je;
	}

	public void setJe(String je) {
		this.je = je;
	}

	public String getSlv() {
		return slv;
	}

	public void setSlv(String slv) {
		this.slv = slv;
	}

	public String getSpsm() {
		return spsm;
	}

	public void setSpsm(String spsm) {
		this.spsm = spsm;
	}

	public String getSsbm() {
		return ssbm;
	}

	public void setSsbm(String ssbm) {
		this.ssbm = ssbm;
	}

	public String getKm() {
		return km;
	}

	public void setKm(String km) {
		this.km = km;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getKind() {
		return kind;
	}

	public void setKind(String kind) {
		this.kind = kind;
	}

	public Integer getSingle() {
		return single;
	}

	public void setSingle(Integer single) {
		this.single = single;
	}

	public Integer getSort() {
		return sort;
	}

	public void setSort(Integer sort) {
		this.sort = sort;
	}

	public boolean isAllin() {
		return allin;
	}

	public void setAllin(boolean allin) {
		this.allin = allin;
	}

	public String getKm2() {
		return km2;
	}

	public void setKm2(String km2) {
		this.km2 = km2;
	}

	@Override
	public int compareTo(FinanceInvoiceDetailModel m) {
		// TODO Auto-generated method stub
		return this.sort.compareTo(m.getSort());
	}

}
