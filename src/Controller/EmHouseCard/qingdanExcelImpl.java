package Controller.EmHouseCard;

import java.io.File;
import java.util.List;

import org.zkoss.zul.Messagebox;

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
import Model.CoHousingFundModel;
import Model.EmHouseTakeCardInfoModel;
import bll.EmHouseCard.EmHouse_TakeCardInfoSelectBll;
import service.ExcelService;

public class qingdanExcelImpl implements ExcelService {

	private String file;// excel文件对象
	private String filename;// sheet名称
	private List<EmHouseTakeCardInfoModel> list;

	public qingdanExcelImpl(String file, String filename,
			List<EmHouseTakeCardInfoModel> lists) {
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
			CoHousingFundModel hm=bll.getCompanyId(idstr);
			String bank="中国银行";
			if(hm.getCohf_houseid()!=null&&!hm.getCohf_houseid().equals(""))
			{
				if(hm.getCohf_houseid().equals("-1"))
				{
					Messagebox.show("导出的数据公积金号不同，不能导出", "提示", Messagebox.OK, Messagebox.ERROR);
					return;
				}
				if(hm.getCohf_banklk()!=null)
				{
					if(hm.getCohf_banklk().contains("中行"))
					{
						bank="中国银行";
					}
					else if(hm.getCohf_banklk().contains("建行"))
					{
						bank="建设银行";
					}
					else if(hm.getCohf_banklk().contains("招行"))
					{
						bank="招商银行";
					}
				}
			}
			else
			{
				Messagebox.show("选择的数据没有单位公积金号，不能导出", "提示", Messagebox.OK, Messagebox.ERROR);
				return;
			}
			List<EmHouseTakeCardInfoModel> newlist = bll
					.getOutEmhouseTakeCardInfo(sql);
			Label firstlabel = null;
			firstlabel = new Label(0, 0,bank+"公积金联名卡领卡交接清单", getFirstHeader());
			sheet.addCell(firstlabel);
			
			String houseId="";
			if(hm.getCohf_houseid()!=null)
			{
				houseId=hm.getCohf_houseid();
			}
			Label twolabel = null;
			twolabel = new Label(1, 1,houseId);
			sheet.addCell(twolabel);
			
			String company="";
			if(hm.getCohf_company()!=null)
			{
				company=hm.getCohf_company();
			}
			Label twolabelt = null;
			twolabelt = new Label(3, 1,company);
			sheet.addCell(twolabelt);
			
			int row =3, y = 1;
			for (int i = 0; i < newlist.size(); i++) {
				// 用model获取每一行数据
				EmHouseTakeCardInfoModel m = newlist.get(i);
				// 将每列数据写入工作表中
				Label label = null;
				label = new Label(0, row, y + "", getHeader());
				sheet.addCell(label);
				
				String gjjno="";
				if(m.getEmhu_houseid()!=null)
				{
					gjjno=m.getEmhu_houseid();
				}
				else
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
				
				String idcard="";
				if(m.getEmba_idcard()!=null)
				{
					idcard=m.getEmba_idcard();
				}
				label = new Label(3, row,idcard, getHeader());
				sheet.addCell(label);
		
				String cardid="";
				if(m.getRe_cardid()!=null)
				{
					cardid=m.getRe_cardid();
				}
				label = new Label(4, row,cardid, getHeader());
				sheet.addCell(label);
				
				String mobile="";
				if(m.getEmba_mobile()!=null)
				{
					mobile=m.getEmba_mobile();
				}
				label = new Label(6, row,mobile, getHeader());
				sheet.addCell(label);

				row++;
				y++;
			}
			
			Label bolowlabel = null;
			bolowlabel = new Label(0, row,"领卡异常情况备注：", getBottom());
			sheet.addCell(bolowlabel);
			sheet.mergeCells(0,row,6,row);
			sheet.setRowView(row,1000);
			
			bolowlabel = new Label(0, row+1,"交接栏：", getBottom2());
			sheet.addCell(bolowlabel);
			sheet.mergeCells(0,row+1,6,row+1);
			
			bolowlabel = new Label(0, row+2,"      已核对上述中国银行公积金联名卡明细及员工身份证复印件，合计领卡         张，密码信封          份。", getBottom3());
			sheet.addCell(bolowlabel);
			sheet.mergeCells(0,row+2,6,row+2);
			
			bolowlabel = new Label(0, row+3,"", getBottom3());
			sheet.addCell(bolowlabel);
			sheet.mergeCells(0,row+3,6,row+3);
			
			bolowlabel = new Label(0, row+4,"银行经办员：                银行复核员：                 资料收取日期：                     单位专办员：                 领卡日期： ", getBottom4());
			sheet.addCell(bolowlabel);
			sheet.mergeCells(0,row+4,6,row+4);
			
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
		WritableFont wf_table = new WritableFont(WritableFont.ARIAL, 20,  
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
