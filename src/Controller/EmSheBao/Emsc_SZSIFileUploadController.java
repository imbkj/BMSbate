package Controller.EmSheBao;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.zkoss.bind.BindContext;
import org.zkoss.bind.annotation.BindingParam;
import org.zkoss.bind.annotation.Command;
import org.zkoss.bind.annotation.ContextParam;
import org.zkoss.bind.annotation.ContextType;
import org.zkoss.bind.annotation.NotifyChange;
import org.zkoss.util.media.Media;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.event.EventListener;
import org.zkoss.zk.ui.event.UploadEvent;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Window;
import org.zkoss.zul.Messagebox.ClickEvent;

import Model.EmShebaoChangeSZSIModel;
import Util.FileOperate;
import bll.EmSheBao.EmSheBao_DOperateBll;
import bll.EmSheBao.EmSheBao_DSelectBll;

public class Emsc_SZSIFileUploadController {
	private EmSheBao_DSelectBll dsbll = new EmSheBao_DSelectBll();
	private EmSheBao_DOperateBll dobll = new EmSheBao_DOperateBll();
	private EmShebaoChangeSZSIModel sbData;
	private String id = Executions.getCurrent().getArg().get("daid").toString();

	private String absolutePath;
	private Media media;
	private String uploadFlieName;
	private String filetype;

	public Emsc_SZSIFileUploadController() {
		// 获取页面数据
		sbData = dsbll.getEmSCSZSIData(" AND escs_id=" + id, "").get(0);
	}

	// 浏览文件
	@Command("browse")
	@NotifyChange("uploadFlieName")
	public void browse(@ContextParam(ContextType.BIND_CONTEXT) BindContext ul) {
		UploadEvent upEvent = (UploadEvent) ul.getTriggerEvent();
		this.media = upEvent.getMedia();
		uploadFlieName = media.getName();
		filetype = media.getFormat();
	}

	// 提交
	@Command("submit")
	public void submit(@BindingParam("win") final Window win,
			@ContextParam(ContextType.VIEW) Component view) {
		if (this.media != null) {
			absolutePath = FileOperate.getAbsolutePath();
			String uploadfolder = "EmSheBao/File/Upload/Declare/";
			String uploadName = mosaicFileName();

			// 上传文件至服务器
			if (FileOperate.upload(media, uploadfolder + uploadName)) {
				// 更新数据
				String[] message;
				message = dobll.declareSZSIUpload(Integer.parseInt(id),
						sbData.getEmsc_tapr_id(), uploadName, sbData.getCid());

				// 判断是否成功
				if (message[0].equals("1")) {
					EventListener<ClickEvent> clickListener = new EventListener<Messagebox.ClickEvent>() {
						public void onEvent(ClickEvent event) throws Exception {
							if (Messagebox.Button.OK.equals(event.getButton())) {
								win.detach();
							}
						}
					};
					// 弹出提示
					Messagebox.show(message[1], "操作提示",
							new Messagebox.Button[] { Messagebox.Button.OK },
							Messagebox.INFORMATION, clickListener);
				} else {
					// 弹出提示
					Messagebox.show(message[1], "操作提示", Messagebox.OK,
							Messagebox.ERROR);

				}
			} else {
				Messagebox.show("文件上传出错。", "操作提示", Messagebox.OK,
						Messagebox.ERROR);
			}
		} else {
			Messagebox.show("请选择需要上传的文件。", "操作提示", Messagebox.OK,
					Messagebox.ERROR);
		}
	}

	// 拼接上传文件的名称
	private String mosaicFileName() {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
		Date date = new Date();
		String nowtime = sdf.format(date);
		String name = nowtime +"."+ filetype;
		return name;
	}

	public EmShebaoChangeSZSIModel getSbData() {
		return sbData;
	}

	public void setSbData(EmShebaoChangeSZSIModel sbData) {
		this.sbData = sbData;
	}

	public String getUploadFlieName() {
		return uploadFlieName;
	}

	public void setUploadFlieName(String uploadFlieName) {
		this.uploadFlieName = uploadFlieName;
	}

}
