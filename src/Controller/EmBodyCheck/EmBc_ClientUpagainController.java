package Controller.EmBodyCheck;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.zkoss.bind.annotation.BindingParam;
import org.zkoss.bind.annotation.Command;
import org.zkoss.bind.annotation.NotifyChange;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.Path;
import org.zkoss.zk.ui.event.EventListener;
import org.zkoss.zul.Cell;
import org.zkoss.zul.Checkbox;
import org.zkoss.zul.Combobox;
import org.zkoss.zul.Grid;
import org.zkoss.zul.ListModelList;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Window;
import org.zkoss.zul.Messagebox.ClickEvent;

import Model.EmBcItemGroupModel;
import Model.EmBcSetupAddressModel;
import Model.EmBcSetupModel;
import Model.EmBodyCheckItemModel;
import Model.EmBodyCheckModel;
import bll.EmBodyCheck.EmBcInfo_OperateBll;
import bll.EmBodyCheck.EmBcInfo_SelectBll;
import bll.EmBodyCheck.EmbcItem_SelectBll;

public class EmBc_ClientUpagainController {
	private Integer id = Integer.valueOf(Executions.getCurrent().getArg()
			.get("daid").toString());
	private Integer tapr_id = null;
	private EmBodyCheckModel ecm = new EmBodyCheckModel();
	private EmBcInfo_SelectBll bll = new EmBcInfo_SelectBll();
	private List<EmBodyCheckModel> bclist = bll
			.getEmBodyCheckInfo(" and embc_id=" + id);
	private Combobox cbHospital;
	private Combobox cbAddress;
	private Combobox cbBcType;
	private Combobox cbBookType;
	private String booktype = "";
	private String msg;
	private Date bookdate;
	private Window win = (Window) Path.getComponent("/upwin");
	private List<EmBcSetupModel> setuplist = new ListModelList<>();
	private List<EmBodyCheckItemModel> listed = new ArrayList<EmBodyCheckItemModel>();
	// 体检项目信息
	private List<EmBodyCheckItemModel> itemlist = new ArrayList<EmBodyCheckItemModel>();
	private List<EmBodyCheckItemModel> seitemlist = new ArrayList<EmBodyCheckItemModel>();
	// 机构地址
	private List<EmBcSetupAddressModel> addresslist = new ArrayList<EmBcSetupAddressModel>();
	private EmbcItem_SelectBll itembll = new EmbcItem_SelectBll();
	private String address = "", hospital = "";

	public EmBc_ClientUpagainController() {
		if (Executions.getCurrent().getArg().get("id") != null) {
			tapr_id = Integer.valueOf(Executions.getCurrent().getArg()
					.get("id").toString());
		}
		if (bclist.size() > 0) {
			ecm = bclist.get(0);
		}
		EmBcSetupModel ebsm = new EmBcSetupModel();
		ebsm.setEbcs_state(1);
		setuplist = bll.getSetupList(ebsm);
		if (ecm.getEbcl_bookmode() != null) {
			if (ecm.getEbcl_bookmode().equals(1)) {
				booktype = "固定时间";
			} else {
				booktype = "不固定时间";
			}
		} else {
			booktype = "不固定时间";
		}
		if (ecm.getEbcl_bookdate() != null
				&& !ecm.getEbcl_bookdate().equals("")) {
			bookdate = strToDate(ecm.getEbcl_bookdate());
		}
		EmBodyCheckItemModel ebcm = new EmBodyCheckItemModel();
		ebcm.setEbit_state(1);
		ebcm.setEbit_hospital(getSetupId(ecm.getEbcs_hospital()));

		// ebcm.setIdList(ecm.getEbcl_itemnums());
		itemlist = bll.getItemList(ebcm);
		seitemlist = itembll.getEmBcItemInfo(" and ebit_id in("
				+ ecm.getEbcl_itemnums() + ")");
		init();
		setmsgs();
		address = ecm.getEbsa_address();
		hospital = ecm.getEbcs_hospital();
	}

	// 获取已选项目
	private void init() {
		if (listed.size() > 0) {
			listed.clear();
		}
		List<EmBodyCheckItemModel> itlist2 = new ArrayList<EmBodyCheckItemModel>();
		for (EmBodyCheckItemModel ml : itemlist) {
			ml.setChecked(false);
		}
		for (EmBodyCheckItemModel m : seitemlist) {
			for (EmBodyCheckItemModel ml : itemlist) {
				if (m.getEbit_id().equals(ml.getEbit_id())) {
					ml.setChecked(true);
					listed.add(ml);
				} else {
					boolean flag = true;
					for (EmBodyCheckItemModel mls : itlist2) {
						if (mls.getEbit_id() == ml.getEbit_id()) {
							flag = false;
						}
					}
					if (flag) {
						itlist2.add(ml);
					}
				}
			}
		}
		itemlist.removeAll(listed);// 把itlist2中与itlist重复的去除
	}

	// 根据体检机构名称获取机构Id
	private Integer getSetupId(String name) {
		Integer id = null;
		for (EmBcSetupModel m : setuplist) {
			if (m.getEbcs_hospital() != null
					&& m.getEbcs_hospital().equals(name)) {
				id = m.getEbcs_id();
			}
		}
		return id;
	}

	// 设置下拉选项
	@Command
	@NotifyChange({ "ecm", "bctype", "booktype", "msg" })
	public void setselected() {
		cbHospital = (Combobox) win.getFellow("setup");
		cbAddress = (Combobox) win.getFellow("address");
		cbBcType = (Combobox) win.getFellow("embctype");
		cbBookType = (Combobox) win.getFellow("booktype");
		List<EmBcSetupAddressModel> adList = new ListModelList<>();
		EmBcSetupAddressModel ebam = new EmBcSetupAddressModel();
		if (ecm.getEbsa_address() != null) {
			if (cbAddress.getSelectedItem() != null
					&& !cbAddress.getSelectedItem().getValue().equals("")) {
				ebam.setEbsa_id(Integer.valueOf(cbAddress.getSelectedItem()
						.getValue().toString()));
			}
			if (ebam.getEbsa_id() == null && ecm.getEbcl_area() != null) {
				ebam.setEbsa_id(ecm.getEbcl_area());
			}
			adList = bll.getSetUpAddress(ebam);

			if (ecm.getEbcl_typename().equals("")) {
				if (adList.get(0).getEbsa_ystate() != null
						&& adList.get(0).getEbsa_ystate().equals(1)) {
					ecm.setEbcl_typename("年度体检");
				} else if (adList.get(0).getEbsa_istate() != null
						&& adList.get(0).getEbsa_istate().equals(1)) {
					ecm.setEbcl_typename("入职体检");
				} else {
					ecm.setEbcl_typename("单次体检");
				}
			} else {
				if (adList.get(0).getEbsa_ystate() != null
						&& adList.get(0).getEbsa_ystate().equals(0)
						&& ecm.getEbcl_typename().equals("年度体检")) {
					ecm.setEbcl_typename("");
					Messagebox.show("该医院不能做年度体检", "提示", Messagebox.OK,
							Messagebox.ERROR);
				} else if (adList.get(0).getEbsa_istate() != null
						&& adList.get(0).getEbsa_istate().equals(0)
						&& ecm.getEbcl_typename().equals("入职体检")) {
					ecm.setEbcl_typename("");
					Messagebox.show("该医院不能做入职体检", "提示", Messagebox.OK,
							Messagebox.ERROR);
				}
			}

		}

		if (!ecm.getEbcl_typename().equals("")
				&& !ecm.getEbcl_typename().equals("入职体检")) {
			booktype = "不固定时间";
		}

		setmsgs();
		setmsgscbHospital();
	}

	// 设置提示信息
	public void setmsgs() {
		msg = "";
		if (ecm.getEbcl_typename() != null
				&& !ecm.getEbcl_typename().equals("")) {
			if (ecm.getEbcl_typename().equals("入职体检")) {

				if (booktype.equals("")) {

				} else if (booktype.equals("固定时间")) {
					msg = "需要提前一天预约,体检时间只有当天生效";
				} else {
					msg = "需要提前一天预约,体检时间从预约时间起15天内有效;由于体检时间不确定,福利部将无法及时跟进后续体检结果反馈,请知悉!";
				}
			} else {
				msg = "需要提前三天预约,体检时间从预约时间起60天内有效";
			}

		}
	}

	public void setmsgscbHospital() {
		EmBcSetupModel ebsm = new EmBcSetupModel();
		String SetUpInfo = "";
		if (cbHospital.getSelectedItem() != null) {
			ebsm.setEbcs_id(Integer.valueOf(cbHospital.getSelectedItem()
					.getValue().toString()));
			SetUpInfo = bll.getSetupList(ebsm).get(0).getEbcs_info();

		}
		if (SetUpInfo != null && !SetUpInfo.equals("")) {
			msg = SetUpInfo + "." + msg;
		}

	}

	// 选择体检医院的事件
	@Command
	@NotifyChange({ "addresslist", "grouplist", "itemlist", "isopen", "ecm" })
	public void changeaddress(@BindingParam("c") Combobox c,
			@BindingParam("cid") Integer cid) {

		if (c.getSelectedItem() != null) {
			EmBcSetupAddressModel ebam = new EmBcSetupAddressModel();
			ebam.setEbsa_ebcs_id(Integer.valueOf(c.getSelectedItem().getValue()
					.toString()));
			ebam.setEbsa_state(1);
			ebam.setEmbc_confirm(1);
			if (ecm.getEbcl_typename() != null
					&& ecm.getEbcl_typename().equals("入职体检")) {
				ebam.setEbsa_istate(1);
			} else if (ecm.getEbcl_typename() != null
					&& ecm.getEbcl_typename().equals("年度体检")) {
				ebam.setEbsa_ystate(1);
			}
			addresslist.add(new EmBcSetupAddressModel());
			addresslist.addAll(bll.getSetUpAddress(ebam));

			addresslist = bll.getSetUpAddress(ebam);
			if (cid != null) {
				if (!cid.equals("")) {
					EmBcItemGroupModel eigm = new EmBcItemGroupModel();
					eigm.setEbig_state(1);
					eigm.setCid(cid);
					eigm.setEbig_hospital(ebam.getEbsa_ebcs_id());
					// grouplist = selectbll.getItemGroup(eigm);
				}

			} else {
				// grouplist = null;
			}

			EmBodyCheckItemModel ebcm = new EmBodyCheckItemModel();
			ebcm.setEbit_state(1);
			ebcm.setEbit_hospital(Integer.valueOf(c.getSelectedItem()
					.getValue().toString()));
			itemlist = bll.getItemList(ebcm);
			if (ecm.getEbcs_hospital() == hospital
					|| ecm.getEbcs_hospital().equals(hospital)) {
				ecm.setEbsa_address(address);
			} else {
				ecm.setEbsa_address("");
			}
			init();
		}
	}

	// 取消选择
	@Command
	@NotifyChange({ "listed", "itemlist" })
	public void cancel(@BindingParam("model") EmBodyCheckItemModel model,
			@BindingParam("cb") Checkbox cb) {
		if (!cb.isChecked()) {
			listed.remove(model);
			model.setChecked(false);
			itemlist.add(model);
		}
	}

	// 选择项目
	@Command
	@NotifyChange({ "listed", "itemlist" })
	public void checkcb(@BindingParam("model") EmBodyCheckItemModel model,
			@BindingParam("cb") Checkbox cb) {
		if (cb.isChecked()) {
			itemlist.remove(model);
			model.setChecked(true);
			listed.add(model);
		}
	}

	// 修改
	@Command
	public void update(@BindingParam("win") Window win,
			@BindingParam("gd") Grid gd) {
		String strinfo = "", itemstr = "", item_id = "";
		if (ecm.getEbcs_hospital() == null || ecm.getEbcs_hospital().equals("")) {
			strinfo = "请选择体检机构";
		} else if (ecm.getEbcl_typename() == null
				|| ecm.getEbcl_typename().equals("")) {
			strinfo = "请选择体检类型";
		} else if (ecm.getEbcs_hospital() == null
				|| ecm.getEbcs_hospital().equals("")) {
			strinfo = "请选择体检医院";
		} else {
			if (listed.size() <= 0) {
				strinfo = "请选择体检项目";
			} else {
				for (EmBodyCheckItemModel itm : listed) {
					if (itemstr == null || itemstr.equals("")) {
						itemstr = itemstr + itm.getEbit_name();
					} else {
						itemstr = itemstr + "," + itm.getEbit_name();
					}
					if (item_id == null || item_id.equals("")) {
						item_id = item_id + itm.getEbit_id();
					} else {
						item_id = item_id + "," + itm.getEbit_id();
					}
				}
			}
		}

		if (strinfo == "") {
			String sql = "";
			if (ecm.getEbcl_typename().equals("单次体检")) {
				sql = sql + ",ebcl_type=0";
			} else if (ecm.getEbcl_typename().equals("入职体检")) {
				sql = sql + ",ebcl_type=1";
			} else {
				sql = sql + ",ebcl_type=2";
			}
			if (getSetupId(ecm.getEbcs_hospital()) != null
					&& getSetupId(ecm.getEbcs_hospital()) != 0) {
				sql = sql + ",ebcl_hospital="
						+ getSetupId(ecm.getEbcs_hospital());
			}
			if (getAddressId(ecm.getEbsa_address()) != null
					&& getAddressId(ecm.getEbsa_address()) != 0) {
				sql = sql + ",ebcl_area=" + getAddressId(ecm.getEbsa_address());
			}
			if (bookdate != null) {
				sql = sql + ",ebcl_bookdate='" + getStringDate(bookdate) + "'";
			}
			sql = sql + ",ebcl_items='" + itemstr + "',ebcl_itemnums='"
					+ item_id + "'";
			if (booktype != null) {
				if (booktype.equals("固定时间")) {
					sql = sql + ",ebcl_bookmode=1";
				} else {
					sql = sql + ",ebcl_bookmode=2";
				}
			}
			sql = sql + ",ebcl_state=1";
			EmBcInfo_OperateBll obll = new EmBcInfo_OperateBll();
			String[] str = obll.EmBodyCheckEditUpAgain(ecm, sql, "2");
			String sqlstr = "";
			if (ecm.getEmbc_mobile() != null
					&& !ecm.getEmbc_mobile().equals("")) {
				sqlstr = ",embc_mobile='" + ecm.getEmbc_mobile() + "'";
			}
			if (ecm.getEmbc_marry() != null && !ecm.getEmbc_marry().equals("")) {
				sqlstr = sqlstr + ",embc_marry='" + ecm.getEmbc_marry() + "'";
			}
			obll.updateEmbodyCheckInfo(ecm.getEmbc_id(), sqlstr);
			if (str[0] == "1") {
				Messagebox.show("修改成功", "操作提示", Messagebox.OK,
						Messagebox.INFORMATION);
				win.detach();
			} else {
				Messagebox
						.show("修改失败", "操作提示", Messagebox.OK, Messagebox.ERROR);
			}
		} else {
			Messagebox.show(strinfo, "操作提示", Messagebox.OK, Messagebox.ERROR);
		}
	}

	// 根据体检机构名称获取机构Id
	private Integer getAddressId(String name) {
		Integer id = null;
		for (EmBcSetupAddressModel m : addresslist) {
			if (m.getEbsa_address() != null && m.getEbsa_address().equals(name)) {
				id = m.getEbsa_id();
			}
		}
		return id;
	}

	@Command
	public void del(@BindingParam("win") final Window win) {
		Messagebox.show("确认删除数据?", "操作提示", new Messagebox.Button[] {
				Messagebox.Button.OK, Messagebox.Button.CANCEL },
				Messagebox.QUESTION,
				new EventListener<Messagebox.ClickEvent>() {
					@Override
					public void onEvent(ClickEvent event) throws Exception {
						if (Messagebox.Button.OK.equals(event.getButton())) {
							EmBcInfo_OperateBll delbll = new EmBcInfo_OperateBll();
							String sql = ",ebcl_flag=0,ebcl_state=14";
							String[] str = delbll.delBodyCheck(ecm, sql);
							if (str[0] == "1") {
								Messagebox.show("删除成功", "提示", Messagebox.OK,
										Messagebox.INFORMATION);
								win.detach();
							} else {
								Messagebox.show(str[1], "提示", Messagebox.OK,
										Messagebox.ERROR);
							}
						}
					}
			});
	}

	public EmBodyCheckModel getEcm() {
		return ecm;
	}

	public void setEcm(EmBodyCheckModel ecm) {
		this.ecm = ecm;
	}

	public List<EmBodyCheckModel> getBclist() {
		return bclist;
	}

	public void setBclist(List<EmBodyCheckModel> bclist) {
		this.bclist = bclist;
	}

	public String getBooktype() {
		return booktype;
	}

	public void setBooktype(String booktype) {
		this.booktype = booktype;
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

	public List<EmBcSetupModel> getSetuplist() {
		return setuplist;
	}

	public void setSetuplist(List<EmBcSetupModel> setuplist) {
		this.setuplist = setuplist;
	}

	public List<EmBcSetupAddressModel> getAddresslist() {
		return addresslist;
	}

	public void setAddresslist(List<EmBcSetupAddressModel> addresslist) {
		this.addresslist = addresslist;
	}

	public Date getBookdate() {
		return bookdate;
	}

	public void setBookdate(Date bookdate) {
		this.bookdate = bookdate;
	}

	public List<EmBodyCheckItemModel> getListed() {
		return listed;
	}

	public void setListed(List<EmBodyCheckItemModel> listed) {
		this.listed = listed;
	}

	public List<EmBodyCheckItemModel> getItemlist() {
		return itemlist;
	}

	public void setItemlist(List<EmBodyCheckItemModel> itemlist) {
		this.itemlist = itemlist;
	}

	/**
	 * 将长时间格式字符串转换为时间 yyyy-MM-dd HH:mm:ss
	 * 
	 * @param strDate
	 * @return
	 */
	public static Date strToDate(String strDate) {
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
		Date strtodate = null;
		try {
			if (strDate != null) {
				strtodate = formatter.parse(strDate);
			}
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return strtodate;
	}

	/**
	 * 获取现在时间
	 * 
	 * @return返回字符串格式 yyyy-MM-dd HH:mm:ss
	 */
	public static String getStringDate(Date date) {
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String dateString = null;
		if (date != null) {
			dateString = formatter.format(date);
		}
		return dateString;
	}

}
