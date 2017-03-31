package Controller.CoHousingFund;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;

import org.zkoss.bind.BindUtils;
import org.zkoss.bind.annotation.BindingParam;
import org.zkoss.bind.annotation.Command;
import org.zkoss.bind.annotation.NotifyChange;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zul.Combobox;
import org.zkoss.zul.ListModelList;
import org.zkoss.zul.Messagebox;

import Model.CoHousingFundModel;
import Model.CoHousingFundPwdModel;
import Model.CoHousingFundZbInfoModel;
import Model.CoHousingFundZbModel;
import Model.PubAreaSZModel;
import Model.PubCoEcoclassModel;
import Model.PubCoNatureModel;
import Model.PubGjBankModel;
import Model.PubIndustryModel;
import Model.PubMemberShipModel;
import Util.StringUtil;
import bll.CoHousingFund.CoHousingFund_ListBll;

public class CoHouseInfoModController {
	private CoHousingFundModel cfm = new CoHousingFundModel();
	private List<CoHousingFundZbModel> zbList = new ListModelList<>();
	private List<CoHousingFundPwdModel> pwdList = new ListModelList<>();
	private List<Integer> tslist = new ListModelList<>();
	private List<String> cplist = new ListModelList<>();
	private List<PubGjBankModel> jclist = new ListModelList<>();
	private List<PubIndustryModel> pilist = new ListModelList<>();
	private List<PubAreaSZModel> szlist = new ListModelList<>();
	private List<PubMemberShipModel> mslist = new ListModelList<>();
	private List<PubCoNatureModel> cnlist = new ListModelList<>();
	private List<PubCoEcoclassModel> ceclist = new ListModelList<>();
	private List<CoHousingFundZbInfoModel> zilist = new ListModelList<>();

	private CoHousingFund_ListBll bll = new CoHousingFund_ListBll();
	private SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

	public CoHouseInfoModController() throws ParseException {
		Integer id = 0;
		if (Executions.getCurrent().getArg().get("id") != null) {
			id = Integer.parseInt(Executions.getCurrent().getArg().get("id")
					.toString());
			cfm = bll.gethouseInfo(id);
			if (cfm.getCohf_createtime() != null
					&& !cfm.getCohf_createtime().equals("")) {
				cfm.setCohf_createtime2(sdf.parse(cfm.getCohf_createtime()));
			}

		}
		// 初始化下拉列表
		for (int i = 1; i < 32; i++) {
			tslist.add(i);
		}
		for (Double i = 0.05; i < 0.21; i = i + 0.01) {
			cplist.add(String.format("%.2f", i));
		}
		jclist = bll.getGjjBankList();
		pilist = bll.pubIndustry();
		szlist = bll.getAreaSzList();
		mslist = bll.getMemberShipList();
		cnlist = bll.getCoNatureList();
		ceclist = bll.getCoEcoclassList();

		// 专办员列表
		zbList = bll.getZbList(id);
		zilist = bll.zbiList();

		// 密钥列表
		pwdList = bll.getPwdList(id);

	}

	/**
	 * @Title: updateNumber
	 * @Description: TODO(级联更新专办员号)
	 * @param cbName
	 * @param model
	 * @return void 返回类型
	 * @throws
	 */
	@Command
	public void updateNumber(@BindingParam("a") Combobox cbName,
			@BindingParam("b") CoHousingFundZbModel model) {
		if (cbName.getSelectedIndex() >= 0) {
			CoHousingFundZbInfoModel m = cbName.getSelectedItem().getValue();
			model.setCfzb_number(m.getCfzi_idcard());
			BindUtils.postNotifyChange(null, null, model, "cfzb_number");
		}else {
			if (cbName.getValue()==null || cbName.getValue().equals("")) {
				model.setCfzb_number(null);
				BindUtils.postNotifyChange(null, null, model, "cfzb_number");
			}
		}
	}

	@Command
	public void updateNum(@BindingParam("a") Combobox cbName,
			@BindingParam("b") CoHousingFundPwdModel model) {

		if (cbName.getSelectedIndex() >= 0) {
			CoHousingFundZbModel m = cbName.getSelectedItem().getValue();
			model.setCfpw_zbnumber(m.getCfzb_number());
			BindUtils.postNotifyChange(null, null, model, "cfpw_zbnumber");
		}
	}

	/**
	 * @Title: addzb
	 * @Description: TODO(添加专办员)
	 * @return void 返回类型
	 * @throws
	 */
	@Command
	@NotifyChange("zbList")
	public void addzb() {
		CoHousingFundZbModel m = new CoHousingFundZbModel();
		m.setCfzb_cohf_id(cfm.getCohf_id());
		zbList.add(m);
	}
	
	@Command
	@NotifyChange("zbList")
	public void delZb(@BindingParam("a") CoHousingFundZbModel m){
		if (m.getCfzb_id()==null) {
			zbList.remove(m);
		}else {
			Messagebox.show("不允许直接删除有效数据.", "ERROR", Messagebox.OK,
					Messagebox.ERROR);
		}
		
	}
	
	@Command
	@NotifyChange("pwdList")
	public void addPwd() {
		CoHousingFundPwdModel m = new CoHousingFundPwdModel();
		m.setCfpw_cohf_id(cfm.getCohf_id());
		pwdList.add(m);
	}
	
	@Command
	@NotifyChange("pwdList")
	public void delPwd(@BindingParam("a") CoHousingFundPwdModel m){
		if (m.getCfpw_id()==null) {
			pwdList.remove(m);
		}else {
			Messagebox.show("不允许直接删除有效数据.", "ERROR", Messagebox.OK,
					Messagebox.ERROR);
		}
		
	}

	public CoHousingFundModel getCfm() {
		return cfm;
	}

	public void setCfm(CoHousingFundModel cfm) {
		this.cfm = cfm;
	}

	public List<CoHousingFundZbModel> getZbList() {
		return zbList;
	}

	public void setZbList(List<CoHousingFundZbModel> zbList) {
		this.zbList = zbList;
	}

	public List<CoHousingFundPwdModel> getPwdList() {
		return pwdList;
	}

	public void setPwdList(List<CoHousingFundPwdModel> pwdList) {
		this.pwdList = pwdList;
	}

	public List<Integer> getTslist() {
		return tslist;
	}

	public void setTslist(List<Integer> tslist) {
		this.tslist = tslist;
	}

	public List<String> getCplist() {
		return cplist;
	}

	public void setCplist(List<String> cplist) {
		this.cplist = cplist;
	}

	public List<PubGjBankModel> getJclist() {
		return jclist;
	}

	public void setJclist(List<PubGjBankModel> jclist) {
		this.jclist = jclist;
	}

	public List<PubIndustryModel> getPilist() {
		return pilist;
	}

	public void setPilist(List<PubIndustryModel> pilist) {
		this.pilist = pilist;
	}

	public List<PubAreaSZModel> getSzlist() {
		return szlist;
	}

	public void setSzlist(List<PubAreaSZModel> szlist) {
		this.szlist = szlist;
	}

	public List<PubMemberShipModel> getMslist() {
		return mslist;
	}

	public void setMslist(List<PubMemberShipModel> mslist) {
		this.mslist = mslist;
	}

	public List<PubCoNatureModel> getCnlist() {
		return cnlist;
	}

	public void setCnlist(List<PubCoNatureModel> cnlist) {
		this.cnlist = cnlist;
	}

	public List<PubCoEcoclassModel> getCeclist() {
		return ceclist;
	}

	public void setCeclist(List<PubCoEcoclassModel> ceclist) {
		this.ceclist = ceclist;
	}

	public List<CoHousingFundZbInfoModel> getZilist() {
		return zilist;
	}

	public void setZilist(List<CoHousingFundZbInfoModel> zilist) {
		this.zilist = zilist;
	}

	

}
