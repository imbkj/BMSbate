package Controller.EmSheBao;

import impl.MessageImpl;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.zkoss.bind.BindContext;
import org.zkoss.bind.annotation.BindingParam;
import org.zkoss.bind.annotation.Command;
import org.zkoss.bind.annotation.ContextParam;
import org.zkoss.bind.annotation.ContextType;
import org.zkoss.bind.annotation.NotifyChange;
import org.zkoss.util.media.Media;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.event.EventListener;
import org.zkoss.zk.ui.event.UploadEvent;
import org.zkoss.zul.Bandbox;
import org.zkoss.zul.Checkbox;
import org.zkoss.zul.Combobox;
import org.zkoss.zul.Grid;
import org.zkoss.zul.Listitem;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Radiogroup;
import org.zkoss.zul.Textbox;
import org.zkoss.zul.Window;
import org.zkoss.zul.Messagebox.ClickEvent;

import service.MessageService;
import Controller.DocumentsInfo.DocumentsInfo_OperationController;
import Model.EmShebaoChangeMAModel;
import Model.LoginModel;
import Model.SysMessageModel;
import Util.FileOperate;
import Util.UserInfo;
import bll.EmSheBao.EmSheBao_DSelectBll;
import bll.EmSheBao.Emsc_DeclareOperateBll;
import bll.EmSheBao.Emsc_DeclareSelBll;
import bll.SysMessage.Message_SelectBll;
import bll.SysMessage.SysMessage_AddBll;
import bll.SystemControl.SystLogInfoBll;
import dal.LoginDal;

public class Emsc_DetailMAController {
	private final int id = Integer.parseInt(Executions.getCurrent().getArg()
			.get("daid").toString());

	private EmShebaoChangeMAModel maModel;
	private Emsc_DeclareSelBll selbll;
	// 页面值：原因
	private String reason;

	// 系统短信
	private SysMessage_AddBll smbll = new SysMessage_AddBll();
	private Message_SelectBll sbll = new Message_SelectBll();
	private List<LoginModel> loginList = smbll.getLoginList(" and log_id<>"
			+ UserInfo.getUserid());
	private List<LoginModel> deptlist = new ArrayList<LoginModel>();
	private String selectedname = "";
	private List<LoginModel> selectedlist = new ArrayList<LoginModel>();
	private MessageService msgservice;
	private LoginModel ml = new LoginModel();
	private String msgname = "";
	private List<SysMessageModel> list = new ArrayList<SysMessageModel>();
	private List<SysMessageModel> temList = sbll
			.gettemList(" and pmte_class='社会保险生育津贴'");
	private LoginDal d = new LoginDal();
	private String old_ifdeclare = "";

	private String absolutePath;
	private Media[] media;
	private String[] uploadFlieName;// 数据库记录的文件名
	private String uploadfolder;
	private String[] uploadFlie;// 浏览后页面显示的文件名

	public Emsc_DetailMAController() {
		selbll = new Emsc_DeclareSelBll();
		maModel = selbll.getMAChangeById(id);
		// 原数据状态
		old_ifdeclare = String.valueOf(maModel.getEscm_ifdeclare());

		// 系统短信
		List<LoginModel> llist = new ArrayList<LoginModel>();
		LoginModel m = new LoginModel();
		m.setLog_name(maModel.getEscm_addname());
		m.setLog_id(d.getUserIDByname(maModel.getEscm_addname()));
		llist.add(m);

		if (llist.size() > 0) {
			for (LoginModel lm : llist) {
				if (lm.getLog_name() != null) {
					ml = lm;
					selectedlist.add(ml);
					getSelectname();
					if (msgname == "") {
						msgname = "'" + lm.getLog_name() + "'";
					} else {
						msgname = msgname + ",'" + lm.getLog_name() + "'";
					}
				}
			}
		}
		msgservice = new MessageImpl("EmShebaoChangeMA", id);
		deptlist = sbll.getLoginList(msgname);
		list = msgservice.Select();
		// 把登陆人的该业务ID的未读信息改为已读
		msgservice.Read();

		media = new Media[2];
		uploadFlieName = new String[2];
		uploadFlie = new String[2];
		uploadFlie[0] = maModel.getEscm_af_filename();
		uploadFlie[1] = maModel.getEscm_bf_filename();
		absolutePath = FileOperate.getAbsolutePath();
		uploadfolder = "EmSheBao/File/Upload/Declare/";

	}

	// 浏览文件
	@Command("browse")
	@NotifyChange("uploadFlie")
	public void browse(@ContextParam(ContextType.BIND_CONTEXT) BindContext ul,
			@BindingParam("type") int type) {
		UploadEvent upEvent = (UploadEvent) ul.getTriggerEvent();
		Media m = upEvent.getMedia();
		if ("pdf".equals(m.getFormat())) {
			if (type == 1) {
				media[0] = m;// 申请表
				uploadFlie[0] = media[0].getName();
				uploadFlieName[0] = mosaicFileName(type);
			} else if (type == 2) {
				media[1] = m;// 批量表
				uploadFlie[1] = media[1].getName();
				uploadFlieName[1] = mosaicFileName(type);
			}
		} else {
			if (type == 1) {
				media[0] = null;
				uploadFlie[0] = "";
				uploadFlieName[0] = "";
			} else if (type == 2) {
				media[1] = null;
				uploadFlie[1] = "";
				uploadFlieName[1] = "";
			}
			Messagebox.show("上传的文件格式有误。", "操作提示", Messagebox.OK,
					Messagebox.NONE);
		}
	}

	// 上传
	@Command("upload")
	public void upload() {
		String[] str1 = null; // 申请表
		Integer chk_af = 0;

		String[] str2 = null; // 批量表
		Integer chk_bf = 0;

		String log = "";// 系统日志
		try {
			// 申请表
			if (this.media[0] != null) {
				// 文件上传服务器
				if (uploadFile(this.media[0], uploadFlieName[0])) {
					// 更新数据库
					log = log + "上传社保生育津贴申请表：" + uploadFlieName[0];
					maModel.setEscm_af_filename(uploadFlieName[0]);
					chk_af = 1;
				} else {
					Messagebox.show("申请表上传出错。", "操作提示", Messagebox.OK,
							Messagebox.ERROR);
					return;
				}
			}
			// 批量表
			if (this.media[1] != null) {
				// 文件上传服务器
				if (uploadFile(this.media[1], uploadFlieName[1])) {
					// 更新数据库
					log = log + "上传社保生育津贴批量表：" + uploadFlieName[1];
					maModel.setEscm_bf_filename(uploadFlieName[1]);
					chk_bf = 1;
				} else {
					Messagebox.show("批量表上传出错。", "操作提示", Messagebox.OK,
							Messagebox.ERROR);
					return;
				}
			}

			// 更新数据库
			if (chk_af == 1 || chk_bf == 1) {
				Emsc_DeclareOperateBll bll = new Emsc_DeclareOperateBll();
				int i = bll.UpChangeMaAfBfFile(maModel, chk_af, chk_bf);

				if (i == 1) {
					// 加log
					if (!"".equals(log)) {
						SystLogInfoBll logBll = new SystLogInfoBll();
						logBll.addLog(null, id, maModel.getCid(),
								maModel.getGid(), "社保信息", log,
								UserInfo.getUsername());
					}
					Messagebox.show("文件导入成功。", "操作提示", Messagebox.OK,
							Messagebox.NONE);
				} else {
					Messagebox.show("更新失败。", "操作提示", Messagebox.OK,
							Messagebox.ERROR);
				}
			} else {
				Messagebox.show("导入失败。", "操作提示", Messagebox.OK,
						Messagebox.ERROR);
			}

		} catch (Exception e) {
			e.printStackTrace();
			Messagebox.show("导入出错。", "操作提示", Messagebox.OK, Messagebox.ERROR);
		}
	}

	// 拼接上传文件的名称
	private String mosaicFileName(Integer type) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
		Date date = new Date();
		String nowtime = sdf.format(date);
		String f_type = "";
		if (type == 1) {// 申请表
			f_type = "af_";
		} else if (type == 2) {// 批量表
			f_type = "bf_";
		}
		String name = "ma_" + f_type + nowtime + ".pdf";
		return name;
	}

	// 文件上传服务器
	private boolean uploadFile(Media m, String filename) {
		boolean bool = false;
		try {
			if (FileOperate.upload(m, uploadfolder + filename)) {
				bool = true;
			}
		} catch (Exception e) {

		}
		return bool;
	}

	// 添加备忘
	@Command("addFlag")
	public void addFlag(@BindingParam("flag") Textbox txtFlag) {
		if (!"".equals(txtFlag.getValue())) {
			if (!txtFlag.getValue().equals(maModel.getEscm_flag())) {
				Emsc_DeclareOperateBll bll = new Emsc_DeclareOperateBll();
				maModel.setEscm_flag(txtFlag.getValue());
				int i = bll.UpChangeMaFlag(maModel, UserInfo.getUsername());
				if (i == 1) {
					// 加log
					SystLogInfoBll logBll = new SystLogInfoBll();
					logBll.addLog(null, id, maModel.getCid(), maModel.getGid(),
							"社保信息", "添加社保生育津贴申请备忘：" + txtFlag.getValue(),
							UserInfo.getUsername());

					Messagebox.show("备忘添加成功。", "操作提示", Messagebox.OK,
							Messagebox.NONE);
				} else {
					Messagebox.show("备忘添加失败。", "操作提示", Messagebox.OK,
							Messagebox.ERROR);
				}
			} else {
				Messagebox.show("您并未修改备忘信息，无需添加。", "操作提示", Messagebox.OK,
						Messagebox.NONE);
			}
		} else {
			Messagebox
					.show("请输入备忘信息。", "操作提示", Messagebox.OK, Messagebox.ERROR);
		}
	}

	// 更新批次号
	@Command("addNum")
	public void addNum(@BindingParam("num") Textbox txtNum) {
		if (!"".equals(txtNum.getValue())) {
			if (!txtNum.getValue().equals(maModel.getEscm_batchnum())) {
				Emsc_DeclareOperateBll bll = new Emsc_DeclareOperateBll();
				maModel.setEscm_batchnum(txtNum.getValue());
				int i = bll.UpChangeMaBatchNum(maModel);
				if (i == 1) {
					// 加log
					SystLogInfoBll logBll = new SystLogInfoBll();
					logBll.addLog(null, id, maModel.getCid(), maModel.getGid(),
							"社保信息", "添加社保生育津贴申请批次号：" + txtNum.getValue(),
							UserInfo.getUsername());

					Messagebox.show("批次号添加成功。", "操作提示", Messagebox.OK,
							Messagebox.NONE);
				} else {
					Messagebox.show("批次号添加失败。", "操作提示", Messagebox.OK,
							Messagebox.ERROR);
				}
			} else {
				Messagebox.show("您并未修改批次号信息，无需添加。", "操作提示", Messagebox.OK,
						Messagebox.NONE);
			}
		} else {
			Messagebox.show("请输入批次号。", "操作提示", Messagebox.OK, Messagebox.ERROR);
		}

	}

	// 更新金额
	@Command("addFee")
	public void addFee(@BindingParam("fee") Textbox txtFee) {

		if (!"".equals(txtFee.getValue())) {
			try {
				BigDecimal t_fee = new BigDecimal(txtFee.getValue());

				if (maModel.getEscm_fee()==null || t_fee.compareTo(maModel.getEscm_fee()) != 0) {
					Emsc_DeclareOperateBll bll = new Emsc_DeclareOperateBll();
					maModel.setEscm_fee(t_fee);
					int i = bll.UpChangeMaFee(maModel);
					if (i == 1) {
						// 加log
						SystLogInfoBll logBll = new SystLogInfoBll();
						logBll.addLog(null, id, maModel.getCid(),
								maModel.getGid(), "社保信息", "添加社保生育津贴申请金额："
										+ txtFee.getValue(),
								UserInfo.getUsername());

						Messagebox.show("金额添加成功。", "操作提示", Messagebox.OK,
								Messagebox.NONE);
					} else {
						Messagebox.show("金额添加失败。", "操作提示", Messagebox.OK,
								Messagebox.ERROR);
					}
				} else {
					Messagebox.show("您并未修改金额信息，无需添加。", "操作提示", Messagebox.OK,
							Messagebox.NONE);
				}

			} catch (Exception e) {
				Messagebox.show("请输入正确的金额格式。", "操作提示", Messagebox.OK, Messagebox.ERROR);
			}
		} else {
			Messagebox.show("请输入金额。", "操作提示", Messagebox.OK, Messagebox.ERROR);
		}

	}

	// 修改状态
	@Command("upState")
	public void upState(@BindingParam("state") Radiogroup rgState,
			@BindingParam("win") Window win, @BindingParam("cb") Checkbox cb) {
		try {
			String state = rgState.getSelectedItem().getValue().toString();

			if (!old_ifdeclare.equals(state)) {

				// 退回时录入原因
				if ("3".equals(state)) {
					if (selectedlist.size() <= 0) {
						Messagebox.show("请选择退回短信收件人。", "操作提示", Messagebox.OK,
								Messagebox.ERROR);
						return;
					} else if (reason == null || "".equals(reason)) {
						Messagebox.show("请填写退回原因。", "操作提示", Messagebox.OK,
								Messagebox.ERROR);
						return;
					} else {
						maModel.setEscm_reason(reason);
					}
				}
				maModel.setEscm_ifdeclare(Integer.parseInt(state));
				Emsc_DeclareOperateBll opbll = new Emsc_DeclareOperateBll();
				String[] mes = opbll.ChangeMADeclareUpState(maModel);
				if ("1".equals(mes[0])) {
					// 退回系统短信
					if ("3".equals(state)) {
						EmSheBao_DSelectBll dsbll = new EmSheBao_DSelectBll();
						for (LoginModel m : selectedlist) {
							SysMessageModel model = new SysMessageModel();
							model.setSyme_content(reason);// 短信内容
							model.setSyme_log_id(Integer.parseInt(UserInfo
									.getUserid()));// 发件人id
							model.setSymr_name(m.getLog_name());// 收件人姓名
							model.setSymr_log_id(m.getLog_id());
							model.setEmail(1);// 1表示同时发邮件
							model.setSyme_title(maModel.getEscm_company()
									+ " 的 " + maModel.getEscm_name()
									+ " ("+String.valueOf(maModel.getGid())+")社保生育津贴申请数据被退回");
							// 调用方法
							msgservice.Add(model);
						}
					}

					// 加log
					SystLogInfoBll logBll = new SystLogInfoBll();
					logBll.addLog(null, id, maModel.getCid(), maModel.getGid(),
							"社保信息", "更新社保生育津贴申请状态：" + state,
							UserInfo.getUsername());

					// 成功提示
					Messagebox.show(mes[1], "操作提示", Messagebox.OK,
							Messagebox.NONE);
					win.detach();
				} else {
					// 错误提示
					Messagebox.show(mes[1], "操作提示", Messagebox.OK,
							Messagebox.ERROR);
				}

			} else {
				// 弹出提示
				Messagebox.show("状态未操作变更，请选择状态！", "操作提示", Messagebox.OK,
						Messagebox.ERROR);
			}

		} catch (Exception e) {
			Messagebox.show("操作出错。", "操作提示", Messagebox.OK, Messagebox.ERROR);
		}
	}

	// 选择收件人
	@Command
	@NotifyChange("selectedname")
	public void checkname(@BindingParam("bd") Bandbox bx,
			@BindingParam("model") LoginModel model,
			@BindingParam("lbitem") Listitem item) {
		if (item.isSelected()) {
			selectedlist.add(model);
		} else {
			selectedlist.remove(model);
		}
		getSelectname();
	}

	// 把已选择的收件人串起来
	private void getSelectname() {
		selectedname = "";
		for (LoginModel m : selectedlist) {
			if (selectedname == "") {
				selectedname = m.getLog_name();
			} else {
				selectedname = selectedname + ";" + m.getLog_name();
			}
		}
	}

	// 选择模板
	@Command("temselect")
	@NotifyChange("reason")
	public void temselect(@BindingParam("temcb") Combobox temcb) {
		if (temcb.getValue() != null || !temcb.getValue().equals("")) {
			reason = "";
			SysMessageModel m = temcb.getSelectedItem().getValue();
			reason = m.getPmte_content();
		}
	}

	// 更新材料
	@Command("upDocData")
	public void upDocData(@BindingParam("gd") Grid gd,
			@BindingParam("win") final Window win) throws Exception {
		try {
			// 调用内联页方法
			DocumentsInfo_OperationController docOC = new DocumentsInfo_OperationController();
			String[] message = docOC.UpsubmitDocHO(gd, String.valueOf(id));

			if (message[0].equals("1")) {
				EventListener<ClickEvent> clickListener = new EventListener<Messagebox.ClickEvent>() {
					public void onEvent(ClickEvent event) throws Exception {
						if (Messagebox.Button.OK.equals(event.getButton())) {
							win.detach();
						}
					}
				};
				Messagebox.show(message[1], "操作提示", Messagebox.OK,
						Messagebox.INFORMATION);
			} else {
				// 弹出提示
				Messagebox.show(message[1], "操作提示", Messagebox.OK,
						Messagebox.ERROR);
			}
		} catch (Exception e) {
			Messagebox.show("操作失败。", "操作提示", Messagebox.OK, Messagebox.ERROR);
		}
	}

	public EmShebaoChangeMAModel getmaModel() {
		return maModel;
	}

	public String getReason() {
		return reason;
	}

	public void setReason(String reason) {
		this.reason = reason;
	}

	public List<LoginModel> getLoginList() {
		return loginList;
	}

	public void setLoginList(List<LoginModel> loginList) {
		this.loginList = loginList;
	}

	public List<LoginModel> getDeptlist() {
		return deptlist;
	}

	public void setDeptlist(List<LoginModel> deptlist) {
		this.deptlist = deptlist;
	}

	public String getSelectedname() {
		return selectedname;
	}

	public void setSelectedname(String selectedname) {
		this.selectedname = selectedname;
	}

	public List<SysMessageModel> getList() {
		return list;
	}

	public void setList(List<SysMessageModel> list) {
		this.list = list;
	}

	public List<SysMessageModel> getTemList() {
		return temList;
	}

	public void setTemList(List<SysMessageModel> temList) {
		this.temList = temList;
	}

	public String[] getUploadFlie() {
		return uploadFlie;
	}

	public void setUploadFlie(String[] uploadFlie) {
		this.uploadFlie = uploadFlie;
	}

}
