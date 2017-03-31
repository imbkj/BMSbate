package Controller.EmHouseCard;

import java.io.File;
import java.util.List;

import jxl.Workbook;
import jxl.format.Border;
import jxl.format.BorderLineStyle;
import jxl.format.Colour;
import jxl.format.UnderlineStyle;
import jxl.write.Label;
import jxl.write.WritableCellFormat;
import jxl.write.WritableFont;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;
import jxl.write.WriteException;
import bll.EmHouseCard.EmHouse_TakeCardInfoSelectBll;

import Model.CoHousingFundModel;
import Model.EmHouseTakeCardInfoModel;
import service.ExcelService;

public class jhExcelImpl implements ExcelService {

	private String file;// excel文件对象
	private String filename;// sheet名称
	private List<EmHouseTakeCardInfoModel> list;
	public jhExcelImpl(String file, String filename,
			List<EmHouseTakeCardInfoModel> lists)
	{
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
		String idstr = "";
		for (EmHouseTakeCardInfoModel model : list) {
			if (idstr == "") {
				idstr = model.getRe_id() + "";
			} else {
				idstr = idstr + "," + model.getRe_id();
			}
		}
		String sql = "";
		if (idstr != "") {
			sql = " and re_id in(" + idstr + ")";
			// 开始写入内容
			EmHouse_TakeCardInfoSelectBll bll = new EmHouse_TakeCardInfoSelectBll();
			CoHousingFundModel hm=bll.getCompany(idstr);
			
			List<EmHouseTakeCardInfoModel> newlist = bll
					.getOutEmhouseTakeCardInfo(sql);
			Label firstlabel = null;
			firstlabel = new Label(5, 0,hm.getCohf_company()+"  公积金卡领取清册", getFirstHeader());
			sheet.addCell(firstlabel);

			int row =3,num=1;
			for (int i = 0; i < newlist.size(); i++) {
				// 用model获取每一行数据
				EmHouseTakeCardInfoModel m = newlist.get(i);
				// 将每列数据写入工作表中
				Label label = null;
				label = new Label(0, row, num+"", getHeader());
				sheet.addCell(label);
				
				String gjjno="";
				if(m.getRe_gjjno()!=null)
				{
					gjjno=m.getRe_gjjno();
				}
				label = new Label(1, row,gjjno, getHeader());
				sheet.addCell(label);
				
				String username="";
				if(m.getUsername()!=null)
				{
					username=m.getUsername();
				}
				label = new Label(2, row,username, getHeader());
				sheet.addCell(label);
				
				label = new Label(3, row,"身份证", getHeader());
				sheet.addCell(label);
		
				String idcard="";
				if(m.getEmba_idcard()!=null)
				{
					idcard=m.getEmba_idcard();
				}
				
				label = new Label(4, row,idcard, getHeader());
				sheet.addCell(label);
				
				String re_cardid="";
				if(m.getRe_cardid()!=null)
				{
					re_cardid=m.getRe_cardid();
				}
				label = new Label(5, row,re_cardid, getHeader());
				sheet.addCell(label);
				
				String cggno="";
				System.out.println("cggno="+m.getRe_cgjjno());
				System.out.println("name="+m.getCoba_company());
				if(m.getRe_cgjjno()!=null)
				{
					cggno=m.getRe_cgjjno();
				}
				label = new Label(6, row,cggno, getHeader());
				sheet.addCell(label);
				
				String ownmonth="";
				if(m.getOwnmonth()!=null)
				{
					ownmonth=m.getOwnmonth();
				}
				label = new Label(7, row,ownmonth, getHeader());
				sheet.addCell(label);
				
				String coba_shortname="";
				if(m.getCoba_company()!=null)
				{
					coba_shortname=m.getCoba_company();
				}
				else if(m.getCname()!=null)
				{
					coba_shortname=m.getCname();
				}
				label = new Label(8, row,coba_shortname, getHeader());
				sheet.addCell(label);
		
				num++;
				row++;
			}
			
			// 写入数据
			wbook.write();
			// 关闭文件
			wbook.close();
		}
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
	
	/**
	 * 设置底部的样式
	 * 
	 * @return
	 */
	public static WritableCellFormat getBottom() {
		WritableFont wf_table = new WritableFont(WritableFont.ARIAL, 10,  
                WritableFont.NO_BOLD, false, UnderlineStyle.NO_UNDERLINE,  
                jxl.format.Colour.BLACK); // 定义格式 字体 下划线 斜体 粗体 颜色
		WritableCellFormat format = new WritableCellFormat(wf_table);
		try {
			format.setBorder(Border.ALL, BorderLineStyle.THIN, Colour.BLACK);// 黑色边框
			format.setVerticalAlignment(jxl.format.VerticalAlignment.TOP);// 上下居中
		} catch (WriteException e) {
			// TODO 自动生成 catch 块
			e.printStackTrace();
		}
		return format;
	}
	
	/**
	 * 设置底部的样式；边框：左上右
	 * 
	 * @return
	 */
	public static WritableCellFormat getBottom2() {
		WritableFont wf_table = new WritableFont(WritableFont.ARIAL, 10,  
                WritableFont.NO_BOLD, false, UnderlineStyle.NO_UNDERLINE,  
                jxl.format.Colour.BLACK); // 定义格式 字体 下划线 斜体 粗体 颜色
		WritableCellFormat format = new WritableCellFormat(wf_table);
		try {
			format.setVerticalAlignment(jxl.format.VerticalAlignment.TOP);// 上下居中
			format.setBorder(Border.LEFT, BorderLineStyle.THIN, Colour.BLACK);// 黑色边框
			format.setBorder(Border.RIGHT, BorderLineStyle.THIN, Colour.BLACK);// 黑色边框
			format.setBorder(Border.TOP, BorderLineStyle.THIN, Colour.BLACK);// 黑色边框
		  
		} catch (WriteException e) {
			// TODO 自动生成 catch 块
			e.printStackTrace();
		}
		return format;
	}
	
	/**
	 * 设置底部的样式；边框左右
	 * 
	 * @return
	 */
	public static WritableCellFormat getBottom3() {
		WritableFont wf_table = new WritableFont(WritableFont.ARIAL, 10,  
                WritableFont.NO_BOLD, false, UnderlineStyle.NO_UNDERLINE,  
                jxl.format.Colour.BLACK); // 定义格式 字体 下划线 斜体 粗体 颜色
		WritableCellFormat format = new WritableCellFormat(wf_table);
		try {
			format.setVerticalAlignment(jxl.format.VerticalAlignment.CENTRE);// 上下居中
			format.setBorder(Border.LEFT, BorderLineStyle.THIN, Colour.BLACK);// 黑色边框
			format.setBorder(Border.RIGHT, BorderLineStyle.THIN, Colour.BLACK);// 黑色边框  
		} catch (WriteException e) {
			// TODO 自动生成 catch 块
			e.printStackTrace();
		}
		return format;
	}
	
	/**
	 * 设置底部的样式；边框左右下
	 * 
	 * @return
	 */
	public static WritableCellFormat getBottom4() {
		WritableFont wf_table = new WritableFont(WritableFont.ARIAL, 10,  
                WritableFont.NO_BOLD, false, UnderlineStyle.NO_UNDERLINE,  
                jxl.format.Colour.BLACK); // 定义格式 字体 下划线 斜体 粗体 颜色
		WritableCellFormat format = new WritableCellFormat(wf_table);
		try {
			format.setAlignment(jxl.format.Alignment.LEFT);// 左右居中
			format.setVerticalAlignment(jxl.format.VerticalAlignment.BOTTOM);// 上下居中
			format.setBorder(Border.LEFT, BorderLineStyle.THIN, Colour.BLACK);// 黑色边框
			format.setBorder(Border.RIGHT, BorderLineStyle.THIN, Colour.BLACK);// 黑色边框
			format.setBorder(Border.BOTTOM, BorderLineStyle.THIN, Colour.BLACK);// 黑色边框
		  
		} catch (WriteException e) {
			// TODO 自动生成 catch 块
			e.printStackTrace();
		}
		return format;
	}
	
	/**
	 * 设置底部的样式
	 * 
	 * @return
	 */
	public static WritableCellFormat getFirstHeader() {
		WritableFont wf_table = new WritableFont(WritableFont.ARIAL, 14,  
                WritableFont.BOLD, false, UnderlineStyle.NO_UNDERLINE,  
                jxl.format.Colour.BLACK); // 定义格式 字体 下划线 斜体 粗体 颜色
		WritableCellFormat format = new WritableCellFormat(wf_table);
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
	@Override
	public void exportExcel() throws Exception {
		// TODO Auto-generated method stub
		
	}


}
