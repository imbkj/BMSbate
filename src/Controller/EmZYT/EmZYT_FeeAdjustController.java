package Controller.EmZYT;

import java.util.List;

import org.zkoss.bind.annotation.BindingParam;
import org.zkoss.bind.annotation.Command;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.event.EventListener;
import org.zkoss.zul.Datebox;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Window;
import org.zkoss.zul.Messagebox.ClickEvent;

import Model.EmZYTModel;
import Util.DateStringChange;
import Util.UserInfo;
import bll.EmZYT.EmZYT_OperateBll;
import bll.EmZYT.EmZYT_SelectBll;

public class EmZYT_FeeAdjustController {
	private EmZYT_SelectBll sbll = new EmZYT_SelectBll();
	private DateStringChange dsc = new DateStringChange();
	private EmZYT_OperateBll obll = new EmZYT_OperateBll();

	private List<EmZYTModel> dataList;

	private String emzt_id = Executions.getCurrent().getArg().get("emzt_id")
			.toString();
	private String sql = "";

	public EmZYT_FeeAdjustController() {
		sql = " AND id in(" + emzt_id + ")";
		dataList = sbll.getEmZYTList(sql);
	}

	// 提交
	@Command("submit")
	public void submit(@BindingParam("win") final Window win,
			@BindingParam("bmsMonth") Datebox bmsMonth) {

		String bmsMonthString;
		try {
			bmsMonthString = dsc.DatetoSting(bmsMonth.getValue(), "yyyy-MM-dd");
		} catch (Exception e) {
			bmsMonthString = "";
		}

		if (!"".equals(bmsMonthString) && dataList.size() > 0) {
			String[] message;
			int j = 0;
			for (int i = 0; i < dataList.size(); i++) {
				EmZYTModel m = dataList.get(i);
				m.setEmzt_declarename(UserInfo.getUsername());
				m.setEmzt_state(1);
				m.setEmzt_ylstartBMS(bmsMonthString);
				m.setEmzt_jlstartBMS(bmsMonthString);
				m.setEmzt_housestartBMS(bmsMonthString);
				// 调用方法更新数据
				message = obll.upEmZYTFeeAdjust(m);
				if ("1".equals(message[0])) {
					j = j + 1;
				}
			}

			String msg;
			if (j == dataList.size()) {
				msg = "操作成功！";
			} else {
				msg = "部分数据操作不成功，请检查！";
			}
			EventListener<ClickEvent> clickListener = new EventListener<Messagebox.ClickEvent>() {
				public void onEvent(ClickEvent event) throws Exception {
					if (Messagebox.Button.OK.equals(event.getButton())) {
						win.detach();
					}
				}
			};
			// 弹出提示
			Messagebox.show(msg, "操作提示",
					new Messagebox.Button[] { Messagebox.Button.OK },
					Messagebox.INFORMATION, clickListener);

		} else {
			if ("".equals(bmsMonthString)) {
				// 弹出提示
				Messagebox.show("请选择“生效日期”！", "操作提示", Messagebox.OK,
						Messagebox.ERROR);
			} else if (dataList.size() < 1) {
				// 弹出提示
				Messagebox.show("请选择数据！", "操作提示", Messagebox.OK,
						Messagebox.ERROR);
			}
		}
	}

	public List<EmZYTModel> getDataList() {
		return dataList;
	}

	public void setDataList(List<EmZYTModel> dataList) {
		this.dataList = dataList;
	}

}
