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
import org.zkoss.zhtml.Em;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.Session;
import org.zkoss.zul.ListModelList;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Window;

import bll.SysMessage.SysMessage_DraftListBll;
import bll.SysMessage.SysMessage_EditBll;
import bll.SysMessage.SysMessage_ReplyBll;

import service.SysMessageService;
import service.UserInfoService;

import Model.LoginModel;
import Model.SysMessageModel;
import Util.UserInfo;

public class SysMessage_DraftListController {

	private List<SysMessageModel> draftList;
	private List<SysMessageModel> replyList = new ListModelList<SysMessageModel>();
	private Date time = null;
	int userid = 0;
	private SysMessageModel edModel = new SysMessageModel();
	private String searcontent = "";

	// 初始化
	public SysMessage_DraftListController() {
		userid = Integer.parseInt(UserInfo.getUserid());
		SysMessage_DraftListBll bll = new SysMessage_DraftListBll();
		setDraftList(new ListModelList<SysMessageModel>(bll.getDraftList(
				userid, "")));
		if (draftList.size() > 0) {
			edModel = draftList.get(0);
			setReplyList(bll.findAll(draftList.get(0).getSyme_id(), userid));
		}
	}

	// 检索
	@Command("search")
	@NotifyChange({"draftList","replyList"})
	public void search() {
		String str = "";
		if (time != null) {
			String timestr = new SimpleDateFormat("yyyy-MM-dd").format(time);
			str += " and convert(nvarchar(8),syme_addtime,120)='" + timestr
					+ "'";
		}
		if (!searcontent.isEmpty()) {
			str += " and (syme_content like '%" + searcontent
					+ "%' or syme_addname like '%" + searcontent + "%'"
					+ " or syme_title like '%" + searcontent + "%')";
		}
		SysMessage_DraftListBll bll = new SysMessage_DraftListBll();
		setDraftList(new ListModelList<SysMessageModel>(bll.getDraftList(
				userid, str)));
		if (draftList.size() > 0) {
			edModel = draftList.get(0);
			setReplyList(bll.findAll(draftList.get(0).getSyme_id(), userid));
		} else {
			edModel = null;
			replyList = null;
		}
	}

	// 弹出编辑窗口
	@Command("edit")
	@NotifyChange({"draftList","replyList"})
	public void edit() {
		if (!edModel.getSyme_content().isEmpty()) {
			Map map = new HashMap();
			map.put("model", edModel);
			Window window = (Window) Executions.createComponents(
					"/SysMessage/SysMessage_Edit.zul", null, map);
			window.doModal();
			search();
		} else {
			Messagebox.show("没有可编辑的草稿!","ERROR",Messagebox.YES,Messagebox.ERROR);
		}
	}
	
	// 删除
	@Command("delete")
	@NotifyChange({ "draftList", "replyList" })
	public void delete() {
		if (!edModel.getSyme_content().isEmpty()) {
			if (Messagebox.show("确认删除!", "CONFIRM", Messagebox.YES
					| Messagebox.NO, Messagebox.QUESTION) == Messagebox.YES) {
				int syme_id = edModel.getSyme_id();
				SysMessage_DraftListBll bll = new SysMessage_DraftListBll();
				int row = bll.delete(syme_id);

				if (row == 1) {
					if (Messagebox.show("删除成功!", "INFORMATION", Messagebox.YES,
							Messagebox.INFORMATION) == Messagebox.YES) {
						search();
					} else {
						Messagebox.show("删除失败,请联系IT部门!", "ERROR",
								Messagebox.YES, Messagebox.ERROR);
					}
				}
			}
		} else {
			Messagebox.show("没有可删除的草稿!","ERROR",Messagebox.YES,Messagebox.ERROR);
		}
	}
	
	// 快速发送
	@Command("quicksend")
	@NotifyChange({"draftList","replyList"})
	public void quicksend(){
		try {
			List<LoginModel> lgList = new ArrayList<LoginModel>();
			List<SysMessageModel> list = new ArrayList<SysMessageModel>();
			
			edModel.setSyme_state(1);
			
			lgList = new SysMessage_ReplyBll().getReplyList(edModel.getSyme_id(), userid);
			if (lgList.size() > 0) {
				for (int i = 0; i < lgList.size(); i++) {
					SysMessageModel smModel = new SysMessageModel();
					smModel.setSymr_log_id(lgList.get(i).getLog_id());
					smModel.setSymr_name(lgList.get(i).getLog_name());
					list.add(smModel);
				}

				int issuccess = new SysMessage_EditBll().EditSubmit(edModel, list, list.size());

				if (issuccess == 1) {
					if (Messagebox.show("发送成功!", "INFORMATION", Messagebox.YES,
							Messagebox.INFORMATION) == Messagebox.YES) {
						search();
					} else {
						Messagebox.show("发送失败,请联系IT部门!", "ERROR",
								Messagebox.YES, Messagebox.ERROR);
					}
				}
			} else {
				Messagebox.show("发送失败,没有接收人!", "ERROR",
						Messagebox.YES, Messagebox.ERROR);
			}
		} catch (Exception e) {
			Messagebox.show("未知错误,请联系IT部门!", "ERROR",
					Messagebox.YES, Messagebox.ERROR);
			System.out.println(e.toString());
		}
	}

	@Command("selectCon")
	/*@NotifyChange({ "content", "recipient" })*/
	public void selectCon(@BindingParam("each") SysMessageModel model) {
		edModel = model;
	}

	public List<SysMessageModel> getDraftList() {
		return draftList;
	}

	public void setDraftList(List<SysMessageModel> draftList) {
		this.draftList = draftList;
	}

	public Date getTime() {
		return time;
	}

	public void setTime(Date time) {
		this.time = time;
	}

	public SysMessageModel getEdModel() {
		return edModel;
	}

	public void setEdModel(SysMessageModel edModel) {
		this.edModel = edModel;
	}

	public String getSearcontent() {
		return searcontent;
	}

	public void setSearcontent(String searcontent) {
		this.searcontent = searcontent;
	}

	public List<SysMessageModel> getReplyList() {
		return replyList;
	}

	public void setReplyList(List<SysMessageModel> replyList) {
		this.replyList = replyList;
	}

}
