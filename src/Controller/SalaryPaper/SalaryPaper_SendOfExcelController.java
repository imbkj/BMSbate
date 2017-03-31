package Controller.SalaryPaper;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.mail.Address;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;

import jxl.Workbook;
import jxl.format.Alignment;
import jxl.format.UnderlineStyle;
import jxl.format.VerticalAlignment;
import jxl.write.Label;
import jxl.write.Number;
import jxl.write.WritableCellFormat;
import jxl.write.WritableFont;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;
import jxl.write.WriteException;

import org.zkoss.bind.annotation.BindingParam;
import org.zkoss.bind.annotation.Command;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.Session;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Window;

import bll.SalaryPaper.SalaryPaperBll;

import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.log.SysoLogger;
import com.jxcell.CellException;
import com.jxcell.View;
import com.lowagie.text.Cell;
import com.lowagie.text.Document;
import com.lowagie.text.PageSize;
import com.lowagie.text.Paragraph;
import com.lowagie.text.Table;
import com.lowagie.text.pdf.PdfWriter;

import Model.EmSalaryDataModel;
import Model.PubEmailModel;
import Util.FileOperate;
import Util.MailSenderInfo;
import Util.SimpleMailSender;

/**
 * excel附件发送
 * 
 * @author Administrator
 * 
 */
public class SalaryPaper_SendOfExcelController {

	private EmSalaryDataModel m = (EmSalaryDataModel) Executions.getCurrent()
			.getArg().get("m");
	private String title;
	private String text;
	private SalaryPaperBll spb = new SalaryPaperBll();
	private String urlJsp;

	public SalaryPaper_SendOfExcelController() {
		urlJsp = "../SalaryPaper/sendHtml.jsp";
		Session session = Executions.getCurrent().getDesktop().getSession();
		session.setAttribute("m", m);
		title = m.getEsda_ba_name() + m.getOwnmonth() + "月份的工资单";
		text = m.getEsda_remark();
	}

	// 发送email
	@Command
	public void sendExcelType(@BindingParam("excelWin") Window win)
			throws AddressException {
		if (m.getEsda_email() != null && !"".equals(m.getEsda_email())) {
			String[] msg = spb.sendExcelEmail(m);
			if ("1".equals(msg[0])) {
				Messagebox.show("发送成功", "操作提示", Messagebox.OK, Messagebox.NONE);
				win.detach();
			} else {
				Messagebox.show("发送失败", "error", Messagebox.OK,
						Messagebox.ERROR);
			}
		} else {
			Messagebox.show("请录入正确的邮箱地址！", "error", Messagebox.OK,
					Messagebox.ERROR);
		}
	}

	public String getUrlJsp() {
		return urlJsp;
	}

	public void setUrlJsp(String urlJsp) {
		this.urlJsp = urlJsp;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public EmSalaryDataModel getM() {
		return m;
	}

	public void setM(EmSalaryDataModel m) {
		this.m = m;
	}
}
