package Controller.EmCensus.EmDh;

import impl.MessageImpl;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.zkoss.bind.annotation.BindingParam;
import org.zkoss.bind.annotation.Command;
import org.zkoss.bind.annotation.NotifyChange;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.Path;
import org.zkoss.zk.ui.event.EventListener;
import org.zkoss.zul.Combobox;
import org.zkoss.zul.Grid;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Radiogroup;
import org.zkoss.zul.Textbox;
import org.zkoss.zul.Window;
import org.zkoss.zul.Messagebox.ClickEvent;

import service.MessageService;
import dal.LoginDal;

import bll.Archives.EmArchive_SelectBll;
import bll.EmCensus.EmCensus_OperateBll;
import bll.EmCensus.EmDh.EmDh_OperateBll;
import bll.EmCensus.EmDh.EmDh_SelectBll;
import bll.EmHouse.EmHouseChangeGjjBll;
import bll.EmSheBao.Emsi_OperateBll;
import bll.EmSheBao.Emsi_SelBll;
import Controller.DocumentsInfo.DocumentsInfo_OperationController;
import Model.EmArchiveRemarkModel;
import Model.EmCensusModel;
import Model.EmDhModel;
import Model.EmHouseChangeGJJModel;
import Model.EmHouseUpdateModel;
import Model.EmSheBaoChangeHjModel;
import Model.EmShebaoUpdateModel;
import Model.SysMessageModel;
import Model.TaskProcessViewModel;
import Util.ConstantsUtil;
import Util.UserInfo;

public class Emdh_StateChangeController {
	private String id = (String) Executions.getCurrent().getArg().get("daid");
	private String tperid = (String) Executions.getCurrent().getArg().get("id");
	private EmDh_SelectBll bll = new EmDh_SelectBll();
	private List<EmDhModel> dhmodellist = bll.getEmDhInfo(" and a.id=" + id);
	private EmDhModel dhmodel = new EmDhModel();
	private Date now = new Date();
	private EmArchive_SelectBll abll = new EmArchive_SelectBll();
	private List<TaskProcessViewModel> tlist = abll.getLastId(tperid);
	private TaskProcessViewModel tmodel = new TaskProcessViewModel();
	private String[] strs = new String[5];
	private String sqls = "";
	private String tittles = "勾选积分入户正式材料";
	private Window win = (Window) Path.getComponent("/win");

	private String feetype = "";
	private String hjintype = "";
	private boolean hjtypevis = false;
	private List<EmArchiveRemarkModel> model = new ArrayList<EmArchiveRemarkModel>();
	private Date emdh_proxytime;

	public Emdh_StateChangeController() {
		if (!dhmodellist.isEmpty()) {
			dhmodel = dhmodellist.get(0);
		}
		if (!tlist.isEmpty()) {
			tmodel = tlist.get(0);
		}
		if (dhmodel != null
				&& dhmodel.getEmdh_dhtype() != null
				&& (dhmodel.getEmdh_dhtype().equals("毕业生接收") || dhmodel
						.getEmdh_dhtype() == "毕业生接收")) {
			tittles = "勾选提交的报到材料";
		}
		if (id != null && id != "") {
			model = bll.getDhRemark(Integer.parseInt(id));
		}
	}

	// 暂存提交按钮
	@Command("submit")
	public void submit(@BindingParam("win") final Window win,
			@BindingParam("docGrid") Grid docGrid) {
		DocumentsInfo_OperationController docOC = new DocumentsInfo_OperationController();
		// 调用内联页方法chkHaveTo(Grid gd)
		try {
			String[] message = docOC.UpchkHaveTo(docGrid);
			// 判断材料必选项是否已选
			if (message[0].equals("1")) {
				message = docOC.UpsubmitDoc(docGrid, id);
				if (message[0].equals("1")) {
					Messagebox.show("提交成功", "操作提示", Messagebox.OK,
							Messagebox.INFORMATION);
					win.detach();
				} else {
					Messagebox.show("提交失败", "操作提示", Messagebox.OK,
							Messagebox.ERROR);
				}
			} else {
				Messagebox.show("有必选材料没有选,请选择", "操作提示", Messagebox.OK,
						Messagebox.ERROR);
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	// 交接材料确认提交
	@Command("submitconfirm")
	public void submitconfirm(@BindingParam("win") final Window win,
			@BindingParam("docGrid") final Grid docGrid,
			@BindingParam("emdh_time") final Date emdh_time) throws Exception {
		final DocumentsInfo_OperationController docOC = new DocumentsInfo_OperationController();
		String[] message = docOC.UpchkHaveTo(docGrid);
		if (emdh_time == null) {
			Messagebox.show("请选择材料交齐时间", "操作提示", Messagebox.OK,
					Messagebox.ERROR);
		} else if (message[0] != "1") {
			Messagebox.show("有必选材料没有选,请选择", "操作提示", Messagebox.OK,
					Messagebox.ERROR);
		} else {
			EventListener<ClickEvent> clickListener = new EventListener<Messagebox.ClickEvent>() {
				public void onEvent(ClickEvent event) throws Exception {
					if (Messagebox.Button.YES.equals(event.getButton())) {
						EmDh_OperateBll opbll = new EmDh_OperateBll();
						EmDhModel m = new EmDhModel();
						m.setId(Integer.parseInt(id));
						if (tperid != null && !tperid.equals("")) {
							m.setEmdh_taprid(Integer.parseInt(tperid));
						}
						m.setStates(tmodel.getWfno_name());
						String sql = ",emdh_time2='" + timechange(emdh_time)
								+ "'";
						String[] str = opbll.EmDhupdate(m, sql, 0);
						if (str[0].equals("1")) {
							// 调用内联页方法submitDoc(Grid gd)
							docOC.UpsubmitDoc(docGrid, id);
							Messagebox.show("提交成功", "操作提示", Messagebox.OK,
									Messagebox.INFORMATION);
							win.detach();
						} else {
							Messagebox.show("提交失败", "操作提示", Messagebox.OK,
									Messagebox.ERROR);
						}
					}
				}
			};
			Messagebox.show("确认交齐材料并提交?", "提示", new Messagebox.Button[] {
					Messagebox.Button.YES, Messagebox.Button.NO },
					Messagebox.QUESTION, clickListener);
		}
	}

	// 更新状态
	@Command("updatestate")
	public void updatestate(@BindingParam("win") final Window win,
			@BindingParam("time3") final Date time3,
			@BindingParam("dhtype") final String dhtype,
			@BindingParam("time4") final Date time4,
			@BindingParam("time5") final Date time5,
			@BindingParam("time6") final Date time6,
			@BindingParam("time8") final Date time8,
			@BindingParam("time9") final Date time9,
			@BindingParam("time10") final Date time10,
			@BindingParam("iflh") Radiogroup iflh,
			@BindingParam("ifda") Radiogroup ifda,
			@BindingParam("fee") Double fee,
			@BindingParam("nowpay") Double nowpay) {
		final EmDhModel models = new EmDhModel();
		final EmDh_OperateBll opbll = new EmDh_OperateBll();
		models.setId(Integer.parseInt(id));
		models.setEmdh_taprid(Integer.parseInt(tperid));
		models.setStates(tmodel.getWfno_name());
		models.setEmdh_dhtype(dhtype + "");
		models.setEmdh_name(dhmodel.getEmdh_name());
		models.setCid(dhmodel.getCid());
		models.setGid(dhmodel.getGid());
		String[] str = new String[5];
		String sql = "";
		int k = 0;

		// 第四步:条件审核
		if (tmodel.getWfno_step() == 4) {
			if (dhtype.equals("") || dhtype == "") {
				Messagebox.show("请选择调户方式", "操作提示", Messagebox.OK,
						Messagebox.ERROR);
			} else {
				if (dhtype.equals("毕业生接收") || dhtype == "毕业生接收") {
					if (!dhmodel.getEmdh_dhtype().equals("毕业生接收")
							&& dhmodel.getEmdh_dhtype() != "毕业生接收") {
						sqls = ",emdh_doc=20,emdh_dhtype='" + dhtype
								+ "',emdh_state=1,emdh_zhtype='"
								+ dhmodel.getEmdh_zhtype() + "'";
						k = 1;
					}
				} else {
					if (dhmodel.getEmdh_dhtype().equals("毕业生接收")
							|| dhmodel.getEmdh_dhtype() == "毕业生接收") {
						sqls = ",emdh_doc=18,emdh_dhtype='" + dhtype
								+ "',emdh_state=1,emdh_zhtype='"
								+ dhmodel.getEmdh_zhtype() + "'";
						k = 1;
					}
				}

				// k=1表示选择的调干方式与客服提交的不同，要退回并重新建任务单
				if (k == 1) {
					EventListener<ClickEvent> clickListener = new EventListener<Messagebox.ClickEvent>() {
						public void onEvent(ClickEvent event) throws Exception {
							if (Messagebox.Button.YES.equals(event.getButton())) {
								strs = opbll.EmDhupdate(models, sqls, 1);
								if (strs[0].equals("1")) {
									// 调用内联页方法submitDoc(Grid gd)
									Messagebox.show("提交成功", "操作提示",
											Messagebox.OK,
											Messagebox.INFORMATION);
									win.detach();
								} else {
									Messagebox.show(strs[1], "操作提示",
											Messagebox.OK, Messagebox.ERROR);
								}
							}
						}
					};
					Messagebox.show("选择的调户方式和客服提交的不同，如果提交流程将退回到第一步", "提示",
							new Messagebox.Button[] { Messagebox.Button.YES,
									Messagebox.Button.NO },
							Messagebox.QUESTION, clickListener);
				} else {
					if (time3 == null) {
						Messagebox.show("请选择条件审查日期", "操作提示", Messagebox.OK,
								Messagebox.ERROR);
					} else {
						sql = ",emdh_time3='" + timechange(time3)
								+ "',emdh_state=2,emdh_zhtype='"
								+ dhmodel.getEmdh_zhtype() + "'";
						str = opbll.EmDhupdate(models, sql, 0);
						if (str[0].equals("1")) {
							Messagebox.show("提交成功", "操作提示", Messagebox.OK,
									Messagebox.INFORMATION);
							win.detach();
						} else {
							Messagebox.show(str[1], "操作提示", Messagebox.OK,
									Messagebox.ERROR);
						}
					}
				}
			}
		}
		// 第五步:信息预审
		else if (tmodel.getWfno_step() == 5) {
			if (time4 == null) {
				Messagebox.show("请选择信息预审日期", "操作提示", Messagebox.OK,
						Messagebox.ERROR);
			} else {
				sql = ",emdh_time4='" + timechange(time4) + "',emdh_state=3";
				str = opbll.EmDhupdate(models, sql, 0);
				if (str[0].equals("1")) {
					// 如果是代理户并且是毕业生接收则跳过预审确认、表格返回
					if (dhtype.equals("毕业生接收") && dhmodel.getCid() == 5511) {
						Integer newtarp_id = Integer.parseInt(str[2]);
						opbll.skipTask(models, newtarp_id, "", 8);
					}
					Messagebox.show("提交成功", "操作提示", Messagebox.OK,
							Messagebox.INFORMATION);
					win.detach();
				} else {
					Messagebox.show(str[1], "操作提示", Messagebox.OK,
							Messagebox.ERROR);
				}
			}
		}
		// 第六步:预审确认
		else if (tmodel.getWfno_step() == 6) {
			if (time5 == null) {
				Messagebox.show("请选择信息预审日期", "操作提示", Messagebox.OK,
						Messagebox.ERROR);
			}
			if (fee != null && (feetype == null || feetype.equals(""))) {
				Messagebox.show("请选择费用类型", "操作提示", Messagebox.OK,
						Messagebox.ERROR);
			} else {
				sql = ",emdh_time5='" + timechange(time5) + "',emdh_state=4";
				if (fee != null && !fee.equals("")) {
					DecimalFormat df = new DecimalFormat("#.00");
					BigDecimal bdfee = new BigDecimal(fee);
					sql = sql + ",emdh_fee='" + df.format(bdfee) + "'";
					bdfee = bdfee.add(dhmodel.getEmdh_totalfee());
					sql = sql + ",emdh_totalfee='" + df.format(bdfee) + "'";
					sql = sql + ",emdh_fistfeetype='" + feetype + "'";
				}
				str = opbll.EmDhupdate(models, sql, 0);
				if (str[0].equals("1")) {
					Messagebox.show("提交成功", "操作提示", Messagebox.OK,
							Messagebox.INFORMATION);
					win.detach();
				} else {
					Messagebox.show(str[1], "操作提示", Messagebox.OK,
							Messagebox.ERROR);
				}
			}
		}
		// 第七步:员工领取材料
		else if (tmodel.getWfno_step() == 7) {
			if (time6 == null) {
				Messagebox.show("请选择信息预审日期", "操作提示", Messagebox.OK,
						Messagebox.ERROR);
			} else {
				sql = ",emdh_time6='" + timechange(time6) + "'";
				str = opbll.EmDhupdate(models, sql, 0);
				if (str[0].equals("1")) {
					Messagebox.show("提交成功", "操作提示", Messagebox.OK,
							Messagebox.INFORMATION);
					win.detach();
				} else {
					Messagebox.show(str[1], "操作提示", Messagebox.OK,
							Messagebox.ERROR);
				}
			}
		}
		// 第九步:代理部受理
		else if (tmodel.getWfno_step() == 9) {
			if (emdh_proxytime == null) {
				Messagebox.show("请选择代理部受理时间", "操作提示", Messagebox.OK,
						Messagebox.ERROR);
			} else {
				sql = ",emdh_proxytime='" + timechange(emdh_proxytime)
						+ "',emdh_proxyname='" + UserInfo.getUsername() + "'";
				str = opbll.EmDhupdate(models, sql, 0);
				if (str[0].equals("1")) {
					Messagebox.show("提交成功", "操作提示", Messagebox.OK,
							Messagebox.INFORMATION);
					win.detach();
				} else {
					Messagebox.show(str[1], "操作提示", Messagebox.OK,
							Messagebox.ERROR);
				}
			}
		}
		// 第十步:上报材料
		else if (tmodel.getWfno_step() == 10) {
			if (time8 == null) {
				Messagebox.show("请选择信息预审日期", "操作提示", Messagebox.OK,
						Messagebox.ERROR);
			} else {
				sql = ",emdh_time8='" + timechange(time8) + "',emdh_state=5";
				str = opbll.EmDhupdate(models, sql, 0);
				if (str[0].equals("1")) {
					Messagebox.show("提交成功", "操作提示", Messagebox.OK,
							Messagebox.INFORMATION);
					win.detach();
				} else {
					Messagebox.show(str[1], "操作提示", Messagebox.OK,
							Messagebox.ERROR);
				}
			}
		}

		// 第十一步:调令下达
		else if (tmodel.getWfno_step() == 11) {
			if (time9 == null) {
				Messagebox.show("请选择调令下达日期", "操作提示", Messagebox.OK,
						Messagebox.ERROR);
			} else {
				sql = ",emdh_time9='" + timechange(time9) + "',emdh_state=6";

				str = opbll.EmDhupdate(models, sql, 0);
				if (str[0].equals("1")) {
					Messagebox.show("提交成功", "操作提示", Messagebox.OK,
							Messagebox.INFORMATION);
					win.detach();
				} else {
					Messagebox.show(str[1], "操作提示", Messagebox.OK,
							Messagebox.ERROR);
				}
			}
		}

		// 第十二步——员工领取调令
		else if (tmodel.getWfno_step() == 12) {
			if (time9 == null) {
				Messagebox.show("请选择员工领取调令日期", "操作提示", Messagebox.OK,
						Messagebox.ERROR);
			} else {
				models.setGid(dhmodel.getGid());
				sql = ",emdh_time10='" + timechange(time10)
						+ "',emdh_inhjtype='" + hjintype + "'";
				if (nowpay != null) {
					DecimalFormat df = new DecimalFormat("#.00");
					BigDecimal bd = new BigDecimal(nowpay);
					sql = sql + ",emdh_fee='" + df.format(bd)
							+ "',emdh_secondfeetype='" + feetype + "'";
					bd = bd.add(dhmodel.getEmdh_totalfee());
					sql = sql + ",emdh_totalfee='" + df.format(bd) + "'";
				}
				str = opbll.EmDhupdate(models, sql, 3);
				if (str[0].equals("1")) {
					String content = models.getEmdh_name() + "("
							+ models.getGid() + ")调户已完成，请及时变更员工的其他项目信息，如档案调入、户口新增";
					String tittle = models.getEmdh_name() + "("
							+ models.getGid() + ")调户完成通知";
					sendMsg(models.getEmdh_client(), content, tittle);
					addtask();
					// 调户成功后自动做落户和档案保管
					if (models.getGid() != null && !models.getGid().equals("")
							&& models.getCid() != null
							&& !models.getCid().equals("")) {
						// 1、落户
						if (iflh.getSelectedItem().getValue().equals("1")) {
							EmDh_SelectBll dhbll = new EmDh_SelectBll();
							List<EmDhModel> dhmodellist = dhbll
									.getEmDhInfo(" and emdh_state=6 and a.gid="
											+ models.getGid());
							if (dhmodellist.size() > 0) {
								EmCensusModel el = new EmCensusModel();
								el.setGid(models.getGid());
								el.setCid(models.getCid());
								el.setEmhj_name(models.getEmdh_name());
								el.setEmhj_idcard(dhmodel.getEmdh_idcard());
								el.setEmhj_addname(UserInfo.getUsername());
								if (dhmodel.getEmdh_zhtype() != null
										&& dhmodel.getEmdh_zhtype().equals(
												"中智户")) {
									el.setEmhj_type("中智集体户");
								} else {
									el.setEmhj_type("客户公司集体户");
								}
								el.setEmhj_in_class(dhmodel.getEmdh_dhtype());
								el.setEmhj_intype(hjintype);
								EmCensus_OperateBll embl = new EmCensus_OperateBll();
								// str = embl.EmCensusInfoAdd(el);
							}
						}
						// 档案保管
						if (ifda.getSelectedItem().getValue().equals("1")) {
							/*
							 * opbll.addFileImport(models.getEmdh_name(),
							 * models.getGid(), dhmodel.getEmdh_idcard(), "",
							 * "", "", "", "", "", "", "", "", "",
							 * UserInfo.getUsername());
							 */
						}
					}
					Messagebox.show("提交成功", "操作提示", Messagebox.OK,
							Messagebox.INFORMATION);
					win.detach();
				} else {
					Messagebox.show(str[1], "操作提示", Messagebox.OK,
							Messagebox.ERROR);
				}
			}
		}
	}

	// 提交正式材料
	@Command("submitdata")
	public void submitdata(@BindingParam("win") final Window win,
			@BindingParam("time7") final Date time7,
			@BindingParam("docGrid") final Grid docGrid) {
		if (time7 == null) {
			Messagebox.show("请选择材料交齐时间", "操作提示", Messagebox.OK,
					Messagebox.ERROR);
		} else {
			final DocumentsInfo_OperationController docOC = new DocumentsInfo_OperationController();
			try {
				String[] message = docOC.UpchkHaveTo(docGrid);
				if (message[0] != "1") {
					Messagebox.show("有必选材料没有选,请选择", "操作提示", Messagebox.OK,
							Messagebox.ERROR);
				} else {
					EventListener<ClickEvent> clickListener = new EventListener<Messagebox.ClickEvent>() {
						public void onEvent(ClickEvent event) throws Exception {
							if (Messagebox.Button.YES.equals(event.getButton())) {
								EmDh_OperateBll opbll = new EmDh_OperateBll();
								EmDhModel m = new EmDhModel();
								m.setId(Integer.parseInt(id));
								if (tperid != null && !tperid.equals("")) {
									m.setEmdh_taprid(Integer.parseInt(tperid));
								}
								m.setStates(tmodel.getWfno_name());
								String sql = ",emdh_time7='"
										+ timechange(time7) + "'";
								String[] str = opbll.EmDhupdate(m, sql, 0);
								if (str[0].equals("1")) {
									docOC.UpsubmitDoc(docGrid, id);
									Messagebox.show("提交成功", "操作提示",
											Messagebox.OK,
											Messagebox.INFORMATION);
									win.detach();
								} else {
									Messagebox.show("提交失败", "操作提示",
											Messagebox.OK, Messagebox.ERROR);
								}
							}
						}
					};
					Messagebox.show("确认交齐材料并提交?", "提示",
							new Messagebox.Button[] { Messagebox.Button.YES,
									Messagebox.Button.NO },
							Messagebox.QUESTION, clickListener);
				}
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	// 重置流程
	@Command("beginagain")
	public void beginagain(@BindingParam("win") final Window win) {
		final EmDhModel models = new EmDhModel();
		final EmDh_OperateBll opbll = new EmDh_OperateBll();
		models.setId(Integer.parseInt(id));
		if (tperid != null && !tperid.equals("")) {
			models.setEmdh_taprid(Integer.parseInt(tperid));
		}
		models.setStates(tmodel.getWfno_name());
		models.setEmdh_name(dhmodel.getEmdh_name());
		models.setEmdh_dhtype(dhmodel.getEmdh_dhtype());
		sqls = ",emdh_state=1";
		EventListener<ClickEvent> clickListener = new EventListener<Messagebox.ClickEvent>() {
			public void onEvent(ClickEvent event) throws Exception {
				if (Messagebox.Button.YES.equals(event.getButton())) {
					strs = opbll.EmDhupdate(models, sqls, 4);
					if (strs[0].equals("1")) {
						Messagebox.show("提交成功", "操作提示", Messagebox.OK,
								Messagebox.INFORMATION);
						win.detach();
					} else {
						Messagebox.show(strs[1], "操作提示", Messagebox.OK,
								Messagebox.ERROR);
					}
				}
			}
		};
		Messagebox.show("重置流程后流程将退回到交接材料，是否确认提交", "提示",
				new Messagebox.Button[] { Messagebox.Button.YES,
						Messagebox.Button.NO }, Messagebox.QUESTION,
				clickListener);
	}

	// 取消办理
	@Command("cancel")
	public void cancel(@BindingParam("wincancel") final Window wincancel,
			@BindingParam("cancelreason") final String cancelreason) {
		if (cancelreason == null || cancelreason.equals("")
				|| cancelreason == "") {
			Messagebox.show("请输入取消理由", "提示", Messagebox.OK, Messagebox.ERROR);
		} else {
			final EmDhModel models = new EmDhModel();
			final EmDh_OperateBll opbll = new EmDh_OperateBll();
			models.setId(Integer.parseInt(id));
			if (tperid != null && !tperid.equals("")) {
				models.setEmdh_taprid(Integer.parseInt(tperid));
			}
			models.setStates(tmodel.getWfno_name());
			models.setEmdh_name(dhmodel.getEmdh_name());
			models.setEmdh_dhtype(dhmodel.getEmdh_dhtype());
			models.setEmdh_endreason(cancelreason);
			sqls = ",emdh_state=0,emdh_endreason='" + cancelreason + "'";
			EventListener<ClickEvent> clickListener = new EventListener<Messagebox.ClickEvent>() {
				public void onEvent(ClickEvent event) throws Exception {
					if (Messagebox.Button.YES.equals(event.getButton())) {
						strs = opbll.EmDhupdate(models, sqls, 2);
						if (strs[0].equals("1")) {
							Messagebox.show("提交成功", "操作提示", Messagebox.OK,
									Messagebox.INFORMATION);
							wincancel.detach();
						} else {
							Messagebox.show(strs[1], "操作提示", Messagebox.OK,
									Messagebox.ERROR);
						}
					}
				}
			};
			Messagebox.show("取消办理后该数据不能再办理，是否确认取消", "提示",
					new Messagebox.Button[] { Messagebox.Button.YES,
							Messagebox.Button.NO }, Messagebox.QUESTION,
					clickListener);
		}
	}

	// 添加备注
	@Command("addremark")
	@NotifyChange("model")
	public void addremark(@BindingParam("win") final Window win,
			@BindingParam("remark") final String remark,
			@BindingParam("remarktxt") final Textbox remarktxt) {
		if (remark == null || remark.equals("") || remark == "") {
			Messagebox.show("请输入备注内容", "提示", Messagebox.OK, Messagebox.ERROR);
		} else {
			EmDh_OperateBll opbll = new EmDh_OperateBll();
			int k = opbll.addramark(id, remark);
			if (k > 0) {
				Messagebox.show("添加成功", "提示", Messagebox.OK,
						Messagebox.INFORMATION);
				remarktxt.setValue("");
				model = bll.getDhRemark(Integer.parseInt(id));
			} else {
				Messagebox.show("添加失败", "提示", Messagebox.OK, Messagebox.ERROR);
			}
		}
	}

	// 调户完成后自动生成社保和公积金户籍变更的任务单
	private String addtask() {
		String ownmonth = ConstantsUtil.ownmonth;
		String strnewhj = "深户", sbstr = "", gjjstr = "", strs = "";
		Integer gjjk = 0, sbk = 0;
		// 新户籍不等于空
		if (strnewhj != null && !strnewhj.equals("") && strnewhj != "") {
			EmDh_SelectBll bl = new EmDh_SelectBll();
			String gjjname = "", sbname = "";
			// 查询是否有社保服务
			sbname = bl.ishaveService(dhmodel.getGid(), "社会保险服务");

			if (sbname != null && !sbname.equals("")) {
				Emsi_SelBll sbbll = new Emsi_SelBll();
				EmDh_OperateBll obll = new EmDh_OperateBll();
				EmShebaoUpdateModel sbModel = sbbll
						.getShebaoUpdateByGid(dhmodel.getGid());
				boolean ifupdate = false;
				String change_p = "";
				if (sbModel != null) {
					if (sbModel.getEsiu_hj() != null) {
						if (!sbModel.getEsiu_hj().equals("市内城镇")) {
							ifupdate = true;
							change_p = sbModel.getEsiu_hj();
						}
					} else {
						ifupdate = true;
					}
				}
				if (ifupdate) {
					String changecon = "市内城镇";
					String con = "户籍由“" + change_p + "”改为“" + changecon + "”";
					EmSheBaoChangeHjModel hjmm = new EmSheBaoChangeHjModel();
					hjmm.setGid(dhmodel.getGid());
					hjmm.setCid(dhmodel.getCid());
					hjmm.setOwnmonth(Integer.parseInt(ownmonth));
					hjmm.setSbci_addname(UserInfo.getUsername());
					hjmm.setSbci_change("变更户籍");
					hjmm.setSbci_content(con);
					hjmm.setSbci_name(dhmodel.getEmdh_name());
					hjmm.setSbci_remark("");
					Emsi_OperateBll opbll = new Emsi_OperateBll();
					String[] message = obll.changeSZSIAdd(hjmm,
							dhmodel.getEmdh_name());
				}
			}
			// 查询是否有公积金服务
			gjjname = bl.ishaveService(dhmodel.getGid(), "住房公积金服务");
			if (gjjname != null && !gjjname.equals("")) {
				// 查询员工公积金户籍
				EmHouseChangeGjjBll gjjbll = new EmHouseChangeGjjBll();
				List<EmHouseUpdateModel> euList = gjjbll
						.getEmhouseupdateInfoByGid(dhmodel.getGid());
				EmHouseUpdateModel gjjmodel = new EmHouseUpdateModel();
				if (euList.size() > 0) {
					gjjmodel = euList.get(0);
					if (gjjmodel.getEmhu_hj() != null
							&& gjjmodel.getEmhu_hj().equals("深户")) {

					} else {
						EmHouseChangeGJJModel housemodel = new EmHouseChangeGJJModel();
						housemodel.setGid(dhmodel.getGid());
						housemodel.setCid(dhmodel.getCid());
						housemodel.setEhcg_remark("");
						housemodel.setOwnmonth(Integer.parseInt(ownmonth));
						housemodel.setEhcg_addname(UserInfo.getUsername());
						if (gjjmodel.getEmhu_hj() == null) {
							gjjmodel.setEmhu_hj("");
						}
						housemodel.setEhcg_hj_p(gjjmodel.getEmhu_hj());// 修改前户籍
						housemodel.setEhcg_hj_n("深户");// 修改后户籍
						housemodel.setEhcg_name(gjjmodel.getEmhu_name());
						gjjk = gjjbll.addData(housemodel);
					}
				}
			}
		}
		if (gjjk > 0 && sbk > 0) {
			strs = "提交成功，并且新增了社保和公积金的户籍变更";
		} else if (gjjk > 0 && sbk <= 0 && sbstr != "") {
			strs = "提交成功，并且新增了公积金的户籍变更,社保户籍变更新增失败";
		} else if (gjjk > 0 && sbk <= 0 && sbstr == "") {
			strs = "提交成功，并且新增了公积金的户籍变更";
		} else if (gjjk <= 0 && sbk > 0 && gjjstr != "") {
			strs = "提交成功，并且新增了社保的户籍变更,公积金户籍biang新增失败";
		} else if (gjjk <= 0 && sbk > 0 && gjjstr == "") {
			strs = "提交成功，并且新增了社保的户籍变更";
		} else if (gjjk <= 0 && sbk <= 0 && sbstr != "" && gjjstr != "") {
			strs = "提交成功，社保和公积金的户籍变更新增失败";
		} else if (sbstr == "" && gjjstr == "") {
			strs = "提交成功，没有社保和公积金服务";
		}
		return strs;
	}

	private String ifChecked() {
		String strif = "";
		Radiogroup rp = (Radiogroup) win.getFellow("ifup");
		Combobox newhjbox = (Combobox) win.getFellow("newhj");
		String newhjtxt = newhjbox.getValue();
		// 如果选择修改户籍
		if (rp.getSelectedItem().getValue().equals("1")
				|| rp.getSelectedItem().getValue() == "1") {
			if (newhjtxt != null && !newhjtxt.equals("") && newhjtxt != "") {
				strif = newhjtxt;
			}
		}
		return strif;
	}

	// 落户的选择事件
	@Command
	@NotifyChange("hjtypevis")
	public void checkradio(@BindingParam("radio") Radiogroup radio) {
		if (radio.getSelectedItem().getValue().equals("1")) {
			hjtypevis = true;
		} else {
			hjtypevis = false;
		}
	}

	/************* 弹出联系员工页面 ************************/
	@Command
	public void link() {
		Map map = new HashMap<>();
		map.put("gid", dhmodel.getGid());
		Window window = (Window) Executions.createComponents(
				"../EmReg/EmReg_LinkAdd.zul", null, map);
		window.doModal();
	}

	// 弹出备注
	@Command
	public void addRemark() {
		Map map = new HashMap<>();
		map.put("daid", dhmodel.getId() + "");
		map.put("id", dhmodel.getEmdh_taprid() + "");
		map.put("model", dhmodel);
		map.put("gid", dhmodel.getGid());
		Window window = (Window) Executions.createComponents(
				"../EmCensus/Emdh_AddRemark.zul", null, map);
		window.doModal();
	}

	public EmDhModel getDhmodel() {
		return dhmodel;
	}

	public void setDhmodel(EmDhModel dhmodel) {
		this.dhmodel = dhmodel;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public Date getNow() {
		return now;
	}

	public void setNow(Date now) {
		this.now = now;
	}

	public TaskProcessViewModel getTmodel() {
		return tmodel;
	}

	public void setTmodel(TaskProcessViewModel tmodel) {
		this.tmodel = tmodel;
	}

	public String getTittles() {
		return tittles;
	}

	public void setTittles(String tittles) {
		this.tittles = tittles;
	}

	public List<EmArchiveRemarkModel> getModel() {
		return model;
	}

	public void setModel(List<EmArchiveRemarkModel> model) {
		this.model = model;
	}

	public String getFeetype() {
		return feetype;
	}

	public void setFeetype(String feetype) {
		this.feetype = feetype;
	}

	public String getHjintype() {
		return hjintype;
	}

	public void setHjintype(String hjintype) {
		this.hjintype = hjintype;
	}

	public boolean isHjtypevis() {
		return hjtypevis;
	}

	public void setHjtypevis(boolean hjtypevis) {
		this.hjtypevis = hjtypevis;
	}

	public Date getEmdh_proxytime() {
		return emdh_proxytime;
	}

	public void setEmdh_proxytime(Date emdh_proxytime) {
		this.emdh_proxytime = emdh_proxytime;
	}

	// 发短信
	private void sendMsg(String symr_name, String content, String tittle) {
		// 发短信
		MessageService msgservice = new MessageImpl("EmDH",
				Integer.parseInt(id));
		SysMessageModel msgmodel = new SysMessageModel();
		msgmodel.setSyme_content(content);// 短信内容
		msgmodel.setSyme_log_id(Integer.parseInt(UserInfo.getUserid()));// 发件人id
		msgmodel.setSyme_title(tittle);
		LoginDal d = new LoginDal();
		msgmodel.setSymr_name(symr_name);// 收件人姓名
		msgmodel.setSymr_log_id(d.getUserIDByname(symr_name));
		msgmodel.setEmail(1);//同时发邮件
		msgmodel.setEmailtitle(tittle);
		msgservice.Add(msgmodel);
	}

	// 时间格式转换
	private java.sql.Date timechange(Date d) {
		java.sql.Date da = null;
		if (d != null && !d.equals("")) {
			java.sql.Date date = new java.sql.Date(d.getTime());
			da = date;
		}
		return da;
	}
}
