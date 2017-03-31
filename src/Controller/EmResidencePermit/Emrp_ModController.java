package Controller.EmResidencePermit;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.zkoss.bind.annotation.BindingParam;
import org.zkoss.bind.annotation.Command;
import org.zkoss.bind.annotation.NotifyChange;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zul.Grid;
import org.zkoss.zul.ListModelList;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Window;

import dal.EmReg.EmReg_OperateDal;
import dal.EmResidencePermit.Emrp_OperateDal;

import bll.EmReg.EmReg_ListBll;
import bll.EmResidencePermit.Emrp_ListBll;
import bll.EmResidencePermit.Emrp_OperateBll;

import Controller.DocumentsInfo.DocumentsInfo_OperationController;
import Model.EmRegistrationInfoModel;
import Model.EmResidencePermitInfoModel;
import Model.EmbaseModel;
import Model.HandoverPeopleModel;
import Model.PubCodeConversionModel;
import Model.PubFolkModel;
import Model.WorkClassInfoModel;

public class Emrp_ModController {
	Integer daid;

	private EmRegistrationInfoModel erm = new EmRegistrationInfoModel();
	private EmResidencePermitInfoModel epm = new EmResidencePermitInfoModel();

	// 下拉列表绑定
	private List<PubFolkModel> folkList;// 民族
	private PubFolkModel folkM = new PubFolkModel();
	private List<PubCodeConversionModel> eduList;// 文化程度
	private PubCodeConversionModel eduM = new PubCodeConversionModel();
	private List<PubCodeConversionModel> marList;// 婚姻状况
	private PubCodeConversionModel marM = new PubCodeConversionModel();
	private List<PubCodeConversionModel> partyList;// 政治面貌
	private PubCodeConversionModel parM = new PubCodeConversionModel();
	private List<PubCodeConversionModel> hjList;// 户籍类型
	private PubCodeConversionModel hjM = new PubCodeConversionModel();
	private List<PubCodeConversionModel> titleList;// 职称
	private PubCodeConversionModel titleM = new PubCodeConversionModel();
	private List<WorkClassInfoModel> wcinList;// 工种
	private WorkClassInfoModel wcinM = new WorkClassInfoModel();

	private List<PubCodeConversionModel> payList;// 付款性质
	private List<PubCodeConversionModel> feeList;// 收费状态
	private PubCodeConversionModel feeM = new PubCodeConversionModel();
	private List<HandoverPeopleModel> handList;// 交接人

	// 特殊字段
	private String is_parttime = "否";// 是否兼职
	private String if_unlimited = "否";// 无固定劳动期限
	private Date compact_s_date;// 劳动合同开始日期
	private Date compact_e_date;// 劳动合同结束日期
	private Date worktime;// 参加工作时间
	private Date unit_b_date;// 本单位工作起始日

	private String if_invoice = "是";// 是否需要发票
	private String dw_entering = "否";// 是否无需录入信息

	private boolean vis1;

	SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
	private EmbaseModel emmodel=new EmbaseModel();

	public Emrp_ModController() {
		try {
			Emrp_ListBll bll = new Emrp_ListBll();
			EmReg_ListBll bll1 = new EmReg_ListBll();
			daid = Integer.parseInt(Executions.getCurrent().getArg()
					.get("daid").toString());
			
			setEpm(bll.getEmrpInfo(daid));
			setErm(bll1.getEmRegInfo(epm.getErin_id(), ""));
			emmodel=bll1.getEmBaseByGid(epm.getGid());
			// 获取下拉列表
			getComboList();
			if(emmodel.getEmba_folk()!=null&&erm.getErin_folk()==null)
			{
				erm.setErin_folk(emmodel.getEmba_folk());
			}
			// 特殊字段初始化
			fieldinit();

			Opvis1();

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
			if(erm.getErin_is_parttime() !=null)
			{
				is_parttime = erm.getErin_is_parttime() == 1 ? "是" : "否";
			}
			if(erm.getErin_if_unlimited() !=null)
			{
				if_unlimited = erm.getErin_if_unlimited() == 1 ? "是" : "否";
			}
			if(epm.getErpi_if_invoice() !=null)
			{
				if_invoice = epm.getErpi_if_invoice() == 1 ? "是" : "否";
			}
			if(epm.getErpi_dw_entering()!=null)
			{
				dw_entering = epm.getErpi_dw_entering() == 1 ? "是" : "否";
			}
			if(erm.getErin_compact_s_date() !=null)
			{
				compact_s_date = erm.getErin_compact_s_date() == null ? null : sdf
					.parse(erm.getErin_compact_s_date());
			}
			if(erm.getErin_compact_e_date() !=null)
			{
				compact_e_date = erm.getErin_compact_e_date() == null ? null : sdf
					.parse(erm.getErin_compact_e_date());
			}
			if(erm.getErin_worktime() !=null)
			{
				worktime = erm.getErin_worktime() == null ? null : sdf.parse(erm
					.getErin_worktime());
			}
			if(erm.getErin_unit_b_date() !=null)
			{
				unit_b_date = erm.getErin_unit_b_date() == null ? null : sdf
					.parse(erm.getErin_unit_b_date());
			}
			epm.setErpi_w_photo_number(true);
			epm.setErpi_w_skill_level(true);
			epm.setErpi_w_a_sz_date(true);
			epm.setErpi_w_r_date(true);
			epm.setErpi_w_house_class(true);
			epm.setErpi_w_r_mode(true);
		} catch (Exception e) {
			e.printStackTrace();
			Messagebox.show("初始化数据出错!", "ERROR", Messagebox.OK,
					Messagebox.ERROR);
		}
	}

	// 获取下拉列表
	public void getComboList() {
		try {
			EmReg_ListBll bll = new EmReg_ListBll();

			setWcinList(new ListModelList<>(bll.getWcinList()));
			if (erm.getErin_wcin() != null && !erm.getErin_wcin().isEmpty()) {
				for (WorkClassInfoModel m : wcinList) {
					if (m.getWcin_name().equals(erm.getErin_wcin())) {
						setWcinM(m);
						break;
					}
				}
			}
			wcinList.add(0, new WorkClassInfoModel());

			setFolkList(new ListModelList<>(bll.getFolkList()));
			if (erm.getErin_folk() != null && !erm.getErin_folk().isEmpty()) {
				for (PubFolkModel m : folkList) {
					if (m.getPufo_name().equals(erm.getErin_folk())) {
						setFolkM(m);
						break;
					}
				}
			}
			folkList.add(0, new PubFolkModel());

			setEduList(new ListModelList<>(
					bll.getPubCodeList(" and pucl_id=16 and pcco_name='文化程度'")));
			if (erm.getErin_education() != null
					&& !erm.getErin_education().isEmpty()) {
				for (PubCodeConversionModel m : eduList) {
					if (m.getPcco_cn().equals(erm.getErin_education())) {
						setEduM(m);
						break;
					}
				}
			}
			eduList.add(0, new PubCodeConversionModel());

			setMarList(new ListModelList<>(
					bll.getPubCodeList(" and pucl_id=16 and pcco_name='婚姻状况'")));
			if (erm.getErin_marital() != null
					&& !erm.getErin_marital().isEmpty()) {
				for (PubCodeConversionModel m : marList) {
					if (m.getPcco_cn().equals(erm.getErin_marital())) {
						setMarM(m);
						break;
					}
				}
			}
			marList.add(0, new PubCodeConversionModel());

			setPartyList(new ListModelList<>(
					bll.getPubCodeList(" and pucl_id=16 and pcco_name='政治面貌'")));
			if (erm.getErin_party() != null && !erm.getErin_party().isEmpty()) {
				for (PubCodeConversionModel m : partyList) {
					if (m.getPcco_cn().equals(erm.getErin_party())) {
						setParM(m);
						break;
					}
				}
			}
			partyList.add(0, new PubCodeConversionModel());

			setHjList(new ListModelList<>(
					bll.getPubCodeList(" and pucl_id=16 and pcco_name='户籍类型' and pcco_code<>10")));
			if (erm.getErin_hjtype() != null && !erm.getErin_hjtype().isEmpty()) {
				for (PubCodeConversionModel m : hjList) {
					if (m.getPcco_cn().equals(erm.getErin_hjtype())) {
						setHjM(m);
						break;
					}
				}
			}
			hjList.add(0, new PubCodeConversionModel());

			setTitleList(new ListModelList<>(
					bll.getPubCodeList(" and pucl_id=16 and pcco_name='职称'")));
			if (erm.getErin_title() != null && !erm.getErin_title().isEmpty()) {
				for (PubCodeConversionModel m : titleList) {
					if (m.getPcco_cn().equals(erm.getErin_title())) {
						setTitleM(m);
						break;
					}
				}
			}
			titleList.add(0, new PubCodeConversionModel());

			setPayList(new ListModelList<>(
					bll.getPubCodeList(" and pucl_id=3 and pcco_name='付款性质'")));
			payList.add(0, new PubCodeConversionModel());

			setFeeList(new ListModelList<>(
					bll.getPubCodeList(" and pucl_id=3 and pcco_name='收费状态'")));
			if (epm.getErpi_fee_state() != null) {
				for (PubCodeConversionModel m : feeList) {
					if (m.getPcco_code().equals(epm.getErpi_fee_state() + "")) {
						setFeeM(m);
						break;
					}
				}
			}
			feeList.add(0, new PubCodeConversionModel());

			setHandList(new ListModelList<>(bll.getHandoverList()));
			erm.setErin_handover_people(handList.get(0).getHape_username());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Command("Opvis1")
	@NotifyChange("vis1")
	public void Opvis1() {
		if (dw_entering.equals("是")) {
			vis1 = false;
		} else {
			vis1 = true;
		}
	}

	/**
	 * 提交
	 * 
	 * @param win
	 * @param a
	 *            1、暂存;2、提交
	 */
	@Command("submit")
	public void submit(@BindingParam("win") Window win,
			@BindingParam("a") Integer a,@BindingParam("gd") final Grid gd) {
		Integer row = 0;
		DocumentsInfo_OperationController docOC = new DocumentsInfo_OperationController();
		try {
			docOC.UpsubmitDoc(gd,daid.toString());
		} catch (Exception e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		if (a == 1) {
			try {
				row = save();

				if (row > 0) {
					Messagebox.show("保存成功!", "INFORMATION", Messagebox.OK,
							Messagebox.INFORMATION);
					win.detach();
				} else {
					Messagebox.show("保存失败!", "ERROR", Messagebox.OK,
							Messagebox.ERROR);
				}
			} catch (Exception e) {
				Messagebox.show("保存出错!", "ERROR", Messagebox.OK,
						Messagebox.ERROR);
			}
		} else if (a == 2) {
			if ((epm.isErpi_w_photo_number() && epm.isErpi_w_a_sz_date()
					&& epm.isErpi_w_house_class() && epm.isErpi_w_r_date()
					&& epm.isErpi_w_r_mode() && epm.isErpi_w_skill_level() && dw_entering
						.equals("否")) || dw_entering.equals("是")) {
				try {

					fieldhandle();

					epm.setErpi_state(2);

					String[] str = new Emrp_OperateBll().UpdateState(epm, erm,
							new Grid());

					if (str[0].equals("1")) {
						Messagebox.show(str[1], "INFORMATION", Messagebox.OK,
								Messagebox.INFORMATION);
						win.detach();
					} else {
						Messagebox.show(str[1], "ERROR", Messagebox.OK,
								Messagebox.ERROR);
					}
				} catch (Exception e) {
					Messagebox.show("提交出错!", "ERROR", Messagebox.OK,
							Messagebox.ERROR);
				}
			} else {
				try {
					row = save();

					if (row > 0) {
						if (Messagebox.show("资料不全，无法提交下一步，保存成功!\n是否关闭窗口?",
								"QUESTION", Messagebox.OK | Messagebox.CANCEL,
								Messagebox.QUESTION) == Messagebox.OK) {
							win.detach();
						}
					} else {
						Messagebox.show("保存失败!", "ERROR", Messagebox.OK,
								Messagebox.ERROR);
					}
				} catch (Exception e) {
					Messagebox.show("保存出错!", "ERROR", Messagebox.OK,
							Messagebox.ERROR);
				}
			}
		}
	}

	// 保存
	public Integer save() {
		Integer row = 0;

		fieldhandle();

		row = new Emrp_OperateDal().mod(epm);
		row += new EmReg_OperateDal().mod(erm);

		return row;
	}

	/**
	 * 提交处理特殊字段
	 * 
	 */
	public void fieldhandle() {
		epm.setErpi_fee_state(feeM.getPcco_code() == null ? null : Integer
				.parseInt(feeM.getPcco_code()));
		epm.setErpi_if_invoice(if_invoice.equals("否") ? 0 : 1);
		epm.setErpi_dw_entering(dw_entering.equals("否") ? 0 : 1);

		erm.setErin_folkcode(folkM.getPufo_id() == null ? null : folkM
				.getPufo_id() + "");
		erm.setErin_folk(folkM.getPufo_name());
		erm.setErin_education(eduM.getPcco_cn());
		erm.setErin_educationcode(eduM.getPcco_code());
		erm.setErin_maritalcode(marM.getPcco_code());
		erm.setErin_marital(marM.getPcco_cn());
		erm.setErin_partycode(parM.getPcco_code());
		erm.setErin_party(parM.getPcco_cn());
		erm.setErin_hjtype(hjM.getPcco_cn());
		erm.setErin_hjtypecode(hjM.getPcco_code());
		erm.setErin_title(titleM.getPcco_cn());
		erm.setErin_titlecode(titleM.getPcco_code());
		erm.setErin_wcin(wcinM.getWcin_name());
		erm.setErin_wcin_code(wcinM.getWcin_code());
		erm.setErin_is_parttime(is_parttime.equals("否") ? 0 : 1);
		erm.setErin_if_unlimited(if_unlimited.equals("否") ? 0 : 1);

		erm.setErin_compact_s_date(compact_s_date == null ? null : sdf
				.format(compact_s_date));
		erm.setErin_compact_e_date(compact_e_date == null ? null : sdf
				.format(compact_e_date));
		erm.setErin_worktime(worktime == null ? null : sdf.format(worktime));
		erm.setErin_unit_b_date(unit_b_date == null ? null : sdf
				.format(unit_b_date));
	}
	
	/*************弹出联系员工页面************************/
	@Command
	public void link(@BindingParam("win") Window win)
	{
		Map map=new HashMap<>();
		map.put("id",daid);
		map.put("table", "EmResidencePermitInfo");
		Window window=(Window)Executions.createComponents("../EmReg/EmReg_Contact.zul", null, map);
		window.doModal();
	}
	
	/*****************退回**********************************/
	@Command
	public void back(@BindingParam("win") Window win)
	{
		String url = "../EmResidencePermit/Emrp_CenterBack.zul";
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("daid", daid);
		map.put("flag", "0");
		Window window = (Window) Executions.createComponents(url, null, map);
		window.doModal();
		if (map.get("flag").equals("1")) {
			win.detach();
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

	public List<PubFolkModel> getFolkList() {
		return folkList;
	}

	public void setFolkList(List<PubFolkModel> folkList) {
		this.folkList = folkList;
	}

	public PubFolkModel getFolkM() {
		return folkM;
	}

	public void setFolkM(PubFolkModel folkM) {
		this.folkM = folkM;
	}

	public List<PubCodeConversionModel> getEduList() {
		return eduList;
	}

	public void setEduList(List<PubCodeConversionModel> eduList) {
		this.eduList = eduList;
	}

	public PubCodeConversionModel getEduM() {
		return eduM;
	}

	public void setEduM(PubCodeConversionModel eduM) {
		this.eduM = eduM;
	}

	public List<PubCodeConversionModel> getMarList() {
		return marList;
	}

	public void setMarList(List<PubCodeConversionModel> marList) {
		this.marList = marList;
	}

	public PubCodeConversionModel getMarM() {
		return marM;
	}

	public void setMarM(PubCodeConversionModel marM) {
		this.marM = marM;
	}

	public List<PubCodeConversionModel> getPartyList() {
		return partyList;
	}

	public void setPartyList(List<PubCodeConversionModel> partyList) {
		this.partyList = partyList;
	}

	public PubCodeConversionModel getParM() {
		return parM;
	}

	public void setParM(PubCodeConversionModel parM) {
		this.parM = parM;
	}

	public List<PubCodeConversionModel> getHjList() {
		return hjList;
	}

	public void setHjList(List<PubCodeConversionModel> hjList) {
		this.hjList = hjList;
	}

	public PubCodeConversionModel getHjM() {
		return hjM;
	}

	public void setHjM(PubCodeConversionModel hjM) {
		this.hjM = hjM;
	}

	public List<PubCodeConversionModel> getTitleList() {
		return titleList;
	}

	public void setTitleList(List<PubCodeConversionModel> titleList) {
		this.titleList = titleList;
	}

	public PubCodeConversionModel getTitleM() {
		return titleM;
	}

	public void setTitleM(PubCodeConversionModel titleM) {
		this.titleM = titleM;
	}

	public List<PubCodeConversionModel> getPayList() {
		return payList;
	}

	public void setPayList(List<PubCodeConversionModel> payList) {
		this.payList = payList;
	}

	public List<PubCodeConversionModel> getFeeList() {
		return feeList;
	}

	public void setFeeList(List<PubCodeConversionModel> feeList) {
		this.feeList = feeList;
	}

	public PubCodeConversionModel getFeeM() {
		return feeM;
	}

	public void setFeeM(PubCodeConversionModel feeM) {
		this.feeM = feeM;
	}

	public List<HandoverPeopleModel> getHandList() {
		return handList;
	}

	public void setHandList(List<HandoverPeopleModel> handList) {
		this.handList = handList;
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

	public Date getCompact_s_date() {
		return compact_s_date;
	}

	public void setCompact_s_date(Date compact_s_date) {
		this.compact_s_date = compact_s_date;
	}

	public Date getCompact_e_date() {
		return compact_e_date;
	}

	public void setCompact_e_date(Date compact_e_date) {
		this.compact_e_date = compact_e_date;
	}

	public Date getWorktime() {
		return worktime;
	}

	public void setWorktime(Date worktime) {
		this.worktime = worktime;
	}

	public String getIf_invoice() {
		return if_invoice;
	}

	public void setIf_invoice(String if_invoice) {
		this.if_invoice = if_invoice;
	}

	public Date getUnit_b_date() {
		return unit_b_date;
	}

	public void setUnit_b_date(Date unit_b_date) {
		this.unit_b_date = unit_b_date;
	}

	public List<WorkClassInfoModel> getWcinList() {
		return wcinList;
	}

	public void setWcinList(List<WorkClassInfoModel> wcinList) {
		this.wcinList = wcinList;
	}

	public WorkClassInfoModel getWcinM() {
		return wcinM;
	}

	public void setWcinM(WorkClassInfoModel wcinM) {
		this.wcinM = wcinM;
	}

	public String getDw_entering() {
		return dw_entering;
	}

	public void setDw_entering(String dw_entering) {
		this.dw_entering = dw_entering;
	}

	public boolean isVis1() {
		return vis1;
	}

	public void setVis1(boolean vis1) {
		this.vis1 = vis1;
	}

	public EmbaseModel getEmmodel() {
		return emmodel;
	}

	public void setEmmodel(EmbaseModel emmodel) {
		this.emmodel = emmodel;
	}

	public Integer getDaid() {
		return daid;
	}

	public void setDaid(Integer daid) {
		this.daid = daid;
	}
	
}
