package Controller.CoPolicyNotice;

import impl.MessageImpl;
import impl.MessageImplProxy;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.zkoss.bind.annotation.BindingParam;
import org.zkoss.bind.annotation.Command;
import org.zkoss.bind.annotation.ContextParam;
import org.zkoss.bind.annotation.ContextType;
import org.zkoss.bind.annotation.NotifyChange;
import org.zkoss.util.media.Media;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.EventListener;
import org.zkoss.zk.ui.event.Events;
import org.zkoss.zk.ui.event.UploadEvent;
import org.zkoss.zk.ui.util.Clients;
import org.zkoss.zul.A;
import org.zkoss.zul.Bandbox;
import org.zkoss.zul.Checkbox;
import org.zkoss.zul.Hlayout;
import org.zkoss.zul.Label;
import org.zkoss.zul.ListModelList;
import org.zkoss.zul.Listbox;
import org.zkoss.zul.Listitem;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Vlayout;
import org.zkoss.zul.Window;
import org.zkoss.zul.Messagebox.ClickEvent;

import service.MessageService;
import Model.EamilFileModel;
import Model.EmbaseModel;
import Model.LoginModel;
import Model.SysMessageModel;
import Model.WfClassModel;
import Util.DateStringChange;
import Util.FileOperate;
import Util.UserInfo;
import bll.SysMessage.Message_SelectBll;
import bll.SysMessage.SysMessageTem_AddBll;
import bll.SysMessage.SysMessage_AddBll;

public class PoNo_Message_AddController {
	private String type = (String) Executions.getCurrent().getArg().get("type");
	private Integer id;
	private String tablename = (String) Executions.getCurrent().getArg()
			.get("tablename");
	private Integer email;
	private List<LoginModel> llist = new ArrayList<LoginModel>();
	private String content, title;
	private MessageService msgservice;
	private MessageService serviceProxy;
	private List<SysMessageModel> list = new ArrayList<SysMessageModel>();
	private SysMessageTem_AddBll plybll = new SysMessageTem_AddBll();
	private List<WfClassModel> classList = plybll.getwfclassList();
	private SysMessage_AddBll bll = new SysMessage_AddBll();
	private List<LoginModel> loginList = bll.getLoginList(" and log_id<>"
			+ UserInfo.getUserid());
	private Message_SelectBll sbll = new Message_SelectBll();
	private List<LoginModel> deptlist = new ArrayList<LoginModel>();
	private List<LoginModel> selectedlist = new ArrayList<LoginModel>();
	private String selectedname = "";
	private List<SysMessageModel> temList = sbll
			.gettemList(" and pmte_class='" + type + "'");
	private String username = UserInfo.getUsername();
	private LoginModel ml = new LoginModel();
	private String msgname = "";

	private EmbaseModel em;
	private List<EamilFileModel> filelist = new ArrayList<EamilFileModel>();
	private List<Media> mlist = new ArrayList<Media>();
	private String replyEmail="";//指定回复人
	public PoNo_Message_AddController() {
		if (Executions.getCurrent().getArg().get("id") != null) {
			id = Integer.parseInt(Executions.getCurrent().getArg().get("id")
					.toString());
		}
		if (Executions.getCurrent().getArg().get("email") != null) {
			email = Integer.parseInt(Executions.getCurrent().getArg()
					.get("email").toString());
		}
		if (Executions.getCurrent().getArg().get("list") != null) {
			llist = (List<LoginModel>) Executions.getCurrent().getArg()
					.get("list");
		}
		if (Executions.getCurrent().getArg().get("content") != null) {
			content = Executions.getCurrent().getArg().get("content")
					.toString();
		}
		if (Executions.getCurrent().getArg().get("title") != null) {
			title = Executions.getCurrent().getArg().get("title").toString();
		}
		//指定回复人
		if (Executions.getCurrent().getArg().get("replyEmail") != null) {
			replyEmail = Executions.getCurrent().getArg().get("replyEmail").toString();
		}

		if (llist.size() > 0) {
			for (LoginModel m : llist) {
				if ((m.getLog_name() != null && !m.getLog_name().equals(""))
						|| m.getLog_id() != 0) {
					ml = m;
					selectedlist.add(ml);
					getSelectname();
					if (msgname == "") {
						msgname = "'" + m.getLog_name() + "'";
					} else {
						msgname = msgname + ",'" + m.getLog_name() + "'";
					}
				}
			}
		}
		msgservice = new MessageImpl(tablename, id);
		serviceProxy = new MessageImplProxy(msgservice);
		list = serviceProxy.SelectList();
		deptlist = sbll.getLoginList(msgname);
		// 把登陆人的该业务ID的未读信息改为已读
		serviceProxy.Read();
	}

	// 发短信
	@Command
	@NotifyChange({ "list", "content" })
	public void send(@BindingParam("win") final Window win,
			@BindingParam("cb") final Checkbox cb)
			throws NoSuchMethodException, SecurityException,
			IllegalAccessException, IllegalArgumentException,
			InvocationTargetException, InstantiationException {
		String errstr = isEmploy();
		if(replyEmail==null||replyEmail.equals(""))
		{
			List<LoginModel> lList = bll.getLoginList(" and log_id="
				+ UserInfo.getUserid());
			if(lList.size()>0)
			{
				replyEmail=lList.get(0).getLog_email();
			}
		}
		
		if (errstr == "") {
			Messagebox.show("确认提交数据?", "操作提示", new Messagebox.Button[] {
					Messagebox.Button.OK, Messagebox.Button.CANCEL },
					Messagebox.QUESTION,
					new EventListener<Messagebox.ClickEvent>() {
						@Override
						public void onEvent(ClickEvent event) throws Exception {
							// TODO Auto-generated method stub

							if (Messagebox.Button.OK.equals(event.getButton())) {
								Integer k = 0;
								String[] str = new String[5];
								for (LoginModel m : selectedlist) {
									SysMessageModel model = new SysMessageModel();
									model.setSyme_content(content);// 短信内容
									model.setSyme_url("");
									model.setSyme_reply_id(0);
									model.setSyme_log_id(Integer
											.parseInt(UserInfo.getUserid()));// 发件人id
									model.setSmwr_type("");
									model.setSymr_name(m.getLog_name());// 收件人姓名
									model.setSymr_log_id(m.getLog_id());//收件人id
									model.setReplyEmailAddress(replyEmail);//指定回复人邮件
									if (cb.isChecked()) {
										model.setEmail(1);
									} else {
										model.setEmail(0);
									}
									if (title != null && !title.equals("")) {

										model.setSyme_title(title);
										model.setEmailtitle(title);
									}
									String[][] filename = new String[mlist
											.size()][2];
									String RelativePath = "OfficeFile/UpLoad/Email/";
									Integer fIndex = 0;
									String realPath = FileOperate
											.getAbsolutePath() + RelativePath;
									for (Media media : mlist) {
										String name = "note"
												+ UserInfo.getUserid()
												+ DateStringChange.DatetoSting(
														new Date(),
														"yyyyMMddhhmmss") + "."
												+ media.getFormat();

										boolean flag = FileOperate.upload(
												media, RelativePath + name);
										if (flag) {
											filename[fIndex][0] = media
													.getName();
											filename[fIndex][1] = realPath
													+ name;
											fIndex++;
										}

									}

									model.setFileurl(filename);
									str = serviceProxy.Add(model);
									if (str[0] == "1") {
										k = k + 1;
									}
								}
								if (k <= 0) {
									if (str[1] != null && !str[1].equals("")) {
										Messagebox
												.show(str[1], "提示",
														Messagebox.OK,
														Messagebox.ERROR);
									} else {
										Messagebox
												.show("发送失败", "提示",
														Messagebox.OK,
														Messagebox.ERROR);
									}
								} else {
									Clients.showNotification("发送成功", "info",
											win, "middle_center", 3000);
									content = "";
									list = serviceProxy.SelectList();
								}
							}
						}
					});
		} else {
			Messagebox.show(errstr, "提示", Messagebox.OK, Messagebox.ERROR);
		}
	}

	// 判断必填是否为空
	private String isEmploy() {
		String str = "";
		if (selectedlist.size() <= 0) {
			str = "请选择收件人";
		} else if (content == null || content.equals("")) {
			str = "请填写短信内容";
		}
		return str;
	}

	// 选择收件人
	@Command
	@NotifyChange("selectedname")
	public void checkname(@BindingParam("bd") Bandbox bx,
			@BindingParam("model") LoginModel model,
			@BindingParam("lbitem") Listitem item) {
		if (item.isSelected()) {
			int k=0;
			for(LoginModel mk:selectedlist)
			{
				if (mk.getLog_id() != 0 && mk.getLog_id() == model
						.getLog_id()) {
					k=1;
					break;
				}
			}
			if(k==0)
			{
				selectedlist.add(model);
			}
		} else {
			selectedlist.remove(model);
			if (selectedlist.size() > 0) {
				for (LoginModel lsm : selectedlist) {
					if ((lsm.getLog_id() != 0 && lsm.getLog_id() == model
							.getLog_id())
							|| (lsm.getLog_name() != null && model
									.getLog_name().equals(lsm.getLog_name()))) {
						selectedlist.remove(lsm);
						break;
					}
				}
			}
		}
		getSelectname();
	}
	
	@Command
	@NotifyChange("selectedname")
	public void checkall(@BindingParam("bd") Bandbox bx,
			@BindingParam("lb") Listbox lb)
	{
		List<Listitem> items=lb.getItems();
		for(int i=0;i<items.size();i++)
		{
			Listitem item=items.get(i);
			LoginModel m=item.getValue();
			int k=0;
			if(item.isSelected())
			{
				if (selectedlist.size() > 0) {
					for (LoginModel lsm : selectedlist) {
						if ((lsm.getLog_id() != 0 && lsm.getLog_id() == m
								.getLog_id())
								|| (lsm.getLog_name() != null && m
										.getLog_name().equals(lsm.getLog_name()))) {
							k=1;
							break;
						}
					}
				}
				if(k==0)
				{
					selectedlist.add(m);
				}
			}
			else
			{
				if (selectedlist.size() > 0) {
					for (LoginModel lsm : selectedlist) {
						if ((lsm.getLog_id() != 0 && lsm.getLog_id() == m
								.getLog_id())
								|| (lsm.getLog_name() != null && m
										.getLog_name().equals(lsm.getLog_name()))) {
							selectedlist.remove(lsm);
							break;
						}
					}
				}
			}
		}
		getSelectname();
	}

	// 把已选择的收件人串起来
	private void getSelectname() {
		selectedname = "";
		for (LoginModel m : selectedlist) {
			if (selectedname == "") {
				selectedname = m.getLog_name();
			} else {
				String[] sname = selectedname.split(";");
				boolean flag = true;
				for (int i = 0; i < sname.length; i++) {
					if (sname[i] != null && sname[i] != null
							&& sname[i].equals(m.getLog_name())) {
						flag = false;
					}
				}
				if (flag) {
					selectedname = selectedname + ";" + m.getLog_name();
				}
			}
		}
	}

	@Command
	@NotifyChange("list")
	public void read(@BindingParam("model") SysMessageModel model,
			@BindingParam("win") Window win) {
		Integer k = serviceProxy.Read();
		if (k > 0) {
			Clients.showNotification("已阅读", "info", win, "middle_center", 3000);
			list = serviceProxy.SelectList();
		}
	}

	// 回复
	@Command
	@NotifyChange("list")
	public void reply(@BindingParam("model") SysMessageModel model,
			@BindingParam("win") Window win) {
		// LoginModel m=new LoginModel();
		// m.setLog_id(model.getSyme_log_id());
		// m.setLog_name(model.getSyme_addname());
		// selectedlist.add(m);
		// getSelectname();
		// selectedlist.clear();
		Map map = new HashMap<>();
		map.put("type", type);
		map.put("id", id);
		map.put("tablename", tablename);
		map.put("model", model);
		map.put("flag", "0");
		Window window = (Window) Executions.createComponents(
				"../SysMessage/Message_ReplyAdd.zul", null, map);
		window.doModal();
		list = serviceProxy.SelectList();
		if (map.get("flag") != null && map.get("flag").equals("1")) {
			Clients.showNotification("回复成功", "info", win, "middle_center", 3000);
		}
	}

	private void SendEmail() {
		String[] AttachFile = new String[2];
		String[] toaddress = new String[2]; // 目标邮件

	}

	// 添加附件
	@Command
	@NotifyChange("filelist")
	public void upfile(@ContextParam(ContextType.TRIGGER_EVENT) UploadEvent ev,
			@BindingParam("hlt") Vlayout vlt) {
		String RelativePath = "OfficeFile/UpLoad/Email/";
		Media media = ev.getMedia();
		if (media != null) {
			EamilFileModel newfm = new EamilFileModel();
			String filename = media.getName();
			Integer existId = 0;
			for (EamilFileModel fm : filelist) {
				if (fm.getFilename() != null
						&& fm.getFilename().equals(filename)) {
					existId = 1;
					break;
				}
			}
			if (existId == 1) {
				Messagebox.show("该文件已在已选列表中，不能重复选择", "提示", Messagebox.OK,
						Messagebox.ERROR);
				return;
			}
			mlist.add(media);
			String realPath = FileOperate.getAbsolutePath() + RelativePath;
			newfm.setFilename(filename);
			newfm.setFileurl(realPath);
			filelist.add(newfm);
			final Hlayout hl = new Hlayout();
			hl.setHflex("1");
			hl.setParent(vlt);
			Label bel = new Label(newfm.getFilename());
			bel.setParent(hl);
			A rm = new A("删除");
			rm.addEventListener(Events.ON_CLICK,
					new org.zkoss.zk.ui.event.EventListener() {
						@Override
						public void onEvent(Event arg0) throws Exception {
							Label lel = (Label) hl.getChildren().get(0);
							String lelVal = lel.getValue();
							for (EamilFileModel m : filelist) {
								if (lelVal.equals(m.getFilename())) {
									filelist.remove(m);
									break;
								}
							}
							for (Media media : mlist) {
								if (lelVal.equals(media.getName())) {
									mlist.remove(media);
									break;
								}
							}
							hl.detach();
						}
					});
			hl.appendChild(rm);
		} else {
			Messagebox.show("该文件已在已选列表中，不能重复选择", "提示", Messagebox.OK,
					Messagebox.ERROR);
		}

	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public List<SysMessageModel> getList() {
		return list;
	}

	public void setList(List<SysMessageModel> list) {
		this.list = list;
	}

	public List<WfClassModel> getClassList() {
		return classList;
	}

	public void setClassList(List<WfClassModel> classList) {
		this.classList = classList;
	}

	public List<LoginModel> getLoginList() {
		return loginList;
	}

	public void setLoginList(List<LoginModel> loginList) {
		this.loginList = loginList;
	}

	public List<LoginModel> getDeptlist() {
		return deptlist;
	}

	public void setDeptlist(List<LoginModel> deptlist) {
		this.deptlist = deptlist;
	}

	public String getSelectedname() {
		return selectedname;
	}

	public void setSelectedname(String selectedname) {
		this.selectedname = selectedname;
	}

	public List<SysMessageModel> getTemList() {
		return temList;
	}

	public void setTemList(List<SysMessageModel> temList) {
		this.temList = temList;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public EmbaseModel getEm() {
		return em;
	}

	public void setEm(EmbaseModel em) {
		this.em = em;
	}
}
