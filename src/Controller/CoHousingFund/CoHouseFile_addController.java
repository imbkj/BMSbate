package Controller.CoHousingFund;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.zkoss.bind.BindContext;
import org.zkoss.bind.BindUtils;
import org.zkoss.bind.annotation.Command;
import org.zkoss.bind.annotation.ContextParam;
import org.zkoss.bind.annotation.ContextType;
import org.zkoss.util.media.Media;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.Path;
import org.zkoss.zk.ui.event.UploadEvent;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Window;

import bll.CoHousingFund.CoHouseFileBll;

import Model.CoHousingFundFileModel;
import Util.FileOperate;
import Util.UserInfo;

public class CoHouseFile_addController {
	private CoHousingFundFileModel cfm;
	private List<Integer> sortlist = new ArrayList<>();

	private Media media;
	private String uploadFlieName;

	private CoHouseFileBll bll = new CoHouseFileBll();

	private Window w = (Window) Path.getComponent("/winF");

	public CoHouseFile_addController() {

		if (Executions.getCurrent().getArg().get("m") != null) {
			cfm = (CoHousingFundFileModel) Executions.getCurrent().getArg()
					.get("m");
			// cfm.setChff_pid(id);
			cfm.setChff_addname(UserInfo.getUsername());
			Integer j = bll.getMaxSort(cfm.getChff_pid());
			for (int i = 1; i <= j; i++) {
				sortlist.add(i);
			}
			cfm.setChff_sort(j);

		}

	}

	@Command
	public void browse(@ContextParam(ContextType.BIND_CONTEXT) BindContext ul) {
		UploadEvent upEvent = (UploadEvent) ul.getTriggerEvent();
		media = upEvent.getMedia();
		System.out.println(media.getFormat());
		if ("xls".equals(media.getFormat()) || "xlsx".equals(media.getFormat())
				|| "doc".equals(media.getFormat())
				|| "docx".equals(media.getFormat())) {
			uploadFlieName = media.getName();
			String uploadfolder = "/OfficeFile/UpLoad/CoHousingFund/workbook/";
			String uploadName = mosaicFileName();
			if (FileOperate.upload(media, uploadfolder + uploadName)) {
				cfm.setUploadTips("上传成功.");
				cfm.setChff_url(uploadfolder + uploadName);
				
				cfm.setChff_iffile(1);

			} else {
				cfm.setUploadTips("上传失败.");
				cfm.setChff_url(null);
			}
			BindUtils.postNotifyChange(null, null, cfm, "uploadTips");
		} else {
			Messagebox
					.show("文件格式不支持", "ERROR", Messagebox.OK, Messagebox.ERROR);
		}
	}

	@Command
	public void add() {
		if (cfm.getChff_name() == null || cfm.getChff_name().equals("")) {
			Messagebox.show("请输入名称", "ERROR", Messagebox.OK, Messagebox.ERROR);
		}

		/*
		 * if (cfm.getChff_url() == null || cfm.getChff_url().equals("")) {
		 * Messagebox.show("请上传文件", "ERROR", Messagebox.OK, Messagebox.ERROR); }
		 */
		if (cfm.getChff_iffile() == null) {
			cfm.setChff_iffile(0);
		}
		Integer i = bll.add(cfm);
		if (i > 0) {
			cfm.setUploadState(true);
			Messagebox.show("添加成功.", "ERROR", Messagebox.OK,
					Messagebox.INFORMATION);
			w.detach();
		} else {
			Messagebox.show("添加失败", "ERROR", Messagebox.OK, Messagebox.ERROR);
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

	public CoHousingFundFileModel getCfm() {
		return cfm;
	}

	public void setCfm(CoHousingFundFileModel cfm) {
		this.cfm = cfm;
	}

	public List<Integer> getSortlist() {
		return sortlist;
	}

	public void setSortlist(List<Integer> sortlist) {
		this.sortlist = sortlist;
	}

}
