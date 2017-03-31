package Controller.EmReg;

import java.io.File;
import java.util.List;

import jxl.Workbook;
import jxl.write.Label;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;
import service.ExcelService;

import Model.EmRegistrationInfoModel;

public class Emreg_StopExportExcelImpl implements ExcelService {
	private String file;// excel文件对象
	private String filename;// sheet名称
	private List<EmRegistrationInfoModel> list;
	
	public Emreg_StopExportExcelImpl(String file, String filename,
			List<EmRegistrationInfoModel> list)
	{
		this.file = file;
		this.filename = filename;
		this.list = list;
	}

	@Override
	public void writeExcel() throws Exception {
		// TODO Auto-generated method stub
		Workbook wk=Workbook.getWorkbook(new File(file));
        // 使用模板创建
     	WritableWorkbook wbook = Workbook.createWorkbook(new File(
     			 filename), wk);
        WritableSheet  sheet = wbook.getSheet(0);
		// 开始写入内容
		int row =5,y=1;
		for (int i=0; i < list.size(); i++) {
			// 用model获取每一行数据
			EmRegistrationInfoModel model = list.get(i);
			// 将每列数据写入工作表中
			Label label = null;
			label = new Label(0, row, model.getErin_idcard());
			sheet.addCell(label);
			label = new Label(1, row,model.getEmba_name());
			sheet.addCell(label);
			label = new Label(2, row, model.getErin_stop_reason());
			sheet.addCell(label);
			row++;
			y++;
		}
		
		Integer jrow=row+7;
		Label label = null;
		label = new Label(0, jrow,"劳动合同终止原因：  1：合同终止 2：单位原因解除 3：个人原因解除 4：修改个人信息");
		sheet.addCell(label);
		// 写入数据
		wbook.write();
		// 关闭文件
		wbook.close();
	}
	
	//把终止原因转换成代码
	private String getReasonId(String reason)
	{
		String reasonid="";
		if(reason!=null)
		{
			if(reason.equals("合同终止"))
			{
				reasonid="1";
			}
			else if(reason.equals("单位原因解除"))
			{
				reasonid="2";
			}
			else if(reason.equals("个人原因解除"))
			{
				reasonid="3";
			}
			else if(reason.equals("修改个人信息"))
			{
				reasonid="4";
			}
		}
		return reasonid;
		
	}
	
}
