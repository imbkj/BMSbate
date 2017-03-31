package Controller.SystemControl;

import java.sql.Date;

import org.zkoss.bind.annotation.BindingParam;
import org.zkoss.bind.annotation.Command;
import org.zkoss.bind.annotation.NotifyChange;
import org.zkoss.zk.ui.Component;
import javax.xml.soap.Text;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.event.EventListener;
import org.zkoss.zk.ui.select.SelectorComposer;
import org.zkoss.zk.ui.select.annotation.Listen;
import org.zkoss.zk.ui.select.annotation.Wire;
import org.zkoss.zk.ui.util.Clients;
import org.zkoss.zul.Combobox;
import org.zkoss.zul.Listbox;
import org.zkoss.zul.Listitem;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Textbox;
import org.zkoss.zul.Window;
import org.zkoss.zul.Messagebox.ClickEvent;
import java.util.List;
import java.util.Set;

import Model.LoginUserModel;
import Model.RoleModel;
import bll.SystemControl.PubRoleManangerBll;

public class PubRoleManangerController extends SelectorComposer<Component> {
	private static final long serialVersionUID = 1L;

	@Wire
	private Combobox role_id;
	@Wire
	private Listbox chosenLb;
	private Integer x;
	private Integer y;
	
	private List roleclist;
	
	PubRoleManangerBll bll = new PubRoleManangerBll();
	
	

	@Listen("onClick = #submitButton")
	public void submit() {
		x = 0;
		y = 0;
		List<Listitem> items = chosenLb.getItems();
		
		bll.PubRoleManangerBllDel((int) role_id.getSelectedItem().getValue());
		if (bll.Dochek2() > 0) {
			y = y + 1;
		}
				
		for (Listitem item : items) {
			
			bll.PubRoleManangerBllAdd(
					((LoginUserModel) item.getValue()).getLog_name(),
					(int) role_id.getSelectedItem().getValue());
			if (bll.Dochek() > 0) {
				x = x + 1;
			}
		}

		// 判断是否插入数据********************start**************************************************************
		if (x == chosenLb.getItemCount()) {
			EventListener<ClickEvent> clickListener = new EventListener<Messagebox.ClickEvent>() {
				public void onEvent(ClickEvent event) throws Exception {
					if (Messagebox.Button.OK.equals(event.getButton())) {
						Executions.sendRedirect("PubRole_Mananger.zul");
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
