package Controller.SalaryPaper;

import java.io.UnsupportedEncodingException;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.mail.Address;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;

import org.zkoss.bind.annotation.BindingParam;
import org.zkoss.bind.annotation.Command;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.Session;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Window;

import bll.SalaryPaper.SalaryPaperBll;

import Model.EmSalaryDataModel;
import Model.PubEmailModel;
import Util.MailSenderInfo;
import Util.SimpleMailSender;

/**
 * 生成html
 * 
 * @author Administrator
 * 
 */
public class SalaryPaper_SendOfHtmlController {

	private EmSalaryDataModel m = (EmSalaryDataModel) Executions.getCurrent()
			.getArg().get("m");
	private String title;
	private String url;
	private SalaryPaperBll spb = new SalaryPaperBll();

	public SalaryPaper_SendOfHtmlController() {
		Session session = Executions.getCurrent().getDesktop().getSession();
		session.setAttribute("m", m);

		url = "../SalaryPaper/sendHtml.jsp";
		// url = "/SendHtmlServlet";
		title = m.getEsda_ba_name() + m.getOwnmonth() + "月份的工资单";

	}

	// 发送email
	@Command
	public void sendHtmlType(@BindingParam("htmlWin") Window win)
			throws AddressException {
		if (m.getEsda_email() != null && !"".equals(m.getEsda_email())) {
			String[] msg = spb.sendHTMLEmail(m);
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

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
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
