package Controller.SystemControl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.zkoss.bind.annotation.NotifyChange;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.event.MouseEvent;
import org.zkoss.zk.ui.select.SelectorComposer;
import org.zkoss.zk.ui.select.annotation.Listen;
import org.zkoss.zul.Label;
import org.zkoss.zul.ListModelList;
import org.zkoss.zul.Window;
import bll.SystemControl.AlarmMainBll;
import Model.AlarmLoginModel;
import Util.UserInfo;

public class AlarmMainItem_ListController extends SelectorComposer<Component> {

	private final Integer alcl_id = Integer.parseInt(Executions.getCurrent()
			.getArg().get("alcl_id").toString());
	Integer userId = Integer.valueOf(UserInfo.getUserid());

	private List<AlarmLoginModel> almList;

	AlarmMainBll bll = new AlarmMainBll();

	// 初始化
	public AlarmMainItem_ListController() {
		setAlmList();
	}

	// 弹出详细预警内容
	@Listen("onClick=label")
	@NotifyChange("almList")
	public void getLink(MouseEvent event) {
		Label lbl = (Label) event.getTarget();
		if (lbl.getTooltiptext() != "" && lbl.getTooltiptext() != null) {
			String url = lbl.getTooltiptext();
			Map map = new HashMap();
			map.put("p1", lbl.getValue());
			Window window = (Window) Executions
					.createComponents(url, null, map);
			window.doModal();

		}
	}

	public List<AlarmLoginModel> getAlmList() {
		return almList;
	}

	public void setAlmList() {
		this.almList = new ListModelList<AlarmLoginModel>(
				bll.getAlarmLoginListByPara(alcl_id, userId));
	}

	public Integer getAlcl_id() {
		return alcl_id;
	}

}
