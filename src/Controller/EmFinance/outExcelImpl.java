package Controller.EmFinance;

import java.io.File;
import java.io.FileOutputStream;
import java.math.BigDecimal;
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
import bll.EmHouseCard.EmHouse_TakeCardInfoSelectBll;

import Model.CoFinanceWtModel;
import Model.CoHousingFundModel;
import Model.EmFinanceWtModel;
import Model.EmHouseTakeCardInfoModel;
import service.ExcelService;

public class outExcelImpl implements ExcelService {
	private File file;// excel文件对象
	private String sheetName;// sheet名称
	private String[] title;// 标题字符串
	private List<CoFinanceWtModel> list;

	public outExcelImpl(File file, String sheetName, String[] title,
			List<CoFinanceWtModel> list) {
		this.title = title;
		this.file = file;
		this.sheetName = sheetName;
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
		for (int i = 0; i < list.size(); i++) {
			// 用model获取每一行数据
			CoFinanceWtModel cm = list.get(i);
			for (EmFinanceWtModel m : cm.getEfWtList()) {
				if (m.getGid() > 0) {
					// 将每列数据写入工作表中
					Label label = null;
					label = new Label(0, row, row + "");// 第row行第0列
					sheet.addCell(label);
					label = new Label(1, row, cm.getCoab_name());// 第row行第1列
					sheet.addCell(label);
					label = new Label(2, row, cm.getCid() + "");// 第row行第2列
					sheet.addCell(label);
					label = new Label(3, row, cm.getCompany());// 第row行第3列
					sheet.addCell(label);
					label = new Label(4, row, cm.getCoba_client());
					sheet.addCell(label);

					label = new Label(5, row, m.getGid() + "");
					sheet.addCell(label);
					label = new Label(6, row, m.getEmfi_name());
					sheet.addCell(label);
					label = new Label(7, row, m.getEmfi_idcard());
					sheet.addCell(label);

					// 系统合计
					BigDecimal emfi_total = BigDecimal.ZERO;
					emfi_total = m.getEmfi_total().add(m.getEmfi_n_total());
					label = new Label(8, row, emfi_total.toString());
					sheet.addCell(label);
					
					//智翼通合计
					BigDecimal emfz_total = BigDecimal.ZERO;
					emfz_total = m.getEmfz_total().add(m.getEmfz_other());
					emfz_total=emfz_total.add(m.getEmfz_fee2());
					label = new Label(9, row, emfz_total.toString());
					sheet.addCell(label);

					BigDecimal etotal = BigDecimal.ZERO;
					etotal = emfi_total.subtract(emfz_total);
					label = new Label(10, row, etotal.toString());
					sheet.addCell(label);

					label = new Label(11, row, m.getEmfi_sbtotal().toString());
					sheet.addCell(label);
					label = new Label(12, row, m.getEmfz_sbtotal().toString());
					sheet.addCell(label);
					// 社保合计
					BigDecimal sbtotal = BigDecimal.ZERO;
					sbtotal = m.getEmfi_sbtotal().subtract(m.getEmfz_sbtotal());
					label = new Label(13, row, sbtotal.toString());
					sheet.addCell(label);

					label = new Label(14, row, m.getEmfi_yltotal().toString());
					sheet.addCell(label);
					label = new Label(15, row, m.getEmfz_yltotal().toString());
					sheet.addCell(label);
					// 养老合计
					BigDecimal yltotal = BigDecimal.ZERO;
					yltotal = m.getEmfi_yltotal().subtract(m.getEmfz_yltotal());
					label = new Label(16, row, yltotal.toString());
					sheet.addCell(label);
					
					//失业
					label = new Label(17, row, m.getEfsb_sye().toString());
					sheet.addCell(label);
					label = new Label(18, row, m.getEmfz_syetotal().toString());
					sheet.addCell(label);
					BigDecimal syetotal = BigDecimal.ZERO;
					syetotal = m.getEfsb_sye().subtract(m.getEmfz_syetotal());
					label = new Label(19, row, syetotal.toString());
					sheet.addCell(label);
					
					//工伤
					label = new Label(20, row, m.getEfsb_gs().toString());
					sheet.addCell(label);
					label = new Label(21, row, m.getEmfz_gstotal().toString());
					sheet.addCell(label);
					BigDecimal gstotal = BigDecimal.ZERO;
					gstotal = m.getEfsb_gs().subtract(m.getEmfz_gstotal());
					label = new Label(22, row, gstotal.toString());
					sheet.addCell(label);
					
					//生育
					label = new Label(23, row, m.getEfsb_syu().toString());
					sheet.addCell(label);
					label = new Label(24, row, m.getEmfz_syutotal().toString());
					sheet.addCell(label);
					BigDecimal syutotal = BigDecimal.ZERO;
					syutotal = m.getEfsb_syu().subtract(m.getEmfz_syutotal());
					label = new Label(25, row, syutotal.toString());
					sheet.addCell(label);
					
					//医疗
					label = new Label(26, row, m.getEfsb_yliao().toString());
					sheet.addCell(label);
					label = new Label(27, row, m.getEmfz_jltotal().toString());
					sheet.addCell(label);
					BigDecimal jltotal = BigDecimal.ZERO;
					jltotal = m.getEfsb_yliao().subtract(m.getEmfz_jltotal());
					label = new Label(28, row, jltotal.toString());
					sheet.addCell(label);

					// 住房公积金
					label = new Label(29, row, m.getEmfi_housetotal()
							.toString());
					sheet.addCell(label);
					label = new Label(30, row, m.getEmfz_housetotal()
							.toString());
					sheet.addCell(label);
					// 住房公积金合计
					BigDecimal hstotal = BigDecimal.ZERO;
					hstotal = m.getEmfi_housetotal().subtract(
							m.getEmfz_housetotal());
					label = new Label(31, row, hstotal.toString());
					sheet.addCell(label);

					// 智翼通其他费用
					label = new Label(32, row, m.getEmfz_elsefee().toString());
					sheet.addCell(label);

					// 智翼通服务费
					label = new Label(33, row, m.getEmfz_fee().toString());
					sheet.addCell(label);
					// 智翼通档案费
					label = new Label(34, row, m.getEmfz_filefee().toString());
					sheet.addCell(label);
					// 系统非标
					label = new Label(35, row, m.getEmfi_n_total().toString());
					sheet.addCell(label);
					
					// 姓名
					label = new Label(36, row, m.getEmfi_name());
					sheet.addCell(label);
					// 身份证
					label = new Label(37, row, m.getEmfi_idcard());
					sheet.addCell(label);
					row++;
				}
			}
		}
		// 写入数据
		wwb.write();
		// 关闭文件
		wwb.close();
	}

}
