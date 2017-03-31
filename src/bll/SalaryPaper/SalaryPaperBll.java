package bll.SalaryPaper;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.mail.Address;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;

import org.zkoss.zk.ui.Executions;
import org.zkoss.zul.Messagebox;

import com.jxcell.CellException;
import com.jxcell.View;
import com.lowagie.text.Cell;
import com.lowagie.text.Document;
import com.lowagie.text.Font;
import com.lowagie.text.Phrase;
import com.lowagie.text.Table;
import com.lowagie.text.pdf.BaseFont;
import com.lowagie.text.pdf.PdfWriter;

import Model.CoSalaryItemInfoModel;
import Model.CoSalaryMbModel;
import Model.EmSalaryBaseAddItemModel;
import Model.EmSalaryDataModel;
import Model.EmSalaryInfoModel;
import Model.PubEmailModel;
import Model.SalaryPaperCoModel;
import Model.SalarySendTypeModel;
import Util.FileOperate;
import Util.MailSenderInfo;
import Util.SimpleMailSender;
import dal.SalaryPaper.SalaryPaperDal;

public class SalaryPaperBll {
	SalaryPaperDal spd = new SalaryPaperDal();

	public List<SalaryPaperCoModel> getCoSetList() {
		List<SalaryPaperCoModel> l = new ArrayList<SalaryPaperCoModel>();
		List<SalaryPaperCoModel> list = spd.getCoSetList();
		for (int i = 0; i < list.size(); i++) {
			list.get(i).setCoss_StringcarbonCopy(
					list.get(i).getCoss_carbonCopy() == 1 ? "是" : "否");
			list.get(i).setCoss_StringSectetend(
					list.get(i).getCoss_secretsend() == 1 ? "是" : "否");
			list.get(i).setCoss_StringSendstate(
					list.get(i).getCoss_sendstate() == 1 ? "是" : "否");
			l.add(list.get(i));
		}
		return l;
	}

	// 查询待选择的工资单项目
	public List<CoSalaryItemInfoModel> getWaitSitemList(int cid, int ownmonth) {

		return spd.getWaitSitemList(cid, ownmonth);
	}

	// 查询已经选择的工资单项目
	public List<CoSalaryItemInfoModel> getIetmList(int cid, int ownmonth) {
		return spd.getIetmList(cid, ownmonth);

	}

	// 保存工资单项目的设置
	public boolean itemMoveSave(List<CoSalaryItemInfoModel> wList,
			List<CoSalaryItemInfoModel> itemList) {
		List<CoSalaryItemInfoModel> l1 = new ArrayList<CoSalaryItemInfoModel>();
		List<CoSalaryItemInfoModel> l2 = new ArrayList<CoSalaryItemInfoModel>();
		List<CoSalaryItemInfoModel> l3 = new ArrayList<CoSalaryItemInfoModel>();
		for (int i = 0; i < wList.size(); i++) {
			if (wList.get(i).getCsii_csd_state() == 1
					|| wList.get(i).getCsii_csd_state() == 2) {
				l1.add(wList.get(i));
			}
		}
		for (int i = 0; i < itemList.size(); i++) {
			if (itemList.get(i).getCsii_csd_state() == 0
					&& itemList.get(i).getCsii_fd_state() != 2) {
				l2.add(itemList.get(i));
			}
			if (itemList.get(i).getCsii_fd_state() == 2
					&& itemList.get(i).getCsii_csd_state() == 0) {
				l3.add(itemList.get(i));
			}
		}
		return spd.itemMoveSave(l1, l2, l3);
	}

	// 修改工资单项目名称
	public void saveUpdateItemname(String sign, CoSalaryItemInfoModel m) {
		// 根据sign传过来的参数确定修改什么样的字段
		if (sign.equals("1")) {
			spd.UpdateItemname(m);
		} else if (sign.equals("2")) {
			spd.UpdateItemen(m);
		} else if (sign.equals("3")) {
			spd.UpdateIteman(m);
		}
	}

	// 公司设置新增

	public int coinfoadd(SalaryPaperCoModel m) {
		return spd.coInfoadd(m);

	}

	// 获取工资单设置
	public List<SalaryPaperCoModel> getmodellist(int str) {
		return spd.getmodellist(str);
	}

	// 公司设置更新
	public boolean coInfoSet(SalaryPaperCoModel m) {
		boolean flag = false;
		try {
			m.setCoss_secretsend(m.getCoss_StringSectetend().equals("是") ? 1
					: 0);
			m.setCoss_carbonCopy(m.getCoss_StringcarbonCopy().equals("是") ? 1
					: 0);
			m.setCoss_sendstate(m.getCoss_StringSendstate().equals("是") ? 1 : 0);
		} catch (Exception e) {

		}

		if (spd.coInfoSet(m) != 0) {
			flag = true;
		}
		return flag;
	}

	// // 根据cid得到员工的工资单信息
	// public List<EmSalaryInfoModel> getEsInfoList(int cid) {
	//
	// return spd.getEsInfoList(cid);
	// }

	// 批量根据cid得到员工的工资单信息
	public List<EmSalaryInfoModel> getPlEsInfoList(List<SalaryPaperCoModel> list) {
		StringBuffer str = new StringBuffer("(" + list.get(0).getCid());
		for (int i = 1; i < list.size(); i++) {
			str.append("," + list.get(i).getCid());
		}
		str.append(")");
		String s = str.toString();
		return spd.getPlEsInfoList(s);
	}

	// 获取发送方式信息
	public List<SalarySendTypeModel> getStmList() {

		return spd.getStmList();
	}

	// 设置发送方式
	public boolean setSendType(List<String> l, EmSalaryInfoModel m) {
		boolean flag = false;
		StringBuffer sb = new StringBuffer(l.get(0));
		// StringBuffer urlsb = new StringBuffer(urlList.get(0));
		// for (int i = 1; i < urlList.size(); i++) {
		// urlsb.append("," + urlList.get(i));
		// }
		for (int i = 1; i < l.size(); i++) {
			sb.append("," + l.get(i));
		}
		// String url = urlsb.toString();
		String sendType = sb.toString();
		if (spd.setSendType(sendType, m) != 0) {
			flag = true;
		}
		return flag;
	}

	// 批量设置发送方式
	public boolean setPlSendType(List<String> l, List<EmSalaryInfoModel> list) {
		List<Integer> esinids = new ArrayList<Integer>();
		StringBuffer sb = new StringBuffer(l.get(0));
		for (int i = 1; i < l.size(); i++) {
			sb.append("," + l.get(i));
		}
		String sendType = sb.toString();

		for (int i = 0; i < list.size(); i++) {
			esinids.add(list.get(i).getEsin_id());
		}
		return spd.setPlSendType(sendType, esinids);
	}

	// 获取工资单模版
	public List<CoSalaryMbModel> getCsmList() {

		return spd.getCsmList();
	}

	// 设置模版
	public boolean setModel(EmSalaryInfoModel m, List<CoSalaryMbModel> l) {
		boolean flag = false;
		StringBuffer str = new StringBuffer(String.valueOf(l.get(0)
				.getCosm_id()));
		for (int i = 1; i < l.size(); i++) {
			str.append("," + l.get(i).getCosm_id());
		}
		if (spd.setModel(m.getEsin_id(), str.toString()) != 0) {
			flag = true;
		}
		return flag;
	}

	// 批量设置模版
	public boolean PlSetModel(List<EmSalaryInfoModel> list,
			List<CoSalaryMbModel> l) {
		List<Integer> esinids = new ArrayList<Integer>();
		boolean flag = false;
		StringBuffer str = new StringBuffer(String.valueOf(l.get(0)
				.getCosm_id()));
		for (int i = 1; i < l.size(); i++) {
			str.append("," + l.get(i).getCosm_id());
		}
		for (int i = 0; i < list.size(); i++) {
			esinids.add(list.get(i).getEsin_id());
		}
		return spd.PlSetModel(esinids, str.toString());
	}

	// 生成随机密码
	public boolean producePwd(List<EmSalaryInfoModel> esinfoList) {

		return spd.producePwd(esinfoList);
	}

	// 工资单数据
	public List<EmSalaryDataModel> getSalaryList(int cid, String ownmonth) {

		return spd.getSalaryList(cid, ownmonth);
	}

	// 项目名称数据
	public List<EmSalaryBaseAddItemModel> getItemList(int cid, String ownmonth) {

		return spd.getItemList(cid, ownmonth);
	}

	// 把发送信息保存到数据库
	public void saveSendEmail(PubEmailModel pe) {
		spd.saveSendEmail(pe);
	}

	// 改变状态为已发送
	public void updateState(EmSalaryDataModel m) {
		spd.updateState(m);
	}

	// email服务器
	public PubEmailModel getServerEmailInfo() {
		PubEmailModel m = new PubEmailModel();
		m.setServer_host("smtp.szciic.com");
		m.setServer_port("25");
		m.setServer_f_address("gzservice@szciic.com");
		m.setServer_password("Service!((#1993");
		m.setServer_username("gzservice@szciic.com");
		return m;
	}

	// 发送html工资单
	public String[] sendHTMLEmail(EmSalaryDataModel m) throws AddressException {
		SalaryPaperBll spb = new SalaryPaperBll();
		String[] result = new String[1];
		String[] toaddress = null;
		if (m.getEsda_email().length() != 0) {
			toaddress = m.getEsda_email().split(","); // 目标邮件
		}
		String title = "你好，" + m.getEsda_ba_name() + "。您" + m.getOwnmonth()
				+ "月份的工资单";
		String[] msaddress = new String[1]; // 密送邮件

		String[][] AttachFile = new String[0][0]; // String[0]为附件名，String[1]为附件地址

		// 判断模版是什么
		MailSenderInfo mailInfo = new MailSenderInfo();
		PubEmailModel serverM = new PubEmailModel();

		serverM = spb.getServerEmailInfo();

		mailInfo.setAttachFileNames(AttachFile); // 邮件附件
		mailInfo.setMailServerHost(serverM.getServer_host()); // 发送服务器
		mailInfo.setMailServerPort(serverM.getServer_port()); // 发送端口
		mailInfo.setValidate(true);
		mailInfo.setUserName(serverM.getServer_username()); // 用户名
		mailInfo.setPassword(serverM.getServer_password());// 您的邮箱密码
		mailInfo.setFromAddress(serverM.getServer_f_address()); // 发送邮箱名
		mailInfo.setToAddress(toaddress); // 目标邮箱名

		Address[] replyAddress = new Address[1];
		replyAddress[0] = new InternetAddress(m.getGzaddname_email());
		mailInfo.setReplyAddress(replyAddress);// 回复人

		mailInfo.setSubject(title); // 邮件标题

		// 创建一个table
		StringBuffer sb = new StringBuffer("<br/>" + m.getEsda_ba_name()
				+ ",您好!   以下是您-" + m.getOwnmonth() + "-月份的工资单明细:<br/>");
		sb.append("<table border='1' cellpadding='6' cellspacing='1' >");
		sb.append("<tr><th bgcolor='#FFFFFF'>姓名</th><th bgcolor='#FFFFFF'>金额</th><th bgcolor='#FFFFFF'>姓名</th><th bgcolor='#FFFFFF'>金额</th></tr>");
		for (int i = 1; i < m.getItemList().size(); i++) {
			if (i % 2 == 0) {
				sb.append("<tr><td bgcolor='#FFFFFF'>"
						+ m.getItemList().get(i - 1).getCsii_item_name()
						+ "</td><td bgcolor='#FFFFFF'>"
						+ m.getItemList().get(i - 1).getAmount()
						+ "</td><td bgcolor='#FFFFFF'>"
						+ m.getItemList().get(i).getCsii_item_name()
						+ "</td><td bgcolor='#FFFFFF'>"
						+ m.getItemList().get(i).getAmount() + "</td></tr>");
			}
		}
		if (m.getItemList().size() % 2 != 0) {
			sb.append("<tr><td bgcolor='#FFFFFF' >"
					+ m.getItemList().get(m.getItemList().size() - 1)
							.getCsii_item_name()
					+ "</td><td bgcolor='#FFFFFF' colspan='3'>"
					+ m.getItemList().get(m.getItemList().size() - 1)
							.getAmount() + "</td></tr>");
		}
		sb.append("</table><br />");
		String text = sb.toString();
		mailInfo.setContent(text + m.getEsda_remark() + "工资计算问题咨询邮箱："
				+ m.getGzaddname_email() + " ；社保扣缴及其他问题咨询邮箱："
				+ m.getClient_email()); // 邮件正文

		// 如果密送状态为1
		if (m.getCoss_secretsend() == 1) {
			if (m.getCoss_secretsendaddress() != null) {
				msaddress[0] = new String(m.getCoss_secretsendaddress());
				mailInfo.setMsAddress(msaddress); // 密送邮件名
			}
		}
		// 如果抄送状态为1
		if (m.getCoss_carbonCopy() == 1) {
			if (m.getCoss_ccaddress() != null) {
				String[] csaddress = m.getCoss_ccaddress().split(","); // 抄送邮件
				mailInfo.setCsAddress(csaddress); // 抄送邮件名
			}
		}

		// 给pubemail赋值
		PubEmailModel pe = new PubEmailModel();
		pe.setPuem_email(m.getEsda_email());
		// pe.setPuem_replyto(puem_replyto); 目标邮件回复人的地址
		pe.setPuem_emailcc(m.getCoss_ccaddress()); // 抄送地址
		pe.setPuem_addname(Executions.getCurrent().getDesktop().getSession()
				.getAttribute("username").toString());
		pe.setPuem_title(title);
		pe.setPuem_content(text);
		pe.setPuem_truesendtime(new SimpleDateFormat("yyyy-MM-dd  HH:mm:ss")
				.format(new Date()));
		pe.setPuem_state(1);
		pe.setPuem_ifHTML(1);
		// 发送邮件
		SimpleMailSender sms = new SimpleMailSender();
		try {

			boolean flag = sms.sendHtmlMail(mailInfo);// 发送html格式
			if (flag) {
				// 把发送信息保存到数据库中
				spb.saveSendEmail(pe);
				// 发送成功改变状态为已发送
				m.setEsda_email_state(1);
				spb.updateState(m);
				result[0] = "1";
			} else {
				result[0] = "0";
			}

		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return result;

	}

	// 发送txt格式email
	public String[] sendTextEmail(EmSalaryDataModel m) throws AddressException {
		SalaryPaperBll spb = new SalaryPaperBll();
		String[] result = new String[1];
		String[] toaddress = null;
		if (m.getEsda_email().length() != 0) {
			toaddress = m.getEsda_email().split(","); // 目标邮件
		}
		String title = "你好，" + m.getEsda_ba_name() + "。您" + m.getOwnmonth()
				+ "月份的工资单";

		String text;
		List<EmSalaryBaseAddItemModel> list = m.getItemList();
		StringBuffer sb = new StringBuffer(list.get(0).getCsii_item_name()
				+ "：" + list.get(0).getAmount());
		for (int i = 1; i < list.size(); i++) {
			sb.append("，|" + list.get(i).getCsii_item_name() + "："
					+ list.get(i).getAmount());
		}
		text = sb.toString();

		String[][] AttachFile = new String[0][0]; // String[0]为附件名，String[1]为附件地址

		MailSenderInfo mailInfo = new MailSenderInfo();
		PubEmailModel serverM = new PubEmailModel();

		serverM = spb.getServerEmailInfo();
		mailInfo.setAttachFileNames(AttachFile); // 邮件附件
		mailInfo.setMailServerHost(serverM.getServer_host()); // 发送服务器
		mailInfo.setMailServerPort(serverM.getServer_port()); // 发送端口
		mailInfo.setValidate(true);
		mailInfo.setUserName(serverM.getServer_username()); // 用户名
		mailInfo.setPassword(serverM.getServer_password());// 您的邮箱密码
		mailInfo.setFromAddress(serverM.getServer_f_address()); // 发送邮箱名
		mailInfo.setToAddress(toaddress); // 目标邮箱名
		Address[] replyAddress = new Address[1];
		replyAddress[0] = new InternetAddress(m.getGzaddname_email());
		mailInfo.setReplyAddress(replyAddress);// 回复人

		mailInfo.setSubject(title); // 邮件标题
		mailInfo.setContent("你好，" + m.getEsda_ba_name() + "，这是您"
				+ m.getOwnmonth() + "月份的工资单。" + text + "         备注："
				+ m.getEsda_remark() + "工资计算问题咨询邮箱：" + m.getGzaddname_email()
				+ " ；社保扣缴及其他问题咨询邮箱：" + m.getClient_email()); // 邮件正文

		// 如果密送状态为1
		if (m.getCoss_secretsend() == 1) {
			if (m.getCoss_secretsendaddress() != null) {
				String[] msaddress = m.getCoss_secretsendaddress().split(","); // 密送邮件
				mailInfo.setMsAddress(msaddress); // 密送邮件名
			}
		}
		// 如果抄送状态为1
		if (m.getCoss_carbonCopy() == 1) {
			if (m.getCoss_ccaddress() != null) {
				String[] csaddress = m.getCoss_ccaddress().split(","); // 抄送邮件
				mailInfo.setCsAddress(csaddress); // 抄送邮件名
			}
		}

		// 给pubemail赋值
		PubEmailModel pe = new PubEmailModel();
		pe.setPuem_email(m.getEsda_email());
		// pe.setPuem_replyto(puem_replyto); 目标邮件回复人的地址
		pe.setPuem_emailcc(m.getCoss_ccaddress()); // 抄送地址
		pe.setPuem_addname(Executions.getCurrent().getDesktop().getSession()
				.getAttribute("username").toString());
		pe.setPuem_title(title);
		pe.setPuem_content("你好," + m.getEsda_ba_name() + ",这是您"
				+ m.getOwnmonth() + "月份的工资单。" + text + "         备注："
				+ m.getEsda_remark());
		pe.setPuem_truesendtime(new SimpleDateFormat("yyyy-MM-dd  HH:mm:ss")
				.format(new Date()));
		pe.setPuem_state(1);

		// 发送邮件
		SimpleMailSender sms = new SimpleMailSender();
		try {
			if (toaddress.length > 0) {
				long stdate = System.currentTimeMillis();
				boolean flag = sms.sendTextMail(mailInfo);
				long enddate = System.currentTimeMillis();
				if (flag) {
					spb.saveSendEmail(pe);
					// 修改状态
					m.setEsda_email_state(1);
					spb.updateState(m);
					result[0] = "1";
				} else {
					result[0] = "0";
				}
			} else {
				result[0] = "2";// 请填写发送地址
			}
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return result;
	}

	// 发送PDF格式email
	public String[] sendPDFEmail(EmSalaryDataModel m) throws AddressException {
		SalaryPaperBll spb = new SalaryPaperBll();
		String[] result = new String[1];

		String title = "你好，" + m.getEsda_ba_name() + "。您" + m.getOwnmonth()
				+ "月份的工资单";

		String urlpdf = FileOperate.getAbsolutePath()
				+ "OfficeFile/DownLoad/SendOfExcel/"
				+ System.currentTimeMillis() + ".pdf"; // pdf路径
		try {
			// 创建文档
			Document doc = new Document();

			// 将doc的内容转换成pdf格式
			PdfWriter writer = PdfWriter.getInstance(doc, new FileOutputStream(
					urlpdf));

			//设置密码
			writer.setEncryption(m.getEsin_attachpassword().getBytes(), m.getEsin_attachpassword()
					.getBytes(), writer.ALLOW_PRINTING,
					writer.ENCRYPTION_AES_128);

			// 设置中文字体，若不设置将会导致中文丢失
			BaseFont bfChinese = BaseFont.createFont("STSongStd-Light",
					"UniGB-UCS2-H", false);
			Font fontChinese = new Font(bfChinese, 12, Font.NORMAL, null);

			doc.open();

			// 调用创建table的方法
			makeDocument(doc, fontChinese, m.getItemList());
			// 关闭文档
			doc.close();
			writer.close();
		} catch (Exception e1) {
			e1.printStackTrace();
		}

		String[] toaddress = null;

		if (m.getEsda_email().length() != 0) {
			toaddress = m.getEsda_email().split(","); // 目标邮件

		}

		String[] msaddress = new String[1]; // 密送邮件

		String[][] AttachFile = new String[1][2]; // String[0]为附件名，String[1]为附件地址

		String excelName = m.getEsda_ba_name() + m.getOwnmonth() + "月份工资单.pdf"; // pdf名称
		// 附件路径和名称
		AttachFile[0][0] = new String(excelName);
		AttachFile[0][1] = new String(urlpdf);
		MailSenderInfo mailInfo = new MailSenderInfo();
		PubEmailModel serverM = new PubEmailModel();
		serverM = spb.getServerEmailInfo();
		// System.out.println(m.getEsin_attachpassword());

		mailInfo.setAttachFileNames(AttachFile); // 邮件附件
		mailInfo.setMailServerHost(serverM.getServer_host()); // 发送服务器
		mailInfo.setMailServerPort(serverM.getServer_port()); // 发送端口
		mailInfo.setValidate(true);
		mailInfo.setUserName(serverM.getServer_username()); // 用户名
		mailInfo.setPassword(serverM.getServer_password());// 您的邮箱密码
		mailInfo.setFromAddress(serverM.getServer_f_address()); // 发送邮箱名
		mailInfo.setToAddress(toaddress); // 目标邮箱名
		Address[] replyAddress = new Address[1];
		replyAddress[0] = new InternetAddress(m.getGzaddname_email());
		mailInfo.setReplyAddress(replyAddress);// 回复人

		mailInfo.setSubject(title); // 邮件标题
		mailInfo.setContent("你好," + m.getEsda_ba_name() + ",这是您"
				+ m.getOwnmonth() + "月份的工资单，请查看附件。" + m.getEsda_remark()
				+ "工资计算问题咨询邮箱：" + m.getGzaddname_email() + " ；社保扣缴及其他问题咨询邮箱："
				+ m.getClient_email()); // 邮件正文

		// 如果密送状态为1
		if (m.getCoss_secretsend() == 1) {
			if (m.getCoss_secretsendaddress() != null) {
				msaddress[0] = new String(m.getCoss_secretsendaddress());
				mailInfo.setMsAddress(msaddress); // 密送邮件名
			}
		}

		// 如果抄送状态为1
		if (m.getCoss_carbonCopy() == 1) {
			if (m.getCoss_ccaddress() != null) {
				String[] csaddress = m.getCoss_ccaddress().split(","); // 抄送邮件
				mailInfo.setCsAddress(csaddress); // 抄送邮件名
			}
		}

		// 给pubemail赋值
		PubEmailModel pe = new PubEmailModel();
		pe.setPuem_email(m.getEsda_email());
		// pe.setPuem_replyto(puem_replyto); 目标邮件回复人的地址
		pe.setPuem_emailcc(m.getCoss_ccaddress()); // 抄送地址
		pe.setPuem_addname(Executions.getCurrent().getDesktop().getSession()
				.getAttribute("username").toString());
		pe.setPuem_title(title);
		pe.setPuem_content("你好," + m.getEsda_ba_name() + ",这是您"
				+ m.getOwnmonth() + "月份的工资单，请查看附件。" + m.getEsda_remark());
		pe.setPuem_truesendtime(new SimpleDateFormat("yyyy-MM-dd  HH:mm:ss")
				.format(new Date()));
		pe.setPuem_iffile(1);
		pe.setPuem_state(1);
		pe.setFileurl(urlpdf);

		// 发送邮件
		SimpleMailSender sms = new SimpleMailSender();
		try {
			// sms.sendHtmlMail(mailInfo);// 发送html格式
			// 发送文体格式
			long stdate = System.currentTimeMillis();
			boolean flag = sms.sendTextMail(mailInfo);
			long enddate = System.currentTimeMillis();
			System.out.println(enddate - stdate);
			// boolean flag = false;
			if (flag) {
				spb.saveSendEmail(pe);
				// 发送成功改变状态为已发送
				m.setEsda_email_state(1);
				spb.updateState(m);
				result[0] = "1";
			} else {
				result[0] = "0";
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		return result;
	}

	// 发送Excel格式email
	public String[] sendExcelEmail(EmSalaryDataModel m) throws AddressException {
		SalaryPaperBll spb = new SalaryPaperBll();
		String[] result = new String[1];
		String title = "你好，" + m.getEsda_ba_name() + "。您" + m.getOwnmonth()
				+ "月份的工资单";
		String text = m.getEsda_remark();

		String[] toaddress = null;
		if (m.getEsda_email().length() != 0) {
			toaddress = m.getEsda_email().split(","); // 目标邮件
		}

		String[] msaddress = new String[1]; // 密送邮件

		String[][] AttachFile = new String[1][2]; // String[0]为附件名，String[1]为附件地址

		// 创建文件夹
		File f = new File(FileOperate.getAbsolutePath()
				+ "OfficeFile/DownLoad/SendOfExcel");
		if (!f.isFile()) {
			f.mkdir();
		}

		String urlpdf = FileOperate.getAbsolutePath()
				+ "OfficeFile/DownLoad/SendOfExcel/"
				+ System.currentTimeMillis() + ".pdf"; // pdf路径
		String url = FileOperate.getAbsolutePath()
				+ "OfficeFile/DownLoad/SendOfExcel/"
				+ System.currentTimeMillis() + ".xls"; // excel路径

		View excelView = new View();
		try {
			excelView.setTextAsValue(0, 0, "项目名称");
			excelView.setTextAsValue(0, 1, "金额");
			excelView.setTextAsValue(0, 2, "项目名称");
			excelView.setTextAsValue(0, 3, "金额");
			for (int i = 1; i < m.getItemList().size(); i++) {
				if (i % 2 == 0) {
					continue;
				}
				excelView.setTextAsValue((i + 1) / 2, 0,
						m.getItemList().get(i - 1).getCsii_item_name());
				excelView.setNumber((i + 1) / 2, 1, m.getItemList().get(i - 1)
						.getAmount().doubleValue());
				excelView.setTextAsValue((i + 1) / 2, 2, m.getItemList().get(i)
						.getCsii_item_name());
				excelView.setNumber((i + 1) / 2, 3, m.getItemList().get(i)
						.getAmount().doubleValue());
			}
			if (m.getItemList().size() % 2 != 0) {
				excelView.setTextAsValue((m.getItemList().size() + 1) / 2, 0, m
						.getItemList().get(m.getItemList().size() - 1)
						.getCsii_item_name());
				excelView.setNumber((m.getItemList().size() + 1) / 2, 1, m
						.getItemList().get(m.getItemList().size() - 1)
						.getAmount().doubleValue());
			}
			// excelView.exportPDF(urlpdf); // 导出并写入密码
			excelView.write(url, m.getEsin_attachpassword());

		} catch (CellException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (IOException e) {
			// TODOAuto-generated catch block
			e.printStackTrace();
		}

		String excelName = m.getEsda_ba_name() + m.getOwnmonth() + "月份工资单.xls"; // excel名称

		// 附件路径和名称
		AttachFile[0][0] = new String(excelName);
		AttachFile[0][1] = new String(url);

		MailSenderInfo mailInfo = new MailSenderInfo();
		PubEmailModel serverM = new PubEmailModel();
		serverM = spb.getServerEmailInfo();
		// System.out.println(m.getEsin_attachpassword());

		mailInfo.setAttachFileNames(AttachFile); // 邮件附件
		mailInfo.setMailServerHost(serverM.getServer_host()); // 发送服务器
		mailInfo.setMailServerPort(serverM.getServer_port()); // 发送端口
		mailInfo.setValidate(true);
		mailInfo.setUserName(serverM.getServer_username()); // 用户名
		mailInfo.setPassword(serverM.getServer_password());// 您的邮箱密码
		mailInfo.setFromAddress(serverM.getServer_f_address()); // 发送邮箱名
		mailInfo.setToAddress(toaddress); // 目标邮箱名
		Address[] replyAddress = new Address[1];
		replyAddress[0] = new InternetAddress(m.getGzaddname_email());
		mailInfo.setReplyAddress(replyAddress);// 回复人

		mailInfo.setSubject(title); // 邮件标题
		mailInfo.setContent("你好," + m.getEsda_ba_name() + ",这是您"
				+ m.getOwnmonth() + "月份的工资单，请查看附件。" + text + "工资计算问题咨询邮箱："
				+ m.getGzaddname_email() + " ；社保扣缴及其他问题咨询邮箱："
				+ m.getClient_email()); // 邮件正文

		// 如果密送状态为1
		if (m.getCoss_secretsend() == 1) {
			if (m.getCoss_secretsendaddress() != null) {
				msaddress[0] = new String(m.getCoss_secretsendaddress());
				mailInfo.setMsAddress(msaddress); // 密送邮件名
			}
		}
		// 如果抄送状态为1
		if (m.getCoss_carbonCopy() == 1) {
			if (m.getCoss_ccaddress() != null) {
				String[] csaddress = m.getCoss_ccaddress().split(","); // 抄送邮件
				mailInfo.setCsAddress(csaddress); // 抄送邮件名
			}
		}

		// 给pubemail赋值
		PubEmailModel pe = new PubEmailModel();
		pe.setPuem_email(m.getEsda_email());
		// pe.setPuem_replyto(puem_replyto); 目标邮件回复人的地址
		pe.setPuem_emailcc(m.getCoss_ccaddress()); // 抄送地址
		pe.setPuem_addname(Executions.getCurrent().getDesktop().getSession()
				.getAttribute("username").toString());
		pe.setPuem_title(title);
		pe.setPuem_content("你好," + m.getEsda_ba_name() + ",这是您"
				+ m.getOwnmonth() + "月份的工资单，请查看附件。" + text);
		pe.setPuem_truesendtime(new SimpleDateFormat("yyyy-MM-dd  HH:mm:ss")
				.format(new Date()));
		pe.setPuem_iffile(1);
		pe.setPuem_state(1);
		pe.setFileurl(url);

		// 发送邮件
		SimpleMailSender sms = new SimpleMailSender();
		try {
			// sms.sendHtmlMail(mailInfo);// 发送html格式
			// 发送文体格式
			long stdate = System.currentTimeMillis();
			boolean flag = sms.sendTextMail(mailInfo);
			long enddate = System.currentTimeMillis();
			System.out.println(enddate - stdate);
			// boolean flag = false;
			if (flag) {
				spb.saveSendEmail(pe);
				// 发送成功改变状态为已发送
				m.setEsda_email_state(1);
				spb.updateState(m);
				result[0] = "1";
			} else {
				result[0] = "0";
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		return result;
	}

	public void makeDocument(Document document, Font fontChinese,
			List<EmSalaryBaseAddItemModel> list) throws Exception {
		// table大部份操做类似与html,下边是一些常用的参数
		// 3是总的列数，也可以同时指定行数和列数new Table(3,4)

		Table table = new Table(4);

		// table的宽度
		table.setWidth(75);
		// 类似html的cellSpaceing
		table.setSpacing(2);
		// 每一列的宽度，是比例不是固定宽度
		table.setWidths(new int[] { 35, 15, 35, 15 });
		// 对齐方式
		table.setAlignment("CENTER");
		// table是否有边框
		table.setBorder(0);
		table.setBorderWidth(0);
		// 自动填充空白
		table.setAutoFillEmptyCells(true);

		for (int i = 0; i < list.size(); i++) {
			if (i % 2 == 0) {
				continue;
			}
			Cell cellname = new Cell(new Phrase(list.get(i - 1)
					.getCsii_item_name(), fontChinese));
			Cell cellAmount = new Cell(list.get(i - 1).getAmount().toString());
			Cell cellname1 = new Cell(new Phrase(list.get(i)
					.getCsii_item_name(), fontChinese));
			Cell cellAmount1 = new Cell(list.get(i).getAmount().toString());

			cellname.setMaxLines(1);
			cellAmount.setMaxLines(1);
			table.addCell(cellname);
			table.addCell(cellAmount);

			cellname1.setMaxLines(1);
			cellAmount1.setMaxLines(1);
			table.addCell(cellname1);
			table.addCell(cellAmount1);
		}

		if (list.size() % 2 != 0) {
			Cell cellname = new Cell(new Phrase(list.get(list.size() - 1)
					.getCsii_item_name(), fontChinese));
			Cell cellAmount = new Cell(list.get(list.size() - 1).getAmount()
					.toString());
			table.addCell(cellname);
			table.addCell(cellAmount);
		}
		// 新的一页要加上这句
		// document.newPage();
		document.add(table);
	}
}
