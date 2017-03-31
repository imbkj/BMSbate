package Controller.SalaryPaper;

import java.io.UnsupportedEncodingException;
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

import bll.SalaryPaper.SalaryPaperBll;

import Model.EmSalaryBaseAddItemModel;
import Model.EmSalaryDataModel;
import Model.PubEmailModel;
import Util.MailSenderInfo;
import Util.SimpleMailSender;

public class SalaryPaper_SendOfTextController {

	private EmSalaryDataModel m = (EmSalaryDataModel) Executions.getCurrent()
			.getArg().get("m");
	private String text;
	private String title;
	private SalaryPaperBll spb = new SalaryPaperBll();

	public SalaryPaper_SendOfTextController() {
		Session session = Executions.getCurrent().getDesktop().getSession();
		session.setAttribute("m", m);

		List<EmSalaryBaseAddItemModel> list = m.getItemList();
		StringBuffer sb = new StringBuffer(list.get(0).getCsii_item_name()
				+ "：" + list.get(0).getAmount());
		for (int i = 1; i < list.size(); i++) {
			sb.append("，|" + list.get(i).getCsii_item_name() + "："
					+ list.get(i).getAmount());
		}
		text = sb.toString();

		title = "你好，" + m.getEsda_ba_name() + "。您" + m.getOwnmonth() + "月份的工资单";
	}

	// 发送email
	@Command
	public void sendTextType(@BindingParam("textWin") Window win)
			throws AddressException {
		if (m.getEsda_email() != null && !"".equals(m.getEsda_email())) {
			String[] msg = spb.sendTextEmail(m);
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

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public EmSalaryDataModel getM() {
		return m;
	}

	public void setM(EmSalaryDataModel m) {
		this.m = m;
	}
}
