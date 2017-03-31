package Controller.EmSalary;

import java.util.List;

import org.zkoss.bind.annotation.BindingParam;
import org.zkoss.bind.annotation.Command;
import org.zkoss.bind.annotation.NotifyChange;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zul.Combobox;
import org.zkoss.zul.Datebox;
import org.zkoss.zul.Messagebox;

import bll.CoAgency.WtAgency_DisCitySelBll;
import bll.EmSalary.EmSalary_SalaryOperateBll;
import bll.EmSalary.EmSalary_SalarySelBll;
import bll.EmSalary.ItemFormula_OperateBll;
import bll.EmSalary.ItemFormula_SelectBll;
import bll.SalaryPaper.SalaryPaperBll;

import Model.CoFormulaInfoModel;
import Model.EmSalaryInfoModel;
import Model.PubNationalityModel;
import Model.SalaryPaperCoModel;
import Util.CheckString;
import Util.DateStringChange;
import Util.UserInfo;

public class EmSalary_EmSalaryInfoUpdateController {
	private WtAgency_DisCitySelBll cBll = new WtAgency_DisCitySelBll();
	private EmSalary_SalarySelBll esBll = new EmSalary_SalarySelBll();
	private EmSalary_SalaryOperateBll eoBll = new EmSalary_SalaryOperateBll();
	private ItemFormula_SelectBll isBll = new ItemFormula_SelectBll();
	private DateStringChange dsc = new DateStringChange();

	private List<PubNationalityModel> pnList;// 国籍
	private List<String> citylist; // 工资发放地
	private List<String> taxcitylist; // 工资发放地

	private int cid = Integer.parseInt(Executions.getCurrent().getArg()
			.get("cid").toString());
	private int gid = Integer.parseInt(Executions.getCurrent().getArg()
			.get("gid").toString());

	private List<EmSalaryInfoModel> esinList;// 薪酬信息
	private String esin_hpro;
	private String emba_nationality;
	private String esin_taxplace;
	private String esin_salaryplace;

	private String emba_gz_bank = "";
	private String emba_gz_account = "";
	private String emba_writeoff_bank = "";
	private String emba_writeoff_account = "";
	private String emba_gz_email = "";
	private String esin_ba_name = "";
	private boolean ifemail = false;

	private boolean chkgz = false;
	private boolean chkgs = false;
	private boolean chkgsm = false;

	public EmSalary_EmSalaryInfoUpdateController() throws Exception {
		citylist = cBll.getCityName(); // 获取工资申报地
		pnList = esBll.getPubNationalityList("");
		taxcitylist = esBll.getTaxCityAll();// 获取个税申报地

		// 获取薪酬信息
		esinList = esBll.getEmSalaryInfo(cid, gid);
		if (esinList.size() > 0) {
			esin_hpro = esinList.get(0).getEsin_hpro();
			emba_nationality = esinList.get(0).getEmba_nationality();
			esin_taxplace = esinList.get(0).getEsin_taxplace();
			esin_salaryplace = esinList.get(0).getEsin_salaryplace();

			emba_gz_bank = esinList.get(0).getEmba_gz_bank();
			emba_gz_account = esinList.get(0).getEmba_gz_account();
			emba_writeoff_bank = esinList.get(0).getEmba_writeoff_bank();
			emba_writeoff_account = esinList.get(0).getEmba_writeoff_account();
			emba_gz_email = esinList.get(0).getEmba_gz_email();
			if (emba_gz_email == null || "".equals(emba_gz_email)
					|| "NULL".equals(emba_gz_email)) {
				emba_gz_email = esinList.get(0).getEmba_email();
			}
			if (esinList.get(0).getEsda_ba_name() != null
					&& !"".equals(esinList.get(0).getEsda_ba_name())
					&& !"NULL".equals(esinList.get(0).getEsda_ba_name())) {
				esin_ba_name = esinList.get(0).getEsda_ba_name();
			} else {
				esin_ba_name = esinList.get(0).getName();
			}
		}

		// 判断报价单信息是否含工资个税项目
		Integer[] chkgzgs = esBll.getEmSalaryCoGlist(cid, gid);
		if (chkgzgs[0] > 0) {// 工资
			chkgz = true;
		}
		if (chkgzgs[1] > 0) {// 个税
			chkgs = true;
		}

		// 判断是否需要发送email工资单
		SalaryPaperBll spb = new SalaryPaperBll();
		SalaryPaperCoModel cosalarysetm = new SalaryPaperCoModel();
		try {
			cosalarysetm = spb.getmodellist(cid).get(0);
			if ("E-mail工资单".equals(cosalarysetm.getCoss_payrollpapertype())) {
				ifemail = true;
			}
		} catch (Exception e) {
			// TODO: handle exception
		}

	}

	@Command("submit")
	public void submit(@BindingParam("hpro") Combobox hpro,
			@BindingParam("nationality") Combobox nationality,
			@BindingParam("taxplace") Combobox taxplace,
			@BindingParam("salaryplace") Combobox salaryplace,
			@BindingParam("n_ownmonth") Datebox n_ownmonth) {

		// 判断是否需要发送email工资单
		boolean chkifemail = true;
		if (ifemail) {
			if (emba_gz_email == null || "".equals(emba_gz_email)) {
				chkifemail = false;
			}
		}

		String n_ownmonth_s = "";
		try {
			if (n_ownmonth.getValue() != null) {
				n_ownmonth_s = dsc.DatetoSting(n_ownmonth.getValue(), "yyyyMM"); // 月份
			}
		} catch (Exception e) {
			n_ownmonth_s = "";
		}

		// 检查银行账号是否存在非数字
		int chk_acc = 0;
		if (!"".equals(emba_gz_account) && emba_gz_account != null) {
			if (CheckString.isNum(emba_gz_account) == false) {
				chk_acc = 1;
			}
		}
		if (!"".equals(emba_writeoff_account) && emba_writeoff_account != null) {
			if (CheckString.isNum(emba_writeoff_account) == false) {
				chk_acc = 2;
			}
		}

		boolean chksalaryplace = false;
		if (chkgz) {
			if (salaryplace.getSelectedItem() != null) {
				chksalaryplace = true;
			}
		} else {
			chksalaryplace = true;
		}

		boolean chktaxplace = false;
		if (chkgs) {
			if (taxplace.getSelectedItem() != null) {
				chktaxplace = true;
			}
		} else {
			chktaxplace = true;
		}

		//判断生效月份是否填写
		boolean chk_n_ownmonth_s=true;
		if (chkgsm && "".equals(n_ownmonth_s)) {
			chk_n_ownmonth_s=false;
		}
		
		if (hpro.getSelectedItem() != null
				&& nationality.getSelectedItem() != null && chkifemail
				&& chksalaryplace && chktaxplace && chk_acc == 0
				&& chk_n_ownmonth_s) {
			// 判断该公司有几套算法
			int cfin_id = 0;
			List<CoFormulaInfoModel> cfinList;
			cfinList = isBll.getFormulaInfoByCid(cid);
			if (cfinList.size() == 1) {// 判断是否只有一套算法
				cfin_id = cfinList.get(0).getCfin_id();
			}

			EmSalaryInfoModel m = new EmSalaryInfoModel();
			m.setCid(cid);
			m.setGid(gid);
			m.setCfin_id(cfin_id);
			m.setEmba_nationality(nationality.getSelectedItem().getLabel());
			if (taxplace.getSelectedItem() != null) {
				m.setEsin_taxplace(taxplace.getSelectedItem().getLabel());
			}
			if (salaryplace.getSelectedItem() != null) {
				m.setEsin_salaryplace(salaryplace.getSelectedItem().getLabel());
			}

			m.setEsin_hpro(hpro.getSelectedItem().getLabel());
			m.setEsin_addname(UserInfo.getUsername());

			m.setEmba_gz_bank(emba_gz_bank);
			m.setEmba_gz_account(emba_gz_account);
			m.setEmba_writeoff_bank(emba_writeoff_bank);
			m.setEmba_writeoff_account(emba_writeoff_account);
			m.setEmba_gz_email(emba_gz_email);
			m.setEsda_ba_name(esin_ba_name);
			if (chk_n_ownmonth_s && chkgsm) {
				m.setEsin_nexttaxplace_smonth(Integer.parseInt(n_ownmonth_s));
			}else {
				m.setEsin_nexttaxplace_smonth(0);
			}
			

			String[] message = eoBll.addEmSalaryInfo(m);
			if (message[0].equals("1")) {

				if (cfinList.size() > 1 && cfin_id == 0) {// 触发任务单
					ItemFormula_OperateBll ioBll = new ItemFormula_OperateBll();
					ioBll.addEmSalaryInfoWF(m);
				}

				// 弹出提示
				Messagebox.show(message[1], "操作提示", Messagebox.OK,
						Messagebox.INFORMATION);
			} else {
				// 弹出提示
				Messagebox.show(message[1], "操作提示", Messagebox.OK,
						Messagebox.ERROR);
			}

		} else {
			// 弹出提示
			if (hpro.getSelectedItem() == null
					|| nationality.getSelectedItem() == null
					|| chktaxplace == false || chksalaryplace == false
					|| chkifemail == false || (chkgsm=true && "".equals(n_ownmonth_s))) {
				Messagebox.show("请填写所有必填项！", "操作提示", Messagebox.OK,
						Messagebox.ERROR);
			} else if (chk_acc == 1) {
				Messagebox.show("工资银行账号格式不正确！", "操作提示", Messagebox.OK,
						Messagebox.ERROR);
			} else if (chk_acc == 2) {
				Messagebox.show("报销银行账号格式不正确！", "操作提示", Messagebox.OK,
						Messagebox.ERROR);
			} else if (chkifemail == false) {
				Messagebox.show("请录入工资单Email！", "操作提示", Messagebox.OK,
						Messagebox.ERROR);
			}
		}

	}

	// 判断生效月份是否必填
	@Command("chkGsM")
	@NotifyChange("chkgsm")
	public void chkGsM(@BindingParam("taxplace") Combobox taxplace) {

		if (esin_taxplace != null && !"".equals(esin_taxplace)) {//判断个税申报地是否为空

			if (taxplace.getSelectedItem() != null) {
				if (!esin_taxplace.equals((taxplace.getSelectedItem().getLabel()))) {//判断是否变更了个税申报地
					chkgsm=true;
				}else {
					chkgsm = false;
				}
			}

		} else {
			chkgsm = false;
		}
	}

	public List<String> getCitylist() {
		return citylist;
	}

	public void setCitylist(List<String> citylist) {
		this.citylist = citylist;
	}

	public List<PubNationalityModel> getPnList() {
		return pnList;
	}

	public void setPnList(List<PubNationalityModel> pnList) {
		this.pnList = pnList;
	}

	public String getEsin_hpro() {
		return esin_hpro;
	}

	public void setEsin_hpro(String esin_hpro) {
		this.esin_hpro = esin_hpro;
	}

	public String getEmba_nationality() {
		return emba_nationality;
	}

	public void setEmba_nationality(String emba_nationality) {
		this.emba_nationality = emba_nationality;
	}

	public String getEsin_taxplace() {
		return esin_taxplace;
	}

	public void setEsin_taxplace(String esin_taxplace) {
		this.esin_taxplace = esin_taxplace;
	}

	public String getEsin_salaryplace() {
		return esin_salaryplace;
	}

	public void setEsin_salaryplace(String esin_salaryplace) {
		this.esin_salaryplace = esin_salaryplace;
	}

	public String getEmba_gz_bank() {
		return emba_gz_bank;
	}

	public void setEmba_gz_bank(String emba_gz_bank) {
		this.emba_gz_bank = emba_gz_bank;
	}

	public String getEmba_gz_account() {
		return emba_gz_account;
	}

	public void setEmba_gz_account(String emba_gz_account) {
		this.emba_gz_account = emba_gz_account;
	}

	public String getEmba_writeoff_bank() {
		return emba_writeoff_bank;
	}

	public void setEmba_writeoff_bank(String emba_writeoff_bank) {
		this.emba_writeoff_bank = emba_writeoff_bank;
	}

	public String getEmba_writeoff_account() {
		return emba_writeoff_account;
	}

	public void setEmba_writeoff_account(String emba_writeoff_account) {
		this.emba_writeoff_account = emba_writeoff_account;
	}

	public String getEmba_gz_email() {
		return emba_gz_email;
	}

	public void setEmba_gz_email(String emba_gz_email) {
		this.emba_gz_email = emba_gz_email;
	}

	public boolean isIfemail() {
		return ifemail;
	}

	public void setIfemail(boolean ifemail) {
		this.ifemail = ifemail;
	}

	public boolean isChkgz() {
		return chkgz;
	}

	public void setChkgz(boolean chkgz) {
		this.chkgz = chkgz;
	}

	public boolean isChkgs() {
		return chkgs;
	}

	public void setChkgs(boolean chkgs) {
		this.chkgs = chkgs;
	}

	public String getEsin_ba_name() {
		return esin_ba_name;
	}

	public void setEsin_ba_name(String esin_ba_name) {
		this.esin_ba_name = esin_ba_name;
	}

	public List<String> getTaxcitylist() {
		return taxcitylist;
	}

	public void setTaxcitylist(List<String> taxcitylist) {
		this.taxcitylist = taxcitylist;
	}

	public boolean isChkgsm() {
		return chkgsm;
	}

	public void setChkgsm(boolean chkgsm) {
		this.chkgsm = chkgsm;
	}

}
