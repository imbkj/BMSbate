package Controller.EmCommercialInsurance;

import java.io.File;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import jxl.Workbook;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;

import org.zkoss.bind.annotation.BindingParam;
import org.zkoss.bind.annotation.Command;
import org.zkoss.bind.annotation.Init;
import org.zkoss.bind.annotation.NotifyChange;
import org.zkoss.zul.Combobox;
import org.zkoss.zul.Datebox;
import org.zkoss.zul.ListModelList;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Textbox;

import bll.CoLatencyClient.CoLatencyClient_AddBll;
import bll.EmCommercialInsurance.CI_Insurant_ListBll;
import bll.EmCommercialInsurance.CI_Insurant_OperateBll;

import Model.CI_Insurant_ListModel;
import Util.FileOperate;

public class CI_InsurantCheck_Controller {

	private ListModelList<CI_Insurant_ListModel> castsortlist;// 显示保险类型

	CI_Insurant_ListBll bll = new CI_Insurant_ListBll();

	private CI_Insurant_OperateBll ccsaBll = new CI_Insurant_OperateBll();

	private ListModelList<CI_Insurant_ListModel> ci_insurant_chlist;// 显示商保查询
	
	private ListModelList<CI_Insurant_ListModel> castsortdatelist;// 显示保险审核时间
	
	private ListModelList<CI_Insurant_ListModel> ci_zc_excel;// 导出excel商保在保数据
	
	// 查询客服
		CoLatencyClient_AddBll blllogin = new CoLatencyClient_AddBll();
		List<String> loginlist = blllogin.getLoginInfo();

	@Init
	public void init() throws SQLException {
		castsortlist = new ListModelList<CI_Insurant_ListModel>(
				bll.castsortlist());// 显示保险类型
		
		castsortdatelist = new ListModelList<CI_Insurant_ListModel>(
				bll.castsortdatelist());// 显示保险类型
	}

	// 商保审核查询
	@Command("ch_search")
	@NotifyChange("ci_insurant_chlist")
	public void ch_search(@BindingParam("castsort") Combobox castsort,
			@BindingParam("change") Combobox change,
			@BindingParam("state") Combobox state,
			@BindingParam("bm") Combobox bm, @BindingParam("bx") Combobox bx,
			@BindingParam("sb_date") Combobox sb_date,
			@BindingParam("name") Textbox name,
			@BindingParam("client") Combobox client) throws Exception {
		String da_date = "";
		if (sb_date.getValue() != null) {
			da_date = sb_date.getValue();
		}

		ci_insurant_chlist = new ListModelList<CI_Insurant_ListModel>(
				bll.ci_insurant_chlist(castsort.getValue(), change.getValue(),
						state.getValue(), bm.getValue(), bx.getValue(),
						da_date, name.getValue(), client.getValue()));// 显示查询信息
	}

	// 导出在册数据报表
	@Command("ch_down")
	public void ch_down(@BindingParam("castsort") Combobox castsort,
			@BindingParam("change") Combobox change,
			@BindingParam("state") Combobox state,
			@BindingParam("bm") Combobox bm, @BindingParam("bx") Combobox bx,
			@BindingParam("sb_date") Combobox sb_date,
			@BindingParam("name") Textbox name,
			@BindingParam("client") Combobox client) throws Exception {

		String da_date = "";
		if (sb_date.getValue() != null) {
			da_date = sb_date.getValue();
		}
		ci_zc_excel = new ListModelList<CI_Insurant_ListModel>(bll.ci_zc_excel(
				castsort.getValue(), change.getValue(), state.getValue(),
				bm.getValue(), bx.getValue(), da_date, name.getValue(),
				client.getValue()));// 显示保险在保类型

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
				+ "EmCommercialInsurance/File/Templet/sy_zc.xls"));
		// 使用模板创建
		WritableWorkbook wwb = Workbook.createWorkbook(new File(absolutePath
				+ "EmCommercialInsurance/File/Download/Em_in/" + filename), wb);
		// 生成工作表
		WritableSheet sheet = wwb.getSheet(0);
		// Label label1 = null;
		int rowdata = 0;
		String pid = "";
		for (int i = 0; i < ci_zc_excel.getSize(); i++) {

			rowdata = rowdata + 1;

			pid = String.valueOf(rowdata);
			jxl.write.Label label1 = new jxl.write.Label(0, rowdata, pid);
			sheet.addCell(label1);
			jxl.write.Label label4 = new jxl.write.Label(1, rowdata,
					ci_zc_excel.get(i).getEcin_insurant());
			sheet.addCell(label4);
			jxl.write.Label label8 = new jxl.write.Label(2, rowdata,
					ci_zc_excel.get(i).getEcin_idcard());
			sheet.addCell(label8);
			jxl.write.Label label9 = new jxl.write.Label(3, rowdata,
					ci_zc_excel.get(i).getEcin_sex());
			sheet.addCell(label9);
			jxl.write.Label label10 = new jxl.write.Label(4, rowdata,
					ci_zc_excel.get(i).getEcin_birthday());
			sheet.addCell(label10);
			jxl.write.Label label111 = new jxl.write.Label(5, rowdata,
					ci_zc_excel.get(i).getEcin_work_st());
			sheet.addCell(label111);
			jxl.write.Label label6 = new jxl.write.Label(6, rowdata,
					ci_zc_excel.get(i).getEcin_sconnection());
			sheet.addCell(label6);
			jxl.write.Label label5 = new jxl.write.Label(7, rowdata,
					ci_zc_excel.get(i).getEcin_insurer());
			sheet.addCell(label5);
			jxl.write.Label label15 = new jxl.write.Label(8, rowdata,
					ci_zc_excel.get(i).getEcin_zidcard());
			sheet.addCell(label15);
			jxl.write.Label label14 = new jxl.write.Label(9, rowdata,
					ci_zc_excel.get(i).getEcin_in_date());
			sheet.addCell(label14);
			jxl.write.Label label25 = new jxl.write.Label(10, rowdata,
					ci_zc_excel.get(i).getEcin_st_date());
			sheet.addCell(label25);
			jxl.write.Label label26 = new jxl.write.Label(11, rowdata, "");
			sheet.addCell(label26);
			jxl.write.Label label27 = new jxl.write.Label(12, rowdata, "");
			sheet.addCell(label27);
			jxl.write.Label label217 = new jxl.write.Label(13, rowdata, "");
			sheet.addCell(label217);
			jxl.write.Label label11 = new jxl.write.Label(14, rowdata,
					ci_zc_excel.get(i).getEcin_castsort());
			sheet.addCell(label11);
			jxl.write.Label label12 = new jxl.write.Label(15, rowdata,
					ci_zc_excel.get(i).getEcin_buy_count());
			sheet.addCell(label12);
			jxl.write.Label label18 = new jxl.write.Label(16, rowdata,
					ci_zc_excel.get(i).getEcin_state());
			sheet.addCell(label18);
			jxl.write.Label label29 = new jxl.write.Label(17, rowdata, "");
			sheet.addCell(label29);
			jxl.write.Label label30 = new jxl.write.Label(18, rowdata, "");
			sheet.addCell(label30);
			jxl.write.Label label7 = new jxl.write.Label(19, rowdata,
					ci_zc_excel.get(i).getEcin_company());
			sheet.addCell(label7);
			jxl.write.Label label19 = new jxl.write.Label(20, rowdata,
					ci_zc_excel.get(i).getEcin_client());
			sheet.addCell(label19);
			jxl.write.Label label2 = new jxl.write.Label(21, rowdata,
					ci_zc_excel.get(i).getGid());
			sheet.addCell(label2);
			jxl.write.Label label3 = new jxl.write.Label(22, rowdata,
					ci_zc_excel.get(i).getCid());
			sheet.addCell(label3);
			jxl.write.Label label32 = new jxl.write.Label(23, rowdata,
					ci_zc_excel.get(i).getEcin_cl_count());
			sheet.addCell(label32);
			jxl.write.Label label312 = new jxl.write.Label(24, rowdata,
					ci_zc_excel.get(i).getEcin_in_date());
			sheet.addCell(label312);
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
		// Executions.sendRedirect("CI_Insurant_Check.zul");

	}

	public ListModelList<CI_Insurant_ListModel> getCastsortlist() {
		return castsortlist;
	}

	public void setCastsortlist(ListModelList<CI_Insurant_ListModel> castsortlist) {
		this.castsortlist = castsortlist;
	}

	public ListModelList<CI_Insurant_ListModel> getCi_insurant_chlist() {
		return ci_insurant_chlist;
	}

	public void setCi_insurant_chlist(
			ListModelList<CI_Insurant_ListModel> ci_insurant_chlist) {
		this.ci_insurant_chlist = ci_insurant_chlist;
	}

	public List<String> getLoginlist() {
		return loginlist;
	}

	public void setLoginlist(List<String> loginlist) {
		this.loginlist = loginlist;
	}

	public ListModelList<CI_Insurant_ListModel> getCastsortdatelist() {
		return castsortdatelist;
	}

	public void setCastsortdatelist(
			ListModelList<CI_Insurant_ListModel> castsortdatelist) {
		this.castsortdatelist = castsortdatelist;
	}
	
	
}
