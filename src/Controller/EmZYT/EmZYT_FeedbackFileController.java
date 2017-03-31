package Controller.EmZYT;


import java.util.List;

import org.zkoss.bind.annotation.BindingParam;
import org.zkoss.bind.annotation.Command;
import org.zkoss.bind.annotation.NotifyChange;

import org.zkoss.zul.Messagebox;

import Model.EmZYTFeedbackFileModel;
import Util.FileOperate;
import bll.EmZYT.EmZYT_SelectBll;

public class EmZYT_FeedbackFileController {
	private EmZYT_SelectBll sbll = new EmZYT_SelectBll();

	private List<EmZYTFeedbackFileModel> dataList;
	private String sql = "";
	private String filename = "";
	private String addname = "";

	public EmZYT_FeedbackFileController() {
		// 获取当月数据
		dataList = sbll.getEmZYTFeedbackFileList(sql);
	}

	// 查询数据
	@Command("search")
	@NotifyChange({ "dataList" })
	public void search() {
		if (!"".equals(filename)) {
			sql = sql + " AND ezff_filename LIKE '%" + filename + "%'";
		}
		if (!"".equals(addname)) {
			sql = sql + " AND ezff_addname='"+addname+"'";
		}

		dataList = sbll.getEmZYTFeedbackFileList(sql);
	}
	
	//下载
	@Command("download")
	public void download(@BindingParam("url") String url) {
		try {
			FileOperate.download(url);
		} catch (Exception e) {
			e.printStackTrace();
			Messagebox.show("模板下载出錯。", "操作提示", Messagebox.OK, Messagebox.ERROR);
		}
	}

	public List<EmZYTFeedbackFileModel> getDataList() {
		return dataList;
	}

	public void setDataList(List<EmZYTFeedbackFileModel> dataList) {
		this.dataList = dataList;
	}

	public String getFilename() {
		return filename;
	}

	public void setFilename(String filename) {
		this.filename = filename;
	}

	public String getAddname() {
		return addname;
	}

	public void setAddname(String addname) {
		this.addname = addname;
	}

}
