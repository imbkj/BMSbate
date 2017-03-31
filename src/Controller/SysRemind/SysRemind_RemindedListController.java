package Controller.SysRemind;

import java.util.List;

import org.zkoss.zul.ListModelList;

import bll.SysRemind.SysRemindBll;

import Model.PubRemindModel;
import Util.UserInfo;

public class SysRemind_RemindedListController {

	private List<PubRemindModel> remindedlist = new ListModelList<PubRemindModel>();
	int userid;

	public SysRemind_RemindedListController() {
		SysRemindBll bll = new SysRemindBll();
		userid = Integer.parseInt(UserInfo.getUserid());
		setRemindedlist(bll.getRemindList(userid, 1));
	}

	public List<PubRemindModel> getRemindedlist() {
		return remindedlist;
	}

	public void setRemindedlist(List<PubRemindModel> remindedlist) {
		this.remindedlist = remindedlist;
	}
}
