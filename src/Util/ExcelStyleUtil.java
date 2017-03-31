package Util;

import jxl.format.Alignment;
import jxl.format.Border;
import jxl.format.BorderLineStyle;
import jxl.format.VerticalAlignment;
import jxl.write.WritableCellFormat;
import jxl.write.WritableFont;
import jxl.write.WriteException;

public class ExcelStyleUtil {// 处理excel导出是的格式
	// 字体设置
	public static WritableCellFormat getFormat() {
		WritableFont wf = new WritableFont(WritableFont.createFont("宋体"), 9);
		WritableCellFormat wc = new WritableCellFormat(wf);
		try {
			wc.setAlignment(Alignment.LEFT);
		} catch (WriteException e) {
			e.printStackTrace();
		}
		return wc;
	}

	// 字体居中加粗
	public static WritableCellFormat getFormatBold() {
		WritableFont wf = new WritableFont(WritableFont.createFont("宋体"), 11,
				WritableFont.BOLD);
		WritableCellFormat wc = new WritableCellFormat(wf);
		try {
			wc.setAlignment(Alignment.RIGHT);
		} catch (WriteException e) {
			e.printStackTrace();
		}
		return wc;
	}

	// 字体居中带边框
	public static WritableCellFormat getFormatBorderLine() {
		WritableFont wf = new WritableFont(WritableFont.createFont("宋体"), 11);
		WritableCellFormat wc = new WritableCellFormat(wf);
		try {
			wc.setAlignment(Alignment.CENTRE);
			wc.setVerticalAlignment(VerticalAlignment.CENTRE);
			wc.setBorder(Border.ALL, BorderLineStyle.THIN);
		} catch (WriteException e) {
			e.printStackTrace();
		}
		return wc;
	}
}
