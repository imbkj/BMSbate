package Controller.EmBodyCheck;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.zkoss.bind.annotation.BindingParam;
import org.zkoss.bind.annotation.Command;
import org.zkoss.bind.annotation.NotifyChange;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zul.Cell;
import org.zkoss.zul.Checkbox;
import org.zkoss.zul.Combobox;
import org.zkoss.zul.Grid;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Window;

import bll.EmBodyCheck.EmBcInfo_OperateBll;
import bll.EmBodyCheck.EmBcInfo_SelectBll;
import bll.EmBodyCheck.EmbcItem_SelectBll;

import Model.EmBcItemGroupModel;
import Model.EmBcSetupAddressModel;
import Model.EmBcSetupModel;
import Model.EmBodyCheckItemModel;
import Model.EmBodyCheckModel;
import Model.EmbaseModel;
import Util.DateUtil;
import Util.UserInfo;

public class EmBc_InfoEditController {
	private EmBodyCheckModel ecm = (EmBodyCheckModel) Executions.getCurrent()
			.getArg().get("model");
	private EmBcInfo_SelectBll selectbll = new EmBcInfo_SelectBll();
	private EmbcItem_SelectBll itembll = new EmbcItem_SelectBll();
	// 员工信息
	private List<EmbaseModel> embaselist = new ArrayList<EmbaseModel>();
	// 体检项目信息
	private List<EmBodyCheckItemModel> itemlist = new ArrayList<EmBodyCheckItemModel>();
	private List<EmBodyCheckItemModel> seitemlist = new ArrayList<EmBodyCheckItemModel>();
	private EmbaseModel emmodel = new EmbaseModel();
	private List<EmBcSetupModel> setuplist = selectbll
			.getSetupList(new EmBcSetupModel());
	// 机构地址
	private List<EmBcSetupAddressModel> addresslist = new ArrayList<EmBcSetupAddressModel>();
	private Date bookdate;
	private String address = "", hospital = "";
	private List<EmBodyCheckItemModel> listed = new ArrayList<EmBodyCheckItemModel>();

	public EmBc_InfoEditController() {
		if (ecm.getGid() != null && ecm.getGid() > 0) {
			embaselist = selectbll.getEmBaseInfo(" and emba_state=1 and gid="
					+ ecm.getGid());
			if (embaselist.size() > 0) {
				emmodel = embaselist.get(0);
			}
		}

		seitemlist = itembll.getEmBcItemInfo(" and ebit_id in("
				+ ecm.getEbcl_itemnums() + ")");
		EmBodyCheckItemModel ebcm = new EmBodyCheckItemModel();
		ebcm.setEbit_state(1);
		ebcm.setEbit_hospital(getSetupId(ecm.getEbcs_hospital()));

		// ebcm.setIdList(ecm.getEbcl_itemnums());
		itemlist = selectbll.getItemList(ebcm);
		if (ecm.getEbcs_hospital() != null) {
			EmBcSetupAddressModel ebam = new EmBcSetupAddressModel();
			ebam.setEbsa_ebcs_id(getSetupId(ecm.getEbcs_hospital()));
			ebam.setEbsa_state(1);
			if (ecm.getEbcl_typename() != null
					&& ecm.getEbcl_typename().equals("入职体检")) {
				ebam.setEmbc_confirm(1);
				ebam.setEbsa_istate(1);
			} else if (ecm.getEbcl_typename() != null
					&& ecm.getEbcl_typename().equals("年度体检")) {
				ebam.setEbsa_ystate(1);
				ebam.setEmbc_confirm(1);
			}
			addresslist = selectbll.getSetUpAddress(ebam);
		}
		init();
		if (ecm.getEbcl_bookdate() != null
				&& !ecm.getEbcl_bookdate().equals("")) {
			bookdate = strToDate(ecm.getEbcl_bookdate());
		}
		address = ecm.getEbsa_address();
		hospital = ecm.getEbcs_hospital();
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
			if (ecm.getEbcl_typename() != null
					&& ecm.getEbcl_typename().equals("入职体检")) {
				ebam.setEbsa_istate(1);
				ebam.setEmbc_confirm(1);
			} else if (ecm.getEbcl_typename() != null
					&& ecm.getEbcl_typename().equals("年度体检")) {
				ebam.setEbsa_ystate(1);
				ebam.setEmbc_confirm(1);
			}
			addresslist = selectbll.getSetUpAddress(ebam);
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
			itemlist = selectbll.getItemList(ebcm);
			if (ecm.getEbcs_hospital() == hospital
					|| ecm.getEbcs_hospital().equals(hospital)) {
				ecm.setEbsa_address(address);
			} else {
				ecm.setEbsa_address("");
			}
			init();
		}
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

	// 获取已选项目
	private void init() {
		if (listed.size() > 0) {
			listed.clear();
		}
		List<EmBodyCheckItemModel> itlist = new ArrayList<EmBodyCheckItemModel>();
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

	// 修改
	@Command
	public void update(@BindingParam("win") Window win,
			@BindingParam("gd") Grid gd) {
		String strinfo = "", itemstr = "", item_id = "";
		if (ecm.getEbcs_hospital() == null || ecm.getEbcs_hospital().equals("")) {
			strinfo = "请选择体检机构";

			// } else if (ecm.getEbsa_address() == null
			// || ecm.getEbsa_address().equals("")) {
			// strinfo = "请选择体检地址";
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
			if (getSetupId(ecm.getEbcs_hospital()) != null
					&& getSetupId(ecm.getEbcs_hospital()) != 0) {
				sql = sql + ",ebcl_hospital="
						+ getSetupId(ecm.getEbcs_hospital());
			}
			if (getAddressId(ecm.getEbsa_address()) != null
					&& getAddressId(ecm.getEbsa_address()) != 0) {
				if (bookdate != null) {
					Integer week[] = new Integer[7];
					for (EmBcSetupAddressModel m : addresslist) {
						if (m.getEbsa_id().equals(
								getAddressId(ecm.getEbsa_address()))) {
							week[1] = m.getEbsa_w1();
							week[2] = m.getEbsa_w2();
							week[3] = m.getEbsa_w3();
							week[4] = m.getEbsa_w4();
							week[5] = m.getEbsa_w5();
							week[6] = m.getEbsa_w6();
							week[0] = m.getEbsa_w7();
						}
					}
					Calendar c = Calendar.getInstance();
					c.setTime(bookdate);
					for (Integer w = 0; w < 7; w++) {
						if (week[w] != null && week[w].equals(0)) {
							if (w.equals((c.get(Calendar.DAY_OF_WEEK) - 1))) {
								Messagebox.show("当前所选机构在所选日期休息,请重新选择.", "提示",
										Messagebox.OK, Messagebox.ERROR);
								return;
							}
						}
					}
				}

				sql = sql + ",ebcl_area=" + getAddressId(ecm.getEbsa_address());
			}

			Date d = new Date();
			if (ecm.getEbcl_typename().equals("年度体检")) {
				if (UserInfo.getDepID() != "8") {
					if (DateUtil
							.datediff(DateUtil.getDate(d, 4), bookdate, "d") < 0) {
						Messagebox.show("请选择4个工作日以后的日期.", "提示", Messagebox.OK,
								Messagebox.ERROR);
						return;
					}
				}
			} else if (ecm.getEbcl_typename().equals("入职体检")) {
				if (UserInfo.getDepID() != "8") {
					if (DateUtil.datediff(DateUtil.getDate(d, 2),
							bookdate, "d") < 0) {
						Messagebox.show("请选择2个工作日以后的日期.", "提示", Messagebox.OK,
								Messagebox.ERROR);
						return;
					}
				}
			}
			if (bookdate != null) {
				sql = sql + ",ebcl_bookdate='" + getStringDate(bookdate) + "'";
			}

			sql = sql + ",ebcl_items='" + itemstr + "',ebcl_itemnums='"
					+ item_id + "'";
			EmBcInfo_OperateBll obll = new EmBcInfo_OperateBll();
			Integer k = obll.updateEmbodyChecklist(ecm.getEbcl_id(), sql);
			if (ecm.getEmbc_mobile() != null
					&& !ecm.getEmbc_mobile().equals("")) {
				String sqlstr = ",embc_mobile='" + ecm.getEmbc_mobile() + "'";
				obll.updateEmbodyCheckInfo(ecm.getEmbc_id(), sqlstr);
			}
			if (k > 0) {
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

	// 获取选择的项目
	private List<EmBodyCheckItemModel> getItemlists(Grid gd) {
		List<EmBodyCheckItemModel> itemlists = new ArrayList<EmBodyCheckItemModel>();
		for (int i = 0; i < itemlist.size(); i++) {
			if (gd.getRows().getChildren().get(i) != null) {
				if (gd.getCell(i, 5) != null) {
					Cell cell = (Cell) gd.getCell(i, 5);
					Checkbox cb = (Checkbox) cell.getChildren().get(0);
					if (cb.isChecked()) {
						EmBodyCheckItemModel m = cb.getValue();
						itemlists.add(m);
					}
				}
			}
		}
		return itemlists;
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

	public EmbaseModel getEmmodel() {
		return emmodel;
	}

	public void setEmmodel(EmbaseModel emmodel) {
		this.emmodel = emmodel;
	}

	public List<EmBodyCheckItemModel> getItemlist() {
		return itemlist;
	}

	public void setItemlist(List<EmBodyCheckItemModel> itemlist) {
		this.itemlist = itemlist;
	}

	public EmBodyCheckModel getEcm() {
		return ecm;
	}

	public void setEcm(EmBodyCheckModel ecm) {
		this.ecm = ecm;
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

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public List<EmBodyCheckItemModel> getListed() {
		return listed;
	}

	public void setListed(List<EmBodyCheckItemModel> listed) {
		this.listed = listed;
	}

	/**
	 * 获取现在时间
	 * 
	 * @return返回字符串格式 yyyy-MM-dd HH:mm:ss
	 */
	public static String getStringDate(Date date) {
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String dateString = formatter.format(date);
		return dateString;
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
			strtodate = formatter.parse(strDate);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return strtodate;
	}
}
