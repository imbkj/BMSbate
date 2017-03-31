package Controller.EmResidencePermit;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.zkoss.bind.annotation.BindingParam;
import org.zkoss.bind.annotation.Command;
import org.zkoss.bind.annotation.NotifyChange;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zul.Grid;
import org.zkoss.zul.Messagebox;

import service.ExcelService;

import bll.EmResidencePermit.Emrp_FeeSelectBll;

import Model.EmCAFFeeInfoModel;
import Model.EmResidencePermitInfoModel;
import Util.FileOperate;
import Util.plyUtil;

public class Emrp_FeeInfoController {
	private EmCAFFeeInfoModel model = (EmCAFFeeInfoModel) Executions
			.getCurrent().getArg().get("model");
	private Emrp_FeeSelectBll bll = new Emrp_FeeSelectBll();
	private List<EmResidencePermitInfoModel> list = bll
			.getFeeList(" and erpi_cl_number='" + model.getEcfi_cl_number()
					+ "'");

	// 生成支付明细excel
	@Command
	@NotifyChange("list")
	public void Export(@BindingParam("gd") Grid gd, HttpServletResponse response)
			throws Exception {
		plyUtil ply = new plyUtil();
		String path = "/../../EmResidencePermit/file/";
		String paths = "EmResidencePermit/downloadfile/";
		String absolutePath = FileOperate.getAbsolutePath();
		String filename = "qdmx.xls";
		// 创建当前日子
		Date date = new Date();
		// 格式化日期
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmssSSS");
		// 格式化日期(产生文件名)
		String newfilename = "qd居住证明细" + sdf.format(date) + ".xls";
		// 获取绝对路径
		String solpath = ply.getAbsolutePath(path, filename, this);// 获取模板路径
		// 创建文件
		// File file = new File(path);
		// file.createNewFile();
		try {
			File f = new File(absolutePath + paths + newfilename);
			if (f.isFile()) {
				f.delete();
			}
			ExcelService exl = new qdnewExcelImpl(solpath, absolutePath + paths
					+ newfilename, list, model.getEcfi_cl_number());
			exl.writeExcel();
		} catch (Exception e) {
			System.out.println(e.toString());
		}
		FileOperate.download(paths + newfilename);
	}

	public List<EmResidencePermitInfoModel> getList() {
		return list;
	}

	public void setList(List<EmResidencePermitInfoModel> list) {
		this.list = list;
	}

}
