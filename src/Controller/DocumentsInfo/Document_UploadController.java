package Controller.DocumentsInfo;

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

import org.zkoss.zk.ui.Executions;

import org.zkoss.zk.ui.event.UploadEvent;
import org.zkoss.zul.Combobox;
import org.zkoss.zul.Messagebox;


import Model.DocumentsInfoModel;
import Model.DocumentsInfoPubPicModel;
import bll.DocumentsInfo.DocCompany_UploadBll;
import Util.FileOperate;
import Util.UserInfo;

public class Document_UploadController {
	//private final int cid = Integer.parseInt(Executions.getCurrent().getArg()
	//		.get("cid").toString());
	 private final int cid = 1;
	private String filename;
	private List<DocumentsInfoModel> classList;
	private List<DocumentsInfoPubPicModel> picList;
	private DocCompany_UploadBll bll = new DocCompany_UploadBll();
	private Media media;

	public Document_UploadController() {
		setClassList();
		setPicList();
	}

	@Command("browse")
	@NotifyChange("filename")
	public void browse(@ContextParam(ContextType.BIND_CONTEXT) BindContext ul) {
		UploadEvent upEvent = (UploadEvent) ul.getTriggerEvent();
		this.media = upEvent.getMedia();
		if (media.getFormat().equals("txt")) {
			this.media = null;
			Messagebox.show("无法上传txt文件。", "操作提示", Messagebox.OK,
					Messagebox.NONE);

		} else {
			setFilename(media.getName());
		}
	}

	@Command("upload")
	@NotifyChange("picList")
	public void upload(@BindingParam("class") Combobox cb) throws Exception {

		int doin_id;
		try {
			doin_id = cb.getSelectedItem().getValue();
		} catch (Exception e) {
			Messagebox
					.show("请选择文件的类型。", "操作提示", Messagebox.OK, Messagebox.NONE);
			return;
		}
		try {
			if (this.media != null) {
				// 获取用户名
				String username = UserInfo.getUsername();
				// 编辑文件名
				String path = "DocumentsInfo/UploadFile/";

				SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
				Date date = new Date();
				String nowtime = sdf.format(date);
				String name = path + nowtime + doin_id + cid + "."
						+ media.getFormat();
				// 上传文件
				if (FileOperate.upload(media, name)) {
					if (bll.DocFileUpload(cid, 0, doin_id, name,
							FileOperate.getFileSize(media), username)) {
						Messagebox.show("文件上传成功。", "操作提示", Messagebox.OK,
								Messagebox.NONE);
						setPicList();
					}
				}
			} else {
				Messagebox.show("请选择需要上传的文件。", "操作提示", Messagebox.OK,
						Messagebox.NONE);
			}

		} catch (Exception e) {
			Messagebox.show("文件上传出錯。", "操作提示", Messagebox.OK, Messagebox.ERROR);
		}

	}

	@Command("download")
	public void download(@BindingParam("id") int id) throws Exception {
		try {
			// 文件名路径
			String fileName = bll.getPicUrl(id);
			FileOperate.download(fileName);
		} catch (Exception e) {
			Messagebox.show("文件下载出錯。", "操作提示", Messagebox.OK, Messagebox.ERROR);
		}
	}

	public String getFilename() {
		return filename;
	}

	public void setFilename(String filename) {
		this.filename = filename;
	}

	public List<DocumentsInfoModel> getClassList() {
		return classList;
	}

	public void setClassList() {
		try {
			classList = bll.getDocClassByType(2);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public List<DocumentsInfoPubPicModel> getPicList() {
		return picList;
	}

	public void setPicList() {
		try {
			picList = bll.getPicById(cid, 2);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
