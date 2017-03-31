package Controller.Embase;

import impl.CheckStringImpl;

import java.lang.reflect.InvocationTargetException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.poi.hssf.record.PageBreakRecord.Break;
import org.zkoss.bind.annotation.BindingParam;
import org.zkoss.bind.annotation.Command;
import org.zkoss.bind.annotation.NotifyChange;

import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.select.SelectorComposer;
import org.zkoss.zul.Combobox;
import org.zkoss.zul.Label;
import org.zkoss.zul.ListModelList;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Window;

import service.CheckStringService;

import bll.EmBodyCheck.EmBcInfo_SelectBll;
import bll.Embase.EmbaseLogin_AddBll;
import bll.SystemControl.SystLogInfoBll;
import bll.Taskflow.EmBaseMenulistBll;
import bll.Taskflow.EmBaseMenulistSelectBll;

import Model.CobasedepartmentModel;
import Model.EmBcSetupAddressModel;
import Model.EmBcSetupModel;
import Model.EmbaseBusinessCenterModel;
import Model.EmbaseModel;
import Model.PubCodeConversionModel;
import Model.SystLogModel;
import Model.cobasepositionModel;
import Util.CheckString;
import Util.EntitiesComparedUtils;
import Util.RedirectUtil;
import Util.UserInfo;

/* 必填字段列表 */

public class Embase_UpdateController {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private EmBaseMenulistSelectBll selectbll = new EmBaseMenulistSelectBll();
	private EmbaseModel emmodel = new EmbaseModel();

	private List<String> requiredList = new ArrayList<String>();
	private List<String> folkList;

	private Date birth = null;// 生日
	private Date graduation = null;// 毕业时间
	private PubCodeConversionModel dgModel = new PubCodeConversionModel();// 文化程度
	private String fileinclass = "";// 档案是否愿意转入中智托管
	private String filedebts = "";// 档案是否有欠费
	private String filehj = "";// 户口是否托管在人才
	private String sbcard = "";// 是否需要办理社保卡
	private String excompanystate = "";// 原单位是否已封存
	private Date housetime = null;// 入住时间
	private Date worktime = null;// 参加工作时间
	private Date sztime = null;// 来深日期
	private Date hjtime = null;// 户口进深日期
	private Date compactstart = null;// 劳动合同开始时间
	private Date compactend = null;// 劳动合同结束时间
	private Date companystart = null;// 本单位工作起始时间
	private Date bctime = null;// 体检时间
	private EmBcSetupModel bcsetupModel = new EmBcSetupModel();// 体检医院
	private List<EmbaseBusinessCenterModel> embulist = new ArrayList<EmbaseBusinessCenterModel>();
	/* 下拉列表绑定 */
	private List<PubCodeConversionModel> degreeList;
	private List<PubCodeConversionModel> partyList;
	private List<PubCodeConversionModel> titleList;
	private List<PubCodeConversionModel> housetypeList;
	private List<PubCodeConversionModel> houseclassList;
	private List<PubCodeConversionModel> skilllevelList;
	private List<PubCodeConversionModel> regtypeList;
	private List<EmBcSetupModel> bcsetupList;
	private List<EmBcSetupAddressModel> bcsetupaddList;
	private List<EmBcSetupAddressModel> bcsetupaddList1 = new ListModelList<EmBcSetupAddressModel>();
	private List<cobasepositionModel> csidList;// 职位
	private cobasepositionModel csidM = new cobasepositionModel();
	private List<CobasedepartmentModel> cpidList;// 部门
	private CobasedepartmentModel cpidM = new CobasedepartmentModel();
	// 日期格式
	private SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
	private int gid = 0;
	private ArrayList<String> sbrelationList = new ArrayList<String>();

	private EmbaseModel ebmold = new EmbaseModel();
	// private int cid=1196;
	/******************************* 分配业务End ************************************/

	public Embase_UpdateController() {

		sbrelationList.add("");
		sbrelationList.add("配偶");
		sbrelationList.add("子女");
		gid = (Integer) Executions.getCurrent().getArg().get("gid");

		List<EmbaseModel> embaselist = selectbll.getEmbaseLoginInfo(gid);
		if (!embaselist.isEmpty()) {
			emmodel = embaselist.get(0);
			// cid = emmodel.getCid();
			ebmold = (EmbaseModel) emmodel.clone();

			csidList = selectbll.getcsidList(" AND cid=" + emmodel.getCid()); // 职位
			cpidList = selectbll.getcpidList(" AND cid=" + emmodel.getCid());// 部门

			/******************************* 分配业务 ***************************************/

			EmbaseLogin_AddBll bllbase = new EmbaseLogin_AddBll();
			setDegreeList(new ListModelList<PubCodeConversionModel>(
					bllbase.getPubCodeList("文化程度")));
			degreeList.add(0, new PubCodeConversionModel());
			setPartyList(new ListModelList<PubCodeConversionModel>(
					bllbase.getPubCodeList("政治面貌")));
			partyList.add(0, new PubCodeConversionModel());
			setTitleList(new ListModelList<PubCodeConversionModel>(
					bllbase.getPubCodeList("职称")));
			titleList.add(0, new PubCodeConversionModel());
			setHousetypeList(new ListModelList<PubCodeConversionModel>(
					bllbase.getPubCodeList("住所类别")));
			housetypeList.add(0, new PubCodeConversionModel());
			setHouseclassList(new ListModelList<PubCodeConversionModel>(
					bllbase.getPubCodeList("居住方式")));
			houseclassList.add(0, new PubCodeConversionModel());
			setSkilllevelList(new ListModelList<PubCodeConversionModel>(
					bllbase.getPubCodeList("职业技能等级")));
			skilllevelList.add(0, new PubCodeConversionModel());
			setRegtypeList(new ListModelList<PubCodeConversionModel>(
					bllbase.getPubCodeList("就业类型")));
			regtypeList.add(0, new PubCodeConversionModel());
			setBcsetupList(new ListModelList<EmBcSetupModel>(
					bllbase.getBcSetupList()));
			bcsetupList.add(0, new EmBcSetupModel());
			setBcsetupaddList(new ListModelList<EmBcSetupAddressModel>(
					bllbase.getBcSetupAddressList()));
			setFolkList(new ListModelList<String>(bllbase.getFolkList()));
			folkList.add(0, "");
			fieldinit();

			/******************************* 分配业务End ************************************/

		}

	}

	/******************************* 分配业务 ***************************************/
	// "*"初始化
	@Command("LabelInit")
	public void LabelInit(@BindingParam("self") Label lbl) {
		try {
			if (requiredList.contains(lbl.getId())) {
				lbl.setVisible(true);
			}
		} catch (Exception e) {
			// TODO: handle exception
		}
	}

	// 特殊字段初始化
	public void fieldinit() {
		try {
			birth = emmodel.getEmba_birth() != null ? sdf.parse(emmodel
					.getEmba_birth()) : null;
			graduation = emmodel.getEmba_graduation() != null ? sdf
					.parse(emmodel.getEmba_graduation()) : null;
			for (int i = 0; i < degreeList.size(); i++) {
				if (degreeList.get(i).getPcco_cn() != null) {
					if (degreeList.get(i).getPcco_cn()
							.equals(emmodel.getEmba_degree())) {
						dgModel = degreeList.get(i);
					}
				}
			}
			fileinclass = (emmodel.getEmba_fileinclass() == null || emmodel
					.getEmba_fileinclass() == 0) ? "否" : "是";
			filedebts = (emmodel.getEmba_filedebts() == null || emmodel
					.getEmba_filedebts() == 0) ? "否" : "是";
			filehj = (emmodel.getEmba_filehj() == null || emmodel
					.getEmba_filehj() == 0) ? "否" : "是";
			sbcard = (emmodel.getEmba_sbcard() == null
					|| emmodel.getEmba_sbcard() == "否" || emmodel
					.getEmba_sbcard() == "0") ? "否" : "是";
			excompanystate = (emmodel.getEmba_excompanystate() == null || emmodel
					.getEmba_excompanystate() == 0) ? "否" : "是";
			housetime = emmodel.getEmba_housetime() != null ? sdf.parse(emmodel
					.getEmba_housetime()) : null;
			worktime = emmodel.getEmba_worktime() != null ? sdf.parse(emmodel
					.getEmba_worktime()) : null;
			sztime = emmodel.getEmba_sztime() != null ? sdf.parse(emmodel
					.getEmba_sztime()) : null;
			hjtime = emmodel.getEmba_hjtime() != null ? sdf.parse(emmodel
					.getEmba_hjtime()) : null;
			compactstart = emmodel.getEmba_compactstart() != null ? sdf
					.parse(emmodel.getEmba_compactstart()) : null;
			compactend = emmodel.getEmba_compactend() != null ? sdf
					.parse(emmodel.getEmba_compactend()) : null;
			companystart = emmodel.getEmba_companystart() != null ? sdf
					.parse(emmodel.getEmba_companystart()) : null;
			bctime = emmodel.getEmba_bctime() != null ? sdf.parse(emmodel
					.getEmba_bctime()) : null;
			for (int i = 0; i < bcsetupList.size(); i++) {
				if (bcsetupList.get(i).getEbcs_hospital() != null) {
					if (bcsetupList.get(i).getEbcs_hospital()
							.equals(emmodel.getEmba_hospital())) {
						// bcsetupModel = bcsetupList.get(i);
					}
				}
			}
			if (bcsetupModel != null) {
				EmBcSetupAddressModel ebam = new EmBcSetupAddressModel();
				ebam.setEbsa_ebcs_id(bcsetupModel.getEbcs_id());
				ebam.setEbsa_state(1);
				EmBcInfo_SelectBll selectbll = new EmBcInfo_SelectBll();
				bcsetupaddList1 = selectbll.getSetUpAddress(ebam);
			}

			if (emmodel.getEmba_csid() != null) {
				for (int i = 0; i < csidList.size(); i++) {
					if (csidList.get(i).getCoba_positionid() == emmodel
							.getEmba_csid()) {
						csidM = csidList.get(i);
					}
				}
			}

			if (emmodel.getEmba_cpid() != null) {
				for (int i = 0; i < cpidList.size(); i++) {
					if (cpidList.get(i).getCoba_pid() == emmodel.getEmba_cpid()) {
						cpidM = cpidList.get(i);
					}
				}
			}

		} catch (Exception e) {
			Messagebox.show("字段加载失败,请联系IT部门!", "ERROR", Messagebox.OK,
					Messagebox.ERROR);
			System.out.println(e.toString());
		}
	}

	// 提交
	@Command("save")
	public void save() throws Exception {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

		if (emmodel.getEmba_sbbirth1_d() != null
				&& !emmodel.getEmba_sbbirth1_d().equals("")) {
			emmodel.setEmba_sbbirth1(sdf.format(emmodel.getEmba_sbbirth1_d()));
		}
		if (emmodel.getEmba_sbbirth2_d() != null
				&& !emmodel.getEmba_sbbirth2_d().equals("")) {
			emmodel.setEmba_sbbirth2(sdf.format(emmodel.getEmba_sbbirth2_d()));
		}
		if (emmodel.getEmba_sbbirth3_d() != null
				&& !emmodel.getEmba_sbbirth3_d().equals("")) {
			emmodel.setEmba_sbbirth3(sdf.format(emmodel.getEmba_sbbirth3_d()));
		}
		if (emmodel.getEmba_sbbirth4_d() != null
				&& !emmodel.getEmba_sbbirth4_d().equals("")) {
			emmodel.setEmba_sbbirth4(sdf.format(emmodel.getEmba_sbbirth4_d()));
		}

		Integer pageChk = 1;
		CheckStringService ch = new CheckStringImpl();
		if (emmodel.getEmba_mobile() != null
				&& !ch.isNum(emmodel.getEmba_mobile().toString())) {

			Messagebox.show("手机号码格式有误！", "提示", Messagebox.OK,
					Messagebox.INFORMATION);
			pageChk = 0;
			return;
		} else if (!"".equals(emmodel.getEmba_gz_account())
				&& emmodel.getEmba_gz_account() != null) {
			if (CheckString.isNum(emmodel.getEmba_gz_account()) == false) {
				Messagebox.show("工资银行账户只能为数字，且不能有空格！", "提示", Messagebox.OK,
						Messagebox.INFORMATION);
				pageChk = 0;
				return;
			}
		} else if (!"".equals(emmodel.getEmba_writeoff_account())
				&& emmodel.getEmba_writeoff_account() != null) {
			if (CheckString.isNum(emmodel.getEmba_writeoff_account()) == false) {
				Messagebox.show("报销银行账户只能为数字，且不能有空格！", "提示", Messagebox.OK,
						Messagebox.INFORMATION);
				pageChk = 0;
				return;
			}
		}

		if (pageChk == 1) {

			String str = updateInfo();
			if (str.equals("")) {
				Messagebox.show("提交成功", "提示", Messagebox.OK,
						Messagebox.INFORMATION);
				// win.detach();
			} else {
				Messagebox.show(str, "提示", Messagebox.OK,
						Messagebox.INFORMATION);
			}
		}
	}

	// 打开详情页面
	@Command("embaseinfo")
	public void embaseinfo(@BindingParam("model") EmbaseModel em) {
		// Map map = new HashMap<>();
		// map.put("m", em);
		// Window window;
		// window = (Window) Executions.createComponents("Embase_yfzxinfo.zul",
		// null,
		// map);
		// window.doModal();

		RedirectUtil u = new RedirectUtil();

		u.indexAddTab("/Embase/Embase_yfzxinfo.zul?gid=" + emmodel.getGid()
				+ "", "雇员服务中心");

	}

	private String updateInfo() {
		String strs = "";
		try {
			handle();
		} catch (Exception e) {
			// TODO: handle exception
			strs = "数据处理失败,请联系IT部门!";
			System.out.println(e.toString());
		}
		try {
			// 修改
			EmBaseMenulistBll uppbll = new EmBaseMenulistBll();
			// emmodel.setCid(cid);
			int kid = uppbll.EmbaseUpdate(emmodel);
			

			SystLogModel logm = new SystLogModel();
			SystLogInfoBll logBll = new SystLogInfoBll();
			String strlog = "";
			try {
				strlog = EntitiesComparedUtils
						.OldToNewReflect(ebmold, emmodel);

			} catch (NoSuchMethodException
					| IllegalAccessException
					| IllegalArgumentException
					| InvocationTargetException e) {
				e.printStackTrace();
			}
			logm.setContent(strlog); // 修改内容或新增内容
			logm.setAddname(UserInfo.getUsername());
			// m.setIP(ip);
			logm.setGID(emmodel.getGid().toString());
			logm.setClass1("员工信息");
			logBll.addSystLog(logm);
			
			
			if (kid > 0) {
				if (embulist.size() > 0) {
					uppbll.EmbaseMenuListdelete(gid);
					for (int i = 0; i < embulist.size(); i++) {
						uppbll.EmbaseMenuListAdd(gid, embulist.get(i)
								.getEmce_id());
					}
				}
			} else {
				strs = "提交失败";
			}
		} catch (Exception e) {
			// TODO: handle exception
			strs = "提交失败,请联系IT部门!";
			System.out.println(e.toString());
		}
		return strs;
	}

	// 处理特殊字段
	public void handle() {

		emmodel.setEmba_birth(birth != null ? sdf.format(birth) : null);
		emmodel.setEmba_graduation(graduation != null ? sdf.format(graduation)
				: null);
		emmodel.setEmba_degree(dgModel.getPcco_cn());
		emmodel.setEmba_fileinclass(fileinclass.equals("是") ? 1 : 0);
		emmodel.setEmba_filedebts(filedebts.equals("是") ? 1 : 0);
		emmodel.setEmba_filehj(filehj.equals("是") ? 1 : 0);
		emmodel.setEmba_sbcard(sbcard.equals("是") ? "是" : "否");
		emmodel.setEmba_excompanystate(excompanystate.equals("是") ? 1 : 0);
		emmodel.setEmba_housetime(housetime != null ? sdf.format(housetime)
				: null);
		emmodel.setEmba_worktime(worktime != null ? sdf.format(worktime) : null);
		emmodel.setEmba_sztime(sztime != null ? sdf.format(sztime) : null);
		emmodel.setEmba_hjtime(hjtime != null ? sdf.format(hjtime) : null);
		emmodel.setEmba_compactstart(compactstart != null ? sdf
				.format(compactstart) : null);
		emmodel.setEmba_compactend(compactend != null ? sdf.format(compactend)
				: null);
		emmodel.setEmba_companystart(companystart != null ? sdf
				.format(companystart) : null);
		emmodel.setEmba_bctime(bctime != null ? sdf.format(bctime) : null);
		emmodel.setEmba_hospital(bcsetupModel.getEbcs_hospital());
		emmodel.setEmba_csid(csidM.getCoba_positionid());
		emmodel.setEmba_cpid(cpidM.getCoba_pid());

	}

	public Integer getGid() {
		return gid;
	}

	public void setGid(Integer gid) {
		this.gid = gid;
	}

	// 体检医院选择事件
	@Command
	@NotifyChange("bcsetupaddList1")
	public void getbcadd(@BindingParam("hospital") String hospital,
			@BindingParam("c") Combobox c) {
		if (hospital != null && !hospital.equals("")) {
			Integer ebcs_id = c.getSelectedItem().getValue();
			EmBcSetupAddressModel ebam = new EmBcSetupAddressModel();
			ebam.setEbsa_ebcs_id(ebcs_id);
			ebam.setEbsa_state(1);
			EmBcInfo_SelectBll selectbll = new EmBcInfo_SelectBll();
			bcsetupaddList1 = selectbll.getSetUpAddress(ebam);
		}
	}

	// public Integer getCid() {
	// return cid;
	// }
	//
	// public void setCid(Integer cid) {
	// this.cid = cid;
	// }
	//
	
	
	// 打开短信页面
	@Command("openmobile")
	public void openmobile()
			throws InterruptedException {
		Map map = new HashMap<>();
		map.put("mobile", emmodel.getEmba_mobile());
		map.put("gid", emmodel.getGid());
		Window window;
		window = (Window) Executions.createComponents("SMS_Add.zul", null, map);
		window.doModal();
	}

	public List<String> getFolkList() {
		return folkList;
	}

	public void setFolkList(List<String> folkList) {
		this.folkList = folkList;
	}

	public EmbaseModel getEmmodel() {
		return emmodel;
	}

	public void setEmmodel(EmbaseModel emmodel) {
		this.emmodel = emmodel;
	}

	public Date getBirth() {
		return birth;
	}

	public void setBirth(Date birth) {
		this.birth = birth;
	}

	public Date getGraduation() {
		return graduation;
	}

	public void setGraduation(Date graduation) {
		this.graduation = graduation;
	}

	public PubCodeConversionModel getDgModel() {
		return dgModel;
	}

	public void setDgModel(PubCodeConversionModel dgModel) {
		this.dgModel = dgModel;
	}

	public String getFileinclass() {
		return fileinclass;
	}

	public void setFileinclass(String fileinclass) {
		this.fileinclass = fileinclass;
	}

	public String getFiledebts() {
		return filedebts;
	}

	public void setFiledebts(String filedebts) {
		this.filedebts = filedebts;
	}

	public String getFilehj() {
		return filehj;
	}

	public void setFilehj(String filehj) {
		this.filehj = filehj;
	}

	public String getSbcard() {
		return sbcard;
	}

	public void setSbcard(String sbcard) {
		this.sbcard = sbcard;
	}

	public String getExcompanystate() {
		return excompanystate;
	}

	public void setExcompanystate(String excompanystate) {
		this.excompanystate = excompanystate;
	}

	public Date getHousetime() {
		return housetime;
	}

	public void setHousetime(Date housetime) {
		this.housetime = housetime;
	}

	public Date getWorktime() {
		return worktime;
	}

	public void setWorktime(Date worktime) {
		this.worktime = worktime;
	}

	public Date getSztime() {
		return sztime;
	}

	public void setSztime(Date sztime) {
		this.sztime = sztime;
	}

	public Date getHjtime() {
		return hjtime;
	}

	public void setHjtime(Date hjtime) {
		this.hjtime = hjtime;
	}

	public Date getCompactstart() {
		return compactstart;
	}

	public void setCompactstart(Date compactstart) {
		this.compactstart = compactstart;
	}

	public Date getCompactend() {
		return compactend;
	}

	public void setCompactend(Date compactend) {
		this.compactend = compactend;
	}

	public Date getCompanystart() {
		return companystart;
	}

	public void setCompanystart(Date companystart) {
		this.companystart = companystart;
	}

	public Date getBctime() {
		return bctime;
	}

	public void setBctime(Date bctime) {
		this.bctime = bctime;
	}

	public EmBcSetupModel getBcsetupModel() {
		return bcsetupModel;
	}

	public void setBcsetupModel(EmBcSetupModel bcsetupModel) {
		this.bcsetupModel = bcsetupModel;
	}

	public List<String> getRequiredList() {
		return requiredList;
	}

	public void setRequiredList(List<String> requiredList) {
		this.requiredList = requiredList;
	}

	public List<PubCodeConversionModel> getDegreeList() {
		return degreeList;
	}

	public void setDegreeList(List<PubCodeConversionModel> degreeList) {
		this.degreeList = degreeList;
	}

	public List<PubCodeConversionModel> getPartyList() {
		return partyList;
	}

	public void setPartyList(List<PubCodeConversionModel> partyList) {
		this.partyList = partyList;
	}

	public List<PubCodeConversionModel> getTitleList() {
		return titleList;
	}

	public void setTitleList(List<PubCodeConversionModel> titleList) {
		this.titleList = titleList;
	}

	public List<PubCodeConversionModel> getHousetypeList() {
		return housetypeList;
	}

	public void setHousetypeList(List<PubCodeConversionModel> housetypeList) {
		this.housetypeList = housetypeList;
	}

	public List<PubCodeConversionModel> getHouseclassList() {
		return houseclassList;
	}

	public void setHouseclassList(List<PubCodeConversionModel> houseclassList) {
		this.houseclassList = houseclassList;
	}

	public List<PubCodeConversionModel> getSkilllevelList() {
		return skilllevelList;
	}

	public void setSkilllevelList(List<PubCodeConversionModel> skilllevelList) {
		this.skilllevelList = skilllevelList;
	}

	public List<PubCodeConversionModel> getRegtypeList() {
		return regtypeList;
	}

	public void setRegtypeList(List<PubCodeConversionModel> regtypeList) {
		this.regtypeList = regtypeList;
	}

	public List<EmBcSetupModel> getBcsetupList() {
		return bcsetupList;
	}

	public void setBcsetupList(List<EmBcSetupModel> bcsetupList) {
		this.bcsetupList = bcsetupList;
	}

	public List<EmBcSetupAddressModel> getBcsetupaddList() {
		return bcsetupaddList;
	}

	public void setBcsetupaddList(List<EmBcSetupAddressModel> bcsetupaddList) {
		this.bcsetupaddList = bcsetupaddList;
	}

	public List<EmBcSetupAddressModel> getBcsetupaddList1() {
		return bcsetupaddList1;
	}

	public void setBcsetupaddList1(List<EmBcSetupAddressModel> bcsetupaddList1) {
		this.bcsetupaddList1 = bcsetupaddList1;
	}

	public List<cobasepositionModel> getCsidList() {
		return csidList;
	}

	public void setCsidList(List<cobasepositionModel> csidList) {
		this.csidList = csidList;
	}

	public List<CobasedepartmentModel> getCpidList() {
		return cpidList;
	}

	public void setCpidList(List<CobasedepartmentModel> cpidList) {
		this.cpidList = cpidList;
	}

	public cobasepositionModel getCsidM() {
		return csidM;
	}

	public void setCsidM(cobasepositionModel csidM) {
		this.csidM = csidM;
	}

	public CobasedepartmentModel getCpidM() {
		return cpidM;
	}

	public void setCpidM(CobasedepartmentModel cpidM) {
		this.cpidM = cpidM;
	}

	public ArrayList<String> getSbrelationList() {
		return sbrelationList;
	}

	public void setSbrelationList(ArrayList<String> sbrelationList) {
		this.sbrelationList = sbrelationList;
	}

}
