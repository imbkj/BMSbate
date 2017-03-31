package Controller.EmCommissionOut;

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
import Model.EmCommissionOutChangeModel;
import Model.EmShebaoCardInfoModel;
import Model.EmbaseModel;
import Model.PubCodeConversionModel;
import bll.EmSheBaocard.EmShebaoCardInfoSelectBll;
import bll.Embase.EmbaseListBll;
import service.ExcelService;

public class wtchangeOutExcelImpl implements ExcelService{
	private String file;// excel文件对象
	private String filename;// sheet名称
	private List<EmCommissionOutChangeModel> modellist;

	public wtchangeOutExcelImpl(String file, String filename,
			List<EmCommissionOutChangeModel> secocList) {
		// TODO Auto-generated method stub
		this.file = file;
		this.filename = filename;
		this.modellist = secocList;
	}

	@Override
	public void writeExcel() throws Exception {
		EmbaseListBll bll=new EmbaseListBll();
		
		Workbook wk = Workbook.getWorkbook(new File(file));
		// 使用模板创建
		WritableWorkbook wbook = Workbook
				.createWorkbook(new File(filename), wk);
		WritableSheet sheet = wbook.getSheet(0);

		// 开始写入内容
		Integer row = 1, y = 1,num=modellist.size();
 
		for (int i = 0; i < modellist.size(); i++) {
			//公司编号	公司名称	变更类型	委托城市	员工姓名	身份证号码	社保基数	住房基数	住房个人比例	住房企业比例	养老起始时间	住房起始时间	客服
			// 用model获取每一行数据
			EmCommissionOutChangeModel m = modellist.get(i);
			// 将每列数据写入工作表中
			Label label = null;
			label = new Label(0, row,m.getCid().toString());//社保单位编号对应表格上面的部门
			sheet.addCell(label);
			label = new Label(1, row,m.getCoba_company().toString());
			sheet.addCell(label);
			label = new Label(2, row,m.getEcoc_addtype());
			sheet.addCell(label);
			label = new Label(3, row,m.getCity());
			sheet.addCell(label);
			label = new Label(4, row,m.getEmba_name());
			sheet.addCell(label);
			label = new Label(5, row,m.getEmba_idcard());
			sheet.addCell(label);
			label = new Label(6, row,m.getEcoc_sb_base().toString());
			sheet.addCell(label);
			label = new Label(7, row,m.getEcoc_house_base().toString());
			sheet.addCell(label);
			label = new Label(8, row,m.getZfeofc_op());
			sheet.addCell(label);
			label = new Label(9, row,m.getZfeofc_cp());
			sheet.addCell(label);
			label = new Label(10, row,m.getSbownmonth());
			sheet.addCell(label);
			label = new Label(11, row,m.getGjjownmonth());
			sheet.addCell(label);
			label = new Label(12, row,m.getEcoc_statestr());
			sheet.addCell(label);
			label = new Label(13, row,m.getCoba_client());
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
