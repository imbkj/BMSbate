package Controller.EmSheBaocard;

import java.io.File;
import java.util.List;

import jxl.Workbook;
import jxl.format.Border;
import jxl.format.BorderLineStyle;
import jxl.format.Colour;
import jxl.write.Label;
import jxl.write.WritableCellFormat;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;
import jxl.write.WriteException;
import Model.EmShebaoCardInfoModel;
import Model.PubCodeConversionModel;
import bll.EmSheBaocard.EmShebaoCardInfoSelectBll;
import service.ExcelService;

public class CunExcelCenterImpl implements ExcelService {
	private String file;// excel文件对象
	private String filename;// sheet名称
	private List<EmShebaoCardInfoModel> list;

	public CunExcelCenterImpl(String file, String filename,
			List<EmShebaoCardInfoModel> lists) {
		// TODO Auto-generated method stub
		this.file = file;
		this.filename = filename;
		this.list = lists;
	}

	@Override
	public void writeExcel() throws Exception {
		Workbook wk = Workbook.getWorkbook(new File(file));
		// 使用模板创建
		WritableWorkbook wbook = Workbook
				.createWorkbook(new File(filename), wk);
		WritableSheet sheet = wbook.getSheet(0);

		// 开始写入内容
		int row = 5, y = 1;
		EmShebaoCardInfoSelectBll bll = new EmShebaoCardInfoSelectBll();
		List<PubCodeConversionModel> edulist = bll.getPubCodeConversionList(36,
				"文化程度");// 文化程度
		List<PubCodeConversionModel> marylist = bll.getPubCodeConversionList(
				36, "婚姻状况");// 婚姻状况
		for (int i = 0; i < list.size(); i++) {
			// 用model获取每一行数据
			EmShebaoCardInfoModel model = list.get(i);
			// 将每列数据写入工作表中
			Label label = null;
			label = new Label(0, row, y + "", getHeader());

			sheet.addCell(label);
			label = new Label(1, row, model.getSbcd_sbnumber(), getHeader());//社保单位编号对应表格上面的部门
			sheet.addCell(label);
			label = new Label(2, row, model.getSbcd_name(), getHeader());
			sheet.addCell(label);
			label = new Label(3, row, model.getSbcd_computerid(),getHeader());
			sheet.addCell(label);
			label = new Label(4, row,model.getSbcd_mobile(), getHeader());
			sheet.addCell(label);
			label = new Label(5, row, model.getSbcd_idcard(), getHeader());
			sheet.addCell(label);
			label = new Label(6, row, model.getSbcd_idcardstartdate(), getHeader());
			sheet.addCell(label);
			label = new Label(7, row, model.getSbcd_idcardenddate(), getHeader());
			sheet.addCell(label);
			row++;
			y++;
		}

		// 写入数据
		wbook.write();
		// 关闭文件
		wbook.close();
	}

	// 根据中文查询代号
	private String getCode(List<PubCodeConversionModel> list, String name) {
		String code = "";
		for (PubCodeConversionModel m : list) {
			if (m.getPcco_cn() != null && m.getPcco_cnname().equals(name)) {
				code = m.getPcco_code();
				break;
			}
		}
		return code;
	}

	/**
	 * 设置头的样式
	 * 
	 * @return
	 */
	public static WritableCellFormat getHeader() {
		WritableCellFormat format = new WritableCellFormat();
		try {
			format.setAlignment(jxl.format.Alignment.CENTRE);// 左右居中
			format.setVerticalAlignment(jxl.format.VerticalAlignment.CENTRE);// 上下居中
			format.setBorder(Border.ALL, BorderLineStyle.THIN, Colour.BLACK);// 黑色边框
		} catch (WriteException e) {
			// TODO 自动生成 catch 块
			e.printStackTrace();
		}
		return format;
	}
}
