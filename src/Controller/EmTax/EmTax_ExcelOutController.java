package Controller.EmTax;

import java.io.File;

import org.zkoss.bind.annotation.Command;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zul.Messagebox;

import Util.FileOperate;

public class EmTax_ExcelOutController {
	private String path = Executions.getCurrent().getArg().get("path")
			.toString(); // 路径
	private String filename = Executions.getCurrent().getArg().get("filename")
			.toString(); // 员工明细文件名


	// 下载
	@Command("download")
	public void download() {
		String allPath = path + filename;

		try {
			File f = new File(allPath);

			FileOperate.download(allPath);

		} catch (Exception e) {
			e.printStackTrace();
			Messagebox.show("下载出錯。", "操作提示", Messagebox.OK, Messagebox.ERROR);
		}
	}


	public String getFilename() {
		return filename;
	}

	public void setFilename(String filename) {
		this.filename = filename;
	}



}
