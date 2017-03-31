package Controller.EmResidencePermit;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.zkoss.bind.annotation.BindingParam;
import org.zkoss.bind.annotation.Command;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zul.ListModelList;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Window;

import dal.EmReg.EmReg_OperateDal;
import dal.EmResidencePermit.Emrp_OperateDal;

import bll.EmReg.EmReg_ListBll;
import bll.EmResidencePermit.Emrp_ListBll;

import Model.EmRegistrationInfoModel;
import Model.EmResidencePermitInfoModel;
import Model.PubCodeConversionModel;

public class Emrp_hd_ModController {

	private EmRegistrationInfoModel erm = new EmRegistrationInfoModel();
	private EmResidencePermitInfoModel epm = new EmResidencePermitInfoModel();

	// 下拉列表
	private List<PubCodeConversionModel> apptypeList;// 申办居住证类型
	private PubCodeConversionModel apptypeM = new PubCodeConversionModel();
	private List<PubCodeConversionModel> appconList;// 申领居住证条件
	private PubCodeConversionModel appconM = new PubCodeConversionModel();
	private List<PubCodeConversionModel> oetypeList;// 就业类型
	private PubCodeConversionModel oetypeM = new PubCodeConversionModel();
	private List<PubCodeConversionModel> skillList;// 职业技能等级
	private PubCodeConversionModel skillM = new PubCodeConversionModel();
	private List<PubCodeConversionModel> comeszList;// 来深居住事由
	private PubCodeConversionModel comeszM = new PubCodeConversionModel();
	private List<PubCodeConversionModel> houseList;// 住所类别
	private PubCodeConversionModel houseM = new PubCodeConversionModel();
	private List<PubCodeConversionModel> housemodeList;// 居住方式
	private PubCodeConversionModel housemodeM = new PubCodeConversionModel();

	// 特殊字段
	private Date come_sz_date;// 来深时间
	private Date r_date;// 入住时间

	Integer daid = 0;
	SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

	public Emrp_hd_ModController() {
		try {
			daid = Integer.parseInt(Executions.getCurrent().getArg()
					.get("daid").toString());

			setEpm(new Emrp_ListBll().getEmrpInfo(daid));
			setErm(new EmReg_ListBll().getEmRegInfo(epm.getErin_id(), ""));

			// 获取下拉列表
			getComboList();

			// 特殊字段初始化
			come_sz_date = erm.getErin_come_sz_date() == null ? null : sdf
					.parse(erm.getErin_come_sz_date());
			r_date = erm.getErin_r_date() == null ? null : sdf.parse(erm
					.getErin_r_date());

		} catch (Exception e) {
			e.printStackTrace();
			Messagebox
					.show("页面加载出错!", "ERROR", Messagebox.OK, Messagebox.ERROR);
		}
	}

	// 获取下拉列表
	public void getComboList() {
		try {
			EmReg_ListBll bll = new EmReg_ListBll();

			setApptypeList(new ListModelList<>(
					bll.getPubCodeList(" and pucl_id=3 and pcco_name='申办居住证类型'")));
			if (epm.getErpi_app_type() != null && epm.getErpi_app_type() != 0) {
				for (PubCodeConversionModel m : apptypeList) {
					if (m.getPcco_code().equals(epm.getErpi_app_type() + "")) {
						setApptypeM(m);
						break;
					}
				}
			}
			apptypeList.add(0, new PubCodeConversionModel());

			setAppconList(new ListModelList<>(
					bll.getPubCodeList(" and pucl_id=3 and pcco_name='申领居住证条件'")));
			if (epm.getErpi_app_con() != null && epm.getErpi_app_con() != 0) {
				for (PubCodeConversionModel m : appconList) {
					if (m.getPcco_code().equals(epm.getErpi_app_con() + "")) {
						setAppconM(m);
						break;
					}
				}
			}
			appconList.add(0, new PubCodeConversionModel());

			setSkillList(new ListModelList<>(
					bll.getPubCodeList(" and pucl_id=16 and pcco_name='职业技能等级'")));
			if (erm.getErin_skilllevel() != null
					&& !erm.getErin_skilllevel().isEmpty()) {
				for (PubCodeConversionModel m : skillList) {
					if (m.getPcco_cn().equals(erm.getErin_skilllevel())) {
						setSkillM(m);
						break;
					}
				}
			}
			skillList.add(0, new PubCodeConversionModel());

			setHousemodeList(new ListModelList<>(
					bll.getPubCodeList(" and pucl_id=16 and pcco_name='居住方式'")));
			if (erm.getErin_house_mode() != null
					&& erm.getErin_house_mode() != 0) {
				for (PubCodeConversionModel m : housemodeList) {
					if (m.getPcco_code().equals(erm.getErin_house_mode() + "")) {
						setHousemodeM(m);
						break;
					}
				}
			}
			housemodeList.add(0, new PubCodeConversionModel());

			setHouseList(new ListModelList<>(
					bll.getPubCodeList(" and pucl_id=16 and pcco_name='住所类别'")));
			if (erm.getErin_house_class() != null
					&& erm.getErin_house_class() != 0) {
				for (PubCodeConversionModel m : houseList) {
					if (m.getPcco_code().equals(erm.getErin_house_class() + "")) {
						setHouseM(m);
						break;
					}
				}
			}
			houseList.add(0, new PubCodeConversionModel());

			setComeszList(new ListModelList<>(
					bll.getPubCodeList(" and pucl_id=16 and pcco_name='来深居住事由'")));
			if (erm.getErin_come_sz_reason() != null
					&& erm.getErin_come_sz_reason() != 0) {
				for (PubCodeConversionModel m : comeszList) {
					if (m.getPcco_code().equals(
							erm.getErin_come_sz_reason() + "")) {
						setComeszM(m);
						break;
					}
				}
			}
			comeszList.add(0, new PubCodeConversionModel());

			setOetypeList(new ListModelList<>(
					bll.getPubCodeList(" and pucl_id=16 and pcco_name='就业类型'")));
			if (erm.getErin_oe_type() != null && erm.getErin_oe_type() != 0) {
				for (PubCodeConversionModel m : oetypeList) {
					if (m.getPcco_code().equals(erm.getErin_oe_type() + "")) {
						setOetypeM(m);
						break;
					}
				}
			}
			oetypeList.add(0, new PubCodeConversionModel());

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Command("submit")
	public void submit(@BindingParam("win") Window win) {
		Integer row = 0;
		try {
			// 特殊字段处理
			fieldhandle();

			row = new Emrp_OperateDal().mod(epm);
			row += new EmReg_OperateDal().mod(erm);
			if (row > 0) {
				Messagebox.show("提交成功!", "INFORMATION", Messagebox.OK,
						Messagebox.INFORMATION);
				win.detach();
			} else {
				Messagebox.show("提交失败!", "ERROR", Messagebox.OK,
						Messagebox.ERROR);
			}
		} catch (Exception e) {
			e.printStackTrace();
			Messagebox.show("提交出错!", "ERROR", Messagebox.OK, Messagebox.ERROR);
		}
	}

	// 特殊字段处理
	public void fieldhandle() {
		// 居住证信息
		epm.setErpi_app_type(apptypeM.getPcco_code() == null ? null : Integer
				.parseInt(apptypeM.getPcco_code()));
		epm.setErpi_app_con(appconM.getPcco_code() == null ? null : Integer
				.parseInt(appconM.getPcco_code()));

		// 就业登记信息
		erm.setErin_skilllevel(skillM.getPcco_cn());
		erm.setErin_skilllevelcode(skillM.getPcco_code());
		erm.setErin_come_sz_reason(comeszM.getPcco_code() == null ? null
				: Integer.parseInt(comeszM.getPcco_code()));
		erm.setErin_house_class(houseM.getPcco_code() == null ? null : Integer
				.parseInt(houseM.getPcco_code()));
		erm.setErin_house_mode(housemodeM.getPcco_code() == null ? null
				: Integer.parseInt(housemodeM.getPcco_code()));
		erm.setErin_oe_type(oetypeM.getPcco_code() == null ? null : Integer
				.parseInt(oetypeM.getPcco_code()));

		erm.setErin_come_sz_date(come_sz_date == null ? null : sdf
				.format(come_sz_date));
		erm.setErin_r_date(r_date == null ? null : sdf.format(r_date));
	}

	public EmRegistrationInfoModel getErm() {
		return erm;
	}

	public void setErm(EmRegistrationInfoModel erm) {
		this.erm = erm;
	}

	public EmResidencePermitInfoModel getEpm() {
		return epm;
	}

	public void setEpm(EmResidencePermitInfoModel epm) {
		this.epm = epm;
	}

	public List<PubCodeConversionModel> getApptypeList() {
		return apptypeList;
	}

	public void setApptypeList(List<PubCodeConversionModel> apptypeList) {
		this.apptypeList = apptypeList;
	}

	public PubCodeConversionModel getApptypeM() {
		return apptypeM;
	}

	public void setApptypeM(PubCodeConversionModel apptypeM) {
		this.apptypeM = apptypeM;
	}

	public List<PubCodeConversionModel> getAppconList() {
		return appconList;
	}

	public void setAppconList(List<PubCodeConversionModel> appconList) {
		this.appconList = appconList;
	}

	public PubCodeConversionModel getAppconM() {
		return appconM;
	}

	public void setAppconM(PubCodeConversionModel appconM) {
		this.appconM = appconM;
	}

	public List<PubCodeConversionModel> getOetypeList() {
		return oetypeList;
	}

	public void setOetypeList(List<PubCodeConversionModel> oetypeList) {
		this.oetypeList = oetypeList;
	}

	public PubCodeConversionModel getOetypeM() {
		return oetypeM;
	}

	public void setOetypeM(PubCodeConversionModel oetypeM) {
		this.oetypeM = oetypeM;
	}

	public List<PubCodeConversionModel> getSkillList() {
		return skillList;
	}

	public void setSkillList(List<PubCodeConversionModel> skillList) {
		this.skillList = skillList;
	}

	public PubCodeConversionModel getSkillM() {
		return skillM;
	}

	public void setSkillM(PubCodeConversionModel skillM) {
		this.skillM = skillM;
	}

	public List<PubCodeConversionModel> getComeszList() {
		return comeszList;
	}

	public void setComeszList(List<PubCodeConversionModel> comeszList) {
		this.comeszList = comeszList;
	}

	public PubCodeConversionModel getComeszM() {
		return comeszM;
	}

	public void setComeszM(PubCodeConversionModel comeszM) {
		this.comeszM = comeszM;
	}

	public List<PubCodeConversionModel> getHouseList() {
		return houseList;
	}

	public void setHouseList(List<PubCodeConversionModel> houseList) {
		this.houseList = houseList;
	}

	public PubCodeConversionModel getHouseM() {
		return houseM;
	}

	public void setHouseM(PubCodeConversionModel houseM) {
		this.houseM = houseM;
	}

	public List<PubCodeConversionModel> getHousemodeList() {
		return housemodeList;
	}

	public void setHousemodeList(List<PubCodeConversionModel> housemodeList) {
		this.housemodeList = housemodeList;
	}

	public PubCodeConversionModel getHousemodeM() {
		return housemodeM;
	}

	public void setHousemodeM(PubCodeConversionModel housemodeM) {
		this.housemodeM = housemodeM;
	}

	public Date getCome_sz_date() {
		return come_sz_date;
	}

	public void setCome_sz_date(Date come_sz_date) {
		this.come_sz_date = come_sz_date;
	}

	public Date getR_date() {
		return r_date;
	}

	public void setR_date(Date r_date) {
		this.r_date = r_date;
	}
}
