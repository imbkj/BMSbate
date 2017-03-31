package bll.SystemControl;

import java.io.File;
import java.io.FileOutputStream;
import java.util.ArrayList;
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
import jxl.write.WriteException;
import jxl.write.biff.RowsExceededException;

import Model.EmHouseTakeCardInfoModel;
import Model.MenuListModel;
import service.ExcelService;

public class ExcelImpl implements ExcelService {
	
	private File file;// excel文件对象
	private String sheetName;// sheet名称
	private String[] title;// 标题字符串
	private List<MenuListModel> bclist;// excel中的内容
	private List<MenuListModel> blist=new ArrayList<MenuListModel>();// excel中的内容
	private Integer k=0,m=1;
	public ExcelImpl(File file, String sheetName, String[] title,
			List<MenuListModel> lists){
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
		for (int i = 0; i < title.length; i++) {

			// 用于写入文本内容到工作表中去
			Label label = null;
			// 在Label对象的构造中指明单元格位置(参数依次代表列数、行数、内容 )
			label = new Label(i, 0, title[i], wcf);
			// 将定义好的单元格添加到工作表中
			sheet.addCell(label);
		}
		//开始写入内容
		for (int row = 1; row < bclist.size() + 1; row++) {
			// 用model获取每一行数据
			MenuListModel cop = bclist.get(row - 1);
			// 将每列数据写入工作表中
			if(cop.getMeu_pid()==0)
			{
				k=0;//列
				m=m+1;//行
				Label label = null;
				label = new Label(k, m, cop.getMeu_name());
				sheet.addCell(label);
				getlist(cop.getMeu_id(),0,sheet);
			}
		}
		// 写入数据
		wwb.write();
		// 关闭文件
		wwb.close();	
	}
	
	private List<MenuListModel> getlist(Integer id,Integer pid,WritableSheet sheet) throws RowsExceededException, WriteException
	{
		Integer l=0;
		m=m+1;
		List<MenuListModel> li=new ArrayList<MenuListModel>();
		for(MenuListModel ml:bclist)
		{
			if(ml.getMeu_pid()==id)
			{
				if(l==0)
				{
					k=k+1;
					l=1;
				}
				Label label = null;
				label = new Label(k, m, ml.getMeu_name());
				sheet.addCell(label);
				getlist(ml.getMeu_id(),ml.getMeu_pid(),sheet);
			}
		}
		return li;
	}

}
