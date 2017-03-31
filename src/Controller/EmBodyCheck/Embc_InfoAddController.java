package Controller.EmBodyCheck;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

import org.apache.xmlbeans.impl.xb.xmlschema.SpaceAttribute.Space;
import org.zkoss.bind.BindUtils;
import org.zkoss.bind.annotation.BindingParam;
import org.zkoss.bind.annotation.Command;
import org.zkoss.bind.annotation.NotifyChange;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.Path;
import org.zkoss.zk.ui.event.EventListener;
import org.zkoss.zul.Checkbox;
import org.zkoss.zul.Combobox;
import org.zkoss.zul.Datebox;
import org.zkoss.zul.East;
import org.zkoss.zul.Grid;
import org.zkoss.zul.ListModelList;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Textbox;
import org.zkoss.zul.Window;
import org.zkoss.zul.Messagebox.ClickEvent;

import Model.CoBaseModel;
import Model.EmBcItemGroupModel;
import Model.EmBcSetupAddressModel;
import Model.EmBcSetupModel;
import Model.EmBodyCheckItemModel;
import Model.EmBodyCheckModel;
import Model.EmbaseModel;
import Model.loginroleModel;
import Util.DateUtil;
import Util.UserInfo;
import bll.EmBodyCheck.EmBcInfo_OperateBll;
import bll.EmBodyCheck.EmBcInfo_SelectBll;

public class Embc_InfoAddController {

	private List<EmBcSetupModel> setuplist = new ListModelList<>();
	private List<String> listid = new ArrayList<String>();
	// 获取客服信息
	private List<loginroleModel> clientlist = new ListModelList<>();
	// 查询公司信息
	private List<CoBaseModel> cobaselist = new ArrayList<CoBaseModel>();
	// 组合信息
	private List<EmBcItemGroupModel> grouplist = new ArrayList<EmBcItemGroupModel>();
	// 体检项目信息
	private List<EmBodyCheckItemModel> itemlist = new ArrayList<EmBodyCheckItemModel>();
	// 员工信息
	private List<EmbaseModel> embaselist = new ArrayList<EmbaseModel>();
	private List<EmbaseModel> embasesplist = new ArrayList<EmbaseModel>();
	// 机构地址
	private List<EmBcSetupAddressModel> addresslist = new ArrayList<EmBcSetupAddressModel>();
	// 已选项目列表
	private List<EmBodyCheckItemModel> siList = new ListModelList<>();
	// 员工体检信息列表
	private EmBodyCheckModel bcModel = new EmBodyCheckModel();

	private EmBcInfo_SelectBll selectbll = new EmBcInfo_SelectBll();
	private EmBcInfo_OperateBll opBll = new EmBcInfo_OperateBll();

	private Integer num = 0; // 已选员工人数
	private String username = UserInfo.getUsername();
	private boolean isopen = true;
	private String msg = "";

	private Window win = (Window) Path.getComponent("/winBc");

	private Combobox cbHospital;
	private Combobox cbAddress;
	private Combobox cbBcType;
	private Combobox cbBookType;
	private String address = "";
	private String bctype = "";
	private String booktype = "";

	private boolean rz = false;

	public Embc_InfoAddController() {
		loginroleModel lm = new loginroleModel();
		lm.setType(1);
		lm.setLog_inure(1);
		setClientlist(lm);
		EmBcSetupModel ebsm = new EmBcSetupModel();
		ebsm.setEbcs_state(1);
		setSetuplist(ebsm);
		EmbaseModel em = new EmbaseModel();
		embasesplist.add(em);
	}

	@Command
	@NotifyChange({ "cobaselist", "embaselist", "num", "grouplist" })
	public void readClient() {
		Combobox cbClient = (Combobox) win.getFellow("client");
		cbClient.setValue(UserInfo.getUsername());
		changecobase();
	}

	// 选择客服时查询该客服的公司
	@Command
	@NotifyChange({ "cobaselist", "embaselist", "num", "grouplist" })
	public void changecobase() {
		Combobox cbClient = (Combobox) win.getFellow("client");
		Combobox cbCompany = (Combobox) win.getFellow("cobase");
		cobaselist = null;
		num = 0;
		if (cbClient.getValue() != null && !cbClient.getValue().equals("")) {
			CoBaseModel cbm = new CoBaseModel();
			cbm.setCoba_client(cbClient.getValue());
			cobaselist = selectbll.getcompanyEmbodyList(cbm);

			if (!cobaselist.isEmpty()) {

				cbCompany.setValue(cobaselist.get(0).getCoba_company());

				embaselist = selectbll
						.getEmBaseInfo("and a.gid in ("
								+ "select distinct gid "
								+ "from coglist a "
								+ "inner join coofferlist b on a.cgli_coli_id=b.coli_id "
								+ "where cgli_stopdate is null and (coli_name like '%体检%' or coli_name like '中智_类' or coli_pclass like '%体检%') ) "
								+ "and emba_state=1 and a.cid="
								+ cobaselist.get(0).getCid()
								+ " order by emba_name");
				updateGroup();
			} else {
				cbCompany.setValue("");
				grouplist = null;
			}
		}
	}

	// 选择公司时查询该公司员工
	@Command
	@NotifyChange({ "embaselist", "num", "grouplist" })
	public void changeembase() {
		Combobox cbCompany = (Combobox) win.getFellow("cobase");
		Combobox cbSex = (Combobox) win.getFellow("sex");
		Combobox cbmarry = (Combobox) win.getFellow("marry");
		Textbox tbName = (Textbox) win.getFellow("tbname");
		embaselist = null;
		String sex = "";
		num = 0;
		if (cbSex.getValue() != null && !cbSex.getValue().equals("")) {
			sex = " and a.emba_sex='" + cbSex.getValue() + "'";
		}
		if (cbmarry.getValue() != null && !cbmarry.getValue().equals("")) {
			String marryState = "";
			switch (cbmarry.getValue()) {
			case "初婚":
			case "再婚":
			case "已婚":
			case "丧偶":
			case "离异":
				marryState = "已婚";
				break;
			case "未婚":
				marryState = "未婚";
			default:
				break;
			}
			sex += " and a.emba_marital='" + marryState + "'";
		}
		if (tbName.getValue() != null && !tbName.getValue().equals("")) {
			sex += " and a.emba_name like '%" + tbName.getValue() + "%'";
		}

		if (cbCompany.getSelectedItem() != null) {
			if (cbCompany.getSelectedItem().getValue() != null
					&& !cbCompany.getSelectedItem().getValue().equals("")) {
				bcModel.setCid(Integer.valueOf(cbCompany.getSelectedItem()
						.getValue().toString()));
				embaselist = selectbll
						.getEmBaseInfo(" and emba_state=1 and a.cid="
								+ cbCompany.getSelectedItem().getValue()
								+ sex
								+ "and gid in(select distinct gid from coglist a "
								+ "inner join coofferlist b on a.cgli_coli_id=b.coli_id "
								+ "where cgli_stopdate is null and (coli_name like '%体检%'"
								+ " or coli_name like '中智_类' or coli_pclass like '%体检%') "
								+ "and a.cid="
								+ cbCompany.getSelectedItem().getValue() + ")"
								+ " order by emba_name");
			}
		}
		changeaddress();
	}

	// 特殊员工列表生成
	@Command
	@NotifyChange("embasesplist")
	public void copyRow(@BindingParam("a") EmbaseModel em) {
		if (em.isChecked()) {
			Combobox cbcompany = (Combobox) win.getFellow("cobase");
			Combobox cbClient = (Combobox) win.getFellow("client");
			EmbaseModel em1 = new EmbaseModel();
			embasesplist.add(em1);
			em.setCoba_client(cbClient.getValue());
			em.setCid(cbcompany.getSelectedItem() != null ? Integer
					.valueOf(cbcompany.getSelectedItem().getValue().toString())
					: null);
			em.setCoba_company(cbcompany.getValue());

		} else {
			if (embasesplist.size() > 0) {
				embasesplist.remove(em);
			}
		}
	}

	// 特殊员工列表身份证自动识别性别年龄
	@Command
	public void getinfo(@BindingParam("a") EmbaseModel em) {
		Calendar calendar = new GregorianCalendar();
		Date trialTime = new Date();
		calendar.setTime(trialTime);

		if (em.getEmba_idcard() != null) {
			if (em.getEmba_idcard().length() == 15) {
				em.setEmba_age(calendar.get(Calendar.YEAR)
						- Integer.valueOf("19"
								+ em.getEmba_idcard().substring(6, 8)));
				em.setEmba_sex((Integer.valueOf(em.getEmba_idcard().substring(
						14)) % 2 == 0 ? "女" : "男"));
			} else if (em.getEmba_idcard().length() == 18) {
				em.setEmba_age(calendar.get(Calendar.YEAR)
						- Integer.valueOf(em.getEmba_idcard().substring(6, 10)));
				em.setEmba_sex((Integer.valueOf(em.getEmba_idcard().substring(
						16, 17)) % 2 == 0 ? "女" : "男"));
			} else {
				em.setEmba_age(null);
				em.setEmba_sex(null);
			}
			BindUtils.postNotifyChange(null, null, em, "emba_age");
			BindUtils.postNotifyChange(null, null, em, "emba_sex");
		}

	}

	@Command
	@NotifyChange("num")
	public void checkall() {
		Checkbox ckEmp = (Checkbox) win.getFellow("ckEmp");
		Grid gd = (Grid) win.getFellow("embasegd");
		num = 0;
		for (int i = 0; i < gd.getRows().getChildren().size(); i++) {
			embaselist.get(i).setChecked(ckEmp.isChecked());
			Checkbox ck = (Checkbox) gd.getCell(i, 6).getChildren().get(0);
			ck.setChecked(ckEmp.isChecked());
			if (ck.isChecked()) {
				num++;
			}
		}

	}

	@Command
	@NotifyChange("num")
	public void checkEmp(@BindingParam("a") EmbaseModel em,
			@BindingParam("b") Checkbox cb) {
		Checkbox ckEmp = (Checkbox) win.getFellow("ckEmp");
		ckEmp.setChecked(false);
		em.setChecked(cb.isChecked());
		num = 0;
		for (EmbaseModel m : embaselist) {
			if (m.isChecked()) {
				num++;
			}
		}
	}

	@Command
	@NotifyChange("siList")
	public void check(@BindingParam("a") EmBodyCheckItemModel eb,
			@BindingParam("b") Checkbox ck) {

		if (ck.isChecked()) {
			if (!getBlood(eb)) {
				ck.setChecked(false);
				Messagebox.show("请先选择抽血项目", "操作提示", Messagebox.OK,
						Messagebox.ERROR);
				return;
			}
			if (siList.size() > 0) {
				boolean b = false;
				for (EmBodyCheckItemModel m : siList) {
					if (m.getEbit_id().equals(eb.getEbit_id())) {
						b = true;
						break;
					}
				}
				if (!b) {
					siList.add(eb);
				}
			} else {
				siList.add(eb);
			}
		} else {
			for (int i = 0; i < siList.size(); i++) {
				if (siList.get(i).getEbit_id().equals(eb.getEbit_id())) {
					siList.remove(i);
					break;
				}
			}

		}

	}

	// 选择体检体检组合
	@Command
	@NotifyChange("siList")
	public void checkGroup(@BindingParam("a") EmBcItemGroupModel em) {
		if (siList.size() > 0) {
			if (em.getChecked()) {
				boolean b = false;
				for (EmBodyCheckItemModel m : em.getList()) {
					b = false;
					for (EmBodyCheckItemModel m2 : siList) {
						if (m.getEbit_id().equals(m2.getEbit_id())) {
							b = true;
						}
					}
					if (!b) {
						siList.add(m);
					}
				}
			} else {
				for (EmBodyCheckItemModel m : em.getList()) {
					for (EmBodyCheckItemModel m2 : siList) {
						if (m.getEbit_id().equals(m2.getEbit_id())) {
							siList.remove(m2);
							break;
						}
					}
				}
			}
		} else {
			if (em.getChecked()) {
				for (EmBodyCheckItemModel m : em.getList()) {
					siList.add(m);
				}
			}
		}
	}

	@Command
	@NotifyChange("siList")
	public void delItem(@BindingParam("a") EmBodyCheckItemModel em) {
		siList.remove(em);
	}

	// 判断项目是否有性别限制
	public String getSex() {

		String sex = "";
		for (EmbaseModel em : embaselist) {
			if (em.isChecked()) {
				if (sex.equals("0")) {
					break;
				} else if (sex.equals("")) {
					sex = em.getEmba_sex();
				} else if (!em.getEmba_sex().equals(sex)) {
					sex = "0";
				}
			}
		}

		for (EmbaseModel em : embasesplist) {
			if (em.isChecked()) {
				if (em.getEmba_sex() != null) {
					if (sex.equals("0")) {
						break;
					} else if (sex.equals("")) {
						sex = em.getEmba_sex();
					} else if (!em.getEmba_sex().equals(sex)) {
						sex = "0";
					}
				}
			}
		}

		if (sex.equals("男")) {
			sex = "1";

		} else if (sex.equals("女")) {
			sex = "2";
		}

		return sex;
	}

	// 判断抽血项目
	public boolean getBlood(EmBodyCheckItemModel eb) {
		boolean b = false;
		String idList = "";

		if (eb.getEbit_blood() != null && eb.getEbit_blood().equals(1)) {
			// 检索是否包含中智套餐

			if (mainItem()) {
				return true;
			}

			for (int i = 0; i < listid.size(); i++) {
				idList = idList + "," + listid.get(i);
			}
			EmBodyCheckItemModel ebc = new EmBodyCheckItemModel();
			ebc.setEbit_bmain(1);
			ebc.setIdList(idList);
			// 检索是否含抽血项目
			if (selectbll.getItemList(ebc).size() > 0) {
				b = true;
			}
		} else {
			b = true;
		}

		return b;
	}

	// 判断所选项目是否包含中智套餐
	public boolean mainItem() {
		boolean b = false;
		for (int i = 0; i < siList.size(); i++) {
			if (siList.get(i).getEbit_package() != null
					&& siList.get(i).getEbit_package().equals(1)) {
				return true;
			}
		}
		return b;
	}

	// 根据生日获取年龄
	public int getAge(Date birthDay) throws Exception {
		Calendar cal = Calendar.getInstance();

		if (cal.before(birthDay)) {
			throw new IllegalArgumentException("出生时间大于当前时间!");
		}

		int yearNow = cal.get(Calendar.YEAR);
		int monthNow = cal.get(Calendar.MONTH) + 1;// 注意此处，如果不加1的话计算结果是错误的
		int dayOfMonthNow = cal.get(Calendar.DAY_OF_MONTH);
		cal.setTime(birthDay);

		int yearBirth = cal.get(Calendar.YEAR);
		int monthBirth = cal.get(Calendar.MONTH);
		int dayOfMonthBirth = cal.get(Calendar.DAY_OF_MONTH);

		int age = yearNow - yearBirth;

		if (monthNow <= monthBirth) {
			if (monthNow == monthBirth) {
				// monthNow==monthBirth
				if (dayOfMonthNow < dayOfMonthBirth) {
					age--;
				} else {
					// do nothing
				}
			} else {
				// monthNow>monthBirth
				age--;
			}
		} else {
			// monthNow<monthBirth
			// donothing
		}

		return age;
	}

	// 选择体检医院的事件
	@Command
	@NotifyChange({ "addresslist", "grouplist", "itemlist", "isopen", "msg" })
	public void changeaddress() {
		Combobox cbSetup = (Combobox) win.getFellow("setup");
		Combobox cbCompany = (Combobox) win.getFellow("cobase");
		Combobox cbAddress = (Combobox) win.getFellow("address");
		cbAddress.setValue("");
		if(cbAddress.getChildren().size()>0){
			cbAddress.setSelectedIndex(0);
		}
		
		if (cbCompany.getSelectedItem() != null) {
			bcModel.setCid(Integer.valueOf(cbCompany.getSelectedItem()
					.getValue().toString()));
		}

		if (cbSetup.getSelectedItem() != null) {
			updateGroup();
			updateBcItem();
			setselected();
		}
		isopen = false;
	}

	// 更新体检组合
	public void updateGroup() {
		Combobox cbSetup = (Combobox) win.getFellow("setup");
		if (cbSetup.getSelectedItem() != null) {
			EmBcSetupAddressModel ebam = new EmBcSetupAddressModel();
			ebam.setEbsa_ebcs_id(Integer.valueOf(cbSetup.getSelectedItem()
					.getValue().toString()));
			ebam.setEbsa_state(1);
			addresslist = selectbll.getSetUpAddress(ebam);
			if (bcModel.getCid() != null && !bcModel.getCid().equals("")) {
				EmBcItemGroupModel eigm = new EmBcItemGroupModel();
				eigm.setEbig_state(1);
				eigm.setCid(bcModel.getCid());
				eigm.setEbig_hospital(ebam.getEbsa_ebcs_id());
				grouplist = selectbll.getItemGroup(eigm);
				for (EmBcItemGroupModel m : grouplist) {
					m.setList(selectbll.getGroupItem(m.getEbig_id()));
				}
			} else {
				grouplist = null;
			}
		}
	}

	// 更新体检项目
	public void updateBcItem() {
		Combobox cbSetup = (Combobox) win.getFellow("setup");
		EmBodyCheckItemModel ebcm = new EmBodyCheckItemModel();
		ebcm.setEbit_state(1);
		ebcm.setEbit_hospital(Integer.valueOf(cbSetup.getSelectedItem()
				.getValue().toString()));
		itemlist = selectbll.getItemList(ebcm);

	}

	// 设置下拉选项
	@Command
	@NotifyChange({ "address", "bctype", "booktype", "msg" })
	public void setselected() {
		cbHospital = (Combobox) win.getFellow("setup");
		cbAddress = (Combobox) win.getFellow("address");
		cbBcType = (Combobox) win.getFellow("embctype");
		cbBookType = (Combobox) win.getFellow("booktype");
		List<EmBcSetupAddressModel> adList = new ListModelList<>();
		EmBcSetupAddressModel ebam = new EmBcSetupAddressModel();
		if (cbAddress.getSelectedItem() != null) {
			if (!cbAddress.getSelectedItem().getValue().equals("")) {
				ebam.setEbsa_id(Integer.valueOf(cbAddress.getSelectedItem()
						.getValue().toString()));
				adList = selectbll.getSetUpAddress(ebam);
				if (adList.get(0).getEbsa_istate() != null
						&& adList.get(0).getEbsa_istate().equals(1)) {
					rz = true;
				}else {
					rz=false;
				}
				if (bctype.equals("")) {
					if (adList.get(0).getEbsa_ystate() != null
							&& adList.get(0).getEbsa_ystate().equals(1)) {
						// bctype = "年度体检";
						bctype = "";
					} else if (adList.get(0).getEbsa_istate() != null
							&& adList.get(0).getEbsa_istate().equals(1)) {
						bctype = "入职体检";
					} else {
						bctype = "单次体检";
					}
				} else {
					if (adList.get(0).getEbsa_ystate() != null
							&& adList.get(0).getEbsa_ystate().equals(0)
							&& bctype.equals("年度体检")) {
						bctype = "";
					} else if (adList.get(0).getEbsa_istate() != null
							&& adList.get(0).getEbsa_istate().equals(0)
							&& bctype.equals("入职体检")) {
						bctype = "";

					}
				}

			}
		}

		if (!bctype.equals("") && !bctype.equals("入职体检")) {
			booktype = "不固定时间";
		} else if (bctype.equals("入职体检")) {
			booktype = "固定时间";
		}

		setmsgs();
	}

	@Command
	public void openItem() {
		East e = (East) win.getFellow("items");
		e.setOpen(true);
	}

	@Command
	public void closeItem() {
		East e = (East) win.getFellow("items");
		e.setOpen(false);
	}

	// 设置提示信息
	public void setmsgs() {

		EmBcSetupModel ebsm = new EmBcSetupModel();
		String SetUpInfo = "";
		msg = "";
		if (!bctype.equals("")) {
			if (bctype.equals("入职体检")) {

				if (booktype.equals("")) {

				} else if (booktype.equals("固定时间")) {
					msg = "需要提前一天预约,体检时间只有当天生效";
				} else {
					msg = "需要提前一天预约,体检时间从预约时间起15天内有效;由于体检时间不确定,福利部将无法及时跟进后续体检结果反馈,请知悉!";
				}
			} else {
				msg = "需要提前四天预约,体检时间从预约时间起60天内有效";
			}

		}

		if (cbHospital.getSelectedItem() != null) {
			ebsm.setEbcs_id(Integer.valueOf(cbHospital.getSelectedItem()
					.getValue().toString()));
			SetUpInfo = selectbll.getSetupList(ebsm).get(0).getEbcs_info();

		}
		if (SetUpInfo != null && !SetUpInfo.equals("")) {
			msg = SetUpInfo + "." + msg;
		}
		if (!rz) {
			msg=msg+".该体检地址无法参加入职体检.";
		}

	}

	public boolean commit() {
		boolean b = true;
		Combobox cbClient = (Combobox) win.getFellow("client");
		cbHospital = (Combobox) win.getFellow("setup");
		cbAddress = (Combobox) win.getFellow("address");
		Datebox db = (Datebox) win.getFellow("bookdate");
		Textbox tb = (Textbox) win.getFellow("remark");
		cbBcType = (Combobox) win.getFellow("embctype");
		cbBookType = (Combobox) win.getFellow("booktype");

		String itemName = "";
		String itemList = "";
		boolean emp = false;
		Integer week[] = new Integer[7];

		// 判断客服
		if (cbClient.getValue() == null || cbClient.getValue().equals("")) {
			Messagebox.show("请选择客服!", "提示", Messagebox.OK, Messagebox.ERROR);
			return false;
		}

		for (EmbaseModel em : embaselist) {
			if (em.isChecked()) {
				emp = true;
				break;
			}
		}

		for (EmbaseModel em : embasesplist) {
			if (em.isChecked()) {
				emp = true;
				break;
			}
		}

		if (!emp) {
			Messagebox.show("请选择参加体检的人员名单!", "提示", Messagebox.OK,
					Messagebox.ERROR);
			return false;
		}

		// 特殊人员信息
		for (EmbaseModel em : embasesplist) {
			if (em.isChecked()) {
				if (em.getEmba_name() == null || em.getEmba_name().equals("")) {
					Messagebox.show("请输入体检人员姓名!", "提示", Messagebox.OK,
							Messagebox.ERROR);
					return false;
				}
				if (em.getEmba_idcard() == null
						|| em.getEmba_idcard().equals("")) {
					Messagebox.show("请输入[" + em.getEmba_name() + "]身份证!", "提示",
							Messagebox.OK, Messagebox.ERROR);
					return false;
				}
			}
		}

		// 体检项目
		if (siList.size() < 1) {
			Messagebox.show("请选择体检项目!", "提示", Messagebox.OK, Messagebox.ERROR);
			return false;
		}

		// 判断性别限制
		String sex = getSex();
		for (int i = 0; i < siList.size(); i++) {
			if (siList.get(i).getEbit_sex() != null
					&& !siList.get(i).getEbit_sex().equals(0)) {

				if (!siList.get(i).getEbit_sex().equals(Integer.valueOf(sex))) {
					Messagebox.show("所选项目有性别限制,请重新选择!", "提示", Messagebox.OK,
							Messagebox.ERROR);
					return false;
				}
			}
		}

		// 体检机构
		if (cbHospital.getSelectedItem() == null) {
			Messagebox.show("请选择体检医院", "提示", Messagebox.OK, Messagebox.ERROR);
			return false;
		} else {
			Integer h = Integer.valueOf(cbHospital.getSelectedItem().getValue()
					.toString());

			bcModel.getModel().setEbcl_hospital(h);
		}

		// 预约地址

		if (cbAddress.getSelectedItem() == null) {
			// Messagebox.show("请选择体检地址!", "提示", Messagebox.OK,
			// Messagebox.ERROR);
			// return false;
		} else {
			bcModel.getModel().setEbcl_area(
					Integer.valueOf(cbAddress.getSelectedItem().getValue()
							.toString()));
			for (EmBcSetupAddressModel m : addresslist) {
				if (m.getEbsa_id().equals(bcModel.getModel().getEbcl_area())) {
					week[1] = m.getEbsa_w1();
					week[2] = m.getEbsa_w2();
					week[3] = m.getEbsa_w3();
					week[4] = m.getEbsa_w4();
					week[5] = m.getEbsa_w5();
					week[6] = m.getEbsa_w6();
					week[0] = m.getEbsa_w7();
				}
			}
		}

		// 体检类型
		if (cbBcType.getValue().equals("")) {
			Messagebox.show("请选择体检类型!", "提示", Messagebox.OK, Messagebox.ERROR);
			return false;
		} else {

			bcModel.getModel()
					.setEbcl_type(
							bctype.equals("年度体检") ? 2
									: (bctype.equals("入职体检") ? 1 : 0));

		}

		// 预约时间

		if (db.getValue() != null && !db.getValue().equals("")) {
			Date d = new Date();
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

			if (DateUtil.datediff(db.getValue(), d, "d") >= 0) {
				Messagebox.show("请选择当天以后的日期.", "提示", Messagebox.OK,
						Messagebox.ERROR);
				return false;
			}

			if (bcModel.getModel().getEbcl_type().equals(2)) {
				Calendar c = Calendar.getInstance();
				c.setTime(db.getValue());
				for (Integer w = 0; w < 7; w++) {
					if (week[w] != null && week[w].equals(0)) {
						if (w.equals((c.get(Calendar.DAY_OF_WEEK) - 1))) {
							Messagebox.show("当前所选机构在所选日期休息,请重新选择.", "提示",
									Messagebox.OK, Messagebox.ERROR);
							return false;
						}
					}
				}
				if (UserInfo.getDepID() != "8") {
					if (DateUtil.datediff(DateUtil.getDate(d, 4),
							db.getValue(), "d") < 0) {
						Messagebox.show("请选择4个工作日以后的日期.", "提示", Messagebox.OK,
								Messagebox.ERROR);
						return false;
					}
				}
			} else if (bcModel.getModel().getEbcl_type().equals(1)) {
				if (UserInfo.getDepID() != "8") {
					if (DateUtil.datediff(DateUtil.getDate(d, 2),
							db.getValue(), "d") < 0) {
						Messagebox.show("请选择2个工作日以后的日期.", "提示", Messagebox.OK,
								Messagebox.ERROR);
						return false;
					}
				}
			}

			bcModel.getModel().setEbcl_bookdate(sdf.format(db.getValue()));

		} else {
			// Messagebox.show("请选择体检时间!", "提示", Messagebox.OK,
			// Messagebox.ERROR);
			// return false;
		}

		// 预约模式
		if (cbBookType.getValue().equals("")) {
			Messagebox.show("请选择预约模式!", "提示", Messagebox.OK, Messagebox.ERROR);
			return false;
		} else {
			bcModel.getModel()
					.setEbcl_bookmode(booktype.equals("固定时间") ? 1 : 2);
		}

		// 判断体检医院金额限制
		EmBcSetupModel ebsm = new EmBcSetupModel();
		ebsm.setEbcs_id(Integer.valueOf(cbHospital.getSelectedItem().getValue()
				.toString()));

		List<EmBcSetupModel> suList = selectbll.getSetupList(ebsm);

		BigDecimal bd = new BigDecimal(0);
		BigDecimal dc = new BigDecimal(0);
		for (int i = 0; i < siList.size(); i++) {
			bd = bd.add(siList.get(i).getEbit_charge());
			dc = dc.add(siList.get(i).getEbit_discount());
			itemName = itemName + "," + siList.get(i).getEbit_name();
			itemList = itemList + "," + siList.get(i).getEbit_id();
		}
		itemName = itemName.substring(1);
		itemList = itemList.substring(1);

		if (suList.get(0).getEbcs_pstate().equals(1)
				&& suList.get(0).getEbcs_limit().compareTo(BigDecimal.ZERO) > 0) {
			if (!mainItem()) {
				if (suList.get(0).getEbcs_limit().compareTo(bd) > 0) {
					Messagebox.show("该医院体检项项目需达到"
							+ suList.get(0).getEbcs_limit().toString()
							+ "元才能.请增加项目.", "提示", Messagebox.OK,
							Messagebox.ERROR);
					return false;
				}
			}
		}
		// 录入费用
		bcModel.getModel().setEbcl_charge(bd);
		bcModel.getModel().setEbcl_discount(dc);
		bcModel.getModel().setEbcl_items(itemName);
		bcModel.getModel().setEbcl_itemnums(itemList);
		// 备注
		bcModel.setEmbc_remark(tb.getValue());

		return b;
	}

	// 体检信息新增提交事件
	@Command
	public void submit() {
		String[] s;
		for (EmbaseModel m : embaselist) {
			if (m.isChecked()) {
				s = selectbll.inureBc(m.getEmba_idcard());
				if (s[0].equals("1")) {
					Messagebox
							.show(s[1], "提示", Messagebox.OK, Messagebox.ERROR);
					return;
				}
			}
		}
		for (EmbaseModel m : embasesplist) {
			if (m.isChecked()) {
				s = selectbll.inureBc(m.getEmba_idcard());
				if (s[0].equals("1")) {
					Messagebox
							.show(s[1], "提示", Messagebox.OK, Messagebox.ERROR);
					return;
				}
			}
		}

		if (!commit()) {
			return;
		}
		Combobox cbCompany = (Combobox) win.getFellow("cobase");
		if (cbCompany.getSelectedItem() != null) {
			if (!selectbll.linkInfo(Integer.valueOf(cbCompany.getSelectedItem()
					.getValue().toString()))) {
				Messagebox.show("该公司未录入操作约定中的体检联系信息.", "提示", Messagebox.OK,
						Messagebox.ERROR);
				return;
			}
		}

		Messagebox.show("确认提交数据?", "操作提示", new Messagebox.Button[] {
				Messagebox.Button.OK, Messagebox.Button.CANCEL },
				Messagebox.QUESTION,
				new EventListener<Messagebox.ClickEvent>() {

					@Override
					public void onEvent(ClickEvent event) throws Exception {
						// TODO Auto-generated method stub

						if (Messagebox.Button.OK.equals(event.getButton())) {
							boolean sub = true;
							Integer k = 0; // 获取组合
							String groupid = "";
							for (EmBcItemGroupModel gm : grouplist) {
								if (gm.getChecked() != null && gm.getChecked()) {
									groupid = groupid + gm.getEbig_id() + ",";
								}
							}
							if (groupid.length() > 0) {
								groupid = groupid.substring(0,
										groupid.length() - 1);
								bcModel.getModel().setEbcl_itemgroup(groupid);
							}
							for (int i = 0; i < embaselist.size(); i++) {
								if (embaselist.get(i).getEmba_id() > 0) {
									if (embaselist.get(i).isChecked()) {
										bcModel.setGid(embaselist.get(i)
												.getGid());
										bcModel.setEmbc_name(embaselist.get(i)
												.getEmba_name());
										bcModel.setEmbc_company(embaselist.get(
												i).getCoba_company());
										bcModel.setEmbc_shortname(embaselist
												.get(i).getCoba_shortname());
										bcModel.setEmbc_bcid("0");
										bcModel.setEmbc_spell(embaselist.get(i)
												.getEmba_spell());
										bcModel.setEmbc_idcard(embaselist
												.get(i).getEmba_idcard());
										bcModel.setEmbc_sex(embaselist.get(i)
												.getEmba_sex());
										bcModel.setEmbc_age(embaselist.get(i)
												.getEmba_age());
										if (embaselist.get(i).getEmba_mobile() != null) {
											bcModel.setEmbc_mobile(embaselist
													.get(i).getEmba_mobile());
										}

										bcModel.setEmbc_client(embaselist
												.get(i).getCoba_client());
										bcModel.setEmbc_addname(username);
										bcModel.getModel().setEbcl_addname(
												username);
										String[] str = new String[5];
										str = opBll.EmBodyCheckAdd(bcModel,
												bcModel.getModel());

										if (!str[0].equals("1")) {
											sub = false;
											break;
										}
										k++;
									}
								}
							}
							for (EmbaseModel em : embasesplist) {
								if (em.isChecked()) {
									EmBodyCheckModel model = new EmBodyCheckModel();
									model.setCid(em.getCid());
									model.setEmbc_name(em.getEmba_name());
									model.setEmbc_company(em.getCoba_company());
									model.setEmbc_shortname(em
											.getCoba_company());
									model.setEmbc_bcid("0");
									model.setEmbc_idcard(em.getEmba_idcard());
									model.setEmbc_sex(em.getEmba_sex());
									model.setEmbc_age(em.getEmba_age());
									model.setEmbc_client(em.getCoba_client());
									model.setEmbc_addname(username);
									if (em.getEmba_mobile() != null) {
										model.setEmbc_mobile(em
												.getEmba_mobile());
									}

									bcModel.getModel()
											.setEbcl_addname(username);
									String[] str = new String[5];
									str = opBll.EmBodyCheckAdd(model,
											bcModel.getModel());

									if (!str[0].equals("1")) {
										sub = false;
										break;
									}
									k++;
								}
							}
							if (sub) {
								Messagebox.show("新增成功 " + k + " 人", "提示",
										Messagebox.OK, Messagebox.INFORMATION);
								Executions.sendRedirect("Embc_InfoAdd.zul");
							} else {
								Messagebox.show("新增成功 " + k + " 人", "提示",
										Messagebox.OK, Messagebox.ERROR);
							}

						}
					}
				});

	}

	// 项目查询
	@Command
	@NotifyChange("itemlist")
	public void search() {
		Textbox tbName = (Textbox) win.getFellow("SearchItem");
		Combobox cbSetup = (Combobox) win.getFellow("setup");

		if (cbSetup.getSelectedItem() != null
				&& cbSetup.getSelectedItem().getValue() != null) {

			EmBodyCheckItemModel ebcm = new EmBodyCheckItemModel();
			ebcm.setEbit_hospital(Integer.valueOf(cbSetup.getSelectedItem()
					.getValue().toString()));
			ebcm.setEbit_state(1);
			if (tbName.getValue() != null && !tbName.getValue().equals("")) {
				ebcm.setEbit_name(tbName.getValue());
			}

			itemlist = selectbll.getItemList(ebcm);

			for (int i = 0; i < itemlist.size(); i++) {
				for (int y = 0; y < listid.size(); y++) {
					if (listid.get(y).equals(
							itemlist.get(i).getEbit_id().toString())) {
						itemlist.get(i).setFlag(true);
					}
				}
			}
		}
	}

	public List<loginroleModel> getClientlist() {
		return clientlist;
	}

	public void setClientlist(loginroleModel lm) {
		this.clientlist = selectbll.getClientList(lm);
	}

	public List<CoBaseModel> getCobaselist() {
		return cobaselist;
	}

	public void setCobaselist(List<CoBaseModel> cobaselist) {
		this.cobaselist = cobaselist;
	}

	public List<EmbaseModel> getEmbaselist() {
		return embaselist;
	}

	public void setEmbaselist(List<EmbaseModel> embaselist) {
		this.embaselist = embaselist;
	}

	public List<EmBcSetupModel> getSetuplist() {
		return setuplist;
	}

	public void setSetuplist(EmBcSetupModel ebsm) {
		this.setuplist = selectbll.getSetupList(ebsm);
	}

	public List<EmBcSetupAddressModel> getAddresslist() {
		return addresslist;
	}

	public void setAddresslist(List<EmBcSetupAddressModel> addresslist) {
		this.addresslist = addresslist;
	}

	public List<EmBcItemGroupModel> getGrouplist() {
		return grouplist;
	}

	public void setGrouplist(List<EmBcItemGroupModel> grouplist) {
		this.grouplist = grouplist;
	}

	public List<EmBodyCheckItemModel> getItemlist() {
		return itemlist;
	}

	public void setItemlist(List<EmBodyCheckItemModel> itemlist) {
		this.itemlist = itemlist;
	}

	public boolean isIsopen() {
		return isopen;
	}

	public void setIsopen(boolean isopen) {
		this.isopen = isopen;
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getBctype() {
		return bctype;
	}

	public void setBctype(String bctype) {
		this.bctype = bctype;
	}

	public String getBooktype() {
		return booktype;
	}

	public void setBooktype(String booktype) {
		this.booktype = booktype;
	}

	public List<EmBodyCheckItemModel> getSiList() {
		return siList;
	}

	public void setSiList(List<EmBodyCheckItemModel> siList) {
		this.siList = siList;
	}

	private String datechange(Date d) {
		String date = "";
		SimpleDateFormat time = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
		date = time.format(d);
		return date;
	}

	public List<EmbaseModel> getEmbasesplist() {
		return embasesplist;
	}

	public void setEmbasesplist(List<EmbaseModel> embasesplist) {
		this.embasesplist = embasesplist;
	}

	public Integer getNum() {
		return num;
	}

	public void setNum(Integer num) {
		this.num = num;
	}

}
