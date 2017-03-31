package Controller.EmBodyCheck;

import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.zkoss.bind.annotation.BindingParam;
import org.zkoss.bind.annotation.Command;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zul.Combobox;
import org.zkoss.zul.Datebox;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Textbox;
import org.zkoss.zul.Window;

import Model.CoAgencyLinkmanModel;
import Model.CoBaseServePromiseModel;
import Model.EmBcSetupAddressModel;
import Model.EmBcSetupModel;
import Model.EmBodyCheckItemModel;
import Model.EmBodyCheckModel;
import Model.EmbaseModel;
import Model.TaskProcessViewModel;
import Model.embodycheckoperlogModel;
import Util.DateUtil;
import Util.UserInfo;
import bll.Archives.EmArchive_SelectBll;
import bll.CoBase.CoBaseLinkMan_SelectBll;
import bll.CoServePromise.CoServePromiseSelectBll;
import bll.EmBodyCheck.EmBcInfo_OperateBll;
import bll.EmBodyCheck.EmBcInfo_SelectBll;
import bll.EmBodyCheck.EmbcItem_SelectBll;

public class Embc_InfoController {
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
	private String infos = "";

	// 构造函数
	public Embc_InfoController() {
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
				if (model.getEmbc_mobile() != null
						&& !model.getEmbc_mobile().equals("")) {
					pomodel.setCosp_bcrp_linknumber(model.getEmbc_mobile());
				} else if (emmodel.getEmba_mobile() != null
						&& !emmodel.getEmba_mobile().equals("")) {
					pomodel.setCosp_bcrp_linknumber(emmodel.getEmba_mobile());
				}
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
						if (linkm.getCali_name() != null
								&& linkm.getCali_name().equals(a[1])) {
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
		if (selectbll.getmarry(model.getGid())) {
			model.setEmbc_marry("已婚");
		}
		setmsgs();
		isBookDateOk();
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
					msg = "需要提前一个工作日预约,体检时间只有当天生效";
				} else {
					msg = "需要提前一个工作日预约,体检时间从预约时间起15天内有效;由于体检时间不确定,福利部将无法及时跟进后续体检结果反馈,请知悉!";
				}
			} else {
				msg = "需要提前三个工作日预约,体检时间从预约时间起60天内有效";
			}

		}

		ebsm.setEbcs_id(model.getEbcl_hospital());
		SetUpInfo = selectbll.getSetupList(ebsm).get(0).getEbcs_info();

		if (SetUpInfo != null && !SetUpInfo.equals("")) {
			msg = SetUpInfo + "." + msg;
		}
	}

	// 检查预约时间是否符合要求
	private boolean isBookDateOk() {
		boolean flag = true;
		Date d = new Date();

		infos = "";
		if (!embctype.equals("") && bookdate != null) {
			if (UserInfo.getDepID() != "8") {
				if (embctype.equals("入职体检")) {
					if (DateUtil
							.datediff(DateUtil.getDate(d, 2), bookdate, "d") < 0) {
						infos = "需要提前一个工作日预约";
						flag = true;
					}
				} else {
					if (DateUtil
							.datediff(DateUtil.getDate(d, 4), bookdate, "d") < 0) {
						infos = "需要提前三个工作日预约";
						flag = true;
					}

				}
			}

		}
		return flag;
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

	// 确认提交
	@Command
	public void confirm(@BindingParam("win") Window win,
			@BindingParam("address") Combobox address) {
		if (bookdate != null) {
			Integer week[] = new Integer[7];
			if (isBookDateOk() == false) {
				Messagebox.show(infos, "提示", Messagebox.OK, Messagebox.ERROR);
				return;
			}

			Date d = new Date();
			if (DateUtil.datediff(d, bookdate, "d") < 0) {
				Messagebox.show("请选择当天以后的日期.", "提示", Messagebox.OK,
						Messagebox.ERROR);
				return;
			}
			if (!UserInfo.getDepID().equals("8")) {
				if (model.getEbcl_type().equals(2)) {
					if (DateUtil
							.datediff(DateUtil.getDate(d, 3), bookdate, "d") < 0) {
						Messagebox.show("请选择3个工作日以后的日期.", "提示", Messagebox.OK,
								Messagebox.ERROR);
						return;
					}
				} else if (model.getEbcl_type().equals(1)) {

					if (DateUtil
							.datediff(DateUtil.getDate(d, 1), bookdate, "d") < 0) {
						Messagebox.show("请选择1个工作日以后的日期.", "提示", Messagebox.OK,
								Messagebox.ERROR);
						return;
					}
				}
			}

			Date nd = getNowDate();
			EmBcInfo_OperateBll obll = new EmBcInfo_OperateBll();
			Integer easa_id = 0;
			String sql = "";
			if (address.getValue() != null && !address.getValue().equals("")) {
				if (address.getSelectedItem() != null) {
					easa_id = address.getSelectedItem().getValue();
					sql = sql + ",ebcl_area=" + easa_id;
					for (EmBcSetupAddressModel m : addresslist) {
						if (m.getEbsa_id() != null
								&& m.getEbsa_id().equals(easa_id)) {
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
			} else {
				sql = sql + ",ebcl_area=''";
				Messagebox.show("体检地址不能为空", "提示", Messagebox.OK,
						Messagebox.ERROR);
				return;
			}

			if (model.getEbcl_type().equals(2)) {

				if (model.getEmbc_sex().equals("女")) {
					if (model.getEmbc_marry() == null
							|| model.getEmbc_marry().equals("")) {
						Messagebox.show("请选择婚姻状况", "提示", Messagebox.OK,
								Messagebox.ERROR);
						return;
					}
				}

			}

			sql = sql + ",ebcl_state=2,ebcl_bookdate='" + changedate(bookdate)
					+ "'";
			model.setOcon("确认体检信息");
			String[] str = new String[5];
			str = obll.EmBodyCheckEdit(model, sql, "2");
			if (str[0] == "1") {
				embodycheckoperlogModel logm = new embodycheckoperlogModel();
				logm.setBclg_addname(UserInfo.getUsername());
				logm.setBclg_content("确认并申报了体检信息");
				logm.setBclg_ebcl_id(model.getEbcl_id());
				obll.insertLog(logm);// 插入操作记录
				if (model.getEmbc_tapr_id() != null) {
					model.setEmbc_tapr_id(Integer.parseInt(str[2]));
				}
				String sqlmarray = ",embc_marry='" + model.getEmbc_marry()
						+ "'";
				obll.updateEmbodyCheckInfo(model.getEmbc_id(), sqlmarray);

				// 更新婚姻状况
				if (model.getEmbc_marry() != null
						&& model.getEmbc_marry().equals("已婚")) {
					obll.updateEmabseMarital(model.getEmbc_marry(),
							model.getGid());
					// 已婚女性调整为已婚项目
					obll.modItem(model.getEbcl_itemnums(), model.getEbcl_id());

				}
				if (pomodel.getCosp_bcrp_bclinkname() != null
						&& pomodel.getCosp_bcrp_bclinkname().contains("本人")) {
					if (pomodel.getCosp_bcrp_email() != null
							&& !pomodel.getCosp_bcrp_email().equals("")) {
						obll.updateEmabseEmail(pomodel.getCosp_bcrp_email(),
								model.getGid());
					}
				}
				// 判断是否是入职体检，如果是入职体检则直接变为体检中
				if (embctype.equals("入职体检")) {
					String sqlruz = ",ebcl_state=3";
					model.setOcon("入职体检，跳过预约中");
					obll.EmBodyCheckSkipToNext(model, sqlruz);
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

	// 申报提交
	@Command
	public void declare(@BindingParam("win") Window win) {
		Datebox declaredate = (Datebox) win.getFellow("declaredate");// 申报时间
		Datebox plandate = (Datebox) win.getFellow("plandate");// 安排体检时间
		Textbox embcid = (Textbox) win.getFellow("embcid");// 保健号
		Datebox drawformdate = (Datebox) win.getFellow("drawformdate");// 领取指引单时间
		Datebox showformdate = (Datebox) win.getFellow("showformdate");// 签收指引单时间
		Textbox showformpeople = (Textbox) win.getFellow("showformpeople");// 签收指引单人
		// Datebox drawreportdate = (Datebox) win.getFellow("drawreportdate");//
		// 领取体检报告时间
		Datebox completeDate = (Datebox) win.getFellow("completeDate");// 完成体检时间
		Datebox showreportdate = (Datebox) win.getFellow("showreportdate");// 收报告时间
		Textbox showreportpeople = (Textbox) win.getFellow("showreportpeople");// 收报人
		Datebox clientshowdate = (Datebox) win.getFellow("clientshowdate");// 客服签收报告时间
		Textbox showclient = (Textbox) win.getFellow("showclient");// 签收报告客服
		Datebox balancedate = (Datebox) win.getFellow("balancedate");// 结算时间
		// Textbox balancepeopt = (Textbox) win.getFellow("balancepeopt");// 结算人
		String sql = "";
		String info = "";
		if (tmodel.getWfno_step() != null) {
			if (tmodel.getWfno_step() == 3) {// 预约体检
				if (declaredate.getValue() != null) {
					sql = ",ebcl_linkdate='"
							+ changedate(declaredate.getValue())
							+ "',ebcl_state=10";
					model.setOcon("预约体检");
				} else {
					info = "请选择联系医院时间";
				}
			} else if (tmodel.getWfno_step() == 4) {// 安排体检
				if (plandate.getValue() != null) {
					// if (embcid.getValue() != null &&
					// !embcid.getValue().equals("")) {
					sql = ",ebcl_plandate='" + changedate(plandate.getValue())
							+ "',ebcl_state=3";
					model.setOcon("安排员工体检");
					// } else {
					// info = "请填写保健号";
					// }
				} else {
					info = "请选择安排体检时间";
				}
				if (drawformdate.getValue() != null)// 领取指引单时间
				{
					sql = sql + ",ebcl_drawformdate='"
							+ changedate(drawformdate.getValue()) + "'";
				}
				if (showformdate.getValue() != null)// 签收指引单时间
				{
					sql = sql + ",ebcl_showformdate='"
							+ changedate(showformdate.getValue()) + "'";
					model.setOcon("签收指引单");
				}
				if (showformpeople.getValue() != null)// 指引单签收人
				{
					sql = sql + ",ebcl_showformpeople='"
							+ showformpeople.getValue() + "'";
				}
			} else if (tmodel.getWfno_step() == 5) {// 体检中
				sql = ",ebcl_state=4";
				if (showreportdate.getValue() != null) {
					sql = sql + ",ebcl_showreportdate='"
							+ changedate(showreportdate.getValue())
							+ "',ebcl_showreportpeople='"
							+ showreportpeople.getValue() + "'";
					model.setOcon("员工完成体检");
					if (embcid.getValue() != null
							&& !embcid.getValue().equals("")) {
						sql = sql + ",ebcl_bcid='" + embcid.getValue() + "'";
					}
				}
				if (completeDate.getValue() != null) {
					sql = sql + ",ebcl_completedate='"
							+ changedate(completeDate.getValue()) + "'";
				} else {
					info = "请选择收报告时间";
				}
			} else if (tmodel.getWfno_step() == 8) {
				if (clientshowdate.getValue() != null) {
					sql = ",ebcl_clientshowdate='"
							+ changedate(clientshowdate.getValue())
							+ "',ebcl_showclient='" + showclient.getValue()
							+ "',ebcl_state=12";
					model.setOcon("客服签收体检报告");
				} else {
					info = "请选择签收时间";
				}
			} else if (tmodel.getWfno_step() == 9) {
				if (balancedate.getValue() != null) {
					if (fee != null) {
						sql = ",ebcl_balancedate='"
								+ changedate(balancedate.getValue())
								+ "',ebcl_state=11,ebcl_finalcharge='" + fee
								+ "'";
						model.setOcon("费用结算");
					} else {
						info = "请输入实际体检费";
					}
				} else {
					info = "请选择结算时间";
				}
			}
		} else {
			if (model.getEbcl_state() != null) {
				if (model.getEbcl_state().equals(2)) {
					if (declaredate.getValue() != null) {
						sql = ",ebcl_linkdate='"
								+ changedate(declaredate.getValue())
								+ "',ebcl_state=10";
						model.setOcon("预约体检");
					} else {
						info = "请选择联系医院时间";
					}
				} else if (model.getEbcl_state().equals(10)) {
					if (plandate.getValue() != null) {
						sql = ",ebcl_plandate='"
								+ changedate(plandate.getValue())
								+ "',ebcl_state=3";
						model.setOcon("安排员工体检");
					} else {
						info = "请选择安排体检时间";
					}
					if (drawformdate.getValue() != null)// 领取指引单时间
					{
						sql = sql + ",ebcl_drawformdate='"
								+ changedate(drawformdate.getValue()) + "'";
					}
					if (showformdate.getValue() != null)// 签收指引单时间
					{
						sql = sql + ",ebcl_showformdate='"
								+ changedate(showformdate.getValue()) + "'";
						model.setOcon("签收指引单");
					}
					if (showformpeople.getValue() != null)// 指引单签收人
					{
						sql = sql + ",ebcl_showformpeople='"
								+ showformpeople.getValue() + "'";
					}
				} else if (model.getEbcl_state().equals(3)) {
					sql = ",ebcl_state=4";
					if (showreportdate.getValue() != null) {
						sql = sql + ",ebcl_showreportdate='"
								+ changedate(showreportdate.getValue())
								+ "',ebcl_showreportpeople='"
								+ showreportpeople.getValue() + "'";
						model.setOcon("员工完成体检");
						if (embcid.getValue() != null
								&& !embcid.getValue().equals("")) {
							sql = sql + ",ebcl_bcid='" + embcid.getValue()
									+ "'";
						}
					}
					if (completeDate.getValue() != null) {
						sql = sql + ",ebcl_completedate='"
								+ changedate(completeDate.getValue()) + "'";
					} else {
						info = "请选择收报告时间";
					}
				} else if (model.getEbcl_state().equals(4)) {
					if (clientshowdate.getValue() != null) {
						sql = ",ebcl_clientshowdate='"
								+ changedate(clientshowdate.getValue())
								+ "',ebcl_showclient='" + showclient.getValue()
								+ "',ebcl_state=12";
						model.setOcon("客服签收体检报告");
					} else {
						info = "请选择签收时间";
					}
				} else if (model.getEbcl_state().equals(4)) {
					if (clientshowdate.getValue() != null) {
						sql = ",ebcl_clientshowdate='"
								+ changedate(clientshowdate.getValue())
								+ "',ebcl_showclient='" + showclient.getValue()
								+ "',ebcl_state=12";
						model.setOcon("客服签收体检报告");
					} else {
						info = "请选择签收时间";
					}
				} else if (model.getEbcl_state().equals(12)) {
					if (balancedate.getValue() != null) {
						if (fee != null) {
							sql = ",ebcl_balancedate='"
									+ changedate(balancedate.getValue())
									+ "',ebcl_state=11,ebcl_finalcharge='"
									+ fee + "'";
							model.setOcon("费用结算");
						} else {
							info = "请输入实际体检费";
						}
					} else {
						info = "请选择结算时间";
					}
				}
			}
		}
		if (info == "") {
			EmBcInfo_OperateBll obll = new EmBcInfo_OperateBll();
			String[] str = obll.EmBodyCheckEdit(model, sql, "3");
			if (str[0] == "1") {
				Messagebox.show("提交成功", "提示", Messagebox.OK,
						Messagebox.INFORMATION);
				win.detach();
			} else {
				Messagebox.show(str[1], "提示", Messagebox.OK, Messagebox.ERROR);
			}
		} else {
			Messagebox.show(info, "提示", Messagebox.OK, Messagebox.ERROR);
		}
	}

	// 暂存
	@Command
	public void scratch(@BindingParam("win") Window win) {
		Datebox declaredate = (Datebox) win.getFellow("declaredate");// 申报时间
		Datebox plandate = (Datebox) win.getFellow("plandate");// 安排体检时间
		Datebox drawformdate = (Datebox) win.getFellow("drawformdate");// 领取指引单时间
		Datebox showformdate = (Datebox) win.getFellow("showformdate");// 签收指引单时间
		Textbox showformpeople = (Textbox) win.getFellow("showformpeople");// 签收指引单人
		// Datebox drawreportdate = (Datebox) win.getFellow("drawreportdate");//
		// 领取体检报告时间
		Textbox embcid = (Textbox) win.getFellow("embcid");// 保健号
		Datebox completeDate = (Datebox) win.getFellow("completeDate");// 完成体检时间
		Datebox showreportdate = (Datebox) win.getFellow("showreportdate");// 收报告时间
		Textbox showreportpeople = (Textbox) win.getFellow("showreportpeople");// 收报人
		Datebox clientshowdate = (Datebox) win.getFellow("clientshowdate");// 客服签收报告时间
		Textbox showclient = (Textbox) win.getFellow("showclient");// 签收报告客服
		Datebox balancedate = (Datebox) win.getFellow("balancedate");// 结算时间
		// Textbox balancepeopt = (Textbox) win.getFellow("balancepeopt");// 结算人
		String sql = "";
		if (tmodel.getWfno_step() == 3) {// 预约体检
			sql = ",ebcl_linkdate='" + changedate(declaredate.getValue()) + "'";
		} else if (tmodel.getWfno_step() == 4) {// 安排体检
			if (plandate.getValue() != null) {
				sql = ",ebcl_plandate='" + changedate(plandate.getValue())
						+ "',ebcl_bcid='" + embcid.getValue() + "'";

			}
			if (drawformdate.getValue() != null)// 领取指引单时间
			{
				sql = sql + ",ebcl_drawformdate='"
						+ changedate(drawformdate.getValue()) + "'";
			}
			if (showformdate.getValue() != null)// 签收指引单时间
			{
				sql = sql + ",ebcl_showformdate='"
						+ changedate(showformdate.getValue()) + "'";
			}
			if (showformpeople.getValue() != null)// 指引单签收人
			{
				sql = sql + ",ebcl_showformpeople='"
						+ showformpeople.getValue() + "'";
			}
		} else if (tmodel.getWfno_step() == 5) {// 体检中
			if (completeDate.getValue() != null) {
				sql = sql + ",ebcl_completedate='"
						+ changedate(completeDate.getValue()) + "'";
			}
			if (showreportdate.getValue() != null) {
				sql = sql + ",ebcl_showreportdate='"
						+ changedate(showreportdate.getValue()) + "'";
			}
			if (showreportpeople.getValue() != null) {
				sql = sql + "ebcl_showreportpeople='"
						+ showreportpeople.getValue() + "'";
			}
		} else if (tmodel.getWfno_step() == 8) {
			sql = ",ebcl_clientshowdate='"
					+ changedate(clientshowdate.getValue())
					+ "',ebcl_showclient='" + showclient.getValue() + "'";
		} else if (tmodel.getWfno_step() == 9) {
			if (balancedate.getValue() != null) {
				sql = ",ebcl_balancedate='"
						+ changedate(balancedate.getValue()) + "'";
			}
		}
		EmBcInfo_OperateBll obll = new EmBcInfo_OperateBll();
		Integer k = obll.scratch(model.getEbcl_id(), sql);
		if (k > 0) {
			Messagebox
					.show("提交成功", "提示", Messagebox.OK, Messagebox.INFORMATION);
			win.detach();
		} else {
			Messagebox.show("提交失败", "提示", Messagebox.OK, Messagebox.ERROR);
		}
	}

	// 弹出取消员工体检页面
	@Command
	public void cancel(@BindingParam("win") Window win) {
		if (model.getEmbc_id() != null) {
			Map map = new HashMap<>();
			map.put("model", model);
			map.put("tarpid", tapr_id);
			map.put("flag", "0");
			Window window = (Window) Executions.createComponents(
					"../EmBodyCheck/Embc_Cancel.zul", null, map);
			window.doModal();
			if (map.get("flag").equals("1")) {
				win.detach();
			}
		} else {
			Messagebox.show("没有该员工体检信息", "提示", Messagebox.OK, Messagebox.ERROR);
		}
	}

	// 退回客服
	@Command
	public void backclient(@BindingParam("win") Window win) {
		if (model.getEmbc_id() != null) {
			Map map = new HashMap<>();
			map.put("model", model);
			map.put("tarpid", tapr_id);
			map.put("flag", "0");
			Window window = (Window) Executions.createComponents(
					"../EmBodyCheck/Embc_BackClient.zul", null, map);
			window.doModal();
			if (map.get("flag").equals("1")) {
				win.detach();
			}
		} else {
			Messagebox.show("没有该员工体检信息", "提示", Messagebox.OK, Messagebox.ERROR);
		}
	}

	// 弹出退回员工体检页面
	@Command
	public void back(@BindingParam("win") Window win) {
		if (model.getEmbc_id() != null) {
			Map map = new HashMap<>();
			map.put("model", model);
			map.put("tarpid", tapr_id);
			map.put("flag", "0");
			Window window = (Window) Executions.createComponents(
					"../EmBodyCheck/Embc_Back.zul", null, map);
			window.doModal();
			if (map.get("flag").equals("1")) {
				win.detach();
			}
		} else {
			Messagebox.show("没有该员工体检信息", "提示", Messagebox.OK, Messagebox.ERROR);
		}
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
					if (linklist.get(i).getCali_name() != null
							&& linklist.get(i).getCali_name().equals(a[1])) {
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

	/**
	 * 计算两个日期之间相差的天数
	 * 
	 * @param smdate
	 *            较小的时间
	 * @param bdate
	 *            较大的时间
	 * @return 相差天数
	 * @throws ParseException
	 */
	public static int daysBetween(Date smdate, Date bdate) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		try {
			smdate = sdf.parse(sdf.format(smdate));
			bdate = sdf.parse(sdf.format(bdate));
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		Calendar cal = Calendar.getInstance();
		cal.setTime(smdate);
		long time1 = cal.getTimeInMillis();
		cal.setTime(bdate);
		long time2 = cal.getTimeInMillis();
		long between_days = (time2 - time1) / (1000 * 3600 * 24);

		return Integer.parseInt(String.valueOf(between_days));
	}
}
