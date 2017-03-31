package Controller.EmCensus.EmDh;

import impl.MessageImpl;
import impl.WorkflowCore.WfOperateImpl;

import java.math.BigDecimal;
import java.text.DecimalFormat;
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
import org.zkoss.zul.Window;
import org.zkoss.zul.Messagebox.ClickEvent;

import dal.LoginDal;

import service.MessageService;
import service.WorkflowCore.WfBusinessService;
import service.WorkflowCore.WfOperateService;

import Controller.DocumentsInfo.DocumentsInfo_OperationController;
import Model.EmCensusModel;
import Model.EmDhModel;
import Model.EmHouseChangeGJJModel;
import Model.EmHouseUpdateModel;
import Model.EmSheBaoChangeHjModel;
import Model.EmShebaoChangeSZSIModel;
import Model.EmShebaoUpdateModel;
import Model.SysMessageModel;
import Model.TaskProcessViewModel;
import Util.ConstantsUtil;
import Util.UserInfo;
import bll.Archives.EmArchive_SelectBll;
import bll.EmCensus.EmCensus_OperateBll;
import bll.EmCensus.EmDh.EmDh_OperateBll;
import bll.EmCensus.EmDh.EmDh_SelectBll;
import bll.EmCensus.EmDh.EmDh_hjUpdateImpl;
import bll.EmHouse.EmHouseChangeGjjBll;
import bll.EmSheBao.Emsi_OperateBll;
import bll.EmSheBao.Emsi_SelBll;

public class Emdh_Bys_StateUpdateController {
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
	private boolean hjtypevis = false;
	private String hjintype = "";
	private Date emdh_proxytime;
	private String feetype = "";

	public Emdh_Bys_StateUpdateController() {
		if (!dhmodellist.isEmpty()) {
			dhmodel = dhmodellist.get(0);
		}
		if (!tlist.isEmpty()) {
			tmodel = tlist.get(0);
		}
	}

	// 调户申请（毕业生接收）更新状态
	@Command("updatebysstate")
	public void updatebysstate(@BindingParam("win") final Window win,
			@BindingParam("time6") final Date time6,
			@BindingParam("time7") final Date time7,
			@BindingParam("time8") final Date time8,
			@BindingParam("time9") final Date time9,
			@BindingParam("time11") final Date time11,
			@BindingParam("time12") final Date time12,
			@BindingParam("iflh") Radiogroup iflh,
			@BindingParam("ifda") Radiogroup ifda,
			@BindingParam("nowpay") Double nowpay) {
		EmDhModel models = new EmDhModel();
		EmDh_OperateBll opbll = new EmDh_OperateBll();
		models.setId(Integer.parseInt(id));
		models.setEmdh_taprid(Integer.parseInt(tperid));
		models.setStates(tmodel.getWfno_name());
		models.setEmdh_name(dhmodel.getEmdh_name());
		models.setCid(dhmodel.getCid());
		models.setGid(dhmodel.getGid());
		String sql = "";
		String[] str = new String[5];
		// 第六步——表格盖章
		if (tmodel.getWfno_step() == 7) {
			if (time6 == null) {
				Messagebox.show("请选择表格盖章返还日期", "操作提示", Messagebox.OK,
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
		// 第八步——代理部受理
		else if (tmodel.getWfno_step() == 8) {
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
		// 第九步——上报材料
		else if (tmodel.getWfno_step() == 9) {
			if (time7 == null) {
				Messagebox.show("请选择上报材料日期", "操作提示", Messagebox.OK,
						Messagebox.ERROR);
			} else {
				sql = ",emdh_time7='" + timechange(time7) + "'";
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
		// 第十步——接收函下达
		else if (tmodel.getWfno_step() == 10) {
			if (time8 == null) {
				Messagebox.show("请选择接收函下达日期", "操作提示", Messagebox.OK,
						Messagebox.ERROR);
			} else {
				sql = ",emdh_time8='" + timechange(time8) + "'";
				if (nowpay != null) {
					DecimalFormat df = new DecimalFormat("#.00");
					BigDecimal bd = new BigDecimal(nowpay);
					sql = sql + ",emdh_fee='" + df.format(bd)
							+ "',emdh_secondfeetype='" + feetype + "'";
					bd = bd.add(dhmodel.getEmdh_totalfee());
					sql = sql + ",emdh_totalfee='" + df.format(bd) + "'";
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
		// 第十一步——员工领取接收函
		else if (tmodel.getWfno_step() == 11) {
			if (time9 == null) {
				Messagebox.show("请选择员工领取接收函日期", "操作提示", Messagebox.OK,
						Messagebox.ERROR);
			} else {
				sql = ",emdh_time9='" + timechange(time9) + "'";
				// 如果是代理户并且是毕业生接收则在完成目前步骤后完结流程
				if (dhmodel.getEmdh_dhtype().equals("毕业生接收")
						&& dhmodel.getCid() == 5511) {
					str = opbll.OverTask(models, Integer.parseInt(tperid), "");
				} else {
					str = opbll.EmDhupdate(models, sql, 0);
				}
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

		// 第十三步——员工报到
		else if (tmodel.getWfno_step() == 13) {
			if (time11 == null) {
				Messagebox.show("请选择员工报到日期", "操作提示", Messagebox.OK,
						Messagebox.ERROR);
			} else {
				sql = ",emdh_time11='" + timechange(time11) + "',emdh_state=5";
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
		// 第十四步——员工领取介绍信
		else if (tmodel.getWfno_step() == 14) {
			if (time12 == null) {
				Messagebox.show("请选择员工领取介绍信日期", "操作提示", Messagebox.OK,
						Messagebox.ERROR);
			} else {
				sql = ",emdh_time12='" + timechange(time12)
						+ "',emdh_state=6,emdh_inhjtype='" + hjintype + "'";
				str = opbll.EmDhupdate(models, sql, 0);
				if (str[0].equals("1")) {
					String content = models.getEmdh_name() + "("
							+ models.getGid() + ")调户已完成，请及时变更员工的其他项目信息，如档案调入、户口新增";
					String tittle = models.getEmdh_name() + "("
							+ models.getGid() + ")调户完成通知";
					sendMsg(models.getEmdh_client(), content, tittle);
					// 自动判断是否做社保和公积金户籍变更
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
								str = embl.EmCensusInfoAdd(el);
							}
						}
						// 档案保管
						if (ifda.getSelectedItem().getValue().equals("1")) {
							opbll.addFileImport(models.getEmdh_name(),
									models.getGid(), dhmodel.getEmdh_idcard(),
									"", "", "", "", "", "", "", "", "", "",
									UserInfo.getUsername());
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
			@BindingParam("time10") final Date time10,
			@BindingParam("docGrid") final Grid docGrid) {
		if (time10 == null) {
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
								m.setEmdh_taprid(Integer.parseInt(tperid));
								m.setStates(tmodel.getWfno_name());
								String sql = ",emdh_time10='"
										+ timechange(time10) + "'";
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
		models.setEmdh_taprid(Integer.parseInt(tperid));
		models.setStates(tmodel.getWfno_name());
		models.setEmdh_name(dhmodel.getEmdh_name());
		models.setEmdh_dhtype(dhmodel.getEmdh_dhtype());
		sqls = ",emdh_state=1";
		EventListener<ClickEvent> clickListener = new EventListener<Messagebox.ClickEvent>() {
			public void onEvent(ClickEvent event) throws Exception {
				if (Messagebox.Button.YES.equals(event.getButton())) {
					strs = opbll.EmDhupdate(models, sqls, 1);
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
		Messagebox.show("重置流程后流程将退回到第一步，是否确认提交", "提示", new Messagebox.Button[] {
				Messagebox.Button.YES, Messagebox.Button.NO },
				Messagebox.QUESTION, clickListener);
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

	private String ifChecked() {
		Window win = (Window) Path.getComponent("/win");
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

					// EmShebaoChangeSZSIModel ecModel = new
					// EmShebaoChangeSZSIModel();
					String con = "户籍由“" + change_p + "”改为“" + changecon + "”";
					// ecModel.setGid(dhmodel.getGid());
					// ecModel.setOwnmonth(Integer.parseInt(ownmonth));
					// ecModel.setEscs_change("变更户籍");
					// ecModel.setEscs_content(con);
					// ecModel.setEscs_s8("");
					// ecModel.setCid(dhmodel.getCid());
					// ecModel.setEscs_addname(UserInfo.getUsername());
					// ecModel.setEscs_remark("");
					// ecModel.setEscs_name(sbModel.getEsiu_name());
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
		map.put("type", "emdh");
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

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getTperid() {
		return tperid;
	}

	public void setTperid(String tperid) {
		this.tperid = tperid;
	}

	public boolean isHjtypevis() {
		return hjtypevis;
	}

	public void setHjtypevis(boolean hjtypevis) {
		this.hjtypevis = hjtypevis;
	}

	public String getHjintype() {
		return hjintype;
	}

	public void setHjintype(String hjintype) {
		this.hjintype = hjintype;
	}

	public Date getEmdh_proxytime() {
		return emdh_proxytime;
	}

	public void setEmdh_proxytime(Date emdh_proxytime) {
		this.emdh_proxytime = emdh_proxytime;
	}

	public String getFeetype() {
		return feetype;
	}

	public void setFeetype(String feetype) {
		this.feetype = feetype;
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
}
