package Controller;

import java.util.HashMap;
import java.util.Map;

import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.EventListener;
import org.zkoss.zk.ui.event.EventQueue;
import org.zkoss.zk.ui.event.EventQueues;
import org.zkoss.zk.ui.select.SelectorComposer;

import org.zkoss.zul.Window;

import Util.UserInfo;
import Util.PublishWindow;

public class EventQueuesController extends SelectorComposer<Component> {
	private static final long serialVersionUID = 1L;
	private EventQueue<Event> que;
	private EventQueue<Event> all;

	public EventQueuesController() {
		all = EventQueues.lookup("all", EventQueues.APPLICATION, true);
		all.subscribe(new EventListener<Event>() {
			public void onEvent(Event evt) {
				openWindow(evt);
			}
		});
		que = EventQueues.lookup("Uid" + UserInfo.getUserid(),
				EventQueues.APPLICATION, true);
		que.subscribe(new EventListener<Event>() {
			public void onEvent(Event evt) {
				openWindow(evt);
			}
		});
	}

	private void openWindow(Event evt) {
		PublishWindow pw = (PublishWindow) evt.getData();
		String openMethod = pw.getOpenMethod();
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("title", pw.getTitle());
		map.put("height", pw.getHeight());
		map.put("width", pw.getWidth());
		map.put("position", pw.getPosition());
		map.put("border", pw.getBorder());
		map.put("closable", pw.getClosable());
		map.put("sizable", pw.getSizable());
		map.put("maximizable", pw.getMaximizable());
		map.put("sendMessage", pw.getSendMessage());
		map.put("sendUrl", pw.getSendUrl());
		map.put("sendMap", pw.getSendMap());
		Window window = (Window) Executions.createComponents(
				"systemWindow.zul", null, map);
		if ("doPopup".equals(openMethod)) {
			window.doPopup();
		} else if ("doModal".equals(openMethod)) {
			window.doModal();
		} else if ("doOverlapped".equals(openMethod)) {
			window.doOverlapped();
		}

	}
}
