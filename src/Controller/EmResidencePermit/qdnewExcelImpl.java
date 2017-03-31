package Controller.EmResidencePermit;

import java.io.File;
import java.util.Calendar;
import java.util.List;

import bll.EmResidencePermit.Emrp_FeeSelectBll;

import jxl.Workbook;
import jxl.write.Label;
import jxl.write.WritableCell;
import jxl.write.WritableCellFormat;
import jxl.write.WritableFont;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;
import Model.EmResidencePermitInfoModel;
import Util.UserInfo;
import service.ExcelService;

public class qdnewExcelImpl implements ExcelService {
	private String file;// excel文件对象
	private String filename;// sheet名称
	private String number = "";
	private List<EmResidencePermitInfoModel> list;

	public qdnewExcelImpl(String file, String filename,
			List<EmResidencePermitInfoModel> list, String number) {
		// TODO Auto-generated method stub
		this.file = file;
		this.filename = filename;
		this.list = list;
		this.number = number;
	}

	@Override
	public void writeExcel() throws Exception {
		Workbook wk = Workbook.getWorkbook(new File(file));
		// 使用模板创建
		WritableWorkbook wbook = Workbook
				.createWorkbook(new File(filename), wk);
		WritableSheet sheet = wbook.getSheet(0);
		WritableFont titleWf = new WritableFont(WritableFont.createFont("宋体"),
				12, WritableFont.BOLD);
		WritableCellFormat wcf = new WritableCellFormat(titleWf);
		Label labels = new Label(0, 2, getDatestr() + "费用明细");
		sheet.addCell(labels);
		WritableCell cell = sheet.getWritableCell(0, 2);
		cell.setCellFormat(wcf);
		Label labelnum = new Label(0, 3, "单号：" + number);
		sheet.addCell(labelnum);
		WritableCell cl = sheet.getWritableCell(0, 3);
		cl.setCellFormat(wcf);
		Label labelgr = new Label(0, 7, "①个人付为" + getFee("个人付") + "元");
		sheet.addCell(labelgr);
		Label labegs = new Label(0, 8, "②公司付为" + getFee("公司付") + "元");
		sheet.addCell(labegs);
		Label labelsf = new Label(0, 9, "③随付款为" + getFee("随付款") + "元");
		sheet.addCell(labelsf);
		Label labelzz = new Label(0, 10, "④中智付为" + getFee("中智付") + "元");
		sheet.addCell(labelzz);
		Label labeldf = new Label(0, 11, getdf()+"元由____________申请垫付");
		sheet.addCell(labeldf);
		Label labeljb = new Label(0, 12,"福利经办人："+getname()+"     客服经办人："+UserInfo.getUsername()+"     审批人____________");
		sheet.addCell(labeljb);
		
		Label label15 = new Label(0, 15, getDatestr() + "费用明细");
		sheet.addCell(label15);
		WritableCell cell15 = sheet.getWritableCell(0, 15);
		cell15.setCellFormat(wcf);
		Label label16 = new Label(0, 3, "单号：" + number);
		sheet.addCell(label16);
		WritableCell cl16 = sheet.getWritableCell(0, 3);
		cl16.setCellFormat(wcf);
		
		Label label17 = new Label(0, 17,"(备注：个人付"+getFee("个人付")+"元；公司付"+getFee("公司付")+"元；随付款"+getFee("随付款")+"元；中智付"+getFee("中智付")+"元。)");
		sheet.addCell(label17);
		
		// 开始写入内容
		int row = 19, y = 1;
		for (int i = 0; i < list.size(); i++) {
			// 用model获取每一行数据
			EmResidencePermitInfoModel model = list.get(i);
			// 将每列数据写入工作表中
			Label label = null;
			label = new Label(0, row, y + "");
			sheet.addCell(label);
			label = new Label(1, row, model.getCoba_client());
			sheet.addCell(label);
			label = new Label(2, row, model.getCoba_shortname());
			sheet.addCell(label);
			label = new Label(3, row, model.getEmba_name());
			sheet.addCell(label);
			label = new Label(4, row, model.getErpi_payment_kind());
			sheet.addCell(label);
			String fee = "";
			if (model.getErpi_fee() != null) {
				fee = model.getErpi_fee() + "";
			}
			label = new Label(5, row, fee);
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

	// 获取当前日期
	private String getDatestr() {
		String datestr = "";
		int y, m, d;
		Calendar cal = Calendar.getInstance();
		y = cal.get(Calendar.YEAR);
		m = cal.get(Calendar.MONTH) + 1;
		d = cal.get(Calendar.DATE);
		datestr = y + "年" + m + "月" + d + "日";
		return datestr;
	}

	// 获取总金额
	private Integer getFee(String paykind) {
		Integer fee = 0;
		for (EmResidencePermitInfoModel m : list) {
			if (m.getErpi_fee() != null) {
				if (m.getErpi_payment_kind() != null
						&& m.getErpi_payment_kind().equals("paykind")) {
					fee = fee + m.getErpi_fee();
				}
			}
		}
		return fee;
	}

	// 获取垫付款
	private Integer getdf() {
		Integer df = 0;
		for (EmResidencePermitInfoModel m : list) {
			if (m.getErpi_payment_kind() != null) {
				if (m.getErpi_payment_kind().equals("公司付")
						|| m.getErpi_payment_kind().equals("随付款")) {
					if (m.getErpi_payment_state() != null
							|| m.getErpi_payment_kind().equals("未付")) {
						df = df + m.getErpi_fee();
					}
				}
			}
		}
		return df;
	}
	private String getname()
	{
		Emrp_FeeSelectBll bll=new Emrp_FeeSelectBll();
		return bll.getWDPeople();
	}
}
