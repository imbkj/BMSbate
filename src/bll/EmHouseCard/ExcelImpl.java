package bll.EmHouseCard;

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

import Model.EmBodyCheckModel;
import Model.EmHouseTakeCardInfoModel;
import service.ExcelService;

public class ExcelImpl implements ExcelService{
	private File file;// excel文件对象
	private String sheetName;// sheet名称
	private String[] title;// 标题字符串
	private List<EmHouseTakeCardInfoModel> bclist;// excel中的内容
	public ExcelImpl(File file, String sheetName, String[] title,
			List<EmHouseTakeCardInfoModel> lists){
		// TODO Auto-generated method stub
		this.file = file;
		this.sheetName = sheetName;
		this.title = title;
		this.bclist = lists;
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
		EmHouse_TakeCardInfoSelectBll bll=new EmHouse_TakeCardInfoSelectBll();
		// 开始写入内容
		for (int row = 1; row < bclist.size() + 1; row++) {
			// 用model获取每一行数据
			EmHouseTakeCardInfoModel cop = bclist.get(row - 1);
			String emba_state=bll.getEmbaState(cop.getGid());
			// 将每列数据写入工作表中
			Label label = null;
			label = new Label(0, row, row+"");//第row行第0列
			sheet.addCell(label);
			label = new Label(1, row, cop.getOwnmonth());//第row行第1列
			sheet.addCell(label);
			label = new Label(2, row, cop.getRe_cgjjno());//第row行第2列
			sheet.addCell(label);
			label = new Label(3, row, cop.getShortname());//第row行第3列
			sheet.addCell(label);
			label = new Label(4, row, cop.getRe_gjjno());
			sheet.addCell(label);
			label = new Label(5, row, cop.getUsername());
			sheet.addCell(label);
			label = new Label(6, row, cop.getIdcard());
			sheet.addCell(label);
			label = new Label(7, row, cop.getRe_cardid());
			sheet.addCell(label);
			label = new Label(8, row, cop.getCoba_client());
			sheet.addCell(label);
			label = new Label(9, row, cop.getCohf_banklk());
			sheet.addCell(label);
			label = new Label(10, row, cop.getRe_accounttype());
			sheet.addCell(label);
			label = new Label(11, row, cop.getState_Name());
			sheet.addCell(label);
			label = new Label(12, row, cop.getRe_apptime());
			sheet.addCell(label);
			label = new Label(13, row, emba_state);
			sheet.addCell(label);
			label = new Label(14, row, cop.getDept());
			sheet.addCell(label);
			String ifpic="无";
			if(cop.getPicnum()>0)
			{
				ifpic="有";
			}
			label = new Label(15, row, ifpic);
			sheet.addCell(label);
		}
		// 写入数据
		wwb.write();
		// 关闭文件
		wwb.close();
		
	}

	@Override
	public void exportExcel() throws Exception {
		// TODO Auto-generated method stub
		
	}

}
