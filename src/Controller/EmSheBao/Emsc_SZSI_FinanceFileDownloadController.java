package Controller.EmSheBao;

import java.util.ArrayList;
import java.util.List;

import org.zkoss.bind.annotation.BindingParam;
import org.zkoss.bind.annotation.Command;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zul.Messagebox;

import Model.EmShebaoSZSIFileModel;
import Util.FileOperate;
import bll.EmSheBao.EmSheBao_DSelectBll;

public class Emsc_SZSI_FinanceFileDownloadController {
	private EmSheBao_DSelectBll sbBll = new EmSheBao_DSelectBll();
	private List<EmShebaoSZSIFileModel> fileList = new ArrayList<EmShebaoSZSIFileModel>();
	private Integer cid = Integer.parseInt(Executions.getCurrent().getArg()
			.get("cid").toString());

	public Emsc_SZSI_FinanceFileDownloadController() {
		fileList = sbBll.downSZSI(String.valueOf(cid));
	}

	//下载
	@Command
	public void download(@BindingParam("url") String url){
		try {
			FileOperate.download(url);
		} catch (Exception e) {
			e.printStackTrace();
			Messagebox.show("下载出錯。", "操作提示", Messagebox.OK, Messagebox.ERROR);
		}
	}
	
	public List<EmShebaoSZSIFileModel> getFileList() {
		return fileList;
	}

	public void setFileList(List<EmShebaoSZSIFileModel> fileList) {
		this.fileList = fileList;
	}

}
