package Controller.Archives;

import org.zkoss.zk.ui.Executions;

import Util.FileOperate;

public class Emarchive_DownFileController {
	private String fm = Executions.getCurrent().getArg().get("filename")
			.toString();

	private String path;

	public Emarchive_DownFileController() {
		
	}

	public String getPath() {
		return path;
	}

	public void setPath(String path) {
		this.path = path;
	}

	public String getFm() {
		return fm;
	}

	public void setFm(String fm) {
		this.fm = fm;
	}

}
