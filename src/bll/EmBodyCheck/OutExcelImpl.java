package bll.EmBodyCheck;

import java.io.File;
import java.io.FileOutputStream;
import java.util.List;

import service.ExcelService;

import jxl.Workbook;
import jxl.format.Alignment;
import jxl.format.UnderlineStyle;
import jxl.format.VerticalAlignment;
import jxl.write.Label;
import jxl.write.WritableCellFormat;
import jxl.write.WritableFont;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;
import Model.EmBcItemGroupModel;
import Model.EmBodyCheckItemModel;
import Model.EmBodyCheckModel;

public class OutExcelImpl implements ExcelService {
	private File file;// excel文件对象
	private String sheetName;// sheet名称
	private String[] title;// 标题字符串
	private List<EmBodyCheckModel> bclist;// excel中的内容
	private EmBcInfo_SelectBll bll = new EmBcInfo_SelectBll();

	public OutExcelImpl(File file, String sheetName, String[] title,
			List<EmBodyCheckModel> lists) {
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
		WritableFont wf = new WritableFont(WritableFont.ARIAL, 12,
				WritableFont.BOLD, true, UnderlineStyle.NO_UNDERLINE);// 字体、粗体、斜体、下划线
		WritableCellFormat wcf = new WritableCellFormat(wf);
		wcf.setVerticalAlignment(VerticalAlignment.CENTRE);
		wcf.setAlignment(Alignment.CENTRE);
		// 行高设置行高,参数(行数,高度)
		sheet.setRowView(0, 500);
		// 开始写入第一行(即标题栏)
		for (int i = 0; i < title.length; i++) {

			// 用于写入文本内容到工作表中去
			Label label = null;
			// 在Label对象的构造中指明单元格位置(参数依次代表列数、行数、内容 )
			label = new Label(i, 0, title[i], wcf);
			// 将定义好的单元格添加到工作表中
			sheet.addCell(label);
		}
		// 开始写入内容
		for (int row = 1; row < bclist.size() + 1; row++) {
			// 用model获取每一行数据
			EmBodyCheckModel cop = bclist.get(row - 1);
			// 将每列数据写入工作表中
			Label label = null;
			label = new Label(0, row, row + "");
			sheet.addCell(label);

			label = new Label(1, row, cop.getCid().toString());
			sheet.addCell(label);
			String gid = "";
			if (cop.getGid() != null) {
				gid = cop.getGid().toString();
			}
			label = new Label(2, row, gid);
			sheet.addCell(label);
			label = new Label(3, row, cop.getEmbc_client());
			sheet.addCell(label);
			label = new Label(4, row, cop.getCoba_manager());
			sheet.addCell(label);
			label = new Label(5, row, cop.getEmbc_shortname());
			sheet.addCell(label);
			label = new Label(6, row, cop.getEmbc_name());
			sheet.addCell(label);
			label = new Label(7, row, cop.getEmbc_idcard());
			sheet.addCell(label);

			label = new Label(8, row, cop.getEbcl_bcid());
			sheet.addCell(label);

			label = new Label(9, row, cop.getEbcs_hospital());
			sheet.addCell(label);

			label = new Label(10, row, cop.getEbcl_itemsstr());
			sheet.addCell(label);
			String fee = "";
			if (cop.getEbcl_charge() != null) {
				fee = cop.getEbcl_charge().toString();
			}
			label = new Label(11, row, fee);
			sheet.addCell(label);

			String disfee = "";
			if (cop.getEbcl_discount() != null) {
				disfee = cop.getEbcl_discount().toString();
			} else {
				if (cop.getEbcl_charge() != null) {
					disfee = cop.getEbcl_charge().toString();
				}
			}
			label = new Label(12, row, disfee);
			sheet.addCell(label);
			label = new Label(13, row, cop.getEbcl_typename());
			sheet.addCell(label);
			String bookdate = cop.getEbcl_plandate() == null ? cop
					.getEbcl_bookdate() : cop.getEbcl_plandate();
			label = new Label(14, row, bookdate);
			sheet.addCell(label);
			label = new Label(15, row, cop.getEbcl_completedate());
			sheet.addCell(label);

			label = new Label(16, row, cop.getEbsa_address());
			sheet.addCell(label);
			label = new Label(17, row, cop.getEbcl_showclient());
			sheet.addCell(label);
			label = new Label(18, row, cop.getEbcl_clientshowdate());
			sheet.addCell(label);
			label = new Label(19, row, cop.getEbcl_balancedate());
			sheet.addCell(label);
			label = new Label(20, row, cop.getEmpType());
			sheet.addCell(label);
		}
		// 写入数据
		wwb.write();
		// 关闭文件
		wwb.close();

	}

	private String getLimit(String itemsId) {

		EmBodyCheckItemModel m = new EmBodyCheckItemModel();
		m.setIdList(itemsId);
		List<EmBodyCheckItemModel> list = bll.getGroupItemList(m);
		String limit = "";
		for (EmBodyCheckItemModel ml : list) {
			if (ml.getSex() != null && !ml.getSex().equals("无限制")) {
				limit = limit + ml.getSex() + ",";
			}
			if (ml.getMarry() != null && !ml.getMarry().equals("无限制")) {
				limit = limit + ml.getMarry() + ",";
			}
		}
		if (limit.length() > 0) {
			limit = limit.substring(0, limit.length() - 1);
		} else {
			limit = "无限制";
		}
		return limit;
	}

	@Override
	public void exportExcel() throws Exception {
		// TODO Auto-generated method stub

	}
}
