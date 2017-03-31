package Controller.EmResidencePermit;

import java.io.File;
import java.util.Calendar;
import java.util.List;

import jxl.Workbook;
import jxl.format.ScriptStyle;
import jxl.write.Label;
import jxl.write.WritableCell;
import jxl.write.WritableCellFormat;
import jxl.write.WritableFont;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;
import Model.EmResidencePermitInfoModel;
import service.ExcelService;

public class newExcelImpl implements ExcelService  {
	private String file;// excel文件对象
	private String filename;// sheet名称
	private String number="";
	private List<EmResidencePermitInfoModel> list;
	public newExcelImpl(String file, String filename,
			List<EmResidencePermitInfoModel> list,String number){
		// TODO Auto-generated method stub
		this.file = file;
		this.filename = filename;
		this.list = list;
		this.number=number;
	}

	
	@Override
	public void writeExcel() throws Exception {
        Workbook wk=Workbook.getWorkbook(new File(file));
        // 使用模板创建
     	WritableWorkbook wbook = Workbook.createWorkbook(new File(
     			 filename), wk);
        WritableSheet  sheet = wbook.getSheet(0);
        WritableFont titleWf = new WritableFont(WritableFont.createFont("宋体"),12, WritableFont.BOLD);  
        WritableCellFormat wcf = new WritableCellFormat(titleWf);
        Label labels = new Label(0,2,getDatestr()+"费用明细");
		sheet.addCell(labels);
		WritableCell  cell = sheet.getWritableCell(0,2);
		cell.setCellFormat(wcf); 
		Label labelnum = new Label(0,3,"单号："+number);
		sheet.addCell(labelnum);
		WritableCell  cl = sheet.getWritableCell(0,3);
		cl.setCellFormat(wcf); 
		Integer fees=getFee();
		Label labelfee = new Label(0,5,"本批证件费用总额为："+fees);
		sheet.addCell(labelfee);
		Label labelp = new Label(0,6,"本次办理居住证"+list.size()+"人，预借办证工本费"+fees+"元。");
		sheet.addCell(labelp);
		// 开始写入内容
		int row =10,y=1;
		for (int i=0; i < list.size(); i++) {
			// 用model获取每一行数据
			EmResidencePermitInfoModel model = list.get(i);
			// 将每列数据写入工作表中
			Label label = null;
			label = new Label(0, row, y+"");
			sheet.addCell(label);
			label = new Label(1, row,model.getCoba_client());
			sheet.addCell(label);
			label = new Label(2, row, model.getCoba_shortname());
			sheet.addCell(label);
			label = new Label(3, row, model.getEmba_name());
			sheet.addCell(label);
			label = new Label(4, row, model.getErpi_payment_kind());
			sheet.addCell(label);
			String fee="";
			if(model.getErpi_fee()!=null)
			{
				fee=model.getErpi_fee()+"";
			}
			label = new Label(5, row,fee);
			sheet.addCell(label);
			label = new Label(6, row, model.getErpi_payment_state());
			sheet.addCell(label);
			row++;
			y++;
		}
		// 写入数据
		wbook.write();
		// 关闭文件
		wbook.close();
	}
	
	//获取当前日期
	private String getDatestr()
	{
		String datestr="";
		int y,m,d;    
		Calendar cal=Calendar.getInstance();    
		y=cal.get(Calendar.YEAR);    
		m=cal.get(Calendar.MONTH)+1;    
		d=cal.get(Calendar.DATE);
		datestr=y+"年"+m+"月"+d+"日";  
		return datestr;
	}
	
	//获取总金额
	private Integer getFee()
	{
		Integer fee=0;
		for(EmResidencePermitInfoModel m:list)
		{
			if(m.getErpi_fee()!=null)
			{
				fee=fee+m.getErpi_fee();
			}
		}
		return fee;
	}

}
