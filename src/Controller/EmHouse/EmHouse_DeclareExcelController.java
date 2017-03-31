package Controller.EmHouse;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.List;

import org.zkoss.bind.annotation.Command;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zul.Filedownload;
import org.zkoss.zul.ListModelList;
import org.zkoss.zul.Messagebox;
import bll.EmHouse.EmHouse_DeclareExcelBll;
import Model.EmHouseChangeModel;
import Util.FileOperate;

public class EmHouse_DeclareExcelController {
	private List<EmHouseChangeModel> eclist = new ListModelList<>();
	private EmHouse_DeclareExcelBll bll = new EmHouse_DeclareExcelBll();
	private String id = Executions.getCurrent().getArg().get("id").toString();
	private String className = Executions.getCurrent().getArg()
			.get("className").toString();
	private String exfileName = Executions.getCurrent().getArg()
			.get("exfileName").toString();

	private String companyid;
	private String clientid;

	public EmHouse_DeclareExcelController() {
		setEclist(id);
		exportExcel();
	}

	public void exportExcel() {
		String fileName = "";
		if (className.equals("New")) {
			fileName = "EmHouseNew.xls";
		} else if (className.equals("Transfer")) {
			fileName = "EmHouseTransfer.xls";
		} else if (className.equals("Open")) {
			fileName = "EmHouseOpen.xls";
		} else if (className.equals("Stop")) {
			fileName = "EmHouseStop.xls";
		} else if (className.equals("Salay")) {
			fileName = "EmHouseSalay.xls";
		}
		
		if (!bll.createExportExcel(className, fileName, exfileName, eclist)) {
			Messagebox.show("导出数据异常", "提示", Messagebox.OK, Messagebox.ERROR);
		}
	}
	
	@Command
	public void download(){
		String absolutePath = FileOperate.getAbsolutePath();
		try {
			Filedownload.save(new File(absolutePath
					+ "OfficeFile/DownLoad/Emhouse/" + exfileName),null);
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public String getCompanyid() {
		return companyid;
	}

	public void setCompanyid(String companyid) {
		this.companyid = companyid;
	}

	public String getClientid() {
		return clientid;
	}

	public void setClientid(String clientid) {
		this.clientid = clientid;
	}

	public List<EmHouseChangeModel> getEclist() {
		return eclist;
	}

	public void setEclist(String idlist) {
		this.eclist = bll.getListInfo(idlist);
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getClassName() {
		return className;
	}

	public void setClassName(String className) {
		this.className = className;
	}

	public String getExfileName() {
		return exfileName;
	}

	public void setExfileName(String exfileName) {
		this.exfileName = exfileName;
	}

}
