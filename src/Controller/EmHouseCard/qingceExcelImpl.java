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

public class qingceExcelImpl implements ExcelService {
	private String file;// excel文件对象
	private String filename;// sheet名称
	private List<EmHouseTakeCardInfoModel> list;

	public qingceExcelImpl(String file, String filename,
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
		EmHouse_TakeCardInfoSelectBll bll = new EmHouse_TakeCardInfoSelectBll();
		CoHousingFundModel hm=bll.getCompanyId(idstr);
		String houseId="";
		if(hm.getCohf_houseid()!=null)
		{
			houseId=hm.getCohf_houseid();
		}
		Label firstlabel = null;
		firstlabel = new Label(3, 1,houseId);
		sheet.addCell(firstlabel);
		
		String company="";
		if(hm.getCohf_company()!=null)
		{
			company=hm.getCohf_company();
		}
		Label twolabelt = null;
		twolabelt = new Label(3, 2,company);
		sheet.addCell(twolabelt);
		
		String sql = "";
		if (idstr != "") {
			sql = " and re_id in(" + idstr + ")";
			// 开始写入内容
			int row =6, y = 1;
			//EmHouse_TakeCardInfoSelectBll bll = new EmHouse_TakeCardInfoSelectBll();
			List<EmHouseTakeCardInfoModel> newlist = bll
					.getOutEmhouseTakeCardInfo(sql);
			for (int i = 0; i < newlist.size(); i++) {
				// 用model获取每一行数据
				EmHouseTakeCardInfoModel m = newlist.get(i);
				// 将每列数据写入工作表中
				Label label = null;
				label = new Label(0, row, y + "", getHeader());

				sheet.addCell(label);
				label = new Label(1, row, m.getUsername(), getHeader());
				sheet.addCell(label);
				label = new Label(2, row, "01", getHeader());
				sheet.addCell(label);
				label = new Label(3, row, m.getEmba_idcard(), getHeader());
				sheet.addCell(label);
				String computerid = "";
				if (m.getEsiu_computerid() != null) {
					computerid = m.getEsiu_computerid();
				}
				label = new Label(4, row, computerid, getHeader());
				sheet.addCell(label);
				String degree = "04";
				if (m.getEmhu_degree() != null) {
					if (m.getEmhu_degree().contains("博士")) {
						degree = "01";
					} else if (m.getEmhu_degree().contains("硕士")) {
						degree = "02";
					} else if (m.getEmhu_degree().contains("学士")) {
						degree = "03";
					}
				}
				label = new Label(5, row, degree, getHeader());
				sheet.addCell(label);

				String tittle = "050";
				if (m.getEmhu_title() != null) {
					if (m.getEmhu_title().contains("正高")) {
						tittle = "010";
					} else if (m.getEmhu_title().contains("副高")) {
						tittle = "020";
					} else if (m.getEmhu_title().contains("中级")) {
						tittle = "030";
					} else if (m.getEmhu_title().contains("初级")) {
						tittle = "040";
					}
				}
				label = new Label(6, row, tittle, getHeader());
				sheet.addCell(label);
				label = new Label(7, row, m.getOwnmonth(), getHeader());
				sheet.addCell(label);

				String radix = "";
				if (m.getEmhu_radix() != null && !m.getEmhu_radix().equals("")) {
					radix = m.getEmhu_radix() + "";
				}
				label = new Label(8, row, radix, getHeader());
				sheet.addCell(label);

				String hj = "04";
				if (m.getEmhu_hj() != null) {
					if (m.getEmhu_hj().contains("深户")) {
						hj = "01";
					} else if (m.getEmhu_hj().contains("城镇")) {
						hj = "02";
					} else if (m.getEmhu_hj().contains("农村")) {
						hj = "03";
					}
				}
				label = new Label(9, row, hj, getHeader());
				sheet.addCell(label);

				String mobile = "";
				if (m.getEmhu_mobile() != null) {
					mobile = m.getEmhu_mobile();
				}
				else if(m.getEmba_mobile()!=null)
				{
					mobile=m.getEmba_mobile();
				}
				
				label = new Label(10, row, mobile, getHeader());
				sheet.addCell(label);

				String marital = "02";
				if (m.getEmba_marital() != null) {
					if (m.getEmba_marital().contains("已婚")
							|| m.getEmba_marital().contains("初婚")
							|| m.getEmba_marital().contains("再婚")) {
						marital = "01";
					}
				}
				label = new Label(11, row, marital, getHeader());
				sheet.addCell(label);

				String wifename = "";
				if (m.getEmhu_wifename() != null) {
					wifename = m.getEmhu_wifename();
				}
				label = new Label(12, row, wifename, getHeader());
				sheet.addCell(label);

				String wifeiidcard = "";
				if (m.getEmhu_wifeidcard() != null) {
					wifeiidcard = m.getEmhu_wifeidcard();
				}
				label = new Label(13, row, wifeiidcard, getHeader());
				sheet.addCell(label);

				String gid = "";
				if (m.getGid() != null) {
					gid = m.getGid().toString();
				}
				label = new Label(14, row, gid, getHeader());
				sheet.addCell(label);
				row++;
				y++;
			}
			
			Label bolowlabel = null;
			bolowlabel = new Label(0, row,"单位意见", getBottom());
			sheet.addCell(bolowlabel);
			sheet.mergeCells(0,row,14,row);
			sheet.setRowView(row,500);
			
			bolowlabel = new Label(0, row+1,"   本单位承诺：以上所填写及提交的材料内容真实、合法、有效。如违反本承诺的，本单位愿意承担由此产生的一切法律责任。", getBottom());
			sheet.addCell(bolowlabel);
			sheet.mergeCells(0,row+1,14,row+1);
			sheet.setRowView(row+1,500);
			
			bolowlabel = new Label(0, row+2,"            专办员：              单位公章：", getBottom());
			sheet.addCell(bolowlabel);
			sheet.mergeCells(0,row+2,14,row+2);
			sheet.setRowView(row+2,1800);
			
			bolowlabel = new Label(0, row+3,"                                               申请日期：    年    月    日", getBottom());
			sheet.addCell(bolowlabel);
			sheet.mergeCells(0,row+3,14,row+3);
			sheet.setRowView(row+3,500);
			
			bolowlabel = new Label(0, row+4,"  备注: 1.证件类型填写相应代码：01身份证(暂只允许01)，(02军官证 03护照 04其他)；      2.最高学位填写相应代码：01博士学位，02硕士学位，03学士学位，04其他；", getBottom2());
			sheet.addCell(bolowlabel);
			sheet.mergeCells(0,row+4,14,row+4);
			sheet.setRowView(row+4,500);
			
			bolowlabel = new Label(0, row+5,"      3.职称填写相应代码：010正高职称，020副高职称，030中级职称，040初级职称，050无； 4.户籍情况填写相应代码：01深户，02非深户城镇，03非深户农村，04其他；", getBottom2());
			sheet.addCell(bolowlabel);
			sheet.mergeCells(0,row+5,14,row+5);
			sheet.setRowView(row+5,500);
			
			bolowlabel = new Label(0, row+6,"      5.婚姻状况： 01是（已婚），02否（单身）;                                    6.职工编号非必填项,若填写职工编号,职工联名卡制卡顺序将按照职工编号排列。     ", getBottom2());
			sheet.addCell(bolowlabel);
			sheet.mergeCells(0,row+6,14,row+6);
			sheet.setRowView(row+6,500);
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
		WritableFont wf_table = new WritableFont(WritableFont.ARIAL, 12,  
                WritableFont.NO_BOLD, false, UnderlineStyle.NO_UNDERLINE,  
                jxl.format.Colour.BLACK); // 定义格式 字体 下划线 斜体 粗体 颜色
		WritableCellFormat format = new WritableCellFormat(wf_table);
		try {
			format.setAlignment(jxl.format.Alignment.CENTRE);// 左右居中
			format.setVerticalAlignment(jxl.format.VerticalAlignment.CENTRE);// 上下居中
			//format.set
		  
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
	public static WritableCellFormat getBottom2() {
		WritableFont wf_table = new WritableFont(WritableFont.ARIAL, 12,  
                WritableFont.NO_BOLD, false, UnderlineStyle.NO_UNDERLINE,  
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
