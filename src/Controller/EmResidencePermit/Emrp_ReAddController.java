package Controller.EmResidencePermit;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.zkoss.bind.BindUtils;
import org.zkoss.bind.annotation.BindingParam;
import org.zkoss.bind.annotation.Command;
import org.zkoss.bind.annotation.NotifyChange;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zul.ListModelList;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Window;

import Model.EmRegistrationInfoModel;
import Model.EmResidencePermitInfoModel;
import Model.EmbaseModel;
import Model.HandoverPeopleModel;
import Model.PubCodeConversionModel;
import Model.PubFolkModel;
import Util.UserInfo;
import bll.EmReg.EmReg_ListBll;
import bll.EmResidencePermit.Emrp_ListBll;
import bll.EmResidencePermit.Emrp_OperateBll;

public class Emrp_ReAddController {
	Integer gid;
	Integer erin_id;

	private EmRegistrationInfoModel erm = new EmRegistrationInfoModel();
	private EmRegistrationInfoModel olderm = new EmRegistrationInfoModel();
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

	private List<PubCodeConversionModel> payList;// 付款性质
	private List<PubCodeConversionModel> feeList;// 收费状态
	private PubCodeConversionModel feeM = new PubCodeConversionModel();
	private List<HandoverPeopleModel> handList;// 交接人
	private EmbaseModel emml = new EmbaseModel();
	// 特殊字段
	private String is_parttime = "否";// 是否兼职
	private String if_unlimited = "否";// 无固定劳动期限
	private Date compact_s_date;// 劳动合同开始日期
	private Date compact_e_date;// 劳动合同结束日期
	private Date worktime;// 参加工作时间

	private String if_invoice = "是";// 是否需要发票

	SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
	private EmResidencePermitInfoModel pm;
	
	private Emrp_ListBll bl = new Emrp_ListBll();
	private boolean editvis=false;
	
	private String ifedit="";

	public Emrp_ReAddController() {
		try {
			EmReg_ListBll bll1 = new EmReg_ListBll();
			gid = Integer.parseInt(Executions.getCurrent().getArg().get("gid")
					.toString());
			try {
				if (Executions.getCurrent().getArg().get("erin_id") != null) {
					erin_id = Integer.parseInt(Executions.getCurrent().getArg()
							.get("erin_id").toString());
				} else {

					pm = bl.getEmResidencePermitInfo(gid);
					if (pm.getErpi_id() != null) {
						erin_id = pm.getErpi_id();
					} else {
						erin_id = 0;
					}
				}
			} catch (Exception e) {
				erin_id = 0;
			}

			if (erin_id == 0) {
				// 无就业登记数据
				setErm(bll1.getEmbaseInfo(gid));
				erm.setErin_t_kind("补办");
				erm.setErin_id(0);
			} else {
				// 已有就业登记数据
				setErm(bll1.getEmRegInfo(erin_id, ""));
			}
			epm.setErpi_t_kind("补办");
			epm.setOwnmonth(new SimpleDateFormat("yyyyMM").format(new Date()));

			// 获取下拉列表
			getComboList();
			EmReg_ListBll ssbll = new EmReg_ListBll();
			emml = ssbll.getEmbaseModel(gid);
			init();
			olderm = (EmRegistrationInfoModel) erm.clone();
		} catch (Exception e) {
			e.printStackTrace();
			Messagebox
					.show("页面加载出错!", "ERROR", Messagebox.OK, Messagebox.ERROR);
		}
	}

	// 创建页面时判断是否已有居住证信息
	@Command
	public void createwin(@BindingParam("win") Window win) {
		if (erin_id != null && erin_id != 0) {
			String sqlstr = " and erpi_t_kind='新办' and erpi_state<10 ";
			EmResidencePermitInfoModel pmmodel = bl.getResidencePermit(gid,
					sqlstr);
			if (pmmodel.getErpi_id() != null) {
				Messagebox.show("该员工的居住证新办数据还没有完成!", "ERROR", Messagebox.OK,
						Messagebox.ERROR);
				Map map = new HashMap<>();
				map.put("url", "/EmHouseCard/CardInfoList.zul");// 跳转的页面连接
				BindUtils.postGlobalCommand(null, null, "refreshEmUrl", map);
			}
		} else {
			Map map = new HashMap<>();
			map.put("url", "/EmResidencePermit/Emrp_Add.zul");// 跳转的页面连接
			BindUtils.postGlobalCommand(null, null, "refreshEmUrl", map);
		}
	}

	// 初始化数据
	private void init() {
		if (erm.getErin_nowadd() == null || erm.getErin_nowadd().equals("")) {
			erm.setErin_nowadd(emml.getEmba_address());
		}
		if (compact_s_date == null) {
			if (emml.getEbco_incept_date() != null
					&& !emml.getEbco_incept_date().equals("")) {
				compact_s_date = getFormatDate(emml.getEbco_incept_date());
			}
		}
		if (compact_e_date == null) {
			if (emml.getEbco_maturity_date() != null
					&& !emml.getEbco_maturity_date().equals("")) {
				compact_e_date = getFormatDate(emml.getEbco_maturity_date());
			}
		}
		if (erm.getErin_computerid() == null
				|| erm.getErin_computerid().equals("")) {
			erm.setErin_computerid(emml.getEmsc_computerid());
		}
		if (erm.getErin_compact_kind() == null
				|| erm.getErin_compact_kind().equals("")) {
			erm.setErin_compact_kind(emml.getEbco_change());
		}
	}

	// 获取下拉列表
	public void getComboList() {
		try {
			EmReg_ListBll bll = new EmReg_ListBll();

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
			feeList.add(0, new PubCodeConversionModel());

			setHandList(new ListModelList<>(bll.getHandoverList()));
			erm.setErin_handover_people(handList.get(0).getHape_username());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Command("submit")
	public void submit(@BindingParam("win") Window win) {
		try {
			fieldhandle();
			// 判断那些字段已修改
			String remark=edit();
			if(epm.getErpi_remark()!=null&&!epm.getErpi_remark().equals(""))
			{
				remark=epm.getErpi_remark()+"，"+remark;
			}
			epm.setErpi_remark(remark);
			String[] str = new Emrp_OperateBll().add(epm, erm);

			if (str[0].equals("1")) {
				Messagebox.show(str[1], "INFORMATION", Messagebox.OK,
						Messagebox.INFORMATION);
				Map map = new HashMap<>();
				map.put("url", "/EmHouseCard/CardInfoList.zul");// 跳转的页面连接
				BindUtils.postGlobalCommand(null, null, "refreshEmUrl", map);
			} else {
				Messagebox.show(str[1], "ERROR", Messagebox.OK,
						Messagebox.ERROR);
			}
		} catch (Exception e) {
			e.printStackTrace();
			Messagebox.show("提交出错!", "ERROR", Messagebox.OK, Messagebox.ERROR);
		}
	}
	
	//是否需要修改信息选择事件
	@Command
	@NotifyChange("editvis")
	public void editvischange()
	{
		if(epm.getErpi_ifedit()!=null)
		{
			if(epm.getErpi_ifedit().equals("是"))
			{
				editvis=true;
			}
			else
			{
				editvis=false;
			}
		}
	}

	private String edit() {
		String remark = "";
		if ((olderm.getErin_folk() == null && erm.getErin_folk() != null)
				|| (olderm.getErin_folk() != null && erm.getErin_folk() == null)) {
			remark = remark + "民族从“" + olderm.getErin_folk() + "”改为：“"
					+ erm.getErin_folk() + "”;";
		} else if (olderm.getErin_folk() != null && erm.getErin_folk() != null
				&& !olderm.getErin_folk().equals(erm.getErin_folk())) {
			remark = remark + "民族从“" + olderm.getErin_folk() + "”改为：“"
					+ erm.getErin_folk() + "”;";
		}
		
		if ((olderm.getErin_education() == null && erm.getErin_education() != null)
				|| (olderm.getErin_education() != null && erm.getErin_education() == null)) {
			remark = remark + "文化程度从“" + olderm.getErin_education() + "”改为：“"
					+ erm.getErin_education() + "”;";
		} else if (olderm.getErin_education() != null && erm.getErin_education() != null
				&& !olderm.getErin_education().equals(erm.getErin_education())) {
			remark = remark + "文化程度从“" + olderm.getErin_education() + "”改为：“"
					+ erm.getErin_education() + "”;";
		}
		
		if ((olderm.getErin_party() == null && erm.getErin_party() != null)
				|| (olderm.getErin_party() != null && erm.getErin_party() == null)) {
			remark = remark + "政治面貌从“" + olderm.getErin_party() + "”改为：“"
					+ erm.getErin_party() + "”;";
		} else if (olderm.getErin_party() != null && erm.getErin_party() != null
				&& !olderm.getErin_party().equals(erm.getErin_party())) {
			remark = remark + "政治面貌从“" + olderm.getErin_party() + "”改为：“"
					+ erm.getErin_party() + "”;";
		}
		
		if ((olderm.getErin_marital() == null && erm.getErin_marital() != null)
				|| (olderm.getErin_marital() != null && erm.getErin_marital() == null)) {
			remark = remark + "婚姻状况从“" + olderm.getErin_marital() + "”改为：“"
					+ erm.getErin_marital() + "”;";
		} else if (olderm.getErin_marital() != null && erm.getErin_marital() != null
				&& !olderm.getErin_marital().equals(erm.getErin_marital())) {
			remark = remark + "婚姻状况从“" + olderm.getErin_marital() + "”改为：“"
					+ erm.getErin_marital() + "”;";
		}
		
		if ((olderm.getErin_hjtype() == null && erm.getErin_hjtype() != null)
				|| (olderm.getErin_hjtype() != null && erm.getErin_hjtype() == null)) {
			remark = remark + "户籍类型从“" + olderm.getErin_hjtype() + "”改为：“"
					+ erm.getErin_hjtype() + "”;";
		} else if (olderm.getErin_hjtype() != null && erm.getErin_hjtype() != null
				&& !olderm.getErin_hjtype().equals(erm.getErin_hjtype())) {
			remark = remark + "户籍类型从“" + olderm.getErin_hjtype() + "”改为：“"
					+ erm.getErin_hjtype() + "”;";
		}
		
		if ((olderm.getErin_hjadd() == null && erm.getErin_hjadd() != null)
				|| (olderm.getErin_hjadd() != null && erm.getErin_hjadd() == null)) {
			remark = remark + "户籍地址从“" + olderm.getErin_hjadd() + "”改为：“"
					+ erm.getErin_hjadd() + "”;";
		} else if (olderm.getErin_hjadd() != null && erm.getErin_hjadd() != null
				&& !olderm.getErin_hjadd().equals(erm.getErin_hjadd())) {
			remark = remark + "户籍地址从“" + olderm.getErin_hjadd() + "”改为：“"
					+ erm.getErin_hjadd() + "”;";
		}
		
		if ((olderm.getErin_nowadd() == null && erm.getErin_nowadd() != null)
				|| (olderm.getErin_nowadd() != null && erm.getErin_nowadd() == null)) {
			remark = remark + "现居住地址地址从“" + olderm.getErin_nowadd() + "”改为：“"
					+ erm.getErin_nowadd() + "”;";
		} else if (olderm.getErin_nowadd() != null && erm.getErin_nowadd() != null
				&& !olderm.getErin_nowadd().equals(erm.getErin_nowadd())) {
			remark = remark + "现居住地址从“" + olderm.getErin_nowadd() + "”改为：“"
					+ erm.getErin_nowadd() + "”;";
		}
		
		if ((olderm.getErin_title() == null && erm.getErin_title() != null)
				|| (olderm.getErin_title() != null && erm.getErin_title() == null)) {
			remark = remark + "职称从“" + olderm.getErin_title() + "”改为：“"
					+ erm.getErin_title() + "”;";
		} else if (olderm.getErin_title() != null && erm.getErin_title() != null
				&& !olderm.getErin_title().equals(erm.getErin_title())) {
			remark = remark + "职称从“" + olderm.getErin_title() + "”改为：“"
					+ erm.getErin_title() + "”;";
		}
		
		if ((olderm.getErin_is_parttime() == null && erm.getErin_is_parttime() != null)
				|| (olderm.getErin_is_parttime() != null && erm.getErin_is_parttime() == null)) {
			remark = remark + "是否兼职从“" + olderm.getErin_is_parttime() + "”改为：“"
					+ erm.getErin_is_parttime() + "”;";
		} else if (olderm.getErin_is_parttime() != null && erm.getErin_is_parttime() != null
				&& !olderm.getErin_is_parttime().equals(erm.getErin_is_parttime())) {
			remark = remark + "是否兼职从“" + olderm.getErin_is_parttime() + "”改为：“"
					+ erm.getErin_is_parttime() + "”;";
		}
		System.out.println("remark="+remark);
		return remark;
	}

	/**
	 * 提交处理特殊字段
	 * 
	 */
	public void fieldhandle() {
		epm.setCid(erm.getCid());
		epm.setGid(erm.getGid());
		epm.setErin_id(erin_id);
		epm.setErpi_reg_state(2);
		epm.setErpi_laststate(0);
		epm.setErpi_state(1);
		epm.setErpi_tzl_state(0);
		epm.setErpi_addname(UserInfo.getUsername());
		epm.setErpi_fee(20);
		epm.setErpi_fee_state(feeM.getPcco_code() == null ? null : Integer
				.parseInt(feeM.getPcco_code()));
		epm.setErpi_if_invoice(if_invoice.equals("否") ? 0 : 1);
		epm.setErpi_dw_entering(0);

		// 如果没有就业登记，新增一条就业登记新办
		if (erm.getErin_id()==null||erm.getErin_id() == 0) {
			erm.setErin_laststate(0);
			erm.setErin_state(1);
			erm.setErin_tzl_state(0);
			erm.setErin_reg_state(2);
			erm.setErin_addname(UserInfo.getUsername());
			erm.setOwnmonth(epm.getOwnmonth());
			erm.setErin_if_resident_con(0);
		}

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
		erm.setErin_is_parttime(is_parttime.equals("否") ? 0 : 1);
		erm.setErin_if_unlimited(if_unlimited.equals("否") ? 0 : 1);

		erm.setErin_compact_s_date(compact_s_date == null ? null : sdf
				.format(compact_s_date));
		erm.setErin_compact_e_date(compact_e_date == null ? null : sdf
				.format(compact_e_date));
		erm.setErin_worktime(worktime == null ? null : sdf.format(worktime));

	}

	public EmRegistrationInfoModel getErm() {
		return erm;
	}

	public void setErm(EmRegistrationInfoModel erm) {
		this.erm = erm;
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

	public EmResidencePermitInfoModel getEpm() {
		return epm;
	}

	public void setEpm(EmResidencePermitInfoModel epm) {
		this.epm = epm;
	}

	public List<PubCodeConversionModel> getPayList() {
		return payList;
	}

	public void setPayList(List<PubCodeConversionModel> payList) {
		this.payList = payList;
	}

	public String getIf_invoice() {
		return if_invoice;
	}

	public void setIf_invoice(String if_invoice) {
		this.if_invoice = if_invoice;
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
	
	public boolean isEditvis() {
		return editvis;
	}

	public void setEditvis(boolean editvis) {
		this.editvis = editvis;
	}
	
	public String getIfedit() {
		return ifedit;
	}

	public void setIfedit(String ifedit) {
		this.ifedit = ifedit;
	}

	/**
	 * 日期格式化，默认日期格式yyyy-MM-dd
	 * 
	 * @param date
	 * @return
	 */
	public Date getFormatDate(String date) {
		Date datestr = null;
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
		try {
			datestr = df.parse(date);
		} catch (Exception ex) {
			System.out.println(ex.getMessage());
		}
		return datestr;
	}
}
