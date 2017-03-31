package Model;

import java.math.BigDecimal;
import java.text.DecimalFormat;

public class EmFinanceReportForLocalityModel {
	private final DecimalFormat df = new DecimalFormat("#.00");
	private int gid;
	private String idcard;
	private String name;
	private String hj;
	private EmFinanceSheBaoModel shebaoModel;
	private EmFinanceHouseGjjModel gjjModel;
	private EmFinanceProductModel emProductModel;

	public EmFinanceReportForLocalityModel() {

	}

	public BigDecimal sumTotalReceivable() {
		BigDecimal bd = BigDecimal.ZERO;
		if (shebaoModel != null) {
			bd = bd.add(shebaoModel.getEfsb_receivable());
		}
		if (gjjModel != null) {
			bd = bd.add(gjjModel.getEfhg_receivable());
		}
		if (emProductModel != null) {
			bd = bd.add(emProductModel.getSumReceivable());
		}
		return new BigDecimal(df.format(bd));
	}

	public int getGid() {
		return gid;
	}

	public void setGid(int gid) {
		this.gid = gid;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getHj() {
		return hj;
	}

	public void setHj(String hj) {
		this.hj = hj;
	}

	public String getIdcard() {
		return idcard;
	}

	public void setIdcard(String idcard) {
		this.idcard = idcard;
	}

	public EmFinanceSheBaoModel getShebaoModel() {
		return shebaoModel;
	}

	public void setShebaoModel(EmFinanceSheBaoModel shebaoModel) {
		this.shebaoModel = shebaoModel;
	}

	public EmFinanceHouseGjjModel getGjjModel() {
		return gjjModel;
	}

	public void setGjjModel(EmFinanceHouseGjjModel gjjModel) {
		this.gjjModel = gjjModel;
	}

	public EmFinanceProductModel getEmProductModel() {
		return emProductModel;
	}

	public void setEmProductModel(EmFinanceProductModel emProductModel) {
		this.emProductModel = emProductModel;
	}

}
