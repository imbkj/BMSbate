package Controller.EmReg;

import java.io.File;
import java.io.FileOutputStream;
import java.util.List;

import jxl.Workbook;
import jxl.format.Alignment;
import jxl.format.UnderlineStyle;
import jxl.format.VerticalAlignment;
import jxl.write.Label;
import jxl.write.WritableCellFormat;
import jxl.write.WritableFont;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;
import service.ExcelService;
import Model.EmHouseTakeCardInfoModel;
import Model.EmRegistrationInfoModel;

public class ExcelImpl implements ExcelService{
	private File file;// excel文件对象
	private String sheetName;// sheet名称
	private String[] title;// 标题字符串
	private List<EmRegistrationInfoModel> list;// excel中的内容
	public ExcelImpl(File file, String sheetName, String[] title,
			List<EmRegistrationInfoModel> list){
		// TODO Auto-generated method stub
		this.file = file;
		this.sheetName = sheetName;
		this.title = title;
		this.list = list;
	}

	@Override
	public void writeExcel() throws Exception {
		WritableWorkbook wwb = Workbook.createWorkbook(new FileOutputStream(
				file));
		WritableSheet sheet = wwb.createSheet(sheetName, 0);
		// 设置标题的文字格式
		WritableFont wf = new WritableFont(WritableFont.ARIAL, 10,
				WritableFont.NO_BOLD, false, UnderlineStyle.NO_UNDERLINE);// 字体、粗体、斜体、下划线
		WritableCellFormat wcf = new WritableCellFormat(wf);
		wcf.setVerticalAlignment(VerticalAlignment.CENTRE);
		wcf.setAlignment(Alignment.CENTRE);
		// 行高设置行高,参数(行数,高度)
		sheet.setRowView(0, 500);
		// 开始写入第一行(即标题栏)
		for (int i = 0; i < title.length; i++) {//写入自定义的表头

			// 用于写入文本内容到工作表中去
			Label label = null;
			// 在Label对象的构造中指明单元格位置(参数依次代表列数、行数、内容 )
			label = new Label(i, 0, title[i], wcf);
			// 将定义好的单元格添加到工作表中
			sheet.addCell(label);
		}
		// 开始写入内容
		for (int row = 1; row < list.size() + 1; row++) {
			// 用model获取每一行数据
			EmRegistrationInfoModel m = list.get(row - 1);
			// 将每列数据写入工作表中
			Label label = null;
			label = new Label(0, row, row+"");//第row行第0列
			sheet.addCell(label);
			label = new Label(1, row, m.getCid()+"");//第row行第1列
			sheet.addCell(label);
			label = new Label(2, row, m.getCoba_shortname());//第row行第2列
			sheet.addCell(label);
			label = new Label(3, row, m.getGid()+"");
			sheet.addCell(label);
			label = new Label(4, row, m.getEmba_name());
			sheet.addCell(label);
			label = new Label(5, row, m.getErin_handover_people());
			sheet.addCell(label);
			label = new Label(6, row, m.getErin_hjtype());
			sheet.addCell(label);
			label = new Label(7, row, m.getErin_stop_reason());
			sheet.addCell(label);
			label = new Label(8, row, m.getErin_stop_date());
			sheet.addCell(label);
			label = new Label(9, row, m.getErin_stop_people());
			sheet.addCell(label);
			label = new Label(10, row, m.getErin_p_stop_date());
			sheet.addCell(label);
			label = new Label(11, row, m.getStop_statename());
			sheet.addCell(label);
		}
		// 写入数据
		wwb.write();
		// 关闭文件
		wwb.close();
		
	}

}
