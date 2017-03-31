package Controller.EmHouse;

import org.zkoss.bind.Binder;
import org.zkoss.bind.annotation.Command;
import org.zkoss.bind.annotation.ContextParam;
import org.zkoss.bind.annotation.ContextType;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.Path;
import org.zkoss.zk.ui.event.EventListener;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Window;
import org.zkoss.zul.Messagebox.ClickEvent;

import com.sun.istack.internal.FinalArrayList;

import bll.EmHouse.EmHouse_InfoBll;

import Model.EmHouseChangeModel;
import Util.UserInfo;

public class EmHouse_InfoController {
	private EmHouseChangeModel ehm = new EmHouseChangeModel();
	private EmHouse_InfoBll bll = new EmHouse_InfoBll();
	private Integer daid = Integer.valueOf(Executions.getCurrent().getArg()
			.get("daid").toString());
	//private Integer taprId = Integer.valueOf(Executions.getCurrent().getArg()
	//		.get("id").toString());
	private String username = UserInfo.getUsername();
	
	private Window win = (Window) Path.getComponent("/winInfo");

	public EmHouse_InfoController() {
		try {
			ehm = bll.getInfoById(daid).get(0);
			ehm.setEmhc_declarename(username);
		} catch (Exception e) {
			// TODO: handle exception
			
		}
	}
	
	/*
	@Command("submit")
	public void submit(@ContextParam(ContextType.VIEW) final Component view){
		Messagebox.show("确认提交数据?", "操作提示", new Messagebox.Button[] {
				Messagebox.Button.OK, Messagebox.Button.CANCEL },
				Messagebox.QUESTION,
				new EventListener<Messagebox.ClickEvent>() {
					@Override
					public void onEvent(ClickEvent event) throws Exception {
						// TODO Auto-generated method stub

						if (Messagebox.Button.OK.equals(event.getButton())) {

							String[] str = bll.declareInfo(ehm);

							if (str[0] =="1") {

								Messagebox.show("操作成功!", "操作提示",
										Messagebox.OK,
										Messagebox.INFORMATION);					
								
							} else {
								Messagebox.show("操作失败!", "操作提示",
										Messagebox.OK, Messagebox.ERROR);

							}
							win.detach();

						}
					}
				});
	}
	*/

	public EmHouseChangeModel getEhm() {
		return ehm;
	}

	public void setEhm(EmHouseChangeModel ehm) {
		this.ehm = ehm;
	}

}
