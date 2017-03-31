package Controller.SmsMessage;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.sql.SQLException;
import java.util.List;

import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.zkoss.bind.annotation.BindingParam;
import org.zkoss.bind.annotation.Command;
import org.zkoss.bind.annotation.ContextParam;
import org.zkoss.bind.annotation.ContextType;
import org.zkoss.bind.annotation.NotifyChange;
import org.zkoss.util.media.Media;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.event.UploadEvent;
import org.zkoss.zk.ui.util.Clients;
import org.zkoss.zul.Checkbox;
import org.zkoss.zul.Filedownload;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Window;

import bll.SmsMessage.SmsGroupBll;
import bll.SmsMessage.Sms_GroupSendBll;

import Model.CoBaseModel;
import Model.EmHouseTakeCardInfoModel;
import Model.EmbaseModel;
import Model.SMSModel;
import Util.FileOperate;
import Util.UserInfo;

public class Sms_GroupSendController {
	private List<EmbaseModel> list = (List<EmbaseModel>) Executions
			.getCurrent().getArg().get("list");
	private String content = "";
	private Integer surplus = 70;
	private SmsGroupBll sbll = new SmsGroupBll();
	private List<String> loginlist = sbll.getLoginList();
	private String username = UserInfo.getUsername();
	private String headstr = "";
	private String filename = "";
	private InputStream inputStream = null;
	private Media media;
	private SmsGroupBll selbb = new SmsGroupBll();
	private boolean distitle=false;
	private String emailtitle="";

	@Command
	@NotifyChange("list")
	public void cancel(@BindingParam("model") EmbaseModel model) {
		list.remove(model);
	}

	@Command
	@NotifyChange("content")
	public void sumbit(@BindingParam("ck") Checkbox ck,
			@BindingParam("win") Window win) {
		if (content != null && !content.equals("")) {
			String isHasMobile = isHasMobile();
			String isHasEmail = isHasEmail();
			String str = "";
			Integer email_flag = 0;
			if (ck.isChecked()) {
				email_flag = 1;
				if(emailtitle==null||emailtitle.equals(""))
				{
					Messagebox.show("邮件标题不能为空", "提示", Messagebox.OK, Messagebox.ERROR);
					return;
				}
			}
			if (!isHasMobile.equals("")) {
				if (ck.isChecked()) {
					if (!isHasEmail.equals("")) {
						str = "部分员工的手机号码和电子邮箱为空,确定要发送信息吗？";
					}
				} else {
					str = "部分员工的手机号码为空,确定要发送信息吗？";
				}
			} else if (!isHasEmail.equals("")) {
				if (ck.isChecked()) {
					if (!isHasEmail.equals("")) {
						str = "部分员工电子邮箱为空,确定要发送信息吗？";
					}
				}
			}
			Integer k = 0;
			if (str.equals("")) {
				/****************** 把文件上传到服务器 **************************/
				if (media != null) {
					String realPath = "SmsMessage/uploadfile/";
					String filepath = FileOperate.getAbsolutePath() + realPath
							+ filename;
					boolean flag = FileOperate.upload(media, realPath
							+ filename);
				}

				/****************** 把文件上传到服务器END **************************/
				k = sendSms(email_flag);
			} else {
				if (Messagebox.show(str, "操作提示",
						Messagebox.YES | Messagebox.NO, Messagebox.QUESTION) == Messagebox.YES) {
					k = sendSms(email_flag);
				}
			}
			if (k > 0) {
				content = "";
				ck.setChecked(false);
				Clients.showNotification("发送成功", "info", win, "middle_center",
						3000);
			}
		} else {
			Messagebox.show("短信内容不能为空", "提示", Messagebox.OK, Messagebox.ERROR);
		}
	}

	// 发送短信的方法
	private Integer sendSms(Integer email_flag) {
		Sms_GroupSendBll bll = new Sms_GroupSendBll();
		// 获取短信内容的长度
		// Integer lenght = content.length();
		Integer k = 0;
		SMSModel model = new SMSModel();
		model.setSendname(username);
		model.setEmail_flag(email_flag);

		for (EmbaseModel m : list) {
			String contentSub = content;
			if (m.getGid() != null && !m.getGid().equals(0)
					&& content.contains("@公司简称@") || content.contains("@客服代表@")) {
				CoBaseModel mo = selbb.getCoBaseModel(m.getGid());
				if (content.contains("@公司简称@")) {
					if (mo.getCoba_shortname() != null) {
						contentSub = content.replaceAll("@公司简称@",
								mo.getCoba_shortname());
					}
				}
				if (contentSub.contains("@客服代表@")) {
					if (mo.getCoba_client() != null) {
						contentSub = contentSub.replaceAll("@客服代表@",
								mo.getCoba_client());
					}
				}
			}
			if (contentSub.contains("@员工姓名@")) {
				if (m.getEmba_name() != null) {
					contentSub = contentSub.replaceAll("@员工姓名@",
							m.getEmba_name());
				}
			}
			String idcard="";
			if(m.getEmba_idcard()!=null)
			{
				idcard=m.getEmba_idcard();
			}
			model.setGid(m.getGid());
			model.setMobile(m.getEmba_mobile());
			model.setEmail(m.getEmba_email());
			model.setContent(contentSub + "【深圳中智】");
			model.setIdcard(idcard);
			

			SMSModel emailm = new SMSModel();
			emailm.setSendname(username);
			emailm.setEmail_flag(email_flag);
			emailm.setContent(contentSub + "【深圳中智】");
			emailm.setEmail(m.getEmba_email());
			emailm.setTitle(emailtitle);//邮件标题
			
			emailm.setIdcard(idcard);

			try {
				k = k + bll.SmsSend(model);
				if (email_flag == 1) {
					k = k + bll.EmailSend(emailm);
				}
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

		return k;
	}

	// 发送短信的方法
	private Integer sendEmail() {
		Sms_GroupSendBll bll = new Sms_GroupSendBll();
		Integer k = 0;
		SMSModel emailm = new SMSModel();
		emailm.setSendname(username);
		for (EmbaseModel m : list) {
			String contentSub = content;
			if (m.getGid() != null && !m.getGid().equals(0)
					&& content.contains("@公司简称@") || content.contains("@客服代表@")) {
				CoBaseModel mo = selbb.getCoBaseModel(m.getGid());
				if (content.contains("@公司简称@")) {
					if (mo.getCoba_shortname() != null) {
						contentSub = content.replaceAll("@公司简称@",
								mo.getCoba_shortname());
					}
				}
				if (contentSub.contains("@客服代表@")) {
					if (mo.getCoba_client() != null) {
						contentSub = contentSub.replaceAll("@客服代表@",
								mo.getCoba_client());
					}
				}
			}
			if (contentSub.contains("@员工姓名@")) {
				if (m.getEmba_name() != null) {
					contentSub = contentSub.replaceAll("@员工姓名@",
							m.getEmba_name());
				}
			}
			emailm.setContent(contentSub);
			emailm.setEmail(m.getEmba_email());
			emailm.setTitle(emailtitle);
			try {
				k = k + bll.EmailSend(emailm);
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return k;
	}

	// 检测是否有员工没有手机号码
	private String isHasMobile() {
		String str = "";
		for (EmbaseModel m : list) {
			if (m.getEmba_mobile() == null || m.getEmba_mobile().equals("")) {
				str = "有部分员工个没有手机号码";
				break;
			}
		}
		return str;
	}

	// 检测是否有员工没有Email
	private String isHasEmail() {
		String str = "";
		for (EmbaseModel m : list) {
			if (m.getEmba_email() == null || m.getEmba_email().equals("")) {
				str = "有部分员工个没有电子邮箱";
				break;
			}
		}
		return str;
	}

	@Command
	@NotifyChange("content")
	public void sumbitemail(@BindingParam("win") Window win) {
		if (content != null && !content.equals("")) {
			String isHasEmail = isHasEmail();
			String str = "";
			if (!isHasEmail.equals("")) {
				str = "部分员工的电子邮箱为空,确定要发送信息吗？";
			}
			Integer k = 0;
			if(emailtitle==null||emailtitle.equals(""))
			{
				Messagebox.show("邮件标题不能为空", "提示", Messagebox.OK, Messagebox.ERROR);
				return;
			}
			if (str.equals("")) {
				/****************** 把文件上传到服务器 **************************/
				if (media != null) {
					String realPath = "SmsMessage/uploadfile/";
					String filepath = FileOperate.getAbsolutePath() + realPath
							+ filename;
					boolean flag = FileOperate.upload(media, realPath
							+ filename);
				}
				/****************** 把文件上传到服务器END **************************/
				k = sendEmail();
			} else {
				if (Messagebox.show(str, "操作提示",
						Messagebox.YES | Messagebox.NO, Messagebox.QUESTION) == Messagebox.YES) {
					k = sendEmail();
				}
			}
			if (k > 0) {
				content = "";
				Clients.showNotification("发送成功", "info", win, "middle_center",
						3000);
			}
		} else {
			Messagebox.show("邮件内容不能为空", "提示", Messagebox.OK, Messagebox.ERROR);
		}
	}

	// 检测剩余输入数量
	@Command
	public void changecontent() {
		/*
		 * if (content.length() >= 70) { content = content.substring(0, 70);
		 * surplus = 0; } else { surplus = 70 - content.length(); }
		 */
	}

	// 获取上传的文件
	@Command
	@NotifyChange({ "filename", "list" })
	public void upfile(@ContextParam(ContextType.TRIGGER_EVENT) UploadEvent ev,
			@BindingParam("type") String type) {
		media = null;
		inputStream = null;
		media = ev.getMedia();
		if (media != null) {
			if (media.getFormat().equals("xls") || media.getFormat() == "xls"
					|| media.getFormat().equals("xlsx")
					|| media.getFormat() == "xlsx") {
				try {
					this.filename = media.getName();
					this.inputStream = media.getStreamData();
					if (type.equals("sms")) {
						uploadSms();
					}
					else
					{
						uploadEmail();
					}
				} catch (Exception e) {
					System.out.println("错误:" + e.getMessage());
				}
			} else {
				Messagebox.show("选择的文件格式不正确", "提示", Messagebox.OK,
						Messagebox.ERROR);
			}
		}
	}

	public void uploadSms() {
		if (inputStream != null) {
			// 创建对Excel工作簿文件的引用
			HSSFWorkbook workbook;
			try {
				workbook = new HSSFWorkbook(inputStream);
				// 创建对工作表的引用。
				// 本例是按名引用（让我们假定那张表有着缺省名"Sheet1"）
				HSSFSheet sheet = workbook.getSheetAt(0);
				// 也可用getSheetAt(int index)按索引引用，
				// 在Excel文档中，第一张工作表的缺省索引是0，
				// 其语句为：
				// 读取左上端单元
				HSSFRow row;
				for (int i = 1; i < sheet.getPhysicalNumberOfRows(); i++) {
					row = sheet.getRow(i);
					String emba_name = "";
					String emba_idcard = "";
					String emba_mobile = "";
					String emba_email = "";
					if (row.getCell(0) != null) {
						row.getCell(0).setCellType(Cell.CELL_TYPE_STRING);
						if (row.getCell(0).getStringCellValue() != null) {
							emba_name = row.getCell(0).getStringCellValue()
									.toString();
						}
					}
					if (row.getCell(1) != null) {
						row.getCell(1).setCellType(Cell.CELL_TYPE_STRING);
						if (row.getCell(1).getStringCellValue() != null) {
							emba_idcard = row.getCell(1).getStringCellValue()
									.toString();
						}
					}
					if (row.getCell(2) != null) {
						row.getCell(2).setCellType(Cell.CELL_TYPE_STRING);
						if (row.getCell(2).getStringCellValue() != null) {
							emba_mobile = row.getCell(2).getStringCellValue()
									.toString();
						}
					}
					if (row.getCell(3) != null) {
						row.getCell(3).setCellType(Cell.CELL_TYPE_STRING);
						if (row.getCell(3).getStringCellValue() != null) {
							emba_email = row.getCell(3).getStringCellValue()
									.toString();
						}
					}
					EmbaseModel m = new EmbaseModel();
					m.setEmba_name(emba_name);
					m.setEmba_mobile(emba_mobile);
					m.setEmba_email(emba_email);
					m.setEmba_idcard(emba_idcard);
					if ((emba_name == null || emba_name.equals(""))
							&& (emba_mobile == null || emba_mobile.equals(""))
							&& (emba_email == null || emba_email.equals(""))) {
						break;
					}
					list.add(m);
				}

			} catch (FileNotFoundException e) {
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	
	public void uploadEmail() {
		if (inputStream != null) {
			// 创建对Excel工作簿文件的引用
			HSSFWorkbook workbook;
			try {
				workbook = new HSSFWorkbook(inputStream);
				// 创建对工作表的引用。
				// 本例是按名引用（让我们假定那张表有着缺省名"Sheet1"）
				HSSFSheet sheet = workbook.getSheetAt(0);
				// 也可用getSheetAt(int index)按索引引用，
				// 在Excel文档中，第一张工作表的缺省索引是0，
				// 其语句为：
				// 读取左上端单元
				HSSFRow row;
				for (int i = 1; i < sheet.getPhysicalNumberOfRows(); i++) {
					row = sheet.getRow(i);
					String emba_name = "";
					String emba_idcard = "";
					String emba_mobile = "";
					String emba_email = "";
					if (row.getCell(0) != null) {
						row.getCell(0).setCellType(Cell.CELL_TYPE_STRING);
						if (row.getCell(0).getStringCellValue() != null) {
							emba_name = row.getCell(0).getStringCellValue()
									.toString();
						}
					}
					if (row.getCell(1) != null) {
						row.getCell(1).setCellType(Cell.CELL_TYPE_STRING);
						if (row.getCell(1).getStringCellValue() != null) {
							emba_idcard = row.getCell(1).getStringCellValue()
									.toString();
						}
					}
					if (row.getCell(2) != null) {
						row.getCell(2).setCellType(Cell.CELL_TYPE_STRING);
						if (row.getCell(2).getStringCellValue() != null) {
							emba_email = row.getCell(2).getStringCellValue()
									.toString();
						}
					}
					
					EmbaseModel m = new EmbaseModel();
					m.setEmba_name(emba_name);
					m.setEmba_email(emba_email);
					m.setEmba_idcard(emba_idcard);
					if ((emba_name == null || emba_name.equals(""))
							&& (emba_mobile == null || emba_mobile.equals(""))
							&& (emba_email == null || emba_email.equals(""))) {
						break;
					}
					list.add(m);
				}

			} catch (FileNotFoundException e) {
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}


	@Command
	@NotifyChange("list")
	public void submiteamil() {
		String realPath = "SmsMessage/uploadfile/";
		String filepath = FileOperate.getAbsolutePath() + realPath + filename;
		boolean flag = FileOperate.upload(media, realPath + filename);
		// 创建对Excel工作簿文件的引用
		HSSFWorkbook workbook;
		try {
			workbook = new HSSFWorkbook(new FileInputStream(filepath));
			HSSFSheet sheet = workbook.getSheetAt(0);
			HSSFRow row;
			for (int i = 1; i < sheet.getPhysicalNumberOfRows(); i++) {
				row = sheet.getRow(i);
				String emba_name = "";
				String emba_email = "";
				if (row.getCell(0) != null) {
					row.getCell(0).setCellType(Cell.CELL_TYPE_STRING);
					if (row.getCell(0).getStringCellValue() != null) {
						emba_name = row.getCell(0).getStringCellValue()
								.toString();
					}
				}
				if (row.getCell(1) != null) {
					row.getCell(1).setCellType(Cell.CELL_TYPE_STRING);
					if (row.getCell(1).getStringCellValue() != null) {
						emba_email = row.getCell(1).getStringCellValue()
								.toString();
					}
				}
				EmbaseModel m = new EmbaseModel();
				m.setEmba_name(emba_name);
				m.setEmba_email(emba_email);
				list.add(m);
			}
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	// 下载批量发短信模板
	@Command
	public void loadfile(@BindingParam("type") String type) {
		String filename = "sms.xls";
		if (type != null && type.equals("email")) {
			filename = "email.xls";
		}
		String absolutePath = FileOperate.getAbsolutePath();
		String path = absolutePath + "SmsMessage/uploadfile/" + filename;
		File file = new File(path);
		try {
			Filedownload.save(file, "xlsx");
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Command
	@NotifyChange("content")
	public void onhead(@BindingParam("headflag") String headflag) {
		if (headflag.equals("1")) {
			content = content + "@员工姓名@";
		} else if (headflag.equals("2")) {
			content = content + "@公司简称@";
		} else if (headflag.equals("3")) {
			content = content + "@客服代表@";
		}
	}
	
	@Command
	@NotifyChange("distitle")
	public void sendemail(@BindingParam("cb") Checkbox cb)
	{
		if(cb.isChecked())
		{
			distitle=true;
		}else
		{
			distitle=false;
		}
	}

	public List<EmbaseModel> getList() {
		return list;
	}

	public void setList(List<EmbaseModel> list) {
		this.list = list;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public Integer getSurplus() {
		return surplus;
	}

	public void setSurplus(Integer surplus) {
		this.surplus = surplus;
	}

	public List<String> getLoginlist() {
		return loginlist;
	}

	public void setLoginlist(List<String> loginlist) {
		this.loginlist = loginlist;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getFilename() {
		return filename;
	}

	public void setFilename(String filename) {
		this.filename = filename;
	}

	public String getHeadstr() {
		return headstr;
	}

	public void setHeadstr(String headstr) {
		this.headstr = headstr;
	}

	public boolean isDistitle() {
		return distitle;
	}

	public void setDistitle(boolean distitle) {
		this.distitle = distitle;
	}

	public String getEmailtitle() {
		return emailtitle;
	}

	public void setEmailtitle(String emailtitle) {
		this.emailtitle = emailtitle;
	}
	
}
