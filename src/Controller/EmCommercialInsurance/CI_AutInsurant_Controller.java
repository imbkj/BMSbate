package Controller.EmCommercialInsurance;

import impl.MessageImpl;
import impl.MessageImplProxy;

import java.io.File;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import jxl.Workbook;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;

import org.zkoss.bind.annotation.BindingParam;
import org.zkoss.bind.annotation.Command;
import org.zkoss.bind.annotation.Init;
import org.zkoss.bind.annotation.NotifyChange;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zul.Checkbox;
import org.zkoss.zul.Combobox;
import org.zkoss.zul.Grid;
import org.zkoss.zul.Label;
import org.zkoss.zul.ListModelList;
import org.zkoss.zul.Messagebox;

import service.MessageService;

import dal.LoginDal;
import dal.EmCommercialInsurance.CI_Insurant_ListDal;
import dal.EmCommissionOut.EmCommissionOut_OperateDal;

import bll.EmCommercialInsurance.CI_Insurant_ListBll;
import bll.EmCommercialInsurance.CI_Insurant_OperateBll;
import bll.Taskflow.Task_controlBll;

import Model.CI_Insurant_ListModel;
import Model.EmComInsuranceChangeModel;
import Model.EmCommissionOutChangeModel;
import Model.SysMessageModel;
import Util.FileOperate;
import Util.RedirectUtil;
import Util.UserInfo;

import Util.UserInfo;
import bll.Taskflow.Task_controlBll;

public class CI_AutInsurant_Controller {
	private ListModelList<CI_Insurant_ListModel> castsortlist;// 显示保险类型

	private ListModelList<CI_Insurant_ListModel> ci_insurant_list;// 显示商保未审核数据

	private ListModelList<CI_Insurant_ListModel> ci_excel;// 导出excel商保数据
	
	private ListModelList<CI_Insurant_ListModel> sign_state;// 签收
	
	private ListModelList<CI_Insurant_ListModel> tali_list;// 显示所选商保连带人类型
	
	private ListModelList<CI_Insurant_ListModel> email_list;// 获取客服邮箱
	
	private MessageService serviceProxy;
	private MessageService msgservice;
	
	private Task_controlBll tbll;

	CI_Insurant_ListBll bll = new CI_Insurant_ListBll();

	private CI_Insurant_OperateBll ccsaBll = new CI_Insurant_OperateBll();

	@Init
	public void init() throws SQLException {
		castsortlist = new ListModelList<CI_Insurant_ListModel>(
				bll.castsortlist());// 显示保险类型

		ci_insurant_list = new ListModelList<CI_Insurant_ListModel>(
				bll.ci_insurant_list(""));// 显示未审核列表
	}

	// 导出报表
	@Command("ci_insurant_down")
	public void ci_insurant_down(@BindingParam("gridco") Grid gridco)
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
				+ "EmCommercialInsurance/File/Templet/sy_sb.xls"));
		// 使用模板创建
		WritableWorkbook wwb = Workbook.createWorkbook(new File(absolutePath
				+ "EmCommercialInsurance/File/Download/Em_in/" + filename), wb);
		// 生成工作表
		WritableSheet sheet = wwb.getSheet(0);
		// Label label1 = null;
		int rowdata = 3;
		String pid = "";
		
		
		for (int i = 0; i < gridco.getRows().getChildren().size(); i++) {
			try {
				System.out.println("aaaaaaa");
				System.out.println(gridco.getRows().getChildren().size());
				Checkbox chk = (Checkbox) gridco.getCell(i, 15).getChildren()
						.get(0);

				Label tapr_id = (Label) gridco.getCell(i, 16).getChildren()
						.get(0);
				
				System.out.println("bbbbbb");
				System.out.println(chk.isChecked());

				if (chk.isChecked()) {

					String[] message = ccsaBll.aut_insurant(
							(int) chk.getValue(),
							Integer.parseInt(tapr_id.getValue().toString()));
					if (i < gridco.getRows().getChildren().size())
						ci_excel = new ListModelList<CI_Insurant_ListModel>(
								bll.ci_excel(chk.getValue().toString()));// 显示保险在保类型
					rowdata = rowdata + 1;
				
					pid = String.valueOf(rowdata - 3);
					jxl.write.Label label1 = new jxl.write.Label(0, rowdata,
							pid);
					sheet.addCell(label1);
					jxl.write.Label label4 = new jxl.write.Label(1, rowdata,
							ci_excel.get(0).getEcin_insurant());
					sheet.addCell(label4);
					jxl.write.Label label8 = new jxl.write.Label(2, rowdata,
							ci_excel.get(0).getEcin_idcard());
					sheet.addCell(label8);
					jxl.write.Label label9 = new jxl.write.Label(3, rowdata,
							ci_excel.get(0).getEcin_sex());
					sheet.addCell(label9);
					jxl.write.Label label10 = new jxl.write.Label(4, rowdata,
							ci_excel.get(0).getEcin_birthday());
					sheet.addCell(label10);
					jxl.write.Label label111 = new jxl.write.Label(5, rowdata,
							ci_excel.get(0).getEcin_work_st());
					sheet.addCell(label111);
					jxl.write.Label label6 = new jxl.write.Label(6, rowdata,
							ci_excel.get(0).getEcin_sconnection());
					sheet.addCell(label6);
					jxl.write.Label label5 = new jxl.write.Label(7, rowdata,
							ci_excel.get(0).getEcin_insurer());
					sheet.addCell(label5);
					jxl.write.Label label15 = new jxl.write.Label(8, rowdata,
							ci_excel.get(0).getEcin_zidcard());
					sheet.addCell(label15);

					jxl.write.Label label14 = new jxl.write.Label(9, rowdata,
							ci_excel.get(0).getEcin_in_date());
					sheet.addCell(label14);

					jxl.write.Label label25 = new jxl.write.Label(10, rowdata,
							ci_excel.get(0).getEcin_st_date());
					sheet.addCell(label25);

					jxl.write.Label label26 = new jxl.write.Label(11, rowdata,
							"");
					sheet.addCell(label26);

					jxl.write.Label label27 = new jxl.write.Label(12, rowdata,
							"");
					sheet.addCell(label27);

					jxl.write.Label label217 = new jxl.write.Label(13, rowdata,
							"");
					sheet.addCell(label217);

					jxl.write.Label label11 = new jxl.write.Label(14, rowdata,
							ci_excel.get(0).getEcin_castsort());
					sheet.addCell(label11);
					jxl.write.Label label12 = new jxl.write.Label(15, rowdata,
							ci_excel.get(0).getEcin_buy_count());
					sheet.addCell(label12);
					jxl.write.Label label18 = new jxl.write.Label(16, rowdata,
							ci_excel.get(0).getEcin_state());
					sheet.addCell(label18);
					jxl.write.Label label29 = new jxl.write.Label(17, rowdata,
							"");
					sheet.addCell(label29);
					jxl.write.Label label30 = new jxl.write.Label(18, rowdata,
							"");
					sheet.addCell(label30);
					jxl.write.Label label7 = new jxl.write.Label(19, rowdata,
							ci_excel.get(0).getEcin_company());
					sheet.addCell(label7);
					jxl.write.Label label19 = new jxl.write.Label(20, rowdata,
							ci_excel.get(0).getEcin_client());
					sheet.addCell(label19);
					jxl.write.Label label2 = new jxl.write.Label(21, rowdata,
							ci_excel.get(0).getGid());
					sheet.addCell(label2);
					jxl.write.Label label3 = new jxl.write.Label(22, rowdata,
							ci_excel.get(0).getCid());
					sheet.addCell(label3);
					jxl.write.Label label32 = new jxl.write.Label(23, rowdata,
							ci_excel.get(0).getEcin_cl_count());
					sheet.addCell(label32);
					jxl.write.Label label312 = new jxl.write.Label(24, rowdata,
							ci_excel.get(0).getEcin_in_date());
					sheet.addCell(label312);

					jxl.write.Label label313 = new jxl.write.Label(25, rowdata,
							ci_excel.get(0).getEcin_work_city());
					sheet.addCell(label313);
					
					jxl.write.Label label314 = new jxl.write.Label(26, rowdata,
							ci_excel.get(0).getEcin_state2());
					sheet.addCell(label314);
				}
			} catch (Exception e) {
				// TODO: handle exception
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
		Executions.sendRedirect("CI_Insurant_Aut.zul");

	}

	@Command("checkall")
	public void checkall(@BindingParam("a") boolean ck,
			@BindingParam("b") Grid gd) {
		for (int i = 0; i < gd.getRows().getChildren().size(); i++) {
			try {
				Checkbox ckb = (Checkbox) gd.getCell(i, 15).getChildren()
						.get(0);
				ckb.setChecked(ck);
			} catch (Exception e) {
				// TODO: handle exception
			}
		}
	}
	
	@Command("sign_st")
	@NotifyChange("ci_insurant_list")
	public void sign_st(@BindingParam("emco") CI_Insurant_ListModel emco,@BindingParam("castsort") Combobox castsort) throws Exception {
		CI_Insurant_ListDal dal = new CI_Insurant_ListDal();
		int st=dal.sign_ok(emco.getEcin_id()); 

		Messagebox.show("操作成功！", "操作提示",
				new Messagebox.Button[] { Messagebox.Button.OK },
				Messagebox.INFORMATION, null);
		
		ci_insurant_list = new ListModelList<CI_Insurant_ListModel>(
				bll.ci_insurant_list(castsort.getValue()));// 显示未审核列表
	}

	// 商保审核查询
	@Command("search")
	@NotifyChange("ci_insurant_list")
	public void search(@BindingParam("castsort") Combobox castsort)
			throws Exception {
		ci_insurant_list = new ListModelList<CI_Insurant_ListModel>(
				bll.ci_insurant_list(castsort.getValue()));// 显示未审核列表
	}
	
	@Command("ci_insurant_back")
	public void ci_insurant_back(@BindingParam("emco") CI_Insurant_ListModel emco)
			throws Exception {	
		CI_Insurant_ListDal dal = new CI_Insurant_ListDal();
		
		email_list = new ListModelList<CI_Insurant_ListModel>(
				dal.getemail_list(emco.getEcin_tapr_id()));
		
		String[] message = ccsaBll.del_insurant(
				String.valueOf(emco.getEcin_id()),
				String.valueOf(emco.getEcin_tapr_id()));

		if (message[0].equals("1")) {
			// ecin_del = new ListModelList<CI_Insurant_ListModel>(
			// bll.ecin_del(String.valueOf(emco.getEcin_id())));// 显示保险在保类型
			Messagebox.show("退回成功！", "操作提示",
					new Messagebox.Button[] { Messagebox.Button.OK },
					Messagebox.INFORMATION, null);
			
			//发送邮件
			Integer k = 0;
			String[] str = new String[5];
			String content;
			 
			// 发邮件和系统短信
            MessageService msgservice=new MessageImpl("embase",0);
            LoginDal d =new LoginDal();
               SysMessageModel sysm =new SysMessageModel();
           String msgstr=email_list.get(0).getEcin_insurant()+"您好！"+email_list.get(0).getEcin_company()+"--"+email_list.get(0).getEcin_insurer()+"由于该员工商保投保信息有误或资料不全，商保小组已将数据退回并终止，如要继续投保请在系统中重新添加商保数据，如有疑问，请联系商保后道，谢谢！";
            sysm.setSyme_title("商保数据退回");
            sysm.setSyme_content(msgstr);//短信内容
            sysm.setSyme_log_id(d.getUserIDByname(UserInfo.getUsername()));//发件人id
            sysm.setSymr_name(email_list.get(0).getEcin_insurant());//收件人姓名
            sysm.setSymr_log_id(d.getUserIDByname(email_list.get(0).getEcin_insurant()));//;
            sysm.setEmail(1);
            sysm.setEmailtitle("商保数据退回");
            msgservice.Add(sysm);
			
			RedirectUtil util = new RedirectUtil();
			util.refreshEmUrl("/EmCommercialInsurance/CI_Insurant_Aut.zul");// url为跳转页面连接
		} else {
			Messagebox.show("退回失败！", "操作提示",
					new Messagebox.Button[] { Messagebox.Button.OK },
					Messagebox.ERROR, null);
		}
	}

	public ListModelList<CI_Insurant_ListModel> getCastsortlist() {
		return castsortlist;
	}

	public void setCastsortlist(
			ListModelList<CI_Insurant_ListModel> castsortlist) {
		this.castsortlist = castsortlist;
	}

	public ListModelList<CI_Insurant_ListModel> getCi_insurant_list() {
		return ci_insurant_list;
	}

	public void setCi_insurant_list(
			ListModelList<CI_Insurant_ListModel> ci_insurant_list) {
		this.ci_insurant_list = ci_insurant_list;
	}

}
