package Controller.CoFinanceManage;

import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.zkoss.bind.annotation.BindingParam;
import org.zkoss.bind.annotation.Command;
import org.zkoss.bind.annotation.NotifyChange;
import org.zkoss.zk.ui.Executions;

import Model.CoFinanceCollectModel;

public class Cfma_AddcollectController {

	private final CoFinanceCollectModel m = (CoFinanceCollectModel) Executions
			.getCurrent().getArg().get("m");

	private Date ownmonth;
	private double serFee = 0;
	private double sal = 0;
	private double shebao = 0;
	private double gjj = 0;
	private double businProf = 0;
	private double oMove = 0;
	private double bodyCheck = 0;
	private double finanService = 0;
	private double activity = 0;
	private double fileManage = 0;
	private double book = 0;
	private double hRestore = 0;
	private double lass = 0;
	private double residence = 0;
	private double recruit = 0;
	private double businessService = 0;
	private double deformity = 0;
	private double other = 0;
	private double total = 0;
	private String remark;
	private String username;
	private double[] fee = { 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
			0, 0 };

	public Cfma_AddcollectController() {

		try {
			ownmonth = new SimpleDateFormat().parse(new SimpleDateFormat()
					.format(System.currentTimeMillis()));
		} catch (ParseException e) {
			e.printStackTrace();
		}
		username = Executions.getCurrent().getDesktop().getSession()
				.getAttribute("username").toString();
	}

	@Command
	public void submit() {

	}

	@Command
	@NotifyChange("total")
	public void totalFee(@BindingParam("sign") String sign) {
		if (sign != null && sign.equals("ser")) {

			total = 0;
			fee[0] = serFee;
			for (int i = 0; i < fee.length; i++) {
				total = total + fee[i];
			}
		} else if (sign != null && sign.equals("tax")) {
			total = 0;
			fee[1] = sal;
			for (int i = 0; i < fee.length; i++) {
				total = total + fee[i];
			}
		} else if (sign != null && sign.equals("sb")) {
			total = 0;
			fee[2] = shebao;
			for (int i = 0; i < fee.length; i++) {
				total = total + fee[i];
			}
		} else if (sign != null && sign.equals("hgjj")) {
			total = 0;
			fee[3] = gjj;
			for (int i = 0; i < fee.length; i++) {
				total = total + fee[i];
			}
		} else if (sign != null && sign.equals("businessp")) {
			total = 0;
			fee[4] = businProf;
			for (int i = 0; i < fee.length; i++) {
				total = total + fee[i];
			}
		} else if (sign != null && sign.equals("om")) {
			total = 0;
			fee[5] = oMove;
			for (int i = 0; i < fee.length; i++) {
				total = total + fee[i];
			}
		} else if (sign != null && sign.equals("bcheck")) {
			total = 0;
			fee[6] = bodyCheck;
			for (int i = 0; i < fee.length; i++) {
				total = total + fee[i];
			}
		} else if (sign != null && sign.equals("finanseri")) {
			total = 0;
			fee[7] = finanService;
			for (int i = 0; i < fee.length; i++) {
				total = total + fee[i];
			}
		} else if (sign != null && sign.equals("activi")) {
			total = 0;
			fee[8] = activity;
			for (int i = 0; i < fee.length; i++) {
				total = total + fee[i];
			}
		} else if (sign != null && sign.equals("file")) {
			total = 0;
			fee[9] = fileManage;
			for (int i = 0; i < fee.length; i++) {
				total = total + fee[i];
			}
		} else if (sign != null && sign.equals("books")) {
			total = 0;
			fee[10] = book;
			for (int i = 0; i < fee.length; i++) {
				total = total + fee[i];
			}
		} else if (sign != null && sign.equals("housere'")) {
			total = 0;
			fee[11] = hRestore;
			for (int i = 0; i < fee.length; i++) {
				total = total + fee[i];
			}
		} else if (sign != null && sign.equals("lasc")) {
			total = 0;
			fee[12] = lass;
			for (int i = 0; i < fee.length; i++) {
				total = total + fee[i];
			}
		} else if (sign != null && sign.equals("residen")) {
			total = 0;
			fee[13] = residence;
			for (int i = 0; i < fee.length; i++) {
				total = total + fee[i];
			}
		} else if (sign != null && sign.equals("recru")) {
			total = 0;
			fee[14] = recruit;
			for (int i = 0; i < fee.length; i++) {
				total = total + fee[i];
			}
		} else if (sign != null && sign.equals("business")) {
			total = 0;
			fee[15] = businessService;
			for (int i = 0; i < fee.length; i++) {
				total = total + fee[i];
			}
		} else if (sign != null && sign.equals("defor")) {
			total = 0;
			fee[16] = deformity;
			for (int i = 0; i < fee.length; i++) {
				total = total + fee[i];
			}
		} else if (sign != null && sign.equals("others")) {
			total = 0;
			fee[17] = other;
			for (int i = 0; i < fee.length; i++) {
				total = total + fee[i];
			}
		}
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public CoFinanceCollectModel getM() {
		return m;
	}

	public Date getOwnmonth() {
		return ownmonth;
	}

	public void setOwnmonth(Date ownmonth) {
		this.ownmonth = ownmonth;
	}

	public double getSerFee() {
		return serFee;
	}

	public void setSerFee(double serFee) {
		this.serFee = serFee;
	}

	public double getSal() {
		return sal;
	}

	public void setSal(double sal) {
		this.sal = sal;
	}

	public double getShebao() {
		return shebao;
	}

	public void setShebao(double shebao) {
		this.shebao = shebao;
	}

	public double getGjj() {
		return gjj;
	}

	public void setGjj(double gjj) {
		this.gjj = gjj;
	}

	public double getBusinProf() {
		return businProf;
	}

	public void setBusinProf(double businProf) {
		this.businProf = businProf;
	}

	public double getoMove() {
		return oMove;
	}

	public void setoMove(double oMove) {
		this.oMove = oMove;
	}

	public double getBodyCheck() {
		return bodyCheck;
	}

	public void setBodyCheck(double bodyCheck) {
		this.bodyCheck = bodyCheck;
	}

	public double getFinanService() {
		return finanService;
	}

	public void setFinanService(double finanService) {
		this.finanService = finanService;
	}

	public double getActivity() {
		return activity;
	}

	public void setActivity(double activity) {
		this.activity = activity;
	}

	public double getFileManage() {
		return fileManage;
	}

	public void setFileManage(double fileManage) {
		this.fileManage = fileManage;
	}

	public double getBook() {
		return book;
	}

	public void setBook(double book) {
		this.book = book;
	}

	public double gethRestore() {
		return hRestore;
	}

	public void sethRestore(double hRestore) {
		this.hRestore = hRestore;
	}

	public double getLass() {
		return lass;
	}

	public void setLass(double lass) {
		this.lass = lass;
	}

	public double getResidence() {
		return residence;
	}

	public void setResidence(double residence) {
		this.residence = residence;
	}

	public double getRecruit() {
		return recruit;
	}

	public void setRecruit(double recruit) {
		this.recruit = recruit;
	}

	public double getBusinessService() {
		return businessService;
	}

	public void setBusinessService(double businessService) {
		this.businessService = businessService;
	}

	public double getDeformity() {
		return deformity;
	}

	public void setDeformity(double deformity) {
		this.deformity = deformity;
	}

	public double getOther() {
		return other;
	}

	public void setOther(double other) {
		this.other = other;
	}

	public double getTotal() {
		return total;
	}

	public void setTotal(double total) {
		this.total = total;
	}

}
