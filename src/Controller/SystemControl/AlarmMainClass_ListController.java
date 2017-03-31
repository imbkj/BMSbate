package Controller.SystemControl;

import java.util.List;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.select.SelectorComposer;
import org.zkoss.zul.ListModelList;
import bll.SystemControl.AlarmMainBll;
import Model.AlarmLoginModel;
import Util.UserInfo;

public class AlarmMainClass_ListController extends SelectorComposer<Component> {
	private List<AlarmLoginModel> almList;
	Integer userId = Integer.valueOf(UserInfo.getUserid());

	AlarmMainBll bll = new AlarmMainBll();

	//初始化
	public AlarmMainClass_ListController() {
		setAlcList();
	}

	public List<AlarmLoginModel> getAlmList() {
		return almList;
	}

	public void setAlcList() {
		this.almList = new ListModelList<AlarmLoginModel>(
				bll.getAlarmLoginDisClassName(userId));
	}

}
