package Controller.EmBodyCheck;

import java.io.InputStream;

import org.zkoss.bind.annotation.Command;
import org.zkoss.bind.annotation.ContextParam;
import org.zkoss.bind.annotation.ContextType;
import org.zkoss.bind.annotation.NotifyChange;
import org.zkoss.util.media.Media;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.event.UploadEvent;
import org.zkoss.zul.Messagebox;

import bll.EmBodyCheck.EmBcSetup_OperateBll;

import Model.EmBcSetupAddressModel;

public class Embc_AddressDocController {
	private EmBcSetupAddressModel model = (EmBcSetupAddressModel) Executions
			.getCurrent().getArg().get("model");
	private String filename = "";
	private Media media;
	private InputStream inputStream = null;

	@Command
	@NotifyChange("filename")
	public void upfile(@ContextParam(ContextType.TRIGGER_EVENT) UploadEvent ev) {
		media = ev.getMedia();
		filename = media.getName();
		String filefomat = media.getFormat();// 后缀
		if (!filefomat.equals("txt")) {
			this.inputStream = media.getStreamData();
		}
	}

	@Command
	public void summit() {
		if (media != null) {
			EmBcSetup_OperateBll bll=new EmBcSetup_OperateBll();
			Integer k=bll.UpdateEmBcSetupAddressFile(filename, model.getEbsa_id());
			if(k>0)
			{
				Messagebox.show("上传成功", "提示", Messagebox.OK, Messagebox.ERROR);
			}
			else
			{
				Messagebox.show("上传失败", "提示", Messagebox.OK, Messagebox.ERROR);
			}
		} else {
			Messagebox.show("请选择文件", "提示", Messagebox.OK, Messagebox.ERROR);
		}
	}

	public String getFilename() {
		return filename;
	}

	public void setFilename(String filename) {
		this.filename = filename;
	}

	public EmBcSetupAddressModel getModel() {
		return model;
	}

	public void setModel(EmBcSetupAddressModel model) {
		this.model = model;
	}
	
}
