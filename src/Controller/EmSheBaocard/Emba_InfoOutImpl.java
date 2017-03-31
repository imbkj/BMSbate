package Controller.EmSheBaocard;

import java.io.File;
import java.io.FileOutputStream;
import java.util.List;

import org.jsoup.safety.Cleaner;

import bll.EmSheBaocard.EmShebaoCardInfoSelectBll;

import jxl.Workbook;
import jxl.format.Alignment;
import jxl.format.UnderlineStyle;
import jxl.format.VerticalAlignment;
import jxl.write.Label;
import jxl.write.WritableCellFormat;
import jxl.write.WritableFont;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;
import Model.EmHouseTakeCardInfoModel;
import Model.EmbaseModel;
import service.ExcelService;

public class Emba_InfoOutImpl implements ExcelService {
	private File file;// excel文件对象
	private String sheetName;// sheet名称
	private String[] title;// 标题字符串
	private List<EmbaseModel> bclist;// excel中的内容
	public Emba_InfoOutImpl(File file, String sheetName, String[] title,
			List<EmbaseModel> lists){
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
		for (int i = 0; i < title.length; i++) {//写入自定义的表头

			// 用于写入文本内容到工作表中去
			Label label = null;
			// 在Label对象的构造中指明单元格位置(参数依次代表列数、行数、内容 )
			label = new Label(i, 0, title[i], wcf);
			// 将定义好的单元格添加到工作表中
			sheet.addCell(label);
		}
		
		String[] title = { "序号", "公司编号", "公司名称", "员工姓名", "性别", "身份证号",
				"手机号码", "社保电脑号", "客服"};
		EmShebaoCardInfoSelectBll bll=new EmShebaoCardInfoSelectBll();
		// 开始写入内容
		for (int row = 1; row < bclist.size() + 1; row++) {
			// 用model获取每一行数据
			EmbaseModel cop = bclist.get(row - 1);
			// 将每列数据写入工作表中
			Label label = null;
			label = new Label(0, row, row+"");//第row行第0列
			sheet.addCell(label);
			String cid="";
			if(cop.getCid()!=null)
			{
				cid=cop.getCid().toString();
			}
			label = new Label(1, row,cid);//第row行第1列
			sheet.addCell(label);
			String company="";
			if(cop.getCoba_company()!=null)
			{
				company=cop.getCoba_company();
			}
			label = new Label(2, row, company);//第row行第2列
			sheet.addCell(label);
			String name="";
			if(cop.getEmba_name()!=null)
			{
				name=cop.getEmba_name();
			}
			label = new Label(3, row, name);//第row行第3列
			sheet.addCell(label);
			String sex="";
			if(cop.getEmba_sex()!=null)
			{
				sex=cop.getEmba_sex();
			}
			label = new Label(4, row, sex);
			sheet.addCell(label);
			String idcard="";
			if(cop.getEmba_idcard()!=null)
			{
				idcard=cop.getEmba_idcard();
			}
			label = new Label(5, row, idcard);
			sheet.addCell(label);
			String mobile="";
			if(cop.getEmba_mobile()!=null)
			{
				mobile=cop.getEmba_mobile();
			}
			label = new Label(6, row, mobile);
			sheet.addCell(label);
			String computerid="";
			if(cop.getEsiu_computerid()!=null)
			{
				computerid=cop.getEsiu_computerid();
			}
			label = new Label(7, row, computerid);
			sheet.addCell(label);
			String client="";
			if(cop.getCoba_client()!=null)
			{
				client=cop.getCoba_client();
			}
			label = new Label(8, row, client);
			sheet.addCell(label);
			if(cop.getSbcd_id()!=null&&!cop.getSbcd_id().equals(0))
			{
				
			}
		}
		// 写入数据
		wwb.write();
		// 关闭文件
		wwb.close();
		
	}
}
