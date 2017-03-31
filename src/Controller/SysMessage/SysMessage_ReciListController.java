package Controller.SysMessage;

import impl.MessageImpl;
import impl.SysMessageImpl;
import impl.UserInfoImpl;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.zkoss.bind.annotation.BindingParam;
import org.zkoss.bind.annotation.Command;
import org.zkoss.bind.annotation.NotifyChange;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.Session;
import org.zkoss.zk.ui.util.Clients;
import org.zkoss.zul.A;
import org.zkoss.zul.Combobox;
import org.zkoss.zul.Hlayout;
import org.zkoss.zul.Label;
import org.zkoss.zul.ListModelList;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Textbox;
import org.zkoss.zul.Vlayout;
import org.zkoss.zul.Window;

import service.MessageService;
import service.SysMessageService;
import service.UserInfoService;

import bll.SysMessage.SysMessageTem_AddBll;
import bll.SysMessage.SysMessageTem_ManageBll;
import bll.SysMessage.SysMessage_NotReadInfoListBll;
import bll.SysMessage.SysMessage_ReciListBll;
import bll.SysMessage.SysMessage_ReplyBll;

import Model.LoginModel;
import Model.SysMessageModel;
import Model.WfClassModel;
import Util.UserInfo;
import Util.FormatUtil;

public class SysMessage_ReciListController {

	private List<SysMessageModel> reciList = new ListModelList<SysMessageModel>();
	private List<SysMessageModel> replyList = new ListModelList<SysMessageModel>();
	private List<WfClassModel> classList = new ListModelList<WfClassModel>();
	private ListModelList<SysMessageModel> temList = new ListModelList<SysMessageModel>();
	private SysMessageModel temModel = new SysMessageModel();
	private SysMessageModel replyMd = new SysMessageModel();
	int userid = 0;
	private Date time = null;
	private String searcontent = "";
	private boolean isall = false;
	private String content = "";
	private String title = "";
	private String username=UserInfo.getUsername();

	// 获取session，实例化UserInfoService接口
	Session session = Executions.getCurrent().getDesktop().getSession();
	UserInfoService user = new UserInfoImpl(session);

	// 初始化
	public SysMessage_ReciListController() {
		userid = Integer.parseInt(UserInfo.getUserid());
		SysMessage_ReciListBll bll = new SysMessage_ReciListBll();
		SysMessageTem_ManageBll tbll = new SysMessageTem_ManageBll();
		SysMessageService smService = new SysMessageImpl();

		setReciList(bll.getReciList(userid, ""));
		if (reciList.size() > 0) {
			setReplyMd(reciList.get(0));
			setReplyList(smService
					.findAll(reciList.get(0).getSyme_id(), userid));
		}
		setTemList(tbll.gettemList(""));
		temList.add(0, new SysMessageModel());
		setClassList(new SysMessageTem_AddBll().getwfclassList());
	}

	@Command("search")
	@NotifyChange({ "reciList", "replyMd", "replyList" })
	public void search() {
		String str = "";
		if (time != null) {
			String timestr = new SimpleDateFormat("yyyy-MM-dd").format(time);
			str += " and convert(nvarchar(10),syme_addtime,120)='" + timestr
					+ "'";
		}
		if (!searcontent.isEmpty()) {
			str += " and (syme_content like '%" + searcontent
					+ "%' or syme_addname like '%" + searcontent + "%'"
					+ " or syme_title like '%" + searcontent + "%')";
		}
		try {
			userid = Integer.parseInt(user.getUserid());
		} catch (Exception e) {
			userid = 0;
		}
		SysMessage_ReciListBll bll = new SysMessage_ReciListBll();
		SysMessageService smService = new SysMessageImpl();
		setReciList(bll.getReciList(userid, str));
		if (reciList.size() > 0) {
			setReplyMd(reciList.get(0));
			setReplyList(smService
					.findAll(reciList.get(0).getSyme_id(), userid));
		}
	}

	// 选择模板
	@Command("temselect")
	@NotifyChange({ "content", "title" })
	public void temselect(@BindingParam("cbbClass") Combobox cbbClass) {
		try {
			content = temModel.getPmte_content();
		} catch (Exception e) {
			content = "";
		}
		if (content == null) {
			title = "";
			cbbClass.setButtonVisible(true);
		} else {
			title = temModel.getPmte_class();
			cbbClass.setButtonVisible(false);
		}
	}

	// 弹窗
	@Command("reply")
	@NotifyChange("replyList")
	public void reply() {
		if (reciList.size() != 0) {
			SysMessageService smService = new SysMessageImpl();

			Map map = new HashMap();
			map.put("model", replyMd);
			Window window = (Window) Executions.createComponents(
					"/SysMessage/SysMessage_Reply.zul", null, map);
			window.doModal();
			setReplyList(smService.findAll(replyMd.getSyme_id(), userid));
		} else {
			Messagebox.show("没有可回复的信息!", "ERROR", Messagebox.YES,
					Messagebox.ERROR);
		}
	}

	// 弹出业务窗体
	@Command("opennew")
	public void opennew(@BindingParam("win") Window win,
			@BindingParam("model") SysMessageModel model) {
		Map<String, Object> map = new HashMap<String, Object>();
		map = FormatUtil.StringToMap(model.getSyme_para());

		win.detach();
		Window window = (Window) Executions.createComponents(
				model.getSyme_url(), null, map);
		window.doModal();
	}

	// 选择一条信息
	@Command("selectCon")
	@NotifyChange({ "content", "recipient", "replyList","reciList" })
	public void selectCon(@BindingParam("each") SysMessageModel model) {
		SysMessageService smService = new SysMessageImpl();
		setReplyMd(model);
		setReplyList(smService.findAll(model.getSyme_id(), userid));
		MessageService msgservice=new MessageImpl();
		for(SysMessageModel m:replyList)
		{
			if(m.getSymr_id()!=null&&m.getSymr_readstate()==0)
			{
				Integer k=msgservice.Read(m.getSymr_id());
			}
		}
		SysMessage_ReciListBll bll = new SysMessage_ReciListBll();
		reciList=bll.getReciList(userid, "");
	}

	@Command("quickreply")
	@NotifyChange({ "content", "isall", "temModel" })
	public void quickreply() throws Exception {
		if (content.isEmpty()) {
			Messagebox
					.show("内容不能为空!", "ERROR", Messagebox.OK, Messagebox.ERROR);
		} else {

			SysMessageService smService = new SysMessageImpl();
			SysMessage_ReplyBll bll = new SysMessage_ReplyBll();
			List<SysMessageModel> list = new ArrayList<SysMessageModel>();
			List<LoginModel> lgList = new ArrayList<LoginModel>();
			LoginModel lmModel = new LoginModel();

			if (isall) {
				lgList = bll.getReplyList(replyMd.getSyme_id(), userid);
				for (int i = 0; i < lgList.size(); i++) {
					SysMessageModel smModel = new SysMessageModel();
					smModel.setSymr_log_id(lgList.get(i).getLog_id());
					smModel.setSymr_name(lgList.get(i).getLog_name());
					list.add(smModel);
				}
			} else {
				lmModel = bll.getSenderModel(replyMd.getSyme_id());
				SysMessageModel smModel = new SysMessageModel();
				smModel.setSymr_log_id(lmModel.getLog_id());
				smModel.setSymr_name(lmModel.getLog_name());
				list.add(smModel);
			}

			int issuccess = smService.add(title, content, null, null, 1,
					replyMd.getSyme_id(), list);
			if (issuccess == 1) {
				if (Messagebox.show("发送成功!", "INFORMATION", Messagebox.YES,
						Messagebox.INFORMATION) == Messagebox.YES) {
					content = "";
					isall = false;
					temModel = null;
				} else {
					Messagebox.show("发送失败,请联系IT部门!", "ERROR", Messagebox.YES,
							Messagebox.ERROR);
				}
			}
		}
	}
	
	//点击回复
	@Command
	public void qureply(@BindingParam("model") SysMessageModel ml,@BindingParam("vl") Vlayout vl,
			@BindingParam("aa") A a)
	{
		Integer i=vl.getChildren().size();
		Label lb=(Label) a.getChildren().get(0);
		if(lb.getValue().equals("回复"))
		{
			if(i>0)
			{
				Hlayout hl=(Hlayout) vl.getChildren().get(i-1);
				hl.setVisible(true);
				lb.setValue("取消");
			}
		}
		else
		{
			Hlayout hl=(Hlayout) vl.getChildren().get(i-1);
			hl.setVisible(false);
			lb.setValue("回复");
		}
	}
	
	//回复发送
	@Command
	@NotifyChange("replyList")
	public void sendmsg(@BindingParam("model") SysMessageModel ml,@BindingParam("hl") Hlayout hl)
	{
		if(hl.getChildren().get(0)!=null)
		{
			Textbox tx=(Textbox) hl.getChildren().get(0);
			String msgcontent=tx.getValue();
			if(msgcontent!=null&&!msgcontent.equals(""))
			{
				MessageService msgservice=new MessageImpl();
				SysMessageModel model = new SysMessageModel();
				model.setSyme_content(msgcontent);
				model.setSyme_title("");
				model.setSyme_reply_id(ml.getSyme_reply_id());
				model.setEmail(0);
				model.setEmailtitle("");
				model.setSymr_log_id(ml.getSyme_log_id());
				model.setSymr_name(ml.getSyme_addname());
				model.setSyme_log_id(Integer.parseInt(UserInfo.getUserid()));
				model.setSyme_id(ml.getSyme_id());
				String[] st=msgservice.Reply(model);
				if(st[0]=="1")
				{
					hl.setVisible(false);
					SysMessageService smService = new SysMessageImpl();
					setReplyList(smService.findAll(model.getSyme_id(), userid));
					Clients.showNotification("发送成功","info",tx,"middle_center",300);
				}
				else
				{
					Clients.showNotification("发送失败","info",tx,"middle_center",2000);
				}
			}
			else
			{
				Clients.showNotification("短信内容不能为空","info",tx,"middle_center",2000);
			}
		}
	}

	public List<SysMessageModel> getReciList() {
		return reciList;
	}

	public void setReciList(List<SysMessageModel> reciList) {
		this.reciList = reciList;
	}

	public Date getTime() {
		return time;
	}

	public void setTime(Date time) {
		this.time = time;
	}

	public List<SysMessageModel> getReplyList() {
		return replyList;
	}

	public void setReplyList(List<SysMessageModel> replyList) {
		this.replyList = replyList;
	}

	public SysMessageModel getReplyMd() {
		return replyMd;
	}

	public void setReplyMd(SysMessageModel replyMd) {
		this.replyMd = replyMd;
	}

	public String getSearcontent() {
		return searcontent;
	}

	public void setSearcontent(String searcontent) {
		this.searcontent = searcontent;
	}

	public boolean isIsall() {
		return isall;
	}

	public void setIsall(boolean isall) {
		this.isall = isall;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public ListModelList<SysMessageModel> getTemList() {
		return temList;
	}

	public void setTemList(ListModelList<SysMessageModel> temList) {
		this.temList = temList;
	}

	public SysMessageModel getTemModel() {
		return temModel;
	}

	public void setTemModel(SysMessageModel temModel) {
		this.temModel = temModel;
	}

	public List<WfClassModel> getClassList() {
		return classList;
	}

	public void setClassList(List<WfClassModel> classList) {
		this.classList = classList;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}
	
}
