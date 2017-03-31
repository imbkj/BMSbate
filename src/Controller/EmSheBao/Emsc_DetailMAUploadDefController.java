package Controller.EmSheBao;

import impl.MessageImpl;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.zkoss.bind.BindContext;
import org.zkoss.bind.annotation.BindingParam;
import org.zkoss.bind.annotation.Command;
import org.zkoss.bind.annotation.ContextParam;
import org.zkoss.bind.annotation.ContextType;
import org.zkoss.bind.annotation.NotifyChange;
import org.zkoss.util.media.Media;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.Path;

import org.zkoss.zk.ui.event.UploadEvent;

import org.zkoss.zul.Grid;

import org.zkoss.zul.Messagebox;

import org.zkoss.zul.Window;

import service.MessageService;

import bll.LoginBll;
import bll.EmSheBao.EmSheBao_DSelectBll;
import bll.EmSheBao.Emsc_DeclareOperateBll;
import bll.SystemControl.SystLogInfoBll;

import Model.EmShebaoChangeMAModel;
import Model.LoginModel;
import Model.SysMessageModel;
import Util.FileOperate;
import Util.UserInfo;

public class Emsc_DetailMAUploadDefController {
	private String absolutePath = FileOperate.getAbsolutePath();
	private String uploadfolder = "EmSheBao/File/Upload/Declare/";
	private Media[] media;
	private String uploadFlieName;
	private String uploadFlie;// 浏览后页面显示的文件名
	private MessageService msgservice;
	private LoginBll loginBll=new LoginBll();
	
	private List<EmShebaoChangeMAModel> maList = (List<EmShebaoChangeMAModel>) Executions
			.getCurrent().getArg().get("list");

	private Window win = (Window) Path.getComponent("/winMAUpload");

	// 提交
	@Command("submit")
	public void submit(@BindingParam("win") Window win,
			@ContextParam(ContextType.VIEW) Component view) {
		String log = "";// 系统日志
		int k = 0;
		try {
			if (this.media != null) {
				// 文件上传服务器
				if (uploadFile(this.media[0], uploadFlieName)) {
					// 更新数据库
					log = log + "上传社保生育津贴决定书：" + uploadFlieName;
				} else {
					Messagebox.show("决定书上传出错。", "操作提示", Messagebox.OK,
							Messagebox.ERROR);
					return;
				}

				if (maList.size() > 0) {
					EmShebaoChangeMAModel maModel;

					for (int i = 0; i < maList.size(); i++) {
						maModel = null;
						maModel = new EmShebaoChangeMAModel();

						maModel = maList.get(i);

						// 已申报数据才能上传决定书
						if (maModel.getEscm_ifdeclare() == 1) {
							
							maModel.setEscm_def_filename(uploadFlieName);
							// 更新数据
							Emsc_DeclareOperateBll opbll = new Emsc_DeclareOperateBll();
							String[] message = opbll.ChangeMAUploadDef(maModel);

							if ("1".equals(message[0])) {
								//提醒系统短信和邮件客服
								EmSheBao_DSelectBll dsbll = new EmSheBao_DSelectBll();
									SysMessageModel model = null;
									model = new SysMessageModel();
									
									model.setSyme_content(maModel.getEscm_company()
											+ " 的 " + maModel.getEscm_name()
											+ " 社保生育津贴申请决定书已上传，请知悉。");// 短信内容
									model.setSyme_log_id(Integer.parseInt(UserInfo
											.getUserid()));// 发件人id
									model.setSymr_name(maModel.getEscm_client());// 收件人姓名
									model.setSymr_log_id(loginBll.getUserIDByname(maModel.getEscm_client()));
									model.setEmail(1);// 1表示同时发邮件
									model.setSyme_title(maModel.getEscm_company()
											+ " 的 " + maModel.getEscm_name()
											+ " 社保生育津贴申请决定书已上传");
									// 调用方法
									msgservice = new MessageImpl("EmShebaoChangeMA", maModel.getId());
									msgservice.Add(model);
								
							
								k = k + 1;
								if (!"".equals(log)) {
									SystLogInfoBll logBll = new SystLogInfoBll();
									logBll.addLog(null, maModel.getId(),
											maModel.getCid(), maModel.getGid(),
											"社保信息", log, UserInfo.getUsername());
								}
							}

						}

					}

					// 弹出提示
					if (maList.size() == k) {
						Messagebox.show("操作成功", "操作提示", Messagebox.OK,
								Messagebox.NONE);
					} else {
						Messagebox.show("部分数据操作不成功，请检查！", "操作提示",
								Messagebox.OK, Messagebox.NONE);
					}
					// 关闭窗口
					win.detach();
				} else {
					Messagebox.show("请选择要上传的文件", "操作提示", Messagebox.OK,
							Messagebox.ERROR);
				}
			} else {
				Messagebox.show("请选择需要上传的文件。", "操作提示", Messagebox.OK,
						Messagebox.NONE);
			}
		} catch (Exception e) {
			e.printStackTrace();
			Messagebox.show("文件上传出錯。", "操作提示", Messagebox.OK, Messagebox.ERROR);
		}
	}

	// 文件检查
	@Command("browse")
	@NotifyChange("uploadFlie")
	public void browse(@ContextParam(ContextType.BIND_CONTEXT) BindContext ul) {
		UploadEvent upEvent = (UploadEvent) ul.getTriggerEvent();
		media = upEvent.getMedias();
		for (int i = 0; i < media.length; i++) {

			if ("pdf".equals(media[i].getFormat())) {
				uploadFlie = media[i].getName();
				uploadFlieName = mosaicFileName();
			} else {
				media = null;
				uploadFlieName = "";
				Messagebox.show("上传的文件格式有误。", "操作提示", Messagebox.OK,
						Messagebox.NONE);
				return;
			}

		}
	}

	// 拼接上传文件的名称
	private String mosaicFileName() {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
		Date date = new Date();
		String nowtime = sdf.format(date);
		String name = "ma_def_" + nowtime + ".pdf";
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

	public String getUploadFlie() {
		return uploadFlie;
	}

}
