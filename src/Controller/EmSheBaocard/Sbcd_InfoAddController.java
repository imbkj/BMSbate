package Controller.EmSheBaocard;

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
import org.zkoss.bind.annotation.NotifyChange;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.event.EventListener;
import org.zkoss.zkmax.ui.select.annotation.Subscribe;
import org.zkoss.zul.Grid;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Window;
import org.zkoss.zul.Messagebox.ClickEvent;

import sun.org.mozilla.javascript.internal.regexp.SubString;

import bll.EmSheBaocard.EmShebaoCardInfoOperateBll;
import bll.EmSheBaocard.EmShebaoCardInfoSelectBll;

import Controller.DocumentsInfo.DocumentsInfo_OperationController;
import Model.CoShebaoModel;
import Model.EmSheBaoChangeModel;
import Model.EmShebaoCardInfoModel;
import Model.EmbaseModel;
import Model.PubBankModel;
import Model.PubCodeConversionModel;
import Util.UserInfo;

public class Sbcd_InfoAddController {
	private String gid = Executions.getCurrent().getArg().get("gid") + "";
	private EmShebaoCardInfoSelectBll bll = new EmShebaoCardInfoSelectBll();
	private List<EmbaseModel> list = new ArrayList<EmbaseModel>();
	private EmbaseModel model = new EmbaseModel();
	private EmShebaoCardInfoModel m = new EmShebaoCardInfoModel();

	private List<PubCodeConversionModel> classlist = bll
			.getPubCodeConversionList(36, "证件类型");// 证件类型
	private List<PubCodeConversionModel> edulist = bll
			.getPubCodeConversionList(36, "文化程度");// 文化程度
	private List<PubCodeConversionModel> marylist = bll
			.getPubCodeConversionList(36, "婚姻状况");// 婚姻状况
	private List<PubCodeConversionModel> hjlist = bll.getPubCodeConversionList(
			36, "户口性质");// 户口性质
	private List<PubCodeConversionModel> joblist = bll
			.getPubCodeConversionList(36, "职业性质");// 职业

	private List<PubCodeConversionModel> folklist = bll.getFolkList("");// 民族
	private List<PubCodeConversionModel> provlist = bll.getPubProvinceList("");// 联系地址省
	private List<PubCodeConversionModel> citylist = bll.getCityList("");// 联系地址城市
	private List<PubCodeConversionModel> hjcitylist = citylist;// 户籍城市
	private Date birthday, startdate, enddate;
	private Integer pubid;
	private CoShebaoModel comodel = new CoShebaoModel();
	private PubBankModel bankmodel = new PubBankModel();
	private EmSheBaoChangeModel ml = new EmSheBaoChangeModel();

	@Command
	public void addwin(@BindingParam("sbwin") Window win) {
		// 判断有没有社保在册信息
//		if (!bll.ifExistShebaoInfo(Integer.parseInt(gid))) {
//			Messagebox.show("该员工没有社保信息，不能办理社保卡", "操作提示", Messagebox.OK,
//					Messagebox.ERROR);
//			win.detach();
//		} else {
//			if (ml.getId() != null && !ml.getId().equals("")) {
//				if (ml.getEmsc_computerid() != null
//						&& !ml.getEmsc_computerid().equals("")) {
//
//				} else {
//					Messagebox.show("该员工没有社保电脑号，不能办理社保卡", "操作提示",
//							Messagebox.OK, Messagebox.ERROR);
//					win.detach();
//				}
//			} else {
//				Messagebox.show("该员工没有社保信息，不能办理社保卡", "操作提示", Messagebox.OK,
//						Messagebox.ERROR);
//				win.detach();
//			}
//		}
	}

	public Sbcd_InfoAddController() {
		list = bll.getEmbaseInfoById(gid);
		if (gid != null && !gid.equals("")) {
			ml = bll.getshebaoindo(Integer.parseInt(gid));
		}
		if (list.size() > 0) {
			model = list.get(0);
			birthday = StrToDate(model.getEmba_birth());
			m.setSbcd_name(model.getEmba_name());
			m.setGid(model.getGid());
			m.setCid(model.getCid());
			m.setSbcd_sex(model.getEmba_sex());
			m.setSbcd_company(model.getCoba_company());
			if (model.getEmsc_computerid() != null
					&& !model.getEmsc_computerid().equals("")) {
				m.setSbcd_computerid(model.getEmsc_computerid());
			}else
			{
				m.setSbcd_computerid(model.getEmba_computerid());
			}
			m.setSbcd_folk(model.getEmba_folk());
			m.setSbcd_marry("9.未说明婚姻状况");
			m.setSbcd_job("11.其他");
			if (model.getEmba_mobile() == null
					|| model.getEmba_mobile().equals("")) {
				m.setSbcd_mobile("075583323640");
			} else {
				m.setSbcd_mobile(model.getEmba_mobile());
			} 
			m.setSbcd_company(model.getCoba_company());
			m.setSbcd_idcardclass(model.getEmba_idcardclass());
			m.setSbcd_address(model.getEmba_address());
			m.setSbcd_addname(UserInfo.getUsername());
			m.setSbcd_single(model.getEmsc_Single() + "");
			m.setSbcd_idcard(model.getEmba_idcard());
			m.setSbcd_province("广东省");
			m.setSbcd_city("深圳");
			if (model.getEsiu_hj() != null && !model.getEsiu_hj().equals("")) {
				if (model.getEsiu_hj().equals("市内城镇")) {
					m.setSbcd_hj("深户");
				} else if (model.getEsiu_hj().equals("市外城镇")) {
					m.setSbcd_hj("非深户非农业");
				} else if (model.getEsiu_hj().equals("市外农村")) {
					m.setSbcd_hj("非深户农业");
				} else {
					m.setSbcd_hj("其他");
				}
			}

			Calendar cal = Calendar.getInstance();
			int year = cal.get(Calendar.YEAR);
			int month = cal.get(Calendar.MONTH) + 1;
			String ownmonth = "";
			if (month < 10) {
				ownmonth = year + "0" + month;
			} else {
				ownmonth = year + "" + month;
			}
			m.setOwnmonth(Integer.parseInt(ownmonth));

			comodel = bll.getCoshebaoInfo(m.getCid());

			if ( model.getCid()==1733 || model.getEmsc_Single() == 2 ) {
				m.setSbcd_sbnumber("167120");
				m.setSbcd_companyname("深圳中智经济技术合作有限公司（一）");
				if (comodel.getCosb_cardbank() == null
						|| comodel.getCosb_cardbank().equals("")) {
					comodel = bll.getCoshebaoBankInfo("167120");
					if (comodel.getCosb_cardbank() == null
							|| comodel.getCosb_cardbank().equals("")) {
						comodel.setCosb_cardbank("建设银行");
						comodel.setCosb_branchbank("建设银行国会支行");
					}
				}
			} else if (model.getEmsc_Single() == 0) {
				m.setSbcd_sbnumber("391055");
				m.setSbcd_companyname("深圳中智经济技术合作有限公司");

				if (comodel.getCosb_cardbank() == null
						|| comodel.getCosb_cardbank().equals("")) {
					comodel = bll.getCoshebaoBankInfo("391055");
					if (comodel.getCosb_cardbank() == null
							|| comodel.getCosb_cardbank().equals("")) {
						comodel.setCosb_cardbank("中国银行");
						comodel.setCosb_branchbank("中行长城支行");
					}
				}
				
			} else 	if (model.getEmsc_Single() ==3) {
				m.setSbcd_sbnumber("464780");
				m.setSbcd_companyname("深圳中智经济技术合作有限公司（五）");
				if (comodel.getCosb_cardbank() == null
						|| comodel.getCosb_cardbank().equals("")) {
					comodel = bll.getCoshebaoBankInfo("464780");
					if (comodel.getCosb_cardbank() == null
							|| comodel.getCosb_cardbank().equals("")) {
						comodel.setCosb_cardbank("建设银行");
						comodel.setCosb_branchbank("建设银行国会支行");
					}
				}
			}
				else 	if (model.getEmsc_Single() == 4) {
					m.setSbcd_sbnumber("464781");
					m.setSbcd_companyname("深圳中智经济技术合作有限公司（四）");
					if (comodel.getCosb_cardbank() == null
							|| comodel.getCosb_cardbank().equals("")) {
						comodel = bll.getCoshebaoBankInfo("464781");
						if (comodel.getCosb_cardbank() == null
								|| comodel.getCosb_cardbank().equals("")) {
							comodel.setCosb_cardbank("建设银行");
							comodel.setCosb_branchbank("建设银行国会支行");
						}
					}
			else 	
			{
				m.setSbcd_sbnumber(comodel.getCosb_sorid());
				m.setSbcd_companyname(comodel.getCosb_comname());
			}
				
				
				}
			bankmodel = bll.getBankModel(comodel.getCosb_cardbank());
			if (comodel.getCosb_branchbank() != null) {
				m.setSbcd_upbankname(comodel.getCosb_branchbank());
			}
			if (bankmodel.getBank_doc_id() != null) {
				m.setSbcd_bankdocid(bankmodel.getBank_doc_id());
			}

			m.setSbcd_address("深圳市福田区深南中路1002号新闻大厦31楼");// 默认详细联系地址
			if (m.getSbcd_agencies() == null || m.getSbcd_agencies().equals("")) {
				m.setSbcd_agencies("公安局");
			}
		}
		if (m.getSbcd_computerid() == null || m.getSbcd_computerid().equals("")) {
			m.setSbcd_computerid(ml.getEmsc_computerid());
		}
			
		
	}

	// 中心社保卡新增
	@Command
	public void summit(@BindingParam("gd") final Grid gd,
			@BindingParam("win") final Window win) {
		if (m.getSbcd_mobile() == null || m.getSbcd_mobile().equals("")) {
			Messagebox.show("移动电话为空，不能办理社保卡信息", "提示", Messagebox.OK,
					Messagebox.ERROR);
		} else {
			// if (startdate == null) {
			// Messagebox.show("证件有效起始日不能为空", "提示", Messagebox.OK,
			// Messagebox.ERROR);
			// return;
			// }
			// if(m.getSbcd_photonum()==null||m.getSbcd_photonum().equals(""))
			// {
			// Messagebox.show("照片回执号不能为空", "提示", Messagebox.OK,
			// Messagebox.ERROR);
			// return;
			// }
			// if (m.getSbcd_hjprovince() == null
			// || m.getSbcd_hjprovince().equals("")) {
			// Messagebox.show("户籍所在省不能为空", "提示", Messagebox.OK,
			// Messagebox.ERROR);
			// return;
			// }
			// if (m.getSbcd_hjcity() == null || m.getSbcd_hjcity().equals(""))
			// {
			// Messagebox.show("户籍所在市不能为空", "提示", Messagebox.OK,
			// Messagebox.ERROR);
			// return;
			// }
			if (m.getSbcd_content() != null && !m.getSbcd_content().equals("")) {
				// 查询该员工是否有正在办理的社保卡数据
				if (m.getSbcd_content().equals("新增")
						&& (m.getSbcd_photonum() == null || m
								.getSbcd_photonum().equals(""))) {
					Messagebox.show("照片回执号不能为空", "提示", Messagebox.OK,
							Messagebox.ERROR);
					return;
				}
				if (m.getSbcd_upbankname() != null
						&& !m.getSbcd_upbankname().equals("")) {
					final EmShebaoCardInfoOperateBll obll = new EmShebaoCardInfoOperateBll();
					if (birthday != null) {
						m.setSbcd_birthday(DateToStr(birthday));
					}
					if (startdate != null) {
						m.setSbcd_idcardstartdate(DateToStr(startdate));
					}
					if (enddate != null) {
						m.setSbcd_idcardenddate(DateToStr(enddate));
					}
					if (m.getSbcd_idcardclass() != null
							&& !m.getSbcd_idcardclass().equals("")) {
						if (!m.getSbcd_idcardclass().equals(
								model.getEmba_idcardclass())) {
							String a[] = m.getSbcd_idcardclass().split("\\.");
							if (a.length > 1) {
								m.setSbcd_idcardclass(a[1]);
							}
						}
					}
					if (m.getSbcd_education() != null
							&& !m.getSbcd_education().equals("")) {
						String a[] = m.getSbcd_education().split("\\.");
						if (a.length > 1) {
							m.setSbcd_education(a[1]);
						}
					}
					if (m.getSbcd_marry() != null
							&& !m.getSbcd_marry().equals("")) {
						String a[] = m.getSbcd_marry().split("\\.");
						if (a.length > 1) {
							m.setSbcd_marry(a[1]);
						}
					}
					if (m.getSbcd_hj() != null && !m.getSbcd_hj().equals("")) {
						String a[] = m.getSbcd_hj().split("\\.");
						if (a.length > 1) {
							m.setSbcd_hj(a[1]);
						}
					}
					if (m.getSbcd_job() != null && !m.getSbcd_job().equals("")) {
						String a[] = m.getSbcd_job().split("\\.");
						if (a.length > 1) {
							m.setSbcd_job(a[1]);
						}
					}
					m.setSbcd_stateid(15);// 直接到后道申报打单
					Integer task_id = 103;// 既不是中行长城支行也不是建行国会支行
					if (ifCenterMakeCard()) {
						task_id = 115;
						m.setSbcd_stateid(13);
					}

					List<EmShebaoCardInfoModel> list = bll
							.getEmShebaoCardInfoList(" and a.gid="
									+ model.getGid());
					if (list.size() > 0) {
						final Integer task_ids = task_id;
						Messagebox.show("该员工已有社保卡信息，是否继续新增?", "操作提示",
								new Messagebox.Button[] { Messagebox.Button.OK,
										Messagebox.Button.CANCEL },
								Messagebox.QUESTION,
								new EventListener<Messagebox.ClickEvent>() {
									@Override
									public void onEvent(ClickEvent event)
											throws Exception {
										if (Messagebox.Button.OK.equals(event
												.getButton())) {

											String[] str = obll
													.EmShebaoCardAdd(m,
															task_ids);
											if (str[0] == "1") {
												DocumentsInfo_OperationController docOC = new DocumentsInfo_OperationController();
												try {
													String[] message = docOC
															.AddsubmitDoc(
																	gd,
																	str[3].toString());
													if (message[0] == "1")// 新增成功后自动执行下一步
													{
														m.setSbcd_id(Integer
																.parseInt(str[3]
																		.toString()));
														m.setSbcd_tarpid(Integer
																.parseInt(str[2]
																		.toString()));
														m.setCdst_statename("中心核收材料");
														// obll.EmShebaoCardUpdate(m,
														// "");
													}
												} catch (Exception e) {
													// TODO Auto-generated catch
													// block
													e.printStackTrace();
												}
												Messagebox.show("提交成功", "提示",
														Messagebox.OK,
														Messagebox.INFORMATION);
												win.detach();
											} else {
												Messagebox.show("提交失败", "提示",
														Messagebox.OK,
														Messagebox.ERROR);
											}
										}
									}
								});
					} else {
						String[] str = obll.EmShebaoCardAdd(m, task_id);
						if (str[0] == "1") {
							DocumentsInfo_OperationController docOC = new DocumentsInfo_OperationController();
							try {
								String[] message = docOC.AddsubmitDoc(gd,
										str[3].toString());
								if (message[0] == "1")// 新增成功后自动执行下一步
								{
									m.setSbcd_id(Integer.parseInt(str[3]
											.toString()));
									m.setSbcd_tarpid(Integer.parseInt(str[2]
											.toString()));
									m.setCdst_statename("中心核收材料");
									// obll.EmShebaoCardUpdate(m,
									// "");
								}
							} catch (Exception e) {
								// TODO Auto-generated catch
								// block
								e.printStackTrace();
							}
							Messagebox.show("提交成功", "提示", Messagebox.OK,
									Messagebox.INFORMATION);
							win.detach();
						} else {
							Messagebox.show("提交失败", "提示", Messagebox.OK,
									Messagebox.ERROR);
						}
					}
				} else {
					Messagebox.show("该员工的单位社保账户没有社保卡办理银行，请先在单位账户信息中补充", "提示",
							Messagebox.OK, Messagebox.ERROR);
				}
			} else {
				Messagebox.show("请选择办理类型", "提示", Messagebox.OK,
						Messagebox.ERROR);
			}
		}
	}

	// 客服提交新增社保卡信息
	@Command
	public void clientsummit(@BindingParam("gd") final Grid gd,
			@BindingParam("win") Window win) {
		if (m.getSbcd_mobile() == null || m.getSbcd_mobile().equals("")) {
			Messagebox.show("移动电话为空，不能办理社保卡信息", "提示", Messagebox.OK,
					Messagebox.ERROR);
		} else {
			if (m.getSbcd_content() != null && !m.getSbcd_content().equals("")) {
				if (m.getSbcd_upbankname() != null
						&& !m.getSbcd_upbankname().equals("")) {
					EmShebaoCardInfoOperateBll obll = new EmShebaoCardInfoOperateBll();
					if (birthday != null) {
						m.setSbcd_birthday(DateToStr(birthday));
					}
					if (startdate != null) {
						m.setSbcd_idcardstartdate(DateToStr(startdate));
					}
					if (enddate != null) {
						m.setSbcd_idcardenddate(DateToStr(enddate));
					}
					if (m.getSbcd_idcardclass() != null
							&& !m.getSbcd_idcardclass().equals("")) {
						if (!m.getSbcd_idcardclass().equals(
								model.getEmba_idcardclass())) {
							String a[] = m.getSbcd_idcardclass().split("\\.");
							if (a.length > 1) {
								m.setSbcd_idcardclass(a[1]);
							}
						}
					}
					if (m.getSbcd_education() != null
							&& !m.getSbcd_education().equals("")) {
						String a[] = m.getSbcd_education().split("\\.");
						if (a.length > 1) {
							m.setSbcd_education(a[1]);
						}
					}
					if (m.getSbcd_marry() != null
							&& !m.getSbcd_marry().equals("")) {
						String a[] = m.getSbcd_marry().split("\\.");
						if (a.length > 1) {
							m.setSbcd_marry(a[1]);
						}
					}
					if (m.getSbcd_hj() != null && !m.getSbcd_hj().equals("")) {
						String a[] = m.getSbcd_hj().split("\\.");
						if (a.length > 1) {
							m.setSbcd_hj(a[1]);
						}
					}
					if (m.getSbcd_job() != null && !m.getSbcd_job().equals("")) {
						String a[] = m.getSbcd_job().split("\\.");
						if (a.length > 1) {
							m.setSbcd_job(a[1]);
						}
					}
					m.setSbcd_stateid(12);
					Integer task_id = 103;// 既不是中行长城支行也不是建行国会支行
					if (ifCenterMakeCard()) {
						task_id = 115;
						m.setSbcd_stateid(13);
					}

					String[] str = obll.EmShebaoCardClientAdd(m, task_id);
					if (str[0] == "1") {
						DocumentsInfo_OperationController docOC = new DocumentsInfo_OperationController();
						try {
							String[] message = docOC.AddsubmitDoc(gd,
									str[3].toString());
						} catch (Exception e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
						Messagebox.show("提交成功", "提示", Messagebox.OK,
								Messagebox.INFORMATION);
						win.detach();
					} else {
						Messagebox.show("提交失败", "提示", Messagebox.OK,
								Messagebox.ERROR);
					}
				} else {
					Messagebox.show("该员工的单位社保账户没有社保卡办理银行，请先在单位账户信息中补充", "提示",
							Messagebox.OK, Messagebox.ERROR);
				}
			} else {
				Messagebox.show("请选择办理类型", "提示", Messagebox.OK,
						Messagebox.ERROR);
			}
		}
	}

	//
	// 客服暂存
	@Command
	public void csummit(@BindingParam("gd") final Grid gd,
			@BindingParam("win") Window win) {
		if (m.getSbcd_content() != null && !m.getSbcd_content().equals("")) {
			if (m.getSbcd_upbankname() != null
					&& !m.getSbcd_upbankname().equals("")) {
				EmShebaoCardInfoOperateBll obll = new EmShebaoCardInfoOperateBll();
				if (birthday != null) {
					m.setSbcd_birthday(DateToStr(birthday));
				}
				if (startdate != null) {
					m.setSbcd_idcardstartdate(DateToStr(startdate));
				}
				if (enddate != null) {
					m.setSbcd_idcardenddate(DateToStr(enddate));
				}
				if (m.getSbcd_idcardclass() != null
						&& !m.getSbcd_idcardclass().equals("")) {
					if (!m.getSbcd_idcardclass().equals(
							model.getEmba_idcardclass())) {
						String a[] = m.getSbcd_idcardclass().split("\\.");
						if (a.length > 1) {
							m.setSbcd_idcardclass(a[1]);
						}
					}
				}
				if (m.getSbcd_education() != null
						&& !m.getSbcd_education().equals("")) {
					String a[] = m.getSbcd_education().split("\\.");
					if (a.length > 1) {
						m.setSbcd_education(a[1]);
					}
				}
				if (m.getSbcd_marry() != null && !m.getSbcd_marry().equals("")) {
					String a[] = m.getSbcd_marry().split("\\.");
					if (a.length > 1) {
						m.setSbcd_marry(a[1]);
					}
				}
				if (m.getSbcd_hj() != null && !m.getSbcd_hj().equals("")) {
					String a[] = m.getSbcd_hj().split("\\.");
					if (a.length > 1) {
						m.setSbcd_hj(a[1]);
					}
				}
				if (m.getSbcd_job() != null && !m.getSbcd_job().equals("")) {
					String a[] = m.getSbcd_job().split("\\.");
					if (a.length > 1) {
						m.setSbcd_job(a[1]);
					}
				}
				m.setSbcd_stateid(13);
				Integer ll = obll.sbcdAdd(m);
				if (ll > 0) {
					DocumentsInfo_OperationController docOC = new DocumentsInfo_OperationController();
					try {
						String[] message = docOC
								.AddsubmitDoc(gd, ll.toString());
					} catch (Exception e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					Messagebox.show("提交成功", "提示", Messagebox.OK,
							Messagebox.INFORMATION);
					win.detach();
				} else {
					Messagebox.show("提交失败", "提示", Messagebox.OK,
							Messagebox.ERROR);
				}
			} else {
				Messagebox.show("该员工的单位社保账户没有社保卡办理银行，请先在单位账户信息中补充", "提示",
						Messagebox.OK, Messagebox.ERROR);
			}
		} else {
			Messagebox.show("请选择办理类型", "提示", Messagebox.OK, Messagebox.ERROR);
		}
	}

	// 查询并判断是否是中行长城支行或者建行国会支行
	private boolean ifCenterMakeCard() {
		boolean flag = false;
		if (comodel != null) {
			if (comodel.getCosb_branchbank() != null) {
				if (comodel.getCosb_branchbank().equals("中行长城支行")
						|| comodel.getCosb_branchbank().contains("国会支行")) {
					flag = true;
				}
			}
		}
		return flag;
	}

	// 联系地址省选择事件
	@Command
	@NotifyChange("citylist")
	public void changeaddcity(@BindingParam("val") String val) {
		if (val != null && !val.equals("")) {
			citylist = bll.getCityList(" and b.name='" + val + "'");
		}
	}

	// 户籍省选择事件
	@Command
	@NotifyChange("hjcitylist")
	public void changehjcity(@BindingParam("val") String val) {
		if (val != null && !val.equals("")) {
			hjcitylist = bll.getCityList(" and b.name='" + val + "'");
		}
	}

	// 根据有效期的开始日期计算有效期终止日期
	@Command
	@NotifyChange("enddate")
	public void changedate() {
		if (startdate != null) {
			String str = DateToStr(startdate);
			if (str != null && !str.equals("")) {
				String a[] = str.split("-");
				int year = Integer.parseInt(a[0]) + 10;
				String en = year + "-" + a[1] + "-" + a[2];
				enddate = StrToDate(en);
			}
		}
	}

	// 发短信
	@Command
	public void sendsms() {
		Map map = new HashMap<>();
		if (m.getSbcd_mobile() != null && !m.getSbcd_mobile().equals("")) {
			map.put("mobile", m.getSbcd_mobile());
			map.put("gid", m.getGid());
			Window window;
			window = (Window) Executions.createComponents(
					"../Embase/SMS_Add.zul", null, map);
			window.doModal();
		} else {
			Messagebox
					.show("手机号码不能为空", "操作提示", Messagebox.OK, Messagebox.ERROR);
		}
	}

	/**
	 * 日期转换成字符串
	 * 
	 * @param date
	 * @return str
	 */
	public static String DateToStr(Date date) {
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		String str = "";
		if (date != null) {
			str = format.format(date);
		}
		return str;
	}

	/**
	 * 日期转换成Long
	 * 
	 * @param date
	 * @return str
	 */
	public static long DateToLong(Date date) {
		SimpleDateFormat format = new SimpleDateFormat("yyyyMMdd");
		long str = 0;
		if (date != null) {
			str = Long.parseLong(format.format(date));
		}
		return str;
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

	public EmbaseModel getModel() {
		return model;
	}

	public void setModel(EmbaseModel model) {
		this.model = model;
	}

	public EmShebaoCardInfoModel getM() {
		return m;
	}

	public void setM(EmShebaoCardInfoModel m) {
		this.m = m;
	}

	public List<PubCodeConversionModel> getClasslist() {
		return classlist;
	}

	public void setClasslist(List<PubCodeConversionModel> classlist) {
		this.classlist = classlist;
	}

	public List<PubCodeConversionModel> getEdulist() {
		return edulist;
	}

	public void setEdulist(List<PubCodeConversionModel> edulist) {
		this.edulist = edulist;
	}

	public List<PubCodeConversionModel> getMarylist() {
		return marylist;
	}

	public void setMarylist(List<PubCodeConversionModel> marylist) {
		this.marylist = marylist;
	}

	public List<PubCodeConversionModel> getHjlist() {
		return hjlist;
	}

	public void setHjlist(List<PubCodeConversionModel> hjlist) {
		this.hjlist = hjlist;
	}

	public List<PubCodeConversionModel> getJoblist() {
		return joblist;
	}

	public void setJoblist(List<PubCodeConversionModel> joblist) {
		this.joblist = joblist;
	}

	public List<PubCodeConversionModel> getFolklist() {
		return folklist;
	}

	public void setFolklist(List<PubCodeConversionModel> folklist) {
		this.folklist = folklist;
	}

	public List<PubCodeConversionModel> getProvlist() {
		return provlist;
	}

	public void setProvlist(List<PubCodeConversionModel> provlist) {
		this.provlist = provlist;
	}

	public List<PubCodeConversionModel> getCitylist() {
		return citylist;
	}

	public void setCitylist(List<PubCodeConversionModel> citylist) {
		this.citylist = citylist;
	}

	public List<PubCodeConversionModel> getHjcitylist() {
		return hjcitylist;
	}

	public void setHjcitylist(List<PubCodeConversionModel> hjcitylist) {
		this.hjcitylist = hjcitylist;
	}

	public Date getBirthday() {
		return birthday;
	}

	public void setBirthday(Date birthday) {
		this.birthday = birthday;
	}

	public Date getStartdate() {
		return startdate;
	}

	public void setStartdate(Date startdate) {
		this.startdate = startdate;
	}

	public Date getEnddate() {
		return enddate;
	}

	public void setEnddate(Date enddate) {
		this.enddate = enddate;
	}

	public Integer getPubid() {
		return pubid;
	}

	public void setPubid(Integer pubid) {
		this.pubid = pubid;
	}

	public PubBankModel getBankmodel() {
		return bankmodel;
	}

	public void setBankmodel(PubBankModel bankmodel) {
		this.bankmodel = bankmodel;
	}

}
