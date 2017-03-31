package Controller.SystemControl;

import impl.UserInfoImpl;

import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.Session;
import org.zkoss.zk.ui.event.EventListener;
import org.zkoss.zk.ui.select.SelectorComposer;
import org.zkoss.zk.ui.select.annotation.Listen;
import org.zkoss.zk.ui.select.annotation.Wire;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Textbox;
import org.zkoss.zul.Messagebox.ClickEvent;

import bll.SystemControl.AlarmClassBll;

import service.UserInfoService;

public class AlarmClass_AddController extends SelectorComposer<Component> {

	@Wire
	Textbox tb;
	
	public AlarmClass_AddController(){
		
	}
	
	//新增项目类型
	@Listen("onClick = #btnSubmit")
	public void AddAlarmClass() {
		if(!tb.getValue().equals("")){
			Session session = Executions.getCurrent().getDesktop()
					.getSession();
			UserInfoService user = new UserInfoImpl(session);
			AlarmClassBll bll = new AlarmClassBll();
			
			String[] message = bll.AddAlarmClass(tb.getValue(), user.getUsername());
			
			if (message[0].equals("1")) {
				EventListener<ClickEvent> clickListener = new EventListener<Messagebox.ClickEvent>() {
	                public void onEvent(ClickEvent event) throws Exception {
	                    if (Messagebox.Button.OK.equals(event.getButton())) {
	                        Executions.sendRedirect("AlarmClass_Manager.zul");	//跳转页面
	                    }
	                }
	            };
	          //弹出提示
	            Messagebox.show(message[1], "操作提示", new Messagebox.Button[] { Messagebox.Button.OK },
	            		Messagebox.INFORMATION, clickListener);	
			}else {
				//弹出提示
				Messagebox.show(message[1], "操作提示", Messagebox.OK, Messagebox.ERROR);
			}
		}else{
			Messagebox.show("请输入需要预警的类型名称", "操作提示", Messagebox.OK,
							Messagebox.ERROR);
		}
	}
}
