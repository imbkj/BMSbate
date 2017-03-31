package bll.EmPay;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.zkoss.zul.Messagebox;

import Controller.EmSheBaocard.CunExcelImpl;
import Controller.EmSheBaocard.newExcelImpl;
import Model.EmPayModel;
import Util.FileOperate;
import Util.plyUtil;
import service.ExcelService;

public class OutPayExcelImpl implements ExcelService {

	private EmPayModel model;

	public OutPayExcelImpl(EmPayModel model) {
		model = model;
	}

	@Override
	public void writeExcel() throws Exception {

	}
	
	private void findExcel()
	{
		plyUtil ply = new plyUtil();
		String path = "/../../EmPay/file/";
		String paths = "EmPay/downfile/";
		String absolutePath = FileOperate.getAbsolutePath();
		String filename = "pay.xlsx";
		Date date = new Date();
		// 格式化日期
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmssSSS");
		// 格式化日期(产生文件名)
		String newfilename = "(" + sbcd_content + ")社保卡信息"
				+ sdf.format(date) + ".xls";
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
			if (sbcd_content != null && sbcd_content.equals("新增")) {
				ExcelService exl = new newExcelImpl(solpath, absolutePath
						+ paths + newfilename, checkedlist);
				exl.writeExcel();
			} else {
				ExcelService exl = new CunExcelImpl(solpath, absolutePath
						+ paths + newfilename, checkedlist);
				exl.writeExcel();
			}
		} catch (Exception e) {
			System.out.println(e.toString());
		}
		FileOperate.download(paths + newfilename);
	} else {
		Messagebox.show(str, "提示", Messagebox.OK, Messagebox.ERROR);
	}
	}

}
