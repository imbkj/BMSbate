package Controller.SalaryPaper;

import java.io.FileOutputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.mail.Address;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;

import org.zkoss.bind.annotation.BindingParam;
import org.zkoss.bind.annotation.Command;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.Session;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Window;

import com.lowagie.text.Cell;
import com.lowagie.text.Document;
import com.lowagie.text.Font;
import com.lowagie.text.Phrase;
import com.lowagie.text.Table;
import com.lowagie.text.pdf.BaseFont;
import com.lowagie.text.pdf.PdfWriter;

import Model.EmSalaryBaseAddItemModel;
import Model.EmSalaryDataModel;
import Model.PubEmailModel;
import Util.FileOperate;
import Util.MailSenderInfo;
import Util.SimpleMailSender;
import bll.SalaryPaper.SalaryPaperBll;

public class SalaryPaper_SendOfPDFController {
	private EmSalaryDataModel m = (EmSalaryDataModel) Executions.getCurrent()
			.getArg().get("m");
	private String title;
	private String url;
	private SalaryPaperBll spb = new SalaryPaperBll();

	public SalaryPaper_SendOfPDFController() {
		Session session = Executions.getCurrent().getDesktop().getSession();
		session.setAttribute("m", m);

		url = "../SalaryPaper/sendHtml.jsp";
		title = m.getEsda_ba_name() + m.getOwnmonth() + "月份的工资单";

	}

	// 发送pdf格式
	@Command
	public void sendPDF(@BindingParam("pdfWin") Window win)
			throws AddressException {
		if (m.getEsda_email() != null && !"".equals(m.getEsda_email())) {
			String[] msg = spb.sendPDFEmail(m);
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

	public EmSalaryDataModel getM() {
		return m;
	}

	public void setM(EmSalaryDataModel m) {
		this.m = m;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

}
