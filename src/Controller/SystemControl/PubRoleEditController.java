package Controller.SystemControl;

import java.sql.Date;

import org.zkoss.zk.ui.Component;
import javax.xml.soap.Text;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.event.EventListener;
import org.zkoss.zk.ui.select.SelectorComposer;
import org.zkoss.zk.ui.select.annotation.Listen;
import org.zkoss.zk.ui.select.annotation.Wire;
import org.zkoss.zk.ui.util.Clients;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Textbox;
import org.zkoss.zul.Label;
import org.zkoss.zul.Window;
import org.zkoss.zul.Messagebox.ClickEvent;


import bll.SystemControl.PubRoleEditBll;

public class PubRoleEditController extends SelectorComposer<Component> {
	private static final long serialVersionUID = 1L;
	@Wire
	private Textbox name;
	@Wire
	private Label rol_id;
	@Wire
	private Textbox index;
	
	@Listen("onClick = #submitButton")
	public void submit() {
		PubRoleEditBll pb = new PubRoleEditBll(name.getValue(),index.getValue(),Integer.parseInt(rol_id.getValue()));

		// 判断是否插入数据********************start**************************************************************
		if (pb.Dochek() > 0) {
			EventListener<ClickEvent> clickListener = new EventListener<Messagebox.ClickEvent>() {
				public void onEvent(ClickEvent event) throws Exception {
					if (Messagebox.Button.OK.equals(event.getButton())) {
						Executions.sendRedirect("PubRole_Select.zul");
					}
				}
			};
			Messagebox.show("提交成功!", "操作提示",
					new Messagebox.Button[] { Messagebox.Button.OK },
					Messagebox.INFORMATION, clickListener);
		} else {
			Messagebox.show("提交失败!", "操作提示", Messagebox.OK, Messagebox.ERROR);
		}
		// ******************************end*****************************************************************
	}
}
