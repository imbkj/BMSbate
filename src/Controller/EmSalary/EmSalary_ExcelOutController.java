package Controller.EmSalary;

import java.io.File;

import org.zkoss.bind.annotation.Command;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zul.Messagebox;

import Util.FileOperate;

public class EmSalary_ExcelOutController {
	//private String filename = Executions.getCurrent().getParameter("filename"); // 文件名
	private String filename = Executions.getCurrent().getArg().get("filename").toString();
	
	//下载
	@Command("download")
	public void download() {
		String path = "EmSalary/File/Download/TXTData/" + filename;

		try {
			File f = new File(path);

			FileOperate.download(path);

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
