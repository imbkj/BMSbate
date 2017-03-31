package Controller.EmHouse;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.zkoss.bind.annotation.BindingParam;
import org.zkoss.bind.annotation.Command;
import org.zkoss.bind.annotation.NotifyChange;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zul.ListModelList;
import org.zkoss.zul.Window;

import bll.EmHouse.EmHouse_AlarmBll;

import Model.EmHouseChangeModel;
import Model.LoginModel;
import Model.loginroleModel;
import Util.UserInfo;

public class EmHouse_returnListController {

	private List<EmHouseChangeModel> changelist = new ListModelList<>();
	private EmHouse_AlarmBll bll = new EmHouse_AlarmBll();
	private EmHouseChangeModel ecm = new EmHouseChangeModel();

	public EmHouse_returnListController() {

		changelist = bll.changeList(ecm, 3,
				Integer.valueOf(UserInfo.getUserid()));
	}

	@Command
	public void message(@BindingParam("a") EmHouseChangeModel em) {

		Map map = new HashMap<>();
		map.put("type", "住房公积金");// 业务类型:来自WfClass的wfcl_name
		map.put("id", em.getEmhc_id());// 业务表id
		map.put("tablename", "emhousechange");// 业务表名

		List<LoginModel> mlist = new ArrayList<LoginModel>();
		List<loginroleModel> msglist = new ListModelList<>();
		msglist = bll.getuserlist("39,40,45,43");
		for (loginroleModel m : msglist) {
			LoginModel lm = new LoginModel();
			lm.setLog_id(m.getLog_id());
			lm.setLog_name(m.getLog_name());
			mlist.add(lm);
		}
		// 收件人姓名和收件人id至少要填一个

		map.put("list", mlist);// 默认收件人list
		Window window = (Window) Executions.createComponents(
				"./SysMessage/Message_Add.zul", null, map);
		window.doModal();

	}

	public List<EmHouseChangeModel> getChangelist() {
		return changelist;
	}

	public void setChangelist(List<EmHouseChangeModel> changelist) {
		this.changelist = changelist;
	}

}
