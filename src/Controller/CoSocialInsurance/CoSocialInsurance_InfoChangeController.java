package Controller.CoSocialInsurance;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.zkoss.bind.annotation.BindingParam;
import org.zkoss.bind.annotation.Command;
import org.zkoss.bind.annotation.NotifyChange;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zul.ListModelList;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Window;

import bll.CoSocialInsurance.CoSocialInsurance_ListBll;
import bll.CoSocialInsurance.CoSocialInsurance_OperateBll;

import Model.CoShebaoChangeModel;
import Model.CoShebaoModel;
import Model.PubBankModel;
import Model.PubIndustryFirstModel;
import Model.PubIndustryModel;
import Util.UserInfo;

public class CoSocialInsurance_InfoChangeController {

	private CoShebaoModel m = new CoShebaoModel();
	private CoShebaoChangeModel cm = new CoShebaoChangeModel();
	Integer daid = 0;
	private Date ownmonth = new Date();
	private List<PubBankModel> bankList;
	private List<PubIndustryFirstModel> industryFirstList;// 行业一级
	private PubIndustryFirstModel industryFirstModel = new PubIndustryFirstModel();
	private List<PubIndustryModel> industryList = new ListModelList<>();// 行业二级
	private List<PubIndustryModel> industryList1 = new ListModelList<>();// 行业二级

	public CoSocialInsurance_InfoChangeController() {
		try {
			CoSocialInsurance_ListBll bll = new CoSocialInsurance_ListBll();

			daid = Integer.parseInt(Executions.getCurrent().getArg()
					.get("daid").toString());

			setM(bll.getCoShebaoInfo(daid));

			setIndustryFirstList(bll.getIndustryFirstList());
			industryFirstList.add(0, new PubIndustryFirstModel());
			setIndustryList(new ListModelList<>(bll.getIndustryList()));

			setBankList(new ListModelList<>(bll.getBankList()));
			bankList.add(0, new PubBankModel());
		} catch (Exception e) {
			e.printStackTrace();
			Messagebox
					.show("页面加载出错!", "ERROR", Messagebox.OK, Messagebox.ERROR);
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
		//this.cm.setCsbc_industryclass(industryList1.get(0).getName());
		this.cm.setCsbc_industryclass(cm.getCsbc_iclassfirst());
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
			String[] str = new CoSocialInsurance_OperateBll().changeadd(cm,
					m.getCoba_shortname());

			if (str[0].equals("1")) {
				Messagebox.show(str[1], "INFORMATION", Messagebox.OK,
						Messagebox.INFORMATION);
				win.detach();
			} else {
				Messagebox.show(str[1], "ERROR", Messagebox.OK,
						Messagebox.ERROR);
			}
		} catch (Exception e) {
			e.printStackTrace();
			Messagebox.show("提交出错!", "ERROR", Messagebox.OK, Messagebox.ERROR);
		}
	}

	/**
	 * 字段处理
	 * 
	 */
	public void fieldhandle() {
		/*//公司名称
		if (isnull(cm.getCoba_company())) {
			cm.setCoba_company(m.getCoba_company());
		}
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
		// 行业一级
		if (isnull(cm.getCsbc_iclassfirst())) {
			cm.setCsbc_iclassfirst(m.getCosb_iclassfirst());
		}
		// 行业二级
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
		}*/
		cm.setOwnmonth(Integer.parseInt(new SimpleDateFormat("yyyyMM")
				.format(ownmonth)));
		cm.setCsbc_addname(UserInfo.getUsername());
		cm.setCsbc_state(1);
		cm.setCsbc_tzlstate(0);
		cm.setCsbc_laststate(0);
		cm.setCid(m.getCid());
		cm.setCsbc_cosb_id(m.getCosb_id());
		cm.setCsbc_addtype("信息变更");
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

	public void setIndustryFirstList(
			List<PubIndustryFirstModel> industryFirstList) {
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
