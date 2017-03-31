package Controller.EmCommissionOut;

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
import Model.EmCommissionOutPayDifModel;
import service.ExcelService;

public class EmCommissionOutPayDifOutExcelImpl implements ExcelService{
	private File file;// excel文件对象
	private String sheetName;// sheet名称
	private String[] title;// 标题字符串
	private List<EmCommissionOutPayDifModel> list;
	
	public EmCommissionOutPayDifOutExcelImpl(File file, String sheetName,
			String[] title,
			List<EmCommissionOutPayDifModel> listEmCommissionOutPayDif) {
		this.title = title;
		this.file = file;
		this.sheetName = sheetName;
		this.list = listEmCommissionOutPayDif;
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
		// 开始写入第一行(即标题栏)
		for (int i = 0; i < title.length; i++) {// 写入自定义的表头

			// 用于写入文本内容到工作表中去
			Label label = null;
			// 在Label对象的构造中指明单元格位置(参数依次代表列数、行数、内容 )
			label = new Label(i, 0, title[i], wcf);
			// 将定义好的单元格添加到工作表中
			sheet.addCell(label);
		}
		int row=1;
		// 开始写入内容
		for (int i = 0; i < list.size(); i++){
			// 用model获取每一行数据
			EmCommissionOutPayDifModel m =list.get(i);
			if(Integer.parseInt(m.getEcod_id())>0){
				// 将每列数据写入工作表中
				Label label = null;
				label = new Label(0, row, row + "");// 第row行第0列
				sheet.addCell(label);
				label = new Label(1, row, m.getCity());// 第row行第2列
				sheet.addCell(label);
				label = new Label(2, row, m.getCoab_name());// 第row行第3列
				sheet.addCell(label);
				label = new Label(3, row, m.getCid());// 第row行第4列
				sheet.addCell(label);
				label = new Label(4, row, m.getCoba_company());// 第row行第4列
				sheet.addCell(label);
				label = new Label(5, row, m.getGid());// 第row行第4列
				sheet.addCell(label);
				label = new Label(6, row, m.getEcod_name());
				sheet.addCell(label);
				label = new Label(7, row, m.getEcod_idcard());
				sheet.addCell(label);
				label = new Label(8, row, m.getOwnmonth());
				sheet.addCell(label);
				label = new Label(9, row, m.getEcod_yl());
				sheet.addCell(label);
				label = new Label(10, row, m.getEcod_sye());
				sheet.addCell(label);
				label = new Label(11, row, m.getEcod_gs());
				sheet.addCell(label);
				label = new Label(12, row, m.getEcod_syu());
				sheet.addCell(label);
				label = new Label(13, row, m.getEcod_jl());
				sheet.addCell(label);
				label = new Label(14, row, m.getEcod_house());
				sheet.addCell(label);
				label = new Label(15, row, m.getEcod_other());
				sheet.addCell(label);
				label = new Label(16, row, m.getEcod_fuwu());
				sheet.addCell(label);
				label = new Label(17, row, m.getEcod_file());
				sheet.addCell(label);
				label = new Label(18, row, m.getEcod_total());
				sheet.addCell(label);
				label = new Label(19, row, m.getEcod_remark());
				sheet.addCell(label);
				label = new Label(20, row, m.getEcod_premark());
				sheet.addCell(label);
				
				label = new Label(21, row, m.getEcod_yl_st());
				sheet.addCell(label);
				label = new Label(22, row, m.getEcod_sye_st());
				sheet.addCell(label);
				label = new Label(23, row, m.getEcod_gs_st());
				sheet.addCell(label);
				label = new Label(24, row, m.getEcod_syu_st());
				sheet.addCell(label);
				label = new Label(25, row, m.getEcod_jl_st());
				sheet.addCell(label);
				label = new Label(26, row, m.getEcod_house_st());
				sheet.addCell(label);
				label = new Label(27, row, m.getEcod_other_st());
				sheet.addCell(label);
				label = new Label(28, row, m.getEcod_fuwu_st());
				sheet.addCell(label);
				label = new Label(29, row, m.getEcod_file_st());
				sheet.addCell(label);
				row++;
			}
		}
		// 写入数据
		wwb.write();
		// 关闭文件
		wwb.close();
		
	}

}
