package Controller.CoBase;

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
import Model.CoBaseModel;
import Model.EmShebaoCardInfoModel;
import Model.EmbaseModel;
import Model.PubCodeConversionModel;
import bll.EmSheBaocard.EmShebaoCardInfoSelectBll;
import bll.Embase.EmbaseListBll;
import service.ExcelService;

public class EmBaseOutExcelImpl implements ExcelService{
	private String file;// excel文件对象
	private String filename;// sheet名称
	private CoBaseModel model;

	public EmBaseOutExcelImpl(String file, String filename,
			CoBaseModel model) {
		// TODO Auto-generated method stub
		this.file = file;
		this.filename = filename;
		this.model = model;
	}

	@Override
	public void writeExcel() throws Exception {
		EmbaseListBll bll=new EmbaseListBll();
		List<EmbaseModel> list=bll.getEmBaseListByCid(model.getCid());
		Workbook wk = Workbook.getWorkbook(new File(file));
		// 使用模板创建
		WritableWorkbook wbook = Workbook
				.createWorkbook(new File(filename), wk);
		WritableSheet sheet = wbook.getSheet(0);

		// 开始写入内容
		Integer row = 3, y = 1,num=list.size();
		Label firstlabel = null;
		firstlabel = new Label(1, 0, num.toString());
		sheet.addCell(firstlabel);
		firstlabel = new Label(3, 0, model.getCid().toString());
		sheet.addCell(firstlabel);
		
		firstlabel = new Label(5, 0, model.getCoba_company());
		sheet.addCell(firstlabel);
		
		for (int i = 0; i < list.size(); i++) {
			// 用model获取每一行数据
			EmbaseModel m = list.get(i);
			// 将每列数据写入工作表中
			Label label = null;
			label = new Label(0, row, y + "");
			sheet.addCell(label);
			
			label = new Label(1, row,m.getGid().toString());//社保单位编号对应表格上面的部门
			sheet.addCell(label);
			label = new Label(2, row,m.getEmba_name());
			sheet.addCell(label);
			label = new Label(3, row,m.getEmba_idcard());
			sheet.addCell(label);
			label = new Label(4, row,m.getEmba_sex());
			sheet.addCell(label);
			label = new Label(5, row,m.getEmba_birth());
			sheet.addCell(label);
			label = new Label(6, row,m.getEsiu_hj());
			sheet.addCell(label);
			label = new Label(7, row,m.getEmba_mobile());
			sheet.addCell(label);

			label = new Label(8, row,m.getEmba_phone());
			sheet.addCell(label);
			label = new Label(9, row,m.getEmba_email());
			sheet.addCell(label);
			label = new Label(10, row,m.getEmba_address());
			sheet.addCell(label);
			label = new Label(11, row,m.getEmba_indate());
			sheet.addCell(label);
			label = new Label(12, row,m.getEsiu_computerid());
			sheet.addCell(label);
			String esiu_radix="";
			if(m.getEsiu_radix()!=null)
			{
				esiu_radix=m.getEsiu_radix().toString();
			}
			label = new Label(13, row,esiu_radix);
			sheet.addCell(label);
			label = new Label(14, row,m.getEsiu_yl());
			sheet.addCell(label);
			label = new Label(15, row,m.getEsiu_yltype());
			sheet.addCell(label);
			
			label = new Label(16, row,m.getEsiu_gs());
			sheet.addCell(label);
			label = new Label(17, row,m.getEsiu_sye());
			sheet.addCell(label);
			
			label = new Label(18, row,m.getEsiu_syu());
			sheet.addCell(label);
			
			String emhu_radix="";
			if(m.getEmhu_radix()!=null)
			{
				emhu_radix=m.getEmhu_radix().toString();
			}
			label = new Label(19,row,emhu_radix);
			sheet.addCell(label);
			
			String bonus="";
			if(m.getEmhu_bonus()!=null)
			{
				bonus=m.getEmhu_bonus().toString();
			}
			label = new Label(20, row,bonus);
			sheet.addCell(label);
			
			String cpp="";
			if(m.getEmhu_cpp()!=null)
			{
				cpp=m.getEmhu_cpp().toString();
			}
			label = new Label(21,row, cpp);
			sheet.addCell(label);
			
			String opp="";
			if(m.getEmhu_opp()!=null)
			{
				opp=m.getEmhu_opp().toString();
			}
			label = new Label(22, row,opp);
			sheet.addCell(label);
			
			//劳动合同起始日
			label = new Label(23, row,m.getEbco_incept_date());
			sheet.addCell(label);
			//劳动合同到期日
			label = new Label(24, row,m.getEbco_maturity_date());
			sheet.addCell(label);
			//试用期起始日
			label = new Label(25, row,m.getEbco_probation_incept());
			sheet.addCell(label);
			label = new Label(26, row,m.getEbco_probation_mdate());
			sheet.addCell(label);
			label = new Label(27, row,m.getEmba_gz_bank());
			sheet.addCell(label);
			label = new Label(28, row,m.getEmba_gz_account());
			sheet.addCell(label);
			
			label = new Label(29, row,m.getEmba_house_bank());
			sheet.addCell(label);
			label = new Label(30, row,m.getEmba_house_account());
			sheet.addCell(label);
			label = new Label(31, row,m.getEmba_sy_bank());
			sheet.addCell(label);
			
			label = new Label(32, row,m.getEmba_sy_account());
			sheet.addCell(label);
			label = new Label(33, row,m.getEmba_writeoff_bank());
			sheet.addCell(label);
			label = new Label(34, row,m.getEmba_writeoff_account());
			sheet.addCell(label);
			label = new Label(35, row,m.getEmba_school());
			sheet.addCell(label);
			label = new Label(36, row,m.getEmba_education());
			sheet.addCell(label);
			label = new Label(37, row,m.getEbco_working_station());
			sheet.addCell(label);
			
			label = new Label(38, row,m.getEmhu_houseid());
			sheet.addCell(label);
			label = new Label(39, row,m.getEmba_hjadd());
			sheet.addCell(label);
			label = new Label(40, row,m.getEmba_folk());
			sheet.addCell(label);
			label = new Label(41, row,m.getEmba_privateemail());
			sheet.addCell(label);
			label = new Label(42, row,m.getEmba_wt());
			sheet.addCell(label);
			row++;
			y++;
		}

		// 写入数据
		wbook.write();
		// 关闭文件
		wbook.close();
	}
}
