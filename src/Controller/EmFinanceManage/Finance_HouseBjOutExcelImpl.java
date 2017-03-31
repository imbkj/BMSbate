package Controller.EmFinanceManage;

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
import Model.SbGatherModel;
import bll.EmHouseCard.EmHouse_TakeCardInfoSelectBll;
import service.ExcelService;

public class Finance_HouseBjOutExcelImpl implements ExcelService {
	private File file;// excel文件对象
	private String sheetName;// sheet名称
	private String[] title;// 标题字符串
	private List<SbGatherModel> bclist;// excel中的内容
	public Finance_HouseBjOutExcelImpl(File file, String sheetName, String[] title,
			List<SbGatherModel> lists){
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
			SbGatherModel m = bclist.get(row - 1);
			// 将每列数据写入工作表中
			Label label = null;
			label = new Label(0, row, row+"");//第row行第0列
			sheet.addCell(label);
			
			String coab_name="";
			if(m.getCoab_name()!=null)
			{
				coab_name=m.getCoab_name();
			}
			label = new Label(1, row,coab_name);//第row行第2列
			sheet.addCell(label);
			
			String ownmonth="";
			if(m.getOwnmonth()!=null)
			{
				ownmonth=m.getOwnmonth().toString();
			}
			label = new Label(2, row,ownmonth);//第row行第1列
			sheet.addCell(label);
			
			String coba_ufid2="";
			if(m.getCoba_ufid2()!=null)
			{
				coba_ufid2=m.getCoba_ufid2();
			}
			label = new Label(3, row,coba_ufid2);//第row行第2列
			sheet.addCell(label);
			
			String clientclass="";
			if(m.getClientclass()!=null)
			{
				clientclass=m.getClientclass();
			}
			label = new Label(4, row,clientclass);//第row行第3列
			sheet.addCell(label);
			
			String ufclass="";
			if(m.getCoba_hsufclass()!=null)
			{
				ufclass=m.getCoba_hsufclass();
			}
			label = new Label(5, row, ufclass);
			sheet.addCell(label);
			
			label = new Label(6, row, m.getCid().toString());
			sheet.addCell(label);
			label = new Label(7, row, m.getCoba_company());
			sheet.addCell(label);
			String hsbjtotal="";
			if(m.getHsbjtotal()!=null)
			{
				hsbjtotal=m.getHsbjtotal().toString();
			}
			label = new Label(8, row, hsbjtotal);
			sheet.addCell(label);
			
			String cfsa_total="";
			if(m.getCfsa_total()!=null)
			{
				cfsa_total=m.getCfsa_total().toString();
			}
			label = new Label(9, row,cfsa_total);
			sheet.addCell(label);
			
			BigDecimal sunTotal=m.getHsbjtotal().subtract(m.getCfsa_total());
			
			label = new Label(10, row, sunTotal.toString());
			sheet.addCell(label);
			
			label = new Label(11, row, m.getCoba_client());
			sheet.addCell(label);
			label = new Label(12, row,"已托收");
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
