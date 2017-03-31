package Controller.EmHouse;

import impl.MessageImpl;
import impl.WorkflowCore.WfOperateImpl;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.zkoss.bind.annotation.BindingParam;
import org.zkoss.bind.annotation.Command;
import org.zkoss.bind.annotation.NotifyChange;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.event.EventListener;
import org.zkoss.zul.ListModelList;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Messagebox.ClickEvent;
import org.zkoss.zul.Window;

import service.MessageService;
import service.WorkflowCore.WfBusinessService;
import service.WorkflowCore.WfOperateService;
import Model.EmHouseBJModel;
import Model.EmHouseChangeGJJModel;
import Model.EmHouseChangeModel;
import Model.EmHouseUpdateModel;
import Model.EmbaseModel;
import Model.LoginModel;
import Model.SysMessageModel;
import Model.TaskProcessModel;
import Model.loginroleModel;
import Util.DateStringChange;
import Util.RedirectUtil;
import Util.StringFormat;
import Util.UserInfo;
import bll.EmHouse.EmHouseChangeGjjImpl;
import bll.EmHouse.EmHouseSetBll;
import bll.EmHouse.EmHouse_EditBJImpl;
import bll.EmHouse.EmHouse_EditBll;
import bll.EmHouse.EmHouse_EditImpl;
import bll.Embase.EmBase_gdBll;
import bll.Embase.EmbaseLogin_AddBll;
import dal.Embase.EmbaseGdDal;
import dal.Taskflow.TaskListDal;
import dal.Taskflow.Task_controlDal;

public class Emhouse_EditListController {
	private Integer embaId = 0;
	private EmHouse_EditBll bll = new EmHouse_EditBll();
	private EmHouseSetBll sbll = new EmHouseSetBll();
	private EmbaseLogin_AddBll abll = new EmbaseLogin_AddBll();

	private List<EmHouseChangeModel> list = new ListModelList<>();
	private List<EmHouseBJModel> bjlist = new ListModelList<>();
	private List<EmHouseChangeGJJModel> gjjlist = new ListModelList<>();
	private List<EmbaseModel> emList = new ListModelList<>();

	private SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
	private EmbaseModel ebm = new EmbaseModel();

	private Integer gjjfwownmonth; // 公积金服务起始月
	private Integer gjjtzownmont; // 公积金台账月
	private boolean house_ifStop;
	private Integer ownmonth;
	private boolean deadline = false;
	private boolean ifstop = false;
	private boolean ifbjstop = false;
	private boolean ifpower = false;
	private boolean ifbjpower = false;

	private Window winC;

	public Emhouse_EditListController() {
		Integer daid = 0;
		Integer gid = 0;
		EmHouseChangeModel em = new EmHouseChangeModel();
		if (Executions.getCurrent().getArg().get("daid") != null) {
			daid = Integer.valueOf(Executions.getCurrent().getArg().get("daid")
					.toString());
			em.setEmhc_id(daid);
			gid = bll.getChangeList(em).get(0).getGid();

		}
		if (Executions.getCurrent().getArg().get("gid") != null) {
			gid = Integer.valueOf(Executions.getCurrent().getArg().get("gid")
					.toString());
		}

		embaId = bll.getEmbaseByGid(gid).get(0).getEmba_id();

		emList = bll.getEmbase(embaId);
		if (emList.size() > 0) {
			ebm = emList.get(0);
			setList();
			setBjlist();
			setGjjlist();
			GjjOwnmonth();

		}
		ifpower = bll.ifpower();
		ifbjpower = bll.ifpower();
	}

	@Command("winC")
	public void winC(@BindingParam("a") Window winD) {
		winC = winD;

	}

	@Command
	public void link() {
		RedirectUtil u = new RedirectUtil();
		u.indexAddTab("/Embase/Embase_yfzxinfo.zul?gid=" + ebm.getGid() + "",
				"雇员服务中心");
	}

	// 计算公积金所属月份
	public void GjjOwnmonth() {

		gjjtzownmont = abll.houseOwnmonth();

		house_ifStop = sbll.gjjOnair(ebm.getCid(), ebm.getGid(), gjjtzownmont,
				null); // 公积金接单日

		if (house_ifStop) {
			// 截单社保所属月份+1
			ownmonth = Integer.valueOf(DateStringChange
					.ownmonthAddoneMonth(gjjtzownmont.toString()));
		} else {
			ownmonth = gjjtzownmont;

		}

	}

	@Command
	public void message(@BindingParam("a") Integer t,
			@BindingParam("b") Object o) {
		Map map = new HashMap<>();
		map.put("type", "住房公积金");// 业务类型:来自WfClass的wfcl_name
		List<LoginModel> mlist = new ArrayList<LoginModel>();
		switch (t) {
		case 1:// 变更
			EmHouseChangeModel m = (EmHouseChangeModel) o;
			List<EmbaseModel> l = new ListModelList<>();
			l = bll.getEmbaseByGid(m.getGid());
			map.put("id", m.getEmhc_id());// 业务表id
			map.put("tablename", "emhousechange");// 业务表名
			LoginModel lm = new LoginModel();
			lm.setLog_name(l.get(0).getCoba_client());
			mlist.add(lm);
			break;
		case 2:// 补缴
			EmHouseBJModel m2 = (EmHouseBJModel) o;
			List<EmbaseModel> l2 = new ListModelList<>();
			l2 = bll.getEmbaseByGid(m2.getGid());
			map.put("id", m2.getEmhb_id());// 业务表id
			map.put("tablename", "emhousebj");// 业务表名
			LoginModel lm2 = new LoginModel();
			lm2.setLog_name(l2.get(0).getCoba_client());
			mlist.add(lm2);
			break;
		case 3:// 交单
			EmHouseChangeGJJModel m3 = (EmHouseChangeGJJModel) o;
			List<EmbaseModel> l3 = new ListModelList<>();
			l3 = bll.getEmbaseByGid(m3.getGid());
			map.put("id", m3.getEhcg_id());// 业务表id
			map.put("tablename", "emhousechangegjj");// 业务表名
			LoginModel lm3 = new LoginModel();
			lm3.setLog_name(l3.get(0).getCoba_client());
			mlist.add(lm3);
			break;
		default:
			break;
		}

		map.put("list", mlist);// 默认收件人list
		Window window = (Window) Executions.createComponents(
				"../SysMessage/Message_Add.zul", null, map);
		window.doModal();
	}

	@Command
	@NotifyChange("list")
	public void mod(@BindingParam("a") EmHouseChangeModel em,
			@BindingParam("b") String control) {
		boolean DS = false;
		
		if (em.getOwnmonth() <= Integer.valueOf(DateStringChange.getOwnmonth())) {
			deadline = bll.getlastDay(em.getGid(), em.getEmhc_cpp());
		}
		ifstop = bll.ifstop(em.getGid(), em.getOwnmonth(), em.getEmhc_cpp());
		if (UserInfo.getDepID().equals("16")) {
			if(!bll.ifstopfw(em.getGid(), em.getOwnmonth(), em.getEmhc_cpp())){
				DS=true;
			}
		}
		final EmHouseChangeModel ecm = new EmHouseChangeModel();
		ecm.setEmhc_id(em.getEmhc_id());
		ecm.setEmhc_company(em.getEmhc_company());
		ecm.setEmhc_companyid(em.getEmhc_companyid());
		ecm.setEmhc_computerid(em.getEmhc_computerid());
		ecm.setCid(em.getCid());
		ecm.setGid(em.getGid());
		ecm.setEmhc_name(em.getEmhc_name());
		ecm.setOwnmonth(em.getOwnmonth());
		ecm.setEmhc_tapr_id(em.getEmhc_tapr_id());

		ecm.setEmhc_change(em.getEmhc_change());
		ecm.setEmhc_tid(em.getEmhc_tid());
		ecm.setEmhc_mobile(em.getEmhc_mobile());
		ecm.setAddname(em.getEmhc_addname());

		List<EmHouseUpdateModel> l = bll.gethouseList(ecm.getGid(), null);
		switch (control) {
		case "mod":
			Map map = new HashMap();
			map.put("id", ecm.getEmhc_id());

			Window window = (Window) Executions.createComponents(
					"/EmHouse/Emhouse_Mod.zul", null, map);
			window.doModal();

			break;
		case "state":

			if ((ecm.getEmhc_companyid() == null || ecm.getEmhc_companyid()
					.equals("")) && ecm.getEmhc_ifdeclare().equals(0)) {
				Messagebox.show("该员工没有单位公积金编号.", "操作提示", Messagebox.OK,
						Messagebox.ERROR);
				return;
			}

			// 未申报变待确认
			if (em.getEmhc_ifdeclare().equals(0)) {
				Messagebox.show("确认变更数据?", "操作提示", new Messagebox.Button[] {
						Messagebox.Button.OK, Messagebox.Button.CANCEL },
						Messagebox.QUESTION,
						new EventListener<Messagebox.ClickEvent>() {
							@Override
							public void onEvent(ClickEvent event)
									throws Exception {
								// TODO Auto-generated method stub

								if (Messagebox.Button.OK.equals(event
										.getButton())) {
									ecm.setEmhc_ifdeclare(4);

									if (ecm.getEmhc_tapr_id() == null) {
										Integer i = 0;
										i = bll.modCommitState(ecm);
										if (i > 0) {

											bll.updateData(ecm.getGid());
											Messagebox.show("操作成功.", "操作提示",
													Messagebox.OK,
													Messagebox.INFORMATION);
										} else {
											Messagebox.show("操作失败.", "操作提示",
													Messagebox.OK,
													Messagebox.ERROR);
										}
									} else {
										String[] str = new String[5];
										WfBusinessService wfbs = new EmHouse_EditImpl();
										WfOperateService wfs = new WfOperateImpl(
												wfbs);
										Object[] obj = { "返回待确认", ecm };
										// 有任务单数据变更状态
										str = wfs.ReturnToN(obj,
												ecm.getEmhc_tapr_id(), 1, "系统");
										if (str[0].equals("1")) {

											bll.updateData(ecm.getGid());
											Messagebox.show("操作成功.", "操作提示",
													Messagebox.OK,
													Messagebox.INFORMATION);
										} else {
											Messagebox.show("操作失败.", "操作提示",
													Messagebox.OK,
													Messagebox.ERROR);
										}
									}

								}
							}
						});
			} else {
				if (ifpower) {
					Messagebox.show("福利已关闭系统", "操作提示", Messagebox.OK,
							Messagebox.ERROR);
				} else {
					if (ifstop && !DS) {
						Messagebox.show("当前月份已截单", "操作提示", Messagebox.OK,
								Messagebox.ERROR);
					} else {

						// 待确认变未申报
						Messagebox.show("确认变更数据?", "操作提示",
								new Messagebox.Button[] { Messagebox.Button.OK,
										Messagebox.Button.CANCEL },
								Messagebox.QUESTION,
								new EventListener<Messagebox.ClickEvent>() {
									@Override
									public void onEvent(ClickEvent event)
											throws Exception {
										// TODO Auto-generated method stub

										if (Messagebox.Button.OK.equals(event
												.getButton())) {

											ecm.setEmhc_ifdeclare(0);
											if (!ecm.getEmhc_change().equals(
													"停交")
													|| ecm.getEmhc_tid() > 0) {
												ecm.setEmhc_addname(UserInfo
														.getUsername());
											}
											String[] str = new String[5];
											WfBusinessService wfbs = new EmHouse_EditImpl();
											WfOperateService wfs = new WfOperateImpl(
													wfbs);
											Integer i = 0;

											if (ecm.getEmhc_tapr_id() == null) {
												if (ecm.getEmhc_change()
														.equals("新增")) {
													// 业务中心加数据产生任务单
													Object[] obj = { "新增任务单",
															ecm };

													str = wfs
															.AddTaskToNext(
																	obj,
																	"员工公积金变更",
																	ownmonth
																			+ ecm.getEmhc_name()
																			+ "("
																			+ ecm.getGid()
																			+ ")新增公积金",
																	34,
																	UserInfo.getUsername(),
																	ecm.getAddname()
																			+ "添加数据",
																	ecm.getCid(),
																	"");
													if (str[0].equals("1")) {
														if (deadline) {
															email(ecm.getCid(),
																	ecm.getGid(),
																	ecm.getEmhc_company(),
																	ecm.getEmhc_name());
														}
														EmBase_gdBll gdbll = new EmBase_gdBll();
														gdbll.modinfo(6, ecm
																.getEmhc_id(),
																"");
														bll.updateData(ecm
																.getGid());
														Messagebox
																.show("操作成功.",
																		"操作提示",
																		Messagebox.OK,
																		Messagebox.INFORMATION);
													} else {
														Messagebox
																.show("操作失败.",
																		"操作提示",
																		Messagebox.OK,
																		Messagebox.ERROR);
													}
												} else if (ecm.getEmhc_change()
														.equals("调入")) {
													// 业务中心加数据产生任务单
													Object[] obj = { "调入任务单",
															ecm };
													str = wfs
															.AddTaskToNext(
																	obj,
																	"员工公积金变更",
																	ownmonth
																			+ ecm.getEmhc_name()
																			+ "("
																			+ ecm.getGid()
																			+ ")调入公积金",
																	35,
																	UserInfo.getUsername(),
																	ecm.getAddname()
																			+ "添加数据",
																	ecm.getCid(),
																	"");
													if (str[0].equals("1")) {
														if (deadline) {
															email(ecm.getCid(),
																	ecm.getGid(),
																	ecm.getEmhc_company(),
																	ecm.getEmhc_name());
														}
														EmBase_gdBll gdbll = new EmBase_gdBll();

														gdbll.modinfo(6, ecm
																.getEmhc_id(),
																"");
														bll.updateData(ecm
																.getGid());
														Messagebox
																.show("操作成功.",
																		"操作提示",
																		Messagebox.OK,
																		Messagebox.INFORMATION);
													} else {
														Messagebox
																.show("操作失败.",
																		"操作提示",
																		Messagebox.OK,
																		Messagebox.ERROR);
													}

												} else {
													// 旧系统数据状态变更
													i = bll.modCommitState(ecm);
													if (i > 0) {
														if (deadline) {
															email(ecm.getCid(),
																	ecm.getGid(),
																	ecm.getEmhc_company(),
																	ecm.getEmhc_name());
														}
														EmBase_gdBll gdbll = new EmBase_gdBll();

														gdbll.modinfo(6, ecm
																.getEmhc_id(),
																"");
														bll.updateData(ecm
																.getGid());
														Messagebox
																.show("操作成功.",
																		"操作提示",
																		Messagebox.OK,
																		Messagebox.INFORMATION);
													} else {
														Messagebox
																.show("操作失败.",
																		"操作提示",
																		Messagebox.OK,
																		Messagebox.ERROR);
													}
												}

											} else {
												Object[] obj = { "确认数据", ecm };
												// 有任务单申报数据

												str = wfs.SkipToN(obj,
														ecm.getEmhc_tapr_id(),
														4,
														UserInfo.getUsername(),
														"", ecm.getCid(), "");
												if (str[0].equals("1")) {
													if (deadline) {
														email(ecm.getCid(),
																ecm.getGid(),
																ecm.getEmhc_company(),
																ecm.getEmhc_name());
													}
													EmBase_gdBll gdbll = new EmBase_gdBll();

													gdbll.modinfo(6,
															ecm.getEmhc_id(),
															"");

													bll.updateData(ecm.getGid());
													Messagebox
															.show("操作成功.",
																	"操作提示",
																	Messagebox.OK,
																	Messagebox.INFORMATION);
												} else {
													Messagebox.show("操作失败.",
															"操作提示",
															Messagebox.OK,
															Messagebox.ERROR);
												}
											}
										}
									}
								});
					}
				}
			}

			break;
		case "send":

			if (ifpower) {
				Messagebox.show("福利已关闭系统", "操作提示", Messagebox.OK,
						Messagebox.ERROR);
			} else {
				if (ifstop && !DS) {
					Messagebox.show("当前月份已截单", "操作提示", Messagebox.OK,
							Messagebox.ERROR);
				} else {
					// 重新发送
					if (em.getEmhc_change() == "新增") {
						if (em.getEmhc_title() == null
								|| em.getEmhc_title().equals("")) {
							Messagebox.show(em.getEmhc_name() + "职称为空!",
									"操作提示", Messagebox.OK, Messagebox.ERROR);
							return;
						}
						if (em.getEmhc_hj() == null
								|| em.getEmhc_hj().equals("")) {
							Messagebox.show(em.getEmhc_name() + "户籍为空!",
									"操作提示", Messagebox.OK, Messagebox.ERROR);
							return;
						}
						if (em.getEmhc_degree() == null
								|| em.getEmhc_degree().equals("")) {
							Messagebox.show(em.getEmhc_name() + "学历为空!",
									"操作提示", Messagebox.OK, Messagebox.ERROR);
							return;
						}
					}
					EmHouseChangeModel m = new EmHouseChangeModel();
					m.setGid(ecm.getGid());
					m.setEmhc_idcard(ecm.getEmhc_idcard());
					m.setEmhc_houseid(ecm.getEmhc_houseid());
					m.setEmhc_change(ecm.getEmhc_change());
					m.setEmhc_tapr_id(ecm.getEmhc_tapr_id());
					m.setEmhc_mobile(ecm.getEmhc_mobile());
					m.setOwnmonth(ecm.getOwnmonth());

					Integer month = sbll.nowmonth2(m.getGid());

					if (month > 0) {

						if (!month.equals(ecm.getOwnmonth())) {
							Messagebox.show("请注意,当前公积金月份与数据所属月不一致", "操作提示",
									Messagebox.OK, Messagebox.ERROR);
						}

					} else {
						Messagebox.show("员工所属合同公积金信息不完整", "操作提示",
								Messagebox.OK, Messagebox.ERROR);
						return;
					}

					if (bll.houseChangeSame(m)) {
						Messagebox.show("当月有同类型未处理变更数据", "操作提示", Messagebox.OK,
								Messagebox.ERROR);
						return;
					}

					if ((ecm.getEmhc_companyid() == null || ecm
							.getEmhc_companyid().equals(""))
							&& ecm.getEmhc_ifdeclare().equals(0)) {
						Messagebox.show("该员工没有单位公积金编号.", "操作提示", Messagebox.OK,
								Messagebox.ERROR);
						return;
					}
					Messagebox.show("确认提交数据?", "操作提示", new Messagebox.Button[] {
							Messagebox.Button.OK, Messagebox.Button.CANCEL },
							Messagebox.QUESTION,
							new EventListener<Messagebox.ClickEvent>() {
								@Override
								public void onEvent(ClickEvent event)
										throws Exception {
									// TODO Auto-generated method stub

									if (Messagebox.Button.OK.equals(event
											.getButton())) {
										ecm.setEmhc_ifdeclare(4);
										ecm.setEmhc_confirmtime(sdf
												.format(new Date()));

										Integer i = bll.modChange(ecm);
										if (i > 0) {
											/*
											 * bll.ControlData(ecm.getEmhc_id(),
											 * ecm.getOwnmonth());
											 */
											if (deadline) {
												email(ecm.getCid(),
														ecm.getGid(),
														ecm.getEmhc_company(),
														ecm.getEmhc_name());
											}
											bll.updateData(ecm.getGid());
											Messagebox.show("操作成功.", "操作提示",
													Messagebox.OK,
													Messagebox.INFORMATION);
										} else {
											Messagebox.show("操作失败.", "操作提示",
													Messagebox.OK,
													Messagebox.ERROR);
										}
									}
								}
							});
				}
			}
			break;
		case "movein":

			if (ifpower) {
				Messagebox.show("福利已关闭系统", "操作提示", Messagebox.OK,
						Messagebox.ERROR);
			} else {
				if (ifstop && !DS) {
					Messagebox.show("当前月份已截单", "操作提示", Messagebox.OK,
							Messagebox.ERROR);
				} else {
					if (ecm.getEmhc_companyid() == null
							|| ecm.getEmhc_companyid().equals("")) {
						Messagebox.show("该员工没有单位公积金编号.", "操作提示", Messagebox.OK,
								Messagebox.ERROR);
						return;
					}

					/*
					 * if (l.size() == 0) {
					 * Messagebox.show("该员工没有在册数据,请先操作变更状态刷新在册信息", "操作提示",
					 * Messagebox.OK, Messagebox.ERROR); return; }
					 */
					Messagebox.show("确认提交数据?", "操作提示", new Messagebox.Button[] {
							Messagebox.Button.OK, Messagebox.Button.CANCEL },
							Messagebox.QUESTION,
							new EventListener<Messagebox.ClickEvent>() {
								@Override
								public void onEvent(ClickEvent event)
										throws Exception {
									// TODO Auto-generated method stub

									if (Messagebox.Button.OK.equals(event
											.getButton())) {
										Map map = new HashMap();
										map.put("id", ecm.getEmhc_id());
										map.put("gid", ecm.getGid());
										Window window = (Window) Executions
												.createComponents(
														"/EmHouse/Emhouse_MoveToNew.zul",
														null, map);
										window.doModal();
									}
								}
							});
				}
			}
			break;
		case "addnew":
			if (ifpower) {
				Messagebox.show("福利已关闭系统", "操作提示", Messagebox.OK,
						Messagebox.ERROR);
			} else {
				if (ifstop && !DS) {
					Messagebox.show("当前月份已截单", "操作提示", Messagebox.OK,
							Messagebox.ERROR);
				} else {
					if (ecm.getEmhc_companyid() == null
							|| ecm.getEmhc_companyid().equals("")) {
						Messagebox.show("该员工没有单位公积金编号.", "操作提示", Messagebox.OK,
								Messagebox.ERROR);
						return;
					}
					if (em.getEmhc_title() == null
							|| em.getEmhc_title().equals("")) {
						Messagebox.show(em.getEmhc_name() + "职称为空!", "操作提示",
								Messagebox.OK, Messagebox.ERROR);
						return;
					}
					if (em.getEmhc_hj() == null || em.getEmhc_hj().equals("")) {
						Messagebox.show(em.getEmhc_name() + "户籍为空!", "操作提示",
								Messagebox.OK, Messagebox.ERROR);
						return;
					}
					if (em.getEmhc_degree() == null
							|| em.getEmhc_degree().equals("")) {
						Messagebox.show(em.getEmhc_name() + "学历为空!", "操作提示",
								Messagebox.OK, Messagebox.ERROR);
						return;
					}
					if (ecm.getEmhc_mobile() == null
							|| ecm.getEmhc_mobile().equals("")) {
						Messagebox.show("手机号必须为11位!", "操作提示", Messagebox.OK,
								Messagebox.ERROR);
						return;
					} else {
						ecm.setEmhc_mobile(StringFormat.replaceSpace(ecm
								.getEmhc_mobile()));
						if (ecm.getEmhc_mobile().length() != 11) {
							Messagebox.show("手机号必须为11位!", "操作提示",
									Messagebox.OK, Messagebox.ERROR);
							return;
						} else {

						}
					}
					/*
					 * if (l.size() == 0) {
					 * Messagebox.show("该员工没有在册数据,请先操作变更状态刷新在册信息", "操作提示",
					 * Messagebox.OK, Messagebox.ERROR); return; }
					 */
					Messagebox.show("确认提交数据?", "操作提示", new Messagebox.Button[] {
							Messagebox.Button.OK, Messagebox.Button.CANCEL },
							Messagebox.QUESTION,
							new EventListener<Messagebox.ClickEvent>() {
								@Override
								public void onEvent(ClickEvent event)
										throws Exception {
									// TODO Auto-generated method stub

									if (Messagebox.Button.OK.equals(event
											.getButton())) {
										if (!ecm.getEmhc_change().equals("停交")
												|| ecm.getEmhc_tid() > 0) {
											ecm.setEmhc_addname(UserInfo
													.getUsername());
										}
										WfBusinessService wfbs = new EmHouse_EditImpl();
										WfOperateService wfs = new WfOperateImpl(
												wfbs);
										Object[] obj = { "调入转新增", ecm };
										String[] str = new String[5];
										if (ecm.getEmhc_tapr_id() != null) {
											str = wfs.StopTask(obj,
													ecm.getEmhc_tapr_id(),
													"系统", "");
										} else {
											str[0] = "1";
										}

										if (str[0].equals("1")) {
											str = wfs.AddTaskToNext(
													obj,
													"员工公积金变更",
													ecm.getOwnmonth()
															+ ecm.getEmhc_name()
															+ "("
															+ ecm.getGid()
															+ ")新增公积金", 34,
													UserInfo.getUsername(),
													ecm.getAddname() + "添加数据",
													0, "");
											ecm.setEmhc_id(Integer
													.valueOf(str[3].toString()));
										}
										// Integer i = bll.changeNew(ecm);
										if (str[0].equals("1")) {
											/*
											 * bll.ControlData(ecm.getEmhc_id(),
											 * ecm.getOwnmonth());
											 */
											if (deadline) {
												email(ecm.getCid(),
														ecm.getGid(),
														ecm.getEmhc_company(),
														ecm.getEmhc_name());
											}
											bll.updateData(ecm.getGid());
											Messagebox.show("操作成功.", "操作提示",
													Messagebox.OK,
													Messagebox.INFORMATION);
										} else {
											Messagebox.show("操作失败.", "操作提示",
													Messagebox.OK,
													Messagebox.ERROR);
										}

									}
								}
							});
				}
			}
			break;

		case "radix":
			if (ifpower) {
				Messagebox.show("福利已关闭系统", "操作提示", Messagebox.OK,
						Messagebox.ERROR);
			} else {
				if (ifstop && !DS) {
					Messagebox.show("当前月份已截单", "操作提示", Messagebox.OK,
							Messagebox.ERROR);
				} else {
					Map map2 = new HashMap();
					map2.put("id", ecm.getEmhc_id());
					map2.put("taprid", ecm.getEmhc_tapr_id());
					map2.put("gid", ecm.getGid());
					map2.put("radix", em.getEmhc_radix());
					Window window2 = (Window) Executions.createComponents(
							"/EmHouse/Emhouse_ChangeInfo.zul", null, map2);
					window2.doModal();
				}
			}
			break;
		default:
			break;
		}

		setList();
	}

	@Command
	@NotifyChange("list")
	public void errorInfo(@BindingParam("a") EmHouseChangeModel em) {

	}

	@Command
	@NotifyChange("bjlist")
	public void bjmod(@BindingParam("a") EmHouseBJModel em,
			@BindingParam("b") String control) {
		boolean DS = false;
		
		final EmHouseBJModel ejm = new EmHouseBJModel();
		ejm.setEmhb_id(em.getEmhb_id());
		ejm.setCid(em.getCid());
		ejm.setGid(em.getGid());
		ejm.setEmhb_name(em.getEmhb_name());
		ejm.setOwnmonth(em.getOwnmonth());
		ejm.setEmhb_tapr_id(em.getEmhb_tapr_id());
		ejm.setEmhb_companyid(em.getEmhb_companyid());
		ejm.setAddname(em.getEmhb_addname());

		ifbjstop = bll.ifstop(em.getGid(), em.getOwnmonth(), em.getEmhb_cpp());
		if (UserInfo.getDepID().equals("16")) {
			if(!bll.ifstopfw(em.getGid(), em.getOwnmonth(), em.getEmhb_cpp())){
				DS=true;
			}
		}
		
		switch (control) {
		case "mod":
			Map map = new HashMap();
			map.put("id", ejm.getEmhb_id());
			map.put("gid", ejm.getGid());
			map.put("daid", ejm.getEmhb_id());
			Window window = (Window) Executions.createComponents(
					"/EmHouse/EmHouse_BjMod.zul", null, map);
			window.doModal();

			break;
		case "send":
			if (ifbjpower) {
				Messagebox.show("福利已关闭系统", "操作提示", Messagebox.OK,
						Messagebox.ERROR);
			} else {
				if (ifbjstop && !DS) {
					Messagebox.show("当前月份已截单", "操作提示", Messagebox.OK,
							Messagebox.ERROR);
				} else {

					if (ejm.getEmhb_companyid() == null
							|| ejm.getEmhb_companyid().equals("")) {
						Messagebox.show("该员工没有单位公积金编号.", "操作提示", Messagebox.OK,
								Messagebox.ERROR);
						return;
					}
					Messagebox.show("确认提交数据?", "操作提示", new Messagebox.Button[] {
							Messagebox.Button.OK, Messagebox.Button.CANCEL },
							Messagebox.QUESTION,
							new EventListener<Messagebox.ClickEvent>() {
								@Override
								public void onEvent(ClickEvent event)
										throws Exception {
									// TODO Auto-generated method stub

									if (Messagebox.Button.OK.equals(event
											.getButton())) {

										Integer i = 0;
										List<TaskProcessModel> list = new ListModelList<>();

										if (ejm.getEmhb_tapr_id() != null) {
											TaskListDal dal = new TaskListDal();
											list = dal.getlist(ejm
													.getEmhb_tapr_id());
										}
										String[] str = new String[5];
										// 有任务流程
										if (list.size() > 0) {
											// 当前步骤与操作人是否相同
											if (list.get(0)
													.getTapr_starname()
													.equals(list
															.get(0)
															.getTapr_appointcon())) {
												// 相同时更新状态
												WfBusinessService wfbs = new EmHouse_EditBJImpl();
												WfOperateService wfs = new WfOperateImpl(
														wfbs);
												ejm.setEmhb_ifdeclare(0);
												Object[] obj = { "补缴重新发送", ejm };

												str = wfs.SkipToN(obj,
														ejm.getEmhb_tapr_id(),
														3,
														UserInfo.getUsername(),
														"", ejm.getCid(), "");

												if (str[0].equals("1")) {
													Task_controlDal dal = new Task_controlDal();
													i = dal.updateAppointid(Integer.valueOf(str[2]
															.toString()));

												}
											} else {
												// 不相同只修改信息
												ejm.setEmhb_ifdeclare(4);
												Task_controlDal dal = new Task_controlDal();
												i = dal.updateAppointid(ejm
														.getEmhb_tapr_id());
												if (i > 0) {
													EmBase_gdBll gdbll = new EmBase_gdBll();
													gdbll.modinfo(7,
															ejm.getEmhb_id(),
															"");
													str[0] = "1";
												}
												bll.modBJ(ejm);
											}
										} else {
											ejm.setEmhb_ifdeclare(4);
											Integer j = bll.modBJ(ejm);
											str[0] = j > 0 ? "1" : "0";
										}
										if (str[0].equals("1")) {
											EmbaseGdDal dal = new EmbaseGdDal();
											dal.mod(7, ejm.getEmhb_id(), "");
											Messagebox.show("操作成功.", "操作提示",
													Messagebox.OK,
													Messagebox.INFORMATION);
										} else {
											Messagebox.show("操作失败.", "操作提示",
													Messagebox.OK,
													Messagebox.ERROR);
										}
									}
								}
							});
				}
			}
			break;
		case "state":

			if ((ejm.getEmhb_companyid() == null || ejm.getEmhb_companyid()
					.equals("")) && ejm.getEmhb_ifdeclare().equals(0)) {
				Messagebox.show("该员工没有单位公积金编号.", "操作提示", Messagebox.OK,
						Messagebox.ERROR);
				return;
			}
			// 未申报变待确认
			if (em.getEmhb_ifdeclare().equals(0)) {
				Messagebox.show("确认提交数据?", "操作提示", new Messagebox.Button[] {
						Messagebox.Button.OK, Messagebox.Button.CANCEL },
						Messagebox.QUESTION,
						new EventListener<Messagebox.ClickEvent>() {
							@Override
							public void onEvent(ClickEvent event)
									throws Exception {
								// TODO Auto-generated method stub

								if (Messagebox.Button.OK.equals(event
										.getButton())) {
									ejm.setEmhb_ifdeclare(4);
									WfBusinessService wfbs = new EmHouse_EditBJImpl();
									WfOperateService wfs = new WfOperateImpl(
											wfbs);
									ejm.setEmhb_addname(UserInfo.getUsername());
									String[] str = new String[5];

									if (ejm.getEmhb_tapr_id() != null) {
										Object[] obj = { "补缴返回待确认", ejm };
										str = wfs.ReturnToN(obj,
												ejm.getEmhb_tapr_id(), 1, "系统");
									} else {

										Integer i = bll.modBJ(ejm);
										str[0] = i > 0 ? "1" : "0";
									}
									if (str[0].equals("1")) {
										Messagebox.show("操作成功.", "操作提示",
												Messagebox.OK,
												Messagebox.INFORMATION);
									} else {
										Messagebox
												.show("操作失败.", "操作提示",
														Messagebox.OK,
														Messagebox.ERROR);
									}
								}
							}
						});
			} else {
				// 待确认变未申报

				if (ifbjpower) {
					Messagebox.show("福利已关闭系统", "操作提示", Messagebox.OK,
							Messagebox.ERROR);
				} else {
					if (ifbjstop && !DS) {
						Messagebox.show("当前月份已截单", "操作提示", Messagebox.OK,
								Messagebox.ERROR);
					} else {
						Messagebox.show("确认提交数据?", "操作提示",
								new Messagebox.Button[] { Messagebox.Button.OK,
										Messagebox.Button.CANCEL },
								Messagebox.QUESTION,
								new EventListener<Messagebox.ClickEvent>() {
									@Override
									public void onEvent(ClickEvent event)
											throws Exception {
										// TODO Auto-generated method stub

										if (Messagebox.Button.OK.equals(event
												.getButton())) {
											ejm.setEmhb_ifdeclare(0);
											WfBusinessService wfbs = new EmHouse_EditBJImpl();
											WfOperateService wfs = new WfOperateImpl(
													wfbs);
											ejm.setEmhb_addname(UserInfo
													.getUsername());
											String[] str = new String[5];
											if (ejm.getEmhb_tapr_id() != null) {
												Object[] obj = { "补缴确认数据", ejm };
												str = wfs.SkipToN(obj,
														ejm.getEmhb_tapr_id(),
														3,
														UserInfo.getUsername(),
														"", ejm.getCid(), "");
											} else {
												Object[] obj = { "新增补缴", ejm };
												str = wfs.AddTaskToNext(
														obj,
														"员工公积金补缴",
														ejm.getOwnmonth()
																+ ejm.getEmhb_name()
																+ "("
																+ ejm.getGid()
																+ ")新增公积金补缴",
														102, UserInfo
																.getUsername(),
														ejm.getAddname()
																+ "添加数据", ejm
																.getCid(), "");
											}
											if (str[0].equals("1")) {

												EmBase_gdBll gdbll = new EmBase_gdBll();

												gdbll.modinfo(7,
														ejm.getEmhb_id(), "");

												Messagebox.show("操作成功.",
														"操作提示", Messagebox.OK,
														Messagebox.INFORMATION);
											} else {
												Messagebox.show("操作失败.",
														"操作提示", Messagebox.OK,
														Messagebox.ERROR);
											}
										}
									}
								});
					}
				}
			}

			break;
		case "del":
			Messagebox.show("确认提交数据?", "操作提示", new Messagebox.Button[] {
					Messagebox.Button.OK, Messagebox.Button.CANCEL },
					Messagebox.QUESTION,
					new EventListener<Messagebox.ClickEvent>() {
						@Override
						public void onEvent(ClickEvent event) throws Exception {
							// TODO Auto-generated method stub

							if (Messagebox.Button.OK.equals(event.getButton())) {
								WfBusinessService wfbs = new EmHouse_EditBJImpl();
								WfOperateService wfs = new WfOperateImpl(wfbs);
								Object[] obj = { "补缴删除", ejm };
								String[] str = new String[5];

								if (ejm.getEmhb_tapr_id() != null) {
									str = wfs.StopTask(obj,
											ejm.getEmhb_tapr_id(),
											UserInfo.getUsername(), "");
								} else {
									Integer i = bll.delbj(ejm.getEmhb_id());
									str[0] = i > 0 ? "1" : "0";
								}
								if (str[0].equals("1")) {
									// bll.delbj(ejm.getEmhb_id());
									Messagebox.show("操作成功.", "操作提示",
											Messagebox.OK,
											Messagebox.INFORMATION);
								} else {
									Messagebox.show("操作失败.", "操作提示",
											Messagebox.OK, Messagebox.ERROR);
								}

							}
						}
					});
			break;
		default:
			break;
		}

		setBjlist();

	}

	@Command
	@NotifyChange("list")
	public void del(@BindingParam("a") final EmHouseChangeModel em) {

		Messagebox.show("确认提交数据?", "操作提示", new Messagebox.Button[] {
				Messagebox.Button.OK, Messagebox.Button.CANCEL },
				Messagebox.QUESTION,
				new EventListener<Messagebox.ClickEvent>() {
					@Override
					public void onEvent(ClickEvent event) throws Exception {
						// TODO Auto-generated method stub

						if (Messagebox.Button.OK.equals(event.getButton())) {
							WfBusinessService wfbs = new EmHouse_EditImpl();
							WfOperateService wfs = new WfOperateImpl(wfbs);
							Object[] obj = { "删除数据", em };
							String[] str = new String[5];
							if (em.getEmhc_tapr_id() != null) {
								str = wfs.StopTask(obj, em.getEmhc_tapr_id(),
										UserInfo.getUsername(), "");
							} else {
								str = wfbs.StopOperate(obj);
							}
							bll.updateData(em.getGid());
							if (str[0].equals("1")) {

								Messagebox.show("操作成功.", "操作提示", Messagebox.OK,
										Messagebox.INFORMATION);
							} else {
								Messagebox.show("操作失败.", "操作提示", Messagebox.OK,
										Messagebox.ERROR);
							}
							setList();
						}
					}
				});

	}

	@Command
	@NotifyChange("gjjlist")
	public void gjjMod(@BindingParam("a") EmHouseChangeGJJModel em) {
		Map map = new HashMap<>();
		map.put("em", em);

		Window window = (Window) Executions.createComponents(
				"../EmHouse/Emhouse_ChangeGjjInfo.zul", null, map);
		window.doModal();
		setGjjlist();
	}

	@Command
	@NotifyChange("gjjlist")
	public void gjjSend(@BindingParam("a") final EmHouseChangeGJJModel em) {
		Messagebox.show("确认提交数据?", "操作提示", new Messagebox.Button[] {
				Messagebox.Button.OK, Messagebox.Button.CANCEL },
				Messagebox.QUESTION,
				new EventListener<Messagebox.ClickEvent>() {
					@Override
					public void onEvent(ClickEvent event) throws Exception {
						// TODO Auto-generated method stub

						if (Messagebox.Button.OK.equals(event.getButton())) {

							Integer i = 0;
							List<TaskProcessModel> list = new ListModelList<>();

							if (em.getEhcg_tapr_id() != null) {
								TaskListDal dal = new TaskListDal();
								list = dal.getlist(em.getEhcg_tapr_id());
								// 有任务流程
								if (list.size() > 0) {
									// 当前步骤与操作人是否相同
									if (list.get(0)
											.getTapr_starname()
											.equals(list.get(0)
													.getTapr_appointcon())) {
										// 相同时更新状态
										em.setEhcg_ifdeclare(0);
										WfBusinessService wfbs = new EmHouseChangeGjjImpl();
										WfOperateService wfs = new WfOperateImpl(
												wfbs);
										Object[] obj = { "重新发送", em };
										String[] str = wfs.SkipToN(obj,
												em.getEhcg_tapr_id(), 3,
												UserInfo.getUsername(), "", 0,
												"");
										if (str[0].equals("1")) {
											Task_controlDal dal2 = new Task_controlDal();
											i = dal2.updateAppointid(Integer
													.valueOf(str[2].toString()));

											Messagebox.show("操作成功.", "操作提示",
													Messagebox.OK,
													Messagebox.INFORMATION);
											setGjjlist();
										} else {
											Messagebox.show("操作失败.", "操作提示",
													Messagebox.OK,
													Messagebox.ERROR);
										}
									} else {
										// 不相同只修改信息
										Task_controlDal dal2 = new Task_controlDal();
										i = dal2.updateAppointid(em
												.getEhcg_tapr_id());
										bll.ModGjj(em);
										Messagebox.show("操作成功.", "操作提示",
												Messagebox.OK,
												Messagebox.INFORMATION);
										setGjjlist();
									}
								} else {
									em.setEhcg_ifdeclare(4);
									bll.ModGjj(em);
									Messagebox.show("操作成功.", "操作提示",
											Messagebox.OK,
											Messagebox.INFORMATION);
									setGjjlist();
								}
							} else {
								em.setEhcg_ifdeclare(4);
								bll.ModGjj(em);
								Messagebox.show("操作成功.", "操作提示", Messagebox.OK,
										Messagebox.INFORMATION);
								setGjjlist();
							}

						}
					}
				});
	}

	@Command
	@NotifyChange("gjjlist")
	public void gjjDel(@BindingParam("a") final EmHouseChangeGJJModel em) {

		Messagebox.show("确认提交数据?", "操作提示", new Messagebox.Button[] {
				Messagebox.Button.OK, Messagebox.Button.CANCEL },
				Messagebox.QUESTION,
				new EventListener<Messagebox.ClickEvent>() {
					@Override
					public void onEvent(ClickEvent event) throws Exception {
						// TODO Auto-generated method stub

						if (Messagebox.Button.OK.equals(event.getButton())) {
							WfBusinessService wfbs = new EmHouseChangeGjjImpl();
							WfOperateService wfs = new WfOperateImpl(wfbs);
							Object[] obj = { "删除交单数据", em };
							String[] str = new String[5];
							if (em.getEhcg_tapr_id() != null) {
								str = wfs.StopTask(obj, em.getEhcg_tapr_id(),
										UserInfo.getUsername(), "");
							} else {
								Integer i = bll.delGjj(em.getEhcg_id());
								str[0] = i > 0 ? "1" : "0";
							}

							if (str[0].equals("1")) {
								Messagebox.show("操作成功.", "操作提示", Messagebox.OK,
										Messagebox.INFORMATION);
								setGjjlist();
							} else {
								Messagebox.show("操作失败.", "操作提示", Messagebox.OK,
										Messagebox.ERROR);
							}
						}
					}
				});

	}

	public EmbaseModel getEbm() {
		return ebm;
	}

	public void setEbm(EmbaseModel ebm) {
		this.ebm = ebm;
	}

	public List<EmHouseChangeModel> getList() {
		return list;
	}

	public void setList() {
		EmHouseChangeModel em = new EmHouseChangeModel();
		em.setDataState(2); // 查询未申报/未确认/退回的数据/核查失败
		if (emList.size() > 0) {
			em.setGid(emList.get(0).getGid());
		}
		this.list = bll.getChangeList(em);
	}

	public List<EmHouseBJModel> getBjlist() {
		return bjlist;
	}

	public void setBjlist() {
		EmHouseBJModel m = new EmHouseBJModel();
		m.setGid(emList.get(0).getGid());
		m.setDataState(2);
		m.setOnboard(true);
		this.bjlist = bll.getbjChangeList(m);
	}

	public List<EmHouseChangeGJJModel> getGjjlist() {
		return gjjlist;
	}

	public void setGjjlist() {
		EmHouseChangeGJJModel em = new EmHouseChangeGJJModel();
		em.setState(1);
		em.setGid(emList.get(0).getGid());
		this.gjjlist = bll.getgjjChangeList(em);
	}

	// 客服或中心在托收日3个工作日内提交数据时通知后道部门人员
	public void email(Integer cid, Integer gid, String company, String name) {
		// 参数解释，业务表名：tablename；业务表id:id
		MessageService msgservice = new MessageImpl("emhousechange", 0);

		String msgstr = "(" + cid.toString() + "," + gid.toString() + ")"
				+ company + name + "有公积金变更数据在托收日三个工作日内提交到后道.";

		String eTitle = "员工公积金数据操作提示";

		try {
			// 调用添加短信息方法

			List<loginroleModel> msglist = new ListModelList<>();
			msglist = bll.getuserlist("40");
			for (loginroleModel m : msglist) {
				SysMessageModel sysm = new SysMessageModel();
				sysm.setSyme_content(msgstr);// 短信内容
				sysm.setSyme_log_id(Integer.parseInt(UserInfo.getUserid()));// 发件人id
				sysm.setSymr_name(m.getLog_name());// 收件人姓名
				sysm.setSymr_log_id(m.getLog_id());// 收件人姓名id
				sysm.setEmail(1);
				sysm.setEmailtitle(eTitle);
				msgservice.Add(sysm);
			}

		} catch (Exception e) {
			System.out.println(e.toString());
		}
	}

}
