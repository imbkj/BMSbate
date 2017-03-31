package Controller.KnowledgeBase;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.zkoss.bind.annotation.BindingParam;
import org.zkoss.bind.annotation.Command;
import org.zkoss.zk.ui.event.*;
import org.zkoss.zul.Hbox;
import org.zkoss.zul.Label;
import org.zkoss.zul.Separator;
import org.zkoss.zul.Textbox;
import org.zkoss.zul.Vbox;

import Model.SysMessageModel;
import Util.UserInfo;

public class publishController {

	EventQueue que = EventQueues.lookup("chat", EventQueues.APPLICATION, true);
	String username = "";
	
	public publishController() {
		username = UserInfo.getUsername();	
	}
	
	@Command("initinf")
	public void initinf(@BindingParam("self") final Vbox inf){
		que.subscribe(new EventListener() {
			public void onEvent(Event evt) {
				SysMessageModel sModel = new SysMessageModel();
				sModel = (SysMessageModel)evt.getData();
				new Label(sModel.getSyme_addtime()).setParent(inf);
				new Separator().setParent(inf);
				Hbox hbox = new Hbox();
				inf.appendChild(hbox);
				new Label(sModel.getSyme_addname()+"：").setParent(hbox);
				new Label(sModel.getSyme_content()).setParent(hbox);
			}
		});
	}

	@Command("post")
	public void post(@BindingParam("self") Textbox tb) {
		SysMessageModel sModel = new SysMessageModel();
		sModel.setSyme_addname(username);
		sModel.setSyme_addtime(new SimpleDateFormat("yyyy-MM-dd hh:mm:ss").format(new Date()));
		sModel.setSyme_content(tb.getValue());
		if (tb.getValue().length() > 0) {
			tb.setValue("");
			que.publish(new Event("onChat", null, sModel));
		}
	}
}
