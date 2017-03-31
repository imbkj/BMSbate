package impl;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;

import jxl.Workbook;
import jxl.format.Alignment;
import jxl.format.UnderlineStyle;
import jxl.format.VerticalAlignment;
import jxl.write.DateTime;
import jxl.write.Label;
import jxl.write.WritableCellFormat;
import jxl.write.WritableFont;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;

import Model.CoProductModel;
import service.ExcelService;

public class CpdExcelImpl implements ExcelService {

	private File file;// excel文件对象
	private String sheetName;// sheet名称
	private String[] title;// 标题字符串
	private List<CoProductModel> lists;// excel中的内容

	public CpdExcelImpl(File file, String sheetName, String[] title,
			List<CoProductModel> lists) {
		super();
		this.file = file;
		this.sheetName = sheetName;
		this.title = title;
		this.lists = lists;
	}

	@Override
	public void writeExcel() throws Exception {
		//读取Excel模板
		//Workbook wb = Workbook.getWorkbook(new File(realpath));
		
		// 创建可以写入的Excel工作薄(默认运行生成的文件在tomcat/bin下 )
		WritableWorkbook wwb = Workbook.createWorkbook(new FileOutputStream(
				file));
		/*使用模板创建
		WritableWorkbook wwb=Workbook.createWorkbook(new FileOutputStream(file), wb);*/
		
		// 生成工作表,(name:First Sheet,参数0表示这是第一页)
		WritableSheet sheet = wwb.createSheet(sheetName, 0);

		// 设置标题的文字格式
		WritableFont wf = new WritableFont(WritableFont.ARIAL, 12,
				WritableFont.BOLD, true, UnderlineStyle.NO_UNDERLINE);// 字体、粗体、斜体、下划线
		WritableCellFormat wcf = new WritableCellFormat(wf);
		wcf.setVerticalAlignment(VerticalAlignment.CENTRE);
		wcf.setAlignment(Alignment.CENTRE);

		// 行高设置行高,参数(行数,高度)
		sheet.setRowView(0, 500);

		// 手动设置列宽,参数(列数,宽度)
		sheet.setColumnView(0, 31);
		sheet.setColumnView(1, 12);
		sheet.setColumnView(2, 12);
		sheet.setColumnView(3, 12);
		sheet.setColumnView(4, 12);

		// 开始写入第一行(即标题栏)
		for (int i = 0; i < title.length; i++) {

			// 用于写入文本内容到工作表中去
			Label label = null;
			// 在Label对象的构造中指明单元格位置(参数依次代表列数、行数、内容 )
			label = new Label(i, 0, title[i], wcf);
			// 将定义好的单元格添加到工作表中
			sheet.addCell(label);
		}

		// 开始写入内容
		for (int row = 1; row < lists.size() + 1; row++) {
			// 用model获取每一行数据
			CoProductModel cop = lists.get(row - 1);
			// 将每列数据写入工作表中
			Label label = null;
			label = new Label(0, row, cop.getCopr_name());
			sheet.addCell(label);
			label = new Label(1, row, cop.getCopc_name());
			sheet.addCell(label);
			label = new Label(2, row, cop.getCpac_name());
			sheet.addCell(label);
			label = new Label(3, row, cop.getCopr_addname());
			sheet.addCell(label);
			DateTime dateTime = new DateTime(4, row, cop.getCopr_addtime());
			sheet.addCell(dateTime);
		}

		// 写入数据
		wwb.write();
		// 关闭文件
		wwb.close();
	}

	public File getFile() {
		return file;
	}

	public void setFile(File file) {
		this.file = file;
	}

	public String getSheetName() {
		return sheetName;
	}

	public void setSheetName(String sheetName) {
		this.sheetName = sheetName;
	}

	public String[] getTitle() {
		return title;
	}

	public void setTitle(String[] title) {
		this.title = title;
	}

	public List<CoProductModel> getLists() {
		return lists;
	}

	public void setLists(List<CoProductModel> lists) {
		this.lists = lists;
	}
}
