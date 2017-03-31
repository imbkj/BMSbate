package Controller.EmCommissionOut;
import java.io.File;
import java.util.List;
import jxl.Workbook;
import jxl.write.Label;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;
import Model.EmCommissionOutChangeEntrustModel;
import service.ExcelService;

public class changeEntrustListOutExcelImpl implements ExcelService{
	private String file;// excel文件对象
	private String filename;// sheet名称
	private List<EmCommissionOutChangeEntrustModel> modellist;
    
	public changeEntrustListOutExcelImpl(String file, String filename,
			List<EmCommissionOutChangeEntrustModel> modellist) {
		this.file = file;
		this.filename = filename;
		this.modellist = modellist;
	}

	@Override
	public void writeExcel() throws Exception {
		Workbook wk = Workbook.getWorkbook(new File(file));
		// 使用模板创建
		WritableWorkbook wbook = Workbook
				.createWorkbook(new File(filename), wk);
		WritableSheet sheet = wbook.getSheet(0);

		// 开始写入内容
		Integer row = 1, y = 1,num=modellist.size();
 
		for (int i = 0; i < modellist.size(); i++) {
			// 用model获取每一行数据
			EmCommissionOutChangeEntrustModel m = modellist.get(i);
			// 将每列数据写入工作表中
			Label label = null;
			label = new Label(0, row,m.getCid().toString());
			sheet.addCell(label);
			label = new Label(1, row,m.getCoba_company());
			sheet.addCell(label);
			label = new Label(2, row,m.getGid().toString());
			sheet.addCell(label);
			label = new Label(3, row,m.getEmba_name());
			sheet.addCell(label);
			label = new Label(4, row,m.getEmba_idcard());
			sheet.addCell(label);
			label = new Label(5, row,m.getEcyc_sb_base().toString());
			sheet.addCell(label);
			label = new Label(6, row,m.getEcyc_house_cp());
			sheet.addCell(label);
			label = new Label(7, row,m.getEcyc_house_base().toString());
			sheet.addCell(label);
			label =new Label(8,row,m.getCoba_client());
			sheet.addCell(label);
			label = new Label(9, row,m.getEcyc_state());
			sheet.addCell(label);
			label = new Label(10, row,m.getCity());
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
