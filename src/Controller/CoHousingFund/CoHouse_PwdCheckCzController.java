package Controller.CoHousingFund;

import impl.MessageImpl;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.zkoss.bind.annotation.BindingParam;
import org.zkoss.bind.annotation.Command;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.event.EventListener;
import org.zkoss.zul.Button;
import org.zkoss.zul.Checkbox;
import org.zkoss.zul.Combobox;
import org.zkoss.zul.Datebox;
import org.zkoss.zul.Grid;
import org.zkoss.zul.Label;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Row;
import org.zkoss.zul.Window;
import org.zkoss.zul.Messagebox.ClickEvent;

import service.MessageService;

import Controller.DocumentsInfo.DocumentsInfo_OperationController;
import Model.CoHousingFundPwdChangeModel;
import Model.CoHousingFundPwdModel;
import Model.CoHousingFundZbModel;
import Model.SysMessageModel;
import Util.SingleBllFactory;
import Util.UserInfo;
import bll.CoHousingFund.CoHousingFundZbBll;
import bll.CoHousingFund.CoHousingFund_PwdBll;

public class CoHouse_PwdCheckCzController {

	private String Remark;
	private String addYearLimit;
	private String currentDate;
	private String pwdName;
	private String pwdNumber;
	private Date addStartDate;
	private Date addEndDate;
	private Date reneStartDate;
	private Date reneEndDate;
	private String reason; // 退回原因
	private String yName;
	private String yNumber;
	private String newName;
	private String newNumber;
	private String renewalLimit;
	private String nameAndNumber;
	private String name;
	private String number;
	private String cid;
	private int addZbid;
	private int chfz_id;
	private int chfp_id;
	private int cfpc_id;
	boolean f2 = false;
	private boolean f = false;
	private boolean addflag = false;
	private boolean updateflag = false;
	private boolean reneflag = false;
	private List<CoHousingFundPwdModel> pwdList;
	private CoHousingFundPwdChangeModel getDate;
	private DocumentsInfo_OperationController docOC = new DocumentsInfo_OperationController();

	private CoHousingFundZbBll cfzb = SingleBllFactory.getInstance().getChzb();
	private CoHousingFund_PwdBll cfpb = SingleBllFactory.getInstance()
			.getCfpb();
	private List<CoHousingFundZbModel> notPwdZb;
	private CoHousingFundPwdModel m;
	private CoHousingFundPwdChangeModel cfpc;
	private Map<String, CoHousingFundPwdChangeModel> map = (Map<String, CoHousingFundPwdChangeModel>) Executions
			.getCurrent().getArg();

	private CoHousingFundPwdChangeModel addchpc;
	Set<String> keySet = map.keySet();
	Iterator it = keySet.iterator();

	public CoHouse_PwdCheckCzController() {
		String key = null;
		while (it.hasNext()) {
			key = (String) it.next();
			if (!"".equals(key) && key.equals("cfpc")) {
				cfpc = map.get(key);
			}
		}
		cfpc_id = cfpc.getCfpc_id();
		// zbList = cfzb.getZbListBycohf_id(cfpc.getCfpc_cohf_id().toString());
		cid = cfpc.getCid();
		Remark = cfpc.getCfpc_remark();
		addYearLimit = cfpc.getCfpc_yearlimit().toString();
		nameAndNumber = cfpc.getCfpc_zb_name() + cfpc.getCfpc_zb_number();
		currentDate = new SimpleDateFormat("yyyyMM").format(System
				.currentTimeMillis());
		yName = cfpc.getCfpc_zb_name();
		yNumber = cfpc.getCfpc_zb_number();
		renewalLimit = cfpc.getCfpc_yearlimit().toString();

		// 该单位所有可用的密钥
		pwdList = cfpb.allPwd(cfpc.getCfpc_cohf_id());

		// 查询该单位没有密钥的专办员
		System.out.println(cfpc.getCfpc_cohf_id());
		notPwdZb = cfpb.notPwdZb(cfpc.getCfpc_cohf_id());

		getDate = cfpb.getDate(cfpc.getCfpc_id());

		addchpc = cfpb.getPwdChangeByid(cfpc.getCfpc_id());
		if (addchpc.getCfpc_addtype().equals("申请数字证书")) {
			addflag = addchpc.getBflag();
			addStartDate = addchpc.getStartdate();
			addEndDate = addchpc.getEnddate();
			chfp_id = addchpc.getCfpc_chfp_id();
			nameAndNumber = addchpc.getNumberAndName();
			name = addchpc.getCfpc_zb_name();
			number = addchpc.getCfpc_zb_number();
			chfz_id = addchpc.getCfpc_chfz_id();
			addYearLimit = addchpc.getCfpc_yearlimit().toString();
			cfpc_id = addchpc.getCfpc_id();

		} else if (addchpc.getCfpc_addtype().equals("数字证书续期")) {
			reneflag = addchpc.getBflag();
			reneStartDate = addchpc.getStartdate();
			reneEndDate = addchpc.getEnddate();
			chfp_id = addchpc.getCfpc_chfp_id();
			nameAndNumber = addchpc.getNumberAndName();
			name = addchpc.getCfpc_zb_name();
			number = addchpc.getCfpc_zb_number();
			chfz_id = addchpc.getCfpc_chfz_id();
			renewalLimit = addchpc.getCfpc_yearlimit().toString();
			cfpc_id = addchpc.getCfpc_id();
		} else if (addchpc.getCfpc_addtype().equals("密钥专办员变更")) {
			updateflag = addchpc.getBflag();
			name = addchpc.getCfpc_zb_name();
			number = addchpc.getCfpc_zb_number();
			chfz_id = addchpc.getCfpc_chfz_id();
			chfp_id = addchpc.getCfpc_chfp_id();
			cfpc_id = addchpc.getCfpc_id();
			System.out.println(chfp_id);
			m = cfpb.getPwdByid(chfp_id);
			nameAndNumber = m.getChfp_zb_name() + m.getChfp_zb_number();

		}

	}

	@Command
	public void addDateCheck() {
		if (addStartDate == null || addEndDate == null) {
			Messagebox.show("请录入日期信息");
		} else {
			if (!addStartDate.before(addEndDate)) {
				Messagebox.show("起始时间请在到期时间之后");
			}
		}
	}

	@Command
	public void reneDateCheck() {
		if (reneStartDate == null || reneEndDate == null) {
			Messagebox.show("请录入日期信息");
		} else {
			if (!reneStartDate.before(reneEndDate)) {
				Messagebox.show("起始时间请在到期时间之后");
			}
		}
	}

	@Command
	public void apply(@BindingParam("addZbid") int addZbid) {
		this.addZbid = addZbid;
	}

	@Command
	public void change(@BindingParam("chfz_id") int chfz_id) {
		this.chfz_id = chfz_id;
	}

	@Command
	public void renewal(@BindingParam("chfp_id") int chfp_id) {
		this.chfp_id = chfp_id;
	}

	/**
	 * 修改状态
	 */
	@Command
	public void updateStatus(@BindingParam("renewalcheck") Checkbox renewalc,
			@BindingParam("updatecheck") Checkbox updatec,
			@BindingParam("addcheck") Checkbox addc,
			@BindingParam("status") int status,
			@BindingParam("wind") final Window win) {
		final CoHousingFundPwdChangeModel cfcm = new CoHousingFundPwdChangeModel();
		cfcm.setCfpc_id(cfpc.getCfpc_id());
		cfcm.setBackReason(reason);
		cfcm.setCfpc_state(status);

		if (status == 3) {
			if (addc.isChecked()) {
				if (addflag || f) {
					final CoHousingFundPwdModel cfpm = new CoHousingFundPwdModel();
					cfpm.setChfp_chfz_id(addchpc.getCfpc_chfz_id());
					cfpm.setChfp_addname(Executions.getCurrent().getDesktop()
							.getSession().getAttribute("username").toString());
					cfpm.setOwnmonth(currentDate);
					cfpm.setChfp_cohf_id(cfpc.getCfpc_cohf_id());
					cfpm.setChfp_zb_name(addchpc.getCfpc_zb_name());
					cfpm.setChfp_zb_number(addchpc.getCfpc_zb_number());
					cfpm.setChfp_state(1);
					cfpm.setChfp_startdate(addchpc.getStartdate());
					cfpm.setChfp_enddate(addchpc.getEnddate());
					cfpm.setChfp_yearlimit(Integer.valueOf(addYearLimit));
					EventListener<ClickEvent> clickListener = new EventListener<Messagebox.ClickEvent>() {
						public void onEvent(ClickEvent event) throws Exception {
							if (Messagebox.Button.YES.equals(event.getButton())) {
								if (!f2) {
									// 状态3说明已申报
									boolean flag2 = cfpb.addPwd(cfpm,
											cfpc.getCfpc_id());
									if (flag2) {
										boolean flag = cfpb.changeStatus(cfcm);
										if (flag) {
											f2 = true;
											Messagebox.show("业务办理成功");
											win.detach();
										}
									}
								} else {
									Messagebox.show("请不要重复申报");
								}
							}
						}
					};
					Messagebox.show("确认完成申报？确认后将不能修改资料", null,
							new Messagebox.Button[] { Messagebox.Button.YES,
									Messagebox.Button.NO },
							Messagebox.QUESTION, clickListener);
				} else {
					Messagebox.show("还没有录入资料");
				}
			} else if (updatec.isChecked()) {
				if (updateflag || f) {
					final CoHousingFundPwdModel cfpm = new CoHousingFundPwdModel();
					cfpm.setChfp_chfz_id(addchpc.getCfpc_chfz_id());
					cfpm.setChfp_id(addchpc.getCfpc_chfp_id());
					cfpm.setChfp_addname(Executions.getCurrent().getDesktop()
							.getSession().getAttribute("username").toString());
					// if (name != null) {
					cfpm.setChfp_zb_name(addchpc.getCfpc_zb_name());
					cfpm.setChfp_zb_number(addchpc.getCfpc_zb_number());
					// } else {

					EventListener<ClickEvent> clickListener = new EventListener<Messagebox.ClickEvent>() {

						public void onEvent(ClickEvent event) throws Exception {
							if (Messagebox.Button.YES.equals(event.getButton())) {
								if (!f2) {
									// 状态3说明已申报
									boolean flag4 = cfpb.changePwd(cfpm,
											cfpc.getCfpc_id());
									if (flag4) {
										boolean flag = cfpb.changeStatus(cfcm);
										if (flag) {
											f2 = true;
											Messagebox.show("业务办理成功");
											win.detach();
										}
									}
								} else {
									Messagebox.show("请不要重复申报");
								}
							}
						}
					};
					Messagebox.show("确认完成申报？确认后将不能修改资料", null,
							new Messagebox.Button[] { Messagebox.Button.YES,
									Messagebox.Button.NO },
							Messagebox.QUESTION, clickListener);

				} else {
					Messagebox.show("还没有录入资料");
				}
			} else if (renewalc.isChecked()) {
				if (reneflag || f) {
					final CoHousingFundPwdModel cfpm = new CoHousingFundPwdModel();
					cfpm.setChfp_yearlimit(Integer.valueOf(renewalLimit));
					cfpm.setOwnmonth(currentDate);
					cfpm.setChfp_id(addchpc.getCfpc_chfp_id());
					cfpm.setChfp_startdate(addchpc.getStartdate());
					cfpm.setChfp_enddate(addchpc.getEnddate());
					cfpm.setChfp_addname(Executions.getCurrent().getDesktop()
							.getSession().getAttribute("username").toString());
					cfpm.setChfp_zb_name(addchpc.getCfpc_zb_name());
					cfpm.setChfp_zb_number(addchpc.getCfpc_zb_number());
					EventListener<ClickEvent> clickListener = new EventListener<Messagebox.ClickEvent>() {
						public void onEvent(ClickEvent event) throws Exception {
							if (Messagebox.Button.YES.equals(event.getButton())) {
								if (!f2) {
									// 状态3说明已申报
									boolean flag3 = cfpb.renewalPwd(cfpm,
											cfpc.getCfpc_id());
									if (flag3) {
										boolean flag = cfpb.changeStatus(cfcm);
										if (flag) {
											f2 = true;
											Messagebox.show("业务办理成功");
											win.detach();
										}
									}
								} else {
									Messagebox.show("请不要重复申报");
								}
							}
						}
					};
					Messagebox.show("确认完成申报？确认后将不能修改资料", null,
							new Messagebox.Button[] { Messagebox.Button.YES,
									Messagebox.Button.NO },
							Messagebox.QUESTION, clickListener);

				} else {
					Messagebox.show("还没有录入资料");
				}
			}
		} else if (status == 4) {
			if (reason != null) {
				// 退回
				MessageService msgservice = new MessageImpl(
						"CoHousingFundPwdChange", cfpc.getCfpc_id());
				SysMessageModel model = new SysMessageModel();
				model.setSyme_content(reason);// 短信内容
				model.setSyme_url("");
				model.setSyme_reply_id(0);
				model.setSyme_log_id(Integer.parseInt(UserInfo.getUserid()));// 发件人id
				model.setSmwr_type("");
				model.setSymr_name(cfpc.getCfpc_addname());// 收件人姓名
				boolean flag = cfpb.changeStatus(cfcm);
				if (flag) {
					msgservice.Add(model);
					Messagebox.show("退回成功");
					win.detach();
				}
			} else {
				Messagebox.show("填写退回原因");
			}
		} else {
			boolean flag = cfpb.changeStatus(cfcm);
			if (flag) {
				Messagebox.show("修改成功");
			}
		}
	}

	@Command
	public void selectPage(@BindingParam("addRow") Row addRow,
			@BindingParam("updateRow") Row updateRow,
			@BindingParam("showRenewalRow") Row showRenewalRow,
			@BindingParam("renewalcheck") Checkbox renewalc,
			@BindingParam("updatecheck") Checkbox updatec,
			@BindingParam("addcheck") Checkbox addc,
			@BindingParam("button") Button button,
			@BindingParam("statusRow") Row statusRow,
			@BindingParam("gd") Grid gd, @BindingParam("gd1") Grid gd1,
			@BindingParam("gd2") Grid gd2,
			@BindingParam("renewalCombo") Combobox c,
			@BindingParam("addCombox") Combobox addcombo,
			@BindingParam("xqsid") Label xqsl,
			@BindingParam("xqeid") Label xqel,
			@BindingParam("sqsid") Label sqsl,
			@BindingParam("sqeid") Label sqel,
			@BindingParam("sqsdid") Datebox sqsd,
			@BindingParam("sqedid") Datebox sqed,
			@BindingParam("xqsdid") Datebox xqsd,
			@BindingParam("xqedid") Datebox xqed,
			@BindingParam("changerow") Row changerow) {
		// 传map过来,根据变动情况选择打开页面
		if (cfpc.getCfpc_addtype().equals("申请数字证书")) {
			addc.setChecked(true);
			addc.setDisabled(true);
			addRow.setVisible(true);
			showRenewalRow.setVisible(false);
			renewalc.setDisabled(true);
			updatec.setDisabled(true);
			updateRow.setVisible(false);
			gd1.setVisible(false);
			gd.setVisible(true);
			gd2.setVisible(false);

		} else if (cfpc.getCfpc_addtype().equals("数字证书续期")) {
			renewalc.setChecked(true);
			renewalc.setDisabled(true);
			showRenewalRow.setVisible(true);
			addRow.setVisible(false);
			addc.setDisabled(true);
			updatec.setDisabled(true);
			updateRow.setVisible(false);
			gd1.setVisible(true);
			gd.setVisible(false);
			gd2.setVisible(false);

		} else if (cfpc.getCfpc_addtype().equals("密钥专办员变更")) {
			updatec.setChecked(true);
			updatec.setDisabled(true);
			updateRow.setVisible(true);
			showRenewalRow.setVisible(false);
			renewalc.setDisabled(true);
			addRow.setVisible(false);
			addc.setDisabled(true);
			gd1.setVisible(false);
			gd.setVisible(false);
			gd2.setVisible(true);
		} else {
			gd1.setVisible(false);
			gd.setVisible(false);
			gd2.setVisible(false);
		}

		if (cfpc.getMark() != null && cfpc.getMark().equals("mark")) {
			statusRow.setVisible(false);
			button.setVisible(false);
			updatec.setDisabled(true);
			addc.setDisabled(true);
			renewalc.setDisabled(true);
			c.setVisible(false);
			addcombo.setVisible(false);
			sqsl.setVisible(true);
			sqsd.setVisible(false);
			sqel.setVisible(true);
			sqed.setVisible(false);
			xqsl.setVisible(true);
			xqsd.setVisible(false);
			xqel.setVisible(true);
			xqed.setVisible(false);
		}
	}

	@Command
	public void submit(@BindingParam("renewalcheck") Checkbox renewalc,
			@BindingParam("updatecheck") Checkbox updatec,
			@BindingParam("addcheck") Checkbox addc,
			@BindingParam("wind") Window win,
			@BindingParam("gd") final Grid gd,
			@BindingParam("gd1") final Grid gd1,
			@BindingParam("gd2") final Grid gd2) {
		try {
			if (addc.isChecked()) {
				if (addZbid != 0 || addflag) {
					if (addStartDate != null && addEndDate != null) {
						if (addStartDate.before(addEndDate)) {

							// 申请新的密钥专办员，先查询该单位的该专办员是否已经存在并且在使用中的密钥，
							if (cfpb.havePwd(cfpc.getCfpc_cohf_id(), addZbid)) {
								// 如果为true说明该单位的该专办员不存在使用中的密钥，可以添加
								final CoHousingFundPwdModel cfpm = new CoHousingFundPwdModel();
								cfpm.setChfp_chfz_id(addZbid);
								cfpm.setChfp_addname(Executions.getCurrent()
										.getDesktop().getSession()
										.getAttribute("username").toString());
								cfpm.setOwnmonth(currentDate);
								cfpm.setChfp_cohf_id(cfpc.getCfpc_cohf_id());
								for (int i = 0; i < notPwdZb.size(); i++) {
									if (notPwdZb.get(i).getChfz_id() == addZbid) {
										cfpm.setChfp_zb_name(notPwdZb.get(i)
												.getChfz_name());
										cfpm.setChfp_zb_number(notPwdZb.get(i)
												.getChfz_number());
									}
								}
								cfpm.setChfp_state(1);
								cfpm.setChfp_startdate(addStartDate);
								cfpm.setChfp_enddate(addEndDate);
								cfpm.setChfp_yearlimit(Integer
										.valueOf(addYearLimit));
								cfpm.setFlag(1);

								String[] message = docOC.UpchkHaveTo(gd);
								if (message[0] == "1") {
									EventListener<ClickEvent> clickListener = new EventListener<Messagebox.ClickEvent>() {
										public void onEvent(ClickEvent event)
												throws Exception {
											if (Messagebox.Button.YES
													.equals(event.getButton())) {
												if (!f2) {
													boolean flag = cfpb
															.addPwdc(
																	cfpm,
																	cfpc.getCfpc_id());
													if (flag) {
														f = true;
														docOC.UpsubmitDoc(
																gd,
																String.valueOf(cfpc_id));
														Messagebox
																.show("资料提交成功!",
																		"操作提示",
																		Messagebox.OK,
																		Messagebox.INFORMATION);
														addchpc = cfpb
																.getPwdChangeByid(cfpc
																		.getCfpc_id());
													} else {
														Messagebox
																.show("添加失败!");
													}
												} else {
													Messagebox
															.show("已完成申报,数据不能再修改");
												}
											}
										}
									};
									Messagebox.show("确认提交资料？", null,
											new Messagebox.Button[] {
													Messagebox.Button.YES,
													Messagebox.Button.NO },
											Messagebox.QUESTION, clickListener);
								}
							} else {
								Messagebox.show("该专办员存在使用中的密钥");
							}
						} else {
							Messagebox.show("开始时间请在结束时间之后");
						}
					} else {
						Messagebox.show("时间不能为空");
					}
				} else {
					Messagebox.show("请选择要申请数字证书的专办员");
				}
			} else if (updatec.isChecked()) {
				if (chfz_id != 0 || updateflag) {
					// 密钥变更 如果已经有密钥的专办员将不能选取
					final CoHousingFundPwdModel cfpm = new CoHousingFundPwdModel();
					CoHousingFundPwdModel p = cfpb.getPwdByid(cfpc
							.getCfpc_chfp_id());
					cfpm.setChfp_yearlimit(p.getChfp_yearlimit());
					cfpm.setChfp_startdate(p.getChfp_startdate());
					cfpm.setChfp_enddate(p.getChfp_enddate());
					cfpm.setChfp_chfz_id(chfz_id);
					cfpm.setChfp_id(cfpc.getCfpc_chfp_id());
					cfpm.setChfp_addname(Executions.getCurrent().getDesktop()
							.getSession().getAttribute("username").toString());

					cfpm.setFlag(1);
					for (int i = 0; i < notPwdZb.size(); i++) {
						if (notPwdZb.get(i).getChfz_id() == chfz_id) {
							cfpm.setChfp_zb_name(notPwdZb.get(i).getChfz_name());
							cfpm.setChfp_zb_number(notPwdZb.get(i)
									.getChfz_number());

						}
					}
					String[] message = docOC.UpchkHaveTo(gd2);

					if (message[0] == "1") {
						EventListener<ClickEvent> clickListener = new EventListener<Messagebox.ClickEvent>() {
							public void onEvent(ClickEvent event)
									throws Exception {
								if (Messagebox.Button.YES.equals(event
										.getButton())) {
									if (!f2) {
										boolean flag = cfpb.changePwdc(cfpm,
												cfpc.getCfpc_id(),2);
										if (flag) {
											f = true;
											docOC.UpsubmitDoc(gd2,
													String.valueOf(cfpc_id));
											Messagebox.show("资料提交成功!", "操作提示",
													Messagebox.OK,
													Messagebox.INFORMATION);
											addchpc = cfpb
													.getPwdChangeByid(cfpc
															.getCfpc_id());
										}
									} else {
										Messagebox.show("已完成申报,数据不能再修改");
									}
								}
							}
						};
						Messagebox.show("确认提交资料？", null,
								new Messagebox.Button[] {
										Messagebox.Button.YES,
										Messagebox.Button.NO },
								Messagebox.QUESTION, clickListener);
					}
				} else {
					Messagebox.show("请选择要变更的专办员");
				}
			} else if (renewalc.isChecked()) {
				if (chfp_id != 0 || reneflag) {
					if (reneStartDate != null && reneEndDate != null) {
						if (reneStartDate.before(reneEndDate)) {
							// 查询密钥专办员表 ，选择给任意密钥续期
							final CoHousingFundPwdModel cfpm = new CoHousingFundPwdModel();
							cfpm.setChfp_yearlimit(Integer
									.valueOf(renewalLimit));
							cfpm.setOwnmonth(currentDate);
							cfpm.setChfp_id(chfp_id);
							cfpm.setChfp_startdate(reneStartDate);
							cfpm.setChfp_enddate(reneEndDate);
							cfpm.setChfp_addname(Executions.getCurrent()
									.getDesktop().getSession()
									.getAttribute("username").toString());
							cfpm.setFlag(1);

							for (int i = 0; i < pwdList.size(); i++) {
								if (pwdList.get(i).getChfp_id() == chfp_id) {
									cfpm.setChfp_zb_name(pwdList.get(i)
											.getChfp_zb_name());
									cfpm.setChfp_zb_number(pwdList.get(i)
											.getChfp_zb_number());
								}
							}
							String[] message = docOC.UpchkHaveTo(gd1);
							if (message[0] == "1") {
								EventListener<ClickEvent> clickListener = new EventListener<Messagebox.ClickEvent>() {
									public void onEvent(ClickEvent event)
											throws Exception {
										if (Messagebox.Button.YES.equals(event
												.getButton())) {
											if (!f2) {
												boolean flag = cfpb
														.renewalPwdc(cfpm, cfpc
																.getCfpc_id());
												if (flag) {
													f = true;
													docOC.UpsubmitDoc(
															gd1,
															String.valueOf(cfpc_id));
													Messagebox
															.show("资料提交成功!",
																	"操作提示",
																	Messagebox.OK,
																	Messagebox.INFORMATION);
													addchpc = cfpb
															.getPwdChangeByid(cfpc_id);
												}
											} else {
												Messagebox
														.show("已完成申报,数据不能再修改");
											}
										}
									}
								};
								Messagebox.show("确认提交资料？", null,
										new Messagebox.Button[] {
												Messagebox.Button.YES,
												Messagebox.Button.NO },
										Messagebox.QUESTION, clickListener);
							}
						} else {
							Messagebox.show("开始时间请在结束时间之后");
						}
					} else {
						Messagebox.show("时间不能为空");
					}
				} else {
					Messagebox.show("请选择要续期的密钥专办员");
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public boolean isAddflag() {
		return addflag;
	}

	public void setAddflag(boolean addflag) {
		this.addflag = addflag;
	}

	public boolean isUpdateflag() {
		return updateflag;
	}

	public void setUpdateflag(boolean updateflag) {
		this.updateflag = updateflag;
	}

	public boolean isReneflag() {
		return reneflag;
	}

	public void setReneflag(boolean reneflag) {
		this.reneflag = reneflag;
	}

	public int getAddZbid() {
		return addZbid;
	}

	public void setAddZbid(int addZbid) {
		this.addZbid = addZbid;
	}

	public int getChfz_id() {
		return chfz_id;
	}

	public void setChfz_id(int chfz_id) {
		this.chfz_id = chfz_id;
	}

	public int getCfpc_id() {
		return cfpc_id;
	}

	public void setCfpc_id(int cfpc_id) {
		this.cfpc_id = cfpc_id;
	}

	public int getChfp_id() {
		return chfp_id;
	}

	public void setChfp_id(int chfp_id) {
		this.chfp_id = chfp_id;
	}

	public CoHousingFundPwdChangeModel getGetDate() {
		return getDate;
	}

	public void setGetDate(CoHousingFundPwdChangeModel getDate) {
		this.getDate = getDate;
	}

	public CoHousingFundPwdChangeModel getCfpc() {
		return cfpc;
	}

	public void setCfpc(CoHousingFundPwdChangeModel cfpc) {
		this.cfpc = cfpc;
	}

	public String getNameAndNumber() {
		return nameAndNumber;
	}

	public void setNameAndNumber(String nameAndNumber) {
		this.nameAndNumber = nameAndNumber;
	}

	public String getCid() {
		return cid;
	}

	public void setCid(String cid) {
		this.cid = cid;
	}

	public List<CoHousingFundZbModel> getNotPwdZb() {
		return notPwdZb;
	}

	public void setNotPwdZb(List<CoHousingFundZbModel> notPwdZb) {
		this.notPwdZb = notPwdZb;
	}

	public List<CoHousingFundPwdModel> getPwdList() {
		return pwdList;
	}

	public void setPwdList(List<CoHousingFundPwdModel> pwdList) {
		this.pwdList = pwdList;
	}

	public String getRemark() {
		return Remark;
	}

	public void setRemark(String remark) {
		Remark = remark;
	}

	public String getAddYearLimit() {
		return addYearLimit;
	}

	public void setAddYearLimit(String addYearLimit) {
		this.addYearLimit = addYearLimit;
	}

	public String getCurrentDate() {
		return currentDate;
	}

	public void setCurrentDate(String currentDate) {
		this.currentDate = currentDate;
	}

	public String getPwdName() {
		return pwdName;
	}

	public void setPwdName(String pwdName) {
		this.pwdName = pwdName;
	}

	public String getPwdNumber() {
		return pwdNumber;
	}

	public void setPwdNumber(String pwdNumber) {
		this.pwdNumber = pwdNumber;
	}

	public Date getAddStartDate() {
		return addStartDate;
	}

	public void setAddStartDate(Date addStartDate) {
		this.addStartDate = addStartDate;
	}

	public Date getAddEndDate() {
		return addEndDate;
	}

	public void setAddEndDate(Date addEndDate) {
		this.addEndDate = addEndDate;
	}

	public Date getReneStartDate() {
		return reneStartDate;
	}

	public void setReneStartDate(Date reneStartDate) {
		this.reneStartDate = reneStartDate;
	}

	public Date getReneEndDate() {
		return reneEndDate;
	}

	public void setReneEndDate(Date reneEndDate) {
		this.reneEndDate = reneEndDate;
	}

	public String getReason() {
		return reason;
	}

	public void setReason(String reason) {
		this.reason = reason;
	}

	public String getyName() {
		return yName;
	}

	public void setyName(String yName) {
		this.yName = yName;
	}

	public String getyNumber() {
		return yNumber;
	}

	public void setyNumber(String yNumber) {
		this.yNumber = yNumber;
	}

	public String getNewName() {
		return newName;
	}

	public void setNewName(String newName) {
		this.newName = newName;
	}

	public String getNewNumber() {
		return newNumber;
	}

	public void setNewNumber(String newNumber) {
		this.newNumber = newNumber;
	}

	public String getRenewalLimit() {
		return renewalLimit;
	}

	public void setRenewalLimit(String renewalLimit) {
		this.renewalLimit = renewalLimit;
	}

}
