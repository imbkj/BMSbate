package Controller.SysMessage;

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
import org.zkoss.zul.ListModelList;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Window;

import service.SysMessageService;
import service.UserInfoService;

import bll.SysMessage.SysMessageTem_ManageBll;
import bll.SysMessage.SysMessage_NotReadInfoListBll;
import bll.SysMessage.SysMessage_ReplyBll;
import bll.SysMessage.SysMessage_SendListBll;

import Model.LoginModel;
import Model.SysMessageModel;
import Util.UserInfo;
import Util.FormatUtil;

public class SysMessage_SendListContoller {

	private List<SysMessageModel> sendList = new ListModelList<SysMessageModel>();
	private List<SysMessageModel> replyList = new ListModelList<SysMessageModel>();
	private ListModelList<SysMessageModel> temList = new ListModelList<SysMessageModel>();
	private SysMessageModel temModel = new SysMessageModel();
	private Date time = null;
	private SysMessageModel replyMd = new SysMessageModel();
	private String searcontent = "";
	private boolean isall = false;
	private String content = "";

	// 获取session，实例化UserInfoService接口
	Session session = Executions.getCurrent().getDesktop().getSession();
	UserInfoService user = new UserInfoImpl(session);

	int userid = 0;

	// 初始化
	public SysMessage_SendListContoller() {
		userid = Integer.parseInt(UserInfo.getUserid());
		SysMessage_SendListBll bll = new SysMessage_SendListBll();
		SysMessageTem_ManageBll tbll = new SysMessageTem_ManageBll();
		SysMessageService smService = new SysMessageImpl();

		setSendList(bll.getSendList(userid, ""));

		if (sendList.size() > 0) {
			setReplyMd(sendList.get(0));
			setReplyList(smService
					.findAll(sendList.get(0).getSyme_id(), userid));
		}
		setTemList(tbll.gettemList(""));
		temList.add(0, new SysMessageModel());
	}

	// 弹窗
	@Command("reply")
	@NotifyChange("replyList")
	public void reply() {
		SysMessageService smService = new SysMessageImpl();

		Map map = new HashMap();
		map.put("model", replyMd);
		Window window = (Window) Executions.createComponents(
				"/SysMessage/SysMessage_Reply.zul", null, map);
		window.doModal();
		setReplyList(smService.findAll(replyMd.getSyme_id(), userid));
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

	// 选择模板
	@Command("temselect")
	@NotifyChange("content")
	public void temselect() {
		try {
			content = temModel.getPmte_content();
		} catch (Exception e) {
			content = "";
		}
	}

	// 选择一条信息
	@Command("selectCon")
	@NotifyChange("replyList")
	public void selectCon(@BindingParam("each") SysMessageModel model) {
		SysMessageService smService = new SysMessageImpl();

		setReplyMd(model);
		setReplyList(smService.findAll(model.getSyme_id(), userid));
	}

	@Command("search")
	@NotifyChange("sendList")
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
		SysMessage_SendListBll bll = new SysMessage_SendListBll();
		setSendList(bll.getSendList(userid, str));
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

			String title = temModel.getPmte_class();

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

	public List<SysMessageModel> getSendList() {
		return sendList;
	}

	public void setSendList(List<SysMessageModel> sendList) {
		this.sendList = sendList;
	}

	public List<SysMessageModel> getReplyList() {
		return replyList;
	}

	public void setReplyList(List<SysMessageModel> replyList) {
		this.replyList = replyList;
	}

	public Date getTime() {
		return time;
	}

	public void setTime(Date time) {
		this.time = time;
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

}
