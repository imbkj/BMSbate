package Controller.EmSheBaocard;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.zkoss.bind.annotation.BindingParam;
import org.zkoss.bind.annotation.Command;
import org.zkoss.bind.annotation.ContextParam;
import org.zkoss.bind.annotation.ContextType;
import org.zkoss.bind.annotation.NotifyChange;
import org.zkoss.util.media.Media;
import org.zkoss.zk.ui.event.UploadEvent;
import org.zkoss.zul.Filedownload;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Window;

import service.ExcelService;

import bll.EmSheBaocard.EmShebaoCardInfoSelectBll;

import Model.EmBodyCheckModel;
import Model.EmbaseModel;
import Util.DateStringChange;
import Util.FileOperate;
import Util.UserInfo;
import Util.plyUtil;

public class Emba_InfoOutController {
	private List<EmbaseModel> list = new ArrayList<EmbaseModel>();
	private List<EmbaseModel> filelist = new ArrayList<EmbaseModel>();
	private String filename = "";
	private InputStream inputStream = null;
	private Media media;
	private String filepath = "";

	@Command
	@NotifyChange({ "list", "filename" })
	public void uploadfile(@BindingParam("win") Window win,
			@ContextParam(ContextType.TRIGGER_EVENT) UploadEvent ev) {
		media = null;
		inputStream = null;
		media = ev.getMedia();
		if (media != null) {
			if (media.getFormat().equals("xls") || media.getFormat() == "xls"
					|| media.getFormat().equals("xlsx")
					|| media.getFormat() == "xlsx") {
				try {
					this.filename = media.getName();
					this.inputStream = media.getStreamData();
					loadList();
				} catch (Exception e) {
					System.out.println("错误:" + e.getMessage());
				}
			} else {
				Messagebox.show("选择的文件格式不正确", "提示", Messagebox.OK,
						Messagebox.ERROR);
			}
		}
	}

	private void loadList() {
		try {
			// 创建对Excel工作簿文件的引用
			HSSFWorkbook workbook = new HSSFWorkbook(inputStream);
			// 创建对工作表的引用。
			// 本例是按名引用（让我们假定那张表有着缺省名"Sheet1"）
			HSSFSheet sheet = workbook.getSheetAt(0);
			// 也可用getSheetAt(int index)按索引引用，
			// 在Excel文档中，第一张工作表的缺省索引是0，
			// 其语句为：HSSFSheet sheet = workbook.getSheetAt(0);
			// 读取左上端单元
			HSSFRow row;
			for (int i = sheet.getFirstRowNum(); i < sheet
					.getPhysicalNumberOfRows(); i++) {
				row = sheet.getRow(i);
				if (i > 0) {
					EmbaseModel m=new EmbaseModel();
					if (row.getCell(1) != null) {
						row.getCell(1).setCellType(Cell.CELL_TYPE_STRING);
						String idcard = row.getCell(1).getStringCellValue();
						m.setEmba_idcard(idcard.toString());	
					}
					if (row.getCell(0) != null) {
						row.getCell(0).setCellType(Cell.CELL_TYPE_STRING);
						String name = row.getCell(0).getStringCellValue();
						m.setEmba_name(name);
					}
					filelist.add(m);
				}
			}
		} catch (Exception e) {

		}
		if (list.size() > 0) {
			list.clear();
		}
		if (filelist.size() > 0) {
			for (EmbaseModel m : filelist) {
				EmShebaoCardInfoSelectBll bll = new EmShebaoCardInfoSelectBll();
				m=bll.getEmbaseInfoList(m);
				list.add(m);
			}
		}
	}

	@Command
	public void downExcel() throws IOException {
		if (list.size() > 0) {
			plyUtil ply = new plyUtil();
			String path = "/../../EmSheBaocard/downloadfile/";
			// 创建当前日子
			Date date = new Date();
			// 格式化日期
			SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmssSSS");
			// 格式化日期(产生文件名)
			String filename = "员工信息信息" + sdf.format(date) + ".xls";// 定义导出文件名称
			// 获取绝对路径
			path = ply.getAbsolutePath(path, filename, this);
			// System.out.println(path);
			// 创建文件
			File file = new File(path);
			file.createNewFile();
			String sheetName = "公积金卡领卡信息";// Excel表格名
			// 定义表头
			String[] title = { "序号", "公司编号", "公司名称", "员工姓名", "性别", "身份证号",
					"手机号码", "社保电脑号", "客服" };
			try {
				// 使用自己写的Excel导出实现类把数据写入Excel
				ExcelService exl = new Emba_InfoOutImpl(file, sheetName, title,
						list);
				exl.writeExcel();
			} catch (Exception e) {
				System.out.println(e.toString());
			}
			Filedownload.save(file, "xls");// 导出Excel
			// file.delete();
		}
	}

	// 下载模板
	@Command
	public void loadfile(@BindingParam("type") String type) {
		String filename = "outExcel.xls";
		String absolutePath = FileOperate.getAbsolutePath();
		String path = absolutePath + "EmSheBaocard/file/" + filename;
		File file = new File(path);
		try {
			Filedownload.save(file, "xlsx");
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public String getFilename() {
		return filename;
	}

	public void setFilename(String filename) {
		this.filename = filename;
	}

	public List<EmbaseModel> getList() {
		return list;
	}

	public void setList(List<EmbaseModel> list) {
		this.list = list;
	}

}
