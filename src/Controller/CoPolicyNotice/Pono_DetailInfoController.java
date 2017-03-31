package Controller.CoPolicyNotice;

import java.util.List;

import org.zkoss.bind.annotation.BindingParam;
import org.zkoss.bind.annotation.Command;
import org.zkoss.zk.ui.Executions;

import Model.CoPolicyNoticeFileModel;
import Model.CoPolicyNoticeModel;
import Util.FileOperate;

public class Pono_DetailInfoController {
	private CoPolicyNoticeModel model = (CoPolicyNoticeModel) Executions.getCurrent().getArg()
			.get("model");
	private List<CoPolicyNoticeFileModel> filelist=model.getFilelist();
	
	//下载
	@Command
	public void downloadfile(@BindingParam("url") String url)
	{
		try{
			FileOperate file=new FileOperate();
			file.download(url);
		}
		catch(Exception e)
		{
			e.printStackTrace();
			System.out.println("e="+e.getMessage());
		}
	}
	
	public List<CoPolicyNoticeFileModel> getFilelist() {
		return filelist;
	}
	public void setFilelist(List<CoPolicyNoticeFileModel> filelist) {
		this.filelist = filelist;
	}
	
}
