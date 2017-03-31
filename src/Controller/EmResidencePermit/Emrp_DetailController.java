package Controller.EmResidencePermit;

import java.text.SimpleDateFormat;
import java.util.List;

import org.zkoss.zk.ui.Executions;
import org.zkoss.zul.Messagebox;

import bll.EmReg.EmReg_ListBll;
import bll.EmResidencePermit.Emrp_ListBll;

import Model.EmResidencePermitInfoModel;
import Model.EmbaseModel;

import Model.EmRegistrationInfoModel;

public class Emrp_DetailController {

	// 页面传值
	Integer daid = 0;
	String role = "";// hd 后道 qd 前道

	private EmRegistrationInfoModel erm = new EmRegistrationInfoModel();
	private EmResidencePermitInfoModel epm = new EmResidencePermitInfoModel();
	private List<EmResidencePermitInfoModel> hosList;

	// 特殊字段
	private String is_parttime;// 是否兼职
	private String if_unlimited;// 无固定劳动期限
	private String if_invoice;// 是否需要发票

	SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
	private EmbaseModel emmodel=new EmbaseModel();
	public Emrp_DetailController() {
		try {
			Emrp_ListBll bll = new Emrp_ListBll();

			daid = Integer.parseInt(Executions.getCurrent().getArg()
					.get("daid").toString());
			role = Executions.getCurrent().getArg().get("role").toString();

			setEpm(bll.getEmrpInfoModel(daid));
			setErm(new EmReg_ListBll().getEmRegInfo(epm.getErin_id(), ""));
			emmodel=new EmReg_ListBll().getEmBaseByGid(epm.getGid());
			if (role.equals("qd")) {
				setHosList(bll.getStateRelList(
						" and typename='前道状态' and state=1", daid));
			}
			else
			{
				setHosList(bll.getStateRelList(
						" and typename='后道状态' and state=1", daid));
			}
			
			if(emmodel.getEmba_folk()!=null&&erm.getErin_folk()==null)
			{
				erm.setErin_folk(emmodel.getEmba_folk());
			}
			fieldinit();

		} catch (Exception e) {
			e.printStackTrace();
			Messagebox
					.show("页面加载出错!", "ERROR", Messagebox.OK, Messagebox.ERROR);
		}
	}

	/**
	 * 特殊字段初始化
	 * 
	 */
	public void fieldinit() {
		try {
			if(erm.getErin_is_parttime()!=null)
			{
				setIs_parttime(erm.getErin_is_parttime() == 1 ? "是" : "否");
			}
			if(erm.getErin_if_unlimited()!=null)
			{
				setIf_unlimited(erm.getErin_if_unlimited() == 1 ? "是" : "否");
			}
			if(epm.getErpi_if_invoice()!=null)
			{
				setIf_invoice(epm.getErpi_if_invoice() == 1 ? "是" : "否");
			}
		} catch (Exception e) {
			e.printStackTrace();
			Messagebox.show("初始化数据出错!", "ERROR", Messagebox.OK,
					Messagebox.ERROR);
		}
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

	public String getIs_parttime() {
		return is_parttime;
	}

	public void setIs_parttime(String is_parttime) {
		this.is_parttime = is_parttime;
	}

	public String getIf_unlimited() {
		return if_unlimited;
	}

	public void setIf_unlimited(String if_unlimited) {
		this.if_unlimited = if_unlimited;
	}

	public String getIf_invoice() {
		return if_invoice;
	}

	public void setIf_invoice(String if_invoice) {
		this.if_invoice = if_invoice;
	}

	public List<EmResidencePermitInfoModel> getHosList() {
		return hosList;
	}

	public void setHosList(List<EmResidencePermitInfoModel> hosList) {
		this.hosList = hosList;
	}

	public EmbaseModel getEmmodel() {
		return emmodel;
	}

	public void setEmmodel(EmbaseModel emmodel) {
		this.emmodel = emmodel;
	}
	
}
