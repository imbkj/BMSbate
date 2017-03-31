package Controller.EmZYT;


import org.zkoss.bind.annotation.Command;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zul.Messagebox;

import Util.FileOperate;

public class EmZYT_ExcelOutController {
	private String path = Executions.getCurrent().getArg().get("path")
			.toString(); // 路径
	private String filename = Executions.getCurrent().getArg().get("filename")
			.toString(); // 文件名

	// 下载
	@Command("download")
	public void download() {
		String allPath = path + filename;

		try {
			FileOperate.download(allPath);
		} catch (Exception e) {
			e.printStackTrace();
			Messagebox.show("模板下载出錯。", "操作提示", Messagebox.OK, Messagebox.ERROR);
		}
	}

	public String getFilename() {
		return filename;
	}

	public void setFilename(String filename) {
		this.filename = filename;
	}
}
