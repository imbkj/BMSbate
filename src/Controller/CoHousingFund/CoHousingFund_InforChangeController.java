package Controller.CoHousingFund;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.zkoss.bind.annotation.BindingParam;
import org.zkoss.bind.annotation.Command;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zul.ListModelList;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Window;

import bll.CoHousingFund.CoHousingFund_ListBll;
import bll.CoHousingFund.CoHousingFund_OperateBll;

import Model.CoHousingFundChangeModel;
import Model.CoHousingFundInforChangeModel;
import Model.CoHousingFundModel;
import Model.PubAreaSZModel;
import Model.PubCoEcoclassModel;
import Model.PubCoNatureModel;
import Model.PubIdcardTypeModel;
import Model.PubMemberShipModel;
import Model.PubTsbankModel;
import Util.UserInfo;

public class CoHousingFund_InforChangeController {

	Integer daid = 0;
	private CoHousingFundModel cohfModel = new CoHousingFundModel();
	private CoHousingFundChangeModel chfcModel = new CoHousingFundChangeModel();

	private List<PubCoNatureModel> conatureList = new ListModelList<>();
	private List<PubIdcardTypeModel> idcardtypeList = new ListModelList<>();
	private List<PubTsbankModel> tsbankList = new ListModelList<>();
	private List<PubAreaSZModel> areaszList = new ListModelList<>();
	private List<PubMemberShipModel> membershipList = new ListModelList<>();
	private List<PubCoEcoclassModel> coecoclassList = new ListModelList<>();

	List<CoHousingFundInforChangeModel> cficList = new ArrayList<>();

	public CoHousingFund_InforChangeController() {
		try {
			daid = Integer.valueOf(Executions.getCurrent().getArg().get("daid")
					.toString());

			init();
		} catch (Exception e) {
			e.printStackTrace();
			Messagebox
					.show("页面加载出错!", "ERROR", Messagebox.OK, Messagebox.ERROR);
		}
	}

	public void init() {
		CoHousingFund_ListBll bll = new CoHousingFund_ListBll();
		try {
			setCohfModel(bll.getCoHoList(" and cohf_id=" + daid,false).get(0));

			chfcModel.setOwnmonthDate(new Date());
			chfcModel.setChfc_cohf_id(cohfModel.getCohf_id());
			chfcModel.setCid(cohfModel.getCid());
			chfcModel.setCoba_shortname(cohfModel.getCoba_shortname());
			chfcModel.setChfc_addtype("信息变更");
			chfcModel.setChfc_addname(UserInfo.getUsername());
			chfcModel.setChfc_laststate(0);
			chfcModel.setChfc_state(1);
			chfcModel.setChfc_tzlstate(0);
			chfcModel.setChfc_remark("");

			setConatureList(bll.getCoNatureList());
			conatureList.add(0, null);
			for (int i = 1; i < conatureList.size(); i++) {
				PubCoNatureModel m = conatureList.get(i);
				if (m != null) {
					if (m.getPcon_name().equals(cohfModel.getCohf_nature())) {
						conatureList.remove(i);
						i--;
					}
				}
			}

			setIdcardtypeList(bll.getIdcardTypeList());
			idcardtypeList.add(0, null);
			for (int i = 1; i < idcardtypeList.size(); i++) {
				PubIdcardTypeModel m = idcardtypeList.get(i);
				if (m != null) {
					if (m.getName().equals(cohfModel.getCohf_coridtype())) {
						idcardtypeList.remove(i);
						i--;
					}
				}
			}

			setTsbankList(bll.getTsBankList());
			tsbankList.add(0, null);
			for (int i = 1; i < tsbankList.size(); i++) {
				PubTsbankModel m = tsbankList.get(i);
				if (m != null) {
					if (m.getBank().equals(cohfModel.getCohf_bankts())) {
						tsbankList.remove(i);
						i--;
					}
				}
			}

			setAreaszList(bll.getAreaSzList());
			areaszList.add(0, null);
			for (int i = 1; i < areaszList.size(); i++) {
				PubAreaSZModel m = areaszList.get(i);
				if (m != null) {
					if (m.getArea().equals(cohfModel.getCohf_area())) {
						areaszList.remove(i);
						i--;
					}
				}
			}

			setMembershipList(bll.getMemberShipList());
			membershipList.add(0, null);
			for (int i = 1; i < membershipList.size(); i++) {
				PubMemberShipModel m = membershipList.get(i);
				if (m != null) {
					if (m.getName().equals(cohfModel.getCohf_attached())) {
						membershipList.remove(i);
						i--;
					}
				}
			}

			setCoecoclassList(bll.getCoEcoclassList());
			coecoclassList.add(0, null);
			for (int i = 1; i < coecoclassList.size(); i++) {
				PubCoEcoclassModel m = coecoclassList.get(i);
				if (m != null) {
					if (m.getPcoe_name().equals(cohfModel.getCohf_ecoclass())) {
						coecoclassList.remove(i);
						i--;
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 提交
	 * 
	 */
	@Command
	public void submit(@BindingParam("win") Window win) {
		String alarmStr = "";
		chfcModel.month_handle();
		data_handle();
		if (chfcModel.getCficList().size() == 0) {
			Messagebox.show("无修改项，请至少修改一项!", "ERROR", Messagebox.OK,
					Messagebox.ERROR);
		} else {
			try {
				if (chfcModel.getChfc_puzu_id().equals(0)) {
					alarmStr = "只修改了备案类信息，无需提交纸质材料\n是否确认提交?";
					chfcModel.setChfc_remark("只修改了备案类信息，无需提交纸质材料；"
							+ chfcModel.getChfc_remark());
				} else {
					alarmStr = "是否确认提交?";
				}

				if (Messagebox.show(alarmStr, "CONFIRM", Messagebox.OK
						| Messagebox.CANCEL, Messagebox.QUESTION) == Messagebox.OK) {

					String[] str = new CoHousingFund_OperateBll()
							.inforchange(chfcModel);

					if (str[0].equals("1")) {
						Messagebox.show(str[1], "INFORMATION", Messagebox.OK,
								Messagebox.INFORMATION);
						win.detach();
					} else {
						Messagebox.show(str[1], "ERROR", Messagebox.OK,
								Messagebox.ERROR);
					}
				}
			} catch (Exception e) {
				e.printStackTrace();
				Messagebox.show("提交出错!", "ERROR", Messagebox.OK,
						Messagebox.ERROR);
			}
		}
	}

	/**
	 * 变更数据处理
	 * 
	 */
	public void data_handle() {
		try {
			cficList.clear();

			// 核准类信息
			cohfModel.setCohf_comid(cohfModel.getCohf_comid() == null ? ""
					: cohfModel.getCohf_comid().trim());
			chfcModel.setChfc_comid(chfcModel.getChfc_comid() == null ? ""
					: chfcModel.getChfc_comid().trim());
			if (!chfcModel.getChfc_comid().isEmpty()
					&& !cohfModel.getCohf_comid().equals(
							chfcModel.getChfc_comid())) {
				CoHousingFundInforChangeModel m = new CoHousingFundInforChangeModel();
				m.setCfic_changestyle("组织机构代码");
				m.setCfic_changetype("核准类信息");
				m.setCfic_changeold(cohfModel.getCohf_comid());
				m.setCfic_changenew(chfcModel.getChfc_comid());
				cficList.add(m);
			} else {
				chfcModel.setChfc_comid(cohfModel.getCohf_comid());
			}

			cohfModel.setCohf_company(cohfModel.getCohf_company() == null ? ""
					: cohfModel.getCohf_company().trim());
			chfcModel.setChfc_company(chfcModel.getChfc_company() == null ? ""
					: chfcModel.getChfc_company().trim());
			if (!chfcModel.getChfc_company().isEmpty()
					&& !cohfModel.getCohf_company().equals(
							chfcModel.getChfc_company())) {
				CoHousingFundInforChangeModel m = new CoHousingFundInforChangeModel();
				m.setCfic_changestyle("单位名称");
				m.setCfic_changetype("核准类信息");
				m.setCfic_changeold(cohfModel.getCohf_company());
				m.setCfic_changenew(chfcModel.getChfc_company());
				cficList.add(m);
			} else {
				chfcModel.setChfc_company(cohfModel.getCohf_company());
			}

			cohfModel.setCohf_nature(cohfModel.getCohf_nature() == null ? ""
					: cohfModel.getCohf_nature().trim());
			chfcModel.setChfc_nature(chfcModel.getChfc_nature() == null ? ""
					: chfcModel.getChfc_nature().trim());
			if (!chfcModel.getChfc_nature().isEmpty()
					&& !cohfModel.getCohf_nature().equals(
							chfcModel.getChfc_nature())) {
				CoHousingFundInforChangeModel m = new CoHousingFundInforChangeModel();
				m.setCfic_changestyle("性质分类");
				m.setCfic_changetype("核准类信息");
				m.setCfic_changeold(cohfModel.getCohf_nature());
				m.setCfic_changenew(chfcModel.getChfc_nature());
				cficList.add(m);
			} else {
				chfcModel.setChfc_nature(cohfModel.getCohf_nature());
			}

			cohfModel.setCohf_corname(cohfModel.getCohf_corname() == null ? ""
					: cohfModel.getCohf_corname().trim());
			chfcModel.setChfc_corname(chfcModel.getChfc_corname() == null ? ""
					: chfcModel.getChfc_corname().trim());
			if (!chfcModel.getChfc_corname().isEmpty()
					&& !cohfModel.getCohf_corname().equals(
							chfcModel.getChfc_corname())) {
				CoHousingFundInforChangeModel m = new CoHousingFundInforChangeModel();
				m.setCfic_changestyle("法人代表人姓名");
				m.setCfic_changetype("核准类信息");
				m.setCfic_changeold(cohfModel.getCohf_corname());
				m.setCfic_changenew(chfcModel.getChfc_corname());
				cficList.add(m);
			} else {
				chfcModel.setChfc_corname(cohfModel.getCohf_corname());
			}

			cohfModel
					.setCohf_coridtype(cohfModel.getCohf_coridtype() == null ? ""
							: cohfModel.getCohf_coridtype().trim());
			chfcModel
					.setChfc_coridtype(chfcModel.getChfc_coridtype() == null ? ""
							: chfcModel.getChfc_coridtype().trim());
			if (!chfcModel.getChfc_coridtype().isEmpty()
					&& !cohfModel.getCohf_coridtype().equals(
							chfcModel.getChfc_coridtype())) {
				CoHousingFundInforChangeModel m = new CoHousingFundInforChangeModel();
				m.setCfic_changestyle("法人代表人证件类型");
				m.setCfic_changetype("核准类信息");
				m.setCfic_changeold(cohfModel.getCohf_coridtype());
				m.setCfic_changenew(chfcModel.getChfc_coridtype());
				cficList.add(m);
			} else {
				chfcModel.setChfc_coridtype(cohfModel.getCohf_coridtype());
			}

			cohfModel
					.setCohf_coridcard(cohfModel.getCohf_coridcard() == null ? ""
							: cohfModel.getCohf_coridcard().trim());
			chfcModel
					.setChfc_coridcard(chfcModel.getChfc_coridcard() == null ? ""
							: chfcModel.getChfc_coridcard().trim());
			if (!chfcModel.getChfc_coridcard().isEmpty()
					&& !cohfModel.getCohf_coridcard().equals(
							chfcModel.getChfc_coridcard())) {
				CoHousingFundInforChangeModel m = new CoHousingFundInforChangeModel();
				m.setCfic_changestyle("法人代表人证件号码");
				m.setCfic_changetype("核准类信息");
				m.setCfic_changeold(cohfModel.getCohf_coridcard());
				m.setCfic_changenew(chfcModel.getChfc_coridcard());
				cficList.add(m);
			} else {
				chfcModel.setChfc_coridcard(cohfModel.getCohf_coridcard());
			}

			cohfModel.setCohf_sorid(cohfModel.getCohf_sorid() == null ? ""
					: cohfModel.getCohf_sorid().trim());
			chfcModel.setChfc_sorid(chfcModel.getChfc_sorid() == null ? ""
					: chfcModel.getChfc_sorid().trim());
			if (!chfcModel.getChfc_sorid().isEmpty()
					&& !cohfModel.getCohf_sorid().equals(
							chfcModel.getChfc_sorid())) {
				CoHousingFundInforChangeModel m = new CoHousingFundInforChangeModel();
				m.setCfic_changestyle("社保单位编号");
				m.setCfic_changetype("核准类信息");
				m.setCfic_changeold(cohfModel.getCohf_sorid());
				m.setCfic_changenew(chfcModel.getChfc_sorid());
				cficList.add(m);
			} else {
				chfcModel.setChfc_sorid(cohfModel.getCohf_sorid());
			}

			cohfModel.setCohf_regid(cohfModel.getCohf_regid() == null ? ""
					: cohfModel.getCohf_regid().trim());
			chfcModel.setChfc_regid(chfcModel.getChfc_regid() == null ? ""
					: chfcModel.getChfc_regid().trim());
			if (!chfcModel.getChfc_regid().isEmpty()
					&& !cohfModel.getCohf_regid().equals(
							chfcModel.getChfc_regid())) {
				CoHousingFundInforChangeModel m = new CoHousingFundInforChangeModel();
				m.setCfic_changestyle("工商注册号");
				m.setCfic_changetype("核准类信息");
				m.setCfic_changeold(cohfModel.getCohf_regid());
				m.setCfic_changenew(chfcModel.getChfc_regid());
				cficList.add(m);
			} else {
				chfcModel.setChfc_regid(cohfModel.getCohf_regid());
			}

			cohfModel
					.setCohf_taxpayerid(cohfModel.getCohf_taxpayerid() == null ? ""
							: cohfModel.getCohf_taxpayerid().trim());
			chfcModel
					.setChfc_taxpayerid(chfcModel.getChfc_taxpayerid() == null ? ""
							: chfcModel.getChfc_taxpayerid().trim());
			if (!chfcModel.getChfc_taxpayerid().isEmpty()
					&& !cohfModel.getCohf_taxpayerid().equals(
							chfcModel.getChfc_taxpayerid())) {
				CoHousingFundInforChangeModel m = new CoHousingFundInforChangeModel();
				m.setCfic_changestyle("纳税人识别号");
				m.setCfic_changetype("核准类信息");
				m.setCfic_changeold(cohfModel.getCohf_taxpayerid());
				m.setCfic_changenew(chfcModel.getChfc_taxpayerid());
				cficList.add(m);
			} else {
				chfcModel.setChfc_taxpayerid(cohfModel.getCohf_taxpayerid());
			}

			if (chfcModel.getChfc_tsday() != null
					&& !cohfModel.getCohf_tsday().equals(
							chfcModel.getChfc_tsday())) {
				CoHousingFundInforChangeModel m = new CoHousingFundInforChangeModel();
				m.setCfic_changestyle("托收日");
				m.setCfic_changetype("核准类信息");
				m.setCfic_changeold(cohfModel.getCohf_tsday() + "");
				m.setCfic_changenew(chfcModel.getChfc_tsday() + "");
				cficList.add(m);
			} else {
				chfcModel.setChfc_tsday(cohfModel.getCohf_tsday());
			}

			if (chfcModel.getChfc_firmonth() != null
					&& !cohfModel.getCohf_firmonth().equals(
							chfcModel.getChfc_firmonth())) {
				CoHousingFundInforChangeModel m = new CoHousingFundInforChangeModel();
				m.setCfic_changestyle("公积金首次托收月");
				m.setCfic_changetype("核准类信息");
				m.setCfic_changeold(cohfModel.getCohf_firmonth() + "");
				m.setCfic_changenew(chfcModel.getChfc_firmonth() + "");
				cficList.add(m);
			} else {
				chfcModel.setChfc_firmonth(cohfModel.getCohf_firmonth());
			}

			cohfModel.setCohf_bankts(cohfModel.getCohf_bankts() == null ? ""
					: cohfModel.getCohf_bankts().trim());
			chfcModel.setChfc_bankts(chfcModel.getChfc_bankts() == null ? ""
					: chfcModel.getChfc_bankts().trim());
			if (!chfcModel.getChfc_bankts().isEmpty()
					&& !cohfModel.getCohf_bankts().equals(
							chfcModel.getChfc_bankts())) {
				CoHousingFundInforChangeModel m = new CoHousingFundInforChangeModel();
				m.setCfic_changestyle("托收账户开户银行");
				m.setCfic_changetype("核准类信息");
				m.setCfic_changeold(cohfModel.getCohf_bankts());
				m.setCfic_changenew(chfcModel.getChfc_bankts());
				cficList.add(m);
			} else {
				chfcModel.setChfc_bankts(cohfModel.getCohf_bankts());
			}

			cohfModel
					.setCohf_banktsid(cohfModel.getCohf_banktsid() == null ? ""
							: cohfModel.getCohf_banktsid().trim());
			chfcModel
					.setChfc_banktsid(chfcModel.getChfc_banktsid() == null ? ""
							: chfcModel.getChfc_banktsid().trim());
			if (!chfcModel.getChfc_banktsid().isEmpty()
					&& !cohfModel.getCohf_banktsid().equals(
							chfcModel.getChfc_banktsid())) {
				CoHousingFundInforChangeModel m = new CoHousingFundInforChangeModel();
				m.setCfic_changestyle("托收账户银行账号");
				m.setCfic_changetype("核准类信息");
				m.setCfic_changeold(cohfModel.getCohf_banktsid());
				m.setCfic_changenew(chfcModel.getChfc_banktsid());
				cficList.add(m);
			} else {
				chfcModel.setChfc_banktsid(cohfModel.getCohf_banktsid());
			}

			cohfModel
					.setCohf_banktsacc(cohfModel.getCohf_banktsacc() == null ? ""
							: cohfModel.getCohf_banktsacc().trim());
			chfcModel
					.setChfc_banktsacc(chfcModel.getChfc_banktsacc() == null ? ""
							: chfcModel.getChfc_banktsacc().trim());
			if (!chfcModel.getChfc_banktsacc().isEmpty()
					&& !cohfModel.getCohf_banktsacc().equals(
							chfcModel.getChfc_banktsacc())) {
				CoHousingFundInforChangeModel m = new CoHousingFundInforChangeModel();
				m.setCfic_changestyle("托收账户名称");
				m.setCfic_changetype("核准类信息");
				m.setCfic_changeold(cohfModel.getCohf_banktsacc());
				m.setCfic_changenew(chfcModel.getChfc_banktsacc());
				cficList.add(m);
			} else {
				chfcModel.setChfc_banktsacc(cohfModel.getCohf_banktsacc());
			}

			// 备案类信息
			cohfModel
					.setCohf_industry(cohfModel.getCohf_industry() == null ? ""
							: cohfModel.getCohf_industry().trim());
			chfcModel
					.setChfc_industry(chfcModel.getChfc_industry() == null ? ""
							: chfcModel.getChfc_industry().trim());
			if (!chfcModel.getChfc_industry().isEmpty()
					&& !cohfModel.getCohf_industry().equals(
							chfcModel.getChfc_industry())) {
				CoHousingFundInforChangeModel m = new CoHousingFundInforChangeModel();
				m.setCfic_changestyle("行业代码");
				m.setCfic_changetype("备案类信息");
				m.setCfic_changeold(cohfModel.getCohf_industry());
				m.setCfic_changenew(chfcModel.getChfc_industry());
				cficList.add(m);
			} else {
				chfcModel.setChfc_industry(cohfModel.getCohf_industry());
			}

			cohfModel.setCohf_zgtype(cohfModel.getCohf_zgtype() == null ? ""
					: cohfModel.getCohf_zgtype().trim());
			chfcModel.setChfc_zgtype(chfcModel.getChfc_zgtype() == null ? ""
					: chfcModel.getChfc_zgtype().trim());
			if (!chfcModel.getChfc_zgtype().isEmpty()
					&& !cohfModel.getCohf_zgtype().equals(
							chfcModel.getChfc_zgtype())) {
				CoHousingFundInforChangeModel m = new CoHousingFundInforChangeModel();
				m.setCfic_changestyle("资格类型");
				m.setCfic_changetype("备案类信息");
				m.setCfic_changeold(cohfModel.getCohf_zgtype());
				m.setCfic_changenew(chfcModel.getChfc_zgtype());
				cficList.add(m);
			} else {
				chfcModel.setChfc_zgtype(cohfModel.getCohf_zgtype());
			}

			cohfModel.setCohf_address(cohfModel.getCohf_address() == null ? ""
					: cohfModel.getCohf_address().trim());
			chfcModel.setChfc_address(chfcModel.getChfc_address() == null ? ""
					: chfcModel.getChfc_address().trim());
			if (!chfcModel.getChfc_address().isEmpty()
					&& !cohfModel.getCohf_address().equals(
							chfcModel.getChfc_address())) {
				CoHousingFundInforChangeModel m = new CoHousingFundInforChangeModel();
				m.setCfic_changestyle("单位地址");
				m.setCfic_changetype("备案类信息");
				m.setCfic_changeold(cohfModel.getCohf_address());
				m.setCfic_changenew(chfcModel.getChfc_address());
				cficList.add(m);
			} else {
				chfcModel.setChfc_address(cohfModel.getCohf_address());
			}

			cohfModel.setCohf_area(cohfModel.getCohf_area() == null ? ""
					: cohfModel.getCohf_area().trim());
			chfcModel.setChfc_area(chfcModel.getChfc_area() == null ? ""
					: chfcModel.getChfc_area().trim());
			if (!chfcModel.getChfc_area().isEmpty()
					&& !cohfModel.getCohf_area().equals(
							chfcModel.getChfc_area())) {
				CoHousingFundInforChangeModel m = new CoHousingFundInforChangeModel();
				m.setCfic_changestyle("所属行政区域");
				m.setCfic_changetype("备案类信息");
				m.setCfic_changeold(cohfModel.getCohf_area());
				m.setCfic_changenew(chfcModel.getChfc_area());
				cficList.add(m);
			} else {
				chfcModel.setChfc_area(cohfModel.getCohf_area());
			}

			cohfModel.setCohf_pastal(cohfModel.getCohf_pastal() == null ? ""
					: cohfModel.getCohf_pastal().trim());
			chfcModel.setChfc_pastal(chfcModel.getChfc_pastal() == null ? ""
					: chfcModel.getChfc_pastal().trim());
			if (!chfcModel.getChfc_pastal().isEmpty()
					&& !cohfModel.getCohf_pastal().equals(
							chfcModel.getChfc_pastal())) {
				CoHousingFundInforChangeModel m = new CoHousingFundInforChangeModel();
				m.setCfic_changestyle("邮政编码");
				m.setCfic_changetype("备案类信息");
				m.setCfic_changeold(cohfModel.getCohf_pastal());
				m.setCfic_changenew(chfcModel.getChfc_pastal());
				cficList.add(m);
			} else {
				chfcModel.setChfc_pastal(cohfModel.getCohf_pastal());
			}

			cohfModel
					.setCohf_attached(cohfModel.getCohf_attached() == null ? ""
							: cohfModel.getCohf_attached().trim());
			chfcModel
					.setChfc_attached(chfcModel.getChfc_attached() == null ? ""
							: chfcModel.getChfc_attached().trim());
			if (!chfcModel.getChfc_attached().isEmpty()
					&& !cohfModel.getCohf_attached().equals(
							chfcModel.getChfc_attached())) {
				CoHousingFundInforChangeModel m = new CoHousingFundInforChangeModel();
				m.setCfic_changestyle("隶属关系");
				m.setCfic_changetype("备案类信息");
				m.setCfic_changeold(cohfModel.getCohf_attached());
				m.setCfic_changenew(chfcModel.getChfc_attached());
				cficList.add(m);
			} else {
				chfcModel.setChfc_attached(cohfModel.getCohf_attached());
			}

			cohfModel
					.setCohf_ecoclass(cohfModel.getCohf_ecoclass() == null ? ""
							: cohfModel.getCohf_ecoclass().trim());
			chfcModel
					.setChfc_ecoclass(chfcModel.getChfc_ecoclass() == null ? ""
							: chfcModel.getChfc_ecoclass().trim());
			if (!chfcModel.getChfc_ecoclass().isEmpty()
					&& !cohfModel.getCohf_ecoclass().equals(
							chfcModel.getChfc_ecoclass())) {
				CoHousingFundInforChangeModel m = new CoHousingFundInforChangeModel();
				m.setCfic_changestyle("企业经济类型");
				m.setCfic_changetype("备案类信息");
				m.setCfic_changeold(cohfModel.getCohf_ecoclass());
				m.setCfic_changenew(chfcModel.getChfc_ecoclass());
				cficList.add(m);
			} else {
				chfcModel.setChfc_ecoclass(cohfModel.getCohf_ecoclass());
			}

			cohfModel.setCohf_cortel(cohfModel.getCohf_cortel() == null ? ""
					: cohfModel.getCohf_cortel().trim());
			chfcModel.setChfc_cortel(chfcModel.getChfc_cortel() == null ? ""
					: chfcModel.getChfc_cortel().trim());
			if (!chfcModel.getChfc_cortel().isEmpty()
					&& !cohfModel.getCohf_cortel().equals(
							chfcModel.getChfc_cortel())) {
				CoHousingFundInforChangeModel m = new CoHousingFundInforChangeModel();
				m.setCfic_changestyle("法人代表人联系电话");
				m.setCfic_changetype("备案类信息");
				m.setCfic_changeold(cohfModel.getCohf_cortel());
				m.setCfic_changenew(chfcModel.getChfc_cortel());
				cficList.add(m);
			} else {
				chfcModel.setChfc_cortel(cohfModel.getCohf_cortel());
			}

			cohfModel
					.setCohf_department(cohfModel.getCohf_department() == null ? ""
							: cohfModel.getCohf_department().trim());
			chfcModel
					.setChfc_department(chfcModel.getChfc_department() == null ? ""
							: chfcModel.getChfc_department().trim());
			if (!chfcModel.getChfc_department().isEmpty()
					&& !cohfModel.getCohf_department().equals(
							chfcModel.getChfc_department())) {
				CoHousingFundInforChangeModel m = new CoHousingFundInforChangeModel();
				m.setCfic_changestyle("上级主管部门名称");
				m.setCfic_changetype("备案类信息");
				m.setCfic_changeold(cohfModel.getCohf_department());
				m.setCfic_changenew(chfcModel.getChfc_department());
				cficList.add(m);
			} else {
				chfcModel.setChfc_department(cohfModel.getCohf_department());
			}

			cohfModel
					.setCohf_departmenttel(cohfModel.getCohf_departmenttel() == null ? ""
							: cohfModel.getCohf_departmenttel().trim());
			chfcModel
					.setChfc_departmenttel(chfcModel.getChfc_departmenttel() == null ? ""
							: chfcModel.getChfc_departmenttel().trim());
			if (!chfcModel.getChfc_departmenttel().isEmpty()
					&& !cohfModel.getCohf_departmenttel().equals(
							chfcModel.getChfc_departmenttel())) {
				CoHousingFundInforChangeModel m = new CoHousingFundInforChangeModel();
				m.setCfic_changestyle("上级主管部门联系电话");
				m.setCfic_changetype("备案类信息");
				m.setCfic_changeold(cohfModel.getCohf_departmenttel());
				m.setCfic_changenew(chfcModel.getChfc_departmenttel());
				cficList.add(m);
			} else {
				chfcModel.setChfc_departmenttel(cohfModel
						.getCohf_departmenttel());
			}

			cohfModel
					.setCohf_createtime(cohfModel.getCohf_createtime() == null ? ""
							: cohfModel.getCohf_createtime().trim());
			chfcModel
					.setChfc_createtime(chfcModel.getChfc_createtime() == null ? ""
							: chfcModel.getChfc_createtime().trim());
			if (!chfcModel.getChfc_createtime().isEmpty()
					&& !cohfModel.getCohf_createtime().equals(
							chfcModel.getChfc_createtime())) {
				CoHousingFundInforChangeModel m = new CoHousingFundInforChangeModel();
				m.setCfic_changestyle("单位成立时间");
				m.setCfic_changetype("备案类信息");
				m.setCfic_changeold(cohfModel.getCohf_createtime());
				m.setCfic_changenew(chfcModel.getChfc_createtime());
				cficList.add(m);
			} else {
				chfcModel.setChfc_createtime(cohfModel.getCohf_createtime());
			}

			cohfModel
					.setCohf_jbdepartment(cohfModel.getCohf_jbdepartment() == null ? ""
							: cohfModel.getCohf_jbdepartment().trim());
			chfcModel
					.setChfc_jbdepartment(chfcModel.getChfc_jbdepartment() == null ? ""
							: chfcModel.getChfc_jbdepartment().trim());
			if (!chfcModel.getChfc_jbdepartment().isEmpty()
					&& !cohfModel.getCohf_jbdepartment().equals(
							chfcModel.getChfc_jbdepartment())) {
				CoHousingFundInforChangeModel m = new CoHousingFundInforChangeModel();
				m.setCfic_changestyle("公积金业务经办部门");
				m.setCfic_changetype("备案类信息");
				m.setCfic_changeold(cohfModel.getCohf_jbdepartment());
				m.setCfic_changenew(chfcModel.getChfc_jbdepartment());
				cficList.add(m);
			} else {
				chfcModel
						.setChfc_jbdepartment(cohfModel.getCohf_jbdepartment());
			}

			cohfModel
					.setCohf_contactname(cohfModel.getCohf_contactname() == null ? ""
							: cohfModel.getCohf_contactname().trim());
			chfcModel
					.setChfc_contactname(chfcModel.getChfc_contactname() == null ? ""
							: chfcModel.getChfc_contactname().trim());
			if (!chfcModel.getChfc_contactname().isEmpty()
					&& !cohfModel.getCohf_contactname().equals(
							chfcModel.getChfc_contactname())) {
				CoHousingFundInforChangeModel m = new CoHousingFundInforChangeModel();
				m.setCfic_changestyle("联系人姓名");
				m.setCfic_changetype("备案类信息");
				m.setCfic_changeold(cohfModel.getCohf_contactname());
				m.setCfic_changenew(chfcModel.getChfc_contactname());
				cficList.add(m);
			} else {
				chfcModel.setChfc_contactname(cohfModel.getCohf_contactname());
			}

			cohfModel
					.setCohf_contacttel(cohfModel.getCohf_contacttel() == null ? ""
							: cohfModel.getCohf_contacttel().trim());
			chfcModel
					.setChfc_contacttel(chfcModel.getChfc_contacttel() == null ? ""
							: chfcModel.getChfc_contacttel().trim());
			if (!chfcModel.getChfc_contacttel().isEmpty()
					&& !cohfModel.getCohf_contacttel().equals(
							chfcModel.getChfc_contacttel())) {
				CoHousingFundInforChangeModel m = new CoHousingFundInforChangeModel();
				m.setCfic_changestyle("联系人固定电话");
				m.setCfic_changetype("备案类信息");
				m.setCfic_changeold(cohfModel.getCohf_contacttel());
				m.setCfic_changenew(chfcModel.getChfc_contacttel());
				cficList.add(m);
			} else {
				chfcModel.setChfc_contacttel(cohfModel.getCohf_contacttel());
			}

			cohfModel
					.setCohf_contactmail(cohfModel.getCohf_contactmail() == null ? ""
							: cohfModel.getCohf_contactmail().trim());
			chfcModel
					.setChfc_contactmail(chfcModel.getChfc_contactmail() == null ? ""
							: chfcModel.getChfc_contactmail().trim());
			if (!chfcModel.getChfc_contactmail().isEmpty()
					&& !cohfModel.getCohf_contactmail().equals(
							chfcModel.getChfc_contactmail())) {
				CoHousingFundInforChangeModel m = new CoHousingFundInforChangeModel();
				m.setCfic_changestyle("联系人电子邮箱");
				m.setCfic_changetype("备案类信息");
				m.setCfic_changeold(cohfModel.getCohf_contactmail());
				m.setCfic_changenew(chfcModel.getChfc_contactmail());
				cficList.add(m);
			} else {
				chfcModel.setChfc_contactmail(cohfModel.getCohf_contactmail());
			}

			cohfModel
					.setCohf_contactmobile(cohfModel.getCohf_contactmobile() == null ? ""
							: cohfModel.getCohf_contactmobile().trim());
			chfcModel
					.setChfc_contactmobile(chfcModel.getChfc_contactmobile() == null ? ""
							: chfcModel.getChfc_contactmobile().trim());
			if (!chfcModel.getChfc_contactmobile().isEmpty()
					&& !cohfModel.getCohf_contactmobile().equals(
							chfcModel.getChfc_contactmobile())) {
				CoHousingFundInforChangeModel m = new CoHousingFundInforChangeModel();
				m.setCfic_changestyle("联系人移动电话");
				m.setCfic_changetype("备案类信息");
				m.setCfic_changeold(cohfModel.getCohf_contactmobile());
				m.setCfic_changenew(chfcModel.getChfc_contactmobile());
				cficList.add(m);
			} else {
				chfcModel.setChfc_contactmobile(cohfModel
						.getCohf_contactmobile());
			}

			chfcModel.setCficList(cficList);
			doc_handle();
			chfcModel.changestr_handle();
		} catch (Exception e) {
			e.printStackTrace();
			Messagebox.show("变更项处理出错：" + e.toString(), "ERROR", Messagebox.OK,
					Messagebox.ERROR);
		}
	}

	/**
	 * 判断是否交材料(只修改备案类信息无需提交材料)
	 * 
	 */
	public void doc_handle() {
		try {
			Boolean bo = false;
			for (int i = 0; i < cficList.size(); i++) {
				if (cficList.get(i).getCfic_changetype().equals("核准类信息")) {
					bo = true;
					break;
				}
			}

			if (bo) {
				chfcModel.setChfc_puzu_id(78);
			} else {
				chfcModel.setChfc_puzu_id(0);
				chfcModel.setChfc_tzlstate(1);
			}
		} catch (Exception e) {
			e.printStackTrace();
			Messagebox.show("纸质材料处理出错：" + e.toString(), "ERROR", Messagebox.OK,
					Messagebox.ERROR);
		}
	}

	public CoHousingFundModel getCohfModel() {
		return cohfModel;
	}

	public void setCohfModel(CoHousingFundModel cohfModel) {
		this.cohfModel = cohfModel;
	}

	public CoHousingFundChangeModel getChfcModel() {
		return chfcModel;
	}

	public void setChfcModel(CoHousingFundChangeModel chfcModel) {
		this.chfcModel = chfcModel;
	}

	public List<PubCoNatureModel> getConatureList() {
		return conatureList;
	}

	public void setConatureList(List<PubCoNatureModel> conatureList) {
		this.conatureList = conatureList;
	}

	public List<PubIdcardTypeModel> getIdcardtypeList() {
		return idcardtypeList;
	}

	public void setIdcardtypeList(List<PubIdcardTypeModel> idcardtypeList) {
		this.idcardtypeList = idcardtypeList;
	}

	public List<PubTsbankModel> getTsbankList() {
		return tsbankList;
	}

	public void setTsbankList(List<PubTsbankModel> tsbankList) {
		this.tsbankList = tsbankList;
	}

	public List<PubAreaSZModel> getAreaszList() {
		return areaszList;
	}

	public void setAreaszList(List<PubAreaSZModel> areaszList) {
		this.areaszList = areaszList;
	}

	public List<PubMemberShipModel> getMembershipList() {
		return membershipList;
	}

	public void setMembershipList(List<PubMemberShipModel> membershipList) {
		this.membershipList = membershipList;
	}

	public List<PubCoEcoclassModel> getCoecoclassList() {
		return coecoclassList;
	}

	public void setCoecoclassList(List<PubCoEcoclassModel> coecoclassList) {
		this.coecoclassList = coecoclassList;
	}
}
