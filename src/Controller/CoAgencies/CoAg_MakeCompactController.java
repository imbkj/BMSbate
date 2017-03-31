package Controller.CoAgencies;

import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import org.zkoss.bind.annotation.BindingParam;
import org.zkoss.bind.annotation.Command;
import org.zkoss.bind.annotation.ContextParam;
import org.zkoss.bind.annotation.ContextType;
import org.zkoss.bind.annotation.NotifyChange;
import org.zkoss.util.media.Media;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.event.UploadEvent;
import org.zkoss.zk.ui.util.Clients;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Textbox;
import org.zkoss.zul.Window;

import Model.CoPolicyNoticeFileModel;
import Util.DateStringChange;
import Util.FileOperate;
import Util.UserInfo;

public class CoAg_MakeCompactController {
	private String userid = UserInfo.getUserid();
	private String filepath;
	private String filename = "";
	private InputStream inputStream = null;
	private Media media;
	private String id = (String) Executions.getCurrent().getArg().get("daid");
	private String tperid = (String) Executions.getCurrent().getArg().get("id");
	private String newfilename = "";

	@Command
	@NotifyChange("filepath")
	public void upload(@ContextParam(ContextType.TRIGGER_EVENT) UploadEvent ev,
			@BindingParam("filetxt") Textbox filetxt) {
		media = null;
		inputStream = null;
		media = ev.getMedia();
		if (media != null) {
			if (media.getFormat().equals("doc") || media.getFormat() == "doc"
					|| media.getFormat().equals("docx")
					|| media.getFormat() == "docx") {
				try {
					this.filename = filepath = media.getName();
					this.inputStream = media.getStreamData();
				} catch (Exception e) {
					System.out.println("错误:" + e.getMessage());
				}
			} else {
				media = null;
				Clients.showNotification("只能选择Word文档", "info", filetxt,
						"end_center", 3000);
			}
		}
	}

	// 提交上传文件
	@Command
	public void summit(@BindingParam("win") Window win,
			@BindingParam("filetxt") Textbox filetxt) {
		if (filename != null && !filename.equals("")) {
			Integer k = savefile();
			if (k > 0) {
				MakeCompactBean bll = new MakeCompactBean();
				Model.CoAgencyCompactModel model = bll.getModel(Integer
						.parseInt(id));
				int kl = bll.SaveComapctFilename(model, UserInfo.getUsername(),
						newfilename);
				if (kl > 0) {
					String[] str = bll.updateComapctFilename(model,
							UserInfo.getUsername());
					if (str[1] == "0") {
						win.detach();
						Messagebox.show("提交成功", "提示", Messagebox.OK,
								Messagebox.INFORMATION);
						
					} else {
						win.detach();
						Messagebox.show("上传失败", "提示", Messagebox.OK,
								Messagebox.ERROR);
					}
				} else {
					win.detach();
					Messagebox.show("保存失败", "提示", Messagebox.OK,
							Messagebox.ERROR);
				}
			} else {
				Clients.showNotification("请选择上传文件", "info", filetxt,
						"end_center", 3000);
			}
		} else {
			Clients.showNotification("请选择上传文件", "info", filetxt, "end_center",
					3000);
		}
	}

	// 保存文件到服务器方法
	private int savefile() {
		Integer k = 0;
		String RelativePath = "CoAgencies/file/savefile/";
		String realPath = FileOperate.getAbsolutePath() + RelativePath;
		if (media != null) {
			String name = "Coct"
					+ UserInfo.getUserid()
					+ DateStringChange
							.DatetoSting(new Date(), "yyyyMMddhhmmss") + "."
					+ media.getFormat();

			boolean flag = FileOperate.upload(media, RelativePath + name);
			if (flag) {
				k = k + 1;
				newfilename =name;
			}
		}

		return k;
	}

	public String getUserid() {
		return userid;
	}

	public void setUserid(String userid) {
		this.userid = userid;
	}

	public String getFilepath() {
		return filepath;
	}

	public void setFilepath(String filepath) {
		this.filepath = filepath;
	}

}
