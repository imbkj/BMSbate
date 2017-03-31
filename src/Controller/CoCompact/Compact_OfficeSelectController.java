package Controller.CoCompact;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.zkoss.bind.annotation.BindingParam;
import org.zkoss.bind.annotation.Command;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zul.Window;

import com.sun.org.apache.xpath.internal.Arg;

import Model.DocumentsInfoPubPicModel;
import Model.OfficeModel;
import Util.FileOperate;
import bll.CoCompact.Compact_OfficeSelectBll;

public class Compact_OfficeSelectController {
	private List officeList; // 合同模板列表
	private Compact_OfficeSelectBll officeBll = new Compact_OfficeSelectBll();
	private List<DocumentsInfoPubPicModel> outcont;
	private String conname;
	private String savename;
	
	
	public Compact_OfficeSelectController() throws Exception {
		int coco_id;//表id
		int sc;//表类型
		int puf_id;//表类型
		coco_id=Integer.parseInt(Executions.getCurrent().getArg().get("daid").toString());
		//sc=Integer.parseInt(Executions.getCurrent().getArg().get("sc").toString());
		//puf_id=Integer.parseInt(Executions.getCurrent().getArg().get("puf_id").toString());
		puf_id=0;
		sc=2;
		outcont = officeBll.getoutcont(Executions.getCurrent().getArg().get("daid").toString());
		
		setConname(outcont.get(0).getDoin_content());
		
		setSavename(outcont.get(0).getPupi_ip());
		
		// 首页加载显示所有数据
		officeList = officeBll.getofficeFile(coco_id,sc,puf_id);
	}
	
	//下载合同
		@Command("compact_down")
		public void compact_add(@BindingParam("coabM") OfficeModel coabM) {
			//System.out.print(coabM.getPuof_url());
			//Executions.sendRedirect("http://localhost:8080/BMS/"+coabM.getPuof_url());
			FileOperate.download("/OfficeFile/DownLoad/CoCompact/"
					+ coabM.getPuof_url());
		}
	
	public List getofficeList() {
		return officeList;
	}

	public void setofficeList(List officeList) {
		this.officeList = officeList;
	}

	public String getConname() {
		return conname;
	}

	public void setConname(String conname) {
		this.conname = conname;
	}

	public String getSavename() {
		return savename;
	}

	public void setSavename(String savename) {
		this.savename = savename;
	}
	
	
}
