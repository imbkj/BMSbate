package Controller.SystemControl;

import java.sql.SQLException;
import java.util.List;

import impl.UserInfoImpl;

import org.zkoss.bind.annotation.Init;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.Session;
import org.zkoss.zk.ui.event.EventListener;
import org.zkoss.zk.ui.select.SelectorComposer;
import org.zkoss.zk.ui.select.annotation.Listen;
import org.zkoss.zk.ui.select.annotation.Wire;
import org.zkoss.zul.Combobox;
import org.zkoss.zul.ListModelList;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Textbox;
import org.zkoss.zul.Messagebox.ClickEvent;
import org.zkoss.zul.Window;

import service.UserInfoService;
import Model.AlarmClassModel;
import bll.SystemControl.AlarmClassBll;

public class AlarmClass_ModController extends SelectorComposer<Component> {
	
	private final Integer alcl_id = Integer.parseInt(Executions.getCurrent()
			.getArg().get("id").toString());

	private List<AlarmClassModel> amlist;
	private String alarmName;
	private String addName;
	private String alarmState;
	
	AlarmClassBll bll = new AlarmClassBll();

	@Wire
	Window winAlarmClassMod;
	@Wire
	Textbox tb;
	@Wire
	Combobox AlarmState;
	

	public AlarmClass_ModController(){
		setAmlist();
	}

	//修改项目类型
	@Listen("onClick = #btnSubmit")
	public void AddAlarmClass() {
		if (!tb.getValue().equals("")) {
			Session session = Executions.getCurrent().getDesktop().getSession();
			UserInfoService user = new UserInfoImpl(session);
			AlarmClassBll bll = new AlarmClassBll();

			String[] message = bll.ModAlarmClass(alcl_id, tb.getValue().toString(),user.getUsername(), AlarmState.getSelectedItem().getValue().toString());

			if (message[0].equals("1")) {
				EventListener<ClickEvent> clickListener = new EventListener<Messagebox.ClickEvent>() {
					public void onEvent(ClickEvent event) throws Exception {
						if (Messagebox.Button.OK.equals(event.getButton())) {
							winAlarmClassMod.detach();
						}
					}
				};
				// 弹出提示
				Messagebox.show(message[1], "操作提示",
						new Messagebox.Button[] { Messagebox.Button.OK },
						Messagebox.INFORMATION, clickListener);
			} else {
				// 弹出提示
				Messagebox.show(message[1], "操作提示", Messagebox.OK,
						Messagebox.ERROR);
			}
		} else {
			Messagebox.show("请输入需要预警的类型名称", "操作提示", Messagebox.OK,
					Messagebox.ERROR);
		}
	}

	public List<AlarmClassModel> getAmlist() {
		return amlist;
	}

	public void setAmlist() {
		this.amlist = bll.getAlarmClassById(alcl_id);
	}

	public String getAlarmName() {
		return alarmName;
	}

	public void setAlarmName(String alarmName) {
		this.alarmName = alarmName;
	}

	public String getAddName() {
		return addName;
	}

	public void setAddName(String addName) {
		this.addName = addName;
	}

	public String getAlarmState() {
		return alarmState;
	}

	public void setAlarmState(String alarmState) {
		this.alarmState = alarmState;
	}

	public Integer getAlcl_id() {
		return alcl_id;
	}
	
	
}
