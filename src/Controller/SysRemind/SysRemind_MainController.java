package Controller.SysRemind;

import org.zkoss.bind.annotation.Command;
import org.zkoss.bind.annotation.NotifyChange;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zul.Window;

import bll.SysRemind.SysRemindBll;
import Model.PubRemindModel;
import Util.UserInfo;
import Util.plyUtil;

public class SysRemind_MainController {

	private PubRemindModel prModel = new PubRemindModel();
	private String content = "";
	private int notremindcount;
	private int isremindcount;
	int userid = 0;

	public SysRemind_MainController() {
		userid = Integer.parseInt(UserInfo.getUserid());
		init();
	}
	
	public void init(){
		SysRemindBll bll = new SysRemindBll();
		setPrModel(bll.getRemindMain(userid));
		content=plyUtil.substr(prModel.getContent(), 25);
		notremindcount = prModel.getNotRemindcount();
		isremindcount = prModel.getIsRemindcount();
	}

	// 弹出未提醒列表窗口
	@Command("notremind")
	@NotifyChange({"notremindcount","isremindcount","content"})
	public void notremind() {
		Window window = (Window) Executions.createComponents(
				"/SysRemind/SysRemind_NotRemindList.zul", null, null);
		window.doModal();
		init();
	}

	// 弹出已提醒列表窗口
	@Command("reminded")
	@NotifyChange({"notremindcount","isremindcount","content"})
	public void reminded() {
		Window window = (Window) Executions.createComponents(
				"/SysRemind/SysRemind_RemindedList.zul", null, null);
		window.doModal();
		init();
	}

	public PubRemindModel getPrModel() {
		return prModel;
	}

	public void setPrModel(PubRemindModel prModel) {
		this.prModel = prModel;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public int getNotremindcount() {
		return notremindcount;
	}

	public void setNotremindcount(int notremindcount) {
		this.notremindcount = notremindcount;
	}

	public int getIsremindcount() {
		return isremindcount;
	}

	public void setIsremindcount(int isremindcount) {
		this.isremindcount = isremindcount;
	}
}
