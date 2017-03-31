package Controller.SysRemind;

import java.util.List;

import org.zkoss.zul.ListModelList;

import bll.SysRemind.SysRemindBll;

import Model.PubRemindModel;
import Util.UserInfo;

public class SysRemind_NotRemindListController {

	private List<PubRemindModel> notremindlist = new ListModelList<PubRemindModel>();
	int userid;

	// 初始化
	public SysRemind_NotRemindListController() {
		SysRemindBll bll = new SysRemindBll();
		userid = Integer.parseInt(UserInfo.getUserid());
		setNotremindlist(bll.getRemindList(userid, 0));
	}

	public List<PubRemindModel> getNotremindlist() {
		return notremindlist;
	}

	public void setNotremindlist(List<PubRemindModel> notremindlist) {
		this.notremindlist = notremindlist;
	}
}
