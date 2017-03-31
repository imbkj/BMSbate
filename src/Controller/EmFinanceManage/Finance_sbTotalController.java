package Controller.EmFinanceManage;

import java.io.File;
import java.io.FileNotFoundException;
import java.text.SimpleDateFormat;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import org.zkoss.bind.BindContext;
import org.zkoss.bind.annotation.Command;
import org.zkoss.bind.annotation.ContextParam;
import org.zkoss.bind.annotation.ContextType;
import org.zkoss.bind.annotation.NotifyChange;
import org.zkoss.util.media.Media;
import org.zkoss.zk.ui.event.UploadEvent;
import org.zkoss.zul.Filedownload;
import org.zkoss.zul.ListModelList;
import org.zkoss.zul.Messagebox;

import Model.CoBaseModel;
import Model.sbTotalModel;
import Util.FileOperate;
import Util.UserInfo;
import bll.EmFinanceManage.sbTotalBll;

public class Finance_sbTotalController {

	private Media[] media;
	private String absolutePath;
	private String uploadFlieName;
	private String uploadfolder;
	private String downloadfolder;

	private String uploadState = "";
	private List<sbTotalModel> flist = new ListModelList<>();

	private sbTotalBll bll;

	public Finance_sbTotalController() {
		bll = new sbTotalBll();
		absolutePath = FileOperate.getAbsolutePath();
		uploadfolder = "/OfficeFile/UpLoad/sbTotal/";
		downloadfolder = "/OfficeFile/DownLoad/sbTotal/";
	}

	// 文件检查
	@Command("browse")
	@NotifyChange({ "uploadState", "flist" })
	public void browse(@ContextParam(ContextType.BIND_CONTEXT) BindContext ul) {
		uploadState = "";
		UploadEvent upEvent = (UploadEvent) ul.getTriggerEvent();
		media = upEvent.getMedias();
		flist.clear();
		for (int i = 0; i < media.length; i++) {

			if ("xls".equals(media[i].getFormat())) {
				uploadFlieName = media[i].getName();
				String uploadName = mosaicFileName();

				if (FileOperate.upload(media[i], uploadfolder + uploadName)) {
					uploadState = "上传成功";
					flist = bll.initData(absolutePath + uploadfolder
							+ uploadName, UserInfo.getUsername());
					if (flist.size() > 0) {
						for (sbTotalModel m : flist) {
							List<CoBaseModel> ulist = bll.getuid(m.getCid());
							if (ulist.size()>0) {
								m.setUid(ulist.get(0).getCoba_ufid());
								m.setCompacttype(ulist.get(0).getCompacttype());
							}
							
						}
						Collections.sort(flist);
					} else {
						uploadState = "数据读取失败";
					}
				} else {
					uploadState = "上传失败";
				}
			} else {
				Messagebox.show("文件格式不对,请确认文件后缀为xls", "操作提示", Messagebox.OK,
						Messagebox.ERROR);
			}
		}
	}

	@Command
	public void export() {
		if (flist.size() > 0) {

			try {
				Filedownload.save(
						new File(bll.export(UserInfo.getUsername(),absolutePath+
								downloadfolder + mosaicFileName(), flist)),
						null);
			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				Messagebox.show("文件生成失败.", "操作提示", Messagebox.OK,
						Messagebox.ERROR);
			}
		} else {
			Messagebox.show("请上传数据", "操作提示", Messagebox.OK, Messagebox.ERROR);
		}
	}

	// 拼接上传文件的名称
	private String mosaicFileName() {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
		Date date = new Date();
		String nowtime = sdf.format(date);
		String name = nowtime + uploadFlieName;
		return name;
	}

	public String getUploadState() {
		return uploadState;
	}

	public void setUploadState(String uploadState) {
		this.uploadState = uploadState;
	}

	public List<sbTotalModel> getFlist() {
		return flist;
	}

	public void setFlist(List<sbTotalModel> flist) {
		this.flist = flist;
	}

}
