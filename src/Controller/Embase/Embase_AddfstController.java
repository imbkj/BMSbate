package Controller.Embase;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.zkoss.bind.annotation.BindingParam;
import org.zkoss.bind.annotation.Command;
import org.zkoss.bind.annotation.NotifyChange;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zul.Cell;
import org.zkoss.zul.Checkbox;
import org.zkoss.zul.Columns;
import org.zkoss.zul.Grid;
import org.zkoss.zul.ListModelList;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Rows;
import org.zkoss.zul.Window;

import com.sun.java.swing.plaf.windows.resources.windows;

import bll.CoServePromise.CoServePromiseSelectBll;
import bll.Embase.EmbaseLogin_AddBll;

import Model.CoBaseServePromiseModel;
import Model.EmBcSetupAddressModel;
import Model.EmBcSetupModel;
import Model.EmbaseModel;
import Model.PubCodeConversionModel;
import Util.DateStringChange;
import Util.EmailUtil;
import Util.IdcardUtil;
import Util.TelUtil;
import Util.UserInfo;

public class Embase_AddfstController {

	private EmbaseModel m = new EmbaseModel();

	/* 必填字段列表 */
	List<String> requiredList = new ArrayList<String>();

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
	private List<String> folkList;
 
	private ArrayList<String> sbrelationList = new ArrayList<String>();

	// 日期格式
	SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

	// 特殊字段
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
	
	private List<CoBaseServePromiseModel> list=new ArrayList<CoBaseServePromiseModel>();
	private CoServePromiseSelectBll copbll=new CoServePromiseSelectBll();

	// 获取页面传值
	int cid = 5304;
	String company = "";
	
	


	// 初始化
	public Embase_AddfstController() {
		try {
			cid = Integer.parseInt(Executions.getCurrent().getArg().get("cid")
					.toString());
			company = Executions.getCurrent().getArg().get("company")
					.toString();
		} catch (Exception e) {

		}
		
		//无雇员服务中心约定的不能做入职
		
		
		list=copbll.getPromiseList("and cid="+cid);
		
		
		try {
			sbrelationList.add("");
			sbrelationList.add("配偶");
			sbrelationList.add("子女");
			
			EmbaseLogin_AddBll bll = new EmbaseLogin_AddBll();

			setDegreeList(new ListModelList<PubCodeConversionModel>(
					bll.getPubCodeList("文化程度")));
			degreeList.add(0, null);
			setPartyList(new ListModelList<PubCodeConversionModel>(
					bll.getPubCodeList("政治面貌")));
			partyList.add(0, null);
			setTitleList(new ListModelList<PubCodeConversionModel>(
					bll.getPubCodeList("职称")));
			titleList.add(0, null);
			setHousetypeList(new ListModelList<PubCodeConversionModel>(
					bll.getPubCodeList("住所类别")));
			housetypeList.add(0, null);
			setHouseclassList(new ListModelList<PubCodeConversionModel>(
					bll.getPubCodeList("居住方式")));
			houseclassList.add(0, null);
			setSkilllevelList(new ListModelList<PubCodeConversionModel>(
					bll.getPubCodeList("职业技能等级")));
			skilllevelList.add(0, null);
			setRegtypeList(new ListModelList<PubCodeConversionModel>(
					bll.getPubCodeList("就业类型")));
			regtypeList.add(0, null);
			setBcsetupList(new ListModelList<EmBcSetupModel>(
					bll.getBcSetupList()));
			bcsetupList.add(0, null);
			setBcsetupaddList(new ListModelList<EmBcSetupAddressModel>(
					bll.getBcSetupAddressList()));
			setFolkList(new ListModelList<String>(bll.getFolkList()));
			m.setEmba_service_place("本地");
			m.setEmba_idcardclass("身份证");

		} catch (Exception e) {
			Messagebox.show("页面加载失败,请联系IT部门!", "ERROR", Messagebox.OK,
					Messagebox.ERROR);
			e.printStackTrace();
		}
	}

	
	//创建Window时检查该公司是否已有约束
	@Command
	public void createwin(@BindingParam("win") Window win)
	{
		
		if (list.size()<=0) {
			Messagebox.show("请先在公司业务中心添加此公司的雇员服务中心约定!", "提示",Messagebox.OK, Messagebox.INFORMATION);
			win.detach();
		}
	}
	// 全选
	@Command("allcheck")
	public void allcheck(@BindingParam("ck1") Checkbox emba_sex,
			@BindingParam("ck2") Checkbox emba_folk,
			@BindingParam("ck3") Checkbox emba_native,
			@BindingParam("ck4") Checkbox emba_idcard,
			@BindingParam("ck5") Checkbox emba_birth,
			@BindingParam("ck6") Checkbox emba_marital,
			@BindingParam("ck7") Checkbox emba_school,
			@BindingParam("ck8") Checkbox emba_specialty,
			@BindingParam("ck9") Checkbox emba_graduation,
			@BindingParam("ck10") Checkbox emba_degree,
			@BindingParam("ck11") Checkbox emba_hjadd,
			@BindingParam("ck12") Checkbox emba_hjtype,
			@BindingParam("ck13") Checkbox emba_address,
			@BindingParam("ck14") Checkbox emba_phone,
			@BindingParam("ck15") Checkbox emba_mobile,
			@BindingParam("ck16") Checkbox emba_ep,
			@BindingParam("ck17") Checkbox emba_email,
			@BindingParam("ck18") Checkbox emba_party,
			@BindingParam("ck19") Checkbox emba_status,
			
			@BindingParam("checked") boolean checked) {
		
		emba_sex.setChecked(checked);reqcheck(emba_sex); 
		 
		emba_folk.setChecked(checked);reqcheck(emba_folk); 
		emba_native.setChecked(checked);reqcheck(emba_native);
		emba_idcard.setChecked(checked);reqcheck(emba_idcard);
		emba_birth.setChecked(checked);reqcheck(emba_birth);
		emba_marital.setChecked(checked);reqcheck(emba_marital);
		emba_school.setChecked(checked);reqcheck(emba_school);
		emba_specialty.setChecked(checked);reqcheck(emba_specialty);
		emba_graduation.setChecked(checked);reqcheck(emba_graduation);
		emba_degree.setChecked(checked);reqcheck(emba_degree);
		emba_hjadd.setChecked(checked);reqcheck(emba_hjadd);
		emba_hjtype.setChecked(checked);reqcheck(emba_hjtype);
		emba_address.setChecked(checked);reqcheck(emba_address);
		emba_phone.setChecked(checked);reqcheck(emba_phone);
		emba_mobile.setChecked(checked);reqcheck(emba_mobile);
		emba_ep.setChecked(checked);reqcheck(emba_ep);
		emba_email.setChecked(checked);reqcheck(emba_email);
		emba_party.setChecked(checked);reqcheck(emba_party);
		emba_status.setChecked(checked);reqcheck(emba_status);
	
	}
	
	// 全选
	@Command("allcheckda")
	public void allcheckda(@BindingParam("ck1") Checkbox emba_fileplace,
			@BindingParam("ck2") Checkbox emba_fileinclass,
			@BindingParam("ck3") Checkbox emba_filereason,
			@BindingParam("ck4") Checkbox emba_filedebts,
			@BindingParam("ck5") Checkbox emba_filedebtsmonth,
			@BindingParam("ck6") Checkbox emba_filehj,
			 
			
			@BindingParam("checked") boolean checked) {
		
		emba_fileplace.setChecked(checked);reqcheck(emba_fileplace);
		emba_fileinclass.setChecked(checked);reqcheck(emba_fileinclass);
		emba_filereason.setChecked(checked);reqcheck(emba_filereason);
		emba_filedebts.setChecked(checked);reqcheck(emba_filedebts);
		emba_filedebtsmonth.setChecked(checked);reqcheck(emba_filedebtsmonth);
		emba_filehj.setChecked(checked);reqcheck(emba_filehj);
	 
		
		

	}
	
	// 全选
	@Command("allchecksb")
	public void allchecksb(@BindingParam("ck1") Checkbox emba_ifcomputerid,
			@BindingParam("ck2") Checkbox emba_computerid,
			@BindingParam("ck3") Checkbox emba_hand,
			@BindingParam("ck4") Checkbox emba_sbcard,
			@BindingParam("checked") boolean checked) {
		
		emba_ifcomputerid.setChecked(checked);reqcheck(emba_ifcomputerid);
		emba_computerid.setChecked(checked);reqcheck(emba_computerid);
		emba_hand.setChecked(checked);reqcheck(emba_hand);
		emba_sbcard.setChecked(checked);reqcheck(emba_sbcard);
	
	}
	
	// 全选
	@Command("allcheckzf")
	public void allcheckzf(@BindingParam("ck1") Checkbox emba_ifhouse,
			@BindingParam("ck2") Checkbox emba_houseid,
			@BindingParam("ck3") Checkbox emba_excompanystate,
			@BindingParam("ck4") Checkbox emba_excompanyid,
			@BindingParam("ck5") Checkbox emba_excompany,
			@BindingParam("ck6") Checkbox emba_wifename,
			@BindingParam("ck7") Checkbox emba_wifeidcard,
			@BindingParam("checked") boolean checked) {
		
		emba_ifhouse.setChecked(checked);reqcheck(emba_ifhouse);
		emba_houseid.setChecked(checked);reqcheck(emba_houseid);
		emba_excompanystate.setChecked(checked);reqcheck(emba_excompanystate);
		emba_excompanyid.setChecked(checked);reqcheck(emba_excompanyid);
		emba_excompany.setChecked(checked);reqcheck(emba_excompany);
		emba_wifename.setChecked(checked);reqcheck(emba_wifename);
		emba_wifeidcard.setChecked(checked);reqcheck(emba_wifeidcard);
	
	
	}
	// 全选
	@Command("allcheckgz")
	public void allcheckgz(@BindingParam("ck1") Checkbox emba_gz_email,
			@BindingParam("ck2") Checkbox emba_gz_cemail,
			@BindingParam("ck3") Checkbox emba_gzbank,
		 
			@BindingParam("checked") boolean checked) {
		
		emba_gz_email.setChecked(checked);reqcheck(emba_gz_email);
		emba_gz_cemail.setChecked(checked);reqcheck(emba_gz_cemail);
		emba_gzbank.setChecked(checked);reqcheck(emba_gzbank);
	 
	
	}
	// 全选
	@Command("allcheckjy")
	public void allcheckjy(@BindingParam("ck1") Checkbox emba_housecode,
			@BindingParam("ck2") Checkbox emba_housetime,
			@BindingParam("ck3") Checkbox emba_housetype,
			@BindingParam("ck4") Checkbox emba_houseclass,
			@BindingParam("ck5") Checkbox emba_title,
			@BindingParam("ck6") Checkbox emba_skilllevel,
			@BindingParam("ck7") Checkbox emba_worktime,
			@BindingParam("ck8") Checkbox emba_hjtime,
			@BindingParam("ck9") Checkbox emba_regtype,
			@BindingParam("ck10") Checkbox emba_compactlimit,
			@BindingParam("ck11") Checkbox emba_compactstart,
			@BindingParam("ck12") Checkbox emba_compactend,
			@BindingParam("ck13") Checkbox emba_companystart,
			@BindingParam("ck14") Checkbox emba_station,
			@BindingParam("ck15") Checkbox emba_birthcontrol,
			@BindingParam("ck16") Checkbox emba_photonum,
			@BindingParam("ck17") Checkbox emba_sztime,
		 
			@BindingParam("checked") boolean checked) {
		
		emba_housecode.setChecked(checked);reqcheck(emba_housecode);
		emba_housetime.setChecked(checked);reqcheck(emba_housetime);
		emba_housetype.setChecked(checked);reqcheck(emba_housetype);
		emba_houseclass.setChecked(checked);reqcheck(emba_houseclass);
		emba_title.setChecked(checked);reqcheck(emba_title);
		emba_skilllevel.setChecked(checked);reqcheck(emba_skilllevel);
		emba_worktime.setChecked(checked);reqcheck(emba_worktime);
		emba_hjtime.setChecked(checked);reqcheck(emba_hjtime);
		emba_regtype.setChecked(checked);reqcheck(emba_regtype);
		emba_compactlimit.setChecked(checked);reqcheck(emba_compactlimit);
		emba_compactstart.setChecked(checked);reqcheck(emba_compactstart);
		emba_compactend.setChecked(checked);reqcheck(emba_compactend);
		emba_companystart.setChecked(checked);reqcheck(emba_companystart);
		emba_station.setChecked(checked);reqcheck(emba_station);
		emba_birthcontrol.setChecked(checked);reqcheck(emba_birthcontrol);
		emba_photonum.setChecked(checked);reqcheck(emba_photonum);
		emba_sztime.setChecked(checked);reqcheck(emba_sztime);
	
	}
	
	
	// 全选
	@Command("allchecksybx")
	public void allchecksybx(@BindingParam("ck1") Checkbox emba_sbinfor,
			@BindingParam("ck2") Checkbox emba_sbbank,
		 
		 
			@BindingParam("checked") boolean checked) {
		
		emba_sbinfor.setChecked(checked);reqcheck(emba_sbinfor);
		emba_sbbank.setChecked(checked);reqcheck(emba_sbbank);
	 
	}
	
	// 全选
		@Command("allchecktj")
		public void allchecktj(@BindingParam("ck1") Checkbox emba_hospital,
				@BindingParam("ck2") Checkbox emba_bctime,
				@BindingParam("ck3") Checkbox emba_bcaddress,
			 
			 
				@BindingParam("checked") boolean checked) {
			
			emba_hospital.setChecked(checked);reqcheck(emba_hospital);
			emba_bctime.setChecked(checked);reqcheck(emba_bctime);
			emba_bcaddress.setChecked(checked);reqcheck(emba_bcaddress);
		 
			 
		}
		
		// 全选
		@Command("allcheckqt")
		public void allcheckqt(@BindingParam("ck1") Checkbox emba_workinfor ,
				@BindingParam("ck2") Checkbox emba_family,
			 
			 
				@BindingParam("checked") boolean checked) {
			
			emba_workinfor.setChecked(checked);reqcheck(emba_workinfor);
			emba_family.setChecked(checked);reqcheck(emba_family);
		 
			 
		}
		
	
	
	
	
	
	

	// 选择体检医院,级联绑定体检医院地址列表
	@Command("getbcadd")
	@NotifyChange({ "bcsetupaddList1", "m" })
	public void getbcadd() {
		bcsetupaddList1.clear();
		try {
			for (EmBcSetupAddressModel fm : bcsetupaddList) {
				if (bcsetupModel.getEbcs_id() == fm.getEbsa_ebcs_id()) {
					bcsetupaddList1.add(fm);
				}
			}
			if (bcsetupaddList1.size() > 0) {
				m.setEmba_bcaddress(bcsetupaddList1.get(0).getEbsa_address());
			} else {
				m.setEmba_bcaddress(null);
			}
		} catch (Exception e) {
			m.setEmba_bcaddress(null);
		}
	}

	// 获取必填字段
	@Command("reqcheck")
	public void reqcheck(@BindingParam("self") Checkbox ckb) {
		try {
			if (ckb.isChecked() && !requiredList.contains(ckb.getId())) {
				requiredList.add(ckb.getId());
			} else if (!ckb.isChecked() && requiredList.contains(ckb.getId())) {
				requiredList.remove(ckb.getId());
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 通过身份证获取生日，省份，性别等信息
	 * 
	 */
	@Command
	@NotifyChange({ "m", "birth" })
	public void idcardhandle() {
		try {
			if (m.getEmba_idcardclass().equals("身份证")) {
				if (m.getEmba_idcard() != null && !m.getEmba_idcard().isEmpty()) {
					// 检查身份证号码合法性
					if (IdcardUtil.validateCard(m.getEmba_idcard())) {
						String idCard = m.getEmba_idcard();

						birth = DateStringChange.StringtoDate(IdcardUtil
								.strtodateformat(IdcardUtil
										.getBirthByIdCard(idCard)),
								"yyyy-MM-dd");
						m.setEmba_sex(IdcardUtil.getGenderByIdCard(idCard));
						if (m.getEmba_native() == null
								|| m.getEmba_native().isEmpty()) {
							m.setEmba_native(IdcardUtil
									.getProvinceByIdCard(idCard));
						}
					} else {
						Messagebox.show("身份证不合法,请检查是否正确!", "ERROR",
								Messagebox.OK, Messagebox.ERROR);
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			Messagebox.show("错误：" + e.toString(), "ERROR", Messagebox.OK,
					Messagebox.ERROR);
		}
	}

	/**
	 * 根据是否有电脑号判断显示(电脑号、利手)
	 * 
	 */
	@Command
	@NotifyChange({ "m" })
	public void ifcomputerid(
			@BindingParam("computerid") Checkbox emba_computerid,
			@BindingParam("hand") Checkbox emba_hand) {
		try {
			if (m.getEmba_ifcomputerid().equals("是")) {
				m.setVis_computerid(true);
				emba_computerid.setChecked(true);
				m.setVis_hand(false);
				emba_hand.setChecked(false);
			} else if (m.getEmba_ifcomputerid().equals("否")) {
				m.setVis_computerid(false);
				emba_computerid.setChecked(false);
				m.setVis_hand(true);
				emba_hand.setChecked(true);
			} else {
				m.setVis_computerid(false);
				emba_computerid.setChecked(false);
				m.setVis_hand(false);
				emba_hand.setChecked(false);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 根据是否办理社保卡判断显示(通知员工)
	 * 
	 */
	@Command
	@NotifyChange({ "m" })
	public void issbcardnotice() {
		try {
			if (m.getEmba_sbcard().equals("是")) {
				m.setVis_sbcard_notice(true);
				m.setEmba_sbcard_notice(true);
			} else {
				m.setVis_sbcard_notice(false);
				m.setEmba_sbcard_notice(false);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 查询社保电脑号
	 * 
	 */
	@Command
	public void computerid_search() {
		try {
			if (m.getEmba_name() == null || m.getEmba_name().isEmpty()) {
				Messagebox.show("请输入姓名!", "ERROR", Messagebox.OK,
						Messagebox.ERROR);
			} else if (m.getEmba_idcard() == null
					|| m.getEmba_idcard().isEmpty()) {
				Messagebox.show("请输入身份证号码!", "ERROR", Messagebox.OK,
						Messagebox.ERROR);
			} else if (!IdcardUtil.validateCard(m.getEmba_idcard())) {
				Messagebox.show("身份证不合法,请检查是否正确!", "ERROR", Messagebox.OK,
						Messagebox.ERROR);
			} else {
				String url = "/Embase/Embase_Computerid_search.zul";
				String searurl = "http://dgciic:81/ComputeridSearch.aspx?idcard="
						+ m.getEmba_idcard();
				Map<String, Object> map = new HashMap<>();
				map.put("url", searurl);

				Window window = (Window) Executions.createComponents(url, null,
						map);
				window.doModal();
			}
		} catch (Exception e) {
			e.printStackTrace();
			Messagebox.show("错误：" + e.toString(), "ERROR", Messagebox.OK,
					Messagebox.ERROR);
		}
	}

	/**
	 * 检查邮箱格式
	 * 
	 * @param email
	 */
	@Command
	@NotifyChange({ "m" })
	public void checkEmailSimple(@BindingParam("email") String email) {
		try {
			if (email != null && !email.isEmpty()) {
				if (!EmailUtil.checkEmailSimple(email)) {
					Messagebox.show("邮箱格式错误,请重新填写!", "ERROR", Messagebox.OK,
							Messagebox.ERROR);
					m.setEmba_email(null);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 检查邮箱真实性
	 * 
	 * @param email
	 */
	@Command
	public void checkEmail(@BindingParam("email") String email) {
		try {
			if (email != null && !email.isEmpty()) {
				if (!EmailUtil.checkEmail(email)) {
					Messagebox.show("邮箱不存在!", "ERROR", Messagebox.OK,
							Messagebox.ERROR);
				} else {
					Messagebox.show("邮箱真实存在!", "INFORMATION", Messagebox.OK,
							Messagebox.INFORMATION);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 验证固定电话
	 * 
	 */
	@Command
	@NotifyChange({ "m" })
	public void checkPhone() {
		try {
			if (m.getEmba_phone() != null && !m.getEmba_phone().isEmpty()) {
				if (!TelUtil.isPhone(m.getEmba_phone())) {
					Messagebox.show("家庭电话格式错误,请重新填写!", "ERROR", Messagebox.OK,
							Messagebox.ERROR);
					m.setEmba_phone(null);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 验证手机号码
	 * 
	 */
	@Command
	@NotifyChange({ "m" })
	public void checkMobile() {
		try {
			if (m.getEmba_mobile() != null && !m.getEmba_mobile().isEmpty()) {
				if (!TelUtil.isMobile(m.getEmba_mobile())) {
					Messagebox.show("手机号码格式错误,请重新填写!", "ERROR", Messagebox.OK,
							Messagebox.ERROR);
					m.setEmba_mobile(null);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	// 提交
	@Command("submit")
	public void submit(@BindingParam("win") Window win) {
		if (m.getEmba_name() == null || m.getEmba_name().isEmpty()) {
			Messagebox.show("请输入姓名!", "ERROR", Messagebox.OK, Messagebox.ERROR);
		} else if ((m.getEmba_phone() == null || m.getEmba_phone().isEmpty())
				&& (m.getEmba_mobile() == null || m.getEmba_mobile().isEmpty())) {
			Messagebox.show("请输入至少一个联系电话!", "ERROR", Messagebox.OK,
					Messagebox.ERROR);
		} else {
			EmbaseLogin_AddBll bll = new EmbaseLogin_AddBll();

			try {
				m.setCid(cid);
				m.setEmba_addname(UserInfo.getUsername());
				m.setEmba_state(0);
				m.setEmba_form(0);//系统

				handle();
			} catch (Exception e) {
				Messagebox.show("数据处理失败,请联系IT部门!", "ERROR", Messagebox.OK,
						Messagebox.ERROR);
				e.printStackTrace();
			}

			try {

				String[] str = bll.EmbaseloginAdd(m, company, requiredList);

				if (str[0].equals("1")) {
					Messagebox.show(str[1], "INFORMATION", Messagebox.OK,
							Messagebox.INFORMATION);
					win.detach();
				} else {
					Messagebox.show(str[1], "ERROR", Messagebox.OK,
							Messagebox.ERROR);
				}
			} catch (Exception e) {
//				Messagebox.show("提交失败,请联系IT部门!", "ERROR", Messagebox.OK,
//						Messagebox.ERROR);
//				e.printStackTrace();
//				win.detach();
			}
		}
	}

	// 处理特殊字段
	public void handle() {
		m.setEmba_birth(birth != null ? sdf.format(birth) : null);
		m.setEmba_graduation(graduation != null ? sdf.format(graduation) : null);
		m.setEmba_degree(dgModel.getPcco_cn());
		m.setEmba_fileinclass(fileinclass.equals("是") ? 1 : 0);
		m.setEmba_filedebts(filedebts.equals("是") ? 1 : 0);
		m.setEmba_filehj(filehj.equals("是") ? 1 : 0);
		m.setEmba_excompanystate(excompanystate.equals("是") ? 1 : 0);
		m.setEmba_housetime(housetime != null ? sdf.format(housetime) : null);
		m.setEmba_worktime(worktime != null ? sdf.format(worktime) : null);
		m.setEmba_sztime(sztime != null ? sdf.format(sztime) : null);
		m.setEmba_hjtime(hjtime != null ? sdf.format(hjtime) : null);
		m.setEmba_compactstart(compactstart != null ? sdf.format(compactstart)
				: null);
		m.setEmba_compactend(compactend != null ? sdf.format(compactend) : null);
		m.setEmba_companystart(companystart != null ? sdf.format(companystart)
				: null);
		m.setEmba_bctime(bctime != null ? sdf.format(bctime) : null);
		m.setEmba_hospital(bcsetupModel.getEbcs_hospital());
	}

	public EmbaseModel getM() {
		return m;
	}

	public void setM(EmbaseModel m) {
		this.m = m;
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

	public List<PubCodeConversionModel> getDegreeList() {
		return degreeList;
	}

	public void setDegreeList(List<PubCodeConversionModel> degreeList) {
		this.degreeList = degreeList;
	}

	public PubCodeConversionModel getDgModel() {
		return dgModel;
	}

	public void setDgModel(PubCodeConversionModel dgModel) {
		this.dgModel = dgModel;
	}

	public List<PubCodeConversionModel> getPartyList() {
		return partyList;
	}

	public void setPartyList(List<PubCodeConversionModel> partyList) {
		this.partyList = partyList;
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

	public List<PubCodeConversionModel> getTitleList() {
		return titleList;
	}

	public void setTitleList(List<PubCodeConversionModel> titleList) {
		this.titleList = titleList;
	}

	public Date getHousetime() {
		return housetime;
	}

	public void setHousetime(Date housetime) {
		this.housetime = housetime;
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

	public List<PubCodeConversionModel> getRegtypeList() {
		return regtypeList;
	}

	public void setRegtypeList(List<PubCodeConversionModel> regtypeList) {
		this.regtypeList = regtypeList;
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

	public EmBcSetupModel getBcsetupModel() {
		return bcsetupModel;
	}

	public void setBcsetupModel(EmBcSetupModel bcsetupModel) {
		this.bcsetupModel = bcsetupModel;
	}

	public List<String> getFolkList() {
		return folkList;
	}

	public void setFolkList(List<String> folkList) {
		this.folkList = folkList;
	}


	public ArrayList<String> getSbrelationList() {
		return sbrelationList;
	}


	public void setSbrelationList(ArrayList<String> sbrelationList) {
		this.sbrelationList = sbrelationList;
	}
	
}
