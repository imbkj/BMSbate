package Controller.CoServicePolicy;

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
import Model.CoAgencyBaseCityRelModel;
import Model.CoServicePolicyTitleModel;
import Model.CoServicePolicyTypeModel;
import service.ExcelService;

public class ExcelImpl implements ExcelService {
	private File file;// excel文件对象
	private String sheetName;// sheet名称
	private String[] title;// 标题字符串
	private List<CoServicePolicyTypeModel> bclist;// excel中的内容
	private CoAgencyBaseCityRelModel coagmodel;

	public ExcelImpl(File file, String sheetName, String[] title,
			List<CoServicePolicyTypeModel> lists,
			CoAgencyBaseCityRelModel coagmodel) {
		// TODO Auto-generated method stub
		this.file = file;
		this.sheetName = sheetName;
		this.title = title;
		this.bclist = lists;
		this.coagmodel = coagmodel;
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

		// 用于写入文本内容到工作表中去
		Label labeltype = null;
		// 在Label对象的构造中指明单元格位置(参数依次代表列数、行数、内容 )
		labeltype = new Label(0, 0, "编号", wcf);
		sheet.addCell(labeltype);
		labeltype = new Label(1, 0,coagmodel.getCabc_id().toString(), wcf);
		sheet.addCell(labeltype);
		labeltype = new Label(3, 0, "机构名称："+ coagmodel.getCoab_name());
		sheet.addCell(labeltype);
		labeltype = new Label(4, 0, "服务城市：" + coagmodel.getCity());
		// 将定义好的单元格添加到工作表中
		sheet.addCell(labeltype);

		// 开始写入第一行(即标题栏)
		for (int i = 0; i < title.length; i++) {// 写入自定义的表头

			// 用于写入文本内容到工作表中去
			Label label = null;
			// 在Label对象的构造中指明单元格位置(参数依次代表列数、行数、内容 )
			label = new Label(i, 2, title[i], wcf);
			// 将定义好的单元格添加到工作表中
			sheet.addCell(label);
		}

		// 开始写入内容
		Integer rownum = 3;
		for (int row = 1; row < bclist.size() + 1; row++) {
			CoServicePolicyTypeModel cop = bclist.get(row - 1);
			Integer nns = rownum + cop.getTitlelist().size() - 1;
			sheet.mergeCells(0, rownum, 0, nns);
			sheet.mergeCells(1, rownum, 0, nns);
			// 将每列数据写入工作表中
			Label label = null;

			label = new Label(0, rownum, cop.getNote_id().toString());// 第rownum行第0列————写入类型
			sheet.setColumnView(0, 10);
			sheet.setColumnView(1, 30);
			sheet.setColumnView(2, 10);
			sheet.setColumnView(3, 40);
			sheet.setColumnView(4, 500);
			WritableCellFormat cellFormat = new WritableCellFormat();
			cellFormat.setAlignment(jxl.format.Alignment.LEFT);
			cellFormat
					.setVerticalAlignment(jxl.format.VerticalAlignment.CENTRE);
			cellFormat.setWrap(true);
			label.setCellFormat(cellFormat);
			sheet.addCell(label);

			label = new Label(1, rownum, cop.getNote_type());// 第rownum行第0列————写入类型
			label.setCellFormat(cellFormat);
			sheet.addCell(label);
			for (CoServicePolicyTitleModel m : cop.getTitlelist()) {
				label = new Label(2, rownum, m.getItem_id().toString());// 第rownum行第1列
				sheet.addCell(label);
				label = new Label(3, rownum, m.getItem_title());// 第rownum行第1列
				sheet.addCell(label);
				label = new Label(4, rownum, m.getItem_content());// 第rownum行第1列
				sheet.addCell(label);
				rownum = rownum + 1;
			}
		}
		// 写入数据
		wwb.write();
		// 关闭文件
		wwb.close();

	}
}
