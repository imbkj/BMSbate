package Controller.EmBodyCheck;

import java.io.InputStream;
import java.util.Date;

import org.zkoss.bind.annotation.BindingParam;
import org.zkoss.bind.annotation.Command;
import org.zkoss.bind.annotation.ContextParam;
import org.zkoss.bind.annotation.ContextType;
import org.zkoss.bind.annotation.NotifyChange;
import org.zkoss.util.media.Media;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.event.UploadEvent;
import org.zkoss.zul.Button;
import org.zkoss.zul.Label;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Window;

import bll.EmBodyCheck.EmBcFile_OperateBll;

import Model.EmbodyCheckFileModel;
import Util.DateStringChange;
import Util.FileOperate;
import Util.UserInfo;

public class Embc_FileEditController {
	private EmbodyCheckFileModel model = (EmbodyCheckFileModel) Executions
			.getCurrent().getArg().get("model");
	private Media media;
	private String filename = "";
	private InputStream inputStream = null;

	@Command
	@NotifyChange("filename")
	public void upfile(@ContextParam(ContextType.TRIGGER_EVENT) UploadEvent ev,
			@BindingParam("btn") Button btn, @BindingParam("bel") Label bel) {
		media = ev.getMedia();
		filename = media.getName();
		String filefomat = media.getFormat();// 后缀
		if (!filefomat.equals("txt")) {
			this.inputStream = media.getStreamData();
		}
		if (filename != null && !filename.equals("")
				&& !filefomat.equals("txt")) {
			bel.setVisible(true);
		} else {
			bel.setVisible(false);
		}
	}

	// 文件修改提交事件
	@Command
	public void summit(@BindingParam("win") Window win) {
		if (filename == null || filename.equals("")) {
			Messagebox.show("请选择文件", "提示", Messagebox.OK, Messagebox.ERROR);
		} else {
			String name = "note"
					+ UserInfo.getUserid()
					+ DateStringChange
							.DatetoSting(new Date(), "yyyyMMddhhmmss") + "."
					+ media.getFormat();
			String realPath = "EmBodyCheck/file/";
			String file_url = realPath + name;
			model.setFile_url(file_url);
			model.setFile_filename(filename);
			if(model.getFile_remark()==null)
			{
				model.setFile_remark("");
			}
			EmBcFile_OperateBll bll = new EmBcFile_OperateBll();
			if (media != null) {

				FileOperate.upload(media, file_url);
			}
			Integer k = bll.EmbodyCheckFileEdit(model);
			if (k > 0) {
				Messagebox.show("提交成功", "提示", Messagebox.OK,
						Messagebox.INFORMATION);
				win.detach();
			} else {
				Messagebox.show("提交失败", "提示", Messagebox.OK, Messagebox.ERROR);
			}
		}
	}

	public EmbodyCheckFileModel getModel() {
		return model;
	}

	public void setModel(EmbodyCheckFileModel model) {
		this.model = model;
	}

	public String getFilename() {
		return filename;
	}

	public void setFilename(String filename) {
		this.filename = filename;
	}

}
