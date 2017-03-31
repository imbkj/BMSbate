package Controller.CoHousingFund;

import java.util.List;

import org.zkoss.bind.annotation.BindingParam;
import org.zkoss.bind.annotation.Command;
import org.zkoss.bind.annotation.NotifyChange;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.event.EventListener;
import org.zkoss.zul.Grid;
import org.zkoss.zul.ListModelList;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Messagebox.ClickEvent;
import org.zkoss.zul.Window;

import Model.CoHousingFundChangeModel;
import Model.PubAreaSZModel;
import Model.PubCoEcoclassModel;
import Model.PubCoNatureModel;
import Model.PubGjBankModel;
import Model.PubIdcardTypeModel;
import Model.PubIndustryModel;
import Model.PubMemberShipModel;
import Model.PubTsbankModel;
import Util.DateStringChange;
import Util.IdcardUtil;
import Util.UserInfo;
import bll.CoHousingFund.CoHousingFund_ListBll;
import bll.CoHousingFund.CoHousingFund_OperateBll;

public class CoHousingFund_TranCommitController {

	private CoHousingFund_OperateBll bll = new CoHousingFund_OperateBll();
	private CoHousingFund_ListBll bll2 = new CoHousingFund_ListBll();
	private Integer daid = 0;

	private List<PubCoNatureModel> conatureList = new ListModelList<>();
	private List<PubCoEcoclassModel> coecoclassList = new ListModelList<>();
	private List<PubMemberShipModel> membershipList = new ListModelList<>();
	private List<PubAreaSZModel> areaszList = new ListModelList<>();
	private List<PubGjBankModel> gjbankList = new ListModelList<>();
	private List<PubTsbankModel> tsbankList = new ListModelList<>();
	private List<PubIdcardTypeModel> idcardtypeList = new ListModelList<>();
	private List<PubIndustryModel> industryList = new ListModelList<>();
	private List<Integer> cppList = new ListModelList<>();
	private boolean dj;
	private boolean jg;

	private CoHousingFundChangeModel cfm = new CoHousingFundChangeModel();
	private Window win;

	public CoHousingFund_TranCommitController() {
		if (Executions.getCurrent().getArg().get("daid").toString() != null) {
			daid = Integer.parseInt(Executions.getCurrent().getArg()
					.get("daid").toString());

			List<CoHousingFundChangeModel> list = bll2.getCoHoChangeList(daid);
			if (list.size() > 0) {
				cfm = list.get(0);
				fieldinit();
				addtype();

			}
		}

	}

	@Command
	public void winD(@BindingParam("a") Window w) {
		win = w;
	}

	public void fieldinit() {
		CoHousingFund_ListBll bll = new CoHousingFund_ListBll();

		//cfm.setChfc_addtype("缴存登记");
		cfm.setChfc_zgtype("法人");
		cfm.setIspwd2(cfm.getChfc_ispwd().equals(1) ? true : false);
		ispwdcheck();
		cfm.setFirmonth(DateStringChange.StringtoDate(cfm.getChfc_firmonth()
				.toString(), "yyyyMM"));
		cfm.setOwnmonthDate(DateStringChange.StringtoDate(cfm.getOwnmonth()
				.toString(), "yyyyMM"));
		// cfm.setFirmonth(new Date());
		// cfm.setOwnmonthDate(new Date());

		setConatureList(bll.getCoNatureList());
		conatureList.add(0, null);
		setCoecoclassList(bll.getCoEcoclassList());
		coecoclassList.add(0, null);
		setAreaszList(bll.getAreaSzList());
		areaszList.add(0, null);
		setGjbankList(bll.getGjBankList());
		gjbankList.add(0, null);
		setTsbankList(bll.getTsBankList());
		tsbankList.add(0, null);
		setMembershipList(bll.getMemberShipList());
		membershipList.add(0, null);
		setIdcardtypeList(bll.getIdcardTypeList());
		idcardtypeList.add(0, null);

		cppList = bll.getHouseCppList(cfm.getCid());
		industryList = bll.pubIndustry();

	}

	@Command
	@NotifyChange({ "cfm" })
	public void ispwdcheck() {
		if (cfm.getIspwd().equals("1")) {
			cfm.setVis_modzb(true);
			cfm.setIsmodzb("1");
		} else {
			cfm.setVis_modzb(false);
			cfm.setIsmodzb("0");
		}
	}

	@Command("addtype")
	@NotifyChange({ "dj", "jg", "m" })
	public void addtype() {
		if (cfm.getChfc_addtype().equals("缴存登记")) {
			setDj(true);
			setJg(false);
		} else if (cfm.getChfc_addtype().equals("账户接管")) {
			setDj(false);
			setJg(true);
			ispwdcheck();
		}

	}

	@Command
	@NotifyChange({ "cfm" })
	public void gjbankchange() {
		cfm.setChfc_banklk(cfm.getChfc_bankgj());
	}

	@Command
	@NotifyChange({ "cfm" })
	public void bankjcchange() {
		cfm.bankjc_handle();
	}

	/**
	 * 字段处理
	 * 
	 */
	public void fieldhandle() {
		try {
			cfm.month_handle();
			cfm.cpp_handle();
			cfm.setChfc_ispwd(Integer.valueOf(cfm.getIspwd()));
			cfm.setChfc_addname(UserInfo.getUsername());
			cfm.setChfc_laststate(0);
			cfm.setChfc_state(1);
			cfm.setChfc_tzlstate(0);
			cfm.setChfc_if_update_compact(false);
		} catch (Exception e) {
			e.printStackTrace();
			Messagebox.show("数据处理出错：" + e.toString(), "ERROR", Messagebox.OK,
					Messagebox.ERROR);
		}
	}

	// 判断必填项是否有填写
	private String isEmploy() {
		String err = "";
		if (cfm.getChfc_comid() == null || cfm.getChfc_comid().equals("")) {
			err = "组织机构代码不能为空";
		} else if (cfm.getChfc_address() == null
				|| cfm.getChfc_address().equals("")) {
			err = "单位地址不能为空";
		} else if (cfm.getChfc_industry() == null
				|| cfm.getChfc_industry().equals("")) {
			err = "行业代码不能为空";
		} else if (cfm.getChfc_pastal() == null
				|| cfm.getChfc_pastal().equals("")) {
			err = "邮政编码不能为空";
		} else if (cfm.getChfc_nature() == null
				|| cfm.getChfc_nature().equals("")) {
			err = "性质分类不能为空";
		} else if (cfm.getChfc_attached() == null
				|| cfm.getChfc_attached().equals("")) {
			err = "隶属关系不能为空";
		} else if (cfm.getChfc_ecoclass() == null
				|| cfm.getChfc_ecoclass().equals("")) {
			err = "企业经济类型不能为空";
		} else if (cfm.getChfc_tsday() == null
				|| cfm.getChfc_tsday().equals("")) {
			err = "托收日不能为空";
		} else if (cfm.getFirmonth() == null || cfm.getFirmonth().equals("")) {
			err = "首次托收月不能为空";
		} else if (cfm.getChfc_tsday() != null
				&& (cfm.getChfc_tsday() < 1 || cfm.getChfc_tsday() > 31)) {
			err = "托收日有误";
		}
		return err;
	}

	@Command
	public void submit(@BindingParam("a") final Grid gd) {
		fieldhandle();
		if (cfm.getChfc_coridtype() != null
				&& cfm.getChfc_coridtype().equals("身份证")) {
			if (cfm.getChfc_coridcard() != null) {
				if (!IdcardUtil.validateCard(cfm.getChfc_coridcard())) {
					Messagebox.show("身份证不合法,请检查是否正确!", "ERROR", Messagebox.OK,
							Messagebox.ERROR);
					return;
				}
			}
		}
		if (cfm.getChfc_cpp() == null) {
			Messagebox.show("比例不正确!", "ERROR", Messagebox.OK, Messagebox.ERROR);
			return;
		}
		String err = "";
		if (cfm.getChfc_addtype() != null
				&& cfm.getChfc_addtype().equals("缴存登记")) {
			err = isEmploy();
		}
		if (!err.equals("")) {
			Messagebox.show(err, "ERROR", Messagebox.OK, Messagebox.ERROR);
			return;
		}

		Messagebox.show("确认变更数据?", "操作提示", new Messagebox.Button[] {
				Messagebox.Button.OK, Messagebox.Button.CANCEL },
				Messagebox.QUESTION,
				new EventListener<Messagebox.ClickEvent>() {
					@Override
					public void onEvent(ClickEvent event) throws Exception {
						// TODO Auto-generated method stub

						if (Messagebox.Button.OK.equals(event.getButton())) {
							Integer i = bll.sendData(cfm);
							if (i > 0) {

								Messagebox.show("操作成功.", "操作提示", Messagebox.OK,
										Messagebox.INFORMATION);
								win.detach();

							} else {
								Messagebox.show("操作失败.", "操作提示", Messagebox.OK,
										Messagebox.INFORMATION);
							}
						}

					}
				});

	}

	public CoHousingFundChangeModel getCfm() {
		return cfm;
	}

	public void setCfm(CoHousingFundChangeModel cfm) {
		this.cfm = cfm;
	}

	public boolean isDj() {
		return dj;
	}

	public void setDj(boolean dj) {
		this.dj = dj;
	}

	public boolean isJg() {
		return jg;
	}

	public void setJg(boolean jg) {
		this.jg = jg;
	}

	public final List<PubCoNatureModel> getConatureList() {
		return conatureList;
	}

	public final List<PubCoEcoclassModel> getCoecoclassList() {
		return coecoclassList;
	}

	public final List<PubAreaSZModel> getAreaszList() {
		return areaszList;
	}

	public final List<PubGjBankModel> getGjbankList() {
		return gjbankList;
	}

	public final List<PubTsbankModel> getTsbankList() {
		return tsbankList;
	}

	public final void setConatureList(List<PubCoNatureModel> conatureList) {
		this.conatureList = conatureList;
	}

	public final void setCoecoclassList(List<PubCoEcoclassModel> coecoclassList) {
		this.coecoclassList = coecoclassList;
	}

	public final void setAreaszList(List<PubAreaSZModel> areaszList) {
		this.areaszList = areaszList;
	}

	public final void setGjbankList(List<PubGjBankModel> gjbankList) {
		this.gjbankList = gjbankList;
	}

	public final void setTsbankList(List<PubTsbankModel> tsbankList) {
		this.tsbankList = tsbankList;
	}

	public List<PubMemberShipModel> getMembershipList() {
		return membershipList;
	}

	public void setMembershipList(List<PubMemberShipModel> membershipList) {
		this.membershipList = membershipList;
	}

	public List<PubIdcardTypeModel> getIdcardtypeList() {
		return idcardtypeList;
	}

	public void setIdcardtypeList(List<PubIdcardTypeModel> idcardtypeList) {
		this.idcardtypeList = idcardtypeList;
	}

	public List<Integer> getCppList() {
		return cppList;
	}

	public void setCppList(List<Integer> cppList) {
		this.cppList = cppList;
	}

	public List<PubIndustryModel> getIndustryList() {
		return industryList;
	}

	public void setIndustryList(List<PubIndustryModel> industryList) {
		this.industryList = industryList;
	}

}
