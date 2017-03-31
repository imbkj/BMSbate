package bll.EmBodyCheck;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import jxl.Workbook;
import jxl.format.Border;
import jxl.format.BorderLineStyle;
import jxl.format.Colour;
import jxl.format.UnderlineStyle;
import jxl.write.WritableCellFormat;
import jxl.write.WritableFont;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;
import jxl.write.WriteException;
import jxl.write.biff.RowsExceededException;

import org.zkoss.zul.ListModelList;

import dal.LoginDal;
import dal.EmBodyCheck.EmBc_DateListSelectDal;

import Model.EmBodyCheckDateListModel;
import Model.LoginModel;
import Util.FileOperate;
import Util.UserInfo;
import Util.pinyin4jUtil;

public class EmBc_DateListSelectBll {

	// 体检时间信息
	public List<EmBodyCheckDateListModel> getEmBodyCheckDateListInfo(String str) {
		EmBc_DateListSelectDal dal = new EmBc_DateListSelectDal();
		return dal.getEmBodyCheckDateListInfo(str, true);
	}

	// 获取客服列表
	public List<LoginModel> getClientList() {
		List<LoginModel> list = new ListModelList<>();
		LoginDal dal = new LoginDal();
		list = dal.getLoginInfo();
		return list;
	}

	// 导出年度体检时间名单
	public void createFile(List<EmBodyCheckDateListModel> list) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
		Date trialTime = new Date();
		String exfileName = sdf.format(trialTime) + "_"
				+ pinyin4jUtil.getPinYinHeadChar(UserInfo.getUsername())
				+ ".xls";
		Workbook workBook = null;
		WritableWorkbook wb = null;
		String absolutePath = FileOperate.getAbsolutePath();
		try {
			workBook = Workbook.getWorkbook(new File(absolutePath
					+ "OfficeFile/Templet/EmBodyCheck/YearBC/bcYear.xls"));
			wb = Workbook.createWorkbook(new File(absolutePath
					+ "OfficeFile/DownLoad/EmBodyCheck/ClientFile/"+exfileName), workBook);

			WritableSheet sheet = wb.getSheet(0);
			WritableCellFormat wcf = getBodyCellStyle();
			jxl.write.Label lbl = null;
			Integer i = 1;
			for (EmBodyCheckDateListModel m : list) {
				sheet.insertRow(i);
				sheet.setRowView(i, 375, false);
				
				lbl = new jxl.write.Label(0, i, i.toString(), wcf);
				sheet.addCell(lbl);
				
				lbl = new jxl.write.Label(1, i,
						m.getCoba_shortname(), wcf);
				sheet.addCell(lbl);
				
				lbl = new jxl.write.Label(2, i,
						m.getEmba_name(), wcf);
				sheet.addCell(lbl);
				
				lbl = new jxl.write.Label(3, i,
						m.getEmba_sex(), wcf);
				sheet.addCell(lbl);
				
				lbl = new jxl.write.Label(4, i,
						m.getEmba_idcard(), wcf);
				sheet.addCell(lbl);
				
				lbl = new jxl.write.Label(5, i,
						m.getEmba_indate(), wcf);
				sheet.addCell(lbl);
				
				lbl = new jxl.write.Label(6, i,
						m.getBctime(), wcf);
				sheet.addCell(lbl);
				
				lbl = new jxl.write.Label(7, i,
						m.getCoba_client(), wcf);
				sheet.addCell(lbl);
				i++;
			}
			
			wb.write();
			wb.close();
			workBook.close();

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		FileOperate.download("OfficeFile/DownLoad/EmBodyCheck/ClientFile/"
				+ exfileName);
	}

	public WritableCellFormat getBodyCellStyle() {

		/*
		 * WritableFont.createFont("宋体")：设置字体为宋体 10：设置字体大小
		 * WritableFont.NO_BOLD:设置字体非加粗（BOLD：加粗 NO_BOLD：不加粗） false：设置非斜体
		 * UnderlineStyle.NO_UNDERLINE：没有下划线
		 */
		WritableFont font = new WritableFont(WritableFont.createFont("宋体"), 10,
				WritableFont.NO_BOLD, false, UnderlineStyle.NO_UNDERLINE);

		WritableCellFormat bodyFormat = new WritableCellFormat(font);
		try {
			// 设置单元格背景色：表体为白色
			bodyFormat.setBackground(Colour.WHITE);
			// 设置表头表格边框样式
			// 整个表格线为细线、黑色
			bodyFormat
					.setBorder(Border.ALL, BorderLineStyle.THIN, Colour.BLACK);

		} catch (WriteException e) {
			System.out.println("表体单元格样式设置失败！");
		}
		return bodyFormat;
	}
}
