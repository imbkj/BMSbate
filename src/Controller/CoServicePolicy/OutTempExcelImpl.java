package Controller.CoServicePolicy;

import java.io.File;
import java.io.FileOutputStream;
import java.util.List;

import bll.CoAgency.BaseInfo_SelBll;
import bll.CoServicePolicy.SePy_CityPolicySelectBll;

import service.ExcelService;

import jxl.CellView;
import jxl.Workbook;
import jxl.format.Alignment;
import jxl.format.Border;
import jxl.format.BorderLineStyle;
import jxl.format.Colour;
import jxl.format.UnderlineStyle;
import jxl.format.VerticalAlignment;
import jxl.write.Label;
import jxl.write.WritableCellFormat;
import jxl.write.WritableFont;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;
import jxl.write.WriteException;
import Model.CoAgencyBaseCityRelModel;
import Model.CoAgencyBaseModel;
import Model.CoServicePolicyTitleModel;
import Model.CoServicePolicyTypeModel;

public class OutTempExcelImpl implements ExcelService {
	private File file;// excel文件对象
	private String sheetName;// sheet名称
	private List<CoServicePolicyTypeModel> bclist;// excel中的内容
	private CoAgencyBaseCityRelModel coagmodel;

	public OutTempExcelImpl(File file, String sheetName,
			List<CoServicePolicyTypeModel> lists,
			CoAgencyBaseCityRelModel coagmodel) {
		// TODO Auto-generated method stub
		this.file = file;
		this.sheetName = sheetName;
		this.bclist = lists;
		this.coagmodel = coagmodel;
	}

	@Override
	public void writeExcel() throws Exception {
		SePy_CityPolicySelectBll sbll = new SePy_CityPolicySelectBll();
		bclist = sbll.getCoServicePolicyTypeLists(0, "");
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

		Label firstlabel = null;
		// 在Label对象的构造中指明单元格位置(参数依次代表列数、行数、内容 )
		
		firstlabel = new Label(0, 0, "机构编号");
		// 将定义好的单元格添加到工作表中
		sheet.addCell(firstlabel);
		
		firstlabel = new Label(1, 0, "城市");
		sheet.addCell(firstlabel);
		
		firstlabel = new Label(2, 0, "机构名称");
		// 将定义好的单元格添加到工作表中
		sheet.addCell(firstlabel);
		sheet.mergeCells(0, 0, 0, 3);
		sheet.mergeCells(1, 0, 0, 3);
		sheet.mergeCells(2, 0, 0, 3);

		Integer stardCell = 2;
		Integer endCell = 2;
		Integer tcell = 3;
		// 开始写入第一行(即标题栏)
		for (int i = 0; i < bclist.size(); i++) {// 写入自定义的表头
			CoServicePolicyTypeModel m = bclist.get(i);
			List<CoServicePolicyTitleModel> tlist = m.getTitlelist();
			stardCell = endCell + 1;
			endCell = endCell + tlist.size();
			// 用于写入文本内容到工作表中去
			Label label = null;
			// 在Label对象的构造中指明单元格位置(参数依次代表列数、行数、内容 )
			label = new Label(stardCell, 1, m.getNote_type(), getHeader());
			sheet.mergeCells(stardCell, 1, endCell, 1);
			// 将定义好的单元格添加到工作表中
			sheet.addCell(label);
			for (CoServicePolicyTitleModel tm : tlist) {
				label = new Label(tcell, 2, tm.getItem_id().toString(), getHeader());
				sheet.addCell(label);
				label = new Label(tcell, 3, tm.getItem_title(), getHeader());
				sheet.addCell(label);
				CellView cellView = new CellView();  
				cellView.setAutosize(true); //设置自动大小 
				sheet.setColumnView(2, cellView);//根据内容自动设置列宽
				sheet.setColumnView(3, cellView);//根据内容自动设置列宽
				tcell++;
			}
		}
		
		BaseInfo_SelBll wtbll = new BaseInfo_SelBll();
		List<CoAgencyBaseModel> wincabaList = wtbll.getWtAgencyList();
		Label wtlabel = null;
		Integer rownum=4;
		for (int wt = 0; wt < wincabaList.size(); wt++) {// 写入委托机构
			//机构编号
			CoAgencyBaseModel wtml=wincabaList.get(wt);
			wtlabel = new Label(0, rownum,wtml.getCabc_id().toString());// 第rownum行第1列
			sheet.addCell(wtlabel);
			
			//城市
			wtlabel = new Label(1, rownum,wtml.getCoab_city());// 第rownum行第1列
			sheet.addCell(wtlabel);
			
			//机构名称
			wtlabel = new Label(2, rownum,wtml.getCoab_name());// 第rownum行第1列
			sheet.addCell(wtlabel);
			rownum++;
		}
		// 写入数据
		wwb.write();
		// 关闭文件
		wwb.close();
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
			format.setBorder(Border.ALL, BorderLineStyle.THIN, Colour.BLACK);// 黑色边框
		} catch (WriteException e) {
			// TODO 自动生成 catch 块
			e.printStackTrace();
		}
		return format;
	}
}
