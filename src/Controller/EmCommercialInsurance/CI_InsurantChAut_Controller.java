package Controller.EmCommercialInsurance;

import java.io.File;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Date;

import jxl.Workbook;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;

import org.zkoss.bind.annotation.BindingParam;
import org.zkoss.bind.annotation.Command;
import org.zkoss.bind.annotation.Init;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zul.Checkbox;
import org.zkoss.zul.Grid;
import org.zkoss.zul.Label;
import org.zkoss.zul.ListModelList;
import org.zkoss.zul.Messagebox;

import bll.EmCommercialInsurance.CI_Insurant_ListBll;
import bll.EmCommercialInsurance.CI_Insurant_OperateBll;

import Model.CI_Insurant_ListModel;
import Util.FileOperate;

public class CI_InsurantChAut_Controller {
	private ListModelList<CI_Insurant_ListModel> ci_insurant_bclist;// 显示商保未审核数据
	CI_Insurant_ListBll bll = new CI_Insurant_ListBll();
	private CI_Insurant_OperateBll ccsaBll = new CI_Insurant_OperateBll();
	private ListModelList<CI_Insurant_ListModel> bci_excel;// 导出excel商保信息变更数据
	
	@Init
	public void init() throws SQLException {
		ci_insurant_bclist = new ListModelList<CI_Insurant_ListModel>(
				bll.ci_insurant_bchlist());// 显示信息变更未审核列表
	}
	
	// 导出报表
		@Command("ci_insurant_bcdown")
		public void ci_insurant_bcdown(@BindingParam("gridco") Grid gridco)
				throws Exception {

			String filename = ""; // 文件名
			String absolutePath; // 服务器地址
			String filetype = ".xls"; // 文件类型
			SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
			Date date = new Date();
			String nowtime = sdf.format(date);

			filename = nowtime + filetype;
			absolutePath = FileOperate.getAbsolutePath();

			// 创建exce
			// 读取Excel模板
			Workbook wb = Workbook.getWorkbook(new File(absolutePath
					+ "EmCommercialInsurance/File/Templet/sy_sb_change.xls"));
			// 使用模板创建
			WritableWorkbook wwb = Workbook.createWorkbook(new File(absolutePath
					+ "EmCommercialInsurance/File/Download/Em_in/" + filename), wb);
			// 生成工作表
			WritableSheet sheet = wwb.getSheet(0);
			// Label label1 = null;
			int rowdata = 2;
			String pid = "";
			for (int i = 0; i < gridco.getRows().getChildren().size(); i++) {
				Checkbox chk = (Checkbox) gridco.getCell(i, 11).getChildren()
						.get(0);

				Label tapr_id = (Label) gridco.getCell(i, 12).getChildren().get(0);

				if (chk.isChecked()) {
					String[] message = ccsaBll.aut_bcinsurant((int) chk.getValue(),
							Integer.parseInt(tapr_id.getValue().toString()));

					if (i < gridco.getRows().getChildren().size())
						bci_excel = new ListModelList<CI_Insurant_ListModel>(
								bll.bci_excel(chk.getValue().toString()));// 显示保险在保类型
					rowdata = rowdata + 1;

					pid = String.valueOf(rowdata);
					jxl.write.Label label1 = new jxl.write.Label(0, rowdata, pid);
					sheet.addCell(label1);
					jxl.write.Label label4 = new jxl.write.Label(1, rowdata,
							bci_excel.get(0).getEcin_insurant());
					sheet.addCell(label4);
					jxl.write.Label label8 = new jxl.write.Label(2, rowdata,
							bci_excel.get(0).getEcin_idcard());
					sheet.addCell(label8);
					jxl.write.Label label9 = new jxl.write.Label(3, rowdata,
							bci_excel.get(0).getEcin_sex());
					sheet.addCell(label9);
					jxl.write.Label label10 = new jxl.write.Label(4, rowdata,
							bci_excel.get(0).getEcin_birthday());
					sheet.addCell(label10);
					jxl.write.Label label6 = new jxl.write.Label(5, rowdata,
							bci_excel.get(0).getEcin_sconnection());
					sheet.addCell(label6);
					jxl.write.Label label5 = new jxl.write.Label(6, rowdata,
							bci_excel.get(0).getEcin_insurer());
					sheet.addCell(label5);
					jxl.write.Label label15 = new jxl.write.Label(7, rowdata,
							bci_excel.get(0).getEcin_zidcard());
					sheet.addCell(label15);
					jxl.write.Label label14 = new jxl.write.Label(15, rowdata,
							bci_excel.get(0).getEcin_in_date());
					sheet.addCell(label14);
					jxl.write.Label label11 = new jxl.write.Label(16, rowdata,
							bci_excel.get(0).getEcin_castsort());
					sheet.addCell(label11);
					jxl.write.Label label18 = new jxl.write.Label(9, rowdata,
							bci_excel.get(0).getEcin_state());
					sheet.addCell(label18);
					jxl.write.Label label29 = new jxl.write.Label(10, rowdata,
							bci_excel.get(0).getEcin_state2());
					sheet.addCell(label29);
					jxl.write.Label label7 = new jxl.write.Label(13, rowdata,
							bci_excel.get(0).getEcin_company());
					sheet.addCell(label7);
					jxl.write.Label label19 = new jxl.write.Label(14, rowdata,
							bci_excel.get(0).getEcin_client());
					sheet.addCell(label19);
					jxl.write.Label label312 = new jxl.write.Label(8, rowdata,
							bci_excel.get(0).getEcin_remark());
					sheet.addCell(label312);
				}
			}

			// 写入数据
			wwb.write();
			// 关闭文件
			wwb.close();

			FileOperate.download("EmCommercialInsurance/File/Download/Em_in/"
					+ filename);
			// 弹出提示
			Messagebox.show("操作成功！", "操作提示",
					new Messagebox.Button[] { Messagebox.Button.OK },
					Messagebox.INFORMATION, null);
			Executions.sendRedirect("CI_Insurant_ChAut.zul");

		}
		
		@Command("bcheckall")
		public void bcheckall(@BindingParam("a") boolean ck,
				@BindingParam("b") Grid gd) {
			try {
				for (int i = 0; i < gd.getRows().getChildren().size(); i++) {
					Checkbox ckb = (Checkbox) gd.getCell(i, 11).getChildren()
							.get(0);
					ckb.setChecked(ck);
				}
			} catch (Exception e) {
				// TODO: handle exception
			}
		}

		public ListModelList<CI_Insurant_ListModel> getCi_insurant_bclist() {
			return ci_insurant_bclist;
		}

		public void setCi_insurant_bclist(
				ListModelList<CI_Insurant_ListModel> ci_insurant_bclist) {
			this.ci_insurant_bclist = ci_insurant_bclist;
		}
		
		
}
