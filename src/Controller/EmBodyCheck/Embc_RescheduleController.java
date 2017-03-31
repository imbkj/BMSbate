package Controller.EmBodyCheck;

import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.zkoss.bind.annotation.BindingParam;
import org.zkoss.bind.annotation.Command;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zul.Combobox;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Window;

import Model.CoAgencyLinkmanModel;
import Model.CoBaseServePromiseModel;
import Model.EmBcSetupAddressModel;
import Model.EmBcSetupModel;
import Model.EmBodyCheckItemModel;
import Model.EmBodyCheckModel;
import Model.EmbaseModel;
import Model.TaskProcessViewModel;
import Util.DateUtil;
import Util.UserInfo;
import bll.Archives.EmArchive_SelectBll;
import bll.CoBase.CoBaseLinkMan_SelectBll;
import bll.CoServePromise.CoServePromiseSelectBll;
import bll.EmBodyCheck.EmBcInfo_OperateBll;
import bll.EmBodyCheck.EmBcInfo_SelectBll;
import bll.EmBodyCheck.EmbcItem_SelectBll;

public class Embc_RescheduleController {
	private EmBodyCheckModel model = new EmBodyCheckModel();
	private Integer eadaId = Integer.valueOf(Executions.getCurrent().getArg()
			.get("daid").toString());
	private Integer tapr_id = null;
	private EmBcInfo_SelectBll selectbll = new EmBcInfo_SelectBll();
	private EmbcItem_SelectBll itembll = new EmbcItem_SelectBll();
	private String embctype = "";// 体检类型
	// 员工信息
	private List<EmbaseModel> embaselist = new ArrayList<EmbaseModel>();
	// 体检项目信息
	private List<EmBodyCheckItemModel> itemlist = new ArrayList<EmBodyCheckItemModel>();
	private EmbaseModel emmodel = new EmbaseModel();
	private List<EmBodyCheckModel> bclist = selectbll
			.getEmBodyCheckInfo(" and embc_id=" + eadaId);
	private TaskProcessViewModel tmodel = new TaskProcessViewModel();
	private List<TaskProcessViewModel> tlist = new ArrayList<TaskProcessViewModel>();
	private String username = UserInfo.getUsername();
	private Date embcplandate, drawformdate, showformdate, bookdate;
	private BigDecimal fee;
	private CoBaseServePromiseModel pomodel = new CoBaseServePromiseModel();
	private CoServePromiseSelectBll bcbll = new CoServePromiseSelectBll();
	private List<CoBaseServePromiseModel> prlist = new ArrayList<CoBaseServePromiseModel>();

	// 机构地址
	private List<EmBcSetupAddressModel> addresslist = new ArrayList<EmBcSetupAddressModel>();

	private String msg = "";
	private boolean vislinkname = false;

	// 构造函数
	public Embc_RescheduleController() {
		if (Executions.getCurrent().getArg().get("id") != null) {
			tapr_id = Integer.valueOf(Executions.getCurrent().getArg()
					.get("id").toString());
		}
		if (bclist.size() > 0) {
			model = bclist.get(0);
		}
		if (model.getGid() != null && model.getGid() > 0) {
			embaselist = selectbll.getEmBaseInfo(" and gid=" + model.getGid());
			if (embaselist.size() > 0) {
				emmodel = embaselist.get(0);
			}
		}
		if (model != null) {
			embctype = chengeEmbcType(model);
		}
		itemlist = itembll.getEmBcItemInfo(" and ebit_id in("
				+ model.getEbcl_itemnums() + ")");
		if (tapr_id != null && !tapr_id.equals("")) {
			EmArchive_SelectBll blsl = new EmArchive_SelectBll();
			tlist = blsl.getLastId(tapr_id + "");
			if (tlist.size() > 0) {
				tmodel = tlist.get(0);
			}
		}
		if (model.getEbcl_plandate() != null) {
			embcplandate = StrToDate(model.getEbcl_plandate());
		}
		if (model.getEbcl_drawformdate() != null) {
			drawformdate = StrToDate(model.getEbcl_drawformdate());
		}
		if (model.getEbcl_showformdate() != null) {
			showformdate = StrToDate(model.getEbcl_showformdate());
		}
		if (emmodel.getEmba_idcard() == null
				|| emmodel.getEmba_idcard().equals("")) {
			if (model.getEmbc_idcard() != null
					&& !model.getEmbc_idcard().equals("")) {
				emmodel.setEmba_idcard(model.getEmbc_idcard());
			}
		}
		if (model.getEbcl_bookdate() != null) {
			bookdate = StrToDate(model.getEbcl_bookdate());
		}
		prlist = bcbll.getPromiseList("and cid=" + model.getCid());
		if (prlist.size() > 0) {
			pomodel = prlist.get(0);
		}
		// 如果体检联系人是联系本人则联系电话读取体检表的电话号码
		String bclinkname = pomodel.getCosp_bcrp_bclinkname();
		if (bclinkname != null) {
			if (bclinkname.contains("本人")) {
				pomodel.setCosp_bcrp_linknumber(model.getEmbc_mobile());
				pomodel.setCosp_bcrp_email(emmodel.getEmba_email());
			} else if (bclinkname.contains("联系人")) {
				vislinkname = true;
				String a[] = bclinkname.split("—");
				if (a.length > 1) {
					Integer cali_id = 0;
					CoBaseLinkMan_SelectBll lmBll = new CoBaseLinkMan_SelectBll();
					List<CoAgencyLinkmanModel> linklist = lmBll
							.getLinkmanByCid(model.getCid(), 1);
					for (int i = 0; i < linklist.size(); i++) {
						CoAgencyLinkmanModel linkm = linklist.get(i);
						if (linkm.getCali_name().equals(a[1])) {
							cali_id = linkm.getCali_id();
							String mobile = "", email = "";
							if (linkm.getCali_mobile() != null
									&& !linkm.getCali_mobile().equals("")) {
								mobile = mobile + linkm.getCali_mobile() + "、";
							}
							if (linkm.getCali_mobile1() != null
									&& !linkm.getCali_mobile1().equals("")) {
								mobile = mobile + linkm.getCali_mobile1() + "、";
							}
							if (linkm.getCali_mobile2() != null
									&& !linkm.getCali_mobile2().equals("")) {
								mobile = mobile + linkm.getCali_mobile2() + "、";
							}
							if (mobile.length() > 0) {
								mobile = mobile.substring(0,
										mobile.length() - 1);
							}
							pomodel.setCosp_bcrp_linknumber(mobile);
							pomodel.setCosp_bcrp_email(linkm.getCali_email());
						}
					}
				}
			}
		}
		EmBcSetupAddressModel ebam = new EmBcSetupAddressModel();
		ebam.setEbsa_ebcs_id(model.getEbcl_hospital());
		ebam.setEbsa_state(1);
		ebam.setEmbc_confirm(1);
		if (embctype != null && embctype.equals("入职体检")) {
			ebam.setEbsa_istate(1);
		} else if (embctype != null && embctype.equals("年度体检")) {
			ebam.setEbsa_ystate(1);
		}
		addresslist.add(new EmBcSetupAddressModel());
		addresslist.addAll(selectbll.getSetUpAddress(ebam));
		if (model.getEmbc_tapr_id() == null) {
			model.setEmbc_tapr_id(tapr_id);
		}
		setmsgs();
	}

	// 设置提示信息
	public void setmsgs() {
		EmBcSetupModel ebsm = new EmBcSetupModel();
		String SetUpInfo = "";
		msg = "";
		if (!embctype.equals("")) {
			if (embctype.equals("入职体检")) {

				if (model.getEbcl_bookmode().equals("")) {

				} else if (model.getEbcl_bookmode().equals(1)) {// 固定时间
					msg = "需要提前一天预约,体检时间只有当天生效";
				} else {
					msg = "需要提前一天预约,体检时间从预约时间起15天内有效;由于体检时间不确定,福利部将无法及时跟进后续体检结果反馈,请知悉!";
				}
			} else {
				msg = "需要提前三天预约,体检时间从预约时间起60天内有效";
			}

		}

		ebsm.setEbcs_id(model.getEbcl_hospital());
		SetUpInfo = selectbll.getSetupList(ebsm).get(0).getEbcs_info();

		if (SetUpInfo != null && !SetUpInfo.equals("")) {
			msg = SetUpInfo + "." + msg;
		}

	}

	// 把体检类型有数字转换成中文
	private String chengeEmbcType(EmBodyCheckModel m) {
		String type = "";
		if (m.getEbcl_type() != null) {
			if (m.getEbcl_type() == 0) {
				type = "单次体检";
			} else if (m.getEbcl_type() == 1) {
				type = "入职体检";
			} else if (m.getEbcl_type() == 2) {
				type = "年度体检";
			}
		}
		return type;
	}

	// 重新预约
	@Command
	public void confirm(@BindingParam("win") Window win,
			@BindingParam("address") Combobox address) {
		if (bookdate != null) {
			Date d = new Date();
			if (UserInfo.getDepID() != "8") {
				if (embctype.equals("年度体检")) {
					if (DateUtil
							.datediff(DateUtil.getDate(d, 4), bookdate, "d") < 0) {
						Messagebox.show("需要提前三个工作日预约", "提示", Messagebox.OK,
								Messagebox.ERROR);
						return;
					}
				} else if (embctype.equals("入职体检")) {
					if (DateUtil
							.datediff(DateUtil.getDate(d, 2), bookdate, "d") < 0) {
						Messagebox.show("需要提前一个工作日预约", "提示", Messagebox.OK,
								Messagebox.ERROR);
						return;
					}
				}
			}

			EmBcInfo_OperateBll obll = new EmBcInfo_OperateBll();
			Integer easa_id=0;
			String sql = "";
			
			if (address.getValue() != null && !address.getValue().equals("")) {
				easa_id = address.getSelectedItem().getValue();
				sql = sql + ",ebcl_area=" + easa_id;
			} else {
				sql = sql + ",ebcl_area=''";
				Messagebox.show("体检地址不能为空", "提示", Messagebox.OK,
						Messagebox.ERROR);
				return;
			}

			sql = sql + ",ebcl_state=9,ebcl_bookdate='" + changedate(bookdate)
					+ "'";
			model.setOcon("重新预约");
			String[] str = new String[5];
			str = obll.EmBodyCheckReschedule(model, tapr_id, sql);
			if (str[0] == "1") {
				String sqlmarray = ",embc_marry='" + model.getEmbc_marry()
						+ "'";
				obll.updateEmbodyCheckInfo(model.getEmbc_id(), sqlmarray);
				if (pomodel.getCosp_bcrp_bclinkname() != null
						&& pomodel.getCosp_bcrp_bclinkname().contains("本人")) {
					if (pomodel.getCosp_bcrp_email() != null
							&& !pomodel.getCosp_bcrp_email().equals("")) {
						obll.updateEmabseEmail(pomodel.getCosp_bcrp_email(),
								model.getGid());
					}
				}
				Messagebox.show("提交成功", "提示", Messagebox.OK,
						Messagebox.INFORMATION);
				win.detach();
			} else {
				Messagebox.show(str[1], "提示", Messagebox.OK, Messagebox.ERROR);
			}
		} else {
			Messagebox.show("请选择预约时间", "提示", Messagebox.OK, Messagebox.ERROR);
		}
	}

	private Date getNowDate() {
		String y = "";
		Calendar now = Calendar.getInstance();
		Integer year = now.get(Calendar.YEAR);
		Integer month = now.get(Calendar.MONTH) + 1;
		Integer nowdate = now.get(Calendar.DAY_OF_MONTH);
		if (month < 9) {
			y = y + year + "-0" + month;
		} else {
			y = y + year + "-" + month;
		}
		if (nowdate <= 9) {
			y = y + "-0" + nowdate;
		} else {
			y = y + "-" + nowdate;
		}
		Date nowdates = StrToDate(y);
		return nowdates;
	}

	// 打开联系人页面
	@Command
	public void lookinfo() {
		if (pomodel.getCosp_bcrp_bclinkname() != null
				&& pomodel.getCosp_bcrp_bclinkname().contains("联系人")) {
			String a[] = pomodel.getCosp_bcrp_bclinkname().split("—");
			if (a.length > 1) {
				Integer cali_id = 0;
				CoBaseLinkMan_SelectBll lmBll = new CoBaseLinkMan_SelectBll();
				List<CoAgencyLinkmanModel> linklist = lmBll.getLinkmanByCid(
						model.getCid(), 1);
				for (int i = 0; i < linklist.size(); i++) {
					if (linklist.get(i).getCali_name().equals(a[1])) {
						cali_id = linklist.get(i).getCali_id();
					}
				}
				if (cali_id != 0) {
					Map<String, String> map = new HashMap<String, String>();
					map.put("cali_id", String.valueOf(cali_id));
					Window window = (Window) Executions.createComponents(
							"../CoBase/CoBaseLinkMan_Sel.zul", null, map);
					window.doModal();
				}
			}
		}
	}

	public EmbaseModel getEmmodel() {
		return emmodel;
	}

	public void setEmmodel(EmbaseModel emmodel) {
		this.emmodel = emmodel;
	}

	public String getEmbctype() {
		return embctype;
	}

	public void setEmbctype(String embctype) {
		this.embctype = embctype;
	}

	public List<EmBodyCheckItemModel> getItemlist() {
		return itemlist;
	}

	public void setItemlist(List<EmBodyCheckItemModel> itemlist) {
		this.itemlist = itemlist;
	}

	public EmBodyCheckModel getModel() {
		return model;
	}

	public void setModel(EmBodyCheckModel model) {
		this.model = model;
	}

	private String changedate(Date d) {
		String formatDate = null;
		if (d != null) {
			SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
			formatDate = df.format(d);
		}
		return formatDate;
	}

	public TaskProcessViewModel getTmodel() {
		return tmodel;
	}

	public void setTmodel(TaskProcessViewModel tmodel) {
		this.tmodel = tmodel;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	private java.sql.Date changetosqldate(Date d) {
		java.sql.Date date = null;
		if (d != null && !d.equals("")) {
			date = new java.sql.Date(d.getTime());
		}
		return date;
	}

	/**
	 * 字符串转换成日期
	 * 
	 * @param str
	 * @return date
	 */
	public static Date StrToDate(String str) {
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		Date date = null;
		try {
			if (str != null && !str.equals("")) {
				date = format.parse(str);
			}
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return date;
	}

	public Date getEmbcplandate() {
		return embcplandate;
	}

	public void setEmbcplandate(Date embcplandate) {
		this.embcplandate = embcplandate;
	}

	public Date getDrawformdate() {
		return drawformdate;
	}

	public void setDrawformdate(Date drawformdate) {
		this.drawformdate = drawformdate;
	}

	public Date getShowformdate() {
		return showformdate;
	}

	public void setShowformdate(Date showformdate) {
		this.showformdate = showformdate;
	}

	public Date getBookdate() {
		return bookdate;
	}

	public void setBookdate(Date bookdate) {
		this.bookdate = bookdate;
	}

	public BigDecimal getFee() {
		return fee;
	}

	public void setFee(BigDecimal fee) {
		this.fee = fee;
	}

	public CoBaseServePromiseModel getPomodel() {
		return pomodel;
	}

	public void setPomodel(CoBaseServePromiseModel pomodel) {
		this.pomodel = pomodel;
	}

	public List<EmBcSetupAddressModel> getAddresslist() {
		return addresslist;
	}

	public void setAddresslist(List<EmBcSetupAddressModel> addresslist) {
		this.addresslist = addresslist;
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

	public boolean isVislinkname() {
		return vislinkname;
	}

	public void setVislinkname(boolean vislinkname) {
		this.vislinkname = vislinkname;
	}

}
