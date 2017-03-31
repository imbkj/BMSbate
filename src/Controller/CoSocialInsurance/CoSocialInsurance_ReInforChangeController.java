package Controller.CoSocialInsurance;

import java.util.Date;
import java.util.List;

import org.zkoss.bind.annotation.BindingParam;
import org.zkoss.bind.annotation.Command;
import org.zkoss.bind.annotation.NotifyChange;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zul.ListModelList;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Window;

import Model.CoShebaoChangeModel;
import Model.CoShebaoModel;
import Model.PubBankModel;
import Model.PubIndustryFirstModel;
import Model.PubIndustryModel;
import Util.UserInfo;
import bll.CoSocialInsurance.CoSocialInsurance_ListBll;
import bll.CoSocialInsurance.CoSocialInsurance_OperateBll;

public class CoSocialInsurance_ReInforChangeController {
	private CoShebaoModel m = new CoShebaoModel();
	private CoShebaoChangeModel cm = new CoShebaoChangeModel();
	Integer daid = 0;
	private Date ownmonth = new Date();
	private List<PubBankModel> bankList;
	private List<PubIndustryFirstModel> industryFirstList;// 行业一级
	private PubIndustryFirstModel industryFirstModel = new PubIndustryFirstModel();
	private List<PubIndustryModel> industryList = new ListModelList<>();// 行业二级
	private List<PubIndustryModel> industryList1 = new ListModelList<>();// 行业二级

	public CoSocialInsurance_ReInforChangeController() {
		try {
			CoSocialInsurance_ListBll bll = new CoSocialInsurance_ListBll();

			daid = Integer.parseInt(Executions.getCurrent().getArg()
					.get("daid").toString());

			setCm(bll.getCoShebaoChangeInfo(daid));
			setM(bll.getCoShebaoInfo(cm.getCsbc_cosb_id()));
			
			setIndustryFirstList(bll.getIndustryFirstList());
			industryFirstList.add(0, new PubIndustryFirstModel());
			setIndustryList(new ListModelList<>(bll.getIndustryList()));
			
			setBankList(new ListModelList<>(bll.getBankList()));
			bankList.add(0, new PubBankModel());
			fieldinit();
		} catch (Exception e) {
			e.printStackTrace();
			Messagebox
					.show("页面加载出错!", "ERROR", Messagebox.OK, Messagebox.ERROR);
		}
	}

	public void fieldinit() {
		m.setCosb_regadd(m.getCosb_regadd() == null ? "" : m.getCosb_regadd());
		m.setCosb_regid(m.getCosb_regid() == null ? "" : m.getCosb_regid());
		m.setCosb_ecoclass(m.getCosb_ecoclass() == null ? "" : m
				.getCosb_ecoclass());
		m.setCosb_industryclass(m.getCosb_industryclass() == null ? "" : m
				.getCosb_industryclass());
		m.setCosb_comid(m.getCosb_comid() == null ? "" : m.getCosb_comid());
		m.setCosb_coridcard(m.getCosb_coridcard() == null ? "" : m
				.getCosb_coridcard());
		m.setCosb_corname(m.getCosb_corname() == null ? "" : m
				.getCosb_corname());
		m.setCosb_bankacctid(m.getCosb_bankacctid() == null ? "" : m
				.getCosb_bankacctid());
		m.setCosb_bankcode(m.getCosb_bankcode() == null ? "" : m
				.getCosb_bankcode());
		m.setCosb_bankname(m.getCosb_bankname() == null ? "" : m
				.getCosb_bankname());

		cm.setCsbc_regadd(cm.getCsbc_regadd() == null ? "" : cm
				.getCsbc_regadd());
		cm.setCsbc_regid(cm.getCsbc_regid() == null ? "" : cm.getCsbc_regid());
		cm.setCsbc_ecoclass(cm.getCsbc_ecoclass() == null ? "" : cm
				.getCsbc_ecoclass());
		cm.setCsbc_industryclass(cm.getCsbc_industryclass() == null ? "" : cm
				.getCsbc_industryclass());
		cm.setCsbc_iclassfirst(cm.getCsbc_iclassfirst() == null ? "" : cm
				.getCsbc_iclassfirst());
		cm.setCsbc_comid(cm.getCsbc_comid() == null ? "" : cm.getCsbc_comid());
		cm.setCsbc_coridcard(cm.getCsbc_coridcard() == null ? "" : cm
				.getCsbc_coridcard());
		cm.setCsbc_corname(cm.getCsbc_corname() == null ? "" : cm
				.getCsbc_corname());
		cm.setCsbc_bankacctid(cm.getCsbc_bankacctid() == null ? "" : cm
				.getCsbc_bankacctid());
		cm.setCsbc_bankcode(cm.getCsbc_bankcode() == null ? "" : cm
				.getCsbc_bankcode());
		cm.setCsbc_bankname(cm.getCsbc_bankname() == null ? "" : cm
				.getCsbc_bankname());

		if (m.getCosb_regadd().equals(cm.getCsbc_regadd())) {
			cm.setCsbc_regadd(null);
		}
		if (m.getCosb_regid().equals(cm.getCsbc_regid())) {
			cm.setCsbc_regid(null);
		}
		if (m.getCosb_ecoclass().equals(cm.getCsbc_ecoclass())) {
			cm.setCsbc_ecoclass(null);
		}
		if (m.getCosb_industryclass().equals(cm.getCsbc_industryclass())) {
			cm.setCsbc_industryclass(null);
		}
		if (m.getCosb_iclassfirst().equals(cm.getCsbc_iclassfirst())) {
			cm.setCsbc_iclassfirst(null);
		}
		if (m.getCosb_comid().equals(cm.getCsbc_comid())) {
			cm.setCsbc_comid(null);
		}
		if (m.getCosb_corname().equals(cm.getCsbc_corname())) {
			cm.setCsbc_corname(null);
		}
		if (m.getCosb_coridcard().equals(cm.getCsbc_coridcard())) {
			cm.setCsbc_coridcard(null);
		}
		if (m.getCosb_bankacctid().equals(cm.getCsbc_bankacctid())) {
			cm.setCsbc_bankacctid(null);
		}
		if (m.getCosb_bankcode().equals(cm.getCsbc_bankcode())) {
			cm.setCsbc_bankcode(null);
		}
		if (m.getCosb_bankname().equals(cm.getCsbc_bankname())) {
			cm.setCsbc_bankname(null);
		}
	}

	/**
	 * 选择行业一级
	 */
	@Command("industryFirstChange")
	@NotifyChange({ "industryList1", "cm" })
	public void industryFirstChange() {
		industryList1.clear();
		if (industryFirstModel.getId() != null) {
			for (PubIndustryModel m : industryList) {
				if (industryFirstModel.getId().toString()
						.equals(m.getF_id().toString())) {
					industryList1.add(m);
				}
			}
		}
		if (industryList1.size() == 0) {
			industryList1.add(new PubIndustryModel());
		}
		this.cm.setCsbc_industryclass(industryList1.get(0).getName());
	}
	
	/**
	 * 提交
	 * 
	 * @param win
	 */
	@Command("submit")
	public void submit(@BindingParam("win") Window win) {
		try {
			fieldhandle();
		} catch (Exception e) {
			e.printStackTrace();
			Messagebox
					.show("数据处理出错!", "ERROR", Messagebox.OK, Messagebox.ERROR);
		}

		try {
			String[] str = new CoSocialInsurance_OperateBll().resubmit(cm);

			if (str[0].equals("1")) {
				Messagebox.show(str[1], "INFORMATION", Messagebox.OK,
						Messagebox.INFORMATION);
				win.detach();
			} else {
				Messagebox.show(str[1], "ERROR", Messagebox.OK,
						Messagebox.ERROR);
			}
		} catch (Exception e) {
			Messagebox.show("提交出错!", "ERROR", Messagebox.OK, Messagebox.ERROR);
			e.printStackTrace();
		}
	}

	/**
	 * 字段处理
	 * 
	 */
	public void fieldhandle() {
		// 企业注册地址
		if (isnull(cm.getCsbc_regadd())) {
			cm.setCsbc_regadd(m.getCosb_regadd());
		}
		// 工商注册号
		if (isnull(cm.getCsbc_regid())) {
			cm.setCsbc_regid(m.getCosb_regid());
		}
		// 经济类型
		if (isnull(cm.getCsbc_ecoclass())) {
			cm.setCsbc_ecoclass(m.getCosb_ecoclass());
		}
		// 行业类型
		if (isnull(cm.getCsbc_industryclass())) {
			cm.setCsbc_industryclass(m.getCosb_industryclass());
		}
		// 组织机构代码
		if (isnull(cm.getCsbc_comid())) {
			cm.setCsbc_comid(m.getCosb_comid());
		}
		// 法人姓名
		if (isnull(cm.getCsbc_corname())) {
			cm.setCsbc_corname(m.getCosb_corname());
		}
		// 法人身份证号
		if (isnull(cm.getCsbc_coridcard())) {
			cm.setCsbc_coridcard(m.getCosb_coridcard());
		}
		// 银行编码
		if (isnull(cm.getCsbc_bankcode())) {
			cm.setCsbc_bankcode(m.getCosb_bankcode());
		}
		// 银行全称(营业部全称)
		if (isnull(cm.getCsbc_bankname())) {
			cm.setCsbc_bankname(m.getCosb_bankname());
		}
		// 银行账号
		if (isnull(cm.getCsbc_bankacctid())) {
			cm.setCsbc_bankacctid(m.getCosb_bankacctid());
		}
		cm.setCsbc_addname(UserInfo.getUsername());
		cm.setCsbc_state(1);
	}

	public boolean isnull(String str) {
		if (str != null && !str.isEmpty()) {
			return false;
		} else {
			return true;
		}
	}

	public CoShebaoModel getM() {
		return m;
	}

	public void setM(CoShebaoModel m) {
		this.m = m;
	}

	public CoShebaoChangeModel getCm() {
		return cm;
	}

	public void setCm(CoShebaoChangeModel cm) {
		this.cm = cm;
	}

	public Date getOwnmonth() {
		return ownmonth;
	}

	public void setOwnmonth(Date ownmonth) {
		this.ownmonth = ownmonth;
	}

	public List<PubBankModel> getBankList() {
		return bankList;
	}

	public void setBankList(List<PubBankModel> bankList) {
		this.bankList = bankList;
	}

	public List<PubIndustryModel> getIndustryList() {
		return industryList;
	}

	public void setIndustryList(List<PubIndustryModel> industryList) {
		this.industryList = industryList;
	}

	public List<PubIndustryFirstModel> getIndustryFirstList() {
		return industryFirstList;
	}

	public void setIndustryFirstList(List<PubIndustryFirstModel> industryFirstList) {
		this.industryFirstList = industryFirstList;
	}

	public PubIndustryFirstModel getIndustryFirstModel() {
		return industryFirstModel;
	}

	public void setIndustryFirstModel(PubIndustryFirstModel industryFirstModel) {
		this.industryFirstModel = industryFirstModel;
	}

	public List<PubIndustryModel> getIndustryList1() {
		return industryList1;
	}

	public void setIndustryList1(List<PubIndustryModel> industryList1) {
		this.industryList1 = industryList1;
	}
	
}
