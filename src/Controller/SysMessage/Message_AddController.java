package Controller.SysMessage;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import impl.MessageImpl;

import org.zkoss.bind.annotation.BindingParam;
import org.zkoss.bind.annotation.Command;
import org.zkoss.bind.annotation.NotifyChange;
import org.zkoss.zhtml.I;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.event.EventListener;
import org.zkoss.zk.ui.util.Clients;
import org.zkoss.zul.Bandbox;
import org.zkoss.zul.Checkbox;
import org.zkoss.zul.Combobox;
import org.zkoss.zul.ListModelList;
import org.zkoss.zul.Listitem;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Window;
import org.zkoss.zul.Messagebox.ClickEvent;

import bll.SysMessage.Message_SelectBll;
import bll.SysMessage.SysMessageTem_AddBll;
import bll.SysMessage.SysMessageTem_ManageBll;
import bll.SysMessage.SysMessage_AddBll;

import Model.EmArchiveDatumModel;
import Model.EmHouseChangeModel;
import Model.EmbaseModel;
import Model.LoginModel;
import Model.SysMessageModel;
import Model.WfClassModel;
import Util.UserInfo;

import service.MessageService;

public class Message_AddController {
	private String type = (String) Executions.getCurrent().getArg().get("type");
	private Integer id;
	private String tablename = (String) Executions.getCurrent().getArg()
			.get("tablename");
	private Integer email;
	private List<LoginModel> llist = new ArrayList<LoginModel>();
	private String content, title;
	private MessageService msgservice;
	private List<SysMessageModel> list = new ArrayList<SysMessageModel>();
	private SysMessageTem_AddBll plybll = new SysMessageTem_AddBll();
	private List<WfClassModel> classList = plybll.getwfclassList();
	SysMessage_AddBll bll = new SysMessage_AddBll();
	private List<LoginModel> loginList = bll.getLoginList(" and log_id<>"
			+ UserInfo.getUserid());
	private Message_SelectBll sbll = new Message_SelectBll();
	private List<LoginModel> deptlist = new ArrayList<LoginModel>();
	private List<LoginModel> selectedlist = new ArrayList<LoginModel>();
	private String selectedname = "";
	private List<SysMessageModel> temList =new ArrayList<SysMessageModel>();
	private String username = UserInfo.getUsername();
	private LoginModel ml = new LoginModel();
	private String msgname = "";
	private Integer tapr_id;

	private Object obj;
	private String method;
	private Class<?> pclass;
	private Object parameter;
	private boolean cnCheck = false;
	private boolean cnDis = false;
	private String checkName;

	private EmbaseModel em;
	private boolean disEm = false;
	private boolean distitle=true;
	private String emailtitle="";
	private boolean titlevis=false;

	public Message_AddController() {
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
		if (Executions.getCurrent().getArg().get("tapr_id") != null) {
			tapr_id = Integer.valueOf(Executions.getCurrent().getArg().get("tapr_id")
					.toString());
		}
		if (Executions.getCurrent().getArg().get("title") != null) {
			title = Executions.getCurrent().getArg().get("title").toString();
			emailtitle=title;
			if(emailtitle!=null&&!emailtitle.equals(""))
			{
				titlevis=true;
			}
		}
		if (Executions.getCurrent().getArg().get("embase") != null) {
			em = (EmbaseModel) Executions.getCurrent().getArg().get("embase");
			disEm = true;
		}

		if (llist.size() > 0) {
			for (LoginModel m : llist) {
				if (m.getLog_name() != null) {
					ml = (LoginModel) m.clone();
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
		if(type!=null&&!type.equals(""))
		{
			if(type.equals("all"))
			{
				temList =sbll
					.gettemList("");
			}else
			{
				temList =sbll
					.gettemList(" and pmte_class='" + type + "'");
			}
		}else{
			temList =sbll
					.gettemList("");
		}
		msgservice = new MessageImpl(tablename, id);
		list = msgservice.SelectList();
		deptlist = sbll.getLoginList(msgname);
		// 把登陆人的该业务ID的未读信息改为已读
		msgservice.Read();

		if (Executions.getCurrent().getArg().get("clazz") != null) {
			obj = Executions.getCurrent().getArg().get("clazz");
			if (Executions.getCurrent().getArg().get("method") != null) {
				method = Executions.getCurrent().getArg().get("method")
						.toString();
				if (Executions.getCurrent().getArg().get("pclass") != null) {
					pclass = (Class<?>) Executions.getCurrent().getArg()
							.get("pclass");
				}
				if (Executions.getCurrent().getArg().get("parameter") != null) {
					parameter = Executions.getCurrent().getArg()
							.get("parameter");
				}
				if (Executions.getCurrent().getArg().get("checkName") != null) {
					checkName = Executions.getCurrent().getArg()
							.get("checkName").toString();
					cnDis = true;
					if (checkName.equals("退回")) {
						if (type.equals("住房公积金")) {
							cnCheck = true;
							if (title != null) {
								title = title + ",公积金数据被退回";
								emailtitle=title;
							} else {
								title = "公积金数据被退回";
								emailtitle=title;
							}
						} else if (type.equals("档案")) {
							cnCheck = true;
							title = title + "被退回";
							emailtitle=title;
						}
					}else  {
						if (type.equals("住房公积金核对")) {
							cnCheck = true;
							title = title + ",公积金数据有异常";
							emailtitle=title;
							for (SysMessageModel m : temList) {
								if (checkName.equals("核查失败")) {
									
								}else{
									
								}
							}
						}
					}
				}
			}
		}

	}

	public boolean callMethod() throws NoSuchMethodException,
			SecurityException, IllegalAccessException,
			IllegalArgumentException, InvocationTargetException,
			InstantiationException {
		boolean b = false;
		Object o = null;
		if (obj != null && method != null) {
			Class clazz = obj.getClass();
			Method m = clazz.getDeclaredMethod(method, pclass);
			if (parameter != null && pclass != null) {
				if (tablename.equals("emarchivedatum")) {
					EmArchiveDatumModel mm = (EmArchiveDatumModel) parameter;
					mm.setEada_backcase(content);
					mm.setEada_tapr_id(tapr_id);
					o = m.invoke(clazz.newInstance(), mm);
				} else {
					o = m.invoke(clazz.newInstance(), parameter);
				}

			} else {
				o = m.invoke(method);

			}
			if (o != null) {
				if (o.getClass().equals(Integer.class)) {
					if ((Integer) o > 0) {
						b = true;
					}
				} else if (o.getClass().equals(Boolean.class)) {
					if ((Boolean) o) {
						b = true;
					}
				} else if (o.getClass().getName().equals("boolean")) {
					if ((boolean) o) {
						b = true;
					}
				}
			}
		}
		return b;
	}

	// 发短信
	@Command
	@NotifyChange({ "list", "content","cnDis" })
	public void send(@BindingParam("win") final Window win,
			@BindingParam("cb") final Checkbox cb)
			throws NoSuchMethodException, SecurityException,
			IllegalAccessException, IllegalArgumentException,
			InvocationTargetException, InstantiationException {
		String errstr = isEmploy();
		if (errstr == "") {
			if(cb.isChecked())
			{
				if(emailtitle==null||emailtitle.equals(""))
				{
					Messagebox.show("邮件标题不能为空", "提示",
							Messagebox.OK, Messagebox.ERROR);
					return;
				}
			}
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
								boolean b = true;
								if (cnCheck) {
									b = callMethod();
								}
								if (b) {

									for (LoginModel m : selectedlist) {
										SysMessageModel model = new SysMessageModel();
										model.setSyme_content(content);// 短信内容
										model.setSyme_url("");
										model.setSyme_reply_id(0);
										model.setSyme_log_id(Integer
												.parseInt(UserInfo.getUserid()));// 发件人id
										model.setSmwr_type("");
										model.setSymr_name(m.getLog_name());// 收件人姓名
										model.setSymr_log_id(m.getLog_id());// ;收件人id
										if (cb.isChecked()) {
											model.setEmail(1);
										} else {
											model.setEmail(0);
										}
										if (title != null && !title.equals("")) {
											model.setSyme_title(title);
										}
										else
										{
											model.setSyme_title(emailtitle);
										}
										model.setEmailtitle(emailtitle);
										str = msgservice.Add(model);
										if (str[0] == "1") {
											k = k + 1;
										}
									}
									if (k <= 0) {
										if (str[1] != null
												&& !str[1].equals("")) {
											Messagebox.show(str[1], "提示",
													Messagebox.OK,
													Messagebox.ERROR);
										} else {
											Messagebox.show("发送失败", "提示",
													Messagebox.OK,
													Messagebox.ERROR);
										}
									} else {
										if (cnCheck) {
											cnDis = false;
											cnCheck=false;
										}
										Clients.showNotification("发送成功",
												"info", win, "middle_center",
												3000);
										content = "";
										list = msgservice.SelectList();
									}
								} else {
									Messagebox.show("后台数据处理失败.", "提示",
											Messagebox.OK, Messagebox.ERROR);
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
			selectedlist.add(model);
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

	// 模板选择
	@Command
	@NotifyChange("content")
	public void temselect(@BindingParam("temcb") Combobox temcb) {
		if (temcb.getValue() != null || !temcb.getValue().equals("")) {
			content = "";
			String contentSub = "";
			SysMessageModel m = temcb.getSelectedItem().getValue();
			content = m.getPmte_content();
			if (disEm) {
				contentSub = m.getPmte_content();
				if (content.contains("@公司简称@")) {
					if (em.getCoba_company() != null) {
						contentSub = content.replaceAll("@公司简称@",
								em.getCoba_company());
					}
				}
				if (contentSub.contains("@员工姓名@")) {
					if (em.getEmba_name() != null) {
						contentSub = contentSub.replaceAll("@员工姓名@",
								em.getEmba_name());
					}
				}
				if (contentSub.contains("@时间@")) {
					if (em.getEmba_name() != null) {
						contentSub = contentSub.replaceAll("@时间@",
								em.getEmba_worktime());
					}
				}
			}
			if (!contentSub.equals("")) {
				content = contentSub;
			}
		}
	}

	@Command
	@NotifyChange("list")
	public void read(@BindingParam("model") SysMessageModel model,
			@BindingParam("win") Window win) {
		Integer k = msgservice.Read();
		if (k > 0) {
			Clients.showNotification("已阅读", "info", win, "middle_center", 3000);
			list = msgservice.SelectList();
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
		list = msgservice.SelectList();
		if (map.get("flag") != null && map.get("flag").equals("1")) {
			Clients.showNotification("回复成功", "info", win, "middle_center", 3000);
		}
	}
	
	@Command
	@NotifyChange("distitle")
	public void checkEmail(@BindingParam("ck") Checkbox ck)
	{
		if(ck.isChecked())
		{
			distitle=true;
		}
		else
		{
			distitle=false;
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

	public boolean isCnCheck() {
		return cnCheck;
	}

	public void setCnCheck(boolean cnCheck) {
		this.cnCheck = cnCheck;
	}

	public boolean isCnDis() {
		return cnDis;
	}

	public void setCnDis(boolean cnDis) {
		this.cnDis = cnDis;
	}

	public String getCheckName() {
		return checkName;
	}

	public void setCheckName(String checkName) {
		this.checkName = checkName;
	}

	public EmbaseModel getEm() {
		return em;
	}

	public void setEm(EmbaseModel em) {
		this.em = em;
	}

	public boolean isDisEm() {
		return disEm;
	}

	public void setDisEm(boolean disEm) {
		this.disEm = disEm;
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

	public boolean isTitlevis() {
		return titlevis;
	}

	public void setTitlevis(boolean titlevis) {
		this.titlevis = titlevis;
	}
	
}
